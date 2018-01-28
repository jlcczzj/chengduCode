/**
 * ���ɳ���MaterialDataPublisher.java	1.0              2007-9-28
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �㲿����������״̬Ϊ�����е����Ҳ���Է�����ERP ������ 2014-03-03
 * SS2 ���Ͻṹ������г�����������״̬Ϊ�����е��Ӽ� ������ 2014-03-05
 * SS3 ����xml�ļ��� Ҫ������ź����Ϻ��г�Ʒ�ű���һ��
 * SS4 �½�һ�����������洢 �㲿���źͳ�Ʒ��
 * SS5 ���˵ļ��ϰ���·���е�����Լ��Ӽ�
 */
package com.faw_qm.erp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.change.model.QMChangeRequestIfc;
import com.faw_qm.erp.ejb.service.MaterialSplitService;
import com.faw_qm.erp.ejb.service.PromulgateNotifyService;
import com.faw_qm.erp.exception.QMXMLException;
import com.faw_qm.erp.model.FilterPartIfc;
import com.faw_qm.erp.model.FilterPartInfo;
import com.faw_qm.erp.model.MaterialSplitIfc;
import com.faw_qm.erp.model.MaterialStructureIfc;
import com.faw_qm.erp.model.MaterialStructureInfo;
import com.faw_qm.erp.model.PromulgateNotifyIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.pcfg.family.model.GenericPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.erp.model.*;

//
/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class MaterialDataPublisher extends BaseDataPublisher
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(MaterialDataPublisher.class);

    /**
     * ���ϲ�ֵĹ�����
     */
    private static String mSplitDefaultDomainName = (String) RemoteProperty
            .getProperty("materialSplitDefaultDomain", "System");

    /**
     * ��ű��Ϊ�޸ĺ��ط������϶�Ӧ��filterPart��
     * key�ǹ��˵��㲿��filterPart�ı�ţ�value�ǹ��˵��㲿��ֵ����filterPart��
     */
    private HashMap filterPartMap = new HashMap();

    /**
     * ����Ѿ����ù�������ǵ�XMLMaterialSplit��
     * key�����ϵ����Ϻţ�value������ֵ�����XML��ʽ��
     */
    private HashMap hasSetSplitPubTypeMap = new HashMap();

    /**
     * ����Ѿ����ù�������ǵ�XMLMaterialStructure��
     * key�����Ͻṹ��ֵ�����BsoID��value�����Ͻṹֵ�����XML��ʽ��
     */
    private final HashMap hasSetStrutPublishType = new HashMap();

    /**
     * ������ϵĶ������ϡ�
     * key���㲿��Master��BsoID��value�Ǹ��㲿��������ϵĶ�������List��
     */
    private final HashMap rootMatSplitMap = new HashMap();

    /**
     * ��ŷ���������XMLMaterialSplitIfc��
     */
    private List xmlMatSplitList = new ArrayList();

    /**
     * ��ŷ��������ϽṹXMLMaterialStructureIfc��
     */
    private List xmlMatStructList = new ArrayList();

    /**
     * �������ݵ���Դ���͡�
     */
    private String sourceType = "";

    /**
     * �ֺŷָ��������ڷָ�·�ߡ�
     */
    private String semicolonDelimiter = ";";
    /**
     * ���ۺŷָ��������ڷָ�·�ߴ��롣
     */
    private String dashDelimiter = "-";
    
    /**
     * �洢����Ʒ��� �� partnumber
     */
    private final HashMap partNumberMap = new HashMap();
    

    /**
     * @throws QMXMLException
     */
    protected synchronized final void invoke() throws Exception
    {
    	try{
        if(logger.isDebugEnabled())
        {
            logger.debug("invoke() - start");   
        }
        filterMaterials();
        if(logger.isDebugEnabled())
        {
            logger.debug("invoke() - end");
        }}catch (Exception e){
        	e.printStackTrace();
        	throw e;
        	
        }
    }

    /**
     * ����ɸѡ�����
     * @throws QMXMLException
     */
    protected final void saveFilterPart() throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("saveFilterPart(List) - start");
        }
        //���úõ�Ҫ�����ɸѡ����ļ��ϡ�
        PersistService pservice=null;
        try
        {
        pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        	throw new QMXMLException(ex);
        }
       
        List filterPartList = new ArrayList();
        HashMap hasUpdatePartMap = new HashMap();
        //CCBegin by dikefeng 20100426,�����Ҫ�����xmlPartList����Ϊ�գ�����ѭ��
        if(xmlPartList==null||xmlPartList.size()==0)
        	return;
        //CCEnd by dikefeng 20100426
        for (int i = 0; i < xmlPartList.size(); i++)
        {
            final QMXMLMaterialSplit xmlMatSplit = (QMXMLMaterialSplit) xmlPartList
                    .get(i);
            FilterPartIfc filterPartIfc = (FilterPartIfc) filterPartMap
                    .get(xmlMatSplit.getPartNumber());
            String filterPartIdentity = xmlMatSplit.getPartNumber()
                    + xmlMatSplit.getPartVersionID()+ xmlMatSplit.getRoute();;
            if(!hasUpdatePartMap.containsKey(filterPartIdentity))
            {
                if(filterPartIfc != null
                        && xmlMatSplit.getPublishType().equals("Z"))
                {
                    //����״̬��
                    filterPartIfc.setState(xmlMatSplit.getState());
                    filterPartIfc.setRoute(xmlMatSplit.getRoute());
                    filterPartList.add(filterPartIfc);
                    hasUpdatePartMap.put(filterPartIdentity, filterPartIfc);
                    continue;
                }
                filterPartIfc = new FilterPartInfo();
                if(publishSourseObject instanceof PromulgateNotifyIfc)
                {
                    filterPartIfc
                            .setNoticeNumber(((PromulgateNotifyIfc) publishSourseObject)
                                    .getPromulgateNotifyNumber());
                    filterPartIfc.setNoticeType("����");
                }
                else if(publishSourseObject instanceof QMChangeRequestIfc)
                {
                    filterPartIfc
                            .setNoticeNumber(((QMChangeRequestIfc) publishSourseObject)
                                    .getNumber());
                    filterPartIfc.setNoticeType("���");
                }
                else if(publishSourseObject instanceof ManagedBaselineIfc)
                {
                	filterPartIfc.setNoticeNumber(((ManagedBaselineIfc)publishSourseObject).getBaselineName() + ((ManagedBaselineIfc)publishSourseObject).getBaselineNumber());
                    String nu=getXmlName();
                    if(nu.equalsIgnoreCase("caiyong"))
                	{
                    	filterPartIfc.setNoticeType("����֪ͨ");
                	}
                	else if(nu.equalsIgnoreCase("biangeng")) 
                	{
                		filterPartIfc.setNoticeType("���֪ͨ");
                	}
                	else
                	{
                		filterPartIfc.setNoticeType(nu);
                	}
                }
                else if(publishSourseObject instanceof TechnicsRouteListIfc)
                {
                    filterPartIfc.setNoticeNumber(((TechnicsRouteListIfc)publishSourseObject).getRouteListName() + ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber());
                    String nu = getXmlName();
                    if(nu.equalsIgnoreCase("caiyong"))
                    {
                        filterPartIfc.setNoticeType("����֪ͨ");
                    }
                    else if(nu.equalsIgnoreCase("biangeng"))
                    {
                        filterPartIfc.setNoticeType("���֪ͨ");
                    }
                    else
                    {
                        filterPartIfc.setNoticeType(nu);
                    }
                }

                filterPartIfc.setPartNumber(xmlMatSplit.getPartNumber());
                filterPartIfc.setState(xmlMatSplit.getState());
                filterPartIfc.setVersionValue(xmlMatSplit.getPartVersionID());
                filterPartIfc.setRoute(xmlMatSplit.getRoute());
                filterPartList.add(filterPartIfc);
                hasUpdatePartMap.put(filterPartIdentity, filterPartIfc);
                if(logger.isDebugEnabled())
                {
                    logger.debug(filterPartIfc.getNoticeNumber());
                    logger.debug(filterPartIfc.getNoticeType());
                    logger.debug(filterPartIfc.getPartNumber());
                    logger.debug(filterPartIfc.getState());
                    logger.debug(filterPartIfc.getVersionValue());
                }
            }
        }
        for (int i = 0;  i < filterPartList.size(); i++)
        {
            try
            {
                pservice.saveValueInfo((BaseValueIfc)filterPartList.get(i));
            }
            catch (QMException e)
            {
                //"�������*ʱ����"
                logger.error(Messages.getString("Util.16",
                        new Object[]{((FilterPartIfc) filterPartList.get(i))
                                .getIdentity()})
                        + e);
                throw new QMXMLException(e);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("saveFilterPart(List) - end"); //$NON-NLS-1$
        }
    }

    /**
     * ִ�����ϵ�ɸѡ���ܡ��������˺����Ͻ���������ø�������������Ϊ����ɸѡ�����׼����
     * ɸѡ�����н����ϵ���Ϣ�ͽṹ��������Ϣ���õ�QMXMLMaterial��QMXMLMaterialStructure�У�
     * ��������������������߼���
     *
     * ɸѡ������¼�������
     * 1.�ȸ���ɸѡ��Ψһ��ʶ�����㲿�����ϣ��ظ��ļ�¼��ɸѡ�������ٷ�����
     * 2.�㲿���źͰ汾����ͬ������ֻ��ɸѡ������д���һ����¼��״̬�仯���޸�������Ϣ��
     *
     * ע�����Խṹ���еݹ鴦��ֻ�����һ���ṹ���ݹ鲿���Ѿ���������ȡʱ��ɡ�
     * @throws QMXMLException
     */
    private final void filterMaterials() throws Exception
    {

        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials() - start"); //$NON-NLS-1$
        }
        //ͨ������֪ͨ�����Ĳ���֪ͨ���ȡ�������㲿����
        List partList = new ArrayList();
        List partList1 = new ArrayList();
     
        try
        {
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
        	PromulgateNotifyService pnservice=(PromulgateNotifyService)EJBServiceHelper.getService("PromulgateNotifyService");
            if(publishSourseObject instanceof PromulgateNotifyIfc)
            {
            	partList = (List)pnservice.getPartsByProId((PromulgateNotifyIfc)publishSourseObject);
                sourceType = "����֪ͨ��";
            }
          
            else if(publishSourseObject instanceof TechnicsRouteListIfc)
            {
            	//CCBegin by chudaming 20100920  dele
//                partList = (List)msservice.getPartByRouteList((TechnicsRouteListIfc)publishSourseObject);
            	//CCEnd by chudaming 20100920  dele
            	//CCBegin by chudaming 20100920
            	partList = (List)getPartByRouteList((TechnicsRouteListIfc)publishSourseObject);
            	
                String nu = getXmlName();
              
                sourceType = "";
                
            }
 
            logger.debug("publishSourseObject222222==============="
                    + publishSourseObject);
        }
        catch (Exception e)
        {
            //"ͨ������֪ͨ�����Ĳ���֪ͨ���ȡ�������㲿��ʧ�ܣ�"
            logger.error(Messages.getString("Util.22"), e);
            throw e;
        }
//      CCBegin SS5
//      ������20140102 ���һ���Ӽ�
        HashMap partTempNew = new HashMap();
        HashMap partTemp = new HashMap();
        
       // MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
		Iterator it = partList.iterator();
		PartHelper partHelper = new PartHelper();
		while (it.hasNext()) {
			QMPartIfc part = (QMPartIfc) it.next();
//			System.out.println("11111111111111111111111111111part1=============="+part);
//			System.out.println("collection000000000000000000000000000=============="+collection);
			Collection colPart = getAllSubParts(part);
			Iterator iter = colPart.iterator();
			while (iter.hasNext()) {
				QMPartIfc subpart = (QMPartIfc) iter.next();
				subpart=partHelper.filterLifeState(subpart);
				if(subpart!=null){
					if(!partTempNew.containsKey(subpart.getBsoID())){
						if (!partTemp.containsKey(subpart.getBsoID())) {
							partTemp.put(subpart.getBsoID(), part);
							partTempNew.put(subpart.getBsoID(), subpart);
							partList1.add(subpart);
//							System.out.println("collection11111111111111111111111111111=============="+collection);
//							System.out.println("subpart=============="+subpart);
						}
					}
					
				}
				
				
			}
			//System.out.println("11111111111111111111111111111part2=============="+part);
			if(!partTempNew.containsKey(part.getBsoID())){
				partList1.add(part);
				partTempNew.put(part.getBsoID(), part);
			}
			
			
		//	System.out.println("collection222222222222222222222222=============="+collection);
		}
		
        System.out.println("partList1********************"+partList1);
        System.out.println("partList********************"+partList1.size());
        //"����*���㲿����Ϣ��"
        logger.fatal(sourceType
                + Messages.getString("Util.61", new Object[]{String
                        .valueOf(partList.size())}));
        if(logger.isDebugEnabled())
        {
            logger.debug("partList==" + partList);
        }
        //������20140108 Ӧ�ò���Ҫˢ�����°汾
       // partList = getLatestParts(partList);
        //����ɸѡ������¼����������㲿��������ɸѡ���QMXMLMaterialSplit���ϴ���xmlMaterialSplitList�С�
        //�������ϴ�����������ϡ��������Ϊ�޸ĵ��㲿����Ӧ��filterPart�ŵ�filterPartMap�С�
        //���ݽṹ���������ṹ������ɸѡ���QMXMLStructure���ϴ���xmlStructureList�С�
        try
        {
        	//filterMaterials(partList);
            filterMaterials(partList1);
        }//CCEnd SS5
        catch (Exception e)
        {
            //"�������ϵķ�������ʱ����"
            logger.error(Messages.getString("Util.68"), e);
            throw e;
        }
        System.out.println("xmlMatSplitList********************"+xmlMatSplitList.size());
        System.out.println("filterPartMap********************"+filterPartMap.size());
        System.out.println("xmlMatStructList********************"+xmlMatStructList.size());
        if(logger.isDebugEnabled())
        {
            logger.debug("���ϴ�������xmlPartList����" + xmlMatSplitList);
            logger.debug("���ϴ�������updatePartMap����" + filterPartMap);
            logger.debug("�ṹ���������˺�xmlStructureList����" + xmlMatStructList);  
        }
        
        //�����������Ϣ�ֱ�����Ӧ��QMXMLData�У������õ�dataList�С�
        setDataRecord(xmlMatSplitList, xmlMatStructList);
        //�����˺��㲿������������ø�������������Ϊ����ɸѡ�����׼����
        BaseDataPublisher.xmlPartList = xmlMatSplitList;
        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials() - end"); //$NON-NLS-1$
        }
    }
	public Collection getPartByRouteList(TechnicsRouteListIfc list)
	throws QMException
	{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		PartHelper partHelper =  new PartHelper();
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
//		System.out.println("query**********************="+query.getDebugSQL());
//		System.out.println("coll***********************="+coll);
//		System.out.println("list***********************="+list);
		for(Iterator iter = coll.iterator(); iter.hasNext();)
		{
			ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)iter.next();
			//System.out.println("linkInfoaaaa***********************="+linkInfo);
			Collection cc = getPartByListRoutepart(linkInfo);
			//System.out.println("cc***********************="+cc);
			QMPartIfc partInfo = null;
			Iterator ii = cc.iterator();
			 while (ii.hasNext()) {
				 partInfo = (QMPartIfc)ii.next();
				 //CCBegin SS1
				 partInfo=partHelper.filterLifeState(partInfo);
				 if(partInfo==null)
			    	 continue;
				//CCEnd SS1
				// System.out.println("101010110111171777777ccccchhhhuuuu11111111partInfo==11111111="+partInfo.getPartNumber());

				 Vector aaa=new Vector();   
				 aaa=RequestHelper.getRouteBranchs(partInfo, null);
				 //CCBegin by chudaming 20100920
				 QMQuery qmquerykk1 = new QMQuery("FilterPart");
	 			 QueryCondition condition121 = new QueryCondition("versionValue", "=",
	 					partInfo.getVersionID());
	 			qmquerykk1.addCondition(condition121);
	 			qmquerykk1.addAND();
	 			qmquerykk1.addCondition(new QueryCondition("partNumber", "=",
	 					partInfo.getPartNumber()));
	 			//CCBegin by chudaming 20101021 ��֤�����һ�ε�FilterPart��¼���жԱ�
	 			qmquerykk1.addOrderBy("createTime",true);
	 			
	 			//CCEnd by chudaming 20101021 ��֤�����һ�ε�FilterPart��¼���жԱ�
	 			//CCBegin by chudaming 20101022
	 			//System.out.println("chudamingooooorrrrrrrrrrrrrrroooooooouuuuuuuuutttttttteeeeeeeeeee000===="+aaa);
//	 			System.out.println("chudamingooooorrrrrrrrrrrrrrroooooooouuuuuuuuutttttttteeeeeeeeeee000===="+aaa.size());
	 	          Collection col1 = ps.findValueInfo(qmquerykk1, false);
	 	         
	 	        //System.out.println("col12014===="+col1);
                 if(col1.size()>0){
    	 	         for (Iterator ii22 = col1.iterator();ii22.hasNext();) {
    		 	        	FilterPartIfc   aaaaaa=(FilterPartIfc)ii22.next();
   		 	        	//System.out.println("aaaaaa2014===="+aaaaaa.getRoute());
    		 	        	//System.out.println("aaa.get(1).toString()aaa.get(1).toString()888888888===="+aaa.get(1).toString());
   		 	       // System.out.println("aaa2014"+aaa);	
   		 	        	if(aaa.size()!=0&&aaaaaa.getRoute()!=null){
    		 	        		//CCBegin by chudaming 20101017
    		 	        	
    		 	        	String cgbs=(String)aaa.get(7);
    		 	        	//System.out.println("cgbs2014"+cgbs);
    		 	        	String routeAllCode = aaa.get(1).toString();
    		 	        	if (cgbs != null&&cgbs != "") {
    							if (cgbs.equals("X")) {
    								routeAllCode = "ë��-" + routeAllCode;
    							} else if (cgbs.equals("X-1")) {
    								routeAllCode = "���Ʒ-" + routeAllCode;
    							}
    						}
    		 	        	//System.out.println("routeAllCode2014"+routeAllCode);
    		 	        	 if(!(aaaaaa.getRoute().equals(routeAllCode))){
    		 	        		// System.out.println("1021111111111111111111111112012111111111====");
    		 	        		c.add(partInfo);
    		 	        	 }
    		 	        	}else if(aaa.size()!=0&&aaaaaa.getRoute()==null){
    		 	        		//System.out.println("ddddddddddddddddddddddddd===");
    		 	        			c.add(partInfo);
//    		 	        		
    		 	        	}else if(aaa.size()==0&&aaaaaa.getRoute()!=null){
    		 	        		c.add(partInfo);
    		 	        	}
    		 	        	break;
    		 	        	
    		 	         }
                 }else{//������20140108 ���filter��û�в鵽��ӽ�������ȥ
                	 c.add(partInfo);
                 }

	 	         
				
				 
			 }
//				for(Iterator ii = cc.iterator(); ii.hasNext(); c.add(partInfo))
//					partInfo = (QMPartIfc)ii.next();

		}
		//System.out.println("chudamingooooorrrrrrrrrrrrrrroooooooouuuuuuuuutttttttteeeeeeeeeee222222222222222222===="+c);
		return c;
	}
//	liujiakun 20131224 ����getCurrentEffctive�д�������
	private Collection getPartByListRoutepart(ListRoutePartLinkInfo linkInfo)
	throws QMException
	{
		try{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		QMPartInfo part = (QMPartInfo)ps.refreshInfo(linkInfo.getRightBsoID());
		QMQuery query = new QMQuery("QMPart");
		QueryCondition qc = new QueryCondition("bsoID", "=", part.getBsoID());
		query.addCondition(qc);
		// ��a��A�汾����a�汾bom����a��A�汾·�ߣ��޶�a������a����B�汾���ٷ�a�汾����׼��Ҫ�����B�汾�Ľṹ��֡�
		//��������ȡ·�߹�������汾�����·�߹����������汾 20131227
		String partVersion = part.getVersionID();
		if(partVersion != null && partVersion.length() > 0)
		{
//			Vector a=new Vector();
			QMQuery qmquery = new QMQuery("FilterPart");
			 
	          qmquery.addCondition(new QueryCondition("partNumber", "=",
	        		  part.getPartNumber()));
	          
	          qmquery.addOrderBy("versionValue",false);
	          Collection col1 = ps.findValueInfo(qmquery, false);
	          Iterator iter22 = col1.iterator();
	          FilterPartInfo ifc =null;
//	          System.out.println("ssssssssvvvvvvvvvvvvvvvvvvvvvvv==="+col1);
//	          System.out.println("ssssssssvvvvvvvvvvvvvvvvvvvvvvv==="+col1.size());
	          if(col1!=null){
	        	  if(col1.size()>0){
 	        	 while (iter22.hasNext()) {
 	        		ifc=(FilterPartInfo)iter22.next();
// 	        		a.add(ifc);
 	        	 }
 	        	 }
                 }
	          //System.out.println("ssssssss===");
	          if(ifc!=null){
	        	//  System.out.println("ssssssss==ffffffffffffffffffff=");
	          if(partVersion
	     	         .compareTo(
	     	        		ifc.getVersionValue()) < 0)
	          {
	        	  //System.out.println("ssssssss==gggggggggggggggggggggggggg=");
	        	  QueryCondition qc1 = new QueryCondition("versionID", "=", ifc.getVersionValue());
	        	 // System.out.println("ssssssss==hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh=");
	  			query.addAND();
	  			query.addCondition(qc1);
	          }else{
	        	  QueryCondition qc1 = new QueryCondition("versionID", "=", partVersion);
	  			query.addAND();
	  			query.addCondition(qc1);
	          }
	          }else{
			QueryCondition qc1 = new QueryCondition("versionID", "=", partVersion);
			query.addAND();
			query.addCondition(qc1);
	          }
		}
		//CCEnd by chudaming 20101019 for ��a��A�汾����a�汾bom����a��A�汾·�ߣ��޶�a������a����B�汾���ٷ�a�汾����׼��Ҫ�����B�汾�Ľṹ��֡�
		QueryCondition qc2 = new QueryCondition("iterationIfLatest", true);
		query.addAND();
		query.addCondition(qc2);
		//System.out.println("queryVVVVVVVVVVVVVV="+query.getDebugSQL());
		return ps.findValueInfo(query, false);
		}catch(QMException e){
			e.printStackTrace();
			throw e;
		}
	}
	
    /**
     * ���ݻ��ߵõ��ܻ��߹�����㲿������
     * @param baseline ����
     * @param coll Ҫ�������㲿���ļ���
     * @return List �ܻ��߹�����㲿���ļ���
     * @throws QMException
     */
    private List getpartsByBaseline(ManagedBaselineIfc baseline,Collection partIDs) throws QMException
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
    //������ֱ���Ӽ�����1000ʱ���������ݲ�ȫ��CCBegin by chudaming  20091221
    private Collection getSubParts(Collection coll, ManagedBaselineIfc baseline)
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
			   int j = query.appendBso("BaselineLink", false);
			   int m = query.appendBso("QMPartMaster", false);
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
			   //query.addCondition(i, new QueryCondition("rightBsoID", "IN", masterids));
			   query.addAND();
			   query.addCondition(m, i, new QueryCondition("bsoID", "leftBsoID"));
			   query.addAND();
			   query.addCondition(j, new QueryCondition("rightBsoID", "=", baseline.getBsoID()));
			   query.addAND();
			   query.addCondition(0, j, new QueryCondition("bsoID", "leftBsoID"));
			   query.addAND();
			   query.addCondition(0, m, new QueryCondition("masterBsoID", "bsoID"));
			   coll1 = pservice.findValueInfo(query);
			   return coll1;

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

	/**
     * ����ɸѡ������¼����������㲿��������ɸѡ���QMXMLMaterialSplit���ϴ���xmlMatSplitList�С�
     * �������ϴ�����������ϡ��������Ϊ�޸ĵ��㲿����Ӧ��filterPart�ŵ�filterPartMap�С�
     * ���ݽṹ���������ṹ������ɸѡ���QMXMLStructure���ϴ���xmlStructureList�С�
     * @param partList ��ɸѡ���㲿�����ϡ�
     * @return ɸѡ���QMXMLMaterialSplit���ϡ�
     * @throws QMXMLException
     */
    private final void filterMaterials(final List partList) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials(List) - start"); //$NON-NLS-1$
        }
        //�㲿����Ψһ��ʶ
        String partIdentity;
        //�㲿����ֵ����
        QMPartIfc partIfc;
        //���ͨ��Ψһ��ʶɸѡ����㲿����key���㲿����Ψһ��ʶ��value���㲿����ֵ����
        HashMap partsMap = new HashMap();
       // System.out.println("���Ｔ��Ҫ������㲿������Ϊ��"+partList.size());
        for (int i = 0; i < partList.size(); i++)
        {
            partIfc = (QMPartIfc) partList.get(i);
            //1 ɸѡ������¼�������1���ȸ���ɸѡ��Ψһ��ʶ�����㲿�����ϣ��ظ��ļ�¼��ɸѡ�������ٷ�����
            //�����ڸ��Ĳ���֪ͨ����Լ�¼ͬһ���汾�Ĳ�ͬ������������Ҫ�����ˣ�����ͬ�汾��ֻ��һ��
            //��ȡ�㲿����Ψһ��ʶ
            //CCBegin SS3
            String[] aa = new String[2];
            aa = getPartIdentity(partIfc);
            partIdentity=aa[0];
            String partnumber = aa[1];
            //CCEnd SS3
           // if(partIfc.getPartNumber().equals("1701110-A4K")){
            System.out.println("partIdentity********************"+partIdentity);
            System.out.println("partsMap********************"+partsMap);
            System.out.println("partIfc********************"+partIfc.getPartNumber());
      //  }
            if(!partsMap.containsKey(partIdentity))
            {
            	
                //2 ��ȡ������ʶ
            	//CCBegin by dikefeng 20100423,��Ϊͨ����¼�Ѿ���¼�˱��β�ֵĺ�Ҫ�������㲿�����ϣ���������Ͳ���ȥ�жϷ�������
//                String publishType = getPublishType(partIfc);
//                if(publishType != null)
//                {
                    partsMap.put(partIdentity, partIfc);
                    //�����¼����Ϻ����Ͻṹ�ķ�������
                    setSubMaterial(partIfc, null, partsMap,partnumber);
//                }
      
            }
        }
        //"��Ҫ����*���㲿����Ϣ��"
        logger.fatal(sourceType
                + Messages.getString("Util.62", new Object[]{String
                        .valueOf(partsMap.values().size())}));
        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials(List) - end"); //$NON-NLS-1$
        }
    }

    /**
     * �������ϴ�����������ϡ�
     *
     * ���ϴ�����򣺲�ѯɸѡ�����������
     * 1.�������ݲ����ڣ����Ϊ����N��
     * 2.�������ݴ��ڣ����汾��a�汾�仯�����Ϊ�޸�U���Խṹ���д���
     *                        b�汾û�б仯��1״̬�仯�����Ϊ�ط�Z��������ṹ��
     *                                     2״̬û�б仯��������
     * ע�⣺�������޸���filterPartMap��
     * @param partIfc Ҫ�����㲿����ֵ����
     * @return ���㲿����ֺ�����Ϸ������
     * @throws QMXMLException
     */
    private String getPublishType(QMPartIfc partIfc) throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPublishType(QMPartIfc) - start");
        }
        //�㲿����ֺ�����Ϸ������
        //CCBegin by dikefeng 20090210
        //��20090205�����������ͶŽ���ȷ�ϣ�������������ʱ�������Ƿ��Ѿ��������������з���������PDMϵͳ�в����й���
        //��20090209���羭��Ž������ȷ�ϣ�ERP����PDM����ʱ��������ȡPDM��������ʱ�ĸ��ı�ǣ����Ծ����Ը��ı��Ĭ��
        //ֵ�����޸ģ��Ա㷽����޸ĺ������
        String publishType =null;
        //CCEnd by dikefeng 20090210
        List tempPartList = new ArrayList();
        //���˷����㲿��ֵ����
        FilterPartIfc filterPartIfc = null;
        List filterPartList = new ArrayList();
        //1 �ñ����Ϊ��ѯ�����������Ƿ��з���������FilterPart��
        try
        {
            filterPartList = getFilterPart(partIfc.getPartNumber());
        }
        catch (QMException e)
        {
            //"���ұ��Ϊ*��FilterPartʱ����"
            logger.error(Messages.getString("Util.15", new Object[]{partIfc
                    .getPartNumber()})
                    + e);
            throw new QMXMLException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("filterPartList==" + filterPartList);
        }
        //2 �Ƚ���������filterpart�İ汾������µİ汾���ھɵ����ٷ�����
        FilterPartIfc tempFilterPart = null;
        if(filterPartList != null && filterPartList.size() > 0)
        {
            for (int j = 0; j < filterPartList.size(); j++)
            {
                filterPartIfc = (FilterPartIfc) filterPartList.get(j);
                if(partIfc.getVersionID().compareTo(
                        filterPartIfc.getVersionValue()) < 0)
                {
                    tempPartList.add(partIfc);
                    break;
                }
                else
                {
                    if(tempFilterPart == null)
                    {
                        tempFilterPart = filterPartIfc;
                    }
                    else if(tempFilterPart.getVersionValue().compareTo(
                            filterPartIfc.getVersionValue()) < 0)
                    {
                        tempFilterPart = filterPartIfc;
                    }
                }
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("xmlPart.getPartNumber()==" + partIfc.getPartNumber());
        }
        //3 �жϷ������
        //���ϴ������1���������ݲ����ڣ����Ϊ����N��
        if(tempFilterPart == null)
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("---------���ϴ������1");
            }
//            System.out.println("tempPartListtempPartList========"+tempPartList);
//            System.out.println("tempPartListtempPartList========"+partIfc.getPartNumber());
            if(!tempPartList.contains(partIfc))
            {
                publishType = "N";
            }
        }
        //���ϴ������2���������ݴ��ڣ����汾��
        else
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("---------���ϴ������2");
            }
            //���ϴ������2.b���汾û�б仯��
            if(partIfc.getVersionID().equals(tempFilterPart.getVersionValue()))
            {
                if(logger.isDebugEnabled())
                    logger.debug("---------���ϴ������2.b");
                if(!partIfc.getLifeCycleState().getDisplay().equals(tempFilterPart.getState()))
                {
                    if(logger.isDebugEnabled())
                        logger.debug("---------���ϴ������2.b.1");
                    filterPartMap.put(partIfc.getPartNumber(), tempFilterPart);
                    publishType = "Z";
                }
                try
                {
                    MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
                    Vector routevec = RequestHelper.getRouteBranchs(partIfc, null);
                    String routeAsString = "";
                    String routeAllCode = "";
                    String routeAssemStr = "";
                    if(routevec.size() != 0)
                    {
                        routeAsString = (String)routevec.elementAt(0);
                        routeAllCode = (String)routevec.elementAt(1);
                    }
                    String aaa = tempFilterPart.getRoute();
                    boolean flag1 = aaa == null || aaa == "";
                    boolean flag2 = routeAllCode == null || routeAllCode == "";
                    if(!flag2 || !flag1)
                        if(!flag2 && flag1)
                        {
                            filterPartMap.put(partIfc.getPartNumber(), tempFilterPart);
                            publishType = "Z";
                        } else
                        if(flag2 && !flag1)
                        {
                            filterPartMap.put(partIfc.getPartNumber(), tempFilterPart);
                            publishType = "Z";
                        } else
                        if(!routeAllCode.equals(tempFilterPart.getRoute()))
                        {
                            filterPartMap.put(partIfc.getPartNumber(), tempFilterPart);
                            publishType = "Z";
                        }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    throw new QMXMLException(ex);
                }
            }
            //���ϴ������2.a���汾�仯�����Ϊ�޸�U���Խṹ���д���
            else
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("---------���ϴ������2.a");
                }
                filterPartMap.put(partIfc.getPartNumber(), tempFilterPart);
                //���������ϵĸ��ı��
                publishType = "U";
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPublishType(QMPartIfc) - end" + publishType);
        }
        return publishType;
    }

    /**
     *  ��ȡ���㲿���ֽ������ϼ��ϡ�
     *  ���isXMLΪtrue����ͬʱ������ֵ�����װ��һ��XML��¼��
     * @param partIfc ��ֵ��㲿����
     * @param matPubType ������ʶ��
     * @param partMap �㲿�����ϡ�
     * @return Collection ���㲿�������ϼ��ϡ�
     * @throws QMXMLException
     */
    private final void setSubMaterial(QMPartIfc partIfc, String matPubType,
            HashMap partMap,String partnumber) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setSubMaterial(String, String, String , HashMap) - start");
        }
      //  System.out.println("qqqqqqqqqqqqqqqqqqqqq==============");
        MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
        //����ֵ����
        MaterialSplitIfc matSplitIfc;
        //����ֵ�����XML��ʽ
        QMXMLMaterialSplit xmlMatSplit;
        //�㲿����ֵ����ϵ��������
        //CCBegin SS3
        //Collection rootList=(Collection)msservice.getRootMSplit(partIfc.getPartNumber());
        Collection rootList=(Collection)msservice.getRootMSplit(partnumber);
        //CCEND SS3
        if(partIfc.getPartNumber().equals("1701516A8A")){
        System.out.println("rootList1111111********************"+rootList);
        }
        Iterator rootMatSplitIter = rootList.iterator();
        //20080122 begin
        HashMap notPublishMat=new HashMap();
        //20080122 end
     
        //��ö�������
        while (rootMatSplitIter.hasNext())
        {
            matSplitIfc = (MaterialSplitIfc) rootMatSplitIter.next();
            //20080122 begin
            boolean flag=isParentHasSpeRoute(matSplitIfc,partIfc);
//            System.out.println("flag********************"+flag);
//            System.out.println("matSplitIfc.getPartNumber()********************"+matSplitIfc.getPartNumber());
//            System.out.println("partIfc.getPartNumber()********************"+partIfc.getPartNumber());
            logger.debug("flag is "+flag);
            logger.debug("matSplitIfc.getPartNumber() is "+matSplitIfc.getPartNumber());
            logger.debug("partIfc.getPartNumber() is "+partIfc.getPartNumber());
            //�����������
            if(notPublishMat.containsKey(matSplitIfc.getPartNumber())||
            		!flag){
            	// System.out.println("matSplitIfc.getPartNumber()==========="+matSplitIfc.getPartNumber());
            	notPublishMat.put(matSplitIfc.getPartNumber(), matSplitIfc.getPartNumber());
            	continue;
            }
           // System.out.println("notPublishMat==========="+notPublishMat);
            
            //20080122 end
            matSplitIfc.setPartVersion(partIfc.getVersionID());
//            System.out.println("flagflagflagflag==========="+flag);
            if(partIfc.getPartNumber().equals("1701516A8A")){
            	System.out.println("matSplitIfc.getMaterialNumber()==========="+matSplitIfc.getMaterialNumber());
            }
            //System.out.println("matSplitIfc.getMaterialNumber()==========="+matSplitIfc.getMaterialNumber());
            if(!hasSetSplitPubTypeMap.containsKey(matSplitIfc
                    .getMaterialNumber()))
            {
                xmlMatSplit = new QMXMLMaterialSplit(matSplitIfc);
                xmlMatSplit.setPartIfc(partIfc);
//                System.out.println("xmlMatSplitxmlMatSplit00000000000000000=============="+matSplitIfc.getBsoID());
//                System.out.println("xmlMatSplitxmlMatSplit0000000000000000022222222222222222222222=============="+matSplitIfc.getPartVersion());
//                System.out.println("xmlMatSplitxmlMatSplit00000000000000000=============="+matSplitIfc.getMaterialSplitType());
                xmlMatSplit.setPublishType(matSplitIfc.getMaterialSplitType());
                //CCEnd by chudaming 20100329
                xmlMatSplit.setPartVersionID(partIfc.getVersionID());
                xmlMatSplitList.add(xmlMatSplit);
                hasSetSplitPubTypeMap.put(matSplitIfc.getMaterialNumber(),
                        xmlMatSplit);
//              CCBegin SS3
//                System.out.println("nizhenniu555555555555555555555555555555------------------------buxinzhaobudaoni--------------");
//                setSubMatStructPubType(partIfc.getPartNumber(), partIfc
//                        .getVersionID(), xmlMatSplit, partMap);
                setSubMatStructPubType(partnumber, partIfc
                        .getVersionID(), xmlMatSplit, partMap);
//              CCEND SS3
//                System.out.println("nizhenniu-666666666666666666666666-----------------------buxinzhaobudaoni--------------");
            }
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setSubMaterial(String, String, String , HashMap) - end");
        }
    }

    //���һ���㲿����·���д��ɣ������еĸ�����·���д��вɣ��򲻷�������㲿����
    private boolean isParentHasSpeRoute(MaterialSplitIfc matSplitIfc,
			QMPartIfc subPartIfc ) throws QMException {
    	return true;
	}

    /**
	 * �����¼����Ͻṹ�����ϵķ�����ǡ�
	 *
	 * @param rootPartNumber���������ϵ��㲿����š�
	 * @param rootPartVersionID���������ϵ��㲿���汾��
	 * @param matSplitIfc������ֵ�����XML��ʽ
	 * @param partMap������㲿��ֵ�����HashMap
	 * @throws QMException
	 */
    private void setSubMatStructPubType(String rootPartNumber,
            String rootPartVersionID, QMXMLMaterialSplit xmlMatSplit,
            HashMap partMap) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setSubMatStructPubType(QMXMLMaterialSplit, HashMap) - start");
        }
        // �����¼����Ͻṹ���¼����ϵķ������
        //1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���0-��ײ�����
        if(xmlMatSplit.getMaterialSplit().getStatus() == 1)
        {
            //�������
            String publishType = xmlMatSplit.getPublishType();
            //�õ������ϵ��¼����ϣ�����ط�ֻ�ǻ���˶������ϵ���һ������
            List list = getMatStruct(xmlMatSplit.getPartNumber(), xmlMatSplit
                    .getMaterialNumber());
            //���Ͻṹֵ����
            MaterialStructureIfc matStructIfc;
            //���Ͻṹֵ����XML��ʽ
            QMXMLMaterialStructure xmlMatStruct;
            //�¼�����ֵ����
            MaterialSplitIfc subMatSplitIfc;
            //�¼�����ֵ����XML��ʽ
            QMXMLMaterialSplit subXMlMatSplit;
            //�㲿��ֵ����
            QMPartIfc partIfc = null;
            String partIdentity = "";
            for (int i = 0; i < list.size(); i++)
            {
                matStructIfc = (MaterialStructureIfc) list.get(i);
                if(!hasSetStrutPublishType.containsKey(matStructIfc.getBsoID()))
                {
                    //�����¼����Ͻṹ�ķ������
                    xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
                    if(matStructIfc.getParentPartNumber().equals(
                            xmlMatSplit.getPartNumber()))
                    {
                        xmlMatStruct.setParentPartVersion(rootPartVersionID);
                    }
                    //�����ϵķ������Ϊ"Z"ʱ�����Ͻṹ�ķ������Ϊ"U"questions
                    //CCBegin by dikefeng 20100422,�����־ɵ����÷�ʽȥ��
//                    if(publishType.equals("Z"))
//                    {
//                        xmlMatStruct.setStructurePublishType("U");
//                    }
//                    else
//                    {
//                        xmlMatStruct.setStructurePublishType(publishType);
//                    }
                    xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
                    //CCEnd by dikefeng 20100422
                    hasSetStrutPublishType.put(matStructIfc.getBsoID(),
                            xmlMatStruct);
                    xmlMatStructList.add(xmlMatStruct);
                    subMatSplitIfc = getMatSplitIfc(matStructIfc.getChildNumber());    
                    if(subMatSplitIfc.getPartNumber().equals(rootPartNumber))
                    {
                        subMatSplitIfc.setPartVersion(rootPartVersionID);
                    }
                    //�����¼����ϵķ������
                    if(!hasSetSplitPubTypeMap.containsKey(subMatSplitIfc
                            .getMaterialNumber()))
                    {
                        partIfc = getPartIfc(subMatSplitIfc, partMap);
                        partIdentity = getPartIdentity(subMatSplitIfc);
                        partMap.put(partIdentity, partIfc);
                        //CCBegin SS4
                        if(!partNumberMap.containsKey(partIfc.getPartNumber())){
                        	partNumberMap.put(partIfc.getPartNumber(), subMatSplitIfc.getPartNumber());
                        }
                        //CCEnd SS4
                        subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);
                       // subXMlMatSplit.setPublishType(publishType);
                        //CCBegin by chudaming 20100331questions������
//                        System.out.println("fdsfsdfdsfsdfsdf");
                        subXMlMatSplit.setPublishType(subMatSplitIfc.getMaterialSplitType());
                        //CCEnd by chudaming 20100331
                        subXMlMatSplit.setPartIfc(partIfc);
                        if(subXMlMatSplit.getPartNumber()
                                .equals(rootPartNumber))
                        {
                            subXMlMatSplit.setPartVersionID(rootPartVersionID);
                        }
//                        subXMlMatSplit.setPartVersionID(partVersionID);
                        hasSetSplitPubTypeMap.put(subMatSplitIfc
                                .getMaterialNumber(), subXMlMatSplit);
                        xmlMatSplitList.add(subXMlMatSplit);
                        //ѭ����ѯ��һ������
                        setSubMatStructPubType(rootPartNumber,
                                rootPartVersionID, subXMlMatSplit, partMap);
                    }
                }
            }
            //CCBegin by dikefeng 20100421����ǰ����״̬���Ϊ1ʱ��˵�������������ڱ��������һ�����ϣ���ôֻҪ�����������¼�����Ĺ�ϵ
            //��ɾ��,���״̬���Ϊ2��0��˵�������ڱ�������¼����ϡ�Ϊ2ʱ��Ӧ�ý���ǰ�Ӽ��ṹ��ɽṹ���бȽϣ�������û�еĽṹ���ΪD
            //�����е�����û�б仯�����Ϊo�������е��������仯�˵ı��ΪU��Ϊ0ʱ��˵��û���¼�����������֮ǰ�ķ��������¼�·��
            //ֱ�ӽ����нṹ���ΪD
            //�˴�û������ɾ������û����
            
            QMQuery materialPartStruQuery=new QMQuery("MaterialPartStructure");
            materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
            materialPartStruQuery.addAND();
            materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
            PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
            Collection mpsColl=pService.findValueInfo(materialPartStruQuery);
            Iterator mpsIte=mpsColl.iterator();
            while(mpsIte.hasNext())
            {
            	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)mpsIte.next();
//            	System.out.println("kuku������������ô��-------------------------------------------");
            	setDeleteMaterialPartStructure(mpsIfc);
            	pService.deleteValueInfo(mpsIfc);
            }
            //CCEnd by dikefeng 20100421
        }
        //�����㲿��ʹ�ýṹ�ķ������
        //CCBegin by dikefeng 20090619,�����Ӽ��ṹʱ����Ҫ������ɾ���Ľṹ��Ϣ
        else if(xmlMatSplit.getMaterialSplit().getStatus() == 2||xmlMatSplit.getMaterialSplit().getStatus() == 0)
        //CCEnd by dikefeng 20090619
        {
            filterByStructureRule(rootPartNumber, rootPartVersionID,
                    xmlMatSplit,partMap);
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setSubMatStructPubType(QMXMLMaterialSplit, HashMap) - end");
        }
    }

    /**
     * �õ�����ֵ�����ֵ��㲿����Ϣ����parts�еõ�����ˢ�����ݿ�õ���
     * @param matSplitIfc:����ֵ����
     * @param partMap
     * @return ����ֵ�����ֵ��㲿����Ϣ
     * @throws QMException
     */
    private QMPartIfc getPartIfc(MaterialSplitIfc matSplitIfc, HashMap partMap)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIfc(MaterialSplitIfc, HashMap) - start");
        }
        //�㲿��ֵ����
        QMPartIfc partIfc = null;
        //�㲿����Ψһ��ʶ
        String partIdentity = getPartIdentity(matSplitIfc);
        //����㲿����parts��
//        System.out.println("partMap====="+partMap);
//        System.out.println("partIdentity====="+partIdentity);
        if(partMap.containsKey(partIdentity))
        {
            partIfc = (QMPartIfc) partMap.get(partIdentity);
           
        }
        //����㲿������parts�У�������ݿ���ˢ��
        else
        {
        	//CCBegin SS3
        	String[] a = matSplitIfc.getPartNumber().split("/");
        	String partnumber= a[0];
            Collection partCol = getPartCol(partnumber,
                    matSplitIfc.getPartVersion());
            //CCEnd SS3
            Iterator partIter = partCol.iterator();
            if(partIter.hasNext())
            {
                try
                {
                    partIfc = (QMPartIfc) getLatestIteration((QMPartIfc) partIter
                            .next());
                }
                catch (QMException e)
                {
                    //"���ұ��Ϊ*���汾Ϊ*�������㲿������ʱ����"
                    logger.error(Messages.getString("Util.63", new Object[]{
                            matSplitIfc.getPartNumber(),
                            matSplitIfc.getPartVersion()})
                            + e);
                    throw new QMException(e);
                }
            }
        }
        if(partIfc == null)
        {
            //"�޷��õ����Ϻ�Ϊ*�����ϲ�ֵ��㲿������Ϣ!"
            logger.error(Messages.getString("Util.64", new Object[]{matSplitIfc
                    .getMaterialNumber()}));
            throw new QMException(Messages.getString("Util.64",
                    new Object[]{matSplitIfc.getMaterialNumber()}));
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIfc(MaterialSplitIfc, HashMap) - end");
        }
        if(!partNumberMap.containsKey(partIfc.getPartNumber())){
        	partNumberMap.put(partIfc.getPartNumber(), matSplitIfc.getPartNumber());
        }
        
        return partIfc;
    }

    /**
     * ���ݽṹ������򣬴���ṹ������ɸѡ���QMXMLStructure���ϴ���xmlMatStructList�С�
     *
     * ע1��������ʹ�ýṹ�У�ͬһ�����ܴ��ڶ����ͬ��������ͬ�Ӽ��������ڴ���ṹǰ��
     * Ҫ�ȶԸ�������д������Ƚ�ԭ�ṹ�е��Ӽ������ϲ����½ṹ�е��Ӽ�����ԭ����
     * ���������ӹ���Ȼ��ԭ�ṹ�е��Ӽ������½ṹ�е��Ӽ����бȽϣ�
     * ͷһ���Ӽ�������������ĵڶ�������������������ͬ�Ӽ�����Ϊ�����Ӽ�����
     *
     * �ṹ����������㲿���İ汾�仯ʱʹ�ô˹���
     * 1.����Ϊ����ʱ���ṹ��Ϣ�������Ӽ��������ṹ��ϵ���Ϊ����N��
     * 2.����Ϊ�汾�仯ʱ�������ݵ��Ӽ�����и����ݵİ汾���Ӽ����бȽϣ�
     *   a�µ��������ԭ�����в����ڣ��ṹ���ݱ��Ϊ����N��
     *   b�µ��������ԭ�����д��ڣ�1��ʹ��������ͬ�����Ϊ����O��
     *                           2��ʹ��������ͬ�����Ϊ��������U��
     *   cԭ����������������в����ڣ����Ϊȡ��D��
     * ע2�����Խṹ���еݹ鴦��ֻ�����һ���ṹ��
     * @param rootPartNumber �������ϵ��㲿�����
     * @param rootPartVersionID �������ϵ��㲿���汾
     * @param xmlMatSplit Ҫ�����㲿��ʹ�ýṹ������ǵ�����ֵ�����XML��ʽ��
     * @throws QMException
     */
    public final void filterByStructureRule(String rootPartNumber,
            String rootPartVersionID, QMXMLMaterialSplit xmlMatSplit,HashMap partMap)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByStructureRule(QMXMLMaterialSplit) - start");
        }
        //�������
        final String publishType = xmlMatSplit.getPublishType();
        //CCBegin by dikefeng 20100421
        PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
        //CCEnd by dikefeng 20100421
//        System.out.println("publishTypepublishType====="+publishType+"xmlMatSplitxmlMatSplitxmlMatSplit222"+xmlMatSplit.getMaterialNumber());
        //�µ�ʹ�ù�ϵ��ֵ����
        PartUsageLinkIfc newUsageLinkIfc;
        //ԭ��ʹ�ù�ϵ��ֵ����
        PartUsageLinkIfc oldUsageLinkIfc;
        //��ʱ��ʹ�ù�ϵ��ֵ����
        PartUsageLinkIfc tempUsageLinkIfc;
        //�������㲿����ʹ�ýṹ
        HashMap newUsageLinkMap = getUsageLinkMap(xmlMatSplit.getPartIfc());
        //�ṹ�������1������Ϊ����ʱ���ṹ��Ϣ�������Ӽ��������ṹ��ϵ���Ϊ����N��questions
        //ȫ������Ϊ Nû�����⣬ֻ�����������������Ϊ·�߱仯������ΪN����Ӧ�ý�ԭ�ӽڵ�·�������ṹɾ��
        if(publishType.equals("N"))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("---------�ṹ�������1");
            }
            //�����㲿���ṹ�ĸ��ı��:
            //�����ϵĸ��ı��Ϊ����ʱ���㲿���ṹ�ĸ��ı�Ǻ����ϵĸ��ı��һ�¡�
            if(xmlMatSplit.getMaterialSplit().getStatus()==2)
            {
            Iterator usageLinkIter = newUsageLinkMap.values().iterator();
            while (usageLinkIter.hasNext())
            {
                newUsageLinkIfc = (PartUsageLinkIfc) usageLinkIter.next();
                setPartStructPubType(rootPartNumber, rootPartVersionID, "N",
                        xmlMatSplit, newUsageLinkIfc);
            //��������ҪΪ�µĽṹ�����������
            //CCBegin by dikefeng  20100421,�������
             MaterialPartStructureInfo mpsInfo=new MaterialPartStructureInfo();
             mpsInfo.setParentPartNumber(xmlMatSplit.getMaterialSplit().getPartNumber());
             mpsInfo.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
             mpsInfo.setParentNumber(xmlMatSplit.getMaterialSplit().getMaterialNumber());
             mpsInfo.setLevelNumber("0");
             mpsInfo.setDefaultUnit("��");
             mpsInfo.setMaterialStructureType("O");
             mpsInfo.setOptionFlag(false);
             mpsInfo.setQuantity(newUsageLinkIfc.getQuantity());
             List rootMatNumberList = getRootMatSplit(newUsageLinkIfc
                     .getLeftBsoID(), xmlMatSplit.getRouteCode());
//         	System.out.println("00000000000000000000kuku������������ô��-------------------------------------------");
             for (int i = 0; i < rootMatNumberList.size(); i++)
             {
                 String childNumber = (String) rootMatNumberList.get(i);
                 mpsInfo.setChildNumber(childNumber);
                 pService.saveValueInfo(mpsInfo);
             }
            //CCEnd by dikefeng 20100421
            }
            }
        }
        else if(publishType.equals("D"))
        {
        	//CCBegin by dikefeng 20100421����ǰ����״̬���Ϊ1ʱ��˵�������������ڱ��������һ�����ϣ���ôֻҪ�����������¼�����Ĺ�ϵ
            //��ɾ��,���״̬���Ϊ2��0��˵�������ڱ�������¼����ϡ�Ϊ2ʱ��Ӧ�ý���ǰ�Ӽ��ṹ��ɽṹ���бȽϣ�������û�еĽṹ���ΪD
            //�����е�����û�б仯�����Ϊo�������е��������仯�˵ı��ΪU��Ϊ0ʱ��˵��û���¼�����������֮ǰ�ķ��������¼�·��
            //ֱ�ӽ����нṹ���ΪD
        	if(xmlMatSplit.getMaterialSplit().getStatus()==2)
        	{
            QMQuery materialPartStruQuery=new QMQuery("MaterialPartStructure");
            materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
            materialPartStruQuery.addAND();
            materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
            Collection mpsColl=pService.findValueInfo(materialPartStruQuery);
            Iterator mpsIte=mpsColl.iterator();
            while(mpsIte.hasNext())
            {
            	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)mpsIte.next();
//           	System.out.println("111122222222222kuku������������ô��-------------------------------------------");
            	setDeleteMaterialPartStructure(mpsIfc);
            	pService.deleteValueInfo(mpsIfc);
            	//questions������ӷ���ɾ����Ϣ�ĳ���
            }
        	}
            //CCEnd by dikefeng 20100421
            
        }
        //�ṹ�������2������Ϊ�汾�仯ʱ�������ݵ��Ӽ�����и����ݵİ汾���Ӽ����бȽϡ�
        //CCBegin by chudaming 20100928 
        else if(publishType.equals("U"))
//        		||publishType.equals("Z")
       
        {
        	 //CCEnd by chudaming 20100928 
//        	System.out.println("jinru____________UUUUUU============"+xmlMatSplit.getMaterialSplit().getStatus());
            if(logger.isDebugEnabled())
            {
                logger.debug("---------�ṹ�������2");
            }
            //CCBegin by dikefeng 20100421����ǰ����״̬���Ϊ1ʱ��˵�������������ڱ��������һ�����ϣ���ôֻҪ�����������¼�����Ĺ�ϵ
            //��ɾ��,���״̬���Ϊ2��0��˵�������ڱ�������¼����ϡ�Ϊ2ʱ��Ӧ�ý���ǰ�Ӽ��ṹ��ɽṹ���бȽϣ�������û�еĽṹ���ΪD
            //�����е�����û�б仯�����Ϊo�������е��������仯�˵ı��ΪU��Ϊ0ʱ��˵��û���¼�����������֮ǰ�ķ��������¼�·��
            //ֱ�ӽ����нṹ���ΪD
            if(xmlMatSplit.getMaterialSplit().getStatus()==0)
            {
//            	System.out.println("xmlMatSplit.getMaterialSplit().getStatus()=========0000000");
            	//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA���������Ϊ�˴���ԭ�еı������ڽṹ
            	MaterialStructureIfc matStructIfc;
                //���Ͻṹֵ����XML��ʽ
                QMXMLMaterialStructure xmlMatStruct;
                //�¼�����ֵ����
                MaterialSplitIfc subMatSplitIfc;
                //�¼�����ֵ����XML��ʽ
                QMXMLMaterialSplit subXMlMatSplit;
                //�㲿��ֵ����
                QMPartIfc partIfc = null;
                String partIdentity = "";
            	List oldMaterialList = getMatStruct(xmlMatSplit.getPartNumber(), xmlMatSplit.getMaterialNumber());
            	Iterator oldMaterialIte=oldMaterialList.iterator();
            	while(oldMaterialIte.hasNext())
            	{
            		matStructIfc = (MaterialStructureIfc)oldMaterialIte.next();
                        //�����¼����Ͻṹ�ķ������
                        xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
                        if(matStructIfc.getParentPartNumber().equals(
                                xmlMatSplit.getPartNumber()))
                        {
                            xmlMatStruct.setParentPartVersion(rootPartVersionID);
                        }
                        //�����ϵķ������Ϊ"Z"ʱ�����Ͻṹ�ķ������Ϊ"U"questions
                        //CCBegin by dikefeng 20100422,�����־ɵ����÷�ʽȥ��
//                        if(publishType.equals("Z"))
//                        {
//                            xmlMatStruct.setStructurePublishType("U");
//                        }
//                        else
//                        {
//                            xmlMatStruct.setStructurePublishType(publishType);
//                        }
                        xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
                        //CCEnd by dikefeng 20100422
                        xmlMatStructList.add(xmlMatStruct);
                        subMatSplitIfc = getMatSplitIfc(matStructIfc
                                .getChildNumber());
                        if(subMatSplitIfc.getPartNumber().equals(rootPartNumber))
                        {
                            subMatSplitIfc.setPartVersion(rootPartVersionID);
                        }
                            partIfc = getPartIfc(subMatSplitIfc, partMap);
                            partIdentity = getPartIdentity(subMatSplitIfc);
                            partMap.put(partIdentity, partIfc);
                            subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);
                           // subXMlMatSplit.setPublishType(publishType);
                            //CCBegin by chudaming 20100331questions������
//                            System.out.println("cccccccccccccvvvvvvvvvvvvvvvvvvcbn");
                            subXMlMatSplit.setPublishType(subMatSplitIfc.getMaterialSplitType());
                            //CCEnd by chudaming 20100331
                            subXMlMatSplit.setPartIfc(partIfc);
                            if(subXMlMatSplit.getPartNumber()
                                    .equals(rootPartNumber))
                            {
                                subXMlMatSplit.setPartVersionID(rootPartVersionID);
                            }
//                            subXMlMatSplit.setPartVersionID(partVersionID);
                            xmlMatSplitList.add(subXMlMatSplit);
//                            System.out.println("nizhenniu------------------------buxinzhaobudaoni--------------");
                            setSubMatStructPubType(rootPartNumber,
                                    rootPartVersionID, subXMlMatSplit, partMap);
//                            System.out.println("11111111111111111111111nizhenniu------------------------buxinzhaobudaoni--------------");
            	}
            	//BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB���������Ϊ��ɾ��ԭ�еĿ�����ṹ
            	QMQuery materialPartStruQuery=new QMQuery("MaterialPartStructure");
                materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
                materialPartStruQuery.addAND();
                materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
                Collection mpsColl=pService.findValueInfo(materialPartStruQuery);
                Iterator mpsIte=mpsColl.iterator();
                while(mpsIte.hasNext())
                {
                	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)mpsIte.next();
//                	System.out.println("333333333333333333333333333333333������������ô��-------------------------------------------");
                	setDeleteMaterialPartStructure(mpsIfc);
                	pService.deleteValueInfo(mpsIfc);
                	//questions������ӷ���ɾ����Ϣ�ĳ���
                }
                
            }else if(xmlMatSplit.getMaterialSplit().getStatus()==2)
            {
//            	System.out.println("44444444444444444444kuku������������ô��-------------------------------------------");
            	//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA���������Ϊ�˴���ԭ�еı������ڽṹ
            	MaterialStructureIfc matStructIfc;
                //���Ͻṹֵ����XML��ʽ
                QMXMLMaterialStructure xmlMatStruct;
                //�¼�����ֵ����
                MaterialSplitIfc subMatSplitIfc;
                //�¼�����ֵ����XML��ʽ
                QMXMLMaterialSplit subXMlMatSplit;
                //�㲿��ֵ����
                QMPartIfc partIfc = null;
                String partIdentity = "";
            	List oldMaterialList = getMatStruct(xmlMatSplit.getPartNumber(), xmlMatSplit.getMaterialNumber());
//            	System.out.println("xmlMatSplit.getMaterialSplit().getStatus()============22222222222========"+oldMaterialList);
            	Iterator oldMaterialIte=oldMaterialList.iterator();
            	while(oldMaterialIte.hasNext())
            	{
            		matStructIfc = (MaterialStructureIfc)oldMaterialIte.next();
                        //�����¼����Ͻṹ�ķ������
                        xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
//                        System.out.println("matStructIfc.getParentPartNumber()========"+matStructIfc.getParentPartNumber());
//                        System.out.println("xmlMatSplit.getPartNumber()========"+xmlMatSplit.getPartNumber());
                        if(matStructIfc.getParentPartNumber().equals(
                                xmlMatSplit.getPartNumber()))
                        {
                            xmlMatStruct.setParentPartVersion(rootPartVersionID);
                            
                        }
                        //�����ϵķ������Ϊ"Z"ʱ�����Ͻṹ�ķ������Ϊ"U"questions
                        //CCBegin by dikefeng 20100422,�����־ɵ����÷�ʽȥ��
//                        if(publishType.equals("Z"))
//                        {
//                            xmlMatStruct.setStructurePublishType("U");
//                        }
//                        else
//                        {
//                            xmlMatStruct.setStructurePublishType(publishType);
//                        }
                        xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
                        //CCEnd by dikefeng 20100422
                        xmlMatStructList.add(xmlMatStruct);
                        subMatSplitIfc = getMatSplitIfc(matStructIfc
                                .getChildNumber());
//                        System.out.println("subMatSplitIfc.getPartNumber()========"+subMatSplitIfc.getPartNumber());
//                        System.out.println("rootPartNumber"+rootPartNumber);
                        if(subMatSplitIfc.getPartNumber().equals(rootPartNumber))
                        {
                            subMatSplitIfc.setPartVersion(rootPartVersionID);
                        }
                            partIfc = getPartIfc(subMatSplitIfc, partMap);
                            partIdentity = getPartIdentity(subMatSplitIfc);
                            partMap.put(partIdentity, partIfc);
                            subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);
                           // subXMlMatSplit.setPublishType(publishType);
                            //CCBegin by chudaming 20100331questions������
//                            System.out.println("fdsasdasdasdasdasdasdasd");
                            subXMlMatSplit.setPublishType(subMatSplitIfc.getMaterialSplitType());
                            //CCEnd by chudaming 20100331
                            subXMlMatSplit.setPartIfc(partIfc);
                            if(subXMlMatSplit.getPartNumber()
                                    .equals(rootPartNumber))
                            {
                                subXMlMatSplit.setPartVersionID(rootPartVersionID);
                            }
//                            subXMlMatSplit.setPartVersionID(partVersionID);
                            xmlMatSplitList.add(subXMlMatSplit);
//                            System.out.println("nizhenniu222222222222222222222222222222------------------------buxinzhaobudaoni--------------");
                            setSubMatStructPubType(rootPartNumber,
                                    rootPartVersionID, subXMlMatSplit, partMap);
//                            System.out.println("nizhenniu33333333333333333333333333333------------------------buxinzhaobudaoni--------------");
            	}
            	//BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB���������£������������Ͻṹ�ķ�����ʶ 
                //�õ����㲿������һ�������İ汾����Ϊ�ɰ淢�����������ϵ��㲿���Ľṹ�����˼�¼����������Ͳ��ٽ����ظ���ȡ�ɰ�����Ľṹ
//            	System.out.println("wwwwwwwwwwwwww"+xmlMatSplit.getPartNumber());
//            	System.out.println("wwwwwwwwwwwwww11111111111"+xmlMatSplit.getPartVersionID());
//            	
//            	System.out.println("wwwwwwwwwwwwww22222222222222222"+xmlMatSplit.getPartVersionID());
            	QMQuery materialPartStruQuery=new QMQuery("MaterialPartStructure");
                materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
                materialPartStruQuery.addAND();
                materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
                Collection oldMpsColl=pService.findValueInfo(materialPartStruQuery);
                Iterator oldMpsIte=oldMpsColl.iterator();
                HashMap oldMpsMap=new HashMap();
//                System.out.println("xmlMatSplit.getPartNumber())fffffffffffffffffffff"+xmlMatSplit.getPartNumber());
//                System.out.println("oldMpsIte1111fffffffffffffffffffff"+oldMpsIte);
//                System.out.println("xmlMatSplit.getMaterialNumber()1111fffffffffffffffffffff"+xmlMatSplit.getMaterialNumber());
                while(oldMpsIte.hasNext())
                {
                	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsIte.next();
                	oldMpsMap.put(mpsIfc.getChildNumber(),mpsIfc);
                }
//                System.out.println("oldMpsMapoldMpsMapoldMpsMap======="+oldMpsMap);
                //�����״̬�����Ӽ������õ�ǰ���Ӽ����ϴη������ݽ��бȽϣ��ֱ�����ɾ�����������޸ĺ����ñ��
                //�����״̬��û���Ӽ������ϴη����������Ӽ��ṹɾ����ʵ������Ϊ��ǰ������״̬Ϊ2�����Բ�����û���Ӽ���
//                System.out.println("newUsageLinkMapnewUsageLinkMapnewUsageLinkMap======="+newUsageLinkMap);
                if(newUsageLinkMap.size() > 0)
                {
                	//�����ݵĽṹ�������飬��ÿһ���µ����ݹ�������ѭ������ 
                    Iterator newUsageLinksIte = newUsageLinkMap.keySet().iterator();
                    while(newUsageLinksIte.hasNext())
                    {
                    	PartUsageLinkIfc newLink=(PartUsageLinkIfc)newUsageLinkMap.get(newUsageLinksIte.next());
                    	QMPartMasterIfc partMaster=(QMPartMasterIfc)pService.refreshInfo(newLink.getLeftBsoID());
                    	//�����ǰ�����ھɽṹ�в����ڣ���ǰ����Ϊ�¹���
                    	if(oldMpsMap.get(partMaster.getPartNumber())==null)
                    	{
                    		
                    		MaterialPartStructureInfo mpsInfo=new MaterialPartStructureInfo();
                            mpsInfo.setParentPartNumber(xmlMatSplit.getMaterialSplit().getPartNumber());
                            mpsInfo.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                            mpsInfo.setParentNumber(xmlMatSplit.getMaterialSplit().getMaterialNumber());
                            mpsInfo.setLevelNumber("0");
                            mpsInfo.setDefaultUnit("��");
                            //???
                            mpsInfo.setMaterialStructureType("O");
                            mpsInfo.setOptionFlag(false);
                            mpsInfo.setQuantity(newLink.getQuantity());
                            //CCBegin SS4
                            String partnumber = (String) partNumberMap.get(partMaster.getPartNumber());
                            if(partnumber==null||partnumber.length()==0){
                            	PartHelper helper = new PartHelper();
                            	partnumber=helper.getPartNumber(partMaster);
                            }
                            
                            mpsInfo.setChildNumber(partnumber);
                            //CCEnd SS4
//                            ����������������д洢����
                            pService.saveValueInfo(mpsInfo);
//                            System.out.println("sssssssssss==="+mpsInfo.getMaterialStructureType());
//                            ���������������������
                            setPartStructPubType(rootPartNumber, rootPartVersionID,
                                    "N", xmlMatSplit, newLink);
                            
                    	}else{
                    		
                    		//������ڶԵ�ǰ�Ӽ��Ĺ��������п�����o����u
                    		MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsMap.get(partMaster.getPartNumber());
//                    		���ʹ��������ͬ����Ϊo������ΪU
//                    		System.out.println("ooooooooooooo1111111==="+newLink.getQuantity());
//                    		System.out.println("ooooooooooooo222222222==="+mpsIfc.getQuantity());
                    		if(newLink.getQuantity()==mpsIfc.getQuantity())
                    		{
                    			mpsIfc.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                    			pService.saveValueInfo(mpsIfc);
                    			setPartStructPubType(rootPartNumber, rootPartVersionID,
                                        "O", xmlMatSplit, newLink);
                    			oldMpsMap.remove(partMaster.getPartNumber());
                    		}else{
                    			mpsIfc.setQuantity(newLink.getQuantity());
                    			mpsIfc.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                    			pService.saveValueInfo(mpsIfc);
                    			setPartStructPubType(rootPartNumber, rootPartVersionID,
                                        "U", xmlMatSplit, newLink);
                    			oldMpsMap.remove(partMaster.getPartNumber());
                    		}
                    	}
                    	
                    }
                    Iterator remainderIte=oldMpsMap.keySet().iterator();
                    //222222
//                    System.out.println("DDDDDDDDDDDDDDDDDD====="+oldMpsMap);
                	while(remainderIte.hasNext())
                	{
                		MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsMap.get((String)remainderIte.next());
//                		System.out.println("1111111111111111111111������1������������ô��-------------------------------------------");
                		setDeleteMaterialPartStructure(mpsIfc);
                    	pService.deleteValueInfo(mpsIfc);
                	}
                }else
                {
               //	System.out.println("oldMpsMapfffffffffffffffffffff"+oldMpsMap);
               // System.out.println("oldMpsIte2222fffffffffffffffffffff"+oldMpsIte.);
                	Iterator keyIte=oldMpsMap.keySet().iterator();
                	while(keyIte.hasNext())
                	{
                		String key=(String) keyIte.next();
                		//System.out.println("keyfffffffffffffffffffff"+key);
                		//�˴������쳣���Լ�����if(oldMpsIte.hasNext()){
                		if(oldMpsIte.hasNext()){
                			MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsIte.next();
//                    		System.out.println("222222222222222222222222������������ô��-------------------------------------------");
                    		setDeleteMaterialPartStructure(mpsIfc);
                        	pService.deleteValueInfo(mpsIfc);
                		}
                		
                	}
                }

            }
            //CCEnd by dikefeng 20100421
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByStructureRule(QMXMLMaterialSplit) - end");
        }
    }

    /**
     * �ϲ��ṹ��ʹ���Ӽ���������
     * @param usageLinkMap ���ϲ�������ʹ�ýṹMap��ֵΪPartUsageLinkIfc��
     * @return HashMap �ϲ����ʹ�ýṹMap����Ϊ�Ӽ�����ϢBsoID��ֵΪPartUsageLinkIfc��
     */
    private final HashMap uniteQuantity(HashMap usageLinkMap)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("uniteQuantity(HashMap) - start"); //$NON-NLS-1$
        }
        //�ϲ����ʹ�ýṹ�������ϡ�
        List usageLinkList = new ArrayList();
        Iterator iter = usageLinkMap.values().iterator();
        PartUsageLinkIfc usageLinkIfc = null;
        PartUsageLinkIfc usageLinkIfc2 = null;
        float quantity = 0;
        String leftBsoID = "";
        boolean flag = false;
        while (iter.hasNext())
        {
            usageLinkIfc = (PartUsageLinkIfc) iter.next();
            quantity = usageLinkIfc.getQuantity();
            leftBsoID = usageLinkIfc.getLeftBsoID();
            //��ʶ�ڼ���usageLinkList���Ƿ���ڵ�ǰ��ѭ��������㲿��,��ʼ�����,��Ϊ������:
            flag = false;
            //�����еĺϲ���ϵļ��Ͻ���ѭ��:
            for (int i = 0; i < usageLinkList.size(); i++)
            {
                usageLinkIfc2 = (PartUsageLinkIfc) usageLinkList.get(i);
                float oldQuantity = usageLinkIfc2.getQuantity();
                String oldLeftBsoID = usageLinkIfc2.getLeftBsoID();
                //���ʹ�õ���ͬһ���㲿���Ļ�,�ϲ�������
                if(leftBsoID.equals(oldLeftBsoID))
                {
                    usageLinkIfc2.setQuantity(quantity + oldQuantity);
                    //�ҵ�������㲿��:
                    flag = true;
                    usageLinkList.set(i, usageLinkIfc2);
                    break;
                }
            }
            if(!flag)
                usageLinkList.add(usageLinkIfc);
        }
        usageLinkMap = new HashMap();
        //���ϲ���Ľ�����ϼӵ�Map�У���Ϊ�Ӽ�����ϢBsoID��ֵΪPartUsageLinkIfc��
        for (int i = 0; i < usageLinkList.size(); i++)
        {
            usageLinkIfc = (PartUsageLinkIfc) usageLinkList.get(i);
            usageLinkMap.put(usageLinkIfc.getLeftBsoID(), usageLinkIfc);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("uniteQuantity(HashMap) - end"); //$NON-NLS-1$
        }
        return usageLinkMap;
    }

    /**
     * �����������Ϣ�ֱ�����Ӧ��QMXMLData�У������õ�dataList�С�
     * @param xmlMaterialSplitList ���˺�xmlPartList��
     * @param xmlStructureList ���˺�xmlStructureList��
     * @throws QMXMLException
     */
    private final void setDataRecord(final List xmlMaterialSplitList,
            final List xmlStructureList) throws Exception
    {
    	try{
        if(logger.isDebugEnabled())
        {
            logger.debug("setDataRecord(List, List) - start"); //$NON-NLS-1$
        }
        for (int j = 0; j < dataList.size(); j++)
        {
            final QMXMLData data = (QMXMLData) dataList.get(j);
            if(logger.isDebugEnabled())
            {
                logger.debug("data.getName==" + data.getName());
            }
            if(data.getName().equals("mpart"))
            {
                final List materialSplitRecordList = new ArrayList();
                for (int i = 0; i < xmlMaterialSplitList.size(); i++)
                {
                    final QMXMLMaterialSplit xmlMaterialSplit = (QMXMLMaterialSplit) xmlMaterialSplitList
                            .get(i);
                    xmlMaterialSplit.setPropertyList(data.getPropertyList());
                    materialSplitRecordList.add(xmlMaterialSplit.getRecord());
                }
                data.setRecordList(materialSplitRecordList);
                setDescNote(Messages.getString("Util.34", new Object[]{
                        Integer.toString(materialSplitRecordList.size()),
                        data.getName()}));
                continue;
            }
            else if(data.getName().equals("structure"))
            {
                final List structureRecordList = new ArrayList();
                for (int i = 0; i < xmlStructureList.size(); i++)
                {
                    final QMXMLMaterialStructure xmlStructure = (QMXMLMaterialStructure) xmlStructureList
                            .get(i);
                    xmlStructure.setPropertyList(data.getPropertyList());
                    structureRecordList.add(xmlStructure.getRecord());
                }
                data.setRecordList(structureRecordList);
                setDescNote(Messages.getString("Util.34", new Object[]{
                        Integer.toString(structureRecordList.size()),
                        data.getName()}));
                continue;
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("setDataRecord(List, List) - end"); //$NON-NLS-1$
        }
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    		
    	}
    }

    /**
     * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
     * �������HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
     * @param partIfc �㲿����
     * @throws QMXMLException
     */
    private final HashMap getUsageLinkMap(QMPartIfc partIfc)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(QMPartIfc) - start");
        }
        final HashMap usageLinkMap = new HashMap();
        PartHelper helper = new PartHelper();
        //ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
        List usesPartList = new ArrayList();
        try
        {
        	StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
        	usesPartList=(List)spservice.getUsesPartMasters(partIfc);
            //Ȼ��õ����㲿��������PartUsageLink
        }
        catch (QMException e)
        {
            //"��ȡ��Ϊ*���㲿���ṹʱ����"
            logger.error(Messages.getString("Util.17", new Object[]{partIfc
                    .getBsoID()})
                    + e);
            throw new QMXMLException(e);
        }
        //��Ҫ�������ӹ����ŵ�HashMap�С�
        Iterator iter = usesPartList.iterator();
       // System.out.println("usesPartList========"+usesPartList);
        while (iter.hasNext())
        {
            Object obj = iter.next();
           // System.out.println("obj========"+obj);
            PartUsageLinkIfc link =  (PartUsageLinkIfc)obj;
            String masterID = link.getLeftBsoID();
		
           
            try {
            	//CCBegin SS2
            	//���Ȼ������С�汾��Ȼ���������ڹ��� ������ 20140303 
            	// �������С�汾QMPartIfc
    			QMPartIfc qmpartifc = helper.getPartInfoByMasterBsoID(masterID);
    			//System.out.println("qmpartifc========1111111"+qmpartifc);
    			QMPartIfc part = helper.filterLifeState(qmpartifc);
    			 //System.out.println("part========1111111"+part);
    			 if(part==null)
    	        		continue;
    			//CCEnd SS2
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	//System.out.println("part22========2222222");
        	
            usageLinkMap.put(obj, obj);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(QMPartIfc) - end");
        }
        return usageLinkMap;
    }

    /**
     * ��ȡ���㲿����Ӧ���Ӽ��ṹ������
     * @param xmlMaterialSplit
     * @return
     * @throws QMXMLException
     */
    public QMPartIfc getOldPartIfc(String partNumber) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getOldUsageLinkMap(QMXMLMaterialSplit) - start"); //$NON-NLS-1$
        }
        VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
        QMPartIfc latestPartIfc = null;
        //����filterPart��¼���㲿�����ƺͰ汾�Ż�ȡ���°���
        final FilterPartIfc filterPartIfc = (FilterPartIfc) filterPartMap
                .get(partNumber);
        List partMasterList = new ArrayList();
        try
        {
        	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            final QMQuery query = new QMQuery("QMPartMaster");
            //CCBegin by dikefeng 20090619,�����޸ĵ���Ϊ�������·�����ʱ�����е��㲿������,Ϊ�˶���
            //��������Ĭ�ϱ�ʶΪ"U",�ڻ�ȡ�ɰ汾�Ľṹʱ�п���û��filterPartIfc
            final QueryCondition condition2 = new QueryCondition("partNumber",
                    QueryCondition.EQUAL, partNumber);
            //CCEnd by dikefeng 20090619
            query.addCondition(condition2);
            //��ʱ�����ǹ��岿���������
            query.setChildQuery(false);
            partMasterList=(List)pservice.findValueInfo(query);
        }
        catch (QueryException e)
        {
            //"�����ѯ����ʱ����"
            logger.error(Messages.getString("Util.19") + e);
            throw new QMXMLException(e);
        }
        catch (QMException e)
        {
            //"��ȡ���Ϊ*���㲿���Ļ�����Ϣʱ����"
            logger.error(Messages.getString("Util.20",
                    new Object[]{filterPartIfc.getPartNumber()})
                    + e);
            throw new QMXMLException(e);
        }
        if(partMasterList != null && partMasterList.size() > 0)
        {
            List versionList = new ArrayList();
            try
            {
            	versionList=(List)vcservice.allVersionsOf((QMPartMasterIfc)partMasterList.get(0));
            }
            catch (QMException e)
            {
                //"��ȡ���Ϊ*���㲿����С�汾����ʱ����"
                logger.error(Messages.getString("Util.21",
                        new Object[]{filterPartIfc.getPartNumber()})
                        + e);
                throw new QMXMLException(e);
            }
            if(logger.isDebugEnabled())
            {
                logger.debug("versionList==" + versionList);
            }
            for (int j = 0; j < versionList.size(); j++)
            {
                //��������ݰ汾��������ݵĴ�汾��һ�£�ֹͣ��questions
                //CCBegin by dikefeng 20090619,�ڽṹ�н�ɾ���Ľṹ��ʶ����
                if(filterPartIfc==null&&versionList.size()>0)
                {
                  latestPartIfc = (QMPartIfc) versionList.get(0);
                }
                else
                //CCEnd by dikefeng 20090619
                if(((QMPartIfc) versionList.get(j)).getVersionID().equals(
                        filterPartIfc.getVersionValue()))
                {
                    latestPartIfc = (QMPartIfc) versionList.get(j);
                    break;
                }
            }
            if(logger.isDebugEnabled())
            {
                logger.debug("latestPartIfc==" + latestPartIfc);
            }
            //���ص�ǰ�汾�����°������
            //CCBegin by dikefeng 20090619,����ɢ��������ֱ�ӶԽṹ�����޸�,�����޶������,
            //���Խ���ȡ����С�汾�Ĵ���ȥ��
//            if(latestPartIfc != null)
//            {
//                latestPartIfc = (QMPartIfc) getLatestIteration(latestPartIfc);
//            }
            //CCEnd by dikefeng 20060619
            if(latestPartIfc == null)
            {
                //"��ȡ���Ϊ*���㲿����һ�������汾ʱ����"
                throw new QMException(Messages.getString("Util.72",
                        new Object[]{partNumber}));
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getOldUsageLinkMap(QMXMLMaterialSplit) - end"); //$NON-NLS-1$
        }
        return latestPartIfc;
    }

    /**
     * ������°���ֵ����
     * @param bsoID
     * @return
     * @throws QMException
     */
    private BaseValueIfc getLatestIteration(IteratedIfc iteratedIfc)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getLatestIteration(IteratedIfc) - start"); //$NON-NLS-1$
        }
        BaseValueIfc baseIfc = null;
        VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
        // ˢ�����°���
        baseIfc=(BaseValueIfc)vcservice.getLatestIteration(iteratedIfc);
        if(logger.isDebugEnabled())
        {
            logger.debug("getLatestIteration(IteratedIfc) - end"); //$NON-NLS-1$
        }
        return baseIfc;
    }

    /**
     *  ��ȡ�㲿����ֵ����Ͻṹ���ϡ�
     * @param parentPartNumber�� �����š�
     * @param parentNumber�� �����Ϻš�
     * @return List ���㲿����ֵ����ϼ��ϡ�
     * @throws QMXMLException
     */
    private final List getMatStruct(String parentPartNumber, String parentNumber)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getMatStruct(String, String) - start"); //$NON-NLS-1$
        }
        List resultList = new ArrayList();
        try
        {
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
        	resultList=(List)msservice.getMStructure(parentPartNumber, parentNumber);
        }
        catch (QMException e)
        {
            //"���ұ��Ϊ*�������Ϻ�Ϊ*�����Ͻṹʱ����"
            logger.error(Messages.getString("Util.71", new Object[]{
                    parentPartNumber, parentNumber})
                    + e);
            throw new QMXMLException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getMatStruct(String, String) - end"); //$NON-NLS-1$
        }
        return resultList;
    }

    /**
     * �������ϺŻ�ȡ���ϡ�
     * @param materialNumber�����Ϻ�
     * @return
     * @throws QMException
     */
    private MaterialSplitIfc getMatSplitIfc(String materialNumber)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getMaterialSplitIfc(String) - start"); //$NON-NLS-1$
        }
        MaterialSplitIfc filterMaterialSplit = null;
        try
        {
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
            //��ѯ���Ӽ��Ķ������ϼ���
        	//System.out.println("materialNumber********************"+materialNumber);
        	filterMaterialSplit=(MaterialSplitIfc)msservice.getMSplit(materialNumber);
        	//System.out.println("filterMaterialSplit********************"+filterMaterialSplit);
        }
        catch (QMException e)
        {
            //"ˢ�����Ϻ�Ϊ*������ʱ����"
            logger.error(Messages.getString("Util.66",
                    new Object[]{materialNumber})
                    + e);
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getMaterialSplitIfc(String) - end"); //$NON-NLS-1$
        }
        return filterMaterialSplit;
    }

    /**
     * �����㲿���źͰ汾��ȡ�㲿����
     * @param partNumber���㲿���ı��
     * @param partVersionid���㲿���İ汾
     * @return
     * @throws QMException
     */
    private Collection getPartCol(String partNumber, String partVersionid)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getQMPart(String, String) - start"); //$NON-NLS-1$
        }
        Collection filterMaterialSplitCol = null;
        try
        {
        	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            QMQuery query = new QMQuery("QMPart", "QMPartMaster");
            query
                    .addCondition(0, 1, new QueryCondition("masterBsoID",
                            "bsoID"));
            query.addAND();
            query.addCondition(1, new QueryCondition("partNumber",
                    QueryCondition.EQUAL, partNumber));
            query.addAND();
            query.addCondition(0, new QueryCondition("versionID",
                    QueryCondition.EQUAL, partVersionid));
            query.setVisiableResult(1);
            filterMaterialSplitCol=(Collection)pservice.findValueInfo(query);
        }
        catch (QMException e)
        {
            //"���ұ��Ϊ*���汾Ϊ*���㲿��ʱ����"
            logger.error(Messages.getString("Util.65", new Object[]{partNumber,
                    partVersionid})
                    + e);
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getQMPart(String, String) - end"); //$NON-NLS-1$
        }
        return filterMaterialSplitCol;
    }

    /**
     * �����㲿���Ų�ѯ���˱�ļ�¼��
     * @param partNumber���㲿�����
     * @return
     * @throws QMException
     */
    private List getFilterPart(String partNumber) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getFilterPartByPartNumber(String) - start"); //$NON-NLS-1$
        }
        List result = null;
        //�ñ����Ϊ��ѯ�����������Ƿ��з���������FilterPart��
        PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        QMQuery query = new QMQuery("FilterPart");
        final QueryCondition condition = new QueryCondition("partNumber",
                QueryCondition.EQUAL, partNumber);
        query.addCondition(condition);
        result=(List)pservice.findValueInfo(query);
        if(logger.isDebugEnabled())
        {
            logger.debug("getFilterPartByPartNumber(String) - end"); //$NON-NLS-1$
        }
        return result;
    }

    /**
     * ��ø��Ĳ���֪ͨ��������㲿����
     * @param bsoID String�����Ĳ���֪ͨ���ID
     * @throws QMException
     * @return List:���Ĳ���֪ͨ��������㲿���ļ���
     * @throws QMException
     * @throws QMException
     */
    private List getPartsByAdoptNoticeId(String adoptNoticeBsoID)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartsByAdoptNoticeId(String) - start"); //$NON-NLS-1$
        }
        //���Ĳ���֪ͨ��������㲿���ļ���
        List partList = new ArrayList(0);
        try
        {
        	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            //1 ��ø��Ĳ���֪ͨ��������㲿��
            QMQuery query = new QMQuery("QMPart", "AdoptNoticePartLink");
            query.addCondition(0, 1, new QueryCondition("bsoID", "rightBsoID"));
            query.addAND();
            query.addCondition(1, new QueryCondition("leftBsoID",
                    QueryCondition.EQUAL, adoptNoticeBsoID));
            //�����ֻ�����㲿������Ϣ
            query.setVisiableResult(1);
            //��ʱ����ѯ���岿��
            query.setChildQuery(false);
            //���Ĳ���֪ͨ��������㲿������
            Collection partCol=(Collection)pservice.findValueInfo(query);
            //2 ���ݼ��Ϸ���partList
            if(partCol != null & partCol.size() > 0)
            {
                Iterator partIter = partCol.iterator();
                while (partIter.hasNext())
                {
                    partList.add((BaseValueIfc) partIter.next());
                }
            }
        }
        catch (QueryException e)
        {
            //"������Ĳ���֪ͨ��*�������㲿���Ĳ�ѯ����ʱ����"
            logger.error(Messages.getString("Util.69",
                    new Object[]{adoptNoticeBsoID})
                    + e);
            throw new QMException(e);
        }
        catch (QMException e)
        {
            //"��ѯ���Ĳ���֪ͨ��*�������㲿��ʱ����"
            logger.error(Messages.getString("Util.70",
                    new Object[]{adoptNoticeBsoID})
                    + e);
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartsByAdoptNoticeId(String) - end"); //$NON-NLS-1$
        }
        return partList;
    }

    /**
     * ��ȡԭ�����������Ｏ�����㲿�������°��򼯺ϡ�
     * ע�⣺��ʱ���������岿����
     * @param oldPartList��ԭ�����������Ｏ�ϡ�
     * @return List��ԭ���㲿�������°��򼯺ϡ�
     * @throws QMException
     */
    private List getLatestParts(List oldPartList) throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getLatestParts(List) - start"); //$NON-NLS-1$
        }
        //���°�����㲿������
        List newPartList = new ArrayList();
        Iterator oldPartIter = oldPartList.iterator();
        BaseValueIfc tempBaseIfc;
        while (oldPartIter.hasNext())
        {
            //1 ��������,ֻ��ȡpart,ȥ�����岿������������
            tempBaseIfc = (BaseValueIfc) oldPartIter.next();
            if((tempBaseIfc instanceof QMPartIfc)
                    && !(tempBaseIfc instanceof GenericPartIfc))
            {
                try
                {
                    //2 ˢ�����°���
                    tempBaseIfc = getLatestIteration((IteratedIfc) tempBaseIfc);
                    //3 ���ݼ��Ϸ���newPartList
                    newPartList.add((QMPartIfc) tempBaseIfc);
                }
                catch (QMException e)
                {
                    //"���ұ��Ϊ*���汾Ϊ*�������㲿������ʱ����"
                    logger.error(Messages.getString("Util.63", new Object[]{
                            ((QMPartIfc) tempBaseIfc).getPartNumber(),
                            ((QMPartIfc) tempBaseIfc).getVersionID()})
                            + e);
                    throw new QMXMLException(e);
                }
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getLatestParts(List) - end"); //$NON-NLS-1$
        }
        return newPartList;
    }

    /**
     *  ��ȡɸѡ��Ψһ��ʶ��
     * @param part �㲿����
     * @return String ɸѡ��Ψһ��ʶ��
     */
    private final String[] getPartIdentity(final QMPartIfc part)throws QMException
    {
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - start");
        PartHelper partHelper =  new PartHelper();
        MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
        Vector routevec = RequestHelper.getRouteBranchs(part, null);
        String routeAsString = "";
        String routeAllCode = "";
        String routeAssemStr = "";
        String lsbb = "";
        
        if(routevec.size() != 0)
        {
            routeAsString = (String)routevec.elementAt(0);
            routeAllCode = (String)routevec.elementAt(1);
            lsbb = (String)routevec.elementAt(6);
            
        }
       // if(part.getPartNumber().equals("1701110-A4K")){
            System.out.println("lsbb11111111111111========"+lsbb);
       
//		CCBegin SS3
        String[] aa = new String[2];
//		 ��ȡ��ǰ��������ϼ�
       
		String folder = part.getLocation();
		// ��������Դ�汾
		String jsbb = partHelper.getPartVersion(part);

       String partnumber = partHelper.getPartNumber(part,folder,lsbb,jsbb);
       if(partNumberMap.containsKey(part.getPartNumber())){
    	   partNumberMap.put(part.getPartNumber(), partnumber);
       }
       
       
        String returnString = partnumber + part.getLifeCycleState().getDisplay() + routeAllCode;
//		CCBEnd SS3
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - end");
        aa[0]=returnString;;
        aa[1]=partnumber;
        return aa;
    }

    /**
     *  ��ȡ����ֵ�������㲿����Ψһ��ʶ��
     * @param matSplitIfc ����ֵ����
     * @return String Ψһ��ʶ��
     */
    private final String getPartIdentity(final MaterialSplitIfc matSplitIfc)
    {
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - start");
        //CCBegin SS3
        //String returnString = matSplitIfc.getPartNumber() + matSplitIfc.getPartVersion() + matSplitIfc.getState() + matSplitIfc.getRoute();
        String returnString = matSplitIfc.getPartNumber()  + matSplitIfc.getState() + matSplitIfc.getRoute();
        //CCEnd SS3
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - end");
        return returnString;
    }
    /**
     * �����㲿��ʹ�ýṹ����ʱ�����Ͻṹ��Ϣ��
     * added by dikefeng 20100421,���������һ�����ϵ��㲿�������ķ���
     * @param rootPartNumber ������ϵ��㲿�����
     * @param rootPartVersionID ������ϵ��㲿���汾
     * @param strctPublishType ���Ͻṹ������ʶ
     * @param xmlMatSplit �����ϵ�XML��ʽ
     * @param partUsageLinkIfc �㲿��ʹ�ù�ϵ
     * @throws QMException
     */
    public void setDeleteMaterialPartStructure(MaterialPartStructureIfc mpsi)
            throws QMException
    {
    	
        //���Ͻṹֵ����
        MaterialStructureIfc matStructIfc = new MaterialStructureInfo();
        matStructIfc.setParentPartNumber(mpsi.getParentPartNumber());
        matStructIfc.setParentPartVersion(mpsi.getParentPartVersion());
        matStructIfc.setParentNumber(mpsi.getParentNumber());
        matStructIfc.setLevelNumber("0");
        matStructIfc.setDefaultUnit(mpsi.getDefaultUnit());
        matStructIfc.setQuantity(mpsi.getQuantity());
        matStructIfc.setOptionFlag(mpsi.getOptionFlag());
        QMXMLMaterialStructure xmlMatStruct = new QMXMLMaterialStructure();
        matStructIfc.setChildNumber(mpsi.getChildNumber());
        xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
//        System.out.println("��������ô");
        xmlMatStruct.setStructurePublishType("D");
//        System.out.println("�ǻ���˵ô");
        xmlMatStructList.add(xmlMatStruct);
//        System.out.println("����ɾ����������");
    }
    /**
     * �����㲿��ʹ�ýṹ����ʱ�����Ͻṹ��Ϣ��
     * @param rootPartNumber ������ϵ��㲿�����
     * @param rootPartVersionID ������ϵ��㲿���汾
     * @param strctPublishType ���Ͻṹ������ʶ
     * @param xmlMatSplit �����ϵ�XML��ʽ
     * @param partUsageLinkIfc �㲿��ʹ�ù�ϵ
     * @throws QMException
     */
    public void setPartStructPubType(String rootPartNumber,
            String rootPartVersionID, String strctPublishType,
            QMXMLMaterialSplit xmlMatSplit, PartUsageLinkIfc partUsageLinkIfc)
            throws QMException
    {
        //���Ͻṹֵ����
        MaterialStructureIfc matStructIfc = new MaterialStructureInfo();
        matStructIfc.setParentPartNumber(xmlMatSplit.getPartNumber());
        if(xmlMatSplit.getPartNumber().equals(rootPartNumber))
        {
            matStructIfc.setParentPartVersion(rootPartVersionID);
        }
        else
        {
            matStructIfc.setParentPartVersion(xmlMatSplit.getPartVersionID());
        }
        matStructIfc.setParentNumber(xmlMatSplit.getMaterialNumber());
        matStructIfc.setLevelNumber("0");
        matStructIfc.setDefaultUnit(partUsageLinkIfc.getDefaultUnit()
                .getDisplay());
        matStructIfc.setQuantity(partUsageLinkIfc.getQuantity());
        matStructIfc.setOptionFlag(partUsageLinkIfc.getOptionFlag());
        //��ѯ�����Ӽ��Ķ�������
        List rootMatNumberList = getRootMatSplit(partUsageLinkIfc
                .getLeftBsoID(), xmlMatSplit.getRouteCode());
        //�㲿���ṹ��XML��ʽ
        QMXMLMaterialStructure xmlMatStruct = new QMXMLMaterialStructure();
        String childNumber = "";
        for (int i = 0; i < rootMatNumberList.size(); i++)
        {
            childNumber = (String) rootMatNumberList.get(i);
            matStructIfc.setChildNumber(childNumber);
            xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
            xmlMatStruct.setStructurePublishType(strctPublishType);
            xmlMatStructList.add(xmlMatStruct);
        }
    }

    /**
     * ��ȡ�������
     * @param childBsoID���Ӽ���MasterID
     * @param parentRouteCode�������ϵ�·�ߴ���
     * @return
     * @throws QMException
     */
    public List getRootMatSplit(String childBsoID, String parentRouteCode)
            throws QMException
    {
        List rootMatNumberList = new ArrayList();
        if(!rootMatSplitMap.containsKey(childBsoID))
        {
            //��ѯ�Ӽ���Masterֵ����
            try
            {
            	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
                //1. ���Ȳ�ѯ���Ӽ����°汾
                QMPartMasterIfc childPartIfc = null;
                final QMQuery query = new QMQuery("QMPartMaster");
                final QueryCondition condition2 = new QueryCondition("bsoID",
                        QueryCondition.EQUAL, childBsoID);
                query.addCondition(condition2);
                //��ʱ�����ǹ��岿���������
                query.setChildQuery(false);
                List partMasterList = (List) pservice.findValueInfo(query);
                if(partMasterList != null && partMasterList.size() > 0)
                {
                    childPartIfc = (QMPartMasterIfc) partMasterList.get(0);
                }
                else
                {
                    //"��ȡBsoIDΪ*���㲿������Ϣʱ����"
                    logger.error(Messages.getString("Util.67",
                            new Object[]{childBsoID}));
                    throw new QMException(Messages.getString("Util.67",
                            new Object[]{childBsoID}));
                }
                //2 ��ѯ���Ӽ��Ķ������ϼ���
                //CCBegin SS4
                String partnumber = (String) partNumberMap.get(childPartIfc.getPartNumber());
                if(partnumber==null||partnumber.length()==0){
                	PartHelper helper = new PartHelper();
                	partnumber=helper.getPartNumber(childPartIfc);
                }
                
                Collection rootList = (Collection)msservice.getRootMSplit(partnumber);
//              CCEnd SS4
                Iterator iter = rootList.iterator();
                //�Ƿ���Ҫƥ��ı�־
                boolean isMatching = RemoteProperty.getProperty(
                        "com.faw_qm.erp.isMatching", "false").equalsIgnoreCase(
                        "true");
                MaterialSplitIfc matSplitIfc;
                while (iter.hasNext())
                {
                    matSplitIfc = (MaterialSplitIfc) iter.next();
                    //�����Ҫƥ�䣬��������¹ҽӵ��Ӽ���·�ߴ�������븸���ϵ�·�ߴ���һ�£�
                    //��������ϵ����ж������϶�����������������򲻹ҽӸ��Ӽ�
                    if(isMatching)
                    {
                        if(matSplitIfc.getRouteCode().equals(parentRouteCode))
                        {
                            rootMatNumberList.add(matSplitIfc
                                    .getMaterialNumber());
                            break;
                        }
                    }
                    else
                    {
                        rootMatNumberList.add(matSplitIfc.getMaterialNumber());
                    }
                }
                //3 ������Ҫƥ���������ϵĶ������ϣ�û�в���Ӽ���ʱ���������Ϻ�Ϊ�㲿����
                if(!isMatching && rootMatNumberList.size() <= 0)
                {
                    rootMatNumberList.add(childPartIfc.getPartNumber());
                }
                rootMatSplitMap.put(childBsoID, rootMatNumberList);
            }
            catch (QMException e)
            {
                throw new QMException(e);
            }
        }
        else
        {
            rootMatNumberList = (List) rootMatSplitMap.get(childBsoID);
        }
        return rootMatNumberList;
    }
    //CCbegin SSS
//  ���һ���Ӽ�
	public Collection getAllSubParts(QMPartIfc partIfc) throws QMException {
		Collection result = new Vector();
		//Collection filterIndex = new ArrayList();
		if (partIfc != null) {
			//����ǰ�����뵽�б�
			//result.add(partIfc);
			result = getSubParts(partIfc);
			//��һ���Ӽ����뵽�б�
			//result.add(col);
		}
		return result;
	}
	//���һ���Ӽ�
	 public Collection getSubParts(QMPartIfc partIfc) throws QMException {
			PartDebug.trace(this, PartDebug.PART_SERVICE, "getSubParts begin ....");
			// ��������������׳�PartEception�쳣"��������Ϊ��"
			if (partIfc == null)
				throw new PartException("����Ų�����", "CP00001", null);
			PersistService pservice = (PersistService) EJBServiceHelper
					.getPersistService();
			Collection collection = pservice.navigateValueInfo(partIfc, "usedBy",
					"PartUsageLink");
			Object[] tempArray = (Object[]) collection.toArray();
			VersionControlService vcservice = (VersionControlService) EJBServiceHelper
					.getService("VersionControlService");
			Vector result = new Vector();
			Vector tempResult = new Vector();
			for (int i = 0; i < tempArray.length; i++) {
				tempResult = new Vector(vcservice
						.allVersionsOf((QMPartMasterIfc) tempArray[i]));
				if (tempResult != null && (tempResult.iterator()).hasNext())
					result.addElement((tempResult.iterator()).next());
			}
			PartDebug.trace(this, PartDebug.PART_SERVICE,
					"getSubParts end....return is Collection ");
			return result;
		}
	 //
}
