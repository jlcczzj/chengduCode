/**
 * 生成程序PromulgatePartLinkEJB.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.ejb.entity;

import javax.ejb.CreateException;
import com.faw_qm.jferp.model.PromulgatePartLinkInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;

/**
 * <p>Title:采用通知书关联零件接口实现 </p>
 * <p>Description: 采用通知书关联零件接口实现</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
abstract public class PromulgatePartLinkEJB extends BinaryLinkEJB
{
    /**
     * 构造函数。
     */
    public PromulgatePartLinkEJB()
    {
        super();
    }

    /**
     * 获取业务类名称。
     * @return "PromulgatePartLink"
     */
    public String getBsoName()
    {
        return "JFPromulgatePartLink";
    }

    /**
     * 设置关联类的值对象。
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
//        PromulgatePartLinkInfo pInfo = (PromulgatePartLinkInfo) info;
    }

    /**
     * 获取关联类的值对象。
     * @return BaseValueIfc
     * @throws QMException
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        PromulgatePartLinkInfo linkInfo = new PromulgatePartLinkInfo();
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
//        PromulgatePartLinkInfo linkInfo = (PromulgatePartLinkInfo) info;
    }

    /**
     * 根据值对象更新关联类的业务对象。
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
//        PromulgatePartLinkInfo linkInfo = (PromulgatePartLinkInfo) info;
    }
}
