/**
 * ���ɳ���QMXMLParser.java	1.0              2006-11-6
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ���������ļ���XML��������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
 * @version 1.0
 */
public final class QMXMLParser
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLParser.class);

    /**
     * ����XML�ļ������ݡ�
     * @param propertyFileName �����ļ����ơ���publish_data.xml����
     * @param dataList ֻ����˵����Ϣ�ķ����ļ���������QMXMLData���ϡ�
     * return List �����������Ϣ�ķ����ļ���������QMXMLData���ϡ�
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
            //"��ȡ��Ϊ*���ļ�ʧ�ܣ�"
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
     * ��ʼ���������ļ���
     * @param doc ���������ĵ���publish_data.xml��
     * @param dataList ֻ����˵����Ϣ�ķ����ļ���������QMXMLData���ϡ�
     * @return List �����ļ���������QMXMLData���ϡ�
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
            //ͨ�������ļ�Ԫ�ص�classnameʵ���������data����
            try
            {
                data = (QMXMLData) Class.forName(
                        dataElement.attributeValue("classname")).newInstance();
            }
            catch (InstantiationException e)
            {
                logger
                        .error(
                                "ʵ������Ϊ" + dataElement.attributeValue("classname") + "ʱ����", e); //$NON-NLS-1$
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
                                "������Ϊ"  + dataElement.attributeValue("classname") + "ʱ����", e); //$NON-NLS-1$
                if(logger.isDebugEnabled())
                {
                    logger.debug("startParse(String) - end"); //$NON-NLS-1$
                }
                throw new QMXMLException(e);
            }
            catch (ClassNotFoundException e)
            {
                //"��Ϊ*���಻���ڣ�"
                logger.error(Messages.getString("Util.6",
                        new Object[]{dataElement.attributeValue("classname")}),
                        e);
                throw new QMXMLException(e);
            }
            //����data�����ԡ�
            data.setName(dataElement.attributeValue("name"));
            data.setDisplayname(dataElement.attributeValue("displayname"));
            data.setClassname(dataElement.attributeValue("classname"));
            data.setPublisher(dataElement.attributeValue("publisher"));
            final List propertyList = dataElement.elements();
            //����property�����ԡ�
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
