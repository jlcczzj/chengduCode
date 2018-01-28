/** ���ɳ���ʱ�� 2003/08/11
  * �����ļ����� ExtendResource_en_US.java
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  * 
  * 
  * CR1 2009/06/02 ������   ԭ��:����չ���ԵĿɱ༭�����Ӹ���ճ���й���.
  * CR2 2009/06/04 �촺Ӣ  �μ�DefectTD=2200
  * CR3 2009/06/04 �촺Ӣ  �μ�DefectTD=2177
  * CR4 2009/06/09 ������  �μ�DefectID=2204       
  * CR5 2009/07/14 ������  �μ�DefectID=2472  
  * CR6 2010/01/04 ��־��  �μ�DefectID=2772   
  * CR7 2010/04/02 �촺Ӣ  �μ�TD����2245         
  * SS1 ��ݹ��������Ҫ���ʱ���������в����ƺž��Զ���ӵ����������Ϣ�� pante 2014-10-23
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
 * <p>Title: ��������panel</p>
* <p>Description:������ģʽ���༭ģʽ������ģʽ���鿴ģʽ
 * ��������ģʽ����query�������� </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company:faw_qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 * ���⣨1��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
 * SS1 ������������Ÿ����������Ͳ�ͬ���µ����װ���� xudezheng 20130726
 */
public class CappExAttrPanel extends JPanel
{

  //��Դ�ļ�
  ResourceBundle rb = ResourceBundle.getBundle("com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanelRB",RemoteProperty.getVersionLocale());
  //���Կ���
  private static boolean VERBOSE = true;
  static{
    VERBOSE = (new Boolean(RemoteProperty.getProperty("com.faw_qm.cappclients.verbose","true"))).booleanValue();
  }
  private Locale  local = RemoteProperty.getVersionLocale();

  //ö���໺��
  HashMap etMaps = new HashMap();

  //�����黺��
  HashMap groupMaps = new HashMap();
  /**
   �������Ե����ּ���
   */
  private Vector attriNames;

  /**
   �������Ե�ֵ����
   */
  private Vector attriValues;

  /**
   ��������ǩ����
   */
  private javax.swing.JLabel[] labelCompnents;

  /**
   ����ֵ�������
   */
  private Vector valueCompnents;

  private HashMap componentsMap = new HashMap();
  /**
   ����ʱ�������������
   */
  private javax.swing.JCheckBox[] conditionComponents;
  //������panel����
  private JPanel[] panelZone;
  /**
   ����ģʽ
   */
  private int model;

  /**
   ����ģʽ֮----�༭
   */
  public  static int EDIT_MODEL = 1;

  /**
   ����ģʽ֮----����
   */
  public static int SEARCH_MODEL = 2;

  /**
   ����ģʽ֮----�鿴
   */

  public static int VIEW_MODEL = 3;
  //�Ƿ���ȱʡ����ֵ
  private boolean hasDefault;

  //������
  private CappExAttrPanelDelegate delegate;

  //�Ƿ��г�������
  private boolean isGroup = false;

  //�Ƿ��п��Ƽƻ�
  boolean isControlPlan = false;
  /**
   * ������������
   */
  private ExtendAttContainer container;

  private JScrollPane jScrollPane1 = new JScrollPane();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();

  private 	ArrayList editVec = new ArrayList();

  /**
   * ���������ް�
   * ���⣨1��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
   */
  public GroupPanel groupPanel;

  /**
   * ���Ƽƻ����
   */
  GroupPanel controlPlanPanel;

  //��������panel
  private JPanel attrPanel = new JPanel();

 // ��������λ��
  private  int activeAttr = -1;

  //��������λ��
  private  int passiveAttr = -1;

  private BorderLayout borderLayout1 = new BorderLayout();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private static boolean verbose = RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true").equals("true"); //debug��ʶ
  private static boolean ts16949 = (RemoteProperty.getProperty(
	        "TS16949", "true")).equals("true");

  //add by wangh on 20070717
  public Vector rVec;
  //CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ����,�������ݴ���
  private QMProcedureInfo proifc = null;
  
  public static boolean isprocedure = true;
  //CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
  

  /**
   *�����캯��
   */
  public CappExAttrPanel()
  {

  }

  /**
   ���캯��
   @param bsoName
   @bsoName: ��������ҵ���������
   ���ݴ����Ƶõ���ǰ��ĸ������Է���
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
    * �����ӵ�����
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
   * �����ʼ����jbuilder�̶�����
   * @throws Exception
   */
  void jbInit() throws Exception {

    this.setLayout(borderLayout1);
    attrPanel.setLayout(gridBagLayout1);
    if(this.isControlPlan)
    {
      this.add(jTabbedPane1,BorderLayout.CENTER);
      if(valueCompnents.size()>0)
        jTabbedPane1.add(jScrollPane1, "��չ����");
      jTabbedPane1.add(controlPlanPanel,"���Ƽƻ�");
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
   * ����������ԣ�������������ֵ�ı�ʱ����������Ҳ�����������Ը���
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
   * ��ʼ���������
   */
  private void prepareValues(){
    int size = container.getAttCount();
    labelCompnents = new JLabel[size];
    valueCompnents = new Vector();
    panelZone = new JPanel[size];

    //��ʼ����ͨ����
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
    //��ʼ����������
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
        if (name.equals("���Կ���") ||name.equals("����FMEA")||name.equals("���Ƽƻ�")) { //Begin CR1
            //����������
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
    //��ʼ�����Ƽƻ�
    if(this.isControlPlan)
    {
      this.controlPlanPanel = new GroupPanel(container,"ControlPlan",true);
      controlPlanPanel.setParentPanel(this);
    }
  }
  
  

  /**
   * ���ݸ�������ģ�͵õ���ʾֵ�Ŀؼ�
   * @param model�����Է�װ��
   * @return��JComponent ����ؼ�
   * @see ExtendAttModel
   */
  public JComponent getCompoment(ExtendAttModel model)
  {
    String type = model.getAttType();
     //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2772
    String groupName = model.getGroupName();//CR6
     //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2772
    //�������Ϊö�����ͣ����������б��������ı���
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
    // ��������Ϊ������Ķ����ݡ�
      if(type.equals("Coding"))
      {
         HashMap map = model.getFeature();
         String defaultValue = model.getAttDefault();
         boolean isValid = false;
         try{
         String  sortType =(String)map.get("SortType");
           if(VERBOSE)
             System.out.println("sortType::��"+sortType);
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
                    	//�����para1��para2��ֵ��Ϊ���ղ���TS16949����½����򲻳��ֿ�ָ��,��ֵ��Ϊ����������κ�
                        //��Ts16949�Ĵ������ṹ��ͬ�������������.
                    	para1="��ͼ����";
                    	para2="���ռ�ͼ";
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
                 //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2772
                 if(groupName==null || !groupName.equals("snv"))//CR6
                 //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2772
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
    	  //CCBegin by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415    
        SpeCharaterTextPanel speCharPanel = new SpeCharaterTextPanel(new JFrame(),false);//this.getParentFrame());
          //CCEnd by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415    
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
          //���������ַ����ӵ����ԣ���ʽ�磺��a*b*c
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
          //�����������ԣ������ʽ�ǣ�������ֵ����λ
            if(refType.equals("ComboAtts"))
            {
              try{
                String  sortType =(String)map.get("SortType");
                StringTokenizer ston = new StringTokenizer(sortType, ";");
                String para1 = ston.nextToken();
                if(VERBOSE)
                   System.out.println("para1��������"+para1);
                String para2 = ston.nextToken();
                if(VERBOSE)
                   System.out.println("para2��������"+para2);
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
                //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2772
                if(groupName==null || !groupName.equals("snv"))//CR6
                combox.addItem(" ");//CR5
                //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2772
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
         *��Bean�Ĺ��캯��
         *@return JFrame ʹ�ø�Bean�Ĵ���
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
         *������ʾ��չ����
         * @param container1 ExtendAttContainer ��չ��������
         * @see ExtendAttContainer
         */
       // 20070205Ѧ���޸�

        public void show(ExtendAttContainer container1)
        {
            if (container == null)
                return;
            if (container1 == null)
                return;
            if (VERBOSE)
            {
                System.out.println("������Ĵ�С������" + container1.getAttGroupCount());
                System.out.println("���ԵĴ�С������" + container1.getAttCount());
            }

            //������ʾ��ͨ����
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
                                    System.out.println("model.getAttName()��������" +
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

            //��ʾ��������
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

            //��ʾ���Ƽƻ�
            if (this.isControlPlan)
                this.controlPlanPanel.show(container1, "ControlPane");

        }

  /**
   * ��ʾĳҵ�����ĸ�������
   * @param info ��չ����ֵ����
   * @see ExtendAttriedInfo
   */
  public void show(ExtendAttriedIfc info)
  {
    ExtendAttContainer container1 = info.getExtendAttributes();
    show(container1);
//    //�����������Ϊ�գ�����
//    if(container==null)
//      return;
//    if(container1==null)
//      return;
//   if(VERBOSE)
//    {
//      System.out.println("������Ĵ�С������"+container1.getAttGroupCount());
//      System.out.println("���ԵĴ�С������"+container1.getAttCount());
//    }
//
//    //������ʾ��ͨ����
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
//                      System.out.println("model.getAttName()��������"+model.getAttName());
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
//    //��ʾ��������
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
//    //��ʾ���Ƽƻ�
//    if(this.isControlPlan)
//      this.controlPlanPanel.show(info,"ControlPane");
  }

  /**
   *������ģʽʱ���õ�����ĸ������Բ�ѯ����
   * @return HashMap ���������ǵ�������ʱ,������������Ϊ��,�ַ�ֵתΪ�����������Ͷ�Ӧ��ֵ
   * Ϊֵ��HashMap.���������ǳ�������ʱ,��������ͨ��������˵��������������ɵ��ַ���Ϊ��,
   * ���������ļ���Ϊֵ��HashMap
   */
  public HashMap getCondition()
  {
    String temp;
    HashMap conditions = new HashMap();
    //������ͨ���ԣ���������
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
             //2007.10.26 �촺Ӣ�޸�  �޸�ԭ��:��boolean����չ��������ҵ�����ʱ�������
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
                 //����������͵���ֵ��û��ֵ�������Ϊ����
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

                 //�ַ�������Ϊ��
                 if (temp.equalsIgnoreCase(""))
                 {
                     temp = null;
                 }
             String name = model.getAttName();
             if (temp != null && temp.trim() != "")
             {
                 //תΪֵ���ȴ�����������ٶ�
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
    //�õ��������Ե�����
    if(this.isGroup){
    	Iterator names = container.getAttGroupNames().iterator();
    	String name;
    	while(names.hasNext()){
    	name = (String)names.next();
    	groupPanel = (GroupPanel)this.groupMaps.get(name);
      conditions = this.groupPanel.getCondition(conditions);
    	}
    }
      //�õ����Ƽƻ���������
    if(this.isControlPlan)
      conditions = this.controlPlanPanel.getCondition(conditions);
    return conditions;
  }


  /**
   * ���ַ�ֵתΪ�����������Ͷ�Ӧ��ֵ
   * @param model�����Է�װ��
   * @param text��������ֵ���ַ�ֵ
   * @return��Object ת����ֵ
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
   * ��Ч�Լ��
   * @return boolean
   */
  public  boolean check()
  {
    if(container==null)
      return true;

   //������ͨ���ԣ����ݾ��������ж����Ƿ�����Լ��
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
    //����������

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

   //�����Ƽƻ�
   if(this.isControlPlan)
  {
   if(!this.controlPlanPanel.check())
     return false;
   }
    return true;
  }

  /**
   * �༭ģʽʱ���õ��������Ե�ֵ
   * @return ExtendAttContainer ������������
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
             //2007.10.26 �촺Ӣ�޸�  �޸�ԭ��:boolean�͵���չ���Ա���ʱ����ѡ��true����false�������false
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
                	//add by wangh on 200803019 ����Ŀ��δ��ֵʱ���ÿ�ֵ,���⽫�ϴ���д��ֵ���뱾�β���
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
   *<p>���߷���
   *<p>�ж��½�������ֵ�Ƿ�����Լ��,��������ʾ��
   *@param rstc ���Զ��壬���ڻ��Լ������
   *@param text ����ֵ�������ж�
   *@param defName �������ƣ���ʾ��
   *@return boolean  ����ֵ�Ƿ�����Լ��,trueΪ����
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
            //����ȱʡֵ
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
            handleWrongType(defName,"����");
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
            handleWrongType(defName,"С��");
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
            handleWrongType(defName,"С��");
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
            handleWrongType(defName,"С��");
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
   * ����ַ�������Ч��
   * @param model ��������
   * @param text���ַ�ֵ
   * @param defName��������
   * @return boolean �ַ�������Ч��,trueΪ��Ч
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
          //����ȱʡֵ
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
          //modify by guoxl on 20080225(�������������ͱ����Դ������ַ����޶�Ӧ����Ϊ>=0,<=50)
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
//       2008.03.26 ��־���޸� �޸�ԭ�򣺸�����Ϣҳ�Ĺ��ϳߴ����Ե�����û���жϡ�Ӧ��Ϊ������ֵ�������жϡ�
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
               handleWrongType(defName, "�Ϸ�����");
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
               handleWrongType(defName, "С��");
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
               handleWrongType(defName, "С��");
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
                                  "��ѡ��"+model.getAttDisplay()+"���ĵ�λ����",
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
                handleWrongType(defName, "��ֵ");//CR2
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
                handleWrongType(defName, "С��");
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
                handleWrongType(defName, "С��");
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
   * ����nullֵ
   * @param defName String ��Ҫ�����������
   */
  public void handleNull(String defName)
  {
    String msg = this.getLocalizedMessage("82", null) +"��"+ defName +"��"+
                 this.getLocalizedMessage("84", null);
    String title = this.getLocalizedMessage("exception", null);
    JOptionPane.showMessageDialog(this.getParentJComponent(),
                                  msg,
                                  title,
                                  JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * �����������
   * @param defName String ��Ҫ�����������
   * @param type String ��Ҫ�������������
   */
  public void handleWrongType(String defName,String type)
  {
    String msg = "��"+ defName +"��" + this.getLocalizedMessage("87", null) + type +
                 this.getLocalizedMessage("88", null);
    String title = this.getLocalizedMessage("exception", null);
    JOptionPane.showMessageDialog(this.getParentJComponent(),
                                  msg,
                                  title,
                                  JOptionPane.INFORMATION_MESSAGE);

  }

  /**
   * ����Խ��
   * @param defName String ��Ҫ�����������
   * @param min String ��Ҫ��������Ե��½�
   * @param max String ��Ҫ��������Ե��Ͻ�
   */
  private void handleOutofBoundary(String defName,String min,String max)
  {
    String title = this.getLocalizedMessage("exception", null);
    String msg = this.getLocalizedMessage("83", null) + min
               + "<=" + "��"+ defName +"��"+ "<=" + max;
    JOptionPane.showMessageDialog(this.getParentJComponent(),
                                  msg,
                                  title,
                                  JOptionPane.INFORMATION_MESSAGE);
  }
  /**
   ���ù���ģʽ
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
   * ���ý���ı༭״̬
   * @param flag ʱ��ɱ༭,trueΪ����
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
   �õ�����ģʽ
   @return int
   @roseuid 3F2710E20040
   */
  public int getModel()
  {
    return this.model;
  }



  /**
   * ��ô������ı�����Ϣ
   * @param key   ��Դ����Ϣ����ڼ�ֵ
   * @param params  �滻��Դ��Ϣ�еĲ����Ķ�������
   * @return String ������Ϣ�� �����ֵkey��Ч��������ؼ�ֵ��
   */
  protected String getLocalizedMessage( String key, Object[] params )
  {
    //�����Դ��Ϣ��
    ResourceBundle bundle = rb;
    String value = "";
    //�����Դ��Ϣ��Ϊ�գ���ֱ�ӷ��ؼ�ֵ��������ݼ�ֵ�Ͳ��������Ϣ
    if ( bundle == null)
    {
      return key;
    }
    else
    {
      try
      {
        //���û����Ϣ����������ݼ�ֱֵ�Ӵ� ResourceBundle�����Ϣ������
        //�����Ϣ������������õ���Ϣ��
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
   * ����ϼ��ؼ�
   * @return Component �ϼ��ؼ�
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

    	int num2=groupPanel.getColByAttname("����˳����(PRN)");
    	int num1=groupPanel.getColByAttname("����Ľ���ʵʩ��PRN");

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
            	//add by wangh on 20080618 �ڱ༭��������Ȼ������ʾ����,ֻ���ø��в��ܱ༭.
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
     *��ó��������������Եļ���
     * @return Vector ���������������Եļ���
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
    //CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ����  
    /**
     * �������������Ĺ����������ݴ���
     * @param ifc QMProcedureIfc
     */
    public void setProIfc(QMProcedureInfo ifc) {
      proifc = ifc;
      container.setProIfc(proifc);

    }

    /**
     *������������Ĺ����������ݴ���
     * @return QMProcedureIfc
     */
    public QMProcedureInfo getProIfc() {
      return proifc;
    }
  //  CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
    
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

