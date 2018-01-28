/**
 * 生成程序 RouteListPartLinkJPanel.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/5/31 郭晓亮   原因:零件关联的列表中可以添加重复的零件,
 *                             而且当删除一个件后不能重新添加此件.
 *                        方案:删除缓存中已经被删除的零件.
 *                        
 *                        
 * CR2  2009/06/08 郭晓亮  参见:测试域:v4r3FunctionTest;TD号2286
 * 
 * SS1 添加路线是否自动获取最新路线的复选框标识，默认选中，即自动添加最新路线，然后在路线编辑界面由用户决定是否修改。 liunan 2013-4-17
 * SS2 在编辑路线表零件tab页中，增加批量添加功能   guoxiaoliang 2011-8-3
 * SS3 根据解放要求，需要添加“变速箱生产准备”状态零部件。 liunan 2014-3-19
 * SS4 前准、艺准、试制、临准4个类别的通知书去掉艺准零部件列表中原来的按钮“更改通知单号添加”和“采用解放添加”，新增按钮“更改通知书”和“采用通知书”2014-6-4
 * SS5 增加颜色件标识 liuyang 2014-6-6
 * SS6 添加另存为复选框 liuyang 2014-6-7
 * SS7 设置按钮“更改通知书”和“采用通知书”的显示名，和”另存为路线“的可见性 liuyang 2014-6-18
 * SS8 去掉setCheckModel的设置，引起了选中一行修改数量（颜色件）后无法继续修改下一行数量（颜色件），必须先选中其他列，再回来数量才能修改。 liunan 2014-12-9
 * SS9 A004-2015-3109艺准自动修改说明内容。 liunan 2015-5-6
 * SS10 A004-2015-3112工艺合件（路线）申请单。 liunan 2015-5-26
 * SS11 A004-2015-3112补充允许添加变速箱的“变速箱试制”、“变速箱生产”状态零部件。 liunan 2015-6-11
 * SS12 A004-2015-3112补充允许添加“试制”状态零部件。 liunan 2015-6-26  (2016-8-18根据李萍要求去掉)
 * SS13 A004-2015-3161 路线状态不同数量不同，无法正确对应，客户端与报表不一致。 liunan 2015-7-8
 * SS14 SS13补充修改，如果零部件是带回最新路线，则状态内容对应不上，partindex里是“无”，而路线里会有状态。改为保存时获取一下已有路线的状态，与后面一致。 liunan 2015-8-5
 * SS15 A004-2016-3293 废弃状态的零件也需要能编艺废通知书 liunan 2016-1-25
 * SS16 不允许有相同零部件，则提示出来哪个相同。 liunan 2016-3-23
 * SS17 添加“数量赋值1”按钮，把数量是0的数量都改成1。 liunan 2016-5-16
 * SS18 A004-2016-3390 1、对于重复件统一一个对话框提示，而不是一个一个提示。2、添加一个删除重复件的功能 liunan 2016-6-29
 * SS19 A004-2016-3436 新增已废弃状态的支持 liunan 2016-12-21
 * SS20 添加零部件，支持技术问题通知单搜到的临准零部件。 liunan 2017-5-2
 * SS21 试制任务单发布后，允许艺试准的路线添加试制状态零部件。并将此前带有艺试准的字样改为试制 liunan 2017-6-28 改回试制 2017-9-6

 */
package com.faw_qm.cappclients.capproute.view;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.faw_qm.cappclients.capproute.controller.*;
import com.faw_qm.cappclients.capproute.view.*;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.clients.util.*;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.part.client.main.model.*;
import com.faw_qm.part.model.*;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.clients.beans.query.*;
import com.faw_qm.technics.route.util.RouteListLevelType;
import com.faw_qm.cappclients.capp.util.ProgressService;

/**
 * 工艺路线表对应的零部件表的编辑界面。 在更新工艺路线表时，编辑要编制工艺路线的零部件表，可以添加零部件、删除零部件。
 * Copyright: Copyright (c) 2004
 * Company: 一汽启明
 * @author 刘明
 * @mender skybird
 * @version 1.0
 */
public class RouteListPartLinkJPanel
    extends RParentJPanel
    implements RefreshListener {
  /** 选择添加零部件的界面 */
  public SelectPartJDialog selectPartDialog;

  /** 零部件列表 */
  //  private QMMultiList qMMultiList = new QMMultiList();
//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线
  private ComponentMultiList qMMultiList = new ComponentMultiList();
//CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线 

  private JPanel buttonPanel = new JPanel();

  private JButton addStructJButton = new JButton();

  private JButton addJButton = new JButton();
  
//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加"采用通知书添加"按钮 ,"空路线添加"按钮,"计算"按钮 
//2009-3-18 原因：解放升级工艺路线,增加"艺准通知书添加"按钮 
 JButton routelistButton = new JButton();//艺准添加按钮
 JButton adoptNoticeButton = new JButton();//采用通知书添加按钮
 JButton newButton = new JButton();//空路线添加按钮
 JButton jsButton = new JButton(); //计算数量按钮
//CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线,增加"采用通知书添加"按钮  
//CCBegin by leixiao 2010-11-30 原因：增加"按发布令号添加"按钮 
 JButton publishButton = new JButton();//采用通知书添加按钮
//CCEnd by leixiao 2010-11-30 原因：增加"按发布令号添加"按钮 
//CCBegin by leixiao 2011-1-12 原因：增加"按结构添加按钮"按钮 
 JButton structButton = new JButton();//结构添加按钮
//CCEnd by leixiao 2011-1-12 原因：增加"按结构添加按钮"按钮 
 private ArrayList updateLinksList = new ArrayList();//20070530 liuming add 存储仅更新的零件主信息 应存储更新后的关联 
 
 
//CCBegin SS2
  JButton addMultPartsJButton1 = new JButton();
//CCEnd SS2

//CCBegin SS10
  JButton addAssemblyListPartsJButton = new JButton();
//CCEnd SS10

  //CCBegin SS17
  JButton setCountButton = new JButton(); //数量赋值1
  //CCEnd SS17
  
  //CCBegin SS18
  JButton deleteYbSamePartButton = new JButton(); //清理艺毕重复件
  //CCEnd SS18

  //记录选中的列表行号
  private int theSelectedNum;

  /**
   * 业务对象:路线表
   */
  private TechnicsRouteListIfc routeListInfo;

  /** 代码测试变量 */
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.cappclients.verbose", "true")).equals("true");

  /** 缓存:界面中出现的所有零部件 */
  private HashMap allParts = new HashMap();

  //工艺路线表原来所关联的零部件经删除后所剩的零部件中所要更改父件编号的集合部件
  private Vector partsToChange = new Vector();

  /** 存储删除的零部件主信息 */
  private HashMap deleteParts = new HashMap();

  /** 存储新添加的零部件主信息 */
  private HashMap addedParts = new HashMap();

  private boolean isChangeRouteList = false;

  private JButton parentPartJButton = new JButton();

  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  private GridBagLayout gridBagLayout2 = new GridBagLayout();

  private JPanel jPanel1 = new JPanel();

  private JButton removeJButton = new JButton();

  private GridBagLayout gridBagLayout3 = new GridBagLayout();  

  private JButton upJButton = new JButton();

  private JButton downJButton = new JButton();

  private QMPartIfc theProductifc = null;
  
  //CCBegin by liunan 2012-05-21 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
  JCheckBox manufacturingCheckBox = new JCheckBox();
  //CCEnd by liunan 2012-05-21 
  
  //CCBegin SS1
  public JCheckBox addLastRouteCheckBox = new JCheckBox();
  //CCEnd SS1
  //CCBegin SS6
  public JCheckBox saveAs =new JCheckBox();
  //CCEnd SS6
  
  //CCBegin SS9
  public RouteListTaskJPanel rltJPanel = null;
  //CCEnd SS9
  
  //CCBegin SS18
  public static String samepartstr = "";
  //CCEnd SS18

  /**
   * 构造函数
   */
  public RouteListPartLinkJPanel() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    RefreshService.getRefreshService().addRefreshListener(this);
  }

  private Vector masterToPart(Vector vect) throws QMRemoteException {

    Vector parts = new Vector();
    for (int i = 0; i < vect.size(); i++) {
      QMPartMasterIfc subMaster = (QMPartMasterIfc) vect.elementAt(i);
      parts.add(filterPart(subMaster.getBsoID()));
    }
    return parts;
  }
  
  //CCBegin SS9
  public void setRLTJPanel(RouteListTaskJPanel jp)
  {
  	rltJPanel = jp;
  }
  //CCEnd SS9

  /**
   * 更新对象
   * @param re 更新事件
   */
//CCBegin by leixiao 2008-7-31
  public void refreshObject(RefreshEvent re) {
	   if (re.getSource().equals("addSelectedParts")) {
		
	     Object obj = re.getTarget();
	     if (obj instanceof Vector) {
	       Vector vect = (Vector) obj;
	      //CCBegin SS9 
	      if(vect.elementAt(0) instanceof String)
	      {
	       	 String str = (String)vect.elementAt(0);
	       	 String[] strs = str.split(",");
	       	 vect.remove(0);
	       	 //System.out.println("str===="+str);
	       	 String s = rltJPanel.descriJTextArea.getText().trim();
	       	 //首次添加时
	       	 if(s.indexOf("技术中心PDM部件更改说明单      ")>0)
	       	 {
	       	 	 s = s.replaceAll("技术中心PDM部件更改说明单      ",str.replaceAll(",",""));
	       	 }
	       	 else if(s.indexOf("技术中心PDM部件更改说明单/技术问题通知单/产品采用及更改通知书      ")>0)
	       	 {
	       	 	 s = s.replaceAll("技术中心PDM部件更改说明单/技术问题通知单/产品采用及更改通知书      ",str.replaceAll(",",""));
	       	 }
	       	 else if(s.indexOf("技术中心采用通知书（PDM部件更改说明单）       及艺准     ")>0)
	       	 {
	       	 	 s = s.replaceAll("技术中心采用通知书（PDM部件更改说明单）       及艺准     ",str.replaceAll(",",""));
	       	 }
	       	 else if(s.indexOf("技术问题通知单（采用通知书）    ")>0)
	       	 {
	       	 	 s = s.replaceAll("技术问题通知单（采用通知书）    ",str.replaceAll(",",""));
	       	 }
	       	 else if(s.indexOf("技术中心PDM部件更改说明单")>0&&strs[0].equals("技术中心PDM部件更改说明单")&&s.indexOf(strs[1])==-1)
	       	 {
	       	 	 int i = s.indexOf("及本艺准进行生产准备");
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("及本前准进行生产准备");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("所列内容生产准备完毕,可投入生产");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("废弃所列内容");
	       	   }
	       	   if(i==-1)
	       	   {
	       	   	 String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	       	   	 JOptionPane.showMessageDialog(null, "艺准说明内容不符合自动添加规则！", title, JOptionPane.WARNING_MESSAGE);
             }
	       	 	 s = s.substring(0,i) + "、" + strs[1] + s.substring(i);
	       	 }
	       	 else if(s.indexOf("解放公司更改通知单")>0&&strs[0].equals("解放公司更改通知单")&&s.indexOf(strs[1])==-1)
	       	 {
	       	 	 int i = s.indexOf("及本艺准进行生产准备");
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("及本前准进行生产准备");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("所列内容生产准备完毕,可投入生产");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("废弃所列内容");
	       	   }
	       	   if(i==-1)
	       	   {
	       	   	 String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	       	   	 JOptionPane.showMessageDialog(null, "艺准说明内容不符合自动添加规则！", title, JOptionPane.WARNING_MESSAGE);
             }
	       	 	 s = s.substring(0,i) + "、" + strs[1] + s.substring(i);
	       	 }
	       	 else if(s.indexOf("技术中心采用通知书")>0&&strs[0].equals("技术中心采用通知书")&&s.indexOf(strs[1])==-1)
	       	 {
	       	 	 int i = s.indexOf("及本艺准进行生产准备");
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("及本前准进行生产准备");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("所列内容生产准备完毕,可投入生产");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("废弃所列内容");
	       	   }
	       	   if(i==-1)
	       	   {
	       	   	 String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	       	   	 JOptionPane.showMessageDialog(null, "艺准说明内容不符合自动添加规则！", title, JOptionPane.WARNING_MESSAGE);
             }
	       	 	 s = s.substring(0,i) + "、" + strs[1] + s.substring(i);
	       	 }
	       	 else if(s.indexOf("解放公司采用通知书")>0&&strs[0].equals("解放公司采用通知书")&&s.indexOf(strs[1])==-1)
	       	 {
	       	 	 int i = s.indexOf("及本艺准进行生产准备");
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("及本前准进行生产准备");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("所列内容生产准备完毕,可投入生产");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("废弃所列内容");
	       	   }
	       	   if(i==-1)
	       	   {
	       	   	 String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	       	   	 JOptionPane.showMessageDialog(null, "艺准说明内容不符合自动添加规则！", title, JOptionPane.WARNING_MESSAGE);
             }
	       	 	 s = s.substring(0,i) + "、" + strs[1] + s.substring(i);
	       	 }
	       	 //CCBegin SS10
	       	 else if(s.indexOf("工艺合件（路线）申请单")>0&&strs[0].equals("工艺合件（路线）申请单")&&s.indexOf(strs[1])==-1)
	       	 {
	       	 	 int i = s.indexOf("及本艺准进行生产准备");
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("及本前准进行生产准备");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("所列内容生产准备完毕,可投入生产");
	       	   }
	       	 	 if(i==-1)
	       	 	 {
	       	 	 	 i = s.indexOf("废弃所列内容");
	       	   }
	       	   if(i==-1)
	       	   {
	       	   	 String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	       	   	 JOptionPane.showMessageDialog(null, "艺准说明内容不符合自动添加规则！", title, JOptionPane.WARNING_MESSAGE);
             }
	       	 	 s = s.substring(0,i) + "、" + strs[1] + s.substring(i);
	       	 }
	       	 //CCEnd SS10
	       	 
	       	 rltJPanel.descriJTextArea.setText(s);
	      }
	      //CCEnd SS9
	       addPartsList(vect);
	     }
	   }
  }
//CCEnd by leixiao 2008-7-31
  /**
   * 计算数量
   * @param parts 零部件
   * @param parent 父件
   * @param product 产品
   * @return
   * @throws QMRemoteException
   */
  public HashMap calCountInProduct(Vector parts, QMPartIfc parent,
                                   QMPartIfc product) throws QMRemoteException {
    if (verbose) {
      System.out.println("计算数量====" + "part==" + parts + "parent=="
                         + parent + "product==" + product);
    }
    if (product == null) {
      return new HashMap();
    }
    RequestServer server = RequestServerFactory.getRequestServer();
    ServiceRequestInfo info = new ServiceRequestInfo();
    info.setServiceName("TechnicsRouteService");
    info.setMethodName("calCountInProduct");
    Class[] paramClass = {
        Vector.class, QMPartIfc.class,
        QMPartIfc.class};
    info.setParaClasses(paramClass);
    Object[] paramValue = {
        parts, parent, product};
    info.setParaValues(paramValue);
    try {
      return (HashMap) server.request(info);
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
      throw ex;
    }

  }

  /**
   * 计算数量
   * @param parts 零部件
   * @param parent 父件
   * @param product 产品
   * @return
   * @throws QMRemoteException
   */
  public int calCountInProduct(QMPartIfc part, QMPartIfc parent,
                               QMPartIfc product) throws QMRemoteException {
    if (verbose) {
      System.out.println("计算数量====" + "part==" + part + "parent=="
                         + parent + "product==" + product);
    }
    RequestServer server = RequestServerFactory.getRequestServer();
    ServiceRequestInfo info = new ServiceRequestInfo();
    info.setServiceName("TechnicsRouteService");
    info.setMethodName("calCountInProduct");
    Class[] paramClass = {
        QMPartIfc.class, QMPartIfc.class,
        QMPartIfc.class};
    info.setParaClasses(paramClass);
    Object[] paramValue = {
        part, parent, product};
    info.setParaValues(paramValue);
    try {
      return ( (Integer) server.request(info)).intValue();
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
      throw ex;
    }

  }

  /**
   * 周茁添加 整体调服务减少调服务次数（开发组4月性能测试优化）
   * @param vec Collection
   * @return Collection
   */
  private static Collection filterPart(Collection vec) {
    ConfigSpecItem configSpecItem = SelectPartJDialog.getPartTreePanel()
        .getConfigSpecItem();
    if (configSpecItem == null) {
      System.out.println("出错，配置规范没有初始化");
      SelectPartJDialog.getPartTreePanel().setConfigSpecCommand();
      configSpecItem = SelectPartJDialog.getPartTreePanel()
          .getConfigSpecItem();
    }
    try {
      if (configSpecItem == null) {
        Class[] classes = {
            Collection.class};
        Object[] objs = {
            vec};
        return (Collection) CappRouteAction.useServiceMethod(
            "TechnicsRouteService",
            "filteredIterationsOfByDefault", classes, objs);
      }
      else {
        Class[] classes = {
            Collection.class, PartConfigSpecIfc.class};
        Object[] objs = {
            vec, configSpecItem.getConfigSpecInfo()};

        Collection col = (Collection) CappRouteAction.useServiceMethod(
            "StandardPartService", "filteredIterationsOf", classes,
            objs);
        // System.out.println(" else " + col.size());
        return col;
      }
    }
    catch (QMRemoteException e) {
      e.printStackTrace();
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), title,
                                    JOptionPane.WARNING_MESSAGE);
      return null;
    }

  }

  /**
   * 零部件过滤器
   * @param master
   * @return
   */
  public static QMPartIfc filterPart(String master) {
    // System.out.println("zz 进入 方法 filterPart ");
    ConfigSpecItem configSpecItem = SelectPartJDialog.getPartTreePanel()
        .getConfigSpecItem();
    if (configSpecItem == null) {
      System.out.println("出错，配置规范没有初始化");
      SelectPartJDialog.getPartTreePanel().setConfigSpecCommand();
      configSpecItem = SelectPartJDialog.getPartTreePanel()
          .getConfigSpecItem();
    }
    try {
      QMPartMasterInfo partmaster = (QMPartMasterInfo) CappTreeHelper
          .refreshInfo(master);
      if (configSpecItem == null) {
        Class[] classes = {
            QMPartMasterIfc.class};
        Object[] objs = {
            partmaster};
        //QMPartIfc qq = (QMPartIfc) CappRouteAction.useServiceMethod(
            //"TechnicsRouteService",
            //"filteredIterationsOfByDefault", classes, objs);
        // System.out.println(" filterPart 方法里configSpecItem == null  " + qq);
        return (QMPartIfc) CappRouteAction.useServiceMethod(
            "TechnicsRouteService",
            "filteredIterationsOfByDefault", classes, objs);
      }
      else {
        Vector vec = new Vector();
        vec.add(partmaster);
        Class[] classes = {
            Collection.class, PartConfigSpecIfc.class};
        Object[] objs = {
            vec, configSpecItem.getConfigSpecInfo()};

        Collection col = (Collection) CappRouteAction.useServiceMethod(
            "StandardPartService", "filteredIterationsOf", classes,
            objs);
        // System.out.println("调服务之后 的collection  " + col.size());
        if (col != null && col.size() > 0) {
          Object[] parts = (Object[]) col.toArray()[0];
          //  System.out.println("filterPart 方法里 else" + (QMPartIfc) parts[0]);
          return (QMPartIfc) parts[0];

        }
      }
    }
    catch (QMRemoteException e) {
      e.printStackTrace();
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), title,
                                    JOptionPane.WARNING_MESSAGE);
      return null;
    }
    return null;
  }

  /**
   * private QMPartIfc filterPart(String master) {
   *
   * ConfigSpecItem configSpecItem = SelectPartJDialog.getPartTreePanel()
   * .getConfigSpecItem(); if (configSpecItem == null) {
   * System.out.println("出错，配置规范没有初始化");
   * SelectPartJDialog.getPartTreePanel().setConfigSpecCommand();
   * configSpecItem = SelectPartJDialog.getPartTreePanel()
   * .getConfigSpecItem(); } Hashtable table = null; try { QMPartMasterInfo
   * partmaster = (QMPartMasterInfo) CappTreeHelper .refreshInfo(master);
   * QMPartMasterInfo[] partMasters = { partmaster }; //获得PART集合 table =
   * PartHelper.getAllVersionsNow(partMasters, configSpecItem
   * .getConfigSpecInfo()); } catch (QMRemoteException e) { String title =
   * QMMessage.getLocalizedMessage(RESOURCE, "information", null);
   * JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), title,
   * JOptionPane.WARNING_MESSAGE); return null; } if (table != null) {
   * //获得part集合 Vector part = (Vector) table.get("part"); //获得master集合 Vector
   * partMaster = (Vector) table.get("partmaster"); //如果没有不符合的PartMaster if
   * (partMaster.size() == 0) { return (QMPartInfo) part.elementAt(0); }
   * //如果有不符合的PartMaster else { //jMenuConfigCrit.setEnabled(true);
   * PartShowMasterDialog dialog = new PartShowMasterDialog( partMaster);
   * dialog.setSize(400, 300); Dimension screenSize =
   * Toolkit.getDefaultToolkit() .getScreenSize(); Dimension frameSize =
   * dialog.getSize(); if (frameSize.height > screenSize.height) {
   * frameSize.height = screenSize.height; } if (frameSize.width >
   * screenSize.width) { frameSize.width = screenSize.width; }
   * dialog.setLocation((screenSize.width - frameSize.width) / 2,
   * (screenSize.height - frameSize.height) / 2); dialog.show(); } } return
   * null; }
   */

  /**
   * 初始化
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout2);
    buttonPanel.setLayout(gridBagLayout1);
    addStructJButton.setMaximumSize(new Dimension(125, 23));
    addStructJButton.setMinimumSize(new Dimension(125, 23));
    addStructJButton.setPreferredSize(new Dimension(125, 23));
    addStructJButton.setActionCommand("从产品结构中添加(P)");
    addStructJButton.setText("从产品结构添加(P)");
    addStructJButton.setMnemonic('P');
    addStructJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addStructJButton_actionPerformed(e);
      }
    });
    addJButton.setMaximumSize(new Dimension(75, 23));
    addJButton.setMinimumSize(new Dimension(75, 23));
    addJButton.setPreferredSize(new Dimension(75, 23));
    addJButton.setText("添加(A)");
    addJButton.setMnemonic('A');
    addJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addJButton_actionPerformed(e);
      }
    });
//  CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,手动输入数量 
//  qMMultiList.addActionListener(new java.awt.event.ActionListener() {
//    public void actionPerformed(ActionEvent e) {
//      qMMultiList_actionPerformed(e);
//    }
//  });
//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线, 
  
//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加"采用通知书添加"按钮 
  adoptNoticeButton.setMaximumSize(new Dimension(130, 23));
  adoptNoticeButton.setMinimumSize(new Dimension(130, 23));
  adoptNoticeButton.setPreferredSize(new Dimension(130, 23));
  adoptNoticeButton.setActionCommand("adoptnoticeButton");
  //CCBegin by liunan 2011-04-07 根据最新需求，有两个搜索入口，一个是“更改通知书号”，一个是“采用编号-解放”
  //adoptNoticeButton.setText("采用更改通知添加");
  adoptNoticeButton.setText("更改通知书号添加");
  //CCEnd by liunan 2011-04-07
  adoptNoticeButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
      adoptNoticeButton_actionPerformed(e);
    }
  });
//CCEnd by leixiao 2008-7-31  原因：解放升级工艺路线,增加"采用通知书添加"按钮    
  
//CCBegin by leixiao 2010-11-30 原因：增加"按发布令号添加"按钮 
  publishButton.setMaximumSize(new Dimension(110, 23));
  publishButton.setMinimumSize(new Dimension(110, 23));
  publishButton.setPreferredSize(new Dimension(110, 23));
  publishButton.setActionCommand("publishButton");
  //CCBegin by liunan 2011-04-07 根据最新需求，有两个搜索入口，一个是“更改通知书号”，一个是“采用编号-解放”
  //publishButton.setText("发布令号添加");

 publishButton.setText("采用解放添加");

  //CCEnd by liunan 2011-04-07
  publishButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
    	publishButton_actionPerformed(e);
    }
  });
//CCEnd by leixiao 2010-11-30 原因：增加"按发布令号添加"按钮   

//CCBegin by leixiao 2011-1-12 原因：增加"按总成结构添加"按钮 
  structButton.setMaximumSize(new Dimension(110, 23));
  structButton.setMinimumSize(new Dimension(110, 23));
  structButton.setPreferredSize(new Dimension(110, 23));
  structButton.setActionCommand("publishButton");
  structButton.setText("总成结构添加");
  structButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
    	structButton_actionPerformed(e);
    }
  });
//CCEnd by leixiao 2011-1-12 原因：增加"按总成结构添加"按钮   
 
//CCBegin by leixiao 2009-3-18 原因：解放升级工艺路线,增加"艺准通知书添加"按钮 
  routelistButton.setMaximumSize(new Dimension(100, 23));
  routelistButton.setMinimumSize(new Dimension(100, 23));
  routelistButton.setPreferredSize(new Dimension(100, 23));
  routelistButton.setActionCommand("adoptnoticeButton");
  routelistButton.setText("艺准通知书添加");
  routelistButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
  	  addroutelistJButton_actionPerformed(e);
    }
  });
//CCEnd by leixiao 2009-3-18 原因：解放升级工艺路线,增加"艺准通知书添加"按钮    

//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加"空路线添加"按钮 
  newButton.setMaximumSize(new Dimension(100, 23));
  newButton.setMinimumSize(new Dimension(100, 23));
  newButton.setPreferredSize(new Dimension(100, 23));
  newButton.setText("空路线添加");
  newButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
      newButton_actionPerformed(e);
    }
  });
//CCBEnd by leixiao 2008-7-31 原因：解放升级工艺路线,增加"空路线添加"按钮  


    //CCBegin SS2
    addMultPartsJButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          addMultPartsJButton1_actionPerformed(e);
        }
      });
      addMultPartsJButton1.setText("批量添加");
      addMultPartsJButton1.setActionCommand("批量添加零件");
      addMultPartsJButton1.setPreferredSize(new Dimension(100, 23));
      addMultPartsJButton1.setMinimumSize(new Dimension(100, 23));
      addMultPartsJButton1.setMaximumSize(new Dimension(100, 23));
    //CCEnd SS2

    //CCBegin SS10
    addAssemblyListPartsJButton.addActionListener(new java.awt.event.ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		addAssemblyListPartsJButton_actionPerformed(e);
    	}
    });
    addAssemblyListPartsJButton.setText("路线申请");
    addAssemblyListPartsJButton.setActionCommand("按工艺合件（路线）申请单添加零件");
    addAssemblyListPartsJButton.setPreferredSize(new Dimension(100, 23));
    addAssemblyListPartsJButton.setMinimumSize(new Dimension(100, 23));
    addAssemblyListPartsJButton.setMaximumSize(new Dimension(100, 23));
    //CCEnd SS10
    
//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加"计算数量"按钮 
  jsButton.setMaximumSize(new Dimension(87, 23));
  jsButton.setMinimumSize(new Dimension(87, 23));
  jsButton.setOpaque(true);
  jsButton.setPreferredSize(new Dimension(87, 23));
  jsButton.setText("计算数量");
  jsButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
      jsButton_actionPerformed(e);
    }
  });
//CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线,增加"计算数量"按钮 

    //CCBegin SS17
    setCountButton.setMaximumSize(new Dimension(87, 23));
    setCountButton.setMinimumSize(new Dimension(87, 23));
    setCountButton.setOpaque(true);
    setCountButton.setPreferredSize(new Dimension(87, 23));
    setCountButton.setText("数量赋值1");
    setCountButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        setCountButton_actionPerformed(e);
      }
    });
    //CCEnd SS17
    
    //CCBegin SS18
    deleteYbSamePartButton.setMaximumSize(new Dimension(87, 23));
    deleteYbSamePartButton.setMinimumSize(new Dimension(87, 23));
    deleteYbSamePartButton.setOpaque(true);
    deleteYbSamePartButton.setPreferredSize(new Dimension(87, 23));
    deleteYbSamePartButton.setText("清理艺毕重复件");
    deleteYbSamePartButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(ActionEvent e) {
        deleteYbSamePartButton_actionPerformed(e);
      }
    });
    //CCEnd SS18
    
    parentPartJButton.setMaximumSize(new Dimension(99, 23));
    parentPartJButton.setMinimumSize(new Dimension(99, 23));
    parentPartJButton.setPreferredSize(new Dimension(99, 23));
    parentPartJButton.setHorizontalAlignment(SwingConstants.CENTER);
    parentPartJButton.setHorizontalTextPosition(SwingConstants.TRAILING);
    parentPartJButton.setText("搜索父件(R)");
    parentPartJButton.setEnabled(false);
    parentPartJButton.setMnemonic('R');
    parentPartJButton
        .addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        parentPartJButton_actionPerformed(e);
      }
    });

    buttonPanel.setDebugGraphicsOptions(0);
    buttonPanel.setMinimumSize(new Dimension(392, 30));
    buttonPanel.setPreferredSize(new Dimension(397, 30));
    buttonPanel.setInputVerifier(null);
    this.setMaximumSize(new Dimension(392, 285));
    this.setMinimumSize(new Dimension(392, 285));
    this.setPreferredSize(new Dimension(392, 285));
    qMMultiList.setDebugGraphicsOptions(0);
    qMMultiList.setMaximumSize(new Dimension(380, 240));
    qMMultiList.setInputVerifier(null);

//  CCBegin by leixiao 2008-11-12 原因：解放升级工艺路线,增加手动输入数量   
    qMMultiList.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        qMMultiList_componentResized(e);
      }
    });

    qMMultiList.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        qMMultiList_actionPerformed(e);
      }
    });
//  CCEnd by leixiao 2008-11-12 原因：解放升级工艺路线,增加手动输入数量 
    removeJButton.setMaximumSize(new Dimension(75, 23));
    removeJButton.setMinimumSize(new Dimension(75, 23));
    removeJButton.setPreferredSize(new Dimension(75, 23));
    removeJButton.setText("删除(O)");
    removeJButton.setMnemonic('O');
    removeJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        removeJButton_actionPerformed(e);
      }
    });
    jPanel1.setLayout(gridBagLayout3);
    upJButton.setMaximumSize(new Dimension(75, 23));
    upJButton.setMinimumSize(new Dimension(75, 23));
    upJButton.setPreferredSize(new Dimension(75, 23));
    upJButton.setMnemonic('U');
    upJButton.setText("上移(U)");
    upJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        upJButton_actionPerformed(e);
      }
    });

    downJButton.setMaximumSize(new Dimension(75, 23));
    downJButton.setMinimumSize(new Dimension(75, 23));
    downJButton.setPreferredSize(new Dimension(75, 23));
    downJButton.setMnemonic('D');
    downJButton.setText("下移(D)");
    downJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        downJButton_actionPerformed(e);
      }
    });
//  CCBegin by leixiao 2008-11-12 原因：解放升级工艺路线,界面重新调整
  /*  this.add(buttonPanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                                                 GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(
        2, 10, 0, 10), 0, 0));
    buttonPanel.add(parentPartJButton, new GridBagConstraints(0, 0, 1, 1,
        0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(16, 15, 12, 0), 0, 0));
    buttonPanel.add(addStructJButton, new GridBagConstraints(1, 0, 1, 1,
        0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(16, 8, 12, 0), 0, 0));
    buttonPanel.add(addJButton, new GridBagConstraints(2, 0, 1, 1, 0.0,
        0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(16, 8, 12, 0), 0, 0));
    this.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                                                 GridBagConstraints.WEST,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(8,
        5, 2, 0), 251, 240));
    this.add(jPanel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                             GridBagConstraints.CENTER,
                                             GridBagConstraints.NONE,
                                             new Insets(
        0, 0, 0, 0), 16, 148));
    jPanel1.add(removeJButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
        8, 0, 8, 0), 0, 0));
    jPanel1.add(upJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                                  GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(
        8, 0, 8, 0), 0, 0));
    jPanel1.add(downJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
        GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
        8, 0, 8, 0), 0, 0));
        */

    //CCBegin by liunan 2012-05-21 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
    manufacturingCheckBox.setFont(new java.awt.Font("SansSerif", 0, 12));
    manufacturingCheckBox.setText("艺毕添加生产件");
    manufacturingCheckBox.setSelected(false);
    manufacturingCheckBox.setVisible(false);
    //CCEnd by liunan 2012-05-21
    
    //CCBegin SS1
    addLastRouteCheckBox.setFont(new java.awt.Font("SansSerif", 0, 12));
    addLastRouteCheckBox.setText("添加最新路线");
    addLastRouteCheckBox.setSelected(true);
    addLastRouteCheckBox.setVisible(true);
    //CCEnd SS1
    //CCBegin Ss6
	saveAs.setFont(new java.awt.Font("SansSerif", 0, 12));
	saveAs.setText("另存为路线");
	saveAs.setSelected(false);
	saveAs.setVisible(false);

    //CCEnd SS6   
    this.add(buttonPanel,           new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(20, 0, 20, 0), 0, 0));
    buttonPanel.add(addStructJButton,
                     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
    buttonPanel.add(addJButton,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
  //CCBegin by leixiao 2011-1-12 原因：增加"按结构添加按钮"按钮 
    buttonPanel.add(structButton,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
  //CCEnd by leixiao 2011-1-12 原因：增加"按结构添加按钮"按钮 
    buttonPanel.add(adoptNoticeButton,
                     new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
    //CCBegin by leixiao 2010-11-30 原因：增加"按发布令号添加"按钮 
    buttonPanel.add(publishButton,
            new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
   ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
  //CCEnd by leixiao 2010-11-30 原因：增加"按发布令号添加"按钮 
    buttonPanel.add(newButton,   new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    buttonPanel.add(jsButton,   new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    buttonPanel.add(routelistButton,   new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    //CCBegin SS2
    buttonPanel.add(addMultPartsJButton1,   new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    //CCEnd SS2
    //CCBegin SS10
    buttonPanel.add(addAssemblyListPartsJButton,   new GridBagConstraints(9, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    //CCEnd SS10
    this.add(qMMultiList,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(8, 5, 2, 0), 0, 0));
    this.add(jPanel1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                             GridBagConstraints.CENTER,
                                             GridBagConstraints.NONE,
                                             new Insets(
        0, 0, 0, 0), 16, 148));
    jPanel1.add(removeJButton,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));
    jPanel1.add(upJButton,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));
    jPanel1.add(downJButton,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));
            
    //CCBegin by liunan 2012-05-21 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
    jPanel1.add(manufacturingCheckBox,   new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));
    //CCEnd by liunan 2012-05-21
    
    //CCBegin SS1
    jPanel1.add(addLastRouteCheckBox,   new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));    
    //CCEnd SS1
//CCBegin SS6
    jPanel1.add(saveAs,   new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));   
    //CCEnd SS6
    
    //CCBegin SS17
    jPanel1.add(setCountButton,   new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));    
    //CCEnd SS17
    //CCBegin SS18
    jPanel1.add(deleteYbSamePartButton,   new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 8, 0), 0, 0));    
    //CCEnd SS18
    
//  CCEnd by leixiao 2008-11-12 原因：解放升级工艺路线,界面重新调整
//  CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    //CCBegin SS5
//    qMMultiList.setHeadings(new String[] {"id", "编号", "名称", "父件编号", "路线状态",
 //                           "键值", "routeID", "数量", "linkID", "partid", "版本", "状态"}); //"键值"quxg add for:路线表中添加多个相同零部件（<＝3）,用于标识，aaa，aaa1，aaa2
    qMMultiList.setHeadings(new String[] {"id", "编号", "名称", "父件编号", "路线状态",
            "键值", "routeID", "数量", "linkID", "partid", "版本", "状态","颜色件标识"});

  //  qMMultiList.setRelColWidth(new int[] {0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1});
    qMMultiList.setRelColWidth(new int[] {0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1,1});

    //CCBegin SS8
    //qMMultiList.setCheckModel(true);
    //CCEnd SS8
 
//  qMMultiList.setCellEditable(false);
    //qMMultiList.setColsEnabled(new int[]{7},true); 
    qMMultiList.setColsEnabled(new int[]{7,12},true); 
//  CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
    //CCEnd SS5
    qMMultiList.setMultipleMode(false); //linshi
    qMMultiList.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        setParentPartJButtonEnabled();
      }
    });    
  }

  /**
   * 设置搜索父件按钮的状态
   */
  private void setParentPartJButtonEnabled() {
    if (qMMultiList.getSelectedRows() == null) {
      parentPartJButton.setEnabled(false);
    }
    else {
      parentPartJButton.setEnabled(true);
    }
  }

  private Vector sortLinks(Vector v) {

    Vector indexs = routeListInfo.getPartIndex();
    if (verbose) {
      System.out.println("排序前 v= " + v + " 排序indexs = " + indexs);
      System.out.println("排序前 v= " + v.size() + " 排序indexs = " + indexs.size());
    }
    if (indexs == null || indexs.size() == 0) {
       // System.out.println("排序集合为空");
      return v;
    }
    Vector result = new Vector();
    String partid;
    String partNum;
    String[] index;
    for (int i = 0; i < indexs.size(); i++) {
      index = (String[]) indexs.elementAt(i);

      partid = index[0];
      partNum = index[1];
      //System.out.println(partid+"=="+partNum);
      ListRoutePartLinkIfc link;
      for (int j = 0; j < v.size(); j++) {
        link = (ListRoutePartLinkIfc) v.elementAt(j);
        //System.out.println(""+link.getRightBsoID()+"      "+link.getParentPartNum());
        if (link.getRightBsoID().equals(partid)) {
          if ( (partNum == null || partNum.trim().equals("")) &&
              (link.getParentPartNum() == null ||
               link.getParentPartNum().trim().equals(""))) {
            //System.out.println("111"+link.getRightBsoID());
            result.add(link);
            v.remove(link);
            break;
          }
          if (partNum != null && link.getParentPartNum() != null
              && partNum.equals(link.getParentPartNum())) {
              	//System.out.println("222"+link.getRightBsoID());
            result.add(link);
            v.remove(link);
            break;
          }
        }
      }
    }
    if (verbose) {
      System.out.println("排序后 result= " + result);
    }
    return result;
  }

  /**
   * 设置路线表值对象，来填充工艺路线表的零部件列表
   *
   * @param routelist
   *            路线表值对象
   * @roseuid 4031B9EC0039
   */
  public void setTechnicsRouteList(TechnicsRouteListIfc routelist) {
     //System.out.println("setTechnicsRouteList 614 routelist " + routelist);
    qMMultiList.clear();
    //清楚上一次缓存的内容
    allParts.clear();
    addedParts.clear();
    deleteParts.clear();
    routeListInfo = routelist;
    theProductifc = filterPart(routelist.getProductMasterID());
    // System.out.println("theProductifc 经过filterPart过滤 得到值 " + theProductifc);
    //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线,因为历史件打不开，先去掉
    HashMap countMap = new HashMap();
    Vector indexVector = routelist.getPartIndex();
    //CCBegin SS13
    boolean routestateflag = false;
    //CCEnd SS13
    //System.out.println(">>>> routelist = "+routelist.getBsoID());
    //System.out.println("indexVector===="+indexVector.size());
    if(indexVector!=null && indexVector.size()>0)
    {
      int size2= indexVector.size();
      for(int k=0;k<size2;k++)
      {
        String[] ids = (String[])indexVector.elementAt(k);
        String key = "";
        //CCBegin SS13
        /*if(countMap.containsKey(ids[0]))
        {
             key = ids[0]+"K"+k;
        }
        else
        {
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
        //System.out.println(key+"=======save partindex========"+ids[2]);
        //CCEnd SS13
        
    //    System.out.println("key = "+key+".........count = "+ids.length+" 1="+ids[0]+" 2="+ids[1]);
//      CCBegin by leixiao 2008-11-5 原因：解放升级工艺路线 ，部分历史件没有ids[2]，这样导致打不开此件  
       if(ids.length>2)
//    	 CCEnd by leixiao 2008-11-5 原因：解放升级工艺路线
        countMap.put(key,ids[2]);   //关联零件有重复的可能，需要加以区别
      }
    }
    //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
    Vector vold = this.getRouteListPartLinks();
    System.out.println(routeListInfo.getRouteListNumber()+":indexVector===="+indexVector.size()+"   and PartLinks===="+vold.size());
    Vector v = sortLinks(vold);
    //System.out.println("v===="+v.size());
    //当前路线表关联的零部件主信息添加入列表
    //把当前路线表关联的零部件主信息缓存
    //CCBegin SS18
    samepartstr = "";
    //CCEnd SS18
    if (v != null && v.size() > 0) {
      for (int i = 0; i < v.size(); i++) {
        ListRoutePartLinkIfc link = (ListRoutePartLinkIfc) v
            .elementAt(i);
        QMPartMasterInfo info = (QMPartMasterInfo) link
            .getPartMasterInfo();
//      CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        
        // String  partid = (String)link.getPartBranchID();
         QMPartIfc part=link.getPartBranchInfo();
         String partid=null;
         String version="";
         String partstate="";
         //CCBegin SS5   
         boolean flag=false;
         if(link.getColorFlag()!=null&&!link.getColorFlag().equals("")){
        	 if(link.getColorFlag().equals("1")){
        		 flag=true;
        	 }     	 
         }     
         //CCEnd SS5
         if(part!=null){
          partid=part.getBsoID();
        version=part.getVersionValue();
        partstate=part.getLifeCycleState().getDisplay();
       //  System.out.println(""+part+" "+partid+"    "+version);
         }
//       CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线

         String coding;
         if (link.getRouteID() == null || link.getRouteID().length() < 1) {
           coding = "无";
         }
         else {
           coding = getCodingDesc(link.getRouteID());
         }
         //CCBegin SS18
         //String finalKey = addPartTohashmap(info,partid,"",allParts);
         String finalKey = addPartTohashmap111(info,partid,"",allParts);
         //CCEnd SS18
         //System.out.println("QMPartMaster = "+info.getBsoID()+" COUNT = "+link.getCount());
         //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
        qMMultiList.addTextCell(i, 0, info.getBsoID());
        qMMultiList.addTextCell(i, 1, info.getPartNumber());
        qMMultiList.addTextCell(i, 2, info.getPartName());
        //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        qMMultiList.addTextCell(i, 3, theProductifc.getPartNumber());
        qMMultiList.addTextCell(i, 4, coding);
        qMMultiList.addTextCell(i, 5, finalKey);
        qMMultiList.addTextCell(i, 6, link.getRouteID());
        //qMMultiList.addTextCell(i, 7, Integer.toString(link.getCount())); //liuming 20070523 add
        qMMultiList.addTextCell(i, 8, link.getBsoID()); //liuming 20070523 add
//      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
        qMMultiList.addTextCell(i, 9, partid);
        qMMultiList.addTextCell(i, 10, version);
        qMMultiList.addTextCell(i, 11, partstate);
//      CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
        //CCbegin SS5
        qMMultiList.addCheckboxCell(i, 12, flag);
        //CCEnd SS5
//////////////////////////////////////////////////////////////////////////////////
//      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线
        String countInProduct = "";
        String masterID = info.getBsoID();
        //CCBegin SS13
        if(routestateflag)
        {
        	masterID = masterID + coding;
        }
        //System.out.println("masterID========="+masterID);
        //CCEnd SS13
        if(countMap.containsKey(masterID))
        {
          countInProduct = countMap.get(masterID).toString();
          countMap.remove(masterID);
        }
        else
        {
            for(Iterator jt= countMap.keySet().iterator();jt.hasNext();)
            {
              String keya = jt.next().toString();
              if(keya.startsWith(masterID))
              {
                masterID = keya;
                countInProduct = countMap.get(masterID).toString();
                countMap.remove(masterID);
                break;
              }
            }
        }
        qMMultiList.addTextCell(i, 7, countInProduct);
      }
//    CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，
    }
    //CCBegin SS18
    if (!samepartstr.equals(""))
    {
    	JOptionPane.showMessageDialog(null, "不能添加相同的零部件！"+samepartstr, "错误！", JOptionPane.WARNING_MESSAGE);
    }
    samepartstr = "";
    //CCEnd SS18
    
    isChangeRouteList = true;
  }
  //CCBegin SS7
 /**
  * 初始化
  */
  public void setPartLinkJPanel(){
	  adoptNoticeButton.setText("更改通知书");
	  publishButton.setText("采用通知书");
	  saveAs.setVisible(true);
  }
  //CCEnd SS7
  /**
   * 获得路线表值对象
   *
   * @return 路线表值对象
   */
  public TechnicsRouteListIfc getTechnicsRouteList() {
    return routeListInfo;
  }

  /**
   * 调用服务，获得当前路线表的所有零部件关联
   *
   * @return 零部件关联的集合
   * @roseuid 4033530D01BC
   */
  private Vector getRouteListPartLinks() {
    Vector nv = null;
    if (routeListInfo != null) {
      Class[] c = {
          TechnicsRouteListIfc.class};
      Object[] objs = {
          routeListInfo};
      try {
        nv = (Vector) useServiceMethod("TechnicsRouteService",
                                       "getRouteListLinkParts", c, objs);
      }
      catch (QMRemoteException ex) {
        JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                      QMMessage.getLocalizedMessage(RESOURCE,
            "information",
            null), JOptionPane.INFORMATION_MESSAGE);
      }
    }
    return nv;
  }

  /**
   * 应用零部件普通搜索，添加零部件
   *
   * @roseuid 4031BC2701B7
   */
  private void addPart() {
    //定义搜索器
	  //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线 搜索具体版本
	    QmChooser qmChooser = new QmChooser("QMPart", "搜索零部件", this
                .getParentJFrame());
	    //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线

//    qmChooser.setChildQuery(false);
    qmChooser.setChildQuery(true);
    //按照给定条件，执行搜索
    qmChooser.addListener(new QMQueryListener() {
      public void queryEvent(QMQueryEvent e) {
        qmChooser_queryEvent(e);
      }
    });
    qmChooser.setVisible(true);
  }

  /**
   * 添加零部件的父件编号 added by skybird 2005.3.4
   * @return
   */
  private void parentPart() {
    int index = qMMultiList.getSelectedRow();
    String thePartBsoid = null;
    String oldParentNum = null;
    setSelectedNum(index);
    if (index != -1) {
      thePartBsoid = qMMultiList.getCellAt(index, 0).toString();
      oldParentNum = qMMultiList.getCellAt(index, 3).toString();
    }
    else {
      JOptionPane.showMessageDialog(this.getParentJFrame(), "请选择零件",
                                    "提示", JOptionPane.WARNING_MESSAGE);
      return;
    }
    ParentPartJDialog parentPartJDialog = new ParentPartJDialog(this
        .getParentJFrame());
    parentPartJDialog.setPartNum(thePartBsoid);
    parentPartJDialog.setTechnicsRouteList(this.getTechnicsRouteList());
    parentPartJDialog.setSize(650, 500);
    //this.isChangeRouteList = false;
    parentPartJDialog.setVisible(true);
    if (ParentPartJDialog.isSave) {
      QMPartIfc theParentPart = parentPartJDialog.getSelectedParentPart();
      if (theParentPart == null) {
        return;
      }
      if (this.allParts.containsKey(thePartBsoid
                                    + theParentPart.getPartNumber())) {
        return;
      }
      qMMultiList.addTextCell(theSelectedNum, 3, theParentPart
                              .getPartNumber());
      qMMultiList.addTextCell(theSelectedNum, 4, theParentPart
                              .getPartName());
      //CCBegin SS5
      
      boolean colorFlag=(Boolean)qMMultiList.getCellAt(theSelectedNum, 12).getValue();
      String flag="";
      if(colorFlag)
      {
    	  flag="1";
      }else
      {
    	  flag="0";
      }
      //CCEnd SS5
      String partMasterID = qMMultiList.getCellAt(theSelectedNum, 0)
          .toString();
      int count = 0;
      try {
        count = this.calCountInProduct(filterPart(partMasterID),
                                       theParentPart, this.theProductifc);
      }
      catch (QMRemoteException ex) {
        JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                      "异常信息", JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      qMMultiList.addTextCell(theSelectedNum, 5, new Integer(count)
                              .toString());
      //这里要计算数量
      //当通过福建列表改变零部件表界面的列表中零部件的父件编号的时候调用
      // changeAddedParts(theSelectedNum, theParentPart);
      this.deleteParts.put(thePartBsoid + oldParentNum, this.allParts
                           .get(thePartBsoid + oldParentNum));
      this.addedParts.remove(thePartBsoid + oldParentNum);
      this.addedParts.put(thePartBsoid + theParentPart.getPartNumber(),
                          new Object[] {thePartBsoid, theParentPart,
                          new Integer(count)});


      this.allParts.remove(thePartBsoid + oldParentNum);
      this.allParts.put(thePartBsoid + theParentPart.getPartNumber(),
                        new Object[] {thePartBsoid, theParentPart});

      //记录要更改父件编号的原来部件的结合
      // changePartsToChange(theSelectedNum, theParentPart);
    }
  }
  //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线 
  private String getCodingDesc(String routeID) {
	    String code = null;
	    if (routeID != null) {
	      Class[] c = {
	          String.class};
	      Object[] objs = {
	          routeID};
	      try {
	        code = (String)this.useServiceMethod("TechnicsRouteService",
	                                             "getRouteCodeDesc", c, objs);
	      }
	      catch (QMRemoteException ex) {
	        JOptionPane.showMessageDialog(this, ex.getClientMessage(),
	                                      QMMessage.getLocalizedMessage(RESOURCE,
	            "information",
	            null), JOptionPane.INFORMATION_MESSAGE);
	      }
	    }
	    return code;

	  }
  //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线


  /**
   * 当通过搜索父件按钮来更改零部件表界面的列表中零部件的父件编号的时候 改变addedParts里面响应的内容
   *
   * @param e
   */
  /*
   * public void changeAddedParts(int theSelectedNum, QMPartIfc theParentPart) {
   * String thePartBsoid = qMMultiList.getCellAt(theSelectedNum,
   * 0).toString(); if (this.addedParts != null) { int loop =
   * addedParts.size(); for (int i = 0; i < loop; i++) { Object[] tmp =
   * (Object[]) addedParts.elementAt(i); if (tmp[0].equals(thePartBsoid)) {
   * addedParts.remove(i); addedParts.add(new Object[] {thePartBsoid,
   * theParentPart}); } } } }
   */

  /**
   * 父件标号欲被修改的原零部件的集合
   *
   * @param e
   */
  /*
   * private void changePartsToChange(int theSelectedNum, QMPartIfc
   * theParentPart) { String thePartBsoid =
   * qMMultiList.getCellAt(theSelectedNum, 0).toString(); if
   * (this.containsPart(this.addedParts,
   * thePartBsoid,theParentPart.getPartNumber())) { return; } else {
   * partsToChange.addElement(new Object[] {thePartBsoid, theParentPart}); }
   * whenDelete(this.partsToChange, this.deleteParts); }
   */


  /**
   * 当路线表已有零部件被删除时
   * @param arg1 Vector
   * @param arg2 Vector
   */
  /*private void whenDelete(Vector arg1, Vector arg2) {
    int loop1 = arg1.size();
    int loop2 = arg2.size();
    for (int i = 0; i < loop1; i++) {
      Object[] tmp = (Object[]) arg1.elementAt(i);
      String partBsoid = (String) tmp[0];
      boolean flag1 = false;
      for (int j = 0; j < loop2; j++) {
        Object[] tmp1 = (Object[]) arg2.elementAt(j);
        String partBsoidToCompare = (String) tmp1[0];
        if (partBsoid.equals(partBsoidToCompare)) {
          flag1 = true;
        }
      }
      if (flag1) {
        arg1.remove(i);
      }
    }
  }*/

  /**
   * 是否是产品的子件 开发组4月性能测试，优化
   * @param psVec Vector
   * @return Collection
   */
  private Collection isSubInProduct(Vector psVec) {
    //System.out.println(" zz 写的 isSubInProduct"+theProductifc.getPartNumber());
    if (theProductifc == null) {
      return null;
    }
    Class[] classes = {
        Vector.class, QMPartIfc.class};
    Object[] objs = {
        psVec, theProductifc};
    try {
      return (Collection) CappRouteAction.useServiceMethod(
          "TechnicsRouteService",
          "isParentPart", classes, objs);
    }
    catch (QMRemoteException ex) {
      String title = QMMessage.getLocalizedMessage(RESOURCE, "notice",
          null);
      JOptionPane.showMessageDialog(this.getParentJFrame(), ex
                                    .getClientMessage(), title,
                                    JOptionPane.WARNING_MESSAGE);
      return null;
    }

  }

  /**
   * 是否是产品的子件
   * @param partMaster
   * @return
   */
  /*private boolean isSubInProduct(QMPartMasterIfc partMaster) {
    //System.out.println(" 844进入 是否是产品的子件原方法 zz");
    //String productID = this.getTechnicsRouteList().getProductMasterID();
    //QMPartIfc theProductifc = this.filterPart(productID);
    if (theProductifc == null) {
      return false;
    }
    QMPartIfc part = filterPart(partMaster.getBsoID());
    Class[] classes = {
        QMPartIfc.class, QMPartIfc.class};
    Object[] objs = {
        part, theProductifc};
    try {
      return ( (Boolean) CappRouteAction.useServiceMethod(
          "StandardPartService", "isParentPart", classes, objs))
          .booleanValue();
    }
    catch (QMRemoteException ex) {
      String title = QMMessage.getLocalizedMessage(RESOURCE, "notice",
          null);
      JOptionPane.showMessageDialog(this.getParentJFrame(), ex
                                    .getClientMessage(), title,
                                    JOptionPane.WARNING_MESSAGE);
      return false;
    }
  }*/

  //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
  public void qmChooser_queryEvent(QMQueryEvent e) {
	    if (verbose) {
	      System.out
	          .println(
	          "capproute.view.RouteListPartLinkJPanel:qmChooser_queryEvent(e) begin...");
	    }

	    if (e.getType().equals(QMQueryEvent.COMMAND)) {
	      if (e.getCommand().equals(QmQuery.OkCMD)) {
	        //按照所给条件，搜索获得所需零部件
	        QmChooser c = (QmChooser) e.getSource();
	        
	        BaseValueIfc[] bvi = c.getSelectedDetails();
	        Vector v = new Vector();
	        for (int i = 0; i < bvi.length; i++) {
//	          CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	          QMPartIfc newPart = (QMPartIfc) bvi[i];
	          QMPartMasterIfc partmaster= (QMPartMasterIfc)newPart.getMaster();
	          //System.out.println("  partmaster="+partmaster+" newPart= "+newPart);
              Object []part={partmaster,newPart};
//            CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	          v.add(part);
	        }
	        addPartsList(v);
	      }
	    }
	    if (verbose) {
	      System.out
	          .println("capproute.view.RouteListPartLinkJPanel:CappChooser_queryEvent(e) end...return is void");
	    }
	  }
  
  private void addPartsList(Vector partsVec) {
	    //System.out.println("addPartsList!!!!!!!!!");
	    for (int i = 0; i < partsVec.size(); i++) {
	    	
	    	Object [] part=(Object[])partsVec.elementAt(i);
//          CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	    	QMPartMasterIfc subMaster=(QMPartMasterIfc) part[0];
	    	QMPartIfc partifc=(QMPartIfc) part[1];
	    	//CCBegin by leixiao 2011-1-12 原因：解放新需求,生产或生准状态才编艺准,生准状态编艺毕	    	
//	    	//如果是生准状态,或者生产状态非艺毕的可以编路线
	    	System.out.println("------"+partifc.getLifeCycleState()+"--"+routeListInfo.getRouteListState());
	    	//CCBegin by liunan 2011-05-18 新增 前准 和 已取消 支持。
	    	//if(partifc.getLifeCycleState().toString().equals("PREPARING")||(!routeListInfo.getRouteListState().equals("艺毕")&&partifc.getLifeCycleState().toString().equals("MANUFACTURING"))){
	    	if(partifc.getLifeCycleState().toString().equals("PREPARING")
	    	   ||partifc.getLifeCycleState().toString().equals("ADVANCEPREPARE")
	    	   ||partifc.getLifeCycleState().toString().equals("CANCELLED")
	    	   //CCBegin SS3
	    	   ||partifc.getLifeCycleState().toString().equals("BSXTrialYield")
	    	   //CCEnd SS3
	    	   //CCBegin SS12
	    	   //||partifc.getLifeCycleState().toString().equals("SHIZHI")
	    	   //CCEnd SS12
	    	   //CCBegin SS21
	    	   ||(routeListInfo.getRouteListState().equals("试制")&&(partifc.getLifeCycleState().toString().equals("SHIZHI")))
	    	   //CCEnd SS21
	    	   //CCBegin SS15 SS19
	    	   ||(routeListInfo.getRouteListState().equals("艺废")&&(partifc.getLifeCycleState().toString().equals("BSXDisused")||partifc.getLifeCycleState().toString().equals("DISAFFIRM")))
	    	   //CCEnd SS15  SS19
	    	   //CCBegin SS15 SS20
	    	   ||(routeListInfo.getRouteListState().equals("临准")&&(partifc.getLifeCycleState().toString().equals("LINZHUN")))
	    	   //CCEnd SS15 SS20
	    	   //CCBegin SS11
	    	   ||partifc.getLifeCycleState().toString().equals("BSXTrialProduce")||partifc.getLifeCycleState().toString().equals("BSXYield")
	    	   //CCEnd SS11
	    	   //CCBegin by liunan 2012-05-21 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
	    	   ||(routeListInfo.getRouteListState().equals("艺毕")&&manufacturingCheckBox.isSelected()&&partifc.getLifeCycleState().toString().equals("MANUFACTURING"))
	    	   //CCEnd by liunan 2012-05-21
	    	   ||(!routeListInfo.getRouteListState().equals("艺毕")&&partifc.getLifeCycleState().toString().equals("MANUFACTURING"))){
	    	//CCEnd by liunan 2011-05-18
	    		//CCEnd by leixiao 2011-1-12 原因：解放新需求
	    		//          CCBegin by leixiao 2009-3-26 原因：解放升级工艺路线，为艺毕添加，按艺准添加路线

	    	String count="";
	    	String routeid="";
	    	if(part.length>3){
	    		count=(String)part[2];	    	
	    	    routeid=(String)part[3];
	    	}
//          CCEnd by leixiao 2009-3-26 原因：解放升级工艺路线，记录当前零件id
	    	//System.out.println("-----subMaster="+subMaster+" partid="+partifc);
	     // QMPartMasterIfc subMaster = (QMPartMasterIfc) partsVec.elementAt(i);
//          CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id	
	      String subID = subMaster.getBsoID();
	      String partid=partifc.getBsoID();
	      if (this.theProductifc == null) {
	        theProductifc = this.filterPart(routeListInfo
	                                        .getProductMasterID());
	      }
//        CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	      String finalkey = addPartTohashmap(subMaster,partid,routeid,allParts);
	      System.out.println("finalkeyfinalkeyfinalkey===="+finalkey);
//        CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id     
	      if (finalkey == null) {
	        return;
	      }
	      if (addPartTohashmap(subMaster,partid,routeid, addedParts) == null) {
	        return;
	      }
	      String partNumber = subMaster.getPartNumber();
	      String partName = subMaster.getPartName();
	      String parentPartNum = theProductifc.getPartNumber();
	      String partRouteStatus = "无";
//        CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	      String partversion=partifc.getVersionValue();
	      String partstate=partifc.getLifeCycleState().getDisplay();
	      
	      String[] listInfo = {
	          subID, partNumber, partName,
	          parentPartNum, partRouteStatus, finalkey, partid,partversion,partstate};
//        CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	      int row = qMMultiList.getNumberOfRows();
	      qMMultiList.addTextCell(row, 0, listInfo[0]);
	      qMMultiList.addTextCell(row, 1, listInfo[1]);
	      qMMultiList.addTextCell(row, 2, listInfo[2]);
	      qMMultiList.addTextCell(row, 3, listInfo[3]);
	      qMMultiList.addTextCell(row, 4, listInfo[4]);
	      qMMultiList.addTextCell(row, 5, listInfo[5]); 	      
	      qMMultiList.addTextCell(row, 6, routeid);
	      qMMultiList.addTextCell(row, 7, count);
	      qMMultiList.addTextCell(row, 8, "");
//        CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id 
	      qMMultiList.addTextCell(row, 9, listInfo[6]);
	      qMMultiList.addTextCell(row, 10, listInfo[7]);
	      qMMultiList.addTextCell(row, 11, listInfo[8]); 
//        CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	      //CCBegin SS5	      
	      qMMultiList.setCheckboxSelected(row, 12, false);
	      qMMultiList.setCheckboxEditable(true);
	      //CCEnd SS5
	    }//ifend艺准才做工艺
	    }

	  }
  //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
  /**
   * 搜索零部件监听事件方法
   * @param e  搜索监听事件
   */
  public void qmChooser_queryEventcp(QMQueryEvent e) {
    if (verbose) {
      System.out.println(
          "capproute.view.RouteListPartLinkJPanel:qmChooser_queryEvent(e) begin...");
    }
    if (e.getType().equals(QMQueryEvent.COMMAND)) {
      if (e.getCommand().equals(QmQuery.OkCMD)) {
        //按照所给条件，搜索获得所需零部件
        QmChooser c = (QmChooser) e.getSource();
        BaseValueIfc[] bvi = c.getSelectedDetails();
        if (bvi != null) {
         //add by guoxl 20080218
          Vector moreVec=new Vector();
          Vector lessVec=new Vector();
        //add by guoxl end

          Vector v = new Vector();
          Vector ps = new Vector();
          Vector nonMasters = new Vector();
          Vector vec = new Vector(bvi.length);
          //add by guoxl on 20080218
          QMPartMasterIfc partMasterIfc=null;
          //add by guoxl end
          for (int j = 0; j < bvi.length; j++) {
          //add by guoxl on 20080218
            partMasterIfc=(QMPartMasterIfc)bvi[j];
            moreVec.addElement(partMasterIfc.getPartNumber());
          //add by guoxl end

            vec.add(bvi[j]);
          }
          // zz start 20061110二级路现根据部门过滤零部件 ,条件满足的零件接着做配置规范过滤 如果所有的零件都不满足的直接返回
          if (vec != null &&
              routeListInfo.getRouteListLevel().equals(RouteListLevelType.
              SENCONDROUTE.getDisplay())) {
            Collection viatheDepart = null;

            Class[] cla = {
                String.class, Vector.class};
            Object[] obj = {
                routeListInfo.getRouteListDepartment(), vec};
            try {
              viatheDepart = (Collection) CappRouteAction.useServiceMethod(
                  "TechnicsRouteService", "getOptionPart", cla, obj);

            }
            catch (QMRemoteException ex) {
              JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                            QMMessage.getLocalizedMessage(
                  RESOURCE, "exception", null),
                                            JOptionPane.INFORMATION_MESSAGE);
            }

          if(viatheDepart==null||viatheDepart.size()==0){

            //modify by guoxl on 20080218(二级路线没找符合的零部件，弹出提示信息)
            for(int i=0;i<v.size();i++)
         {
           Object[] array=(Object[])v.elementAt(i);
           partMasterIfc=(QMPartMasterIfc)array[0];
           lessVec.addElement(partMasterIfc.getPartNumber());
         }

         if (moreVec.size() > lessVec.size()) {

           for (int i = 0; i < lessVec.size(); i++) {

             if (moreVec.contains(lessVec.elementAt(i))) {

               moreVec.remove(lessVec.elementAt(i));

             }

           }
           JOptionPane.showMessageDialog(this, "" + moreVec.toString() + "零部件不是该路线单位的路线件!", "提示信息",
                                         JOptionPane.INFORMATION_MESSAGE);
         }

          //modify by guoxl end
            return;

          }
          else
            vec = (Vector) viatheDepart;

          }

          // zz end 20061110二级路现根据部门过滤零部件
         Collection colection = filterPart(vec);
           //System.out.println(" 经过新filterPart方法的 collection====== " + colection.size());
          if (colection.size() == 0) {
            JOptionPane.showMessageDialog(this, "没有符合标准的零部件", "提示信息",
                                          JOptionPane.INFORMATION_MESSAGE);
            return;

          }
          Vector vector = new Vector(colection);
          //System.out.println("vector========"+vector);
          for (int ii = 0, jj = vector.size(); ii < jj; ii++) {
            Object[] obj = (Object[]) vector.get(ii);
            if (obj[0] == null) { // 没有master的part
              nonMasters.add( (QMPartMasterIfc) bvi[ii]);
            }
            else { //ps
              //System.out.println("obj[0] obj[0] obj[0] " + obj[0]);
              ps.add( (QMPartIfc) obj[0]);
            }
          }

          HashMap map = null;
          try {
            map = this.calCountInProduct(ps, this.theProductifc,
                                         this.theProductifc);
          }
          catch (QMRemoteException ex1) {
            JOptionPane.showMessageDialog(this, ex1
                                         .getClientMessage(), "异常信息",
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
          }
          Collection isCollection = this.isSubInProduct(ps);
          // System.out.println("isSubInProduct 得到的 collection  " + isCollection);
          Vector isornot = null;
          //zz  start
          if (isCollection == null) {
            for (int i = 0; i < ps.size(); i++) {
              QMPartIfc parent = null;
              String parentNum = null;
              Integer count = null;
              
              //所选择的某一零部件主信息
              QMPartIfc pi = (QMPartIfc) ps.elementAt(i);
              QMPartMasterIfc newPart = (QMPartMasterIfc) pi.getMaster();

              if (!allParts.containsKey(newPart.getBsoID()
                                        + parentNum)) {

                //这里应该加以处理
                v.add(new Object[] {newPart, parent});
                allParts
                    .put(newPart.getBsoID() + parentNum,
                         new Object[] {newPart.getBsoID(),
                         parent});
           
                addedParts.put(newPart.getBsoID() + parentNum,
                               new Object[] {newPart.getBsoID(), parent,
                               count});
    
      
         
              }
            }
          }
          // zz end
          else {
            isornot = new Vector(isCollection);
            for (int i = 0; i < ps.size(); i++) {
              QMPartIfc parent = null;
              String parentNum = null;
              Integer count = null;
              //所选择的某一零部件主信息
              QMPartIfc pi = (QMPartIfc) ps.elementAt(i);
              QMPartMasterIfc newPart = (QMPartMasterIfc) pi.getMaster();

              if ( ( (Boolean) isornot.elementAt(i)).equals(new Boolean(true))) {
                parent = theProductifc;
                parentNum = theProductifc.getPartNumber();
                count = (Integer) map.get(pi.getBsoID());

              }

              if ( ( (Boolean) isornot.elementAt(i)).equals(new Boolean(false))) {
                parent = null;

                count = null;

              }

        

              if (!allParts.containsKey(newPart.getBsoID()
                                        + parentNum)) {

                //这里应该加以处理
                v.add(new Object[] {newPart, parent});
                allParts
                    .put(newPart.getBsoID() + parentNum,
                         new Object[] {newPart.getBsoID(),
                         parent});
     
              addedParts.put(newPart.getBsoID() + parentNum,
                             new Object[] {newPart.getBsoID(), parent,
                             count});
  
              }
            }
          }

          this.addPartsToList(v, map, ps);


          for (int i = 0; i < nonMasters.size(); i++) {
            QMPartMasterInfo newPart = (QMPartMasterInfo) nonMasters.elementAt(
                i);
            Object[] obj2 = {
                ( (QMPartMasterInfo) newPart)
                .getPartNumber()};
            String s = QMMessage.getLocalizedMessage(RESOURCE,
                "55", obj2, null);
            String title = QMMessage.getLocalizedMessage(
                RESOURCE, "notice", null);
            JOptionPane.showMessageDialog(this
                                          .getParentJFrame(), s, title,
                                          JOptionPane.WARNING_MESSAGE);

          }
        }
      }
    }
    if (verbose) {
      System.out
          .println("capproute.view.RouteListPartLinkJPanel:CappChooser_queryEvent(e) end...return is void");
    }
  }

  //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线 
//CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  private String addPartTohashmap(QMPartMasterIfc subMaster,String partid,String routeid,
          HashMap map) {
	//  System.out.println("--subMaster="+subMaster+" partid="+partid);
	  String iscomplete="n";
	 if( routeListInfo.getRouteListState().equals("艺毕")){
		 iscomplete="y";
	 }
		 
String subID = subMaster.getBsoID();
if (this.theProductifc == null) {
theProductifc = this.filterPart(routeListInfo.getProductMasterID());
}
String key = subID + theProductifc.getPartNumber();
if (!map.containsKey(key)) {
map.put(key, new Object[] {subID,theProductifc,partid,routeid,iscomplete});
}
else {
int i = 0;
//CCBegin by leixiao 2009-11-12
int p =0;
//CCEnd by leixiao 2009-11-12
//CCBegin SS16
String samepart = "";
//CCEnd SS16
for (Iterator iter = map.keySet().iterator(); iter.hasNext(); ) {
String key1 = iter.next().toString();
if (key1.startsWith(key)) {
	String partid1=(String)((Object[])map.get(key1))[2];
//	CCBegin by leixiao 2009-11-12
	p++;
//	CCEnd by leixiao 2009-11-12
	//System.out.println("partid1="+partid1+" partid="+partid);
//	CCBegin by leixiao 2009-12-29 历史件没有partid
	if(partid1==null)
		continue;
//	CCEnd by leixiao 2009-12-29
	if(partid1.equals(partid))
	//CCBegin SS16
	{
    i++;
    try
    {
    	QMPartIfc cpart = (QMPartIfc) CappTreeHelper.refreshInfo(partid);
      if(samepart.equals(""))
      {
    	  samepart = cpart.getPartNumber();
      }
      else
      {
      	if(samepart.indexOf(cpart.getPartNumber())!=-1)
      	{
      		samepart = samepart +"," +cpart.getPartNumber();
      	}
      }
    }
    catch(QMRemoteException qe)
    {
    	qe.printStackTrace();
    }
  }
  //CCEnd SS16
}
}

//CCBegin by liunan 2012-05-21 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
//if (iscomplete.equals("y")&&i >= 1) {
if (iscomplete.equals("y")&&i >= 1&&!manufacturingCheckBox.isSelected()) {
//CCEnd by liunan 2012-05-21
	JOptionPane.showMessageDialog(null,
	              "艺毕中不能添加相同的零部件！"+samepart,
	              "错误！",
	              JOptionPane.WARNING_MESSAGE);
	return null;
	}
else if (i >= 3) {
JOptionPane.showMessageDialog(null,
              "相同零部件，最多添加三个！"+samepart,
              "错误！",
              JOptionPane.WARNING_MESSAGE);
return null;   
}
else {
for (int j = 0; j < p; j++) {
key = key + 1;
}
map.put(key, new Object[] {subID,theProductifc, partid,routeid,iscomplete});
}
}
return key;
}
//CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
  //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
  
  //CCBegin SS18
  private String addPartTohashmap111(QMPartMasterIfc subMaster, String partid, String routeid, HashMap map)
  {
  	String iscomplete="n";
  	if( routeListInfo.getRouteListState().equals("艺毕"))
  	{
  		iscomplete="y";
  	}
  	String subID = subMaster.getBsoID();
  	if (this.theProductifc == null)
  	{
  		theProductifc = this.filterPart(routeListInfo.getProductMasterID());
  	}
  	String key = subID + theProductifc.getPartNumber();
  	if (!map.containsKey(key))
  	{
  		map.put(key, new Object[] {subID,theProductifc,partid,routeid,iscomplete});
  	}
  	else
  	{
  		int i=0;
  		int p =0;
  		for (Iterator iter = map.keySet().iterator(); iter.hasNext(); )
  		{
  			String key1 = iter.next().toString();
  			if (key1.startsWith(key))
  			{
  				String partid1=(String)((Object[])map.get(key1))[2];
  				p++;
  				if(partid1==null)
  				  continue;
  				if(partid1.equals(partid))
  				{
  					i++;
  				}
  			}
  		}
  		
  		if ((iscomplete.equals("y")&&i >= 1&&!manufacturingCheckBox.isSelected())||(i >= 3)) 
  		{
  			try
  			{
  				QMPartIfc cpart = (QMPartIfc) CappTreeHelper.refreshInfo(partid);
  				if(samepartstr.equals(""))
  				{
  					samepartstr = cpart.getPartNumber();
  				}
  				else
  				{
  					if(samepartstr.indexOf(cpart.getPartNumber())==-1)
  					{
  						samepartstr = samepartstr +"," +cpart.getPartNumber();
  					}
  				}
  			}
  			catch(QMRemoteException qe)
  			{
  				qe.printStackTrace();
  			}
  		}
  		
  		for (int j = 0; j < p; j++)
  		{
  			key = key + 1;
  		}
  		map.put(key, new Object[] {subID,theProductifc, partid,routeid,iscomplete});
  	}
  	return key;
  }
  //CCEnd SS18

  /**
   * 从产品结构中添加零部件。可以根据该零部件的最新产品结构获得一个零部件列表，作为工
   * 艺路线表中零部件表中备选零部件。如果当前编辑的工艺路线表是一级工艺路线表，则系统 应列出产品结构中的所有零部件；
   * 如果当前编辑的工艺路线表是二级工艺路线表，则系统应根据路线表的单位属性，列出产品 结构中所有一级路线有对应单位的零部件，作为备选零部件。
   *
   * @roseuid 4031BC410074
   */
  private void addConstructPart() {
    if (this.isChangeRouteList) {
    	
      selectPartDialog = new SelectPartJDialog(this.getParentJFrame());
      SelectPartJDialog.partTreePanel.getchangeConfig().clear();//CR2
      selectPartDialog.setRouteList(this.getTechnicsRouteList());
      this.isChangeRouteList = false;
    }
    selectPartDialog.setVisible(true);
    /**
     * if (selectPartDialog.isSave) { Vector v =
     * selectPartDialog.getSelectedParts(); if (v != null && v.size() > 0) {
     * Vector v2 = new Vector(); for (int i = 0; i < v.size(); i++) {
     * QMPartMasterInfo partinfo = (QMPartMasterInfo) v.elementAt(i); if
     * (!this.isContain(partinfo.getBsoID())) { //这里 v2.add(partinfo);
     * allParts.add(partinfo.getBsoID()); addedParts.add(new Object[]
     * {partinfo.getBsoID(), getProductNum()}); } else { Object[] obj2 = {
     * partinfo.getPartNumber()}; String s =
     * QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.ADD_RECYCLE,
     * obj2, null); String title = QMMessage.getLocalizedMessage(RESOURCE,
     * "notice", null);
     * JOptionPane.showMessageDialog(this.getParentJFrame(), s, title,
     * JOptionPane.WARNING_MESSAGE); } } this.addPartsToList(v2);
     * System.out.println("to addpartstolist"); } }
     */

  }

  /**
   * 把所有符合条件的零部件主信息添加入列表中
   *
   * @param parts
   *            零部件主信息值对象集合
   */
  private void addPartsToList(Vector parts, Map map, Vector ps) {
    if (parts != null && parts.size() > 0) {
      Object[] objs = parts.toArray();
      //  String as[] = new String[parts.size()];
      /* Vector ps = new Vector();
       for (int i = 0; i < parts.size(); i++) {
         Object[] partAndParent = (Object[]) objs[i];
         QMPartMasterIfc partMaster = (QMPartMasterIfc) partAndParent[0];
         ps.add(filterPart(partMaster.getBsoID()));
       }
       HashMap map = null;
       try {
         map = this.calCountInProduct(ps, this.theProductifc,
                                      this.theProductifc);
       }
       catch (QMRemoteException ex1) {
         JOptionPane.showMessageDialog(this, ex1
                                       .getClientMessage(), "异常信息",
                                       JOptionPane.INFORMATION_MESSAGE);
         return;
       }*/

      for (int i = 0; i < parts.size(); i++) {
        int row = qMMultiList.getNumberOfRows();
        Object[] partAndParent = (Object[]) objs[i];
        QMPartMasterIfc partMaster = (QMPartMasterIfc) partAndParent[0];
        QMPartIfc parent = (QMPartIfc) partAndParent[1];
        String partNumber = partMaster.getPartNumber();
        String partName = partMaster.getPartName();
        String parentPartNum = "";
        String parentPartName = "";
        String count = "";
        if (parent != null) {
          parentPartNum = parent.getPartNumber();
          parentPartName = parent.getPartName();
          int co = ( (Integer) map.get( ( (QMPartIfc) ps.elementAt(i)).getBsoID())).
              intValue();
          count = new Integer(co).toString();
        }
        String partRouteStatus = "无";
        String[] listInfo = {
            partMaster.getBsoID(), partNumber,
            partName, parentPartNum, parentPartName, count,
            partRouteStatus};
        qMMultiList.addTextCell(row, 0, listInfo[0]);
        qMMultiList.addTextCell(row, 1, listInfo[1]);
        qMMultiList.addTextCell(row, 2, listInfo[2]);
        qMMultiList.addTextCell(row, 3, listInfo[3]);
        qMMultiList.addTextCell(row, 4, listInfo[4]);
        qMMultiList.addTextCell(row, 5, listInfo[5]);
        qMMultiList.addTextCell(row, 6, listInfo[6]);
        if (verbose) {
          System.out.println("=1=" + listInfo[0] + "=2="
                             + listInfo[1] + "=3=" + listInfo[2] + "=4="
                             + listInfo[3]);
        }
      }
    }
  }

  /**
   * 删除零部件
   */
  //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
  private void deletePart() {
	    int index = qMMultiList.getSelectedRow();

	    if (index < 0) {
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(this
                                    .getParentJFrame(), "请选择零部件", title,
                                    JOptionPane.WARNING_MESSAGE);
      return;
    }
	    if (index != -1) {
	      String adoptStatus = qMMultiList.getCellAt(index, 4).toString();
	      if (!adoptStatus.equals("无")) {
	        int result = JOptionPane.showConfirmDialog(this
	            .getParentJFrame(), "删除零部件会删除它的工艺路线，是否继续？", "提示",
	            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	        switch (result) {
	          case JOptionPane.NO_OPTION: {
	            return;
	          }
	        }
	      }
	      String bsoid = qMMultiList.getCellAt(index, 0).toString();
	      String parentNum = qMMultiList.getCellAt(index, 3).toString();
	      String finalkey = qMMultiList.getCellAt(index, 5).toString();
	      String routeID = qMMultiList.getCellAt(index, 6).toString();
//	      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	      String partID = qMMultiList.getCellAt(index, 9).toString();
//	      CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	      QMPartIfc parent = null;
	      if (parentNum != null && !parentNum.trim().equals("")) {
	        parent = (QMPartIfc) ( (Object[])this.allParts.get(finalkey))[1];

	      }
	      allParts.remove(finalkey);
	      if (addedParts.containsKey(finalkey)) {
	        addedParts.remove(finalkey);








	      }
	      else {

	      }
//	      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	      deleteParts.put(finalkey,
	                        new Object[] {bsoid, parent, routeID, partID});
//	      CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id

	      //liuming 20070605 add
	      String linkID = qMMultiList.getCellAt(index, 8).toString();
	      
	      //String linkID = qMMultiList.getCellAt(index, 8).toString();
	      if(!linkID.equals(""))
	      {
	        if(updateLinksList.contains(linkID))
	        {
	          updateLinksList.remove(linkID);
	        }
	      }
	      qMMultiList.removeRow(index);

	    }
	  }
  
  //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线

  public void clearPartLinks() {
    this.addedParts = new HashMap();
    this.deleteParts = new HashMap();
  }

  /**
   * 获得所有被添加的零部件，用于提交服务端保存。
   * @return java.util.Vector 元素为零部件的BsoID
   */
  public HashMap getAddedPartLinks() {
    if (verbose) {
      System.out.println("所有被添加的零部件:" + addedParts);
    }
    return addedParts;
  }

  /**
   * 获得要修改父件编号的零部件，用于提交服务端保存。 added by skybird 2005.3.4
   * @return
   */
  public Vector getPartsToChange() {
    return this.partsToChange;
  }

  /**
   * 获得所有被删除的零部件，用于提交服务端删除。 服务端应判断零部件关联是否已存在，只删除已经存在的关联
   * @return java.util.Vector 元素为零部件的BsoID
   */
  public HashMap getDeletedPartLinks() {
    if (verbose) {
      System.out.println("所有被删除的零部件:" + deleteParts);
    }
    return deleteParts;
  }

  /**
   * 得到零部件顺序集合
   * @return Vector
   */
 /* public Vector getPartIndex() {
    Vector vec = new Vector();
    int rows = qMMultiList.getNumberOfRows();
    for (int i = 0; i < rows; i++) {
      String[] ids = new String[2];
      ids[0] = qMMultiList.getCellText(i, 0);
      ids[1] = qMMultiList.getCellText(i, 3);
      vec.add(ids);
    }
    return vec;
  }*/
  //CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线
  public Vector getPartIndex() {
	    Vector vec = new Vector();
	    int rows = qMMultiList.getNumberOfRows();
	    System.out.println("获取partindex："+rows);
	    for (int i = 0; i < rows; i++) {
	    	//CCBegin SS5
	    	//  String[] ids = new String[4];
	      //CCBegin SS13
	      //String[] ids = new String[5];
	      String[] ids = new String[6];
	      //CCEnd SS13
	      //CCEnd SS5
	      ids[0] = qMMultiList.getCellText(i, 0); //零件masterID
	      ids[1] = qMMultiList.getCellText(i, 3); //父件编号  实际是产品编号
	      ids[2] = qMMultiList.getCellText(i, 7); //数量  liuming 20070523 add
//        CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	      ids[3] = qMMultiList.getCellText(i, 9); //parid
//        CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
	      String partNumber = qMMultiList.getCellText(i, 1);
	      //CCBegin SS5
	      boolean colorFlag=(Boolean)qMMultiList.getSelectedObject(i, 12);
	      if(colorFlag==true)
	      {
	    	  ids[4]="1";
	      }else
	      {
	    	  ids[4]="0";
	      }
	      //CCEnd SS5
	      //CCBegin SS13
	      //CCBegin SS14
	      //ids[5] = qMMultiList.getCellText(i, 4); //路线状态
	      String rstate = qMMultiList.getCellText(i, 4);
	      if(rstate.equals("无"))
	      {
	      	Class[] c = {String.class};
	      	Object[] objs = {ids[0]};
	      	try
	      	{
	      		ids[5] = (String)useServiceMethod("TechnicsRouteService", "getPartRouteState", c, objs);
	        }
	        catch (QMRemoteException ex)
	        {
	      	  JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                      QMMessage.getLocalizedMessage(RESOURCE, "information", null), JOptionPane.INFORMATION_MESSAGE);
          }
        }
        else
        {
        	ids[5] = rstate;
        }
	      //CCEnd SS14
	      //System.out.println(i+" 路线状态："+ids[5]);
	      //CCEnd SS13
	    //  System.out.println("RouteListPartLink.getPartIndex::::::::::");
	    //  System.out.println(" RouteListPartLinkPanel PartNumber = "+partNumber + "    Count = "+ids[2]);
	      vec.add(ids);
	    }
	    return vec;
	  }

	  //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线


  /**
   * 设置界面为查看状态
   */
  public void setViewModel() {
    addStructJButton.setVisible(false);
    addJButton.setVisible(false);
//  CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加"采用通知书添加"按钮 ,"空路线添加"按钮,"计算"按钮 
    adoptNoticeButton.setVisible(false);
    newButton.setVisible(false);
    //CCBegin SS2
    addMultPartsJButton1.setVisible(false);
    //CCEnd SS2
    //CCBegin SS10
    addAssemblyListPartsJButton.setVisible(false);
    //CCEnd SS10
    jsButton.setVisible(false);
    routelistButton.setVisible(false);
    publishButton.setVisible(false);
//  CCBEnd by leixiao 2008-7-31 原因：解放升级工艺路线,增加"采用通知书添加"按钮 ,"空路线添加"按钮,"计算"按钮  
    structButton.setVisible(false);
    removeJButton.setVisible(false);
    upJButton.setVisible(false);
    downJButton.setVisible(false);
    parentPartJButton.setVisible(false);
    
    //CCBegin by liunan 2012-05-21 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
    manufacturingCheckBox.setVisible(false);
    //CCEnd by liunan 2012-05-21 
    
    //CCBegin SS1
    addLastRouteCheckBox.setVisible(false);
    //CCEnd SS1
    //CCBegin SS6
    saveAs.setVisible(false);
    //CCEnd SS6
    //CCBegin SS17
    setCountButton.setVisible(false);
    //CCEnd SS17
    //CCBegin SS18
    deleteYbSamePartButton.setVisible(false);
    //CCEnd SS18
  }

  /**
   * 设置界面为编辑状态
   */
//CCBegin by leixiao 2009-12-04 setEditModel()->setEditModel(route)
  public void setEditModel(TechnicsRouteListIfc route) {
	routeListInfo=route;
    addStructJButton.setVisible(true);
    addJButton.setVisible(true);
//  CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加"采用通知书添加"按钮 ,"空路线添加"按钮,"计算"按钮 
    adoptNoticeButton.setVisible(true);
    newButton.setVisible(true);
    //CCBegin SS2
    addMultPartsJButton1.setVisible(true);
    //CCEnd SS2
    //CCBegin SS10
    addAssemblyListPartsJButton.setVisible(true);
    //CCEnd SS10
    jsButton.setVisible(true);
    publishButton.setVisible(true);
    structButton.setVisible(true);
   // System.out.println(routeListInfo);    
    if(routeListInfo.getRouteListState().equals("艺毕")){
    	//System.out.println("为艺毕");
        routelistButton.setVisible(true);
      //CCBegin by liunan 2012-05-21 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
      manufacturingCheckBox.setVisible(true);
      //CCEnd by liunan 2012-05-21 
        }
        //CCBegin by liunan 2011-09-21 艺废通知书。
        else if(routeListInfo.getRouteListState().equals("艺废"))
        {
        	routelistButton.setVisible(true);
        }
        //CCEnd by liunan 2011-09-21
        else{
            routelistButton.setVisible(false);
        }
//  CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线,增加"采用通知书添加"按钮 ,"空路线添加"按钮,"计算"按钮   
    
    //CCBegin SS1
    addLastRouteCheckBox.setVisible(true);
    //CCEnd SS1
    //CCBegin SS6
    if(this.getTechnicsRouteList().getRouteListState().equals("前准")||this.getTechnicsRouteList().getRouteListState().equals("艺准")||
    		this.getTechnicsRouteList().getRouteListState().equals("试制")
				|| this.getTechnicsRouteList().getRouteListState().equals("临准")) {
    saveAs.setVisible(true);
    }
    //CCEnd SS6
    removeJButton.setVisible(true);
    parentPartJButton.setVisible(true);
    upJButton.setVisible(true);
    downJButton.setVisible(true);
    //CCBegin SS17
    setCountButton.setVisible(true);
    //CCEnd SS17
    //CCBegin SS18
    if(this.getTechnicsRouteList().getRouteListState().equals("艺毕"))
    {
    	deleteYbSamePartButton.setVisible(true);
    }
    //CCEnd SS18
  }
//CCEnd by leixiao 2009-12-04 
  /**
   * 执行从产品结构中添加操作
   * @param e ActionEvent
   */
  void addStructJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    addConstructPart();
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * 执行普通搜索添加
   * @param e  ActionEvent
   */
  void addJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    addPart();
    setCursor(Cursor.getDefaultCursor());
  }
//CCBegin by leixiao 2009-3-18 原因：解放升级工艺路线,增加"艺准通知书添加"按钮 
  void addroutelistJButton_actionPerformed(ActionEvent e) {
	  //System.out.println("搜索艺准通知书");
     // setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      SearchRouteListJDialog d = new SearchRouteListJDialog(this.getParentJFrame(), "搜索艺准通知书", true);
      d.setVisible(true);
    //  setCursor(Cursor.getDefaultCursor());
	  }
//CCEnd by leixiao 2009-3-18 原因：解放升级工艺路线,增加"艺准通知书添加"按钮  
  /**
   * 执行采用通知书添加
   *
   * @param e
   *            ActionEvent
   */
//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,采用通知书添加  
  void adoptNoticeButton_actionPerformed(ActionEvent e) {
	    //CCBegin by liunan 2011-04-07 根据最新需求，有两个搜索入口，一个是“更改通知书号”，一个是“采用编号-解放”
	    //new SearchAdoptNoticeDialog(this.getParentJFrame(), "搜索采用通知书", true);
	    
	    //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
	    //new SearchAdoptNoticeDialog(this.getParentJFrame(), "搜索更改通知书", true);
		//CCBegin SS4
	    if(manufacturingCheckBox.isSelected())
	    {
	         if(this.getTechnicsRouteList().getRouteListState().equals("前准")||this.getTechnicsRouteList().getRouteListState().equals("艺准")||
	            		this.getTechnicsRouteList().getRouteListState().equals("试制")
	        				|| this.getTechnicsRouteList().getRouteListState().equals("临准")) {
	        	 new SearchZXChangePartDialog(this.getParentJFrame(), "按更改通知书添加", true,true,routeListInfo.getProjectId());
	         }else{
	    	     new SearchAdoptNoticeDialog(this.getParentJFrame(), "搜索更改通知书", true, true);
	         }
	    
	    }
	    else
	    {
	    	
	    	   if(this.getTechnicsRouteList().getRouteListState().equals("前准")||this.getTechnicsRouteList().getRouteListState().equals("艺准")||
	            		this.getTechnicsRouteList().getRouteListState().equals("试制")
	        				|| this.getTechnicsRouteList().getRouteListState().equals("临准")) {
	    	new SearchZXChangePartDialog(this.getParentJFrame(), "按更改通知书添加", true, false,routeListInfo.getProjectId());
	    	   }else{
	    		 new SearchAdoptNoticeDialog(this.getParentJFrame(), "搜索更改通知书", true, false);
	    	   }
	    }
	    	//CCEnd SS4
	    //CCEnd by liunan 2012-05-22
	    
	    //CCEnd by liunan 2011-04-07
	  }
//CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线,采用通知书添加 
  
//CCBegin by leixiao 2008-7-31 原因：增加按发布令号添加  
  void publishButton_actionPerformed(ActionEvent e) {
	    //CCBegin by liunan 2011-04-07 根据最新需求，有两个搜索入口，一个是“更改通知书号”，一个是“采用编号-解放”
	    //new SearchPublishDialog(this.getParentJFrame(), "按发布令号添加", true);
	    
	    //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
	    //new SearchPublishDialog(this.getParentJFrame(), "按解放采用编号添加", true);
		//CCBegin SS4
	    if(manufacturingCheckBox.isSelected())
	    {
	    	  if(this.getTechnicsRouteList().getRouteListState().equals("前准")||this.getTechnicsRouteList().getRouteListState().equals("艺准")||
	            		this.getTechnicsRouteList().getRouteListState().equals("试制")
	        				|| this.getTechnicsRouteList().getRouteListState().equals("临准")) {
	        	 new SearchZXAdoptPartDialog(this.getParentJFrame(), "按采用通知书添加", true,true,routeListInfo.getProjectId());
	         }else{
	    	     new SearchPublishDialog(this.getParentJFrame(), "按解放采用编号添加", true, true);
	         }
	      
	    }
	    else
	    {
	    	  if(this.getTechnicsRouteList().getRouteListState().equals("前准")||this.getTechnicsRouteList().getRouteListState().equals("艺准")||
	            		this.getTechnicsRouteList().getRouteListState().equals("试制")
	        				|| this.getTechnicsRouteList().getRouteListState().equals("临准")) {
	        	 new SearchZXAdoptPartDialog(this.getParentJFrame(), "按采用通知书添加", true,false,routeListInfo.getProjectId());
	         }else{
	    	     new SearchPublishDialog(this.getParentJFrame(), "按解放采用编号添加", false, true);
	         }
	      
	    }
	  //CCEnd SS4
	    //CCEnd by liunan 2012-05-22
	    
	    //System.out.println("1111111111111111");
	    //CCEnd by liunan 2011-04-07
	  }
//CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线,发布令号添加 
  
//CCBegin by leixiao 2011-1-13 原因：增加按总成结构添加  
  void structButton_actionPerformed(ActionEvent e) {
	  SearchStructPartJDialog struct=new SearchStructPartJDialog(this.getParentJFrame());  
	  struct.setVisible(true);
	  }
//CCEnd by leixiao 2011-1-13 原因：解放升级工艺路线,按总成结构添加 
 
    
  //CCBegin SS2
  /**批量添加功能
   * @param e
   */
  void addMultPartsJButton1_actionPerformed(ActionEvent e) {
	    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    MultPartsSearchJDialog searchJDialog = new MultPartsSearchJDialog(this.
	        getParentJFrame());
	    searchJDialog.setVisible(true);
	    setCursor(Cursor.getDefaultCursor());

	  }
  //CCEnd SS2

  //CCBegin SS10
  /**按 工艺合件（路线）申请单 添加生产状态零部件功能
   * @param e
   */
  void addAssemblyListPartsJButton_actionPerformed(ActionEvent e)
  {
  	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  	AssemblyListPartsSearchJDialog searchJDialog = new AssemblyListPartsSearchJDialog(this.getParentJFrame());
	  searchJDialog.setVisible(true);
	  setCursor(Cursor.getDefaultCursor());
	}
  //CCEnd SS10
  
  /**
   * 按输入的零件号搜索没有路线的子件
   * @param e ActionEvent
   */
//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,空路线添加  
  void newButton_actionPerformed(ActionEvent e) {
     SearchSubDialog d = new SearchSubDialog(this.getParentJFrame(), "按零件号搜索", true);
     d.setVisible(true);
  } 
//CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线,空路线添加    
  
  /**
   * 计算表格中所有零件在车型产品中的使用数量，并显示在表格“数量”列
   * @param e ActionEvent
   */
//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,计算数量 
  void jsButton_actionPerformed(ActionEvent e) {
     setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
     ProgressService.setProgressText("正在处理数据...");
     ProgressService.showProgress();

      String productID = routeListInfo.getProductMasterID();
      int rowCounts = qMMultiList.getNumberOfRows();
      HashMap al = new HashMap();
      for (int i = 0; i < rowCounts; i++)
      {
          String partMasterID = qMMultiList.getCellText(i, 0); //零件id
        //  System.out.println("-"+i+"--------partMasterID:"+partMasterID);
          al.put(String.valueOf(i),partMasterID);
      }

      //以productID和al为参数调用服务，返回HashMap key=i, value=数量
      Class[] c = { String.class,HashMap.class};
      Object[] objs = { productID,al};

      try {
        al = (HashMap)this.useServiceMethod("TechnicsRouteService",
                                            "getCounts", c, objs);
      // System.out.println("---------------a1:"+al);
        int size = al.size();
        //将数量逐一添加入表格
        for(int i=0;i<size;i++)
        {
          qMMultiList.addTextCell(i, 7, al.get(String.valueOf(i)).toString());
        }

        //更新关联
        for(int k=0;k<rowCounts;k++)
        {
          String linkID = qMMultiList.getCellAt(k,8).toString();
          if(!linkID.equals("") && !updateLinksList.contains(linkID))
          {
            updateLinksList.add(linkID);
          }
        }


      }
      catch (QMRemoteException ex) {
        JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                      QMMessage.getLocalizedMessage(RESOURCE,
            "information",
            null), JOptionPane.INFORMATION_MESSAGE);

      }
      ProgressService.hideProgress();
      setCursor(Cursor.getDefaultCursor());


  }  
//CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线,计算数量 

  /**
   * 执行搜索父件的功能
   * @param e ActionEvent
   * 有不妥之处
   */
  void parentPartJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    parentPart();
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * 删除所选择的零部件
   * @param e ActionEvent
   */
  void removeJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    deletePart();
    setCursor(Cursor.getDefaultCursor());
  }

//CCBegin by leixiao 2008-11-11 原因：解放升级工艺路线 ,数量手动更改
//void qMMultiList_actionPerformed(ActionEvent e) {
//}
void qMMultiList_componentResized(ComponentEvent e) {
    qMMultiList.redrawColumns();
}

void qMMultiList_actionPerformed(ActionEvent e) {
	  //System.out.println("----11111111111111111");
	     Object obj = e.getSource();
	     int index = qMMultiList.getSelectedRow();
	     //CCBegin SS5
	   //  if(obj instanceof JTextField )
	     if((obj instanceof JTextField || obj instanceof JCheckBox))
	     //CCEnd SS5
	     {
	    	
	       String linkID = qMMultiList.getCellAt(index,8).toString();
	       //System.out.println("----"+linkID);
	       if(!linkID.equals("") && !updateLinksList.contains(linkID))
	       {
	         //System.out.println("Update PART = "+qMMultiList.getCellAt(index,1));
	         updateLinksList.add(linkID);
	       }
	     }
	  }
//CCEnd by leixiao 2008-11-11 原因：解放升级工艺路线

  /** 设置被选中的行 */
  public void setSelectedNum(int arg) {
    theSelectedNum = arg;
  }

  /** 获取被选中的行 */
  public int getSelectedNum() {
    return theSelectedNum;
  }

  /**
   * 执行上下移操作 使用MiltiList的方法取得选择行
   * 设置要修改的行 判断要上下移的行是否到达数组边界
   * Added by Ginger 05/05/08
   */
  private void upRow() {
    int selectedRow = qMMultiList.getSelectedRow();
    int changedRow = selectedRow - 1;
    if (selectedRow < 0) {
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(this
                                    .getParentJFrame(), "请选择零部件", title,
                                    JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (selectedRow == 0) {
      upJButton.enableInputMethods(false);
    }
    else {
      qMMultiList.changeRow(selectedRow, changedRow);
    }
    if (selectedRow-- <= 0) {
      selectedRow = 0;
    }
    qMMultiList.selectRow(selectedRow);
  }

  private void downRow() {
    int selectedRow = qMMultiList.getSelectedRow();
    if (selectedRow < 0) {
      String title = QMMessage.getLocalizedMessage(RESOURCE,
          "information", null);
      JOptionPane.showMessageDialog(this
                                    .getParentJFrame(), "请选择零部件", title,
                                    JOptionPane.WARNING_MESSAGE);
      return;
    }
    int changedRow = selectedRow + 1;
    int totalRowNumber = qMMultiList.getNumberOfRows();
    if (selectedRow == (totalRowNumber - 1)) {
      downJButton.enableInputMethods(false);
    }
    else {
      qMMultiList.changeRow(selectedRow, changedRow);
    }
    if (selectedRow++ >= (totalRowNumber - 1)) {
      selectedRow = totalRowNumber - 1;
    }
    qMMultiList.selectRow(selectedRow);
  }

  /**
   * 执行上移事件
   * @param e ActionEvent
   */
  private void upJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    upRow();
    setCursor(Cursor.getDefaultCursor());
  }

  /**
   * 执行下移事件
   * @param e ActionEvent
   */
  private void downJButton_actionPerformed(ActionEvent e) {
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    downRow();
    setCursor(Cursor.getDefaultCursor());
  }
  
  //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线 
  public ArrayList getUpdateLinks()
  {
    return updateLinksList;
  }
  //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
  //CCBegin SS5
  
  //CCEnd SS6
  
  //CCBegin SS17
  void setCountButton_actionPerformed(ActionEvent e)
  {
  	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  	ProgressService.setProgressText("正在处理数据...");
  	ProgressService.showProgress();
  	
  	int rowCounts = qMMultiList.getNumberOfRows();
  	System.out.println("rowCounts==="+rowCounts);
  	for (int i = 0; i < rowCounts; i++)
  	{
  		String count = qMMultiList.getCellText(i, 7);
  		System.out.println(i+"  count==="+count);
  		if(count==null||count.equals("")||count.equals("0"))
  		{
  			qMMultiList.addTextCell(i, 7, "1");
  		System.out.println("after count==="+qMMultiList.getCellText(i, 7));
        String linkID = qMMultiList.getCellAt(i,8).toString();
        if(!linkID.equals("") && !updateLinksList.contains(linkID))
        {
        	updateLinksList.add(linkID);
        }
  		}
  	}
    ProgressService.hideProgress();
    setCursor(Cursor.getDefaultCursor());
  }
  //CCEnd SS17

  //CCBegin SS18
  void deleteYbSamePartButton_actionPerformed(ActionEvent e)
  {
  	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  	ProgressService.setProgressText("正在处理数据...");
  	ProgressService.showProgress();
  	
  	ArrayList numlist = new ArrayList();
  	ArrayList indexlist = new ArrayList();
  	int rowCounts = qMMultiList.getNumberOfRows();
  	for (int i = 0; i < rowCounts; i++)
  	{
  		String num = qMMultiList.getCellAt(i,1).toString();
  		System.out.println("num==="+num);
  		if(num!=null&&!num.equals(""))
  		{
  			if(numlist.contains(num))
  			{System.out.println("11");
  				indexlist.add(0,i);
  			}
  			else
  			{System.out.println("22");
  				numlist.add(num);
  			}
  		}
  	}
  	
  	for(Iterator iter= indexlist.iterator();iter.hasNext();)
  	{
  		int index = (Integer)iter.next();
  		
  		deletePart(index);
  	}
  	
    ProgressService.hideProgress();
    setCursor(Cursor.getDefaultCursor());
  }
  
  
  private void deletePart(int index)
  {
  	System.out.println("index==="+index);
  	if (index < 0)
  	{
      String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
      JOptionPane.showMessageDialog(this.getParentJFrame(), "请选择零部件", title, JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (index != -1)
    {
	    String bsoid = qMMultiList.getCellAt(index, 0).toString();
	    String parentNum = qMMultiList.getCellAt(index, 3).toString();
	    String finalkey = qMMultiList.getCellAt(index, 5).toString();
	    String routeID = qMMultiList.getCellAt(index, 6).toString();
	    String partID = qMMultiList.getCellAt(index, 9).toString();
	    QMPartIfc parent = null;
	    if (parentNum != null && !parentNum.trim().equals(""))
	    {
	    	parent = (QMPartIfc) ( (Object[])this.allParts.get(finalkey))[1];
	    }
	    allParts.remove(finalkey);
	    if (addedParts.containsKey(finalkey))
	    {
	    	addedParts.remove(finalkey);
	    }
	    deleteParts.put(finalkey, new Object[] {bsoid, parent, routeID, partID});
	    String linkID = qMMultiList.getCellAt(index, 8).toString();
	    if(!linkID.equals(""))
	    {
	    	if(updateLinksList.contains(linkID))
	    	{
	    		updateLinksList.remove(linkID);
	    	}
	    }
	    qMMultiList.removeRow(index);
	  }
	}
  //CCEnd SS18
}
