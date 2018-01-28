/**
 * ���ɳ���QMXMLDesc.java	1.0              2006-10-31
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.util;

/**
 * <p>Title: �����ļ��еġ�description��XMLԪ�ء�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
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
     * @return ���� date��
     */
    public final String getDate()
    {
        return date;
    }

    /**
     * @param date Ҫ���õ� date��
     */
    public final void setDate(final String date)
    {
        this.date = date;
    }

    /**
     * @return ���� filenumber��
     */
    public final String getFilenumber()
    {
        return filenumber;
    }

    /**
     * @param filenumber Ҫ���õ� filenumber��
     */
    public final void setFileNumber(final String filenumber)
    {
        this.filenumber = filenumber;
    }

    /**
     * @return ���� notes��
     */
    public final String getNotes()
    {
        return notes;
    }

    /**
     * @param notes Ҫ���õ� notes��
     */
    public final void setNotes(final String notes)
    {
        this.notes = notes;
    }

    /**
     * @return ���� sourcetag��
     */
    public final String getSourcetag()
    {
        return sourcetag;
    }

    /**
     * @param sourcetag Ҫ���õ� sourcetag��
     */
    public final void setSourcetag(final String sourcetag)
    {
        this.sourcetag = sourcetag;
    }

    /**
     * @return ���� type��
     */
    public final String getType()
    {
        return type;
    }

    /**
     * @param type Ҫ���õ� type��
     */
    public final void setType(final String type)
    {
        this.type = type;
    }
}
