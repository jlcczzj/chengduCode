/**
 * ���ɳ���InterimMaterialStructureEJB.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.jferp.model.InterimMaterialStructureInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public abstract class InterimMaterialStructureEJB extends BsoReferenceEJB
{
    /**
     * ���ø����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @param parentPartNumber �����š�
     */
    public abstract void setParentPartNumber(String parentPartNumber);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    public abstract String getParentPartNumber();

    /**
     * ���ø����汾�������ϲ��ǰ����汾�����ڼ��ṹ�Ƿ���ġ�
     * @param parentPartVersion �����汾��
     */
    public abstract void setParentPartVersion(String parentPartVersion);

    /**
     * ��ȡ�����汾�������ϲ��ǰ����汾�����ڼ��ṹ�Ƿ���ġ�
     * @return �����汾��
     */
    public abstract String getParentPartVersion();

    /**
     * ���ø����ϣ���¼��ֺ�����ϸ���š�
     * @param parentNumber �����ϡ�
     */
    public abstract void setParentNumber(String parentNumber);

    /**
     * ��ȡ�����ϣ���¼��ֺ�����ϸ���š�
     * @return �����ϡ�
     */
    public abstract String getParentNumber();

    /**
     * ���������ϣ���¼��ֺ����������š�
     * @param childNumber �����ϡ�
     */
    public abstract void setChildNumber(String childNumber);

    /**
     * ��ȡ�����ϣ���¼��ֺ����������š�
     * @return �����ϡ�
     */
    public abstract String getChildNumber();

    /**
     * �����������������ڸ����е�ʹ�������������ʹ�ù�ϵ�е����������ϲ������Ϊ��1����
     * @param quantity ������
     */
    public abstract void setQuantity(float quantity);

    /**
     * ��ȡ�������������ڸ����е�ʹ�������������ʹ�ù�ϵ�е����������ϲ������Ϊ��1����
     * @return ������
     */
    public abstract float getQuantity();

    /**
     * ���ò㼶��������Ϊ���ϵĲ㼶�ţ���0��ʼ��
     * @param levelNumber �㼶��
     */
    public abstract void setLevelNumber(String levelNumber);

    /**
     * ��ȡ�㼶��������Ϊ���ϵĲ㼶�ţ���0��ʼ��
     * @return �㼶��
     */
    public abstract String getLevelNumber();    

    /**
     * ����ʹ�õ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס�����
     * @param defaultUnit ʹ�õ�λ��
     */
    public abstract void setDefaultUnit(String defaultUnit);

    /**
     * ��ȡʹ�õ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס�����
     * @return ʹ�õ�λ��
     */
    public abstract String getDefaultUnit();

    /**
     * ����ѡװ��ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @param flag ѡװ��ʶ��
     */
    public abstract void setOptionFlag(boolean optionFlag);

    /**
     * ��ȡѡװ��ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @return ѡװ��ʶ��
     */
    public abstract boolean getOptionFlag();
    
    /**
     * ����Դ���Ͻṹ��bsoID��
     */
    public abstract void setSourceBsoID(String sourceBsoID);
    
    /**
     * ��ȡԴ���Ͻṹ��bsoID��
     * @return Դ���Ͻṹ��bsoID��
     */
    public abstract String getSourceBsoID();
    
    /**
     * ���ø��±�ʶ��
     */
    public abstract void setUpdateFlag(String updateFlag);
    
    /**
     * ���ø����ߡ�
     */
    public abstract void setOwner(String userID);
    
    /**
     * ��ȡ�����ߡ�
     * @return �����ߡ�
     */
    public abstract String getOwner();
    
    /**
     * ��ȡ���±�ʶ��
     * @return ���±�ʶ��
     */
    public abstract String getUpdateFlag();
    
    public BaseValueIfc getValueInfo() throws QMException
    {
        InterimMaterialStructureInfo info = new InterimMaterialStructureInfo();
        setValueInfo(info);
        return info;
    }

    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        InterimMaterialStructureInfo info1 = (InterimMaterialStructureInfo) info;
        info1.setParentPartNumber(getParentPartNumber());
        info1.setParentPartVersion(getParentPartVersion());
        info1.setParentNumber(getParentNumber());
        info1.setChildNumber(getChildNumber());
        info1.setQuantity(getQuantity());
        info1.setLevelNumber(getLevelNumber());
        info1.setDefaultUnit(getDefaultUnit());
        info1.setOptionFlag(getOptionFlag());
        info1.setSourceBsoID(getSourceBsoID());
        info1.setUpdateFlag(getUpdateFlag());
        info1.setOwner(getOwner());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        InterimMaterialStructureInfo info1 = (InterimMaterialStructureInfo) info;
        setParentPartNumber(info1.getParentPartNumber());
        setParentPartVersion(info1.getParentPartVersion());
        setParentNumber(info1.getParentNumber());
        setChildNumber(info1.getChildNumber());
        setQuantity(info1.getQuantity());
        setLevelNumber(info1.getLevelNumber());
        setDefaultUnit(info1.getDefaultUnit());
        setOptionFlag(info1.getOptionFlag());
        setSourceBsoID(info1.getSourceBsoID());
        setUpdateFlag(info1.getUpdateFlag());
        setOwner(info1.getOwner());
    }

    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        InterimMaterialStructureInfo info1 = (InterimMaterialStructureInfo) info;
        setParentPartNumber(info1.getParentPartNumber());
        setParentPartVersion(info1.getParentPartVersion());
        setParentNumber(info1.getParentNumber());
        setChildNumber(info1.getChildNumber());
        setQuantity(info1.getQuantity());
        setLevelNumber(info1.getLevelNumber());
        setDefaultUnit(info1.getDefaultUnit());
        setOptionFlag(info1.getOptionFlag());
        setSourceBsoID(info1.getSourceBsoID());
        setUpdateFlag(info1.getUpdateFlag());
        setOwner(info1.getOwner());
    }

    public String getBsoName()
    {
        return "JFInterimMaterialStructure";
    }
}
