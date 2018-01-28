/**
 * ���ɳ��� TechnicsRouteBranchIfc.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/22 �촺Ӣ       ԭ������Ԥ������
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title:TechnicsRouteBranchIfc.java</p> <p>Description: ����·�߷�֧�ӿ�</p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface TechnicsRouteBranchIfc extends CappIdentity, BaseValueIfc
{
    /**
     * �Ƿ�����Ҫ·��
     */
    public void setMainRoute(boolean mainRoute);

    /**
     * ���·�ߴ��Ƿ�Ϊ��Ҫ·�ߣ�Ĭ��ֵΪTrue ,�û��ɱ��ΪFalse
     */
    public boolean getMainRoute();

    public void setRouteStr(String srt);

    public String getRouteStr();

    /**
     * �õ�·��ֵ����
     */
    public TechnicsRouteIfc getRouteInfo();

    /**
     * ����·��ֵ����
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo);

    //begin CR1
    /**
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1);

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1();

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2);

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2();
    //end CR1

}
