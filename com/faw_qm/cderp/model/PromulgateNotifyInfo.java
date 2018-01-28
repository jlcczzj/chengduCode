/**
 * 生成程序PromulgateNotifyInfo.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 青气升级成都兼容问题  liuzhicheng 2012-01-01
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
 * <p>Title: 采用通知书实现</p>
 * <p>Description:采用通知书实现  </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public class PromulgateNotifyInfo extends BaseValueInfo implements
        PromulgateNotifyIfc
{
    static final long serialVersionUID = -1L;

    private DomainAdministeredMap domainAdministeredMap;

    //域管理
    private LifeCycleManagedMap lifeCycleManagedMap;

    /**
     * 访问控制Map
     */
    private AdHocControlledMap adHocControlledMap;

    //项目组管理Map
    private ProjectManagedMap projectManagedMap;

    private NotifiableMap notifiableMap;

    private FolderBasedMap folderBasedMap;

    //名称
    private String promulgateNotifyName;

    //标号
    private String promulgateNotifyNumber;

    private String promulgateNotifyType;

    //标记
    private String promulgateNotifyFlag;

    private String promulgateNotifyDescription;

    //创建者
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
     * 获得访问控制列表
     * @return 访问控制列表
     */
    public AdHocAcl getAdHocAcl()
    {
        return getAdHocControlledMap().getAdHocAcl();
    } //end getAdHocAcl()

    /**
     * 设置访问控制列表
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
     * 设置域
     * @param domain 域
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
     * 设置域管理Map
     * @param map 域管理Map
     */
    public void setDomainAdministeredMap(DomainAdministeredMap map)
    {
        this.domainAdministeredMap = map;
    }

    /**
     * 获得通知者Map
     * @return 通知者Map
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
     * 设置通知者Map
     * @param map 通知者Map
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
     * 设置事件集
     * @param eventSet
     */
    public void setEventSet(String eventSet)
    {
        getNotifiableMap().setEventSet(eventSet);
    }

    /**
     * 获取项目组ID
     * @return 项目组ID
     */
    public String getProjectId()
    {
        return getProjectManagedMap().getProjectId();
    }

    /**
     * 设置项目组ID
     * @param projectId 项目组ID
     */
    public void setProjectId(String projectId)
    {
        getProjectManagedMap().setProjectId(projectId);
    }

    /**
     * 获取项目组管理Map
     * @return 项目组管理Map
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
     * 设置项目组管理Map
     * @param map 项目组管理Map
     */
    public void setProjectManagedMap(ProjectManagedMap map)
    {
        this.projectManagedMap = map;
    }

    /**
     * 获取生命周期管理Map
     * @return 生命周期管理Map
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
     * 设置生命周期管理Map
     * @param map 生命周期管理Map
     */
    public void setLifeCycleManagedMap(LifeCycleManagedMap map)
    {
        this.lifeCycleManagedMap = map;
    }

    /**
     * 获得FolderBasedMap
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
     * 设置FolderBasedMap
     * @param folderBasedMap
     */
    public void setFolderBasedMap(FolderBasedMap folderBasedMap)
    {
        this.folderBasedMap = folderBasedMap;
    } //
    //CCBegin SS1
    /**
     * 获得状态
     * @return 状态
     */
    public LifeCycleState getLifeCycleState()
    {
        return getLifeCycleManagedMap().getLifeCycleState();
    } //end getLifecycleState()

    /**
     * 设置状态
     * @param state
     */
    public void setLifeCycleState(LifeCycleState state)
    {
        getLifeCycleManagedMap().setLifeCycleState(state);
    } //end setLifeCycleState(State)
    //CCEnd SS1
    /**
     * 判断受生命周期管理的对象是否处于生命周期的关口
     * @return AtGate 处于生命周期的关口的标识
     */
    public boolean getLifeCycleAtGate()
    {
        return getLifeCycleManagedMap().getLifeCycleAtGate();
    }

    /**
     * 设置受生命周期管理的对象是否处于生命周期的关口
     * @param lifeCycleAtGate 处于生命周期的关口的标识
     */
    public void setLifeCycleAtGate(boolean lifeCycleAtGate)
    {
        getLifeCycleManagedMap().setLifeCycleAtGate(lifeCycleAtGate);
    }

    /**
     * 获取生命周期模板的ID
     * @return 生命周期的模板的ID
     */
    public String getLifeCycleTemplate()
    {
        return getLifeCycleManagedMap().getLifeCycleTemplate();
    }

    /**
     * 设置生命周期的模板
     * @param lifeCycleTemplate 生命周期模板
     */
    public void setLifeCycleTemplate(String lifeCycleTemplate)
    {
        getLifeCycleManagedMap().setLifeCycleTemplate(lifeCycleTemplate);
    }

    /**
     * 获取生命周期的当前阶段的Id
     * @return 生命周期的当前阶段的Id
     */
    public String getCurrentPhaseId()
    {
        return getLifeCycleManagedMap().getCurrentPhaseId();
    }

    /**
     * 设置生命周期的当前阶段
     * @param phaseId 生命周期的当前阶段的Id
     */
    public void setCurrentPhaseId(String phaseId)
    {
        getLifeCycleManagedMap().setCurrentPhaseId(phaseId);
    }

    /**
     * 获得文件位置
     * @return 文件位置
     */
    public String getLocation()
    {
        return getFolderBasedMap().getLocation();
    } //end getLocation()

    /**
     * 设置文件位置
     * @param location 文件位置
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
     * 设置名字
     * @param name 名字
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
