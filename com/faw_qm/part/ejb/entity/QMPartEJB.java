/** 生成程序 QMPartEJB.java    1.0    2003/02/17
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.part.ejb.entity;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.ejb.CreateException;

import com.faw_qm.affixattr.ejb.entity.AttrContainer;
import com.faw_qm.content.ejb.entity.ContentItem;
import com.faw_qm.content.ejb.entity.DataFormat;
import com.faw_qm.content.ejb.service.ContentHolder;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.ejb.service.FormatContentHolder;
import com.faw_qm.enterprise.ejb.entity.RevisionControlledEJB;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.iba.value.util.AttributeContainer;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.part.util.Unit;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.util.EJBServiceHelper;


/**
 * 零部件。
 * @author 吴先超
 * @version 1.0
 */

public abstract class QMPartEJB extends RevisionControlledEJB
{
    private AttributeContainer theAttributeContainer;
    private String RESOURCE = "com.faw_qm.part.util.PartResource";


    /**
     * 获取part对应的partMaster值对象。
     * @return QMPartMasterInfo
     * @see QMPartMasterInfo
     */
    private QMPartMasterInfo getPartMaster()
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getPartMaster() begin ....");
        String id = getMasterBsoID();
        QMPartMasterInfo masterInfo = null;
        try
        {
            PersistService persistService = (PersistService) EJBServiceHelper.
                                            getService("PersistService");
            masterInfo = (QMPartMasterInfo) persistService.refreshInfo(id);
        }
        catch (QMException exception)
        {
            exception.printStackTrace();
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getPartMaster() end....return is QMPartMasterInfo");
        return masterInfo;
    }


    /**
     * 获得零部件名。
     * @return String 零部件名称
     */
    public String getPartName()
    {
        return getPartMaster().getPartName();
    }


    /**
     * 设置零部件名。用户只能通过QMPartMaster设定PartName。此处方法为空。
     * @param name String 零部件名称
     */
    public void setPartName(String name)
    {

    }


    /**
     * 获得零部件号。
     * @return String 零部件编号
     */
    public String getPartNumber()
    {
        return getPartMaster().getPartNumber();
    }


    /**
     * 设置零部件号，该方法为空。用户只能通过QMPartMaster来设定QMPart的PartNumber。
     * @param number String 零部件编号
     */
    public void setPartNumber(String number)
    {

    }


    /**
     * 获得零部件默认单位。
     * @return Unit 零部件单位
     * @see Unit
     */
    public Unit getDefaultUnit()
    {
        return getPartMaster().getDefaultUnit();

    }


    /**
     * 设置零部件默认的单位。不受CMP管理。
     * @param unit :Unit 枚举类型。
     * @see Unit
     */
    public void setDefaultUnit(Unit unit)
    {
        getPartMaster().setDefaultUnit(unit);
    }


    /**
     * 获得零部件来源。
     * @return String 零部件来源
     */
    public abstract String getProducedByStr();


    /**
     * 设置零部件来源。
     * @param producedBy String 零部件来源
     */
    public abstract void setProducedByStr(String producedBy);


    /**
     * 获取零部件来源。不受CMP管理.
     * @return ProducedBy 零部件来源
     * @see ProducedBy
     */
    public ProducedBy getProducedBy()
    {
        return ProducedBy.toProducedBy(getProducedByStr());
    }


    /**
     * 设置零部件来源，不受CMP管理
     * @param producedBy ProducedBy 零部件来源
     * @see ProducedBy
     */
    public void setProducedBy(ProducedBy producedBy)
    {
        setProducedByStr(producedBy.toString());
    }


    /**
     * 获得零部件类型。
     * @return String 零部件类型
     */
    public abstract String getPartTypeStr();


    /**
     * 设置零部件类型。
     * @param partType String 零部件类型
     */
    public abstract void setPartTypeStr(String partType);


    /**
     * 获取零部件类型。不受CMP管理。
     * @return QMPartType 零部件类型
     * @see QMPartType
     */
    public QMPartType getPartType()
    {
        return QMPartType.toQMPartType(getPartTypeStr());
    }


    /**
     * 设置零部件类型。不受CMP管理。
     * @param partType QMPartType 零部件类型
     * @see QMPartType
     */
    public void setPartType(QMPartType partType)
    {
        setPartTypeStr(partType.toString());
    }


    /**
     * 设置工作组名称。
     * @param projectName String 工作组名称
     */
    public void setProjectName(String projectName)
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "setProjectName(String) begin ....");
        //根据projectName , 找到值对象->bsoID, 设置bsoID.(setProjectID方法)
        if (projectName == null)
        {
            return;
        }
        try
        {
            QMQuery query = new QMQuery("Project");
            QueryCondition condition = new QueryCondition("name", "=",
                    projectName);
            query.addCondition(condition);
            PersistService pService = (PersistService) EJBServiceHelper.
                                      getPersistService();
            Collection collection = pService.findValueInfo(query);
            if ((collection == null) || (collection.size() == 0) ||
                (collection.size() > 1))
            {
                //"没有查找到(或者查找到了多个)相应的项目组，请确定输入项目组名是否正确"
                throw new PartException(RESOURCE, "8", null);
            }
            else
            {
                Iterator iterator = collection.iterator();
                ProjectIfc projectIfc = (ProjectIfc) iterator.next();
                setProjectId(projectIfc.getBsoID());
            }
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "setProjectName(String) end....return is void ");
    }


    /**
     * 获取工作组名称。
     * @return ProjectName 工作组名称
     */
    public String getProjectName()
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getProjectName() begin ....");
        //根据getProjectID()获取bsoID->恢复成值对象->getName()
        String tempString = getProjectId();
        //如果没有指定项目组，返回空的字符串。
        if (tempString == null)
        {
            return "";
        }
        ProjectIfc projectIfc = null;
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper.
                                      getPersistService();
            projectIfc = (ProjectIfc) pService.refreshInfo(tempString);
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getProjectName() end....return is String");
        if (projectIfc != null)
        {
            return projectIfc.getName();
        }
        else
        {
            return null;
        }
    }


    //下面两个方法实现ViewManageable：
    /**
     * 获取零部件视图名称。
     * @return String
     */
    public abstract String getViewName();


    /**
     * 设置零部件视图名称。
     * @param viewName String
     */
    public abstract void setViewName(String viewName);


    //下面需要实现ContextHolder中的方法。

    /**
     * 获取数据格式ID。
     * @return String
     */
    public abstract String getDataFormatID();


    /**
     * 设置数据格式ID。
     * @param data String
     */
    public abstract void setDataFormatID(String data);


    /**
     * 调用持久化服务,获取数据格式。
     * @return DataFormat 数据格式对象
     * @throws QMException
     * @see DataFormat
     */
    public DataFormat getDataFormat()
            throws QMException
    {
        if (getDataFormatID() != null)
        {
            PersistService service = (PersistService) EJBServiceHelper.
                                     getPersistService();
            return (DataFormat) service.refreshBso(getDataFormatID());
        }
        return null;
    }


    /**
     * 查看容器是否包含内容。
     * @return boolean
     * @throws QMException
     */
    public boolean isHasContents()
            throws QMException
    {
        ContentService service = (ContentService) EJBServiceHelper.getService(
                "ContentService");
        return service.isHasContent((ContentHolder) entityContext.
                                    getEJBLocalObject());
    }


    /**
     * 获取容器关联的所有内容。
     * @return Vector 内容（ContentItemInfo）集合
     * @throws QMException
     */
    public Vector getContentVector()
            throws QMException
    {
        ContentService service = (ContentService) EJBServiceHelper.getService(
                "ContentService");
        return service.getContents((ContentHolder) entityContext.
                                   getEJBLocalObject());
    }


    /**
     * 设置数据流格式。
     * @param dformat DataFormat
     */
    public void setDataFormat(DataFormat dformat)
    {
        if (dformat != null)
        {
            setDataFormatID(dformat.getBsoID());
        }
    }


    /**
     * 获取容器中的数据项。
     * @return ContentItem 内容项
     * @throws QMException
     * @see ContentItem
     */
    public ContentItem getPrimary()
            throws QMException
    {
        ContentService service = (ContentService) EJBServiceHelper.getService(
                "ContentService");
        return service.getPrimaryContent((FormatContentHolder) entityContext.
                                         getEJBLocalObject());
    }


    /**
     * 获取有效性值的集合,实现EffManagedVersion中的方法，该处方法体为空。
     * @return null
     */
    public Vector getEffVector()
    {
        return null;
    }


    /**
     * 设置零部件的有效性值的集合，实现父类EffManagedVersion中的方法，该处方法体为空。
     * @param vector Vector
     */
    public void setEffVector(Vector vector)
    {
    }


    /**
     * 构造函数。
     */
    public QMPartEJB()
    {
        super();
    }


    /**
     * 获取业务类名。
     * @return "QMPart".
     */
    public String getBsoName()
    {
        return "QMPart";
    }


    /**
     * 设置是否是颜色零件标识。
     * @param flag 颜色零件标识。
     */
    abstract public void setColorFlag(boolean flag);


    /**
     * 得到附加属性容器的ID。
     * @return String
     */
    public abstract String getAttrContID();


    /**
     * 设置附加属性容器ID。
     * @param contID String
     */
    public abstract void setAttrContID(String contID);


    /**
     * 得到附加属性容器对象。
     * @return AttrContainer 附加属性容器
     * @see AttrContainer
     */
    public AttrContainer getAttrContainer()
    {
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper.
                                      getService("PersistService");
            return (AttrContainer) pService.refreshBso(getAttrContID());
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * 设置附加属性容器对象。
     * @param attrCont AttrContainer 附加属性容器对象
     * @see AttrContainer
     */
    public void setAttrContainer(AttrContainer attrCont)
    {
        setAttrContID(attrCont.getBsoID());
    }


    /**
     * 获取是否是颜色零件标识。
     * @return 颜色零件标识。
     */
    abstract public boolean getColorFlag();
    /**
     * 获取选装代码
     * @return String 选装代码
     */
    public abstract String getOptionCode();
    /**
     * 设置选装代码
     * @param code String
     */
    public abstract void setOptionCode(String code);


    /**
     * 获取业务对象对应的值对象。
     * @return BaseValueIfc
     * @throws QMException
     * @see BaseValueIfc
     */
    public BaseValueIfc getValueInfo()
            throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getValueInfo() begin ....");
        QMPartInfo info = new QMPartInfo();
        setValueInfo(info);
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getValueInfo() end....return is BaseValueIfc");
        return info;
    }


    /**
     * 设置业务对象对应的值对象。
     * @param info BasevalueIfc
     * @throws QMException
     * @see BaseValueIfc
     */
    public void setValueInfo(BaseValueIfc info)
            throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "setValueInfo(BaseValueIfc) begin ....");
        QMPartMasterInfo master=getPartMaster();
        super.setValueInfo(info);
        QMPartInfo pinfo = (QMPartInfo) info;
        pinfo.setPartName(master.getPartName());
        pinfo.setPartNumber(master.getPartNumber());
        pinfo.setName(master.getPartName());
        pinfo.setDefaultUnit(master.getDefaultUnit());
        pinfo.setProducedBy(getProducedBy());
        pinfo.setPartType(getPartType());
        pinfo.setViewName(getViewName());
        pinfo.setColorFlag(getColorFlag());
        pinfo.setDataFormatID(getDataFormatID());
        if (getDataFormatID() != null)
        {
            pinfo.setDataFormatName(getDataFormatName());
        }
        //CRJF Begin zhangq
        //pinfo.setMaster(getPartMaster());
        pinfo.setMaster(master);
        //CRJF End zhangq
        pinfo.setProjectDisplayName(getProjectName());
        pinfo.setOptionCode(getOptionCode());
        //pinfo.setAttrContID(getAttrContID());
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "setValueInfo(BaseValueIfc) end....return is void");
    }


    /**
     * 根据值对象创建业务对象。
     * @param info BaseValueIfc
     * @throws CreateException
     * @see BaseValueIfc
     */
    public void createByValueInfo(BaseValueIfc info)
            throws CreateException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "createByValueInfo(BaseValueIfc) begin ....");
        super.createByValueInfo(info);
        QMPartInfo pinfo = (QMPartInfo) info;
        setPartName(pinfo.getPartName());
        setPartNumber(pinfo.getPartNumber());
        setName(pinfo.getPartName());
        setDefaultUnit(pinfo.getDefaultUnit());
        setProducedBy(pinfo.getProducedBy());
        setPartType(pinfo.getPartType());
        setViewName(pinfo.getViewName());
        setDataFormatID(pinfo.getDataFormatID());
        setColorFlag(pinfo.getColorFlag());
        setOptionCode(pinfo.getOptionCode());
        //setAttrContID(pinfo.getAttrContID());
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "createByValueInfo(BaseValueIfc) end....return is void");
    }


    /**
     * 根据值对象更新业务对象。
     * @param info BaseValueIfc
     * @throws QMException
     * @see BaseValueIfc
     */
    public void updateByValueInfo(BaseValueIfc info)
            throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "updateByValueInfo(BaseValueIfc) begin ....");
        super.updateByValueInfo(info);
        QMPartInfo pinfo = (QMPartInfo) info;
        setPartName(pinfo.getPartName());
        setPartNumber(pinfo.getPartNumber());
        setName(pinfo.getPartName());
        setDefaultUnit(pinfo.getDefaultUnit());
        setProducedBy(pinfo.getProducedBy());
        setPartType(pinfo.getPartType());
        setViewName(pinfo.getViewName());
        setDataFormatID(pinfo.getDataFormatID());
        setColorFlag(pinfo.getColorFlag());
        setOptionCode(pinfo.getOptionCode());
        //setAttrContID(pinfo.getAttrContID());
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "updateByValueInfo(BaseValueIfc) end....return is void ");
    }


    /**
     * 获取数据流格式名。
     * @return String
     */
    public String getDataFormatName()
    {
        try
        {
            DataFormat df = getDataFormat();
            if (df != null)
            {
                return df.getFormatName();
            }
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
	/**
	 * 获取事物特性属性容器
	 * @return AttributeContainer 事物特性属性容器
	 *@see AttributeContainer
	 */
    public AttributeContainer getAttributeContainer()
    {
        return theAttributeContainer;
    }
   /**
    * 设置事物特性属性容器
    * @param attributecontainer 事物特性属性容器
    *@see AttributeContainer
    */
    public void setAttributeContainer(AttributeContainer attributecontainer)
    {
        theAttributeContainer = attributecontainer;
    }
    /**
     * 获取一个字符串标识。
     * @return String 形如："QMPart" + PartName + PartNumber;
   
     */
    public String getIBAID()
    {
        String partIden = "QMPart" + this.getPartName() + this.getPartNumber();
        return partIden;
    }
}
