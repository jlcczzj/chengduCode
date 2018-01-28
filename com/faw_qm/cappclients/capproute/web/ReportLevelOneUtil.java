/**
 * 生成程序 ReportLevelOneUtil.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 根据解放要求，艺准、前准、临准、艺试准的报表中要增加“保安重要”属性的显示。 liunan 2013-6-14
 * SS2 有些艺准生成了一个未启动流程，因此得不到流程信息，搜索流程时不搜未启动流程。 liunan 2013-8-22
 * SS3 艺准报表第一种获取父件，即得到整个结构的方法，map的key是bsoid，这是如果艺准中添加的结构零部件多是旧版本，
 * 则无法得到上级件,现在将key改成masterbsoid。liunan 2013-10-15
 * SS4 路线是协协的并且符合条件的子组号或零部件号，发图数+4。 liunan 2014-2-8
 * SS5 分割制造路线用的→，但有些地方保存的是-，导致送往路线里带有重复路线，比如专-轴，轴。 liunan 2014-4-14
 * SS6 工艺路线显示，卡车厂路线单位在前 liuyang 2014-6-11
 * SS7 增加“颜色件标识” liuyang 2014-6-13
 * SS8 在艺准处于个人资料夹时“颜色件标识” 显示不出来，原因是数组位置有问题，和jsp输出位置错了一位。刘家坤 2014-6-13
 * SS9 青汽用户单独的报表，零部件编号、版本分开成两列。 liunan 2014-11-3
 * SS10 修改装配路线卡车厂路线单位在前排序不对问题。 xianglx 2014-12-9
 * SS11 A004-2014-3018 新需求之取消导出艺准制造路线中的横线 liunan 2014-12-12
 * SS12 如果零部件iba属性“文档信息”不为空，则将其内容与零部件名称合并显示。见xx图纸的信息都改记在这里了，以前是写在零部件名称里。 liunan 2014-12-15
 * SS13 艺准通知书在个人资料夹下路线显示不准确(合并成一行了)。 xianglx 2014-12-15
 * SS14 “颜色件标识”存储的位置不正确。 xianglx 2014-12-15
 * SS15 A004-2014-3074 艺废不需要计算发图数，由于出现2S、3S开头的编号，如果艺准中有这类零部件，需要修改子组判断方式。 liunan 2015-1-4
 * SS16 因路线报表中制造路线与装配路线对应错乱，所以取消SS6的修改 pante 2015-01-06
 * SS17 新增的路线“架(横)”和“架(抛)”也要往“架(准)”发一份图纸 liunan 2015-1-7
 * SS18 补充SS15中判断出现null的错误。 liunan 2015-1-22
 * SS19 新需求，固定数减少1，不再发给贸易公司；“岛”的不再发图，青岛看电子文档。 liunan 2015-1-22
 * SS20 问题A004-2015-3108：新需求，报表版本显示新规则，当零件号中含“/”时，零合件号属性为“零件号”，不再附加版本。 liunan 2015-3-16
 * SS21 A004-2015-3161 路线状态不同数量不同，无法正确对应，客户端与报表不一致。 liunan 2015-7-8
 * SS22 A004-2016-3290 报表存在路线显示多斜杠“/”的情况。 liunan 2016-1-25
 * SS23 A004-2016-3340 零部件重命名后查看报表时，显示内容不一致。编号名称每次刷最新内容。 liunan 2016-4-12
 * SS24 艺废报表出错，空指针。 liunan 2016-5-19
 * SS25 青汽数据发布时，导出报表用Administrator用户，因此按照青汽模版导出。 liunan 2016-8-30
 * SS26 A004-2016-3434 发图数整体调整。 liunan 2016-10-31
 * SS27 逻辑总成判断规则，增加第五位是G的条件判断。 liunan 2016-11-19
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
 * Title:生成一级路线报表
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 * (问题一)20060629 周茁修改 修改原因:工艺路线生成报表操作速度慢
 * @author 刘明
 * @version 1.0
 */

public class ReportLevelOneUtil {
//  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
  //  private static TechnicsRouteListIfc myRouteList;
    private static Object lock = new Object(); //yanqi-20060918
    private static boolean expandByProduct;
//  CCBeginby leixiao 2009-1-6 原因：解放升级工艺路线,sendToColl不能作为静态变量
   // private static Collection sendToColl;
//  CCEndby leixiao 2009-1-6 原因：解放升级工艺路线
    private static final Map signMap = new HashMap(4);
    static {
        signMap.put("采用", "C");
        signMap.put("新增", "X");
        signMap.put("改图", "G");
        signMap.put("取消", "Q");
        //CCBegin by liunan 2011-08-30 增加废弃“F”
        signMap.put("废弃", "F");
        //CCEnd by liunan 2011-08-30
      }
//  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
//  CCBeginby leixiao 2009-1-6 原因：解放升级工艺路线
    private static final Map typeMap = new HashMap(4);
    static {
    	typeMap.put("试制", "试 制 工 艺 准 备 通 知 书");
    	typeMap.put("前准", "提 前 工 艺 准 备 通 知 书");
    	typeMap.put("临准", "临 时 工 艺 准 备 通 知 书");
    	typeMap.put("艺准", "工 艺 准 备 通 知 书");
    	typeMap.put("艺毕", "工 艺 准 备 完 毕 通 知 书");
    	//CCBegin by liunan 2011-09-21 艺废通知书。
    	typeMap.put("艺废", "工 艺 废 弃 通 知 书");
    	//CCEnd by liunan 2011-09-21
      }
//  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
    //CCBegin by leixiao 2010-6-2 解放艺准增加发图数量列
    private static  Map tucountMap = null;
    //CCEnd by leixiao 2010-6-2 解放艺准增加发图数量列

    //CCBegin by liunan 2009-02-22 
    //全局变量集合，用于记录当前时刻正在做艺准报表（指全新生成并要将明细插入到表中）
    //生成的艺准标识。艺准bsoid在开始生成明细前加入集合中，生成完明细、插入到数据库表中并返回结果后
    //从集合中移除。其他用户对集合中存在的艺准生成报表时，不进行处理返回空，提示用户稍后再做。
    //艺准在首次或升级版本后生成报表时要经过计算处理获得明细然后保存到数据库表中，之后都从表中直接获取。
    public static ArrayList checkReport = new ArrayList();
    //CCEnd by liunan 2009-02-22

  public ReportLevelOneUtil() {
  }

  /**
   * 获得界面头部信息
   *
   * @param routeListID
   *            路线表的BsoID
   * @return 路线表的编号、名称、产品、日期
   */
  public static String[] getHeader(String routeListID) {
    routeListID = routeListID.trim();
    PersistService pService = null;
    try {
      pService = (PersistService) EJBServiceHelper
          .getService("PersistService");
      TechnicsRouteListIfc routelist = (TechnicsRouteListIfc) pService
          .refreshInfo(routeListID);
      //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
      //myRouteList = routelist;
      //  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
      String number = routelist.getRouteListNumber();
      String name = routelist.getRouteListName();
      String list = number + "（" + name + "）" + "的一级工艺路线报表";

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
      String date = year + "年" + month + "月" + day + "日";
      String[] c = {
          list, product, date};
      return c;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }

  }
  
  //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
    /**
     * 获得界面头部信息
     * @param routeListID 路线表的BsoID
     * @return 路线表的编号、名称、产品、日期
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
        String date = year + "年" + month + "月" + day + "日";
//      CCBeginby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出
        String type=routelist.getRouteListState();
       // System.out.println("type="+type);
        String title=(String)typeMap.get(type);
//      CCBeginby leixiao 2009-3-4 原因：解放升级工艺路线,表头按类型出       
        String[] c = {
            name, product, date, partmaster.getPartNumber(),title};
        return c;
      }
      catch (Exception ex) {
        ex.printStackTrace();
        return null;
      }
    }

//  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线

  /**
   * 获得指定路线表的一级路线报表的数据
   *
   * @return 返回集合的元素为数组arrayObjs。其元素依次为序号、零部件编号、零部件名、
   *         一级路线串（字符串数组array的集合。array[0]为制造路线串；array[1]为装配路线串）
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
        //(问题一)20060629 周茁修改 修改原因:工艺路线生成报表操作速度慢,
        //从technicsrouteBranch对象中直接取出路线串字符串，不用恢复node对象 begin
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
        //(问题一)20060629 周茁修改 begin
        Object[] arrayObjs = {
            String.valueOf(i++),
            partmaster.getPartNumber(), partmaster.getPartName(),
            "", strVector};

        //add end

        //modify by guoxl on 20080310(一级路线生成报表显示时，序号排列错误)

        // resultsMap.put(partmaster.getBsoID(),arrayObjs);
        resultsMap.put(partmaster.getPartNumber(), arrayObjs);
        v.add(partmaster);

      }
      //获得最新值对象集合

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
    //(问题一)20060629 周茁修改 end
  }*/
  //  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线,增加"计算数量"
    /**
     * yanqi-20061010
     * 获取零部件part在车型root上的使用数量，递规
     * @param part QMPartIfc
     * @param root 车型
     * @param parentMap key=partID value=以数组(PartUsageLinkIfc, QMPartIfc)为元素的集合
     * @param useQuanMap key=partID  value=零部件在车型上的使用数量
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

        //在数据库中寻找使用了(partIfc所对应的)PartMasterIfc的
        //所有的父件(QMPartIfc)。返回值是以数组(PartUsageLinkIfc, QMPartIfc)为元素的集合。
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
          //这里的useCount=0
          useQuanMap.put(part.getBsoID(), Float.valueOf(useCount + ""));
          return useCount;
        }
      }

      //如果有父件
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
     * 从StandPartService复制来的同名方法，目的是将获得的父件中不是最新小版本的去掉
     * 通过指定的配置规范，在数据库中寻找使用了(partIfc所对应的)PartMasterIfc的
     * 所有符合配置规范的部件(QMPartIfc)。
     * 返回值是以Object[] = (PartUsageLinkIfc, QMPartIfc)为元素的集合。
     * @param partIfc 零部件值对象
     * @param partConfigSpecIfc 零部件配置规范
     * @return Collection
     * @throws QMException
     */
    private static Collection getParentPartsByConfigSpec(QMPartIfc partIfc,
        PartConfigSpecIfc
        partConfigSpecIfc) throws
        QMException {

      //调用navigateUsedByToIteration(partIfc, partConfigSpecIfc)
      //对结果集合进行过滤，只保留是QMPartIfc的对象
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
          //判断是否是最新版本,去掉不是最新小版本的Part
          if (parentPartIfc.getBsoID().equals(newPart.getBsoID())) {
            //leftBsoID是被使用的零部件的QMPartMaster的BsoID
            //rightBsoID是使用者零部件的BsoID::
            String leftBsoID = partIfc.getMasterBsoID();
            String rightBsoID = parentPartIfc.getBsoID();
            Collection coll = null;
            //需要根据leftBsoID和rightBsoID找到PartUsageLinkIfc对象。应该只有一个：（不一定只有一个，因为有多次添加同一子件的情况 skx）
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
            //modify by skx 若有多次添加的情况要在被用于产品界面中把每一条路径都显示出来
            if (coll != null && coll.size() > 0) {
              Iterator iterator = coll.iterator();
              while (iterator.hasNext()) {
                PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) iterator.next();
                Object[] obj1 = new Object[2];
             //   System.out.println("找父件---usageLinkIfc=-"+usageLinkIfc+" parentPartIfc="+parentPartIfc);
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
     * 从StandPartService复制来的同名方法（完全相同）
     * 根据配置规范获取使用了(指定的零部件所对应的)QMPartMaster的所有零部件的集合。
     * @param partIfc 指定的零部件值对象
     * @param configSpec 过滤配置规范
     * @return Vector 零部件的集合
     * @throws QMException
     */
    private static Vector navigateUsedByToIteration(QMPartIfc partIfc,
                                                    ConfigSpec configSpec) throws
        QMException {

      PersistService pService = (PersistService) EJBServiceHelper.getService(
          "PersistService");
      //返回的结果集合:
      Vector resultVector = new Vector();
      QMPartMasterIfc masterIfc = (QMPartMasterIfc) partIfc.getMaster();
      QMQuery qmQuery = new QMQuery("QMPart", "PartUsageLink");
      qmQuery = configSpec.appendSearchCriteria(qmQuery);
      //根据查询条件获得相应的结果集:
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
      //构造结果i集合:
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
//  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线,增加"计算数量"

//  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
    /**
     * 获取当前用户的配置规范，如果用户是首次登陆系统，则构造默认的“工程视图标准”配置规范。yanqi-20060918-生成工艺路线表时使用
     * @throws QMException 使用ExtendedPartService时会抛出。
     * @return PartConfigSpecIfc 标准配置规范。
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
          ViewObjectIfc view = viewService.getView("工程视图");
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
//  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线

	// CCBeginby leixiao 2009-2-21 原因：解放升级工艺路线,路线优化
	public static Collection getFirstLeveRouteListReport(
			TechnicsRouteListIfc routelist, ArrayList sendToColl)
			throws QMException {

           return getFirstLeveRouteListReportnew(routelist, sendToColl);
	}
	// CCEndby leixiao 2009-2-21 原因：解放升级工艺路线,路线优化
//  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
    /**
     * 获得指定路线表的一级路线报表的数据
     * @return 返回集合的元素为数组arrayObjs。其元素依次为序号、零部件编号、零部件名、
     * 一级路线串（字符串数组array的集合。array[0]为制造路线串；array[1]为装配路线串）
     */
    public static Collection getFirstLeveRouteListReportolder(TechnicsRouteListIfc
        routelist,ArrayList sendToColl) throws QMException {

      ///// 20070525 liuming add
      //从数据库直接取出关联零件的数量
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
//        CCBegin by leixiao 2008-11-5 原因：解放升级工艺路线
          if(ids.length>2)
//        	  CCEnd by leixiao 2008-11-5 原因：解放升级工艺路线
          countMap.put(key, ids[2]); //关联零件有重复的可能，需要加以区别
        }
      }


      ArrayList v = new ArrayList();
      //零部件id－该零部件的父件集合，减少查询父件的次数,yanqi-20061010
      HashMap parentMap = new HashMap();
      //零部件在车型上使用数量的缓存,yanqi-20061010
      HashMap useQuantityMap = new HashMap();
      //a件是否b件的子件的缓存,键:a的bsoID+"_"+b的bsoID,值:Boolean,yanqi-20061010
      HashMap isSonMap = new HashMap();

      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      StandardPartService partService = (StandardPartService)
          EJBServiceHelper.getService("StandardPartService");
      TechnicsRouteService routeService =
          (TechnicsRouteService) EJBServiceHelper.getService(
              "TechnicsRouteService");
      //获得当前用户的配置规范
      PartConfigSpecIfc configSpecIfc = getCurrentConfigSpec_enViewDefault();
      if (configSpecIfc == null || configSpecIfc.getStandard() == null ||
          configSpecIfc.getStandard().getViewObjectIfc() == null) {
        configSpecIfc = getPartConfigSpecByViewName("工程视图");
      }
      //key:ListRoutePartLinkIfc
      CodeManageTable map = routeService.getFirstLeveRouteListReport(routelist);
   //   System.out.println("leix leix leix  ---------CodeManageTable="+map);
      //用来标识路线表关联的零部件数量是否大于2000，大于2000时采用不同的策略计算相关部件在车型上的数量
      boolean flag = (map.size() > 2000);
      QMPartMasterIfc rootmaster = (QMPartMasterIfc) pservice.refreshInfo(
          routelist.getProductMasterID());
      //整车产品的最新版本
      QMPartIfc rootPart = routeService.getLastedPartByConfig(rootmaster,
          configSpecIfc);
      HashMap partIDCountMap = null;
      //如果关联零件数量大于2000，则统计该车型的所有子件的数量
      if (expandByProduct && flag) {
        //获得在指定车型下的所有子部件的数量,键为子件BSOID,值为子件数量
        partIDCountMap = partService.getSonPartsQuantityMap(rootPart);
      }
    //  System.out.println("关联零部件的数量： " + map.size() );
      Enumeration enum2 = map.keys();
      int i = 0;
      ArrayList informationlist = null;
      StringBuffer remark = null;
      String change = null;
      String countInProduct = null;
      String version = null;
      /////////////循环对象：每个关联零部件
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
////      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
//        QMPartIfc part=null;
//        QMPartIfc part1=listPartRoute.getPartBranchInfo();
//        if(part==null){
//           part = routeService.getLastedPartByConfig(partmaster,
//          configSpecIfc); //liuming 20061215
//        }
//        else{
//        	part=part1;
//        }
////      CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
        if (part == null) {
          continue;
        }
      //  System.out.println("part = "+part.getBsoID()+" partMakeRoute="+partMakeRoute+"partMakeRoute="+partMakeRoute);
//-------------------------leixleixleix
        if (flag) {
        }
        else {
          //是产品本身
          if (part.getBsoID().equals(rootPart.getBsoID())) {

          }
          else {
            //这里调用getUseQuantity方法只是为了给parentMap赋值，计算的数量无用。
            getUseQuantity(part, rootPart, parentMap, useQuantityMap,
                           configSpecIfc);
          }
        }
//-------------------------leixleixleix

        ////////处理“每车数量”////////////////////////////////////////////////////
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
        //////////“每车数量”处理完毕////////////////////////////////////////////////////////


        ////根据解放要求路线中有"用"放在后面/////////////////////////////////////
        if (partMakeRoute.indexOf("用") != -1 && (!partMakeRoute.endsWith("用"))) {
          String sortedRoute = "";
          StringTokenizer yyy = new StringTokenizer(partMakeRoute, "/");
          while (yyy.hasMoreTokens()) {
            String haha = yyy.nextToken();
            if (!haha.equals("用")) {
              sortedRoute += haha + "/";
            }
          }
          sortedRoute += "用";
          partMakeRoute = sortedRoute;
        }
        ////////////////////////////////////////////////////////////////////

        //三个元素：分别为“装配路线”、“合件数量”和“上级件号”
        informationlist = new ArrayList();
        String[] informationStr = null;
        //包含“用”且零件名含有“装置图”的不出现父件   liuming 20061212
        if (partMakeRoute == "" ||
            ( (partMakeRoute.indexOf("用") != -1) &&
             partmaster.getPartName().indexOf("装置图") != -1)) {
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
          //过滤掉父件中的广义部件（GenericPart）
          getParentPartsNew(part, 1.0F, parentPartList, parentMap, configSpecIfc); //yanqi-20060927

          ///////////////map32:   key=partID value=合件数量
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
            //System.out.println("父件 = "+partID);
            //如果按车展开，但这个父件不在该车上，则不显示此父件的信息
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
            //System.out.println("父件Number = "+parentpart.getPartNumber());
            //如果没有装配路线,则寻找父件的制造路线
            if (myAssRoute == null || myAssRoute.equals("")) {
              //对于逻辑总成或者制造路线含"用的",就不获取父件的制造路线了
              //CCBegin SS27
              if (part.getPartType().toString().equalsIgnoreCase("Logical") || (part.getPartNumber().trim().length()>5&&part.getPartNumber().trim().substring(4,5).equals("G")) ||
                  (partMakeRoute.indexOf("用") != -1)) { //////20070904 modify by liuming  CCEnd SS27
              }
              else {
                myAssRoute = getAssisRoute(parentpart);

                //将路线单位加入发往单位中.
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


        //设置更改标识和备注
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
//        CCBeginby leixiao 2008-10-04 原因：解放升级工艺路线,说明信息的零件版本附加信息不显示
          if(des2!=null&&des2.startsWith("(")&&des2.indexOf(")")!=-1){
        	  des2=des2.substring(des2.indexOf(")")+1,des2.length());
          }
//        CCEnd leixiao 2008-10-04 原因：解放升级工艺路线
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
        //对于编号以Q,CQ,T开头的标准件不显示版本号
        String partNum = part.getPartNumber();
        if (partNum.startsWith("Q") || partNum.startsWith("CQ") ||
            partNum.startsWith("T")) {
        }
        else {
          //{{{{{{{{{{{{{{{获得大版本号
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
           // 	CCEndby leixiao 2008-10-04 原因：解放升级工艺路线

             // version = route.getRouteDescription().substring(1,2);//liunan
              String routeStr1=route.getRouteDescription();
              if(routeStr1!=null&&routeStr1.startsWith("(")&&routeStr1.indexOf(")")!=-1){
              version =  routeStr1.substring(routeStr1.indexOf("(")+1,routeStr1.indexOf(")"));
              }
            }
        	//CCBeginby leixiao 2008-10-04 原因：解放升级工艺路线,异常情况处理：路线说明为空时，不处理版本

        }
        if (version.trim().equals("")) {
        }
        else {
          version = "/" + version;
        }
////////////////////////////////////////////////////////////////////////  /

//      CCBeginby leixiao 2008-11-21 原因：解放升级工艺路线
         QMQuery query = new QMQuery("StringValue");
         int u = query.appendBso("StringDefinition", false);
        Collection col=null;
        QueryCondition qc1 = new QueryCondition("definitionBsoID", "bsoID");
        query.addCondition(0, u, qc1);
        query.addAND();
        QueryCondition qc2 = new QueryCondition("displayName", " = ", "影响互换");
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
//        System.out.println("-----ibavalue　　零件号="+part.getPartNumber()+" 影响互换＝"+exchangiba);
//      CCEndby leixiao 2008-11-21 原因：解放升级工艺路线

        /////添加了备注 20070122
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

//  CCEndnby leixiao 2008-7-31 原因：解放升级工艺路线

	// CCBeginnby leixiao 2009-2-21 原因：解放升级工艺路线优化
	public static Collection getFirstLeveRouteListReportnew(
			TechnicsRouteListIfc routelist, ArrayList sendToColl)
			throws QMException {
	//	System.out.println("--------进入getFirstLeveRouteListReportnew");
    	boolean iscomplete=false;
      	 if( routelist.getRouteListState().equals("艺毕")){
   		 iscomplete=true;
   	 }
   	 //CCBegin by liunan 2011-09-21 艺废通知书。
   	 if( routelist.getRouteListState().equals("艺废")){
   		 iscomplete=true;
   	 }
   	 //CCEnd by liunan 2011-09-21
    //CCBegin SS21
    boolean routestateflag = false;
    //CCEnd SS21
		// 1.从数据库艺准表中直接取出关联零件的数量
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
				// CCBegin by leixiao 2008-11-5 原因：解放升级工艺路线
				if (ids.length > 2)
					// CCEnd by leixiao 2008-11-5 原因：解放升级工艺路线
					countMap.put(key, ids[2]); // 关联零件有重复的可能，需要加以区别
			}
		}
		indexVector.clear();

		ArrayList v = new ArrayList();
		// a件是否b件的子件的缓存,键:a的bsoID+"_"+b的bsoID,值:Boolean,yanqi-20061010
		HashMap isSonMap = new HashMap();

		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();
		StandardPartService partService = (StandardPartService) EJBServiceHelper
				.getService("StandardPartService");
		TechnicsRouteService routeService = (TechnicsRouteService) EJBServiceHelper
				.getService("TechnicsRouteService");
		// 获得当前用户的配置规范
		PartConfigSpecIfc configSpecIfc = getCurrentConfigSpec_enViewDefault();
		if (configSpecIfc == null || configSpecIfc.getStandard() == null
				|| configSpecIfc.getStandard().getViewObjectIfc() == null) {
			configSpecIfc = getPartConfigSpecByViewName("工程视图");
		}

		long a1 = System.currentTimeMillis();
		//2.找到对应艺准的路线串
		CodeManageTable map = routeService
				.getFirstLeveRouteListReport(routelist);// 找到对应艺准的路线串
		// map(link,routestr)
		long b1 = System.currentTimeMillis();
		//System.out.print("找到对应艺准的路线串 map(link,routestr) 时间＝" + (b1 - a1));

		QMPartMasterIfc rootmaster = (QMPartMasterIfc) pservice
				.refreshInfo(routelist.getProductMasterID());
		// 整车产品的最新版本
		QMPartIfc rootPart = routeService.getLastedPartByConfig(rootmaster,
				configSpecIfc);
		Collection partIDCountMap = null;
		HashMap parentMap2000 = null;
		HashMap parentMap = new HashMap();
		HashMap parentMap1 = new HashMap();
		// 如果关联零件数量大于150，则统计该车型的所有子件的数量，采用第一种方案
		boolean flag = (map.size() > 150);
		System.out.println("零件数量＝" + map.size());

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
		// begin----获取影响互换的definitionid
		QMQuery query = new QMQuery("StringDefinition");
		QueryCondition qc = new QueryCondition("displayName", " = ", "影响互换");
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
		QueryCondition qc1 = new QueryCondition("displayName", " = ", "保安重要");
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
		QueryCondition qc2 = new QueryCondition("displayName", " = ", "文档信息");
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
		// End----获取影响互换的definitionid
		
		// begin 获取零件masterid
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
				// CCBeginby leixiao 2009-3-5 原因：解放升级工艺路线,关联中记零件id
			 if(part1!=null){//如果ListRoutePartLinkIfc关联了零件，直接从里面取
					if (part1.getPartNumber().startsWith("Q")
							|| part1.getPartNumber().startsWith("CQ")) {
						//System.out.println("-----有标准件");
						flag = true;//如果有标准件，则采用第一种方案
					}
					String masterid = part1.getMasterBsoID();
					masterlinkmap.put(masterid, part1);
					partholer.add(part1.toString()); 
			 }				
			 else{
			partmasterList.add(partmaster); //将partmaster放到集合中供一次性调用数据库
			 }
			 allpartmasterList.add(partmaster);
//			 CCEndby leixiao 2009-3-5 原因：解放升级工艺路线,关联中记零件id
			listPartRoute=null;
			partmaster=null;
		}
		enum2 = null;
		// end 获取零件masterid
     //3.获取最新零件集合
		// begin 获取最新零件集合
		long a3 = System.currentTimeMillis();
		Collection lastedpart = jf.getLastedPartByConfig(partmasterList,
				configSpecIfc);
		long b3 = System.currentTimeMillis();
		//System.out.println("获取最新零件集合时间＝" + (b3 - a3));

		partlist.addAll(lastedpart);

		// End 获取最新零件集合
		// begin 获取零件id iba
		
		for (int j = 0; j < partlist.size(); j++) {
			QMPartIfc last = (QMPartIfc) ((Object[]) partlist.get(j))[0];
			if (last.getPartNumber().startsWith("Q")
					|| last.getPartNumber().startsWith("CQ")) {
				//System.out.println("-----有标准件");
				flag = true;//如果有标准件，则采用第一种方案
			}
			String masterid = last.getMasterBsoID();
			masterlinkmap.put(masterid, last);
			partholer.add(last.toString());
		}
		
		partlist.clear();
      //4.获取零件id 
//		CCBegin by leixiao 2009-3-27 原因：艺毕输出报表
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
//		CCEnd by leixiao 2009-3-27 原因：艺毕输出报表
		partholer.clear();
		// end 获取零件id iba

		long a2 = System.currentTimeMillis();
//5.找父件
//		CCBegin by leixiao 2009-3-27 原因：艺毕输出报表时不需要父件信息
		ArrayList parentpartlist = null;
		if(iscomplete){
		}
		else{
		if (expandByProduct && flag) {
			// 获得在指定车型下的所有子部件，第一种方案找父件
			ArrayList rootPartlist = new ArrayList();
			parentMap2000 = new HashMap();
			rootPartlist.add(rootPart.getBsoID());
			// partIDCountMap =
			// jf.getSubParts(rootPartlist,parentMap2000,configSpecIfc);
			jf.getSubParts(rootPartlist, parentMap2000, configSpecIfc);
			// System.out.println("---parentMap大小＝"+parentMap.size());
			rootPartlist.clear();
		}
		long b2 = System.currentTimeMillis();
		//System.out.println("获得在指定车型下的所有子部件时间=" + (b2 - a2));

		// 获取父件及数量
		long a4 = System.currentTimeMillis();
		if (expandByProduct && flag) {

		} else {
			//第二种方案找父件
			parentpartlist = jf.getparentparts(allpartmasterList, parentMap,
					rootPart, configSpecIfc);
		}
		}
//		CCBegin by leixiao 2009-3-27 原因：艺毕输出报表
		allpartmasterList=null;
		long b4 = System.currentTimeMillis();
		//System.out.println("----------获取父件及数量时间=" + (b4 - a4));
		parentpartlist = null;
		int i = 0;
		ArrayList informationlist = null;
		StringBuffer remark = null;
		String change = null;
		String countInProduct = null;
		String version = null;
		// ///////////循环对象：每个关联零部件
		//6.每个关联零部件数据整理组装
		Enumeration enum3 = map.keys();
		//ListRoutePartLinkIfc listPartRoute=null;
		//QMPartMasterIfc partmaster=null;
		String masterID=null;
		QMPartIfc part=null;
		
		
		//CCBegin by liunan 2012-03-22 临时使用 艺毕1014.01-1 才处理。
		ArrayList yblist = null;
		//if(iscomplete&&routelist.getRouteListNumber().equals("艺毕1014.01-1"))
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
			 //CCBegin by leixiao 2009-11-17 如果ListRoutePartLinkIfc中记了partid,从link中取
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
			
			// //////处理“每车数量”////////////////////////////////////////////////////
			countInProduct = "";
			String masterID1 = masterID;
        //CCBegin SS21
        if(routestateflag)
        {
        	String coding;
        	if (listPartRoute.getRouteID() == null || listPartRoute.getRouteID().length() < 1)
        	{
        		coding = "无";
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
			// ////////“每车数量”处理完毕////////////////////////////////////////////////////////

			// //根据解放要求路线中有"用"放在后面/////////////////////////////////////
			if (partMakeRoute.indexOf("用") != -1
					&& (!partMakeRoute.endsWith("用"))) {
				String sortedRoute = "";
				StringTokenizer yyy = new StringTokenizer(partMakeRoute, "/");
				while (yyy.hasMoreTokens()) {
					String haha = yyy.nextToken();
					if (!haha.equals("用")) {
						sortedRoute += haha + "/";
					}
				}
				sortedRoute += "用";
				partMakeRoute = sortedRoute;
			}
			// //////////////////////////////////////////////////////////////////
			/*int le = 0;
		if(parentMap2000!=null&&parentMap2000.size()>0)
		{
			//System.out.println("parentMap2000 个数："+parentMap2000.size());
			le = getlevel(expandByProduct,flag,parentMap2000,parentMap,part.getBsoID(),masterID,part.getPartNumber(),le);
		}
		if(parentMap!=null&&parentMap.size()>0)	
		{
			//System.out.println("parentMap 个数："+parentMap.size());
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
			
			System.out.println(part.getPartNumber()+" 的层级是："+le);*/

			// 三个元素：分别为“装配路线”、“合件数量”和“上级件号”
			informationlist = new ArrayList();
			String[] informationStr = null;
			// 包含“用”且零件名含有“装置图”的不出现父件 liuming 20061212
//			CCBegin by leixiao 2009-3-27 原因：艺毕输出报表
			if(iscomplete){
				// 如果没有装配路线,则寻找父件的制造路线
				if (myAssRoute == null || myAssRoute.equals("")) {
					// 对于逻辑总成或者制造路线含"用的",就不获取父件的制造路线了

					//CCBegin SS27
					if (part.getPartType().toString().equalsIgnoreCase(
							"Logical") || (part.getPartNumber().trim().length()>5&&part.getPartNumber().trim().substring(4,5).equals("G"))
							|| (partMakeRoute.indexOf("用") != -1)) { // ////20070904  CCEnd SS27
						informationStr = new String[3];
						informationStr[0] = myAssRoute;
						informationStr[1] = "";
						informationStr[2] = "";
						informationlist.add(informationStr);
						informationStr = null;

					} else {
						//System.out.println("------装配为空");
						Collection parentcollection = getParentPartsByConfigSpec(part, configSpecIfc);
						//CCBegin SS24
						if(parentcollection!=null)
						{
						//CCEnd SS24
						Iterator i1=parentcollection.iterator();
						//System.out.println("------parentcollection"+parentcollection.size());
						while(i1.hasNext()){
							QMPartIfc parentpart=(QMPartIfc)(((Object[])i1.next())[1]);
							//System.out.println("------父件号="+parentpart);
							
							//CCBegin by liunan 2012-03-22 临时使用 艺毕1014.01-1 才处理。
							//if(routelist.getRouteListNumber().equals("艺毕1014.01-1"))
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
						//System.out.println("父件路线="+myAssRoute);
                        if(myAssRoute == null || myAssRoute.equals(""))
                        	continue;
						// 将路线单位加入发往单位中.
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
//			CCEnd by leixiao 2009-3-27 原因：艺毕输出报表
			else if (partMakeRoute == ""
					|| ((partMakeRoute.indexOf("用") != -1) && partmaster
							.getPartName().indexOf("装置图") != -1)) {
				informationStr = new String[3];
				informationStr[0] = myAssRoute;
				informationStr[1] = "";
				informationStr[2] = "";
				informationlist.add(informationStr);
				informationStr = null;
			} else {// //////////////////else H begin
				String parentPartList = null;
				// System.out.print(" 最新 part="+part);
				if (expandByProduct && flag) {

					//CCBegin SS3
					//parentPartList = (String) parentMap2000.get(part.getBsoID());
					parentPartList = (String) parentMap2000.get(part.getMasterBsoID());
					//CCEnd SS3
				} else {

					parentPartList = (String) parentMap.get(masterID);

				}
				//System.out.println(" number="+part.getPartNumber()+" "+parentPartList+"\n");

				// /////////////map32: key=partID value=合件数量
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
								//System.out.println("----有多个关联的情况");
								int countnum1=Integer.parseInt(partcount);
								String count2=(String)map32.get(parepartid);
								int countnum2=Integer.parseInt(count2);
								int partcountnum=countnum2+countnum1;
								partcount=String.valueOf(partcountnum);
								//System.out.println("---数量＝　"+partcount);
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
					// System.out.println("零件"+part.getPartNumber()+"父件Number =
					// "+parentpart.getPartNumber());

					// 如果按车展开，但这个父件不在该车上，则不显示此父件的信息
					if (expandByProduct) {
						if (flag) {
							// //如果零件数大于2000，则直接从root展开的的结构中找是否有该件
							// if (parentMap2000.get(partID) == null) {
							// continue;
							// }
						} else if (!isSonOf(parentpart, rootPart, parentMap1,
								isSonMap, configSpecIfc)) {
							continue;
						}

					}
					// 如果没有装配路线,则寻找父件的制造路线
					if (myAssRoute == null || myAssRoute.equals("")) {
						// 对于逻辑总成或者制造路线含"用的",就不获取父件的制造路线了

						//CCBegin SS27
						if (part.getPartType().toString().equalsIgnoreCase(
								"Logical") || (part.getPartNumber().trim().length()>5&&part.getPartNumber().trim().substring(4,5).equals("G"))
								|| (partMakeRoute.indexOf("用") != -1)) { // ////20070904  CCEnd SS27
							// modify
							// by
							// liuming
						} else {
							myAssRoute = getAssisRoute(parentpart);

							// 将路线单位加入发往单位中.
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

			// 设置更改标识和备注
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
				// CCBeginby leixiao 2008-10-04 原因：解放升级工艺路线,说明信息的零件版本附加信息不显示
				if (des2 != null && des2.startsWith("(")
						&& des2.indexOf(")") != -1) {
					des2 = des2.substring(des2.indexOf(")") + 1, des2.length());
				}
				// CCEnd leixiao 2008-10-04 原因：解放升级工艺路线
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
			// 对于编号以Q,CQ,T开头的标准件不显示版本号

			partNum = part.getPartNumber();

			if (partNum.startsWith("Q") || partNum.startsWith("CQ")
					|| partNum.startsWith("T")) {
			} else {

				// CCBeginby leixiao 2008-10-04 原因：解放升级工艺路线,异常情况处理：路线说明为空时，不处理版本
//				 if(listPartRoute.getPartBranchID()!=null){
//				 version=routeService.getibaPartVersion(part);
//				 System.out.println("version="+version);
//				 }
//				 else{
				if (route != null && route.getRouteDescription() != null) {
					// CCEndby leixiao 2008-10-04 原因：解放升级工艺路线

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
//			CCBegin by leixiao 2009-3-27 原因：艺毕输出报表
        if(iscomplete){
        	//CCBegin by liunan 2011-10-12 废弃通知书 时 exchangvalue 对应属性 “首用车型”
        	if( routelist.getRouteListState().equals("艺废"))
        	{
        		//根据零部件获得最新 艺准 编号。
        		Collection trlcol = (Collection)routeService.getListsByPart(part.getMasterBsoID());
        		//System.out.println("trlcol===="+trlcol);
        		for (Iterator ittrl = trlcol.iterator(); ittrl.hasNext(); )
        		{
        			TechnicsRouteListIfc trl = (TechnicsRouteListIfc) ittrl.next();
        			if(trl.getRouteListState().equals("艺准"))
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
//		CCEnd by leixiao 2009-3-27 原因：艺毕输出报表     
			// System.out.println("-----ibavalue 零件号="+part.getPartNumber()+"
			// 影响互换＝"+exchangvalue);
			// CCEndby leixiao 2008-11-21 原因：解放升级工艺路线
      
        //CCBegin SS6
        /*CCBegin SS16
        String route1="";
        String route2="";
        if(partMakeRoute.indexOf("/")!=-1){
        	String[] makeRoute=partMakeRoute.split("/");
        	for(int n=0;n<makeRoute.length;n++) {
               String ss=makeRoute[n];   	 
               if(ss.trim().startsWith("总")||ss.trim().startsWith("薄")||ss.trim().startsWith("厚(试制)")||
            	ss.trim().startsWith("厚(纵)")||ss.trim().startsWith("厚（焊）")||ss.trim().startsWith("焊")||
            	ss.trim().startsWith("涂")||ss.trim().startsWith("架（装）")||ss.trim().startsWith("架（漆）")||
            	ss.trim().startsWith("架（纵）")||ss.trim().startsWith("架（钻）")||ss.trim().startsWith("架（横抛）")||
            	ss.trim().startsWith("架（横）"))
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
               if(ss.trim().startsWith("总")||ss.trim().startsWith("薄")||ss.trim().startsWith("厚(试制)")||
            	ss.trim().startsWith("厚(纵)")||ss.trim().startsWith("厚（焊）")||ss.trim().startsWith("焊")||
            	ss.trim().startsWith("涂")||ss.trim().startsWith("架（装）")||ss.trim().startsWith("架（漆）")||
            	ss.trim().startsWith("架（纵）")||ss.trim().startsWith("架（钻）")||ss.trim().startsWith("架（横抛）")||
            	ss.trim().startsWith("架（横）"))
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

		// ///添加了备注 20070122
//		Object[] arrayObjs = { String.valueOf(++i), change,
//				partmaster.getPartNumber(), version,
//				partmaster.getPartName(), countInProduct, partMakeRoute,
//				informationlist, part.getBsoID(), remark.toString(),
//				//CCBegin SS1
//				//exchangvalue };
//				exchangvalue, safetyvalue};
        String colorFlag="";
        if(listPartRoute.getColorFlag()!=null&&listPartRoute.getColorFlag().trim().equals("1")){
        	colorFlag="是";
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

//  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线


//  CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
    /**
     * 显示报表时调用(持久化报表)
     * 首先判断艺准是否在个人资料夹.如果在个人资料夹,则现生成报表,不存入数据库;
     * 如果在公共资料夹,则进一步判断其在数据库ReportResult表中是否已有记录,如果有就直
     * 接提取,如果没有则现生成报表并存入数据库.
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
    //  CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
    
        /**
     * 过滤掉父件中的广义部件（GenericPart）
     * yanqi-20061010
     * 对原来的获取路线表相关零件父件的方法进行修改，减少遍历次数
     * @param part QMPartIfc 子件
     * @param count float 数量基数
     * @param result Collection 返回的父件及使用数量集合
     * @param parentMap key=partID value=以数组(PartUsageLinkIfc, QMPartIfc)为元素的集合
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
             continue; /////////根据解放要求 Gpart不要!!
           }*/

          float newCount = usageLinkIfc.getQuantity() * count;
          //对于“逻辑总成”，做普通零件处理，不再用此判断 （肖立艳征得李萍同意） liuming 20061212
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
     * 判断零部件part是否是root的子件
     * @param part QMPartIfc
     * @param root QMPartIfc
     * @param parentMap Map 缓存，用来缓存零部件id－该零部件的父件集合，减少按配置规范获取父件的次数
     * @param isSonMap Map a件是否b件的子件的缓存,键:a的bsoID+"_"+b的bsoID,值:Boolean
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
     * 根据视图名称返回零部件配置规范  liuming 20061207 add
     * @param viewName String
     * @throws QMException
     * @return PartConfigSpecIfc
     */
    private static PartConfigSpecIfc getPartConfigSpecByViewName(String viewName) throws
        QMException {
      ViewService viewService = (ViewService) EJBServiceHelper.getService(
          "ViewService");
      ViewObjectIfc view = viewService.getView(viewName);
      //若根据指定的视图名称没有获取到相应的值对象则返回当前配置规范
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
     * 获得指定零件在当前路线表中的制造路线和装配路线
     * @param listPartRoute 零件关联
     * @param map CodeManageTable
     * @return String[] 第一个元素是制造路线,第二个元素是装配路线
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
      ArrayList makeList = new ArrayList(); //制造路线集合,为了保证已出现的制造节点不再出现,如 总/总---->总
      ArrayList assList = new ArrayList(); //同上
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

//        CCBegin by leixiao 2009-01-05 原因：解放升级工艺路线,路线从路线串缓存取
          String routeString=(String)branches[j];
          String makeStrBranch="";
          String assDepartment="";
          if(routeString!=null&&routeString.length()>0){
          String route[]= routeString.split(";");
          for(int k=0;k<route.length;k++){
        	  String r=route[k];
           int s=r.indexOf("@");
          String  longdepartmentName=r.substring(0,s);
          if(longdepartmentName.equals("无"))
        	  longdepartmentName="";
          //System.out.println("制造单位＝"+longdepartmentName);
          //CCBegin SS5
          longdepartmentName = longdepartmentName.replaceAll("-","→");
          //CCEnd SS5
          String [] de=longdepartmentName.split("→");
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
           if(assDepartment.equals("无"))
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
        //  System.out.println("装配单位＝"+assDepartment);
          }

        }
      }
//    CCEnd by leixiao 2009-01-05 原因：解放升级工艺路线,路线从路线串缓存取
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
      sendToColl.remove("用");
      String haha = "";
      for (Iterator it1 = sendToColl.iterator(); it1.hasNext(); ) {
        String jj = (String) it1.next();
        haha += jj + "、";
      }
      if (haha.endsWith("、")) {
        haha = haha.substring(0, haha.length() - 1);
      }
      return haha;
    }
    
    //CCBegin by liunan 2011-06-18 新规则，发往单位如果没有“岛”，而根据车型不是J6，
    //或是J6L、J6M车型判断需要往青岛发图，则在发往单位中添加“岛”。
    public static String getTail1(ArrayList sendToColl,boolean flag) {
      if (sendToColl == null) {
        return "";
      }
      sendToColl.remove("用");
      String haha = "";
      for (Iterator it1 = sendToColl.iterator(); it1.hasNext(); ) {
        String jj = (String) it1.next();
        haha += jj + "、";
      }
      if (haha.endsWith("、")) {
        haha = haha.substring(0, haha.length() - 1);
      }
      //CCBegin by liunan 2011-06-18
      if(haha.indexOf("岛")==-1&&!flag)
      {
      	if(haha.equals(""))
      	  haha = "岛";
      	else
      	  haha = haha + "、岛";
      }
      //CCEnd by liunan 2011-06-18
      return haha;
    }
    //CCEnd by liunan 2011-06-18

    /**
     * liuming 20070116 add
     * 取父件的制造路线的第一个制造单位作为该件的装配路线，
     * 如果找到的上级件有多条制造路线(多路线)，则取每条制造路线的第一个制造单位(该件具有装配路线的多路线)。
     * 如果零件没有制造路线，则不生成其装配路线。
     * @param part 父件
     * @return 装配路线
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
 //  CCEnd by leixiao 2009-01-05 原因：解放升级工艺路线
  
   //  CCBegin by leixiao 2009-01-05 原因：解放升级工艺路线 
       /**
     * 显示报表时，客户端调用。(不持久化报表)
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
      System.out.println("当前用户为＝"+user+"  "+user1+"开始路线表"+" "+routeListID);
     // System.out.println("--------------------------------------------------"+"\n\n\n");
      ArrayList allinfomationColl = new ArrayList(5);
//    CCBeginby leixiao 2009-1-6 原因：解放升级工艺路线,sendToColl不能作为静态变量
      ArrayList sendToColl = new ArrayList();
      expandByProduct = expandByProduct1.equalsIgnoreCase("true");
     // CCBeginby leixiao 2009-1-6 原因：解放升级工艺路线,判断路线为空的情况

      allinfomationColl.add(getHeader(myRouteList));

      //CCBegin by liunan 2009-02-21
      // CCEndby leixiao 2009-1-6 原因：解放升级工艺路线
      //原码如下：
      //allinfomationColl.add(getFirstLeveRouteListReport(myRouteList,sendToColl));
      //allinfomationColl.add(getTail1(sendToColl)); //发往单位
//    CCBeginby leixiao 2009-1-6 原因：解放升级工艺路线,sendToColl不能作为静态变量
      FolderService folderService = (FolderService) EJBServiceHelper.getService(
        "FolderService");
      FolderIfc folder = folderService.getFolder(myRouteList.getLocation());
      if (folder == null)
      {
        throw new QMException("您对资料夹" + myRouteList.getLocation() + "没有读权限!");
      }
      boolean isPersonFolder = folderService.isPersonalFolder(folder);
      //判断艺准是否在个人资料夹，是则计算生成，否则去搜报表保存表，有则提取，无则计算生成。
      if (isPersonFolder)
      {
        allinfomationColl.add(getFirstLeveRouteListReport(myRouteList,sendToColl));
        //CCBegin by liunan 2011-06-18 修改 getTail1 方法，见 getIsJ6 方法。
        //2011-06-23 liunan 改回去
        allinfomationColl.add(getTail1(sendToColl)); //发往单位
        //allinfomationColl.add(getTail1(sendToColl, getIsJ6(myRouteList.getRouteListNumber()))); //发往单位
        //CCEnd by liunan 2011-06-18
      }
      else
      {
      	//在reportRouteList表中搜索艺准master，返回空则计算生成明细和发往单位并保存，有则直接使用。
              try
              {
              	System.out.println("checkReport的数量为："+checkReport.size()+"个，具体内容如下：");
                Iterator ite1 = checkReport.iterator();
                while (ite1.hasNext())
                {
                  System.out.println("======="+ite1.next());
                }
                if(checkReport.contains(myRouteList.getMasterBsoID()))
                {
                	//System.out.println("==========进来就应该抛异常啦！！！");
                  //throw new QMRemoteException("当前艺准" + myRouteList.getRouteListNumber() + "正在由其他用户生成报表，请稍后使用!");
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

      allinfomationColl.add(getTail2(myRouteList)); //说明
      allinfomationColl.add(getWFProcessDetails2(myRouteList)); //流程信息
      sendToColl = null;
      //System.out.println("----------　------------------------------------"+"\n\n\n");
long t22 = System.currentTimeMillis();
      System.out.println("结束路线表"+routeListID+" "+user+"  "+user1+"时间为： "+(t22-t11)/1000+" 秒");
      System.out.println("--------------------------------------------------"+"\n\n\n");
      return allinfomationColl;

    }
     //  CCEnd by leixiao 2009-01-05 原因：解放升级工艺路线
     
        /**
     * 获得经过处理的路线说明
     * @return Collection
     */
    public static Collection getTail2(TechnicsRouteListIfc listInf) {
      Collection coll = new ArrayList();
      String tip = listInf.getRouteListDescription();
      if (tip == null) {
        return coll;
      }
      ///过滤掉“说明”中的分割符   liuming  add 20061212
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
      if (tip.indexOf("说明：") != -1) {
        tip1 = tip.substring(0, tip.indexOf("说明："));
        tip2 = tip.substring(tip.indexOf("说明："));
        coll.add(tip1);
        coll.add(tip2);
      }
      else if (tip.indexOf("说明:") != -1) {
        tip1 = tip.substring(0, tip.indexOf("说明:"));
        tip2 = tip.substring(tip.indexOf("说明:"));
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
        WfProcessIfc process = (WfProcessIfc) it.next(); //取最新的相关进程
        ArrayList list = getAllActivities(process.getBsoID());
        for (int i = 0; i < list.size(); i++) {
          WfActivityIfc waInfo = (WfActivityIfc) list.get(i);

          ArrayList paticipants = getAllPaticipantOfActivity(waInfo);
          // System.out.println("活动 " + waInfo.getName() + " 的参与者  " + paticipants);
          if (waInfo.getName().indexOf("批") > -1) {
            vec.setElementAt(paticipants, 0);
          }
          if (waInfo.getName().indexOf("审核") > -1) {
            vec.setElementAt(paticipants, 1);
          }
          if (waInfo.getName().indexOf("校对") > -1) {
            vec.setElementAt(paticipants, 2);
          }
          //2006年9月7日－闫琦：取出会签者，并在报表中将其列到校对者后
          if (waInfo.getName().indexOf("会签") > -1) {
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
     * 获取工作流程参与者信息。根据解放要求添加此方法。   20061201 liuming add
     * 要去除“驳回”之前的所有参与者记录。
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
        WfProcessIfc process = (WfProcessIfc) it.next(); //取最新的相关进程
        Vector infoVector = new Vector();
        //获取所有活动
        ArrayList list = getAllActivities(process.getBsoID());
        for (int i = 0; i < list.size(); i++) {
          WfActivityIfc activity = (WfActivityIfc) list.get(i);
          infoVector.addAll(getVoteInformation(activity));
        }
        //流程顺序：校对-会签-审核-批准，每个活动都可能有“驳回”行为
        //遇到“驳回”，就将之前的参与者去掉
        if (infoVector.size() > 0) {
          //System.out.println("全部参与者的个数："+infoVector.size());

          int oldSize = infoVector.size();
          Timestamp rejectTime = null;

          ////////////查看是否有“驳回”
          for (int i = 0; i < oldSize; i++) {
            Object[] array = (Object[]) infoVector.elementAt(i);
            String vote = array[2].toString();
            if (vote.indexOf("驳回") > -1) {
              Timestamp tempTime = (Timestamp) array[3];
              //System.out.println("驳回时间1 = " + rejectTime);
              if (rejectTime == null) {
                rejectTime = tempTime;
              }
              else {
                if (tempTime != null && tempTime.after(rejectTime)) {
                  rejectTime = tempTime;
                }
              }
              //System.out.println("驳回时间2 = " + rejectTime);
            }
          }

          Vector tv = new Vector();
          ////////////如果有“驳回”，则过滤
          if (rejectTime != null) {
          //  System.out.println("--------------有驳回-------------");
            //System.out.println("驳回时间 = " + rejectTime);
            for (int j = 0; j < oldSize; j++) {
              Object[] array = (Object[]) infoVector.elementAt(j);
              Timestamp time = (Timestamp) array[3];
              //String name = array[0].toString();
              //String user = array[1].toString();
              //System.out.println("进程o = "+name+user+time);
              if (time.after(rejectTime)) { //只保留晚于驳回时间的记录
                tv.add(array);
              }
            }

            //考虑：驳回到会签人的情况，这时校对人就被过滤掉了，需要找回来  20071022
            boolean noJiao = true;
            if (tv.size() > 0) {
              for (int t = 0; t < tv.size(); t++) {
                Object[] array = (Object[]) tv.elementAt(t);
                String name = array[0].toString();
                if (name.indexOf("校对") > -1) {
                  noJiao = false;
                  break;
                }
              }

              if (noJiao) { //没有校对人
                for (int g = 0; g < oldSize; g++) {
                  Object[] array = (Object[]) infoVector.elementAt(g);
                  String name = array[0].toString();
                  if (name.indexOf("校对") > -1) {
                    tv.add(array);
                    break;
                  }
                }
              }
            } /////////////////////////////20071022

          }
          else {
           // System.out.println("--------------无驳回-------------");
            tv = infoVector;
          }

          ///////////////////////////////////////////





          ArrayList jiaoduiVector = new ArrayList();
          ArrayList huiqianVector = new ArrayList();
          ArrayList shenheVector = new ArrayList();
          ArrayList pizhunVector = new ArrayList();
          /////////////对活动信息进行分类（校对、会签、审核、批准）
          if (tv.size() > 0) {
          //  System.out.println("--------------有剩余-------------");
            int newSize = tv.size();
            for (int k = 0; k < newSize; k++) {
              Object[] array = (Object[]) tv.elementAt(k);
              String name = array[0].toString();
              //System.out.println("进程 = " + name);
              String user = array[1].toString();
              if (name.indexOf("批") > -1) {
                //System.out.println("进程 = "+name);
                //如果是批准活动，要在参与者后面加上批准时间，该时间只要日期，不要具体的时、分、秒
                String modifyTime = array[3] + "";
                int i = modifyTime.trim().indexOf(" ");
                modifyTime = modifyTime.substring(0, i);
                pizhunVector.add(user + "：" + modifyTime);
              }
              else if (name.indexOf("审核") > -1) {
                //System.out.println("进程 = "+name);
                if (!shenheVector.contains(user))
                  shenheVector.add(user);
              }
              else if (name.indexOf("校对") > -1) {
                //System.out.println("进程 = "+name);
                if (!jiaoduiVector.contains(user))
                  jiaoduiVector.add(user);
              }
              else if (name.indexOf("会签") > -1) {
                //System.out.println("进程 = "+name);
                if (!huiqianVector.contains(user))
                  huiqianVector.add(user);
              }
            }
          } ///////////////////////分类完毕!

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

//  CCBegin by leixiao 2009-1-6 原因：解放升级工艺路线,性能优化
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
        	 // System.out.println("给我走进来-------");
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
                   // vote += vec.elementAt(i) + "、";
                    votebuffer.append(vec.elementAt(i));
                    if(i!=vec.size()-1)
                    {
                    	votebuffer.append("、");
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
          System.out.println("多少WfAssignmentID---"+WfAssignmentID);
          System.out.println("多少AssignmentBallotLink---"+coll.size());
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
              vote += vec.elementAt(i) + "、";
            }
          }*/
          //vote = vote.endsWith("、") ? vote.substring(0, vote.length() - 1) : vote;
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
//  CCEnd by leixiao 2009-1-6 原因：解放升级工艺路线

//  CCBeginby leixiao 2009-1-6 原因：解放升级工艺路线
    /**
     * 返回给定工作流中的所有过期的的活动
     * @param   process    给定的工作流对象
     * @return Iterator    工作流活动值对象集合
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
     *  根据活动得到所有的工作项，每个工作项的所有者即是该活动的所有参与者
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
          if (waInfo.getName().indexOf("批") > -1) {
            //如果是批准活动，要在参与者后面加上批准时间，该时间只要日期，不要具体的时、分、秒
            String modifyTime = workitemIfc.getModifyTime() + "";
            int i = modifyTime.trim().indexOf(" ");
            modifyTime = modifyTime.substring(0, i);
            paticipants.add(userinfo.getUsersDesc() + "：" +
                            modifyTime);

          }
          else {
            String userdesc = userinfo.getUsersDesc();
            if (!paticipants.contains(userdesc)) {
              paticipants.add(userdesc);
            }
          }
        }

        /*暂时不生成未完成的,如果添加去掉注释即可
                 else {
          ActorInfo userinfo = (ActorInfo) persistService.refreshInfo(ownerid);
          paticipants.add(paticipants + userinfo.getUsersDesc() );
                 }
         */
      }

      return paticipants;
    }
    //  CCEnd by leixiao 2009-1-6 原因：解放升级工艺路线

//CCBebin by liunan 2009-02-21
    /**
     * 艺准在首次或升级版本后生成报表时要经过计算处理获得明细然后保存到数据库表中，之后都从表中直接获取。
     * 获得明细和发往单位后添加到返回集合中。通过与数据库直接连接，
     * 用sql把明细内容插入到专门做艺准报表缓存的reportRouteList表中，
     * 然后在使用时根据艺准masterid搜索获得。
     * @param allinfomationColl ArrayList 组装的集合，
     * 已经添加了艺准报表的表头信息，在此方法中需要继续向里添加明细和发往单位。
     * @param myRouteList TechnicsRouteListIfc 艺准。
     * @param sendToColl ArrayList 发往单位集合。
     * @return ArrayList
     */
    public static ArrayList getReportRouteList(ArrayList allinfomationColl, TechnicsRouteListIfc myRouteList, ArrayList sendToColl)
  {
  	//System.out.println("=====================进入艺准报表处理方法！！！");
    Connection conn = null;
    Statement stmt =null;
    ResultSet rs=null;
  	try
  	{
  		//CCBegin by liunan 2011-05-17 根据艺准号判断是否J6车，增加参数 isJ6 
  		boolean isJ6 = getIsJ6(myRouteList.getRouteListNumber());
  		//CCEnd by liunan 2011-05-17
  		
  	  //搜索表，根据艺准的masterid搜索数量。
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
    	//判断结果是否为空，空则调用方法获得明细，然后保存；
    	//否则获得明细，判断是否最新，是则返回，否则删除明细，再获得明细并保存。
    	if(countList>0)
    	{
  		  //搜索，根据艺准的masterid和bsoid搜索数量。
  		  rs = stmt.executeQuery(countIdSql);
        rs.next();
        int countId = rs.getInt(1);
  		  //如果数量为1，表示要生成报表的艺准与表中缓存报表艺准版本一致，则返回，否则重新生成。
  		  if(countId==1)
  		  {
  		  	System.out.println("路线表直接生成。。。");
  			  //整理返回
  			  rs = stmt.executeQuery(toUnitSql);
  			  rs.next();
  			  toUnit = rs.getString(1);

  			  //搜索表，根据艺准masterid搜索所有字段。
  			  rs = stmt.executeQuery(listSql);

  			  //CCBegin by liunan 2011-05-17 根据艺准号判断是否J6车，增加参数 isJ6 
  			  //allinfomationColl = handleResultData(allinfomationColl,rs,toUnit);
  			  allinfomationColl = handleResultData(allinfomationColl,rs,toUnit,isJ6);
  			  //CCEnd by liunan 2011-05-17
  	  	}
  		  else
  		  {
  		  	System.out.println("路线表全新生成。。。");
                        //checkReport.add(myRouteList.getBsoID());
                        checkReport.add(myRouteList.getMasterBsoID());

  			  //删除表，删除masterid为当前艺准的数据
  			  stmt.executeQuery(deleteSql);

  			  //获得明细
  			  Collection dataList = getFirstLeveRouteListReport(myRouteList, sendToColl);

  			  //插入表，整理明细插入
  			  //CCBegin by liunan 2011-06-18 修改 getTail1 方法，见 getIsJ6 方法。
  			  //2011-06-23 liunan 改回去
  			  handleInsertData(myRouteList, dataList, getTail1(sendToColl), stmt);
  			  //handleInsertData(myRouteList, dataList, getTail1(sendToColl, isJ6), stmt);
  			  //CCEnd by liunan 2011-06-18

  			  //整理返回
  			  rs = stmt.executeQuery(toUnitSql);
  			  rs.next();
  			  toUnit = rs.getString(1);
  	    	rs = stmt.executeQuery(listSql);
  	  	  //CCBegin by liunan 2011-05-17 根据艺准号判断是否J6车，增加参数 isJ6 
  	  	  //allinfomationColl = handleResultData(allinfomationColl,rs,toUnit);
  	  	  allinfomationColl = handleResultData(allinfomationColl,rs,toUnit,isJ6);
  	  	  //CCEnd by liunan 2011-05-17

//                            checkReport.remove(myRouteList.getBsoID());
  	  	checkReport.remove(myRouteList.getMasterBsoID());
  		  }
  	  }
  	  else
  	  {
  	  	System.out.println("路线表全新生成。。。");
//  	  	checkReport.add(myRouteList.getBsoID());
  	   checkReport.add(myRouteList.getMasterBsoID());

  	  	//获得明细
  	  	Collection resultList = getFirstLeveRouteListReport(myRouteList,sendToColl);

  	  	//插入表，整理明细插入
  			//CCBegin by liunan 2011-06-18 修改 getTail1 方法，见 getIsJ6 方法。
  			//2011-06-23 liunan 改回去
  	  	handleInsertData(myRouteList, resultList, getTail1(sendToColl), stmt);
  	  	//handleInsertData(myRouteList, resultList, getTail1(sendToColl, isJ6), stmt);
  			//CCEnd by liunan 2011-06-18

  	  	//整理返回
  	  	rs = stmt.executeQuery(toUnitSql);
  	  	rs.next();
  	  	toUnit = rs.getString(1);
  	  	rs = stmt.executeQuery(listSql);
  	  	//CCBegin by liunan 2011-05-17 根据艺准号判断是否J6车，增加参数 isJ6 
  	  	//allinfomationColl = handleResultData(allinfomationColl,rs,toUnit);
  	  	allinfomationColl = handleResultData(allinfomationColl,rs,toUnit,isJ6);
  	  	//CCEnd by liunan 2011-05-17

//                           checkReport.remove(myRouteList.getBsoID());
  	                     checkReport.remove(myRouteList.getMasterBsoID());
    	}
    	//清空并关闭sql返回数据
  	  rs.close();
  	  //关闭Statement
  	  stmt.close();
  	  //关闭数据库连接
      conn.close();

      //System.out.println("=====================退出艺准报表处理方法！！！");
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
        	System.out.println("出现异常！！收回checkReport中当前艺准的标识。");
//        	checkReport.remove(myRouteList.getBsoID());
        	 checkReport.remove(myRouteList.getMasterBsoID());
        }
        return allinfomationColl;
    }
  }

  /**
   * 用sql把明细数据向表中做插入操作。
   * @param myRouteList TechnicsRouteListIfc 艺准。
   * @param dataList Collection 明细数据。
   * @param sendToColl String 发往单位。
   * @param stmt Statement 数据库类，用于执行sql操作。
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
          //arrayObjs数组中元素依次为：序号[0]、更改标记[1]、零部件编号[2]、零部件版本[3]、零部件名称[4]、每车数量[5]、制造路线[6]、
          //集合（装配路线、合件数量、上级件）[7]、零部件bsoid[8]、备注[9]、影响互换[10]。
          //indexNum(1),changeSign(2),partNumber(3),partVersion(4),partName(5),countAve(6),makeRoute(7),
          //assembleRoute(8),countAll(9),parentPart(10),partID(11),remark(12),isChange(13),toUnit(14)
          Object[] arrayObjs = (Object[]) ite.next();
          ArrayList informationlist = (ArrayList) arrayObjs[7];
          String[] info = handleInfo(informationlist);
          //只在第一条数据中记录路线表的艺准bsoid和发往单位。到时候通过masterid和序号1来搜索。
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
          //释放对象，减少内存占用。
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
   * 处理信息集合。将制造路线、合件数、上级件的集合分解成三个字符串。
   * @param informationlist ArrayList 制造路线、合件数、上级件的集合。
   * @return String[] 三个元素。制造露馅、合件数、上级件的字符串。
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
        //释放对象，减少内存占用。
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
   * 字符串处理，用于处理制造路线、合件数、上级件为空情况。
   * @param str String 字符串。
   * @return String 如果空返回##。
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
   * 将从数据库中搜索的结果进行处理，返回生成报表的数据结构。
   * @param allinfomationColl ArrayList 生成报表的数据结构，此处需要添加明细和发往单位。
   * @param rs ResultSet 数据库返回结果。
   * @param toUnit String 发往单位。
   * @return ArrayList 返回的报表所需的数据结构。
   */
  //CCBegin by liunan 2011-05-17 根据艺准号判断是否J6车，增加参数 isJ6 
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
      //三个元素：分别为“装配路线”、“合件数量”和“上级件号”
      String[] informationStr = null ;
      ArrayList informationlist = null;
      while(rs.next())
      {
        //arrayObjs数组中元素依次为：序号、更改标记、零部件编号、零部件版本、零部件名称、每车数量、制造路线、
        //集合（装配路线、合件数量、上级件）、零部件bsoid、备注、影响互换。
        //indexNum(1),changeSign(2),partNumber(3),partVersion(4),partName(5),countAve(6),makeRoute(7),
        //assembleRoute(8),countAll(9),parentPart(10),partID(11),remark(12),isChange(13),toUnit(14)
        //“装配路线”、“合件数量”和“上级件号”保存在表中都用“,”分割。
        String[] assembleRoute = rs.getString(8).split(",");
        String[] countAll = rs.getString(9).split(",");
        String[] parentPart = rs.getString(10).split(",");
        //CCBegin by leixiao 2010-6-2 解放艺准增加发图数量列
        //CCBegin by liunan 2011-05-17 根据艺准号判断是否J6车，增加参数 isJ6
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
        //CCEnd by leixiao 2010-6-2 解放艺准增加发图数量列
        //CCBegin by leixiao 2010-7-9 如果是广义部件，备注中加“广义部件”字样，以区分
        String remark=stringNullDeal(rs.getString(12));
        if(stringNullDeal(rs.getString(11)).startsWith("GenericPart_"))
        	remark="广义部件 "+remark;
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
        //CCBegin by leixiao 2010-6-2 解放艺准增加发图数量列
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
        		QueryCondition qc2 = new QueryCondition("displayName", " = ", "文档信息");
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
        //CCEnd by leixiao 2010-6-2 解放艺准增加发图数量列
        //添加一条明细
        result.add(arrayObjs);
      }
      //System.out.println("返回结果总数为： "+result.size()+" 个");
      allinfomationColl.add(result);
      allinfomationColl.add(toUnit);

      //释放对象，减少内存占用。
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
  //CCBegin by leixiao 2010-6-2 解放艺准增加发图数量列
  /**
   * 计算发图数
   * @param str String 制造路线　装配路线
   * @return String 发图数
   * liunan 2011-05-02 此次修改三处内容：
   * 1、路线中同时出现“总”、“用”的数量+1。
   * 2、多个路线都会给“车身生准组”或“车架生准组”发图，不累加，因此改成只发一份，并将系统代码管理中这些路线的数量不计“车身生准组”或“车架生准组”。
   * 3、路线“岛”判断是否J6，是则不发，-1，因为代码管理里计数为1。
   */
  //CCBegin by liunan 2011-05-17 根据艺准号判断是否J6车，增加参数 isJ6
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
	//CCBegin by liunan 2011-05-02 在“薄”、“焊”、“涂”、“饰”中都有一份是给“车身生准组”的，实际只需一份。
	//在“厚或架(钻)”、“厚（焊）”、“架（漆）”、“架（装）”中都有一份是给“车架生准组”的，实际只需一份。
	boolean cszFlag = false;
	boolean cjzFlag = false;
	//CCEnd by liunan 2011-05-02
	
	//CCBegin by liunan 2011-11-24 50以下的话 焊 和 厚(焊) 都是6+1，以上 都是3+1。代码管理中 厚焊 由6改成3。
	boolean hanFlag = false;
	//CCEnd by liunan 2011-11-24
	
//	System.out.println(route);
	allRouteDep=allRouteDep.replace("架(钻)","厚");
	//System.out.println("-----改后="+allRouteDep);
	//CCBegin by liunan 2011-11-30 根据解放最新需求 焊 厚(焊) 按一个路线处理数量，不需要分别加数。
	
	//作此修改后，后面代码中的 厚(焊) 的判断及处理，实际上不起作用了，也不会进入。
	allRouteDep=allRouteDep.replace("厚(焊)","焊");
	//子组判断
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
	      Collection result= cService.findDirectClassificationByName("路线组织机构","代码分类");
	      Iterator i=result.iterator();
	      CodingClassificationIfc codingIfc =null;
	      while(i.hasNext()){
	      	String type=(i.next()).toString();
	      	try {
				codingIfc = cService.findClassificationByName(type,"路线组织机构");
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
		//特别说明：以零件为单位，相同路线单位的发图数量不累加
        if(!newroute.contains(count1[i]))
        {
        newroute.add(count1[i]);	
        //CCBegin by liunan 2011-05-02 如果有发给“车身生准组”或“车架生准组”的则标识为真，后面+1。
        //CCBegin by liunan 2011-11-30 给身准 还是 架准 的 +1 跟子组有关 跟  焊 厚焊  没关了。
        //判断其零件子组号，当<50时数量为 +6厚焊+1架准，>=50时数量为 +3焊+1身准
        //if(count1[i].equals("薄")||count1[i].equals("焊")||count1[i].equals("涂")||count1[i].equals("饰"))
        if(count1[i].equals("薄")||(count1[i].equals("焊")&&Integer.parseInt(zizu)>=50)||count1[i].equals("涂")||count1[i].equals("饰"))
        {
        	cszFlag = true;//给身准
        }
        //if(count1[i].equals("厚")||count1[i].equals("厚(焊)")||count1[i].equals("架(装)")||count1[i].equals("架(漆)"))
        //CCBegin SS17
        //if(count1[i].equals("厚")||(count1[i].equals("焊")&&Integer.parseInt(zizu)<50)||count1[i].equals("架(装)")||count1[i].equals("架(漆)"))
        if(count1[i].equals("厚")||(count1[i].equals("焊")&&Integer.parseInt(zizu)<50)||count1[i].equals("架(装)")||count1[i].equals("架(漆)")||count1[i].equals("架(横)")||count1[i].equals("架(抛)"))
        //CCEnd SS17
        {
        	cjzFlag = true;//给架准
        }
        //CCEnd by liunan 2011-11-30
        //CCEnd by liunan 2011-05-02                
        
		//CCBegin by liunan 2011-11-24 50以下的话 焊 和 厚(焊) 都是6+1，以上 都是3+1。代码管理中 厚焊 由6改成3。
        if(count1[i].equals("焊"))
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
	//CCBegin by liunan 2011-05-17 增加多个路线都是协协时的判断。
	//if(route.equals("协,协")||route.equals("协-协,协")){//路线为协＝协时，
	//CCBegin by liunan 2011-11-11 新需求：当零件子组号为24、25、2912、2919、2902、30、31、3501、3502、3503、3519、3530、3550，
	//制造或装配路线中无“底”时，发图总数在原有基础上增加2份。
	//if(route.equals("协,协")||route.equals("协-协,协")||route.equals("协,协,协")||route.startsWith("协,协,协,协")){//路线为协＝协时，
	if(route.indexOf("底")==-1){
	//CCEnd by liunan 2011-05-17	
		//零件以24、25、2912、2919、30、31、3501、3502、3503、3519、3530开头的
		if(partNumber.startsWith("24")||partNumber.startsWith("25")||partNumber.startsWith("2912")||
		partNumber.startsWith("2919")||partNumber.startsWith("30")||partNumber.startsWith("31")||
		partNumber.startsWith("3501")||partNumber.startsWith("3502")||partNumber.startsWith("3519")||		
		partNumber.startsWith("2902")||partNumber.startsWith("3503")||partNumber.startsWith("3550")||//liunan 2011-11-11 新增2902、3503、3550三个子组。
		partNumber.startsWith("3530"))
		{
			allCount+=2;
		}
//		//零件以16、17、42开头的
//		if(partNumber.startsWith("16")||partNumber.startsWith("17")||partNumber.startsWith("42"))
//		{
//			allCount+=2;
//		}
		
	}
	
	//CCBegin SS4
	if(route.equals("协,协")||route.equals("协,协,协")||route.equals("协,协,协,协")||route.equals("协,协,协,协,协"))
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
	
	//CCBegin by leixiao 2011-1-14 解放艺准增加发图数量列
	//CCBegin by liunan 2011-05-02 只要路线中既有“总”又有“用”就+1
	//else if (route.indexOf("总,用")!=-1||route.indexOf("总-用")!=-1){
	//CCBegin by liunan 2011-11-16 饶飞发现此处注释并无 “底”  与“总用” 的关系，也咨询了沙显周，因此去掉 else，
	//源码 else if (route.indexOf("总")!=-1&&route.indexOf("用")!=-1){
	if (route.indexOf("总")!=-1&&route.indexOf("用")!=-1){
	//CCEnd by liunan 2011-11-16
	//CCEnd by liunan 2011-05-02	
		//System.out.println("---------");
		allCount+=1;
	}
	//CCEnd by leixiao 2010-1-14 解放艺准增加发图数量列
	//CCBegin by liunan 2011-10-26 如果为研-研，并且零件子组号在50（包括50）以上，则数量+1。
	/*else if (route.equals("研,研"))
	{
		String zizu = partNumber.substring(0,2);
		if(Integer.parseInt(zizu)>=50)
		{
			allCount+=1;
		}
	}*/ 
	//liunan 2011-11-11 注释掉因为此处需求还不确定。
	//CCEnd by liunan 2011-10-26
	
	//CCBegin by liunan 2011-05-02 如果有发给“车身生准组”或“车架生准组”的则数量+1。
	if(cszFlag)
	{
		allCount+=1;
	}
	if(cjzFlag)
	{
		allCount+=1;
	}
	//CCEnd by liunan 2011-05-02
	
	
	//CCBegin by liunan 2011-11-24 50以下的话 焊 和 厚(焊) 都是6+1，以上 都是3+1。代码管理中 厚焊 由6改成3。	
	if(hanFlag&&Integer.parseInt(zizu)<50)
	{
		allCount +=3 ;		
	}	
	//CCEnd by liunan 2011-11-24
	
	//CCBegin by liunan 2011-05-02 如果路线中有“岛”，则判断是否是J6，是则不发图。
	//J6车型特征:(1)*P6* (2)*.*-* (3)*J6* (4)####-* (5)####?-* （*零个或多个字符   #任意一个数字   ?任意一个字符）
	//CCBegin by liunan 2011-05-17 规则改变：路线中有“岛”则确定发图+1，没有则判断艺准号是否是J6，不是则+1
	/*if(route.indexOf("岛")!=-1)
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
		//判断是否是J6车
		if(partNumber.indexOf("P6")!=-1||partNumber.indexOf("J6")!=-1||
		   (partNumber.indexOf(".")!=-1&&partNumber.indexOf("-")!=-1)||
		   (digitFlag&&partNumber.indexOf("-")==4)||(digitFlag&&partNumber.indexOf("-")==5))
		{
			allCount = allCount-1;
		}
	}*/
	//CCBegin SS19
	//if(route.indexOf("岛")==-1&&!isJ6)
	//{
		//allCount = allCount + 1;
	//}
	//CCEnd SS19
	//CCEnd by liunan 2011-05-17
	//CCEnd by liunan 2011-05-02
	
	newroute=null;
	//CCBegin by liunan 2011-09-05 根据具体情况补充条件。
	//if(allCount!=0)
	//CCBegin SS19
	//allCount+=2;//所有的都加2份给固定用户
	allCount+=1;//只剩一份给张丽梅所在组
	//CCEnd SS19
//	System.out.println("发图数＝"+allCount);
	return String.valueOf(allCount);
}
  
  //CCEnd by leixiao 2010-6-2 解放艺准增加发图数量列
  
  
  //CCBegin SS26
  /**
   * 新版计算发图数
   * 简化路线发图数，很多单位的发图数不再计算。
   * @param partNumber String 零部件编号
   * @param makeRoute String 制造路线
   * @param assembleRoute String 装配路线
   * @param isJ6 boolean 是否J6标记（已不使用）
   * @return String 发图数
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
  	allRouteDep=allRouteDep.replace("厚(纵)","架(钻)");
  	allRouteDep=allRouteDep.replace("架(纵)","架(钻)");
  	allRouteDep=allRouteDep.replace("厚(试制)","厚(焊)");
  	allRouteDep=allRouteDep.replace("架(漆)","架(抛)");
  	
  	//子组判断
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
	      Collection result= cService.findDirectClassificationByName("路线组织机构","代码分类");
	      Iterator i=result.iterator();
	      CodingClassificationIfc codingIfc =null;
	      while(i.hasNext())
	      {
	      	String type=(i.next()).toString();
	      	codingIfc = cService.findClassificationByName(type,"路线组织机构");
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
	  	//特别说明：以零件为单位，相同路线单位的发图数量不累加
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
    
    //多个路线都是协协时的判断。
    if(route.equals("协,协")||route.equals("协,协,协")||route.equals("协,协,协,协")||route.equals("协,协,协,协,协"))
    {
    	//车桥的零件以 24,25,2902,2912,2919,30,31,3501,3502,3503,3519,3530,3550 开头，多个协时发2份
    	if(partNumber.startsWith("24")||partNumber.startsWith("25")||partNumber.startsWith("2902")||
    	   partNumber.startsWith("2912")||partNumber.startsWith("2919")||partNumber.startsWith("30")||
    	   partNumber.startsWith("31")||partNumber.startsWith("3501")||partNumber.startsWith("3502")||
    	   partNumber.startsWith("3503")||partNumber.startsWith("3519")||partNumber.startsWith("3530")||
    	   partNumber.startsWith("3550"))
    	{
    		allCount+=2;
    	}
    	
    	//变速箱的零件以 1601,1602,1607,1701,1702,1706,1707,3506,3507,3508,3533,3550,3611,3724,3774,4207 开头，多个协时发3份
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
    //如果是xxxxx21零部件则加一份
    if(partNumber.length()>=7&&partNumber.substring(5,7).equals("21"))
    {
    	allCount+=1;
    }
    //都要给一份到文件室
    allCount+=1;
    //System.out.println("发图数＝"+allCount);
    return String.valueOf(allCount);
  }
  //CCEnd SS26

/**
   * 空串处理。
   * @param str String 明细字符串。
   * @return String 如果null，返回""。
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

  //CCBegin by liunan 2011-05-17 根据艺准号判断是否J6车，返回参数 isJ6 ,J6车不往青岛发。  
  //CCBegin by liunan 2011-06-18 发往单位和发图数规则修改，如果单位中不包含“岛”，则判断是否需要往青岛发图
  //目前往青岛发图的车型是非J6和J6L、J6M三类车型。
  //返回true则表示不需要发图，false表示需要发图。
  //CCBegin by liunan 2011-10-26 新增 是军车 也不往青岛发。 2120J* 2150J* 1218J* 都是军车。
  private static boolean getIsJ6(String num)
  {
  	System.out.println("num==="+num);
  	boolean isJ6 = false;
  	if(num.startsWith("前准")||num.startsWith("临准")||num.startsWith("艺准")||num.startsWith("艺毕")||num.startsWith("艺废"))
  	{
  		num = num.substring(2);
  	}
  	if(num.startsWith("艺试准"))
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
		//判断是否是J6车
		if(num.indexOf("P6")!=-1||num.indexOf("J6")!=-1||
		   (num.indexOf(".")!=-1&&num.indexOf("-")!=-1)||
		   (digitFlag&&num.indexOf("-")==4)||(digitFlag&&num.indexOf("-")==5))
		{
			isJ6 = true;
		}
		//CCBegin by liunan 2011-06-18 新增规则判断是否是J6L和J6M车型。
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
		
		//CCBegin by liunan 2011-10-26 是否军车，是返回true，数量不加1。
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
								//System.out.println("上级件："+ifc.getPartNumber());
								level = getlevel(expandByProduct,flag,parentMap2000,parentMap,ifc.getBsoID(),ifc.getMasterBsoID(),ifc.getPartNumber(),level);
							}
							else if(linkid.startsWith("Part"))
							{
								QMPartIfc ifc = (QMPartIfc) pservice.refreshInfo(linkifc.getRightBsoID());
								//System.out.println("上级件："+ifc.getPartNumber());
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
			if (groupName.equals("青汽分公司用户组")) 
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