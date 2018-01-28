/**
 * ���ɳ���SplitMaterielEventResponse.java   1.0              2007-10-10
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.client.event;

import com.faw_qm.framework.event.EventResponseSupport;

/**
 * <p>Title: ���ϲ���¼���Ӧ�ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class SplitMaterielEventResponse extends EventResponseSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * ��ID��
     */
    private String BsoID = "";

    private String partids = "";

    private String partid = "";
    
    //20080103 begin
    //�㲿���Ƿ����²��
    private String resplit = "";

    //�㲿����ֺ��Ƿ�������ʾ
    private String show = "";
    //20080103 end
    
    //20081008 begin
    private String sourceId = "";
    //20081008 end

    /**
     * UploadDocFileEventResponse���캯����
     * @param streamIDHashMap
     */
    public SplitMaterielEventResponse(String BsoID)
    {
        super(BsoID);
        this.BsoID = BsoID;
    }

    /**
     * ���ò����ĵ���bsoID��
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
     * ��ȡ�����ĵ���bsoID��
     * @param streamIDHashMap
     */
    public String getBsoID()
    {
        return BsoID;
    }

    /**
     * ��ȡ�¼���Ӧ���ơ�
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
     * �����㲿���Ƿ����²��Ϊ���ϵı�־��
     * @param resplit
     */
    public void setResplit(String resplit)
    {
        this.resplit = resplit;
    }

    /**
     * ��ȡ�㲿���Ƿ����²�����ϵı�־��
     * @return
     */
    public String getResplit()
    {
        return resplit;
    }

    /**
     * �����㲿����ֺ��Ƿ�������ʾ�ı�־��
     * @param result
     */
    public void setShow(String result)
    {
        show = result;
    }

    /**
     * ��ȡ�㲿����ֺ��Ƿ�������ʾ�ı�־��
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
