/**
 * ���ɳ���PromulgateNotifyEJB.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���������ɶ���������  liuzhicheng 2012-01-01
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
 * <p>Title: ����֪ͨ��ʵ��</p>
 * <p>Description: ����֪ͨ��ʵ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
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
     * ���÷��ʿ����б�
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
     * �������������Ƿ���Gate
     * @param lifeCycleAtGate
     */
    public abstract void setLifeCycleAtGate(boolean lifeCycleAtGate);

    /**
     * �����������ģ��
     * @return ��������ģ��
     */
    public abstract String getLifeCycleTemplate();

    /**
     * ������������ģ��
     * @param lifeCycleTemplate ��������ģ��
     */
    public abstract void setLifeCycleTemplate(String lifeCycleTemplate);

    /**
     * ��õ�ǰ�׶ε�ID
     * @return ��ǰ�׶ε�ID
     */
    public abstract String getCurrentPhaseId();

    /**
     * ���õ�ǰ�׶ε�ID
     * @param phaseID
     */
    public abstract void setCurrentPhaseId(String phaseID);

    /**
     * ��ÿ���������,���ڹ���AdHocAcl
     * @return ����������
     */
    public abstract String getAclOwner();

    /**
     * ���ÿ���������
     * @param alcOwner ����������
     */
    public abstract void setAclOwner(String aclOwner);

    /**
     * ���entrySet
     * @return entrySet
     */
    public abstract String getEntrySet();

    /**
     * ����entrySet
     * @param entrySet
     */
    public abstract void setEntrySet(String entrySet);

    /**
     * ���״̬
     * @return state
     */
    public abstract String getLifeCycleState();

    /**
     * ����״̬
     * @param state
     */
    public abstract void setLifeCycleState(String state);

    /**
     * �����
     * @return ��
     */
    public abstract String getDomain();

    /**
     * ������
     * @param domain ��
     */
    public abstract void setDomain(String domain);
    /**
     * ��ô�����
     * @return String
     */
    public abstract String getCreator();
    /**
     * ���ô�����
     * @param creator String
     */
    public abstract void setCreator(String creator);
    /**
     * ���λ��
     * @return String
     */
    public abstract String getLocation();
    /**
     * ����λ��
     * @param location String
     */
    public abstract void setLocation(String location);
    /**
     * �������
     * @return String
     */
    public abstract String getName();
    /**
     * ��������
     * @param name String
     */
    public abstract void setName(String name);
    /**
     * ��� ·��
     * @return String
     */
    public String getPath()
    {
        return getLocation() + "\\" + getName();
    }

    /**
     * ����¼���
     * @return �¼���
     */
    public abstract String getEventSet();

    /**
     * �����¼���
     * @param event �¼���
     */
    public abstract void setEventSet(String Event);

    /**
     * ������ĿID
     * @param projectID ��ĿID
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
     * ����֪ͨ��ʶ���
     * @return String
     */
    public abstract String getPromulgateNotifyNumber();

    public abstract void setPromulgateNotifyNumber(String number);

    /**
     * ����֪ͨ����
     * @return String
     */
    public abstract String getPromulgateNotifyName();
    /**
     * ���ò�������
     * @param name String
     */
    public abstract void setPromulgateNotifyName(String name);

    /**
     * ����֪ͨ���
     * @return String
     */
    public abstract String getPromulgateNotifyType();
    /**
     * ���ò������
     * @param type String
     */
    public abstract void setPromulgateNotifyType(String type);

    /**
     * ���ñ��
     * @return String
     */
    public abstract String getPromulgateNotifyFlag();
    /**
     * ���ò��ñ��
     * @param flag String
     */
    public abstract void setPromulgateNotifyFlag(String flag);

    /**
     * ����˵��
     * @return String
     */
    public abstract String getPromulgateNotifyDescription();
    /**
     * ���ò���˵��
     * @param description String
     */
    public abstract void setPromulgateNotifyDescription(String description);
    /**
     * ����Ƿ񷢲�
     * @return String
     */
    public abstract String getHasPromulgate();
    /**
     * �����Ƿ񷢲�
     * @param has String
     */
    public abstract void setHasPromulgate(String has);

    /**
     *��ȡҵ������Ӧ��ֵ����
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
     *����ҵ������Ӧ��ֵ������Ҫ�趨���������Ϣ
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
        //��Ҫ��������У��,
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
     *���ظ���ķ��������ȵ��ø������Ӧ�����������Ϊ�����ж��Ƶ�ҵ�����Ը�ֵ��
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
