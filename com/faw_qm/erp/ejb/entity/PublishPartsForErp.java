/**
 * ���ɳ���MaterialStructure.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: ���Ͻṹ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public interface PublishPartsForErp extends BsoReference
{
    /**
     * ���ø����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @param parentPartNumber �����š�
     */
	void setPartBsoId(String partBsoid);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
	String getPartBsoId();
    void setPartState(String partState);

  

    /**
     * ��ȡ�����汾�������ϲ��ǰ����汾�����ڼ��ṹ�Ƿ���ġ�
     * @return �����汾��
     */
    String getPartState();

    /**
     * ���������ϣ���¼��ֺ����������š�
     * @param childNumber �����ϡ�
     */
    void setZhunBei(String zhunBei);

    /**
     * ��ȡ�����ϣ���¼��ֺ����������š�
     * @return �����ϡ�
     */
    String getZhunBei();
    void setZhunBei1(int zhunBei1);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    int getZhunBei1();

}