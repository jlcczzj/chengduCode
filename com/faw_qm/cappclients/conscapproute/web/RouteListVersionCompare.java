/**
 * ���ɳ��� RouteListVersionCompare.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.web;

import java.util.Map;

import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.capp.util.CappServiceWebHelper;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title:·�߱�İ汾�Ƚ� </p> <p>Description: ·�߱�İ汾�Ƚ�</p> <p>Copyright: Copyright (c) 2006</p> <p>Company:һ������ </p>
 * @author ����
 * @version 1.0 (����һ)20060703 �����޸� �޸�ԭ��:�����Ż�����·�߱�İ汾�Ƚ�,ͬʱ�޸��˰汾�Ƚϵ�jsp
 */
public class RouteListVersionCompare
{
    /** ��ѡ������ID */
    private String[] selectBsoID;

    /** �־û����� */
    private PersistService service;

    /** ·�߱���� */
    private TechnicsRouteListIfc myObjectInfo;

    /** �ݿͻ���ͷ���ı�Ҫ��ʾ��Ϣ(·�߱��š����ơ��汾�š�ͼ��) */
    public String[] myHeadInfo;

    public boolean flag = false;

    /** ·�߱�İ汾�� */
    public String technicsVersionID;

    /** �汾�ż��� */
    public Vector myVersionIDCollection = new Vector();

    /** ·�߱���󼯺� */
    public Vector myTechnicsCollection = new Vector();

    /***/
    private RouteWebHelper helper = new RouteWebHelper();

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");
    public Map map = null;

    /**
     * ���캯��
     */
    public RouteListVersionCompare()
    {}

    public void clearMap()
    {
        map = null;
    }

    /**
     * ����ѡ������ж����BsoID
     * @param sBsoID �����BsoID������
     */
    public void setSelectBsoID(String[] sBsoID)
    {
        if(verbose)
            System.out.println("capproute.web.TechnicsVersionCompareUtil.setSelectBsoID() begin...");
        //��bsoID�����������к󷵻�
        selectBsoID = RouteWebHelper.sortedIterate(sBsoID);
        flag = true;
        if(verbose)
            System.out.println("cappclients.capp.web.TechnicsVersionCompareUtil.setSelectBsoID() end...return is void");
    }

    /**
     * �õ��ݿͻ���ͷ���ı�Ҫ��ʾ��Ϣ(·�߱��š����ơ��汾�š�ͼ��)
     * @param technicsBsoID �����BsoID
     */
    public String[] getHeadInfo(String technicsBsoID)
    {
        if(verbose)
            System.out.println("cappclients.capp.web.TechnicsVersionCompareUtil.getHeadInfo() begin...");
        try
        {

            service = (PersistService)EJBServiceHelper.getService("PersistService");
            TechnicsRouteListIfc technicsInfo = (TechnicsRouteListIfc)service.refreshInfo(technicsBsoID);

            String technicsNumber = technicsInfo.getRouteListNumber();
            String technicsName = technicsInfo.getRouteListName();
            String versionID = technicsInfo.getVersionID();
            String headIconUrl = helper.getStandardIconName(technicsInfo);
            String[] myHeadInfo1 = {technicsNumber, technicsName, versionID, headIconUrl};
            if(verbose)
                System.out.println("cappclients.capp.web.TechnicsVersionCompareUtil.getHeadInfo() end...return: " + myHeadInfo1.length);
            return myHeadInfo1;
        }catch(Exception sle)
        {
            System.out.println(sle.toString());
            return null;
        }
    }

    /**
     * �õ�ѡ�еĶ���ļ��ϣ�ҳ����ѡ�е���BsoIDͨ���������ת�ɶ���
     * @return Vector ѡ�еĶ���ļ���
     */
    public Vector getSelectObject()
    {
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getSelectObject() begin...");
        if(flag)
        {
            for(int i = 0;i < selectBsoID.length;i++)
            {
                try
                {
                    service = (PersistService)EJBServiceHelper.getService("PersistService");
                    myObjectInfo = (TechnicsRouteListIfc)service.refreshInfo(selectBsoID[i].trim());
                }catch(Exception sle)
                {
                    System.out.println(sle.toString());
                }

                technicsVersionID = myObjectInfo.getVersionID();
                myVersionIDCollection.add(technicsVersionID);
                myTechnicsCollection.add(myObjectInfo);
            }
            if(verbose)
                System.out.println("cappclients.capp.web.RouteListVersionCompare.getSelectObject() end...return: " + myTechnicsCollection);
            return myTechnicsCollection;
        }else
            return null;
    }

    /**
     * �õ�ѡ�еĶ���İ汾�ŵļ���
     * @return Vector
     */
    public Vector getMyVersionIDCollection()
    {
        return myVersionIDCollection;
    }

    /**
     * ���ѡ�еĶ���Ļ������Եļ��ϣ�ҳ����ѡ�е���BsoIDͨ���������ת�ɶ���
     * @return Vector ��Ҫ�����Եļ��ϣ����ϵ�Ԫ�����������
     */
    public Vector getAttVector()
    {
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getAttVector() begin...");
        try
        {
            Vector av = new Vector();
            //Vector tav=new Vector();
            String[][] obj = new String[myTechnicsCollection.size() + 1][9];

            obj[0][0] = "���";
            obj[0][1] = "����";
            obj[0][2] = "����";
            obj[0][3] = "��λ";
            obj[0][4] = "���ڲ�Ʒ";
            obj[0][5] = "��������״̬";
            obj[0][6] = "������";
            obj[0][7] = "���ϼ�";
            obj[0][8] = "˵��";

            for(int j = 1;j < myTechnicsCollection.size() + 1;j++)
            {
                obj[j][0] = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getRouteListNumber();
                obj[j][1] = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getRouteListName();
                obj[j][2] = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getRouteListLevel();
                //��λ
                String department = ((TechnicsRouteListInfo)(myTechnicsCollection.get(j - 1))).getDepartmentName();
                if(department != null)
                    obj[j][3] = department;
                else
                    obj[j][3] = "";

                //���ڲ�Ʒ
                String productID = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getProductMasterID();
                service = (PersistService)EJBServiceHelper.getService("PersistService");
                obj[j][4] = ((QMPartMasterIfc)service.refreshInfo(productID)).getPartNumber();

                //״̬
                LifeCycleState sta = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getLifeCycleState();
                if(sta != null)
                    obj[j][5] = sta.getDisplay();
                else
                    obj[j][5] = "";

                //�����Ŀ����
                String projectID = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getProjectId();
                if(projectID != null)
                {
                    ProjectIfc project = (ProjectIfc)service.refreshInfo(projectID);
                    obj[j][6] = CappServiceWebHelper.getIdentity(project);
                }else
                    obj[j][6] = "";

                //���ϼ�
                obj[j][7] = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getLocation();

                //˵��
                String description = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j - 1))).getRouteListDescription();
                if(description != null)
                    obj[j][8] = description;
                else
                    obj[j][8] = "";

            }
            //��
            boolean flag = false;
            //��δ�������ʲô�õ��أ�
            for(int l = 0;l < 9;l++)
            {
                for(int k = 2;k < selectBsoID.length + 1;k++)
                {
                    if(!((obj[k][l]).equals((obj[1][l]))))
                    {
                        flag = true;
                        break;
                    }
                }
                String[] tempArray = new String[selectBsoID.length + 1];
                if(flag)
                {
                    for(int m = 0;m < selectBsoID.length + 1;m++)
                    {
                        tempArray[m] = obj[m][l];
                    }
                    av.add(tempArray);
                    flag = false;
                }
            }

            if(verbose)
                System.out.println("cappclients.capp.web.RouteListVersionCompare.getAttVector() end...return: " + av);
            return av;
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ���·�߱�Ĺ����㲿���Ĳ������
     * @return ���ؼ��ϵ�Ԫ��Ϊ�ַ������飻������ĵ�һ��Ԫ��Ϊ�㲿����ţ�����Ԫ��Ϊ���㲿��������·�ߴ�
     */
    public Vector getdePartVector()
    {
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getdePartVector() begin...");
        Vector v = new Vector();
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            if(verbose)
                System.out.println("�������÷������õĲ�����myTechnicsCollection" + myTechnicsCollection);
            // zz�޸� �����Ż� start
            //Map map = routeService.compareIterate(myTechnicsCollection);
            if(map == null)
            {
                map = routeService.compareIterate(myTechnicsCollection);
            }
            if(verbose)
            {
                System.out.println("����TechnicsRouteServiec��compareIterate()�������õĽ��" + map);
                System.out.println("�������÷������õĲ�����myTechnicsCollection" + myTechnicsCollection);
            }
            if(map != null && map.size() > 0)
            {
                Object[] keys = map.keySet().toArray();
                for(int i = 0;i < keys.length;i++)
                {
                    String value = keys[i].toString();
                    StringTokenizer st = new StringTokenizer(value, ";");
                    String id;
                    String parentNum = null;
                    id = st.nextToken();
                    if(st.hasMoreTokens())
                        parentNum = st.nextToken();

                    String partNumber = ((QMPartMasterInfo)service.refreshInfo(id)).getPartNumber();
                    Vector partlinks = (Vector)map.get(value);
                    if(partlinks != null && partlinks.size() > 0)
                    {
                        String[] temp = new String[partlinks.size() + 1];
                        temp[0] = partNumber + "(����" + parentNum + ")";
                        for(int j = 0;j < partlinks.size();j++)
                        {
                            ListRoutePartLinkInfo link = (ListRoutePartLinkInfo)partlinks.elementAt(j);
                            String routeStr = " ";
                            if(link != null)
                            {
                                String routeID = link.getRouteID();
                                if(routeID != null)
                                    routeStr = getRouteBranches(routeID);
                            }
                            temp[j + 1] = routeStr;
                        }
                        v.add(temp);
                    }

                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getdePartVector() end...return: " + v);
        return v;
    }

    /**
     * ���·�߱�������㲿��������BsoID
     * @return �÷����ķ���ֵǡ����getdePartVector()�ķ���ֵ��ƥ�䡣
     */
    public Vector getLinkIDVector()
    {
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getLinkIDVector() begin...");
        Vector v = new Vector();
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");

            // ������һ)20060703 ���� zz �޸� �޸�ԭ��:�����Ż�����·�߱�İ汾�Ƚ�,ͬʱ�޸��˰汾�Ƚϵ�jsp
            // Map map = routeService.compareIterate(myTechnicsCollection);
            if(map == null)
            {
                map = routeService.compareIterate(myTechnicsCollection);
            }
            //zz ����  �޸�end �����Ż�
            if(map != null && map.size() > 0)
            {
                if(verbose)
                    System.out.println("RouteListVersionCompare,333��" + map);
                Object[] partmasters = map.keySet().toArray();
                for(int i = 0;i < partmasters.length;i++)
                {
                    String value = partmasters[i].toString();
                    StringTokenizer st = new StringTokenizer(value, ";");
                    String id;
                    String parentNum = null;
                    id = st.nextToken();
                    if(st.hasMoreTokens())
                        parentNum = st.nextToken();

                    String partNumber = ((QMPartMasterInfo)service.refreshInfo(id)).getPartNumber();
                    Vector partlinks = (Vector)map.get(value);
                    if(partlinks != null && partlinks.size() > 0)
                    {
                        String[] temp = new String[partlinks.size() + 1];
                        temp[0] = partNumber;
                        for(int j = 0;j < partlinks.size();j++)
                        {
                            ListRoutePartLinkInfo link = (ListRoutePartLinkInfo)partlinks.elementAt(j);
                            String routeStr = null;
                            if(link != null)
                            {
                                routeStr = link.getBsoID();
                            }
                            temp[j + 1] = routeStr;
                        }
                        v.add(temp);
                    }
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        if(verbose)
            System.out.println("cappclients.capp.web.RouteListVersionCompare.getLinkIDVector() end...return: " + v);
        return v;
    }

    /**
     * ���ָ��·�ߵ�·�ߴ�
     * @param routeID ����·�ߵ�BsoID
     * @return ·�ߴ�
     */
    public static String getRouteBranches(String routeID)
    {
        String result = "";
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            Map map = routeService.getRouteBranchs(routeID);
            Object[] branchs = RouteHelper.sortedInfos(map.keySet()).toArray();
            for(int i = 0;i < branchs.length;i++)
            {
                TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo)branchs[i];
                String makeStr = "";
                String assemStr = "";
                String routeStr = "";
                Object[] nodes = (Object[])map.get(branchinfo);
                Vector makeNodes = (Vector)nodes[0];
                RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1];

                if(makeNodes != null && makeNodes.size() > 0)
                {
                    //System.out.println(">>>>>>>>>>>>>>>>> ���
                    // ��֧"+branchinfo.getBsoID()+"������ڵ� ������"+makeNodes.size());
                    for(int m = 0;m < makeNodes.size();m++)
                    {
                        RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
                        if(makeStr.equals(""))
                            makeStr = makeStr + node.getNodeDepartmentName();
                        else
                            makeStr = makeStr + "��" + node.getNodeDepartmentName();
                    }
                }

                if(asseNode != null)
                {
                    assemStr = asseNode.getNodeDepartmentName();
                }
                if(!makeStr.equals("") && !assemStr.equals(""))
                    routeStr = makeStr + "��" + assemStr;
                else if(makeStr.equals("") && !assemStr.equals(""))
                    routeStr = assemStr;
                else if(!makeStr.equals("") && assemStr.equals(""))
                    routeStr = makeStr;
                else if(makeStr.equals("") && assemStr.equals(""))
                    routeStr = "";

                if(routeStr == null || routeStr.equals(""))
                {
                    routeStr = " ";
                }

                if(result.equals(""))
                    result = result + routeStr;
                else
                    result = result + "��" + routeStr;

                if(result == null || result.equals(""))
                    result = " ";
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

}
