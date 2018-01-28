/** 生成程序 PartUsageLinkInfo.java    1.0    2003/02/17
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2012/03/16 王亮 修改原因：被用于部件和被用于产品改为用jdbc查询。
 * SS1 增加BOM行项和子组 liuyang 2014-6-20
 *  SS2 增加生产版本 xianglx 2014-8-12
 */

package com.faw_qm.part.model;

import com.faw_qm.build.model.BuildTargetIfc;
import com.faw_qm.build.util.BuildReference;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.struct.model.IteratedUsageLinkInfo;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;


/**
 * 零部件与零部件主信息的关联类的值对象实现类，
 * 主要记录了零部件之间的使用关系及使用的数量和单位。
 * @author 吴先超
 * @version 1.0
 */

public class PartUsageLinkInfo extends IteratedUsageLinkInfo implements
        PartUsageLinkIfc
{
    public PartUsageLinkMap thePartUsageLinkMap;
    private BuildReference sourceIdentification;
    static final long serialVersionUID = -1L;


    /**
     * 构造函数。
     */
    public PartUsageLinkInfo()
    {
        super();
        thePartUsageLinkMap = new PartUsageLinkMap();
    }


    /**
     * 含有两个参数的构造函数。
     * @param leftID :String 关联类对象的左关联对象的BsoID。
     * @param rightID :String 关联类对象的右关联对象的BsoID。
     */
    public PartUsageLinkInfo(String leftID, String rightID)
    {
        super(leftID, rightID);
    }


    /**
     * 获得使用数量。
     * @return float
     */
    public float getQuantity()
    {
        return getPartUsageLinkMap().getQuantity();
    }


    /**
     * 设置使用数量。
     * @param quantity :float
     */
    public void setQuantity(float quantity)
    {
        getPartUsageLinkMap().setQuantity(quantity);
    }


    /**
     * 获得使用单位。
     * @return Unit 单位的枚举类
     * @see Unit
     */
    public Unit getDefaultUnit()
    {
        return getPartUsageLinkMap().getDefaultUnit();
    }


    /**
     * 设置使用单位。
     * @param unit Unit
     * @see Unit
     */
    public void setDefaultUnit(Unit unit)
    {
        getPartUsageLinkMap().setDefaultUnit(unit);
    }
    /**
     * 设置使用单位。
     * @param unit Unit
     * @see Unit
     */
    public void setDefaultUnit(String unit)
    {
        getPartUsageLinkMap().setDefaultUnit(unit);
    }


    /**
     * 获取关联类辅助类对象。
     * @return PartUsageLinkMap
     */
    public PartUsageLinkMap getPartUsageLinkMap()
    {
        if (thePartUsageLinkMap == null)
        {
            thePartUsageLinkMap = new PartUsageLinkMap();
        }
        return thePartUsageLinkMap;
    }


    /**
     * 设置关联类辅助类对象。
     * @param map PartUsageLinkMap
     */
    public void setPartUsageLinkMap(PartUsageLinkMap map)
    {
        thePartUsageLinkMap = map;
    }


    /**
     * 获取业务对象名。
     * @return "PartUsageLink"
     */
    public String getBsoName()
    {
        return "PartUsageLink";
    }


    /**
     * 获取使用数量类对象。
     * @return QMQuantity 数量的封装类
     * @see QMQuantity
     */
    public QMQuantity getQMQuantity()
    {
        return getPartUsageLinkMap().getQMQuantity();
    }


    /**
     * 设置使用数量类对象。
     * @param qmQuantity QMQuantity
     * @see QMQuantity
     */
    public void setQMQuantity(QMQuantity qmQuantity)
    {
        getPartUsageLinkMap().setQMQuantity(qmQuantity);
    }
   /**
    * 获取关联类中right对象（QMPartIfc）
    * @return 返回是关联结构中的父零部件对象QMPartIfc,上溯为BuildTargetIfc
    * @see BuildTargetIfc
    */
    public BuildTargetIfc getBuildTarget()
    {
        return (BuildTargetIfc) getUsedBy();
    }
    /**
     * 设置关联类中right对象（QMPartIfc）
     * @param buildtarget 关联中的right对象（QMPartIfc）
     * @see BuildTargetIfc
     */
    public void setBuildTarget(BuildTargetIfc buildtarget)
    {
        setUsedBy((IteratedIfc) buildtarget);
    }
    /**
     * 获取关联类中left对象（QMPartMasterIfc）
     * @return 返回是关联结构中的子零部件主对象QMPartMasterIfc,上溯为BaseValueIfc
     * @see BaseValueIfc
     */
    public BaseValueIfc getPersistable()
    {
        return getUses();
    }
    /**
     * 设置关联类中right对象（QMPartMasterIfc）
     * @param persistable 关联中的left对象（QMPartMasterIfc）
     * @see BaseValueIfc
     */
    public void setPersistable(BaseValueIfc persistable)
    {
        setUses((MasteredIfc) persistable);
    }
    /**
     * 获取和EPM文档相关的BuildReference对象
     * @return BuildReference 记录EPM文档信息
     * @see BuildReference
     */
    public BuildReference getSourceIdentification()
    {
        return sourceIdentification;
    }
    /**
     * 设置和EPM文档相关的BuildReference信息
     * @param buildreference 
     * @see BuildReference
     */
    public void setSourceIdentification(BuildReference buildreference)
    {
        sourceIdentification = buildreference;
    }


    /**
     * 访问选装标识。
     *
     * @return boolean true为选装
     */
    public boolean getOptionFlag()
    {
        return getPartUsageLinkMap().getOptionFlag();
    }


    /**
     * 设置选装标识。
     *
     * @param flag boolean true为选装
     */
    public void setOptionFlag(boolean flag)
    {
        getPartUsageLinkMap().setOptionFlag(flag);
    }
    
    public void setBuiltByApplication(boolean flag)//Begin CR1
    {}
    public boolean getBuiltByApplication()
    {
    	return false;
    }//End CR1


//CCBegin SS1

    public String getSubUnitNumber()
    {
    	return getPartUsageLinkMap().getSubUnitNumber();
    }
    public void setSubUnitNumber(String subUnitNumber)
    {
    	getPartUsageLinkMap().setSubUnitNumber(subUnitNumber);
    }
    public String getBomItem()
    {
    	return getPartUsageLinkMap().getBomItem();
    }
    public void setBomItem(String bomItem)
    {
    	getPartUsageLinkMap().setBomItem(bomItem);
    }
	//CCEnd SS1
//CCBegin SS2
    public String getProVersion()
    {
    	return getPartUsageLinkMap().getProVersion();
    }
    public void setProVersion(String ProVersion)
    {
    	getPartUsageLinkMap().setProVersion(ProVersion);
    }
	//CCEnd SS2
}
