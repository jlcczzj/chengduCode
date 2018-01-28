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
 * <p>Title: �������ϵͳ���ݵ��ൺ��</p>
 * <p>Description: ���ݼ������ĵĲ��á�����������׼���в�����ʽ�����㲿����bom��pdf����������ȥ��
 * �磺P25��JH6��P62��J6L��P63��J6M��PK��JL01
 * ����P26</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: ����</p>
 * @author ���
 * @version 1.0
 * 2016.4.21�޸ģ�
 * 1���ļ�������������
 * 2�������ļ��ı�����Ʋ�������Ӣ�Ķ���
 * 3����ͼ��ʾ�����ͼֽ��
 * 4������ȷ��֪ͨ���ĵ�  ��˵�� ��ʾ ����+������ȷ�����š�
 * 2016-11-30
 * 1.D-QZ4250P26K24T3E4-1-6.csv ����2�У��㲿���汾������׼�����ġ����á�֪ͨ�ĵ��Ľ��URL��ַ��
 * 2.R-QZ4250P26K24T3E4-1-6.csv ���һ�����ӡ�����汾����Ű汾����
 * 3.U-QZ4250P26K24T3E4-1-6.csv ���һ�����ӡ������汾(��Ű汾)��
 * ���ĺͲ���֪ͨ�ĵ���˵���������ﲻд���ͺ��ˣ��Ѹ��ĺͲ��õ�˵���������ÿ��ַ�������
 * 2016-12-1
 * 1.��׼����ȡ��·�ߵ��㲿����
 * 2.��׼֪ͨ�������ġ����õ��з������ϵͳ��ʱʱ��ڵ�����°汾�����ͬһ���������巢��ͬһ����Ĳ�ͬ�汾����
 * 3.�����ൺ�����ݣ���֤һ���������̲�����ͬһ������Ĳ�ͬ�汾���ݡ�
 * 4.�޸�ԭ���߼�����������������壨���õ�����������а������������һ�������Ϸ��������Ĺ�������������������������������
 *   �޸ĺ��߼����������������������ݣ�ֻ�������з��Ϸ������������ݣ������Ϲ��������Զ����˵����������ൺ�����������жϣ�
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
   * ��ȡ�����м��ķ���������Ϣ��
   */
  public void checkTableData() throws QMException
  {
  	System.out.println("���������������ʼ>........ʱ��["+ new java.util.Date().toLocaleString()+ "]");
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
        System.out.println("û�����ڷ��͵ķ������̣�׼��������Ҫ���������񣡣�");
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
          System.out.println("�õ���Ҫ���������ݣ�");
          testPublish(rs.getString(1), publishcount);
        }
        else
        {
        	System.out.println("û�еõ���Ҫ���������ݣ�");
        }
      }
      else if (countId == 1)
      {
        System.out.println("�����ڷ��͵ķ������̣���鷢����ʱ��");
        rs = stmt.executeQuery("select transdatetime from qqpublishmonitor where transflag='true'");
        rs.next();
        int td = Integer.parseInt(rs.getString(1));//��������ʼʱ��
        Calendar calendar = Calendar.getInstance();
        int cd = calendar.get(Calendar.HOUR_OF_DAY);//��ǰʱ��
        //�����������δ��2��Сʱ���������أ��ȴ��´δ���
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
        	System.out.println("��ʱ�����������˴μ�أ���");
        }
        //������������Ѿ�ִ��2��Сʱ�������á�
        else
        {
        	publishInit();
        	System.out.println("��ʱ��ʱ�����÷������ݣ���");
        }
      }
      else
      {
        System.out.println("�ж�����ڷ��͵ķ������̣������ˣ���");
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
		System.out.println("������������������!");
  }
  
  
	public void publishInit()
	{
		System.out.println("���� transflag ��ǣ� ��ʼ����");
		
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
		System.out.println("���� transflag ��ǣ� ��������");
	}
	
  
  public static void savedata(String[] str)
  {
    System.out.println("�����ݷ����м�����½����ݣ�����");
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
    System.out.println("�����ݷ����м���У����ݱ�Ż�ȡ���ݣ�����");
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
    System.out.println("�����ݷ����м���и������ݣ�����");
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
			//ȷ��֪ͨ��
			if(id.startsWith("Doc"))
			{
				DocInfo info = (DocInfo) ps.refreshInfo(id);
				publishConfirmDoc((BaseValueIfc)info, publishcount);
				result = "true";
			}
			//���ĵ�
			else if(id.startsWith("Borrow"))
			{
				BorrowInfo info = (BorrowInfo) ps.refreshInfo(id);
				publishBorrow((BaseValueIfc)info, publishcount);
				result = "true";
			}
			//��׼
			else if(id.startsWith("TechnicsRouteList"))
			{
				TechnicsRouteListInfo info = (TechnicsRouteListInfo) ps.refreshInfo(id);
				publishTechnicRoute((BaseValueIfc)info, publishcount);
				result = "true";
			}
			//���ͱ�ţ�����Ҫ�ط�
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
				result = "�����м��������ɣ�";
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
	 * �������ݷ���ȷ��֪ͨ�飬��鷢�����ݣ���Ҫ���������м��
	 */
	public static void publishConfirmDocCheck(BaseValueIfc primaryBusinessObject)
	{
		DocInfo info = (DocInfo)primaryBusinessObject;
		//System.out.println("����������飺"+info.getDocNum()+"=="+info.getBsoID());
		System.out.println("����������"+info.getDocNum()+"=="+info.getBsoID());
		try
		{
			//if(checkConfirmDoc(info).equals("true"))
			//{
				
				savedata(new String[]{info.getDocNum(),info.getBsoID(),"false","false"});
			//}
			//else
			//{
				//System.out.println("�����Ϸ����������������м��������׼��!");
			//}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * ������׼����鷢�����ݣ���Ҫ���������м��
	 */
	public static void publishTechnicRouteCheck(BaseValueIfc primaryBusinessObject)
	{
		TechnicsRouteListInfo info = (TechnicsRouteListInfo)primaryBusinessObject;
		System.out.println("����������飺"+info.getRouteListNumber()+"=="+info.getBsoID());
		try
		{
			if(checkTechnicsRoute(info).equals("true"))
			{
				
				savedata(new String[]{info.getRouteListNumber(),info.getBsoID(),"false","false"});
			}
			else
			{
				System.out.println("�����Ϸ����������������м��������׼��!");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * ���ݽ��ĵ�����鷢�����ݣ���Ҫ���������м��
	 */
	public static void publishBorrowCheck(BaseValueIfc primaryBusinessObject)
	{
		BorrowInfo info = (BorrowInfo)primaryBusinessObject;
		System.out.println("����������飺"+info.getBorrowNumber()+"=="+info.getBsoID());
		try
		{
			if(checkBorrow(info).equals("true"))
			{
				
				savedata(new String[]{info.getBorrowNumber(),info.getBsoID(),"false","false"});
			}
			else
			{
				System.out.println("�����Ϸ����������������м��������׼��!");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * �������ݷ���ȷ��֪ͨ�飬�������ݡ�
	 */
	public static void publishConfirmDoc(BaseValueIfc primaryBusinessObject, String publishcount) throws Exception
	{
		String result = "false";
		long t1 = System.currentTimeMillis();
		StringBuffer buffer = new StringBuffer();
		DocInfo info = (DocInfo)primaryBusinessObject;
		DocInfo docinfo = null;
		updatedata(info.getDocNum(),info.getBsoID(),"false","true",publishcount);
		System.out.println("����������"+info.getDocNum()+"=="+info.getBsoID());
		String curPath = "";
		try
		{
			buffer.append("���ݷ�����ʼ��\n");
			buffer.append("���ͣ����ݷ���ȷ��֪ͨ��\n");
			String sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			buffer.append("����׼����ʼʱ�䣺"+sendData+"\n");
			
			//У������
			//�Ƿ������ݷ���ȷ��֪ͨ��
			//����������飬������Ҫ��Ľ��з���
			/*if(PublishHelper.isConfirmDoc(info).equals("true"))
			{
				//�������㲿���Ƿ���Ϸ���Ҫ��
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
				//�������㲿���Ƿ���Ϸ���Ҫ��
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
			
			//��������
			System.out.println("parts.size()==="+parts.size());
			if(parts.size()>0)
			{
				docinfo = createLogDoc(info.getDocNum(), info.getBsoID());
				parts = getAllParts(parts);
				System.out.println("parts.size() 111 ==="+parts.size());
				//׼���㲿��
				Group partGroup = preparePartsGroup(parts);
				buffer.append("�㲿��������"+partGroup.getElementCount()+"\n");
				exportFile(getPartStringBuffer(partGroup),curPath, "P-"+info.getDocNum());
				//partGroup.toXML(new PrintWriter(System.out), true);
				//׼���㲿������
				Group linkGroup = preparePartLinksGroup(parts);
				buffer.append("�㲿����������"+linkGroup.getElementCount()+"\n");
				exportFile(getPartLinkStringBuffer(linkGroup),curPath, "U-"+info.getDocNum());
				//linkGroup.toXML(new PrintWriter(System.out), true);
				//׼��pdf�ĵ�
				Group docGroup = prepareDocsGroup(parts, curPath);
				buffer.append("�ĵ�������"+docGroup.getElementCount()+"\n");
				//׼���㲿���ĵ�����
				exportFile(getPartDocLinkStringBuffer(docGroup),curPath, "R-"+info.getDocNum());
				//׼�����ݷ���ȷ��֪ͨ���ĵ�
				docGroup = prepareDocGroupForConfirm(docGroup, curPath, info);
				exportFile(getDocStringBuffer(docGroup),curPath, "D-"+info.getDocNum());
				
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("����׼�����ʱ�䣺"+sendData+"\n");
				
				//ftp���ļ�
				ftpLoad(f);
				//ftp������ɱ���ļ�
				ftpComplete(f);
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("���ݴ������ʱ�䣺"+sendData+"\n");
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
			buffer.append("���ݷ���ʧ�ܣ��벹������鿴����󲹷���\n");
			buffer.append("�쳣��Ϣ��"+ex.getLocalizedMessage()+"\n");
			writeFile(buffer, qqPublishPath + info.getDocNum() + ".txt");
			if(docinfo!=null)
			{
				addAppForLogDoc(docinfo, info.getDocNum(), curPath, "fail");
			}
			setFailState(docinfo.getBsoID());
		}
		long t2 = System.currentTimeMillis();
    System.out.println("�˴η�������ȷ��֪ͨ����ʱ�� "+(t2-t1)/1000+" ��");
	}
	
	
	/**
	 * ������׼���������ݡ�
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
		  System.out.println("����������"+info.getRouteListNumber()+"=="+info.getBsoID());
		  
			buffer.append("���ݷ�����ʼ��\n");
			buffer.append("���ͣ���׼\n");
			String sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			buffer.append("����׼����ʼʱ�䣺"+sendData+"\n");
			
			//У������
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
			
			//��������
			System.out.println("parts.size()==="+parts.size());
			if(parts.size()>0)
			{
				docinfo = createLogDoc(fname, info.getBsoID());
				parts = getAllParts(parts);
				System.out.println("parts.size() 111 ==="+parts.size());
				Group partGroup = preparePartsGroup(parts);
				buffer.append("�㲿��������"+partGroup.getElementCount()+"\n");
				exportFile(getPartStringBuffer(partGroup),curPath, "P-"+fname);
				//partGroup.toXML(new PrintWriter(System.out), true);
				Group linkGroup = preparePartLinksGroup(parts);
				buffer.append("�㲿����������"+linkGroup.getElementCount()+"\n");
				exportFile(getPartLinkStringBuffer(linkGroup),curPath, "U-"+fname);
				//linkGroup.toXML(new PrintWriter(System.out), true);
				//׼��pdf�ĵ�
				Group docGroup = prepareDocsGroup(parts, curPath);
				buffer.append("�ĵ�������"+docGroup.getElementCount()+"\n");
				//׼���㲿���ĵ�����
				exportFile(getPartDocLinkStringBuffer(docGroup),curPath, "R-"+fname);
				//׼�����ݷ���ȷ��֪ͨ���ĵ�
				docGroup = prepareDocGroupForTechnicsRoute(docGroup, info, curPath);
				exportFile(getDocStringBuffer(docGroup),curPath, "D-"+fname);
				
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("����׼�����ʱ�䣺"+sendData+"\n");
				writeFile(buffer, qqPublishPath + fname + ".txt");
				
				//ftp���ļ�
				ftpLoad(f);
				//ftp������ɱ���ļ�
				ftpComplete(f);
				
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("���ݴ������ʱ�䣺"+sendData+"\n");
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
			buffer.append("���ݷ���ʧ�ܣ��벹������鿴����󲹷���\n");
			buffer.append("�쳣��Ϣ��"+ex.getLocalizedMessage()+"\n");
			writeFile(buffer, qqPublishPath + fname + ".txt");
			if(docinfo!=null)
			{
				addAppForLogDoc(docinfo, fname, curPath, "fail");
			}
			setFailState(docinfo.getBsoID());
		}
		long t2 = System.currentTimeMillis();
    System.out.println("�˴η�������·����ʱ�� "+(t2-t1)/1000+" ��");
	}
	
	
	/**
	 * ���ݽ��ĵ����������ݡ�
	 */
	public static void publishBorrow(BaseValueIfc primaryBusinessObject, String publishcount)
	{
		String result = "false";
		long t1 = System.currentTimeMillis();
		StringBuffer buffer = new StringBuffer();
		BorrowInfo info = (BorrowInfo)primaryBusinessObject;
		updatedata(info.getBorrowNumber(),info.getBsoID(),"false","true",publishcount);
		System.out.println("����������"+info.getBorrowNumber()+"=="+info.getBsoID());
		DocInfo docinfo = null;
		String curPath = "";
		try
		{
			buffer.append("���ݷ�����ʼ��\n");
			buffer.append("���ͣ�����\n");
			String sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			buffer.append("����׼����ʼʱ�䣺"+sendData+"\n");
			
			//У������
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
			
			//��������
			System.out.println("parts.size()==="+parts.size());
			if(parts.size()>0)
			{
				docinfo = createLogDoc(info.getBorrowNumber(), info.getBsoID());
				parts = getAllParts(parts);
				System.out.println("parts.size() 111 ==="+parts.size());
				Group partGroup = preparePartsGroup(parts);
				buffer.append("�㲿��������"+partGroup.getElementCount()+"\n");
				exportFile(getPartStringBuffer(partGroup),curPath, "P-"+info.getBorrowNumber());
				//partGroup.toXML(new PrintWriter(System.out), true);
				Group linkGroup = preparePartLinksGroup(parts);
				buffer.append("�㲿����������"+linkGroup.getElementCount()+"\n");
				exportFile(getPartLinkStringBuffer(linkGroup),curPath, "U-"+info.getBorrowNumber());
				//linkGroup.toXML(new PrintWriter(System.out), true);
				//׼��pdf�ĵ�
				Group docGroup = prepareDocsGroup(parts, curPath);
				buffer.append("�ĵ�������"+docGroup.getElementCount()+"\n");
				exportFile(getDocStringBuffer(docGroup),curPath, "D-"+info.getBorrowNumber());
				//׼���㲿���ĵ�����
				exportFile(getPartDocLinkStringBuffer(docGroup),curPath, "R-"+info.getBorrowNumber());
				
				
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("����׼�����ʱ�䣺"+sendData+"\n");
				
				//ftp���ļ�
				ftpLoad(f);
				//ftp������ɱ���ļ�
				ftpComplete(f);
				sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				buffer.append("���ݴ������ʱ�䣺"+sendData+"\n");
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
			buffer.append("���ݷ���ʧ�ܣ��벹������鿴����󲹷���\n");
			buffer.append("�쳣��Ϣ��"+ex.getLocalizedMessage()+"\n");
			writeFile(buffer, qqPublishPath + info.getBorrowNumber() + ".txt");
			if(docinfo!=null)
			{
				addAppForLogDoc(docinfo, info.getBorrowNumber(), curPath, "fail");
			}
			setFailState(docinfo.getBsoID());
		}
		long t2 = System.currentTimeMillis();
    System.out.println("�˴η������ĵ���ʱ�� "+(t2-t1)/1000+" ��");
	}
	
	
	/**
	 * �������ݷ���ȷ��֪ͨ�飬��ȡ�����㲿�����ϡ�
	 */
	public static Collection getPartsByConfirmDoc(BaseValueIfc primaryBusinessObject)
	{
		Collection parts = new ArrayList();
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			DocIfc ifc = (DocIfc)primaryBusinessObject;
			String res = PublishHelper.isCaiYong(ifc);
			//����
			if(res.equals("true"))
			{
				Collection coll = PublishHelper.getPartCaiYong(ifc);
				Iterator ite = coll.iterator();
				while (ite.hasNext())
				{
					String[] str = (String[]) ite.next();
					QMPartIfc part = (QMPartIfc) ps.refreshInfo(str[0]);
					part = getLatestPart(part);
					if(part.getViewName().trim().equals("���������ͼ"))
        	{
        		if(!checkPart(part))
        		{
        			continue;
        		}
        		parts.add(part);
        	}
				}
			}
			//���
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
						if(part.getViewName().trim().equals("���������ͼ"))
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
	 * ������ݷ���ȷ��֪ͨ����������Ƿ��Ƿ���Ҫ����㲿������������������P25��JH6��P62��J6L��P63��J6M��PK��JL01��P26
	 * ���򷵻� true��ֱ�ӷ�����
	 * ���򷵻� false����Ҫ���������̣�Ȼ������Ƿ񷢲���
	 */
	/*public static String checkConfirmDoc(BaseValueIfc primaryBusinessObject)
	{
		try
		{
			PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
			DocIfc ifc = (DocIfc)primaryBusinessObject;
			String res = PublishHelper.isCaiYong(ifc);
			//����
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
			//���
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
	 * ������׼֪ͨ�飬��ȡ�����㲿�����ϡ�
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
        	if(!temp.contains(part.getBsoID())&&part.getViewName().trim().equals("���������ͼ"))
        	{
        		parts.add(part);
        		temp.add(part.getBsoID());
        	}
        }
        else
        {
        	System.out.println(link.getPartBranchID()+"û���ҵ���Ӧ�㲿����");
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
	 * ������ݷ���ȷ��֪ͨ����������Ƿ��Ƿ���Ҫ����㲿������������������P25��JH6��P62��J6L��P63��J6M��PK��JL01��P26
	 * ���򷵻� true��ֱ�ӷ�����
	 * ���򷵻� false����Ҫ���������̣�Ȼ������Ƿ񷢲���
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
	        //������������������Ӧ�Ľ�ɫ�Ͳ����ߣ����浽HashMap�У�����key�ǽ�ɫ����value�ǲ����ߡ�
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
		            					    if(userName.indexOf("S����-��׼")!=-1)
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
	 * �������ݡ�
	 * ʹ�����еĽ��ĵ����󣬻�ȡ������㲿��
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
        if(part!=null&&part.getViewName().trim().equals("���������ͼ"))
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
	 * ��鲹�����������Ƿ��Ƿ���Ҫ����㲿������������������P25��JH6��P62��J6L��P63��J6M��PK��JL01��P26
	 * ���򷵻� true��ֱ�ӷ�����
	 * ���򷵻� false����Ҫ���������̣�Ȼ������Ƿ񷢲���
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
	 * ����㲿���Ƿ���P25��JH6��P62��J6L��P63��J6M��PK��JL01��P26������
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
	 * �����㲿�����ϣ���ȡ���е�ȫ�ṹ���ϡ�
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
  	ViewObjectIfc viewObjectIfc=vs.getView("���������ͼ");
  	partStandardConfigSpec.setViewObjectIfc(viewObjectIfc);
  	partStandardConfigSpec.setLifeCycleState(null);
  	partStandardConfigSpec.setWorkingIncluded(true);
  	configSpec.setStandard(partStandardConfigSpec);
  	return configSpec;
  }
  
  
	/**
	 * �����㲿������ȡ����С�汾��
	 */
	public static QMPartIfc getLatestPart(QMPartIfc part)
	{
		try
		{
			VersionControlService vs = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
			if(!vs.isLatestIteration(part)||!vs.isLatestVersion(part))
			{
				System.out.print(part.getPartNumber()+" �������°棬��ǰ�汾"+part.getVersionValue());
				part = (QMPartIfc)vs.getLatestVersion(part);
				System.out.println("     ��ȡ�汾"+part.getVersionValue());
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return part;
	}
  
	
	/**
	 * ���������㲿�����ϣ�������㲿������������������ļ��ϣ��������������ļ���
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
	 * ���㲿����ʹ�ù�ϵ��װ��һ��Ԫ�أ�Element�������С�
	 * @param link PartUsageLinkIfc �㲿����ʹ�ù�ϵ
	 * @return Element�����ص�Ԫ�ض���
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
		  QMPartIfc parent = (QMPartIfc)ps.refreshInfo(link.getRightBsoID()); // ��ȡ����
		  QMPartMasterIfc child = (QMPartMasterIfc)ps.refreshInfo(link.getLeftBsoID()); // ��ȡ�Ӽ�
		  if(!partlist.contains(child.getPartNumber()))
		  {
		  	return null;
		  }
		  ele.addAtt(new Att("parent", qudouhao("JF-"+parent.getPartNumber()))); // �������
		  ele.addAtt(new Att("child", qudouhao("JF-"+child.getPartNumber()))); // �Ӽ����
		  ele.addAtt(new Att("unit", link.getDefaultUnit().toString())); // ��λ
		  ele.addAtt(new Att("quantity", Double.toString(link.getQuantity()))); // �Ӽ�����
		  ele.addAtt(new Att("parentVersion", parent.getVersionValue())); // �����Ľ�Ű汾
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ele;
	}
	
	
  /**
	 * ׼���㲿���飨�������岿����
	 * @param parts Collection����Ҫ��װ�����е��㲿����
	 * @return Group�����ص��㲿����
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
	 * ��һ���㲿������Ϣ��װ��һ��Ԫ�أ�Element��������
	 * @param part QMPartIfc �㲿��
	 * @return Element�����ص�Ԫ�ض���
	 */
	private static Element preparePart(QMPartIfc part)
	{
		if (part == null)
		{
			return null;
		}
		//�㲿�����/����/����/���ϼ�/��������״̬��������ʾ����
		//ʣ�¾����㲿����5����չ���Զ���Ҫ

		Element ele = new Element();
		//�Ի������Է�����
		ele.addAtt(new Att("num", qudouhao("JF-"+part.getPartNumber()))); // ���
		ele.addAtt(new Att("name", qudouhao(part.getPartName()))); // ����
		ele.addAtt(new Att("partType", getPartType(part))); // ����
		ele.addAtt(new Att("source", part.getProducedBy().toString())); // ��Դ
		ele.addAtt(new Att("unit", part.getDefaultUnit().toString())); // ��λ
		ele.addAtt(new Att("path", part.getLocation())); // �ļ���·��
		ele.addAtt(new Att("viewName", part.getViewName())); // ��ͼ��
		ele.addAtt(new Att("lifecyclestate", part.getLifeCycleState().toString())); // ��������״̬
		
		//��IBA���Է����ģ���ŷ���Դ�汾�����ķ���Դ�汾������㲿��URL����ŷ�����š������׼URL
		String[] str = getPartIba(part);//��ȡ ���ķ���Դ�汾 �� ��ŷ������ �� ��׼���
		ele.addAtt(new Att("jfVersion", part.getVersionValue())); // ��ŷ���Դ�汾
		ele.addAtt(new Att("sourceVersion", str[0])); // ���ķ���Դ�汾
		ele.addAtt(new Att("jfPartUrl", "http://jfpdm.faw.com/PhosphorPDM/Part-Other-PartLookOver-001.screen?bsoID="+part.getBsoID())); // ����㲿��URL
		ele.addAtt(new Att("jfPublish", str[1])); // ��ŷ������
		ele.addAtt(new Att("FAWTZHBB", getTuZhiDes(part)));//��¼�Ƿ���ͼֽ������ FAWTZHBB  ���û��ͼֽ��¼��WUA��,��ͼֱֽ��дͼֽ���ơ�
		String routeID = getRouteBsoID(str[2]);
		if(routeID==null||routeID.equals(""))
		{
			ele.addAtt(new Att("jfRouteUrl", "")); // �����׼URL
		}
		else
		{
			ele.addAtt(new Att("jfRouteUrl", "http://jfpdm.faw.com/PhosphorPDM/route_look_routeList.screen?bsoID="+routeID)); // �����׼URL
		}
		return ele;
	}
	
	/**
	 * ��ȡ�㲿����IBA���ԣ�����Դ�汾������֪ͨ��š���׼֪ͨ���
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
					if (defView.getName().equals("��׼֪ͨ���") && value != null)
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
	 * �����㲿����ȡ���ͣ���Ӧ�����ġ�װ��ģʽ��
	 * �ȴ�IBA���Եġ��㲿�����͡���ȡ�����ȡ������ȡ�����͡��е�ֵ��ֱ��ȡ���ģ�����Ӣ�����롣
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
	 * ������׼��Ż�ȡ��׼bsoID
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
	 * �����㲿��bsoID�õ�ͼֽ��Ϣ��
	 * ��¼�Ƿ���ͼֽ������ FAWTZHBB  ���û��ͼֽ��¼��WUA��,��ͼֱֽ��дͼֽ���ơ�
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
	 * �㲿����PDF������׼����ͨ�ĵ�
	 * @param parts Collection �㲿������
	 * @return Group�����ص������
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
		
		Group group = new Group(); // Ҫ���ص���
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
				ele.addAtt(new Att("num", qudouhao("JF-"+part.getPartNumber()))); // �ĵ����
				ele.addAtt(new Att("doctype", "ͼֽ�ĵ�"));
				ele.addAtt(new Att("name", qudouhao(part.getPartName()))); // �ĵ�����
				ele.addAtt(new Att("department", "")); // �ĵ��Ĵ�����λ
				ele.addAtt(new Att("location", part.getLocation())); // �ĵ���λ�ã��ļ��У�
				ele.addAtt(new Att("desc", getVersion(part, app.getFileName()))); // �ĵ�������
				ele.addAtt(new Att("filename", qudouhao(checkCHNString(filename)))); // �ĵ����ļ�����
				ele.addAtt(new Att("lifecyclestate", part.getLifeCycleState().toString())); // �ĵ�����������״̬��ȡ�㲿��״̬
				ele.addAtt(new Att("url", "")); // url,ֻ����׼�����á������������ֵ��
				ele.addAtt(new Att("partVersion", part.getVersionValue())); // �㲿����Ű汾
				group.addElement(ele);
			}
			else if(vec.size()>1)
			{
				Element ele = new Element();
				ele.addAtt(new Att("num", qudouhao("JF-"+part.getPartNumber()))); // �ĵ����
				ele.addAtt(new Att("doctype", "ͼֽ�ĵ�"));
				ele.addAtt(new Att("name", qudouhao(part.getPartName()))); // �ĵ�����
				ele.addAtt(new Att("department", "")); // �ĵ��Ĵ�����λ
				ele.addAtt(new Att("location", part.getLocation())); // �ĵ���λ�ã��ļ��У�
				ele.addAtt(new Att("desc", getVersion(part, ""))); // �ĵ�������
				ele.addAtt(new Att("filename", "���ͼֽ")); // �ĵ����ļ�����
				ele.addAtt(new Att("lifecyclestate", part.getLifeCycleState().toString())); // �ĵ�����������״̬��ȡ�㲿��״̬
				ele.addAtt(new Att("url", "")); // url,ֻ����׼�����á������������ֵ��
				ele.addAtt(new Att("partVersion", part.getVersionValue())); // �㲿����Ű汾
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
   * �õ��㲿����pdf����
   * @param id String �㲿��bsoid
   * @return Vector ApplicationDataInfo ������
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
   * �����㲿��PDF�������ļ���ȡ��ͼ����汾����������д�汾�����ڣ�
   * ��ȡ�㲿���ġ�����Դ�汾�����ԣ����������Դ�汾�����ڡ���ȡ���ϵͳ�и�����İ汾
   */
  public static String getVersion(QMPartIfc part, String fileName)
  {
  	String version = "";
  	//ȡpdf������ͼ����汾
		try
		{
			if(fileName.indexOf("(")>0)
			{
				version = fileName.substring(fileName.indexOf("(")+1,fileName.indexOf(")"));
			}
			else
			{
				//����з���Դ�汾��ȡ����Դ�汾������ȡ�㲿���汾
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
	 * ����pdf�ļ���ָ��Ŀ¼��
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
				//��ȡ��ID
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
	 * ����ַ������Ƿ���Ӣ�Ķ��ţ���ֹ�����ļ�������У�ȥ��Ӣ�Ķ��š�
	 */
	private static String qudouhao(String s)
	{
		s = s.replaceAll(",", "");
		return s;
	}
	
	
	/**
	 * ����ַ������Ƿ������ģ�����ȥ����
	 * Java�ж�һ���ַ����Ƿ�������������Unicode�������жϣ�
	 * ��Ϊ���ĵı�������Ϊ��0x4e00--0x9fbb
	 */
	private static String checkCHNString(String s)
	{
		char[] cs = s.toCharArray();
		int length= cs.length; 
		
		//����������ַ����������������ַ����顣
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
	 * �õ�ָ����������������ָ����������
   * @param priInfo ����id ��������
   * @return Vector ApplicationDataInfo ������
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
	 * ����׼����׼������ͨ�ĵ�
	 * @param parts Collection �㲿������
	 * @return Group�����ص������
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
			System.out.println("��׼�ļ�����"+f.getPath());
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
		ele.addAtt(new Att("num", qudouhao(route.getRouteListNumber()))); // �ĵ����
		ele.addAtt(new Att("doctype", "��׼�ĵ�"));
		ele.addAtt(new Att("name", qudouhao(route.getRouteListName()))); // �ĵ�����
		ele.addAtt(new Att("department", "")); // �ĵ��Ĵ�����λ
		ele.addAtt(new Att("location", "")); // �ĵ���λ�ã��ļ��У�
		ele.addAtt(new Att("desc", qudouhao(route.getRouteListDescription().replaceAll("\n","")))); // �ĵ�������
		ele.addAtt(new Att("filename", qudouhao(getRouteListFileName(route.getRouteListNumber())+".xls"))); // �ĵ����ļ�����
		ele.addAtt(new Att("lifecyclestate", "RELEASED")); // �ĵ�����������״̬��ȡ�㲿��״̬
		ele.addAtt(new Att("url", "http://jfpdm.faw.com/PhosphorPDM/route_look_routeList.screen?bsoID="+route.getBsoID())); // �����׼url
		ele.addAtt(new Att("partVersion", "")); // �㲿����Ű汾���˴�Ϊ�մ����ĵ���������ֵ
		gp.addElement(ele);
		
		if (VERBOSE)
		{
			System.out.println("***   leaving QQPartPublsih.prepareDocGroupForTechnicsRoute   ***");
		}
		return gp;
	}
	
	
	/**
	 * �����ݷ���ȷ��֪ͨ�飬׼������ͨ�ĵ�
	 * @param parts Collection �㲿������
	 * @return Group�����ص������
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
		ele.addAtt(new Att("num", qudouhao("JFNOTICE"+doc.getDocNum()))); // �ĵ����
		ele.addAtt(new Att("name", qudouhao("JFNOTICE"+doc.getDocName()))); // �ĵ�����
		ele.addAtt(new Att("department", "")); // �ĵ��Ĵ�����λ
		ele.addAtt(new Att("location", "")); // �ĵ���λ�ã��ļ��У�
		ele.addAtt(new Att("lifecyclestate", "RELEASED")); // �ĵ�����������״̬��ȡ�㲿��״̬
		ele.addAtt(new Att("url", "http://jfpdm.faw.com/PhosphorPDM/Doc-Bas-View-001.screen?bsoID="+doc.getBsoID())); // ����ĵ�url
		ele.addAtt(new Att("partVersion", "")); // �㲿���汾���˴�ֵΪ""
		
		String desc = "";
		String res = PublishHelper.isCaiYong(doc);
		String filename = "";
		String tongzhitype = "";
		//����
		if(res.equals("true"))
		{
			tongzhitype = "����";
			desc = PublishHelper.getCaiYongDesc(doc);
			//��ȡ�����㲿��
			Collection coll = PublishHelper.getPartCaiYong(doc);
			if(coll!=null)
			{
				Iterator iter = coll.iterator();
				while (iter.hasNext())
				{
					String[] str = (String[]) iter.next();
					desc = desc+" ��"+str[1]+"��";
				}
			}
		}
		else
		{
			tongzhitype = "����";
			desc = PublishHelper.getChangeOrderOriDesc(doc);
			//��ȡ���и��ĵ���Ȼ��õ����и�����
			Collection coll = PublishHelper.getConfirmDoc(doc.getBsoID());
			if (coll != null && coll.size() != 0)
			{
				try
				{
					ContentService contentService = (ContentService)EJBServiceHelper.getService("ContentService");
					Iterator iter = coll.iterator();
					while (iter.hasNext())
					{
						//���ĵ�
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
		//ele.addAtt(new Att("desc", qudouhao(desc))); // �ĵ�������
		ele.addAtt(new Att("desc", "")); // �ĵ�������
		ele.addAtt(new Att("doctype", tongzhitype+"֪ͨ�ĵ�"));
		ele.addAtt(new Att("filename", qudouhao(filename))); // �ĵ����ļ�����
		gp.addElement(ele);
		
		if (VERBOSE)
		{
			System.out.println("***   leaving QQPartPublsih.prepareDocGroupForConfirm   ***");
		}
		return gp;
	}
	
	
	/**
	 * ������֯�����ݼ��ϣ����ɵ��� �㲿�� �ļ�����Ҫ���ַ�����
	 */
	public static StringBuffer getPartStringBuffer(Group gp)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append("#��ʶ,�������,������,�������,���ϼ�,��������״̬"+"\n");
			Enumeration enumeration = gp.getElements();
			Element ele = null;
			//8�������ԣ�5IBA����
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
	 * ������֯�����ݼ��ϣ����ɵ��� �㲿������ �ļ�����Ҫ���ַ�����
	 */
	public static StringBuffer getPartLinkStringBuffer(Group gp)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append("#��ʶ,�������,�Ӽ����,ʹ������"+"\n");
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
	 * ������֯�����ݼ��ϣ����ɵ��� �ĵ� �ļ�����Ҫ���ַ�����
	 */
	public static StringBuffer getDocStringBuffer(Group gp)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append("#��ʶ,�ĵ�����,�ĵ����,�ĵ����ö�����ͣ���׼�ĵ���֪ͨ�ĵ���ͼֽ�ĵ���,���ϼУ���������ͼֽ����д�㲿�����ϼУ������֪ͨ�ĵ��������Ϊ�գ�,������˵����,��������,��������״̬"+"\n");
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
	 * ������֯�����ݼ��ϣ����ɵ��� �㲿���ĵ����� �ļ�����Ҫ���ַ�����
	 */
	public static StringBuffer getPartDocLinkStringBuffer(Group gp)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append("#��ʶ,������,�ĵ����"+"\n");
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
			/*FileWriter filewriter = new FileWriter(folderName + filename + ".csv", false);//�Ƿ�ȡ��ԭ�����ݣ�true��д��false��д
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
   * ͨ��ftp��ʽ�ϴ��ļ��ļ���
   * @param file �ϴ����ļ���
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
      		System.out.println("FTPĿ��������޷����ӣ�");
      		ftp.disconnect();
      	}
      	boolean f1 = ftp.changeWorkingDirectory(ftpPath);
      	System.out.println("f1=="+f1);
      	//String newdir = f.getName().substring(2,f.getName().length());
      	//System.out.println("newdir=="+newdir);
      	ftp.makeDirectory(f.getName());
      	boolean f2 = ftp.changeWorkingDirectory(ftpPath+f.getName()+File.separator);
      	System.out.println("f2=="+f2);
      	//ͳ��
      	long t1 = System.currentTimeMillis();
      	filesize = 0;
      	upload(f, ftp);
      	System.out.println("�����ļ��ܴ�С:"+(filesize/1024/1024)+"M");
      	long t2 = System.currentTimeMillis();
        System.out.println("��ʱ�� "+(t2-t1)/1000+" ��");
      	ftp.disconnect();
  		}
  	}
  	catch(Exception e)
  	{
  		System.out.println("�����ļ���"+f.getName()+" ʱ����");
  		//e.printStackTrace();
  		throw new Exception(e);
  	}
  }
  
  private static void upload(File file, FTPClient ftp) throws Exception
  {
  	if(file.isDirectory())
  	{
  		String[] files = file.list();
  		System.out.println("�ļ�������"+files.length);
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
  					System.out.println("  ��ϣ�");
  					input.close();
  			  }
  			  catch(Exception e)
  			  {
  			  	System.out.println("�����ˣ�"+j);
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
   * ͨ��ftp��ʽ�ϴ��ļ��ļ���
   * @param file �ϴ����ļ���
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
      		System.out.println("FTPĿ��������޷����ӣ�");
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
        System.out.println("������ɱ���ļ���ʱ�� "+(t2-t1)/1000+" ��");
      	ftp.disconnect();
  		}
  	}
  	catch(Exception e)
  	{
  		System.out.println("�����ļ���"+f.getName()+" ʱ����");
  		//e.printStackTrace();
  		throw new Exception(e);
  	}
	}
  
  private static String getRouteListFileName(String fname)
  {
  	try
  	{
			if(fname.indexOf("ǰ׼")!=-1)
			{
				fname = fname.replaceAll("ǰ׼","QZ");
			}
			if(fname.indexOf("��׼")!=-1)
			{
				fname = fname.replaceAll("��׼","LZ");
			}
			if(fname.indexOf("����׼")!=-1)
			{
				fname = fname.replaceAll("����׼","YSZ");
			}
			if(fname.indexOf("��׼")!=-1)
			{
				fname = fname.replaceAll("��׼","YZ");
			}
			if(fname.indexOf("�ձ�")!=-1)
			{
				fname = fname.replaceAll("�ձ�","YB");
			}
			if(fname.indexOf("�շ�")!=-1)
			{
				fname = fname.replaceAll("�շ�","YF");
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
		
		// �����ĵ��������ɵ��ļ���Ϊ�ĵ�����Ҫ�ļ�
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
			String docCfBsoID = PublishHelper.getDocCf("������\\���ݷ���������־");
			DocFormData formData = new DocFormData();
			if (docInfo == null)
			{
				formData.setDocumentAttribute("docName", "�������ݷ�����־��" + fname);
				formData.setDocumentAttribute("docNum", getConfirmationNumber(fname));// �����ĵ����
				formData.setDocumentAttribute("lifecycleTemplate", "�������ݷ���������������");
				formData.setDocumentAttribute("createDept", "ϵͳ");// ���ô�����λ
				formData.setDocumentAttribute("docCfBsoID", docCfBsoID);
				formData.setDocumentAttribute("folder", "\\Root\\���ݽ�������");// �������ϼ�
				formData.setDocumentAttribute("lifeCState", LifeCycleState.toLifeCycleState("RECEIVING"));// ������������״̬ ����
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
				//ͣ���̺��޶���
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
	
	//����ʧ�ܺ�Ϊ��־�ĵ����óɡ�����ʧ��״̬��
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
			//��Ҫ�ļ���������־
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
			
			//�����ļ� csv�ļ�
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
	 * �γ��ļ�
	 * @param buffer StringBuffer �ַ�����
	 * @param path String �����ļ���·��
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
	 * ������ɾ���ļ�
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
  			//System.out.println("ɾ�����ļ�==="+f.getPath());
  			f.delete();
  		}
  		else
  		{
  			System.out.println("Ҫɾ�����ļ������ڣ�"+f.getPath());
  		}
  	}
  	catch(Exception ex)
  	{
  		ex.printStackTrace();
  	}
  }
}
