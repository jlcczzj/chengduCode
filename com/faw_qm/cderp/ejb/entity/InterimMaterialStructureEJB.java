/**
 * 生成程序InterimMaterialStructureEJB.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.cderp.model.InterimMaterialStructureInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public abstract class InterimMaterialStructureEJB extends BsoReferenceEJB
{
    /**
     * 设置父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @param parentPartNumber 父件号。
     */
    public abstract void setParentPartNumber(String parentPartNumber);

    /**
     * 获取父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @return 父件号。
     */
    public abstract String getParentPartNumber();

    /**
     * 设置父件版本，父物料拆分前零件版本，用于检查结构是否更改。
     * @param parentPartVersion 父件版本。
     */
    public abstract void setParentPartVersion(String parentPartVersion);

    /**
     * 获取父件版本，父物料拆分前零件版本，用于检查结构是否更改。
     * @return 父件版本。
     */
    public abstract String getParentPartVersion();

    /**
     * 设置父物料，记录拆分后的物料父项号。
     * @param parentNumber 父物料。
     */
    public abstract void setParentNumber(String parentNumber);

    /**
     * 获取父物料，记录拆分后的物料父项号。
     * @return 父物料。
     */
    public abstract String getParentNumber();

    /**
     * 设置子物料，记录拆分后的物料子项号。
     * @param childNumber 子物料。
     */
    public abstract void setChildNumber(String childNumber);

    /**
     * 获取子物料，记录拆分后的物料子项号。
     * @return 子物料。
     */
    public abstract String getChildNumber();

    /**
     * 设置数量，子物料在父项中的使用数量，零件的使用关系中的数量，物料拆分数量为“1”。
     * @param quantity 数量。
     */
    public abstract void setQuantity(float quantity);

    /**
     * 获取数量，子物料在父项中的使用数量，零件的使用关系中的数量，物料拆分数量为“1”。
     * @return 数量。
     */
    public abstract float getQuantity();

    /**
     * 设置层级，零件拆分为物料的层级号，从0开始。
     * @param levelNumber 层级。
     */
    public abstract void setLevelNumber(String levelNumber);

    /**
     * 获取层级，零件拆分为物料的层级号，从0开始。
     * @return 层级。
     */
    public abstract String getLevelNumber();    

    /**
     * 设置使用单位，枚举类型，包括：个、按照所需、千克、米、升。
     * @param defaultUnit 使用单位。
     */
    public abstract void setDefaultUnit(String defaultUnit);

    /**
     * 获取使用单位，枚举类型，包括：个、按照所需、千克、米、升。
     * @return 使用单位。
     */
    public abstract String getDefaultUnit();

    /**
     * 设置选装标识，1位数字：0为否、1为是。
     * @param flag 选装标识。
     */
    public abstract void setOptionFlag(boolean optionFlag);

    /**
     * 获取选装标识，1位数字：0为否、1为是。
     * @return 选装标识。
     */
    public abstract boolean getOptionFlag();
    
    /**
     * 设置源物料结构的bsoID。
     */
    public abstract void setSourceBsoID(String sourceBsoID);
    
    /**
     * 获取源物料结构的bsoID。
     * @return 源物料结构的bsoID。
     */
    public abstract String getSourceBsoID();
    
    /**
     * 设置更新标识。
     */
    public abstract void setUpdateFlag(String updateFlag);
    
    /**
     * 设置更新者。
     */
    public abstract void setOwner(String userID);
    
    /**
     * 获取更新者。
     * @return 更新者。
     */
    public abstract String getOwner();
    
    /**
     * 获取更新标识。
     * @return 更新标识。
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
