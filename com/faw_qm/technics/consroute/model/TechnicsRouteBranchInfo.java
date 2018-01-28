/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/22 �촺Ӣ       ԭ������Ԥ������
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * ·�߷�ֵ֧���� <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw_qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class TechnicsRouteBranchInfo extends BaseValueInfo implements TechnicsRouteBranchIfc
{
    //�Ƿ�����Ҫ·��
    private boolean mainRoute;

    // ·�ߴ��ַ���
    private String routeStr;

    //·��ֵ����
    private TechnicsRouteIfc routeInfo;
    private static final long serialVersionUID = 1L;
    //begin CR1
    private String attribute1;
    private String attribute2;

    //end CR1

    /**
     * ���캯��
     */
    public TechnicsRouteBranchInfo()
    {

    }

    /**
     * �Ƿ���·��ֵ����
     */
    public void setMainRoute(boolean mainRoute)
    {
        this.mainRoute = mainRoute;
    }

    /**
     * ���·�ߴ��Ƿ�Ϊ��Ҫ·�ߣ�Ĭ��ֵΪTrue ,�û��ɱ��ΪFalse
     */
    public boolean getMainRoute()
    {
        return mainRoute;
    }

    public void setRouteStr(String routeString)
    {
        this.routeStr = routeString;
    }

    public String getRouteStr()
    {
        return routeStr;
    }

    /**
     * ��ù���·�߱�ID.
     * @return java.lang.String
     */
    public TechnicsRouteIfc getRouteInfo()
    {
        return this.routeInfo;
    }

    /**
     * ���ù���·�߱�ID.
     * @param routeListID - ����·�߱�ID.
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo)
    {
        this.routeInfo = routeInfo;
    }

    /**
     * �õ���ʶ
     * @return java.lang.String
     */
    public String getIdentity()
    {
        return getBsoName();
    }

    /**
     * ���ҵ���������
     * @return String
     */
    public String getBsoName()
    {
        return "consTechnicsRouteBranch";
    }

    //begin CR1
    /**
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }
    //end CR1
}
