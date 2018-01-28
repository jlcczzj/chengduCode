/**
 * 生成程序 RouteListNumberedNamedIdentifier.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.util;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.identity.StandardNumberedNamedIdentifier;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterIfc;

/**
 * <p>Title:RouteListNumberedNamedIdentifier.java</p> <p>Description:工艺路线编号名称标识 </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:一汽启明</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteListNumberedNamedIdentifier extends StandardNumberedNamedIdentifier
{
    /**
     * 构造函数
     * @param obj
     */
    public RouteListNumberedNamedIdentifier(Object obj) throws QMException
    {
        number = ((TechnicsRouteListMasterIfc)obj).getRouteListNumber();
        try
        {
            name = ((TechnicsRouteListMasterIfc)obj).getRouteListName();
            return;
        }catch(Exception qmruntimeexception1)
        {
            System.out.println(qmruntimeexception1);
        }
        System.out.println("ignoring - set name to null ");
        name = null;
    }

}
