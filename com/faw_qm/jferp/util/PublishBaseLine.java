package com.faw_qm.jferp.util;

import java.util.*;

import com.faw_qm.baseline.ejb.service.BaselineService;
import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.baseline.model.BaselineLinkIfc;
import com.faw_qm.baseline.model.BaselineLinkInfo;
import com.faw_qm.baseline.model.BaselineableIfc;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.baseline.model.ManagedBaselineInfo;
import com.faw_qm.domain.ejb.service.DomainService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.part.ejb.extendedService.ExtendedPartService;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
/**
 *��erp�ش����ݼ������������ߡ�
 * @author ������
 * @version 1.0
 */ 
public class PublishBaseLine {
	  private PersistService ps = null;
	  private BaselineService bs = null;
	  private StandardPartService sp = null;
	  private ExtendedPartService ep = null;
	  private static final String BASELINE_LOCATION = "\\Root\\System"; //Ĭ�ϻ������ϼ�
	  private static final String BASELINE_LIFECYCLE = "datapublish"; //Ĭ�ϻ�����������
	/**
	 * ȱʡ���캯����
	 */
	public PublishBaseLine()
	{
	    super();
	}
	
	public void putBaseLine(){
		Vector vector = new Vector();
		PublishReadXML readXml = new PublishReadXML();
		vector = readXml.readXml();
		String partNumber = "";
		String partVersion = "";
		String datetime = "";
		if(vector.size()>0){
		//0�����汾�ı��1���汾2��ʱ��
		for (int i = 0; i < vector.size(); i++) {
			String[] arr = (String[])vector.elementAt(i);
			 partNumber  = (String) arr[0];
			 partVersion = (String) arr[1];
			 datetime    = (String) arr[2];
			 QMPartIfc part = getPartByNumber(partNumber,partVersion);
			 if(part!=null){
				 putPartToBaseLine(part,"�������汾����");
			 } 
		 }
		}
		
	}
	 /**
	   * ���ݱ�Ű汾����㲿��
	   * @param baseline ManagedBaselineIfc
	   */
	  public QMPartIfc getPartByNumber(String partNumber,String partVersion) {
		 QMPartIfc part = null;
		// System.out.println("partNumber="+partNumber+"--------partVersion="+partVersion);
		    try {
		    	QMQuery query = new QMQuery("QMPart");
		 		int j = query.appendBso("QMPartMaster", false);
		    	 QueryCondition qc1 = new QueryCondition("partNumber", "=", partNumber);
		    	 QueryCondition qc2 = new QueryCondition("masterBsoID", "bsoID");
		    	 QueryCondition qc3 = new QueryCondition("versionID","=", partVersion);
		    	 QueryCondition qc4 = new QueryCondition("iterationIfLatest",true);
		    	 query.addCondition(j, qc1);
		    	 query.addAND();
		    	 query.addCondition(0,j, qc2);
		    	 query.addAND();
		    	 query.addCondition(0, qc3);
		    	 query.addAND();
		    	 query.addCondition(0, qc4);
		    	 if (ps == null) {
		   	      ps = (PersistService) EJBServiceHelper.getService(
		   	          "PersistService");
		   	    }
		    	// System.out.println("query="+query.getDebugSQL());
		    	 Collection coll =  ps.findValueInfo(query);
		    	// System.out.println("coll="+coll);
		    	  if (coll != null && coll.size() > 0) {
		    		  part = (QMPartIfc) coll.iterator().next();
		    		  System.out.println("part="+part);
		  		}
		    }
	    	 catch (QMException e) {
	   	      e.printStackTrace();
	   	    }
	    	 return part;
	  }

	 /**
	   * ��������뵽��֪������
	   * @param baseline ManagedBaselineIfc
	   */
	  public void putPartToBaseLine(BaseValueIfc value,String baselineName ) {
	    try {
	      long a = System.currentTimeMillis();
	      QMPartIfc partInfo = (QMPartIfc) value;
	      String baselineNum = "1";
	      BaselineService bs = (BaselineService) EJBServiceHelper.getService(
	            "BaselineService");
	      if (sp == null) {
	    	  sp = (StandardPartService) EJBServiceHelper.getService(
	            "StandardPartService");
	      }
	      ManagedBaselineIfc pdmBaseline = getBaselineByName(baselineName,
	          baselineNum);
	     
	      if (ep == null) {
	        ep = (ExtendedPartService) EJBServiceHelper.getService(
	            "ExtendedPartService");
	      }
	     
	      populateBaseline(partInfo, pdmBaseline);
	  
	      long b = System.currentTimeMillis();
	     // System.out.println("������������ӻ����е�ʱ��:" + (b - a));
	    }
	    catch (QMException e) {
	      e.printStackTrace();
	    }
	  }

	  /**
	   * �������ù淶�����������������Ӽ���ӵ�ָ���Ļ�����
	   * @param name String
	   * @param num String
	   * @return ManagedBaselineIfc
	   */
	  public void populateBaseline(QMPartIfc partInfo,
	                               ManagedBaselineIfc pdmBaseline
	                               ) throws QMException {
	    if (sp == null) {
	      sp = (StandardPartService) EJBServiceHelper.getService(
	          "StandardPartService");
	    }
	    if (ps == null) {
	      ps = (PersistService) EJBServiceHelper.getService(
	          "PersistService");
	    }

	    BaselineService bService = (BaselineService) EJBServiceHelper.getService(
	        "BaselineService");

	    String baselineID=pdmBaseline.getBsoID();

	      BaselineLinkInfo a_BaselineLinkIfc = (BaselineLinkInfo)
	          findAnyIteration(partInfo, pdmBaseline);
	      if (a_BaselineLinkIfc == null) {
	    	//  System.out.println("Ԫ�����в�����111:"+partInfo.getPartNumber() );
	        a_BaselineLinkIfc = new BaselineLinkInfo();
	        a_BaselineLinkIfc.setLeftBsoID(partInfo.getBsoID());
	        a_BaselineLinkIfc.setRightBsoID(baselineID);
	        ps.saveValueInfo(a_BaselineLinkIfc);
	     
	      }
	      else
	        {
	    	 // System.out.println("Ԫ�����д���222222:"+partInfo.getPartNumber()  );
	            //��a_BaselineLinkIfc��Ϊ��
	              //�ɵ�part��id
	                String replaceItemID = a_BaselineLinkIfc.getLeftBsoID();
	             //   String oldBasePartID =  a_BaselineLinkIfc.getRightBsoID();
	               

	                //�Ƚ������ַ�����ֵ�Ƿ���ͬ��
	                //a_BaselineLinkIfc��Ϊ���������ַ�������ͬʱ��
	                if (!replaceItemID.equals(partInfo.getBsoID()))
	                {
	                	//����汾��һ�£���ô�Ƚϰ汾�������߰汾
	                	 QMPartIfc oldpart = (QMPartIfc)ps.refreshInfo(replaceItemID);
	                	 String newVersion = partInfo.getVersionID();
	                	 String oldVersion = oldpart.getVersionValue();
	                	 if(newVersion.compareTo(oldVersion)>0){
	                		  a_BaselineLinkIfc.setLeftBsoID(partInfo.getBsoID());
	  	                    ps.saveValueInfo(a_BaselineLinkIfc);
	                	 }
	                  
	                }
	     
	        }

	    
	  }
	  private BaselineLinkIfc findAnyIteration(BaselineableIfc a_BaselineableIfc,
              BaselineIfc a_BaselineIfc) throws
			QMException {
			QMQuery qmquery = new QMQuery("BaselineLink", "QMPart");
			
			String masterID = a_BaselineableIfc.getMaster().getBsoID();
			QueryCondition condition = new QueryCondition("masterBsoID", "=",
			                     masterID);
			qmquery.addCondition(1, condition);
			qmquery.addAND();
			QueryCondition condition1 = new QueryCondition("bsoID", "leftBsoID");
			qmquery.addCondition(1, 0, condition1);
			qmquery.addAND();
			String a_BsoID = a_BaselineIfc.getBsoID();
			QueryCondition condition2 = new QueryCondition("rightBsoID", "=",
			a_BsoID);
			qmquery.addCondition(0, condition2);
			qmquery.setVisiableResult(1);
			qmquery.setChildQuery(false);
			PersistService pservice = (PersistService) EJBServiceHelper
			.getService("PersistService");
			Collection aCollection = pservice.findValueInfo(qmquery, false);
			Iterator result = aCollection.iterator();
			return result.hasNext() ? (BaselineLinkIfc) result.next() : null;

}
	  /**
	   * ͨ�����ߵ����ֵõ�����
	   * ���û���򴴽��»���
	   * @param name String
	   * @param num String
	   * @return ManagedBaselineIfc
	   */
	  private ManagedBaselineIfc getBaselineByName(String name, String num) {
	    try {
	      QMQuery query = new QMQuery("ManagedBaseline");
	      QueryCondition con = new QueryCondition("baselineName", "=", name);
	      query.addCondition(con);
	      QueryCondition con1 = new QueryCondition("baselineDescription", "=", num);
	      query.addAND();
	      query.addCondition(con1);
	      
	      PersistService   ps = (PersistService) EJBServiceHelper.getService(
	            "PersistService");
	      
	      Collection col = ps.findValueInfo(query, false);
	      if (col == null || (col != null && col.size() == 0)) {
	      //  System.out.println("û����֪����");
	        return createBaseLine(name, num);
	      }
	      else {
	    	  //System.out.println("�л���");
	        for (Iterator i = col.iterator(); i.hasNext(); ) {
	          ManagedBaselineIfc line = (ManagedBaselineIfc) i.next();
	          return line;
	        }
	      }
	    }
	    catch (QMException e) {
	      e.printStackTrace();
	    }
	    return null;
	  }
	  /**
	   * ������׼��
	   * @param baselineName String ���ߵ�����
	   * @param baselineNum String ���ߵı��
	   * @throws QMException
	   * @return ManagedBaselineIfc
	   */
	  private ManagedBaselineIfc createBaseLine(String baselineName,
	                                            String baselineNum) throws
	      QMException {
	    //����һ����׼�߶���
	    ManagedBaselineIfc managedBaseline = new ManagedBaselineInfo();
	    managedBaseline.setBaselineName(baselineName);
	    //���û�׼��λ��
	    String location = BASELINE_LOCATION;
	    managedBaseline.setLocation(location);
	    //���û�׼��˵��
	    String description = baselineNum;
	    managedBaseline.setBaselineDescription(description);
	    DomainService domain = (DomainService) EJBServiceHelper.getService(
	        "DomainService");
	    String domainID = domain.getDomainID("System");
	    managedBaseline.setDomain(domainID);
//	    String lifecycle = BASELINE_LIFECYCLE;
//	    String lifeCyID = getLifeCyID(lifecycle);
//	    managedBaseline.setLifeCycleTemplate(lifeCyID);
	    if (ep == null) {
	      ep = (ExtendedPartService) EJBServiceHelper.getService(
	          "ExtendedPartService");
	    }
	    managedBaseline = (ManagedBaselineIfc) ep.saveBaseline(managedBaseline);

	    return managedBaseline;
	  }
	 

}
