/**
 * ���ɳ���QMXMLRecord.java	1.0              2006-10-31
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.util;

import java.util.List;

/**
 * <p>Title: �����ļ��еġ�record��XMLԪ�ء�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
 * @version 1.0
 */
public final class QMXMLRecord
{
    private List colList;

    /**
     * @return ���� colList��
     */
    public final List getColList()
    {
        return colList;
    }

    /**
     * @param colList Ҫ���õ� colList��
     */
    public final void setColList(final List colList)
    {
        this.colList = colList;
    }

    /* ���� Javadoc��
     * @see java.lang.Object#toString()
     */
    public final String toString()
    {
        // TODO �Զ����ɷ������
        return "record's content:" + "  colList==" + getColList();
    }
}
