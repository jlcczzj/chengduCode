/**
 * 生成程序SplitMaterielEventResponse.java   1.0              2007-10-10
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.client.event;

import com.faw_qm.framework.event.EventResponseSupport;

/**
 * <p>Title: 物料拆分事件响应类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 王海军
 * @version 1.0
 */
public class SplitMaterielEventResponse extends EventResponseSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 流ID。
     */
    private String BsoID = "";

    private String partids = "";

    private String partid = "";
    
    //20080103 begin
    //零部件是否重新拆分
    private String resplit = "";

    //零部件拆分后是否立即显示
    private String show = "";
    //20080103 end
    
    //20081008 begin
    private String sourceId = "";
    //20081008 end

    /**
     * UploadDocFileEventResponse构造函数。
     * @param streamIDHashMap
     */
    public SplitMaterielEventResponse(String BsoID)
    {
        super(BsoID);
        this.BsoID = BsoID;
    }

    /**
     * 设置操作文档的bsoID。
     * @param streamIDHashMap
     */
    public void setPartIDs(String BsoID)
    {
        this.partids = BsoID;
    }

    public String getPartIDs()
    {
        return partids;
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
     * 获取事件相应名称。
     * @return java.lang.String
     */
    public String getEventName()
    {
        return "java:comp/env/param/event/SplitMaterielEventResponse";
    }

    public String getPartid()
    {
        return partid;
    }

    public void setPartid(String id)
    {
        partid = id;
    }
    
    //20080103 begin
    /**
     * 设置零部件是否重新拆分为物料的标志。
     * @param resplit
     */
    public void setResplit(String resplit)
    {
        this.resplit = resplit;
    }

    /**
     * 获取零部件是否重新拆分物料的标志。
     * @return
     */
    public String getResplit()
    {
        return resplit;
    }

    /**
     * 设置零部件拆分后是否立即显示的标志。
     * @param result
     */
    public void setShow(String result)
    {
        show = result;
    }

    /**
     * 获取零部件拆分后是否立即显示的标志。
     * @return
     */
    public String getShow()
    {
        return show;
    }
    //20080103 end
    
    //20081008 begin
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
    //20081008 end
}
