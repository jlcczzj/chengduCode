/**
 * 生成程序PromulgateNotifyEvent.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.client;

import java.util.Vector;
import com.faw_qm.framework.event.EventSupport;

/**
 * <p>Title: 采用通知事件类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public class PromulgateNotifyEvent extends EventSupport
{
    private static final long serialVersionUID = 1L;

    public static int ADD = 1;

    public static int DELETE = 2;

    public static int UPDATE = 3;

    public static int UPDATEADDDOC = 4;

    public static int UPDATEPRODUCT = 5;

    /**
     * 文档表单数据。
     */
    private Vector data = new Vector();

    private int actionType = -1;

    /**
     * 构造函数。
     * @param int actionType1
     * @param DocFormData docFormData1
     */
    public PromulgateNotifyEvent(int actionType1, Vector data)
    {
        this.actionType = actionType1;
        this.data = data;
    }

    /**
     * 获取文档表单数据。
     * @return com.faw_qm.doc.util.DocFormData
     */
    public Vector getVector()
    {
        return data;
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
        return "com.faw_qm.erp.client.PromulgateNotifyEJBAction";
    }
}
