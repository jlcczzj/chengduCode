/**
 * ���ɳ���QMCompositiveTypeRB_en_US.java	1.0              2007-9-24
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.util;

import java.util.ListResourceBundle;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class IntePackSourceTypeRB_en_US extends ListResourceBundle
{
    public IntePackSourceTypeRB_en_US()
    {
    }

    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][];
    static
    {
        contents = (new Object[][]{
        		{"com.faw_qm.jferp.QMCompositiveType.PROMULGATENOTIFY",
                "promulgateNotify,����֪ͨ��,����֪ͨ��״̬,10,true,true"}
        		,
                {"com.faw_qm.jferp.QMCompositiveType.ADOPTNOTICE",
                "adoptNotice,���Ĳ���֪ͨ��,���Ĳ���֪ͨ��״̬,20,false,true"}
        		,
                {"com.faw_qm.jferp.QMCompositiveType.BASELINE",
                "baseLine,����,����״̬,30,false,true"}
        		,
        		{"com.faw_qm.jferp.QMCompositiveType.technicsRouteList",
                "technicsRouteList,·��,·��״̬,40,false,true"}});
    }
}
