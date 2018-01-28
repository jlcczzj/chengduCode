/**
 * 生成程序PublicProcessEventResponse.java	1.0              2007-11-5
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.client.event;

import com.faw_qm.framework.event.EventResponseSupport;

/**
 * <p>Title: 发布工艺事件响应类。。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class PublicTechnicsEventResponse extends EventResponseSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * UploadDocFileEventResponse构造函数。
     * @param streamIDHashMap
     */
    public PublicTechnicsEventResponse(String BsoID)
    {
        super(BsoID);
    }

    /**
     * 获取事件相应名称。
     * @return java.lang.String
     */
    public String getEventName()
    {
        return "java:comp/env/param/event/PublicTechnicsEventResponse";
    }
}
