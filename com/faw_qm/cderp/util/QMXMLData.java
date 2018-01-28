/**
 * 生成程序QMXMLData.java	1.0              2006-10-27
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.util;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: 属性配置文件中的“data”XML元素，同时对应数据文件中的“table”元素。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
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
     * @return 返回 displayname。
     */
    public final String getDisplayname()
    {
        return displayname;
    }

    /**
     * @param displayname 要设置的 displayname。
     */
    public final void setDisplayname(final String displayname)
    {
        this.displayname = displayname;
    }

    /**
     * @return 返回 name。
     */
    public final String getName()
    {
        return name;
    }

    /**
     * @param name 要设置的 name。
     */
    public final void setName(final String name)
    {
        this.name = name;
    }

    /**
     * @return 返回 classname。
     */
    public final String getClassname()
    {
        return classname;
    }

    /**
     * @param classname 要设置的 classname。
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
     * @return 返回 propertyList。
     */
    public final List getPropertyList()
    {
        return propertyList;
    }

    /**
     * @param propertyList 要设置的 propertyList。
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
     * @return 返回 recordList。
     */
    public final List getRecordList()
    {
        return recordList;
    }

    /**
     * @param recordList 要设置的 recordList。
     */
    public final void setRecordList(final List recordList)
    {
        this.recordList = recordList;
    }

    /* （非 Javadoc）
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
     * @return 返回 publisher。
     */
    public final String getPublisher()
    {
        return publisher;
    }

    /**
     * @param publisher 要设置的 publisher。
     */
    public final void setPublisher(final String publisher)
    {
        this.publisher = publisher;
    }
}
