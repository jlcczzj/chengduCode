/**
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  * SS1 ��������¼ liuyuzhu 2017-04-27
  * SS2 �޸ĳ�ʼ������BOM,��ֳ����������ܣ���ʻ�������� ���� 2017-5-10
  * SS3 ���ӱ������ع��ܡ�lishu 2017-05-03
  * SS4 �Զ�����BOM������ lishu 2017-5-12
  * SS5 ���浥���滻���Ĺ���BOM liuyuzhu 2017-05-19
  * SS6 ��ȡ��ǰ�û����ڵ�λ�� ������ 2017-5-24
  * SS7 �����㲿������BOM������ lishu 2017-5-25
  * SS8 ��ѯ��������ǩ liuyuzhu 2017-07-20
  * SS9 �޸ĵ����ܳɷ��� liuyuzhu 2017-07-25
  * SS10 �޸Ľṹ�ȽϷ��� liuyuzhu 2017-08-22
  * SS11 �޸Ĺ���BOM������������������ maxiaotong 2017-09-04 
  * SS12 �޸ı�����޷������㲿������ liuyuzhu 2017-09-04
  * SS13 �޸��滻�����û����ȡ���ü����� liuyuzhu 2017-09-18
  * SS14 �޸İ汾��ʾ����Ϊ�������İ汾�� liuyuzhu 2017-09-26
  * SS15 ������ò�ѯ���� liuyuzhu 2017-09-26
  * SS16 �Զ����ɱ�������� liuyuzhu 2017-10-19
  * SS17 �滻�����������仯 liuyuzhu 2017-11-02
  * SS18 �����㲿�� liuyuzhu 2017-11-13
  * SS19 ��ȡ��ǰ��λ���Ʋ��ԣ������޸� ������ 2017-12-17
  * SS20 �޸Ĳ���������ȡ���� liuyuzhu 2017-12-20
  * SS21 У�鹤��bom�Ƿ�༭·�߲�����  ������ 2017-12-22
  * SS22 ����BOM����ʱ����Ҫ�ж��¼���bom��ϵͳ�Ƿ���� ������ 2017-12-26
  */
package com.faw_qm.gybom.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.faw_qm.bomNotice.model.ChangeOrdersContentIfc;
import com.faw_qm.bomNotice.model.ChangeOrdersContentInfo;
import com.faw_qm.bomNotice.model.ChangeOrdersInfo;
import com.faw_qm.consadoptnotice.model.UniteIdentifyInfo;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.gybom.ejb.service.GYBomService;
import com.faw_qm.gybomNotice.ejb.service.GYBomNoticeService;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticePartLinkInfo;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.jfpublish.receive.PublishHelper;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.project.model.RoleHolderIfc;
import com.faw_qm.project.util.RolePrincipalTable;
import com.faw_qm.ration.util.RationHelper;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.workflow.definer.ejb.service.WfDefinerService;
import com.faw_qm.workflow.definer.model.WfProcessDefinitionIfc;
import com.faw_qm.workflow.definer.model.WfProcessTemplateIfc;
import com.faw_qm.workflow.definer.model.WfProcessTemplateInfo;
import com.faw_qm.workflow.definer.util.ProcessDataInfo;
import com.faw_qm.workflow.engine.ejb.entity.ProcessData;
import com.faw_qm.workflow.engine.ejb.entity.WfProcess;
import com.faw_qm.workflow.engine.ejb.service.WfEngineService;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
//CCBegin SS6
import java.util.Enumeration;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
//CCEnd SS6
//CCBegin SS7
import com.faw_qm.part.util.NormalPart;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import java.util.Date;
//CCEnd SS7
import com.faw_qm.clients.workflow.initiate.*;

/**
 * ����BOM����ƽ̨��������
 * @author ���   �޸�ʱ��  2016-5-16
 * @version 1.0
 */
public class GYBomHelper
{
  /**
   * �������BOM����
   * @param Collection mtreenode Ŀ����������BOM���ڵ㼯��
   * ����ʱҪ��ȡ��ǰ�û����ڵ�λ��Ȼ��ֱ��ͨ�����ݿⴴ����
   */
  public static String saveBom(String ytreenode)
  {
//  	System.out.println("come in helper saveBom ytreenode=="+ytreenode);
  	String s = "";
    try
    {
      GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
      s = gybs.saveBom(ytreenode);
    } 
    catch(Exception e)
    {
      e.printStackTrace();
    }
//		System.out.println("over in helper saveBom ");
    return s;
  }
  
  
  /**
   * ��ק����BOM���ϵ��ܳɼ��㲿��������BOM���ϵȲ�����
   * ������ק������bom����ק��Ŀ��bom��֮����Ҫ��ͼ�괦��
   * ������ק������BOM���µ��㲿�����ṹ����������BOM���£�����bom����Ҫ��ק������bom���ڵ�֮�£���ק֮����Ҫ���оֲ�ˢ�²����棩
   * ������ק������BOM������ṹ�ĵ�����������ק��������ṹ�ĵ���������Ҫ�ı�ͼ��
   * ������ק��Ŀ����������ק������ק֮������ύ���档
   * ������ק��addArr��ֵ��������ק��updateArr��ֵ���Ƴ���deleteArr��ֵ�������ƶ���addArr��updateArr��ֵ
   * @param String[] addArr �����ṹ����  ����id,�Ӽ�id;�Ӽ�id1;�Ӽ�id2,����;����1;����2,��λ;��λ1;��λ2
   * @param String[] updateArr ���½ṹ����  linkid;����id;����,linkid1;����id1;����1,linkid2;����id2;����2
   * @param String[] deleteArr ɾ���ṹ����  linkid,linkid1,linkid2
   * @param String carModelCode ������
   * @param String dwbs ����
   * ����������json
   */
  public static String saveTreeNode(String addstr, String updatestr, String deletestr, String carModelCode, String dwbs, String left)
  {
//  	System.out.println("come in helper saveTreeNode: addstr=="+addstr+" updatestr=="+updatestr+" deletestr=="+deletestr+" carModelCode=="+carModelCode+" dwbs=="+dwbs+" left=="+left);
    String s = "";
    try
    {
      GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
      s = gybs.saveTreeNode(addstr, updatestr, deletestr, carModelCode , dwbs, left);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
//		System.out.println("over in helper saveTreeNode ");
		return s;
  }
  
  
  /**
   * ����ָ���㲿������һ���Ӽ��ڵ㼯��
   * ��������㲿�����bom��Դ��������flag��������ʱ���л�ȡ���ǻ�ȡ���½ṹ��
   * @param id �㲿��bsoID
   * @param flag �Ƿ��ȡ��Ʊ�ǩ��
   * @param carModelCode ������
   * @param dwbs ��λ
   * @return �㲿������һ���Ӽ������ṹ
   */
  public static String getDesignBom(String id, String flag, String carModelCode, String dwbs)throws QMException
  {
  	long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper  getDesignBom   id=="+id+"  flag=="+flag+"  carModelCode=="+carModelCode+"  dwbs=="+dwbs);
  	String s = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		s = gybs.getDesignBom(id, flag, carModelCode, dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper getDesignBom ��ʱ�� "+(t2-t1)+" ����");
		return s;
  }
  
  
  /**
   * ����ָ���㲿����һ���Ӽ��ڵ㼯�ϣ�������ʾ���BOM�㲿���б�
   * @param id �㲿��bsoID
   * @param flag �Ƿ��ȡ��Ʊ�ǩ��
   * @param carModelCode ������
   * @param dwbs ��λ
   * @return �㲿������һ���Ӽ������ṹ
   */
  public static String getDesignBomList(String id, String flag, String carModelCode, String dwbs )throws QMException
  {
//  	System.out.println("come in helper getDesignBomList   id=="+id+"  flag=="+flag+"  carModelCode=="+carModelCode+"  dwbs=="+dwbs);
  	String s = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		s = gybs.getDesignBomList(id, flag, carModelCode, dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
//		System.out.println("over in helper getDesignBomList ");
		return s;
  }

  
  /**
   * ���ɳ�ʼ����BOM
   **/
  public static String initGYBom(String partID, String gyID, String dwbs)
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper initGYBom   partID=="+partID+"  gyID=="+gyID+"  dwbs=="+dwbs);
  	String str = "";
		try
		{
  		PersistService ps = (PersistService)EJBServiceHelper.getPersistService();
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		QMPartIfc part = (QMPartIfc)ps.refreshInfo(gyID);
  		
    	gybs.initGYBom(partID, gyID, dwbs);
    	str = getGYBom(gyID, part.getPartNumber(), dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper initGYBom  ��ʱ�� "+(t2-t1)+" ����");
		return str;
	}
  
  /**
   * ����ָ���㲿������һ���Ӽ��ڵ㼯��
   * ������ӹ���bom��Ŀ������
   * @param id �㲿��bsoID
   * @param carModelCode ������
   * @param dwbs ��λ
   * @return �㲿������һ���Ӽ������ṹ
   */
  public static String getGYBom(String id, String carModelCode, String dwbs)throws QMException
  {
//  	System.out.println("come in  helper  getGYBom   id=="+id+"  carModelCode=="+carModelCode+"  dwbs=="+dwbs);
  	String s = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		s = gybs.getGYBom(id, carModelCode, dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
//		System.out.println("over in helper getGYBom ");
		return s;
  }
  
  
  /**
   * ����ָ���㲿����һ���Ӽ��ڵ㼯�ϣ�������ʾ����BOM�㲿���б�
   * @param id �㲿��bsoID
   * @param carModelCode ������
   * @param dwbs ��λ
   * @return �㲿������һ���Ӽ������ṹ
   */
  public static String getGYBomList(String id, String carModelCode, String dwbs)throws QMException
  {
//  	System.out.println("come in helper getGYBomList id=="+id+"  carModelCode=="+carModelCode+"  dwbs=="+dwbs);
  	String s = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		s = gybs.getGYBomList(id, carModelCode, dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
//		System.out.println("over in helper getGYBomList ");
		return s;
  }
  
  
  /**
   * �������������bom��
   * ���ݱ�ź����������㲿��
   * @param name �㲿������
   * @param number1 �㲿�����
   * @param ignoreCase ���Դ�Сд
   * @return �����������㲿������
   * @exception com.faw_qm.framework.exceptions.QMException
   */
  public static String findPart(String number1, String name, String ignoreCase) throws QMException
  {
//  	System.out.println("come in helper findPart name=="+name+"  number1=="+number1+"  ignoreCase=="+ignoreCase);
  	String s = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		s = (String)gybs.findPart(number1, name, ignoreCase);
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
//  	System.out.println("over in helper findPart");
		return s;
  }
  
  
  
  /**
   * �����㲿��
   * pid+partNumbe+partName+selectst+selectdw+selectlx+selectly+selectsmzq+selectzlj+code+dwbs
   * ������+���+����+��ͼ+��λ+����+��Դ+��������+���ϼ�+������+����
   * @param String s
   * @return ��ǰ����Ϊ�Ӽ��Ľڵ�
   * @exception com.faw_qm.framework.exceptions.QMException
   */
  public static String createPart(String s) throws QMException
  {
//  	System.out.println("come in helper createPart s=="+s);
  	String str = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		str = gybs.createPart(s);
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
//  	System.out.println("over in helper createPart");
		return str;
  }
  
  
  /**
   * ����
   */
  public static String addLock(String carModelCode, String dwbs) throws QMException
  {
//  	System.out.println("come in helper addLock carModelCode=="+carModelCode+" dwbs=="+dwbs);
  	String s = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		s = (String)gybs.addLock(carModelCode, dwbs);
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
//  	System.out.println("over in helper addLock");
  	return s;
  }
  
  
  /**
   * ����
   */
  public static String unLock() throws QMException
  {
//  	System.out.println("come in helper unLock");
  	String s = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		s = (String)gybs.unLock();
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
//  	System.out.println("over in helper unLock");
  	return s;
  }
  
  
  /**
   * ����Ƿ���
   */
  public static String checkLock(String carModelCode, String dwbs) throws QMException
  {
//  	System.out.println("come in helper checkLock carModelCode=="+carModelCode+" dwbs=="+dwbs);
  	String s = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		s = (String)gybs.checkLock(carModelCode, dwbs);
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
//  	System.out.println("over in helper checkLock");
  	return s;
  }
  
  
  /**
   * �ṹ�Ƚ�
   */
  public static String CompartTreeResult(String desginID, String gyID, String carModelCode, String dwbs) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper CompartTreeResult desginID=="+desginID+" gyID=="+gyID+" carModelCode=="+carModelCode+" dwbs=="+dwbs);
  	String s = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		s = (String)gybs.CompartTreeResult(desginID, gyID,carModelCode, dwbs);
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
		long t2 = System.currentTimeMillis();
//  	System.out.println("over in helper CompartTreeResult��ʱ�� "+(t2-t1)+" ����");
  	return s;
  }
  
  
  /**
   * ���Ϊ����BOM��
   * String yid ѡ���㲿����
   * String mid Ŀ���㲿����
   * String dwbs ѡ���㲿������
   */
  public static String saveAsGYBom(String yid, String mid, String ydwbs) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper saveAsGYBom   yid=="+yid+"  mid=="+mid+"  ydwbs=="+ydwbs);
  	String str = "";
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	str = gybs.saveAsGYBom(yid, mid, ydwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper saveAsGYBom  ��ʱ�� "+(t2-t1)+" ����");
		return str;
  }
  
  
  /**
   * ���ñ����Ϊһ����Ч�Ĺ���BOM��¡һ��δ��ЧBOM��
   */
  public static String changeGYBom(String id, String carModelCode, String dwbs) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper changeGYBom   carModelCode=="+carModelCode+"  dwbs=="+dwbs+"  id =="+id);
  	String str = "";
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	str = gybs.changeGYBom(id, carModelCode, dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper changeGYBom  ��ʱ�� "+(t2-t1)+" ����");
		return str;
  }
  
  
  /**
   * �������������滻���ͱ�
   * id ���ͳ���bsoid,������,����,�滻����1,�滻����2,�滻����3...
   */
  public static String saveBatchUpdateCM(String id) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper saveBatchUpdateCM   id=="+id);
  	String str = "";
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	str = gybs.saveBatchUpdateCM(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper saveBatchUpdateCM  ��ʱ�� "+(t2-t1)+" ����");
		return str;
  }
  
  
  /**
   * ��������ȫ���滻���ͱ�
   * id ����id
   */
  public static String saveAllBatchUpdateCM(String id) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper saveAllBatchUpdateCM   id=="+id);
  	String str = "";
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	str = gybs.saveAllBatchUpdateCM(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper saveAllBatchUpdateCM  ��ʱ�� "+(t2-t1)+" ����");
		return str;
  }
  
  
  /**
   * ��ȡ�����滻���ͱ�
   */
  public static String getBatchUpdateCM(String id, String carModelCode, String dwbs) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper getBatchUpdateCM  id=="+id+"  carModelCode=="+carModelCode+"  dwbs =="+dwbs);
  	String str = "";
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	str = gybs.getBatchUpdateCM(id, carModelCode, dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper getBatchUpdateCM  ��ʱ�� "+(t2-t1)+" ����");
		return str;
  }
  
  
  /**
   * ���汾
   * ������bom�Ͻڵ�汾����һ���汾������ԭ���汾�ǡ�D����ѡ�񽵰汾֮���Ϊ��C���汾
   * @parm String linkid ����id��linkid��
   * @parm String id ѡ�нڵ�bsoid��id��
   * @parm String carModelCode ������
   * @parm String dwbs ���� 
   */
  public static String upperVersion(String linkid, String id, String carModelCode, String dwbs) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper upperVersion  linkid=="+linkid+"  id=="+id+"  carModelCode=="+carModelCode+"  dwbs =="+dwbs);
  	String str = "";
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	str = gybs.upperVersion(linkid, id, carModelCode, dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper upperVersion  ��ʱ�� "+(t2-t1)+" ����");
		return str;
  }
  
  
  /**
   * ���
   * �����Ҫ��ֵ��߼��ܳɣ���ͬ�����Զ�����һ���߼��ܳɺ�
   * ��Ź���ԭ�߼��ܳɺ�+F+3λ��ˮ��
   * @parm String linkid ����id��linkid��
   */
  public static String chaiFenPart(String linkid) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper chaiFenPart  linkid=="+linkid);
  	String str = "";
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	str = gybs.chaiFenPart(linkid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper chaiFenPart  ��ʱ�� "+(t2-t1)+" ����");
		return str;
  }
  
  
  /**
   * ����һ�����嵥
   */
  public static Vector getExportFirstLeveList(String id, String carModelCode, String dwbs) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper getExportFirstLeveList  id=="+id+"  carModelCode=="+carModelCode+"  dwbs =="+dwbs);
  	Vector vec = new Vector();
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	vec = gybs.getExportFirstLeveList(id,carModelCode,dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper getExportFirstLeveList  ��ʱ�� "+(t2-t1)+" ����");
		return vec;
  }
  
  
  /**
   * ��ӱ�Ʒ
   * ��ѡ���㲿���Ӽ���ӵ�ָ���㲿���¡�
   * ����
   * @parm String parentID ָ���㲿��
   * @parm String beipinID ѡ���㲿��
   * @parm String carModelCode ������
   * @parm String dwbs ���� 
   */
  public static String addBeiPin(String parentID, String beipinID, String carModelCode, String dwbs) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper addBeiPin  parentID=="+parentID+"  beipinID=="+beipinID+"  carModelCode=="+carModelCode+"  dwbs =="+dwbs);
  	String str = "";
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	str = gybs.addBeiPin(parentID, beipinID, carModelCode, dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper addBeiPin  ��ʱ�� "+(t2-t1)+" ����");
		return str;
  }
  
  
  
  /**��õ�ǰ�û�
   * @return
   * @throws QMException
   */
  public static String getUserName() throws QMException
  {
	  GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
	  String user = gybs.getCurrentUserInfo().getUsersName();
	  return "("+user+")";
  }
  
  
  /**
   * ����bom��ʷ���ݵ��뷽��
   * AddUsageLink	C01AM44141BF204	CQ1511065	ea	8
   * AddUsageLink	C01AM44141BF204	Q1841240F6	ea	8
   * ��� ���� �Ӽ� ��λ ����
   */
  public static String uploadBomExcel(String isupdate) throws QMException
  {
  	String s = "";
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		s = gybs.uploadBomExcel(isupdate);
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
	  return "("+s+")";
  }
  
  
  /**
   * ��ȡ����BOM�б��嵥
   */
  public static Vector getBomExcel() throws QMException
  {
  	Vector result = new Vector();
  	try
  	{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		result = gybs.getBomExcel();
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
	  return result;
  }
  
  
  /**
   * ɾ���ܳ�
   * �Ҽ���Ӳ˵���ɾ���ܳɡ����˲˵������ǽ��ܳ�ɾ�����������Ӽ��㼶����һ����
   * ����
   * @parm String parentID ѡ���㲿���ĸ���
   * @parm String partID ѡ���㲿��
   * @parm String carModelCode ������
   * @parm String dwbs ���� 
   */
  public static String deleteSeparable(String parentID, String partID, String carModelCode, String dwbs) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper deleteSeparable  parentID=="+parentID+"  partID=="+partID+"  carModelCode=="+carModelCode+"  dwbs =="+dwbs);
  	String str = "";
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	str = gybs.deleteSeparable(parentID, partID, carModelCode, dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper deleteSeparable  ��ʱ�� "+(t2-t1)+" ����");
		return str;
  }
  
  
  /**
   * ����BOM ָ�����͡�������BOM���ϡ�
   */
  public static Vector getExportBomList(String carModelCode) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper getExportBomList carModelCode=="+carModelCode);
  	Vector vec = new Vector();
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	vec = gybs.getExportBomList(carModelCode);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper getExportBomList  ��ʱ�� "+(t2-t1)+" ����");
		return vec;
  }
  
  
  /**
   * ��ȡ�㲿�������ڸù����ڵ����и���
   */
  public static Vector getParentFromDwbs(String id) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper getParentFromDwbs  id=="+id);
  	Vector vec = new Vector();
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	vec = gybs.getParentFromDwbs(id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper getParentFromDwbs  ��ʱ�� "+(t2-t1)+" ����");
		return vec;
  }
  
  
  /**
    * ������Ϣ
    */
  private static final boolean VERBOSE = (RemoteProperty.getProperty("com.faw_qm.gybom.verbose","false")).equals("true");
  
  //CCBegin SS1
	/**
	 * ��������¼
	 * 
	 * @param cxh ���ͺ�
	 * @param gc ����
	 * @param parentid ����id
	 * @param subid �Ӽ�id
	 * @param quantityb ���ǰ����
	 * @param quantitya ���������
	 * @param sign ���ı�ʶ
	 * @return
	 */
	public static String saveChangeContent(String cxh, String gc,
			String parentid, String subid,String tsubid, String quantityb, String quantitya,
			String sign) {
//		System.out.println("come in  helper  saveChangeContent   cxh==" + cxh
//				+ "  gc==" + gc + "  parentid==" + parentid + "  subid=="
//				+ subid + "  quantityb==" + quantityb + "  quantitya=="
//				+ quantitya + "  sign==" + sign);
		String s = "";
		try {
			GYBomService gybs = (GYBomService) EJBServiceHelper
					.getService("GYBomService");
			s = gybs.saveChangeContent(cxh, gc, parentid, subid,tsubid, quantityb,
					quantitya, sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
  //CCEnd SS1
  
  
  //CCBegin SS3
  /**
   * ���������
   * @param changeNumber ��������
   * @return 
   * @exception com.faw_qm.framework.exceptions.QMException
   */
  public static String searchChangeOrders(String changeNumber) throws QMException
    {
//          System.out.println("--------------�����------------------");
        //�����
        PersistService pService = (PersistService)EJBServiceHelper.getPersistService();

        QMQuery query = new QMQuery("Doc", "DocMaster");
        int k = query.appendBso("DocClassification", false);
        //QueryCondition con1 = new QueryCondition("technicsName",QueryCondition.LIKE,"%"+stepName+"%");
        query.addCondition(0, new QueryCondition("location", QueryCondition.LIKE, "%�������ı����%"));
        query.addAND();
        query.addCondition(1, new QueryCondition("docNum", QueryCondition.LIKE, "%" + changeNumber + "%"));
        query.addAND();
        query.addCondition(k, new QueryCondition("docCfName", QueryCondition.EQUAL, "�������ı����"));
        query.addAND();
        query.addCondition(0, new QueryCondition("iterationIfLatest", true));
        query.addAND();
        query.addCondition(0, 1, new QueryCondition("masterBsoID", "bsoID"));
        query.addAND();
        query.addCondition(0, k, new QueryCondition("docCfBsoID", "bsoID"));

        query.addOrderBy(0, "createTime", true);

        query.setVisiableResult(1);
        String s = "[";
//        System.out.println("sql -============= " + query.getDebugSQL());
        Collection result = pService.findValueInfo(query);
//        System.out.println("���ϴ�С===== " + result.size());
        Iterator ite = result.iterator();
        boolean flag = true;
        while(ite.hasNext())
        {
            DocIfc doc = (DocIfc)ite.next();
//            System.out.println("���==== " + doc.getDocNum());
            if(flag)
            {
                s = s + "{'num':'" + doc.getDocNum() + "','id':'" + doc.getBsoID() + "'}";
                flag = false;
            }else
            {
                s = s + ",{'num':'" + doc.getDocNum() + "','id':'" + doc.getBsoID() + "'}";
            }
        }
        s=s+"]";
//        System.out.println("s==================" + s);
        return s;

//        com.faw_qm.jfpublish.receive.PublishHelper.getPartBeforechange(String docId)
//        getPartAfterchange
//        ��ȡ���ǰ��ļ�
    }
  
  /**
   * ��ȡ���������
   * @param changeID �����ID
   * @return 
   * @exception com.faw_qm.framework.exceptions.QMException
   */
    public static String getChangeContentByID(String changeID) throws QMException
    {
//          System.out.println("--------------��ȡ���������------------------");
        //�����
        PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
        
//        QMQuery query = new QMQuery("DocMaster");
//        query.addCondition(new QueryCondition("docNum",
//                                              QueryCondition.EQUAL, s));

        QMQuery query = new QMQuery("ChangeOrders");
        query.addCondition(new QueryCondition("changeBsoID", QueryCondition.EQUAL, changeID));
//        System.out.println("changeID ==== " + changeID);
//        System.out.println("query ==== " + query.getDebugSQL());
        Collection result = pService.findValueInfo(query);
        String s = "[";
        if(result.size()>0)
        {
            Iterator ite = result.iterator();
            ChangeOrdersInfo co = (ChangeOrdersInfo)ite.next();
            
            QMQuery queryContent = new QMQuery("ChangeOrdersContent");
            queryContent.addCondition(new QueryCondition("changeOrdersID", QueryCondition.EQUAL, co.getBsoID()));
            Collection resultContent = pService.findValueInfo(queryContent);
            Iterator itea = resultContent.iterator();
            while(itea.hasNext())
            {
                ChangeOrdersContentInfo ifc = (ChangeOrdersContentInfo)itea.next();
                
                String str = "{'num':'"+ifc.getPartNumber()+"','oldVersion':'"+ifc.getOldVersion()+
                            "','newVersion':'"+ifc.getNewVersion()+"','flag':true"+",'checked':true}";
                
                if(s.length()==1)
                {
                    s = s + str;
                }
                else
                {
                    s = s + "," + str;
                }
            }
            
        }
        else
        {
            HashMap map = new HashMap();
            
            //��ȡ���ǰ����
            Collection colb = PublishHelper.getPartBeforechange(changeID);
            ArrayList arrb = new ArrayList(colb);
            for(int i=0; i<arrb.size(); i++)
            {
                String[] ss = (String[])arrb.get(i);
//                System.out.println("����1===="+ss[0]);
//                System.out.println("����2===="+ss[1]);
//                System.out.println("����3===="+ss[2]);
//                System.out.println("����4===="+ss[3]);
//                System.out.println("����5===="+ss[4]);
                ChangeOrdersContentIfc old = new ChangeOrdersContentInfo();
                old.setOldVersion(ss[3]);
                old.setPartNumber(ss[1]);
                map.put(ss[1], old);
            }
            
            //��ȡ���������
            Collection cola = PublishHelper.getPartAfterchange(changeID);
            ArrayList arra = new ArrayList(cola);
            for(int i=0; i<arra.size(); i++)
            {
                String[] ss = (String[])arra.get(i);
//                System.out.println("��ȡ�������������1===="+ss[0]);
//                System.out.println("��ȡ�������������2===="+ss[1]);
//                System.out.println("��ȡ�������������3===="+ss[2]);
//                System.out.println("��ȡ�������������4===="+ss[3]);
//                System.out.println("��ȡ�������������5===="+ss[4]);
                ChangeOrdersContentIfc obj = (ChangeOrdersContentIfc)map.get(ss[1]);
                if(obj == null)
                {
                    obj = new ChangeOrdersContentInfo();
                }
                obj.setNewVersion(ss[3]);
                obj.setPartNumber(ss[1]);
                map.put(ss[1], obj);
            }
//            System.out.println("map === "+ map);
            ArrayList arr = new ArrayList(map.keySet());
            for(int i=0; i<arr.size(); i++)
            {
                String key = (String)arr.get(i);
                ChangeOrdersContentIfc obj = (ChangeOrdersContentIfc)map.get(key);
                //s = "[{'num':'CA1250P66K24L5T1E5_Q00002_01','oldVersion':'A.1','newVersion':'B.5','id':'Doc_142530655'}]";
                String str = "{'num':'"+obj.getPartNumber()+"','oldVersion':'"+obj.getOldVersion()+
                                "','newVersion':'"+obj.getNewVersion()+"','flag':true"+",'checked':true}";
//                System.out.println("str ==== " + str);
                if(s.length()==1)
                {
                    s = s + str;
                }
                else
                {
                    s = s + "," + str;
                }
            }
        }
        
        
//        System.out.println("1111sql -============= " + query.getDebugSQL());
        
//        System.out.println("���ϴ�С===== " + result.size());
//        Iterator ite = result.iterator();
//        boolean flag = true;
//        while(ite.hasNext())
//        {
//            DocIfc doc = (DocIfc)ite.next();
//            System.out.println("���==== " + doc.getDocNum());
//            if(flag)
//            {
//                s = s + "{'num':'" + doc.getDocNum() + "','id':'" + doc.getBsoID() + "'}";
//                flag = false;
//            }else
//            {
//                s = s + ",{'num':'" + doc.getDocNum() + "','id':'" + doc.getBsoID() + "'}";
//            }
//        }
//        String s = "[{'num':'CA1250P66K24L5T1E5_Q00002_01','oldVersion':'A.1','newVersion':'B.5','id':'Doc_142530655'}]";
        s=s+"]";
//        System.out.println("s==================" + s);
//        s = "[{'num':'CA1250P66K24L5T1E5_Q00002_01','oldVersion':'A.1','newVersion':'B.5','id':'Doc_142530655'}]";
        return s;

//        com.faw_qm.jfpublish.receive.PublishHelper.getPartBeforechange(String docId)
//        getPartAfterchange
//        ��ȡ���ǰ��ļ�
    }
  
    /**
     * ����������ѡ��
     * @param content 
     * @return 
     * @exception com.faw_qm.framework.exceptions.QMException
     */
      public static String saveChangeOrders(String content) throws QMException
      {
//          System.out.println("===========����������ѡ��=============="+content);
          String contents[] = content.split("---");
          String ids[] = contents[0].split(";;;");
          String docID = ids[0];
          ArrayList partIDArr = new ArrayList();
          for(int i=1; i<ids.length; i++)
          {
              partIDArr.add(ids[i]);
          }
          
          PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
          QMQuery query = new QMQuery("ChangeOrders");
          query.addCondition(new QueryCondition("changeBsoID", QueryCondition.EQUAL, docID));
          Collection result = pService.findValueInfo(query);
          ChangeOrdersInfo co;
          if(result.size()>0)
          {
              Iterator ite = result.iterator();
              co = (ChangeOrdersInfo)ite.next();
//              System.out.println("aaaaaaaaaaaaaaaa");
          }
          else
          {
              co = new ChangeOrdersInfo();
              co.setChangeBsoID(docID);
              DocIfc doc = (DocIfc)pService.refreshInfo(docID, false);
              co.setChangeNumber(doc.getDocNum());
              pService.saveValueInfo(co);
//              System.out.println("bbbbbbbbbbbbbbbb");
          }
          
          //��ɾ�����������
          QMQuery queryContent = new QMQuery("ChangeOrdersContent");
          queryContent.addCondition(new QueryCondition("changeOrdersID", QueryCondition.EQUAL, co.getBsoID()));
          Collection resultContent = pService.findValueInfo(queryContent);
          Iterator itea = resultContent.iterator();
          while(itea.hasNext())
          {
              BaseValueIfc ifc = (BaseValueIfc)itea.next();
              pService.deleteValueInfo(ifc);
          }
          
          for(int i=1; i<contents.length; i++)
          {
              String ss[] = contents[i].split(";;;");
              ChangeOrdersContentInfo value = new ChangeOrdersContentInfo();
              value.setChangeOrdersID(co.getBsoID());
              String flag = "0";
              if(partIDArr.contains(ss[0]))
              {
                  flag = "1";
              }
              value.setIsChanged(flag);
              value.setOldVersion(ss[1]);
              value.setNewVersion(ss[2]);
              value.setPartNumber(ss[0]);
              pService.saveValueInfo(value);
//              System.out.println("cccccccccccccc");
          }
          
          return "";
      }
      
      /**
       * ���°汾
       */
      public static String updateVersion(String linkid, String id, String carModelCode, String dwbs, String version) throws QMException
      {
//        System.out.println("come in updateVersion  ���°汾" );
        String str = "";
        try
        {
            PersistService ps = (PersistService)EJBServiceHelper.getPersistService();
            IBAValueService IBAservice=(IBAValueService)EJBServiceHelper.getService("IBAValueService");
            VersionControlService vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
            
            QMPartIfc part = (QMPartIfc)ps.refreshInfo(id,false);
            
            QMPartMasterIfc partmaster=(QMPartMasterIfc)ps.refreshInfo(part.getMasterBsoID());
            Collection co=vcService.allVersionsOf(partmaster);
            Iterator itea=co.iterator();
            QMPartIfc partIfc=null;//�°汾���
            
            QMQuery query = new QMQuery("QMPart");
            query.addCondition(new QueryCondition("masterBsoID", QueryCondition.EQUAL, part.getMasterBsoID()));
//            query.addAND();
//            query.addCondition(new QueryCondition("versionValue", QueryCondition.EQUAL, version.trim()));
//            System.out.println("�㲿��sql==========="+query.getDebugSQL());
            Collection result = ps.findValueInfo(query,false);
//            System.out.println("version==========="+version);
//            System.out.println("part.getMasterBsoID()==========="+part.getMasterBsoID());
//            System.out.println("��С====="+result.size());
            Iterator ite = result.iterator();
            while(ite.hasNext())
            {
                QMPartIfc value = (QMPartIfc)ite.next();
//                System.out.println("�㲿��==========="+value);
                
                HashMap ibaMap=IBAservice.getIBADefinitionAndValues(value);
                ArrayList al=(ArrayList)ibaMap.get("sourceVersion");
                String caiyongNum= (String)al.get(0);
//                System.out.println("caiyongNum========11111111==========="+caiyongNum);
                if(caiyongNum.equals(version))
                {
                    partIfc = value;
                }
            }
            
//            Vector partMasterInfoVec=(Vector)sPervice.findPartMaster(partName,partNum);
//            QMPartMasterIfc myPartInfo=(QMPartMasterIfc)partMasterInfoVec.get(0);
//            Vector partVec=(Vector)sPervice.findPart(myPartInfo);
//            
//            QMPartIfc lastPart=(QMPartIfc)partVec.get(0);
//            
//            String partVerstionID=lastPart.getVersionID();
//            
//            HashMap ibaMap=IBAservice.getIBADefinitionAndValues(lastPart);
////          System.out.println("0000000=================="+ibaMap);
////          System.out.println("1111111=================="+ibaMap.get("AdoptNumber_jf").getClass());
//            
//           ArrayList al=(ArrayList)ibaMap.get("AdoptNumber_jf");
//           String caiyongNum= (String)al.get(0);
//           System.out.println("caiyongNum========11111111==========="+caiyongNum);
//           ArrayList proPartNumList=(ArrayList)ibaMap.get("ProcessPartNumber");
//           String biangengNum=(String)proPartNumList.get(0);

            if(partIfc != null)
            {
                str = partIfc.getVersionValue();
//                System.out.println("partIfc.getVersionValue();==========="+partIfc.getVersionValue());
              //���¹������Ӽ�id�滻����һ���㲿��id
                Connection conn = null;
                Statement stmt =null;
                ResultSet rs=null;
                try
                {
                    conn = PersistUtil.getConnection();
                    stmt = conn.createStatement();
                    stmt.executeQuery("update GYBomStructure set childPart='" + partIfc.getBsoID() + "' where effectCurrent='0' and childPart='" + part.getBsoID() + "' and dwbs='"+dwbs+"'");
                    
                    //�ر�Statement
                    stmt.close();
                    //�ر����ݿ�����
                    conn.close();
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
                
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return str;
      }
      //CCEnd SS3
      
      //SS2 begin
	// 20170425 ���ӳ�ʼ������BOMǰ���һ���Ӽ�����������״̬
	/**
	 * ��ʼ������BOMǰ�ļ�� ��鳵���µ�һ���Ӽ�����������״̬�Ƿ�Ϊ�������ɡ�����������������״̬Ϊ�������ɡ���һ���Ӽ���ŷ��ؿͻ���
	 * 
	 * @param partID
	 *            ���͵�ID
	 * @return string
	 * @throws QMException
	 */
	public static String checkInitGYBom(String partID) throws QMException
	{
//		System.out.println("come in helper checkInitGYBom   partID=="+partID);
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			StandardPartService sp=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(partID);
			// �����㲿�������ó��͵�����һ���Ӽ�
			Collection coll=sp.getSubParts(part);
			String returnStr="";
			for(Iterator iter=coll.iterator();iter.hasNext();)
			{
				QMPartIfc subPart=(QMPartIfc)iter.next();
				String state=subPart.getLifeCycleState().toString();
				// �ж�һ���Ӽ�����������״̬�Ƿ�Ϊ�������ɡ�
				if(state.equals("SHEJIWANCHENG"))
				{
					if(returnStr=="")
					{
						returnStr=subPart.getPartNumber();
					}
					else
					{
						returnStr=returnStr+","+subPart.getPartNumber();
					}
				}
			}
			return returnStr;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}

	/**
	 * ����������ʼ����BOM
	 **/
	public static String initGYZCBom(String partID,String gyID,String dwbs)
	{
		long t1=System.currentTimeMillis();
//		System.out.println("come in helper initGYBom   partID=="+partID+"  gyID=="+gyID+"  dwbs=="+dwbs);
		String str="";
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			GYBomService gybs=(GYBomService)EJBServiceHelper.getService("GYBomService");
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(gyID);

			gybs.initGYZCBom(partID,gyID,dwbs);
			str=getGYBom(gyID,part.getPartNumber(),dwbs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		long t2=System.currentTimeMillis();
//		System.out.println("over in helper initGYBom  ��ʱ�� "+(t2-t1)+" ����");
		return str;
	}

	/**
	 * ���ɳ��ܳ�ʼ����BOM
	 **/
	public static String initGYCJBom(String partID,String gyID,String dwbs)
	{
		long t1=System.currentTimeMillis();
//		System.out.println("come in helper initGYBom   partID=="+partID+"  gyID=="+gyID+"  dwbs=="+dwbs);
		String str="";
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			GYBomService gybs=(GYBomService)EJBServiceHelper.getService("GYBomService");
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(gyID);

			gybs.initGYCJBom(partID,gyID,dwbs);
			str=getGYBom(gyID,part.getPartNumber(),dwbs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		long t2=System.currentTimeMillis();
//		System.out.println("over in helper initGYBom  ��ʱ�� "+(t2-t1)+" ����");
		return str;
	}

	/**
	 * ���ɼ�ʻ�ҳ�ʼ����BOM
	 **/
	public static String initGYJSSBom(String partID,String gyID,String dwbs,String newnumber)
	{
		long t1=System.currentTimeMillis();
//		System.out.println("come in helper initGYBom   partID=="+partID+"  gyID=="+gyID+"  dwbs=="+dwbs);
		String str="";
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			GYBomService gybs=(GYBomService)EJBServiceHelper.getService("GYBomService");
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(gyID);

			gybs.initGYJSSBom(partID,gyID,dwbs,newnumber);
			str=getGYBom(gyID,part.getPartNumber(),dwbs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		long t2=System.currentTimeMillis();
//		System.out.println("over in helper initGYBom  ��ʱ�� "+(t2-t1)+" ����");
		return str;
	}
	
	/**
   * ���汾
   * ������bom�Ͻڵ�汾����һ���汾������ԭ���汾�ǡ�C����ѡ�����汾֮���Ϊ��D���汾
   * @parm String linkid ����id��linkid��
   * @parm String id ѡ�нڵ�bsoid��id��
   * @parm String carModelCode ������
   * @parm String dwbs ���� 
   */
  public static String upgradeVersion(String linkid, String id, String carModelCode, String dwbs) throws QMException
  {
		long t1 = System.currentTimeMillis();
//  	System.out.println("come in helper upperVersion  linkid=="+linkid+"  id=="+id+"  carModelCode=="+carModelCode+"  dwbs =="+dwbs);
  	String str = "";
		try
		{
  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
  		
    	str = gybs.upgradeVersion(linkid, id, carModelCode, dwbs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
//		System.out.println("over in helper upperVersion  ��ʱ�� "+(t2-t1)+" ����");
		return str;
  }
	//SS2 end
	
	
      //CCBegin SS4
      /**
       * �Զ�����BOM������
       * @param content 
       * @return 
       * @exception com.faw_qm.framework.exceptions.QMException
       */
        public static String createBom(String carModelCodeStr, String publishType) throws QMException
        {
            Connection conn = null;
            Statement stmt =null;
            ResultSet rs=null;
            String ret = "";
            try
            {
            	//CCBegin SS12
            	PersistService pss = (PersistService)EJBServiceHelper.getPersistService();
            	QMPartIfc partifc = (QMPartIfc)pss.refreshInfo(carModelCodeStr, false);
            	//CCEnd SS12
                conn=PersistUtil.getConnection();
                stmt=conn.createStatement();
              //CCBegin SS12
//              String sql="select * from BOMChangeContent where carModelCode='"
//                  +carModelCodeStr+"' order by id";
              String sql="select * from BOMChangeContent where carModelCode='"
                      +partifc.getPartNumber()+"' order by id";
              //CCEnd SS12
//                System.out.println(sql);
                rs=stmt.executeQuery(sql);

                //���ü���
                ArrayList adoptList = new ArrayList();
                //�����ü���
                ArrayList partList = new ArrayList();
                ArrayList arr = new ArrayList();
                while(rs.next())
                {
//                    id   id
//                    ���ͺ�  carModelCode
//                    ����   dwbs
//                    ����   parentPartID
//                    �Ӽ�   subPartID
//                    �޸�ǰ����    quantity1
//                    �޸ĺ�����    quantity2
//                    ��ɾ�ĵı�ǣ�A,D,U��    sign
                    String id=rs.getString("id");
                    String carModelCode=rs.getString("carModelCode");
                    String dwbs=rs.getString("dwbs");
                    String parentPartID=rs.getString("parentPartID");
                    String subPartID=rs.getString("subPartID");
                    String quantity1=rs.getString("quantity1");
                    String quantity2=rs.getString("quantity2");
                    String sign=rs.getString("sign");
//                    System.out.println("����   parentPartID==========="+parentPartID);
                    BOMChangeContentBean bean = new BOMChangeContentBean();
                    bean.setCarModelCode(carModelCode);
                    bean.setDwbs(dwbs);
                    bean.setId(id);
                    bean.setParentPartID(parentPartID);
                    bean.setQuantity1(quantity1);
                    bean.setQuantity2(quantity2);
                    bean.setSign(sign);
                    bean.setSubPartID(subPartID);
                    
                    if(sign.equalsIgnoreCase("A"))
                    {
                        adoptList.add(bean);
                    }
                    else if(sign.equalsIgnoreCase("D"))
                    {
                        partList.add(bean);
                    }
                    //CCBegin SS13
                    else if(sign.equalsIgnoreCase("T")){
                    	String tsubPartID = rs.getString("tsubPartID");
                    	bean.setSubPartID(tsubPartID);
                    	bean.setTSubPartID(subPartID);
                    	adoptList.add(bean);
                    	BOMChangeContentBean bean1 = new BOMChangeContentBean();
                        bean1.setCarModelCode(carModelCode);
                        bean1.setDwbs(dwbs);
                        bean1.setId(id);
                        bean1.setParentPartID(parentPartID);
                        bean1.setQuantity1(quantity1);
                        bean1.setQuantity2(quantity2);
                        bean1.setSign(sign);
                        bean1.setSubPartID(subPartID);
                        partList.add(bean1);
                    }
                    //CCEnd SS13
                    else
                    {
                        if(quantity1 != null && !quantity1.equals(""))
                        {
                            partList.add(bean);
                            arr.add(bean.getParentPartID()+bean.getSubPartID());
                        }
                        else
                        {
                            adoptList.add(bean);
                            arr.add(bean.getParentPartID()+bean.getSubPartID());
                        }
                    }
                }
                
//                if(true)
//                {
//                    return "";
//                }
                
                SessionService sservice = (SessionService)EJBServiceHelper.getService("SessionService");
                PersistService ps = (PersistService)EJBServiceHelper.getPersistService();
                GYBomNoticeService gs = (GYBomNoticeService)EJBServiceHelper.getService("GYBomNoticeService");
                FolderService fservice = (FolderService) EJBServiceHelper.getService("FolderService");
                LifeCycleService lifeCycle = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");
                QMPartIfc root = (QMPartIfc)ps.refreshInfo(carModelCodeStr, false);
                GYBomAdoptNoticeInfo notice = new GYBomAdoptNoticeInfo();
                String type = "";
                if(publishType.equals("0"))
                {
                    type = "��������BOM���õ�";
                }
                else if(publishType.equals("1"))
                {
                    type = "���ܹ���BOM���õ�";
                }
                else
                {
                    type = "��ʻ�ҹ���BOM���õ�";
                }
                notice.setPublishType(type);
                notice.setTopPart(carModelCodeStr);
                notice.setCreator(sservice.getCurUserID());
                notice.setOwner(sservice.getCurUserID());
                //�Զ����
                String adoptnoticenumber = getBomAdoptNoticeNumber("ZC");
                notice.setAdoptnoticenumber(adoptnoticenumber);
                //CCBegin SS16
                notice.setAdoptnoticename(root.getPartNumber()+"�Ĺ���BOM������");
                //CCEnd SS16
                
                String folderStr = "\\Root\\��������BOM���ø��ĵ�";
                notice.setLocation(folderStr);
                fservice.assignFolder((FolderEntryIfc) notice, folderStr);
                
                //CCBegin SS11
//                lifeCycle.setLifeCycle(notice, lifeCycle.getLifeCycleTemplate("�ɶ�����BOM��������������"));
                //CCBegin SS19
                //String userComp=getUserFromCompany();
                String userComp=getCompany();
                //CCEnd SS19
                if(userComp.equals("cd")){
                	 lifeCycle.setLifeCycle(notice, lifeCycle.getLifeCycleTemplate("�ɶ�����BOM��������������"));
                }else{
                	 lifeCycle.setLifeCycle(notice, lifeCycle.getLifeCycleTemplate("����BOM��������������"));
                }
                //CCEnd SS11
                
                notice = (GYBomAdoptNoticeInfo)ps.saveValueInfo(notice);
                //���ü���
                for(int i=0; i<adoptList.size(); i++)
                {
                    BOMChangeContentBean bean = (BOMChangeContentBean)adoptList.get(i);
                    GYBomAdoptNoticePartLinkInfo link = new GYBomAdoptNoticePartLinkInfo();
                    System.out.println("bean.getSubPartID()==="+bean.getSubPartID());
                    QMPartInfo part = (QMPartInfo)ps.refreshInfo(bean.getSubPartID(), false);
                    link.setAdoptBs("����");
                    link.setBz2(bean.getParentPartID());//����
                    int size = arr.indexOf(bean.getParentPartID()+bean.getSubPartID());
                    link.setBz4(String.valueOf(size));//ƥ���ʶ���滻��ʱ���滻���ͱ��滻��ƥ���ã�һ�ű�һ��ƥ�����ʶΨһ
                    String bz3 = getBz3(carModelCodeStr ,bean.getSubPartID());
                    link.setBz3(bz3);//���飬�ڹ���BOM���BZ2����
                    link.setLifecyclestate(part.getLifeCycleState().toString());
                    link.setLinkPart(bean.getTSubPartID());//���������ü�������ǲ��ü������ʾ�滻�Ǹ������ü���
                    link.setNoticeID(notice.getBsoID());
                    link.setPartID(bean.getSubPartID());
                    link.setPartName(part.getPartName());
                    link.setPartNumber(part.getPartNumber());
                    link.setPartView(part.getViewName());
                    link.setSl(bean.getQuantity2());//����
                    //CCBegin SS14
//                    link.setVersionValue(part.getVersionValue());
        			String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",part.getBsoID());
        			if(fbyIbaValue==""){
        				fbyIbaValue = part.getVersionValue();
        			}
                    link.setVersionValue(fbyIbaValue);
                    //CCEnd SS14
                    QMPartInfo parts[] = new QMPartInfo[1];
                    parts[0] = part;
                    Vector vec = (Vector)gs.findPartAndRoute(parts, root);
                    if(vec.size()>0)
                    {
                        String[] partStr = (String[])vec.get(0);
                        link.setZplx(partStr[8]);//װ��
                        link.setZzlx(partStr[7]);//����
                    }
                    
                    ps.saveValueInfo(link, false);
                }
                //�����ü���
                for(int i=0; i<partList.size(); i++)
                {
                    BOMChangeContentBean bean = (BOMChangeContentBean)partList.get(i);
                    GYBomAdoptNoticePartLinkInfo link = new GYBomAdoptNoticePartLinkInfo();
                    QMPartInfo part = (QMPartInfo)ps.refreshInfo(bean.getSubPartID(), false);
                    link.setAdoptBs("������");
                    link.setBz2(bean.getParentPartID());//����
                    int size = arr.indexOf(bean.getParentPartID()+bean.getSubPartID());
                    link.setBz4(String.valueOf(size));//ƥ���ʶ���滻��ʱ���滻���ͱ��滻��ƥ���ã�һ�ű�һ��ƥ�����ʶΨһ
                    String bz3 = getBz3(carModelCodeStr ,bean.getSubPartID());
                    link.setBz3(bz3);//���飬�ڹ���BOM���BZ2����
                    link.setLifecyclestate(part.getLifeCycleState().toString());
                    link.setLinkPart(bean.getTSubPartID());//���������ü�������ǲ��ü������ʾ�滻�Ǹ������ü���
                    link.setNoticeID(notice.getBsoID());
                    link.setPartID(bean.getSubPartID());
                    link.setPartName(part.getPartName());
                    link.setPartNumber(part.getPartNumber());
                    link.setPartView(part.getViewName());
                    //CCBegin SS20
//                  link.setSl(bean.getQuantity2());//����
                    link.setSl(bean.getQuantity1());//����
                    //CCEnd SS20
                    link.setVersionValue(part.getVersionValue());
                    //CCBegin SS14
//                  link.setVersionValue(part.getVersionValue());
	      			String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",part.getBsoID());
	      			if(fbyIbaValue==""){
	      				fbyIbaValue = part.getVersionValue();
	      			}
	      			link.setVersionValue(fbyIbaValue);
	      			//CCEnd SS14
                    QMPartInfo parts[] = new QMPartInfo[1];
                    parts[0] = part;
                    Vector vec = (Vector)gs.findPartAndRoute(parts, root);
                    if(vec.size()>0)
                    {
                        String[] partStr = (String[])vec.get(0);
                        link.setZplx(partStr[8]);//װ��
                        link.setZzlx(partStr[7]);//����
                    }
                    
                    ps.saveValueInfo(link, false);
                }
                ret = notice.getBsoID();
                // ��ղ��ر�sql��������
                rs.close();
                // �ر�Statement
                stmt.close();
                // �ر����ݿ�����
                conn.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    if(rs!=null)
                    {
                        rs.close();
                    }
                    if(stmt!=null)
                    {
                        stmt.close();
                    }
                    if(conn!=null)
                    {
                        conn.close();
                    }
                }
                catch(SQLException ex1)
                {
                    ex1.printStackTrace();
                }
            }
//            ret = "GYBomAdoptNotice_105737679";
            
//            System.out.println("ret  =========   " + ret);
            return ret;
        }
      
        /**
         * ��÷�������ţ��Զ���ˮ�ţ�
         * @param noticeIfc BomAdoptNoticeIfc
         * @throws QMException
         * @return String
         */
        public static String getBomAdoptNoticeNumber(String type) throws QMException
        {
            String result = "";
            try
            {
                //������������λ
                String yearLast = new SimpleDateFormat("yy",Locale.CHINESE).format(Calendar.getInstance().getTime());
                String className = "GYBomAdoptNotice";
                String baseKey="LX-JF-"+yearLast+"-"+type;
                
                //ȡ������1λ���кŹ����3λ
                int sortNumber = getNextSortNumber(className, baseKey, false);
                char[] sortNum = String.valueOf(sortNumber).toCharArray();
                char[] arry  = {'0','0','0','0'};
                System.arraycopy(sortNum, 0, arry, arry.length-sortNum.length, sortNum.length);
                String resNum = new String(arry);
                result = "LX-JF-"+yearLast+"-"+type+"-"+resNum;
            }catch(Exception e)
            {
                throw new QMException(e);
            }
            return result.toUpperCase();
        }
        
        /**
         * ���õ��Զ���š�������к�
         * @param String className ��������
         * @param String baseKey ���к�ͷ
         * @param boolean changeFlag �Ƿ���
         * @throws QMException
         * @return QMPartInfo
         */
        private static int getNextSortNumber(String className, String baseKey, boolean changeFlag) throws QMException
        {
            int result = 0;
            try
            {
                PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
                QMQuery query = new QMQuery("UniteIdentify");
                QueryCondition qc = new QueryCondition("className", "=", className);
                query.addCondition(qc);
                QueryCondition qc1 = new QueryCondition("baseKey", "=", baseKey);
                query.addAND();
                query.addCondition(qc1);
                Collection col = pService.findValueInfo(query);
                UniteIdentifyInfo uniteIde;
                if(col.size() == 0)
                {
                    uniteIde = createUniteIdentify(className, baseKey, changeFlag);
                    
                    result = uniteIde.getSortNumber();
                }else
                {
                    Iterator ite = col.iterator();
                    uniteIde = (UniteIdentifyInfo)ite.next();
                    result = uniteIde.getSortNumber();
                }
                uniteIde.setSortNumber(result + 1);
                pService.saveValueInfo(uniteIde);

            }catch(QMException e)
            {
                e.printStackTrace();
                throw new QMException(e);
            }
            return result;
        }
        
        /**
         * ���õ��Զ���š�������к�Ψһ�Զ���
         * @param String className ��������
         * @param String baseKey ���к�ͷ
         * @param boolean changeFlag �Ƿ���
         * @throws QMException
         * @return UniteIdentifyInfo
         */
        private static UniteIdentifyInfo createUniteIdentify(String className, String baseKey, boolean changeFlag) throws QMException
        {
            UniteIdentifyInfo ui = null;
            try
            {
                PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
                if(changeFlag)
                {
                    QMQuery query = new QMQuery("UniteIdentify");
                    QueryCondition qc = new QueryCondition("className", "=", className);
                    query.addCondition(qc);
                    Collection col = pService.findValueInfo(query);
                    Iterator ite = col.iterator();
                    while(ite.hasNext())
                    {
                        UniteIdentifyInfo oldui = (UniteIdentifyInfo)ite.next();
                        pService.deleteValueInfo(oldui);
                    }
                }
                ui = new UniteIdentifyInfo();
                ui.setClassName(className);
                ui.setBaseKey(baseKey);
                ui.setChangeFlag(changeFlag);
                ui.setSortNumber(1);
                ui = (UniteIdentifyInfo)pService.saveValueInfo(ui);
            }catch(QMException e)
            {
                e.printStackTrace();
                throw new QMException(e);
            }
            return ui;
        } 
        
        private static String getBz3(String parentID, String childID)
        {
            String ret = null;
          //���¹������Ӽ�id�滻����һ���㲿��id
            Connection conn = null;
            Statement stmt =null;
            ResultSet rs=null;
            try
            {
                conn = PersistUtil.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("select * from gybomstructure where parentPart='" + 
                        parentID + "' and childPart='" + childID + "'");
                if(rs.next())
                {
                    ret=rs.getString("bz2");
                }
                //�ر�Statement
                stmt.close();
                //�ر����ݿ�����
                conn.close();
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
            return ret;
        }
      //CCEnd SS4
	
	
	//CCBegin SS5
	/**�滻��
	 * @param linkid linkid
	 * @param yid ԭ�ڵ�id
	 * @param xid �滻�ڵ�id
	 * @param carModelCode ����
	 * @param dwbs ����
	 * @return
	 * @throws QMException
	 */
     //CCBegin SS17
//	public static String changePart(String linkid,String yid,String xid,String carModelCode,String dwbs) throws QMException{
    public static String changePart(String linkid,String yid,String xid,String carModelCode,String dwbs,String parentid,String changeType) throws QMException{
        	//CCEnd SS17
		String s = "";
		try {
			GYBomService gybs = (GYBomService) EJBServiceHelper
					.getService("GYBomService");
			//CCBegin SS17
//			s = gybs.changePart(linkid,yid,xid,carModelCode,dwbs);
			s = gybs.changePart(linkid,yid,xid,carModelCode,dwbs,parentid,changeType);
			//CCEnd SS17
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	//CCEnd SS5
	
	
	//CCBegin SS6
	/**
	 * ��ȡ��ǰ�û����ڵ�λ��ơ�
	 * @return ��λ����
	 * @throws QMException
	 */
	public static String getUserFromCompany() throws QMException 
	{
		String s = "";
		try
		{
			GYBomService gybs = (GYBomService) EJBServiceHelper.getService("GYBomService");
			s = "("+gybs.getCurrentDWBS()+")";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	//CCEnd SS6
	
	
      //CCBegin SS7
        /**
         * �������Ƽ�
         * @param num ���Ƶ���� 
         * @return 
         * @exception com.faw_qm.framework.exceptions.QMException
         */
          public static String shizhisearch(String num) throws QMException
          {
              Connection conn = null;
              Statement stmt =null;
              ResultSet rs=null;
              String ret = "[";
              try
              {
                  conn=PersistUtil.getConnection();
                  stmt=conn.createStatement();
                  String sql="select * from GYBomShiZhiPart where shiZhiNumber like '%"
                      +num+"%'";
                  System.out.println(sql);
                  rs=stmt.executeQuery(sql);
                  boolean flag = true;
                  while(rs.next())
                  {
                      String partNumber=rs.getString("partNumber");
                      String partName=rs.getString("partName");
                      String shiZhiNumber=rs.getString("shiZhiNumber");
                      String routeListNumber=rs.getString("routeListNumber");
                      String partVersion=rs.getString("partVersion");
                      String partView =rs.getString("partView");
                      String partID =rs.getString("partID");
                       
                      
                      if(flag)
                      {
                          ret = ret + "{'num':'" + partNumber + "','name':'" + partName + "','shiZhiNumber':'" + 
                                  shiZhiNumber + "','routeListNumber':'" + routeListNumber + 
                                  "','partVersion':'" + partVersion + "','partView':'" + 
                                  partView + "','partID':'" + partID + "'}";
                          flag = false;
                      }else
                      {
                          ret = ret + ",{'num':'" + partNumber + "','name':'" + partName + "','shiZhiNumber':'" + 
                                  shiZhiNumber + "','routeListNumber':'" + routeListNumber + 
                                  "','partVersion':'" + partVersion + "','partView':'" + 
                                  partView + "','partID':'" + partID + "'}";
                      }
                  }
                  ret=ret+"]";
                  
                  // ��ղ��ر�sql��������
                  rs.close();
                  // �ر�Statement
                  stmt.close();
                  // �ر����ݿ�����
                  conn.close();
              }
              catch(Exception e)
              {
                  e.printStackTrace();
              }
              finally
              {
                  try
                  {
                      if(rs!=null)
                      {
                          rs.close();
                      }
                      if(stmt!=null)
                      {
                          stmt.close();
                      }
                      if(conn!=null)
                      {
                          conn.close();
                      }
                  }
                  catch(SQLException ex1)
                  {
                      ex1.printStackTrace();
                  }
              }
//              ret = "GYBomAdoptNotice_105737679";
              
              System.out.println("ret  =========   " + ret);
              return ret;
          }
          
      /**
       * �������Ƽ�
       * @param partID ���ID 
       * @exception com.faw_qm.framework.exceptions.QMException
       */
        public static void saveShizhiPart(String partID, String shiZhiNumber) throws QMException
        {
            PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
            
            QMPartIfc part = (QMPartIfc)pService.refreshInfo(partID, false);
            String routeListNumber = "";
//            part
            Connection conn=null;
            Statement st=null;
            try
            {
                conn=PersistUtil.getConnection();
                st=conn.createStatement();
                String sql = "INSERT INTO gybomshizhipart (partID ,partVersion ,partView ,partNumber ,partName ," +
                		"shiZhiNumber ,routeListNumber ,bz1 ,bz2 ,bz3 ,bz4) VALUES ('"+partID+"','"+
                		part.getVersionValue()+"','"+part.getViewName()+"','"+part.getPartNumber()+"','"+
                		part.getPartName()+"','"+shiZhiNumber+"','"+routeListNumber+"','','','','')";
                st.addBatch(sql);
                st.executeBatch();
                
//                System.out.println("-----------------�������Ƽ�-----------"+sql);
                // �ر�Statement
                st.close();
                // �ر����ݿ�����
                conn.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    st.close();
                    conn.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        /**
         * �������Ƽ��ṹ
         * @param partent ����ID 
         * @param child �Ӽ�ID
         * @param quantity ����
         * @exception com.faw_qm.framework.exceptions.QMException
         */
          public static void saveShizhiStructure(String partent, String child, String quantity) throws QMException
          {
              Connection conn=null;
              Statement st=null;
              try
              {
                  conn=PersistUtil.getConnection();
                  st=conn.createStatement();
                  
//                  String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                  String sql = "INSERT INTO gybomshizhistructure (ID, PARENTPART ,CHILDPART ,QUANTITY ," +
                  		"CARMODELCODE ,DWBS ,EFFECTCURRENT ,LOCKER ,LOCKDATE ,UNIT ,CHILDNUMBER " +
                  		",bz1 ,bz2 ,bz3 ,bz4) VALUES ('"+new Date().getTime()+"','"+
                  		partent+"','"+child+"','"+quantity+"','','','','','','','','','','','')";
                  System.out.println("-----------------�������Ƽ��ṹ-----------"+sql);
                  // �ر�Statement
                  st.addBatch(sql);
                  st.executeBatch();
                  
                  st.close();
                  // �ر����ݿ�����
                  conn.close();
              }
              catch(Exception e)
              {
                  e.printStackTrace();
              }
              finally
              {
                  try
                  {
                      st.close();
                      conn.close();
                  }
                  catch(SQLException e)
                  {
                      e.printStackTrace();
                  }
              }
          }
    
    /**
     * ��ȡ���Ƽ��ṹ
     * @param partID
     * @return
     */
    public static Vector getShizhiStructure(String partID)
    {
//        System.out.println("-------------��ȡ���Ƽ��ṹ-----------------------");
        Vector resultVector = new Vector();
        //���¹������Ӽ�id�滻����һ���㲿��id
          Connection conn = null;
          Statement stmt =null;
          ResultSet rs=null;
          
          Statement stmt1 =null;
          ResultSet rs1=null;
          try
          {
              
              conn = PersistUtil.getConnection();
              stmt = conn.createStatement();
              rs = stmt.executeQuery("select * from qmpart a , gybomshizhistructure b, Qmpartmaster c " +
              		"where a.bsoID = b.childpart and a.masterbsoid = c.bsoid  and b.parentpart = '" + partID + "'");
              while(rs.next())
              {
                  String bsoID=rs.getString("childpart");
                  String partNumber=rs.getString("PARTNUMBER");
                  String partName=rs.getString("PARTNAME");
                  String versionValue=rs.getString("versionValue");
                  String viewName=rs.getString("viewName");
                  String quantity=rs.getString("quantity");
                  String partUsageLink=rs.getString("id");
                  
                  NormalPart normalPart1 = new NormalPart();
                  normalPart1.bsoID = bsoID;
                  normalPart1.parentBsoID = partID;
                  normalPart1.partName = partName;
                  normalPart1.partNumber = partNumber;
                  normalPart1.versionID = "";
                  normalPart1.iterationID = "";
                  normalPart1.versionValue = versionValue;
                  normalPart1.viewName = viewName;
                  normalPart1.quantity = Float.valueOf(quantity);
//                  normalPart1.defaultUnit = "";
                  normalPart1.partUsageLink = partUsageLink;
                  //PartServiceHelper pshelper= new PartServiceHelper();
                  normalPart1.partIcon = "images/part.gif";
                  //��Ϊ�㲿������Ϣ�ڲ鿴�ṹ�в�����չ����
                  stmt1 = conn.createStatement();
                  rs1 = stmt1.executeQuery("select * from gybomshizhistructure where parentpart = '" + bsoID + "'");
                  normalPart1.isHasSub = "0";
                  if(rs1.next())
                  {
                      normalPart1.isHasSub = "1";
                  }
                  if(normalPart1.isHasSub.equals("0"))
                  {
                      PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
                      StandardPartService ss = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
                      PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
                      QMPartIfc part = (QMPartIfc)pService.refreshInfo(bsoID, false);
                      //��Ϊ�㲿������Ϣ�ڲ鿴�ṹ�в�����չ����
                      Collection collection1 = ss.getUsesPartIfcs(part, partConfigSpecIfc);

                      //�Ƿ����Ӽ��ı�ʶ����ʼ��Ϊ0����û���Ӽ����û���Ӽ��ķ���
                      //getUsesPartIfcs()���������к����Ӽ���������Ϊ1��
                      normalPart1.isHasSub = "0";
                      if((collection1.size() > 0) || (collection1 != null))
                      {
                         Object[] resultArray = new Object[collection1.size()];
                         collection1.toArray(resultArray);
                         for (int ii=0; ii<resultArray.length; ii++)
                         {
                             Object obj = resultArray[ii];
                            if(obj instanceof Object[])
                            {
                               Object[] obj1 = (Object[])obj;

                               if (obj1[1] instanceof QMPartIfc)
                               {
                                  normalPart1.isHasSub = "1";
                                  break;
                               }
                            }
                          }
                      }
                  }
                  //�����õ����Ӽ�Ҫ�����Ƿ�չ������ʼ������Ϊ0��������Ҫչ����
                  //��Ϊ�ڲ鿴�ṹ�Ľ��������ڵ������չ�������Ե�ǰ�㶼�ǲ�չ��״̬��
                  //ֻ�е��չ���󣬲Ż�ı�״̬��
                  normalPart1.isOpen = "0";
//System.out.println("234234234234234234234234234aaaaaaaaa=="+partNumber);
                  resultVector.addElement(normalPart1);
              }
              //�ر�Statement
              stmt.close();
              //�ر����ݿ�����
              conn.close();
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
          finally
          {
              try
              {
                  if (rs1 != null)
                  {
                      rs1.close();
                  }
                  if (stmt1 != null)
                  {
                      stmt1.close();
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
              catch (SQLException ex1)
              {
                  ex1.printStackTrace();
              }
          }
          return resultVector;
      }
    
      //CCEnd SS7
    //CCBegin SS8
  	/**�滻��
  	 * @param linkid linkid
  	 * @param yid ԭ�ڵ�id
  	 * @param xid �滻�ڵ�id
  	 * @param carModelCode ����
  	 * @param dwbs ����
  	 * @return
  	 * @throws QMException
  	 */
  	public static String searchLinkBook(String carModelCode) throws QMException{
  		String s = "";
  		try {
  			GYBomService gybs = (GYBomService) EJBServiceHelper
  					.getService("GYBomService");
  			s = gybs.searchLinkBook(carModelCode);
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		return s;
  	}
  	//CCEnd SS8
  	//CCBegin SS9
    /**
     * ����BOM
     */
    public static Vector getExportBOMList(String id, String carModelCode, String dwbs) throws QMException
    {
  		long t1 = System.currentTimeMillis();
//    	System.out.println("come in helper getExportFirstLeveList  id=="+id+"  carModelCode=="+carModelCode+"  dwbs =="+dwbs);
    	Vector vec = new Vector();
  		try
  		{
    		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
    		
      	vec = gybs.getExportBOMList(id,carModelCode,dwbs);
  		}
  		catch (Exception e)
  		{
  			e.printStackTrace();
  		}
  		long t2 = System.currentTimeMillis();
//  		System.out.println("over in helper getExportFirstLeveList  ��ʱ�� "+(t2-t1)+" ����");
  		return vec;
    }
    //CCEnd SS9
    //CCBegin SS10
    /**
     * �ṹ�Ƚ�
     */
    public static Vector CompartTreeResult1(String desginID, String gyID, String carModelCode, String dwbs,String isRelease) throws QMException
    {
  		long t1 = System.currentTimeMillis();
  		Vector s = new Vector() ;
    	try
    	{
    		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
    		s = (Vector)gybs.CompartTreeResult1(desginID, gyID,carModelCode, dwbs,isRelease);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
  		long t2 = System.currentTimeMillis();
    	return s;
    }
    //CCEnd SS10
  //CCBegin SS15
    /**
     * �ṹ�Ƚ�
     */
    public static String getEffect(String parentPart,String carModelCode,String dwbs) throws QMException
    {
  		long t1 = System.currentTimeMillis();
  		String s = "0";
    	try
    	{
    		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
    		s = (String)gybs.getEffect(parentPart,carModelCode, dwbs);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
  		long t2 = System.currentTimeMillis();
  		s = "[{"+s+"}]";
    	return s;
    }
    //CCEnd SS15
    //CCBegin SS17
    /**
     * @param linkid linkid
     * @param yid ԭ�ڵ�id
     * @param xid �滻�ڵ�id
     * @param carModelCode ����
     * @param dwbs ����
     * @param parentid ����id
     * @param changeType 
     * @return
     * @throws QMException
     */
    public static String changePartOther(String linkid,String yid,String xid,String carModelCode,String dwbs,String parentid,String productid) throws QMException{
        	
		String s = "";
		try {
			GYBomService gybs = (GYBomService) EJBServiceHelper
					.getService("GYBomService");
			s = gybs.changePartOther(linkid,yid,xid,carModelCode,dwbs,parentid,productid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
  //CCEnd SS17
    //CCBegin SS18
    /**�������
     * @param partid ѡ�нڵ�����id
     * @param dwbs ����
     * @return �Ƿ�ɹ�
     */
    public static String lockPart(String partid,String dwbs)throws QMException{
    	String s="";
    	try{
    		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
    		s = gybs.lockPart(partid,dwbs);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return s;
    }
    /**�������
     * @param partid ѡ�нڵ�����id
     * @param dwbs ����
     * @return �Ƿ�ɹ�
     */
    public static String unLockPart(String partid,String dwbs)throws QMException{
    	String s="";
    	try{
    		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
    		s = gybs.unLockPart(partid,dwbs);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return s;
    }
	/**
	 * ��ѯ�����û��Ƿ�Ϊ��ǰ�û�
	 * 
	 * @return false û�е�ǰ�û������Ķ���,true �е�ǰ�û������Ķ���
	 * @throws QMException
	 */
	public static String searchLockUser(String partid, String dwbs) throws QMException{
    	String s="";
    	try{
    		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
    		s = gybs.searchLockUser(partid,dwbs);
//    		if(flag){
//    			s = "[{1}]";
//    		}else{
//    			s = "[{0}]";
//    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	s = "[{"+s+"}]";
    	return s;
    }
    //CCEnd SS18
    //	CCBegin SS19
	/**
	 * ��ȡ��ǰ�û����ڵ�λ��ơ�
	 * @return ��λ����
	 * @throws QMException
	 */
	public static String getCompany() throws QMException 
	{
		DocServiceHelper dsh=new DocServiceHelper();
		String com=dsh.getUserFromCompany();
		return com;
	}
//	CCEnd SS19
    //	CCBegin SS21
	  /**
	   * У�鹤��bom�Ƿ�༭·�߲�������
	   */
	  public static Vector checkBom(String carModelCode) throws QMException
	  {
			long t1 = System.currentTimeMillis();
//	  	System.out.println("come in helper getExportBomList carModelCode=="+carModelCode);
	  	Vector vec = new Vector();
			try
			{
	  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
	  		
	    	vec = gybs.checkBom(carModelCode);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			long t2 = System.currentTimeMillis();
//			System.out.println("over in helper getExportBomList  ��ʱ�� "+(t2-t1)+" ����");
			return vec;
	  }//	CCEnd SS21
	  //	CCBegin SS22
	  /**
		 * ���ݸ������Ӽ��͹������ж�ϵͳ���Ƿ���ڸ�BOM
		 */
		public String getExitBOMnew(String parentPart,String childPart,String sl,String carModelCode,String dwbs) throws QMException
		{
			// System.out.println("getEffect parentPart=="+parentPart+"  and carModelCode=="+carModelCode+"  and dwbs=="+dwbs);
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			String result="0";
			try
			{
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();

//		       System.out.println("sql==="+"select count(*) from GYBomStructure where  parentPart='"
//						+parentPart+"' and  childPart='"+childPart+"' and  quantity='"+sl+"' and dwbs='"+dwbs+"'");
				rs=stmt.executeQuery("select count(*) from GYBomStructure where  parentPart='"
					+parentPart+"' and  childPart='"+childPart+"' and  quantity='"+sl+"' and dwbs='"+dwbs+"'");
				rs.next();
				int countList=rs.getInt(1);
				if(countList>0)
				{
					result="1";
				}

			

				rs.close();
				// �ر�Statement
				stmt.close();
				// �ر����ݿ�����
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(rs!=null)
					{
						rs.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(conn!=null)
					{
						conn.close();
					}
				}
				catch(SQLException ex1)
				{
					ex1.printStackTrace();
				}
			}
			return result;
		}
		/**
		 * ���ݸ������Ӽ��͹������ж�ϵͳ���Ƿ���ڸ�BOM
		 */
		public String getExitBOM(String parentPart,String childPart,String sl,String carModelCode,String dwbs) throws QMException
		{
			// System.out.println("getEffect parentPart=="+parentPart+"  and carModelCode=="+carModelCode+"  and dwbs=="+dwbs);
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			String result="0";
			try
			{
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();

//		       System.out.println("sql==="+"select count(*) from GYBomStructure where  parentPart='"
//						+parentPart+"' and  childPart='"+childPart+"' and  quantity='"+sl+"' and dwbs='"+dwbs+"'");
				rs=stmt.executeQuery("select count(*) from GYBomStructure where  parentPart='"
					+parentPart+"' and dwbs='"+dwbs+"'");
				rs.next();
				int countList=rs.getInt(1);
				if(countList>0)
				{
					result="1";
				}

			

				rs.close();
				// �ر�Statement
				stmt.close();
				// �ر����ݿ�����
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(rs!=null)
					{
						rs.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(conn!=null)
					{
						conn.close();
					}
				}
				catch(SQLException ex1)
				{
					ex1.printStackTrace();
				}
			}
			return result;
		}//	CCEnd SS22
	  
	
}
