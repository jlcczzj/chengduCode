/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/22 徐春英      原因:增加预留属性
 * CR2 2011/12/22 徐春英       原因：增加产品信息和路线信息
 * CR3 2012/01/10 徐春英      原因：增加路线生效时间和失效时间
 * SS1 轴齿中心增加“采购标识”属性 Liuyang 2013-12-24 
 * SS2 轴齿中心增加零部件版本属性 liuyang 2014-2-25
 * SS3 长特增加供应商 liuyang 2014-8-15
 * SS4 成都工艺路线整合，增加工厂dwbs、lastEffRoute属性。 liunan 2016-8-11 
 * SS5 成都工艺路线，增加颜色件标识colorFlag属性。 liunan 2016-10-25
 * SS6 成都工艺路线特殊处理件标识，为成都的零部件进行版本转换 徐德政 2018-01-11
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.faw_qm.framework.service.BinaryLink;

/**
 * 工艺路线对应的产品结构可以选择。产品的子零件 <p>Title: </p> <p>Description: leftID = routeListID, rightID = partMasterID.</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw_qm </p>
 * @author 管春元
 * @version 1.0 2005/07/07
 */
public interface ListRoutePartLink extends BinaryLink
{

    /**
     * 设置工艺路线ID。
     * @roseuid 4039BA590094
     */
    public void setRouteID(String routeID);

    /**
     * 得到路线的id
     * @return String
     */
    public String getRouteID();

    /**
     * 判断零件是否有工艺路线。因为零件对应的工艺路线通常不会删除，所以此处的工艺路线状 态作为持久化属性。 注意：当RoutePartLink删除时应维护此属性，保持数据的一致性。 数据的一致性在saveRoute和RoutePostDeleteListener中维护。
     * @return boolean
     */
    public String getAdoptStatus();

    /**
     * 设置零件的工艺路线是否被采用。
     * @param status 采用、取消。
     */
    public void setAdoptStatus(String status);

    //st skybird 2005.3.1 零部件的父件编号
    // gcy add in 2005.4.26 for reinforce requirement  start

    /**
     * 得到父件的编号
     * @return String 父件的编号
     */
    public String getParentPartNum();

    /**
     * 设置父件的编号
     * @param parentPartNum String
     */
    public void setParentPartNum(String parentPartNum);

    /**
     * 得到父件名
     * @return String 父件的名称
     */
    public String getParentPartName();

    /**
     * 设置父件的名称
     * @param parentPartNum String
     */
    public void setParentPartName(String parentPartNum);

    /**
     * 得到父件的id
     * @return String
     */
    public String getParentPartID();

    /**
     * 设置父件的id
     * @param parentPartNum String 父件的id
     */
    public void setParentPartID(String parentPartNum);

    /**
     * 得到当前零部件在父件中在产品中使用数量（不一定是直接上级）
     * @return int 零部件在父件中使用数量（不一定是直接上级）
     */
    public int getCount();

    /**
     * 设置零部件在父件中在产品中使用的数量（不一定是直接上级）
     * @param count int
     */
    public void setCount(int count);

    // gcy add in 2005.4.26 for reinforce requirement  end

    /**
     * 此标识用来判断与路线表关联的路线是否显示（在物料清单中）。是否处理采用、取消。
     * @return int =0 表示是从上一版本继承下来的； int=1，从本版本生成的。涉及到路线是否重新生成； int=2，此版本删除的，当路线表检出时，不复制此关联。
     */
    public int getAlterStatus();

    /**
     * 设置修改状态
     * @param alterStatus int int =0 表示是从上一版本继承下来的； int=1，从本版本生成的。涉及到路线是否重新生成； int=2，此版本删除的，当路线表检出时，不复制此关联。
     */
    public void setAlterStatus(int alterStatus);

    /**
     * 得到路线表的id
     * @return String 路线表的id
     */
    public String getRouteListMasterID();

    /**
     * 设置路线表的id
     * @param listMasterID String 路线表的id
     */
    public void setRouteListMasterID(String listMasterID);

    /**
     * 得到关联中路线表的小版本
     * @return String
     */
    public String getRouteListIterationID();

    /**
     * 设置关联中路线表的小版本
     * @param listIterationID String
     */
    public void setRouteListIterationID(String listIterationID);

    /**
     * 初始采用版本。A, B.大版本号。
     * @return String
     */
    public String getInitialUsed();

    /**
     * 设置采用版本。A, B.大版本号
     * @param version String
     */
    public void setInitialUsed(String version);

    //begin CR1
    /**
     * 设置预留属性1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1);

    /**
     * 获得预留属性1
     * @param attribute1
     * @return
     */
    public String getAttribute1();

    /**
     * 设置预留属性2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2);

    /**
     * 获得预留属性2
     * @param attribute2
     * @return
     */
    public String getAttribute2();

    //end CR1
    //begin CR2
    /**
     * 获得更改标记
     */
    public String getModifyIdenty();

    /**
     * 设置更改标记
     */
    public void setModifyIdenty(String identy);

    /**
     * 得到产品的id
     * @return String 产品的id
     */
    public String getProductID();

    /**
     * 设置产品的id
     * @param parentPartNum String 产品的id
     */
    public void setProductID(String productID);

    /**
     * 设置零部件的产品标识
     */
    public boolean getProductIdenty();

    /**
     * 设置零部件的产品标识
     * @param productIdenty boolean 零部件的产品标识
     */
    public void setProductIdenty(boolean productIdenty);

    /**
     * 得到产品数量
     * @return int 产品数量
     */
    public int getProductCount();

    /**
     * 设置产品数量
     * @param productCount int 产品数量
     */
    public void setProductCount(int productCount);

    /**
     * 得到路线发布信息
     * @return int 路线发布信息
     */
    public int getReleaseIdenty();

    /**
     * 设置路线发布信息
     * @param releaseIdenty int 路线发布信息
     */
    public void setReleaseIdenty(int releaseIdenty);

    /**
     * 得到主要路线串信息
     * @return String 主要路线串信息
     */
    public String getMainStr();

    /**
     * 设置主要路线串信息
     * @param mainStr String 主要路线串信息
     */
    public void setMainStr(String mainStr);

    /**
     * 得到次要路线串信息
     * @return String 次要路线串信息
     */
    public String getSecondStr();

    /**
     * 设置次要路线串信息
     * @param mainStr String 次要路线串信息
     */
    public void setSecondStr(String secondStr);
    
    /**
     * 路线说明
     */
    public java.lang.String getRouteDescription();

    /**
     * 路线说明。
     */
    public void setRouteDescription(String description);
    //end CR2
    
    //begin CR3
    /**
     * 获得 生效时间
     * @return Timestamp 生效时间
     */
    public Timestamp getEfficientTime();

    /**
     * 设置生效时间
     * @param efficientData 生效时间
     */
    public void setEfficientTime(Timestamp efficientTime);

    /**
     * 获得失效时间
     * @return Timestamp 失效时间
     */
    public Timestamp getDisabledDateTime();

    /**
     * 设置失效时间
     * @param disabledDateTime 失效时间
     */
    public void setDisabledDateTime(Timestamp disabledDateTime);
    //end CR3
    //CCBegin SS1
    /**
     * 得到采购标识
     */
    public String getStockID();
    /**
     * 设置采购标识
     * @param stockID
     */
    public void setStockID(String stockID);
  
    //CCEnd SS1
    
    //CCBegin SS2
    /**
     * 得到零部件版本
     */
    public String getPartVersion();
    /**
     * 设置零部件版本
     * @param partVersion
     * @return
     */
    public void setPartVersion(String partVersion);
    //CCEnd SS2
    //CCBegin SS3
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
    //CCEnd SS3
    //CCBegin SS4
    /**
     * 得到单位标识
     */
    public String getDwbs();
    /**
     * 设置单位标识
     */
    public void setDwbs(String dwbs);
    
    /**
     * 得到取消路线
     */
    public String getLastEffRoute();
    /**
     * 设置取消路线
     */
    public void setLastEffRoute(String lastEffRoute);
    //CCEnd SS4
    
    
    //CCBegin SS5
    /**
     * 颜色标识
     */
    public String getColorFlag();
    public void setColorFlag(String colorFlag);
    //CCEnd SS5
  //CCBegin SS6
    /**
     * 特殊处理标识
     */
    public String getSpecialFlag();
    public void setSpecialFlag(String specialFlag);
    //CCEnd SS6
}

