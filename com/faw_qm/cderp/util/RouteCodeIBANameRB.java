/**
 * ���ɳ���RouteCodeIBANameRB.java	1.0              2007-11-27
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
public class RouteCodeIBANameRB  extends ListResourceBundle
{
    public RouteCodeIBANameRB()
    {
    }

    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][];
    static
    {
        contents = (new Object[][]
        {
            {
                 "com.faw_qm.cderp.util.SX", "SX,ˮ�䳵���������,ˮ�䳵��,10,true,true"
            },
            {
                 "com.faw_qm.cderp.util.LQ", "LQ,�����������������,����������,20,false,true"
            },
            {
                 "com.faw_qm.cderp.util.CY", "CY,��ѹ�����������,��ѹ����,30,false,true"
            },
            {
                "com.faw_qm.cderp.util.DH", "DH,�ƺ������������,�ƺ�����,40,false,true"
            },
            {
                "com.faw_qm.cderp.util.SZ", "SZ,��װ���쳵���������,��װ���쳵��,5,false,true"
            }
            ,
            {
                "com.faw_qm.cderp.util.SR", "SR,ɢ�����ֹ�˾��������,ɢ�����ֹ�˾,6,false,true"
            }
        });
    }
}
