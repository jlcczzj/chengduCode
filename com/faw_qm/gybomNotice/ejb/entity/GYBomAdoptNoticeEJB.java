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
     * 获得BsoName
     * @return BsoName "GYBomAdoptNotice"
     */
    public String getBsoName()
    {
        return "GYBomAdoptNotice";
    } 
   
    /**
     * 设置通知单编号
     *@param topic编号
     */
    public abstract void setAdoptnoticenumber(String num);

    /**
     * 获得通知单编号
     * @return 编号
     */
    public abstract String getAdoptnoticenumber();
    /**
     * 设置通知单名称
     *@param topic名称
     */
    public abstract void setAdoptnoticename(String num);

    /**
     * 获得通知单名称
     * @return 名称
     */
    public abstract String getAdoptnoticename();
    /**
     * 设置发布类型
     */
    public abstract void setPublishType(String topic);
    
    
    /**
     * 获得发布类型
     * @return  
     */
    public abstract String getPublishType();
    
    /**
     * 设置分类
     * @param sort 类别
     */
    public abstract void setClassification(String sort);
    
    /**
     * 获得分类
     * @return
     */
    public abstract String getClassification();
    
    /**
     * 设置技术设计任务单
     */
    public abstract void setDesignDoc(String  description);
    
    /**
     * 获得技术设计任务单
     * @return
     */
    public abstract String getDesignDoc();
    
   
	/**
     * 设置解放设计变更单
     * @param jfbomnotice
     */
    public abstract void setJfBomnotice(String jfbomnotice);
    
    /**
     * 获得解放设计变更单
     * @return String
     */
    public abstract String getJfBomnotice();
    
    /**
     * 设置顶级零部件
     *@param String num 编号
     */
    public abstract void setTopPart(String num);

    /**
     * 获得顶级零部件
     * @return 编号
     */
    public abstract String getTopPart();
    /**
     * 设置说明
     * @param Source
     */
    public abstract void setConsdesc(String desc);
    
    /**
     * 获得说明
     * @return
     */
    public abstract String getConsdesc();
    /**
     * 设置中心采用变更单
     * @param Source
     */
    public abstract void setZxAdoptNotice(String notice);
    
    /**
     * 获得中心采用变更单
     * @return
     */
    public abstract String getZxAdoptNotice();
    /**
     * 设置父通知单（如果是子采用单则此属性有值，如果是父采用单则此属性为空）
     * @param Source
     */
    public abstract void setParentNotice(String pnotice);
    
    /**
     * 获得父通知单（如果是子采用单则此属性有值，如果是父采用单则此属性为空）
     * @return
     */
    public abstract String getParentNotice();
    
    /**
     * 设置备注1
     * @param Source
     */
    public abstract void setBz1(String bz1);
    
    /**
     * 获得备注1
     * @return
     */
    public abstract String getBz1();
    /**
     * 设置备注2
     * @param Source
     */
    public abstract void setBz2(String bz2);
    
    /**
     * 获得备注2
     * @return
     */
    public abstract String getBz2();
    /**
     * 设置备注3
     * @param Source
     */
    public abstract void setBz3(String bz3);
    
    /**
     * 获得备注3
     * @return
     */
    public abstract String getBz3();
    /**
     * 设置备注4
     * @param Source
     */
    public abstract void setBz4(String bz4);
    
    /**
     * 获得备注1
     * @return
     */
    public abstract String getBz4();
    
    
    /**
     * 获得加锁日期。
     * @return Timestamp
     */
    public abstract Timestamp getDate();


    /**
     * 设置加锁日期。
     * @param time Timestamp
     */
    public abstract void setDate(Timestamp time);


    /**
     * 获得加锁注释。
     * @return String
     */
    public abstract String getNote();


    /**
     * 设置加锁注释。
     * @param note String
     */
    public abstract void setNote(String note);


    /**
     * 获得加锁者。
     * @return String
     */
    public abstract String getLocker();


    
    
    
    /**
     * 设置加锁者。
     * @param locker String
     */
    public abstract void setLocker(String locker);



    /**
	   * 获得值对象。
	   */
	  public BaseValueIfc getValueInfo() throws QMException {
		  GYBomAdoptNoticeInfo info = new GYBomAdoptNoticeInfo();
	      setValueInfo(info);
	      return info;
	  }
	  
    /**
     * 设置值对象
     * @param info 值对象
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
     * 创建值对象
     * @param info 值对象
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
     * 更新值对象
     * @param info 值对象
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
