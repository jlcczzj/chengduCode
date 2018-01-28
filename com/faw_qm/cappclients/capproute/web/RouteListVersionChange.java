/** 生成程序 RouteListVersionCompare.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capproute.web;

import java.util.Vector;
import java.util.Map;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;
import java.util.*;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.project.model.*;
import com.faw_qm.part.model.*;
import com.faw_qm.technics.route.ejb.service.*;
import com.faw_qm.technics.route.util.*;
import com.faw_qm.capp.util.*;
import com.faw_qm.version.ejb.service.*;
import java.util.*;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.part.model.*;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.*;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.technics.route.util.*;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.*;
import com.faw_qm.folder.ejb.service.*;

/**
 * <p>Title:路线表的版本比较 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */

public class RouteListVersionChange {
  /**所选择对象的ID*/
  private String[] selectBsoID;
  /**持久化服务*/
  private PersistService service;
  /**路线表对象*/
 // private TechnicsRouteListIfc myObjectInfo;
  /**瘦客户端头部的必要显示信息(路线表编号、名称、版本号、图标)*/
  public String[] myHeadInfo;

  public boolean flag=false;
  private String technicsBsoID;
  private String preBsoID;
  /**路线表的版本号*/
 // public String technicsVersionID;
  /**版本号集合*/
  public Vector myVersionIDCollection =new Vector();
  /**路线表对象集合*/
  public Vector myTechnicsCollection = new Vector();
  /***/
  private RouteWebHelper helper=new RouteWebHelper();
  /**代码测试变量*/
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.cappclients.verbose", "true")).equals("true");

  /**
   * 构造函数
   */
  public RouteListVersionChange()
  {
  }

  /**
   * 设置选择的所有对象的BsoID
   * @param sBsoID 对象的BsoID的数组
   */
  public void setSelectBsoID(String sBsoID)
  {
    if(verbose)
      System.out.println("capproute.web.TechnicsVersionCompareUtil.setSelectBsoID() begin...");
    //把bsoID按照升序排列后返回
  try{
       service = (PersistService) EJBServiceHelper.getService("PersistService");
       TechnicsRouteListIfc technicsInfo = (TechnicsRouteListIfc) service.
           refreshInfo(sBsoID);
      FolderService fservice =   (FolderService) EJBServiceHelper.getService("FolderService");
     VersionControlService service1 = (VersionControlService)
           EJBServiceHelper.getService("VersionControlService");
     Collection  myVersionCollection = (Collection) service1.allVersionsOf( technicsInfo);
     Iterator i = myVersionCollection.iterator();
     selectBsoID = new String[2];
     if(i.hasNext())
     {
      TechnicsRouteListIfc temp =  (TechnicsRouteListIfc)i.next();
        //     System.out.println("进入第一个"+temp);
     while(fservice.inPersonalFolder(temp))
       temp = (TechnicsRouteListIfc)i.next();
     this.technicsBsoID = temp.getBsoID();
     selectBsoID[0] = technicsBsoID;
//   CCBegin by leixiao 2008-11-11 原因：解放升级工艺路线 ,变更信息汇总表的版本应显示为版序  
//      myVersionIDCollection.add(temp.getVersionID());
      myVersionIDCollection.add(temp.getVersionValue());
      myTechnicsCollection.add(temp);
//    CCBegin by leixiao 2008-11-11 原因：解放升级工艺路线 ,变更信息汇总表的版本应显示为版序  
     }
     if(i.hasNext())
    {
        TechnicsRouteListIfc temp =  (TechnicsRouteListIfc)i.next();
           //    System.out.println("进入第二个"+temp);
       // while(fservice.inPersonalFolder(temp))
        // temp = (TechnicsRouteListIfc)i.next();
        this.preBsoID = temp.getBsoID();
        selectBsoID[1] = preBsoID;
//      CCBegin by leixiao 2008-11-11 原因：解放升级工艺路线  1变更信息汇总表的版本应显示为版序  2先显示变更前，再显示变更后    
//        myVersionIDCollection.add(temp.getVersionID());
        myVersionIDCollection.insertElementAt(temp.getVersionValue(),0);
        myTechnicsCollection.insertElementAt(temp,0);
//      CCEnd by leixiao 2008-11-11 原因：解放升级工艺路线       
    }
     
     } catch(Exception sle)
     {
       sle.printStackTrace();
       System.out.println(sle.toString());
     }
    flag=true;
    if(verbose)
      System.out.println("cappclients.capp.web.TechnicsVersionCompareUtil.setSelectBsoID() end...return is void");
  }

  /**
   * 得到瘦客户端头部的必要显示信息(路线表编号、名称、版本号、图标)
   * @param technicsBsoID 对象的BsoID
   */
  public String[] getHeadInfo(String technicsBsoID)
  {
    if(verbose)
      System.out.println("cappclients.capp.web.TechnicsVersionCompareUtil.getHeadInfo() begin...");
    try{

      service =(PersistService)EJBServiceHelper.getService("PersistService");
      TechnicsRouteListIfc  technicsInfo=(TechnicsRouteListIfc)service.refreshInfo(technicsBsoID);

      String technicsNumber = technicsInfo.getRouteListNumber();
      String technicsName = technicsInfo.getRouteListName();
      String versionID = technicsInfo.getVersionID();
      String headIconUrl = helper.getStandardIconName(technicsInfo);
//    CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,因为原来的字符包括“＜”在jsp无法显示其内的字符，故将其去掉 
      String total =technicsNumber+"("+technicsName+")"+" 变更信息汇总表";
//    CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线     
    //  System.out.println("total="+total+" technicsName="+technicsName+" versionID="+versionID+" headIconUrl="+headIconUrl);
      String[] myHeadInfo1 = {total,technicsName,versionID,headIconUrl};
      if(verbose)
        System.out.println("cappclients.capp.web.TechnicsVersionCompareUtil.getHeadInfo() end...return: "+myHeadInfo1.length);
      return myHeadInfo1;
    }
    catch(Exception sle)
    {
      System.out.println(sle.toString());
      return null;
    }
  }

  /**
   * 得到选中的对象的集合，页面上选中的是BsoID通过这个方法转成对象
   * @return Vector 选中的对象的集合
   */
  public Vector getSelectObject()
  {
      return myTechnicsCollection;

  }



  /**
   * 得到选中的对象的版本号的集合
   * @return Vector
   */
  public Vector getMyVersionIDCollection()
  {
    return myVersionIDCollection;
  }

  /**
   * 获得选中的对象的基本属性的集合，页面上选中的是BsoID通过这个方法转成对象
   * @return Vector 必要的属性的集合，集合的元素是数组对象
   */
  public Vector getAttVector()
  {
    if(verbose)
      System.out.println("cappclients.capp.web.RouteListVersionCompare.getAttVector() begin...");
    try
    {
      Vector av=new Vector();
      //Vector tav=new Vector();
     // System.out.println("------myTechnicsCollection="+myTechnicsCollection);
      String[][] obj=new String[myTechnicsCollection.size()+1][9];

      obj[0][0]="编号";
      obj[0][1]="名称";
      obj[0][2]="级别";
      obj[0][3]="单位";
      obj[0][4]="用于产品";
      obj[0][5]="生命周期状态";
      obj[0][6]="项目组";
      obj[0][7]="资料夹";
      obj[0][8]="说明";

      for(int j=1;j<myTechnicsCollection.size()+1;j++)
      {
        obj[j][0]=((TechnicsRouteListIfc)(myTechnicsCollection.get(j-1))).getRouteListNumber();
        obj[j][1]=((TechnicsRouteListIfc)(myTechnicsCollection.get(j-1))).getRouteListName();
        obj[j][2]=((TechnicsRouteListIfc)(myTechnicsCollection.get(j-1))).getRouteListLevel();
        //单位
        String department = ((TechnicsRouteListInfo)(myTechnicsCollection.get(j-1))).getDepartmentName();
        if(department!=null)
          obj[j][3]=department;
        else
          obj[j][3]="";

        //用于产品
        String productID = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j-1))).getProductMasterID();
        service =(PersistService)EJBServiceHelper.getService("PersistService");
        obj[j][4]=((QMPartMasterIfc)service.refreshInfo(productID)).getPartNumber();

        //状态
//      CCBegin by leixiao 2009-11-26 原因：解放升级工艺路线 
        LifeCycleState sta = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j-1))).getLifeCycleState();
//      CCEnd by leixiao 2008-11-26 原因：解放升级工艺路线 
        if(sta != null)
          obj[j][5]= sta.getDisplay();
        else
          obj[j][5]="";


        //获得项目组名
        String projectID = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j-1))).getProjectId();
        if(projectID != null)
        {
          ProjectIfc project=(ProjectIfc)service.refreshInfo(projectID);
          obj[j][6]=CappServiceHelper.getIdentity(project);
        }
        else
          obj[j][6]="";

        //资料夹
        obj[j][7]=((TechnicsRouteListIfc)(myTechnicsCollection.get(j-1))).getLocation();

        //说明
        String description = ((TechnicsRouteListIfc)(myTechnicsCollection.get(j-1))).getRouteListDescription();
        if(description!=null)
          obj[j][8] = description;
        else
          obj[j][8] = "";
      }
      //？
      boolean flag=false;
      //这段代码是做什么用的呢？
      for(int l=0;l<9;l++)
      {
        for(int k=2;k<selectBsoID.length+1;k++)
        {
          if(!((obj[k][l]).equals((obj[1][l]))))
          {
            flag=true;
            break;
          }
        }
        String[] tempArray=new String[selectBsoID.length+1];
        if(flag)
        {
          for(int m=0;m<selectBsoID.length+1;m++)
          {
            tempArray[m]=obj[m][l];
          }
          av.add(tempArray);
          flag=false;
        }
      }

      if(verbose)
        System.out.println("cappclients.capp.web.RouteListVersionCompare.getAttVector() end...return: "+av);
      return av;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 获得路线表的关联零部件的差异情况
   * @return 返回集合的元素为字符串数组；各数组的第一个元素为零部件编号，其他元素为各零部件关联的路线串
   */
  public Vector getdePartVector()
  {
    if(verbose)
      System.out.println("cappclients.capp.web.RouteListVersionCompare.getdePartVector() begin...");
    Vector v=new Vector();
    try{
      TechnicsRouteService routeService =
              (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
      if(verbose)
          System.out.println("上述调用方法所用的参数，myTechnicsCollection"+myTechnicsCollection);
      Map map = routeService.compareIterate(myTechnicsCollection);
      if(verbose)
      {
        System.out.println("调用TechnicsRouteServiec的compareIterate()方法所得的结果"+map);
        System.out.println("上述调用方法所用的参数，myTechnicsCollection"+myTechnicsCollection);
      }
      if(map!=null && map.size()>0)
      {
        Object[] keys = map.keySet().toArray();
        for(int i=0;i<keys.length;i++)
        {
          String value = keys[i].toString();
          StringTokenizer st = new StringTokenizer(value,";");
          String id;
          String parentNum = null;
          id = st.nextToken();
          if(st.hasMoreTokens())
            parentNum = st.nextToken();

          String partNumber = ((QMPartMasterInfo)service.refreshInfo(id)).getPartNumber();
          Vector partlinks = (Vector)map.get(value);
          if(partlinks!=null && partlinks.size()>0)
          {
            String[] temp = new String[4];
            temp[0] = partNumber;
            for(int j=0;j<partlinks.size();j++)
            {
              ListRoutePartLinkInfo link = (ListRoutePartLinkInfo)partlinks.elementAt(j);
              String routeStr = " ";
              if(link!=null)
              {
                String routeID = link.getRouteID();
                if(routeID!=null)
                 routeStr = getRouteBranches(routeID);
              }
              temp[j+1] = routeStr;
            }
            if(!temp[1].equals(temp[2]))
            {
               temp[3] = changeType(temp[1], temp[2]);
              // System.out.println("-----0="+temp[0]+" 1="+temp[1]+" 2="+temp[2]+" 3="+temp[3]);
               v.add(temp);
            }
          }

        }
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    if(verbose)
      System.out.println("cappclients.capp.web.RouteListVersionCompare.getdePartVector() end...return: "+v);
    return v;
  }

  private String changeType(String str1,String str2)
  {
	 // CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,统一改成路线
	  if(true)
	  return "路线";
	//  CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线
      if(str1.equals("")&&!str2.equals(""))
        return "路线";
     if(!str1.equals("")&&str2.equals(""))
        return "路线";
      StringTokenizer sk1 = new StringTokenizer(str1,"=");
      StringTokenizer sk2 = new StringTokenizer(str2,"=");
      String mstr1 = "";
      String astr1 = "";
      String mstr2 = "";
      String astr2 = "";
       if(sk1.hasMoreTokens())
         mstr1 = sk1.nextToken();
       if(sk1.hasMoreTokens())
         astr1 = sk1.nextToken();
       if(sk2.hasMoreTokens())
        mstr2 = sk2.nextToken();
      if(sk2.hasMoreTokens())
        astr2 = sk2.nextToken();
      if(mstr1.equals(mstr2)&&!astr1.equals(astr2))
        return "装配路线";
      if(!mstr1.equals(mstr2)&&astr1.equals(astr2))
        return "制造路线";
      return "路线";

  }
  // CCBegin by leixiao 2008-11-12 原因：解放升级工艺路线,变更信息汇总表汇总不出变更   
 public Vector getRouteAtt()
 {
   Vector result = new Vector();
   TechnicsRouteListIfc tl1 = (TechnicsRouteListIfc)myTechnicsCollection.elementAt(0);
   TechnicsRouteListIfc tl2 = (TechnicsRouteListIfc)myTechnicsCollection.elementAt(1);
   Collection col1 = this.getRoutesAndLinks(tl1);
   Collection col2 = this.getRoutesAndLinks(tl2);
   Vector v1=(Vector)col1;
   Vector v2=(Vector)col2;
//   System.out.println("v1.s="+v1.size()+" v2.s"+v2.size());
//   Iterator i1 = col1.iterator();
//   Iterator i2 = col2.iterator();
//   while(i1.hasNext())
	   for(int k1=0;k1<v1.size();k1++)
   {
//     Object[] objs1 = (Object[])i1.next();
     Object[] objs1 = (Object[])v1.get(k1);
     ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo) objs1[0];
     TechnicsRouteInfo routelist1 = (TechnicsRouteInfo) objs1[1];
     QMPartMasterIfc partmast1 =  info1.getPartMasterInfo();
 //    System.out.println("ListRoutePartLinkInfo1="+info1+" TechnicsRouteInfo1="+routelist1+" partmast1"+partmast1+" routelist1.des="+routelist1.getRouteDescription()+"--id1="+routelist1.getModefyIdenty()+""+partmast1.getPartNumber());
//     while(i2.hasNext())
     for(int k2=0;k2<v2.size();k2++)
     {
//       Object[] objs2 = (Object[])i2.next();
    	 Object[] objs2 = (Object[])v2.get(k2); 
       ListRoutePartLinkInfo info2 = (ListRoutePartLinkInfo) objs2[0];
       TechnicsRouteInfo routelist2 = (TechnicsRouteInfo) objs2[1];
       QMPartMasterIfc partmast2 =  info2.getPartMasterInfo();
  //     System.out.println(" ListRoutePartLinkInfo2="+info2+" TechnicsRouteInfo2="+routelist1+" partmast2"+partmast2+" routelist2.des="+routelist2.getRouteDescription()+"--id2="+routelist2.getModefyIdenty()+""+partmast2.getPartNumber());
       if(partmast2.getBsoID().equals(partmast1.getBsoID()))
       {
         if(!routelist2.getModifyTime().equals(routelist1.getModifyTime()))
         {
             if(!routelist2.getRouteDescription().equals(routelist1.getRouteDescription()))
             {
               String[] atts =  new String[4];
               atts[0] = partmast2.getPartNumber();
               atts[1] = "路线说明";
               atts[2] = routelist1.getRouteDescription();
               atts[3] = routelist2.getRouteDescription();
              // System.out.println(atts[0]+" "+atts[1]+" "+atts[2]+" "+atts[3]);
               result.add(atts);
             }
             if(!routelist2.getModefyIdenty().getCodeContent().equals(routelist1.getModefyIdenty().getCodeContent()))
             {
               String[] atts =  new String[4];
               atts[0] = partmast2.getPartNumber();
               atts[1] = "更改标记";
               atts[2] = routelist1.getModefyIdenty().getCodeContent();
               atts[3] = routelist2.getModefyIdenty().getCodeContent();
              // System.out.println(atts[0]+" "+atts[1]+" "+atts[2]+" "+atts[3]);
               result.add(atts);
             }
             
         }
         break;
       }
     }
   }
   return result;

 }
 // CCEnd by leixiao 2008-11-12 原因：解放升级工艺路线,变更信息汇总表汇总不出变更   

 private Collection getRoutesAndLinks(TechnicsRouteListIfc list)  {
   try{
     PersistService pservice = (PersistService) EJBServiceHelper.
         getPersistService();
     QMQuery query = new QMQuery("ListRoutePartLink");
     int j = query.appendBso("TechnicsRoute", true);
    // int k = query.appendBso(TechnicsRouteServiceEJB.ROUTELIST_BSONAME, false);
     QueryCondition cond3 = new QueryCondition("leftBsoID",
                                               QueryCondition.EQUAL,
                                               list.getBsoID());
     query.addCondition(0, cond3);
     query.addAND();
     // CCBegin by leixiao 2008-11-12 原因：解放升级工艺路线,变更信息汇总表汇总不出变更   
//     QueryCondition cond4 = new QueryCondition("alterStatus",
//                                               QueryCondition.EQUAL,
//                                               1);
//     query.addCondition(0, cond4);
//     query.addAND();
     // CCEnd by leixiao 2008-11-12 原因：解放升级工艺路线
     QueryCondition cond5 = new QueryCondition("routeID",
                                               QueryCondition.NOT_NULL);
     query.addCondition(0, cond5);
     query.addAND();
     QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
     query.addCondition(0, j, cond6);
     //SQL不能正常处理。
//     query.setDisticnt(true);
     //返回ListRoutePartLinkIfc
     return pservice.findValueInfo(query);
   }catch(Exception e)
   {
     e.printStackTrace();
   }
   return null;
  }


  /**
   * 获得路线表的所有零部件关联的BsoID
   * @return 该方法的返回值恰好与getdePartVector()的返回值相匹配。
   */
  public Vector getLinkIDVector()
  {
    if(verbose)
      System.out.println("cappclients.capp.web.RouteListVersionCompare.getLinkIDVector() begin...");
    Vector v=new Vector();
    try{
      TechnicsRouteService routeService =
              (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
      Map map = routeService.compareIterate(myTechnicsCollection);
      if(map!=null && map.size()>0)
      {
        if(verbose)
          System.out.println("RouteListVersionCompare,333行"+map);
        Object[] partmasters = map.keySet().toArray();
        for(int i=0;i<partmasters.length;i++)
        {
          String value = partmasters[i].toString();
         StringTokenizer st = new StringTokenizer(value,";");
         String id;
         String parentNum = null;
         id = st.nextToken();
         if(st.hasMoreTokens())
           parentNum = st.nextToken();

          String partNumber = ((QMPartMasterInfo)service.refreshInfo(id)).getPartNumber();
          Vector partlinks = (Vector)map.get(value);
          if(partlinks!=null && partlinks.size()>0)
          {
            String[] temp = new String[partlinks.size()+1];
            temp[0] = partNumber;
            for(int j=0;j<partlinks.size();j++)
            {
              ListRoutePartLinkInfo link = (ListRoutePartLinkInfo)partlinks.elementAt(j);
              String routeStr = null;
              if(link!=null)
              {
                 routeStr = link.getBsoID();
              }
              temp[j+1] = routeStr;
            }
            v.add(temp);
          }
        }
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    if(verbose)
      System.out.println("cappclients.capp.web.RouteListVersionCompare.getLinkIDVector() end...return: "+v);
    return v;
  }

  /**
   * 获得指定路线的路线串
   * @param routeID 工艺路线的BsoID
   * @return 路线串
   */
  public static String getRouteBranches(String routeID)
  {
    String result = "";
    try
    {
      TechnicsRouteService routeService =
          (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
      Map map = routeService.getRouteBranchs(routeID);
      Object[] branchs = RouteHelper.sortedInfos(map.keySet()).toArray();
      for(int i=0;i<branchs.length;i++)
      {
        TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo)branchs[i];
        String makeStr = "";
        String assemStr = "";
        String routeStr = "";
        Object[] nodes = (Object[])map.get(branchinfo);
        Vector makeNodes = (Vector)nodes[0];
        RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1];

        if(makeNodes != null && makeNodes.size()>0)
        {
          //System.out.println(">>>>>>>>>>>>>>>>>  获得 分支"+branchinfo.getBsoID()+"的制造节点 个数："+makeNodes.size());
          for(int m=0;m<makeNodes.size();m++)
          {
            RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
            if(makeStr.equals(""))
              makeStr = makeStr + node.getNodeDepartmentName();
            else
              makeStr = makeStr +"-"+node.getNodeDepartmentName();
          }
        }

        if(asseNode!=null)
        {
          assemStr = asseNode.getNodeDepartmentName();
        }
        if(!makeStr.equals("") && !assemStr.equals(""))
          routeStr = makeStr +"="+assemStr;
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
          result = result+routeStr;
        else
          result = result + "；"+routeStr;

        if(result== null ||result.equals(""))
          result = " ";
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return result;
  }

}
