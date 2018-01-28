/**
 * 生成程序PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import com.faw_qm.jferp.model.FileNameLinkInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;

/**
 * <p>Title:发布和文件名称的关联实现</p>
 * <p>Description: 发布和文件名称的关联实现</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public abstract class FileNameLinkEJB extends BsoReferenceEJB
{
    public FileNameLinkEJB()
    {
    }

    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "JFFileNameLink";
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
     * 设置文件名
     * @param fileName String
     */
    public abstract void setFileName(String fileName);

    /**
     * 获得文件名
     * @return String
     */
    public abstract String getFileName();

    public abstract void setNotice(String notice);

    public abstract String getNotice();

    /**
     *获取业务对象对应的值对象
     * @throws QMException
     * @return BaseValueIfc
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        FileNameLinkInfo info = new FileNameLinkInfo();
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
        FileNameLinkInfo minfo = (FileNameLinkInfo) info;
        minfo.setFileName(this.getFileName());
        minfo.setNotice(this.getNotice());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        FileNameLinkInfo minfo = (FileNameLinkInfo) info;
        //需要进行属性校验,
        setFileName(minfo.getFileName());
        setNotice(minfo.getNotice());
    }

    /**
     *过载父类的方法。首先调用父类的相应方法，其后在为本类中定制的业务属性赋值。
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        FileNameLinkInfo minfo = (FileNameLinkInfo) info;
        setFileName(minfo.getFileName());
        setNotice(minfo.getNotice());
    }
}
