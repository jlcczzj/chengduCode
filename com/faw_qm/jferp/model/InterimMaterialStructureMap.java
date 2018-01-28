/**
 * ���ɳ���InterimMaterialStructureMap.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.model;

import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class InterimMaterialStructureMap implements ObjectMappable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String parentPartNumber;

    private String parentPartVersion;

    private String parentNumber;

    private String childNumber;

    private float quantity;

    private String levelNumber;

    private String defaultUnit;

    private boolean optionFlag;
    
    private String sourceBsoID;

    private String updateFlag;
    
    private String userID;
    
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
    
    public String getSourceBsoID()
    {
        return sourceBsoID;
    }

    public String getUpdateFlag()
    {
        return updateFlag;
    }

    public void setSourceBsoID(String sourceBsoID)
    {
        this.sourceBsoID = sourceBsoID;
    }

    public void setUpdateFlag(String updateFlag)
    {
        this.updateFlag = updateFlag;
    }
    
    public String getOwner()
    {
        return userID;
    }

    public void setOwner(String userID)
    {
        this.userID = userID;
    }
}
