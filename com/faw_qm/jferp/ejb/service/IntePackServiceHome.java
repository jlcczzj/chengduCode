/**
 * ���ɳ���IntePackServiceHome.java	1.0              2007-9-25
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
 * <p>Title: ���ɰ����ط���ӿڡ�</p>
 * <p>Description: ���ɰ����ط���ӿ�</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public interface IntePackServiceHome extends BaseServiceHome
{
    public IntePackService create() throws CreateException;
}
