/**
 * ���ɳ���MaterialSplitServiceEJB.java	1.0              2007-10-7
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �༭����·�߲ɹ���ʶΪ��ʱ��ִ��ERP����XML��ִ�й���������׼��ϵͳû����ERP ������ 2014-02-28
 * SS2 ���Ͻṹ������г�����������״̬Ϊ�����е��Ӽ� ������ 2014-03-05
 * SS3 ƽ̨����A034-2015-0223 ���ϼб���󣬵���������롣 liunan 2015-4-15
 * SS4 ƽ̨����A034-2015-0224 ȡ�����ɽӿڷ�����������ݶ���·�ߡ��ɹ���ʶ���������ϵĹ��� liunan 2015-4-15
 * SS5 ����xml�ļ��� Ҫ������ź����Ϻ��г�Ʒ�ű���һ��
 * SS6 ��ȡ����Դ�汾����
 */
package com.faw_qm.erp.ejb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.erp.exception.ERPException;
import com.faw_qm.erp.exception.QMXMLException;
import com.faw_qm.erp.model.FilterPartInfo;
import com.faw_qm.erp.model.InterimMaterialSplitIfc;
import com.faw_qm.erp.model.InterimMaterialSplitInfo;
import com.faw_qm.erp.model.InterimMaterialStructureIfc;
import com.faw_qm.erp.model.InterimMaterialStructureInfo;
import com.faw_qm.erp.model.MaterialSplitIfc;
import com.faw_qm.erp.model.MaterialSplitInfo;
import com.faw_qm.erp.model.MaterialStructureIfc;
import com.faw_qm.erp.model.MaterialStructureInfo;
import com.faw_qm.erp.model.SameMaterialIfc;
import com.faw_qm.erp.util.BaseDataPublisher;
import com.faw_qm.erp.util.Messages;
import com.faw_qm.erp.util.PartHelper;
import com.faw_qm.erp.util.RequestHelper;
import com.faw_qm.erp.util.RouteCodeIBAName;
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
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.iba.value.model.StringValueIfc;

// CCEnd by dikfeng 20090217

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
 * @author л��
 * @version 1.0
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

	
	/**
	 * ���������ļ��õ��������ϺŵĹ��ɷ�ʽ��true�������+·�ߴ��룻false������š�
	 */
	private static boolean addRouteCode = RemoteProperty.getProperty(
			"addRouteCode", "false").equalsIgnoreCase("true");

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

	private static final String RESOURCE = "com.faw_qm.erp.util.ERPResource";

	/**
	 * ���������ļ��õ��Ƿ񷢲���ͷ����
	 */
	private static boolean publicCyclePart = RemoteProperty.getProperty(
			"publicCyclePart", "false").equalsIgnoreCase("true");

	// 20080103 begin
	/**
	 * �������ʱ��������·�ߴ�������������ϱ�������磺ɢ�����ġ��ɹ������룬��ֺ���������������ʱ�����ɹ��������Ӧ�����ϱ��ʹ������ţ��������ϱ��ʹ�ã������+��-��+·�ߴ��롣
	 */
	private static final String specialRouteCode = RemoteProperty.getProperty(
			"specialRouteCode", "�ɹ�");

	/**
	 * û�й���������Ϣ�Ĺ��ղ��Ŵ����ַ������ԡ�������Ϊ�ָ����������ù���ʱ�������й���ı���Ƿ��ڹ��յĹ���������Ϣ�е�У�顣
	 */
	private static final String noProcessRouteCode = RemoteProperty
	.getProperty("noProcessRouteCode", "CY");
	//������ 20130102
	/**
	 * ��ù���·�߲ɹ���ǡ�
	 */
	private static HashMap CGBJMap = null;

	/**
	 * �ԡ�������Ϊ�ָ������������ʱ��Ҫ��������ж��·�ߴ����Ҹ�·�ߴ��벻���ڵ�һ�������ڲ��ʱû�и�·�ߴ�������ϣ�
	 * ����·����ȥ����·�ߴ�����ٲ�֣��磺ɢ�����ġ��ɹ������룬��ֺ������������㲿��ʱ�����·��ΪSX-DH-CG��
	 * ����·��SX-DH�������·��ΪSX-CG-DH������·��SX-DH�������·��ΪCG-SX-DH��
	 * ����Ȼ����·��CG-SX-DH�������·��ֻ��CG�����Ե���·��CG����
	 */
	private static final String deleteWhenSplitRouteCode = RemoteProperty
	.getProperty("deleteWhenSplitRouteCode", "�ɹ�");

	/**
	 * deleteWhenSplitRouteCode�ļ��ϡ�
	 */
	private static HashMap specRCHashMap = null;

	/**
	 * ���������ļ��õ������Ĭ��ѡ���·�������������ơ�
	 */
	private static String defRouteIBA = RemoteProperty.getProperty(
			"defRouteIBA", "·��1");

	/**
	 * ���ŷָ��������ڷָ�useProcessPartRouteCode��
	 */
	private String delimiter = "��";
	private String delimiter1 = ",";
	//���һ���Ӽ�
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
				throw new PartException(RESOURCE, "CP00001", null);
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
	/**
	 * ������ϣ�ERP����ר��
	 * @param coll �㲿���ļ���
	 * @param baselineiD �㲿����ŵĻ���
	 * @param isPublishRoute �Ƿ���·�߷���
	 * @param routeID ������·��
	 * @throws QMException
	 */
	public Vector split(String routeID)throws QMException
	{
		HashMap hamap;
		PersistService pservice;
		Vector filterPartMap = new Vector();
		QMPartIfc partIfc;
		String filterPartBsoIDs[];
		PartHelper partHelper =  new PartHelper();
		// ������ʶ�ǰ�����׼�������ǰ��գ�bom����
		// ��ö���·�� routepartlink����
		HashMap partLinkMap = new HashMap();
		// �洢���ӹ�ϵ
		HashMap partTemp = new HashMap();
		HashMap partTempNew = new HashMap();
		try {
			RequestHelper.initRouteHashMap();
			hamap = new HashMap();
			Collection collection = new Vector();
			pservice = (PersistService) EJBServiceHelper
					.getService("PersistService");
			StandardPartService sp = (StandardPartService) EJBServiceHelper
					.getService("StandardPartService");

			// ����ǰ�����׼�������������ǰ�������Ӽ��Ļ���Ҫ�Ե�ǰ������Ҷ���������Ӽ����ϵĹ�ϵ���д���questions
			if (routeID == null)
				throw new QMException("Ҫ������·�߲����ڣ����ܽ������ݷ�������");
			TechnicsRouteListIfc routelistifc = (TechnicsRouteListIfc) pservice
					.refreshInfo(routeID);
			//���·�߹������㲿��
			Collection coll1 = getPartByRouteList(routelistifc, partLinkMap);
			//System.out.println("11111111111111111111111111111coll1=============="+coll1);
			if (coll1 == null)
				throw new QMException("û�л�ù��������");
			// collection.addAll(coll1);
			// ������20140102 ���һ���Ӽ�
			Iterator it = coll1.iterator();
			
			while (it.hasNext()) {
				QMPartIfc part = (QMPartIfc) it.next();
//				System.out.println("11111111111111111111111111111part1=============="+part);
//				System.out.println("collection000000000000000000000000000=============="+collection);
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
								collection.add(subpart);
//								System.out.println("collection11111111111111111111111111111=============="+collection);
//								System.out.println("subpart=============="+subpart);
							}
						}
						
					}
					
					
				}
				//System.out.println("11111111111111111111111111111part2=============="+part);
				if(!partTempNew.containsKey(part.getBsoID())){
					collection.add(part);
					partTempNew.put(part.getBsoID(), part);
				}
				
				
			//	System.out.println("collection222222222222222222222222=============="+collection);
			}

			/// System.out.println("collection3333333333333333333=============="+collection);
			// System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz1111111111111=============="+partLinkMap);
			// �����Ҫ��ֵ��㲿���ļ���
			filterPartMap = filterParts(collection, partLinkMap);
			//System.out.println("11111111111111111111111111111filterPartMap=============="+filterPartMap);
			if (filterPartMap == null || filterPartMap.size() == 0) {
				//logger.debug("��������û����Ҫ��ֵ��㲿����");
				System.out.println("��������û����Ҫ��ֵ��㲿����");
				return null;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new QMException(ex);
		}
		partIfc = null;
		filterPartBsoIDs = new String[filterPartMap.size()];
		// ���������Ҫ��ֵ��㲿����id
		for (int mm = 0; mm < filterPartMap.size(); mm++) {
			String partid = (String) filterPartMap.elementAt(mm);
			filterPartBsoIDs[mm] = partid;
		}

//		 System.out.println("filterPartBsoIDs.lengthzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz=============="+filterPartBsoIDs.length);
		// System.out.println("filterPartMapzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz4444444444========="+filterPartMap);
		// ������������·��
		// ����·�ߴ洢���󣬰���һ��·�ߺͶ���·��
		HashMap routeTempMap = new HashMap();

		for (int i = 0; i < filterPartBsoIDs.length; i++) {
			Object[] objRoute = new Object[2];
			partIfc = (QMPartIfc) pservice.refreshInfo(filterPartBsoIDs[i]);

			Vector routevec = new Vector();
			// һ��·��
			Vector routevec1 = new Vector();

			ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) partLinkMap
					.get(partIfc.getBsoID());
			if (partLinkMap.get(partIfc.getBsoID()) == null) {
				routevec = RequestHelper.getRouteBranchs(partIfc, null);
			} else {
				routevec = RequestHelper.getRouteBranchs(partIfc, link);
			}
			// System.out.println("routevecczzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz4444444444========="+routevec);
			routevec1 = PartHelper.getRouteBranchs(partIfc, null);
			objRoute[0] = routevec;
			objRoute[1] = routevec1;
			routeTempMap.put(partIfc.getBsoID(), objRoute);

		}
		// ���ÿһ���㲿�����д���
		for (int i = 0; i < filterPartBsoIDs.length; i++) {
			partIfc = (QMPartIfc) pservice.refreshInfo(filterPartBsoIDs[i]);
			// System.out.println("partIfczzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz4444444444========="+partIfc);

			List usageLinkList = getUsageLinks(partIfc);
			Object[] objRoute = new Object[2];
			Vector routevec = new Vector();
			// һ��·��
			Vector routevec1 = new Vector();
			objRoute = (Object[]) routeTempMap.get(partIfc.getBsoID());
			routevec = (Vector) objRoute[0];
			routevec1 = (Vector) objRoute[1];
			// CCBegin by dikefeng
			// 20100624���ж��ǲ���ͨ������·�߷����������ͨ������·�ߣ���ǰ�ڻ����listroutepartlinkinfo����ȥ���Ա�
			// ȡ����ȷ��·��
			// System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa00000000000=============="+partIfc.getBsoID()+"sssssssssss"+partIfc.getPartNumber());
			// System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa=============="+partLinkMap);
			// //��׼�µļ��������Ͳ��ٷ��� dele
			ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) partLinkMap
					.get(partIfc.getBsoID());

			// ����·��
			String routeAsString = "";
			// һ��·��
			String routeAsString1 = "";
			String routeAllCode1 = "";
			String routeAssemStr1 = "";
			// ȫ·�ߴ�
			String routeAllCode = "";
			// װ��·��   
			String routeAssemStr = "";
			String lsbb = "";
			//System.out.println("routevecuuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa=============="+routevec);
			boolean isMoreRoute = false;
			if (routevec.size() != 0) {
				if (routevec.size() > 0) {
					routeAsString = (String) routevec.elementAt(5);
					routeAllCode = (String) routevec.elementAt(1);
					routeAssemStr = (String) routevec.elementAt(2);
					isMoreRoute = (new Boolean((String) routevec.elementAt(4)))
							.booleanValue();
					//lsbb = (String) routevec.elementAt(6);
				}

				if (routevec1.size() > 0) {
					routeAsString1 = (String) routevec1.elementAt(5);
					routeAllCode1 = (String) routevec1.elementAt(1);
					routeAssemStr1 = (String) routevec1.elementAt(2);
				}

				// ������ 20140102
				// 1) �ɹ���ʶ����ѡ��X������������·��ǰ��ӡ�ë�� ��·�ߵ�λ���ٽ��в�֡�
				// 2) �ɹ���ʶ����ѡ��X-1������������·��ǰ��ӡ����Ʒ��·�ߵ�λ���ٽ��в�֡�
				String cgbs = "";
				if(link!=null){
					cgbs = link.getStockID();
					lsbb = link.getAttribute1();
				}
				 
				// System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa00000000000=============="+link);
				// System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuaaaaaaaaaaaaaaaaaaaaaaa00000000000=============="+cgbs);
//CCBegin SS1
				//CCBegin SS4
				/*
				if (cgbs != null&&cgbs != "") {
					//CCEnd SS1
					if (cgbs.equals("X")) {
						routeAsString = "ë��-" + routeAsString;
						routeAllCode = "ë��-" + routeAllCode;
					} else if (cgbs.equals("X-1")) {
						routeAsString = "���Ʒ-" + routeAsString;
						routeAllCode = "���Ʒ-" + routeAllCode;

					}
				}
				*/
				//CCEnd SS4

				 //System.out.println("routeAllCode111111111111111111111111111111111111111111111111111111111wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+routeAllCode);

			}
//			CCBegin SS5
//			CCBegin SS6
//			 ��ȡ��ǰ��������ϼ�
			String folder = partIfc.getLocation(); 
			// ��������Դ�汾
			String jsbb = partHelper.getPartVersion(partIfc);
//			CCBEnd SS5
			//System.out.println("partnumber000000000000000000"+partIfc.getPartNumber());
            String partnumber = partHelper.getPartNumber(partIfc,folder,lsbb,jsbb);
            //if(partIfc.getPartNumber().equals("1701110-A4K")){
              System.out.println("partnumber11111111111111========"+partnumber);
              System.out.println("lsbb11111111111111========"+lsbb);
            //}

			// �����㲿����ţ�ɾ�����е����ϲ�ֺ����ϲ�ֽṹhere
			// Ϊ���·�ɾ����ǣ������ϱ�ͽṹ���ж���¼�˹������Ϻ����Ͻṹ��Ӧ��ɾ���ļ�¼�����������²��֮ǰ����ɾ��

			deleteMaterialStructure(partIfc, true,partnumber);
			List oldMSplitList = deleteMaterialSplit(partIfc, true,partnumber);
			
			List mSplitListBiaoJi = new ArrayList();
			// �������ѷ���������
			mSplitListBiaoJi = getAllMSplit(partnumber);
//			CCBEnd SS5
			// ֱ�����ϣ����ٻ������ϱ��
			HashMap oldMaterialHashMap = new HashMap();
			for (int dd = 0; dd < mSplitListBiaoJi.size(); dd++) {
				MaterialSplitIfc a = (MaterialSplitIfc) mSplitListBiaoJi
						.get(dd);
				oldMaterialHashMap.put(a.getMaterialNumber(), a);

			}
			// System.out.println("dddddddddddddddddddssssssss=========="+oldMaterialHashMap);

			// �����㲿����ţ�����������ϲ�ֽṹ���ŵ�mStructureListBaoJi��
			List mStructureListBaoJi = new ArrayList();
			//degug
			mStructureListBaoJi = getMStructure(partIfc.getPartNumber());
			// ֱ�ӻ���ṹ�����ٻ�����
			HashMap oldMaterialStructrue = new HashMap();
			for (int ff = 0; ff < mStructureListBaoJi.size(); ff++) {
				MaterialStructureIfc s = (MaterialStructureIfc) mStructureListBaoJi
						.get(ff);
				oldMaterialStructrue.put(s.getParentNumber() + ";;;"
						+ s.getChildNumber(), s);
			}

			// ����㲿����һ��·�ߵ�����·�����һ����λ���ǡ��ᡱ ����������·�߰������ᡱ
			// ��ü�����ERPϵͳ����
		//	 System.out.println("routeAsString1ssssssssssss=========="+routeAsString1);
			List routeCodeListTemp = getRouteCodeList(routeAsString1);
			boolean flag1 = true;
			for (int m = routeCodeListTemp.size() - 1; m >= 0; m--) {

				// ����㲿����һ��·�ߵ�����·�����һ����λ���ǡ��ᡱ ����������·�߰������ᡱ����ü�����ERPϵͳ����
				if (m == routeCodeListTemp.size() - 1) { 
					String routeLast = (String) routeCodeListTemp.get(m);
					//System.out.println("routeLastssssssssssss=========="+routeLast);
					if (!routeLast.equals("��")
							&& (routeCodeListTemp.contains("��"))) {
						//System.out.println("������==========");
						flag1 = false;
					}
				}
			}

			//����㲿���Ķ���·��Ϊ�ա�һ��·��Ϊ��Э=�ᡱ���Ҹü�û�и��㲿��������㲿����������
		
			//�˴�����˼�ǣ���������·�߹��������
			
			if (!partTemp.containsKey(partIfc.getBsoID())) {
				if (routeAllCode == null || routeAllCode.length() == 0) {
					if (routeAllCode1.equals("Э=��")) {
						flag1 = false;
					}
				}
			}   
			//System.out.println("flag1ssssssssssss=========="+flag1);
			if (!flag1) {
				filterPartMap.remove(filterPartBsoIDs[i]);
				continue;
			}
			
			// �����׼�������㲿����һ���Ӽ�����·��Ϊ�գ�һ��·��Ϊ��Э=�ᡱ
			// ��ü��Ķ���·�����쵥λ����Ϊ��Э����װ��·�ߵ�λ�͸����Ķ���·�����������
			// �ĵ�һ���ӹ�����һ�£�������·�ߵ�һ����λ�ǡ��ᡱ�����ߵ�һ���ǡ�Э���ڶ������ᣩ
			//�˴�����˼�ǣ���������·�߹�����������Ӽ�
			if (partTemp.containsKey(partIfc.getBsoID())) {
				if (routeAllCode == null || routeAllCode.length() == 0) {
					if (routeAllCode1.equals("Э=��")) {
						Vector routev = new Vector();

						routeAsString = "Э";
						QMPartIfc parentPart = (QMPartIfc) partTemp.get(partIfc
								.getBsoID());
						Object[] objRout = (Object[]) routeTempMap.get(parentPart
								.getBsoID());
						routev = (Vector) objRoute[0];
						String parentAss = (String) routev.elementAt(2);
						if (parentAss == null || parentAss.length() == 0)
							continue;
						String[] aa = parentAss.split(parentAss);
						if (aa[0].equals("Э")) {
							routeAssemStr = aa[1];
						} else { 
							routeAssemStr = aa[0];
						}
						routeAllCode = "Э=" + routeAssemStr;

					}
				}
			
			}

			// ��Ҫ�������ϲ�ֵ�����£����Ƚ�����·�߷ֽ�Ϊһ����·���ַ�

			List routeCodeList = getRouteCodeList(routeAsString);
//			 System.out.println("routeAsString==" + routeAsString);
//			 System.out.println("routeCodeList==" + routeCodeList);
			if(partIfc.getPartNumber().equals("1701516A8A")){
			 System.out.println("routeCodeListrouteCodeListww======="+routeCodeList);
			}
			HashMap routeCodeMap = new HashMap(5);
			Vector mSplitList = new Vector();
			Vector co = new Vector();

			for (int m = routeCodeList.size() - 1; m >= 0; m--) {
				String routeCode = (String) routeCodeList.get(m);
				String makeCodeNameStr = "";
				makeCodeNameStr = RemoteProperty
						.getProperty("com.faw_qm.erp.routecode." + routeCode);
//				System.out.println("routeCode==" + routeCode);
//				System.out.println("makeCodeNameStr==" + makeCodeNameStr);
				// CCBegin by chudaming 20100428 ��ͷ��+1�Ĺ������
				int n = 0;
				for (int k = m - 1; k >= 0; k--) {
					String routeCode1 = (String) routeCodeList.get(k);
					String makeCodeNameStr1 = RemoteProperty
							.getProperty("com.faw_qm.erp.routecode."
									+ routeCode1);
					if (makeCodeNameStr != null) {
						if (makeCodeNameStr.equals(makeCodeNameStr1)) {
							n++;

						}
					}

				}
				
				// System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn============================================"+jsbb);
				// CCEnd by chudaming 20100428 ��ͷ��+1�Ĺ������
				MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
				//System.out.println("lsbb==" + lsbb);
		

				if (makeCodeNameStr == null)
					makeCodeNameStr = "";
				if (m == routeCodeList.size() - 1) {
					// �����·�ߴ��е����һ��·�ߵ�λ
					mSplitIfc.setVirtualFlag(false);

					// ���������ļ���ȡ�㲿������·�����һ��·�ߵ�λ�ı��뷽ʽ������������+·�ߵ�λ����ֱ������
					//��������һ��·�ߵ�λ�򣬲���Ҫ��·�ߵ�λ
					String materialNumber = "";
	
					materialNumber=partnumber;

					//System.out.println("materialNumber11111111=="+ materialNumber);
//					CCBegin SS6
					mSplitIfc.setMaterialNumber(materialNumber);
//					CCBEnd SS5
					//������
					mSplitIfc.setRootFlag(true);
					hamap.put(partIfc.getMasterBsoID(), mSplitIfc);
				}
				// ����������һ��·�ߵ�λ��
				// ��ȡ�������Ϲ��ɹ�������Ǽ�·�ߵ�λ���ൺΪ���ӣ������жϵ�ǰ·���Ƿ��Ѿ����ֹ�
				// û��������Ϊ�㲿�����+·�ߵ�λ����������Ϊ�㲿�����+·�ߵ�λ+����
				// �������Ϊ�������ϲ���·�ߵ�λ����ʵ����жϺ͵�ǰ�������֧û��ʲô��ϵ��������ط�Ψһ����������ж��ǲ���������·��
				// ��λ �Լ��Ƿ��л�ͷ������
				else {
					String materialNumber = "";
					if(makeCodeNameStr.equals("")){
						materialNumber=partnumber;
					}else{
						materialNumber=partnumber+ dashDelimiter + makeCodeNameStr;;
					}
				
		

//					System.out.println("materialNumber22222222=="
//							+ materialNumber);
					if (n == 0) {
					
						mSplitIfc.setMaterialNumber(materialNumber);
					} else {
					
						mSplitIfc.setMaterialNumber(materialNumber + dashDelimiter + n);
						// routeCodeMap.get(makeCodeNameStr));
					}

				}
				//CCBegin SS5
				mSplitIfc.setPartNumber(partnumber);
//				CCBEnd SS5
				if (jsbb != null && jsbb.length() > 0) {
					mSplitIfc.setPartVersion(jsbb);
				} else {
					mSplitIfc.setPartVersion(partIfc.getVersionID());
				}

				mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
				mSplitIfc.setSplited(true);
				//��������¼�����
				if (m == 0) {
					
					if (usageLinkList != null && usageLinkList.size() > 0)
						mSplitIfc.setStatus(2);//�����㲿������һ���ṹ
					else
						mSplitIfc.setStatus(0);//û���¼��ṹ
				} else {
					mSplitIfc.setStatus(1);//�м���
				}
				mSplitIfc.setRouteCode(routeCode);
				mSplitIfc.setRoute(routeAllCode.toString());
				// �����㲿������������������Ҫ������������Ϣ

				setMaterialSplit(partIfc, mSplitIfc);

				// // ͬ�ϳ߹���ȡ��
				// mSplitIfc.setIsMoreRoute(isMoreRoute);

				mSplitList.add(mSplitIfc);

			}

			Object[] objs = mSplitList.toArray();

			// System.out.println("mSplitList==" + mSplitList);
			for (Iterator iter = mSplitList.iterator(); iter.hasNext();) {
				// questions������㲿������·�߱仯��Ҫ���²�֣���������ĳ�������ϵ�����û�з����仯����ôҲ����Щ��������ΪU��ʶ
				MaterialSplitIfc ifc = (MaterialSplitIfc) iter.next();
//				System.out
//						.println("dsadasdasdasdasd" + ifc.getMaterialNumber());

				//�鿴ϵͳ���Ƿ���ڸ���������ϣ����û�д�N������У��жϾ������Ƿ���������ϣ����û�д�N�������U
				//System.out.println("old1111111111111111=========="+oldMaterialHashMap);
				if (oldMaterialHashMap != null && oldMaterialHashMap.size() > 0) {
					// System.out.println("00000=========="+ifc.getMaterialNumber());
					// System.out.println("old1111111111111111=========="+oldMaterialHashMap.get(ifc.getMaterialNumber()));
					if (oldMaterialHashMap.get(ifc.getMaterialNumber()) != null) {
						
						ifc.setMaterialSplitType("U");
						// }
						// CCEnd by chudaming 20100928
						// System.out.println("dele====================================="+oldMaterialHashMap.get(ifc.getMaterialNumber()));
						pservice
								.deleteValueInfo((MaterialSplitIfc) oldMaterialHashMap
										.get(ifc.getMaterialNumber()));
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
//				System.out.println("getMaterialNumber=="
//						+ ifc.getMaterialNumber());

				// System.out.println("shenmenea ========"+ifc.getRoute());
			}
			// �������������ݶ���ɾ�����
			Iterator oldMaterialIter = oldMaterialHashMap.keySet().iterator();
			while (oldMaterialIter.hasNext()) {
				String key = (String) oldMaterialIter.next();
				MaterialSplitIfc oldMaterialSplitIfc = (MaterialSplitIfc) oldMaterialHashMap
						.get(key);
				oldMaterialSplitIfc.setMaterialSplitType("D");
				// System.out.println("222222222222222222"+oldMaterialSplitIfc.getMaterialNumber());
				pservice.saveValueInfo(oldMaterialSplitIfc);
			}
			// CCEnd by dikefeng 20100419
			//����ͬһ�������ֺ�����ֱ�ӵĹ�ϵ
			for (int p = 0; p < mSplitList.size(); p++) {
				MaterialSplitIfc parentMSIfc = (MaterialSplitIfc) mSplitList
						.get(p);
				MaterialSplitIfc childMSfc = null;
				if (p != mSplitList.size() - 1)//����������һ�������ȡ����һ������
					childMSfc = (MaterialSplitIfc) mSplitList.get(p + 1);
				if (parentMSIfc != null) {
					MaterialStructureIfc mStructureIfc = new MaterialStructureInfo();
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
						if (oldMaterialStructrue.get(parentMSIfc
								.getMaterialNumber()
								+ ";;;" + childMSfc.getMaterialNumber()) != null) {
							MaterialStructureIfc oldStructrue = (MaterialStructureIfc) oldMaterialStructrue
									.get(parentMSIfc.getMaterialNumber()
											+ ";;;"
											+ childMSfc.getMaterialNumber());
							mStructureIfc.setMaterialStructureType("O");
							pservice.deleteValueInfo(oldStructrue);
							oldMaterialStructrue.remove(parentMSIfc
									.getMaterialNumber()
									+ ";;;" + childMSfc.getMaterialNumber());
						} else {
							mStructureIfc.setMaterialStructureType("N");
						}
						// CCEnd by dikefeng 200100419
						mStructureIfc.setChildNumber(childMSfc
								.getMaterialNumber());
						mStructureIfc.setQuantity(1.0F);
						mStructureIfc.setLevelNumber(String.valueOf(p));
						mStructureIfc
								.setDefaultUnit(childMSfc.getDefaultUnit());
						// ������������ϲ�ֽṹ�ı��� dikef
						mStructureIfc = (MaterialStructureIfc) pservice
								.saveValueInfo(mStructureIfc);
						// }
					} else if (parentMSIfc.getStatus() == 2)
						logger.debug("2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���");
					else if (parentMSIfc.getStatus() == 0)
						logger.debug("0-��ײ����ϡ�");
				}
			}
			// ��ʹ�õ����Ͻṹ��ɾ�����
			Iterator oldStruIte = oldMaterialStructrue.keySet().iterator();
			while (oldStruIte.hasNext()) {

				MaterialStructureIfc oldStru = (MaterialStructureIfc) oldMaterialStructrue
						.get(oldStruIte.next());

				oldStru.setMaterialStructureType("D");
				pservice.saveValueInfo(oldStru);
				// System.out.println("------------------------------------sssssssss--------------"+oldStru.get);
			}

		}
		if (logger.isDebugEnabled())
			logger.debug("split(String, boolean) - end");
		// CCBegin by dikefeng
		// 20090422��Ϊ��ʹ���ڷ�����ʱ��֪�������·������϶�����Щ����Ҫ������ѹ��˺������嵥����һ��
	
		return filterPartMap;
		// CCEnd by dikefeng 20090422
	}

	

	public Collection getPartByRouteList(TechnicsRouteListIfc list)
	throws QMException
	{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		Collection c = new Vector();
		QMQuery query = new QMQuery("ListRoutePartLink");
		QueryCondition cond = new QueryCondition("leftBsoID", "=", list.getBsoID());
		query.addCondition(cond);
		Collection coll = ps.findValueInfo(query);
		for(Iterator iter = coll.iterator(); iter.hasNext();)
		{
			ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)iter.next();
			//����������·��������а汾�������ڻ�û�У��������������ʱ���أ��������������汾֮���ٴ�
			Collection cc = getPartByListRoutepart(linkInfo);
//			QMPartIfc partInfo = null;
//			for(Iterator ii = cc.iterator(); ii.hasNext(); c.add(partInfo))
//				partInfo = (QMPartIfc)ii.next();

		}

		return c;
	}
	
	

	private Collection getPartByListRoutepart(ListRoutePartLinkInfo linkInfo)
	throws QMException
	{
		try{
		PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
		//��������ȡ·�߹�������汾�����·�߹����������汾 20131227
		String partBsoid = linkInfo.getRightBsoID();
		
		QMPartIfc part = (QMPartIfc) ps.refreshInfo(partBsoid);
		//System.out.println("part==="+part);
		QMQuery query = new QMQuery("QMPart");
		QueryCondition qc = new QueryCondition("bsoID", "=", part.getBsoID());
		query.addCondition(qc);
//		
		String partVersion = part.getVersionID();
		//System.out.println("partVersion==="+partVersion);
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
	        //  System.out.println("ssssssssvvvvvvvvvvvvvvvvvvvvvvv==="+col1);
//	          System.out.println("ssssssssvvvvvvvvvvvvvvvvvvvvvvv==="+col1.size());
	          if(col1!=null){
	        	  if(col1.size()>0){
 	        	 while (iter22.hasNext()) {
 	        		ifc=(FilterPartInfo)iter22.next();
// 	        		a.add(ifc);
 	        	 }
 	        	 }
                 }
	         // System.out.println("ifc==="+ifc);
	          if(ifc!=null){
	        	 // System.out.println("ssssssss==ffffffffffffffffffff=");
	          if(partVersion
	     	         .compareTo(
	     	        		ifc.getVersionValue()) < 0)
	          {
//	        	    System.out.println("ssssssss==gggggggggggggggggggggggggg=");
	        	    QueryCondition qc1 = new QueryCondition("versionID", "=", ifc.getVersionValue());
//	        	    System.out.println("ssssssss==hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh=");
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
	//	��������� 20131231 �������ǵ�ǰ�汾���Բ������һ���汾
//		QueryCondition qc2 = new QueryCondition("iterationIfLatest", true);
//		query.addAND();
//		query.addCondition(qc2);
	
		return ps.findValueInfo(query, false);
		}catch(QMException e){
			e.printStackTrace();
			throw e;
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
	private final Vector filterParts1(Collection parts,String partroute)
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
		List filterPartList = filterByIdentity1(lalatestPartBsoIDs,partroute);
//		System.out.println("100304++++++++++++++++++++++=======jinru===filterBySplitRule======== ");
		
		filterBySplitRule1(filterPartList,partroute);
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
	private final void filterBySplitRule(final List filterPartList)
	 throws QMException 
	 {
	  if (logger.isDebugEnabled()) 
	  {
	   logger.debug("filterBySplitRule(List) - start"); //$NON-NLS-1$
	   logger.debug("������filterPartList==" + filterPartList);
	  }
	  PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
	  // �����ֵ��㲿�����ϡ�
	  final List removePartList = new ArrayList();
//	  System.out.println("100304++++++++filterPartList"+filterPartList.size()+"kkkkkkkkkkjjjjjjjjjjjj==="+filterPartList);
	  for (int i = 0; i < filterPartList.size(); i++) 
	  {
//		  System.out.println("daodijiciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===================================================");
	   MaterialSplitIfc materialSplitIfc = null;
	   final QMPartIfc partIfc = (QMPartIfc) filterPartList.get(i);
//	   System.out.println("cacacalalalalalala==="+partIfc.getPartNumber());
	   List mSplitList = new ArrayList();
	   try {
	    // ��������Ż�ȡ�Ѳ�ֵ����ϡ�
	    mSplitList = getSplitedMSplit(partIfc.getPartNumber());
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
	      if (partIfc.getVersionID().compareTo(
	        materialSplitIfc.getPartVersion()) < 0) 
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
	       } else if (tempMaterialSplit.getPartVersion()
	         .compareTo(
	           materialSplitIfc.getPartVersion()) < 0) 
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
	      Vector routevec = RequestHelper.getRouteBranchs(partIfc, null);
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
//	    	  System.out.println("100304++++++++flag0000000000000000000000000000-------------");
	       removePartList.add(partIfc);
	      }
	      if(!flag1 && !flag2)
	      {
//	    	  System.out.println("100304++++++++flag11111111111111111111111111111111-------------");
	       if(partIfc.getLifeCycleState().getDisplay().equals(tempMaterialSplit.getState()) && routeAllCode.equals(tempMaterialSplit.getRoute()))
	       {
//	    	   System.out.println("100304++++++++flag222222222222222222222222222222222222-------------");
	        removePartList.add(partIfc);
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
	         if(mSplitIfc.getState().equals(tempMaterialSplit.getState()) && mSplitIfc.getRoute().equals(routeAllCode))
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
	 
	  filterPartList.removeAll(removePartList);
	  //CCEnd by chudaming 20100609
	 } 
	
	
	private final void filterBySplitRule1(final List filterPartList,String partroute)
	 throws QMException 
	 {
	  if (logger.isDebugEnabled()) 
	  {
	   logger.debug("filterBySplitRule(List) - start"); //$NON-NLS-1$
	   logger.debug("������filterPartList==" + filterPartList);
	  }
	  PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
	  // �����ֵ��㲿�����ϡ�
	  final List removePartList = new ArrayList();
//	  System.out.println("100304++++++++filterPartList"+filterPartList.size()+"kkkkkkkkkkjjjjjjjjjjjj==="+filterPartList);
	  for (int i = 0; i < filterPartList.size(); i++) 
	  {
//		  System.out.println("daodijiciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===================================================");
	   MaterialSplitIfc materialSplitIfc = null;
	   final QMPartIfc partIfc = (QMPartIfc) filterPartList.get(i);
//	   System.out.println("cacacalalalalalala==="+partIfc.getPartNumber());
	   List mSplitList = new ArrayList();
	   try {
	    // ��������Ż�ȡ�Ѳ�ֵ����ϡ�
	    mSplitList = getSplitedMSplit(partIfc.getPartNumber());
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
	      if (partIfc.getVersionID().compareTo(
	        materialSplitIfc.getPartVersion()) < 0) 
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
	       } else if (tempMaterialSplit.getPartVersion()
	         .compareTo(
	           materialSplitIfc.getPartVersion()) < 0) 
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
	      Vector routevec = RequestHelper.getRouteBranchs(partIfc, null);
	      String routeAsString = "";
	      String routeAllCode = "";
	      String routeAssemStr = "";
//	      if(routevec.size() != 0)
//	      {
//	       routeAsString = (String)routevec.elementAt(0);
	       routeAllCode =partroute;
//	      }
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
//	    	  System.out.println("100304++++++++flag0000000000000000000000000000-------------");
	       removePartList.add(partIfc);
	      }
	      if(!flag1 && !flag2)
	      {
//	    	  System.out.println("100304++++++++flag11111111111111111111111111111111-------------");
	       if(partIfc.getLifeCycleState().getDisplay().equals(tempMaterialSplit.getState()) && routeAllCode.equals(tempMaterialSplit.getRoute()))
	       {
//	    	   System.out.println("100304++++++++flag222222222222222222222222222222222222-------------");
	        removePartList.add(partIfc);
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
	         if(mSplitIfc.getState().equals(tempMaterialSplit.getState()) && mSplitIfc.getRoute().equals(routeAllCode))
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
		if(baseValueIfc instanceof QMPartIfc)
		{
			Vector routevec = RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);
			String routeAsString = "";
			String routeAllCode = "";
			String routeAssemStr = "";
			if(routevec.size() != 0)
			{
				routeAsString = (String)routevec.elementAt(0);
				routeAllCode = (String)routevec.elementAt(1);
			}
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
		//System.out.println("100304++++++++++++++++++++++=======jinru===filterBySplitRule======== "+filterPartList);
		
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
	 * ������ϡ�
	 * noted by dikefeng 20100419,��Ϊ�ൺû�����ϲ�ֹ��̣�������������������ò��ϣ����Ȳ����ˣ������Ҫ�Ļ��ٸ�
	 * @param partBsoIDs
	 *            �ԡ�;��Ϊ�ָ������㲿��BsoID���ϡ�
	 * @param routes
	 *            ��Ҫ��ֵ�·�ߡ�
	 * @param doSplit
	 *            Ϊ����������ɰ汾�Ѳ��Ϊ���ϣ����β���Ƿ����²�֡������׼����boolean������true�����²�֣�false�������²�֡�
	 * @throws QMException
	 */
	public void split(String partBsoIDs, String routes, boolean doSplit)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("split(String, boolean) - start"); //$NON-NLS-1$
			logger.debug("������partBsoIDs==" + partBsoIDs);
			logger.debug("������doSplit==" + doSplit);
		}
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		// ִ�����ϲ��ǰ�Ĺ����㲿�����ܡ��õ����ϲ������������Ҫ���в�ִ�����㲿�����ϡ�
		HashMap filterPartMap = filterParts(partBsoIDs, routes);
		if (filterPartMap == null || filterPartMap.size() == 0) {
			// ��������û����Ҫ��ֵ��㲿����
			logger.debug("��������û����Ҫ��ֵ��㲿����");   
			return;
		}
		// ��Ҫ��ֵ��㲿����
		QMPartIfc partIfc = null;
		Object[] filterPartBsoIDs = filterPartMap.keySet().toArray();
		for (int i = 0; i < filterPartBsoIDs.length; i++) {
			partIfc = (QMPartIfc)pservice.refreshInfo((String)filterPartBsoIDs[i]);
			// ��ø��㲿����һ���ӽṹ��
			List usageLinkList = getUsageLinks(partIfc);
			// ���ϲ�ֵ�·�����ԡ��磺04��02��01;04-03-01
			String routeAsString = (String) filterPartMap.get(partIfc
					.getBsoID());

			// ��׼����ֵ��㲿���ľɰ汾�Ѳ��Ϊ��ת�������ϣ����ڽ�����ѡ���˲����²�֣��Ͳ����²�����ϣ�
			// ��Ҫ�滻�ɰ汾��Ϊ���㲿���İ汾�š��������ϲ�ֱ�����Ͻṹ��
			// ��������ϱ���ļ�¼��δת�������ϣ�������Ҫ���²�֡�
			List sPlitedMSplitList = getSplitedMSplit(partIfc.getPartNumber());
			if (sPlitedMSplitList != null && sPlitedMSplitList.size() > 0
					&& !doSplit) {
				replaceMaterialStructure(partIfc);
				replaceMaterialSplit(partIfc);
				continue;
			}
			// ɾ���㲿����Ӧ�����Ͻṹ�����Ѿ����ڵľɰ汾�����Ͻṹ��ϵ��������δ�ɹ������޷��ع���
			deleteMaterialStructure(partIfc);
			// ɾ���㲿����Ӧ�����ϲ�ֱ����Ѿ����ڵľɰ汾�����ϲ�ֶ���ͬʱ����ɾ�����ľɰ汾�����϶�Ӧ�ļ�¼���ϡ�Ϊ�滻���Ͻṹ����������Ϊ�ɰ汾����ʱʹ�á�
			List oldMSplitList = deleteMaterialSplit(partIfc);
			// ·��Ϊ�գ����������㲿����
			if (routeAsString != null && routeAsString.toString().equals("")) {
				logger.debug("·��Ϊ�գ����������㲿����");
				MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
				mSplitIfc.setRootFlag(true);
				mSplitIfc.setPartNumber(partIfc.getPartNumber());
				mSplitIfc.setPartVersion(partIfc.getVersionID());
				mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
				mSplitIfc.setMaterialNumber(partIfc.getPartNumber());
				mSplitIfc.setSplited(false);
				// ������Ӽ������ò㼶״̬Ϊ0�����Ӽ�������Ϊ2�������Ƿ񾭹���֣�0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
				if (usageLinkList != null && usageLinkList.size() > 0) {
					mSplitIfc.setStatus(2);
				} else {
					mSplitIfc.setStatus(0);
					// ������·�ߴ��룬��������йص����ϲ�ֺ��·�ߴ��롣
					// msIfc.setRouteCode("");
					// ������·�ߣ��������Ա���һ���������������·��,��·��ʱ�÷ֺš�;���ָ���
					// msIfc.setRoute("");
				}
				setMaterialSplit(partIfc, mSplitIfc);
				mSplitIfc = (MaterialSplitIfc) pservice.saveValueInfo(mSplitIfc);
			}
			// ·�߲�Ϊ�գ���Ҫ����㲿����
			else {  
				logger.debug("·�߲�Ϊ�գ���Ҫ����㲿����" + routeAsString);
				boolean hasSpecialRouteCode = false;
				// 20081205 zhangq begin
				// ·�ߣ��磺04��02��01��
				// String route = routeAsString;
				// logger.debug("route==" + route);
				// StringTokenizer st = new StringTokenizer(route,
				// dashDelimiter);
				// List routeCodeList = new ArrayList();
				// while (st.hasMoreTokens())
				// {
				// routeCodeList.add(st.nextToken());
				// }
				// ·�ߴ��뼯�ϣ��磺[04,02,01]��
				List routeCodeList = getRouteCodeList(routeAsString);
				// 20081205 zhangq end
				logger.debug("routeCodeList==" + routeCodeList);
				// ����·�ߴ��룬Ϊ�жϻ�ͷ����׼����
				HashMap routeCodeMap = new HashMap(5);
				// ���ø���·�߲�ֺõ����ϲ�ֶ���
				List mSplitList = new ArrayList();
				if (routeCodeList.contains(specialRouteCode)) {
					hasSpecialRouteCode = true;
					// ����·�ߴ������㲿����
				}
				for (int m = routeCodeList.size() - 1; m >= 0; m--) {
					String routeCode = (String) routeCodeList.get(m);
					// �����ͷ����
					if (!routeCodeMap.containsKey(routeCode)) {
						routeCodeMap.put(routeCode, "0");
					}
					MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
					// �������Ϻţ���ֺ�����Ϻţ��������+��-��+·�ߴ������,��ͷ���ڶ��γ��ּ�β��"-1"��
					if (m == routeCodeList.size() - 1) {
						// ������ҵ�ж������Ϻţ������+��-��+·�ߴ��롣
						if (addRouteCode) {
							mSplitIfc.setMaterialNumber(partIfc.getPartNumber()
									+ dashDelimiter + routeCode);
							// ɢ������˾����������֣�һ���������⹤��·�ߴ��룬��һ����û������·�ߴ��롣
						} else {
							// ������·�ߴ�������������֣���ǰ·�ߴ����������·�ߴ��룬�����ǡ�
							if (hasSpecialRouteCode) {
								// ��ǰ·�ߴ���������·�ߴ��룬���Ϻţ�����š�
								if (routeCode.equals(specialRouteCode)) {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber());
								}
								// �������Ϻţ������+��-��+·�ߴ��롣
								else {
									// CCBegin zhangq 20090324
									// ����ֵ��㲿�������Ͳ�Ϊ��Ʒʱ�����һ��·�ߴ�������ϵ����Ϻ�=�����+·�ߴ���
									if (partIfc.getPartType().toString()
											.equalsIgnoreCase("product")) {
										mSplitIfc.setMaterialNumber(partIfc
												.getPartNumber());
									} else {
										mSplitIfc.setMaterialNumber(partIfc
												.getPartNumber()
												+ dashDelimiter + routeCode);
									}
									// mSplitIfc.setMaterialNumber(partIfc
									// .getPartNumber()
									// + dashDelimiter + routeCode);
									// CCEnd zhangq 20090324
								}
							}
							// û������·�ߴ��������ɢ�����ж������Ϻţ�����š�
							else {
								// CCBegin zhangq 20090324
								// ����ֵ��㲿�������Ͳ�Ϊ��Ʒʱ�����һ��·�ߴ�������ϵ����Ϻ�=�����+·�ߴ���
								if (partIfc.getPartType().toString()
										.equalsIgnoreCase("product")) {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber());
								} else {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber()
											+ dashDelimiter + routeCode);
								}

								// mSplitIfc.setMaterialNumber(partIfc
								// .getPartNumber());
								// CCEnd zhangq 20090324
							}
							mSplitIfc.setRootFlag(true);
						}
					} else {
						// ������ҵ�����Ϻţ������+��-��+·�ߴ��롣��
						if (addRouteCode) {
							// �����ͷ������һ�γ��ֲ���β�š�
							if (routeCodeMap.get(routeCode).equals("0")) {
								mSplitIfc.setMaterialNumber(partIfc
										.getPartNumber()
										+ dashDelimiter + routeCode);
							}
							// ��ͷ���ڶ��γ��ּ�β�š�-��+���ִ������ڶ��γ���Ϊ1�������γ���Ϊ2���������ơ�
							else {
								mSplitIfc.setMaterialNumber(partIfc
										.getPartNumber()
										+ dashDelimiter
										+ routeCode
										+ dashDelimiter
										+ routeCodeMap.get(routeCode));
							}
						}
						// ɢ������˾����������֣�һ���������⹤��·�ߴ��룬��һ����û������·�ߴ��롣
						else {
							// ������·�ߴ��벢�ҵ�ǰ·�ߴ����������·�ߴ��룬���Ϻţ�����š�
							if (hasSpecialRouteCode
									&& routeCode.equals(specialRouteCode)) {
								// �����ͷ������һ�γ��ֲ���β�š�
								if (routeCodeMap.get(routeCode).equals("0")) {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber());
								}
								// ��ͷ���ڶ��γ��ּ�β�š�-��+���ִ������ڶ��γ���Ϊ1�������γ���Ϊ2���������ơ�
								else {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber()
											+ dashDelimiter
											+ routeCodeMap.get(routeCode));
								}
							}
							// �������Ϻţ������+��-��+·�ߴ��롣
							else {
								// �����ͷ������һ�γ��ֲ���β�š�
								if (routeCodeMap.get(routeCode).equals("0")) {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber()
											+ dashDelimiter + routeCode);
								}
								// ��ͷ���ڶ��γ��ּ�β�š�-��+���ִ������ڶ��γ���Ϊ1�������γ���Ϊ2���������ơ�
								else {
									mSplitIfc.setMaterialNumber(partIfc
											.getPartNumber()
											+ dashDelimiter
											+ routeCode
											+ dashDelimiter
											+ routeCodeMap.get(routeCode));
								}
							}
						}
					}
					// �����ͷ�����ִ�����
					routeCodeMap.put(routeCode, String
							.valueOf(Integer.parseInt((String) routeCodeMap
									.get(routeCode)) + 1));
					// �жϸò�ֳ����������Ƿ�Ψһ�����ԭ���ϲ�ֱ��Ѵ��ڸ�����¼���ͷ������������档������һ��ѭ����������ṹʱ������Ҫ�õ��ġ�
					MaterialSplitIfc tempMSplit = getMSplit(mSplitIfc
							.getMaterialNumber());
					if (tempMSplit != null) {
						mSplitList.add(tempMSplit);
						continue;
					}
					mSplitIfc.setPartNumber(partIfc.getPartNumber());
					mSplitIfc.setPartVersion(partIfc.getVersionID());
					mSplitIfc
					.setState(partIfc.getLifeCycleState().getDisplay());
					// ������ת��������Ƿ�ת��Ϊ���ϣ�0��δת����1����ת����
					mSplitIfc.setSplited(true);
					// ���ò㼶״̬�������Ƿ񾭹���֣�0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
					if (m == 0) {
						if (usageLinkList != null && usageLinkList.size() > 0) {
							mSplitIfc.setStatus(2);
						} else {
							mSplitIfc.setStatus(0);
						}
					} else {
						mSplitIfc.setStatus(1);
					}
					// ����·�ߴ��룬��������йص����ϲ�ֺ��·�ߴ��롣
					mSplitIfc.setRouteCode(routeCode);
					// ����·�ߣ��������Ա���һ���������������·��,��·��ʱ�÷ֺš�;���ָ���
					mSplitIfc.setRoute(routeAsString.toString());
					setMaterialSplit(partIfc, mSplitIfc);
					mSplitIfc = (MaterialSplitIfc) pservice.saveValueInfo(mSplitIfc);
					logger.debug("mSplitIfc==" + mSplitIfc);
					mSplitList.add(mSplitIfc);
				}
				logger.debug("mSplitList==" + mSplitList);
				// ����ṹʱ���Ѿ����˵��˾ɵİ汾�����迼�ǰ汾�Ƚϵ����⣬ֱ�Ӵ����²�ֵ������Ϻ�������Ҫ�ҽӵ��㲿�����С�
				for (int p = 0; p < mSplitList.size(); p++) {
					MaterialSplitIfc parentMSIfc = (MaterialSplitIfc) mSplitList
					.get(p);
					logger.debug("parentMSIfc==" + parentMSIfc);
					logger.debug("parentMSIfc.getStatus=="
							+ parentMSIfc.getStatus());
					MaterialSplitIfc childMSfc = null;
					if (p != mSplitList.size() - 1) {
						childMSfc = (MaterialSplitIfc) mSplitList.get(p + 1);
					}
					logger.debug("childMSfc==" + childMSfc);
					if (parentMSIfc != null) {
						MaterialStructureIfc mStructureIfc = new MaterialStructureInfo();
						// 1-���¼����ϡ�
						if (childMSfc != null && parentMSIfc.getStatus() == 1) {
							if (!hasSplitedStructure(parentMSIfc, childMSfc)) {
								logger.debug("1-���¼����ϡ�");
								mStructureIfc.setParentPartNumber(parentMSIfc
										.getPartNumber());
								mStructureIfc.setParentPartVersion(parentMSIfc
										.getPartVersion());
								mStructureIfc.setParentNumber(parentMSIfc
										.getMaterialNumber());
								mStructureIfc.setChildNumber(childMSfc
										.getMaterialNumber());
								mStructureIfc.setQuantity(1);
								// xiebӦ������Ϊint�͡�
								mStructureIfc.setLevelNumber(String.valueOf(p));
								mStructureIfc.setDefaultUnit(childMSfc
										.getDefaultUnit());
								// ������ѡװ�����롣
								// materialStructureIfc.setOptionFlag(childMSfc.getOptionCode());
								mStructureIfc = (MaterialStructureIfc) pservice.saveValueInfo(mStructureIfc);
							}
						}
						// 2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
						else if (parentMSIfc.getStatus() == 2) {
							logger.debug("2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���");
							// ȥ��ԭ�����д����ӽṹ���߼���
						}
						// 0-��ײ����ϡ�
						else if (parentMSIfc.getStatus() == 0) {
							logger.debug("0-��ײ����ϡ�");
						}
					} // if(parentMSIfc != null)
				} // for (int p = 0; p < mSplitList.size(); p++)
			} // ·�߲�Ϊ�գ���Ҫ����㲿����
			// �������ʱ������ɰ汾������Ϊ���Ƽ�ʹ��ʱ���������Ƽ��Ľṹ��Ϣʱ����Ҫɾ���ýṹ��Ϣ������ᵼ���Ҳ���ҵ�������쳣��
			for (int j = 0; j < oldMSplitList.size(); j++) {
				MaterialSplitIfc oldMSplitIfc = (MaterialSplitIfc) oldMSplitList
				.get(j);
				List mStructureList = getMStructureByChild(oldMSplitIfc
						.getMaterialNumber());
				for (int k = 0; k < mStructureList.size(); k++) {
					MaterialStructureIfc mStructureIfc = (MaterialStructureIfc) mStructureList
					.get(k);
					if (mStructureIfc.getParentPartNumber() != partIfc
							.getPartNumber()) {
						pservice.deleteValueInfo(mStructureIfc);
					}
				}
			}
		} // for (int i = 0; i < filterPartList.size(); i++)
		if (logger.isDebugEnabled()) {
			logger.debug("split(String, boolean) - end"); //$NON-NLS-1$
		}
	}

	// 20080103 end

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
		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
		List mSplitList = getAllMSplit(partIfc.getPartNumber());
		if (mSplitList != null && mSplitList.size() > 0) {
			MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) mSplitList.get(0);
			if (mSplitIfc.getPartVersion() != null
					&& partIfc.getVersionID().compareTo(
							mSplitIfc.getPartVersion()) > 0) {
				Iterator iter = mSplitList.iterator();
				while (iter.hasNext()) {
					mSplitIfc = (MaterialSplitIfc) iter.next();
					mSplitIfc.setPartVersion(partIfc.getVersionID());
					mSplitIfc = (MaterialSplitIfc) pservice.saveValueInfo(mSplitIfc);
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
		final QMQuery query = new QMQuery("MaterialSplit");
		//		CCBegin SS5
		QueryCondition condition = new QueryCondition("partNumber",
				QueryCondition.EQUAL, partNumber);
		//CCBegin SS5
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
	 *  added by dikefeng 20100419
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
		QMQuery query = new QMQuery("MaterialStructure");
//		CCBegin SS5
		QueryCondition condition1 = new QueryCondition("parentPartNumber", "=",
				partNumber);
//		CCEnd SS5
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
	 * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
	 * 
	 * @param partIfc
	 *            �㲿����
	 * @throws QMException
	 *             return PartUsageLinkIfc�Ķ��󼯺ϡ�
	 */
	private final List getUsageLinks(final QMPartIfc partIfc)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUsageLinks(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("������partIfc==" + partIfc);
		}
		// ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
		List usesPartList = new ArrayList();
		try {
			StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			usesPartList = (List)spservice.getUsesPartMasters(partIfc);
		} catch (QMException e) {
			Object[] aobj = new Object[] { partIfc.getPartNumber() };
			// "��ȡ��Ϊ*���㲿���ṹʱ����"
			logger.error(Messages.getString("Util.17", aobj) + e);
			throw new ERPException(e, RESOURCE, "Util.17", aobj);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getUsageLinks(QMPartIfc) - end"); //$NON-NLS-1$
		}
		return usesPartList;
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
		mSplitIfc.setDefaultUnit(partIfc.getDefaultUnit().getDisplay());
		mSplitIfc.setProducedBy(partIfc.getProducedBy().getDisplay());
		mSplitIfc.setPartType(partIfc.getPartType().getDisplay());
		mSplitIfc.setPartModifyTime(partIfc.getModifyTime());
		mSplitIfc.setOptionCode(partIfc.getOptionCode());
//		�޸�����ط�������ɫ����ʶ����Ϣ dikef
//		CCBegin by dikefeng 20100416,�ൺ��������ɫ����ʶ���㲿����IBA�����У�������Ϊpart_colorflag��������ͨ����ȡ�㲿��IBA���Բ���
//		���������õ���������Ϊ���ϵ���ɫ����ʶ
		PersistService pSerivce=(PersistService)EJBServiceHelper.getPersistService();
		QMQuery query = new QMQuery("StringValue");
	    int j = query.appendBso("StringDefinition", false);
	      QueryCondition qc = new QueryCondition("iBAHolderBsoID", "=",partIfc.getBsoID());
	      query.addCondition(qc);
	      query.addAND();
	      QueryCondition qc1 = new QueryCondition("definitionBsoID", "bsoID");
	      query.addCondition(0, j, qc1);
	      query.addAND();
	      QueryCondition qc2 = new QueryCondition("name", " = ", "part_colorflag");
	      query.addCondition(j, qc2);
	      Collection colorCollection = pSerivce.findValueInfo(query, false);
	      if(colorCollection==null||colorCollection.size()==0)
	    	  mSplitIfc.setColorFlag(false);
	      else{
	    	  StringValueIfc colorValue=(StringValueIfc)colorCollection.iterator().next();
	    	  //CCBegin by chudaming 20100608  ��ɫ������·�߲����Ϻ����Ӽ�Ҳ����ע����ɫ��
	    	  if(colorValue.getValue().equalsIgnoreCase("true")&&mSplitIfc.getRootFlag())
	    		  //CCEnd by chudaming 20100608
	    		  mSplitIfc.setColorFlag(true);
	    	  else
	    		  mSplitIfc.setColorFlag(false);
	      }
//		mSplitIfc.setColorFlag(partIfc.getColorFlag());
//		CCEnd by dikefeng 20100416
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
	 * �����㲿��Ψһ��ʶ��������㲿����Ψһ��ʶΪ�����+��汾��+��������״̬+·�ߴ���
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
		if(logger.isDebugEnabled())
		{
			logger.debug("getObjectIdentity(BaseValueIfc) - start");
			logger.debug("\u53C2\u6570\uFF1AbaseValueIfc==" + baseValueIfc);
		}
//		System.out.println("hashMaphashMaphashMap22222222222222222222222222222222222222============"+hashMap);
		String returnString = "";
		if(baseValueIfc instanceof QMPartIfc)
		{
			QMPartIfc part1 = (QMPartIfc)baseValueIfc;
			 Vector routevec=null;
	    	 if(hashMap.get(part1.getBsoID())==null){
	    		 routevec=RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);
	    	 }
			else{
//					System.out.println("hashMaphashMaphashMap3333333333333333333333============"+hashMap);
				routevec=RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, (ListRoutePartLinkInfo)hashMap.get(part1.getBsoID()));
			}
//			Vector routevec = RequestHelper.getRouteBranchs((QMPartIfc)baseValueIfc, null);

			
			String routeAsString = "";
			String routeAllCode = "";
			String routeAssemStr = "";
			if(routevec.size() != 0)
			{
				routeAsString = (String)routevec.elementAt(0);
				routeAllCode = (String)routevec.elementAt(1);
			}
			QMPartIfc part = (QMPartIfc)baseValueIfc;
			returnString = part.getPartNumber() + part.getVersionID() + part.getLifeCycleState().getDisplay() + routeAllCode;
//			System.out.println("returnStringreturnString100304========"+returnString);
		} else
			if(baseValueIfc instanceof MaterialSplitIfc)
			{
				MaterialSplitIfc materialSplitIfc = (MaterialSplitIfc)baseValueIfc;
				returnString = materialSplitIfc.getPartNumber() + materialSplitIfc.getPartVersion() + materialSplitIfc.getState() + materialSplitIfc.getRoute();
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
	  if (logger.isDebugEnabled()) 
	  {
	   logger.debug("filterBySplitRule(List) - start"); //$NON-NLS-1$
	   logger.debug("������filterPartList==" + filterPartList);
	  }
	  PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
	  // �����ֵ��㲿�����ϡ�
	  final List removePartList = new ArrayList();
//	  System.out.println("100304++++++++filterPartList"+filterPartList.size()+"kkkkkkkkkkjjjjjjjjjjjj==="+filterPartList);
	  for (int i = 0; i < filterPartList.size(); i++) 
	  {
//		  System.out.println("daodijiciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa===================================================");
	   MaterialSplitIfc materialSplitIfc = null;
	   final QMPartIfc partIfc = (QMPartIfc) filterPartList.get(i);
//	   System.out.println("cacacalalalalalala==="+partIfc.getPartNumber());
	   List mSplitList = new ArrayList();
	   try {
	    // ��������Ż�ȡ�Ѳ�ֵ����ϡ�
	    mSplitList = getSplitedMSplit(partIfc.getPartNumber());
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
	   // �Ƿ���Ҫ��ִ������1.�Ƚ��µ���Ҫ��ֵ��㲿�������ϲ�ֱ���Ķ�Ӧ�����ϵİ汾�������Ҫ��ֵ��㲿���İ汾�������ϲ�ֱ���ļ�¼�����ٲ�֡�
	   MaterialSplitIfc tempMaterialSplit = null;
	   if (materialSplitIfcs != null && materialSplitIfcs.length > 0) 
	   {
		   
	    if (logger.isDebugEnabled()) {
	     logger
	     .debug("---------�Ƿ���Ҫ��ִ������1.�Ƚ��µ���Ҫ��ֵ��㲿�������ϲ�ֱ���Ķ�Ӧ�����ϵİ汾�������Ҫ��ֵ��㲿���İ汾�������ϲ�ֱ���ļ�¼�����ٲ�֡�");
	    }
	    System.out.println("100304++++++++materialSplitIfcs.lengthmaterialSplitIfcs.length"+materialSplitIfcs.length);
	    for (int j = 0; j < materialSplitIfcs.length; j++) 
	    {
	     materialSplitIfc = (MaterialSplitIfc) materialSplitIfcs[j];
	     if (materialSplitIfc.getPartVersion() != null) 
	     {
	    	 System.out.println("100304++++++++partIfc.getVersionID()-------------"+partIfc.getVersionID());
	    	 System.out.println("100304++++++++ materialSplitIfc.getPartVersion()-------------"+ materialSplitIfc.getPartVersion());
	      if (partIfc.getVersionID().compareTo(
	        materialSplitIfc.getPartVersion()) < 0) 
	      {
	    	  
	       removePartList.add(partIfc);
	       System.out.println("100304++++++++removePartListremovePartList-------------"+removePartList);
	       break;
	      } else 
	      {
	       if (tempMaterialSplit == null) 
	       {
	        tempMaterialSplit = materialSplitIfc;
//	        System.out.println("100304++++++++11111111111111-------------"+materialSplitIfcs.length+"ccccccccc"+materialSplitIfc);
	       } else if (tempMaterialSplit.getPartVersion()
	         .compareTo(
	           materialSplitIfc.getPartVersion()) < 0) 
	       {
//	    	   System.out.println("100304++++++++22222222222222-------------"+materialSplitIfcs.length+"ddddddddddd"+materialSplitIfc);
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
	     System.out.println("100304++++++++partIfc.getLifeCycleState().getDisplay()-------------"+partIfc.getLifeCycleState().getDisplay());
	     System.out.println("100304++++++++tempMaterialSplit.getState()-------------"+tempMaterialSplit.getState());
	     // �Ƿ���Ҫ��ִ������3.b.2��״̬û�б仯�������˼�¼��
	     if (partIfc.getLifeCycleState().getDisplay().equals(
	       tempMaterialSplit.getState())) 
	     {
	    	 Vector routevec=null;
//	    	 System.out.println("ssssssssssssssssssssssssssssssss00000000000======="+hashmap.get(partIfc.getBsoID()));
//	    	 System.out.println("ssssssssssssssssssssssssssssssss======="+(ListRoutePartLinkInfo)hashmap.get(partIfc.getBsoID()));
	    	 
	    	 if(hashmap.get(partIfc.getBsoID())==null){
	    		 routevec=RequestHelper.getRouteBranchs(partIfc, null);
	    	 }
				else{
					routevec=RequestHelper.getRouteBranchs(partIfc, (ListRoutePartLinkInfo)hashmap.get(partIfc.getBsoID()));
				}
//	      Vector routevec = RequestHelper.getRouteBranchs(partIfc, null);
//	      System.out.println("081000000000000000000000==="+routevec);
	      
	      String routeAsString = "";
	      String routeAllCode = "";
	      String routeAssemStr = "";
	      //CCBegin SS3
	      String cgbs="";
	      if(routevec.size() != 0)
	      {
	       routeAsString = (String)routevec.elementAt(0);
	       routeAllCode = (String)routevec.elementAt(1);
	       cgbs=(String)routevec.elementAt(7);
	       //CCEnd SS2
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
//	    	  System.out.println("100304++++++++flag0000000000000000000000000000-------------");
	       removePartList.add(partIfc);
	      }
			System.out.println("1034=============="+cgbs);

	      if (cgbs != null&&cgbs != "") {
				if (cgbs.equals("X")) {
					routeAsString = "ë��-" + routeAsString;
					routeAllCode = "ë��-" + routeAllCode;
				} else if (cgbs.equals("X-1")) {
					routeAsString = "���Ʒ-" + routeAsString;
					routeAllCode = "���Ʒ-" + routeAllCode;
  
				}
			}
	      if(!flag1 && !flag2)
	      {
	    	  System.out.println("100304++++++++routeAllCode-------------"+routeAllCode+ "   tempMaterialSplit.getRoute()"+tempMaterialSplit.getRoute()+"ddddddddddddd"+materialSplitIfc);
	       if(partIfc.getLifeCycleState().getDisplay().equals(tempMaterialSplit.getState()) && routeAllCode.equals(tempMaterialSplit.getRoute()))
	       {
	    	   System.out.println("1");
	        removePartList.add(partIfc);
//	        System.out.println("100304++++++++flag222222222222222222222222222222222222-------------"+removePartList);
	       } else
	       {
	    	   System.out.println("2");
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
	         if(mSplitIfc.getState().equals(tempMaterialSplit.getState()) && mSplitIfc.getRoute().equals(routeAllCode))
	        	 //CCEnd by chudaming 20090304
	         {
	        	 System.out.println("3");
//	        	 System.out.println("100304++++++++flag444444444444444444444-------------");
		          mSplitIfc.setState(partIfc.getLifeCycleState().getDisplay());
		          setMaterialSplit(partIfc, mSplitIfc);
		          mSplitIfc = (MaterialSplitIfc)pservice.saveValueInfo(mSplitIfc);
		          removePartList.add(partIfc);
//		          System.out.println("100304++++++++flag5555555555555-------------"+removePartList);
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
	 
		/**
		 * ��������Ż�ȡ���ϡ�Ϊ�ݿͻ���ʾʹ�á�
		 * 
		 * @param partNumberList
		 *            ����ż��ϡ�
		 * @return ����Map������һ�����Ϲ�����Ϣ���飻ֵ��������Ϣ���鼯�ϡ�
		 * @throws QMException
		 */
		public HashMap getAllMaterial(List partNumberList) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllMaterial(List) - start" + partNumberList); //$NON-NLS-1$
				logger.debug("������partNumberList==" + partNumberList);
			}
			// ����Map������һ�����Ϲ�����Ϣ���飻ֵ��������Ϣ���鼯�ϡ�
			HashMap materialMap = new HashMap();
			for (int i = 0; i < partNumberList.size(); i++) {
				// ��ȡ���㲿���ֽ������ϼ��ϡ�
				List resultList = getResultMSplitList((String) partNumberList
						.get(i));
				// ������Ϣ���鼯�ϣ��ü����е�ÿ��Ԫ�ض������顣
				List mSplitList = new ArrayList();
				// ���һ�����Ϲ�����Ϣ�����顣����Ϊ���������ת����ʶ������š������汾�š�����·�ߡ���������״̬������ż��ϵ��ַ���ʽ��
				String[] commonField = null;
				HashMap partNumberMap = new HashMap();
				for (int j = 0; j < resultList.size(); j++) {
					// ���ϡ�����ʾ�õ����ϲ㼶�������Ϻš�
					List mSplitAndLevelList = (List) resultList.get(j);
					MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) mSplitAndLevelList
					.get(0);
					if (mSplitIfc != null) {
						partNumberMap.put(mSplitIfc.getPartNumber(), mSplitIfc
								.getPartNumber());
						if (commonField == null) {
							commonField = new String[] { mSplitIfc.getPartName(),
									String.valueOf(mSplitIfc.getSplited()),
									mSplitIfc.getPartNumber(),
									mSplitIfc.getPartVersion(),
									mSplitIfc.getRoute(), mSplitIfc.getState(), "" };
						}
						if (j == resultList.size() - 1) {
							String partNumberString = "";
							Iterator iter = partNumberMap.values().iterator();
							while (iter.hasNext()) {
								if (partNumberString.equals("")) {
									partNumberString = partNumberString
									+ iter.next();
								} else {
									partNumberString = partNumberString
									+ semicolonDelimiter + iter.next();
								}
							}
							commonField[6] = partNumberString;
						}
						String parentNumber = null;
						if (mSplitAndLevelList.size() == 3) {
							parentNumber = (String) mSplitAndLevelList.get(2);
							// ������Ϣ������Ϊ������BsoID����ʾ�㼶�����Ϻš��㼶״̬������·�ߴ��롢�����Ϻš�
						}
						String[] mStrings = new String[] { mSplitIfc.getBsoID(),
								(String) mSplitAndLevelList.get(1),
								mSplitIfc.getMaterialNumber(),
								String.valueOf(mSplitIfc.getStatus()),
								mSplitIfc.getRouteCode(), parentNumber };
						mSplitList.add(mStrings);
					}
				}
				if (commonField != null) {
					materialMap.put(commonField, mSplitList);
					for (int m = 0; m < commonField.length; m++) {
						logger.debug(commonField[m]);
					}
				}
				for (int j = 0; j < mSplitList.size(); j++) {
					String[] m = (String[]) mSplitList.get(j);
					for (int n = 0; n < m.length; n++) {
						logger.debug(m[n]);
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getAllMaterial(List) - end        " + materialMap); //$NON-NLS-1$
			}
			return materialMap;
		}

		/**
		 * ��ȡ������ֽ������ϼ��ϡ�
		 * 
		 * @param String
		 *            ����š�
		 * @return List ���㲿�������ϼ��ϡ�
		 * @throws QMException
		 */
		private final List getResultMSplitList(String partNumber)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getResultMSplitList(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
			// ���Ͻ�����ϡ�
			List resultMSplitList = new ArrayList();
			// ���ж������ϼ��ϡ�
			List rootMSplitList = getRootMSplit(partNumber);
			for (int i = 0; i < rootMSplitList.size(); i++) {
				MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) rootMSplitList
				.get(i);
				List mSplitAndLevelList = new ArrayList();
				mSplitAndLevelList.add(mSplitIfc);
				mSplitAndLevelList.add("0");
				resultMSplitList.add(mSplitAndLevelList);
				// �õ����ϵ������ϡ�ֻ�ڲ㼶״̬Ϊ1������²�ִ�С�
				if (mSplitIfc.getStatus() == 1) {
					getSubResultMSplitList(mSplitIfc, resultMSplitList, 0);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getResultMSplitList(String) - end      " + resultMSplitList); //$NON-NLS-1$
			}
			return resultMSplitList;
		}

		/**
		 * ��������Ż�ȡ�������ϡ�
		 * 
		 * @param partNumber
		 *            ����š�
		 * @return �������ϼ��ϡ�
		 * @throws QMException
		 */
		public List getRootMSplit(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getRootMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query2 = new QMQuery("MaterialSplit");
			QueryCondition condition2 = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query2.addCondition(condition2);
			query2.addAND();
			QueryCondition condition3 = new QueryCondition("rootFlag", true);
			query2.addCondition(condition3);
			List returnList=(List)pservice.findValueInfo(query2);
			if (logger.isDebugEnabled()) {
				logger.debug("getRootMSplit(String) - end       " + returnList); //$NON-NLS-1$
			}
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

		/**
		 * ��������Ż�ȡ��ת�������ϡ�
		 * 
		 * @param partNumber
		 *            ����š�
		 * @return ���ϼ��ϡ�
		 * @throws QMException
		 */
//		private List getSplitedMSplit(String partNumber) throws QMException {
//			if (logger.isDebugEnabled()) {
//				logger.debug("getSplitedMSplit(String) - start"); //$NON-NLS-1$
//				logger.debug("������partNumber==" + partNumber);
//			}
//			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
////			int aa=1;
//			final QMQuery query = new QMQuery("MaterialSplit");
//			QueryCondition condition = new QueryCondition("partNumber",
//					QueryCondition.EQUAL, partNumber);
//			query.addCondition(condition);
//			//CCBegin by chudaming 20100610 dele �·����������γ�XML�ļ����㲿�����мȴ��ڡ�N����ʶ��Ҳ���ڡ�U����ʶ���ṹ���мȴ��ڡ�N����ʶ��Ҳ���ڡ�O����ʶ
//			//CCBegin by chudaming 20101216
////			query.addAND();
////			QueryCondition condition1 = new QueryCondition("rootFlag",
////					true);
////			query.addCondition(condition1);
//			//CCEnd by chudaming 20101216
////			//??splited�о�·��Ϊ����Ϊfalse
////			QueryCondition condition4 = new QueryCondition("splited", true);
////			query.addCondition(condition4);
//			//CCEnd by chudaming 20100610 dele
//			List mSplitList=(List)pservice.findValueInfo(query);
//			if (logger.isDebugEnabled()) {
//				logger.debug("getSplitedMSplit(String) - end"); //$NON-NLS-1$
//			}
////			System.out.println("wwwwwwwwwwwwwwwwsssssssssssssssssssssssssssssssssmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy================="+mSplitList);
//			return mSplitList;
//		}
		private List getSplitedMSplit(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getSplitedMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
//			int aa=1;
			final QMQuery query = new QMQuery("MaterialSplit");
			QueryCondition condition = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query.addCondition(condition);
			//CCBegin by chudaming 20100610 dele �·����������γ�XML�ļ����㲿�����мȴ��ڡ�N����ʶ��Ҳ���ڡ�U����ʶ���ṹ���мȴ��ڡ�N����ʶ��Ҳ���ڡ�O����ʶ
			//CCBegin by chudaming 20101216
			query.addAND();
			QueryCondition condition1 = new QueryCondition("rootFlag",
					true);
			query.addCondition(condition1);
			//CCEnd by chudaming 20101216
//			//??splited�о�·��Ϊ����Ϊfalse
//			QueryCondition condition4 = new QueryCondition("splited", true);
//			query.addCondition(condition4);
			//CCEnd by chudaming 20100610 dele
			List mSplitList=(List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getSplitedMSplit(String) - end"); //$NON-NLS-1$
			}
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
			final QMQuery query = new QMQuery("MaterialSplit");
			QueryCondition condition = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query.addCondition(condition);
			List mSplitList=(List)pservice.findValueInfo(query);
			//System.out.println("mSplitListmSplitListmSplitListmSplitList======="+mSplitList);
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
			final QMQuery query = new QMQuery("MaterialSplit");
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
			QMQuery query = new QMQuery("MaterialStructure");
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
			final QMQuery query = new QMQuery("MaterialStructure");
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
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("MaterialStructure");
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
			final QMQuery query = new QMQuery("MaterialStructure");
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
		 * ��ʽִ�и��¡����µ�ǰ�û�����ʱ���е���Ϣ����ɸ��º������ʱ�������ڸ��û������м�¼��
		 * 
		 * @param updateMap
		 *            ������ʱ��bsoID��ֵ���޸ĵ����Ϻš�
		 * @throws QMException
		 */
		public void updateMaterial(HashMap updateMap) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("updateMaterial(HashMap) - start"); //$NON-NLS-1$
				logger.debug("������updateMap==" + updateMap);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			Object[] bsoIDs = updateMap.keySet().toArray();
			for (int i = 0; i < bsoIDs.length; i++) {
				String bsoID = (String) bsoIDs[i];
				// ��ʱ���ϡ�
				InterimMaterialSplitIfc imSplitIfc = (InterimMaterialSplitIfc)pservice.refreshInfo(bsoID);
				// ԭ���Ϻš�
				String oldMaterialNumber = imSplitIfc.getMaterialNumber();
				// �����޸ĵ����Ϻš�
				imSplitIfc.setMaterialNumber((String) updateMap.get(bsoID));
				imSplitIfc.setUpdateFlag("U");
				pservice.saveValueInfo(imSplitIfc);
				List imStructureList = getInterimMStructure(imSplitIfc
						.getPartNumber(), false);
				for (int m = 0; m < imStructureList.size(); m++) {
					InterimMaterialStructureIfc imStructureIfc = (InterimMaterialStructureIfc) imStructureList
					.get(m);
					if (imStructureIfc.getParentNumber().equals(oldMaterialNumber)) {
						imStructureIfc.setParentNumber(imSplitIfc
								.getMaterialNumber());
						imStructureIfc.setUpdateFlag("U");
						pservice.saveValueInfo(imStructureIfc);
					}
					if (imStructureIfc.getChildNumber().equals(oldMaterialNumber)) {
						imStructureIfc.setChildNumber(imSplitIfc
								.getMaterialNumber());
						imStructureIfc.setUpdateFlag("U");
						pservice.saveBso(imStructureIfc);
					}
				}
			}
			List imSplitList = getAllInterimMSplit();
			for (int i = 0; i < imSplitList.size(); i++) {
				InterimMaterialSplitIfc imSplitIfc = (InterimMaterialSplitIfc) imSplitList
				.get(i);
				if (imSplitIfc.getUpdateFlag() != null
						&& imSplitIfc.getUpdateFlag().equals("U")) {
					MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) pservice.refreshInfo(imSplitIfc.getSourceBsoID());
					mSplitIfc.setMaterialNumber(imSplitIfc.getMaterialNumber());
					mSplitIfc.setStatus(imSplitIfc.getStatus());
					pservice.saveValueInfo(mSplitIfc);
				}
			}
			List imStructureList = getAllInterimMStructure();
			for (int i = 0; i < imStructureList.size(); i++) {
				InterimMaterialStructureIfc imStructureIfc = (InterimMaterialStructureIfc) imStructureList
				.get(i);
				if (imStructureIfc.getUpdateFlag() != null) {
					if (imStructureIfc.getUpdateFlag().equals("D")) {
						pservice.deleteBso(imStructureIfc.getSourceBsoID());
					} else if (imStructureIfc.getUpdateFlag().equals("U")) {
						MaterialStructureIfc mStructureIfc = (MaterialStructureIfc)pservice.refreshInfo(imStructureIfc.getSourceBsoID());
						mStructureIfc.setParentNumber(imStructureIfc
								.getParentNumber());
						mStructureIfc.setChildNumber(imStructureIfc
								.getChildNumber());
						mStructureIfc.setLevelNumber(imStructureIfc
								.getLevelNumber());
						pservice.saveValueInfo(mStructureIfc);
					}
				}
			}
			deleteAllInterimData();
			if (logger.isDebugEnabled()) {
				logger.debug("updateMaterial(HashMap) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * ɾ��������ʱ���е�ǰ�û������ݡ�
		 * 
		 * @throws QMException
		 */
		public void deleteAllInterimData() throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("deleteAllInterimData() - start"); //$NON-NLS-1$
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			List imSplitList = getAllInterimMSplit();
			for (int i = 0; i < imSplitList.size(); i++) {
				InterimMaterialSplitIfc imSplitIfc = (InterimMaterialSplitIfc) imSplitList
				.get(i);
				pservice.deleteValueInfo(imSplitIfc);
			}
			List imStructureList = getAllInterimMStructure();
			for (int j = 0; j < imStructureList.size(); j++) {
				InterimMaterialStructureIfc imStructureIfc = (InterimMaterialStructureIfc) imStructureList
				.get(j);
				pservice.deleteValueInfo(imStructureIfc);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("deleteAllInterimData() - end"); //$NON-NLS-1$
			}
		}

		/**
		 * ֻɾ�ṹ��ϵ������ʱ���Ͻṹ���±�ʶ����Ϊ��D����
		 * 
		 * @param parentPartNumber
		 *            �����š�
		 * @param parentNumber
		 *            �����Ϻš�
		 * @param childBsoID
		 *            ��ɾ����ʱ�����ϵ�bsoID��
		 * @throws QMException
		 */
		public void delete(String parentPartNumber, String parentNumber,
				String childBsoID) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("delete(String, String, String) - start"); //$NON-NLS-1$
				logger.debug("������parentPartNumber==" + parentPartNumber);
				logger.debug("������parentNumber==" + parentNumber);
				logger.debug("������childBsoID==" + childBsoID);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			logger.debug("parentPartNumber==" + parentPartNumber);
			logger.debug("parentNumber==" + parentNumber);
			logger.debug("childBsoID==" + childBsoID);
			InterimMaterialSplitIfc childIMSplitIfc = (InterimMaterialSplitIfc)pservice.refreshInfo(childBsoID);
			List iMStructureList = getInterimMStructure(parentPartNumber,
					parentNumber, childIMSplitIfc.getMaterialNumber());
			for (int i = 0; i < iMStructureList.size(); i++) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) iMStructureList
				.get(i);
				logger.debug("����ʱ���Ͻṹ���±�ʶ����Ϊ��D����");
				iMStructureIfc.setUpdateFlag("D");
				pservice.saveValueInfo(iMStructureIfc);
			}
			if (iMStructureList != null && iMStructureList.size() > 0) {

				// ɾ���м��ʱ���²�㼶�����������㼶״̬���䡣
				if (childIMSplitIfc.getStatus() == 1) {
					logger.debug("ɾ���м��ʱ���²�㼶�����������㼶״̬���䡣");
					setAllIMStructure(childIMSplitIfc, parentNumber);
				}
				// ɾ����ײ�ʱ���ϲ�㼶���䣬�㼶״̬�䣺ͬ��ɾ���Ĳ㼶״̬��
				else {
					logger.debug("ɾ����ײ�ʱ���ϲ�㼶���䣬�㼶״̬�䣺ͬ��ɾ���Ĳ㼶״̬��");
					InterimMaterialSplitIfc parentIMSplitIfc = getInterimMSplit(parentNumber);
					iMStructureList = getInterimMStructure(parentPartNumber,
							parentNumber);
					// ������������ϹҽӶ���һ�������ϡ��ϲ�㼶״̬���䡣
					if (iMStructureList != null && iMStructureList.size() > 0) {
						logger.debug("�ϲ�㼶״̬���䡣ɾ���ýṹ��¼��");
						// ���������ϹҽӶ���һ�������ϡ��ϲ�㼶״̬���䡣
					} else {
						logger.debug("�ϲ�㼶״̬ͬ��ɾ���Ĳ㼶״̬��");
						// �ϲ�㼶״̬�䣺ͬ��ɾ���Ĳ㼶״̬��
						parentIMSplitIfc.setStatus(childIMSplitIfc.getStatus());
						parentIMSplitIfc.setUpdateFlag("U");
					}
					pservice.saveValueInfo(parentIMSplitIfc);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("delete(String, String, String) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * ���ø�����ʱ�����ϵĽṹ��Ϣ������ʱ���Ͻṹ���±�ʶ����Ϊ��U����
		 * 
		 * @param iMSplitIfc
		 *            ��ʱ�����ϡ�
		 * @param parentNumber
		 *            ��ʱ�����ϵĸ����Ϻš�
		 * @throws QMException
		 */
		private void setAllIMStructure(InterimMaterialSplitIfc iMSplitIfc,
				String parentNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("setAllIMStructure(InterimMaterialSplitIfc, String) - start"); //$NON-NLS-1$
				logger.debug("������iMSplitIfc==" + iMSplitIfc);
				logger.debug("������parentNumber==" + parentNumber);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			// ��ȡָ�������Ϻŵ����Ͻṹ���ϡ�
			List mStructureList = getInterimMStructure(iMSplitIfc.getPartNumber(),
					iMSplitIfc.getMaterialNumber());
			Iterator iter = mStructureList.iterator();
			while (iter.hasNext()) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) iter
				.next();
				// �㼶��
				InterimMaterialSplitIfc subIMSplitIfc = getInterimMSplit(iMStructureIfc
						.getChildNumber());
				int subLevel = Integer.valueOf(iMStructureIfc.getLevelNumber())
				.intValue();
				iMStructureIfc.setParentNumber(parentNumber);
				iMStructureIfc.setLevelNumber(String.valueOf(subLevel - 1));
				iMStructureIfc.setUpdateFlag("U");
				pservice.saveValueInfo(iMStructureIfc);
				// �������ϺŻ�ȡ���ϡ�
				if (subIMSplitIfc != null && subIMSplitIfc.getStatus() == 1) {
					// ���ø�����ʱ�����ϵĽṹ��Ϣ��
					setAllSubIMStructure(subIMSplitIfc);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("setAllIMStructure(InterimMaterialSplitIfc, String) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * ���ø�����ʱ�����ϵĽṹ��Ϣ������ʱ���Ͻṹ���±�ʶ����Ϊ��U����
		 * 
		 * @param iMSplitIfc
		 *            ��ʱ�����ϡ�
		 * @throws QMException
		 */
		private void setAllSubIMStructure(InterimMaterialSplitIfc iMSplitIfc)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("setAllSubIMStructure(InterimMaterialSplitIfc) - start"); //$NON-NLS-1$
				logger.debug("������iMSplitIfc==" + iMSplitIfc);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			// ��ȡָ�������Ϻŵ����Ͻṹ���ϡ�
			List iMStructureList = getInterimMStructure(iMSplitIfc.getPartNumber(),
					iMSplitIfc.getMaterialNumber());
			Iterator iter = iMStructureList.iterator();
			while (iter.hasNext()) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) iter
				.next();
				// �㼶��
				InterimMaterialSplitIfc subIMSplitIfc = getInterimMSplit(iMStructureIfc
						.getChildNumber());
				int subLevel = Integer.valueOf(iMStructureIfc.getLevelNumber())
				.intValue();
				iMStructureIfc.setLevelNumber(String.valueOf(subLevel - 1));
				iMStructureIfc.setUpdateFlag("U");
				pservice.saveValueInfo(iMStructureIfc);
				// �������ϺŻ�ȡ���ϡ�
				if (subIMSplitIfc != null && subIMSplitIfc.getStatus() == 1) {
					// �ݹ����ø�����ʱ�����ϵĽṹ��Ϣ��
					setAllSubIMStructure(subIMSplitIfc);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("setAllSubIMStructure(InterimMaterialSplitIfc) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * ֻ�Ľṹ��ϵ�е������Ϻš��㼶���㼶״̬�����䡣����ʱ���Ͻṹ���±�ʶ����Ϊ��U������Ҫ���滻�������ϵ���Ϣ��ӵ���ʱ���С�
		 * 
		 * @param parentPartNumber
		 *            �����š�
		 * @param parentNumber
		 *            �����Ϻš�
		 * @param childBsoID
		 *            ��ʱ��������bsoID��
		 * @param replaceBsoID
		 *            �滻���ϵ�bsoID��
		 * @throws QMException
		 */
		public void replace(String parentPartNumber, String parentNumber,
				String childBsoID, String replaceBsoID) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("replace(String, String, String, String) - start"); //$NON-NLS-1$
				logger.debug("������parentPartNumber==" + parentPartNumber);
				logger.debug("������parentNumber==" + parentNumber);
				logger.debug("������childBsoID==" + childBsoID);
				logger.debug("������replaceBsoID==" + replaceBsoID);
			}
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			InterimMaterialSplitIfc childIMSplitIfc = (InterimMaterialSplitIfc) pservice.refreshInfo(childBsoID);
			MaterialSplitIfc replaceIMSplitIfc = (MaterialSplitIfc) pservice.refreshInfo(replaceBsoID);
			List iMStructureList = getInterimMStructure(parentPartNumber,
					parentNumber, childIMSplitIfc.getMaterialNumber());
			for (int i = 0; i < iMStructureList.size(); i++) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) iMStructureList
				.get(i);
				iMStructureIfc
				.setChildNumber(replaceIMSplitIfc.getMaterialNumber());
				iMStructureIfc.setUpdateFlag("U");
				pservice.saveValueInfo(iMStructureIfc);
			}
			if (iMStructureList != null && iMStructureList.size() > 0) {
				// ���滻�������ϵ���Ϣ��ӵ���ʱ���С�
				createInterimMaterial(replaceIMSplitIfc.getPartNumber());
			}
			if (logger.isDebugEnabled()) {
				logger.debug("replace(String, String, String, String) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * ��������ż��ϵ��ַ�����ʽ������ʱ���ϼ�¼��
		 * 
		 * @param partNumberString
		 *            ����ż��ϵ��ַ�����ʽ��p01;p02
		 * @throws QMException
		 */
		public void createInterimMaterial(String partNumberString)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("createInterimMaterial(String) - start" + partNumberString); //$NON-NLS-1$
				logger.debug("������partNumberString==" + partNumberString);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			StringTokenizer st = new StringTokenizer(partNumberString,
					semicolonDelimiter);
			// ����ż��ϡ��磺p01,p02
			List partNumberList = new ArrayList();
			while (st.hasMoreTokens()) {
				partNumberList.add(st.nextToken());
			}
			logger.debug("partNumberList==" + partNumberList);
			for (int i = 0; i < partNumberList.size(); i++) {
				List imSplitList = getAllInterimMSplit((String) partNumberList
						.get(i));
				UserIfc owner=(UserIfc)sservice.getCurUserInfo();
				if (imSplitList == null || imSplitList.isEmpty()) {
					List mSplitList = getAllMSplit((String) partNumberList.get(i));
					for (Iterator ite = mSplitList.iterator(); ite.hasNext();) {
						Object obj = ite.next();
						MaterialSplitIfc mSplitIfc = (MaterialSplitIfc) obj;
						InterimMaterialSplitIfc imSplitIfc = new InterimMaterialSplitInfo();
						imSplitIfc.setColorFlag(mSplitIfc.getColorFlag());
						imSplitIfc.setDefaultUnit(mSplitIfc.getDefaultUnit());
						imSplitIfc.setMaterialNumber(mSplitIfc.getMaterialNumber());
						imSplitIfc.setOptionCode(mSplitIfc.getOptionCode());
						imSplitIfc.setPartModifyTime(mSplitIfc.getPartModifyTime());
						imSplitIfc.setPartName(mSplitIfc.getPartName());
						imSplitIfc.setPartNumber(mSplitIfc.getPartNumber());
						imSplitIfc.setPartType(mSplitIfc.getPartType());
						imSplitIfc.setPartVersion(mSplitIfc.getPartVersion());
						imSplitIfc.setProducedBy(mSplitIfc.getProducedBy());
						imSplitIfc.setRoute(mSplitIfc.getRoute());
						imSplitIfc.setRouteCode(mSplitIfc.getRouteCode());
						imSplitIfc.setSplited(mSplitIfc.getSplited());
						imSplitIfc.setState(mSplitIfc.getState());
						imSplitIfc.setStatus(mSplitIfc.getStatus());
						imSplitIfc.setSourceBsoID(mSplitIfc.getBsoID());
						imSplitIfc.setOwner(owner.getBsoID());
						imSplitIfc.setRootFlag(mSplitIfc.getRootFlag());
						pservice.saveValueInfo(imSplitIfc);
					}
				}
				List imStructureList = getInterimMStructure((String) partNumberList
						.get(i), true);
				if (imStructureList == null || imStructureList.isEmpty()) {
					List mStructureList = getMStructure((String) partNumberList
							.get(i));
					for (Iterator iter = mStructureList.iterator(); iter.hasNext();) {
						MaterialStructureIfc mStructureIfc = (MaterialStructureIfc) iter
						.next();
						InterimMaterialStructureIfc imStructureIfc = new InterimMaterialStructureInfo();
						imStructureIfc.setChildNumber(mStructureIfc
								.getChildNumber());
						imStructureIfc.setDefaultUnit(mStructureIfc
								.getDefaultUnit());
						imStructureIfc.setLevelNumber(mStructureIfc
								.getLevelNumber());
						imStructureIfc.setOptionFlag(mStructureIfc.getOptionFlag());
						imStructureIfc.setParentNumber(mStructureIfc
								.getParentNumber());
						imStructureIfc.setParentPartNumber(mStructureIfc
								.getParentPartNumber());
						imStructureIfc.setParentPartVersion(mStructureIfc
								.getParentPartVersion());
						imStructureIfc.setQuantity(mStructureIfc.getQuantity());
						imStructureIfc.setSourceBsoID(mStructureIfc.getBsoID());
						imStructureIfc.setOwner(owner.getBsoID());
						pservice.saveValueInfo(imStructureIfc);
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("createInterimMaterial(String) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * ��������Ż�ȡ��ʱ���ϡ�Ϊ�ݿͻ���������ʱʹ�á�
		 * 
		 * @param partNumber
		 *            ����š�
		 * @return ��ʱ����Map������һ����ʱ���Ϲ�����Ϣ���飻ֵ����ʱ������Ϣ���鼯�ϡ�
		 * @throws QMException
		 */
		public HashMap getAllInterimMaterial(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllInterimMaterial(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
			// ����Map������һ�����Ϲ�����Ϣ���飻ֵ��������Ϣ���鼯�ϡ�
			HashMap iMaterialMap = new HashMap();
			// ��ȡ���㲿���ֽ������ϼ��ϡ�
			List resultList = getResultIMSplitList(partNumber);
			// ������Ϣ���鼯�ϣ��ü����е�ÿ��Ԫ�ض������顣
			List imSplitList = new ArrayList();
			// ���һ�����Ϲ�����Ϣ�����顣����Ϊ���������ת����ʶ������š������汾�š�����·�ߡ���������״̬��
			String[] commonField = null;
			for (int j = 0; j < resultList.size(); j++) {
				// ��¼���ϡ�����ʾ�õ����ϲ㼶�����ϵĸ����Ϻš�
				List imSplitAndLevelList = (List) resultList.get(j);
				InterimMaterialSplitIfc imSplitIfc = (InterimMaterialSplitIfc) imSplitAndLevelList
				.get(0);
				if (imSplitIfc != null) {
					if (commonField == null) {
						commonField = new String[] { imSplitIfc.getPartName(),
								String.valueOf(imSplitIfc.getSplited()),
								imSplitIfc.getPartNumber(),
								imSplitIfc.getPartVersion(), imSplitIfc.getRoute(),
								imSplitIfc.getState() };
					}
					String parentNumber = null;
					if (imSplitAndLevelList.size() == 3) {
						parentNumber = (String) imSplitAndLevelList.get(2);
					}
					logger.debug("mSplitIfc==" + imSplitIfc);
					// ������Ϣ������Ϊ������BsoID����ʾ�㼶�����Ϻš��㼶״̬������·�ߴ��롢�����Ϻš�
					String[] mStrings = new String[] { imSplitIfc.getBsoID(),
							(String) imSplitAndLevelList.get(1),
							imSplitIfc.getMaterialNumber(),
							String.valueOf(imSplitIfc.getStatus()),
							imSplitIfc.getRouteCode(), parentNumber };
					imSplitList.add(mStrings);
				}
			}
			if (commonField != null) {
				iMaterialMap.put(commonField, imSplitList);
				for (int m = 0; m < commonField.length; m++) {
					logger.debug(commonField[m]);
				}
			}
			for (int j = 0; j < imSplitList.size(); j++) {
				String[] m = (String[]) imSplitList.get(j);
				for (int n = 0; n < m.length; n++) {
					logger.debug(m[n]);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getAllInterimMaterial(String) - end       " + iMaterialMap); //$NON-NLS-1$
			}
			return iMaterialMap;
		}

		/**
		 * ��ȡ�������ֺ����ʱ���ϼ��ϡ�
		 * 
		 * @param String
		 *            ����š�
		 * @return List ���㲿������ʱ���ϼ��ϡ�
		 * @throws QMException
		 */
		private final List getResultIMSplitList(String partNumber)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getResultIMSplitList(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
			// ���Ͻ�����ϡ�
			List resultIMSplitList = new ArrayList();
			// ���ж������ϼ��ϡ�
			List rootIMSplitList = getRootInterimMSplit(partNumber);
			for (int i = 0; i < rootIMSplitList.size(); i++) {
				InterimMaterialSplitIfc imSplitIfc = (InterimMaterialSplitIfc) rootIMSplitList
				.get(i);
				// ��¼���ϡ�����ʾ�õ����ϲ㼶�����ϵĸ����Ϻš�
				List imSplitAndLevelList = new ArrayList();
				imSplitAndLevelList.add(imSplitIfc);
				imSplitAndLevelList.add("0");
				resultIMSplitList.add(imSplitAndLevelList);
				// �õ����ϵ������ϡ�ֻ�ڲ㼶״̬Ϊ1������²�ִ�С�
				if (imSplitIfc.getStatus() == 1) {
					getSubResultIMSplitList(imSplitIfc, resultIMSplitList, 0);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getResultIMSplitList(String) - end        "
						+ resultIMSplitList); //$NON-NLS-1$
			}
			return resultIMSplitList;
		}

		/**
		 * ��ȡ��ʱ�����Ͻ�����ϡ�
		 * 
		 * @param imSplitIfc
		 *            ��ʱ�����ϡ�
		 * @param resultIMSplitList
		 *            ���յĽ�����ϡ�
		 * @param level
		 *            �㼶��
		 * @throws QMException
		 */
		private void getSubResultIMSplitList(InterimMaterialSplitIfc imSplitIfc,
				List resultIMSplitList, int level) throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultIMSplitList(InterimMaterialSplitIfc, List, int) - start"); //$NON-NLS-1$
				logger.debug("������imSplitIfc==" + imSplitIfc);
				logger.debug("������resultIMSplitList==" + resultIMSplitList);
				logger.debug("������level==" + level);
			}
			// ��ȡָ�������Ϻŵ����Ͻṹ���ϡ�
			List imStructureList = getInterimMStructure(imSplitIfc.getPartNumber(),
					imSplitIfc.getMaterialNumber());
			Iterator iter = imStructureList.iterator();
			while (iter.hasNext()) {
				InterimMaterialStructureIfc imStructureIfc = (InterimMaterialStructureIfc) iter
				.next();
				// �������ϺŻ�ȡ���ϡ�
				InterimMaterialSplitIfc subIMSplitIfc = getInterimMSplit(imStructureIfc
						.getChildNumber());
				// ��¼���ϡ�����ʾ�õ����ϲ㼶�����ϵĸ����Ϻš�
				List imSplitAndLevelList = new ArrayList();
				imSplitAndLevelList.add(subIMSplitIfc);
				imSplitAndLevelList.add(String.valueOf(level + 1));
				imSplitAndLevelList.add(imStructureIfc.getParentNumber());
				resultIMSplitList.add(imSplitAndLevelList);
				if (subIMSplitIfc != null && subIMSplitIfc.getStatus() == 1) {
					// �ݹ��ȡ�����ϣ�����filterMSplitList�С�
					getSubResultIMSplitList(subIMSplitIfc, resultIMSplitList,
							level + 1);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getSubResultIMSplitList(InterimMaterialSplitIfc, List, int) - end"); //$NON-NLS-1$
			}
		}

		/**
		 * ��������Ż�ȡ��ǰ�û��Ķ������ϡ�
		 * 
		 * @param partNumber
		 *            ����š�
		 * @return �������ϼ��ϡ�
		 * @throws QMException
		 */
		private List getRootInterimMSplit(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getRootInterimMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query2 = new QMQuery("InterimMaterialSplit");
			QueryCondition condition2 = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query2.addCondition(condition2);
			query2.addAND();
			QueryCondition condition3 = new QueryCondition("rootFlag", true);
			query2.addCondition(condition3);
			query2.addAND();
			UserIfc owner = (UserIfc) sservice.getCurUserInfo();
			QueryCondition condition4 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query2.addCondition(condition4);
			List returnList = (List) pservice.findValueInfo(query2);
			if (logger.isDebugEnabled()) {
				logger
				.debug("getRootInterimMSplit(String) - end        " + returnList); //$NON-NLS-1$
			}
			return returnList;
		}

		/**
		 * �������ϺŻ�ȡ��ǰ�û�����ʱ�����ϡ�
		 * 
		 * @param materialNumber
		 *            ���Ϻš�
		 * @return ���ϡ�
		 * @throws QMException
		 */
		private InterimMaterialSplitIfc getInterimMSplit(String materialNumber)
		throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getInterimMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("������materialNumber==" + materialNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("InterimMaterialSplit");
			QueryCondition condition = new QueryCondition("materialNumber",
					QueryCondition.EQUAL, materialNumber);
			query.addCondition(condition);
			query.addAND();
			UserIfc owner = (UserIfc) sservice.getCurUserInfo();
			QueryCondition condition2 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition2);
			List imSplitList = (List) pservice.findValueInfo(query);
			InterimMaterialSplitIfc imSplitIfc = null;
			if (imSplitList != null && imSplitList.size() > 0) {
				imSplitIfc = (InterimMaterialSplitIfc) imSplitList.get(0);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getInterimMSplit(String) - end        " + imSplitIfc); //$NON-NLS-1$
			}
			return imSplitIfc;
		}

		/**
		 * �ҵ���ʱ���ϱ������е�ǰ�û���������Ϣ��
		 * 
		 * @return ���ϼ��ϡ�
		 * @throws QMException
		 */
		private List getAllInterimMSplit() throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllInterimMSplit() - start"); //$NON-NLS-1$
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			final QMQuery query = new QMQuery("InterimMaterialSplit");
			QueryCondition condition = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition);
			List imSplitList = (List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger.debug("getAllInterimMSplit() - end      " + imSplitList); //$NON-NLS-1$
			}
			return imSplitList;
		}

		/**
		 * ��������Ż�ȡ��ǰ�û�����ʱ�����ϡ�
		 * 
		 * @param partNumber
		 *            ����š�
		 * @return ��ʱ�����ϼ��ϡ�
		 * @throws QMException
		 */
		public List getAllInterimMSplit(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllInterimMSplit(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("InterimMaterialSplit");
			QueryCondition condition = new QueryCondition("partNumber",
					QueryCondition.EQUAL, partNumber);
			query.addCondition(condition);
			query.addAND();
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			QueryCondition condition2 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition2);
			List imSplitList = (List)pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger
				.debug("getAllInterimMSplit(String) - end     " + imSplitList); //$NON-NLS-1$
			}
			return imSplitList;
		}

		/**
		 * �ҵ���ʱ�ṹ�������е�ǰ�û��Ľṹ��Ϣ��������ʶΪɾ���ġ�
		 * 
		 * @return �ṹ��Ϣ��
		 * @throws QMException
		 */
		private List getAllInterimMStructure() throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getAllInterimMStructure() - start"); //$NON-NLS-1$
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			final QMQuery query = new QMQuery("InterimMaterialStructure");
			QueryCondition condition1 = new QueryCondition("owner", "=", owner
					.getBsoID());
			query.addCondition(condition1);
			List imStructureList = (List) pservice.findValueInfo(query);
			if (logger.isDebugEnabled()) {
				logger
				.debug("getAllInterimMStructure() - end     " + imStructureList); //$NON-NLS-1$
			}
			return imStructureList;
		}

		/**
		 * ��ȡָ��������ŵĵ�ǰ�û�����ʱ���Ͻṹ���ϡ�����ѡ������򲻰�����ʶΪɾ���ġ�
		 * 
		 * @param String
		 *            ������š�
		 * @param boolean
		 *            �Ƿ�������Ϊɾ���ļ�¼��true��������false����������
		 * @return List ָ��������ŵ���ʱ���Ͻṹ���ϡ�
		 * @throws QMException
		 */
		private final List getInterimMStructure(String parentPartNumber,
				boolean isAll) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getInterimMStructure(String) - start"); //$NON-NLS-1$
				logger.debug("������parentPartNumber==" + parentPartNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			QMQuery query = new QMQuery("InterimMaterialStructure");
			QueryCondition condition1 = new QueryCondition("parentPartNumber", "=",
					parentPartNumber);
			query.addCondition(condition1);
			query.addAND();
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			QueryCondition condition2 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition2);
			List imStructureList = (List) pservice.findValueInfo(query);
			List returnList = new ArrayList();
			if (isAll) {
				returnList = imStructureList;
			} else {
				for (int i = 0; i < imStructureList.size(); i++) {
					InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) imStructureList
					.get(i);
					if (iMStructureIfc.getUpdateFlag() == null
							|| !iMStructureIfc.getUpdateFlag().equals("D")) {
						returnList.add(iMStructureIfc);
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getInterimMStructure(String) - end       " + returnList); //$NON-NLS-1$
			}
			return returnList;
		}

		/**
		 * ����ָ���ĸ����ź͸����Ϻ��������ҵ���ʱ�ṹ���е�ǰ�û������з��������Ľṹ��Ϣ����������ʶΪɾ���ġ�
		 * 
		 * @param parentPartNumber
		 *            �����š�
		 * @param parentNumber
		 *            �����Ϻš�
		 * @return �ṹ��Ϣ��
		 * @throws QMException
		 */
		private List getInterimMStructure(String parentPartNumber,
				String parentNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getInterimMStructure(String, String) - start"); //$NON-NLS-1$
				logger.debug("������parentPartNumber==" + parentPartNumber);
				logger.debug("������parentNumber==" + parentNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("InterimMaterialStructure");
			// QueryCondition condition = new QueryCondition("parentPartNumber",
			// QueryCondition.EQUAL, parentPartNumber);
			// query.addCondition(condition);
			// query.addAND();
			QueryCondition condition3 = new QueryCondition("parentNumber",
					QueryCondition.EQUAL, parentNumber);
			query.addCondition(condition3);
			query.addAND();
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			QueryCondition condition2 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition2);
			List imStructureList = (List)pservice.findValueInfo(query);
			List returnList = new ArrayList();
			for (int i = 0; i < imStructureList.size(); i++) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) imStructureList
				.get(i);
				if (iMStructureIfc.getUpdateFlag() == null
						|| !iMStructureIfc.getUpdateFlag().equals("D")) {
					returnList.add(iMStructureIfc);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getInterimMStructure(String, String) - end        "
						+ returnList); //$NON-NLS-1$
			}
			return returnList;
		}

		/**
		 * ����ָ���ĸ����ź͸����Ϻź������Ϻ��������ҵ���ʱ�ṹ���е�ǰ�û������з��������Ľṹ��Ϣ����������ʶΪɾ���ġ�
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
		private List getInterimMStructure(String parentPartNumber,
				String parentNumber, String childNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger
				.debug("getInterimMStructure(String, String, String) - start"); //$NON-NLS-1$
				logger.debug("������parentPartNumber==" + parentPartNumber);
				logger.debug("������parentNumber==" + parentNumber);
				logger.debug("������childNumber==" + childNumber);
			}
			SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			final QMQuery query = new QMQuery("InterimMaterialStructure");
			// QueryCondition condition = new QueryCondition("parentPartNumber",
			// QueryCondition.EQUAL, parentPartNumber);
			// query.addCondition(condition);
			// query.addAND();
			QueryCondition condition2 = new QueryCondition("parentNumber",
					QueryCondition.EQUAL, parentNumber);
			query.addCondition(condition2);
			query.addAND();
			QueryCondition condition3 = new QueryCondition("childNumber",
					QueryCondition.EQUAL, childNumber);
			query.addCondition(condition3);
			query.addAND();
			UserIfc owner = (UserIfc)sservice.getCurUserInfo();
			QueryCondition condition4 = new QueryCondition("owner",
					QueryCondition.EQUAL, owner.getBsoID());
			query.addCondition(condition4);
			List imStructureList = (List)pservice.findValueInfo(query);
			List returnList = new ArrayList();
			for (int i = 0; i < imStructureList.size(); i++) {
				InterimMaterialStructureIfc iMStructureIfc = (InterimMaterialStructureIfc) imStructureList
				.get(i);
				if (iMStructureIfc.getUpdateFlag() == null
						|| !iMStructureIfc.getUpdateFlag().equals("D")) {
					returnList.add(iMStructureIfc);
				}
			}
			if (logger.isDebugEnabled()) {
				logger
				.debug("getInterimMStructure(String, String, String) - end        "
						+ returnList); //$NON-NLS-1$
			}
			return returnList;
		}

		/**
		 * �����㲿����źͲ��Ŵ��롢�Ƿ������ͷ����־��ȡ��Ӧ�����ϡ�
		 * 
		 * ���ȸ����㲿���͹��չ�̵Ĳ��Ŵ����ѯ���ҹ��̴��롣 Ȼ���жϸù��չ���µĹ���ı������̴����Ƿ��н�����
		 * a.�н���������ݹ��չ���µĹ���Ĳ��Ŵ�����Ҹ��㲿����Ӧ�����ϡ� b.û�н��������������㲿����
		 * ע�⣺���һ�����չ��û����Ҫ�������㲿�����򲻷����ù��չ�̡�
		 * 
		 * @param processRC�����չ�̵Ĳ��Ŵ���
		 * @param partIfc���㲿��ֵ����
		 * @param stepNumberList������ı��
		 * @param stepRCList��������빤�չ�̲��Ŵ��벻ͬ�Ĳ��Ŵ���
		 * @return List��ע�⣬���һ��Ԫ���Ǹ��㲿����Ӧ�������Ƿ������ͷ���ı�־������ΪBoolean
		 * @throws QMException
		 */
		public List getMaterialByPro(String processRC, QMPartIfc partIfc,
				List stepNumberList, List stepRouteCodeList) throws QMException {
			// �������������ϼ���
			List proPartList = new ArrayList();
			// �Ƿ��л�ͷ���ı�־
			boolean hasCyclePart = false;
			// �жϸù��չ���µĹ���ı������㲿���ĸù��չ�����ڲ��ŵĹ��̴����Ƿ��н�����
			// 20080110 begin
			// �������Ϊ����Ĺ���,����������㲿�����ù����й������㲿��.
			StringTokenizer stringToken = new StringTokenizer(noProcessRouteCode,
					delimiter);
			// �ù����Ƿ��й���������Ϣ�ı�־��
			boolean flag1 = true;
			// �����µĹ����빤�յĹ���������Ϣ�Ƿ��н����ı�־��
			boolean flag2 = false;
			String processRouteName;
			while (stringToken.hasMoreTokens()) {
				processRouteName = stringToken.nextToken();
				if (processRC != null && processRouteName != null
						&& processRC.equalsIgnoreCase(processRouteName)) {
					flag1 = false;
					break;
				}
			}
			if (flag1) {
				flag2 = isNumInProRoute(processRC, stepNumberList, partIfc);
			}

			// if(isNumInProRoute(processRC, stepNumberList, partIfc))
			// 20080110 end
			if (!flag1 || (flag1 && flag2)) {
				// ���㲿����Ӧ�����ϼ���
				List matList = getMSplitList(partIfc.getPartNumber());
				// ���Ŵ���
				String routeCode = null;
				// ��ʱ����List
				List tempMatList = new ArrayList();
				// ���湤��Ĳ��Ŵ����HashMap�����ظ��Ĺ����Ŵ���ȥ��
				HashMap stepRCMap = new HashMap();
				// ��ͷ���ı�ʶ
				Boolean hasCyclePartFlag;
				for (int index1 = 0; index1 < stepRouteCodeList.size(); index1++) {
					routeCode = (String) stepRouteCodeList.get(index1);
					if (!stepRCMap.containsKey(routeCode)) {
						stepRCMap.put(routeCode, routeCode);
						tempMatList = getMaterialByRC(routeCode, matList);
						// ���һ���ǻ�ͷ���ı�ʶ
						for (int i = 0; i < tempMatList.size() - 1; i++) {
							proPartList.add((MaterialSplitIfc) tempMatList.get(i));
						}
						hasCyclePartFlag = (Boolean) tempMatList.get(tempMatList
								.size() - 1);
						hasCyclePart = hasCyclePart
						|| hasCyclePartFlag.booleanValue();
					}
				}
			}
			proPartList.add(Boolean.valueOf(hasCyclePart));
			return proPartList;
		}

		/**
		 * �����㲿����źͲ��Ŵ��롢�Ƿ������ͷ����־��ȡ��Ӧ�����ϡ�
		 * 
		 * @param partNumber���㲿�����
		 * @param processRC������Ĳ��Ŵ���
		 * @return List��ע�⣬���һ��Ԫ���Ǹ��㲿����Ӧ�������Ƿ������ͷ���ı�־������ΪBoolean
		 * @throws QMException
		 */
		public List getMaterialByStep(String partNumber, String processRC)
		throws QMException {
			// �����϶�Ӧ�����ϼ���
			List matList = getMSplitList(partNumber);
			// �����϶�Ӧ�����ϼ��Ϲ��˺�Ľ��
			List resultList = getMaterialByRC(processRC, matList);
			MaterialSplitIfc mat;
			// ����Ҳ�������Ķ�Ӧ�����ϣ�����ʱ����һ������
			if (resultList.size() <= 1) {
				mat = new MaterialSplitInfo();
				mat.setRouteCode(processRC);
				// 20080107 begin
				matList = getRootMSplit(partNumber);
				if (matList.size() > 0) {
					MaterialSplitIfc matSplit = (MaterialSplitIfc) matList.get(0);
					mat.setMaterialNumber(matSplit.getMaterialNumber());
				} else {
					mat.setMaterialNumber(partNumber);
				}
				// 20080107 end

				mat.setSplited(false);
				resultList.add(0, mat);
			}
			// 20070110 begin
			else {
				// �жϸù����Ƿ�Ϊ���⹤�գ����Ϊ���⹤�գ���ȡ�õ�����Ҫ����һ�����ŵ����ϣ�
				boolean flag = true;
				// �������Ϊ����Ĺ���,����������㲿�����ù����й������㲿��.
				// StringTokenizer stringToken = new
				// StringTokenizer(useProcessPartRouteCode, delimiter);
				// //�Ƿ������⹤�յı�־��
				// boolean flag2=false;
				// String processName;
				// while(stringToken.hasMoreTokens()){
				// processName=stringToken.nextToken();
				// logger.debug("processName is "+processName);
				// if(technicShop != null
				// && processName != null &&
				// technicShop.equalsIgnoreCase(processName)){
				// flag2=true;
				// logger.debug("99999 technicShop.equalsIgnoreCase(processName) ");
				// break;
				// }
				// }
				if (flag) {
					List tempResultList = new ArrayList();
					for (int i = 0; i < resultList.size() - 1; i++) {
						mat = (MaterialSplitIfc) resultList.get(i);
						if (mat.getStatus() == 1) {
							List childList = this.getMStructure(
									mat.getPartNumber(), mat.getMaterialNumber());
							tempResultList.add((MaterialSplitIfc) childList.get(0));
						}

					}
					tempResultList.add((Boolean) resultList
							.get(resultList.size() - 1));
					resultList = tempResultList;
				}

			}
			// 20070110 end
			return resultList;
		}

		// 20070110 begin
		/**
		 * �����㲿����źͲ��Ŵ��롢���յ������ȡ��Ӧ�����ϡ�
		 * 
		 * @param partNumber���㲿�����
		 * @param stepRC������Ĳ��Ŵ���
		 * @param processType�����յ�����
		 * @return List��ע�⣬���һ��Ԫ���Ǹ��㲿����Ӧ�������Ƿ������ͷ���ı�־������ΪBoolean
		 * @throws QMException
		 */
		public List getMaterialByStep(String partNumber, String stepRC,
				String processType) throws QMException {
			logger.debug("4444444 partNumber is " + partNumber);
			logger.debug("4444444 stepRC is " + stepRC);
			logger.debug("4444444 processType is " + processType);
			// �����϶�Ӧ�����ϼ���
			List matList = getMSplitList(partNumber);
			// �����϶�Ӧ�����ϼ��Ϲ��˺�Ľ��
			List resultList = getMaterialByRC(stepRC, matList);
			MaterialSplitIfc mat;
			// ����Ҳ�������Ķ�Ӧ�����ϣ�����ʱ����һ������
			if (resultList.size() <= 1) {
				mat = new MaterialSplitInfo();
				mat.setRouteCode(stepRC);
				// 20080107 begin
				matList = getRootMSplit(partNumber);
				logger.debug("66666:" + matList.size());
				if (matList.size() > 0) {
					MaterialSplitIfc matSplit = (MaterialSplitIfc) matList.get(0);
					mat.setMaterialNumber(matSplit.getMaterialNumber());
				} else {
					mat.setMaterialNumber(partNumber);
				}
				logger.debug("66666 processRC:" + stepRC);
				logger.debug("66666:" + mat.getMaterialNumber());
				// 20080107 end

				mat.setSplited(false);
				resultList.add(0, mat);
			}
			// 20070110 begin
			else {
				// 20080122 begin
				// �ù����Ƿ�Ϊ���⹤�յı�־.
				boolean flag = false;
				// �жϸù����Ƿ�Ϊ���⹤�գ�
				// StringTokenizer stringToken = new StringTokenizer(hasSubpartTech,
				// delimiter);
				// String processName;
				// while (stringToken.hasMoreTokens()) {
				// processName = stringToken.nextToken();
				// if (processType != null && processName != null
				// && processType.equalsIgnoreCase(processName)) {
				// flag=false;
				// break;
				// }
				// }
				// 20080122 end
				// �������Ϊ����Ĺ���,�������������Ҫ����һ�����ŵ�����.
				if (flag) {
					List tempResultList = new ArrayList();
					for (int i = 0; i < resultList.size() - 1; i++) {
						mat = (MaterialSplitIfc) resultList.get(i);
						if (mat.getStatus() == 1) {
							List childList = this.getMStructure(
									mat.getPartNumber(), mat.getMaterialNumber());
							MaterialStructureIfc matStruct = (MaterialStructureIfc) childList
							.get(0);
							tempResultList
							.add(getMSplit(matStruct.getChildNumber()));
						} else {
							tempResultList.add(mat);
						}

					}
					tempResultList.add((Boolean) resultList
							.get(resultList.size() - 1));
					resultList = tempResultList;
				}

			}
			// 20070110 end
			return resultList;
		}

		// 20070110 end

		/**
		 * ��ȡ���ϼ������ض����ϴ�������ϡ�
		 * 
		 * @param routeCode�����Ŵ���
		 * @param matList�����㲿����Ŷ�Ӧ�����ϼ���
		 * @return List��ע�⣬���һ��Ԫ���Ǹ��㲿����Ӧ�������Ƿ������ͷ���ı�־������ΪBoolean
		 * @throws QMException
		 */
		private List getMaterialByRC(String routeCode, List matList) {
			logger.debug("routeCode is " + routeCode);
			logger.debug("matList.size() is " + matList.size());
			// �������������ϼ���
			List resultList = new ArrayList();
			// ����ֵ����
			MaterialSplitIfc matSplit = null;
			// �Ƿ��л�ͷ���ı�־
			boolean hasCyclePart = false;
			// �Ƿ��ҵ����ϲ��Ŵ�������ϵı�־
			boolean isFind = false;
			if (matList.size() > 0) {
				// ���Ȳ��ҹ��չ�̶�Ӧ������
				for (int index = 0; index < matList.size(); index++) {
					matSplit = (MaterialSplitIfc) matList.get(index);
					if (matSplit.getSplited()) {
						if (matSplit.getRouteCode().trim().equals(routeCode.trim())) {
							// ����ǵ�һ���ҵ��������
							if (!isFind) {
								resultList.add(matSplit);
								isFind = true;
							} else {
								// ����ǵڶ����ҵ���˵���ǻ�ͷ������Ҫ�����ж��Ƿ����
								if (publicCyclePart) {
									resultList.add(matSplit);
								}
								hasCyclePart = true;
							}
						}
					}
				}
			}
			resultList.add(Boolean.valueOf(hasCyclePart));
			return resultList;
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
		 * ��ȡ�㲿����ֵ����ϡ�
		 * 
		 * @param partNumber
		 * @return
		 * @throws QMException
		 */
		private List getMSplitList(String partNumber) throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getMSplitList(String) - start"); //$NON-NLS-1$
				logger.debug("������partNumber==" + partNumber);
			}
			List matSplitList = new ArrayList();
			// ���ж������ϼ��ϡ�
			List rootMSplitList = getRootMSplit(partNumber);
			// ����ֵ����
			MaterialSplitIfc mSplitIfc;
			for (int i = 0; i < rootMSplitList.size(); i++) {
				mSplitIfc = (MaterialSplitIfc) rootMSplitList.get(i);
				matSplitList.add(mSplitIfc);
				// �õ����ϵ������ϡ�ֻ�ڲ㼶״̬Ϊ1������²�ִ�С�
				if (mSplitIfc.getStatus() == 1) {
					getSubResultMSplitList(mSplitIfc, matSplitList);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getMSplitList(String) - end" + matSplitList); //$NON-NLS-1$
			}
			return matSplitList;
		}

		/**
		 * �����������ݡ�
		 * 
		 * @param techs
		 * @throws QMException
		 */
		public void publicTechnics(String techs) throws Exception {
			try{
			List bsoIDList = new ArrayList();
			StringTokenizer st = new StringTokenizer(techs, semicolonDelimiter);
			while (st.hasMoreTokens()) {
				bsoIDList.add(st.nextToken());
			}
			// ��ȡ�־û�����
			PersistService pService = (PersistService) EJBServiceHelper
			.getService("PersistService");
			for (int i = 0; i < bsoIDList.size(); i++) {
				// ��������
				BaseDataPublisher.publish(pService.refreshInfo((String) bsoIDList
						.get(i)));
			}
			}catch(Exception e){
				e.printStackTrace();
				
				
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
		 * �жϹ���ı���Ƿ����㲿���Ĺ��̴����У�����ڣ��򷢲����㲿�������ϣ����򲻷������㲿�������ϡ�
		 * 
		 * @param processRC:���յĲ��Ŵ���
		 * @param processNum:����ı��
		 * @param part�����չ������㲿��ֵ����
		 * @return
		 * @throws QMException
		 */
		private boolean isNumInProRoute(String processRC, List processNumList,
				QMPartIfc partIfc) throws QMException {
			logger.debug("processRC is " + processRC);
			logger.debug("processNumList.size() is " + processNumList.size());
			logger.debug("partIfc.getPartNumber() is " + partIfc.getPartNumber());
			boolean isNumInProRoute = false;
			// ���Ȼ�ȡ���㲿���Ĺ��̴��롣
			RouteCodeIBAName routeCodeIBAName = RouteCodeIBAName
			.toRouteCodeIBAName(processRC);
			// 20080125 begin
			logger.debug("routeCodeIBAName is " + routeCodeIBAName);
			if (routeCodeIBAName != null) {
				String display = routeCodeIBAName.getDisplay();
				String processCode = "";
				processCode = getPartIBA(partIfc, display);
				String processNum;
				int index;
				// Ȼ���жϸù���ı���Ƿ����㲿���Ĺ��̴����У�
				for (int i = 0; i < processNumList.size(); i++) {
					processNum = (String) processNumList.get(i);
					if (processCode != null && processCode.trim().length() > 0) {
						index = processCode.indexOf(processNum);
						if (index >= 0) {
							isNumInProRoute = true;
							break;
						}
					}
				}
			}
			// 20080125 end
			return isNumInProRoute;
		}

		// 20081205 zhangq begin
		/**
		 * ��·���е�·�ߴ���ת��ΪList,���Ҹ��������ļ�������ȥ��·���е�������롣
		 * 
		 * @param routeStr
		 * @return
		 */
		public List getRouteCodeList(String routeStr) {
			StringTokenizer routeTok = new StringTokenizer(routeStr, dashDelimiter);
			String routeCode = "";
			HashMap specRCHashMap = getDleteWhenSplitRC();
			List routeCodeList = new ArrayList();
			boolean isFirst = true;
			while (routeTok.hasMoreTokens()) {
				routeCode = routeTok.nextToken();
				if (isFirst || specRCHashMap.get(routeCode) == null) {
					routeCodeList.add(routeCode);
					isFirst = false;
				}
			}
			logger.debug("getRouteCodeList end routeCodeList is " + routeCodeList);
			return routeCodeList;
		}

		private HashMap getDleteWhenSplitRC() {
			if (specRCHashMap == null || specRCHashMap.keySet().size() <= 0) {
				specRCHashMap = new HashMap();
				String routeCode = "";
				logger.debug("getDleteWhenSplitRC deleteWhenSplitRouteCode is "
						+ deleteWhenSplitRouteCode);
				StringTokenizer stringToken = new StringTokenizer(
						deleteWhenSplitRouteCode, delimiter);
				while (stringToken.hasMoreTokens()) {
					routeCode = stringToken.nextToken();
					specRCHashMap.put(routeCode, routeCode);
				}
			}
			logger.debug("getDleteWhenSplitRC specRouteCode end is "
					+ specRCHashMap);
			return specRCHashMap;
		}

		/**
		 * �����㲿���ı�ź�·�ߴ�������֪ͨ�飬Ȼ�������������֪ͨ�������ϣ��������ɰ��������� ���̣� 1.��������֪ͨ�� 2.������� 3.�������ɰ�
		 * 4.��������
		 * 
		 * @param partBsoId
		 *            String:����֪ͨ����õĲ�Ʒ��BsoId
		 * @param promulgateNotifyFlag
		 *            String:����֪ͨ��Ĳ��ñ�ʶ
		 * @throws QMException
		 */
		/*public void publishMaterial(String partBsoId, String promulgateNotifyFlag)
			throws QMException {
		// ��һ������������֪ͨ��
		StandardPartService standardPartService = (StandardPartService) EJBServiceHelper
				.getService("StandardPartService");
		PersistService PersistService = (PersistService) EJBServiceHelper
				.getService("PersistService");
		PromulgateNotifyService promulgateNotifyService = (PromulgateNotifyService) EJBServiceHelper
				.getService("PromulgateNotifyService");
		QMPartIfc partIfc = (QMPartIfc) PersistService.refreshInfo(partBsoId);
		String partNumber = partIfc.getPartNumber();
		// findPartMaster
		// Collection coll = standardPartService.getAllPartMasters("",
		// partNumber);
		// if(coll == null || coll.size() != 1)
		// {
		// throw new QMException("�㲿�����"+partNumber+"��Ψһ��");
		// }
		partIfc = standardPartService.getPartByConfigSpec(
				(QMPartMasterIfc) partIfc.getMaster(), standardPartService
						.findPartConfigSpecIfc());
		if (partIfc == null) {
			throw new QMException(partNumber + "û�з������ù淶���㲿��!");
		}
		// CCBegin by dikefeng 20090217
		// String promulgateNotifyNumber = getPromulgateNotifyNumber(partIfc
		// .getPartNumber(), promulgateNotifyFlag);
		StandardAdoptNoticeService sanService = (StandardAdoptNoticeService) EJBServiceHelper
				.getService("StandardAdoptNoticeService");
		String promulgateNotifyNumber = "";
		int sortnumber = sanService.getNextSortNumber("PromulgateNotify",
				partIfc.getPartNumber() + promulgateNotifyFlag, false);
		promulgateNotifyNumber = getPromulgateNotifyNumber(partIfc
				.getPartNumber(), promulgateNotifyFlag, sortnumber);
		// System.out.println("here get the promugatenotify
		// number="+sortnumber+",and the number="+promulgateNotifyNumber);
		// CCEnd by dikefeng 20090217
		PromulgateNotifyInfo promulgateNotifyInfo = new PromulgateNotifyInfo();
		// ���ò���֪ͨ�������:����һ��
		promulgateNotifyInfo.setPromulgateNotifyName(promulgateNotifyNumber);
		// ���ò���֪ͨ��ı��
		promulgateNotifyInfo.setPromulgateNotifyNumber(promulgateNotifyNumber);
		// ���ò��ñ�ʶ
		promulgateNotifyInfo.setPromulgateNotifyFlag(PromulgateNotifyFlag
				.toPromulgateNotifyFlag(promulgateNotifyFlag).toString());
		// ���ò���֪ͨ���˵��
		promulgateNotifyInfo.setPromulgateNotifyDescription("");
		// ���ò���֪ͨ��ķ������:0��û�з�����1�Ƿ�������
		promulgateNotifyInfo.setHasPromulgate("0");
		// �ر�����,Ԥ���ֶΣ�׼���Ժ���չ
		promulgateNotifyInfo.setPromulgateNotifyType("PromulgateNotify");
		// ����֪ͨ��Ĳ��ò�Ʒ�ļ���
		ArrayList refPartList = new ArrayList();
		refPartList.add(partIfc.getMasterBsoID());
		// ����֪ͨ������Ĳο��ĵ��ļ���
		ArrayList refDocList = new ArrayList();
		promulgateNotifyInfo = (PromulgateNotifyInfo) promulgateNotifyService
				.createPromulgateNotify(promulgateNotifyInfo, refPartList,
						refDocList);
		// �ڶ������������
		// ����֪ͨ��Ĳ��ò����ļ���
		Vector parts = promulgateNotifyService
				.getPartsByProId(promulgateNotifyInfo.getBsoID());
		StringBuffer partBsoIdBuffer = new StringBuffer();
		QMPartIfc temPartIfc = null;
		boolean isFirst = true;
		for (int index = 0; index < parts.size(); index++) {
			temPartIfc = (QMPartIfc) parts.get(index);
			if (isFirst) {
				partBsoIdBuffer.append(temPartIfc.getBsoID());
			} else {
				partBsoIdBuffer.append(semicolonDelimiter
						+ temPartIfc.getBsoID());
			}
			isFirst = false;
		}
		RequestHelper reuqestHelper = new RequestHelper();
		Object[] obj = reuqestHelper.getPartAndRoute(
				partBsoIdBuffer.toString(), true);
		List mutPartAndRouteList = (List) obj[0];
		if (mutPartAndRouteList.size() > 0) {
			throw new QMException("����֪ͨ��Ĺ��������д��ڶ�·�ߣ�");
		}
		split((String) obj[1], (String) obj[2], true);
		// ���������������ɰ�
		String sourceid = promulgateNotifyInfo.getBsoID();
		IntePackSourceType sourcetype = IntePackSourceType
				.toIntePackSourceType("promulgateNotify");
		// �����µļ��ɰ�ֵ����
		IntePackIfc intePackIfc = new IntePackInfo();
		// intePackIfc.setName(reuqestHelper.getSourceInfo(sourceid)[1]);
		intePackIfc.setName(getIntePackName(promulgateNotifyInfo));
		intePackIfc.setSourceType(sourcetype);
		intePackIfc.setSource(sourceid);
		// ���÷��񴴽����ɰ������ɰ����������ڷ�����Ĭ�ϸ�����
		IntePackService itservice = (IntePackService) EJBServiceHelper
				.getService("IntePackService");
		intePackIfc = itservice.createIntePack(intePackIfc);
		// ���Ĳ�����������
		itservice.publishIntePack(intePackIfc.getBsoID());
	}*/

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
			// CCBegin by dikefeng 20090217
			// QMQuery query = new QMQuery("PromulgateNotify");
			// query.addCondition(new QueryCondition("promulgateNotifyNumber",
			// QueryCondition.LIKE, partNumber + promulgateNotifyFlag));
			// PersistService ps = (PersistService) EJBServiceHelper
			// .getService("PersistService");
			// Collection coll = ps.findValueInfo(query);
			// TODO:��ʱ�������ַ�ʽ����Ҫ�޸�Ϊ�Ҹ��ṩ�ķ�����
			String str = "000" + Integer.toString(j);
			// CCEnd by dikefeng 20090217
			return partNumber + promulgateNotifyFlag
			+ str.substring(str.length() - 3);
		}

		/**
		 * ��ȡ����֪ͨ����Ϊ����Դ�ļ��ɰ������ơ� ����֪ͨ����Ϊ����Դ�ļ��ɰ����Ƶı������AN-����֪ͨ��ı��-��-��-��-��λ���ֱ�š�
		 * ���ֱ�Ų�һ�����ڣ�ֻ�е�����β�ŵļ��ɰ����ƴ���ʱ�żӡ���2��ʼ�������ӡ�
		 * 
		 * @param promulgateNotifyIfc
		 * @return
		 * @throws QMException
		 */
		/*private String getIntePackName(PromulgateNotifyIfc promulgateNotifyIfc)
			throws QMException {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String number = "AN-" + promulgateNotifyIfc.getPromulgateNotifyNumber()
				+ "-" + year + "-" + month + "-" + day;
		// CCBegin by dikefeng 20090217
		// QMQuery query = new QMQuery("IntePack");
		// query.addCondition(new QueryCondition("name", QueryCondition.LIKE,
		// number));
		// PersistService ps = (PersistService) EJBServiceHelper
		// .getService("PersistService");
		// Collection coll = ps.findValueInfo(query);
		StandardAdoptNoticeService sanService = (StandardAdoptNoticeService) EJBServiceHelper
				.getService("StandardAdoptNoticeService");
		int sortnumber = sanService
				.getNextSortNumber("IntePack", number, false);
		String num = "";
		String str = "000" + Integer.toString(sortnumber);
		// CCEnd by dikefeng 20090217
		num = "-" + str.substring(str.length() - 3);

		return number + num;
	}*/

		/**
		 * ��ȡĬ�ϵ�·�����Զ��塣
		 * 
		 * @return ·�����Զ��������������ļ��ϡ�
		 * @throws QMException
		 */
		public List getDefaultRouteDefList() throws QMException {
			if (logger.isDebugEnabled()) {
				logger.debug("getDefaultRouteDefList() - start"); //$NON-NLS-1$
			}
			List routeDefList = new ArrayList();
			List tempRouteDefList = new ArrayList();
			PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
			// �ñ����Ϊ��ѯ�����������Ƿ��з���������StringDefinition��
			final QMQuery query = new QMQuery("StringDefinition");
			QueryCondition condition = new QueryCondition("name",
					QueryCondition.EQUAL, defRouteIBA);
			query.addCondition(condition);
			tempRouteDefList.addAll((List)pservice.findValueInfo(query));
			logger.debug("tempRouteDefList==" + tempRouteDefList);
			for (int i = 0; i < tempRouteDefList.size(); i++) {
				routeDefList
				.add(IBADefinitionObjectsFactory
						.newAttributeDefDefaultView((AttributeHierarchyChild) tempRouteDefList
								.get(i)));
			}
			if (logger.isDebugEnabled()) {
				logger.debug("getDefaultRouteDefList() - end" + routeDefList); //$NON-NLS-1$
			}
			return routeDefList;
		}

		public Collection getPartByRouteList(TechnicsRouteListIfc list,HashMap hashmap)
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
//			System.out.println("query = "+query.getDebugSQL());
//			System.out.println("coll = "+coll);
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
					//�˴�������룬����Ӧ��ȥ��
		
//					QMQuery qmquery = new QMQuery("FilterPart");
//		 			 QueryCondition condition1 = new QueryCondition("versionValue", "=",
//		 					partInfo.getVersionID());
//		 	          qmquery.addCondition(condition1); 
//		 	          qmquery.addAND();
//		 	          qmquery.addCondition(new QueryCondition("partNumber", "=",
//		 	        		 partInfo.getPartNumber()));
////		 	         qmquery.addOrderBy("createTime",true);
//		 	          Collection col1 = ps.findValueInfo(qmquery, false);
//						System.out.println("ssssssss0000000000dddddddddddddddddddd==="+col1.size());
//
//		 	          if(col1.size()!=0){
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
		 * added by dikefeng for sanrq 20090226
		 * 
		 * @param part
		 *            QMPartIfc
		 * @throws QMException
		 * @return String
		 */
		private String getCurrentRoute(QMPartIfc part) throws QMException {
			String route = "";
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
				QueryCondition qc2 = new QueryCondition("name", " = ", "·��1");
				query.addCondition(j, qc2);
				Collection col = pService.findValueInfo(query, false);
				if (col != null && col.size() > 0) {
					StringValueIfc value = (StringValueIfc) col.iterator().next();
					route = value.getValue();
					if (route != null && route.length() > 0)
						route = changeRoute(route);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new QMException(e);
			}
			return route;
		}

		// 20081205 zhangq end
	}
