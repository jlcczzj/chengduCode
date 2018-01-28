/**
 * 生成程序PromulgateNotifyEJB.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 青气升级成都兼容问题  liuzhicheng 2012-01-01
 */
package com.faw_qm.erp.ejb.entity;

import javax.ejb.CreateException;
import com.faw_qm.acl.util.AclEntrySet;
import com.faw_qm.acl.util.AdHocAcl;
import com.faw_qm.erp.model.PromulgateNotifyInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;
//CCBegin SS1
import com.faw_qm.lifecycle.util.LifeCycleState;
//CCEnd SS1
/**
 * <p>Title: 采用通知书实现</p>
 * <p>Description: 采用通知书实现</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public abstract class PromulgateNotifyEJB extends BsoReferenceEJB
{
    public abstract String getProjectId();

    public AdHocAcl getAdHocAcl()
    {
        if((getAclOwner() == null) && (getEntrySet() == null))
        {
            return AdHocAcl.newAdHocAcl();
        }
        else if((getAclOwner() != null) && (getEntrySet() == null))
        {
            return AdHocAcl.newAdHocAcl(getAclOwner());
        }
        AdHocAcl adHocAcl = AdHocAcl.newAdHocAcl(getAclOwner());
        AclEntrySet entrySet = new AclEntrySet(getEntrySet());
        adHocAcl.setEntrySet(entrySet);
        return adHocAcl;
    } //end getAdHocAcl()

    /**
     * 设置访问控制列表
     * @param adHocAct
     */
    public void setAdHocAcl(AdHocAcl adHocAcl)
    {
        if(adHocAcl == null)
        {
            return;
        }
        setAclOwner(adHocAcl.getOwner());
        setEntrySet(adHocAcl.getEntrySet().getValue());
    } //end setAdHocAcl(AdHocAcl)

    public abstract boolean getLifeCycleAtGate();

    /**
     * 设置生命周期是否在Gate
     * @param lifeCycleAtGate
     */
    public abstract void setLifeCycleAtGate(boolean lifeCycleAtGate);

    /**
     * 获得生命周期模版
     * @return 生命周期模版
     */
    public abstract String getLifeCycleTemplate();

    /**
     * 设置生命周期模版
     * @param lifeCycleTemplate 生命周期模版
     */
    public abstract void setLifeCycleTemplate(String lifeCycleTemplate);

    /**
     * 获得当前阶段的ID
     * @return 当前阶段的ID
     */
    public abstract String getCurrentPhaseId();

    /**
     * 设置当前阶段的ID
     * @param phaseID
     */
    public abstract void setCurrentPhaseId(String phaseID);

    /**
     * 获得控制所有者,用于构造AdHocAcl
     * @return 控制所有者
     */
    public abstract String getAclOwner();

    /**
     * 设置控制所有者
     * @param alcOwner 控制所有者
     */
    public abstract void setAclOwner(String aclOwner);

    /**
     * 获得entrySet
     * @return entrySet
     */
    public abstract String getEntrySet();

    /**
     * 设置entrySet
     * @param entrySet
     */
    public abstract void setEntrySet(String entrySet);

    /**
     * 获得状态
     * @return state
     */
    public abstract String getLifeCycleState();

    /**
     * 设置状态
     * @param state
     */
    public abstract void setLifeCycleState(String state);

    /**
     * 获得域
     * @return 域
     */
    public abstract String getDomain();

    /**
     * 设置域
     * @param domain 域
     */
    public abstract void setDomain(String domain);
    /**
     * 获得创建者
     * @return String
     */
    public abstract String getCreator();
    /**
     * 设置创建者
     * @param creator String
     */
    public abstract void setCreator(String creator);
    /**
     * 获得位置
     * @return String
     */
    public abstract String getLocation();
    /**
     * 设置位置
     * @param location String
     */
    public abstract void setLocation(String location);
    /**
     * 活的名称
     * @return String
     */
    public abstract String getName();
    /**
     * 设置名称
     * @param name String
     */
    public abstract void setName(String name);
    /**
     * 获得 路径
     * @return String
     */
    public String getPath()
    {
        return getLocation() + "\\" + getName();
    }

    /**
     * 获得事件集
     * @return 事件集
     */
    public abstract String getEventSet();

    /**
     * 设置事件集
     * @param event 事件集
     */
    public abstract void setEventSet(String Event);

    /**
     * 设置项目ID
     * @param projectID 项目ID
     */
    public abstract void setProjectId(String projectID);

    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "PromulgateNotify";
    }

    /**
     * 采用通知标识编号
     * @return String
     */
    public abstract String getPromulgateNotifyNumber();

    public abstract void setPromulgateNotifyNumber(String number);

    /**
     * 采用通知名称
     * @return String
     */
    public abstract String getPromulgateNotifyName();
    /**
     * 设置采用名称
     * @param name String
     */
    public abstract void setPromulgateNotifyName(String name);

    /**
     * 采用通知类别
     * @return String
     */
    public abstract String getPromulgateNotifyType();
    /**
     * 设置采用类别
     * @param type String
     */
    public abstract void setPromulgateNotifyType(String type);

    /**
     * 采用标记
     * @return String
     */
    public abstract String getPromulgateNotifyFlag();
    /**
     * 设置采用标记
     * @param flag String
     */
    public abstract void setPromulgateNotifyFlag(String flag);

    /**
     * 采用说明
     * @return String
     */
    public abstract String getPromulgateNotifyDescription();
    /**
     * 设置采用说明
     * @param description String
     */
    public abstract void setPromulgateNotifyDescription(String description);
    /**
     * 获得是否发布
     * @return String
     */
    public abstract String getHasPromulgate();
    /**
     * 设置是否发布
     * @param has String
     */
    public abstract void setHasPromulgate(String has);

    /**
     *获取业务对象对应的值对象
     * @throws QMException
     * @return BaseValueIfc
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        PromulgateNotifyInfo info = new PromulgateNotifyInfo();
        setValueInfo(info);
        return info;
    }

    /**
     *设置业务对象对应的值对象，需要设定锁服务的信息
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        PromulgateNotifyInfo minfo = (PromulgateNotifyInfo) info;
        minfo.setPromulgateNotifyName(getPromulgateNotifyName());
        minfo.setPromulgateNotifyNumber(getPromulgateNotifyNumber());
        minfo.setPromulgateNotifyFlag(getPromulgateNotifyFlag());
        minfo.setPromulgateNotifyDescription(this
                .getPromulgateNotifyDescription());
        minfo.setPromulgateNotifyType(this.getPromulgateNotifyType());
        minfo.setHasPromulgate(getHasPromulgate());
        minfo.setName(getName());
        minfo.setLocation(getLocation());
        minfo.setDomain(getDomain());
        minfo.setAdHocAcl(getAdHocAcl());
        minfo.setProjectId(getProjectId());
        minfo.setEventSet(getEventSet());
        if(getLifeCycleState() != null)
        {
        	//CCBegin SS1
            minfo.setLifeCycleState(LifeCycleState.toLifeCycleState(getLifeCycleState()));
            //CCEnd SS1
        }
        minfo.setLifeCycleAtGate(getLifeCycleAtGate());
        minfo.setLifeCycleTemplate(getLifeCycleTemplate());
        minfo.setCurrentPhaseId(getCurrentPhaseId());
        minfo.setCreator(getCreator());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        PromulgateNotifyInfo minfo = (PromulgateNotifyInfo) info;
        //需要进行属性校验,
        setPromulgateNotifyName(minfo.getPromulgateNotifyName());
        setPromulgateNotifyNumber(minfo.getPromulgateNotifyNumber());
        setPromulgateNotifyFlag(minfo.getPromulgateNotifyFlag());
        setPromulgateNotifyDescription(minfo.getPromulgateNotifyDescription());
        setPromulgateNotifyType(minfo.getPromulgateNotifyType());
        setHasPromulgate(minfo.getHasPromulgate());
        setDomain(minfo.getDomain());
        setName(minfo.getName());
        setLocation(minfo.getLocation());
        setProjectId(minfo.getProjectId());
        setAdHocAcl(minfo.getAdHocAcl());
        setCurrentPhaseId(minfo.getCurrentPhaseId());
        setLifeCycleAtGate(minfo.getLifeCycleAtGate());
        setEventSet(minfo.getEventSet());
        if(minfo.getLifeCycleState() != null)
        {
            setLifeCycleState(minfo.getLifeCycleState().toString());
        }
        setLifeCycleTemplate(minfo.getLifeCycleTemplate());
        setCreator(minfo.getCreator());
    }

    /**
     *过载父类的方法。首先调用父类的相应方法，其后在为本类中定制的业务属性赋值。
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        PromulgateNotifyInfo minfo = (PromulgateNotifyInfo) info;
        setPromulgateNotifyName(minfo.getPromulgateNotifyName());
        setPromulgateNotifyNumber(minfo.getPromulgateNotifyNumber());
        setPromulgateNotifyFlag(minfo.getPromulgateNotifyFlag());
        setPromulgateNotifyDescription(minfo.getPromulgateNotifyDescription());
        setPromulgateNotifyType(minfo.getPromulgateNotifyType());
        setHasPromulgate(minfo.getHasPromulgate());
        setDomain(minfo.getDomain());
        setName(minfo.getName());
        setLocation(minfo.getLocation());
        setProjectId(minfo.getProjectId());
        setAdHocAcl(minfo.getAdHocAcl());
        setCurrentPhaseId(minfo.getCurrentPhaseId());
        setLifeCycleAtGate(minfo.getLifeCycleAtGate());
        setEventSet(minfo.getEventSet());
        if(minfo.getLifeCycleState() != null)
        {
            setLifeCycleState(minfo.getLifeCycleState().toString());
        }
        setLifeCycleTemplate(minfo.getLifeCycleTemplate());
        setCreator(minfo.getCreator());
    }
}
