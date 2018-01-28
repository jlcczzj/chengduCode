package com.faw_qm.jfpublish.receive;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.faw_qm.borrow.model.BorrowInfo;
import com.faw_qm.borrow.model.BorrowObjectLinkIfc;
import com.faw_qm.cappclients.capproute.web.DownloadExcelManager;
import com.faw_qm.content.ejb.service.ContentHolder;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.StreamDataInfo;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.model.ContentItemInfo;
import com.faw_qm.content.model.ContentItemIfc;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.content.util.StreamUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.util.DocFormData;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.IteratorVector;
import com.faw_qm.iba.definition.litedefinition.StringDefView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.integration.model.Att;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.ejb.enterpriseService.EnterprisePartService;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.project.util.RolePrincipalTable;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.workflow.engine.ejb.service.WfEngineService;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.workflow.engine.util.enumerate.WfState;
import com.faw_qm.workflow.engine.util.enumerate.WfTransition;

/**
 * <p>Title: 发布解放系统数据到青岛。</p>
 * <p>Description: 根据技术中心的采用、变更、解放艺准还有补发方式，将零部件、bom和pdf附件发布过去。
 * 如：P25—JH6、P62—J6L、P63—J6M、PK—JL01
 * 新增P26</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: 启明</p>
 * @author 刘楠
 * @version 1.0
 * 2016.4.21修改：
 * 1、文件名不能有中文
 * 2、导入文件的编号名称不能有中英文逗号
 * 3、多图显示“多个图纸”
 * 4、采用确认通知书文档  的说明 显示 车型+【采用确认书编号】
 * 2016-11-30
 * 1.D-QZ4250P26K24T3E4-1-6.csv 增加2列，零部件版本，“艺准、更改、采用”通知文档的解放URL地址。
 * 2.R-QZ4250P26K24T3E4-1-6.csv 最后一列增加“零件版本（解放版本）”
 * 3.U-QZ4250P26K24T3E4-1-6.csv 最后一列增加“父件版本(解放版本)”
 * 更改和采用通知文档，说明的属性里不写车型号了，把更改和采用的说明属性设置空字符串就行
 * 2016-12-1
 * 1.艺准不发取消路线的零部件。
 * 2.艺准通知单，更改、采用单中发布解放系统当时时间节点的最新版本（解决同一个数据载体发布同一零件的不同版本）。
 * 3.发往青岛的数据，保证一个发布过程不包含同一个零件的不同版本数据。
 * 4.修改原有逻辑：如果发布数据载体（采用单、变更单）中包含的零件，有一个不符合发给青汽的规则，整个数据载体关联的零件都不发。
 *   修改后逻辑：检索发布数据载体数据，只发布其中符合发给青规则的数据，不符合规则数据自动过滤掉，不发给青岛。（不整体判断）
 */
public class QQPartPublish
{
	public static final boolean VERBOSE = true;
	static boolean fileVaultUsed = (RemoteProperty.getProperty("registryFileVaultStoreMode", "true")).equals("true");
  static String qqPublishPath = RemoteProperty.getProperty("qqPublishPath");
  static String ftpHost = RemoteProperty.getProperty("qq.ftp.url","");
  static int ftpPort = Integer.parseInt(RemoteProperty.getProperty("qq.ftp.port","21"));
  static String ftpUserName = RemoteProperty.getProperty("qq.ftp.user","");
  static String ftpPassword = RemoteProperty.getProperty("qq.ftp.password","");
  static String ftpPath = RemoteProperty.getProperty("qq.ftp.path","");
  private static long filesize = 0;
  
  
	public void QQPartPublish()
	{
	}
  
  
  /**
   * 获取汽研中间表的反馈流程信息。
   */
  public void checkTableData() throws QMException
  {
  	System.out.println("青汽发布监控任务开始>........时间["+ new java.util.Date().toLocaleString()+ "]");
  	Connection conn = null;
  	Statement stmt =null;
  	ResultSet rs=null;
    try
    {
  		conn = PersistUtil.getConnection();
  		stmt = conn.createStatement();
  		
  		String transsql = "select count(*) from qqpublishmonitor where transflag='true'";
  		String datasql = "select objectid,EXT1 from qqpublishmonitor where overflag='false' and (EXT1 is null or EXT1!='3') order by pdate";

      rs = stmt.executeQuery(transsql);
      rs.next();
      int countId = rs.getInt(1);
      if (countId == 0)
      {
        System.out.println("没有正在发送的发布进程，准备搜索需要发布的任务！！");
        rs = stmt.executeQuery(datasql);
        if (rs.next())
        {
        	String publishcount = "";
        	if(rs.getString(2)==null||rs.getString(2).equals(""))
        	{
        		publishcount = "1";
        	}
        	else
        	{
        		publishcount = Integer.toString(Integer.parseInt(rs.getString(2))+1);
        	}
          System.out.println("得到需要发布的数据！");
          testPublish(rs.getString(1), publishcount);
        }
        else
        {
        	System.out.println("没有得到需要发布的数据！");
        }
      }
      else if (countId == 1)
      {
        System.out.println("有正在发送的发布进程，检查发布用时！");
        rs = stmt.executeQuery("select transdatetime from qqpublishmonitor where transflag='true'");
        rs.next();
        int td = Integer.parseInt(rs.getString(1));//发送任务开始时间
        Calendar calendar = Calendar.getInstance();
        int cd = calendar.get(Calendar.HOUR_OF_DAY);//当前时间
        //如果传输任务未到2个小时，则结束监控，等待下次处理。
        System.out.println(cd+":::"+td);
        int hour = 0;
        if(cd<td)
        {
        	hour = 24+cd-td;
        }
        else
        {
        	hour = cd-td;
        }
        System.out.println("hour=="+hour);
        if(hour<2)
        {
        	System.out.println("用时正常，结束此次监控！！");
        }
        //如果传输任务已经执行2个小时，则重置。
        else
        {
        	publishInit();
        	System.out.println("用时超时，重置发布内容！！");
        }
      }
      else
      {
        System.out.println("有多个正在发送的发布进程，不对了！！");
      }

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
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
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
		System.out.println("青汽发布监控任务结束!");
  }
  
  
	public void publishInit()
	{
		System.out.println("重置 transflag 标记， 开始！！");
		
		Connection conn = null;
		Statement stmt =null;
		ResultSet rs=null;
		String updatesql = "update qqpublishmonitor set transflag='false' where transflag='true'";
		try 
		{
			conn = PersistUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(updatesql);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
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
		System.out.println("重置 transflag 标记， 结束！！");
	}
	
  
  public static void savedata(String[] str)
  {
    System.out.println("向数据发布中间表中新建数据！！！");
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try
    {
      conn = PersistUtil.getConnection();
      stmt = conn.createStatement();

      String tdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

      String delsql = "delete from qqpublishmonitor where  objectnum='" + str[0] + "'";
      String savesql = "INSERT INTO qqpublishmonitor (objectnum,objectid,overflag,transflag,pdate,EXT1,EXT2) VALUES ('" + str[0] + "','" + str[1] + "','" + str[2] + "','" + str[3] + "',to_date('" + tdate + "','yyyy-mm-dd hh24:mi:ss'),'','')";

      System.out.println("delsql===" + delsql);
      rs = stmt.executeQuery(delsql);

      System.out.println("savesql===" + savesql);
      rs = stmt.executeQuery(savesql);

      rs.close();

      stmt.close();

      conn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
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
  }

  public String[] getdata(String num)
  {
    System.out.println("从数据发布中间表中，根据编号获取数据！！！");
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String[] str = new String[5];
    try
    {
      conn = PersistUtil.getConnection();
      stmt = conn.createStatement();
      String datasql = "select objectnum,objectid,overflag,transflag,EXT1 from qqpublishmonitor where objectnum='" + num + "' order by pdate";
      System.out.println("getdatasql===" + datasql);
      rs = stmt.executeQuery(datasql);

      if (rs.next())
      {
        str[0] = rs.getString(1);
        str[1] = rs.getString(2);
        str[2] = rs.getString(3);
        str[3] = rs.getString(4);
        str[4] = rs.getString(5);
      }
      else
      {
      	str = null;
      }
      rs.close();

      stmt.close();

      conn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
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
    return str;
  }

  public static void updatedata(String num, String id, String oflag, String tflag, String publishcount)
  {
    System.out.println("在数据发布中间表中更新数据！！！");
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try
    {
      conn = PersistUtil.getConnection();
      stmt = conn.createStatement();
      Calendar calendar = Calendar.getInstance();
      int tdate = calendar.get(Calendar.HOUR_OF_DAY);
      String updatesql = "update qqpublishmonitor set objectid='" + id + "',overflag='" + oflag + "',transflag='" + tflag + "',EXT1='" + publishcount + "',transdatetime='" + tdate + "' where objectnum='" + num + "'";
      System.out.println("updatesql===" + updatesql);
      rs = stmt.executeQuery(updatesql);

      rs.close();

      stmt.close();

      conn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
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
  }
  
  
	public String testPublish(String id, String publishcount)
	{
		System.out.println("id==="+id);
		String result = "";
		
		//SessionService ss = null;
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			String password = RemoteProperty.getProperty("administratorpassword");
			SessionService sservice = (SessionService) PublishHelper.getEJBService(
				"SessionService", "Administrator", password);
			//ss = (SessionService) EJBServiceHelper.getService("SessionService");
			Collection parts = new ArrayList();
			//ss.setAdministrator();
			//ss.setCurUser("Administrator");
			//确认通知书
			if(id.startsWith("Doc"))
			{
				DocInfo info = (DocInfo) ps.refreshInfo(id);
				publishConfirmDoc((BaseValueIfc)info, publishcount);
				result = "true";
			}
			//借阅单
			else if(id.startsWith("Borrow"))
			{
				BorrowInfo info = (BorrowInfo) ps.refreshInfo(id);
				publishBorrow((BaseValueIfc)info, publishcount);
				result = "true";
			}
			//艺准
			else if(id.startsWith("TechnicsRouteList"))
			{
				TechnicsRouteListInfo info = (TechnicsRouteListInfo) ps.refreshInfo(id);
				publishTechnicRoute((BaseValueIfc)info, publishcount);
				result = "true";
			}
			//发送编号，表明要重发
			else
			{
				String[] str = id.split(";");
				String objnum = "";
				if(str[1].startsWith("Doc"))
				{
					DocInfo info = (DocInfo) ps.refreshInfo(str[1]);
					objnum = info.getDocNum();
				}
				else if(str[1].startsWith("Borrow"))
				{
					BorrowInfo info = (BorrowInfo) ps.refreshInfo(str[1]);
					objnum = info.getBorrowNumber();
				}
				else if(str[1].startsWith("TechnicsRouteList"))
				{
					TechnicsRouteListInfo info = (TechnicsRouteListInfo) ps.refreshInfo(str[1]);
					objnum = info.getRouteListNumber();
				}
				if(getdata(objnum)==null)
				{
					savedata(new String[]{objnum,str[1],"false","false"});
				}
				else
				{
					updatedata(objnum,str[1],"false","false",publishcount);
				}
				result = "发布中间表重置完成！";
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		/*finally
		{
			try
			{
				ss.freeAdministrator();
			}
			catch(QMException e)
			{
				e.printStackTrace();
			}
		}*/
		return result;
	}
	
	/**
	 * 根据数据发布确认通知书，检查发布数据，需要发布则发往中间表。
	 */
	public static void publishConfirmDocCheck(BaseValueIfc primaryBusinessObject)
	{
		DocInfo info = (DocInfo)primaryBusinessObject;
		//System.out.println("青汽发布检查："+info.getDocNum()+"=="+info.getBsoID());
		System.out.println("青汽发布："+info.getDocNum()+"=="+info.getBsoID());
		try
		{
			//if(checkConfirmDoc(info).equals("true"))
			//{
				
				savedata(new String[]{info.getDocNum(),info.getBsoID(),"false","false"});
			//}
			//else
			//{
				//System.out.println("不符合发布条件，不发往中间表做发布准备!");
			//}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * 根据艺准，检查发布数据，需要发布则发往中间表。
	 */
	public static void publishTechnicRouteCheck(BaseValueIfc primaryBusinessObject)
	{
		TechnicsRouteListInfo info = (TechnicsRouteListInfo)primaryBusinessObject;
		System.out.println("青汽发布检查："+info.getRouteListNumber()+"=="+info.getBsoID());
		try
		{
			if(checkTechnicsRoute(info).equals("true"))
			{
				
				savedata(new String[]{info.getRouteListNumber(),info.getBsoID(),"false","false"});
			}
			else
			{
				System.out.println("不符合发布条件，不发往中间表做发布准备!");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * 根据借阅单，检查发布数据，需要发布则发往中间表
	 */
	public static void publishBorrowCheck(BaseValueIfc primaryBusinessObject)
	{
		BorrowInfo info = (BorrowInfo)primaryBusinessObject;
		System.out.println("青汽发布检查："+info.getBorrowNumber()+"=="+info.getBsoID());
		try
		{
			if(checkBorrow(info).equals("true"))
			{
				
				savedata(new String[]{info.getBorrowNumber(),info.getBsoID(),"false","false"});
			}
			else
			{
				System.out.println("不符合发布条件，不发往中间表做发布准备!");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * 根据数据发布确认通知书，发布数据。
	 */
	public static void publishConfirmDoc(BaseValueIfc primaryBusinessObject, String publishcount) throws Exception
	{
		String result = "false";
		long t1 = System.currentTimeMillis();
		StringBuffer buffer = new StringBuffer();
		DocInfo info = (DocInfo)primaryBusinessObject;
		DocInfo docinfo = null;
		updatedata(info.getDocNum(),info.getBsoID(),"false","true",publishcount);
		System.out.println("青汽发布："+info.getDocNum()+"=="+info.getBsoID());
		String curPath = "";
		try
		{
			buffer.append("数据发布开始！\n");
			buffer.append("类型：数据发布确认通知书\n");
			String sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			buffer.append("数据准备开始时间："+sendData+"\n");
			
			//校验数据
			//是否是数据发布确认通知书
			//不再整体检验，将符合要求的进行发布
			/*if(PublishHelper.isConfirmDoc(info).equals("true"))
			{
				//检查关联零部件是否符合发布要求
				String s = checkConfirmDoc(info);
				System.out.println("s==="+s);
				if(s.equals("true"))
				{
					result = "true";
				}
				else
				{
					result = "false";
				}
			}
			else
			{
				//检查关联零部件是否符合发布要求
				String s = checkConfirmDoc(info);
				System.out.println("s==="+s);
				if(s.equals("true"))
				{
					result = "true";
				}
				else
				{
					result = "false";
				}
			}
			System.out.println("result==="+result);
			if(result.equals("false"))
			{
				updatedata(info.getDocNum(),info.getBsoID(),"true","false",publishcount);
				return;
			}*/
			
			curPath = qqPublishPath + "ByTechnology-"+info.getDocNum().replaceAll("\\[","(").replaceAll("\\]",")") + File.separator;
			File f = new File(curPath);
			if(f.exists())
			{
				f.delete();
				f.mkdir();
			}
		  else
		  {
		  	f.mkdir();	
			}
			
			Collection parts = getPartsByConfirmDoc(info);
			
			//发布数据
			System.out.println("parts.size()==="+parts.size());
			if(parts.size()>0)
			{
				docinfo = createLogDoc(info.getDocNum(), info.getBsoID());
				parts = getAllParts(parts);
				System.out.println("parts.size() 111 ==="+parts.size());
				//准备零部件
				Group partGroup = preparePartsGroup(parts);
				buffer.append("零部件数量："+partGroup.getElementCount()+"\n");
				exportFile(getPartStringBuffer(partGroup),curPath, "P-"+info.getDocNum());
				//partGroup.toXML(new PrintWriter(System.out), true);
				//准备零部件关联
				Group linkGroup = preparePartLinksGroup(parts);
				buffer.append("零部关联数量："+linkGroup.getElementCount()+"\n");
				exportFile(getPartLinkStringBuffer(linkGroup),curPath, "U-"+info.getDocNum());
				//linkGroup.toXML(new PrintWriter(System.out), true);
				//准备pdf文档
				Group docGroup = prepareDocsGroup(parts, curPath);
				buffer.append("文档数量："+docGroup.getElementCount()+"\n");
				//准备零部件文档关联
				exportFile(getPartDocLinkStringBuffer(docGroup),curPath, "R-"+info.getDocNum());
				//准备数据发布确认通知书文档
				docGroup = prepareDocGroupForConfirm(docGroup, curPath, info);
				exportFile(getDocStringBuffer(docGroup),curPath, "D-"+info.getDocNum());
				
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("数据准备完毕时间："+sendData+"\n");
				
				//ftp发文件
				ftpLoad(f);
				//ftp发送完成标记文件
				ftpComplete(f);
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("数据传输完毕时间："+sendData+"\n");
				writeFile(buffer, qqPublishPath + info.getDocNum() + ".txt");
				addAppForLogDoc(docinfo, info.getDocNum(), curPath, "success");
			}
			deleteFile(f);
			updatedata(info.getDocNum(),info.getBsoID(),"true","false",publishcount);
		}
		catch (Exception ex)
		{
			updatedata(info.getDocNum(),info.getBsoID(),"false","false",publishcount);
			ex.printStackTrace();
			buffer.append("数据发布失败，请补发，或查看问题后补发！\n");
			buffer.append("异常信息："+ex.getLocalizedMessage()+"\n");
			writeFile(buffer, qqPublishPath + info.getDocNum() + ".txt");
			if(docinfo!=null)
			{
				addAppForLogDoc(docinfo, info.getDocNum(), curPath, "fail");
			}
			setFailState(docinfo.getBsoID());
		}
		long t2 = System.currentTimeMillis();
    System.out.println("此次发布汽研确认通知书用时： "+(t2-t1)/1000+" 秒");
	}
	
	
	/**
	 * 根据艺准，发布数据。
	 */
	public static void publishTechnicRoute(BaseValueIfc primaryBusinessObject, String publishcount)
	{
		String result = "";
		long t1 = System.currentTimeMillis();
		StringBuffer buffer = new StringBuffer();
		String fname = "";
		DocInfo docinfo = null;
		TechnicsRouteListInfo info = (TechnicsRouteListInfo)primaryBusinessObject;
		String curPath = "";
		try
		{
			updatedata(info.getRouteListNumber(),info.getBsoID(),"false","true",publishcount);
		  System.out.println("青汽发布："+info.getRouteListNumber()+"=="+info.getBsoID());
		  
			buffer.append("数据发布开始！\n");
			buffer.append("类型：艺准\n");
			String sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			buffer.append("数据准备开始时间："+sendData+"\n");
			
			//校验数据
			/*result = checkTechnicsRoute(info);
			System.out.println("result==="+result);
			if(result.equals("false"))
			{
				updatedata(info.getRouteListNumber(),info.getBsoID(),"true","false",publishcount);
				return;
			}*/
			fname = getRouteListFileName(info.getRouteListNumber());
			
			curPath = qqPublishPath + "ByTechnics-"+fname.replaceAll("\\[","(").replaceAll("\\]",")") + File.separator;
			System.out.println("curPath=="+curPath);
			File f = new File(curPath);
			if(f.exists())
			{
				f.delete();
				f.mkdir();
			}
		  else
		  {
		  	f.mkdir();	
			}
			
			Collection parts = getPartsByTechnicRoute(info);
			
			//发布数据
			System.out.println("parts.size()==="+parts.size());
			if(parts.size()>0)
			{
				docinfo = createLogDoc(fname, info.getBsoID());
				parts = getAllParts(parts);
				System.out.println("parts.size() 111 ==="+parts.size());
				Group partGroup = preparePartsGroup(parts);
				buffer.append("零部件数量："+partGroup.getElementCount()+"\n");
				exportFile(getPartStringBuffer(partGroup),curPath, "P-"+fname);
				//partGroup.toXML(new PrintWriter(System.out), true);
				Group linkGroup = preparePartLinksGroup(parts);
				buffer.append("零部关联数量："+linkGroup.getElementCount()+"\n");
				exportFile(getPartLinkStringBuffer(linkGroup),curPath, "U-"+fname);
				//linkGroup.toXML(new PrintWriter(System.out), true);
				//准备pdf文档
				Group docGroup = prepareDocsGroup(parts, curPath);
				buffer.append("文档数量："+docGroup.getElementCount()+"\n");
				//准备零部件文档关联
				exportFile(getPartDocLinkStringBuffer(docGroup),curPath, "R-"+fname);
				//准备数据发布确认通知书文档
				docGroup = prepareDocGroupForTechnicsRoute(docGroup, info, curPath);
				exportFile(getDocStringBuffer(docGroup),curPath, "D-"+fname);
				
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("数据准备完毕时间："+sendData+"\n");
				writeFile(buffer, qqPublishPath + fname + ".txt");
				
				//ftp发文件
				ftpLoad(f);
				//ftp发送完成标记文件
				ftpComplete(f);
				
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("数据传输完毕时间："+sendData+"\n");
				writeFile(buffer, qqPublishPath + fname + ".txt");
				addAppForLogDoc(docinfo, fname, curPath, "success");
			}
			deleteFile(f);
			updatedata(info.getRouteListNumber(),info.getBsoID(),"true","false",publishcount);
		}
		catch (Exception ex)
		{
			updatedata(info.getRouteListNumber(),info.getBsoID(),"false","false",publishcount);
			ex.printStackTrace();
			buffer.append("数据发布失败，请补发，或查看问题后补发！\n");
			buffer.append("异常信息："+ex.getLocalizedMessage()+"\n");
			writeFile(buffer, qqPublishPath + fname + ".txt");
			if(docinfo!=null)
			{
				addAppForLogDoc(docinfo, fname, curPath, "fail");
			}
			setFailState(docinfo.getBsoID());
		}
		long t2 = System.currentTimeMillis();
    System.out.println("此次发布工艺路线用时： "+(t2-t1)/1000+" 秒");
	}
	
	
	/**
	 * 根据借阅单，发布数据。
	 */
	public static void publishBorrow(BaseValueIfc primaryBusinessObject, String publishcount)
	{
		String result = "false";
		long t1 = System.currentTimeMillis();
		StringBuffer buffer = new StringBuffer();
		BorrowInfo info = (BorrowInfo)primaryBusinessObject;
		updatedata(info.getBorrowNumber(),info.getBsoID(),"false","true",publishcount);
		System.out.println("青汽发布："+info.getBorrowNumber()+"=="+info.getBsoID());
		DocInfo docinfo = null;
		String curPath = "";
		try
		{
			buffer.append("数据发布开始！\n");
			buffer.append("类型：补发\n");
			String sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			buffer.append("数据准备开始时间："+sendData+"\n");
			
			//校验数据
			result = checkBorrow(info);
			System.out.println("result==="+result);
			if(result.equals("false"))
			{
				updatedata(info.getBorrowNumber(),info.getBsoID(),"true","false",publishcount);
				return;
			}
			
			curPath = qqPublishPath + "ByReplenish-"+info.getBorrowNumber().replaceAll("\\[","(").replaceAll("\\]",")") + File.separator;
			File f = new File(curPath);
			if(f.exists())
			{
				f.delete();
				f.mkdir();
			}
		  else
		  {
		  	f.mkdir();	
			}
			
			Collection parts = getPartsByBorrow(info);
			
			//发布数据
			System.out.println("parts.size()==="+parts.size());
			if(parts.size()>0)
			{
				docinfo = createLogDoc(info.getBorrowNumber(), info.getBsoID());
				parts = getAllParts(parts);
				System.out.println("parts.size() 111 ==="+parts.size());
				Group partGroup = preparePartsGroup(parts);
				buffer.append("零部件数量："+partGroup.getElementCount()+"\n");
				exportFile(getPartStringBuffer(partGroup),curPath, "P-"+info.getBorrowNumber());
				//partGroup.toXML(new PrintWriter(System.out), true);
				Group linkGroup = preparePartLinksGroup(parts);
				buffer.append("零部关联数量："+linkGroup.getElementCount()+"\n");
				exportFile(getPartLinkStringBuffer(linkGroup),curPath, "U-"+info.getBorrowNumber());
				//linkGroup.toXML(new PrintWriter(System.out), true);
				//准备pdf文档
				Group docGroup = prepareDocsGroup(parts, curPath);
				buffer.append("文档数量："+docGroup.getElementCount()+"\n");
				exportFile(getDocStringBuffer(docGroup),curPath, "D-"+info.getBorrowNumber());
				//准备零部件文档关联
				exportFile(getPartDocLinkStringBuffer(docGroup),curPath, "R-"+info.getBorrowNumber());
				
				
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("数据准备完毕时间："+sendData+"\n");
				
				//ftp发文件
				ftpLoad(f);
				//ftp发送完成标记文件
				ftpComplete(f);
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("数据传输完毕时间："+sendData+"\n");
				writeFile(buffer, qqPublishPath + info.getBorrowNumber() + ".txt");
				addAppForLogDoc(docinfo, info.getBorrowNumber(), curPath, "success");
			}
			deleteFile(f);
			updatedata(info.getBorrowNumber(),info.getBsoID(),"true","false",publishcount);
		}
		catch (Exception ex)
		{
			updatedata(info.getBorrowNumber(),info.getBsoID(),"false","false",publishcount);
			ex.printStackTrace();
			buffer.append("数据发布失败，请补发，或查看问题后补发！\n");
			buffer.append("异常信息："+ex.getLocalizedMessage()+"\n");
			writeFile(buffer, qqPublishPath + info.getBorrowNumber() + ".txt");
			if(docinfo!=null)
			{
				addAppForLogDoc(docinfo, info.getBorrowNumber(), curPath, "fail");
			}
			setFailState(docinfo.getBsoID());
		}
		long t2 = System.currentTimeMillis();
    System.out.println("此次发布借阅单用时： "+(t2-t1)/1000+" 秒");
	}
	
	
	/**
	 * 根据数据发布确认通知书，获取种子零部件集合。
	 */
	public static Collection getPartsByConfirmDoc(BaseValueIfc primaryBusinessObject)
	{
		Collection parts = new ArrayList();
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			DocIfc ifc = (DocIfc)primaryBusinessObject;
			String res = PublishHelper.isCaiYong(ifc);
			//采用
			if(res.equals("true"))
			{
				Collection coll = PublishHelper.getPartCaiYong(ifc);
				Iterator ite = coll.iterator();
				while (ite.hasNext())
				{
					String[] str = (String[]) ite.next();
					QMPartIfc part = (QMPartIfc) ps.refreshInfo(str[0]);
					part = getLatestPart(part);
					if(part.getViewName().trim().equals("中心设计视图"))
        	{
        		if(!checkPart(part))
        		{
        			continue;
        		}
        		parts.add(part);
        	}
				}
			}
			//变更
			else
			{
				Collection coll = PublishHelper.getConfirmDoc(ifc.getBsoID());
				Iterator ite = coll.iterator();
				while (ite.hasNext())
				{
					DocIfc docifc = (DocIfc) ite.next();
					Collection coll1 = PublishHelper.getPartAfterchange(docifc);
					Iterator ite1 = coll1.iterator();
					while (ite1.hasNext())
					{
						String[] str = (String[]) ite1.next();
						QMPartIfc part = (QMPartIfc) ps.refreshInfo(str[0]);
						part = getLatestPart(part);
						if(part.getViewName().trim().equals("中心设计视图"))
						{
							if(!checkPart(part))
							{
								continue;
							}
							parts.add(part);
						}
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return parts;
	}

	/**
	 * 检查数据发布确认通知书的数据中是否都是符合要求的零部件，属于以下整车：P25—JH6、P62—J6L、P63—J6M、PK—JL01、P26
	 * 是则返回 true，直接发布。
	 * 否则返回 false，需要走审批流程，然后决定是否发布。
	 */
	/*public static String checkConfirmDoc(BaseValueIfc primaryBusinessObject)
	{
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			DocIfc ifc = (DocIfc)primaryBusinessObject;
			String res = PublishHelper.isCaiYong(ifc);
			//采用
			if(res.equals("true"))
			{
				Collection coll = PublishHelper.getPartCaiYong(ifc);
				Iterator ite = coll.iterator();
				while (ite.hasNext())
				{
					String[] str = (String[]) ite.next();
					QMPartIfc part = (QMPartIfc) ps.refreshInfo(str[0]);
					if(!checkPart(part))
			        {
			        	return "false";
			        }
				}
			}
			//变更
			else
			{
				Collection coll = PublishHelper.getConfirmDoc(ifc.getBsoID());
				Iterator ite = coll.iterator();
				while (ite.hasNext())
				{
					DocIfc docifc = (DocIfc) ite.next();
					Collection coll1 = PublishHelper.getPartAfterchange(docifc);
					Iterator ite1 = coll1.iterator();
					while (ite1.hasNext())
					{
						String[] str = (String[]) ite1.next();
						System.out.println(str[0]+"=="+str[1]+"=="+str[2]+"=="+str[3]);
						QMPartIfc part = (QMPartIfc) ps.refreshInfo(str[0]);
						if(!checkPart(part))
				        {
				        	return "false";
				        }
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return "true";
	}*/
	
	/**
	 * 根据艺准通知书，获取种子零部件集合。
	 */
	public static Collection getPartsByTechnicRoute(BaseValueIfc primaryBusinessObject)
	{
		Collection parts = new ArrayList();
		Collection temp = new ArrayList();
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			TechnicsRouteListIfc info = (TechnicsRouteListIfc)primaryBusinessObject;
      QMQuery qmQuery = new QMQuery("ListRoutePartLink");
      int j = qmQuery.appendBso("TechnicsRoute", false);
      qmQuery.addCondition(0, new QueryCondition("leftBsoID", "=", info.getBsoID()));
      qmQuery.addAND();
      qmQuery.addCondition(0, j, new QueryCondition("routeID", "bsoID"));
      qmQuery.addAND();
      qmQuery.addCondition(j, new QueryCondition("modefyIdenty", QueryCondition.NOT_EQUAL, "Coding_221664"));
      Collection co = ps.findValueInfo(qmQuery, false);
      Iterator ite = co.iterator();
      while (ite.hasNext())
      {
        ListRoutePartLinkIfc link = (ListRoutePartLinkIfc) ite.next();
        QMPartIfc part = (QMPartIfc) ps.refreshInfo(link.getPartBranchID());
        part = getLatestPart(part);
        if(part!=null)
        {
        	if(!temp.contains(part.getBsoID())&&part.getViewName().trim().equals("中心设计视图"))
        	{
        		parts.add(part);
        		temp.add(part.getBsoID());
        	}
        }
        else
        {
        	System.out.println(link.getPartBranchID()+"没有找到对应零部件！");
        }
      }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return parts;
	}

	/**
	 * 检查数据发布确认通知书的数据中是否都是符合要求的零部件，属于以下整车：P25—JH6、P62—J6L、P63—J6M、PK—JL01、P26
	 * 是则返回 true，直接发布。
	 * 否则返回 false，需要走审批流程，然后决定是否发布。
	 */
	public static String checkTechnicsRoute(BaseValueIfc primaryBusinessObject)
	{
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			TechnicsRouteListIfc info = (TechnicsRouteListIfc)primaryBusinessObject;
			
			String processID = "";
			

	        WfEngineService service = (WfEngineService) EJBServiceHelper.getService("WfEngineService");
	        IteratorVector vec = (IteratorVector) service.getAssociatedProcesses(info.getBsoID(), null);
	        Object obj[] = vec.toArray();
	        for (int i = 0; i < obj.length; i++)
	        {
	            WfProcessIfc process = (WfProcessIfc) obj[i];
	            String state = process.getState();
	            if(state.equals("OPEN_RUNNING"))
	            {
	            	processID = process.getBsoID();
	            }
	        }
	        if(processID.equals(""))
	        {
	        	for (int i = 0; i < obj.length; i++)
	          {
	            WfProcessIfc process = (WfProcessIfc) obj[i];
	            String state = process.getState();
	            if(state.equals("CLOSED_COMPLETED_EXECUTED"))
	            {
	            	processID = process.getBsoID();
	            }
	          }
	        }
	        System.out.println("processID===="+processID);
	        if(processID.equals(""))
	        {
	        	return "false";
	        }
	        
	        WfProcessIfc wfprocess = (WfProcessIfc)ps.refreshInfo(processID);
	        //获得流程中所有任务对应的角色和参与者，保存到HashMap中，其中key是角色名，value是参与者。
	        if (wfprocess instanceof WfProcessIfc)
	        {
	            RolePrincipalTable table = ( (WfProcessIfc) wfprocess).getRolePrincipalMap();           
	            Enumeration enumeration = table.keys();
	            while (enumeration.hasMoreElements())
	            {
	                String role = (String) enumeration.nextElement();
	                System.out.println("role===="+role);
	            	  if (role.equals("TONGZHIZHE"))
	            	  {
	            	  	Vector rolevector = (Vector) table.get(role);
	            	  	if (rolevector != null&&rolevector.size()>0)
	            	  	{
	            	  		for (int tt = 0; tt < rolevector.size(); tt++)
	            	  		{
	            	  			String userid = (String)rolevector.elementAt(tt);
		                        String userName = "";
		                        if(userid.startsWith("Group_"))
		                        {
		                        	GroupInfo groupinfo = (GroupInfo) ps.refreshInfo(userid);
		            					    userName = groupinfo.getUsersName();
		            					    System.out.println("userName===="+userName);
		            					    if(userName.indexOf("S青汽-艺准")!=-1)
		            					    {
		            					    	return "true";
		            					    }
		                        }
	            	  		}
	            	  	}
	            	  }
	            	  else
	            	  {
	            	  	continue;
	            	  }   	  
	            }
	            
	        }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return "false";
	}
	
	
	/**
	 * 补发数据。
	 * 使用现有的借阅单对象，获取里面的零部件
	 */
	public static Collection getPartsByBorrow(BaseValueIfc primaryBusinessObject)
	{
		Collection parts = new ArrayList();
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
			BorrowInfo info = (BorrowInfo)primaryBusinessObject;
      QMQuery qmQuery = new QMQuery("BorrowObjectLink");
      QueryCondition cond = new QueryCondition("rightBsoID", "=", info.getBsoID());
      qmQuery.addCondition(cond);
      Collection co = ps.findValueInfo(qmQuery, false);
      Iterator ite = co.iterator();
      while (ite.hasNext())
      {
        BorrowObjectLinkIfc link = (BorrowObjectLinkIfc) ite.next();
        String partMasterID = link.getObjectMasterID();
        QMPartMasterIfc master = (QMPartMasterIfc)ps.refreshInfo(partMasterID);
        Collection col = vcService.allVersionsOf(master);
        QMPartIfc part = (QMPartIfc) col.iterator().next();
        if(part!=null&&part.getViewName().trim().equals("中心设计视图"))
        {
        	parts.add(part);
        }
      }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return parts;
	}
	
	/**
	 * 检查补发的数据中是否都是符合要求的零部件，属于以下整车：P25—JH6、P62—J6L、P63—J6M、PK—JL01、P26
	 * 是则返回 true，直接发布。
	 * 否则返回 false，需要走审批流程，然后决定是否发布。
	 */
	public static String checkBorrow(BaseValueIfc primaryBusinessObject)
	{
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
			BorrowInfo info = (BorrowInfo)primaryBusinessObject;
      QMQuery qmQuery = new QMQuery("BorrowObjectLink");
      QueryCondition cond = new QueryCondition("rightBsoID", "=", info.getBsoID());
      qmQuery.addCondition(cond);
      Collection co = ps.findValueInfo(qmQuery, false);
      Iterator ite = co.iterator();
      while (ite.hasNext())
      {
        BorrowObjectLinkIfc link = (BorrowObjectLinkIfc) ite.next();
        String partMasterID = link.getObjectMasterID();
        QMPartMasterIfc master = (QMPartMasterIfc)ps.refreshInfo(partMasterID);
        Collection col = vcService.allVersionsOf(master);
        QMPartIfc part = (QMPartIfc) col.iterator().next();
        if(!checkPart(part))
        {
        	return "false";
        }
      }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return "true";
	}
	
	
	/**
	 * 检查零部件是否在P25—JH6、P62—J6L、P63—J6M、PK—JL01、P26整车下
	 */
	private static boolean checkPart(QMPartIfc part)
	{
		try
		{
			PartServiceHelper helper = new PartServiceHelper();
			Vector vec = (Vector)helper.getUsageList(part.getBsoID());
			if(vec==null||vec.size()==0)
			{
				return true;
			}
			for(int i=0;i<vec.size();i++)
			{
				Object[] obj = (Object[])vec.elementAt(i);
				System.out.println("checkpart:"+obj[0]);
				if(obj[0] instanceof QMPartInfo)
				{
					QMPartInfo parentPart = (QMPartInfo)obj[0];
					String parent = parentPart.getPartNumber();
					System.out.println("parent:"+parent);
					if(parent.indexOf("P25")!=-1||parent.indexOf("P62")!=-1||parent.indexOf("P63")!=-1||parent.indexOf("PK")!=-1||parent.indexOf("P26")!=-1)
					{
						return true;
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 根据零部件集合，获取所有的全结构集合。
	 */
	public static Collection getAllParts(Collection parts)
	{
		Collection result = new ArrayList();
		try
		{
			Collection temp = new ArrayList();
			//StandardPartService partService = (StandardPartService) EJBServiceHelper.getService("StandardPartService");
			EnterprisePartService partService = (EnterprisePartService) EJBServiceHelper.getService("EnterprisePartService");
			
			Iterator iterator = parts.iterator();
			while (iterator.hasNext())
			{
				QMPartIfc part = (QMPartIfc) iterator.next();
				//if(!temp.contains(part.getBsoID()))
				if(!temp.contains(part.getPartNumber()))
				{
					//temp.add(part.getBsoID());
					temp.add(part.getPartNumber());
					result.add(part);
				}
			}
			
			PartConfigSpecIfc configSpec = getConfigSpec();
			Iterator iterator1 = parts.iterator();
			while (iterator1.hasNext())
			{
				QMPartIfc part = (QMPartIfc) iterator1.next();
				//Collection coll = partService.getAllSubParts(part);
				Vector vec = partService.getAllSubPartsByConfigSpec(part,configSpec);
				for(int i=0;i<vec.size();i++)
				{
					QMPartIfc subpart = (QMPartIfc) vec.elementAt(i);
					//if(!temp.contains(subpart.getBsoID()))
					if(!temp.contains(subpart.getPartNumber()))
					{
						//temp.add(subpart.getBsoID());
						temp.add(subpart.getPartNumber());
						result.add(subpart);
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
	}
	
	public static PartConfigSpecIfc getConfigSpec() throws QMException
  {
  	PartConfigSpecIfc configSpec = new PartConfigSpecInfo();
  	configSpec = new PartConfigSpecInfo();
  	configSpec.setStandardActive(true);
  	configSpec.setBaselineActive(false);
  	configSpec.setEffectivityActive(false);
  	PartStandardConfigSpec partStandardConfigSpec = new PartStandardConfigSpec();
  	ViewService vs = (ViewService)EJBServiceHelper.getService("ViewService");
  	ViewObjectIfc viewObjectIfc=vs.getView("中心设计视图");
  	partStandardConfigSpec.setViewObjectIfc(viewObjectIfc);
  	partStandardConfigSpec.setLifeCycleState(null);
  	partStandardConfigSpec.setWorkingIncluded(true);
  	configSpec.setStandard(partStandardConfigSpec);
  	return configSpec;
  }
  
  
	/**
	 * 根据零部件，获取最新小版本。
	 */
	public static QMPartIfc getLatestPart(QMPartIfc part)
	{
		try
		{
			VersionControlService vs = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
			if(!vs.isLatestIteration(part)||!vs.isLatestVersion(part))
			{
				System.out.print(part.getPartNumber()+" 不是最新版，当前版本"+part.getVersionValue());
				part = (QMPartIfc)vs.getLatestVersion(part);
				System.out.println("     获取版本"+part.getVersionValue());
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return part;
	}
  
	
	/**
	 * 将给定的零部件集合，整理出零部件关联发布属性数组的集合，用于制作导出文件。
	 */
	public static Group preparePartLinksGroup(Collection parts) throws QMException 
	{
		if (VERBOSE)
		{
			System.out.println("***   comming QQPartPublsih.preparePartLinksGroup   ***");
		}
		if (parts == null || parts.size() == 0)
		{
			return new Group(); // return null
		}
		Group group = new Group();
		Element ele = null;
		
		ArrayList partlist = new ArrayList();
		Iterator iter = parts.iterator();
		while (iter.hasNext())
		{
			QMPartIfc part = (QMPartIfc) iter.next();
			partlist.add(part.getPartNumber());
		}
		
		Iterator iterator = parts.iterator();
		Collection temp = new ArrayList();
		try
		{
			StandardPartService partService = (StandardPartService) EJBServiceHelper.getService("StandardPartService");
			
			while (iterator.hasNext())
			{
				QMPartIfc part = (QMPartIfc) iterator.next();
				Collection coll = partService.getUsesPartIfcs(part);
				Iterator ite = coll.iterator();
				while (ite.hasNext())
				{
					Object[] obj = (Object[])ite.next();
					PartUsageLinkIfc link = (PartUsageLinkIfc) obj[0];
					if(!temp.contains(link.getBsoID()))
					{
						ele = preparePartUsageLink(link, partlist);
						if(ele==null)
						{
							continue;
						}
						group.addElement(ele);
						temp.add(link.getBsoID());
					}
				}
			}
			System.out.println("link.size() ==="+temp.size());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		if (VERBOSE)
		{
			//group.toXML(new PrintWriter(System.out), true);
			System.out.println("***   leaving QQPartPublsih.preparePartLinksGroup   ***");
		}
		return group;

	}
	
	/**
	 * 将零部件的使用关系封装到一个元素（Element）对象中。
	 * @param link PartUsageLinkIfc 零部件的使用关系
	 * @return Element：返回的元素对象
	 */
	private static Element preparePartUsageLink(PartUsageLinkIfc link, ArrayList partlist)
	{
		if (link == null)
		{
			return null;
		}
		Element ele = new Element();
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
		  QMPartIfc parent = (QMPartIfc)ps.refreshInfo(link.getRightBsoID()); // 获取父件
		  QMPartMasterIfc child = (QMPartMasterIfc)ps.refreshInfo(link.getLeftBsoID()); // 获取子件
		  if(!partlist.contains(child.getPartNumber()))
		  {
		  	return null;
		  }
		  ele.addAtt(new Att("parent", qudouhao("JF-"+parent.getPartNumber()))); // 父件编号
		  ele.addAtt(new Att("child", qudouhao("JF-"+child.getPartNumber()))); // 子件编号
		  ele.addAtt(new Att("unit", link.getDefaultUnit().toString())); // 单位
		  ele.addAtt(new Att("quantity", Double.toString(link.getQuantity()))); // 子件数量
		  ele.addAtt(new Att("parentVersion", parent.getVersionValue())); // 父件的解放版本
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ele;
	}
	
	
  /**
	 * 准备零部件组（包涵广义部件）
	 * @param parts Collection：需要封装到组中的零部件。
	 * @return Group：返回的零部件组
	 * @throws Exception
	 */ 
	public static Group preparePartsGroup(Collection parts)throws Exception 
	{
		if (VERBOSE)
		{
			System.out.println("***   comming QQPartPublsih.preparePartsGroup   ***");
		}
		if (parts == null || parts.size() == 0)
		{
			return new Group();
		}
		Group group = new Group();
		Element ele = null;
		Iterator iterator = parts.iterator();
		while (iterator.hasNext())
		{
			QMPartIfc part = (QMPartIfc) iterator.next();
			if(part==null)
			{
				continue;
			}
			ele = preparePart(part);
			group.addElement(ele);
		}
		if (VERBOSE)
		{
			//System.out.println("***the return group: \r\n");
			//group.toXML(new PrintWriter(System.out), true);
			System.out.println("***   leaving QQPartPublsih.preparePartsGroup   ***");
		}
		return group;
	}

	/**
	 * 将一个零部件的信息封装到一个元素（Element）对象中
	 * @param part QMPartIfc 零部件
	 * @return Element：返回的元素对象
	 */
	private static Element preparePart(QMPartIfc part)
	{
		if (part == null)
		{
			return null;
		}
		//零部件编号/名称/类型/资料夹/生命周期状态（中文显示名）
		//剩下就是零部件的5个扩展属性都需要

		Element ele = new Element();
		//以基本属性发布的
		ele.addAtt(new Att("num", qudouhao("JF-"+part.getPartNumber()))); // 编号
		ele.addAtt(new Att("name", qudouhao(part.getPartName()))); // 名称
		ele.addAtt(new Att("partType", getPartType(part))); // 类型
		ele.addAtt(new Att("source", part.getProducedBy().toString())); // 来源
		ele.addAtt(new Att("unit", part.getDefaultUnit().toString())); // 单位
		ele.addAtt(new Att("path", part.getLocation())); // 文件夹路径
		ele.addAtt(new Att("viewName", part.getViewName())); // 视图名
		ele.addAtt(new Att("lifecyclestate", part.getLifeCycleState().toString())); // 生命周期状态
		
		//以IBA属性发布的：解放发布源版本、中心发布源版本、解放零部件URL、解放发布令号、解放艺准URL
		String[] str = getPartIba(part);//获取 中心发布源版本 和 解放发布令号 和 艺准编号
		ele.addAtt(new Att("jfVersion", part.getVersionValue())); // 解放发布源版本
		ele.addAtt(new Att("sourceVersion", str[0])); // 中心发布源版本
		ele.addAtt(new Att("jfPartUrl", "http://jfpdm.faw.com/PhosphorPDM/Part-Other-PartLookOver-001.screen?bsoID="+part.getBsoID())); // 解放零部件URL
		ele.addAtt(new Att("jfPublish", str[1])); // 解放发布令号
		ele.addAtt(new Att("FAWTZHBB", getTuZhiDes(part)));//记录是否有图纸。名称 FAWTZHBB  如果没有图纸记录“WUA”,有图纸直接写图纸名称。
		String routeID = getRouteBsoID(str[2]);
		if(routeID==null||routeID.equals(""))
		{
			ele.addAtt(new Att("jfRouteUrl", "")); // 解放艺准URL
		}
		else
		{
			ele.addAtt(new Att("jfRouteUrl", "http://jfpdm.faw.com/PhosphorPDM/route_look_routeList.screen?bsoID="+routeID)); // 解放艺准URL
		}
		return ele;
	}
	
	/**
	 * 获取零部件的IBA属性：发布源版本、更改通知书号、艺准通知书号
	 */
	private static String[] getPartIba(IBAHolderIfc holder)
	{
		String[] str = new String[3];
		try
		{
			IBAValueService vs = (IBAValueService) EJBServiceHelper.getService("IBAValueService");
			holder = vs.refreshAttributeContainerWithoutConstraints(holder);
			DefaultAttributeContainer attrCont = (DefaultAttributeContainer) holder.getAttributeContainer();
			AbstractValueView values[] = attrCont.getAttributeValues();
			for (int i = 0; i < values.length; i++)
			{
				if (values[i] instanceof StringValueDefaultView)
				{
					StringValueDefaultView strValue = (StringValueDefaultView) values[i];
					StringDefView defView = (StringDefView) strValue.getDefinition();
					String value = strValue.getValue();
					if (defView.getName().equals("sourceVersion") && value != null)
					{
						str[0] = value;
					}
					if (defView.getName().equals("notenumbers") && value != null)
					{
						str[1] = value;
					}
					if (defView.getName().equals("艺准通知书号") && value != null)
					{
						str[2] = value;
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		if(str[0]==null)
		{
			str[0] = "";
		}
		if(str[1]==null)
		{
			str[1] = "";
		}
		if(str[2]==null)
		{
			str[2] = "";
		}
		return str;
	}
	
	
	/**
	 * 根据零部件获取类型，对应青汽的“装配模式”
	 * 先从IBA属性的“零部件类型”中取，如果取不到才取“类型”中的值。直接取中文，不用英文内码。
	 */
	private static String getPartType(QMPartIfc part)
	{
		String partType = part.getPartType().getDisplay();
		try
		{
			partType = (String)PublishHelper.getIBAValue((IBAHolderIfc)part,"PartTypes");
			if(partType==null||partType.equals(""))
			{
				partType = part.getPartType().getDisplay();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return partType;
		}
		
		return partType;
	}
	
	/**
	 * 根据艺准编号获取艺准bsoID
	 */
	private static String getRouteBsoID(String num)
	{
		String routeID = "";
		try
		{
			TechnicsRouteService ts = (TechnicsRouteService) EJBServiceHelper.getService("TechnicsRouteService");
			TechnicsRouteListInfo info = (TechnicsRouteListInfo)ts.findRouteListByNum(num);
			if(info!=null)
			{
				routeID = info.getBsoID();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
		return routeID;
	}
	
	
	
	/**
	 * 根据零部件bsoID得到图纸信息。
	 * 记录是否有图纸。名称 FAWTZHBB  如果没有图纸记录“WUA”,有图纸直接写图纸名称。
	 */
	private static String getTuZhiDes(QMPartIfc part)
	{
		String des = "WUA";
		try
		{
			Vector vec = getPartPDFContents(part.getBsoID());
			if(vec!=null)
			{
				if(vec.size()==1)
    		{
    			ApplicationDataInfo info = (ApplicationDataInfo) vec.elementAt(0);
    			des = getVersion(part,info.getFileName());
        }
        else if(vec.size()>1)
        {
        	des = getVersion(part,"");
        }
        
			}
			if(des.indexOf(".")>0)
			{
				des = des.substring(0,des.indexOf("."));
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return des;
		}
		
		return des;
	}
	
	/**
	 * 零部件的PDF附件，准备普通文档
	 * @param parts Collection 零部件集合
	 * @return Group：返回的组对象
	 */
	public static Group prepareDocsGroup(Collection parts, String path)throws Exception 
	{
		if (VERBOSE)
		{
			System.out.println("***   comming QQPartPublsih.prepareDocsGroup   ***");
		}
		if (parts == null || parts.size() == 0)
		{
			return new Group();
		}
		
		Group group = new Group(); // 要返回的组
		Iterator iterator = parts.iterator();
		while (iterator.hasNext())
		{
			QMPartIfc part = (QMPartIfc) iterator.next();
			Vector vec = new Vector();
			try
			{
				vec = getPartPDFContents(part.getBsoID());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			if(vec==null||vec.size()==0)
			{
				continue;
			}
			if(vec.size()==1)
			{
				ApplicationDataInfo app = (ApplicationDataInfo)vec.elementAt(0);
				String filename = app.getFileName();
				filename = part.getPartNumber()+filename.substring(filename.indexOf(".pdf"),filename.length());
				downloadPDF(path + qudouhao(checkCHNString(filename)), app);
				Element ele = new Element();
				ele.addAtt(new Att("num", qudouhao("JF-"+part.getPartNumber()))); // 文档编号
				ele.addAtt(new Att("doctype", "图纸文档"));
				ele.addAtt(new Att("name", qudouhao(part.getPartName()))); // 文档名称
				ele.addAtt(new Att("department", "")); // 文档的创建单位
				ele.addAtt(new Att("location", part.getLocation())); // 文档的位置（文件夹）
				ele.addAtt(new Att("desc", getVersion(part, app.getFileName()))); // 文档的描述
				ele.addAtt(new Att("filename", qudouhao(checkCHNString(filename)))); // 文档的文件名称
				ele.addAtt(new Att("lifecyclestate", part.getLifeCycleState().toString())); // 文档的生命周期状态，取零部件状态
				ele.addAtt(new Att("url", "")); // url,只有艺准、采用、变更该属性有值。
				ele.addAtt(new Att("partVersion", part.getVersionValue())); // 零部件解放版本
				group.addElement(ele);
			}
			else if(vec.size()>1)
			{
				Element ele = new Element();
				ele.addAtt(new Att("num", qudouhao("JF-"+part.getPartNumber()))); // 文档编号
				ele.addAtt(new Att("doctype", "图纸文档"));
				ele.addAtt(new Att("name", qudouhao(part.getPartName()))); // 文档名称
				ele.addAtt(new Att("department", "")); // 文档的创建单位
				ele.addAtt(new Att("location", part.getLocation())); // 文档的位置（文件夹）
				ele.addAtt(new Att("desc", getVersion(part, ""))); // 文档的描述
				ele.addAtt(new Att("filename", "多个图纸")); // 文档的文件名称
				ele.addAtt(new Att("lifecyclestate", part.getLifeCycleState().toString())); // 文档的生命周期状态，取零部件状态
				ele.addAtt(new Att("url", "")); // url,只有艺准、采用、变更该属性有值。
				ele.addAtt(new Att("partVersion", part.getVersionValue())); // 零部件解放版本
				group.addElement(ele);
			}
		}
		if (VERBOSE)
		{
			System.out.println("***   leaving QQPartPublsih.prepareDocsGroup   ***");
		}
		return group;
	}
	
	
	/**
   * 得到零部件的pdf附件
   * @param id String 零部件bsoid
   * @return Vector ApplicationDataInfo 内容项
   * @throws Exception 
   */
  private static Vector getPartPDFContents(String id)throws Exception
  {
    Vector vec = new Vector();
    try
    {
    	Vector c = getContents(id);
    	if(c!=null)
    	{
    		ContentItemIfc item;
    		ApplicationDataInfo appDataInfo = null;
    		for (Iterator iter = c.iterator(); iter.hasNext(); )
    		{
    			item = (ContentItemIfc) iter.next();
    			if (item instanceof ApplicationDataInfo)
    			{
    				appDataInfo = (ApplicationDataInfo) item;
    				if(appDataInfo.getContentRank().equals("SECONDARY"))
    				{
    					if(appDataInfo.getFileName().toUpperCase().endsWith(".PDF"))
    					{
    						vec.add(appDataInfo);
    					}
    				}
           }
         }
    	}
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }
    return vec;
  }
  
  /**
   * 根据零部件PDF附件的文件名取出图档大版本，如果名称中大版本不存在，
   * 则取零部件的“发布源版本”属性，如果“发布源版本不存在”则取解放系统中该零件的版本
   */
  public static String getVersion(QMPartIfc part, String fileName)
  {
  	String version = "";
  	//取pdf附件中图档大版本
		try
		{
			if(fileName.indexOf("(")>0)
			{
				version = fileName.substring(fileName.indexOf("(")+1,fileName.indexOf(")"));
			}
			else
			{
				//如果有发布源版本，取发布源版本，否则取零部件版本
				String sourceVersion = (String)PublishHelper.getIBAValue((IBAHolderIfc)part,"sourceVersion");
				if(sourceVersion==null||sourceVersion.equals(""))
				{
					version = part.getVersionValue();
				}
				else
				{
					version = sourceVersion;
				}
			}
			if(version.indexOf(".")!=-1)
			{
				version = version.substring(0,version.indexOf("."));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
  	return version;
  }
	
  
	/**
	 * 下载pdf文件到指定目录。
	 */
	public static void downloadPDF(String filePath, ApplicationDataInfo appInfo)throws Exception 
	{
		//System.out.println("fileVaultUsed=="+fileVaultUsed);
		//System.out.println("filePath=="+filePath);
		FileOutputStream fos = null;
		try
		{
			byte[] content = null;
			if(fileVaultUsed)
			{
				ContentClientHelper helper = new ContentClientHelper();
				content = helper.requestDownload(appInfo.getBsoID());
			}
			else
			{
				//获取流ID
				String streamID = appInfo.getStreamDataID();
				StreamDataInfo result = StreamUtil.getInfoHasContent(streamID);
				if(result==null)
				{
					return;
				}
				content = result.getDataContent();
			}
			
			FileOutputStream out = new FileOutputStream(new File(filePath));
			out.write(content);
			out.close();
		}
		catch (FileNotFoundException ex1)
		{
			ex1.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 检查字符串中是否含有英文逗号，防止导入文件引起窜行，去掉英文逗号。
	 */
	private static String qudouhao(String s)
	{
		s = s.replaceAll(",", "");
		return s;
	}
	
	
	/**
	 * 检查字符串中是否含有中文，有则去掉。
	 * Java判断一个字符串是否有中文是利用Unicode编码来判断，
	 * 因为中文的编码区间为：0x4e00--0x9fbb
	 */
	private static String checkCHNString(String s)
	{
		char[] cs = s.toCharArray();
		int length= cs.length; 
		
		//计算非中文字符数量，用来构造字符数组。
		int count = 0;
		for (int i = 0; i <length; i++)
		{
			char c = cs[i];
			if (!checkCNChar(c)) 
			{
				count++;
			}
		}
		
		char[] buf = new char[count];
		int j=0;
		for (int i = 0; i <length; i++)
		{
			char c = cs[i];
			if (!checkCNChar(c))
			{
				buf[j]=c;
				j++;
			}
		}
		return (new String(buf)).trim();
	}
	
	private static boolean checkCNChar(char oneChar)
	{
		if ((oneChar >= '\u4e00' && oneChar <= '\u9fa5')  || (oneChar >= '\uf900' && oneChar <= '\ufa2d'))
		{
			return true;
		}
		return false;
	} 
	
	/**
	 * 得到指定工艺内容容器中指定的数据项
   * @param priInfo 对象id 内容容器
   * @return Vector ApplicationDataInfo 内容项
   * @throws Exception 
   */
  public static Vector getContents(String id)throws Exception
  {
  	PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
  	QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(id);
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
    
	/**
	 * 将艺准报表，准备成普通文档
	 * @param parts Collection 零部件集合
	 * @return Group：返回的组对象
	 */
	public static Group prepareDocGroupForTechnicsRoute(Group gp, TechnicsRouteListInfo route, String path)throws Exception 
	{
		if (VERBOSE)
		{
			System.out.println("***   comming QQPartPublsih.prepareDocGroupForTechnicsRoute   ***");
		}
		if (route == null)
		{
			return gp;
		}
		if(gp == null)
		{
			gp = new Group();
		}
		
		try
		{
			File f = new File(path+getRouteListFileName(route.getRouteListNumber())+".xls");
			System.out.println("艺准文件名："+f.getPath());
			OutputStream out = new FileOutputStream(f);
			DownloadExcelManager dem = new DownloadExcelManager();
			dem.getFile(route.getBsoID(),out);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		
		Element ele = new Element();
		ele.addAtt(new Att("num", qudouhao(route.getRouteListNumber()))); // 文档编号
		ele.addAtt(new Att("doctype", "艺准文档"));
		ele.addAtt(new Att("name", qudouhao(route.getRouteListName()))); // 文档名称
		ele.addAtt(new Att("department", "")); // 文档的创建单位
		ele.addAtt(new Att("location", "")); // 文档的位置（文件夹）
		ele.addAtt(new Att("desc", qudouhao(route.getRouteListDescription().replaceAll("\n","")))); // 文档的描述
		ele.addAtt(new Att("filename", qudouhao(getRouteListFileName(route.getRouteListNumber())+".xls"))); // 文档的文件名称
		ele.addAtt(new Att("lifecyclestate", "RELEASED")); // 文档的生命周期状态，取零部件状态
		ele.addAtt(new Att("url", "http://jfpdm.faw.com/PhosphorPDM/route_look_routeList.screen?bsoID="+route.getBsoID())); // 解放艺准url
		ele.addAtt(new Att("partVersion", "")); // 零部件解放版本，此处为空串，文档此属性有值
		gp.addElement(ele);
		
		if (VERBOSE)
		{
			System.out.println("***   leaving QQPartPublsih.prepareDocGroupForTechnicsRoute   ***");
		}
		return gp;
	}
	
	
	/**
	 * 将数据发布确认通知书，准备成普通文档
	 * @param parts Collection 零部件集合
	 * @return Group：返回的组对象
	 */
	public static Group prepareDocGroupForConfirm(Group gp, String path, DocInfo doc)throws Exception 
	{
		if (VERBOSE)
		{
			System.out.println("***   comming QQPartPublsih.prepareDocGroupForConfirm   ***");
		}
		if (doc == null)
		{
			return gp;
		}
		if(gp == null)
		{
			gp = new Group();
		}
		
		Element ele = new Element();
		ele.addAtt(new Att("num", qudouhao("JFNOTICE"+doc.getDocNum()))); // 文档编号
		ele.addAtt(new Att("name", qudouhao("JFNOTICE"+doc.getDocName()))); // 文档名称
		ele.addAtt(new Att("department", "")); // 文档的创建单位
		ele.addAtt(new Att("location", "")); // 文档的位置（文件夹）
		ele.addAtt(new Att("lifecyclestate", "RELEASED")); // 文档的生命周期状态，取零部件状态
		ele.addAtt(new Att("url", "http://jfpdm.faw.com/PhosphorPDM/Doc-Bas-View-001.screen?bsoID="+doc.getBsoID())); // 解放文档url
		ele.addAtt(new Att("partVersion", "")); // 零部件版本，此处值为""
		
		String desc = "";
		String res = PublishHelper.isCaiYong(doc);
		String filename = "";
		String tongzhitype = "";
		//采用
		if(res.equals("true"))
		{
			tongzhitype = "采用";
			desc = PublishHelper.getCaiYongDesc(doc);
			//获取采用零部件
			Collection coll = PublishHelper.getPartCaiYong(doc);
			if(coll!=null)
			{
				Iterator iter = coll.iterator();
				while (iter.hasNext())
				{
					String[] str = (String[]) iter.next();
					desc = desc+" 【"+str[1]+"】";
				}
			}
		}
		else
		{
			tongzhitype = "更改";
			desc = PublishHelper.getChangeOrderOriDesc(doc);
			//获取所有更改单，然后得到所有附件。
			Collection coll = PublishHelper.getConfirmDoc(doc.getBsoID());
			if (coll != null && coll.size() != 0)
			{
				try
				{
					ContentService contentService = (ContentService)EJBServiceHelper.getService("ContentService");
					Iterator iter = coll.iterator();
					while (iter.hasNext())
					{
						//更改单
						DocIfc docIfc = (DocIfc) iter.next();
						Vector c = (Vector)contentService.getContents((ContentHolderIfc)docIfc);
						if(c!=null)
						{
							ContentItemIfc item;
							ApplicationDataInfo appDataInfo = null;
							for (Iterator iter1 = c.iterator(); iter1.hasNext(); )
							{
								item = (ContentItemIfc) iter1.next();
								if (item instanceof ApplicationDataInfo)
								{
									appDataInfo = (ApplicationDataInfo) item;
									downloadPDF(path + checkCHNString(appDataInfo.getFileName()), appDataInfo);
									if(filename.equals(""))
									{
										filename = checkCHNString(appDataInfo.getFileName());
									}
									else
									{
										filename = filename + ";" + checkCHNString(appDataInfo.getFileName());
									}
								}
							}
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		//ele.addAtt(new Att("desc", qudouhao(desc))); // 文档的描述
		ele.addAtt(new Att("desc", "")); // 文档的描述
		ele.addAtt(new Att("doctype", tongzhitype+"通知文档"));
		ele.addAtt(new Att("filename", qudouhao(filename))); // 文档的文件名称
		gp.addElement(ele);
		
		if (VERBOSE)
		{
			System.out.println("***   leaving QQPartPublsih.prepareDocGroupForConfirm   ***");
		}
		return gp;
	}
	
	
	/**
	 * 根据组织的数据集合，生成导入 零部件 文件所需要的字符串。
	 */
	public static StringBuffer getPartStringBuffer(Group gp)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append("#标识,零件名称,零件编号,零件类型,资料夹,生命周期状态"+"\n");
			Enumeration enumeration = gp.getElements();
			Element ele = null;
			//8基本属性，5IBA属性
			String num = null;
			String name = null;
			String partType = null;
			String path = null;
			String source = null;
			String unit = null;
			String lifecyclestate = null;
			String viewName = null;
			
			String jfVersion = null;
			String sourceVersion = null;
			String jfPartUrl = null;
			String jfPublish = null;
			String FAWTZHBB = null;
			String jfRouteUrl = null;
			while (enumeration.hasMoreElements())
			{
				ele = (Element) enumeration.nextElement();
				num = ((String) ele.getValue("num")).trim();
				name = ((String) ele.getValue("name")).trim();
				partType = ((String) ele.getValue("partType")).trim();
				path = ((String) ele.getValue("path")).trim();
				source = ((String) ele.getValue("source")).trim();
				unit = ((String) ele.getValue("unit")).trim();
				lifecyclestate = ((String) ele.getValue("lifecyclestate")).trim();
				viewName = ((String) ele.getValue("viewName")).trim();
				
				jfVersion = ((String) ele.getValue("jfVersion")).trim();
				sourceVersion = ((String) ele.getValue("sourceVersion")).trim();
				jfPartUrl = ((String) ele.getValue("jfPartUrl")).trim();
				jfPublish = ((String) ele.getValue("jfPublish")).trim();
				FAWTZHBB = ((String) ele.getValue("FAWTZHBB")).trim();
				jfRouteUrl = ((String) ele.getValue("jfRouteUrl")).trim();
				
				//Part,name,num,partType,path,lifecyclestate
				sb.append("Part,");
				sb.append(name + ",");
				sb.append(num + ",");
				sb.append(partType + ",");
				sb.append(path + ",");
				sb.append(lifecyclestate + ",\n");
				
				sb.append("IBAValue,jfVersion,"+jfVersion+"\n");
				sb.append("IBAValue,sourceVersion,"+sourceVersion+"\n");
				sb.append("IBAValue,jfPartUrl,"+jfPartUrl+"\n");
				sb.append("IBAValue,jfPublish,"+jfPublish+"\n");
				sb.append("IBAValue,FAWTZHBB,"+FAWTZHBB+"\n");
				sb.append("IBAValue,jfRouteUrl,"+jfRouteUrl+"\n");
				sb.append("endIBAHolder"+"\n");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sb;
	}
	
	/**
	 * 根据组织的数据集合，生成导入 零部件关联 文件所需要的字符串。
	 */
	public static StringBuffer getPartLinkStringBuffer(Group gp)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append("#标识,父件编号,子件编号,使用数量"+"\n");
			Enumeration enumeration = gp.getElements();
			Element ele = null;
			
			String parent = null;
			String child = null;
			String unit = null;
			String quantity = null;
			String version = null;
			while (enumeration.hasMoreElements())
			{
				ele = (Element) enumeration.nextElement();
				parent = ((String) ele.getValue("parent")).trim();
				child = ((String) ele.getValue("child")).trim();
				unit = ((String) ele.getValue("unit")).trim();
				quantity = ((String) ele.getValue("quantity")).trim();
				version = ((String) ele.getValue("parentVersion")).trim();
				
				sb.append("PartUsageLink,");
				sb.append(parent + ",");
				sb.append(child + ",");
				sb.append(quantity + ",");
				sb.append(version + ",\n");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sb;
	}
	
	
	/**
	 * 根据组织的数据集合，生成导入 文档 文件所需要的字符串。
	 */
	public static StringBuffer getDocStringBuffer(Group gp)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append("#标识,文档名称,文档编号,文档类别（枚举类型：艺准文档、通知文档、图纸文档）,资料夹（如果是零件图纸，则写零部件资料夹，如果是通知文档，则此项为空）,描述（说明）,附件名称,生命周期状态"+"\n");
			Enumeration enumeration = gp.getElements();
			Element ele = null;
			
			String num = null;
			String name = null;
			String doctype = null;
			String department = null;
			String location = null;
			String desc = null;
			String filename = null;
			String lifecyclestate = null;
			String partVersion = null;
			String docurl = null;
			while (enumeration.hasMoreElements())
			{
				ele = (Element) enumeration.nextElement();
				num = ((String) ele.getValue("num")).trim();
				name = ((String) ele.getValue("name")).trim();
				doctype = ((String) ele.getValue("doctype")).trim();
				department = ((String) ele.getValue("department")).trim();
				location = ((String) ele.getValue("location")).trim();
				desc = ((String) ele.getValue("desc")).trim();
				filename = ((String) ele.getValue("filename")).trim();
				lifecyclestate = ((String) ele.getValue("lifecyclestate")).trim();
				partVersion = ((String) ele.getValue("partVersion")).trim();
				docurl = ((String) ele.getValue("url")).trim();
				
				sb.append("Doc,");
				sb.append(name + ",");
				sb.append(num + ",");
				sb.append(doctype + ",");
				sb.append(location + ",");
				sb.append(desc + ",");
				sb.append(filename + ",");
				sb.append(lifecyclestate + ",");
				sb.append(partVersion + ",");
				sb.append(docurl + ",\n");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sb;
	}
	
	
	/**
	 * 根据组织的数据集合，生成导入 零部件文档关联 文件所需要的字符串。
	 */
	public static StringBuffer getPartDocLinkStringBuffer(Group gp)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append("#标识,零件编号,文档编号"+"\n");
			Enumeration enumeration = gp.getElements();
			Element ele = null;
			String num = null;
			String partVersion = null;
			while (enumeration.hasMoreElements())
			{
				ele = (Element) enumeration.nextElement();
				num = ((String) ele.getValue("num")).trim();
				partVersion = ((String) ele.getValue("partVersion")).trim();
				
				sb.append("PartDocLink,");
				sb.append(num + ",");
				sb.append(num + ",");
				sb.append(partVersion + ",\n");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return sb;
	}
	
	public static void exportFile(StringBuffer sb, String folderName, String filename)
	{
		System.out.println("folderName=="+folderName+"  and filename=="+filename);
		try
		{
			/*FileWriter filewriter = new FileWriter(folderName + filename + ".csv", false);//是否取代原有内容，true续写，false重写
			filewriter.write(sb.toString());
			filewriter.flush();
			filewriter.close();*/
			
			FileOutputStream fis=new FileOutputStream(folderName + filename + ".csv");
			OutputStreamWriter osw=new OutputStreamWriter(fis,"UTF-8");
			osw.write(sb.toString());
			osw.flush();
			osw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
   * 通过ftp方式上传文件文件夹
   * @param file 上传的文件夹
   * @throws Exception
   */
  private static void ftpLoad(File f)throws Exception
  {
  	System.out.println("ftpHost=="+ftpHost);
  	System.out.println("ftpUserName=="+ftpUserName);
  	System.out.println("ftpPath=="+ftpPath);
  	try
  	{
  		if(f.exists())
  		{
      	FTPClient ftp = new FTPClient();
      	int reply;
      	ftp.connect(ftpHost, ftpPort);
      	ftp.login(ftpUserName, ftpPassword);
      	ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
      	reply = ftp.getReplyCode();
      	if (!FTPReply.isPositiveCompletion(reply))
      	{
      		System.out.println("FTP目标服务器无法连接！");
      		ftp.disconnect();
      	}
      	boolean f1 = ftp.changeWorkingDirectory(ftpPath);
      	System.out.println("f1=="+f1);
      	//String newdir = f.getName().substring(2,f.getName().length());
      	//System.out.println("newdir=="+newdir);
      	ftp.makeDirectory(f.getName());
      	boolean f2 = ftp.changeWorkingDirectory(ftpPath+f.getName()+File.separator);
      	System.out.println("f2=="+f2);
      	//统计
      	long t1 = System.currentTimeMillis();
      	filesize = 0;
      	upload(f, ftp);
      	System.out.println("传输文件总大小:"+(filesize/1024/1024)+"M");
      	long t2 = System.currentTimeMillis();
        System.out.println("用时： "+(t2-t1)/1000+" 秒");
      	ftp.disconnect();
  		}
  	}
  	catch(Exception e)
  	{
  		System.out.println("传输文件："+f.getName()+" 时出错！");
  		//e.printStackTrace();
  		throw new Exception(e);
  	}
  }
  
  private static void upload(File file, FTPClient ftp) throws Exception
  {
  	if(file.isDirectory())
  	{
  		String[] files = file.list();
  		System.out.println("文件总数："+files.length);
  		int j = 1;
  		for (int i = 0; i < files.length; i++)
  		{
  			File file1 = new File(file.getPath()+File.separator+files[i] );
  			if(file1.isDirectory())
  			{
  				upload(file1, ftp);
  				ftp.changeToParentDirectory();
  			}
  			else
  			{
  				try
  				{
  					File file2 = new File(file.getPath()+File.separator+files[i]);
  					filesize = filesize+file2.length();
  					System.out.print("qqftp: file "+j+" is "+file2.getPath()+"  and total size is "+filesize);
  					j++;
  					FileInputStream input = new FileInputStream(file2);
  					ftp.storeFile(new String(file2.getName().getBytes("GBK"), "iso-8859-1"), input);
  					System.out.println("  完毕！");
  					input.close();
  			  }
  			  catch(Exception e)
  			  {
  			  	System.out.println("出错了："+j);
  			  	e.printStackTrace();
  			  	throw new Exception(e);
  			  }
  			}
  		}
  	}
  	else
  	{
  		File file2 = new File(file.getPath());
  		FileInputStream input = new FileInputStream(file2);
  		ftp.storeFile(new String(file2.getName().getBytes("GBK"), "iso-8859-1"), input);
  		input.close();
  	}
  }
  
	/**
   * 通过ftp方式上传文件文件夹
   * @param file 上传的文件夹
   * @throws Exception
   */
  private static void ftpComplete(File f) throws Exception
  {
  	try
  	{
  		if(f.exists())
  		{
      	FTPClient ftp = new FTPClient();
      	int reply;
      	ftp.connect(ftpHost, ftpPort);
      	ftp.login(ftpUserName, ftpPassword);
      	ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
      	reply = ftp.getReplyCode();
      	if (!FTPReply.isPositiveCompletion(reply))
      	{
      		System.out.println("FTP目标服务器无法连接！");
      		ftp.disconnect();
      	}
      	boolean f1 = ftp.changeWorkingDirectory(ftpPath);
      	ftp.makeDirectory(f.getName());
      	boolean f2 = ftp.changeWorkingDirectory(ftpPath+f.getName()+File.separator);
      	long t1 = System.currentTimeMillis();
      	
      	String sendCompletepath = qqPublishPath + "sendComplete" + File.separator + "sendComplete.txt";
      	File file2 = new File(sendCompletepath);
      	FileInputStream input = new FileInputStream(file2);
      	ftp.storeFile(new String(file2.getName().getBytes("GBK"), "iso-8859-1"), input);
      	input.close();
      	long t2 = System.currentTimeMillis();
        System.out.println("传输完成标记文件用时： "+(t2-t1)/1000+" 秒");
      	ftp.disconnect();
  		}
  	}
  	catch(Exception e)
  	{
  		System.out.println("传输文件："+f.getName()+" 时出错！");
  		//e.printStackTrace();
  		throw new Exception(e);
  	}
	}
  
  private static String getRouteListFileName(String fname)
  {
  	try
  	{
			if(fname.indexOf("前准")!=-1)
			{
				fname = fname.replaceAll("前准","QZ");
			}
			if(fname.indexOf("临准")!=-1)
			{
				fname = fname.replaceAll("临准","LZ");
			}
			if(fname.indexOf("艺试准")!=-1)
			{
				fname = fname.replaceAll("艺试准","YSZ");
			}
			if(fname.indexOf("艺准")!=-1)
			{
				fname = fname.replaceAll("艺准","YZ");
			}
			if(fname.indexOf("艺毕")!=-1)
			{
				fname = fname.replaceAll("艺毕","YB");
			}
			if(fname.indexOf("艺废")!=-1)
			{
				fname = fname.replaceAll("艺废","YF");
			}
			
			String reg = "[\u4e00-\u9fa5]";
			Pattern pat = Pattern.compile(reg);
			Matcher mat=pat.matcher(fname);
			fname = mat.replaceAll("");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return fname;
  }
  
  public static DocInfo createLogDoc(String fname, String id)
  {
  	System.out.println("createLogDoc  begin  fname=="+fname);
		
		// 创建文档，将生成的文件作为文档的主要文件
		DocInfo docInfo = null;
		PersistService ps = null;
		StandardDocService ser = null;
		ContentService cser = null;
		//UsersService userservice = null;
		SessionService sservice = null;
		UserIfc user = null;
		String tempNum = "";
		try 
		{
			docInfo = PublishHelper.getDocInfoByNumber(getConfirmationNumber(fname));
			ps = (PersistService) EJBServiceHelper.getService("PersistService");
			ser = (StandardDocService) PublishHelper.getEJBService("StandardDocService");
			cser = (ContentService) PublishHelper.getEJBService("ContentService");
			sservice = (SessionService) PublishHelper.getEJBService("SessionService");
			//userservice = (UsersService) PublishHelper.getEJBService("UsersService");
			//user = userservice.getUserValueInfo("Administrator");
			user = (UserIfc) sservice.getCurUserInfo();
			System.out.println("user=="+user);
			String docCfBsoID = PublishHelper.getDocCf("其它类\\数据发布接收日志");
			DocFormData formData = new DocFormData();
			if (docInfo == null)
			{
				formData.setDocumentAttribute("docName", "青汽数据发布日志－" + fname);
				formData.setDocumentAttribute("docNum", getConfirmationNumber(fname));// 设置文档编号
				formData.setDocumentAttribute("lifecycleTemplate", "青汽数据发布接收生命周期");
				formData.setDocumentAttribute("createDept", "系统");// 设置创建单位
				formData.setDocumentAttribute("docCfBsoID", docCfBsoID);
				formData.setDocumentAttribute("folder", "\\Root\\数据接收其它");// 设置资料夹
				formData.setDocumentAttribute("lifeCState", LifeCycleState.toLifeCycleState("RECEIVING"));// 设置生命周期状态 接收
				formData.setDocumentAttribute("contDesc", id);
				if (user != null)
				{
					formData.setDocumentAttribute("iterationCreator", user.getBsoID());
					formData.setDocumentAttribute("iterationModifier", user.getBsoID());
					formData.setDocumentAttribute("aclOwner", user.getBsoID());
					formData.setDocumentAttribute("creator", user.getBsoID());
				}
				Vector docvec = (Vector) ser.createDoc(formData);
				docInfo = (DocInfo)docvec.elementAt(0);
				docInfo = (DocInfo) ps.refreshInfo(docInfo);
			}
			else
			{
				//停流程后修订。
				WfEngineService engineservice = (WfEngineService) EJBServiceHelper.getService("WfEngineService");
				IteratorVector vec = (IteratorVector) engineservice.getAssociatedProcesses(docInfo.getBsoID(), null);
				Object obj[] = vec.toArray();
				for (int i = 0; i < obj.length; i++)
        {
        	WfProcessIfc process = (WfProcessIfc) obj[i];
        	engineservice.changeState(process, WfTransition.TERMINATE);
        }

				docInfo = (DocInfo) ps.refreshInfo(docInfo.getBsoID());
				docInfo.setLifeCycleState(LifeCycleState.toLifeCycleState("RECEIVING"));
				docInfo.setContDesc(id);
				docInfo = (DocInfo) ((StandardDocService) PublishHelper.getEJBService("StandardDocService")).reviseDoc(docInfo, formData);
				Vector v = cser.getContents(docInfo);
				ContentItemInfo item;
				for (Iterator iter = v.iterator(); iter.hasNext();) 
				{
					item = (ContentItemInfo) iter.next();
					if (item instanceof ApplicationDataInfo) 
					{
						cser.deleteApplicationData(docInfo, (ApplicationDataInfo) item);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("createLogDoc  end!");
		return docInfo;
	}
	
	//发布失败后，为日志文档设置成“发布失败状态”
	public static void setFailState(String id)
	{
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			DocInfo docinfo = (DocInfo) ps.refreshInfo(id);
			LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");
			lservice.setLifeCycleState(docinfo,LifeCycleState.toLifeCycleState("PUBLISHFAIL"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
  public static void addAppForLogDoc(DocInfo docInfo, String fname, String curPath, String state)
  {
  	System.out.println("addAppForLogDoc  begin  fname=="+fname);
		try 
		{
			ContentService cser = (ContentService) PublishHelper.getEJBService("ContentService");
			//主要文件，发布日志
			String filename = qqPublishPath + fname + ".txt";
			File file = new File(filename);
			if(fileVaultUsed)
			{
				ContentClientHelper helper = new ContentClientHelper();
				ApplicationDataInfo app = helper.requestUpload(file);
				app.setFileName(fname+".txt");
				app.setUploadPath(filename);
				app = (ApplicationDataInfo) cser.uploadPrimaryContent(docInfo, app);
			}
			else
			{
				ApplicationDataInfo app = new ApplicationDataInfo();
				app.setFileName(fname+".txt");
				app.setUploadPath(filename);
				app.setFileSize(file.length());
				ApplicationDataInfo datainfo = (ApplicationDataInfo) cser.uploadPrimaryContent(docInfo, app);
				String streamid = datainfo.getStreamDataID();
				DataInputStream dataInputStream = null;
				byte[] data = null;
				data = new byte[(int) file.length()];
				dataInputStream = new DataInputStream(new FileInputStream(file));
				dataInputStream.read(data);
				dataInputStream.close();
				StreamUtil.writeData(streamid, data);
			}
			
			if(state.equals("fail"))
			{
				return;
			}
			
			//附加文件 csv文件
			File f = new File(curPath);
  		if(f.exists()&&f.isDirectory())
  		{
  			String[] files = f.list();
  			for (int i = 0; i < files.length; i++)
  			{
  				File file1 = new File(f.getPath()+File.separator+files[i] );
  				if(!file1.getName().endsWith(".csv")&&!file1.getName().endsWith(".xls"))
  				{
  					continue;
  				}
  				if(fileVaultUsed)
  				{
  					ContentClientHelper helper = new ContentClientHelper();
  					ApplicationDataInfo app = helper.requestUpload(file1);
  					app.setFileName(file1.getName());
  					app.setUploadPath(file1.getPath());
  					app.setFileSize(file1.length());
  					app = (ApplicationDataInfo) cser.uploadContent(docInfo, app);
  				}
  				else
  				{
  					ApplicationDataInfo app = new ApplicationDataInfo();
  					app.setFileName(file1.getName());
  					app.setUploadPath(file1.getPath());
  					app.setFileSize(file1.length());
  					ApplicationDataInfo datainfo = (ApplicationDataInfo) cser.uploadContent(docInfo, app);
  					String streamid = datainfo.getStreamDataID();
  					DataInputStream dataInputStream = null;
  					byte[] data = null;
  					data = new byte[(int) file1.length()];
  					dataInputStream = new DataInputStream(new FileInputStream(file1));
  					dataInputStream.read(data);
  					dataInputStream.close();
  					StreamUtil.writeData(streamid, data);
  				}
  			}
  	  }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("addAppForLogDoc  end!");
	}
	
	
	private static String getConfirmationNumber(String noteNumber)
	{
		return "QQPUBLISH-" + noteNumber;
	}
	
	/**
	 * 形成文件
	 * @param buffer StringBuffer 字符容器
	 * @param path String 生成文件的路径
	 * @throws Exception
	 */
	public static void writeFile(StringBuffer buffer, String path)
	{
		try
		{
			FileWriter writer = new FileWriter(path);
			writer.write(buffer.toString());
			writer.flush();
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 发布后删除文件
	 */
  public static void deleteFile(File f) throws QMException 
  {
  	try
  	{
  		if(f.exists())
  		{
  			if(f.isFile())
  			{
  				f.delete();
  			}
  			else if(f.isDirectory())
  			{
  				File f1[] = f.listFiles();
  				for(int i=0;i<f1.length;i++)
  				{
  					deleteFile(f1[i]);
  				}
  			}
  			//System.out.println("删除的文件==="+f.getPath());
  			f.delete();
  		}
  		else
  		{
  			System.out.println("要删除的文件不存在！"+f.getPath());
  		}
  	}
  	catch(Exception ex)
  	{
  		ex.printStackTrace();
  	}
  }
}
