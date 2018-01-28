/**
 * 生成程序ChangeDataPublisher.java	1.0              2006-12-22
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.dtd.AttributeDecl;
import org.dom4j.dtd.ElementDecl;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultDocumentType;
import com.faw_qm.change.model.QMChangeRequestIfc;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.model.StreamDataInfo;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;

/**
 * <p>Title: 生成变更请求的附件，即变更通知单.html。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public class ChangeDataPublisher
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(ChangeDataPublisher.class);

    /**
     * XML编码类型。
     */
    private static final String encoding = "gb2312";

    /**
     * 与XML文件配套使用的样式单的类型及文件名。
     */
    private final String xslFileName = "ChangeOutPut_Data.xsl";

    /**
     * 附件文件名称。
     */
    private final String xmlFileName = "变更通知单.html";

    /**
     * 附加输出路径。
     */
    private String pathName = (String) RemoteProperty.getProperty("pathName");

    /**
     * 传入的变更请求。
     */
    private QMChangeRequestIfc changeRequestIfc;

    /**
     * 设置比较数据，被BaseDataPublisher调用。
     * @param changerequest 变更请求。
     * @throws QMException
     */
    public final void setCompareData(QMChangeRequestIfc changerequest)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("setCompareData(QMChangeRequestIfc) - start"); //$NON-NLS-1$
        }
        changeRequestIfc = changerequest;
        try
        {
            //1 通过变更请求获得变更单,通过变更单获得变更活动关联的changerecord关联,从而获得变更后的part数据
            //2 过滤数据,只提取part,去掉文档和广义部件等其他数据
            //3 数据集合放在recoedarratlist
            //4 获得变更前计划的零件数据affectactivedata,
            //5 过滤只提取part,去掉文档和广义部件等其他数据
            //  返回的结果集是第一个位置为Arraylist变更后的结果,第二个位置变更前的结果
            ArrayList[] list = (ArrayList[]) RequestHelper.request(
                    "PromulgateNotifyService", "obtainDataForChange",
                    new Class[]{BaseValueIfc.class},
                    new Object[]{changeRequestIfc});
            List afterChangelist = (List) list[0];
            List beforeChangelist = (List) list[1];
            //测试数据：
            //QMPartIfc after = (QMPartIfc) RequestHelper.request(
            //        "PersistService", "refreshInfo", new Class[]{String.class},
            //        new Object[]{"QMPart_972605"});
            //QMPartIfc before = (QMPartIfc) RequestHelper.request(
            //        "PersistService", "refreshInfo", new Class[]{String.class},
            //        new Object[]{"QMPart_970154"});
            //List afterChangelist = new ArrayList();
            //afterChangelist.add(after);
            //List beforeChangelist = new ArrayList();
            //beforeChangelist.add(before);
            //比较版本
            getCompareData(beforeChangelist, afterChangelist);
        }
        catch (QMException e)
        {
            //"附加变更通知单.html失败！"
            logger.error(Messages.getString("Util.49"), e); //$NON-NLS-1$
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("setCompareData(QMChangeRequestIfc) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 获取比较数据。
     * 变更后的零部件只能多，不能少，不考虑减少的情况。
     * 变更后有而变更前没有的零部件就是新增的。新增零部件变更前的版本和子件数量全部显示为空。
     * @param beforeChangelist 变更前零部件集合。
     * @param afterChangelist 变更后零部件集合。
     * @throws QMException
     */
    private final void getCompareData(List beforeChangelist,
            List afterChangelist) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getCompareData(List, List) - start"); //$NON-NLS-1$
        }
        HashMap befHashMap = new HashMap();
        List dataList = new ArrayList();
        Iterator iter = beforeChangelist.iterator();
        while (iter.hasNext())
        {
            QMPartIfc beforePart = (QMPartIfc) iter.next();
            befHashMap.put(beforePart.getPartNumber(), beforePart);
        }
        for (int j = 0; j < afterChangelist.size(); j++)
        {
            QMPartIfc afterPart = (QMPartIfc) afterChangelist.get(j);
            //如果变更前后都有该零部件。
            if(befHashMap.containsKey(afterPart.getPartNumber()))
            {
                QMPartIfc beforePart = (QMPartIfc) befHashMap.get(afterPart
                        .getPartNumber());
                if(!(beforePart.getVersionID().compareTo(
                        afterPart.getVersionID()) == 0))
                {
                    dataList.addAll(getDisplayData(beforePart, afterPart));
                }
            }
            //否则，就是变更后新增的。
            else
            {
                dataList.addAll(getDisplayData(null, afterPart));
            }
        }
        createHtml(dataList);
        if(logger.isDebugEnabled())
        {
            logger.debug("getCompareData(List, List) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 获取显示数据。只显示前后子件数量有变化的数据，没变化的除去。
     * 结构只比较一层，同级含有相同子件先合并数量。
     * 拿变更后的结构零部件和变更前的比，前后都有继续比较，后没有前，则结构子件被删除，后的数量为0；
     * 前没有后，则结构子件被增加，前的数量为0。
     * @param beforePart 变更前的零部件集合。
     * @param afterPart 变更后的零部件集合。
     * @return 显示数据集合。所有元素都是一个String[4]数组。
     * @throws QMException
     */
    private final List getDisplayData(QMPartIfc beforePart, QMPartIfc afterPart)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getDisplayData(QMPartIfc, QMPartIfc) - start"); //$NON-NLS-1$
        }
        List dataList = new ArrayList();
        List beforeUsageLinkList = new ArrayList();
        List afterUsageLinkList = new ArrayList();
        if(beforePart != null)
        {
            beforeUsageLinkList = uniteQuantity(getUsageLinkMap(beforePart));
        }
        //合并后的使用结构关联集合。
        afterUsageLinkList = uniteQuantity(getUsageLinkMap(afterPart));
        HashMap beforeUsageHashMap = new HashMap();
        for (int i = 0; i < beforeUsageLinkList.size(); i++)
        {
            beforeUsageHashMap.put(((PartUsageLinkIfc) beforeUsageLinkList
                    .get(i)).getLeftBsoID(), beforeUsageLinkList.get(i));
        }
        HashMap afterUsageHashMap = new HashMap();
        for (int i = 0; i < afterUsageLinkList.size(); i++)
        {
            afterUsageHashMap.put(
                    ((PartUsageLinkIfc) afterUsageLinkList.get(i))
                            .getLeftBsoID(), afterUsageLinkList.get(i));
        }
        //变更前不存在该零部件。
        if(beforePart == null)
        {
            String[] tempArray4 = new String[4];
            tempArray4[0] = "0";
            tempArray4[1] = afterPart.getPartNumber() + "("
                    + afterPart.getPartName() + ")";
            tempArray4[2] = "";
            tempArray4[3] = afterPart.getVersionID();
            dataList.add(tempArray4);
            for (int i = 0; i < afterUsageLinkList.size(); i++)
            {
                PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) afterUsageLinkList
                        .get(i);
                QMPartMasterIfc childPartMasterIfc = (QMPartMasterIfc) RequestHelper
                        .request("PersistService", "refreshInfo",
                                new Class[]{String.class},
                                new Object[]{usageLinkIfc.getLeftBsoID()});
                tempArray4 = new String[4];
                tempArray4[0] = "--1";
                tempArray4[1] = childPartMasterIfc.getPartNumber() + "("
                        + childPartMasterIfc.getPartName() + ")";
                tempArray4[2] = "";
                tempArray4[3] = getQuantity(usageLinkIfc);
                dataList.add(tempArray4);
            }
        }
        //变更前后都存在该零部件。
        else
        {
            String[] tempArray4 = new String[4];
            tempArray4[0] = "0";
            tempArray4[1] = afterPart.getPartNumber() + "("
                    + afterPart.getPartName() + ")";
            tempArray4[2] = beforePart.getVersionID();
            tempArray4[3] = afterPart.getVersionID();
            dataList.add(tempArray4);
            for (int i = 0; i < beforeUsageLinkList.size(); i++)
            {
                PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) beforeUsageLinkList
                        .get(i);
                QMPartMasterIfc childPartMasterIfc = (QMPartMasterIfc) RequestHelper
                        .request("PersistService", "refreshInfo",
                                new Class[]{String.class},
                                new Object[]{usageLinkIfc.getLeftBsoID()});
                if(afterUsageHashMap.containsKey(childPartMasterIfc.getBsoID()))
                {
                    tempArray4 = new String[4];
                    tempArray4[0] = "--1";
                    tempArray4[1] = childPartMasterIfc.getPartNumber() + "("
                            + childPartMasterIfc.getPartName() + ")";
                    tempArray4[2] = getQuantity(usageLinkIfc);
                    tempArray4[3] = getQuantity((PartUsageLinkIfc) afterUsageHashMap
                            .get(childPartMasterIfc.getBsoID()));
                    if(!tempArray4[2].equals(tempArray4[3]))
                        dataList.add(tempArray4);
                    afterUsageHashMap.remove(childPartMasterIfc.getBsoID());
                }
                else
                {
                    tempArray4 = new String[4];
                    tempArray4[0] = "--1";
                    tempArray4[1] = childPartMasterIfc.getPartNumber() + "("
                            + childPartMasterIfc.getPartName() + ")";
                    tempArray4[2] = getQuantity(usageLinkIfc);
                    tempArray4[3] = "0";
                    dataList.add(tempArray4);
                }
            }
            Iterator afterUsageIter = afterUsageHashMap.values().iterator();
            List afterUsageList = new ArrayList();
            while (afterUsageIter.hasNext())
                afterUsageList.add(afterUsageIter.next());
            for (int i = 0; i < afterUsageList.size(); i++)
            {
                PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) afterUsageList
                        .get(i);
                QMPartMasterIfc childPartMasterIfc = (QMPartMasterIfc) RequestHelper
                        .request("PersistService", "refreshInfo",
                                new Class[]{String.class},
                                new Object[]{usageLinkIfc.getLeftBsoID()});
                tempArray4 = new String[4];
                tempArray4[0] = "--1";
                tempArray4[1] = childPartMasterIfc.getPartNumber() + "("
                        + childPartMasterIfc.getPartName() + ")";
                tempArray4[2] = "0";
                tempArray4[3] = getQuantity(usageLinkIfc);
                dataList.add(tempArray4);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getDisplayData(QMPartIfc, QMPartIfc) - end"); //$NON-NLS-1$
        }
        return dataList;
    }

    /**
     * 合并结构中使用子件的数量。
     * @param usageLinkMap 待合并数量的使用结构Map，值为PartUsageLinkIfc。
     * @return List 合并后的使用结构关联集合。
     */
    private final List uniteQuantity(HashMap usageLinkMap)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("uniteQuantity(HashMap) - start"); //$NON-NLS-1$
        }
        //合并后的使用结构关联集合。
        List usageLinkList = new ArrayList();
        Iterator iter = usageLinkMap.values().iterator();
        PartUsageLinkIfc usageLinkIfc = null;
        PartUsageLinkIfc usageLinkIfc2 = null;
        float quantity = 0;
        String leftBsoID = "";
        boolean flag = false;
        while (iter.hasNext())
        {
            usageLinkIfc = (PartUsageLinkIfc) iter.next();
            quantity = usageLinkIfc.getQuantity();
            leftBsoID = usageLinkIfc.getLeftBsoID();
            //标识在集合usageLinkList中是否存在当前被循环的这个零部件,初始情况下,认为不存在:
            flag = false;
            //对已有的合并完毕的集合进行循环:
            for (int i = 0; i < usageLinkList.size(); i++)
            {
                usageLinkIfc2 = (PartUsageLinkIfc) usageLinkList.get(i);
                float oldQuantity = usageLinkIfc2.getQuantity();
                String oldLeftBsoID = usageLinkIfc2.getLeftBsoID();
                //如果使用的是同一个零部件的话,合并数量,
                if(leftBsoID.equals(oldLeftBsoID))
                {
                    usageLinkIfc2.setQuantity(quantity + oldQuantity);
                    //找到了这个零部件:
                    flag = true;
                    usageLinkList.set(i, usageLinkIfc2);
                    break;
                }
            }
            if(!flag)
                usageLinkList.add(usageLinkIfc);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("uniteQuantity(HashMap) - end"); //$NON-NLS-1$
        }
        return usageLinkList;
    }

    /**
     * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
     * 结果存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
     * @param partIfc 零部件。
     * @throws QMXMLException
     */
    private final HashMap getUsageLinkMap(final QMPartIfc partIfc)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(QMPartIfc) - start"); //$NON-NLS-1$
        }
        final HashMap usageLinkMap = new HashMap();
        //通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
        List usesPartList = new ArrayList();
        try
        {
            usesPartList = (List) RequestHelper.request("StandardPartService",
                    "getUsesPartMasters", new Class[]{QMPartIfc.class},
                    new Object[]{partIfc});
        }
        catch (QMException e)
        {
            //"获取名为*的零部件结构时出错！"
            logger.error(Messages.getString("Util.17", new Object[]{partIfc
                    .getPartNumber()})
                    + e);
            throw new QMXMLException(e);
        }
        //将要发布的子关联放到HashMap中。
        Iterator iter = usesPartList.iterator();
        while (iter.hasNext())
        {
            Object obj = iter.next();
            usageLinkMap.put(obj, obj);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(QMPartIfc) - end"); //$NON-NLS-1$
        }
        return usageLinkMap;
    }

    /**
     * 去掉数量无用的.0部分。
     * @return 处理后的数量。
     */
    private final String getQuantity(PartUsageLinkIfc usageLinkIfc)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getQuantity(PartUsageLinkIfc) - start"); //$NON-NLS-1$
        }
        String quantityStr = Float.toString(usageLinkIfc.getQuantity());
        if(quantityStr.endsWith(".0"))
        {
            quantityStr = quantityStr.substring(0, quantityStr.length() - 2);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getQuantity(PartUsageLinkIfc) - end"); //$NON-NLS-1$
        }
        return quantityStr;
    }

    /**
     * 创建HTML文件。
     * @param dataList 所有需要显示的数据的集合。所有元素都是一个String[4]数组。
     * @throws QMException
     */
    private final void createHtml(List dataList) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("createHtml(List) - start"); //$NON-NLS-1$
        }
        Document doc = DocumentHelper.createDocument();
        //设置XML美化格式和XML声明。
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置XML编码类型。
        format.setEncoding("gb2312");
        writeDTDInfo(doc);
        writeDocContent(doc, dataList);
        try
        {
            doc = styleDocument(doc);
            write(doc);
            if(!pathName.endsWith(BaseDataPublisher.endString))
            {
                pathName += BaseDataPublisher.endString;
            }
            File file = new File(pathName + xmlFileName);
            long l = file.length();
            byte[] byteStream = new byte[(int) l];
            try
            {
                //转化为数据流
                FileInputStream in = new FileInputStream(file);
                in.read(byteStream);
                in.close();
            }
            catch (FileNotFoundException ex1)
            {
                throw new QMException(ex1);
            }
            catch (IOException ex1)
            {
                throw new QMException(ex1);
            }
            StreamDataInfo streamDataInfo = new StreamDataInfo();
            streamDataInfo.setDataContent(byteStream);
            RequestHelper.request("PromulgateNotifyService",
                    "setContentForChangeRequest", new Class[]{
                            ContentHolderIfc.class, File.class,
                            StreamDataInfo.class}, new Object[]{
                            changeRequestIfc, file, streamDataInfo});
        }
        catch (Exception e)
        {
            logger.error("createHtml(List)", e); //$NON-NLS-1$
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("createHtml(List) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 写DTD描述信息。
     * @param doc XML文档。
     */
    private final void writeDTDInfo(Document doc)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("writeDTDInfo(Document) - start"); //$NON-NLS-1$
        }
        DocumentType docType = new DefaultDocumentType();
        docType.setElementName("bom");
        docType.setInternalDeclarations(getInternalDeclarations());
        doc.setDocType(docType);
        if(logger.isDebugEnabled())
        {
            logger.debug("writeDTDInfo(Document) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 获取DTD描述信息列表。
     * @return List DTD描述信息列表。
     */
    private final List getInternalDeclarations()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getInternalDeclarations() - start"); //$NON-NLS-1$
        }
        List decls = new ArrayList();
        decls.add(new ElementDecl("bom", "(description, table+)"));
        decls.add(new ElementDecl("description",
                "(filenumber,type,date,sourcetag,notes)"));
        decls.add(new ElementDecl("filenumber", "(#PCDATA)"));
        decls.add(new ElementDecl("type", "(#PCDATA)"));
        decls.add(new ElementDecl("date", "(#PCDATA)"));
        decls.add(new ElementDecl("sourcetag", "(#PCDATA)"));
        decls.add(new ElementDecl("notes", "(#PCDATA)"));
        decls.add(new ElementDecl("table", "(col_header+, record+)"));
        decls
                .add(new AttributeDecl("table", "name", "CDATA", "#REQUIRED",
                        null));
        decls.add(new ElementDecl("col_header", "(#PCDATA)"));
        decls.add(new AttributeDecl("col_header", "id", "CDATA", "#REQUIRED",
                null));
        decls.add(new AttributeDecl("col_header", "type", "CDATA", "#REQUIRED",
                null));
        decls.add(new ElementDecl("record", "(col+)"));
        decls.add(new ElementDecl("col", "(#PCDATA)"));
        decls.add(new AttributeDecl("col", "id", "CDATA", "#REQUIRED", null));
        if(logger.isDebugEnabled())
        {
            logger.debug("getInternalDeclarations() - end"); //$NON-NLS-1$
        }
        return decls;
    }

    /**
     * 写XML文档的内容。
     * @param doc XML文档。
     * @param dataList 所有需要显示的数据的集合。所有元素都是一个String[4]数组。
     */
    private final void writeDocContent(Document doc, List dataList)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("writeDocContent(Document, List) - start"); //$NON-NLS-1$
        }
        doc.addElement("bom");
        Element elem = doc.getRootElement().addElement("description");
        if(changeRequestIfc != null)
            elem.addElement("filenumber").addText(changeRequestIfc.getNumber());
        else
            elem.addElement("filenumber").addText("");
        elem.addElement("type").addText("");
        SimpleDateFormat simple = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.S z"); //生成文件时间。
        elem.addElement("date").addText(simple.format(new Date()));
        elem.addElement("sourcetag").addText("");
        elem.addElement("notes").addText("");
        Element table = doc.getRootElement().addElement("table");
        table.addAttribute("name", "");
        table.addElement("col_header").addAttribute("id", "1").addAttribute(
                "type", "").addText("层级");
        table.addElement("col_header").addAttribute("id", "2").addAttribute(
                "type", "").addText("零件");
        table.addElement("col_header").addAttribute("id", "3").addAttribute(
                "type", "").addText("变更前(版本/数量)");
        table.addElement("col_header").addAttribute("id", "4").addAttribute(
                "type", "").addText("变更后(版本/数量)");
        for (int i = 0; i < dataList.size(); i++)
        {
            Element recordElem = table.addElement("record");
            String[] objs = (String[]) dataList.get(i);
            for (int j = 0; j < objs.length; j++)
            {
                recordElem.addElement("col").addAttribute("id",
                        String.valueOf(j + 1)).addText(objs[j]);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("writeDocContent(Document, List) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 根据指定的XSL文件格式化XML文档内容。
     * @param document XML文档。
     * @return 格式化后的HTML文档。
     * @throws Exception
     */
    private final Document styleDocument(Document document) throws Exception
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("styleDocument(Document) - start"); //$NON-NLS-1$
        }
        // load the transformer using JAXP
        TransformerFactory factory = TransformerFactory.newInstance();
        File xslFile = new File(getClass().getResource(xslFileName).getFile());
        Transformer transformer = factory.newTransformer(new StreamSource(
                xslFile));
        // now lets style the given document
        DocumentSource source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);
        // return the transformed document
        Document transformedDoc = result.getDocument();
        if(logger.isDebugEnabled())
        {
            logger.debug("styleDocument(Document) - end"); //$NON-NLS-1$
        }
        return transformedDoc;
    }

    /**
     * 在目标输出路径生成文档文件。
     * @param document 文档。
     * @throws IOException
     */
    private final void write(Document document) throws IOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("write(Document) - start"); //$NON-NLS-1$
        }
        if(!pathName.endsWith("/"))
        {
            pathName += "/";
        }
        File path = new File(pathName);
        if(!path.exists())
        {
            path.mkdir();
        }
        //设置XML美化格式和XML声明。
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置XML编码类型。
        format.setEncoding(encoding);
        //向目标路径写入XML数据文件。
        XMLWriter writer = new XMLWriter(
                new FileWriter(pathName + xmlFileName), format);
        writer.write(document);
        writer.flush();
        writer.close();
        if(logger.isDebugEnabled())
        {
            logger.debug("write(Document) - end"); //$NON-NLS-1$
        }
    }
}