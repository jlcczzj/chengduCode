/**
 * 生成程序QMXMLParser.java	1.0              2006-11-6
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.util;

import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.faw_qm.cderp.exception.QMXMLException;

/**
 * <p>Title: 属性配置文件的XML解析器。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public final class QMXMLParser
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLParser.class);

    /**
     * 解析XML文件的内容。
     * @param propertyFileName 属性文件名称。“publish_data.xml”。
     * @param dataList 只包括说明信息的发布文件数据内容QMXMLData集合。
     * return List 添加了其它信息的发布文件数据内容QMXMLData集合。
     * @throws QMXMLException 
     */
    public static final List parse(final String propertyFileName, List dataList)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("parse(String) - start"); //$NON-NLS-1$
        }
        final SAXReader reader = new SAXReader();
        Document doc = null;
        try
        {
            logger.debug("propertyFileName==" + propertyFileName);
            File propertyFile = new File(QMXMLParser.class.getResource(
                    propertyFileName).getFile());
            logger.debug("propertyFile==" + propertyFile);
            doc = reader.read(propertyFile);
            logger.debug("doc==" + doc);
        }
        catch (DocumentException e)
        {
            //"读取名为*的文件失败！"
            logger.error(Messages.getString("Util.5",
                    new Object[]{propertyFileName}));
            throw new QMXMLException(e);
        }
        if(doc != null)
        {
            dataList = startParse(doc, dataList);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("parse(String) - end"); //$NON-NLS-1$
        }
        return dataList;
    }

    /**     
     * 开始解析属性文件。
     * @param doc 属性配置文档。publish_data.xml。
     * @param dataList 只包括说明信息的发布文件数据内容QMXMLData集合。
     * @return List 发布文件数据内容QMXMLData集合。
     * @throws QMXMLException 
     */
    private static final List startParse(Document doc, final List dataList)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("startParse(String) - start");
        }
        final List dataElementList = doc.getRootElement().elements();
        for (int i = 0; i < dataElementList.size(); i++)
        {
            QMXMLData data = null;
            final Element dataElement = (Element) dataElementList.get(i);
            //通过配置文件元素的classname实例化具体的data对象。
            try
            {
                data = (QMXMLData) Class.forName(
                        dataElement.attributeValue("classname")).newInstance();
            }
            catch (InstantiationException e)
            {
                logger
                        .error(
                                "实例化名为" + dataElement.attributeValue("classname") + "时出错！", e); //$NON-NLS-1$
                if(logger.isDebugEnabled())
                {
                    logger.debug("startParse(String) - end"); //$NON-NLS-1$
                }
                throw new QMXMLException(e);
            }
            catch (IllegalAccessException e)
            {
                logger
                        .error(
                                "访问名为"  + dataElement.attributeValue("classname") + "时出错！", e); //$NON-NLS-1$
                if(logger.isDebugEnabled())
                {
                    logger.debug("startParse(String) - end"); //$NON-NLS-1$
                }
                throw new QMXMLException(e);
            }
            catch (ClassNotFoundException e)
            {
                //"名为*的类不存在！"
                logger.error(Messages.getString("Util.6",
                        new Object[]{dataElement.attributeValue("classname")}),
                        e);
                throw new QMXMLException(e);
            }
            //设置data的属性。
            data.setName(dataElement.attributeValue("name"));
            data.setDisplayname(dataElement.attributeValue("displayname"));
            data.setClassname(dataElement.attributeValue("classname"));
            data.setPublisher(dataElement.attributeValue("publisher"));
            final List propertyList = dataElement.elements();
            //设置property的属性。
            for (int j = 0; j < propertyList.size(); j++)
            {
                final QMXMLProperty property = new QMXMLProperty();
                property.setId(((Element) propertyList.get(j))
                        .attributeValue("id"));
                property.setName(((Element) propertyList.get(j))
                        .attributeValue("name"));
                property.setType(((Element) propertyList.get(j))
                        .attributeValue("type"));
                property.setDisplayname(((Element) propertyList.get(j))
                        .attributeValue("displayname"));
                property.setValue(((Element) propertyList.get(j)).getText());
                data.addProperty(property);
            }
            dataList.add(data);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("startParse(String) - end");
        }
        return dataList;
    }
}
