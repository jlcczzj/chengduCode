
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
 *向erp发布数据流程预警类。
 * @author 刘家坤
 * @version 1.0
 *  SS1 去掉预警信息不需要 刘家坤 2014-12-11
 */ 
public class WfProcessAlert
{
     

    /**
     * 缺省构造函数。
     */
    public WfProcessAlert()
    {
        super();
    }
    /**路线发布需要预警
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
	    /**路线发布也要求回滚
	    * @param user 用户
	    * @param list 路线表
	    * @throws Exception
	    */
	    private void processpublishDataForERP( TechnicsRouteListIfc list) throws Exception {
	    	 UserIfc user = null;
	     	SessionService ss = (SessionService) EJBServiceHelper.getService(
	                 "SessionService");
	             user = ss.getCurUserInfo();
	      QMQuery query = new QMQuery("WfProcess");
	      QueryCondition con = new QueryCondition("name", "=", "向ERP发放预警流程");
	      query.addCondition(con);
	      QueryCondition con1 = new QueryCondition("state", "=", "OPEN_RUNNING");
	      query.addAND();
	      query.addCondition(con1);
	      
	      PersistService  ps = (PersistService) EJBServiceHelper.getService("PersistService");
	      
	      Collection col = ps.findValueInfo(query, false);
	               if (col.size()!=0) {
	                return;
	                       }
	                    //创建一个新的流程
	               WfProcessIfc process = WfProcessInfo.newWfProcessInfo();
	             try {
	            //设置流程名字
	              String processName = "向ERP发放预警流程";
	                process.setName(processName);
	            //取得流程模板
	               WfProcessTemplateIfc wftemplate = getProcessByName(processName);
	             //设置流程中的对象
	              process.setBusinessObjBsoID(list.getBsoID());
	            //设置流程模板id
	              process.setTemplateID(wftemplate.getBsoID());
	            //设置流程启动者
	                 process.setCreator(user.getBsoID());
	               WfEngineService wservice = (WfEngineService) EJBServiceHelper.getService(
	                "WfEngineService");
	              //保存流程信息
	                  WfProcess wfprocess = (WfProcess) ps.storeBso(process);
	                 ProcessDataInfo processdatainfo = wftemplate.getContextSignature();
	                 ProcessData processdata = ProcessData.newProcessData(
	                 processdatainfo);
	                 processdata.setValue("primaryBusinessObject", list);
//	                 processdata.setValue("发布说明", "");
//	                 processdata.setValue("publishtype","biangengOrcaiyong");
	                 process.setContext(processdata);
	                  wfprocess = (WfProcess) ps.updateBso(process);
	                   //启动流程
	                 wservice.startProcess(wfprocess, processdata,
	                 Long.valueOf("1").longValue());
	                   }
	                 catch (Exception e) {
	                 e.printStackTrace();
	                   }
	                  }	  
/**路线发布也要求回滚
* @param user 用户
* @param list 路线表
* @throws Exception
*/
private void processpublishRouteDataForERP( TechnicsRouteListIfc list) throws Exception {
	 UserIfc user = null;
 	SessionService ss = (SessionService) EJBServiceHelper.getService(
             "SessionService");
         user = ss.getCurUserInfo();
  QMQuery query = new QMQuery("WfProcess");
  QueryCondition con = new QueryCondition("name", "=", "向ERP发放预警流程");
  query.addCondition(con);
  QueryCondition con1 = new QueryCondition("state", "=", "OPEN_RUNNING");
  query.addAND();
  query.addCondition(con1);
  
  PersistService  ps = (PersistService) EJBServiceHelper.getService("PersistService");
  
  Collection col = ps.findValueInfo(query, false);
           if (col.size()!=0) {
            return;
                   }
                //创建一个新的流程
           WfProcessIfc process = WfProcessInfo.newWfProcessInfo();
         try {
        //设置流程名字
          String processName = "向ERP发放预警流程";
            process.setName(processName);
        //取得流程模板
           WfProcessTemplateIfc wftemplate = getProcessByName(processName);
         //设置流程中的对象
          process.setBusinessObjBsoID(list.getBsoID());
        //设置流程模板id
          process.setTemplateID(wftemplate.getBsoID());
        //设置流程启动者
             process.setCreator(user.getBsoID());
           WfEngineService wservice = (WfEngineService) EJBServiceHelper.getService(
            "WfEngineService");
          //保存流程信息
              WfProcess wfprocess = (WfProcess) ps.storeBso(process);
             ProcessDataInfo processdatainfo = wftemplate.getContextSignature();
             ProcessData processdata = ProcessData.newProcessData(
             processdatainfo);
             processdata.setValue("primaryBusinessObject", list);
//             processdata.setValue("发布说明", "");
//             processdata.setValue("publishtype","biangengOrcaiyong");
             process.setContext(processdata);
              wfprocess = (WfProcess) ps.updateBso(process);
               //启动流程
             wservice.startProcess(wfprocess, processdata,
             Long.valueOf("1").longValue());
               }
             catch (Exception e) {
             e.printStackTrace();
               }
              }
/**获得工作流状态
* @param name 工作流名称
* @return 流程状态
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
