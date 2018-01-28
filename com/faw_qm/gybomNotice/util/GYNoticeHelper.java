 // SS1 查看BOM版本与逻辑不合栏、没子组、不显示保存成功、应支持按单级/多级导出 xianglx 2014-9-28
package com.faw_qm.gybomNotice.util;

import java.io.Serializable;

import com.faw_qm.bomNotice.ejb.service.BomNoticeService;
import com.faw_qm.bomNotice.model.BomAdoptNoticeIfc;
import com.faw_qm.bomNotice.model.BomAdoptNoticeInfo;
import com.faw_qm.bomNotice.model.BomAdoptNoticePartLinkIfc;
import com.faw_qm.bomNotice.model.BomChangeNoticeIfc;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.config.util.ConfigSpecHelper;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.IteratorVector;
import com.faw_qm.gybomNotice.ejb.service.GYBomNoticeService;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticePartLinkIfc;
import com.faw_qm.iba.value.model.StringValueInfo;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;   
import com.faw_qm.project.util.Role;
import com.faw_qm.project.util.RolePrincipalTable;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.RouteNodeIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.users.model.ActorInfo;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.workflow.definer.model.WfAssignedActivityTemplateIfc;
import com.faw_qm.workflow.definer.util.WfVariableInfo;
import com.faw_qm.workflow.engine.ejb.entity.WfVariable;
import com.faw_qm.workflow.engine.model.WfActivityIfc;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.workflow.engine.model.WfProcessInfo;
import com.faw_qm.workflow.work.model.WfBallotInfo;
import com.faw_qm.workflow.work.model.WorkItemIfc;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
//SS1 修改TD8416 流程信息显示不全问题  文柳 2014-7-16
//SS2 修改TD8458 文柳 2014-7-18
//SS3 修改TD8409 文柳 2014-7-29
//SS4 设置工艺BOM生效，流程批准后，将已生效bom删除，将未生效bom设置成生效。 liunan 2016-9-5
//SS5 工艺BOM批量修改，流程批准后，批量修改工艺BOM。 liunan 2016-9-20
//SS6 添加整理人信息 maxiaotong 2017-09-11
public class GYNoticeHelper implements Serializable{
    /**
     * 调试开关
     */
    public static final boolean VERBOSE = RemoteProperty.getProperty("com.faw_qm.GYNBomNotice.verbose", "true").equals("true");
    /**
     * 封装服务端请求方法
     * @param serviceName 服务名
     * @param methodName 方法名
     * @param paraClass 参数类
     * @param paraObj 参数值
     * @return Object 请求服务返回的结果
     * @throws QMException
     */
    public static Object requestServer(String serviceName, String methodName, Class[] paraClass, Object[] paraObj) throws QMException
    {

        RequestServer server = RequestServerFactory.getRequestServer();
        if(server == null)
        {
            throw new QMException( "获取远程RequestServer失败！");
        }
        ServiceRequestInfo sInfo = new ServiceRequestInfo();
        sInfo.setServiceName(serviceName);
        sInfo.setMethodName(methodName);
        sInfo.setParaClasses(paraClass);
        sInfo.setParaValues(paraObj);
        Object obj = null;
        try
        {
            obj = server.request(sInfo);
        }catch(QMRemoteException e)
        {
            throw new QMException(e);
        }
        return obj;
    }
    
    /**
     * 客户端调用服务端方法。 判断了当前的调用是在服务端还是客户端，如果是服务端即按照服务端的方式调用服务；如果是客户端即按照客户端的方式调用服务。
     * @param info ServiceRequestInfo 服务请求信息封装类。
     * @return Object 请求服务返回的结果。
     * @throws QMException
     * @throws QMException
     */
    public static Object request(ServiceRequestInfo info) throws QMException
    {
        Object obj = null;
        RequestServer server = RequestServerFactory.getRequestServer();
        if(server != null)
        {
            obj = server.request(info);
        }else
        {
            try
            {
                BaseService baseService = EJBServiceHelper.getService(info.getServiceName());
                Method method = baseService.getClass().getMethod(info.getMethodName(), info.getParaClasses());
                obj = method.invoke(baseService, info.getParaValues());
            }catch(QMException e)
            {
                throw e;
            }catch(Exception e)
            {
                throw new QMException(e);
            }
        }
        return obj;
    }
    /**
     * 瘦客户显示发布BOM
     * @param String bsoID 采用单BsoID
     * @return Object 请求服务返回的结果。
     * @throws QMException
     */
 //CCBegin SS1
   public Collection getReleaseBom(String bsoID,String jbbs)throws QMException{
//CCEnd SS1
    	
    	PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
    	GYBomNoticeService bservice = (GYBomNoticeService) EJBServiceHelper.getService("GYBomNoticeService");
    	QMPartIfc bomIfc = (QMPartIfc)service.refreshInfo(bsoID);
//CCBegin SS1
    	Collection col = null;
    	if (jbbs.equals("单级"))
    	 	col=bservice.getReleaseBomDJ(bomIfc);
    	else
    	 	col=bservice.getReleaseBom(bomIfc);
//CCEnd SS1
    	return col;
    	
    }
    
    /**
	 * 通过采用单BsoID获得其值对象
	 * @param id 采用单BsoID
	 * @return 采用单值对象
	 * @throws QMException
	 */
	public static GYBomAdoptNoticeInfo findNoticeInfoByID(String id) throws QMException {

		GYBomAdoptNoticeInfo noticeInfo = null;
		try {
			// 获得持久化服务
			PersistService persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			noticeInfo = (GYBomAdoptNoticeInfo) persistService.refreshInfo(id);
		} 
		catch (Exception e) {
			throw new QMException(e);
		} 
		
		return noticeInfo;
	} 
	 /**
     * 瘦客户显示工艺发布单
     * @param String bsoID 采用单BsoID
     * @return Object 请求服务返回的结果。
     * @throws QMException
     */
    public Object[] getGYBomAdopNoticeInformation(String bsoID)throws QMException{
    	//CCBegin SS3
    	Object[] obj = new Object[3]; 
    	PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
    	GYBomNoticeService bservice = (GYBomNoticeService) EJBServiceHelper.getService("GYBomNoticeService");
    	GYBomAdoptNoticeIfc bomIfc = (GYBomAdoptNoticeIfc)service.refreshInfo(bsoID);
    	String[] notice = new String[13];
    	notice[0] = bomIfc.getPublishType();
    	notice[1] = bomIfc.getClassification();
    	notice[2] = bomIfc.getAdoptnoticenumber();
    	notice[3] = bomIfc.getAdoptnoticename();
    	notice[4] = bomIfc.getLocation();
    	notice[5] = bomIfc.getLifeCycleState().getDisplay();
    	String lifeCycleTemplate = bomIfc.getLifeCycleTemplate();
    	LifeCycleTemplateIfc template = (LifeCycleTemplateIfc)service.refreshInfo(lifeCycleTemplate);
    	notice[6] =template.getLifeCycleName();
    	notice[7] = bomIfc.getConsdesc();
    	
    	
    	String designDoc = bomIfc.getDesignDoc();
    	if(designDoc!=null&&!designDoc.equals("")){
    		DocIfc desDoc = (DocIfc)service.refreshInfo(designDoc);
    		notice[8] = desDoc.getDocNum();
    	}else{
    		notice[8] = "";
    	}
    	
    	String jfNoticeBsoID = bomIfc.getJfBomnotice();
    	if(jfNoticeBsoID!=null&&!jfNoticeBsoID.equals("")){
    		BaseValueIfc jfNoticeIfc = (BaseValueIfc)service.refreshInfo(jfNoticeBsoID);
    		if(jfNoticeIfc instanceof BomAdoptNoticeIfc){
    			notice[9] = ((BomAdoptNoticeIfc)jfNoticeIfc).getAdoptnoticenumber();
    		}else if(jfNoticeIfc instanceof BomChangeNoticeIfc){
    			notice[9] = ((BomChangeNoticeIfc)jfNoticeIfc).getAdoptnoticenumber();
    		}else{
    			notice[9] = "";
    		}
    	}else{
    		notice[9] = "";
    	}
    	
    	String zxDocBsoID = bomIfc.getZxAdoptNotice();
    	if(zxDocBsoID!=null&&!zxDocBsoID.equals("")){
    		DocIfc zxDocIfc = (DocIfc)service.refreshInfo(zxDocBsoID);
    		//CCBegin SS2
    		String zxDocNum = zxDocIfc.getDocNum();
    		if(zxDocNum.startsWith("CONFIRMATION-")&&zxDocNum.length()>13){

    			String numStr = zxDocNum.substring(13,zxDocNum.length());
    			notice[10]=numStr;
    		}else{
    			notice[10]=zxDocNum;
    		}
    		//notice[10] = zxDocIfc.getDocNum();
    		//CCEnd SS2
    	}else{
    		notice[10] = "";
    	}
    
    	String topPartBsoID = bomIfc.getTopPart();
    	if(topPartBsoID!=null&&!topPartBsoID.equals("")){
    		QMPartIfc partIfc = (QMPartIfc)service.refreshInfo(topPartBsoID);
    		notice[11] = partIfc.getPartNumber();
    	}else{
    		notice[11] = "";
    	}
    	String parentBsoID = bomIfc.getParentNotice();
    	if(parentBsoID!=null&&!parentBsoID.equals("")){
    		GYBomAdoptNoticeIfc parentNoticeIfc = (GYBomAdoptNoticeIfc)service.refreshInfo(parentBsoID);
    		notice[12] = parentNoticeIfc.getAdoptnoticenumber();
    	}else{
    		notice[12] = "";
    	}
    	
    	obj[0] = notice;
    	Vector adoptVec = new Vector();
    	Vector noVec = new Vector();
    	Collection col = bservice.getBomPartFromBomAdoptNotice(bomIfc);
    	if(col!=null&&col.size()>0){
    		for(Iterator itee= col.iterator();itee.hasNext();){
    			GYBomAdoptNoticePartLinkIfc links = (GYBomAdoptNoticePartLinkIfc)itee.next();
    			if(links.getAdoptBs().equals("采用")){
    				String[] adoptParts = new String[10];
    				adoptParts[0] = links.getPartNumber();
    				adoptParts[1] = links.getPartName();
    				adoptParts[2] = links.getVersionValue();
    				adoptParts[3] = links.getSl();
    				adoptParts[4] = links.getZzlx();
    				adoptParts[5] = links.getZplx();
    				adoptParts[6] = links.getPartView();
    				adoptParts[7] = links.getLifecyclestate();
    				adoptParts[8] = links.getVirtualPart();
    				if(links.getLinkPart()!=null&&!links.getLinkPart().equals("")){
    					QMPartIfc linkPart = (QMPartIfc)service.refreshInfo(links.getLinkPart());
    					if(linkPart!=null){
    						adoptParts[9] = linkPart.getPartNumber();
    					}else{
    						adoptParts[9] = "";
    					}
    				}else{
    					adoptParts[9] ="";
    				}
    				adoptVec.add(adoptParts);
    				
    			}else if(links.getAdoptBs().equals("不采用")){
    				String[] noAdoptParts = new String[7];
    				noAdoptParts[0]=links.getPartNumber();
    				noAdoptParts[1]=links.getPartName();
    				noAdoptParts[2]=links.getVersionValue();
    				noAdoptParts[3]=links.getSl();
    				noAdoptParts[4]=links.getZzlx();
    				noAdoptParts[5]=links.getZplx();
    				noAdoptParts[6]=links.getPartView();
    				noVec.add(noAdoptParts);
    			}
    		}
    	}
    	obj[1]= adoptVec;
    	obj[2] = noVec;
    	
    	//CCEnd SS3
    	return obj;
    	
    }
	/**
	 * 根据生命周期ID，获取生命周期名
	 * @param lfcbsoID 生命周期ID
	 * @return 获取生命周期名
	 * @throws QMException
	 */
	public static String getLifeCycleNameByID(String lfcbsoID)
			throws QMException {
		String lfcName = "";
		LifeCycleTemplateInfo lfcInfo = null;
		try {
			// 获得持久化服务
			PersistService persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			lfcInfo = (LifeCycleTemplateInfo) persistService.refreshInfo(
					lfcbsoID, false);
			if (lfcInfo != null) {
				lfcName = lfcInfo.getLifeCycleName();
			}
		} catch (Exception ex) {
			return lfcName;
		}
		return lfcName;
	}
	 /**
	 * 通过采用单BsoID获得当前对象的生命周期名称、业务对象的当前状态、所有状态、
	 * 
	 * @param String
	 *            id 生命周期ID
	 * @return Vector 获取生命周期名
	 * @throws QMException
	 * 
	 */
	public static Vector getNoticeLifecycleInfo(String id) throws QMException {
		Vector infos = new Vector();
		LifeCycleManagedIfc lcmInfo = null;
		try {
			// 获得持久化服务
			PersistService persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			lcmInfo = (LifeCycleManagedIfc) persistService.refreshInfo(id);
		} catch (Exception e) {
			throw new QMException(e);
		}
		LifeCycleTemplateInfo lctInfo = null;
		Vector allstates = new Vector(); // allstate存放当前对象的生命周期的所有生命周期状态
		try {
			// 获得持久化服务
			LifeCycleService lifecycleService = (LifeCycleService) EJBServiceHelper
					.getService("LifeCycleService");
			lctInfo = lifecycleService.getLifeCycleTemplate(lcmInfo);
			String lifecyclename = lctInfo.getLifeCycleName();
			Vector temp1 = new Vector();
			temp1.addElement(lifecyclename);
			infos.addElement(temp1);
			String currentState = lcmInfo.getLifeCycleState().getDisplay(); // 获得当前生命周期状态
			Vector temp2 = new Vector();
			temp2.addElement(currentState);
			infos.addElement(temp2);
			if (lctInfo != null) {
				allstates = lifecycleService.findStates(lctInfo);
			}
			if (allstates != null && allstates.size() > 0) {
				for (int j = 0; j < allstates.size(); j++) {
					String state = allstates.elementAt(j).toString();
					String stateDisplay = LifeCycleState
							.toLifeCycleState(state).getDisplay();
					allstates.setElementAt(stateDisplay, j);
				}
			}
			infos.addElement(allstates);
		} catch (Exception e) {
			throw new QMException(e);
		}
		return infos;
	}
	 /**
	 * 通过采用单BsoID获得当前对象的流程信息
	 * @param String bsoId 采用单BsoID
	 * @return String[] 流程信息
	 * @throws QMException
	 * 
	 */
	public static String[] getProcessInfomation(String bsoID) throws QMException {

		//CCBegin SS6
//		String[] returnStr = new String[10];
		String[] returnStr = new String[12];
		//CCEnd SS6
		returnStr[0] = "拟制";
		returnStr[2] = "校对";
		returnStr[4] = "审核";
		returnStr[6] = "批准";
		returnStr[8] = "项目经理";
		returnStr[1] = "";
		returnStr[3] = "";
		returnStr[5] = "";
		returnStr[7] = "";
		returnStr[9] = "";
		//CCBegin SS6
		returnStr[10] = "整理人";
		returnStr[11] = "";
		//CCEnd SS6
		PersistService persistService = (PersistService) EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("WfProcess");
		query.addOrderBy("createTime",true);
		
		query.addCondition(new QueryCondition("businessObjBsoID", "=", bsoID));
		Collection col = (Collection)persistService.findValueInfo(query);
		
		if(col!=null&&col.size()>0){
			for(Iterator ite = col.iterator();ite.hasNext();){
				WfProcessInfo pInfo = (WfProcessInfo)ite.next();

				IteratorVector itvc = getAllActivities(pInfo.getBsoID());
				while (itvc.hasNext()) {
					WfActivityIfc waInfo = (WfActivityIfc) itvc.next();
					String activityID = waInfo.getBsoID();
					String name = waInfo.getName();
					String paticipants = getAllPaticipantOfActivity(activityID);
					String time = waInfo.getModifyTime().toLocaleString();
					System.out.println("活动名称====="+name);
					//CCBegin SS1
					if(name.equals("拟制BOM")){
						returnStr[1] = paticipants;
					}else if(name.equals("校对")){
						returnStr[3] = paticipants;
					}else if(name.equals("审核")){
						returnStr[5] = paticipants;
					}else if(name.equals("批准")){
						returnStr[7] = paticipants;
					}else if(name.equals("维护专用件")){
						returnStr[9] = paticipants;
					}
					//CCBegin SS6
					else if(name.equals("整理BOM")){
						
//						returnStr[11] = paticipants.substring(0, paticipants.indexOf(";"));
						returnStr[11] = paticipants;
					}
					//CCEnd SS6
					//CCEnd SS1

				}
		}
		}
			return returnStr;
	}
	/**
	 * 通过采用单BsoID和进程id获得当前对象的生命周期名称、业务对象的当前状态、所有状态和各状态对应任务的参与者。
	 * 对上述getNoticeLifecycleInfo()方法的修改，添加了进程id，以便获得相关参与者。
	 * 
	 * @param String
	 *            id 流程ID
	 * @param String
	 *            wordid 对象ID
	 * @return Vector
	 * @throws QMException
	 */
	public static Vector getNoticeLifecycleInfo(String id, String wordid)
			throws QMException {
		Vector infos = new Vector();
		try {
			// 获得持久化服务
			PersistService persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			HashMap rolemap = new HashMap();
			Vector paticipantsvec = new Vector();
			WfProcessIfc wfprocess = (WfProcessIfc) persistService
					.refreshInfo(wordid);
			// 获得流程中所有任务对应的角色和参与者，保存到HashMap中，其中key是角色名，value是参与者。
			if (wfprocess instanceof WfProcessIfc) {
				RolePrincipalTable table = ((WfProcessIfc) wfprocess)
						.getRolePrincipalMap();
				Enumeration enumeration = table.keys();
				while (enumeration.hasMoreElements()) {
					String role = (String) enumeration.nextElement();
					if (role.equals("PROMOTER") || role.equals("SUBMITTER"))
						continue;
					Role roleobject = Role.toRole(role);
					String rolename = roleobject.getDisplay();

					Vector rolevector = (Vector) table.get(role);
					String roleString = "";
					if (rolevector != null && rolevector.size() > 0) {
						for (int tt = 0; tt < rolevector.size(); tt++) {
							String userid = (String) rolevector.elementAt(tt);
							String userName = "";
							if (userid.startsWith("User_")) {
								UserInfo userInfo = (UserInfo) persistService
										.refreshInfo(userid);
								userName = userInfo.getUsersDesc();
							} else if (userid.startsWith("Group_")) {
								GroupInfo groupinfo = (GroupInfo) persistService
										.refreshInfo(userid);
								userName = groupinfo.getUsersName();
							} else {
								System.out.println("参与者不是用户也不是用户组！");
							}
							if (roleString.equals("")) {
								roleString = userName;
							} else {
								roleString = roleString + "," + userName;
							}
						}
					}
					String[] roleandname = { rolename, roleString };
					infos.add(roleandname);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new QMException(e);
		}
		return infos;
	}

	/**
	 * 得到采用单当前的生命周期状态
	 * 
	 * @param String
	 *            id 给定ID
	 * @return Vector 状态集合
	 * @throws QMException
	 */
	public static String getCurrentState(String id) throws QMException {
		String state = "";
		LifeCycleManagedIfc lcmInfo = null;
		try {
			// 获得持久化服务
			PersistService persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			lcmInfo = (LifeCycleManagedIfc) persistService.refreshInfo(id);
		} catch (Exception e) {
			throw new QMException(e);
		}
		if (lcmInfo != null) {
			state = lcmInfo.getLifeCycleState().getDisplay();
		}
		return state;
	}

	/**
	 * 通过进程ID获得所有流程中的信息 首先获得所有的活动，
	 * 返回是Vector1，Vector1的每个元素都是Vector2，Vector2中第一个元素是活动名称vector
	 * ，第二个元素是所有参与者vector，第三个元素是一个Vector3，Vector3包含活动的所有变量及变量值。
	 * 
	 * @param String
	 *            id 给定ID
	 * @return Vector 流程信息集合
	 * @throws QMException
	 */

	public static Vector getWorkflowInfo(String id) throws QMException {
		Vector activityInfos = new Vector();
		PersistService persistService = null;
		try {
			// 获得持久化服务
			persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
		} catch (Exception e) {
			throw new QMException(e);
		}
		try {
			WfProcessInfo wfprocessInfo = (WfProcessInfo) persistService
					.refreshInfo(id); // 得到进程Info
			IteratorVector itvc = getAllActivities(id);
			while (itvc.hasNext()) {
				WfActivityIfc waInfo = (WfActivityIfc) itvc.next();
				
				Vector variables = getAllVariablesByWfActivity(waInfo);
				String activityID = waInfo.getBsoID();
				String paticipants = getAllPaticipantOfActivity(activityID);
				String VoteInformation = getVoteInformation(waInfo);
				Vector activityinfo = new Vector();
				Vector wanamevec = new Vector();
				wanamevec.addElement(waInfo.getName()); // 活动名称
				Vector paticipantsvec = new Vector();
				paticipantsvec.addElement(paticipants); // 参与者
				Vector voteVec = new Vector();
				voteVec.addElement(VoteInformation); // 投票信息
				activityinfo.addElement(wanamevec);
				activityinfo.addElement(paticipantsvec);
				activityinfo.addElement(voteVec);
				activityinfo.addElement(variables);
				activityInfos.addElement(activityinfo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return activityInfos;
	}

	/**
	 * 返回给定工作流中的所有过期的的活动
	 * 
	 * @param process
	 *            给定的工作流对象
	 * @return Iterator 工作流活动值对象集合
	 * @throws QMException
	 */
	public static IteratorVector getAllActivities(String bsoID)
			throws QMException {
		// 如果参数为空,返回空对象
		IteratorVector itvc = new IteratorVector();
		try {
			// 生成查询,加入查询条件
			QMQuery query = new QMQuery("WfAssignedActivity");
			query.addCondition(new QueryCondition("parentProcessBsoID", "=",
					bsoID));
			// 添加对结果的排序，按升序排列，旧任务在上，新任务在下。
			query.addOrderBy("modifyTime", false);
			// 查询,返回结果

			itvc.addAll(((PersistService) EJBServiceHelper.getPersistService())
					.findValueInfo(query));
		} catch (QMException e) {
			throw new QMException(e);
		}
		return itvc;
	}

	/**
	 * 获得所有的分配活动模板的变量及变量值
	 * @param workitemid
	 *            工作项的ID
	 * @return WfVariable[]中包含所有可见的分配活动的变量.
	 * @throws QMException
	 */
	private static Vector getAllVariablesByWfActivity(WfActivityIfc activityifc)
			throws QMException {
		// 获得分配活动的所有变量
		Vector variables = new Vector();
		WfVariable awfvariable[] = activityifc.getContext().getVariableList();
		String wfactivityname = activityifc.getName();
		// 获得分配活动模板
		String assignedactivitytemplateid = activityifc.getTemplateID();
		PersistService persistService = null;
		try {
			// 获得持久化服务
			persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
		} catch (Exception e) {
			throw new QMException(e);
		}
		WfAssignedActivityTemplateIfc wfassignedactivitytemplateifc = (WfAssignedActivityTemplateIfc) persistService
				.refreshInfo(assignedactivitytemplateid);
		// 遍历分配活动的所有变量
		for (int i = 0; i < awfvariable.length; i++) {
			String wfvariablename = awfvariable[i].getName();
			WfVariableInfo wfvariableinfo = wfassignedactivitytemplateifc
					.getContextSignature().getVariableInfo(wfvariablename);
			// 获得分配活动模板的变量是可见并且不是primaryBusinessObject和self
			if (wfvariableinfo.isVisible()
					&& !(wfvariablename.equals("primaryBusinessObject"))
					&& !(wfvariablename.equals("self"))) {
				Object value = awfvariable[i].getValue();
				if (value == null) {
					value = "";
				}
				String str = value.toString();
				if (str.indexOf("~") >= 0) {
					String temp[] = str.split("-");
					String nstr = "";
					for (int ii = 0; ii < temp.length; ii++) {
						String str1 = temp[ii];
						String timestr = str1.substring(0, str1.indexOf("~"));
						timestr = timestr.replaceAll(" CST", "");
						timestr = timestr.replaceAll(" GMT", "");
						SimpleDateFormat pSdf = new SimpleDateFormat(
								"EEE MMM DD HH:mm:ss yyyy", Locale.ENGLISH);
						SimpleDateFormat fSdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						try {
							str1 = fSdf.format(pSdf.parse(timestr))
									+ str1.substring(str1.indexOf("~"), str1
											.length());
						} catch (ParseException ee) {
							ee.printStackTrace();
						}
						if (nstr.equals("")) {
							nstr = str1;
						} else {
							nstr = nstr + "-" + str1;
						}
					}
					str = nstr;
				}

				variables.addElement(wfvariablename + ":  " + str);
			}
		}
		return variables;
	}

	/**
	 * 根据活动得到所有的工作项，每个工作项的所有者即是该活动的所有参与者
	 * @param String    activityID 活动ID
	 *@throws QMException
	 */
	public static String getAllPaticipantOfActivity(String activityID)
			throws QMException {
		String paticipants = "";
		PersistService persistService = null;
		try {
			QMQuery query = new QMQuery("WorkItem");
			query.addCondition(new QueryCondition("sourceID", "=", activityID));
			Iterator iterator = (((PersistService) EJBServiceHelper
					.getPersistService()).findValueInfo(query)).iterator();
			persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			while (iterator.hasNext()) {
				WorkItemIfc workitemIfc = (WorkItemIfc) iterator.next();
				String ownerid = workitemIfc.getOwner();
				if (workitemIfc.getCompletedBy() != null) {
					ActorInfo userinfo = (ActorInfo) persistService
							.refreshInfo(ownerid);
					paticipants = paticipants + userinfo.getUsersDesc() + ";"
							+ "完成时间：" + workitemIfc.getModifyTime() + "\n";
				} else {
					ActorInfo userinfo = (ActorInfo) persistService
							.refreshInfo(ownerid);
					paticipants = paticipants + userinfo.getUsersDesc() + ";"
							+ "未完成。\n";
				}
			}
		} catch (QMException qme) {
			throw new QMException(qme);
		}
		return paticipants;
	}

	/**
	 * 得到活动投票信息
	 * 
	 * @param WfActivityIfc
	 *            waInfo 活动
	 *@return String
	 *@throws QMException
	 */
	static String getVoteInformation(WfActivityIfc waInfo) throws QMException {
		String activityID = waInfo.getBsoID();
		PersistService persistService = (PersistService) EJBServiceHelper
				.getService("PersistService");
		QMQuery query = new QMQuery("WorkItem");
		query.addCondition(new QueryCondition("sourceID", "=", activityID));
		Iterator iterator = (persistService.findValueInfo(query)).iterator();
		String userAndVote = "";
		while (iterator.hasNext()) {
			WorkItemIfc workitemIfc = (WorkItemIfc) iterator.next();
			String ownerid = workitemIfc.getOwner();
			if (workitemIfc.getCompletedBy() != null) {
				ActorInfo userinfo = (ActorInfo) persistService
						.refreshInfo(ownerid);
				String user = userinfo.getUsersDesc();
				String WfAssignmentID = workitemIfc.getParentWA();
				QMQuery query1 = new QMQuery("WfBallot");
				int n = query1.appendBso("AssignmentBallotLink", false);
				query1.addCondition(n, new QueryCondition("leftBsoID", "=",
						WfAssignmentID));
				query1.addAND();
				query1.addCondition(n, 0, new QueryCondition("rightBsoID",
						"bsoID"));
				query1.addAND();
				query1.addCondition(0,
						new QueryCondition("vetor", "=", ownerid));
				Collection coll = persistService.findValueInfo(query1);
				if (coll.size() == 0) {
					continue;
				}
				String vote = "";
				for (Iterator iterator1 = coll.iterator(); iterator1.hasNext();) {
					WfBallotInfo ballot = (WfBallotInfo) iterator1.next();
					Vector vec = ballot.getEventList();
					for (int i = 0; i < vec.size(); i++) {
						vote += vec.elementAt(i) + "、";
					}
				}
				vote = vote.endsWith("、") ? vote
						.substring(0, vote.length() - 1) : vote;
				if (vote.length() > 0) {
					userAndVote += user + ":" + vote + ";";
				}
			}
		}
		return userAndVote;
	}
	/**
	 * 整车BOM报毕报表
	 * @param partID
	 * @return
	 * @throws QMException
	 */
	public Vector getBomForWholePartStatistics(String partID)throws QMException{
		PersistService persistService = (PersistService) EJBServiceHelper
		.getService("PersistService");
		QMPartIfc part =(QMPartIfc)persistService.refreshInfo(partID);
		Vector vec=(Vector) getReleaseBom(part);
		return vec;
	}
	/**
	 * 计算整车报毕率
	 */
	public String bomRate(String partID)throws QMException{
		PersistService persistService = (PersistService) EJBServiceHelper
		.getService("PersistService");
		QMPartIfc part1 =(QMPartIfc)persistService.refreshInfo(partID);
		Vector vec=(Vector) getReleaseBom(part1);
		String rate="0";  
		float m=0;
		float z=0;
		if (vec != null) {
			for (int n = 0; n < vec.size(); n++) {
				Object[] obj = (Object[]) vec.get(n);
				QMPartInfo part = (QMPartInfo) obj[0];
				String h = (String) obj[1];
				if (part.getLifeCycleState().getDisplay().equals("生产")) {
					m = Float.parseFloat(h) + m;
				}
				z = Float.parseFloat(h) + z;
			}
			float zong = (float) vec.size();
			if (vec.size() != 0) {
				rate = String.valueOf(m / zong * 100);
			}
		}
     
		return rate;  
	}
	/**
	 * 获取题目
	 */
	public String getTile(String partID)throws QMException{
		PersistService persistService = (PersistService) EJBServiceHelper
		.getService("PersistService");
		QMPartIfc part =(QMPartIfc)persistService.refreshInfo(partID);
		String title="";
		title=part.getPartNumber()+" ("+part.getPartName()+")"+part.getVersionValue()+"("+part.getViewName()+")";
		return title;
	}
	/**
	 * 独立总成报表
	 * @param partID
	 * @param view
	 * @return
	 * @throws QMException
	 */
	public Vector getBomForSiglePartStatistics(String partID,String view)throws QMException{
		PersistService persistService = (PersistService) EJBServiceHelper
		.getService("PersistService");
		QMPartIfc part =(QMPartIfc)persistService.refreshInfo(partID);
		PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
//    	StandardPartService sp = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
    	PartUsageLinkIfc partlink=new PartUsageLinkInfo();
        String quantity="0.0";
    	Vector vec=new Vector();
    	//第一个元素：QMPartIfc;第二元素：使用数量String；第三元素：路线相关内容String[]
    	vec = getParts(part,vec,view,quantity);

		return vec;
	}
	/**
	 * 独立总成零部件
	 * @param partifc_old
	 * @param partlink
	 * @param result
	 * @return
	 * @throws QMException
	 */
	private HashMap map=new HashMap();
	   private Vector getParts(QMPartIfc partifc_old,Vector result,String viewName,String quantity)
	    throws QMException
	    {
	      Object[] obj = new Object[2];  
	       // 第一步：调用getUsesPartIfcs方法，返回Collection，如果Collection为空，或者长度为0,抛出异常
	       Collection collection = getUsesPartIfcs(partifc_old);
	       if((collection.size() == 0) || (collection == null))
	       {   	 	  
	    	   if(partifc_old.getViewName().trim().equals(viewName)){
	    		
	    		   Object[] part=(Object[])map.get(partifc_old.getPartNumber());
	    	      if(part==null){
	    	    	   obj[0]=partifc_old; 		
		    		   obj[1]=quantity;
	    		      map.put(partifc_old.getPartNumber(), obj);
	    	      }else{
	    	    	  obj[0]=partifc_old;
	    	    	  obj[1]=String.valueOf((Float.parseFloat(quantity)+Float.parseFloat((String)part[1])));
	    	    	   map.put(partifc_old.getPartNumber(), obj);
	    	      }
	    	      result.add(map.get(partifc_old.getPartNumber()));	      				  
	    	   }  
	    	 
	    	  
	       }
	       //第二步: 对collection中的每个元素进行循环，需要另外声明一个递归的方法productStructure()
	       else
	       {
	           Object[] tempArray = new Object[collection.size()];
	           collection.toArray(tempArray);
	           //先把tempArray中的所有元素都放到resultVector中来
	           for (int i = 0; i < tempArray.length; i++)
	           {
	        	   QMPartIfc partIfc1 = null;
	               Object[] tempObj = (Object[]) tempArray[i];
	               if(tempObj[1] instanceof QMPartIfc
	                       && tempObj[0] instanceof PartUsageLinkIfc)
	               {
	                   partIfc1 = (QMPartIfc) tempObj[1];	
	                   PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc) tempObj[0];
	                   if(partIfc1!=null)
	                   {             
	                       if(partUsageLinkIfc1!=null){
	                    	   quantity = Float.toString(partUsageLinkIfc1.getQuantity());   
	                       }else{
	                    	   quantity="0.0";
	                       }
						if (partIfc1 != null && partUsageLinkIfc1 != null){
						     getParts(partIfc1, result,viewName,quantity);
						  }
						 }
	                }
	           }       
	       }
	   
	       return result;
	    }
	   /**
		 * 计算独立整车报毕率
		 */
		public String siglePartRate(String partID,String view)throws QMException{
			PersistService persistService = (PersistService) EJBServiceHelper
			.getService("PersistService");
			Vector vec=(Vector) getBomForSiglePartStatistics(partID,view);
			String rate="0";  
			float m=0;
			float j=0;

			if (vec != null) {
			for (int n = 0; n < vec.size(); n++) {
				Object[] obj = (Object[]) vec.get(n);
				QMPartInfo part = (QMPartInfo) obj[0];
				String h = (String) obj[1];

				if (part.getLifeCycleState().getDisplay().equals("生产")&&!part.getPartType().toString().equalsIgnoreCase("Standard")) {
					m = Float.parseFloat(h) + m;
					
					
				}
				if (!part.getPartType().toString().equalsIgnoreCase("Standard")) {
					j = Float.parseFloat(h) + j;
				}
			}
	
			if (j != 0) {
				rate = String.valueOf(m/ j * 100);
			 }
	     	}

			return rate;
		}
	 /**
	    * 解放查看发布BOM（解放查看发布BOM）
	    * @param String bsoID
	    * @return Collection
	    */
		public Collection getReleaseBom(QMPartIfc partIfc)throws QMException {
			PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
//	    	StandardPartService sp = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
	    	PartUsageLinkIfc partlink=new PartUsageLinkInfo();

	    	Vector vec=new Vector();
	    	//第一个元素：QMPartIfc;第二元素：使用数量String；第三元素：路线相关内容String[]
	    	vec = getAllParts(partIfc,partlink,vec);
	    	 return vec;
		  
		}
		 /**
	     * 根据指定配置规范，获得指定部件的使用结构：
	     * 返回集合Collection的每个元素是一个数组对象，第0个元素记录关联对象信息，
	     * @param partIfc 零部件值对象。
	     * @param configSpecIfc 零部件配置规范。
	     * @throws QMException
	     * @see QMPartInfo
	     * @see PartConfigSpecInfo
	     * @return Collection 集合Collection的每个元素是一个数组对象:<br>
	     */

	    private Vector getAllParts(QMPartIfc partifc_old,PartUsageLinkIfc partlink,Vector result)
	    throws QMException
	    {
	
	     // float quan=partlink.getQuantity();
	      Object[] obj = null;
	       // 第一步：调用getUsesPartIfcs方法，返回Collection，如果Collection为空，或者长度为0,抛出异常
	       Collection collection = getUsesPartIfcs(partifc_old);
	       if((collection.size() == 0) || (collection == null))
	       {

	           return result;
	       }
	       //第二步: 对collection中的每个元素进行循环，需要另外声明一个递归的方法productStructure()
	       else
	       {
	           Object[] tempArray = new Object[collection.size()];
	           collection.toArray(tempArray);
	          
	          // PartUsageLinkIfc partUsageLinkIfc1=null;
	           //先把tempArray中的所有元素都放到resultVector中来
	           for (int i = 0; i < tempArray.length; i++)
	           {
	        	   QMPartIfc partIfc1 = null;
	              // QMPartIfc part1 = null;
	               QMPartIfc part2 = null;
	               Object[] tempObj = (Object[]) tempArray[i];
	               if(tempObj[1] instanceof QMPartIfc
	                       && tempObj[0] instanceof PartUsageLinkIfc)
	               {
	                   obj = new Object[3];
	                   partIfc1 = (QMPartIfc) tempObj[1];
	                   partlink=(PartUsageLinkIfc)tempObj[0];
	                   part2 = filterLifeState(partIfc1);
	            
	                   PartUsageLinkIfc partUsageLinkIfc1 = (PartUsageLinkIfc) tempObj[0];
	                   if(part2!=null)
	                   {
	                       obj[0] = part2;          
	                       //quan=quan*partUsageLinkIfc1.getQuantity();
	                       if(partUsageLinkIfc1!=null){
		                       obj[1] =Float.toString( partUsageLinkIfc1.getQuantity());   
	                       }else{
	                    	   obj[1] = "0.0";
	                       }
                           if (part2.getPartType().toString().equalsIgnoreCase(
								"Logical")) {
							// 根据规则判断路线串内容:返回内容为String数组，代表需显示零件；返回为QMPartIfc，代表不显示零件
							Object routeObj = filterPartWithRoute(part2);

							if (routeObj instanceof QMPartIfc) {// 不显示的零件

							} else if (routeObj instanceof String[]) {// 显示的零件
								String[] strV = (String[]) routeObj;
								obj[2] = routeObj;
								result.add(obj);
								if (part2 != null && partlink != null)
									getAllParts(part2, partlink, result);
							}

						}
	                   }
		              
	               }

	            }
	        }
	        return result;
	    }
	    /**
		 * 根据指定配置规范，获得指定部件的使用结构： 返回集合Collection的每个元素是一个数组对象，第0个元素记录关联对象信息，
		 * 第1个元素记录关联对象记录的use角色的mastered对象匹配配置规范的iterated对象，
		 * 如果没有匹配对象，记录mastered对象。
		 * 
		 * @param partIfc
		 *            零部件值对象。
		 * @return
		 * @throws QMException
		 */
	    public Collection getUsesPartIfcs(QMPartIfc partIfc) throws QMException {
	        Collection links = null;
	        PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
	        if (partIfc.getBsoName().equals("GenericPart"))
	            links = pservice.navigateValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
	        else if (partIfc.getBsoName().equals("QMPart"))
	            links = pservice.navigateValueInfo(partIfc, "usedBy", "PartUsageLink", false);
	        if (links == null || links.size() == 0)
	            return new Vector();
	        return getUsesPartsFromLinks(links);
	    }
	    /**
	     * 根据规则过滤符合条件的零件
	     * @param QMPartIfc part
	     * @return QMPartIfc
	     * @throws QMException 
	     */
	    private Object filterPartWithRoute(QMPartIfc part) throws QMException
	    {
	    	if(part!=null){
	    		  /**卡车厂路线代码*/
	    		String kache = RemoteProperty.getProperty("com.faw_qm.gybomNotice.kacheCoding", "总;薄;厚(试制);厚(纵);厚（焊）;焊;涂;架（装）;架（漆）;架（纵）;架（钻）;架（横抛）;架（横）;饰;饰(长)");
	    		//String[] kacheVec = kache.split(";");
	    		//String[] returnVec = new String[8];
	    		String partNum = part.getPartNumber();
	    		String partName = part.getPartName();
	    		String[] routeVec = getRouteBranchs(part);
	    		String makeStr ="";
	    		String assembStr ="" ;
	    		if(routeVec!=null){
	    			makeStr = routeVec[0];
	    			assembStr = routeVec[1];
	    		}
	    		//条件1装置图：：零件号第5、6位为0，且不是标准件，制造路线含“用”，装配路线为空
	    		String subNumber = partNum.substring(4,6);
	    		String partType = part.getPartType().getDisplay();
	    		if(subNumber.equals("00")&&(!partType.equals("标准件"))&&makeStr.contains("用")&&assembStr.equals("")){
	    			return part;
	    		}
	    		//条件2：技术条件：零件名称含“技术条件”，制造路线含“用”，装配路线为空
	    		if(partName.contains("技术条件")&&makeStr.contains("用")&&assembStr.equals("")){
	    			return part;
	    		}
	    		//条件3:根据路线过滤的件：路线单位（包括制造路线和装配路线）均不含在卡车厂路线单位范围内的零部件
	    		//制造路线是多个节点的组合字符串，用'-'分隔::判断，只要包含就返回参数集合
	    		boolean assembleIsKache = kache.contains(assembStr);
	    		if(assembleIsKache){
	    			return routeVec;
	    		}else if(makeStr!=null&&!makeStr.equals("")){
	    			String[] temMake = makeStr.split("-");
	    			for(int j = 0;j<temMake.length;j++){
	    				String temStr = temMake[j];
	    				if(kache.contains(temStr)){
	    					return routeVec;
	    				}
	    			}
	    		}
	    		
	    		return routeVec;
	    	}
	   	    return part;
	    }  
	    /**
	     * 过滤生命周期状况，不满足的向前追溯
	     * 并以试制以及试制以后的状态才添加为过滤条件，如果制造视图最新版本零部件状态不满足条件
	     * 需追溯该件制造视图之前满足条件的版本。
	     * @param vec 零部件集合 或 结构件集合
	     * @return
	     * Vector
	     * @throws QMException 
	     */
	    private QMPartIfc filterLifeState(QMPartIfc part1) throws QMException
	    {
	        QMPartIfc part = null;
	        Object[] obj;
	        Vector result = new Vector();
	        if(part1 instanceof QMPartIfc)
	        {
	        	 part = part1;
	             // 追溯,先获取制造视图最新版本，如果符合条件则取出，如果不符合条件则获取工艺视图最新件。
	                part = getZZPartInfoByMasterBsoID(part1.getMasterBsoID());
	                if(part==null){
	                    PartConfigSpecIfc configSpecGY = getPartConfigSpecByViewName("工艺视图");
	                    part = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)part1.getMaster() , configSpecGY);

	                }
	      
	        }
	        return part;
	    } 
	    /**
		 * 根据零件获得最新已发布路线串内容（制造、装配）
		 * @param part
		 * @param link
		 * @return
		 * @throws QMException
		 */
		public static String[] getRouteBranchs(QMPartIfc part)
		throws QMException
		{
			try
			{

				//获得当前有效的路线关联
				TechnicsRouteService tr = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
				//执行搜索
				Collection c = getRoutesAndLinks(part);
				Iterator i = c.iterator();
				//这里只可能有一个
				ListRoutePartLinkInfo info = null;
				TechnicsRouteListInfo listInfo = null;
				//Vector objs = new Vector();
				String[] routeStr = new String[5];
				//如果是按照基线搜索则执行if语句否则执行else，仅仅是为了得到适合的关联
				if(c != null&&c.size()>0)
				{
					Object[] obj = (Object[])i.next();
					info = (ListRoutePartLinkInfo)obj[0];
					listInfo = (TechnicsRouteListInfo)obj[1];
				}else{
					return null;
				}
				
				
				HashMap map = (HashMap)tr.getRouteBranchs(info.getRouteID());

				if(map == null || map.size() == 0)
					return routeStr;
				Collection coll = getRouteBranches(map);
//				制造路线
				String routeAsString = "";
//				装配路线
				String routeAssemStr = "";
				for(Iterator routeiter = coll.iterator(); routeiter.hasNext();)
				{
					String routeAs[] = (String[])routeiter.next();
					String makeStr = routeAs[1];
					String assemStr = routeAs[2];
					String isMainRoute = routeAs[3];
					if(isMainRoute.equals("是"))
					{
						routeAsString = makeStr;
						routeAssemStr = assemStr;
						
					}
				}
				routeStr[0] = routeAsString;
				routeStr[1] = routeAssemStr;
				routeStr[2] = info.getParentPartNum();
				routeStr[3] = listInfo.getRouteListNumber();
				routeStr[4] = listInfo.getLifeCycleState().getDisplay();

				return routeStr;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new QMException(ex);
			}
		}
		   /**
	     *获取制造视图最新小版本，并且生命周期状态是试制、生产、生产准备、
	     *@param masterid
	     *@return QMPartInfo
	     *@throws QMException
	     */
	     private QMPartInfo getZZPartInfoByMasterBsoID(String masterid)
	     		throws QMException {
	     	try {
	     		PersistService pService = (PersistService) EJBServiceHelper.getPersistService();
	     		VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
	     		Collection col = vcservice.allVersionsOf((QMPartMasterIfc) pService.refreshInfo(masterid));
	     		if(col != null ){
	     			Iterator iter = col.iterator();
	     			while (iter.hasNext()) {
	         			QMPartInfo part = (QMPartInfo) iter.next();
	         			String viewName = part.getViewName().toString();
	         			 if(viewName.equals("工艺视图")){
	     	    			  return part;
	     	    	           
	         	 		 }
	     		    }
	     		
	     		}
	     	} catch (QMException ex) {
	     		ex.printStackTrace();
	     		throw ex;
	     	}
	     	return null;
	     }
	     /**
		     * 通过指定的筛选条件，将集合collection中的PartUsageLinkIfc对象对应的
		     * 符合筛选条件的Iterated部件进行筛选，如果不符合筛选条件则返回对应的Mastered主零部件。
		     * @param collection 是PartUsageLinkIfc对象的集合。
		     * @return 每个元素为一个数组.
		     * 数组的第一个元素为PartUsageLinkIfc对象，第二个元素为QMPartIfc对象，如果没有QMPartIfc对象，为关联的QMPartMasterIfc对象。
		     * @throws QMException
		     */
		    public Collection getUsesPartsFromLinks(Collection collection) throws QMException
		    {
		        Collection masterCollection = mastersOf(collection);
		   	    PartConfigSpecInfo partConfigSpecIfc = null;
		        ConfigService cservice = (ConfigService)EJBServiceHelper.getService("ConfigService");
		        if(partConfigSpecIfc == null)
		        {
		            partConfigSpecIfc = new PartConfigSpecInfo();
		            PartStandardConfigSpec pStandardcs = new PartStandardConfigSpec();
		            PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
		            QMQuery query = new QMQuery("ViewObject");
		            QueryCondition cond = new QueryCondition("viewName", "=", "工艺视图");
		            query.addCondition(cond);
		            Collection col = pservice.findValueInfo(query);
		            if(col == null || col.isEmpty())
		                throw new QMException("没有工艺视图！");
		            pStandardcs.setViewObjectIfc((ViewObjectIfc)col.iterator().next());
		            pStandardcs.setWorkingIncluded(true);
		            partConfigSpecIfc.setStandard(pStandardcs);
		            partConfigSpecIfc.setStandardActive(true);
		            partConfigSpecIfc.setBaselineActive(false);
		            partConfigSpecIfc.setEffectivityActive(false);
		        }
		        PartConfigSpecAssistant pcon = new PartConfigSpecAssistant(partConfigSpecIfc);
		        Collection iteratedCollection = cservice.filteredIterationsOf(masterCollection, pcon);
		        Collection allCollection = ConfigSpecHelper.recoverMissingMasters(masterCollection, iteratedCollection);
		        return ConfigSpecHelper.buildConfigResultFromLinks(collection, "uses", allCollection);
		    }
		    /**
		     * 根据视图名称返回零部件配置规范
		     * @param viewName String
		     * @throws QMException
		     * @return PartConfigSpecIfc
		     */
		    private static PartConfigSpecIfc getPartConfigSpecByViewName(String viewName) throws
		        QMException {
		      ViewService viewService = (ViewService) EJBServiceHelper.getService(
		          "ViewService");
		      ViewObjectIfc view = viewService.getView(viewName);
		      //若根据指定的视图名称没有获取到相应的值对象则返回当前配置规范
		      if (view == null) {
		        return (PartConfigSpecIfc) PartServiceRequest.
		            findPartConfigSpecIfc();
		      }
		      PartConfigSpecIfc partConfigSpecIfc = new PartConfigSpecInfo();
		      partConfigSpecIfc = new PartConfigSpecInfo();
		      partConfigSpecIfc.setStandardActive(true);
		      partConfigSpecIfc.setBaselineActive(false);
		      partConfigSpecIfc.setEffectivityActive(false);
		      PartStandardConfigSpec partStandardConfigSpec_en = new
		          PartStandardConfigSpec();
		      partStandardConfigSpec_en.setViewObjectIfc(view);
		      partStandardConfigSpec_en.setLifeCycleState(null);
		      partStandardConfigSpec_en.setWorkingIncluded(true);
		      partConfigSpecIfc.setStandard(partStandardConfigSpec_en);
		      return partConfigSpecIfc;
		    }
		    /**
		     * 根据零件获得link
		     * @param QMPartIfc part
		     * @return QMPartIfc
		     * @throws QMException 
		     */
			private static Collection getRoutesAndLinks(QMPartIfc part)
			throws QMException
			{
				
				PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
				QMQuery query = new QMQuery("ListRoutePartLink");
				int i = query.appendBso("TechnicsRouteList", true);
				QueryCondition qc = new QueryCondition("leftBsoID", "bsoID");
			    query.addCondition(0, i, qc);
				QueryCondition qc1 = new QueryCondition("partBranchID", QueryCondition.EQUAL, part.getBsoID());
				query.addAND();
				query.addCondition(qc1);
				QueryCondition qc2 = new QueryCondition("lifeCycleState" ,QueryCondition.EQUAL,"RELEASED");
				query.addAND();
				query.addCondition(i,qc2);
				query.addOrderBy("modifyTime",true);
		        Collection   col = (Collection)pservice.findValueInfo(query);

				return col;
			}
			   /**
			 * 获得最新已发布路线串内容（制造、装配）
			 * @param part
			 * @param link
			 * @return
			 * @throws QMException
			 */
			private static Collection getRouteBranches(HashMap map)
			throws QMException
			{
				Collection v = new Vector();
				Object branchs[] = map.keySet().toArray();
				for(int i = 0; i < branchs.length; i++)
				{
					PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
					TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo)branchs[i];
					
					String isMainRoute = "是";
					if(branchinfo.getMainRoute())
						isMainRoute = "是";
					else
						isMainRoute = "否";
					String makeStr = "";
					String assemStr = "";
					String tempcode = "";
					Object nodes[] = (Object[])map.get(branchinfo);
					Vector makeNodes = (Vector)nodes[0];
					RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1];
					if(makeNodes != null && makeNodes.size() > 0)
					{
						for(int m = 0; m < makeNodes.size(); m++)
			 			{
							RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
							String departid = node.getNodeDepartment();
							BaseValueIfc codeInfo = pservice.refreshInfo(departid);
							String makeCodeStr = "";
							if(codeInfo instanceof CodingIfc)

								makeCodeStr = ((CodingIfc)codeInfo).getShorten();
		
							if(codeInfo instanceof CodingClassificationIfc)
								makeCodeStr = ((CodingClassificationIfc)codeInfo).getClassSort();
							if(makeStr == "")
							{
								makeStr = makeStr + makeCodeStr;
								tempcode = makeCodeStr;
							} else
								if(!tempcode.equals(makeCodeStr))
								{
									makeStr = makeStr + "-" + makeCodeStr;
									tempcode = makeCodeStr;
								}
						}

					}
					if(asseNode != null)
					{
						String departid = asseNode.getNodeDepartment();
						BaseValueIfc codeInfo = pservice.refreshInfo(departid);
						String assemcode = "";
						if(codeInfo instanceof CodingIfc)

							assemcode = ((CodingIfc)codeInfo).getShorten();

						if(codeInfo instanceof CodingClassificationIfc)
							assemcode = ((CodingClassificationIfc)codeInfo).getClassSort();
						assemStr = assemcode;
					}
					if(makeStr == null || makeStr.equals(""))
						makeStr = "";
					if(assemStr == null || assemStr.equals(""))
						assemStr = "";
					String array[] = {
							String.valueOf(i + 1), makeStr, assemStr, isMainRoute
					};
					v.add(array);
				}

				return v;
			}
			  /**
		     * 返回指定的关联（master与iteration之间）集合中每个关联对象连接的
		     * mastered对象（指定角色master）的结果集
		     * @param links 关联类值对象集合
		     * @param role 角色名
		     * @exception com.faw_qm.config.exception.ConfigException
		     * @return 对应关联类值对象的Mastered对象集合。
		     */
		    public Collection mastersOf(Collection links) throws QMException
		    {

		        Vector vector = (Vector)links;// CR2
		        Vector resultVector = new Vector();

		        for(int i = 0;i < vector.size();i++)
		        {
		            PartUsageLinkIfc obj = (PartUsageLinkIfc)vector.elementAt(i);
		            String bsoID;
		            try
		            {
		                bsoID = obj.getRoleBsoID("uses");
		            }catch(QMException e)
		            {
		                throw new QMException(e, "根据角色名获得其BsoID时出错");
		            }
		            BaseValueIfc bsoObj;

		            PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
		            bsoObj = (BaseValueIfc)persistService.refreshInfo(bsoID, false);

		            if(!(bsoObj instanceof QMPartMasterIfc))
		            {
		                throw new QMException();
		            }// endif 如果collection中的元素左连接对象object不是MasteredIfc的
		            // 实例，抛出角色无效例外
		            resultVector.addElement(bsoObj);
		        }
		        return removeDuplicates(resultVector);
		    }
		    /**
		     * 将指定的结果集中重复的元素排除。
		     * @param collection 结果集
		     * @return Collection 排除了重复数据的集合 Collection中每一个元素为一Object数组 该Object数组中的第0个元素为一值对象
		     */
		    private Vector removeDuplicates(Collection collection) throws QMException
		    {
		        Hashtable hashtable = new Hashtable();
		        Vector resultVector = new Vector();
		        for(Iterator ite = collection.iterator();ite.hasNext();)
		        {
		            BaseValueInfo obj = (BaseValueInfo)ite.next();
		            String objBsoID = obj.getBsoID();
		            boolean flag = hashtable.containsKey(objBsoID);
		            if(flag == true)
		                continue;
		            hashtable.put(objBsoID, "");// 将BsoID做为标志放入Hash表
		            resultVector.addElement(obj);
		        }
		        return resultVector;
		    }
		    /**
		     * 获得专用件
		     * @return
		     * @throws QMException
		     */
		    public Vector getSpecPart()throws QMException
		    {
		Vector vec = new Vector();
		PersistService pservice = (PersistService) EJBServiceHelper
				.getService("PersistService");
		QMQuery qu = new QMQuery("StringValue");
		int j = qu.appendBso("StringDefinition", true);
		QueryCondition cod = new QueryCondition("definitionBsoID", "bsoID");
		qu.addCondition(0, j, cod);
		QueryCondition cod1 = new QueryCondition("name", QueryCondition.EQUAL,
				"specialPart");
		qu.addAND();
		qu.addCondition(j, cod1);
	
		QueryCondition cod2 = new QueryCondition("value", QueryCondition.EQUAL,
				"Y");
		qu.addAND();
		qu.addCondition(0,cod2);
//		qu.addOrderBy("modifyTime", true);
		qu.setVisiableResult(1);
		Collection collection = (Collection) pservice.findValueInfo(qu);
		if (collection.size() == 0) {
			return null;
		}
		for (Iterator iterator1 = collection.iterator(); iterator1.hasNext();) {
			StringValueInfo value = (StringValueInfo) iterator1.next();
			QMQuery query = new QMQuery("ListRoutePartLink");
			int i = query.appendBso("TechnicsRouteList", true);
			QueryCondition qc = new QueryCondition("leftBsoID", "bsoID");
			query.addCondition(0, i, qc);
			QueryCondition qc1 = new QueryCondition("partBranchID",
					QueryCondition.EQUAL, value.getIBAHolderBsoID());
			query.addAND();
			query.addCondition(qc1);  
			QueryCondition qc2 = new QueryCondition("lifeCycleState",
					QueryCondition.EQUAL, "RELEASED");
			query.addAND();
			query.addCondition(i, qc2);
			query.addOrderBy("modifyTime", true);
			Collection col = (Collection) pservice.findValueInfo(query);
			Iterator n = col.iterator();
			ListRoutePartLinkInfo info = null;
			TechnicsRouteListInfo listInfo = null;
			QMPartIfc part = (QMPartIfc) pservice.refreshInfo(value
					.getIBAHolderBsoID());
			String[] obj = new String[7];
			TechnicsRouteService tr = (TechnicsRouteService) EJBServiceHelper
					.getService("TechnicsRouteService");
			//如果是按照基线搜索则执行if语句否则执行else，仅仅是为了得到适合的关联
			if (col != null && col.size() > 0) {
				Object[] object = (Object[]) n.next();
				info = (ListRoutePartLinkInfo) object[0];
				listInfo = (TechnicsRouteListInfo) object[1];
				HashMap map = (HashMap) tr.getRouteBranchs(info.getRouteID());
				// 制造路线
				String routeAsString = "";
				// 装配路线
				String routeAssemStr = "";
				if (map != null) {
					Collection coll = getRouteBranches(map);
			
					for (Iterator routeiter = coll.iterator(); routeiter
							.hasNext();) {
						String routeAs[] = (String[]) routeiter.next();
						routeAsString = routeAs[1];
						routeAssemStr = routeAs[2];				
			
					}
				}
				obj[0] = "Y";
				obj[1] = part.getPartNumber();
				obj[2] = part.getPartName();
				obj[3] = part.getVersionValue();
				obj[4] = routeAsString;
				obj[5]=  routeAssemStr;
				obj[6]=  listInfo.getRouteListNumber();
				vec.add(obj);

			} else {
				obj[0] = "Y";
				obj[1] = part.getPartNumber();
				obj[2] = part.getPartName();
				obj[3] = part.getVersionValue();
				obj[4] = "";
				obj[5]=  "";
				obj[6]=  "";
				vec.add(obj);
			}
		  }


		return vec;
	}
	
	//CCBegin SS4
	public void setValidBom(BaseValueIfc primaryBusinessObject)
	{
		try
		{
			com.faw_qm.gybom.ejb.service.GYBomService bs = (com.faw_qm.gybom.ejb.service.GYBomService)EJBServiceHelper.getService("GYBomService");
			
    	PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
    	GYBomNoticeService bservice = (GYBomNoticeService) EJBServiceHelper.getService("GYBomNoticeService");
    	
			GYBomAdoptNoticeIfc ifc = (GYBomAdoptNoticeIfc)primaryBusinessObject;
			System.out.println("发布 工艺BOM发布单："+ifc.getAdoptnoticenumber()+"  关联整车："+ifc.getTopPart());
			//获取关联整车
			if(ifc.getTopPart()!=null)
			{
				QMPartIfc part = (QMPartIfc)ps.refreshInfo(ifc.getTopPart());
				if(part!=null)
				{
					System.out.println("发布 零部件"+part.getPartNumber());
					bs.setValidBom(part.getBsoID());
				}
			}
			//不取采用的件
			/*Collection col = bservice.getBomPartFromBomAdoptNotice(ifc);
    	if(col!=null&&col.size()>0)
    	{
    		System.out.println("col.size()=="+col.size());
    		for(Iterator itee= col.iterator();itee.hasNext();)
    		{
    			GYBomAdoptNoticePartLinkIfc links = (GYBomAdoptNoticePartLinkIfc)itee.next();
    			System.out.println("links.getAdoptBs()=="+links.getAdoptBs());
    			if(links.getAdoptBs().equals("采用"))
    			{
    				if(links.getPartID()!=null&&!links.getPartID().equals(""))
    				{
    					QMPartIfc linkPart = (QMPartIfc)ps.refreshInfo(links.getPartID());
    					System.out.println("linkPart=="+linkPart);
    					if(linkPart!=null)
    					{
    						System.out.println("发布 零部件"+linkPart.getPartNumber());
    						bs.setValidBom(linkPart.getPartNumber());
    					}
    				}
    			}
    		}
    	}*/
		}
		catch (QMException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//CCEnd SS4
	
	
	//CCBegin SS5
	public void multiChangeGYBom(BaseValueIfc primaryBusinessObject)
	{
		try
		{
			com.faw_qm.gybom.ejb.service.GYBomService bs = (com.faw_qm.gybom.ejb.service.GYBomService)EJBServiceHelper.getService("GYBomService");
			
    	PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
    	GYBomNoticeService bservice = (GYBomNoticeService) EJBServiceHelper.getService("GYBomNoticeService");
    	
			GYBomAdoptNoticeIfc ifc = (GYBomAdoptNoticeIfc)primaryBusinessObject;
			System.out.println("批量修改工艺BOM 发布单："+ifc.getAdoptnoticenumber()+"  关联整车："+ifc.getTopPart());
			//获取关联整车
			if(ifc.getTopPart()!=null)
			{
				QMPartIfc part = (QMPartIfc)ps.refreshInfo(ifc.getTopPart());
				if(part!=null)
				{
					System.out.println("批量修改工艺BOM  整车:"+part.getPartNumber());
					bs.multiChangeGYBom(part);
				}
			}
		}
		catch (QMException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//CCEnd SS5
}
