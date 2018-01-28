/** ���ɳ���ExportAttributesJPanel.java	1.1  2003/05/20
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 A004-2016-3365 �޸�iba���Ի�ȡ��ʽ�����ٴ�properties�ļ��л�ȡ����Ϊ�Ӵ�������л�ȡ���û�������ʱ���á� liunan 2016-6-13
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
 * <p>Title: �㲿������������</p>
 * <p>Description: ���ڶ��������嵥����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author ����
 * @version 1.0
 */

public class ExportAttributesJPanel
    extends JPanel {

  /**���л�ID*/
  static final long serialVersionUID = 1L;     
   	
  /**�����*/
  private JPanel mainJPanel = new JPanel();

  /**��������Թ������*/
  private JScrollPane mayOutPutJScrollPane = new JScrollPane();

  /**������Թ������*/
  private JScrollPane outPutJScrollPane = new JScrollPane();

  /**����������ԡ���ǩ*/
  private JLabel mayOutputJLabel = new JLabel();

  /**��������ԡ���ǩ*/
  private JLabel outPutJLabel = new JLabel();

  /**������԰�ť�����ƣ�*/
  private JButton addAttributeJButton = new JButton();

  /**ȫ����Ӱ�ť�����ƣ�*/
  private JButton addAllJButton = new JButton();

  /**ɾ�����԰�ť�����ƣ�*/
  private JButton deleteAttriJButton = new JButton();

  /**ɾ��ȫ����ť�����ƣ�*/
  private JButton deleteAllJButton = new JButton();

  /**����������б�*/
  private JList mayOutPutJList = new JList();

  /**��������б�*/
  private JList outPutJList = new JList();

  /**�������ư�ť*/
  private JButton upMoveJButton = new JButton();

  /**�������ư�ť*/
  private JButton downMoveJButton = new JButton();

//CCBegin by leixiao 2009-12-10   ��Դ�ļ���ʽ���ԣ��Ҳ���
//private static String RESOURCE = "com/faw_qm/part/client/other/util/OtherRB";
private static String RESOURCE =
        "com.faw_qm.part.client.other.util.OtherRB";
//CCEnd by leixiao 2009-12-10  
  /**�㲿������*/
  private String name = QMMessage.getLocalizedMessage(RESOURCE, "partName", null);

  /**�㲿�����*/
  private String number = QMMessage.getLocalizedMessage(RESOURCE, "partNumber", null);

  /**�㲿������*/
  private String quantity = QMMessage.getLocalizedMessage(RESOURCE, "quantity", null);

  /**��ź����Ƶ�����*/
  private String[] expData = {
      number, name, quantity};

  /**�㲿���汾*/
  private String version = QMMessage.getLocalizedMessage(RESOURCE, "version", null);

  /**�㲿����Դ*/
  private String producedBy = QMMessage.getLocalizedMessage(RESOURCE, "source", null);

  /**�㲿����ͼ*/
  private String viewName = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);

  /**�㲿������Ŀ������*/
  private String projectTeamName = QMMessage.getLocalizedMessage(RESOURCE,
      "projectTeamName", null);

  /**�㲿����λ*/
  private String unit = QMMessage.getLocalizedMessage(RESOURCE, "unit", null);

  /**�㲿������*/
  private String partType = QMMessage.getLocalizedMessage(RESOURCE, "partType", null);

  /**�㲿����������״̬*/
  private String lifeCycleState = QMMessage.getLocalizedMessage(RESOURCE,
      "lifeCycleState", null);

  /**���л����������飨������ʾ�ڽ����ϣ�*/
  private String[] basicAttriName = {
      producedBy, viewName, projectTeamName, version, partType, lifeCycleState,
      unit};
  private int basicAttriNameLen = basicAttriName.length;

  /**���л����������飨���ڲ������ݣ�*/
  private String[] basicAttriEnglishName = {
      "producedBy", "viewName", "projectName", "versionValue", "partType",
      "lifeCycleState", "defaultUnit"};

  /**��չ�������ƣ�������ʾ�ڽ����ϣ�*/
  private String[] extendDefineName;

  /**��չ�������ƣ����ڲ������ݣ�*/
  private String[] extendDefineEnglishName;

  /**�����������飨������ʾ�ڽ����ϣ�*/
  private String[] showAttributes;

  /**�����������飨���ڲ������ݣ�*/
  private String[] showAttributes1;

  /**���Ҫ�����ݿͻ���ҳ��Ĳ���*/
  private HashMap map = new HashMap();

  /**�������Ƶļ���*/
  private Vector attributeName;

  /**���Ա�*/
  private HashMap hashmap = new HashMap();

  /**���Ա���*/
  private static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.part.verbose", "true")).equals("true");

//add by cdc
  private int extendSize = 0;

  //add by liun
  /**����������*/
  JPanel mayOutPutJPanel = new JPanel();
  /**����·�����*/
  JPanel routeJPanel = new JPanel();
  /**����·�ߵ�ѡ��*/
  JCheckBox routeJCheckBox = new JCheckBox();
  /**����·�߹������*/
  JScrollPane routeJScrollPane = new JScrollPane();
  /**����·���б�*/
  JList routeJList = new JList();
  /**���ֹ�����*/
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  /**����·��*/
  private String manufactureRoute = QMMessage.getLocalizedMessage(RESOURCE,
      "manufactureRoute", null);

  /**װ��·��*/
  private String assemblyRoute = QMMessage.getLocalizedMessage(RESOURCE,
      "assemblyRoute", null);

  /**��ע*/
  private String remark = QMMessage.getLocalizedMessage(RESOURCE, "remark", null);

  /**��׼���*/
  private String routeListNumber = QMMessage.getLocalizedMessage(RESOURCE,
      "routeListNumber", null);

  /**��Ӧ��*/
  private String vendor = QMMessage.getLocalizedMessage(RESOURCE, "vendor", null);

  /**�㲿��*/
  private String part = QMMessage.getLocalizedMessage(RESOURCE, "part", null);

  /**����·��*/
  private String technicsRoute = QMMessage.getLocalizedMessage(RESOURCE,
      "technicsRoute", null);

  /**��������·��*/
  private String includeTechnicsRoute = QMMessage.getLocalizedMessage(RESOURCE,
      "includeTechnicsRoute", null);

  /**���л����������飨������ʾ�ڽ����ϣ�*/
  private String[] basicRouteName = {
      manufactureRoute, assemblyRoute, remark, routeListNumber};

  /**���л����������飨���ڲ������ݣ�*/
  private String[] basicRouteEnglishName = {
      manufactureRoute, assemblyRoute, remark, routeListNumber};

  /**����·�����飨������ʾ�ڽ����ϣ�*/
  private String[] showRouteAttributes;

  /**����·�����飨���ڲ������ݣ�*/
  private String[] showRouteAttributes1;
  GridBagLayout gridBagLayout4 = new GridBagLayout();

  /**
   * ���캯��
   */
  public ExportAttributesJPanel()
  {
      try
      {
          //�����л������Ժ���չ���Ժϲ�,���������������
          this.getCollectAttributes();
          //���������Լ��������
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
   * ���캯��
   * @param bool boolean
   */
  public ExportAttributesJPanel(boolean bool) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:ExportAttributeJPanel() begin ....");
    }
    try {
      //�����л������Ժ���չ���Ժϲ�,���������������
      this.getCollectAttributes();
      //���������Լ��������
      for (int i = 0; i < showAttributes.length; i++) {
        hashmap.put(showAttributes[i], showAttributes1[i]);
        if (verbose) {
          System.out.println(hashmap.get(showAttributes[i]));
        }
      }
      //�������·������
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
   * ��ʼ��
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
    //CCBegin by liunan 2011-06-21 �����ѡ������������ۡ�
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
    //��ʼ������������б����������б�
    populateList();
    //������ʹ�ù���ˢ�½���
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
   * ���ػ�
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
   * �����л������Ժ���չ���Ժϲ�,���������������
   */
  //add by lyp ���ڻ�����ж����IBA����
  private void getCollectAttributes() {
    if (verbose) {
      System.out.println("����lyp�Ļ��IBA���Է���GetCollectAttributes()");
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
       //if(verbose)System.out.println("IBA���Ե���ʾ����Ϊ�� "+extendDefineName[index]);
         extendDefineEnglishName[index] = englishName[index];
         //if(verbose)System.out.println("IBA���Ե�����Ϊ�� "+extendDefineEnglishName[index]);
             }
       */
      //���ϻ�����е�IBA��������
      //�����л������Ժ���չ���Ժϲ�
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
   *�˷��������Ӵ�������л��Ҫ�����IBA���Ե�����
   *���ص�Vector��������Ԫ�أ���һ��Ԫ�ش�ŵ���IBA���Ե�Ӣ�����Ƶ����飬�ڶ���Ԫ�ش�ŵ���IBA���Ե��������Ƶ����顣
   */
  public Vector getExportIBAAttributes()
  {
  	Vector exportiba = new Vector();
  	try
  	{
  		Class[] c = {String.class,String.class};
  		Object[] objs = {"��������嵥IBA����","�������"};
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
   * ����������б����������б�ĳ�ʼ������
   */
  public void populateList() {
    if (verbose) {
      System.out.println(
          "part.client.other.view.ExportAttributeJPanel:populateList() begin ....");
    }
    //����������б�ģ��
    DefaultListModel mayExpModel = new DefaultListModel();
    //�����������б�ģ���������������
    for (int i = 0; i < showAttributes.length; i++) {
      mayExpModel.addElement(showAttributes[i]);
    }
    //���ÿ���������б�����ģ��
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
    //������������б�������Ըı����
    mayOutPutJList.addPropertyChangeListener(new java.beans.
                                             PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent e) {
        mayOutPutJList_propertyChange(e);
      }
    });
    //������������б������Ŀѡ�����
    mayOutPutJList.addListSelectionListener(new javax.swing.event.
                                            ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        mayOutPutJList_valueChanged(e);
      }
    });

    //��������б������ģ��
    DefaultListModel expModel = new DefaultListModel();
    //����������б�ģ��������㲿���ı�ź���������
    for (int j = 0; j < expData.length; j++) {
      expModel.addElement(expData[j]);
    }
    //������������б������ģ��
    outPutJList.setModel(expModel);
    outPutJList.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        outPutJList_keyPressed(e);
      }
    });
    //����������б������굥������
    outPutJList.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        outPutJList_mouseClicked(e);
      }
    });

//add by liun
//���·���б������ģ��
    DefaultListModel expRouteModel = new DefaultListModel();
    //����������б�ģ��������㲿���ı�ź���������
    for (int j = 0; j < showRouteAttributes.length; j++) {
      expRouteModel.addElement(showRouteAttributes[j]);
    }
    //������������б������ģ��
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
   * ˢ�½���
   */
  public void refresh() {
    if (verbose) {
      System.out.println(
          "part.client.other.view.ExportAttributeJPanel:refresh() begin ....");
    }
    //������б������ģ��
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    /**����������б����п�ѡ��ʱ,ȫ����Ӱ�ť��Ч��������Ч
     *����������б����޿�ѡ��ʱ,��Ӱ�ť��Ч
     */
    if (mayExpModel.isEmpty() == true) {
      addAllJButton.setEnabled(false);
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
      addAllJButton.setEnabled(true);
    }

    /**����������б�����ѡ�ѡ��ʱ,��Ӱ�ť��Ч��������Ч*/
    for (int i = 0; i < mayExpModel.size(); i++) {
      if (mayOutPutJList.isSelectedIndex(i) == true) {
        addAttributeJButton.setEnabled(true);
      }
      else {
        addAttributeJButton.setEnabled(false);
      }
    }

    /**��������б����п�ѡ�����ţ����ƣ�ʱ,ɾ����ť��ȫ��ɾ����ť��Ч��������Ч
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

    /**��������б�����ѡ�ѡ��ʱ,ɾ����ť��Ч��������Ч*/
    for (int i = 0; i < expModel.size(); i++) {
      if (outPutJList.isSelectedIndex(i) == true) {
        deleteAttriJButton.setEnabled(true);
      }
      else {
        deleteAttriJButton.setEnabled(false);
      }
    }

    //add by liun
    //���·���б������ģ��
    DefaultListModel routeModel = (DefaultListModel) routeJList.getModel();
    /**
     * �����·���б����޿�ѡ��ʱ,��Ӱ�ť��Ч
     */
    if (routeModel.isEmpty() == true) {
      addAttributeJButton.setEnabled(false);
    }
    else {
      addAttributeJButton.setEnabled(true);
    }

    /**�����·���б�����ѡ�ѡ��ʱ,��Ӱ�ť��Ч��������Ч*/
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
   * ����������б����Ըı�����¼�����
   * <p>ֻ��ѡ���б���ĳһ���Ӱ�ť�ű�����</p>
   * @param e ����������б����Ըı��¼�
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
   * ����������б�ֵ�ı�����¼�����
   * <p>�ü����¼�ʵ���˿���������б�����ѡ�ѡ��ʱ,��Ӱ�ť��Ч��������Ч</p>
   * @param e �б�ѡȡ�¼�
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
   * ��������б���굥���¼�����
   * <p>���ѡ�С��㲿����š��͡��㲿�����ơ�����ɾ����ťʧЧ��������Ч</p>
   * @param e ��굥���¼�
   */
  void outPutJList_mouseClicked(MouseEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:outPutJList_mouseClicked() begin ....");
    }
    //�����������б������ģ��
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    //��������е���������С����
    if (outPutJList.locationToIndex(e.getPoint()) >= 0) {
      //���������Ե�������
      int index = outPutJList.locationToIndex(e.getPoint());
      //���������������������
      String indexString = (String) expModel.getElementAt(index);
      //���ѡ�С��㲿����š��͡��㲿�����ơ�����ɾ����ťʧЧ��������Ч
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
   * ��ȫ����ӡ���ť����Ϊ�¼�����
   * @param e ��Ϊ�¼�
   */
  void addAllJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:addAllJButton_actionPerformed���� begin ....");
    }
    //��ÿ���������б�����ģ��
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
    //�����������б�����ģ��
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    //�����������������������б�
    for (int i = 0; i < mayExpModel.getSize(); i++) {
      expModel.addElement(mayExpModel.getElementAt(i));
    }
    //���������Դӿ���������б�ɾ��
    mayExpModel.removeAllElements();
    //ˢ�½���
    refresh();
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:addAllJButton_actionPerformed() end ....return is void");
    }
  }

  /**
   * ��Ӱ�ť����Ϊ�¼�����
   * @param e ��Ϊ�¼�
   */
  void addAttributeJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:addAttributeJButton_actionPerformed() begin ....");
    }
    //��ÿ���������б�����ѡ�е������������(��Ϊ�����ѡ)
    int[] selected = mayOutPutJList.getSelectedIndices();
    //��ÿ���������б�����ģ��
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
    //�����������б�����ģ��
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    //����������б������ѡ�е����ԣ�ͬʱ�������Դӿ���������б���ɾ��
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
    //��ÿ����·���б�����ѡ�е������������(��Ϊ�����ѡ)
    int[] selectedRoute = routeJList.getSelectedIndices();
    //��ÿ����·���б�����ģ��
    DefaultListModel routeModel = (DefaultListModel) routeJList.getModel();
    //����������б������ѡ�е�·�ߣ�ͬʱ����·�ߴӿ����·���б���ɾ��
    for (int i = 0; i < selectedRoute.length; i++) {
      String a = routeModel.getElementAt(selectedRoute[i] - i).toString();

      expModel.addElement(a);

      routeModel.removeElementAt(selectedRoute[i] - i);
    }

    //ˢ�½���
    refresh();
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:addAttributeJButton_actionPerformed() end ....return is void");
    }
  }

  /**
   * ɾ����ť����Ϊ�¼�����
   * @param e ��Ϊ�¼�
   */
  void deleteAttriJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:deleteAttriJButton_actionPerformed���� begin ....");
    }
    //�����������б�����ѡ�е������������(��Ϊ�����ѡ)
    int[] selected = outPutJList.getSelectedIndices();
    //��ÿ���������б�����ģ��
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
    //�����������б�����ģ��
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    //������·���б�����ģ��
    DefaultListModel routeModel = (DefaultListModel) routeJList.getModel();
    //�����������б������ѡ�е����ԣ�ͬʱ�������Դ���������б���ɾ��
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
      System.out.println("part.client.other.view.ExportAttributeJPanel:deleteAttriJButton_actionPerformed���� end....return is void");
    }
  }

  /**
   * ȫ��ɾ����ť����Ϊ�¼�����
   * @param e ��Ϊ�¼�
   */
  void deleteAllJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:deleteAllJButton_actionPerformed���� begin ....");
    }
    //��ÿ���������б�����ģ��
    DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
    //�����������б�����ģ��
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
    //������·���б�����ģ��
    DefaultListModel routeModel = (DefaultListModel) routeJList.getModel();
    //����������б��е��������ԣ���ȥ��ź����ƣ���������������б�
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
    //ɾ����������б��е���������
    expModel.removeAllElements();
    //���¼��ϱ�ź��������ԣ���Ϊ�������Ǳ�ѡ��
    expModel.addElement(name);
    expModel.addElement(number);

    refresh();

    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:deleteAllJButton_actionPerformed���� end....return is void");
    }
  }

  /**
   * ��ǰ�ƶ���ť����Ϊ�¼�����
   * @param e ��Ϊ�¼�
   */
  void upMoveJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:upMoveJButton_actionPerformed() begin ....");
    }

    //�����������б�����ѡ�е������������(��Ϊ�����ѡ)
    int[] selected = outPutJList.getSelectedIndices();
    //�����������б�����ģ��
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();

    for (int i = 0; i < selected.length; i++) {
      if (selected[i] > 0) {

        /**���Ҫ�ı�λ�õ���������*/
        String a = expModel.getElementAt(selected[i]).toString();
        String b = expModel.getElementAt(selected[i] - 1).toString();
//        if (isExtendAttr(a) && !isExtendAttr(b) && !isRouteAttr(b)) {
//          continue;
//        }
        /**���ߵ�ֵ�������ݽ���*/
        String temp;
        temp = b;
        b = a;
        a = temp;
        /**���ߵ�ֵ����λ�ý���*/
        expModel.setElementAt(a, selected[i]);
        expModel.setElementAt(b, selected[i] - 1);
        /**�����ѡ��λ������һ����λ*/
        outPutJList.setSelectedIndex(selected[i] - 1);
      }
    }

    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:upMoveJButton_actionPerformed() end....return is void");
    }
  }

  private boolean isExtendAttr(String s) {
    boolean flag = false;
    if (this.extendDefineName != null) { //add by л��
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
    if (this.basicRouteName != null) { //add by л��
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
   * ����ƶ���ť����Ϊ�¼�����
   * @param e ��Ϊ�¼�
   */
  void downMoveJButton_actionPerformed(ActionEvent e) {
    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:downMove_jButton_actionPerformed begin ....");
    }

    //�����������б�����ѡ�е������������(��Ϊ�����ѡ)
    int[] selected = outPutJList.getSelectedIndices();
    //�����������б�����ģ��
    DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();

    for (int i = 0; i < selected.length; i++) {
      if (selected[i] + 1 <= expModel.size() - 1) {
        /**���Ҫ�ı�λ�õ���������*/
        String a = expModel.getElementAt(selected[i]).toString();
        String b = expModel.getElementAt(selected[i] + 1).toString();
//        if (!isExtendAttr(a) && !isRouteAttr(a) && isExtendAttr(b)) {
//          continue;
//        }
        /**���ߵ�ֵ�������ݽ���*/
        String temp;
        temp = b;
        b = a;
        a = temp;
        /**���ߵ�ֵ����λ�ý���*/
        expModel.setElementAt(a, selected[i]);
        expModel.setElementAt(b, selected[i] + 1);
        /**�����ѡ��λ������һ����λ*/
        outPutJList.setSelectedIndex(selected[i] + 1);
      }
    }

    if (verbose) {
      System.out.println("part.client.other.view.ExportAttributeJPanel:downMove_jButton_actionPerformed end....return is void");
    }
  }

  /**
   * ��ȡҪ��������Ե�
   * @return ��������
   */
  public String[] getAttribute() {
    if (verbose) {
      System.out.println(
          "part.client.other.view.ExportAttributeJPanel:getAttribute begin ....");
    }
    attributeName = new Vector();
    //�����������б������ģ��
    DefaultListModel model = (DefaultListModel) outPutJList.getModel();
    //�������ģ�͵Ĵ�С�������Եĸ�����
    int modelSize = ( (DefaultListModel) outPutJList.getModel()).getSize();
    //������ԣ�������ʾ��
    String array1 = "";
    //������ԣ����ڵ��÷���
    String array2 = "";

    //���������������������˱�ź����ƣ�
    for (int k = 0; k < modelSize; k++) {
//      if (model.getElementAt(k) == name || model.getElementAt(k) == number) {
//
//      }
//      else {
      attributeName.add(model.getElementAt(k).toString());
//      }
    }

    //��������������Ĵ�С
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
   * ����������б����Ըı�����¼�����
   * <p>ֻ��ѡ���б���ĳһ���Ӱ�ť�ű�����</p>
   * @param e ����������б����Ըı��¼�
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
