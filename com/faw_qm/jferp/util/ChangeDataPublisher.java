/**
 * ���ɳ���ChangeDataPublisher.java	1.0              2006-12-22
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ���ɱ������ĸ����������֪ͨ��.html��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
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
     * XML�������͡�
     */
    private static final String encoding = "gb2312";

    /**
     * ��XML�ļ�����ʹ�õ���ʽ�������ͼ��ļ�����
     */
    private final String xslFileName = "ChangeOutPut_Data.xsl";

    /**
     * �����ļ����ơ�
     */
    private final String xmlFileName = "���֪ͨ��.html";

    /**
     * �������·����
     */
    private String pathName = (String) RemoteProperty.getProperty("pathName");

    /**
     * ����ı������
     */
    private QMChangeRequestIfc changeRequestIfc;

    /**
     * ���ñȽ����ݣ���BaseDataPublisher���á�
     * @param changerequest �������
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
            //1 ͨ����������ñ����,ͨ���������ñ���������changerecord����,�Ӷ���ñ�����part����
            //2 ��������,ֻ��ȡpart,ȥ���ĵ��͹��岿������������
            //3 ���ݼ��Ϸ���recoedarratlist
            //4 ��ñ��ǰ�ƻ����������affectactivedata,
            //5 ����ֻ��ȡpart,ȥ���ĵ��͹��岿������������
            //  ���صĽ�����ǵ�һ��λ��ΪArraylist�����Ľ��,�ڶ���λ�ñ��ǰ�Ľ��
            ArrayList[] list = (ArrayList[]) RequestHelper.request(
                    "PromulgateNotifyService", "obtainDataForChange",
                    new Class[]{BaseValueIfc.class},
                    new Object[]{changeRequestIfc});
            List afterChangelist = (List) list[0];
            List beforeChangelist = (List) list[1];
            //�������ݣ�
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
            //�Ƚϰ汾
            getCompareData(beforeChangelist, afterChangelist);
        }
        catch (QMException e)
        {
            //"���ӱ��֪ͨ��.htmlʧ�ܣ�"
            logger.error(Messages.getString("Util.49"), e); //$NON-NLS-1$
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("setCompareData(QMChangeRequestIfc) - end"); //$NON-NLS-1$
        }
    }

    /**
     * ��ȡ�Ƚ����ݡ�
     * �������㲿��ֻ�ܶ࣬�����٣������Ǽ��ٵ������
     * ������ж����ǰû�е��㲿�����������ġ������㲿�����ǰ�İ汾���Ӽ�����ȫ����ʾΪ�ա�
     * @param beforeChangelist ���ǰ�㲿�����ϡ�
     * @param afterChangelist ������㲿�����ϡ�
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
            //������ǰ���и��㲿����
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
            //���򣬾��Ǳ���������ġ�
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
     * ��ȡ��ʾ���ݡ�ֻ��ʾǰ���Ӽ������б仯�����ݣ�û�仯�ĳ�ȥ��
     * �ṹֻ�Ƚ�һ�㣬ͬ��������ͬ�Ӽ��Ⱥϲ�������
     * �ñ����Ľṹ�㲿���ͱ��ǰ�ıȣ�ǰ���м����Ƚϣ���û��ǰ����ṹ�Ӽ���ɾ�����������Ϊ0��
     * ǰû�к���ṹ�Ӽ������ӣ�ǰ������Ϊ0��
     * @param beforePart ���ǰ���㲿�����ϡ�
     * @param afterPart �������㲿�����ϡ�
     * @return ��ʾ���ݼ��ϡ�����Ԫ�ض���һ��String[4]���顣
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
        //�ϲ����ʹ�ýṹ�������ϡ�
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
        //���ǰ�����ڸ��㲿����
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
        //���ǰ�󶼴��ڸ��㲿����
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
     * �ϲ��ṹ��ʹ���Ӽ���������
     * @param usageLinkMap ���ϲ�������ʹ�ýṹMap��ֵΪPartUsageLinkIfc��
     * @return List �ϲ����ʹ�ýṹ�������ϡ�
     */
    private final List uniteQuantity(HashMap usageLinkMap)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("uniteQuantity(HashMap) - start"); //$NON-NLS-1$
        }
        //�ϲ����ʹ�ýṹ�������ϡ�
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
            //��ʶ�ڼ���usageLinkList���Ƿ���ڵ�ǰ��ѭ��������㲿��,��ʼ�����,��Ϊ������:
            flag = false;
            //�����еĺϲ���ϵļ��Ͻ���ѭ��:
            for (int i = 0; i < usageLinkList.size(); i++)
            {
                usageLinkIfc2 = (PartUsageLinkIfc) usageLinkList.get(i);
                float oldQuantity = usageLinkIfc2.getQuantity();
                String oldLeftBsoID = usageLinkIfc2.getLeftBsoID();
                //���ʹ�õ���ͬһ���㲿���Ļ�,�ϲ�����,
                if(leftBsoID.equals(oldLeftBsoID))
                {
                    usageLinkIfc2.setQuantity(quantity + oldQuantity);
                    //�ҵ�������㲿��:
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
     * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
     * �������HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
     * @param partIfc �㲿����
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
        //ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
        List usesPartList = new ArrayList();
        try
        {
            usesPartList = (List) RequestHelper.request("StandardPartService",
                    "getUsesPartMasters", new Class[]{QMPartIfc.class},
                    new Object[]{partIfc});
        }
        catch (QMException e)
        {
            //"��ȡ��Ϊ*���㲿���ṹʱ����"
            logger.error(Messages.getString("Util.17", new Object[]{partIfc
                    .getPartNumber()})
                    + e);
            throw new QMXMLException(e);
        }
        //��Ҫ�������ӹ����ŵ�HashMap�С�
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
     * ȥ���������õ�.0���֡�
     * @return ������������
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
     * ����HTML�ļ���
     * @param dataList ������Ҫ��ʾ�����ݵļ��ϡ�����Ԫ�ض���һ��String[4]���顣
     * @throws QMException
     */
    private final void createHtml(List dataList) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("createHtml(List) - start"); //$NON-NLS-1$
        }
        Document doc = DocumentHelper.createDocument();
        //����XML������ʽ��XML������
        OutputFormat format = OutputFormat.createPrettyPrint();
        //����XML�������͡�
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
                //ת��Ϊ������
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
     * дDTD������Ϣ��
     * @param doc XML�ĵ���
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
     * ��ȡDTD������Ϣ�б�
     * @return List DTD������Ϣ�б�
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
     * дXML�ĵ������ݡ�
     * @param doc XML�ĵ���
     * @param dataList ������Ҫ��ʾ�����ݵļ��ϡ�����Ԫ�ض���һ��String[4]���顣
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
                "yyyy-MM-dd HH:mm:ss.S z"); //�����ļ�ʱ�䡣
        elem.addElement("date").addText(simple.format(new Date()));
        elem.addElement("sourcetag").addText("");
        elem.addElement("notes").addText("");
        Element table = doc.getRootElement().addElement("table");
        table.addAttribute("name", "");
        table.addElement("col_header").addAttribute("id", "1").addAttribute(
                "type", "").addText("�㼶");
        table.addElement("col_header").addAttribute("id", "2").addAttribute(
                "type", "").addText("���");
        table.addElement("col_header").addAttribute("id", "3").addAttribute(
                "type", "").addText("���ǰ(�汾/����)");
        table.addElement("col_header").addAttribute("id", "4").addAttribute(
                "type", "").addText("�����(�汾/����)");
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
     * ����ָ����XSL�ļ���ʽ��XML�ĵ����ݡ�
     * @param document XML�ĵ���
     * @return ��ʽ�����HTML�ĵ���
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
     * ��Ŀ�����·�������ĵ��ļ���
     * @param document �ĵ���
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
        //����XML������ʽ��XML������
        OutputFormat format = OutputFormat.createPrettyPrint();
        //����XML�������͡�
        format.setEncoding(encoding);
        //��Ŀ��·��д��XML�����ļ���
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