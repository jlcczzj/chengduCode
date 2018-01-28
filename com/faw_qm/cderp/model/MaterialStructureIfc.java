/**
 * 生成程序MaterialStructureIfc.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 增加添加替换前数量、替换前零件和子组功能 刘家坤 2017-3-3
 */
package com.faw_qm.cderp.model;

import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public interface MaterialStructureIfc extends BaseValueIfc
{
    /**
     * 设置父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @param parentPartNumber 父件号。
     */
    void setParentPartNumber(String parentPartNumber);

    /**
     * 获取父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @return 父件号。
     */
    String getParentPartNumber();

    /**
     * 设置父件版本，父物料拆分前零件版本，用于检查结构是否更改。
     * @param parentPartVersion 父件版本。
     */
    void setParentPartVersion(String parentPartVersion);

    /**
     * 获取父件版本，父物料拆分前零件版本，用于检查结构是否更改。
     * @return 父件版本。
     */
    String getParentPartVersion();

    /**
     * 设置父物料，记录拆分后的物料父项号。
     * @param parentNumber 父物料。
     */
    void setParentNumber(String parentNumber);

    /**
     * 获取父物料，记录拆分后的物料父项号。
     * @return 父物料。
     */
    String getParentNumber();

    /**
     * 设置子物料，记录拆分后的物料子项号。
     * @param childNumber 子物料。
     */
    void setChildNumber(String childNumber);

    /**
     * 获取子物料，记录拆分后的物料子项号。
     * @return 子物料。
     */
    String getChildNumber();
    void setMaterialStructureType(String materialStructureType);

    /**
     * 获取父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @return 父件号。
     */
    String getMaterialStructureType();
    /**
     * 设置数量，子物料在父项中的使用数量，零件的使用关系中的数量，物料拆分数量为“1”。
     * @param quantity 数量。
     */
    void setQuantity(float quantity);

    /**
     * 获取数量，子物料在父项中的使用数量，零件的使用关系中的数量，物料拆分数量为“1”。
     * @return 数量。
     */
    float getQuantity();

    /**
     * 设置层级，零件拆分为物料的层级号，从0开始。
     * @param levelNumber 层级。
     */
    void setLevelNumber(String levelNumber);

    /**
     * 获取层级，零件拆分为物料的层级号，从0开始。
     * @return 层级。
     */
    String getLevelNumber();

    /**
     * 设置使用单位，枚举类型，包括：个、按照所需、千克、米、升。
     * @param defaultUnit 使用单位。
     */
    void setDefaultUnit(String defaultUnit);

    /**
     * 获取使用单位，枚举类型，包括：个、按照所需、千克、米、升。
     * @return 使用单位。
     */
    String getDefaultUnit();

    /**
     * 设置选装标识，1位数字：0为否、1为是。
     * @param flag 选装标识。
     */
    void setOptionFlag(boolean optionFlag);

    /**
     * 获取选装标识，1位数字：0为否、1为是。
     * @return 选装标识。
     */
    boolean getOptionFlag();
    /**
     * 设置子组。
     * @param subGroup 子组。
     */
    void setSubGroup(String subGroup);

    /**
     * 获取子组。
     * @return 子组。
     */
    String getSubGroup();
    /**
     * 设置BOM行项号。
     * @param BOMLine BOM行项号。
     */
    void setBomLine(String BOMLine);

    /**
     * 获取BOM行项号。
     * @return BOM行项号。
     */
    String getBomLine();
    /**
     * 获取替换前零件号。
     * @return 替换前零件号。
     */
    //CCBegin SS1
    String getBeforeMaterial();
    /**
     * 设置替换前零件号。
     * @param flag 替换前零件号。
     */
    void setBeforeMaterial(String beforeMaterial);

    /**
     * 获取替换前数量。
     * @return 替换前数量。
     */
    
    float getBeforeQuantity();
    /**
     * 设置替换前数量。
     * @param flag 替换前数量。
     */
    void setBeforeQuantity(float bquantity);
    //CCEnd SS1
}
