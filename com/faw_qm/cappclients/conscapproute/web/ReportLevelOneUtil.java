/**
 * ���ɳ��� ReportLevelOneUtil.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.web;

import java.sql.Timestamp;
import java.util.*;

import com.faw_qm.persist.ejb.service.*;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.users.model.ActorInfo;
import com.faw_qm.util.*;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.workflow.engine.ejb.service.WfEngineHelper;
import com.faw_qm.workflow.engine.model.WfActivityIfc;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.workflow.work.model.AssignmentBallotLinkInfo;
import com.faw_qm.workflow.work.model.WfBallotInfo;
import com.faw_qm.workflow.work.model.WorkItemIfc;
import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.part.ejb.extendedService.ExtendedPartService;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.*;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.technics.consroute.ejb.entity.ListRoutePartLink;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.codemanage.ejb.entity.CodingEJB;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.codemanage.util.CodeManageTable;
import com.faw_qm.config.util.ConfigSpec;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.model.StringValueInfo;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.jfpublish.receive.PublishHelper;
//CCBegin SS18
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.MasteredIfc;
//CCEnd SS18
//CCBegin SS40
import com.jf.util.jfuputil;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
//CCEnd SS40

//SS1 ������·������ΪR5��,�鿴����������ʾ����  pante 20130729
//SS2 ������·��װ��·��û��ʱ����ʾ"��",��Ϊ��ʾ��"" pante 20130729
//SS3 ������·�߱�����ʾ��ŷ���Դ�汾,���Ǳ�����ļ�����ʾ�����汾,��û�е�ʱ�����ʾ�������汾 pante 2013-11-01
//SS4 ������·�߱���ģ������޸� maxt 20131203
//SS5 ����·�߱�ʹ���������� pante 2013-12-23
//SS6 �������û�Ҫ��:��ͼ��ʶ������ʹ������Ϊ0,����ʾ�ڱ����� pante 2013-12-26
//SS7 �޸ı������������ʾ������������ pante 2013-12-27
//SS8 �޸�����·�߲鿴ʱ��ʾ����ģ������ pante 2013-12-27
//SS9 �������û�Ҫ��:����ʹ������Ϊ0�Ķ���ʾΪ��  pante 2013-12-27
//SS10 �����������ë��״̬���� Liuyang 2014-1-9
//SS11 �������·��˵�� liuyang 2014-1-10
//SS12 ������Ļ�ȡ·�ߵ�λ liuyang 2014-1-10
//SS13 ������Ĳ鿴�����㲿���鿴����Ϊ�����㲿�������������㲿��  liuyang 2014-2-28
//SS14 ��ʷ����·�������Ǵ���partindex�У����ڹ����С�������������ȥpartindex�ģ����Ϊ0��Ϊ""����ȡ�����ġ� liunan 2014-2-26
//SS15 ������Ĳ鿴����汾Ϊ�����㲿���汾 Liuyang 2014-4-8
//SS16 �������˵��ÿ�е���һ�� Liuyang2014-4-17
//SS17 ������˵������ȡ����·��ʵ�����ݣ�����ȡ�̶�ȫֵ�� liunan 2014-5-9
//SS18 ����ʷ�����д�qmpartmaster����Ĵ��� liunan 2014-5-30
//SS19 �������շϣ������ظ���ʾ�����������㲿���������� liunan 2014-8-5
//SS20 ������鿴·��ʱ���汾ҲҪ��ʾ��������İ汾�� liunan 2014-9-12
//SS21 ss20��������ʷ���ݵĿ�ָ����� liunan 2014-9-24
//SS22 ������鿴·��ʱ��û�л�ȡ������һ���㲿����Դ�汾���������°��Դ�汾�� liunan 2014-10-13
//SS23 �������Ҫ������汾������ pante 2014-10-09
//SS24 �������㲿���鲻��Դ�汾�ĳ��ֿ�ָ����� liunan 2014-11-11
//SS25 ��������ʷ�����а汾�Ǽ�¼�ڱ�ע�У����ڲ鿴�������⣬������׼��Ҫ�ӱ�ע�л�ȡ�汾�� liunan 2014-11-20
//SS26 �������ӻ�ǩ���� liuyang 2014-9-1
//SS27 ����·�߱����ù�Ӧ����Ϣ���桰ר<Э>�� ���� 2014-12-18
//SS28 ������˫�汾ʱ��������ֻ��ȡ�˵�һ����汾��Ҫ����ʾ������汾����B.3.C.4����ʾΪB.C liunan 2014-12-25
//SS29 A005-2014-3025 �汾��ȡ���ԣ�û�л�ȡ��ʱ�����㲿���İ汾������ȡ�������㲿���汾�������޷���ȡiba����Դ�汾���޸Ļ�ȡiba������ liunan 2014-12-31
//SS30 ����·�߱����ù�Ӧ����Ϣ����˳����桰ר<Э>�� ���� 2014-12-18
//SS31 ����·�߱�����,����ź�����Ҫ��ʾ����汾   ���� 2015-2-6
//SS32 ����һ��·�߱�������汾����ʾ����ȷ  ����  2015-3-9
//SS33 ������鿴����ʱ���㲿�����ӵ����°棬�����Ƕ�������·�����������㲿���� liunan 2015-3-25
//SS34 ����·�߱���ȥ����Ӧ���滻���ڱ༭·����ʵ�ֹ��� �� ����  2015-4-15
//SS35 ����·�߱�������汾ȥ������� ����  2015-4-15
//SS36 ��ݱ���Ҫ����㲿����źͰ汾�ϵ�һ����Ԫ���У��汾��ȡ����Դ�汾����ȡ�㲿���汾�� liunan 2015-4-29
//SS37 ������ǰ׼����׼�������ݵ����ݻ�ȡ��ʽ�������û���������༭�������ݣ�����Ҫ���ȡ��˵������ǰ���ݡ� liunan 2015-7-8
//SS38 A037-2015-0182��S��2S��ͷ���㲿������ʱ����ʾ�㲿���汾�� liuzhicheng 2015-12-8
//SS39 �ձ��п��ܲ�������׼�����ţ�����˵���в�����С�����׼�����������´���˵���ַ����쳣�� liunan 2016-3-22
//SS40 �ɶ�����·�����ϣ�����·��5�ֱ��� liunan 2016-8-15
//SS41 A004-2017-3474 �����乤��·�߱����ȡ�汾Ҫ����༭����һ�¡���˴ӹ����л�ȡ�汾�� liunan 2017-3-19
/**
 * <p>
 * Title:����һ��·�߱���
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 * (����һ)20060629 �����޸� �޸�ԭ��:����·�����ɱ�������ٶ���
 * @author ����
 * @version 1.0
 */

public class ReportLevelOneUtil {

	private static TechnicsRouteListIfc myRouteList;
	private static Object lock = new Object(); //yanqi-20060918
	private static Collection sendToColl;//yanqi-20060918
	//CCBegin SS12
	private static Collection routeListUnit =new ArrayList();
	//CCEnd SS12
	public ReportLevelOneUtil() {
	}

	/**
	 * ��ý���ͷ����Ϣ
	 *
	 * @param routeListID
	 *            ·�߱��BsoID
	 * @return ·�߱�ı�š����ơ���Ʒ������
	 */
	public static String[] getHeader(TechnicsRouteListIfc routelist) throws QMException {
		PersistService pService = (PersistService) EJBServiceHelper.getService(
				"PersistService");
		try {

			String name = routelist.getRouteListName();
			//liunan
			String partNumber = "";

			if (routelist.getProductMasterID() != null) {
				QMPartMasterIfc partmaster = (QMPartMasterIfc) pService
							.refreshInfo(routelist.getProductMasterID());
				partNumber = partmaster.getPartNumber();
			}
	
			String product = routelist.getRouteListNumber();
			Timestamp stamp = routelist.getCreateTime();
			String year = String.valueOf(stamp.getYear()+1900);
			String month = String.valueOf(stamp.getMonth() + 1);
			String day = String.valueOf(stamp.getDate());
			String date = year + "��" + month + "��" + day + "��";
			String[] c = {
					//CCBegin SS40
					name, product, date, partNumber, routelist.getRouteListState()};
					//CCEnd SS40
			return c;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ý���ͷ����Ϣ
	 *
	 * @param routeListID
	 *            ·�߱��BsoID
	 * @return ·�߱�ı�š����ơ���Ʒ������
	 */
	public static String[] getHeader(String routeListID) {
		routeListID = routeListID.trim();
		PersistService pService = null;
		try {
			pService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			TechnicsRouteListIfc routelist = (TechnicsRouteListIfc) pService
					.refreshInfo(routeListID);
			myRouteList = routelist;
			String number = routelist.getRouteListNumber();
			String name = routelist.getRouteListName();
			String list = number + "��" + name + "��" + "��һ������·�߱���";

			QMPartMasterIfc partmaster = (QMPartMasterIfc) pService
					.refreshInfo(routelist.getProductMasterID());
			String product = partmaster.getPartNumber() + "_"
					+ partmaster.getPartName();

			String year = String.valueOf(Calendar.getInstance().get(
					Calendar.YEAR));
			String month = String.valueOf(Calendar.getInstance().get(
					Calendar.MONTH) + 1);
			String day = String.valueOf(Calendar.getInstance().get(
					Calendar.DATE));
			String date = year + "��" + month + "��" + day + "��";
			String[] c = {
					list, product, date};
			return c;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/**
	 * ���ָ��·�߱��һ��·�߱��������
	 *
	 * @return ���ؼ��ϵ�Ԫ��Ϊ����arrayObjs����Ԫ������Ϊ��š��㲿����š��㲿������
	 *         һ��·�ߴ����ַ�������array�ļ��ϡ�array[0]Ϊ����·�ߴ���array[1]Ϊװ��·�ߴ���
	 */
	public static Collection getFirstLeveRouteListReport() {
//		System.out.println("222222222222222222222222222222222222222222222222222222222");
		Vector v = new Vector();
		Vector resultVector = new Vector();
		try {
			TechnicsRouteService routeService = (TechnicsRouteService)
					EJBServiceHelper
					.getService("consTechnicsRouteService");
			CodeManageTable map = routeService
					.getFirstLeveRouteListReport(myRouteList);
			Enumeration enum1 = map.keys();
			int i = 1;
			HashMap resultsMap = new HashMap();
			while (enum1.hasMoreElements()) {
				QMPartMasterIfc partmaster = (QMPartMasterIfc) enum1
						.nextElement();
				//(����һ)20060629 �����޸� �޸�ԭ��:����·�����ɱ�������ٶ���,
				//��technicsrouteBranch������ֱ��ȡ��·�ߴ��ַ��������ûָ�node���� begin
				Collection branches = (Collection) map.get(partmaster);
				Vector strVector = new Vector();
				if (branches != null && branches.size() > 0) {
					Iterator ite = branches.iterator();
					while (ite.hasNext()) {
						String makeStr = "";
						String assemStr = "";
						String unionStr = (String) ite.next();
//						System.out.println("unionStr====================="+unionStr);
						if (unionStr != null) {
							StringTokenizer hh = new StringTokenizer(unionStr, "@");
							if (hh.hasMoreTokens()) {
								makeStr = hh.nextToken();
								assemStr = hh.nextToken();
							}
						}
						String[] array = {
								makeStr, assemStr};
						strVector.add(array);
					}
				} //end
				else {
					String[] array = {
							"", ""};
					strVector.add(array);
				}
				//(����һ)20060629 �����޸� begin
				Object[] arrayObjs = {
						String.valueOf(i++),
						partmaster.getPartNumber(), partmaster.getPartName(),
						"", strVector};

				//add end

				//modify by guoxl on 20080310(һ��·�����ɱ�����ʾʱ��������д���)

				// resultsMap.put(partmaster.getBsoID(),arrayObjs);
				resultsMap.put(partmaster.getPartNumber(), arrayObjs);
				v.add(partmaster);

			}
			//�������ֵ���󼯺�

			HashMap result = routeService.getLatestsVersion1(v);

			Vector tempVec = new Vector();
			int temp = 1;
			for (int iii = 0; iii < v.size(); iii++) {
				QMPartMasterIfc part = (QMPartMasterIfc) v.elementAt(iii);
				if (tempVec.contains(part.getPartNumber())) {
					continue;
				}

				Object[] objs = ( (Object[]) resultsMap.get(part.getPartNumber()));
				objs[0] = String.valueOf(temp++);
				objs[3] = ( (QMPartIfc) (result.get(part.getPartNumber()))).
						getVersionValue();
				resultVector.add(objs);
				tempVec.addElement(part.getPartNumber());

			}
		} //modify end
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultVector;
		//(����һ)20060629 �����޸� end
	}

	/**
	 * ���ָ��·�߱��һ��·�߱��������
	 *
	 * @return ���ؼ��ϵ�Ԫ��Ϊ����arrayObjs����Ԫ������Ϊ��š��㲿����š��㲿������
	 *         һ��·�ߴ����ַ�������array�ļ��ϡ�array[0]Ϊ����·�ߴ���array[1]Ϊװ��·�ߴ���
	 */
	public static Collection getFirstLeveRouteListReport(TechnicsRouteListIfc routelist) throws QMException {
//		System.out.println("33333333333333333333333333333333333333333333333333333333333");
		///// 20070525 liuming add
		ArrayList v = new ArrayList();

		HashMap countMap = new HashMap();
		Vector indexVector = routelist.getPartIndex();
//		System.out.println("   ReportLevelOneUtil getFirstLeveRouteListReport ()indexVector = "+indexVector);
		if (indexVector != null && indexVector.size() > 0) {
			int size2 = indexVector.size();
			String key = null;
			for (int k = 0; k < size2; k++) {
				String[] ids = (String[]) indexVector.elementAt(k);
				if(ids.length==3){
//					System.out.println("       ids .length="+ids.length+" 0:"+ids[0]+" 1:"+ids[1]+" 2:"+ids[2]);
					if (countMap.containsKey(ids[0])) {
						key = ids[0] + "K" + k;
					}
					else {
						key = ids[0];
					}
//					System.out.println("key = "+key+".........count = "+ids[2]);
					countMap.put(key, ids[2]); //����������ظ��Ŀ��ܣ���Ҫ��������
				}
			}
		}

		try {
			PersistService pservice = (PersistService) EJBServiceHelper.
					getPersistService();
			TechnicsRouteService routeService =
					(TechnicsRouteService) EJBServiceHelper.getService(
							"consTechnicsRouteService");
			//��õ�ǰ�û������ù淶
			PartConfigSpecIfc configSpecIfc = getCurrentConfigSpec_enViewDefault();
			if(configSpecIfc==null ||configSpecIfc.getStandard()==null||configSpecIfc.getStandard().getViewObjectIfc()==null)
			{
				//SSBegin SS1
				configSpecIfc = getPartConfigSpecByViewName("�����ͼ");
				//SSEnd SS1
			}

			CodeManageTable map = routeService.getFirstLeveRouteListReport(routelist);
			//liunan
			Enumeration enum1 = map.keys();
			int i = 0;
			String countInProduct = null;

			/////////////ѭ������ÿ�������㲿��
			while (enum1.hasMoreElements())
			{
				//CCBegin SS7
				QMPartMasterIfc partmaster = null;
				Object[] obj = null;
				Object o = enum1.nextElement();
				if(o instanceof QMPartMasterIfc){
					partmaster = (QMPartMasterIfc) o;
					 obj = (Object[])map.get(partmaster);
				}
				else if(o instanceof QMPartIfc){
					QMPartIfc part = (QMPartIfc) o;
					partmaster = (QMPartMasterIfc) pservice.refreshInfo(part.getMasterBsoID());
					 obj = (Object[])map.get(part);
				}
				//CCEnd SS7
				ListRoutePartLinkIfc listPartRoute =(ListRoutePartLinkIfc)obj[1];
				
				String s = (String)obj[0];
				//(����һ)20060629 �����޸� �޸�ԭ��:����·�����ɱ�������ٶ���,
				//��technicsrouteBranch������ֱ��ȡ��·�ߴ��ַ��������ûָ�node���� begin
				//            Collection branches = (Collection) obj[0];
				//            Object[] branches = ((Collection) obj[0]).toArray();
				//            String[] hhhh = getPartMakeAndAssRouteInRouteList(branches);
				String partMakeRoute = "";;
				String myAssRoute = "";
				//SSBegin SS1
				String sub = "";
				sub = s.substring(1, s.length()-1);
				String str[] = sub.split(",");
				for(int e = 0;e<str.length;e++){
					if(sub.contains("=")){
						String str1[] = str[e].split("=");
						if(e==str.length-1){
							if(str1.length>1){
								partMakeRoute += str1[0];
								//SSBegin SS2
								if(str1[1].equals("��"))
									str1[1]="";
								//SSEnd SS2
								myAssRoute += str1[1];
							}
							else if(str1.length==1){
								partMakeRoute += str1[0];
 								myAssRoute += " ";
							}
							else{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
						else{
							if(str1.length>1){
								partMakeRoute += str1[0]+"/";
								//SSBegin SS2
								if(str1[1].equals("��"))
									str1[1]="";
								//SSEnd SS2
								myAssRoute += str1[1]+"/";
							}
							else if(str1.length==1){
								partMakeRoute += str1[0]+"/";
								myAssRoute += " "+"/";
							}
							else{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
					}
					if(sub.contains("@")){
						String str1[] = str[e].split("@");
						if(e==str.length-1){
							if(str1.length>1){
								partMakeRoute += str1[0];
								//SSBegin SS2
								if(str1[1].equals("��"))
									str1[1]="";
								//SSEnd SS2
								myAssRoute += str1[1];
							}
							else if(str1.length==1){
								partMakeRoute += str1[0];
								myAssRoute += " ";
							}
							else{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
						else{
							if(str1.length>1){
								partMakeRoute += str1[0]+"/";
								//SSBegin SS2
								if(str1[1].equals("��"))
									str1[1]="";
								//SSEnd SS2
								myAssRoute += str1[1]+"/";
							}
							else if(str1.length==1){
								partMakeRoute += str1[0]+"/";
								myAssRoute += " "+"/";
							}
							else{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
					}
//					if(s.contains("=")){
//						String[] ss = s.split("=");
//						partMakeRoute = ss[0];
//						if(ss.length>1){
//							myAssRoute = ss[1];
//						}
//					}
//					if(s.contains("@")){
//						String[] ss = s.split("@");
//						partMakeRoute = ss[0];
//						if(ss.length>1){
//							myAssRoute = ss[1];
//						}
//					}	
					//SSEnd SS1
				}

				//CCBegin SS33
				/*QMPartIfc part = routeService.getLastedPartByConfig(partmaster,configSpecIfc); //liuming 20061215
				if(part ==null)
				{
					continue;
				}*/
				//CCEnd SS33

				countInProduct = "";
				String masterID = partmaster.getBsoID();
				if (countMap.containsKey(masterID)) {
					countInProduct = countMap.get(masterID).toString();
					countMap.remove(masterID);
				}
				else {
					String keya = null;
					for (Iterator jt = countMap.keySet().iterator(); jt.hasNext(); ) {
						keya = jt.next().toString();
						if (keya.startsWith(masterID)) {
							masterID = keya;
							countInProduct = countMap.get(masterID).toString();
							countMap.remove(masterID);
							break;
						}
					}
				}
				//System.out.println(">>> masterID = "+masterID+".....countInProduct = "+countInProduct);
				if (countInProduct.trim().equals("0")) {
					countInProduct = "";
				}


				///////����ÿ�����������
				/////////////////////////////////////////////////////////////



				//���ø��ı�ʶ�ͱ�ע
				TechnicsRouteIfc route = null;
				String change = "";
				StringBuffer remark = new StringBuffer("") ;
				if (listPartRoute != null)
				{
					String routeID = listPartRoute.getRouteID();
					//System.out.println("routeID is " + routeID );
					if (routeID != null)
					{
						route = (TechnicsRouteIfc) pservice.refreshInfo(routeID,false);
					}
				}
				String version = "";
		    	//CCBegin SS15
				String partBsoID=listPartRoute.getRightBsoID();
				//CCBegin SS18
				//QMPartIfc partInfo = (QMPartIfc) pservice.refreshInfo(partBsoID);
				QMPartIfc partInfo = null;
				if(partBsoID.startsWith("QMPartMaster"))
				{
					QMPartMasterIfc partMasterInfo = (QMPartMasterIfc) pservice.refreshInfo(partBsoID);
					VersionControlService vs = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
					Vector nv = (Vector)vs.allIterationsOf((MasteredIfc)partMasterInfo);
					partInfo = (QMPartIfc) nv.get(0);
				}
				else
				{
					partInfo = (QMPartIfc) pservice.refreshInfo(partBsoID);
				}
				//CCEnd SS18
			    String comp =RouteClientUtil.getUserFromCompany();
				//CCEnd SS15
//				System.out.println("listPartRoute.getRouteID()=========="+listPartRoute.getRouteID());

				if(listPartRoute.getRouteID()!=null){
	
					if (listPartRoute != null)
					{//liunan
						//Begin by chudaming 20090716
						String des1 ="";
						//SSBegin SS1
						if(listPartRoute.getModifyIdenty()!=null){
//							change = route.getRouteDescription().substring(route.getRouteDescription().indexOf(":")+1,route.getRouteDescription().indexOf(":")+2);
							//SS4 BEGIN
//							change = listPartRoute.getModifyIdenty().trim();
							change = listPartRoute.getModifyIdenty().trim();

							if(change.equals("��ͼ")){
								change = "G";
							}else if(change.equals("����")){
								change = "C";
							}else if(change.equals("ȡ��")){
								change = "Q";
							}else if(change.equals("����")){
								change = "X";
							}else{
								change = "F";
							}
							//SS4 END
//							des1 = route.getRouteDescription().substring(route.getRouteDescription().indexOf(")")+1,route.getRouteDescription().length());
							des1 = listPartRoute.getRouteDescription();
//							System.out.println("--------"+change+"--------"+des1+"--------"+part.getVersionValue());
							//SSEnd SS1
							//End by chudaming 20090716
							if(des1==null)
							{
								des1= "";
							}
						}
						remark.append(des1) ;
					}
		
					if (listPartRoute != null)
					{
						//CCBegin SS15
					    if(comp.equals("zczx")){
					    	//CCBegin SS36
					    	/*IBAValueService ibaService = null;
							ibaService = (IBAValueService) PublishHelper
										.getEJBService("IBAValueService");

							DefaultAttributeContainer attc = (DefaultAttributeContainer) partInfo
									.getAttributeContainer();
							if (attc!= null) {
								AbstractValueView[] valueview = attc
										.getAttributeValues();
								int m = valueview.length;
								for (int ii = 0; ii < m; ii++) {
									if ((valueview[ii].getDefinition()
											.getName()).toLowerCase().trim()
											.equals("sourceversion")) {
										AbstractValueIfc value = IBAValueObjectsFactory
												.newAttributeValue(
														valueview[ii], partInfo);
										version = ((StringValueIfc) value)
												.getValue().toString();
									} else if ((valueview[ii].getDefinition()
											.getName()).toLowerCase().trim()
											.equals("partVersion")) {
										AbstractValueIfc value = IBAValueObjectsFactory
												.newAttributeValue(
														valueview[ii], partInfo);
										version = ((StringValueIfc) value)
												.getValue().toString();
									}
								}
							}else{
								version = listPartRoute.getPartVersion();
//								version=version.trim().substring(0, 1);////SS23
							}
							
							if(version==""||version.equals("")){
								version = listPartRoute.getPartVersion();
//								version=version.trim().substring(0, 1);////SS23
							}*/
					  version = getPartSourceVersion(partInfo.getBsoID());
						if(version==""||version.equals(""))
						{
							version = listPartRoute.getPartVersion();
							if(version!=null)
							{
								String[] vv = version.trim().split("[.]");//split�޷�����.  ���[.]
								if(vv.length==2)//���汾���
								{
									version=vv[0];
								}
								else if(vv.length==4)//˫�汾���
								{
									version=vv[0]+"."+vv[2];
								}
							}
							else
							{
								version = partInfo.getVersionID();
							}
						}
						else
						{
							version = version.trim().substring(0, version.indexOf("."));
						}
						if(!version.equals(""))
						{
							version = "/"+version;
						}
						//CCEnd SS36
	                    }
					//CCBegin SS41
					else if(comp.equals("bsx"))
					{
						version = listPartRoute.getPartVersion();
						if(version!=null&&(version==""||version.equals("")))
						{
							//CCBegin SS25
							if(remark!=null)
							{
								if(remark.indexOf(":"+change)!=-1)
								{
									version = remark.substring(1,remark.indexOf(":"));
								}
							}
							//CCEnd SS25
						}
						if(version!=null && version.indexOf(".")!=-1)
						{
							version = version.trim().substring(0, version.indexOf("."));
						}
					}
					//CCEnd SS41
	                    else{
	                   //CCEnd SS15
						//Begin by chudaming 20090716
//						if(listPartRoute.getRightBsoID()!=null)
						//CCBegin SS3
						//CCBegin SS29
						/*IBAValueService ibaService = null;
						ibaService = (IBAValueService) PublishHelper
									.getEJBService("IBAValueService");
						//CCBegin SS22
						//part = (QMPartInfo) ibaService
								//.refreshAttributeContainerWithoutConstraints(part);
						//DefaultAttributeContainer attc = (DefaultAttributeContainer) part
						DefaultAttributeContainer attc = (DefaultAttributeContainer) partInfo
								.getAttributeContainer();
						//CCEnd SS22
						//CCBegin SS24
						//System.out.println("partInfo==="+partInfo+"  and attc=="+attc);
						if (attc!= null)
						{
						//CCEnd SS24
						AbstractValueView[] valueview = attc.getAttributeValues();
						int m = valueview.length;
						for (int ii = 0; ii < m; ii++) {
							if ((valueview[ii].getDefinition().getName())
									.toLowerCase().trim().equals("sourceversion")) {
								AbstractValueIfc value = IBAValueObjectsFactory.newAttributeValue(
										//CCBegin SS22
										//valueview[ii], part);
										valueview[ii], partInfo);
										//CCEnd SS22
								version = ((StringValueIfc) value).getValue().toString();
							}
							else if((valueview[ii].getDefinition().getName())
									.toLowerCase().trim().equals("partVersion")) {
								AbstractValueIfc value = IBAValueObjectsFactory.newAttributeValue(
										//CCBegin SS22
										//valueview[ii], part);
										valueview[ii], partInfo);
										//CCEnd SS22
								version = ((StringValueIfc) value).getValue().toString();
							}
						}
						//CCBegin SS24
					}*/
					version = getPartSourceVersion(partInfo.getBsoID());
					//CCEnd SS29
					//System.out.println("version==="+version);
						//CCEnd SS24
						if(version==""||version.equals("")){
							//CCBegin SS20
							//version = part.getVersionID();//0716
							version = listPartRoute.getPartVersion();
							//System.out.println("version 111==="+version);
							//CCBegin SS21
							//version=version.trim().substring(0, 1);
							if(version!=null)
							{
								//CCBegin SS28
								//version=version.trim().substring(0, 1);
								String[] vv = version.trim().split("[.]");//split�޷�����.  ���[.]
								if(vv.length==2)//���汾���
								{
									version=vv[0];
								}
								else if(vv.length==4)//˫�汾���
								{
									version=vv[0]+"."+vv[2];
								}
								//CCEnd SS28
							}
							else
							{
								//CCBegin SS29
								//version = part.getVersionID();
								version = partInfo.getVersionID();
								//CCEnd SS29
							}
							//CCEnd SS21
							//CCEnd SS20
							//System.out.println("version 222==="+version);
						}
						//CCBegin SS29
						else
						{
							version = version.trim().substring(0, version.indexOf("."));
						}
						//CCEnd SS29
						//CCEnd SS3	
						//End by chudaming 20090716
	                    }
					}

				}

//				else{
//					if (listPartRoute != null)
//					{//liunan
//						//Begin by chudaming 20090716
//						String des1 ="";
//						if(listPartRoute.getRouteDescription()!=null){
//							change = listPartRoute.getRouteDescription().substring(listPartRoute.getRouteDescription().indexOf(":")+1,listPartRoute.getRouteDescription().indexOf(":")+2);
//							des1 = listPartRoute.getRouteDescription().substring(listPartRoute.getRouteDescription().indexOf(")")+1,listPartRoute.getRouteDescription().length());
//							//End by chudaming 20090716
//							if(des1==null)
//							{
//								des1= "";
//							}
//						}
//						remark.append(des1) ;
//					}
//
//
//					if (listPartRoute != null)
//					{
//						//Begin by chudaming 20090716
//						if(listPartRoute.getRouteDescription()!=null)
//							version = listPartRoute.getRouteDescription().substring(1,listPartRoute.getRouteDescription().indexOf(":"));//0716
//						//End by chudaming 20090716
//
//					}
//				}

				/*System.out.println(">>> partmaster = "+partmaster.getPartNumber()+".....version = "+version
                             +".....countInProduct = "+countInProduct+".....partMakeRoute = "+partMakeRoute+".....part.getBsoID() = "+part.getBsoID()
                             +".....remark.toString() = "+remark.toString());*/
				/////����˱�ע 20070122
				//CCBegin SS5
//				Object[] arrayObjs =
//					{
//						change, partmaster.getPartNumber(),
//						version,
//						partmaster.getPartName(), countInProduct, partMakeRoute,
//						myAssRoute, part.getBsoID(),remark.toString()
//					};
				//CCBegin SS10
				String sockID=listPartRoute.getStockID();
				//CCEnd SS10
				//CCBegin SS6
				//CCBegin SS14
				if(countInProduct.equals("0")||countInProduct.equals(""))
				//CCEnd SS14
				countInProduct = String.valueOf(listPartRoute.getProductCount());

				//CCBegin SS9
				//CCBegin SS14
				//if(listPartRoute.getProductCount()==0)
				if(countInProduct.equals("0"))
				//CCEnd SS14
					countInProduct = "";
				//CCEnd SS9
				//CCEnd SS6
				//CCBegin SS12
//                String comp =RouteClientUtil.getUserFromCompany();
				if (comp.equals("zczx")) {
					if (partMakeRoute != null && !partMakeRoute.equals("")) {
						if (partMakeRoute.indexOf("/") != -1) {
							String[] r1 = partMakeRoute.split("/");
							for (int m = 0; m < r1.length; m++) {
								if (r1[m] != null && !r1[m].equals("")) {
									String[] route1 = r1[m].split("-");
									for (int n = 0; n < route1.length; n++) {
										if (routeListUnit.contains(route1[n])) {
										} else {
											routeListUnit.add(route1[n]);
										}
									}
								}
							}
						} else {
							String[] route1 = partMakeRoute.split("-");
							for (int n = 0; n < route1.length; n++) {
								if (routeListUnit.contains(route1[n])) {
								} else {
									routeListUnit.add(route1[n]);
								}
							}
						}
					}
					if (myAssRoute != null && !myAssRoute.equals("")) {
						if (myAssRoute.indexOf("/") != -1) {
							String[] r2 = myAssRoute.split("/");
							for (int m = 0; m < r2.length; m++) {
								if (r2[m] != null && !r2[m].equals("")) {
									String[] route2 = r2[m].split("-");
									for (int n = 0; n < route2.length; n++) {
										if (routeListUnit.contains(route2[n])) {
										} else {
											routeListUnit.add(route2[n]);
										}
									}
								}
							}
					 	} else {
							String[] route2 = myAssRoute.split("-");
							for (int n = 0; n < route2.length; n++) {
								if (routeListUnit.contains(route2[n])) {

								} else {
									routeListUnit.add(route2[n]);
								}
							}
						}
					}
				}
				//CCEnd SS12
				//CCBegin SS10
//				Object[] arrayObjs =
//					{
//						change, partmaster.getPartNumber(),
//						version,
//						partmaster.getPartName(), countInProduct, partMakeRoute,
//						myAssRoute, part.getBsoID(),remark.toString()
//					};
	           //CCBegin SS13
				   
//				String partBsoID=listPartRoute.getRightBsoID();
//				QMPartIfc partInfo = (QMPartIfc) pservice.refreshInfo(partBsoID);
				if(comp.equals("zczx")){
					//CCBegin SS36
					//Object[] arrayObjs ={change,partInfo.getPartNumber(),version,partInfo.getPartName(),countInProduct, partMakeRoute,
					i++;
					Object[] arrayObjs ={Integer.toString(i),change,partInfo.getPartNumber()+version,partInfo.getPartName(),countInProduct, partMakeRoute,
					//CCEnd SS36
							myAssRoute, partInfo.getBsoID(),remark.toString(),sockID				
					};
					v.add(arrayObjs);
				}else{
				//CCEnd SS13
				
				//CCBegin SS27
				//CCBegin SS30
				if(comp.equals("ct")){
					//��������·�߻���װ��·��Ϊ��һ·��ʱ��ȥ����/��
					partMakeRoute = partMakeRoute.trim();
					if(!partMakeRoute.equals("")){
						//�жϵ�һ�������һ���ַ����ǲ���"/"
						String be = partMakeRoute.substring(0,1);
						String le = partMakeRoute.substring(partMakeRoute.length()-1,partMakeRoute.length());
						if(partMakeRoute.equals("/")){//����·��ֻ��"/"�����
							partMakeRoute = "";
						}else{
							if(be.equals("/")){//��ʼ��"/"��ȥ����һ��"/"
								partMakeRoute=partMakeRoute.substring(1,partMakeRoute.length());
							}
							if(le.equals("/")){//������"/"��ȥ�����һ��"/"
								partMakeRoute=partMakeRoute.substring(0,partMakeRoute.length()-1);
							}
						}
						//CCBegin SS34
						if(partMakeRoute.contains("/ /")){
							String[] tempMake = partMakeRoute.split("/ /");
							StringBuffer tempMakeBuffer = new StringBuffer();
							for(int j = 0 ; j<tempMake.length;j++){
								if(j!=tempMake.length-1){
									tempMakeBuffer.append(tempMake[j]+"/");
								}else{
									tempMakeBuffer.append(tempMake[j]);
								}
								
							}
							partMakeRoute= tempMakeBuffer.toString();
						}
						//CCEnd SS34
						
					}
					myAssRoute = myAssRoute.trim();
					if(!myAssRoute.equals("")){
						String be = myAssRoute.substring(0,1);
						String le = myAssRoute.substring(myAssRoute.length()-1,myAssRoute.length());
						if(myAssRoute.equals("/")){
							myAssRoute = "";
						}else{
							if(be.equals("/")){
								myAssRoute=myAssRoute.substring(1,myAssRoute.length());
							}
							if(le.equals("/")){
								myAssRoute=myAssRoute.substring(0,myAssRoute.length()-1);
							}
						}
						
						//CCBegin SS34
						
						if(myAssRoute.contains("/ /")){
							String[] tempAss = myAssRoute.split("/ /");
							StringBuffer tempAssBuffer = new StringBuffer();
							for(int j = 0 ; j<tempAss.length;j++){
								if(j!=tempAss.length-1){
									tempAssBuffer.append(tempAss[j]+"/");	
								}else{
									tempAssBuffer.append(tempAss[j]);
								}
								
							}
							myAssRoute = tempAssBuffer.toString();
						}
						//CCEnd SS34
					}
					//�����Ӧ�̿���
					//�����Ӧ�̣���������װ�䶼����ר<Э>�������һ����Ӧ���滻װ��·�ߵ�ר<Э>��������Ӧ���滻����·�ߵ�ר<Э>
					//һ����Ӧ�̣��������·�߻���װ��·�߰���ר<Э>�����ù�Ӧ���滻·�ߵ�λר<Э>
					//CCBegin SS34
//					boolean isManySupper = false;
//					
//					String supper=listPartRoute.getSupplier();
//					if(supper!=null){
//						supper = supper.trim();
//					}
//					if(supper!=null&&(!supper.equals(""))){
//						
//						String assSupper = "";
//						StringBuffer makeSupper = new StringBuffer();;
//					
//						if(supper.contains(";")){
//							isManySupper = true;
//							String[] subSup = supper.split(";");
//							assSupper = subSup[subSup.length-1];
//							for(int j = 0 ; j<subSup.length-1;j++){
//								if(j==subSup.length-2){
//									makeSupper.append(subSup[j]) ;
//								}else{
//									makeSupper.append(subSup[j]+";") ;
//								}
//								
//							}
//						}else{
//							isManySupper = false;
//							assSupper = supper;
//						}
//						
//						//�����װ�䶼���ǿ�
//						if((!partMakeRoute.equals(""))&&(!myAssRoute.equals(""))){
//							//�����װ�䶼����רЭ
//							if(partMakeRoute.contains("ר<Э>")&&myAssRoute.contains("ר<Э>")){
//								if(isManySupper){
//									partMakeRoute = partMakeRoute.replaceAll("ר<Э>",makeSupper.toString());
//									myAssRoute = myAssRoute.replaceAll("ר<Э>",assSupper);
//								}else{
//									if(supper!=null&&(!supper.equals(""))){
//										partMakeRoute = partMakeRoute.replaceAll("ר<Э>",supper);
//										myAssRoute = myAssRoute.replaceAll("ר<Э>",supper);
//									}
//								}
//							}else{
//								if(partMakeRoute.contains("ר<Э>")){
//									partMakeRoute = partMakeRoute.replaceAll("ר<Э>",supper);
//								}
//								if(myAssRoute.contains("ר<Э>")){
//									myAssRoute = myAssRoute.replaceAll("ר<Э>",supper);
//								}
//							}
//							
//							//�����ǿգ�װ�䲻�ǿգ���װ���滻
//						}else if((partMakeRoute.equals(""))&&(!myAssRoute.equals(""))){
//							if(myAssRoute.contains("ר<Э>")){
//								if(supper!=null&&!(supper.equals(""))){
//									myAssRoute = myAssRoute.replaceAll("ר<Э>",supper);
//								}
//							
//							}
//							//װ���ǿգ����첻�ǿգ��������滻
//						}else if((!partMakeRoute.equals(""))&&(myAssRoute.equals(""))){
//							if(partMakeRoute.contains("ר<Э>")){
//								if(supper!=null&&!(supper.equals(""))){
//									partMakeRoute = partMakeRoute.replaceAll("ר<Э>",supper);
//								}
//							
//							}
//						}
//						
//						
//					}	
					//CCEnd SS34
				//CCBegin SS31
				//CCBegin SS35 SS38
					String partVersionValue = listPartRoute.getPartVersion();
					String partVer = partVersionValue.substring(0, 1);
					String partNumVer = partmaster.getPartNumber()+"/"+partVer;
					if(partmaster.getPartNumber().startsWith("S")||partmaster.getPartNumber().indexOf("S")== 1)//��S��2S��ͷ���㲿������ʱ����ʾ�㲿���汾
					{
						partNumVer = partmaster.getPartNumber();
					}
					Object[] arrayObjs ={
							//CCBegin SS32
							change, partNumVer,
			    //CCEnd SS35 SS38
							//CCEnd SS32
							version,
							partmaster.getPartName(), countInProduct, partMakeRoute,
							//CCBegin SS33
							//myAssRoute, part.getBsoID(),remark.toString(),sockID
							myAssRoute, partInfo.getBsoID(),remark.toString(),sockID
							//CCEnd SS33
						};
						v.add(arrayObjs);
				//CCEnd SS31
				}
				//CCBegin��SS31
				else{
				//CCEnd SS31
					//CCEnd SS30
					//CCEnd SS27	
						
					Object[] arrayObjs ={
						change, partmaster.getPartNumber(),
						version,
						partmaster.getPartName(), countInProduct, partMakeRoute,
						//CCBegin SS33
						//myAssRoute, part.getBsoID(),remark.toString(),sockID
						myAssRoute, partInfo.getBsoID(),remark.toString(),sockID
						//CCEnd SS33
					};
					v.add(arrayObjs);
					}
				//CCBegin��SS31
				}
				//CCEnd SS31
					//CCEnd SS10
					//CCEnd SS5
					
			}
		}
		catch (QMException ex) {
			ex.printStackTrace();
			//yanqi_20060922
			throw ex;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			//yanqi_20060922
			QMException e = new QMException(ex);
			throw e;
		}
		return v;
	}
	/*  public static Collection getFirstLeveRouteListReport(TechnicsRouteListIfc routelist) throws QMException {

      ///// 20070525 liuming add
      ArrayList v = new ArrayList();

       HashMap countMap = new HashMap();
      Vector indexVector = routelist.getPartIndex();
    //  System.out.println("   ReportLevelOneUtil getFirstLeveRouteListReport ()indexVector = "+indexVector);
      if (indexVector != null && indexVector.size() > 0) {
        int size2 = indexVector.size();
        System.out.println("size2============"+size2);
        String key = null;
        for (int k = 0; k < size2; k++) {
          String[] ids = (String[]) indexVector.elementAt(k);
          System.out.println("1111111============"+ids[0]);
          System.out.println("2222222============"+ids[1]);
//          System.out.println("       ids .length="+ids.length+" 0:"+ids[0]+" 1:"+ids[1]);
          if (countMap.containsKey(ids[0])) {
            key = ids[0] + "K" + k;
            System.out.println("iiiiiiiiiiiiii============");
            countMap.put(key, ids[0]); //����������ظ��Ŀ��ܣ���Ҫ��������
          }
          else {
            key = ids[0];
            System.out.println("eeeeeeeeeeeeeee============");
//            countMap.put(key, ids[0]);
             //����������ظ��Ŀ��ܣ���Ҫ��������
          }
          countMap.put(key, ids[2]);
//          System.out.println("kkkkkkkkk============"+ids[k].toString());
//          System.out.println("key = "+key+".........count = "+ids[2]);

        }
      }

      try {
        PersistService pservice = (PersistService) EJBServiceHelper.
            getPersistService();
        TechnicsRouteService routeService =
            (TechnicsRouteService) EJBServiceHelper.getService(
            "consTechnicsRouteService");
        //��õ�ǰ�û������ù淶
        PartConfigSpecIfc configSpecIfc = getCurrentConfigSpec_enViewDefault();
        if(configSpecIfc==null ||configSpecIfc.getStandard()==null||configSpecIfc.getStandard().getViewObjectIfc()==null)
        {
          configSpecIfc = getPartConfigSpecByViewName("������ͼ");
        }

        CodeManageTable map = routeService.getFirstLeveRouteListReport(routelist);
        //liunan
        System.out.println("map================"+map.size());
        Enumeration enum1 = map.keys();
        int i = 0;
        String countInProduct = null;

        /////////////ѭ������ÿ�������㲿��
        while (enum1.hasMoreElements())
        {
            QMPartMasterIfc partmaster = (QMPartMasterIfc) enum1.nextElement();
            System.out.println("------------"+map.get(partmaster));
            System.out.println("------------"+map.values());
            Object obj = (Object)map.get(partmaster);
            ListRoutePartLinkIfc listPartRoute =(ListRoutePartLinkIfc)obj;

            //(����һ)20060629 �����޸� �޸�ԭ��:����·�����ɱ�������ٶ���,
            //��technicsrouteBranch������ֱ��ȡ��·�ߴ��ַ��������ûָ�node���� begin
//            Collection branches = (Collection) obj[0];
            Object[] branches = ((Collection) obj).toArray();
            String[] hhhh = getPartMakeAndAssRouteInRouteList(branches);
            String partMakeRoute = hhhh[0];
            String myAssRoute = hhhh[1];

//            if (branches != null && branches.size() > 0)
//            {
//                Iterator  ite  = branches.iterator();
//                while (ite.hasNext())
//                {
//                    String unionStr = (String)ite.next();
//                    if(unionStr!=null)
//                    {
//                        StringTokenizer hh = new StringTokenizer(unionStr,"@");
//                        if(hh.hasMoreTokens())
//                        {
//                            partMakeRoute = hh.nextToken();
//                            myAssRoute = hh.nextToken();
//                        }
//                    }
//                }
//            }


          QMPartIfc part = routeService.getLastedPartByConfig(partmaster,configSpecIfc); //liuming 20061215
          if(part ==null)
          {
              continue;
          }

          ////////����ÿ��������////////////////////////////////////////////////////
          //String countInProduct = Integer.toString(listPartRoute.getCount());
          //String masterID = partmaster.getBsoID();
//          System.out.println(">>> masterID = "+masterID+".....countInProduct = "+countInProduct);
          //if (countInProduct.trim().equals("0"))
          //{
            //countInProduct = "";
          //}

        countInProduct = "";
        String masterID = partmaster.getBsoID();
        if (countMap.containsKey(masterID)) {
          countInProduct = countMap.get(masterID).toString();
          countMap.remove(masterID);
        }
        else {
          String keya = null;
          for (Iterator jt = countMap.keySet().iterator(); jt.hasNext(); ) {
            keya = jt.next().toString();
            if (keya.startsWith(masterID)) {
              masterID = keya;
              countInProduct = countMap.get(masterID).toString();
              countMap.remove(masterID);
              break;
            }
          }
        }
        //System.out.println(">>> masterID = "+masterID+".....countInProduct = "+countInProduct);
        if (countInProduct.trim().equals("0")) {
          countInProduct = "";
        }


          ///////����ÿ�����������
          /////////////////////////////////////////////////////////////



          //���ø��ı�ʶ�ͱ�ע
          TechnicsRouteIfc route = null;
          String change = "";
          StringBuffer remark = new StringBuffer("") ;
          if (listPartRoute != null)
          {
            String routeID = listPartRoute.getRouteID();
            //System.out.println("routeID is " + routeID );
            if (routeID != null)
            {
              route = (TechnicsRouteIfc) pservice.refreshInfo(routeID,false);
            }
          }
          if (route != null)
          {//liunan
        	  //Begin by chudaming 20090716
            change = route.getRouteDescription().substring(route.getRouteDescription().indexOf(":")+1,route.getRouteDescription().indexOf(":")+2);
            String des1 = route.getRouteDescription().substring(route.getRouteDescription().indexOf(")")+1,route.getRouteDescription().length());
            //End by chudaming 20090716
            if(des1==null)
            {
              des1= "";
            }
            remark.append(des1) ;
//            String des2 = route.getRouteDescription();xs
//            if(des2==null || des2.equals("") )
//            {
//            }
//            else
//            {
//              if(des1.equals(""))
//              {
//              }
//              else
//              {
//                remark.append(" ");
//              }
//              remark.append(des2);
//            }
          }

          String version = "";
          version = getPartSourceVersion(part.getBsoID());
          if (version == null || version.equals(""))
          {
              version = part.getVersionValue();
              if (version == null || version.equals(""))
              {
                  version = "  ";
              }
          }
          version = version.substring(0, 1);
          if (route != null)
          {
        	  //Begin by chudaming 20090716
            version = route.getRouteDescription().substring(1,route.getRouteDescription().indexOf(":"));//0716
            //End by chudaming 20090716

          }
          //CCBegin  by wanghonglian 2008-06-05
          //�汾��Ϣǰ���ü��ϡ�/��
          if(!version.trim().equals(""))
          {
            version = "/"+version;
          }
          //CCEnd by wanghonglian 2008-06-05

System.out.println(">>> partmaster = "+partmaster.getPartNumber()+".....version = "+version
                             +".....countInProduct = "+countInProduct+".....partMakeRoute = "+partMakeRoute+".....part.getBsoID() = "+part.getBsoID()
                             +".....remark.toString() = "+remark.toString());
          /////����˱�ע 20070122
          Object[] arrayObjs =
              {
              change, partmaster.getPartNumber(),
              version,
              partmaster.getPartName(), countInProduct, partMakeRoute,
              myAssRoute, part.getBsoID(),remark.toString()
          };
          v.add(arrayObjs);
        }
      }
      catch (QMException ex) {
        ex.printStackTrace();
        //yanqi_20060922
        throw ex;
      }
      catch (Exception ex) {
        ex.printStackTrace();
        //yanqi_20060922
        QMException e = new QMException(ex);
        throw e;
      }
      return v;
   }*/


	/**
	 * yanqi-20061010
	 * ��ȡ�㲿��part�ڳ���root�ϵ�ʹ���������ݹ�
	 * @param part QMPartIfc
	 * @param root ����
	 * @param parentMap key=partID value=������(PartUsageLinkIfc, QMPartIfc)ΪԪ�صļ���
	 * @param useQuanMap key=partID  value=�㲿���ڳ����ϵ�ʹ������
	 * @throws ServiceLocatorException
	 * @throws QMException
	 */
	public static float getUseQuantity(QMPartIfc part, QMPartIfc root,
			Map theparentMap, Map useQuanMap,PartConfigSpecIfc configSpec) throws
			ServiceLocatorException, QMException
			{
//		System.out.println("44444444444444444444444444444444444444444444444444444444");
		Float f = (Float) useQuanMap.get(part.getBsoID());
		if (f != null) {
			return f.floatValue();
		}

		if (part.getBsoID().equals(root.getBsoID()))
		{
			return 1f;
		}


		float useCount = 0f;
		StandardPartService partService = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
		Collection parentPartColl = (Collection) theparentMap.get(part.getBsoID());
		if (parentPartColl == null)
		{

			//�����ݿ���Ѱ��ʹ����(partIfc����Ӧ��)PartMasterIfc��
			//���еĸ���(QMPartIfc)������ֵ��������(PartUsageLinkIfc, QMPartIfc)ΪԪ�صļ��ϡ�
			parentPartColl = getParentPartsByConfigSpec(part,configSpec);
			if (parentPartColl != null)
			{
				theparentMap.put(part.getBsoID(), parentPartColl);
			}
			else
			{
				//�����useCount=0
				useQuanMap.put(part.getBsoID(), Float.valueOf(useCount + ""));
				return useCount;
			}
		}


		//����и���
		if (parentPartColl.size() != 0)
		{
			for (Iterator it = parentPartColl.iterator(); it.hasNext(); )
			{
				Object[] obj1 = (Object[]) it.next();
				PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) obj1[0];
				QMPartIfc parentPart = (QMPartIfc) obj1[1];
				useCount = useCount + usageLinkIfc.getQuantity() *
						getUseQuantity(parentPart, root, theparentMap, useQuanMap,configSpec);
			}
		}
		useQuanMap.put(part.getBsoID(), Float.valueOf(useCount + ""));

		return useCount;
			}


	/**
	 * liuming 20070116 add
	 * ��StandPartService��������ͬ��������Ŀ���ǽ���õĸ����в�������С�汾��ȥ��
	 * ͨ��ָ�������ù淶�������ݿ���Ѱ��ʹ����(partIfc����Ӧ��)PartMasterIfc��
	 * ���з������ù淶�Ĳ���(QMPartIfc)��
	 * ����ֵ����Object[] = (PartUsageLinkIfc, QMPartIfc)ΪԪ�صļ��ϡ�
	 * @param partIfc �㲿��ֵ����
	 * @param partConfigSpecIfc �㲿�����ù淶
	 * @return Collection
	 * @throws QMException
	 */
	private static Collection getParentPartsByConfigSpec(QMPartIfc partIfc,
			PartConfigSpecIfc
			partConfigSpecIfc) throws
			QMException {
//		System.out.println("5555555555555555555555555555555555555555555555555");
		//����navigateUsedByToIteration(partIfc, partConfigSpecIfc)
		//�Խ�����Ͻ��й��ˣ�ֻ������QMPartIfc�Ķ���
		Vector result = new Vector();
		Collection collection = navigateUsedByToIteration(partIfc,
				new PartConfigSpecAssistant(partConfigSpecIfc));
		if ( (collection == null) || (collection.size() == 0)) {

			return null;
		}
		else {
			Object[] array = new Object[collection.size()];
			array = (Object[]) collection.toArray(array);
			for (int i = 0; i < array.length; i++) {
				if (array[i] instanceof QMPartIfc) {
					result.addElement(array[i]);
				}
			}

			PersistService pService = (PersistService) EJBServiceHelper.getService(
					"PersistService");
			Vector resultVector = new Vector();
			for (int i = 0; i < result.size(); i++)
			{
				QMPartIfc parentPartIfc = (QMPartIfc) result.elementAt(i);

				TechnicsRouteService routeService = (TechnicsRouteService)
						EJBServiceHelper.getService("consTechnicsRouteService");
				QMPartIfc newPart = routeService.getLastedPartByConfig( (
						QMPartMasterIfc) parentPartIfc.getMaster(), partConfigSpecIfc);
				//�ж��Ƿ������°汾,ȥ����������С�汾��Part
				if (parentPartIfc.getBsoID().equals(newPart.getBsoID()))
				{
					//leftBsoID�Ǳ�ʹ�õ��㲿����QMPartMaster��BsoID
					//rightBsoID��ʹ�����㲿����BsoID::
					String leftBsoID = partIfc.getMasterBsoID();
					String rightBsoID = parentPartIfc.getBsoID();
					Collection coll = null;
					//��Ҫ����leftBsoID��rightBsoID�ҵ�PartUsageLinkIfc����Ӧ��ֻ��һ��������һ��ֻ��һ������Ϊ�ж�����ͬһ�Ӽ������ skx��
					if(rightBsoID.startsWith("GenericPart"))
					{
						coll = pService.findLinkValueInfo("GenericPartUsageLink",
								leftBsoID,
								"uses", rightBsoID);
					}
					else
					{
						coll = pService.findLinkValueInfo("PartUsageLink",
								leftBsoID,
								"uses", rightBsoID);
					}
					//modify by skx ���ж����ӵ����Ҫ�ڱ����ڲ�Ʒ�����а�ÿһ��·������ʾ����
					if (coll != null && coll.size() > 0) {
						Iterator iterator = coll.iterator();
						while (iterator.hasNext()) {
							PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) iterator.next();
							Object[] obj1 = new Object[2];
							obj1[0] = usageLinkIfc;
							obj1[1] = parentPartIfc;
							resultVector.addElement(obj1);
						}
					}
				}
			}

			return resultVector;
		}
	}

	/**
	 * liuming 20070116 add
	 * ��StandPartService��������ͬ����������ȫ��ͬ��
	 * �������ù淶��ȡʹ����(ָ�����㲿������Ӧ��)QMPartMaster�������㲿���ļ��ϡ�
	 * @param partIfc ָ�����㲿��ֵ����
	 * @param configSpec �������ù淶
	 * @return Vector �㲿���ļ���
	 * @throws QMException
	 */
	private static Vector navigateUsedByToIteration(QMPartIfc partIfc,
			ConfigSpec configSpec) throws
			QMException {
//		System.out.println("6666666666666666666666666666666666666666666666");
		PersistService pService = (PersistService) EJBServiceHelper.getService(
				"PersistService");
		//���صĽ������:
		Vector resultVector = new Vector();
		QMPartMasterIfc masterIfc = (QMPartMasterIfc) partIfc.getMaster();
		QMQuery qmQuery = new QMQuery("QMPart", "PartUsageLink");
		qmQuery = configSpec.appendSearchCriteria(qmQuery);
		//���ݲ�ѯ���������Ӧ�Ľ����:
		Collection colAll = pService.navigateValueInfo(masterIfc, "uses", qmQuery, true);
		Collection resultCollection = null;
		if (colAll != null || colAll.size() > 0) {
			resultCollection = configSpec.process(colAll);
		}
		//����������:
		if (resultCollection != null && resultCollection.size() > 0) {
			Iterator iterator = resultCollection.iterator();

			while (iterator.hasNext()) {
				Object object0 = iterator.next();
				if (object0 instanceof Object[]) {
					Object[] objArray = (Object[]) object0;
					resultVector.addElement( (QMPartIfc) objArray[0]);
				}
				else {
					resultVector.addElement(object0);
				}
				//end if(object0 instanceof Object[])
			}
			//end while(iterator.hasNext())
		}
		//end if(resultCollection != null && resultCollection.size() > 0)

		return resultVector;
	}

	/**
	 * ��ȡ��ǰ�û������ù淶������û����״ε�½ϵͳ������Ĭ�ϵġ�������ͼ��׼�����ù淶��yanqi-20060918-���ɹ���·�߱�ʱʹ��
	 * @throws QMException ʹ��ExtendedPartServiceʱ���׳���
	 * @return PartConfigSpecIfc ��׼���ù淶��
	 */
	public static PartConfigSpecIfc getCurrentConfigSpec_enViewDefault() throws
	QMException {
		synchronized (lock) {
			StandardPartService spService = (StandardPartService) EJBServiceHelper.
					getService("StandardPartService");
			PartConfigSpecIfc configSpec = (PartConfigSpecIfc) spService.
					findPartConfigSpecIfc();
			if (configSpec == null) {
				configSpec = new PartConfigSpecInfo();
				configSpec.setStandardActive(true);
				configSpec.setBaselineActive(false);
				configSpec.setEffectivityActive(false);
				PartStandardConfigSpec partStandardConfigSpec = new
						PartStandardConfigSpec();
				ViewService viewService = (ViewService) EJBServiceHelper.getService(
						"ViewService");
				ViewObjectIfc view = viewService.getView("�����ͼ");
				partStandardConfigSpec.setViewObjectIfc(view);
				partStandardConfigSpec.setLifeCycleState(null);
				partStandardConfigSpec.setWorkingIncluded(true);
				configSpec.setStandard(partStandardConfigSpec);
				ExtendedPartService extendedPartService = (ExtendedPartService)
						EJBServiceHelper.getService("ExtendedPartService");
				return extendedPartService.savePartConfigSpec(configSpec);
			}
			else {
				return configSpec;
			}
		}
	}

	/**
	 * ��ʾ����ʱ����(�־û�����)
	 * �����ж���׼�Ƿ��ڸ������ϼ�.����ڸ������ϼ�,�������ɱ���,���������ݿ�;
	 * ����ڹ������ϼ�,���һ���ж��������ݿ�ReportResult�����Ƿ����м�¼,����о�ֱ
	 * ����ȡ,���û���������ɱ����������ݿ�.
	 * liuming 20061108
	 * @param routeListID String
	 * @throws QMException
	 */
	public static ArrayList getAllInformation2(String routeListID) throws
	QMException

	{
//		System.out.println("88888888888888888888888888888888888888888888888888888");
		PersistService pservice = (PersistService) EJBServiceHelper.
				getPersistService();
		TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.refreshInfo(
				routeListID, false);
		FolderService folderService = (FolderService) EJBServiceHelper.getService(
				"FolderService");
		FolderIfc folder = folderService.getFolder(listInfo.getLocation());
//		System.out.println("-----------------------------------------------"+folder.getName()+"             "+listInfo.getLocation());
		if(folder==null)
		{
			throw new QMException("�������ϼ�"+listInfo.getLocation()+"û�ж�Ȩ��!");
		}
		return getAllInformationColl(routeListID);
	}

	/**
	 * ��ʾ����ʱ���ͻ��˵��á�(���־û�����)
	 * @param routeListID String
	 * @return ArrayList
	 * @throws QMException
	 */
	public static ArrayList getAllInformationColl(String routeListID) throws
	QMException {
//		System.out.println("999999999999999999999999999999999999999999999");
		QMQuery query = new QMQuery("consListRoutePartLink");
		PersistService pservice = (PersistService) EJBServiceHelper.
				getPersistService();
		query.addCondition(new QueryCondition("leftBsoID", "=",routeListID.trim()));
		Collection coll = pservice.findValueInfo(query);
		PersistService pService = (PersistService) EJBServiceHelper.getService(
				"PersistService");
		TechnicsRouteListIfc myRouteList1 = (TechnicsRouteListIfc) pService.
				refreshInfo(routeListID.trim());
		ArrayList allinfomationColl = new ArrayList(5);
		//CCBegin SS40
		String comp = RouteClientUtil.getUserFromCompany();
		//CCEnd SS40
		sendToColl = new ArrayList();
		allinfomationColl.add(getHeader(myRouteList1));
		//CCBegin SS40
		if(comp.equals("cd"))
		{
			System.out.println("aaaaaaaaaa");
			allinfomationColl.add(getFirstLeveRouteListReportCD(myRouteList1, coll));
			allinfomationColl.add(getTail1(sendToColl));  //������λ
		}
		else
		{
			allinfomationColl.add(getFirstLeveRouteListReport(myRouteList1));
			allinfomationColl.add(getTail1(myRouteList1));  //������λ
		}
		//CCEnd SS40
		//SS4 BEGIN 
		allinfomationColl.add(getTail2(myRouteList1,routeListID));  //˵��
		//SS4 END
		allinfomationColl.add(getWFProcessDetails2(myRouteList1)); //������Ϣ
		sendToColl = null;
		return allinfomationColl;
	}
	
	//CCBegin SS40
	    public static String getTail1(Collection sendToColl) {
      if (sendToColl == null) {
        return "";
      }
      sendToColl.remove("��");
      String haha = "";
      for (Iterator it1 = sendToColl.iterator(); it1.hasNext(); ) {
        String jj = (String) it1.next();
        haha += jj + "��";
      }
      if (haha.endsWith("��")) {
        haha = haha.substring(0, haha.length() - 1);
      }
      return haha;
    }
  //CCEnd SS40

	public static String getTail1(TechnicsRouteListIfc listInf)
	{
//		System.out.println("00000000000000000000000000000000000000000000000000000000");
		if(sendToColl==null)
		{
			return "";
		}

		String haha = "";
		//CCBegin by wanghonglian 2008-06-04  
		//ɾ��������λ��˵����Ϣ
		String tip = listInf.getRouteListDescription();
		if(tip != null)
		{
			if(tip.indexOf("������λ��")!= -1)
			{
				System.out.println("tip-------------="+tip);
				tip = tip.substring(tip.indexOf("������λ��")+5);
				System.out.println("tip-----������λ--------="+tip);
				haha += tip;
			}
		}
		//CCEnd by wanghonglian  2008-06-04
		//CCBegin by wanghonglian 2008-09-05
		//ȥ��������λ��·�ߵ�λ��ʾ���û��Լ��ֶ�ά��
		//Դ�������£�
		/*  for (Iterator it1 = sendToColl.iterator(); it1.hasNext(); ) {
    String jj = (String) it1.next();
    if(jj.equals("Э"))//liunan ·��Ϊ��Э���� ����ʾ�ڷ�����λ�С�
    continue;
    //CCBegin by wanghonglian 2008-06-04
    //�����ٺŵ���ʾ
    if(haha.length()!= 0)
    {
  	  haha += "��"+jj ;
    }  
    else
        haha += jj + "��";
    //CCEnd by wanghonglian 2008-06-04

  }
  if (haha.endsWith("��")) {
    haha = haha.substring(0, haha.length() - 1);
  }*/
		//CCEnd by wanghonglian 2008-09-05
		//System.out.println("whl test sent to ===== " + haha);
		return haha;
	}

	/**
	 * ��þ�������Ĺ���·�߱�˵��
	 * @return Collection
	 * @throws QMException 
	 */
	//SS4 BEGIN
//	public static Collection getTail2(TechnicsRouteListIfc listInf) throws QMException {
	public static Collection getTail2(TechnicsRouteListIfc listInf,String routeListID) throws QMException {
	//SS4 END
		Collection coll = new ArrayList();
		String tip = listInf.getRouteListDescription();
	    System.out.println("whl test routelist description === " + tip);
		if (tip == null) {
			return coll;
		}
		///���˵���˵�����еķָ��   liuming  add 20061212
		String dd = String.valueOf((char)1);
		int ddt = tip.indexOf(dd);
		if(ddt != -1)
		{
			String b1 = tip.substring(0,ddt);
			String b2 = tip.substring(ddt+1);
			tip = b1 + b2;
		}
		///////////////////////
		//CCBegin by wanghonglian 2008-06-04 
		// ɾ��������λ��˵����Ϣ 
		if(tip.indexOf("������λ��")!= -1)
		{
			tip = tip.substring(0,tip.indexOf("������λ��"));
		}
		//SS4 BEGIN
		//���·�߱�״̬
		int isYSZ= isYshizhun(routeListID);
		//SS4 END
		//CCEnd by wanghonglian 2008-06-04 
		String tip1 = "";
		String tip2 = "";
		String tip3 = "";
	
		if (tip.indexOf("˵����") != -1) {
			// CCBegin SS11	
			String comp = RouteClientUtil.getUserFromCompany();
			String ss="";
			if (comp.equals("zczx")) {
				tip1 = tip.substring(0, tip.indexOf("˵����"));
				//CCBegin SS16
				if (tip1.indexOf("���ݣ�") != -1) {
					 //CCEnd SS16
					tip1=tip1.substring(0,tip1.indexOf("���ݣ�"));				
					if (routeListUnit != null) {
						Iterator it = routeListUnit.iterator();
						while (it.hasNext()) {
							String ss0 = (String) it.next();
							if (!ss0.trim().equals("") && ss0 != null) {
								if (!ss.equals("") && ss != null) {
									ss = ss + ";" + ss0;
								} else {
									ss = ss0;
								}
							}
						}
					}
					//CCBegin SS16
					tip2= tip.substring(tip.indexOf("���ݣ�"), tip.indexOf("˵����"));
					String tip4 = "";
					String tip5 = "";
				
					tip4= tip.substring(tip.indexOf("˵����"), tip.indexOf("·�ߴ��룺"));
					
		            tip5="·�ߴ��룺"+ss;   
		            //CCEnd SS16
					routeListUnit.clear();
					coll.add(tip1);
					coll.add(tip2);
					//CCBegin SS16
					coll.add(tip4);
					coll.add(tip5);
					 //CCEnd SS16
					
				}
			} else {
		// CCEnd SS11
				// SS4 BEGIN
				// tip1 = tip.substring(0, tip.indexOf("˵����"));
				// tip2 = tip.substring(tip.indexOf("˵����"));
				if (isYSZ == 1) {
					// ����׼
					tip1 = tip.substring(0, tip.indexOf("˵����"));
				} else if (isYSZ == 2) {
					// ǰ׼
					//CCBegin SS37
					/*String str = tip.substring(0, tip.indexOf("��"))
							+ "����ǰ׼��������׼��;";
					String str1 = tip.substring(tip.indexOf("��") + 2, tip
							.indexOf("˵����"));
					tip1 = str + " " + str1 + "���׼����";*/
					tip1 = tip.substring(0, tip.indexOf("˵����"));
					//CCEnd SS37
				} else if (isYSZ == 3) {
					// ��׼
					tip1 = tip.substring(0, tip.indexOf("˵����"));
				} else if (isYSZ == 4) {
					// ��׼
					//CCBegin SS37
					/*String str = tip.substring(0, tip.indexOf("��"))
							+ "����ǰ׼��������׼��;";
					String str1 = tip.substring(tip.indexOf("��") + 2, tip
							.indexOf("˵����"));
					tip1 = str + " " + str1 + "���׼����";*/
					tip1 = tip.substring(0, tip.indexOf("˵����"));
					//CCEnd SS37
				} else if (isYSZ == 5) {
					// �ձ�
					//CCBegin SS39
					//tip1 = tip.substring(0, tip.indexOf("��"))
					//		+ "����׼     ������������׼�����,��Ͷ��������";
					if(tip.indexOf("��")!=-1)
					{
						tip1 = tip.substring(0, tip.indexOf("��"))
							+ "����׼     ������������׼�����,��Ͷ��������";
					}
					else
					{
						tip1 = tip.substring(0, tip.indexOf("������������׼�����"))
							+ "������������׼�����,��Ͷ��������";
					}
					//CCEnd SS39
				} else {
					// �շ�
					//CCBegin SS19
					//tip1 = tip.substring(0, tip.indexOf("˵����")) + "���������㲿����";
					tip1 = tip.substring(0, tip.indexOf("˵����"));
					//CCEnd SS19
				}

				//CCBegin SS17
				//tip2 = "˵��:·�ߴ���  ���ɡ�Ϊ�ɹ��������䡱Ϊװ�䳵�䣬���㡱Ϊ������䣬��Э��Ϊ��Ź�˾�ɹ��������ᡱΪ������ģ�������ΪӪ������";
				tip2 = tip.substring(tip.indexOf("˵����"),tip.length());
				//CCEnd SS17
				// SS4 END

				coll.add(tip1);
				coll.add(tip2);
			}
		}
		else {
			coll.add(tip);
		}
	
		return coll;
	}


	/**
	 * ��ȡ�������̲�������Ϣ�����ݽ��Ҫ����Ӵ˷�����   20061201 liuming add
	 * Ҫȥ�������ء�֮ǰ�����в����߼�¼��
	 * @param routeListID String
	 * @return Collection
	 * @throws QMException
	 */
	public static Collection getWFProcessDetails2(TechnicsRouteListIfc routeListInfo) throws
	QMException
	{
//		System.out.println("=========================================================");
		//CCBegin SS26
		String comp=RouteClientUtil.getUserFromCompany();
		//CCEnd SS26
		Vector vec = new Vector();
		vec.add(new ArrayList());
		vec.add(new ArrayList());
		vec.add(new ArrayList());
		//CCBegin SS26 SS40
		if(comp.equals("ct")||comp.equals("cd")){
			vec.add(new ArrayList());
		}
		//CCEnd SS26 SS40
		vec.add("");
		QMQuery query = new QMQuery("WfProcess");
		PersistService pservice = (PersistService) EJBServiceHelper.
				getPersistService();
		query.addCondition(new QueryCondition("businessObjBsoID", "=",
				WfEngineHelper.getPBOIdentity(routeListInfo)));
		query.addOrderBy("startTime", true);
		Collection coll = pservice.findValueInfo(query);
		if (coll != null && coll.size() > 0)
		{
			Iterator it = coll.iterator();
			WfProcessIfc process = (WfProcessIfc) it.next(); //ȡ���µ���ؽ���
			Vector infoVector = new Vector();
			//��ȡ���л
			ArrayList list = getAllActivities(process.getBsoID());
			for (int i = 0; i < list.size(); i++)
			{
				WfActivityIfc activity = (WfActivityIfc) list.get(i);
				infoVector.addAll(getVoteInformation(activity));
			}
			//����˳����ˣ������С����ء���Ϊ
			//���������ء����ͽ�֮ǰ�Ĳ�����ȥ��
			if(infoVector.size()>0)
			{

				int oldSize = infoVector.size();
				Timestamp rejectTime = null;
				////////////�鿴�Ƿ��С����ء�
				for(int i=0;i<oldSize;i++)
				{
					Object[] array = (Object[])infoVector.elementAt(i);
					String vote = array[2].toString();
					if(vote.indexOf("����") > -1)
					{
						Timestamp tempTime = (Timestamp) array[3];
//						System.out.println("����ʱ��1 = " + rejectTime);
						if (rejectTime == null) {
							rejectTime = tempTime;
						}
						else {
							if (tempTime != null && tempTime.after(rejectTime)) {
								rejectTime = tempTime;
							}
						}
//						System.out.println("����ʱ��2 = " + rejectTime);
					}
				}

				Vector tv = new Vector();
				////////////����С����ء��������
				if(rejectTime!=null)
				{
					//System.out.println("--------------�в���-------------");
//					System.out.println("����ʱ�� = "+rejectTime);
					for(int j=0;j<oldSize;j++)
					{
						Object[] array = (Object[])infoVector.elementAt(j);
						Timestamp time = (Timestamp)array[3];
						//String name = array[0].toString();
						//String user = array[1].toString();
						//System.out.println("����o = "+name+user+time);
						if(time.after(rejectTime))  //ֻ�������ڲ���ʱ��ļ�¼
						{
							tv.add(array);
						}
					}
				}
				else
				{
//					System.out.println("--------------�޲���-------------");
					tv = infoVector;
				}
				///////////////////////////////////////////

				ArrayList shenheVector = new ArrayList();
				//CCBegin by wanghonglian 2008-06-04 
				//�����ɱ�����������ʾ��׼�ߺ�У���ߵ���Ϣ
				ArrayList pizhunVector = new ArrayList();
				ArrayList jiaoduiVector = new ArrayList();
				/////////////�Ի��Ϣ���з��ࣨУ�ԡ���ǩ����ˡ���׼��
				//CCBegin SS26
				ArrayList huiqianVector = new ArrayList();
				//CCEnd SS26
				if(tv.size()>0)
				{
					//System.out.println("--------------��ʣ��-------------");
					int newSize = tv.size();
					for(int k =0 ; k<newSize; k++)
					{
						Object[] array = (Object[])tv.elementAt(k);
						String name = array[0].toString();
						String user = array[1].toString();
						//CCBegin by wanghonglian 2008-04-28
						//�ڽ�ɫ��������� 
						user += array[3].toString().substring(0,10);
						//CCEnd by wanghonglian 2008-04-28
						if(name.indexOf("��׼")>-1)
						{
							if(!pizhunVector.contains(user))
								pizhunVector.add(user);
						}
						else
							if(name.indexOf("���")>-1)
							{
								if(!shenheVector.contains(user))
									shenheVector.add(user);
							}
							else
								if(name.indexOf("У��")>-1)
								{
									if(!jiaoduiVector.contains(user))
										jiaoduiVector.add(user);
								}
								//CCBegin SS26 SS40
						if(comp.equals("ct")||comp.equals("cd")){
							if(name.indexOf("��ǩ")>-1){
								if(!huiqianVector.contains(user))
									huiqianVector.add(user);
							}
						}
						//CCEnd SS26 SS40
					}
				}///////////////////////�������!
				vec.setElementAt(pizhunVector,0);
				vec.setElementAt(shenheVector,1);
				vec.setElementAt(jiaoduiVector,2);
				//CCBegin SS26  SS40
				if(comp.equals("ct")||comp.equals("cd")){
					vec.setElementAt(huiqianVector,3);
				}
				//CCEnd SS26 SS40
			}
		}
		ActorInfo userinfo = (ActorInfo) pservice.refreshInfo(routeListInfo.
				getCreator());
				//CCBegin SS26 SS40
		if(comp.equals("ct")||comp.equals("cd")){
			vec.setElementAt(userinfo.getUsersDesc(),4);
		}else{
			vec.setElementAt(userinfo.getUsersDesc(), 3);
		}
		//CCend SS26 SS40
		return vec;
	}

	/**
	 * ���ظ����������е����й��ڵĵĻ
	 * @param   process    �����Ĺ���������
	 * @return Iterator    �������ֵ���󼯺�
	 * @throws WfException
	 */
	public static ArrayList getAllActivities(String bsoID) throws QMException
	{
//		System.out.println("```````````````````````````````````````````````````````");
		ArrayList list = new ArrayList();
		QMQuery query = new QMQuery("WfAssignedActivity");
		query.addCondition(new QueryCondition("parentProcessBsoID", "=", bsoID));
		list.addAll( ( (PersistService) EJBServiceHelper.getPersistService()).
				findValueInfo(query));
		return list;
	}


	/**
	 * ���ָ�������Ϣ���û���ͶƱ��Ϣ��ͶƱʱ�䣩    20061201 liuming add
	 * @param waInfo WfActivityIfc
	 * @return Vector��ÿ��Ԫ�ض���һ�����顣array[0]=�û�����array[1]=ͶƱ��Ϣ��array[2]=ͶƱʱ��
	 * @throws QMException
	 */
	public static Vector getVoteInformation(WfActivityIfc waInfo) throws QMException
	{
//		System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
		Vector v = new Vector();
		String activityID = waInfo.getBsoID();
		PersistService persistService = (PersistService) EJBServiceHelper.
				getService(
						"PersistService");
		QMQuery query = new QMQuery("WorkItem");
		query.addCondition(new QueryCondition("sourceID", "=", activityID));
		Iterator iterator = (persistService.findValueInfo(query)).iterator();
		String name = waInfo.getName();
		Object[] tempInfo = null ;
		while (iterator.hasNext())
		{
			WorkItemIfc workitemIfc = (WorkItemIfc) iterator.next();
			String ownerid = workitemIfc.getOwner();
			if (workitemIfc.getCompletedBy() != null)
			{
				ActorInfo userinfo = (ActorInfo) persistService.refreshInfo(ownerid);
				String user = userinfo.getUsersDesc();
				String WfAssignmentID = workitemIfc.getParentWA();
				QMQuery query1 = new QMQuery("AssignmentBallotLink");
				query1.addCondition(new QueryCondition("leftBsoID", "=", WfAssignmentID));
				Collection coll = persistService.findValueInfo(query1);
				if (coll.size() == 0)
				{
					continue;
				}

				Timestamp modifyTime = workitemIfc.getModifyTime();
				String vote = "";
				for (Iterator iterator1 = coll.iterator(); iterator1.hasNext(); )
				{
					AssignmentBallotLinkInfo assignmentBallotLink = (
							AssignmentBallotLinkInfo) iterator1.next();
					String voteID = assignmentBallotLink.getRightBsoID();
					WfBallotInfo ballot = (WfBallotInfo) persistService.refreshInfo(
							voteID);
					Vector vec = ballot.getEventList();
					for (int i = 0; i < vec.size(); i++)
					{
						vote += vec.elementAt(i) + "��";
					}
				}
				vote = vote.endsWith("��") ? vote.substring(0, vote.length() - 1) : vote;
				if (vote.length() > 0)
				{
					tempInfo = new Object[4] ;
					tempInfo[0] = name;
					tempInfo[1] = user;
					tempInfo[2] = vote;
					tempInfo[3] = modifyTime;
					v.add(tempInfo);
					tempInfo = null;
				}
			}
		}
		return v;
	}

	/**
	 * ��IBA���ԣ���ȡ����Ĵ�汾
	 * @param ID PartBsoID
	 * @return �����IBA�����еİ汾
	 * @throws QMException
	 */
	private static String getPartSourceVersion(String ID) throws
	QMException
	{
//		System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
		PersistService pservice = (PersistService) EJBServiceHelper.
				getPersistService();
		String defintionID =null;

		QMQuery query11 = new QMQuery("StringDefinition");
		query11.addCondition(new QueryCondition("name", "=", "sourceVersion"));
		Collection collf = pservice.findValueInfo(query11);
		for (Iterator iterator = collf.iterator(); iterator.hasNext(); )
		{
			com.faw_qm.iba.definition.model.StringDefinitionInfo dd = (com.faw_qm.
					iba.
					definition.model.StringDefinitionInfo) iterator.next();
			defintionID = dd.getBsoID();
		}

		QMQuery query = new QMQuery("StringValue");
		query.addCondition(new QueryCondition("definitionBsoID", "=",
				defintionID));

		query.addAND();
		query.addCondition(new QueryCondition("iBAHolderBsoID", "=", ID));

		Collection collf1 = pservice.findValueInfo(query);
		for (Iterator iterator = collf1.iterator(); iterator.hasNext(); )
		{
			StringValueInfo a = (StringValueInfo) iterator.next();
			return a.getValue().trim();
		}
		return "";
	}


	/**
	 * liuming 20070116 add
	 * ȡ����������·�ߵĵ�һ�����쵥λ��Ϊ�ü���װ��·�ߣ�
	 * ����ҵ����ϼ����ж�������·��(��·��)����ȡÿ������·�ߵĵ�һ�����쵥λ(�ü�����װ��·�ߵĶ�·��)��
	 * ������û������·�ߣ���������װ��·�ߡ�
	 * @param part ����
	 * @return װ��·��
	 * @throws QMException
	 */
	private static String getAssisRoute(QMPartIfc part) throws QMException
	{
//		System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
		TechnicsRouteService routeService =
				(TechnicsRouteService) EJBServiceHelper.getService(
						"consTechnicsRouteService");

		String[] prouts = routeService.getRouteString(part);
		String myAssRoute = prouts[0];
		StringTokenizer yy = new StringTokenizer(myAssRoute, "/");
		String parentMakeRouteFirst = "";
		while (yy.hasMoreTokens()) {
			String aa = yy.nextToken();
			StringTokenizer MM = new StringTokenizer(aa, "--");
			parentMakeRouteFirst += MM.nextToken() + "/";
		}
		if (parentMakeRouteFirst.endsWith("/")) {
			parentMakeRouteFirst = parentMakeRouteFirst.substring(0,
					parentMakeRouteFirst.length() - 1);
		}
		return parentMakeRouteFirst;
	}
	/**
	 * ���˵������еĹ��岿����GenericPart��
	 * yanqi-20061010
	 * ��ԭ���Ļ�ȡ·�߱������������ķ��������޸ģ����ٱ�������
	 * @param part QMPartIfc �Ӽ�
	 * @param count float ��������
	 * @param result Collection ���صĸ�����ʹ����������
	 * @param parentMap key=partID value=������(PartUsageLinkIfc, QMPartIfc)ΪԪ�صļ���
	 * @throws ServiceLocatorException
	 * @throws QMException
	 */
	private static void getParentPartsNew(QMPartIfc part, float count,
			Collection result, Map parentMap,PartConfigSpecIfc configSpec) throws
			ServiceLocatorException, QMException
			{
//		System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
		Collection parentColl = (Collection) parentMap.get(part.getBsoID());
		if (parentColl == null) {
			parentColl = getParentPartsByConfigSpec(part,configSpec);
			if (parentColl != null) {
				parentMap.put(part.getBsoID(), parentColl);
			}
		}

		if (parentColl != null)
		{
			for (Iterator it = parentColl.iterator(); it.hasNext(); )
			{
				Object[] obj1 = (Object[]) it.next();
				PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) obj1[0];
				QMPartIfc parentPart = (QMPartIfc) obj1[1];
				/** if (parentPart instanceof com.faw_qm.pcfg.family.model.
          GenericPartInfo)
      {
        continue; /////////���ݽ��Ҫ�� Gpart��Ҫ!!
      }*/

				float newCount = usageLinkIfc.getQuantity() * count;
				//���ڡ��߼��ܳɡ�������ͨ������������ô��ж� ��Ф����������Ƽͬ�⣩ liuming 20061212
				/** if (parentPart.getPartType().toString().equalsIgnoreCase("Logical"))
      {
        getParentPartsNew(parentPart, newCount, result, parentMap);
      }
      else { */
				String beyond = parentPart.getBsoID() + "..." +
						new Integer( (new Float(newCount)).intValue());
				result.add(beyond);
				// }
			}
		}
			}

	/**
	 * ������ͼ���Ʒ����㲿�����ù淶  liuming 20061207 add
	 * @param viewName String
	 * @throws QMException
	 * @return PartConfigSpecIfc
	 */
	private static PartConfigSpecIfc getPartConfigSpecByViewName(String viewName) throws
	QMException {
//		System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
		ViewService viewService = (ViewService) EJBServiceHelper.getService("ViewService");
		ViewObjectIfc view = viewService.getView(viewName);
		//������ָ������ͼ����û�л�ȡ����Ӧ��ֵ�����򷵻ص�ǰ���ù淶
		if(view==null)
		{
			StandardPartService spService = (StandardPartService) EJBServiceHelper.
					getService("StandardPartService");
			return (PartConfigSpecIfc) spService.
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
		return  partConfigSpecIfc;
	}

	/**
	 * ���ָ������ڵ�ǰ·�߱��е�����·�ߺ�װ��·��
	 * @param listPartRoute �������
	 * @param map CodeManageTable
	 * @return String[] ��һ��Ԫ��������·��,�ڶ���Ԫ����װ��·��
	 * @throws QMException
	 */
	private static  String[] getPartMakeAndAssRouteInRouteList(Object[] branches) throws
	QMException
	{
//		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		String makeStr = "";
		String assStr = "";
		ArrayList makeList = new ArrayList(); //����·�߼���,Ϊ�˱�֤�ѳ��ֵ�����ڵ㲻�ٳ���,�� ��/��---->��
		ArrayList assList = new ArrayList(); //ͬ��
		if(sendToColl==null)
		{
			sendToColl = new ArrayList();
		}
		if (branches != null && branches.length > 0)
		{
			for (int j = 0; j < branches.length; j++)
			{
				if (makeStr.length() > 0 && !makeStr.endsWith("/"))
				{
					makeStr += "/";
				}
				if (assStr.length() > 0 && !assStr.endsWith("/"))
				{
					assStr += "/";
				}
				Object[] objs = (Object[]) branches[j];
				Vector makeNodes = (Vector) objs[0]; //����ڵ�
				RouteNodeIfc asseNode = (RouteNodeIfc) objs[1]; //װ��ڵ�
				if (makeNodes != null && makeNodes.size() > 0)
				{
					String makeStrBranch = "";
					for (int m = 0; m < makeNodes.size(); m++)
					{
						RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
						String departmentName = node.getNodeDepartmentName().toString();
						if(departmentName==null)
						{
							throw new QMException("·�ߵ�λ��"+node.getNodeDepartment()+"û�����ƣ�");
						}
						if (!sendToColl.contains(departmentName) && departmentName != "")
						{
							sendToColl.add(departmentName);
						}
						if (makeStrBranch == "")
						{
							if (node.getIsTempRoute())
							{
								makeStrBranch = makeStrBranch + departmentName + "*";
							}
							else {
								makeStrBranch = makeStrBranch + departmentName;
							}
						}
						else
						{
							if (node.getIsTempRoute())
							{
								makeStrBranch = makeStrBranch + "-" + departmentName + "*";
							}
							else {
								makeStrBranch = makeStrBranch + "-" + departmentName;
							}
						}
					}
					if (!makeList.contains(makeStrBranch))
					{
						makeList.add(makeStrBranch);
						makeStr = makeStr + makeStrBranch + "/";
					}
				}
				String assDepartment = "";
				if (asseNode != null)
				{
					assDepartment = asseNode.getNodeDepartmentName().toString();
					if (!sendToColl.contains(assDepartment) &&
							assDepartment != "")
					{
						sendToColl.add(assDepartment);
					}
					if (!assList.contains(assDepartment))
					{
						assList.add(assDepartment);
						if (asseNode.getIsTempRoute())
						{
							assStr = assStr + assDepartment + "*" + "/";
						}
						else {
							assStr = assStr + assDepartment + "/";
						}
					}
				}
			}
		}
		makeStr = makeStr.endsWith("/") ?
				makeStr.substring(0, makeStr.length() - 1) :
					makeStr;
				assStr = assStr.endsWith("/") ? assStr.substring(0, assStr.length() - 1) :
					assStr;
				String[] aaaa = new String[2];
				aaaa[0] = makeStr;
				aaaa[1] = assStr;
				return aaaa;
	}

	/**
	 * yanqi-20061010
	 * �ж��㲿��part�Ƿ���root���Ӽ�
	 * @param part QMPartIfc
	 * @param root QMPartIfc
	 * @param parentMap Map ���棬���������㲿��id�����㲿���ĸ������ϣ����ٰ����ù淶��ȡ�����Ĵ���
	 * @param isSonMap Map a���Ƿ�b�����Ӽ��Ļ���,��:a��bsoID+"_"+b��bsoID,ֵ:Boolean
	 * @throws ServiceLocatorException
	 * @throws QMException
	 * @return boolean
	 */
	private static boolean isSonOf(QMPartIfc part, QMPartIfc root, Map parentMap,
			Map isSonMap,PartConfigSpecIfc configSpec) throws
			ServiceLocatorException, QMException {
//		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		//System.out.println("=====>enter isSonOf,son: " + part.getPartNumber() +
		//  " ," + "root: " + root.getPartNumber() + "," +
		//  new Timestamp(System.currentTimeMillis()));
		if (part == null || root == null) {
			return false;
		}
		String key = part.getBsoID() + "_" + root.getBsoID();
		Boolean flag = (Boolean) isSonMap.get(key);
		if (flag != null) {
			return flag.booleanValue();
		}
		if (part.getBsoID().equals(root.getBsoID())) {
			isSonMap.put(key, Boolean.TRUE);
			return true;
		}
		StandardPartService partService = (StandardPartService)
				EJBServiceHelper.getService("StandardPartService");
		Collection parentPartColl = (Collection) parentMap.get(part.getBsoID());
		if (parentPartColl == null)
		{
			parentPartColl = getParentPartsByConfigSpec(part,configSpec);
			if (parentPartColl != null) {
				parentMap.put(part.getBsoID(), parentPartColl);
			}
			else {
				isSonMap.put(key, Boolean.FALSE);
				return false;
			}
		}
		if (parentPartColl.size() != 0) {
			for (Iterator it = parentPartColl.iterator(); it.hasNext(); ) {
				Object[] obj1 = (Object[]) it.next();
				QMPartIfc parentPart = (QMPartIfc) obj1[1];
				if (isSonOf(parentPart, root, parentMap, isSonMap,configSpec)) {
					isSonMap.put(key, Boolean.TRUE);
					return true;
				}
			}
		}
		//System.out.println("=====>leave isSonOf, isOrNot: " + result +
		//  new Timestamp(System.currentTimeMillis()));
		isSonMap.put(key, Boolean.FALSE);
		return false;
	}


	/**
	 * ���ݸ���·�߻�����ĸ��ı�ǡ�
	 * �������зֽ������
	 * @param route TechnicsRouteIfc
	 * @return String
	 */
	private static String getModefyIdenty(TechnicsRouteIfc route)
	{
//		System.out.println("lllllllllllllllllllllllllllllllllllllllllllllllll");
		String desc = route.getRouteDescription();
		System.out.println("-----------------------------------------------"+desc);
		return desc.substring(3,4);
	}
	
	private static String newgetModefyIdenty(ListRoutePartLinkInfo route) throws QMException
	{
//		System.out.println("lllllllllllllllllllllllllllllllllllllllllllllllll");
//		PersistService pservice = (PersistService) EJBServiceHelper. getPersistService();
		System.out.println("--------------------------------"+route.getModifyIdenty());
//		CodingIfc code = (CodingIfc)pservice.refreshInfo(route.getModifyIdenty(), false);
//		String mi = code.getCodeContent();
//		System.out.println("-----------------------------------------------"+mi);
		return route.getModifyIdenty();
	}

	//SS4 BEGIN
	/**
	 * ���ݸ���·�ߣ��������Ӧ��״̬
	 * �ж�����״̬�Ƿ�Ϊ����׼״̬
	 * @author ������
	 * @version 1.0 
	 */

//	public static boolean isYshizhun(String routeListID)throws
//	QMException
//	{
//		boolean isYsz = false ;
//		PersistService pservice = (PersistService) EJBServiceHelper. getPersistService();
//		TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.refreshInfo(
//				routeListID, false);
//		String state = listInfo.getRouteListState();
//		if(state != null && state.equalsIgnoreCase("����׼"))
//		{
//			isYsz = true ;
//		}
//		return isYsz ;
//	}
	
	
	public static int isYshizhun(String routeListID)throws
	QMException
	{
		int isYsz = 0;
		PersistService pservice = (PersistService) EJBServiceHelper. getPersistService();
		TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.refreshInfo(
				routeListID, false);
		String state = listInfo.getRouteListState();
		//CCBegin SS8
		if(state != null && (state.equalsIgnoreCase("����")||state.equalsIgnoreCase("����׼"))){
			//CCEnd SS8
			isYsz = 1;
		}else if(state != null && state.equalsIgnoreCase("ǰ׼")){
			isYsz = 2;
		}else if(state != null && state.equalsIgnoreCase("��׼")){
			isYsz = 3;
		}else if(state != null && state.equalsIgnoreCase("��׼")){
			isYsz = 4;
		}else if(state != null && state.equalsIgnoreCase("�ձ�")){
			isYsz = 5;
		}else{
			isYsz = 6;
		}
	
		return isYsz ;
	}
	//SS4 END



	/**
	 * ���ݸ���·�ߣ��������Ӧ��״̬
	 * @author liunan 2012-5-28
	 * @version 1.0 
	 */

	public static String getRouteListType(String routeListID)throws
	QMException
	{
//		System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
		PersistService pservice = (PersistService) EJBServiceHelper. getPersistService();
		TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.refreshInfo(
				routeListID, false);
		String state = listInfo.getRouteListState();
		//System.out.println("����·�������ǣ�"+state);
		return state ;
	}
	
	
	//CCBegin SS40
  public static Collection getFirstLeveRouteListReportCD(TechnicsRouteListIfc routelist, Collection coll) throws QMException
  {
  	boolean iscomplete=false;
  	boolean expandByProduct = true;
  	if( routelist.getRouteListState().equals("�շ�"))
  	{
  		iscomplete=true;
  	}
    //CCEnd SS21
		// 1.�����ݿ���׼����ֱ��ȡ���������������
		jfuputil jf = new jfuputil();
		HashMap countMap = new HashMap();
		int tempxh = 0;
		for (Iterator it = coll.iterator(); it.hasNext(); )
		{
			ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)it.next();
			if (countMap.containsKey(link.getRightBsoID()))
			{
				//System.out.println("countmap 22��"+(link.getRightBsoID()+"K"+tempxh)+"===="+link.getProductCount());
				countMap.put(link.getRightBsoID()+"K"+tempxh, link.getProductCount());
				tempxh++;
			}
			else
			{
				//System.out.println("countmap 11��"+link.getRightBsoID()+"===="+link.getProductCount());
				countMap.put(link.getRightBsoID(), link.getProductCount());
			}
		}

		ArrayList v = new ArrayList();
		// a���Ƿ�b�����Ӽ��Ļ���,��:a��bsoID+"_"+b��bsoID,ֵ:Boolean,yanqi-20061010
		HashMap isSonMap = new HashMap();

		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();
		StandardPartService partService = (StandardPartService) EJBServiceHelper
				.getService("StandardPartService");
		TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper
				.getService("consTechnicsRouteService");
		// ��õ�ǰ�û������ù淶
		PartConfigSpecIfc configSpecIfc = getCurrentConfigSpec_enViewDefault();
		if (configSpecIfc == null || configSpecIfc.getStandard() == null
				|| configSpecIfc.getStandard().getViewObjectIfc() == null) {
			configSpecIfc = getPartConfigSpecByViewName("������ͼ");
		}
		
		//2.�ҵ���Ӧ��׼��·�ߴ�
		CodeManageTable map = routeService.getFirstLeveRouteListReport(routelist);// �ҵ���Ӧ��׼��·�ߴ�

		QMPartMasterIfc rootmaster = (QMPartMasterIfc) pservice.refreshInfo(routelist.getProductMasterID());
		// ������Ʒ�����°汾
		QMPartIfc rootPart = routeService.getLastedPartByConfig(rootmaster,
				configSpecIfc);
		Collection partIDCountMap = null;
		HashMap parentMap2000 = null;
		HashMap parentMap = new HashMap();
		HashMap parentMap1 = new HashMap();
		// ������������������150����ͳ�Ƹó��͵������Ӽ������������õ�һ�ַ���
		boolean flag = (map.size() > 150);
		System.out.println("���������" + map.size());

		Enumeration enum2 = map.keys();
		String exchangiba = "";
		String safetyiba = "";
		String docnoteiba = "";
		if(iscomplete)
		{
		}
		else
		{
			QMQuery query = new QMQuery("StringDefinition");
			QueryCondition qc = new QueryCondition("displayName", " = ", "Ӱ�컥��");
			query.addCondition(qc);
			Collection col = pservice.findValueInfo(query, false);
			Iterator iba = col.iterator();
			if (iba.hasNext())
			{
				StringDefinitionIfc s = (StringDefinitionIfc) iba.next();
				exchangiba = s.getBsoID();
			}
			
			QMQuery query1 = new QMQuery("StringDefinition");
			QueryCondition qc1 = new QueryCondition("displayName", " = ", "������Ҫ");
			query1.addCondition(qc1);
			Collection col1 = pservice.findValueInfo(query1, false);
			Iterator iba1 = col1.iterator();
			if (iba1.hasNext())
			{
				StringDefinitionIfc s = (StringDefinitionIfc) iba1.next();
				safetyiba = s.getBsoID();
			}
			
			QMQuery query2 = new QMQuery("StringDefinition");
			QueryCondition qc2 = new QueryCondition("displayName", " = ", "�ĵ���Ϣ");
			query2.addCondition(qc2);
			Collection col2 = pservice.findValueInfo(query2, false);
			Iterator iba2 = col2.iterator();
			if (iba2.hasNext())
			{
				StringDefinitionIfc s = (StringDefinitionIfc) iba2.next();
				docnoteiba = s.getBsoID();
			}
		}
			
			ArrayList partlist = new ArrayList();
			ArrayList partmasterList = new ArrayList();
			ArrayList allpartmasterList = new ArrayList();
			HashMap masterlinkmap = new HashMap();
			ListRoutePartLinkIfc listPartRoute=null;
			QMPartMasterIfc partmaster=null;
			ArrayList partholer = new ArrayList();
			while (enum2.hasMoreElements())
			{
				Object[] obj = null;
				Object o = enum2.nextElement();
				if(o instanceof QMPartMasterIfc)
				{
					partmaster = (QMPartMasterIfc) o;
					obj = (Object[])map.get(partmaster);
				}
				else if(o instanceof QMPartIfc)
				{
					QMPartIfc part = (QMPartIfc) o;
					partmaster = (QMPartMasterIfc) pservice.refreshInfo(part.getMasterBsoID());
					obj = (Object[])map.get(part);
				}
				listPartRoute =(ListRoutePartLinkIfc)obj[1];
				partmaster = listPartRoute.getPartMasterInfo();
				QMPartIfc part1= (QMPartIfc) pservice.refreshInfo(listPartRoute.getRightBsoID());
				if(part1!=null)
				{//���ListRoutePartLinkIfc�����������ֱ�Ӵ�����ȡ
					if (part1.getPartNumber().startsWith("Q") || part1.getPartNumber().startsWith("CQ"))
					{
						flag = true;//����б�׼��������õ�һ�ַ���
					}
					String masterid = part1.getMasterBsoID();
					masterlinkmap.put(masterid, part1);
					partholer.add(part1.toString());
				}
				else
				{
					partmasterList.add(partmaster); //��partmaster�ŵ������й�һ���Ե������ݿ�
				}
				allpartmasterList.add(partmaster);
				listPartRoute=null;
				partmaster=null;
			}
			enum2 = null;
			//3.��ȡ�����������
			Collection lastedpart = jf.getLastedPartByConfig(partmasterList, configSpecIfc);
			partlist.addAll(lastedpart);
			// End ��ȡ�����������
			
			// begin ��ȡ���id iba
			for (int j = 0; j < partlist.size(); j++)
			{
				QMPartIfc last = (QMPartIfc) ((Object[]) partlist.get(j))[0];
				if (last.getPartNumber().startsWith("Q") || last.getPartNumber().startsWith("CQ"))
				{
					flag = true;//����б�׼��������õ�һ�ַ���
				}
				String masterid = last.getMasterBsoID();
				masterlinkmap.put(masterid, last);
				partholer.add(last.toString());
			}
			partlist.clear();
			
			
      //4.��ȡ���id 
      HashMap returnList=null;
      HashMap returnSafetyList = null;
      HashMap returnDocnoteList = null;
      if(!iscomplete)
      {
      	returnList = jf.getiba(partholer, exchangiba);
      	returnSafetyList = jf.getiba(partholer, safetyiba);
      	returnDocnoteList = jf.getiba(partholer, docnoteiba);
      }
      partholer.clear();
      // end ��ȡ���id iba
      
      
      //5.�Ҹ���
      //�ձ��������ʱ����Ҫ������Ϣ
      ArrayList parentpartlist = null;
      if(!iscomplete)
      {
      	if (expandByProduct && flag)
      	{
      		// �����ָ�������µ������Ӳ�������һ�ַ����Ҹ���
      		ArrayList rootPartlist = new ArrayList();
      		parentMap2000 = new HashMap();
      		rootPartlist.add(rootPart.getBsoID());
      		jf.getSubParts(rootPartlist, parentMap2000, configSpecIfc);
      		rootPartlist.clear();
      	}
      	
      	// ��ȡ����������
      	if (expandByProduct && flag)
      	{
      	}
      	else
      	{
      		//�ڶ��ַ����Ҹ���
      		parentpartlist = jf.getparentparts(allpartmasterList, parentMap, rootPart, configSpecIfc);
      	}
      }
      
      allpartmasterList=null;
      parentpartlist = null;
      int i = 0;
      ArrayList informationlist = null;
      StringBuffer remark = null;
      String change = null;
      String countInProduct = null;
      String version = null;
      
      
      //ѭ������ÿ�������㲿��
      //6.ÿ�������㲿������������װ
      Enumeration enum3 = map.keys();
      String masterID=null;
      QMPartIfc part=null;
      ArrayList yblist = null;
      if(iscomplete)
      {
      	ArrayList rootPartlist = new ArrayList();
      	HashMap ybmap = new HashMap();
      	rootPartlist.add(rootPart.getBsoID());
      	yblist = jf.getSubParts(rootPartlist, ybmap, configSpecIfc);
      }
      while (enum3.hasMoreElements())
      {
				Object[] obj = null;
				Object o = enum3.nextElement();
				if(o instanceof QMPartMasterIfc)
				{
					partmaster = (QMPartMasterIfc) o;
					obj = (Object[])map.get(partmaster);
				}
				else if(o instanceof QMPartIfc)
				{
					QMPartIfc part1 = (QMPartIfc) o;
					partmaster = (QMPartMasterIfc) pservice.refreshInfo(part1.getMasterBsoID());
					obj = (Object[])map.get(part1);
				}
				listPartRoute =(ListRoutePartLinkIfc)obj[1];
				partmaster = listPartRoute.getPartMasterInfo();
				masterID = partmaster.getBsoID();
				part=(QMPartIfc) pservice.refreshInfo(listPartRoute.getRightBsoID());
				if(part==null)
				{
					part = (QMPartIfc) masterlinkmap.get(masterID);
				}
				if (part == null)
				{
					continue;
				}
				String s = (String)obj[0];
				String partMakeRoute = "";;
				String myAssRoute = "";
				String sub = "";
				sub = s.substring(1, s.length()-1);
				String str[] = sub.split(",");
				for(int e = 0;e<str.length;e++)
				{
					if(sub.contains("="))
					{
						//CCBegin SS40
						if(str[e].endsWith("="))
						{
							str[e] = str[e] + "��";
						}
					//CCEnd SS40
						String str1[] = str[e].split("=");
						if(e==str.length-1)
						{
							if(str1.length>1)
							{
								partMakeRoute += str1[0];
								if(str1[1].equals("��"))
								{
									str1[1]="";
								}
								myAssRoute += str1[1];
							}
							else if(str1.length==1)
							{
								partMakeRoute += str1[0];
 								myAssRoute += " ";
							}
							else
							{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
							System.out.println(str1[0]+"=======111======"+str1[1]);
							String[] makes = str1[0].split("-");
							for(int f = 0;f<makes.length;f++)
							{
								System.out.println("=======111 makes======"+makes[f]);
								if (!sendToColl.contains(makes[f]) && makes[f] != "")
								{
									sendToColl.add(makes[f]);
								}
							}
							if (!sendToColl.contains(str1[1]) && str1[1] != "")
							{
								sendToColl.add(str1[1]);
							}
						}
						else
						{
							if(str1.length>1)
							{
								partMakeRoute += str1[0]+"/";
								if(str1[1].equals("��"))
								{
									str1[1]="";
								}
								myAssRoute += str1[1]+"/";
							}
							else if(str1.length==1)
							{
								partMakeRoute += str1[0]+"/";
								myAssRoute += " "+"/";
							}
							else
							{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
							System.out.println(str1[0]+"=======222======"+str1[1]);
							if (!sendToColl.contains(str1[0]) && str1[0] != "")
							{
								sendToColl.add(str1[0]);
							}
							if (!sendToColl.contains(str1[1]) && str1[1] != "")
							{
								sendToColl.add(str1[1]);
							}
						}
					}
					else if(sub.contains("@"))
					{
						String str1[] = str[e].split("@");
						if(e==str.length-1)
						{
							if(str1.length>1)
							{
								partMakeRoute += str1[0];
								if(str1[1].equals("��"))
								{
									str1[1]="";
								}
								myAssRoute += str1[1];
							}
							else if(str1.length==1)
							{
								partMakeRoute += str1[0];
								myAssRoute += " ";
							}
							else
							{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
						else
						{
							if(str1.length>1)
							{
								partMakeRoute += str1[0]+"/";
								if(str1[1].equals("��"))
								{
									str1[1]="";
								}
								myAssRoute += str1[1]+"/";
							}
							else if(str1.length==1)
							{
								partMakeRoute += str1[0]+"/";
								myAssRoute += " "+"/";
							}
							else
							{
								partMakeRoute += " ";
								myAssRoute += " ";
							}
						}
					}
				}
				
				// //////����ÿ��������////////////////////
				countInProduct = "";
				String masterID1 = part.getBsoID();
				//System.out.println("masterID1:"+masterID1);
        if (countMap.containsKey(masterID1))
        {
        	countInProduct = countMap.get(masterID1).toString();
        	countMap.remove(masterID1);
        }
        else
        {
        	String keya = null;
        	for (Iterator jt = countMap.keySet().iterator(); jt.hasNext();)
        	{
        		keya = (String)jt.next();
        		if(keya==null)
        		{
        			continue;
        		}
        		if (keya.startsWith(masterID1))
        		{
        			masterID1 = keya;
        			countInProduct = countMap.get(masterID1).toString();
        			countMap.remove(masterID1);
        			break;
        		}
        	}
        }
        if (countInProduct.trim().equals("0"))
        {
        	countInProduct = "";
        }
        //System.out.println("countInProduct===="+countInProduct);
        // ////////��ÿ���������������////////////////////
        
        // //���ݽ��Ҫ��·������"��"���ں���////////////
        if (partMakeRoute.indexOf("��") != -1 && (!partMakeRoute.endsWith("��")))
        {
        	String sortedRoute = "";
        	StringTokenizer yyy = new StringTokenizer(partMakeRoute, "/");
        	while (yyy.hasMoreTokens())
        	{
        		String haha = yyy.nextToken();
        		if (!haha.equals("��"))
        		{
        			sortedRoute += haha + "/";
        		}
        	}
        	sortedRoute += "��";
        	partMakeRoute = sortedRoute;
        }
        
        // ����Ԫ�أ��ֱ�Ϊ��װ��·�ߡ������ϼ��������͡��ϼ����š�
        informationlist = new ArrayList();
        String[] informationStr = null;
        // �������á�����������С�װ��ͼ���Ĳ����ָ��� liuming 20061212
        if(iscomplete)
        {
        	// ���û��װ��·��,��Ѱ�Ҹ���������·��
        	if (myAssRoute == null || myAssRoute.equals(""))
        	{
        		// �����߼��ܳɻ�������·�ߺ�"�õ�",�Ͳ���ȡ����������·����
        		if (part.getPartType().toString().equalsIgnoreCase("Logical") || (partMakeRoute.indexOf("��") != -1))
						{
							informationStr = new String[3];
							informationStr[0] = myAssRoute;
							informationStr[1] = "";
							informationStr[2] = "";
							informationlist.add(informationStr);
							informationStr = null;
						}
						else
						{
							Collection parentcollection = getParentPartsByConfigSpec(part, configSpecIfc);
							if(parentcollection!=null)
							{
								Iterator i1=parentcollection.iterator();
								while(i1.hasNext())
								{
									QMPartIfc parentpart=(QMPartIfc)(((Object[])i1.next())[1]);
									if(!yblist.contains(parentpart.getBsoID()))
									{
										continue;
									}
									myAssRoute = getAssisRoute(parentpart);
									if(myAssRoute == null || myAssRoute.equals(""))
									{
										continue;
									}
									// ��·�ߵ�λ���뷢����λ��.
									StringTokenizer qq = new StringTokenizer(myAssRoute, "/");
									while (qq.hasMoreTokens())
									{
										String departmentName = qq.nextToken();
										System.out.println("=======333======"+departmentName);
										if (!sendToColl.contains(departmentName) && departmentName != "")
										{
											sendToColl.add(departmentName);
										}
									}
									qq = null;
									informationStr = new String[3];
									informationStr[0] = myAssRoute;
									informationStr[1] = "";
									informationStr[2] = "";
									informationlist.add(informationStr);
								}
							}
						}
					}
			}
			else if (partMakeRoute == "" || ((partMakeRoute.indexOf("��") != -1) && partmaster.getPartName().indexOf("װ��ͼ") != -1))
			{
				informationStr = new String[3];
				informationStr[0] = myAssRoute;
				informationStr[1] = "";
				informationStr[2] = "";
				informationlist.add(informationStr);
				informationStr = null;
			}
			else
			{
				String parentPartList = null;
				if (expandByProduct && flag)
				{
					parentPartList = (String) parentMap2000.get(part.getMasterBsoID());
				}
				else
				{
					parentPartList = (String) parentMap.get(masterID);
				}
				
				//map32: key=partID value=�ϼ�����
				HashMap map32 = new HashMap();
				if (parentPartList != null)
				{
					StringTokenizer yyy = new StringTokenizer(parentPartList, ";");
					while (yyy.hasMoreTokens())
					{
						String parentcount = yyy.nextToken();
						StringTokenizer yy = new StringTokenizer(parentcount, "...");
						if (yy.hasMoreTokens())
						{
							String parepartid = yy.nextToken();
							String partcount = yy.nextToken();
							if(map32.get(parepartid)!=null)
							{
								//System.out.println("----�ж�����������");
								int countnum1=Integer.parseInt(partcount);
								String count2=(String)map32.get(parepartid);
								int countnum2=Integer.parseInt(count2);
								int partcountnum=countnum2+countnum1;
								partcount=String.valueOf(partcountnum);
								//System.out.println("---��������"+partcount);
							}
							map32.put(parepartid, partcount);
						}
						yy = null;
					}
					yyy = null;
				}
				parentPartList=null;

				Collection keySet = map32.keySet();
				for (Iterator it = keySet.iterator(); it.hasNext();)
				{
					String partID = (String) it.next();
					String count = (String) map32.get(partID);
					QMPartIfc parentpart = (QMPartIfc) pservice.refreshInfo(partID);
					// �������չ����������������ڸó��ϣ�����ʾ�˸�������Ϣ
					if (expandByProduct)
					{
						if (flag)
						{
							//������������2000����ֱ�Ӵ�rootչ���ĵĽṹ�����Ƿ��иü�
							// if (parentMap2000.get(partID) == null) {
							// continue;
							// }
						}
						else if(!isSonOf(parentpart, rootPart, parentMap1, isSonMap, configSpecIfc))
						{
							continue;
						}
					}
					// ���û��װ��·��,��Ѱ�Ҹ���������·��
					if (myAssRoute == null || myAssRoute.equals(""))
					{
						// �����߼��ܳɻ�������·�ߺ�"�õ�",�Ͳ���ȡ����������·����
						if (part.getPartType().toString().equalsIgnoreCase("Logical") || (partMakeRoute.indexOf("��") != -1))
						{
						}
						else
						{
							myAssRoute = getAssisRoute(parentpart);
							// ��·�ߵ�λ���뷢����λ��.
							StringTokenizer qq = new StringTokenizer(myAssRoute, "/");
							while (qq.hasMoreTokens())
							{
								String departmentName = qq.nextToken();
								System.out.println("=======444======"+departmentName);
								if (!sendToColl.contains(departmentName) && departmentName != "")
								{
									sendToColl.add(departmentName);
								}
							}
							qq = null;
						}
					}
					informationStr = new String[3];
					informationStr[2] = parentpart.getPartNumber();
					informationStr[1] = count;
					informationStr[0] = myAssRoute;
					informationlist.add(informationStr);
					informationStr = null;
				}
				map32.clear();
			}

			if (informationlist.size() == 0)
			{
				informationStr = new String[3];
				informationStr[0] = myAssRoute;
				informationStr[1] = "";
				informationStr[2] = "";
				informationlist.add(informationStr);
				informationStr = null;
			}

			// ���ø��ı�ʶ�ͱ�ע
			TechnicsRouteIfc route = null;
			change = "";
			remark = new StringBuffer("");
			if (listPartRoute != null)
			{
				String routeID = listPartRoute.getRouteID();
				if (routeID != null)
				{
					route = (TechnicsRouteIfc) pservice.refreshInfo(routeID, false);
				}
			}
			//if (route != null)
			{
				String modifyidenty = listPartRoute.getModifyIdenty();
				//System.out.println("modifyidenty==="+modifyidenty);
				if(modifyidenty==null)
				{
					modifyidenty = "";
				}
				if(modifyidenty.equals("����"))
				{
					change = "C";
				}
				else if(modifyidenty.equals("����"))
				{
					change = "X";
				}
				else if(modifyidenty.equals("��ͼ"))
				{
					change = "G";
				}
				else if(modifyidenty.equals("ȡ��"))
				{
					change = "Q";
				}
				else if(modifyidenty.equals("����"))
				{
					change = "F";
				}
				/*String des1 = route.getDefaultDescreption();
				if (des1 == null)
				{
					des1 = "";
				}
				remark.append(des1);*/
				String des2 = listPartRoute.getRouteDescription();
				if (des2 != null && des2.startsWith("(") && des2.indexOf(")") != -1)
				{
					des2 = des2.substring(des2.indexOf(")") + 1, des2.length());
				}
				if (des2 == null || des2.equals(""))
				{
				}
				else
				{
					remark.append(des2);
				}
			}
			version = "";
			String partNum = "";
			// ���ڱ����Q,CQ,T��ͷ�ı�׼������ʾ�汾��
			partNum = part.getPartNumber();
			if (partNum.startsWith("Q") || partNum.startsWith("CQ") || partNum.startsWith("T"))
			{
			}
			else
			{
				if (route != null && route.getRouteDescription() != null)
				{
					String routeStr1 = route.getRouteDescription();
					if (routeStr1 != null && routeStr1.startsWith("(") && routeStr1.indexOf(")") != -1)
					{
						version = routeStr1.substring(routeStr1.indexOf("(") + 1, routeStr1.indexOf(")"));
					}
				}
			}

			if (version.trim().equals(""))
			{
			}
			else
			{
				version = "/" + version;
			}
			
			String exchangvalue="";
			String safetyvalue = "";
			String docnotevalue = "";
			String colorFlag = "";
			if(iscomplete)
			{
				//����֪ͨ�� ʱ exchangvalue ��Ӧ���� �����ó��͡�
				if( routelist.getRouteListState().equals("�շ�"))
				{
					//�����㲿��������� ��׼ ��š�
					Collection trlcol = (Collection)routeService.getListsByPart(part.getMasterBsoID());
					for (Iterator ittrl = trlcol.iterator(); ittrl.hasNext(); )
					{
						TechnicsRouteListIfc trl = (TechnicsRouteListIfc) ittrl.next();
						if(trl.getRouteListState().equals("��׼"))
						{
							exchangvalue = trl.getRouteListNumber();
							break;
						}
					}
				}
			}
			else
			{
				exchangvalue = (String) returnList.get(part.getBsoID());
				safetyvalue = (String) returnSafetyList.get(part.getBsoID());
				docnotevalue = (String) returnDocnoteList.get(part.getBsoID());
				if(docnotevalue==null)
				{
					docnotevalue = "";
				}
				if(!docnotevalue.equals(""))
				{
					docnotevalue = "("+docnotevalue+")";
				}
			}
			
			if(listPartRoute.getColorFlag()!=null&&listPartRoute.getColorFlag().trim().equals("1"))
			{
				colorFlag="��";
			}
			
			//��š����ı�ǡ���š��汾������+��xxx������������·�ߡ���װ��·�ߡ���������������bsoID����ע��Ӱ�컥����������������Ҫ
			Object[] arrayObjs = { String.valueOf(++i), change,
					partmaster.getPartNumber(), version,
					partmaster.getPartName()+docnotevalue, countInProduct, partMakeRoute,
					informationlist, part.getBsoID(), remark.toString(),
			        exchangvalue,"", safetyvalue, colorFlag};
			v.add(arrayObjs);
			listPartRoute=null;
			partmaster=null; 
			masterID=null;
			part=null;
		}
		enum3=null;
		masterlinkmap = null;
		countMap.clear();
		parentMap = null;
		parentMap2000=null;
		parentMap1=null;
		partmasterList=null;
		isSonMap=null;
		map.clear();
		returnList=null;
		informationlist = null;
		return v;
	}
	
	
	private static String[] getPartMakeAndAssRouteInRouteList(ListRoutePartLinkIfc listPartRoute, CodeManageTable map, ArrayList sendToColl) throws QMException
	{
		Object[] branches = ( (Collection) map.get(listPartRoute)).toArray();
		String makeStr = "";
		String assStr = "";
		ArrayList makeList = new ArrayList(); //����·�߼���,Ϊ�˱�֤�ѳ��ֵ�����ڵ㲻�ٳ���,�� ��/��---->��
		ArrayList assList = new ArrayList(); //ͬ��
		if (sendToColl == null)
		{
			sendToColl = new ArrayList();
		}
		if (branches != null && branches.length > 0)
		{
			for (int j = 0; j < branches.length; j++)
			{
				if (makeStr.length() > 0 && !makeStr.endsWith("/"))
				{
					makeStr += "/";
				}
				if (assStr.length() > 0 && !assStr.endsWith("/"))
				{
					assStr += "/";
				}
				//�����������·��,·�ߴ�·�ߴ�����ȡ
				String routeString=(String)branches[j];
				String makeStrBranch="";
				String assDepartment="";
				if(routeString!=null&&routeString.length()>0)
				{
          String route[]= routeString.split(";");
          for(int k=0;k<route.length;k++)
          {
        	  String r=route[k];
        	  int s=r.indexOf("@");
        	  String  longdepartmentName=r.substring(0,s);
        	  if(longdepartmentName.equals("��"))
        	  {
        	  	longdepartmentName="";
        	  }
        	  //System.out.println("���쵥λ��"+longdepartmentName);
        	  longdepartmentName = longdepartmentName.replaceAll("-","��");
        	  String [] de=longdepartmentName.split("��");
        	  for(int i=0;i<de.length;i++)
        	  {
        	  	String departmentName=de[i];
        	  	if (makeStrBranch == "")
        	  	{
        	  		makeStrBranch = makeStrBranch + departmentName;
        	  	}
        	  	else
        	  	{
        	  		makeStrBranch = makeStrBranch + "-" + departmentName;
        	  	}
        	  	if (!sendToColl.contains(departmentName) && departmentName != "")
        	  	{
        	  		sendToColl.add(departmentName);
        	  	}
        	  }
        	  if (!makeList.contains(makeStrBranch))
        	  {
              makeList.add(makeStrBranch);
              makeStr = makeStr + makeStrBranch + "/";
            }
            
            assDepartment=r.substring(s+1,r.length());
            if(assDepartment.equals("��"))
            {
            	assDepartment="";
            }
            if (!sendToColl.contains(assDepartment) && assDepartment != "")
            {
            	sendToColl.add(assDepartment);
            }
          }
          if (!assList.contains(assDepartment))
          {
          	assList.add(assDepartment);
          	assStr = assStr + assDepartment + "/";
          }
        }
      }
    }
    makeStr = makeStr.endsWith("/") ? makeStr.substring(0, makeStr.length() - 1) : makeStr;
    String[] ss = assStr.split("/");
    int cc = ss.length;
    if(cc==0)
    {
    	assStr = "";
    }
    for(int ii=0;ii<cc;ii++)
    {
    	assStr = assStr.endsWith("/") ? assStr.substring(0, assStr.length() - 1) : assStr;
    	assStr = assStr.startsWith("/") ? assStr.substring(1, assStr.length()) : assStr;
    }
    String[] aaaa = new String[2];
    aaaa[0] = makeStr;
    aaaa[1] = assStr;
    makeList = null;
    assList = null;
    return aaaa;
  }
}
