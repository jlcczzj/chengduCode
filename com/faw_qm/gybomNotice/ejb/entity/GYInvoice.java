package com.faw_qm.gybomNotice.ejb.entity;


import com.faw_qm.enterprise.ejb.entity.Managed;
import com.faw_qm.framework.service.BsoReference;
import com.faw_qm.lock.ejb.entity.Lock;


public interface GYInvoice  extends BsoReference
{
	 /**
     * ���������
     *@return ������
     */
	public String getPartNumber();
    /**
     * ����������
     *@param ������
     */
	public void setPartNumber(String partNumber) ;
    /**
     * ����������
     *@return �������
     */
	public String getPartName() ;
    /**
     * �����������
     *@param �������
     */
	public void setPartName(String partName) ;
    /**
     * ��ò㼶
     *@return ����㼶
     */
	public String getLevel1() ;
    /**
     * ��������㼶
     *@param ����㼶
     */
	public void setLevel1(String level1);
    /**
     * ������bsoID
     *@return ������bsoID
     */
	public String getPartID() ;
    /**
     * �������bsoID
     *@param ���bsoID
     */
	public void setPartID(String partID);
    /**
     * ���֪ͨ��ID
     *@return ֪ͨ��ID
     */
	public String getNoticeID() ;
    /**
     * ����֪ͨ��ID
     *@param ֪ͨ��ID
     */
	public void setNoticeID(String noticeID) ;
   
    /**
     * ��ð汾
     *@return �汾
     */
	public String getVersionValue() ;
    /**
     * ���ð汾
     *@param �汾
     */
	public void setVersionValue(String versionValue) ;
    /**
     * �����������״̬
     *@return ��������״̬
     */
	public String getLifecyclestate() ;
    /**
     * ������������״̬
     *@param ��������״̬
     */
	public void setLifecyclestate(String lifecyclestate) ;
    /**
     * �����ͼ
     *@return ��ͼ
     */
	public String getPartView() ;
    /**
     * ������ͼ
     *@param ��ͼ
     */
	public void setPartView(String partView) ;
    /**
     * ��������
     *@return �����
     */
	public String getVirtualPart();
    /**
     * ���������
     *@param �����
     */
	public void setVirtualPart(String virtualPart) ;
    /**
     * �������·��
     *@return ����·��
     */
	public String getZzlx() ;
    /**
     * ��������·��
     *@param ����·��
     */
	public void setZzlx(String zzlx) ;
    /**
     * ���װ��·��
     *@return װ��·��
     */
	public String getZplx() ;
    /**
     * ����װ��·��
     *@param װ��·��
     */
	public void setZplx(String zplx) ;
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public String getBz1() ;
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public void setBz1(String bz1) ;
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public String getBz2() ;
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public void setBz2(String bz2) ;
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public String getBz3() ;
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public void setBz3(String bz3) ;
    /**
     * ��ñ�ע
     *@return ��ע
     */
	public String getBz4() ;
    /**
     * ���ñ�ע
     *@param ��ע
     */
	public void setBz4(String bz4) ;
	
    /**
     * ��������
     * @param Source
     */
    public  void setSl(String sl);
    
    /**
     * �������
     * @return
     */
    public  String getSl();
}
