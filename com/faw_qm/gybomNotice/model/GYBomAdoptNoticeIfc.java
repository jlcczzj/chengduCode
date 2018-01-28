package com.faw_qm.gybomNotice.model;

import com.faw_qm.affixattr.model.AffixIfc;
import com.faw_qm.content.ejb.service.FormatContentHolder;
import com.faw_qm.content.model.FormatContentHolderIfc;
import com.faw_qm.enterprise.model.ManagedIfc;
import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.framework.service.BsoReference;
import com.faw_qm.lock.model.LockIfc;

/**
 * �������ֵ����ӿ�
 * @author ������
 *
 */
public interface GYBomAdoptNoticeIfc extends  ManagedIfc, LockIfc {

    /**
     * ����֪ͨ�����
     *@param topic���
     */
    public void setAdoptnoticenumber(String num);

    /**
     * ���֪ͨ�����
     * @return ���
     */
    public String getAdoptnoticenumber();
   
    /**
     * ����֪ͨ������
     *@param topic����
     */
    public void setAdoptnoticename(String num);

    /**
     * ���֪ͨ������
     * @return ����
     */
    public String getAdoptnoticename();
    /**
     * ���÷������ͣ����õ��������Ӳ��õ���
     */
    public void setPublishType(String topic);
    
    
    /**
     * ��÷������ͣ����õ��������Ӳ��õ���
     * @return  
     */
    public String getPublishType();
    
    /**
     * ���÷���
     * @param sort ���
     */
    public void setClassification(String sort);
    
    /**
     * ��÷���
     * @return
     */
    public String getClassification();
    
    /**
     * ���ü����������
     */
    public void setDesignDoc(String  description);
    
    /**
     * ��ü����������
     * @return
     */
    public String getDesignDoc();
    
  
	/**
     * ���ý����Ʊ����
     * @param jfbomnotice
     */
    public void setJfBomnotice(String jfbomnotice);
    
    /**
     * ��ý����Ʊ����
     * @return String
     */
    public String getJfBomnotice();
    
    /**
     * ���ö����㲿��
     *@param String num ���
     */
    public void setTopPart(String num);

    /**
     * ��ö����㲿��
     * @return ���
     */
    public String getTopPart();
    /**
     * ����˵��
     * @param Source
     */
    public void setConsdesc(String desc);
    
    /**
     * ���˵��
     * @return
     */
    public String getConsdesc();
    /**
     * �������Ĳ��ñ����
     * @param Source
     */
    public void setZxAdoptNotice(String notice);
    
    /**
     * ������Ĳ��ñ����
     * @return
     */
    public String getZxAdoptNotice();
    /**
     * ���ø�֪ͨ����������Ӳ��õ����������ֵ������Ǹ����õ��������Ϊ�գ�
     * @param Source
     */
    public void setParentNotice(String pnotice);
    
    /**
     * ��ø�֪ͨ����������Ӳ��õ����������ֵ������Ǹ����õ��������Ϊ�գ�
     * @return
     */
    public String getParentNotice();
    
    /**
     * ���ñ�ע1
     * @param Source
     */
    public void setBz1(String bz1);
    
    /**
     * ��ñ�ע1
     * @return
     */
    public String getBz1();
    /**
     * ���ñ�ע2
     * @param Source
     */
    public void setBz2(String bz2);
    
    /**
     * ��ñ�ע2
     * @return
     */
    public String getBz2();
    /**
     * ���ñ�ע3
     * @param Source
     */
    public void setBz3(String bz3);
    
    /**
     * ��ñ�ע3
     * @return
     */
    public String getBz3();
    /**
     * ���ñ�ע4
     * @param Source
     */
    public void setBz4(String bz4);
    
    /**
     * ��ñ�ע1
     * @return
     */
    public String getBz4();
	
}
