/**
 * 生成程序IntePackEJB.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import com.faw_qm.enterprise.ejb.entity.ItemEJB;
import com.faw_qm.erp.model.IntePackInfo;
import com.faw_qm.erp.util.IntePackSourceType;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public abstract class IntePackEJB extends ItemEJB
{
    /**
     * 构造函数。
     */
    public IntePackEJB()
    {
        super();
    }

    /**
     * 获取业务类名。
     * @return "QMPart".
     */
    public String getBsoName()
    {
        return "IntePack";
    }



    /**
     * setBsoID
     *
     * @param string String
     */
    public abstract void setBsoID(String string);

    /**
     * 设置集成包创建时间。
     * @param timestamp Timestamp
     */
    public abstract void setCreateTime(Timestamp timestamp);

    /**
     * 设置集成包名称。
     * @param name String
     */
    public abstract void setName(String name);

    /**
     * 设置集成包来源类型。
     * @param name IntePackSourceType
     */
    public abstract void setSourceTypeStr(String sourceTypeStr);

    /**
     * 设置集成包来源类型。
     * @param name IntePackSourceType
     */
    public void setSourceType(IntePackSourceType sourceType)
    {
        setSourceTypeStr(sourceType.toString());
    }

    /**
     * 设置集成包来源。
     * @param name String
     */
    public abstract void setSource(String source);

    /**
     * 设置集成包状态。
     * @param name int
     */
    public abstract void setState(int state);

    /**
     * 设置集成包创建者。
     * @param name String
     */
    public abstract void setCreator(String creator);

    /**
     * 设置集成包发布者。
     * @param name String
     */
    public abstract void setPublisher(String publisher);

    /**
     * 设置集成包发布时间。
     * @param name String
     */
    public abstract void setPublishTime(Timestamp publishTime);

    /**
     * getBsoID
     *
     * @return String
     */
    public abstract String getBsoID();

    /**
     * getCreateTime
     *
     * @return Timestamp
     */
    public abstract Timestamp getCreateTime();

    /**
     * 获得集成包名称。
     * @return String
     */
    public abstract String getName();

    /**
     * 获得集成包来源类型。
     * @return String
     */
    public abstract String getSourceTypeStr();

    /**
     * 获得集成包来源类型。
     * @return String
     */
    public IntePackSourceType getSourceType()
    {
        return IntePackSourceType.toIntePackSourceType(getSourceTypeStr());
    }

    /**
     * 获得集成包来源。
     * @return String
     */
    public abstract String getSource();

    /**
     * 获得集成包状态。
     * @return String
     */
    public abstract int getState();

    /**
     * 获得集成包创建者。
     * @return String
     */
    public abstract String getCreator();

    /**
     * 获得集成包发布者。
     * @return String
     */
    public abstract String getPublisher();

    /**
     * 获得集成包发布时间。
     * @return String
     */
    public abstract Timestamp getPublishTime();

    /**
     * 获取业务对象对应的值对象。
     * @return BaseValueIfc
     * @throws QMException
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        IntePackInfo info = new IntePackInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * 设置业务对象对应的值对象。
     * @param info BasevalueIfc
     * @throws QMException
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        IntePackInfo inteInfo = (IntePackInfo) info;
        inteInfo.setCreator(this.getCreator());
        inteInfo.setName(this.getName());
        inteInfo.setPublisher(this.getPublisher());
        inteInfo.setPublishTime(this.getPublishTime());
        inteInfo.setSource(this.getSource());
        inteInfo.setSourceType(this.getSourceType());
        inteInfo.setState(this.getState());
    }

    /**
     * 根据值对象创建业务对象。
     * @param info BaseValueIfc
     * @throws CreateException
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        IntePackInfo inteInfo = (IntePackInfo) info;
        //需要进行属性校验。
        try
        {
            inteInfo.attrValidate();
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
            throw new CreateException(ex.getClientMessage());
        }
        this.setCreator(inteInfo.getCreator());
        this.setName(inteInfo.getName());
        this.setPublisher(inteInfo.getPublisher());
        this.setPublishTime(inteInfo.getPublishTime());
        this.setSource(inteInfo.getSource());
        IntePackSourceType sourceTypeStr=inteInfo.getSourceType();
        if(sourceTypeStr!=null){
        	 this.setSourceType(inteInfo.getSourceType());
        }
        this.setState(inteInfo.getState());
    }

    /**
     * 根据值对象更新业务对象。
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        IntePackInfo inteInfo = (IntePackInfo) info;
        //需要进行属性校验。
        inteInfo.attrValidate();
        this.setCreator(inteInfo.getCreator());
        this.setName(inteInfo.getName());
        this.setPublisher(inteInfo.getPublisher());
        this.setPublishTime(inteInfo.getPublishTime());
        this.setSource(inteInfo.getSource());
        this.setSourceType(inteInfo.getSourceType());
        this.setState(inteInfo.getState());
    }
}
