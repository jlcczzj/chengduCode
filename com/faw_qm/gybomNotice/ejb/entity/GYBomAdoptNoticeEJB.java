package com.faw_qm.gybomNotice.ejb.entity;


import java.sql.Timestamp;

import javax.ejb.CreateException;

import com.faw_qm.enterprise.ejb.entity.ManagedEJB;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;


public abstract class GYBomAdoptNoticeEJB extends ManagedEJB
{


    /**
     * ���BsoName
     * @return BsoName "GYBomAdoptNotice"
     */
    public String getBsoName()
    {
        return "GYBomAdoptNotice";
    } 
   
    /**
     * ����֪ͨ�����
     *@param topic���
     */
    public abstract void setAdoptnoticenumber(String num);

    /**
     * ���֪ͨ�����
     * @return ���
     */
    public abstract String getAdoptnoticenumber();
    /**
     * ����֪ͨ������
     *@param topic����
     */
    public abstract void setAdoptnoticename(String num);

    /**
     * ���֪ͨ������
     * @return ����
     */
    public abstract String getAdoptnoticename();
    /**
     * ���÷�������
     */
    public abstract void setPublishType(String topic);
    
    
    /**
     * ��÷�������
     * @return  
     */
    public abstract String getPublishType();
    
    /**
     * ���÷���
     * @param sort ���
     */
    public abstract void setClassification(String sort);
    
    /**
     * ��÷���
     * @return
     */
    public abstract String getClassification();
    
    /**
     * ���ü����������
     */
    public abstract void setDesignDoc(String  description);
    
    /**
     * ��ü����������
     * @return
     */
    public abstract String getDesignDoc();
    
   
	/**
     * ���ý����Ʊ����
     * @param jfbomnotice
     */
    public abstract void setJfBomnotice(String jfbomnotice);
    
    /**
     * ��ý����Ʊ����
     * @return String
     */
    public abstract String getJfBomnotice();
    
    /**
     * ���ö����㲿��
     *@param String num ���
     */
    public abstract void setTopPart(String num);

    /**
     * ��ö����㲿��
     * @return ���
     */
    public abstract String getTopPart();
    /**
     * ����˵��
     * @param Source
     */
    public abstract void setConsdesc(String desc);
    
    /**
     * ���˵��
     * @return
     */
    public abstract String getConsdesc();
    /**
     * �������Ĳ��ñ����
     * @param Source
     */
    public abstract void setZxAdoptNotice(String notice);
    
    /**
     * ������Ĳ��ñ����
     * @return
     */
    public abstract String getZxAdoptNotice();
    /**
     * ���ø�֪ͨ����������Ӳ��õ����������ֵ������Ǹ����õ��������Ϊ�գ�
     * @param Source
     */
    public abstract void setParentNotice(String pnotice);
    
    /**
     * ��ø�֪ͨ����������Ӳ��õ����������ֵ������Ǹ����õ��������Ϊ�գ�
     * @return
     */
    public abstract String getParentNotice();
    
    /**
     * ���ñ�ע1
     * @param Source
     */
    public abstract void setBz1(String bz1);
    
    /**
     * ��ñ�ע1
     * @return
     */
    public abstract String getBz1();
    /**
     * ���ñ�ע2
     * @param Source
     */
    public abstract void setBz2(String bz2);
    
    /**
     * ��ñ�ע2
     * @return
     */
    public abstract String getBz2();
    /**
     * ���ñ�ע3
     * @param Source
     */
    public abstract void setBz3(String bz3);
    
    /**
     * ��ñ�ע3
     * @return
     */
    public abstract String getBz3();
    /**
     * ���ñ�ע4
     * @param Source
     */
    public abstract void setBz4(String bz4);
    
    /**
     * ��ñ�ע1
     * @return
     */
    public abstract String getBz4();
    
    
    /**
     * ��ü������ڡ�
     * @return Timestamp
     */
    public abstract Timestamp getDate();


    /**
     * ���ü������ڡ�
     * @param time Timestamp
     */
    public abstract void setDate(Timestamp time);


    /**
     * ��ü���ע�͡�
     * @return String
     */
    public abstract String getNote();


    /**
     * ���ü���ע�͡�
     * @param note String
     */
    public abstract void setNote(String note);


    /**
     * ��ü����ߡ�
     * @return String
     */
    public abstract String getLocker();


    
    
    
    /**
     * ���ü����ߡ�
     * @param locker String
     */
    public abstract void setLocker(String locker);



    /**
	   * ���ֵ����
	   */
	  public BaseValueIfc getValueInfo() throws QMException {
		  GYBomAdoptNoticeInfo info = new GYBomAdoptNoticeInfo();
	      setValueInfo(info);
	      return info;
	  }
	  
    /**
     * ����ֵ����
     * @param info ֵ����
     * @exception QMException;
     */
    public void setValueInfo(BaseValueIfc info)
            throws QMException
    {
    	 super.setValueInfo(info);
    	GYBomAdoptNoticeInfo cqInfo=(GYBomAdoptNoticeInfo)info;
    	cqInfo.setAdoptnoticename(this.getAdoptnoticename());
    	cqInfo.setAdoptnoticenumber(this.getAdoptnoticenumber());
    	cqInfo.setPublishType(this.getPublishType());
    	cqInfo.setClassification(this.getClassification());
    	cqInfo.setDesignDoc(this.getDesignDoc());
    	cqInfo.setConsdesc(this.getConsdesc());
    	cqInfo.setZxAdoptNotice(this.getZxAdoptNotice());
    	cqInfo.setParentNotice(this.getParentNotice());
    	cqInfo.setTopPart(this.getTopPart());
    	cqInfo.setJfBomnotice(this.getJfBomnotice());
    	cqInfo.setBz1(this.getBz1());
    	cqInfo.setBz2(this.getBz2());
    	cqInfo.setBz3(this.getBz3());
    	cqInfo.setBz4(this.getBz4());
    	cqInfo.setLocker(getLocker());
      cqInfo.setDate(this.getDate());
        cqInfo.setNote(getNote());
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
    	GYBomAdoptNoticeInfo cqInfo=(GYBomAdoptNoticeInfo)info;
    	
    	this.setAdoptnoticename(cqInfo.getAdoptnoticename());
    	this.setAdoptnoticenumber(cqInfo.getAdoptnoticenumber());
    	this.setPublishType(cqInfo.getPublishType());
    	this.setClassification(cqInfo.getClassification());
    	this.setDesignDoc(cqInfo.getDesignDoc());

    	this.setConsdesc(cqInfo.getConsdesc());
    	this.setZxAdoptNotice(cqInfo.getZxAdoptNotice());
    	this.setParentNotice(cqInfo.getParentNotice());
    	this.setTopPart(cqInfo.getTopPart());
    	this.setJfBomnotice(cqInfo.getJfBomnotice());
    	this.setBz1(cqInfo.getBz1());
    	this.setBz2(cqInfo.getBz2());
    	this.setBz3(cqInfo.getBz3());
    	this.setBz4(cqInfo.getBz4());
    	 setDate(cqInfo.getDate());
       setLocker(cqInfo.getLocker());
       setNote(cqInfo.getNote());

    }
    
    /**
     * ����ֵ����
     * @param info ֵ����
     * @exception QMException
     */
    public void updateByValueInfo(BaseValueIfc info)
            throws QMException
    {
    	 super.updateByValueInfo(info);
    	 GYBomAdoptNoticeInfo cqInfo=(GYBomAdoptNoticeInfo)info;
    	this.setAdoptnoticename(cqInfo.getAdoptnoticename());
        this.setAdoptnoticenumber(cqInfo.getAdoptnoticenumber());
        this.setPublishType(cqInfo.getPublishType());
        this.setClassification(cqInfo.getClassification());
        this.setDesignDoc(cqInfo.getDesignDoc());

        this.setConsdesc(cqInfo.getConsdesc());
        this.setZxAdoptNotice(cqInfo.getZxAdoptNotice());
        this.setParentNotice(cqInfo.getParentNotice());
        this.setTopPart(cqInfo.getTopPart());
        this.setJfBomnotice(cqInfo.getJfBomnotice());
        this.setBz1(cqInfo.getBz1());
        this.setBz2(cqInfo.getBz2());
        this.setBz3(cqInfo.getBz3());
        this.setBz4(cqInfo.getBz4());
          setDate(cqInfo.getDate());
        setLocker(cqInfo.getLocker());
        setNote(cqInfo.getNote());
    }
	
}
