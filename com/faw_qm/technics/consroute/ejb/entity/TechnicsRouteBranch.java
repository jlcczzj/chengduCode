/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/22 �촺Ӣ      ԭ��:����Ԥ������
 */

package com.faw_qm.technics.consroute.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * ����·�߷�֧����·�ߴ� <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface TechnicsRouteBranch extends BsoReference
{
    /**
     * �����Ƿ�����Ҫ·��
     */
    public void setMainRoute(boolean mainRoute);

    /**
     * ���·�ߴ��Ƿ�Ϊ��Ҫ·�ߣ�Ĭ��ֵΪTrue ,�û��ɱ��ΪFalse
     */
    public boolean getMainRoute();

    /**
     *�õ�·�ߵ�id
     */
    public java.lang.String getRouteID();

    /**
     * ����·�ߵ�id
     */
    public void setRouteID(String routeID);

    /**
     * �õ�·�ߴ��ַ���
     * @return String
     */
    public java.lang.String getRouteStr();

    public void setRouteStr(String routeID);

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
