/**
 * 生成程序QMXMLCreator.java	1.0              2006-10-26
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 文件名称出现“前准”ftp无法识别，由于解放服务器有问题引起，所以将“前准”替换为“QZ” 2016-03-21
 */
package com.faw_qm.erp.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.dtd.AttributeDecl;
import org.dom4j.dtd.ElementDecl;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultDocumentType;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: 数据文件XML生成器。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public final class QMXMLCreator
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLCreator.class);

    /**
     * XML编码类型。
     */
    private static final String encoding = "gb2312";

    /**
     * 根元素名称。
     */
    private static final String rootElementName = "bom";

    /**
     * 与XML文件配套使用的样式单的类型及文件名。
     */
    private static final String xslFileName = "OutPut_Data.xsl";

    /**
     * 
     * 创建XML文档。
     * @param publishSourseObject 采用通知书或更改采用通知书。
     * @param dataList 数据文件中的XML数据元素集合。
     * @param publishFathName 发布XML文件存放的路径。
     * @return String XML文件名称。
     * @throws IOException
     */
    public final String createDoc(final BaseValueIfc publishSourseObject,
            final List dataList,String publishFathName) throws IOException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("createDoc(String, String) - start"); //$NON-NLS-1$
        }
        Document doc = DocumentHelper.createDocument();
        //设置XML美化格式和XML声明。 
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置XML编码类型。
        format.setEncoding(encoding);
        //指定与XML文件配套使用的样式单的类型及文件名。
        doc.addProcessingInstruction("xml-stylesheet",
                "type=\"text/xsl\" href=\"" + xslFileName + "\"");
        //CCBegin by chudaming 20100809 路线和bom同步，bom在发布过后，路线、版本、状态没变得话艺准不再发布
        boolean a =false;
        for (int k = 0; k < dataList.size(); k++){
        	final QMXMLData data = (QMXMLData) dataList.get(k);
        	if(data.getRecordList().size()>0){
        		  
        		a=true;       
        	}
        }
//        System.out.println("wwwewsdcccccccccccccc========="+a);
        String xmlFileName = "";
        if(a){
        	//CCEnd  by chudaming 20100809 路线和bom同步，bom在发布过后，路线、版本、状态没变得话艺准不再发布
        writeDoc(doc, dataList);
        
        
        for (int j = 0; j < dataList.size(); j++)
        {
            final QMXMLData data = (QMXMLData) dataList.get(j);
            
            if(data instanceof QMXMLDesc)
            {
                xmlFileName = ((QMXMLDesc) data).getFilenumber() + ".xml";
            }
        }
        //String publishFathName = (String) RemoteProperty.getProperty("pathName");
        logger.debug("1111 pathName is "+publishFathName);
        if(!publishFathName.endsWith(BaseDataPublisher.endString))
        {
            publishFathName += BaseDataPublisher.endString;
        }
        logger.debug("2222 pathName is "+publishFathName);
      //CCBegin by chudaming 20101016 材料定额和bom发送的文件前面的时间变成090903
//        System.out.println("cacacacacac====================="+xmlFileName);
//        String str1="";
//        String str2="";
//        String str3="";
//        Date now = new Date(); 
//        DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
//        String str8 = d8.format(now);
////        System.out.println("sssssssssssssssssss==="+str8);
//        str1=str8.substring(0,str8.length()-6);
//        str2=str8.substring(str8.length()-5,str8.length()-3);
//        str3=str8.substring(str8.length()-2,str8.length());
////        System.out.println("sssssssssssssssssss==="+str1+"====22222222222222222=="+str2+"====3333333333333333333======="+str3);
//        str8=str1+str2+str3;
        
        Timestamp time = new Timestamp(System.currentTimeMillis());
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
    			"yyyy-MM-dd kkmmss");
    	String aa=sdf.format(time);
    	
//    	String bb=aa.substring(11, aa.length());
//    	 System.out.println("bbbbbbbbbbbbb0000====="+bb);
//    	String dd=aa.substring(0, 11);
//    	String cc="";
//    	if(bb.length()<6){
//    		 cc=dd+"0"+bb;
//    		 System.out.println("jjjjjjjjdeeeeeeeeee00000====="+cc);
//    	}else{
//    		cc=aa;
//    	}
//    	System.out.println("cccccccccczzzzzzzzzzcccccccccc===="+cc);
    	//文件名要求是日期时间+路线编号,前面不需要在加时间了 刘家坤20140224
      //  xmlFileName=aa+xmlFileName;
//        System.out.println("xmlFileNamexmlFileNamexmlFileName==========="+xmlFileName);
        //CCBegin SS1
        xmlFileName=BaseDataPublisher.replaceStr(xmlFileName);
        //CCEnd SS1
        logger.debug("2222 xmlFileName is "+xmlFileName);
      //CCEnd by chudaming 20101016 材料定额和bom发送的文件前面的时间变成090903
        File path = new File(publishFathName);
        if(!path.exists())
        {
            path.mkdir();
        }
        //如果目标路径没有OutPut_Data.xsl，则复制一个过去。
      //  System.out.println("sssssssssssssssssssxslFileName==="+xslFileName);
        File xslFile = new File(getClass().getResource(xslFileName).getFile());
//        System.out.println("sssssssssssssssssss2222222222222222222222222222222222==="+xslFile);
        File oxslFile = new File(publishFathName + xslFileName);
//        System.out.println("ssssssssssssssssss333333333333333333333s==="+oxslFile);
//        System.out.println("ssssssssssssssssss333333333333333333333s==="+(xslFile.exists() && !oxslFile.exists()));
        if(xslFile.exists() && !oxslFile.exists())
        {
            

            BufferedReader in = new BufferedReader(new FileReader(xslFile));
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new FileWriter(oxslFile)));
//            System.out.println("dddddddddddddddddddddddddddddddddd===");
            String linetemp = "";
            while ((linetemp = in.readLine()) != null)
                out.println(linetemp);
//            System.out.println("dddddddddddddddddddddddddddddddddd=111111111111111111111111111==");
            out.flush();
            out.close();
        }
       // System.out.println("ddddddddddddddddddddddddd222222222222222222222222222222222222xmlFileName==="+xmlFileName);
        File file=new File(publishFathName + xmlFileName);
//        System.out.println("3333333333344444444wwwwwwwwwwwwwwwwwwww==="+(!file.exists()));
        if(!file.exists()){
        	//向目标路径写入XML数据文件。
            XMLWriter writer = new XMLWriter(
                    new FileWriter(publishFathName + xmlFileName), format);
            writer.write(doc);
            writer.flush();
            writer.close();
        }
        else{
        	
        }

        if(logger.isDebugEnabled())
        {
            logger.debug("createDoc(String, String) - end" + xmlFileName); //$NON-NLS-1$
        }
      //  System.out.println("cacacaczzzzzzzzzzzzzzzzzzzzzzzzzzacac====================="+xmlFileName);
       }
        return xmlFileName;
        
    }

    /**
     * 正式开始写XML。
     * @param doc XML文档。
     * @param dataList 数据文件中的XML数据元素集合。
     */
    private final void writeDoc(Document doc, final List dataList)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("startDoc(Document) - start"); //$NON-NLS-1$
        }
        writeDTDInfo(doc);
        writeDocContent(doc, dataList);
        if(logger.isDebugEnabled())
        {
            logger.debug("startDoc(Document) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 写文档元素内容。
     * @param doc XML文档。
     * @param dataList 数据文件中的XML数据元素集合。
     */
    private final void writeDocContent(Document doc, final List dataList)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("writeDocContent(Document, List) - start"); //$NON-NLS-1$
        }
        doc.addElement(rootElementName);
        
        for (int i = 0; i < dataList.size(); i++)
        {
        	
            final QMXMLData data = (QMXMLData) dataList.get(i);
//            System.out.println("recordListrecordListrecordList0000000000========="+data.getRecordList());
//            System.out.println("recordListrecordListrecordList11111111111111111========="+data.getRecordList().size());
            if(data instanceof QMXMLDesc)
            {
            	
                Element elem = doc.getRootElement().addElement(data.getName());
                elem.addElement("filenumber").addText(
                        ((QMXMLDesc) data).getFilenumber());
                elem.addElement("type").addText(((QMXMLDesc) data).getType());
                elem.addElement("date").addText(((QMXMLDesc) data).getDate());
                elem.addElement("sourcetag").addText(
                        ((QMXMLDesc) data).getSourcetag());
                elem.addElement("notes").addText(((QMXMLDesc) data).getNotes());
            }
            else if(data.getRecordList() != null)
            {
                if(data.getRecordList().size() > 0)
                {
                    Element table = doc.getRootElement().addElement("table");
                    table.addAttribute("name", data.getName());
                    List propertyList = data.getPropertyList();
                    QMXMLProperty property = null;
                    for (int j = 0; j < propertyList.size(); j++)
                    {
                        property = (QMXMLProperty) propertyList.get(j);
                        table.addElement("col_header").addAttribute("id",
                                property.getId()).addAttribute("type",
                                property.getType())
                                .addText(property.getValue());
                    }
                    final List recordList = data.getRecordList();
//                    System.out.println("recordListrecordListrecordList========="+recordList);
                    QMXMLRecord record = null;
                    QMXMLColumn col = null;
                    for (int k = 0; k < recordList.size(); k++)
                    {
                        Element recordElem = table.addElement("record");
                        record = (QMXMLRecord) recordList.get(k);
                        for (int m = 0; m < record.getColList().size(); m++)
                        {
                            col = (QMXMLColumn) record.getColList().get(m);
                            if(col.getValue() == null)
                            {
                                recordElem.addElement("col").addAttribute("id",
                                        col.getId()).addText("");
                            }
                            else
                            {
                                recordElem.addElement("col").addAttribute("id",
                                        col.getId()).addText(col.getValue());
                            }
                        }
                    }
                   
                }
                //"共发布*条名称为*的数据！"
                logger.fatal(Messages.getString("Util.34", new Object[]{
                        Integer.toString(data.getRecordList().size()),
                        data.getName()}));
            }
        
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("writeDocContent2(Document, List) - end"); //$NON-NLS-1$
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
        docType.setElementName(rootElementName);
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
            logger.debug("setInternalDeclarations() - start"); //$NON-NLS-1$
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
            logger.debug("setInternalDeclarations() - end"); //$NON-NLS-1$
        }
        return decls;
    }
}
