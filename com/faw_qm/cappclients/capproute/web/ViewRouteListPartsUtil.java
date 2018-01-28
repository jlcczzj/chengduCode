/**
 * ���ɳ��� ViewRouteListPartsUtil.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

/* CR1 2009/04/21  ����   ԭ��: �Ż�����·�߱��ݿͻ��鿴��
 *                       ����: 1.��ʾ��ʱ��ֱ�Ӵӹ���·�߱��֧���н�·�ߴ���ʾ
 *                       ��ע: ���ܲ�����������"�ݿͻ��鿴����·�߱�". 
 *                       
 * CR2 2009/06/08  ������  �μ�:������:v4r3FunctionTest;TD��2268                 
 *                        
 *                        */

package com.faw_qm.cappclients.capproute.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.RouteNodeIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.route.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.technics.route.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title: �鿴·�߱���㲿����</p>
 * <p>Description: �鿴��·�߱���㲿����</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 */

public class ViewRouteListPartsUtil {
    private static PersistService pService;
    
//  CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·�� 
    private static HashMap  tempResult = new HashMap();
//  CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·��     
    public ViewRouteListPartsUtil() {
    }

    /**
     * �õ��ݿͻ���ͷ���ı�Ҫ��ʾ��Ϣ
     * @param bsoID ·�߱��BsoID
     * @return ·�߱��BsoID,���,����,����,��λ,�汾,���ڲ�Ʒ
     */
    public static String[] getRouteListHead(String bsoID) {
        String id = bsoID.trim();
        String number = "";
        String name = "";
        String product = "";
        String level = "";
        String department = "";
        String version = "";
        TechnicsRouteListInfo routelist;

        try {
            pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            routelist = (TechnicsRouteListInfo) pService.refreshInfo(id);
            number = routelist.getRouteListNumber();
            name = routelist.getRouteListName();
            level = routelist.getRouteListLevel();
            String dep = routelist.getDepartmentName();
            if (dep != null) {
                department = dep;
            }
            version = routelist.getVersionValue();
            QMPartMasterIfc partmaster = (QMPartMasterIfc) pService
                    .refreshInfo(routelist.getProductMasterID());
            product = partmaster.getPartNumber() + "_"
                    + partmaster.getPartName();

            //��������
            String[] myVersionArray1 = { bsoID, number, name, product, level,
                    department, version, };
            return myVersionArray1;

        } catch (Exception sle) {
            System.out.println(sle.toString());
            return null;
        }
    }
    
//  CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·��   
    

    private static Collection doFilter(String routelistBsoID,String isall,String contand,String depart)
{
  Collection col = (Collection)tempResult.get(routelistBsoID);
  if(col==null)
   {
     //System.out.println("������û������") ;
     return new Vector();
   }
   Vector result = new Vector();
   Iterator i = col.iterator();
   while(i.hasNext())
   {
    // String[] array = {linkID,partNum,partName,routeStr,routeState,iconURL};
    String[] array = (String[])i.next();
    String routeStr = array[3];
    if(isContaind(routeStr,isall,contand,depart))
       result.add(array);
   }
  return result;
}

private static boolean isContaind(String routeStr,String isall,String contand,String depart){
	//  System.out.println("routeStr="+routeStr+" isall="+isall+" contand="+contand+" depart="+depart);

    if(depart.trim().equals(""))
      return true;
     boolean flag = false;
     if(routeStr.trim().equals(""))
       return false;
//   CCBegin by leixiao 2008-11-6 ԭ�򣺽��ϵͳ����,��·�ߵ�λ��ѯ��ѡ������·�ߣ�װ��·��Ϊ����������
     StringTokenizer st = new StringTokenizer(routeStr,"��");
     while(st.hasMoreTokens())
     {
     String filterStr = st.nextToken();
    // System.out.println("----filterStr="+filterStr);
      int i = filterStr.lastIndexOf("��");
      int size = filterStr.length();
      if(isall.equals("����·��"))
       {
        if(i!=-1){
        filterStr =  filterStr.substring(0,i);
        }
       }
      else
       if(isall.equals("װ��·��"))
       {
         if(i!=-1){
          filterStr = filterStr.substring(i+1,size);
         }
          else
            filterStr = "";           
       }
//    CCEnd by leixiao 2008-11-6 ԭ�򣺽��ϵͳ����

       int location =  filterStr.indexOf(depart);
       if(contand.equals("����"))
       {
         if(location!=-1)
           return true;
       }
       if(contand.equals("ʼ��"))
       {
       //  System.out.println("location=="+location);
         if(location==0)
           return true;
       }
       if(contand.equals("ֹ��"))
       {
        if(filterStr.endsWith(depart))
          return true;
       }
      if(contand.equals("��ȫƥ��"))
       {
        if(filterStr.equals(depart))
          return true;
       }
     }
    return flag;
}
//CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·��   

    /**
     * ���ָ��·�߱��е������㲿������·�߷�֧,·��״̬
     * @param routelistBsoID
     * @return
     */
    public static Collection getParts(String routelistBsoID) {
      Vector c = new Vector();
      try {
       pService = (PersistService) EJBServiceHelper
                  .getService("PersistService");
       TechnicsRouteService routeServer = (TechnicsRouteService) EJBServiceHelper//CR2
       .getService("TechnicsRouteService");
       //CR1 BEGIN
        QMQuery query = new QMQuery("TechnicsRouteList");
        int a = query.appendBso("ListRoutePartLink",true);
        int b = query.appendBso("TechnicsRoute",false);
        int d = query.appendBso("TechnicsRouteBranch",true);
        query.addCondition(a,new QueryCondition("leftBsoID","=",
                                                 routelistBsoID));
        query.addAND();
        query.addLeftParentheses();
        query.addCondition(a,new QueryCondition("alterStatus", "=", 1));
        query.addOR();
        query.addCondition(a,new QueryCondition("alterStatus", "=", 0));
        query.addRightParentheses();
        query.addAND();
        QueryCondition qu=new QueryCondition("routeID", "bsoID");
        qu.setOuterJoin(1);
        query.addCondition(d,b,qu);
        
        query.addAND();
        QueryCondition qu2= new QueryCondition("routeID", "bsoID");
        qu2.setOuterJoin(2);
        query.addCondition(a,b,qu2);
        query.addAND();
        query.addCondition(new QueryCondition("bsoID","=",
                                                 routelistBsoID));
       
        Collection coll = pService.findValueInfo(query);
          //Vector links = (Vector) routeService
                  //.getRouteListLinkParts(routelist);
          if (coll != null && coll.size() > 0) {
          	
              for (Iterator it = coll.iterator(); it.hasNext(); ) {
                  BaseValueIfc[] bvis = (BaseValueIfc[]) it.next();
                  TechnicsRouteListIfc routeList=(TechnicsRouteListIfc)bvis[0];//CR2
                  ListRoutePartLinkIfc linkinfo = (ListRoutePartLinkIfc)bvis[1];
                  TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)bvis[2];
                  String linkID = linkinfo.getBsoID();
                  String partNum = linkinfo.getPartMasterInfo().getPartNumber();
                  String partName = linkinfo.getPartMasterInfo().getPartName();
                  String routeID = linkinfo.getRouteID();
                  String routeStr = "";
                  String routeState = "";
                  String iconURL = "";
                  
                  if (routeID==null) {                            //Begin CR2

                      String level = routeList.getRouteListLevel();
                      String level2 = new String("����·��");
                      String status = null;
                      if (level.equals(level2)) {
                        String departmentBsoid = routeList.
                            getRouteListDepartment();
                        status = routeServer.getStatus2(linkinfo.getPartMasterID(),
                                            level, departmentBsoid);
                      }
                      else {
                        status =routeServer. getStatus(linkinfo.getPartMasterID(),
                        		routeList.getRouteListLevel());
                      }
                      linkinfo.setAdoptStatus(status);
                     
                    }                                         //End CR2
 
                  routeState = linkinfo.getAdoptStatus();//·��״̬
                  if (routeID != null) {
                      //routeStr = getRouteBranches(link.getRouteID());
                      routeStr = branch.getRouteStr();
                      //END CR1
                      iconURL = "images/route.gif";
                  } else {
                      routeStr = "";
                      iconURL = "images/route_emptyRoute.gif";
                  }
                  String[] array = { linkID, partNum, partName, routeStr,
                          routeState, iconURL };
                  c.addElement(array);
              }
          }
      } catch (Exception ex) {
          System.out.println(ex.toString());
      }
      return c;
  }
    
  //  CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·�� 

    
    public static Collection getParts(String routelistBsoID,String isall,String contand,String depart)
    {
    	   
        //	System.out.println("getParts routelist="+routelistBsoID+" isall="+isall+" contand="+contand+" depart="+depart);

          Vector c = new Vector();
          try
          {
            pService = (PersistService)EJBServiceHelper.getService("PersistService");
            TechnicsRouteListIfc routelist = (TechnicsRouteListIfc)pService.refreshInfo(routelistBsoID);
            TechnicsRouteService routeService =
                (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
            Vector links;
            if(isall.trim().equals(""))
             // links = (Vector)routeService.getRouteListLinkParts(routelist);
            links= (Vector)getRouteListLinkParts(routelistBsoID);
             else
             // links = (Vector)routeService.getRouteListLinkParts(routelist,isall,contand,depart);
            return doFilter(routelistBsoID,isall,contand,depart);
            if(links != null && links.size()>0)
            {
              for(int i=0;i<links.size();i++)
              {
                ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)links.elementAt(i);
                String linkID = link.getBsoID();
                String partNum = link.getPartMasterInfo().getPartNumber();
                String partName = link.getPartMasterInfo().getPartName();
                String routeID = link.getRouteID();
                String routeStr="";
                String routeState="";
                String iconURL = "";
                //CCBegin by leixiao 201-1-7 ״̬��route��ȡModefyIdenty
                if(routeID!=null){
                TechnicsRouteIfc route = (TechnicsRouteIfc)pService.refreshInfo(routeID);
                routeState=route.getModefyIdenty().getCodeContent();
                }
                else{
                routeState = "��";//·��״̬
                }
                //CCEnd by leixiao 201-1-7
                if(routeID!=null)
                {
                  routeStr = getRouteBranches(link.getRouteID());
                  iconURL = "images/route.gif";
                }
                else
                {
                  routeStr = "";
                  iconURL = "images/route_emptyRoute.gif";
                }
                String[] array = {linkID,partNum,partName,routeStr,routeState,iconURL};
                c.addElement(array);
              }
            }
          }
          catch (Exception ex)
          {
            System.out.println(ex.toString());
          }
          if(isall.trim().equals(""))
            tempResult.put(routelistBsoID,c);
          return c;
        }
    //  CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·�� 

    /**
	 * ���ָ��·�ߵ�·�ߴ�
	 * 
	 * @param routeID
	 *            ����·�ߵ�BsoID
	 * @return ·�ߴ�
	 */
    public static String getRouteBranches(String routeID) {
      //long starttime = System.currentTimeMillis();

        String result = "";
        try {
            TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper
                    .getService("TechnicsRouteService");
            Map map = routeService.getRouteBranchs(routeID);

            Object[] branchs = RouteHelper.sortedInfos(map.keySet()).toArray();
            for (int i = 0; i < branchs.length; i++) {
                TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo) branchs[i];
                String makeStr = "";
                String assemStr = "";
                String routeStr = "";
                Object[] nodes = (Object[]) map.get(branchinfo);
                Vector makeNodes = (Vector) nodes[0];
                RouteNodeIfc asseNode = (RouteNodeIfc) nodes[1];

                if (makeNodes != null && makeNodes.size() > 0) {
                    // ��֧"+branchinfo.getBsoID()+"������ڵ� ������"+makeNodes.size());
                    for (int m = 0; m < makeNodes.size(); m++) {
                        RouteNodeInfo node = (RouteNodeInfo) makeNodes
                                .elementAt(m);
                        if (makeStr.equals(""))
                            makeStr = makeStr + node.getNodeDepartmentName();
                        else
                            makeStr = makeStr + "��"
                                    + node.getNodeDepartmentName();
                    }
                }

                if (asseNode != null) {
                    assemStr = asseNode.getNodeDepartmentName();
                }
                if (!makeStr.equals("") && !assemStr.equals(""))
                    routeStr = makeStr + "��" + assemStr;
                else if (makeStr.equals("") && !assemStr.equals(""))
                    routeStr = assemStr;
                else if (!makeStr.equals("") && assemStr.equals(""))
                    routeStr = makeStr;
                else if (makeStr.equals("") && assemStr.equals(""))
                    routeStr = "";

                if (routeStr.equals("")) {
                    routeStr = "��";
                }

                if (result.equals(""))
                    result = result + routeStr;
                else
                    result = result + "��" + routeStr;

                if (result.equals(""))
                    result = "��";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        long endtime = System.currentTimeMillis();
//          System.out.println(" kankan time  end  " +( endtime- starttime));
        return result;
    }
    
//  CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·��     
    public Vector getAllDeps()
    {
      try{
        TechnicsRouteService routeService =
            (TechnicsRouteService) EJBServiceHelper.getService(
            "TechnicsRouteService");
           return routeService.getAllDeps();
         }catch (Exception ex)
         {
           System.out.println("�������е�λ��������");
           ex.printStackTrace();
         }
        return new Vector();
    }
//  CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·�� 
    
//  CCBegin by leixiao 2009-11-26 ԭ�򣺽����������·�� �������ò�Ʒ�����Ż�������4���ѯ��������û��
    private static Collection getRouteListLinkParts(String routelistBsoID) {
    	try{
    	pService = (PersistService)EJBServiceHelper.getService("PersistService");
        QMQuery query = new QMQuery("ListRoutePartLink");
        query.addCondition(new QueryCondition("leftBsoID","=",
                                                 routelistBsoID));
        query.addAND();
        query.addLeftParentheses();
        query.addCondition(new QueryCondition("alterStatus", "=", 1));
        query.addOR();
        query.addCondition(new QueryCondition("alterStatus", "=", 0));
        query.addRightParentheses(); 
        return pService.findValueInfo(query);
    	}catch(QMException e){
    		e.printStackTrace();
    		return null;
    	}
    }
//  CCEnd by leixiao 2009-11-26 ԭ�򣺽����������·�� 
}
