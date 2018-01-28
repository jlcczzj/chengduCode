
package com.faw_qm.cderp.util;


import java.text.SimpleDateFormat;
import java.util.*;

import com.faw_qm.cderp.ejb.service.MaterialSplitService;
import com.faw_qm.consadoptnotice.model.UniteIdentifyInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybom.util.GYBomHelper;
import com.faw_qm.gybomNotice.ejb.service.GYBomNoticeService;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticePartLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;
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
    /**
	 * 艺准通知书向ERP发布
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public void CDRouteForERP(BaseValueIfc base) throws Exception {
  	    try {
  	    	System.out.println("向erp发布数据=======");
  	    	WfProcessAlert alert = new WfProcessAlert();
  	    	Collection coll = null;
  	    	TechnicsRouteListIfc list = (TechnicsRouteListIfc)base;
  	    	alert.publishRouteForERP(list);

  	    } 
  	    catch (QMException e) {
  	      e.printStackTrace();
  	      throw e;
  	    }
  	  }
    /**
	 * 艺准通知书向ERP发布
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public void CDRouteForERP1(String baseid) throws Exception {
  	    try {
  	    	System.out.println("向erp发布route数据=======");
  	    	PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
  	    	TechnicsRouteListIfc list = (TechnicsRouteListIfc)pservice.refreshInfo(baseid);

  	    	WfProcessAlert alert = new WfProcessAlert();
  	    	Collection coll = null;

  	    	alert.publishRouteForERP(list);

  	    } 
  	    catch (QMException e) {
  	      e.printStackTrace();
  	      throw e;
  	    }
  	  }
    /**
	 * BOM向ERP发布
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public static void CDPartForERP(BaseValueIfc base) throws Exception {
  	    try {
  	    	//HashMap scbbMap = new HashMap(); 
  	    	System.out.println("向erp发布BOM数据======="+base);
  	    	PartHelper helper = new PartHelper();
  	    	//查找需要批量替换的车型集合
  	    	Vector cxvec = new Vector();
  	    	
  	    	PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
  
  	    	GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)base;
  	    
//  	    	Collection vec = null;
//  	    	Vector coll = new Vector();

//  	    	获取车型零件
  	    	String partID = list.getTopPart();
  	    	cxvec = helper.getBatchUpdateCM(partID,"W34");
  	    	//对车型中每一个进行循环
  	    	for(int i=0;i<cxvec.size();i++){
  	    		Collection vec = null;
  	    		//QMPartIfc partIfc = (QMPartIfc) pservice.refreshInfo(partID);
  	    		QMPartIfc partIfc = (QMPartIfc)cxvec.elementAt(i);
  	    		//System.out.println("partIfc======="+partIfc.getPartNumber());
  	  	        vec = helper.getReleaseBom(partIfc,"W34");

  	  	         //System.out.println("vecll11111111111="+vec.size());
            
////  	  	    加入车型零件
//  	  	        if(vec.size()!=0){
//  	  	        	coll.add(partIfc);
//  	  	        }
  		    	
  		 
  		    	//scbbMap.put(partIfc.getPartNumber(), cxVerion);
	  	    	if(vec==null||vec.size()==0){
	  	    		System.out.println(partIfc.getPartNumber()+"工艺bom数据为空，无法向erp发布");
	  	    		continue;
	  	    	}
	  	    	Iterator iter = vec.iterator();
	  	    	Vector coll = new Vector();
		    	while(iter.hasNext()){
		    		Object[] obj = new Object[5];
		    		obj = (Object[])iter.next();
		    		QMPartIfc partIfc1 = (QMPartIfc)obj[0];
		    		coll.add(partIfc1);
		    		//System.out.println("partIfc1======="+partIfc1.getPartNumber());
		    	}
	
	//	    	System.out.println("scbbMap11111111111="+vec.size());
	
		    	PublishData data = new PublishData();
	  	    	data.publishPartsToERP(coll,list.getBsoID(),vec,i);
  	    	}
  	    	
  	    } 
  	    catch (Exception e) {
  	      e.printStackTrace();
  	      throw e;
  	    }
  	  }
    
    /**
	 * BOM向ERP发布测试用
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public static void CDPartForERP1(String list1) throws Exception {
  	    try {
  	    	HashMap scbbMap = new HashMap();
  	    	PartHelper helper = new PartHelper();
  	  	//查找需要批量替换的车型集合
  	    	Vector cxvec = new Vector();
  	    	System.out.println("向erp发布数据工艺bom=======");
  	    	GYBomNoticeService bomservice = (GYBomNoticeService)EJBServiceHelper.getService("GYBomNoticeService");
  	    	PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
  	    	GYBomAdoptNoticeInfo base = (GYBomAdoptNoticeInfo)pservice.refreshInfo(list1);

//  	    HashMap scbbMap = new HashMap();
  	    	System.out.println("向erp发布BOM数据======="+base);
  	    	
  	   
  
  	    	GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)base;
  	    
  	    	
  	    	

//  	    	获取车型零件
  	  	    String partID = list.getTopPart();
  	  		cxvec = helper.getBatchUpdateCM(partID,"W34");
  	    	//对车型中每一个进行循环
  	    	for(int i=0;i<cxvec.size();i++){
  	    		Collection vec = null;
  	    		//QMPartIfc partIfc = (QMPartIfc) pservice.refreshInfo(partID);
  	    		QMPartIfc partIfc = (QMPartIfc)cxvec.elementAt(i);
  	    		System.out.println("partIfc======="+partIfc.getPartNumber());
  	  	        vec = helper.getReleaseBom(partIfc,"W34");

  	  	         System.out.println("vecll11111111111="+vec.size());
            
////  	  	    加入车型零件
//  	  	        if(vec.size()!=0){
//  	  	        	coll.add(partIfc);
//  	  	        }
  		    	
  		 
  		    	//scbbMap.put(partIfc.getPartNumber(), cxVerion);
	  	    	if(vec==null||vec.size()==0){
	  	    		System.out.println(partIfc.getPartNumber()+"工艺bom数据为空，无法向erp发布");
	  	    		continue;
	  	    	}
	  	    	Iterator iter = vec.iterator();
	  	    	Vector coll = new Vector();
		    	while(iter.hasNext()){
		    		Object[] obj = new Object[5];
		    		obj = (Object[])iter.next();
		    		QMPartIfc partIfc1 = (QMPartIfc)obj[0];
		    		coll.add(partIfc1);
		    	//	System.out.println("partIfc1======="+partIfc1.getPartNumber());
		    	}
	
	//	    	System.out.println("scbbMap11111111111="+vec.size());
	//	    	System.out.println("coll11111111111="+coll);
		    	PublishData data = new PublishData();
	  	    	data.publishPartsToERP(coll,list.getBsoID(),vec,i);
  	    	}
  	    } 
  	    catch (Exception e) {
  	      e.printStackTrace();
  	      throw e;
  	    }
  	  }

    /**
	 * 路线向ERP发布
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public void publishPartsToERPlc(Collection coll,String routeID)
        throws Exception
    {
    	MaterialSplitService materialservice = (MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
    	materialservice.publishPartsToERPlc( coll, routeID);
     
    }
    
    public void publishPartsToERP(Collection coll ,String id,Collection vec,int i )
    throws Exception
{
	MaterialSplitService materialservice = (MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");;
	materialservice.publishPartsToERP(coll, id,vec,i);
 
}


    //CCBegin by chudaming 20100520为流程自动发布添加
    public String getVirtualPartNumber() {
     
    	  Date today=new Date();
    	  SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd-hhmmss");
    	  String time=f.format(today);

        return time.trim();
      }
    public int getNextSortNumber(String className,String baseKey,boolean changeFlag) throws QMException
    {
    int result=0;
    try{
      PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
      QMQuery query =new  QMQuery("UniteIdentify");
      QueryCondition qc=new QueryCondition("className","=",className);
      query.addCondition(qc);
      QueryCondition qc1=new  QueryCondition("baseKey","=",baseKey);
      query.addAND();
      query.addCondition(qc1);
      Collection col=pService.findValueInfo(query);
      UniteIdentifyInfo uniteIde;
      if(col.size()==0)
      {
          uniteIde = createUniteIdentify(className, baseKey, changeFlag);
          result = uniteIde.getSortNumber();
      }
      else
      {
        Iterator ite = col.iterator();
        uniteIde = (UniteIdentifyInfo) ite.next();
        result=uniteIde.getSortNumber();
      }
      uniteIde.setSortNumber(result+1);
      pService.saveValueInfo(uniteIde);


    }catch(QMException e)
    {
      e.printStackTrace();
      throw new QMException(e);
    }
    return result;

}

  public UniteIdentifyInfo createUniteIdentify(String className,String baseKey,boolean changeFlag) throws QMException
  {
    UniteIdentifyInfo ui=null;
    try{
      PersistService pService =(PersistService)EJBServiceHelper.getPersistService();
      if(changeFlag)
      {
        QMQuery query=new QMQuery("UniteIdentify");
        QueryCondition qc=new QueryCondition("className","=",className);
        query.addCondition(qc);
        Collection col=pService.findValueInfo(query);
        Iterator ite=col.iterator();
        while(ite.hasNext())
        {
          UniteIdentifyInfo oldui=(UniteIdentifyInfo)ite.next();
          pService.deleteValueInfo(oldui);
        }
      }
      ui=new UniteIdentifyInfo();
      ui.setClassName(className);
      ui.setBaseKey(baseKey);
      ui.setChangeFlag(changeFlag);
      ui.setSortNumber(1);
      ui=(UniteIdentifyInfo)pService.saveValueInfo(ui);
    }catch(QMException e)
    {
      e.printStackTrace();
      throw new QMException(e);
    }
    return ui;
  }
//CCBegin   SS1
  /**
	 * 获得零件版本号
	 * @param QMPartIfc partIfc
	 * @param String partVersion
	 * @throws QMException
	 */
  public static String getPartNumber(QMPartIfc partIfc,String partVersion) throws QMException{
      String partNumber = partIfc.getPartNumber();
      String partType = partIfc.getPartType().getDisplay().toString();
      String materialNumber="";
      //?	如果零部件类型属性为标准件，则物料号不加版本，规则：零部件号+路线单位简称
      //?	如果“零部件类型”属性为车型，则物料号不加版本，规则：零部件号+路线单位简称
      if((partIfc.getPartNumber().startsWith("CQ")) || (partIfc.getPartNumber().startsWith("Q")) || (partIfc.getPartNumber().startsWith("T"))||partType.equals("车型")){
      	materialNumber =  partIfc.getPartNumber();
      	return materialNumber;
      }
      //驾驶室零件号包含“5000990”、 发动机零件号包含“1000940”，则物料号不加版本，物料号不加版本，规则：零部件号+路线单位简称
      if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0){
      	materialNumber =  partIfc.getPartNumber()  ;
      	return materialNumber;
      }
      //零部件编号第一个“/”后为“*L01*”、“0”、“*0（1，2，3，4）”、、“ZBT”、“*(L)”、“AH”、“*J0*”、
      //“*J1*”、“*-SF”、“BQ”和“*-ZC”的不加版本，规则：零部件号+路线单位简称 。
      if(partNumber.indexOf("/")>=0){
      	int a = partNumber.indexOf("/");
      	//System.out.println("a="+a);
      	String temp = partNumber.substring(a);
      	//System.out.println("temp="+temp);
      	//完全匹配型a
      	String[] array1 = {"0","ZBT","AH","BQ"};
      	//在中间型×a×
      	String[] array2 = {"L01","J0","J1"};
      	//在结尾a×
      	String[] array3 = {"0","1","2","3","4","(L)","-SF","-ZC"};
      	//完全匹配型a
      	for (int i1 = 0; i1 < array1.length; i1++){
      		String str = array1[i1];
      		if(str.equals(temp)){
      			materialNumber =  partIfc.getPartNumber()  ;
      			return materialNumber;
      		}
      	}
      	//在中间型×a×
      	for (int i1 = 0; i1 < array2.length; i1++){
      		String str = array2[i1];
      		if(temp.indexOf(str)>=0){
      			materialNumber =  partIfc.getPartNumber()  ;
      			return materialNumber;
      		}
      	}
      	//在结尾a×
      	for (int i1 = 0; i1 < array3.length; i1++){
      		String str = array3[i1];
      		if(temp.endsWith(str)){
      			materialNumber =  partIfc.getPartNumber() ;
      			return materialNumber;
      		}
      	}
      }
      return    partIfc.getPartNumber() + "/" + partVersion  ;
      }
  //CCEnd SS1
    
}
