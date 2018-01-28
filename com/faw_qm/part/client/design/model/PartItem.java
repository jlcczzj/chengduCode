/**
 * 生成程序 PartItem.java    1.0    2003/02/20
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/02 谢斌 原因:增加获取子件数据集合的方法，为下面构造UsesInterfaceList使用
 *                     方案:优化初始化使用结构数据类
 *                     备注:解放v3r11-展开零部件树节点性能优化
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
 * 是一个模型类用来操作QMPart对象。它包含用来操作QMPart类的属性和关系的帮助方法。他
 * 继承QMBusinessObject来允许在QMExplorer中进行简单的操作。
 * 从QMBusinessObject类继承来的方法：
 * getAttributeValue, getAttributeValue,
 * getContents, getObject, getOpenIcon,
 * getStandardIcon, setIdentity, setObject,
 * setObject, setStandardIcon, setStandardIcon
 */
//隐含的问题有：对零部件的附加属性的操作问题????内容服务问题， 以及其他的在QMPart表中的所有的
//持久化字段的赋值方法，维护方法问题???????? 2003.04.04
/**
 * 模型类，用来封装零部件值对象。
 * @author 吴先超
 * @version 1.0
 */
//问题(1)20081014 张强修改 修改原因:当刚创建零部件时，工作状态应该显示为“未检入”。（TD-1868）
public class PartItem extends QMBusinessObject
{
	/**序列化ID*/
	static final long serialVersionUID = 1L;
	/**绑定的资源信息*/
	private static ResourceBundle resource = null;

	/**客户端资源文件路径*/
	private static final String RESOURCE = "com.faw_qm.part.client.design.util.PartDesignViewRB";

	/**零部件的配置项值对象，这里定义的是封装类ConfigSpecItem中的对象，它封装了配置项的信息*/
	private ConfigSpecItem configSpecItem = null;

	/**零部件是否被修改的标识*/
	private boolean modified = false;

	/**是否是新创建的零部件的标识*/
	private transient boolean isCreate = false;

	/**零部件编号*/
	private String number = null;

	/**零部件所在的视图名称*/
	private transient String viewName = null;

	/**零部件受那个视图对象管理*/
	private transient ViewObjectIfc viewObjectIfc = null;

	/**零部件名称*/
	private String name = null;

	/**修订后零部件的大版本*/
	private String revision = null;

	/**大版本号。例如：A.1.B.2中的大版本号是:A.1.B*/
        //将version属性static属性去掉，原因是如果静态属性，按版本无法排序 cdc 20071016
	private  String version = null;

	/**迭代小版本(版序号)。例如：A.1.B.2中的版序号是：2*/
	private String iteration = null;

	/**完整的版本+版序值*/
	private transient String iterationID = null;

	/**生命周期状态*/
	private String state = null;

	/**零部件所在的工作组*/
	private transient String project = null;

	/**生命周期模版名称*/
	private transient String lifecycle = null;

	/**零部件单位。枚举类型*/
	private transient Unit unitOfMeasure = null;

	/**零部件类型。枚举类型*/
	private QMPartType type = null;

	/**零部件来源。枚举类型*/
	private ProducedBy source = null;

	/**零部件所在文件夹位置*/
	private String location = null;

	/**零部件所在文件夹完整的路径*/
	public transient String path = null;

	/**零部件所在文件夹值对象*/
	private FolderIfc folderIfc = null;

	/**用来操作零部件使用关系的集合的封装类对象*/
	private UsesInterfaceList usesInterface = null;

	/**记录零部件参考关系的列表*/
	private ReferencesList references = null;

	/**记录零部件使用结构的列表*/
	private UsedByList usedBy = null;

	/**（QMPart + 图标名, 对应的图标绝对路径）*/
	public transient Map iconNameCache = new HashMap();

    /** 当前零部件都使用子件集合。 */
    private Explorable[] explorableArray = null;
//	  CCBegin by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准 	
    private String routeText;
//	  CCEnd by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准 
	/**
	 * 构造一个新的PartItem 对象。
	 * 设置零部件默认的类型:SEPARABLE。
	 * 默认的来源:MAKE。
	 * 并且调用QMPartIfc父类RevisionControlledIfc中的方法:initialize()。
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
	 * 构造一个新的PartItem 对象。
	 * @param partIfc QMPartIfc 零部件值对象。
	 * @see QMPartInfo
	 */
	public PartItem(QMPartIfc partIfc)
	{
		//调用父类的方法，把零部件的编号作为零部件的标识。
		super(partIfc.getPartNumber(), partIfc);
		modified = false;
		setPart(partIfc);
	}

	/**
	 * 设置零部件值对象的信息。
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
	 * 设置QMPartIfc partIfc 调用了父类的方法。
	 * @param partIfc QMPartIfc
	 * @see QMPartInfo
	 */
	public void setPartSimply(final QMPartIfc partIfc)
	{
		super.setObject(partIfc);
	}

	/**
	 * 初始化零部件值对象。
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
	 * 获取零部件值对象。
	 * @return String
	 * @see QMPartInfo
	 */
	public QMPartIfc getPart()
	{
		return (QMPartIfc) getObject();
	}

	/**
	 * 设置配置项。
	 * @param configSpecItem1 ConfigSpecItem 配置规范封装类
	 * @see ConfigSpecItem
	 */
	public void setConfigSpecItem(final ConfigSpecItem configSpecItem1)
	{
		configSpecItem = configSpecItem1;
	}

	/**
	 * 获取配置项(configSpecItem)类型为ConfigSpecItem。
	 * @return ConfigSpecItem
	 * @see ConfigSpecItem
	 */
	public ConfigSpecItem getConfigSpecItem()
	{
		return configSpecItem;
	}

	/**
	 * 设置这个PartItem对象是否为新的一个新的值，即零部件是否是新建的。
	 * @param flag boolean
	 */
	public void setNew(final boolean flag)
	{
		isCreate = flag;
	}

	/**
	 * 获取这个PartItem对象是否是新的标识。
	 * @return boolean
	 */
	public boolean isNew()
	{
		return isCreate;
	}

	/**
	 * 设置这个PartItem对象是否改变的标识。
	 * @param modified1 boolean true为改变
	 */
	protected void setModified(final boolean modified1)
	{
		modified = modified1;
	}

	/**
	 * 获取这个PartItem对象是否被改变的标识。
	 * @return boolean
	 */
	public boolean isModified()
	{
		return modified;
	}

	/**
	 * 为这个partItem建立一个新的被包含的零部件。
	 * @throws QMRemoteException
	 */
	public void create() throws QMRemoteException
	{
		try
		{
			//给新创建的零部件值对象的属性容器ID赋值：
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
	 * 设置PartItem中的PartName, PartNumber, 文件夹, 视图，类型，来源，单位。
	 * @throws QMRemoteException
	 */
	public void setFields() throws QMRemoteException
	{
		PartDebug.debug("setFields() begin ....", this, PartDebug.PART_CLIENT);
		if(isNew())
		{
			try
			{
				//给新创建的零部件值对象的属性容器ID赋值：
				QMPartIfc partIfc = this.getPart();
				if(null == partIfc)
				{
					partIfc = new QMPartInfo();
				}
				setPartSimply(partIfc);
                //CCBegin by liunan 2008-07-25
                //对零部件代码进行大写转换后，再进行保存。
//				原代码如下
				getPart().setPartNumber(number);                
                getPart ().setPartNumber ( number.toUpperCase() ) ;
                //CCEnd by liunan 2008-07-25
				
				getPart().setPartName(name);
				//参数类型的集合。
				final Class[] paraClass2 = {FolderEntryIfc.class, String.class};
				//参数值的集合。
				final Object[] objs2 = {getPart(), location};
				//actor1保存获取值。
				final FolderEntryIfc view1 = (FolderEntryIfc) IBAUtility
						.invokeServiceMethod("FolderService", "assignFolder",
								paraClass2, objs2);
				//把获取值actor1转换为字符串：
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
	 * 更新零部件。
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
		//如果修改标示没有改变的话，说明没有对当前的零部件更新，直接获取，不进行任何的操作。
		else
		{
			PartDebug.debug("update() end....return is null", this,
					PartDebug.PART_CLIENT);
		}
	}

	/**
	 * 删除这个partItem的一个包含的零部件。
	 * @throws QMRemoteException
	 */
	public void delete() throws QMRemoteException
	{
		try
		{
			PartHelper.deletePart(getPart());
			//把客户端的零部件值对象删除：
			setPartSimply(null);
			return;
		}
		catch (QMRemoteException qmexception)
		{
			throw qmexception;
		}
	}

	/**
	 * 由于修改为被包含的零部件分配一个刷新事件。
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
	 * 设置零部件的编号。
	 * @param number1 String
	 */
	public void setNumber(final String number1)
	{
        //CCBegin by liunan 2008-07-25
        //对零部件代码进行大写转换后，再处理。
        //原代码如下
        //number = number1;
        number = number1.toUpperCase() ;
        //CCEnd by liunan 2008-07-25
		setModified(true);
	}

	/**
	 * 获取零部件编号。
	 * @return String
	 */
	public String getNumber()
	{
        //CCBegin by liunan 2008-07-25
        //对零部件代码进行大写转换后，再返回。
        //原代码如下
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
	 * 获取零部件编号。
	 * @return String
	 */
	public String getObjectNumber()
	{
        //CCBegin by liunan 2008-07-25
        //对零部件代码进行大写转换后，再返回。
        //原代码如下
        //return number;
        return number.toUpperCase() ;
        //CCEnd by liunan 2008-07-25
	}

	/**
	 * 设置零部件所在的文件夹。
	 * @param folderIfc1 :FolderIfc 文件夹值对象。
	 * @see FolderIfc
	 */
	public void setFolder(final FolderIfc folderIfc1)
	{
		folderIfc = folderIfc1;
	}

	/**
	 * 获取零部件所在的文件夹。
	 * @return FolderIfc
	 * @see FolderIfc
	 */
	public FolderIfc getFolder()
	{
		return folderIfc;
	}

	/**
	 * 获取零部件完整的版本值(大版本号+小版本号)。
	 * @return String
	 */
	public String getIterationID()
	{
		return iterationID;
	}

	/**
	 * 获取零部件的大版本号。
	 * @return String
	 */
	public String getVersion()
	{
		return version;
	}

	/**
	 * 获取零部件的大版本号。
	 * @return String
	 */
	public String getObjectVersion()
	{
		return version;
	}

	/**
	 * 获取零部件的版序号。
	 * @return String
	 */
	public String getIteration()
	{
		return iteration;
	}

	/**
	 * 设置零部件的版序号。用户应该是不能直接操作，所以方法体为空。
	 * @param iteration1 String
	 */
	public void setIteration(final String iteration1)
	{
	}

	/**
	 * 设置修订后零部件的大版本号。
	 * @param revision1 :String
	 */
	public void setRevision(final String revision1)
	{
		revision = revision1;
		setModified(true);
	}

	/**
	 * 获取修订后零部件的大版本号。
	 * @return String
	 */
	public String getRevision()
	{
		return revision;
	}

	/**
	 * 设置零部件的名称。
	 * @param name1 :String
	 */
	public void setName(final String name1)
	{
		name = name1;
		setModified(true);
	}

	/**
	 * 获取零部件名称。
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * 获取零部件名称。
	 * @return String
	 */
	public String getObjectName()
	{
		return name;
	}

	/**
	 * 设置零部件的生命周期状态。
	 * @param state1 :String
	 */
	public void setState(final String state1)
	{
		state = state1;
		setModified(true);
	}

	/**
	 * 获取零部件的生命周期状态。
	 * @return state:String
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * 设置零部件类型。
	 * @param type1 :QMPartType 枚举类型
	 * @see QMPartType
	 */
	public void setType(final QMPartType type1)
	{
		type = type1;
		setModified(true);
	}

	/**
	 * 获取零部件类型。
	 * @return QMPartType 枚举类型
	 * @see QMPartType
	 */
	public QMPartType getType()
	{
		return type;
	}

	/**
	 * 设置零部件来源。
	 * @param source1 :ProducedBy 枚举类型
	 * @see ProducedBy
	 */
	public void setSource(final ProducedBy source1)
	{
		source = source1;
		setModified(true);
	}

	/**
	 * 获取零部件来源。
	 * @return ProducedBy  枚举类型。
	 * @see ProducedBy
	 */
	public ProducedBy getSource()
	{
		return source;
	}

	/**
	 * 设置零部件所在的文件夹位置。
	 * @param location1 :String
	 */
	public void setLocation(final String location1)
	{
		location = location1;
		setModified(true);
	}

	/**
	 * 获取零部件所在文件夹位置。
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
				//需要先调用SessionService获取当前用户，再根据当前用户的指对象，
				//获取对应的个人文件夹。
				//第一步：获取当前用户的值对象，
				//参数类型的集合。
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
				//第二步：根据当前用户，获取个人文件夹，需要调用FolderService中的方法
				//getPersonalFolder(UserIfc)方法，
				//参数类型的集合。
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
				//获取完整的路径：
				location = folderIfc1.getPath();
				path = folderIfc1.getPath();
			} //end if(location == null)零部件值对象的location为空。
		}
		//end if(location == null),以前没有对location属性进行过赋值。
		PartDebug.debug("getLocation() end....return is String ", this,
				PartDebug.PART_CLIENT);
		return location;
	}

	/**
	 * 获取创建者的名字。
	 * @return String 创建者的名字
	 */
	public String getCreatedByPersonName()
	{
		String userName = "";
		//liyz add 对获取创建者名字的标签进行判断，解决删除时异常
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
	 * 获取修改者的显示名。
	 * @return String 修改者的显示名
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
	 * 获取创建日期。
	 * @return Timestamp
	 */
	public Timestamp getCreationDate()
	{
		return getPart().getCreateTime();
	}

	/**
	 * 获取创建日期。
	 * @return Timestamp
	 */
	public Timestamp getObjectCreationDate()
	{
		return getPart().getCreateTime();
	}

	/**
	 * 获取最近更改日期。
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
	 * 获取零部件使用关系列表。
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
	 * 获取该零部件都使用了哪些别的零部件。
	 * @return Explorable[] 返回的是零部件对象集合，客户端将零部件又封装成Explorable对象
	 * @throws QMRemoteException
	 * @see Explorable
	 */
	public Explorable[] getUses() throws QMRemoteException
    {
        if(null == getConfigSpecItem())
        {
            //ConfigSpecItem(配置规范)为空!
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
	 * 获取零部件参考文档列表。
	 * @return ReferencesList 零部件对应的参考文档的列表。
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
	 * 获取零部件唯一标识,返回零部件id。
	 * @return String 零部件id
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
	 * 获取这个对象（零部件）的显示的唯一标识；
	 * DisplayIdentity 方法返回null
	 * @param locale String
	 * @deprecated
	 * @return String 对象（零部件）的显示的唯一标识
	 */
	public String getDisplayIdentity(String locale)
	{
		return null;
	}

	/**
	 * 获取零部件的标识，包括编号（名称）版本 视图。
	 * @return String  零部件的标识，包括编号（名称）版本 视图
	 */
	public String getIdentity()
	{
		//该处需要修改：如果去调用DisplayIdentity的话，降低效率比较大，如果采用直接获取
		//part的各个属性组织成Identity()的话，就可以提高效率：2003.09.10
		final QMPartIfc part = getPart();
		String identity = part.getPartNumber() + "(" + part.getPartName() + ")"
				+ part.getVersionID();
		if(null != part.getViewName() && part.getViewName().length() > 0)
		{
			identity = identity + "(" + part.getViewName() + ")";
		}
//		  CCBegin by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准 	
        if(this.routeText!=null)
            identity = identity+"_"+this.routeText;
//      CCEnd by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准  
		return identity;
	}

	/**
	 * 获取零部件的视图名称。
	 * @return String
	 */
	public String getViewName()
	{
		return viewName;
	}

	/**
	 * 设置零部件所在的视图。
	 * @param view1 ViewObjectIfc 视图值对象
	 * @see ViewObjectInfo
	 */
	public void setView(final ViewObjectIfc view1)
	{
		viewObjectIfc = view1;
		viewName = view1.getViewName();
	}

	/**
	 * 获取零部件受哪个视图对象管理。
	 * @return ViewObjectIfc 返回值是视图的值对象
	 * @see ViewObjectInfo
	 */
	public ViewObjectIfc getView()
	{
		return viewObjectIfc;
	}

	/**
	 * 获取零部件所在的工作组。
	 * @return project:String
	 */
	public String getProjectName()
	{
		return project;
	}

	/**
	 * 获取零部件的生命周期名。
	 * @return String
	 */
	public String getLifecycleName()
	{
		return lifecycle;
	}

	/**
	 * 设置状态栏文本。方法体为空。
	 * @param value String
	 */
	public void setStatusText(final String value)
	{
	}

	/**
	 * 需要检查。
	 * 获取这个对象的检出状态栏显示的字符串。
	 * @return String
	 * @throws QMException 
	 */
	public String getStatusText() throws QMException
	{
		String statusStr = "";
		String tempWorkableState = getPart().getWorkableState();
		if(tempWorkableState != null && tempWorkableState.length() > 0)
		{
		    //问题(1)20081014 张强修改 begin 修改原因:当刚创建零部件时，工作状态应该显示为“未检入”。
//			WorkInProgressState wState = WorkInProgressState
//					.toWorkInProgressState(tempWorkableState);
//			statusStr = wState.getDisplay();
		    WorkInProgressHelper workInProgressHelper=new WorkInProgressHelper();
		    statusStr = workInProgressHelper.getStatus(getPart());
		    //问题(1)20081014 张强修改 end
		}
		PartDebug.debug("getStatusText() begin ....", this,
				PartDebug.PART_CLIENT);
		PartDebug.debug("getStatusText() end....return is String", this,
				PartDebug.PART_CLIENT);
		return statusStr;
	}

	/**
	 * 初始化资源文件。
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
	 * 获取绑定的资源。
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
	 * 获取这个对象的唯一性标识。
	 * @return String
	 */
	public String getUniqueIdentity()
	{
		final QMPartIfc part = getPart();
		final String identity = part.getBranchID();
		return identity;
	}

	/**
	 * 获取零部件的默认单位。
	 * @return Unit 单位的封装类
	 * @see Unit
	 */
	public Unit getDefaultUnit()
	{
		return unitOfMeasure;
	}

	/**
	 * 设置默认单位。
	 * @param unit Unit 单位的枚举类
	 * @see Unit
	 */
	public void setDefaultUnit(final Unit unit)
	{
		unitOfMeasure = unit;
		setModified(true);
	}

	/**
	 * 根据零部件的工作状态为节点设置不同的图标。
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
	 * 获取给定图标的绝对路径。
	 * @param curName 给定的图标名。
	 * @return 该图标的绝对路径。
	 */
	public String getIconName(final String curName)
	{
		String iconName = null;
		final String key = ((BaseValueIfc) (this.getObject())).getBsoName()
				+ curName;
		//图标缓存中的内容为："QMPart + 具体的图标名,如OpenIcon" ,获取该图标的绝对的路径：
		iconName = (String) iconNameCache.get(key);
		//如果在图标缓存中没有找到iconName的话，就把当前的图标名构造并放到iconNameCache中:
		if(null == iconName)
		{
			iconName = ((BaseValueIfc) (this.getObject())).getIconName(curName);
			iconNameCache.put(key, iconName);
		}
		return iconName;
	}

	/**
	 * 获取使用结构列表。
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
	
//  CCBegin by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准 	
	   public void setRouteTest(String text)
	   {
	     this.routeText = text;
	   }
//	  CCEnd by leixiao 2008-11-10 原因：解放升级工艺路线,增加工艺路线按结构添加零件时，在零件结构选择树上显示相关艺准 
	
	/**
     * 获取子件数据集合。
     */
    public Explorable[] getExplorableArray() {//Begin CR1
        return explorableArray;
    }//End CR1
}
