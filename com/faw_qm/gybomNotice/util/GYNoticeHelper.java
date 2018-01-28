 // SS1 �鿴BOM�汾���߼���������û���顢����ʾ����ɹ���Ӧ֧�ְ�����/�༶���� xianglx 2014-9-28
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
//SS1 �޸�TD8416 ������Ϣ��ʾ��ȫ����  ���� 2014-7-16
//SS2 �޸�TD8458 ���� 2014-7-18
//SS3 �޸�TD8409 ���� 2014-7-29
//SS4 ���ù���BOM��Ч��������׼�󣬽�����Чbomɾ������δ��Чbom���ó���Ч�� liunan 2016-9-5
//SS5 ����BOM�����޸ģ�������׼�������޸Ĺ���BOM�� liunan 2016-9-20
//SS6 �����������Ϣ maxiaotong 2017-09-11
public class GYNoticeHelper implements Serializable{
    /**
     * ���Կ���
     */
    public static final boolean VERBOSE = RemoteProperty.getProperty("com.faw_qm.GYNBomNotice.verbose", "true").equals("true");
    /**
     * ��װ��������󷽷�
     * @param serviceName ������
     * @param methodName ������
     * @param paraClass ������
     * @param paraObj ����ֵ
     * @return Object ������񷵻صĽ��
     * @throws QMException
     */
    public static Object requestServer(String serviceName, String methodName, Class[] paraClass, Object[] paraObj) throws QMException
    {

        RequestServer server = RequestServerFactory.getRequestServer();
        if(server == null)
        {
            throw new QMException( "��ȡԶ��RequestServerʧ�ܣ�");
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
     * �ͻ��˵��÷���˷����� �ж��˵�ǰ�ĵ������ڷ���˻��ǿͻ��ˣ�����Ƿ���˼����շ���˵ķ�ʽ���÷�������ǿͻ��˼����տͻ��˵ķ�ʽ���÷���
     * @param info ServiceRequestInfo ����������Ϣ��װ�ࡣ
     * @return Object ������񷵻صĽ����
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
     * �ݿͻ���ʾ����BOM
     * @param String bsoID ���õ�BsoID
     * @return Object ������񷵻صĽ����
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
    	if (jbbs.equals("����"))
    	 	col=bservice.getReleaseBomDJ(bomIfc);
    	else
    	 	col=bservice.getReleaseBom(bomIfc);
//CCEnd SS1
    	return col;
    	
    }
    
    /**
	 * ͨ�����õ�BsoID�����ֵ����
	 * @param id ���õ�BsoID
	 * @return ���õ�ֵ����
	 * @throws QMException
	 */
	public static GYBomAdoptNoticeInfo findNoticeInfoByID(String id) throws QMException {

		GYBomAdoptNoticeInfo noticeInfo = null;
		try {
			// ��ó־û�����
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
     * �ݿͻ���ʾ���շ�����
     * @param String bsoID ���õ�BsoID
     * @return Object ������񷵻صĽ����
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
    			if(links.getAdoptBs().equals("����")){
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
    				
    			}else if(links.getAdoptBs().equals("������")){
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
	 * ������������ID����ȡ����������
	 * @param lfcbsoID ��������ID
	 * @return ��ȡ����������
	 * @throws QMException
	 */
	public static String getLifeCycleNameByID(String lfcbsoID)
			throws QMException {
		String lfcName = "";
		LifeCycleTemplateInfo lfcInfo = null;
		try {
			// ��ó־û�����
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
	 * ͨ�����õ�BsoID��õ�ǰ����������������ơ�ҵ�����ĵ�ǰ״̬������״̬��
	 * 
	 * @param String
	 *            id ��������ID
	 * @return Vector ��ȡ����������
	 * @throws QMException
	 * 
	 */
	public static Vector getNoticeLifecycleInfo(String id) throws QMException {
		Vector infos = new Vector();
		LifeCycleManagedIfc lcmInfo = null;
		try {
			// ��ó־û�����
			PersistService persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			lcmInfo = (LifeCycleManagedIfc) persistService.refreshInfo(id);
		} catch (Exception e) {
			throw new QMException(e);
		}
		LifeCycleTemplateInfo lctInfo = null;
		Vector allstates = new Vector(); // allstate��ŵ�ǰ������������ڵ�������������״̬
		try {
			// ��ó־û�����
			LifeCycleService lifecycleService = (LifeCycleService) EJBServiceHelper
					.getService("LifeCycleService");
			lctInfo = lifecycleService.getLifeCycleTemplate(lcmInfo);
			String lifecyclename = lctInfo.getLifeCycleName();
			Vector temp1 = new Vector();
			temp1.addElement(lifecyclename);
			infos.addElement(temp1);
			String currentState = lcmInfo.getLifeCycleState().getDisplay(); // ��õ�ǰ��������״̬
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
	 * ͨ�����õ�BsoID��õ�ǰ�����������Ϣ
	 * @param String bsoId ���õ�BsoID
	 * @return String[] ������Ϣ
	 * @throws QMException
	 * 
	 */
	public static String[] getProcessInfomation(String bsoID) throws QMException {

		//CCBegin SS6
//		String[] returnStr = new String[10];
		String[] returnStr = new String[12];
		//CCEnd SS6
		returnStr[0] = "����";
		returnStr[2] = "У��";
		returnStr[4] = "���";
		returnStr[6] = "��׼";
		returnStr[8] = "��Ŀ����";
		returnStr[1] = "";
		returnStr[3] = "";
		returnStr[5] = "";
		returnStr[7] = "";
		returnStr[9] = "";
		//CCBegin SS6
		returnStr[10] = "������";
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
					System.out.println("�����====="+name);
					//CCBegin SS1
					if(name.equals("����BOM")){
						returnStr[1] = paticipants;
					}else if(name.equals("У��")){
						returnStr[3] = paticipants;
					}else if(name.equals("���")){
						returnStr[5] = paticipants;
					}else if(name.equals("��׼")){
						returnStr[7] = paticipants;
					}else if(name.equals("ά��ר�ü�")){
						returnStr[9] = paticipants;
					}
					//CCBegin SS6
					else if(name.equals("����BOM")){
						
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
	 * ͨ�����õ�BsoID�ͽ���id��õ�ǰ����������������ơ�ҵ�����ĵ�ǰ״̬������״̬�͸�״̬��Ӧ����Ĳ����ߡ�
	 * ������getNoticeLifecycleInfo()�������޸ģ�����˽���id���Ա�����ز����ߡ�
	 * 
	 * @param String
	 *            id ����ID
	 * @param String
	 *            wordid ����ID
	 * @return Vector
	 * @throws QMException
	 */
	public static Vector getNoticeLifecycleInfo(String id, String wordid)
			throws QMException {
		Vector infos = new Vector();
		try {
			// ��ó־û�����
			PersistService persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			HashMap rolemap = new HashMap();
			Vector paticipantsvec = new Vector();
			WfProcessIfc wfprocess = (WfProcessIfc) persistService
					.refreshInfo(wordid);
			// ������������������Ӧ�Ľ�ɫ�Ͳ����ߣ����浽HashMap�У�����key�ǽ�ɫ����value�ǲ����ߡ�
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
								System.out.println("�����߲����û�Ҳ�����û��飡");
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
	 * �õ����õ���ǰ����������״̬
	 * 
	 * @param String
	 *            id ����ID
	 * @return Vector ״̬����
	 * @throws QMException
	 */
	public static String getCurrentState(String id) throws QMException {
		String state = "";
		LifeCycleManagedIfc lcmInfo = null;
		try {
			// ��ó־û�����
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
	 * ͨ������ID������������е���Ϣ ���Ȼ�����еĻ��
	 * ������Vector1��Vector1��ÿ��Ԫ�ض���Vector2��Vector2�е�һ��Ԫ���ǻ����vector
	 * ���ڶ���Ԫ�������в�����vector��������Ԫ����һ��Vector3��Vector3����������б���������ֵ��
	 * 
	 * @param String
	 *            id ����ID
	 * @return Vector ������Ϣ����
	 * @throws QMException
	 */

	public static Vector getWorkflowInfo(String id) throws QMException {
		Vector activityInfos = new Vector();
		PersistService persistService = null;
		try {
			// ��ó־û�����
			persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
		} catch (Exception e) {
			throw new QMException(e);
		}
		try {
			WfProcessInfo wfprocessInfo = (WfProcessInfo) persistService
					.refreshInfo(id); // �õ�����Info
			IteratorVector itvc = getAllActivities(id);
			while (itvc.hasNext()) {
				WfActivityIfc waInfo = (WfActivityIfc) itvc.next();
				
				Vector variables = getAllVariablesByWfActivity(waInfo);
				String activityID = waInfo.getBsoID();
				String paticipants = getAllPaticipantOfActivity(activityID);
				String VoteInformation = getVoteInformation(waInfo);
				Vector activityinfo = new Vector();
				Vector wanamevec = new Vector();
				wanamevec.addElement(waInfo.getName()); // �����
				Vector paticipantsvec = new Vector();
				paticipantsvec.addElement(paticipants); // ������
				Vector voteVec = new Vector();
				voteVec.addElement(VoteInformation); // ͶƱ��Ϣ
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
	 * ���ظ����������е����й��ڵĵĻ
	 * 
	 * @param process
	 *            �����Ĺ���������
	 * @return Iterator �������ֵ���󼯺�
	 * @throws QMException
	 */
	public static IteratorVector getAllActivities(String bsoID)
			throws QMException {
		// �������Ϊ��,���ؿն���
		IteratorVector itvc = new IteratorVector();
		try {
			// ���ɲ�ѯ,�����ѯ����
			QMQuery query = new QMQuery("WfAssignedActivity");
			query.addCondition(new QueryCondition("parentProcessBsoID", "=",
					bsoID));
			// ��ӶԽ�������򣬰��������У����������ϣ����������¡�
			query.addOrderBy("modifyTime", false);
			// ��ѯ,���ؽ��

			itvc.addAll(((PersistService) EJBServiceHelper.getPersistService())
					.findValueInfo(query));
		} catch (QMException e) {
			throw new QMException(e);
		}
		return itvc;
	}

	/**
	 * ������еķ���ģ��ı���������ֵ
	 * @param workitemid
	 *            �������ID
	 * @return WfVariable[]�а������пɼ��ķ����ı���.
	 * @throws QMException
	 */
	private static Vector getAllVariablesByWfActivity(WfActivityIfc activityifc)
			throws QMException {
		// ��÷��������б���
		Vector variables = new Vector();
		WfVariable awfvariable[] = activityifc.getContext().getVariableList();
		String wfactivityname = activityifc.getName();
		// ��÷���ģ��
		String assignedactivitytemplateid = activityifc.getTemplateID();
		PersistService persistService = null;
		try {
			// ��ó־û�����
			persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
		} catch (Exception e) {
			throw new QMException(e);
		}
		WfAssignedActivityTemplateIfc wfassignedactivitytemplateifc = (WfAssignedActivityTemplateIfc) persistService
				.refreshInfo(assignedactivitytemplateid);
		// �������������б���
		for (int i = 0; i < awfvariable.length; i++) {
			String wfvariablename = awfvariable[i].getName();
			WfVariableInfo wfvariableinfo = wfassignedactivitytemplateifc
					.getContextSignature().getVariableInfo(wfvariablename);
			// ��÷���ģ��ı����ǿɼ����Ҳ���primaryBusinessObject��self
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
	 * ���ݻ�õ����еĹ����ÿ��������������߼��Ǹû�����в�����
	 * @param String    activityID �ID
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
							+ "���ʱ�䣺" + workitemIfc.getModifyTime() + "\n";
				} else {
					ActorInfo userinfo = (ActorInfo) persistService
							.refreshInfo(ownerid);
					paticipants = paticipants + userinfo.getUsersDesc() + ";"
							+ "δ��ɡ�\n";
				}
			}
		} catch (QMException qme) {
			throw new QMException(qme);
		}
		return paticipants;
	}

	/**
	 * �õ��ͶƱ��Ϣ
	 * 
	 * @param WfActivityIfc
	 *            waInfo �
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
						vote += vec.elementAt(i) + "��";
					}
				}
				vote = vote.endsWith("��") ? vote
						.substring(0, vote.length() - 1) : vote;
				if (vote.length() > 0) {
					userAndVote += user + ":" + vote + ";";
				}
			}
		}
		return userAndVote;
	}
	/**
	 * ����BOM���ϱ���
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
	 * ��������������
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
				if (part.getLifeCycleState().getDisplay().equals("����")) {
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
	 * ��ȡ��Ŀ
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
	 * �����ܳɱ���
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
    	//��һ��Ԫ�أ�QMPartIfc;�ڶ�Ԫ�أ�ʹ������String������Ԫ�أ�·���������String[]
    	vec = getParts(part,vec,view,quantity);

		return vec;
	}
	/**
	 * �����ܳ��㲿��
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
	       // ��һ��������getUsesPartIfcs����������Collection�����CollectionΪ�գ����߳���Ϊ0,�׳��쳣
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
	       //�ڶ���: ��collection�е�ÿ��Ԫ�ؽ���ѭ������Ҫ��������һ���ݹ�ķ���productStructure()
	       else
	       {
	           Object[] tempArray = new Object[collection.size()];
	           collection.toArray(tempArray);
	           //�Ȱ�tempArray�е�����Ԫ�ض��ŵ�resultVector����
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
		 * �����������������
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

				if (part.getLifeCycleState().getDisplay().equals("����")&&!part.getPartType().toString().equalsIgnoreCase("Standard")) {
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
	    * ��Ų鿴����BOM����Ų鿴����BOM��
	    * @param String bsoID
	    * @return Collection
	    */
		public Collection getReleaseBom(QMPartIfc partIfc)throws QMException {
			PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
//	    	StandardPartService sp = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
	    	PartUsageLinkIfc partlink=new PartUsageLinkInfo();

	    	Vector vec=new Vector();
	    	//��һ��Ԫ�أ�QMPartIfc;�ڶ�Ԫ�أ�ʹ������String������Ԫ�أ�·���������String[]
	    	vec = getAllParts(partIfc,partlink,vec);
	    	 return vec;
		  
		}
		 /**
	     * ����ָ�����ù淶�����ָ��������ʹ�ýṹ��
	     * ���ؼ���Collection��ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
	     * @param partIfc �㲿��ֵ����
	     * @param configSpecIfc �㲿�����ù淶��
	     * @throws QMException
	     * @see QMPartInfo
	     * @see PartConfigSpecInfo
	     * @return Collection ����Collection��ÿ��Ԫ����һ���������:<br>
	     */

	    private Vector getAllParts(QMPartIfc partifc_old,PartUsageLinkIfc partlink,Vector result)
	    throws QMException
	    {
	
	     // float quan=partlink.getQuantity();
	      Object[] obj = null;
	       // ��һ��������getUsesPartIfcs����������Collection�����CollectionΪ�գ����߳���Ϊ0,�׳��쳣
	       Collection collection = getUsesPartIfcs(partifc_old);
	       if((collection.size() == 0) || (collection == null))
	       {

	           return result;
	       }
	       //�ڶ���: ��collection�е�ÿ��Ԫ�ؽ���ѭ������Ҫ��������һ���ݹ�ķ���productStructure()
	       else
	       {
	           Object[] tempArray = new Object[collection.size()];
	           collection.toArray(tempArray);
	          
	          // PartUsageLinkIfc partUsageLinkIfc1=null;
	           //�Ȱ�tempArray�е�����Ԫ�ض��ŵ�resultVector����
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
							// ���ݹ����ж�·�ߴ�����:��������ΪString���飬��������ʾ���������ΪQMPartIfc��������ʾ���
							Object routeObj = filterPartWithRoute(part2);

							if (routeObj instanceof QMPartIfc) {// ����ʾ�����

							} else if (routeObj instanceof String[]) {// ��ʾ�����
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
		 * ����ָ�����ù淶�����ָ��������ʹ�ýṹ�� ���ؼ���Collection��ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
		 * ��1��Ԫ�ؼ�¼���������¼��use��ɫ��mastered����ƥ�����ù淶��iterated����
		 * ���û��ƥ����󣬼�¼mastered����
		 * 
		 * @param partIfc
		 *            �㲿��ֵ����
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
	     * ���ݹ�����˷������������
	     * @param QMPartIfc part
	     * @return QMPartIfc
	     * @throws QMException 
	     */
	    private Object filterPartWithRoute(QMPartIfc part) throws QMException
	    {
	    	if(part!=null){
	    		  /**������·�ߴ���*/
	    		String kache = RemoteProperty.getProperty("com.faw_qm.gybomNotice.kacheCoding", "��;��;��(����);��(��);�񣨺���;��;Ϳ;�ܣ�װ��;�ܣ��ᣩ;�ܣ��ݣ�;�ܣ��꣩;�ܣ����ף�;�ܣ��ᣩ;��;��(��)");
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
	    		//����1װ��ͼ��������ŵ�5��6λΪ0���Ҳ��Ǳ�׼��������·�ߺ����á���װ��·��Ϊ��
	    		String subNumber = partNum.substring(4,6);
	    		String partType = part.getPartType().getDisplay();
	    		if(subNumber.equals("00")&&(!partType.equals("��׼��"))&&makeStr.contains("��")&&assembStr.equals("")){
	    			return part;
	    		}
	    		//����2������������������ƺ�������������������·�ߺ����á���װ��·��Ϊ��
	    		if(partName.contains("��������")&&makeStr.contains("��")&&assembStr.equals("")){
	    			return part;
	    		}
	    		//����3:����·�߹��˵ļ���·�ߵ�λ����������·�ߺ�װ��·�ߣ��������ڿ�����·�ߵ�λ��Χ�ڵ��㲿��
	    		//����·���Ƕ���ڵ������ַ�������'-'�ָ�::�жϣ�ֻҪ�����ͷ��ز�������
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
	     * ������������״�������������ǰ׷��
	     * ���������Լ������Ժ��״̬�����Ϊ�������������������ͼ���°汾�㲿��״̬����������
	     * ��׷�ݸü�������ͼ֮ǰ���������İ汾��
	     * @param vec �㲿������ �� �ṹ������
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
	             // ׷��,�Ȼ�ȡ������ͼ���°汾���������������ȡ��������������������ȡ������ͼ���¼���
	                part = getZZPartInfoByMasterBsoID(part1.getMasterBsoID());
	                if(part==null){
	                    PartConfigSpecIfc configSpecGY = getPartConfigSpecByViewName("������ͼ");
	                    part = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)part1.getMaster() , configSpecGY);

	                }
	      
	        }
	        return part;
	    } 
	    /**
		 * ���������������ѷ���·�ߴ����ݣ����졢װ�䣩
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

				//��õ�ǰ��Ч��·�߹���
				TechnicsRouteService tr = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
				//ִ������
				Collection c = getRoutesAndLinks(part);
				Iterator i = c.iterator();
				//����ֻ������һ��
				ListRoutePartLinkInfo info = null;
				TechnicsRouteListInfo listInfo = null;
				//Vector objs = new Vector();
				String[] routeStr = new String[5];
				//����ǰ��ջ���������ִ��if������ִ��else��������Ϊ�˵õ��ʺϵĹ���
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
//				����·��
				String routeAsString = "";
//				װ��·��
				String routeAssemStr = "";
				for(Iterator routeiter = coll.iterator(); routeiter.hasNext();)
				{
					String routeAs[] = (String[])routeiter.next();
					String makeStr = routeAs[1];
					String assemStr = routeAs[2];
					String isMainRoute = routeAs[3];
					if(isMainRoute.equals("��"))
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
	     *��ȡ������ͼ����С�汾��������������״̬�����ơ�����������׼����
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
	         			 if(viewName.equals("������ͼ")){
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
		     * ͨ��ָ����ɸѡ������������collection�е�PartUsageLinkIfc�����Ӧ��
		     * ����ɸѡ������Iterated��������ɸѡ�����������ɸѡ�����򷵻ض�Ӧ��Mastered���㲿����
		     * @param collection ��PartUsageLinkIfc����ļ��ϡ�
		     * @return ÿ��Ԫ��Ϊһ������.
		     * ����ĵ�һ��Ԫ��ΪPartUsageLinkIfc���󣬵ڶ���Ԫ��ΪQMPartIfc�������û��QMPartIfc����Ϊ������QMPartMasterIfc����
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
		            QueryCondition cond = new QueryCondition("viewName", "=", "������ͼ");
		            query.addCondition(cond);
		            Collection col = pservice.findValueInfo(query);
		            if(col == null || col.isEmpty())
		                throw new QMException("û�й�����ͼ��");
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
		     * ������ͼ���Ʒ����㲿�����ù淶
		     * @param viewName String
		     * @throws QMException
		     * @return PartConfigSpecIfc
		     */
		    private static PartConfigSpecIfc getPartConfigSpecByViewName(String viewName) throws
		        QMException {
		      ViewService viewService = (ViewService) EJBServiceHelper.getService(
		          "ViewService");
		      ViewObjectIfc view = viewService.getView(viewName);
		      //������ָ������ͼ����û�л�ȡ����Ӧ��ֵ�����򷵻ص�ǰ���ù淶
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
		     * ����������link
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
			 * ��������ѷ���·�ߴ����ݣ����졢װ�䣩
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
					
					String isMainRoute = "��";
					if(branchinfo.getMainRoute())
						isMainRoute = "��";
					else
						isMainRoute = "��";
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
		     * ����ָ���Ĺ�����master��iteration֮�䣩������ÿ�������������ӵ�
		     * mastered����ָ����ɫmaster���Ľ����
		     * @param links ������ֵ���󼯺�
		     * @param role ��ɫ��
		     * @exception com.faw_qm.config.exception.ConfigException
		     * @return ��Ӧ������ֵ�����Mastered���󼯺ϡ�
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
		                throw new QMException(e, "���ݽ�ɫ�������BsoIDʱ����");
		            }
		            BaseValueIfc bsoObj;

		            PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
		            bsoObj = (BaseValueIfc)persistService.refreshInfo(bsoID, false);

		            if(!(bsoObj instanceof QMPartMasterIfc))
		            {
		                throw new QMException();
		            }// endif ���collection�е�Ԫ�������Ӷ���object����MasteredIfc��
		            // ʵ�����׳���ɫ��Ч����
		            resultVector.addElement(bsoObj);
		        }
		        return removeDuplicates(resultVector);
		    }
		    /**
		     * ��ָ���Ľ�������ظ���Ԫ���ų���
		     * @param collection �����
		     * @return Collection �ų����ظ����ݵļ��� Collection��ÿһ��Ԫ��ΪһObject���� ��Object�����еĵ�0��Ԫ��Ϊһֵ����
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
		            hashtable.put(objBsoID, "");// ��BsoID��Ϊ��־����Hash��
		            resultVector.addElement(obj);
		        }
		        return resultVector;
		    }
		    /**
		     * ���ר�ü�
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
			//����ǰ��ջ���������ִ��if������ִ��else��������Ϊ�˵õ��ʺϵĹ���
			if (col != null && col.size() > 0) {
				Object[] object = (Object[]) n.next();
				info = (ListRoutePartLinkInfo) object[0];
				listInfo = (TechnicsRouteListInfo) object[1];
				HashMap map = (HashMap) tr.getRouteBranchs(info.getRouteID());
				// ����·��
				String routeAsString = "";
				// װ��·��
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
			System.out.println("���� ����BOM��������"+ifc.getAdoptnoticenumber()+"  ����������"+ifc.getTopPart());
			//��ȡ��������
			if(ifc.getTopPart()!=null)
			{
				QMPartIfc part = (QMPartIfc)ps.refreshInfo(ifc.getTopPart());
				if(part!=null)
				{
					System.out.println("���� �㲿��"+part.getPartNumber());
					bs.setValidBom(part.getBsoID());
				}
			}
			//��ȡ���õļ�
			/*Collection col = bservice.getBomPartFromBomAdoptNotice(ifc);
    	if(col!=null&&col.size()>0)
    	{
    		System.out.println("col.size()=="+col.size());
    		for(Iterator itee= col.iterator();itee.hasNext();)
    		{
    			GYBomAdoptNoticePartLinkIfc links = (GYBomAdoptNoticePartLinkIfc)itee.next();
    			System.out.println("links.getAdoptBs()=="+links.getAdoptBs());
    			if(links.getAdoptBs().equals("����"))
    			{
    				if(links.getPartID()!=null&&!links.getPartID().equals(""))
    				{
    					QMPartIfc linkPart = (QMPartIfc)ps.refreshInfo(links.getPartID());
    					System.out.println("linkPart=="+linkPart);
    					if(linkPart!=null)
    					{
    						System.out.println("���� �㲿��"+linkPart.getPartNumber());
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
			System.out.println("�����޸Ĺ���BOM ��������"+ifc.getAdoptnoticenumber()+"  ����������"+ifc.getTopPart());
			//��ȡ��������
			if(ifc.getTopPart()!=null)
			{
				QMPartIfc part = (QMPartIfc)ps.refreshInfo(ifc.getTopPart());
				if(part!=null)
				{
					System.out.println("�����޸Ĺ���BOM  ����:"+part.getPartNumber());
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
