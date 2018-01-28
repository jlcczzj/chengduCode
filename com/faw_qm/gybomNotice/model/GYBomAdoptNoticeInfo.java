package com.faw_qm.gybomNotice.model;

import java.sql.Timestamp;

import com.faw_qm.enterprise.model.ManagedInfo;
import com.faw_qm.lock.exception.LockException;
import com.faw_qm.lock.model.LockMap;

public class GYBomAdoptNoticeInfo extends ManagedInfo implements GYBomAdoptNoticeIfc {
	

	private static final long serialVersionUID = 1L;
    //通知单编号
	private String adoptnoticenumber;
    //名称
	private String adoptnoticename;
    //发布类型
	private String publishType;
    //分类
	private String classification;
    //技术设计任务单
	private String designDoc;
   
    //说明
    private String consdesc;
    //中心技术任务单
    private String zxAdoptNotice;
    //父通知单
    private String parentNotice;
    //备注1
    private String bz1;
    //备注2
    private String bz2;
    //备注3
    private String bz3;
    //备注4
    private String bz4;
   
    //顶级零件
    private String topPart;
    
    //解放通知单
    private String jfBomnotice;
        /**
     * 锁辅助类对象。
     */
    private LockMap lockMap = null;
        /**
     * 构造值对象
     */
    public GYBomAdoptNoticeInfo()
    {
        super();
        lockMap = new LockMap();

    }

    /**
     * 设置通知单编号
     *@param topic编号
     */
    public void setAdoptnoticenumber(String num)
    {
        this.adoptnoticenumber = num;
    }

    /**
     * 获得通知单编号
     * @return 编号
     */
    public String getAdoptnoticenumber()
    {
        return this.adoptnoticenumber;
    }
    /**
     * 设置通知单名称
     *@param topic名称
     */
    public void setAdoptnoticename(String num)
    {
        this.adoptnoticename = num;
    }

    /**
     * 获得通知单名称
     * @return 名称
     */
    public String getAdoptnoticename()
    {
        return  this.adoptnoticename;
    }
    /**
     * 设置发布类型（采用单或者是子采用单）
     */
    public void setPublishType(String topic)
    {
        this.publishType = topic;
    }
    
    
    /**
     * 获得发布类型（采用单或者是子采用单）
     * @return  
     */
    public String getPublishType()
    {
        return this.publishType;
    }
    
    /**
     * 设置分类
     * @param sort 类别
     */
    public void setClassification(String sort)
    {
        this.classification = sort;
    }
    
    /**
     * 获得分类
     * @return
     */
    public String getClassification()
    {
        return this.classification;
    }
    
    /**
     * 设置技术设计任务单
     */
    public void setDesignDoc(String  description)
    {
        this.designDoc = description;
    }
    
    /**
     * 获得技术设计任务单
     * @return
     */
    public String getDesignDoc()
    {
        return this.designDoc;
    }

	/**
     * 设置解放设计变更单
     * @param jfbomnotice
     */
    public void setJfBomnotice(String jfbomnotice){
    	this.jfBomnotice = jfbomnotice;
    }
    
    /**
     * 获得解放设计变更单
     * @return String
     */
    public String getJfBomnotice(){
    	return this.jfBomnotice;
    }
    
    /**
     * 设置顶级零部件
     *@param String num 编号
     */
    public void setTopPart(String num){
    	this.topPart = num;
    }

    /**
     * 获得顶级零部件
     * @return 编号
     */
    public String getTopPart(){
    	return this.topPart;
    }
    /**
     * 设置说明
     * @param Source
     */
    public void setConsdesc(String desc)
    {
        this.consdesc = desc;
    }
    
    /**
     * 获得说明
     * @return
     */
    public String getConsdesc()
    {
        return this.consdesc;
    }
    /**
     * 设置中心采用变更单
     * @param Source
     */
    public void setZxAdoptNotice(String notice)
    {
        this.zxAdoptNotice = notice;
    }
    
    /**
     * 获得中心采用变更单
     * @return
     */
    public String getZxAdoptNotice()
    {
        return this.zxAdoptNotice;
    }
    /**
     * 设置父通知单
     * @param pnotice
     */
    public void setParentNotice(String pnotice)
    {
        parentNotice = pnotice;
    }
    
    /**
     * 获得父通知单
     * @return
     */
    public String getParentNotice()
    {
        return parentNotice;
    }
    
    /**
     * 设置备注1
     * @param Source
     */
    public void setBz1(String bz1)
    {
        this.bz1 = bz1;
    }
    
    /**
     * 获得备注1
     * @return
     */
    public String getBz1()
    {
        return this.bz1;
    }
    /**
     * 设置备注2
     * @param Source
     */
    public void setBz2(String bz2)
    {
        this.bz2 = bz2;
    }
    /**
     * 获得备注2
     * @return
     */
    public String getBz2()
    {
        return this.bz2;
    }
    /**
     * 设置备注3
     * @param Source
     */
    public void setBz3(String bz3)
    {
        this.bz3 = bz3;
    }
    
    /**
     * 获得备注3
     * @return
     */
    public String getBz3()
    {
        return this.bz3;
    }
    /**
     * 设置备注4
     * @param Source
     */
    public void setBz4(String bz4)
    {
        this.bz4 = bz4;
    }
    
    /**
     * 获得备注1
     * @return
     */
    public String getBz4()
    {
        return this.bz4;
    }
    
	 /*
     * 加入对锁服务的操作。
     * 获取lockMap对象属性。
     * @return LockMap
     */
    public LockMap getLockMap()
    {
        if (lockMap == null)
        {
            setLockMap(new LockMap());
        }
        return lockMap;
    }


    /**
     * 设置lockMap对象属性。
     * @param map LockMap
     */
    public void setLockMap(LockMap map)
    {
        lockMap = map;
    }


    /**
     * 获得加锁时间。
     * @return Timestamp
     */
    public Timestamp getDate()
    {
        return getLockMap().getDate();
    }


    /**
     * 设置加锁时间。
     * @param date Timestamp
     */
    public void setDate(Timestamp date)
    {
        getLockMap().setDate(date);
    }


    /**
     * 获得加锁注释。
     * @return String
     */
    public String getNote()
    {
        return getLockMap().getNote();
    }


    /**
     * 设置加锁注释。
     * @param note String
     */
    public void setNote(String note)
    {
        getLockMap().setNote(note);
    }


    /**
     * 获得加锁者。
     * @return String
     */
    public String getLocker()
    {
        return getLockMap().getLocker();
    }


    /**
     * 设置加锁者。
     * @param locker String
     * @throws LockException
     */
    public void setLocker(String locker)
            throws LockException
    {
        getLockMap().setLocker(locker);
    }


	/**
	 * 获得业务对象名
	 */
	public String getBsoName() {
		return "GYBomAdoptNotice";
	}
   
    /**
     * 获取唯一标识。
     * @return String 唯一标识。
     */
    public String getIdentity()
    {
      return this.adoptnoticenumber;
    }
    

}
