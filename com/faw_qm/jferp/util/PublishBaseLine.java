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
 *将erp回传数据加入现生产基线。
 * @author 刘家坤
 * @version 1.0
 */ 
public class PublishBaseLine {
	  private PersistService ps = null;
	  private BaselineService bs = null;
	  private StandardPartService sp = null;
	  private ExtendedPartService ep = null;
	  private static final String BASELINE_LOCATION = "\\Root\\System"; //默认基线资料夹
	  private static final String BASELINE_LIFECYCLE = "datapublish"; //默认基线生命周期
	/**
	 * 缺省构造函数。
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
		//0、带版本的编号1、版本2、时间
		for (int i = 0; i < vector.size(); i++) {
			String[] arr = (String[])vector.elementAt(i);
			 partNumber  = (String) arr[0];
			 partVersion = (String) arr[1];
			 datetime    = (String) arr[2];
			 QMPartIfc part = getPartByNumber(partNumber,partVersion);
			 if(part!=null){
				 putPartToBaseLine(part,"现生产版本基线");
			 } 
		 }
		}
		
	}
	 /**
	   * 根据编号版本获得零部件
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
	   * 将零件加入到已知基线中
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
	     // System.out.println("将整个车型添加基线中的时间:" + (b - a));
	    }
	    catch (QMException e) {
	      e.printStackTrace();
	    }
	  }

	  /**
	   * 根据配置规范将零件及零件的所有子件添加到指定的基线中
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
	    	//  System.out.println("元基线中不存在111:"+partInfo.getPartNumber() );
	        a_BaselineLinkIfc = new BaselineLinkInfo();
	        a_BaselineLinkIfc.setLeftBsoID(partInfo.getBsoID());
	        a_BaselineLinkIfc.setRightBsoID(baselineID);
	        ps.saveValueInfo(a_BaselineLinkIfc);
	     
	      }
	      else
	        {
	    	 // System.out.println("元基线中存在222222:"+partInfo.getPartNumber()  );
	            //当a_BaselineLinkIfc不为空
	              //旧的part的id
	                String replaceItemID = a_BaselineLinkIfc.getLeftBsoID();
	             //   String oldBasePartID =  a_BaselineLinkIfc.getRightBsoID();
	               

	                //比较两个字符串的值是否相同：
	                //a_BaselineLinkIfc不为空且两个字符串不相同时。
	                if (!replaceItemID.equals(partInfo.getBsoID()))
	                {
	                	//如果版本不一致，那么比较版本，保留高版本
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
	   * 通过基线的名字得到基线
	   * 如果没有则创建新基线
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
	      //  System.out.println("没有已知基线");
	        return createBaseLine(name, num);
	      }
	      else {
	    	  //System.out.println("有基线");
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
	   * 创建基准线
	   * @param baselineName String 基线的名称
	   * @param baselineNum String 基线的编号
	   * @throws QMException
	   * @return ManagedBaselineIfc
	   */
	  private ManagedBaselineIfc createBaseLine(String baselineName,
	                                            String baselineNum) throws
	      QMException {
	    //声明一个基准线对象
	    ManagedBaselineIfc managedBaseline = new ManagedBaselineInfo();
	    managedBaseline.setBaselineName(baselineName);
	    //设置基准线位置
	    String location = BASELINE_LOCATION;
	    managedBaseline.setLocation(location);
	    //设置基准线说明
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
