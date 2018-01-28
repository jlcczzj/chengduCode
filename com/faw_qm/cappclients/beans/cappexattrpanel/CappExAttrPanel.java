/** 生成程序时间 2003/08/11
  * 程序文件名称 ExtendResource_en_US.java
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  * 
  * 
  * CR1 2009/06/02 郭晓亮   原因:给扩展属性的可编辑表格添加复制粘贴行功能.
  * CR2 2009/06/04 徐春英  参见DefectTD=2200
  * CR3 2009/06/04 徐春英  参见DefectTD=2177
  * CR4 2009/06/09 刘玉柱  参见DefectID=2204       
  * CR5 2009/07/14 刘玉柱  参见DefectID=2472  
  * CR6 2010/01/04 刘志城  参见DefectID=2772   
  * CR7 2010/04/02 徐春英  参见TD问题2245         
  * SS1 轴齿工艺添加主要零件时，如果零件有材料牌号就自动添加到其他相关信息中 pante 2014-10-23
  */

package com.faw_qm.cappclients.beans.cappexattrpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.util.CalendarSelectedPanel;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.beans.QMTextArea;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.extend.model.ExtendAttriedIfc;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.extend.util.ExtendAttGroup;
import com.faw_qm.extend.util.ExtendAttModel;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;

/**
 * <p>Title: 附加属性panel</p>
* <p>Description:有三种模式：编辑模式和搜索模式，查看模式
 * 其中搜索模式用在query的搜索中 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company:faw_qm </p>
 * @author 管春元
 * @version 1.0
 * 问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
 * SS1 处理变速箱与解放更改日期类型不同导致点击工装出错 xudezheng 20130726
 */
public class CappExAttrPanel extends JPanel
{

  //资源文件
  ResourceBundle rb = ResourceBundle.getBundle("com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanelRB",RemoteProperty.getVersionLocale());
  //调试开关
  private static boolean VERBOSE = true;
  static{
    VERBOSE = (new Boolean(RemoteProperty.getProperty("com.faw_qm.cappclients.verbose","true"))).booleanValue();
  }
  private Locale  local = RemoteProperty.getVersionLocale();

  //枚举类缓存
  HashMap etMaps = new HashMap();

  //属性组缓存
  HashMap groupMaps = new HashMap();
  /**
   附加属性的名字集合
   */
  private Vector attriNames;

  /**
   附加属性的值集合
   */
  private Vector attriValues;

  /**
   属性名标签集合
   */
  private javax.swing.JLabel[] labelCompnents;

  /**
   属性值组件集合
   */
  private Vector valueCompnents;

  private HashMap componentsMap = new HashMap();
  /**
   查找时的条件组件集合
   */
  private javax.swing.JCheckBox[] conditionComponents;
  //属性项panel集合
  private JPanel[] panelZone;
  /**
   工作模式
   */
  private int model;

  /**
   工作模式之----编辑
   */
  public  static int EDIT_MODEL = 1;

  /**
   工作模式之----搜索
   */
  public static int SEARCH_MODEL = 2;

  /**
   工作模式之----查看
   */

  public static int VIEW_MODEL = 3;
  //是否有缺省属性值
  private boolean hasDefault;

  //代理类
  private CappExAttrPanelDelegate delegate;

  //是否发有成组属性
  private boolean isGroup = false;

  //是否有控制计划
  boolean isControlPlan = false;
  /**
   * 附加属性容器
   */
  private ExtendAttContainer container;

  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();

  private 	ArrayList editVec = new ArrayList();

  /**
   * 成组属性棉板
   * 问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
   */
  public GroupPanel groupPanel;

  /**
   * 控制计划面板
   */
  GroupPanel controlPlanPanel;

  //附加属性panel
  private JPanel attrPanel = new JPanel();

 // 主动属性位置
  private  int activeAttr = -1;

  //被动属性位置
  private  int passiveAttr = -1;

  private BorderLayout borderLayout1 = new BorderLayout();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private static boolean verbose = RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true").equals("true"); //debug标识
  private static boolean ts16949 = (RemoteProperty.getProperty(
	        "TS16949", "true")).equals("true");

  //add by wangh on 20070717
  public Vector rVec;
  //CCBegin by leixiao 2008-10-27 原因：解放系统升级,用于数据传递
  private QMProcedureInfo proifc = null;
  
  public static boolean isprocedure = true;
  //CCEnd by leixiao 2008-10-27 原因：解放系统升级 
  

  /**
   *　构造函数
   */
  public CappExAttrPanel()
  {

  }

  /**
   构造函数
   @param bsoName
   @bsoName: 附加属性业务类的名称
   根据此名称得到当前类的附加属性方案
   @roseuid 3F27126F0377
   */
  public CappExAttrPanel(String bsoName,String secondName,int model) throws QMException
  {
    try {
      this.model =model;
      delegate = new  CappExAttrPanelDelegate();
      container = delegate.getScheme(bsoName,secondName);


      this.isControlPlan = container.isControlPlan();
      prepareValues();
      jbInit();
    }
    catch(QMException ex) {
      throw ex;
      }catch(Exception e)
      {
        e.printStackTrace();
      }

  }
  public CappExAttrPanel(String bsoName,String secondName,int model,ArrayList vec)throws QMException
  {
	  try {
		  this.editVec = vec;
	      this.model =model;
	      delegate = new  CappExAttrPanelDelegate();
	      container = delegate.getScheme(bsoName,secondName);

	      this.isControlPlan = container.isControlPlan();
	      prepareValues();
	      groupInputJDiolgInit();
	      //jbInit();

	    }
	    catch(QMException ex) {
	      throw ex;
	      }catch(Exception e)
	      {
	        e.printStackTrace();
	      }
  }

  /**
    * 清除添加的内容
    */
  public void clear()
  {
    this.attrPanel.removeAll();
    if(this.isControlPlan)
      jTabbedPane1.remove(controlPlanPanel);
    try {
       prepareValues();
       jbInit();
     }
     catch(Exception e)
     {
       e.printStackTrace();
       }
  }


  /**
   * 界面初始化，jbuilder固定函数
   * @throws Exception
   */
  void jbInit() throws Exception {

    this.setLayout(borderLayout1);
    attrPanel.setLayout(gridBagLayout1);
    if(this.isControlPlan)
    {
      this.add(jTabbedPane1,BorderLayout.CENTER);
      if(valueCompnents.size()>0)
        jTabbedPane1.add(jScrollPane1, "扩展属性");
      jTabbedPane1.add(controlPlanPanel,"控制计划");
      controlPlanPanel.setModel(this.getModel());
    }
    else
      this.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.setBorder(null);
    jScrollPane1.getViewport().add(attrPanel, null);
    HashMap attmap = new HashMap();
    for(int i = 0 ; i<container.getAttCount() ;i++ )
    {
      labelCompnents[i].setHorizontalAlignment(SwingConstants.LEFT);
      labelCompnents[i].setBorder(BorderFactory.createLineBorder(Color.black));
      labelCompnents[i].setMaximumSize(new Dimension(186,22));
      labelCompnents[i].setMinimumSize(new Dimension(186,22));
      labelCompnents[i].setPreferredSize(new Dimension(186,22));

      panelZone[i].setLayout(gridBagLayout1);

      panelZone[i].add(labelCompnents[i],new GridBagConstraints(0,i,1,1,1.0,0.0,GridBagConstraints.WEST,
          GridBagConstraints.NONE,new Insets(4,4,4,4),0,0));

      panelZone[i].add((JComponent)valueCompnents.get(i),new GridBagConstraints(1,i,1,1,100000.0,0.0,GridBagConstraints.EAST,
          GridBagConstraints.HORIZONTAL,new Insets(4,4,4,4),0,0));
      attmap = container.getAttributeAt(i).getFeature();
      String unitLabel = (String)attmap.get("UnitLabel");
      if(unitLabel!=null)
        panelZone[i].add(new JLabel(unitLabel),new GridBagConstraints(2,i,1,1,1.0,0.0,GridBagConstraints.EAST,
          GridBagConstraints.HORIZONTAL,new Insets(4,4,4,4),0,0));
      if(this.getModel()==this.VIEW_MODEL)
      {
        ((JComponent)valueCompnents.elementAt(i)).setEnabled(false);
        if(valueCompnents.elementAt(i) instanceof JPanel)
         {
               for(int j=0;j<((JPanel)valueCompnents.elementAt(i)).getComponentCount();j++)
               ((JPanel)valueCompnents.elementAt(i)).getComponent(j).setEnabled(false);
        }
      }
      if(i==this.passiveAttr)
        ((JComponent)valueCompnents.elementAt(i)).setEnabled(false);
      attrPanel.add(panelZone[i],         new GridBagConstraints(0,i,1,1,1.0,0.0,GridBagConstraints.NORTH,
          GridBagConstraints.HORIZONTAL,new Insets(8,8,8,8),0,0));

    }
    if(isGroup)
    {
      Iterator names = container.getAttGroupNames().iterator();
      String name;
      JLabel nameLabel;
      int i =0;
      while(names.hasNext())
      {
        name = (String)names.next();
        nameLabel = new JLabel(name);
        groupPanel = (GroupPanel)this.groupMaps.get(name);
        attrPanel.add(nameLabel,new GridBagConstraints(0, container.getAttCount()+i, 1, 1, 1.0, 0
         ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(8,8,8,8), 0, 0));
        attrPanel.add(groupPanel,new GridBagConstraints(0, container.getAttCount()+i+1, 1, 1, 1.0, 1.0
         ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(8,8,8,8), 0, 0));
       groupPanel.setModel(this.getModel());
       i = i+2;
      }
    }
    else
    {
      JPanel emptyJPanel = new JPanel();
      attrPanel.add(emptyJPanel,new GridBagConstraints(0, container.getAttCount(), 1, 1, 1.0, 1.0
          ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(8,8,8,8), 0, 0));
    }
  }

  /**
   * 处理关联属性，即当主动属性值改变时，被动属性也根据主动属性更新
   */
  public void handleLinkAtt()
  {
    //xuejing
     if(passiveAttr!=-1)
     {
       Object obj = ( (JComboBox) valueCompnents.elementAt(this.activeAttr)).
           getSelectedItem();
       if ( ( (JComboBox) valueCompnents.elementAt(this.passiveAttr)) != null) {
         if (obj.toString().equals(" "))
           ( (JComboBox) valueCompnents.elementAt(this.passiveAttr)).
               setSelectedItem(" ");
         else {
           String past = (String) container.getAttributeAt(this.passiveAttr).
               getFeature().get("SortType");
           Collection pacol = (Collection)this.etMaps.get(past);
           Iterator i = pacol.iterator();
           CodingIfc ifc = null;
           while (i.hasNext()) {
             ifc = (CodingIfc) i.next();
             if (obj.toString().equals(ifc.getCodingClassification().getCodeSort())) {
               ( (JComboBox) valueCompnents.elementAt(this.passiveAttr)).
                   setSelectedItem(ifc.getCodeContent());
               return;
             }
           }
         }
       }
     }

  }

  /**
   * 初始化界面组件
   */
  private void prepareValues(){
    int size = container.getAttCount();
    labelCompnents = new JLabel[size];
    valueCompnents = new Vector();
    panelZone = new JPanel[size];

    //初始化普通属性
    for(int i = 0;i<size;i++)
    {
      ExtendAttModel model = container.getAttributeAt(i);
      panelZone[i] = new JPanel();
      labelCompnents[i] = new JLabel();
      labelCompnents[i].setText(model.getAttDisplay());
      JComponent component =getCompoment(model);
      componentsMap.put(model.getAttName(),component);
      valueCompnents.add(component);
       HashMap map =   model.getFeature();
       String isActive = (String)map.get("isActive");
       if(isActive!=null)
         if(isActive.equals("true"))
         {
           this.activeAttr = i;

           ((JComboBox)component).addActionListener(new ActionListener()
           {
             public void actionPerformed(ActionEvent e)
             {

                 handleLinkAtt();
             }
           }
             );
         }
         else
           this.passiveAttr = i;
    }
    isGroup = container.isGroup();
    //初始化成组属性
    if(isGroup)
    {
      Iterator names = container.getAttGroupNames().iterator();
      String name;
      while(names.hasNext())
      {
        name = (String)names.next();
        groupPanel = new GroupPanel(container,name,false);
        this.groupMaps.put(name, groupPanel);
        groupPanel.defaultColumnWidths();
        groupPanel.setParentPanel(this);
        if (name.equals("特性控制") ||name.equals("过程FMEA")||name.equals("控制计划")) { //Begin CR1
            //创建表格对象
            ComponentMultiList multiList = groupPanel.multiList;
            multiList.setisShowPopupMenu(true);
          }
        //Begin CR4
        else if(name.equals("snv"))
        {
        	ComponentMultiList multiList = groupPanel.multiList;
            multiList.setisShowPopupMenu(false);
        }
        //End CR4
           //End  CR1
      }
    }
    isControlPlan = container.isControlPlan()&&container.getPlanGroupDes()!=null;
    //初始化控制计划
    if(this.isControlPlan)
    {
      this.controlPlanPanel = new GroupPanel(container,"ControlPlan",true);
      controlPlanPanel.setParentPanel(this);
    }
  }
  
  

  /**
   * 根据附加属性模型得到表示值的控件
   * @param model　属性封装类
   * @return　JComponent 具体控件
   * @see ExtendAttModel
   */
  public JComponent getCompoment(ExtendAttModel model)
  {
    String type = model.getAttType();
     //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2772
    String groupName = model.getGroupName();//CR6
     //CCEnd by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2772
    //如果属性为枚举类型，则获得下拉列表；否则获得文本框
    if(type.equals("EnumeratedType"))
    {
      HashMap map = model.getFeature();
      String classPath = (String)map.get("classpath");
      String newString = (String)map.get("newMethod");
      try{
        Class class1 =  Class.forName(classPath);
        Method newMethod =  class1.getMethod(newString,null);
        EnumeratedType et1 = (EnumeratedType)newMethod.invoke(class1,null);
        EnumeratedType[] ets = et1.getValueSet();
        String value = et1.getDisplay(local);
        Vector values = new Vector();
        for(int k = 0;k<ets.length;k++)
          values.add(ets[k].getDisplay(local));
        JComboBox comBox = new JComboBox(values);
        comBox.setSelectedItem(value);
        this.etMaps.put(classPath,ets);
        return  comBox;
        }catch(Exception e)
        {
          if(verbose)
          e.printStackTrace();
          JOptionPane.showMessageDialog(getParentFrame(),e.getMessage());
        }
        return null;
    }else
    // 处理类型为代码项的饿数据。
      if(type.equals("Coding"))
      {
         HashMap map = model.getFeature();
         String defaultValue = model.getAttDefault();
         boolean isValid = false;
         try{
         String  sortType =(String)map.get("SortType");
           if(VERBOSE)
             System.out.println("sortType::　"+sortType);
                  StringTokenizer ston = new StringTokenizer(sortType, ";");
                  String para1 = ston.nextToken();
                     if(VERBOSE)
                  System.out.println("para1 == "+para1);
                  String para2 = ston.nextToken();
                     if(VERBOSE)
                  System.out.println("para2 == "+para2);
                  String para3 = ston.nextToken();
                  if(VERBOSE)
                    System.out.println(" para3 == "+para3);
                  //modify by wangh on 20070605
                    if(para2.indexOf("Ts16949")!=-1&&(!ts16949))
                    {
                    	//这里给para1和para2设值是为工艺不在TS16949情况下建工序不出现空指针,其值可为代码管理中任何
                        //与Ts16949的代码类别结构相同的其他代码类别.
                    	para1="简图分类";
                    	para2="工艺简图";
                    }
                  Collection sorts = CappTreeHelper.getCoding(para1,para2,para3);
                  Iterator ite = sorts.iterator();
                  Vector vec = new Vector();
                  while(ite.hasNext())
                 {
                   CodingInfo info = (CodingInfo)ite.next();
                   if(info.getCodeContent().equals(defaultValue))
                       isValid = true;
                   vec.add(info.getCodeContent());
                 }
                 JComboBox combox = new JComboBox(vec);
                 //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2772
                 if(groupName==null || !groupName.equals("snv"))//CR6
                 //CCEnd by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2772
                 combox.addItem(" ");//CR5
                 if(isValid&&defaultValue!=null&&!defaultValue.equalsIgnoreCase(""))
                    combox.setSelectedItem(defaultValue);
                 else
                    combox.setSelectedItem(" ");
                 this.etMaps.put(sortType,sorts);
                 return combox;
                }catch(QMRemoteException e)
              {
                if(verbose)
                e.printStackTrace();
                JOptionPane.showMessageDialog(this.getParentJComponent(),e.getClientMessage());
                return null;
                }
      }
    else
      if(type.equalsIgnoreCase("Boolean"))
      {
        Vector vec = new Vector();
        vec.add(" ");
        vec.add(new Boolean("True"));
        vec.add(new Boolean("False"));
        JComboBox checkBox = new JComboBox(vec);
        return checkBox;
    }else
    if(type.equalsIgnoreCase("SpecChar"))
    {
    	  //CCBegin by leixiao 2010-5-4 打补丁 v4r3_p014_20100415    
        SpeCharaterTextPanel speCharPanel = new SpeCharaterTextPanel(new JFrame(),false);//this.getParentFrame());
          //CCEnd by leixiao 2010-5-4 打补丁 v4r3_p014_20100415    
        HashMap speHashmap =  CappClientHelper.getSpechar();
       if(speHashmap != null){
          speCharPanel.setDrawInfo(speHashmap);}
       speCharPanel.setFilePath(RemoteProperty.getProperty("spechar.image.path"));
       speCharPanel.speCharaterTextBean.setFont(new java.awt.Font("Dialog", 0, 18));
       speCharPanel.speCharaterTextBean.setRows(1);
       //speCharPanel.resumeData(vevtor);
      return speCharPanel;
    }
      else
        if(type.equalsIgnoreCase("String"))
        {
          HashMap map = model.getFeature();
          String refType = (String)map.get("RefType");
          if(refType==null)
          {
            JTextField textFld = new JTextField(19);
            return  textFld;
          }else
          if(refType.equals("Date"))
          {
             CalendarSelectedPanel dialog = new CalendarSelectedPanel();
             return  dialog;
          }else
          if(refType.equals("TextArea"))
          {
            QMTextArea jta = new QMTextArea();
           // jta.setColumns(3);
            jta.setAutoscrolls(true);
            jta.setBorder(BorderFactory.createLoweredBevelBorder());
            return jta;
          }else
          if(refType.equals("EnumerateType"))
          {
            String classPath = (String)map.get("classpath");
            String newString = (String)map.get("newMethod");
            try{
              Class class1 =  Class.forName(classPath);
              Method newMethod =  class1.getMethod(newString,null);
              EnumeratedType et1 = (EnumeratedType)newMethod.invoke(class1,null);
              EnumeratedType[] ets = et1.getValueSet();
              String value = et1.getDisplay(local);
              Vector values = new Vector();
              for(int k = 0;k<ets.length;k++)
                values.add(ets[k].getDisplay(local));
              JComboBox comBox = new JComboBox(values);
              comBox.setSelectedItem(value);
              this.etMaps.put(classPath,ets);
              return  comBox;
              }catch(Exception e)
              {
                if(verbose)
                e.printStackTrace();
                JOptionPane.showMessageDialog(getParentFrame(),e.getMessage());
              }
              return null;
          }
          else
          //处理三个字符连接的属性，格式如：　a*b*c
          if(refType.equals("three-dimension"))
          {
                JTextField textField = new JTextField(13);
                JLabel label = new JLabel(" * ");
                JTextField textField1 = new JTextField(13);
                JLabel label1 = new JLabel(" * ");
                JTextField textField2 = new JTextField(13);
               // JLabel label2 = new JLabel(" mm*mm*mm");
                JPanel comPanel = new JPanel();
                comPanel.setLayout(gridBagLayout1);
                comPanel.add(textField,new GridBagConstraints(0,0,1,1,100.0,0.0,GridBagConstraints.WEST,
                    GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0));
                comPanel.add(label,new GridBagConstraints(1,0,1,1,1.0,0.0,GridBagConstraints.WEST,
                    GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));
                 comPanel.add(textField1,new GridBagConstraints(2,0,1,1,100.0,0.0,GridBagConstraints.WEST,
                     GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0));
                comPanel.add(label1,new GridBagConstraints(3,0,1,1,1.0,0.0,GridBagConstraints.WEST,
                     GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));
                comPanel.add(textField2,new GridBagConstraints(4,0,1,1,100.0,0.0,GridBagConstraints.WEST,
                            GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0));
              //  comPanel.add(label2,new GridBagConstraints(5,0,1,1,1.0,0.0,GridBagConstraints.WEST,
                         //   GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));

                return comPanel;
          }else
          //处理复合型属性，具体格式是：　　数值；单位
            if(refType.equals("ComboAtts"))
            {
              try{
                String  sortType =(String)map.get("SortType");
                StringTokenizer ston = new StringTokenizer(sortType, ";");
                String para1 = ston.nextToken();
                if(VERBOSE)
                   System.out.println("para1　＝＝　"+para1);
                String para2 = ston.nextToken();
                if(VERBOSE)
                   System.out.println("para2　＝＝　"+para2);
                String para3 = ston.nextToken();
                Collection sorts = CappTreeHelper.getCoding(para1,para2,para3);
                this.etMaps.put(sortType,sorts);
                Iterator ite = sorts.iterator();
                Vector vec = new Vector();
                while(ite.hasNext())
                {
                  CodingInfo info = (CodingInfo)ite.next();
                  vec.add(info.getCodeContent());
                }
                JComboBox combox = new JComboBox(vec);
                //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2772
                if(groupName==null || !groupName.equals("snv"))//CR6
                combox.addItem(" ");//CR5
                //CCEnd by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2772
                combox.setSelectedItem(" ");
                JTextField textField = new JTextField(19);
                JPanel comPanel = new JPanel();
                comPanel.setLayout(gridBagLayout1);
                comPanel.add(textField,new GridBagConstraints(0,0,1,1,1000.0,0.0,GridBagConstraints.WEST,
                    GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0));
                comPanel.add(combox,new GridBagConstraints(1,0,1,1,1.0,0.0,GridBagConstraints.WEST,
                    GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));
                return comPanel;
                }catch(QMRemoteException e)
                {
                  if(verbose)
                  e.printStackTrace();
                  JOptionPane.showMessageDialog(this.getParentJComponent(),e.getClientMessage());
                }
                return null;
            }
            else
            {
              JTextField textFld = new JTextField(19);
              return  textFld;
            }
        }
        else
        {
          JTextField textFld = new JTextField(19);
          return  textFld;
        }

  }

        /**
         *该Bean的构造函数
         *@return JFrame 使用该Bean的窗口
         */
        protected JFrame getParentFrame()
        {
            Component parent = getParent();
            if (parent == null)
            {
                return null;
            }
            while (parent != null && !(parent instanceof JFrame))
            {
                parent = parent.getParent();
            }
            return (JFrame) parent;
        }

        /**
         *遍历显示扩展属性
         * @param container1 ExtendAttContainer 扩展属性容器
         * @see ExtendAttContainer
         */
       // 20070205薛静修改

        public void show(ExtendAttContainer container1)
        {
            if (container == null)
                return;
            if (container1 == null)
                return;
            if (VERBOSE)
            {
                System.out.println("属性组的大小＝＝　" + container1.getAttGroupCount());
                System.out.println("属性的大小＝＝　" + container1.getAttCount());
            }

            //遍历显示普通属性
            for (int i = 0; i < container1.getAttCount(); i++)
            {
                // if(i==this.passiveAttr)
                //   continue;
                ExtendAttModel model = container1.getAttributeAt(i);
                Object obj = model.getAttValue();
                Component component = (Component)this.componentsMap.get(model.
                        getAttName());
                if (component == null)
                    continue;
                if (model.getAttType().equalsIgnoreCase("EnumeratedType"))
                {
                    if (obj != null)
                        ((JComboBox) component).setSelectedItem(((EnumeratedType)
                                obj).getDisplay(local));
                }
                else
                if (model.getAttType().equalsIgnoreCase("Coding"))
                {
                    if (obj != null)
                    {
                        String sortType = (String) model.getFeature().get(
                                "SortType");
                        Collection col = (Collection) etMaps.get(sortType);
                        Iterator ii = col.iterator();
                        CodingIfc et;
                        while (ii.hasNext())
                        {
                            et = (CodingIfc) ii.next();


                            if (et.getBsoID().equals((String) obj))
                                ((JComboBox) component).setSelectedItem((et).
                                        getCodeContent());

                        }
                    }
                    else
                        ((JComboBox) component).setSelectedItem(" ");
                }
                else
                if (model.getAttType().equalsIgnoreCase("Boolean"))
                {
                    if (obj != null)
                        ((JComboBox) component).setSelectedItem(((Boolean) obj));
                    else
                        ((JComboBox) component).setSelectedItem(" ");

                }
                else
                if (model.getAttType().equalsIgnoreCase("SpecChar"))
                {
                    if (obj != null)
                        ((SpeCharaterTextPanel) component).resumeData(((Vector) obj));
                    // else
                    //((JComboBox)component).setSelectedItem(" ");
                }
                else
                {
                    if (model.getAttType().equalsIgnoreCase("String"))
                    {
                        HashMap map = model.getFeature();
                        String refType = (String) map.get("RefType");
                        if (refType == null)
                        {
                        	//CCBegin SS1
                        	if(component instanceof CalendarSelectedPanel){
                        		((CalendarSelectedPanel) component).setDate("");
                        	}
                        	if(component instanceof JTextField)
                        		////CCEnd SS1
                            if (obj != null)
                                ((JTextField) component).setText(obj.toString());
                            else
                                ((JTextField) component).setText("");
                        }
                        else
                        if (refType.equals("TextArea"))
                        {
                            if (obj != null)
                                ((QMTextArea) component).setText(obj.toString());
                            else
                                ((QMTextArea) component).setText("");
                        }
                        else
                        if (refType.equals("Date"))
                        {
                            if (obj != null)
                                ((CalendarSelectedPanel) component).setDate(obj.
                                        toString());
                            else
                                ((CalendarSelectedPanel) component).setDate("");
                        }
                        else
                        if (refType.equals("EnumerateType"))
                        {
                            if (obj != null)
                                ((JComboBox) component).setSelectedItem(obj.
                                        toString());
                            else
                                ((JComboBox) component).setSelectedItem(" ");
                        }
                        else
                        if (refType.equals("three-dimension"))
                        {
                            if (obj != null)
                            {
                                StringTokenizer ston = new StringTokenizer((String)
                                        obj, "*");
                                JPanel panel = (JPanel) component;
                                if (ston.hasMoreTokens())
                                {
                                    ((JTextField) panel.getComponent(0)).setText(
                                            ston.nextToken());
                                }
                                if (ston.hasMoreTokens())
                                {
                                    ((JTextField) panel.getComponent(2)).setText(
                                            ston.nextToken());
                                }
                                if (ston.hasMoreTokens())
                                {
                                    ((JTextField) panel.getComponent(4)).setText(
                                            ston.nextToken());
                                }
                            }
                        }
                        else
                        if (refType.equals("ComboAtts"))
                        {
                            if (obj != null)
                            {
                                StringTokenizer ston = new StringTokenizer((String)
                                        obj, ";");
                                String valueStr = ston.nextToken();
                                String sortStr = ston.nextToken();
                                if (VERBOSE)
                                    System.out.println("model.getAttName()　＝＝　" +
                                            model.getAttName());
                                JPanel panel = (JPanel) component;
                                ((JTextField) panel.getComponent(0)).setText(
                                        valueStr);
                                String sortType = (String) model.getFeature().get(
                                        "SortType");
                                Collection col = (Collection) etMaps.get(sortType);
                                Iterator ii = col.iterator();
                                CodingIfc et;
                                while (ii.hasNext())
                                {
                                    et = (CodingIfc) ii.next();
                                    if (et.getBsoID().equals((String) sortStr))
                                        ((JComboBox) panel.getComponent(1)).
                                                setSelectedItem(et.getCodeContent());
                                }
                            }
                            else
                            {
                                JPanel panel = (JPanel) component;
                                ((JTextField) panel.getComponent(0)).setText("");
                                ((JComboBox) panel.getComponent(1)).setSelectedItem(
                                        " ");
                            }
                        }
                        else
                        {
                            if (obj != null)
                                ((JTextField) component).setText(obj.toString());
                        }

                    }
                    else
                    if (obj != null)
                        ((JTextField) component).setText(obj.toString());
                    else
                        ((JTextField) component).setText("");
                }

            }

            //显示成组属性
            if (isGroup)
            {
                Iterator names = container.getAttGroupNames().iterator();
                String name;
                GroupPanel gpanel = null;
                while (names.hasNext())
                {
                    name = (String) names.next();
                    gpanel = (GroupPanel)this.groupMaps.get(name);
                    gpanel.show(container1, name);
                }
            }

            //显示控制计划
            if (this.isControlPlan)
                this.controlPlanPanel.show(container1, "ControlPane");

        }

  /**
   * 显示某业务对象的附加属性
   * @param info 扩展属性值对象
   * @see ExtendAttriedInfo
   */
  public void show(ExtendAttriedIfc info)
  {
    ExtendAttContainer container1 = info.getExtendAttributes();
    show(container1);
//    //如果属性容器为空，返回
//    if(container==null)
//      return;
//    if(container1==null)
//      return;
//   if(VERBOSE)
//    {
//      System.out.println("属性组的大小＝＝　"+container1.getAttGroupCount());
//      System.out.println("属性的大小＝＝　"+container1.getAttCount());
//    }
//
//    //遍历显示普通属性
//    for(int i = 0;i<container1.getAttCount();i++)
//    {
//     // if(i==this.passiveAttr)
//     //   continue;
//      ExtendAttModel model = container1.getAttributeAt(i);
//      Object obj = model.getAttValue();
//      Component component = (Component)this.componentsMap.get(model.getAttName());
//      if(component==null)
//        continue;
//      if(model.getAttType().equalsIgnoreCase("EnumeratedType"))
//      {
//        if(obj!=null)
//          ((JComboBox)component).setSelectedItem(((EnumeratedType)obj).getDisplay(local));
//      }
//      else
//        if(model.getAttType().equalsIgnoreCase("Coding"))
//      {
//        if(obj!=null)
//        {
//          String sortType = (String)model.getFeature().get("SortType");
//          Collection col = (Collection)etMaps.get(sortType);
//          Iterator ii = col.iterator();
//          CodingIfc et;
//          while(ii.hasNext())
//          {
//               et = (CodingIfc)ii.next();
//               if(et.getBsoID().equals((String)obj))
//                  ((JComboBox)component).setSelectedItem((et).getCodeContent());
//          }
//        }
//          else
//         ((JComboBox)component).setSelectedItem(" ");
//      }else
//        if(model.getAttType().equalsIgnoreCase("Boolean"))
//        {
//         if(obj!=null)
//            ((JComboBox)component).setSelectedItem(((Boolean)obj));
//          else
//            ((JComboBox)component).setSelectedItem(" ");
//
//        }
//        else
//        if(model.getAttType().equalsIgnoreCase("SpecChar"))
//        {
//         if(obj!=null)
//            ((SpeCharaterTextPanel)component).resumeData(((Vector)obj));
//         // else
//            //((JComboBox)component).setSelectedItem(" ");
//        }
//        else
//        {
//          if(model.getAttType().equalsIgnoreCase("String"))
//          {
//            HashMap map = model.getFeature();
//            String refType = (String)map.get("RefType");
//            if(refType==null)
//            {
//              if(obj!=null)
//                ((JTextField)component).setText(obj.toString());
//                else
//                 ((JTextField)component).setText("");
//            }else
//              if(refType.equals("TextArea"))
//              {
//                if(obj!=null)
//                ((QMTextArea)component).setText(obj.toString());
//                else
//                ((QMTextArea)component).setText("");
//             }else
//             if(refType.equals("Date"))
//             {
//               if(obj!=null)
//                 ((CalendarSelectedPanel)component).setDate(obj.toString());
//               else
//                ((CalendarSelectedPanel)component).setDate("");
//             }
//            else
//              if(refType.equals("EnumerateType"))
//              {
//                if(obj!=null)
//                  ((JComboBox)component).setSelectedItem(obj.toString());
//                else
//                 ((JComboBox)component).setSelectedItem(" ");
//              }
//              else
//              if(refType.equals("three-dimension"))
//              {
//                  if(obj!=null)
//                  {
//                    StringTokenizer ston = new StringTokenizer((String)obj, "*");
//                     JPanel panel = (JPanel)component;
//                    if(ston.hasMoreTokens())
//                    {
//                        ((JTextField)panel.getComponent(0)).setText(ston.nextToken());
//                    }
//                    if(ston.hasMoreTokens())
//                   {
//                      ((JTextField)panel.getComponent(2)).setText(ston.nextToken());
//                   }
//                   if(ston.hasMoreTokens())
//                    {
//                       ((JTextField)panel.getComponent(4)).setText(ston.nextToken());
//                     }
//                  }
//                }else
//                if(refType.equals("ComboAtts"))
//                {
//                  if(obj!=null)
//                  {
//                    StringTokenizer ston = new StringTokenizer((String)obj, ";");
//                    String valueStr = ston.nextToken();
//                    String sortStr = ston.nextToken();
//                    if(VERBOSE)
//                      System.out.println("model.getAttName()　＝＝　"+model.getAttName());
//                    JPanel panel = (JPanel)component;
//                    ((JTextField)panel.getComponent(0)).setText(valueStr);
//                    String sortType = (String)model.getFeature().get("SortType");
//                    Collection col = (Collection)etMaps.get(sortType);
//                    Iterator ii = col.iterator();
//                    CodingIfc et;
//                    while(ii.hasNext())
//                    {
//                       et = (CodingIfc)ii.next();
//                       if(et.getBsoID().equals((String)sortStr))
//                          ((JComboBox)panel.getComponent(1)).setSelectedItem(et.getCodeContent());
//                    }
//                  }
//                  else
//                  {
//                     JPanel panel = (JPanel)component;
//                    ((JTextField)panel.getComponent(0)).setText("");
//                    ((JComboBox)panel.getComponent(1)).setSelectedItem(" ");
//                  }
//                }
//                else
//                {
//                	if(obj!=null)
//                  ((JTextField)component).setText(obj.toString());
//                }
//
//          }else
//            if(obj!=null)
//              ((JTextField)component).setText(obj.toString());
//            else
//             ((JTextField)component).setText("");
//        }
//
//    }
//
//    //显示成组属性
//    if(isGroup)
//    {
//      Iterator names = container.getAttGroupNames().iterator();
//      String name;
//      while(names.hasNext())
//      {
//       name = (String)names.next();
//       groupPanel = (GroupPanel)this.groupMaps.get(name);
//       this.groupPanel.show(info,name);
//     }
//    }
//
//    //显示控制计划
//    if(this.isControlPlan)
//      this.controlPlanPanel.show(info,"ControlPane");
  }

  /**
   *　搜索模式时，得到构造的附加属性查询条件
   * @return HashMap 当搜索的是单个属性时,返回以属性名为键,字符值转为附加属性类型对应的值
   * 为值的HashMap.当搜索的是成组属性时,返回以普通成组属性说明和属性组名组成的字符串为键,
   * 搜索条件的集合为值的HashMap
   */
  public HashMap getCondition()
  {
    String temp;
    HashMap conditions = new HashMap();
    //遍历普通属性，构造条件
    for(int i = 0;i<container.getAttCount();i++)
    {
      ExtendAttModel model = container.getAttributeAt(i);
      Component component = (Component)this.componentsMap.get(model.getAttName());
      if (model.getAttType().equalsIgnoreCase("SpecChar"))
      {
          Vector vector = new Vector();
          vector.addElement(((SpeCharaterTextPanel)component).save());
          conditions.put(model.getAttName(), vector);
          //model.setAttValue(vector);
      }else
      {
         if (model.getAttType().equalsIgnoreCase("EnumeratedType"))
         {
             temp = (String) ((JComboBox) component).getSelectedItem();
         }
         else
         if (model.getAttType().equalsIgnoreCase("Coding"))
         {
             temp = (String) ((JComboBox) component).getSelectedItem();
             if (temp.equals(" "))
                 continue;
         }
         else
         if (model.getAttType().equalsIgnoreCase("Boolean"))
         {
             if (((JComboBox) component).getSelectedItem().toString().equals(" "))
                 continue;
             //2007.10.26 徐春英修改  修改原因:用boolean型扩展属性搜索业务对象时结果不对
             if (((JComboBox) component).getSelectedItem().toString().equals("true"))
                 temp = "true";
             else
                 temp = "false";
         }
         else
         if (model.getAttType().equalsIgnoreCase("String"))
         {
             HashMap map = model.getFeature();
             String refType = (String) map.get("RefType");
             if (refType == null)
                 temp = ((JTextField) component).getText();
             else
             if (refType.equalsIgnoreCase("TextArea"))
             {
                 temp = ((QMTextArea) component).getText();
             }
             else
             if (refType.equalsIgnoreCase("Date"))
             {
                 temp = ((CalendarSelectedPanel) component).getDate();
             }
             else
             if (refType.equalsIgnoreCase("EnumerateType"))
             {
                 temp = (String) ((JComboBox) component).getSelectedItem();
             }
             else
             if (refType.equalsIgnoreCase("three-dimension"))
             {
                 JPanel panel = (JPanel) component;
                 JTextField tf = ((JTextField) panel.getComponent(0));
                 JTextField tf1 = ((JTextField) panel.getComponent(2));
                 JTextField tf2 = ((JTextField) panel.getComponent(4));
                 if (tf.getText().trim().equals("") &&
                     tf1.getText().trim().equals("") &&
                     tf2.getText().trim().equals(""))
                     continue;
                 temp = tf.getText() + "*" + tf1.getText() + "*" +
                        tf2.getText();
             }
             else
             if (refType.equalsIgnoreCase("ComboAtts"))
             {
                 JPanel panel = (JPanel) component;
                 if (((String) ((JComboBox) panel.getComponent(1)).
                      getSelectedItem()).equals(" "))
                     continue;
                 //如果复合类型的数值框没有值，则不添加为条件
                 if (((JTextField) panel.getComponent(0)).getText().trim().
                     equals(""))
                     continue;
                 else
                     temp = ((JTextField) panel.getComponent(0)).getText() +
                            ";" +
                            (String) ((JComboBox) panel.getComponent(1)).
                            getSelectedItem();
             }
             else
                 temp = ((JTextField) component).getText();

         }
         else
             temp = ((JTextField) component).getText();

         boolean check = checkValidity(model, temp, model.getAttDisplay());
         if (check)
         {
             if (temp != null)

                 //字符串不能为空
                 if (temp.equalsIgnoreCase(""))
                 {
                     temp = null;
                 }
             String name = model.getAttName();
             if (temp != null && temp.trim() != "")
             {
                 //转为值，等代服务端做好再定
                 Object value = change(model, temp);
                 conditions.put(name, value);
             }
         }
         else
         {
             return null;
         }
     }
    }
    //得到成组属性的条件
    if(this.isGroup){
    	Iterator names = container.getAttGroupNames().iterator();
    	String name;
    	while(names.hasNext()){
    	name = (String)names.next();
    	groupPanel = (GroupPanel)this.groupMaps.get(name);
      conditions = this.groupPanel.getCondition(conditions);
    	}
    }
      //得到控制计划属性条件
    if(this.isControlPlan)
      conditions = this.controlPlanPanel.getCondition(conditions);
    return conditions;
  }


  /**
   * 把字符值转为附加属性类型对应的值
   * @param model　属性封装类
   * @param text　　属性值，字符值
   * @return　Object 转变后的值
   * @see ExtendAttModel
   */
  Object change( ExtendAttModel model,String text)
  {
    String rstcType = model.getAttType();
    String  defName = model.getAttDisplay();
    HashMap map =  model.getFeature();
    if(rstcType.equalsIgnoreCase("EnumeratedType"))
    {
      String classPath = (String)map.get("classpath");
      EnumeratedType[] ets = (EnumeratedType[])etMaps.get(classPath);
      EnumeratedType et;
      for(int i =0;i<ets.length;i++)
      {
        et = (EnumeratedType)ets[i];
        if(et.getDisplay(local).equals(text))
          return et;
      }
    }else
      if(rstcType.equalsIgnoreCase("Coding"))
      {
        String sortType = (String)map.get("SortType");
        Collection col = (Collection)etMaps.get(sortType);
        Iterator i = col.iterator();
        CodingIfc et;
        while(i.hasNext())
        {
          et = (CodingIfc)i.next();
         if(et.getCodeContent().equals(text))
            return et.getBsoID();
        }
      }else
      if (rstcType.equalsIgnoreCase("int"))
      {
        int textVal = 0;
        textVal = Integer.parseInt(text);
        return new Integer(textVal);
      }else
        if (rstcType.equalsIgnoreCase("double"))
        {
          double doubleVal = 0;
          if(text!=null)
             doubleVal = Double.parseDouble(text);
          return new Double(doubleVal);
        }
        else
          if (rstcType.equalsIgnoreCase("float")) {
        float floatVal = 0;
        floatVal = Float.parseFloat(text);
        return new Float(floatVal);
          }else if (rstcType.equalsIgnoreCase("long")) {
            long longVal = 0;
            longVal = Long.parseLong(text);
            return new Long(longVal);
          }else if (rstcType.equalsIgnoreCase("String")){
            String typestr = (String)map.get("RefType");
           if(typestr!=null&&typestr.equals("ComboAtts"))
           {
                String sortType = (String)map.get("SortType");
                Collection col = (Collection)etMaps.get(sortType);
                Iterator i = col.iterator();
                CodingIfc et;
                StringTokenizer ston = new StringTokenizer(text, ";");
               String valuestr= ston.nextToken();
                String value = ston.nextToken();
                while(i.hasNext())
               {
                    et = (CodingIfc)i.next();
                    if(et.getCodeContent().equals(value))
                      return valuestr+";"+et.getBsoID();
               }
              return text;
           }else
               return text;
          }else if (rstcType.equalsIgnoreCase("boolean")){
            if (text.equalsIgnoreCase("true"))
              return new Boolean(true);
            else
              return  new Boolean(false);

          }
          return null;
  }


  /**
   * 有效性检查
   * @return boolean
   */
  public  boolean check()
  {
    if(container==null)
      return true;

   //遍历普通属性，根据具体类型判断其是否满足约束
    for(int i = 0;i<container.getAttCount();i++)
    {
      String temp = null;
      ExtendAttModel model = container.getAttributeAt(i);
     Component component = (Component)this.componentsMap.get(model.getAttName());
     if(model.getAttType().equalsIgnoreCase("SpecChar"))
    {
           continue;
    }
    else
      if(model.getAttType().equalsIgnoreCase("EnumeratedType"))
      {
        temp = (String)((JComboBox)component).getSelectedItem();
      }
      else
        if(model.getAttType().equalsIgnoreCase("Coding"))
        {
          temp = (String)((JComboBox)component).getSelectedItem();
            if(!model.getAllowNull()&&temp.equals(" "))
            {
                handleNull(model.getAttDisplay());
                return false;
            }
          if(temp.equals(" "))
          continue;
        }else
        if(model.getAttType().equalsIgnoreCase("Boolean"))
        {
          if(((JComboBox)component).getSelectedItem().equals(" "))
            continue;
          if(((JComboBox)component).getSelectedItem().equals("true"))
           temp = "true";
         else
           temp = "false";
        }
        else
          if(model.getAttType().equalsIgnoreCase("String"))
          {
            HashMap map = model.getFeature();
            String refType = (String)map.get("RefType");
            if(refType==null)
              temp = ((JTextField)component).getText();
            else
              if(refType.equalsIgnoreCase("EnumerateType"))
              {
                temp = (String)((JComboBox)component).getSelectedItem();
              }else
                if(refType.equalsIgnoreCase("TextArea"))
                {
                  temp = ((QMTextArea)component).getText();
                }else
              if(refType.equalsIgnoreCase("three-dimension"))
                {
                  JPanel panel =(JPanel)component;
                  JTextField tf = ((JTextField)panel.getComponent(0));
                  JTextField tf1 = ((JTextField)panel.getComponent(2));
                  JTextField tf2 = ((JTextField)panel.getComponent(4));
                     temp = tf.getText()+"*"+tf1.getText()+"*"+tf2.getText();
                }else
                if(refType.equalsIgnoreCase("ComboAtts"))
                {
                  JPanel panel =(JPanel)component;
                  if(((String)((JComboBox)panel.getComponent(1)).getSelectedItem()).equals(" "))
                  {
                    if (((JTextField) panel.getComponent(0)).getText().trim() != null&&!((JTextField) panel.getComponent(0)).getText().trim().equals(""))
                      temp = "null";
                    else
                      continue;
                  }
                  else
                  if(((JTextField)panel.getComponent(0)).getText().equals(""))
                     temp = "0"+";"+(String)((JComboBox)panel.getComponent(1)).getSelectedItem();
                  else
                  temp = ((JTextField)panel.getComponent(0)).getText()+";"+(String)((JComboBox)panel.getComponent(1)).getSelectedItem();
                }else
                if(refType.equals("Date"))
                {
                    temp = ((CalendarSelectedPanel)component).getDate();
                 }
                  else
                  {
                    temp = ((JTextField)component).getText();
                  }
          }
          else
            temp = ((JTextField)component).getText();
          boolean check = checkValidity(model, temp, model.getAttDisplay());
          if(!check)
            return false;
    }
    //检查成组属性

    if(this.isGroup)
   {
    	Iterator names = container.getAttGroupNames().iterator();
    	String name;
    	GroupPanel gpanel ;
    	while(names.hasNext()){
    	name = (String)names.next();
    	gpanel = (GroupPanel)groupMaps.get(name);
    if(!gpanel.check())
      return false;
   }
   }

   //检查控制计划
   if(this.isControlPlan)
  {
   if(!this.controlPlanPanel.check())
     return false;
   }
    return true;
  }

  /**
   * 编辑模式时，得到附加属性的值
   * @return ExtendAttContainer 附加属性容器
   */
  public ExtendAttContainer getExAttr()
  {
    if(container==null)
      return null;
    for(int i = 0;i<container.getAttCount();i++)
    {
      String temp = null;
      ExtendAttModel model = container.getAttributeAt(i);
      Component component = (Component)this.componentsMap.get(model.getAttName());
      if (model.getAttType().equalsIgnoreCase("SpecChar"))
      {
               Vector vector = new Vector();
               vector.addElement(((SpeCharaterTextPanel)component).save());
               model.setAttValue(vector);
               //model.setAttValue(vector);
     }else
     {
         if (model.getAttType().equalsIgnoreCase("EnumeratedType"))
         {
             temp = (String) ((JComboBox) component).getSelectedItem();
         }
         else
         if (model.getAttType().equalsIgnoreCase("Coding"))
         {
             temp = (String) ((JComboBox) component).getSelectedItem();
             if (temp.equals(" "))
                 continue;
             if (VERBOSE)
                 System.out.println("temp== " + temp);
         }
         else
         if (model.getAttType().equalsIgnoreCase("Boolean"))
         {
             //2007.10.26 徐春英修改  修改原因:boolean型的扩展属性保存时无论选择true还是false保存后都是false
             String item = ((JComboBox) component).getSelectedItem().toString();
             if (item.equals(" "))
                 continue;
             if (item.equals("true"))
             {
                 temp = "true";
             }
             else
             {
                 temp = "false";
             }
         }
         else
         if (model.getAttType().equalsIgnoreCase("String"))
         {
             HashMap map = model.getFeature();
             String refType = (String) map.get("RefType");
             if (refType == null)
                 temp = ((JTextField) component).getText();
             else
             if (refType.equalsIgnoreCase("TextArea"))
             {
                 temp = ((QMTextArea) component).getText();
             }
             else
             if (refType.equalsIgnoreCase("Date"))
             {
                 temp = ((CalendarSelectedPanel) component).getDate();
             }
             else
             if (refType.equalsIgnoreCase("EnumerateType"))
             {
                 temp = (String) ((JComboBox) component).getSelectedItem();
             }
             else
             if (refType.equalsIgnoreCase("three-dimension"))
             {
                 JPanel panel = (JPanel) component;
                 JTextField tf = ((JTextField) panel.getComponent(0));
                 JTextField tf1 = ((JTextField) panel.getComponent(2));
                 JTextField tf2 = ((JTextField) panel.getComponent(4));
                 temp = tf.getText() + "*" + tf1.getText() + "*" +
                        tf2.getText();
             }
             else
             if (refType.equalsIgnoreCase("ComboAtts"))
             {
                 JPanel panel = (JPanel) component;
                if (((String) ((JComboBox) panel.getComponent(1)).
                      getSelectedItem()).equals(" ")){
                	//add by wangh on 200803019 在项目中未填值时设置空值,避免将上次填写的值带入本次操作
                	model.setAttValue(null);
                	//add end
                     continue;
                }
                 if (((JTextField) panel.getComponent(0)).getText().
                     equals("")){
                     temp = "0" + ";" +
                            (String) ((JComboBox) panel.getComponent(1)).
                            getSelectedItem();
                 }
                 else{
                     temp = ((JTextField) panel.getComponent(0)).getText() +
                            ";" +
                            (String) ((JComboBox) panel.getComponent(1)).
                            getSelectedItem();
                }
             }
             else
                 temp = ((JTextField) component).getText();
         }
         else
             temp = ((JTextField) component).getText();
         boolean check = checkValidity(model, temp, model.getAttDisplay());
         if (check)
         {
             if (temp != null)
                 if (temp.trim().equals(""))
                 {
                     if (hasDefault)
                     {
                         temp = model.getAttDefault();
                         hasDefault = false;
                     }
                     else
                     {
                         temp = null;
                     }
                 }
         }
         else
         {
             return null;
         }
         Object value = null;
         if (temp != null && !temp.trim().equals(""))
             value = change(model, temp);
         model.setAttValue(value);
     }

    }
    if(this.isGroup)
    {
    	Iterator names = container.getAttGroupNames().iterator();
    	String name;
    	GroupPanel gp;
    	this.container.removeAllGroupAtts();
    	while(names.hasNext()){
    	name = (String)names.next();
    	gp = (GroupPanel)this.groupMaps.get(name);
      Vector vec =gp.getExAttr();
      for(int i=0;i<vec.size();i++){
        this.container.addAttGroup((ExtendAttGroup)vec.elementAt(i));
      }
      }
    }
    if(this.isControlPlan)
   {
     Vector vec = this.controlPlanPanel.getExAttr();
     this.container.removeAllPlanGroupAtts();
     for(int i=0;i<vec.size();i++)
       this.container.addPlanGroup((ExtendAttGroup)vec.elementAt(i));
    }
    return container;
  }

  /**
   *<p>工具方法
   *<p>判断新建的属性值是否满足约束,并弹出提示框
   *@param rstc 属性定义，用于获得约束条件
   *@param text 属性值，用于判断
   *@param defName 属性名称，提示用
   *@return boolean  属性值是否满足约束,true为满足
   *@see ExtendAttModel
   */
  boolean checkValidity(ExtendAttModel model,String text,String defName)
  {
    String rstcType = model.getAttType();
    String defaultValue;

    if(!rstcType.equalsIgnoreCase("EnumeratedType"))
    {
      String msg = null;
      boolean mayNull = model.getAllowNull();
      defaultValue = model.getAttDefault();
      String maxStr =model.getMaxValue();
      String minStr = model.getMinValue();
      if (text.trim().equalsIgnoreCase(""))
      {
        if (mayNull) {
          if (defaultValue != null)
          {
            //设者缺省值
            hasDefault = true;
            return true;
          }
          return true;
        }else
        if(this.model==this.EDIT_MODEL)
        {
          handleNull(defName);
          return false;
        }
        }else if (rstcType.equalsIgnoreCase("int"))
        {
          int maxV = 0 ;
          int minV = 0;
          if(maxStr!=null)
              maxV = new Integer((String)maxStr).intValue();
           else
              maxV = 2147483647;
         if(minStr!=null)
               minV = new Integer((String)minStr).intValue();
          int textVal = 0;
          if(text.length()>10)
          {
               handleOutofBoundary(defName,new Integer(minV).toString(),new Integer(maxV).toString());
               return false;
          } //endif
          try {
            textVal = Integer.parseInt(text);
          }catch (NumberFormatException e) {
            handleWrongType(defName,"整数");
            return false;
          }
          if (textVal > maxV || textVal < minV) {
            handleOutofBoundary(defName,new Integer(minV).toString(),new Integer(maxV).toString());
            return false;
          } //endif
        }else if (rstcType.equalsIgnoreCase("double")) {
          double maxV =0;
          double minV=0;
          if(maxStr!=null)
             maxV = new Double((String)maxStr).doubleValue();
        if(minStr!=null)
              minV = new Integer((String)minStr).intValue();
          double doubleVal = 0;
          try {
            if(text.equals("0d")||text.equals("0f"))
               text = text+"a";
            doubleVal = Double.parseDouble(text);
          }catch (NumberFormatException e) {
            handleWrongType(defName,"小数");
            return false;
          }
          if(maxV !=0)
          if (doubleVal > maxV || doubleVal < minV) {
            handleOutofBoundary(defName,new Double(minV).toString(),new Double(maxV).toString());
            return false;
          } //endif
        }else if (rstcType.equalsIgnoreCase("float")) {
          float maxV =0;
         float minV=0;
         if(maxStr!=null)
           maxV = new Float((String)maxStr).floatValue();
         if(minStr!=null)
            minV = new Float((String)minStr).floatValue();
          float floatVal = 0;
          try {
            floatVal = Float.parseFloat(text);
          }catch (NumberFormatException e){
            handleWrongType(defName,"小数");
            return false;
          }
          if(maxV !=0)
          if (floatVal > maxV || floatVal < minV) {
            handleOutofBoundary(defName,new Float(minV).toString(),new Float(maxV).toString());
            return false;
          } //endif
        }else if (rstcType.equalsIgnoreCase("long")) {
          long maxV =0;
          long minV=0;
          if(maxStr!=null)
              maxV = new Long((String)maxStr).longValue();
          if(minStr!=null)
              minV = new Long((String)minStr).longValue();
          long longVal = 0;
          try {
            longVal = Long.parseLong(text);
          }catch (NumberFormatException e) {
            handleWrongType(defName,"小数");
            return false;
          }
        if(maxV !=0)
          if (longVal > maxV || longVal < minV) {
            handleOutofBoundary(defName,new Long(minV).toString(),new Long(maxV).toString());
            return false;
          } //endif
        }else if (rstcType.equalsIgnoreCase("string")){
          return checkStringValidity(model,text,defName);
        }
        else if (rstcType.equalsIgnoreCase("boolean")){
          if (!text.equalsIgnoreCase("true") && !text.equalsIgnoreCase("false")) {
            msg = getLocalizedMessage("83", null) + defName +
                  getLocalizedMessage("86", null);
            String title = this.getLocalizedMessage("exception", null);
            JOptionPane.showMessageDialog(this.getParentJComponent(),
                msg,
                title,
                JOptionPane.INFORMATION_MESSAGE);
            return false;
          }
        }

    }
    return true;
  }

  /**
   * 检查字符串的有效性
   * @param model 属性容器
   * @param text　字符值
   * @param defName　属性名
   * @return boolean 字符串的有效性,true为有效
   * @see ExtendAttModel
   */
  private boolean checkStringValidity(ExtendAttModel model,String text,String defName)
  {
    String rstcType = model.getAttType();
    String defaultValue;
    String msg = null;
    boolean mayNull = model.getAllowNull();
    defaultValue = model.getAttDefault();
    String maxStr =model.getMaxValue();
    String minStr = model.getMinValue();
    if (text.trim().equalsIgnoreCase(""))
    {
      if (mayNull) {
        if (defaultValue != null)
        {
          //设者缺省值
          hasDefault = true;
          return true;
        }
        return true;
      }else
      {
        handleNull(defName);
        return false;
      }
    }
    else{
      HashMap map = model.getFeature();
      String refType =(String)map.get("RefType");
      if(refType==null)
      {
        int length = text.length();
        int maxV = 0 ;
         int minV = 0;
         if(maxStr!=null)
             maxV = new Integer((String)maxStr).intValue();
          else
             maxV = 2147483647;
        if(minStr!=null)
              minV = new Integer((String)minStr).intValue();
        if (length > maxV || length < minV)
        {
          //modify by guoxl on 20080225(过程流程描述和变差来源输入的字符的限定应更改为>=0,<=50)
          msg = this.getLocalizedMessage("83", null) + minV
              + " <= " + defName + this.getLocalizedMessage("85", null) +
                " <= " + maxV;
            //modify end
          String title = this.getLocalizedMessage("exception", null);
          JOptionPane.showMessageDialog(this.getParentJComponent(),
                                        msg,
                                        title,
                                        JOptionPane.INFORMATION_MESSAGE);
          return false;
        }
        return true;
      }else
       if(refType.equalsIgnoreCase("three-dimension"))
       {
         String valueType = (String)map.get("ValueType");
         StringTokenizer ston = new StringTokenizer(text, "*");
         String[] valueStrs = new String[3];
         if(ston.hasMoreTokens())
            valueStrs[0] =ston.nextToken();
         if(ston.hasMoreTokens())
             valueStrs[1]  =ston.nextToken();
         if(ston.hasMoreTokens())
             valueStrs[2]  =ston.nextToken();
//       2008.03.26 刘志城修改 修改原因：附加信息页的供料尺寸属性第三个没有判断。应改为：三个值都进行判断。
         for(int i =0;i<=2;i++){
           String valueStr = valueStrs[i];
           if(valueStr==null)
             continue;
           if (valueType.equalsIgnoreCase("int")) {
             int maxV = 0 ;
            int minV = 0;
            if(maxStr!=null)
                 maxV = new Integer((String)maxStr).intValue();
             else
                   maxV = 2147483647;
          if(minStr!=null)
               minV = new Integer((String)minStr).intValue();

             int textVal = 0;
             try {
               textVal = Integer.parseInt(valueStr);
             }
             catch (NumberFormatException e) {
               handleWrongType(defName, "Integer");
               return false;
             }
             if (textVal > maxV || textVal < minV) {
               handleOutofBoundary(defName, new Integer(minV).toString(),new Integer(maxV).toString());
               return false;
             } //endif
           }
           else if (valueType.equalsIgnoreCase("double")) {
             double maxV =0;
             double minV=0;
             if(maxStr!=null)
               maxV = new Double((String)maxStr).doubleValue();
             if(minStr!=null)
             minV = new Double((String)minStr).intValue();
             double doubleVal = 0;
             try {
              if(valueStr.equals("0d")||valueStr.equals("0f"))
                  valueStr = valueStr+"a";
               doubleVal = Double.parseDouble(valueStr);
             }
             catch (NumberFormatException e) {
               handleWrongType(defName, "合法数字");
               return false;
             }
            if(maxV !=0)
             if (doubleVal > maxV || doubleVal < minV) {
               handleOutofBoundary(defName, new Double(minV).toString(), new Double(maxV).toString());
               return false;
             } //endif
           }
           else if (valueType.equalsIgnoreCase("float")) {
             float maxV =0;
             float minV=0;
             if(maxStr!=null)
                maxV = new Float((String)maxStr).floatValue();
            if(minStr!=null)
                minV = new Float((String)minStr).floatValue();
             float floatVal = 0;
             try {
               floatVal = Float.parseFloat(valueStr);
             }
             catch (NumberFormatException e) {
               handleWrongType(defName, "小数");
               return false;
             }
            if(maxV !=0)
             if (floatVal > maxV || floatVal < minV) {
               handleOutofBoundary(defName, new Float(minV).toString(), new Float(maxV).toString());
               return false;
             } //endif
           }
           else if (valueType.equalsIgnoreCase("long")) {
             long longVal = 0;
             long maxV =0;
             long minV=0;
             if(maxStr!=null)
                  maxV = new Long((String)maxStr).longValue();
             if(minStr!=null)
                  minV = new Long((String)minStr).longValue();

             try {
               longVal = Long.parseLong(valueStr);
             }
             catch (NumberFormatException e) {
               handleWrongType(defName, "小数");
               return false;
             }
           if(maxV !=0)
             if (longVal > maxV || longVal < minV) {
               handleOutofBoundary(defName, new Long(minV).toString(), new Long(maxV).toString());
               return false;
             }
           }
         }
       }
       else
        if(refType.equalsIgnoreCase("ComboAtts"))
        {
          String valueType = (String)map.get("ValueType");
          if(text.equals("null"))
          {
            String title = this.getLocalizedMessage("exception", null);
            JOptionPane.showMessageDialog(this.getParentJComponent(),
                                  "请选择“"+model.getAttDisplay()+"”的单位类型",
                                  title,
                                  JOptionPane.INFORMATION_MESSAGE);
             return false;
          }
          StringTokenizer ston = new StringTokenizer(text, ";");
          String valueStr =ston.nextToken();
          if (valueType.equalsIgnoreCase("int")) {
              int maxV = 0 ;
             int minV = 0;
             if(maxStr!=null)
                  maxV = new Integer((String)maxStr).intValue();
              else
                    maxV = 2147483647;
           if(minStr!=null)
                minV = new Integer((String)minStr).intValue();

              int textVal = 0;
              try {
                textVal = Integer.parseInt(valueStr);
              }
              catch (NumberFormatException e) {
                handleWrongType(defName, "Integer");
                return false;
              }
              if (textVal > maxV || textVal < minV) {
                handleOutofBoundary(defName, new Integer(minV).toString(),new Integer(maxV).toString());
                return false;
              } //endif
            }
            else if (valueType.equalsIgnoreCase("double")) {
              double maxV =0;
              double minV=0;
              if(maxStr!=null)
                maxV = new Double((String)maxStr).doubleValue();
              if(minStr!=null)
              minV = new Integer((String)minStr).intValue();
              double doubleVal = 0;
              try {
                if(valueStr.equals("0d")||valueStr.equals("0f"))
                   valueStr = valueStr+"a";
                doubleVal = Double.parseDouble(valueStr);
              }
              catch (NumberFormatException e) {
                handleWrongType(defName, "数值");//CR2
                return false;
              }
             if(maxV !=0)
              if (doubleVal > maxV || doubleVal < minV) {
                handleOutofBoundary(defName, new Double(minV).toString(), new Double(maxV).toString());
                return false;
              } //endif
            }
            else if (valueType.equalsIgnoreCase("float")) {
              float maxV =0;
              float minV=0;
              if(maxStr!=null)
                 maxV = new Float((String)maxStr).floatValue();
             if(minStr!=null)
                 minV = new Float((String)minStr).floatValue();
              float floatVal = 0;
              try {
                floatVal = Float.parseFloat(valueStr);
              }
              catch (NumberFormatException e) {
                handleWrongType(defName, "小数");
                return false;
              }
              if(maxV !=0)
              if (floatVal > maxV || floatVal < minV) {
                handleOutofBoundary(defName, new Float(minV).toString(), new Float(maxV).toString());
                return false;
              } //endif
            }
            else if (valueType.equalsIgnoreCase("long")) {
              long longVal = 0;
              long maxV =0;
              long minV=0;
              if(maxStr!=null)
                   maxV = new Long((String)maxStr).longValue();
              if(minStr!=null)
                   minV = new Long((String)minStr).longValue();

              try {
                longVal = Long.parseLong(valueStr);
              }
              catch (NumberFormatException e) {
                handleWrongType(defName, "小数");
                return false;
              }
            if(maxV !=0)
              if (longVal > maxV || longVal < minV) {
                handleOutofBoundary(defName, new Long(minV).toString(), new Long(maxV).toString());
                return false;
              }
            }
        }
    }
    return true;
  }

  /**
   * 处理null值
   * @param defName String 需要处理的属性名
   */
  public void handleNull(String defName)
  {
    String msg = this.getLocalizedMessage("82", null) +"“"+ defName +"”"+
                 this.getLocalizedMessage("84", null);
    String title = this.getLocalizedMessage("exception", null);
    JOptionPane.showMessageDialog(this.getParentJComponent(),
                                  msg,
                                  title,
                                  JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * 处理错误类型
   * @param defName String 需要处理的属性名
   * @param type String 需要处理的属性类型
   */
  public void handleWrongType(String defName,String type)
  {
    String msg = "“"+ defName +"”" + this.getLocalizedMessage("87", null) + type +
                 this.getLocalizedMessage("88", null);
    String title = this.getLocalizedMessage("exception", null);
    JOptionPane.showMessageDialog(this.getParentJComponent(),
                                  msg,
                                  title,
                                  JOptionPane.INFORMATION_MESSAGE);

  }

  /**
   * 处理越界
   * @param defName String 需要处理的属性名
   * @param min String 需要处理的属性的下界
   * @param max String 需要处理的属性的上界
   */
  private void handleOutofBoundary(String defName,String min,String max)
  {
    String title = this.getLocalizedMessage("exception", null);
    String msg = this.getLocalizedMessage("83", null) + min
               + "<=" + "“"+ defName +"”"+ "<=" + max;
    JOptionPane.showMessageDialog(this.getParentJComponent(),
                                  msg,
                                  title,
                                  JOptionPane.INFORMATION_MESSAGE);
  }
  /**
   设置工作模式
   @param model
   @roseuid 3F27100C00B1
   */
  public void setModel(int model)
  {
    this.model = model;
    if(this.model==this.VIEW_MODEL)
      this.setComponentEnable(false);
    else
      this.setComponentEnable(true);
        if(isGroup){
        Iterator names = container.getAttGroupNames().iterator();
    	String name;
    	while(names.hasNext()){
    	name = (String)names.next();
    	groupPanel = (GroupPanel)this.groupMaps.get(name);
          this.groupPanel.setModel(model);
    	}
        }
        if(this.isControlPlan)
          this.controlPlanPanel.setModel(model);
  }

  /**
   * 设置界面的编辑状态
   * @param flag 时候可编辑,true为可以
   */
      private void setComponentEnable(boolean flag)
      {
           if(container!=null)
            for(int i = 0 ; i<container.getAttCount() ;i++ )
            {
                 ((JComponent)valueCompnents.elementAt(i)).setEnabled(flag);
                  if(valueCompnents.elementAt(i) instanceof JPanel)
                  {
                    for(int j=0;j<((JPanel)valueCompnents.elementAt(i)).getComponentCount();j++)
                     ((JPanel)valueCompnents.elementAt(i)).getComponent(j).setEnabled(flag);
                   }
            }
      }
  /**
   得到工作模式
   @return int
   @roseuid 3F2710E20040
   */
  public int getModel()
  {
    return this.model;
  }



  /**
   * 获得带参数的本地信息
   * @param key   资源绑定信息的入口键值
   * @param params  替换资源信息中的参数的对象数组
   * @return String 本地信息。 如果键值key无效，则仅返回键值。
   */
  protected String getLocalizedMessage( String key, Object[] params )
  {
    //获得资源信息类
    ResourceBundle bundle = rb;
    String value = "";
    //如果资源信息类为空，则直接返回键值；否则根据键值和参数获得信息
    if ( bundle == null)
    {
      return key;
    }
    else
    {
      try
      {
        //如果没有信息参数，则根据键值直接从 ResourceBundle获得信息；否则
        //需把信息参数插入所获得的信息中
        if( params == null )
        {
          value = bundle.getString( key );
        }
        else
        {
          value = MessageFormat.format( bundle.getString( key ),params);
        }
      }
      catch ( MissingResourceException mre)
      {
        mre.printStackTrace();
        value = key;
      }
    }

    return value;
  }

  /**
   * 获得上级控件
   * @return Component 上级控件
   */
  public Component getParentJComponent()
  {

    Component parent = getParent();
    while (!(parent instanceof JFrame))
    {
      if(parent instanceof JDialog)
        return parent;
      parent = parent.getParent();
    }
    return parent;
  }
  void groupInputJDiolgInit()throws Exception{
	  this.setLayout(borderLayout1);
	  attrPanel.setLayout(gridBagLayout1);

	  if(isGroup&&editVec!=null){
	  Iterator names = container.getAttGroupNames().iterator();
      String name;
      JLabel nameLabel;


      Vector temp = (Vector)editVec.get(0);
      Vector temp1 = (Vector)editVec.get(1);
      Vector temp2 = (Vector)editVec.get(2);


      while(names.hasNext())
	      {
        name = (String)names.next();
    	groupPanel = new GroupPanel(container,name,false);

    	int num2=groupPanel.getColByAttname("风险顺序数(PRN)");
    	int num1=groupPanel.getColByAttname("估算的建议实施后PRN");

	    labelCompnents = new JLabel[temp.size()];
	    valueCompnents = new Vector();
	    panelZone = new JPanel[temp.size()];
	    nameLabel = new JLabel(name);
	    attrPanel.add(nameLabel,new GridBagConstraints(0, 0, 1, 1, 1.0, 0
    	         ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(8,8,8,8), 0, 0));
	    for(int i = 0 ; i<temp.size() ;i++ )
        {
	    	panelZone[i] = new JPanel();
            panelZone[i].setLayout(gridBagLayout1);
            labelCompnents[i] = new JLabel();
            labelCompnents[i].setText((String)temp.get(i));
            labelCompnents[i].setHorizontalAlignment(SwingConstants.LEFT);
            labelCompnents[i].setBorder(BorderFactory.createLineBorder(Color.black));
            labelCompnents[i].setMaximumSize(new Dimension(186,22));
            labelCompnents[i].setMinimumSize(new Dimension(186,22));
            labelCompnents[i].setPreferredSize(new Dimension(186,22));
            JComponent component = (JComponent)temp1.get(i);
            valueCompnents.add(component);
            if(i!=num1&&i!=num2){
            if(valueCompnents.get(i).getClass().toString().equalsIgnoreCase("class javax.swing.JTextField")){
            ((JTextField)valueCompnents.get(i)).setText((String)temp2.get(i));
            }
            //begin CR3
            else if(valueCompnents.get(i).getClass().toString().equalsIgnoreCase("class javax.swing.JComboBox")){
            ((JComboBox)valueCompnents.get(i)).setSelectedItem(temp2.get(i));
            }
            else if(valueCompnents.get(i).getClass().toString().equalsIgnoreCase("class javax.swing.JPanel")){
            	JPanel panel = (JPanel)valueCompnents.get(i);
            	String str = (String)temp2.get(i);
            	JTextField text= (JTextField)panel.getComponent(0);
            	text.setText(str.substring(0, str.indexOf(";")));
            	JComboBox comBox = (JComboBox)panel.getComponent(1);
            	comBox.setSelectedItem(str.substring(
						str.indexOf(";") + 1, str.length()));
            	
            }
            else if(valueCompnents.get(i).getClass().toString().equalsIgnoreCase("class com.faw_qm.jview.chrset.SpeCharaterTextPanel"))
            {
            	SpeCharaterTextPanel spePanel = (SpeCharaterTextPanel)valueCompnents.get(i);
            	spePanel.resumeData((Vector)temp2.get(i));
            }//end CR3
            }
            else{
            	//add by wangh on 20080618 在编辑界面中仍然正常显示数据,只是让该行不能编辑.
            	((JTextField)valueCompnents.get(i)).setText((String)temp2.get(i));
            	//add end
            	((JTextField)valueCompnents.get(i)).setEditable(false);
            }
            panelZone[i].add((JComponent)valueCompnents.get(i),new GridBagConstraints(1,i,1,1,100000.0,0.0,GridBagConstraints.EAST,
                  GridBagConstraints.HORIZONTAL,new Insets(4,4,4,4),0,0));
            panelZone[i].add(labelCompnents[i],new GridBagConstraints(0,i,1,1,1.0,0.0,GridBagConstraints.WEST,
                  GridBagConstraints.NONE,new Insets(4,4,4,4),0,0));

      	    attrPanel.add(panelZone[i],new GridBagConstraints(0, i+1, 1, 1, 1.0, 1.0
      	         ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(8,8,8,8), 0, 0));

      	    attrPanel.setVisible(true);



	    }
	    this.add(jScrollPane1, BorderLayout.CENTER);
	      jScrollPane1.setBorder(null);
	      jScrollPane1.getViewport().add(attrPanel, null);
	  }
	  return;
	  }

    }
    /**
     *获得成组属性所有属性的集合
     * @return Vector 成组属性所有属性的集合
     */
    public Vector reDate(){
	  rVec = new Vector();
	  int i = ((Vector)editVec.get(0)).size();
	  for(int j=0;j<i;j++){
		  if(valueCompnents.get(j).getClass().toString().equalsIgnoreCase("class javax.swing.JTextField")){
			  rVec.addElement(((JTextField)valueCompnents.get(j)).getText());
		  }
		  //begin CR3
		  else if(valueCompnents.get(j).getClass().toString().equalsIgnoreCase("class javax.swing.JComboBox"))
		  {
			  rVec.addElement(((JComboBox)valueCompnents.get(j)).getSelectedItem());
		  }
		  else if(valueCompnents.get(j).getClass().toString().equalsIgnoreCase("class javax.swing.JPanel"))
		  {
			  JPanel panel = (JPanel)valueCompnents.get(j);
          	  JTextField text= (JTextField)panel.getComponent(0);
          	  JComboBox comBox = (JComboBox)panel.getComponent(1);
          	  String[] attr = new String[2];
          	  attr[0] = text.getText();
          	  attr[1] = (String)comBox.getSelectedItem();
          	  rVec.addElement(attr);
          	  //rVec.addElement(comBox.getSelectedItem());
		  }
		  else if(valueCompnents.get(j).getClass().toString().equalsIgnoreCase("class com.faw_qm.jview.chrset.SpeCharaterTextPanel"))
		  {
			  SpeCharaterTextPanel speTextPanel = (SpeCharaterTextPanel)valueCompnents.get(j);
			  rVec.addElement(speTextPanel.save());
		  }//end CR3
	  }
	  return rVec;
  }
    //Begin CR4    
//    public void controlRM(boolean flag){
//        ComponentMultiList multiList = new ComponentMultiList();
//        multiList.setisShowPopupMenu(flag);
//    }
    //End CR4  
    //CCBegin by leixiao 2008-10-27 原因：解放系统升级  
    /**
     * 设置容器所属的工序，用于数据传递
     * @param ifc QMProcedureIfc
     */
    public void setProIfc(QMProcedureInfo ifc) {
      proifc = ifc;
      container.setProIfc(proifc);

    }

    /**
     *获得容器所属的工序，用于数据传递
     * @return QMProcedureIfc
     */
    public QMProcedureInfo getProIfc() {
      return proifc;
    }
  //  CCEnd by leixiao 2008-10-27 原因：解放系统升级 
    
//    CCBegin SS1
    public void setPartM(String iba){
    	if(iba.equals("")||iba == "")
    		return;
    	for(int i = 0;i<container.getAttCount();i++)
    	{
    		ExtendAttModel model = container.getAttributeAt(i);
    		if(model.getAttName().equals("PartMaterial")){
    			Component component = (Component)this.componentsMap.get(model.getAttName());
    			((JTextField) component).setText(iba);
    		}
    	}
    }
//    CCEnd SS1
    
  }

