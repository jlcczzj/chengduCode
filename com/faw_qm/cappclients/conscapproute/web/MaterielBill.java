/**
 * 生成程序 MaterielBill.java    1.0    2004/04/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.web;

import java.util.Map;

import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.part.util.PartWebHelper;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p> Title:生成带工艺路线的物料清单 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class MaterielBill
{
    /** 代码测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    private TechnicsRouteListInfo myRouteList = null;

    public MaterielBill()
    {}

    /**
     * 获得指定零部件的路线串
     * @param partMasterID 指定零部件
     * @param level_Display 路线级别的显示名
     * @return 字符串数组 obj[0]=制造路线串；obj[1]=装配路线串
     */
    private String[] getProductStructRoutes(String partMasterID, String level_Display)
    {
        String[] array = new String[2];
        array[0] = "";
        array[1] = "";
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Object[] objs;
            if(myRouteList != null)
            {
                objs = routeService.getMaterialBillRoutes(myRouteList, partMasterID, level_Display);
            }else
                objs = routeService.getProductStructRoutes(partMasterID, level_Display);

            if(objs != null)
            {
                TechnicsRouteListIfc routeList = (TechnicsRouteListIfc)objs[0];
                TechnicsRouteIfc route = (TechnicsRouteIfc)objs[1];
                Map branchesMap = (Map)objs[2];
                if(branchesMap != null && branchesMap.size() > 0)
                {
                    Object[] branchnodes = branchesMap.values().toArray();
                    if(branchnodes != null)
                    {
                        String mString = "";
                        String aString = "";
                        for(int i = 0;i < branchnodes.length;i++)
                        {
                            String makeStr = "";
                            String assemStr = "";
                            Object[] nodes = (Object[])branchnodes[i];
                            Vector makeNodes = (Vector)nodes[0];
                            RouteNodeIfc assemNode = (RouteNodeIfc)nodes[1];
                            if(makeNodes != null && makeNodes.size() > 0)
                            {
                                for(int m = 0;m < makeNodes.size();m++)
                                {
                                    RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
                                    if(makeStr == "")
                                        makeStr = makeStr + node.getNodeDepartmentName();
                                    else
                                        makeStr = makeStr + "→" + node.getNodeDepartmentName();
                                }
                            }
                            if(assemNode != null)
                            {
                                assemStr = assemNode.getNodeDepartmentName();
                            }
                            if(makeStr == null || makeStr.equals(""))
                            {
                                makeStr = "（无路线）";
                            }
                            if(assemStr == null || assemStr.equals(""))
                            {
                                assemStr = "（无路线）";
                            }

                            if(mString == "")
                                mString = mString + makeStr;
                            else
                                mString = mString + ";" + makeStr;

                            if(aString == "")
                                aString = aString + assemStr;
                            else
                                aString = aString + ";" + assemStr;
                        }
                        array[0] = mString;
                        array[1] = aString;
                    }
                }
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return array;
    }

    /**
     * 获得用于指定产品的最新工艺路线表
     * @param productMasterID 产品的主信息bsoID
     * @throws QMException
     * @return 最新工艺路线表
     */
    public TechnicsRouteListInfo getRouteList(String productMasterID) throws QMException
    {
        TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        myRouteList = routeService.getRouteListByProduct(productMasterID);
        if(verbose)
            System.out.println("刘明：MaterialBill:getRouteList: " + myRouteList);
        return myRouteList;
    }

    /**
     * 获得指定零部件(产品)的分级物料清单
     * @param productID 要生成物料清单的零部件（路线表的产品）
     * @param attrNames 物料清单中要输出的所有零部件属性
     * @param isSelectLevelOne 是否输出一级路线
     * @param isSelectLevelTwo 是否输出二级路线
     * @return 分级物料清单（直接用于廋客户界面显示）
     */
    public Vector getMaterialBillClassific(String productID, String attrNames, String isSelectLevelOne, String isSelectLevelTwo)

    {
        if(verbose)
            System.out.println("MaterielBill:getMaterialBillClassific 参数 productID = " + productID + "; attrNames = " + attrNames + "; isSelectLevelOne = " + isSelectLevelOne
                    + "; isSelectLevelTwo = " + isSelectLevelTwo);

        PartWebHelper psh = new PartWebHelper();
        try
        {
            getRouteList(RouteWebHelper.getPartMasterID(productID));
            Vector v = psh.getMaterialList(productID, attrNames);
            Vector returnVector = new Vector();
            if(v != null)
            {
                if(Boolean.valueOf(isSelectLevelTwo).booleanValue())
                {
                    if(verbose)
                        System.out.println("刘明：生成二级路线");

                    Object[] nameTitles = (Object[])v.firstElement();
                    Object[] nameTitles2 = new Object[nameTitles.length + 4];
                    nameTitles2[nameTitles2.length - 4] = "一级制造路线";
                    nameTitles2[nameTitles2.length - 3] = "一级装配路线";
                    nameTitles2[nameTitles2.length - 2] = "二级制造路线";
                    nameTitles2[nameTitles2.length - 1] = "二级装配路线";
                    for(int j = 0;j < nameTitles.length;j++)
                        nameTitles2[j] = nameTitles[j];
                    returnVector.add(nameTitles2);

                    for(int i = 1;i < v.size();i++)
                    {
                        Object[] tempV = (Object[])v.elementAt(i);
                        if(tempV[0] != null && !tempV[0].equals(""))
                        {
                            String partMasterID = RouteWebHelper.getPartMasterID(tempV[0].toString());
                            String[] routes = getProductStructRoutes(partMasterID, RouteListLevelType.FIRSTROUTE.getDisplay());
                            String makeStr = routes[0];
                            String asseStr = routes[1];
                            String[] routes2 = getProductStructRoutes(partMasterID, RouteListLevelType.SENCONDROUTE.getDisplay());
                            String makeStr2 = routes2[0];
                            String asseStr2 = routes2[1];
                            if(verbose)
                            {
                                System.out.println("刘明：一级制造路线" + makeStr);
                                System.out.println("刘明：一级装配路线" + asseStr);
                                System.out.println("刘明：二级制造路线" + makeStr2);
                                System.out.println("刘明：二级装配路线" + asseStr2);
                            }
                            Object[] tempV2 = new Object[tempV.length + 4];
                            tempV2[tempV2.length - 4] = makeStr;
                            tempV2[tempV2.length - 3] = asseStr;
                            tempV2[tempV2.length - 2] = makeStr2;
                            tempV2[tempV2.length - 1] = asseStr2;
                            for(int k = 0;k < tempV.length;k++)
                                tempV2[k] = tempV[k];

                            returnVector.add(tempV2);
                        }
                    }
                }else if(Boolean.valueOf(isSelectLevelOne).booleanValue())
                {
                    if(verbose)
                        System.out.println("刘明：生成一级路线");
                    Object[] nameTitles = (Object[])v.firstElement();
                    Object[] nameTitles2 = new Object[nameTitles.length + 2];
                    nameTitles2[nameTitles2.length - 2] = "一级制造路线";
                    nameTitles2[nameTitles2.length - 1] = "一级装配路线";
                    for(int j = 0;j < nameTitles.length;j++)
                        nameTitles2[j] = nameTitles[j];
                    returnVector.add(nameTitles2);

                    for(int i = 1;i < v.size();i++)
                    {
                        Object[] tempV = (Object[])v.elementAt(i);
                        if(verbose)
                        {
                            for(int j = 0;j < tempV.length;j++)
                                System.out.print(tempV[j]);
                            System.out.println("零件：" + tempV[0]);
                        }
                        if(tempV[0] != null && !tempV[0].equals(""))
                        {
                            String partMasterID = RouteWebHelper.getPartMasterID(tempV[0].toString());
                            String[] routes = getProductStructRoutes(partMasterID, RouteListLevelType.FIRSTROUTE.getDisplay());
                            String makeStr = routes[0];
                            String asseStr = routes[1];
                            if(verbose)
                            {
                                System.out.println("刘明：一级制造路线" + makeStr);
                                System.out.println("刘明：一级装配路线" + asseStr);
                            }
                            Object[] tempV2 = new Object[tempV.length + 2];
                            tempV2[tempV2.length - 2] = makeStr;
                            tempV2[tempV2.length - 1] = asseStr;
                            for(int k = 0;k < tempV.length;k++)
                                tempV2[k] = tempV[k];

                            returnVector.add(tempV2);
                        }
                    }
                }

            }
            return returnVector;
        }catch(QMException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 获得指定零部件(产品)的物料清单统计表
     * @param productID 要生成物料清单的零部件（路线表的产品）
     * @param attrNames 物料清单中要输出的所有零部件属性
     * @param source 零部件来源
     * @param type 零部件类型
     * @param isSelectLevelOne 是否输出一级路线
     * @param isSelectLevelTwo 是否输出二级路线
     * @return 物料清单统计表（直接用于廋客户界面显示）
     */
    public Vector getMaterialBillStatis(String productID, String attrNames, String source, String type, String isSelectLevelOne, String isSelectLevelTwo)
    {

        PartWebHelper psh = new PartWebHelper();
        try
        {
            getRouteList(RouteWebHelper.getPartMasterID(productID));
            Vector v = psh.getBOMList(productID, attrNames, source, type);
            ////从 此处开始直到本方法结束，所有代码与getMaterialBillClassific方法的相应部分完全相同
            Vector returnVector = new Vector();
            if(v != null)
            {
                if(Boolean.valueOf(isSelectLevelTwo).booleanValue())
                {
                    if(verbose)
                        System.out.println("刘明：生成二级路线");

                    Object[] nameTitles = (Object[])v.firstElement();
                    Object[] nameTitles2 = new Object[nameTitles.length + 4];
                    nameTitles2[nameTitles2.length - 4] = "一级制造路线";
                    nameTitles2[nameTitles2.length - 3] = "一级装配路线";
                    nameTitles2[nameTitles2.length - 2] = "二级制造路线";
                    nameTitles2[nameTitles2.length - 1] = "二级装配路线";
                    for(int j = 0;j < nameTitles.length;j++)
                        nameTitles2[j] = nameTitles[j];
                    returnVector.add(nameTitles2);

                    for(int i = 1;i < v.size();i++)
                    {
                        Object[] tempV = (Object[])v.elementAt(i);
                        if(tempV[0] != null && !tempV[0].equals(""))
                        {
                            String partMasterID = RouteWebHelper.getPartMasterID(tempV[0].toString());
                            String[] routes = getProductStructRoutes(partMasterID, RouteListLevelType.FIRSTROUTE.getDisplay());
                            String makeStr = routes[0];
                            String asseStr = routes[1];
                            String[] routes2 = getProductStructRoutes(partMasterID, RouteListLevelType.SENCONDROUTE.getDisplay());
                            String makeStr2 = routes2[0];
                            String asseStr2 = routes2[1];
                            if(verbose)
                            {
                                System.out.println("刘明：一级制造路线" + makeStr);
                                System.out.println("刘明：一级装配路线" + asseStr);
                                System.out.println("刘明：二级制造路线" + makeStr2);
                                System.out.println("刘明：二级装配路线" + asseStr2);
                            }
                            Object[] tempV2 = new Object[tempV.length + 4];
                            tempV2[tempV2.length - 4] = makeStr;
                            tempV2[tempV2.length - 3] = asseStr;
                            tempV2[tempV2.length - 2] = makeStr2;
                            tempV2[tempV2.length - 1] = asseStr2;
                            for(int k = 0;k < tempV.length;k++)
                                tempV2[k] = tempV[k];

                            returnVector.add(tempV2);
                        }
                    }
                }else if(Boolean.valueOf(isSelectLevelOne).booleanValue())
                {
                    if(verbose)
                        System.out.println("刘明：生成一级路线");
                    Object[] nameTitles = (Object[])v.firstElement();
                    Object[] nameTitles2 = new Object[nameTitles.length + 2];
                    nameTitles2[nameTitles2.length - 2] = "一级制造路线";
                    nameTitles2[nameTitles2.length - 1] = "一级装配路线";
                    for(int j = 0;j < nameTitles.length;j++)
                        nameTitles2[j] = nameTitles[j];
                    returnVector.add(nameTitles2);

                    for(int i = 1;i < v.size();i++)
                    {
                        Object[] tempV = (Object[])v.elementAt(i);
                        if(verbose)
                        {
                            for(int j = 0;j < tempV.length;j++)
                                System.out.print(tempV[j]);
                            System.out.println("零件：" + tempV[0]);
                        }
                        if(tempV[0] != null && !tempV[0].equals(""))
                        {
                            String partMasterID = RouteWebHelper.getPartMasterID(tempV[0].toString());
                            String[] routes = getProductStructRoutes(partMasterID, RouteListLevelType.FIRSTROUTE.getDisplay());
                            String makeStr = routes[0];
                            String asseStr = routes[1];
                            if(verbose)
                            {
                                System.out.println("刘明：一级制造路线" + makeStr);
                                System.out.println("刘明：一级装配路线" + asseStr);
                            }
                            Object[] tempV2 = new Object[tempV.length + 2];
                            tempV2[tempV2.length - 2] = makeStr;
                            tempV2[tempV2.length - 1] = asseStr;
                            for(int k = 0;k < tempV.length;k++)
                                tempV2[k] = tempV[k];

                            returnVector.add(tempV2);
                        }
                    }
                }

            }
            return returnVector;
        }catch(QMException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

}
