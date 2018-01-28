/** 
 * ���ɳ��� ViewRoute.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.web;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title:�鿴����·�� </p> <p>Description: </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class ViewRoute
{

    /** ·�ߴ����� */
    private static Vector nodeIndexVector = new Vector();

    /**
     * ���캯��
     */
    public ViewRoute()
    {}

    /**
     * �õ��ݿͻ���ͷ���ı�Ҫ��ʾ��Ϣ
     * @param bsoID ·�߱����㲿���Ĺ�������ListRoutePartLink��BsoID
     * @return ���,����,����,��λ,�汾,���ڲ�Ʒ
     */
    public static String[] getRouteListHead(String bsoID)
    {
        String id = bsoID.trim();
        String number = "";
        String name = "";
        String level = "";
        String department = "";
        ListRoutePartLinkIfc link;

        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            link = (ListRoutePartLinkIfc)pService.refreshInfo(id);
            id = link.getRouteListID();
            TechnicsRouteListInfo list = (TechnicsRouteListInfo)pService.refreshInfo(id);
            number = list.getRouteListNumber();
            name = list.getRouteListName();
            level = list.getRouteListLevel();
            String partNum = link.getPartMasterInfo().getPartNumber() + "_" + link.getPartMasterInfo().getPartName();
            String dep = list.getDepartmentName();
            if(dep != null)
                department = dep;

            TechnicsRouteIfc route = (TechnicsRouteIfc)pService.refreshInfo(link.getRouteID());
            String routeDescri = "";
            String descri = route.getRouteDescription();
            if(descri != null)
                routeDescri = descri;
            //��������
            String[] myVersionArray1 = {number, name, level, department, descri, partNum};
            return myVersionArray1;

        }catch(Exception sle)
        {
            System.out.println(sle.toString());
            return null;
        }
    }

    /**
     * ���ָ��·�ߵ�·�߷�֧��Ϣ
     * @param linkBsoID ·�߱����㲿���Ĺ�������ListRoutePartLink��BsoID
     * @return ���ؼ��ϵ�Ԫ��Ϊ���飬ÿ�������Ԫ������Ϊ��š�����·�ߴ���װ��·�ߴ�����/����Ҫ·��
     */
    public static Collection getRoutes(String linkBsoID)
    {
        nodeIndexVector.clear();
        Vector v = new Vector();
        PersistService pService = null;
        try
        {
            pService = (PersistService)EJBServiceHelper.getService("PersistService");
            ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)pService.refreshInfo(linkBsoID);
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Map map = routeService.getRouteBranchs(link.getRouteID());
            Object[] branchs = RouteHelper.sortedInfos(map.keySet()).toArray();
            for(int i = 0;i < branchs.length;i++)
            {
            	
                TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo)branchs[i];
                String isMainRoute = "��";
                if(branchinfo.getMainRoute())
                    isMainRoute = "��";
                else
                    isMainRoute = "��";

                String makeStr = "";
                String assemStr = "";
                Object[] nodes = (Object[])map.get(branchinfo);
                Vector makeNodes = (Vector)nodes[0]; //����ڵ㼯��
                RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1]; //װ��ڵ�

                //{{{Ϊ���ɽڵ����׼��
                Vector nodeVector = new Vector();
                if(makeNodes != null && makeNodes.size() > 0)
                    nodeVector.addAll(makeNodes);
                if(asseNode != null)
                    nodeVector.addElement(asseNode);
                Object[] indexObjs = new Object[2];
                indexObjs[0] = String.valueOf(i + 1);
                indexObjs[1] = nodeVector;
                nodeIndexVector.addElement(indexObjs);
                //}}}}

                //Vector makeNodeVector = new Vector();
                if(makeNodes != null && makeNodes.size() > 0)
                {
                    //System.out.println(">>>>>>>>>>>>>>>>>  ��� ��֧"+branchinfo.getBsoID()+"������ڵ� ������"+makeNodes.size());
                    for(int m = 0;m < makeNodes.size();m++)
                    {
                        RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
                        if(makeStr == "")
                            makeStr = makeStr + node.getNodeDepartmentName();
                        else
                            makeStr = makeStr + "��" + node.getNodeDepartmentName();
                        //makeNodeVector.addElement(node.getNodeDepartmentName());
                    }
                }
                if(asseNode != null)
                {
                    assemStr = asseNode.getNodeDepartmentName();
                }
                if(makeStr == null || makeStr.equals(""))
                {
                    makeStr = "";
                }
                if(assemStr == null || assemStr.equals(""))
                {
                    assemStr = "";
                }

                Object[] array = {String.valueOf(i + 1), makeStr, assemStr, isMainRoute};
                v.addElement(array);
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return v;
    }

    /**
     * ���·�������нڵ����Ϣ
     * @return ���ؼ��ϵ�Ԫ��Ϊ����returnElement��һ��·�ߴ���Ӧһ��returnElement�� ����returnElement[0] = ·�ߴ���ţ� returnElement[1] = ���ϣ�Ԫ��Ϊ��·�ߴ������нڵ��������Ϣ��
     */
    public static Vector getNodeDepartment()
    {
        Vector v = new Vector();
        try
        {
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            if(nodeIndexVector.size() > 0)
            {
                for(int k = 0;k < nodeIndexVector.size();k++)
                {
                    Object[] indexObj = (Object[])nodeIndexVector.elementAt(k); //��ǰ·�ߴ�
                    Vector nodeVector = (Vector)indexObj[1]; //��ǰ·�ߴ��е����нڵ�ļ���
                    Vector tempV = new Vector();
                    if(nodeVector.size() > 0)
                    {
                        for(int i = 0;i < nodeVector.size();i++)
                        {
                            RouteNodeInfo node = (RouteNodeInfo)nodeVector.elementAt(i);
                            String code = node.getNodeDepartmentName();
                            String departemntName = pService.refreshInfo(node.getNodeDepartment()).toString();
                            String type = node.getRouteType();
                            String descri = node.getNodeDescription();
                            //�ڵ���Ϣ�����루��ƣ�����λ�������˵��
                            String[] array = {code, departemntName, type, descri};
                            tempV.add(array);
                        }
                    }
                    Object[] returnElement = new Object[2];
                    returnElement[0] = indexObj[0]; //·�ߴ����
                    returnElement[1] = tempV; //��ǰ·�ߴ������нڵ����Ϣ
                    v.add(returnElement);
                }
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return v;
    }

}