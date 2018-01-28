/**
 * 生成程序 ViewRouteListPartsUtil.java    1.0    2005/03/09
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * <p> Title:通过路线单位查询零部件 </p> <p> Description:本类提供的方法为廋客户界面使用 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author skybird
 * @version 1.0
 */

public class ViewByDepartmentUtil
{
    private static PersistService pService;

    private static TechnicsRouteService trsService;

    /**
     * 得到瘦客户端头部的必要显示信息
     * @param bsoID 路线表的BsoID
     * @return 路线表的BsoID,编号,名称,级别,单位,版本,用于产品
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
     * 得到零部件
     * @param mDepartment
     * @param cDepartment
     * @return
     */
    public static Collection getParts(String mDepartment, String cDepartment)
    {
        //返回的结果
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
                    routeState = tmp[2]; //路线状态
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
     * 得到零部件
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
     * 综合路线
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
                    // System.out.println("附件为==========" + product);
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
                    // System.out.println("附件为==========" + part);
                    if(!part.equals("null"))
                        parts.add(part);
                }
            }
            Collection result = trsService.getColligationRoutes(mDepartment, cDepartment, parts, products);
            long after = System.currentTimeMillis();
            // System.out.println("helper用时====="+(after - before));
            return result;

        }catch(QMException e)
        {
            e.printStackTrace();
            throw e;
        }
    }

}
