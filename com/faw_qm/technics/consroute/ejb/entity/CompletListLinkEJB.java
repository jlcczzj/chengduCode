/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;
import com.faw_qm.technics.consroute.model.*;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class CompletListLinkEJB extends BinaryLinkEJB
{

    /**
     * 获取值对象
     * @return 值对象
     */
    public BaseValueIfc getValueInfo()
            throws QMException
    {
        CompletListLinkInfo info = new CompletListLinkInfo();
        setValueInfo(info);
        return info;
    }


    /**
     * 设置值对象
     * @param info  值对象
     */
    public void setValueInfo(BaseValueIfc info)
            throws QMException
    {
        super.setValueInfo(info);
    }


    /**
     * 获得类名
     * @return 类名
     */
    public String getBsoName()
    {
        return "consCompletListLink";
    }


    /**
     * 根据值对象创建业务对象
     * @param info  值对象
     */
    public void createByValueInfo(BaseValueIfc info)
            throws CreateException
    {
        super.createByValueInfo(info);
    }


    /**
     * 根据值对象修改业务对象
     * @param info  值对象
     */
    public void updateByValueInfo(BaseValueIfc info)
            throws QMException
    {
        super.updateByValueInfo(info);
    }
}
