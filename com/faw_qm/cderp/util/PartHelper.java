/**
 * 生成程序 PartHelper.java    1.0    2013/06/05
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 bom存储结构发生变化，由一车一个bom改为结构公用 刘家坤 2017-4-5
 * SS2 批量变更查找替换件 2017-4-10
 * SS3 成都存在虚拟件转实件情况，原来是焊到焊改为长春调件到焊 2017-12-17
 */
package com.faw_qm.cderp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.cderp.exception.ERPException;
import com.faw_qm.cderp.exception.QMXMLException;
import com.faw_qm.cderp.model.MaterialSplitIfc;
import com.faw_qm.domain.util.DomainHelper;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.gybom.ejb.service.GYBomService;
import com.faw_qm.gybom.util.GYBomHelper;

import com.faw_qm.iba.value.model.StringValueIfc;

import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.queue.exception.QueueException;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;


  
/**
* <p>向ERP发数据用工具类</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2013</p>
* <p>Company: 一汽启明</p>
* @author 刘家坤
* @version 1.0
*/
public class PartHelper
{
	 private static final String RESOURCE = "com.faw_qm.cderp.util.ERPResource";
	 PartConfigSpecInfo partConfigSpecIfc = null;
	 String[] State={"生产准备"};
    public PartHelper()
    {
    }
  
    /**
     * 返回指定的关联（master与iteration之间）集合中每个关联对象连接的
     * mastered对象（指定角色master）的结果集
     * @param links 关联类值对象集合
     * @param role 角色名
     * @exception com.faw_qm.config.exception.ConfigException
     * @return 对应关联类值对象的Mastered对象集合。
     */
    /**
     * 返回指定的关联（master与iteration之间）集合中每个关联对象连接的
     * mastered对象（指定角色master）的结果集
     * @param links 关联类值对象集合
     * @param role 角色名
     * @exception com.faw_qm.config.exception.ConfigException
     * @return 对应关联类值对象的Mastered对象集合。
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
                throw new QMException(e, "根据角色名获得其BsoID时出错");
            }
            BaseValueIfc bsoObj;

            PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
            bsoObj = (BaseValueIfc)persistService.refreshInfo(bsoID, false);

            if(!(bsoObj instanceof QMPartMasterIfc))
            {
                throw new QMException();
            }// endif 如果collection中的元素左连接对象object不是MasteredIfc的
            // 实例，抛出角色无效例外
            resultVector.addElement(bsoObj);
        }// endfor i
        return removeDuplicates(resultVector);
    }
    /**
     * 将指定的结果集中重复的元素排除。
     * @param collection 结果集
     * @return Collection 排除了重复数据的集合 Collection中每一个元素为一Object数组 该Object数组中的第0个元素为一值对象
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
            hashtable.put(objBsoID, "");// 将BsoID做为标志放入Hash表
            resultVector.addElement(obj);
        }// endfor i
        return resultVector;
    }
    public Collection filterLifeCycle(Collection al){
    	  PartHelper helper = new PartHelper();
    	  
    	  Vector temp = new Vector();
    	  try {
    		  PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
    		  Iterator iter = al.iterator();
    	  while(iter.hasNext()){
    		  String partID = (String)iter.next();
    		  QMPartIfc partIfc1 = (QMPartIfc)pservice.refreshInfo(partID);
    		  //获取制造视图最新版本
    		  PartConfigSpecIfc configSpec = PartHelper.getPartConfigSpecByViewName("工艺视图");
    		  QMPartIfc partIfc =  (QMPartInfo) PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc) partIfc1.getMaster() , configSpec);
    		  QMPartIfc part1 = helper.filterLifeState(partIfc);
    		  if(part1==null)
    			  continue;
    		 // QMPartIfc part2 = helper.publishViewPart(part1.getBsoID());
    		  temp.add(part1.getBsoID());
    	  }
    	  } catch (QMException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	  return (Collection)temp;
    	 
      }
    public Collection filterLifeCycle1(Collection al){
  	  PartHelper helper = new PartHelper();
  	  
  	  Vector temp = new Vector();
  	  try {
  		  PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
  		  Iterator iter = al.iterator();
  	  while(iter.hasNext()){
  		QMPartIfc partIfc1 = (QMPartIfc)iter.next();
  		  //获取制造视图最新版本
  		 // PartConfigSpecIfc configSpec = PartHelper.getPartConfigSpecByViewName("工艺视图");
  		//  QMPartIfc partIfc =  (QMPartInfo) PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc) partIfc1.getMaster() , configSpec);
  		 QMPartIfc partIfc = partIfc1;
  		  QMPartIfc part1 = helper.filterLifeState(partIfc);
  		  if(part1==null)
  			  continue;
  		 // QMPartIfc part2 = helper.publishViewPart(part1.getBsoID());
  		  temp.add(part1);
  	  }
  	  } catch (QMException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	  return (Collection)temp;
  	 
    }
    /**
     * 过滤生命周期状况，不满足的向前追溯
     * 并以试制以及试制以后的状态才添加为过滤条件，如果制造视图最新版本零部件状态不满足条件
     * 需追溯该件制造视图之前满足条件的版本。
     * @param vec 零部件集合 或 结构件集合
     * @return
     * 
     * Vector
     * @throws QMException 
     */
    public QMPartIfc filterLifeState(QMPartIfc part1) throws QMException
    {
        QMPartIfc part = null;
        Object[] obj;
        Vector result = new Vector();
        if(part1 instanceof QMPartIfc)
        {
        	 part = part1;
               
                String state = part.getLifeCycleState().getDisplay();
                //System.out.println("state========1"+state);
                for(int j=0;j<State.length;j++)
                {
                    if(state.equals(State[j]))
                    {                       
                    	 return part;
                    }
                }
             // 追溯,先获取制造视图最新版本，如果符合条件则取出，如果不符合条件则获取工艺视图最新件。
              //  part = getZZPartInfoByMasterBsoID(part1.getMasterBsoID());
               // System.out.println("制造视图版本 = "+part);
                if(part==null){
//                	 System.out.println("开始获取工艺视图最新版本");
//                	 System.out.println("part1 = "+part1);
//                	添加工艺视图最新版本
                    PartConfigSpecIfc configSpecGY = PartHelper.getPartConfigSpecByViewName("工艺视图");
                    part = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)part1.getMaster() , configSpecGY);
                  //  part = getPart(part);
                }
                
                //result.add(part);
             
              //  System.out.println("最终版本 = "+part);
      
        }
        return part;
    }
    /**
     * 根据生命周期状态追溯当前大版本零部件
     * @param curPart
     * @return
     * 
     * QMPartIfc
     * @throws QMException 
     */
    public QMPartIfc getPart(QMPartIfc curPart) throws QMException
    {
        String preID = curPart.getPredecessorID();
        PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
       
       // System.out.println("curPart.getPredecessorID() = "+curPart.getPredecessorID());
        if(preID==null||preID.length()==0)
        {
        	return null;
        }
        QMPartIfc prePart = (QMPartIfc)persistService.refreshInfo(preID, false);
        //CCBegin  SS2
//        if(!prePart.getBranchID().equals(curPart.getBranchID()))
//        {
//            // 最新版本
//            VersionControlService vservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
//            return (QMPartIfc)vservice.getLatestIteration(curPart);
//        }else
//        {//CCEnd SS2
            String state = prePart.getLifeCycleState().getDisplay();
            for(int j = 0;j < State.length;j++)
            {
              //  System.out.println("==================根据生命周期状态追溯当前大版本零部件 state = "+state);                
                if(state.equals(State[j]))
                {
                    return prePart;
                }
            }
           // System.out.println("==================根据生命周期状态追溯当前大版本零部件 prePart = "+prePart); 
            return getPart(prePart);
//        }
    }
    /**
    *获取制造视图最新小版本，并且生命周期状态是试制、生产、生产准备、
    *@param masterid
    *@return QMPartInfo
    *@throws QMException
    */
    public QMPartInfo getZZPartInfoByMasterBsoID(String masterid)
    		throws QMException {
    	try {
    		PersistService pService = (PersistService) EJBServiceHelper.getPersistService();
    		VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
    		Collection col = vcservice.allVersionsOf((QMPartMasterIfc) pService.refreshInfo(masterid));
    		//System.out.println("col = "+col);
    		if(col != null ){
    			Iterator iter = col.iterator();
    			if (iter.hasNext()) {
        			QMPartInfo part = (QMPartInfo) iter.next();
        			return part;
    		    }
    		
    		}
    	} catch (QMException ex) {
    		ex.printStackTrace();
    		throw ex;
    	}
    	return null;
    }
  
    /**
     * 根据输入的零部件主信息BsoID，和零部件配置规范,
     * 返回符合零部件配置规范的，受该QMPartMasterIfc管理的所有零部件的集合。
     * @param partMasterID 零部件主信息BsoID。
     * @return Vector 过滤出来的零部件值对象的集合，如果没有合格的零部件返回new Vector()。
     * @exception QMException
     */
    public static Vector filterIterations(QMPartMasterIfc partMasterIfc )
        throws QMException
    {
    	
      //第二步：零部件配置规范值对象：
      PartConfigSpecIfc configSpecIfc = PartServiceRequest.findPartConfigSpecIfc();
      //第三步：根据两个参数调用StandardPartService中的方法filterdIterationsOf方法
      Vector paraVector = new Vector();
      paraVector.addElement(partMasterIfc);
    //  System.out.println("paraVector="+paraVector);
      Collection result = PartServiceRequest.filteredIterationsOf(paraVector, getPartConfigSpecByViewName("工艺视图"));
      //System.out.println("result="+result);
      Vector resultVector = new Vector();
      if(result == null | result.size() == 0)
      {
       
        return resultVector;
      }
      else
      {
        Iterator iterator = result.iterator();
        while(iterator.hasNext())
        {
          resultVector.addElement(iterator.next());
        }
        return resultVector;
      }
    }
    /**
     * 根据视图名称返回零部件配置规范
     * @param viewName String
     * @throws QMException
     * @return PartConfigSpecIfc
     * @author liunan 2008-08-01
     */
    public static PartConfigSpecIfc getPartConfigSpecByViewName(String viewName) throws
        QMException {
      ViewService viewService = (ViewService) EJBServiceHelper.getService(
          "ViewService");
      ViewObjectIfc view = viewService.getView(viewName);
      //若根据指定的视图名称没有获取到相应的值对象则返回当前配置规范
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
     * 获取管理员密码
     */
    public String initPassword()
    {
        //管理员用户和密码设置在PasswordTable表中
    	String adminPassword = "";
        String passwordTable = RemoteProperty.getProperty(
                "com.faw_qm.queue.passwordTable", "PasswordTable");
        String sql = "Select password from " + passwordTable +
                     " where username=?";
        String adminUser = DomainHelper.ADMINISTRATOR_NAME;
        java.sql.Connection conn = null;
        ResultSet result = null;
        PreparedStatement pm = null;
        try
        {
            conn = PersistUtil.getConnection();
            pm = conn.prepareStatement(sql);
            pm.setString(1, adminUser);
            result = pm.executeQuery();
            String s = null;
            if (result.next())
            {
                s = result.getString(1);
                //if(s!=null)
                //{
                //	 s=new String(fromb64(s));
                //}
            }
            if (s == null)
            {
                throw new QueueException("2", null);
            }
            adminPassword = s;
        }
        catch (Exception se)
        {
            throw new QMRuntimeException(new QueueException(se, "4", null));
        }
        finally
        {
            try
            {
                if (result != null)
                {
                    result.close();
                }
                if (pm != null)
                {
                    pm.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
     return adminPassword;
    }
    public ManagedBaselineIfc getBaselineByName(String name) {
        try {
          QMQuery query = new QMQuery("ManagedBaseline");
          QueryCondition con = new QueryCondition("baselineName", "=", name);
          query.addCondition(con);
   
          PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
          Collection col = pservice.findValueInfo(query, false);
          Iterator i = col.iterator();
          if (i.hasNext()) {
            ManagedBaselineIfc line = (ManagedBaselineIfc) i.next();
            return line;
          }

        }
        catch (QMException e) {
          e.printStackTrace();
        }
        return null;
      }
    /**
     *比较两个版本的大小
     *@param version1
     *@param version2
     *@return QMPartInfo
     *@throws QMException
     */
     public static int compareVersion(String version1,String version2)
     		 {
//    	     System.out.println("version1="+version1);
//    	     System.out.println("version2="+version2);
            int bool = 1;
            //获得版本1和版本2的位置
            int dot1 = version1.indexOf(".");
            int dot2 = version2.indexOf(".");
            //System.out.println("dot1="+dot1+"     dot2="+dot1);
            //如果版本1和版本2都不含点
            if(dot1<0&&dot2<0){
            	// System.out.println("dot1111111111");
            	if(version1.length()==version2.length()){
   		          if(version1.compareTo(version2) < 0){
   		        	bool = 2;
   		          }else{
   		        	bool = 1;
   		          }
            	 }else if(version1.length()<version2.length()){
            		 bool = 2;
            	 }else{
            		 bool = 1;
            	 }
            }
            //如果版本1和版本2都含点
            if(dot1>=0&&dot2>=0){
            	// System.out.println("dot22222222");
            	String gyVersion1 = version1.substring(0,dot1);
            	String gyVersion2 = version2.substring(0,dot2);
//            	System.out.println("gyVersion1="+gyVersion1);
//            	System.out.println("gyVersion2="+gyVersion2);
            	//对“.”前进行比较
            	//如果点前版本不相同
            	if(!gyVersion1.equals(gyVersion2)){
            		
            		if(version1.length()==version2.length()){
	     		          if(gyVersion1.compareTo(gyVersion2) < 0){
	     		        	bool = 2;
	     		          }else{
	     		        	bool = 1;
	     		          }
	              	 }else if(gyVersion1.length()<gyVersion2.length()){
	              		 bool = 2;
	              	 }else{
	              		 bool = 1;
	              	 }
            	}//如果点前版本相同,则比较点后版本
            	else{
	            		String zzVersion1 = version1.substring(dot1+1);
	                	String zzVersion2 = version2.substring(dot2+1);
//	                	System.out.println("zzVersion1="+zzVersion1);
//	                	System.out.println("zzVersion2="+zzVersion2);
	                	if(zzVersion1.length()==zzVersion2.length()){
		     		          if(zzVersion1.compareTo(zzVersion2) < 0){
		     		        	bool = 2;
		     		          }else{
		     		        	bool = 1;
		     		          }
		              	 }else if(zzVersion1.length()<zzVersion2.length()){
		              		 bool = 2;
		              	 }else{
		              		 bool = 1;
		              	 }
            	    }
            }
            if(dot1>=0&&dot2<0){
            	bool = 1;
            }
            if(dot1<0&&dot2>=0){
               bool = 3;	
            }
     	return bool;
     }
     /**
 	 * 通过零部件编号获取最新版本的零部件。
 	 * @param partNumber partNumber
 	 * @return QMPartInfo 最新版本的零部件(QMPart);
 	 * @throws QMException
 	 * @see QMPartInfo
 	 */
 	public QMPartInfo getPartByPartNumber(String partNumber) throws QMException
 	{
         PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
 		QMQuery query = new QMQuery("QMPartMaster");
 		QueryCondition qc1 = new QueryCondition("partNumber", "=", partNumber);
 		query.addCondition(qc1);
 		QMPartInfo partInfo = null;
 		Collection collection = pservice.findValueInfo(query);
 		if (collection != null && collection.size() > 0) 
 		{
 			Iterator iterator = collection.iterator();
 			while (iterator.hasNext()) 
 			{
 				QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)iterator.next();
 		        PartServiceHelper pshelper= new PartServiceHelper();
 				partInfo = pshelper.getPartByMasterID(partMasterIfc.getBsoID());
 			}
 		}
//         PartServiceHelper pshelper= new PartServiceHelper();
// 		QMPartInfo partInfo = pshelper.getPartByMasterID(partIfc);
 		return partInfo;
 	}
	/**
	 * liujiakun 20140226
	 *  * 根据给定的事物特性持有者,获得特定iba属性值。 return String 为该iba属性的值。
	 * @param part
	 *            QMPartIfc
	 * @throws QMException
	 * @return String
	 */

	public static String getPartIBA(QMPartIfc part) throws QMException {
		String ibavalue = "";
		try {
			PersistService pService = (PersistService) EJBServiceHelper
			.getPersistService();
			QMQuery query = new QMQuery("StringValue");
			
			QueryCondition qc = new QueryCondition("iBAHolderBsoID", "=", part
					.getBsoID());
			query.addCondition(qc);
			query.addAND();
			QueryCondition qc1 = new QueryCondition("definitionBsoID","=", "StringDefinition_7646");
			query.addCondition(qc1);
			//System.out.println("getPartIBA query="+query.getDebugSQL());
			Collection col = pService.findValueInfo(query, false);
			if (col != null && col.size() > 0) {
				StringValueIfc value = (StringValueIfc) col.iterator().next();
				ibavalue = value.getValue();
				
			} 

		} catch (Exception e) {
			e.printStackTrace();
			throw new QMException(e);
		}
		
		return ibavalue;
	}
	public static String getPartVersion(QMPartIfc partIfc){
		// 技术中心源版本
		String jsbb;
		String partVersion = "";
		try {
			jsbb = getPartIBA(partIfc);
	
			//1)	零部件“技术中心源版本”属性有值，则零部件版本属性为技术中心源版本。
			//2)	零部件“技术中心源版本”属性为空，则零部件版本属性为PDM部件版本。
//			System.out.println("jsbb===="+jsbb);
//			System.out.println("partIfc===="+partIfc.getPartNumber());
			if (jsbb != null && jsbb.length() > 0) {
				int index = jsbb.indexOf(".");
				if(index>=0){
					jsbb = jsbb.substring(0,index);
				}
				partVersion = jsbb ;	
			} else {
				partVersion = partIfc.getVersionID();
				
			}
		//	System.out.println("partVersion===="+partVersion);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return partVersion;
	}
	/**
	 * 根据给定的事物特性持有者,获得特定iba属性值。 return String 为该iba属性的值。
	 * 
	 * @throws QMXMLException
	 */
	  /**
 	 * 零部件的制造路线单位中只要不包含卡车厂路线单位，“采购件标识”设置为“Y”，其他设置为“N”。
 	 * @param List routeCodeList
 	 * @return String 采购标识;
 	 * @throws QMException
 	 */
	public String getCgbs(String routeAsString){
		 String cgbs = "N";
		// System.out.println("routeAsString==============="+routeAsString);
		 if(routeAsString.equals("协")||routeAsString.equals("川生(调)")||routeAsString.equals("川生(塑)")||routeAsString.equals("川漆")||routeAsString.equals("川生")){
			 cgbs = "Y";
		 }
		 System.out.println("cgbs==============="+cgbs);
			return cgbs;
	}
	
	  /**
 	 * 判断路线中是否存在回头件
 	 * @param List routeCodeList
 	 * @return boolean;
 	 * @throws QMException
 	 */
	public boolean ifCodeHT(List routeCodeList){
		Vector vec = new Vector();
		for (int k = routeCodeList.size() - 1; k >= 0; k--) {
			String routeCode = (String) routeCodeList.get(k);
			if(vec.contains(routeCode)){
				return true;
			}else{
				vec.add(routeCode);
			}
			
		}
		return false;
	}
	  /**
 	 * 判断 是否是虚拟件
 	 * @param QMPartIfc partIfc
 	 * @return boolean;
	 * @throws QMXMLException 
 	 * @throws QMException
 	 */
	public boolean getJFVirtualIdentity(QMPartIfc partIfc,String routeAsString,String routeAssemStr,boolean islogic,boolean colorFlag) throws QMException{
		Vector vec = new Vector();
		//String xnj =getPartIBA(partIfc, "虚拟件","virtualPart");
		//System.out.println("getPartIBA xnj="+xnj);
		boolean re = false;
		//if(xnj==null||xnj.length()==0){
			String partNumber = partIfc.getPartNumber();
			String lx         = partIfc.getPartType().toString();
			//零部件是逻辑总成，并且制造路线和装配路线为空
			if(islogic&&((routeAssemStr==null)||routeAssemStr.length()==0)){
				//System.out.println("111111111==============="+lx);
				re=true;
			}
			//制造路线等于装配路线
			if(routeAsString.equals(routeAssemStr)){
				//System.out.println("2222222222===============");
				re=true;
			}
			//零件编号包含“1000940”
			if(partNumber.contains("1000940")){
				//System.out.println("333===============");
				re=true;
			}
//				是颜色件标识、且制造路线是外购件（协、川生(调)或者川生(塑)）
//			if(colorFlag&&(routeAsString.contains("协")||routeAsString.contains("川生(调)")||routeAsString.contains("川生(塑)"))){
//				//System.out.println("444===============");
//				re=true;
//			}

//			有制造路线，且装配路线为空，同时制造路线不包含协或川生(调)
			if((routeAssemStr==null||routeAssemStr.length()==0)&&(!routeAsString.contains("协"))&&(!routeAsString.contains("川生(调)"))){
				//System.out.println("555===============");
				re=true;
			}
//		}else{
//			if(xnj.equals("Y")){
//				//System.out.println("6666==============="+xnj);
//				re=true;
//			}
//			
//		}

		return re;
	}
	  /**
 	 *  物料编号生成规则
 	 * @param QMPartIfc partIfc
 	 * @param String partVersion
 	 * @param String makeCodeNameStr
 	 * @param String dashDelimiter
 	 * @return boolean;
	 * @throws QMXMLException 
 	 * @throws QMException
 	 */
	public String getMaterialNumber(QMPartIfc partIfc,String partVersion  ) throws QMException{
        String partNumber = partIfc.getPartNumber();
        String partType = partIfc.getPartType().getDisplay().toString();
        String materialNumber="";
     //   System.out.println("partNumber=="+partNumber);
        //?	如果零部件类型属性为标准件，则物料号不加版本，规则：零部件号+路线单位简称
        //?	如果“零部件类型”属性为车型，则物料号不加版本，规则：零部件号+路线单位简称
        if((partIfc.getPartNumber().startsWith("CQ")) || (partIfc.getPartNumber().startsWith("Q")) || (partIfc.getPartNumber().startsWith("T"))){
        	materialNumber =  partIfc.getPartNumber();
        	return materialNumber;
        }
        //驾驶室零件号包含“5000990”、 发动机零件号包含“1000940”，则物料号不加版本，物料号不加版本，规则：零部件号+路线单位简称
        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0||partNumber.indexOf("5000020")>=0||partNumber.indexOf("5000030")>=0){
        	materialNumber =  partIfc.getPartNumber()  ;
        	return materialNumber;
        }
        //原材料不带版本
        if(partNumber.indexOf("*")>=0||partType.equals("工艺合件")){
        	materialNumber =  partIfc.getPartNumber()  ;
        	return materialNumber;
        }
//      判断零件是否是车型
		boolean ifauto = checkAuto(partIfc);
		if(ifauto){
			
        	materialNumber =  partIfc.getPartNumber()  ;
        	return materialNumber;
        }
        //零部件编号第一个“/”后为“*L01*”、“0”、“*0（1，2，3，4）”、、“ZBT”、“*(L)”、“AH”、“*J0*”、
        //“*J1*”、“*-SF”、“BQ”和“*-ZC”的不加版本，规则：零部件号+路线单位简称 。
        if(partNumber.indexOf("/")>=0){
        	
        	int a = partNumber.indexOf("/");
        	//System.out.println("a="+a);
        	String temp = partNumber.substring(a+1);
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
//        		System.out.println("temp==="+temp);
//        		System.out.println("str==="+str);
        		if(str.equals(temp)){
        			materialNumber =  partIfc.getPartNumber()  ;
        			return materialNumber;
        		}
        	}
        	//在中间型×a×
        	for (int i1 = 0; i1 < array2.length; i1++){
        	//	System.out.println("55555555555555==");
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
        //获取零部件的“ERP回导零部件号”属性，如果该属性“/”后面的字符串含有“-”，取“-”后面的字符串，
        //取到的字符串和“L01”、“0”，“1”、“2”、“3”、“4”、“ZBT”、“L”、“AH”、“J0”、“J1”、“SF”、
        //“BQ”、“ZC”比较，如果不属于以上列出的字符串，把ERP回导零部件号“/”后版本更新为新版本作为
        //PDM零部件号，按照“零部件号+路线单位简称”规则形成物料号。如果零部件的“ERP回导零部件号”
        //属性为空，则按照规则○1形成物料编码。
        //正常的物料编码
        String str = getERPHD(partNumber+"/"+partVersion);
        if(str!=""){
        	if(partNumber.indexOf("/")>=0){
        		int a = partNumber.indexOf("/");
            	String temp = partNumber.substring(a);
            	if(temp.indexOf("-")>=0){
            		//System.out.println("7777777777777777777==");
            		String[] array1 = {"0","ZBT","AH","BQ","L01","J0","J1","0","1","2","3","4","(L)","-SF","-ZC"};
         
            		int b = partNumber.indexOf("-");
            		String temp1 = partNumber.substring(b);
            		for (int i1 = 0; i1 < array1.length; i1++){
                		String strtemp = array1[i1];
                		if(strtemp.equals(temp1)){
                			materialNumber =  partIfc.getPartNumber()  ;
                			return materialNumber;
                		}
                	}
            		//如果“/”后“-”之后字符串不再集合array1里
            		//把ERP回导零部件号“/”后版本更新为新版本作为PDM零部件号，按照“零部件号+路线单位简称”规则形成物料号
            		//零件号=原来的erp回导零件号，将原来的版本替换为最新的版本
            		String oldPartNumber = str.substring(0, a)+"/"+partVersion+"-"+temp1;
            		materialNumber =  oldPartNumber  ;
            		return materialNumber;
            	}
        	}
        }
		materialNumber =  partIfc.getPartNumber() + "/" + partVersion  ;

return materialNumber;
}
	  /**
 	 *  物料编号生成规则
 	 * @param QMPartIfc partIfc
 	 * @param String partVersion
 	 * @param String makeCodeNameStr
 	 * @param String dashDelimiter
 	 * @return boolean;
	 * @throws QMXMLException 
 	 * @throws QMException
 	 */
	public String getMaterialNumberHistory(String[] partQuan) throws QMException{
		String partNumber ="";
		String partVersionValue="";
		//零部件编号
		partNumber=partQuan[0];
		//零部件版本
		partVersionValue=partQuan[1];

		//类型
		String partType = partQuan[8];
        String materialNumber="";
        //?	如果零部件类型属性为标准件，则物料号不加版本，规则：零部件号+路线单位简称
        //?	如果“零部件类型”属性为车型，则物料号不加版本，规则：零部件号+路线单位简称
        if(partType.equals("标准件")||partType.equals("车型")){
        	materialNumber =  partNumber  ;
        	return materialNumber;
        }
        //驾驶室零件号包含“5000990”、 发动机零件号包含“1000990”，则物料号不加版本，物料号不加版本，规则：零部件号+路线单位简称
        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0){
        	materialNumber =  partNumber  ;
        	return materialNumber;
        }
        //零部件编号第一个“/”后为“*L01*”、“0”、“*0（1，2，3，4）”、、“ZBT”、“*(L)”、“AH”、“*J0*”、
        //“*J1*”、“*-SF”、“BQ”和“*-ZC”的不加版本，规则：零部件号+路线单位简称 。
        if(partNumber.indexOf("/")>=0){
        	int a = partNumber.indexOf("/");
        	String temp = partNumber.substring(a);
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
        			materialNumber =  partNumber  ;
        			return materialNumber;
        		}
        	}
        	//在中间型×a×
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(temp.indexOf(str)>=0){
        			materialNumber =  partNumber  ;
        			return materialNumber;
        		}
        	}
        	//在结尾a×
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(temp.endsWith(str)){
        			materialNumber =  partNumber ;
        			return materialNumber;
        		}
        	}
        }
        //获取零部件的“ERP回导零部件号”属性，如果该属性“/”后面的字符串含有“-”，取“-”后面的字符串，
        //取到的字符串和“L01”、“0”，“1”、“2”、“3”、“4”、“ZBT”、“L”、“AH”、“J0”、“J1”、“SF”、
        //“BQ”、“ZC”比较，如果不属于以上列出的字符串，把ERP回导零部件号“/”后版本更新为新版本作为
        //PDM零部件号，按照“零部件号+路线单位简称”规则形成物料号。如果零部件的“ERP回导零部件号”
        //属性为空，则按照规则○1形成物料编码。
        //正常的物料编码
        String str = getERPHD(partNumber+"/"+partVersionValue);
        if(str!=""){
        	if(partNumber.indexOf("/")>=0){
        		int a = partNumber.indexOf("/");
            	String temp = partNumber.substring(a);
            	if(temp.indexOf("-")>=0){
            		String[] array1 = {"0","ZBT","AH","BQ","L01","J0","J1","0","1","2","3","4","(L)","-SF","-ZC"};
         
            		int b = partNumber.indexOf("-");
            		String temp1 = partNumber.substring(b);
            		for (int i1 = 0; i1 < array1.length; i1++){
                		String strtemp = array1[i1];
                		if(strtemp.equals(temp1)){
                			materialNumber =  partNumber ;
                			return materialNumber;
                		}
                	}
            		//如果“/”后“-”之后字符串不再集合array1里
            		//把ERP回导零部件号“/”后版本更新为新版本作为PDM零部件号，按照“零部件号+路线单位简称”规则形成物料号
            		//零件号=原来的erp回导零件号，将原来的版本替换为最新的版本
            		String oldPartNumber = str.substring(0, a)+"/"+partVersionValue+"-"+temp1;
            		materialNumber =  oldPartNumber  ;
            		return materialNumber;
            	}
        	}
        }
		materialNumber =  partNumber + "/" + partVersionValue  ;

return materialNumber;
}
	//获取erp回导属性
	private String getERPHD(String partNumber){
		//Collection col = query("CDMaterialSplit","partNumber","=",partNumber);
		Collection col = null;
		PersistService pService;
		try {
//			CCBegin SS7
			pService = (PersistService)EJBServiceHelper.getService("PersistService");
//		    QMQuery query = new QMQuery("CDMaterialSplit");
//		    System.out.println("query="+query.getSQLSelf());
//			QueryCondition condition = new QueryCondition("partNumber",
//					QueryCondition.EQUAL, partNumber);
			 QMQuery query = new QMQuery("CDMaterialSplit");
			 QueryCondition qc1=new QueryCondition("partNumber","=",partNumber);
             query.addCondition(qc1);
             col=pService.findValueInfo(query);
//           CCEnd SS7
		} catch (ServiceLocatorException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (QueryException e) {  
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (QMException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		
		if(col==null)
			return "";
		  Iterator ite=col.iterator();
		  while(ite.hasNext()){
			  MaterialSplitIfc ifc=(MaterialSplitIfc)ite.next();
			  String str = ifc.getOptionCode();
			  if(str!=null&&str.length()!=0){
				  return str;
			  }
		  }
	     
	      return "";
	}
	 /**
	  * 用于query查询
	  * @param tableName
	  * return Iterator
	  */


	public static Collection query(String tableName,String attrName,String operator,String condition)
				
		{
		 Collection partCondition= null;
            PersistService pService;
			try {
				pService = (PersistService)EJBServiceHelper.getService("PersistService");
				 QMQuery query=new QMQuery(tableName);
			     QueryCondition condition1=new QueryCondition(attrName,operator,condition);
			    query.addCondition(condition1);
			     partCondition=pService.findValueInfo(query,false);
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
		    return partCondition;
		}
	/**
	 * 将零件编号拆分出来
	 * 
	 * @return 零部件编号。
	 * @throws QMException
	 */
	public String getPartNumber(String materPartNumber) throws QMException {
		int i = materPartNumber.lastIndexOf("/");
		
		if(i>0){
			String partNumber = materPartNumber.substring(0,i);
		//	System.out.println("partNumber="+partNumber);
			return partNumber;
		}
		return materPartNumber;
	}
//	CCBegin SS3
	/**
	 * 是否是逻辑总成
	 * @return boolean
	 * @throws QMException
	 */
	public boolean isLogical(QMPartIfc part,String routeAsString ,String assembStr)throws QMException{
		boolean result=false;
		//第5位为G并且装配路线为空并且制造路线含“用”
		if (part.getPartNumber().substring(4, 5).equals("G"))
		{
			if(routeAsString.contains("用")&&assembStr.equals("")){
				result=true;
				System.out.println("虚件");
			}
			if(!routeAsString.contains("用")&&!assembStr.equals("")){
				result=true;
				System.out.println("实件");
			}
		}
		return result;
	}
//	CCEnd SS3
//	CCBegin SS5
	  /**
     * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
     * @param partIfc :QMPartIfc 零部件的值对象。
     * @return collection:Collection 与partIfc关联的PartUsageLinkIfc的对象集合。
     * @throws QMException
     */
    public Collection getUsesPartMasters(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters begin ....");
        //如果条件成立，则抛出PartException"参数不能为空"
        if(partIfc == null)
            throw new PartException(RESOURCE, "CP00001", null);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters end....return is Colletion ");
        if(partIfc.getBsoName().equals("GenericPart"))
        	return pservice.navigateValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
          else 
        	  return pservice.navigateValueInfo(partIfc, "usedBy", "PartUsageLink", false);
      
    }
    /**
	 * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
	 * 
	 * @param partIfc
	 *            零部件。
	 * @throws QMException
	 *             return PartUsageLinkIfc的对象集合。
	 */
	private final List getUsageLinks(final QMPartIfc partIfc)
	throws QMException {
	
		// 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
		List usesPartList = new ArrayList();
		try {
			StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");

			GYBomHelper helper = new GYBomHelper();
		//	String strList=helper.getGYBomList();
//			if(strList.equals("[]")){
//				
//			}
			//usesPartList = (List)helper.getUsesPartMasters(partIfc);

		} catch (QMException e) {
			Object[] aobj = new Object[] { partIfc.getPartNumber() };
			// "获取名为*的零部件结构时出错！"
			
			throw new ERPException(e, RESOURCE, "Util.17", aobj);
		}

		return usesPartList;
	}
	 /**
 	 * 根据下一个路线判断当前路线代码
 	 * 如果下一个路线单位是"川漆(架)","川漆(身)","川涂(塑)","川生(塑)"中的一个
 	 * 如果是最后一个路线单位，则是-T,如果不是最后，则是-T1
 	 * @param List routeCodeList
 	 * @return String 采购标识;
 	 * @throws QMException
 	 */
	public String getLXCode(List routeCodeList,String routeCode){
		
		 Collection coll = null;
		 int i = 0;
		 Vector vecCode= new Vector();
		 vecCode.add("川漆(架)");
		 vecCode.add("川漆(身)");
		 vecCode.add("川涂(塑)");
		 vecCode.add("川生(塑)");
		 

		 String[] makeNodes = new String[routeCodeList.size()];
			for (int m = 0;m<routeCodeList.size();  m++) {
				makeNodes[m] = (String) routeCodeList.get(m);
				i++;
			}
			//System.out.println("routeCode="+routeCode);
			
			//如果是"川焊"，如果下一个路线单位事vecCode中的一个。那么路线单位是-T,否则是—B
              	 if(routeCode.trim().equals("川焊")){
              		
              		for (int a = 0; a < makeNodes.length; a++) {
							String Nowcode = (String) makeNodes[a];							
							if(Nowcode.equals(routeCode)){
								String nextCode = "";
		              		    int next = a + 1;
								if (next != makeNodes.length){
									
									nextCode = makeNodes[a+1];
									System.out.println("nextCode====="+nextCode);
									if(vecCode.contains(nextCode)){
										routeCode = "川焊1";
									}
									else{
										routeCode = "川焊2";
									}
								}
							}
							
              			}
              	 }
              	 if(routeCode.trim().equals("川架")){
               		
               		for (int a = 0; a < makeNodes.length; a++) {
 							String Nowcode = (String) makeNodes[a];							
 							if(Nowcode.equals(routeCode)){
 								String nextCode = "";
 		              		    int next = a + 1;
 								if (next != makeNodes.length){
 									
 									nextCode = makeNodes[a+1];
 									System.out.println("nextCode====="+nextCode);
 									if(vecCode.contains(nextCode)){
 										routeCode = "川架1";
 									}
 									else{
 										routeCode = "川架2";
 									}
 								}
 							}
 							
               			}
               	 }
              if(routeCode.trim().equals("川冲")){
               		
               		for (int a = 0; a < makeNodes.length; a++) {
 							String Nowcode = (String) makeNodes[a];							
 							if(Nowcode.equals(routeCode)){
 								String nextCode = "";
 		              		    int next = a + 1;
 								if (next != makeNodes.length){
 									
 									nextCode = makeNodes[a+1];
 									System.out.println("nextCode====="+nextCode);
 									if(vecCode.contains(nextCode)){
 										routeCode = "川冲1";
 									}
 									
 								}
 							}
 							
               			}
               	 }
              	 
              if(routeCode.trim().equals("川生(调)")){
             		
             		for (int a = 0; a < makeNodes.length; a++) {
							String Nowcode = (String) makeNodes[a];							
							if(Nowcode.equals(routeCode)){
								String nextCode = "";
		              		    int next = a + 1;
								if (next != makeNodes.length){
									
									nextCode = makeNodes[a+1];
							
									if(vecCode.contains(nextCode)){
										routeCode = "川生(调)1";
									}
									else{
										routeCode = "川生(调)2";
									}
								}
							}
							
             			}
             	 } 
              if(routeCode.trim().equals("协")){
                		
                		for (int a = 0; a < makeNodes.length; a++) {
  							String Nowcode = (String) makeNodes[a];							
  							if(Nowcode.equals(routeCode)){
  								String nextCode = "";
  		              		    int next = a + 1;
  								if (next != makeNodes.length){
  									
  									nextCode = makeNodes[a+1];
  						
  									if(vecCode.contains(nextCode)){
  										routeCode = "协1";
  									}
  									else{
  										routeCode = "协2";
  									}
  								}
  							}
  							
                			}
                	 }
              //制造路线数量是3
              //如果下一个路线单位是"川漆(架)","川漆(身)","川涂(塑)","川生(塑)"中的一个
          	 //如果是最后一个路线单位，则是-T,如果不是最后，则是-T1
              if(vecCode.contains(routeCode)){
            	 /// System.out.println("routeCodeList.size()====="+routeCodeList.size());
          		if(routeCodeList.size()==3){
          			for (int a = 0; a < makeNodes.length; a++) {
						String Nowcode = (String) makeNodes[a];							
						if(Nowcode.equals(routeCode)){
							String nextCode = "";
	              		    int next = a + 1;
							if (next != makeNodes.length){
								
								nextCode = makeNodes[a+1];
								//System.out.println("nextCode====="+nextCode);
								if(vecCode.contains(nextCode)){
									routeCode = "川漆(架)2";
								}
								else{
									routeCode = "川漆(架)1";
								}
							}
						}
						
          			}
          		}
          	
          	 }
             
              if(routeCode.trim().equals("川生")){
            		
            		for (int a = 0; a < makeNodes.length; a++) {
  						String Nowcode = (String) makeNodes[a];							
  						if(Nowcode.equals(routeCode)){
  							String nextCode = "";
  	              		    int next = a + 1;
  							if (next != makeNodes.length){
  								
  								nextCode = makeNodes[a+1];
  						
  								if(vecCode.contains(nextCode)){
  									routeCode = "川生1";
  								}
  								else{
  									routeCode = "川生2";
  								}
  							}
  						}
  						
            			}
            	 }
          
		   
			return routeCode;
	}
//	CCBegin SS1
	 /**
 	 * 根据车型获取工艺BOM清单
 	 * @param QMPartIfc cxIfc 车型
 	 * @return Vector;
 	 * @throws QMException
 	 */
	public Vector getReleaseBom(QMPartIfc cxIfc,String dwbs ) throws QMException{
	
		
		
	    String partnumber = getMaterialNumber(cxIfc,cxIfc.getVersionID());
		Object[] object1 = new Object[5];
		object1[0]=	cxIfc;
		object1[1]=	cxIfc.getPartNumber();
		object1[2]=	"1";
		object1[3]=	"top";
		object1[4]=	partnumber;
	  	Vector vec = new Vector();
	  	vec.add(object1);
	  	bianliBomList(vec, cxIfc.getBsoID(), cxIfc.getPartNumber(), dwbs);
	    System.out.println("vec===="+vec.size());
			return vec;
		
	}
	/**
	   * 导出BOM 指定车型、工厂的BOM集合。
	   * 获取子件id和当前用户工厂的生效bom中的父件。
	   * 返回集合 编号 名称 数量 制造路线 装配路线 父件编号
	   */
		private void bianliBomList(Vector vec, String id, String carModelCode, String dwbs) throws QMException
		{		
			PersistService ps = (PersistService)EJBServiceHelper.getPersistService();


			//获取子件id和数量
			Vector subvec = findChildPart(id, dwbs, "1");
			QMPartIfc parentpart = (QMPartIfc)ps.refreshInfo(id);
			
			//获取子件路线
	    String childPartid = "";
	    QMPartIfc part = null;


	    for(int i=0;i<subvec.size();i++)
	    {
	    	String[] str = subvec.elementAt(i).toString().split(",");
	    	childPartid = str[0];
	    	part = (QMPartIfc)ps.refreshInfo(childPartid);	
	    	
	
	    	//Integer.toString(level),
	  		//vec.add(new String[]{part.getPartNumber(),part.getPartName(),str[1],zhizao,zhuangpei,parentpart.getPartNumber()});	
	  		    Object[] object = new Object[5];
	            String parentpartnumber="";
				if(part==null)
				{
					System.out.println("未找到子件id："+childPartid);
					continue;
				}
				
				object[0]=	part;
				//String partVersionchild = PartHelper.getPartVersion(part);
				String partVersionchild = PartHelper.getPartVersion(part);
				String partnumberChild =getMaterialNumber(part,partVersionchild);
				object[1]=	partnumberChild;
				object[2]=	str[1];
				
				QMPartIfc Parentpart = parentpart;
				object[3]=	Parentpart.getPartNumber();
				String partVersion = PartHelper.getPartVersion(Parentpart);
				parentpartnumber =getMaterialNumber(Parentpart,partVersion);
				object[4]=	parentpartnumber;
				
				vec.add(object);
	  		     bianliBomList(vec, childPartid, carModelCode, dwbs);
	  		
	  		
	  	}
		}
		
		/**
		   * 获取子件和数量
		   */
		  private Vector findChildPart(String id, String dwbs, String effect) throws QMException
		  {
		  	Vector vec = new Vector();
		  	Connection conn = null;
		  	Statement stmt =null;
		  	ResultSet rs=null;
		  	try
		  	{
		  		PersistService ps = (PersistService)EJBServiceHelper.getPersistService();
		  		conn = PersistUtil.getConnection();
		  		stmt = conn.createStatement();
		  		rs = stmt.executeQuery("select childPart,quantity from GYBomStructure where effectCurrent='"+effect+"' and parentPart='" + id + "' and dwbs='"+dwbs+"' order by childNumber");
		  		
		  		while(rs.next())
		  		{
		  			vec.add(rs.getString(1)+","+rs.getString(2));
		  		}
		  		//关闭ResultSet
		  		rs.close();
		  		//关闭Statement
		  		stmt.close();
		  		//关闭数据库连接
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
		  	return vec;
		  }
		  //CCEnd SS1
//		CCBegin SS2
	/**
	   * 获取批量替换车型表。
	   */
	  public Vector getBatchUpdateCM(String id , String dwbs) throws QMException
	  {
		  Vector cxvec = new Vector();
	  	System.out.println("come in getBatchUpdateCM   id=="+id+" dwbs=="+dwbs);
	  	//JSONArray json = new JSONArray();
	  	try
	  	{
	  		PersistService ps = (PersistService)EJBServiceHelper.getPersistService();
	  		
	  		QMPartIfc part = null;
	  		part = (QMPartIfc)ps.refreshInfo(id);
	  		cxvec.add(part);
	  		//设置根节点
	  		//JSONObject jo = new JSONObject();
	  		
	  		//获取标签
	  		Connection conn = null;
	  		Statement stmt =null;
	  		ResultSet rs=null;
	  		try
	  		{
	  			conn = PersistUtil.getConnection();
	  			stmt = conn.createStatement();
	  			String sql = "select assisPart from batchupdateCM where yPart='" + id + "' and dwbs='"+dwbs+"'";
	  			rs = stmt.executeQuery(sql);
	  			while(rs.next())
	  			{
	  				part = (QMPartIfc)ps.refreshInfo(rs.getString(1));
	  				cxvec.add(part);
	  			}
	  			
	  			//清空并关闭sql返回数据
	  			rs.close();
	  			//关闭Statement
	  			stmt.close();
	  			//关闭数据库连接
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
	  		System.out.println("cxvec====="+cxvec);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return cxvec;
	  }
	  /**
	 	 * 判断零件是否是车型码 
	 	 *1、零件类型是“车型”
         *2、C开头
         *3、长度是15位
	 	 * @param QMPartIfc cxIfc 车型
	 	 * @return Vector;
	 	 * @throws QMException
	 	 */
		public boolean checkAuto(QMPartIfc  part) throws QMException{
		
			
			boolean freturn = false;
		    String partnumber = part.getPartNumber();
		    String parttype = part.getPartType().getDisplay().toString();
		    long lenNumber = partnumber.length();
		   // System.out.println("partnumber==="+partnumber+"parttype=="+parttype+lenNumber);
		    if(partnumber.startsWith("C")&&parttype.equals("车型")&&lenNumber==15){
		    	freturn=true;
		    }
			return freturn;			
		}
//	CCEnd SS2
		 /**
	 	 *  检查编号是否带版本
	 	 * @param String partNumber
	 	 * @param String partType
		 * @throws QMXMLException 
	 	 * @throws QMException
	 	 */
		  /**
	 	 *  检查编号是否带版本
	 	 * @param String partNumber
	 	 * @param String partType
		 * @throws QMXMLException 
	 	 * @throws QMException
	 	 */
		public static boolean ifHasVersion(String partNumber ,String partType) throws QMException{
	       // String partNumber = partIfc.getPartNumber()
	      //  String partType = partIfc.getPartType().getDisplay().toString();
	      //  String materialNumber="";
//	       System.out.println("partNumber=="+partNumber);
//	       System.out.println("partType=="+partType);
	        //?	如果零部件类型属性为标准件，则物料号不加版本，规则：零部件号+路线单位简称
	        //?	如果“零部件类型”属性为车型，则物料号不加版本，规则：零部件号+路线单位简称
	        if((partNumber.startsWith("CQ")) || (partNumber.startsWith("Q")) || (partNumber.startsWith("T"))){
	        	
	        	return true;
	        }
	        //驾驶室零件号包含“5000990”、 发动机零件号包含“1000940”，则物料号不加版本，物料号不加版本，规则：零部件号+路线单位简称
	        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0||partNumber.indexOf("5000020")>=0||partNumber.indexOf("5000030")>=0){
	        	return true;
	        }
	        //原材料不带版本
	        if(partNumber.indexOf("*")>=0||partType.equals("Assembly")){

	        	return true;
	        }
//	      判断零件是否是车型
	        long lenNumber = partNumber.length();
	      //  System.out.println("lenNumber=="+lenNumber);
	        if(partNumber.startsWith("C")&&partType.equals("Model")&&lenNumber==15){
	        	return true;
		    }
	        //零部件编号第一个“/”后为“*L01*”、“0”、“*0（1，2，3，4）”、、“ZBT”、“*(L)”、“AH”、“*J0*”、
	        //“*J1*”、“*-SF”、“BQ”和“*-ZC”的不加版本，规则：零部件号+路线单位简称 。
	        if(partNumber.indexOf("/")>=0){
	        	
	        	int a = partNumber.indexOf("/");
	        	//System.out.println("a="+a);
	        	String temp = partNumber.substring(a+1);
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
//	        		System.out.println("temp==="+temp);
//	        		System.out.println("str==="+str);
	        		if(str.equals(temp)){
	        			return true;
	        		}
	        	}
	        	//在中间型×a×
	        	for (int i1 = 0; i1 < array2.length; i1++){
	        	//	System.out.println("55555555555555==");
	        		String str = array2[i1];
	        		if(temp.indexOf(str)>=0){
	    
	        			return true;
	        		}
	        	}
	        	//在结尾a×
	        	for (int i1 = 0; i1 < array3.length; i1++){
	        		
	        		String str = array3[i1];
	        		if(temp.endsWith(str)){
	        			return true;
	        		}
	        	}
	        }
	       

		return false;
		}
    /**
     * 调试信息
     */

          private static final boolean VERBOSESYSTEM = (RemoteProperty.getProperty(
	        "com.faw_qm.bomchange.verbose","true")).equals("true");
}

