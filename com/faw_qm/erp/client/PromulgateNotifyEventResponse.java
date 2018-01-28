/**
 * 生成程序PromulgateNotifyEventResponse.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.client;

import com.faw_qm.framework.event.EventResponseSupport;

/**
 * <p>Title: 采用通知事件响应类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public class PromulgateNotifyEventResponse extends EventResponseSupport
{
    private static final long serialVersionUID = 1L;

    /**
     * 流ID。
     */
    private String BsoID = "";

    /**
     * UploadDocFileEventResponse构造函数。
     * @param streamIDHashMap
     */
    public PromulgateNotifyEventResponse(String BsoID)
    {
        super(BsoID);
        this.BsoID = BsoID;
    }

    /**
     * 设置操作文档的bsoID。
     * @param streamIDHashMap
     */
    public void setPromulgateNotifyBsoID(String BsoID)
    {
        this.BsoID = BsoID;
    }

    /**
     * 获取操作文档的bsoID。
     * @param streamIDHashMap
     */
    public String getPromulgateNotifyBsoID()
    {
        return BsoID;
    }

    /**
     * 获取事件相应名称。
     * @return java.lang.String
     */
    public String getEventName()
    {
        return "java:comp/env/param/event/PromulgateNotifyEventResponse";
    }
}
