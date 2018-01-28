/** 生成程序 ListRoutePartLinkEJB 1.0 2005.3.2
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/22 徐春英      原因:增加预留属性
 * CR2 2011/12/22 徐春英       原因：增加产品信息和路线信息
 * CR3 2011/12/30 徐春英      原因：通过关联对象获得路线值对象
 * CR4 2012/01/10 徐春英      原因：增加路线生效时间和失效时间
 * SS1 变速箱工艺路线重复添加同一零件不同版本 pante 2013-11-16
 * SS2 轴齿中心增加“采购标识” Liuyang 2013-12-24 
 * SS3 轴齿中心增加零部件版本属性 liuyang 2014-2-25
 * SS4 变速箱要求可以添加广义零部件。 liunan 2014-11-4
 * SS5 长特增加供应商 liuyang 2014-8-15
 * SS6 成都工艺路线整合，增加工厂dwbs、lastEffRoute属性。 liunan 2016-8-11
 * SS7 成都工艺路线，增加颜色件标识colorFlag属性。 liunan 2016-10-25
 * SS8 成都工艺路线特殊处理件标识，为成都的零部件进行版本转换 徐德政 2018-01-11
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;

import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.exception.CodeManageException;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.util.RouteAdoptedType;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * 工艺路线对应的产品结构可以选择。产品的子零件
 * @author 管春元
 * @version 1.0
 */
abstract public class ListRoutePartLinkEJB extends BinaryLinkEJB
{
    /**
     * 构造函数
     */
    public ListRoutePartLinkEJB()
    {

    }

    /**
     * 得到业务类名
     * @return String ListRoutePartLink
     */
    public String getBsoName()
    {
        return "consListRoutePartLink";
    }

    /**
     * 设置工艺路线ID
     * @param routeID String 工艺路线ID
     */
    public abstract void setRouteID(String routeID);

    /**
     * 得到工艺路线ID
     * @return String 工艺路线ID
     */
    public abstract String getRouteID();

    /**
     * 判断零件是否有工艺路线。因为零件对应的工艺路线通常不会删除，所以此处的工艺路线状 态作为持久化属性。 注意：当RoutePartLink删除时应维护此属性，保持数据的一致性。 数据的一致性在saveRoute和RoutePostDeleteListener中维护。
     * @return boolean
     */
    public abstract String getAdoptStatus();

    /**
     * 设置零件的工艺路线是否被采用。
     * @param status 采用、取消。
     */
    public abstract void setAdoptStatus(String status);

    //st skybird 2005.3.1
    // gcy add in 2005.4.26 for reinforce requirement  start

    /**
     * 得到父件的编号
     * @return String 父件的编号
     */
    public abstract String getParentPartNum();

    /**
     * 设置父件的编号
     * @param parentPartNum String 父件的编号
     */
    public abstract void setParentPartNum(String parentPartNum);

    /**
     * 得到父件名
     * @return String 父件名
     */
    public abstract String getParentPartName();

    /**
     * 设置父件的名称
     * @param parentPartNum String 父件的名称
     */
    public abstract void setParentPartName(String parentPartNum);

    /**
     * 得到父件的id
     * @return String 父件的id
     */
    public abstract String getParentPartID();

    /**
     * 设置父件的id
     * @param parentPartNum String 父件的id
     */
    public abstract void setParentPartID(String parentPartNum);

    /**
     * 得到当前零部件在父件中使用数量（不一定是直接上级）
     * @return String 当前零部件在父件中使用数量
     */
    public abstract int getCount();

    /**
     * 设置零部件在父件中在产品中使用的数量（不一定是直接上级）
     * @param count int 零部件在父件中在产品中使用的数量
     */
    public abstract void setCount(int count);

    // gcy add in 2005.4.26 for reinforce requirement  end

    /**
     * 获得用来判断与路线表关联的路线是否显示的标识（在物料清单中）。是否处理采用、取消。
     * @return alterStatus=0表示是从上一版本继承下来的。否则alterStatus=1，从本版本生成的。涉及到路线是否重新生成。
     */
    public abstract int getAlterStatus();

    /**
     * 设置修改状态
     * @param alterStatus int int =0 表示是从上一版本继承下来的； int=1，从本版本生成的。涉及到路线是否重新生成； int=2，此版本删除的，当路线表检出时，不复制此关联。
     */
    public abstract void setAlterStatus(int alterStatus);

    /**
     * 初始采用版本。A, B.大阪本号。
     * @return int
     */
    public abstract String getInitialUsed();

    /**
     * 设置采用版本。A, B.大版本号
     * @param version String 设置采用版本
     */
    public abstract void setInitialUsed(String version);

    /**
     * 得到路线表的id
     * @return String 路线表的id
     */
    public abstract String getRouteListMasterID();

    /**
     * 设置路线表的id
     * @param listMasterID String 路线表的id
     */
    public abstract void setRouteListMasterID(String listMasterID);

    /**
     * 得到关联中路线表的小版本
     * @return String 路线表的小版本
     */
    public abstract String getRouteListIterationID();

    /**
     * 设置关联中路线表的小版本
     * @param listIterationID String 关联中路线表的小版本
     */
    public abstract void setRouteListIterationID(String listIterationID);

    //begin CR1
    /**
     * 设置预留属性1
     */
    public abstract void setAttribute1(String attribute1);

    /**
     * 获得预留属性1
     */
    public abstract String getAttribute1();

    /**
     * 设置预留属性2
     */
    public abstract void setAttribute2(String attribute2);

    /**
     * 获得预留属性2
     */
    public abstract String getAttribute2();

    //end CR1

    //begin CR2
    /**
     * 获得更改标记
     */
    public abstract String getModifyIdenty();

    /**
     * 设置更改标记
     */
    public abstract void setModifyIdenty(String identy);

    /**
     * 得到产品的id
     * @return String 产品的id
     */
    public abstract String getProductID();

    /**
     * 设置产品的id
     * @param parentPartNum String 产品的id
     */
    public abstract void setProductID(String productID);

    /**
     * 设置零部件的产品标识
     */
    public abstract boolean getProductIdenty();

    /**
     * 设置零部件的产品标识
     * @param productIdenty boolean 零部件的产品标识
     */
    public abstract void setProductIdenty(boolean productIdenty);

    /**
     * 得到产品数量
     * @return int 产品数量
     */
    public abstract int getProductCount();

    /**
     * 设置产品数量
     * @param productCount int 产品数量
     */
    public abstract void setProductCount(int productCount);

    /**
     * 得到路线发布信息
     * @return int 路线发布信息
     */
    public abstract int getReleaseIdenty();

    /**
     * 设置路线发布信息
     * @param releaseIdenty int 路线发布信息
     */
    public abstract void setReleaseIdenty(int releaseIdenty);

    /**
     * 得到主要路线串信息
     * @return String 主要路线串信息
     */
    public abstract String getMainStr();

    /**
     * 设置主要路线串信息
     * @param mainStr String 主要路线串信息
     */
    public abstract void setMainStr(String mainStr);

    /**
     * 得到次要路线串信息
     * @return String 次要路线串信息
     */
    public abstract String getSecondStr();

    /**
     * 设置次要路线串信息
     * @param mainStr String 次要路线串信息
     */
    public abstract void setSecondStr(String secondStr);

    /**
     * 路线说明
     */
    public abstract java.lang.String getRouteDescription();

    /**
     * 路线说明。
     */
    public abstract void setRouteDescription(String description);
    //end CR2

    //begin CR4
    /**
     * 获得 生效时间
     * @return Timestamp 生效时间
     */
    public abstract Timestamp getEfficientTime();

    /**
     * 设置生效时间
     * @param efficientData 生效时间
     */
    public abstract void setEfficientTime(Timestamp efficientTime);

    /**
     * 获得失效时间
     * @return Timestamp 失效时间
     */
    public abstract Timestamp getDisabledDateTime();

    /**
     * 设置失效时间
     * @param disabledDateTime 失效时间
     */
    public abstract void setDisabledDateTime(Timestamp disabledDateTime);
    //end CR4
    //CCBegin SS2
    /**
     * 得到采购标识
     */
    public abstract String getStockID();
    /**
     * 设置采购标识
     * @param stockID
     */
    public abstract void setStockID(String stockID);
    //CCEnd SS2
    //CCBegin SS3
    /**
     * 获取零部件版本
     */
	public abstract String getPartVersion();
    /**
     * 设置零部件版本
     * @param partVersion
     */
	public abstract void setPartVersion(String partVersion);
	//CCEnd SS3
	//CCBegin SS5
	  /**
	   * 得到供应商
	   */
	  public abstract String getSupplier();
	  /**
	   * 设置供应商
	   * @param supplier
	   * @return
	   */
	  public abstract void setSupplier(String supplier);
	  /**
	   * 得到供应商
	   */
	  public abstract String getSupplierBsoId();
	  /**
	   * 设置供应商
	   * @param supplier
	   * @return
	   */
	  public abstract void setSupplierBsoId(String supplierBsoId);
	  //CCEnd SS5
	  
    //CCBegin SS6
    /**
     * 得到单位标识
     */
    public abstract String getDwbs();
    /**
     * 设置单位标识
     * @param stockID
     */
    public abstract void setDwbs(String dwbs);
    
    /**
     * 得到取消路线
     */
    public abstract String getLastEffRoute();
    /**
     * 设置取消路线
     * @param stockID
     */
    public abstract void setLastEffRoute(String lastEffRoute);
    //CCEnd SS6
    
    //CCBegin SS7
    /**
     * 颜色标识 
     */
    public abstract String getColorFlag();
    public abstract void setColorFlag(String colorFlag);
    //CCEnd SS7
  //CCBegin SS8
    /**
     * 特殊处理标识 
     */
    public abstract String getSpecialFlag();
    public abstract void setSpecialFlag(String specialFlag);
    //CCEnd SS8
    
    /**
     * 获得值对象。
     * @throws QMException
     * @return BaseValueIfc
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        ListRoutePartLinkInfo info = ListRoutePartLinkInfo.newListRoutePartLinkInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * 对新建值对象进行设置。
     * @param info BaseValueIfc 值对象
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        ListRoutePartLinkInfo listPartInfo = (ListRoutePartLinkInfo)info;
        if(this.getAdoptStatus() != null && RouteAdoptedType.toRouteAdoptedType(this.getAdoptStatus()) != null)
        {
            listPartInfo.setAdoptStatus(RouteAdoptedType.toRouteAdoptedType(this.getAdoptStatus()).getDisplay());
        }
        listPartInfo.setInitialUsed(this.getInitialUsed());
        listPartInfo.setRouteListMasterID(this.getRouteListMasterID());
        listPartInfo.setRouteListIterationID(this.getRouteListIterationID());
        listPartInfo.setAlterStatus(this.getAlterStatus());
        //st skybird 2005.3.1
        // gcy add in 2005.4.26 for reinforce requirement  start
        listPartInfo.setParentPartNum(this.getParentPartNum());
        listPartInfo.setParentPartName(this.getParentPartName());
        listPartInfo.setParentPartID(this.getParentPartID());
        listPartInfo.setCount(this.getCount());
        if(this.getParentPartID() != null && this.getParentPartID().trim().length() != 0)
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            try
            {
                QMPartIfc partInfo = (QMPartIfc)pservice.refreshInfo(this.getParentPartID());
                listPartInfo.setParentPart(partInfo);
            }catch(Exception e)
            {
                //System.out.println("父件删除");
            }
        }
        // gcy add in 2005.4.26 for reinforce requirement  end
        //ed
        listPartInfo.setRouteListID(this.getLeftBsoID());
        listPartInfo.setPartMasterID(this.getRightBsoID());
        listPartInfo.setRouteID(this.getRouteID());
        if(this.getRightBsoID() != null && this.getRightBsoID().trim().length() != 0)
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            //CCBegin SS1
            QMPartIfc part = null;
            QMPartMasterIfc partMasterInfo = null;
            if(this.getRightBsoID().contains("QMPart_")){
            	part = (QMPartIfc)pservice.refreshInfo(this.getRightBsoID());
            	partMasterInfo = (QMPartMasterIfc)pservice.refreshInfo(part.getMasterBsoID());
            }
            //CCBegin SS4
            else if(this.getRightBsoID().contains("GenericPart_")){
            	part = (QMPartIfc)pservice.refreshInfo(this.getRightBsoID());
            	partMasterInfo = (QMPartMasterIfc)pservice.refreshInfo(part.getMasterBsoID());
            }
            //CCEnd SS4
            else
            	partMasterInfo = (QMPartMasterIfc)pservice.refreshInfo(this.getRightBsoID());
          //CCEnd SS1
            listPartInfo.setPartMasterInfo(partMasterInfo);
        }else
        {
            throw new QMException("partMasterID 在listRoutePart关联中不应为空");
        }
        //begin CR1
        listPartInfo.setAttribute1(this.getAttribute1());
        listPartInfo.setAttribute2(this.getAttribute2());
        //end CR1
        //begin CR2
        if(this.getModifyIdenty() != null)
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            CodingIfc codInfo = (CodingIfc)pservice.refreshInfo(this.getModifyIdenty());
            listPartInfo.setModifyIdenty(codInfo.getCodeContent());
        }
        listPartInfo.setProductID(this.getProductID());
        listPartInfo.setProductIdenty(this.getProductIdenty());
        listPartInfo.setProductCount(this.getProductCount());
        listPartInfo.setReleaseIdenty(this.getReleaseIdenty());
        listPartInfo.setMainStr(this.getMainStr());
        listPartInfo.setSecondStr(this.getSecondStr());
        listPartInfo.setRouteDescription(this.getRouteDescription());
        //end CR2
        //begin CR3
        if(this.getRouteID() != null && this.getRouteID().trim().length() != 0)
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)pservice.refreshInfo(this.getRouteID());
            listPartInfo.setRouteInfo(routeInfo);
        }else
        {
            listPartInfo.setRouteInfo(null);
        }
        //end CR3
        //begin CR4
        listPartInfo.setEfficientTime(this.getEfficientTime());
        listPartInfo.setDisabledDateTime(this.getDisabledDateTime());
        //end CR4
        //CCBegin SS2
        listPartInfo.setStockID(this.getStockID());
        //CCEnd SS2
        //CCBegin SS3
        listPartInfo.setPartVersion(this.getPartVersion());
        //CCEnd SS3
        //CCBegin SS5
        listPartInfo.setSupplier(this.getSupplier());
        listPartInfo.setSupplierBsoId(this.getSupplierBsoId());
        //CCEnd SS5
        //CCBegin SS6
        listPartInfo.setDwbs(this.getDwbs());
        listPartInfo.setLastEffRoute(this.getLastEffRoute());
        //CCEnd SS6
        //CCBegin SS7
        listPartInfo.setColorFlag(this.getColorFlag());
        //CCEnd SS7
      //CCBegin SS8
        listPartInfo.setSpecialFlag(this.getSpecialFlag());
        //CCEnd SS8
    }

    /**
     * 根据值对象进行持久化。
     * @param info BaseValueIfc 值对象
     * @see BaseValueInfo
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException 
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter ListRoutePartLinkEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        ListRoutePartLinkInfo listPartInfo = (ListRoutePartLinkInfo)info;
        this.setAdoptStatus(RouteHelper.getValue(RouteAdoptedType.getRouteAdoptedTypeSet(), listPartInfo.getAdoptStatus()));
        this.setInitialUsed(listPartInfo.getInitialUsed());
        this.setRouteListMasterID(listPartInfo.getRouteListMasterID());
        this.setRouteListIterationID(listPartInfo.getRouteListIterationID());
        this.setAlterStatus(listPartInfo.getAlterStatus());
        //st skybird 2005.3.1 零部件的父件编号
        // gcy add in 2005.4.26 for reinforce requirement  start
        this.setParentPartNum(listPartInfo.getParentPartNum());
        this.setParentPartName(listPartInfo.getParentPartName());
        this.setParentPartID(listPartInfo.getParentPartID());
        this.setCount(listPartInfo.getCount());
        // gcy add in 2005.4.26 for reinforce requirement  end
        //ed
        this.setRouteID(listPartInfo.getRouteID());
        //begin CR1
        this.setAttribute1(listPartInfo.getAttribute1());
        this.setAttribute2(listPartInfo.getAttribute2());
        //end CR1
        //begin CR2
        String identy = listPartInfo.getModifyIdenty();
        CodingManageService codeService;
		try {
			codeService = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
		
        Collection codes = codeService.findCoding("consTechnicsRoute", "modifyIdenty");
        if(codes != null && codes.size() > 0)
        {
            Iterator i = codes.iterator();
            String codeID = null;
            while(i.hasNext())
            {
                CodingIfc code = (CodingIfc)i.next();
                if(code.getCodeContent().equals(identy))
                    codeID = code.getBsoID();
            }
            this.setModifyIdenty(codeID);
        }
        this.setProductID(listPartInfo.getProductID());
        this.setProductIdenty(listPartInfo.getProductIdenty());
        this.setProductCount(listPartInfo.getProductCount());
        this.setReleaseIdenty(listPartInfo.getReleaseIdenty());
        this.setMainStr(listPartInfo.getMainStr());
        this.setSecondStr(listPartInfo.getSecondStr());
        this.setRouteDescription(listPartInfo.getRouteDescription());
        //end CR2
        
        //begin CR4
        this.setEfficientTime(listPartInfo.getEfficientTime());
        this.setDisabledDateTime(listPartInfo.getDisabledDateTime());
        //end CR4
        //CCBegin SS2
        this.setStockID(listPartInfo.getStockID());
        //CCEnd SS2
        //CCBegin SS3
        this.setPartVersion(listPartInfo.getPartVersion());
        //CCEnd SS3
        //CCBegin SS5
        this.setSupplier(listPartInfo.getSupplier());
        this.setSupplierBsoId(listPartInfo.getSupplierBsoId());
        //CCEnd SS5
        //CCBegin SS6
        this.setDwbs(listPartInfo.getDwbs());
        this.setLastEffRoute(listPartInfo.getLastEffRoute());
        //CCEnd SS6
        //CCBegin SS7
        this.setColorFlag(listPartInfo.getColorFlag());
        //CCEnd SS7
      //CCBegin SS8
        this.setSpecialFlag(listPartInfo.getSpecialFlag());
        //CCEnd SS8
		} catch (QMException e) {
			e.printStackTrace();
			return;
		}
    }

    /**
     * 根据值对象进行更新。
     * @param info BaseValueIfc 值对象
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter ListRoutePartLinkEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        ListRoutePartLinkInfo listPartInfo = (ListRoutePartLinkInfo)info;
        this.setAdoptStatus(RouteHelper.getValue(RouteAdoptedType.getRouteAdoptedTypeSet(), listPartInfo.getAdoptStatus()));
        // gcy add in 2005.4.26 for reinforce requirement  start
        this.setParentPartNum(listPartInfo.getParentPartNum());
        this.setParentPartName(listPartInfo.getParentPartName());
        this.setParentPartID(listPartInfo.getParentPartID());
        this.setCount(listPartInfo.getCount());
        // gcy add in 2005.4.26 for reinforce requirement  end
        this.setInitialUsed(listPartInfo.getInitialUsed());
        this.setRouteListMasterID(listPartInfo.getRouteListMasterID());
        this.setRouteListIterationID(listPartInfo.getRouteListIterationID());
        this.setAlterStatus(listPartInfo.getAlterStatus());
        this.setRouteID(listPartInfo.getRouteID());
        //begin CR1
        this.setAttribute1(listPartInfo.getAttribute1());
        this.setAttribute2(listPartInfo.getAttribute2());
        //end CR1
        //begin CR2
        String identy = listPartInfo.getModifyIdenty();
        CodingManageService codeService = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
        Collection codes = codeService.findCoding("consTechnicsRoute", "modifyIdenty");
        if(codes != null && codes.size() > 0)
        {
            Iterator i = codes.iterator();
            String codeID = null;
            while(i.hasNext())
            {
                CodingIfc code = (CodingIfc)i.next();
                if(code.getCodeContent().equals(identy))
                    codeID = code.getBsoID();
            }
            this.setModifyIdenty(codeID);
        }
        this.setProductID(listPartInfo.getProductID());
        this.setProductIdenty(listPartInfo.getProductIdenty());
        this.setProductCount(listPartInfo.getProductCount());
        this.setReleaseIdenty(listPartInfo.getReleaseIdenty());
        this.setMainStr(listPartInfo.getMainStr());
        this.setSecondStr(listPartInfo.getSecondStr());
        this.setRouteDescription(listPartInfo.getRouteDescription());
        //end CR2
        
        //begin CR4
        this.setEfficientTime(listPartInfo.getEfficientTime());
        this.setDisabledDateTime(listPartInfo.getDisabledDateTime());
        //end CR4
      //CCBegin SS2
        this.setStockID(listPartInfo.getStockID());
        //CCEnd SS2
        //CCBegin SS3
        this.setPartVersion(listPartInfo.getPartVersion());
        //CCEnd SS3
        //CCBegin SS5
        this.setSupplier(listPartInfo.getSupplier());
        this.setSupplierBsoId(listPartInfo.getSupplierBsoId());
        //CCEnd SS5
        //CCBegin SS6
        this.setDwbs(listPartInfo.getDwbs());
        this.setLastEffRoute(listPartInfo.getLastEffRoute());
        //CCEnd SS6
        //CCBegin SS7
        this.setColorFlag(listPartInfo.getColorFlag());
        //CCEnd SS7
      //CCBegin SS8
        this.setSpecialFlag(listPartInfo.getSpecialFlag());
        //CCEnd SS8
    }
}
