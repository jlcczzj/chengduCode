/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.ejb.service;
 
import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.ServiceHomeDelegate;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 刘 明
 * @version 1.0
 */

public class SupplierServiceHomeDelegate implements ServiceHomeDelegate
{
    /**
     * 初始化服务Home接口
     * @param object 服务Home接口实例
     */
    public void init(Object object)
    {
        if (!(object instanceof SupplierServiceHome))
        {
            Object[] objs =
                    {
                    object.getClass(),
                    "SupplierServiceHome"
            };
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkMaterial", "70", objs);
        }
        home = (SupplierServiceHome) object;
    }


    /**
     * 实现ServiceHomeDelegate接口方法。创建代码服务Bean实例
     * @return BaseService 服务接口实例
     */
    public BaseService create()
            throws CreateException
    {
        return home.create();
    }

    private SupplierServiceHome home = null;


}
