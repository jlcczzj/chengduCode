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

import com.faw_qm.enterprise.ejb.entity.MasterEJB;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterInfo;

/**
 * ·�߱�����Ϣ ���Ψһ�����̳�Ψһ�԰���Identified�ӿڡ�ͨ�������ݿ�������Ψһ��Լ����֤��Ψһ�� �ڿͻ��˽�ȡ�쳣ʱ�������⴦�� ���������ʼ�����Ӧ�����°汾һ�¡� ע�⳷�����ʱ���������°汾������һ���ԡ�
 * @author ������
 * @version 1.0
 */
public abstract class TechnicsRouteListMasterEJB extends MasterEJB
{

    public TechnicsRouteListMasterEJB()
    {}

    /**
     * ��ù���·�߱�ı�ţ�Ψһ��ʶ ����·�߱��а汾����ŵ�Ψһ���ڶ�Ӧ��Master�б�֤��
     * @return String ����·�߱�ı��
     */
    public abstract java.lang.String getRouteListNumber();

    /**
     * ��ù���·�߱�ı�ţ�Ψһ��ʶ ����·�߱��а汾����ŵ�Ψһ���ڶ�Ӧ��Master�б�֤��
     *@param number String ����·�߱�ı��
     */

    public abstract void setRouteListNumber(String number);

    /**
     * ��ù���·�߱�����ơ�
     * @return String ����·�߱�����ơ�
     */
    public abstract java.lang.String getRouteListName();

    /**
     * ���ù���·�߱������
     * @param name String ����·�߱������
     */
    public abstract void setRouteListName(String name);

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     * @return String ����·�߱��Ӧ�Ĳ�ƷID.
     */
    public abstract String getProductMasterID();

    /**
     * ���ù���·�߱��Ӧ�Ĳ�ƷID.
     * @param productMasterID String ����·�߱��Ӧ�Ĳ�ƷID.
     */
    public abstract void setProductMasterID(String productMasterID);

    /**
     * ���ҵ���������
     * @return String TechnicsRouteListMaster
     */
    public String getBsoName()
    {
        return "consTechnicsRouteListMaster";
    }

    /**
     * ���ֵ����
     * @throws QMException
     * @return BaseValueIfc ֵ����
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        TechnicsRouteListMasterInfo info = new TechnicsRouteListMasterInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * ���½�ֵ����������á�
     * @param info BaseValueIfc ֵ����
     * @throws QMException
     * @see BaseValueInfo
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        TechnicsRouteListMasterInfo listinfo = (TechnicsRouteListMasterInfo)info;
        listinfo.setRouteListName(this.getRouteListName());
        listinfo.setRouteListNumber(this.getRouteListNumber());
        listinfo.setProductMasterID(this.getProductMasterID());
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
            System.out.println("enter TechnicsRouteListMasterEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        TechnicsRouteListMasterInfo listinfo = (TechnicsRouteListMasterInfo)info;
        this.setRouteListName(listinfo.getRouteListName());
        this.setRouteListNumber(listinfo.getRouteListNumber());
        this.setProductMasterID(listinfo.getProductMasterID());
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
            System.out.println("enter TechnicsRouteListMasterEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        TechnicsRouteListMasterInfo listinfo = (TechnicsRouteListMasterInfo)info;
        this.setRouteListName(listinfo.getRouteListName());
        this.setRouteListNumber(listinfo.getRouteListNumber());
        this.setProductMasterID(listinfo.getProductMasterID());
    }
}
