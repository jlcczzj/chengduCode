/**
 * ���ɳ���QMXMLColumn.java	1.0              2006-11-6
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.util;

/**
 * <p>Title: �����ļ��еġ�col��XMLԪ�ء�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
 * @version 1.0
 */
public final class QMXMLColumn
{
    private String id;

    private String value;

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
        return "col's content:" + "  id==" + getId() + "  value==" + getValue();
    }
}
