/**
 * 生成程序QMXMLProperty.java	1.0              2006-10-27
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.util;

/**
 * <p>Title: 属性配置文件中的“property”XML元素。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public final class QMXMLProperty
{
    private String name = "";

    private String type = "";

    private String displayname = "";

    private String value = "";

    private String id = "";

    /**
     * @return 返回 id。
     */
    public final String getId()
    {
        return id;
    }

    /**
     * @param id 要设置的 id。
     */
    public final void setId(final String id)
    {
        this.id = id;
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
     * @return 返回 type。
     */
    public final String getType()
    {
        return type;
    }

    /**
     * @param type 要设置的 type。
     */
    public final void setType(final String type)
    {
        this.type = type;
    }

    /**
     * @return 返回 value。
     */
    public final String getValue()
    {
        return value;
    }

    /**
     * @param value 要设置的 value。
     */
    public final void setValue(final String value)
    {
        this.value = value;
    }

    /* （非 Javadoc）
     * @see java.lang.Object#toString()
     */
    public final String toString()
    {
        return "prperty's content:" + "  name==" + getName() + "  type=="
                + getType() + "  displayname==" + getDisplayname()
                + "  value==" + getValue() + "  id==" + getId();
    }
}
