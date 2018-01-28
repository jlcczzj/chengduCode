/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.RouteListProductLinkInfo;

//leftID = sourceNodeID, rightID = destinationNodeID.
/**
 * ·�߱�master�Ͳ�Ʒmaster������
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public abstract class RouteListProductLinkEJB extends BinaryLinkEJB
{

    /**
     * @roseuid 4039F29D00AA
     */
    public RouteListProductLinkEJB()
    {

    }

    /**
     * ���ҵ�������
     * @return String RouteListProductLink
     */
    public String getBsoName()
    {
        return "consRouteListProductLink";
    }

    /**
     * ���ֵ����
     * @throws QMException
     * @return BaseValueIfc ֵ����
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        RouteListProductLinkInfo info = new RouteListProductLinkInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * ���½�ֵ����������á�
     * @param info BaseValueIfc ֵ����
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
    }

    /**
     * ����ֵ������г־û���
     * @param info BaseValueIfc ֵ����
     * @throws CreateException 
     * @throws CreateException
     * @see BaseValueInfo
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteListProductLinkEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
    }

    /**
     * ����ֵ������и��¡�
     * @param info BaseValueIfc ֵ����
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteListProductLinkEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
    }
}
