package com.faw_qm.gybomNotice.model;

import com.faw_qm.affixattr.model.AffixIfc;
import com.faw_qm.content.ejb.service.FormatContentHolder;
import com.faw_qm.content.model.FormatContentHolderIfc;
import com.faw_qm.enterprise.model.ManagedIfc;
import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.framework.service.BsoReference;
import com.faw_qm.lock.model.LockIfc;

/**
 * 变更请求值对象接口
 * @author 郭晓亮
 *
 */
public interface GYBomAdoptNoticeIfc extends  ManagedIfc, LockIfc {

    /**
     * 设置通知单编号
     *@param topic编号
     */
    public void setAdoptnoticenumber(String num);

    /**
     * 获得通知单编号
     * @return 编号
     */
    public String getAdoptnoticenumber();
   
    /**
     * 设置通知单名称
     *@param topic名称
     */
    public void setAdoptnoticename(String num);

    /**
     * 获得通知单名称
     * @return 名称
     */
    public String getAdoptnoticename();
    /**
     * 设置发布类型（采用单或者是子采用单）
     */
    public void setPublishType(String topic);
    
    
    /**
     * 获得发布类型（采用单或者是子采用单）
     * @return  
     */
    public String getPublishType();
    
    /**
     * 设置分类
     * @param sort 类别
     */
    public void setClassification(String sort);
    
    /**
     * 获得分类
     * @return
     */
    public String getClassification();
    
    /**
     * 设置技术设计任务单
     */
    public void setDesignDoc(String  description);
    
    /**
     * 获得技术设计任务单
     * @return
     */
    public String getDesignDoc();
    
  
	/**
     * 设置解放设计变更单
     * @param jfbomnotice
     */
    public void setJfBomnotice(String jfbomnotice);
    
    /**
     * 获得解放设计变更单
     * @return String
     */
    public String getJfBomnotice();
    
    /**
     * 设置顶级零部件
     *@param String num 编号
     */
    public void setTopPart(String num);

    /**
     * 获得顶级零部件
     * @return 编号
     */
    public String getTopPart();
    /**
     * 设置说明
     * @param Source
     */
    public void setConsdesc(String desc);
    
    /**
     * 获得说明
     * @return
     */
    public String getConsdesc();
    /**
     * 设置中心采用变更单
     * @param Source
     */
    public void setZxAdoptNotice(String notice);
    
    /**
     * 获得中心采用变更单
     * @return
     */
    public String getZxAdoptNotice();
    /**
     * 设置父通知单（如果是子采用单则此属性有值，如果是父采用单则此属性为空）
     * @param Source
     */
    public void setParentNotice(String pnotice);
    
    /**
     * 获得父通知单（如果是子采用单则此属性有值，如果是父采用单则此属性为空）
     * @return
     */
    public String getParentNotice();
    
    /**
     * 设置备注1
     * @param Source
     */
    public void setBz1(String bz1);
    
    /**
     * 获得备注1
     * @return
     */
    public String getBz1();
    /**
     * 设置备注2
     * @param Source
     */
    public void setBz2(String bz2);
    
    /**
     * 获得备注2
     * @return
     */
    public String getBz2();
    /**
     * 设置备注3
     * @param Source
     */
    public void setBz3(String bz3);
    
    /**
     * 获得备注3
     * @return
     */
    public String getBz3();
    /**
     * 设置备注4
     * @param Source
     */
    public void setBz4(String bz4);
    
    /**
     * 获得备注1
     * @return
     */
    public String getBz4();
	
}
