/** 生成程序PartEvent.java	  1.0  2003/05/10
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.part.client.web.actions;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author (correct by 孙贤民)
 * @version 1.0
 */

import java.util.Vector;

import com.faw_qm.framework.event.EventSupport;

public class PartEvent extends EventSupport
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    public static final int ADDAlter = 1;
    public static final int ADDStruAlter = 2;
    public static final int DELETEAlterPart = 3;
    public static final int DELETEStruAlterPart = 4;
    public static final int ADDBeAlteredPart = 5;
    public static final int SavePartConfigSpec = 6;
    public static final int ViewStructure = 7;
    public static final int ShizhiStructure = 8;//shizhi add by lis

    private Vector vector;
    private int actionType = -1;

    public PartEvent(int actionType1, Vector vector)
    {
        this.actionType = actionType1;
        this.vector = vector;
    }

    public Vector getVector()
    {
        return vector;
    }

    public int getActionType()
    {
        return actionType;
    }

    public String getEventName()
    {
        return "com.faw_qm.part.client.web.actions.PartEJBAction";
    }
}
