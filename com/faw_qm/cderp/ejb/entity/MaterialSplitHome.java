/**
 * 生成程序MaterialSplitHome.java	1.0              2007-9-25
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
 * @author 谢斌
 * @version 1.0
 */
public interface MaterialSplitHome extends ItemHome
{
    MaterialSplit create(BaseValueIfc basevalueifc) throws CreateException;

    MaterialSplit create(BaseValueIfc basevalueifc, Timestamp timestamp,
            Timestamp timestamp1) throws CreateException;

    MaterialSplit findByPrimaryKey(String s) throws FinderException;
}
