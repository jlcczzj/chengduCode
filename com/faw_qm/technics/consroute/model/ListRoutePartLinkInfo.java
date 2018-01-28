/**
 * 生成程序 ListRoutePartLinkInfo.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/22 徐春英       原因：增加预留属性
 * CR2 2011/12/22 徐春英       原因：增加更改标记、产品信息和路线信息
 * CR3 2011/12/30 徐春英      原因：通过关联对象获得路线值对象
 * CR4 2012/01/10 徐春英      原因：增加路线描述、路线生效时间和失效时间
 * SS1 轴齿中心增加“采购标识”Liuyang 2013-12-24
 * SS2 轴齿中心增加零部件版本属性 liuyang 2014-2-25
 * SS3 长特供应商 liuyang 2014-8-15
 * SS4 成都工艺路线整合，增加工厂dwbs、lastEffRoute属性。 liunan 2016-8-11
 * SS5 成都工艺路线，增加颜色件标识colorFlag属性。 liunan 2016-10-25
 * SS6 成都工艺路线特殊处理件标识，为成都的零部件进行版本转换 徐德政 2018-01-11
 * SS7 成都工艺路显示发布源版本 徐德政 2018-01-21
 */
package com.faw_qm.technics.consroute.model;

import java.sql.Timestamp;
import java.util.Date;

import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BinaryLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.util.RouteAdoptedType;
import com.faw_qm.util.EJBServiceHelper;

/**
 * 路线零部件联系值对象
 * @author 管春元
 * @version 1.0
 */
public class ListRoutePartLinkInfo extends BinaryLinkInfo implements ListRoutePartLinkIfc
{

    //private static final String routeMode = RemoteProperty.getProperty("routeManagerMode", "partRelative");
    /**
     * 零部件主信息值对象
     */
    private QMPartMasterIfc partMasterInfo;

    /**
     * 父件值对象
     */
    private QMPartIfc parentPartInfo;

    /**
     * 路线id
     */
    private String routeID;

    /**
     * 修改状态
     */
    private int alterStatus;

    /**
     * 路线状态
     */
    private String status;

    //st skybird 2005.3.1

    /**
     * 父件编号
     */
    private String parentPartNum;

    /**
     * 父件名称
     */
    private String parentPartName;

    /**
     * 父件id
     */
    private String parentPartID;

    /**
     * 零部件在父件在产品中使用的数量
     */
    private int count;

    /**
     * 初始路线表大版本
     */
    private String vesion;

    /**
     * 路线表主信息id
     */
    private String listMasterID;

    /**
     * 路线表的具体版本
     */
    private String listIterationID;
    private static final long serialVersionUID = 1L;
    //begin CR1
    private String attribute1;
    private String attribute2;
    //end CR1

    //begin CR2
    /**
     * 更改标记
     */
    private String modifyIdenty;

    /**
     * 产品id
     */
    private String productID;

    /**
     * 产品标识
     */
    private boolean productIdenty;

    /**
     * 产品数量
     */
    private int productCount;

    /**
     * 路线是否发布
     */
    private int releaseIdenty;

    /**
     * 主要路线串
     */
    private String mainStr;

    /**
     * 次要路线串
     */
    private String secondStr;
    
    
    //end CR2

    //CR3
    private TechnicsRouteIfc routeInfo;

    //begin CR4
    /**
     * 路线描述
     */
    private String routeDescription;
    
    /**
     * 生效时间
     */
    private Timestamp efficientTime;
    
    /**
     * 失效时间
     */
    private Timestamp disabledDateTime;
    
    private String PartBranchID;
    //CCBegin SS1
    private String stockID;
    //CCEnd SS1
  //CCBegin SS2
	private String partVersion;
	//CCEnd SS2
    
    //CCBegin SS4
    private String dwbs;
    
    private String lastEffRoute;
    //CCEnd SS4
    //CCBegin SS5
    private String colorFlag;
    //CCEnd SS5
    //CCBegin SS6
    private String specialFlag;
    //CCEnd SS6
  //CCBegin SS7
    private String sourceVersion;
    //CCEnd SS7
    /**
     * 构造函数
     */
    public ListRoutePartLinkInfo()
    {}

    /**
     *得到新的路线与零件关联的值对象
     * @return ListRoutePartLinkInfo listLinkInfo 新的路线与零件关联的值对象
     */
    public static ListRoutePartLinkInfo newListRoutePartLinkInfo()
    {
        ListRoutePartLinkInfo listLinkInfo = new ListRoutePartLinkInfo();
        return listLinkInfo;
    }

    /**
     * 客户端调用方法。生成一个新的值对象
     * @param routeListInfo TechnicsRouteListIfc 路线表值对象
     * @param partMasterID String 零件主信息ID
     * @return ListRoutePartLinkInfo 新的路线与零件关联的值对象
     */
    public static ListRoutePartLinkInfo newListRoutePartLinkInfo(TechnicsRouteListIfc routeListInfo, String partMasterID)
    {
        ListRoutePartLinkInfo listLinkInfo = new ListRoutePartLinkInfo();
        listLinkInfo.setPartMasterID(partMasterID);
        listLinkInfo.setRouteListID(routeListInfo.getBsoID());
   
        listLinkInfo.setInitialUsed(routeListInfo.getVersionID());
     
        //路线未生成，初始状态为继承。
        listLinkInfo.setAlterStatus(TechnicsRouteServiceEJB.INHERIT);
        listLinkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
        listLinkInfo.setRouteListIterationID(routeListInfo.getVersionValue());
        listLinkInfo.setRouteListMasterID(routeListInfo.getMaster().getBsoID());
        return listLinkInfo;
    }
    

    /**
     * 获得业务对象
     * @return String ListRoutePartLink
     */
    public String getBsoName()
    {
        return "consListRoutePartLink";
    }

    /**
     * 得到路线id
     * @return String 路线id
     */
    public String getRouteID()
    {
        return this.routeID;
    }

    /**
     * 设置路线id
     * @param routeID String 路线id
     */
    public void setRouteID(String routeID)
    {
        this.routeID = routeID;
    }

    //begin CR3
    /**
     * 获得工艺路线对象
     */
    public TechnicsRouteIfc getRouteInfo()
    {
        return routeInfo;
    }

    /**
     * 设置工艺 路线对象
     * @param routeInfo 工艺路线
     * @return void
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo)
    {
        this.routeInfo = routeInfo;
    }

    //end CR3

    /**
     * 得到零部件主信息id
     * @return String 零部件主信息id
     */
    public String getPartMasterID()
    {
        return this.getRightBsoID();
    }

    /**
     * 设置零部件主信息id
     * @param partMasterID String 零部件主信息id
     */
    public void setPartMasterID(String partMasterID)
    {
        this.setRightBsoID(partMasterID);
    }

    /**
     * 判断零件是否有工艺路线。因为零件对应的工艺路线通常不会删除，所以此处的工艺路线状 态作为持久化属性。 注意：当RoutePartLink删除时应维护此属性，保持数据的一致性。 数据的一致性在saveRoute和RoutePostDeleteListener中维护。
     */
    public String getAdoptStatus()
    {
        return this.status;
    }

    /**
     * 设置零件的工艺路线是否被采用。
     * @param status 采用、取消。
     */
    public void setAdoptStatus(String status)
    {
        this.status = status;
    }

    //st skybird 2005.3.1
    // gcy add in 2005.4.26 for reinforce requirement  start

    /**
     * 设置零部件表的零部件的父件编号
     * @param status 采用、取消。
     */
    public String getParentPartNum()
    {
        return this.parentPartNum;
    }

    /**
     * 设置零部件的父件编号
     * @param parentPartNum String 零部件的父件编号
     */
    public void setParentPartNum(String parentPartNum)
    {
        this.parentPartNum = parentPartNum;
    }

    /**
     * 得到父件名
     * @return String 父件名
     */
    public String getParentPartName()
    {
        return this.parentPartName;
    }

    /**
     * 设置父件名
     * @param parentPartNum String 父件名
     */
    public void setParentPartName(String parentPartNum)
    {
        this.parentPartName = parentPartNum;
    }

    /**
     * 得到父件的id
     * @return String 父件的id
     */
    public String getParentPartID()
    {
        return this.parentPartID;
    }

    /**
     * 设置父件的id
     * @param parentPartNum String 父件的id
     */
    public void setParentPartID(String parentPartNum)
    {
        this.parentPartID = parentPartNum;
    }

    /**
     * 得到父件值对象
     * @return String 父件值对象
     */
    public QMPartIfc getParentPart()
    {
        return this.parentPartInfo;
    }

    /**
     * 设置父件值对象
     * @param part QMPartIfc 父件值对象
     * @see QMPartInfo
     */
    public void setParentPart(QMPartIfc part)
    {
        this.parentPartInfo = part;
    }

    /**
     * 得到当前零部件在父件中使用数量（不一定是直接上级）
     * @return String 当前零部件在父件中使用数量
     */
    public int getCount()
    {
        return this.count;
    }

    /**
     * 设置当前零部件在父件中使用数量（不一定是直接上级）
     * @param count int 当前零部件在父件中使用数量
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    // gcy add in 2005.4.26 for reinforce requirement  end

    //ed

    /**
     * 初始采用版本。A, B.大版本号。
     * @return String A, B.大版本号
     */
    public String getInitialUsed()
    {
        return this.vesion;
    }

    /**
     * 设置初始采用版本。A, B.大版本号。
     * @param vesion String 初始采用版本
     */
    public void setInitialUsed(String version)
    {
        this.vesion = version;
    }

    /**
     * 此标识用来判断与路线表关联的路线是否显示（在物料清单中）。是否处理采用、取消。
     * @return int =0 表示是从上一版本继承下来的；int=1，从本版本生成的。涉及到路线是否重新生成；int=2，此版本删除的，当路线表检出时，不复制此关联。
     */
    public int getAlterStatus()
    {
        return this.alterStatus;
    }

    /**
     * 设置修改状态
     * @param alterStatus int int =0 表示是从上一版本继承下来的； int=1，从本版本生成的。涉及到路线是否重新生成； int=2，此版本删除的，当路线表检出时，不复制此关联。
     */
    public void setAlterStatus(int alterStatus)
    {
        this.alterStatus = alterStatus;
    }

    /**
     * 得到工艺路线表ID。
     * @return String 工艺路线表ID。
     */
    public String getRouteListID()
    {
        return this.getLeftBsoID();
    }

    /**
     * 设置工艺路线表ID。
     * @param routeListID String 工艺路线表ID。
     */
    public void setRouteListID(String routeListID)
    {
        this.setLeftBsoID(routeListID);
    }

    /**
     * 得到路线表的id
     * @return String 路线表的id
     */
    public String getRouteListMasterID()
    {
        return this.listMasterID;
    }

    /**
     * 设置路线表的id
     * @param listMasterID String 路线表的id
     */
    public void setRouteListMasterID(String listMasterID)
    {
        this.listMasterID = listMasterID;
    }

    /**
     * 得到关联中路线表的小版本
     * @return String 关联中路线表的小版本
     */
    public String getRouteListIterationID()
    {
        return this.listIterationID;
    }

    /**
     * 设置关联中路线表的小版本
     * @param listIterationID String 关联中路线表的小版本
     */
    public void setRouteListIterationID(String listIterationID)
    {
        this.listIterationID = listIterationID;
    }

    /**
     * 获得零件主信息值对象 在客户端调用。
     * @return QMPartMasterIfc 零件主信息值对象
     */
    public QMPartMasterIfc getPartMasterInfo()
    {
        return this.partMasterInfo;
    }

    /**
     * 设置零件主信息值对象 partMasterInfo在EJB初始化。
     * @param partMasterInfo QMPartMasterIfc
     * @see QMPartMasterInfo
     */
    public void setPartMasterInfo(QMPartMasterIfc partMasterInfo)
    {
        this.partMasterInfo = partMasterInfo;
    }

    //begin CR1
    /**
     * 设置预留属性1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * 获得预留属性1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * 设置预留属性2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * 获得预留属性2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }

    //end CR1

    //begin CR2
    /**
     * 获得更改标记
     * @return 更改标记
     */
    public String getModifyIdenty()
    {
        return this.modifyIdenty;
    }

    /**
     * 设置更改标记
     * @param identy 更改标记
     */
    public void setModifyIdenty(String identy)
    {
        this.modifyIdenty = identy;
    }

    /**
     * 得到产品的id
     * @return String 产品的id
     */
    public String getProductID()
    {
        return this.productID;
    }

    /**
     * 设置产品的id
     * @param parentPartNum String 产品的id
     */
    public void setProductID(String productID)
    {
        this.productID = productID;
    }

    /**
     * 设置零部件的产品标识
     */
    public boolean getProductIdenty()
    {
        return this.productIdenty;
    }

    /**
     * 设置零部件的产品标识
     * @param productIdenty boolean 零部件的产品标识
     */
    public void setProductIdenty(boolean productIdenty)
    {
        this.productIdenty = productIdenty;
    }

    /**
     * 得到产品数量
     * @return int 产品数量
     */
    public int getProductCount()
    {
        return this.productCount;
    }

    /**
     * 设置产品数量
     * @param productCount int 产品数量
     */
    public void setProductCount(int productCount)
    {
        this.productCount = productCount;
    }

    /**
     * 得到路线发布信息
     * @return int 路线发布信息
     */
    public int getReleaseIdenty()
    {
        return this.releaseIdenty;
    }

    /**
     * 设置路线发布信息
     * @param releaseIdenty int 路线发布信息
     */
    public void setReleaseIdenty(int releaseIdenty)
    {
        this.releaseIdenty = releaseIdenty;
    }

    /**
     * 得到主要路线串信息
     * @return String 主要路线串信息
     */
    public String getMainStr()
    {
        return this.mainStr;
    }

    /**
     * 设置主要路线串信息
     * @param mainStr String 主要路线串信息
     */
    public void setMainStr(String mainStr)
    {
        this.mainStr = mainStr;
    }

    /**
     * 得到次要路线串信息
     * @return String 次要路线串信息
     */
    public String getSecondStr()
    {
        return this.secondStr;
    }

    /**
     * 设置次要路线串信息
     * @param mainStr String 次要路线串信息
     */
    public void setSecondStr(String secondStr)
    {
        this.secondStr = secondStr;
    }
    
    /**
     * 获得路线说明
     * @return String 路线说明
     */
    public String getRouteDescription()
    {
        return this.routeDescription;
    }

    /**
     * 设置路线说明。
     * @param description 路线说明
     */
    public void setRouteDescription(String description)
    {
        this.routeDescription = description;
    }
    //end CR2
    
    //begin CR4
    /**
     * 获得 生效时间
     * @return Timestamp 生效时间
     */
    public Timestamp getEfficientTime()
    {
        return this.efficientTime;
    }

    /**
     * 设置生效时间
     * @param efficientData 生效时间
     */
    public void setEfficientTime(Timestamp efficientTime)
    {
        this.efficientTime = efficientTime;
    }
    

    /**
     * 获得失效时间
     * @return Timestamp 失效时间
     */
    public Timestamp getDisabledDateTime()
    {
        return this.disabledDateTime;
    }

    /**
     * 设置失效时间
     * @param disabledDateTime 失效时间
     */
    public void setDisabledDateTime(Timestamp disabledDateTime)
    {
        this.disabledDateTime = disabledDateTime;
    }
    //end CR4
    
    /**
     * 得到关联中零件的小版本
     * @return String
     */
    public String getPartBranchID() {
      return this.PartBranchID;
    }

    /**
     * 设置关联中零件的小版本
     * @param listIterationID String
     */
    public void setPartBranchID(String partBranchID) {
      this.PartBranchID = partBranchID;
    }

    //CCBegin SS1
    /**
     * 得到采购标识
     */
	public String getStockID() {
		return this.stockID;
	}

	  /**
     * 设置采购标识
     * @param stockID
     */
	public void setStockID(String stockID) {
		
		this.stockID=stockID;
	}
	  //CCEnd SS1
    //CCBegin SS2

	public String getPartVersion() {
		
		return this.partVersion;
	}


	public void setPartVersion(String partVersion) {
		
		this.partVersion=partVersion;
	}
	//CCEnd SS2

//CCBegin SS3
	private String supplier;
	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier=supplier;
		
	}
	private String supplierBsoId;
	public String getSupplierBsoId() {
		
		return this.supplierBsoId;
	}

	
	public void setSupplierBsoId(String supplierBsoId) {
		this.supplierBsoId=supplierBsoId;
	//CCEnd SS3
	}
	
	//CCBegin SS4
	public String getDwbs() {
		return this.dwbs;
	}

	public void setDwbs(String dwbs) {
		this.dwbs=dwbs;
	}
	
  public String getLastEffRoute()
  {
    return this.lastEffRoute;
  }

  public void setLastEffRoute(String lastEffRoute)
  {
    this.lastEffRoute = lastEffRoute;
  }
	//CCEnd SS4
	
	
	//CCBegin SS5
	public String getColorFlag()
	{
		return this.colorFlag;
	}
	
	public void setColorFlag(String colorFlag)
	{
		this.colorFlag=colorFlag;
	}
	//CCEnd SS5
	//CCBegin SS6
		public String getSpecialFlag()
		{
			return this.specialFlag;
		}
		
		public void setSpecialFlag(String specialFlag)
		{
			this.specialFlag=specialFlag;
		}
		//CCEnd SS6
		//CCBegin SS7
	    
	    public String getSourceVersion() {
	    	return this.sourceVersion;
	    };
	      
	    public void setSourceVersion(String sourceVersion) {
	    	this.sourceVersion = sourceVersion;
	    };
	  //CCEnd SS7
}
