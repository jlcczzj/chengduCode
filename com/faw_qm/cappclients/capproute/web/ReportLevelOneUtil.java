/**
 * ���ɳ��� ReportLevelOneUtil.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���ݽ��Ҫ����׼��ǰ׼����׼������׼�ı�����Ҫ���ӡ�������Ҫ�����Ե���ʾ�� liunan 2013-6-14
 * SS2 ��Щ��׼������һ��δ�������̣���˵ò���������Ϣ����������ʱ����δ�������̡� liunan 2013-8-22
 * SS3 ��׼�����һ�ֻ�ȡ���������õ������ṹ�ķ�����map��key��bsoid�����������׼����ӵĽṹ�㲿�����Ǿɰ汾��
 * ���޷��õ��ϼ���,���ڽ�key�ĳ�masterbsoid��liunan 2013-10-15
 * SS4 ·����ЭЭ�Ĳ��ҷ�������������Ż��㲿���ţ���ͼ��+4�� liunan 2014-2-8
 * SS5 �ָ�����·���õġ�������Щ�ط��������-����������·��������ظ�·�ߣ�����ר-�ᣬ�ᡣ liunan 2014-4-14
 * SS6 ����·����ʾ��������·�ߵ�λ��ǰ liuyang 2014-6-11
 * SS7 ���ӡ���ɫ����ʶ�� liuyang 2014-6-13
 * SS8 ����׼���ڸ������ϼ�ʱ����ɫ����ʶ�� ��ʾ��������ԭ��������λ�������⣬��jsp���λ�ô���һλ�������� 2014-6-13
 * SS9 �����û������ı����㲿����š��汾�ֿ������С� liunan 2014-11-3
 * SS10 �޸�װ��·�߿�����·�ߵ�λ��ǰ���򲻶����⡣ xianglx 2014-12-9
 * SS11 A004-2014-3018 ������֮ȡ��������׼����·���еĺ��� liunan 2014-12-12
 * SS12 ����㲿��iba���ԡ��ĵ���Ϣ����Ϊ�գ������������㲿�����ƺϲ���ʾ����xxͼֽ����Ϣ���ļ��������ˣ���ǰ��д���㲿������� liunan 2014-12-15
 * SS13 ��׼֪ͨ���ڸ������ϼ���·����ʾ��׼ȷ(�ϲ���һ����)�� xianglx 2014-12-15
 * SS14 ����ɫ����ʶ���洢��λ�ò���ȷ�� xianglx 2014-12-15
 * SS15 A004-2014-3074 �շϲ���Ҫ���㷢ͼ�������ڳ���2S��3S��ͷ�ı�ţ������׼���������㲿������Ҫ�޸������жϷ�ʽ�� liunan 2015-1-4
 * SS16 ��·�߱���������·����װ��·�߶�Ӧ���ң�����ȡ��SS6���޸� pante 2015-01-06
 * SS17 ������·�ߡ���(��)���͡���(��)��ҲҪ������(׼)����һ��ͼֽ liunan 2015-1-7
 * SS18 ����SS15���жϳ���null�Ĵ��� liunan 2015-1-22
 * SS19 �����󣬹̶�������1�����ٷ���ó�׹�˾���������Ĳ��ٷ�ͼ���ൺ�������ĵ��� liunan 2015-1-22
 * SS20 ����A004-2015-3108�������󣬱���汾��ʾ�¹��򣬵�������к���/��ʱ����ϼ�������Ϊ������š������ٸ��Ӱ汾�� liunan 2015-3-16
 * SS21 A004-2015-3161 ·��״̬��ͬ������ͬ���޷���ȷ��Ӧ���ͻ����뱨��һ�¡� liunan 2015-7-8
 * SS22 A004-2016-3290 �������·����ʾ��б�ܡ�/��������� liunan 2016-1-25
 * SS23 A004-2016-3340 �㲿����������鿴����ʱ����ʾ���ݲ�һ�¡��������ÿ��ˢ�������ݡ� liunan 2016-4-12
 * SS24 �շϱ��������ָ�롣 liunan 2016-5-19
 * SS25 �������ݷ���ʱ������������Administrator�û�����˰�������ģ�浼���� liunan 2016-8-30
 * SS26 A004-2016-3434 ��ͼ����������� liunan 2016-10-31
 * SS27 �߼��ܳ��жϹ������ӵ���λ��G�������жϡ� liunan 2016-11-19
*/
package com.faw_qm.cappclients.capproute.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.util.CodeManageTable;
import com.faw_qm.config.util.ConfigSpec;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.part.ejb.extendedService.ExtendedPartService;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.pcfg.family.model.GenericPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.users.model.ActorInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.workflow.WfException;
import com.faw_qm.workflow.engine.ejb.service.WfEngineHelper;
import com.faw_qm.workflow.engine.model.WfActivityIfc;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.workflow.work.model.WfBallotInfo;
import com.faw_qm.workflow.work.model.WorkItemIfc;
import com.jf.util.jfuputil;

//CCBegin SS9
import com.faw_qm.users.model.GroupIfc;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.users.ejb.service.UsersService;
//CCEnd SS9

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
//  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
  //  private static TechnicsRouteListIfc myRouteList;
    private static Object lock = new Object(); //yanqi-20060918
    private static boolean expandByProduct;
//  CCBeginby leixiao 2009-1-6 ԭ�򣺽����������·��,sendToColl������Ϊ��̬����
   // private static Collection sendToColl;
//  CCEndby leixiao 2009-1-6 ԭ�򣺽����������·��
    private static final Map signMap = new HashMap(4);
    static {
        signMap.put("����", "C");
        signMap.put("����", "X");
        signMap.put("��ͼ", "G");
        signMap.put("ȡ��", "Q");
        //CCBegin by liunan 2011-08-30 ���ӷ�����F��
        signMap.put("����", "F");
        //CCEnd by liunan 2011-08-30
      }
//  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
//  CCBeginby leixiao 2009-1-6 ԭ�򣺽����������·��
    private static final Map typeMap = new HashMap(4);
    static {
    	typeMap.put("����", "�� �� �� �� ׼ �� ͨ ֪ ��");
    	typeMap.put("ǰ׼", "�� ǰ �� �� ׼ �� ͨ ֪ ��");
    	typeMap.put("��׼", "�� ʱ �� �� ׼ �� ͨ ֪ ��");
    	typeMap.put("��׼", "�� �� ׼ �� ͨ ֪ ��");
    	typeMap.put("�ձ�", "�� �� ׼ �� �� �� ͨ ֪ ��");
    	//CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
    	typeMap.put("�շ�", "�� �� �� �� ͨ ֪ ��");
    	//CCEnd by liunan 2011-09-21
      }
//  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
    //CCBegin by leixiao 2010-6-2 �����׼���ӷ�ͼ������
    private static  Map tucountMap = null;
    //CCEnd by leixiao 2010-6-2 �����׼���ӷ�ͼ������

    //CCBegin by liunan 2009-02-22 
    //ȫ�ֱ������ϣ����ڼ�¼��ǰʱ����������׼����ָȫ�����ɲ�Ҫ����ϸ���뵽���У�
    //���ɵ���׼��ʶ����׼bsoid�ڿ�ʼ������ϸǰ���뼯���У���������ϸ�����뵽���ݿ���в����ؽ����
    //�Ӽ������Ƴ��������û��Լ����д��ڵ���׼���ɱ���ʱ�������д����ؿգ���ʾ�û��Ժ�������
    //��׼���״λ������汾�����ɱ���ʱҪ�������㴦������ϸȻ�󱣴浽���ݿ���У�֮�󶼴ӱ���ֱ�ӻ�ȡ��
    public static ArrayList checkReport = new ArrayList();
    //CCEnd by liunan 2009-02-22

  public ReportLevelOneUtil() {
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
      //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
      //myRouteList = routelist;
      //  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
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
  
  //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
    /**
     * ��ý���ͷ����Ϣ
     * @param routeListID ·�߱��BsoID
     * @return ·�߱�ı�š����ơ���Ʒ������
     */

    public static String[] getHeader(TechnicsRouteListIfc routelist) throws
        QMException {
      PersistService pService = (PersistService) EJBServiceHelper.getService(
          "PersistService");
      try {

        String name = routelist.getRouteListName();
        QMPartMasterIfc partmaster = (QMPartMasterIfc) pService.refreshInfo(
            routelist.getProductMasterID());
        String product = routelist.getRouteListNumber();
        Timestamp stamp = routelist.getCreateTime();
        String year = String.valueOf(stamp.getYear() + 1900);
        String month = String.valueOf(stamp.getMonth() + 1);
        String day = String.valueOf(stamp.getDate());
        String date = year + "��" + month + "��" + day + "��";
//      CCBeginby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ�
        String type=routelist.getRouteListState();
       // System.out.println("type="+type);
        String title=(String)typeMap.get(type);
//      CCBeginby leixiao 2009-3-4 ԭ�򣺽����������·��,��ͷ�����ͳ�       
        String[] c = {
            name, product, date, partmaster.getPartNumber(),title};
        return c;
      }
      catch (Exception ex) {
        ex.printStackTrace();
        return null;
      }
    }

//  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��

  /**
   * ���ָ��·�߱��һ��·�߱��������
   *
   * @return ���ؼ��ϵ�Ԫ��Ϊ����arrayObjs����Ԫ������Ϊ��š��㲿����š��㲿������
   *         һ��·�ߴ����ַ�������array�ļ��ϡ�array[0]Ϊ����·�ߴ���array[1]Ϊװ��·�ߴ���
   */
  /*public static Collection getFirstLeveRouteListReport() {
    Vector v = new Vector();
    Vector resultVector = new Vector();
    try {
      TechnicsRouteService routeService = (TechnicsRouteService)
          EJBServiceHelper
          .getService("TechnicsRouteService");
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
  }*/
  //  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��,����"��������"
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
                                       Map theparentMap, Map useQuanMap,
                                       PartConfigSpecIfc configSpec) throws
        ServiceLocatorException, QMException {

    	//System.out.println("---------getUseQuantity--"+part+"----root="+root);

      Float f = (Float) useQuanMap.get(part.getBsoID());
      if (f != null) {
        return f.floatValue();
      }

      if (part.getBsoID().equals(root.getBsoID())) {
        return 1f;
      }


      float useCount = 0f;
      Collection parentPartColl = (Collection) theparentMap.get(part.getBsoID());
      if (parentPartColl == null) {

        //�����ݿ���Ѱ��ʹ����(partIfc����Ӧ��)PartMasterIfc��
        //���еĸ���(QMPartIfc)������ֵ��������(PartUsageLinkIfc, QMPartIfc)ΪԪ�صļ��ϡ�
    	  List tempList=null;
       parentPartColl = getParentPartsByConfigSpec(part, configSpec);
  //      HashMap parentPart=new HashMap();
//        StandardPartService partService = (StandardPartService)
//        EJBServiceHelper.getService("StandardPartService");
//    	parentPart = partService.getParentPartsFromProduct((QMPartMasterIfc)root.getMaster(),(QMPartMasterIfc)part.getMaster());

        if (parentPartColl != null) {
          theparentMap.put(part.getBsoID(), parentPartColl);
        }
        else {
          //�����useCount=0
          useQuanMap.put(part.getBsoID(), Float.valueOf(useCount + ""));
          return useCount;
        }
      }

      //����и���
      if (parentPartColl.size() != 0) {
    	 // System.out.println("-----parentPartColl.size="+parentPartColl.size());
        for (Iterator it = parentPartColl.iterator(); it.hasNext(); ) {
          Object[] obj1 = (Object[]) it.next();
          PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) obj1[0];
          QMPartIfc parentPart = (QMPartIfc) obj1[1];
    //      System.out.println("usageLinkIfc="+usageLinkIfc+" parentPart="+parentPart);
          useCount = useCount + usageLinkIfc.getQuantity() *
              getUseQuantity(parentPart, root, theparentMap, useQuanMap,
                             configSpec);
        }
      }
      useQuanMap.put(part.getBsoID(), Float.valueOf(useCount + ""));
     // System.out.println("useCount="+useCount);

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
        	//  System.out.println("%%%%%%%%%%%%%%%%"+array[i]);
            result.addElement(array[i]);
          }
        }

        PersistService pService = (PersistService) EJBServiceHelper.getService(
            "PersistService");
        Vector resultVector = new Vector();
        for (int i = 0; i < result.size(); i++) {
          QMPartIfc parentPartIfc = (QMPartIfc) result.elementAt(i);

          TechnicsRouteService routeService = (TechnicsRouteService)
              EJBServiceHelper.getService("TechnicsRouteService");
          QMPartIfc newPart = routeService.getLastedPartByConfig( (
              QMPartMasterIfc) parentPartIfc.getMaster(), partConfigSpecIfc);
          //�ж��Ƿ������°汾,ȥ����������С�汾��Part
          if (parentPartIfc.getBsoID().equals(newPart.getBsoID())) {
            //leftBsoID�Ǳ�ʹ�õ��㲿����QMPartMaster��BsoID
            //rightBsoID��ʹ�����㲿����BsoID::
            String leftBsoID = partIfc.getMasterBsoID();
            String rightBsoID = parentPartIfc.getBsoID();
            Collection coll = null;
            //��Ҫ����leftBsoID��rightBsoID�ҵ�PartUsageLinkIfc����Ӧ��ֻ��һ��������һ��ֻ��һ������Ϊ�ж�����ͬһ�Ӽ������ skx��
            if (rightBsoID.startsWith("GenericPart")) {
              coll = pService.findLinkValueInfo("GenericPartUsageLink",
                                                leftBsoID,
                                                "uses", rightBsoID);
            }
            else {
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
             //   System.out.println("�Ҹ���---usageLinkIfc=-"+usageLinkIfc+" parentPartIfc="+parentPartIfc);
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

      PersistService pService = (PersistService) EJBServiceHelper.getService(
          "PersistService");
      //���صĽ������:
      Vector resultVector = new Vector();
      QMPartMasterIfc masterIfc = (QMPartMasterIfc) partIfc.getMaster();
      QMQuery qmQuery = new QMQuery("QMPart", "PartUsageLink");
      qmQuery = configSpec.appendSearchCriteria(qmQuery);
      //���ݲ�ѯ���������Ӧ�Ľ����:
      //CCBegin by liunan 2009-02-19
      Collection colAll = pService.navigateValueInfo(masterIfc, "uses", qmQuery, true);
      //CCEnd by liunan 2009-02-19
      Collection resultCollection = null;
   //   if (colAll != null || colAll.size() > 0) {
        if (colAll != null && colAll.size() > 0) {//leix
    	 //CCBegin by liunan 2009-02-19
        //resultCollection = configSpec.process(colAll);
        resultCollection = configSpec.process(colAll);
    	 //CCEnd by liunan 2009-02-19

      }
      //������i����:
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
//  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��,����"��������"

//  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
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
          ViewObjectIfc view = viewService.getView("������ͼ");
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
//  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��

	// CCBeginby leixiao 2009-2-21 ԭ�򣺽����������·��,·���Ż�
	public static Collection getFirstLeveRouteListReport(
			TechnicsRouteListIfc routelist, ArrayList sendToColl)
			throws QMException {

           return getFirstLeveRouteListReportnew(routelist, sendToColl);
	}
	// CCEndby leixiao 2009-2-21 ԭ�򣺽����������·��,·���Ż�
//  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
    /**
     * ���ָ��·�߱��һ��·�߱��������
     * @return ���ؼ��ϵ�Ԫ��Ϊ����arrayObjs����Ԫ������Ϊ��š��㲿����š��㲿������
     * һ��·�ߴ����ַ�������array�ļ��ϡ�array[0]Ϊ����·�ߴ���array[1]Ϊװ��·�ߴ���
     */
    public static Collection getFirstLeveRouteListReportolder(TechnicsRouteListIfc
        routelist,ArrayList sendToColl) throws QMException {

      ///// 20070525 liuming add
      //�����ݿ�ֱ��ȡ���������������
//    	System.out.println("   ReportLevelOneUtil getFirstLeveRouteListReport ()routelist = "+routelist);
      HashMap countMap = new HashMap();
      Vector indexVector = routelist.getPartIndex();
    //  System.out.println("   ReportLevelOneUtil getFirstLeveRouteListReport ()indexVector = "+indexVector);
      if (indexVector != null && indexVector.size() > 0) {
        int size2 = indexVector.size();
        String key = null;
        for (int k = 0; k < size2; k++) {
          String[] ids = (String[]) indexVector.elementAt(k);
//          System.out.println("       ids .length="+ids.length+" 0:"+ids[0]+" 1:"+ids[1]);
          if (countMap.containsKey(ids[0])) {
            key = ids[0] + "K" + k;
          }
          else {
            key = ids[0];
          }
//          System.out.println("key = "+key+".........count = "+ids[2]);
//        CCBegin by leixiao 2008-11-5 ԭ�򣺽����������·��
          if(ids.length>2)
//        	  CCEnd by leixiao 2008-11-5 ԭ�򣺽����������·��
          countMap.put(key, ids[2]); //����������ظ��Ŀ��ܣ���Ҫ��������
        }
      }


      ArrayList v = new ArrayList();
      //�㲿��id�����㲿���ĸ������ϣ����ٲ�ѯ�����Ĵ���,yanqi-20061010
      HashMap parentMap = new HashMap();
      //�㲿���ڳ�����ʹ�������Ļ���,yanqi-20061010
      HashMap useQuantityMap = new HashMap();
      //a���Ƿ�b�����Ӽ��Ļ���,��:a��bsoID+"_"+b��bsoID,ֵ:Boolean,yanqi-20061010
      HashMap isSonMap = new HashMap();

      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      StandardPartService partService = (StandardPartService)
          EJBServiceHelper.getService("StandardPartService");
      TechnicsRouteService routeService =
          (TechnicsRouteService) EJBServiceHelper.getService(
              "TechnicsRouteService");
      //��õ�ǰ�û������ù淶
      PartConfigSpecIfc configSpecIfc = getCurrentConfigSpec_enViewDefault();
      if (configSpecIfc == null || configSpecIfc.getStandard() == null ||
          configSpecIfc.getStandard().getViewObjectIfc() == null) {
        configSpecIfc = getPartConfigSpecByViewName("������ͼ");
      }
      //key:ListRoutePartLinkIfc
      CodeManageTable map = routeService.getFirstLeveRouteListReport(routelist);
   //   System.out.println("leix leix leix  ---------CodeManageTable="+map);
      //������ʶ·�߱�������㲿�������Ƿ����2000������2000ʱ���ò�ͬ�Ĳ��Լ�����ز����ڳ����ϵ�����
      boolean flag = (map.size() > 2000);
      QMPartMasterIfc rootmaster = (QMPartMasterIfc) pservice.refreshInfo(
          routelist.getProductMasterID());
      //������Ʒ�����°汾
      QMPartIfc rootPart = routeService.getLastedPartByConfig(rootmaster,
          configSpecIfc);
      HashMap partIDCountMap = null;
      //������������������2000����ͳ�Ƹó��͵������Ӽ�������
      if (expandByProduct && flag) {
        //�����ָ�������µ������Ӳ���������,��Ϊ�Ӽ�BSOID,ֵΪ�Ӽ�����
        partIDCountMap = partService.getSonPartsQuantityMap(rootPart);
      }
    //  System.out.println("�����㲿���������� " + map.size() );
      Enumeration enum2 = map.keys();
      int i = 0;
      ArrayList informationlist = null;
      StringBuffer remark = null;
      String change = null;
      String countInProduct = null;
      String version = null;
      /////////////ѭ������ÿ�������㲿��
      while (enum2.hasMoreElements()) {
        ListRoutePartLinkIfc listPartRoute = (ListRoutePartLinkIfc) enum2.
            nextElement();
        QMPartMasterIfc partmaster = listPartRoute.getPartMasterInfo();
        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        String[] hhhh = getPartMakeAndAssRouteInRouteList(listPartRoute, map,sendToColl);
        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&777
        String partMakeRoute = hhhh[0];
        String myAssRoute = hhhh[1];
        QMPartIfc part = routeService.getLastedPartByConfig(partmaster,
            configSpecIfc); //liuming 20061215
//        QMPartIfc part = routeService.getLastedPartofsz(partmaster,
//                configSpecIfc);
////      CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
//        QMPartIfc part=null;
//        QMPartIfc part1=listPartRoute.getPartBranchInfo();
//        if(part==null){
//           part = routeService.getLastedPartByConfig(partmaster,
//          configSpecIfc); //liuming 20061215
//        }
//        else{
//        	part=part1;
//        }
////      CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
        if (part == null) {
          continue;
        }
      //  System.out.println("part = "+part.getBsoID()+" partMakeRoute="+partMakeRoute+"partMakeRoute="+partMakeRoute);
//-------------------------leixleixleix
        if (flag) {
        }
        else {
          //�ǲ�Ʒ����
          if (part.getBsoID().equals(rootPart.getBsoID())) {

          }
          else {
            //�������getUseQuantity����ֻ��Ϊ�˸�parentMap��ֵ��������������á�
            getUseQuantity(part, rootPart, parentMap, useQuantityMap,
                           configSpecIfc);
          }
        }
//-------------------------leixleixleix

        ////////����ÿ��������////////////////////////////////////////////////////
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
        //////////��ÿ���������������////////////////////////////////////////////////////////


        ////���ݽ��Ҫ��·������"��"���ں���/////////////////////////////////////
        if (partMakeRoute.indexOf("��") != -1 && (!partMakeRoute.endsWith("��"))) {
          String sortedRoute = "";
          StringTokenizer yyy = new StringTokenizer(partMakeRoute, "/");
          while (yyy.hasMoreTokens()) {
            String haha = yyy.nextToken();
            if (!haha.equals("��")) {
              sortedRoute += haha + "/";
            }
          }
          sortedRoute += "��";
          partMakeRoute = sortedRoute;
        }
        ////////////////////////////////////////////////////////////////////

        //����Ԫ�أ��ֱ�Ϊ��װ��·�ߡ������ϼ��������͡��ϼ����š�
        informationlist = new ArrayList();
        String[] informationStr = null;
        //�������á�����������С�װ��ͼ���Ĳ����ָ���   liuming 20061212
        if (partMakeRoute == "" ||
            ( (partMakeRoute.indexOf("��") != -1) &&
             partmaster.getPartName().indexOf("װ��ͼ") != -1)) {
          informationStr = new String[3];
          informationStr[0] = myAssRoute;
          informationStr[1] = "";
          informationStr[2] = "";
          informationlist.add(informationStr);
          informationStr=null;
        }
        else {////////////////////else H  begin
          //
          Collection parentPartList = new ArrayList(); //yanqi-20060927
          //���˵������еĹ��岿����GenericPart��
          getParentPartsNew(part, 1.0F, parentPartList, parentMap, configSpecIfc); //yanqi-20060927

          ///////////////map32:   key=partID value=�ϼ�����
          HashMap map32 = new HashMap();
          for (Iterator it = parentPartList.iterator(); it.hasNext(); ) {
            String partNumCount = (String) it.next();
//            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//            System.out.println("partNumCount = "+partNumCount);
//            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            StringTokenizer yyy = new StringTokenizer(partNumCount, "...");
            if (yyy.hasMoreTokens()) {
              String partID = yyy.nextToken();
              String partCount = yyy.nextToken();
              if (map32.get(partID) == null) {
                map32.put(partID, partCount);
              }
              else {
                String count32 = (String) map32.get(partID);
                int countFinal = Integer.parseInt(count32) +
                    Integer.parseInt(partCount);
                map32.put(partID, Integer.toString(countFinal));
              }
            }
            yyy=null;
          }
          ////////////////////////////////////////////

          parentPartList.clear();

          ///////////////////////////////////////A
          Collection keySet = map32.keySet();
          for (Iterator it = keySet.iterator(); it.hasNext(); ) {
            String partID = (String) it.next();
            String count = (String) map32.get(partID);
            //System.out.println("���� = "+partID);
            //�������չ����������������ڸó��ϣ�����ʾ�˸�������Ϣ
            if (expandByProduct) {
              if (flag) {
                if (partIDCountMap.get(partID) == null) {
                  continue;
                }
              }
              else if (
              //CCBegin by liunan 2009-02-20
                  /*!isSonOf( (QMPartIfc)
                           pservice.refreshInfo(partID),
                           rootPart, parentMap, isSonMap, configSpecIfc)) */
                  !isSonOf( (QMPartIfc)
                           pservice.refreshInfo(partID),
                           rootPart, parentMap, isSonMap, configSpecIfc))
              //CCEnd by liunan 2009-02-20
              {
                continue;
              }

            }
            //CCBegin by liunan 2009-02-19
            QMPartIfc parentpart = (QMPartIfc) pservice.refreshInfo(partID);
            //CCEnd by liunan 2009-02-19
            //System.out.println("����Number = "+parentpart.getPartNumber());
            //���û��װ��·��,��Ѱ�Ҹ���������·��
            if (myAssRoute == null || myAssRoute.equals("")) {
              //�����߼��ܳɻ�������·�ߺ�"�õ�",�Ͳ���ȡ����������·����
              //CCBegin SS27
              if (part.getPartType().toString().equalsIgnoreCase("Logical") || (part.getPartNumber().trim().length()>5&&part.getPartNumber().trim().substring(4,5).equals("G")) ||
                  (partMakeRoute.indexOf("��") != -1)) { //////20070904 modify by liuming  CCEnd SS27
              }
              else {
                myAssRoute = getAssisRoute(parentpart);

                //��·�ߵ�λ���뷢����λ��.
                StringTokenizer qq = new StringTokenizer(myAssRoute, "/");
                while (qq.hasMoreTokens()) {
                  String departmentName = qq.nextToken();
                  if (!sendToColl.contains(departmentName) &&
                      departmentName != "") {
                    sendToColl.add(departmentName);
                  }
                }
                qq=null;
              }
            }
            informationStr = new String[3];
            informationStr[2] = parentpart.getPartNumber();
            informationStr[1] = count;
            informationStr[0] = myAssRoute;
            informationlist.add(informationStr);
            informationStr=null;
          }

          map32 = null; //20071102
          parentPartList=null;
          /////////////////////////////////////////////A

        }/////else H  end



        if (informationlist.size() == 0) {
          informationStr = new String[3];
          informationStr[0] = myAssRoute;
          informationStr[1] = "";
          informationStr[2] = "";
          informationlist.add(informationStr);
          informationStr =null;
        }


        //���ø��ı�ʶ�ͱ�ע
        TechnicsRouteIfc route = null;
        change = "";
        remark = new StringBuffer("");
        if (listPartRoute != null) {
          String routeID = listPartRoute.getRouteID();
          if (routeID != null) {
            route = (TechnicsRouteIfc) pservice.refreshInfo(routeID, false);
          }
        }
        if (route != null) {
          change = (String) signMap.get(route.getModefyIdenty().getCodeContent());
          String des1 = route.getDefaultDescreption();
          if (des1 == null) {
            des1 = "";
          }
          remark.append(des1);
          String des2 = route.getRouteDescription();
//        CCBeginby leixiao 2008-10-04 ԭ�򣺽����������·��,˵����Ϣ������汾������Ϣ����ʾ
          if(des2!=null&&des2.startsWith("(")&&des2.indexOf(")")!=-1){
        	  des2=des2.substring(des2.indexOf(")")+1,des2.length());
          }
//        CCEnd leixiao 2008-10-04 ԭ�򣺽����������·��
          if (des2 == null || des2.equals("")) {
          }
          else {
            if (des1.equals("")) {
            }
            else {
              remark.append(" ");
            }
            remark.append(des2);
          }
        }

////////////////////////////////////////////////////////////////////////////////  /
         version = "";
        //���ڱ����Q,CQ,T��ͷ�ı�׼������ʾ�汾��
        String partNum = part.getPartNumber();
        if (partNum.startsWith("Q") || partNum.startsWith("CQ") ||
            partNum.startsWith("T")) {
        }
        else {
          //{{{{{{{{{{{{{{{��ô�汾��
//          version = getPartSourceVersion(part.getBsoID());
//          if (version == null || version.equals("")) {
//            version = part.getVersionValue();
//            if (version == null || version.equals("")) {
//              version = "  ";
//            }
//          }
//          version = version.substring(0, 1);
//          //}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}]
        	           if (route != null&&route.getRouteDescription()!=null)
            {
           // 	CCEndby leixiao 2008-10-04 ԭ�򣺽����������·��

             // version = route.getRouteDescription().substring(1,2);//liunan
              String routeStr1=route.getRouteDescription();
              if(routeStr1!=null&&routeStr1.startsWith("(")&&routeStr1.indexOf(")")!=-1){
              version =  routeStr1.substring(routeStr1.indexOf("(")+1,routeStr1.indexOf(")"));
              }
            }
        	//CCBeginby leixiao 2008-10-04 ԭ�򣺽����������·��,�쳣�������·��˵��Ϊ��ʱ��������汾

        }
        if (version.trim().equals("")) {
        }
        else {
          version = "/" + version;
        }
////////////////////////////////////////////////////////////////////////  /

//      CCBeginby leixiao 2008-11-21 ԭ�򣺽����������·��
         QMQuery query = new QMQuery("StringValue");
         int u = query.appendBso("StringDefinition", false);
        Collection col=null;
        QueryCondition qc1 = new QueryCondition("definitionBsoID", "bsoID");
        query.addCondition(0, u, qc1);
        query.addAND();
        QueryCondition qc2 = new QueryCondition("displayName", " = ", "Ӱ�컥��");
        query.addCondition(u, qc2);
        query.addAND();
        QueryCondition qc3 = new QueryCondition("iBAHolderBsoID", " = ", part.getBsoID());
        query.addCondition(0, qc3);
        col = pservice.findValueInfo(query, false);
        Iterator iba=col.iterator();
        String exchangiba="";
        while(iba.hasNext()){
        	StringValueIfc s=(StringValueIfc)iba.next();
        		exchangiba=s.getValue();
        }
//        System.out.println("-----ibavalue���������="+part.getPartNumber()+" Ӱ�컥����"+exchangiba);
//      CCEndby leixiao 2008-11-21 ԭ�򣺽����������·��

        /////����˱�ע 20070122
        Object[] arrayObjs = {
            String.valueOf(++i), change, partmaster.getPartNumber(),
            version,
            partmaster.getPartName(), countInProduct, partMakeRoute,
            informationlist, part.getBsoID(), remark.toString(),exchangiba
        };
        v.add(arrayObjs);
      }

      countMap = null; //20071102
      parentMap = null;
      useQuantityMap = null;
      isSonMap = null;
      partIDCountMap = null;
     //System.out.println("--------------v="+v);
      return v;
    }

//  CCEndnby leixiao 2008-7-31 ԭ�򣺽����������·��

	// CCBeginnby leixiao 2009-2-21 ԭ�򣺽����������·���Ż�
	public static Collection getFirstLeveRouteListReportnew(
			TechnicsRouteListIfc routelist, ArrayList sendToColl)
			throws QMException {
	//	System.out.println("--------����getFirstLeveRouteListReportnew");
    	boolean iscomplete=false;
      	 if( routelist.getRouteListState().equals("�ձ�")){
   		 iscomplete=true;
   	 }
   	 //CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
   	 if( routelist.getRouteListState().equals("�շ�")){
   		 iscomplete=true;
   	 }
   	 //CCEnd by liunan 2011-09-21
    //CCBegin SS21
    boolean routestateflag = false;
    //CCEnd SS21
		// 1.�����ݿ���׼����ֱ��ȡ���������������
		jfuputil jf = new jfuputil();
		HashMap countMap = new HashMap();
		Vector indexVector = routelist.getPartIndex();
		if (indexVector != null && indexVector.size() > 0) {
			int size2 = indexVector.size();
			String key = null;
			for (int k = 0; k < size2; k++) {
				String[] ids = (String[]) indexVector.elementAt(k);
				// System.out.println(" ids .length="+ids.length+" 0:"+ids[0]+"
				// 1:"+ids[1]);{
        //CCBegin SS21
        /*if (countMap.containsKey(ids[0])) {
					key = ids[0] + "K" + k;
				} else {
					key = ids[0];
				}*/
        String routestate = "";
        if(ids.length>5&&ids[5]!=null)
        {
        	routestate = ids[5];
        	if(!routestateflag)
        	routestateflag = true;
        }
        if(countMap.containsKey(ids[0]+routestate))
        {
             key = ids[0]+routestate+"K"+k;
        }
        else
        {
             key = ids[0]+routestate;
        }
        //CCEnd SS21
				 System.out.println("key = "+key+".........count = "+ids[2]);
				// CCBegin by leixiao 2008-11-5 ԭ�򣺽����������·��
				if (ids.length > 2)
					// CCEnd by leixiao 2008-11-5 ԭ�򣺽����������·��
					countMap.put(key, ids[2]); // ����������ظ��Ŀ��ܣ���Ҫ��������
			}
		}
		indexVector.clear();

		ArrayList v = new ArrayList();
		// a���Ƿ�b�����Ӽ��Ļ���,��:a��bsoID+"_"+b��bsoID,ֵ:Boolean,yanqi-20061010
		HashMap isSonMap = new HashMap();

		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();
		StandardPartService partService = (StandardPartService) EJBServiceHelper
				.getService("StandardPartService");
		TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper
				.getService("TechnicsRouteService");
		// ��õ�ǰ�û������ù淶
		PartConfigSpecIfc configSpecIfc = getCurrentConfigSpec_enViewDefault();
		if (configSpecIfc == null || configSpecIfc.getStandard() == null
				|| configSpecIfc.getStandard().getViewObjectIfc() == null) {
			configSpecIfc = getPartConfigSpecByViewName("������ͼ");
		}

		long a1 = System.currentTimeMillis();
		//2.�ҵ���Ӧ��׼��·�ߴ�
		CodeManageTable map = routeService
				.getFirstLeveRouteListReport(routelist);// �ҵ���Ӧ��׼��·�ߴ�
		// map(link,routestr)
		long b1 = System.currentTimeMillis();
		//System.out.print("�ҵ���Ӧ��׼��·�ߴ� map(link,routestr) ʱ�䣽" + (b1 - a1));

		QMPartMasterIfc rootmaster = (QMPartMasterIfc) pservice
				.refreshInfo(routelist.getProductMasterID());
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
		//CCBegin SS1
		String safetyiba = "";
		//CCEnd SS1
		//CCBegin SS12
		String docnoteiba = "";
		//CCEnd SS12
		if(iscomplete){
		}
		else{
		// begin----��ȡӰ�컥����definitionid
		QMQuery query = new QMQuery("StringDefinition");
		QueryCondition qc = new QueryCondition("displayName", " = ", "Ӱ�컥��");
		query.addCondition(qc);
		Collection col = pservice.findValueInfo(query, false);
		Iterator iba = col.iterator();
		
		if (iba.hasNext()) {
			StringDefinitionIfc s = (StringDefinitionIfc) iba.next();
			exchangiba = s.getBsoID();
		}
		// System.out.println("exchangiba="+exchangiba);
		
		//CCBegin SS1
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
		System.out.println("safetyiba="+safetyiba);
		//CCEnd SS1
		
		//CCBegin SS12
		QMQuery query2 = new QMQuery("StringDefinition");
		QueryCondition qc2 = new QueryCondition("displayName", " = ", "�ĵ���Ϣ");
		query2.addCondition(qc2);
		Collection col2 = pservice.findValueInfo(query2, false);
		Iterator iba2 = col2.iterator();
		
		if (iba2.hasNext()) 
		{
			StringDefinitionIfc s = (StringDefinitionIfc) iba2.next();
			docnoteiba = s.getBsoID();
		  System.out.println("docnoteiba="+docnoteiba);
		}
		//CCEnd SS12
		}
		// End----��ȡӰ�컥����definitionid
		
		// begin ��ȡ���masterid
		ArrayList partlist = new ArrayList();
		ArrayList partmasterList = new ArrayList();
		ArrayList allpartmasterList = new ArrayList();
		HashMap masterlinkmap = new HashMap();
		ListRoutePartLinkIfc listPartRoute=null;
		QMPartMasterIfc partmaster=null;
		ArrayList partholer = new ArrayList();
		while (enum2.hasMoreElements()) {
			listPartRoute = (ListRoutePartLinkIfc) enum2
					.nextElement();
			partmaster = listPartRoute.getPartMasterInfo();
			 QMPartIfc part1= listPartRoute.getPartBranchInfo();
				// CCBeginby leixiao 2009-3-5 ԭ�򣺽����������·��,�����м����id
			 if(part1!=null){//���ListRoutePartLinkIfc�����������ֱ�Ӵ�����ȡ
					if (part1.getPartNumber().startsWith("Q")
							|| part1.getPartNumber().startsWith("CQ")) {
						//System.out.println("-----�б�׼��");
						flag = true;//����б�׼��������õ�һ�ַ���
					}
					String masterid = part1.getMasterBsoID();
					masterlinkmap.put(masterid, part1);
					partholer.add(part1.toString()); 
			 }				
			 else{
			partmasterList.add(partmaster); //��partmaster�ŵ������й�һ���Ե������ݿ�
			 }
			 allpartmasterList.add(partmaster);
//			 CCEndby leixiao 2009-3-5 ԭ�򣺽����������·��,�����м����id
			listPartRoute=null;
			partmaster=null;
		}
		enum2 = null;
		// end ��ȡ���masterid
     //3.��ȡ�����������
		// begin ��ȡ�����������
		long a3 = System.currentTimeMillis();
		Collection lastedpart = jf.getLastedPartByConfig(partmasterList,
				configSpecIfc);
		long b3 = System.currentTimeMillis();
		//System.out.println("��ȡ�����������ʱ�䣽" + (b3 - a3));

		partlist.addAll(lastedpart);

		// End ��ȡ�����������
		// begin ��ȡ���id iba
		
		for (int j = 0; j < partlist.size(); j++) {
			QMPartIfc last = (QMPartIfc) ((Object[]) partlist.get(j))[0];
			if (last.getPartNumber().startsWith("Q")
					|| last.getPartNumber().startsWith("CQ")) {
				//System.out.println("-----�б�׼��");
				flag = true;//����б�׼��������õ�һ�ַ���
			}
			String masterid = last.getMasterBsoID();
			masterlinkmap.put(masterid, last);
			partholer.add(last.toString());
		}
		
		partlist.clear();
      //4.��ȡ���id 
//		CCBegin by leixiao 2009-3-27 ԭ���ձ��������
		HashMap returnList=null;
		//CCBegin SS1
		HashMap returnSafetyList = null;
		//CCEnd SS1
		//CCBegin SS12
		HashMap returnDocnoteList = null;
		//CCEnd SS12
		if(iscomplete){
		}
		else{
		 returnList = jf.getiba(partholer, exchangiba);
		 //CCBegin SS1
		 returnSafetyList = jf.getiba(partholer, safetyiba);
		 //CCEnd SS1
		 //CCBegin SS12
		 returnDocnoteList = jf.getiba(partholer, docnoteiba);
		 //CCEnd SS12
		}
//		CCEnd by leixiao 2009-3-27 ԭ���ձ��������
		partholer.clear();
		// end ��ȡ���id iba

		long a2 = System.currentTimeMillis();
//5.�Ҹ���
//		CCBegin by leixiao 2009-3-27 ԭ���ձ��������ʱ����Ҫ������Ϣ
		ArrayList parentpartlist = null;
		if(iscomplete){
		}
		else{
		if (expandByProduct && flag) {
			// �����ָ�������µ������Ӳ�������һ�ַ����Ҹ���
			ArrayList rootPartlist = new ArrayList();
			parentMap2000 = new HashMap();
			rootPartlist.add(rootPart.getBsoID());
			// partIDCountMap =
			// jf.getSubParts(rootPartlist,parentMap2000,configSpecIfc);
			jf.getSubParts(rootPartlist, parentMap2000, configSpecIfc);
			// System.out.println("---parentMap��С��"+parentMap.size());
			rootPartlist.clear();
		}
		long b2 = System.currentTimeMillis();
		//System.out.println("�����ָ�������µ������Ӳ���ʱ��=" + (b2 - a2));

		// ��ȡ����������
		long a4 = System.currentTimeMillis();
		if (expandByProduct && flag) {

		} else {
			//�ڶ��ַ����Ҹ���
			parentpartlist = jf.getparentparts(allpartmasterList, parentMap,
					rootPart, configSpecIfc);
		}
		}
//		CCBegin by leixiao 2009-3-27 ԭ���ձ��������
		allpartmasterList=null;
		long b4 = System.currentTimeMillis();
		//System.out.println("----------��ȡ����������ʱ��=" + (b4 - a4));
		parentpartlist = null;
		int i = 0;
		ArrayList informationlist = null;
		StringBuffer remark = null;
		String change = null;
		String countInProduct = null;
		String version = null;
		// ///////////ѭ������ÿ�������㲿��
		//6.ÿ�������㲿������������װ
		Enumeration enum3 = map.keys();
		//ListRoutePartLinkIfc listPartRoute=null;
		//QMPartMasterIfc partmaster=null;
		String masterID=null;
		QMPartIfc part=null;
		
		
		//CCBegin by liunan 2012-03-22 ��ʱʹ�� �ձ�1014.01-1 �Ŵ���
		ArrayList yblist = null;
		//if(iscomplete&&routelist.getRouteListNumber().equals("�ձ�1014.01-1"))
		if(iscomplete)
		{
			System.out.println("aaaaaaaaaaaaaaaaaa");
			ArrayList rootPartlist = new ArrayList();
			HashMap ybmap = new HashMap();
			rootPartlist.add(rootPart.getBsoID());
			yblist = jf.getSubParts(rootPartlist, ybmap, configSpecIfc);
			System.out.println("yblist============"+yblist.size());
		}
		//CCEnd by liunan 2012-03-22
		
		int aa = 0;
		while (enum3.hasMoreElements()) {
			 listPartRoute = (ListRoutePartLinkIfc) enum3
					.nextElement();
			 partmaster = listPartRoute.getPartMasterInfo();			 
			 masterID = partmaster.getBsoID();
			 //CCBegin by leixiao 2009-11-17 ���ListRoutePartLinkIfc�м���partid,��link��ȡ
			 part=listPartRoute.getPartBranchInfo();
			 //CCBegin by leixiao 2009-11-17
			 if(part==null)
			 part = (QMPartIfc) masterlinkmap.get(masterID);
			if (part == null)
				continue;

			//System.out.println(aa+"==================="+part.getPartNumber());
			aa++;
			
        System.out.println("masterID========"+masterID);
			String[] hhhh = getPartMakeAndAssRouteInRouteList(listPartRoute,
					map, sendToColl);
			String partMakeRoute = hhhh[0];
			String myAssRoute = hhhh[1];
			
			// //////����ÿ��������////////////////////////////////////////////////////
			countInProduct = "";
			String masterID1 = masterID;
        //CCBegin SS21
        if(routestateflag)
        {
        	String coding;
        	if (listPartRoute.getRouteID() == null || listPartRoute.getRouteID().length() < 1)
        	{
        		coding = "��";
        	}
        	else
        	{
        		coding = getCodingDesc(listPartRoute.getRouteID());
        	}
        	masterID1 = masterID1 + coding;
        }
        //CCEnd SS21
			if (countMap.containsKey(masterID1)) {
				countInProduct = countMap.get(masterID1).toString();
				countMap.remove(masterID1);
			} else {
				String keya = null;
				for (Iterator jt = countMap.keySet().iterator(); jt.hasNext();) {
					//CCBegin by liunan 2009-09-20 9:44
					//keya = jt.next().toString();
					keya = (String)jt.next();
					if(keya==null)
					{
						continue;
					}
					//CCEnd by liunan 2009-09-20 9:44
					if (keya.startsWith(masterID1)) {
						masterID1 = keya;
						countInProduct = countMap.get(masterID1).toString();
						countMap.remove(masterID1);
						break;
					}
				}
			}
			// System.out.println(">>> masterID =
			// "+masterID+".....countInProduct = "+countInProduct);
			if (countInProduct.trim().equals("0")) {
				countInProduct = "";
			}
			System.out.println("countInProduct===="+countInProduct);
			// ////////��ÿ���������������////////////////////////////////////////////////////////

			// //���ݽ��Ҫ��·������"��"���ں���/////////////////////////////////////
			if (partMakeRoute.indexOf("��") != -1
					&& (!partMakeRoute.endsWith("��"))) {
				String sortedRoute = "";
				StringTokenizer yyy = new StringTokenizer(partMakeRoute, "/");
				while (yyy.hasMoreTokens()) {
					String haha = yyy.nextToken();
					if (!haha.equals("��")) {
						sortedRoute += haha + "/";
					}
				}
				sortedRoute += "��";
				partMakeRoute = sortedRoute;
			}
			// //////////////////////////////////////////////////////////////////
			/*int le = 0;
		if(parentMap2000!=null&&parentMap2000.size()>0)
		{
			//System.out.println("parentMap2000 ������"+parentMap2000.size());
			le = getlevel(expandByProduct,flag,parentMap2000,parentMap,part.getBsoID(),masterID,part.getPartNumber(),le);
		}
		if(parentMap!=null&&parentMap.size()>0)	
		{
			//System.out.println("parentMap ������"+parentMap.size());
			StandardPartService spService = (StandardPartService) EJBServiceHelper.
            getService("StandardPartService");
			Vector vec = spService.getUsedProductStruct(part,rootPart.getBsoID(),(PartConfigSpecIfc) spService.findPartConfigSpecIfc());
			if(vec!=null&&vec.size()>0)
			{
				Vector vec1 = (Vector)vec.elementAt(vec.size()-1);
				if(vec1!=null&&vec1.size()>0)
            {
            	Object[] aaa = (Object[])(vec1.elementAt(vec1.size()-1));
            	le = Integer.parseInt(aaa[0].toString());
            }
			}
		}
			
			System.out.println(part.getPartNumber()+" �Ĳ㼶�ǣ�"+le);*/

			// ����Ԫ�أ��ֱ�Ϊ��װ��·�ߡ������ϼ��������͡��ϼ����š�
			informationlist = new ArrayList();
			String[] informationStr = null;
			// �������á�����������С�װ��ͼ���Ĳ����ָ��� liuming 20061212
//			CCBegin by leixiao 2009-3-27 ԭ���ձ��������
			if(iscomplete){
				// ���û��װ��·��,��Ѱ�Ҹ���������·��
				if (myAssRoute == null || myAssRoute.equals("")) {
					// �����߼��ܳɻ�������·�ߺ�"�õ�",�Ͳ���ȡ����������·����

					//CCBegin SS27
					if (part.getPartType().toString().equalsIgnoreCase(
							"Logical") || (part.getPartNumber().trim().length()>5&&part.getPartNumber().trim().substring(4,5).equals("G"))
							|| (partMakeRoute.indexOf("��") != -1)) { // ////20070904  CCEnd SS27
						informationStr = new String[3];
						informationStr[0] = myAssRoute;
						informationStr[1] = "";
						informationStr[2] = "";
						informationlist.add(informationStr);
						informationStr = null;

					} else {
						//System.out.println("------װ��Ϊ��");
						Collection parentcollection = getParentPartsByConfigSpec(part, configSpecIfc);
						//CCBegin SS24
						if(parentcollection!=null)
						{
						//CCEnd SS24
						Iterator i1=parentcollection.iterator();
						//System.out.println("------parentcollection"+parentcollection.size());
						while(i1.hasNext()){
							QMPartIfc parentpart=(QMPartIfc)(((Object[])i1.next())[1]);
							//System.out.println("------������="+parentpart);
							
							//CCBegin by liunan 2012-03-22 ��ʱʹ�� �ձ�1014.01-1 �Ŵ���
							//if(routelist.getRouteListNumber().equals("�ձ�1014.01-1"))
							{
								System.out.println("bbbbbbbbbbbbbbbbb");
								if(!yblist.contains(parentpart.getBsoID()))
								{
									System.out.println("cccccccccccccccccc");
									continue;
								}
							}
							//CCEnd by liunan 2012-03-22
							
							/*if (!isSonOf(parentpart, rootPart, parentMap1,
									isSonMap, configSpecIfc)) {
								continue;
							}*/
						myAssRoute = getAssisRoute(parentpart);
						//System.out.println("����·��="+myAssRoute);
                        if(myAssRoute == null || myAssRoute.equals(""))
                        	continue;
						// ��·�ߵ�λ���뷢����λ��.
						StringTokenizer qq = new StringTokenizer(
								myAssRoute, "/");
						while (qq.hasMoreTokens()) {
							String departmentName = qq.nextToken();
							if (!sendToColl.contains(departmentName)
									&& departmentName != "") {
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
						//CCBegin SS24
					  }
					  //CCEnd SS24
					}
				}

			}
//			CCEnd by leixiao 2009-3-27 ԭ���ձ��������
			else if (partMakeRoute == ""
					|| ((partMakeRoute.indexOf("��") != -1) && partmaster
							.getPartName().indexOf("װ��ͼ") != -1)) {
				informationStr = new String[3];
				informationStr[0] = myAssRoute;
				informationStr[1] = "";
				informationStr[2] = "";
				informationlist.add(informationStr);
				informationStr = null;
			} else {// //////////////////else H begin
				String parentPartList = null;
				// System.out.print(" ���� part="+part);
				if (expandByProduct && flag) {

					//CCBegin SS3
					//parentPartList = (String) parentMap2000.get(part.getBsoID());
					parentPartList = (String) parentMap2000.get(part.getMasterBsoID());
					//CCEnd SS3
				} else {

					parentPartList = (String) parentMap.get(masterID);

				}
				//System.out.println(" number="+part.getPartNumber()+" "+parentPartList+"\n");

				// /////////////map32: key=partID value=�ϼ�����
				HashMap map32 = new HashMap();
				if (parentPartList != null) {

					StringTokenizer yyy = new StringTokenizer(parentPartList,
							";");
					while (yyy.hasMoreTokens()) {
						String parentcount = yyy.nextToken();
						StringTokenizer yy = new StringTokenizer(parentcount,
								"...");
						if (yy.hasMoreTokens()) {
							String parepartid = yy.nextToken();
							String partcount = yy.nextToken();
							if(map32.get(parepartid)!=null){
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
				for (Iterator it = keySet.iterator(); it.hasNext();) {
					String partID = (String) it.next();
					String count = (String) map32.get(partID);
					// CCBegin by liunan 2009-02-19
					QMPartIfc parentpart = (QMPartIfc) pservice.refreshInfo(
							partID);
					// CCEnd by liunan 2009-02-19
					// System.out.println("���"+part.getPartNumber()+"����Number =
					// "+parentpart.getPartNumber());

					// �������չ����������������ڸó��ϣ�����ʾ�˸�������Ϣ
					if (expandByProduct) {
						if (flag) {
							// //������������2000����ֱ�Ӵ�rootչ���ĵĽṹ�����Ƿ��иü�
							// if (parentMap2000.get(partID) == null) {
							// continue;
							// }
						} else if (!isSonOf(parentpart, rootPart, parentMap1,
								isSonMap, configSpecIfc)) {
							continue;
						}

					}
					// ���û��װ��·��,��Ѱ�Ҹ���������·��
					if (myAssRoute == null || myAssRoute.equals("")) {
						// �����߼��ܳɻ�������·�ߺ�"�õ�",�Ͳ���ȡ����������·����

						//CCBegin SS27
						if (part.getPartType().toString().equalsIgnoreCase(
								"Logical") || (part.getPartNumber().trim().length()>5&&part.getPartNumber().trim().substring(4,5).equals("G"))
								|| (partMakeRoute.indexOf("��") != -1)) { // ////20070904  CCEnd SS27
							// modify
							// by
							// liuming
						} else {
							myAssRoute = getAssisRoute(parentpart);

							// ��·�ߵ�λ���뷢����λ��.
							StringTokenizer qq = new StringTokenizer(
									myAssRoute, "/");
							while (qq.hasMoreTokens()) {
								String departmentName = qq.nextToken();
								if (!sendToColl.contains(departmentName)
										&& departmentName != "") {
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

				// ///////////////////////////////////////////A

			}// ///else H end

			if (informationlist.size() == 0) {
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
			if (listPartRoute != null) {
				String routeID = listPartRoute.getRouteID();
				if (routeID != null) {
					route = (TechnicsRouteIfc) pservice.refreshInfo(routeID,
							false);
				}
			}
			if (route != null) {

				change = (String) signMap.get(route.getModefyIdenty()
						.getCodeContent());
				

				String des1 = route.getDefaultDescreption();
				if (des1 == null) {
					des1 = "";
				}
				remark.append(des1);
				String des2 = route.getRouteDescription();
				// CCBeginby leixiao 2008-10-04 ԭ�򣺽����������·��,˵����Ϣ������汾������Ϣ����ʾ
				if (des2 != null && des2.startsWith("(")
						&& des2.indexOf(")") != -1) {
					des2 = des2.substring(des2.indexOf(")") + 1, des2.length());
				}
				// CCEnd leixiao 2008-10-04 ԭ�򣺽����������·��
				if (des2 == null || des2.equals("")) {
				} else {
					if (des1.equals("")) {
					} else {
						remark.append(" ");
					}
					remark.append(des2);
				}
			}

			// //////////////////////////////////////////////////////////////////////////////
			// /
			version = "";
			String partNum = "";
			// ���ڱ����Q,CQ,T��ͷ�ı�׼������ʾ�汾��

			partNum = part.getPartNumber();

			if (partNum.startsWith("Q") || partNum.startsWith("CQ")
					|| partNum.startsWith("T")) {
			} else {

				// CCBeginby leixiao 2008-10-04 ԭ�򣺽����������·��,�쳣�������·��˵��Ϊ��ʱ��������汾
//				 if(listPartRoute.getPartBranchID()!=null){
//				 version=routeService.getibaPartVersion(part);
//				 System.out.println("version="+version);
//				 }
//				 else{
				if (route != null && route.getRouteDescription() != null) {
					// CCEndby leixiao 2008-10-04 ԭ�򣺽����������·��

					// version =
					// route.getRouteDescription().substring(1,2);//liunan
					String routeStr1 = route.getRouteDescription();
					if (routeStr1 != null && routeStr1.startsWith("(")
							&& routeStr1.indexOf(")") != -1) {
						version = routeStr1.substring(
								routeStr1.indexOf("(") + 1, routeStr1
										.indexOf(")"));
					}
				}
//				 }
			}

			if (version.trim().equals("")) {
			} else {
				version = "/" + version;
			}
			// //////////////////////////////////////////////////////////////////////
			// /
			String exchangvalue="";			
			//CCBegin SS1
			String safetyvalue = "";
			//CCEnd SS1
			//CCBegin SS12
			String docnotevalue = "";
			//CCEnd SS12
//			CCBegin by leixiao 2009-3-27 ԭ���ձ��������
        if(iscomplete){
        	//CCBegin by liunan 2011-10-12 ����֪ͨ�� ʱ exchangvalue ��Ӧ���� �����ó��͡�
        	if( routelist.getRouteListState().equals("�շ�"))
        	{
        		//�����㲿��������� ��׼ ��š�
        		Collection trlcol = (Collection)routeService.getListsByPart(part.getMasterBsoID());
        		//System.out.println("trlcol===="+trlcol);
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
        	//CCEnd by liunan 2011-10-12
        }
        else{
			exchangvalue = (String) returnList.get(part.getBsoID());
			//CCBegin SS1
			safetyvalue = (String) returnSafetyList.get(part.getBsoID());
			//CCEnd SS1
			//CCBegin SS12
			docnotevalue = (String) returnDocnoteList.get(part.getBsoID());
			if(docnotevalue==null)
			{
				docnotevalue = "";
			}
			
			if(!docnotevalue.equals(""))
			{
				docnotevalue = "("+docnotevalue+")";
			}
			//CCEnd SS12
           }
//		CCEnd by leixiao 2009-3-27 ԭ���ձ��������     
			// System.out.println("-----ibavalue �����="+part.getPartNumber()+"
			// Ӱ�컥����"+exchangvalue);
			// CCEndby leixiao 2008-11-21 ԭ�򣺽����������·��
      
        //CCBegin SS6
        /*CCBegin SS16
        String route1="";
        String route2="";
        if(partMakeRoute.indexOf("/")!=-1){
        	String[] makeRoute=partMakeRoute.split("/");
        	for(int n=0;n<makeRoute.length;n++) {
               String ss=makeRoute[n];   	 
               if(ss.trim().startsWith("��")||ss.trim().startsWith("��")||ss.trim().startsWith("��(����)")||
            	ss.trim().startsWith("��(��)")||ss.trim().startsWith("�񣨺���")||ss.trim().startsWith("��")||
            	ss.trim().startsWith("Ϳ")||ss.trim().startsWith("�ܣ�װ��")||ss.trim().startsWith("�ܣ��ᣩ")||
            	ss.trim().startsWith("�ܣ��ݣ�")||ss.trim().startsWith("�ܣ��꣩")||ss.trim().startsWith("�ܣ����ף�")||
            	ss.trim().startsWith("�ܣ��ᣩ"))
               {
            	   if(route1.equals("")){
            		   route1=makeRoute[n];
            	   }else{
            		   route1=route1+"/"+makeRoute[n];
            	   }   
            	 }else{
            		 if(route2.equals("")){
              		   route2=makeRoute[n];
              	   }else{
              		   route2=route2+"/"+makeRoute[n];
              	   }   
            	 }
               } 	
        }   
        if(!route1.equals("")){
        	if(!route2.equals("")){
        		partMakeRoute=route1+"/"+route2;
        	}
        } 
        
        String route3="";
        String route4="";
//					CCBegin SS10
//        String[] info=handleInfo(informationlist);
//        String assRoute=info[0];
        String route5="";
	      for(Iterator iteabc = informationlist.iterator();iteabc.hasNext();)
        {
        String[] info = (String[]) iteabc.next();
        route3="";
        route4="";
      	String assRoute=info[0];
//					CCEnd SS10
        if(assRoute.indexOf("/")!=-1){
        	String[] assRoute1=assRoute.split("/");
        	for(int n=0;n<assRoute1.length;n++) {
               String ss=assRoute1[n];
               if(ss.trim().startsWith("��")||ss.trim().startsWith("��")||ss.trim().startsWith("��(����)")||
            	ss.trim().startsWith("��(��)")||ss.trim().startsWith("�񣨺���")||ss.trim().startsWith("��")||
            	ss.trim().startsWith("Ϳ")||ss.trim().startsWith("�ܣ�װ��")||ss.trim().startsWith("�ܣ��ᣩ")||
            	ss.trim().startsWith("�ܣ��ݣ�")||ss.trim().startsWith("�ܣ��꣩")||ss.trim().startsWith("�ܣ����ף�")||
            	ss.trim().startsWith("�ܣ��ᣩ"))
               {
            	   if(route3.equals("")){
            		   route3=assRoute1[n];
            	   }else{
            		   route3=route3+"/"+assRoute1[n];
            	   }   
            	 }else{
            		 if(route4.equals("")){
              		   route4=assRoute1[n];
              	   }else{
              		   route4=route4+"/"+assRoute1[n];
              	   }   
            	 }
               } 	
        }   
        if(!route3.equals("")){
        	if(!route4.equals("")){
        		assRoute=route3+"/"+route4;
 //					CCBegin SS10
       	}
        }
//					CCBegin SS13
        info[0]=assRoute;
//        		if (route5==null || route5.equals(""))
//	        		route5=assRoute;
//	        	else
//	        		route5=route5+","+assRoute;
//					CCEnd SS13
      	} 
      	CCEnd SS16*/
//					CCBegin SS13
/*      	
        String[] info=handleInfo(informationlist);
//					CCEnd SS10
        		informationlist.clear();
        		String[] info1=new String[3];
//					CCBegin SS10
//        		info1[0]=assRoute;
        		info1[0]=route5;
//					CCEnd SS10
        		info1[1]=info[1];
        		info1[2]=info[2];
        		informationlist.add(info1);
//					CCBegin SS10
//        	}
//        } 
//					CCEnd SS10
*/
//					CCEnd SS13
       //CCEnd SS6
        //CCBegin SS7

		// ///����˱�ע 20070122
//		Object[] arrayObjs = { String.valueOf(++i), change,
//				partmaster.getPartNumber(), version,
//				partmaster.getPartName(), countInProduct, partMakeRoute,
//				informationlist, part.getBsoID(), remark.toString(),
//				//CCBegin SS1
//				//exchangvalue };
//				exchangvalue, safetyvalue};
        String colorFlag="";
        if(listPartRoute.getColorFlag()!=null&&listPartRoute.getColorFlag().trim().equals("1")){
        	colorFlag="��";
        }
//        System.out.println("colorFlagcolorFlag====="+colorFlag);
//        System.out.println("safetyvaluesafetyvalue==="+safetyvalue);
			Object[] arrayObjs = { String.valueOf(++i), change,
					partmaster.getPartNumber(), version,
					//CCBegin SS12
					//partmaster.getPartName(), countInProduct, partMakeRoute,
					partmaster.getPartName()+docnotevalue, countInProduct, partMakeRoute,
					//CCEnd SS12
					informationlist, part.getBsoID(), remark.toString(),
					//CCBegin SS1
					//exchangvalue };
					
//					CCBegin SS8
					//exchangvalue,"", safetyvalue,colorFlag};
//					CCEnd SS7
					//CCEnd SS1
			        exchangvalue,"", safetyvalue,colorFlag};
//			CCEnd SS8
		 	
			//anan		
					
			v.add(arrayObjs);
			listPartRoute=null;
			partmaster=null; 
			masterID=null;
			part=null;
		}
		enum3=null;
		masterlinkmap = null;
		countMap.clear(); // 20071102
		parentMap = null;
		parentMap2000=null;
//		parentMap1.clear();
//		partmasterList.clear();
//		isSonMap.clear();
//		map.clear();
//		returnList.clear();
		parentMap1=null;
		partmasterList=null;
		isSonMap=null;
		map.clear();
		returnList=null;
		
		informationlist = null;

		// System.out.println("--------------v="+v);
		return v;
	}

//  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��


//  CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
    /**
     * ��ʾ����ʱ����(�־û�����)
     * �����ж���׼�Ƿ��ڸ������ϼ�.����ڸ������ϼ�,�������ɱ���,���������ݿ�;
     * ����ڹ������ϼ�,���һ���ж��������ݿ�ReportResult�����Ƿ����м�¼,����о�ֱ
     * ����ȡ,���û���������ɱ����������ݿ�.
     * liuming 20061108
     * @param routeListID String
     * @param expandByProduct1 String
     * @throws QMException
     */
    public static ArrayList getAllInformation2(String routeListID,
            String expandByProduct1) throws
QMException{

    	return getAllInformationColl(routeListID, expandByProduct1);

    }
    //  CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
    
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
                                          Collection result, Map parentMap,
                                          PartConfigSpecIfc configSpec) throws
        ServiceLocatorException, QMException {
      Collection parentColl = (Collection) parentMap.get(part.getBsoID());
      if (parentColl == null) {
        parentColl = getParentPartsByConfigSpec(part, configSpec);
        if (parentColl != null) {
          parentMap.put(part.getBsoID(), parentColl);
        }
      }

      if (parentColl != null) {
        for (Iterator it = parentColl.iterator(); it.hasNext(); ) {
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
                                   Map isSonMap, PartConfigSpecIfc configSpec) throws
        ServiceLocatorException, QMException {
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

      Collection parentPartColl = (Collection) parentMap.get(part.getBsoID());
      if (parentPartColl == null) {
        parentPartColl = getParentPartsByConfigSpec(part, configSpec);
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
          if (isSonOf(parentPart, root, parentMap, isSonMap, configSpec)) {
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
     * ������ͼ���Ʒ����㲿�����ù淶  liuming 20061207 add
     * @param viewName String
     * @throws QMException
     * @return PartConfigSpecIfc
     */
    private static PartConfigSpecIfc getPartConfigSpecByViewName(String viewName) throws
        QMException {
      ViewService viewService = (ViewService) EJBServiceHelper.getService(
          "ViewService");
      ViewObjectIfc view = viewService.getView(viewName);
      //������ָ������ͼ����û�л�ȡ����Ӧ��ֵ�����򷵻ص�ǰ���ù淶
      if (view == null) {
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
      return partConfigSpecIfc;
    }

    /**
     * ���ָ������ڵ�ǰ·�߱��е�����·�ߺ�װ��·��
     * @param listPartRoute �������
     * @param map CodeManageTable
     * @return String[] ��һ��Ԫ��������·��,�ڶ���Ԫ����װ��·��
     * @throws QMException
     */
    private static String[] getPartMakeAndAssRouteInRouteList(
        ListRoutePartLinkIfc
        listPartRoute,
        CodeManageTable map,ArrayList sendToColl) throws QMException
    {
     // System.out.println("ReportLevelOneUtil-> getPartMakeAndAssRouteInRouteList() listPartRoute= "+listPartRoute);
      Object[] branches = ( (Collection) map.get(listPartRoute)).toArray();
     // System.out.println("---branches="+branches);
      String makeStr = "";
      String assStr = "";
      ArrayList makeList = new ArrayList(); //����·�߼���,Ϊ�˱�֤�ѳ��ֵ�����ڵ㲻�ٳ���,�� ��/��---->��
      ArrayList assList = new ArrayList(); //ͬ��
      if (sendToColl == null) {
        sendToColl = new ArrayList();
      }

      if (branches != null && branches.length > 0)
      {
        for (int j = 0; j < branches.length; j++)
        {
          if (makeStr.length() > 0 && !makeStr.endsWith("/")) {
            makeStr += "/";
          }
          if (assStr.length() > 0 && !assStr.endsWith("/")) {
            assStr += "/";
          }
      //    System.out.println(" branches=   "+branches[j]);

//        CCBegin by leixiao 2009-01-05 ԭ�򣺽����������·��,·�ߴ�·�ߴ�����ȡ
          String routeString=(String)branches[j];
          String makeStrBranch="";
          String assDepartment="";
          if(routeString!=null&&routeString.length()>0){
          String route[]= routeString.split(";");
          for(int k=0;k<route.length;k++){
        	  String r=route[k];
           int s=r.indexOf("@");
          String  longdepartmentName=r.substring(0,s);
          if(longdepartmentName.equals("��"))
        	  longdepartmentName="";
          //System.out.println("���쵥λ��"+longdepartmentName);
          //CCBegin SS5
          longdepartmentName = longdepartmentName.replaceAll("-","��");
          //CCEnd SS5
          String [] de=longdepartmentName.split("��");
          for(int i=0;i<de.length;i++){
         String departmentName=de[i];
         if (makeStrBranch == "") {
        	 makeStrBranch = makeStrBranch + departmentName;
         }
         else{
         makeStrBranch = makeStrBranch + "-" + departmentName;
         }

           if (!sendToColl.contains(departmentName) && departmentName != "") {
               sendToColl.add(departmentName);
             }

          }

          if (!makeList.contains(makeStrBranch)) {
              makeList.add(makeStrBranch);
              makeStr = makeStr + makeStrBranch + "/";
            }

           assDepartment=r.substring(s+1,r.length());
           if(assDepartment.equals("��"))
        	   assDepartment="";
           if (!sendToColl.contains(assDepartment) &&
                   assDepartment != "") {
                 sendToColl.add(assDepartment);
               }

          }
          if (!assList.contains(assDepartment)) {
              assList.add(assDepartment);
              assStr = assStr + assDepartment + "/";

            }
        //  System.out.println("װ�䵥λ��"+assDepartment);
          }

        }
      }
//    CCEnd by leixiao 2009-01-05 ԭ�򣺽����������·��,·�ߴ�·�ߴ�����ȡ
      makeStr = makeStr.endsWith("/") ?
          makeStr.substring(0, makeStr.length() - 1) :
          makeStr;
      //CCBegin SS22
      //assStr = assStr.endsWith("/") ? assStr.substring(0, assStr.length() - 1) :
          //assStr;
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
      //CCEnd SS22
      String[] aaaa = new String[2];

      //System.out.println("    makeStr="+makeStr+"  assStr="+assStr);
      aaaa[0] = makeStr;
      aaaa[1] = assStr;
      makeList = null;
      assList = null;
      return aaaa;
    }

    public static String getTail1(ArrayList sendToColl) {
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
    
    //CCBegin by liunan 2011-06-18 �¹��򣬷�����λ���û�С������������ݳ��Ͳ���J6��
    //����J6L��J6M�����ж���Ҫ���ൺ��ͼ�����ڷ�����λ����ӡ�������
    public static String getTail1(ArrayList sendToColl,boolean flag) {
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
      //CCBegin by liunan 2011-06-18
      if(haha.indexOf("��")==-1&&!flag)
      {
      	if(haha.equals(""))
      	  haha = "��";
      	else
      	  haha = haha + "����";
      }
      //CCEnd by liunan 2011-06-18
      return haha;
    }
    //CCEnd by liunan 2011-06-18

    /**
     * liuming 20070116 add
     * ȡ����������·�ߵĵ�һ�����쵥λ��Ϊ�ü���װ��·�ߣ�
     * ����ҵ����ϼ����ж�������·��(��·��)����ȡÿ������·�ߵĵ�һ�����쵥λ(�ü�����װ��·�ߵĶ�·��)��
     * ������û������·�ߣ���������װ��·�ߡ�
     * @param part ����
     * @return װ��·��
     * @throws QMException
     */
    private static String getAssisRoute(QMPartIfc part) throws QMException {
      TechnicsRouteService routeService =
          (TechnicsRouteService) EJBServiceHelper.getService(
              "TechnicsRouteService");

      String[] prouts = routeService.getRouteString(part);
      String myAssRoute = prouts[0];
      StringTokenizer yy = new StringTokenizer(myAssRoute, "/");
      String parentMakeRouteFirst = "";
      StringTokenizer MM =null;
      while (yy.hasMoreTokens()) {
        String aa = yy.nextToken();
        MM = new StringTokenizer(aa, "--");
        parentMakeRouteFirst += MM.nextToken() + "/";
      }
      if (parentMakeRouteFirst.endsWith("/")) {
        parentMakeRouteFirst = parentMakeRouteFirst.substring(0,
            parentMakeRouteFirst.length() - 1);
      }
      return parentMakeRouteFirst;
    }
 //  CCEnd by leixiao 2009-01-05 ԭ�򣺽����������·��
  
   //  CCBegin by leixiao 2009-01-05 ԭ�򣺽����������·�� 
       /**
     * ��ʾ����ʱ���ͻ��˵��á�(���־û�����)
     * @param routeListID String
     * @param expandByProduct1 String
     * @return ArrayList
     * @throws QMException
     */
    public static ArrayList getAllInformationColl(String routeListID,
                                                  String expandByProduct1) throws
        QMException {
      PersistService pService = (PersistService) EJBServiceHelper.getService(
          "PersistService");
      TechnicsRouteListIfc myRouteList=null;
  			  long t11 = System.currentTimeMillis();
      try {
         myRouteList = (TechnicsRouteListIfc) pService.refreshInfo(routeListID);
          }
     catch (Exception ex) {
      ex.printStackTrace();
          }
      SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
     String  user=sservice.getCurUserInfo().getUsersName();
    String  user1=sservice.getCurUserInfo().getUsersDesc();
    System.out.println("\n\n\n"+"--------------------------------------------------");
      System.out.println("��ǰ�û�Ϊ��"+user+"  "+user1+"��ʼ·�߱�"+" "+routeListID);
     // System.out.println("--------------------------------------------------"+"\n\n\n");
      ArrayList allinfomationColl = new ArrayList(5);
//    CCBeginby leixiao 2009-1-6 ԭ�򣺽����������·��,sendToColl������Ϊ��̬����
      ArrayList sendToColl = new ArrayList();
      expandByProduct = expandByProduct1.equalsIgnoreCase("true");
     // CCBeginby leixiao 2009-1-6 ԭ�򣺽����������·��,�ж�·��Ϊ�յ����

      allinfomationColl.add(getHeader(myRouteList));

      //CCBegin by liunan 2009-02-21
      // CCEndby leixiao 2009-1-6 ԭ�򣺽����������·��
      //ԭ�����£�
      //allinfomationColl.add(getFirstLeveRouteListReport(myRouteList,sendToColl));
      //allinfomationColl.add(getTail1(sendToColl)); //������λ
//    CCBeginby leixiao 2009-1-6 ԭ�򣺽����������·��,sendToColl������Ϊ��̬����
      FolderService folderService = (FolderService) EJBServiceHelper.getService(
        "FolderService");
      FolderIfc folder = folderService.getFolder(myRouteList.getLocation());
      if (folder == null)
      {
        throw new QMException("�������ϼ�" + myRouteList.getLocation() + "û�ж�Ȩ��!");
      }
      boolean isPersonFolder = folderService.isPersonalFolder(folder);
      //�ж���׼�Ƿ��ڸ������ϼУ�����������ɣ�����ȥ�ѱ������������ȡ������������ɡ�
      if (isPersonFolder)
      {
        allinfomationColl.add(getFirstLeveRouteListReport(myRouteList,sendToColl));
        //CCBegin by liunan 2011-06-18 �޸� getTail1 �������� getIsJ6 ������
        //2011-06-23 liunan �Ļ�ȥ
        allinfomationColl.add(getTail1(sendToColl)); //������λ
        //allinfomationColl.add(getTail1(sendToColl, getIsJ6(myRouteList.getRouteListNumber()))); //������λ
        //CCEnd by liunan 2011-06-18
      }
      else
      {
      	//��reportRouteList����������׼master�����ؿ������������ϸ�ͷ�����λ�����棬����ֱ��ʹ�á�
              try
              {
              	System.out.println("checkReport������Ϊ��"+checkReport.size()+"���������������£�");
                Iterator ite1 = checkReport.iterator();
                while (ite1.hasNext())
                {
                  System.out.println("======="+ite1.next());
                }
                if(checkReport.contains(myRouteList.getMasterBsoID()))
                {
                	//System.out.println("==========������Ӧ�����쳣��������");
                  //throw new QMRemoteException("��ǰ��׼" + myRouteList.getRouteListNumber() + "�����������û����ɱ������Ժ�ʹ��!");
                  return null;
                }
                allinfomationColl = getReportRouteList(allinfomationColl, myRouteList,
                                                       sendToColl);
              }
              catch(Exception e)
              {
                e.printStackTrace();
              }
      }

      //CCEnd by liunan 2009-02-21

      allinfomationColl.add(getTail2(myRouteList)); //˵��
      allinfomationColl.add(getWFProcessDetails2(myRouteList)); //������Ϣ
      sendToColl = null;
      //System.out.println("----------��------------------------------------"+"\n\n\n");
long t22 = System.currentTimeMillis();
      System.out.println("����·�߱�"+routeListID+" "+user+"  "+user1+"ʱ��Ϊ�� "+(t22-t11)/1000+" ��");
      System.out.println("--------------------------------------------------"+"\n\n\n");
      return allinfomationColl;

    }
     //  CCEnd by leixiao 2009-01-05 ԭ�򣺽����������·��
     
        /**
     * ��þ��������·��˵��
     * @return Collection
     */
    public static Collection getTail2(TechnicsRouteListIfc listInf) {
      Collection coll = new ArrayList();
      String tip = listInf.getRouteListDescription();
      if (tip == null) {
        return coll;
      }
      ///���˵���˵�����еķָ��   liuming  add 20061212
      String dd = String.valueOf( (char) 1);
      int ddt = tip.indexOf(dd);
      if (ddt != -1) {
        String b1 = tip.substring(0, ddt);
        String b2 = tip.substring(ddt + 1);
        tip = b1 + b2;
      }
      ///////////////////////
      String tip1 = "";
      String tip2 = "";
      if (tip.indexOf("˵����") != -1) {
        tip1 = tip.substring(0, tip.indexOf("˵����"));
        tip2 = tip.substring(tip.indexOf("˵����"));
        coll.add(tip1);
        coll.add(tip2);
      }
      else if (tip.indexOf("˵��:") != -1) {
        tip1 = tip.substring(0, tip.indexOf("˵��:"));
        tip2 = tip.substring(tip.indexOf("˵��:"));
        coll.add(tip1);
        coll.add(tip2);
      }
      else {
        coll.add(tip);
        coll.add(tip1);
      }
      return coll;
    }

    public static Collection getWFProcessDetails(String routeListID) throws
        QMException {
      Vector vec = new Vector();
      vec.add(new ArrayList());
      vec.add(new ArrayList());
      vec.add(new ArrayList());
      vec.add(new ArrayList());
      vec.add("");
      QMQuery query = new QMQuery("WfProcess");
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      BaseValueIfc pbo = pservice.refreshInfo(routeListID);
      query.addCondition(new QueryCondition("businessObjBsoID", "=",
                                            WfEngineHelper.getPBOIdentity(pbo)));
      query.addOrderBy("startTime", true);
      Collection coll = pservice.findValueInfo(query);
      //System.out.println("coll.size()  " + coll.size());
      if (coll != null && coll.size() > 0) {
        Iterator it = coll.iterator();
        WfProcessIfc process = (WfProcessIfc) it.next(); //ȡ���µ���ؽ���
        ArrayList list = getAllActivities(process.getBsoID());
        for (int i = 0; i < list.size(); i++) {
          WfActivityIfc waInfo = (WfActivityIfc) list.get(i);

          ArrayList paticipants = getAllPaticipantOfActivity(waInfo);
          // System.out.println("� " + waInfo.getName() + " �Ĳ�����  " + paticipants);
          if (waInfo.getName().indexOf("��") > -1) {
            vec.setElementAt(paticipants, 0);
          }
          if (waInfo.getName().indexOf("���") > -1) {
            vec.setElementAt(paticipants, 1);
          }
          if (waInfo.getName().indexOf("У��") > -1) {
            vec.setElementAt(paticipants, 2);
          }
          //2006��9��7�գ�������ȡ����ǩ�ߣ����ڱ����н����е�У���ߺ�
          if (waInfo.getName().indexOf("��ǩ") > -1) {
            vec.setElementAt(paticipants, 3);
          }
        }
      }

      TechnicsRouteListIfc routeList111 = (TechnicsRouteListIfc) pservice.
          refreshInfo(routeListID);
      ActorInfo userinfo = (ActorInfo) pservice.refreshInfo(routeList111.
          getCreator());
      vec.setElementAt(userinfo.getUsersDesc(), 4);

      return vec;
    }

    /**
     * ��ȡ�������̲�������Ϣ�����ݽ��Ҫ����Ӵ˷�����   20061201 liuming add
     * Ҫȥ�������ء�֮ǰ�����в����߼�¼��
     * @param routeListID String
     * @return Collection
     * @throws QMException
     */
    public static Collection getWFProcessDetails2(TechnicsRouteListIfc
                                                  routeListInfo) throws
        QMException {
      Vector vec = new Vector();
      vec.add(new ArrayList());
      vec.add(new ArrayList());
      vec.add(new ArrayList());
      vec.add(new ArrayList());
      vec.add("");
      QMQuery query = new QMQuery("WfProcess");
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      query.addCondition(new QueryCondition("businessObjBsoID", "=",
                                            WfEngineHelper.getPBOIdentity(
          routeListInfo)));
      //CCBegin SS2
      query.addAND();
      query.addCondition(new QueryCondition("state", "<>", "OPEN_NOT_RUNNING_NOT_STARTED"));
      //CCEnd SS2
      query.addOrderBy("startTime", true);
      Collection coll = pservice.findValueInfo(query);

      if (coll != null && coll.size() > 0) {
        Iterator it = coll.iterator();
        WfProcessIfc process = (WfProcessIfc) it.next(); //ȡ���µ���ؽ���
        Vector infoVector = new Vector();
        //��ȡ���л
        ArrayList list = getAllActivities(process.getBsoID());
        for (int i = 0; i < list.size(); i++) {
          WfActivityIfc activity = (WfActivityIfc) list.get(i);
          infoVector.addAll(getVoteInformation(activity));
        }
        //����˳��У��-��ǩ-���-��׼��ÿ����������С����ء���Ϊ
        //���������ء����ͽ�֮ǰ�Ĳ�����ȥ��
        if (infoVector.size() > 0) {
          //System.out.println("ȫ�������ߵĸ�����"+infoVector.size());

          int oldSize = infoVector.size();
          Timestamp rejectTime = null;

          ////////////�鿴�Ƿ��С����ء�
          for (int i = 0; i < oldSize; i++) {
            Object[] array = (Object[]) infoVector.elementAt(i);
            String vote = array[2].toString();
            if (vote.indexOf("����") > -1) {
              Timestamp tempTime = (Timestamp) array[3];
              //System.out.println("����ʱ��1 = " + rejectTime);
              if (rejectTime == null) {
                rejectTime = tempTime;
              }
              else {
                if (tempTime != null && tempTime.after(rejectTime)) {
                  rejectTime = tempTime;
                }
              }
              //System.out.println("����ʱ��2 = " + rejectTime);
            }
          }

          Vector tv = new Vector();
          ////////////����С����ء��������
          if (rejectTime != null) {
          //  System.out.println("--------------�в���-------------");
            //System.out.println("����ʱ�� = " + rejectTime);
            for (int j = 0; j < oldSize; j++) {
              Object[] array = (Object[]) infoVector.elementAt(j);
              Timestamp time = (Timestamp) array[3];
              //String name = array[0].toString();
              //String user = array[1].toString();
              //System.out.println("����o = "+name+user+time);
              if (time.after(rejectTime)) { //ֻ�������ڲ���ʱ��ļ�¼
                tv.add(array);
              }
            }

            //���ǣ����ص���ǩ�˵��������ʱУ���˾ͱ����˵��ˣ���Ҫ�һ���  20071022
            boolean noJiao = true;
            if (tv.size() > 0) {
              for (int t = 0; t < tv.size(); t++) {
                Object[] array = (Object[]) tv.elementAt(t);
                String name = array[0].toString();
                if (name.indexOf("У��") > -1) {
                  noJiao = false;
                  break;
                }
              }

              if (noJiao) { //û��У����
                for (int g = 0; g < oldSize; g++) {
                  Object[] array = (Object[]) infoVector.elementAt(g);
                  String name = array[0].toString();
                  if (name.indexOf("У��") > -1) {
                    tv.add(array);
                    break;
                  }
                }
              }
            } /////////////////////////////20071022

          }
          else {
           // System.out.println("--------------�޲���-------------");
            tv = infoVector;
          }

          ///////////////////////////////////////////





          ArrayList jiaoduiVector = new ArrayList();
          ArrayList huiqianVector = new ArrayList();
          ArrayList shenheVector = new ArrayList();
          ArrayList pizhunVector = new ArrayList();
          /////////////�Ի��Ϣ���з��ࣨУ�ԡ���ǩ����ˡ���׼��
          if (tv.size() > 0) {
          //  System.out.println("--------------��ʣ��-------------");
            int newSize = tv.size();
            for (int k = 0; k < newSize; k++) {
              Object[] array = (Object[]) tv.elementAt(k);
              String name = array[0].toString();
              //System.out.println("���� = " + name);
              String user = array[1].toString();
              if (name.indexOf("��") > -1) {
                //System.out.println("���� = "+name);
                //�������׼���Ҫ�ڲ����ߺ��������׼ʱ�䣬��ʱ��ֻҪ���ڣ���Ҫ�����ʱ���֡���
                String modifyTime = array[3] + "";
                int i = modifyTime.trim().indexOf(" ");
                modifyTime = modifyTime.substring(0, i);
                pizhunVector.add(user + "��" + modifyTime);
              }
              else if (name.indexOf("���") > -1) {
                //System.out.println("���� = "+name);
                if (!shenheVector.contains(user))
                  shenheVector.add(user);
              }
              else if (name.indexOf("У��") > -1) {
                //System.out.println("���� = "+name);
                if (!jiaoduiVector.contains(user))
                  jiaoduiVector.add(user);
              }
              else if (name.indexOf("��ǩ") > -1) {
                //System.out.println("���� = "+name);
                if (!huiqianVector.contains(user))
                  huiqianVector.add(user);
              }
            }
          } ///////////////////////�������!

          vec.setElementAt(pizhunVector, 0);
          vec.setElementAt(shenheVector, 1);
          vec.setElementAt(jiaoduiVector, 2);
          vec.setElementAt(huiqianVector, 3);
          pizhunVector=null;
          shenheVector=null;
          jiaoduiVector=null;
          huiqianVector=null;
          infoVector = null;
          tv=null;
        }
      }
      ActorInfo userinfo = (ActorInfo) pservice.refreshInfo(routeListInfo.
          getCreator());
      vec.setElementAt(userinfo.getUsersDesc(), 4);
      return vec;
    }

//  CCBegin by leixiao 2009-1-6 ԭ�򣺽����������·��,�����Ż�
    public static Vector getVoteInformation(WfActivityIfc waInfo) throws
        QMException {
      Vector v = new Vector();
      HashMap AssignmentMap=new HashMap();
      String activityID = waInfo.getBsoID();
      PersistService persistService = (PersistService) EJBServiceHelper.
          getService(
              "PersistService");
      QMQuery query = new QMQuery("WorkItem");
      query.addCondition(new QueryCondition("sourceID", "=", activityID));
      query.addAND();
      query.addCondition(new QueryCondition("completedBy",QueryCondition.NOT_NULL));
      Iterator iterator = (persistService.findValueInfo(query)).iterator();
      String name = waInfo.getName();
      Object[] tempInfo = null;
      while (iterator.hasNext()) {
        WorkItemIfc workitemIfc = (WorkItemIfc) iterator.next();
        Timestamp modifyTime = workitemIfc.getModifyTime();
        String ownerid = workitemIfc.getOwner();
          ActorInfo userinfo = (ActorInfo) persistService.refreshInfo(ownerid);
          String user = userinfo.getUsersDesc();

          String WfAssignmentID = workitemIfc.getParentWA();
          String vote=(String)AssignmentMap.get(WfAssignmentID);
          if(vote==null)
        		  {
        	 // System.out.println("�����߽���-------");
        	  StringBuffer votebuffer=new StringBuffer();
        	  QMQuery query1=new QMQuery("AssignmentBallotLink","WfBallot");
        	  query1.addCondition(0,new QueryCondition("leftBsoID","=",WfAssignmentID));
        	  query1.addAND();
        	  query1.addCondition(0,1,new QueryCondition("rightBsoID","bsoID"));
        	  query1.addAND();
        	  query1.addCondition(1,new QueryCondition("eventList",QueryCondition.NOT_NULL));
        	  query1.setVisiableResult(0);
        	  Collection coll = persistService.findValueInfo(query1);
        	  for (Iterator iterator1 = coll.iterator(); iterator1.hasNext(); ) {
        		  WfBallotInfo ballot=(WfBallotInfo) iterator1.next();
                  Vector vec = ballot.getEventList();
                  for (int i = 0; i < vec.size(); i++) {
                   // vote += vec.elementAt(i) + "��";
                    votebuffer.append(vec.elementAt(i));
                    if(i!=vec.size()-1)
                    {
                    	votebuffer.append("��");
                    }
                  }
                 // System.out.println("meiyoujieguo -----"+votebuffer);
        	  }
        	  vote=votebuffer.toString();
        	  AssignmentMap.put(WfAssignmentID, vote);

        		  }



          /*
          QMQuery query1 = new QMQuery("AssignmentBallotLink","WfBallot");
          query1.addCondition(new QueryCondition("leftBsoID", "=", WfAssignmentID));
          query1.addCondition(new QueryCondition("rightBsoID", "bsoID"));


          Collection coll = persistService.findValueInfo(query1);
          System.out.println("����WfAssignmentID---"+WfAssignmentID);
          System.out.println("����AssignmentBallotLink---"+coll.size());
          if (coll.size() == 0) {
            continue;
          }

          Timestamp modifyTime = workitemIfc.getModifyTime();
          String vote = "";
          for (Iterator iterator1 = coll.iterator(); iterator1.hasNext(); ) {
            AssignmentBallotLinkInfo assignmentBallotLink = (
                AssignmentBallotLinkInfo) iterator1.next();
            String voteID = assignmentBallotLink.getRightBsoID();
            WfBallotInfo ballot = (WfBallotInfo) persistService.refreshInfo(
                voteID);
            Vector vec = ballot.getEventList();
            for (int i = 0; i < vec.size(); i++) {
              vote += vec.elementAt(i) + "��";
            }
          }*/
          //vote = vote.endsWith("��") ? vote.substring(0, vote.length() - 1) : vote;
          if (vote!=null&&vote.length() > 0) {
            tempInfo = new Object[4];
            tempInfo[0] = name;
            tempInfo[1] = user;
            tempInfo[2] = vote;
            tempInfo[3] = modifyTime;
            v.add(tempInfo);
            tempInfo = null;
          }

      }

      return v;
    }
//  CCEnd by leixiao 2009-1-6 ԭ�򣺽����������·��

//  CCBeginby leixiao 2009-1-6 ԭ�򣺽����������·��
    /**
     * ���ظ����������е����й��ڵĵĻ
     * @param   process    �����Ĺ���������
     * @return Iterator    �������ֵ���󼯺�
     * @throws WfException
     */
    public static ArrayList getAllActivities(String bsoID) throws QMException {

      ArrayList list = new ArrayList();
      QMQuery query = new QMQuery("WfAssignedActivity");
      query.addCondition(new QueryCondition("parentProcessBsoID", "=", bsoID));
      list.addAll( ( (PersistService) EJBServiceHelper.getPersistService()).
                  findValueInfo(query));
      return list;
    }

    /**
     *  ���ݻ�õ����еĹ����ÿ��������������߼��Ǹû�����в�����
     *
     */
    public static ArrayList getAllPaticipantOfActivity(WfActivityIfc waInfo) throws
        QMException {

      ArrayList paticipants = new ArrayList();
      PersistService persistService = (PersistService) EJBServiceHelper.
          getPersistService();
      //  System.out.println("waInfo.getName()  " + waInfo.getName());
      QMQuery query = new QMQuery("WorkItem");
      query.addCondition(new QueryCondition("sourceID", "=", waInfo.getBsoID()));
      Iterator iterator = (persistService.findValueInfo(query)).iterator();
      while (iterator.hasNext()) {
        WorkItemIfc workitemIfc = (WorkItemIfc) iterator.next();
        String ownerid = workitemIfc.getOwner();
        if (workitemIfc.getCompletedBy() != null) {
          ActorInfo userinfo = (ActorInfo) persistService.refreshInfo(ownerid);
          if (waInfo.getName().indexOf("��") > -1) {
            //�������׼���Ҫ�ڲ����ߺ��������׼ʱ�䣬��ʱ��ֻҪ���ڣ���Ҫ�����ʱ���֡���
            String modifyTime = workitemIfc.getModifyTime() + "";
            int i = modifyTime.trim().indexOf(" ");
            modifyTime = modifyTime.substring(0, i);
            paticipants.add(userinfo.getUsersDesc() + "��" +
                            modifyTime);

          }
          else {
            String userdesc = userinfo.getUsersDesc();
            if (!paticipants.contains(userdesc)) {
              paticipants.add(userdesc);
            }
          }
        }

        /*��ʱ������δ��ɵ�,������ȥ��ע�ͼ���
                 else {
          ActorInfo userinfo = (ActorInfo) persistService.refreshInfo(ownerid);
          paticipants.add(paticipants + userinfo.getUsersDesc() );
                 }
         */
      }

      return paticipants;
    }
    //  CCEnd by leixiao 2009-1-6 ԭ�򣺽����������·��

//CCBebin by liunan 2009-02-21
    /**
     * ��׼���״λ������汾�����ɱ���ʱҪ�������㴦������ϸȻ�󱣴浽���ݿ���У�֮�󶼴ӱ���ֱ�ӻ�ȡ��
     * �����ϸ�ͷ�����λ����ӵ����ؼ����С�ͨ�������ݿ�ֱ�����ӣ�
     * ��sql����ϸ���ݲ��뵽ר������׼�������reportRouteList���У�
     * Ȼ����ʹ��ʱ������׼masterid������á�
     * @param allinfomationColl ArrayList ��װ�ļ��ϣ�
     * �Ѿ��������׼����ı�ͷ��Ϣ���ڴ˷�������Ҫ�������������ϸ�ͷ�����λ��
     * @param myRouteList TechnicsRouteListIfc ��׼��
     * @param sendToColl ArrayList ������λ���ϡ�
     * @return ArrayList
     */
    public static ArrayList getReportRouteList(ArrayList allinfomationColl, TechnicsRouteListIfc myRouteList, ArrayList sendToColl)
  {
  	//System.out.println("=====================������׼��������������");
    Connection conn = null;
    Statement stmt =null;
    ResultSet rs=null;
  	try
  	{
  		//CCBegin by liunan 2011-05-17 ������׼���ж��Ƿ�J6�������Ӳ��� isJ6 
  		boolean isJ6 = getIsJ6(myRouteList.getRouteListNumber());
  		//CCEnd by liunan 2011-05-17
  		
  	  //������������׼��masterid����������
    	conn = PersistUtil.getConnection();
    	stmt = conn.createStatement();
    	String countIdSql = "select count(*) from reportroutelist where routeListMasterBsoID='"+myRouteList.getMasterBsoID()+"' and routeListBsoID='"+myRouteList.getBsoID()+"' and indexnum='1'";
    	String countListSql = "select count(*) from reportroutelist where routeListMasterBsoID='"+myRouteList.getMasterBsoID()+"'";
    	//CCBegin SS7
    	//CCBegin SS1
    	//String listSql = "select indexNum,changeSign,partNumber,partVersion,partName,countAve,makeRoute,assembleRoute,countAll,parentPart,partID,remark,isChange from reportroutelist where routeListMasterBsoID='"+myRouteList.getMasterBsoID()+"' order by indexnum";
    	//String listSql = "select indexNum,changeSign,partNumber,partVersion,partName,countAve,makeRoute,assembleRoute,countAll,parentPart,partID,remark,isChange,safety from reportroutelist where routeListMasterBsoID='"+myRouteList.getMasterBsoID()+"' order by indexnum";
    	String listSql = "select indexNum,changeSign,partNumber,partVersion,partName,countAve,makeRoute,assembleRoute,countAll,parentPart,partID,remark,isChange,safety,colorflag from reportroutelist where routeListMasterBsoID='"+myRouteList.getMasterBsoID()+"' order by indexnum";
    	//CCEnd SS1
    	//CCEnd SS7
    	String deleteSql = "delete from reportroutelist where routeListMasterBsoID='"+myRouteList.getMasterBsoID()+"'";
      String toUnitSql = "select toUnit from reportroutelist where routeListMasterBsoID='"+myRouteList.getMasterBsoID()+"' and routeListBsoID='"+myRouteList.getBsoID()+"' and indexnum='1'";
      String toUnit = "";
    	rs = stmt.executeQuery(countListSql);
      rs.next();
      int countList = rs.getInt(1);
    	//�жϽ���Ƿ�Ϊ�գ�������÷��������ϸ��Ȼ�󱣴棻
    	//��������ϸ���ж��Ƿ����£����򷵻أ�����ɾ����ϸ���ٻ����ϸ�����档
    	if(countList>0)
    	{
  		  //������������׼��masterid��bsoid����������
  		  rs = stmt.executeQuery(countIdSql);
        rs.next();
        int countId = rs.getInt(1);
  		  //�������Ϊ1����ʾҪ���ɱ������׼����л��汨����׼�汾һ�£��򷵻أ������������ɡ�
  		  if(countId==1)
  		  {
  		  	System.out.println("·�߱�ֱ�����ɡ�����");
  			  //������
  			  rs = stmt.executeQuery(toUnitSql);
  			  rs.next();
  			  toUnit = rs.getString(1);

  			  //������������׼masterid���������ֶΡ�
  			  rs = stmt.executeQuery(listSql);

  			  //CCBegin by liunan 2011-05-17 ������׼���ж��Ƿ�J6�������Ӳ��� isJ6 
  			  //allinfomationColl = handleResultData(allinfomationColl,rs,toUnit);
  			  allinfomationColl = handleResultData(allinfomationColl,rs,toUnit,isJ6);
  			  //CCEnd by liunan 2011-05-17
  	  	}
  		  else
  		  {
  		  	System.out.println("·�߱�ȫ�����ɡ�����");
                        //checkReport.add(myRouteList.getBsoID());
                        checkReport.add(myRouteList.getMasterBsoID());

  			  //ɾ����ɾ��masteridΪ��ǰ��׼������
  			  stmt.executeQuery(deleteSql);

  			  //�����ϸ
  			  Collection dataList = getFirstLeveRouteListReport(myRouteList, sendToColl);

  			  //�����������ϸ����
  			  //CCBegin by liunan 2011-06-18 �޸� getTail1 �������� getIsJ6 ������
  			  //2011-06-23 liunan �Ļ�ȥ
  			  handleInsertData(myRouteList, dataList, getTail1(sendToColl), stmt);
  			  //handleInsertData(myRouteList, dataList, getTail1(sendToColl, isJ6), stmt);
  			  //CCEnd by liunan 2011-06-18

  			  //������
  			  rs = stmt.executeQuery(toUnitSql);
  			  rs.next();
  			  toUnit = rs.getString(1);
  	    	rs = stmt.executeQuery(listSql);
  	  	  //CCBegin by liunan 2011-05-17 ������׼���ж��Ƿ�J6�������Ӳ��� isJ6 
  	  	  //allinfomationColl = handleResultData(allinfomationColl,rs,toUnit);
  	  	  allinfomationColl = handleResultData(allinfomationColl,rs,toUnit,isJ6);
  	  	  //CCEnd by liunan 2011-05-17

//                            checkReport.remove(myRouteList.getBsoID());
  	  	checkReport.remove(myRouteList.getMasterBsoID());
  		  }
  	  }
  	  else
  	  {
  	  	System.out.println("·�߱�ȫ�����ɡ�����");
//  	  	checkReport.add(myRouteList.getBsoID());
  	   checkReport.add(myRouteList.getMasterBsoID());

  	  	//�����ϸ
  	  	Collection resultList = getFirstLeveRouteListReport(myRouteList,sendToColl);

  	  	//�����������ϸ����
  			//CCBegin by liunan 2011-06-18 �޸� getTail1 �������� getIsJ6 ������
  			//2011-06-23 liunan �Ļ�ȥ
  	  	handleInsertData(myRouteList, resultList, getTail1(sendToColl), stmt);
  	  	//handleInsertData(myRouteList, resultList, getTail1(sendToColl, isJ6), stmt);
  			//CCEnd by liunan 2011-06-18

  	  	//������
  	  	rs = stmt.executeQuery(toUnitSql);
  	  	rs.next();
  	  	toUnit = rs.getString(1);
  	  	rs = stmt.executeQuery(listSql);
  	  	//CCBegin by liunan 2011-05-17 ������׼���ж��Ƿ�J6�������Ӳ��� isJ6 
  	  	//allinfomationColl = handleResultData(allinfomationColl,rs,toUnit);
  	  	allinfomationColl = handleResultData(allinfomationColl,rs,toUnit,isJ6);
  	  	//CCEnd by liunan 2011-05-17

//                           checkReport.remove(myRouteList.getBsoID());
  	                     checkReport.remove(myRouteList.getMasterBsoID());
    	}
    	//��ղ��ر�sql��������
  	  rs.close();
  	  //�ر�Statement
  	  stmt.close();
  	  //�ر����ݿ�����
      conn.close();

      //System.out.println("=====================�˳���׼��������������");
      return allinfomationColl;
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
        if(checkReport.contains(myRouteList.getMasterBsoID()))
        {
        	System.out.println("�����쳣�����ջ�checkReport�е�ǰ��׼�ı�ʶ��");
//        	checkReport.remove(myRouteList.getBsoID());
        	 checkReport.remove(myRouteList.getMasterBsoID());
        }
        return allinfomationColl;
    }
  }

  /**
   * ��sql����ϸ��������������������
   * @param myRouteList TechnicsRouteListIfc ��׼��
   * @param dataList Collection ��ϸ���ݡ�
   * @param sendToColl String ������λ��
   * @param stmt Statement ���ݿ��࣬����ִ��sql������
   */
  private static void handleInsertData(TechnicsRouteListIfc myRouteList, Collection dataList, String sendToColl, Statement stmt)
  {
    //CCBegin SS7
    //CCBegin SS1
    //String sqlBase = "INSERT INTO reportroutelist (routeListMasterBsoID,routeListBsoID,indexNum,changeSign,partNumber,partVersion,partName,countAve,makeRoute,assembleRoute,countAll,parentPart,partID,remark,isChange,toUnit) VALUES ";
    //String sqlBase = "INSERT INTO reportroutelist (routeListMasterBsoID,routeListBsoID,indexNum,changeSign,partNumber,partVersion,partName,countAve,makeRoute,assembleRoute,countAll,parentPart,partID,remark,isChange,toUnit,safety) VALUES ";
    String sqlBase = "INSERT INTO reportroutelist (routeListMasterBsoID,routeListBsoID,indexNum,changeSign,partNumber,partVersion,partName,countAve,makeRoute,assembleRoute,countAll,parentPart,partID,remark,isChange,toUnit,safety,colorflag) VALUES ";
    //CCEnd SS1
    //CCEnd SS7
    try
    {
      if (dataList != null && dataList.size() > 0)
      {
        Iterator ite = dataList.iterator();
        int curCount = 0;
        while (ite.hasNext())
        {
          //arrayObjs������Ԫ������Ϊ�����[0]�����ı��[1]���㲿�����[2]���㲿���汾[3]���㲿������[4]��ÿ������[5]������·��[6]��
          //���ϣ�װ��·�ߡ��ϼ��������ϼ�����[7]���㲿��bsoid[8]����ע[9]��Ӱ�컥��[10]��
          //indexNum(1),changeSign(2),partNumber(3),partVersion(4),partName(5),countAve(6),makeRoute(7),
          //assembleRoute(8),countAll(9),parentPart(10),partID(11),remark(12),isChange(13),toUnit(14)
          Object[] arrayObjs = (Object[]) ite.next();
          ArrayList informationlist = (ArrayList) arrayObjs[7];
          String[] info = handleInfo(informationlist);
          //ֻ�ڵ�һ�������м�¼·�߱����׼bsoid�ͷ�����λ����ʱ��ͨ��masterid�����1��������
          if(curCount == 0)
          {
          	/*System.out.println("('"+myRouteList.getMasterBsoID()+"', "+"'"+myRouteList.getBsoID()+"', "+"'"+(String) arrayObjs[0]+"', "
                              +"'"+(String) arrayObjs[1]+"', "+"'"+(String) arrayObjs[2]+"', "+"'"+(String) arrayObjs[3]+"', "
                              +"'"+(String) arrayObjs[4]+"', "+"'"+(String) arrayObjs[5]+"', "+"'"+(String) arrayObjs[6]+"', "
                              +"'"+info[0]+"', "+"'"+info[1]+"', "+"'"+info[2]+"', "+"'"+(String) arrayObjs[8]+"', "
                              +"'"+(String) arrayObjs[9]+"', "+"'"+(String) arrayObjs[10]+"', "+"'"+sendToColl+"')");*/
            stmt.executeQuery(sqlBase+"('"+myRouteList.getMasterBsoID()+"', "+"'"+myRouteList.getBsoID()+"', "+"'"+Integer.parseInt((String) arrayObjs[0])+"', "
                              +"'"+(String) arrayObjs[1]+"', "+"'"+(String) arrayObjs[2]+"', "+"'"+(String) arrayObjs[3]+"', "
                              +"'"+(String) arrayObjs[4]+"', "+"'"+(String) arrayObjs[5]+"', "+"'"+(String) arrayObjs[6]+"', "
                              +"'"+info[0]+"', "+"'"+info[1]+"', "+"'"+info[2]+"', "+"'"+(String) arrayObjs[8]+"', "
                              //CCBegin SS12
                              //CCBegin SS7
                              //CCBegin SS1
                              //+"'"+(String) arrayObjs[9]+"', "+"'"+(String) arrayObjs[10]+"', "+"'"+sendToColl+"')");
                              //+"'"+(String) arrayObjs[9]+"', "+"'"+(String) arrayObjs[10]+"', "+"'"+sendToColl+"', "+"'"+(String) arrayObjs[11]+"')");
//                              +"'"+(String) arrayObjs[9]+"', "+"'"+(String) arrayObjs[10]+"', "+"'"+sendToColl+"', "+"'"+(String) arrayObjs[11]+"', "+"'"+(String) arrayObjs[12]+"')");
                              //CCEnd SS1
                              //CCEnd SS7
                              +"'"+(String) arrayObjs[9]+"', "+"'"+(String) arrayObjs[10]+"', "+"'"+sendToColl+"', "+"'"+(String) arrayObjs[12]+"', "+"'"+(String) arrayObjs[13]+"')");
                              //CCEnd SS12
          }
          else
          {
          	/*System.out.println("==================================================================");
          	System.out.println("'"+myRouteList.getMasterBsoID()+"', "+"'', "+"'"+(String) arrayObjs[0]+"', "
                            +"'"+(String) arrayObjs[1]+"', "+"'"+(String) arrayObjs[2]+"', "+"'"+(String) arrayObjs[3]+"', "
                            +"'"+(String) arrayObjs[4]+"', "+"'"+(String) arrayObjs[5]+"', "+"'"+(String) arrayObjs[6]+"', "
                            +"'"+info[0]+"', "+"'"+info[1]+"', "+"'"+info[2]+"', "+"'"+(String) arrayObjs[8]+"', "
                            +"'"+(String) arrayObjs[9]+"', "+"'"+(String) arrayObjs[10]+"', "+"''");*/
            stmt.executeQuery(sqlBase+"('"+myRouteList.getMasterBsoID()+"', "+"'', "+"'"+Integer.parseInt((String) arrayObjs[0])+"', "
                            +"'"+(String) arrayObjs[1]+"', "+"'"+(String) arrayObjs[2]+"', "+"'"+(String) arrayObjs[3]+"', "
                            +"'"+(String) arrayObjs[4]+"', "+"'"+(String) arrayObjs[5]+"', "+"'"+(String) arrayObjs[6]+"', "
                            +"'"+info[0]+"', "+"'"+info[1]+"', "+"'"+info[2]+"', "+"'"+(String) arrayObjs[8]+"', "
                            //CCBegin SS12
                            //CCBegin SS7
                            //CCBegin SS1
                            //+"'"+(String) arrayObjs[9]+"', "+"'"+(String) arrayObjs[10]+"', "+"'')");
                            //+"'"+(String) arrayObjs[9]+"', "+"'"+(String) arrayObjs[10]+"', "+"'', "+"'"+(String) arrayObjs[11]+"')");
//                            +"'"+(String) arrayObjs[9]+"', "+"'"+(String) arrayObjs[10]+"', "+"'', "+"'"+(String) arrayObjs[11]+"', "+"'"+(String) arrayObjs[12]+"')");
                            //CCEnd SS1
                            //CCEnd SS7
                            +"'"+(String) arrayObjs[9]+"', "+"'"+(String) arrayObjs[10]+"', "+"'', "+"'"+(String) arrayObjs[12]+"', "+"'"+(String) arrayObjs[13]+"')");
                            //CCEnd SS12
          }
          curCount++;
          //�ͷŶ��󣬼����ڴ�ռ�á�
          arrayObjs = null;
          informationlist = null;
          info = null;
        }
      }
    }
    catch (SQLException ex)
    {
        ex.printStackTrace();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * ������Ϣ���ϡ�������·�ߡ��ϼ������ϼ����ļ��Ϸֽ�������ַ�����
   * @param informationlist ArrayList ����·�ߡ��ϼ������ϼ����ļ��ϡ�
   * @return String[] ����Ԫ�ء�����¶�ڡ��ϼ������ϼ������ַ�����
   */
  private static String[] handleInfo(ArrayList informationlist)
  {
    String[] info = new String[3];
    try
    {
      if (informationlist != null && informationlist.size() > 0)
      {
        String assembleRoute = "";
        String countAll = "";
        String parentPart = "";
        String[] informationStr = new String[3];
        Iterator ite1 = informationlist.iterator();
        while (ite1.hasNext())
        {
          informationStr = (String[]) ite1.next();
          //System.out.println(informationStr[0]+"===="+informationStr[1]+"===="+informationStr[2]);
          if (assembleRoute.equals(""))
          {
            assembleRoute = stringDeal(informationStr[0]);
          }
          else
          {
            assembleRoute = assembleRoute + "," + stringDeal(informationStr[0]);
          }
          if (countAll.equals(""))
          {
            countAll = stringDeal(informationStr[1]);
          }
          else
          {
            countAll = countAll + "," + stringDeal(informationStr[1]);
          }
          if (parentPart.equals(""))
          {
            parentPart = stringDeal(informationStr[2]);
          }
          else
          {
            parentPart = parentPart + "," + stringDeal(informationStr[2]);
          }
        }
        info[0] = assembleRoute;
        info[1] = countAll;
        info[2] = parentPart;
        //�ͷŶ��󣬼����ڴ�ռ�á�
        informationStr = null;
        assembleRoute = null;
        countAll = null;
        parentPart = null;
      }
      else
      {
        info[0] = "##";
        info[1] = "##";
        info[2] = "##";
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return info;

  }

  /**
   * �ַ����������ڴ�������·�ߡ��ϼ������ϼ���Ϊ�������
   * @param str String �ַ�����
   * @return String ����շ���##��
   */
  private static String stringDeal(String str)
  {
     if (str == null || str.equals(""))
     {
       return "##";
     }
     else
     {
         return str;
     }
  }

  /**
   * �������ݿ��������Ľ�����д����������ɱ�������ݽṹ��
   * @param allinfomationColl ArrayList ���ɱ�������ݽṹ���˴���Ҫ�����ϸ�ͷ�����λ��
   * @param rs ResultSet ���ݿⷵ�ؽ����
   * @param toUnit String ������λ��
   * @return ArrayList ���صı�����������ݽṹ��
   */
  //CCBegin by liunan 2011-05-17 ������׼���ж��Ƿ�J6�������Ӳ��� isJ6 
  //private static ArrayList handleResultData(ArrayList allinfomationColl, ResultSet rs, String toUnit)
  private static ArrayList handleResultData(ArrayList allinfomationColl, ResultSet rs, String toUnit, boolean isJ6)
  //CCEnd by liunan 2011-05-17
  {
    try
    {
    	//CCBegin SS23
    	PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
    	//CCEnd SS23
      ArrayList result = new ArrayList();
      //����Ԫ�أ��ֱ�Ϊ��װ��·�ߡ������ϼ��������͡��ϼ����š�
      String[] informationStr = null ;
      ArrayList informationlist = null;
      while(rs.next())
      {
        //arrayObjs������Ԫ������Ϊ����š����ı�ǡ��㲿����š��㲿���汾���㲿�����ơ�ÿ������������·�ߡ�
        //���ϣ�װ��·�ߡ��ϼ��������ϼ��������㲿��bsoid����ע��Ӱ�컥����
        //indexNum(1),changeSign(2),partNumber(3),partVersion(4),partName(5),countAve(6),makeRoute(7),
        //assembleRoute(8),countAll(9),parentPart(10),partID(11),remark(12),isChange(13),toUnit(14)
        //��װ��·�ߡ������ϼ��������͡��ϼ����š������ڱ��ж��á�,���ָ
        String[] assembleRoute = rs.getString(8).split(",");
        String[] countAll = rs.getString(9).split(",");
        String[] parentPart = rs.getString(10).split(",");
        //CCBegin by leixiao 2010-6-2 �����׼���ӷ�ͼ������
        //CCBegin by liunan 2011-05-17 ������׼���ж��Ƿ�J6�������Ӳ��� isJ6
        //String fatushu=futushucount(rs.getString(3),rs.getString(7),rs.getString(8));
        //CCBegin SS15
        //String fatushu=futushucount(rs.getString(3),rs.getString(7),rs.getString(8),isJ6);
        String fatushu="";
        //CCBegin SS18
        if(rs.getString(2)==null)
        {
        	fatushu=futushucount(rs.getString(3),rs.getString(7),rs.getString(8),isJ6);
        }
        else
        //CCEnd SS18
        if(!rs.getString(2).equals("F"))
        {
        	fatushu=futushucount(rs.getString(3),rs.getString(7),rs.getString(8),isJ6);
        }
        //CCEnd SS15
        //CCEnd by liunan 2011-05-17
        //CCEnd by leixiao 2010-6-2 �����׼���ӷ�ͼ������
        //CCBegin by leixiao 2010-7-9 ����ǹ��岿������ע�мӡ����岿����������������
        String remark=stringNullDeal(rs.getString(12));
        if(stringNullDeal(rs.getString(11)).startsWith("GenericPart_"))
        	remark="���岿�� "+remark;
        //CCEnd by leixiao 2010-7-9 
        informationlist = new ArrayList();
        for(int i = 0 ; i < assembleRoute.length ; i++)
        {
        	//System.out.println("=====("+assembleRoute[i]+")=====("+countAll[i]+")=====("+countAll[i]+")=====");
        	informationStr = new String[3];
          informationStr[0] = assembleRoute[i].replaceAll("##","");
          informationStr[1] = countAll[i].replaceAll("##","");
          informationStr[2] = parentPart[i].replaceAll("##","");
          informationlist.add(informationStr);
          informationStr = null;
        }
        //System.out.println("rs.getString(5)========="+rs.getString(5));
        //System.out.println("stringNullDeal(rs.getString(5))==========="+stringNullDeal(rs.getString(5)));
        //CCBegin by leixiao 2010-6-2 �����׼���ӷ�ͼ������
        //CCBegin SS9
        String vs = stringNullDeal(rs.getString(4));
        if(!vs.equals("")&&isQQGroupUser().equals("true"))
        {
        	vs = vs.replaceAll("/", "");
        }
        //CCBegin SS20
        if(stringNullDeal(rs.getString(3)).indexOf("/")>1)
        {
        	vs = "";
        }
        //CCEnd SS20
        //CCBegin SS11
        String mr = stringNullDeal(rs.getString(7));
        if(mr!=null)
        {
        	mr = mr.replaceAll("-","");
        }
        //CCEnd SS11
        //CCBegin SS23
        String partID = stringNullDeal(rs.getString(11));
        QMPartIfc pp = (QMPartIfc) pservice.refreshInfo(partID);
        String partNumber = stringNullDeal(rs.getString(3));
        String partName = stringNullDeal(rs.getString(5));
        if(pp!=null)
        {
        	partNumber = pp.getPartNumber();
        	if(partName.indexOf(pp.getPartName())==-1)
        	{
        		QMQuery query2 = new QMQuery("StringDefinition");
        		QueryCondition qc2 = new QueryCondition("displayName", " = ", "�ĵ���Ϣ");
        		query2.addCondition(qc2);
        		Collection col2 = pservice.findValueInfo(query2, false);
        		String docnoteiba = "";
        		Iterator iba2 = col2.iterator();
        		if (iba2.hasNext())
        		{
        			StringDefinitionIfc s = (StringDefinitionIfc) iba2.next();
        			docnoteiba = s.getBsoID();
        		}
        		jfuputil jf = new jfuputil();
        		ArrayList partholer = new ArrayList();
        		partholer.add(pp.toString());
        		HashMap returnDocnoteList = jf.getiba(partholer, docnoteiba);
        		String docnotevalue = (String) returnDocnoteList.get(pp.getBsoID());
        		if(docnotevalue==null)
        		{
        			docnotevalue = "";
        		}
        		if(!docnotevalue.equals(""))
        		{
        			docnotevalue = "("+docnotevalue+")";
        		}
        		partName = pp.getPartName()+docnotevalue;
        	}
        }
        //Object[] arrayObjs = {stringNullDeal(rs.getString(1)), stringNullDeal(rs.getString(2)), stringNullDeal(rs.getString(3)),
        Object[] arrayObjs = {stringNullDeal(rs.getString(1)), stringNullDeal(rs.getString(2)), partNumber,
                    //stringNullDeal(rs.getString(4)), stringNullDeal(rs.getString(5)), stringNullDeal(rs.getString(6)),
                    //vs, stringNullDeal(rs.getString(5)), stringNullDeal(rs.getString(6)),
                    vs, partName, stringNullDeal(rs.getString(6)),
                    //CCEnd SS23
                    //CCEnd SS9
                    //CCBegin SS11
                    //stringNullDeal(rs.getString(7)), informationlist, stringNullDeal(rs.getString(11)),
                    mr, informationlist, stringNullDeal(rs.getString(11)),
                    //CCEnd SS11
                    //CCBegin SS7
                    //CCBegin SS1
                    //remark, stringNullDeal(rs.getString(13)),fatushu};
                    //remark, stringNullDeal(rs.getString(13)), fatushu, stringNullDeal(rs.getString(14))};
                    remark, stringNullDeal(rs.getString(13)), fatushu, stringNullDeal(rs.getString(14)), stringNullDeal(rs.getString(15))};
                    //CCEnd SS1
                    //CCEnd SS7
        //CCEnd by leixiao 2010-6-2 �����׼���ӷ�ͼ������
        //���һ����ϸ
        result.add(arrayObjs);
      }
      //System.out.println("���ؽ������Ϊ�� "+result.size()+" ��");
      allinfomationColl.add(result);
      allinfomationColl.add(toUnit);

      //�ͷŶ��󣬼����ڴ�ռ�á�
      informationStr = null;
      informationlist = null;
      result = null;
      toUnit = null;
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return allinfomationColl;
  }
  //CCBegin by leixiao 2010-6-2 �����׼���ӷ�ͼ������
  /**
   * ���㷢ͼ��
   * @param str String ����·�ߡ�װ��·��
   * @return String ��ͼ��
   * liunan 2011-05-02 �˴��޸��������ݣ�
   * 1��·����ͬʱ���֡��ܡ������á�������+1��
   * 2�����·�߶������������׼�顱�򡰳�����׼�顱��ͼ�����ۼӣ���˸ĳ�ֻ��һ�ݣ�����ϵͳ�����������Щ·�ߵ��������ơ�������׼�顱�򡰳�����׼�顱��
   * 3��·�ߡ������ж��Ƿ�J6�����򲻷���-1����Ϊ������������Ϊ1��
   */
  //CCBegin by liunan 2011-05-17 ������׼���ж��Ƿ�J6�������Ӳ��� isJ6
  //private static String futushucount(String partNumber,String makeRoute, String assembleRoute) {
  //CCBegin SS26
  //private static String futushucount(String partNumber,String makeRoute, String assembleRoute, boolean isJ6) {
  private static String oldfutushucount(String partNumber,String makeRoute, String assembleRoute, boolean isJ6) {
  //CCEnd SS26
  //CCEnd by liunan 2011-05-17
  if(partNumber.startsWith("S")||partNumber.startsWith("2S")||partNumber.startsWith("3S")||partNumber.startsWith("4S")||partNumber.startsWith("5S"))
  {
  	return "";
  }
	String route=makeRoute+","+assembleRoute;
	//System.out.println("route===="+route);
	String allRouteDep=route.replace("/", ",");
	allRouteDep=allRouteDep.replace("-", ",");
	int	allCount=0;
	//CCBegin by liunan 2011-05-02 �ڡ�����������������Ϳ�������Ρ��ж���һ���Ǹ���������׼�顱�ģ�ʵ��ֻ��һ�ݡ�
	//�ڡ�����(��)�������񣨺����������ܣ��ᣩ�������ܣ�װ�����ж���һ���Ǹ���������׼�顱�ģ�ʵ��ֻ��һ�ݡ�
	boolean cszFlag = false;
	boolean cjzFlag = false;
	//CCEnd by liunan 2011-05-02
	
	//CCBegin by liunan 2011-11-24 50���µĻ� �� �� ��(��) ����6+1������ ����3+1����������� �� ��6�ĳ�3��
	boolean hanFlag = false;
	//CCEnd by liunan 2011-11-24
	
//	System.out.println(route);
	allRouteDep=allRouteDep.replace("��(��)","��");
	//System.out.println("-----�ĺ�="+allRouteDep);
	//CCBegin by liunan 2011-11-30 ���ݽ���������� �� ��(��) ��һ��·�ߴ�������������Ҫ�ֱ������
	
	//�����޸ĺ󣬺�������е� ��(��) ���жϼ�����ʵ���ϲ��������ˣ�Ҳ������롣
	allRouteDep=allRouteDep.replace("��(��)","��");
	//�����ж�
	int zizucount = 0;
	for(int j=0;j<partNumber.length();j++)
	{
		if(java.lang.Character.isDigit(partNumber.charAt(j)))
		{
			zizucount = j;
			break;
		}
	}
	String zizu = partNumber.substring(zizucount,zizucount+2);
	//CCEnd by liunan
	
	String count1[]=allRouteDep.split(",");
	ArrayList newroute=new ArrayList();
	if(tucountMap==null){
		tucountMap=new HashMap();
	    CodingManageService cService = null;
	    try {
	      cService = (CodingManageService) EJBServiceHelper
	          .getService("CodingManageService");
	      Collection result= cService.findDirectClassificationByName("·����֯����","�������");
	      Iterator i=result.iterator();
	      CodingClassificationIfc codingIfc =null;
	      while(i.hasNext()){
	      	String type=(i.next()).toString();
	      	try {
				codingIfc = cService.findClassificationByName(type,"·����֯����");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
//			System.out.println(codingIfc);
			if(codingIfc!=null&&codingIfc.getCodeNote()!=null&&!codingIfc.getCodeNote().equals(""))
	         tucountMap.put(codingIfc.getClassSort(),codingIfc.getCodeNote());
	      }
	      
	    }
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	for(int i=0;i<count1.length;i++)
	{        
		String countStr=(String)tucountMap.get(count1[i]);
		//�ر�˵���������Ϊ��λ����ͬ·�ߵ�λ�ķ�ͼ�������ۼ�
        if(!newroute.contains(count1[i]))
        {
        newroute.add(count1[i]);	
        //CCBegin by liunan 2011-05-02 ����з�����������׼�顱�򡰳�����׼�顱�����ʶΪ�棬����+1��
        //CCBegin by liunan 2011-11-30 ����׼ ���� ��׼ �� +1 �������й� ��  �� ��  û���ˡ�
        //�ж����������ţ���<50ʱ����Ϊ +6��+1��׼��>=50ʱ����Ϊ +3��+1��׼
        //if(count1[i].equals("��")||count1[i].equals("��")||count1[i].equals("Ϳ")||count1[i].equals("��"))
        if(count1[i].equals("��")||(count1[i].equals("��")&&Integer.parseInt(zizu)>=50)||count1[i].equals("Ϳ")||count1[i].equals("��"))
        {
        	cszFlag = true;//����׼
        }
        //if(count1[i].equals("��")||count1[i].equals("��(��)")||count1[i].equals("��(װ)")||count1[i].equals("��(��)"))
        //CCBegin SS17
        //if(count1[i].equals("��")||(count1[i].equals("��")&&Integer.parseInt(zizu)<50)||count1[i].equals("��(װ)")||count1[i].equals("��(��)"))
        if(count1[i].equals("��")||(count1[i].equals("��")&&Integer.parseInt(zizu)<50)||count1[i].equals("��(װ)")||count1[i].equals("��(��)")||count1[i].equals("��(��)")||count1[i].equals("��(��)"))
        //CCEnd SS17
        {
        	cjzFlag = true;//����׼
        }
        //CCEnd by liunan 2011-11-30
        //CCEnd by liunan 2011-05-02                
        
		//CCBegin by liunan 2011-11-24 50���µĻ� �� �� ��(��) ����6+1������ ����3+1����������� �� ��6�ĳ�3��
        if(count1[i].equals("��"))
        {
        	hanFlag = true;
        }
    //CCEnd by liunan 2011-11-24
    
		if(countStr!=null){
            try
            {            
           int count=Integer.parseInt(countStr);
           allCount+=count;
            }
            catch (NumberFormatException ex)
            {               
            }		
		}
		}
	}
	//System.out.println("---route--"+route);
	//CCBegin by liunan 2011-05-17 ���Ӷ��·�߶���ЭЭʱ���жϡ�
	//if(route.equals("Э,Э")||route.equals("Э-Э,Э")){//·��ΪЭ��Эʱ��
	//CCBegin by liunan 2011-11-11 �����󣺵���������Ϊ24��25��2912��2919��2902��30��31��3501��3502��3503��3519��3530��3550��
	//�����װ��·�����ޡ��ס�ʱ����ͼ������ԭ�л���������2�ݡ�
	//if(route.equals("Э,Э")||route.equals("Э-Э,Э")||route.equals("Э,Э,Э")||route.startsWith("Э,Э,Э,Э")){//·��ΪЭ��Эʱ��
	if(route.indexOf("��")==-1){
	//CCEnd by liunan 2011-05-17	
		//�����24��25��2912��2919��30��31��3501��3502��3503��3519��3530��ͷ��
		if(partNumber.startsWith("24")||partNumber.startsWith("25")||partNumber.startsWith("2912")||
		partNumber.startsWith("2919")||partNumber.startsWith("30")||partNumber.startsWith("31")||
		partNumber.startsWith("3501")||partNumber.startsWith("3502")||partNumber.startsWith("3519")||		
		partNumber.startsWith("2902")||partNumber.startsWith("3503")||partNumber.startsWith("3550")||//liunan 2011-11-11 ����2902��3503��3550�������顣
		partNumber.startsWith("3530"))
		{
			allCount+=2;
		}
//		//�����16��17��42��ͷ��
//		if(partNumber.startsWith("16")||partNumber.startsWith("17")||partNumber.startsWith("42"))
//		{
//			allCount+=2;
//		}
		
	}
	
	//CCBegin SS4
	if(route.equals("Э,Э")||route.equals("Э,Э,Э")||route.equals("Э,Э,Э,Э")||route.equals("Э,Э,Э,Э,Э"))
	{
		if(partNumber.startsWith("1601")||partNumber.startsWith("1602")||partNumber.startsWith("1607")||
		partNumber.startsWith("1701")||partNumber.startsWith("1702")||partNumber.startsWith("1706")||
		partNumber.startsWith("1707")||partNumber.startsWith("3507")||partNumber.startsWith("3508")||		
		partNumber.startsWith("4207")||partNumber.equals("3506403-50A")||partNumber.equals("3506406-50A")||
		partNumber.equals("3506407-50A")||partNumber.equals("3506408-50A")||partNumber.equals("3506403-M01")||
		partNumber.equals("3506407-M01")||partNumber.equals("3506408-M01")||partNumber.equals("3533056-55A")||
		partNumber.equals("3550376-55A")||partNumber.equals("3611220-A2K")||partNumber.equals("3611225-A2K")||
		partNumber.equals("3611210-A4G")||partNumber.equals("3611235-A4G")||partNumber.equals("3611225-A4G")||
		partNumber.equals("3611225-M01")||partNumber.equals("3724155-M01")||partNumber.equals("3724160-M01"))
		{
			allCount+=4;
		}
	}
	//CCEnd SS4
	
	//CCBegin by leixiao 2011-1-14 �����׼���ӷ�ͼ������
	//CCBegin by liunan 2011-05-02 ֻҪ·���м��С��ܡ����С��á���+1
	//else if (route.indexOf("��,��")!=-1||route.indexOf("��-��")!=-1){
	//CCBegin by liunan 2011-11-16 �ķɷ��ִ˴�ע�Ͳ��� ���ס�  �롰���á� �Ĺ�ϵ��Ҳ��ѯ��ɳ���ܣ����ȥ�� else��
	//Դ�� else if (route.indexOf("��")!=-1&&route.indexOf("��")!=-1){
	if (route.indexOf("��")!=-1&&route.indexOf("��")!=-1){
	//CCEnd by liunan 2011-11-16
	//CCEnd by liunan 2011-05-02	
		//System.out.println("---------");
		allCount+=1;
	}
	//CCEnd by leixiao 2010-1-14 �����׼���ӷ�ͼ������
	//CCBegin by liunan 2011-10-26 ���Ϊ��-�У���������������50������50�����ϣ�������+1��
	/*else if (route.equals("��,��"))
	{
		String zizu = partNumber.substring(0,2);
		if(Integer.parseInt(zizu)>=50)
		{
			allCount+=1;
		}
	}*/ 
	//liunan 2011-11-11 ע�͵���Ϊ�˴����󻹲�ȷ����
	//CCEnd by liunan 2011-10-26
	
	//CCBegin by liunan 2011-05-02 ����з�����������׼�顱�򡰳�����׼�顱��������+1��
	if(cszFlag)
	{
		allCount+=1;
	}
	if(cjzFlag)
	{
		allCount+=1;
	}
	//CCEnd by liunan 2011-05-02
	
	
	//CCBegin by liunan 2011-11-24 50���µĻ� �� �� ��(��) ����6+1������ ����3+1����������� �� ��6�ĳ�3��	
	if(hanFlag&&Integer.parseInt(zizu)<50)
	{
		allCount +=3 ;		
	}	
	//CCEnd by liunan 2011-11-24
	
	//CCBegin by liunan 2011-05-02 ���·�����С����������ж��Ƿ���J6�����򲻷�ͼ��
	//J6��������:(1)*P6* (2)*.*-* (3)*J6* (4)####-* (5)####?-* ��*��������ַ�   #����һ������   ?����һ���ַ���
	//CCBegin by liunan 2011-05-17 ����ı䣺·�����С�������ȷ����ͼ+1��û�����ж���׼���Ƿ���J6��������+1
	/*if(route.indexOf("��")!=-1)
	{
		boolean digitFlag = true;
		for(int j=0;j<4;j++)
		{
			if(!java.lang.Character.isDigit(partNumber.charAt(j)))
			{
				digitFlag = false;
				break;
			}
		}
		//�ж��Ƿ���J6��
		if(partNumber.indexOf("P6")!=-1||partNumber.indexOf("J6")!=-1||
		   (partNumber.indexOf(".")!=-1&&partNumber.indexOf("-")!=-1)||
		   (digitFlag&&partNumber.indexOf("-")==4)||(digitFlag&&partNumber.indexOf("-")==5))
		{
			allCount = allCount-1;
		}
	}*/
	//CCBegin SS19
	//if(route.indexOf("��")==-1&&!isJ6)
	//{
		//allCount = allCount + 1;
	//}
	//CCEnd SS19
	//CCEnd by liunan 2011-05-17
	//CCEnd by liunan 2011-05-02
	
	newroute=null;
	//CCBegin by liunan 2011-09-05 ���ݾ����������������
	//if(allCount!=0)
	//CCBegin SS19
	//allCount+=2;//���еĶ���2�ݸ��̶��û�
	allCount+=1;//ֻʣһ�ݸ�����÷������
	//CCEnd SS19
//	System.out.println("��ͼ����"+allCount);
	return String.valueOf(allCount);
}
  
  //CCEnd by leixiao 2010-6-2 �����׼���ӷ�ͼ������
  
  
  //CCBegin SS26
  /**
   * �°���㷢ͼ��
   * ��·�߷�ͼ�����ܶ൥λ�ķ�ͼ�����ټ��㡣
   * @param partNumber String �㲿�����
   * @param makeRoute String ����·��
   * @param assembleRoute String װ��·��
   * @param isJ6 boolean �Ƿ�J6��ǣ��Ѳ�ʹ�ã�
   * @return String ��ͼ��
   */
  private static String futushucount(String partNumber,String makeRoute, String assembleRoute, boolean isJ6)
  {
  	if(partNumber.startsWith("S")||partNumber.startsWith("2S")||partNumber.startsWith("3S")||partNumber.startsWith("4S")||partNumber.startsWith("5S"))
  	{
  		return "";
  	}
  	String route=makeRoute+","+assembleRoute;
  	System.out.println("route===="+route);
  	String allRouteDep=route.replace("/", ",");
  	allRouteDep=allRouteDep.replace("-", ",");
  	int	allCount=0;
  	allRouteDep=allRouteDep.replace("��(��)","��(��)");
  	allRouteDep=allRouteDep.replace("��(��)","��(��)");
  	allRouteDep=allRouteDep.replace("��(����)","��(��)");
  	allRouteDep=allRouteDep.replace("��(��)","��(��)");
  	
  	//�����ж�
  	int zizucount = 0;
  	for(int j=0;j<partNumber.length();j++)
  	{
  		if(java.lang.Character.isDigit(partNumber.charAt(j)))
  		{
  			zizucount = j;
  			break;
  		}
  	}
  	String zizu = partNumber.substring(zizucount,zizucount+2);
  	
  	String count1[]=allRouteDep.split(",");
  	ArrayList newroute=new ArrayList();
  	if(tucountMap==null)
  	{
  		tucountMap=new HashMap();
	    CodingManageService cService = null;
	    try
	    {
	      cService = (CodingManageService) EJBServiceHelper.getService("CodingManageService");
	      Collection result= cService.findDirectClassificationByName("·����֯����","�������");
	      Iterator i=result.iterator();
	      CodingClassificationIfc codingIfc =null;
	      while(i.hasNext())
	      {
	      	String type=(i.next()).toString();
	      	codingIfc = cService.findClassificationByName(type,"·����֯����");
	      	if(codingIfc!=null&&codingIfc.getCodeNote()!=null&&!codingIfc.getCodeNote().equals(""))
	      	{
	      		tucountMap.put(codingIfc.getClassSort(),codingIfc.getCodeNote());
	      	}
	      }
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	  }
	  for(int i=0;i<count1.length;i++)
	  {
	  	String countStr=(String)tucountMap.get(count1[i]);
	  	//�ر�˵���������Ϊ��λ����ͬ·�ߵ�λ�ķ�ͼ�������ۼ�
	  	if(!newroute.contains(count1[i]))
	  	{
        newroute.add(count1[i]);
        if(countStr!=null)
        {
        	try
        	{
        		int count=Integer.parseInt(countStr);
        		allCount+=count;
        	}
        	catch (NumberFormatException ex)
        	{
        	}
        }
      }
    }
    
    //���·�߶���ЭЭʱ���жϡ�
    if(route.equals("Э,Э")||route.equals("Э,Э,Э")||route.equals("Э,Э,Э,Э")||route.equals("Э,Э,Э,Э,Э"))
    {
    	//���ŵ������ 24,25,2902,2912,2919,30,31,3501,3502,3503,3519,3530,3550 ��ͷ�����Эʱ��2��
    	if(partNumber.startsWith("24")||partNumber.startsWith("25")||partNumber.startsWith("2902")||
    	   partNumber.startsWith("2912")||partNumber.startsWith("2919")||partNumber.startsWith("30")||
    	   partNumber.startsWith("31")||partNumber.startsWith("3501")||partNumber.startsWith("3502")||
    	   partNumber.startsWith("3503")||partNumber.startsWith("3519")||partNumber.startsWith("3530")||
    	   partNumber.startsWith("3550"))
    	{
    		allCount+=2;
    	}
    	
    	//������������ 1601,1602,1607,1701,1702,1706,1707,3506,3507,3508,3533,3550,3611,3724,3774,4207 ��ͷ�����Эʱ��3��
    	if(partNumber.startsWith("1601")||partNumber.startsWith("1602")||partNumber.startsWith("1607")||
    	   partNumber.startsWith("1701")||partNumber.startsWith("1702")||partNumber.startsWith("1706")||
    	   partNumber.startsWith("1707")||partNumber.startsWith("3506")||partNumber.startsWith("3507")||
    	   partNumber.startsWith("3508")||partNumber.startsWith("3533")||partNumber.startsWith("3550")||
    	   partNumber.startsWith("3611")||partNumber.startsWith("3724")||partNumber.startsWith("3774")||
    	   partNumber.startsWith("4207"))
    	{
    		allCount+=3;
    	}
    }
    newroute=null;
    //�����xxxxx21�㲿�����һ��
    if(partNumber.length()>=7&&partNumber.substring(5,7).equals("21"))
    {
    	allCount+=1;
    }
    //��Ҫ��һ�ݵ��ļ���
    allCount+=1;
    //System.out.println("��ͼ����"+allCount);
    return String.valueOf(allCount);
  }
  //CCEnd SS26

/**
   * �մ�����
   * @param str String ��ϸ�ַ�����
   * @return String ���null������""��
   */
  private static String stringNullDeal(String str)
  {
     if (str == null||str.equals("null"))
     {
       str = "";
     }
     return str;
  }
//CCEnd by liunan 2009-02-21

  //CCBegin by liunan 2011-05-17 ������׼���ж��Ƿ�J6�������ز��� isJ6 ,J6�������ൺ����  
  //CCBegin by liunan 2011-06-18 ������λ�ͷ�ͼ�������޸ģ������λ�в����������������ж��Ƿ���Ҫ���ൺ��ͼ
  //Ŀǰ���ൺ��ͼ�ĳ����Ƿ�J6��J6L��J6M���೵�͡�
  //����true���ʾ����Ҫ��ͼ��false��ʾ��Ҫ��ͼ��
  //CCBegin by liunan 2011-10-26 ���� �Ǿ��� Ҳ�����ൺ���� 2120J* 2150J* 1218J* ���Ǿ�����
  private static boolean getIsJ6(String num)
  {
  	System.out.println("num==="+num);
  	boolean isJ6 = false;
  	if(num.startsWith("ǰ׼")||num.startsWith("��׼")||num.startsWith("��׼")||num.startsWith("�ձ�")||num.startsWith("�շ�"))
  	{
  		num = num.substring(2);
  	}
  	if(num.startsWith("����׼"))
  	{
  		num = num.substring(3);
  	}
  	boolean digitFlag = true;
		for(int j=0;j<4;j++)
		{
			if(!java.lang.Character.isDigit(num.charAt(j)))
			{
				digitFlag = false;
				break;
			}
		}
		//�ж��Ƿ���J6��
		if(num.indexOf("P6")!=-1||num.indexOf("J6")!=-1||
		   (num.indexOf(".")!=-1&&num.indexOf("-")!=-1)||
		   (digitFlag&&num.indexOf("-")==4)||(digitFlag&&num.indexOf("-")==5))
		{
			isJ6 = true;
		}
		//CCBegin by liunan 2011-06-18 ���������ж��Ƿ���J6L��J6M���͡�
		if(isJ6)
		{
			if(num.indexOf("P62")!=-1||num.indexOf("P63")!=-1||num.startsWith("1014")||num.startsWith("1016")||
			   num.startsWith("1018")||num.startsWith("1622")||num.startsWith("1220")||num.startsWith("1423")||
			   num.startsWith("1426")||num.startsWith("2518")||num.startsWith("2524")||num.startsWith("2526")||
			   num.startsWith("2528")||num.startsWith("2532")||num.startsWith("3124")||num.startsWith("3126"))
			{
				isJ6 = false;
			}
		}
		//CCEnd by liunan 2011-06-18
		
		//CCBegin by liunan 2011-10-26 �Ƿ�������Ƿ���true����������1��
		if(num.startsWith("2120J")||num.startsWith("2150J")||num.startsWith("1218J"))
		{
			isJ6 = true;
		}
		//CCEnd by liunan 2011-10-26
		
  	System.out.println("isJ6==="+isJ6);
		return isJ6;
  }
  //CCEnd by liunan 2011-05-17
  
  
  private static int getlevel(boolean expandByProduct,boolean flag,HashMap parentMap2000,HashMap parentMap,String bsoid,String masterID,String num,int level)
  {
  		String aaaa = null;
  		try {
			if (expandByProduct && flag) {

					aaaa = (String) parentMap2000.get(bsoid);
				} else {

					aaaa = (String) parentMap.get(masterID);

				}
			//System.out.println(" number="+num+" "+aaaa+"\n");
			
			PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();
			
			if (aaaa != null) 
			{
				level++;
				StringTokenizer yyy = new StringTokenizer(aaaa,";");
				while (yyy.hasMoreTokens())
				{
						String parentcount = yyy.nextToken();
						StringTokenizer yy = new StringTokenizer(parentcount,"...");
						if (yy.hasMoreTokens()) 
						{
							String parepartid = yy.nextToken();
							String partcount = yy.nextToken();
							String linkid = yy.nextToken();
							PartUsageLinkIfc linkifc = (PartUsageLinkIfc) pservice.refreshInfo(linkid);
							if(linkid.startsWith("GenericPart"))
							{
								GenericPartIfc ifc = (GenericPartIfc) pservice.refreshInfo(linkifc.getRightBsoID());
								//System.out.println("�ϼ�����"+ifc.getPartNumber());
								level = getlevel(expandByProduct,flag,parentMap2000,parentMap,ifc.getBsoID(),ifc.getMasterBsoID(),ifc.getPartNumber(),level);
							}
							else if(linkid.startsWith("Part"))
							{
								QMPartIfc ifc = (QMPartIfc) pservice.refreshInfo(linkifc.getRightBsoID());
								//System.out.println("�ϼ�����"+ifc.getPartNumber());
								level = getlevel(expandByProduct,flag,parentMap2000,parentMap,ifc.getBsoID(),ifc.getMasterBsoID(),ifc.getPartNumber(),level);
							}

						}
					}
				}
			aaaa= null;
		}
    catch (Exception ex) {
      ex.printStackTrace();
  }
  return level;
}

  //CCBegin SS9
	public static String isQQGroupUser() throws QMException 
	{
		String flag = "false";
		Vector groupVec = new Vector();
		SessionService sessionser = (SessionService) EJBServiceHelper.getService("SessionService");
		UserIfc user = sessionser.getCurUserInfo();
		//CCBegin SS25
		if(user.getUsersName().equals("Administrator"))
		{
			return "true";
		}
		//CCEnd SS25
		UsersService userservice = (UsersService) EJBServiceHelper.getService("UsersService");
		Enumeration groups = userservice.userMembers((UserInfo) user, true);
		for (; groups.hasMoreElements();) 
		{			
			GroupIfc group = (GroupIfc) groups.nextElement();
			String groupName = group.getUsersName();
			if (groupName.equals("�����ֹ�˾�û���")) 
			{
				flag =  "true";
				break;
			}
		}
		//System.out.println("flag==="+flag);
		return flag;
	}
 	//CCEnd SS9
 	
 	
 	//CCBegin SS21
 	private static String getCodingDesc(String routeID)
 	{
 		String code = null;
 		if (routeID != null)
 		{
 			try
 			{
 				TechnicsRouteService rs = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
 				code = rs.getRouteCodeDesc(routeID);
 			}
 			catch(Exception ex)
 			{
 				ex.printStackTrace();
 			}
 		}
 	  return code;
 	}
	//CCEnd SS21
}