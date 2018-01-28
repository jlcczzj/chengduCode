package com.faw_qm.gybomNotice.model;

import java.sql.Timestamp;

import com.faw_qm.enterprise.model.ManagedInfo;
import com.faw_qm.lock.exception.LockException;
import com.faw_qm.lock.model.LockMap;

public class GYBomAdoptNoticeInfo extends ManagedInfo implements GYBomAdoptNoticeIfc {
	

	private static final long serialVersionUID = 1L;
    //֪ͨ�����
	private String adoptnoticenumber;
    //����
	private String adoptnoticename;
    //��������
	private String publishType;
    //����
	private String classification;
    //�����������
	private String designDoc;
   
    //˵��
    private String consdesc;
    //���ļ�������
    private String zxAdoptNotice;
    //��֪ͨ��
    private String parentNotice;
    //��ע1
    private String bz1;
    //��ע2
    private String bz2;
    //��ע3
    private String bz3;
    //��ע4
    private String bz4;
   
    //�������
    private String topPart;
    
    //���֪ͨ��
    private String jfBomnotice;
        /**
     * �����������
     */
    private LockMap lockMap = null;
        /**
     * ����ֵ����
     */
    public GYBomAdoptNoticeInfo()
    {
        super();
        lockMap = new LockMap();

    }

    /**
     * ����֪ͨ�����
     *@param topic���
     */
    public void setAdoptnoticenumber(String num)
    {
        this.adoptnoticenumber = num;
    }

    /**
     * ���֪ͨ�����
     * @return ���
     */
    public String getAdoptnoticenumber()
    {
        return this.adoptnoticenumber;
    }
    /**
     * ����֪ͨ������
     *@param topic����
     */
    public void setAdoptnoticename(String num)
    {
        this.adoptnoticename = num;
    }

    /**
     * ���֪ͨ������
     * @return ����
     */
    public String getAdoptnoticename()
    {
        return  this.adoptnoticename;
    }
    /**
     * ���÷������ͣ����õ��������Ӳ��õ���
     */
    public void setPublishType(String topic)
    {
        this.publishType = topic;
    }
    
    
    /**
     * ��÷������ͣ����õ��������Ӳ��õ���
     * @return  
     */
    public String getPublishType()
    {
        return this.publishType;
    }
    
    /**
     * ���÷���
     * @param sort ���
     */
    public void setClassification(String sort)
    {
        this.classification = sort;
    }
    
    /**
     * ��÷���
     * @return
     */
    public String getClassification()
    {
        return this.classification;
    }
    
    /**
     * ���ü����������
     */
    public void setDesignDoc(String  description)
    {
        this.designDoc = description;
    }
    
    /**
     * ��ü����������
     * @return
     */
    public String getDesignDoc()
    {
        return this.designDoc;
    }

	/**
     * ���ý����Ʊ����
     * @param jfbomnotice
     */
    public void setJfBomnotice(String jfbomnotice){
    	this.jfBomnotice = jfbomnotice;
    }
    
    /**
     * ��ý����Ʊ����
     * @return String
     */
    public String getJfBomnotice(){
    	return this.jfBomnotice;
    }
    
    /**
     * ���ö����㲿��
     *@param String num ���
     */
    public void setTopPart(String num){
    	this.topPart = num;
    }

    /**
     * ��ö����㲿��
     * @return ���
     */
    public String getTopPart(){
    	return this.topPart;
    }
    /**
     * ����˵��
     * @param Source
     */
    public void setConsdesc(String desc)
    {
        this.consdesc = desc;
    }
    
    /**
     * ���˵��
     * @return
     */
    public String getConsdesc()
    {
        return this.consdesc;
    }
    /**
     * �������Ĳ��ñ����
     * @param Source
     */
    public void setZxAdoptNotice(String notice)
    {
        this.zxAdoptNotice = notice;
    }
    
    /**
     * ������Ĳ��ñ����
     * @return
     */
    public String getZxAdoptNotice()
    {
        return this.zxAdoptNotice;
    }
    /**
     * ���ø�֪ͨ��
     * @param pnotice
     */
    public void setParentNotice(String pnotice)
    {
        parentNotice = pnotice;
    }
    
    /**
     * ��ø�֪ͨ��
     * @return
     */
    public String getParentNotice()
    {
        return parentNotice;
    }
    
    /**
     * ���ñ�ע1
     * @param Source
     */
    public void setBz1(String bz1)
    {
        this.bz1 = bz1;
    }
    
    /**
     * ��ñ�ע1
     * @return
     */
    public String getBz1()
    {
        return this.bz1;
    }
    /**
     * ���ñ�ע2
     * @param Source
     */
    public void setBz2(String bz2)
    {
        this.bz2 = bz2;
    }
    /**
     * ��ñ�ע2
     * @return
     */
    public String getBz2()
    {
        return this.bz2;
    }
    /**
     * ���ñ�ע3
     * @param Source
     */
    public void setBz3(String bz3)
    {
        this.bz3 = bz3;
    }
    
    /**
     * ��ñ�ע3
     * @return
     */
    public String getBz3()
    {
        return this.bz3;
    }
    /**
     * ���ñ�ע4
     * @param Source
     */
    public void setBz4(String bz4)
    {
        this.bz4 = bz4;
    }
    
    /**
     * ��ñ�ע1
     * @return
     */
    public String getBz4()
    {
        return this.bz4;
    }
    
	 /*
     * �����������Ĳ�����
     * ��ȡlockMap�������ԡ�
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
     * ����lockMap�������ԡ�
     * @param map LockMap
     */
    public void setLockMap(LockMap map)
    {
        lockMap = map;
    }


    /**
     * ��ü���ʱ�䡣
     * @return Timestamp
     */
    public Timestamp getDate()
    {
        return getLockMap().getDate();
    }


    /**
     * ���ü���ʱ�䡣
     * @param date Timestamp
     */
    public void setDate(Timestamp date)
    {
        getLockMap().setDate(date);
    }


    /**
     * ��ü���ע�͡�
     * @return String
     */
    public String getNote()
    {
        return getLockMap().getNote();
    }


    /**
     * ���ü���ע�͡�
     * @param note String
     */
    public void setNote(String note)
    {
        getLockMap().setNote(note);
    }


    /**
     * ��ü����ߡ�
     * @return String
     */
    public String getLocker()
    {
        return getLockMap().getLocker();
    }


    /**
     * ���ü����ߡ�
     * @param locker String
     * @throws LockException
     */
    public void setLocker(String locker)
            throws LockException
    {
        getLockMap().setLocker(locker);
    }


	/**
	 * ���ҵ�������
	 */
	public String getBsoName() {
		return "GYBomAdoptNotice";
	}
   
    /**
     * ��ȡΨһ��ʶ��
     * @return String Ψһ��ʶ��
     */
    public String getIdentity()
    {
      return this.adoptnoticenumber;
    }
    

}
