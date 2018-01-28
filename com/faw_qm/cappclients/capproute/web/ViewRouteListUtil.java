/**
 * ���ɳ��� ViewRouteListUtil.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ������ unit��λ����� D31��,partunitversion �㲿���汾�� liunan 2013-1-6
 * SS2 �޸ı����м�����ӡ� liunan 2013-1-26
 * SS3 ��׼���㲿�������ǵ�F������׼��Ҫ���ü�����Ϊ����״̬�������͵������м���С� liunan 2014-4-20
 * SS4 ��׼û��F���ʱ���п�ָ�롣 liunan 2014-5-1
 * SS5 ����ձϡ��շϵ��㲿��û�з���Դ�汾����˵���������з����ļ����������з�����ϡ�����֪ͨ�� liunan 2015-3-17
 * SS6 ��̨�����쳣�����е��޸ġ� liunan 2015-6-5
 * SS7 ��Ӹ����� liunan 2015-6-18
 * SS8 jdbc���Ӳ�����Ϊ�������ļ��л�ȡ�� liunan 2015-10-16
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
 * Title:�鿴·�߱�
 * </p>
 * <p>
 * Description:�����ṩ�ķ���Ϊ�C�ͻ�����ʹ��
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @mender skybird
 * @version 1.0
 */

public class ViewRouteListUtil {

    /** ���ڴ������ */
    public static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    public ViewRouteListUtil() {
    }

    /**
     * �õ������ݿͻ�����ʾ��������ݵļ���
     *
     * @param BsoID
     *            ·�߱��BsoID
     * @return �ַ������飬��������Ϊ BsoID,���,����,����,��λ,�汾,���ڲ�Ʒ,˵��,��������,��Ŀ��,���ϼ�,��������״̬,
     *         ������,����ʱ��
     *
     */
    public static String[] getMyCollection(String bsoID) {
        if (verbose)
            System.out.println("getMyCollection begin...BsoID=" + bsoID);
        bsoID = bsoID.trim();
        //���ڷ���
        Collection myCollection = new Vector();
        //���÷��񣬸��ݹ��տ���BsoID�����ֵ����
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
            //�����Ŀ����
            if (project != null)
                //modify by guoxl on 2008.4.10(������ֻ��ʾ�����͵ı�ʶ��Ϣ)
                    // projectName = project.getName();
                      projectName = CappServiceHelper.getIdentity(project);
                   //modify by guoxl end


            String location = routelist.getLocation();

            String state = "";
            LifeCycleState st = routelist.getLifeCycleState();
            //��ö����״̬
            if (st != null)
                state = st.getDisplay();

            String creator = "";
            String ic = routelist.getIterationCreator();
            if (ic != null)
                creator = RouteWebHelper.getUserNameByID(ic);

            //��ô���ʱ��
            String createTime = routelist.getCreateTime().toString();
            createTime =  createTime.substring(0,createTime.lastIndexOf("."));
            //��������
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
     * ���·�߱�����Ϣ�����bsoID
     *
     * @param bsoID
     *            ·�߱�ֵ�����bsoID
     * @return ·�߱�����Ϣ�����bsoID
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
    
//  CCBegin by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձ�
    /**
     * �ж��ǲ����ձ�
     *
     * @param bsoID
     *            ·�߱�ֵ�����bsoID
     * @return ·�߱�����Ϣ�����bsoID
     */
    public static boolean isroutecomplete(String bsoID) {
        try {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            TechnicsRouteListInfo info = (TechnicsRouteListInfo) pService
                    .refreshInfo(bsoID);
           if( info.getRouteListState().equals("�ձ�")){
        	   return true;
           }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }
//  CCEnd by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձ� 
//  CCBegin by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձ�
    /**
     * �жϣ�·�߱��Ƿ��ڷ���״̬
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
//  CCEnd by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձ� 
//  CCBegin by leixiao 2009-4-1 ԭ�򣺽����������·��,����"��׼֪ͨ��"ʱ��ӡ����  
    /**
     * ���������׼���
     *
     * @param  
     *            
     * @return ��׼���
     */
	public static Collection getRouteListStateSet() {
      // System.out.println("getRouteListStateSet()");
		Collection result =null;
		ArrayList list = new ArrayList();
        try {
        	CodingManageService codingManageService = (CodingManageService) EJBServiceHelper
            .getService("CodingManageService");
        	result = (Collection) codingManageService.findDirectClassificationByName("״̬", "����·�߱�" );
        	//System.out.println(""+result);
        

        } catch (Exception ex) {
            //����쳣��Ϣ��
            ex.printStackTrace();

        }
        Iterator i=result.iterator();
        while(i.hasNext()){
        	String type=(i.next()).toString();
            list.add(new FormatData(type,type));
        }
		return list;
	}
//  CCEnd by leixiao 2009-4-1 ԭ�򣺽����������·��,����"��׼֪ͨ�����"��ť  	
//  CCBegin by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձϷ���׼��޸�����	
	/**
	 * �ձ�֪ͨ�����̾�����׼��Ҫ������ص��㲿��
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
//  CCEnd by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձϷ���׼��޸�����
	
//  CCBegin by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձ�
	  public boolean  printFile(String routeListBsoID,String number){
		 // System.out.print("����printFile-----------------");
		    Connection conn = null;
		    Statement stmt =null;
		    ResultSet rs=null;
		    //CCBegin by liunan 2011-09-21 ��������Ҳһ�𱣴档
		    //String sqlBase = "INSERT INTO routecompletepart (routelistnumber,partnumber,partversion,createtime) VALUES ";
		    //CCBegin SS1
		    //String sqlBase = "INSERT INTO routecompletepart (routelistnumber,partnumber,partversion,createtime,routelisttype) VALUES ";
		    String sqlBase = "INSERT INTO routecompletepart (routelistnumber,partnumber,partversion,createtime,routelisttype,unit,partunitversion) VALUES ";
		    //CCEnd SS1
		    //CCEnd by liunan 2011-09-21
		    int count = 0;//CCBegin by liunan 2012-03-28 �鿴�ձϷ���������
		  try{
				Collection part= RouteHelper.getRouteService().getcompleteroutepart(routeListBsoID);
				 Iterator i=part.iterator();
			    	conn = getConnection();
			    	stmt = conn.createStatement();
			   System.out.println(number+" �������㲿������Ϊ�� "+part.size());//CCBegin by liunan 2012-03-28 �鿴�ձϷ���������
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
                     //CCBegin by liunan 2011-09-21 ��������Ҳһ�𱣴档
                     //String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"',to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'))";
                     //CCBegin SS1
                     //String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"',to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'),'W')";
                     String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"',to_date('"+date+"','yyyy-mm-dd hh24:mi:ss'),'W','D31','"+partunitversion+"')";
                     //CCEnd SS1
                     //CCEnd by liunan 2011-09-21
                    // String listSql=sqlBase+"('"+routelistnumber+"','"+partnumber+"','"+partversion+"','to_date("+"'"+d+"','yyyy-MM-dd   HH:mm:ss')"+date+"')";
               	  	rs = stmt.executeQuery(listSql);
               	 rs.next();
               	 count++;//CCBegin by liunan 2012-03-28 �鿴�ձϷ���������
				 }
			  	  rs.close();
			  	  //�ر�Statement
			  	  stmt.close();
			  	  //�ر����ݿ�����
			      conn.close();
		  }
		  catch(Exception e){
			  e.printStackTrace();
			  return false;
		  }

		    finally
		    {
		    	System.out.println("�ɹ�����������"+count);
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
      throw new SQLException("���ܻ�ȡ����");
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
	  
	  
//	  CCEnd by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձ�


	  //CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
	  	/**
	 * �ձ�֪ͨ�����̾�����׼��Ҫ������ص��㲿��
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
		    int count = 0;//CCBegin by liunan 2012-03-28 �鿴�շϷ���������
		  try{
				Collection part= RouteHelper.getRouteService().getcompleteroutepart(routeListBsoID);
			   System.out.println(number+" �������㲿������Ϊ�� "+part.size());//CCBegin by liunan 2012-03-28 �鿴�շϷ���������
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
               	 count++;//CCBegin by liunan 2012-03-28 �鿴�շϷ���������
				 }
			  	  rs.close();
			  	  //�ر�Statement
			  	  stmt.close();
			  	  //�ر����ݿ�����
			      conn.close();
		  }
		  catch(Exception e){
			  e.printStackTrace();
			  return false;
		  }

		    finally
		    {
		    	System.out.println("�ɹ�����������"+count);//CCBegin by liunan 2012-03-28 �鿴�շϷ���������
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
     * �ж����ձϡ�����������������
     *
     * @param bsoID
     *            ·�߱�ֵ�����bsoID
     * @return ·�߱�����Ϣ�����bsoID
     */
    public static String isroutecompleteordisaffirm(String bsoID) {
        try {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            TechnicsRouteListInfo info = (TechnicsRouteListInfo) pService
                    .refreshInfo(bsoID);
           if( info.getRouteListState().equals("�ձ�")){
        	   return "WB";
           }
           else if( info.getRouteListState().equals("�շ�")){
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
	 * ��׼֪ͨ�����̾�����׼��Ҫ��F����㲿�������ó��ѷ���״̬
	 */
	public void changePartStateByF(String routeListID)
	{
		try
		{
			if(routeListID==null || routeListID.equals(""))
			{
				throw new QMException("routeListIDΪ�գ��޷�Ϊ��׼�����ѷ���״̬��");
			}
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");
			LifeCycleTemplateInfo lct = lservice.getLifeCycleTemplate("���������㲿����������");
			
			//CCBegin by liunan 2012-05-29 ��øü���EPM�ĵ���������״̬��
			LifeCycleTemplateInfo lctEpm = lservice.getLifeCycleTemplate("ͼ����������");
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
				
				//CCBegin by liunan 2012-05-29 ��øü���EPM�ĵ���������״̬��
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
	 * ��׼֪ͨ�����̾�����׼��Ҫ��F����㲿���������������м����
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
		int count = 0;//CCBegin by liunan 2012-03-28 �鿴�շϷ���������
		try
		{
			Collection part= getRouteListLinkPartsAttByF(routeListBsoID);
			System.out.println(number+" �������㲿������Ϊ�� "+part.size());//CCBegin by liunan 2012-03-28 �鿴�շϷ���������
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
				count++;//CCBegin by liunan 2012-03-28 �鿴�շϷ���������
			}
			//CCBegin SS4
			//rs.close();
			//�ر�Statement
			//stmt.close();
			//�ر����ݿ�����
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
			System.out.println("�ɹ�����������"+count);//CCBegin by liunan 2012-03-28 �鿴�շϷ���������
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
   * �����·�߱���ص��㲿���У����ΪF���㲿����
   * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ����
   * @throws QMException
   * @return Collection �洢ListRoutePartLinkIfc������ֵ���󼯺ϡ�
   * @see TechnicsRouteListInfo
   */
  public Collection getRouteListLinkPartsByF(String routeListBsoID) throws QMException
  {
    PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
    
    QMQuery query = new QMQuery("ListRoutePartLink");
    QueryCondition cond = new QueryCondition("leftBsoID", QueryCondition.EQUAL,routeListBsoID);
    query.addCondition(cond);
    query.addAND();
    //�п������δʹ��·�ߡ�
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
    	if(routeInfo.getModefyIdenty().getCodeContent().equals("����"))
    	{
				result.add(listLinkInfo.getPartBranchInfo());
			}
		}
    return result;
  }
  
  
  /**
   * �����·�߱���ص��㲿���У����ΪF���㲿����
   * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ����
   * @throws QMException
   * @return Collection �洢ListRoutePartLinkIfc������ֵ���󼯺ϡ�
   * @see TechnicsRouteListInfo
   */
  public Collection getRouteListLinkPartsAttByF(String routeListBsoID) throws QMException
  {
    PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
    
    QMQuery query = new QMQuery("ListRoutePartLink");
    QueryCondition cond = new QueryCondition("leftBsoID", QueryCondition.EQUAL,routeListBsoID);
    query.addCondition(cond);
    query.addAND();
    //�п������δʹ��·�ߡ�
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
    	if(routeInfo.getModefyIdenty().getCodeContent().equals("����"))
    	{
    		if(pversion.equals(""))
    		{
    			QMQuery query1 = new QMQuery("StringDefinition");
    			QueryCondition qc = new QueryCondition("displayName", " = ", "����Դ�汾");
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
     * �õ�ָ����������������ָ����������
     * @param priInfo QMEquipmentInfo ��������
     * @return Vector ApplicationDataInfo ������
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
