/**
 * ���ɳ��� ViewRouteListPartsUtil.java    1.0    2005/03/09
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.cappclients.conscapproute.web;

import java.util.Collection;
import java.util.Vector;

import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.framework.remote.*;

/**
 * <p> Title:ͨ��·�ߵ�λ��ѯ�㲿�� </p> <p> Description:�����ṩ�ķ���Ϊ�C�ͻ�����ʹ�� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author skybird
 * @version 1.0
 */

public class CompositiveRouteUtil
{
    private static PersistService pService;

    private static TechnicsRouteService trsService;

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**
     * �õ��ݿͻ���ͷ���ı�Ҫ��ʾ��Ϣ
     * @param bsoID ·�߱��BsoID
     * @return ·�߱��BsoID,���,����,����,��λ,�汾,���ڲ�Ʒ
     */
    public static Vector getDepartAndRoute(String arg)
    {
        Vector result = new Vector();
        Vector tmpresult = new Vector();
        Vector temparg = new Vector();
        temparg.addElement(arg);
        try
        {
            pService = (PersistService)EJBServiceHelper.getService("PersistService");
            trsService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            for(int i = 0;i < temparg.size();i++)
            {
                String tmp = (String)temparg.elementAt(i);
                String partID = (String)tmp;
                if(verbose)
                {
                    System.out.println("compositiveRouteUtil:51:partID:" + partID);
                }
                tmpresult = (Vector)trsService.getPartRoutes(partID);
                for(int j = 0;j < tmpresult.size();j++)
                {
                    result.addElement(tmpresult.elementAt(j));
                }
            }
            return result;
        }catch(Exception sle)
        {
            System.out.println(sle.toString());
            sle.printStackTrace();
            return result;
        }
    }

    /**
     * ͨ��·�ߵ�λ��ѯ�㲿��
     * @param parts
     * @return Collection
     */
    public static Collection getParts(String parts)
    {
        //���صĽ��
        if(verbose)
        {
            System.out.println("the partid:" + parts);
        }
        Vector result = new Vector();
        Vector c = new Vector();
        String[] tmp = null;
        c = (Vector)getDepartAndRoute(parts);
        if(c != null && c.size() != 0)
        {
            if(verbose)
            {
                System.out.println("87");
            }
            String partNum = new String();
            String partName = new String();
            String routeStr = new String();
            String routeState = new String();
            String iconURL = new String();
            String linkId = new String();
            //״̬
            String state = new String();
            //�������
            String parentPartNum = new String();
            //��������
            String parentPartName = new String();
            for(int i = 0;i < c.size();i++)
            {
                try
                {
                    if(verbose)
                    {
                        System.out.println("97");
                    }
                    tmp = (String[])c.elementAt(i);
                    pService = (PersistService)EJBServiceHelper.getService("PersistService");
                    QMPartMasterIfc part = (QMPartMasterIfc)pService.refreshInfo(tmp[0]);
                    partNum = part.getPartNumber();
                    partName = part.getPartName();
                    linkId = tmp[3];
                    state = tmp[4];
                    parentPartNum = tmp[5];
                    if(parentPartNum != null && !parentPartNum.equals(""))
                    {
                        QMPartMasterIfc part1 = (QMPartMasterIfc)pService.refreshInfo(parentPartNum);
                        parentPartName = part1.getPartName();
                    }
                    if(verbose)
                    {
                        System.out.println("linkId:" + linkId);
                    }
                    routeState = "";
                    iconURL = "";
                    routeState = tmp[2]; //·��״̬
                    if(tmp[1] != null)
                    {
                        routeStr = ViewRouteListPartsUtil.getRouteBranches(tmp[1]);
                        iconURL = "images/route.gif";
                    }else
                    {
                        routeStr = "";
                        iconURL = "images/route_emptyRoute.gif";
                    }
                    String[] array = {linkId, partNum, partName, routeStr, routeState, iconURL, state, parentPartNum, parentPartName};
                    result.addElement(array);

                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            return result;
        }else
        {
            if(verbose)
            {
                System.out.println("null" + result.size());
            }
            return result;
        }
    }
}
