/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoHomeDelegate;
import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: </p> <p>Description: 工艺路线服务端类</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @version 1.0
 */

public class TechnicsRouteListHomeDelegate implements BsoHomeDelegate
{
    private TechnicsRouteListHome home = null;

    public void init(Object obj) 
    {
        if(!(obj instanceof TechnicsRouteListHome))
        {
            Object[] objs = {obj.getClass(), "TechnicsRouteListHome"};
            throw new QMRuntimeException("com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (TechnicsRouteListHome)obj;
    }

    /**
     * 用于根据主键恢复对象
     */
    public BsoReference findByPrimaryKey(String bsoID) throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }

    /**
     * 根据值对象建立业务对象
     */
    public BsoReference create(BaseValueIfc info) throws CreateException
    {
        return home.create(info);
    }

    /**
     * 根据值对象和指定时间戳建立业务对象
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException
    {
        return home.create(info, ct, mt);
    }

}
