/**
 * ���ɳ���MaterialStructureMap.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �������Ӹ��������鹦�� ������ 2017-3-3
 */
package com.faw_qm.cderp.model;

import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class MaterialStructureMap implements ObjectMappable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String parentPartNumber;

    private String parentPartVersion;

    private String parentNumber;
    private String materialStructureType;

    private String childNumber;

    private float quantity;

    private String levelNumber;

    private String defaultUnit;

    private boolean optionFlag;

    private String subGroup;

    private String BOMLine;
    //CCBegin SS1
    private float bquantity;

    private String beforeMaterial;

    //CCEnd SS1
    /**
     * @return ���� childNumber��
     */
    public String getChildNumber()
    {
        return childNumber;
    }

    /**
     * @param childNumber Ҫ���õ� childNumber��
     */
    public void setChildNumber(String childNumber)
    {
        this.childNumber = childNumber;
    }

    /**
     * @return ���� defaultUnit��
     */
    public String getDefaultUnit()
    {
        return defaultUnit;
    }

    /**
     * @param defaultUnit Ҫ���õ� defaultUnit��
     */
    public void setDefaultUnit(String defaultUnit)
    {
        this.defaultUnit = defaultUnit;
    }

    /**
     * @return ���� levelNumber��
     */
    public String getLevelNumber()
    {
        return levelNumber;
    }

    /**
     * @param levelNumber Ҫ���õ� levelNumber��
     */
    public void setLevelNumber(String levelNumber)
    {
        this.levelNumber = levelNumber;
    }

    /**
     * @return ���� optionFlag��
     */
    public boolean getOptionFlag()
    {
        return optionFlag;
    }

    /**
     * @param optionFlag Ҫ���õ� optionFlag��
     */
    public void setOptionFlag(boolean optionFlag)
    {
        this.optionFlag = optionFlag;
    }

    /**
     * @return ���� parentNumber��
     */
    public String getParentNumber()
    {
        return parentNumber;
    }

    /**
     * @param parentNumber Ҫ���õ� parentNumber��
     */
    public void setParentNumber(String parentNumber)
    {
        this.parentNumber = parentNumber;
    }

    /**
     * @return ���� parentPartNumber��
     */
    public String getParentPartNumber()
    {
        return parentPartNumber;
    }

    /**
     * @param parentPartNumber Ҫ���õ� parentPartNumber��
     */
    public void setParentPartNumber(String parentPartNumber)
    {
        this.parentPartNumber = parentPartNumber;
    }

    /**
     * @return ���� parentPartVersion��
     */
    public String getParentPartVersion()
    {
        return parentPartVersion;
    }
    public void setMaterialStructureType(String materialStructureType)
    {
        this.materialStructureType = materialStructureType;
    }

    /**
     * @return ���� parentPartVersion��
     */
    public String getMaterialStructureType()
    {
        return materialStructureType;
    }
    /**
     * @param parentPartVersion Ҫ���õ� parentPartVersion��
     */
    public void setParentPartVersion(String parentPartVersion)
    {
        this.parentPartVersion = parentPartVersion;
    }

    /**
     * @return ���� quantity��
     */
    public float getQuantity()
    {
        return quantity;
    }

    /**
     * @param quantity Ҫ���õ� quantity��
     */
    public void setQuantity(float quantity)
    {
        this.quantity = quantity;
    }
    /**
     * �������顣
     * @param subGroup ���顣
     */
    public void setSubGroup(String subGroup)
    {
    	this.subGroup=subGroup;
    }

    /**
     * ��ȡ���顣
     * @return ���顣
     */
    public String getSubGroup(){
    	 return subGroup;
    }
    /**
     * ����BOM����š�
     * @param BOMLine BOM����š�
     */
    public void setBomLine(String BOMLine){
    	this.BOMLine=BOMLine;
    }

    /**
     * ��ȡBOM����š�
     * @return BOM����š�
     */
    public String getBomLine(){
    	return BOMLine;
    }
    //CCBegin SS1
    /**
     * ��ȡ�滻ǰ����š�
     * @return �滻ǰ����š�
     */
 
    public String getBeforeMaterial(){
    	 return beforeMaterial;
    }
    /**
     * �����滻ǰ����š�
     * @param flag �滻ǰ����š�
     */
    public  void setBeforeMaterial(String beforeMaterial){
    	this.beforeMaterial = beforeMaterial;
    }

    /**
     * ��ȡ�滻ǰ������
     * @return �滻ǰ������
     */
    
    public float getBeforeQuantity(){
    	return bquantity;
    }
    /**
     * �����滻ǰ������
     * @param flag �滻ǰ������
     */
    public void setBeforeQuantity(float bquantity){
    	this.bquantity = bquantity;
    }
    //CCEnd SS1
   
}