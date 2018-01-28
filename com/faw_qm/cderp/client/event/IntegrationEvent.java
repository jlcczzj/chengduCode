/**
 * 生成程序IntegrationEvent.java   1.0              2007-10-10
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.client.event;

import java.util.Vector;
import com.faw_qm.framework.event.EventSupport;

/**
 * <p>Title: 集成包事件类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 王海军
 * @version 1.0
 */
public class IntegrationEvent extends EventSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static int CREATE = 1;

    public static int DELETE = 2;

    public static int RELEASE = 3;
    
    public static int RELEASETECH = 4;

    /**
     * 文档表单数据。
     */
    private Vector data = new Vector();

    private int actionType = -1;

    private String bsoID = "";

    /**
     * 构造函数。
     * @param int actionType1
     * @param DocFormData docFormData1
     */
    public IntegrationEvent(int actionType1)
    {
        this.actionType = actionType1;
    }

    /**
     * 获取文档表单数据。
     * @return com.faw_qm.doc.util.DocFormData
     */
    public Vector getVector()
    {
        return data;
    }

    public void setVector(Vector vector)
    {
        data = vector;
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
        return "com.faw_qm.cderp.client.ejbaction.IntegrationEJBAction";
    }

    public void setBsoID(String id)
    {
        bsoID = id;
    }

    public String getBsoID()
    {
        return bsoID;
    }
}
