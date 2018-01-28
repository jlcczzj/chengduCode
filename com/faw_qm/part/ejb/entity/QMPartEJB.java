/** ���ɳ��� QMPartEJB.java    1.0    2003/02/17
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * �㲿����
 * @author ���ȳ�
 * @version 1.0
 */

public abstract class QMPartEJB extends RevisionControlledEJB
{
    private AttributeContainer theAttributeContainer;
    private String RESOURCE = "com.faw_qm.part.util.PartResource";


    /**
     * ��ȡpart��Ӧ��partMasterֵ����
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
     * ����㲿������
     * @return String �㲿������
     */
    public String getPartName()
    {
        return getPartMaster().getPartName();
    }


    /**
     * �����㲿�������û�ֻ��ͨ��QMPartMaster�趨PartName���˴�����Ϊ�ա�
     * @param name String �㲿������
     */
    public void setPartName(String name)
    {

    }


    /**
     * ����㲿���š�
     * @return String �㲿�����
     */
    public String getPartNumber()
    {
        return getPartMaster().getPartNumber();
    }


    /**
     * �����㲿���ţ��÷���Ϊ�ա��û�ֻ��ͨ��QMPartMaster���趨QMPart��PartNumber��
     * @param number String �㲿�����
     */
    public void setPartNumber(String number)
    {

    }


    /**
     * ����㲿��Ĭ�ϵ�λ��
     * @return Unit �㲿����λ
     * @see Unit
     */
    public Unit getDefaultUnit()
    {
        return getPartMaster().getDefaultUnit();

    }


    /**
     * �����㲿��Ĭ�ϵĵ�λ������CMP����
     * @param unit :Unit ö�����͡�
     * @see Unit
     */
    public void setDefaultUnit(Unit unit)
    {
        getPartMaster().setDefaultUnit(unit);
    }


    /**
     * ����㲿����Դ��
     * @return String �㲿����Դ
     */
    public abstract String getProducedByStr();


    /**
     * �����㲿����Դ��
     * @param producedBy String �㲿����Դ
     */
    public abstract void setProducedByStr(String producedBy);


    /**
     * ��ȡ�㲿����Դ������CMP����.
     * @return ProducedBy �㲿����Դ
     * @see ProducedBy
     */
    public ProducedBy getProducedBy()
    {
        return ProducedBy.toProducedBy(getProducedByStr());
    }


    /**
     * �����㲿����Դ������CMP����
     * @param producedBy ProducedBy �㲿����Դ
     * @see ProducedBy
     */
    public void setProducedBy(ProducedBy producedBy)
    {
        setProducedByStr(producedBy.toString());
    }


    /**
     * ����㲿�����͡�
     * @return String �㲿������
     */
    public abstract String getPartTypeStr();


    /**
     * �����㲿�����͡�
     * @param partType String �㲿������
     */
    public abstract void setPartTypeStr(String partType);


    /**
     * ��ȡ�㲿�����͡�����CMP����
     * @return QMPartType �㲿������
     * @see QMPartType
     */
    public QMPartType getPartType()
    {
        return QMPartType.toQMPartType(getPartTypeStr());
    }


    /**
     * �����㲿�����͡�����CMP����
     * @param partType QMPartType �㲿������
     * @see QMPartType
     */
    public void setPartType(QMPartType partType)
    {
        setPartTypeStr(partType.toString());
    }


    /**
     * ���ù��������ơ�
     * @param projectName String ����������
     */
    public void setProjectName(String projectName)
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "setProjectName(String) begin ....");
        //����projectName , �ҵ�ֵ����->bsoID, ����bsoID.(setProjectID����)
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
                //"û�в��ҵ�(���߲��ҵ��˶��)��Ӧ����Ŀ�飬��ȷ��������Ŀ�����Ƿ���ȷ"
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
     * ��ȡ���������ơ�
     * @return ProjectName ����������
     */
    public String getProjectName()
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "getProjectName() begin ....");
        //����getProjectID()��ȡbsoID->�ָ���ֵ����->getName()
        String tempString = getProjectId();
        //���û��ָ����Ŀ�飬���ؿյ��ַ�����
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


    //������������ʵ��ViewManageable��
    /**
     * ��ȡ�㲿����ͼ���ơ�
     * @return String
     */
    public abstract String getViewName();


    /**
     * �����㲿����ͼ���ơ�
     * @param viewName String
     */
    public abstract void setViewName(String viewName);


    //������Ҫʵ��ContextHolder�еķ�����

    /**
     * ��ȡ���ݸ�ʽID��
     * @return String
     */
    public abstract String getDataFormatID();


    /**
     * �������ݸ�ʽID��
     * @param data String
     */
    public abstract void setDataFormatID(String data);


    /**
     * ���ó־û�����,��ȡ���ݸ�ʽ��
     * @return DataFormat ���ݸ�ʽ����
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
     * �鿴�����Ƿ�������ݡ�
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
     * ��ȡ�����������������ݡ�
     * @return Vector ���ݣ�ContentItemInfo������
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
     * ������������ʽ��
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
     * ��ȡ�����е������
     * @return ContentItem ������
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
     * ��ȡ��Ч��ֵ�ļ���,ʵ��EffManagedVersion�еķ������ô�������Ϊ�ա�
     * @return null
     */
    public Vector getEffVector()
    {
        return null;
    }


    /**
     * �����㲿������Ч��ֵ�ļ��ϣ�ʵ�ָ���EffManagedVersion�еķ������ô�������Ϊ�ա�
     * @param vector Vector
     */
    public void setEffVector(Vector vector)
    {
    }


    /**
     * ���캯����
     */
    public QMPartEJB()
    {
        super();
    }


    /**
     * ��ȡҵ��������
     * @return "QMPart".
     */
    public String getBsoName()
    {
        return "QMPart";
    }


    /**
     * �����Ƿ�����ɫ�����ʶ��
     * @param flag ��ɫ�����ʶ��
     */
    abstract public void setColorFlag(boolean flag);


    /**
     * �õ���������������ID��
     * @return String
     */
    public abstract String getAttrContID();


    /**
     * ���ø�����������ID��
     * @param contID String
     */
    public abstract void setAttrContID(String contID);


    /**
     * �õ�����������������
     * @return AttrContainer ������������
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
     * ���ø���������������
     * @param attrCont AttrContainer ����������������
     * @see AttrContainer
     */
    public void setAttrContainer(AttrContainer attrCont)
    {
        setAttrContID(attrCont.getBsoID());
    }


    /**
     * ��ȡ�Ƿ�����ɫ�����ʶ��
     * @return ��ɫ�����ʶ��
     */
    abstract public boolean getColorFlag();
    /**
     * ��ȡѡװ����
     * @return String ѡװ����
     */
    public abstract String getOptionCode();
    /**
     * ����ѡװ����
     * @param code String
     */
    public abstract void setOptionCode(String code);


    /**
     * ��ȡҵ������Ӧ��ֵ����
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
     * ����ҵ������Ӧ��ֵ����
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
     * ����ֵ���󴴽�ҵ�����
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
     * ����ֵ�������ҵ�����
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
     * ��ȡ��������ʽ����
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
	 * ��ȡ����������������
	 * @return AttributeContainer ����������������
	 *@see AttributeContainer
	 */
    public AttributeContainer getAttributeContainer()
    {
        return theAttributeContainer;
    }
   /**
    * ��������������������
    * @param attributecontainer ����������������
    *@see AttributeContainer
    */
    public void setAttributeContainer(AttributeContainer attributecontainer)
    {
        theAttributeContainer = attributecontainer;
    }
    /**
     * ��ȡһ���ַ�����ʶ��
     * @return String ���磺"QMPart" + PartName + PartNumber;
   
     */
    public String getIBAID()
    {
        String partIden = "QMPart" + this.getPartName() + this.getPartNumber();
        return partIden;
    }
}
