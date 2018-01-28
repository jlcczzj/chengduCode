/**
 * 生成程序RouteWrapData.java    1.0    2011-12-31
 * 版权归启明信息技术股份有限公司所有
 * 本程序属本公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 轴齿中心增加毛坯状态 liuyang 2013-1-13
 * SS2 轴齿中心增加零部件版本 liuyang 2014-2-25
 * SS3 长特增加供应商和供应商Bsoid liuyang 2014-8-20
 * SS4 成都增加颜色件标识colorFlag属性。 liunan 2016-10-25
 * SS5 成都工艺路线特殊处理件标识，为成都的零部件进行版本转换 徐德政 2018-01-11
 */
package com.faw_qm.technics.consroute.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;

/**
 * <p>客户端数据封装类</p> 
 * 用来在客户端与服务端传递路线数据的封装类， 它封装艺准使用零件的关联信息，零件信息以及零件路线信息
 * 启明信息技术股份有限公司
 * @author 徐春英
 * @version 1.0
 */
public class RouteWrapData implements Serializable
{
    static final long serialVersionUID = 1L;
    //艺准关联零件
    private String partMasterID;
    //路线更改标记
    private String modifyIdenty;
    //主要路线串
    private String mainStr;
    //次要路线串
    private String secondStr;
    //路线描述
    private String description;
    //产品数量
    private int productCount;
    //产品标识
    private boolean productIndenty = false;
    //父件编号
    String parentNum;
    //父件名称
    String parentName;
    //路线ID
    private String routeID;
    //艺准和零件关联ID
    private String linkID;
    //要存储的路线相关对象集合
    //20120116 xucy add  产品信息
    private QMPartMasterIfc product;
    //父件信息 
    private QMPartIfc parent;
    //路线信息集合
    private HashMap routeMap;
    //零件编号
    String partNum;
    //零件名称
    String partName;
    //零件行号
    int rowNum;
    //CCBegin SS1
    private String stockID;
    //CCend SS1
    //CCBegin SS2
    private String partVersion;
    //CCend SS2
    //CCBegin SS3
    private String supplier;
    private String supplierBsoId;
    //CCEnd SS3
    //CCBegin SS4
    private String colorFlag;
    //CCend SS4
  //CCBegin SS5
    private String specialFlag;
    //CCend SS5
    //存放数据库中不存在的路线单位集合
    private Object[] departmentVec;

    /**
     * RouteWrapData类的构造方法
     */
    public RouteWrapData()
    {
        super();
    }

    //CCBegin SS1
    /**
     * 获得毛坯状态
     */
    public String getStockID()
    {
    	return stockID;
    }
    /**
     * 设置毛坯状态
     * @param stockID
     */
    public void setStockID(String stockID)
    {
    	this.stockID=stockID;
    }
    //CCend SS1
    //CCBegin SS2

	public String getPartVersion() {
		
		return partVersion;
	}


	public void setPartVersion(String partVersion) {
		
		this.partVersion=partVersion;
	}
	//CCEnd SS2
	//CCBegin SS3
	 public String getSupplier()
	    {
	    	return supplier;
	    }

     public void setSupplier(String supplier)
	    {
	    	this.supplier=supplier;
	    }
     public String getSupplierBsoId()
	    {
	    	return supplierBsoId;
	    }

  public void setSupplierBsoId(String supplierBsoId)
	    {
	    	this.supplierBsoId=supplierBsoId;
	    }
	//CCend SS3
	
	//CCBegin SS4
	public String getColorFlag()
	{
		return colorFlag;
	}
	
	public void setColorFlag(String colorFlag)
	{	
		this.colorFlag=colorFlag;
	}
	//CCEnd SS4
	//CCBegin SS5
		public String getSpecialFlag()
		{
			return specialFlag;
		}
		
		public void setSpecialFlag(String specialFlag)
		{	
			this.specialFlag=specialFlag;
		}
		//CCEnd SS5
    /**
     * 获得艺准零件关联ID
     * @return String 艺准零件关联ID
     */
    public String getPartMasterID()
    {
        return partMasterID;
    }

    /**
     * 设置艺准零件关联ID
     * @param partMasterID String 艺准零件关联ID
     */
    public void setPartMasterID(String partMasterID)
    {
        this.partMasterID = partMasterID;
    }

    /**
     * 获得路线更改标记
     * @return String 路线更改标记
     */
    public String getModifyIdenty()
    {
        return modifyIdenty;
    }

    /**
     * 设置路线更改标记
     * @param codeContent String 路线更改标记
     * @see String
     */
    public void setModifyIdenty(String modifyIdenty)
    {
        this.modifyIdenty = modifyIdenty;
    }

    /**
     * 获得主要路线串
     * @return String 主要路线串
     */
    public String getMainStr()
    {
        return mainStr;
    }

    /**
     * 设置主要路线串
     * @param mainStr String 主要路线串
     */
    public void setMainStr(String mainStr)
    {
        this.mainStr = mainStr;
    }

    /**
     * 获得次要路线串
     * @return String 次要路线串
     */
    public String getSecondStr()
    {
        return secondStr;
    }

    /**
     * 设置次要路线串
     * @param secondStr String 次要路线串
     */
    public void setSecondStr(String secondStr)
    {
        this.secondStr = secondStr;
    }

    /**
     * 获得路线描述
     * @return String 路线描述
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * 设置路线描述
     * @param description String 路线描述
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * 获得产品数量
     * @return int 产品数量
     */
    public int getProductCount()
    {
        return productCount;
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
     * 获得产品标识
     * @return boolean 产品标识
     */
    public boolean getProductIndenty()
    {
        return productIndenty;
    }

    /**
     * 设置产品标识
     * @param productIndenty boolean 产品标识
     */
    public void setProductIndenty(boolean productIndenty)
    {
        this.productIndenty = productIndenty;
    }

    /**
     * 获得父件编号
     * @return String 父件编号
     */
    public String getParentNum()
    {
        return parentNum;
    }

    /**
     * 设置父件编号
     * @param parentNum String 父件编号
     */
    public void setParentNum(String parentNum)
    {
        this.parentNum = parentNum;
    }

    /**
     * 获得父件名称
     * @return String 父件名称
     */
    public String getParentName()
    {
        return parentName;
    }

    /**
     * 设置父件名称
     * @param parentName String 父件名称
     */
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    /**
     * 获得路线ID
     * @return String 路线ID
     */
    public String getRouteID()
    {
        return routeID;
    }

    /**
     * 设置路线ID
     * @param description String 路线ID
     */
    public void setRouteID(String routeID)
    {
        this.routeID = routeID;
    }

    /**
     * 获得艺准零件关联ID
     * @return String 艺准零件关联ID
     */
    public String getLinkID()
    {
        return linkID;
    }

    /**
     * 设置艺准零件关联ID
     * @param description String 艺准零件关联ID
     */
    public void setLinkID(String linkID)
    {
        this.linkID = linkID;
    }

    /**
     * 获得路线相关对象集合
     * @return HashMap 路线相关对象集合
     */
    public HashMap getRouteMap()
    {
        return routeMap;
    }

    /**
     * 设置路线相关对象集合
     * @param routeMap HashMap 路线相关对象集合
     */
    public void setRouteMap(HashMap routeMap)
    {
        this.routeMap = routeMap;
    }

    /**
     * 获得产品信息
     * @return QMPartMasterIfc 产品信息
     */
    public QMPartMasterIfc getProduct()
    {
        return product;
    }

    /**
     * 设置产品
     * @param description QMPartMasterIfc 产品信息
     */
    public void setProduct(QMPartMasterIfc product)
    {
        this.product = product;
    }
    
    /**
     * 获得父件信息
     * @return QMPartMasterIfc 父件信息
     */
    public QMPartIfc getParent()
    {
        return parent;
    }

    /**
     * 设置父件
     * @param description QMPartIfc 父件信息
     */
    public void setParent(QMPartIfc parent)
    {
        this.parent = parent;
    }
    
    /**
     * 获得零件编号
     * @return String 零件编号
     */
    public String getPartNum()
    {
        return this.partNum;
    }

    /**
     * 设置零件编号
     * @param partNum String 零件编号
     */
    public void setPartNum(String partNum)
    {
        this.partNum = partNum;
    }

    /**
     * 获得零件名称
     * @return String 零件名称
     */
    public String getPartName()
    {
        return this.partName;
    }

    /**
     * 设置零件名称
     * @param partName String 零件名称
     */
    public void setPartName(String partName)
    {
        this.partName = partName;
    }

    /**
     * 获得零件行号
     * @return int 零件行号
     */
    public int getRowNum()
    {
        return this.rowNum;
    }

    /**
     * 设置零件行号
     * @param partName int 零件行号
     */
    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }
    
    
    /**
     * 获得路线单位集合
     * @return Vector 路线单位集合
     */
    public Object[] getDepartmentVec()
    {
        return this.departmentVec;
    }

    /**
     * 设置路线单位集合
     * @param partName Vector 路线单位集合
     */
    public void setDepartmentVec(Object[] departmentVec)
    {
        this.departmentVec = departmentVec;
    }
    
    
    /**
     * 将当前类的非静态和非瞬态字段写入此流
     * @param oos ObjectOutputStream 输入流 @
     * @throws QMException 
     */
    private void writeObject(ObjectOutputStream oos) throws QMException
    {
        try
        {
            oos.defaultWriteObject();
        }catch(Exception ex)
        {
            throw new QMException(ex);
        }
    }

    /**
     * 从此流读取当前类的非静态和非瞬态字段
     * @param ois ObjectInputStream 输出流 @ @
     * @throws QMException 
     */
    private void readObject(ObjectInputStream ois) throws QMException
    {
        try
        {
            ois.defaultReadObject();
        }catch(Exception ex)
        {
            throw new QMException(ex);
        }
    }
}
