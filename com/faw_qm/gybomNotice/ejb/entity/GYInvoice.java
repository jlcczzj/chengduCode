package com.faw_qm.gybomNotice.ejb.entity;


import com.faw_qm.enterprise.ejb.entity.Managed;
import com.faw_qm.framework.service.BsoReference;
import com.faw_qm.lock.ejb.entity.Lock;


public interface GYInvoice  extends BsoReference
{
	 /**
     * 获得零件编号
     *@return 零件编号
     */
	public String getPartNumber();
    /**
     * 设置零件编号
     *@param 零件编号
     */
	public void setPartNumber(String partNumber) ;
    /**
     * 获得零件名称
     *@return 零件名称
     */
	public String getPartName() ;
    /**
     * 设置零件名称
     *@param 零件名称
     */
	public void setPartName(String partName) ;
    /**
     * 获得层级
     *@return 零件层级
     */
	public String getLevel1() ;
    /**
     * 设置零件层级
     *@param 零件层级
     */
	public void setLevel1(String level1);
    /**
     * 获得零件bsoID
     *@return 零件零件bsoID
     */
	public String getPartID() ;
    /**
     * 设置零件bsoID
     *@param 零件bsoID
     */
	public void setPartID(String partID);
    /**
     * 获得通知单ID
     *@return 通知单ID
     */
	public String getNoticeID() ;
    /**
     * 设置通知单ID
     *@param 通知单ID
     */
	public void setNoticeID(String noticeID) ;
   
    /**
     * 获得版本
     *@return 版本
     */
	public String getVersionValue() ;
    /**
     * 设置版本
     *@param 版本
     */
	public void setVersionValue(String versionValue) ;
    /**
     * 获得生命周期状态
     *@return 生命周期状态
     */
	public String getLifecyclestate() ;
    /**
     * 设置生命周期状态
     *@param 生命周期状态
     */
	public void setLifecyclestate(String lifecyclestate) ;
    /**
     * 获得视图
     *@return 视图
     */
	public String getPartView() ;
    /**
     * 设置视图
     *@param 视图
     */
	public void setPartView(String partView) ;
    /**
     * 获得虚拟件
     *@return 虚拟件
     */
	public String getVirtualPart();
    /**
     * 设置虚拟件
     *@param 虚拟件
     */
	public void setVirtualPart(String virtualPart) ;
    /**
     * 获得制造路线
     *@return 制造路线
     */
	public String getZzlx() ;
    /**
     * 设置制造路线
     *@param 制造路线
     */
	public void setZzlx(String zzlx) ;
    /**
     * 获得装配路线
     *@return 装配路线
     */
	public String getZplx() ;
    /**
     * 设置装配路线
     *@param 装配路线
     */
	public void setZplx(String zplx) ;
    /**
     * 获得备注
     *@return 备注
     */
	public String getBz1() ;
    /**
     * 设置备注
     *@param 备注
     */
	public void setBz1(String bz1) ;
    /**
     * 获得备注
     *@return 备注
     */
	public String getBz2() ;
    /**
     * 设置备注
     *@param 备注
     */
	public void setBz2(String bz2) ;
    /**
     * 获得备注
     *@return 备注
     */
	public String getBz3() ;
    /**
     * 设置备注
     *@param 备注
     */
	public void setBz3(String bz3) ;
    /**
     * 获得备注
     *@return 备注
     */
	public String getBz4() ;
    /**
     * 设置备注
     *@param 备注
     */
	public void setBz4(String bz4) ;
	
    /**
     * 设置数量
     * @param Source
     */
    public  void setSl(String sl);
    
    /**
     * 获得数量
     * @return
     */
    public  String getSl();
}
