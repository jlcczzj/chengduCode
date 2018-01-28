/**
 * 生成程序IntegrationEventResponse.java   1.0              2007-10-10
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.client.event;

import com.faw_qm.framework.event.EventResponseSupport;

/**
 * <p>Title: 集成包事件响应类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 王海军
 * @version 1.0
 */
public class IntegrationEventResponse extends EventResponseSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 流ID。
     */
    private String BsoID = "";

    private String message = "";

    /**
     * UploadDocFileEventResponse构造函数。
     * @param streamIDHashMap
     */
    public IntegrationEventResponse(String BsoID)
    {
        super(BsoID);
        this.BsoID = BsoID;
    }

    /**
     * 获取操作文档的bsoID。
     * @param streamIDHashMap
     */
    public String getBsoID()
    {
        return BsoID;
    }

    /**
     * 设置操作后的返回信息
     * @param message
     */
    public void setResult(String message)
    {
        this.message = message;
    }

    /**
     * 获取信息
     * @return
     */
    public String getResult()
    {
        return message;
    }

    /**
     * 获取事件相应名称。
     * @return java.lang.String
     */
    public String getEventName()
    {
        return "java:comp/env/param/event/IntegrationEventResponse";
    }
}
