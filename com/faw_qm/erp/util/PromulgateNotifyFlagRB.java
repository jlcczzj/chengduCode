/**
 * ���ɳ���PromulgateNotifyFlagRB.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.util;

import java.util.ListResourceBundle;

/**
 * ���ñ�ʶ��Դ�ļ�
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class PromulgateNotifyFlagRB extends ListResourceBundle
{
    public PromulgateNotifyFlagRB()
    {
    }

    static final Object[][] contents = {
            {"SHIZHI", "SHIZHI,����,����,1,true,true"},
            {"SHENGCHANZHUNBEI", "SHENGCHANZHUNBEI,����׼��,����׼��,10,false,true"},
            {"SHENGCHAN", "SHENGCHAN,����,����,20,false,true"}
            };

    public Object[][] getContents()
    {
        return contents;
    }
}