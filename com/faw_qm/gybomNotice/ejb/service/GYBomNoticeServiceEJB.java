/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �鿴����BOmʱ�����������������λ���жϳ���  TD8319 ���� 2014-7-16
 * SS2 TD8290 ����BOM����������  ���� 2014-7-17
 * SS3 �������BOM�������������� ���� 2014-7-28
 * SS4 �޸Ĳ鿴����bom������ɢ���嵥�߼�  ���� 2014-7-28
 * SS5 �޸�TD8462 ��������BOM����  ���� 2014-7-31
 * SS6 ����BOM������������BOM������㲿����ѯ��Ҫ������д�汾 xianglx 2014-8-28
 * SS7 jdbc���ӷŵ�ȫ�ֱ����У�����ٶ������� xianglx 2014-8-28
 * SS8 Bom���� xianglx 2014-9-10
 * SS9 ����BOM����������ӽ����Ʊ�����������Ӧ���ݵĲ��úͲ����ü� xianglx 2014-8-28
 * SS10 �鿴BOM�汾���߼���������û���顢����ʾ����ɹ���Ӧ֧�ְ�����/�༶���� xianglx 2014-9-28
 * SS11 BOM�����еİ汾��ȡ������ԭ�汾����û�к�ȡ��PDM�㲿���İ汾�� xianglx 2014-10-20
 * SS12 �鿴����BOM�й��������е�û����·����������һ��·�� xianglx 2014-11-25
 * SS13 ����������·�ߺ�װ��·�߹�����������ȷ xianglx 2014-11-25  
 * SS14 ���ȽϿ���������·�ߺ�װ��·�ߣ�ֱ���� jfmaterialsplit���Ƿ���ڸ��㲿�� xianglx 2014-11-25
 * SS15 ���ݹ����ж��Ƿ���Ҫ���汾 ������ 2014-11-30
 * SS16 ��ȡ����BOM�ж������汾ĩβ�Ƿ��"+",�����ȡPDM�����part���粻��ȡ�����汾��Ӧ��part xianglx 2014-12-05
 * SS17 ��Ʊ��������ʱ���ж��㲿��Ϊ������ͼ�µ� xianglx 2014-12-9
 * SS18 ͨ��JDBC����·�߲���ʱ�ж������ڹ������ϼ��£��������ϼ��²�Ӧ��� xianglx 2014-12-9
 * SS19 ���·�߷�֧ʱ��ʱ�䵹���� xianglx 2014-12-18
 * SS20 �鿴����BOMʱ��������Ϊ�߼��ܳɲ��Ƚ�erp��jfmaterialsplit���Ƿ���ڶ�ֱ����ʾ��ȡ�����޸��߼��ܳ����� xianglx 2014-12-24
 * SS21 ��������߼��ܳ���������������Ұ�������������·�ߣ��ڲ鿴����bomʱҲ��ʾ���� pante 2015-01-04
 * SS22 ��ӱ�ʶ���룬Ϊ�Զ����ʹ�� ������ 2015-01-28
 * SS23 �ж��߼��ܳ�Ҫ���������С��5λ�� xianglx 2015-02-10
 * SS24 ���������ʱ�޸����°汾��ʾ;רҵ������ʱ�ҷ���ԭ�汾,��û�з���ԭ�汾����PDM�汾,���޸����ʾ xianglx 2015-02-10
 * SS25 �����û�������鿴����BOM���༶����ʱ ���������ݡ� liuzhicheng 2016-06-28
 * SS26 �Զ�����BOM������ lishu 2017-5-12
 * SS27 ����BOM���������ʱ���޸Ĳ��ü����Ҳ�֧��ģ����ѯ�����⡣ maxiaotong 2017-09-15
 * SS28 ����㲿���Ƿ���ڣ������Ƿ��гɶ�·�� ����� 2017-12-30
 * SS29 �����жϵ�ǰ�û��ǳɶ�����ţ�Ȼ���ȡ����·�ߡ����·�ߡ� liunan 2018-1-9
*/

package com.faw_qm.gybomNotice.ejb.service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.faw_qm.acl.ejb.entity.QMPermission;
import com.faw_qm.acl.ejb.service.AccessControlService;
import com.faw_qm.bomNotice.ejb.service.BomNoticeService;
import com.faw_qm.bomNotice.model.BomAdoptNoticeIfc;
import com.faw_qm.bomNotice.model.BomAdoptNoticeInfo;
import com.faw_qm.bomNotice.model.BomAdoptNoticePartLinkIfc;
import com.faw_qm.bomNotice.model.BomAdoptNoticePartLinkInfo;
import com.faw_qm.bomNotice.model.BomChangeNoticeIfc;
import com.faw_qm.bomNotice.model.BomChangeNoticeInfo;
import com.faw_qm.bomNotice.model.BomChangeNoticePartLinkIfc;
import com.faw_qm.bomNotice.model.BomChangeNoticePartLinkInfo;
import com.faw_qm.bomNotice.util.NoticeHelper;
import com.faw_qm.cderp.util.PartHelper;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.config.util.ConfigSpecHelper;
import com.faw_qm.consadoptnotice.model.UniteIdentifyInfo;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticePartLinkIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticePartLinkInfo;
import com.faw_qm.gybomNotice.model.GYInvoiceIfc;
import com.faw_qm.gybomNotice.model.GYInvoiceInfo;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.model.StringValueInfo;
import com.faw_qm.jferp.util.PublishData;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.model.LifeCycleTemplateMasterInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.lock.ejb.service.LockService;
import com.faw_qm.lock.model.LockIfc;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.RouteNodeIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.users.ejb.entity.User;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.exception.VersionControlException;
import com.faw_qm.version.util.VersionControlHelper;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.exception.WorkInProgressException;
import com.faw_qm.wip.model.CheckoutLinkInfo;
import com.faw_qm.workflow.engine.ejb.service.WfEngineHelper;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.workflow.engine.util.enumerate.WfState;
//CCEnd SS16
//CCBegin SS29
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.doc.util.DocServiceHelper;
//CCEnd SS29

 
/**
 * <p>Title: ����������ˡ�</p> <p>Description: </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ����
 * @version 1.0 
 */
public class GYBomNoticeServiceEJB extends BaseServiceImp
{
    private static final long serialVersionUID = 1L; 
    static boolean verbose = NoticeHelper.VERBOSE;

    private Connection conn = null;

    /**
     * ��÷�����
     * @return String ������
     */
    public String getServiceName()
    {
        return "GYBomNoticeService";
    }

    

    /**
     * ����������
     * @param Object[] data ���ݼ���
     * @return Object[]
     */

	public Object[] createGYBomAdoptNotice(Object[] data) throws QMException
    {
        Object[] obj = new Object[2];
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
        FolderService fservice = (FolderService) EJBServiceHelper.getService("FolderService");
		LifeCycleService lifeCycle = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");
		SessionService sservice = (SessionService)EJBServiceHelper.getService("SessionService");
        try
        {
            GYBomAdoptNoticeInfo newInfo = new GYBomAdoptNoticeInfo();
            if(data == null)
            {
                throw new QMException("createGYBomAdoptNotice Data is NULL");
            }
            newInfo.setCreator(sservice.getCurUserID());
            newInfo.setOwner(sservice.getCurUserID());
            
            String publishTypeStr = (String)data[0];
            newInfo.setPublishType(publishTypeStr);
            
            String classificationStr = (String)data[1];
            newInfo.setClassification(classificationStr);
            
            String adoptnoticenameStr = (String)data[2];
            newInfo.setAdoptnoticename(adoptnoticenameStr);
            
            BaseValueInfo designDoc = (BaseValueInfo)data[5];
            if(designDoc!=null){
            	newInfo.setDesignDoc(designDoc.getBsoID());
            }
            BaseValueInfo jfBomNoticeIfc = (BaseValueInfo)data[6];
            if(jfBomNoticeIfc!=null){
            	newInfo.setJfBomnotice(jfBomNoticeIfc.getBsoID());
            }
            BaseValueInfo zxDoc = (BaseValueInfo)data[7];
            if(zxDoc!=null){
            	newInfo.setZxAdoptNotice(zxDoc.getBsoID());
            } 
            BaseValueInfo cxPart = (BaseValueInfo)data[8];
            if(cxPart!=null){
            	newInfo.setTopPart(cxPart.getBsoID());
            } 
            String remarkStr = (String)data[9];
	        	newInfo.setConsdesc(remarkStr);
	        //�Զ����
            String adoptnoticenumber = getBomAdoptNoticeNumber("ZC");
            newInfo.setAdoptnoticenumber(adoptnoticenumber);
            
            
            String folderStr = (String)data[4];
            newInfo.setLocation(folderStr);
            fservice.assignFolder((FolderEntryIfc) newInfo, folderStr);
            
            String lifeCycleTemplate = (String)data[3];
            lifeCycle.setLifeCycle(newInfo, lifeCycle.getLifeCycleTemplate(lifeCycleTemplate));
            newInfo = (GYBomAdoptNoticeInfo)service.saveValueInfo(newInfo);
            obj[0] = newInfo;
        }catch(QMException e)
        {
            setRollbackOnly();
            throw new QMException(e);
        }

        return obj;
    }
	
	  /**
     * �������ܡ���ʻ�ҷ�����
     * @param Object[] data ���ݼ���
     * @return Object[]
     */

	public Object[] createFrameAndBodyBomAdoptNotice(Object[] data) throws QMException
    {
        Object[] obj = new Object[2];
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
        FolderService fservice = (FolderService) EJBServiceHelper.getService("FolderService");
		LifeCycleService lifeCycle = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");
		SessionService sservice = (SessionService)EJBServiceHelper.getService("SessionService");
        try
        {
            GYBomAdoptNoticeInfo newInfo = new GYBomAdoptNoticeInfo();
            if(data == null)
            {
                throw new QMException("createFrameAndBodyBomAdoptNotice Data is NULL");
            }
            newInfo.setCreator(sservice.getCurUserID());
            newInfo.setOwner(sservice.getCurUserID());
            
            String publishTypeStr = (String)data[0];
            newInfo.setPublishType(publishTypeStr);
            
            String classificationStr = (String)data[1];
            newInfo.setClassification(classificationStr);
            
            String adoptnoticenameStr = (String)data[2];
            newInfo.setAdoptnoticename(adoptnoticenameStr);
            //����������
            BaseValueInfo parentNotice = (BaseValueInfo)data[5];
            if(parentNotice!=null){
            	newInfo.setParentNotice(parentNotice.getBsoID());
            }
           
            
            BaseValueInfo cxPart = (BaseValueInfo)data[6];
            if(cxPart!=null){
            	newInfo.setTopPart(cxPart.getBsoID());
            } 
            String remarkStr = (String)data[7];
	        	newInfo.setConsdesc(remarkStr);
//CCBegin SS9
            BaseValueInfo jfNoticeIfc = (BaseValueInfo)data[8];
            if(jfNoticeIfc!=null){
            	newInfo.setJfBomnotice(jfNoticeIfc.getBsoID());
            }
//CCBegin SS9
		       
	        //�Զ����	        	
	        if(publishTypeStr.startsWith("����")){
	            String adoptnoticenumber = getBomAdoptNoticeNumber("CJ");
	            newInfo.setAdoptnoticenumber(adoptnoticenumber);
	        }else if (publishTypeStr.startsWith("��ʻ��")){
	            String adoptnoticenumber = getBomAdoptNoticeNumber("NS");
	            newInfo.setAdoptnoticenumber(adoptnoticenumber);
	        }
            String folderStr = (String)data[4];
            newInfo.setLocation(folderStr);
            fservice.assignFolder((FolderEntryIfc) newInfo, folderStr);
            
            String lifeCycleTemplate = (String)data[3];
            lifeCycle.setLifeCycle(newInfo, lifeCycle.getLifeCycleTemplate(lifeCycleTemplate));
            newInfo = (GYBomAdoptNoticeInfo)service.saveValueInfo(newInfo);
            obj[0] = newInfo;
        }catch(QMException e)
        {
            setRollbackOnly();
            throw new QMException(e);
        }

        return obj;
    }
	 /**
     * ���³��ܡ���ʻ�ҷ�����
     * @param Object[] data ���ݼ���
     * @param GYBomAdoptNoticeIfc ifc ����������
     * @return Object[]
     */

	public Object[] updateFrameAndBodyBomAdoptNotice(Object[] data,GYBomAdoptNoticeIfc ifc) throws QMException
    {
        Object[] obj = new Object[2];
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
		SessionService sessionservice = (SessionService)EJBServiceHelper.getService("SessionService");
        try
        {
        	if(data!=null){
        		String publishTypeStr = (String)data[0];
        		ifc.setPublishType(publishTypeStr);
                String classificationStr = (String)data[1];
                ifc.setClassification(classificationStr);
                String adoptnoticenameStr = (String)data[2];
                ifc.setAdoptnoticename(adoptnoticenameStr);
               //����������
                 BaseValueInfo parentNotice = (BaseValueInfo)data[5];
                 if(parentNotice!=null){
                	 ifc.setParentNotice(parentNotice.getBsoID());
                 }
                  BaseValueInfo cxPart = (BaseValueInfo)data[6];
                  if(cxPart!=null){
                	  ifc.setTopPart(cxPart.getBsoID());
                  } 
                  String remarkStr = (String)data[7];
                  ifc.setConsdesc(remarkStr);
 //CCBegin SS9
            BaseValueInfo jfNoticeIfc = (BaseValueInfo)data[8];
            if(jfNoticeIfc!=null){
            	ifc.setJfBomnotice(jfNoticeIfc.getBsoID());
            }
//CCBegin SS9
             

                ifc = (GYBomAdoptNoticeInfo)service.updateValueInfo(ifc);
                //����֮�����
                unlock(ifc,sessionservice.getCurUserID());
                
        	}
        	obj[0] = ifc;
        }catch(QMException e)
        {
            unlock(ifc,sessionservice.getCurUserID());
            setRollbackOnly();
            throw new QMException(e);
        }
        return obj;
    }
	/**
     * ��������BOM����б����
     * @param Object[] data ���ݼ���
     * @return Object[]
     */

	public Vector createGYBomZCPartLink(Vector linkVec,GYBomAdoptNoticeIfc ifc) throws QMException
    {
        Vector vec = new Vector();
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
      
       try
        {
    	   if(linkVec!=null&&linkVec.size()>0){
    		   String noticeID = ifc.getBsoID();
    		   for(Iterator ite = linkVec.iterator();ite.hasNext();){
    			   GYBomAdoptNoticePartLinkIfc link = (GYBomAdoptNoticePartLinkIfc)ite.next();
    			   link.setNoticeID(noticeID);
    			   link = (GYBomAdoptNoticePartLinkIfc)service.saveValueInfo(link);
    			   vec.add(link);
    		   }
    	   }
        }catch(QMException e)
        {
            setRollbackOnly();
            throw new QMException(e);
        }
        return vec;
    }
	
	  /**
     * ��������BOM����б����
     * @param Object[] data ���ݼ���
     * @return Object[]
     */

	public Vector updateGYBomZCPartLink(Vector linkVec,GYBomAdoptNoticeIfc ifc) throws QMException
    {
        Vector vec = new Vector();
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
        Collection col = getBomPartFromBomAdoptNotice(ifc);
        if(col!=null&&col.size()>0){
        	for(Iterator ites = col.iterator();ites.hasNext();){
        		GYBomAdoptNoticePartLinkIfc links = (GYBomAdoptNoticePartLinkIfc)ites.next();
        		service.deleteValueInfo(links);
        	}
        }
       try
        {
    	   if(linkVec!=null&&linkVec.size()>0){
    		   String noticeID = ifc.getBsoID();
    		   for(Iterator ite = linkVec.iterator();ite.hasNext();){
    			   GYBomAdoptNoticePartLinkIfc link = (GYBomAdoptNoticePartLinkIfc)ite.next();
    			   link.setNoticeID(noticeID);
    			   link = (GYBomAdoptNoticePartLinkIfc)service.saveValueInfo(link);
    			   vec.add(link);
    		   }
    	   }
        }catch(QMException e)
        {
            setRollbackOnly();
            throw new QMException(e);
        }
        return vec;
    } 
	 /**
     * ��������������
     * @param Object[] data ���ݼ���
     * @param GYBomAdoptNoticeIfc ifc ����������
     * @return Object[]
     */

	public Object[] updateGYBomAdoptNotice(Object[] data,GYBomAdoptNoticeIfc ifc) throws QMException
    {
        Object[] obj = new Object[2];
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
		SessionService sessionservice = (SessionService)EJBServiceHelper.getService("SessionService");
        try
        {
        	if(data!=null){
        		   
                String classificationStr = (String)data[1];
                ifc.setClassification(classificationStr);
        		  BaseValueInfo designDoc = (BaseValueInfo)data[5];
                  if(designDoc!=null){
                	  ifc.setDesignDoc(designDoc.getBsoID());
                  }
                  BaseValueInfo jfBomNoticeIfc = (BaseValueInfo)data[6];
                  if(jfBomNoticeIfc!=null){
                	  ifc.setJfBomnotice(jfBomNoticeIfc.getBsoID());
                  }
                  BaseValueInfo zxDoc = (BaseValueInfo)data[7];
                  if(zxDoc!=null){
                	  ifc.setZxAdoptNotice(zxDoc.getBsoID());
                  } 
                  BaseValueInfo cxPart = (BaseValueInfo)data[8];
                  if(cxPart!=null){
                	  ifc.setTopPart(cxPart.getBsoID());
                  } 
                  String remarkStr = (String)data[9];
                  ifc.setConsdesc(remarkStr);
                  
                  //CCBegin SS26
              String publishTypeStr = (String)data[0];
              ifc.setPublishType(publishTypeStr);
              
              String adoptnoticenameStr = (String)data[2];
              ifc.setAdoptnoticename(adoptnoticenameStr);
                  //CCEnd SS26
              

                ifc = (GYBomAdoptNoticeInfo)service.updateValueInfo(ifc);
                //����֮�����
                unlock(ifc,sessionservice.getCurUserID());
                
        	}
        	obj[0] = ifc;
        }catch(QMException e)
        {
            unlock(ifc,sessionservice.getCurUserID());
            setRollbackOnly();
            throw new QMException(e);
        }
        return obj;
    }
   
	  /**
     * �����Ӳ��õ�
     * @param Object[] data ���ݼ���
     * @return Object[]
     */

	public Object[] createSubBomAdoptNotice(Object[] data) throws QMException
    {
        Object[] obj = new Object[2];
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
        FolderService fservice = (FolderService) EJBServiceHelper.getService("FolderService");
		LifeCycleService lifeCycle = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");
		SessionService sservice = (SessionService)EJBServiceHelper.getService("SessionService");
	      String lifeCycleTemplate1 =RemoteProperty.getProperty("com.faw_qm.bomNotice.lifeCycleTemplate1", "���õ���У���ܱ�");
	        String lifeCycleTemplate2 =RemoteProperty.getProperty("com.faw_qm.bomNotice.lifeCycleTemplate2", "���õ���У���ܱ�����");
       try
        {
            BomAdoptNoticeInfo newInfo = new BomAdoptNoticeInfo();
            if(data == null)
            {
                throw new QMException("createSubBomAdoptNotice Data is NULL");
            }
            newInfo.setCreator(sservice.getCurUserID());
            newInfo.setOwner(sservice.getCurUserID());
            
            //����
            String classification = (String)data[0];
            if(classification!=null){
            	newInfo.setClassification(classification);
            }
            //�Ӳ��õ�δ���á���Դ������
            String sourceStr = "";
            newInfo.setSource(sourceStr);
           //�Ӳ��õ�����
            String adoptnoticenameStr = (String)data[1];
            newInfo.setAdoptnoticename(adoptnoticenameStr);
            
            //���ϼ�
            String folderStr = (String)data[2];
            newInfo.setLocation(folderStr);
            fservice.assignFolder((FolderEntryIfc) newInfo, folderStr);
            
            
            //����ĵ�
            BaseValueInfo doc = (BaseValueInfo)data[3];
            if(doc!=null){
            	newInfo.setDesignDoc(doc.getBsoID());
            }
            //����
            String workShop = (String)data[4];
            if(workShop!=null){
            	newInfo.setDepartment(workShop);
            }
                        
            //��������
            BaseValueInfo oldPart = (BaseValueInfo)data[5];
            if(oldPart!=null){
            	newInfo.setOldAuto(oldPart.getBsoID());
            }
            //˵��
            String remarkStr = (String)data[6];
            newInfo.setConsdesc(remarkStr);
            
            
            //�����õ�����
            BomAdoptNoticeInfo parentInfo = (BomAdoptNoticeInfo)data[9];
            if(parentInfo!=null){
                //�Ӳ��õ��ĸ���
                newInfo.setParentNotice(parentInfo.getBsoID());	
            }

           //�Զ���ţ������������һ��ѡ�񸸲��õ���һ�ֲ�ѡ�񸸲��õ�
            if(parentInfo!=null){
            	  //�Զ���ţ����ݸ����õ������Զ����
                int sortNumber = this.getNextSortNumber("BomAdoptNotice", parentInfo.getAdoptnoticenumber(), false);
                char[] sortNum = String.valueOf(sortNumber).toCharArray();
                char[] arry  = {'0','0','0'};
                System.arraycopy(sortNum, 0, arry, arry.length-sortNum.length, sortNum.length);
                String resNum = new String(arry);
                newInfo.setAdoptnoticenumber(parentInfo.getAdoptnoticenumber()+"-"+resNum);	
            }else{
                //����ҳ�洫�ݵĳ�����Ϣ����ó�����룬����������õ��Զ����
                HashMap<String,String> workShopMap = (HashMap<String,String>)data[7];
                String workShopID = (String)workShopMap.get(workShop);            
                String adoptnoticenumber = getBomAdoptNoticeNumber(workShopID);
                int sortNumber = this.getNextSortNumber("BomAdoptNotice", adoptnoticenumber, false);
                char[] sortNum = String.valueOf(sortNumber).toCharArray();
                char[] arry  = {'0','0','0'};
                System.arraycopy(sortNum, 0, arry, arry.length-sortNum.length, sortNum.length);
                String resNum = new String(arry);
                newInfo.setAdoptnoticenumber(adoptnoticenumber+"-"+resNum);
            }
            //�³���
            BaseValueInfo newPart = (BaseValueInfo)data[10];
            if(newPart!=null){
            	newInfo.setNewAuto(newPart.getBsoID());
            }

           //��������
            newInfo.setPublishType("�Ӳ���֪ͨ��");
            //��������
            if(doc==null){
                lifeCycle.setLifeCycle(newInfo, lifeCycle.getLifeCycleTemplate(lifeCycleTemplate2));
            }else{
                lifeCycle.setLifeCycle(newInfo, lifeCycle.getLifeCycleTemplate(lifeCycleTemplate1));
            }

            newInfo = (BomAdoptNoticeInfo)service.saveValueInfo(newInfo);

            //������õ����������
            Vector noticePartLinkVec = (Vector)data[8];
            Vector newLinkVec = new Vector();
            //�������
			if(noticePartLinkVec!=null&&noticePartLinkVec.size()>0){
				for(Iterator ites =noticePartLinkVec.iterator();ites.hasNext(); ){
					BomAdoptNoticePartLinkIfc links = (BomAdoptNoticePartLinkInfo)ites.next();
					links.setNoticeID(newInfo.getBsoID());
					links = (BomAdoptNoticePartLinkIfc)service.saveValueInfo(links);
					newLinkVec.add(links);
				}
				
			}

            obj[0] = newInfo;
            obj[1] = newLinkVec;
        }catch(QMException e)
        {
            setRollbackOnly();
            throw new QMException(e);
        }
        return obj;
    }
	
	  /**
     * �����Ӳ��õ�
     * @param Object[] data ���ݼ���
     * @param BomAdoptNoticeIfc ifc ���µĶ���
     * @return Object[]
     */

	public Object[] updateSubBomAdoptNotice(Object[] data,BomAdoptNoticeIfc ifc) throws QMException
    {
        Object[] obj = new Object[2];
        Vector returnVec = new Vector();
        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
        FolderService fservice = (FolderService) EJBServiceHelper.getService("FolderService");
		LifeCycleService lifeCycle = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");
		SessionService sservice = (SessionService)EJBServiceHelper.getService("SessionService");
		try
        {

            if(data != null)
            {
            	 //����
                String classification = (String)data[0];
                if(classification!=null){
                	ifc.setClassification(classification);
                }

               //�Ӳ��õ�����
                String adoptnoticenameStr = (String)data[1];
                ifc.setAdoptnoticename(adoptnoticenameStr);
                

                
                
                //����ĵ�
                BaseValueInfo doc = (BaseValueInfo)data[3];
                if(doc!=null){
                	ifc.setDesignDoc(doc.getBsoID());
                }

                            
                //��������
                BaseValueInfo oldPart = (BaseValueInfo)data[5];
                if(oldPart!=null){
                	ifc.setOldAuto(oldPart.getBsoID());
                }
                //˵��
                String remarkStr = (String)data[6];
                ifc.setConsdesc(remarkStr);
                

                //�³���
                BaseValueInfo newPart = (BaseValueInfo)data[10];
                if(newPart!=null){
                	ifc.setNewAuto(newPart.getBsoID());
                }

               
                ifc = (BomAdoptNoticeInfo)service.updateValueInfo(ifc);

                

                //ɾ��ԭ�й���
                Collection existLink = getPartsFromBomSubAdoptNotice(ifc.getBsoID());
                if(existLink!=null&&existLink.size()>0){
                	for(Iterator ite=existLink.iterator();ite.hasNext();){
                		BomAdoptNoticePartLinkIfc elinks = (BomAdoptNoticePartLinkIfc)ite.next();
                		service.deleteValueInfo(elinks);

                	}
                }
                
                //�¹���
                Vector noticePartLinkVec = (Vector)data[8];
                //�������
    			if(noticePartLinkVec!=null&&noticePartLinkVec.size()>0){
    				for(Iterator ites =noticePartLinkVec.iterator();ites.hasNext(); ){
    					BomAdoptNoticePartLinkIfc links = (BomAdoptNoticePartLinkInfo)ites.next();
    					String key = links.getPartID()+links.getAdoptBs();
    					links.setNoticeID(ifc.getBsoID());
    					links = (BomAdoptNoticePartLinkIfc)service.saveValueInfo(links);
    					returnVec.add(links);
    				}
    				
    			}
    		
                unlock(ifc,sservice.getCurUserID());
            }
            
           
            obj[0] = ifc;
            obj[1] = returnVec;
        }catch(QMException e)
        {
            unlock(ifc,sservice.getCurUserID());
            setRollbackOnly();
            throw new QMException(e);
        }
        return obj;
    }
	

    /**
     * ���ָ���㲿������Ϣ�ڵ�ǰ�û����ù淶�µ���һ���㲿������Ϣ�ļ���
     * @param QMPartIfc ָ���㲿��
     * @param HashMap map
     * @return ��һ���㲿������Ϣ�ļ���
     * @throws QMException 
     */
    public HashMap getSubPartMastersByConfigSpec(QMPartIfc part,HashMap map) throws QMException
    {
  
        try
        {
        	if(map==null){
        		map = new HashMap();
        	}
            StandardPartService partService = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
            // ��õ�ǰ�û����ù淶
            PartConfigSpecIfc configSpec = partService.findPartConfigSpecIfc();
            // ���master�ڵ�ǰ���ù淶�µ����
            if(part == null)
            {
                return null;
            }
            // ���part��ʹ�õ���һ�����������Ϣ�ļ���
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
          
             if(part.getBsoName().equals("QMPart"))
            {
                Collection c2 = pservice.navigateValueInfo(part, "usedBy", "PartUsageLink");
                if(c2!=null&&c2.size()>0){
                	Vector reVec = new Vector() ;
                	for(Iterator ite = c2.iterator();ite.hasNext();){
                		QMPartMasterIfc subMaster = (QMPartMasterIfc)ite.next();
                		QMPartIfc temPartIfc = partService.getPartByConfigSpec(subMaster, configSpec);
                		reVec.add(temPartIfc);
            
                	}
                	map.put(part.getBsoID(), reVec);
                	if(reVec!=null&&reVec.size()>0){
                		for(Iterator ites = reVec.iterator();ites.hasNext();){
                			QMPartIfc temPartIfc1 = (QMPartIfc)ites.next();
                			getSubPartMastersByConfigSpec(temPartIfc1,map);
                		}
                	}
                }
                
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }
	
	 /**
    * ���ݲ��õ�����ѯʹ�������������ڲ鿴BOM��
    * @param BomAdoptNoticeIfc bomIfc
    * @return Collection
    */
	public Collection getBomAdoptUsagePart(BomAdoptNoticeIfc bomIfc)throws QMException {
		PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
		Vector vec = new Vector();
		if (bomIfc != null) {
			if (bomIfc.getPublishType().equals("�Ӳ���֪ͨ��")) {
				Collection col1 = getPartsFromBomSubAdoptNotice(bomIfc.getBsoID());
				vec.add(col1);
			} else if (bomIfc.getPublishType().equals("����֪ͨ��")) {
				Collection col2 = getSubGYBomAdoptNotice((GYBomAdoptNoticeInfo)bomIfc);
				if(col2!=null&&col2.size()>0){
					for(Iterator ite = col2.iterator();ite.hasNext();){
						BomAdoptNoticeIfc noticeIfc = (BomAdoptNoticeIfc)ite.next();
						Collection col3 = getPartsFromBomSubAdoptNotice(noticeIfc.getBsoID());	
						vec.add(col3);
					}
				}
			}
		}
		return vec;

	}
	 /**
	    * ��Ų鿴����BOM����Ų鿴����BOM��
	    * @param String bsoID
	    * @return Collection
	    */
		public Collection getReleaseBom(QMPartIfc partIfc)throws QMException {
			PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
	    	StandardPartService sp = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
	    	PartUsageLinkIfc partlink=new PartUsageLinkInfo();

	    	Vector vec=new Vector();
	    	//��һ��Ԫ�أ�QMPartIfc;�ڶ�Ԫ�أ�ʹ������String������Ԫ�أ�·���������String[]
//CCBegin SS7
    		  try {
    		conn = getConnection();
//CCEnd SS7
	    	vec = getAllParts(partIfc,partlink,vec,0,false);
//CCBegin SS7
		if(conn!=null){
			conn.close();	
		}//CCBegin SS25
			} catch (Exception e) {
				e.printStackTrace();
			}//CCEnd SS25
//CCEnd SS7
	    	 return vec;
		  
		}
//CCBegin SS16
//���ݵ����汾����������汾����ø������Ӧ�����汾�����С�汾����
    private QMPartIfc getLatestIteration(QMPartIfc iterated,String proVersion)
            throws
            VersionControlException, QMException
    {
    //������Դ�ļ�������
    	String RESOURCE ="com.faw_qm.version.util.VersionResource";
        // �������Ϊ�գ��׳��쳣
        if (iterated == null)
        {
            java.lang.Object aobj[] =
                    {
                    "VersionControlService.getLastestIteration",
                    "iterated"};
            throw new VersionControlException(RESOURCE, "0", aobj);
        }
        QMPartIfc iterated1 = null;
        //�����ѯ
        QMQuery query = new QMQuery("QMPart");
        //�����ѯ��������ָ��masterΪ����
        query.addCondition(VersionControlHelper.getCondForMaster(iterated.
                getMasterBsoID()));
        //�������ѯ����
        query.addAND();
        //�����ѯ�������������汾��֦Ϊ����
        query.addCondition(new QueryCondition("versionID", "=", proVersion));
        //�������ѯ����
        query.addAND();
        //�����ѯ�����������°汾Ϊ����
        query.addCondition(VersionControlHelper.getCondForLatest(true));
        //�õ��־û��������
        PersistService service = (PersistService) EJBServiceHelper.
                                 getPersistService();
        //ִ�в�ѯ
        Collection queryresult = service.findValueInfo(query);
        if (queryresult.size() > 1)
        {
            java.lang.Object aobj[] =
                    {
                    new Integer(queryresult.size())
            };
            throw new VersionControlException(RESOURCE, "10", null);
        }
        else
        {
            //�ӽ�����õ����
            if (queryresult.size() == 0)
            {
            		iterated1=iterated;
            }
            else{
                iterated1 = (QMPartIfc) (queryresult.iterator().next());
            }
            
        }
        return iterated1;
    }
//CCEnd SS16
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
	    private Vector getAllParts(QMPartIfc partifc_old,PartUsageLinkIfc partlink,Vector result,int cc,boolean isDanJi)
	    throws QMException
	    {
	    	Object[] obj = null;
	    	//CCBegin SS4
	    	PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
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

	    		//�Ȱ�tempArray�е�����Ԫ�ض��ŵ�resultVector����
	    		//CCBegin SS10
	    		cc=cc+1;
	    		//CCEnd SS10
	    		for (int i = 0; i < tempArray.length; i++)
	    		{
	    			QMPartIfc partIfc1 = null;
	    			Object[] tempObj = (Object[]) tempArray[i];
	    			if(tempObj[1] instanceof QMPartIfc&& tempObj[0] instanceof PartUsageLinkIfc)
	    			{
	    				//CCBegin SS10
	    				//	                   obj = new Object[7];
	    				obj = new Object[11];
	    				//CCEnd SS10
	    				partIfc1 = (QMPartIfc) tempObj[1];
	    				partlink = (PartUsageLinkIfc) tempObj[0];
	    				//CCBegin SS16
	    				String proVersion=null;
	    				if(partlink!=null){
	    					proVersion=partlink.getProVersion();
	    				}
	    				if (proVersion!=null && proVersion.length()>0){
	    					if (proVersion.endsWith("+")){
	    						proVersion=proVersion.substring(0,proVersion.length()-1);
	    					}else{
	    						partIfc1=getLatestIteration(partIfc1,proVersion);
	    					}

	    				}
	    				//CCEnd SS16
	    				if(partIfc1!=null)
	    				{
	    					obj[0] = partIfc1;
	    					//��ȡ����Ѿ�����IBAֵ�������
	    					Collection ibaCol = getPartIBA(partIfc1,"�����","virtualPart");
	    					if(ibaCol!=null&&ibaCol.size()>0){
	    						Iterator ibaIte = ibaCol.iterator();
	    						StringValueIfc existValue = (StringValueIfc)ibaIte.next();
	    						obj[1] = existValue.getValue();
	    					}else{
	    						obj[1] = "";
	    					}
	    					//��ȡ����Ѿ�����IBAֵ:ר�ü�
	    					Collection spCol = getPartIBA(partIfc1,"ר�ü�","specialPart");
	    					if(spCol!=null&&spCol.size()>0){
	    						Iterator ibaItes = spCol.iterator();
	    						StringValueIfc existValue2 = (StringValueIfc)ibaItes.next();
	    						obj[3] = existValue2.getValue();
	    					}else{
	    						obj[3] = "";
	    					}
	    					if(partlink!=null){
	    						obj[4] = Float.toString(partlink.getQuantity());
	    						String parentPartBso = partlink.getRightBsoID();
	    						QMPartIfc parentPart = (QMPartIfc)pservice.refreshInfo(parentPartBso);
	    						obj[5] = parentPart.getPartNumber();

	    					}else{
	    						obj[4] = "0.0";
	    						obj[5] = "";
	    					}
	    					obj[6] = partIfc1.getLifeCycleState().getDisplay();

	    					//CCEnd SS4
	    					//CCBegin SS10

	    					//CCBegin SS16
	    					//	                String proVersion=null;
	    					//CCEnd SS16
	    					obj[7]="";
	    					if(partlink!=null){
	    						obj[7]=partlink.getSubUnitNumber();
	    						proVersion=partlink.getProVersion();
	    					}else{
	    						obj[7]="";
	    					}
	    					Collection svCol = getPartIBA(partIfc1,"����Դ�汾","sourceVersion");
	    					String sourceVersion=null;
	    					obj[8]="";
	    					if(svCol!=null&&svCol.size()>0){
	    						Iterator ibaItes = svCol.iterator();
	    						StringValueIfc existValue2 = (StringValueIfc)ibaItes.next();
	    						sourceVersion = existValue2.getValue();
	    					}
	    					if((partIfc1.getPartNumber().startsWith("CQ")) || (partIfc1.getPartNumber().startsWith("Q")) || (partIfc1.getPartNumber().startsWith("T")) || (partIfc1.getPartNumber().startsWith("1000940")) || (partIfc1.getPartNumber().startsWith("5000990"))) //��׼����ȥ�汾
	    					{
	    						obj[8]=partIfc1.getPartNumber();
	    					}
	    					else if(isPartVersion(partIfc1.getPartNumber())){
	    						obj[8]=partIfc1.getPartNumber();
	    					}
	    					else if (proVersion!=null && proVersion.length()>0)//�������汾ȡ�����汾
	    					{
	    						obj[8]=partIfc1.getPartNumber()+"/"+proVersion;
	    					}
	    					else if (sourceVersion!=null && sourceVersion.length()>0)//û�������汾��ԭ�汾ȡԭ�汾
	    					{
	    						if (sourceVersion.indexOf(".")>0)
	    						{
	    							sourceVersion=sourceVersion.substring(0, sourceVersion.indexOf("."));
	    						}
	    						obj[8]=partIfc1.getPartNumber()+"/"+sourceVersion;
	    					}else{//����ȡ����汾
	    						obj[8]=partIfc1.getPartNumber()+"/"+partIfc1.getVersionID();
	    					}
	    					obj[9]=cc;
	    					//CCEnd SS10
	    					//CCBegin SS11
	    					obj[10]=partIfc1.getVersionID();
	    					if(sourceVersion!=null && sourceVersion.length()>0)
	    						if (sourceVersion.indexOf(".")>0)
	    						{
	    							sourceVersion=sourceVersion.substring(0, sourceVersion.indexOf("."));
	    						}
	    					obj[10]=sourceVersion;
	    					//CCEnd SS11

	    					//���ݹ����ж�·�ߴ�����:��������ΪString���飬��������ʾ���������ΪQMPartIfc��������ʾ���
	    					//CCBegin SS14
	    					//			               Object routeObj = filterPartWithRoute(partIfc1);
	    					Object routeObj = filterPartWithRoute(partIfc1,(String)obj[8]);
	    					//CCEnd SS14
	    					if(routeObj instanceof QMPartIfc){//����ʾ�����

	    					}else if(routeObj instanceof String[]){//��ʾ�����
	    						String[] strV = (String[])routeObj;
	    						obj[2] = routeObj;
	    						result.add(obj);
	    						//CCBegin SS10
	    						//				               if(partIfc1 != null&&partlink!=null)
	    						//				                   getAllParts(partIfc1, partlink,
	    						//				                            result,cc,isDanJi); 
	    						if (isDanJi){//����ֻչ��һ�㣬�����߼��ܳ�չ��
	    							if (isLogical(partIfc1)){
	    								if(partIfc1 != null&&partlink!=null)
	    									getAllParts(partIfc1, partlink,
	    											result,cc,isDanJi);
	    							}

	    						}
	    						else{//�༶��ȫ��չ��
	    							if(partIfc1 != null&&partlink!=null)
	    								getAllParts(partIfc1, partlink,
	    										result,cc,isDanJi);
	    						} 
	    						//CCEnd SS10
	    					}
	    				}
	    			}
	    		}
	    	}
	    	return result;
	    }
	    /**
	     * ���ݹ�����˷������������
	     * @param QMPartIfc part
	     * @return QMPartIfc
	     * @throws QMException 
	     */
	    		//CCBegin SS14
//	    private Object filterPartWithRoute(QMPartIfc part) throws QMException
	    private Object filterPartWithRoute(QMPartIfc part,String partNumberVer) throws QMException
	    		//CCEnd SS14
	    {
	    	if(part!=null){
	    		/**������·�ߴ���*/
	    		String kache = RemoteProperty.getProperty("com.faw_qm.gybomNotice.kacheCoding", "��;��;��(����);��(��);�񣨺���;��;Ϳ;�ܣ�װ��;�ܣ��ᣩ;�ܣ��ݣ�;�ܣ��꣩;�ܣ����ף�;�ܣ��ᣩ;��;��(��)");

	    		String partNum = part.getPartNumber();
	    		String partName = part.getPartName();
	    		//CCBegin SS4
	    		PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
	    		String makeStr ="";
	    		String assembStr ="" ;
	    		String routeListBsoID = "";
	    		String[] partBso = new String[1];
	    		partBso[0] = part.getBsoID();
	    		Collection cols;
	    		try {
	    			cols = getRouteByJDBC(partBso);
	    			if(cols!=null&&cols.size()>0){
	    				for(Iterator ite = cols.iterator();ite.hasNext();){
	    					Vector vec = (Vector)ite.next();
	    					if(vec!=null&&vec.size()>0){
	    						for(int n = 0 ; n <vec.size();n++){
	    							String[] routeStrs = (String[])vec.get(n);
	    							String routeStr = routeStrs[0];
	    							//CCBegin SS12
	    							if (n==0)
	    							{
	    								String[] routest = routeStr.split("@");
	    								makeStr = routest[0];
	    								assembStr = routest[1];
	    							}
	    							//CCEnd SS12
	    							String mainStr =routeStrs[1] ;
	    							routeListBsoID = routeStrs[2];
	    							//�ж��Ƿ�����Ҫ·�ߣ�ֻ��¼��Ҫ·��
	    							if(mainStr.equals("1")){
	    								String[] routest = routeStr.split("@");
	    								makeStr = routest[0];
	    								assembStr = routest[1];
	    								break;
	    							}

	    						}
	    					}

	    				}
	    			}
	    		} catch (SQLException e) {

	    			e.printStackTrace();
	    		}
	    		TechnicsRouteListInfo listInfo = null;
	    		if(!routeListBsoID.equals("")){
	    			listInfo = (TechnicsRouteListInfo)pservice.refreshInfo(routeListBsoID);
	    		}
	    		String[] routeVec = new String[3];
	    		routeVec[0] = makeStr;
	    		routeVec[1] = assembStr;
	    		if(listInfo!=null){
	    			routeVec[2] = listInfo.getRouteListNumber();
	    		}else{
	    			routeVec[2] = "";
	    		}

	    		//String[] routeVec = getRouteBranchs(part);
	    		//CCEnd SS4


	    		//����1װ��ͼ��������ŵ�5��6λΪ0���Ҳ��Ǳ�׼��������·�ߺ����á���װ��·��Ϊ��
	    		//CCBegin SS1
	    		if(partNum.length()>=6){

	    			String subNumber = partNum.substring(4,6);
	    			String partType = part.getPartType().getDisplay();
	    			if(subNumber.equals("00")&&(!partType.equals("��׼��"))&&makeStr.contains("��")&&assembStr.equals("")){
	    				//System.out.println("partNumberVer1="+partNumberVer);
	    				return part;
	    			}
	    		}
	    		//CCEnd SS1
	    		//����2������������������ƺ�������������������·�ߺ����á���װ��·��Ϊ��
	    		if(partName.contains("��������")&&makeStr.contains("��")&&assembStr.equals("")){
	    			//System.out.println("partNumberVer2="+partNumberVer);
	    			return part;
	    		}
	    		//CCBegin SS4
	    		//�����������������·�ߡ�װ��·�߶��ǿգ�����ʾ
	    		if(makeStr.equals("")&&assembStr.equals("")){
	    			//System.out.println("partNumberVe3="+partNumberVer);
	    			return part;
	    		}
	    		//CCEnd SS4
	    		//����3:����·�߹��˵ļ���·�ߵ�λ����������·�ߺ�װ��·�ߣ��������ڿ�����·�ߵ�λ��Χ�ڵ��㲿��
	    		//����·���Ƕ���ڵ������ַ�������'-'�ָ�::�жϣ�ֻҪ�����ͷ��ز�������
	    		//CCBegin SS13
	    		//	    		boolean assembleIsKache = kache.contains(assembStr);
	    		//	    		if(assembleIsKache){
	    		//	    			return routeVec;
	    		//	    		}else if(makeStr!=null&&!makeStr.equals("")){
	    		//	    			String[] temMake = makeStr.split("-");
	    		//	    			for(int j = 0;j<temMake.length;j++){
	    		//	    				String temStr = temMake[j];
	    		//	    				if(kache.contains(temStr)){
	    		//	    					return routeVec;
	    		//	    				}
	    		//	    			}
	    		//	    		}
	    		//CCBegin SS14
	    		//CCBegin SS21
//	    		�����������߼��ܳɹ������ж�����Ƿ����������·�ߣ�������������
	    		//�ж�װ��·���ַ������Ƿ������������
//	    		System.out.println("part============"+part.getPartNumber());
//	    		System.out.println("luoji============"+isLogical(part));
	    		
	    		if(isLogical(part)){
	    			String[] kaches = kache.split(";");
	    			for(int i = 0;i<kaches.length;i++){
//	    				System.out.println("ZPkaches============"+kaches[i]);
	    			}
	    			Collection assembList = new ArrayList();
	    			String[] temAssemb = assembStr.split("/");
	    			for(int j = 0;j<temAssemb.length;j++){
	    				String temStr = temAssemb[j];
//	    				System.out.println("ZPtemStr============"+temStr);
	    				assembList.add(temStr);
	    			}
	    			for(Iterator ite = assembList.iterator();ite.hasNext();){
	    				String assembStrLs = (String)ite.next();
	    				for(int j1 = 0;j1<kaches.length;j1++){
	    					String kacheStr = kaches[j1];
	    					if(kacheStr.equals(assembStrLs)){
	    						return routeVec;
	    					}
	    				}
	    			}
	    			//�ж�����·���ַ������Ƿ������������
	    			Collection makeList = new ArrayList();
	    			String[] temMake = makeStr.split("-");
//	    			System.out.println("temMake============"+temMake);
	    			for(int j = 0;j<temMake.length;j++){
	    				String temStr = temMake[j];
	    				String[] temMake1 = temStr.split("��");
	    				for(int jj = 0;jj<temMake1.length;jj++){
	    					String temStr1 = temMake1[jj];
//	    					System.out.println("temStr1============"+temStr1);
	    					makeList.add(temStr1);
	    				}
	    			}
	    			for(Iterator ite = makeList.iterator();ite.hasNext();){
	    				String makeStrLs = (String)ite.next();
	    				for(int j1 = 0;j1<kaches.length;j1++){
	    					String kacheStr = kaches[j1];
	    					if(kacheStr.equals(makeStrLs)){
	    						return routeVec;
	    					}
	    				}
	    			}
	    		}
	    		//CCEnd SS21
	    		try {
	    			//CCEnd SS20
	    			//					if (isExistOfERP(partNumberVer))
//	    			CCBegin SS21
//	    			if (isLogical(part) || isExistOfERP(partNumberVer))
	    				if (isExistOfERP(partNumberVer))
//	    					CCEnd SS21
	    				//CCEnd SS20
	    				return routeVec;
	    		} catch (SQLException e) {

	    			e.printStackTrace();
	    		}
	    		//CCEnd SS14


	    		//	    		return routeVec; 
	    		return part;
	    		//CCEnd SS13

	    	}
	    	return part;
	    }    
	    		//CCBegin SS14
	    private boolean isExistOfERP(String partNumberVer) throws SQLException
	    {
//	    	System.out.println("partNumberVer==========="+partNumberVer);
				Statement statement = conn.createStatement();
				String partnumber1 = partNumberVer;
				String sql ="select PARTNUMBER,MATERIALNUMBER from jfmaterialsplit where MATERIALNUMBER = '"+partNumberVer+"' ";
				
				if(partNumberVer.endsWith("/A")){
					partnumber1 = partNumberVer.substring(0,partNumberVer.length()-2);
//					System.out.println("partnumber1==========="+partnumber1);
					 sql  = "select PARTNUMBER,MATERIALNUMBER from jfmaterialsplit where MATERIALNUMBER = '"+partNumberVer+"' or  MATERIALNUMBER = '"+partnumber1+"'";
				}
				
				ResultSet listRs = statement.executeQuery(sql);
//				ResultSet listRs = statement.executeQuery("select PARTNUMBER,MATERIALNUMBER from jfmaterialsplit where PARTNUMBER = '"+partNumberVer+"'");
			
	    	if (listRs.next())
	    		return true;
	    	
	    	if(partNumberVer.indexOf("1001028-237")>=0 || partNumberVer.indexOf("1104261-1H")>=0 || (partNumberVer.indexOf("CQ1501040")>=0)){
				//System.out.println("sql="+sql);
				//System.out.println("listRs="+listRs);
			}
	    	return false;
			}	    		
	    		//CCEnd SS14

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
				String[] routeStr = new String[6];
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
				routeStr[4] = part.getLifeCycleState().getDisplay();
				routeStr[5] = Integer.toString(info.getCount());

				return routeStr;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new QMException(ex);
			}
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
	   	    String[] State={"����׼��"};
	        QMPartIfc part = null;
	        Object[] obj;
	        Vector result = new Vector();
	        if(part1 instanceof QMPartIfc)
	        {
	        	 part = part1;
	               
	                String state = part.getLifeCycleState().getDisplay();
	                for(int j=0;j<State.length;j++)
	                {
	                    if(state.equals(State[j]))
	                    {                       
	                    	 return part;
	                    }
	                }
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
		   	    String[] State={"����׼��"};
	     		if(col != null ){
	     			Iterator iter = col.iterator();
	     			while (iter.hasNext()) {
	         			QMPartInfo part = (QMPartInfo) iter.next();
	         			String state = part.getLifeCycleState().getDisplay();
	         			String viewName = part.getViewName().toString();
	         			 if(viewName.equals("������ͼ")){
	         				 for(int j = 0;j < State.length;j++)
	         		            {
	     	    				  if(state.equals(State[j]))
	     	    	                {
	     	    					  return part;
	     	    	                }
	         		            }
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
	     * ����ָ�����ù淶�����ָ��������ʹ�ýṹ��
	     * ���ؼ���Collection��ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
	     * ��1��Ԫ�ؼ�¼���������¼��use��ɫ��mastered����ƥ�����ù淶��iterated����
	     * ���û��ƥ����󣬼�¼mastered����
	     * @param partIfc �㲿��ֵ����
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
     * ɾ������BOM
     * @param BomAdoptNoticeInfo list
     */
    public void deleteGYBomAdoptNotice(GYBomAdoptNoticeInfo notice) throws QMException
    {
        try
        {
            PersistService service = (PersistService)EJBServiceHelper.getPersistService();
            String publicType =notice.getPublishType();
            String bsoID = notice.getBsoID();
            if(publicType.startsWith("����")){

                //ɾ����������BOM�������
                Collection parts = getBomPartFromBomAdoptNotice(notice);
                if(parts!=null&&parts.size()>0){
                	for(Iterator ite = parts.iterator();ite.hasNext();){
                		GYBomAdoptNoticePartLinkIfc partLink = (GYBomAdoptNoticePartLinkIfc)ite.next();
                		service.deleteValueInfo(partLink);
                	}
                }
                //ɾ�������������ܡ���ʻ�ҷ�����
                Collection subNotices = getSubGYBomAdoptNotice(notice);
                if(subNotices!=null&&subNotices.size()>0){
                	for(Iterator ites = subNotices.iterator();ites.hasNext();){
                		GYBomAdoptNoticeIfc subNotice = (GYBomAdoptNoticeIfc)ites.next();
                		Collection subparts = getBomPartFromBomAdoptNotice(subNotice);
                		if(subparts!=null&&subparts.size()>0){
                			for(Iterator it = subparts.iterator();it.hasNext();){
                				GYBomAdoptNoticePartLinkIfc subPartLink = (GYBomAdoptNoticePartLinkIfc)it.next();
                				service.deleteValueInfo(subPartLink);//ɾ���ӵ��ӹ������
                			}
                		}
                		
                        //ɾ�����ܡ���ʻ�ҹ���ɢ����
          	    	   Collection existInvoice = getInvoiceByNotice(subNotice.getBsoID());
          	    	   if(existInvoice!=null&&existInvoice.size()>0){
          	    		   for(Iterator itess = existInvoice.iterator();itess.hasNext();){
          	    			   GYInvoiceIfc existIfc = (GYInvoiceIfc)itess.next();
          	    			   service.deleteValueInfo(existIfc);//ɾ��ɢ���嵥
          	    		   }
          	    	   }
                         
                		
                		service.deleteValueInfo(subNotice);//ɾ���ӵ���
                	}
                	
                }
                //ɾ������������
                service.deleteValueInfo(notice);
            }else if(publicType.startsWith("����")||publicType.startsWith("��ʻ��")){
                //ɾ�����ܡ���ʻ�ҹ�������BOm
                Collection parts = getBomPartFromBomAdoptNotice(notice);
                if(parts!=null&&parts.size()>0){
                	for(Iterator ite = parts.iterator();ite.hasNext();){
                		GYBomAdoptNoticePartLinkIfc partLink = (GYBomAdoptNoticePartLinkIfc)ite.next();
                		service.deleteValueInfo(partLink);//ɾ���������
                	}
                }
                //ɾ�����ܡ���ʻ�ҹ���ɢ����
 	    	   Collection existInvoice = getInvoiceByNotice(notice.getBsoID());
 	    	   if(existInvoice!=null&&existInvoice.size()>0){
 	    		   for(Iterator ites = existInvoice.iterator();ites.hasNext();){
 	    			   GYInvoiceIfc existIfc = (GYInvoiceIfc)ites.next();
 	    			   service.deleteValueInfo(existIfc);//ɾ��ɢ���嵥
 	    		   }
 	    	   }
                
                service.deleteValueInfo(notice);//ɾ�����ӷ�����
            }

        }catch(Exception exception)
        {
            exception.printStackTrace();
            setRollbackOnly();
            throw new QMException(exception);
        }
    }
    /**
	 * ����ҵ�����ID��Ϣ���жϵ�ǰ�û��Ըö������޹���Ȩ��
	 * @param bsoID ҵ�����ID
	 * @return true �й���Ȩ�� false �޹���Ȩ��
	 * @throws QMException
	 */
	private boolean hasRightSetLifecycleState(String bsoID) throws QMException {
	
		boolean flag = false;
		try {
			AccessControlService as = (AccessControlService) EJBServiceHelper.getService("AccessControlService");
			PersistService ps = (PersistService) EJBServiceHelper
					.getPersistService();
			BaseValueIfc bvi = ps.refreshInfo(bsoID, false);
			flag = as.hasAccess(bvi, QMPermission.ADMINISTRATIVE);
		} catch (Exception ex) {
		}
		
		return flag;
	}
	/**
	 * ���ݸ��������ID�õ���ؽ���
	 * @param bsoID �����Ķ���Id
	 * @return  ��ؽ�����
	 * @throws QMException
	 */
	private Vector getAssociatedProcesses(String bsoID)throws QMException {
		WfState wfstate = WfState.OPEN_RUNNING;
		try {
			QMQuery query = new QMQuery("WfProcess");
			PersistService pservice = (PersistService) EJBServiceHelper
					.getPersistService();
			BaseValueIfc pbo = pservice.refreshInfo(bsoID);
			// ��������businessObjBsoID=objectID
			query.addCondition(new QueryCondition("businessObjBsoID", "=",
					WfEngineHelper.getPBOIdentity(pbo)));
			query.addAND();
			query.addLeftParentheses();
			Iterator iterator = wfstate.getSubstates();
			WfState wfstate1 = (WfState) iterator.next();
			query.addCondition(new QueryCondition("state", "=", wfstate1
					.toString()));
			WfState wfstate2;
			for (; iterator.hasNext(); query.addCondition(new QueryCondition("state", "=", wfstate2.toString()))) {
				query.addOR();
				wfstate2 = (WfState) iterator.next();
			}
			query.addRightParentheses();

			// ִ�в�ѯ,���ؽ��
			Vector itvc = new Vector();
			itvc.addAll(((PersistService) EJBServiceHelper.getPersistService())
					.findValueInfo(query));

			return itvc;

		} catch (QMException e) {
			e.printStackTrace();
			throw new QMException(e);
		}
	}
    /**
     * ����������
     * @param GYBomAdoptNoticeInfo notice
     */
    public void disuseNotice(GYBomAdoptNoticeInfo notice) throws QMException
    {
        try
        {
    		LifeCycleService lifeCycle = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");
            PersistService service = (PersistService)EJBServiceHelper.getPersistService();

            if(hasRightSetLifecycleState(notice.getBsoID())){
            	Hashtable processMap = new Hashtable();
            	//��ѯ��ؽ���
            	Vector processVec = getAssociatedProcesses(notice.getBsoID());
            	if(processVec!=null&&processVec.size()>0){
            		for(Iterator ite = processVec.iterator();ite.hasNext();){
            			WfProcessIfc processIfc = (WfProcessIfc)ite.next();
            			//��Ҫ��������ؽ���
            			processMap.put(processIfc.getBsoID(), true);
            		}
            	}
            	
        		LifeCycleManagedIfc info = (LifeCycleManagedIfc) service.refreshInfo(notice.getBsoID(), false);
        		lifeCycle.setLifeCycleStated(info, LifeCycleState.toLifeCycleState("DISAFFIRM"),processMap);
            }else{
            	 throw new QMException("�û�û��Ȩ�����÷�����������");
            }


        }catch(Exception exception)
        {
            exception.printStackTrace();
            setRollbackOnly();
            throw new QMException(exception);
        }
    }
    /**
     * ɾ��ָ����ҵ�����
     * @param bsoIDVector Vector Ԫ��ΪBsoID
     */
    private void deleteBaseValueInfo(Collection bsoIDVector) throws QMException
    {
        if(bsoIDVector != null && bsoIDVector.size() > 0)
        {

            PersistService service = (PersistService)EJBServiceHelper.getPersistService();
            Iterator itera = bsoIDVector.iterator();
            while(itera.hasNext())
            {
                String linkID = (String)itera.next();
                service.deleteBso(linkID);
            }
        }
    }
	 /**
     * ���õ����ü�����Ӽ�
     * @param QMPartInfo[] QMPartInfos ��Ҫ���ӵ��Ӽ�
     * @param String selectAdoptPart ѡ��ĸ���
     * @param String[] usageCount  ʹ����������
     * @throws QMException
     * @return Object[] :��һ��Ԫ��Ϊ������������ڶ���Ԫ��Ϊ��������б�����Ӽ��ļ���
     */
    public Object[] replaceBomAdoptNoticePart(QMPartInfo[] QMPartInfos,String selectAdoptPart,String[] usageCount) throws QMException
    {
    	QMPartInfo resultPartInfo = null;
    	Object[] objs = new Object[2];
        try
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
            WorkInProgressService wservice = (WorkInProgressService) EJBServiceHelper.getService("WorkInProgressService");
            QMPartInfo parentPart = (QMPartInfo)pservice.refreshInfo(selectAdoptPart);
            QMPartInfo newParentPart = null;
            Vector linksVec = new Vector();//�洢������Ҫ�½����߸��µĹ���
            if(QMPartInfos!=null&&QMPartInfos.length>0){
            	 CheckoutLinkInfo checkOut = (CheckoutLinkInfo)wservice.checkout(parentPart, getCheckoutFolder(),null);
                 newParentPart = (QMPartInfo)pservice.refreshInfo(checkOut.getLeftBsoID());
            	 for(int i = 0 ; i < QMPartInfos.length;i++){
            		PartUsageLinkIfc link = queryPartUsageLink(parentPart.getBsoID(),QMPartInfos[i].getMasterBsoID());
            		if(link==null){//�����ڹ������򴴽�����
            			PartUsageLinkIfc newLink = new PartUsageLinkInfo();
            			newLink.setLeftBsoID(QMPartInfos[i].getMasterBsoID());
            			newLink.setRightBsoID(newParentPart.getBsoID());
//            			System.out.println("YYYYYYYYYY========"+i+"EEEEE"+usageCount[i]);
            			if(usageCount[i]!=null&&!usageCount[i].equals("")){
            				float quantity = (new Float(usageCount[i])).floatValue();
//            				System.out.println("BBBBBBBBBBB-------"+quantity);
            				newLink.setQuantity(quantity);
            			}
        				linksVec.add(newLink);
            		}else if(link!=null){//���ڹ���������Ҫ�鿴�����Ƿ�һ�£���һ�������
            			float newQuantity = 0;
            			float exQuantity = link.getQuantity();
            			
            			if(usageCount[i]!=null&&!usageCount[i].equals("")){
            				newQuantity = (new Float(usageCount[i])).floatValue();
            			}else{
            				newQuantity = 0;
            			}
//            			System.out.println("AAAAAA====1===="+exQuantity);
//            			System.out.println("AAAAAA====2===="+newQuantity);
            			if(exQuantity!=newQuantity){//���������һ��
            				PartUsageLinkIfc newLink = new PartUsageLinkInfo();
                			newLink.setLeftBsoID(QMPartInfos[i].getMasterBsoID());
                			newLink.setRightBsoID(newParentPart.getBsoID());
            				float quantity = (new Float(usageCount[i])).floatValue();
            				newLink.setQuantity(quantity);
            				linksVec.add(newLink);
            			}
            		}
            	}
            	 //������������Ҽ������
            	 if(linksVec!=null&&linksVec.size()>0){
            		 for(Iterator ite = linksVec.iterator();ite.hasNext();){
            			 PartUsageLinkIfc saveLink = (PartUsageLinkIfc)ite.next();
//            			 System.out.println("DDDDDDDDddd========"+saveLink.getQuantity());
            			 pservice.saveValueInfo(saveLink);
            		 }
            		 newParentPart =  (QMPartInfo)wservice.checkin(newParentPart,"");
            		 resultPartInfo = newParentPart;
            	 }else{//�������������
            		 wservice.undoCheckout(parentPart);
            		 resultPartInfo = parentPart;
            	 }
            }
            //�������������
            objs[0]=resultPartInfo;
            Vector subPartVec = new Vector();
            if(QMPartInfos!=null&&QMPartInfos.length>0){
            	//
            	for(int j = 0 ; j <QMPartInfos.length;j++){
                	QMPartInfo subPart = (QMPartInfo)QMPartInfos[j];
                	if(subPart.getLifeCycleState().toString().equals("DESIGN")){
                		subPartVec.add(subPart);
                	}
            	}
            	//������ơ�״̬���Ӽ������ص�ҳ�沢����ӵ���ʾ�б���
            	if(subPartVec!=null&&subPartVec.size()>0){
            		QMPartInfo[] subPart = new QMPartInfo[subPartVec.size()];
            		for(int jj = 0 ; jj<subPartVec.size();jj++){
            			subPart[jj] = (QMPartInfo)subPartVec.get(jj);
            		}
            		objs[1] = subPart;
            	}else{
            		objs[1] = null;
            	}
            }
            
            
            
        }catch(QMException e)
        {
    
            throw new QMException(e);
        }
        return objs;
    }
    /**
     * �����Ӳ��õ�����ò��õ��������
     * @return Collection ���õ������������
     * @param String bsoID ���õ�����bsoID
     * @throws QMException
     */
    public Collection getPartsFromBomSubAdoptNotice(String bsoID)throws QMException
    {
    	Collection col = null;
        PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
        QMQuery query = new QMQuery("BomAdoptNoticePartLink");
        query.addCondition( new QueryCondition("noticeID", QueryCondition.EQUAL, bsoID));
        col = (Collection)pservice.findValueInfo(query);
    	
    	return col;
    }
  
    /**
     * �����Ӳ��õ�����ò��õ��������
     * @return Collection ���õ������������
     * @param BomAdoptNoticeInfo info ���õ�����
     * @throws QMException
     */
    public Collection getPartsFromBomSubAdoptNotice(BomAdoptNoticeInfo info)throws QMException
    {
    	Collection col = null;
        PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
    	if(info!=null&&info.getPublishType().equals("�Ӳ���֪ͨ��")){
            QMQuery query = new QMQuery("BomAdoptNoticePartLink");
            query.addCondition( new QueryCondition("noticeID", QueryCondition.EQUAL, info.getBsoID()));
             col = (Collection)pservice.findValueInfo(query);
    	}
    	return col;
    }
    /**
     * ���������÷���������������ɾ���źż�����
     * @return Collection ���õ������������
     * @param QMPartInfo info �������
     * @throws QMException
     */
    public Collection getPartsFromBomSubAdoptNoticeLink(QMPartInfo info)throws QMException
    {
    	Collection col = null;
    	Collection col2 = null;
        PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
        QMQuery query = new QMQuery("GYBomAdoptNoticePartLink");
        query.addCondition( new QueryCondition("partID", QueryCondition.EQUAL, info.getBsoID()));
        col = (Collection)pservice.findValueInfo(query);
        
        QMQuery query2 = new QMQuery("GYInvoice");
        query2.addCondition( new QueryCondition("partID", QueryCondition.EQUAL, info.getBsoID()));
        col2 = (Collection)pservice.findValueInfo(query2);
        
        if(col!=null&&col.size()>0){
        	return col;
        }
        if(col2!=null&&col2.size()>0){
        	return col2;
        }
        
    	return col;
    }
    /**
     * �����ӷ���������ó��ܡ���ʻ�ҷ�����
     * @return Collection ���õ�����
     * @param BomAdoptNoticeInfo info ���õ�����
     * @throws QMException
     */
    public Collection getSubGYBomAdoptNotice(GYBomAdoptNoticeInfo info)throws QMException
    {
    	
    	Collection col = null;
        PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
    	if(info!=null&&info.getPublishType().startsWith("����")){
            QMQuery query = new QMQuery("GYBomAdoptNotice");
            query.addCondition( new QueryCondition("parentNotice", QueryCondition.EQUAL, info.getBsoID()));
             col = (Collection)pservice.findValueInfo(query);
    	}
    	return col;
    }

    /**
     * ��ȡ����ļ���.
     * @return ����ļ���
     * @throws QMException
     * <br>���û�Ϊ��ʱ���׳��쳣��"�����ʼ������ȷ(��Ϊ��)"��
     */
    public FolderIfc getCheckoutFolder()throws QMException
    {

            SessionService sessionService = null;
            FolderService folderService = null;
            User user = null;
            try
            {
                    sessionService = (SessionService)EJBServiceHelper.getService("SessionService");
                    user = sessionService.getCurUser(); //����û��ӿ�
                    if (user == null)
                    {
                            throw new WorkInProgressException("com.faw_qm.wip.util.WorkInProgressResource","150",null);
                    }//end if
                    folderService = (FolderService)EJBServiceHelper.getService("FolderService");  //����ļ��з���
            }
            catch(Exception ex)
            {
                    throw new QMException(ex);
            }//end try-catch
            UserInfo info = (UserInfo)user.getValueInfo();
            FolderIfc folder = null;
            try
            {
                    folder = folderService.getPersonalFolder(info); //�����ļ���
                    //��������ΪUserInfo
                    folder = folderService.getFolder(folder.getPath()+"\\"+
                                                     "Check_Out");  //��ü��������ļ���
            }
            catch(Exception ex)
            {
                    throw new QMException(ex);
            }//end try-catch


            return folder;
    }
    /**
	 *  �����Ӽ��͸����������Ƿ����ʹ�ù�ϵ��
	 * @param String childMasterBsoID,�Ӽ�MasterID
			  String parentBsoID ����BsoID
	 * @return PartUsageLinkIfc �Ӽ������Ĺ���
	 * @throws QMException
	 */
	protected static PartUsageLinkIfc queryPartUsageLink(String childMasterBsoID,
			String parentBsoID) throws QMException {
		QMQuery query;
		try {
			query = new QMQuery("PartUsageLink");
			QueryCondition condition = new QueryCondition("leftBsoID", "=",
					childMasterBsoID);
			query.addCondition(condition);
			query.addAND();
			QueryCondition condition2 = new QueryCondition("rightBsoID", "=",
					parentBsoID);
			query.addCondition(condition2);
			PersistService persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			Collection collection = persistService.findValueInfo(query);
			PartUsageLinkIfc link = null;
			if (collection != null && collection.size() > 0) {
				for (Iterator ite = collection.iterator(); ite.hasNext();) {
					link = (PartUsageLinkInfo) ite.next();
				}
			}
			return link;
		} catch (QueryException e) {
			e.printStackTrace();
			throw new QMException(e);
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
			throw new QMException(e);
		} catch (QMException e) {
			e.printStackTrace();
			throw new QMException(e);
		}

	}
	
	/**
	 * �����㲿����BsoID��ȡ�㲿������Ӧ��QMPartMaster��BsoID��
	 * @param partID  �㲿��BsoID
	 * @return String MasterBsoID
	 * @throws QMException
	 */
	public String getMasterIDByPartID(String partID) throws QMException {
		PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
		QMPartIfc partIfc = (QMPartIfc) pService.refreshInfo(partID);
		String masterID = partIfc.getMasterBsoID();
		return masterID;
	}
	
    /**
     * ��÷�������ţ��Զ���ˮ�ţ�
     * @param noticeIfc BomAdoptNoticeIfc
     * @throws QMException
     * @return String
     */
    public String getBomAdoptNoticeNumber(String type) throws QMException
    {
        String result = "";
        try
        {
            //������������λ
        	String yearLast = new SimpleDateFormat("yy",Locale.CHINESE).format(Calendar.getInstance().getTime());
            String className = "GYBomAdoptNotice";
            String baseKey="LX-JF-"+yearLast+"-"+type;
            
            //ȡ������1λ���кŹ����3λ
            int sortNumber = this.getNextSortNumber(className, baseKey, false);
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
     * ��ñ����ţ��Զ���ˮ�ţ�
     * @param noticeIfc BomAdoptNoticeIfc
     * @throws QMException
     * @return String
     */
    public String getBomChangeNoticeNumber(String workShopID) throws QMException
    {
        String result = "";
        try
        {
            //������������λ
            String yearLast = new SimpleDateFormat("yy",Locale.CHINESE).format(Calendar.getInstance().getTime());


            String className = "BomChangeNotice";
            String baseKey="Z28-S"+workShopID+"-TZD-"+yearLast;
            
            //ȡ������1λ���кŹ����4
            int sortNumber = this.getNextSortNumber(className, baseKey, false);
            char[] sortNum = String.valueOf(sortNumber).toCharArray();
            char[] arry  = {'0','0','0','0'};
            System.arraycopy(sortNum, 0, arry, arry.length-sortNum.length, sortNum.length);
            String resNum = new String(arry);
            
            
            result = "Z28-S"+workShopID+"-TZD-"+yearLast + resNum;
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
    private int getNextSortNumber(String className, String baseKey, boolean changeFlag) throws QMException
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
    private UniteIdentifyInfo createUniteIdentify(String className, String baseKey, boolean changeFlag) throws QMException
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

    /**
     * �����õ�����
     * @param list
     * @param locker
     * @throws QMException
     */
    public void unlock(LockIfc list, String locker) throws QMException
    {
        PersistService service = (PersistService)EJBServiceHelper.getPersistService();
        list = (LockIfc)service.refreshInfo(list);
        LockService lService = (LockService)EJBServiceHelper.getService("LockService");
        lService.unlock(list, locker);
    }

    /**
     * �����õ�����
     * @param list
     * @param locker
     * @throws QMException
     */
    public LockIfc lock(LockIfc list, String locker) throws QMException
    {
        PersistService service = (PersistService)EJBServiceHelper.getPersistService();
        list = (LockIfc)service.refreshInfo(list);
       // System.out.println("locker=======" + list.getLocker());
        if(list.getLocker()!= null&&list.getLocker().equalsIgnoreCase(locker))
        {
        	return list;
        }
        else
        {
        	if(list.getLocker() == null)
        	{
        		LockService lService = 
        			(LockService)EJBServiceHelper.getService("LockService");
                return lService.lock(list, locker, "");
        	}
        	else
        	{
        		User user = (User) service.refreshInfo(list.getLocker());
        		throw new QMException("�ö����ѱ�"+user.getUsersName()+"������");
        	}
        }
    }
    
    /**
     * �����������
     * @param Object[] param
     * @throws QMException
     * @return Collection
     */
   public Collection findMultPartsByNumbers(Object[] param) throws QMException
   {
	   Collection partVec = new Vector();
	   PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
	   StandardPartService partService = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
        		   //CCBegin SS6
	   VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
        		   //CCEnd SS6
       try
       {
    	   Collection result = null;
           if(param != null && param.length > 0)
           {

               QMQuery query = new QMQuery("QMPartMaster");
               query.setChildQuery(false);
               for(int i = 0;i < param.length;i++)
               {
                   if(param[i] != null && param[i].toString().trim().length() > 0)
                   {
                     //CCBegin SS27 
//                       QueryCondition cond = new QueryCondition("partNumber", "=", param[i].toString());
                	   	String str = param[i].toString().replace("*", "%");
                	  	QueryCondition cond = new QueryCondition("partNumber", QueryCondition.LIKE, "%"+str+"%");
                	   //CCEnd SS27 
                       if(query.getConditionCount() > 0)
                       {
                           query.addOR();
                       }
                       query.addCondition(cond);
                   }
               }
               if(query.getConditionCount() > 0)
               {
                   query.addOrderBy("partNumber", false);

                   result = pservice.findValueInfo(query, false);
               }

           }
         //��ѯ��ʼ�Ӽ�
           if(result!=null&&result.size()>0){ 
        	   for(Iterator ite = result.iterator();ite.hasNext();){

        		   QMPartMasterInfo master = (QMPartMasterInfo)ite.next();
        		   //CCBegin SS6
        		   Collection coll=vcservice.allVersionsOf(master);
        		   partVec.addAll(coll);
        		   //QMPartInfo part =  partService.getPartByMasterID(master.getBsoID());
        		   //partVec.add(part);
        		   //CCEnd SS6
        	   }
      }
       }catch(QMException e)
       {
           e.printStackTrace();
           throw new QMException(e);
       }
       
       
       
       return partVec;
   }

   /**
    * ���������������õ�
    * @param condition �����������
    * @return Vector �������
    * @author wenliu 2014-6-12
    */
   public Collection searchGYBomAdoptNotice(HashMap condition) throws QMException
   {

   	Vector returnVec = new Vector();
       PersistService service = (PersistService)EJBServiceHelper.getPersistService();
		
       String routeListNumberStr = (String)condition.get("routeListNumberStr");
       String routeListNameStr = (String)condition.get("routeListNameStr");
       String routeListStatStr = (String)condition.get("routeListStatStr");
     
       QMQuery query = new QMQuery("GYBomAdoptNotice");
       //���õ����
       if(routeListNumberStr.length()>0){
           if(routeListNumberStr.indexOf("*") == -1 && routeListNumberStr.indexOf("%") == -1)
           {
               query.addAND();
               query.addCondition( new QueryCondition("adoptnoticenumber", QueryCondition.EQUAL, routeListNumberStr));
           }else
           {

           	routeListNumberStr = routeListNumberStr.replace('*', '%');
               query.addAND();
               query.addCondition(new QueryCondition("adoptnoticenumber", QueryCondition.LIKE, routeListNumberStr));
           }
       }

       //���õ�����
       
       if(routeListNameStr.length()>0){
           if(routeListNameStr.indexOf("*") == -1 && routeListNameStr.indexOf("%") == -1)
           {
               query.addAND();
               query.addCondition( new QueryCondition("adoptnoticename", QueryCondition.EQUAL, routeListNameStr));
           }else
           {

           	routeListNameStr = routeListNameStr.replace('*', '%');
               query.addAND();
               query.addCondition(new QueryCondition("adoptnoticename", QueryCondition.LIKE, routeListNameStr));
           }
       } 
       Collection col = (Collection)service.findValueInfo(query);
       
       if(routeListStatStr.length()>0&&col!=null&&col.size()>0){
       	for(Iterator ites =col.iterator();ites.hasNext(); ){
       		GYBomAdoptNoticeInfo adopt = (GYBomAdoptNoticeInfo)ites.next();
       	  	if(adopt!=null){
           	  	String lifeCycleStateStr = adopt.getLifeCycleState().getDisplay();
           	  	if(lifeCycleStateStr!=null&&lifeCycleStateStr.equals(routeListStatStr)){
           	  		returnVec.add(adopt);
           	  	}		
       	  	}
       	}
           return returnVec;	
       }else{
       	return col;
       }
   }
   
   
   /**
    * ���������������Ĳ��á������
    * @param condition �����������
    * @return Vector �������
    * @author wenliu 2014-6-4
    */
   public Collection searchBomAdoptChangeNotice(HashMap condition) throws QMException
   {

   	Vector returnVec = new Vector();
       PersistService service = (PersistService)EJBServiceHelper.getPersistService();
		
       String routeListNumberStr = (String)condition.get("routeListNumberStr");
       String routeListNameStr = (String)condition.get("routeListNameStr");
       String noticeTypeStr = (String)condition.get("noticeTypeStr");
       Collection col = new Vector();
       if(noticeTypeStr.equals("����֪ͨ��")){
       	 QMQuery query = new QMQuery("BomAdoptNotice");
            //���õ����
            if(routeListNumberStr.length()>0){
                if(routeListNumberStr.indexOf("*") == -1 && routeListNumberStr.indexOf("%") == -1)
                {
                    query.addAND();
                    query.addCondition( new QueryCondition("adoptnoticenumber", QueryCondition.EQUAL, routeListNumberStr));
                }else
                {

                	routeListNumberStr = routeListNumberStr.replace('*', '%');
                    query.addAND();
                    query.addCondition(new QueryCondition("adoptnoticenumber", QueryCondition.LIKE, routeListNumberStr));
                }
            }

            //���õ�����
            
            if(routeListNameStr.length()>0){
                if(routeListNameStr.indexOf("*") == -1 && routeListNameStr.indexOf("%") == -1)
                {
                    query.addAND();
                    query.addCondition( new QueryCondition("adoptnoticename", QueryCondition.EQUAL, routeListNameStr));
                }else
                {

                	routeListNameStr = routeListNameStr.replace('*', '%');
                    query.addAND();
                    query.addCondition(new QueryCondition("adoptnoticename", QueryCondition.LIKE, routeListNameStr));
                }
            } 

            col = (Collection)service.findValueInfo(query);
   
       }else if(noticeTypeStr.equals("���֪ͨ��")){
      	 QMQuery query = new QMQuery("BomChangeNotice");
       
        if(routeListNumberStr.length()>0){
            if(routeListNumberStr.indexOf("*") == -1 && routeListNumberStr.indexOf("%") == -1)
            {
                query.addAND();
                query.addCondition( new QueryCondition("adoptnoticenumber", QueryCondition.EQUAL, routeListNumberStr));
            }else
            {

            	routeListNumberStr = routeListNumberStr.replace('*', '%');
                query.addAND();
                query.addCondition(new QueryCondition("adoptnoticenumber", QueryCondition.LIKE, routeListNumberStr));
            }
        }

        //���õ�����
        
        if(routeListNameStr.length()>0){
            if(routeListNameStr.indexOf("*") == -1 && routeListNameStr.indexOf("%") == -1)
            {
                query.addAND();
                query.addCondition( new QueryCondition("adoptnoticename", QueryCondition.EQUAL, routeListNameStr));
            }else
            {

            	routeListNameStr = routeListNameStr.replace('*', '%');
                query.addAND();
                query.addCondition(new QueryCondition("adoptnoticename", QueryCondition.LIKE, routeListNameStr));
            }
        } 
        col = (Collection)service.findValueInfo(query);
       }
       return col;

   }
   /**
    * ����������ϣ���ѯ������Ч·������
    * @param QMPartInfo[] parts �������
    * @param QMPartIfc parentPart ��������
    * @return Vector ��������Լ�·������
    * @author wenliu 2014-6-13
    */
   //CCBegin SS2
   public Collection findPartAndRoute(QMPartInfo[] parts,QMPartIfc parentPart) throws QMException
   {
       Vector returnVec = new Vector();
       PersistService service = (PersistService)EJBServiceHelper.getPersistService();
       TechnicsRouteService tr = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
       StandardPartService partService = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
       if(parts!=null&&parts.length>0){
    	   for(int i = 0 ; i <parts.length;i++){
    		  QMPartInfo part = (QMPartInfo) parts[i];
    		  String[] partStr = new String[10];
			  partStr[0] = part.getBsoID();
			  partStr[1] = part.getPartNumber();
			  partStr[2] = part.getPartName();
			  partStr[3] = part.getVersionID();
			  partStr[4] = part.getLifeCycleState().getDisplay();
			  partStr[5] = part.getViewName();
			  if(parentPart!=null){
				 // partStr[6] = partService.getPartQuantity(parentPart,part);
			  }else{
				  partStr[6] = "";
			  }
			
    		//CCBegin SS3
			  String[] bsoStr = new String[1];
			  bsoStr[0] = part.getBsoID();
			  StringBuffer zhizaoBuffer = new StringBuffer();
			  StringBuffer zhuangpeiBuffer = new StringBuffer();
    		  try {
    		  	//CCBegin SS29
    		  	String dwbs = getCurrentDWBS();
    		  	System.out.println("��������ȡ��λ��"+dwbs);
    		  	if(dwbs.equals("W34"))//�ɶ�
    		  	{
    		  		Collection cols = getSecondRouteByJDBC(part.getBsoID());
    		  		if(cols!=null&&cols.size()>0)
    		  		{
    		  			int n = 0;
    		  			for(Iterator ite = cols.iterator();ite.hasNext();)
    		  			{
    		  				String[] splitStr = (String[])ite.next();
    		  				if(n==cols.size()-1)
    		  				{
    		  					zhizaoBuffer.append(splitStr[0]);
    		  					zhuangpeiBuffer.append(splitStr[1]);
    		  				}
    		  				else
    		  				{
    		  					zhizaoBuffer.append(splitStr[0]+"/");
    		  					zhuangpeiBuffer.append(splitStr[1]+"/");
    		  				}
    		  				n++;
    		  			}
    		  		}
    		  	}
    		  	else//Ŀǰ�ǽ�ţ��Ժ����ӵ�λ����else
    		  	{
    		  	//CCEnd SS29
//CCBegin SS7
    		conn = getConnection();
//CCEnd SS7
				Collection cols = getRouteByJDBC(bsoStr);
//CCBegin SS7
		if(conn!=null){
			conn.close();	
		}
//CCEnd SS7
				if(cols!=null&&cols.size()>0){
					for(Iterator ite = cols.iterator();ite.hasNext();){
						Vector vec = (Vector)ite.next();
						if(vec!=null&&vec.size()>0){
							for(int n = 0 ; n <vec.size();n++){
								String[] routeStrs = (String[])vec.get(n);
								String routeStr = routeStrs[0];
								String[] splitStr = routeStr.split("@");
								if(n==vec.size()-1){
									zhizaoBuffer.append(splitStr[0]);
									zhuangpeiBuffer.append(splitStr[1]);
								}else{
									zhizaoBuffer.append(splitStr[0]+"/");
									zhuangpeiBuffer.append(splitStr[1]+"/");
								}

							}
						}
				
					}
				}//CCBegin SS25
				//CCBegin SS29
			  }
			  //CCEnd SS29
			} catch (Exception e) {
				e.printStackTrace();
			}//CCEnd SS25
    		if(zhizaoBuffer!=null){
    			partStr[7] = zhizaoBuffer.toString();//����
    		}else{
    			partStr[7] = "";
    		}
    		if(zhuangpeiBuffer!=null){
    			partStr[8] = zhuangpeiBuffer.toString();//װ��
    		}else{
    			partStr[8] = "";
    		}
    		//CCEnd SS3
				//���IBA���ԣ��Ƿ������
				Collection ibaCol = getPartIBA(part,"�����","virtualPart");
				//��ǰ����Ѿ�����IBAֵ������
				if(ibaCol!=null&&ibaCol.size()>0){
					Iterator ibaIte = ibaCol.iterator();
					StringValueIfc existValue = (StringValueIfc)ibaIte.next();
					partStr[9] = existValue.getValue();
				}else{
					partStr[9] = "";
				}
				returnVec.add(partStr);
    	   }
       }
	   return returnVec;
   }
   //CCEnd SS2
   //CCBegin SS3
   /**
	 * �������
	 * @return
	 * @throws SQLException
	 */
	private Connection getConnection() throws Exception {
	    String url =RemoteProperty.getProperty("com.faw_qm.gybomNotice.DB.url", "jdbc:oracle:thin:@10.133.67.137:orcl");
		String user =RemoteProperty.getProperty("com.faw_qm.gybomNotice.DB.User","jfbom"); 
		String password =RemoteProperty.getProperty("com.faw_qm.gybomNotice.DB.Password","jfbom"); 
//		return DriverManager.getConnection(url, user, password);
		//CCBegin SS25
		try {
			conn = PersistUtil.getConnection();
			return conn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;//CCEnd SS25
	}
   /**
	 * �������bsoID����ѯ�������·��
	 * @return
	 * @throws SQLException
	 */
	private Collection getRouteByJDBC(String[] partBsoIDs)throws SQLException{

		ResultSet rs = null;
//CCBegin SS7
//�ŵ�ȫ�ֱ�����		Connection conn = getConnection();
//CCEnd SS7
		Statement statement = conn.createStatement();
		StringBuffer parts = new StringBuffer();
		Collection col =  new ArrayList();
		if(partBsoIDs!=null&&partBsoIDs.length>0){
			for(int i = 0 ; i <partBsoIDs.length;i++){
				if(i==0){
					parts.append("'"+partBsoIDs[i]+",");
				}if(i==partBsoIDs.length-1){
					parts.append(partBsoIDs[i]+"'");
				}else{
					parts.append(partBsoIDs[i]+",");
				}
				//System.out.println("partBsoIDs[i]========================="+partBsoIDs[i]);
				//��ѯ�����ѷ���·�߱�ID
//CCBegin SS18
//				ResultSet listRs = statement.executeQuery("select b.bsoid as listID from listroutepartlink a,technicsroutelist b where a.leftbsoid = b.bsoid and (b.lifecyclestate =  'RELEASED' or b.lifecyclestate =  'QIYENEIKONG' ) and a.adoptstatus = 'ADOPT' and a.partbranchid = '"+partBsoIDs[i]+"'  order by b.createtime desc");
//				CCBegin SS21
//				ResultSet listRs = statement.executeQuery("select b.bsoid as listID from listroutepartlink a,technicsroutelist b where a.leftbsoid = b.bsoid and (b.lifecyclestate =  'RELEASED' or b.lifecyclestate =  'QIYENEIKONG' ) and b.owner is null and a.adoptstatus = 'ADOPT' and a.partbranchid = '"+partBsoIDs[i]+"'  order by b.createtime desc");
				ResultSet listRs = statement.executeQuery("select b.bsoid as listID from listroutepartlink a,technicsroutelist b where a.leftbsoid = b.bsoid and (b.lifecyclestate =  'RELEASED' or b.lifecyclestate =  'QIYENEIKONG' ) and b.owner is null and a.adoptstatus = 'ADOPT' and a.partbranchid = '"+partBsoIDs[i]+"'  order by b.modifytime desc");
//				CCEnd SS21
//CCEnd SS18
				String listBsoID = "";
										    		//CCBegin SS12
				int count=0;
										    		//CCEnd SS12
			
				while (listRs.next()) {
					listBsoID = listRs.getString("listID");
//					System.out.println("listBsoID=========================="+listBsoID);
										    		//CCBegin SS12
					count++;
										    		//CCEnd SS12
					break;
					
				}
				//��·������
//CCBegin SS19
//				ResultSet routeRs = statement.executeQuery("select d.routestr as routestr ,d.mainroute as mainroute from listroutepartlink c,technicsroutebranch d where c.routeid = d.routeid and c.leftbsoid = '"+listBsoID+"' and c.partbranchid = '"+partBsoIDs[i]+"'");
				ResultSet routeRs = statement.executeQuery("select d.routestr as routestr ,d.mainroute as mainroute from listroutepartlink c,technicsroutebranch d where c.routeid = d.routeid and c.leftbsoid = '"+listBsoID+"' and c.partbranchid = '"+partBsoIDs[i]+"'  order by d.createtime desc");
//CCBegin SS19
										    		//CCBegin SS12
				if ( count==0)
				{
					//ͨ��partid���masterid����ͨ��partmasterid����
					ResultSet listRs1 = statement.executeQuery("select a.masterbsoid as listID from qmpart a where a.bsoid = '"+partBsoIDs[i]+"'  order by a.modifytime desc");
					String listBsoID1 = "";
				
					while (listRs1.next()) {
						listBsoID1 = listRs1.getString("listID");
						break;
						
					}
//CCBegin SS18
//					listRs = statement.executeQuery("select b.bsoid as listID from listroutepartlink a,technicsroutelist b where a.leftbsoid = b.bsoid and (b.lifecyclestate =  'RELEASED' or b.lifecyclestate =  'QIYENEIKONG' ) and a.adoptstatus = 'ADOPT' and a.rightbsoid = '"+listBsoID1+"'  order by b.createtime desc");
					listRs = statement.executeQuery("select b.bsoid as listID from listroutepartlink a,technicsroutelist b where a.leftbsoid = b.bsoid and (b.lifecyclestate =  'RELEASED' or b.lifecyclestate =  'QIYENEIKONG' ) and b.owner is null and a.adoptstatus = 'ADOPT' and a.rightbsoid = '"+listBsoID1+"'  order by b.createtime desc");
//CCEnd SS18
					while (listRs.next()) {
						listBsoID = listRs.getString("listID");
					count++;
						break;
						
					}
				//��·������
//CCBegin SS19
//				routeRs = statement.executeQuery("select d.routestr as routestr ,d.mainroute as mainroute from listroutepartlink c,technicsroutebranch d where c.routeid = d.routeid and c.leftbsoid = '"+listBsoID+"' and c.rightbsoid = '"+listBsoID1+"'");
				routeRs = statement.executeQuery("select d.routestr as routestr ,d.mainroute as mainroute from listroutepartlink c,technicsroutebranch d where c.routeid = d.routeid and c.leftbsoid = '"+listBsoID+"' and c.rightbsoid = '"+listBsoID1+"'  order by d.createtime desc");
//CCEnd SS19
			}
										    		//CCEnd SS12

				Vector vec = new Vector();
				
				while (routeRs.next()) {
					String[] routeStrs = new String[3];
					routeStrs[0] = routeRs.getString("routestr");
					routeStrs[1] = routeRs.getString("mainroute");
					routeStrs[2] = listBsoID;
					vec.add(routeStrs);
				}
				col.add(vec);
			}	
		}
		
		
		//conn.commit();
//CCBegin SS7
////�ŵ�ȫ�ֱ�����		if(conn!=null){
//			conn.close();	
//		}
//CCEnd SS7

		return col;
	}
   //CCEnd SS3
   /**
    * ��������Ҫ·�ߵ�·������
    * @param QMPartIfc part
    * @return QMPartIfc
    * @throws QMException 
    */
	private static Collection getMainRoute(String routeID)
	throws QMException
	{
		
		PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("TechnicsRouteBranch");
		
		QueryCondition qc = new QueryCondition("routeID", QueryCondition.EQUAL,routeID);
		query.addCondition(qc);
		QueryCondition qc2 = new QueryCondition("mainRoute" ,Boolean.TRUE);
		query.addAND();
		query.addCondition(qc2);
        Collection   col = (Collection)pservice.findValueInfo(query);
		return col;
	}
	/**
	 * ���������
	 * 
	 * @param String csvData CSV�ļ�����
	 * @return Vector �������־��Ϣ
	 */

	public Vector importVirtualPart(String csvData) throws QMException {
		Vector vec = new Vector();
		Vector coll = new Vector();
		PersistService service = (PersistService) EJBServiceHelper
				.getService("PersistService");
		StringDefinitionIfc definition = queryDefinition();
		if(definition==null){
			vec.add("��ȡ��������ġ��������IBA���ԣ�");
			return vec;
		}
		if (csvData != null && !csvData.equals("")) {
			String[] csvRow = csvData.split("\r\n");
			if (csvRow != null && csvRow.length > 0) {
				for (int i = 1; i < csvRow.length; i++) {
					String[] message = new String[2];	
					String row = csvRow[i];
					String[] partInformation = row.split(",");
					String partNumber = partInformation[0];
					message[0]="��"+(i+1)+"�У�������Ϊ��"+partNumber+"����������ݵ���";
					String partName = partInformation[1];
				//CCBegin SS24
//					String partVersion = partInformation[2];
					String partVirtual = null;
//					if (partInformation.length==4)
//						partVirtual = partInformation[3];
					if (partInformation.length==3)
						partVirtual = partInformation[2];
				//CCEnd SS24
					if(partVirtual==null){
						partVirtual = "";
					}
					if(partNumber==null||partNumber.equals("")){
						message[1] = "ʧ�ܡ�ԭ�򣺱��Ϊ�գ�";
						vec.add(message);
						continue;
					}
				//CCBegin SS24
//					if(partVersion==null||partVersion.equals("")){
//						message[1] = "ʧ�ܡ�ԭ�򣺰汾Ϊ�գ�";
//						vec.add(message);
//						continue;
//					}
//					Collection parts = queryPartForImport(partNumber,partVersion);
					Collection parts=new ArrayList();
					VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
					Collection partsLs = queryPartForImport(partNumber);
					if (partsLs!=null && partsLs.size()>0)
					{
						Iterator iterator2 = partsLs.iterator();
						QMPartIfc partObject=null;
						if (iterator2.hasNext()) {
							partObject=(QMPartIfc)iterator2.next();
						}
						if (partObject!=null){
			     		Collection col = vcservice.allVersionsOf(partObject);
							Iterator iterator1 = col.iterator();
							if (iterator1.hasNext()) {
								parts.add( (QMPartIfc) iterator1.next());
							}
						}
					}
				//CCEnd SS24
					if(parts==null||parts.size()<=0){
				//CCBegin SS24
//						message[1] = "ʧ�ܡ�ԭ�򣺲�ѯ�����"+"�汾("+partVersion+")ʧ�ܣ�";
						message[1] = "ʧ�ܡ�ԭ�򣺲�ѯ�����"+partNumber+"ʧ�ܣ�";
				//CCEnd SS24
						vec.add(message);
						continue;
					}
					//���ܴ��ڶ���汾������޸�ÿ�������Ӧ��IBA����
					for(Iterator ite = parts.iterator();ite.hasNext();){
						QMPartIfc part = (QMPartIfc)ite.next();
						Collection ibaCol = getPartIBA(part,"�����","virtualPart");
						//��ǰ����Ѿ�����IBAֵ������
						if(ibaCol!=null&&ibaCol.size()>0){
							Iterator ibaIte = ibaCol.iterator();
							StringValueIfc existValue = (StringValueIfc)ibaIte.next();
							existValue.setValue(partVirtual);
							service.saveValueInfo(existValue);
							message[1] = "����IBA���Գɹ���";
						//��ǰ���������IBAֵ������
						}else{
							StringValueIfc newValue = new StringValueInfo();
							newValue.setDefinitionBsoID(definition.getBsoID());
							newValue.setIBAHolderBsoID(part.getBsoID());
							newValue.setHierarchyID(definition.getHierarchyID());
							newValue.setValue(partVirtual);
							service.saveValueInfo(newValue);
							message[1] = "����IBA���Գɹ���";
						}	
						coll.add(part);
					}
					vec.add(message);	
				
				}
			}
		}
        if(coll!=null){
        	//PublishData publish = new PublishData();
        	try {
        		PublishData.JFvirtualPartForERP(coll);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return vec;
	}
	/**
	 * ����ר�ü�
	 * 
	 * @param String csvData CSV�ļ�����
	 * @return Vector �������־��Ϣ
	 */

	public Vector importSpecPart(String csvData) throws QMException {
		Vector vec = new Vector();
		PersistService service = (PersistService) EJBServiceHelper
				.getService("PersistService");
		StringDefinitionIfc definition = queryDefinitionForSpec();
		if(definition==null){
			vec.add("��ȡ��������ġ�ר�ü���IBA���ԣ�");
			return vec;
		}
		if (csvData != null && !csvData.equals("")) {
			String[] csvRow = csvData.split("\r\n");
			if (csvRow != null && csvRow.length > 0) {
				for (int i = 1; i < csvRow.length; i++) {
					String[] message = new String[2];	
					String row = csvRow[i];
					String[] partInformation = row.split(",");
					String partNumber = partInformation[0];
					message[0]="��"+(i+1)+"�У�������Ϊ��"+partNumber+"����������ݵ���";
					String partName = partInformation[1];
					String partVersion = partInformation[2];
					String partVirtual = partInformation[3];
					if(partVirtual==null){
						partVirtual = "";
					}
					if(partNumber==null||partNumber.equals("")){
						message[1] = "ʧ�ܡ�ԭ�򣺱��Ϊ�գ�";
						vec.add(message);
						continue;
					}
					if(partVersion==null||partVersion.equals("")){
						message[1] = "ʧ�ܡ�ԭ�򣺰汾Ϊ�գ�";
						vec.add(message);
						continue;
					}
				//CCBegin SS24
//					Collection parts = queryPartForImport(partNumber,partVersion);
					Collection parts=new ArrayList();
					VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
					Collection partsLs = queryPartForImport(partNumber);
					if (partsLs!=null && partsLs.size()>0)
					{
						Iterator iterator2 = partsLs.iterator();
						QMPartIfc partObject=null;
						if (iterator2.hasNext()) {
							partObject=(QMPartIfc)iterator2.next();
						}
						if (partObject!=null){
			     		Collection col = vcservice.allVersionsOf(partObject);
							for(Iterator ites1 = col.iterator();ites1.hasNext();){
								QMPartIfc temPartIfc = (QMPartIfc)ites1.next();
								Collection svCol = getPartIBA(temPartIfc,"����Դ�汾","sourceVersion");
								if(svCol!=null&&svCol.size()>0){
	    						Iterator ibaItes = svCol.iterator();
	    						StringValueIfc existValue2 = (StringValueIfc)ibaItes.next();
	    						String sourceVersion = existValue2.getValue();
	    						if (partVersion.equals(sourceVersion))
	    						{
										parts.add(temPartIfc);
										break;
									}
								}
							}
						}
					}
					if (parts==null||parts.size()<=0){
						partsLs = queryPartForImport(partNumber,partVersion);
						if (partsLs!=null && partsLs.size()>0)
						{
							Iterator iterator2 = partsLs.iterator();
							QMPartIfc partObject=null;
							if (iterator2.hasNext()) {
								partObject=(QMPartIfc)iterator2.next();
								parts.add(partObject);
							}
						}
					}
				//CCEnd SS24
					if(parts==null||parts.size()<=0){
				//CCBegin SS24
//						message[1] = "ʧ�ܡ�ԭ�򣺲�ѯ���ʧ�ܣ�";
						message[1] = "ʧ�ܡ�ԭ�򣺲�ѯ�����"+"�汾("+partVersion+")ʧ�ܣ�";
				//CCEnd SS24
						vec.add(message);
						continue;
					}
					//���ܴ��ڶ���汾������޸�ÿ�������Ӧ��IBA����
					for(Iterator ite = parts.iterator();ite.hasNext();){
						QMPartIfc part = (QMPartIfc)ite.next();
						Collection ibaCol = getPartIBA(part,"ר�ü�","specialPart");
						//��ǰ����Ѿ�����IBAֵ������
						if(ibaCol!=null&&ibaCol.size()>0){
							Iterator ibaIte = ibaCol.iterator();
							StringValueIfc existValue = (StringValueIfc)ibaIte.next();
							existValue.setValue(partVirtual);
							service.saveValueInfo(existValue);
							message[1] = "����IBA���Գɹ���";
						//��ǰ���������IBAֵ������
						}else{
							StringValueIfc newValue = new StringValueInfo();
							newValue.setDefinitionBsoID(definition.getBsoID());
							newValue.setIBAHolderBsoID(part.getBsoID());
							newValue.setHierarchyID(definition.getHierarchyID());
							newValue.setValue(partVirtual);
							service.saveValueInfo(newValue);
							message[1] = "����IBA���Գɹ���";
						}	
					}
					vec.add(message);	
				
				}
			}
		}

		return vec;
	}

	//CCBegin SS28
	
	/**
	 * ����ָ���㲿����ţ������л���㲿���汾
	 * @param partNumber
	 * @return
	 */
	public String getNumberOfPartFromPen(String partNumber){
		String number = "";
		try{
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			String routesql="select beforeMaterial from ERPPan where afterMaterial ='"+partNumber+"'";
			rs=stmt.executeQuery(routesql);
			String[] str=new String[1];
			if(rs.next())
			{
				number=rs.getString("beforeMaterial");
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}
		return number;
	}
	
	/**
	 * ����㲿���Ƿ���ڣ������Ƿ��гɶ�·��
	 * @param csvData
	 * @return
	 * @throws QMException
	 */
	public HashMap checkPartFromExcel(String csvData) throws QMException {
//		System.out.println("csvData===="+csvData);
		String rawNumber = "";
		Vector vec = new Vector();
		Vector parts = new Vector();
		Vector noVersion = new Vector();
		QMPartIfc partObject=null;
		HashMap result = new HashMap();
		Vector routes = new Vector();
		if (csvData != null && !csvData.equals("")) {
			String[] csvRow = csvData.split("\r\n");
			if (csvRow != null && csvRow.length > 0) {
				for (int i = 0; i < csvRow.length; i++) {
					String row = csvRow[i];
					String[] partInformation = row.split(",");
					String partNumber = rawNumber=partInformation[0].trim();
					int l = 0;
					int ii= 0;
					String partVersion = "";
					String penNumber = "";
					if(partNumber.lastIndexOf("/")>-1 && partNumber.substring(partNumber.lastIndexOf("/")+1).length()==1)
					{
						
						l=partNumber.length();
						ii=partNumber.lastIndexOf("/");
						if(l-ii==2)
						{
							partVersion=partNumber.substring(l-1);
							partNumber=partNumber.substring(0,ii);
						}
						//							 System.out.println("number****************="+partNumber+"*******"+partVersion);
					}else {
						Collection coll = getPartByNumber(partNumber);
						//							System.out.println("number****************="+partNumber+"*******"+partVersion);
						Vector hadParts = new Vector();
						if(coll == null || coll.size() == 0){
							//ϵͳ��û������㲿��
							//								System.out.println("NOPart****************="+partNumber);
							if(!parts.contains(rawNumber))
								parts.add(rawNumber);
							continue;
						}
						//							System.out.println("ת��ǰ���㲿���ţ�"+partNumber);
						penNumber = getNumberOfPartFromPen(partNumber);
						//							System.out.println("ת������㲿���ţ�"+penNumber);
						if(penNumber != null && !penNumber.equals("")){
							l=penNumber.length();
							ii=penNumber.lastIndexOf("/");
							if(l-ii==2)
							{
								partVersion=penNumber.substring(l-1);
								partNumber=penNumber.substring(0,ii);
							}
						}else{
							//								�ж��Ƿ�����ް汾����
							//								System.out.println("�ް汾�����Ҳ�������ģ�"+partNumber);
							Collection col = getPartByNumber(partNumber);
							if(col==null||col.size()==0){
								//									System.out.println("�ü��ް汾������ϵͳ��û�иü���"+partNumber);
								if(!noVersion.contains(rawNumber))
									noVersion.add(rawNumber);
								continue;
							}
							partObject =(QMPartIfc)col.iterator().next();
							//								System.out.println("parttype========"+partObject.getPartType().getDisplay());
							//								System.out.println("parttype-----------"+partObject.getPartType().getValue());

							PartHelper helper = new PartHelper();
							//								System.out.println("partNumber==="+partNumber+"-----Version------"+helper.ifHasVersion(partNumber, partObject.getPartType().getValue()));
							if(!helper.ifHasVersion(partNumber, partObject.getPartType().getValue())){
								//									System.out.println("�ü��������ް汾����"+partNumber);
								if(!noVersion.contains(rawNumber))
									noVersion.add(rawNumber);
								continue;
							}else{
								partVersion="A";
							}
						}
					}
					if(partVersion== null || partVersion==""){
						if(!noVersion.contains(rawNumber))
							noVersion.add(rawNumber);
						continue;
					}
					partObject = isHasPart(partNumber,partVersion,partObject);
					Vector hadParts = new Vector();
					if(partObject == null || partVersion == ""){
						if(rawNumber.lastIndexOf("/")>-1 && rawNumber.substring(rawNumber.lastIndexOf("/")+1).length()==1)
						{
							String penstr = getNumberOfPartFromPen(rawNumber);
							if(penstr != null && penstr!= "") {
								partNumber = penstr;
							}
							int l1=penNumber.length();
							int ii1=penNumber.lastIndexOf("/");
							if(l1-ii1==2)
							{
								partVersion=penNumber.substring(l1-1);
								partNumber=penNumber.substring(0,ii1);
							}
							partObject = isHasPart(partNumber,partVersion,partObject);
						}
						//ϵͳ��û������㲿��
						if(partObject == null && !parts.contains(rawNumber))
							parts.add(rawNumber);
						continue;
					}else
					{
						//�жϸ��㲿���Ƿ��гɶ�����·��
						try{
							Connection conn=null;
							Statement stmt=null;
							ResultSet rs=null;
							conn=PersistUtil.getConnection();
							stmt=conn.createStatement();
							String routesql="select mainStr,rightBsoID from consListRoutePartLink where releaseIdenty='1' and rightBsoID ='"+partObject.getBsoID()+"'";
							//System.out.println("#####==="+partObject.getBsoID()+"---------"+partObject.getPartNumber()+"-----partVersion------"+partVersion);
							rs=stmt.executeQuery(routesql);
							String[] str=new String[2];
							if(!rs.next())
							{
								if(!routes.contains(rawNumber))
									routes.add(rawNumber);
							}
						}catch(Exception exc){
							exc.printStackTrace();

						}
					}
				}
			}
		}
		if(parts.size()>0){
			result.put("parts", parts);
		}
		if(routes.size()>0){
			result.put("routes", routes);
		}
		result.put("noversion", noVersion);
		return result;
	}
	//CCEnd SS28
	
	
	/**
	 * ��ѯ���IBA���������������
	 * @return Object[]
	 */

	private StringDefinitionIfc queryDefinitionForSpec() throws QMException {
		Vector vec = new Vector();
		StringDefinitionIfc defIfc=null;
		PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("StringDefinition");
        query.addCondition(new QueryCondition("name", QueryCondition.EQUAL,"specialPart"));
        Collection col = service.findValueInfo(query, false);
        for(Iterator iterator = col.iterator();iterator.hasNext();)
        {
            defIfc = (StringDefinitionIfc)iterator.next();
           // bsoID = defIfc.getBsoID();
            
        }
		return defIfc;
	}
	/**
	 * ��ѯ���IBA���������������
	 * @return Object[]
	 */

	private StringDefinitionIfc queryDefinition() throws QMException {
		Vector vec = new Vector();
		StringDefinitionIfc defIfc=null;
		PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("StringDefinition");
        query.addCondition(new QueryCondition("name", QueryCondition.EQUAL,"virtualPart"));
        Collection col = service.findValueInfo(query, false);
        for(Iterator iterator = col.iterator();iterator.hasNext();)
        {
            defIfc = (StringDefinitionIfc)iterator.next();
           // bsoID = defIfc.getBsoID();
            
        }
		return defIfc;
	}
	/**
	 * ��ѯ���IBA����ֵ
	 * @return Object[]
	 */
	public static Collection getPartIBA(QMPartIfc part, String ibaDisplayName,String sName) throws QMException {
		  String ibavalue = "";
		  try {
		   PersistService pService = (PersistService) EJBServiceHelper
		   .getPersistService();
		   QMQuery query = new QMQuery("StringValue");
		   int j = query.appendBso("StringDefinition", false);
		   QueryCondition qc = new QueryCondition("iBAHolderBsoID", "=", part
		     .getBsoID());
		   query.addCondition(qc);
		   query.addAND();
		   QueryCondition qc1 = new QueryCondition("definitionBsoID", "bsoID");
		   query.addCondition(0, j, qc1);
		   query.addAND();
		   QueryCondition qc2 = new QueryCondition("name", " = ", sName);
		   query.addCondition(j, qc2);
		   query.addAND();
		   QueryCondition qc3 = new QueryCondition("ibaDisplayName", " = ", ibaDisplayName);
		   query.addCondition(j, qc2);
		   Collection col = pService.findValueInfo(query, false);
		   return col;
		  } catch (Exception e) {
		   e.printStackTrace();
		   throw new QMException(e);
		  }
	
		 }
	/**
	 * �����������ʶ�����ݵ����ļ����ݣ���ѯ�������������
	 * @param String partNumber ������
	 * @param String version ����汾
	 * @return String bsoID
	 */

	private Collection queryPartForImport(String partNumber,String version) throws QMException {
		String bsoID = "";
		PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("QMPart");
		int i = query.appendBso("QMPartMaster", false);
         //����2����
	    QueryCondition qc1 = new QueryCondition("masterBsoID", "bsoID");
	    query.addCondition(0, i, qc1);
	    query.addAND();
        query.addCondition(i,new QueryCondition("partNumber", QueryCondition.EQUAL,partNumber));
        query.addAND();
        query.addCondition(new QueryCondition("versionID", QueryCondition.EQUAL,version));
        query.addAND();
        query.addCondition(new QueryCondition("iterationIfLatest", true));
      
        Collection col = service.findValueInfo(query, false);
        
		return col;
	}
				//CCBegin SS24
	private Collection queryPartForImport(String partNumber) throws QMException {
		String bsoID = "";
		PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("QMPart");
		int i = query.appendBso("QMPartMaster", false);
         //����2����
	    QueryCondition qc1 = new QueryCondition("masterBsoID", "bsoID");
	    query.addCondition(0, i, qc1);
	    query.addAND();
        query.addCondition(i,new QueryCondition("partNumber", QueryCondition.EQUAL,partNumber));
        query.addAND();
        query.addCondition(new QueryCondition("iterationIfLatest", true));
      
        Collection col = service.findValueInfo(query, false);
        
		return col;
	}
				//CCEnd SS24
	
	//CCBegin SS28
	/**
	 * ���ݱ�Ż���㲿������
	 * @param partNumber
	 * @return
	 * @throws QMException
	 */
	public Collection getPartByNumber(String partNumber)throws QMException{
		PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("QMPart");
		int i = query.appendBso("QMPartMaster", false);
		//����2����
		QueryCondition qc1 = new QueryCondition("masterBsoID", "bsoID");
		query.addCondition(0, i, qc1);
		query.addAND();
		query.addCondition(i,new QueryCondition("partNumber", QueryCondition.EQUAL,partNumber));
		query.addAND();
		query.addCondition(new QueryCondition("iterationIfLatest", true));
		query.setChildQuery(false);
		Collection col = service.findValueInfo(query);
//		System.out.println("sql===="+query.getDebugSQL());
		return col;
	}
	
	/**
	 * �����㲿����Ų�ѯϵͳ���Ƿ����ָ�����㲿��
	 * @param partNumber
	 * @return
	 * @throws QMException
	 */
	private QMPartIfc isHasPart(String partNumber,String partVersion,QMPartIfc partObject) throws QMException {
		String bsoID = "";
		Collection col = getPartByNumber(partNumber);
		
		VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
		//System.out.println("col----------------"+col.size());
		if(col!=null && col.size()>0){
			Iterator iterator2 = col.iterator();
			QMPartIfc sysVersionObject = null;
			while(iterator2.hasNext()) {
				partObject=(QMPartIfc)iterator2.next();
				if (partObject!=null){
					Collection coll = vcservice.allVersionsOf(partObject);
					//System.out.println("ָ�����㲿���ǣ�"+partObject.getPartNumber()+",�汾�ǣ�"+partObject.getVersionID());
					//�Ȳ��ҷ���Դ�汾���ٲ���ϵͳ�汾
					for(Iterator ites1 = coll.iterator();ites1.hasNext();){
						QMPartIfc temPartIfc = (QMPartIfc)ites1.next();
						Collection svCol = getPartIBA(temPartIfc,"����Դ�汾","sourceVersion");
						//System.out.println("svCol------"+svCol+"=====temPartIfc=="+temPartIfc.getBsoID());
						//System.out.println("partObject------"+partObject.getBsoID());
						if(svCol!=null&&svCol.size()>0){
							Iterator ibaItes = svCol.iterator();
							StringValueIfc existValue2 = (StringValueIfc)ibaItes.next();
							String sourceVersion = existValue2.getValue();
							sourceVersion = sourceVersion.substring(0,sourceVersion.indexOf("."));
							//System.out.println("����㲿���з���Դ�汾��"+sourceVersion);
							//System.out.println("partNumber------"+partNumber+"---sourceVersion--"+sourceVersion);
							if (partVersion.equals(sourceVersion))
							{
								//System.out.println("����Դ�汾��������"+temPartIfc.getBsoID());
								partObject = temPartIfc;
								return partObject;
							}
						}
						/*else{
						String sysVersion = temPartIfc.getVersionID();
						System.out.println("����㲿��û�з���Դ�汾��ѭ��ϵͳ�汾��"+sysVersion);
						if(partVersion.equals(sysVersion)){
							System.out.println("����㲿���ж�Ӧ��ϵͳ�汾��"+sysVersion);
							partObject = temPartIfc;
							return partObject;
						}
					}*/
					}
					if(partObject.getVersionID().equals(partVersion)){
						//System.out.println("ϵͳ�汾��������"+partObject.getBsoID());
						sysVersionObject = partObject;
					}
				}
			}
			//System.out.println("û�ҵ�����Դ�汾��"+(sysVersionObject!=null));
			return sysVersionObject;
		}else{
			return null;
		}
}
	//CCEnd SS28
	
	   /**
	    * ���ݷ�����ID������BOM
	    * @param GYBomAdoptNoticeIfc ifc
	    * @return Collection
	    * @throws QMException 
	    */
		public  Collection getBomPartFromBomAdoptNotice(GYBomAdoptNoticeIfc ifc)throws QMException
		{
			Collection   col  = new Vector();
			if(ifc!=null){
				PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
				QMQuery query = new QMQuery("GYBomAdoptNoticePartLink");
				QueryCondition qc = new QueryCondition("noticeID", QueryCondition.EQUAL,ifc.getBsoID());
				query.addCondition(qc);
		        col= (Collection)pservice.findValueInfo(query);
			}
			
			return col;
		}
	 /**
	    * ���ݷ�����IDɢ���嵥
	    * @param String bsoID
	    * @return Collection
	    * @throws QMException 
	    */
		public  Collection getInvoiceByNotice(String bsoID)throws QMException
		{
			
			PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
			QMQuery query = new QMQuery("GYInvoice");
			QueryCondition qc = new QueryCondition("noticeID", QueryCondition.EQUAL,bsoID);
			query.addCondition(qc);
	        Collection   col = (Collection)pservice.findValueInfo(query);
			return col;
		}
		/**
	     * ����ɢ���嵥
	     * @param Object[] data ���ݼ���
	     * @param GYBomAdoptNoticeIfc ifc ����������
	     * @return Object[]
	     */

		public Vector saveInvoice(Vector linkVec,GYBomAdoptNoticeIfc ifc) throws QMException
	    {
	        Vector vec = new Vector();
	        PersistService service = (PersistService)EJBServiceHelper.getService("PersistService");
	      
	       try
	        {
	    	   //����Ѿ����ڵĹ���
	    	   Collection existInvoice = getInvoiceByNotice(ifc.getBsoID());
	    	   if(existInvoice!=null&&existInvoice.size()>0){
	    		   for(Iterator ites = existInvoice.iterator();ites.hasNext();){
	    			   GYInvoiceIfc existIfc = (GYInvoiceIfc)ites.next();
	    			   service.deleteValueInfo(existIfc);
	    		   }
	    	   }
	    	   
	    	   if(linkVec!=null&&linkVec.size()>0){
	    		   String noticeID = ifc.getBsoID();
	    		   for(Iterator ite = linkVec.iterator();ite.hasNext();){
	    			   GYInvoiceInfo link = (GYInvoiceInfo)ite.next();
	    			   link.setNoticeID(noticeID);
	    			   link = (GYInvoiceInfo)service.saveValueInfo(link);
	    			   vec.add(link);
	    		   }
	    	   }
	        }catch(QMException e)
	        {
	            setRollbackOnly();
	            throw new QMException(e);
	        }
	        return vec;
	    }
		
		/**
	 * ��õ������������й���BOM������
	 * @param ifc ��������������
	 * @return �����Ƿ�ɹ�
	 * @throws QMException
	 * @author houhf
	 */
	public Vector<Object[]> exportAllBOM(GYBomAdoptNoticeIfc ifc)
			throws QMException {
		PersistService pservice = (PersistService)EJBServiceHelper
														.getPersistService();
		//�����������صļ���
		Vector<Object[]> result = new Vector<Object[]>();
		//���ҹ�������������BOM��Ϣ����ʱ����
		Vector<Object> tempVec = new Vector<Object>();
		//ͨ��������������������BOM���浽����������
		QMPartInfo zcPart = (QMPartInfo)pservice.refreshInfo(ifc.getTopPart());
//		QMPartInfo zcPart = (QMPartInfo) findQMPartByPartNumber(ifc
//				.getTopPart());
		//ȡ���ļ��ϵ�һ��Ԫ����QMPartifc
		
		tempVec = (Vector<Object>) getReleaseBom(zcPart);
		if (tempVec != null && tempVec.size() > 0) {
			for (int i = 0; i < tempVec.size(); i++) {
				Object[] obj = (Object[]) tempVec.get(i);
				QMPartInfo part = (QMPartInfo) obj[0];
				String route[] = (String[]) obj[2];
				//����
				String level = "";
				//���
				String partNumber = part.getPartNumber();
				//����
				String partName = part.getPartName();
				//�汾
				String version = part.getVersionValue();
				//CCBegin SS5
				//����
				String quantity = (String) obj[4];
				//����·��
				String manuRoute = route[0];
				//װ��·��
				String assRoute = route[1];
				//��ͼ
				String view = part.getViewName();
				
				//��׼���
				String listNumber = route[2];
				//CCEnd SS5
				//��������״̬
				String lifecycleState = part.getLifeCycleState().toString();
				//ר�ü�
				StringValueIfc strvalueifc = getStringValue(part, "ר�ü�");
				String zhuanyong = "";
				if(strvalueifc != null)
				{
					zhuanyong = strvalueifc.getValue();
				}
				
				//�����ʾ
				StringValueIfc strvalueifc1 = getStringValue(part, "�����");
				String dummy = "";
				if(strvalueifc1 != null)
				{
					dummy = strvalueifc1.getValue();
				}

				Object[] retObj = new Object[12];
				retObj[0] = level;
				retObj[1] = partNumber;
				retObj[2] = partName;
				retObj[3] = version;
				retObj[4] = quantity;
				retObj[5] = manuRoute;
				retObj[6] = assRoute;
				retObj[7] = view;
				retObj[8] = listNumber;
				retObj[9] = lifecycleState;
				retObj[10] = zhuanyong;
				retObj[11] = dummy;

				result.add(retObj);
				//�ж��Ƿ��Ѿ������part
				//					if(partNumberMap.containsKey(part.getPartNumber()))
				//					{
				//					continue;
				//					}
				//					else
				//					{
				//					partVec.add(part);
				//					partNumberMap.put(part.getPartNumber(), part);
				//					}
			}
		}
		//ͨ���������������������ӷ�����
		//�ӹ��ղ��õ�����
		Vector<GYBomAdoptNoticeInfo> GYSub = new Vector<GYBomAdoptNoticeInfo>();
		GYSub = (Vector<GYBomAdoptNoticeInfo>) getSubGYBomAdoptNotice((GYBomAdoptNoticeInfo) ifc);
		//ͨ���ӷ������ҳ�����BOM���뵽����BOM��
		if (GYSub != null && GYSub.size() > 0) {
			for (int i = 0; i < GYSub.size(); i++) {
				tempVec.clear();
				GYBomAdoptNoticeIfc subInfo = GYSub.get(i);
				//ͨ����������������BOM���浽����������
				QMPartInfo subPart = (QMPartInfo)pservice.refreshInfo(ifc.getTopPart());
//				QMPartInfo subPart = (QMPartInfo) findQMPartByPartNumber(ifc
//						.getTopPart());
				//ȡ���ļ��ϵ�һ��Ԫ����QMPartifc
				tempVec = (Vector<Object>) getReleaseBom(subPart);
				if (tempVec != null && tempVec.size() > 0) {
					for (int ii = 0; ii < tempVec.size(); ii++) {
						Object[] obj = (Object[]) tempVec.get(i);
						QMPartInfo part = (QMPartInfo) obj[0];
						String route[] = (String[]) obj[2];
						//����
						String level = "";
						//���
						String partNumber = part.getPartNumber();
						//����
						String partName = part.getPartName();
						//�汾
						String version = part.getVersionValue();
						//����
						String quantity = (String) obj[1];
						//����·��
						String manuRoute = route[0];
						//װ��·��
						String assRoute = route[1];
						//��ͼ
						String view = part.getViewName();
						//��׼���
						String listNumber = route[3];
						//��������״̬
						String lifecycleState = part.getLifeCycleState()
								.toString();
						//ר�ü�
						StringValueIfc strvalueifc = getStringValue(part, "ר�ü�");
						String zhuanyong = strvalueifc.getValue();
						//�����ʾ
						StringValueIfc strvalueifc1 = getStringValue(part,
								"�����");
						String dummy = strvalueifc1.getValue();

						Object[] retObj = new Object[12];
						retObj[0] = level;
						retObj[1] = partNumber;
						retObj[2] = partName;
						retObj[3] = version;
						retObj[4] = quantity;
						retObj[5] = manuRoute;
						retObj[6] = assRoute;
						retObj[7] = view;
						retObj[8] = listNumber;
						retObj[9] = lifecycleState;
						retObj[10] = zhuanyong;
						retObj[11] = dummy;

						result.add(retObj);
					}
				}
			}
		}

		return result;
	}

	/**ͨ���㲿����Ų����㲿��masterֵ����
	 * @param partNumber
	 * @return
	 * @throws QMException
	 * @author houhf
	 */
	private QMPartMasterIfc findPartMasterByNumber(String partNumber)
			throws QMException {
		try {
			PersistService pservice = (PersistService) EJBServiceHelper
					.getPersistService();
			QMQuery query = new QMQuery("QMPartMaster");
			query
					.addCondition(new QueryCondition("partNumber", "=",
							partNumber));
			Collection<QMPartMasterInfo> coll = pservice.findValueInfo(query);
			if (coll == null || coll.size() == 0) {
				return null;
			} else {
				return (QMPartMasterIfc) coll.toArray()[0];
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}

	/**
	 * �����㲿���ı�ţ�����ϵͳ�з������ù淶���㲿��
	 * 
	 * @param partNumber
	 *            �㲿�����
	 * @return QMPartIfc
	 * @throws QMException
	 * @author houhf
	 */
	private QMPartIfc findQMPartByPartNumber(String partNumber)
			throws QMException {
		try {
			StandardPartService sservice = (StandardPartService) EJBServiceHelper
					.getService("StandardPartService");
			QMPartMasterIfc master = findPartMasterByNumber(partNumber);
			if (master == null) {
				return null;
			} else {
				QMPartIfc part = sservice.getPartByConfigSpec(master,
						(PartConfigSpecIfc) PartServiceRequest
								.findPartConfigSpecIfc());
				if (part == null) {
					throw new QMException("���ݵ�ǰ�����ù淶�����Ϊ��" + partNumber
							+ "û���ҵ���Ӧ���㲿����������������ù淶��");
				} else {
					return part;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}

	/**����String�͵�IBA���Ե�ֵ����
	 * @param ibaHolder
	 * @param name
	 * @return StringValueIfc
	 * @throws QMException
	 * @author houhf
	 */
	private StringValueIfc getStringValue(IBAHolderIfc ibaHolder, String name)
			throws QMException {
		PersistService pService = (PersistService) EJBServiceHelper
				.getPersistService();
		QMPartIfc partifc = (QMPartIfc) ibaHolder;
		String bsoID = partifc.getBsoID();
		QMQuery query = new QMQuery("StringValue");
		int i = query.appendBso("StringDefinition", false);
		QueryCondition cond = new QueryCondition("iBAHolderBsoID", "=", bsoID);
		query.addCondition(0, cond);
		query.addAND();
		QueryCondition cond1 = new QueryCondition("displayName", "=", name);
		query.addCondition(i, cond1);
		query.addAND();
		QueryCondition cond2 = new QueryCondition("definitionBsoID", "bsoID");
		query.addCondition(0, i, cond2);
		Collection coll = pService.findValueInfo(query);
		if(coll != null && coll.size()>0)
		{
			return (StringValueIfc) coll.iterator().next();
		}
		else
		{
			return null;
		}
//		return (StringValueIfc) coll.iterator().next();
	}

	/**
	 * ��ָ����excel�ļ�д�����������й���BOM����
	 * @param path Excel�ļ�ȫ·��
	 * @param ifc ������
	 * @author houhf
	 * @throws IOException,QMException 
	 */
	private void exportAllBOM(GYBomAdoptNoticeIfc ifc, String path)
			throws IOException, QMException {
		//���ص�BOM����
		Vector<Object[]> result = new Vector<Object[]>();
		//���ϵ���BOM��Ϣ
		StringBuffer backBuffer = new StringBuffer();
		String head = "��������" + ifc.getAdoptnoticenumber() + "("
				+ ifc.getAdoptnoticename() + ")" + " �Ĺ���BOM�嵥" + "\n";
		backBuffer.append(head);

		String table = "����,���,����,�汾,����,����·��,װ��·��,��ͼ,��׼���,��������״̬,ר�ü�,�����ʾ,"
				+ "\n";
		backBuffer.append(table);

		result = exportAllBOM(ifc);
		if (result != null && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				String value = "";
				Object obj[] = result.get(i);
				value = (String) obj[0] + ",";
				value += (String) obj[1] + ",";
				value += (String) obj[2] + ",";
				value += (String) obj[3] + ",";
				value += (String) obj[4] + ",";
				value += (String) obj[5] + ",";
				value += (String) obj[6] + ",";
				value += (String) obj[7] + ",";
				value += (String) obj[8] + ",";
				value += (String) obj[9] + ",";
				value += (String) obj[10] + ",";
				value += (String) obj[11] + ",";
				value += "\n";
				backBuffer.append(value);
			}
		}

		FileWriter fw = new FileWriter(path, false);
		fw.write(backBuffer.toString());
		fw.close();
	}
//CCBegin SS8
	/**
     * ����Bom
     * @param String csvData CSV�ļ�����
     * @return Object[]
     */

	public String importBom(String csvData) throws QMException
	{
		if (csvData == null || csvData.equals("")) {
			return "������ļ�Ϊ�գ�";
		
		}
		LifeCycleService lcservice = (LifeCycleService)EJBServiceHelper.getService("LifeCycleService");
		String[] csvRow = csvData.split("\r\n");
		if (csvRow != null && csvRow.length > 0) {
			VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
			AccessControlService as = (AccessControlService) EJBServiceHelper.getService("AccessControlService");
	    PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
			String classification=null;
			String adoptnoticeName=null;
			String parentBomChangeNotice=null;
			String designDocNumber=null;
			String workShop=null;
			String remark=null;
			String zxDocNumber=null;
			HashMap workShopMap = new HashMap();
			//CCBegin SS22
//          ����ҳ�洫�ݵı�ʶ���룬��ñ�ʶ������룬����������õ��Զ����
            String bscode = null;
            String adoptNumberStr = null;
            HashMap<String,String> bscodeMap =  new HashMap();
            //CCEnd SS22
//			String folder;
			//���֪ͨ��
			String[] bomChangeNoticeValue = csvRow[1].split(",");
			classification=bomChangeNoticeValue[0];
//			CCBegin SS22
			adoptNumberStr=bomChangeNoticeValue[1];
			adoptnoticeName=bomChangeNoticeValue[2];
			workShop=bomChangeNoticeValue[3];
			bscode=bomChangeNoticeValue[4];
			zxDocNumber=bomChangeNoticeValue[5];
			designDocNumber=bomChangeNoticeValue[6];
			parentBomChangeNotice=bomChangeNoticeValue[7];			
			if (bomChangeNoticeValue.length>8)
				remark=bomChangeNoticeValue[8];
			  //CCEnd SS22
/*
			for (int j = 0; j < 7; j++) {
				String row = csvRow[j];
				String[] str = row.split(",");
				if (str[0].equals("classificationBegin"))
				{
					classification=str[1];
				}
				if (str[0].equals("adoptnoticeNameBegin"))
				{
					adoptnoticeName=str[1];
				}
				if (str[0].equals("parentBomChangeNoticeBegin"))
				{
					parentBomChangeNotice=str[1];
				}
				if (str[0].equals("designDocNumberBegin"))
				{
					designDocNumber=str[1];
				}
				if (str[0].equals("workShopBegin"))
				{
					workShop=str[1];
				}
				if (str[0].equals("remarkBegin"))
				{
					remark=str[1];
				}
				if (str[0].equals("zxDocNumberBegin"))
				{
					zxDocNumber=str[1];
				}
			}			
*/
			String classificationJh = RemoteProperty.getProperty("com.faw_qm.bomNotice.BomChangeNoticeClassification", "�汾����;ϵ�г����滻;ʵ������;��ʿ;��������;����");
			String workShopJh =RemoteProperty.getProperty("com.faw_qm.bomNotice.BomChangeNoticeWorkShop", "���һ��:Y;��ƶ���:E;ר�ó�:Z");
			String folder =RemoteProperty.getProperty("com.faw_qm.bomNotice.BomChangeNoticeFolder", "\\Root\\��Ʊ��֪ͨ��");

//			CCBegin SS22
		    String bscodeProperty =RemoteProperty.getProperty("com.faw_qm.bomNotice.BomChangeNoticeBsCode", "��׼:Z;ǰ׼:Q;����:S;����:C;����:F");
		    //CCEnd SS22
      //У������Ƿ���ȷ
      String[] classificationVec = classificationJh.split(";");
      boolean classificationFlag=false;
      if(classificationVec!=null&&classificationVec.length>0){
          for(int i = 0 ; i <classificationVec.length;i++){
          		if (classificationVec[i].equals(classification))
          		{
          			classificationFlag=true;
          			break;
              }
          }
      }
      if(!classificationFlag){
				setRollbackOnly();
				return "���಻��ȷ��";			
     }
     
			//У�����Ʋ���Ϊ��
			if (adoptnoticeName==null || adoptnoticeName.length()==0)
			{
				setRollbackOnly();
				return "���֪ͨ�����Ʋ���Ϊ�գ�";			
			}
			
      //У�鲿���Ƿ���ȷ
      String[] workShopVec = workShopJh.split(";");
      boolean workShopFlag=false;
      if(workShopVec!=null&&workShopVec.length>0){
           for(int i = 0 ; i <workShopVec.length;i++){
              String temShop = workShopVec[i];
              String[] temShopVec = temShop.split(":");
              
              if(temShopVec!=null&&temShopVec.length>0){
                  workShopMap.put(temShopVec[0], temShopVec[1]);
            			if (temShopVec[0].equals(workShop))
            			{
            				workShopFlag=true;
            				//break;
                	}
              }
              
          }
      }
      if(!workShopFlag){
				setRollbackOnly();
				return "���Ų���ȷ��";			
     	}
//		CCBegin SS22
//		У���Ų���Ϊ��
		if (adoptNumberStr==null || adoptNumberStr.length()==0)
		{
			setRollbackOnly();
			return "���ͨ��Ų���Ϊ�գ�";			
		}
//		�����ʶ����Ϊ��
		if (bscode==null || bscode.length()==0)
		{
			setRollbackOnly();
			return "���ͨ�����ʶ����Ϊ�գ�";			
		}
	      //У���ʶ�����Ƿ���ȷ
	      String[] bscodeVec = bscodeProperty.split(";");
	      boolean bscodeFlag=false;
	      if(bscodeVec!=null&&bscodeVec.length>0){
	           for(int i = 0 ; i <bscodeVec.length;i++){
	              String temShop = bscodeVec[i];
	              String[] temShopVec = temShop.split(":");
	              
	              if(temShopVec!=null&&temShopVec.length>0){
	            	  bscodeMap.put(temShopVec[0], temShopVec[1]);
	            			if (temShopVec[0].equals(bscode))
	            			{
	            				bscodeFlag=true;
	            				//break;
	                	}
	              }
	              
	          }
	      }
	      if(!bscodeFlag){
					setRollbackOnly();
					return "��ʶ���벻��ȷ��";			
	     	}
	      
		  //CCEnd SS22
		
     	//���Ĳ���/������Ƿ���ȷ
	    DocInfo zxDocIfc=null;
     	if (zxDocNumber!=null && zxDocNumber.length()>0)
     	{
	    	DocMasterIfc zxDocMasterIfc=getDocMasterByNumber(zxDocNumber);
				if (zxDocMasterIfc!=null){
	     		Collection col = vcservice.allVersionsOf(zxDocMasterIfc);
					Iterator iterator1 = col.iterator();
					if (iterator1.hasNext()) {
						zxDocIfc = (DocInfo) iterator1.next();
					}
				}
				if (zxDocMasterIfc==null || zxDocIfc==null){
					setRollbackOnly();
					return "���Ĳ���/�������Ų���ȷ��";
				}
   		}
   		//������������Ƿ���ȷ
	    DocInfo designDocIfc=null;
     	if (designDocNumber!=null && designDocNumber.length()>0)
     	{
	    	DocMasterIfc designDocMasterIfc=getDocMasterByNumber(designDocNumber);
				if (designDocMasterIfc!=null){
	     		Collection col = vcservice.allVersionsOf(designDocMasterIfc);
					Iterator iterator1 = col.iterator();
					if (iterator1.hasNext()) {
						designDocIfc = (DocInfo) iterator1.next();
					}
				}
				if (designDocMasterIfc==null || designDocIfc==null){
					setRollbackOnly();
					return "����������񵥱�Ų���ȷ��";
				}
			}			
			//��������Ƿ���ȷ
			BomChangeNoticeIfc bomChangeNoticeIfc=null;
     	if (parentBomChangeNotice!=null && parentBomChangeNotice.length()>0)
     	{
				bomChangeNoticeIfc=getBomChangeNoticeByNumber(parentBomChangeNotice);
				if (bomChangeNoticeIfc==null){
					setRollbackOnly();
					return "���������Ų���ȷ��";
				}
			}
			
			
			//�������
     	//�������б�
      Vector noticePartLinkVec = new Vector();
      //Ҫ�޶��İ汾�ƻ�
      Vector receiveVec = new Vector();
      Vector cyVec = new Vector();
      Vector bcyVec = new Vector();
//CCBegin SS17
	    PartConfigSpecIfc configSpecGY = getPartConfigSpecByViewName("������ͼ");

//CCEnd SS17
			for (int i = 3; i < csvRow.length; i++) {
				String[] message = new String[2];	
				String row = csvRow[i];
				String[] partInformation = row.split(",");
				String	addBs=partInformation[5].toUpperCase();
				//����
				String parentNumber = partInformation[0];
//CCBegin SS17
				message[0]="������ͼ�µ�"+(i+1)+"�У���������Ϊ��"+parentNumber+"����������ݵ���";
//CCEnd SS17
				if(parentNumber==null||parentNumber.equals("")){
					message[1] = "ʧ�ܡ�ԭ�򣺸�������Ϊ�գ�";
					setRollbackOnly();
					return message[0]+message[1];
				}
				QMPartMasterIfc parentMasterIfc = getPartMasterByNumber(parentNumber);
//CCBegin SS17
//	     	QMPartInfo parentPartIfc=null;
	     	QMPartIfc parentPartIfc=null;
//CCEnd SS17
				QMPartIfc parentNewPartIfc=parentPartIfc;
				if (parentMasterIfc!=null){
//CCBegin SS17
//	     		Collection col = vcservice.allVersionsOf(parentMasterIfc);
//					Iterator iterator1 = col.iterator();
//					if (iterator1.hasNext()) {
//						parentPartIfc = (QMPartInfo) iterator1.next();
//						parentNewPartIfc=parentPartIfc;
//					}
	          parentPartIfc = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)parentMasterIfc, configSpecGY);
						parentNewPartIfc=parentPartIfc;	          
//CCEnd SS17
					//�����ǰ���¼�Ѱ����˴˼���Ӧȡ���°����һ���汾ΪparentPartIfc�����°汾ΪparentNewPartIfc
//CCBegin SS17
//					if (receiveVec.contains(parentNumber) && iterator1.hasNext()){
//						parentPartIfc = (QMPartInfo) iterator1.next();
					if (receiveVec.contains(parentNumber) && parentPartIfc!=null && parentPartIfc.getPredecessorID()!=null){
						parentPartIfc = (QMPartInfo) pservice.refreshInfo(parentPartIfc.getPredecessorID());
//CCEnd SS17
					}
				}
				if (parentMasterIfc==null || parentPartIfc==null){
					message[1] = "ʧ�ܡ�ԭ�������š�"+parentNumber+"�������ڣ�";
					setRollbackOnly();
					return message[0]+message[1];
				}
				//����Ҫ���޶�Ȩ��
				if	(!as.hasAccess(parentPartIfc, QMPermission.REVISE)){
					message[1] = "ʧ�ܡ�ԭ�������š�"+parentNumber+"�����޶�Ȩ�ޣ�";
					setRollbackOnly();
					return message[0]+message[1];
				}
				//���Ƕ��ͬһ���ֻ�޶�һ���汾
				if (!receiveVec.contains(parentNumber))
				{			
					receiveVec.add(parentNumber);
					parentNewPartIfc=(QMPartIfc)vcservice.newVersion(parentPartIfc);
//					CCBegin SS22
					//��ȡ������������ģ��
					  String templateID = ((LifeCycleManagedIfc)parentNewPartIfc).getLifeCycleTemplate();
					  LifeCycleTemplateInfo template = (LifeCycleTemplateInfo)pservice.refreshInfo(templateID);
					  LifeCycleTemplateMasterInfo master = (LifeCycleTemplateMasterInfo)template.getMaster();//��ȡ��������ģ��master��
					  template = lcservice.getLifeCycleTemplate(master.getLifeCycleName());//��ȡ���µ���������ģ�塣
					  parentNewPartIfc = (QMPartIfc) lcservice.setLifeCycle(parentNewPartIfc, template);//����ָ����������
//						CCEnd SS22
					pservice.saveValueInfo(parentNewPartIfc);
					
          if (!cyVec.contains(parentNewPartIfc.getBsoID()))
          {
	          cyVec.add(parentNewPartIfc.getBsoID());
	          BomChangeNoticePartLinkIfc link = new BomChangeNoticePartLinkInfo();
	          link.setAdoptBs("����");
	 	       	link.setPartID(parentNewPartIfc.getBsoID());
	          link.setPartName(parentNewPartIfc.getPartName());
	          link.setPartNumber(parentNewPartIfc.getPartNumber());
	          link.setIsNew("0");
	          link.setIsSubPart("0"); //����ͨ������Ӽ���ӵ�
	          link.setVersionValue(parentNewPartIfc.getVersionValue());
	          link.setLinkPart(parentPartIfc.getBsoID());
	          link.setNumberAuto("1");
	          link.setBz1(NoticeHelper.BBBG);
	          noticePartLinkVec.add(link);
					}
          if (!bcyVec.contains(parentPartIfc.getBsoID()))
          {
	          cyVec.add(parentPartIfc.getBsoID());
	          BomChangeNoticePartLinkIfc link1 = new BomChangeNoticePartLinkInfo();
	          link1.setAdoptBs("������");
	 	       	link1.setPartID(parentPartIfc.getBsoID());
	          link1.setPartName(parentPartIfc.getPartName());
	          link1.setPartNumber(parentPartIfc.getPartNumber());
	          link1.setVersionValue(parentPartIfc.getVersionValue());
	          link1.setNumberAuto("1");
	          link1.setBz1(NoticeHelper.BBBG);
	          noticePartLinkVec.add(link1);
					}
				}
				//�����Ӽ�����
				String addNumber = partInformation[1];
				String addNumberSum = partInformation[2];
				if (addNumberSum==null || addNumberSum.length()==0)
					addNumberSum="1";
				if (addNumber!=null && addNumber.length()>0)
				{
					QMPartMasterIfc addMasterIfc = getPartMasterByNumber(addNumber);
		     	QMPartIfc addIfc=null;
					if (addMasterIfc!=null){
//CCBegin SS17
//						Collection col = vcservice.allVersionsOf(addMasterIfc);
//						Iterator iterator1 = col.iterator();
//						if (iterator1.hasNext()) {
//							addIfc = (QMPartInfo) iterator1.next();
//						}
	          addIfc = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)addMasterIfc, configSpecGY);
//CCEnd SS17
					}
					if (addMasterIfc==null || addIfc==null){
						message[1] = "ʧ�ܡ�ԭ�������š�"+addNumber+"�������ڣ�";
						setRollbackOnly();
						return message[0]+message[1];
					}
          if (!cyVec.contains(addIfc.getBsoID()))
          {
	          cyVec.add(addIfc.getBsoID());
						//���ò��ñ��������
		        BomChangeNoticePartLinkIfc link = new BomChangeNoticePartLinkInfo();
	          link.setAdoptBs("����");
	 	       	link.setPartID(addIfc.getBsoID());
	          link.setPartName(addIfc.getPartName());
	          link.setPartNumber(addIfc.getPartNumber());
	          link.setLinkPart(parentPartIfc.getBsoID());
//	          if(addIfc.getVersionValue().startsWith("A."))
//	         		link.setIsNew("1");
//	          else
	         		link.setIsNew("0");
	          link.setIsSubPart("0"); //����ͨ������Ӽ���ӵ�
	          link.setVersionValue(addIfc.getVersionValue());
	          link.setNumberAuto(addNumberSum);
	          link.setBz1(NoticeHelper.JGBG);
	          link.setBz2(parentNewPartIfc.getBsoID());
	          noticePartLinkVec.add(link);
	          if (addBs.equals("Y"))
	          {
			        BomChangeNoticePartLinkIfc link2 = new BomChangeNoticePartLinkInfo();
		          link2.setAdoptBs("����");
		 	       	link2.setPartID(addIfc.getBsoID());
		          link2.setPartName(addIfc.getPartName());
		          link2.setPartNumber(addIfc.getPartNumber());
//	          	link2.setLinkPart(parentPartIfc.getBsoID());
	         		link2.setIsNew("1");
		          link2.setIsSubPart("0"); //����ͨ������Ӽ���ӵ�
		          link2.setVersionValue(addIfc.getVersionValue());
	          	link2.setNumberAuto(addNumberSum);
		          link2.setBz1(NoticeHelper.SCCY);
//		          link2.setBz2(parentNewPartIfc.getBsoID());
		          noticePartLinkVec.add(link2);
						
						}	          
					}				
					//���ù���
					PartUsageLinkIfc linkIfc = new PartUsageLinkInfo();
					linkIfc.setLeftBsoID(addMasterIfc.getBsoID());
					linkIfc.setRightBsoID(parentNewPartIfc.getBsoID());
          linkIfc.setQuantity(Float.parseFloat(addNumberSum));
          linkIfc.setDefaultUnit(com.faw_qm.part.util.Unit.getUnitDefault());
					pservice.saveValueInfo(linkIfc);
				}
				
				//ɾ���Ӽ�����
				String deleteNumber = null;
				if (partInformation.length>3)
					deleteNumber=partInformation[3];
				String deleteNumberSum=partInformation[4];
				if (deleteNumberSum==null || deleteNumberSum.length()==0)
					deleteNumberSum="1";
				if (deleteNumber!=null && deleteNumber.length()>0)
				{
					QMPartMasterIfc deleteMasterIfc = getPartMasterByNumber(deleteNumber);
		     	QMPartIfc deleteIfc=null;
					if (deleteMasterIfc!=null){
//CCBegin SS17
//						Collection col = vcservice.allVersionsOf(deleteMasterIfc);
//						Iterator iterator1 = col.iterator();
//						if (iterator1.hasNext()) {
//							deleteIfc = (QMPartInfo) iterator1.next();
//						}
	          deleteIfc = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)deleteMasterIfc, configSpecGY);
//CCEnd SS17
					}
					if (deleteMasterIfc==null || deleteIfc==null){
						message[1] = "ʧ�ܡ�ԭ�������š�"+deleteNumber+"�������ڣ�";
						setRollbackOnly();
						return message[0]+message[1];
					}
          if (!bcyVec.contains(deleteIfc.getBsoID()))
          {
	          bcyVec.add(deleteIfc.getBsoID());
	 					//���ò��ñ��������
		        BomChangeNoticePartLinkIfc link1 = new BomChangeNoticePartLinkInfo();
	          link1.setAdoptBs("������");
	 	       	link1.setPartID(deleteIfc.getBsoID());
	          link1.setPartName(deleteIfc.getPartName());
	          link1.setPartNumber(deleteIfc.getPartNumber());
	          link1.setVersionValue(deleteIfc.getVersionValue());
	          link1.setNumberAuto(deleteNumberSum);
	          link1.setBz1(NoticeHelper.JGBG);
	          link1.setBz2(parentPartIfc.getBsoID());
	          noticePartLinkVec.add(link1);
					}         	
					//���ù���
         	QMQuery query = new QMQuery("PartUsageLink");
          QueryCondition condition = new QueryCondition("rightBsoID", "=", parentNewPartIfc.getBsoID());
          query.addCondition(condition);
          query.addAND();
          QueryCondition condition1 = new QueryCondition("leftBsoID", "=", deleteMasterIfc.getBsoID());
          query.addCondition(condition1);
          Collection delCol = pservice.findValueInfo(query);
    		  for(Iterator ite = delCol.iterator();ite.hasNext();){
    			  PartUsageLinkIfc linkIfc = (PartUsageLinkIfc)ite.next();
          	pservice.deleteValueInfo(linkIfc);
    		 }
          
					
				}
				
			}
			
//			CCBegin SS22
			Object[] data = new Object[13];
//			CCEnd SS22
			data[0]=classification;
			data[1]=adoptnoticeName;
			data[2]=folder;
			data[3]=designDocIfc;
			data[4]=workShop;
			data[5]=bomChangeNoticeIfc;
			data[6]=remark;
			data[7]=workShopMap;
      data[8] = noticePartLinkVec;//�������������Link
      data[9] = zxDocIfc;
      //CCBegin SS22
      data[10]=bscode;
      data[11]=bscodeMap;
      data[12]=adoptNumberStr;
      //CCEnd SS22
	    BomNoticeService bService = (BomNoticeService)EJBServiceHelper.getService("BomNoticeService");;
			bService.createSubBomChangeNotice(data);
		}
		return "����ɹ���";
	}
	
	/**
     * ����Bom
     * @param String csvData CSV�ļ�����
     * @return Object[]
     */

	public String importBom1(String csvData) throws QMException
	{
		if (csvData == null || csvData.equals("")) {
			return "������ļ�Ϊ�գ�";
		
		}
		String[] csvRow = csvData.split("\r\n");
		if (csvRow != null && csvRow.length > 0) {
			VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
			AccessControlService as = (AccessControlService) EJBServiceHelper.getService("AccessControlService");
	    PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
	    LifeCycleService lcservice = (LifeCycleService)EJBServiceHelper.getService("LifeCycleService");
			String classification=null;
			String adoptnoticeName=null;
			String parentBomChangeNotice=null;
			String designDocNumber=null;
			String workShop=null;
			String remark=null;
			String zxDocNumber=null;
			HashMap workShopMap = new HashMap();
			//CCBegin SS22
//          ����ҳ�洫�ݵı�ʶ���룬��ñ�ʶ������룬����������õ��Զ����
            String bscode = null;
            String adoptNumberStr = null;
            HashMap<String,String> bscodeMap =  new HashMap();
            //CCEnd SS22
//			String folder;
			//���֪ͨ��
			String[] bomChangeNoticeValue = csvRow[1].split(",");
			classification=bomChangeNoticeValue[0];
//			CCBegin SS22
			adoptNumberStr=bomChangeNoticeValue[1];
			adoptnoticeName=bomChangeNoticeValue[2];
			workShop=bomChangeNoticeValue[3];
			bscode=bomChangeNoticeValue[4];
			zxDocNumber=bomChangeNoticeValue[5];
			designDocNumber=bomChangeNoticeValue[6];
			parentBomChangeNotice=bomChangeNoticeValue[7];			
			if (bomChangeNoticeValue.length>8)
				remark=bomChangeNoticeValue[7];
			  //CCEnd SS22
			String classificationJh = RemoteProperty.getProperty("com.faw_qm.bomNotice.BomChangeNoticeClassification", "�汾����;ϵ�г����滻;ʵ������;��ʿ;��������;����");
			String workShopJh =RemoteProperty.getProperty("com.faw_qm.bomNotice.BomChangeNoticeWorkShop", "���һ��:Y;��ƶ���:E;ר�ó�:Z");
			String folder =RemoteProperty.getProperty("com.faw_qm.bomNotice.BomChangeNoticeFolder", "\\Root\\��Ʊ��֪ͨ��");
//			CCBegin SS22
		    String bscodeProperty =RemoteProperty.getProperty("com.faw_qm.bomNotice.BomChangeNoticeBsCode", "��׼:Z;ǰ׼:Q;����:S;����:C");
		    //CCEnd SS22
      //У������Ƿ���ȷ
      String[] classificationVec = classificationJh.split(";");
      boolean classificationFlag=false;
      if(classificationVec!=null&&classificationVec.length>0){
          for(int i = 0 ; i <classificationVec.length;i++){
          		if (classificationVec[i].equals(classification))
          		{
          			classificationFlag=true;
          			break;
              }
          }
      }
      if(!classificationFlag){
				setRollbackOnly();
				return "���಻��ȷ��";			
     }
     
			//У�����Ʋ���Ϊ��
			if (adoptnoticeName==null || adoptnoticeName.length()==0)
			{
				setRollbackOnly();
				return "���֪ͨ�����Ʋ���Ϊ�գ�";			
			}
//			CCBegin SS22
//			У���Ų���Ϊ��
			if (adoptNumberStr==null || adoptNumberStr.length()==0)
			{
				setRollbackOnly();
				return "���ͨ��Ų���Ϊ�գ�";			
			}
//			�����ʶ����Ϊ��
			if (bscode==null || bscode.length()==0)
			{
				setRollbackOnly();
				return "���ͨ�����ʶ����Ϊ�գ�";			
			}
			  //CCEnd SS22
			
      //У�鲿���Ƿ���ȷ
      String[] workShopVec = workShopJh.split(";");
      boolean workShopFlag=false;
      if(workShopVec!=null&&workShopVec.length>0){
           for(int i = 0 ; i <workShopVec.length;i++){
              String temShop = workShopVec[i];
              String[] temShopVec = temShop.split(":");
              
              if(temShopVec!=null&&temShopVec.length>0){
                  workShopMap.put(temShopVec[0], temShopVec[1]);
            			if (temShopVec[0].equals(workShop))
            			{
            				workShopFlag=true;
            				//break;
                	}
              }
              
          }
      }
//      System.out.println("workShopMap="+workShopMap);
//      System.out.println("workShop="+workShop);
      if(!workShopFlag){
				setRollbackOnly();
				return "���Ų���ȷ��";			
     	}
//		CCBegin SS22
      //У���ʶ�����Ƿ���ȷ
      String[] bscodeVec = bscodeProperty.split(";");
      boolean bscodeFlag=false;
      if(bscodeVec!=null&&bscodeVec.length>0){
           for(int i = 0 ; i <bscodeVec.length;i++){
              String temShop = bscodeVec[i];
              String[] temShopVec = temShop.split(":");
              
              if(temShopVec!=null&&temShopVec.length>0){
            	  bscodeMap.put(temShopVec[0], temShopVec[1]);
            			if (temShopVec[0].equals(bscode))
            			{
            				bscodeFlag=true;
            				//break;
                	}
              }
              
          }
      }
      if(!bscodeFlag){
				setRollbackOnly();
				return "��ʶ���벻��ȷ��";			
     	}
      
//		CCEnd SS22
     	//���Ĳ���/������Ƿ���ȷ
	    DocInfo zxDocIfc=null;
     	if (zxDocNumber!=null && zxDocNumber.length()>0)
     	{
	    	DocMasterIfc zxDocMasterIfc=getDocMasterByNumber(zxDocNumber);
				if (zxDocMasterIfc!=null){
	     		Collection col = vcservice.allVersionsOf(zxDocMasterIfc);
					Iterator iterator1 = col.iterator();
					if (iterator1.hasNext()) {
						zxDocIfc = (DocInfo) iterator1.next();
					}
				}
				if (zxDocMasterIfc==null || zxDocIfc==null){
					setRollbackOnly();
					return "���Ĳ���/�������Ų���ȷ��";
				}
   		}
   		//������������Ƿ���ȷ
	    DocInfo designDocIfc=null;
     	if (designDocNumber!=null && designDocNumber.length()>0)
     	{
	    	DocMasterIfc designDocMasterIfc=getDocMasterByNumber(designDocNumber);
				if (designDocMasterIfc!=null){
	     		Collection col = vcservice.allVersionsOf(designDocMasterIfc);
					Iterator iterator1 = col.iterator();
					if (iterator1.hasNext()) {
						designDocIfc = (DocInfo) iterator1.next();
					}
				}
				if (designDocMasterIfc==null || designDocIfc==null){
					setRollbackOnly();
					return "����������񵥱�Ų���ȷ��";
				}
			}			
			//��������Ƿ���ȷ
			BomChangeNoticeIfc bomChangeNoticeIfc=null;
     	if (parentBomChangeNotice!=null && parentBomChangeNotice.length()>0)
     	{
				bomChangeNoticeIfc=getBomChangeNoticeByNumber(parentBomChangeNotice);
				if (bomChangeNoticeIfc==null){
					setRollbackOnly();
					return "���������Ų���ȷ��";
				}
			}
			
			//���ó��ͱ���
/*			Vector adoptPartBsoIDVec=new Vector();
		  for(Iterator ite = noticePartLinkVec.iterator();ite.hasNext();){
			  BomChangeNoticePartLinkIfc linkIfc = (BomChangeNoticePartLinkIfc)ite.next();
			  if (linkIfc.getAdoptBs().equals("����"))
			  {
			  	adoptPartBsoIDVec.add(linkIfc.getPartID());
			  }
			  	
 		 	}
 */
  		String[] autoValues = csvRow[2].split(",");
  		Vector autoVector=new Vector();
//CCBegin SS17
	    PartConfigSpecIfc configSpecGY = getPartConfigSpecByViewName("������ͼ");

//CCEnd SS17
			for (int j = 1; j < autoValues.length; j++) {
				String autoNumber = autoValues[j];
				QMPartMasterIfc autoMasterIfc = getPartMasterByNumber(autoNumber);
	     	QMPartIfc autoIfc=null;
				if (autoMasterIfc!=null){
//CCBegin SS17
//					Collection col = vcservice.allVersionsOf(autoMasterIfc);
//					Iterator iterator1 = col.iterator();
//					if (iterator1.hasNext()) {
//						autoIfc = (QMPartInfo) iterator1.next();
//						autoVector.add(autoIfc);
//					}
	          autoIfc = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)autoMasterIfc, configSpecGY);
						autoVector.add(autoIfc);
//CCEnd SS17
				}
				if (autoMasterIfc==null || autoIfc==null){
					setRollbackOnly();
//CCBegin SS17
					return "������ͼ�����ó��ͱ�š�"+autoNumber+"�������ڣ�";
//CCEnd SS17
				}
			}
			
			//�������
     	//�������б�
      Vector noticePartLinkVec = new Vector();
      //Ҫ�޶��İ汾�ƻ�
      Vector receiveVec = new Vector();
      Vector cyVec = new Vector();
      Vector bcyVec = new Vector();
		  for(Iterator iter1 = autoVector.iterator();iter1.hasNext();){
			  QMPartIfc parentPartIfc = (QMPartIfc)iter1.next();
				QMPartIfc parentNewPartIfc=(QMPartIfc)vcservice.newVersion(parentPartIfc);
//				CCBegin SS22
				//��ȡ������������ģ��
				  String templateID = ((LifeCycleManagedIfc)parentNewPartIfc).getLifeCycleTemplate();
				  LifeCycleTemplateInfo template = (LifeCycleTemplateInfo)pservice.refreshInfo(templateID);
				  LifeCycleTemplateMasterInfo master = (LifeCycleTemplateMasterInfo)template.getMaster();//��ȡ��������ģ��master��
				  template = lcservice.getLifeCycleTemplate(master.getLifeCycleName());//��ȡ���µ���������ģ�塣
				  parentNewPartIfc = (QMPartIfc) lcservice.setLifeCycle(parentNewPartIfc, template);//����ָ����������
//					CCEnd SS22
				pservice.saveValueInfo(parentNewPartIfc);
			for (int i = 4; i < csvRow.length; i++) {
				String[] message = new String[2];	
				String row = csvRow[i];
				String[] partInformation = row.split(",");
	
//CCBegin SS17
				message[0]="������ͼ�µ�"+(i+1)+"��";
//CCEnd SS17
	

				
				String	addBs=partInformation[4].toUpperCase();

				//�����Ӽ�����
				String addNumber = partInformation[0];
				String addNumberSum = partInformation[1];
				if (addNumberSum==null || addNumberSum.length()==0)
					addNumberSum="1";
				if (addNumber!=null && addNumber.length()>0)
				{
					QMPartMasterIfc addMasterIfc = getPartMasterByNumber(addNumber);
		     	QMPartIfc addIfc=null;
					if (addMasterIfc!=null){
//CCBegin SS17
//						Collection col = vcservice.allVersionsOf(addMasterIfc);
//						Iterator iterator1 = col.iterator();
//						if (iterator1.hasNext()) {
//							addIfc = (QMPartInfo) iterator1.next();
//						}
	          addIfc = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)addMasterIfc, configSpecGY);
//CCEnd SS17
					}
					if (addMasterIfc==null || addIfc==null){
						message[1] = "ʧ�ܡ�ԭ�������š�"+addNumber+"�������ڣ�";
						setRollbackOnly();
						return message[0]+message[1];
					}
          if (!cyVec.contains(addIfc.getBsoID()))
          {
	          cyVec.add(addIfc.getBsoID());
						//���ò��ñ��������
		        BomChangeNoticePartLinkIfc link = new BomChangeNoticePartLinkInfo();
	          link.setAdoptBs("����");
	 	       	link.setPartID(addIfc.getBsoID());
	          link.setPartName(addIfc.getPartName());
	          link.setPartNumber(addIfc.getPartNumber());
//	          if(addIfc.getVersionValue().startsWith("A."))
//	         		link.setIsNew("1");
//	          else
	         		link.setIsNew("0");
	          link.setIsSubPart("0"); //����ͨ������Ӽ���ӵ�
	          link.setVersionValue(addIfc.getVersionValue());
	          link.setNumberAuto(addNumberSum);
	          link.setBz1(NoticeHelper.JGBG);
	          noticePartLinkVec.add(link);
	          if (addBs.equals("Y"))
	          {
			        BomChangeNoticePartLinkIfc link2 = new BomChangeNoticePartLinkInfo();
		          link2.setAdoptBs("����");
		 	       	link2.setPartID(addIfc.getBsoID());
		          link2.setPartName(addIfc.getPartName());
		          link2.setPartNumber(addIfc.getPartNumber());
		         	link2.setIsNew("1");
		          link2.setIsSubPart("0"); //����ͨ������Ӽ���ӵ�
		          link2.setVersionValue(addIfc.getVersionValue());
		          link2.setBz1(NoticeHelper.SCCY);
		          noticePartLinkVec.add(link2);
          }
					}				
					//���ù���
					PartUsageLinkIfc linkIfc = new PartUsageLinkInfo();
					linkIfc.setLeftBsoID(addMasterIfc.getBsoID());
					linkIfc.setRightBsoID(parentNewPartIfc.getBsoID());
          linkIfc.setQuantity(Float.parseFloat(addNumberSum));
          linkIfc.setDefaultUnit(com.faw_qm.part.util.Unit.getUnitDefault());
					pservice.saveValueInfo(linkIfc);
				}
				
				//ɾ���Ӽ�����
				String deleteNumber = null;
//				if (partInformation.length>2)
					deleteNumber=partInformation[2];
				String	deleteNumberSum=partInformation[3];
				if (deleteNumberSum==null || deleteNumberSum.length()==0)
					deleteNumberSum="1";
				if (deleteNumber!=null && deleteNumber.length()>0)
				{
					QMPartMasterIfc deleteMasterIfc = getPartMasterByNumber(deleteNumber);
		     	QMPartIfc deleteIfc=null;
					if (deleteMasterIfc!=null){
//CCBegin SS17
//						Collection col = vcservice.allVersionsOf(deleteMasterIfc);
//						Iterator iterator1 = col.iterator();
//						if (iterator1.hasNext()) {
//							deleteIfc = (QMPartInfo) iterator1.next();
//						}
	          deleteIfc = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)deleteMasterIfc, configSpecGY);
//CCEnd SS17
					}
					if (deleteMasterIfc==null || deleteIfc==null){
						message[1] = "ʧ�ܡ�ԭ�������š�"+deleteNumber+"�������ڣ�";
						setRollbackOnly();
						return message[0]+message[1];
					}
          if (!bcyVec.contains(deleteIfc.getBsoID()))
          {
	          bcyVec.add(deleteIfc.getBsoID());
	 					//���ò��ñ��������
		        BomChangeNoticePartLinkIfc link1 = new BomChangeNoticePartLinkInfo();
	          link1.setAdoptBs("������");
	 	       	link1.setPartID(deleteIfc.getBsoID());
	          link1.setPartName(deleteIfc.getPartName());
	          link1.setPartNumber(deleteIfc.getPartNumber());
	          link1.setVersionValue(deleteIfc.getVersionValue());
	          link1.setNumberAuto(deleteNumberSum);
	          link1.setBz1(NoticeHelper.JGBG);
	          noticePartLinkVec.add(link1);
					}         	
					//���ù���
         	QMQuery query = new QMQuery("PartUsageLink");
          QueryCondition condition = new QueryCondition("rightBsoID", "=", parentNewPartIfc.getBsoID());
          query.addCondition(condition);
          query.addAND();
          QueryCondition condition1 = new QueryCondition("leftBsoID", "=", deleteMasterIfc.getBsoID());
          query.addCondition(condition1);
          Collection delCol = pservice.findValueInfo(query);
    		  for(Iterator ite = delCol.iterator();ite.hasNext();){
    			  PartUsageLinkIfc linkIfc = (PartUsageLinkIfc)ite.next();
          	pservice.deleteValueInfo(linkIfc);
    		 }
          
					
				}

			}
			}
//			CCBegin SS22
			Object[] data = new Object[13];
//			CCEnd SS22
			data[0]=classification;
			data[1]=adoptnoticeName;
			data[2]=folder;
			data[3]=designDocIfc;
			data[4]=workShop;
			data[5]=bomChangeNoticeIfc;
			data[6]=remark;
			data[7]=workShopMap;
      data[8] = noticePartLinkVec;//�������������Link
      data[9] = zxDocIfc;
      //CCBegin SS22
      data[10]=bscode;
      data[11]=bscodeMap;
      data[12]=adoptNumberStr;
      //CCEnd SS22
	    BomNoticeService bService = (BomNoticeService)EJBServiceHelper.getService("BomNoticeService");;
			Object[] resultSave=bService.createSubBomChangeNotice(data);
			
			
			BomChangeNoticeIfc newBomChangeNotice=(BomChangeNoticeInfo)resultSave[0];
			bService.saveAllAuto(newBomChangeNotice,autoVector);			
		}
		return "����ɹ���";
	}
	
	
	
	public String importGYBom(String csvData) throws QMException
	{
		if (csvData == null || csvData.equals("")) {
			return "������ļ�Ϊ�գ�";
		
		}
		String[] csvRow = csvData.split("\r\n");
		if (csvRow != null && csvRow.length > 0) {
			VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
			AccessControlService as = (AccessControlService) EJBServiceHelper.getService("AccessControlService");
	    PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
			String zc="����";
			String cj="����";
			String jss="��ʻ��";
			String publishType=null;
			String classification=null;
			String adoptnoticeName=null;
			String cxPartNumber=null;
			String designDocNumber=null;
			String jfBomNoticeNumber=null;
			String zxDocNumber=null;
			String zcAdoptnoticeNumber=null;
			String remark=null;
			//���֪ͨ��
			String[] gYBomAdoptNoticeValue = csvRow[1].split(",");
			publishType=gYBomAdoptNoticeValue[0];
			classification=gYBomAdoptNoticeValue[1];
			adoptnoticeName=gYBomAdoptNoticeValue[2];
			cxPartNumber=gYBomAdoptNoticeValue[3];
			designDocNumber=gYBomAdoptNoticeValue[4];
			jfBomNoticeNumber=gYBomAdoptNoticeValue[5];			
			zxDocNumber=gYBomAdoptNoticeValue[6];			
			zcAdoptnoticeNumber=gYBomAdoptNoticeValue[7];			
			if (gYBomAdoptNoticeValue.length>8)
				remark=gYBomAdoptNoticeValue[8];
/*			for (int j = 0; j < 9; j++) {
				String row = csvRow[j];
				String[] str = row.split(",");
				if (str[0].equals("publishTypeBegin"))
				{
					publishType=str[1];
				}
				if (str[0].equals("classificationBegin"))
				{
					classification=str[1];
				}
				if (str[0].equals("adoptnoticeNameBegin"))
				{
					adoptnoticeName=str[1];
				}
				if (str[0].equals("cxPartNumberBegin"))
				{
					cxPartNumber=str[1];
				}
				if (str[0].equals("designDocNumberBegin"))
				{
					designDocNumber=str[1];
				}
				if (str[0].equals("jfBomNoticeNumberBegin"))
				{
					jfBomNoticeNumber=str[1];
				}
				if (str[0].equals("remarkBegin"))
				{
					remark=str[1];
				}
				if (str[0].equals("zxDocNumberBegin"))
				{
					zxDocNumber=str[1];
				}
				if (str[0].equals("zcAdoptnoticeNumberBegin"))
				{
					zcAdoptnoticeNumber=str[1];
				}
			}			
*/
			String classificationJh = RemoteProperty.getProperty("com.faw_qm.gybomNotice.CarloadAndCarFrameClassification", "J6L;J6M;J6P;J7;MV3;����");
			String pType ="��������BOM���ĵ�";
			String folder =RemoteProperty.getProperty("com.faw_qm.gybomNotice.BomAdoptNoticeFolder1", "\\Root\\��������BOM���ø��ĵ�");
			String lifeCycleTemplate =RemoteProperty.getProperty("com.faw_qm.gybomNotice.lifeCycleTemplate1", "����BOM��������������");

      //У������Ƿ���ȷ
			if (publishType==null || publishType.length()==0)
			{
				setRollbackOnly();
				return "�������Ͳ���Ϊ�գ�";			
			}
 			if (!publishType.equals(zc) && !publishType.equals(cj) && !publishType.equals(jss))
			{
				setRollbackOnly();
				return "�������Ͳ���ȷ��";			
			}
			//����
			if (publishType.equals(zc))
			{
				if (zcAdoptnoticeNumber!=null && zcAdoptnoticeNumber.length()>0)
				{
					setRollbackOnly();
					return "���������������������������ӦΪ�գ�";			
				}
			}
			//����
			if (publishType.equals(cj))
			{
				if ((designDocNumber!=null && designDocNumber.length()>0) ||(zxDocNumber!=null && zxDocNumber.length()>0))
				{
					setRollbackOnly();
					return "���ܷ��������ͼ�������֪ͨ����š��������ı�������ӦΪ�գ�";			
				}
				pType="���ܹ���BOM���ĵ�";
				folder=RemoteProperty.getProperty("com.faw_qm.gybomNotice.BomAdoptNoticeFolder2", "\\Root\\���ܼ�ʻ�ҹ���BOM���ø��ĵ�");
				lifeCycleTemplate=RemoteProperty.getProperty("com.faw_qm.gybomNotice.lifeCycleTemplate2", "���ܼ�ʻ�ҹ���BOM��������������");
			}
			//��ʻ��
			if (publishType.equals(jss))
			{
				if ((designDocNumber!=null && designDocNumber.length()>0) ||(zxDocNumber!=null && zxDocNumber.length()>0))
				{
					setRollbackOnly();
					return "��ʻ�ҷ��������ͼ�������֪ͨ����š��������ı�������ӦΪ�գ�";			
				}
				pType="��ʻ�ҹ���BOM���ĵ�";
				classificationJh=RemoteProperty.getProperty("com.faw_qm.gybomNotice.CarBodyClassification", "J7͹�ذ�߶�;J7͹�ذ�ƽ��;J7ƽ�ذ�߶�;J7ƽ�ذ�ƽ��;J6P���Ű�-13��߶�;J6P���Ű�-13��ƽ��;J6P���Ű�-11��߶�;J6P���Ű�-11��ƽ��;J6PС�Ű�-ƽ��;J6M���Ű�-11��߶�;J6M���Ű�-11��ƽ��;J6Lƽ��;J6����");
				folder=RemoteProperty.getProperty("com.faw_qm.gybomNotice.BomAdoptNoticeFolder2", "\\Root\\���ܼ�ʻ�ҹ���BOM���ø��ĵ�");
				lifeCycleTemplate=RemoteProperty.getProperty("com.faw_qm.gybomNotice.lifeCycleTemplate2", "���ܼ�ʻ�ҹ���BOM��������������");
			}

     //У������Ƿ���ȷ
      String[] classificationVec = classificationJh.split(";");
      boolean classificationFlag=false;
      if(classificationVec!=null&&classificationVec.length>0){
          for(int i = 0 ; i <classificationVec.length;i++){
          		if (classificationVec[i].equals(classification))
          		{
          			classificationFlag=true;
          			break;
              }
          }
      }
      if(!classificationFlag){
				setRollbackOnly();
				return "���಻��ȷ��";			
     }
     
			//У�鷢�������Ʋ���Ϊ��
			if (adoptnoticeName==null || adoptnoticeName.length()==0)
			{
				setRollbackOnly();
				return "���������Ʋ���Ϊ�գ�";			
			}
			//У�鳵�Ͳ���Ϊ��
			if (cxPartNumber==null || cxPartNumber.length()==0)
			{
				setRollbackOnly();
				return "���Ͳ���Ϊ�գ�";			
			}
   		//�����Ƿ���ȷ
	    QMPartInfo cxPartIfc=null;
     	if (cxPartNumber!=null && cxPartNumber.length()>0)
     	{
	    	QMPartMasterIfc cxPartMasterIfc=getPartMasterByNumber(cxPartNumber);
				if (cxPartMasterIfc!=null){
	     		Collection col = vcservice.allVersionsOf(cxPartMasterIfc);
					Iterator iterator1 = col.iterator();
					if (iterator1.hasNext()) {
						cxPartIfc = (QMPartInfo) iterator1.next();
					}
				}
				if (cxPartMasterIfc==null || cxPartIfc==null){
					setRollbackOnly();
					return "���ͱ�Ų���ȷ��";
				}
			}			
   		//������������Ƿ���ȷ������������
	    DocInfo designDocIfc=null;
     	if (designDocNumber!=null && designDocNumber.length()>0)
     	{
	    	DocMasterIfc designDocMasterIfc=getDocMasterByNumber(designDocNumber);
				if (designDocMasterIfc!=null){
	     		Collection col = vcservice.allVersionsOf(designDocMasterIfc);
					Iterator iterator1 = col.iterator();
					if (iterator1.hasNext()) {
						designDocIfc = (DocInfo) iterator1.next();
					}
				}
				if (designDocMasterIfc==null || designDocIfc==null){
					setRollbackOnly();
					return "��������֪ͨ����Ų���ȷ��";
				}
			}			
     	//�������ı�����Ƿ���ȷ������������
	    DocInfo zxDocIfc=null;
     	if (zxDocNumber!=null && zxDocNumber.length()>0)
     	{
	    	DocMasterIfc zxDocMasterIfc=getDocMasterByNumber(zxDocNumber);
				if (zxDocMasterIfc!=null){
	     		Collection col = vcservice.allVersionsOf(zxDocMasterIfc);
					Iterator iterator1 = col.iterator();
					if (iterator1.hasNext()) {
						zxDocIfc = (DocInfo) iterator1.next();
					}
				}
				if (zxDocMasterIfc==null || zxDocIfc==null){
					setRollbackOnly();
					return "�������ı������Ų���ȷ��";
				}
   		}
			//�����Ʊ�����Ƿ���ȷ
			BaseValueInfo baseIfc=null;
     	if (jfBomNoticeNumber!=null && jfBomNoticeNumber.length()>0)
     	{
				baseIfc=(BaseValueInfo)getBomChangeNoticeByNumber(jfBomNoticeNumber);
				if (baseIfc==null){
					baseIfc = (BaseValueInfo)getBomAdoptNoticeByNumber(jfBomNoticeNumber);
					if (baseIfc==null){
						setRollbackOnly();
						return "�����Ʊ������Ų���ȷ��";
					}
				}
			}

			//�����������Ƿ���ȷ�����ڳ��ܺͼ�ʻ�ң�
			GYBomAdoptNoticeIfc gYBomAdoptNoticeIfc=null;
     	if (zcAdoptnoticeNumber!=null && zcAdoptnoticeNumber.length()>0)
     	{
				gYBomAdoptNoticeIfc=getGYBomAdoptNoticeByNumber(zcAdoptnoticeNumber);
				if (gYBomAdoptNoticeIfc==null){
					setRollbackOnly();
					return "������������Ų���ȷ��";
				}
			}
			//�������
     	//�������б�
      Vector noticePartLinkVec = new Vector();
      //Ҫ�޶��İ汾�ƻ�
      Vector receiveVec = new Vector();
      Vector cyVec = new Vector();
      Vector bcyVec = new Vector();
			for (int i = 3; i < csvRow.length; i++) {
				String[] message = new String[2];	
				String row = csvRow[i];
				String[] partInformation = row.split(",");
				//����
				String parentNumber = partInformation[0];
				message[0]="��"+(i+1)+"�У���������Ϊ��"+parentNumber+"����������ݵ���";
				if(parentNumber==null||parentNumber.equals("")){
					message[1] = "ʧ�ܡ�ԭ�򣺸�������Ϊ�գ�";
					setRollbackOnly();
					return message[0]+message[1];
				}
				QMPartMasterIfc parentMasterIfc = getPartMasterByNumber(parentNumber);
	     	QMPartInfo parentPartIfc=null;
				if (parentMasterIfc!=null){
	     		Collection col = vcservice.allVersionsOf(parentMasterIfc);
					Iterator iterator1 = col.iterator();
					if (iterator1.hasNext()) {
						parentPartIfc = (QMPartInfo) iterator1.next();
					}
				}
				if (parentMasterIfc==null || parentPartIfc==null){
					message[1] = "ʧ�ܡ�ԭ�������š�"+parentNumber+"�������ڣ�";
					setRollbackOnly();
					return message[0]+message[1];
				}
				//����Ҫ���޶�Ȩ��
				if	(!as.hasAccess(parentPartIfc, QMPermission.REVISE)){
					message[1] = "ʧ�ܡ�ԭ�������š�"+parentNumber+"�����޶�Ȩ�ޣ�";
					setRollbackOnly();
					return message[0]+message[1];
				}
				QMPartIfc parentNewPartIfc=parentPartIfc;
				//���Ƕ��ͬһ���ֻ�޶�һ���汾
				if (!receiveVec.contains(parentNumber))
				{			
					receiveVec.add(parentNumber);
					parentNewPartIfc=(QMPartIfc)vcservice.newVersion(parentPartIfc);
					pservice.saveValueInfo(parentNewPartIfc);
					
          if (!cyVec.contains(parentNewPartIfc.getBsoID()))
          {
	          cyVec.add(parentNewPartIfc.getBsoID());
	          GYBomAdoptNoticePartLinkInfo link = new GYBomAdoptNoticePartLinkInfo();
	         	String[] partStr = getPartValueByPartID(parentNewPartIfc);
	          link.setAdoptBs("����");
	 	       	link.setPartID(parentNewPartIfc.getBsoID());
	          link.setPartName(parentNewPartIfc.getPartName());
	          link.setPartNumber(parentNewPartIfc.getPartNumber());
	          link.setVersionValue(parentNewPartIfc.getVersionValue());
						link.setZzlx(partStr[0]);
						link.setZplx(partStr[1]);
						link.setPartView(parentNewPartIfc.getViewName());
						link.setVirtualPart(partStr[2]);
						link.setLifecyclestate(parentNewPartIfc.getLifeCycleState().getDisplay());
	          noticePartLinkVec.add(link);

					}
          if (!bcyVec.contains(parentNewPartIfc.getBsoID()))
          {
	          cyVec.add(parentPartIfc.getBsoID());
						GYBomAdoptNoticePartLinkInfo link = new GYBomAdoptNoticePartLinkInfo();
	         	String[] partStr = getPartValueByPartID(parentPartIfc);
						link.setAdoptBs("������");
						link.setPartID(parentPartIfc.getBsoID());
						link.setPartNumber(parentPartIfc.getPartNumber());
						link.setPartName(parentPartIfc.getPartName());
						link.setVersionValue(parentPartIfc.getVersionValue());
						link.setZzlx(partStr[0]);
						link.setZplx(partStr[1]);
						link.setPartView(parentPartIfc.getViewName());
						noticePartLinkVec.add(link);
					}
				}
				//�����Ӽ�����
				String addNumber = partInformation[1];
				if (addNumber!=null && addNumber.length()>0)
				{
					QMPartMasterIfc addMasterIfc = getPartMasterByNumber(addNumber);
		     	QMPartIfc addIfc=null;
					if (addMasterIfc!=null){
						Collection col = vcservice.allVersionsOf(addMasterIfc);
						Iterator iterator1 = col.iterator();
						if (iterator1.hasNext()) {
							addIfc = (QMPartInfo) iterator1.next();
						}
					}
					if (addMasterIfc==null || addIfc==null){
						message[1] = "ʧ�ܡ�ԭ�������š�"+addNumber+"�������ڣ�";
						setRollbackOnly();
						return message[0]+message[1];
					}
          if (!cyVec.contains(addIfc.getBsoID()))
          {
	          cyVec.add(addIfc.getBsoID());
						//���ò��ñ��������
	          GYBomAdoptNoticePartLinkInfo link = new GYBomAdoptNoticePartLinkInfo();
	         	String[] partStr = getPartValueByPartID(addIfc);
	          link.setAdoptBs("����");
	 	       	link.setPartID(addIfc.getBsoID());
	          link.setPartName(addIfc.getPartName());
	          link.setPartNumber(addIfc.getPartNumber());
	          link.setVersionValue(addIfc.getVersionValue());
						link.setZzlx(partStr[0]);
						link.setZplx(partStr[1]);
						link.setPartView(addIfc.getViewName());
						link.setVirtualPart(partStr[2]);
						link.setLifecyclestate(addIfc.getLifeCycleState().getDisplay());
	          noticePartLinkVec.add(link);

					}				
					//���ù���
					PartUsageLinkIfc linkIfc = new PartUsageLinkInfo();
					linkIfc.setLeftBsoID(addMasterIfc.getBsoID());
					linkIfc.setRightBsoID(parentNewPartIfc.getBsoID());
          linkIfc.setQuantity(1);
          linkIfc.setDefaultUnit(com.faw_qm.part.util.Unit.getUnitDefault());
					pservice.saveValueInfo(linkIfc);
				}
				
				//ɾ���Ӽ�����
				String deleteNumber = null;
				if (partInformation.length>2)
					deleteNumber=partInformation[2];
				if (deleteNumber!=null && deleteNumber.length()>0)
				{
					QMPartMasterIfc deleteMasterIfc = getPartMasterByNumber(deleteNumber);
		     	QMPartIfc deleteIfc=null;
					if (deleteMasterIfc!=null){
						Collection col = vcservice.allVersionsOf(deleteMasterIfc);
						Iterator iterator1 = col.iterator();
						if (iterator1.hasNext()) {
							deleteIfc = (QMPartInfo) iterator1.next();
						}
					}
					if (deleteMasterIfc==null || deleteIfc==null){
						message[1] = "ʧ�ܡ�ԭ�������š�"+deleteNumber+"�������ڣ�";
						setRollbackOnly();
						return message[0]+message[1];
					}
          if (!bcyVec.contains(deleteIfc.getBsoID()))
          {
	          bcyVec.add(deleteIfc.getBsoID());
						GYBomAdoptNoticePartLinkInfo link = new GYBomAdoptNoticePartLinkInfo();
	         	String[] partStr = getPartValueByPartID(deleteIfc);
						link.setAdoptBs("������");
						link.setPartID(deleteIfc.getBsoID());
						link.setPartNumber(deleteIfc.getPartNumber());
						link.setPartName(deleteIfc.getPartName());
						link.setVersionValue(deleteIfc.getVersionValue());
						link.setZzlx(partStr[0]);
						link.setZplx(partStr[1]);
						link.setPartView(deleteIfc.getViewName());
						noticePartLinkVec.add(link);
					}         	
					//���ù���
         	QMQuery query = new QMQuery("PartUsageLink");
          QueryCondition condition = new QueryCondition("rightBsoID", "=", parentNewPartIfc.getBsoID());
          query.addCondition(condition);
          query.addAND();
          QueryCondition condition1 = new QueryCondition("leftBsoID", "=", deleteMasterIfc.getBsoID());
          query.addCondition(condition1);
          Collection delCol = pservice.findValueInfo(query);
    		  for(Iterator ite = delCol.iterator();ite.hasNext();){
    			  PartUsageLinkIfc linkIfc = (PartUsageLinkIfc)ite.next();
          	pservice.deleteValueInfo(linkIfc);
    		 }
          
					
				}
				
			}
			if (publishType.equals(zc)){
				Object[] data = new Object[10];
				data[0]=pType;
				data[1]=classification;
				data[2]=adoptnoticeName;
				data[3]=lifeCycleTemplate;
				data[4]=folder;				
				data[5]=designDocIfc;
				data[6]=baseIfc;
				data[7]=zxDocIfc;
	      data[8] = cxPartIfc;
	      data[9] = remark;
				data = createGYBomAdoptNotice(data);
				createGYBomZCPartLink(noticePartLinkVec,(GYBomAdoptNoticeIfc)data[0]);
			}
			else{
				Object[] data = new Object[8];
				data[0]=pType;
				data[1]=classification;
				data[2]=adoptnoticeName;
				data[3]=lifeCycleTemplate;
				data[4]=folder;				
				data[5]=gYBomAdoptNoticeIfc;
				data[6]=cxPartIfc;
				data[7]=remark;
				data = createFrameAndBodyBomAdoptNotice(data);
				createGYBomZCPartLink(noticePartLinkVec,(GYBomAdoptNoticeIfc)data[0]);
			}

		}
		return "����ɹ���";
	}


    private QMPartMasterIfc getPartMasterByNumber(String partNumber)
        throws QMException
    {
	      PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("QMPartMaster");
        //����Ҫ���ҹ��岿��
        query.setChildQuery(false);
        QueryCondition condition2 = new QueryCondition("partNumber", "=", partNumber);
        query.addCondition(condition2);
        Collection coll = pservice.findValueInfo(query);
        Iterator vc = coll.iterator();
        QMPartMasterIfc ifc=null;
        if (vc.hasNext())
        	ifc=(QMPartMasterIfc)vc.next();
        return ifc;
    }
    private DocMasterIfc getDocMasterByNumber(String docNumber)
        throws QMException
    {
	      PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("DocMaster");
        QueryCondition condition2 = new QueryCondition("docNum", "=", docNumber);
        query.addCondition(condition2);
        Collection coll = pservice.findValueInfo(query);
        Iterator vc = coll.iterator();
        DocMasterIfc ifc=null;
        if (vc.hasNext())
        	ifc=(DocMasterIfc)vc.next();
        return ifc;
    }
	
    private BomChangeNoticeIfc getBomChangeNoticeByNumber(String bomChangeNoticeNumber)
        throws QMException
    {
	      PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("BomChangeNotice");
        QueryCondition condition2 = new QueryCondition("adoptnoticenumber", "=", bomChangeNoticeNumber);
        query.addCondition(condition2);
        Collection coll = pservice.findValueInfo(query);
        Iterator vc = coll.iterator();
        BomChangeNoticeIfc ifc=null;
        if (vc.hasNext())
        	ifc=(BomChangeNoticeIfc)vc.next();
        return ifc;
    }
    private BomAdoptNoticeIfc getBomAdoptNoticeByNumber(String bomAdoptNoticeNumber)
        throws QMException
    {
	      PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("BomAdoptNotice");
        QueryCondition condition2 = new QueryCondition("adoptnoticenumber", "=", bomAdoptNoticeNumber);
        query.addCondition(condition2);
        Collection coll = pservice.findValueInfo(query);
        Iterator vc = coll.iterator();
        BomAdoptNoticeIfc ifc=null;
        if (vc.hasNext())
        	ifc=(BomAdoptNoticeIfc)vc.next();
        return ifc;
    }
    private GYBomAdoptNoticeIfc getGYBomAdoptNoticeByNumber(String adoptnoticeNumber)
        throws QMException
    {
	      PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("GYBomAdoptNotice");
        QueryCondition condition2 = new QueryCondition("adoptnoticenumber", "=", adoptnoticeNumber);
        query.addCondition(condition2);
        Collection coll = pservice.findValueInfo(query);
        Iterator vc = coll.iterator();
        GYBomAdoptNoticeIfc ifc=null;
        if (vc.hasNext())
        	ifc=(GYBomAdoptNoticeIfc)vc.next();
        return ifc;
    }
    
    
    private String[] getPartValueByPartID(QMPartIfc part) throws QMException
    {
 			  String[] partStr = new String[3];
 			  String[] bsoStr = new String[1];
			  bsoStr[0] = part.getBsoID();
			  StringBuffer zhizaoBuffer = new StringBuffer();
			  StringBuffer zhuangpeiBuffer = new StringBuffer();
    		  try {
    		  	//CCBegin SS29
    		  	String dwbs = getCurrentDWBS();
    		  	System.out.println("��������ȡ��λ��"+dwbs);
    		  	if(dwbs.equals("W34"))//�ɶ�
    		  	{
    		  		Collection cols = getSecondRouteByJDBC(part.getBsoID());
    		  		if(cols!=null&&cols.size()>0)
    		  		{
    		  			int n = 0;
    		  			for(Iterator ite = cols.iterator();ite.hasNext();)
    		  			{
    		  				String[] splitStr = (String[])ite.next();
    		  				if(n==cols.size()-1)
    		  				{
    		  					zhizaoBuffer.append(splitStr[0]);
    		  					zhuangpeiBuffer.append(splitStr[1]);
    		  				}
    		  				else
    		  				{
    		  					zhizaoBuffer.append(splitStr[0]+"/");
    		  					zhuangpeiBuffer.append(splitStr[1]+"/");
    		  				}
    		  				n++;
    		  			}
    		  		}
    		  	}
    		  	else//Ŀǰ�ǽ�ţ��Ժ����ӵ�λ����else
    		  	{
    		  	//CCEnd SS29
    		conn = getConnection();
				Collection cols = getRouteByJDBC(bsoStr);
				if(conn!=null){
					conn.close();	
				}
				if(cols!=null&&cols.size()>0){
					for(Iterator ite = cols.iterator();ite.hasNext();){
						Vector vec = (Vector)ite.next();
						if(vec!=null&&vec.size()>0){
							for(int n = 0 ; n <vec.size();n++){
								String[] routeStrs = (String[])vec.get(n);
								String routeStr = routeStrs[0];
								String[] splitStr = routeStr.split("@");
								if(n==vec.size()-1){
									zhizaoBuffer.append(splitStr[0]);
									zhuangpeiBuffer.append(splitStr[1]);
								}else{
									zhizaoBuffer.append(splitStr[0]+"/");
									zhuangpeiBuffer.append(splitStr[1]+"/");
								}

							}
						}
				
					}
				}//CCBegin SS25
				//CCBegin SS29
			  }
			  //CCEnd SS29
			} catch (Exception e) {
				e.printStackTrace();
			}//CCEnd SS25
    		if(zhizaoBuffer!=null){
    			partStr[0] = zhizaoBuffer.toString();//����
    		}else{
    			partStr[0] = "";
    		}
    		if(zhuangpeiBuffer!=null){
    			partStr[1] = zhuangpeiBuffer.toString();//װ��
    		}else{
    			partStr[1] = "";
    		}
    		//CCEnd SS3
				//���IBA���ԣ��Ƿ������
				Collection ibaCol = getPartIBA(part,"�����","virtualPart");
				//��ǰ����Ѿ�����IBAֵ������
				if(ibaCol!=null&&ibaCol.size()>0){
					Iterator ibaIte = ibaCol.iterator();
					StringValueIfc existValue = (StringValueIfc)ibaIte.next();
					partStr[2] = existValue.getValue();
				}else{
					partStr[2] = "";
				}
				return partStr;
		}
    
//CCEnd SS8
//CCBegin SS9
	/**
	���ݱ�����Ż���õ��Ż�����е���ز����㲿��������Ϣ�ļ���
	*/
	public Vector getNouseByID(BaseValueIfc ifc) throws QMException{
		Collection vector1=new Vector();
	  PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
	  BomNoticeService bService = (BomNoticeService)EJBServiceHelper.getService("BomNoticeService");;
		if (ifc instanceof BomAdoptNoticeIfc)
		{
			BomAdoptNoticeIfc bomInfo=(BomAdoptNoticeIfc)ifc;
			if (bomInfo.getPublishType().equals("�Ӳ���֪ͨ��")) {
				Collection col1 =bService. getPartsFromBomSubAdoptNotice(bomInfo.getBsoID());
				Vector vector2=new Vector();
//				QMPartInfo[] partInfos = new QMPartInfo[]();
				for(Iterator ite = col1.iterator();ite.hasNext();){
					BomAdoptNoticePartLinkInfo linkIfc = (BomAdoptNoticePartLinkInfo)ite.next();
					if (linkIfc.getAdoptBs().equals("������"))
					{
						QMPartInfo partInfo = (QMPartInfo)pservice.refreshInfo(linkIfc.getPartID());						
						vector2.add(partInfo);
					}
				}
				QMPartInfo[] partInfos = new QMPartInfo[vector2.size()];
				int i=0;
				for(Iterator ite = vector2.iterator();ite.hasNext();){
					QMPartInfo info=(QMPartInfo)ite.next();
					partInfos[i]=info;
					i=i+1;
				}				
				vector1=this.findPartAndRoute(partInfos,null);
			}				
      else if (bomInfo.getPublishType().equals("����֪ͨ��")) {
				Collection col2 = bService.getParentBomSubAdoptNotice((BomAdoptNoticeInfo)bomInfo);
				if(col2!=null&&col2.size()>0){
					Vector vector2=new Vector();
					for(Iterator ite = col2.iterator();ite.hasNext();){
						BomAdoptNoticeIfc noticeIfc = (BomAdoptNoticeIfc)ite.next();
						Collection col3 = bService.getPartsFromBomSubAdoptNotice(noticeIfc.getBsoID());
						for(Iterator ite1 = col3.iterator();ite1.hasNext();){
							BomAdoptNoticePartLinkInfo linkIfc = (BomAdoptNoticePartLinkInfo)ite1.next();
							if (linkIfc.getAdoptBs().equals("������"))
							{
								QMPartInfo partInfo = (QMPartInfo)pservice.refreshInfo(linkIfc.getPartID());						
								vector2.add(partInfo);
							}
						}
					}
//					QMPartInfo[] partInfos = (QMPartInfo[])vector2.toArray();
					QMPartInfo[] partInfos = new QMPartInfo[vector2.size()];
					int i=0;
					for(Iterator ite = vector2.iterator();ite.hasNext();){
						QMPartInfo info=(QMPartInfo)ite.next();
						partInfos[i]=info;
						i=i+1;
					}				
					vector1=this.findPartAndRoute(partInfos,null);
				}
			}
		}
		if (ifc instanceof BomChangeNoticeIfc)
		{
			BomChangeNoticeIfc bomInfo=(BomChangeNoticeIfc)ifc;
			if (bomInfo.getPublishType().equals("�ӱ��֪ͨ��")) {
				Collection col1 =bService. getPartsFromBomSubChangeNotice(bomInfo.getBsoID());
				Vector vector2=new Vector();
//				QMPartInfo[] partInfos = new QMPartInfo[]();
				for(Iterator ite = col1.iterator();ite.hasNext();){
					BomChangeNoticePartLinkInfo linkIfc = (BomChangeNoticePartLinkInfo)ite.next();
					if (linkIfc.getAdoptBs().equals("������"))
					{
						QMPartInfo partInfo = (QMPartInfo)pservice.refreshInfo(linkIfc.getPartID());						
						vector2.add(partInfo);
					}
				}
//				QMPartInfo[] partInfos = (QMPartInfo[])vector2.toArray();
				QMPartInfo[] partInfos = new QMPartInfo[vector2.size()];
				int i=0;
				for(Iterator ite = vector2.iterator();ite.hasNext();){
					QMPartInfo info=(QMPartInfo)ite.next();
					partInfos[i]=info;
					i=i+1;
				}				
				vector1=this.findPartAndRoute(partInfos,null);
			}				
      else if (bomInfo.getPublishType().equals("���֪ͨ��")) {
				Collection col2 = bService.getParentBomSubChangeNotice((BomChangeNoticeInfo)bomInfo);
				if(col2!=null&&col2.size()>0){
					Vector vector2=new Vector();
					for(Iterator ite = col2.iterator();ite.hasNext();){
						BomChangeNoticeIfc noticeIfc = (BomChangeNoticeIfc)ite.next();
						Collection col3 = bService.getPartsFromBomSubChangeNotice(noticeIfc.getBsoID());
						for(Iterator ite1 = col3.iterator();ite1.hasNext();){
							BomChangeNoticePartLinkInfo linkIfc = (BomChangeNoticePartLinkInfo)ite1.next();
							if (linkIfc.getAdoptBs().equals("������"))
							{
								QMPartInfo partInfo = (QMPartInfo)pservice.refreshInfo(linkIfc.getPartID());						
								vector2.add(partInfo);
							}
						}
					}
//					QMPartInfo[] partInfos = (QMPartInfo[])vector2.toArray();
					QMPartInfo[] partInfos = new QMPartInfo[vector2.size()];
					int i=0;
					for(Iterator ite = vector2.iterator();ite.hasNext();){
						QMPartInfo info=(QMPartInfo)ite.next();
						partInfos[i]=info;
						i=i+1;
					}				
					vector1=this.findPartAndRoute(partInfos,null);
				}
			}
			
		}
		return (Vector)vector1;
	}
	/**
	���ݱ�����Ż���õ��Ż�����е���ز������㲿��������Ϣ�ļ���
	*/
	public Vector getUseByID(BaseValueIfc ifc) throws QMException{
		Collection vector1=new Vector();
	  PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
	  BomNoticeService bService = (BomNoticeService)EJBServiceHelper.getService("BomNoticeService");;
		if (ifc instanceof BomAdoptNoticeIfc)
		{
			BomAdoptNoticeIfc bomInfo=(BomAdoptNoticeIfc)ifc;
			if (bomInfo.getPublishType().equals("�Ӳ���֪ͨ��")) {
				Collection col1 =bService. getPartsFromBomSubAdoptNotice(bomInfo.getBsoID());
				Vector vector2=new Vector();
//				QMPartInfo[] partInfos = new QMPartInfo[]();
				for(Iterator ite = col1.iterator();ite.hasNext();){
					BomAdoptNoticePartLinkInfo linkIfc = (BomAdoptNoticePartLinkInfo)ite.next();
					if (linkIfc.getAdoptBs().equals("����"))
					{
						QMPartInfo partInfo = (QMPartInfo)pservice.refreshInfo(linkIfc.getPartID());						
						vector2.add(partInfo);
					}
				}
//				QMPartInfo[] partInfos = (QMPartInfo[])vector2.toArray();
				QMPartInfo[] partInfos = new QMPartInfo[vector2.size()];
				int i=0;
				for(Iterator ite = vector2.iterator();ite.hasNext();){
					QMPartInfo info=(QMPartInfo)ite.next();
					partInfos[i]=info;
					i=i+1;
				}				
				vector1=this.findPartAndRoute(partInfos,null);
			}				
      else if (bomInfo.getPublishType().equals("����֪ͨ��")) {
				Collection col2 = bService.getParentBomSubAdoptNotice((BomAdoptNoticeInfo)bomInfo);
				if(col2!=null&&col2.size()>0){
					Vector vector2=new Vector();
					for(Iterator ite = col2.iterator();ite.hasNext();){
						BomAdoptNoticeIfc noticeIfc = (BomAdoptNoticeIfc)ite.next();
						Collection col3 = bService.getPartsFromBomSubAdoptNotice(noticeIfc.getBsoID());
						for(Iterator ite1 = col3.iterator();ite1.hasNext();){
							BomAdoptNoticePartLinkInfo linkIfc = (BomAdoptNoticePartLinkInfo)ite1.next();
							if (linkIfc.getAdoptBs().equals("����"))
							{
								QMPartInfo partInfo = (QMPartInfo)pservice.refreshInfo(linkIfc.getPartID());						
								vector2.add(partInfo);
							}
						}
					}
//					QMPartInfo[] partInfos = (QMPartInfo[])vector2.toArray();
					QMPartInfo[] partInfos = new QMPartInfo[vector2.size()];
					int i=0;
					for(Iterator ite = vector2.iterator();ite.hasNext();){
						QMPartInfo info=(QMPartInfo)ite.next();
						partInfos[i]=info;
						i=i+1;
					}				
					vector1=this.findPartAndRoute(partInfos,null);
				}
			}
		}
		if (ifc instanceof BomChangeNoticeIfc)
		{
			BomChangeNoticeIfc bomInfo=(BomChangeNoticeIfc)ifc;
			if (bomInfo.getPublishType().equals("�ӱ��֪ͨ��")) {
				Collection col1 =bService. getPartsFromBomSubChangeNotice(bomInfo.getBsoID());
				Vector vector2=new Vector();
//				QMPartInfo[] partInfos = new QMPartInfo[]();
				for(Iterator ite = col1.iterator();ite.hasNext();){
					BomChangeNoticePartLinkInfo linkIfc = (BomChangeNoticePartLinkInfo)ite.next();
					if (linkIfc.getAdoptBs().equals("����"))
					{
						QMPartInfo partInfo = (QMPartInfo)pservice.refreshInfo(linkIfc.getPartID());						
						vector2.add(partInfo);
					}
				}
//				QMPartInfo[] partInfos = (QMPartInfo[])vector2.toArray();
				QMPartInfo[] partInfos = new QMPartInfo[vector2.size()];
				int i=0;
				for(Iterator ite = vector2.iterator();ite.hasNext();){
					QMPartInfo info=(QMPartInfo)ite.next();
					partInfos[i]=info;
					i=i+1;
				}				
				vector1=this.findPartAndRoute(partInfos,null);
			}				
      else if (bomInfo.getPublishType().equals("���֪ͨ��")) {
				Collection col2 = bService.getParentBomSubChangeNotice((BomChangeNoticeInfo)bomInfo);
				if(col2!=null&&col2.size()>0){
					Vector vector2=new Vector();
					for(Iterator ite = col2.iterator();ite.hasNext();){
						BomChangeNoticeIfc noticeIfc = (BomChangeNoticeIfc)ite.next();
						Collection col3 = bService.getPartsFromBomSubChangeNotice(noticeIfc.getBsoID());
						for(Iterator ite1 = col3.iterator();ite1.hasNext();){
							BomChangeNoticePartLinkInfo linkIfc = (BomChangeNoticePartLinkInfo)ite1.next();
							if (linkIfc.getAdoptBs().equals("����"))
							{
								QMPartInfo partInfo = (QMPartInfo)pservice.refreshInfo(linkIfc.getPartID());						
								vector2.add(partInfo);
							}
						}
					}
//					QMPartInfo[] partInfos = (QMPartInfo[])vector2.toArray();
					QMPartInfo[] partInfos = new QMPartInfo[vector2.size()];
					int i=0;
					for(Iterator ite = vector2.iterator();ite.hasNext();){
						QMPartInfo info=(QMPartInfo)ite.next();
						partInfos[i]=info;
						i=i+1;
					}				
					vector1=this.findPartAndRoute(partInfos,null);
				}
			}
			
		}
		return (Vector)vector1;
	}

//CCEnd SS9
//CCBegin SS10
	public boolean isLogical(QMPartIfc ifc) throws QMException{
		boolean result=false;
		//��5λΪG��װ��·��Ϊ�ղ�������·�ߺ����á�,��װ��·�߲�Ϊ�ղ�������·�߲������á�
//CCBegin SS23
//		if (ifc.getPartNumber().substring(4, 5).equals("G"))
		if (ifc.getPartNumber().length()>4 && ifc.getPartNumber().substring(4, 5).equals("G"))
//CCEnd SS23
		{
	    String[] partBso = new String[1];
	    partBso[0] = ifc.getBsoID();
			try {
				Collection cols=getRouteByJDBC(partBso);
				if(cols!=null&&cols.size()>0){
					for(Iterator ite = cols.iterator();ite.hasNext();){
						Vector vec = (Vector)ite.next();
						if(vec!=null&&vec.size()>0){
							for(int n = 0 ; n <vec.size();n++){
								String[] routeStrs = (String[])vec.get(n);
								String routeStr = routeStrs[0];
								String mainStr =routeStrs[1] ;
//		System.out.println("-------++++++++"+mainStr+"!!!!!!!!!!:::::"+routeStr+"}}}");
								//routeListBsoID = routeStrs[2];
								//�ж��Ƿ�����Ҫ·�ߣ�ֻ��¼��Ҫ·��
								if(mainStr.equals("1")){
									String[] routest = routeStr.split("@");
									String makeStr = routest[0];
									String assembStr = routest[1];
		    					if(makeStr.contains("��")&&(assembStr==null || assembStr.equals("") || assembStr.equals("��"))){
		    						result=true;
		    					}
//CCBegin SS20
		    					if(!makeStr.contains("��")&&(assembStr!=null && !assembStr.equals("") && !assembStr.equals("��"))){
		    						result=true;
		    					}
//CCBegin SS20
								}
	
							}
						}
					}
				}
				} catch (SQLException e) {
		
					e.printStackTrace();
				}
		
		}
		return result;
	}
	 /**
	    * ��Ų鿴����BOM����Ų鿴����BOM��������
	    * @param String bsoID
	    * @return Collection
	    */
		public Collection getReleaseBomDJ(QMPartIfc partIfc)throws QMException {
			PersistService service = (PersistService) EJBServiceHelper.getService("PersistService");
	    	StandardPartService sp = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
	    	PartUsageLinkIfc partlink=new PartUsageLinkInfo();

	    	Vector vec=new Vector();
	    	//��һ��Ԫ�أ�QMPartIfc;�ڶ�Ԫ�أ�ʹ������String������Ԫ�أ�·���������String[]
    		  try {
    		conn = getConnection();
	    	vec = getAllParts(partIfc,partlink,vec,0,true);
		if(conn!=null){
			conn.close();	
		}//CCBegin SS25
			} catch (Exception e) {
				e.printStackTrace();
			}//CCEnd SS25
	    	 return vec;
		  
		}
//CCEnd SS10
//		CCBegin SS15
public boolean isPartVersion(String partNumber){
	 if(partNumber.indexOf("/")>=0){
     	int a = partNumber.indexOf("/");
     	//System.out.println("a="+a);
     	String temp = partNumber.substring(a);
     	//System.out.println("temp="+temp);
     	//��ȫƥ����a
     	String[] array1 = {"0","ZBT","AH","BQ"};
     	//���м��͡�a��
     	String[] array2 = {"L01","J0","J1"};
     	//�ڽ�βa��
     	String[] array3 = {"0","1","2","3","4","(L)","-SF","-ZC"};
     	//��ȫƥ����a
     	for (int i1 = 0; i1 < array1.length; i1++){
     		String str = array1[i1];
     		if(str.equals(temp)){
     			return true;
     		}
     	}
     	//���м��͡�a��
     	for (int i1 = 0; i1 < array2.length; i1++){
     		String str = array2[i1];
     		if(temp.indexOf(str)>=0){
     			return true;
     		}
     	}
     	//�ڽ�βa��
     	for (int i1 = 0; i1 < array3.length; i1++){
     		String str = array3[i1];
     		if(temp.endsWith(str)){
     			return true;
     		}
     	}
     }
	 return false;
}
//CCEnd SS15


    //CCBegin SS26
    /**
     * 
     */
    public Collection getFsqd(QMPartIfc partIfc) throws QMException
    {
        String rootID = partIfc.getBsoID();
        ArrayList arr = new ArrayList();
//        rootID = "QMPart_2949966";
        ArrayList allParts = getGybomstructureChildAll(rootID, arr);
        PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
        HashMap map = new HashMap();

        try
        {
            conn = getConnection();
            for(int i = 0;i < allParts.size();i++)
            {
                String strs[] = (String[])allParts.get(i);
                String parentPart = strs[0];
                String childPart = strs[1];
                String quantity = strs[2];
                Object[] obj = (Object[])map.get(childPart);
                if(obj == null)
                {
                    obj = new Object[6];
                }

                QMPartIfc part = (QMPartIfc)ps.refreshInfo(childPart, false);
                obj[0] = part;

                Collection ibaCol = getPartIBA(part, "�����", "virtualPart");
                if(ibaCol != null && ibaCol.size() > 0)
                {
                    Iterator ibaIte = ibaCol.iterator();
                    StringValueIfc existValue = (StringValueIfc)ibaIte.next();
                    obj[1] = existValue.getValue();
                }else
                {
                    obj[1] = "";
                }

                String partNumberVer = part.getPartNumber() + "/" + part.getVersionID();
                Object routeObj = filterPartWithRoute(part, partNumberVer);
                if(routeObj instanceof QMPartIfc)
                {//����ʾ�����
                    String[] strV = new String[2];
                    strV[0] = "";
                    strV[1] = "";
                    obj[2] = strV;
                }else if(routeObj instanceof String[])
                {//��ʾ�����
                    String[] strV = (String[])routeObj;
                    obj[2] = routeObj;
                }

                obj[5] = quantity;

                map.put(childPart, obj);
            }
            if(conn != null)
            {
                conn.close();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        ArrayList ret = new ArrayList(map.values());
        return ret;
    }
    
    public ArrayList getGybomstructureChildAll(String bsoID, ArrayList arr) throws QMException
    {
//        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaabbb = "+bsoID);
        ArrayList parts = getGybomstructureChild(bsoID);
        for(int i=0; i<parts.size(); i++)
        {
            String strs[] = (String[])parts.get(i);
            arr.add(strs);
            getGybomstructureChildAll(strs[1], arr);
        }
        
        return arr;
    }
    
    private ArrayList getGybomstructureChild(String bsoID)
    {

        ArrayList ret = new ArrayList();
        //���¹������Ӽ�id�滻����һ���㲿��id
        Connection conn = null;
        Statement stmt =null;
        ResultSet rs=null;
        try
        {
            
            conn = PersistUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from gybomstructure where parentPart='" + 
                    bsoID + "'");
            while(rs.next())
            {
                String parentPart=rs.getString("PARENTPART");
                String childPart=rs.getString("CHILDPART");
                String quantity=rs.getString("QUANTITY");
                String strs[] = new String[3];
                strs[0] = parentPart;
                strs[1] = childPart;
                strs[2] = quantity;
//                System.out.println("ccccccccccccccccccc=="+childPart);
                ret.add(strs);
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
    //CCEnd SS26
    
  //CCBegin SS29
	/**
	 * ��ȡ��ǰ�û����ڵĹ�����
	 * @return ����
	 * @throws QMException
	 */
	public String getCurrentDWBS() throws QMException
	{
		String dwbs="";
		DocServiceHelper dsh=new DocServiceHelper();
		String com=dsh.getUserFromCompany();
		CodingManageService cmservice=(CodingManageService)EJBServiceHelper.getService("CodingManageService");
		CodingIfc coding=cmservice.findCodingByContent(com,"��������","�������");
		if(coding==null)
		{
			dwbs="";
		}
		else
		{
			dwbs=coding.getShorten();
			if(dwbs==null)
			{
				dwbs="";
			}
		}
		if(com.equals("Admin"))
		{
			dwbs="R06";
		}
		return dwbs;
	}
	
   /**
	 * �������bsoID����ѯ�������·��
	 * @return
	 * @throws SQLException
	 */
	private Collection getSecondRouteByJDBC(String partID)throws SQLException
	{
		try
		{
			conn = getConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		ResultSet rs = null;
		Statement statement = conn.createStatement();
		StringBuffer parts = new StringBuffer();
		Collection col =  new ArrayList();
		String routesql="select mainStr,rightBsoID from consListRoutePartLink where  releaseIdenty='1' and rightBsoID='"+partID+"' order by modifytime desc";
		rs=statement.executeQuery(routesql);
		String[] str=new String[2];
		while(rs.next())
		{
			if(rs.getString(1)==null)
			{
				continue;
			}
			String route=rs.getString(1).toString();
			if(route.indexOf("=")!=-1)
			{
				str=rs.getString(1).toString().split("=");
			}
			else
			{
				str[0]=route;
			}
			if(str[0]==null)
			{
				str[0]="";
			}
			if(str[1]==null)
			{
				str[1]="";
			}
			col.add(str);
		}
		
		try
		{
			if(conn!=null)
			{
				conn.close();	
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return col;
	}
	//CCEnd SS29
}
