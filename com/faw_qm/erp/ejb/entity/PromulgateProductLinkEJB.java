/**
 * 生成程序PromulgateProductLinkEJB.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.ejb.entity;

import javax.ejb.CreateException;
import com.faw_qm.erp.model.PromulgateProductLinkInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;

/**
 * <p>Title: 采用通知书关联产品接口实现</p>
 * <p>Description: 采用通知书关联产品接口实现</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
abstract public class PromulgateProductLinkEJB extends BinaryLinkEJB
{
    /**
     * 构造函数。
     */
    public PromulgateProductLinkEJB()
    {
        super();
    }

    /**
     * 获取业务类名称。
     * @return "PromulgateProductLink"
     */
    public String getBsoName()
    {
        return "PromulgateProductLink";
    }

    /**
     * 设置关联类的值对象。
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
//        PromulgateProductLinkInfo pInfo = (PromulgateProductLinkInfo) info;
    }

    /**
     * 获取关联类的值对象。
     * @return BaseValueIfc
     * @throws QMException
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        PromulgateProductLinkInfo linkInfo = new PromulgateProductLinkInfo();
        setValueInfo(linkInfo);
        return linkInfo;
    }

    /**
     * 根据关联类值对象创建关联类的业务对象。
     * @param info BaseValueIfc
     * @throws CreateException
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
//        PromulgateProductLinkInfo linkInfo = (PromulgateProductLinkInfo) info;
    }

    /**
     * 根据值对象更新关联类的业务对象。
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
//        PromulgateProductLinkInfo linkInfo = (PromulgateProductLinkInfo) info;
    }
}
