/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public interface CompletDocLinkHome extends BsoReferenceHome
{
    /**
     根据主键查找对象
     */
    public CompletDocLink findByPrimaryKey(String bsoID)
            throws
            FinderException;


    /**
     *  根据主键创建对象
     */
    public CompletDocLink create(BaseValueIfc info)
            throws CreateException;


    /**
     * 根据主键和时间戳
     */
    public CompletDocLink create(BaseValueIfc info, Timestamp ct, Timestamp mt)
            throws
            CreateException;

}
