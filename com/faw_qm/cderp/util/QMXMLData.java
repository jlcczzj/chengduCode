/**
 * ���ɳ���QMXMLData.java	1.0              2006-10-27
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: ���������ļ��еġ�data��XMLԪ�أ�ͬʱ��Ӧ�����ļ��еġ�table��Ԫ�ء�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
 * @version 1.0
 */
public class QMXMLData
{
    private String name = "";

    private String displayname = "";

    private String classname = "";

    private String publisher = "";

    private List propertyList = new ArrayList();

    private List recordList = new ArrayList();

    public QMXMLData()
    {
        super();
    }

    /**
     * @return ���� displayname��
     */
    public final String getDisplayname()
    {
        return displayname;
    }

    /**
     * @param displayname Ҫ���õ� displayname��
     */
    public final void setDisplayname(final String displayname)
    {
        this.displayname = displayname;
    }

    /**
     * @return ���� name��
     */
    public final String getName()
    {
        return name;
    }

    /**
     * @param name Ҫ���õ� name��
     */
    public final void setName(final String name)
    {
        this.name = name;
    }

    /**
     * @return ���� classname��
     */
    public final String getClassname()
    {
        return classname;
    }

    /**
     * @param classname Ҫ���õ� classname��
     */
    public final void setClassname(final String classname)
    {
        this.classname = classname;
    }

    public final void addProperty(final QMXMLProperty property)
    {
        propertyList.add(property);
    }

    /**
     * @return ���� propertyList��
     */
    public final List getPropertyList()
    {
        return propertyList;
    }

    /**
     * @param propertyList Ҫ���õ� propertyList��
     */
    public final void setPropertyList(final List propertyList)
    {
        this.propertyList = propertyList;
    }

    public final void addRecord(final QMXMLRecord record)
    {
        this.recordList.add(record);
    }

    /**
     * @return ���� recordList��
     */
    public final List getRecordList()
    {
        return recordList;
    }

    /**
     * @param recordList Ҫ���õ� recordList��
     */
    public final void setRecordList(final List recordList)
    {
        this.recordList = recordList;
    }

    /* ���� Javadoc��
     * @see java.lang.Object#toString()
     */
    public final String toString()
    {
        return "\ndata's content:" + "  name==" + getName() + "  displayname=="
                + getDisplayname() + "  classname==" + getClassname()
                + "  publisher==" + getPublisher() + "\npropertyList=="
                + getPropertyList() + "\nredordList==" + getRecordList();
    }

    /**
     * @return ���� publisher��
     */
    public final String getPublisher()
    {
        return publisher;
    }

    /**
     * @param publisher Ҫ���õ� publisher��
     */
    public final void setPublisher(final String publisher)
    {
        this.publisher = publisher;
    }
}
