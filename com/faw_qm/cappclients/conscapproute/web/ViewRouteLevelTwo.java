/** 
 * ���ɳ��� ViewRouteLevelTwo.java    1.0    2004/02/19
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

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title:�鿴����·�� </p> <p>Description: </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class ViewRouteLevelTwo
{

    private RouteWebHelper helper = new RouteWebHelper();
    /** ������Ա��� */

    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**
     * ���캯��
     */
    public ViewRouteLevelTwo()
    {}

    /**
     * ���ָ���㲿���Ļ�������
     * @param partID �㲿����BsoID
     * @return �ַ�������:���\����\�汾\��ͼ\ͼ��URL
     */
    public static String getPartHeader(String partID)
    {
        try
        {
            PersistService service1 = (PersistService)EJBServiceHelper.getService("PersistService");
            QMPartIfc info = (QMPartIfc)service1.refreshInfo(partID);
            String number = info.getPartNumber();
            String name = info.getPartName();
            String versionID = info.getVersionValue();
            String viewName = info.getViewName();
            String view = "";
            if(viewName != null)
                view = viewName;
            String myHeadInfo1 = number + " " + name + " " + versionID + " " + view;
            return myHeadInfo1;
        }catch(Exception sle)
        {
            System.out.println(sle.toString());
            return null;
        }
    }

    /**
     * ���ָ���㲿���Ķ���·��
     * @param partMasterID ָ���㲿��
     * @return ����·����Ϣ
     */
    public static Collection getPartLevelRoutes(String partMasterID)
    {
        Vector v = new Vector();
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            PersistService service1 = (PersistService)EJBServiceHelper.getService("PersistService");

            Vector v2 = (Vector)routeService.getPartLevelRoutes(partMasterID, RouteListLevelType.SENCONDROUTE.getDisplay());
            if(v2 != null && v2.size() > 0)
            {
                for(int i = 0;i < v2.size();i++)
                {
                    Object[] objs = (Object[])v2.elementAt(i);
                    TechnicsRouteListInfo list = (TechnicsRouteListInfo)objs[0];
                    TechnicsRouteIfc route = (TechnicsRouteIfc)objs[1];
                    Map map = (Map)objs[2];
                    ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)objs[3];
                    String number = list.getRouteListNumber();
                    String name = list.getRouteListName();
                    String level = list.getRouteListLevel();

                    String departmentName = "";
                    String department = list.getDepartmentName();
                    if(department != null)
                        departmentName = department;

                    String version = list.getVersionValue();
                    QMPartMasterIfc info = (QMPartMasterIfc)service1.refreshInfo(list.getProductMasterID());
                    String product = info.getPartNumber() + "_" + info.getPartName();
                    String state = "";
                    LifeCycleState st = list.getLifeCycleState();
                    //��ö����״̬
                    if(st != null)
                        state = st.getDisplay();

                    String creator = "";
                    String ic = list.getIterationCreator();
                    if(ic != null)
                        creator = RouteWebHelper.getUserNameByID(ic);

                    //��ô���ʱ��
                    String createTime = list.getCreateTime().toString();
                    //·��״̬
                    String routeState = link.getAdoptStatus();

                    String[] array = {link.getBsoID(), number, name, level, departmentName, version, product, routeState, state, creator, createTime};
                    Vector branchVector = getRouteBranches(map);
                    Object[] element = {array, branchVector};
                    v.addElement(element);
                }
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return v;
    }

    /**
     * �������·�߷�֧��·�ߴ�
     * @param map ·�߷�֧�ļ���
     * @return ����·�߷�֧��·�ߴ�
     * @throws QMException 
     */
    public static Vector getRouteBranches(Map map) throws QMException
    {
        Vector v = new Vector();
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
            Vector makeNodes = (Vector)nodes[0]; //����ڵ�
            RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1]; //װ��ڵ�

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

            String[] array = {String.valueOf(i + 1), makeStr, assemStr, isMainRoute};
            v.addElement(array);
        }
        return v;
    }

}