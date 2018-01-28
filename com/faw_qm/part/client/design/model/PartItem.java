/**
 * ���ɳ��� PartItem.java    1.0    2003/02/20
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/02 л�� ԭ��:���ӻ�ȡ�Ӽ����ݼ��ϵķ�����Ϊ���湹��UsesInterfaceListʹ��
 *                     ����:�Ż���ʼ��ʹ�ýṹ������
 *                     ��ע:���v3r11-չ���㲿�����ڵ������Ż�
 */
package com.faw_qm.part.client.design.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.clients.beans.explorer.QMBusinessObject;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.part.util.Unit;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.QMCt;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.viewmanage.util.ViewHelper;
import com.faw_qm.wip.util.WorkInProgressHelper;

/*
 * PartItem
 * ��һ��ģ������������QMPart������������������QMPart������Ժ͹�ϵ�İ�����������
 * �̳�QMBusinessObject��������QMExplorer�н��м򵥵Ĳ�����
 * ��QMBusinessObject��̳����ķ�����
 * getAttributeValue, getAttributeValue,
 * getContents, getObject, getOpenIcon,
 * getStandardIcon, setIdentity, setObject,
 * setObject, setStandardIcon, setStandardIcon
 */
//�����������У����㲿���ĸ������ԵĲ�������????���ݷ������⣬ �Լ���������QMPart���е����е�
//�־û��ֶεĸ�ֵ������ά����������???????? 2003.04.04
/**
 * ģ���࣬������װ�㲿��ֵ����
 * @author ���ȳ�
 * @version 1.0
 */
//����(1)20081014 ��ǿ�޸� �޸�ԭ��:���մ����㲿��ʱ������״̬Ӧ����ʾΪ��δ���롱����TD-1868��
public class PartItem extends QMBusinessObject
{
	/**���л�ID*/
	static final long serialVersionUID = 1L;
	/**�󶨵���Դ��Ϣ*/
	private static ResourceBundle resource = null;

	/**�ͻ�����Դ�ļ�·��*/
	private static final String RESOURCE = "com.faw_qm.part.client.design.util.PartDesignViewRB";

	/**�㲿����������ֵ�������ﶨ����Ƿ�װ��ConfigSpecItem�еĶ�������װ�����������Ϣ*/
	private ConfigSpecItem configSpecItem = null;

	/**�㲿���Ƿ��޸ĵı�ʶ*/
	private boolean modified = false;

	/**�Ƿ����´������㲿���ı�ʶ*/
	private transient boolean isCreate = false;

	/**�㲿�����*/
	private String number = null;

	/**�㲿�����ڵ���ͼ����*/
	private transient String viewName = null;

	/**�㲿�����Ǹ���ͼ�������*/
	private transient ViewObjectIfc viewObjectIfc = null;

	/**�㲿������*/
	private String name = null;

	/**�޶����㲿���Ĵ�汾*/
	private String revision = null;

	/**��汾�š����磺A.1.B.2�еĴ�汾����:A.1.B*/
        //��version����static����ȥ����ԭ���������̬���ԣ����汾�޷����� cdc 20071016
	private  String version = null;

	/**����С�汾(�����)�����磺A.1.B.2�еİ�����ǣ�2*/
	private String iteration = null;

	/**�����İ汾+����ֵ*/
	private transient String iterationID = null;

	/**��������״̬*/
	private String state = null;

	/**�㲿�����ڵĹ�����*/
	private transient String project = null;

	/**��������ģ������*/
	private transient String lifecycle = null;

	/**�㲿����λ��ö������*/
	private transient Unit unitOfMeasure = null;

	/**�㲿�����͡�ö������*/
	private QMPartType type = null;

	/**�㲿����Դ��ö������*/
	private ProducedBy source = null;

	/**�㲿�������ļ���λ��*/
	private String location = null;

	/**�㲿�������ļ���������·��*/
	public transient String path = null;

	/**�㲿�������ļ���ֵ����*/
	private FolderIfc folderIfc = null;

	/**���������㲿��ʹ�ù�ϵ�ļ��ϵķ�װ�����*/
	private UsesInterfaceList usesInterface = null;

	/**��¼�㲿���ο���ϵ���б�*/
	private ReferencesList references = null;

	/**��¼�㲿��ʹ�ýṹ���б�*/
	private UsedByList usedBy = null;

	/**��QMPart + ͼ����, ��Ӧ��ͼ�����·����*/
	public transient Map iconNameCache = new HashMap();

    /** ��ǰ�㲿����ʹ���Ӽ����ϡ� */
    private Explorable[] explorableArray = null;
//	  CCBegin by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼ 	
    private String routeText;
//	  CCEnd by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼ 
	/**
	 * ����һ���µ�PartItem ����
	 * �����㲿��Ĭ�ϵ�����:SEPARABLE��
	 * Ĭ�ϵ���Դ:MAKE��
	 * ���ҵ���QMPartIfc����RevisionControlledIfc�еķ���:initialize()��
	 * @throws QMRemoteException
	 */
	public PartItem() throws QMRemoteException
	{
		super();
		modified = false;
        explorableArray = null;
		try
		{
			QMPartIfc partIfc = new QMPartInfo();
			setPart(partIfc);
		}
		catch (QMException ex)
		{
			throw new QMRemoteException(ex);
		}
	}

	/**
	 * ����һ���µ�PartItem ����
	 * @param partIfc QMPartIfc �㲿��ֵ����
	 * @see QMPartInfo
	 */
	public PartItem(QMPartIfc partIfc)
	{
		//���ø���ķ��������㲿���ı����Ϊ�㲿���ı�ʶ��
		super(partIfc.getPartNumber(), partIfc);
		modified = false;
		setPart(partIfc);
	}

	/**
	 * �����㲿��ֵ�������Ϣ��
	 * @param partIfc QMPartIfc
	 * @see QMPartInfo
	 */
	public void setPart(final QMPartIfc partIfc)
	{
		setPartSimply(partIfc);
		try
		{
			initPart();
			return;
		}
		catch (QMRemoteException qmexception)
		{
		}
	}

	/**
	 * ����QMPartIfc partIfc �����˸���ķ�����
	 * @param partIfc QMPartIfc
	 * @see QMPartInfo
	 */
	public void setPartSimply(final QMPartIfc partIfc)
	{
		super.setObject(partIfc);
	}

	/**
	 * ��ʼ���㲿��ֵ����
	 * @throws QMRemoteException
	 */
	protected void initPart() throws QMRemoteException
	{
		PartDebug.debug("initPart() begin ....", this, PartDebug.PART_CLIENT);
		if(!PersistHelper.isPersistent(getPart()))
		{
			setNew(true);
		}
		QMPartIfc part = getPart();
		number = part.getPartNumber();
		name = part.getPartName();
		if(!isCreate)
		{
			version = part.getVersionID();
			revision = version;
			iterationID = part.getVersionValue();
			iteration = part.getIterationID();
			viewName = part.getViewName();
			viewObjectIfc = (ViewObjectIfc) ViewHelper.getView(part);
		}
		type = part.getPartType();
		source = part.getProducedBy();
		state = PartHelper.getLifeCycleState(part);
		//lifecycle = PartHelper.getLifeCycle(part);
		project = PartHelper.getProject(part);
		unitOfMeasure = part.getDefaultUnit();
		setModified(false);
		PartDebug.debug("initPart() " + "end....return is void", this,
				PartDebug.PART_CLIENT);
	}

	/**
	 * ��ȡ�㲿��ֵ����
	 * @return String
	 * @see QMPartInfo
	 */
	public QMPartIfc getPart()
	{
		return (QMPartIfc) getObject();
	}

	/**
	 * ���������
	 * @param configSpecItem1 ConfigSpecItem ���ù淶��װ��
	 * @see ConfigSpecItem
	 */
	public void setConfigSpecItem(final ConfigSpecItem configSpecItem1)
	{
		configSpecItem = configSpecItem1;
	}

	/**
	 * ��ȡ������(configSpecItem)����ΪConfigSpecItem��
	 * @return ConfigSpecItem
	 * @see ConfigSpecItem
	 */
	public ConfigSpecItem getConfigSpecItem()
	{
		return configSpecItem;
	}

	/**
	 * �������PartItem�����Ƿ�Ϊ�µ�һ���µ�ֵ�����㲿���Ƿ����½��ġ�
	 * @param flag boolean
	 */
	public void setNew(final boolean flag)
	{
		isCreate = flag;
	}

	/**
	 * ��ȡ���PartItem�����Ƿ����µı�ʶ��
	 * @return boolean
	 */
	public boolean isNew()
	{
		return isCreate;
	}

	/**
	 * �������PartItem�����Ƿ�ı�ı�ʶ��
	 * @param modified1 boolean trueΪ�ı�
	 */
	protected void setModified(final boolean modified1)
	{
		modified = modified1;
	}

	/**
	 * ��ȡ���PartItem�����Ƿ񱻸ı�ı�ʶ��
	 * @return boolean
	 */
	public boolean isModified()
	{
		return modified;
	}

	/**
	 * Ϊ���partItem����һ���µı��������㲿����
	 * @throws QMRemoteException
	 */
	public void create() throws QMRemoteException
	{
		try
		{
			//���´������㲿��ֵ�������������ID��ֵ��
			final QMPartIfc partIfc = new QMPartInfo();
			setPart(partIfc);
		}
		catch (QMException ex)
		{
			throw new QMRemoteException(ex);
		}
		setModified(true);
	}

	/**
	 * ����PartItem�е�PartName, PartNumber, �ļ���, ��ͼ�����ͣ���Դ����λ��
	 * @throws QMRemoteException
	 */
	public void setFields() throws QMRemoteException
	{
		PartDebug.debug("setFields() begin ....", this, PartDebug.PART_CLIENT);
		if(isNew())
		{
			try
			{
				//���´������㲿��ֵ�������������ID��ֵ��
				QMPartIfc partIfc = this.getPart();
				if(null == partIfc)
				{
					partIfc = new QMPartInfo();
				}
				setPartSimply(partIfc);
                //CCBegin by liunan 2008-07-25
                //���㲿��������д�дת�����ٽ��б��档
//				ԭ��������
				getPart().setPartNumber(number);                
                getPart ().setPartNumber ( number.toUpperCase() ) ;
                //CCEnd by liunan 2008-07-25
				
				getPart().setPartName(name);
				//�������͵ļ��ϡ�
				final Class[] paraClass2 = {FolderEntryIfc.class, String.class};
				//����ֵ�ļ��ϡ�
				final Object[] objs2 = {getPart(), location};
				//actor1�����ȡֵ��
				final FolderEntryIfc view1 = (FolderEntryIfc) IBAUtility
						.invokeServiceMethod("FolderService", "assignFolder",
								paraClass2, objs2);
				//�ѻ�ȡֵactor1ת��Ϊ�ַ�����
				setObject((QMPartIfc) view1);
				if(null != viewObjectIfc)
				{
					ViewHelper.assignToView(getPart(),
							(ViewObjectIfc) viewObjectIfc);
				}
			}
			catch (QMException ex)
			{
				throw new QMRemoteException(ex);
			}
		}
		//end if(isNew())
		if(null != unitOfMeasure)
		{
			getPart().setDefaultUnit(unitOfMeasure);
		}
		if(isModified())
		{
			if(null != type)
			{
				getPart().setPartType(type);
			}
			if(null != source)
			{
				getPart().setProducedBy(source);
			}
		}
		//end if(isModified())
		PartDebug.debug("setFields() end....return is void ", this,
				PartDebug.PART_CLIENT);
	}

	/**
	 * �����㲿����
	 * @throws QMRemoteException
	 */
	public void update() throws QMRemoteException
	{
		PartDebug.debug("udpate() begin ....", this, PartDebug.PART_CLIENT);
		if(isModified())
		{
			try
			{
				if(!isCreate)
				{
					final Class[] paraClass = {BaseValueIfc.class};
					final Object[] objs = {getPart()};
					QMPartIfc partIfc = null;
					try
					{
						partIfc = (QMPartIfc) IBAUtility.invokeServiceMethod(
								"PersistService", "refreshInfo", paraClass,
								objs);
					}
					catch (QMRemoteException ex1)
					{
					}
					if(getPart().getModifyTime()
							.before(partIfc.getModifyTime()))
					{
						setPart(partIfc);
					}
				}
				setPartSimply(PartHelper.savePart(getPart()));
				iterationID = getPart().getVersionValue();
				setModified(false);
				PartDebug.debug("update() end....return is null ", this,
						PartDebug.PART_CLIENT);
			}
			catch (QMRemoteException qmexception)
			{
				throw qmexception;
			}
		}
		//����޸ı�ʾû�иı�Ļ���˵��û�жԵ�ǰ���㲿�����£�ֱ�ӻ�ȡ���������κεĲ�����
		else
		{
			PartDebug.debug("update() end....return is null", this,
					PartDebug.PART_CLIENT);
		}
	}

	/**
	 * ɾ�����partItem��һ���������㲿����
	 * @throws QMRemoteException
	 */
	public void delete() throws QMRemoteException
	{
		try
		{
			PartHelper.deletePart(getPart());
			//�ѿͻ��˵��㲿��ֵ����ɾ����
			setPartSimply(null);
			return;
		}
		catch (QMRemoteException qmexception)
		{
			throw qmexception;
		}
	}

	/**
	 * �����޸�Ϊ���������㲿������һ��ˢ���¼���
	 */
	public void dispatchRefresh()
	{
		PartDebug.debug("dispatchRefresh() begin ....", this,
				PartDebug.PART_CLIENT);
		if(isNew())
		{
			RefreshService.getRefreshService().dispatchRefresh(this, 0,
					getPart());
			setNew(false);
			PartDebug.debug("dispatchRefresh() end....return is null", this,
					PartDebug.PART_CLIENT);
		}
		else
		{
			RefreshService.getRefreshService().dispatchRefresh(this, 1,
					getPart());
			PartDebug.debug("dispatchRefresh() end....return is null", this,
					PartDebug.PART_CLIENT);
		}
	}

	/**
	 * �����㲿���ı�š�
	 * @param number1 String
	 */
	public void setNumber(final String number1)
	{
        //CCBegin by liunan 2008-07-25
        //���㲿��������д�дת�����ٴ���
        //ԭ��������
        //number = number1;
        number = number1.toUpperCase() ;
        //CCEnd by liunan 2008-07-25
		setModified(true);
	}

	/**
	 * ��ȡ�㲿����š�
	 * @return String
	 */
	public String getNumber()
	{
        //CCBegin by liunan 2008-07-25
        //���㲿��������д�дת�����ٷ��ء�
        //ԭ��������
        //return number;
        if(number==null)
        {
          return null;
        }
        else
          return number.toUpperCase() ;
        //CCEnd by liunan 2008-07-25
	}

	/**
	 * ��ȡ�㲿����š�
	 * @return String
	 */
	public String getObjectNumber()
	{
        //CCBegin by liunan 2008-07-25
        //���㲿��������д�дת�����ٷ��ء�
        //ԭ��������
        //return number;
        return number.toUpperCase() ;
        //CCEnd by liunan 2008-07-25
	}

	/**
	 * �����㲿�����ڵ��ļ��С�
	 * @param folderIfc1 :FolderIfc �ļ���ֵ����
	 * @see FolderIfc
	 */
	public void setFolder(final FolderIfc folderIfc1)
	{
		folderIfc = folderIfc1;
	}

	/**
	 * ��ȡ�㲿�����ڵ��ļ��С�
	 * @return FolderIfc
	 * @see FolderIfc
	 */
	public FolderIfc getFolder()
	{
		return folderIfc;
	}

	/**
	 * ��ȡ�㲿�������İ汾ֵ(��汾��+С�汾��)��
	 * @return String
	 */
	public String getIterationID()
	{
		return iterationID;
	}

	/**
	 * ��ȡ�㲿���Ĵ�汾�š�
	 * @return String
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * ��ȡ�㲿���Ĵ�汾�š�
	 * @return String
	 */
	public String getObjectVersion()
	{
		return version;
	}

	/**
	 * ��ȡ�㲿���İ���š�
	 * @return String
	 */
	public String getIteration()
	{
		return iteration;
	}

	/**
	 * �����㲿���İ���š��û�Ӧ���ǲ���ֱ�Ӳ��������Է�����Ϊ�ա�
	 * @param iteration1 String
	 */
	public void setIteration(final String iteration1)
	{
	}

	/**
	 * �����޶����㲿���Ĵ�汾�š�
	 * @param revision1 :String
	 */
	public void setRevision(final String revision1)
	{
		revision = revision1;
		setModified(true);
	}

	/**
	 * ��ȡ�޶����㲿���Ĵ�汾�š�
	 * @return String
	 */
	public String getRevision()
	{
		return revision;
	}

	/**
	 * �����㲿�������ơ�
	 * @param name1 :String
	 */
	public void setName(final String name1)
	{
		name = name1;
		setModified(true);
	}

	/**
	 * ��ȡ�㲿�����ơ�
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ��ȡ�㲿�����ơ�
	 * @return String
	 */
	public String getObjectName()
	{
		return name;
	}

	/**
	 * �����㲿������������״̬��
	 * @param state1 :String
	 */
	public void setState(final String state1)
	{
		state = state1;
		setModified(true);
	}

	/**
	 * ��ȡ�㲿������������״̬��
	 * @return state:String
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * �����㲿�����͡�
	 * @param type1 :QMPartType ö������
	 * @see QMPartType
	 */
	public void setType(final QMPartType type1)
	{
		type = type1;
		setModified(true);
	}

	/**
	 * ��ȡ�㲿�����͡�
	 * @return QMPartType ö������
	 * @see QMPartType
	 */
	public QMPartType getType()
	{
		return type;
	}

	/**
	 * �����㲿����Դ��
	 * @param source1 :ProducedBy ö������
	 * @see ProducedBy
	 */
	public void setSource(final ProducedBy source1)
	{
		source = source1;
		setModified(true);
	}

	/**
	 * ��ȡ�㲿����Դ��
	 * @return ProducedBy  ö�����͡�
	 * @see ProducedBy
	 */
	public ProducedBy getSource()
	{
		return source;
	}

	/**
	 * �����㲿�����ڵ��ļ���λ�á�
	 * @param location1 :String
	 */
	public void setLocation(final String location1)
	{
		location = location1;
		setModified(true);
	}

	/**
	 * ��ȡ�㲿�������ļ���λ�á�
	 * @return String
	 * @throws QMRemoteException
	 */
	public String getLocation() throws QMRemoteException
	{
		PartDebug
				.debug("getLocation() begin ....", this, PartDebug.PART_CLIENT);
		if(null == location)
		{
			location = getPart().getLocation();
			if(null == location)
			{
				//��Ҫ�ȵ���SessionService��ȡ��ǰ�û����ٸ��ݵ�ǰ�û���ָ����
				//��ȡ��Ӧ�ĸ����ļ��С�
				//��һ������ȡ��ǰ�û���ֵ����
				//�������͵ļ��ϡ�
				final Class[] paraClass = {};
				final Object[] objs = {};
				UserIfc userIfc1 = null;
				try
				{
					userIfc1 = (UserIfc) IBAUtility
							.invokeServiceMethod("SessionService",
									"getCurUserInfo", paraClass, objs);
				}
				catch (QMRemoteException ex)
				{
					throw ex;
				}
				//�ڶ��������ݵ�ǰ�û�����ȡ�����ļ��У���Ҫ����FolderService�еķ���
				//getPersonalFolder(UserIfc)������
				//�������͵ļ��ϡ�
				final Class[] paraClass1 = {UserInfo.class};
				final Object[] objs1 = {userIfc1};
				FolderIfc folderIfc1 = null;
				try
				{
					folderIfc1 = (FolderIfc) IBAUtility.invokeServiceMethod(
							"FolderService", "getPersonalFolder", paraClass1,
							objs1);
				}
				catch (QMRemoteException ex)
				{
					throw ex;
				}
				//��ȡ������·����
				location = folderIfc1.getPath();
				path = folderIfc1.getPath();
			} //end if(location == null)�㲿��ֵ�����locationΪ�ա�
		}
		//end if(location == null),��ǰû�ж�location���Խ��й���ֵ��
		PartDebug.debug("getLocation() end....return is String ", this,
				PartDebug.PART_CLIENT);
		return location;
	}

	/**
	 * ��ȡ�����ߵ����֡�
	 * @return String �����ߵ�����
	 */
	public String getCreatedByPersonName()
	{
		String userName = "";
		//liyz add �Ի�ȡ���������ֵı�ǩ�����жϣ����ɾ��ʱ�쳣
		if(null != (getPart().getIterationCreator())
				&& (getPart().getIterationCreator().length() > 0))//end
		{
			try
			{
				userName = PartHelper.getUserNameByID(getPart()
						.getIterationCreator());
			}
			catch (QMException e)
			{
				e.printStackTrace();
			}		
		}
		return userName;
	}

	/**
	 * ��ȡ�޸��ߵ���ʾ����
	 * @return String �޸��ߵ���ʾ��
	 */
	public String getModifiedByPersonName()
	{
		String modifier = "";
		if(null != (getPart().getIterationModifier())
				&& (getPart().getIterationModifier().length() > 0))
		{
			try
			{
				modifier = PartHelper.getUserNameByID(getPart()
						.getIterationModifier());
			}
			catch (QMException e)
			{
				e.printStackTrace();
			}
		}
		return modifier;
	}

	/**
	 * ��ȡ�������ڡ�
	 * @return Timestamp
	 */
	public Timestamp getCreationDate()
	{
		return getPart().getCreateTime();
	}

	/**
	 * ��ȡ�������ڡ�
	 * @return Timestamp
	 */
	public Timestamp getObjectCreationDate()
	{
		return getPart().getCreateTime();
	}

	/**
	 * ��ȡ����������ڡ�
	 * @return String
	 */
	public String getLastUpdated()
	{
		if(isNew())
		{
			return "";
		}
		return getPart().getModifyTime().toString();
	}

	/**
	 * ��ȡ�㲿��ʹ�ù�ϵ�б�
	 * @return UsesInterfaceList
	 * @see UsesInterfaceList
	 */
	public UsesInterfaceList getUsesInterfaceList()
	{
		if(null == usesInterface)
		{
			try
			{
				usesInterface = new UsesInterfaceList(this);
			}
			catch (QMRemoteException ex)
			{
			}
		}
		return usesInterface;
	}

	/**
	 * ��ȡ���㲿����ʹ������Щ����㲿����
	 * @return Explorable[] ���ص����㲿�����󼯺ϣ��ͻ��˽��㲿���ַ�װ��Explorable����
	 * @throws QMRemoteException
	 * @see Explorable
	 */
	public Explorable[] getUses() throws QMRemoteException
    {
        if(null == getConfigSpecItem())
        {
            //ConfigSpecItem(���ù淶)Ϊ��!
            throw new QMRemoteException(RESOURCE,
                    PartDesignViewRB.SPEC_IS_NULL, null);
        }
        else
        {
            if(explorableArray == null)
                explorableArray = PartHelper.getUses(getPart(),
                        getConfigSpecItem().getConfigSpecInfo());
            return explorableArray;
        }
    }

	/**
	 * ��ȡ�㲿���ο��ĵ��б�
	 * @return ReferencesList �㲿����Ӧ�Ĳο��ĵ����б�
	 * @see ReferencesList
	 */
	public ReferencesList getReferences()
	{
		if(null == references)
		{
			references = new ReferencesList(this);
		}
		return references;
	}

	/**
	 * ��ȡ�㲿��Ψһ��ʶ,�����㲿��id��
	 * @return String �㲿��id
	 */
	public String toString()
	{
		String ident = null;
		if(null == (getPart().getBsoID()))
		{
			ident = number;
		}
		else
		{
			ident = getPart().getBsoID();
		}
		return ident;
	}

	/**
	 * ��ȡ��������㲿��������ʾ��Ψһ��ʶ��
	 * DisplayIdentity ��������null
	 * @param locale String
	 * @deprecated
	 * @return String �����㲿��������ʾ��Ψһ��ʶ
	 */
	public String getDisplayIdentity(String locale)
	{
		return null;
	}

	/**
	 * ��ȡ�㲿���ı�ʶ��������ţ����ƣ��汾 ��ͼ��
	 * @return String  �㲿���ı�ʶ��������ţ����ƣ��汾 ��ͼ
	 */
	public String getIdentity()
	{
		//�ô���Ҫ�޸ģ����ȥ����DisplayIdentity�Ļ�������Ч�ʱȽϴ��������ֱ�ӻ�ȡ
		//part�ĸ���������֯��Identity()�Ļ����Ϳ������Ч�ʣ�2003.09.10
		final QMPartIfc part = getPart();
		String identity = part.getPartNumber() + "(" + part.getPartName() + ")"
				+ part.getVersionID();
		if(null != part.getViewName() && part.getViewName().length() > 0)
		{
			identity = identity + "(" + part.getViewName() + ")";
		}
//		  CCBegin by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼ 	
        if(this.routeText!=null)
            identity = identity+"_"+this.routeText;
//      CCEnd by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼  
		return identity;
	}

	/**
	 * ��ȡ�㲿������ͼ���ơ�
	 * @return String
	 */
	public String getViewName()
	{
		return viewName;
	}

	/**
	 * �����㲿�����ڵ���ͼ��
	 * @param view1 ViewObjectIfc ��ͼֵ����
	 * @see ViewObjectInfo
	 */
	public void setView(final ViewObjectIfc view1)
	{
		viewObjectIfc = view1;
		viewName = view1.getViewName();
	}

	/**
	 * ��ȡ�㲿�����ĸ���ͼ�������
	 * @return ViewObjectIfc ����ֵ����ͼ��ֵ����
	 * @see ViewObjectInfo
	 */
	public ViewObjectIfc getView()
	{
		return viewObjectIfc;
	}

	/**
	 * ��ȡ�㲿�����ڵĹ����顣
	 * @return project:String
	 */
	public String getProjectName()
	{
		return project;
	}

	/**
	 * ��ȡ�㲿����������������
	 * @return String
	 */
	public String getLifecycleName()
	{
		return lifecycle;
	}

	/**
	 * ����״̬���ı���������Ϊ�ա�
	 * @param value String
	 */
	public void setStatusText(final String value)
	{
	}

	/**
	 * ��Ҫ��顣
	 * ��ȡ�������ļ��״̬����ʾ���ַ�����
	 * @return String
	 * @throws QMException 
	 */
	public String getStatusText() throws QMException
	{
		String statusStr = "";
		String tempWorkableState = getPart().getWorkableState();
		if(tempWorkableState != null && tempWorkableState.length() > 0)
		{
		    //����(1)20081014 ��ǿ�޸� begin �޸�ԭ��:���մ����㲿��ʱ������״̬Ӧ����ʾΪ��δ���롱��
//			WorkInProgressState wState = WorkInProgressState
//					.toWorkInProgressState(tempWorkableState);
//			statusStr = wState.getDisplay();
		    WorkInProgressHelper workInProgressHelper=new WorkInProgressHelper();
		    statusStr = workInProgressHelper.getStatus(getPart());
		    //����(1)20081014 ��ǿ�޸� end
		}
		PartDebug.debug("getStatusText() begin ....", this,
				PartDebug.PART_CLIENT);
		PartDebug.debug("getStatusText() end....return is String", this,
				PartDebug.PART_CLIENT);
		return statusStr;
	}

	/**
	 * ��ʼ����Դ�ļ���
	 */
	public void initResources()
	{
		try
		{
			resource = ResourceBundle.getBundle(RESOURCE, QMCt.getContext()
					.getLocale());
			return;
		}
		catch (MissingResourceException msexception)
		{
		}
	}

	/**
	 * ��ȡ�󶨵���Դ��
	 * @return String
	 */
	protected ResourceBundle getResource()
	{
		if(null == resource)
		{
			initResources();
		}
		return resource;
	}

	/**
	 * ��ȡ��������Ψһ�Ա�ʶ��
	 * @return String
	 */
	public String getUniqueIdentity()
	{
		final QMPartIfc part = getPart();
		final String identity = part.getBranchID();
		return identity;
	}

	/**
	 * ��ȡ�㲿����Ĭ�ϵ�λ��
	 * @return Unit ��λ�ķ�װ��
	 * @see Unit
	 */
	public Unit getDefaultUnit()
	{
		return unitOfMeasure;
	}

	/**
	 * ����Ĭ�ϵ�λ��
	 * @param unit Unit ��λ��ö����
	 * @see Unit
	 */
	public void setDefaultUnit(final Unit unit)
	{
		unitOfMeasure = unit;
		setModified(true);
	}

	/**
	 * �����㲿���Ĺ���״̬Ϊ�ڵ����ò�ͬ��ͼ�ꡣ
	 */
	public void setIcon()
	{
		final String state = getPart().getWorkableState();
		if(state.equals("c/o"))
		{
			setStandardIcon("CheckOutIcon");
		}
		if(state.equals("c/i"))
		{
			setStandardIcon("StandardIcon");
		}
		if(state.equals("wrk"))
		{
			setStandardIcon("WorkingIcon");
		}
	}

	/**
	 * ��ȡ����ͼ��ľ���·����
	 * @param curName ������ͼ������
	 * @return ��ͼ��ľ���·����
	 */
	public String getIconName(final String curName)
	{
		String iconName = null;
		final String key = ((BaseValueIfc) (this.getObject())).getBsoName()
				+ curName;
		//ͼ�껺���е�����Ϊ��"QMPart + �����ͼ����,��OpenIcon" ,��ȡ��ͼ��ľ��Ե�·����
		iconName = (String) iconNameCache.get(key);
		//�����ͼ�껺����û���ҵ�iconName�Ļ����Ͱѵ�ǰ��ͼ�������첢�ŵ�iconNameCache��:
		if(null == iconName)
		{
			iconName = ((BaseValueIfc) (this.getObject())).getIconName(curName);
			iconNameCache.put(key, iconName);
		}
		return iconName;
	}

	/**
	 * ��ȡʹ�ýṹ�б�
	 * @return UsedByList
	 * @see UsedByList
	 */
	public UsedByList getUsedBy()
	{
		if(null == usedBy)
		{
			usedBy = new UsedByList(this);
		}
		return usedBy;
	}
	
//  CCBegin by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼ 	
	   public void setRouteTest(String text)
	   {
	     this.routeText = text;
	   }
//	  CCEnd by leixiao 2008-11-10 ԭ�򣺽����������·��,���ӹ���·�߰��ṹ������ʱ��������ṹѡ��������ʾ�����׼ 
	
	/**
     * ��ȡ�Ӽ����ݼ��ϡ�
     */
    public Explorable[] getExplorableArray() {//Begin CR1
        return explorableArray;
    }//End CR1
}
