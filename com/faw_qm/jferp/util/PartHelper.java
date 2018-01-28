/**
 * ���ɳ��� PartHelper.java    1.0    2013/06/05
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �����������ڰ��㼶���ˣ������ǽ���ʱ��һ����� ������ 2013-09-03
 * SS2 ���������ܹ�����󣬲���Ҫ�ж�branchid ������ 2013-09-03
 * SS3 �¼��߼��ܳ��жϹ��� ��1����ŵ���λ�ǡ�G������1������·�ߺ��á���װ��·�ߣ�2������·�߲����ã���װ��·�ߣ�3���߼��ܳɾ�����ְ��Ʒ�������� 2014-12-17
 * SS4 ·�߲��ʱ�������Լ����Լ��������ڲ��·�ߴ���һ������ liujiakun 20150129
 * SS5 ����ǹ��岿��������Ҫװ��·���ǿ�����·�� ������ 2015-10-30
 * SS6 �������岿���жϷ��������жϱ��λ������5λ�� liunan 2017-3-3
 * SS7 ���ݿ�����ʻ���������·�߲�ֹ��򣬶Գ�����е��� ����� 2017-12-13
 */
package com.faw_qm.jferp.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.log4j.Logger;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.config.util.ConfigSpecHelper;
import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.jferp.model.MaterialSplitIfc;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.model.LifeCycleTemplateMasterInfo;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartConfigSpecAssistant;
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
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewManageableIfc;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.viewmanage.model.ViewObjectInfo;


/**
 * <p>
 * ��ERP�������ù�����
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: һ������
 * </p>
 * 
 * @author ������
 * @version 1.0
 */
public class PartHelper
{
	 private static final String RESOURCE = "com.faw_qm.jferp.util.ERPResource";
	 PartConfigSpecInfo partConfigSpecIfc = null;
	 String[] State={"����׼��"};
    public PartHelper()
    {
    }
    public  Collection getSubParts(QMPartIfc partIfc) throws QMException {
    	if(VERBOSESYSTEM)
		System.out.println("getSubParts begin ....");
    	StandardPartService sp = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
    	 // PartConfigSpecIfc configSpecIf =
			// getPartConfigSpecByViewName("������ͼ");
    	 PartUsageLinkIfc partlink=new PartUsageLinkInfo();
    	 Vector result=new Vector();
    	 Vector vec=new Vector();
// System.out.println("getSubParts begin 11111111111...."+partlink.getBsoID());
// System.out.println("getSubParts begin 2222222222...."+partIfc.getBsoID());
    	 vec = getAllParts(partIfc,partlink,vec);
// QMPartIfc part1 = filterLifeState(partIfc);
// result.add(part1);
    	// System.out.println("getSubParts vec="+vec);
         if(vec!=null&&vec.size()>0){
        	 for(int i = 0;i<vec.size();i++){
        		 Object[] obj=(Object[])vec.elementAt(i);
        		 QMPartIfc partInfo = (QMPartIfc)obj[1];
        		 result.add(partInfo);
        	 }
         }
         // ������������
        // System.out.println("getSubParts result="+result);
         
         Vector resultVector = result;
        // System.out.println("getSubParts resultVector="+resultVector);
    	 return resultVector;
    	 
	    }
    /**
	 * ����ָ�����ù淶�����ָ��������ʹ�ýṹ�� ���ؼ���Collection��ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
	 * 
	 * @param partIfc
	 *            �㲿��ֵ����
	 * @param configSpecIfc
	 *            �㲿�����ù淶��
	 * @throws QMException
	 * @see QMPartInfo
	 * @see PartConfigSpecInfo
	 * @return Collection ����Collection��ÿ��Ԫ����һ���������:<br>
	 */
    private Vector getAllParts(QMPartIfc partifc_old,PartUsageLinkIfc partlink,Vector result)
    throws QMException
    {

      float quan=partlink.getQuantity();
      Object[] obj = null;
       // ��һ��������getUsesPartIfcs����������Collection�����CollectionΪ�գ����߳���Ϊ0,�׳��쳣
     // System.out.println("getAllParts partifc_old="+partifc_old);
     // QMPartIfc partifc = filterLifeState(partifc_old);

      
     // System.out.println("getAllParts partifc="+partifc);
       Collection collection = getUsesPartIfcs(partifc_old);
     // System.out.println("getAllParts collection="+collection.size());
       if((collection.size() == 0) || (collection == null))
       {
           // "δ�ҵ����ϵ�ǰɸѡ�����İ汾"
           // throw new PartException(RESOURCE, "E03014", null);
           return result;
       }
       // �ڶ���: ��collection�е�ÿ��Ԫ�ؽ���ѭ������Ҫ��������һ���ݹ�ķ���productStructure()
       // productStructure(subPartIfc, configSpecIfc, parentBsoID,
		// partUsageLinkIfc)
       else
       {
           Object[] tempArray = new Object[collection.size()];
           collection.toArray(tempArray);
          
           PartUsageLinkIfc partUsageLinkIfc1=null;
           // �Ȱ�tempArray�е�����Ԫ�ض��ŵ�resultVector����
           for (int i = 0; i < tempArray.length; i++)
           {
        	   QMPartIfc partIfc1 = null;
               QMPartIfc part1 = null;
              // QMPartIfc part2 = null;
               Object[] tempObj = (Object[]) tempArray[i];
               if(tempObj[1] instanceof QMPartIfc
                       && tempObj[0] instanceof PartUsageLinkIfc)
               {
                   obj = new Object[3];
                   partIfc1 = (QMPartIfc) tempObj[1];
                 // ?�㲿��������װ��ͼ���ü������Ӽ�����ERP����
                   String partType = partIfc1.getPartType().getDisplay().toString();
                   if(partType.equals("װ��ͼ"))
                   continue;
                   part1 = filterLifeState(partIfc1);
                 // System.out.println("getAllParts part2="+part2);
                   if(part1!=null)
                   {
             
                   partUsageLinkIfc1 = (PartUsageLinkIfc) tempObj[0];
                  // System.out.println("getAllParts part1="+part1);
                   obj[0] = part1.getPartNumber();

                       obj[1] = part1;
                       quan=quan*partUsageLinkIfc1.getQuantity();
                       obj[2]=Float.toString(quan);
                       result.add(obj);
                   }

               }
               
               if(part1 != null&&partUsageLinkIfc1!=null)
                   getAllParts(part1, partUsageLinkIfc1,
                            result);
            }
        }
        return result;
    }
  
    /**
	 * ����ָ�����ù淶�����ָ��������ʹ�ýṹ�� ���ؼ���Collection��ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
	 * ��1��Ԫ�ؼ�¼���������¼��use��ɫ��mastered����ƥ�����ù淶��iterated���� ���û��ƥ����󣬼�¼mastered����
	 * 
	 * @param partIfc
	 *            �㲿��ֵ����
	 * @return
	 * @throws QMException
	 */
    public Collection getUsesPartIfcs(QMPartIfc partIfc) throws QMException {
        Collection links = null;
        PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
        if (partIfc.getBsoName().equals("GenericPart"))
            links = pservice.navigateValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
        else if (partIfc.getBsoName().equals("QMPart"))
            links = pservice.navigateValueInfo(partIfc, "usedBy", "PartUsageLink", false);
        if (links == null || links.size() == 0)
            return new Vector();
        return getUsesPartsFromLinks(links);
    }
 
    /**
	 * ͨ��ָ����ɸѡ������������collection�е�PartUsageLinkIfc�����Ӧ��
	 * ����ɸѡ������Iterated��������ɸѡ�����������ɸѡ�����򷵻ض�Ӧ��Mastered���㲿����
	 * 
	 * @param collection
	 *            ��PartUsageLinkIfc����ļ��ϡ�
	 * @return ÿ��Ԫ��Ϊһ������.
	 *         ����ĵ�һ��Ԫ��ΪPartUsageLinkIfc���󣬵ڶ���Ԫ��ΪQMPartIfc�������û��QMPartIfc����Ϊ������QMPartMasterIfc����
	 * @throws QMException
	 */
    public Collection getUsesPartsFromLinks(Collection collection) throws QMException
    {
        Collection masterCollection = mastersOf(collection);
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService("ConfigService");
        if(partConfigSpecIfc == null)
        {
            partConfigSpecIfc = new PartConfigSpecInfo();
            PartStandardConfigSpec pStandardcs = new PartStandardConfigSpec();
            PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
            QMQuery query = new QMQuery("ViewObject");
            QueryCondition cond = new QueryCondition("viewName", "=", "������ͼ");
            query.addCondition(cond);
            Collection col = pservice.findValueInfo(query);
            if(col == null || col.isEmpty())
                throw new QMException("û��������ͼ��");
            pStandardcs.setViewObjectIfc((ViewObjectIfc)col.iterator().next());
            pStandardcs.setWorkingIncluded(true);
            partConfigSpecIfc.setStandard(pStandardcs);
            partConfigSpecIfc.setStandardActive(true);
            partConfigSpecIfc.setBaselineActive(false);
            partConfigSpecIfc.setEffectivityActive(false);
        }
        PartConfigSpecAssistant pcon = new PartConfigSpecAssistant(partConfigSpecIfc);
        Collection iteratedCollection = cservice.filteredIterationsOf(masterCollection, pcon);
        Collection allCollection = ConfigSpecHelper.recoverMissingMasters(masterCollection, iteratedCollection);
        return ConfigSpecHelper.buildConfigResultFromLinks(collection, "uses", allCollection);
    }
    /**
	 * ����ָ���Ĺ�����master��iteration֮�䣩������ÿ�������������ӵ� mastered����ָ����ɫmaster���Ľ����
	 * 
	 * @param links
	 *            ������ֵ���󼯺�
	 * @param role
	 *            ��ɫ��
	 * @exception com.faw_qm.config.exception.ConfigException
	 * @return ��Ӧ������ֵ�����Mastered���󼯺ϡ�
	 */
    /**
	 * ����ָ���Ĺ�����master��iteration֮�䣩������ÿ�������������ӵ� mastered����ָ����ɫmaster���Ľ����
	 * 
	 * @param links
	 *            ������ֵ���󼯺�
	 * @param role
	 *            ��ɫ��
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
	 * 
	 * @param collection
	 *            �����
	 * @return Collection �ų����ظ����ݵļ��� Collection��ÿһ��Ԫ��ΪһObject����
	 *         ��Object�����еĵ�0��Ԫ��Ϊһֵ����
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
    		  // ��ȡ������ͼ���°汾
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
  		  // ��ȡ������ͼ���°汾
  		  PartConfigSpecIfc configSpec = PartHelper.getPartConfigSpecByViewName("������ͼ");
  		  QMPartIfc partIfc =  (QMPartInfo) PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc) partIfc1.getMaster() , configSpec);
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
	 * ������������״�������������ǰ׷�� ���������Լ������Ժ��״̬�����Ϊ�������������������ͼ���°汾�㲿��״̬����������
	 * ��׷�ݸü�������ͼ֮ǰ���������İ汾��
	 * 
	 * @param vec
	 *            �㲿������ �� �ṹ������
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
                // System.out.println("state========1"+state);
                for(int j=0;j<State.length;j++)
                {
                    if(state.equals(State[j]))
                    {                       
                    	 return part;
                    }
                }
             // ׷��,�Ȼ�ȡ������ͼ���°汾���������������ȡ��������������������ȡ������ͼ���¼���
              // part = getZZPartInfoByMasterBsoID(part1.getMasterBsoID());
               // System.out.println("������ͼ�汾 = "+part);
                if(part==null){
// System.out.println("��ʼ��ȡ������ͼ���°汾");
// System.out.println("part1 = "+part1);
// ��ӹ�����ͼ���°汾
                    PartConfigSpecIfc configSpecGY = PartHelper.getPartConfigSpecByViewName("������ͼ");
                    part = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)part1.getMaster() , configSpecGY);
                  // part = getPart(part);
                }
                
                // result.add(part);
             
              // System.out.println("���հ汾 = "+part);
      
        }
        return part;
    }
    /**
	 * ������������״̬׷�ݵ�ǰ��汾�㲿��
	 * 
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
       
       // System.out.println("curPart.getPredecessorID() =
		// "+curPart.getPredecessorID());
        if(preID==null||preID.length()==0)
        {
        	return null;
        }
        QMPartIfc prePart = (QMPartIfc)persistService.refreshInfo(preID, false);
        // CCBegin SS2
// if(!prePart.getBranchID().equals(curPart.getBranchID()))
// {
// // ���°汾
// VersionControlService vservice =
// (VersionControlService)EJBServiceHelper.getService("VersionControlService");
// return (QMPartIfc)vservice.getLatestIteration(curPart);
// }else
// {//CCEnd SS2
            String state = prePart.getLifeCycleState().getDisplay();
            for(int j = 0;j < State.length;j++)
            {
              // System.out.println("==================������������״̬׷�ݵ�ǰ��汾�㲿��
				// state = "+state);
                if(state.equals(State[j]))
                {
                    return prePart;
                }
            }
           // System.out.println("==================������������״̬׷�ݵ�ǰ��汾�㲿�� prePart
			// = "+prePart);
            return getPart(prePart);
// }
    }
    /**
	 * ��ȡ������ͼ����С�汾��������������״̬�����ơ�����������׼����
	 * 
	 * @param masterid
	 * @return QMPartInfo
	 * @throws QMException
	 */
    public QMPartInfo getZZPartInfoByMasterBsoID(String masterid)
    		throws QMException {
    	try {
    		PersistService pService = (PersistService) EJBServiceHelper.getPersistService();
    		VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
    		Collection col = vcservice.allVersionsOf((QMPartMasterIfc) pService.refreshInfo(masterid));
    		// System.out.println("col = "+col);
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
	 * ����������㲿������ϢBsoID�����㲿�����ù淶, ���ط����㲿�����ù淶�ģ��ܸ�QMPartMasterIfc����������㲿���ļ��ϡ�
	 * 
	 * @param partMasterID
	 *            �㲿������ϢBsoID��
	 * @return Vector ���˳������㲿��ֵ����ļ��ϣ����û�кϸ���㲿������new Vector()��
	 * @exception QMException
	 */
    public static Vector filterIterations(QMPartMasterIfc partMasterIfc )
        throws QMException
    {
    	
      // �ڶ������㲿�����ù淶ֵ����
      PartConfigSpecIfc configSpecIfc = PartServiceRequest.findPartConfigSpecIfc();
      // ������������������������StandardPartService�еķ���filterdIterationsOf����
      Vector paraVector = new Vector();
      paraVector.addElement(partMasterIfc);
    // System.out.println("paraVector="+paraVector);
      Collection result = PartServiceRequest.filteredIterationsOf(paraVector, getPartConfigSpecByViewName("������ͼ"));
      // System.out.println("result="+result);
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
	 * 
	 * @param viewName
	 *            String
	 * @throws QMException
	 * @return PartConfigSpecIfc
	 * @author liunan 2008-08-01
	 */
    public static PartConfigSpecIfc getPartConfigSpecByViewName(String viewName) throws
        QMException {
      ViewService viewService = (ViewService) EJBServiceHelper.getService(
          "ViewService");
      ViewObjectIfc view = viewService.getView(viewName);
      // ������ָ������ͼ����û�л�ȡ����Ӧ��ֵ�����򷵻ص�ǰ���ù淶
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
        // ����Ա�û�������������PasswordTable����
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
                // if(s!=null)
                // {
                // s=new String(fromb64(s));
                // }
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
	 * �Ƚ������汾�Ĵ�С
	 * 
	 * @param version1
	 * @param version2
	 * @return QMPartInfo
	 * @throws QMException
	 */
     public static int compareVersion(String version1,String version2)
     		 {
// System.out.println("version1="+version1);
// System.out.println("version2="+version2);
            int bool = 1;
            // ��ð汾1�Ͱ汾2��λ��
            int dot1 = version1.indexOf(".");
            int dot2 = version2.indexOf(".");
            // System.out.println("dot1="+dot1+" dot2="+dot1);
            // ����汾1�Ͱ汾2��������
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
            // ����汾1�Ͱ汾2������
            if(dot1>=0&&dot2>=0){
            	// System.out.println("dot22222222");
            	String gyVersion1 = version1.substring(0,dot1);
            	String gyVersion2 = version2.substring(0,dot2);
// System.out.println("gyVersion1="+gyVersion1);
// System.out.println("gyVersion2="+gyVersion2);
            	// �ԡ�.��ǰ���бȽ�
            	// �����ǰ�汾����ͬ
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
            	}// �����ǰ�汾��ͬ,��Ƚϵ��汾
            	else{
	            		String zzVersion1 = version1.substring(dot1+1);
	                	String zzVersion2 = version2.substring(dot2+1);
// System.out.println("zzVersion1="+zzVersion1);
// System.out.println("zzVersion2="+zzVersion2);
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
		 * 
		 * @param partNumber
		 *            partNumber
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
// PartServiceHelper pshelper= new PartServiceHelper();
// QMPartInfo partInfo = pshelper.getPartByMasterID(partIfc);
 		return partInfo;
 	}
	/**
	 * liujiakun 20140226 * ���ݸ������������Գ�����,����ض�iba����ֵ�� return String Ϊ��iba���Ե�ֵ��
	 * 
	 * @param part
	 *            QMPartIfc
	 * @throws QMException
	 * @return String
	 */
	public static String getPartIBA(QMPartIfc part, String ibaDisplayName,String sName) throws QMException {
		String ibavalue = "";
		try {
			PersistService pService = (PersistService) EJBServiceHelper
			.getPersistService();
			QMQuery query = new QMQuery("StringValue");
			int j = query.appendBso("StringDefinition", false);
			QueryCondition qc = new QueryCondition("iBAHolderBsoID", "=", part
					.getBsoID());
			query.addCondition(qc);
			query.addAND();
			QueryCondition qc1 = new QueryCondition("definitionBsoID", "bsoID");
			query.addCondition(0, j, qc1);
			query.addAND();
			QueryCondition qc2 = new QueryCondition("name", " = ", sName);
			query.addCondition(j, qc2);
			query.addAND();
			QueryCondition qc3 = new QueryCondition("ibaDisplayName", " = ", ibaDisplayName);
			query.addCondition(j, qc2);
			// System.out.println("getPartIBA query="+query.getDebugSQL());
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
			jsbb = getPartIBA(partIfc, "����Դ�汾","sourceVersion");
	
			// 1) �㲿������������Դ�汾��������ֵ�����㲿���汾����Ϊ��������Դ�汾��
			// 2) �㲿������������Դ�汾������Ϊ�գ����㲿���汾����ΪPDM�����汾��
			if (jsbb != null && jsbb.length() > 0) {
				int index = jsbb.indexOf(".");
				if(index>=0){
					jsbb = jsbb.substring(0,index);
				}
				partVersion = jsbb ;	
			} else {
				partVersion = partIfc.getVersionID();
				
			}
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
// public static String getPartIBA(QMPartIfc partIfc, String
// ibaDisplayName,String sName)
// throws QMException {
//
// String ibaValue = "";
// final HashMap nameAndValue = new HashMap();
// try {
// IBAValueService
// ibservice=(IBAValueService)EJBServiceHelper.getService("IBAValueService");
// partIfc =
// (QMPartIfc)ibservice.refreshAttributeContainerWithoutConstraints(partIfc);
// } catch (QMException e) {
// // "ˢ����������ʱ����"
// throw new QMException(e);
// }
// DefaultAttributeContainer container = (DefaultAttributeContainer) partIfc
// .getAttributeContainer();
// if (container == null) {
// container = new DefaultAttributeContainer();
// }
// final AbstractValueView[] values = container.getAttributeValues();
// for (int i = 0; i < values.length; i++) {
// final AbstractValueView value = values[i];
// final AttributeDefDefaultView definition = value.getDefinition();
// if (definition.getDisplayName().trim()
// .equals(ibaDisplayName.trim())&&definition.getName().trim()
// .equals(sName.trim())) {
// if (value instanceof AbstractContextualValueDefaultView) {
// MeasurementSystemCache.setCurrentMeasurementSystem("SI");
// final String measurementSystem = MeasurementSystemCache
// .getCurrentMeasurementSystem();
// if (value instanceof UnitValueDefaultView) {
// DefaultUnitRenderer defaultunitrenderer = new DefaultUnitRenderer();
// String ss = "";
// try {
// ss = defaultunitrenderer
// .renderValue(
// ((UnitValueDefaultView) value)
// .toUnit(),
// ((UnitValueDefaultView) value)
// .getUnitDisplayInfo(measurementSystem));
// } catch (UnitFormatException e) {
// // "������λ��ʽ����"
// throw new QMXMLException(e);
// } catch (IncompatibleUnitsException e) {
// // "���ֲ����ݵ�λ��"
// throw new QMXMLException(e);
// }
// final String ddd = ((UnitValueDefaultView) value)
// .getUnitDefinition()
// .getDefaultDisplayUnitString(measurementSystem);
// // nameAndValue.put(definition.getDisplayName(), ss +
// // ddd);
// ibaValue = ss + ddd;
// } else {
//				
// ibaValue = ((AbstractContextualValueDefaultView) value)
// .getValueAsString();
// }
//			
// ibaValue = ((ReferenceValueDefaultView) value).getLocalizedDisplayString();
// }
// break;
// }
// }
//	
// return ibaValue;
// }
	  /**
		 * �㲿��������·�ߵ�λ��ֻҪ������������·�ߵ�λ�����ɹ�����ʶ������Ϊ��Y������������Ϊ��N����
		 * ����������·�ߵ�λ���ܡ��Ρ��Σ���������(��)����(װ)����(��)����(��)����(��)����(����)����(��)����(����)����(��)��Э(����)����������Ϳ
		 * 
		 * @param List
		 *            routeCodeList
		 * @return String �ɹ���ʶ;
		 * @throws QMException
		 */
	public String getCgbs(List routeCodeList){
		 String cgbs = "Y";
		 Vector coll = new Vector();
		 String kcc = "";
			kcc = RemoteProperty.getProperty("kcc");
			for (int m = routeCodeList.size() - 1; m >= 0; m--) {
				String routeCode = (String) routeCodeList.get(m);
				// System.out.println("cgbs="+cgbs);
				
				  String[] array = kcc.split("��");
				  for(int ii=0;ii<array.length;ii++){
					  String objID =  array[ii];
					  coll.add(objID);
				    }
// System.out.println("coll="+coll);
// System.out.println("routeCode="+routeCode);
				  if(coll.contains(routeCode)){
					  cgbs = "N";
				  }
			}
			return cgbs;
	}
	  /**
		 * ������һ��·���жϵ�ǰ·�ߴ���
		 * 
		 * @param List
		 *            routeCodeList
		 * @return String �ɹ���ʶ;
		 * @throws QMException
		 */
	public String getLXCode(List routeCodeList,String routeCode,int index,HashMap backCount){
		String cgbs = "Y";
		Collection coll = null;
		String routeKey = "";
		int i = 0;
		// CCBegin SS7
		// ��ͷ����
		boolean flag = false;
		int hz = 0;
		//HashMap count = new HashMap();

		String[] codeList={"��","��(��)","��(����)","��(��)","��(��)","��(��)","��","��","��(��)","��(����)","��(װ)","��(��)"};
		// CCEnd SS7
		String nextCode = "";
		//System.out.println("routeCodeList.size()��"+routeCodeList.size()+"========="+index);


		if(!(index == routeCodeList.size()-1))
			nextCode = (String)routeCodeList.get(index+1);
		System.out.println("nextCode-------------------------------------------------"+nextCode);
		//ֻҪ��һ��·���Ǽ�(��)����Ϳ����ô��װ��ƷΪ��-T��
		if(nextCode.equals("��(��)")||nextCode.equals("Ϳ")){
			return "T";
		}
		if(ifCodeHT1(routeCodeList,index,routeCode,backCount)){
			//System.out.println("·��====="+routeCode+"====�ǻ�ͷ�����ǵ�"+((Integer)backCount.get(routeCode)).intValue()+"�λ�ͷ");
			String makeCodeNameStr = "";
			//if(((Integer)backCount.get(routeCode)).intValue()==1)
				//makeCodeNameStr = "B";
			//else
				makeCodeNameStr = "B"+(((Integer)backCount.get(routeCode)).intValue());
			return makeCodeNameStr;
		}
		// �����������·�߽��д���
		for(int j=0;j<codeList.length;j++)
		{
			//System.out.println("��ǰ·�ߣ�"+routeCode);
			//System.out.println("��һ��·�ߣ�"+nextCode);
			//System.out.println("����ѭ������"+codeList[j]);
			if(routeCode.equals(codeList[j]))
			{   
				if(routeCode.trim().equals("��")){
					// �����"��"���¸�����·�ߵ�λΪ����(��)���͡�Ϳ����·�ߵ�λ�ǡ�T��
					// �¸�����·�ߵ�λΪ����(��)��·�ߵ�λ�ǡ�B��
					//if(!count.keySet().contains(nextCode)){
					if(nextCode.equals("��(��)")||nextCode.equals("Ϳ")||nextCode.equals("ר")){
						// CCEnd SS7
						routeKey = "��1";
					}
//					CCBegin SS7
					else if(nextCode.equals("��(��)")){
//						CCEnd SS7
						routeKey = "��2";
					}else{
						routeKey = "";
					}
				}
				//}//
				else if(routeCode.equals("��(��)")){
					// ����¸�����·�ߵ�λΪ����(��)������Ϳ���͡�ר����·�ߵ�λ�ǡ�T��
					// �¸�����·�ߵ�λΪ����(����)����·�ߵ�λ�ǡ�P��
					// �¸�����·�ߵ�λΪ����(װ)����·�ߵ�λ�ǡ�B��
					//if(!count.keySet().contains(nextCode)){
					if(nextCode.equals("��(��)")||nextCode.equals("Ϳ")||nextCode.equals("ר")){
						routeKey = "��(��)1";
					}
					else if(nextCode.equals("��(����)")){
						routeKey = "��(��)2";
					}
					else if(nextCode.equals("��(װ)")){
						routeKey = "��(��)3";
					}
					//}
				} 
				else if(routeCode.equals("��(����)")){
					// �¸�����·�ߵ�λΪ����(��)������Ϳ����·�ߵ�λ�ǡ�T��
					// �¸�����·�ߵ�λΪ����(����)������Э(����)��·�ߵ�λ�ǡ�P��
					// ����·�ߵ�λ�л�ͷ����·�ߵ�λ�ǡ�B1��

					if(nextCode.equals("��(��)")||nextCode.equals("Ϳ")){
						routeKey = "��(����)1";
					}
					else if(nextCode.equals("��(����)")||nextCode.equals("Э(����)")||nextCode.equals("Э(��)")||nextCode.equals("��(��)")){
						routeKey = "��(����)2";
					}
					// CCBegin SS7
					else if((nextCode.equals("��(��)") || nextCode.equals("��(װ)") || nextCode.equals("��(��)"))&&ifCodeHT1(routeCodeList,index,nextCode,backCount)){
						//if(((Integer)backCount.get(nextCode)) != null)
							return routeKey = "B"+(((Integer)backCount.get(nextCode)).intValue());
						//else
							//return routeKey = "B";
					}
					//}// CCEnd SS7
				}
				else if(routeCode.equals("��(��)")){

					// �¸�����·�ߵ�λΪ��Э(����)��������(����)��·�ߵ�λ�ǡ�P��
					// �¸�����·�ߵ�λΪ����(����)��������(��)����·�ߵ�λ�ǡ�B��
					// �¸�����·�ߵ�λΪ��Э����·�ߵ�λ�ǡ�Q��

					if(nextCode.equals("��(����)")||nextCode.equals("Э(����)")||nextCode.equals("Э(��)")||nextCode.equals("��(��)")){
						routeKey = "��(��)1";
					}
					else if(nextCode.equals("��(����)")||nextCode.equals("��(��)")){
						routeKey = "��(��)2";
					}
					// CCBegin SS7
					else if(nextCode.equals("Э")){
						routeKey = "��(��)3";
					}
					// CCEnd SS7

				}
				else if(routeCode.equals("��(��)")){
					// �¸�����·�ߵ�λΪ����(��)������Ϳ������Э����·�ߵ�λ�ǡ�T��
					// �¸�����·�ߵ�λΪ����(����)������Э(��)����·�ߵ�λ�ǡ�P��
					// �¸�����·�ߵ�λΪ����(����)��������(��)����·�ߵ�λ�ǡ�B��
					//if(!count.keySet().contains(nextCode)){
					if(nextCode.equals("��(��)")||nextCode.equals("Ϳ")||nextCode.equals("Э")){
						routeKey = "��(��)1";
					}
					else if(nextCode.equals("��(����)")||nextCode.equals("Э(����)")||nextCode.equals("Э(��)")||nextCode.equals("��(��)")){
						routeKey = "��(��)2";
					}
					else if(nextCode.equals("��(����)")||nextCode.equals("��(��)")){
						routeKey = "��(��)3";
					}
					//}
				}// CCBegin SS4
				else if(routeCode.equals("��(��)")){

					// �¸�����·�ߵ�λΪ��Ϳ��������(��)����·�ߵ�λ�ǡ�B��������·�ߵ�λ�ǡ�T��
					if(nextCode.equals("Ϳ")){
						routeKey = "��(��)2";
					}
					else {
						routeKey = "��(��)1";
					}

				}// CCEnd SS4
				// CCBegin SS7
				else if(routeCode.equals("��(����)")){

					// �¸�����·�ߵ�λΪ����(װ)����·�ߵ�λ�ǡ�P��
					if(nextCode.equals("��(װ)")){
						routeKey = "��(����)1";
					}
					//}
				}else if(routeCode.equals("��(��)")){						
					// �¸�����·�ߵ�λΪ����(����)����·�ߵ�λ�ǡ�B��
					if(nextCode.equals("��(����)")){
						routeKey = "��(��)1";
					}
				}else if(routeCode.equals("��")){						
					// �¸�����·�ߵ�λΪ����(����)����·�ߵ�λ�ǡ�B��
					// �¸�����·�ߵ�λΪ���ܣ��ᣩ���򡰱�����·�ߵ�λ�ǡ�M��
					if(nextCode.equals("��(����)")){
						routeKey = "��1";
					}
					else if(nextCode.equals("��(��)") || nextCode.equals("��")){
						routeKey = "��2";
					}
				}else if(routeCode.equals("��")){
					// �¸�����·�ߵ�λΪ����(����)���򡰺�(��)����·�ߵ�λ�ǡ�M��
					if(nextCode.equals("��(����)") || nextCode.equals("��(��)")){
						routeKey = "��";
					}
				}else if(routeCode.equals("��(װ)")){
					// �¸�����·�ߵ�λΪ����(��)����·�ߵ�λ�ǡ�T��
					if(nextCode.equals("��(��)")){
						routeKey = "��(װ)";
					}
				}else if(routeCode.equals("��(��)")){
					// �¸�����·�ߵ�λΪ����(װ)���򡰺�(����)����·�ߵ�λ�ǡ�B��
					// �¸�����·�ߵ�λΪ��Э����·�ߵ�λ�ǡ�Q��
					if(nextCode.equals("��(װ)")||nextCode.equals("��(����)")){
						routeKey = "��(��)1";
					}else if(nextCode.equals("Э")){
						routeKey = "��(��)2";
					}
				}else if(routeCode.equals("Э")){
					// �¸�����·�ߵ�λΪ����(��)�����ܡ��򡰺�(��)����·�ߵ�λ�ǡ�H��
					// �¸�����·�ߵ�λΪ����(װ)���򡰺�(����)����·�ߵ�λ�ǡ�B��
					if(nextCode.equals("��(��)")||nextCode.equals("��(��)")){
						routeKey = "Э1"; 
					}else if(nextCode.equals("��(װ)")||nextCode.equals("��(����)")){
						routeKey = "Э2";
					}
				}else if(routeCode.equals("Э(����)")){
					// �¸�����·�ߵ�λΪ����(��)����·�ߵ�λ�ǡ�B��
					// �¸�����·�ߵ�λΪ����(װ)����·�ߵ�λ�ǡ�H��
					if(nextCode.equals("��(��)")){
						routeKey = "��(��)1";
					}else if(nextCode.equals("��(װ)")){
						routeKey = "��(��)2";
					}
				}else if(routeCode.equals("��(��)")){
					// �¸�����·�ߵ�λΪ����(��)����·�ߵ�λ�ǡ�B��
					if(nextCode.equals("Ϳ")){
						routeKey = "��(��)2";
					}
				}
			}
		}
		// CCEnd SS7
		return RemoteProperty.getProperty("com.faw_qm.jferp.routecode." + routeKey);
	}
	  /**
		 * �ж�·�����Ƿ���ڻ�ͷ��
		 * 
		 * @param List
		 *            routeCodeList
		 * @return boolean;
		 * @throws QMException
		 */
	public boolean ifCodeHT(List routeCodeList,int index,String code){
		while(index>0){
			String currentCode = "";
			currentCode = (String)routeCodeList.get(--index);
			if(code.equals(currentCode)){
				return true;
			}
		}
		return false;
	}
	
	  /**
		 * �ж�·�����Ƿ���ڻ�ͷ��
		 * 
		 * @param List
		 *            routeCodeList
		 * @return boolean;
		 * @throws QMException
		 */
	public boolean ifCodeHT1(List routeCodeList,int index,String code,HashMap backCount){
		// String routeCode = makeNodes[j];
		while(index>0){
			String currentCode = "";
			currentCode = (String)routeCodeList.get(--index);
			//System.out.println("1---------"+currentCode+"------2--------"+(String)routeCodeList.get(x)+"----4---"+currentCode.equals((String)routeCodeList.get(x)));
			if(code.equals(currentCode)){
				int c1 = 0;
				if(backCount.keySet().contains(code)){
					Integer c = (Integer)backCount.get(code);
					c1 = c.intValue()+1;
				}else{
					c1=1;
				}
				backCount.put(code, new Integer(c1));
				return true;
			}
			//System.out.println("1---------"+currentCode+"-------3--------------"+j);
		}
		return false;

	}
	
	  /**
		 * �ж� �Ƿ��������
		 * 
		 * @param QMPartIfc
		 *            partIfc
		 * @return boolean;
		 * @throws QMXMLException
		 * @throws QMException
		 */
	public boolean getJFVirtualIdentity(QMPartIfc partIfc,String routeAsString,String routeAssemStr) throws QMException{
		Vector vec = new Vector();
		String xnj =getPartIBA(partIfc, "�����","virtualPart");
		// System.out.println("getPartIBA xnj="+xnj);
		boolean re = false;
		if(xnj==null||xnj.length()==0){
			String partNumber = partIfc.getPartNumber();
			String lx         = partIfc.getPartType().toString();
			if(lx.equals("Logical")&&((routeAssemStr==null)||routeAssemStr.length()==0)){
				// System.out.println("111111111==============="+lx);
				re=true;
			}if(routeAsString.equals(routeAssemStr)&&!partNumber.contains("1000940")){
				// System.out.println("2222222222===============");
				re=true;
			}
		}else{
			if(xnj.equals("Y")){
				// System.out.println("33333333==============="+xnj);
				re=true;
			}
			
		}

		return re;
	}
	  /**
		 * ���ϱ�����ɹ���
		 * 
		 * @param QMPartIfc
		 *            partIfc
		 * @param String
		 *            partVersion
		 * @param String
		 *            makeCodeNameStr
		 * @param String
		 *            dashDelimiter
		 * @return boolean;
		 * @throws QMXMLException
		 * @throws QMException
		 */
	public String getMaterialNumber(QMPartIfc partIfc,String partVersion  ) throws QMException{
        String partNumber = partIfc.getPartNumber();
        String partType = partIfc.getPartType().getDisplay().toString();
        String materialNumber="";
        // ? ����㲿����������Ϊ��׼���������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        // ? ������㲿�����͡�����Ϊ���ͣ������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        if((partIfc.getPartNumber().startsWith("CQ")) || (partIfc.getPartNumber().startsWith("Q")) || (partIfc.getPartNumber().startsWith("T"))||partType.equals("����")){
        	materialNumber =  partIfc.getPartNumber();
        	return materialNumber;
        }
        // ��ʻ������Ű�����5000990���� ����������Ű�����1000940���������ϺŲ��Ӱ汾�����ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0){
        	materialNumber =  partIfc.getPartNumber()  ;
        	return materialNumber;
        }
        // �㲿����ŵ�һ����/����Ϊ��*L01*������0������*0��1��2��3��4����������ZBT������*(L)������AH������*J0*����
        // ��*J1*������*-SF������BQ���͡�*-ZC���Ĳ��Ӱ汾�������㲿����+·�ߵ�λ��� ��
        if(partNumber.indexOf("/")>=0){
        	int a = partNumber.indexOf("/");
        	// System.out.println("a="+a);
        	String temp = partNumber.substring(a);
        	// System.out.println("temp="+temp);
        	// ��ȫƥ����a
        	String[] array1 = {"0","ZBT","AH","BQ"};
        	// ���м��͡�a��
        	String[] array2 = {"L01","J0","J1"};
        	// �ڽ�βa��
        	String[] array3 = {"0","1","2","3","4","(L)","-SF","-ZC"};
        	// ��ȫƥ����a
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(str.equals(temp)){
        			materialNumber =  partIfc.getPartNumber()  ;
        			return materialNumber;
        		}
        	}
        	// ���м��͡�a��
        	for (int i1 = 0; i1 < array2.length; i1++){
        		String str = array2[i1];
        		if(temp.indexOf(str)>=0){
        			materialNumber =  partIfc.getPartNumber()  ;
        			return materialNumber;
        		}
        	}
        	// �ڽ�βa��
        	for (int i1 = 0; i1 < array3.length; i1++){
        		String str = array3[i1];
        		if(temp.endsWith(str)){
        			materialNumber =  partIfc.getPartNumber() ;
        			return materialNumber;
        		}
        	}
        }
        // ��ȡ�㲿���ġ�ERP�ص��㲿���š����ԣ���������ԡ�/��������ַ������С�-����ȡ��-��������ַ�����
        // ȡ�����ַ����͡�L01������0������1������2������3������4������ZBT������L������AH������J0������J1������SF����
        // ��BQ������ZC���Ƚϣ���������������г����ַ�������ERP�ص��㲿���š�/����汾����Ϊ�°汾��Ϊ
        // PDM�㲿���ţ����ա��㲿����+·�ߵ�λ��ơ������γ����Ϻš�����㲿���ġ�ERP�ص��㲿���š�
        // ����Ϊ�գ����չ����1�γ����ϱ��롣
        // ���������ϱ���
        String str = getERPHD(partNumber+"/"+partVersion);
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
                			materialNumber =  partIfc.getPartNumber()  ;
                			return materialNumber;
                		}
                	}
            		// �����/����-��֮���ַ������ټ���array1��
            		// ��ERP�ص��㲿���š�/����汾����Ϊ�°汾��ΪPDM�㲿���ţ����ա��㲿����+·�ߵ�λ��ơ������γ����Ϻ�
            		// �����=ԭ����erp�ص�����ţ���ԭ���İ汾�滻Ϊ���µİ汾
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
		 * ���ϱ�����ɹ���
		 * 
		 * @param QMPartIfc
		 *            partIfc
		 * @param String
		 *            partVersion
		 * @param String
		 *            makeCodeNameStr
		 * @param String
		 *            dashDelimiter
		 * @return boolean;
		 * @throws QMXMLException
		 * @throws QMException
		 */
	public String getMaterialNumberHistory(String[] partQuan) throws QMException{
		String partNumber ="";
		String partVersionValue="";
		// �㲿�����
		partNumber=partQuan[0];
		// �㲿���汾
		partVersionValue=partQuan[1];

		// ����
		String partType = partQuan[8];
        String materialNumber="";
        // ? ����㲿����������Ϊ��׼���������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        // ? ������㲿�����͡�����Ϊ���ͣ������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        if(partType.equals("��׼��")||partType.equals("����")){
        	materialNumber =  partNumber  ;
        	return materialNumber;
        }
        // ��ʻ������Ű�����5000990���� ����������Ű�����1000990���������ϺŲ��Ӱ汾�����ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0){
        	materialNumber =  partNumber  ;
        	return materialNumber;
        }
        // �㲿����ŵ�һ����/����Ϊ��*L01*������0������*0��1��2��3��4����������ZBT������*(L)������AH������*J0*����
        // ��*J1*������*-SF������BQ���͡�*-ZC���Ĳ��Ӱ汾�������㲿����+·�ߵ�λ��� ��
        if(partNumber.indexOf("/")>=0){
        	int a = partNumber.indexOf("/");
        	String temp = partNumber.substring(a);
        	// ��ȫƥ����a
        	String[] array1 = {"0","ZBT","AH","BQ"};
        	// ���м��͡�a��
        	String[] array2 = {"L01","J0","J1"};
        	// �ڽ�βa��
        	String[] array3 = {"0","1","2","3","4","(L)","-SF","-ZC"};
        	// ��ȫƥ����a
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(str.equals(temp)){
        			materialNumber =  partNumber  ;
        			return materialNumber;
        		}
        	}
        	// ���м��͡�a��
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(temp.indexOf(str)>=0){
        			materialNumber =  partNumber  ;
        			return materialNumber;
        		}
        	}
        	// �ڽ�βa��
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
        		if(temp.endsWith(str)){
        			materialNumber =  partNumber ;
        			return materialNumber;
        		}
        	}
        }
        // ��ȡ�㲿���ġ�ERP�ص��㲿���š����ԣ���������ԡ�/��������ַ������С�-����ȡ��-��������ַ�����
        // ȡ�����ַ����͡�L01������0������1������2������3������4������ZBT������L������AH������J0������J1������SF����
        // ��BQ������ZC���Ƚϣ���������������г����ַ�������ERP�ص��㲿���š�/����汾����Ϊ�°汾��Ϊ
        // PDM�㲿���ţ����ա��㲿����+·�ߵ�λ��ơ������γ����Ϻš�����㲿���ġ�ERP�ص��㲿���š�
        // ����Ϊ�գ����չ����1�γ����ϱ��롣
        // ���������ϱ���
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
            		// �����/����-��֮���ַ������ټ���array1��
            		// ��ERP�ص��㲿���š�/����汾����Ϊ�°汾��ΪPDM�㲿���ţ����ա��㲿����+·�ߵ�λ��ơ������γ����Ϻ�
            		// �����=ԭ����erp�ص�����ţ���ԭ���İ汾�滻Ϊ���µİ汾
            		String oldPartNumber = str.substring(0, a)+"/"+partVersionValue+"-"+temp1;
            		materialNumber =  oldPartNumber  ;
            		return materialNumber;
            	}
        	}
        }
		materialNumber =  partNumber + "/" + partVersionValue  ;

return materialNumber;
}
	// ��ȡerp�ص�����
	private String getERPHD(String partNumber){
		// Collection col =
		// query("JFMaterialSplit","partNumber","=",partNumber);
		Collection col = null;
		PersistService pService;
		try {
// CCBegin SS7
			pService = (PersistService)EJBServiceHelper.getService("PersistService");
// QMQuery query = new QMQuery("JFMaterialSplit");
// System.out.println("query="+query.getSQLSelf());
// QueryCondition condition = new QueryCondition("partNumber",
// QueryCondition.EQUAL, partNumber);
			 QMQuery query = new QMQuery("JFMaterialSplit");
			 QueryCondition qc1=new QueryCondition("partNumber","=",partNumber);
             query.addCondition(qc1);
             col=pService.findValueInfo(query);
// CCEnd SS7
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
		 * 
		 * @param tableName
		 *            return Iterator
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
		// System.out.println("partNumber="+partNumber);
			return partNumber;
		}
		return materPartNumber;
	}
// CCBegin SS3
	/**
	 * �Ƿ����߼��ܳ�
	 * 
	 * @return boolean
	 * @throws QMException
	 */
	public boolean isLogical(QMPartIfc part,String lx_y ,String assembStr)throws QMException{
		boolean result=false;
		// ��5λΪG����װ��·��Ϊ�ղ�������·�ߺ����á�
		//CCBegin SS6
		//if (part.getPartNumber().substring(4, 5).equals("G"))
		if (part.getPartNumber().length()>5&&part.getPartNumber().substring(4, 5).equals("G"))
		//CCEnd SS6
		{
			if(lx_y.contains("1")&&assembStr.equals("")){
				result=true;
				System.out.println("���");
			}
			if(!lx_y.contains("1")&&!assembStr.equals("")){
				result=true;
				System.out.println("ʵ��");
			}
		}
		return result;
	}
// CCEnd SS3
// CCBegin SS5
	  /**
		 * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
		 * 
		 * @param partIfc
		 *            :QMPartIfc �㲿����ֵ����
		 * @return collection:Collection ��partIfc������PartUsageLinkIfc�Ķ��󼯺ϡ�
		 * @throws QMException
		 */
    public Collection getUsesPartMasters(QMPartIfc partIfc) throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters begin ....");
        // ����������������׳�PartException"��������Ϊ��"
        if(partIfc == null)
            throw new PartException(RESOURCE, "CP00001", null);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        PartDebug.trace(this, PartDebug.PART_SERVICE, "getUsesPartMasters end....return is Colletion ");
        if(partIfc.getBsoName().equals("GenericPart"))
        	return pservice.navigateValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
          else 
        	  return pservice.navigateValueInfo(partIfc, "usedBy", "PartUsageLink", false);
      
    }
// CCEnd SS5
    /**
	 * ������Ϣ
	 */

          private static final boolean VERBOSESYSTEM = (RemoteProperty.getProperty(
	        "com.faw_qm.bomchange.verbose","true")).equals("true");
}

