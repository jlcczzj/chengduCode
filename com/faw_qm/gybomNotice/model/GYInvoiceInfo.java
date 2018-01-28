package com.faw_qm.gybomNotice.model;

import java.sql.Timestamp;

import com.faw_qm.enterprise.model.ManagedInfo;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.lock.exception.LockException;
import com.faw_qm.lock.model.LockMap;

public class GYInvoiceInfo extends BaseValueInfo implements GYInvoiceIfc {
	

	private static final long serialVersionUID = 1L;
    //������
	private String partNumber;
	//�������
	private String partName;
	//�㼶 
	private String level1;
	//�������bsoID
	private String partID;
	//֪ͨ����� 
	private String noticeID;
	//�汾
	private String versionValue;
	//��������״̬
	private String lifecyclestate;
	//�����ͼ
	private String partView;
	//�����
	private String virtualPart;
	//����·��
	private String zzlx;
	//װ��·��
	private String zplx;
	//��ע1
	private String bz1;
	//��ע2
	private String bz2;
	//��ע3
	private String bz3;
	//��ע4
	private String bz4;
	private String sl;
    /**
     * ����ֵ����
     */
    public GYInvoiceInfo()
    {
        super();

    }
    /**
     * ���������
     *@return ������
     */
	public String getPartNumber() {
		return partNumber;
	}
    /**
     * ����������
     *@param ������
     */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
    /**
     * ����������
     *@return �������
     */
	public String getPartName() {
		return partName;
	}
    /**
     * �����������
     *@param �������
     */
	public void setPartName(String partName) {
		this.partName = partName;
	}
    /**
     * ��ò㼶
     *@return ����㼶
     */
	public String getLevel1() {
		return level1;
	}
    /**
     * ��������㼶
     *@param ����㼶
     */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}
    /**
     * ������bsoID
     *@return ������bsoID
     */
	public String getPartID() {
		return partID;
	}
    /**
     * �������bsoID
     *@param ���bsoID
     */
	public void setPartID(String partID) {
		this.partID = partID;
	}
    /**
     * ���֪ͨ��ID
     *@return ֪ͨ��ID
     */
	public String getNoticeID() {
		return noticeID;
	}
    /**
     * ����֪ͨ��ID
     *@param ֪ͨ��ID
     */
	public void setNoticeID(String noticeID) {
		this.noticeID = noticeID;
	}
  
    /**
     * ��ð汾
     *@return �汾
     */
	public String getVersionValue() {
		return versionValue;
	}
    /**
     * ���ð汾
     *@param �汾
     */
	public void setVersionValue(String versionValue) {
		this.versionValue = versionValue;
	}
    /**
     * �����������״̬
     *@return ��������״̬
     */
	public String getLifecyclestate() {
		return lifecyclestate;
	}
    /**
     * ������������״̬
     *@param ��������״̬
     */
	public void setLifecyclestate(String lifecyclestate) {
		this.lifecyclestate = lifecyclestate;
	}
    /**
     * �����ͼ
     *@return ��ͼ
     */
	public String getPartView() {
		return partView;
	}
    /**
     * ������ͼ
     *@param ��ͼ
     */
	public void setPartView(String partView) {
		this.partView = partView;
	}
    /**
     * ��������
     *@return �����
     */
	public String getVirtualPart() {
		return virtualPart;
	}
    /**
     * ���������
     *@param �����
     */
	public void setVirtualPart(String virtualPart) {
		this.virtualPart = virtualPart;
	}
    /**
     * �������·��
     *@return ����·��
     */
	public String getZzlx() {
		return zzlx;
	}
    /**
     * ��������·��
     *@param ����·��
     */
	public void setZzlx(String zzlx) {
		this.zzlx = zzlx;
	}
    /**
     * ���װ��·��
     *@return װ��·��
     */
	public String getZplx() {
		return zplx;
	}
    /**
     * ����װ��·��
     *@param װ��·��
     */
	public void setZplx(String zplx) {
		this.zplx = zplx;
	}
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public String getBz1() {
		return bz1;
	}
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public void setBz1(String bz1) {
		this.bz1 = bz1;
	}
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public String getBz2() {
		return bz2;
	}
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public void setBz2(String bz2) {
		this.bz2 = bz2;
	}
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public String getBz3() {
		return bz3;
	}
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public void setBz3(String bz3) {
		this.bz3 = bz3;
	}
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public String getBz4() {
		return bz4;
	}
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public void setBz4(String bz4) {
		this.bz4 = bz4;
	}

	/**
     * �������
     *@return ����
     */
	public String getSl() {
		return sl;
	}
    /**
     * ��������
     *@param ����
     */
	public void setSl(String sl) {
		this.sl = sl;
	}
	
	/**
	 * ���ҵ�������
	 */
	public String getBsoName() {
		return "GYInvoice";
	}

}
