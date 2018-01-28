/**
 * 生成程序 ViewRouteListUtil.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 新增列 unit单位（解放 D31）,partunitversion 零部件版本。 liunan 2013-1-6
 * SS2 修改报表中间表链接。 liunan 2013-1-26
 * SS3 艺准中零部件如果标记的F，则批准后要将该件设置为废弃状态，并发送到汽研中间表中。 liunan 2014-4-20
 * SS4 艺准没有F标件时会有空指针。 liunan 2014-5-1
 * SS5 如果艺毕、艺废的零部件没有发布源版本，则说明不是汽研发布的件，不往汽研发布完毕、作废通知。 liunan 2015-3-17
 * SS6 后台出现异常，进行的修改。 liunan 2015-6-5
 * SS7 添加附件。 liunan 2015-6-18
 * SS8 jdbc链接参数改为从配置文件中获取。 liunan 2015-10-16
 */
package com.faw_qm.cappclients.capproute.web;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.capp.util.*;
import com.faw_qm.codemanage.ejb.entity.Coding;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.clients.common.FormatData;
import com.faw_qm.technics.route.util.RouteHelper;
import com.faw_qm.framework.exceptions.QMException;

//CCBegin SS3
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;
import com.faw_qm.epm.build.model.EPMBuildHistoryIfc;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
//CCEnd SS3

//CCBegin SS7
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ContentHolderIfc;
//CCEnd SS7
/**
 * <p>
 * Title:查看路线表
 * </p>
 * <p>
 * Description:本类提供的方法为廋客户界面使用
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 *
 * @author 刘明
 * @mender skybird
 * @version 1.0
 */

public class ViewRouteListUtil {

    /** 用于代码测试 */
    public static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    public ViewRouteListUtil() {
    }

    /**
     * 得到符合瘦客户端显示必须的内容的集合
     *
     * @param BsoID
     *            路线表的BsoID
     * @return 字符串数组，数组内容为 BsoID,编号,名称,级别,单位,版本,用于产品,说明,生命周期,项目组,资料夹,生命周期状态,
     *         创建者,创建时间
     *
     */
    public static String[] getMyCollection(String bsoID) {
        if (verbose)
            System.out.println("getMyCollection begin...BsoID=" + bsoID);
        bsoID = bsoID.trim();
        //用于返回
        Collection myCollection = new Vector();
        //调用服务，根据工艺卡的BsoID获得其值对象
        try {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) pService
                    .refreshInfo(bsoID);
            String number = routelist.getRouteListNumber();
            String name = routelist.getRouteListName();
            String level = routelist.getRouteListLevel();
            //st skybird 2005.2.25
            String state1 = routelist.getRouteListState();
            //ed
            String department = "";
            String dep = routelist.getDepartmentName();
            if (dep != null) {
                department = dep;
            }

            String version = routelist.getVersionValue();

            QMPartMasterIfc partmaster = (QMPartMasterIfc) pService
                    .refreshInfo(routelist.getProductMasterID());
            String product = partmaster.getPartNumber() + "_"
                    + partmaster.getPartName();

            String description = "";
            String des = routelist.getRouteListDescription();
            if (des != null)
                description = des;

            String lifecycle = "";
            String lc = routelist.getLifeCycleTemplate();
            if (lc != null) {
                LifeCycleTemplateInfo lct = (LifeCycleTemplateInfo) pService
                        .refreshInfo(lc);
                lifecycle = lct.getLifeCycleName();
            }

            String projectName = "";
            ProjectIfc project = null;
            String projectID = ((LifeCycleManagedIfc) routelist).getProjectId();
            if (projectID != null)
                project = (ProjectIfc) pService.refreshInfo(projectID);
            //获得项目组名
            if (project != null)
                //modify by guoxl on 2008.4.10(工作组只显示无类型的标识信息)
                    // projectName = project.getName();
                      projectName = CappServiceHelper.getIdentity(project);
                   //modify by guoxl end


            String location = routelist.getLocation();

            String state = "";
            LifeCycleState st = routelist.getLifeCycleState();
            //获得对象的状态
            if (st != null)
                state = st.getDisplay();

            String creator = "";
            String ic = routelist.getIterationCreator();
            if (ic != null)
                creator = RouteWebHelper.getUserNameByID(ic);

            //获得创建时间
            String createTime = routelist.getCreateTime().toString();
            createTime =  createTime.substring(0,createTime.lastIndexOf("."));
            //构造数组
            String[] myVersionArray1 = { bsoID, number, name, level,
                    department, version, product, description, lifecycle,
                    projectName, location, state, creator, createTime, state1 };
            return myVersionArray1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得路线表主信息对象的bsoID
     *
     * @param bsoID
     *            路线表值对象的bsoID
     * @return 路线表主信息对象的bsoID
     */
    public static String getRouteListMasterID(String bsoID) {
        try {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            TechnicsRouteListInfo info = (TechnicsRouteListInfo) pService
                    .refreshInfo(bsoID);
            return info.getMaster().getBsoID();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
//  CCBegin by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕
    /**
     * 判断是不是艺毕
     *
     * @param bsoID
     *            路线表值对象的bsoID
     * @return 路线表主信息对象的bsoID
     */
    public static boolean isroutecomplete(String bsoID) {
        try {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            TechnicsRouteListInfo info = (TechnicsRouteListInfo) pService
                    .refreshInfo(bsoID);
           if( info.getRouteListState().equals("艺毕")){
        	   return true;
           }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }
//  CCEnd by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕 
//  CCBegin by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕
    /**
     * 判断：路线表是否处于发布状态
     * @param info TechnicsRouteListIfc
     * @return boolean
     */
    public static boolean isReleased(String bsoID)
    {
        try {
        PersistService pService = (PersistService) EJBServiceHelper
        .getService("PersistService");
         TechnicsRouteListInfo info = (TechnicsRouteListInfo) pService
        .refreshInfo(bsoID);
      LifeCycleManagedIfc lcm = (LifeCycleManagedIfc)info;
      if(lcm.getLifeCycleState().toString().equals("RELEASED"))
      {
        return true;
      }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
      return false;
    }
//  CCEnd by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕 
//  CCBegin by leixiao 2009-4-1 原因：解放升级工艺路线,搜索"艺准通知书"时添加“类别”  
    /**
     * 获得所有艺准类别
     *
     * @param  
     *            
     * @return 艺准类别
     */
	public static Collection getRouteListStateSet() {
      // System.out.println("getRouteListStateSet()");
		Collection result =null;
		ArrayList list = new ArrayList();
        try {
        	CodingManageService codingManageService = (CodingManageService) EJBServiceHelper
            .getService("CodingManageService");
        	result = (Collection) codingManageService.findDirectClassificationByName("状态", "工艺路线表" );
        	//System.out.println(""+result);
        

        } catch (Exception ex) {
            //输出异常信息：
            ex.printStackTrace();

        }
        Iterator i=result.iterator();
        while(i.hasNext()){
        	String type=(i.next()).toString();
            list.add(new FormatData(type,type));
        }
		return list;
	}
//  CCEnd by leixiao 2009-4-1 原因：解放升级工艺路线,增加"艺准通知书添加"按钮  	
//  CCBegin by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕放艺准里，修改流程	
	/**
	 * 艺毕通知书流程经过批准后要将其相关的零部件
	 * 
	 * @param completeListID
	 */
	public void changeCompletePartState(String routeListID) {

		try {
			RouteHelper.getRouteService().setRouteCompletePartsState(routeListID);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//  CCEnd by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕放艺准里，修改流程
	
//  CCBegin by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕
	  public boolean  printFile(String routeListBsoID,String number){
		 // System.out.print("进入printFile-----------------");
		    Connection conn = null;
		    Statement stmt =null;
		    ResultSet rs=null;
		    //CCBegin by liunan 2011-09-21 新增废弃也一起保存。
		    //String sqlBase = "INSERT INTO routecompletepart (routelistnumber,partnumber,partversion,createtime) VALUES ";
		    //CCBegin SS1
		    //String sqlBase = "INSERT INTO routecompletepart (routelistnumber,partnumber,partversion,createtime,routelisttype) VALUES ";
		    String sqlBase = "INSERT INTO routecompletepart (routelistnumber,partnumber,partversion,createtime,routelisttype,unit,partunitversion) VALUES ";
		    //CCEnd SS1
		    //CCEnd by liunan 2011-09-21
		    int count = 0;//CCBegin by liunan 2012-03-28 查看艺毕发布数量。
		  try{
				Collection part= RouteHelper.getRouteService().getcompleteroutepart(routeListBsoID);
				 Iterator i=part.iterator();
			    	conn = getConnection();
			    	stmt = conn.createStatement();
			   System.out.println(number+" 发布的零部件个数为： "+part.size());//CCBegin by liunan 2012-03-28 查看艺毕发布数量。
				 while(i.hasNext()){
					 String partandversion[]=(String[])i.next();
					 String routelistnumber=number;
                     String partnumber=partandversion[0];
                     String partversion=partandversion[1];
                     //CCBegin SS1
                     String partunitversion=partandversion[2];
                     //CCEnd SS1
				//CCBegin SS5
				if(partversion==null||partversion.equals(""))
				{
					continue;
				}
				//CCEnd SS5
                     String   date=new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new   Date()); 
                     //CCBegin by liunan 2011-09-21 新增废弃也一起保存。
                     //String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"',to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'))";
                     //CCBegin SS1
                     //String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"',to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'),'W')";
                     String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"',to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'),'W','D31','"+partunitversion+"')";
                     //CCEnd SS1
                     //CCEnd by liunan 2011-09-21
                    // String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"','to_date("+"'"+d+"','yyyy-MM-dd   HH:mm:ss')"+date+"')";
               	  	rs = stmt.executeQuery(listSql);
               	 rs.next();
               	 count++;//CCBegin by liunan 2012-03-28 查看艺毕发布数量。
				 }
			  	  rs.close();
			  	  //关闭Statement
			  	  stmt.close();
			  	  //关闭数据库连接
			      conn.close();
		  }
		  catch(Exception e){
			  e.printStackTrace();
			  return false;
		  }

		    finally
		    {
		    	System.out.println("成功发布个数："+count);
		        try
		        {
		            if (rs != null)
		            {
		                rs.close();
		            }
		            if (stmt != null)
		            {
		                stmt.close();
		            }
		            if (conn != null)
		            {
		                conn.close();
		            }
		        }
		        catch (SQLException ex1)
		        {
		            ex1.printStackTrace();
		        }

		        
		    }
		    return true;
	  }
	  public boolean  printFile1(String routeListBsoID,String number){
//		  path="D:\\";
	       String qm_HOME = RemoteProperty.getProperty("qm.home", "c:" + File.separator + "lifecycle");
	       String filedir = RemoteProperty.getProperty("xmlFileDir", qm_HOME + File.separator + "ejbjar" + File.separator + "webapp" + File.separator+"completexml"+File.separator);
	      String path=filedir+number+".xml";
		  try{
			Collection part= RouteHelper.getRouteService().getcompleteroutepart(routeListBsoID);
		 StringBuffer returnStr =new  StringBuffer();
		 returnStr.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
		 returnStr.append("\n");
		 returnStr.append("<routecomplete>");
		 Iterator i=part.iterator();
		 while(i.hasNext()){
			 String partandversion[]=(String[])i.next();
			 returnStr.append("\n");
			 returnStr.append("<part>"); 
			 returnStr.append("\n");
			 returnStr.append("<number>");
			 returnStr.append(partandversion[0]);
			 returnStr.append("</number>");
			 returnStr.append("\n");
			 returnStr.append("<version>");
			 returnStr.append(partandversion[1]);
			 returnStr.append("</version>");
			 returnStr.append("\n");
			 returnStr.append("</part>"); 
		 }
		 returnStr.append("\n");
		 returnStr.append("</routecomplete>");


	      FileWriter fw = new FileWriter(path, false);
	      fw.write(returnStr.toString());     
	      fw.close();
		  }catch(Exception e){
			  e.printStackTrace();
			  return false;
		  }
		  return true;
	  }


	  public Connection getConnection() throws SQLException{
	    //CCBegin SS8 
	       /* Connection conn=null;
        //CCBegin SS2
        //String url = "jdbc:oracle:thin:@10.60.15.193:1521:wind";
        //String url = "jdbc:oracle:thin:@10.43.4.123:1521:pdmtest";
        String url = "jdbc:oracle:thin:@10.60.15.241:1521:wind";
        try
        {
    Class.forName("oracle.jdbc.driver.OracleDriver");
    //conn=DriverManager.getConnection(url,"externaluser","external");
    conn=DriverManager.getConnection(url,"intext","int1509");
        //CCEnd SS2

       }
        catch(Exception e) {
      throw new SQLException("不能获取连接");
        }

        return conn;*/
        
	    String url = RemoteProperty.getProperty("yqqy.DB.url", "");
		  String user = RemoteProperty.getProperty("yqqy.DB.user", ""); 
		  String password = RemoteProperty.getProperty("yqqy.DB.password", "");
		  System.out.println("ViewRouteListUtil   getConnection  url=="+url);
		  System.out.println("ViewRouteListUtil   getConnection  user=="+user);
		  return DriverManager.getConnection(url, user, password);
		  //CCEnd SS8
	  }
	  
	  
//	  CCEnd by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕


	  //CCBegin by liunan 2011-09-21 艺废通知书。
	  	/**
	 * 艺毕通知书流程经过批准后要将其相关的零部件
	 * 
	 * @param completeListID
	 */
	public void changeDisaffirmPartState(String routeListID) {
		try {
			RouteHelper.getRouteService().setRouteDisaffirmPartsState(routeListID);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
    public boolean  printFileDisaffirm(String routeListBsoID,String number)
    {
		    Connection conn = null;
		    Statement stmt =null;
		    ResultSet rs=null;
		    //CCBegin SS1
		    //String sqlBase = "INSERT INTO routecompletepart (routelistnumber,partnumber,partversion,createtime,routelisttype) VALUES ";
		    String sqlBase = "INSERT INTO routecompletepart (routelistnumber,partnumber,partversion,createtime,routelisttype,unit,partunitversion) VALUES ";
		    //CCEnd SS1
		    int count = 0;//CCBegin by liunan 2012-03-28 查看艺废发布数量。
		  try{
				Collection part= RouteHelper.getRouteService().getcompleteroutepart(routeListBsoID);
			   System.out.println(number+" 发布的零部件个数为： "+part.size());//CCBegin by liunan 2012-03-28 查看艺废发布数量。
				 Iterator i=part.iterator();
			    	conn = getConnection();
			    	stmt = conn.createStatement();
				 while(i.hasNext()){
					 String partandversion[]=(String[])i.next();
					 String routelistnumber=number;
                     String partnumber=partandversion[0];
                     String partversion=partandversion[1];
                     //CCBegin SS1
                     String partunitversion=partandversion[2];
                     //CCEnd SS1
				//CCBegin SS5
				if(partversion==null||partversion.equals(""))
				{
					continue;
				}
				//CCEnd SS5
                     String   date=new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new   Date()); 
                     //CCBegin SS1
                     //String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"',to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'),'F')";
                     String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"',to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'),'F','D31','"+partunitversion+"')";
                     //CCEnd SS1
                    // String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"','to_date("+"'"+d+"','yyyy-MM-dd   HH:mm:ss')"+date+"')";
               	  	System.out.println("listSql================="+listSql);
               	  	rs = stmt.executeQuery(listSql);
               	 rs.next();
               	 count++;//CCBegin by liunan 2012-03-28 查看艺废发布数量。
				 }
			  	  rs.close();
			  	  //关闭Statement
			  	  stmt.close();
			  	  //关闭数据库连接
			      conn.close();
		  }
		  catch(Exception e){
			  e.printStackTrace();
			  return false;
		  }

		    finally
		    {
		    	System.out.println("成功发布个数："+count);//CCBegin by liunan 2012-03-28 查看艺废发布数量。
		        try
		        {
		            if (rs != null)
		            {
		                rs.close();
		            }
		            if (stmt != null)
		            {
		                stmt.close();
		            }
		            if (conn != null)
		            {
		                conn.close();
		            }
		        }
		        catch (SQLException ex1)
		        {
		            ex1.printStackTrace();
		        }

		        
		    }
		    return true;
	  }
	  //CCEnd by liunan 2011-09-21
	  
	  //CCBegin by liunan 2011-09-28
	      /**
     * 判断是艺毕、废弃、还是其他。
     *
     * @param bsoID
     *            路线表值对象的bsoID
     * @return 路线表主信息对象的bsoID
     */
    public static String isroutecompleteordisaffirm(String bsoID) {
        try {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            TechnicsRouteListInfo info = (TechnicsRouteListInfo) pService
                    .refreshInfo(bsoID);
           if( info.getRouteListState().equals("艺毕")){
        	   return "WB";
           }
           else if( info.getRouteListState().equals("艺废")){
        	   return "FQ";
           }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return "QT";
        }
        return "QT";
    }
    //CCEnd by liunan 2011-09-28
    
    
  //CCBegin SS3
  /**
	 * 艺准通知书流程经过批准后要将F标的零部件，设置成已废弃状态
	 */
	public void changePartStateByF(String routeListID)
	{
		try
		{
			if(routeListID==null || routeListID.equals(""))
			{
				throw new QMException("routeListID为空，无法为艺准设置已废弃状态！");
			}
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");
			LifeCycleTemplateInfo lct = lservice.getLifeCycleTemplate("无流程类零部件生命周期");
			
			//CCBegin by liunan 2012-05-29 获得该件的EPM文档，并设置状态。
			LifeCycleTemplateInfo lctEpm = lservice.getLifeCycleTemplate("图档生命周期");
			//CCEnd by liunan 2012-05-29
			
			
			Collection coll = getRouteListLinkPartsByF(routeListID);
			System.out.println("coll============="+coll);
			for (Iterator iter = coll.iterator(); iter.hasNext(); )
			{
				QMPartIfc  partInfo = (QMPartIfc)iter.next();
				partInfo = (QMPartIfc) ps.refreshInfo(partInfo.getBsoID());
				System.out.println("partInfo============="+partInfo);
				if(partInfo!=null)
				{
					lservice.reassign(partInfo, lct);
					lservice.setLifeCycleState(partInfo, LifeCycleState.toLifeCycleState("DISAFFIRM"));
				}
				
				//CCBegin by liunan 2012-05-29 获得该件的EPM文档，并设置状态。
				QMQuery query = new QMQuery("EPMBuildHistory");
				QueryCondition condition = new QueryCondition("rightBsoID","=",partInfo.getBsoID());
				query.addCondition(condition);
				Collection coll1 = ps.findValueInfo(query);
				Iterator iter1 = coll1.iterator();
				if(iter1.hasNext())
				{
					EPMBuildHistoryIfc link = (EPMBuildHistoryIfc)iter1.next();
					EPMDocumentIfc doc = (EPMDocumentIfc)link.getBuiltBy();
					if(doc!=null)
					{
						lservice.reassign(doc, lctEpm);
						lservice.setLifeCycleState(doc, LifeCycleState.toLifeCycleState("DISAFFIRM"));
					}
				}
				//CCEnd by liunan 2012-05-29
			}
		}
		catch (QMException e)
		{
			e.printStackTrace();
		}
	}
	
	
  /**
	 * 艺准通知书流程经过批准后要将F标的零部件，发动到汽研中间表中
	 */
	public boolean printPartDisaffirmByF(String routeListBsoID,String number)
	{
		Connection conn = null;
		Statement stmt =null;
		ResultSet rs=null;
		//CCBegin SS1
		//String sqlBase = "INSERT INTO routecompletepart (routelistnumber,partnumber,partversion,createtime,routelisttype) VALUES ";
		String sqlBase = "INSERT INTO routecompletepart (routelistnumber,partnumber,partversion,createtime,routelisttype,unit,partunitversion) VALUES ";
		//CCEnd SS1
		int count = 0;//CCBegin by liunan 2012-03-28 查看艺废发布数量。
		try
		{
			Collection part= getRouteListLinkPartsAttByF(routeListBsoID);
			System.out.println(number+" 发布的零部件个数为： "+part.size());//CCBegin by liunan 2012-03-28 查看艺废发布数量。
			Iterator i=part.iterator();
			conn = getConnection();
			stmt = conn.createStatement();
			while(i.hasNext())
			{
				String partandversion[]=(String[])i.next();
				String routelistnumber=number;
				String partnumber=partandversion[0];
				String partversion=partandversion[1];
				//CCBegin SS1
				String partunitversion=partandversion[2];
				//CCEnd SS1
				//CCBegin SS5
				if(partversion==null||partversion.equals(""))
				{
					continue;
				}
				//CCEnd SS5
				String   date=new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); 
				//CCBegin SS1
				//String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"',to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'),'F')";
				String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"',to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'),'F','D31','"+partunitversion+"')";
				//CCEnd SS1
				// String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"','to_date("+"'"+d+"','yyyy-MM-dd   HH:mm:ss')"+date+"')";
				System.out.println("listSql================="+listSql);
				rs = stmt.executeQuery(listSql);
				rs.next();
				count++;//CCBegin by liunan 2012-03-28 查看艺废发布数量。
			}
			//CCBegin SS4
			//rs.close();
			//关闭Statement
			//stmt.close();
			//关闭数据库连接
			//conn.close();
			//CCEnd SS4
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			System.out.println("成功发布个数："+count);//CCBegin by liunan 2012-03-28 查看艺废发布数量。
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (stmt != null)
				{
					stmt.close();
				}
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return true;
	}
	  
	  

  /**
   * 获得与路线表相关的零部件中，标记为F的零部件。
   * @param routeListInfo TechnicsRouteListIfc 路线表值对象
   * @throws QMException
   * @return Collection 存储ListRoutePartLinkIfc：关联值对象集合。
   * @see TechnicsRouteListInfo
   */
  public Collection getRouteListLinkPartsByF(String routeListBsoID) throws QMException
  {
    PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
    
    QMQuery query = new QMQuery("ListRoutePartLink");
    QueryCondition cond = new QueryCondition("leftBsoID", QueryCondition.EQUAL,routeListBsoID);
    query.addCondition(cond);
    query.addAND();
    //有可能零件未使用路线。
    QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL,2);
    query.addCondition(cond1);
    Collection coll = pservice.findValueInfo(query,false);
    Collection result = new Vector();
    for (Iterator iter = coll.iterator(); iter.hasNext(); )
    {
    	ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
    	//CCBegin SS6
    	if(listLinkInfo.getRouteID()==null)
    	{
    		continue;
    	}
    	//CCEnd SS6
    	TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) pservice.refreshInfo(listLinkInfo.getRouteID(),false);
    	if(routeInfo.getModefyIdenty().getCodeContent().equals("废弃"))
    	{
				result.add(listLinkInfo.getPartBranchInfo());
			}
		}
    return result;
  }
  
  
  /**
   * 获得与路线表相关的零部件中，标记为F的零部件。
   * @param routeListInfo TechnicsRouteListIfc 路线表值对象
   * @throws QMException
   * @return Collection 存储ListRoutePartLinkIfc：关联值对象集合。
   * @see TechnicsRouteListInfo
   */
  public Collection getRouteListLinkPartsAttByF(String routeListBsoID) throws QMException
  {
    PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
    
    QMQuery query = new QMQuery("ListRoutePartLink");
    QueryCondition cond = new QueryCondition("leftBsoID", QueryCondition.EQUAL,routeListBsoID);
    query.addCondition(cond);
    query.addAND();
    //有可能零件未使用路线。
    QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL,2);
    query.addCondition(cond1);
    Collection coll = pservice.findValueInfo(query,false);
    ArrayList completepart=new ArrayList();
    String pversion="";
    String pversionvalue="";
    for (Iterator iter = coll.iterator(); iter.hasNext(); )
    {
    	ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
    	//CCBegin SS6
    	if(listLinkInfo.getRouteID()==null)
    	{
    		continue;
    	}
    	//CCEnd SS6
    	TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) pservice.refreshInfo(listLinkInfo.getRouteID(),false);
    	if(routeInfo.getModefyIdenty().getCodeContent().equals("废弃"))
    	{
    		if(pversion.equals(""))
    		{
    			QMQuery query1 = new QMQuery("StringDefinition");
    			QueryCondition qc = new QueryCondition("displayName", " = ", "发布源版本");
    			query1.addCondition(qc);
    			Collection col = pservice.findValueInfo(query1, false);
    			Iterator iba = col.iterator();
    			if (iba.hasNext())
    			{
    				StringDefinitionIfc s = (StringDefinitionIfc) iba.next();
    				pversion = s.getBsoID();
    			}
    		}
    		QMPartIfc part = listLinkInfo.getPartBranchInfo();
			  String partnumber = part.getPartNumber();
				QMQuery query2 = new QMQuery("StringValue");
				query2.addCondition(new QueryCondition("iBAHolderBsoID", "=", part.getBsoID()));
				query2.addAND();
				query2.addCondition(new QueryCondition("definitionBsoID", "=", pversion));
				Collection ibavalue = pservice.findValueInfo(query2, false);
				Iterator value = ibavalue.iterator();
				if(value.hasNext())
				{
					StringValueIfc s=(StringValueIfc)value.next();
					pversionvalue=s.getValue();
				}
				if(pversionvalue!=null&&!pversionvalue.trim().equals(""))
				{
					//CCBegin SS2
					//String [] partvalue={partnumber,pversionvalue};
					String [] partvalue={partnumber,pversionvalue,part.getVersionValue()};
					//CCEnd SS2
					completepart.add(partvalue);
				}
			}
		}
    return completepart;
  }
    //CCEnd SS3
    
  //CCBegin SS7
    /**
     * 得到指定工艺内容容器中指定的数据项
     * @param priInfo QMEquipmentInfo 内容容器
     * @return Vector ApplicationDataInfo 内容项
     * @throws RationException 
     */
    public Vector getContents(String id)throws QMException
    {
    	PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
      TechnicsRouteListIfc partIfc = (TechnicsRouteListIfc)pService.refreshInfo(id);
    	Vector c = null;
    	try
    	{
    		ContentService contentService = (ContentService)EJBServiceHelper.getService("ContentService");
    		c = (Vector)contentService.getContents((ContentHolderIfc)partIfc);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
		  return c;
    }
  //CCEnd SS7
}
