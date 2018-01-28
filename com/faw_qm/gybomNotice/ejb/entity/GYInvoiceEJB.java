package com.faw_qm.gybomNotice.ejb.entity;


import java.sql.Timestamp;

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;

import com.faw_qm.gybomNotice.model.GYInvoiceInfo;


public abstract class GYInvoiceEJB extends BsoReferenceEJB
{


    /**
     * 获得BsoName
     * @return BsoName "GYInvoice"
     */
    public String getBsoName()
    {
        return "GYInvoice";
    } 
   
    /**
     * 获得零件编号
     *@return 零件编号
     */
	public abstract String getPartNumber();
    /**
     * 设置零件编号
     *@param 零件编号
     */
	public abstract void setPartNumber(String partNumber) ;
    /**
     * 获得零件名称
     *@return 零件名称
     */
	public abstract String getPartName() ;
    /**
     * 设置零件名称
     *@param 零件名称
     */
	public abstract void setPartName(String partName) ;
    /**
     * 获得层级
     *@return 零件层级
     */
	public abstract String getLevel1() ;
    /**
     * 设置零件层级
     *@param 零件层级
     */
	public abstract void setLevel1(String level1);
    /**
     * 获得零件bsoID
     *@return 零件零件bsoID
     */
	public abstract String getPartID() ;
    /**
     * 设置零件bsoID
     *@param 零件bsoID
     */
	public abstract void setPartID(String partID);
    /**
     * 获得通知单ID
     *@return 通知单ID
     */
	public abstract String getNoticeID() ;
    /**
     * 设置通知单ID
     *@param 通知单ID
     */
	public abstract void setNoticeID(String noticeID) ;
    /**
     * 获得版本
     *@return 版本
     */
	public abstract String getVersionValue() ;
    /**
     * 设置版本
     *@param 版本
     */
	public abstract void setVersionValue(String versionValue) ;
    /**
     * 获得生命周期状态
     *@return 生命周期状态
     */
	public abstract String getLifecyclestate() ;
    /**
     * 设置生命周期状态
     *@param 生命周期状态
     */
	public abstract void setLifecyclestate(String lifecyclestate) ;
    /**
     * 获得视图
     *@return 视图
     */
	public abstract String getPartView() ;
    /**
     * 设置视图
     *@param 视图
     */
	public abstract void setPartView(String partView) ;
    /**
     * 获得虚拟件
     *@return 虚拟件
     */
	public abstract String getVirtualPart();
    /**
     * 设置虚拟件
     *@param 虚拟件
     */
	public abstract void setVirtualPart(String virtualPart) ;
    /**
     * 获得制造路线
     *@return 制造路线
     */
	public abstract String getZzlx() ;
    /**
     * 设置制造路线
     *@param 制造路线
     */
	public abstract void setZzlx(String zzlx) ;
    /**
     * 获得装配路线
     *@return 装配路线
     */
	public abstract String getZplx() ;
    /**
     * 设置装配路线
     *@param 装配路线
     */
	public abstract void setZplx(String zplx) ;
    /**
     * 获得备注
     *@return 备注
     */
	public abstract String getBz1() ;
    /**
     * 设置备注
     *@param 备注
     */
	public abstract void setBz1(String bz1) ;
    /**
     * 获得备注
     *@return 备注
     */
	public abstract String getBz2() ;
    /**
     * 设置备注
     *@param 备注
     */
	public abstract void setBz2(String bz2) ;
    /**
     * 获得备注
     *@return 备注
     */
	public abstract String getBz3() ;
    /**
     * 设置备注
     *@param 备注
     */
	public abstract void setBz3(String bz3) ;
    /**
     * 获得备注
     *@return 备注
     */
	public abstract String getBz4() ;
    /**
     * 设置备注
     *@param 备注
     */
	public abstract void setBz4(String bz4) ;
    /**
     * 设置数量
     * @param Source
     */
    public abstract void setSl(String sl);
    
    /**
     * 获得数量
     * @return
     */
    public abstract String getSl();



    /**
	   * 获得值对象。
	   */
	  public BaseValueIfc getValueInfo() throws QMException {
		  GYInvoiceInfo info = new GYInvoiceInfo();
	      setValueInfo(info);
	      return info;
	  }
	  
    /**
     * 设置值对象
     * @param info 值对象
     * @exception QMException;
     */
    public void setValueInfo(BaseValueIfc info) throws QMException {
		super.setValueInfo(info);
		GYInvoiceInfo cqInfo = (GYInvoiceInfo) info;
		cqInfo.setLevel1(this.getLevel1());
		cqInfo.setLifecyclestate(this.getLifecyclestate());
		cqInfo.setNoticeID(this.getNoticeID());
		cqInfo.setPartID(this.getPartID());
		cqInfo.setPartName(this.getPartName());
		cqInfo.setPartNumber(this.getPartNumber());
		cqInfo.setPartView(this.getPartView());
		cqInfo.setVersionValue(this.getVersionValue());
		cqInfo.setVirtualPart(this.getVirtualPart());
		cqInfo.setZplx(this.getZplx());
		cqInfo.setZzlx(this.getZzlx());
		cqInfo.setBz1(this.getBz1());
		cqInfo.setBz2(this.getBz2());
		cqInfo.setBz3(this.getBz3());
		cqInfo.setBz4(this.getBz4());
		cqInfo.setSl(this.getSl());
	}
    
    /**
     * 创建值对象
     * @param info 值对象
     * @exception CreateException
     */
    public void createByValueInfo(BaseValueIfc info)
            throws CreateException
    {
    	super.createByValueInfo(info);
    	GYInvoiceInfo cqInfo=(GYInvoiceInfo)info;
    	this.setLevel1(cqInfo.getLevel1());
    	this.setLifecyclestate(cqInfo.getLifecyclestate());
    	this.setNoticeID(cqInfo.getNoticeID());
    	this.setPartID(cqInfo.getPartID());
    	this.setPartName(cqInfo.getPartName());
    	this.setPartNumber(cqInfo.getPartNumber());
    	this.setPartView(cqInfo.getPartView());
    	this.setVersionValue(cqInfo.getVersionValue());
    	this.setVirtualPart(cqInfo.getVirtualPart());
    	this.setZplx(cqInfo.getZplx());
    	this.setZzlx(cqInfo.getZzlx());
    	this.setBz1(cqInfo.getBz1());
    	this.setBz2(cqInfo.getBz2());
    	this.setBz3(cqInfo.getBz3());
    	this.setBz4(cqInfo.getBz4());
    	this.setSl(cqInfo.getSl());

    }
    
    /**
     * 更新值对象
     * @param info 值对象
     * @exception QMException
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException {
		super.updateByValueInfo(info);
		GYInvoiceInfo cqInfo = (GYInvoiceInfo) info;
		this.setLevel1(cqInfo.getLevel1());
		this.setLifecyclestate(cqInfo.getLifecyclestate());
		this.setNoticeID(cqInfo.getNoticeID());
		this.setPartID(cqInfo.getPartID());
		this.setPartName(cqInfo.getPartName());
		this.setPartNumber(cqInfo.getPartNumber());
		this.setPartView(cqInfo.getPartView());
		this.setVersionValue(cqInfo.getVersionValue());
		this.setVirtualPart(cqInfo.getVirtualPart());
		this.setZplx(cqInfo.getZplx());
		this.setZzlx(cqInfo.getZzlx());
		this.setBz1(cqInfo.getBz1());
		this.setBz2(cqInfo.getBz2());
		this.setBz3(cqInfo.getBz3());
		this.setBz4(cqInfo.getBz4());
		this.setSl(cqInfo.getSl());
	}
}
