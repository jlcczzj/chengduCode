package com.faw_qm.gybomNotice.model;

import java.sql.Timestamp;

import com.faw_qm.enterprise.model.ManagedInfo;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.lock.exception.LockException;
import com.faw_qm.lock.model.LockMap;

public class GYInvoiceInfo extends BaseValueInfo implements GYInvoiceIfc {
	

	private static final long serialVersionUID = 1L;
    //零件编号
	private String partNumber;
	//零件名称
	private String partName;
	//层级 
	private String level1;
	//关联零件bsoID
	private String partID;
	//通知单编号 
	private String noticeID;
	//版本
	private String versionValue;
	//生命周期状态
	private String lifecyclestate;
	//零件视图
	private String partView;
	//虚拟件
	private String virtualPart;
	//制造路线
	private String zzlx;
	//装配路线
	private String zplx;
	//备注1
	private String bz1;
	//备注2
	private String bz2;
	//备注3
	private String bz3;
	//备注4
	private String bz4;
	private String sl;
    /**
     * 构造值对象
     */
    public GYInvoiceInfo()
    {
        super();

    }
    /**
     * 获得零件编号
     *@return 零件编号
     */
	public String getPartNumber() {
		return partNumber;
	}
    /**
     * 设置零件编号
     *@param 零件编号
     */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
    /**
     * 获得零件名称
     *@return 零件名称
     */
	public String getPartName() {
		return partName;
	}
    /**
     * 设置零件名称
     *@param 零件名称
     */
	public void setPartName(String partName) {
		this.partName = partName;
	}
    /**
     * 获得层级
     *@return 零件层级
     */
	public String getLevel1() {
		return level1;
	}
    /**
     * 设置零件层级
     *@param 零件层级
     */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}
    /**
     * 获得零件bsoID
     *@return 零件零件bsoID
     */
	public String getPartID() {
		return partID;
	}
    /**
     * 设置零件bsoID
     *@param 零件bsoID
     */
	public void setPartID(String partID) {
		this.partID = partID;
	}
    /**
     * 获得通知单ID
     *@return 通知单ID
     */
	public String getNoticeID() {
		return noticeID;
	}
    /**
     * 设置通知单ID
     *@param 通知单ID
     */
	public void setNoticeID(String noticeID) {
		this.noticeID = noticeID;
	}
  
    /**
     * 获得版本
     *@return 版本
     */
	public String getVersionValue() {
		return versionValue;
	}
    /**
     * 设置版本
     *@param 版本
     */
	public void setVersionValue(String versionValue) {
		this.versionValue = versionValue;
	}
    /**
     * 获得生命周期状态
     *@return 生命周期状态
     */
	public String getLifecyclestate() {
		return lifecyclestate;
	}
    /**
     * 设置生命周期状态
     *@param 生命周期状态
     */
	public void setLifecyclestate(String lifecyclestate) {
		this.lifecyclestate = lifecyclestate;
	}
    /**
     * 获得视图
     *@return 视图
     */
	public String getPartView() {
		return partView;
	}
    /**
     * 设置视图
     *@param 视图
     */
	public void setPartView(String partView) {
		this.partView = partView;
	}
    /**
     * 获得虚拟件
     *@return 虚拟件
     */
	public String getVirtualPart() {
		return virtualPart;
	}
    /**
     * 设置虚拟件
     *@param 虚拟件
     */
	public void setVirtualPart(String virtualPart) {
		this.virtualPart = virtualPart;
	}
    /**
     * 获得制造路线
     *@return 制造路线
     */
	public String getZzlx() {
		return zzlx;
	}
    /**
     * 设置制造路线
     *@param 制造路线
     */
	public void setZzlx(String zzlx) {
		this.zzlx = zzlx;
	}
    /**
     * 获得装配路线
     *@return 装配路线
     */
	public String getZplx() {
		return zplx;
	}
    /**
     * 设置装配路线
     *@param 装配路线
     */
	public void setZplx(String zplx) {
		this.zplx = zplx;
	}
    /**
     * 获得备注
     *@return 备注
     */
	public String getBz1() {
		return bz1;
	}
    /**
     * 设置备注
     *@param 备注
     */
	public void setBz1(String bz1) {
		this.bz1 = bz1;
	}
    /**
     * 获得备注
     *@return 备注
     */
	public String getBz2() {
		return bz2;
	}
    /**
     * 设置备注
     *@param 备注
     */
	public void setBz2(String bz2) {
		this.bz2 = bz2;
	}
    /**
     * 获得备注
     *@return 备注
     */
	public String getBz3() {
		return bz3;
	}
    /**
     * 设置备注
     *@param 备注
     */
	public void setBz3(String bz3) {
		this.bz3 = bz3;
	}
    /**
     * 获得备注
     *@return 备注
     */
	public String getBz4() {
		return bz4;
	}
    /**
     * 设置备注
     *@param 备注
     */
	public void setBz4(String bz4) {
		this.bz4 = bz4;
	}

	/**
     * 获得数量
     *@return 数量
     */
	public String getSl() {
		return sl;
	}
    /**
     * 设置数量
     *@param 数量
     */
	public void setSl(String sl) {
		this.sl = sl;
	}
	
	/**
	 * 获得业务对象名
	 */
	public String getBsoName() {
		return "GYInvoice";
	}

}
