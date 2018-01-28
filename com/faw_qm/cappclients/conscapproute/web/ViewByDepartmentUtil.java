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
import com.faw_qm.framework.exceptions.*;
import java.util.*;

/**
 * <p> Title:ͨ��·�ߵ�λ��ѯ�㲿�� </p> <p> Description:�����ṩ�ķ���Ϊ�C�ͻ�����ʹ�� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author skybird
 * @version 1.0
 */

public class ViewByDepartmentUtil
{
    private static PersistService pService;

    private static TechnicsRouteService trsService;

    /**
     * �õ��ݿͻ���ͷ���ı�Ҫ��ʾ��Ϣ
     * @param bsoID ·�߱��BsoID
     * @return ·�߱��BsoID,���,����,����,��λ,�汾,���ڲ�Ʒ
     */
    public static Vector getDepartAndRoute(String mDepartment, String cDepartment)
    {
        Vector result = new Vector();
        try
        {
            pService = (PersistService)EJBServiceHelper.getService("PersistService");
            trsService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            if(mDepartment == null || mDepartment.equals("") && (cDepartment != null && !cDepartment.equals("")))
            {
                result = (Vector)trsService.getAllPartsRoutesC(cDepartment);
            }
            if(cDepartment == null || cDepartment.equals("") && (mDepartment != null && !mDepartment.equals("")))
            {
                result = (Vector)trsService.getAllPartsRoutesM(mDepartment);
            }
            if((mDepartment != null && !mDepartment.equals("")) && (cDepartment != null && !cDepartment.equals("")))
            {
                result = (Vector)trsService.getAllPartsRoutesA(mDepartment, cDepartment);
            }
            return result;
        }catch(Exception sle)
        {
            return result;
        }
    }

    /**
     * �õ��㲿��
     * @param mDepartment
     * @param cDepartment
     * @return
     */
    public static Collection getParts(String mDepartment, String cDepartment)
    {
        //���صĽ��
        Vector result = new Vector();
        Vector c = new Vector();
        String[] tmp = null;
        c = (Vector)getDepartAndRoute(mDepartment, cDepartment);
        if(c != null && c.size() != 0)
        {
            String partNum = new String();
            String partName = new String();
            String routeStr = new String();
            String routeState = new String();
            String iconURL = new String();
            String linkId = new String();
            for(int i = 0;i < c.size();i++)
            {
                try
                {
                    tmp = (String[])c.elementAt(i);
                    pService = (PersistService)EJBServiceHelper.getService("PersistService");
                    QMPartMasterIfc part = (QMPartMasterIfc)pService.refreshInfo(tmp[0]);
                    partNum = part.getPartNumber();
                    partName = part.getPartName();
                    linkId = tmp[3];
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
                    String[] array = {linkId, partNum, partName, routeStr, routeState, iconURL};
                    result.addElement(array);

                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            return result;
        }else
        {
            return result;
        }
    }

    /**
     * �õ��㲿��
     * @param mDepartment
     * @param cDepartment
     * @param productID
     * @param source
     * @param type
     * @return
     * @throws QMException
     */
    public static Collection getParts(String mDepartment, String cDepartment, String productID, String source, String type) throws QMException
    {
        TechnicsRouteService trsService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        return trsService.getAllPartsRoutes(mDepartment, cDepartment, productID, source, type);
    }

    /**
     * �ۺ�·��
     * @param mDepartment
     * @param cDepartment
     * @param productIDs
     * @param partIDs
     * @return
     * @throws QMException
     */
    public static Collection compositiveRoutes(String mDepartment, String cDepartment, String productIDs, String partIDs) throws QMException
    {
        try
        {
            long before = System.currentTimeMillis();
            TechnicsRouteService trsService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Vector products = new Vector();
            Vector parts = new Vector();
            if(productIDs != null)
            {
                StringTokenizer ptokenizer = new StringTokenizer(productIDs, ";");
                while(ptokenizer.hasMoreTokens())
                {
                    String product = ptokenizer.nextToken();
                    // System.out.println("����Ϊ==========" + product);
                    if(!product.equals("null"))
                        products.add(product);
                }
            }
            if(partIDs != null)
            {
                StringTokenizer stokenizer = new StringTokenizer(partIDs, ";");
                while(stokenizer.hasMoreTokens())
                {
                    String part = stokenizer.nextToken();
                    // System.out.println("����Ϊ==========" + part);
                    if(!part.equals("null"))
                        parts.add(part);
                }
            }
            Collection result = trsService.getColligationRoutes(mDepartment, cDepartment, parts, products);
            long after = System.currentTimeMillis();
            // System.out.println("helper��ʱ====="+(after - before));
            return result;

        }catch(QMException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

}
