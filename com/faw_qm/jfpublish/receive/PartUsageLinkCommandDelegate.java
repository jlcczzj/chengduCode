package com.faw_qm.jfpublish.receive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import com.faw_qm.adapter.BaseCommandDelegate;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.integration.model.Command;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Param;
import com.faw_qm.integration.model.Script;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.jfpublish.receive.PublishHelper;
import com.faw_qm.jfpublish.receive.PublishLoadHelper;
import com.faw_qm.jfpublish.receive.PublishPartsLog;
import com.faw_qm.jfpublish.receive.ResultReport;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserInfo;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartDescribeLinkInfo;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.pcfg.family.model.GenericPartInfo;
import com.faw_qm.pcfg.family.model.GenericPartUsageLinkInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.version.ejb.service.VersionControlService;
//CCBegin by liunan 2008-12-01
import com.faw_qm.part.model.PartAlternateLinkInfo;
import com.faw_qm.part.ejb.entity.PartUsageLink;
import com.faw_qm.part.model.PartSubstituteLinkInfo;

public class PartUsageLinkCommandDelegate extends BaseCommandDelegate
{
	public static Properties mapPro = null;
	
	protected static String MAPP_PATH;

	static
	{
		try
		{
			MAPP_PATH = QMProperties.getLocalProperties().getProperty(
	                "publish.mappingreceive.path");
			mapPro = new Properties();
			Properties old = new Properties();
			old.load(new FileInputStream(
      new File(MAPP_PATH)));																
			Enumeration e = old.keys();
			if (PublishHelper.VERBOSE)
				System.out.println("======>old pro amount: " + old.size());
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				if (PublishHelper.VERBOSE)
					System.out.println("key: "
							+ PublishHelper.strDecoding(key, "ISO8859-1")
							+ ",value: "
							+ PublishHelper.strDecoding(old.getProperty(key),
									"ISO8859-1"));
				mapPro.setProperty(PublishHelper.strDecoding(key, "ISO8859-1"),
						PublishHelper.strDecoding(old.getProperty(key),
								"ISO8859-1"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Script _invoke(Script script) throws QMException
	{
		SessionService sservice = null;
		UsersService userservice = null;
		UserInfo curUser = null;
		boolean administratorFlag = false;// 用来标识当前用户是否属于管理员组，如果不属于则需要暂时加入管理员组
		GroupInfo groupinfo = null;
		try {
			System.out.println("****** 进入  补发结构  线程");
			log("****** 进入  补发结构  线程");
			PublishHelper.setStateBusy();
			if (PublishHelper.VERBOSE)
				System.out.println("****** 进入业务处理线程" + "the script is :" + script != null);
			sservice = (SessionService) PublishHelper.getEJBService(
					"SessionService", "rdc", "wcsendjf");
			userservice = (UsersService) PublishHelper
					.getEJBService("UsersService");
			Command cmd = script.getCommand();
			String cmdName = cmd.getName();
			UserInfo user = userservice.getUserValueInfo("rdc");
			curUser = (UserInfo) sservice.getCurUserInfo();
			sservice.setAdministrator();
			groupinfo = (GroupInfo) userservice.getActorValueInfo(
					"Administrators", false);
			if (!userservice.isMember(curUser, groupinfo)) {
				userservice.groupAddUser(groupinfo, curUser);
			} else {
				administratorFlag = true;
			}
			sservice.freeAdministrator();
			PersistService ps = (PersistService) PublishHelper
					.getEJBService("PersistService");
			// 读传入输入组信息

			Group links = script.getGroupIn("PARTUSAGELINK");
			if (links != null && links.getElementCount() != 0)
			{
				System.out.println("****** 有 结构");
				log("****** 有 结构");
				savePartUsageLinks(links);
				System.out.println("****** 补发结构完成!");
				log("****** 补发结构完成!");
			}
			else
			{
				System.out.println("****** 没有 结构");
				log("****** 没有 结构");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			PublishHelper.setStateFree();
			try {
				sservice.setAdministrator();
				// 解除当前用户的管理员权限
				if (!administratorFlag) {
					userservice.groupDeleteUser(groupinfo, curUser);
				}
				sservice.freeAdministrator();
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Script response = new Script();
			return response;
		}
	}
	
	public void savePartUsageLinks(Group links) throws QMException
	{
		VersionControlService versiobSer = (VersionControlService) PublishHelper
				.getEJBService("VersionControlService");
		StandardPartService ser = (StandardPartService) PublishHelper
				.getEJBService("StandardPartService");

		Enumeration enumeration = links.getElements();
		Element ele = null;
		String parent = null;
		String parentVersion = null;
		String child = null;
		float quantity = 0.0f;
		String unit = null;
		String id = null;
		
		int count = 0;
		int ecount = 0;
		String pnum = "";
		while (enumeration.hasMoreElements())
		{
			ele = (Element) enumeration.nextElement();
			parent = ((String) ele.getValue("parent")).trim();
			parentVersion = ((String) ele.getValue("parentversion")).trim();
			child = ((String) ele.getValue("child")).trim();
			id = (String) ele.getValue("id");
			unit = (String) ele.getValue("unit");
			try
			{
				quantity = Float.valueOf((String) ele.getValue("quantity")).floatValue();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				quantity = 1.0F;
			}
			if(pnum.equals("")&&parent.endsWith("]"))
			{
				pnum = parent;
			}
			try
			{
				QMPartInfo parentPart = PublishHelper.getPartInfoByOrigInfo(parent, parentVersion);
				if (parentPart == null || parentPart.getViewName() == null)
				{
					log("补发结构：at save PartUsageLink 零部件(parent):" + parent + ",原版本：" + parentVersion + "is null ");
					ecount++;
					continue;
				}

				QMPartMasterInfo childMaster = null;
				QMPartIfc childPart = PublishHelper.getPartInfoByNumber(child);
				if (childPart == null)
				{
					log("补发结构：at save PartUsageLink 子零部件(child=" + child + ") is null ");
					ecount++;
					continue;
				}
				childMaster = (QMPartMasterInfo) childPart.getMaster();
				PersistService ps = (PersistService) PublishHelper.getEJBService("PersistService");
				Collection linkColl = ps.findLinkValueInfo("PartUsageLink", childMaster.getBsoID(), "uses", parentPart.getBsoID());
				if(linkColl != null && linkColl.size() > 0)
				{
					//log("补发结构：零部件 " + parent + " （父）与零部件 " + child + " （子）的结构关联已存在");
					continue;
				}
				PartUsageLinkInfo usageLink = null;
				if (id != null)
				{
					usageLink = new GenericPartUsageLinkInfo();
					((GenericPartUsageLinkInfo) usageLink).setId(id);
				}
				else
				{
					usageLink = new PartUsageLinkInfo();
				}
				usageLink.setLeftBsoID(childMaster.getBsoID());
				usageLink.setRightBsoID(parentPart.getBsoID());
				QMQuantity quan = new QMQuantity();
				quan.setDefaultUnit(Unit.toUnit(unit) != null ? Unit.toUnit(unit) : Unit.getUnitDefault());
				quan.setQuantity(quantity);
				usageLink.setQMQuantity(quan);
				usageLink.setQuantity(quantity);
				// 设置默认单位(使用方式)
				if (unit == null || unit.trim().length() == 0)
				{
					usageLink.setDefaultUnit(Unit.getUnitDefault());
				}
				else
				{
					// 读取属性文件，获得默认单位（使用方式）对应值
					unit = PublishLoadHelper.mapPro.getProperty("part.unit." + unit, null);
					if (unit == null)
					{
						unit = PublishLoadHelper.mapPro.getProperty("part.unit.default", null);
					}
					Unit unitT = Unit.toUnit(unit);
					if (unitT == null)
					{
						usageLink.setDefaultUnit(Unit.getUnitDefault());
					}
					else
					{
						usageLink.setDefaultUnit(unitT);
					}
				}
				ps.saveValueInfo(usageLink);
				count++;
				log("补发结构：创建零部件 " + parent + " （父）与零部件 " + child + " （子）的结构关联成功");
			}
			catch (QMException ex)
			{
				String msg = "补发结构：at save PartUsageLink (parent=" + parent
						+ " parentversion=" + parentVersion + " child=" + child
						+ ") ERROR:" + ex.getClientMessage();
				log(msg);
				ecount++;
				continue;
			}
			catch (Exception ex)
			{
				String msg = "补发结构：at save PartUsageLink (parent=" + parent
						+ " parentversion=" + parentVersion + " child=" + child
						+ ") ERROR:" + ex.getLocalizedMessage();
				log(msg);
				ecount++;
				continue;
			}
		}
		System.out.println("补发结构 整车为："+pnum);
		System.out.println("补发结构共 新建了结构："+count+"条");
		System.out.println("补发结构共 出错结构："+ecount+"条");
		log("补发结构 整车为："+pnum);
		log("补发结构共 新建了结构："+count+"条");
		log("补发结构共 出错结构："+ecount+"条");
	}

	protected void log(Object msg) {
		PublishPartsLog.log(msg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
