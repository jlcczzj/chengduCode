/**
 * ���ɳ���PromulgateNotifyInfo.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���������ɶ���������  liuzhicheng 2012-01-01
 */
package com.faw_qm.cderp.model;

import com.faw_qm.acl.model.AdHocControlledMap;
import com.faw_qm.acl.util.AdHocAcl;
import com.faw_qm.domain.model.DomainAdministeredMap;
import com.faw_qm.folder.model.FolderBasedMap;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.lifecycle.model.LifeCycleManagedMap;
//CCBegin SS1
import com.faw_qm.lifecycle.util.LifeCycleState;
//CCEnd SS1
import com.faw_qm.notify.model.NotifiableMap;
import com.faw_qm.project.model.ProjectManagedMap;

/**
 * <p>Title: ����֪ͨ��ʵ��</p>
 * <p>Description:����֪ͨ��ʵ��  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class PromulgateNotifyInfo extends BaseValueInfo implements
        PromulgateNotifyIfc
{
    static final long serialVersionUID = -1L;

    private DomainAdministeredMap domainAdministeredMap;

    //�����
    private LifeCycleManagedMap lifeCycleManagedMap;

    /**
     * ���ʿ���Map
     */
    private AdHocControlledMap adHocControlledMap;

    //��Ŀ�����Map
    private ProjectManagedMap projectManagedMap;

    private NotifiableMap notifiableMap;

    private FolderBasedMap folderBasedMap;

    //����
    private String promulgateNotifyName;

    //���
    private String promulgateNotifyNumber;

    private String promulgateNotifyType;

    //���
    private String promulgateNotifyFlag;

    private String promulgateNotifyDescription;

    //������
    private String creator;

    private String hasPromulgate;

    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "CDPromulgateNotify";
    }

    /**
     * getAdHocControlledMap
     *
     * @return AdHocControlledMap
     */
    public AdHocControlledMap getAdHocControlledMap()
    {
        if(adHocControlledMap == null)
        {
            setAdHocControlledMap(new AdHocControlledMap());
            //end if
        }
        return adHocControlledMap;
    }

    /**
     * ��÷��ʿ����б�
     * @return ���ʿ����б�
     */
    public AdHocAcl getAdHocAcl()
    {
        return getAdHocControlledMap().getAdHocAcl();
    } //end getAdHocAcl()

    /**
     * ���÷��ʿ����б�
     * @param adHocAcl
     */
    public void setAdHocAcl(AdHocAcl adHocAcl)
    {
        getAdHocControlledMap().setAdHocAcl(adHocAcl);
    }

    /**
     * setAdHocControlledMap
     *
     * @param adHocControlledMap AdHocControlledMap
     */
    public void setAdHocControlledMap(AdHocControlledMap adHocControlledMap)
    {
        this.adHocControlledMap = adHocControlledMap;
    }

    /**
     * getPromulgateNotifyNumber
     *
     * @return String
     */
    public String getPromulgateNotifyNumber()
    {
        return this.promulgateNotifyNumber;
    }

    /**
     * setPromulgateNotifyNumber
     *
     * @param number String
     */
    public void setPromulgateNotifyNumber(String number)
    {
        this.promulgateNotifyNumber = number;
    }

    /**
     * getPromulgateNotifyName
     *
     * @return String
     */
    public String getPromulgateNotifyName()
    {
        return this.promulgateNotifyName;
    }

    /**
     * setPromulgateNotifyName
     *
     * @param name String
     */
    public void setPromulgateNotifyName(String name)
    {
        this.promulgateNotifyName = name;
    }

    /**
     * getPromulgateNotifyType
     *
     * @return String
     */
    public String getPromulgateNotifyType()
    {
        return this.promulgateNotifyType;
    }

    /**
     * setPromulgateNotifyType
     *
     * @param type String
     */
    public void setPromulgateNotifyType(String type)
    {
        this.promulgateNotifyType = type;
    }

    /**
     * getPromulgateNotifyFlag
     *
     * @return String
     */
    public String getPromulgateNotifyFlag()
    {
        return this.promulgateNotifyFlag;
    }

    /**
     * setPromulgateNotifyFlag
     *
     * @param flag String
     */
    public void setPromulgateNotifyFlag(String flag)
    {
        this.promulgateNotifyFlag = flag;
    }

    /**
     * getPromulgateNotifyDescription
     *
     * @return String
     */
    public String getPromulgateNotifyDescription()
    {
        return this.promulgateNotifyDescription;
    }

    /**
     * setPromulgateNotifyDescription
     *
     * @param description String
     */
    public void setHasPromulgate(String has)
    {
        this.hasPromulgate = has;
    }

    /**
     * getPromulgate
     *
     * @return String
     */
    public String getHasPromulgate()
    {
        return this.hasPromulgate;
    }

    /**
     * setPromulgate
     *
     * @param description String
     */
    public void setPromulgateNotifyDescription(String description)
    {
        this.promulgateNotifyDescription = description;
    }

    /**
     * getCreator
     *
     * @return String
     */
    public String getCreator()
    {
        return creator;
    }

    /**
     * setCreator
     *
     * @param creator String
     */
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getDomain()
    {
        return getDomainAdministeredMap().getDomain();
    } //end getDomain()

    /**
     * ������
     * @param domain ��
     */
    public void setDomain(String domain)
    {
        getDomainAdministeredMap().setDomain(domain);
    }

    public DomainAdministeredMap getDomainAdministeredMap()
    {
        if(domainAdministeredMap == null)
        {
            setDomainAdministeredMap(new DomainAdministeredMap());
        }
        return domainAdministeredMap;
    } //end getDomainAdministeredMap()

    /**
     * ���������Map
     * @param map �����Map
     */
    public void setDomainAdministeredMap(DomainAdministeredMap map)
    {
        this.domainAdministeredMap = map;
    }

    /**
     * ���֪ͨ��Map
     * @return ֪ͨ��Map
     */
    public NotifiableMap getNotifiableMap()
    {
        if(notifiableMap == null)
        {
            setNotifiableMap(new NotifiableMap());
        }
        return notifiableMap;
    } //end getNotifiableMap()

    /**
     * ����֪ͨ��Map
     * @param map ֪ͨ��Map
     */
    public void setNotifiableMap(NotifiableMap map)
    {
        this.notifiableMap = map;
    }

    public String getEventSet()
    {
        return getNotifiableMap().getEventSet();
    } //end getEventSet()

    /**
     * �����¼���
     * @param eventSet
     */
    public void setEventSet(String eventSet)
    {
        getNotifiableMap().setEventSet(eventSet);
    }

    /**
     * ��ȡ��Ŀ��ID
     * @return ��Ŀ��ID
     */
    public String getProjectId()
    {
        return getProjectManagedMap().getProjectId();
    }

    /**
     * ������Ŀ��ID
     * @param projectId ��Ŀ��ID
     */
    public void setProjectId(String projectId)
    {
        getProjectManagedMap().setProjectId(projectId);
    }

    /**
     * ��ȡ��Ŀ�����Map
     * @return ��Ŀ�����Map
     */
    public ProjectManagedMap getProjectManagedMap()
    {
        if(projectManagedMap == null)
        {
            setProjectManagedMap(new ProjectManagedMap());
        }
        return projectManagedMap;
    }

    /**
     * ������Ŀ�����Map
     * @param map ��Ŀ�����Map
     */
    public void setProjectManagedMap(ProjectManagedMap map)
    {
        this.projectManagedMap = map;
    }

    /**
     * ��ȡ�������ڹ���Map
     * @return �������ڹ���Map
     */
    public LifeCycleManagedMap getLifeCycleManagedMap()
    {
        if(lifeCycleManagedMap == null)
        {
            setLifeCycleManagedMap(new LifeCycleManagedMap());
        }
        return lifeCycleManagedMap;
    }

    /**
     * �����������ڹ���Map
     * @param map �������ڹ���Map
     */
    public void setLifeCycleManagedMap(LifeCycleManagedMap map)
    {
        this.lifeCycleManagedMap = map;
    }

    /**
     * ���FolderBasedMap
     * @return FolderBasedMap
     */
    public FolderBasedMap getFolderBasedMap()
    {
        if(folderBasedMap == null)
        {
            setFolderBasedMap(new FolderBasedMap());
        }
        return folderBasedMap;
    } //end getFolderBasedMap()

    /**
     * ����FolderBasedMap
     * @param folderBasedMap
     */
    public void setFolderBasedMap(FolderBasedMap folderBasedMap)
    {
        this.folderBasedMap = folderBasedMap;
    } //
    //CCBegin SS1
    /**
     * ���״̬
     * @return ״̬
     */
    public LifeCycleState getLifeCycleState()
    {
        return getLifeCycleManagedMap().getLifeCycleState();
    } //end getLifecycleState()

    /**
     * ����״̬
     * @param state
     */
    public void setLifeCycleState(LifeCycleState state)
    {
        getLifeCycleManagedMap().setLifeCycleState(state);
    } //end setLifeCycleState(State)
    //CCEnd SS1
    /**
     * �ж����������ڹ���Ķ����Ƿ����������ڵĹؿ�
     * @return AtGate �����������ڵĹؿڵı�ʶ
     */
    public boolean getLifeCycleAtGate()
    {
        return getLifeCycleManagedMap().getLifeCycleAtGate();
    }

    /**
     * �������������ڹ���Ķ����Ƿ����������ڵĹؿ�
     * @param lifeCycleAtGate �����������ڵĹؿڵı�ʶ
     */
    public void setLifeCycleAtGate(boolean lifeCycleAtGate)
    {
        getLifeCycleManagedMap().setLifeCycleAtGate(lifeCycleAtGate);
    }

    /**
     * ��ȡ��������ģ���ID
     * @return �������ڵ�ģ���ID
     */
    public String getLifeCycleTemplate()
    {
        return getLifeCycleManagedMap().getLifeCycleTemplate();
    }

    /**
     * �����������ڵ�ģ��
     * @param lifeCycleTemplate ��������ģ��
     */
    public void setLifeCycleTemplate(String lifeCycleTemplate)
    {
        getLifeCycleManagedMap().setLifeCycleTemplate(lifeCycleTemplate);
    }

    /**
     * ��ȡ�������ڵĵ�ǰ�׶ε�Id
     * @return �������ڵĵ�ǰ�׶ε�Id
     */
    public String getCurrentPhaseId()
    {
        return getLifeCycleManagedMap().getCurrentPhaseId();
    }

    /**
     * �����������ڵĵ�ǰ�׶�
     * @param phaseId �������ڵĵ�ǰ�׶ε�Id
     */
    public void setCurrentPhaseId(String phaseId)
    {
        getLifeCycleManagedMap().setCurrentPhaseId(phaseId);
    }

    /**
     * ����ļ�λ��
     * @return �ļ�λ��
     */
    public String getLocation()
    {
        return getFolderBasedMap().getLocation();
    } //end getLocation()

    /**
     * �����ļ�λ��
     * @param location �ļ�λ��
     */
    public void setLocation(String location)
    {
        getFolderBasedMap().setLocation(location);
    } //end setLocation(String)

    public String getName()
    {
        return getFolderBasedMap().getName();
    } //end getName()

    /**
     * ��������
     * @param name ����
     */
    public void setName(String name)
    {
        getFolderBasedMap().setName(name);
    }

    public String getPath()
    {
        return getLocation() + "\\" + getName();
    }

    public String getIdentity()
    {
        return this.getPromulgateNotifyNumber() + "("
                + getPromulgateNotifyName() + ")";
    }

    //CCBeign SS1

	public void setLifeCycleState(String lifeCycleState0) {
	
		
	}
	//CCEnd SS1

//	public void setLifeCycleState(String arg0) {
//		// TODO Auto-generated method stub
//		
//	}
}
