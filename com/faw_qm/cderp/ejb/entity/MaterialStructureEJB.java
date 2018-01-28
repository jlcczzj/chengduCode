/**
 * ���ɳ���MaterialStructureEJB.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ������Ӹ��������鹦�� ������ 2017-3-3
 */
package com.faw_qm.cderp.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.cderp.model.MaterialStructureInfo;
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
public abstract class MaterialStructureEJB extends BsoReferenceEJB
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
    public abstract void setMaterialStructureType(String materialStructureType);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    public abstract String getMaterialStructureType();

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
     * �������顣
     * @param subGroup ���顣
     */
    public abstract void setSubGroup(String subGroup);

    /**
     * ��ȡ���顣
     * @return ���顣
     */
    public  abstract String getSubGroup();
    /**
     * ����BOM����š�
     * @param BOMLine BOM����š�
     */
    public  abstract void setBomLine(String bomLine);

    /**
     * ��ȡBOM����š�
     * @return BOM����š�
     */
    public  abstract String getBomLine();
    //CCBegin SS1
    public abstract String  getBeforeMaterial();
    /**
     * �����滻ǰ����š�
     * @param flag �滻ǰ����š�
     */
    public abstract void  setBeforeMaterial(String beforeMaterial); 

    /**
     * ��ȡ�滻ǰ������
     * @return �滻ǰ������
     */
    
    public abstract float  getBeforeQuantity();
    /**
     * �����滻ǰ������
     * @param flag �滻ǰ������
     */
    public abstract void  setBeforeQuantity(float bquantity);
    //CCEnd SS1
    public BaseValueIfc getValueInfo() throws QMException
    {
        MaterialStructureInfo info = new MaterialStructureInfo();
        setValueInfo(info);
        return info;
    }

    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        MaterialStructureInfo info1 = (MaterialStructureInfo) info;
        info1.setParentPartNumber(getParentPartNumber());
        info1.setMaterialStructureType(getMaterialStructureType());
        info1.setParentPartVersion(getParentPartVersion());
        info1.setParentNumber(getParentNumber());
        info1.setChildNumber(getChildNumber());
        info1.setQuantity(getQuantity());
        info1.setLevelNumber(getLevelNumber());
        info1.setDefaultUnit(getDefaultUnit());
        info1.setOptionFlag(getOptionFlag());
        info1.setSubGroup(getSubGroup());
        info1.setBomLine(getBomLine());
        info1.setBeforeQuantity(getBeforeQuantity());
        info1.setBeforeMaterial(getBeforeMaterial());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        MaterialStructureInfo info1 = (MaterialStructureInfo) info;
        setParentPartNumber(info1.getParentPartNumber());
        setMaterialStructureType(info1.getMaterialStructureType());
        setParentPartVersion(info1.getParentPartVersion());
        setParentNumber(info1.getParentNumber());
        setChildNumber(info1.getChildNumber());
        setQuantity(info1.getQuantity());
        setLevelNumber(info1.getLevelNumber());
        setDefaultUnit(info1.getDefaultUnit());
        setOptionFlag(info1.getOptionFlag());
        setSubGroup(info1.getSubGroup());
        setBomLine(info1.getBomLine());
        setBeforeQuantity(info1.getBeforeQuantity());
        setBeforeMaterial(info1.getBeforeMaterial());
    }

    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        MaterialStructureInfo info1 = (MaterialStructureInfo) info;
        setParentPartNumber(info1.getParentPartNumber());
        setMaterialStructureType(info1.getMaterialStructureType());
        setParentPartVersion(info1.getParentPartVersion());
        setParentNumber(info1.getParentNumber());
        setChildNumber(info1.getChildNumber());
        setQuantity(info1.getQuantity());
        setLevelNumber(info1.getLevelNumber());
        setDefaultUnit(info1.getDefaultUnit());
        setOptionFlag(info1.getOptionFlag());
        setSubGroup(info1.getSubGroup());
        setBomLine(info1.getBomLine());
        setBeforeQuantity(info1.getBeforeQuantity());
        setBeforeMaterial(info1.getBeforeMaterial());
    }

    public String getBsoName()
    {
        return "CDMaterialStructure";
    }
}
