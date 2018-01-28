/**
 * ���ɳ��� PartHelper.java    1.0    2013/06/05
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 bom�洢�ṹ�����仯����һ��һ��bom��Ϊ�ṹ���� ������ 2017-4-5
 * SS2 ������������滻�� 2017-4-10
 * SS3 �ɶ����������תʵ�������ԭ���Ǻ�������Ϊ������������ 2017-12-17
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
* <p>��ERP�������ù�����</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2013</p>
* <p>Company: һ������</p>
* @author ������
* @version 1.0
*/
public class PartHelper
{
	 private static final String RESOURCE = "com.faw_qm.cderp.util.ERPResource";
	 PartConfigSpecInfo partConfigSpecIfc = null;
	 String[] State={"����׼��"};
    public PartHelper()
    {
    }
  
    /**
     * ����ָ���Ĺ�����master��iteration֮�䣩������ÿ�������������ӵ�
     * mastered����ָ����ɫmaster���Ľ����
     * @param links ������ֵ���󼯺�
     * @param role ��ɫ��
     * @exception com.faw_qm.config.exception.ConfigException
     * @return ��Ӧ������ֵ�����Mastered���󼯺ϡ�
     */
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
        }// endfor i
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
    		  //��ȡ������ͼ���°汾
    		  PartConfigSpecIfc configSpec = PartHelper.getPartConfigSpecByViewName("������ͼ");
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
  		  //��ȡ������ͼ���°汾
  		 // PartConfigSpecIfc configSpec = PartHelper.getPartConfigSpecByViewName("������ͼ");
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
     * ������������״�������������ǰ׷��
     * ���������Լ������Ժ��״̬�����Ϊ�������������������ͼ���°汾�㲿��״̬����������
     * ��׷�ݸü�������ͼ֮ǰ���������İ汾��
     * @param vec �㲿������ �� �ṹ������
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
             // ׷��,�Ȼ�ȡ������ͼ���°汾���������������ȡ��������������������ȡ������ͼ���¼���
              //  part = getZZPartInfoByMasterBsoID(part1.getMasterBsoID());
               // System.out.println("������ͼ�汾 = "+part);
                if(part==null){
//                	 System.out.println("��ʼ��ȡ������ͼ���°汾");
//                	 System.out.println("part1 = "+part1);
//                	��ӹ�����ͼ���°汾
                    PartConfigSpecIfc configSpecGY = PartHelper.getPartConfigSpecByViewName("������ͼ");
                    part = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)part1.getMaster() , configSpecGY);
                  //  part = getPart(part);
                }
                
                //result.add(part);
             
              //  System.out.println("���հ汾 = "+part);
      
        }
        return part;
    }
    /**
     * ������������״̬׷�ݵ�ǰ��汾�㲿��
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
//            // ���°汾
//            VersionControlService vservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
//            return (QMPartIfc)vservice.getLatestIteration(curPart);
//        }else
//        {//CCEnd SS2
            String state = prePart.getLifeCycleState().getDisplay();
            for(int j = 0;j < State.length;j++)
            {
              //  System.out.println("==================������������״̬׷�ݵ�ǰ��汾�㲿�� state = "+state);                
                if(state.equals(State[j]))
                {
                    return prePart;
                }
            }
           // System.out.println("==================������������״̬׷�ݵ�ǰ��汾�㲿�� prePart = "+prePart); 
            return getPart(prePart);
//        }
    }
    /**
    *��ȡ������ͼ����С�汾��������������״̬�����ơ�����������׼����
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
     * ����������㲿������ϢBsoID�����㲿�����ù淶,
     * ���ط����㲿�����ù淶�ģ��ܸ�QMPartMasterIfc����������㲿���ļ��ϡ�
     * @param partMasterID �㲿������ϢBsoID��
     * @return Vector ���˳������㲿��ֵ����ļ��ϣ����û�кϸ���㲿������new Vector()��
     * @exception QMException
     */
    public static Vector filterIterations(QMPartMasterIfc partMasterIfc )
        throws QMException
    {
    	
      //�ڶ������㲿�����ù淶ֵ����
      PartConfigSpecIfc configSpecIfc = PartServiceRequest.findPartConfigSpecIfc();
      //������������������������StandardPartService�еķ���filterdIterationsOf����
      Vector paraVector = new Vector();
      paraVector.addElement(partMasterIfc);
    //  System.out.println("paraVector="+paraVector);
      Collection result = PartServiceRequest.filteredIterationsOf(paraVector, getPartConfigSpecByViewName("������ͼ"));
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
     * ������ͼ���Ʒ����㲿�����ù淶
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
     * ��ȡ����Ա����
     */
    public String initPassword()
    {
        //����Ա�û�������������PasswordTable����
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
     *�Ƚ������汾�Ĵ�С
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
            //��ð汾1�Ͱ汾2��λ��
            int dot1 = version1.indexOf(".");
            int dot2 = version2.indexOf(".");
            //System.out.println("dot1="+dot1+"     dot2="+dot1);
            //����汾1�Ͱ汾2��������
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
            //����汾1�Ͱ汾2������
            if(dot1>=0&&dot2>=0){
            	// System.out.println("dot22222222");
            	String gyVersion1 = version1.substring(0,dot1);
            	String gyVersion2 = version2.substring(0,dot2);
//            	System.out.println("gyVersion1="+gyVersion1);
//            	System.out.println("gyVersion2="+gyVersion2);
            	//�ԡ�.��ǰ���бȽ�
            	//�����ǰ�汾����ͬ
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
            	}//�����ǰ�汾��ͬ,��Ƚϵ��汾
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
 	 * ͨ���㲿����Ż�ȡ���°汾���㲿����
 	 * @param partNumber partNumber
 	 * @return QMPartInfo ���°汾���㲿��(QMPart);
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
	 *  * ���ݸ������������Գ�����,����ض�iba����ֵ�� return String Ϊ��iba���Ե�ֵ��
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
		// ��������Դ�汾
		String jsbb;
		String partVersion = "";
		try {
			jsbb = getPartIBA(partIfc);
	
			//1)	�㲿������������Դ�汾��������ֵ�����㲿���汾����Ϊ��������Դ�汾��
			//2)	�㲿������������Դ�汾������Ϊ�գ����㲿���汾����ΪPDM�����汾��
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
	 * ���ݸ������������Գ�����,����ض�iba����ֵ�� return String Ϊ��iba���Ե�ֵ��
	 * 
	 * @throws QMXMLException
	 */
	  /**
 	 * �㲿��������·�ߵ�λ��ֻҪ������������·�ߵ�λ�����ɹ�����ʶ������Ϊ��Y������������Ϊ��N����
 	 * @param List routeCodeList
 	 * @return String �ɹ���ʶ;
 	 * @throws QMException
 	 */
	public String getCgbs(String routeAsString){
		 String cgbs = "N";
		// System.out.println("routeAsString==============="+routeAsString);
		 if(routeAsString.equals("Э")||routeAsString.equals("����(��)")||routeAsString.equals("����(��)")||routeAsString.equals("����")||routeAsString.equals("����")){
			 cgbs = "Y";
		 }
		 System.out.println("cgbs==============="+cgbs);
			return cgbs;
	}
	
	  /**
 	 * �ж�·�����Ƿ���ڻ�ͷ��
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
 	 * �ж� �Ƿ��������
 	 * @param QMPartIfc partIfc
 	 * @return boolean;
	 * @throws QMXMLException 
 	 * @throws QMException
 	 */
	public boolean getJFVirtualIdentity(QMPartIfc partIfc,String routeAsString,String routeAssemStr,boolean islogic,boolean colorFlag) throws QMException{
		Vector vec = new Vector();
		//String xnj =getPartIBA(partIfc, "�����","virtualPart");
		//System.out.println("getPartIBA xnj="+xnj);
		boolean re = false;
		//if(xnj==null||xnj.length()==0){
			String partNumber = partIfc.getPartNumber();
			String lx         = partIfc.getPartType().toString();
			//�㲿�����߼��ܳɣ���������·�ߺ�װ��·��Ϊ��
			if(islogic&&((routeAssemStr==null)||routeAssemStr.length()==0)){
				//System.out.println("111111111==============="+lx);
				re=true;
			}
			//����·�ߵ���װ��·��
			if(routeAsString.equals(routeAssemStr)){
				//System.out.println("2222222222===============");
				re=true;
			}
			//�����Ű�����1000940��
			if(partNumber.contains("1000940")){
				//System.out.println("333===============");
				re=true;
			}
//				����ɫ����ʶ��������·�����⹺����Э������(��)���ߴ���(��)��
//			if(colorFlag&&(routeAsString.contains("Э")||routeAsString.contains("����(��)")||routeAsString.contains("����(��)"))){
//				//System.out.println("444===============");
//				re=true;
//			}

//			������·�ߣ���װ��·��Ϊ�գ�ͬʱ����·�߲�����Э����(��)
			if((routeAssemStr==null||routeAssemStr.length()==0)&&(!routeAsString.contains("Э"))&&(!routeAsString.contains("����(��)"))){
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
 	 *  ���ϱ�����ɹ���
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
        //?	����㲿����������Ϊ��׼���������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        //?	������㲿�����͡�����Ϊ���ͣ������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        if((partIfc.getPartNumber().startsWith("CQ")) || (partIfc.getPartNumber().startsWith("Q")) || (partIfc.getPartNumber().startsWith("T"))){
        	materialNumber =  partIfc.getPartNumber();
        	return materialNumber;
        }
        //��ʻ������Ű�����5000990���� ����������Ű�����1000940���������ϺŲ��Ӱ汾�����ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0||partNumber.indexOf("5000020")>=0||partNumber.indexOf("5000030")>=0){
        	materialNumber =  partIfc.getPartNumber()  ;
        	return materialNumber;
        }
        //ԭ���ϲ����汾
        if(partNumber.indexOf("*")>=0||partType.equals("���պϼ�")){
        	materialNumber =  partIfc.getPartNumber()  ;
        	return materialNumber;
        }
//      �ж�����Ƿ��ǳ���
		boolean ifauto = checkAuto(partIfc);
		if(ifauto){
			
        	materialNumber =  partIfc.getPartNumber()  ;
        	return materialNumber;
        }
        //�㲿����ŵ�һ����/����Ϊ��*L01*������0������*0��1��2��3��4����������ZBT������*(L)������AH������*J0*����
        //��*J1*������*-SF������BQ���͡�*-ZC���Ĳ��Ӱ汾�������㲿����+·�ߵ�λ��� ��
        if(partNumber.indexOf("/")>=0){
        	
        	int a = partNumber.indexOf("/");
        	//System.out.println("a="+a);
        	String temp = partNumber.substring(a+1);
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
//        		System.out.println("temp==="+temp);
//        		System.out.println("str==="+str);
        		if(str.equals(temp)){
        			materialNumber =  partIfc.getPartNumber()  ;
        			return materialNumber;
        		}
        	}
        	//���м��͡�a��
        	for (int i1 = 0; i1 < array2.length; i1++){
        	//	System.out.println("55555555555555==");
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
        //��ȡ�㲿���ġ�ERP�ص��㲿���š����ԣ���������ԡ�/��������ַ������С�-����ȡ��-��������ַ�����
        //ȡ�����ַ����͡�L01������0������1������2������3������4������ZBT������L������AH������J0������J1������SF����
        //��BQ������ZC���Ƚϣ���������������г����ַ�������ERP�ص��㲿���š�/����汾����Ϊ�°汾��Ϊ
        //PDM�㲿���ţ����ա��㲿����+·�ߵ�λ��ơ������γ����Ϻš�����㲿���ġ�ERP�ص��㲿���š�
        //����Ϊ�գ����չ����1�γ����ϱ��롣
        //���������ϱ���
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
            		//�����/����-��֮���ַ������ټ���array1��
            		//��ERP�ص��㲿���š�/����汾����Ϊ�°汾��ΪPDM�㲿���ţ����ա��㲿����+·�ߵ�λ��ơ������γ����Ϻ�
            		//�����=ԭ����erp�ص�����ţ���ԭ���İ汾�滻Ϊ���µİ汾
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
 	 *  ���ϱ�����ɹ���
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
		//�㲿�����
		partNumber=partQuan[0];
		//�㲿���汾
		partVersionValue=partQuan[1];

		//����
		String partType = partQuan[8];
        String materialNumber="";
        //?	����㲿����������Ϊ��׼���������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        //?	������㲿�����͡�����Ϊ���ͣ������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        if(partType.equals("��׼��")||partType.equals("����")){
        	materialNumber =  partNumber  ;
        	return materialNumber;
        }
        //��ʻ������Ű�����5000990���� ����������Ű�����1000990���������ϺŲ��Ӱ汾�����ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0){
        	materialNumber =  partNumber  ;
        	return materialNumber;
        }
        //�㲿����ŵ�һ����/����Ϊ��*L01*������0������*0��1��2��3��4����������ZBT������*(L)������AH������*J0*����
        //��*J1*������*-SF������BQ���͡�*-ZC���Ĳ��Ӱ汾�������㲿����+·�ߵ�λ��� ��
        if(partNumber.indexOf("/")>=0){
        	int a = partNumber.indexOf("/");
        	String temp = partNumber.substring(a);
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
        			materialNumber =  partNumber  ;
        			return materialNumber;
        		}
        	}
        	//���м��͡�a��
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(temp.indexOf(str)>=0){
        			materialNumber =  partNumber  ;
        			return materialNumber;
        		}
        	}
        	//�ڽ�βa��
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(temp.endsWith(str)){
        			materialNumber =  partNumber ;
        			return materialNumber;
        		}
        	}
        }
        //��ȡ�㲿���ġ�ERP�ص��㲿���š����ԣ���������ԡ�/��������ַ������С�-����ȡ��-��������ַ�����
        //ȡ�����ַ����͡�L01������0������1������2������3������4������ZBT������L������AH������J0������J1������SF����
        //��BQ������ZC���Ƚϣ���������������г����ַ�������ERP�ص��㲿���š�/����汾����Ϊ�°汾��Ϊ
        //PDM�㲿���ţ����ա��㲿����+·�ߵ�λ��ơ������γ����Ϻš�����㲿���ġ�ERP�ص��㲿���š�
        //����Ϊ�գ����չ����1�γ����ϱ��롣
        //���������ϱ���
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
            		//�����/����-��֮���ַ������ټ���array1��
            		//��ERP�ص��㲿���š�/����汾����Ϊ�°汾��ΪPDM�㲿���ţ����ա��㲿����+·�ߵ�λ��ơ������γ����Ϻ�
            		//�����=ԭ����erp�ص�����ţ���ԭ���İ汾�滻Ϊ���µİ汾
            		String oldPartNumber = str.substring(0, a)+"/"+partVersionValue+"-"+temp1;
            		materialNumber =  oldPartNumber  ;
            		return materialNumber;
            	}
        	}
        }
		materialNumber =  partNumber + "/" + partVersionValue  ;

return materialNumber;
}
	//��ȡerp�ص�����
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
			// TODO �Զ����� catch ��
			e.printStackTrace();
		} catch (QueryException e) {  
			// TODO �Զ����� catch ��
			e.printStackTrace();
		} catch (QMException e) {
			// TODO �Զ����� catch ��
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
	  * ����query��ѯ
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
	 * �������Ų�ֳ���
	 * 
	 * @return �㲿����š�
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
	 * �Ƿ����߼��ܳ�
	 * @return boolean
	 * @throws QMException
	 */
	public boolean isLogical(QMPartIfc part,String routeAsString ,String assembStr)throws QMException{
		boolean result=false;
		//��5λΪG����װ��·��Ϊ�ղ�������·�ߺ����á�
		if (part.getPartNumber().substring(4, 5).equals("G"))
		{
			if(routeAsString.contains("��")&&assembStr.equals("")){
				result=true;
				System.out.println("���");
			}
			if(!routeAsString.contains("��")&&!assembStr.equals("")){
				result=true;
				System.out.println("ʵ��");
			}
		}
		return result;
	}
//	CCEnd SS3
//	CCBegin SS5
	  /**
     * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
     * @param partIfc :QMPartIfc �㲿����ֵ����
     * @return collection:Collection ��partIfc������PartUsageLinkIfc�Ķ��󼯺ϡ�
     * @throws QMException
     */
    public Collection getUsesPartMasters(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters begin ....");
        //����������������׳�PartException"��������Ϊ��"
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
	 * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
	 * 
	 * @param partIfc
	 *            �㲿����
	 * @throws QMException
	 *             return PartUsageLinkIfc�Ķ��󼯺ϡ�
	 */
	private final List getUsageLinks(final QMPartIfc partIfc)
	throws QMException {
	
		// ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
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
			// "��ȡ��Ϊ*���㲿���ṹʱ����"
			
			throw new ERPException(e, RESOURCE, "Util.17", aobj);
		}

		return usesPartList;
	}
	 /**
 	 * ������һ��·���жϵ�ǰ·�ߴ���
 	 * �����һ��·�ߵ�λ��"����(��)","����(��)","��Ϳ(��)","����(��)"�е�һ��
 	 * ��������һ��·�ߵ�λ������-T,��������������-T1
 	 * @param List routeCodeList
 	 * @return String �ɹ���ʶ;
 	 * @throws QMException
 	 */
	public String getLXCode(List routeCodeList,String routeCode){
		
		 Collection coll = null;
		 int i = 0;
		 Vector vecCode= new Vector();
		 vecCode.add("����(��)");
		 vecCode.add("����(��)");
		 vecCode.add("��Ϳ(��)");
		 vecCode.add("����(��)");
		 

		 String[] makeNodes = new String[routeCodeList.size()];
			for (int m = 0;m<routeCodeList.size();  m++) {
				makeNodes[m] = (String) routeCodeList.get(m);
				i++;
			}
			//System.out.println("routeCode="+routeCode);
			
			//�����"����"�������һ��·�ߵ�λ��vecCode�е�һ������ô·�ߵ�λ��-T,�����ǡ�B
              	 if(routeCode.trim().equals("����")){
              		
              		for (int a = 0; a < makeNodes.length; a++) {
							String Nowcode = (String) makeNodes[a];							
							if(Nowcode.equals(routeCode)){
								String nextCode = "";
		              		    int next = a + 1;
								if (next != makeNodes.length){
									
									nextCode = makeNodes[a+1];
									System.out.println("nextCode====="+nextCode);
									if(vecCode.contains(nextCode)){
										routeCode = "����1";
									}
									else{
										routeCode = "����2";
									}
								}
							}
							
              			}
              	 }
              	 if(routeCode.trim().equals("����")){
               		
               		for (int a = 0; a < makeNodes.length; a++) {
 							String Nowcode = (String) makeNodes[a];							
 							if(Nowcode.equals(routeCode)){
 								String nextCode = "";
 		              		    int next = a + 1;
 								if (next != makeNodes.length){
 									
 									nextCode = makeNodes[a+1];
 									System.out.println("nextCode====="+nextCode);
 									if(vecCode.contains(nextCode)){
 										routeCode = "����1";
 									}
 									else{
 										routeCode = "����2";
 									}
 								}
 							}
 							
               			}
               	 }
              if(routeCode.trim().equals("����")){
               		
               		for (int a = 0; a < makeNodes.length; a++) {
 							String Nowcode = (String) makeNodes[a];							
 							if(Nowcode.equals(routeCode)){
 								String nextCode = "";
 		              		    int next = a + 1;
 								if (next != makeNodes.length){
 									
 									nextCode = makeNodes[a+1];
 									System.out.println("nextCode====="+nextCode);
 									if(vecCode.contains(nextCode)){
 										routeCode = "����1";
 									}
 									
 								}
 							}
 							
               			}
               	 }
              	 
              if(routeCode.trim().equals("����(��)")){
             		
             		for (int a = 0; a < makeNodes.length; a++) {
							String Nowcode = (String) makeNodes[a];							
							if(Nowcode.equals(routeCode)){
								String nextCode = "";
		              		    int next = a + 1;
								if (next != makeNodes.length){
									
									nextCode = makeNodes[a+1];
							
									if(vecCode.contains(nextCode)){
										routeCode = "����(��)1";
									}
									else{
										routeCode = "����(��)2";
									}
								}
							}
							
             			}
             	 } 
              if(routeCode.trim().equals("Э")){
                		
                		for (int a = 0; a < makeNodes.length; a++) {
  							String Nowcode = (String) makeNodes[a];							
  							if(Nowcode.equals(routeCode)){
  								String nextCode = "";
  		              		    int next = a + 1;
  								if (next != makeNodes.length){
  									
  									nextCode = makeNodes[a+1];
  						
  									if(vecCode.contains(nextCode)){
  										routeCode = "Э1";
  									}
  									else{
  										routeCode = "Э2";
  									}
  								}
  							}
  							
                			}
                	 }
              //����·��������3
              //�����һ��·�ߵ�λ��"����(��)","����(��)","��Ϳ(��)","����(��)"�е�һ��
          	 //��������һ��·�ߵ�λ������-T,��������������-T1
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
									routeCode = "����(��)2";
								}
								else{
									routeCode = "����(��)1";
								}
							}
						}
						
          			}
          		}
          	
          	 }
             
              if(routeCode.trim().equals("����")){
            		
            		for (int a = 0; a < makeNodes.length; a++) {
  						String Nowcode = (String) makeNodes[a];							
  						if(Nowcode.equals(routeCode)){
  							String nextCode = "";
  	              		    int next = a + 1;
  							if (next != makeNodes.length){
  								
  								nextCode = makeNodes[a+1];
  						
  								if(vecCode.contains(nextCode)){
  									routeCode = "����1";
  								}
  								else{
  									routeCode = "����2";
  								}
  							}
  						}
  						
            			}
            	 }
          
		   
			return routeCode;
	}
//	CCBegin SS1
	 /**
 	 * ���ݳ��ͻ�ȡ����BOM�嵥
 	 * @param QMPartIfc cxIfc ����
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
	   * ����BOM ָ�����͡�������BOM���ϡ�
	   * ��ȡ�Ӽ�id�͵�ǰ�û���������Чbom�еĸ�����
	   * ���ؼ��� ��� ���� ���� ����·�� װ��·�� �������
	   */
		private void bianliBomList(Vector vec, String id, String carModelCode, String dwbs) throws QMException
		{		
			PersistService ps = (PersistService)EJBServiceHelper.getPersistService();


			//��ȡ�Ӽ�id������
			Vector subvec = findChildPart(id, dwbs, "1");
			QMPartIfc parentpart = (QMPartIfc)ps.refreshInfo(id);
			
			//��ȡ�Ӽ�·��
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
					System.out.println("δ�ҵ��Ӽ�id��"+childPartid);
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
		   * ��ȡ�Ӽ�������
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
		  		//�ر�ResultSet
		  		rs.close();
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
		  	return vec;
		  }
		  //CCEnd SS1
//		CCBegin SS2
	/**
	   * ��ȡ�����滻���ͱ�
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
	  		//���ø��ڵ�
	  		//JSONObject jo = new JSONObject();
	  		
	  		//��ȡ��ǩ
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
	  			
	  			//��ղ��ر�sql��������
	  			rs.close();
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
	  		System.out.println("cxvec====="+cxvec);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return cxvec;
	  }
	  /**
	 	 * �ж�����Ƿ��ǳ����� 
	 	 *1����������ǡ����͡�
         *2��C��ͷ
         *3��������15λ
	 	 * @param QMPartIfc cxIfc ����
	 	 * @return Vector;
	 	 * @throws QMException
	 	 */
		public boolean checkAuto(QMPartIfc  part) throws QMException{
		
			
			boolean freturn = false;
		    String partnumber = part.getPartNumber();
		    String parttype = part.getPartType().getDisplay().toString();
		    long lenNumber = partnumber.length();
		   // System.out.println("partnumber==="+partnumber+"parttype=="+parttype+lenNumber);
		    if(partnumber.startsWith("C")&&parttype.equals("����")&&lenNumber==15){
		    	freturn=true;
		    }
			return freturn;			
		}
//	CCEnd SS2
		 /**
	 	 *  ������Ƿ���汾
	 	 * @param String partNumber
	 	 * @param String partType
		 * @throws QMXMLException 
	 	 * @throws QMException
	 	 */
		  /**
	 	 *  ������Ƿ���汾
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
	        //?	����㲿����������Ϊ��׼���������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
	        //?	������㲿�����͡�����Ϊ���ͣ������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
	        if((partNumber.startsWith("CQ")) || (partNumber.startsWith("Q")) || (partNumber.startsWith("T"))){
	        	
	        	return true;
	        }
	        //��ʻ������Ű�����5000990���� ����������Ű�����1000940���������ϺŲ��Ӱ汾�����ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
	        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0||partNumber.indexOf("5000020")>=0||partNumber.indexOf("5000030")>=0){
	        	return true;
	        }
	        //ԭ���ϲ����汾
	        if(partNumber.indexOf("*")>=0||partType.equals("Assembly")){

	        	return true;
	        }
//	      �ж�����Ƿ��ǳ���
	        long lenNumber = partNumber.length();
	      //  System.out.println("lenNumber=="+lenNumber);
	        if(partNumber.startsWith("C")&&partType.equals("Model")&&lenNumber==15){
	        	return true;
		    }
	        //�㲿����ŵ�һ����/����Ϊ��*L01*������0������*0��1��2��3��4����������ZBT������*(L)������AH������*J0*����
	        //��*J1*������*-SF������BQ���͡�*-ZC���Ĳ��Ӱ汾�������㲿����+·�ߵ�λ��� ��
	        if(partNumber.indexOf("/")>=0){
	        	
	        	int a = partNumber.indexOf("/");
	        	//System.out.println("a="+a);
	        	String temp = partNumber.substring(a+1);
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
//	        		System.out.println("temp==="+temp);
//	        		System.out.println("str==="+str);
	        		if(str.equals(temp)){
	        			return true;
	        		}
	        	}
	        	//���м��͡�a��
	        	for (int i1 = 0; i1 < array2.length; i1++){
	        	//	System.out.println("55555555555555==");
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
    /**
     * ������Ϣ
     */

          private static final boolean VERBOSESYSTEM = (RemoteProperty.getProperty(
	        "com.faw_qm.bomchange.verbose","true")).equals("true");
}

