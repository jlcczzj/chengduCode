/**
 * ���ɳ���PromulgateNotifyServiceHome.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.ejb.service;

import javax.ejb.CreateException;
import com.faw_qm.framework.service.BaseServiceHome;

/**
 * <p>Title: ����֪ͨ���ط���ӿ�</p>
 * <p>Description: ����֪ͨ����ӿ�</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public interface PromulgateNotifyServiceHome extends BaseServiceHome
{
    public PromulgateNotifyService create() throws CreateException;
}
