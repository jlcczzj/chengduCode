/**
 * ���ɳ��� MaterielBill.java    1.0    2004/04/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p> Title:���ɴ�����·�ߵ������嵥 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class MaterielBill
{
    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    private TechnicsRouteListInfo myRouteList = null;

    public MaterielBill()
    {}

    /**
     * ���ָ���㲿����·�ߴ�
     * @param partMasterID ָ���㲿��
     * @param level_Display ·�߼������ʾ��
     * @return �ַ������� obj[0]=����·�ߴ���obj[1]=װ��·�ߴ�
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
                                        makeStr = makeStr + "��" + node.getNodeDepartmentName();
                                }
                            }
                            if(assemNode != null)
                            {
                                assemStr = assemNode.getNodeDepartmentName();
                            }
                            if(makeStr == null || makeStr.equals(""))
                            {
                                makeStr = "����·�ߣ�";
                            }
                            if(assemStr == null || assemStr.equals(""))
                            {
                                assemStr = "����·�ߣ�";
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
     * �������ָ����Ʒ�����¹���·�߱�
     * @param productMasterID ��Ʒ������ϢbsoID
     * @throws QMException
     * @return ���¹���·�߱�
     */
    public TechnicsRouteListInfo getRouteList(String productMasterID) throws QMException
    {
        TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        myRouteList = routeService.getRouteListByProduct(productMasterID);
        if(verbose)
            System.out.println("������MaterialBill:getRouteList: " + myRouteList);
        return myRouteList;
    }

    /**
     * ���ָ���㲿��(��Ʒ)�ķּ������嵥
     * @param productID Ҫ���������嵥���㲿����·�߱�Ĳ�Ʒ��
     * @param attrNames �����嵥��Ҫ����������㲿������
     * @param isSelectLevelOne �Ƿ����һ��·��
     * @param isSelectLevelTwo �Ƿ��������·��
     * @return �ּ������嵥��ֱ�����ڏC�ͻ�������ʾ��
     */
    public Vector getMaterialBillClassific(String productID, String attrNames, String isSelectLevelOne, String isSelectLevelTwo)

    {
        if(verbose)
            System.out.println("MaterielBill:getMaterialBillClassific ���� productID = " + productID + "; attrNames = " + attrNames + "; isSelectLevelOne = " + isSelectLevelOne
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
                        System.out.println("���������ɶ���·��");

                    Object[] nameTitles = (Object[])v.firstElement();
                    Object[] nameTitles2 = new Object[nameTitles.length + 4];
                    nameTitles2[nameTitles2.length - 4] = "һ������·��";
                    nameTitles2[nameTitles2.length - 3] = "һ��װ��·��";
                    nameTitles2[nameTitles2.length - 2] = "��������·��";
                    nameTitles2[nameTitles2.length - 1] = "����װ��·��";
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
                                System.out.println("������һ������·��" + makeStr);
                                System.out.println("������һ��װ��·��" + asseStr);
                                System.out.println("��������������·��" + makeStr2);
                                System.out.println("����������װ��·��" + asseStr2);
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
                        System.out.println("����������һ��·��");
                    Object[] nameTitles = (Object[])v.firstElement();
                    Object[] nameTitles2 = new Object[nameTitles.length + 2];
                    nameTitles2[nameTitles2.length - 2] = "һ������·��";
                    nameTitles2[nameTitles2.length - 1] = "һ��װ��·��";
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
                            System.out.println("�����" + tempV[0]);
                        }
                        if(tempV[0] != null && !tempV[0].equals(""))
                        {
                            String partMasterID = RouteWebHelper.getPartMasterID(tempV[0].toString());
                            String[] routes = getProductStructRoutes(partMasterID, RouteListLevelType.FIRSTROUTE.getDisplay());
                            String makeStr = routes[0];
                            String asseStr = routes[1];
                            if(verbose)
                            {
                                System.out.println("������һ������·��" + makeStr);
                                System.out.println("������һ��װ��·��" + asseStr);
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
     * ���ָ���㲿��(��Ʒ)�������嵥ͳ�Ʊ�
     * @param productID Ҫ���������嵥���㲿����·�߱�Ĳ�Ʒ��
     * @param attrNames �����嵥��Ҫ����������㲿������
     * @param source �㲿����Դ
     * @param type �㲿������
     * @param isSelectLevelOne �Ƿ����һ��·��
     * @param isSelectLevelTwo �Ƿ��������·��
     * @return �����嵥ͳ�Ʊ�ֱ�����ڏC�ͻ�������ʾ��
     */
    public Vector getMaterialBillStatis(String productID, String attrNames, String source, String type, String isSelectLevelOne, String isSelectLevelTwo)
    {

        PartWebHelper psh = new PartWebHelper();
        try
        {
            getRouteList(RouteWebHelper.getPartMasterID(productID));
            Vector v = psh.getBOMList(productID, attrNames, source, type);
            ////�� �˴���ʼֱ�����������������д�����getMaterialBillClassific��������Ӧ������ȫ��ͬ
            Vector returnVector = new Vector();
            if(v != null)
            {
                if(Boolean.valueOf(isSelectLevelTwo).booleanValue())
                {
                    if(verbose)
                        System.out.println("���������ɶ���·��");

                    Object[] nameTitles = (Object[])v.firstElement();
                    Object[] nameTitles2 = new Object[nameTitles.length + 4];
                    nameTitles2[nameTitles2.length - 4] = "һ������·��";
                    nameTitles2[nameTitles2.length - 3] = "һ��װ��·��";
                    nameTitles2[nameTitles2.length - 2] = "��������·��";
                    nameTitles2[nameTitles2.length - 1] = "����װ��·��";
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
                                System.out.println("������һ������·��" + makeStr);
                                System.out.println("������һ��װ��·��" + asseStr);
                                System.out.println("��������������·��" + makeStr2);
                                System.out.println("����������װ��·��" + asseStr2);
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
                        System.out.println("����������һ��·��");
                    Object[] nameTitles = (Object[])v.firstElement();
                    Object[] nameTitles2 = new Object[nameTitles.length + 2];
                    nameTitles2[nameTitles2.length - 2] = "һ������·��";
                    nameTitles2[nameTitles2.length - 1] = "һ��װ��·��";
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
                            System.out.println("�����" + tempV[0]);
                        }
                        if(tempV[0] != null && !tempV[0].equals(""))
                        {
                            String partMasterID = RouteWebHelper.getPartMasterID(tempV[0].toString());
                            String[] routes = getProductStructRoutes(partMasterID, RouteListLevelType.FIRSTROUTE.getDisplay());
                            String makeStr = routes[0];
                            String asseStr = routes[1];
                            if(verbose)
                            {
                                System.out.println("������һ������·��" + makeStr);
                                System.out.println("������һ��װ��·��" + asseStr);
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
