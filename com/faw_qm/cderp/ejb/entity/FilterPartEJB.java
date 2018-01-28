/**
 * 生成程序PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;

import com.faw_qm.cderp.model.FilterPartInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;

/**
 * <p>Title: 发布的零件实现</p>
 * <p>Description: 发布的零件实现</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public abstract class FilterPartEJB extends BsoReferenceEJB
{
    public FilterPartEJB()
    {
    }

    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "JFFilterPart";
    }

    /**
     * getBsoID
     *
     * @return String
     */
    public abstract String getBsoID();

    /**
     * setBsoID
     *
     * @param string String
     */
    public abstract void setBsoID(String string);

    /**
     * getCreateTime
     *
     * @return Timestamp
     */
    public abstract Timestamp getCreateTime();

    /**
     * setCreateTime
     *
     * @param timestamp Timestamp
     */
    public abstract void setCreateTime(Timestamp timestamp);

    /**
     * getModifyTime
     *
     * @return Timestamp
     */
    public abstract Timestamp getModifyTime();

    /**
     * setModifyTime
     *
     * @param timestamp Timestamp
     */
    public abstract void setModifyTime(Timestamp timestamp);
    /**
     * 设置采用类型
     * @param noticeType String
     */
    public abstract void setNoticeType(String noticeType);

    /**
     * 获得采用类型
     * @return String
     */
    public abstract String getNoticeType();
    /**
     * 设置采用编号
     * @param noticeNumber String
     */
    public abstract void setNoticeNumber(String noticeNumber);
    /**
     * 获得采用标号
     * @return String
     */
    public abstract String getNoticeNumber();
    /**
     * 设置状态
     * @param state String
     */
    public abstract void setState(String state);
    /**
     * 获得状态
     * @return String
     */
    public abstract String getState();
    /**
     * 设置版本信息
     * @param versionValue String
     */
    public abstract void setVersionValue(String versionValue);
    /**
     * 获得版本信息
     * @return String
     */
    public abstract String getVersionValue();
    /**
     * 设置零件信息
     * @param partNumber String
     */
    public abstract void setPartNumber(String partNumber);
    /**
     * 获得零件信息
     * @return String
     */
    public abstract String getPartNumber();
    
    public abstract String getRoute();
    
    public abstract void setRoute(String route);

    /**
     *获取业务对象对应的值对象
     * @throws QMException
     * @return BaseValueIfc
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        FilterPartInfo info = new FilterPartInfo();
        setValueInfo(info);
        return info;
    }

    /**
     *设置业务对象对应的值对象，需要设定锁服务的信息
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        FilterPartInfo minfo = (FilterPartInfo) info;
        minfo.setNoticeNumber(this.getNoticeNumber());
        minfo.setNoticeType(this.getNoticeType());
        minfo.setPartNumber(this.getPartNumber());
        minfo.setState(this.getState());
        minfo.setVersionValue(this.getVersionValue());
        minfo.setRoute(this.getRoute());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        FilterPartInfo minfo = (FilterPartInfo) info;
        //需要进行属性校验,
        setNoticeNumber(minfo.getNoticeNumber());
        setNoticeType(minfo.getNoticeType());
        setPartNumber(minfo.getPartNumber());
        setState(minfo.getState());
        setVersionValue(minfo.getVersionValue());
        setRoute(minfo.getRoute());
    }

    /**
     *过载父类的方法。首先调用父类的相应方法，其后在为本类中定制的业务属性赋值。
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        FilterPartInfo minfo = (FilterPartInfo) info;
        setNoticeNumber(minfo.getNoticeNumber());
        setNoticeType(minfo.getNoticeType());
        setPartNumber(minfo.getPartNumber());
        setState(minfo.getState());
        setVersionValue(minfo.getVersionValue());
        setRoute(minfo.getRoute());
    }
}
