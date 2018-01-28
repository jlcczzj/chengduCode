/** 生成程序ExportAttributesJPanel.java	1.1  2003/05/20
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 A004-2016-3365 修改iba属性获取方式，不再从properties文件中获取，改为从代码管理中获取，用户可以随时配置。 liunan 2016-6-13
 */
package com.faw_qm.part.client.other.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.affixattr.model.AttrDefineInfo;
//add by lyp
import com.faw_qm.part.util.WfUtil;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.beans.*;
import javax.swing.event.*;
//CCBegin SS1
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.codemanage.model.CodingIfc;
import java.util.Collection;
import com.faw_qm.framework.remote.QMRemoteException;
//CCEnd SS1

/**
 * <p>Title: 零部件属性输出面板</p>
 * <p>Description: 用于定制物料清单界面</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 刘明
 * @version 1.0
 */

public class ExportAttributesJPanel
    extends JPanel {

  /**序列化ID*/
  static final long serialVersionUID = 1L;     
   	
  /**主面板*/
  private JPanel mainJPanel = new JPanel();

  /**可输出属性滚动面板*/
  private JScrollPane mayOutPutJScrollPane = new JScrollPane();

  /**输出属性滚动面板*/
  private JScrollPane outPutJScrollPane = new JScrollPane();

  /**“可输出属性”标签*/
  private JLabel mayOutputJLabel = new JLabel();

  /**“输出属性”标签*/
  private JLabel outPutJLabel = new JLabel();

  /**添加属性按钮（右移）*/
  private JButton addAttributeJButton = new JButton();

  /**全部添加按钮（右移）*/
  private JButton addAllJButton = new JButton();

  /**删除属性按钮（左移）*/
  private JButton deleteAttriJButton = new JButton();

  /**删除全部按钮（左移）*/
  private JButton deleteAllJButton = new JButton();

  /**可输出属性列表*/
  private JList mayOutPutJList = new JList();

  /**输出属性列表*/
  private JList outPutJList = new JList();

  /**属性上移按钮*/
  private JButton upMoveJButton = new JButton();

  /**属性下移按钮*/
  private JButton downMoveJButton = new JButton();

//CCBegin by leixiao 2009-12-10   资源文件方式不对，找不着
//private static String RESOURCE = "com/faw_qm/part/client/other/util/OtherRB";
private static String RESOURCE =
        "com.faw_qm.part.client.other.util.OtherRB";
//CCEnd by leixiao 2009-12-10  
  /**零部件名称*/
  private String name = QMMessage.getLocalizedMessage(RESOURCE, "partName", null);

  /**零部件编号*/
  private String number = QMMessage.getLocalizedMessage(RESOURCE, "partNumber", null);

  /**零部件数量*/
  private String quantity = QMMessage.getLocalizedMessage(RESOURCE, "quantity", null);

  /**编号和名称的数组*/
  private String[] expData = {
      number, name, quantity};

  /**零部件版本*/
  private String version = QMMessage.getLocalizedMessage(RESOURCE, "version", null);

  /**零部件来源*/
  private String producedBy = QMMessage.getLocalizedMessage(RESOURCE, "source", null);

  /**零部件视图*/
  private String viewName = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);

  /**零部件的项目组名称*/
  private String projectTeamName = QMMessage.getLocalizedMessage(RESOURCE,
      "projectTeamName", null);

  /**零部件单位*/
  private String unit = QMMessage.getLocalizedMessage(RESOURCE, "unit", null);

  /**零部件类型*/
  private String partType = QMMessage.getLocalizedMessage(RESOURCE, "partType", null);

  /**零部件生命周期状态*/
  private String lifeCycleState = QMMessage.getLocalizedMessage(RESOURCE,
      "lifeCycleState", null);

  /**所有基本属性数组（用于显示在界面上）*/
  private String[] basicAttriName = {
      producedBy, viewName, projectTeamName, version, partType, lifeCycleState,
      unit};
  private int basicAttriNameLen = basicAttriName.length;

  /**所有基本属性数组（用于参数传递）*/
  private String[] basicAttriEnglishName = {
      "producedBy", "viewName", "projectName", "versionValue", "partType",
      "lifeCycleState", "defaultUnit"};

  /**扩展属性名称（用于显示在界面上）*/
  private String[] extendDefineName;

  /**扩展属性名称（用于参数传递）*/
  private String[] extendDefineEnglishName;

  /**所有属性数组（用于显示在界面上）*/
  private String[] showAttributes;

  /**所有属性数组（用于参数传递）*/
  private String[] showAttributes1;

  /**存放要传到瘦客户端页面的参数*/
  private HashMap map = new HashMap();

  /**属性名称的集合*/
  private Vector attributeName;

  /**属性表*/
  private HashMap hashmap = new HashMap();

  /**测试变量*/
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.part.verbose", "true")).equals("true");

//add by cdc
  private int extendSize = 0;

  //add by liun
  /**输出属性面板*/
  JPanel mayOutPutJPanel = new JPanel();
  /**工艺路线面板*/
  JPanel routeJPanel = new JPanel();
  /**工艺路线单选框*/
  JCheckBox routeJCheckBox = new JCheckBox();
  /**工艺路线滚动面板*/
  JScrollPane routeJScrollPane = new JScrollPane();
  /**工艺路线列表*/
  JList routeJList = new JList();
  /**布局管理器*/
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  /**制造路线*/
  private String manufactureRoute = QMMessage.getLocalizedMessage(RESOURCE,
      "manufactureRoute", null);

  /**装配路线*/
  private String assemblyRoute = QMMessage.getLocalizedMessage(RESOURCE,
      "assemblyRoute", null);

  /**备注*/
  private String remark = QMMessage.getLocalizedMessage(RESOURCE, "remark", null);

  /**艺准编号*/
  private String routeListNumber = QMMessage.getLocalizedMessage(RESOURCE,
      "routeListNumber", null);

  /**供应商*/
  private String vendor = QMMessage.getLocalizedMessage(RESOURCE, "vendor", null);

  /**零部件*/
  private String part = QMMessage.getLocalizedMessage(RESOURCE, "part", null);

  /**工艺路线*/
  private String technicsRoute = QMMessage.getLocalizedMessage(RESOURCE,
      "technicsRoute", null);

  /**包含工艺路线*/
  private String includeTechnicsRoute = QMMessage.getLocalizedMessage(RESOURCE,
      "includeTechnicsRoute", null);

  /**所有基本属性数组（用于显示在界面上）*/
  private String[] basicRouteName = {
      manufactureRoute, assemblyRoute, remark, routeListNumber};

  /**所有基本属性数组（用于参数传递）*/
  private String[] basicRouteEnglishName = {
      manufactureRoute, assemblyRoute, remark, routeListNumber};

  /**所有路线数组（用于显示在界面上）*/
  private String[] showRouteAttributes;

  /**所有路线数组（用于参数传递）*/
  private String[] showRouteAttributes1;
  GridBagLayout gridBagLayout4 = new GridBagLayout();

  /**
   * 构造函数
   */
  public ExportAttributesJPanel()
  {
      try
      {
          //把所有基本属性和扩展属性合并,获得所有属性名称
          this.getCollectAttributes();
          //把所有属性加入哈西表
          for (int i = 0; i < showAttributes.length; i++)
          {
              hashmap.put(showAttributes[i], showAttributes1[i]);
          }

          jbInit();
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
  }

  /**
   * 构造函数
   * @param bool boolean
   */
  public ExportAttributesJPanel(boolean bool) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:ExportAttributeJPanel() begin ....");
    }
    try {
      //把所有基本属性和扩展属性合并,获得所有属性名称
      this.getCollectAttributes();
      //把所有属性加入哈西表
      for (int i = 0; i < showAttributes.length; i++) {
        hashmap.put(showAttributes[i], showAttributes1[i]);
        if (verbose) {
          System.out.println(hashmap.get(showAttributes[i]));
        }
      }
      //获得所有路线名称
      this.getRouteAttributes();
      for (int i = 0; i < showRouteAttributes.length; i++) {
        hashmap.put(showRouteAttributes[i], showRouteAttributes1[i]);
        if (verbose) {
          System.out.println(hashmap.get(showRouteAttributes[i]));
        }
      }

      jbInit();
      if (bool) {
        mayOutPutJPanel.remove(routeJPanel);

      }
      else {
        mainJPanel.remove(addAllJButton);
        mainJPanel.remove(deleteAllJButton);
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:ExportAttributeJPanel() end ....return is void");
    }
  }

  /**
   * 初始化
   * @throws Exception
   */
  private void jbInit() throws Exception {

    this.setLayout(gridBagLayout1);
    mainJPanel.setLayout(gridBagLayout4);
    mayOutputJLabel.setText("mayOutput");
    outPutJLabel.setText("outPut");
    addAttributeJButton.setMaximumSize(new Dimension(100, 23));
    addAttributeJButton.setMinimumSize(new Dimension(100, 23));
    addAttributeJButton.setPreferredSize(new Dimension(100, 23));
    addAttributeJButton.setText("");
    addAttributeJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addAttributeJButton_actionPerformed(e);
      }
    });
    addAllJButton.setMaximumSize(new Dimension(100, 23));
    addAllJButton.setMinimumSize(new Dimension(100, 23));
    addAllJButton.setPreferredSize(new Dimension(100, 23));
    addAllJButton.setText("");
    addAllJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addAllJButton_actionPerformed(e);
      }
    });
    deleteAttriJButton.setMaximumSize(new Dimension(100, 23));
    deleteAttriJButton.setMinimumSize(new Dimension(100, 23));
    deleteAttriJButton.setPreferredSize(new Dimension(100, 23));
    deleteAttriJButton.setMnemonic('0');
    deleteAttriJButton.setText("");
    deleteAttriJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteAttriJButton_actionPerformed(e);
      }
    });
    deleteAllJButton.setMaximumSize(new Dimension(100, 23));
    deleteAllJButton.setMinimumSize(new Dimension(100, 23));
    deleteAllJButton.setPreferredSize(new Dimension(100, 23));
    deleteAllJButton.setText("");
    deleteAllJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteAllJButton_actionPerformed(e);
      }
    });
    upMoveJButton.setMaximumSize(new Dimension(100, 23));
    upMoveJButton.setMinimumSize(new Dimension(100, 23));
    upMoveJButton.setPreferredSize(new Dimension(100, 23));
    upMoveJButton.setToolTipText("");
    upMoveJButton.setText("up");
    upMoveJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        upMoveJButton_actionPerformed(e);
      }
    });
    downMoveJButton.setMaximumSize(new Dimension(100, 23));
    downMoveJButton.setMinimumSize(new Dimension(100, 23));
    downMoveJButton.setPreferredSize(new Dimension(100, 23));
    downMoveJButton.setText("down");
    downMoveJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        downMoveJButton_actionPerformed(e);
      }
    });
    mainJPanel.setMinimumSize(new Dimension(1, 1));
    mainJPanel.setPreferredSize(new Dimension(1, 1));
    mayOutPutJPanel.setBorder(null);
    mayOutPutJPanel.setLayout(gridBagLayout3);
    routeJPanel.setBorder(null);
    routeJPanel.setLayout(gridBagLayout2);
    routeJCheckBox.setFont(new java.awt.Font("Dialog", 0, 12));
    routeJCheckBox.setText(includeTechnicsRoute);
    mainJPanel.add(outPutJLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(5, 10, 5, 0), 0, 0));
    this.add(mainJPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 399,
                                                299));
    mainJPanel.add(outPutJScrollPane,
                   new GridBagConstraints(2, 1, 1, 4, 1.0, 1.0
                                          , GridBagConstraints.WEST,
                                          GridBagConstraints.BOTH,
                                          new Insets(0, 10, 0, 0), 0, 0));
    outPutJScrollPane.getViewport().add(outPutJList, null);
    mainJPanel.add(mayOutputJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(5, 0, 5, 0), 0, 0));
    mainJPanel.add(deleteAllJButton,
                   new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                                          , GridBagConstraints.CENTER,
                                          GridBagConstraints.NONE,
                                          new Insets(30, 8, 92, 0), 0, 0));
    mainJPanel.add(addAttributeJButton,
                   new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                          , GridBagConstraints.CENTER,
                                          GridBagConstraints.NONE,
                                          new Insets(0, 8, 0, 0), 0, 0));
    mainJPanel.add(upMoveJButton, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(30, 8, 0, 0), 0, 0));
    mainJPanel.add(downMoveJButton, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(30, 8, 0, 0), 0, 0));
    mainJPanel.add(mayOutPutJPanel, new GridBagConstraints(0, 1, 1, 4, 1.0, 1.0
        , GridBagConstraints.WEST, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0));
    mayOutPutJPanel.add(mayOutPutJScrollPane,
                        new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.NORTH,
                                               GridBagConstraints.BOTH,
                                               new Insets(0, 0, 0, 0), 0, 0));
    mayOutPutJScrollPane.getViewport().add(mayOutPutJList, null);
    //CCBegin by liunan 2011-06-21 扩大可选属性面积，美观。
    mayOutPutJPanel.add(routeJPanel,
                        new GridBagConstraints(//0, 1, 1, 1, 1.0, 1.0
                                               //, GridBagConstraints.NORTH,
                                               //GridBagConstraints.BOTH,
                                               0, 1, 1, 1, 1.0, 0
                                               , GridBagConstraints.SOUTH,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(0, 0, 0, 0), 0, 55));
    //CCEnd by liunan 2011-06-21            
                
    routeJPanel.add(routeJCheckBox, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.NORTH, GridBagConstraints.NONE,
        new Insets(0, 0, 0, 0), 0, 0));
    routeJPanel.add(routeJScrollPane,
                    new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                                           , GridBagConstraints.NORTH,
                                           GridBagConstraints.BOTH,
                                           new Insets(0, 0, 0, 0), 0, 0));
    routeJScrollPane.getViewport().add(routeJList, null);
    mainJPanel.add(deleteAttriJButton,
                   new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                          , GridBagConstraints.CENTER,
                                          GridBagConstraints.NONE,
                                          new Insets(30, 8, 0, 0), 0, 0));
    mainJPanel.add(addAllJButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(30, 8, 0, 0), 0, 0));

    TitledBorder tb = new TitledBorder(part);
    mayOutPutJScrollPane.setBorder(tb);
    TitledBorder tb1 = new TitledBorder(technicsRoute);
    routeJScrollPane.setBorder(tb1);

    localize();
    //初始化可输出属性列表和输出属性列表
    populateList();
    //按界面使用规则刷新界面
    refresh();
    addAllJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addAllJButton_actionPerformed(e);
      }
    });
    /**upMoveJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        upMoveJButton_actionPerformed(e);
      }
         });*/
    addAttributeJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addAttributeJButton_actionPerformed(e);
      }
    });
    deleteAttriJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteAttriJButton_actionPerformed(e);
      }
    });
    addAttributeJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addAttributeJButton_actionPerformed(e);
      }
    });
    deleteAllJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteAllJButton_actionPerformed(e);
      }
    });
    deleteAttriJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteAttriJButton_actionPerformed(e);
      }
    });
    deleteAllJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteAllJButton_actionPerformed(e);
      }
    });
    deleteAttriJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteAttriJButton_actionPerformed(e);
      }
    });
    addAllJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addAllJButton_actionPerformed(e);
      }
    });
    deleteAllJButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteAllJButton_actionPerformed(e);
      }
    });

  }

  /**
   * 本地化
   */
  public void localize() {
    if (verbose) {
      System.out.println(
          "part.client.other.view.ExportAttributeJPanel:localize begin ....");
    }

    mayOutputJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE, "mayOutput", null));
    outPutJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE, "outPut", null));
    addAttributeJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        "addAttribute", null));
    addAllJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "addAll", null));
    deleteAttriJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        "deleteAttri", null));
    deleteAllJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        "deleteAll", null));
    upMoveJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "upMove", null));
    downMoveJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "downMove", null));

    if (verbose) {
      System.out.println(
          "part.client.other.view.ExportAttributeJPanel:localize end....return is void");
    }

  }

  /**
   * 把所有基本属性和扩展属性合并,获得所有属性名称
   */
  //add by lyp 用于获得所有定义的IBA属性
  private void getCollectAttributes() {
    if (verbose) {
      System.out.println("进入lyp的获得IBA属性方法GetCollectAttributes()");
    }
    //CCBegin SS1
    //WfUtil wfutil = new WfUtil();
    //Vector allstringdef = wfutil.getExportIBAAttributes();
    Vector allstringdef = getExportIBAAttributes();
    //CCEnd SS1
    String[] englishName = (String[]) allstringdef.elementAt(0);
    String[] chineseName = (String[]) allstringdef.elementAt(1);
    //StringDefView[] allstringdef = wfutil.getStringAttributes();
    if (allstringdef != null) {
      int size = englishName.length;
      if (size == 0) {
        int collectSize = basicAttriNameLen + size;
        showAttributes = new String[collectSize];
        showAttributes1 = new String[collectSize];
        for (int i = 0; i < basicAttriNameLen; i++) {
          showAttributes[i] = basicAttriName[i];
          showAttributes1[i] = "," + basicAttriEnglishName[i] + "-0";
        }
        return;
      }
      int index = 0;
      extendDefineName = new String[size];
      extendDefineEnglishName = new String[size];
      extendDefineName = chineseName;
      extendDefineEnglishName = englishName;
      /*
             for(index=0;index<size;index++)
             {
       //StringDefView stringDefView = (StringDefView)allstringdef[index];
       extendDefineName[index] = chineseName[index];
       //if(verbose)System.out.println("IBA属性的显示名称为： "+extendDefineName[index]);
         extendDefineEnglishName[index] = englishName[index];
         //if(verbose)System.out.println("IBA属性的名称为： "+extendDefineEnglishName[index]);
             }
       */
      //以上获得所有的IBA属性名称
      //把所有基本属性和扩展属性合并
      int collectSize = basicAttriNameLen + size;
      showAttributes = new String[collectSize];
      showAttributes1 = new String[collectSize];
      for (int i = 0; i < basicAttriNameLen; i++) {
        showAttributes[i] = basicAttriName[i];
        showAttributes1[i] = basicAttriEnglishName[i] + "-0";
      }
      for (int j = basicAttriNameLen; j < collectSize; j++) {
        showAttributes[j] = extendDefineName[j - basicAttriNameLen];
        showAttributes1[j] = extendDefineEnglishName[j - basicAttriNameLen] +
            "-1";
      }
    }
    else {
      int collectSize = basicAttriNameLen;
      showAttributes = new String[collectSize];
      showAttributes1 = new String[collectSize];
      for (int i = 0; i < basicAttriNameLen; i++) {
        showAttributes[i] = basicAttriName[i];
        showAttributes1[i] = basicAttriEnglishName[i] + "-0";
      }
      extendDefineName = new String[0];
      extendDefineEnglishName = new String[0];
      return;
    }

  }
  
  //CCBegin SS1
  /**
   *此方法用来从代码管理中获得要输出的IBA属性的名称
   *返回的Vector中有两个元素，第一个元素存放的是IBA属性的英文名称的数组，第二个元素存放的是IBA属性的中文名称的数组。
   */
  public Vector getExportIBAAttributes()
  {
  	Vector exportiba = new Vector();
  	try
  	{
  		Class[] c = {String.class,String.class};
  		Object[] objs = {"解放物料清单IBA属性","代码分类"};
  		Collection coll  = (Collection) IBAUtility.invokeServiceMethod("CodingManageService", "getCoding", c, objs);
  		if (coll != null && coll.size() != 0)
  		{
  			String englishName[] = new String[coll.size()];
  			String chineseName[] = new String[coll.size()];
  			int j = 0;
  			for (Iterator i = coll.iterator(); i.hasNext();)
  			{
  				CodingIfc coding = (CodingIfc) i.next();
  				System.out.println(coding.getCodeContent()+"======"+coding.getShorten());
  				englishName[j]=coding.getCodeContent();
  				chineseName[j]=coding.getShorten();
  				j++;
  			}
  			exportiba.addElement(englishName);
  			exportiba.addElement(chineseName);
  		}
  	}
  	catch(QMRemoteException e1)
  	{
  		e1.printStackTrace();
  	}
  	catch(Exception e)
  	{
  		e.printStackTrace();
  	}
  	return exportiba;

  }
  //CCEnd SS1

  private void getRouteAttributes() {
    int size = 4;
    showRouteAttributes = new String[size];
    showRouteAttributes1 = new String[size];
    for (int i = 0; i < size; i++) {
      showRouteAttributes[i] = basicRouteName[i];
      showRouteAttributes1[i] = basicRouteEnglishName[i];
    }
    return;
  }

  /**
   * 可输出属性列表和输出属性列表的初始化方法
   */
  public void populateList() {
    if (verbose) {
      System.out.println(
          "part.client.other.view.ExportAttributeJPanel:populateList() begin ....");
    }
    //可输出属性列表模型
    DefaultListModel mayExpModel = new DefaultListModel();
    //向可输出属性列表模型添加入所有属性
    for (int i = 0; i < showAttributes.length; i++) {
      mayExpModel.addElement(showAttributes[i]);
    }
    //设置可输出属性列表数据模型
    mayOutPutJList.setModel(mayExpModel);
    mayOutPutJList.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        mayOutPutJList_keyPressed(e);
      }
    });
    mayOutPutJList.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        mayOutPutJList_mouseClicked(e);
      }
    });
    //给可输出属性列表添加属性改变监听
    mayOutPutJList.addPropertyChangeListener(new java.beans.
                                             PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        mayOutPutJList_propertyChange(e);
      }
    });
    //给可输出属性列表添加项目选择监听
    mayOutPutJList.addListSelectionListener(new javax.swing.event.
                                            ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        mayOutPutJList_valueChanged(e);
      }
    });

    //输出属性列表的数据模型
    DefaultListModel expModel = new DefaultListModel();
    //向输出属性列表模型添加入零部件的编号和名称属性
    for (int j = 0; j < expData.length; j++) {
      expModel.addElement(expData[j]);
    }
    //设置输出属性列表的数据模型
    outPutJList.setModel(expModel);
    outPutJList.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        outPutJList_keyPressed(e);
      }
    });
    //给输出属性列表添加鼠标单击监听
    outPutJList.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        outPutJList_mouseClicked(e);
      }
    });

//add by liun
//输出路线列表的数据模型
    DefaultListModel expRouteModel = new DefaultListModel();
    //向输出属性列表模型添加入零部件的编号和名称属性
    for (int j = 0; j < showRouteAttributes.length; j++) {
      expRouteModel.addElement(showRouteAttributes[j]);
    }
    //设置输出属性列表的数据模型
    routeJList.setModel(expRouteModel);
    routeJList.addListSelectionListener(new javax.swing.event.
                                        ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        routeJList_valueChanged(e);
      }
    });
    routeJList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        routeJList_propertyChange(e);
      }
    });
    routeJList.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        routeJList_mouseClicked(e);
      }
    });
    routeJList.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        routeJList_keyPressed(e);
      }
    });

    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:populateList() end....return is void");
    }
  }

  /**
   * 刷新界面
   */
  public void refresh() {
    if (verbose) {
      System.out.println(
          "part.client.other.view.ExportAttributeJPanel:refresh() begin ....");
    }
    //获得两列表的数据模型
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    /**可输出属性列表中有可选项时,全部添加按钮有效，否则无效
     *可输出属性列表中无可选项时,添加按钮无效
     */
    if (mayExpModel.isEmpty() == true) {
      addAllJButton.setEnabled(false);
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
      addAllJButton.setEnabled(true);
    }

    /**可输出属性列表中有选项被选中时,添加按钮有效，否则无效*/
    for (int i = 0; i < mayExpModel.size(); i++) {
      if (mayOutPutJList.isSelectedIndex(i) == true) {
        addAttributeJButton.setEnabled(true);
      }
      else {
        addAttributeJButton.setEnabled(false);
      }
    }

    /**输出属性列表中有可选项（除编号，名称）时,删除按钮和全部删除按钮有效，否则无效
     */
    for (int i = 0; i < expModel.size(); i++) {
      String a = expModel.getElementAt(i).toString();
      if (a != name && a != number && expModel.isEmpty() != true) {
        deleteAllJButton.setEnabled(true);
        deleteAttriJButton.setEnabled(true);
      }
      else {
        deleteAllJButton.setEnabled(false);
        deleteAttriJButton.setEnabled(false);
      }
    }

    /**输出属性列表中有选项被选中时,删除按钮有效，否则无效*/
    for (int i = 0; i < expModel.size(); i++) {
      if (outPutJList.isSelectedIndex(i) == true) {
        deleteAttriJButton.setEnabled(true);
      }
      else {
        deleteAttriJButton.setEnabled(false);
      }
    }

    //add by liun
    //获得路线列表的数据模型
    DefaultListModel routeModel = (DefaultListModel) routeJList.getModel();
    /**
     * 可输出路线列表中无可选项时,添加按钮无效
     */
    if (routeModel.isEmpty() == true) {
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
    }

    /**可输出路线列表中有选项被选中时,添加按钮有效，否则无效*/
    for (int i = 0; i < mayExpModel.size(); i++) {
      if (mayOutPutJList.isSelectedIndex(i) == true) {
        addAttributeJButton.setEnabled(true);
      }
      else {
        addAttributeJButton.setEnabled(false);
      }
    }

    if (verbose) {
      System.out.println(
          "part.client.other.view.ExportAttributeJPanel:refresh() end....return is void");
    }
  }

  /**
   * 可输出属性列表属性改变监听事件方法
   * <p>只有选中列表中某一项，添加按钮才被激活</p>
   * @param e 可输出属性列表属性改变事件
   */
  void mayOutPutJList_propertyChange(PropertyChangeEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:mayOutPutJList_propertyChange() begin ....");
    }
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();

    if (mayOutPutJList.isSelectionEmpty() == true) {
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
    }
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:mayOutPutJList_propertyChange() end....return is void");
    }
  }

  /**
   * 可输出属性列表值改变监听事件方法
   * <p>该监听事件实现了可输出属性列表中有选项被选中时,添加按钮有效，否则无效</p>
   * @param e 列表选取事件
   */
  void mayOutPutJList_valueChanged(ListSelectionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:mayOutPutJList_valueChanged() begin ....");
    }
    //DefaultListModel mayExpModel=(DefaultListModel)mayOutPutJList.getModel();

    if (mayOutPutJList.isSelectionEmpty() == true) {
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
    }
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:mayOutPutJList_valueChanged() end....return is void");
    }
  }

  /**
   * 输出属性列表鼠标单击事件方法
   * <p>如果选中“零部件编号“和“零部件名称“，则删除按钮失效，否则有效</p>
   * @param e 鼠标单击事件
   */
  void outPutJList_mouseClicked(MouseEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:outPutJList_mouseClicked() begin ....");
    }
    //获得输出属性列表的数据模型
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    //如果鼠标点中的行索引不小于零
    if (outPutJList.locationToIndex(e.getPoint()) >= 0) {
      //获得输出属性的行索引
      int index = outPutJList.locationToIndex(e.getPoint());
      //根据属性索引获得属性名
      String indexString = (String) expModel.getElementAt(index);
      //如果选中“零部件编号“和“零部件名称“，则删除按钮失效，否则有效
      if (indexString == name || indexString == number ||
          indexString == quantity) {
        deleteAttriJButton.setEnabled(false);
      }
      else {
        deleteAttriJButton.setEnabled(true);
      }
    }
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:outPutJList_mouseClicked() end....return is void ");
    }
  }

  /**
   * “全部添加”按钮的行为事件方法
   * @param e 行为事件
   */
  void addAllJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:addAllJButton_actionPerformed（） begin ....");
    }
    //获得可输出属性列表数据模型
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
    //获得输出属性列表数据模型
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    //把所有属性添加入输出属性列表
    for (int i = 0; i < mayExpModel.getSize(); i++) {
      expModel.addElement(mayExpModel.getElementAt(i));
    }
    //把所有属性从可输出属性列表删除
    mayExpModel.removeAllElements();
    //刷新界面
    refresh();
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:addAllJButton_actionPerformed() end ....return is void");
    }
  }

  /**
   * 添加按钮的行为事件方法
   * @param e 行为事件
   */
  void addAttributeJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:addAttributeJButton_actionPerformed() begin ....");
    }
    //获得可输出属性列表中所选中的所有项的索引(因为允许多选)
    int[] selected = mayOutPutJList.getSelectedIndices();
    //获得可输出属性列表数据模型
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
    //获得输出属性列表数据模型
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    //向输出属性列表添加所选中的属性，同时将该属性从可输出属性列表中删除
    for (int i = 0; i < selected.length; i++) {
      String a = mayExpModel.getElementAt(selected[i] - i).toString();
      if (isExtendAttr(a)) {
        expModel.addElement(a);
      }
      else {
        for (int j = expModel.size() - 1; j > 0; j--) {
          if (!isExtendAttr(expModel.getElementAt(j).toString())) {
            expModel.insertElementAt(a, j + 1);
            break;
          }
        }
      }
      mayExpModel.removeElementAt(selected[i] - i);
    }

    //add by liun
    //获得可输出路线列表中所选中的所有项的索引(因为允许多选)
    int[] selectedRoute = routeJList.getSelectedIndices();
    //获得可输出路线列表数据模型
    DefaultListModel routeModel = (DefaultListModel) routeJList.getModel();
    //向输出属性列表添加所选中的路线，同时将该路线从可输出路线列表中删除
    for (int i = 0; i < selectedRoute.length; i++) {
      String a = routeModel.getElementAt(selectedRoute[i] - i).toString();

      expModel.addElement(a);

      routeModel.removeElementAt(selectedRoute[i] - i);
    }

    //刷新界面
    refresh();
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:addAttributeJButton_actionPerformed() end ....return is void");
    }
  }

  /**
   * 删除按钮的行为事件方法
   * @param e 行为事件
   */
  void deleteAttriJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:deleteAttriJButton_actionPerformed（） begin ....");
    }
    //获得输出属性列表中所选中的所有项的索引(因为允许多选)
    int[] selected = outPutJList.getSelectedIndices();
    //获得可输出属性列表数据模型
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
    //获得输出属性列表数据模型
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    //获得输出路线列表数据模型
    DefaultListModel routeModel = (DefaultListModel) routeJList.getModel();
    //向可输出属性列表添加所选中的属性，同时将该属性从输出属性列表中删除
    for (int i = 0; i < selected.length; i++) {
      String a = expModel.getElementAt(selected[i] - i).toString();
      if (a != name && a != number) {
        if (isRouteAttr(a)) {
          routeModel.addElement(a);
        }
        else if (isExtendAttr(a) || mayExpModel.size() == 0) {
          mayExpModel.addElement(a);
        }
        else {
          for (int j = mayExpModel.size() - 1; j >= 0; j--) {
            if (j == 0) {
              mayExpModel.insertElementAt(a, 0);
              break;
            }
            else
            if (!isExtendAttr(mayExpModel.getElementAt(j).toString())) {
              mayExpModel.insertElementAt(a, j + 1);
              break;
            }
          }
        }
        expModel.removeElementAt(selected[i] - i);
      }
    }

    refresh();
    repaint();

    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:deleteAttriJButton_actionPerformed（） end....return is void");
    }
  }

  /**
   * 全部删除按钮的行为事件方法
   * @param e 行为事件
   */
  void deleteAllJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:deleteAllJButton_actionPerformed（） begin ....");
    }
    //获得可输出属性列表数据模型
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
    //获得输出属性列表数据模型
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    //获得输出路线列表数据模型
    DefaultListModel routeModel = (DefaultListModel) routeJList.getModel();
    //把输出属性列表中的所有属性（除去编号和名称）填加入可输出属性列表
    for (int i = 0; i < expModel.getSize(); i++) {
      String a = expModel.getElementAt(i).toString();
      if (a != name && a != number) {
        if (isRouteAttr(a)) {
          routeModel.addElement(a);
        }
        else if (isExtendAttr(a) || mayExpModel.size() == 0) {
          mayExpModel.addElement(a);
        }
        else {
          for (int j = mayExpModel.size() - 1; j >= 0; j--) {
            if (j == 0) {
              mayExpModel.insertElementAt(a, 0);
              break;
            }
            else
            if (!isExtendAttr(mayExpModel.getElementAt(j).toString())) {
              mayExpModel.insertElementAt(a, j + 1);
              break;
            }
          }
        }
        //  mayExpModel.addElement(expModel.getElementAt(i));
      }
    }
    //删除输出属性列表中的所有属性
    expModel.removeAllElements();
    //重新加上编号和名称属性，因为这两项是必选项
    expModel.addElement(name);
    expModel.addElement(number);

    refresh();

    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:deleteAllJButton_actionPerformed（） end....return is void");
    }
  }

  /**
   * 向前移动按钮的行为事件方法
   * @param e 行为事件
   */
  void upMoveJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:upMoveJButton_actionPerformed() begin ....");
    }

    //获得输出属性列表中所选中的所有项的索引(因为允许多选)
    int[] selected = outPutJList.getSelectedIndices();
    //获得输出属性列表数据模型
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();

    for (int i = 0; i < selected.length; i++) {
      if (selected[i] > 0) {

        /**获得要改变位置的两个对象*/
        String a = expModel.getElementAt(selected[i]).toString();
        String b = expModel.getElementAt(selected[i] - 1).toString();
//        if (isExtendAttr(a) && !isExtendAttr(b) && !isRouteAttr(b)) {
//          continue;
//        }
        /**二者的值对象内容交换*/
        String temp;
        temp = b;
        b = a;
        a = temp;
        /**二者的值对象位置交换*/
        expModel.setElementAt(a, selected[i]);
        expModel.setElementAt(b, selected[i] - 1);
        /**将鼠标选择位置上移一个单位*/
        outPutJList.setSelectedIndex(selected[i] - 1);
      }
    }

    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:upMoveJButton_actionPerformed() end....return is void");
    }
  }

  private boolean isExtendAttr(String s) {
    boolean flag = false;
    if (this.extendDefineName != null) { //add by 谢斌
      for (int i = 0; i < this.extendDefineName.length; i++) {
        if (s.equalsIgnoreCase(extendDefineName[i])) {
          flag = true;
          break;
        }
      }
    }
    return flag;
  }

  private boolean isRouteAttr(String s) {
    boolean flag = false;
    if (this.basicRouteName != null) { //add by 谢斌
      for (int i = 0; i < this.basicRouteName.length; i++) {
        if (s.equalsIgnoreCase(basicRouteName[i])) {
          flag = true;
          break;
        }
      }
    }
    return flag;
  }

  /**
   * 向后移动按钮的行为事件方法
   * @param e 行为事件
   */
  void downMoveJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:downMove_jButton_actionPerformed begin ....");
    }

    //获得输出属性列表中所选中的所有项的索引(因为允许多选)
    int[] selected = outPutJList.getSelectedIndices();
    //获得输出属性列表数据模型
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();

    for (int i = 0; i < selected.length; i++) {
      if (selected[i] + 1 <= expModel.size() - 1) {
        /**获得要改变位置的两个对象*/
        String a = expModel.getElementAt(selected[i]).toString();
        String b = expModel.getElementAt(selected[i] + 1).toString();
//        if (!isExtendAttr(a) && !isRouteAttr(a) && isExtendAttr(b)) {
//          continue;
//        }
        /**二者的值对象内容交换*/
        String temp;
        temp = b;
        b = a;
        a = temp;
        /**二者的值对象位置交换*/
        expModel.setElementAt(a, selected[i]);
        expModel.setElementAt(b, selected[i] + 1);
        /**将鼠标选择位置下移一个单位*/
        outPutJList.setSelectedIndex(selected[i] + 1);
      }
    }

    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:downMove_jButton_actionPerformed end....return is void");
    }
  }

  /**
   * 获取要输出的属性的
   * @return 属性数组
   */
  public String[] getAttribute() {
    if (verbose) {
      System.out.println(
          "part.client.other.view.ExportAttributeJPanel:getAttribute begin ....");
    }
    attributeName = new Vector();
    //获得输出属性列表的数据模型
    DefaultListModel model = (DefaultListModel) outPutJList.getModel();
    //获得数据模型的大小（即属性的个数）
    int modelSize = ( (DefaultListModel) outPutJList.getModel()).getSize();
    //输出属性（用于显示）
    String array1 = "";
    //输出属性（用于调用服务）
    String array2 = "";

    //将属性名加入容器（除了编号和名称）
    for (int k = 0; k < modelSize; k++) {
//      if (model.getElementAt(k) == name || model.getElementAt(k) == number) {
//
//      }
//      else {
      attributeName.add(model.getElementAt(k).toString());
//      }
    }

    //获得属性名容器的大小
    int vectorSize = attributeName.size();

    for (int j = 0; j < vectorSize; j++) {
      if (j == (vectorSize - 1)) {
        array1 += attributeName.elementAt(j).toString();
        if (attributeName.elementAt(j).toString() ==
            number) {
          array2 += "partNumber-0" + ",";
        }
        if (attributeName.elementAt(j).toString() ==
            name) {
          array2 += "partName-0" + ",";
        }
        if (attributeName.elementAt(j).toString().equals(quantity)) {
          array2 += "quantity-0" + ",";
        }

        for (int m = 0; m < showAttributes.length; m++) {
          if (attributeName.elementAt(j).toString() == showAttributes[m]) {
            array2 += hashmap.get(showAttributes[m]).toString() + ",";
          }
        }
        for (int mm = 0; mm < showRouteAttributes.length; mm++) {
          if (attributeName.elementAt(j).toString() == showRouteAttributes[mm]) {
            array2 += hashmap.get(showRouteAttributes[mm]).toString() + "-2" +
                ",";
          }
        }
      }
      else {
        array1 += attributeName.elementAt(j).toString();
        if (attributeName.elementAt(j).toString().equals(number)) {
          array2 += "partNumber-0" + ",";
        }
        if (attributeName.elementAt(j).toString().equals(name)) {
          array2 += "partName-0" + ",";
        }
        if (attributeName.elementAt(j).toString().equals(quantity)) {
          array2 += "quantity-0" + ",";
        }
        if (verbose) {
          System.out.println(attributeName.elementAt(j).toString());
        }
        for (int m = 0; m < showAttributes.length; m++) {
          if (attributeName.elementAt(j).toString().equals(showAttributes[m])) {
            if (verbose) {
              System.out.println(hashmap.get(showAttributes[m]));
            }
            array2 += (hashmap.get(showAttributes[m])).toString() + ",";
          }
        }
        for (int mm = 0; mm < showRouteAttributes.length; mm++) {
          if (attributeName.elementAt(j).toString().equals(showRouteAttributes[
              mm])) {
            if (verbose) {
              System.out.println(hashmap.get(showRouteAttributes[mm]));
            }
            array2 += (hashmap.get(showRouteAttributes[mm])).toString() + "-2" +
                ",";
          }
        }
      }
    }

    String[] attribute = {
        array1, array2};
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:getAttribute end....return is String[]");
    }
    return attribute;
  }

  void mayOutPutJList_mouseClicked(MouseEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:mayOutPutJList_mouseClicked() begin ....");
    }
    if (mayOutPutJList.isSelectionEmpty() == true) {
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
    }
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:mayOutPutJList_mouseClicked() end....return is void ");
    }
  }

  void mayOutPutJList_keyPressed(KeyEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:mayOutPutJList_keyPressed() begin ....");
    }
    if (mayOutPutJList.isSelectionEmpty() == true) {
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
    }
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:mayOutPutJList_keyPressed() end....return is void ");
    }
  }

  void outPutJList_keyPressed(KeyEvent e) {

  }

  void routeJList_keyPressed(KeyEvent e) {

  }

  void routeJList_mouseClicked(MouseEvent e) {
    if (routeJList.isSelectionEmpty() == true) {
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
    }
  }

  /**
   * 可输出属性列表属性改变监听事件方法
   * <p>只有选中列表中某一项，添加按钮才被激活</p>
   * @param e 可输出属性列表属性改变事件
   */
  void routeJList_propertyChange(PropertyChangeEvent e) {
    DefaultListModel mayExpModel = (DefaultListModel) routeJList.getModel();

    if (routeJList.isSelectionEmpty() == true) {
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
    }
  }

  void routeJList_valueChanged(ListSelectionEvent e) {
    if (routeJList.isSelectionEmpty() == true) {
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
    }
  }

}
