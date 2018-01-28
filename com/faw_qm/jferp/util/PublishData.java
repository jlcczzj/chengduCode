
package com.faw_qm.jferp.util;
//SS1 ��ȡbom���������ͳ��ܣ�������ԭ���ĵ���bom����Ϊ�༶bom ������ 2015-03-07

import java.text.SimpleDateFormat;
import java.util.*;

import com.faw_qm.consadoptnotice.model.UniteIdentifyInfo;
import com.faw_qm.jferp.ejb.service.IntePackService;
import com.faw_qm.jferp.ejb.service.MaterialSplitService;
import com.faw_qm.jferp.model.IntePackIfc;
import com.faw_qm.jferp.model.IntePackInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.gybomNotice.ejb.service.GYBomNoticeService;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticePartLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.workflow.definer.ejb.service.WfDefinerService;
import com.faw_qm.workflow.definer.model.WfProcessTemplateIfc;
import com.faw_qm.workflow.definer.model.WfProcessTemplateMasterIfc;
import com.faw_qm.workflow.engine.ejb.entity.WfProcess;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.workflow.engine.model.WfProcessInfo;
import com.jf.util.jfuputil;

import java.util.Vector;

/**
 * ����·����Ϣ�����ࡣ
 * @author ������
 * @version 1.0
 */ 
public class PublishData
{
     

    /**
     * ȱʡ���캯����
     */
    public PublishData()
    {
        super();
    }
    /**
	 * ��׼֪ͨ����ERP����
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public void JFRouteForERP(BaseValueIfc base) throws Exception {
  	    try {
  	    	System.out.println("��erp��������=======");
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
	 * ��׼֪ͨ����ERP����
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public void JFRouteForERP1(String baseid) throws Exception {
  	    try {
  	    	System.out.println("��erp����route����=======");
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
	 * BOM��ERP����
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public static void JFPartForERP(BaseValueIfc base) throws Exception {
  	    try {
  	    	HashMap scbbMap = new HashMap();
  	    	System.out.println("��erp��������======="+base);
  	    	PartHelper helper = new PartHelper();
  	    	GYBomNoticeService bomservice = (GYBomNoticeService)EJBServiceHelper.getService("GYBomNoticeService");
  	    	PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
  	    	//WfProcessAlert alert = new WfProcessAlert();
  	    	GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)base;
  	    	//��ȡ���������ͣ���������������������ȡ���͵ĵ���bom������ǳ��ܼ�ʻ�ң����ȡ��������ĵ���bom
  	    	//String fblx = list.getPublishType();
  	    	Collection vec = null;
  	    	Vector coll = new Vector();
//  	    CCBegin SS1
  	    	//if(fblx.indexOf("����")>-1){
//  	    CCEnd SS1
//  	    	��ȡ�������
  	  	    	String partID = list.getTopPart();
  	  	    	QMPartIfc partIfc = (QMPartIfc) pservice.refreshInfo(partID);
  	  	    	//CCBegin SS1
  	  	        vec = bomservice.getReleaseBom(partIfc);
  	  	        //CCEnd SS1
//  	  	    ���복�����
  		    	coll.add(partIfc);
  		    	String cxVerion = helper.getPartVersion(partIfc);
  		    	String partnumber = getPartNumber(partIfc,cxVerion);
  		    	Object[] object = new Object[11];
  		    	object[0] = partIfc;
  		    	object[1] = "";
  		    	object[2] = "";
  		    	object[3] = "";
  		    	object[4] = "0";
  		    	object[5] = "top";
  		    	object[6] = "";
  		    	object[7] = "";
  		    	object[8] = partnumber;
  		    	object[9] = "";
  		    	vec.add(object);
  		    	scbbMap.put(partIfc.getPartNumber(), cxVerion);
//  	  	    CCBegin SS1
//  	    	}else{
//  	    		String noticeID = list.getBsoID();
//  	    		QMQuery query = new QMQuery("GYBomAdoptNoticePartLink");
//	  	  		QueryCondition cond = new QueryCondition("noticeID", "=", noticeID);
//	  	  		query.addCondition(cond);
//	  	  		query.addAND();
//	  	  		QueryCondition cond1 = new QueryCondition("adoptBs", "=", "����");
//	  	  		query.addCondition(cond1);
//	  	  	    Collection c = pservice.findValueInfo(query);
//		  	  	for(Iterator iter = c.iterator(); iter.hasNext();)
//				{
//			  	  	 GYBomAdoptNoticePartLinkInfo linkInfo = (GYBomAdoptNoticePartLinkInfo)iter.next();
//			  	  	 String partID =linkInfo.getPartID();
//			  	  	 QMPartIfc partIfc = (QMPartIfc) pservice.refreshInfo(partID);
//			  	   	 Collection v = bomservice.getReleaseBomDJ(partIfc);
//			  	   	 if(v!=null)
//			  	  	 vec.addAll(v);
////			 	    ���복�����
//				    	coll.add(partIfc);
//				    	String cxVerion = helper.getPartVersion(partIfc);
//				    	Object[] obj = new Object[11];
//				    	obj[0] = partIfc;
//				    	obj[1] = "";
//				    	obj[2] = "";
//				    	obj[3] = "";
//				    	obj[4] = "0";
//				    	obj[5] = "top";
//				    	obj[6] = "";
//				    	obj[7] = "";
//				    	obj[8] = partIfc.getPartNumber()+"/"+cxVerion;
//				    	obj[9] = "";
//				    	vec.add(obj);
//				    	scbbMap.put(partIfc.getPartNumber(), cxVerion);
//				}
//	  
//  	    	}
//  		    CCEnd SS1
  	    	if(vec==null||vec.size()==0){
  	    		System.out.println("����bom����Ϊ�գ��޷���erp����");
  	    		return;
  	    	}
  	  	Iterator iter = vec.iterator();
	    	
	    	while(iter.hasNext()){
	    		Object[] obj = new Object[5];
	    		obj = (Object[])iter.next();
	    		QMPartIfc partIfc1 = (QMPartIfc)obj[0];
	    		String scbb = (String) obj[8];
	    		//System.out.println("partIfc11111111111="+partIfc1);
	    		String bb[]=scbb.split("/");
	    		if(bb.length>1){
	    			scbb = bb[bb.length-1];
	    		}else{
	    			scbb=partIfc1.getVersionID();
	    		}
	    		scbbMap.put(partIfc1.getPartNumber(), scbb);
	    		coll.add(partIfc1);
	    	}

//	    	System.out.println("scbbMap11111111111="+scbbMap);
//	    	System.out.println("coll11111111111="+coll);
	    	PublishData data = new PublishData();
  	    	data.publishPartsToERP(coll,list.getBsoID(),scbbMap,vec);
  	    } 
  	    catch (Exception e) {
  	      e.printStackTrace();
  	      throw e;
  	    }
  	  }
    
    /**
	 * BOM��ERP����������
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public static void JFPartForERP1(String list1) throws Exception {
  	    try {
  	    	HashMap scbbMap = new HashMap();
  	    	PartHelper helper = new PartHelper();
  	    	System.out.println("��erp�������ݹ���bom=======");
  	    	GYBomNoticeService bomservice = (GYBomNoticeService)EJBServiceHelper.getService("GYBomNoticeService");
  	    	PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
  	    	GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)pservice.refreshInfo(list1);

  	    //	String partID = list.getTopPart();

  	  	//��ȡ���������ͣ���������������������ȡ���͵ĵ���bom������ǳ��ܼ�ʻ�ң����ȡ��������ĵ���bom
  	    	
  	    	String fblx = list.getPublishType();
  	    	Vector vec = new Vector();
  	    	Vector coll = new Vector();
  	    	if(fblx.indexOf("����")>-1){
//  	    	��ȡ�������
  	  	    	String partID = list.getTopPart();
  	  	    	QMPartIfc partIfc = (QMPartIfc) pservice.refreshInfo(partID);
  	  	        vec = (Vector)bomservice.getReleaseBomDJ(partIfc);
//  	  	    ���복�����
  		    	coll.add(partIfc);
  		    	String cxVerion = helper.getPartVersion(partIfc);
  		    	String partnumber = getPartNumber(partIfc,cxVerion);
  		    	Object[] obj = new Object[11];
  		    	obj[0] = partIfc;
  		    	obj[1] = "";
  		    	obj[2] = "";
  		    	obj[3] = "";
  		    	obj[4] = "0";
  		    	obj[5] = "top";
  		    	obj[6] = "";
  		    	obj[7] = "";
  		    	obj[8] = partnumber;
  		    	obj[9] = "";
  		    	vec.add(obj);
  		    	scbbMap.put(partIfc.getPartNumber(), cxVerion);
  	    	}else{
  	    		String noticeID = list.getBsoID();
  	    		QMQuery query = new QMQuery("GYBomAdoptNoticePartLink");
	  	  		QueryCondition cond = new QueryCondition("noticeID", "=", noticeID);
	  	  		query.addCondition(cond);
	  	  		query.addAND();
	  	  		QueryCondition cond1 = new QueryCondition("adoptBs", "=", "����");
	  	  		query.addCondition(cond1);
	  	  	    Collection c = pservice.findValueInfo(query);
		  	  	for(Iterator iter = c.iterator(); iter.hasNext();)
				{
			  	  	 GYBomAdoptNoticePartLinkInfo linkInfo = (GYBomAdoptNoticePartLinkInfo)iter.next();
			  	  	 String partID =linkInfo.getPartID();
			  	  	 QMPartIfc partIfc = (QMPartIfc) pservice.refreshInfo(partID);
			  	   	 Collection v = bomservice.getReleaseBomDJ(partIfc);
			  	   	 if(v!=null)
			  	  	 vec.addAll(v);
//			 	    ���복�����
				    	coll.add(partIfc);
				    	String cxVerion = helper.getPartVersion(partIfc);
				    	String partnumber = getPartNumber(partIfc,cxVerion);
				    	Object[] obj = new Object[11];
				    	obj[0] = partIfc;
				    	obj[1] = "";
				    	obj[2] = "";
				    	obj[3] = "";
				    	obj[4] = "0";
				    	obj[5] = "top";
				    	obj[6] = "";
				    	obj[7] = "";
				    	obj[8] = partnumber;
				    	obj[9] = "";
				    	vec.add(obj);
				    	scbbMap.put(partIfc.getPartNumber(), cxVerion);
				}
	  
  	    	}
  	    	
  	    	if(vec==null||vec.size()==0){
  	    		System.out.println("����bom����Ϊ�գ��޷���erp����");
  	    		return;
  	    	}
  	  	Iterator iter = vec.iterator();
	    	
	    	while(iter.hasNext()){
	    		Object[] obj = new Object[5];
	    		obj = (Object[])iter.next();
	    		QMPartIfc partIfc1 = (QMPartIfc)obj[0];
	    		String scbb = (String) obj[8];
	    		//System.out.println("partIfc11111111111="+partIfc1);
	    		String bb[]=scbb.split("/");
	    		if(bb.length>1){
	    			scbb = bb[bb.length-1];
	    		}else{
	    			scbb=partIfc1.getVersionID();
	    		}
	    		scbbMap.put(partIfc1.getPartNumber(), scbb);
	    		coll.add(partIfc1);
	    	}

  	    	PublishData data = new PublishData();
  	    	
  	  	    data.publishPartsToERP(coll,list.getBsoID(),scbbMap,vec);
  	    } 
  	    catch (Exception e) {
  	      e.printStackTrace();
  	      throw e;
  	    }
  	  }
   
    /**
	 * �������ERP����
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public static void JFvirtualPartForERP(Collection coll) throws QMException {
  	    try {
  	     
  	    	System.out.println("�������erp��������======="+coll);
  	    	MaterialSplitService materialservice = (MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
  	    	materialservice.publishvirtualPartsToERP(coll);
  	    	
  	    } 
  	    catch (Exception e) {
  	      e.printStackTrace();
	  	   
  	    }

  	  }
   
  	 
    public void publishPartsToERPlc(Collection coll,String routeID)
        throws Exception
    {
    	MaterialSplitService materialservice = (MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
    	materialservice.publishPartsToERPlc( coll, routeID);
     
    }
    public void publishPartsToERP(Collection coll ,String id ,HashMap scbbMap,Collection vec )
    throws Exception
{
	MaterialSplitService materialservice = (MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");;
	materialservice.publishPartsToERP(coll, id,scbbMap,vec);
 
}


    //CCBegin by chudaming 20100520Ϊ�����Զ��������
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
	 * �������汾��
	 * @param QMPartIfc partIfc
	 * @param String partVersion
	 * @throws QMException
	 */
  public static String getPartNumber(QMPartIfc partIfc,String partVersion) throws QMException{
      String partNumber = partIfc.getPartNumber();
      String partType = partIfc.getPartType().getDisplay().toString();
      String materialNumber="";
      //?	����㲿����������Ϊ��׼���������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
      //?	������㲿�����͡�����Ϊ���ͣ������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
      if((partIfc.getPartNumber().startsWith("CQ")) || (partIfc.getPartNumber().startsWith("Q")) || (partIfc.getPartNumber().startsWith("T"))||partType.equals("����")){
      	materialNumber =  partIfc.getPartNumber();
      	return materialNumber;
      }
      //��ʻ������Ű�����5000990���� ����������Ű�����1000940���������ϺŲ��Ӱ汾�����ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
      if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0){
      	materialNumber =  partIfc.getPartNumber()  ;
      	return materialNumber;
      }
      //�㲿����ŵ�һ����/����Ϊ��*L01*������0������*0��1��2��3��4����������ZBT������*(L)������AH������*J0*����
      //��*J1*������*-SF������BQ���͡�*-ZC���Ĳ��Ӱ汾�������㲿����+·�ߵ�λ��� ��
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
      			materialNumber =  partIfc.getPartNumber()  ;
      			return materialNumber;
      		}
      	}
      	//���м��͡�a��
      	for (int i1 = 0; i1 < array2.length; i1++){
      		String str = array2[i1];
      		if(temp.indexOf(str)>=0){
      			materialNumber =  partIfc.getPartNumber()  ;
      			return materialNumber;
      		}
      	}
      	//�ڽ�βa��
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
