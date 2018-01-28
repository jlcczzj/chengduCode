
package com.faw_qm.erp.util;


import java.text.SimpleDateFormat;
import java.util.*;

import com.faw_qm.consadoptnotice.model.UniteIdentifyInfo;
import com.faw_qm.erp.ejb.service.IntePackService;
import com.faw_qm.erp.ejb.service.MaterialSplitService;
import com.faw_qm.erp.model.IntePackIfc;
import com.faw_qm.erp.model.IntePackInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.jf.util.jfuputil;

import java.util.Vector;

/**
 * 发布路线信息启动类。
 * @author 刘家坤
 * @version 1.0
 */ 
public class PublishData
{
     

    /**
     * 缺省构造函数。
     */
    public PublishData()
    {
        super();
    }
    public void publishDataToERP(BaseValueIfc base) throws Exception {
//  	  System.out.println("jinru---------------------kaishifabu1111");
  	    try {
//  	    	System.out.println("jinru---------------------kaishifabu");
  	    	TechnicsRouteListIfc list = (TechnicsRouteListIfc)base;
  	    	System.out.println("listlistlist======="+list.getBsoID());
           publishPartsToERPlc(list.getBsoID());

  	    }
  	    
  	    catch (Exception e) {
  	      e.printStackTrace();
  	      throw e;
  	    }
  	  }
    
    public void publishPartsToERPlc(String routeID)
        throws Exception
    {
        try
        {
     	//  System.out.println("jinru-------publishPartsToERPlc");
            String xmlName = "";
            //生成路线表名称
           xmlName = getVirtualPartNumber1();
 
            MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
            //拆分零部件
            Vector filterPartVec=msservice.split(routeID);
            
           System.out.println("filterPartVec-------"+filterPartVec);
            if(filterPartVec==null||filterPartVec.size()==0){
          	  return;     
            }
            System.out.println("qqq-------"+filterPartVec.size());
           //将数据打集成包
            String packid = createIntePack(xmlName, "", true, routeID);   

            
            IntePackService packservice = (IntePackService)EJBServiceHelper.getService("IntePackService");;
            //packservice.publishIntePack(packid, xmlName,al,filterPartVec);
            //发布数据
            packservice.publishIntePack(packid, xmlName,null,filterPartVec);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }
    private String createIntePack(String xmlName, String baselineID, boolean flag, String routeID)
    throws QMException
{
    String name = xmlName;
    String sourceid = "";
    IntePackSourceType sourcetype = null;
    if(flag)
    {
        sourceid = routeID;
        sourcetype = IntePackSourceType.technicsRouteList;
    } else
    {
        sourceid = baselineID;
        sourcetype = IntePackSourceType.BASELINE;
    }
    IntePackInfo intepack = new IntePackInfo();
    intepack.setName(name);
    intepack.setSourceType(sourcetype);
    intepack.setSource(sourceid);
    IntePackService itservice = (IntePackService)EJBServiceHelper.getService("IntePackService");
    IntePackIfc intepackifc = itservice.createIntePack(intepack);
    String bsoid = intepackifc.getBsoID();
    return bsoid;
}
    //CCBegin by chudaming 20100520为流程自动发布添加
    private String getVirtualPartNumber1() {
     
    	  Date today=new Date();
    	  SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd-hhmmss");
    	  String time=f.format(today);

        return time;
      }
//    public int getNextSortNumber(String className,String baseKey,boolean changeFlag) throws QMException
//    {
//    int result=0;
//    try{
//      PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
//      QMQuery query =new  QMQuery("UniteIdentify");
//      QueryCondition qc=new QueryCondition("className","=",className);
//      query.addCondition(qc);
//      QueryCondition qc1=new  QueryCondition("baseKey","=",baseKey);
//      query.addAND();
//      query.addCondition(qc1);
//      Collection col=pService.findValueInfo(query);
//      UniteIdentifyInfo uniteIde;
//      if(col.size()==0)
//      {
//          uniteIde = createUniteIdentify(className, baseKey, changeFlag);
//          result = uniteIde.getSortNumber();
//      }
//      else
//      {
//        Iterator ite = col.iterator();
//        uniteIde = (UniteIdentifyInfo) ite.next();
//        result=uniteIde.getSortNumber();
//      }
//      uniteIde.setSortNumber(result+1);
//      pService.saveValueInfo(uniteIde);
//
//
//    }catch(QMException e)
//    {
//      e.printStackTrace();
//      throw new QMException(e);
//    }
//    return result;
//
//}
//
//  public UniteIdentifyInfo createUniteIdentify(String className,String baseKey,boolean changeFlag) throws QMException
//  {
//    UniteIdentifyInfo ui=null;
//    try{
//      PersistService pService =(PersistService)EJBServiceHelper.getPersistService();
//      if(changeFlag)
//      {
//        QMQuery query=new QMQuery("UniteIdentify");
//        QueryCondition qc=new QueryCondition("className","=",className);
//        query.addCondition(qc);
//        Collection col=pService.findValueInfo(query);
//        Iterator ite=col.iterator();
//        while(ite.hasNext())
//        {
//          UniteIdentifyInfo oldui=(UniteIdentifyInfo)ite.next();
//          pService.deleteValueInfo(oldui);
//        }
//      }
//      ui=new UniteIdentifyInfo();
//      ui.setClassName(className);
//      ui.setBaseKey(baseKey);
//      ui.setChangeFlag(changeFlag);
//      ui.setSortNumber(1);
//      ui=(UniteIdentifyInfo)pService.saveValueInfo(ui);
//    }catch(QMException e)
//    {
//      e.printStackTrace();
//      throw new QMException(e);
//    }
//    return ui;
//  }
    
}
