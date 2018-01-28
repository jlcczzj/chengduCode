/**
 * ���ɳ��� ViewRouteLevelOne.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �޸��㲿������鿴��������·�߼�����·�߸��ı����ʾ�������� pante 20130730
 * SS2 ��׼BSX-1842.01-36���е�3802020-54B�汾C �ڹ�����ͼ�����±��Ƶ�·�ߣ����ڲ鿴�㲿��·��ʱȴ������·������ leixiao 2013-9-10
 * SS3 �޸Ĵ��㲿�����湤��·��˫��·����ʾ��������� maxt 2013-12-17
 * SS4 �޸ı��������·�����������ʾ�����������⣬��Ϊconslistpartlink����rightbsoid�ﲻһ������qmpartmasterid�ˣ��µ���qmpartid�ˡ� liunan 2014-1-23
 * SS5 �������·�����Ӳɹ���ʶ��ʾ pante 2014-05-22
 * SS6 A005-2015-3096 �㲿���Ķ���·���б��У�ֻ��ʾ���°�����ݣ�����ʾ�м�С�汾�� liunan 2015-8-11
 * SS7 ���Ӱ汾ת����ʶ xudezheng 2018-01-26
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
 * <p> Title: �鿴һ��·�� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class ViewRouteLevelOne
{

    private RouteWebHelper helper = new RouteWebHelper();

    /** ������Ա��� */

    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    public ViewRouteLevelOne()
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

    /** ·�ߴ����� */
    //private static Vector nodeIndexVector = new Vector();
    
    /**
     * ���ָ���㲿����һ��·��
     * @param partMasterID
     * @return
     */
    public static Collection getPartLevelRoutes(String partMasterID)
    {
        Vector v = new Vector();
        //nodeIndexVector.clear();
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            PersistService service1 = (PersistService)EJBServiceHelper.getService("PersistService");
           

            Vector v2 = (Vector)routeService.getPartLevelRoutes(partMasterID, RouteListLevelType.FIRSTROUTE.getDisplay());
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
                    //CCBegin SS2
                    String product="";
                    if(list.getProductMasterID()!=null&&!list.getProductMasterID().trim().equals("")){
                    QMPartMasterIfc info = (QMPartMasterIfc)service1.refreshInfo(list.getProductMasterID());
                     product = info.getPartNumber() + "_" + info.getPartName();
                    }
                    //CCEnd SS2
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
                    //SSBegin SS1
//                    Vector branchVector = getRouteBranches(map);
                    Vector branchVector = new Vector();
                    String makeStr = "";
                    String assemStr = "";
                    if(link.getMainStr()!=null){
                    	String[] s = link.getMainStr().split("=");
                    	if(s.length>1){
                    		makeStr = s[0];
                    		assemStr = s[1];
                    	}
                    	else{
                    		makeStr = s[0];
                    		assemStr = "";
                    	}
                    	//CCBegin SS5
                    	//CCBegin SS7
//                    	String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID()};
                    	String[] array1 = null;
                    	if(link.getSpecialFlag()!=null && link.getSpecialFlag().equals("1"))
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID(),"ת��"};
                    	else
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID(),""};
                    	//CCEnd SS7
                    	//CCEnd SS5
                    	branchVector.addElement(array1);
                    }
                    if(link.getSecondStr()!=null){
                    	//SSBegin SS3
//                    	String[] s = link.getMainStr().split("=");
                    	String[] s = link.getSecondStr().split("=");
                    	//SSEnd SS3
                    	if(s.length>1){
                    		makeStr = s[0];
                    		assemStr = s[1];
                    	}
                    	else{
                    		makeStr = s[0];
                    		assemStr = "";
                    	}
                    	//CCBegin SS5
                    	//CCBegin SS7
//                    	String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID()};
                    	String[] array1 = null;
                    	if(link.getSpecialFlag()!=null && link.getSpecialFlag().equals("1"))
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID(),"ת��"};
                    	else
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID(),""};
                    	//CCEnd SS7
                    	//CCEnd SS5
                    	branchVector.addElement(array1);
                    }
                  //SSEnd SS1
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
    
    
    //CCBegin SS4
    /**
     * ���ָ���㲿����һ��·��
     * @param partMasterID
     * @return
     */
    public static Collection getPartLevelRoutes(String partMasterID, String partID)
    {
    	System.out.println("partMasterID-----------11111-----------="+partMasterID);
        Vector v = new Vector();
        //nodeIndexVector.clear();
        try
        {
            TechnicsRouteService routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
            PersistService service1 = (PersistService)EJBServiceHelper.getService("PersistService");
           

            Vector v2 = (Vector)routeService.getPartLevelRoutes(partMasterID, partID, RouteListLevelType.FIRSTROUTE.getDisplay());
            if(v2 != null && v2.size() > 0)
            {
                for(int i = 0;i < v2.size();i++)
                {
                    Object[] objs = (Object[])v2.elementAt(i);
                    TechnicsRouteListInfo list = (TechnicsRouteListInfo)objs[0];
                    //CCBegin SS6
                    if(!list.getIterationIfLatest())
                    {
                    	continue;
                    }
                    //CCEnd SS6
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
                    //CCBegin SS2
                    String product="";
                    if(list.getProductMasterID()!=null&&!list.getProductMasterID().trim().equals("")){
                    QMPartMasterIfc info = (QMPartMasterIfc)service1.refreshInfo(list.getProductMasterID());
                     product = info.getPartNumber() + "_" + info.getPartName();
                    }
                    //CCEnd SS2
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
                    //SSBegin SS1
//                    Vector branchVector = getRouteBranches(map);
                    Vector branchVector = new Vector();
                    String makeStr = "";
                    String assemStr = "";
                    if(link.getMainStr()!=null){
                    	String[] s = link.getMainStr().split("=");
                    	if(s.length>1){
                    		makeStr = s[0];
                    		assemStr = s[1];
                    	}
                    	else{
                    		makeStr = s[0];
                    		assemStr = "";
                    	}
                    	//CCBegin SS5
                    	//String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "��"};
                    	//CCBegin SS7
//                    	String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID()};
                    	String[] array1 = null;
                    	if(link.getSpecialFlag()!=null && link.getSpecialFlag().equals("1"))
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID(),"ת��"};
                    	else
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID(),""};
                    	//CCEnd SS7
                    	//CCEnd SS5
                    	branchVector.addElement(array1);
                    }
                    if(link.getSecondStr()!=null){
                    	//SSBegin SS3
//                    	String[] s = link.getMainStr().split("=");
                    	String[] s = link.getSecondStr().split("=");
                    	//SSEnd SS3
                    	if(s.length>1){
                    		makeStr = s[0];
                    		assemStr = s[1];
                    	}
                    	else{
                    		makeStr = s[0];
                    		assemStr = "";
                    	}
                    	//CCBegin SS5
                    	//String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "��"};
                    	//CCBegin SS7
//                    	String[] array1 = {link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID()};
                    	String[] array1 = null;
                    	if(link.getSpecialFlag()!=null && link.getSpecialFlag().equals("1"))
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID(),"ת��"};
                    	else
                    		array1 = new String[]{link.getModifyIdenty(), makeStr, assemStr, "��", link.getStockID(),""};
                    	//CCEnd SS7
                    	//CCEnd SS5
                    	branchVector.addElement(array1);
                    }
                  //SSEnd SS1
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
    //CCEnd SS4
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
            System.out.println("------------------"+branchinfo.getBsoID());
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

                // ��֧"+branchinfo.getBsoID()+"������ڵ� ������"+makeNodes.size());

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
