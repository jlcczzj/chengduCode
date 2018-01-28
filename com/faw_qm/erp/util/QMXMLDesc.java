/**
 * 生成程序QMXMLDesc.java	1.0              2006-10-31
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.util;

/**
 * <p>Title: 数据文件中的“description”XML元素。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public final class QMXMLDesc extends QMXMLData
{
    private String filenumber = "";

    private String type = "";

    private String date = "";

    private String sourcetag = "";

    private String notes = "";

    public QMXMLDesc()
    {
        super();
        setName("description");
    }

    /**
     * @return 返回 date。
     */
    public final String getDate()
    {
        return date;
    }

    /**
     * @param date 要设置的 date。
     */
    public final void setDate(final String date)
    {
        this.date = date;
    }

    /**
     * @return 返回 filenumber。
     */
    public final String getFilenumber()
    {
        return filenumber;
    }

    /**
     * @param filenumber 要设置的 filenumber。
     */
    public final void setFileNumber(final String filenumber)
    {
        this.filenumber = filenumber;
    }

    /**
     * @return 返回 notes。
     */
    public final String getNotes()
    {
        return notes;
    }

    /**
     * @param notes 要设置的 notes。
     */
    public final void setNotes(final String notes)
    {
        this.notes = notes;
    }

    /**
     * @return 返回 sourcetag。
     */
    public final String getSourcetag()
    {
        return sourcetag;
    }

    /**
     * @param sourcetag 要设置的 sourcetag。
     */
    public final void setSourcetag(final String sourcetag)
    {
        this.sourcetag = sourcetag;
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
}
