/**
 * 生成程序QMXMLColumn.java	1.0              2006-11-6
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.util;

/**
 * <p>Title: 数据文件中的“col”XML元素。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public final class QMXMLColumn
{
    private String id;

    private String value;

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
        return "col's content:" + "  id==" + getId() + "  value==" + getValue();
    }
}
