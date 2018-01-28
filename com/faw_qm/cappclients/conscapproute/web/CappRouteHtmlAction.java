/**
 * <p>Title:艺毕通知书处理类 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 屈晓光
 * @version 1.0
 */

package com.faw_qm.cappclients.conscapproute.web;

import com.faw_qm.framework.controller.web.action.HTMLAction;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.controller.web.action.HTMLActionException;
import com.faw_qm.content.util.StreamRequestHandler;

import java.text.Collator;
import java.util.HashMap;
import com.faw_qm.content.exception.ContentException;
import java.util.Set;
import java.util.Iterator;
import com.faw_qm.content.model.FileInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.content.model.ApplicationDataInfo;
//CCBegin by wanghonglian 2008-07-30
//注释掉不使用的导入路径
//import com.faw_qm.framework.exceptions.AppException;
//CCEnd by wanghonglian 2008-07-30
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.session.ejb.service.SessionService;
import java.util.ArrayList;
import java.util.Collection;
import com.faw_qm.technics.consroute.model.RoutelistCompletInfo;
import com.faw_qm.content.ejb.service.ContentService;
import java.sql.Timestamp;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleTemplateIfc;
import com.faw_qm.technics.consroute.util.RouteCompletType;
import com.faw_qm.technics.consroute.model.CompletPartLinkInfo;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.content.model.HolderAndContentInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.wip.util.WorkInProgressState;
import com.faw_qm.content.util.StreamUtil;
import com.faw_qm.technics.consroute.model.CompletListLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.part.ejb.standardService.StandardPartService;
//CCBegin by wanghonglian 2008-07-30
//注释掉不使用的导入路径
//import com.faw_qm.part.exception.PartException;
//CCEnd by wanghonglian 2008-07-30
import com.faw_qm.technics.consroute.model.CompletedPartsInfo;
import java.util.Vector;
import com.faw_qm.users.model.ActorIfc;
import com.faw_qm.version.ejb.service.VersionControlService;
//CCBegin by wanghonglian 2008-07-30
//注释掉不使用的导入路径
//import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
//import com.faw_qm.lifecycle.util.LifeCycleServiceHandler;
//import com.faw_qm.lifecycle.util.State;
//CCEnd by wanghonglian 2008-07-30
import com.faw_qm.technics.consroute.model.*;

import com.faw_qm.part.model.*;
import com.faw_qm.technics.consroute.ejb.service.*;
import com.faw_qm.technics.consroute.util.*;
import java.util.*;

public class CappRouteHtmlAction implements HTMLAction {

	private HashMap hashMap = null;

	public static String MULTIPART = "multipart/form-data";

	private String actionType;

	private boolean hasFix = false;

	public CappRouteHtmlAction() {
	}

	public void setServletContext(ServletContext servletContext) {
	}

	public void doStart(HttpServletRequest httpServletRequest) {
	}

	public Event perform(HttpServletRequest request) throws HTMLActionException {
		getActionType(request);
		if (actionType != null && actionType.equals("creat1")) {
			performCreateListAndFixProcess(request);
			return null;
		}

		if (actionType != null && actionType.equals("checkPartProcess")) {
			performAddPartProcess(request);
			return null;
		}
		if (actionType != null && actionType.equals("checkRouteList")) {
			performAddRouteListProcess(request);
			return null;
		}
		if (actionType != null && actionType.equals("create2")) {
			performFinalCreateProcess(request);
			return null;
		}

		return null;

	}

	private void performCreateListAndFixProcess(HttpServletRequest request) {
		try {
			RoutelistCompletInfo completeListInfo = saveCompleteListProcess(request);
			if (completeListInfo == null) {
				return;
			}
			request.setAttribute("bsoId", completeListInfo.getBsoID());
			saveFixProcess(completeListInfo, request);
		} catch (QMException ex) {
			request.setAttribute("error", ex.getClientMessage());
		}
	}

	private void checkFixSize(HttpServletRequest request)
			throws HTMLActionException {
		int totalByte = request.getContentLength();
		String allowMaxStr = RemoteProperty.getProperty("allowMaxHTTPPostByte",
				"31457280");
		long allowMaxHTTPPostByte = 31457280L; // 30M
		try {
			allowMaxHTTPPostByte = Long.parseLong(allowMaxStr);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (totalByte > allowMaxHTTPPostByte) {
			String errorInfo = roundToTwoDecimals(allowMaxHTTPPostByte
					/ (1024F * 1024F))
					+ "M";
			throw new HTMLActionException("配置中允许提交的最大字节为 " + errorInfo
					+ "，目前您提交的字节为 " + totalByte + " bytes，请您减少附件的大小");
		}

	}

	public void doEnd(HttpServletRequest httpServletRequest,
			EventResponse eventResponse) {

	}

	private static float roundToTwoDecimals(float f) {
		int i = (int) (f * 1000F);
		int j = i + 5;
		i = (j / 10) * 10;
		return (float) i / 1000F;
	}

	private RoutelistCompletInfo saveCompleteListProcess(
			HttpServletRequest request) throws QMException {

		checkFixSize(request);
		String routeFinishPosterNum = ((String) hashMap
				.get("textRouterFinishNum")).toUpperCase();
		String routeFinishPosterName = (String) hashMap
				.get("textRoutrFinishName");
		String routeFinishType = (String) hashMap.get("selectRouterFinishType");
		String routeFinishFolder = (String) hashMap.get("selectfolder");
		String routeFinishDesc = (String) hashMap.get("routeFinishDesc");
		// String lifeCircleID = (String) hashMap.get("selectlifeCName");
		PersistService pservice1 = (PersistService) EJBServiceHelper
				.getPersistService();
		SessionService service = (SessionService) EJBServiceHelper
				.getService("SessionService");
		RoutelistCompletInfo routeListInfo = new RoutelistCompletInfo();
		routeListInfo.setCompletNum(routeFinishPosterNum);
		routeListInfo.setCompletName(routeFinishPosterName);
		routeListInfo.setCompletNote(routeFinishDesc);
		routeListInfo.setCompletType(routeFinishType);
		routeListInfo.setDate(new Timestamp(System.currentTimeMillis()));
		routeListInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		routeListInfo.setLocation(routeFinishFolder);
		String userID = service.getCurUserID();
		routeListInfo.setCreator(userID);
		com.faw_qm.lock.util.LockHelper.assignLock(routeListInfo, userID, null);
		FolderService fservice = (FolderService) EJBServiceHelper
				.getService("FolderService");
		fservice.assignFolder(routeListInfo, routeListInfo.getLocation());
		// LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.
		// getService("LifeCycleService");
		// lservice.setLifeCycle(routeListInfo,
		// (LifeCycleTemplateIfc)
		// pservice1.refreshInfo(lifeCircleID));
		assignRouteCompleteLifeCycle(routeListInfo);
		try{
		RoutelistCompletInfo routeCompleteInfo = (RoutelistCompletInfo) pservice1.saveValueInfo(routeListInfo);
		return routeCompleteInfo ;
		}
		catch(QMException e)
		{
			e.printStackTrace();
			return null ;
		}

		//return (RoutelistCompletInfo) pservice1.saveValueInfo(routeListInfo);

	}

	private void saveFixProcess(RoutelistCompletInfo completeListInfo,
			HttpServletRequest request) throws QMException {
		handleUploadFileMessages(completeListInfo, request);
	}

	private void performFinalCreateProcess(HttpServletRequest request) {
		try {
			String routeCompleteBsoID;
			PersistService service = (PersistService) EJBServiceHelper
					.getPersistService();
			routeCompleteBsoID = (String) hashMap.get("routeListCompleteBsoID");
			RoutelistCompletInfo completeInfo = (RoutelistCompletInfo) service
					.refreshInfo(routeCompleteBsoID);
			request.setAttribute("bsoId", routeCompleteBsoID);
			String completeNote = (String) hashMap.get("routeFinishDesc");
			if (completeNote != null) {
				completeInfo.setCompletNote(completeNote);
				service.updateValueInfo(completeInfo);
			}

			Collection checkedFixIdList = new ArrayList();
			if (hashMap.get("checkFixProcess") instanceof String) {
				String checkedFixId = (String) hashMap.get("checkFixProcess");
				checkedFixIdList.add(checkedFixId);
			} else {
				checkedFixIdList = (Collection) hashMap.get("checkFixProcess");
			}
			ArrayList savedFixIDs = getRouteCompleteFixByBsoID(routeCompleteBsoID);
			for (int i = 0; i < savedFixIDs.size(); i++) {
				ApplicationDataInfo appdataInfo = (ApplicationDataInfo) savedFixIDs
						.get(i);
				String fixId = appdataInfo.getBsoID();
				if (checkedFixIdList == null) {
					service.deleteValueInfo(appdataInfo);
				}
				if (checkedFixIdList != null
						&& !checkedFixIdList.contains(fixId)) {
					service.deleteValueInfo(appdataInfo);
				}
			}
			if (hasFix) {
				handleUploadFileMessages(completeInfo, request);
			}
			if (completeInfo.getCompletType().equals("零件")) {
				performFinalSavePart(completeInfo, request);
			}
			if (completeInfo.getCompletType().equals("艺准")) {
				performFinalSaveRouteList(completeInfo, request);
			}

		} catch (QMException ex) {
			ex.printStackTrace();
			request.setAttribute("error", ex.getClientMessage());
		}

	}

	private void performFinalSavePart(RoutelistCompletInfo completeInfo,
			HttpServletRequest request) throws QMException {

		PersistService service = (PersistService) EJBServiceHelper
				.getPersistService();
		Collection checkedPartIdList = new ArrayList();
		if (hashMap.get("checkedPart") instanceof String) {
			String checkedPartID = (String) hashMap.get("checkedPart");
			checkedPartIdList.add(checkedPartID);
		} else {
			checkedPartIdList = (Collection) hashMap.get("checkedPart");
		}
		ArrayList savedPartinfo = getRouteCompleteLinkedPartByBsoID(completeInfo
				.getBsoID());
		if (checkedPartIdList == null || checkedPartIdList.size() < 1) {
			QMQuery query11 = new QMQuery("CompletPartLink");
			query11.addCondition(new QueryCondition("leftBsoID", "=",
					completeInfo.getBsoID()));
			Collection collf = service.findValueInfo(query11);
			for (Iterator iterator = collf.iterator(); iterator.hasNext();) {
				CompletPartLinkInfo comPartLink = (CompletPartLinkInfo) iterator
						.next();
				service.deleteValueInfo(comPartLink);
			}
			deleteAllLinkedParts(completeInfo);
		} else {
			for (int i = 0; i < savedPartinfo.size(); i++) {
				QMPartInfo part = (QMPartInfo) savedPartinfo.get(i);
				if (!checkedPartIdList.contains(part.getBsoID())) {
					QMQuery query = new QMQuery("CompletPartLink");
					query.addCondition(new QueryCondition("leftBsoID", "=",
							completeInfo.getBsoID()));
					query.addAND();
					query.addCondition(new QueryCondition("rightBsoID", "=",
							part.getBsoID()));
					Collection coll = service.findValueInfo(query);
					if (coll.size() != 0) {
						Iterator iter = coll.iterator();
						CompletPartLinkInfo comPartLink = (CompletPartLinkInfo) iter
								.next();
						service.deleteValueInfo(comPartLink);
					}
					// 根据解放业务，父件为总成类型，不抱毕父件。也要报毕子件
					/*if (!part.getPartName().equals("逻辑总成")
							|| !part.getPartName().equals("装置件")) {
						deleteSubpartsbypart(completeInfo, part);
					}*/
				}
			}
		}
		Collection checkedPartsColl = new Vector();
		if (hashMap.get("checkPartsProcess") instanceof String) {
			String checkedPartsID = (String) hashMap.get("checkPartsProcess");
			checkedPartsColl.add(checkedPartsID);
		} else {
			checkedPartsColl = (Collection) hashMap.get("checkPartsProcess");
		}
		if (checkedPartsColl == null || checkedPartsColl.size() < 1) {
			return;
		}
		for (Iterator iterator = checkedPartsColl.iterator(); iterator
				.hasNext();) {
			String partsId = (String) iterator.next();
			Collection partsColl = getCompletePartsLink(
					completeInfo.getBsoID(), partsId);
			if (partsColl.size() != 0) {
				Iterator iter = partsColl.iterator();
				CompletedPartsInfo comPartsInfo = (CompletedPartsInfo) iter
						.next();
				service.deleteValueInfo(comPartsInfo);
			}

		}

	}

	private void deleteAllLinkedParts(RoutelistCompletInfo completeInfo)
			throws QMException {

		PersistService service = (PersistService) EJBServiceHelper
				.getPersistService();
		QMQuery query11 = new QMQuery("CompletedParts");
		query11.addCondition(new QueryCondition("leftBsoID", "=", completeInfo
				.getBsoID()));
		Collection collf = service.findValueInfo(query11);
		for (Iterator iterator = collf.iterator(); iterator.hasNext();) {
			CompletedPartsInfo comPartsLink = (CompletedPartsInfo) iterator
					.next();
			service.deleteValueInfo(comPartsLink);
		}

	}

	private void deleteSubpartsbypart(RoutelistCompletInfo completeInfo,
			QMPartInfo partInfo) throws QMException {

		PersistService pservice1 = (PersistService) EJBServiceHelper
				.getPersistService();
		StandardPartService partService = (StandardPartService) EJBServiceHelper
				.getService("StandardPartService");
		Collection subPartColl = partService.getAllSubParts(partInfo);
		Collection subPartIDColl = new Vector();
		if (subPartColl != null) {
			for (Iterator iterator = subPartColl.iterator(); iterator.hasNext();) {
				subPartIDColl.add(((QMPartInfo) iterator.next()).getBsoID());
			}
		}
		QMQuery query = new QMQuery("CompletedParts");
		query.addCondition(new QueryCondition("leftBsoID", "=", completeInfo
				.getBsoID()));
		Collection subPartsColl = pservice1.findValueInfo(query);
		for (Iterator iterator = subPartsColl.iterator(); iterator.hasNext();) {
			CompletedPartsInfo partsInfo = (CompletedPartsInfo) iterator.next();
			if (subPartIDColl.contains(partsInfo.getRightBsoID())) {
				System.out.println("准备删除子件 。。。。");
				if (!checkIfOtherPartContainThisSubPart(
						completeInfo.getBsoID(), partInfo.getBsoID(), partsInfo
								.getRightBsoID())) {
					pservice1.deleteValueInfo(partsInfo);
				}
			}
		}
	}

	private boolean checkIfOtherPartContainThisSubPart(String CompleteID,
			String parentPartID, String partID) {
		try {
			StandardPartService partService = (StandardPartService) EJBServiceHelper
					.getService("StandardPartService");
			Collection partColl = getRouteCompleteLinkedPartByBsoID(CompleteID);
			for (Iterator iter1 = partColl.iterator(); iter1.hasNext();) {
				QMPartInfo part = (QMPartInfo) iter1.next();
				if (!part.getBsoID().equals(parentPartID)) {
					Collection subPartColl = partService.getAllSubParts(part);
					for (Iterator iter2 = subPartColl.iterator(); iter2
							.hasNext();) {
						QMPartInfo part1 = (QMPartInfo) iter2.next();
						if (part1.getBsoID().equals(partID)) {
							return true;
						}
					}
				}
			}

		} catch (QMException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	private void performFinalSaveRouteList(RoutelistCompletInfo completeInfo,
			HttpServletRequest request) throws QMException {
		PersistService service = (PersistService) EJBServiceHelper
				.getPersistService();
		Collection checkedRouteIdList = new ArrayList();
		if (hashMap.get("checkedRouteList") instanceof String) {
			String checkedRouteListID = (String) hashMap
					.get("checkedRouteList");
			checkedRouteIdList.add(checkedRouteListID);
		} else {
			checkedRouteIdList = (Collection) hashMap.get("checkedRouteList");
		}

		ArrayList savedRouteList = getCompleteLinkedListByBsoID(completeInfo
				.getBsoID());
		for (int i = 0; i < savedRouteList.size(); i++) {
			TechnicsRouteListInfo routeList = (TechnicsRouteListInfo) savedRouteList
					.get(i);
			if (checkedRouteIdList == null) {
				QMQuery query = new QMQuery("CompletListLink");
				query.addCondition(new QueryCondition("leftBsoID", "=",
						completeInfo.getBsoID()));
				query.addAND();
				query.addCondition(new QueryCondition("rightBsoID", "=",
						routeList.getBsoID()));
				Collection coll = service.findValueInfo(query);
				if (coll.size() != 0) {
					Iterator iter = coll.iterator();
					CompletListLinkInfo comListLink = (CompletListLinkInfo) iter
							.next();
					service.deleteValueInfo(comListLink);
				}

			}
			if (checkedRouteIdList != null
					&& !checkedRouteIdList.contains(routeList.getBsoID())) {
				QMQuery query = new QMQuery("CompletListLink");
				query.addCondition(new QueryCondition("leftBsoID", "=",
						completeInfo.getBsoID()));
				query.addAND();
				query.addCondition(new QueryCondition("rightBsoID", "=",
						routeList.getBsoID()));
				Collection coll = service.findValueInfo(query);
				if (coll.size() != 0) {
					Iterator iter = coll.iterator();
					CompletListLinkInfo comListLink = (CompletListLinkInfo) iter
							.next();
					service.deleteValueInfo(comListLink);
				}

			}
		}

	}

	private void performAddPartProcess(HttpServletRequest request) {
		try {
			String routeCompleteBsoID = request
					.getParameter("routeCompleteBsoID");
			request.setAttribute("bsoId", routeCompleteBsoID);
			String[] partIDs = request.getParameterValues("checkProcess");
			if (partIDs != null) {
				for (int i = 0; i < partIDs.length; i++) {
					String partID = partIDs[i];
					CompletPartLinkInfo completePartInfo = new CompletPartLinkInfo();
					completePartInfo.setLeftBsoID(routeCompleteBsoID);
					completePartInfo.setRightBsoID(partID);

					PersistService service = (PersistService) EJBServiceHelper
							.getPersistService();
					service.saveValueInfo(completePartInfo);
					addSubPartsProcess(routeCompleteBsoID, partID);
				}
			}
		} catch (QMException ex) {
			ex.printStackTrace();
			request.setAttribute("error", ex.getClientMessage());
		}

	}

	private boolean isHasContainThisPart(String routeCompleteBsoID,
			String partID) {
		if (getCompletePartsLink(routeCompleteBsoID, partID).size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	private Collection getCompletePartsLink(String routeCompleteBsoID,
			String partID) {
		try {
			PersistService service = (PersistService) EJBServiceHelper
					.getPersistService();
			QMQuery query = new QMQuery("CompletedParts");
			query.addCondition(new QueryCondition("leftBsoID", "=",
					routeCompleteBsoID));
			query.addAND();
			query.addCondition(new QueryCondition("rightBsoID", "=", partID));
			return service.findValueInfo(query);
		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private void addSubPartsProcess(String routeCompleteBsoID,
			String parentpartID) throws QMException {
		PersistService service = (PersistService) EJBServiceHelper
				.getPersistService();
		QMPartInfo partInfo = (QMPartInfo) service.refreshInfo(parentpartID);
		Collection subPartColl = getAllSubParts(partInfo);
		for (Iterator iterator = subPartColl.iterator(); iterator.hasNext();) {
			QMPartInfo subPartInfo = (QMPartInfo) iterator.next();
			if (isHasContainThisPart(routeCompleteBsoID, subPartInfo.getBsoID())) {
				continue;
			}
			if (subPartInfo.getBsoID().equals(parentpartID)
					/*|| subPartInfo.getPartName().equals("逻辑总成")
					|| subPartInfo.getPartName().equals("装置件")*/) {
				continue;
			}
			CompletedPartsInfo comPartsLinkInfo = new CompletedPartsInfo();
			comPartsLinkInfo.setLeftBsoID(routeCompleteBsoID);
			comPartsLinkInfo.setRightBsoID(subPartInfo.getBsoID());
			service.saveValueInfo(comPartsLinkInfo);
		}
	}

	/**
	 * yanqi-20080128添加，part服务原来的方法不能处理GPart 根据指定零部件返回所有下级零部件的最新版本的值对象集合。
	 *
	 * @param partIfc
	 *            QMPartIfc 零部件的值对象；
	 * @return collection Collection partIfc使用的下级子件的最新版本的值对象集合。
	 * @throws QMException
	 */
	public Collection getAllSubParts(QMPartIfc partIfc) throws QMException {
		Collection result = new Vector();
		Collection filterIndex = new ArrayList();
		if (partIfc != null) {
			result.add(partIfc);
			filterIndex.add(partIfc.getBsoID());
			getAllSubParts(partIfc, result, filterIndex);
		}
		return result;
	}

	/**
	 * yanqi-20080128添加，part服务原来的方法不能处理GPart 根据指定零部件返回所有下级零部件的最新版本的值对象集合。
	 *
	 * @param partIfc
	 *            QMPartIfc 零部件的值对象；
	 * @param result
	 *            Collection 保存结果
	 * @param indexID
	 *            Collection 保存ID
	 * @throws QMException
	 */
	private void getAllSubParts(QMPartIfc partIfc, Collection result,
			Collection indexID) throws QMException {
		Collection col = getSubParts(partIfc);
		Iterator iter = col.iterator();
		while (iter.hasNext()) {
			QMPartIfc part = (QMPartIfc) iter.next();
			String bsoID = part.getBsoID();
			if (!indexID.contains(bsoID)) {
				result.add(part);
				indexID.add(bsoID);
				getAllSubParts(part, result, indexID);
			}
		}
	}

	/**
	 * yanqi-20080128添加，part服务原来的方法不能处理GPart 根据指定的零部件返回下级零部件的最新版本的值对象的集合。
	 *
	 * @param partIfc
	 *            QMPartIfc 零部件的值对象；
	 * @return collection partIfc使用的下级子件的最新版本的值对象集合。
	 * @throws QMException
	 */
	public Collection getSubParts(QMPartIfc partIfc) throws QMException {
		// 如果条件成立，抛出PartEception异常"参数不能为空"
		if (partIfc == null) {
			return new Vector();
		}
		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();
		Collection collection = new Vector();
		if (partIfc.getBsoName().equals("GenericPart")) {
			collection = pservice.navigateValueInfo(partIfc, "usedBy",
					"GenericPartUsageLink");
		} else if (partIfc.getBsoName().equals("QMPart")) {
			collection = pservice.navigateValueInfo(partIfc, "usedBy",
					"PartUsageLink");
		}
		Object[] tempArray = (Object[]) collection.toArray();
		VersionControlService vcservice = (VersionControlService) EJBServiceHelper
				.getService("VersionControlService");
		Vector result = new Vector();
		Vector tempResult = new Vector();
		for (int i = 0; i < tempArray.length; i++) {
			tempResult = new Vector(vcservice
					.allVersionsOf((QMPartMasterIfc) tempArray[i]));
			if (tempResult != null && (tempResult.iterator()).hasNext()) {
				result.addElement((tempResult.iterator()).next());
			}
		}
		return result;
	}

	private void performAddRouteListProcess(HttpServletRequest request) {
		String routeCompleteBsoID = request
				.getParameter("routeListCompleteBsoID");
		request.setAttribute("bsoId", routeCompleteBsoID);
		String[] routeListIDs = request.getParameterValues("checkProcess");
		try {
			if (routeListIDs != null) {
				for (int i = 0; i < routeListIDs.length; i++) {
					String routeListID = routeListIDs[i];
					CompletListLinkInfo completelistLinkInfo = new CompletListLinkInfo();
					completelistLinkInfo.setLeftBsoID(routeCompleteBsoID);
					completelistLinkInfo.setRightBsoID(routeListID);
					PersistService service = (PersistService) EJBServiceHelper
							.getPersistService();
					service.saveValueInfo(completelistLinkInfo);
				}
			}
		} catch (QMException ex) {
			request.setAttribute("error", ex.getClientMessage());
			ex.printStackTrace();
		}
	}

	public void handleUploadFileMessages(RoutelistCompletInfo completeListInfo,
			HttpServletRequest request) throws QMException {
		ContentService contentService = (ContentService) EJBServiceHelper
				.getService("ContentService");
		if (hashMap == null) {
			StreamRequestHandler handler = new StreamRequestHandler(request);
			try {
				hashMap = handler.handle();
			} catch (ContentException ex) {
				ex.printStackTrace();
			}
		}
		Set set = hashMap.keySet();
		Object file[];
		byte[] fileContent = null;
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object obj = hashMap.get(key);

			if (obj instanceof Object[]) {
				if (!(obj instanceof String[])) {
					file = (Object[]) obj;
					FileInfo fileDescript = (FileInfo) file[0];
					if (fileDescript != null) {
						if (fileDescript.getFileSize() > 0
								&& fileDescript.getFileName() != null
								&& !fileDescript.getFileName().equals("")) {
							ApplicationDataInfo appDataInfo = new ApplicationDataInfo();
							appDataInfo.setFileName(fileDescript.getFileName());
							appDataInfo.setUploadPath(fileDescript
									.getFilePath());
							appDataInfo.setFileSize(fileDescript.getFileSize());
							SessionService session = (SessionService) EJBServiceHelper
									.getService("SessionService");
							appDataInfo.setCreateUserID(session.getCurUserID());

							contentService.uploadContent(completeListInfo,
									appDataInfo);
							String streamID = appDataInfo.getStreamDataID();
							fileContent = (byte[]) file[1];
							if (fileContent != null) {
								try {
									StreamUtil.writeData(streamID, fileContent);
								} catch (QMException e) {
									e.printStackTrace();
									throw e;
								}
							}

						}
					}
				}
			}
		}
	}

	private void getActionType(HttpServletRequest request) {
		String contentType = request.getContentType();
		if (contentType != null && contentType.indexOf(MULTIPART) != -1) {
			hasFix = true;
			StreamRequestHandler handler = new StreamRequestHandler(request);
			try {

				hashMap = handler.handle();
				actionType = (String) hashMap.get("actionType");
			} catch (ContentException ex) {
				ex.printStackTrace();
			}

		} else {
			actionType = (String) request.getParameter("actionType");
		}
	}

	public FolderIfc getPersonFolder() throws QMException {

		FolderService fS = (FolderService) EJBServiceHelper
				.getService("FolderService");
		SessionService ss = (SessionService) EJBServiceHelper
				.getService("SessionService");
		UserIfc usr = ss.getCurUserInfo();
		FolderIfc folder = fS.getPersonalFolder((UserInfo) usr);
		return folder;
	}

	public RouteCompletType[] getAllTypes() {
		RouteCompletType[] hehe = RouteCompletType.getRouteCompletTypeSet();
		return hehe;
	}

	public RoutelistCompletInfo getRouteCompleteInfoByBsoID(
			String routeCompleteBsoID) {
		try {
			PersistService service = (PersistService) EJBServiceHelper
					.getPersistService();
			return (RoutelistCompletInfo) service
					.refreshInfo(routeCompleteBsoID);
		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public ArrayList getRouteCompleteFixByBsoID(String routeCompleteBsoID)
			throws QMException {
		ArrayList fixList = new ArrayList();
		PersistService service = (PersistService) EJBServiceHelper
				.getPersistService();
		QMQuery query = new QMQuery("HolderAndContent");
		query.addCondition(new QueryCondition("leftBsoID", "=",
				routeCompleteBsoID));
		Collection coll = service.findValueInfo(query);
		for (Iterator iterator = coll.iterator(); iterator.hasNext();) {
			HolderAndContentInfo holderAndContent = (HolderAndContentInfo) iterator
					.next();
			String contentID = holderAndContent.getRightBsoID();
			ApplicationDataInfo appDataInfo = (ApplicationDataInfo) service
					.refreshInfo(contentID);
			fixList.add(appDataInfo);
		}
		return fixList;

	}
    /**
     * yanqi-20080128 返回按编号排序的零部件集合
     * @param routeCompleteBsoID
     * @return
     */
	public ArrayList getRouteCompleteLinkedPartByBsoID(String routeCompleteBsoID) {
		try {
			Collection coll=RouteHelper.getRouteService()
					.getRouteCompleteLinkedPartByBsoID(routeCompleteBsoID);
			return getOrderedPartsColl(coll);

		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String getPartDisplsy(String partID) {
		String partState = null;
		try {
			PersistService service = (PersistService) EJBServiceHelper
					.getPersistService();
			QMPartInfo partInfo = (QMPartInfo) service.refreshInfo(partID);
			String tempWorkableState = partInfo.getWorkableState();
			if (tempWorkableState != null && tempWorkableState.length() > 0) {
				WorkInProgressState wState = WorkInProgressState
						.toWorkInProgressState(tempWorkableState);
				partState = wState.getDisplay();
			}
			return partState;
		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public ArrayList getCompleteLinkedListByBsoID(String routeCompleteBsoID) {
		try {
			return RouteHelper.getRouteService().getCompleteLinkedListByBsoID(
					routeCompleteBsoID);
		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public Collection getRouteList(String listNum, String listName) {
		if ((listNum == null || listNum.length() < 1)
				&& (listName == null || listName.length() < 1)) {
			return getAllRouteList();
		} else {
			if ((listNum == null || listNum.length() < 1)) {
				return getRouteListByName(listName);
			}
			if (listName == null || listName.length() < 1) {
				return getRouteListByNum(listNum);
			}
			return getRouteListByNumAndName(listNum, listName);
		}
	}

	private Collection getAllRouteList() {
		Collection coll = null;
		try {
			QMQuery query = new QMQuery("TechnicsRouteList");
			PersistService service = (PersistService) EJBServiceHelper
					.getPersistService();
			coll = service.findValueInfo(query);
			if (coll == null) {
				coll = new ArrayList();
			}
			return coll;
		} catch (QMException ex) {
			ex.printStackTrace();
			if (coll == null) {
				coll = new ArrayList();
			}
			return coll;

		}
	}

	/**
	 * 艺毕通知书流程经过批准后要更改其相关的零部件状态
	 *
	 * @param completeListID
	 */
	public void changeCompleteListLinkPartState(String completeListID) {
		System.out.println("====>enter!");
		/*State s = State.toState("MANUFACTURING");
		Collection coll = null;
		try {
			coll = getAllLinkedParts(completeListID);
		} catch (QMException e) {
			e.printStackTrace();
		}
		coll.addAll(getRouteCompleteLinkedPartByBsoID(completeListID));
		if (coll != null && coll.size() > 0) {
			Iterator iter = coll.iterator();
			while (iter.hasNext()) {
				QMPartIfc part = (QMPartIfc) iter.next();
				System.out.println("=====>Part: " + part.getName() + ",state: "
						+ part.getLifeCycleState().getDisplay());
				if (!part.getLifeCycleState().getDisplay().equals(
						s.getDisplay())) {
					try {
						LifeCycleServiceHandler.setState(part, s, false);
					} catch (AppException e) {
						e.printStackTrace();
					} catch (QMException e) {
						e.printStackTrace();
					}
					System.out.println("=====>state after change: "
							+ part.getLifeCycleState());
				}
			}
		}*/
		try {
			RouteHelper.getRouteService().setRouteListCompletePartsState(completeListID);
		} catch (QMException e) {
			e.printStackTrace();
		}
		System.out.println("====>leave!");
		
	}
        public void changeCompleteListLinkPartState1(String completeListID) {
                        try {
                                RouteHelper.getRouteService().setRouteListCompletePartsState1(completeListID);
                        } catch (QMException e) {
                                e.printStackTrace();
                        }
	}
	public Collection getAllLinkedParts(String completeListID)
			throws QMException {
		return getOrderedPartsColl(RouteHelper.getRouteService().getRouteCompleteLinkedParts(
				completeListID));

	}

	private Collection getRouteListByName(String listName) {
		Collection coll = null;
		try {
			QMQuery query = new QMQuery("TechnicsRouteList");
			query.addCondition(new QueryCondition("routeListName", "=",
					listName));
			PersistService service = (PersistService) EJBServiceHelper
					.getPersistService();
			coll = service.findValueInfo(query);
			if (coll == null) {
				coll = new ArrayList();
			}
			return coll;
		} catch (QMException ex) {
			ex.printStackTrace();
			if (coll == null) {
				coll = new ArrayList();
			}
			return coll;
		}

	}

	private Collection getRouteListByNum(String listNum) {
		Collection coll = null;
		try {
			QMQuery query = new QMQuery("TechnicsRouteList");
			query.addCondition(new QueryCondition("routeListNumber", "=",
					listNum));
			PersistService service = (PersistService) EJBServiceHelper
					.getPersistService();
			coll = service.findValueInfo(query);
			if (coll == null) {
				coll = new ArrayList();
			}
			return coll;
		} catch (QMException ex) {
			ex.printStackTrace();
			if (coll == null) {
				coll = new ArrayList();
			}
			return coll;

		}

	}

	private Collection getRouteListByNumAndName(String listNum, String listName) {
		Collection coll = null;
		try {
			QMQuery query = new QMQuery("TechnicsRouteList");
			query.addCondition(new QueryCondition("routeListNumber", "=",
					listNum));
			query.addAND();
			query.addCondition(new QueryCondition("routeListName", "=",
					listName));
			PersistService service = (PersistService) EJBServiceHelper
					.getPersistService();
			coll = service.findValueInfo(query);
			if (coll == null) {
				coll = new ArrayList();
			}
			return coll;
		} catch (QMException ex) {
			ex.printStackTrace();
			if (coll == null) {
				coll = new ArrayList();
			}
			return coll;
		}
	}

	public String getUserDesc(String userID) throws QMException {
		PersistService pservice = (PersistService) EJBServiceHelper
				.getService("PersistService");
		ActorIfc actor = (ActorIfc) pservice.refreshInfo(userID, false);
		String userdesc = actor.getUsersDesc();
		return userdesc;
	}

	public void assignRouteCompleteLifeCycle(RoutelistCompletInfo routeListInfo)
			throws QMException {
		Vector vector = null;
		LifeCycleService lcService = (LifeCycleService) EJBServiceHelper
				.getService("LifeCycleService");
		vector = lcService.findCandidateTemplates("RoutelistComplet");
		// Vector vector1 = new Vector();
		LifeCycleTemplateIfc lct;
		for (int i = 0; i < vector.size(); i++) {
			lct = (LifeCycleTemplateIfc) vector.elementAt(i);
			if (lct.getLifeCycleName() != null
					&& lct.getLifeCycleName().equals("艺毕通知书")) {
				lcService.setLifeCycle(routeListInfo, lct);

			}
		}
	}

	public static void deleteRouteListComplet(String routeListCompletID)
			throws QMException {
		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();
		pservice.deleteBso(routeListCompletID);
	}

	public String isHasRoutePart(String partID) {
		try {
			PersistService pservice = (PersistService) EJBServiceHelper
					.getPersistService();
			// System.out.println("111111111111111111"+partID);
			QMPartInfo part = (QMPartInfo) pservice.refreshInfo(partID);
			String routeState1 = "无";

			QMQuery query = new QMQuery("ListRoutePartLink");
			query.addCondition(new QueryCondition("rightBsoID", "=", part
					.getMasterBsoID()));
			Collection coll = pservice.findValueInfo(query);
			// System.out.println("part 关联路线表数量＝＝＝ " + coll.size());
			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter
						.next();
				String routeState = linkInfo.getAdoptStatus();
				// System.out.println("路线状态" + routeState);
				if (!routeState.equals("无")) {
					routeState1 = "存在";
					return routeState1;
				}
			}
			return routeState1;
		} catch (QMException ex) {
			ex.printStackTrace();
		}
		return null;

	}

	public Collection getRouteCompleteLinkedProductsNames(String completeID) {
		try {
			return RouteHelper.getRouteService()
					.getRouteCompleteLinkedProductsNames(completeID);
		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	public Collection createReportFromRouteComplete(String CompleteID) {
		try {
			PersistService pservice = (PersistService) EJBServiceHelper
					.getPersistService();
			TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper
					.getService("TechnicsRouteService");
			RoutelistCompletInfo completeInfo = (RoutelistCompletInfo) pservice
					.refreshInfo(CompleteID);

			if (completeInfo.getCompletType().equals("零件")) {

				Collection hehe = new ArrayList();
				Collection mycoll = getRouteCompleteLinkedPartByBsoID(CompleteID);
				for (Iterator iter = mycoll.iterator(); iter.hasNext();) {
					QMPartInfo partInfo = (QMPartInfo) iter.next();
					String partmasterId = partInfo.getMasterBsoID();
					if (!hehe.contains(partmasterId)) {
						hehe.add(partmasterId);
					}
				}
				Collection mycoll2 = getAllLinkedParts(CompleteID);
				for (Iterator iter = mycoll2.iterator(); iter.hasNext();) {
					QMPartInfo partInfo = (QMPartInfo) iter.next();
					String partmasterId = partInfo.getMasterBsoID();
					hehe.add(partmasterId);
				}
				return getNeededCollForReport(hehe);
			}
			if (completeInfo.getCompletType().equals("艺准")) {
				ArrayList list = getCompleteLinkedListByBsoID(CompleteID);
				Collection allInformationColl = new ArrayList();
				for (int i = 0; i < list.size(); i++) {
					Collection partsColl = new ArrayList();
					TechnicsRouteListInfo routeList = (TechnicsRouteListInfo) list
							.get(i);
					Collection coll1 = routeService
							.getRouteListLinkParts(routeList);
					// System.out.println("艺准
					// "+routeList.getRouteListName()+"关联的零件数量 "+coll1.size());

					for (Iterator iter = coll1.iterator(); iter.hasNext();) {
						ListRoutePartLinkInfo LinkInfo = (ListRoutePartLinkInfo) iter
								.next();
						String partMasterID = LinkInfo.getRightBsoID();
						partsColl.add(partMasterID);
					}
					Collection Infomation = getNeededCollForReport(partsColl,
							routeList);
					for (Iterator iter2 = Infomation.iterator(); iter2
							.hasNext();) {
						allInformationColl.add(iter2.next());
					}

				}
				return allInformationColl;
			}
			return null;
		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	private Collection getNeededCollForReport(Collection partMasterIDColl) {
		try {
			return RouteHelper.getRouteService().getNeededCollForReport(
					partMasterIDColl);
		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private Collection getNeededCollForReport(Collection partMasterIDColl,
			TechnicsRouteListInfo routeList) {
		try {
			return RouteHelper.getRouteService().getNeededCollForReport(
					partMasterIDColl, routeList);
		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Collection getCompleteLinkedRouteListNameAndDescr(String completeID) {
		try {
			return RouteHelper.getRouteService()
					.getCompleteLinkedRouteListNameAndDescr(completeID);
		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/**
	 * 将零部件集合按编号排序
	 * @param parts
	 * @return
	 */
	public ArrayList getOrderedPartsColl(Collection parts)
	{
		PartComparator pc=new PartComparator();
		TreeSet set=new TreeSet(pc);
		set.addAll(parts);
		ArrayList result = new ArrayList();
        result.addAll(set);
		return result;
	}
	/**
	 * 放回零部件编号的比较结果
	 * @author yanq
	 *
	 */
	class PartComparator implements Comparator {

		public int compare(Object partA, Object partB) {
			if (!(partA instanceof QMPartIfc) || !(partB instanceof QMPartIfc)) {
				return -1;
			}
			Collator chCollator = Collator.getInstance(Locale.CHINESE);
			return chCollator.compare(((QMPartIfc) partA).getPartNumber(),
					((QMPartIfc) partB).getPartNumber());
		}

	}
}
