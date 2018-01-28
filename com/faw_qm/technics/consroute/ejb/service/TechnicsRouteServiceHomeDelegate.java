/**
 * 版权归一汽启明公司所有

 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.service;

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.ServiceHomeDelegate;

/**
 * <p>Title: </p> <p>Description: 工艺路线服务端类</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @version 1.0
 */

public class TechnicsRouteServiceHomeDelegate implements ServiceHomeDelegate
{
    private TechnicsRouteServiceHome home = null;

    public void init(Object obj) 
    {
        if(!(obj instanceof TechnicsRouteServiceHome))
        {
            Object[] objs = {obj.getClass(), "TechnicsRouteServiceHome"};
            throw new QMRuntimeException("com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (TechnicsRouteServiceHome)obj;
    }

    public BaseService create() throws CreateException
    {
        return home.create();
    }

}
