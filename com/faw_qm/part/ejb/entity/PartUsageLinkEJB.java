/** 生成程序 PartUsageLinkEJB.java    1.0    2003/02/17
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 *  SS1 增加BOM行项和子组 liuyang 2014-6-20
 *  SS2 增加生产版本 xianglx 2014-8-12
 */

package com.faw_qm.part.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.build.util.BuildReference;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.struct.ejb.entity.IteratedUsageLinkEJB;


/**
 * 零部件与零部件主信息的关联类，主要记录了零部件之间的使用关系及使用的数量和单位。
 * @author 吴先超
 * @version 1.0
 */

public abstract class PartUsageLinkEJB extends IteratedUsageLinkEJB
{
    private QMQuantity qmQuantity;


    /**
     * 获得使用数量。
     * @return float
     */
    public abstract float getQuantity();


    /**
     * 设置使用数量。
     * @param quantity float
     */
    public abstract void setQuantity(float quantity);


    /**
     * 获得使用单位。受CMP管理。
     * @return String
     */
    public abstract String getDefaultUnitStr();


    /**
     * 获取使用的单位。
     * @return Unit
     * @see Unit
     */
    public Unit getDefaultUnit()
    {
        return Unit.toUnit(getDefaultUnitStr());
    }


    /**
     * 设置使用单位。受CMP管理。
     * @param unit String
     */

    public abstract void setDefaultUnitStr(String unit);


    /**
     * 设置使用单位。
     * @param unit Unit
     * @see Unit
     */
    public void setDefaultUnit(Unit unit)
    {
        setDefaultUnitStr(unit.toString());
    }


    /**
     * 获取使用数量类的对象。该对象包含了使用数量和默认的使用单位。
     * @return QMQuantity
     * @see QMQuantity
     */
    public QMQuantity getQMQuantity()
    {
        if (qmQuantity == null)
        {
            qmQuantity = new QMQuantity();
            qmQuantity.setDefaultUnit(getDefaultUnit());
            qmQuantity.setQuantity(getQuantity());
        }
        return qmQuantity;
    }


    /**
     * 设置使用单位类的对象。
     * @param qmQuantity1 QMQuantity
     * @see QMQuantity
     */
    public void setQMQuantity(QMQuantity qmQuantity1)
    {
        qmQuantity = qmQuantity1;
    }


    /**
     * 构造函数。
     */
    public PartUsageLinkEJB()
    {
        super();
    }


    /**
     * 获取业务类名称。
     * @return "PartUsageLink"
     */
    public String getBsoName()
    {
        return "PartUsageLink";
    }


    /**
     * 访问选装标识。
     *
     * @return boolean
     */
    public abstract boolean getOptionFlag();


    /**
     * 设置选装标识。
     *    
     * @param flag boolean
     */
    public abstract void setOptionFlag(boolean flag);
        
//CCBegin SS1
    private String subUnitNumber;
    private String bomItem;
  public abstract String getSubUnitNumber();
    
    public abstract void setSubUnitNumber(String subUnitNumber);
    
    public abstract String getBomItem();
   
    public abstract void  setBomItem(String bomItem);
    //CCEnd SS1
//CCBegin SS2
    public abstract String getProVersion();
   
    public abstract void  setProVersion(String proVersion);
//CCEnd SS1
    /**
     * 设置关联类的值对象。
     * @param info BaseValueIfc
     * @throws QMException
     * @see BaseValueIfc
     */
    public void setValueInfo(BaseValueIfc info)
            throws QMException
    {
        super.setValueInfo(info);
        PartUsageLinkInfo pInfo = (PartUsageLinkInfo) info;
        pInfo.setQuantity(getQuantity());
        pInfo.setDefaultUnit(getDefaultUnit());
        pInfo.setQMQuantity(getQMQuantity());
        pInfo.setSourceIdentification(getSourceIdentification());
        pInfo.setOptionFlag(getOptionFlag());
        //CCBegin SS1
        pInfo.setSubUnitNumber(getSubUnitNumber());
        pInfo.setBomItem(getBomItem());
        //CCEnd SS1
        //CCBegin SS2
        pInfo.setProVersion(getProVersion());
        //CCEnd SS2
    }
    


    /**
     * 获取关联类的值对象。
     * @return BaseValueIfc
     * @throws QMException
     * @see BaseValueIfc
     */
    public BaseValueIfc getValueInfo()
            throws QMException
    {
        PartUsageLinkInfo linkInfo = new PartUsageLinkInfo();
        setValueInfo(linkInfo);
        return linkInfo;
    }


    /**
     * 根据关联类值对象创建关联类的业务对象。
     * @param info BaseValueIfc
     * @throws CreateException
     * @see BaseValueIfc
     */
    public void createByValueInfo(BaseValueIfc info)
            throws CreateException
    {
        super.createByValueInfo(info);
        PartUsageLinkInfo linkInfo = (PartUsageLinkInfo) info;
        setQuantity(linkInfo.getQuantity());
        setDefaultUnit(linkInfo.getDefaultUnit());
        setQMQuantity(linkInfo.getQMQuantity());
        BuildReference build = linkInfo.getSourceIdentification();
        setSourceIdentification(build);
        setOptionFlag(linkInfo.getOptionFlag());
        //CCBegin SS1
        setSubUnitNumber(linkInfo.getSubUnitNumber());
        setBomItem(linkInfo.getBomItem());
        //CCEnd SS1
        //CCBegin SS2
        setProVersion(linkInfo.getProVersion());
        //CCEnd SS2
    }


    /**
     * 根据值对象更新关联类的业务对象。
     * @param info BaseValueIfc
     * @throws QMException
     * @see BaseValueIfc
     */
    public void updateByValueInfo(BaseValueIfc info)
            throws QMException
    {
        super.updateByValueInfo(info);
        PartUsageLinkInfo linkInfo = (PartUsageLinkInfo) info;
        setQuantity(linkInfo.getQuantity());
        setDefaultUnit(linkInfo.getDefaultUnit());
        setQMQuantity(linkInfo.getQMQuantity());
        BuildReference build = linkInfo.getSourceIdentification();
        setSourceIdentification(build);
        setOptionFlag(linkInfo.getOptionFlag());
        //CCBegin SS1
        setSubUnitNumber(linkInfo.getSubUnitNumber());
        setBomItem(linkInfo.getBomItem());
        //CCEnd SS1
        //CCBegin SS2
        setProVersion(linkInfo.getProVersion());
        //CCEnd SS2
    }


    /**
     * 访问BuildReference。
     *
     * @return BuildReference
     * @see BuildReference
     */
    public BuildReference getSourceIdentification()
    {
        BuildReference build = new BuildReference(getApplicationTag(),
                                                  getUniqueID());
        build.setBuiltByApplication(getBuiltByApplication());
        return build;
    }


    /**
     * 设置BuildReference中的数据。
     *
     * @param build BuildReference
     * @see BuildReference
     */
    public void setSourceIdentification(BuildReference build)
    {
        if (build != null)
        {
            setBuiltByApplication(build.isBuiltByApplication());
            setApplicationTag(build.getApplicationTag());
            setUniqueID(build.getUniqueID());
        }
    }
/**
 * 设置层次号，用于区分结构层次，不需要手动添加
 * @param uid
 */
    public abstract void setUniqueID(String uid);
/**
 * 获取层次号
 * @return
 */
    public abstract String getUniqueID();
/**
 * 设置使用EPM文档目标
 * @param atag
 */
    public abstract void setApplicationTag(String atag);
/**
 * 获取使用EPM文档目标
 * @return 返回字串形如：com.faw_qm.epm.build.model.EPMBuildLinksRuleInfo:131
 */
    public abstract String getApplicationTag();

    public abstract void setBuiltByApplication(boolean by);

    public abstract boolean getBuiltByApplication();


}
