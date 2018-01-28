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

import javax.ejb.CreateException;

import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.util.RouteCategoryType;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * ·�߽ڵ� <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw-qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public abstract class RouteNodeEJB extends BsoReferenceEJB
{

    public RouteNodeEJB()
    {

    }

    /**
     * ����·���еĽڵ�˵����
     */
    public abstract java.lang.String getNodeDescription();

    /**
     * ����·���еĽڵ�˵����
     */
    public abstract void setNodeDescription(String description);

    /**
     * ����·��ID.
     */
    public abstract java.lang.String getRouteID();

    /**
     * ����·��ID.
     */
    public abstract void setRouteID(String routeID);

    /**
     * ���·�ߵ�λ��
     */
    public abstract java.lang.String getNodeDepartment();

    /**
     * ����·�ߵ�λ��
     */
    public abstract void setNodeDepartment(String department);

    /**
     * ��¼·�߽ڵ����������컹��װ�䣬��ö�ټ�����ȡֵ{װ��/����}��Ĭ��ֵΪ����
     */
    public abstract java.lang.String getRouteType();

    /**
     * ��¼·�߽ڵ����������컹��װ�䣬��ö�ټ�����ȡֵ{װ��/����}��Ĭ��ֵΪ����
     */
    public abstract void setRouteType(String routeType);

    //st skybird 2005.2.25
    // gcy add in 2005.4.26 for reinforce requirement  start
    /**
     *����·���еĽڵ����˵��(��ʱ����)
     */
    public abstract java.lang.String getNodeRatio();

    /**
     * ����·���еĽڵ�����˵������ʱ���ã�
     */
    public abstract void setNodeRatio(String ratio);

    /**
     * �õ��Ƿ�����ʱ·��
     * @return boolean
     */
    public abstract boolean getIsTempRoute();

    /**
     * �����Ƿ�����ʱ·��
     * @param temp boolean
     */
    public abstract void setIsTempRoute(boolean temp);

    /**
     * ����·�߽ڵ��е���Чʱ��˵��
     * @return validTime
     */
    public abstract java.lang.String getNodeValidTime();

    /**
     * ����·���еĽڵ����Чʱ��
     */
    public abstract void setNodeValidTime(String validTime);

    /**
     * ����·���еĽӵ�ʧЧʱ��˵��
     */
    public abstract java.lang.String getNodeInvalidTime();

    /**
     * ����·���еĽڵ��ʧЧʱ��˵��
     */
    public abstract void setNodeInvalidTime(String invalidTime);

    // gcy add in 2005.4.26 for reinforce requirement  end

    /**
     * ��ýڵ��X���ꡣ
     */
    public abstract long getX();

    /**
     * ���ýڵ��X����
     */
    public abstract void setX(long Xlocation);

    /**
     * ��ýڵ��Y���ꡣ
     */
    public abstract long getY();

    /**
     * ���ýڵ��Y����
     */
    public abstract void setY(long Ylocation);

    /**
     * ��ù���·�߷�֦ID.
     */
    public abstract java.lang.String getRouteBranchID();

    /**
     * ���ù���·�߷�֦ID.
     */
    public abstract void setRouteBranchID(String routeBranchID);

    //begin CR1
    /**
     * ����Ԥ������1
     */
    public abstract void setAttribute1(String attribute1);

    /**
     * ���Ԥ������1
     */
    public abstract String getAttribute1();

    /**
     * ����Ԥ������2
     */
    public abstract void setAttribute2(String attribute2);

    /**
     * ���Ԥ������2
     */
    public abstract String getAttribute2();

    //end CR1

    /**
     * ���ҵ���������
     */
    public String getBsoName()
    {
        return "consRouteNode";
    }

    
    /**
     * ���ֵ����
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        RouteNodeInfo info = new RouteNodeInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * ���½�ֵ����������á�
     * @throws QMException 
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        RouteNodeInfo nodeInfo = (RouteNodeInfo)info;
        if(this.getNodeDepartment() != null)
        {
            nodeInfo.setNodeDepartmentName(getDepartmentName(this.getNodeDepartment()));
            nodeInfo.setNodeDepartment(this.getNodeDepartment());
        }else
        {
            throw new QMException("��λID��Ӧ��Ϊ�ա�");
        }
        nodeInfo.setNodeDescription(this.getNodeDescription());
        nodeInfo.setRouteType(RouteCategoryType.toRouteCategoryType(this.getRouteType()).getDisplay());
        //st skybird 20025.2.25
        // gcy add in 2005.4.26 for reinforce requirement  start
        nodeInfo.setIsTempRoute(this.getIsTempRoute());
        nodeInfo.setNodeRatio(this.getNodeRatio());
        nodeInfo.setNodeValidTime(this.getNodeValidTime());
        nodeInfo.setNodeInvalidTime(this.getNodeInvalidTime());
        // gcy add in 2005.4.26 for reinforce requirement  end
        //ed
        //nodeInfo.setStartNode(this.getStartNode());
        nodeInfo.setX(this.getX());
        nodeInfo.setY(this.getY());
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)RouteHelper.refreshInfo(this.getRouteID());
        nodeInfo.setRouteInfo(routeInfo);
        //begin CR1
        nodeInfo.setAttribute1(this.getAttribute1());
        nodeInfo.setAttribute2(this.getAttribute2());
        //end CR1
    }

    //��õ�λ���ơ�
    private String getDepartmentName(String departmentID) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        BaseValueIfc codeInfo = pservice.refreshInfo(departmentID);
        String departmentName = null;
        if(codeInfo instanceof CodingIfc)
        {
            departmentName = ((CodingIfc)codeInfo).getShorten();
        }
        if(codeInfo instanceof CodingClassificationIfc)
        {
            departmentName = ((CodingClassificationIfc)codeInfo).getClassSort();
        }
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("RouteNodeEJB's getDepartmentName name = " + departmentName);
        }
        return departmentName;
    }

    /**
     * ����ֵ������г־û���
     * @throws CreateException 
     * @throws QMException 
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteNodeEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        RouteNodeInfo nodeInfo = (RouteNodeInfo)info;
        this.setNodeDepartment(nodeInfo.getNodeDepartment());
        this.setNodeDescription(nodeInfo.getNodeDescription());
        this.setRouteType(RouteHelper.getValue(RouteCategoryType.getRouteCategoryTypeSet(), nodeInfo.getRouteType()));

        //st skybird 2005.2.25
        // gcy add in 2005.4.26 for reinforce requirement  start
        this.setIsTempRoute(nodeInfo.getIsTempRoute());
        this.setNodeRatio(nodeInfo.getNodeRatio());
        this.setNodeValidTime(nodeInfo.getNodeValidTime());
        this.setNodeInvalidTime(nodeInfo.getNodeInvalidTime());
        // gcy add in 2005.4.26 for reinforce requirement  end
        //ed

        //this.setStartNode(nodeInfo.getStartNode());
        this.setX(nodeInfo.getX());
        this.setY(nodeInfo.getY());
        if(nodeInfo.getRouteInfo() == null || nodeInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMRuntimeException("����·�߽ڵ����õĹ���·��ֵ���������д���");
        }
        this.setRouteID(nodeInfo.getRouteInfo().getBsoID());
        //begin CR1
        this.setAttribute1(nodeInfo.getAttribute1());
        this.setAttribute2(nodeInfo.getAttribute2());
        //end CR1
    }

    /**
     * ����ֵ������и��¡�
     * @throws QMException 
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteNodeEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        RouteNodeInfo nodeInfo = (RouteNodeInfo)info;
        this.setNodeDepartment(nodeInfo.getNodeDepartment());
        this.setNodeDescription(nodeInfo.getNodeDescription());
        this.setRouteType(RouteHelper.getValue(RouteCategoryType.getRouteCategoryTypeSet(), nodeInfo.getRouteType()));
        //st skybird 2005.2.25
        // gcy add in 2005.4.26 for reinforce requirement  start
        this.setIsTempRoute(nodeInfo.getIsTempRoute());
        this.setNodeRatio(nodeInfo.getNodeRatio());
        this.setNodeValidTime(nodeInfo.getNodeValidTime());
        this.setNodeInvalidTime(nodeInfo.getNodeInvalidTime());
        // gcy add in 2005.4.26 for reinforce requirement  end
        //ed

        //this.setStartNode(nodeInfo.getStartNode());
        this.setX(nodeInfo.getX());
        this.setY(nodeInfo.getY());
        if(nodeInfo.getRouteInfo() == null || nodeInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMException("����·�߽ڵ����õĹ���·��ֵ���������д���");
        }
        this.setRouteID(nodeInfo.getRouteInfo().getBsoID());
        //begin CR1
        this.setAttribute1(nodeInfo.getAttribute1());
        this.setAttribute2(nodeInfo.getAttribute2());
        //end CR1
    }
}
