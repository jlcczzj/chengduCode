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
     * ���BsoName
     * @return BsoName "GYInvoice"
     */
    public String getBsoName()
    {
        return "GYInvoice";
    } 
   
    /**
     * ���������
     *@return ������
     */
	public abstract String getPartNumber();
    /**
     * ����������
     *@param ������
     */
	public abstract void setPartNumber(String partNumber) ;
    /**
     * ����������
     *@return �������
     */
	public abstract String getPartName() ;
    /**
     * �����������
     *@param �������
     */
	public abstract void setPartName(String partName) ;
    /**
     * ��ò㼶
     *@return ����㼶
     */
	public abstract String getLevel1() ;
    /**
     * ��������㼶
     *@param ����㼶
     */
	public abstract void setLevel1(String level1);
    /**
     * ������bsoID
     *@return ������bsoID
     */
	public abstract String getPartID() ;
    /**
     * �������bsoID
     *@param ���bsoID
     */
	public abstract void setPartID(String partID);
    /**
     * ���֪ͨ��ID
     *@return ֪ͨ��ID
     */
	public abstract String getNoticeID() ;
    /**
     * ����֪ͨ��ID
     *@param ֪ͨ��ID
     */
	public abstract void setNoticeID(String noticeID) ;
    /**
     * ��ð汾
     *@return �汾
     */
	public abstract String getVersionValue() ;
    /**
     * ���ð汾
     *@param �汾
     */
	public abstract void setVersionValue(String versionValue) ;
    /**
     * �����������״̬
     *@return ��������״̬
     */
	public abstract String getLifecyclestate() ;
    /**
     * ������������״̬
     *@param ��������״̬
     */
	public abstract void setLifecyclestate(String lifecyclestate) ;
    /**
     * �����ͼ
     *@return ��ͼ
     */
	public abstract String getPartView() ;
    /**
     * ������ͼ
     *@param ��ͼ
     */
	public abstract void setPartView(String partView) ;
    /**
     * ��������
     *@return �����
     */
	public abstract String getVirtualPart();
    /**
     * ���������
     *@param �����
     */
	public abstract void setVirtualPart(String virtualPart) ;
    /**
     * �������·��
     *@return ����·��
     */
	public abstract String getZzlx() ;
    /**
     * ��������·��
     *@param ����·��
     */
	public abstract void setZzlx(String zzlx) ;
    /**
     * ���װ��·��
     *@return װ��·��
     */
	public abstract String getZplx() ;
    /**
     * ����װ��·��
     *@param װ��·��
     */
	public abstract void setZplx(String zplx) ;
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public abstract String getBz1() ;
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public abstract void setBz1(String bz1) ;
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public abstract String getBz2() ;
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public abstract void setBz2(String bz2) ;
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public abstract String getBz3() ;
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public abstract void setBz3(String bz3) ;
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public abstract String getBz4() ;
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public abstract void setBz4(String bz4) ;
    /**
     * ��������
     * @param Source
     */
    public abstract void setSl(String sl);
    
    /**
     * �������
     * @return
     */
    public abstract String getSl();



    /**
	   * ���ֵ����
	   */
	  public BaseValueIfc getValueInfo() throws QMException {
		  GYInvoiceInfo info = new GYInvoiceInfo();
	      setValueInfo(info);
	      return info;
	  }
	  
    /**
     * ����ֵ����
     * @param info ֵ����
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
     * ����ֵ����
     * @param info ֵ����
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
     * ����ֵ����
     * @param info ֵ����
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
