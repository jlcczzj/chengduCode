/**
 * ���ɳ���MaterialSplitServiceEJB.java	1.0              2007-10-7
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �ڹ���bom��������������汾������������汾���ȡ�����汾 ������ 2014-10-08
 * SS2 ����bomʱ��xmlֻ���bom��Ϣ������·�߷�����ֻ���������Ϣ 2014-11-20
 * SS4 ����ǳ��ͷ�bom�����ڳ��Ͳ��༭·�ߡ�������Ҫ�������ɳ�������
 * SS5 �������·���ǡ�����-���塱���ֳ�����Ϊ����ǰ��λ�ǡ����塱��������λ��װ��·�� ������ 2017-12-11
 */
package com.faw_qm.cderp.ejb.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.cderp.exception.ERPException;
import com.faw_qm.cderp.exception.QMXMLException;
import com.faw_qm.cderp.model.FilterPartInfo;
import com.faw_qm.cderp.model.IntePackIfc;
import com.faw_qm.cderp.model.IntePackInfo;
import com.faw_qm.cderp.model.MaterialSplitIfc;
import com.faw_qm.cderp.model.MaterialSplitInfo;
import com.faw_qm.cderp.model.MaterialStructureIfc;
import com.faw_qm.cderp.model.MaterialStructureInfo;
import com.faw_qm.cderp.util.IntePackSourceType;
import com.faw_qm.cderp.util.Messages;
import com.faw_qm.cderp.util.PartHelper;
import com.faw_qm.cderp.util.PublishBaseLine;
import com.faw_qm.cderp.util.PublishData;
import com.faw_qm.cderp.util.RequestHelper;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.iba.definition.AttributeHierarchyChild;
import com.faw_qm.iba.definition.ejb.service.IBADefinitionObjectsFactory;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;



/**
 * <p>
 * Title: ���ϲ�ַ���
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * 
 * </p>
 * <p>
 * Company: ������Ϣ�����ɷ����޹�˾
 * </p>
 * 
 * @author ������
 * @version 1.0
 *  �����Ӽ���ƷĬ�ϲ�part��������й��岿�� ������ 2014-06-12
 */
public class MaterialSplitServiceEJB extends BaseServiceImp {
	private static final long serialVersionUID = 1L;

	/**
	 * ��ȡ��������
	 * 
	 * @return String "MaterialSplitService"��
	 * 
	 */
	public String getServiceName() {
		return "MaterialSplitService";
	}

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
	.getLogger(MaterialSplitServiceEJB.class);

	/**
	 * �ֺŷָ��������ڷָ�·�ߺͲ��ò�Ʒ��BsoId��
	 */
	private String semicolonDelimiter = ";";

	/**
	 * ���ۺŷָ��������ڷָ�·�ߴ��롣
	 */
	private String dashDelimiter = "-";

	public Collection djbm=null;
	

	/**
	 * ���������ļ��õ������·�������������ơ�
	 */
	private static String routeIBA = RemoteProperty.getProperty("routeIBA",
	"·��1;·��2");

	/**
	 * ���ϲ�ֵĹ�����
	 */
	private static String mSplitDefaultDomainName = (String) RemoteProperty
	.getProperty("materialSplitDefaultDomain", "System");

	private static final String RESOURCE = "com.faw_qm.cderp.util.ERPResource";

	

	/**
	 * ���ŷָ��������ڷָ�useProcessPartRouteCode��
	 */
	private String delimiter = "��";
    //Ϊ���⹺�������Ƽ��ֶѣ���Ҫ����Ϊϵͳ�л�����Ҫ���л��⹺�������л����Ƽ�
	HashMap mapcglx = new HashMap();


		
		/**
		 * ��������bom
		 * @param coll �㲿���ļ���
		 * @param baselineiD �㲿����ŵĻ���
		 * @param lx �������ͣ���1������bom��������2��·�߷���
		 * @param routeID ������·��
		 * @throws QMException
		 */
		 public void publishPartsToERP(Collection coll ,String routeID ,Collection vec,int i)
	        throws Exception
	    {
	        try
	        {
	        	PartHelper partHelper = new PartHelper();
	        	PublishData publish = new PublishData();
	     	  System.out.println("jinru-------publishPartsToERPlc");
	        	
	            String xmlName = "";
	            //·�߱�ʱ��
	           xmlName = publish.getVirtualPartNumber();
	         //   MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
	           if(coll!=null){
	        	   coll = partHelper.filterLifeCycle1(coll);
	           }
	           if(coll==null||coll.size()==0){
	        	   return;
	           }
	 

	            //����㲿��
	           
	            Vector filterPartVec = split( coll, "1",  routeID);
	       
	            System.out.println("filterPartVec-------"+filterPartVec);
	        
	
	           //�����ݴ򼯳ɰ�
	            String packid = createIntePack(xmlName, "", true, routeID);             
	            IntePackService packservice = (IntePackService)EJBServiceHelper.getService("CDIntePackService");;
	            //��������
	            packservice.publishIntePack(packid, xmlName,coll,filterPartVec,vec,i);

	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	            throw e;
	        }
	    }
		/**
		 * ��������·��
		 * @param coll �㲿���ļ���
		 * @param baselineiD �㲿����ŵĻ���
		 * @param lx �������ͣ���1������bom��������2��·�߷���
		 * @param routeID ������·��
		 * @throws QMException
		 */
		 public void publishPartsToERPlc(Collection coll,String routeID)
	        throws Exception
	    {
	        try
	        {
	        	PartHelper partHelper = new PartHelper();
	        	PublishData publish = new PublishData();	     
	        	
	            String xmlName = "";
	            //����·�߱�����
	           xmlName = publish.getVirtualPartNumber();

	         
	            //����㲿��
	            Vector filterPartVec = split( null, "2",  routeID);
	           // Vector filterPartVec = new Vector();
	           System.out.println("filterPartVec-------"+filterPartVec);
	            if(filterPartVec==null||filterPartVec.size()==0){
	          	  return;     
	            }
	            System.out.println("qqq-------"+filterPartVec.size());
	           //�����ݴ򼯳ɰ�
	            String packid = createIntePack(xmlName, "", true, routeID);             
	            IntePackService packservice = (IntePackService)EJBServiceHelper.getService("CDIntePackService");;
	            //packservice.publishIntePack(packid, xmlName,al,filterPartVec);
	            //��������
	            packservice.publishIntePack(packid, xmlName,coll,filterPartVec,null,0);

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
		        sourceid = "";
		        sourcetype = IntePackSourceType.BASELINE;
		    }
		    IntePackInfo intepack = new IntePackInfo();
		    intepack.setName(name);
		    intepack.setSourceType(sourcetype);
		    intepack.setSource(sourceid);
		    IntePackService itservice = (IntePackService)EJBServiceHelper.getService("CDIntePackService");
		    IntePackIfc intepackifc = itservice.createIntePack(intepack);
		    String bsoid = intepackifc.getBsoID();
		    return bsoid;
		}

	/**
	 * ������ϣ����ERP����ר��
	 * @param coll �㲿���ļ���
	 * @param baselineiD �㲿����ŵĻ���
	 * @param lx �������ͣ���1������bom��������2��·�߷���
	 * @param routeID ������·��
	 * @throws QMException
	 */
	public Vector split(Collection coll, String inputLx, String routeID)throws QMException
	{
	
		HashMap hamap;
		PersistService pservice;
		Vector filterPartMap = new Vector();
		Vector filterPartMap1 = new Vector();
		QMPartIfc partIfc;
		String filterPartBsoIDs[];
		HashMap partLinkMap=new HashMap();
		//���������ʹ��ݸ�ʵ������
		 PartHelper partHelper = new PartHelper();
		 PublishBaseLine publishBase = new PublishBaseLine();
	
		try
		{
			RequestHelper.initRouteHashMap();
			//hamap = new HashMap();
			Collection collection = new Vector();
			pservice = (PersistService)EJBServiceHelper.getService("PersistService");
			StandardPartService sp = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
			ManagedBaselineIfc baseline = null;
			PartConfigSpecIfc configspecifc = null;
			//System.out.println("inputLx="+inputLx);	
			
			 if(inputLx.equals("2"))//·�߷���
			{
				// System.out.println("inputLx="+inputLx);	
//			����ǰ�����׼�������������ǰ�������Ӽ��Ļ���Ҫ�Ե�ǰ������Ҷ���������Ӽ����ϵĹ�ϵ���д���questions
				if(routeID == null)
					throw new QMException("Ҫ������·�߲����ڣ����ܽ������ݷ�������");
				TechnicsRouteListIfc routelistifc = (TechnicsRouteListIfc)pservice.refreshInfo(routeID);
				Collection coll1 = getPartByRouteListJF(routelistifc,partLinkMap);
				collection.addAll(coll1);
			}  
			 //���ͺ�
			 QMPartIfc cxifc = null;
		   if(inputLx.equals("1"))//BOM������
			{  
				QMPartIfc qmpartifc = null;
				Iterator iter = coll.iterator();
				while(iter.hasNext()){
					QMPartIfc qmpartifc1 = (QMPartIfc) iter.next();
					   //?�㲿��������װ��ͼ���ü������Ӽ�����ERP����
	                   String partType = qmpartifc1.getPartType().getDisplay().toString();
	                   String partnumber = qmpartifc1.getPartNumber();
	                  // System.out.println("partType="+partType);
	                   if(partType.equals("װ��ͼ"))
	                   continue;
	                  // qmpartifc = partHelper.filterLifeState(qmpartifc1);
	                   //CCBegin SS4
	                   //collection.add(qmpartifc1);
	                   if(partType.equals("����")&&(partnumber.indexOf("5000990")<0)){
	                	   collection.add(qmpartifc1);
	                   }
					  //CCEnd SS4
					   
				}
					
			}

			System.out.println("cccccccccccccccccccccccccccccbbbbbbcccc11111111111111111111111111111=============="+collection);
//    		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz1111111111111=============="+partLinkMap);
		 
		   Vector tempVec = new Vector();
		   Vector resVec = new Vector();
	
	
		  
		   //�����Ҫ��ֵ��㲿���ļ���  ,�鿴�Ƿ���Ҫ����
		   filterPartMap = filterParts(collection,partLinkMap);
		//	System.out.println("filterPartMap===" + filterPartMap);
//			System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz000000000000000=============="+filterPartMapOld);
//			System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz22222222222222=============="+partLinkMap);
			if(filterPartMap == null || filterPartMap.size() == 0)
			{
				System.out.println("��������û����Ҫ��ֵ��㲿��");
				logger.debug("��������û����Ҫ��ֵ��㲿����");
//				System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz3333333333333============="+filterPartMap);
				return null;
				
			}
			 
			 if(inputLx.equals("1"))//BOM������
				{ 
//				 filterPartMap1 = filterPartMap;
//				 filterPartMap = new Vector();
				// filterPartMap.add(cxifc.getBsoID());
//				 
//				 return filterPartMap;
				}
			
			System.out.println("filterPartMap="+filterPartMap);
			
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
		
		partIfc = null;
		filterPartBsoIDs = new String[filterPartMap.size()];
		//���������Ҫ��ֵ��㲿����id
		for(int mm = 0; mm < filterPartMap.size(); mm++)
		{
			String partid = (String)filterPartMap.elementAt(mm);
			filterPartBsoIDs[mm] = partid;
		}
		//���ÿһ���㲿�����д���
		// ���ÿһ���㲿�����д���

		for (int i = 0; i < filterPartBsoIDs.length; i++) {
			partIfc = (QMPartIfc) pservice.refreshInfo(filterPartBsoIDs[i]);

			// System.out.println("partIfczzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz4444444444========="+partIfc);
            //gybom
			//List usageLinkList = getUsageLinks(partIfc);
			Vector routevec = new Vector();

			//�ж��ǲ���ͨ������·�߷����������ͨ������·�ߣ���ǰ�ڻ����listroutepartlinkinfo����ȥ���Ա�
			//ȡ����ȷ��·��
		//	System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa/00000000000=============="+partIfc.getBsoID()+"sssssssssss"+partIfc.getPartNumber());
			//���ݽ����ϡ�����·�ߺϲ�����ѡ�����������װ��·�ߺϲ� 
             //partLinkMap�ǻ�ȡ����·�߹������ʱ���Ѿ�ˢ�³�����·��link
				if(partLinkMap.get(partIfc.getBsoID())==null)
					routevec=RequestHelper.getRouteBranchs(partIfc, null);
				else
					routevec=RequestHelper.getRouteBranchs(partIfc, (ListRoutePartLinkInfo)partLinkMap.get(partIfc.getBsoID()));

		//	System.out.println("routevec1111111111111111=============="+partLinkMap);
			//			����·��
			String routeAsString = "";
//			ȫ·�ߴ�
			String routeAllCode = "";
//			װ��·��
			String routeAssemStr = "";
			//��ɫ����ʶ
			boolean colorFlag = false;
			boolean isMoreRoute=false;
			String lx_y = "";
			if(routevec.size() != 0)
			{
				
				routeAsString = (String)routevec.elementAt(5);
				
				routeAllCode = (String)routevec.elementAt(1);
				//System.out.println("routeAllCode11111111111111"+routeAllCode);
				routeAssemStr=(String)routevec.elementAt(2);


				//System.out.println("routeAssemStr1111111111111w"+routeAssemStr);
				isMoreRoute=(new Boolean((String)routevec.elementAt(4))).booleanValue();
			    String scolorFlag = (String)routevec.elementAt(6);
			    if(scolorFlag.equals("1")){
			    	colorFlag = true;
			    }
			    lx_y =  (String)routevec.elementAt(7);

			}
//			�鿴�Ƿ���Ҫ����	
			boolean aa = ifPublish(partIfc,routeAsString,routeAssemStr);
			if(!aa){

				filterPartMap.remove(filterPartBsoIDs[i]);
			
				continue;
			}
				
//			�¼��߼��ܳ��жϹ��� ��1����ŵ���λ�ǡ�G������1������·�ߺ��á���װ��·�ߣ�2������·�߲����ã���װ��·�ߣ�3���߼��ܳɾ�����ְ��Ʒ
			boolean islogic =partHelper.isLogical(partIfc,routeAsString,routeAssemStr);	


	       
//			��Ź�˾���ɽӿڷ������㲿��������ӵ����������ߡ��У�����ҵ����Աͨ���û��߲鿴�㲿������
			publishBase.putPartToBaseLine(partIfc,"��������");
			

//			�����㲿����ţ�ɾ�����е����ϲ�ֺ����ϲ�ֽṹhere
			//Ϊ���·�ɾ����ǣ������ϱ�ͽṹ���ж���¼�˹������Ϻ����Ͻṹ��Ӧ��ɾ���ļ�¼�����������²��֮ǰ����ɾ��		
			//��� ������ 20140624erpҪ���Ŵ��汾
			// ��������Դ�汾

			String partnumber = partIfc.getPartNumber();
			String partVersion = "";
			//System.out.println("scbb="+scbb);
		
		     partVersion = PartHelper.getPartVersion(partIfc);
		
			
			 partnumber =partHelper.getMaterialNumber(partIfc,partVersion);
		

		//	 erpҪ��partnumber��ʾ��Ʒ���
			String partnumberold = partnumber;
			
			
//
			System.out.println("start partnumber ========"+partnumber);
			//ɾ������������+�汾
			deleteMaterialStructure(partIfc,true,partnumber);
			List oldMSplitList = deleteMaterialSplit(partIfc, true,partnumber);
			List mSplitListBiaoJi = new ArrayList();
			// �������ѷ���������

			mSplitListBiaoJi = getAllMSplit(partnumber);

			// ֱ�����ϣ����ٻ������ϱ�� 
			HashMap oldMaterialHashMap = new HashMap();
			//����ʷ������Ϣ���ŵ�һ��map�У�Ϊ�Ժ�鿴������Ƿ񷢲��������д���
			for (int dd = 0; dd < mSplitListBiaoJi.size(); dd++) {
				MaterialSplitIfc a = (MaterialSplitIfc) mSplitListBiaoJi
						.get(dd);
				oldMaterialHashMap.put(a.getMaterialNumber(), a);

			}
			// System.out.println("dddddddddddddddddddssssssss=========="+oldMaterialHashMap);

			// �����㲿����ţ�����������ϲ�ֽṹ���ŵ�mStructureListBaoJi��
			List mStructureListBaoJi = new ArrayList();
			mStructureListBaoJi = getMStructure(partnumber);
			// ֱ�ӻ���ṹ�����ٻ�����
			HashMap oldMaterialStructrue = new HashMap();
			for (int ff = 0; ff < mStructureListBaoJi.size(); ff++) {
				MaterialStructureIfc s = (MaterialStructureIfc) mStructureListBaoJi
						.get(ff);
				oldMaterialStructrue.put(s.getParentNumber() + ";;;"
						+ s.getChildNumber(), s);
			}
			//CCbegin ss5
			if(routeAsString.toString().startsWith("����")){
				routeAsString=routeAsString.substring(3);
				routeAllCode=routeAllCode.substring(3);
//				System.out.println("routeAllCode999999999=="+routeAllCode);
//				System.out.println("routeAsString999999999=="+routeAsString);
				//continue;
			}
			
//			CCEnd SS5
			String partType = partIfc.getPartType().getDisplay().toString();
			//CCBegin SS4
			if(partType.equals("����")&&(partnumber.indexOf("5000990")<0)){
				routeAsString = "����(װ)";
				routeAllCode = "����(װ)";
				colorFlag = true;
			}
			//CCEnd SS4
          //���·��Ϊ�գ������㲿�����߼��ܳɣ�"����-����"�����в��
//			CCbegin ss5
		    System.out.println("routeAsString==="+routeAsString);
			if((routeAsString != null && routeAsString.toString().equals("")||routeAsString.toString().equals("����-����"))||routeAsString.toString().equals("����-����")||(islogic))
			{//CCEnd SS4
				System.out.println("·��Ϊ�ջ���·�����߼��ܳɣ����������㲿��");
				MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
				mSplitIfc.setRootFlag(true);
				//hamap.put(partIfc.getMasterBsoID(), mSplitIfc);
				String materialNumber =  partnumber;
				mSplitIfc.setMaterialNumber(materialNumber); 
				mSplitIfc.setPartNumber(partnumberold);
				mSplitIfc.setPartVersion(partVersion);
				mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
				mSplitIfc.setSplited(false);
//				CCBegin SS5
				System.out.println("routeAsString000000000==="+routeAsString);
				if(routeAsString.toString().equals("����-����")){
					System.out.println("����1===");
					mSplitIfc.setRoute(routeAllCode);	
				    mSplitIfc.setRCode("����");
					mSplitIfc.setRouteCode("����");
				}else{
					System.out.println("����2===");
					mSplitIfc.setRoute(routeAllCode);
				    mSplitIfc.setRCode(routeAsString);
					mSplitIfc.setRouteCode(routeAsString);
				}
				if(routeAsString.toString().equals("����-����")){
					mSplitIfc.setRoute(routeAllCode);					
				    mSplitIfc.setRCode("����");
					mSplitIfc.setRouteCode("����");
				}else{
					mSplitIfc.setRoute(routeAllCode);
				    mSplitIfc.setRCode(routeAsString);
					mSplitIfc.setRouteCode(routeAsString);
				}
			
				//CCEnd SS6
				 String cgbs = partHelper.getCgbs(routeAsString);
				 System.out.println("cgbs0000==============="+cgbs);
				 mapcglx.put(partIfc.getBsoID(), cgbs);
				mSplitIfc.setIsMoreRoute(isMoreRoute);
				 
//				�������Ӽ��б�Ϊ�գ���status����Ϊ2����������Ϊ0	
			//·�߽׶��޷��ж��Ƿ����Ӽ�
				mSplitIfc.setStatus(0);
				
				setMaterialSplit(partIfc, mSplitIfc);
				//�������ʶ
				 //System.out.println("startvirture===============");
				 boolean virtualFlag= false;
			     if(partType.equals("����")&&(partnumber.indexOf("5000990")<0)){
			    	 
			     }else{
			    	  virtualFlag=partHelper.getJFVirtualIdentity( partIfc, routeAsString, routeAssemStr,islogic,colorFlag);
			     }
				
//				 System.out.println("routeAsString==============="+routeAsString+"ddd"+routeAssemStr);
//				 System.out.println("partIfc0000==============="+partIfc);
//				System.out.println("virtualFlag0000==============="+virtualFlag);
				 mSplitIfc.setVirtualFlag(virtualFlag);
				
               //�ɹ���ʶ
				mSplitIfc.setProducedBy("Y");
				//��ɫ����ʶ
			    mSplitIfc.setColorFlag(colorFlag);
				

			  //  System.out.println("oldMaterialHashMap==============="+oldMaterialHashMap);
				if(oldMaterialHashMap!=null&&oldMaterialHashMap.size()>0){
				 //   System.out.println("mSplitIfc.getMaterialNumber()==============="+mSplitIfc.getMaterialNumber());

					if(oldMaterialHashMap.get(mSplitIfc.getMaterialNumber())!=null){
						
							mSplitIfc.setMaterialSplitType("U");
							//ɾ����ʷ����
							pservice.deleteValueInfo((MaterialSplitIfc)oldMaterialHashMap.get(mSplitIfc.getMaterialNumber()));
							oldMaterialHashMap.remove(mSplitIfc.getMaterialNumber());
							
					
							Iterator oldMaterialIter=oldMaterialHashMap.keySet().iterator();
		//					System.out.println("meiyongde---========================"+oldMaterialHashMap);
							//�˴�����û������ΪʲôҪ����D���ǰ���Ѿ�ɾ����
							while(oldMaterialIter.hasNext())
							{
								
								System.out.println("�˴�Ӧ�ý�����======================11111111111=="+oldMaterialHashMap);
								String key=(String)oldMaterialIter.next();
								MaterialSplitIfc oldMaterialSplitIfc=(MaterialSplitIfc)oldMaterialHashMap.get(key);
								oldMaterialSplitIfc.setMaterialSplitType("D");
								pservice.saveValueInfo(oldMaterialSplitIfc);
							}
							Iterator oldStruIte=oldMaterialStructrue.keySet().iterator();
							while(oldStruIte.hasNext())
							{
								MaterialStructureIfc oldStru=(MaterialStructureIfc)oldMaterialStructrue.get(oldStruIte.next());
								oldStru.setMaterialStructureType("D");
								pservice.saveValueInfo(oldStru);
							}
					}
					else {
						mSplitIfc.setMaterialSplitType("N");
					}
//					System.out.println("3333333333333333333333333333======"+oldMaterialHashMap);
				
				}
				else{
//					System.out.println("zzzzzzzzzzzzzzhhhhhwaadasdhhhhhhhh"+mSplitIfc.getMaterialNumber());
					mSplitIfc.setMaterialSplitType("N");
				}
//				mSplitList.add(mSplitIfc);
				
//				System.out.println("zzzzzzzzzzzzzzhhhhhwaadasdhhhhhhhh"+mSplitIfc.getMaterialNumber());
				mSplitIfc = (MaterialSplitIfc)pservice.saveValueInfo(mSplitIfc);
				
				
			} else{
				// ��Ҫ�������ϲ�ֵ�����£����Ƚ�����·�߷ֽ�Ϊһ����·���ַ�
				//System.out.println("routeAsString==" + routeAsString);
				List routeCodeList = getRouteCodeList(routeAsString);
				
		
			
				
				// System.out.println("routeCodeListrouteCodeListww======="+routeCodeList);
				HashMap routeCodeMap = new HashMap(5);
				Vector mSplitList = new Vector();
				Vector co = new Vector();
				
				for (int m = routeCodeList.size() - 1; m >= 0; m--) {
					String routeCode = (String) routeCodeList.get(m);
					String makeCodeNameStr = "";
					String makeCodeNameStr0 = RemoteProperty
							.getProperty("com.faw_qm.cderp.routecode." + routeCode);
//					System.out.println("routeCode====="+routeCode);
//					System.out.println("makeCodeNameStr0====="+makeCodeNameStr0);
//					if(routeCode.equals("��")){
//						continue;
//					}
					if(makeCodeNameStr0==null||makeCodeNameStr0.equals("")){
						String routeCode1 = partHelper.getLXCode(routeCodeList,routeCode);
						//System.out.println("routeCode1====="+routeCode1);
						 makeCodeNameStr = RemoteProperty.getProperty("com.faw_qm.cderp.routecode." + routeCode1);
					}else{
						makeCodeNameStr = makeCodeNameStr0;
					}
					//System.out.println("makeCodeNameStr====="+makeCodeNameStr);
					// ��ȡ��ǰ��������ϼ�
					String folder = partIfc.getLocation();
			
				//	 System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn============================================"+jsbb);
					MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
					String materialNumber = "";
					// �����·�ߴ��е����һ��·�ߵ�λ
					if (makeCodeNameStr == null)
						makeCodeNameStr = "";
					    materialNumber =  partnumberold;
					// ���������ļ���ȡ�㲿������·�����һ��·�ߵ�λ�ı��뷽ʽ������������+·�ߵ�λ����ֱ������
					//��������һ�� �����򣬲���Ҫ��·�ߵ�λ
					if (m == routeCodeList.size() - 1) {
						materialNumber = materialNumber;				
						mSplitIfc.setRootFlag(true);
						
						//hamap.put(partIfc.getMasterBsoID(), mSplitIfc);
					}
					
					else {
						if(makeCodeNameStr==null||makeCodeNameStr.length()==0){
							materialNumber = materialNumber;
						}else{
							materialNumber =  materialNumber + dashDelimiter + makeCodeNameStr;	
						}
						colorFlag = false;
									
					}
					
					mSplitIfc.setMaterialNumber(materialNumber);
					mSplitIfc.setPartNumber(partnumberold);
					mSplitIfc.setPartVersion(partVersion);

					mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
					mSplitIfc.setSplited(true);
					//����ǵ�һ���ڵ㣬˵������ĩ������Ҫ�鿴��ǰ����Ƿ��й������Ӽ��������˵�������Ӽ���
					//���ò㼶״̬�������Ƿ񾭹���֣�0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
					if (m == 0) {
//						·�߽׶��޷��ж��Ƿ����Ӽ�
					    mSplitIfc.setStatus(0);
					} else {
						mSplitIfc.setStatus(1);
					}
					//�������ʶ
					//System.out.println("startvirture===============");
					 boolean virtualFlag= false;
				     if(partType.equals("����")&&(partnumber.indexOf("5000990")<0)){
				    	  
				     }else{
				    	 virtualFlag=partHelper.getJFVirtualIdentity( partIfc, routeCode, routeAssemStr,islogic,colorFlag);
				     }
//				   �ж��Ƿ��ǲɹ���
				   //  System.out.println("routeCode==============="+routeCode);
					 String cgbs = partHelper.getCgbs(routeCode);
					// boolean virtualFlag=partHelper.getJFVirtualIdentity( partIfc, routeAsString, routeAssemStr,islogic,colorFlag);
	
					
//					 System.out.println("partIfc000==============="+partIfc);
//					System.out.println("virtualFlag0000==============="+virtualFlag);
					 System.out.println("cgbs1111==============="+cgbs);
					 mapcglx.put(partIfc.getBsoID(), cgbs);
					 mSplitIfc.setVirtualFlag(virtualFlag);
					
	                //�ɹ���ʶ
					mSplitIfc.setProducedBy(cgbs);
					//��ɫ����ʶ
				    mSplitIfc.setColorFlag(colorFlag);
				    mSplitIfc.setRCode(routeCode);
					mSplitIfc.setRouteCode(routeCode);
					mSplitIfc.setRoute(routeAllCode.toString());
					//erp�ص������
				    // System.out.println("")
					
					// �����㲿������������������Ҫ������������Ϣ
					setMaterialSplit(partIfc, mSplitIfc);
					mSplitList.add(mSplitIfc);

				}

				Object[] objs = mSplitList.toArray();
				//���ñ��
				// System.out.println("mSplitList==" + mSplitList);
				for (Iterator iter = mSplitList.iterator(); iter.hasNext();) {
					// questions������㲿������·�߱仯��Ҫ���²�֣���������ĳ�������ϵ�����û�з����仯����ôҲ����Щ��������ΪU��ʶ
					MaterialSplitIfc ifc = (MaterialSplitIfc) iter.next();
//					System.out
//							.println("dsadasdasdasdasd" + ifc.getMaterialNumber());

					//System.out.println("old1111111111111111=========="+oldMaterialHashMap);
					if (oldMaterialHashMap != null && oldMaterialHashMap.size() > 0) {
//						 System.out.println("00000=========="+ifc.getMaterialNumber());
//						 System.out.println("old1111111111111111=========="+oldMaterialHashMap.get(ifc.getMaterialNumber()));
						if (oldMaterialHashMap.get(ifc.getMaterialNumber()) != null) {
							
							ifc.setMaterialSplitType("U");
							// }
							//��������� 20140624 erpҪ��ɰ汾����Ҳ�������˴�������ֻɾ����ǰ�汾�����ݡ�
							//ֻ������ǰ�汾����������
							MaterialSplitIfc mifc = (MaterialSplitIfc) oldMaterialHashMap.get(ifc.getMaterialNumber());
							String oldPartVersion = mifc.getPartVersion();
						
								pservice.deleteValueInfo(mifc);
								oldMaterialHashMap.remove(ifc.getMaterialNumber());
							
							
							// System.out.println("deleh========================"+oldMaterialHashMap);
						} else {
							ifc.setMaterialSplitType("N");
						}
					} else {
						// System.out.println("bunengba----------");
						ifc.setMaterialSplitType("N");
					}
					// ������������в�����ϵı���
					ifc = (MaterialSplitIfc) pservice.saveValueInfo(ifc);
//					System.out.println("getMaterialNumber=="
//							+ ifc.getMaterialNumber());

					// System.out.println("shenmenea ========"+ifc.getRoute());
				}
				// �˴�������,�����Ѿ�ɾ��������ô��ɾ�����
				Iterator oldMaterialIter = oldMaterialHashMap.keySet().iterator();
				while (oldMaterialIter.hasNext()) {
					String key = (String) oldMaterialIter.next();
					MaterialSplitIfc oldMaterialSplitIfc = (MaterialSplitIfc) oldMaterialHashMap
							.get(key);
					oldMaterialSplitIfc.setMaterialSplitType("D");
					// System.out.println("222222222222222222"+oldMaterialSplitIfc.getMaterialNumber());
					pservice.saveValueInfo(oldMaterialSplitIfc);
				}

				for (int p = 0; p < mSplitList.size(); p++) {
					MaterialSplitIfc parentMSIfc = (MaterialSplitIfc) mSplitList
							.get(p);
					MaterialSplitIfc childMSfc = null;
					if (p != mSplitList.size() - 1)
						childMSfc = (MaterialSplitIfc) mSplitList.get(p + 1);
					if (parentMSIfc != null) {
						MaterialStructureIfc mStructureIfc = new MaterialStructureInfo();
						//������¼�����
						if (childMSfc != null && parentMSIfc.getStatus() == 1) {
							// if(!hasSplitedStructure(parentMSIfc, childMSfc))
							// {
							logger.debug("1-���¼����ϡ�");

							mStructureIfc.setParentPartNumber(parentMSIfc
									.getPartNumber());
							mStructureIfc.setParentPartVersion(parentMSIfc
									.getPartVersion());
							mStructureIfc.setParentNumber(parentMSIfc
									.getMaterialNumber());
		                    //���ϵͳ�д��ڸ����ϵĽṹ��ϵ��ɾ���ͽṹ���½ṹ��D��ǡ�
							if (oldMaterialStructrue.get(parentMSIfc
									.getMaterialNumber()
									+ ";;;" + childMSfc.getMaterialNumber()) != null) {
								MaterialStructureIfc oldStructrue = (MaterialStructureIfc) oldMaterialStructrue
										.get(parentMSIfc.getMaterialNumber()
												+ ";;;"
												+ childMSfc.getMaterialNumber());
								mStructureIfc.setMaterialStructureType("O");
								////��������� �˴������޸�
								
								pservice.deleteValueInfo(oldStructrue);
								oldMaterialStructrue.remove(parentMSIfc
										.getMaterialNumber()
										+ ";;;" + childMSfc.getMaterialNumber());
							} else {
								mStructureIfc.setMaterialStructureType("N");
							}

							mStructureIfc.setChildNumber(childMSfc
									.getMaterialNumber());
							mStructureIfc.setQuantity(1.0F);
							mStructureIfc.setLevelNumber(String.valueOf(p));
			
							mStructureIfc
									.setDefaultUnit("��");
						
							mStructureIfc = (MaterialStructureIfc) pservice
									.saveValueInfo(mStructureIfc);
							
							// }
						} else if (parentMSIfc.getStatus() == 2)
							logger.debug("2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���");
						else if (parentMSIfc.getStatus() == 0)
							logger.debug("0-��ײ����ϡ�");
					}
				}
				//���Ͻṹ��ɾ�����
				Iterator oldStruIte = oldMaterialStructrue.keySet().iterator();
				while (oldStruIte.hasNext()) {

					MaterialStructureIfc oldStru = (MaterialStructureIfc) oldMaterialStructrue
							.get(oldStruIte.next());

					oldStru.setMaterialStructureType("D");
					pservice.saveValueInfo(oldStru);
					// System.out.println("------------------------------------sssssssss--------------"+oldStru.get);
				}

			}
			
		}
		if (logger.isDebugEnabled())
			logger.debug("split(String, boolean) - end");
		// 20090422��Ϊ��ʹ���ڷ�����ʱ��֪�������·������϶�����Щ����Ҫ������ѹ��˺������嵥����һ��
		//filterPartMap.addAll(filterPartMap1);
		return filterPartMap;
	}
	
	
	public Collection getPartByRouteList(TechnicsRouteListIfc list)
	throws QMException
	{
		  System.out.println("list---------="+list);
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		Collection c = new Vector();
		QMQuery query = new QMQuery("ListRoutePartLink");
		QueryCondition cond = new QueryCondition("leftBsoID", "=", list.getBsoID());
		query.addCondition(cond);
		Collection coll = ps.findValueInfo(query);
		// System.out.println("coll---------="+coll);
//		���������ͼ���°汾
        PartConfigSpecIfc configSpecGY = PartHelper.getPartConfigSpecByViewName("������ͼ");
		for(Iterator iter = coll.iterator(); iter.hasNext();)
		{
			ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)iter.next();
			Collection cc = getPartByListRoutepart(linkInfo);
			QMPartIfc partInfo = null;
			for(Iterator ii = cc.iterator(); ii.hasNext(); c.add(partInfo))
			{//CCBegin SS1
				QMPartIfc partInfo_old = (QMPartIfc)ii.next();
				//System.out.println("partInfo_old="+partInfo_old);
	            partInfo = PartServiceRequest.getPartByConfigSpec((QMPartMasterIfc)partInfo_old.getMaster() , configSpecGY);
	           // System.out.println("partInfo="+partInfo);
			}//CCEnd SS1
				
			

		}
	//	System.out.println("c="+c);
		return c;
	}
	
	

	private Collection getPartByListRoutepart(ListRoutePartLinkInfo linkInfo)
	throws QMException
	{
		Vector coll = new Vector();
		try{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
	    String partBsoid = linkInfo.getRightBsoID();
		
		QMPartIfc part = (QMPartIfc) ps.refreshInfo(partBsoid);
		 
		coll.add(part);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
		return coll;
	}
	//������ֱ���Ӽ�����1000ʱ���������ݲ�ȫ��
	private Collection getSubParts(Collection coll, ManagedBaselineIfc baseline)
	throws QMException
	{
		try
		{
			Collection coll1;
			   PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
			   String masterids[] = new String[coll.size()];
			   Object objs[] = coll.toArray();
//			   System.out.println("coll==============="+coll);
//			   System.out.println("objsobjsobjs==============="+objs.length);
//			   System.out.println("baselinebaseline000000==============="+baseline.getBsoID());
			   for(int i = 0; i < objs.length; i++)
			   {
			    if(objs[i] instanceof QMPartIfc)
			    {
			     QMPartIfc part = (QMPartIfc)objs[i];
			     masterids[i] = part.getBsoID();
//			     System.out.println("part.getBsoID()part.getBsoID()==111111111111111============="+part.getBsoID());
			    }
			    if(objs[i] instanceof String)
			    {
			    	
			     String partid = (String)objs[i];
			     QMPartIfc part = (QMPartIfc)pservice.refreshInfo(partid);
//			     System.out.println("part.getBsoID()part.getBsoID()==2222222222222222222222222222222============="+part.getBsoID());
			     String masterid = part.getBsoID();
			     masterids[i] = masterid;
			    }
			   }
//			   System.out.println("sssssssssssssssssssssss"+masterids);
			   int a=(int)masterids.length/500;
			   int b=masterids.length%500;
			   if(b>0)
			   {
			    a=a+1;
			   }
			   Vector vec=new Vector();
			   for(int j=0;j<a;j++)
			   {
			    String[] mas;
			    if(j!=a-1)
			    {
			    	
			     mas=new String[500];
//			     System.out.println("masmasmas1111111111111"+mas);
			    }
			    else if(j==a-1 && b>0)
			    {
			     mas=new String[b];
//			     System.out.println("masmasmas2222222222222222"+mas);
			    }
			    else
			    {
			     mas=new String[500];
//			     System.out.println("masmasmas3333333333333333"+mas);
			    }
//			    System.out.println("masmasmas44444444444444444444"+mas.length);
			    for(int k=0;k<mas.length;k++)
			    {
			     mas[k]=masterids[j*500+k];
			    }
			    vec.add(mas);
			   }
			   QMQuery query = new QMQuery("QMPart");
			   int i = query.appendBso("PartUsageLink", false);
			   int j = query.appendBso("BaselineLink", false);
			   int m = query.appendBso("QMPartMaster", false);
			   //query.addCondition(i, new QueryCondition("rightBsoID", "IN", masterids));
			   query.addCondition(m, i, new QueryCondition("bsoID", "leftBsoID"));
			   query.addAND();
			   query.addCondition(j, new QueryCondition("rightBsoID", "=", baseline.getBsoID()));
			   query.addAND();
			   query.addCondition(0, j, new QueryCondition("bsoID", "leftBsoID"));
			   query.addAND();
			   query.addCondition(0, m, new QueryCondition("masterBsoID", "bsoID"));
			   query.addAND();
			   query.addLeftParentheses();
//			   System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwww==============="+vec.size());
			   for(int k=0;k<vec.size();k++)
			   {
				   
			    String[] mast=(String[])vec.elementAt(k);
			    query.addCondition(i, new QueryCondition("rightBsoID", "IN", mast));
			    if(k!=vec.size()-1)
			    {
			     query.addOR();
			    }
			   }
			   query.addRightParentheses();
//			   System.out.println("queryquery========="+query.getDebugSQL());
			   coll1 = pservice.findValueInfo(query);
			   return coll1;

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	private Collection getSubPartsErp(Collection coll)
	throws QMException
	{
		try
		{
			Collection coll1;
			   PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
			   String masterids[] = new String[coll.size()];
			   Object objs[] = coll.toArray();
			   
			   for(int i = 0; i < objs.length; i++)
			   {
			    if(objs[i] instanceof QMPartIfc)
			    {
			     QMPartIfc part = (QMPartIfc)objs[i];
			     masterids[i] = part.getBsoID();
			    }
			    if(objs[i] instanceof String)
			    {
			     String partid = (String)objs[i];
			     QMPartIfc part = (QMPartIfc)pservice.refreshInfo(partid);
			     String masterid = part.getBsoID();
			     masterids[i] = masterid;
			    }
			   }
			   
			   int a=(int)masterids.length/500;
			   int b=masterids.length%500;
			   if(b>0)
			   {
			    a=a+1;
			   }
			   Vector vec=new Vector();
			   for(int j=0;j<a;j++)
			   {
			    String[] mas;
			    if(j!=a-1)
			    {
			     mas=new String[500];
			    }
			    else if(j==a-1 && b>0)
			    {
			     mas=new String[b];
			    }
			    else
			    {
			     mas=new String[500];
			    }
			    for(int k=0;k<mas.length;k++)
			    {
			     mas[k]=masterids[j*500+k];
			    }
			    vec.add(mas);
			   }
			   QMQuery query = new QMQuery("QMPart");
			   int i = query.appendBso("PartUsageLink", false);
			   int m = query.appendBso("QMPartMaster", false);
			   query.addCondition(m, i, new QueryCondition("bsoID", "leftBsoID"));
			   query.addAND();
			   query.addCondition(0, m, new QueryCondition("masterBsoID", "bsoID"));
			   query.addAND();
			   query.addLeftParentheses();
			   for(int k=0;k<vec.size();k++)
			   {
			    String[] mast=(String[])vec.elementAt(k);
			    query.addCondition(i, new QueryCondition("rightBsoID", "IN", mast));
			    if(k!=vec.size()-1)
			    {
			     query.addOR();
			    }
			   }
			   query.addRightParentheses();
			   coll1 = pservice.findValueInfo(query);
//			   System.out.println("coll1coll1coll1============"+coll1);
			   return coll1;

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	private Collection getAllSubParts(Collection partIDs, ManagedBaselineIfc baseline)
	throws QMException
	{
		try
		{
			Collection collection;
			Vector resultVector;
			collection = getSubParts(partIDs, baseline);
			resultVector = new Vector();
			if(collection == null || collection.size() == 0)
				return resultVector;
			resultVector.addAll(collection);
			Vector tempVector = new Vector();
			tempVector = productStructure(collection, baseline);
			resultVector.addAll(tempVector);
			return resultVector;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	private Collection getAllSubPartsErp(Collection partIDs)
	throws QMException
	{
		try
		{
			Collection collection;
			Vector resultVector;
			collection = getSubPartsErp(partIDs);
			resultVector = new Vector();
			if(collection == null || collection.size() == 0)
				return resultVector;
			resultVector.addAll(collection);
			Vector tempVector = new Vector();
			tempVector = productStructureErp(collection);
			resultVector.addAll(tempVector);
			return resultVector;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	private Vector productStructure(Collection coll, ManagedBaselineIfc baseline)
	throws QMException
	{
		Vector resultVector = new Vector();
		Collection collection = getSubParts(coll, baseline);
		if(collection == null || collection.size() < 1)
		{
			return resultVector;
		} else
		{
			resultVector.addAll(collection);
			Vector tempVector = new Vector();
			tempVector = productStructure(collection, baseline);
			resultVector.addAll(tempVector);
			return resultVector;
		}
	}
	private Vector productStructureErp(Collection coll)
	throws QMException
	{
		Vector resultVector = new Vector();
		Collection collection = getSubPartsErp(coll);
		if(collection == null || collection.size() < 1)
		{
			return resultVector;
		} else
		{
			resultVector.addAll(collection);
			Vector tempVector = new Vector();
			tempVector = productStructureErp(collection);
			resultVector.addAll(tempVector);
			return resultVector;
		}
	}



	private final Vector filterParts(Collection parts)
	throws QMException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("filterParts(String) - start");
			logger.debug("������partBsoIDs==" + parts);
		}
		List routeList = new ArrayList();
		logger.debug("������routeList==" + routeList);
		logger.debug("������routeList.size()==" + routeList.size());
		logger.debug("Ҫ���˵��㲿���ļ���==" + parts);
		Object lalatestPartBsoIDs[] = parts.toArray();
//		��ͬһ�������е��ظ���ȡ��
		List filterPartList = filterByIdentity(lalatestPartBsoIDs);
//		System.out.println("100304++++++++++++++++++++++=======jinru===filterBySplitRule======== ");
		
		filterBySplitRule(filterPartList);
//		System.out.println("100304++++++++++++++++++++++=======chuqu===filterBySplitRule======== ");
		Vector filterPartMap = new Vector();
		for(int i = 0; i < filterPartList.size(); i++)
		{
			QMPartIfc filterPartIfc = (QMPartIfc)filterPartList.get(i);
			filterPartMap.add(filterPartIfc.getBsoID());
		}

		if(logger.isDebugEnabled())
			logger.debug("filterParts(String) - end     " + filterPartMap);
		return filterPartMap;
	}
	private final void filterBySplitRule(final List filterPartList )
	 throws QMException 
	 {
	  if (logger.isDebugEnabled()) 
	  {
	   logger.debug("filterBySplitRule(List) - start"); //$NON-NLS-1$
	   logger.debug("������filterPartList==" + filterPartList);
	  }
	  PartHelper partHelper = new PartHelper();
	  PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
	  // �����ֵ��㲿�����ϡ�
	  final List removePartList = new ArrayList();
//	  System.out.println("100304++++++++filterPartList"+filterPartList.size()+"kkkkkkkkkkjjjjjjjjjjjj==="+filterPartList);
	  for (int i = 0; i < filterPartList.size(); i++) 
	  {
//		  System.out.println("daodijiciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===================================================");
	   MaterialSplitIfc materialSplitIfc = null;
	   final QMPartIfc partIfc = (QMPartIfc) filterPartList.get(i);
		 String partVersion ="";
	
		 partVersion = PartHelper.getPartVersion(partIfc);
		 

	   List mSplitList = new ArrayList();
	   try {
	    // ��������Ż�ȡ�Ѳ�ֵ����ϡ�
	    mSplitList = getSplitedMSplit(partIfc.getPartNumber(),partVersion);
	   } catch (QMException e) 
	   {
	    Object[] aobj = new Object[] { partIfc.getPartNumber() };
	    // "���ұ��Ϊ*���㲿����Ӧ������ʱ����"
	    logger.error(Messages.getString("Util.51", aobj) + e);
	    throw new ERPException(e, RESOURCE, "Util.51", aobj);
	   }
	   // ��ͬΨһ��ʶ������ֻ����һ����
	   HashMap maHashMap = new HashMap(5);
//	   System.out.println("100304++++++++mSplitList.size()"+mSplitList.size()+"============pppp"+mSplitList);
	   for (int k = 0; k < mSplitList.size(); k++) 
	   {
	    MaterialSplitIfc ma = (MaterialSplitIfc) mSplitList.get(k);
	    String objectIdectity = getObjectIdentity(ma);
//	    System.out.println("100304++++++++maHashMamaHashMap"+maHashMap);
//	    System.out.println("100304++++++++objectIdectityobjectIdectity"+objectIdectity);
	    if (!maHashMap.containsKey(objectIdectity)) 
	    {
//	    	System.out.println("100304++++++++fffffffffffffffffffffffffffffffffffffffffffffffffffffffp");
	     maHashMap.put(objectIdectity, ma);
	     
	    }
	   }
	   Object[] materialSplitIfcs = maHashMap.values().toArray();
	   // �Ƿ���Ҫ��ִ������1.�Ƚ��µ���Ҫ��ֵ��㲿�������ϲ�ֱ���Ķ�Ӧ�����ϵİ汾�������Ҫ��ֵ��㲿���İ汾�������ϲ�ֱ���ļ�¼�����ٲ�֡�
	   MaterialSplitIfc tempMaterialSplit = null;
	   if (materialSplitIfcs != null && materialSplitIfcs.length > 0) 
	   {
		   
	    if (logger.isDebugEnabled()) {
	     logger
	     .debug("---------�Ƿ���Ҫ��ִ������1.�Ƚ��µ���Ҫ��ֵ��㲿�������ϲ�ֱ���Ķ�Ӧ�����ϵİ汾�������Ҫ��ֵ��㲿���İ汾�������ϲ�ֱ���ļ�¼�����ٲ�֡�");
	    }
//	    System.out.println("100304++++++++materialSplitIfcs.lengthmaterialSplitIfcs.length"+materialSplitIfcs.length);
	    for (int j = 0; j < materialSplitIfcs.length; j++) 
	    {
	     materialSplitIfc = (MaterialSplitIfc) materialSplitIfcs[j];
	     if (materialSplitIfc.getPartVersion() != null) 
	     {
//	      if (partIfc.getVersionID().compareTo(
//	        materialSplitIfc.getPartVersion()) < 0) 
    	 int compare = partHelper.compareVersion(partIfc.getVersionID(), materialSplitIfc.getPartVersion());
          if(compare==2)
	      {
//	    	  System.out.println("100304++++++++0000000000-------------"+materialSplitIfcs.length);
	       removePartList.add(partIfc);
	       break;
	      } else 
	      {
	       if (tempMaterialSplit == null) 
	       {
	        tempMaterialSplit = materialSplitIfc;
//	        System.out.println("100304++++++++11111111111111-------------"+materialSplitIfcs.length);
	       } else if (partHelper.compareVersion(tempMaterialSplit.getPartVersion(),materialSplitIfc.getPartVersion())==2)
	       {
//	    	   System.out.println("100304++++++++22222222222222-------------"+materialSplitIfcs.length);
	        tempMaterialSplit = materialSplitIfc;
	       }
	      }
	     }
	    }
	   }
	   if (logger.isDebugEnabled()) 
	   {
	    logger.debug("partIfc.getPartNumber()=="
	      + partIfc.getPartNumber());
	   }
	   // �Ƿ���Ҫ��ִ������2.�������ݲ����ڣ���Ҫ��֡�
	   if (tempMaterialSplit == null) 
	   {
//		   System.out.println("100304++++++++33333333333333333-------------"+materialSplitIfcs.length);
	    if (logger.isDebugEnabled()) 
	    {
	     logger
	     .debug("---------�Ƿ���Ҫ��ִ������2.�������ݲ����ڣ���Ҫ��֡���汾�Ǿɵģ������֡�");
	    }
	    
	   }
	   // �Ƿ���Ҫ��ִ������3���������ݴ��ڣ����汾��
	   else 
	   {
	    if (logger.isDebugEnabled()) 
	    {
	     logger.debug("---------�Ƿ���Ҫ��ִ������3���������ݴ��ڣ����汾��");
	    }
	    // �Ƿ���Ҫ��ִ������3.b���汾û�б仯��
	    if (partIfc.getVersionID().equals(
	      tempMaterialSplit.getPartVersion())) 
	    {
	     if (logger.isDebugEnabled()) 
	     {
	      logger.debug("---------�Ƿ���Ҫ��ִ������3.b���汾û�б仯��");
	     }
	     // �Ƿ���Ҫ��ִ������3.b.2��״̬û�б仯�������˼�¼��
	     if (partIfc.getLifeCycleState().getDisplay().equals(
	       tempMaterialSplit.getState())) 
	     {
//	      Vector routevec = RequestHelper.getRouteBranchs(partIfc, null);
//	      String routeAsString = "";
//	      String routeAllCode = "";
//	      String routeAssemStr = "";
//	      if(routevec.size() != 0)
//	      {
//	       routeAsString = (String)routevec.elementAt(0);
//	       routeAllCode = (String)routevec.elementAt(1);
//	       routeAssemStr= (String)routevec.elementAt(2);
//	      }
//	      boolean flag1=false;
//	      boolean flag2=false;
//	      if(routeAllCode==null || routeAllCode=="")
//	      {
//	       flag1=true;
//	      }
//	      if(tempMaterialSplit.getRoute()==null || tempMaterialSplit.getRoute()=="")
//	      {
//	       flag2=true;
//	      }
//	      System.out.println("100304++++++++flag1flag1flag1flag1flag1-------------"+flag1);
//	      System.out.println("100304++++++++flag2flag2flag2flag2flag2-------------"+flag2);
//	      if(flag1&&flag2)
//	      {
////	    	  System.out.println("100304++++++++flag0000000000000000000000000000-------------");
//	       removePartList.add(partIfc);
//	      }
//	      if(!flag1 && !flag2)
	      {
//	    	  System.out.println("100304++++++++flag11111111111111111111111111111111-------------");
	    	 System.out.println("100304++++++++flag11111111111111111111111-------------"); 
	       if(partIfc.getLifeCycleState().getDisplay().equals(tempMaterialSplit.getState()))
	       {
	    	   System.out.println("100304++++++++flag222222222222222222222222222222222222-------------");  
	    	 //������20140609,�������������仯��Ҳ��Ҫ���
	    	   String xnjMaterial = "N";
	    	   String xnj ="";
	    	   if(xnj==null){
	    		   xnj = "N";
	    	   }
				 boolean virtualFlag1=tempMaterialSplit.getVirtualFlag();
				 if(virtualFlag1){
					 xnjMaterial = "Y";
				 }
				 System.out.println("100304++++++++flag222222222222222222222222222222222222-------------xnf"+xnj);  

				 System.out.println("100304++++++++flag222222222222222222222222222222222222-------------xnjMaterial"+xnjMaterial);  
				 if(xnj.equals(xnjMaterial)){
					 removePartList.add(partIfc);
				 }
	        
	       } else
	       {
//	    	   System.out.println("100304++++++++mSplitList.size()mSplitList.size()-------------"+mSplitList.size());
	        for(int k = 0; k < mSplitList.size(); k++)
	        {
	         MaterialSplitIfc mSplitIfc = (MaterialSplitIfc)mSplitList.get(k);
//	         System.out.println("100304++++++++flag333333333333333333333-------------"+mSplitIfc.getRoute());
//	         System.out.println("100304++++++++flag9999999-------------"+tempMaterialSplit.getRoute());
//	         System.out.println("100304++++++++flaghhhhhhhhhhhhhhhhhh-------------"+mSplitIfc.getState());
//	         System.out.println("100304++++++++oooooooooooooooiiiiiii-------------"+routeAllCode);
//	         System.out.println("0610 ++++++++zzzzzzzz-------------"+mSplitIfc.getRoute().equals(routeAllCode));
	         //CCBegin by chudaming 20090304
	         if(mSplitIfc.getState().equals(tempMaterialSplit.getState()))
	        	 //CCEnd by chudaming 20090304
	         {
//	        	 System.out.println("100304++++++++flag444444444444444444444-------------");
		          mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
		          setMaterialSplit(partIfc, mSplitIfc);
		          mSplitIfc = (MaterialSplitIfc)pservice.saveValueInfo(mSplitIfc);
		          removePartList.add(partIfc);
//		          System.out.println("100304++++++++flag5555555555555-------------");
	         }
	        }
	 
	       }
	      } 
	      
	     }
	     // �Ƿ���Ҫ��ִ������3.a�汾�仯����Ҫ��֣�
	     else 
	     {
	      if (logger.isDebugEnabled()) 
	      {
	       logger.debug("---------�Ƿ���Ҫ��ִ������3.a�汾�仯����Ҫ��֣�");
	      }
	     }
	    }
	   }
	   // ��δ�ı���㲿����ֻ�ı�״̬���㲿���Ӳ���б��г�ȥ��
//	   System.out.println("100304++++++++flag66666666666666-------------"+removePartList);
	   //CCBegin by chudaming 20100609  dele.ѭ����remove�ģ�ʵ���Ͼ�ɾ����filterPartList����λ������
	   //filterPartList.removeAll(removePartList);
//	   System.out.println("100304++++++++flag7777777777777777777-------------"+removePartList);
	   if (logger.isDebugEnabled()) {
	    logger.debug("filterBySplitRule(List) - end"); //$NON-NLS-1$
	   }
	  }
	  //CCBegin by chudaming 20100609 ѭ����remove�ģ�ʵ���Ͼ�ɾ����filterPartList����λ������
	  filterPartList.removeAll(removePartList);
	  //CCEnd by chudaming 20100609
	 } 
	
	private final List filterByIdentity(final Object[] partBsoIDs)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - start"); //$NON-NLS-1$
			logger.debug("������parts==" + partBsoIDs);
		}
		PersistService pservice =(PersistService)EJBServiceHelper.getService("PersistService");
		final List filterPartList = new ArrayList();
		// ���ͨ��Ψһ��ʶ���˺���㲿����
		final HashMap tempPartMap = new HashMap();
		for (int i = 0; i < partBsoIDs.length; i++) {
			
			QMPartIfc partIfc=null;
			if(partBsoIDs[i] instanceof String)
			{
				partIfc= (QMPartIfc)  pservice.refreshInfo((String)partBsoIDs[i]);
			}
			else if(partBsoIDs[i] instanceof BaseValueIfc)
			{
				partIfc=(QMPartIfc)pservice.refreshInfo((BaseValueIfc)partBsoIDs[i]);
			}
			String partIdentity = getObjectIdentity(partIfc);
			// �����㲿����Ψһ��ʶ�����㲿�����ϣ��ظ��ļ�¼�����˵������ٲ�֡�
			if (!tempPartMap.containsKey(partIdentity)) {
				tempPartMap.put(partIdentity, partIfc);
				filterPartList.add(partIfc);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - end      " + filterPartList); //$NON-NLS-1$
		}
		return filterPartList;
	}
	private final List filterByIdentity1(final Object[] partBsoIDs,String partroute)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - start"); //$NON-NLS-1$
			logger.debug("������parts==" + partBsoIDs);
		}
		PersistService pservice =(PersistService)EJBServiceHelper.getService("PersistService");
		final List filterPartList = new ArrayList();
		// ���ͨ��Ψһ��ʶ���˺���㲿����
		final HashMap tempPartMap = new HashMap();
		for (int i = 0; i < partBsoIDs.length; i++) {
			
			QMPartIfc partIfc=null;
			if(partBsoIDs[i] instanceof String)
			{
				partIfc= (QMPartIfc)  pservice.refreshInfo((String)partBsoIDs[i]);
			}
			else if(partBsoIDs[i] instanceof BaseValueIfc)
			{
				partIfc=(QMPartIfc)pservice.refreshInfo((BaseValueIfc)partBsoIDs[i]);
			}
			String partIdentity = getObjectIdentity1(partIfc,partroute);
			// �����㲿����Ψһ��ʶ�����㲿�����ϣ��ظ��ļ�¼�����˵������ٲ�֡�
			if (!tempPartMap.containsKey(partIdentity)) {
				tempPartMap.put(partIdentity, partIfc);
				filterPartList.add(partIfc);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - end      " + filterPartList); //$NON-NLS-1$
		}
		return filterPartList;
	}
	
	private final String getObjectIdentity(final BaseValueIfc baseValueIfc) throws QMException{
		if(logger.isDebugEnabled())
		{
			logger.debug("getObjectIdentity(BaseValueIfc) - start");
			logger.debug("\u53C2\u6570\uFF1AbaseValueIfc==" + baseValueIfc);
		}
		String returnString = "";
		//������20140609
		String xnj = "N";
		if(baseValueIfc instanceof QMPartIfc)
		{
			PartHelper partHelper = new PartHelper();
//			Vector routevec = RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);
//			String routeAsString = "";
//			String routeAllCode = "";
//			String routeAssemStr = "";
//			if(routevec.size() != 0)
//			{
//				routeAsString = (String)routevec.elementAt(0);
//				routeAllCode = (String)routevec.elementAt(1);
//				routeAssemStr = (String)routevec.elementAt(2);
//			}
//			
			QMPartIfc part = (QMPartIfc)baseValueIfc;
			String partVersion = PartHelper.getPartVersion(part);
			
			String partnumber = part.getPartNumber() + "/" + partVersion;
			//���������20140609
			// xnj = PartHelper.getPartIBA(part, "�����","virtualPart");
			 if(xnj==null){
				 xnj = "N";
			 }
			 //���+�汾+��������״̬+·��+�����
			returnString = partnumber + part.getLifeCycleState().getDisplay()  + xnj;
//			System.out.println("returnStringreturnString100304========"+returnString);
		} else
			if(baseValueIfc instanceof MaterialSplitIfc)
			{
				MaterialSplitIfc materialSplitIfc = (MaterialSplitIfc)baseValueIfc;
				//������20140609
				 if( materialSplitIfc.getVirtualFlag()){
					 xnj = "Y";
				 }
				returnString = materialSplitIfc.getPartNumber()  + materialSplitIfc.getState()  + xnj;;
			}
		if(logger.isDebugEnabled())
			logger.debug("getObjectIdentity(BaseValueIfc) - end     " + returnString);
		return returnString;
	}
	private final String getObjectIdentity1(final BaseValueIfc baseValueIfc,String partroute) throws QMException{
		if(logger.isDebugEnabled())
		{
			logger.debug("getObjectIdentity(BaseValueIfc) - start");
			logger.debug("\u53C2\u6570\uFF1AbaseValueIfc==" + baseValueIfc);
		}
		String returnString = "";
		if(baseValueIfc instanceof QMPartIfc)
		{
//			Vector routevec = RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);
//			String routeAsString = "";
			String routeAllCode = "";
//			String routeAssemStr = "";
//			if(routevec.size() != 0)
//			{
//				routeAsString = (String)routevec.elementAt(0);
				routeAllCode = partroute;
//			}
			QMPartIfc part = (QMPartIfc)baseValueIfc;
			returnString = part.getPartNumber() + part.getVersionID() + part.getLifeCycleState().getDisplay() + routeAllCode;
//			System.out.println("returnStringreturnString100304========"+returnString);
		} else
			if(baseValueIfc instanceof MaterialSplitIfc)
			{
				MaterialSplitIfc materialSplitIfc = (MaterialSplitIfc)baseValueIfc;
				returnString = materialSplitIfc.getPartNumber() + materialSplitIfc.getPartVersion() + materialSplitIfc.getState() + materialSplitIfc.getRoute();
			}
		if(logger.isDebugEnabled())
			logger.debug("getObjectIdentity(BaseValueIfc) - end     " + returnString);
		return returnString;
	}
	/**
	 * ִ�����ϲ��ǰ�Ĺ����㲿�����ܡ��õ����ϲ������������Ҫ���в�ִ�����㲿�����ϡ�
	 * 1.�ȸ����㲿��Ψһ��ʶ��������㲿����Ψһ��ʶΪ�����+��汾��+��������״̬���ظ��ļ�¼�������������ٲ�֡�
	 * 2.�����Ƿ���Ҫ��ֹ������㲿���� �Ƿ���Ҫ��ִ�����򣺲�ѯ���ϲ�ֱ������ݡ�
	 * 1.�Ƚ���Ҫ��ֵ��㲿�������ϲ�ֱ����Ӧ�����ϵİ汾�������Ҫ��ֵ��㲿���İ汾�������ϲ�ֱ�������ݣ����ٲ�֣��������㲿����
	 * 2.�������ݲ����ڣ���Ҫ��֡� 3.�������ݴ��ڣ����汾��a�汾�仯����Ҫ��֣�
	 * b�汾û�б仯��1״̬�仯���ҵ����ϲ�ֱ�����Ӧ���ݣ��滻���ԣ�����֣� 2״̬û�б仯�������˼�¼��
	 * @param parts
	 *            ����㲿���ļ���
	 * @return �������˺󣬷��ϲ������������Ҫ���в�ִ�����㲿�����ϡ�
	 * @throws QMException
	 */
	
	private final Vector filterParts(Collection parts,HashMap hashMap)
	throws QMException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("filterParts(String) - start");
			logger.debug("������partBsoIDs==" + parts);
		}
		List routeList = new ArrayList();
		logger.debug("������routeList==" + routeList);
		logger.debug("������routeList.size()==" + routeList.size());
		logger.debug("Ҫ���˵��㲿���ļ���==" + parts);
		Object lalatestPartBsoIDs[] = parts.toArray();
//		��ͬһ�������е��ظ���ȡ��
		List filterPartList = filterByIdentity(lalatestPartBsoIDs,hashMap);
	//	System.out.println("100304++++++++++++++++++++++=======jinru===filterBySplitRule======== "+filterPartList);

		filterBySplitRule(filterPartList,hashMap);
		
		
		//System.out.println("100304++++++++++++++++++++++=======chuqu===filterBySplitRule======== "+filterPartList);
		Vector filterPartMap = new Vector();
		for(int i = 0; i < filterPartList.size(); i++)
		{
			QMPartIfc filterPartIfc = (QMPartIfc)filterPartList.get(i);
			filterPartMap.add(filterPartIfc.getBsoID());
		}

		if(logger.isDebugEnabled())
			logger.debug("filterParts(String) - end     " + filterPartMap);
//		System.out.println("kuleeeeeeeeeeeeeeeeeeee++++++++++++++++++++++==== "+filterPartMap);
		return filterPartMap;
	}




	/**
	 * �滻���ϲ�ֱ���İ汾���汾��Ϊ��ʱ���滻�°汾��
	 * 
	 * @param partIfc
	 *            �²�ֵ��㲿����
	 * @throws QMException
	 */
	private void replaceMaterialSplit(QMPartIfc partIfc) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("replaceMaterialSplit(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("������partIfc==" + partIfc);
		}
		PartHelper partHelper = new PartHelper();
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		List mSplitList = getAllMSplit(partIfc.getPartNumber());
		if (mSplitList != null && mSplitList.size() > 0) {
			MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) mSplitList.get(0);
			////
//		if (mSplitIfc.getPartVersion() != null
//					&& partIfc.getVersionID().compareTo(
//							mSplitIfc.getPartVersion()) > 0) 
		  int compare = partHelper.compareVersion(partIfc.getVersionID(),mSplitIfc.getPartVersion());
		  if(mSplitIfc.getPartVersion() != null){
			  if(compare==1)
				{
					Iterator iter = mSplitList.iterator();
					while (iter.hasNext()) {
						mSplitIfc = (MaterialSplitIfc) iter.next();
						mSplitIfc.setPartVersion(partIfc.getVersionID());
						mSplitIfc = (MaterialSplitIfc) pservice.saveValueInfo(mSplitIfc);
					}
				}
		  }
         
		}
		if (logger.isDebugEnabled()) {
			logger.debug("replaceMaterialSplit(QMPartIfc) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * �滻���Ͻṹ����İ汾���汾��Ϊ��ʱ���滻�°汾��
	 * 
	 * @param partIfc
	 *            �²�ֵ��㲿����
	 * @throws QMException
	 */
	private void replaceMaterialStructure(QMPartIfc partIfc) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("replaceMaterialStructure(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("������partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		// ����ָ���ĸ������������ҵ��ṹ�������з��������Ľṹ��Ϣ��
		List mStructureList = getMStructure(partIfc.getPartNumber());
		if (mStructureList != null && mStructureList.size() > 0) {
			MaterialStructureIfc mStructureIfc = (MaterialStructureIfc) mStructureList
			.get(0);
			if (mStructureIfc.getParentPartVersion() != null
					&& partIfc.getVersionID().compareTo(
							mStructureIfc.getParentPartVersion()) > 0) {
				Iterator iter = mStructureList.iterator();
				while (iter.hasNext()) {
					mStructureIfc = (MaterialStructureIfc) iter.next();
					mStructureIfc.setParentPartVersion(partIfc.getVersionID());
					mStructureIfc = (MaterialStructureIfc)pservice.saveValueInfo(mStructureIfc);
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("replaceMaterialStructure(QMPartIfc) - end"); //$NON-NLS-1$
		}
	}

	/**
	 * ɾ���㲿����Ӧ�����ϲ�ֱ����Ѿ����ڵľɰ汾�����ϲ�ֶ���
	 * 
	 * @param partIfc
	 *            �㲿����
	 * @return ɾ�����ľɰ汾�����϶�Ӧ�ļ�¼���ϡ�
	 * @throws QMException
	 */
	private List deleteMaterialSplit(QMPartIfc partIfc) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialSplit(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("������partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		List mSplitList = getAllMSplit(partIfc.getPartNumber());
		for (int i = 0; i < mSplitList.size(); i++) {
			pservice.deleteValueInfo((BaseValueIfc)mSplitList.get(i));
		}
		if (logger.isDebugEnabled()) {
			logger
			.debug("deleteMaterialSplit(QMPartIfc) - end      " + mSplitList); //$NON-NLS-1$
		}
		return mSplitList;
	}
	/**
	 * ɾ���㲿����Ӧ�����ϲ�ֱ����Ѿ����ڵľɰ汾�����ϲ�ֶ���
	 * added by dikefeng 20100419
	 * @param partIfc
	 *            �㲿����
	 * @return ɾ�����ľɰ汾�����϶�Ӧ�ļ�¼���ϡ�
	 * @throws QMException
	 */
	private List deleteMaterialSplit(QMPartIfc partIfc,boolean flag,String partNumber) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialSplit(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("������partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		final QMQuery query = new QMQuery("CDMaterialSplit");
		QueryCondition condition = new QueryCondition("partNumber",
				QueryCondition.EQUAL, partNumber);
		query.addCondition(condition);
		if(flag)
		{
			query.addAND();
			query.addCondition(new QueryCondition("materialSplitType","=","D"));
		}
		List mSplitList=(List)pservice.findValueInfo(query);
		for (int i = 0; i < mSplitList.size(); i++) {
			pservice.deleteValueInfo((BaseValueIfc)mSplitList.get(i));
		}
		if (logger.isDebugEnabled()) {
			logger
			.debug("deleteMaterialSplit(QMPartIfc) - end      " + mSplitList); //$NON-NLS-1$
		}
		return mSplitList;
	}
	/**
	 * ɾ���㲿����Ӧ�����Ͻṹ�����Ѿ����ڵľɰ汾�����Ͻṹ��ϵ��
	 * @param partIfc
	 *            �㲿����
	 * @throws QMException
	 */
	private void deleteMaterialStructure(QMPartIfc partIfc,boolean flag,String partNumber) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialStructure(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("������partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("CDMaterialStructure");
		QueryCondition condition1 = new QueryCondition("parentPartNumber", "=",
				partNumber);
		query.addCondition(condition1);
		if(flag)
		{
			query.addAND();
			query.addCondition(new QueryCondition("materialStructureType","=","D"));
		}
		List mStructureList=(List)pservice.findValueInfo(query);
		// ����ָ���ĸ������������ҵ��ṹ�������з��������Ľṹ��Ϣ��
		for (int i = 0; i < mStructureList.size(); i++) {
			pservice.deleteValueInfo((BaseValueIfc)mStructureList.get(i));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialStructure(QMPartIfc) - end"); //$NON-NLS-1$
		}
	}
	/**
	 * ɾ���㲿����Ӧ�����Ͻṹ�����Ѿ����ڵľɰ汾�����Ͻṹ��ϵ��
	 * 
	 * @param partIfc
	 *            �㲿����
	 * @throws QMException
	 */
	private void deleteMaterialStructure(QMPartIfc partIfc) throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialStructure(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("������partIfc==" + partIfc);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		// ����ָ���ĸ������������ҵ��ṹ�������з��������Ľṹ��Ϣ��
		List mStructureList = getMStructure(partIfc.getPartNumber());
		for (int i = 0; i < mStructureList.size(); i++) {
			pservice.deleteValueInfo((BaseValueIfc)mStructureList.get(i));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("deleteMaterialStructure(QMPartIfc) - end"); //$NON-NLS-1$
		}
	}
	/**
	 * ���ݸ����Ϻ������ϣ��ж����Ϲҽ�ԭ�㲿�����߼��Ƿ�ִ�й���
	 * 
	 * @param parentMSIfc
	 *            �����ϡ�
	 * @param childMSIfc
	 *            �����ϡ�
	 * @return ��ʶ��
	 * @throws QMException
	 */
	private boolean hasSplitedStructure(MaterialSplitIfc parentMSIfc,
			MaterialSplitIfc childMSIfc) throws QMException {
		if (logger.isDebugEnabled()) {
			logger
			.debug("hasSplitedStructure(MaterialSplitIfc, MaterialSplitIfc) - start"); //$NON-NLS-1$
		}
		// ����ָ���ĸ����ź͸����Ϻ��������ҵ��ṹ�������з��������Ľṹ��Ϣ��
		List mStructureList = getMStructure(parentMSIfc.getPartNumber(),
				parentMSIfc.getMaterialNumber(), childMSIfc.getMaterialNumber());
		boolean flag = false;
		if (mStructureList != null && mStructureList.size() > 0) {
			flag = true;
		}
		if (logger.isDebugEnabled()) {
			logger
			.debug("hasSplitedStructure(MaterialSplitIfc, MaterialSplitIfc) - end       "
					+ flag); //$NON-NLS-1$
		}
		return flag;
	}

	

	/**
	 * ����������Ҫ���������ԣ������ϲ�ּ��ṹ�޹أ���������ʱ���Ƶ�ÿ����ֺ�����ϼ�¼�С�
	 * 
	 * @param partIfc
	 * @param mSplitIfc
	 * @throws QMException
	 */
	private void setMaterialSplit(QMPartIfc partIfc, MaterialSplitIfc mSplitIfc)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger
			.debug("setMaterialSplit(QMPartIfc, MaterialSplitIfc) - start"); //$NON-NLS-1$
			logger.debug("������partIfc==" + partIfc);
			logger.debug("������mSplitIfc==" + mSplitIfc);
		}
		mSplitIfc.setPartName(partIfc.getPartName());
        String partType = partIfc.getPartType().getDisplay();
        String partNumber = partIfc.getPartNumber();
        if(partType.equals("����")&&(partNumber.indexOf("5000990")<0)){
        	mSplitIfc.setDefaultUnit("��");
        }else{
        	mSplitIfc.setDefaultUnit("��");
        }
		

		mSplitIfc.setPartType(partType);
		mSplitIfc.setPartModifyTime(partIfc.getModifyTime());
		
		mSplitIfc.setDomain(DomainHelper.getDomainID(mSplitDefaultDomainName));
		if (logger.isDebugEnabled()) {
			logger.debug("setMaterialSplit(QMPartIfc, MaterialSplitIfc) - end"); //$NON-NLS-1$
		}
	}

	// 20080103 begin
	/**
	 * ��ȡ·�����Զ��塣
	 * 
	 * @return ·�����Զ��������������ļ��ϡ�
	 * @throws QMException
	 */
	public List getRouteDefList() throws QMException
	// 20080103 end
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getRouteDefList() - start"); //$NON-NLS-1$
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		List routeDefList = new ArrayList();
		List tempRouteDefList = new ArrayList();
		ArrayList list = new ArrayList();
		// ���������ַ���
		StringTokenizer st = new StringTokenizer(routeIBA, ";");
		while (st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		for (int i = 0; i < list.size(); i++) {
			String routeName = (String) list.get(i);
			// �ñ����Ϊ��ѯ�����������Ƿ��з���������StringDefinition��
			final QMQuery query = new QMQuery("StringDefinition");
			QueryCondition condition = new QueryCondition("name",
					QueryCondition.EQUAL, routeName);
			query.addCondition(condition);
			tempRouteDefList.addAll((List)pservice.findValueInfo(query));
		}
		logger.debug("tempRouteDefList==" + tempRouteDefList);
		for (int i = 0; i < tempRouteDefList.size(); i++) {
			routeDefList
			.add(IBADefinitionObjectsFactory
					.newAttributeDefDefaultView((AttributeHierarchyChild) tempRouteDefList
							.get(i)));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getRouteDefList() - end" + routeDefList); //$NON-NLS-1$
		}
		return routeDefList;
	}

	// 20080103 begin
	/**
	 * ִ�����ϲ��ǰ�Ĺ����㲿�����ܡ��õ����ϲ������������Ҫ���в�ִ�����㲿�����ϡ�
	 * 1.�ȸ����㲿��Ψһ��ʶ��������㲿����Ψһ��ʶΪ�����+��汾��+��������״̬���ظ��ļ�¼�������������ٲ�֡�
	 * 2.�����Ƿ���Ҫ��ֹ������㲿���� �Ƿ���Ҫ��ִ�����򣺲�ѯ���ϲ�ֱ������ݡ�
	 * 1.�Ƚ���Ҫ��ֵ��㲿�������ϲ�ֱ����Ӧ�����ϵİ汾�������Ҫ��ֵ��㲿���İ汾�������ϲ�ֱ�������ݣ����ٲ�֣��������㲿����
	 * 2.�������ݲ����ڣ���Ҫ��֡� 3.�������ݴ��ڣ����汾��a�汾�仯����Ҫ��֣�
	 * b�汾û�б仯��1״̬�仯���ҵ����ϲ�ֱ�����Ӧ���ݣ��滻���ԣ�����֣� 2״̬û�б仯�������˼�¼��
	 * 
	 * @param partBsoIDs
	 *            �ԡ�;��Ϊ�ָ������㲿��BsoID���ϡ�
	 * @param routes
	 *            �ԡ�;��Ϊ�ָ�����·�߼��ϡ�
	 * @return �������˺󣬷��ϲ������������Ҫ���в�ִ�����㲿�����ϡ�
	 * @throws QMException
	 */
	private final HashMap filterParts(String partBsoIDs, String routes)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("filterParts(String) - start"); //$NON-NLS-1$
			logger.debug("������partBsoIDs==" + partBsoIDs);
			logger.debug("������routes==" + routes);
		}
		VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
		List routeList = new ArrayList();
		if (routes != null && !routes.equals("")) {
			StringTokenizer st = new StringTokenizer(routes, semicolonDelimiter);
			// 20080102 zhangq add for srq begin:ɢ�����Ĳ��Ŵ����Ǻ��֣���Ҫת�������磢�䣢ת��Ϊ���̣ѣ���
			String routeString = "";
			while (st.hasMoreTokens()) {
				// routeList.add(st.nextToken());
				routeString = changeRoute(st.nextToken());
				routeList.add(routeString);
			}
			// 20080102 zhangq add for srq end
		}
		// ͨ������֪ͨ�����Ĳ���֪ͨ���ȡ�������㲿����
		List partList = new ArrayList();
		try {
			partList = getAllPartByBsoID(partBsoIDs);
		} catch (QMException e) {
			// "ͨ������֪ͨ�����Ĳ���֪ͨ���ȡ�������㲿��ʧ�ܣ�"
			logger.error(Messages.getString("Util.22"), e);
			throw new ERPException(e, RESOURCE, "Util.22", null);
		}
		// ͨ�����°������ù淶�����㲿����
		HashMap latestIterationMap = new HashMap();
		logger.debug("������routeList==" + routeList);
		logger.debug("������partList==" + partList);
		logger.debug("������routeList.size()==" + routeList.size());
		logger.debug("������partList.size()==" + partList.size());
		for (int i = 0; i < partList.size(); i++) {
			QMPartIfc partIfc = (QMPartIfc) partList.get(i);
			partIfc = (QMPartIfc) vcservice.getLatestIteration(partIfc);
			latestIterationMap.put(partIfc.getBsoID(), routeList.get(i));
		}
		logger.debug("ͨ�����°������ù淶�����㲿����latestIterationList=="
				+ latestIterationMap);
		Object[] lalatestPartBsoIDs = latestIterationMap.keySet().toArray();
		// �����㲿��Ψһ��ʶ��������㲿����Ψһ��ʶΪ�����+��汾��+��������״̬���������˺��QMPartIfc���ϴ���filterPartList�С�
		final List filterPartList = filterByIdentity(lalatestPartBsoIDs);
		// �����Ƿ���Ҫ��ֹ������㲿����
		filterBySplitRule(filterPartList);
		HashMap filterPartMap = new HashMap();
		for (int i = 0; i < filterPartList.size(); i++) {
			QMPartIfc filterPartIfc = (QMPartIfc) filterPartList.get(i);
			filterPartMap.put(filterPartIfc.getBsoID(), latestIterationMap
					.get(filterPartIfc.getBsoID()));
		}
		if (logger.isDebugEnabled()) {
			logger.debug("filterParts(String) - end     " + filterPartMap); //$NON-NLS-1$
		}
		return filterPartMap;
	}

	// 20080103 end
	/**
	 * �����Ŵ���Ӻ���ת��Ϊ��ơ�
	 * 
	 * @param tempRouteCodes��·�ߣ���dashDelimiter��Ϊ�ָ�����
	 */
	public String changeRoute(String routeCodes) throws QMException {
		String routeID;
		CodingIfc codeIfc;
		String routeCode;
		String routeString = "";
		CodingManageService cmservice=(CodingManageService)EJBServiceHelper.getService("CodingManageService");
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		StringTokenizer routesTok = new StringTokenizer(routeCodes,
				dashDelimiter);
		boolean isFirst = true;
		while (routesTok.hasMoreTokens()) {
			routeCode = routesTok.nextToken();
			routeID = (String)cmservice.getIDbySort(routeCode);
			logger.debug("routeID is " + routeID);
			if (routeID != null) {
				codeIfc = (CodingIfc)pservice.refreshInfo(routeID);
				routeCode = codeIfc.getSearchWord();
				logger.debug("codeIfc.getSearchWord() is "
						+ codeIfc.getSearchWord());
				if (routeCode == null || routeCode.trim().length() <= 0) {
					logger
					.debug("tempRouteCode==null||tempRouteCode.trim().length()<=0 ");
					routeCode = codeIfc.getCodeContent();
				}
				logger.debug("codeIfc.getCodeContent() is "
						+ codeIfc.getCodeContent());
				logger.debug("tempRouteCode is " + routeCode);
			}
			if (isFirst) {
				routeString = routeCode;
				isFirst = false;
			} else {
				routeString += dashDelimiter + routeCode;
			}

		}
		return routeString;
	}

	/**
	 * �����ݿͻ��˴��ݵ��ԡ�;����Ϊ�ָ������㲿��BsoID�ַ�����ȡ���е��㲿�����ϡ�
	 * 
	 * @param partBsoIDs
	 *            �㲿��BsoID�ַ�����
	 * @return �㲿�����ϡ�
	 * @throws QMException
	 */
	// 20080103 begin
	public List getAllPartByBsoID(String partBsoIDs) throws QMException
	// 20080103 end
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getAllPartByBsoID(String) - start"); //$NON-NLS-1$
			logger.debug("������partBsoIDs==" + partBsoIDs);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		List bsoIDList = new ArrayList();
		List partList = new ArrayList();
		if (partBsoIDs != null && !partBsoIDs.equals("")) {
			StringTokenizer st = new StringTokenizer(partBsoIDs,
					semicolonDelimiter);
			while (st.hasMoreTokens()) {
				bsoIDList.add(st.nextToken());
			}
			for (int i = 0; i < bsoIDList.size(); i++) {
				QMPartIfc partIfc = (QMPartIfc) pservice.refreshInfo((String)bsoIDList.get(i));
				partList.add(partIfc);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getAllPartByBsoID(String) - end       " + partList); //$NON-NLS-1$
		}
		return partList;
	}

	// 20080103 begin
	/**
	 * �����㲿��Ψһ��ʶ��������㲿����Ψһ��ʶΪ�����+��汾��+��������״̬+·��+�������
	 * 
	 * @param partBsoIDs
	 *            �����˵��㲿�����ϡ�
	 * @return ���˺��QMPartIfc���ϡ�
	 * @throws QMException
	 */
	private final List filterByIdentity(final Object[] partBsoIDs,HashMap hashMap)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - start"); //$NON-NLS-1$
			logger.debug("������parts==" + partBsoIDs);
		}
		PersistService pservice =(PersistService)EJBServiceHelper.getService("PersistService");
		final List filterPartList = new ArrayList();
		// ���ͨ��Ψһ��ʶ���˺���㲿����
		final HashMap tempPartMap = new HashMap();
		for (int i = 0; i < partBsoIDs.length; i++) {
			
			QMPartIfc partIfc=null;
			if(partBsoIDs[i] instanceof String)
			{
				partIfc= (QMPartIfc)  pservice.refreshInfo((String)partBsoIDs[i]);
			}
			else if(partBsoIDs[i] instanceof BaseValueIfc)
			{
				partIfc=(QMPartIfc)pservice.refreshInfo((BaseValueIfc)partBsoIDs[i]);
			}

			String partIdentity = getObjectIdentity(partIfc,hashMap);
			// �����㲿����Ψһ��ʶ�����㲿�����ϣ��ظ��ļ�¼�����˵������ٲ�֡�
			if (!tempPartMap.containsKey(partIdentity)) {
				tempPartMap.put(partIdentity, partIfc);
				filterPartList.add(partIfc);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("filterByIdentity(List) - end      " + filterPartList); //$NON-NLS-1$
		}
		return filterPartList;
	}

	// 20080103 end

	/**
	 * ��ȡҵ������Ψһ��ʶ��
	 * 
	 * @param baseValueIfc
	 *            ҵ����󣬰����㲿�������ϲ�֡�
	 * @return String ҵ������Ψһ��ʶ��
	 */
	private final String getObjectIdentity(final BaseValueIfc baseValueIfc,HashMap hashMap) throws QMException{

//		System.out.println("hashMaphashMaphashMap22222222222222222222222222222222222222============"+hashMap);
		String returnString = "";
		String xnj = "N";
		if(baseValueIfc instanceof QMPartIfc)
		{
			QMPartIfc part1 = (QMPartIfc)baseValueIfc;
			//��ȡ����������
			PartHelper partHelper = new PartHelper();

			Vector routevec = RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);
			if(hashMap.get(part1.getBsoID())==null)
				routevec=RequestHelper.getRouteBranchs(part1, null);
			else
				routevec=RequestHelper.getRouteBranchs(part1, (ListRoutePartLinkInfo)hashMap.get(part1.getBsoID()));
			String routeAsString = "";
			String routeAllCode = "";
			String routeAssemStr = "";
			String lx_y = "";
			boolean colorFlag = false;
			if(routevec.size() != 0)
			{
				routeAsString = (String)routevec.elementAt(0);
				routeAllCode = (String)routevec.elementAt(1);
				routeAssemStr = (String)routevec.elementAt(2);
				 String scolorFlag = (String)routevec.elementAt(6);
				 
				    if(scolorFlag.equals("1")){
				    	colorFlag = true;
				    }
				 lx_y =  (String)routevec.elementAt(7);
			}
			boolean islogic =partHelper.isLogical(part1,routeAsString,routeAssemStr);	
			 boolean virtualFlag=partHelper.getJFVirtualIdentity( part1, routeAsString, routeAssemStr,islogic,colorFlag);
			 if(virtualFlag){
				 xnj = "Y";
			 }

			//QMPartIfc part = (QMPartIfc)baseValueIfc;
			String partVersion = "";

			 if(partVersion.equals("")){
				 partVersion = PartHelper.getPartVersion(part1);
			 }
			//String partVersion = PartHelper.getPartVersion(part);
	    	String partnumber = part1.getPartNumber() + "/" + partVersion;
			returnString = partnumber + part1.getLifeCycleState().getDisplay()  + xnj;
//			System.out.println("returnStringreturnString100304========"+returnString);
		} else
			if(baseValueIfc instanceof MaterialSplitIfc)
			{
				MaterialSplitIfc materialSplitIfc = (MaterialSplitIfc)baseValueIfc;
				 if( materialSplitIfc.getVirtualFlag()){
					 xnj = "Y";
				 }
				returnString = materialSplitIfc.getPartNumber() + materialSplitIfc.getPartVersion() + materialSplitIfc.getState() + materialSplitIfc.getRoute() + xnj;
//				System.out.println("materialSplitIfcmaterialSplitIfcsssssssssaaaaaaaaaaaaaaaaaaa========"+materialSplitIfc	);
			}
		if(logger.isDebugEnabled())
			logger.debug("getObjectIdentity(BaseValueIfc) - end     " + returnString);
//		System.out.println("returnStringreturnString100304========"+returnString);
		return returnString;
	}

	/**
	 * �����Ƿ���Ҫ��ֹ������㲿����
	 * 
	 * �Ƿ���Ҫ��ִ�����򣺲�ѯ���ϲ�ֱ������ݡ�
	 * 1.�Ƚ���Ҫ��ֵ��㲿�������ϲ�ֱ����Ӧ���Ѳ�ֵ����ϵİ汾�������Ҫ��ֵ��㲿���İ汾�������ϲ�ֱ�������ݣ����ٲ�֣��������㲿����
	 * 2.�������ݲ����ڣ���Ҫ��֡� 3.�������ݴ��ڣ����汾��a�汾�仯����Ҫ��֣�
	 * b�汾û�б仯��1״̬�仯���ҵ����ϲ�ֱ�����Ӧ���ݣ��滻���ԣ�����֣� 2״̬û�б仯�������˼�¼��
	 * 
	 * @param filterPartList
	 *            ɸѡ���QMPartIfc���ϡ�
	 */
	private final void filterBySplitRule(final List filterPartList,HashMap hashmap)
	 throws QMException 
	 {
		PartHelper partHelper = new PartHelper();
	  if (logger.isDebugEnabled()) 
	  {
	   logger.debug("filterBySplitRule(List) - start"); //$NON-NLS-1$
	   logger.debug("������filterPartList==" + filterPartList);
	  }
	  PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
	  // �����ֵ��㲿�����ϡ�
	  final List removePartList = new ArrayList();
	 // System.out.println("100304++++++++filterPartList"+filterPartList.size()+"kkkkkkkkkkjjjjjjjjjjjj==="+filterPartList);
	  for (int i = 0; i < filterPartList.size(); i++) 
	  {
	   //System.out.println("daodijiciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===================================================");
	   MaterialSplitIfc materialSplitIfc = null;
	   final QMPartIfc partIfc = (QMPartIfc) filterPartList.get(i);
		 String partVersion ="";

		 if(partVersion.equals("")){
			 partVersion = PartHelper.getPartVersion(partIfc);
		 }
		 String partnumber ="";

	     partnumber =getMaterialNumber(partIfc,partVersion);
		 
  	    
//  	  System.out.println("partVersioncacacalalalalalala1111111==="+partVersion);
	 //  System.out.println("partnumbercacacalalalalalala11111==="+partnumber);
	   List mSplitList = new ArrayList();
	   try {
	    // ��������Ż�ȡ�Ѳ�ֵ����ϡ�
	    mSplitList = getSplitedMSplit(partnumber,partVersion);
	   } catch (QMException e) 
	   {
		   
	    Object[] aobj = new Object[] { partIfc.getPartNumber() };
	    // "���ұ��Ϊ*���㲿����Ӧ������ʱ����"
	    logger.error(Messages.getString("Util.51", aobj) + e);
	    throw new ERPException(e, RESOURCE, "Util.51", aobj);
	   }
	 //  System.out.println("mSplitList==="+mSplitList.size());
	   // ��ͬΨһ��ʶ������ֻ����һ����
	   HashMap maHashMap = new HashMap(5);
	  // System.out.println("100304++++++++mSplitList.size()"+mSplitList.size()+"============pppp"+mSplitList);
	   for (int k = 0; k < mSplitList.size(); k++) 
	   {
	    MaterialSplitIfc ma = (MaterialSplitIfc) mSplitList.get(k);
	    String objectIdectity = getObjectIdentity(ma,hashmap);
//	    System.out.println("100304++++++++maHashMamaHashMap"+maHashMap);
//	    System.out.println("100304++++++++objectIdectityobjectIdectity"+objectIdectity);
	    if (!maHashMap.containsKey(objectIdectity)) 
	    {
//	    	System.out.println("100304++++++++fffffffffffffffffffffffffffffffffffffffffffffffffffffffp");
	     maHashMap.put(objectIdectity, ma);
	     
	    }
	   }
	   Object[] materialSplitIfcs = maHashMap.values().toArray();
	
	   MaterialSplitIfc tempMaterialSplit = null;
	   if (materialSplitIfcs != null && materialSplitIfcs.length > 0) 
	   {
		   
	    if (logger.isDebugEnabled()) {
	     logger
	     .debug("---------�Ƿ���Ҫ��ִ������1.�Ƚ��µ���Ҫ��ֵ��㲿�������ϲ�ֱ���Ķ�Ӧ�����ϵİ汾�������Ҫ��ֵ��㲿���İ汾�������ϲ�ֱ���ļ�¼�����ٲ�֡�");
	    }
	  //  System.out.println("100304++++++++materialSplitIfcs.lengthmaterialSplitIfcs.length"+materialSplitIfcs.length);
	    for (int j = 0; j < materialSplitIfcs.length; j++) 
	    {
	     materialSplitIfc = (MaterialSplitIfc) materialSplitIfcs[j];
	     if (materialSplitIfc.getPartVersion() != null) 
	     {
  
	      {
	       if (tempMaterialSplit == null) 
	       {
	        tempMaterialSplit = materialSplitIfc;
	       } else if(partHelper.compareVersion(tempMaterialSplit.getPartVersion(),materialSplitIfc.getPartVersion())==2)
	       {//�˴�Ӧ����Ϊ��ȥ���ظ����ݣ�ȡ�߰汾���ݣ�����Ҳûʲô��
//	    	   System.out.println("100304++++++++22222222222222-------------"+materialSplitIfcs.length+"ddddddddddd"+materialSplitIfc);
	        tempMaterialSplit = materialSplitIfc;
	       }
	      
	     }
	    }
	   }
	   if (logger.isDebugEnabled()) 
	   {
	    logger.debug("partIfc.getPartNumber()=="
	      + partIfc.getPartNumber());
	   }
	   // �Ƿ���Ҫ��ִ������2.�������ݲ����ڣ���Ҫ��֡�
 	// System.out.println("tempMaterialSplits00000000000======="+tempMaterialSplit);

	   if (tempMaterialSplit == null) 
	   {
//		   System.out.println("100304++++++++33333333333333333-------------"+materialSplitIfcs.length);
	    if (logger.isDebugEnabled()) 
	    {
	     logger
	     .debug("---------�Ƿ���Ҫ��ִ������2.�������ݲ����ڣ���Ҫ��֡���汾�Ǿɵģ������֡�");
	    }
	    
	   }
	   // �Ƿ���Ҫ��ִ������3���������ݴ��ڣ����汾��
	   else 
	   {
	    
	     // �Ƿ���Ҫ��ִ������3.b.2��״̬û�б仯�������˼�¼��
	     if (partIfc.getLifeCycleState().getDisplay().equals(
	       tempMaterialSplit.getState())) 
	     {//��������״̬��û�䡢��Ҫ�Ƚ�·���Ƿ����仯
		 // System.out.println("100304++++++++tempMaterialSplit.getState()"+partIfc.getLifeCycleState().getDisplay());

	      Vector routevec = new Vector();
	      if(hashmap.get(partIfc.getBsoID())==null)
				routevec=RequestHelper.getRouteBranchs(partIfc, null);
			else
				routevec=RequestHelper.getRouteBranchs(partIfc, (ListRoutePartLinkInfo)hashmap.get(partIfc.getBsoID()));

	     // System.out.println("081000000000000000000000==="+routevec);
	      
	      String routeAsString = "";
	      String routeAllCode = "";
	      String routeAssemStr = "";
	      if(routevec.size() != 0)
	      {
	       routeAsString = (String)routevec.elementAt(0);
	       routeAllCode = (String)routevec.elementAt(1);
	      }
	      boolean flag1=false;
	      boolean flag2=false;
	      if(routeAllCode==null || routeAllCode=="")
	      {
	       flag1=true;
	      }
	      if(tempMaterialSplit.getRoute()==null || tempMaterialSplit.getRoute()=="")
	      {
	       flag2=true;
	      }
//	      System.out.println("100304++++++++flag1flag1flag1flag1flag1-------------"+flag1);
//	      System.out.println("100304++++++++flag2flag2flag2flag2flag2-------------"+flag2);
	      if(flag1&&flag2)
	      {
	    	  System.out.println("100304++++++++flag0000000000000000000000000000-------------");
	       removePartList.add(partIfc);
	      }
	      if(!flag1 && !flag2)
	      {
//	    	  System.out.println("100304++++++++flag11111111111111111111111111111111-------------");
//	    	  System.out.println("100304++++++++routeAllCode222222222222222222222222222222222222-------------"+routeAllCode);
//	    	  System.out.println("100304++++++++tempMaterialSplit.getRoute()222222222222222222222222222222222222-------------"+tempMaterialSplit.getRoute());
	       if(partIfc.getLifeCycleState().getDisplay().equals(tempMaterialSplit.getState()) && routeAllCode.equals(tempMaterialSplit.getRoute()))
	       {	    	   
	        removePartList.add(partIfc);
	       }

//	
	      }
  
	 
	      
	     }
	     // �Ƿ���Ҫ��ִ������3.a�汾�仯����Ҫ��֣�
	     else 
	     {
	      if (logger.isDebugEnabled()) 
	      {
	       logger.debug("---------�Ƿ���Ҫ��ִ������3.a�汾�仯����Ҫ��֣�");
	      }
	     }
	    }
	    
	    
	   
	    

	  }
	  //CCBegin by chudaming 20100609 ѭ����remove�ģ�ʵ���Ͼ�ɾ����filterPartList����λ������
	  filterPartList.removeAll(removePartList);
	  //System.out.println("100304++++++++filterPartList7777777777777777777-------------"+filterPartList);
	  //CCEnd by chudaming 20100609
	 } 
	 }
	

		/**
		 * ��������Ż�ȡ�������ϡ�
		 * 
		 * @param partNumber
		 *            ����š�
		 * @return �������ϼ��ϡ�
		 * @throws QMException
		 */
		public List getRootMSplit(String partNumber,String partVersion) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getRootMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query2 = new QMQuery("CDMaterialSplit");
			
		
			QueryCondition condition = new QueryCondition("partVersion",
					QueryCondition.EQUAL, partVersion);
			query2.addCondition(condition);
			query2.addAND();
		
			QueryCondition condition2 = new QueryCondition("materialNumber",
					QueryCondition.EQUAL, partNumber);
			query2.addCondition(condition2);
			query2.addAND();
			QueryCondition condition3 = new QueryCondition("rootFlag", true);
			query2.addCondition(condition3);
			List returnList=(List)pservice.findValueInfo(query2);
			if (logger.isDebugEnabled()) {
				logger.debug("getRootMSplit(String) - end       " + returnList); //$NON-NLS-1$
			}
			System.out.println("query2==="+query2);
			return returnList;
		}

		/**
		 * ��ȡ�����Ͻ�����ϡ�
		 * 
		 * @param mSplitIfc
		 *            �����ϡ�
		 * @param resultMSplitList
		 *            ���յĽ�����ϡ�
		 * @throws QMException
		 */
		private void getSubResultMSplitList(MaterialSplitIfc mSplitIfc,
				List resultMSplitList, int level) throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultMSplitList(MaterialSplitIfc, List, int) - start"); //$NON-NLS-1$
				logger.debug("������mSplitIfc==" + mSplitIfc);
				logger.debug("������resultMSplitList==" + resultMSplitList);
				logger.debug("������level==" + level);
			}
			// ��ȡָ�������Ϻŵ����Ͻṹ���ϡ�
			List mStructureList = getMStructure(mSplitIfc.getPartNumber(),
					mSplitIfc.getMaterialNumber());
			Iterator iter = mStructureList.iterator();
			while (iter.hasNext()) {
				MaterialStructureIfc mStructureIfc = (MaterialStructureIfc) iter
				.next();
				// �������ϺŻ�ȡ���ϡ�
				
				MaterialSplitIfc subMSplitIfc = getMSplit(mStructureIfc
						.getChildNumber());
				// ��¼���ϡ�����ʾ�õ����ϲ㼶�����ϵĸ����Ϻš�
				List mSplitAndLevelList = new ArrayList();
				mSplitAndLevelList.add(subMSplitIfc);
				mSplitAndLevelList.add(String.valueOf(level + 1));
				mSplitAndLevelList.add(mStructureIfc.getParentNumber());
				resultMSplitList.add(mSplitAndLevelList);
				if (subMSplitIfc != null && subMSplitIfc.getStatus() == 1) {
					// �ݹ��ȡ�����ϣ�����filterMSplitList�С�
					getSubResultMSplitList(subMSplitIfc, resultMSplitList,
							level + 1);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultMSplitList(MaterialSplitIfc, List, int) - end"); //$NON-NLS-1$
			}
		}

	
		private List getSplitedMSplit(String partNumber,String partVersion) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getSplitedMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
//			System.out.println("partNumber1111111="+partNumber);
//			System.out.println("partVersion1111111="+partVersion);
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
//			int aa=1;//ȥ��ԭ���� final
			 QMQuery query = new QMQuery("CDMaterialSplit");
			QueryCondition condition = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query.addCondition(condition);
			//CCBegin by chudaming 20100610 dele �·����������γ�XML�ļ����㲿�����мȴ��ڡ�N����ʶ��Ҳ���ڡ�U����ʶ���ṹ���мȴ��ڡ�N����ʶ��Ҳ���ڡ�O����ʶ
			//CCBegin by chudaming 20101216
			query.addAND();
			
			QueryCondition condition2 = new QueryCondition("partVersion",
					QueryCondition.EQUAL, partVersion);
			query.addCondition(condition2);
			query.addAND();
		
			QueryCondition condition1 = new QueryCondition("rootFlag",
					true);
			query.addCondition(condition1);
			//CCEnd by chudaming 20101216
			//??splited�о�·��Ϊ����Ϊfalse
//			QueryCondition condition4 = new QueryCondition("splited", true);
//			query.addCondition(condition4);
			//CCEnd by chudaming 20100610 dele
			List mSplitList=(List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getSplitedMSplit(String) - end"); //$NON-NLS-1$
			}
			//System.out.println("query2222="+query.getDebugSQL());
//			System.out.println("wwwwwwwwwwwwwwwwsssssssssssssssssssssssssssssssssmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy================="+mSplitList);
			return mSplitList;
		}

		/**
		 * ��������Ż�ȡ���ϲ�ֱ���ļ�¼��������ת���ĺ�δת���ġ�
		 * 
		 * @param partNumber
		 *            ����š�
		 * @return ���ϼ��ϡ�
		 * @throws QMException
		 */
		private List getAllMSplit(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("CDMaterialSplit");
			QueryCondition condition = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query.addCondition(condition);
			List mSplitList=(List)pservice.findValueInfo(query);
			//System.out.println("query======="+query.getDebugSQL());
			if (logger.isDebugEnabled()) {
				logger.debug("getAllMSplit(String) - end        " + mSplitList); //$NON-NLS-1$
			}
			return mSplitList;
		}

		/**
		 * �������ϺŻ�ȡ���ϡ�
		 * 
		 * @param materialNumber
		 *            ���Ϻš�
		 * @return ���ϡ�
		 * @throws QMException
		 */
		public MaterialSplitIfc getMSplit(String materialNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("������materialNumber==" + materialNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("CDMaterialSplit");
			QueryCondition condition = new QueryCondition("materialNumber",
					QueryCondition.EQUAL, materialNumber);
			query.addCondition(condition);
			List mSplitList=(List)pservice.findValueInfo(query);
			MaterialSplitIfc mSplitIfc = null;
			if (mSplitList != null && mSplitList.size() > 0) {
				mSplitIfc = (MaterialSplitIfc) mSplitList.get(0);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getMSplit(String) - end       " + mSplitList); //$NON-NLS-1$
			}
			return mSplitIfc;
		}

		/**
		 * ��ȡָ�������ŵ����Ͻṹ���ϡ�
		 * 
		 * @param String
		 *            �����š�
		 * @return List ָ�������ŵ����Ͻṹ���ϡ�
		 * @throws QMException
		 */
		private final List getMStructure(String parentPartNumber)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructureByNum(String) - start"); //$NON-NLS-1$
				logger.debug("������parentPartNumber==" + parentPartNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			QMQuery query = new QMQuery("CDMaterialStructure");
			QueryCondition condition1 = new QueryCondition("parentPartNumber", "=",
					parentPartNumber);
			query.addCondition(condition1);
			List mStructureList=(List)pservice.findValueInfo(query);
//			System.out.println("mStructureListmStructureListmStructureListmStructureList========"+mStructureList);
			if (logger.isDebugEnabled()) {
				logger
				.debug("getMStructureByNum(String) - end      " + mStructureList); //$NON-NLS-1$
			}
			return mStructureList;
		}

		/**
		 * ����ָ���ĸ����ź͸����Ϻ��������ҵ��ṹ�������з��������Ľṹ��Ϣ��
		 * 
		 * @param parentPartNumber
		 *            �����š�
		 * @param parentNumber
		 *            �����Ϻš�
		 * @return �ṹ��Ϣ��
		 * @throws QMException
		 */
		public List getMStructure(String parentPartNumber, String parentNumber)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructure(String, String) - start"); //$NON-NLS-1$
				logger.debug("������parentPartNumber==" + parentPartNumber);
				logger.debug("������parentNumber==" + parentNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("CDMaterialStructure");
			QueryCondition condition = new QueryCondition("parentPartNumber",
					QueryCondition.EQUAL, parentPartNumber);
			query.addCondition(condition);
			query.addAND();
			QueryCondition condition3 = new QueryCondition("parentNumber",
					QueryCondition.EQUAL, parentNumber);
			query.addCondition(condition3);
			List mStructureList=(List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructure(String, String) - end"); //$NON-NLS-1$
			}
			return mStructureList;
		}

		/**
		 * ����ָ���ĸ����ź͸����Ϻź������Ϻ��������ҵ��ṹ�������з��������Ľṹ��Ϣ��
		 * 
		 * @param parentPartNumber
		 *            �����š�
		 * @param parentNumber
		 *            �����Ϻš�
		 * @param childNumber
		 *            �����Ϻš�
		 * @return �ṹ��Ϣ��
		 * @throws QMException
		 */
		private List getMStructure(String parentPartNumber, String parentNumber,
				String childNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructure(String, String, String) - start"); //$NON-NLS-1$
				logger.debug("������parentPartNumber==" + parentPartNumber);
				logger.debug("������parentNumber==" + parentNumber);
				logger.debug("������childNumber==" + childNumber);
			}
			System.out.println("parentPartNumber="+parentPartNumber);
			System.out.println("childNumber="+childNumber);
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("CDMaterialStructure");
			QueryCondition condition = new QueryCondition("parentPartNumber",
					QueryCondition.EQUAL, parentPartNumber);
			query.addCondition(condition);
			query.addAND();
			QueryCondition condition2 = new QueryCondition("parentNumber",
					QueryCondition.EQUAL, parentNumber);
			query.addCondition(condition2);
			query.addAND();
			QueryCondition condition3 = new QueryCondition("childNumber",
					QueryCondition.EQUAL, childNumber);
			query.addCondition(condition3);
			List mStructureList = (List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructure(String, String, String) - end"); //$NON-NLS-1$
			}
			return mStructureList;
		}

		/**
		 * ����ָ���������Ϻ��������ҵ��ṹ�������з��������Ľṹ��Ϣ��
		 * 
		 * @param childNumber
		 *            �����Ϻš�
		 * @return �ṹ��Ϣ��
		 * @throws QMException
		 */
		private List getMStructureByChild(String childNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructureByChild(String) - start"); //$NON-NLS-1$
				logger.debug("������childNumber==" + childNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("CDMaterialStructure");
			QueryCondition condition3 = new QueryCondition("childNumber",
					QueryCondition.EQUAL, childNumber);
			query.addCondition(condition3);
			List mStructureList = (List) pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getMStructureByChild(String) - end"); //$NON-NLS-1$
			}
			return mStructureList;
		}


		

		/**
		 * ��ȡ�����Ͻ�����ϡ�
		 * 
		 * @param mSplitIfc
		 *            �����ϡ�
		 * @param resultMSplitList
		 *            ���յĽ�����ϡ�
		 * @throws QMException
		 */
		private void getSubResultMSplitList(MaterialSplitIfc mSplitIfc,
				List resultMSplitList) throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultMSplitList(MaterialSplitIfc, List) - start"); //$NON-NLS-1$
				logger.debug("������mSplitIfc==" + mSplitIfc);
				logger.debug("������resultMSplitList==" + resultMSplitList);
			}
			// ��ȡָ�������Ϻŵ����Ͻṹ���ϡ�
			List mStructureList = getMStructure(mSplitIfc.getPartNumber(),
					mSplitIfc.getMaterialNumber());
			Iterator iter = mStructureList.iterator();
			// ���Ͻṹֵ����
			MaterialStructureIfc mStructureIfc;
			// ����ֵ����
			MaterialSplitIfc subMSplitIfc;
			while (iter.hasNext()) {
				mStructureIfc = (MaterialStructureIfc) iter.next();
				// �������ϺŻ�ȡ���ϡ�
				subMSplitIfc = getMSplit(mStructureIfc.getChildNumber());
				resultMSplitList.add(subMSplitIfc);
				if (subMSplitIfc != null && subMSplitIfc.getStatus() == 1) {
					// �ݹ��ȡ�����ϣ�����resultMSplitList�С�
					getSubResultMSplitList(subMSplitIfc, resultMSplitList);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultMSplitList(MaterialSplitIfc, List) - end"); //$NON-NLS-1$
			}
		}




		/**
		 * ���ݸ������������Գ�����,����ض�iba����ֵ�� return String Ϊ��iba���Ե�ֵ��
		 * 
		 * @throws QMXMLException
		 */
		public String getPartIBA(QMPartIfc partIfc, String ibaDisplayName)
		throws QMXMLException {
			if (logger.isDebugEnabled()) {
				logger.debug("getPartIBA() - start"); //$NON-NLS-1$
			}
			String ibaValue = "";
			final HashMap nameAndValue = new HashMap();
			try {
				IBAValueService ibservice=(IBAValueService)EJBServiceHelper.getService("IBAValueService");
				partIfc = (QMPartIfc)ibservice.refreshAttributeContainerWithoutConstraints(partIfc);
			} catch (QMException e) {
				// "ˢ����������ʱ����"
				logger.error(Messages.getString("Util.7"), e); //$NON-NLS-1$
				throw new QMXMLException(e);
			}
			DefaultAttributeContainer container = (DefaultAttributeContainer) partIfc
			.getAttributeContainer();
			if (container == null) {
				container = new DefaultAttributeContainer();
			}
			final AbstractValueView[] values = container.getAttributeValues();
			for (int i = 0; i < values.length; i++) {
				final AbstractValueView value = values[i];
				final AttributeDefDefaultView definition = value.getDefinition();
				if (definition.getDisplayName().trim()
						.equals(ibaDisplayName.trim())) {
					if (value instanceof AbstractContextualValueDefaultView) {
						MeasurementSystemCache.setCurrentMeasurementSystem("SI");
						final String measurementSystem = MeasurementSystemCache
						.getCurrentMeasurementSystem();
						if (value instanceof UnitValueDefaultView) {
							DefaultUnitRenderer defaultunitrenderer = new DefaultUnitRenderer();
							String ss = "";
							try {
								ss = defaultunitrenderer
								.renderValue(
										((UnitValueDefaultView) value)
										.toUnit(),
										((UnitValueDefaultView) value)
										.getUnitDisplayInfo(measurementSystem));
							} catch (UnitFormatException e) {
								// "������λ��ʽ����"
								logger.error(Messages.getString("Util.8"), e); //$NON-NLS-1$
								throw new QMXMLException(e);
							} catch (IncompatibleUnitsException e) {
								// "���ֲ����ݵ�λ��"
								logger.error(Messages.getString("Util.9"), e); //$NON-NLS-1$
								throw new QMXMLException(e);
							}
							final String ddd = ((UnitValueDefaultView) value)
							.getUnitDefinition()
							.getDefaultDisplayUnitString(measurementSystem);
							// nameAndValue.put(definition.getDisplayName(), ss +
							// ddd);
							ibaValue = ss + ddd;
						} else {
							// nameAndValue.put(definition.getDisplayName(),
							// ((AbstractContextualValueDefaultView) value)
							// .getValueAsString());
							ibaValue = ((AbstractContextualValueDefaultView) value)
							.getValueAsString();
						}
					} else {
						// nameAndValue.put(definition.getDisplayName(),
						// ((ReferenceValueDefaultView) value)
						// .getLocalizedDisplayString());
						ibaValue = ((ReferenceValueDefaultView) value)
						.getLocalizedDisplayString();
					}
					break;
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getPartIBA() - end" + nameAndValue.size()); //$NON-NLS-1$
			}
			return ibaValue;
		}

	

	
		/**
		 * ��·���е�·�ߴ���ת��ΪList,���Ҹ��������ļ�������ȥ��·���е�������롣
		 * 
		 * @param routeStr
		 * @return
		 */
		public List getRouteCodeList(String routeStr) {
			StringTokenizer routeTok = new StringTokenizer(routeStr, dashDelimiter);
			String routeCode = "";
	
			List routeCodeList = new ArrayList();
			//boolean isFirst = true;
			while (routeTok.hasMoreTokens()) {
				routeCode = routeTok.nextToken();
				//System.out.println("routeCode=============="+routeCode);
			//	System.out.println("isFirst=============="+isFirst);
				//if (isFirst ) {
					routeCodeList.add(routeCode);
				//	isFirst = false;
				//}
			}
			logger.debug("getRouteCodeList end routeCodeList is " + routeCodeList);
			return routeCodeList;
		}

		

		/**
		 * ��ȡ����֪ͨ��ı�š� ����֪ͨ���Զ���Ź��򣺲��õĲ�Ʒ�ı��+���ñ�ʶ+��λ���ֱ�š� ����������λ���ֱ�Ŵ�1��ʼ�������ӡ�
		 * 
		 * @param partNumber
		 * @param promulgateNotifyFlag
		 * @return
		 * @throws QMException
		 */
		private String getPromulgateNotifyNumber(String partNumber,
				String promulgateNotifyFlag, int j) throws QMException {

			String str = "000" + Integer.toString(j);
			// CCEnd by dikefeng 20090217
			return partNumber + promulgateNotifyFlag
			+ str.substring(str.length() - 3);
		}

	

		 //�����������ȡ·�߹������

		public Collection getPartByRouteListJF(TechnicsRouteListIfc list,HashMap hashmap)
		throws Exception
		{
			try {
			PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
			Collection c = new Vector();
		
			QMQuery query = new QMQuery("consListRoutePartLink");
			QueryCondition cond = new QueryCondition("leftBsoID", "=", list.getBsoID());
			query.addCondition(cond);
			//CCBegin by chudaming 20101222
			query.addAND();
			QueryCondition condition11 = new QueryCondition("adoptStatus",
					QueryCondition.NOT_EQUAL, "CANCEL");
			query.addCondition(condition11);
			Collection coll = ps.findValueInfo(query);
			System.out.println("query = "+query.getDebugSQL());
			System.out.println("coll = "+coll);
			PartHelper partHelper =  new PartHelper();
			for(Iterator iter = coll.iterator(); iter.hasNext();)
			{
				ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)iter.next();

				Collection cc = getPartByListRoutepart(linkInfo);
				QMPartIfc partInfo = null;
				for(Iterator ii = cc.iterator(); ii.hasNext();)
				{
					partInfo = (QMPartIfc)ii.next();
					partInfo=partHelper.filterLifeState(partInfo);
//
					     if(partInfo==null)
					    	 continue;
						 c.add(partInfo);
						// System.out.println("ssssssss0000000000dwwwwwwwwwwwwwwwwww==="+c);
						 
						 hashmap.put(partInfo.getBsoID(), linkInfo);
//		 	          }
				}

			}

			return c;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
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
//		��ȡerp�ص�����
		private String getERPHD(String partNumber)  throws QMException{
			//Collection col = query("CDMaterialSplit","partNumber","=",partNumber);
	
			Collection col = null;
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			 QMQuery query = new QMQuery("CDMaterialSplit");
			 QueryCondition qc1=new QueryCondition("partNumber","=",partNumber);
             query.addCondition(qc1);
            // System.out.println("query="+query.getDebugSQL());
             col=pservice.findValueInfo(query);

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
		//CCBegin SS8
		/**
		 * ����·�ߣ��ж��Ƿ���Ҫ������
		 * �������������·������Է���
		 * װ��ͼ����ERP������װ��ͼ�жϹ�������ŵ�5��6λΪ0���Ҳ��Ǳ�׼��������·�ߺ����á���װ��·��Ϊ��
		 * ������������ERP���������������жϹ���������ƺ�������������������·�ߺ����á���װ��·��Ϊ��
		 * ·�ߵ�λ����������·�ߺ�װ��·�ߣ��������ڿ�����·�ߵ�λ��Χ�ڵ��㲿������ERP������

		 * @return �㲿����š�
		 * @throws QMException
		 */
		public boolean ifPublish(QMPartIfc part,String makeStr,String assembStr) throws QMException {
			boolean flag = true;
			PartHelper helper = new PartHelper();
			//�Ƿ���װ��ͼ
			String partNum = part.getPartNumber();
//			System.out.println("makeStr="+makeStr);
//			System.out.println("assembStr="+assembStr);
			if(partNum.length()>=6){
    			
    			String subNumber = partNum.substring(4,6);
    		//	System.out.println("subNumber="+subNumber);
	    		String partType = part.getPartType().getDisplay();
	    		if(subNumber.equals("00")&&(!partType.equals("��׼��"))&&makeStr.contains("��")&&assembStr.equals("")){
	    			//System.out.println("partNumberVer1="+partNumberVer);
	    			System.out.println("��װ��ͼ=");
	    			return false;
	    		}
    		}
			
			//�Ƿ��Ǽ�������
			String partName = part.getPartName();
//			System.out.println("partName="+partName);
//			System.out.println("lx_y="+lx_y);
//			System.out.println("assembStr="+assembStr);
			if(partName.contains("��������")&&makeStr.contains("��")&&assembStr.equals("")){
    			//System.out.println("partNumberVer2="+partNumberVer);
				System.out.println("��������=");
    			return false;
    		}

		

			return true;
		
		}
		
	//CCEnd SS8
	
	}
