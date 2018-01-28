
package com.faw_qm.jferp.util;


import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.workflow.definer.ejb.service.WfDefinerService;
import com.faw_qm.workflow.definer.model.WfProcessTemplateIfc;
import com.faw_qm.workflow.definer.model.WfProcessTemplateMasterIfc;
import com.faw_qm.workflow.definer.util.ProcessDataInfo;
import com.faw_qm.workflow.engine.ejb.entity.ProcessData;
import com.faw_qm.workflow.engine.ejb.entity.WfProcess;
import com.faw_qm.workflow.engine.ejb.service.WfEngineService;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.workflow.engine.model.WfProcessInfo;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 *��erp������������Ԥ���ࡣ
 * @author ������
 * @version 1.0
 *  SS1 ȥ��Ԥ����Ϣ����Ҫ ������ 2014-12-11
 */ 
public class WfProcessAlert
{
     

    /**
     * ȱʡ���캯����
     */
    public WfProcessAlert()
    {
        super();
    }
    /**·�߷�����ҪԤ��
	     * @throws Exception
	     */
	    public void publishRouteForERP(BaseValueIfc base)throws Exception
	    {
	    	boolean ispush=true;
	        {
	        
	          PublishData publish = new PublishData();
	          TechnicsRouteListIfc list = (TechnicsRouteListIfc)base;
	          try {
	          
	           Collection coll = null;
		    	
		    	System.out.println("listlistlist======="+list.getBsoID());
		    	publish.publishPartsToERPlc(coll,list.getBsoID());
	          }
	          catch (Exception e) {
	            e.printStackTrace();
	         
	            ispush=false;
	          }
	          // CCBegin SS1 
//	          finally {
//	            
//
//	            if(!ispush){
//
//	            	SessionService ss = (SessionService) EJBServiceHelper.getService(
//	                        "SessionService");
//	                  
//	            	processpublishRouteDataForERP(list);
//	            	ispush=true;
//	            }
//	          }//CCEnd SS1
	        }
	    }
	    /**·�߷���ҲҪ��ع�
	    * @param user �û�
	    * @param list ·�߱�
	    * @throws Exception
	    */
	    private void processpublishDataForERP( TechnicsRouteListIfc list) throws Exception {
	    	 UserIfc user = null;
	     	SessionService ss = (SessionService) EJBServiceHelper.getService(
	                 "SessionService");
	             user = ss.getCurUserInfo();
	      QMQuery query = new QMQuery("WfProcess");
	      QueryCondition con = new QueryCondition("name", "=", "��ERP����Ԥ������");
	      query.addCondition(con);
	      QueryCondition con1 = new QueryCondition("state", "=", "OPEN_RUNNING");
	      query.addAND();
	      query.addCondition(con1);
	      
	      PersistService  ps = (PersistService) EJBServiceHelper.getService("PersistService");
	      
	      Collection col = ps.findValueInfo(query, false);
	               if (col.size()!=0) {
	                return;
	                       }
	                    //����һ���µ�����
	               WfProcessIfc process = WfProcessInfo.newWfProcessInfo();
	             try {
	            //������������
	              String processName = "��ERP����Ԥ������";
	                process.setName(processName);
	            //ȡ������ģ��
	               WfProcessTemplateIfc wftemplate = getProcessByName(processName);
	             //���������еĶ���
	              process.setBusinessObjBsoID(list.getBsoID());
	            //��������ģ��id
	              process.setTemplateID(wftemplate.getBsoID());
	            //��������������
	                 process.setCreator(user.getBsoID());
	               WfEngineService wservice = (WfEngineService) EJBServiceHelper.getService(
	                "WfEngineService");
	              //����������Ϣ
	                  WfProcess wfprocess = (WfProcess) ps.storeBso(process);
	                 ProcessDataInfo processdatainfo = wftemplate.getContextSignature();
	                 ProcessData processdata = ProcessData.newProcessData(
	                 processdatainfo);
	                 processdata.setValue("primaryBusinessObject", list);
//	                 processdata.setValue("����˵��", "");
//	                 processdata.setValue("publishtype","biangengOrcaiyong");
	                 process.setContext(processdata);
	                  wfprocess = (WfProcess) ps.updateBso(process);
	                   //��������
	                 wservice.startProcess(wfprocess, processdata,
	                 Long.valueOf("1").longValue());
	                   }
	                 catch (Exception e) {
	                 e.printStackTrace();
	                   }
	                  }	  
/**·�߷���ҲҪ��ع�
* @param user �û�
* @param list ·�߱�
* @throws Exception
*/
private void processpublishRouteDataForERP( TechnicsRouteListIfc list) throws Exception {
	 UserIfc user = null;
 	SessionService ss = (SessionService) EJBServiceHelper.getService(
             "SessionService");
         user = ss.getCurUserInfo();
  QMQuery query = new QMQuery("WfProcess");
  QueryCondition con = new QueryCondition("name", "=", "��ERP����Ԥ������");
  query.addCondition(con);
  QueryCondition con1 = new QueryCondition("state", "=", "OPEN_RUNNING");
  query.addAND();
  query.addCondition(con1);
  
  PersistService  ps = (PersistService) EJBServiceHelper.getService("PersistService");
  
  Collection col = ps.findValueInfo(query, false);
           if (col.size()!=0) {
            return;
                   }
                //����һ���µ�����
           WfProcessIfc process = WfProcessInfo.newWfProcessInfo();
         try {
        //������������
          String processName = "��ERP����Ԥ������";
            process.setName(processName);
        //ȡ������ģ��
           WfProcessTemplateIfc wftemplate = getProcessByName(processName);
         //���������еĶ���
          process.setBusinessObjBsoID(list.getBsoID());
        //��������ģ��id
          process.setTemplateID(wftemplate.getBsoID());
        //��������������
             process.setCreator(user.getBsoID());
           WfEngineService wservice = (WfEngineService) EJBServiceHelper.getService(
            "WfEngineService");
          //����������Ϣ
              WfProcess wfprocess = (WfProcess) ps.storeBso(process);
             ProcessDataInfo processdatainfo = wftemplate.getContextSignature();
             ProcessData processdata = ProcessData.newProcessData(
             processdatainfo);
             processdata.setValue("primaryBusinessObject", list);
//             processdata.setValue("����˵��", "");
//             processdata.setValue("publishtype","biangengOrcaiyong");
             process.setContext(processdata);
              wfprocess = (WfProcess) ps.updateBso(process);
               //��������
             wservice.startProcess(wfprocess, processdata,
             Long.valueOf("1").longValue());
               }
             catch (Exception e) {
             e.printStackTrace();
               }
              }
/**��ù�����״̬
* @param name ����������
* @return ����״̬
* @throws Exception
*/
private WfProcessTemplateIfc getProcessByName(String name) throws Exception {
WfProcessTemplateMasterIfc master = null;
PersistService  ps = (PersistService) EJBServiceHelper.getService("PersistService");
QMQuery query = new QMQuery("WfProcessTemplateMaster");
QueryCondition cond = new QueryCondition("name", "=", name);
query.addCondition(cond);
WfDefinerService dservice = (WfDefinerService) EJBServiceHelper.
    getService("WfDefinerService");
Collection coll = (Collection) ps.findValueInfo(query, false);
if (coll == null) {
  return null;
}
Iterator iterator = coll.iterator();
if (iterator.hasNext()) {
  master = ( (WfProcessTemplateMasterIfc) iterator.next());
  return dservice.getLatestIteration(master);
}
return null;
}
    
}
