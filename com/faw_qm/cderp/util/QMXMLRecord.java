/**
 * 生成程序QMXMLRecord.java	1.0              2006-10-31
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.util;

import java.util.List;

/**
 * <p>Title: 数据文件中的“record”XML元素。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public final class QMXMLRecord
{
    private List colList;

    /**
     * @return 返回 colList。
     */
    public final List getColList()
    {
        return colList;
    }

    /**
     * @param colList 要设置的 colList。
     */
    public final void setColList(final List colList)
    {
        this.colList = colList;
    }

    /* （非 Javadoc）
     * @see java.lang.Object#toString()
     */
    public final String toString()
    {
        // TODO 自动生成方法存根
        return "record's content:" + "  colList==" + getColList();
    }
}
