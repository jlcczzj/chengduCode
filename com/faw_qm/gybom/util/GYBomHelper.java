/**
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  * SS1 保存变更记录 liuyuzhu 2017-04-27
  * SS2 修改初始化工艺BOM,拆分成整车，车架，驾驶室三部分 刘涛 2017-5-10
  * SS3 增加变更单相关功能。lishu 2017-05-03
  * SS4 自动生成BOM发布单 lishu 2017-5-12
  * SS5 保存单独替换件的工艺BOM liuyuzhu 2017-05-19
  * SS6 获取当前用户所在单位。 吕正超 2017-5-24
  * SS7 试制零部件工艺BOM调整。 lishu 2017-5-25
  * SS8 查询关联的书签 liuyuzhu 2017-07-20
  * SS9 修改导出总成方法 liuyuzhu 2017-07-25
  * SS10 修改结构比较方法 liuyuzhu 2017-08-22
  * SS11 修改工艺BOM发布单生命周期名称 maxiaotong 2017-09-04 
  * SS12 修改变更单无法加载零部件问题 liuyuzhu 2017-09-04
  * SS13 修改替换件变更没有提取采用件问题 liuyuzhu 2017-09-18
  * SS14 修改版本显示（改为技术中心版本） liuyuzhu 2017-09-26
  * SS15 界面调用查询方法 liuyuzhu 2017-09-26
  * SS16 自动生成变更单名称 liuyuzhu 2017-10-19
  * SS17 替换件方法参数变化 liuyuzhu 2017-11-02
  * SS18 加锁零部件 liuyuzhu 2017-11-13
  * SS19 获取当前单位名称不对，进行修改 刘家坤 2017-12-17
  * SS20 修改采用数量提取问题 liuyuzhu 2017-12-20
  * SS21 校验工艺bom是否编辑路线并发布  刘家坤 2017-12-22
  * SS22 工艺BOM导入时候，需要判断下级子bom在系统是否存在 刘家坤 2017-12-26
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
 * 工艺BOM管理平台服务辅助类
 * @author 刘楠   修改时间  2016-5-16
 * @version 1.0
 */
public class GYBomHelper
{
  /**
   * 创建设计BOM关联
   * @param Collection mtreenode 目标树（工艺BOM）节点集合
   * 保存时要获取当前用户所在单位，然后直接通过数据库创建。
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
   * 拖拽参照BOM树上的总成及零部件到工艺BOM树上等操作；
   * 左右拖拽，参照bom树拖拽到目标bom树之后需要变图标处理
   * 左右拖拽：参照BOM树下的零部件及结构调整到工艺BOM树下，连带bom都需要拖拽到工艺bom树节点之下（拖拽之后需要进行局部刷新并保存）
   * 上下拖拽：工艺BOM树自身结构的调整，可以拖拽进行自身结构的调整，不需要改变图标
   * 上下拖拽：目标树自身拖拽，再拖拽之后进行提交保存。
   * 左右拖拽：addArr有值；上下拖拽：updateArr有值；移除：deleteArr有值；部分移动：addArr和updateArr有值
   * @param String[] addArr 新增结构数组  父件id,子件id;子件id1;子件id2,数量;数量1;数量2,单位;单位1;单位2
   * @param String[] updateArr 更新结构数组  linkid;父件id;数量,linkid1;父件id1;数量1,linkid2;父件id2;数量2
   * @param String[] deleteArr 删除结构数组  linkid,linkid1,linkid2
   * @param String carModelCode 车型码
   * @param String dwbs 工厂
   * 返回新增件json
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
   * 返回指定零部件及其一级子件节点集合
   * 用于添加零部件设计bom到源树，根据flag决定从临时表中获取还是获取最新结构。
   * @param id 零部件bsoID
   * @param flag 是否获取设计标签树
   * @param carModelCode 车型码
   * @param dwbs 单位
   * @return 零部件及其一级子件的树结构
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
//		System.out.println("over in helper getDesignBom 用时： "+(t2-t1)+" 毫秒");
		return s;
  }
  
  
  /**
   * 返回指定零部件的一级子件节点集合，用于显示设计BOM零部件列表。
   * @param id 零部件bsoID
   * @param flag 是否获取设计标签树
   * @param carModelCode 车型码
   * @param dwbs 单位
   * @return 零部件及其一级子件的树结构
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
   * 生成初始工艺BOM
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
//		System.out.println("over in helper initGYBom  用时： "+(t2-t1)+" 毫秒");
		return str;
	}
  
  /**
   * 返回指定零部件及其一级子件节点集合
   * 用于添加工艺bom到目标树。
   * @param id 零部件bsoID
   * @param carModelCode 车型码
   * @param dwbs 单位
   * @return 零部件及其一级子件的树结构
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
   * 返回指定零部件的一级子件节点集合，用于显示工艺BOM零部件列表。
   * @param id 零部件bsoID
   * @param carModelCode 车型码
   * @param dwbs 单位
   * @return 零部件及其一级子件的树结构
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
   * 用例（搜索设计bom）
   * 根据编号和名称搜索零部件
   * @param name 零部件名称
   * @param number1 零部件编号
   * @param ignoreCase 忽略大小写
   * @return 符合条件的零部件集合
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
   * 创建零部件
   * pid+partNumbe+partName+selectst+selectdw+selectlx+selectly+selectsmzq+selectzlj+code+dwbs
   * 父件号+编号+名称+视图+单位+类型+来源+生命周期+资料夹+车型码+工厂
   * @param String s
   * @return 当前件作为子件的节点
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
   * 加锁
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
   * 解锁
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
   * 检查是否被锁
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
   * 结构比较
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
//  	System.out.println("over in helper CompartTreeResult用时： "+(t2-t1)+" 毫秒");
  	return s;
  }
  
  
  /**
   * 另存为工艺BOM。
   * String yid 选中零部件。
   * String mid 目标零部件。
   * String dwbs 选中零部件工厂
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
//		System.out.println("over in helper saveAsGYBom  用时： "+(t2-t1)+" 毫秒");
		return str;
  }
  
  
  /**
   * 设置变更，为一个生效的工艺BOM克隆一套未生效BOM。
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
//		System.out.println("over in helper changeGYBom  用时： "+(t2-t1)+" 毫秒");
		return str;
  }
  
  
  /**
   * 保存批量部分替换车型表。
   * id 典型车型bsoid,车型码,工厂,替换车型1,替换车型2,替换车型3...
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
//		System.out.println("over in helper saveBatchUpdateCM  用时： "+(t2-t1)+" 毫秒");
		return str;
  }
  
  
  /**
   * 保存批量全部替换车型表。
   * id 车型id
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
//		System.out.println("over in helper saveAllBatchUpdateCM  用时： "+(t2-t1)+" 毫秒");
		return str;
  }
  
  
  /**
   * 获取批量替换车型表。
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
//		System.out.println("over in helper getBatchUpdateCM  用时： "+(t2-t1)+" 毫秒");
		return str;
  }
  
  
  /**
   * 降版本
   * 将工艺bom上节点版本降低一个版本，例如原来版本是“D”，选择降版本之后变为“C”版本
   * @parm String linkid 关联id（linkid）
   * @parm String id 选中节点bsoid（id）
   * @parm String carModelCode 车型码
   * @parm String dwbs 工厂 
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
//		System.out.println("over in helper upperVersion  用时： "+(t2-t1)+" 毫秒");
		return str;
  }
  
  
  /**
   * 拆分
   * 点击需要拆分的逻辑总成，在同级下自动生成一个逻辑总成号
   * 编号规则：原逻辑总成号+F+3位流水。
   * @parm String linkid 关联id（linkid）
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
//		System.out.println("over in helper chaiFenPart  用时： "+(t2-t1)+" 毫秒");
		return str;
  }
  
  
  /**
   * 整车一级件清单
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
//		System.out.println("over in helper getExportFirstLeveList  用时： "+(t2-t1)+" 毫秒");
		return vec;
  }
  
  
  /**
   * 添加备品
   * 将选中零部件子件添加到指定零部件下。
   * 返回
   * @parm String parentID 指定零部件
   * @parm String beipinID 选中零部件
   * @parm String carModelCode 车型码
   * @parm String dwbs 工厂 
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
//		System.out.println("over in helper addBeiPin  用时： "+(t2-t1)+" 毫秒");
		return str;
  }
  
  
  
  /**获得当前用户
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
   * 工艺bom历史数据导入方法
   * AddUsageLink	C01AM44141BF204	CQ1511065	ea	8
   * AddUsageLink	C01AM44141BF204	Q1841240F6	ea	8
   * 标记 父件 子件 单位 数量
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
   * 获取导入BOM列表清单
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
   * 删除总成
   * 右键添加菜单“删除总成”，此菜单功能是将总成删除掉，并把子件层级上升一级。
   * 返回
   * @parm String parentID 选中零部件的父件
   * @parm String partID 选中零部件
   * @parm String carModelCode 车型码
   * @parm String dwbs 工厂 
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
//		System.out.println("over in helper deleteSeparable  用时： "+(t2-t1)+" 毫秒");
		return str;
  }
  
  
  /**
   * 导出BOM 指定车型、工厂的BOM集合。
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
//		System.out.println("over in helper getExportBomList  用时： "+(t2-t1)+" 毫秒");
		return vec;
  }
  
  
  /**
   * 获取零部件父件在该工厂内的所有父件
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
//		System.out.println("over in helper getParentFromDwbs  用时： "+(t2-t1)+" 毫秒");
		return vec;
  }
  
  
  /**
    * 调试信息
    */
  private static final boolean VERBOSE = (RemoteProperty.getProperty("com.faw_qm.gybom.verbose","false")).equals("true");
  
  //CCBegin SS1
	/**
	 * 保存变更记录
	 * 
	 * @param cxh 车型号
	 * @param gc 工厂
	 * @param parentid 父件id
	 * @param subid 子件id
	 * @param quantityb 变更前数量
	 * @param quantitya 变更后数量
	 * @param sign 更改标识
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
   * 搜索变更单
   * @param changeNumber 变更单编号
   * @return 
   * @exception com.faw_qm.framework.exceptions.QMException
   */
  public static String searchChangeOrders(String changeNumber) throws QMException
    {
//          System.out.println("--------------变更单------------------");
        //变更单
        PersistService pService = (PersistService)EJBServiceHelper.getPersistService();

        QMQuery query = new QMQuery("Doc", "DocMaster");
        int k = query.appendBso("DocClassification", false);
        //QueryCondition con1 = new QueryCondition("technicsName",QueryCondition.LIKE,"%"+stepName+"%");
        query.addCondition(0, new QueryCondition("location", QueryCondition.LIKE, "%技术中心变更单%"));
        query.addAND();
        query.addCondition(1, new QueryCondition("docNum", QueryCondition.LIKE, "%" + changeNumber + "%"));
        query.addAND();
        query.addCondition(k, new QueryCondition("docCfName", QueryCondition.EQUAL, "技术中心变更单"));
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
//        System.out.println("集合大小===== " + result.size());
        Iterator ite = result.iterator();
        boolean flag = true;
        while(ite.hasNext())
        {
            DocIfc doc = (DocIfc)ite.next();
//            System.out.println("编号==== " + doc.getDocNum());
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
//        获取变更前后的件
    }
  
  /**
   * 获取变更单内容
   * @param changeID 变更单ID
   * @return 
   * @exception com.faw_qm.framework.exceptions.QMException
   */
    public static String getChangeContentByID(String changeID) throws QMException
    {
//          System.out.println("--------------获取变更单内容------------------");
        //变更单
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
            
            //获取变更前数据
            Collection colb = PublishHelper.getPartBeforechange(changeID);
            ArrayList arrb = new ArrayList(colb);
            for(int i=0; i<arrb.size(); i++)
            {
                String[] ss = (String[])arrb.get(i);
//                System.out.println("数组1===="+ss[0]);
//                System.out.println("数组2===="+ss[1]);
//                System.out.println("数组3===="+ss[2]);
//                System.out.println("数组4===="+ss[3]);
//                System.out.println("数组5===="+ss[4]);
                ChangeOrdersContentIfc old = new ChangeOrdersContentInfo();
                old.setOldVersion(ss[3]);
                old.setPartNumber(ss[1]);
                map.put(ss[1], old);
            }
            
            //获取变更后数据
            Collection cola = PublishHelper.getPartAfterchange(changeID);
            ArrayList arra = new ArrayList(cola);
            for(int i=0; i<arra.size(); i++)
            {
                String[] ss = (String[])arra.get(i);
//                System.out.println("获取变更后数据数组1===="+ss[0]);
//                System.out.println("获取变更后数据数组2===="+ss[1]);
//                System.out.println("获取变更后数据数组3===="+ss[2]);
//                System.out.println("获取变更后数据数组4===="+ss[3]);
//                System.out.println("获取变更后数据数组5===="+ss[4]);
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
        
//        System.out.println("集合大小===== " + result.size());
//        Iterator ite = result.iterator();
//        boolean flag = true;
//        while(ite.hasNext())
//        {
//            DocIfc doc = (DocIfc)ite.next();
//            System.out.println("编号==== " + doc.getDocNum());
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
//        获取变更前后的件
    }
  
    /**
     * 保存变更内容选择
     * @param content 
     * @return 
     * @exception com.faw_qm.framework.exceptions.QMException
     */
      public static String saveChangeOrders(String content) throws QMException
      {
//          System.out.println("===========保存变更内容选择=============="+content);
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
          
          //先删除内容在添加
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
       * 更新版本
       */
      public static String updateVersion(String linkid, String id, String carModelCode, String dwbs, String version) throws QMException
      {
//        System.out.println("come in updateVersion  更新版本" );
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
            QMPartIfc partIfc=null;//新版本零件
            
            QMQuery query = new QMQuery("QMPart");
            query.addCondition(new QueryCondition("masterBsoID", QueryCondition.EQUAL, part.getMasterBsoID()));
//            query.addAND();
//            query.addCondition(new QueryCondition("versionValue", QueryCondition.EQUAL, version.trim()));
//            System.out.println("零部件sql==========="+query.getDebugSQL());
            Collection result = ps.findValueInfo(query,false);
//            System.out.println("version==========="+version);
//            System.out.println("part.getMasterBsoID()==========="+part.getMasterBsoID());
//            System.out.println("大小====="+result.size());
            Iterator ite = result.iterator();
            while(ite.hasNext())
            {
                QMPartIfc value = (QMPartIfc)ite.next();
//                System.out.println("零部件==========="+value);
                
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
              //更新关联，子件id替换成上一版零部件id
                Connection conn = null;
                Statement stmt =null;
                ResultSet rs=null;
                try
                {
                    conn = PersistUtil.getConnection();
                    stmt = conn.createStatement();
                    stmt.executeQuery("update GYBomStructure set childPart='" + partIfc.getBsoID() + "' where effectCurrent='0' and childPart='" + part.getBsoID() + "' and dwbs='"+dwbs+"'");
                    
                    //关闭Statement
                    stmt.close();
                    //关闭数据库连接
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
	// 20170425 增加初始化工艺BOM前检查一级子件的生命周期状态
	/**
	 * 初始化工艺BOM前的检查 检查车型下的一级子件的生命周期状态是否为“设计完成”，将所有生命周期状态为“设计完成”的一级子件编号返回客户端
	 * 
	 * @param partID
	 *            车型的ID
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
			// 调用零部件服务获得车型的所有一级子件
			Collection coll=sp.getSubParts(part);
			String returnStr="";
			for(Iterator iter=coll.iterator();iter.hasNext();)
			{
				QMPartIfc subPart=(QMPartIfc)iter.next();
				String state=subPart.getLifeCycleState().toString();
				// 判断一级子件的生命周期状态是否为“设计完成”
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
	 * 生成整车初始工艺BOM
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
//		System.out.println("over in helper initGYBom  用时： "+(t2-t1)+" 毫秒");
		return str;
	}

	/**
	 * 生成车架初始工艺BOM
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
//		System.out.println("over in helper initGYBom  用时： "+(t2-t1)+" 毫秒");
		return str;
	}

	/**
	 * 生成驾驶室初始工艺BOM
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
//		System.out.println("over in helper initGYBom  用时： "+(t2-t1)+" 毫秒");
		return str;
	}
	
	/**
   * 升版本
   * 将工艺bom上节点版本提升一个版本，例如原来版本是“C”，选择升版本之后变为“D”版本
   * @parm String linkid 关联id（linkid）
   * @parm String id 选中节点bsoid（id）
   * @parm String carModelCode 车型码
   * @parm String dwbs 工厂 
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
//		System.out.println("over in helper upperVersion  用时： "+(t2-t1)+" 毫秒");
		return str;
  }
	//SS2 end
	
	
      //CCBegin SS4
      /**
       * 自动生成BOM发布单
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

                //采用集合
                ArrayList adoptList = new ArrayList();
                //不采用集合
                ArrayList partList = new ArrayList();
                ArrayList arr = new ArrayList();
                while(rs.next())
                {
//                    id   id
//                    车型号  carModelCode
//                    工厂   dwbs
//                    父件   parentPartID
//                    子件   subPartID
//                    修改前数量    quantity1
//                    修改后数量    quantity2
//                    增删改的标记（A,D,U）    sign
                    String id=rs.getString("id");
                    String carModelCode=rs.getString("carModelCode");
                    String dwbs=rs.getString("dwbs");
                    String parentPartID=rs.getString("parentPartID");
                    String subPartID=rs.getString("subPartID");
                    String quantity1=rs.getString("quantity1");
                    String quantity2=rs.getString("quantity2");
                    String sign=rs.getString("sign");
//                    System.out.println("父件   parentPartID==========="+parentPartID);
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
                    type = "整车工艺BOM采用单";
                }
                else if(publishType.equals("1"))
                {
                    type = "车架工艺BOM采用单";
                }
                else
                {
                    type = "驾驶室工艺BOM采用单";
                }
                notice.setPublishType(type);
                notice.setTopPart(carModelCodeStr);
                notice.setCreator(sservice.getCurUserID());
                notice.setOwner(sservice.getCurUserID());
                //自动编号
                String adoptnoticenumber = getBomAdoptNoticeNumber("ZC");
                notice.setAdoptnoticenumber(adoptnoticenumber);
                //CCBegin SS16
                notice.setAdoptnoticename(root.getPartNumber()+"的工艺BOM发布单");
                //CCEnd SS16
                
                String folderStr = "\\Root\\整车工艺BOM采用更改单";
                notice.setLocation(folderStr);
                fservice.assignFolder((FolderEntryIfc) notice, folderStr);
                
                //CCBegin SS11
//                lifeCycle.setLifeCycle(notice, lifeCycle.getLifeCycleTemplate("成都工艺BOM发布单生命周期"));
                //CCBegin SS19
                //String userComp=getUserFromCompany();
                String userComp=getCompany();
                //CCEnd SS19
                if(userComp.equals("cd")){
                	 lifeCycle.setLifeCycle(notice, lifeCycle.getLifeCycleTemplate("成都工艺BOM发布单生命周期"));
                }else{
                	 lifeCycle.setLifeCycle(notice, lifeCycle.getLifeCycleTemplate("工艺BOM发布单生命周期"));
                }
                //CCEnd SS11
                
                notice = (GYBomAdoptNoticeInfo)ps.saveValueInfo(notice);
                //采用集合
                for(int i=0; i<adoptList.size(); i++)
                {
                    BOMChangeContentBean bean = (BOMChangeContentBean)adoptList.get(i);
                    GYBomAdoptNoticePartLinkInfo link = new GYBomAdoptNoticePartLinkInfo();
                    System.out.println("bean.getSubPartID()==="+bean.getSubPartID());
                    QMPartInfo part = (QMPartInfo)ps.refreshInfo(bean.getSubPartID(), false);
                    link.setAdoptBs("采用");
                    link.setBz2(bean.getParentPartID());//父件
                    int size = arr.indexOf(bean.getParentPartID()+bean.getSubPartID());
                    link.setBz4(String.valueOf(size));//匹配标识，替换的时候替换件和被替换件匹配用，一张表单一对匹配件标识唯一
                    String bz3 = getBz3(carModelCodeStr ,bean.getSubPartID());
                    link.setBz3(bz3);//自组，在工艺BOM里的BZ2属性
                    link.setLifecyclestate(part.getLifeCycleState().toString());
                    link.setLinkPart(bean.getTSubPartID());//关联不采用件（如果是采用件这里表示替换那个不采用件）
                    link.setNoticeID(notice.getBsoID());
                    link.setPartID(bean.getSubPartID());
                    link.setPartName(part.getPartName());
                    link.setPartNumber(part.getPartNumber());
                    link.setPartView(part.getViewName());
                    link.setSl(bean.getQuantity2());//数量
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
                        link.setZplx(partStr[8]);//装配
                        link.setZzlx(partStr[7]);//制造
                    }
                    
                    ps.saveValueInfo(link, false);
                }
                //不采用集合
                for(int i=0; i<partList.size(); i++)
                {
                    BOMChangeContentBean bean = (BOMChangeContentBean)partList.get(i);
                    GYBomAdoptNoticePartLinkInfo link = new GYBomAdoptNoticePartLinkInfo();
                    QMPartInfo part = (QMPartInfo)ps.refreshInfo(bean.getSubPartID(), false);
                    link.setAdoptBs("不采用");
                    link.setBz2(bean.getParentPartID());//父件
                    int size = arr.indexOf(bean.getParentPartID()+bean.getSubPartID());
                    link.setBz4(String.valueOf(size));//匹配标识，替换的时候替换件和被替换件匹配用，一张表单一对匹配件标识唯一
                    String bz3 = getBz3(carModelCodeStr ,bean.getSubPartID());
                    link.setBz3(bz3);//自组，在工艺BOM里的BZ2属性
                    link.setLifecyclestate(part.getLifeCycleState().toString());
                    link.setLinkPart(bean.getTSubPartID());//关联不采用件（如果是采用件这里表示替换那个不采用件）
                    link.setNoticeID(notice.getBsoID());
                    link.setPartID(bean.getSubPartID());
                    link.setPartName(part.getPartName());
                    link.setPartNumber(part.getPartNumber());
                    link.setPartView(part.getViewName());
                    //CCBegin SS20
//                  link.setSl(bean.getQuantity2());//数量
                    link.setSl(bean.getQuantity1());//数量
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
                        link.setZplx(partStr[8]);//装配
                        link.setZzlx(partStr[7]);//制造
                    }
                    
                    ps.saveValueInfo(link, false);
                }
                ret = notice.getBsoID();
                // 清空并关闭sql返回数据
                rs.close();
                // 关闭Statement
                stmt.close();
                // 关闭数据库连接
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
         * 获得发布单编号（自动流水号）
         * @param noticeIfc BomAdoptNoticeIfc
         * @throws QMException
         * @return String
         */
        public static String getBomAdoptNoticeNumber(String type) throws QMException
        {
            String result = "";
            try
            {
                //构造年份最后两位
                String yearLast = new SimpleDateFormat("yy",Locale.CHINESE).format(Calendar.getInstance().getTime());
                String className = "GYBomAdoptNotice";
                String baseKey="LX-JF-"+yearLast+"-"+type;
                
                //取出来的1位序列号构造成3位
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
         * 采用单自动编号。获得序列号
         * @param String className 对象名称
         * @param String baseKey 序列号头
         * @param boolean changeFlag 是否变更
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
         * 采用单自动编号。获得序列号唯一性对象
         * @param String className 对象名称
         * @param String baseKey 序列号头
         * @param boolean changeFlag 是否变更
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
          //更新关联，子件id替换成上一版零部件id
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
                //关闭Statement
                stmt.close();
                //关闭数据库连接
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
	/**替换件
	 * @param linkid linkid
	 * @param yid 原节点id
	 * @param xid 替换节点id
	 * @param carModelCode 车型
	 * @param dwbs 工厂
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
	 * 获取当前用户所在单位简称。
	 * @return 单位名称
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
         * 搜索试制件
         * @param num 试制单编号 
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
                  
                  // 清空并关闭sql返回数据
                  rs.close();
                  // 关闭Statement
                  stmt.close();
                  // 关闭数据库连接
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
       * 创建试制件
       * @param partID 零件ID 
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
                
//                System.out.println("-----------------创建试制件-----------"+sql);
                // 关闭Statement
                st.close();
                // 关闭数据库连接
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
         * 创建试制件结构
         * @param partent 父件ID 
         * @param child 子件ID
         * @param quantity 数量
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
                  System.out.println("-----------------创建试制件结构-----------"+sql);
                  // 关闭Statement
                  st.addBatch(sql);
                  st.executeBatch();
                  
                  st.close();
                  // 关闭数据库连接
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
     * 获取试制件结构
     * @param partID
     * @return
     */
    public static Vector getShizhiStructure(String partID)
    {
//        System.out.println("-------------获取试制件结构-----------------------");
        Vector resultVector = new Vector();
        //更新关联，子件id替换成上一版零部件id
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
                  //因为零部件主信息在查看结构中不会再展开。
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
                      //因为零部件主信息在查看结构中不会再展开。
                      Collection collection1 = ss.getUsesPartIfcs(part, partConfigSpecIfc);

                      //是否有子件的标识，初始化为0，即没有子件。用获得子件的方法
                      //getUsesPartIfcs()，如果结果中含有子件，则设置为1。
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
                  //搜索得到的子件要设置是否展开，初始化设置为0，即不需要展开。
                  //因为在查看结构的界面中树节点是逐层展开，所以当前层都是不展开状态，
                  //只有点击展开后，才会改变状态。
                  normalPart1.isOpen = "0";
//System.out.println("234234234234234234234234234aaaaaaaaa=="+partNumber);
                  resultVector.addElement(normalPart1);
              }
              //关闭Statement
              stmt.close();
              //关闭数据库连接
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
  	/**替换件
  	 * @param linkid linkid
  	 * @param yid 原节点id
  	 * @param xid 替换节点id
  	 * @param carModelCode 车型
  	 * @param dwbs 工厂
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
     * 导出BOM
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
//  		System.out.println("over in helper getExportFirstLeveList  用时： "+(t2-t1)+" 毫秒");
  		return vec;
    }
    //CCEnd SS9
    //CCBegin SS10
    /**
     * 结构比较
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
     * 结构比较
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
     * @param yid 原节点id
     * @param xid 替换节点id
     * @param carModelCode 车型
     * @param dwbs 工厂
     * @param parentid 父件id
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
    /**加锁零件
     * @param partid 选中节点的零件id
     * @param dwbs 工厂
     * @return 是否成功
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
    /**解锁零件
     * @param partid 选中节点的零件id
     * @param dwbs 工厂
     * @return 是否成功
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
	 * 查询加锁用户是否为当前用户
	 * 
	 * @return false 没有当前用户加锁的对象,true 有当前用户加锁的对象
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
	 * 获取当前用户所在单位简称。
	 * @return 单位名称
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
	   * 校验工艺bom是否编辑路线并发布。
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
//			System.out.println("over in helper getExportBomList  用时： "+(t2-t1)+" 毫秒");
			return vec;
	  }//	CCEnd SS21
	  //	CCBegin SS22
	  /**
		 * 根据父件，子件和工厂，判断系统中是否存在该BOM
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
				// 关闭Statement
				stmt.close();
				// 关闭数据库连接
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
		 * 根据父件，子件和工厂，判断系统中是否存在该BOM
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
				// 关闭Statement
				stmt.close();
				// 关闭数据库连接
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
