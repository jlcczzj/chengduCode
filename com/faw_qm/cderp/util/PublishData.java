
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
    public void CDRouteForERP(BaseValueIfc base) throws Exception {
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
    public void CDRouteForERP1(String baseid) throws Exception {
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
    public static void CDPartForERP(BaseValueIfc base) throws Exception {
  	    try {
  	    	//HashMap scbbMap = new HashMap(); 
  	    	System.out.println("��erp����BOM����======="+base);
  	    	PartHelper helper = new PartHelper();
  	    	//������Ҫ�����滻�ĳ��ͼ���
  	    	Vector cxvec = new Vector();
  	    	
  	    	PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
  
  	    	GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)base;
  	    
//  	    	Collection vec = null;
//  	    	Vector coll = new Vector();

//  	    	��ȡ�������
  	    	String partID = list.getTopPart();
  	    	cxvec = helper.getBatchUpdateCM(partID,"W34");
  	    	//�Գ�����ÿһ������ѭ��
  	    	for(int i=0;i<cxvec.size();i++){
  	    		Collection vec = null;
  	    		//QMPartIfc partIfc = (QMPartIfc) pservice.refreshInfo(partID);
  	    		QMPartIfc partIfc = (QMPartIfc)cxvec.elementAt(i);
  	    		//System.out.println("partIfc======="+partIfc.getPartNumber());
  	  	        vec = helper.getReleaseBom(partIfc,"W34");

  	  	         //System.out.println("vecll11111111111="+vec.size());
            
////  	  	    ���복�����
//  	  	        if(vec.size()!=0){
//  	  	        	coll.add(partIfc);
//  	  	        }
  		    	
  		 
  		    	//scbbMap.put(partIfc.getPartNumber(), cxVerion);
	  	    	if(vec==null||vec.size()==0){
	  	    		System.out.println(partIfc.getPartNumber()+"����bom����Ϊ�գ��޷���erp����");
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
	 * BOM��ERP����������
	 * @param base BaseValueIfc
	 * @throws QMException
	 */
    public static void CDPartForERP1(String list1) throws Exception {
  	    try {
  	    	HashMap scbbMap = new HashMap();
  	    	PartHelper helper = new PartHelper();
  	  	//������Ҫ�����滻�ĳ��ͼ���
  	    	Vector cxvec = new Vector();
  	    	System.out.println("��erp�������ݹ���bom=======");
  	    	GYBomNoticeService bomservice = (GYBomNoticeService)EJBServiceHelper.getService("GYBomNoticeService");
  	    	PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
  	    	GYBomAdoptNoticeInfo base = (GYBomAdoptNoticeInfo)pservice.refreshInfo(list1);

//  	    HashMap scbbMap = new HashMap();
  	    	System.out.println("��erp����BOM����======="+base);
  	    	
  	   
  
  	    	GYBomAdoptNoticeInfo list = (GYBomAdoptNoticeInfo)base;
  	    
  	    	
  	    	

//  	    	��ȡ�������
  	  	    String partID = list.getTopPart();
  	  		cxvec = helper.getBatchUpdateCM(partID,"W34");
  	    	//�Գ�����ÿһ������ѭ��
  	    	for(int i=0;i<cxvec.size();i++){
  	    		Collection vec = null;
  	    		//QMPartIfc partIfc = (QMPartIfc) pservice.refreshInfo(partID);
  	    		QMPartIfc partIfc = (QMPartIfc)cxvec.elementAt(i);
  	    		System.out.println("partIfc======="+partIfc.getPartNumber());
  	  	        vec = helper.getReleaseBom(partIfc,"W34");

  	  	         System.out.println("vecll11111111111="+vec.size());
            
////  	  	    ���복�����
//  	  	        if(vec.size()!=0){
//  	  	        	coll.add(partIfc);
//  	  	        }
  		    	
  		 
  		    	//scbbMap.put(partIfc.getPartNumber(), cxVerion);
	  	    	if(vec==null||vec.size()==0){
	  	    		System.out.println(partIfc.getPartNumber()+"����bom����Ϊ�գ��޷���erp����");
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
	 * ·����ERP����
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
