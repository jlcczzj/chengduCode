/**
 * 生成程序InterimMaterialSplit.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.ejb.entity;

import java.sql.Timestamp;
import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: 临时物料拆分。</p>
 * <p>Description: 零部件按路线节点拆分为物料的临时对象，主要为更新物料时使用。</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public interface InterimMaterialSplit extends BsoReference
{
    /**
     * 设置零件号，被拆分为物料的零件号。
     * @param partNumber 零件号。
     */
    void setPartNumber(String partNumber);

    /**
     * 获取零件号，被拆分为物料的零件号。
     * @return 零件号。
     */
    String getPartNumber();

    /**
     * 设置版本，被拆分为物料的零件的版本(不含版序)。
     * @param partVersion 版本。
     */
    void setPartVersion(String partVersion);

    /**
     * 获取版本，被拆分为物料的零件的版本(不含版序)。
     * @return 版本。
     */
    String getPartVersion();

    /**
     * 设置状态，被拆分为物料的零件拆分时的生命周期状态。
     * @param state 状态。
     */
    void setState(String state);

    /**
     * 获取状态，被拆分为物料的零件拆分时的生命周期状态。
     * @return 状态。
     */
    String getState();

    /**
     * 设置物料号，拆分后的物料号，由零件号+“-”+路线代码组成,回头件第二次出现加尾号"-1"。
     * @param materialNumber 物料号。
     */
    void setMaterialNumber(String materialNumber);

    /**
     * 获取物料号，拆分后的物料号，由零件号+“-”+路线代码组成,回头件第二次出现加尾号"-1"。
     * @return 物料号。
     */
    String getMaterialNumber();

    /**
     * 设置转换标识，零件是否转换为物料，0—未转换，1—已转换。
     * @param splited 转换标识。
     */
    void setSplited(boolean splited);

    /**
     * 获取转换标识，零件是否转换为物料，0—未转换，1—已转换。
     * @return 转换标识。
     */
    boolean getSplited();

    /**
     * 设置层级状态，物料是否经过拆分，0-最底层物料，1-有下级物料，2—此物料下要挂接原零部件的子件。
     * @param status 层级状态。
     */
    void setStatus(int status);

    /**
     * 获取层级状态，物料是否经过拆分，0-最底层物料，1-有下级物料，2—此物料下要挂接原零部件的子件。
     * @return 层级状态。
     */
    int getStatus();

    /**
     * 设置路线代码，与此物料有关的物料拆分后的路线代码。
     * @param routeCode 路线代码。
     */
    void setRouteCode(String routeCode);

    /**
     * 获取路线代码与此物料有关的物料拆分后的路线代码。
     * @return 路线代码。
     */
    String getRouteCode();    

    /**
     * 设置路线，事物特性表中一个零件可以有两条路线,多路线时用分号“;”分隔。
     * @param route 路线。
     */
    void setRoute(String route);

    /**
     * 获取路线，事物特性表中一个零件可以有两条路线,多路线时用分号“;”分隔。
     * @return 路线。
     */
    String getRoute();

    /**
     * 设置零件名称，被拆分为物料的零件名称,也作为物料的名称。
     * @param partName 零件名称。
     */
    void setPartName(String partName);

    /**
     * 获取零件名称，被拆分为物料的零件名称,也作为物料的名称。
     * @return 零件名称。
     */
    String getPartName();

    /**
     * 设置默认单位，枚举类型，包括：个、按照所需、千克、米、升,散热器只使用"件",处理为"个"。
     * @param defaultUnit 默认单位。
     */
    void setDefaultUnit(String defaultUnit);

    /**
     * 获取默认单位，枚举类型，包括：个、按照所需、千克、米、升,散热器只使用"件",处理为"个"。
     * @return 默认单位。
     */
    String getDefaultUnit();

    /**
     * 设置来源，枚举值为：自制、外购、待定,默认值为：待定。
     * @param producedBy 来源。
     */
    void setProducedBy(String producedBy);

    /**
     * 获取来源，枚举值为：自制、外购、待定,默认值为：待定。
     * @return 来源。
     */
    String getProducedBy();

    /**
     * 设置类型，枚举值有：零件、总成、标准件、产品,还可包括:冲压件、铸件、管件、非金属件、标准件、装置件、组件、逻辑总成、车型、机型、焊接、装配、酸洗、试制、油漆件。
     * @param partType 类型。
     */
    void setPartType(String partType);

    /**
     * 获取类型，枚举值有：零件、总成、标准件、产品,还可包括:冲压件、铸件、管件、非金属件、标准件、装置件、组件、逻辑总成、车型、机型、焊接、装配、酸洗、试制、油漆件。
     * @return 类型。
     */
    String getPartType();
    
    /**
     * 设置零件更新时间。
     * @param partModifyTime 零件更新时间。
     */
    void setPartModifyTime(Timestamp partModifyTime);

    /**
     * 获取零件更新时间。
     * @return 零件更新时间。
     */
    Timestamp getPartModifyTime();

    /**
     * 设置选装策略码，从PDM配置器注册出的零部件记录此码。
     * @param optionCode 选装策略码。
     */
    void setOptionCode(String optionCode);

    /**
     * 获取选装策略码，从PDM配置器注册出的零部件记录此码。
     * @return 选装策略码。
     */
    String getOptionCode();

    /**
     * 设置颜色件标识，1位数字：0为否、1为是。
     * @param colorFlag 颜色件标识。
     */
    void setColorFlag(boolean colorFlag);

    /**
     * 获取颜色件标识，1位数字：0为否、1为是。
     * @return 颜色件标识。
     */
    boolean getColorFlag();
    
    /**
     * 设置源物料拆分的bsoID。
     */
    void setSourceBsoID(String sourceBsoID);
    
    /**
     * 获取源物料拆分的bsoID。
     * @return 源物料拆分的bsoID。
     */
    String getSourceBsoID();
    
    /**
     * 设置更新标识。
     */
    void setUpdateFlag(String updateFlag);
    
    /**
     * 获取更新标识。
     * @return 更新标识。
     */
    String getUpdateFlag();
    
    /**
     * 设置更新者。
     */
    void setOwner(String userID);
    
    /**
     * 获取更新者。
     * @return 更新者。
     */
    String getOwner();
    
    /**
     * 设置顶级物料标识，1位数字：0为否、1为是。
     * @param rootFlag 顶级物料标识。
     */
    void setRootFlag(boolean rootFlag);

    /**
     * 获取顶级物料标识，1位数字：0为否、1为是。
     * @return 顶级物料标识。
     */
    boolean getRootFlag();
}
