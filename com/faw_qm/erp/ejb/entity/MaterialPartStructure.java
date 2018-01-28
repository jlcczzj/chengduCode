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
public interface MaterialPartStructure extends BsoReference
{
    /**
     * ���ø����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @param parentPartNumber �����š�
     */
    void setParentPartNumber(String parentPartNumber);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    String getParentPartNumber();
    void setMaterialStructureType(String materialStructureType);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    String getMaterialStructureType();


    /**
     * ���ø����汾�������ϲ��ǰ����汾�����ڼ��ṹ�Ƿ���ġ�
     * @param parentPartVersion �����汾��
     */
    void setParentPartVersion(String parentPartVersion);

    /**
     * ��ȡ�����汾�������ϲ��ǰ����汾�����ڼ��ṹ�Ƿ���ġ�
     * @return �����汾��
     */
    String getParentPartVersion();

    /**
     * ���ø����ϣ���¼��ֺ�����ϸ���š�
     * @param parentNumber �����ϡ�
     */
    void setParentNumber(String parentNumber);

    /**
     * ��ȡ�����ϣ���¼��ֺ�����ϸ���š�
     * @return �����ϡ�
     */
    String getParentNumber();

    /**
     * ���������ϣ���¼��ֺ����������š�
     * @param childNumber �����ϡ�
     */
    void setChildNumber(String childNumber);

    /**
     * ��ȡ�����ϣ���¼��ֺ����������š�
     * @return �����ϡ�
     */
    String getChildNumber();

    /**
     * �����������������ڸ����е�ʹ�������������ʹ�ù�ϵ�е����������ϲ������Ϊ��1����
     * @param quantity ������
     */
    void setQuantity(float quantity);

    /**
     * ��ȡ�������������ڸ����е�ʹ�������������ʹ�ù�ϵ�е����������ϲ������Ϊ��1����
     * @return ������
     */
    float getQuantity();

    /**
     * ���ò㼶��������Ϊ���ϵĲ㼶�ţ���0��ʼ��
     * @param levelNumber �㼶��
     */
    void setLevelNumber(String levelNumber);

    /**
     * ��ȡ�㼶��������Ϊ���ϵĲ㼶�ţ���0��ʼ��
     * @return �㼶��
     */
    String getLevelNumber();

    /**
     * ����ʹ�õ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס�����
     * @param defaultUnit ʹ�õ�λ��
     */
    void setDefaultUnit(String defaultUnit);

    /**
     * ��ȡʹ�õ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס�����
     * @return ʹ�õ�λ��
     */
    String getDefaultUnit();

    /**
     * ����ѡװ��ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @param flag ѡװ��ʶ��
     */
    void setOptionFlag(boolean optionFlag);

    /**
     * ��ȡѡװ��ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @return ѡװ��ʶ��
     */
    boolean getOptionFlag();
}
