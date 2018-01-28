/**
 * 生成程序IntePackHome.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.enterprise.ejb.entity.ItemHome;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public interface IntePackHome extends ItemHome
{
    /**
     * 根据输入的集成包值对象在数据库中创建集成包对象。
     * @param basevalueifc 输入的值对象。
     * @return IntePack
     * @throws CreateException
     */
    public abstract IntePack create(BaseValueIfc basevalueifc)
            throws CreateException;

    /**
     * 根据输入的集成包值对象和时间戳在数据库中创建集成包对象。
     * @param basevalueifc 输入的集成包值对象。
     * @param timestamp 创建时间戳。
     * @param timestamp1 更新时间戳。
     * @return IntePack
     * @throws CreateException
     */
    public abstract IntePack create(BaseValueIfc basevalueifc,
            Timestamp timestamp, Timestamp timestamp1) throws CreateException;

    /**
     * 根据主关键字在数据库中查找集成包对象。
     * @param s 主关键字。
     * @return IntePack
     * @throws FinderException
     */
    public abstract IntePack findByPrimaryKey(String s) throws FinderException;
}
