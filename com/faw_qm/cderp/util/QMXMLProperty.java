/**
 * ���ɳ���QMXMLProperty.java	1.0              2006-10-27
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.util;

/**
 * <p>Title: ���������ļ��еġ�property��XMLԪ�ء�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
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
     * @return ���� id��
     */
    public final String getId()
    {
        return id;
    }

    /**
     * @param id Ҫ���õ� id��
     */
    public final void setId(final String id)
    {
        this.id = id;
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

    /**
     * @return ���� value��
     */
    public final String getValue()
    {
        return value;
    }

    /**
     * @param value Ҫ���õ� value��
     */
    public final void setValue(final String value)
    {
        this.value = value;
    }

    /* ���� Javadoc��
     * @see java.lang.Object#toString()
     */
    public final String toString()
    {
        return "prperty's content:" + "  name==" + getName() + "  type=="
                + getType() + "  displayname==" + getDisplayname()
                + "  value==" + getValue() + "  id==" + getId();
    }
}
