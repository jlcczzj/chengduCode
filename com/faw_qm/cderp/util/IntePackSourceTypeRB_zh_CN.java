/**
 * ���ɳ���QMCompositiveTypeRB_zh_CN.java	1.0              2007-9-24
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.util;

import java.util.ListResourceBundle;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class IntePackSourceTypeRB_zh_CN extends ListResourceBundle
{
    public IntePackSourceTypeRB_zh_CN()
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
        		{"com.faw_qm.cderp.QMCompositiveType.PROMULGATENOTIFY",
                "promulgateNotify,����֪ͨ��,����֪ͨ��״̬,10,true,true"}
        		,
                {"com.faw_qm.cderp.QMCompositiveType.ADOPTNOTICE",
                "adoptNotice,���Ĳ���֪ͨ��,���Ĳ���֪ͨ��״̬,20,false,true"}
        		,
                {"com.faw_qm.cderp.QMCompositiveType.BASELINE",
                "baseLine,����,����״̬,30,false,true"}
        		,
        		{"com.faw_qm.cderp.QMCompositiveType.technicsRouteList",
                "technicsRouteList,·��,·��״̬,40,false,true"}});
    }
}
