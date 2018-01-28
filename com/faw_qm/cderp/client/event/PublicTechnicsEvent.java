/**
 * 生成程序PublicProcessEvent.java	1.0              2007-11-5
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.client.event;

import com.faw_qm.framework.event.EventSupport;

/**
 * <p>Title:发布工艺事件类 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class PublicTechnicsEvent extends EventSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static int REALEASE = 1;

    private int actionType = -1;

    private String processIDs = "";

    /**
     * 构造函数。
     * @param int actionType1
     * @param DocFormData docFormData1
     */
    public PublicTechnicsEvent(int actionType)
    {
        this.actionType = actionType;
    }

    /**
     * 获取动作类型。
     * @return int
     */
    public int getActionType()
    {
        return actionType;
    }

    /**
     * 获取事件名称。
     * @return java.lang.String
     */
    public String getEventName()
    {
        return "com.faw_qm.cderp.client.ejbaction.PublicTechnicsEJBAction";
    }

    /**
     * 设置发布的工艺规程ID。
     * @param ids
     */
    public void setProcessIDs(String ids)
    {
        processIDs = ids;
    }

    /**
     * 获取发布的工艺规程ID。
     * @return
     */
    public String getProcessIDs()
    {
        return processIDs;
    }
}