/* ���ɳ���TechnicDataServiceHome.java	1.0              2007-10-31
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.erp.ejb.service;

import com.faw_qm.framework.service.BaseServiceHome;
import javax.ejb.CreateException;

/**
 * <p>Title: �������ݷ��񱾵ؽӿڡ�</p>
 * <p>Description: �������ݷ��񱾵ؽӿ�</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author �촺Ӣ
 * @version 1.0
 */

public interface TechnicDataServiceHome extends BaseServiceHome
{
   public TechnicDataService create() throws CreateException;
}
