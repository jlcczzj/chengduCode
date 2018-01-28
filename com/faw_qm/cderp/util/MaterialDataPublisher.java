/**
 * ���ɳ���MaterialDataPublisher.java	1.0              2007-9-28
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 partnumber�����汾 ������ 2014-09-12
 * SS2 ���ڽ�Ŵ��ڶ���汾������ֻ��ѯpartnumber
 * �Ҷ������ϲ���ȷ����Ҫ��materialNumber���Ҵ��汾�� ������ 2014-09-29
 * SS3 �ڹ���bom��������������汾������������汾���ȡ�����汾 ������ 2014-10-08
 * SS4 �ڹ���bom���ȡ�㲿���¼��ṹ��������bom��ʽ��ȡ 2014-11-22
 * SS5 ����bomʱ��xmlֻ���bom��Ϣ������·�߷�����ֻ���������Ϣ 2014-11-20
 * SS6 ������Ψһ���ж�ȥ��·�ߺ���������״̬ 2014-12-15
 * SS7 BOM�������Ʒ��ϵ 2014-12-20
 * SS8 ����������U����N����Ҫ�����жϽṹ�Ƿ���� 2014-12-20
 * SS9 ����·���б��У��б����˵������ݣ�Ҳ��ʾ�����ˡ� 2015-09-15
 * SS10 ͬһ���ṹ�¿����ж���Ӽ�����Ҫ�Ƚ�����+��� 2016-10-15
 * SS11 �����bom��Ҫ������һ�鳵�����ϡ���Ϊ���Ͳ���д·��
 * SS12 ��������滻ǰ�������滻ǰ��������鹦�� ������ 2017-3-3
 * SS13 �����ɾ����ǣ��򲻷������ϣ����Ҫ�����ϲ���ɾ����ֻ��ɾ���ṹ
 * SS14 �����жϹ���1����������ǳ���2��C��ͷ3������15λ  ������ 2017-12-23
 */
package com.faw_qm.cderp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.cderp.ejb.service.MaterialSplitService;
import com.faw_qm.cderp.ejb.service.PromulgateNotifyService;
import com.faw_qm.cderp.exception.QMXMLException;
import com.faw_qm.cderp.model.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.pcfg.family.model.GenericPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;

//
/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
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
    private List xmlMatSplitList1 = new ArrayList();

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
    	//System.out.println("publishSourseObject=============+++=="+ publishSourseObject);
    	
        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials() - start"); //$NON-NLS-1$
        }
        //ͨ������֪ͨ�����Ĳ���֪ͨ���ȡ�������㲿����
        List partList = new ArrayList();
        logger
                .debug("publishSourseObject==============="
                        + publishSourseObject);
        try
        {
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
        	PromulgateNotifyService pnservice=(PromulgateNotifyService)EJBServiceHelper.getService("CDPromulgateNotifyService");
        	//����bom������
          if(publishSourseObject instanceof GYBomAdoptNoticeIfc)
            {
                //���صĽ�����ǵ�һ��λ��ΪArraylist�����Ľ��,�ڶ���λ�ñ��ǰ�Ľ����
            	QMPartIfc qmpartifc;
            	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
				//collΪ���е��������
            	for(Iterator iter = coll.iterator(); iter.hasNext(); partList.add(qmpartifc))
				{
					 qmpartifc = (QMPartIfc)iter.next();
		
				}
				//Ϊ��ʹ���ڷ�����ʱ��֪�������·������϶�����Щ
              // System.out.println("filterParts===="+filterParts);
				if(this.filterParts!=null&&filterParts.size()>0)
				{
					System.out.println("���չ���bom�����ߵ�����ȷ�ķ�֧���Ҫ�·������㲿������Ϊ��"+filterParts.size());
					PersistService pService=(PersistService)EJBServiceHelper.getService("PersistService");
					for(int i=0;i<filterParts.size();i++)
					{
						partList.add((QMPartIfc)pService.refreshInfo((String)filterParts.get(i)));
					}
				}
				  System.out.println("partList111111111111111===="+partList);
                String nu=getXmlName();
               // System.out.println("nu===="+nu);
             
            		sourceType="";
            	
            }
            else if(publishSourseObject instanceof TechnicsRouteListIfc)
            {
	//CCBegin SS9
            	
            	partList = (List)getPartbyFilter(filterParts); 
            	//partList = (List)getPartByRouteListJF((TechnicsRouteListIfc)publishSourseObject);
            	// System.out.println("partList0000000000="+partList);
            	//CCEnd SS9

//                System.out.println("hainengzoume??????????????????????????????????????????????");
                String nu = getXmlName();
                if(nu.equalsIgnoreCase("caiyong"))
                {
                    sourceType = "����֪ͨ";
                }
                else if(nu.equalsIgnoreCase("biangeng"))
                {
                    sourceType = "���֪ͨ";
                }
                else
                {
                    sourceType = "";
                }
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
        sourceType = "";
        //"����*���㲿����Ϣ��"
        logger.fatal(sourceType
                + Messages.getString("Util.61", new Object[]{String
                        .valueOf(partList.size())}));
        if(logger.isDebugEnabled())
        {
            logger.debug("partList==" + partList);
        }
        //��� ������ 20140624ˢ�����°汾questions,����Ҫˢ���°汾�ɣ�
       // partList = getLatestParts(partList);
        //����ɸѡ������¼����������㲿��������ɸѡ���QMXMLMaterialSplit���ϴ���xmlMaterialSplitList�С�
        //�������ϴ�����������ϡ��������Ϊ�޸ĵ��㲿����Ӧ��filterPart�ŵ�filterPartMap�С�
        //���ݽṹ���������ṹ������ɸѡ���QMXMLStructure���ϴ���xmlStructureList�С�
        try
        {
        	//System.out.println("111111111111111==="+partList);
            filterMaterials(partList);
        }
        catch (Exception e)
        {
            //"�������ϵķ�������ʱ����"
            logger.error(Messages.getString("Util.68"), e);
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("���ϴ�������xmlPartList����" + xmlMatSplitList);
            logger.debug("���ϴ�������updatePartMap����" + filterPartMap.size());
            logger.debug("�ṹ���������˺�xmlStructureList����" + xmlMatStructList);
        }
        //�����������Ϣ�ֱ�����Ӧ��QMXMLData�У������õ�dataList�С�
        //CCBegin SS5
        if(publishSourseObject instanceof GYBomAdoptNoticeIfc){
        	//����ǹ���bom�򣬲���Ҫ��ʾ������Ϣ�����Դ˴���ʼ����������Ϣ��
        	//CCBegin SS11
        	xmlMatSplitList1=xmlMatSplitList;
        	xmlMatSplitList = new ArrayList();
        	PartHelper helper = new PartHelper();
        	if(xmlMatSplitList1.size()>0){
        		for(int i=0;i<xmlMatSplitList1.size();i++){
        			QMXMLMaterialSplit xmlMatSplit = (QMXMLMaterialSplit)xmlMatSplitList1.get(i);
//        			CCBegin SS14
        			QMPartIfc part = xmlMatSplit.getPartIfc();
        			String publish_type = xmlMatSplit.getPublishType();
        			String partnumber = xmlMatSplit.getPartNumber();
        			//�ж�����Ƿ��ǳ���
        			boolean ifauto = helper.checkAuto(part);
        			//����ǳ��ͣ����Ҳ��Ǽ�ʻ�ң��������Ͳ���U�ŷ���
//        			if(parttype.equals("����")&&(partnumber.indexOf("5000990")<0)&&!publish_type.equals("U")){
//        				xmlMatSplitList.add(xmlMatSplit);
//        			}
        			if(ifauto&&!publish_type.equals("U")){
        				xmlMatSplitList.add(xmlMatSplit);
        			}
        			//CCEnd SS14
        		}
        	}
        	
//        	CCEnd SS11
        	
        }
       // System.out.println("���ϴ�������xmlPartList����" + xmlMatSplitList);
      //  System.out.println("�ṹ���������˺�xmlStructureList����" + xmlMatStructList);
      
        setDataRecord(xmlMatSplitList, xmlMatStructList);
        //CCEnd SS5
        //�����˺��㲿������������ø�������������Ϊ����ɸѡ�����׼����
        BaseDataPublisher.xmlPartList = xmlMatSplitList;
        if(logger.isDebugEnabled())
        {
            logger.debug("filterMaterials() - end"); //$NON-NLS-1$
        }
    }

	  //CCBegin SS9
    /**
     * �Թ��˺��·�߹���������д���ת��Ϊֵ����
     *
     * @param Collection
     *            ���˺���������
     * @return Collection ��õ�ֵ���󼯺�
     */
    public Collection getPartbyFilter(Collection coll)
	throws QMException
	{
    	PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
    	Collection c = new Vector();
		for(Iterator iter = coll.iterator(); iter.hasNext();)
		{
			String partID = (String)iter.next();
			QMPartInfo partInfo = (QMPartInfo)ps.refreshInfo(partID);
			c.add(partInfo);
		}
		return c;
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
        System.out.println("partList.size()uuuuuuuuuu==========="+partList.size());
        for (int i = 0; i < partList.size(); i++)
        {
        	// System.out.println("partsMapuuuuuuuuuuu==========="+partsMap);
        	
            partIfc = (QMPartIfc) partList.get(i);
      
            //1 ɸѡ������¼�������1���ȸ���ɸѡ��Ψһ��ʶ�����㲿�����ϣ��ظ��ļ�¼��ɸѡ�������ٷ�����
            //�����ڸ��Ĳ���֪ͨ����Լ�¼ͬһ���汾�Ĳ�ͬ������������Ҫ�����ˣ�����ͬ�汾��ֻ��һ��
            //��ȡ�㲿����Ψһ��ʶ
            partIdentity = getPartIdentity(partIfc);
//            if(partIdentity.contains("1302130A01")){
//            	  System.out.println("djbom000000000==========="+djbom);
//                  System.out.println("partsMap000000000==========="+partsMap);
                // System.out.println("***partIdentitly000000000==========="+partIdentity);
//            }
           // System.out.println("partsMap000000000==========="+partsMap);
            if(!partsMap.containsKey(partIdentity))
            {
                    partsMap.put(partIdentity, partIfc);
                    //�����¼����Ϻ����Ͻṹ�ķ�������
                    setSubMaterial(partIfc, null, partsMap);

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
        PartHelper partHelper = new PartHelper();
        if(filterPartList != null && filterPartList.size() > 0)
        {
            for (int j = 0; j < filterPartList.size(); j++)
            {
                filterPartIfc = (FilterPartIfc) filterPartList.get(j);
                //CCBegin SS2
                int compare = partHelper.compareVersion(partIfc.getVersionID(), filterPartIfc.getVersionValue());
                if(compare == 2)
                {
                    tempPartList.add(partIfc);
                    break;
                } //CCEnd SS2
                else
                {
                    if(tempFilterPart == null)
                    {
                        tempFilterPart = filterPartIfc;
                    }//CCBegin SS2
                    else if(partHelper.compareVersion(tempFilterPart.getVersionValue(),filterPartIfc.getVersionValue()) ==2)
                    { //CCEnd SS2
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
                    MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
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
            HashMap partMap) throws QMException
    {
    	PartHelper parthelper =  new PartHelper();
    	String partVersion = "";
		//CCBegin SS2
		 //�˴�partnumber����ȷ�Ϳ��ܵ������ɲ���xml�ļ�
		 //String partnumber =parthelper.getMaterialNumber(partIfc,partVersion);
		 String partnumber ="";
		 //���ݴ������ĵ���bom���ݻ�ȡ���ϱ��

			  partVersion = parthelper.getPartVersion(partIfc);
			  partnumber =parthelper.getMaterialNumber(partIfc,partVersion);
		 

    	
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setSubMaterial(String, String, String , HashMap) - start");
        }

        MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
        //����ֵ����
        MaterialSplitIfc matSplitIfc;
        //����ֵ�����XML��ʽ
        QMXMLMaterialSplit xmlMatSplit;
        //�㲿����ֵ����ϵ��������
        System.out.println("partnumber111111111==========="+partnumber);
        System.out.println("partVersion111111111==========="+partVersion);
        Collection rootList=(Collection)msservice.getRootMSplit(partnumber,partVersion);

        Iterator rootMatSplitIter = rootList.iterator();
        System.out.println("rootList111111111==========="+rootList);
        HashMap notPublishMat=new HashMap();
        //���ϸ��㲿����ֵ�����
        while (rootMatSplitIter.hasNext())
        {
            matSplitIfc = (MaterialSplitIfc) rootMatSplitIter.next();
            //�˴����Ƿ���true
            boolean flag=true;
            logger.debug("flag is "+flag);
            logger.debug("matSplitIfc.getPartNumber() is "+matSplitIfc.getPartNumber());
            logger.debug("partIfc.getPartNumber() is "+partIfc.getPartNumber());
            //��ȡδ�����Ķ���������ϣ�����Ӧ����һ����¼
            if(notPublishMat.containsKey(matSplitIfc.getPartNumber())||
            		!flag){
            	notPublishMat.put(matSplitIfc.getPartNumber(), matSplitIfc.getPartNumber());
            	continue;
            }
             matSplitIfc.setPartVersion(partVersion);
             System.out.println("matSplitIfc1111111111==========="+matSplitIfc.getMaterialNumber());
             System.out.println("hasSetSplitPubTypeMap1111111111==========="+hasSetSplitPubTypeMap);
//           ��ȡδ�����Ķ������ϼ��ϣ�����Ӧ����һ����¼
            if(!hasSetSplitPubTypeMap.containsKey(matSplitIfc
                    .getMaterialNumber()))
            {
                xmlMatSplit = new QMXMLMaterialSplit(matSplitIfc);
                xmlMatSplit.setPartIfc(partIfc);
               // System.out.println("xmlMatSplitxmlMatSplit00000000000000000=============="+matSplitIfc.getBsoID());
//                System.out.println("xmlMatSplitxmlMatSplit0000000000000000022222222222222222222222=============="+matSplitIfc.getPartVersion());
                System.out.println("xmlMatSplitxmlMatSplit000000���ý�����=============="+matSplitIfc.getMaterialSplitType());
                xmlMatSplit.setPublishType(matSplitIfc.getMaterialSplitType());
                xmlMatSplit.setPartVersionID(matSplitIfc.getPartVersion());

                //�����ɾ����ǣ��ⲻ��������
               // if(!matSplitIfc.getMaterialSplitType().equals("D")){
                	xmlMatSplitList.add(xmlMatSplit);
             //   }

                hasSetSplitPubTypeMap.put(matSplitIfc.getMaterialNumber(),
                        xmlMatSplit);
           
              //  System.out.println("xmlMatSplitList==========="+xmlMatSplitList);
//                System.out.println("nizhenniu555555555555555555555555555555------------------------buxinzhaobudaoni--------------");
               //��������� 20140624 ԭ����partIfc.getversionid();�޸�ΪmatSplitIfc.getPartVersion()
              //  System.out.println("xmlMatSplit==========="+xmlMatSplit);
                //��ö�����split �����¼�
                setSubMatStructPubType(partnumber, matSplitIfc
                        .getPartVersion(), xmlMatSplit, partMap);
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
     //   0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ�
       System.out.println("xmlMatSplit.getMaterialSplit().getStatus() =="+xmlMatSplit.getMaterialSplit().getStatus());
        if(xmlMatSplit.getMaterialSplit().getStatus() == 1)//
        {
        	//CCBegin SS7
        	//BOM�������Ʒ��ϵ
        	//if(djbom==null){
//        		CCEnd SS7
        		 //�������
                String publishType = xmlMatSplit.getPublishType();
                //�õ������ϵ��¼����ϣ�����ط�ֻ�ǻ���˶������ϵ���һ������
                System.out.println("xmlMatSplit.getPartNumber()==="+xmlMatSplit.getPartNumber());
                System.out.println(" xmlMatSplit.getMaterialNumber()==="+ xmlMatSplit
                        .getMaterialNumber());
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
                System.out.println("list==="+list);
                System.out.println("list==="+list.size());
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
                        
                        xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
                        System.out.println("tupe====="+xmlMatStruct.getStructurePublishType());
                        System.out.println("material==="+matStructIfc.getBeforeMaterial());
                        hasSetStrutPublishType.put(matStructIfc.getBsoID(),
                                xmlMatStruct);
                        System.out.println("xmlMatStruct=====��������structure"+xmlMatStruct.getDisplayname());
                        //���Ҫ�ڷ�������bomʱ����ʾСbom���Խ�����ȥ��
                        if(djbom==null){
                           xmlMatStructList.add(xmlMatStruct);
                           System.out.println("tupe=====��������structure");
                        }
                        subMatSplitIfc = getMatSplitIfc(matStructIfc
                                .getChildNumber());
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
                            subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);

                            subXMlMatSplit.setPublishType(subMatSplitIfc.getMaterialSplitType());
                            subXMlMatSplit.setPartIfc(partIfc);
                            if(subXMlMatSplit.getPartNumber()
                                    .equals(rootPartNumber))
                            {
                                subXMlMatSplit.setPartVersionID(rootPartVersionID);
                            }
//  
                            hasSetSplitPubTypeMap.put(subMatSplitIfc
                                    .getMaterialNumber(), subXMlMatSplit);
                            //CCbegin SS13
                            System.out.println("subMatSplitIfc.getMaterialSplitType()2222222222=="+subMatSplitIfc.getMaterialSplitType());
                            if(!subMatSplitIfc.getMaterialSplitType().equals("D")){
                            	xmlMatSplitList.add(subXMlMatSplit);
                            }//CCEnd SS13
                            setSubMatStructPubType(rootPartNumber,
                                    rootPartVersionID, subXMlMatSplit, partMap);
                        }
                    }
                }
                //CCBegin by dikefeng 20100421����ǰ����״̬���Ϊ1ʱ��˵�������������ڱ��������һ�����ϣ���ôֻҪ�����������¼�����Ĺ�ϵ
                //��ɾ��,���״̬���Ϊ2��0��˵�������ڱ�������¼����ϡ�Ϊ2ʱ��Ӧ�ý���ǰ�Ӽ��ṹ��ɽṹ���бȽϣ�������û�еĽṹ���ΪD
                //�����е�����û�б仯�����Ϊo�������е��������仯�˵ı��ΪU��Ϊ0ʱ��˵��û���¼�����������֮ǰ�ķ��������¼�·��
                //ֱ�ӽ����нṹ���ΪD
                //·�߱��ˣ�������Ҫ����ʷ�ṹ����ɾ��
                QMQuery materialPartStruQuery=new QMQuery("CDMaterialPartStructure");
                materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
                materialPartStruQuery.addAND();
                materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
                PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
                Collection mpsColl=pService.findValueInfo(materialPartStruQuery);
                Iterator mpsIte=mpsColl.iterator();
                System.out.println("xmlMatSplit.getPartNumber()=="+xmlMatSplit.getPartNumber());
                System.out.println("xmlMatSplit.getMaterialNumber()=="+xmlMatSplit.getMaterialNumber());
                System.out.println("mpsColl=="+mpsColl);
                while(mpsIte.hasNext())
                {
                	MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)mpsIte.next();
//                	System.out.println("kuku������������ô��-------------------------------------------");
                	setDeleteMaterialPartStructure(mpsIfc);
                	pService.deleteValueInfo(mpsIfc);
                }
                
        	//}
             
        }
        //�����㲿��ʹ�ýṹ�ķ������
        //�����Ӽ��ṹʱ����Ҫ������ɾ���Ľṹ��Ϣ
        else if(xmlMatSplit.getMaterialSplit().getStatus() == 2||xmlMatSplit.getMaterialSplit().getStatus() == 0)
     
        {//����������ϣ��Ͳ���Ҫ��ȥ�ж�bom�ˡ��˴���ʱȥ����֪���Բ���
        	//System.out.println("djbom0000000000000==="+djbom.size());
        	//if(djbom!=null){ 
        		filterByStructureRule(rootPartNumber, rootPartVersionID,
                        xmlMatSplit,partMap);		
        	//}
            
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
        //�������˴�partMap��û�оͻ��������
//    	System.out.println("partIdentity33333333333="+partIdentity); 
//    	System.out.println("partMap33333333333="+partMap); 
        if(partMap.containsKey(partIdentity))
        {
            partIfc = (QMPartIfc) partMap.get(partIdentity);

        }
        //����㲿������parts�У�������ݿ���ˢ��
        else
        {
//        	System.out.println("matSplitIfc.getPartNumber()="+matSplitIfc.getPartNumber()); 
//        	System.out.println("matSplitIfc.getPartVersion()="+matSplitIfc.getPartVersion()); 
        	String[] a = matSplitIfc.getPartNumber().split("/");
        	String partnumber= a[0];
        	System.out.println("partnumber="+partnumber); 
            Collection partCol = getPartCol(partnumber,
                    matSplitIfc.getPartVersion());
            Iterator partIter = partCol.iterator();
            if(partIter.hasNext())
            {
                try
                {
                    partIfc = (QMPartIfc) getLatestIteration((QMPartIfc) partIter
                            .next());
                    System.out.println("partIfc="+partIfc); 
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
        
        PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
      
      //  System.out.println("getMaterialNumber====="+xmlMatSplit.getMaterialNumber());
//        System.out.println("publishType="+publishType);
        //�µ�ʹ�ù�ϵ��ֵ����
        PartUsageLinkIfc newUsageLinkIfc;
        //ԭ��ʹ�ù�ϵ��ֵ����
        PartUsageLinkIfc oldUsageLinkIfc;
        //��ʱ��ʹ�ù�ϵ��ֵ����
        PartUsageLinkIfc tempUsageLinkIfc;
        //�������㲿����ʹ�ýṹ
        //CCBegin SS1
        
        Vector newUsageLinkMap = new Vector();
        QMPartIfc ppart = xmlMatSplit.getPartIfc();
        System.out.println("xmlMatSplit.getPartIfc()="+xmlMatSplit.getPartIfc().getPartNumber());
        if(djbom!=null&&djbom.size()>0){
        	newUsageLinkMap =getUsageLinkMap(xmlMatSplit.getPartIfc());
        	//System.out.println("newUsageLinkMap="+newUsageLinkMap.size());
        }
      // System.out.println("xmlMatSplit.getPartIfc()="+xmlMatSplit.getPartIfc().getPartNumber());
//        System.out.println("newUsageLinkMap="+newUsageLinkMap.size());
//    	   System.out.println("xmlMatSplit.getPartIfc()11111111111="+xmlMatSplit.getPartIfc().getPartNumber());
//          
          
        //���û���¼�������ѯ��������20141222
//          if(newUsageLinkMap==null||newUsageLinkMap.size()==0)
//        	  return;
         
        
  
         if(publishType.equals("D"))
        {
        	//��ǰ����״̬���Ϊ1ʱ��˵�������������ڱ��������һ�����ϣ���ôֻҪ�����������¼�����Ĺ�ϵ
            //��ɾ��,���״̬���Ϊ2��0��˵�������ڱ�������¼����ϡ�Ϊ2ʱ��Ӧ�ý���ǰ�Ӽ��ṹ��ɽṹ���бȽϣ�������û�еĽṹ���ΪD
            //�����е�����û�б仯�����Ϊo�������е��������仯�˵ı��ΪU��Ϊ0ʱ��˵��û���¼�����������֮ǰ�ķ��������¼�·��
            //ֱ�ӽ����нṹ���ΪD
        	if(xmlMatSplit.getMaterialSplit().getStatus()==2) 
        	{
            QMQuery materialPartStruQuery=new QMQuery("CDMaterialPartStructure");
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
            
        }
        //�ṹ�������2������Ϊ�汾�仯ʱ�������ݵ��Ӽ�����и����ݵİ汾���Ӽ����бȽϡ�
        else if(publishType.equals("U")||publishType.equals("N"))
       
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("---------�ṹ�������2");
            }
        
            	//System.out.println("44444444444444444444kuku������������ô��----------------------2222222222");
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
                //��ø����������Ӽ�
            	List oldMaterialList = getMatStruct(xmlMatSplit.getPartNumber(), xmlMatSplit.getMaterialNumber());
            	//System.out.println("oldMaterialList============22222222222========"+oldMaterialList);
            	Iterator oldMaterialIte=oldMaterialList.iterator();
            	//���ҵ�ǰ�����Ƿ����¼����Ʒ����
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
  
                        xmlMatStruct.setStructurePublishType(matStructIfc.getMaterialStructureType());
                        xmlMatStructList.add(xmlMatStruct);
                        subMatSplitIfc = getMatSplitIfc(matStructIfc
                                .getChildNumber());
                        //���������Ϊ�գ��򲻷���
                        if(subMatSplitIfc==null)
                        	continue;
//                        System.out.println("matStructIfc.getChildNumber()========"+matStructIfc.getChildNumber());
//                        System.out.println("subMatSplitIfc========"+subMatSplitIfc);
//                        System.out.println("subMatSplitIfc.getPartNumber()========"+subMatSplitIfc.getPartNumber());
                        //System.out.println("rootPartNumber"+rootPartNumber);
                        //������¼����Ʒ����������Ӽ�
                        if(subMatSplitIfc.getPartNumber().equals(rootPartNumber))
                        {
                            subMatSplitIfc.setPartVersion(rootPartVersionID);
                        }
                            partIfc = getPartIfc(subMatSplitIfc, partMap);
                            partIdentity = getPartIdentity(subMatSplitIfc);
                            partMap.put(partIdentity, partIfc);
                            
                           // System.out.println("33333333subMatSplitIfc.getMaterialSplitType()"+subMatSplitIfc.getMaterialSplitType());
                            subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);
                            subXMlMatSplit.setPublishType(subMatSplitIfc.getMaterialSplitType());
                          //  System.out.println("11111111111111111111111psubXMlMatSplit.getPartNumbery"+subXMlMatSplit.getPartNumber());
                            //CCEnd by chudaming 20100331
                            subXMlMatSplit.setPartIfc(partIfc);
                            if(subXMlMatSplit.getPartNumber()
                                    .equals(rootPartNumber))
                            {
                                subXMlMatSplit.setPartVersionID(rootPartVersionID);
                            }
//                            subXMlMatSplit.setPartVersionID(partVersionID);
                            //CCbegin SS13
                            if(!subMatSplitIfc.getMaterialSplitType().equals("D")){
                            	 xmlMatSplitList.add(subXMlMatSplit);
                            }
                            //CCEnd SS13
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
//            	System.out.println("wwwwwwwwwwwwww22222222222222222"+xmlMatSplit.getPartNumber());
//            	System.out.println("wwwwwwwwwwwwww22222222222222222"+xmlMatSplit.getMaterialNumber());
            	//��ȡ��һ���汾�ľ�part�ṹ
            	if(djbom==null){
            		return;
            	}
            	QMQuery materialPartStruQuery=new QMQuery("CDMaterialPartStructure");
                materialPartStruQuery.addCondition(new QueryCondition("parentPartNumber","=",xmlMatSplit.getPartNumber()));
                materialPartStruQuery.addAND();
                materialPartStruQuery.addCondition(new QueryCondition("parentNumber","=",xmlMatSplit.getMaterialNumber()));
                Collection oldMpsColl=pService.findValueInfo(materialPartStruQuery);
               
            
                //�¼���ֻ��������ɾ�����������û��U״̬��
                Vector oldVector = (Vector) oldMpsColl;
                if(newUsageLinkMap.size() > 0)
                {
                	//�����ݵĽṹ�������飬��ÿһ���µ����ݹ�������ѭ������ 

                    for(int i=0;i<newUsageLinkMap.size();i++)
                    {
                    	//PartUsageLinkIfc newLink=(PartUsageLinkIfc)newUsageLinkMap.get(newUsageLinksIte.next());
                    	String[] aa = (String[]) newUsageLinkMap.elementAt(i);
//                    	��ֵ���Ӽ���+����
                    	String partNumber = (String) aa[0]+"-"+aa[3];
                    	
            
                    	//QMPartMasterIfc partMaster=(QMPartMasterIfc)pService.refreshInfo(newLink.getLeftBsoID());
                    	//�����ǰ�����ھɽṹ�в����ڣ���ǰ����Ϊ�¹���
                    //	System.out.println("oldMpsMap.get(aa[0]======="+oldMpsMap.get(aa[0]));
                    	//System.out.println("partNumber+ls======="+partNumber);
                    	//���½ṹ��ϵͳ�ɽṹ���бȽ�
                    	MaterialPartStructureIfc mpsIfc= null;
                    	int ibs = 0;
                    	//System.out.println("oldMpsColl======="+oldMpsColl);
                    	if(oldMpsColl!=null){
                    		for(int j=0;j<oldMpsColl.size();j++){
                    			mpsIfc = (MaterialPartStructureIfc) oldVector.elementAt(j);
                    			String quanti = String.valueOf(mpsIfc.getQuantity());
                           	 if(quanti.endsWith(".0"))
                                {
                           		 quanti = quanti.substring(0, quanti.length() - 2);
                                }
                           	String childNumber = mpsIfc.getChildNumber();
                           	String oldPartNumber = childNumber+"-"+quanti;
                           	//System.out.println("oldPartNumber======="+oldPartNumber);
                    			if(partNumber.equals(oldPartNumber)){
                    				ibs=1;
                    				break;
                    			}
                        	}
                    	}
                    //	System.out.println("ibs======="+ibs);
                    	
//                    	�����Ͻṹ���Ƿ���������Ͻṹ,���û����Ϊ�����ṹ
                    	if(ibs==0)
                    	{//���û�п϶��������ṹ
                    	
//                    		System.out.println("xmlMatSplit.getMaterialSplit().getPartNumber()======="+xmlMatSplit.getMaterialSplit().getPartNumber());
                    	//	System.out.println("aa[0]======="+aa[0]);
                    		MaterialPartStructureInfo mpsInfo=new MaterialPartStructureInfo();
                            mpsInfo.setParentPartNumber(xmlMatSplit.getMaterialSplit().getPartNumber());
                            mpsInfo.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                            mpsInfo.setParentNumber(xmlMatSplit.getMaterialSplit().getMaterialNumber());
                            mpsInfo.setLevelNumber("0");
                            mpsInfo.setDefaultUnit("��");
                            //???
                            mpsInfo.setMaterialStructureType("O");
                            mpsInfo.setOptionFlag(false);
                            mpsInfo.setQuantity(Float.parseFloat(aa[3]));
                            mpsInfo.setChildNumber(aa[0]);
//                            ����������������д洢����
                            pService.saveValueInfo(mpsInfo);
//                            System.out.println("sssssssssss==="+mpsInfo.getMaterialStructureType());
//                            ���������������������
                            setPartStructPubType(rootPartNumber, rootPartVersionID,
                                    "N", xmlMatSplit, aa);
                            
                    	}else{//��������ھɼ�����ȥ����Ϊ��������Ϣ

                    			mpsIfc.setParentPartVersion(xmlMatSplit.getMaterialSplit().getPartVersion());
                    			pService.saveValueInfo(mpsIfc);
                    			setPartStructPubType(rootPartNumber, rootPartVersionID,
                                        "O", xmlMatSplit, aa);
//                    			
                    			oldVector.remove(mpsIfc);
                    	}
                    	
                    	
                    }
               //���ṹѭ���Ա�֮�󣬾ɽṹ���½ṹ���ɾ��
                	for(int i=0;i<oldVector.size();i++){
                		MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldVector.elementAt(i);
                		
                		setDeleteMaterialPartStructure(mpsIfc);
                    	pService.deleteValueInfo(mpsIfc);
                	}
                }else
                {//���û���¼��ṹ����ɾ�����нṹ����
                //	System.out.println("fffffffffffffffffffff"+oldMpsColl);
                	Iterator oldMpsIteDel=oldMpsColl.iterator();
                	while(oldMpsIteDel.hasNext())
                	{
                	
                    	
                    	    
                    		MaterialPartStructureIfc mpsIfc=(MaterialPartStructureIfc)oldMpsIteDel.next();
                    	//	System.out.println("mpsIfc===="+mpsIfc);
                
                    		setDeleteMaterialPartStructure(mpsIfc);
                        	pService.deleteValueInfo(mpsIfc);
                		//}
                		
                	}
                }
         	
            //}
               
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
//  CCBegin SS4
//    /**
//     * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
//     * �������HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
//     * @param partIfc �㲿����
//     * @throws QMXMLException
//     */
//    private final HashMap getUsageLinkMap(QMPartIfc partIfc)
//            throws QMXMLException
//    {
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("getUsageLinkMap(QMPartIfc) - start");
//        }
//        final HashMap usageLinkMap = new HashMap();
//        //ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
//        List usesPartList = new ArrayList();
//        try
//        {
//        	
//      
//        	StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
//        	usesPartList=(List)spservice.getUsesPartMasters(partIfc);
//        
//            //Ȼ��õ����㲿��������PartUsageLink
//        }
//        catch (QMException e)
//        {
//            //"��ȡ��Ϊ*���㲿���ṹʱ����"
//            logger.error(Messages.getString("Util.17", new Object[]{partIfc
//                    .getBsoID()})
//                    + e);
//            throw new QMXMLException(e);
//        }
//        //��Ҫ�������ӹ����ŵ�HashMap�С�
//        Iterator iter = usesPartList.iterator();
//        while (iter.hasNext())
//        {
//            Object obj = iter.next();
//            usageLinkMap.put(obj, obj);
//        }
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("getUsageLinkMap(QMPartIfc) - end");
//        }
//        return usageLinkMap;
//    }
    /**
     * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
     * �������HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
     * @param partIfc �㲿����
     * @throws QMXMLException
     */
    private final Vector getUsageLinkMap(QMPartIfc partIfc)
            throws QMXMLException
    {
    	//Collection vec = null;
    	
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(QMPartIfc) - start");
        }
        final Vector usageLinkMap = new Vector();
        //ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
       // List usesPartList = new ArrayList();

        //��Ҫ�������ӹ����ŵ�HashMap�С�
       // PartUsageLinkIfc
       // System.out.println("partIfc.getPartNumber()00000="+partIfc.getPartNumber());
        Iterator iter = djbom.iterator();
        PartHelper helper = new PartHelper();
        while (iter.hasNext())
        {
            Object[] obj = new Object[5];
	    	obj = (Object[])iter.next();
	    	String[] aa = new String[5];
	    	String[] bb = new String[5];
            aa[0]=(String) obj[1]; //��Ŵ��汾
            aa[1]=""; //����
            QMPartIfc part=(QMPartIfc)obj[0]; 
	    	aa[2]=helper.getPartVersion(part);
	    	aa[3]=(String)obj[2]; // ����
//	    	bb=(String[])usageLinkMap.get(aa[0]);
//	    	if(bb!=null){
//	    		aa[3] = String.valueOf(Integer.parseInt(aa[3])+Integer.parseInt(bb[3]));
//	    	}
           
            aa[4]=(String)obj[3]; // �������
//            System.out.println("partIfc.getPartNumber()11111111111="+partIfc.getPartNumber());
//            System.out.println("aa[0]11111111111="+aa[0]);
//            System.out.println("aa[3]11111111111="+aa[3]);
//            System.out.println("aa[4]11111111111="+aa[4]);
 
            if(aa[4].equals(partIfc.getPartNumber())){
            	//��ֵΪ���+����
            	 usageLinkMap.add(aa);
            }
           
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(QMPartIfc) - end");
        }
        return usageLinkMap;
    }
	//CCEnd SS4
  //CCBegin SS1
    /**
     * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
     * �������HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
     * @param partIfc �㲿����
     * @throws QMXMLException
     */
//    private final HashMap getUsageLinkMapNew(QMPartIfc partIfc)
//            throws QMXMLException
//    {
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("getUsageLinkMap(QMPartIfc) - start");
//        }
//        System.out.println("getUsageLinkMapNew=start");
//        PartHelper helper = new PartHelper();
//        final HashMap usageLinkMap = new HashMap();
//        //ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
//        List usesPartList = new ArrayList();
//        Collection collection = null;
//        try
//        {
////        	StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
////        	usesPartList=(List)spservice.getUsesPartMasters(partIfc);
//        	 collection = helper.getUsesPartIfcs(partIfc);
//            //Ȼ��õ����㲿��������PartUsageLink
//        
//	      
//	        //��Ҫ�������ӹ����ŵ�HashMap�С�
//	        Iterator iter = collection.iterator();
//	        System.out.println("collection="+collection);
//	        while (iter.hasNext())
//	        {
//	        	//obj = new Object[3];
//	        	Object[] objnew = (Object[]) iter.next();
//	        	
//	        	QMPartIfc part1 = (QMPartIfc) objnew[1];
//	        	Object obj = objnew[0];
//	        	//QMPartIfc part2 = helper.filterLifeState(part1);
//	        	System.out.println("part2="+part2);
//	        	if(part2==null)
//	        		continue;
//	            usageLinkMap.put(obj, obj);
//	        }
//        } catch (QMException e)
//        {
//            //"��ȡ��Ϊ*���㲿���ṹʱ����"
//            logger.error(Messages.getString("Util.17", new Object[]{partIfc
//                    .getBsoID()})
//                    + e);
//            throw new QMXMLException(e);
//        }
//        if(logger.isDebugEnabled())
//        {
//            logger.debug("getUsageLinkMap(QMPartIfc) - end");
//        }
//        System.out.println("getUsageLinkMapNew=end");
//        return usageLinkMap;
//    }
  //CCEnd SS1
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
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
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
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
            //��ѯ���Ӽ��Ķ������ϼ���
        	System.out.println("materialNumber=="+materialNumber);
        	filterMaterialSplit=(MaterialSplitIfc)msservice.getMSplit(materialNumber);
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
    	PartHelper helper = new PartHelper();
    	partNumber = helper.getPartNumber(partNumber);
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
        QMQuery query = new QMQuery("CDFilterPart");
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
     * @return String ɸѡ��Ψһ��ʶ�����+�汾+��������״̬+·��+�����
     */
    private final String getPartIdentity(final QMPartIfc part)throws QMException
    {
   	PartHelper partHelper = new PartHelper();
	
		 String partVersion ="";	 
    	String partnumber ="";

		 if(djbom!=null&&djbom.size()>0){
			//  partnumber =getMaterialNumber(partIfc,partVersion);
			  Iterator iter = djbom.iterator();
		        while (iter.hasNext())
		        {
		            Object[] obj = new Object[5];
			    	obj = (Object[])iter.next();
			    	partnumber=(String) obj[1]; //��Ŵ��汾
		            QMPartIfc partIfcnew = (QMPartIfc)obj[0]; 
		            if(partIfcnew.getPartNumber().equals(part.getPartNumber()))
		              break;
		        }
		 }else{
			  partVersion = PartHelper.getPartVersion(part);
			  partnumber =partHelper.getMaterialNumber(part,partVersion);
		 }
//			CCBegin SS6
       // String returnString = partnumber + part.getLifeCycleState().getDisplay() + routeAllCode + xnj;
        String returnString = partnumber;
//		CCEnd SS6
        if(logger.isDebugEnabled())
            logger.debug("getPartIdentity(QMPartIfc) - end");
        return returnString;
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
        String xnj = "";
		boolean virtualFlag=matSplitIfc.getVirtualFlag();
		 if(virtualFlag){
			 xnj = "Y";
		 }
       // System.out.println("getPartIfc xnj======="+xnj);
		 //CCBegin SS1
        //String returnString = matSplitIfc.getPartNumber() ++matSplitIfc.getState() + matSplitIfc.getRoute()+xnj;
//		CCBegin SS6
		 // String returnString = matSplitIfc.getPartNumber() +matSplitIfc.getState() + matSplitIfc.getRoute()+xnj;
		//����Ҫ�ж���������״̬����ȡ���ϵ�ʱ��
		 String returnString = matSplitIfc.getPartNumber();
//		CCEnd SS6
		 //CCEnd SS1
		// System.out.println("returnString======"+returnString);
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
        matStructIfc.setDefaultUnit("��");
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
            QMXMLMaterialSplit xmlMatSplit, String[] a)
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
        matStructIfc.setDefaultUnit("��");
        matStructIfc.setQuantity(Float.parseFloat(a[3]));
        matStructIfc.setOptionFlag(false);
  
        //��ѯ�����Ӽ��Ķ�������
        List rootMatNumberList = getRootMatSplit(a, xmlMatSplit.getRouteCode());
        //�㲿���ṹ��XML��ʽ
        QMXMLMaterialStructure xmlMatStruct = new QMXMLMaterialStructure();
        String childNumber = "";
        for (int i = 0; i < rootMatNumberList.size(); i++)
        {
            childNumber = (String) rootMatNumberList.get(i);
            matStructIfc.setChildNumber(childNumber);
           
            //�����O�����
            if(strctPublishType.equals("O"))
            	continue;
            //CCBegin SS12
            //�����ź��Ӽ���,�滻ǰ�������ͷ������в��ü���Ӧ�����ڴ˴���ֵ��
//            System.out.println("mapid11111111==="+rootPartNumber+"-"+childNumber+"-"+a[3]);
//            System.out.println("partMapnew="+partMapnew);
            String[] adoptParts = new String[10];
            
            if(partMapnew.get(rootPartNumber+"-"+childNumber+"-"+a[3])!=null){
            	 adoptParts = (String[]) partMapnew.get(rootPartNumber+"-"+childNumber+"-"+a[3]);
    
            		//System.out.println("22222222222222222");
           		    matStructIfc.setSubGroup(adoptParts[6]);
                    matStructIfc.setBeforeMaterial(adoptParts[3]);
                   // System.out.println("adoptParts[7]="+adoptParts[7]);
                    matStructIfc.setBeforeQuantity(Float.parseFloat(adoptParts[7]));
                    partMapnew.remove(rootPartNumber+"-"+childNumber+"-"+a[3]);
            }
                else{//��������Ӽ��������Ҳ�����Ӧ�ģ��п����������滻���͡�������Ҫ�������滻��������
                	
                	//System.out.println("cxvec11111111==="+cxvec);
//                	System.out.println("rootPartNumber11111111==="+rootPartNumber);
            		if(cxvec.contains(rootPartNumber)){
            			adoptParts = (String[]) partMapnew.get(cxvec.get(0)+"-"+childNumber+"-"+a[3]);
            			if(adoptParts!=null){
//            				System.out.println("cxvec.get(0)==="+cxvec.get(0)+"-"+childNumber+"-"+a[3]);
//                			System.out.println("33333333");
                   		    matStructIfc.setSubGroup(adoptParts[6]);
                            matStructIfc.setBeforeMaterial(adoptParts[3]);
                            if(adoptParts[7]!=null){
                            	 matStructIfc.setBeforeQuantity(Float.parseFloat(adoptParts[7]));
                            }
                            partMapnew.remove(adoptParts[5]+"-"+childNumber+"-"+a[3]);
            			}
            			
                       
                      
            		}
	
            }
            xmlMatStruct = new QMXMLMaterialStructure(matStructIfc);
            xmlMatStruct.setStructurePublishType(strctPublishType);
            //CCEnd SS12
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
    public List getRootMatSplit(String[] a, String parentRouteCode)
            throws QMException
    {
    	PartHelper partHelper =  new PartHelper();
        List rootMatNumberList = new ArrayList();
        if(!rootMatSplitMap.containsKey(a[0]))
        {
            //��ѯ�Ӽ���Masterֵ����
            try
            {
            	//PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
            	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
                //1. ���Ȳ�ѯ���Ӽ����°汾
                QMPartMasterIfc childPartIfc = null;

//				 }
                Collection rootList = (Collection)msservice.getRootMSplit(a[0],a[2]);
                Iterator iter = rootList.iterator();
                //�Ƿ���Ҫƥ��ı�־
                boolean isMatching = RemoteProperty.getProperty(
                        "com.faw_qm.cderp.isMatching", "false").equalsIgnoreCase(
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
                	
                //	String partVersion = (String) scbb.get(part.getPartNumber());

//    				 if(partVersion==null||partVersion.length()==0){
//    					 partVersion = PartHelper.getPartVersion(part);
//    				 }
    				//String childNumber =partHelper.getMaterialNumber(part,partVersion);
                	//String partVersion = PartHelper.getPartVersion(part);
                	String childNumber =a[0];
                    rootMatNumberList.add(childNumber);
                }
                rootMatSplitMap.put(a[0], rootMatNumberList);
            }
            catch (QMException e)
            {
                throw new QMException(e);
            }
        }
        else
        {
            rootMatNumberList = (List) rootMatSplitMap.get(a[0]);
        }
        return rootMatNumberList;
    }
}
