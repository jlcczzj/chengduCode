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

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.util.RouteHelper;

/**
 * ����·�߷�֧����·�ߴ� <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author �ܴ�Ԫ zz ��������ֶ�routeStr ����ƺõ�·�ߴ�
 * @version 1.0
 */
public abstract class TechnicsRouteBranchEJB extends BsoReferenceEJB
{

    public TechnicsRouteBranchEJB()
    {

    }

    /**
     * �����Ƿ�����Ҫ·��
     */
    public abstract void setMainRoute(boolean mainRoute);

    /**
     * ���·�ߴ��Ƿ�Ϊ��Ҫ·�ߣ�Ĭ��ֵΪTrue ,�û��ɱ��ΪFalse
     */
    public abstract boolean getMainRoute();

    /**
     * �õ�·�ߵ�id
     */
    public abstract java.lang.String getRouteID();

    /**
     * ����·�ߴ����ַ���
     */
    public abstract void setRouteStr(String routeID);

    /**
     * �õ�·�ߴ����ַ���
     */
    public abstract java.lang.String getRouteStr();

    /**
     * ����·�ߵ�id
     */
    public abstract void setRouteID(String routeID);

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
        return "consTechnicsRouteBranch";
    }

    /**
     * ���ֵ����
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        TechnicsRouteBranchInfo info = new TechnicsRouteBranchInfo();
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
        TechnicsRouteBranchInfo branchInfo = (TechnicsRouteBranchInfo)info;
        branchInfo.setMainRoute(this.getMainRoute());
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)RouteHelper.refreshInfo(this.getRouteID());
        branchInfo.setRouteInfo(routeInfo);
        branchInfo.setRouteStr(this.getRouteStr());
        //begin CR1
        branchInfo.setAttribute1(this.getAttribute1());
        branchInfo.setAttribute2(this.getAttribute2());
        //end CR1
    }

    /**
     * ����ֵ������г־û���
     * @throws CreateException 
     * @throws QMException 
     * @throws QMException 
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter TechnicsRouteBranchEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        TechnicsRouteBranchInfo branchInfo = (TechnicsRouteBranchInfo)info;
        this.setMainRoute(branchInfo.getMainRoute());
        if(branchInfo.getRouteInfo() == null || branchInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMRuntimeException("·�ߴ����õĹ���·��ֵ���������д���");
        }
        this.setRouteID(branchInfo.getRouteInfo().getBsoID());
        // zz
        this.setRouteStr(branchInfo.getRouteStr());
        // zz
        //begin CR1
        this.setAttribute1(branchInfo.getAttribute1());
        this.setAttribute2(branchInfo.getAttribute2());
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
            System.out.println("enter TechnicsRouteBranchEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        TechnicsRouteBranchInfo branchInfo = (TechnicsRouteBranchInfo)info;
        this.setMainRoute(branchInfo.getMainRoute());
        if(branchInfo.getRouteInfo() == null || branchInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMException("·�ߴ����õĹ���·��ֵ���������д���");
        }
        this.setRouteID(branchInfo.getRouteInfo().getBsoID());
        this.setRouteStr(branchInfo.getRouteStr());
        //begin CR1
        this.setAttribute1(branchInfo.getAttribute1());
        this.setAttribute2(branchInfo.getAttribute2());
        //end CR1
    }

}
