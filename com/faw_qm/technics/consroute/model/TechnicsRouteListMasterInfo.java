/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/20 ���� ͳһ������
 * CR2 2011/12/20 �촺Ӣ       ԭ��ͳһ����
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.cappclients.conscapproute.util.CappRouteRB;
import com.faw_qm.clients.rename.model.RenameIfc;
import com.faw_qm.enterprise.model.MasterInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;

/**
 * ����·�߱�����Ϣֵ����
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class TechnicsRouteListMasterInfo extends MasterInfo implements TechnicsRouteListMasterIfc, RenameIfc//CR1
{

    //·�߱���
    private String name;

    //·�߱���
    private String number;

    //��Ʒ����Ϣid��PastMasrer��
    private String productMasterID;
    private static final long serialVersionUID = 1L;
    //CR1
    /** ��Դ�ļ�·�� */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /**
     * ���캯��
     */
    public TechnicsRouteListMasterInfo()
    {

    }

    /**
     *�õ�ҵ������
     * @return String TechnicsRouteListMaster
     */
    public String getBsoName()
    {
        return "consTechnicsRouteListMaster";
    }

    /**
     * �õ�·�߱�ı��
     * @return String ·�߱�ı��
     */
    public java.lang.String getRouteListNumber()
    {
        return number;
    }

    /**
     * ����·�߱�ı��
     * @param number String ·�߱�ı��
     */
    public void setRouteListNumber(String number)
    {
        this.number = number;
    }

    /**
     * �õ�·�߱���
     * @return String ·�߱���
     */
    public java.lang.String getRouteListName()
    {
        return name;
    }

    /**
     * ����·�߱���
     * @param name String ·�߱���
     */
    public void setRouteListName(String name)
    {
        this.name = name;
    }

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     * @return String ����·�߱��Ӧ�Ĳ�ƷID.
     */
    public String getProductMasterID()
    {
        return this.productMasterID;
    }

    /**
     * ���ù���·�߱��Ӧ�Ĳ�ƷID.
     * @param productMasterID - �㲿��ID.
     */
    public void setProductMasterID(String productMasterID)
    {
        this.productMasterID = productMasterID;
    }

    //begin CR2
    /**
     * �����ʾ��ʶ
     */
    public String getIdentity()
    {
        return this.getRouteListNumber() + "(" + this.getRouteListName() + ")";
    }

    //end CR2

    //begin CR1
    public String getNameLabel()
    {
        return "·�߱�����";
    }

    public String getNameText()
    {
        return this.getRouteListName();
    }

    public String getNumberLabel()
    {
        return "·�߱���";
    }

    public String getNumberText()
    {
        return this.getRouteListNumber();
    }

    public boolean isRenameTwoAttribute()
    {
        return true;
    }

    public Object rename(String number, String name) throws QMException
    {
        TechnicsRouteListMasterIfc routelist = (TechnicsRouteListMasterIfc)RequestHelper.request("PersistService", "refreshInfo", new Class[]{BaseValueIfc.class}, new Object[]{this});
        //�ͻ��˵�����˷�������������
        if(routelist.getRouteListNumber().equals(number) && routelist.getRouteListName().equals(name))
        {
            // ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
            DisplayIdentity displayidentity = IdentityFactory.getDisplayIdentity(routelist);
            String s = displayidentity.getLocalizedMessage(null);
            Object aobj[] = {s};
            throw new QMException(RESOURCE, CappRouteRB.NO_CHANGE_MADE, aobj);
        }
        TechnicsRouteListMasterIfc masterObj;
        try
        {
            routelist.setRouteListNumber(number);
            routelist.setRouteListName(name);
            masterObj = (TechnicsRouteListMasterIfc)RequestHelper.request("consTechnicsRouteService", "rename", new Class[]{TechnicsRouteListMasterIfc.class}, new Object[]{routelist});
        }catch(Exception ex)
        {
            throw new QMException(ex);
        }
        return masterObj;
    }
    //end CR1

}
