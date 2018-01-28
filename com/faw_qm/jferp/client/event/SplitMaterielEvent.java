/**
 * ���ɳ���SplitMaterielEvent.java   1.0              2007-10-10
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.client.event;

import java.util.Vector;
import com.faw_qm.framework.event.EventSupport;

/**
 * <p>Title: ���ϲ���¼��ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class SplitMaterielEvent extends EventSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static int REPLACE = 1;

    public static int DELETE = 2;

    public static int UPDATE = 3;

    public static int SPLIT = 4;

    public static int CANCLE = 5;
    
    //20080103 begin
    public static int CHOOSE = 6;
    //20080103 end
    
    public static int SAMEMATERIAL=7;
    
    public static int DELETESAMEMATERIAL=8;
    
    public static int PUBLISHBYROUTE=9;
    
    public static int PUBLISHBYBASELINE=10;

    /**
     * �ĵ������ݡ�
     */
    private Vector data = new Vector();

    private int actionType = -1;

    private String partids = "";

    private String resplit = "";

    private String splitresult = "";

    private String partid = "";

    private String mateid = "";
    
    //20080103 begin
    private String routes = "";
    //20080103 end
    
    //20081008 begin
    private String sourceId = "";
    //20081008 end

    /**
     * ���캯����
     * @param int actionType1
     * @param DocFormData docFormData1
     */
    public SplitMaterielEvent(int actionType1)
    {
        this.actionType = actionType1;
    }

    /**
     * ��ȡ�ĵ������ݡ�
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
     * ��ȡ�������͡�
     * @return int
     */
    public int getActionType()
    {
        return actionType;
    }

    /**
     * ��ȡ�¼����ơ�
     * @return java.lang.String
     */
    public String getEventName()
    {
        return "com.faw_qm.jferp.client.ejbaction.SplitMaterielEJBAction";
    }

    public void setPartIDs(String ids)
    {
        partids = ids;
    }

    public String getPartIDs()
    {
        return partids;
    }

    public void setResplit(String resplit)
    {
        this.resplit = resplit;
    }

    public String getResplit()
    {
        return resplit;
    }

    public void setSplitResult(String result)
    {
        splitresult = result;
    }

    public String getSplitResult()
    {
        return splitresult;
    }

    public String getPartid()
    {
        return partid;
    }

    public void setPartid(String partid)
    {
        this.partid = partid;
    }

    public void setMateid(String id)
    {
        this.mateid = id;
    }

    public String getMateid()
    {
        return mateid;
    }
    
    //20080103 begin
    public String getRoutes()
    {
        return routes;
    }

    public void setRoutes(String routes)
    {
        this.routes = routes;
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
