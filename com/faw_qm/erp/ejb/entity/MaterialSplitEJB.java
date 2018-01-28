/**
 * 生成程序MaterielSplitEJB.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import com.faw_qm.enterprise.ejb.entity.ItemEJB;
import com.faw_qm.erp.model.MaterialSplitInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public abstract class MaterialSplitEJB extends ItemEJB
{
    /**
     * 设置零件号，被拆分为物料的零件号。
     * @param partNumber 零件号。
     */
    public abstract void setPartNumber(String partNumber);

    /**
     * 获取零件号，被拆分为物料的零件号。
     * @return 零件号。
     */
    public abstract String getPartNumber();
    public abstract void setRCode(String rCode);

    /**
     * 获取零件号，被拆分为物料的零件号。
     * @return 零件号。
     */
    public abstract String getRCode();
    public abstract void setMaterialSplitType(String materialSplitType);

    /**
     * 获取零件号，被拆分为物料的零件号。
     * @return 零件号。
     */
    public abstract String getMaterialSplitType();
    /**
     * 设置版本，被拆分为物料的零件的版本(不含版序)。
     * @param partVersion 版本。
     */
    public abstract void setPartVersion(String partVersion);

    /**
     * 获取版本，被拆分为物料的零件的版本(不含版序)。
     * @return 版本。
     */
    public abstract String getPartVersion();

    /**
     * 设置状态，被拆分为物料的零件拆分时的生命周期状态。
     * @param State 状态。
     */
    public abstract void setState(String State);

    /**
     * 获取状态，被拆分为物料的零件拆分时的生命周期状态。
     * @return 状态。
     */
    public abstract String getState();

    /**
     * 设置物料号，拆分后的物料号，由零件号+“-”+路线代码组成,回头件第二次出现加尾号"-1"。
     * @param MaterielNumber 物料号。
     */
    public abstract void setMaterialNumber(String MaterielNumber);

    /**
     * 获取物料号，拆分后的物料号，由零件号+“-”+路线代码组成,回头件第二次出现加尾号"-1"。
     * @return 物料号。
     */
    public abstract String getMaterialNumber();

    /**
     * 设置转换标识，零件是否转换为物料，0—未转换，1—已转换。
     * @param Splited 转换标识。
     */
    public abstract void setSplited(boolean Splited);

    /**
     * 获取转换标识，零件是否转换为物料，0—未转换，1—已转换。
     * @return 转换标识。
     */
    public abstract boolean getSplited();

    /**
     * 设置层级状态，物料是否经过拆分，0-最底层物料，1-有下级物料，2—此物料下要挂接原零部件的子件。
     * @param Status 层级状态。
     */
    public abstract void setStatus(int Status);

    /**
     * 获取层级状态，物料是否经过拆分，0-最底层物料，1-有下级物料，2—此物料下要挂接原零部件的子件。
     * @return 层级状态。
     */
    public abstract int getStatus();

    /**
     * 设置路线代码，与此物料有关的物料拆分后的路线代码。
     * @param RouteCode 路线代码。
     */
    public abstract void setRouteCode(String RouteCode);

    /**
     * 获取路线代码与此物料有关的物料拆分后的路线代码。
     * @return 路线代码。
     */
    public abstract String getRouteCode();

    /**
     * 设置路线，事物特性表中一个零件可以有两条路线,多路线时用分号“;”分隔。
     * @param Route 路线。
     */
    public abstract void setRoute(String Route);

    /**
     * 获取路线，事物特性表中一个零件可以有两条路线,多路线时用分号“;”分隔。
     * @return 路线。
     */
    public abstract String getRoute();

    /**
     * 设置零件名称，被拆分为物料的零件名称,也作为物料的名称。
     * @param PartName 零件名称。
     */
    public abstract void setPartName(String PartName);

    /**
     * 获取零件名称，被拆分为物料的零件名称,也作为物料的名称。
     * @return 零件名称。
     */
    public abstract String getPartName();

    /**
     * 设置默认单位，枚举类型，包括：个、按照所需、千克、米、升,散热器只使用"件",处理为"个"。
     * @param DefaultUnit 默认单位。
     */
    public abstract void setDefaultUnit(String DefaultUnit);

    /**
     * 获取默认单位，枚举类型，包括：个、按照所需、千克、米、升,散热器只使用"件",处理为"个"。
     * @return 默认单位。
     */
    public abstract String getDefaultUnit();

    /**
     * 设置来源，枚举值为：自制、外购、待定,默认值为：待定。
     * @param ProducedBy 来源。
     */
    public abstract void setProducedBy(String ProducedBy);

    /**
     * 获取来源，枚举值为：自制、外购、待定,默认值为：待定。
     * @return 来源。
     */
    public abstract String getProducedBy();

    /**
     * 设置类型，枚举值有：零件、总成、标准件、产品,还可包括:冲压件、铸件、管件、非金属件、标准件、装置件、组件、逻辑总成、车型、机型、焊接、装配、酸洗、试制、油漆件。
     * @param PartType 类型。
     */
    public abstract void setPartType(String PartType);

    /**
     * 获取类型，枚举值有：零件、总成、标准件、产品,还可包括:冲压件、铸件、管件、非金属件、标准件、装置件、组件、逻辑总成、车型、机型、焊接、装配、酸洗、试制、油漆件。
     * @return 类型。
     */
    public abstract String getPartType();
    
    /**
     * 设置零件更新时间。
     * @param partModifyTime 零件更新时间。
     */
    public abstract void setPartModifyTime(Timestamp partModifyTime);

    /**
     * 获取零件更新时间。
     * @return 零件更新时间。
     */
    public abstract Timestamp getPartModifyTime();

    /**
     * 设置选装策略码，从PDM配置器注册出的零部件记录此码。
     * @param OptionCode 选装策略码。
     */
    public abstract void setOptionCode(String OptionCode);

    /**
     * 获取选装策略码，从PDM配置器注册出的零部件记录此码。
     * @return 选装策略码。
     */
    public abstract String getOptionCode();

    /**
     * 设置颜色件标识，1位数字：0为否、1为是。
     * @param ColorFlag 颜色件标识。
     */
    public abstract void setColorFlag(boolean ColorFlag);

    /**
     * 获取颜色件标识，1位数字：0为否、1为是。
     * @return 颜色件标识。
     */
    public abstract boolean getColorFlag();
    
    /**
     * 设置顶级物料标识，1位数字：0为否、1为是。
     * @param rootFlag 顶级物料标识。
     */
    public abstract void setRootFlag(boolean rootFlag);

    /**
     * 获取顶级物料标识，1位数字：0为否、1为是。
     * @return 顶级物料标识。
     */
    public abstract boolean getRootFlag();
    
    /**
     * 设置虚拟物料标识
     * @param flag 虚拟物料标识
     */
    public abstract void setVirtualFlag(boolean flag);
    
    /**
     * 获取多路线标识
     * @return 多路线标识
     */
    public abstract boolean getIsMoreRoute();
    
    /**
     * 设置多路线标识
     * @param flag 多路线标识
     */
    public abstract void setIsMoreRoute(boolean flag);
    
    /**
     * 获取虚拟物料标识
     * @return 虚拟物料标识
     */
    public abstract boolean getVirtualFlag();

    public BaseValueIfc getValueInfo() throws QMException
    {
        MaterialSplitInfo info = new MaterialSplitInfo();
        setValueInfo(info);
        return info;
    }

    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        MaterialSplitInfo info1 = (MaterialSplitInfo) info;
        info1.setPartNumber(getPartNumber());
        info1.setRCode(getRCode());
        info1.setMaterialSplitType(getMaterialSplitType());
        info1.setPartName(getPartName());
        info1.setPartVersion(getPartVersion());
        info1.setState(getState());
        info1.setMaterialNumber(getMaterialNumber());
        info1.setSplited(getSplited());
        info1.setStatus(getStatus());
        info1.setRouteCode(getRouteCode());
        info1.setRoute(getRoute());
        info1.setPartName(getPartName());
        info1.setDefaultUnit(getDefaultUnit());
        info1.setProducedBy(getProducedBy());
        info1.setPartType(getPartType());
        info1.setPartModifyTime(getPartModifyTime());
        info1.setOptionCode(getOptionCode());
        info1.setColorFlag(getColorFlag());
        info1.setRootFlag(getRootFlag());
        info1.setVirtualFlag(getVirtualFlag());
        info1.setIsMoreRoute(getIsMoreRoute());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        MaterialSplitInfo info1 = (MaterialSplitInfo) info;
        setPartNumber(info1.getPartNumber());
        setRCode(info1.getRCode());
        setMaterialSplitType(info1.getMaterialSplitType());
        setPartName(info1.getPartName());
        setPartVersion(info1.getPartVersion());
        setState(info1.getState());
        setMaterialNumber(info1.getMaterialNumber());
        setSplited(info1.getSplited());
        setStatus(info1.getStatus());
        setRouteCode(info1.getRouteCode());
        setRoute(info1.getRoute());
        setPartName(info1.getPartName());
        setDefaultUnit(info1.getDefaultUnit());
        setProducedBy(info1.getProducedBy());
        setPartType(info1.getPartType());
        setPartModifyTime(info1.getPartModifyTime());
        setOptionCode(info1.getOptionCode());
        setColorFlag(info1.getColorFlag());
        setRootFlag(info1.getRootFlag());
        setVirtualFlag(info1.getVirtualFlag());
        setIsMoreRoute(info1.getIsMoreRoute());
    }

    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        MaterialSplitInfo info1 = (MaterialSplitInfo) info;
        setPartNumber(info1.getPartNumber());
        setRCode(info1.getRCode());
        setPartName(info1.getPartName());
        setPartVersion(info1.getPartVersion());
        setState(info1.getState());
        setMaterialNumber(info1.getMaterialNumber());
        setSplited(info1.getSplited());
        setStatus(info1.getStatus());
        setRouteCode(info1.getRouteCode());
        setRoute(info1.getRoute());
        setPartName(info1.getPartName());
        setDefaultUnit(info1.getDefaultUnit());
        setProducedBy(info1.getProducedBy());
        setPartType(info1.getPartType());
        setPartModifyTime(info1.getPartModifyTime());
        setOptionCode(info1.getOptionCode());
        setColorFlag(info1.getColorFlag());
        setRootFlag(info1.getRootFlag());
        setVirtualFlag(info1.getVirtualFlag());
        setIsMoreRoute(info1.getIsMoreRoute());
        //CCBegin by dikefeng 20100422，这个地方需要加上，否则更新之后再保存的话，就保存不上
        setMaterialSplitType(info1.getMaterialSplitType());
        //CCEnd by dikefeng 20100422
    }

    public String getBsoName()
    {
        return "MaterialSplit";
    }
}
