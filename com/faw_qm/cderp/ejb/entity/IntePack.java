/**
 * 生成程序IntePack.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.ejb.entity;

import java.sql.Timestamp;

import com.faw_qm.cderp.util.IntePackSourceType;
import com.faw_qm.enterprise.ejb.entity.Item;

/**
 * <p>Title: 集成包的接口。</p>
 * <p>Description:集成包的接口。</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public interface IntePack extends Item
{
    /**
     * 设置集成包名称。
     * @param name String
     */
    public void setName(String name);

    /**
     * 设置集成包来源类型。
     * @param name IntePackSourceType
     */
    public void setSourceType(IntePackSourceType sourceType);

    /**
     * 设置集成包来源类型。
     * @param name IntePackSourceType
     */
    public void setSourceTypeStr(String sourceTypeStr);

    /**
     * 设置集成包来源。
     * @param name String
     */
    public void setSource(String source);

    /**
     * 设置集成包状态。
     * @param name int
     */
    public void setState(int state);

    /**
     * 设置集成包创建者。
     * @param name String
     */
    public void setCreator(String creator);

    /**
     * 设置集成包发布者。
     * @param name String
     */
    public void setPublisher(String publisher);

    /**
     * 设置集成包发布时间。
     * @param name String
     */
    public void setPublishTime(Timestamp publishTime);

    /**
     * 获得集成包名称。
     * @return String
     */
    public String getName();

    /**
     * 获得集成包来源类型。
     * @return String
     */
    public IntePackSourceType getSourceType();

    /**
     * 获得集成包来源类型。
     * @return String
     */
    public String getSourceTypeStr();

    /**
     * 获得集成包来源。
     * @return String
     */
    public String getSource();

    /**
     * 获得集成包状态。
     * @return String
     */
    public int getState();

    /**
     * 获得集成包创建者。
     * @return String
     */
    public String getCreator();

    /**
     * 获得集成包发布者。
     * @return String
     */
    public String getPublisher();

    /**
     * 获得集成包发布时间。
     * @return String
     */
    public Timestamp getPublishTime();
}
