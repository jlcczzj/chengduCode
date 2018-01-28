package com.faw_qm.cappclients.beans.cappexattrpanel;
/**
 *SS1 郭晓亮 2014-05-05 创建工序的时候，PEMEA页中风险顺序数PRN=严重度S*频度O*探测度D措施结果PRN=措施结果S*措施结果O*措施结果D 
 *SS2 郭晓亮 2014-05-05 创建工序过程流程也特性分类显示不正确
 *SS3 郭晓亮 2014-05-05 创建工序控制计划页，输入评价/测量技术的数据，系统提示材料不存在；
 *SS4 郭晓亮 2014-05-19 修改服务平台问题，问题编号：A034-2014-0079。
 *SS5 郭晓亮 2014-06-27 修改服务平台问题A034-2014-0063
 *SS6 郭晓亮 2014-07-04 修改服务平台问题A034-2014-0109
 *SS7 修改轴齿反应计划不折行问题，原意是为了防止下拉菜单出问题，所以遇到下拉菜单列就不折行 pante 2014-10-20
 *SS8 轴齿需求：量检具添加在控制计划的"评价测量技术“中 pante 2014-10-21
 *SS9 轴齿控制计划搜索工装时增加扩展属性搜索 pante 2014-11-11
 *SS10 轴齿需求：设备只在控制计划中维护，在过程流程中通过按钮把控制计划的设备信息传递过来，传递规则为：控
 *		制计划中所有已保存过的设备关联信息，按照控制计划中的顺序从过程流程中第一行开始逐次向下排列，如果
 *		过程流程中已有数据，则覆盖。 pante 2014-11-12
 *SS11 找不到配置文件属性 pante 2014-11-18
 *SS12 平台问题A034-2015-0228，材料带出的属性“材料规格”改为“材料牌号” liunan 2015-3-31
 *SS13 设备手动去掉后没有同步删除掉工序关联的设备。 liunan 2015-5-14
 *SS14 工装“-”删除后，工序关联的工装没有去掉。 liunan 2015-6-1
 *SS15 A004-2016-3455 界面维护调整：
       1、工艺维护界面内，插入新行或删除某行时，将工装和工装数量左侧的列都跟随增加或减少改成不跟随增加或减少（尾行除外）
       2、工艺维护界面内，某个单元格需要折行时，将只单列折行其他行不跟着动改成多行同时折行（不含工装和工装数量左侧的列）
       3、删除设备列某一行内容，其他列跟着减少
       liunan 2017-2-16
 */
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.view.TParamJDialog;
import com.faw_qm.cappclients.resource.view.ResourceDialog;
import com.faw_qm.cappclients.util.CappTextAndImageCell;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.extend.model.ExtendAttriedIfc;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.extend.util.ExtendAttGroup;
import com.faw_qm.extend.util.ExtendAttModel;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.jview.chrset.DataSource;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;
import com.faw_qm.print.clients.printModel.multiLine.MultiLineController;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMToolInfo;

public class GroupPanelForZC extends JPanel{

	
	 private JScrollPane jScrollPane1 = new JScrollPane();
	    
	    
	    private static ResourceBundle resource = null;

	    //属性显示的表格
	    public ComponentMultiList multiList = new ComponentMultiList();
	    
	    
	    
	    //public CappExAttrPanel processFlowJPanel;
	    //问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
	    //过程流程multilist
	    public  ComponentMultiList flowMultiList = new ComponentMultiList();
	    
	    

	    //添加按钮
	    private JButton addButton = new JButton();

	    //删除按钮
	    private JButton deleteButton = new JButton();
	    private Locale local = RemoteProperty.getVersionLocale();
	    private GridBagLayout gridBagLayout1 = new GridBagLayout();
	    

	    //属性组
	    ExtendAttGroup group;
	    //属性组所属于的容器
	    ExtendAttContainer container;
	    //属性组的大小
	    int groupSize;

	    //是否是控制计划
	    boolean isControlPlan = false;
	    //20070321 xuejing add
	    /**
	     * 需计算的列数
	     */
	    //private ArrayList calculateCols;
	    //父界面－－扩展属性总界面
	    CappExAttrPanelForZC parentPanel;
	    //枚举类欢存
	    HashMap etMaps = new HashMap();
	    //是否有缺省属性值
	    private boolean hasDefault;
	    static boolean VERBOSE = new Boolean(RemoteProperty.getProperty(
	            "com.faw_qm.extend.verbose", "true")).booleanValue();
	    private int[] rds;
	    private static boolean verbose = RemoteProperty.getProperty(
	            "com.faw_qm.cappclients.verbose", "true").equals("true");
	    /**
	     * 20070321薛静添加，计算按钮
	     */
	    private JButton calculateButton = new JButton(); //debug标识
	    //CCBegin SS4
	    
	    private JLabel eqequipLabel = new JLabel(); 
	    private JLabel toolLabel=new JLabel();
	    private JLabel materialLabel=new JLabel();
	    private JButton eqequipDecreaseButton = new JButton(); 
	    private JButton toolDecreaseButton = new JButton(); 
	    private JButton materialDecreaseButton = new JButton(); 
	    //CCEnd SS4
	    
	    //设备
	    private JButton eqequipButton = new JButton(); 
	    //工装
	    private JButton toolButton = new JButton(); 
	    //材料
	    private JButton materialButton = new JButton(); 
	    
	    
	    
	    
	    //add by wangh on 20070531
	       // CCBegin by leixiao 2008-10-27 原因：解放系统升级  继承按钮 实现过程流程,控制计划，过程FMEA的数据继承
	   //添加行与行复制按钮
	    //CCBegin SS6
	    public JButton inheritButton = new JButton();
	    //CCEnd SS6
	  //CCBegin SS10
	    public JButton EQInheritButton = new JButton();
	    //CCEnd SS10
	    private JButton copyAddButton = new JButton();
	    
	    private String groupname = "";
	  //  CCEnd by leixiao 2008-10-27 原因：解放系统升级 
	    protected static String RESOURCE
	    = "com.faw_qm.cappclients.capp.util.CappLMRB";
	    
	    private static String RESOURCEOther = "com.faw_qm.clients.beans.BeansRB";
	    
	    /**父窗口*/
	    private JFrame parentJFrame;

	    /**
	     * 20070627wangh添加,编辑按钮
	     */
	    private JButton editButton = new JButton();
	    //问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
	    private JButton attrButton = new JButton(); 
	    
	    public Vector eqDeleteVec=new Vector();
	    public Vector  materDeleteVec=new Vector();
	    public Vector toolDeleteVec=new Vector();
	    
	    /**父界面是否可以保存*/
	    private boolean canSave = true;
	    
	    /**
	     * 输入的设备编号
	     */
	    private String number = "";
	    
	    /**
	     * 构造函数
	     * @param container ExtendAttContainer　扩展属性容器
	     * @param groupName String　属性组名
	     * @param isControlPlan boolean　　是否是控制计划
	     * @see ExtendAttContainer
	     */
	    public GroupPanelForZC(ExtendAttContainer container, String groupName,
	                      boolean isControlPlan)
	    {
	  	    //CCBegin by leixiao 2008-10-27 原因：解放系统升级 
	        groupname = groupName;
	        //CCEnd by leixiao 2008-10-27 原因：解放系统升级 
	        System.out.println("groupName========11111111111111111==============================="+groupName);
	        try
	        {
	            this.container = container;
	            this.isControlPlan = isControlPlan;
	            //如果是不同组
	            if (!isControlPlan)
	            {
	                group = this.container.getAttGroupDes(groupName);
	                groupSize = group.getCount();
	            }
	            //如果是控制计划
	            else
	            {
	                group = this.container.getPlanGroupDes();
	                groupSize = group.getCount();
	            }
	            // this.multiList.setCheckModel(true);
	            String[] heads = new String[groupSize];
	            int[] colSize=new int[groupSize];
	            for (int i = 0; i < groupSize; i++)
	            {
	            	
	                ExtendAttModel model = group.getAttributeAt(i);

	                prepareDatas(model);
	                //设置标题
	                heads[i] = model.getAttDisplay();
	                
	                
	                
	                if(groupname.equals("特性控制")){
	                	
	                	    if(i<=11)
	    	                 colSize[i] =1;
	    	                else if(i>11&&i<=12)
	    	                 colSize[i] =0;
	    	                if(i>12)
	    	                  colSize[i] =1;
	                }
	                if(groupname.equals("控制计划")){
	                	
	                	   if(i<=14)
	    	                 colSize[i] =1;
	                	   //CCBegin SS4
	    	                else if(i>=15&&i<=17)
	    	                	//CCEnd SS4
	    	                  //colSize[i] =0;
	    	                  colSize[i] =1;//anan
	    	                if(i>17)
	    	                  colSize[i] =1;
	                	
	                }
	                
	                
	               
	                
	                
	            }

	            this.multiList.setHeadings(heads);
	            multiList.setRelColWidth(colSize);
	            //add by wangh on 20070518(可以配置成组属性中不可编辑的列)
	            //int r= multiList.getSelectedRow();
//	  		CCBegin by liuzc 2009-12-21 原因：解放升级,FMEA计算   参见TD号2685
	            int n=this.getColByAttname("措施结果PRN");
//	  		CCEnd by liuzc 2009-12-21 原因：解放升级,FMEA计算   参见TD号2685
	            int m=this.getColByAttname("风险顺序数(PRN)");
	            
	            int a=this.getColByAttname("设备编号/物料编码/材料编码/型号/规格/名称");
	            int b=this.getColByAttname("过程编号");
	            int c=this.getColByAttname("定额T分（分）");
	            int d=this.getColByAttname("定额T单（分）");
//	            int e=this.getColByAttname("设备编号");
	            int f=this.getColByAttname("设备型号");
	            int g=this.getColByAttname("设备名称");
	            

	            
	            multiList.setColsEnabled(new int[]{n,m,a,b,c,d,f,g}, false);
	            //defaultColumnWidths();
	            
	            multiList.getTable().getModel().addTableModelListener(new myTableModelListener());
	            multiList.addActionListener(new java.awt.event.
	                    ActionListener(){
	            	
	            	 public void actionPerformed(ActionEvent e)
	                 {
	                     extend_actionPerformed(e);
	                 }
	            });
	            jbInit();
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	        }
	    }


	    /**
	     * 根据具体的属性组织数据
	     * @param model ExtendAttModel　具体一个属性的封装类
	     * @see ExtendAttModel
	     */
	    private void prepareDatas(ExtendAttModel model)
	    {
	        HashMap map = model.getFeature();
	        if (model.getAttType().equals("EnumeratedType"))
	        {
	            String classPath = (String) map.get("classpath");
	            String newString = (String) map.get("newMethod");
	            try
	            {
	                if (VERBOSE)
	                {
	                    System.out.println("类型路径==  " + classPath);
	                }
	                Class class1 = Class.forName(classPath);
	                Method newMethod = class1.getMethod(newString, null);
	                EnumeratedType et1 = (EnumeratedType) newMethod.invoke(class1, null);
	                EnumeratedType[] ets = et1.getValueSet();
	                this.etMaps.put(classPath, ets);
	            }
	            catch (Exception e)
	            {
	                if(verbose)
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(this, e.getMessage());
	            }
	        }
	        else
	        if (model.getAttType().equals("Coding"))
	        {
	            try
	            {
	                String sortType = (String) map.get("SortType");
	                StringTokenizer ston = new StringTokenizer(sortType, ";");
	                String para1 = ston.nextToken();
	                if (VERBOSE)
	                {
	                    System.out.println("para1== " + para1);
	                }
	                String para2 = ston.nextToken();
	                if (VERBOSE)
	                {
	                    System.out.println("para2==  " + para2);
	                }
	                String para3 = ston.nextToken();
	                Collection sorts = CappTreeHelper.getCoding(para1, para2, para3);
	                this.etMaps.put(sortType, sorts);
	            }
	            catch (QMRemoteException e)
	            {
	                if(verbose)
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(this.getParentJFrame(),
	                                              e.getClientMessage());
	            }
	        }
	        else
	        if (model.getAttType().equals("String"))
	        {
	            String refType = (String) map.get("RefType");
	            if (refType == null)
	            {
	                return;
	            }
	            if (refType.equals("EnumerateType"))
	            {
	                String classPath = (String) map.get("classpath");
	                String newString = (String) map.get("newMethod");
	                try
	                {
	                    Class class1 = Class.forName(classPath);
	                    Method newMethod = class1.getMethod(newString, null);
	                    EnumeratedType et1 = (EnumeratedType) newMethod.invoke(
	                            class1, null);
	                    EnumeratedType[] ets = et1.getValueSet();
	                    this.etMaps.put(classPath, ets);
	                }
	                catch (Exception e)
	                {
	                    if(verbose)
	                    e.printStackTrace();
	                    JOptionPane.showMessageDialog(this.getParent(),
	                                                  e.getMessage());
	                }
	            }
	            else
	            if (refType.equals("ComboAtts"))
	            {
	                try
	                {
	                    String sortType = (String) map.get("SortType");
	                    StringTokenizer ston = new StringTokenizer(sortType, ";");
	                    String para1 = ston.nextToken();
	                    if (VERBOSE)
	                    {
	                        System.out.println("para1==  " + para1);
	                    }
	                    String para2 = ston.nextToken();
	                    if (VERBOSE)
	                    {
	                        System.out.println("para2==  " + para2);
	                    }
	                    String para3 = ston.nextToken();
	                    Collection sorts = CappTreeHelper.getCoding(para1, para2,
	                            para3);
	                    this.etMaps.put(sortType, sorts);

	                }
	                catch (QMRemoteException e)
	                {
	                      if(verbose)
	                    e.printStackTrace();
	                    JOptionPane.showMessageDialog(this.getParentJFrame(),
	                                                  e.getClientMessage());
	                }
	            }
	        }
	    }



	    /**
	     * 界面初始化
	     * @throws Exception
	     */
	    void jbInit()
	            throws Exception
	    {
	        this.setLayout(gridBagLayout1);
	        
	      //CCBegin SS4
	        addButton.setMaximumSize(new Dimension(110, 23));
	        addButton.setMinimumSize(new Dimension(110, 23));
	        addButton.setPreferredSize(new Dimension(110, 23));
	      //CCEnd SS4
	        addButton.setMnemonic('A');
	        addButton.setText("添加(A)");
	        addButton.addActionListener(new java.awt.event.ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                addButton_actionPerformed(e);
	            }
	        });

	        //CCBegin SS4
	        deleteButton.setMaximumSize(new Dimension(110, 23));
	        deleteButton.setMinimumSize(new Dimension(110, 23));
	        deleteButton.setPreferredSize(new Dimension(110, 23));
	        //CCEnd SS4
	        deleteButton.setMnemonic('D');
	        deleteButton.setText("删除(D)");
	        deleteButton.addActionListener(new java.awt.event.ActionListener()
	        {
	            public void actionPerformed(ActionEvent e)
	            {
	                deleteButton_actionPerformed(e);
	            }
	        });
	        //CCBegin SS4
	    calculateButton.setMaximumSize(new Dimension(110, 23));
	    calculateButton.setMinimumSize(new Dimension(110, 23));
	    calculateButton.setPreferredSize(new Dimension(110, 23));
	    //CCEnd SS4
	    calculateButton.setRolloverEnabled(true);
	    calculateButton.setText("计算");
	    calculateButton.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        calculateButton_actionPerformed(e);
	      }
	    });

	    
	    
	    //问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
	    //CCBegin by leixiao 2008-10-27 原因：解放系统升级 
	  //CCBegin SS4
	    inheritButton.setMaximumSize(new Dimension(110, 23));
	    inheritButton.setMinimumSize(new Dimension(110, 23));
	    inheritButton.setPreferredSize(new Dimension(110, 23));
	    //CCEnd SS4
	    inheritButton.setRolloverEnabled(true);
	    inheritButton.setText("传递");
	    //CCBegin SS6
//	    inheritButton.addActionListener(new java.awt.event.ActionListener() {
//	      public void actionPerformed(ActionEvent e) {
//	    	          inheritButton_actionPerformed(e);
//	      }
//	    });
	    
	    
	    //CCEnd SS6
	    
//	    CCBegin SS10
	    EQInheritButton.setMaximumSize(new Dimension(110, 23));
	    EQInheritButton.setMinimumSize(new Dimension(110, 23));
	    EQInheritButton.setPreferredSize(new Dimension(110, 23));
	    EQInheritButton.setRolloverEnabled(true);
	    EQInheritButton.setText("传递设备到过程流程");
	    EQInheritButton.addActionListener(new java.awt.event.ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		EQInheritButton_actionPerformed(e);
	    	}
	    });
//	    CCEnd SS10
	    
	    //CCEnd by leixiao 2008-10-27 原因：解放系统升级 
	    //CCBegin by leixiao 2009-7-2 原因：添加复制按钮
	    copyAddButton.setMaximumSize(new Dimension(80, 23));
	    copyAddButton.setMinimumSize(new Dimension(80, 23));
	    copyAddButton.setPreferredSize(new Dimension(80, 23));
	    copyAddButton.setText("复制");
	    copyAddButton.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	          copyAddButton_actionPerformed(e);
	      }
	    });
	    //add by wangh on 20070627
	    editButton.setMaximumSize(new Dimension(80, 23));
	    editButton.setMinimumSize(new Dimension(80, 23));
	    editButton.setPreferredSize(new Dimension(80, 23));
	    editButton.setRolloverEnabled(true);  
	    editButton.setText("编辑");
	    editButton.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	  editButton_actionPerformed(e);
	      }
	    });  
	    //CCEnd by leixiao 2009-7-2
	    
	    //CCBegin SS4
	    
	    eqequipLabel.setMaximumSize(new Dimension(25, 23));
	    eqequipLabel.setMinimumSize(new Dimension(25, 23));
	    eqequipLabel.setPreferredSize(new Dimension(25, 23));
	    eqequipLabel.setText("设备");
	    
	    eqequipButton.setMaximumSize(new Dimension(40, 23));
	    eqequipButton.setMinimumSize(new Dimension(40, 23));
	    eqequipButton.setPreferredSize(new Dimension(40, 23));
	    eqequipButton.setRolloverEnabled(true);
	    eqequipButton.setText("+");
	    eqequipButton.addActionListener(new java.awt.event.ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  eqequipButton_actionPerformed(e);
		      }
		    });
	    
	    eqequipDecreaseButton.setMaximumSize(new Dimension(40, 23));
	    eqequipDecreaseButton.setMinimumSize(new Dimension(40, 23));
	    eqequipDecreaseButton.setPreferredSize(new Dimension(40, 23));
	    eqequipDecreaseButton.setText("-");
	    
	    eqequipDecreaseButton.addActionListener(new java.awt.event.ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	eqequipDecreaseButton_actionPerformed(e);
	        }
	      });
	    
	 
	    
	    toolLabel.setMaximumSize(new Dimension(25, 23));
	    toolLabel.setMinimumSize(new Dimension(25, 23));
	    toolLabel.setPreferredSize(new Dimension(25, 23));
	    toolLabel.setText("工装");
	    
	    toolButton.setMaximumSize(new Dimension(40, 23));
	    toolButton.setMinimumSize(new Dimension(40, 23));
	    toolButton.setPreferredSize(new Dimension(40, 23));
	    toolButton.setText("+");
	    toolButton.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	  toolButton_actionPerformed(e);
	      }
	    });
	    
	    
	    toolDecreaseButton.setMaximumSize(new Dimension(40, 23));
	    toolDecreaseButton.setMinimumSize(new Dimension(40, 23));
	    toolDecreaseButton.setPreferredSize(new Dimension(40, 23));
	    toolDecreaseButton.setText("-");
	    toolDecreaseButton.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	  toolDecreaseButton_actionPerformed(e);
	      }
	    });
	    
	    
	    materialLabel.setMaximumSize(new Dimension(25, 23));
	    materialLabel.setMinimumSize(new Dimension(25, 23));
	    materialLabel.setPreferredSize(new Dimension(25, 23));
	    materialLabel.setText("材料");
	    
	    materialButton.setMaximumSize(new Dimension(40, 23));
	    materialButton.setMinimumSize(new Dimension(40, 23));
	    materialButton.setPreferredSize(new Dimension(40, 23));
	    materialButton.setRolloverEnabled(true);
	    materialButton.setText("+");
	    materialButton.addActionListener(new java.awt.event.ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	    	  materialButton_actionPerformed(e);
	      }
	    });

	    
	    materialDecreaseButton.setMaximumSize(new Dimension(40, 23));
	    materialDecreaseButton.setMinimumSize(new Dimension(40, 23));
	    materialDecreaseButton.setPreferredSize(new Dimension(40, 23));
	    materialDecreaseButton.setText("-");
	    
	    materialDecreaseButton.addActionListener(new java.awt.event.ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  materialDecreaseButton_actionPerformed(e);
		      }
		    });

	    
	    //CCEnd SS4
	    
	        this.add(jScrollPane1,  new GridBagConstraints(0, 0, 1, 7, 1.0, 1.0
	            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 113, 156));
//	        this.add(editButton,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
//	                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 10, 0, 8), 0, 0));
	        this.add(deleteButton,   new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
	        this.add(addButton,    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
	        
	        if(groupname.equals("过程FMEA"))
	          this.add(calculateButton,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
	        
	        // CCBegin by leixiao 2008-10-27 原因：解放系统升级 
	        
	        //CCBegin SS4
//	        CCBegin SS10
//	        if(!groupname.equals("过程FMEA")){
	        if(groupname.equals("控制计划")){
//		        CCEnd SS10
//	          this.add(eqequipButton,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
//	                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
	          
	        
	          this.add(eqequipLabel,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
	                  ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 0, 0, 70), 0, 0));
	          
	          this.add(eqequipButton,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
	                  ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 10), 0, 0));
	          
	          this.add(eqequipDecreaseButton,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
	                  ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 90, 0, 10), 0, 0));
	          
	          
	        }
	       
	        
//	        this.add(copyAddButton,     new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
//	              ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 8), 0, 0));
	        
	        
	       
	        
	        if(!groupname.equals("过程FMEA")&&!groupname.equals("特性控制")){
	        	
	        	 
	        	 this.add(toolLabel,     new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
	   	              ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 0, 0, 70), 0, 0));
	        	
	        	
	           this.add(toolButton,     new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
	              ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 10), 0, 0));
	           
	           
	           this.add(toolDecreaseButton,     new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
	 	              ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 90, 0, 10), 0, 0));
	        }
	        
	        
	        
	        if(!groupname.equals("过程FMEA")&&!groupname.equals("特性控制")){
	        	 this.add(materialLabel, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
	 	                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 0, 0, 70), 0, 0));
	        	 
	           this.add(materialButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
	                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 10), 0, 0));
	           
	           this.add(materialDecreaseButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
		                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 90, 0, 10), 0, 0));
	        
	         
	        }
	        this.add(inheritButton, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
	                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 8), 0, 0));
	        
	        //CCEnd SS4
//	        CCBegin SS10
	        if(groupname.equals("特性控制"))
	        	this.add(EQInheritButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
		                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 8), 0, 0));
//	        CCEnd SS10
	       
	            
	  // CCEnd by leixiao 2008-10-27 原因：解放系统升级 
	        jScrollPane1.getViewport().add(multiList, null);
	    }


	    /**
	     * 得到附加属性的搜索条件
	     * @param conditions　由扩展属性总界面传递过来的集合
	     * @return HashMap 由于现在的工艺不再使用以前的控制计划,所以只可能返回以普通成组属性说明和
	     * 属性组名组成的字符串为键,搜索条件的集合为值的HashMap
	     */
	    public HashMap getCondition(HashMap conditions)
	    {
	        ExtendAttGroup group;
	        Vector vec = new Vector();
	        for (int i = 0; i < multiList.getNumberOfRows(); i++)
	        {
	            group = this.group.duplicate();
	            ExtendAttModel model;
	            HashMap conMap = new HashMap();
	            for (int j = 0; j < this.groupSize; j++)
	            {
	                model = group.getAttributeAt(j);

	                String temp = null;
	                if (model.getAttType().equalsIgnoreCase("SpecChar"))
	                  {
	                      conMap.put(model.getAttName(), (Vector)this.multiList.getSelectedObject(i, j));
	                  }
	               else{
	                   if (model.getAttType().equals("EnumeratedType"))
	                   {
	                       temp = (String)this.multiList.getSelectedObject(i, j);
	                   }
	                   else
	                   if (model.getAttType().equals("Coding"))
	                   {
	                       temp = (String)this.multiList.getSelectedObject(i, j);
	                   }
	                   else
	                   if (model.getAttType().equalsIgnoreCase("Boolean"))
	                   {
	                       if (((Boolean) multiList.getSelectedObject(i, j)).
	                           booleanValue())
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
	                       {
	                           temp = (String)this.multiList.getSelectedObject(i,
	                                   j);
	                       }
	                       else
	                       if (refType.equalsIgnoreCase("EnumerateType"))
	                       {
	                           temp = (String)this.multiList.getSelectedObject(i,
	                                   j);
	                       }
	                       else
	                       if (refType.equalsIgnoreCase("ComboAtts"))
	                       {
	                           temp = multiList.getCellAt(i, j).getStringValue() +
	                                  ";" +
	                                  (String) multiList.getCellAt(i, j).getValue();
	                       }
	                   }

	                   boolean check = parentPanel.checkValidity(model, temp,
	                           model.getAttDisplay());
	                   if (check)
	                   {
	                       if (temp != null)
	                       {
	                           if (temp.equals(""))
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
	                   }
	                   else
	                   {
	                       return null;
	                   }

	                   if (temp != null && temp.trim().equals(""))
	                   {
	                       Object value = change(model, temp);

	                       conMap.put(model.getAttName(), value);
	                   }
	               }
	                //  conditions.put("",null);
	            }
	            if (conMap.size() != 0)
	            {
	                vec.add(conMap);
	            }
	        }

	        if (this.isControlPlan)
	        {
	            if (vec.size() > 0)
	            {
	                conditions.put(ExtendAttContainer.CONTROLPLAN, vec);
	            }
	        }
	        else
	        {
	            if (vec.size() > 0)
	            {
	                conditions.put(ExtendAttContainer.NORMALGROUP + ";" +
	                               this.group.getGroupName(), vec);
	            }
	        }
	        return conditions;
	    }


	    /**
	     * 得到附加属性
	     * @return　扩展属性组的集合
	     */
	    public Vector getExAttr()
	    {
	        Vector groups = new Vector();
	        ExtendAttGroup group;
	        for (int i = 0; i < multiList.getNumberOfRows(); i++)
	        {
	            group = this.group.duplicate();
	            boolean flag = false;
	            for (int j = 0; j < this.groupSize; j++)
	            {
	                ExtendAttModel model = group.getAttributeAt(j);
	                this.setAtts(model, i, j);
	                if (model.getAttValue() != null)
	                {
	                    flag = true;
	                }
	            }
	            if (flag)
	            {
	                groups.add(group);
	            }
	        }
	        return groups;
	    }


	    /**
	     * 有效性检查
	     * @return boolean 返回值表示被检查的目标是否有效,true为有效
	     */
	    public boolean check()
	    {
	        ExtendAttGroup group;
	        for (int i = 0; i < multiList.getNumberOfRows(); i++)
	        {
	            group = this.group.duplicate();
	            for (int j = 0; j < this.groupSize; j++)
	            {
	                ExtendAttModel model = group.getAttributeAt(j);
	                //System.out.println("model.getAttType()="+model.getAttType());
	                String temp = null;
	                if (model.getAttType().equals("EnumeratedType"))
	                {
	                    temp = (String)this.multiList.getSelectedObject(i, j);
	                }else
	                if (model.getAttType().equals("SpecChar"))
	               {
	                  continue;
	               }
	               else

	                if (model.getAttType().equals("Coding"))
	                {
	                    temp = (String)this.multiList.getSelectedObject(i, j);
	                    if(!model.getAllowNull()&&temp.equals(" "))
	                    {
	                          this.parentPanel.handleNull(model.getAttDisplay());
	                           return false;
	                    }
	                }
	                else
	                if (model.getAttType().equalsIgnoreCase("Boolean"))
	                {
	                    if (((Boolean) multiList.getSelectedObject(i, j)).
	                        booleanValue())
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
	                    {
	                        temp = (String)this.multiList.getSelectedObject(i, j);
	                    }
	                    else
	                    if (refType.equalsIgnoreCase("EnumerateType"))
	                    {
	                        temp = (String)this.multiList.getSelectedObject(i, j);
	                    }else
	                    if (refType.equalsIgnoreCase("ComboAtts"))
	                    {
	                        temp = multiList.getCellAt(i, j).getStringValue() + ";" +
	                               (String) multiList.getCellAt(i, j).getValue();
	                    }
	                }
	                else
	                {
	                    temp = (String)this.multiList.getSelectedObject(i, j);
	                }
	                boolean check = parentPanel.checkValidity(model, temp,
	                        model.getAttDisplay());
	                if (!check)
	                {
	                    return false;
	                }
	            }
	        }
	        return true;
	    }


	    /**
	     * 设置父panel
	     * @param panel CappExAttrPanel 附加属性panel
	     */
	    public void setParentPanel(CappExAttrPanelForZC panel)
	    {
	        this.parentPanel = panel;
	    }
	    /**
	     * 设置对应行和列的成组属性值
	     * @param model ExtendAttModel 附加属性模型
	     * @param i int 行号
	     * @param j int 列号
	     * @see ExtendAttModel
	     */
	    private void setAtts(ExtendAttModel model, int i, int j)
	    {
	        String temp = null;
	        if (model.getAttType().equalsIgnoreCase("SpecChar"))
	                {

	                     model.setAttValue((Vector)this.multiList.getSelectedObject(i, j));
	                }
	      else
	      {
	          if (model.getAttType().equals("EnumeratedType"))
	          {
	              temp = (String)this.multiList.getSelectedObject(i, j);
	          }
	          else
	          if (model.getAttType().equals("Coding"))
	          {
	              temp = (String)this.multiList.getSelectedObject(i, j);
	          }
	          else
	          if (model.getAttType().equalsIgnoreCase("Boolean"))
	          {
	              if (((Boolean) multiList.getSelectedObject(i, j)).booleanValue())
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
	              {
	                  temp = (String)this.multiList.getSelectedObject(i, j);
	              }
	              else
	              if (refType.equalsIgnoreCase("EnumerateType"))
	              {
	                  temp = (String)this.multiList.getSelectedObject(i, j);
	              }
	              else
	              if (refType.equalsIgnoreCase("ComboAtts"))
	              {

	                  temp = multiList.getCellAt(i, j).getStringValue() +";"+
	                         (String) multiList.getCellAt(i, j).getValue();
	              }

	          }
	          else
	          {
	              temp = (String)this.multiList.getSelectedObject(i, j);
	          }
	          boolean check = parentPanel.checkValidity(model, temp,
	                  model.getAttDisplay());
	          if (check)
	          {
	              if (temp != null)
	              {
	                  if (temp.equals(""))
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
	          }
	          else
	          {
	              return;
	          }
	          if(temp!=null)
	          {
	              Object value = change(model, temp);
	              model.setAttValue(value);
	          }
	      }
	    }


	    /**
	     * 显示当前对象
	     * @param info ExtendAttriedIfc　受扩展属性管理的值对象
	     * @param groupName 扩展属性组名
	     * @see ExtendAttriedInfo
	     */
	    public void show(ExtendAttriedIfc info, String groupName)
	    {
	        ExtendAttContainer container1 = info.getExtendAttributes();
	        show(container1,groupName);
//	        ExtendAttGroup group;
//	        int count;
//	        if (isControlPlan)
//	        {
//	            count = container1.getPlanGroupCount();
//	            for (int i = 0; i < count; i++)
//	            {
//	                group = container1.getPlanGroupAt(i);
//	                for (int j = 0; j < group.getCount(); j++)
//	                {
//	                    showAttsToTable(i, j, group.getAttributeAt(j));
//	                }
	//
//	            }
//	        }
//	        else
//	        {
//	            Vector vec = container1.getAttGroups(groupName);
//	            if (vec == null)
//	            {
//	                count = 0;
//	            }
//	            else
//	            {
//	                count = vec.size();
//	            }
//	            for (int i = 0; i < count; i++)
//	            {
//	                group = (ExtendAttGroup) vec.elementAt(i);
//	                for (int j = 0; j < group.getCount(); j++)
//	                {
//	                    showAttsToTable(i, j, group.getAttributeAt(j));
//	                }
	//
//	            }
//	        }

	    }

	    /**
	      * 显示当前对象
	      * @param info container1　扩展属性容器
	      * @param groupName 扩展属性组名
	      * @see ExtendAttContainer
	      */
	    // 20070205薛静添加

	     public void show(ExtendAttContainer container1, String groupName)
	     {

	         //CCBegin by liunan 2011-03-15 打补丁v4r3_p028_20110216
	         //begin CR5
	         //源码
	         /*ExtendAttGroup group;
	         int count;*/
	         ExtendAttGroup group1 ;
	         //行数
	         int count;
	         //原工序中属性列数
	         int count1 = 0;
	         //xml文件中比原来工序中多的属性数量
	         int a = 0;
	         //end CR5
	         //CCEnd by liunan 2011-03-15 
	         if (isControlPlan)
	         {
	             count = container1.getPlanGroupCount();
	             for (int i = 0; i < count; i++)
	             {
	                 group = container1.getPlanGroupAt(i);
	                 for (int j = 0; j < group.getCount(); j++)
	                 {
	                     showAttsToTable(i, j, group.getAttributeAt(j));
	                 }

	             }
	         }
	         else
	         {
	             Vector vec = container1.getAttGroups(groupName);
	             if (vec == null)
	             {
	                 count = 0;
	             }
	             else
	             {
	                 count = vec.size();
	             }
	             //CCBegin by liunan 2011-03-15 打补丁v4r3_p028_20110216
	             //begin CR5
	             //源码
	             /*for (int i = 0; i < count; i++)
	             {
	                 group = (ExtendAttGroup) vec.elementAt(i);
	                 for (int j = 0; j < group.getCount(); j++)
	                 {
	                     showAttsToTable(i, j, group.getAttributeAt(j));
	                 }

	             }*/
	             ArrayList list = new ArrayList();
	             for (int i = 0; i < count; i++)
	             {
	                 group1 = (ExtendAttGroup) vec.elementAt(i);
	                 count1 = group1.getCount();
	                 //如果xml中的属性少于原工序的扩展属性，则直接将原工序中的属性值设置到列表中
	                 if (groupSize <= count1)
	                 {
	                 	   for (int j = 0; j < groupSize; j++)
	                 	   {
	                 	   	   showAttsToTable(i, j, group1.getAttributeAt(j));
	                 	   }
	                 }
	                 else
	                 {
	                	 for (int j = 0; j < count1; j++)
	                     {
	                    	 ExtendAttModel model1 = group1.getAttributeAt(j);
	                    	 //将原工序中的属性设置到列表中
	                         showAttsToTable(i, j, group1.getAttributeAt(j));
	                         //将原工序中的扩展属性显示名放到集合里
	                         list.add(model1.getAttDisplay());     
	                     }
	                 }
	             }
	             String head[] = new String[groupSize];
	             //将xml中的属性显示名放到数组集合里
	             for (int i = 0; i < groupSize; i++)
	             {
	             	   head[i] = group.getAttributeAt(i).getAttDisplay();
	             }
	             //如果xml中配置的属性多于原工序扩展属性，将多于的属性值用空字符串显示
	             if(groupSize > count1)
	             {
	             	   a = groupSize - count1;
	             	   for (int i = 0; i < count; i++)
	             	   {
	             	   	   for (int j = 0, k = groupSize; j < k; j++)
	             	   	   {
	             	   	   	   //将xml中多于的属性值用空字符串显示
	             	   	   	   if (!list.contains(head[j]))
	             	   	   	   {
	             	   	   	   	   for (int m = 0; m < a; m++)
	             	   	   	   	   {
	             	   	   	   	   	   multiList.addTextCell(i, count1 + m, "");
	             	   	   	   	   }
	             	   	   	   }
	             	   	   }
	             	   }
	             }
	             //end CR5
	             //CCEnd by liunan 2011-03-15 
	         }

	     }

	    /**
	     * 把对象的一个附加属性模型显示到指定的行列
	     * @param i 行
	     * @param j 列
	     * @param model ExtendAttModel 附加属性模型
	     * @see ExtendAttModel
	     */
	    private void showAttsToTable(int i, int j, ExtendAttModel model)
	    {
	        Object obj1 = model.getAttValue();
	        
	        
	        if (model.getAttType().equals("SpecChar"))
	         {

	             if (obj1 != null)
	             {

	                 multiList.addSpeCharCell(i, j, (Vector)obj1);
	             }
	             else
	             {
	                 multiList.addSpeCharCell(i, j, new Vector());
	             }

	         }

	       else
	       if (model.getAttType().equalsIgnoreCase("EnumeratedType")) {
	         EnumeratedType et1 = (EnumeratedType) obj1;
	         if (et1 == null) {
	           HashMap map = model.getFeature();
	           String classPath = (String) map.get("classpath");
	           String newString = (String) map.get("newMethod");
	           try {
	             Class class1 = Class.forName(classPath);
	             Method newMethod = class1.getMethod(newString, null);
	             et1 = (EnumeratedType) newMethod.invoke(class1, null);
	           }
	           catch (Exception e) {
	                 if(verbose)
	             e.printStackTrace();
	             JOptionPane.showMessageDialog(getParentJFrame(), e.getMessage());
	           }
	         }
	         EnumeratedType[] ets = et1.getValueSet();
	         String value = et1.getDisplay(local);
	         Vector values = new Vector();
	         for (int k = 0; k < ets.length; k++) {
	           values.add(ets[k].getDisplay(local));
	         }
	         this.multiList.addComboBoxCell(i, j, value, values);
	       }
	       else
	       if (model.getAttType().equalsIgnoreCase("Coding")) {
	    	
	         String et1 = (String) obj1;
	         HashMap map = model.getFeature();
	         String sorttype = (String) map.get("SortType");
	         Collection sorts = (Collection)this.etMaps.get(sorttype);
	        
	         Iterator ite = sorts.iterator();
	         Vector vec = new Vector();
	          CodingIfc et2 = null;
	          String cont = "";
	          vec.add(cont);
	         while (ite.hasNext()) {
	           et2 = (CodingInfo) ite.next();
	            if(et2.getCodeContent().equals(et1))
	           {
	             cont = et2.getCodeContent();
	           }
	            
	           vec.add( et2.getCodeContent());
	         }

	         if (et1 == null) {

	           this.multiList.addComboBoxCell(i, j, "", vec);
	         }
	         else {
//	           this.multiList.addComboBoxCell(i, j,
//	                                          ( (CodingIfc) vec.elementAt(0)).
//	                                          getCodeContent(), vec);
//	        	  CCBeginby leixiao 2009-7-8 原因：增加上下移       	 
	           this.multiList.addComboBoxCell(i, j,
	        		   cont, vec);
//	         CCEndby leixiao 2009-7-8 原因：增加上下移   
	         }
	       }
	       else if (model.getAttType().equalsIgnoreCase("String")) {
	         HashMap map = model.getFeature();
	         String refType = (String) map.get("RefType");
	         if (refType == null) {
	           if (obj1 != null) {
	             multiList.addTextCell(i, j, obj1.toString());
	           }
	           else {
	             multiList.addTextCell(i, j, "");
	           }
	         }
	         else
	         if (refType.equals("EnumerateType")) {
	           String classPath = (String) map.get("classpath");
	           EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
	           EnumeratedType et;
	           Vector vec = new Vector();
	           for (int k = 0; k < ets.length; k++) {
	             et = (EnumeratedType) ets[k];
	             vec.add(et.getDisplay(local));
	           }
	           if (obj1 != null) {
	             this.multiList.addComboBoxCell(i, j, obj1.toString(), vec);
	           }
	           else {
	             this.multiList.addComboBoxCell(i, j, "", vec);
	           }
	         }
	         else
	         if (refType.equals("ComboAtts")) {

	           String sorttype = (String) map.get("SortType");
	           Collection sorts = (Collection)this.etMaps.get(sorttype);
	           String valueStr = "";
	           String sortStr = "";
	           if (obj1 != null) {
	             StringTokenizer ston = new StringTokenizer( (String) obj1,
	                 ";");
	             if (ston.hasMoreTokens()) {
	               valueStr = ston.nextToken();
	             }
	             if (ston.hasMoreTokens()) {
	               sortStr = ston.nextToken();
	             }
	           }
	           Iterator ite = sorts.iterator();
	           Vector vec = new Vector();
	           while (ite.hasNext()) {
	             vec.add( ( (CodingInfo) ite.next()).getCodeContent());
	           }
	           this.multiList.addComboBoxCell(i, j, valueStr, sortStr, vec);
	         }

	       }
	       else
	       if (model.getAttType().equalsIgnoreCase("Boolean")) {
	         boolean flag = true;
	         if (rds != null) {
	           for (int len = 0; len < rds.length; len++) {

	             if (j == rds[len]) {
	               flag = false;

	             }
	           }
	         }
	         if (obj1 != null) {
	           if (flag) {
	             multiList.addCheckboxCell(i, j,
	                                       ( (Boolean) obj1).booleanValue());
	           }
	           else {
	             multiList.addRadioButtonCell(i, j,
	                                          ( (Boolean) obj1).booleanValue());
	           }
	         }
	         else {
	           multiList.addCheckboxCell(i, j, false);
	         }
	       }
	       else {
	         if (obj1 != null) {
	           multiList.addTextCell(i, j, obj1.toString());
	         }
	         else {
	           multiList.addTextCell(i, j, "");
	         }
	       }

	    }


	    /**
	     * 设置哪一行是单选按钮1
	     * @param cols　要设置的行号集合
	     */
	    public void setRadioButtonCols(int[] cols)
	    {
	        this.rds = cols;
	    }


	    /**
	     * 设置表格各列的绝对宽度
	     */
	    public void setMulistAbsWidths()
	    {
	        int i = multiList.getNumberOfCols();
	        String[] cols = new String[i];
	        for (int j = 0; j < i; j++)
	        {
	            cols[j] = "80";
	        }
	        multiList.setColumnSizes(cols);
	    }


	    /**
	     * 应用默认列宽
	     * @throws PropertyVetoException
	     */
	    protected void defaultColumnWidths() //throws PropertyVetoException
	    {
	        int i = multiList.getNumberOfCols();
	        int j = i - 1;
	        int as[] = new int[i];
	        for (int k = 0; k < j; k++)
	        {
	            as[k] = 1;
	        }
	        as[j] = 1;
	        multiList.setRelColWidth(as);

	    }


	    /**
	     * 添加按钮执行
	     * @param e　按钮事件
	     */
//	    void addButton_actionPerformed(ActionEvent e)
//	    {  
//	        int row = this.multiList.getNumberOfRows();
//	        for (int i = 0; i < group.getCount(); i++)
//	        {
//	            addDefalutAttsToTable(row, i, group.getAttributeAt(i));
//	        }
//	        this.attrButton.setEnabled(true);
	//
//	    }
	    //CCBegin by liuzhicheng 2010-01-21 原因：“添加”按钮的执行方式，改为选中某行添加时，顺序添加到下一行。
	    void addButton_actionPerformed(ActionEvent e)
	    {
	        //修改“添加”按钮的执行方式，改为选中某行添加时，顺序添加到下一行。
	        int row = this.multiList.getNumberOfRows();
	        int row1 = this.multiList.getSelectedRow();
	        if (row1 < 0)
	        {
	            for (int i = 0; i < group.getCount(); i++)
	            {
	                addDefalutAttsToTable(row, i, group.getAttributeAt(i));
	                multiList.setSelectedRow(row);
	            }
	        }
	        else
	        {
	            for (int i = 0; i < group.getCount(); i++)
	            {
	                addDefalutAttsToTable(row, i, group.getAttributeAt(i));
	            }
	            for (int i = row - 1; i > row1; i--)
	            {
	                //CCBegin SS15
	                //multiList.moveDown(i);
	                //不进行所有行的下移，而是只针对前5列外的内容进行下移
	              if (groupname.equalsIgnoreCase("控制计划"))
	              {
	                ExtendAttModel model;
	                for (int j = 4; j < group.getCount()-3; j++)
	                {
	                	model = group.getAttributeAt(j);
	                	String temp = null;
	                	if (model.getAttType().equalsIgnoreCase("SpecChar"))
	                  {
	                  	multiList.addSpeCharCell(i+1, j, (Vector)this.multiList.getSelectedObject(i, j));
	                  	if(i==(row1+1))
	                  	{
	                  		Vector tv = new Vector();
	                  		tv.add("");
	                  		multiList.addSpeCharCell(i, j, tv);
	                  	}
	                  }
	                  else if (model.getAttType().equals("Coding"))
	                  {
	                  	HashMap map = model.getFeature();
	                  	String sorttype = (String) map.get("SortType");
	                  	Collection sorts = (Collection)this.etMaps.get(sorttype);
	                  	Iterator ite = sorts.iterator();
	                  	Vector vec = new Vector();
	                  	vec.add("");
	                  	while (ite.hasNext())
	                  	{
	                  	 	CodingInfo info = (CodingInfo)ite.next();
	                  	 	if(info.getCodeContent().equalsIgnoreCase("C"))
	                  	 	  vec.add(0,info.getCodeContent());
	                  	 	else
	                  	 	  vec.add(info.getCodeContent());
	                  	}
	                  	temp = (String)this.multiList.getSelectedObject(i, j);
	                  	if (temp != null)
	                  	{
	                  		multiList.addComboBoxCell(i+1, j, temp, vec);
	                  	}
	                  	else
	                  	{
	                  		multiList.addComboBoxCell(i+1, j, "", vec);
	                  	}
	                  	if(i==(row1+1))
	                  	{
	                  		multiList.addComboBoxCell(i, j, "", vec);
	                  	}
	                  }
	                  else if (model.getAttType().equalsIgnoreCase("String"))
	                  {
	                   	temp = (String)this.multiList.getSelectedObject(i, j);
	                   	if (temp != null)
	                   	{
	                   		multiList.addTextCell(i+1, j, temp.toString());
	                   	}
	                   	else
	                   	{
	                   		multiList.addTextCell(i+1, j, "");
	                   	}
	                   	if(i==(row1+1))
	                   	{
	                   		multiList.addTextCell(i, j, "");
	                   	}
	                  }
	                }
	              }
	              else
	              {
	              	multiList.moveDown(i);
	              }
	                //CCEnd SS15
	            }
//	            if (groupname.equalsIgnoreCase("特性控制"))
//	            {
//	                for (int i = 0; i < multiList.getNumberOfRows(); i++)
//	                {
//	                	  multiList.addTextCell(i, 0, (new Integer(i + 1)).toString());
//	                }
//	            }
	            multiList.setSelectedRow(row1 + 1);
	        }  

	    }
	    //CCEnd by liuzhicheng 2010-01-21 原因：“添加”按钮的执行方式，改为选中某行添加时，顺序添加到下一行。

	    
	    /**  
	     * 设置默认值到行列
	     * @param i　行号
	     * @param j　列号
	     * @param model　ExtendAttModel　属性封装类
	     * @see ExtendAttModel
	     */
	    private void addDefalutAttsToTable(int i, int j, ExtendAttModel model)
	    {
	        Object obj1 = model.getAttDefault();
	        if (model.getAttType().equalsIgnoreCase("EnumeratedType"))
	        {
	            HashMap map = model.getFeature();
	            String classPath = (String) map.get("classpath");
	            String newString = (String) map.get("newMethod");
	           System.out.println("classPath=="+classPath);
	           System.out.println("newString=="+classPath);
	            try
	            {
	                EnumeratedType[] ets = (EnumeratedType[])this.etMaps.get(
	                        classPath);
	                String value = "";
	                Vector values = new Vector();
	                for (int k = 0; k < ets.length; k++)
	                {
	                    values.add(ets[k].getDisplay(local));
	                }
	                multiList.addComboBoxCell(i, j,value, values);
	            }
	            catch (Exception e)
	            {
	                  if(verbose)
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(getParentJFrame(), e.getLocalizedMessage());
	            }
	        }
	        else if (model.getAttType().equals("SpecChar"))
	            {
	                if (obj1 != null)
	                {

	                    multiList.addSpeCharCell(i, j, new Vector());
	                }
	                else
	                {
	                    multiList.addSpeCharCell(i, j, new Vector());
	                }

	            }

	        else
	        if (model.getAttType().equalsIgnoreCase("Coding"))
	        {
	            HashMap map = model.getFeature();
	            String defaultValue = model.getAttDefault();
	            boolean isValid = false;
	            String sorttype = (String) map.get("SortType");
	            Collection sorts = (Collection)this.etMaps.get(sorttype);
	            Iterator ite = sorts.iterator();
	            Vector vec = new Vector();
	            String deV="";
	            vec.add(deV);
	            while (ite.hasNext())
	            {
	                CodingInfo info = (CodingInfo)ite.next();
	                //CCBegin by liunan 2009-09-14
	                if(info.getCodeContent().equalsIgnoreCase("C"))
	                  vec.add(0,info.getCodeContent());
	                else
	                //CCEnd by liunan
	                vec.add(info.getCodeContent());
	                if(info.getCodeContent().equals(defaultValue))
	                  isValid = true;
	            }
	           if(isValid&&defaultValue!=null&&!defaultValue.equalsIgnoreCase(""))
	                this.multiList.addComboBoxCell(i, j,
	                                          defaultValue,
	                                          vec);
	           else
	             //20070302xuejing modify
	               this.multiList.addComboBoxCell(i, j,
	                                           (String) vec.elementAt(0),
	                                           vec);
	        }
	        else
	        if (model.getAttType().equalsIgnoreCase("String"))
	        {
	            HashMap map = model.getFeature();
	            String refType = (String) map.get("RefType");
	            if (refType == null)
	            {
	                if (obj1 != null)
	                {
	                    multiList.addTextCell(i, j, obj1.toString());
	                }
	                else
	                {
	                    multiList.addTextCell(i, j, "");
	                }
	            }
	            else
	            if (refType.equals("EnumerateType"))
	            {
	                String classPath = (String) map.get("classpath");
	                EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
	                EnumeratedType et;
	                Vector vec = new Vector();
	                for (int k = 0; k < ets.length; k++)
	                {
	                    et = (EnumeratedType) ets[k];
	                    vec.add(et.getDisplay(local));
	                }
	                if (obj1 != null)
	                {
	                    this.multiList.addComboBoxCell(i, j, obj1.toString(), vec);
	                }
	                else
	                {
	                    this.multiList.addComboBoxCell(i, j, "", vec);
	                }
	            }
	            else
	            if (refType.equals("ComboAtts"))
	            {

	                String sorttype = (String) map.get("SortType");
	                Collection sorts = (Collection)this.etMaps.get(sorttype);
	                String valueStr = "";
	                String sortStr = "";
	                if (obj1 != null)
	                {
	                    StringTokenizer ston = new StringTokenizer((String) obj1,
	                            ";");
	                    
	                    if (ston.hasMoreTokens())
	                    {
	                        valueStr = ston.nextToken();
	                    }
	                    if (ston.hasMoreTokens())
	                    {
	                        sortStr = ston.nextToken();
	                    }
	                }
	                Iterator ite = sorts.iterator();
	                Vector vec = new Vector();
	                while (ite.hasNext())
	                {
	                    vec.add(((CodingInfo) ite.next()).getCodeContent());
	                }
	                //begin CR2
	                if(sortStr.equals(""))
	                {
	                	sortStr = (String)vec.elementAt(0);
	                }//end CR2
	                this.multiList.addComboBoxCell(i, j, valueStr, sortStr, vec);
	            }

	        }
	        else
	        if (model.getAttType().equalsIgnoreCase("Boolean"))
	        {
	            boolean flag = true;
	            if (rds != null)
	            {
	                for (int len = 0; len < rds.length; len++)
	                {

	                    if (j == rds[len])
	                    {
	                        flag = false;

	                    }
	                }
	            }
	            if (obj1 != null)
	            {
	                if (flag)
	                {
	                    multiList.addCheckboxCell(i, j,
	                                              (new Boolean(obj1.toString())).booleanValue());
	                }
	                else
	                {
	                    multiList.addRadioButtonCell(i, j,
	                                                 (new Boolean(obj1.toString())).
	                                                 booleanValue());
	                }
	            }
	            else
	            {
	                multiList.addCheckboxCell(i, j, false);
	            }
	        }
	        else
	        {
	            if (obj1 != null)
	            {
	                multiList.addTextCell(i, j, obj1.toString());
	            }
	            else
	            {
	                multiList.addTextCell(i, j, "");
	            }
	        }
	    }
	    /**
	     * 删除按钮执行事件
	     * @param e ActionEvent 按钮触发事件
	     */
	    void deleteButton_actionPerformed(ActionEvent e)
	    {
	    	
	    	
	    	Hashtable eqMap=new Hashtable();
	    	Hashtable materMap=new Hashtable();
	    	Hashtable toolMap=new Hashtable();
	    	
	    	boolean isHasEq=false;
	    	
	    	
	    	
	      int row = this.multiList.getSelectedRow();
	      if (row != -1){
	    	//CCBegin SS15
	    	/*if(groupname.equals("控制计划")){
	    		
	    	  String eqID=multiList.getCellText(row, 15);
	    	  String toolID=multiList.getCellText(row, 16);
	    	  String materID=multiList.getCellText(row, 17);
	    	  String eqCount=multiList.getCellText(row, 3);
	    	  String toolCount=multiList.getCellText(row, 3);
	    	  
	    	  QMProcedureInfo info = container.getProIfc();
	    	  
	    	  if(info != null || info.getProcessControl() != null)
		      {
	    		  ExtendAttContainer con = info.getProcessFlow();
	    		  Vector numberValue = getExtendAttValue(con, "eqBsoID");
	    		  
	    		  
	    		  if(numberValue !=null&&numberValue.size()!=0){
	    			  
	    			  for(int a=0;a<numberValue.size();a++){
	    				  
	    				  String otherEqID=(String)numberValue.get(a);
	    				  //CCBegin SS5
	    				  if(otherEqID!=null&&otherEqID.equals(eqID)){
	    				  //CCEnd SS5
	    					  isHasEq=true;
	    					  
	    					  break;
	    					  
	    				  }
	    				  
	    				  
	    			  }
	    			  
	    			  
	    		  }
	    		  
		      }
	    	  
	    	  if(eqID!=null&&!eqID.equals("")&&!isHasEq){
	    		  
	    		  if(eqCount.indexOf("(")!=-1||eqCount.indexOf("（")!=-1){
	        		  
	    			  int rCount=Integer.parseInt(eqCount);
	    			  
	    			  if(!eqMap.containsKey(eqID))
	    			     eqMap.put(eqID, rCount);
	    			  else{
	    				  int oldCount=(Integer)eqMap.get(eqID);
	    				  rCount+=oldCount;
	    				  eqMap.put(eqID, rCount);
	    			  }
	        		  
	        	  }else{
	        		  
	        		  eqMap.put(eqID, 1);
	        	  }
	    		  
	    		  eqDeleteVec.add(eqMap);
	    		  
	    	  }
	    	  if(materID!=null&&!materID.equals("")){
	        		  
	    		  materMap.put(materID, 1);
	    		  
	    		  materDeleteVec.add(materMap);
	    		  
	    	  }
	    	  if(toolID!=null&&!toolID.equals("")){
	    		  
	    		  
	    		  
	             if(toolCount.indexOf("(")!=-1||toolCount.indexOf("（")!=-1||toolCount.equals("")){
	        		  
	    			 
	    			  
	    			  toolMap.put(toolID, 0);
	        		  
	        	  }else{
	        		  
                     int rCount=Integer.parseInt(toolCount);
	    			  
	    			  if(!toolMap.containsKey(toolID))
	    				  toolMap.put(toolID, rCount);
	    			  else{
	    				  int oldCount=(Integer)toolMap.get(toolID);
	    				  rCount+=oldCount;
	    				  toolMap.put(toolID, rCount);
	    			  }
	        	  }
	    		  
	    		  toolDeleteVec.add(toolMap);
	    	   }
	    	 
	    	  }*/
	    	  //CCEnd SS15
	    	
	    	 if(groupname.equals("特性控制")){
	    		 
	    		 //CCBegin SS13
	    		 //String eqID=multiList.getCellText(row, 11);
	    		 String eqID=multiList.getCellText(row, 12);
	    		 //CCEnd SS13
	    		 QMProcedureInfo info = container.getProIfc();
		    	  if(info != null || info.getProcessControl() != null)
			      {
		    		  ExtendAttContainer con = info.getProcessControl();
		    		  Vector numberValue = getExtendAttValue(con, "eqBsoID");
		    		  
		    		  
		    		  if(numberValue !=null&&numberValue.size()!=0){
		    			  
		    			  for(int a=0;a<numberValue.size();a++){
		    				  
		    				  String otherEqID=(String)numberValue.get(a);
		    				  System.out.println("otherEqID======================================"+otherEqID);
		    				  if(otherEqID!=null&&otherEqID.equals(eqID)){
		    					  
		    					  isHasEq=true;
		    					  
		    					  break;
		    					  
		    				  }
		    				  
		    				  
		    			  }
		    			  
		    			  
		    		  }
		    		  
			      }
	    		  if(eqID!=null&&!eqID.equals("")&&!isHasEq){
	    			  
	    			  if(!eqMap.containsKey(eqID))
	    			      eqMap.put(eqID, 1);
	    		  
	    		      eqDeleteVec.add(eqMap);
	    			  
	    		  }
	    	 }
	    	  
	    	  
	    	  //CCBegin SS15
	        //multiList.removeRow(row);
	        if(groupname.equals("控制计划"))
	        {
	        	//删除5到15列，实现：将5到15列内容上移
	        	int rows = this.multiList.getNumberOfRows();
	        	for (int i = row; i < rows; i++)
	        	{
	                ExtendAttModel model;
	                for (int j = 4; j < group.getCount()-3; j++)
	                {
	                	model = group.getAttributeAt(j);
	                	String temp = null;
	                	if (model.getAttType().equalsIgnoreCase("SpecChar"))
	                  {
	                  	if(i==(rows-1))
	                  	{
	                  		Vector tv = new Vector();
	                  		tv.add("");
	                  		multiList.addSpeCharCell(i, j, tv);
	                  	}
	                  	else
	                  	{
	                  		multiList.addSpeCharCell(i, j, (Vector)this.multiList.getSelectedObject(i+1, j));
	                  	}
	                  }
	                  else if (model.getAttType().equals("Coding"))
	                  {
	                  	HashMap map = model.getFeature();
	                  	String sorttype = (String) map.get("SortType");
	                  	Collection sorts = (Collection)this.etMaps.get(sorttype);
	                  	Iterator ite = sorts.iterator();
	                  	Vector vec = new Vector();
	                  	vec.add("");
	                  	while (ite.hasNext())
	                  	{
	                  	 	CodingInfo info = (CodingInfo)ite.next();
	                  	 	if(info.getCodeContent().equalsIgnoreCase("C"))
	                  	 	  vec.add(0,info.getCodeContent());
	                  	 	else
	                  	 	  vec.add(info.getCodeContent());
	                  	}
	                  	
	                  	if(i==(rows-1))
	                  	{
	                  		multiList.addComboBoxCell(i, j, "", vec);
	                  	}
	                  	else
	                  	{
	                  	temp = (String)this.multiList.getSelectedObject(i+1, j);
	                  	if (temp != null)
	                  	{
	                  		multiList.addComboBoxCell(i, j, temp, vec);
	                  	}
	                  	else
	                  	{
	                  		multiList.addComboBoxCell(i, j, "", vec);
	                  	}
	                  }
	                  }
	                  else if (model.getAttType().equalsIgnoreCase("String"))
	                  {
	                  	
	                   	if(i==(rows-1))
	                   	{
	                   		multiList.addTextCell(i, j, "");
	                   	}
	                   	else
	                   	{
	                   	temp = (String)this.multiList.getSelectedObject(i+1, j);
	                   	if (temp != null)
	                   	{
	                   		multiList.addTextCell(i, j, temp.toString());
	                   	}
	                   	else
	                   	{
	                   		multiList.addTextCell(i, j, "");
	                   	}
	                  }
	                  }
	                }
	        	}
	        	
	        	
	        }
	        else
	    	  {
	    	  	multiList.removeRow(row);
	    	  }
	        //CCEnd SS15
	        
	      }
	      if (row > 0) {
	        this.multiList.selectRow(row - 1);
	      }
	      else
	      if (this.multiList.getTable().getRowCount() > 0) {
	        this.multiList.selectRow(row);
	      }
	      
	    }


	    /**
	     * 把字符值转为附加属性类型对应的值
	     * @param model　属性封装类
	     * @param text　属性的字符串值
	     * @return Object 字符值转为的附加属性类型对应的值
	     * @see ExtendAttModel
	     */
	    Object change(ExtendAttModel model, String text)
	    {
	        String rstcType = model.getAttType();
	        String defName = model.getAttDisplay();
	        HashMap map = model.getFeature();
	        if (rstcType.equalsIgnoreCase("EnumeratedType"))
	        {
	            String classPath = (String) map.get("classpath");
	            EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
	            EnumeratedType et;
	            for (int i = 0; i < ets.length; i++)
	            {
	                et = (EnumeratedType) ets[i];
	                if (et.getDisplay(local).equals(text))
	                {
	                    return et;
	                }
	            }
	        }
	        else
	        if (rstcType.equalsIgnoreCase("Coding"))
	        {
//	            String sortType = (String) map.get("SortType");
//	            Collection col = (Collection) etMaps.get(sortType);
//	            Iterator i = col.iterator();
//	            CodingIfc et;
//	            while (i.hasNext())
//	            {
//	                et = (CodingIfc) i.next();
//	                if (et.getCodeContent().equals(text))
//	                {
//	                    return et.getBsoID();
//	                }
//	            }
	          return text;
	        }
	        else
	        if (rstcType.equalsIgnoreCase("int"))
	        {
	            int textVal = 0;
	            textVal = Integer.parseInt(text);
	            return new Integer(textVal);
	        }
	        else
	        if (rstcType.equalsIgnoreCase("double"))
	        {
	            double doubleVal = 0;
	            doubleVal = Double.parseDouble(text);
	            return new Double(doubleVal);
	        }
	        else
	        if (rstcType.equalsIgnoreCase("float"))
	        {
	            float floatVal = 0;
	            floatVal = Float.parseFloat(text);
	            return new Float(floatVal);
	        }
	        else if (rstcType.equalsIgnoreCase("long"))
	        {
	            long longVal = 0;
	            longVal = Long.parseLong(text);
	            return new Long(longVal);
	        }
	        else if (rstcType.equalsIgnoreCase("string"))
	        {
	            return text;
	        }
	        else if (rstcType.equalsIgnoreCase("boolean"))
	        {
	            if (text.equalsIgnoreCase("true"))
	            {
	                return new Boolean(true);
	            }
	            else
	            {
	                return new Boolean(false);
	            }

	        }
	        return null;
	    }


	    /**
	     * 设置工作模式，有三种　，编辑，查看，搜索
	     * @param model int
	     */
	    public void setModel(int model)
	    {
	        if (model == CappExAttrPanel.VIEW_MODEL)
	        {
	            this.multiList.setCellEditable(false);
	            this.addButton.setEnabled(false);
	            this.deleteButton.setEnabled(false);
	            this.calculateButton.setEnabled(false);
	            this.editButton.setEnabled(false);
//	  		CCBegin by liuzc 2009-12-14 原因：解放升级 
	            this.inheritButton.setEnabled(false);
	            this.copyAddButton.setEnabled(false);
//	  		CCBegin by liuzc 2009-12-14 原因：解放升级    
//	            CCBegin SS10
	            this.EQInheritButton.setEnabled(false);
//	            CCEnd SS10
	        }
	        else
	        {
	            this.multiList.setCellEditable(true);
	            this.addButton.setEnabled(true);
	            this.deleteButton.setEnabled(true);
	            this.calculateButton.setEnabled(true);   
	            this.editButton.setEnabled(true);
//	  		CCBegin by liuzc 2009-12-14 原因：解放升级 
	            this.inheritButton.setEnabled(true);
	            this.copyAddButton.setEnabled(true);
//	  		CCBegin by liuzc 2009-12-14 原因：解放升级 
//	            CCBegin SS10
	            this.EQInheritButton.setEnabled(true);
//	            CCEnd SS10
	    }
	    }


	    /**
	     * 获得上级frame
	     * @return JFrame 上级frame
	     */
	    public JFrame getParentJFrame()
	    {
	    	Component parent = getParent();

	        while (!(parent instanceof JFrame))
	        {
	            parent = parent.getParent();
	        }
	        return (JFrame) parent;
	    }

	    /**
	     * 设置添加按钮长度
	     * @param i int 按钮长度
	     */
	    public void setAddButtonSize(int i)
	     {
	        addButton.setMaximumSize(new Dimension(i, 23));
	        addButton.setMinimumSize(new Dimension(i, 23));
	        addButton.setPreferredSize(new Dimension(i, 23));
	       // addButton.setMnemonic('A');
	        //addButton.setText("添加(A)");
	     }

	     /**
	      * 设置删除按钮长度
	      * @param i int 按钮长度
	      */
	     public void setDelButtonSize(int i)
	  {
	     deleteButton.setMaximumSize(new Dimension(i, 23));
	     deleteButton.setMinimumSize(new Dimension(i, 23));
	     deleteButton.setPreferredSize(new Dimension(i, 23));
	    // addButton.setMnemonic('A');
	     //addButton.setText("添加(A)");
	  }

	  /**
	   * 设置添加按钮的助记符
	   * @param mn char 助记符名
	   */
	  public void setAddButtonMnemonic(char mn)
	  {
	      this.addButton.setMnemonic(mn);
	      this.addButton.setText("添加"+"("+mn+")");
	  }

	  /**
	  * 设置删除按钮的助记符
	  * @param mn char 助记符名
	  */
	  public void setDelButtonMnemonic(char mn)
	  {
	      this.deleteButton.setMnemonic(mn);
	      this.deleteButton.setText("删除"+"("+mn+")");
	  }


	  /**
	   * 上移按钮执行事件
	   * @param e ActionEvent 按钮触发事件
	   */
	  void upButton_actionPerformed(ActionEvent e) {
	       if( multiList.getTable().isEditing())
	       multiList.getTable().getCellEditor().stopCellEditing();
	        int selectRow = multiList.getSelectedRow();
	        if(selectRow==0||selectRow==-1)
	        {
	          return;
	        }
	        else
	        {
	            int uprow=selectRow-1;
	            int colums=multiList.getTable().getColumnCount();
	            //存放上一行的数据
	            CappTextAndImageCell tempobj;
	            for(int i=0;i<colums;i++)
	            {
	               //缓存上一行第i列的数据
	               tempobj=multiList.getCellAt(uprow,i);
	               //将选中行的数据挪到上一行

	               multiList.addCell(uprow,i,multiList.getCellAt(selectRow,i));
	               //将缓存的数据放在选中行
	               multiList.addCell(selectRow,i,tempobj);
	            }
	           multiList.setSelectedRow(uprow);
	         }


	  }


	  /**
	   * 下移按钮事件执行
	   * @param e ActionEvent 按钮触发事件
	   */
	  void downButton_actionPerformed(ActionEvent e) {
	    if( multiList.getTable().isEditing())
	       multiList.getTable().getCellEditor().stopCellEditing();
	    int selectRow = multiList.getSelectedRow();
	    if (selectRow == (multiList.getRowCount() - 1) || selectRow == -1) {
	      return;
	    }
	    else {
	      int downrow = selectRow + 1;
	      int colums = multiList.getTable().getColumnCount();
	      //存放下一行的数据
	      CappTextAndImageCell tempobj;
	      for (int i = 0; i < colums; i++) {
	        //缓存下一行第i列的数据
	        tempobj = multiList.getCellAt(downrow, i);
	        //将选中行的数据挪到下一行
	        multiList.addCell(downrow, i, multiList.getCellAt(selectRow, i));
	        //将缓存的数据放在选中行
	        multiList.addCell(selectRow, i, tempobj);
	      }
	      multiList.setSelectedRow(downrow);
	    }

	  }


	  /**
	   * 计算按钮事件执行
	   * @param e ActionEvent 按钮触发事件
	   */
	  void calculateButton_actionPerformed(ActionEvent e) {

	    calculate();
	  }
	  /**
	   * 设备
	   * @param e
	   */
	  void eqequipButton_actionPerformed(ActionEvent e){
		  
		  try {
			  
			launchChooserDialog("QMEquipment");
			
		} catch (QMRemoteException e1) {
			e1.printStackTrace();
		}
	  }
	  
	//CCBegin SS4
	  /**
	   * 清空工装
	   */
	  void toolDecreaseButton_actionPerformed(ActionEvent e){
		  
		  int selectRow=this.multiList.getSelectedRow();
		  int rowCount=this.multiList.getRowCount();
		  int toolInfoRow=0;
		  if(selectRow<0){
			  
			  JOptionPane.showMessageDialog(getParentJFrame(),
						"请选择要清空并删除的工装!", "提示",
						JOptionPane.INFORMATION_MESSAGE);

				return;
			  
		  }else{
			  
			  
              String toolBsoid=multiList.getCellText(selectRow, 16);
			  
			  if(toolBsoid!=null&&!toolBsoid.equals("")){
				  
				  for(int i=selectRow+1;i<rowCount;i++){
					  
					  String eqInfo=multiList.getCellText(i, 15);
					  String toolid=multiList.getCellText(i, 16);
					  String  meaBsoid=multiList.getCellText(i, 17);
					  
					  if((eqInfo==null||eqInfo.equals(""))&&(meaBsoid==null||meaBsoid.equals(""))&&(toolid==null||toolid.equals(""))){
						  
						  toolInfoRow++;
					  }else{
						  
						  break;
					  }
					  
					  
				  }
				  
				  
				  if(toolInfoRow!=0){
					  
					  
			           for(int j=0;j<=toolInfoRow;j++ ){
				 
			        	   multiList.addTextCell(selectRow+j, 2, "");
			        	   //CCBegin SS14
			        	   multiList.addTextCell(selectRow+j, 3, "");
			        	   //CCEnd SS14
			        	   multiList.addTextCell(selectRow+j, 16, "");
			            }
			           
			           Hashtable eqMap=new Hashtable();
			           
						  if(!eqMap.containsKey(toolBsoid))
			    			     eqMap.put(toolBsoid, 1);
						  
					  //CCBegin SS14
					  //this.materDeleteVec.add(eqMap);
					  this.toolDeleteVec.add(eqMap);
					  //CCEnd SS14
			  }
				  
			  }else{
			    	 
			    	 JOptionPane.showMessageDialog(getParentJFrame(),
								"请选择工装编号!", "提示",
								JOptionPane.INFORMATION_MESSAGE);

						return;
			     }
			  
			  
		  }
		  
		  
	  }
	  
	  /**
	   * 清空材料
	   * @param e
	   */
	  void materialDecreaseButton_actionPerformed(ActionEvent e){
		  
		  int selectRow=this.multiList.getSelectedRow();
		  int rowCount=this.multiList.getRowCount();
		  int materInfoRow=0;
		  if(selectRow<0){
			  
			  JOptionPane.showMessageDialog(getParentJFrame(),
						"请选择要清空并删除的材料!", "提示",
						JOptionPane.INFORMATION_MESSAGE);

				return;
			  
		  }else{
			  
			  String materBsoid=multiList.getCellText(selectRow, 17);
			  
			  if(materBsoid!=null&&!materBsoid.equals("")){
				  
				  for(int i=selectRow+1;i<rowCount;i++){
					  
					  String eqInfo=multiList.getCellText(i, 15);
					  String toolBsoid=multiList.getCellText(i, 16);
					  String  meaBsoid=multiList.getCellText(i, 17);
					  
					  if((eqInfo==null||eqInfo.equals(""))&&(meaBsoid==null||meaBsoid.equals(""))&&(toolBsoid==null||toolBsoid.equals(""))){
						  
						  materInfoRow++;
					  }else{
						  
						  break;
					  }
					  
					  
				  }
				  
				  
				  if(materInfoRow!=0){
					  
					  
			           for(int j=0;j<=materInfoRow;j++ ){
				 
			        	   multiList.addTextCell(selectRow+j, 2, "");
			        	   //CCBegin SS14
			        	   multiList.addTextCell(selectRow+j, 3, "");
			        	   //CCEnd SS14
			        	   multiList.addTextCell(selectRow+j, 17, "");
			            }
			           
			           Hashtable eqMap=new Hashtable();
			           
						  if(!eqMap.containsKey(materBsoid))
			    			     eqMap.put(materBsoid, 1);
						  
					  this.materDeleteVec.add(eqMap);
			  }
				  
			  }else{
			    	 
			    	 JOptionPane.showMessageDialog(getParentJFrame(),
								"请选择材料编号!", "提示",
								JOptionPane.INFORMATION_MESSAGE);

						return;
			     }
			  
			  
		  }
	  }
	  
	  /**
	   * 
	   * 清空设备
	   */
	  void eqequipDecreaseButton_actionPerformed(ActionEvent e){
		  

			  int selectRow=this.multiList.getSelectedRow();
			  int rowCount=this.multiList.getRowCount();
			  int eqInfoRow=0;
			  
			  if(selectRow<0){
				  
				  JOptionPane.showMessageDialog(getParentJFrame(),
							"请选择要清空并删除的设备!", "提示",
							JOptionPane.INFORMATION_MESSAGE);

					return;
				  
			  }else{
				  
				  if(groupname.equals("特性控制")){
					  String eqBsoid=multiList.getCellText(selectRow, 12);
					  
					  if(eqBsoid!=null&&!eqBsoid.equals("")){
						  
						  multiList.addTextCell(selectRow, 3, "");
						  multiList.addTextCell(selectRow, 4, "");
						  multiList.addTextCell(selectRow, 5, "");
						  multiList.addTextCell(selectRow, 12, "");
						  
						  Hashtable eqMap=new Hashtable();
				           
						  if(!eqMap.containsKey(eqBsoid))
			    			     eqMap.put(eqBsoid, 1);
						  
					   eqDeleteVec.add(eqMap);
						  
						  
					  }else{
						  JOptionPane.showMessageDialog(getParentJFrame(),
									"请选择设备编号!", "提示",
									JOptionPane.INFORMATION_MESSAGE);

							return;
						  
					  }
					  
					  
				  }else if(groupname.equals("控制计划")){
				     String eqBsoid=multiList.getCellText(selectRow, 15);
				     
				     if(eqBsoid!=null&&!eqBsoid.equals("")){
				    	 
						  
						  for(int i=selectRow+1;i<rowCount;i++){
							  
							  String eqInfo=multiList.getCellText(i, 15);
							  
							  String toolBsoid=multiList.getCellText(i, 16);
							  String  meaBsoid=multiList.getCellText(i, 17);
							  if((eqInfo==null||eqInfo.equals(""))&&(meaBsoid==null||meaBsoid.equals(""))&&(toolBsoid==null||toolBsoid.equals(""))){
								  
								  eqInfoRow++;
							  }else{
								  
								  break;
							  }
							  
							  
						  }
						  
						  if(eqInfoRow!=0){
							  
							  
					           for(int j=0;j<=eqInfoRow;j++ ){
						 
					        	   multiList.addTextCell(selectRow+j, 2, "");
					        	   //CCBegin SS14
					        	   multiList.addTextCell(selectRow+j, 3, "");
					        	   //CCEnd SS14
					        	   multiList.addTextCell(selectRow+j, 15, "");
					            }
					           
					           Hashtable eqMap=new Hashtable();
					           
								  if(!eqMap.containsKey(eqBsoid))
					    			     eqMap.put(eqBsoid, 1);
								  
							   eqDeleteVec.add(eqMap);
					  }
						  
				    	 
				     }else{
				    	 
				    	 JOptionPane.showMessageDialog(getParentJFrame(),
									"请选择设备编号!", "提示",
									JOptionPane.INFORMATION_MESSAGE);

							return;
				     }
				     
				  }

				  
			  }
			  
			  
	  }
	  
	  //CCEnd SS4
	  
	  
	  
	 
	//CCBegin by leixiao 2008-10-27 原因：解放系统升级 
	  /**
	   * 实现过程流程到控制计划的数据传递
	   * @param e ActionEvent
	   */
	  void inheritButton_actionPerformed(ActionEvent e) {
		    if (groupname.equals("特性控制")) {
		        QMProcedureInfo info = container.getProIfc();
//	          CCBegin by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
		    	if(info == null || info.getProcessControl() == null)
		    	{
		    		
		    		return;    
		    	}
//	          CCEnd by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
		        ExtendAttContainer con = info.getProcessControl();

		       // ExtendAttContainer femacon = info.getFema();
		        //控制计划产品特性
		        Vector productIdentityValue = getExtendAttValue(con,
		            "productAttr");
		        //控制计划过程特性
		        Vector productCharacterValue = getExtendAttValue(con, "processAttr");
		        //控制计划分类
		        Vector sortValue = getExtendAttValue(con, "attrSort");
		        int ii=sortValue.size();
		        int row = this.multiList.getNumberOfRows();
		        
		        if(row==0){
		        for (int i = row; i < row + ii; i++) {
		          for (int j = 0; j < group.getCount(); j++) {
		            addDefalutAttsToTable(i, j, group.getAttributeAt(j));
		          }
		        }
		        //传递内容      
		        for (int k = row, n = 0; k < row + ii; k++, n++) {

		      	  multiList.addTextCell(k, 9, (String) productIdentityValue.elementAt(n));
		      	  multiList.addTextCell(k, 10, (String) productCharacterValue.elementAt(n));
//		      	  multiList.addTextCell(k, 10, (String) sortValue.elementAt(n));
		      	 
		      	  multiList.addSpeCharCell(k, 11, (Vector)sortValue.elementAt(k));
		        }
		    	}
		        else{
		        int srow = this.multiList.getSelectedRow();
		    	if(srow >=0)
		    	{
			      	  multiList.addTextCell(srow, 9, (String) productIdentityValue.elementAt(srow));
			      	  multiList.addTextCell(srow, 10, (String) productCharacterValue.elementAt(srow));
			      	 
			      	  multiList.addSpeCharCell(srow, 11, (Vector)sortValue.elementAt(srow));
		    	}	
		        }

		        
		      	
		    }
	    if (groupname.equals("控制计划")) {
	      QMProcedureInfo info = container.getProIfc();
//	    CCBegin by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
	      if(info == null || info.getProcessControl() == null)
	      {
	    	  return;    
	      }
//	    CCEnd by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
	      ExtendAttContainer con = info.getProcessFlow();

	      ExtendAttContainer femacon = info.getFema();
	      //特性编号
	    //  Vector numberValue = getExtendAttValue(con, "characterNum");
	      //产品特性
	      Vector productIdentityValue = getExtendAttValue(con,
	          "productIdentity");
	      //过程特性
	      Vector productCharacterValue = getExtendAttValue(con, "productCharacter");
	      //分类
	      Vector sortValue = getExtendAttValue(con, "attrSort");
	      int ii=sortValue.size();


	      //首先添加行
	      int row = this.multiList.getNumberOfRows();
	      if(row==0){
	      for (int i = row; i < row + ii; i++) {
	        for (int j = 0; j < group.getCount(); j++) {
	          addDefalutAttsToTable(i, j, group.getAttributeAt(j));
	        }
	      }
	      //传递内容      
	      for (int k = row, n = 0; k < row + ii; k++, n++) {

	    	  multiList.addTextCell(k, 5, (String) productIdentityValue.elementAt(n));
	    	  multiList.addTextCell(k, 6, (String) productCharacterValue.elementAt(n));
//	    	  multiList.addTextCell(k, 15, (String) sortValue.elementAt(n));
	    	 
	      	  multiList.addSpeCharCell(k, 7, (Vector)sortValue.elementAt(n));
	      }
	  	}
	      else{
	    	  int srow = this.multiList.getSelectedRow();
	        	if(srow >=0)
	        	{
	      	      	  multiList.addTextCell(srow, 5, (String) productIdentityValue.elementAt(srow));
	      	      	  multiList.addTextCell(srow, 6, (String) productCharacterValue.elementAt(srow));
//	      	      	  multiList.addTextCell(srow, 15, (String) sortValue.elementAt(srow));
	      	      	
	   	      	     multiList.addSpeCharCell(srow, 7, (Vector)sortValue.elementAt(srow));
	        	}	
	      }
	    }
	    if (groupname.equals("过程FMEA")) {
	        QMProcedureInfo info = container.getProIfc();
//	      CCBegin by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
	    	if(info == null || info.getProcessControl() == null)
	    	{
	    		return;    
	    	}
//	      CCEnd by liuzc 2009-12-31 原因：工步扩展属性传递功能错误，将工序传给工步 参见TD=2758
	        ExtendAttContainer con = info.getProcessControl();

	        //分类
	        Vector sortValue = getExtendAttValue(con, "attrSort");
	        if(sortValue==null||sortValue.size()==0){
	         ExtendAttContainer con1 = info.getProcessFlow();         
	         //分类
	         sortValue = getExtendAttValue(con1, "sort");
	        }
	        //首先添加行
	        int row = this.multiList.getNumberOfRows();
	      //  System.out.println("-----"+row+"   "+sortValue);
	        if(row==0){
	          for (int i = row; i < row + sortValue.size(); i++) {
	            for (int j = 0; j < group.getCount(); j++) {
	              addDefalutAttsToTable(i, j, group.getAttributeAt(j));
	            }
	          }
	          for (int k = 0; k < sortValue.size(); k++) {
	        	  multiList.addTextCell(k + row, 4, (String) sortValue.elementAt(k));
	          }
	        }
	        else{

	        	 int srow = this.multiList.getSelectedRow();
	        	 //System.out.println("-----------不为空"+srow);
	        	if(srow >=0)
	        	{
	      	      	  multiList.addTextCell(srow, 4, (String) sortValue.elementAt(srow));
	        	}	
	        }

	      }
	  }

	 // CCEnd by leixiao 2008-10-27 原因：解放系统升级 
	  // CCBegin by leixiao 2009-7-6 原因：解放系统升级 ,增加复制按钮
	  /**
	   * 添加属性复制功能。
	   * @param e ActionEvent
	   * @author l 2008-12-26
	   */
	  void copyAddButton_actionPerformed(ActionEvent e)
	  {
	    int srow = this.multiList.getSelectedRow();
	    	if(srow >=0)
	    	{
	        int rows = this.multiList.getNumberOfRows();
	        int row = this.multiList.getNumberOfRows();
	        for (int i = 0; i < group.getCount(); i++)
	        {
	          addDefalutAttsToTable(row, i, group.getAttributeAt(i));
	          multiList.addCell(row,i,multiList.getCellAt(srow,i));
	        }
	    }
	  }
	  // CCEnd  by leixiao 2009-7-6 原因：解放系统升级 
	  
	  /**
	   * 工装
	   */
	  void toolButton_actionPerformed(ActionEvent e){
		  
		  try {
			  
			launchChooserDialog("QMTool");
			
		} catch (QMRemoteException e1) {
			e1.printStackTrace();
		}
	  }
	  /**
	   * 材料
	   * @param e
	   */
	  void materialButton_actionPerformed(ActionEvent e){
		  
		  try {
			  
				launchChooserDialog("QMMaterial");
				
			} catch (QMRemoteException e1) {
				e1.printStackTrace();
			}

	  }
	  
	  /**
	   * 计算按钮事件执行
	   * @param e ActionEvent 按钮触发事件
	   * 问题（1）20080822 xucy  修改原因：将“过程流程”中的相同列表项内容“全部（包括多条列表项内容）”复制到“控制计划”内
	   */
	  void attrButton_actionPerformed(ActionEvent e)
	  {
	      if (container.getSecondClassifyValue().equals("控制计划")) 
	      {
	    	  String[] processFlowHead = flowMultiList.getHeadings();//(String[])TechnicsStepJPanel.list.get(0);
	    	  String[]  controlPlanHead = multiList.getHeadings();//(String[])TechnicsStepJPanel.list.get(1);
	    	  Vector vec = compareTableHeader(processFlowHead,controlPlanHead);
	    	  int  flowRows =flowMultiList.getRowCount();
	    	  int planRows = multiList.getRowCount();
	    	  if(planRows > 0)
	    	  {
	    		  if(planRows <= flowRows)
	    		  {
	    			  for(int d = planRows; d < flowRows; d++)
	    		      {
	    		          for (int m = 0; m < group.getCount(); m++)
	        			  {
	        			      addDefalutAttsToTable(d, m, group.getAttributeAt(m));
	        			  }
	    		      }
	    		  }

	    		  for(int i = 0; i < vec.size(); i++)
	        	  {
	        	      int[] str = (int[])vec.elementAt(i);
	    		      {
	    		          for(int n = 0; n < flowRows; n++)
	    			      {
	    			          String flowStr = flowMultiList.getCellText(n, str[0]);
	    				      String planStr = multiList.getCellText(n, str[1]);
	    				      if(planStr == null ||planStr.equals(""))
	    				      {
	    				          multiList.addTextCell(n, str[1], flowStr);
	    				      }
	    			      }
	    				
	    		      }
	    	      }
	    	  }	
	    	  else
	    	  {
	    	      for(int p = 0; p < flowRows; p++)
	    		  {
	    		      //int row = this.multiList.getNumberOfRows();
	    			  for (int q = 0; q < group.getCount(); q++)
	    			  {
	    			      addDefalutAttsToTable(p, q, group.getAttributeAt(q));
	    			  }
	    			  for(int i = 0; i < vec.size(); i++)
	    			  {
	    			      int[] str = (int[])vec.elementAt(i);
	    			      String flowStr = flowMultiList.getCellText(p, str[0]);
	    			      String planStr = multiList.getCellText(p, str[1]);
	    			      if(planStr == null ||planStr.equals(""))
	    			      {
	    				      multiList.addTextCell(p, str[1], flowStr);
	    			      }
	    			  }
	    		  }
	    	  }
		  } 
	  }

	  

	  //oneHeader和twoHeader分别是两个表格的表头字符串数组。
	  //如果该方法返回的Vector中有两个元素，例如：[3,4]和[7,2]，则说明第一个表格的第3列和第二个表格的
	  //第4列表头相同，第一个表格的第7列和第二个表格的第2列表头相同
	  public  Vector compareTableHeader(String[] oneHeader,String[] twoHeader)
	  {
	      Vector vec=new Vector();
	      if(null==oneHeader||null==twoHeader)
	          return vec;
	      for(int i=0;i<oneHeader.length;i++)
	      {
	          for(int j=0;j<twoHeader.length;j++)
	          {
	              if(null!=oneHeader[i]&&null!=twoHeader[j]&&oneHeader[i].equals(twoHeader[j]))
	                  vec.addElement(new int[]{i,j});
	          }
	      }
	      return vec;       
	  }
	  //add by wangh on 20070709 用于编辑按扭的监听方法.
	  void editButton_actionPerformed(ActionEvent e){
		  ExtendAttGroup group;
		  
		  group = this.group.duplicate();
		  //得到所选择行的行号;
		  int row = multiList.getSelectedRow();

		  //得到总共列数;
		  int col = multiList.getNumberOfCols();
		  if(col*row>=0){
		  ArrayList rowObj = new ArrayList();
		  Vector nameVec = new Vector();
		  Vector rowVector = new Vector();
		  Vector contentVec = new Vector();
		  for(int i = 0;i<col;i++){
		  //得到选定一行的每个表格内的具体内容.
		  //begin CR1
		  ExtendAttModel model = group.getAttributeAt(i);
		  String type = model.getAttType();
		  if(type.equalsIgnoreCase("SpecChar"))
		  {
		      Vector vec = (Vector)multiList.getSelectedObject(row, i);
		      contentVec.add(vec);
		  }
		  else if(type.equalsIgnoreCase("String"))
		  {
			  HashMap map = model.getFeature();
	          String refType = (String) map.get("RefType");
	          String oneRow = "";
	          if (refType == null)
	          {
	        	  oneRow = (String)this.multiList.getSelectedObject(row,
	                      i);
	          }
	          else
	          if (refType.equalsIgnoreCase("EnumerateType"))
	          {
	        	  oneRow = (String)this.multiList.getSelectedObject(row,
	                      i);
	          }
	          else
	          if (refType.equalsIgnoreCase("ComboAtts"))
	          {
	        	  oneRow = multiList.getCellAt(row, i).getStringValue() +
	            ";" +
	            (String) multiList.getCellAt(row, i).getValue();
	          }
			  //String oneRow = multiList.getCellText(row, i);
			  contentVec.add(oneRow);
		  }
		  //Begin CR3
		  else 
		  {
			  String str = (String)this.multiList.getSelectedObject(row, i);
			  contentVec.add(str);
		  }
		  //End CR3
		  //end CR1
		  //得到所有表头.
		  String head = multiList.getHeading(i);
		  nameVec.addElement(head);

		  CappExAttrPanel cappExAttrPanel = new CappExAttrPanel();
		  //ExtendAttModel model = group.getAttributeAt(i);//CR1
		  //得到每个表格的值的控件
		  JComponent component = cappExAttrPanel.getCompoment(model);
		  rowVector.addElement(component);
		  //contentVec.add(oneRow);//CR1
		  }
		  rowObj.add(nameVec);
		  rowObj.add(rowVector);
		  rowObj.add(contentVec);

		  //获得自省文件中对应的一及分类名和二及分类名
		  String firstClassi = container.getFirstClassify();
		  String secondClassi = container.getSecondClassifyValue();

		  TParamJDialog p = new TParamJDialog(firstClassi,parentJFrame,secondClassi,rowObj);

		  Vector obj=(Vector)p.showDialog();

		  for(int j=0;j<col;j++){
			  ExtendAttModel model = group.getAttributeAt(j);
			  String type = model.getAttType();//CR1
			  //将JDialog中修改的数据传回multiList中显示。
			  if (type.equalsIgnoreCase("Coding")){
				         HashMap map = model.getFeature();
				         String sorttype = (String) map.get("SortType");
				         Collection sorts = (Collection)this.etMaps.get(sorttype);
				         Iterator ite = sorts.iterator();
				         Vector vec = new Vector();
				          CodingIfc et2 = null;
				         while (ite.hasNext()) {
				           et2 = (CodingInfo) ite.next();

				           vec.add( et2.getCodeContent());
				         }
				         multiList.addComboBoxCell(row, j,  obj.get(j), vec);
				         }
			  //begin CR1
			  else if(type.equalsIgnoreCase("String")){
				  //multiList.addTextCell(row, j, String.valueOf((String)obj.get(j)));
				  HashMap map = model.getFeature();
		            String refType = (String) map.get("RefType");
		            if (refType == null)
		            {
		                if (obj != null)
		                {
		                    multiList.addTextCell(row, j, (String)obj.get(j));
		                }
		                else
		                {
		                    multiList.addTextCell(row, j, "");
		                }
		            }
		            else
		            if (refType.equals("EnumerateType"))
		            {
		                String classPath = (String) map.get("classpath");
		                EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
		                EnumeratedType et;
		                Vector vec = new Vector();
		                for (int k = 0; k < ets.length; k++)
		                {
		                    et = (EnumeratedType) ets[k];
		                    vec.add(et.getDisplay(local));
		                }
		                if (obj != null)
		                {
		                    this.multiList.addComboBoxCell(row, j, obj.get(j).toString(), vec);
		                }
		                else
		                {
		                    this.multiList.addComboBoxCell(row, j, "", vec);
		                }
		            }
		            else
		            if (refType.equals("ComboAtts"))
		            {

		                String sorttype = (String) map.get("SortType");
		                Collection sorts = (Collection)this.etMaps.get(sorttype);
//		                String valueStr = "";
//		                String sortStr = "";
//		                if (obj != null)
//		                {
//		                    StringTokenizer ston = new StringTokenizer(String.valueOf((String)obj.get(j)),
//		                            ";");
//		                    if (ston.hasMoreTokens())
//		                    {
//		                        valueStr = ston.nextToken();
//		                    }
//		                    if (ston.hasMoreTokens())
//		                    {
//		                        sortStr = ston.nextToken();
//		                    }
//		                }
		                Iterator ite = sorts.iterator();
		                Vector vec = new Vector();
		                while (ite.hasNext())
		                {
		                    vec.add(((CodingInfo) ite.next()).getCodeContent());
		                }
	                    String[] attr = (String[])obj.get(j);
		                this.multiList.addComboBoxCell(row, j, attr[0], attr[1], vec);
		            }

			  }
			  else if(type.equalsIgnoreCase("SpecChar")){
				  if (obj != null)
		             {
					     Vector vec = new Vector();
					     vec.addElement((String)obj.get(j));
		                 multiList.addSpeCharCell(row, j, vec);
		             }
		             else
		             {
		                 multiList.addSpeCharCell(row, j, new Vector());
		             }
				  
			  }//end CR1

		  }
	  }
		  return;
	  }

	  /**
	   * 根据属性名获得列
	   * @param s String 属性名
	   * @return int 属性所在列
	   */
	  public int getColByAttname(String s) {
	    String[] heads = this.multiList.getHeadings();

	    for (int i = 0; i < heads.length; i++) {
	      if (heads[i].equals(s))
	        return i;
	    }
	    return -1;
	  }

	  private void AssembleCalculateForZC() throws NumberFormatException{
		  
		  
		  //得到选中行
	      int row= multiList.getSelectedRow();
		  int j1=this.getColByAttname("措施结果(S)");
	      int j2=this.getColByAttname("措施结果(O)");
	      //CCBegin SS1
	      int j3=this.getColByAttname("措施结果(D)");
	      //CCEnd SS1
	      int j4=this.getColByAttname("措施结果PRN");

	      int j5=this.getColByAttname("严重度(s)");
	      int j6=this.getColByAttname("频度数(0)");
	    //CCBegin SS1
	      int j7=this.getColByAttname("探测度(D)");
	    //CCEnd SS1
	      int j8=this.getColByAttname("风险顺序数(PRN)");

	      String arr55 = (String) multiList.getSelectedObject(row, j5);
          String arr66 = (String) multiList.getSelectedObject(row, j6);
        //CCBegin SS1
          String arr77 = (String) multiList.getSelectedObject(row, j7);

          if (!arr55.equals("") && !arr66.equals("")&& !arr77.equals("") )
          {
        	  int newnum1 = Integer.parseInt(arr55) * Integer.parseInt(arr66)* Integer.parseInt(arr77);
        	  //CCEnd SS1
			  //将计算结果放到第3列
			  multiList.addTextCell(row, j8, String.valueOf(newnum1));
          }
          else
          {
        	  if(arr55.equals("")){
        		  String message = "严重度(s)为空";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  else if(arr66.equals("")){
        		  String message = "频度数(o)为空";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	//CCBegin SS1
        	  else if(arr77.equals("")){
        		  String message = "探测度(D)为空";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  }
        	  //CCEnd SS1
        	 
        	  multiList.addTextCell(row, j8, "");
          }

 


	      String arr11 = (String) multiList.getSelectedObject(row, j1);
          //得到第2列的文本
          String arr22 = (String) multiList.getSelectedObject(row, j2);
        //CCBegin SS1
          String arr33 = (String) multiList.getSelectedObject(row, j3);
//        计算
          if (!arr11.equals("") && !arr22.equals("") &&!arr33.equals(""))
          {
        	  int newnum2 = Integer.parseInt(arr11) * Integer.parseInt(arr22) *Integer.parseInt(arr33);
        	//CCEnd SS1
			  //将计算结果放到第3列
			  multiList.addTextCell(row, j4, String.valueOf(newnum2));
          }
          else
          {
        	  if(arr11.equals("")){
        		  String message = "措施结果(S)为空";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  else if(arr22.equals("")){
        		  String message = "措施结果(O)为空";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  
        	//CCBegin SS1
        	  else if(arr33.equals("")){
        		  String message = "措施结果(D)为空";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  }
        	//CCEnd SS1
        	  multiList.addTextCell(row, j4, "");
              
          }
		  
	  }
	  
	  
	  /**
	   * 装配工序的计算
	   *
	   */
		  private void AssembleCalculate()
		  throws NumberFormatException
		  {

	        //得到选中行
		      int row= multiList.getSelectedRow();
//			  CCBegin by liuzc 2009-12-21 原因：解放升级,FMEA计算   参见TD号2685
			  int j1=this.getColByAttname("措施结果(S)");
		      int j2=this.getColByAttname("措施结果(O)");
		      int j3=this.getColByAttname("措施结果(D)");
		      int j4=this.getColByAttname("措施结果PRN");
//		      CCEnd by liuzc 2008-12-21 原因：解放升级,FMEA计算   参见TD号2685

//			    CCBegin by leixiao 2009-12-21 原因：解放升级,FMEA计算
		      //add by wangh on 20070516
		      int j5=this.getColByAttname("严重度(s)");
		      int j6=this.getColByAttname("频度数(o)");
		      int j7=this.getColByAttname("探测度(D)");
		      int j8=this.getColByAttname("风险顺序数(PRN)");
//		    CCEnd by leixiao 2009-12-21 原因：解放升级   
System.out.println("j5====================================================="+j5);
System.out.println("j6====================================================="+j6);
System.out.println("j7====================================================="+j7);
System.out.println("j8====================================================="+j8);
		      String arr55 = (String) multiList.getSelectedObject(row, j5);
	          String arr66 = (String) multiList.getSelectedObject(row, j6);
	          String arr77 = (String) multiList.getSelectedObject(row, j7);

	          if (!arr55.equals("") && !arr66.equals("") && !arr77.equals(""))
	          {
	        	  int newnum1 = Integer.parseInt(arr55) * Integer.parseInt(arr66) *
	              Integer.parseInt(arr77);
				  //将计算结果放到第3列
				  multiList.addTextCell(row, j8, String.valueOf(newnum1));
	          }
	          else
	          {
	        	  if(arr55.equals("")){
	        		  String message = "严重度(s)为空";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	        	  }
	        	  else if(arr66.equals("")){
	        		  String message = "频度数(o)为空";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	        	  }
	        	  else if(arr77.equals("")){
	        		  String message = "探测度(D)为空";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  }
	        	  multiList.addTextCell(row, j8, "");
	          }

	 


		      String arr11 = (String) multiList.getSelectedObject(row, j1);
	          //得到第2列的文本
	          String arr22 = (String) multiList.getSelectedObject(row, j2);
	          String arr33 = (String) multiList.getSelectedObject(row, j3);
//	        计算
	          if (!arr11.equals("") && !arr22.equals("") && !arr33.equals(""))
	          {
	        	  int newnum2 = Integer.parseInt(arr11) * Integer.parseInt(arr22) *
	              Integer.parseInt(arr33);
				  //将计算结果放到第3列
				  multiList.addTextCell(row, j4, String.valueOf(newnum2));
	          }
	          else
	          {
	        	  if(arr11.equals("")){
//	      		  CCBegin by liuzc 2009-12-21 原因：解放升级,FMEA计算   参见TD号2685
	        		  String message = "措施结果(S)为空";
//	        	      CCEnd by liuzc 2009-12-21原因：解放升级,FMEA计算   参见TD号2685
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	        	  }
	        	  else if(arr22.equals("")){
//	      		  CCBegin by liuzc 2009-11-21原因：解放升级,FMEA计算   参见TD号2685
	        		  String message = "措施结果(O)为空";
//	        	      CCEnd by liuzc 2009-12-21 原因：解放升级,FMEA计算   参见TD号2685
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	        	  }
	        	  else if(arr33.equals("")){
//	        	      CCEnd by liuzc 2009-12-21原因：解放升级,FMEA计算   参见TD号2685
	        		  String message = "措施结果(D)为空";
//	        	      CCEnd by liuzc 2009-12-21原因：解放升级,FMEA计算   参见TD号2685
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  }
	        	  multiList.addTextCell(row, j4, "");
	              
	          }

		  }

	  /**
	   *  计算,此方法实现了计算功能,在实施过程中需要根据具体计算规则相应的修改此方法
	   */
	  private void calculate() {
	    String firstClassi = container.getFirstClassify();
	    System.out.println("111111111111111111111111111******************=============="+firstClassi);
	    if (firstClassi.equals("QMAssembleProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	    	  AssembleCalculate();
	    	  }
	    	  catch(NumberFormatException ex){
	              String message = "你输入的需要计算的数据不是整形";
	              String title = QMMessage.getLocalizedMessage(RESOURCE,
	                      "information", null);
	              JOptionPane.showMessageDialog(parentJFrame,
	                                            message,
	                                            title,
	                                            JOptionPane.INFORMATION_MESSAGE);
	              return;
	              }
	      }
	    }
	    else
	    if (firstClassi.equals("QMPMProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	        	  AssembleCalculate();
	        	  }
	        	  catch(NumberFormatException ex){
	                  String message = "你输入的需要计算的数据不是整形";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  return;
	                  }
	      }

	    }
	    else
	    if (firstClassi.equals("QMPushProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	        	  AssembleCalculate();
	        	  }
	        	  catch(NumberFormatException ex){
	                  String message = "你输入的需要计算的数据不是整形";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  return;
	                  }
	      }

	    }
	    else
	    if (firstClassi.equals("QMWeldProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	        	  AssembleCalculate();
	        	  }
	        	  catch(NumberFormatException ex){
	                  String message = "你输入的需要计算的数据不是整形";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  return;
	                  }
	      }

	    }
	    else
	    if (firstClassi.equals("QMGelaProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	        	  AssembleCalculate();
	        	  }
	        	  catch(NumberFormatException ex){
	                  String message = "你输入的需要计算的数据不是整形";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  return;
	                  }
	      }

	    }
	    else
	    if (firstClassi.equals("QMVitrWashProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	        	  AssembleCalculate();
	        	  }
	        	  catch(NumberFormatException ex){
	                  String message = "你输入的需要计算的数据不是整形";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  return;
	                  }
	      }

	    }
	    else
	    if (firstClassi.equals("QMDrillProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	        	  AssembleCalculate();
	        	  }
	        	  catch(NumberFormatException ex){
	                  String message = "你输入的需要计算的数据不是整形";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  return;
	                  }
	      }

	    }
	    else
	    if (firstClassi.equals("QMMachineProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	        	  AssembleCalculate();
	        	  }
	        	  catch(NumberFormatException ex){
	                  String message = "你输入的需要计算的数据不是整形";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  return;
	                  }
	      }

	    }

	    else
	    if (firstClassi.equals("QMPaintProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	        	  AssembleCalculate();
	        	  }
	        	  catch(NumberFormatException ex){
	                  String message = "你输入的需要计算的数据不是整形";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  return;
	                  }
	      }

	    }

	    else
	    if (firstClassi.equals("QMCheckProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	        	  AssembleCalculate();
	        	  }
	        	  catch(NumberFormatException ex){
	                  String message = "你输入的需要计算的数据不是整形";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  return;
	                  }
	      }

	    }

	    else
	    if (firstClassi.equals("QMCheckProcedure")) {
	      if (container.getSecondClassifyValue().equals("过程FMEA")) {
	    	  try{
	        	  AssembleCalculate();
	        	  }
	        	  catch(NumberFormatException ex){
	                  String message = "你输入的需要计算的数据不是整形";
	                  String title = QMMessage.getLocalizedMessage(RESOURCE,
	                          "information", null);
	                  JOptionPane.showMessageDialog(parentJFrame,
	                                                message,
	                                                title,
	                                                JOptionPane.INFORMATION_MESSAGE);
	                  return;
	                  }
	      }

	    }
	    else if(firstClassi.equals("zcQMMachineProcedure")){
	    	
	    	if (container.getSecondClassifyValue().equals("过程FMEA")) {
		    	  try{
		        	  AssembleCalculateForZC();
		        	  }
		        	  catch(NumberFormatException ex){
		                  String message = "你输入的需要计算的数据不是整形";
		                  String title = QMMessage.getLocalizedMessage(RESOURCE,
		                          "information", null);
		                  JOptionPane.showMessageDialog(parentJFrame,
		                                                message,
		                                                title,
		                                                JOptionPane.INFORMATION_MESSAGE);
		                  return;
		                  }
		      }
	    	
	    }


	  }
	  //CCBegin by leixiao 2008-10-27 原因：解放系统升级  
	  /**
	   *  过程流程的属性带到控制计划中
	   * 根据扩展属性名,获得业务对象的扩展属性值
	   * @param extendattriedifc 可使用扩展属性的业务对象
	   * @param attname 扩展属性名称
	   * @return Object 指定扩展属性的属性值(对于成组的属性,返回主要数据值组的数据值)
	   */
	  private Vector getExtendAttValue(ExtendAttContainer container,
	                                   String attname) {
	    Vector obj = null;
	    ExtendAttModel model = null;
	    //获得业务对象的扩展属性容器
	    if (container == null) {
	      return null;
	    }
	    //从容器中获得指定的属性模型
	    model = container.findExtendAttModel(attname);
	    //如果非成组属性没有
	    if (model == null) {
	      //判断容器是否有成组属性
	      if (container.isGroup()) {
	        ExtendAttGroup group = null;
	        Iterator names = container.getAttGroupNames().iterator();
	        Vector vec = new Vector();
	        Vector groups;
	        while (names.hasNext()) {
	          String name = (String) names.next();
	          groups = container.getAttGroups(name);
	          if (groups != null) {
	            for (int i = 0; i < groups.size(); i++) {
	              group = (ExtendAttGroup) groups.elementAt(i);
	              model = group.findExtendAttModel(attname);
	              if (model != null) {
	                vec.add(model.getAttValue());
	              }
	            } //end for
	          }
	        }
	        if (vec.size() > 0) {
	          obj = vec;
	        }
	      } //end if

	      return obj;
	    } //end if
	    return obj;
	  }
	 // CCEnd by leixiao 2008-10-27 原因：解放系统升级 
	  
	  
	 //变速箱简易所见即所得 添加 begin 
	   //CCBegin SS4
	    int addRowCount = 0;
	    Vector dateVec = null;
	    boolean turnoff = true;
	  
	  class myTableModelListener  implements TableModelListener{

		  
//		public void tableChanged(TableModelEvent arg0) {
//
//				int row = multiList.getTable().getEditingRow();
//				int column = multiList.getTable().getEditingColumn();
//				
//				
////				System.out.println("row===column===============" + row
////						+ "============" + column);
//
//				if (row != -1 && column != -1) {
//					
//					
//					//每列内容的最大像素
//					int maxLen= new Integer(RemoteProperty.getProperty(
//				            "com.faw_qm.extend.columnNum-"+column, "55")).intValue();
//					
////					System.out.println("maxLen==============================="+maxLen);
//					
//
//					int type = multiList.getCellAt(row, column).getType();
//
//					if (type == 5) {
//
//						SpeCharaterTextPanel sepChar = multiList.getCellAt(row,
//								column).getSpecialCharacter();
//
//						String editData = sepChar.save();
////						System.out
////								.println("vvvv========11111111111==全部文本=========="
////										+ editData);
//
//						 MultiLineController mlc = new MultiLineController();
//						 Vector dataVec=null;
////						 System.out.println("editData====================***************===================="+editData);
//						if(editData.indexOf((char)128)!=-1&&editData.indexOf((char)129)!=-1){
//							
//						    DataSource dataSource = sepChar.resumeData(editData);// 通过字符串获得封装对象
//						   
//						    dataVec = mlc.newSplitLine(dataSource, maxLen);
//						    
//						}else{
//							  int index=editData.indexOf((char)128);
//							  editData=editData.substring(0, index);
//							  dataVec =mlc.doChangeLine(editData, maxLen);
//						}
//
//	                   if(dataVec!=null){
//
//						for (int k = 0; k < dataVec.size(); k++) {
//							
//							
//							if(k!=0){
//								CappTextAndImageCell nexRowObj=multiList.getCellAtForBSX(row+k, column);
//								
//								if(nexRowObj==null){
//									addNewRow();
//									multiList.setSelectedRow(multiList.getSelectedRow()+1);
//									
//								}else if(nexRowObj!=null&&nexRowObj.getSpecialCharacter().save().indexOf((char)128)!=0){
//									
//									addNewRow();
//									multiList.setSelectedRow(multiList.getSelectedRow()+1);
//									
//								}
//							}
//							else{
//							   multiList.getCellAt(row, column)
//									.getSpecialCharacter().clearAll();
//							}
//
//							String str = (String) dataVec.get(k);
//							Vector v1 = new Vector();
//							v1.add(str);
//							multiList.getCellAt(row + k, column)
//									.getSpecialCharacter().resumeData(v1);
////							System.out.println("=======end===========");
//
//						}
//					}
//
//					}
//					// 文本框
//					else if (type == 1) {
//
//						String editStrData = multiList.getCellAt(row, column)
//								.getStringValue();
//
////						DataSource dataSource = new DataSource();
////						dataSource.setTexts(editStrData);
//
//						MultiLineController mlc = new MultiLineController();
//						Vector dataVec = mlc.doChangeLine(editStrData, maxLen);
//
//						
//						
//						if(dataVec.size()>1)
//						{
//							for (int k = 0; k < dataVec.size(); k++) {
//		
//								String str = (String) dataVec.get(k);
////								String sepCharBeginFlag=String.valueOf((char) 128);
////								
////								int bb=str.indexOf(sepCharBeginFlag);
////								str=str.substring(0,bb);
//								
//								if (k != 0) {
//									
//									String nexRowText=multiList.getCellText(row+k, column);
//									if(nexRowText==null){
//										
//										addNewRow();
//										multiList.setSelectedRow(multiList.getSelectedRow()+1);
//									}else if(nexRowText!=null&&!nexRowText.equals("")){
//										
//										addNewRow();
//										multiList.setSelectedRow(multiList.getSelectedRow()+1);
//									}
//									
//									 multiList.addTextCell(row+k, column,
//											  str);
//		
//								}else {
//									
//									
//									multiList.addTextCell(row, column, str);
//								}
//							}
//		
//						}
//					}
//
//				}
//
//			}
		  
		  public void tableChanged(TableModelEvent arg0) {
			  
			  
			  if(turnoff)
	            {
	                autoAddRowViewDate();
	                turnoff = true;
	            }
			  
		  }

		  
	  }
	  
	  String editData = "";
	  
	  private void autoAddRowViewDate()
	    {
	        int editRow = multiList.getTable().getEditingRow();
	        int editColumn = multiList.getTable().getEditingColumn();
	        if(editRow != -1 && editColumn != -1)
	        {
	            // 下移数据集合
	            Vector valueDownVec = new Vector();
	            
	            // 每列内容的最大像素
	            
	            int maxLen=0;
	            if(groupname.equals("特性控制")){
//	            	CCBegin SS11
//	            	 maxLen= new Integer(RemoteProperty.getProperty(" gc_com.faw_qm.extend.columnNum-" + editColumn, "55")).intValue();
	            	maxLen= new Integer(RemoteProperty.getProperty("gc_com.faw_qm.extend.columnNum-" + editColumn, "55")).intValue();
//	            	CCEnd SS11
	            }
	            	
	            if (groupname.equals("控制计划")) {
//	            	CCBegin SS11
//	            	maxLen= new Integer(RemoteProperty.getProperty(" cont_com.faw_qm.extend.columnNum-" + editColumn, "55")).intValue();
	            	maxLen= new Integer(RemoteProperty.getProperty("cont_com.faw_qm.extend.columnNum-" + editColumn, "55")).intValue();
//	            	CCEnd SS11
	            		 
	            }
	            	 
	            if (groupname.equals("过程FMEA")) {
//	            	CCBegin SS11
//	            	maxLen= new Integer(RemoteProperty.getProperty(" fm_com.faw_qm.extend.columnNum-" + editColumn, "55")).intValue();
	            	maxLen= new Integer(RemoteProperty.getProperty("fm_com.faw_qm.extend.columnNum-" + editColumn, "55")).intValue();
//	            	CCEnd SS11
	            }
	       	 
	            	
	            int type = multiList.getCellAt(editRow, editColumn).getType();
	            System.out.println("type AA:"+type);
	            // 特殊符号
	            if(type == 5)
	            {
	                SpeCharaterTextPanel sepChar = multiList.getCellAt(editRow, editColumn).getSpecialCharacter();
	                editData = sepChar.save();
	                MultiLineController mlc = new MultiLineController();
	                Vector dataVec = null;
	                // 包含特殊符号
	                if(editData.indexOf((char)128) != -1 && editData.indexOf((char)129) != -1)
	                {
	                    DataSource dataSource = sepChar.resumeData(editData);// 通过字符串获得封装对象
	                    dataVec = mlc.newSplitLine(dataSource, maxLen);

	                }else
	                {
	                    int index = editData.indexOf((char)128);
	                    editData = editData.substring(0, index);
	                    dataVec = mlc.doChangeLine(editData, maxLen);
	                }
	                if(dataVec != null && dataVec.size() > 1)
	                {
	                    addRowCount = dataVec.size();
	                    int allRowCount = multiList.getRowCount();
	                    SpeCharaterTextPanel nexRowsepChar;
	                    for(int a = editRow + 1;a < allRowCount;a++)
	                    {
//	                        String nexRowText=multiList.getCellAt(a, editColumn).getStringValue();
	                         nexRowsepChar = multiList.getCellAt(a, editColumn).getSpecialCharacter();
	                        String nexRowText = nexRowsepChar.save();
	                        valueDownVec.add(nexRowText);
	                    }

	                    turnoff = false;

	                    int insertRows =  emptySpeRows(editRow,editColumn,dataVec.size() - 1);
	                    // 在列表末尾添加折行所需要的行
	                    this.addNewRowOnLast(dataVec.size()-insertRows-1);
//	                    this.addNewRowOnLast(dataVec.size() - 1);

	                    allRowCount = multiList.getRowCount();
	                    for(int e = valueDownVec.size();e >0;e--)
	                    {
	                        String value = (String)valueDownVec.get(e-1);
	                        if(value==null)
	                            value = "";
	                        Vector v = new Vector();
	                        v.add(value);
	                        multiList.getCellAt(allRowCount +e-valueDownVec.size()-1, editColumn).getSpecialCharacter().clearAll();
	                        multiList.getCellAt(allRowCount +e-valueDownVec.size()-1, editColumn).getSpecialCharacter().resumeData(v);
	                    }

	                    for(int k = 0;k < dataVec.size();k++)
	                    {
	                        String str = (String)dataVec.get(k);
	                        Vector v = new Vector();
	                        v.add(str);
	                        multiList.getCellAt(editRow + k, editColumn).getSpecialCharacter().clearAll();
	                        multiList.getCellAt(editRow + k, editColumn).getSpecialCharacter().resumeData(v);
	                    }
	                }
	            }
	            // 文本框
	            else if(type == 1)
	            {
	                String editStrData = multiList.getCellAt(editRow, editColumn).getStringValue();
	                MultiLineController mlc = new MultiLineController();
	                Vector dataVec = mlc.doChangeLine(editStrData, maxLen);
//	                CCBegin SS7
//	                if(editColumn==1||editColumn==14)
//	                    return;
//	                CCEnd SS7

	                if(dataVec != null && dataVec.size() > 1)
	                {
	                    addRowCount = dataVec.size();
	                    System.out.println("addRowCount BB:"+addRowCount);
	                    int allRowCount = multiList.getRowCount();
	                    System.out.println("allRowCount CC:"+allRowCount);
	                    for(int a = editRow + 1;a < allRowCount;a++)
	                    {
	                        String nexRowStrData = multiList.getCellAt(a, editColumn).getStringValue();
	                        System.out.println("nexRowStrData DD:"+nexRowStrData);
	                        valueDownVec.add(nexRowStrData);
	                    }
	                    
	                    turnoff = false;
	                 
	                    int insertRows =  emptyRows(editRow,editColumn,dataVec.size() - 1);
	                    System.out.println("insertRows EE:"+insertRows);
	                    //CCBegin SS15
	                    if(groupname.equals("控制计划"))
	                    {
	                    	for(int ins=0;ins<insertRows;ins++)
	                    	{
	                    		
	                    	int row = ins+allRowCount;
	                    	int row1 = editRow + ins;
	                    	
	                    	
	            for (int i = 0; i < group.getCount(); i++)
	            {
	                addDefalutAttsToTable(row, i, group.getAttributeAt(i));
	            }
	            for (int i = row - 1; i > row1; i--)
	            {
	                //CCBegin SS15
	                //multiList.moveDown(i);
	                //不进行所有行的下移，而是只针对前5列外的内容进行下移
	              if (groupname.equalsIgnoreCase("控制计划"))
	              {
	                ExtendAttModel model;
	                for (int j = 4; j < group.getCount()-3; j++)
	                {
	                	model = group.getAttributeAt(j);
	                	String temp = null;
	                	if (model.getAttType().equalsIgnoreCase("SpecChar"))
	                  {
	                  	multiList.addSpeCharCell(i+1, j, (Vector)this.multiList.getSelectedObject(i, j));
	                  	if(i==(row1+1))
	                  	{
	                  		Vector tv = new Vector();
	                  		tv.add("");
	                  		multiList.addSpeCharCell(i, j, tv);
	                  	}
	                  }
	                  else if (model.getAttType().equals("Coding"))
	                  {
	                  	HashMap map = model.getFeature();
	                  	String sorttype = (String) map.get("SortType");
	                  	Collection sorts = (Collection)this.etMaps.get(sorttype);
	                  	Iterator ite = sorts.iterator();
	                  	Vector vec = new Vector();
	                  	vec.add("");
	                  	while (ite.hasNext())
	                  	{
	                  	 	CodingInfo info = (CodingInfo)ite.next();
	                  	 	if(info.getCodeContent().equalsIgnoreCase("C"))
	                  	 	  vec.add(0,info.getCodeContent());
	                  	 	else
	                  	 	  vec.add(info.getCodeContent());
	                  	}
	                  	temp = (String)this.multiList.getSelectedObject(i, j);
	                  	if (temp != null)
	                  	{
	                  		multiList.addComboBoxCell(i+1, j, temp, vec);
	                  	}
	                  	else
	                  	{
	                  		multiList.addComboBoxCell(i+1, j, "", vec);
	                  	}
	                  	if(i==(row1+1))
	                  	{
	                  		multiList.addComboBoxCell(i, j, "", vec);
	                  	}
	                  }
	                  else if (model.getAttType().equalsIgnoreCase("String"))
	                  {
	                   	temp = (String)this.multiList.getSelectedObject(i, j);
	                   	if (temp != null)
	                   	{
	                   		multiList.addTextCell(i+1, j, temp.toString());
	                   	}
	                   	else
	                   	{
	                   		multiList.addTextCell(i+1, j, "");
	                   	}
	                   	if(i==(row1+1))
	                   	{
	                   		multiList.addTextCell(i, j, "");
	                   	}
	                  }
	                }
	              }
	              else
	              {
	              	multiList.moveDown(i);
	              }
	            }
	                    	}
	                    }
	                    //CCEnd SS15
	                    // 在列表末尾添加折行所需要的行
	                    this.addNewRowOnLast(dataVec.size()-insertRows-1);
//	                    this.addNewRowOnLast(dataVec.size() - 1);
	                    
	                    allRowCount = multiList.getRowCount();
	                    System.out.println("allRowCount FF:"+allRowCount);
	                    for(int e = valueDownVec.size();e >0;e--)
	                    {
	                        String value = (String)valueDownVec.get(e-1);
	                        System.out.println("value GG:"+(allRowCount +e-valueDownVec.size()-1)+"---"+editColumn+"---"+value);
	                        multiList.addTextCell(allRowCount +e-valueDownVec.size()-1, editColumn,value);
	                    }
	                    
	                    for(int k = 0;k < dataVec.size();k++)
	                    {
	                        String str = (String)dataVec.get(k);
	                        System.out.println("str HH:"+(editRow + k)+"---"+editColumn+"---"+str);
	                        multiList.addTextCell(editRow + k, editColumn, str);
	                    }
	                }
	            }
	            valueDownVec = null;
	        }

	    }


	  /**
	   * 探测指定列的空行数
	   * @param row
	   * @param col
	   * @param count
	   * @return
	   * //20140616 
	   * int
	   */
	  private int emptyRows(int row,int col,int count)
	  {
	      String temp;
	      int rows = 0;
	      for(int i=1;i<=count;i++)
	      {
	          temp = multiList.getCellText(row+i, col);
	          if(temp==null)
	              return rows;
	          if(!temp.isEmpty())
	              return rows;
	          rows++;
	      }
	      return rows;
	  }
	  /**
	   * 在末尾添加空行
	   * @param addRowCout
	   */
	  public void addNewRowOnLast(int addRowCout)
	    {

	        int row1 = multiList.getSelectedRow();
	        if(addRowCout != 0)
	        {
	            for(int a = 0;a < addRowCout;a++)
	            {

	                int row = multiList.getNumberOfRows();

	                if(row1 != -1)
	                {
	                    for(int i = 0;i < group.getCount();i++)
	                    {
	                        addDefalutAttsToTable(row, i, group.getAttributeAt(i));
	                    }

	                }
	            }
	        }
	    }
	  
	  /**
	   * 探测特殊符号指定列的空行数
	   * @param row
	   * @param col
	   * @param count
	   * @return
	   * //20140616 
	   * int
	   */
	  private int emptySpeRows(int row,int col,int count)
	  {
	      SpeCharaterTextPanel temp;
	      int rows = 0;
	      CappTextAndImageCell text;
	      for(int i=1;i<=count;i++)
	      {
	          text = multiList.getCellAt(row+i, col);
	          if(text==null)
	              return rows;
	          temp = text.getSpecialCharacter();
//	          String str = temp.save();
//	          temp = multiList.getCellText(row+i, col);
	          if(temp==null)
	              return rows;
	          if(temp.save().length()!=1)
	              return rows;
	          rows++;
	      }
	      return rows;
	  }
	  
	  
	  //CCEnd SS4
	  /**
	   * 获得本地化显示信息
	   * @param s 资源文件中的key
	   * @param aobj 参数集合
	   * @return String 本地化显示信息
	   */
	  private String display(String s, Object aobj[])
	  {
	      String s1 = "";
	      if (resource == null)
	      {
	    	  
	          initResources();
	      }
	      s1 = QM.getLocalizedMessage(RESOURCEOther, s, aobj, null);

	      return s1;
	  }
	  /**
	   * 初始化资源
	   */
	  private void initResources()
	  {
	      try
	      {
	          if (resource == null)
	          {
	              resource = ResourceBundle.getBundle(RESOURCEOther, Locale.getDefault());
	              return;
	          }
	      }
	      catch (MissingResourceException _exception)
	      {
	          _exception.printStackTrace();
	      }
	  }
	  
	  //CCBegin SS4
	  
	  
	  /**
	   * 插入工装
	   * @param tool
	   * @param row
	   * @param col
	   * //20140616
	   * void
	   */
	  private void insertTool(QMToolInfo tool,int row,int col)
	  {
	      String no = "--";
	      String id = multiList.getCellText(row, 16);

	      if(id == null || id.isEmpty()) 
	      {

	          int allrow = emptyRows(row,col,2);
	          downRow(row, col, 2-allrow,false);

	    
	          multiList.addTextCell(row, 16, tool.getBsoID());
	          multiList.addTextCell(row, col, tool.getToolNum());
	          if(tool.getToolSpec() == null || tool.getToolSpec().length() == 0)
	                multiList.addTextCell(row + 1, col, no);
	            else
	                multiList.addTextCell(row + 1, col, tool.getToolSpec());
	          if(tool.getToolName() == null || tool.getToolName().length() == 0)
	                multiList.addTextCell(row + 2, col, tool.getToolName());
	            else
	                multiList.addTextCell(row + 2, col, tool.getToolName());

	      }else if(!id.isEmpty())
	      {
	          int allrow = multiList.getRowCount();
	          if(allrow-row-1<3)
	            downRow(row, col, 3-(allrow-row-1),false);
	          
	          if(multiList.getCellText(row + 1, 16) != null)
	          multiList.addTextCell(row+1, 16, "");
	          if(multiList.getCellText(row + 2, 16) != null)
	          multiList.addTextCell(row+2, 16, "");

	          
	          multiList.addTextCell(row, 16, tool.getBsoID());
	          multiList.addTextCell(row, col, tool.getToolNum());
	          if(tool.getToolSpec() == null || tool.getToolSpec().length() == 0)
	              multiList.addTextCell(row + 1, col, no);
	          else
	              multiList.addTextCell(row + 1, col, tool.getToolSpec());
	        if(tool.getToolName() == null || tool.getToolName().length() == 0)
	              multiList.addTextCell(row + 2, col, tool.getToolName());
	          else
	              multiList.addTextCell(row + 2, col, tool.getToolName());
	      }
	  }
	  
	  
	  
	  
	  /**
	   * 插入材料
	   * @param mater
	   * @param row
	   * @param col
	   * 
	   * void
	   */
	  private void insertMa(QMMaterialInfo mater,int row,int col)
	  {
	      
	      
	      String no = "--";
	      String mid = multiList.getCellText(row, 15);
	      String tid = multiList.getCellText(row, 16);
	      String eqid = multiList.getCellText(row, 17);
	      if((eqid == null || eqid.isEmpty()) && (mid == null || mid.isEmpty())&& (tid == null || tid.isEmpty()))
	      {
	          


	          int allrow = emptyRows(row,col,2);
	          downRow(row, col, 2-allrow,false);

	     
	          multiList.addTextCell(row, 17, mater.getBsoID());

	          multiList.addTextCell(row, col, mater.getMaterialNumber());
	          
	          //CCBegin SS12
	          //if(mater.getMaterialSpecial() == null || mater.getMaterialSpecial().length() == 0)
	          if(mater.getMaterialState() == null || mater.getMaterialState().length() == 0)
	              multiList.addTextCell(row + 1, col, no);
	          else
	              //multiList.addTextCell(row + 1, col, mater.getMaterialSpecial());
	              multiList.addTextCell(row + 1, col, mater.getMaterialState());
	          //CCEnd SS12
	          if(mater.getMaterialName() == null || mater.getMaterialName().length() == 0)
	              multiList.addTextCell(row + 2, col, no);
	          else
	              multiList.addTextCell(row + 2, col, mater.getMaterialName());
	         
	          
	      }else if(!mid.isEmpty())
	      {
	          int allrow = multiList.getRowCount();
	          if(allrow-row-1<2)
	            downRow(row, col, 2-(allrow-row-1),false);
	          
	          multiList.addTextCell(row, 17, mater.getBsoID());
	          if(multiList.getCellText(row + 1,17) != null)
	          multiList.addTextCell(row+1, 17, "");
	          if(multiList.getCellText(row + 2, 17) != null)
	          multiList.addTextCell(row+2, 17, "");
	         
	          multiList.addTextCell(row, col, mater.getMaterialNumber());
	         
	          //CCBegin SS12
	          //if(mater.getMaterialSpecial() == null || mater.getMaterialSpecial().length() == 0)
	          if(mater.getMaterialState() == null || mater.getMaterialState().length() == 0)
	              multiList.addTextCell(row + 1, col, no);
	          else
	              //multiList.addTextCell(row + 1, col, mater.getMaterialSpecial());
	              multiList.addTextCell(row + 1, col, mater.getMaterialState());
	          //CCEnd SS12
	          if(mater.getMaterialName() == null || mater.getMaterialName().length() == 0)
	              multiList.addTextCell(row +2, col, no);
	          else
	              multiList.addTextCell(row +2, col, mater.getMaterialName());
	          
	      }else if(!eqid.isEmpty())
	      {
	          multiList.addTextCell(row, 15, "");

	          int allrow = emptyRows(row,col,2);
	          downRow(row, col, 2-allrow,false);


	          multiList.addTextCell(row, 17, mater.getBsoID());
	          multiList.addTextCell(row, col, mater.getMaterialNumber());
	          
	          //CCBegin SS12
	          //if(mater.getMaterialSpecial() == null || mater.getMaterialSpecial().length() == 0)
	          if(mater.getMaterialState() == null || mater.getMaterialState().length() == 0)
	              multiList.addTextCell(row + 1, col, no);
	          else
	              //multiList.addTextCell(row + 1, col, mater.getMaterialSpecial());
	              multiList.addTextCell(row + 1, col, mater.getMaterialState());
	          //CCEnd SS12
	          
	          if(mater.getMaterialName() == null || mater.getMaterialName().length() == 0)
	              multiList.addTextCell(row + 2, col, no);
	          else
	              multiList.addTextCell(row + 2, col, mater.getMaterialName());

	      }else if(!tid.isEmpty()){
	    	  
	    	  multiList.addTextCell(row, 16, "");

	          int allrow = emptyRows(row,col,2);
	          downRow(row, col, 2-allrow,false);


	          multiList.addTextCell(row, 17, mater.getBsoID());
	          multiList.addTextCell(row, col, mater.getMaterialNumber());
	         
	          
	          //CCBegin SS12
	          //if(mater.getMaterialSpecial() == null || mater.getMaterialSpecial().length() == 0)
	          if(mater.getMaterialState() == null || mater.getMaterialState().length() == 0)
	              multiList.addTextCell(row + 1, col, no);
	          else
	              //multiList.addTextCell(row + 1, col, mater.getMaterialSpecial());
	              multiList.addTextCell(row + 1, col, mater.getMaterialState());
	          //CCEnd SS12
	          if(mater.getMaterialName() == null || mater.getMaterialName().length() == 0)
	              multiList.addTextCell(row + 2, col, no);
	          else
	              multiList.addTextCell(row + 2, col, mater.getMaterialName());
	    	  
	      }
	      
	      
	      
	      
	      
	  }
	  
	  
	  
	  /**
	   * 插入设备
	   * @param eq
	   * @param row
	   * @param col
	   * //20140616
	   * void
	   */
	  private void insertEq(QMEquipmentInfo eq,int row,int col)
	  {
	      String no = "--";
	      String id = multiList.getCellText(row, 15);
	      String tid = multiList.getCellText(row, 16);
	      String mid = multiList.getCellText(row, 17);
	      if((id == null || id.isEmpty()) && (mid == null || mid.isEmpty())&& (tid == null || tid.isEmpty()))
	      {
	          


	          int allrow = emptyRows(row,col,2);
	          downRow(row, col, 2-allrow,false);

	     
	          multiList.addTextCell(row, 15, eq.getBsoID());

	          multiList.addTextCell(row, col, eq.getEqNum());
	          
	          if(eq.getEqModel() == null || eq.getEqModel().length() == 0)
	              multiList.addTextCell(row + 1, col, no);
	          else
	              multiList.addTextCell(row + 1, col, eq.getEqModel());
	          
	          if(eq.getEqName() == null || eq.getEqName().length() == 0)
	              multiList.addTextCell(row + 2, col, no);
	          else
	              multiList.addTextCell(row + 2, col, eq.getEqName());
	         
	          
	      }else if(!id.isEmpty())
	      {
	          int allrow = multiList.getRowCount();
	          if(allrow-row-1<2)
	            downRow(row, col, 2-(allrow-row-1),false);
	          
	          multiList.addTextCell(row, 15, eq.getBsoID());
	          if(multiList.getCellText(row + 1,15) != null)
	          multiList.addTextCell(row+1, 15, "");
	          if(multiList.getCellText(row + 2, 15) != null)
	          multiList.addTextCell(row+2, 15, "");
	         
	          multiList.addTextCell(row, col, eq.getEqNum());
	         
	          if(eq.getEqModel() == null || eq.getEqModel().length() == 0)
	              multiList.addTextCell(row + 1, col, no);
	          else
	              multiList.addTextCell(row + 1, col, eq.getEqModel());
	          if(eq.getEqName() == null || eq.getEqName().length() == 0)
	              multiList.addTextCell(row + 2, col, no);
	          else
	              multiList.addTextCell(row + 2, col, eq.getEqName());
	          
	      }else if(!mid.isEmpty())
	      {
	          multiList.addTextCell(row, 17, "");

	          int allrow = emptyRows(row,col,2);
	          downRow(row, col, 2-allrow,false);


	          multiList.addTextCell(row, 15, eq.getBsoID());
	          multiList.addTextCell(row, col, eq.getEqNum());
	          
	          if(eq.getEqModel() == null || eq.getEqModel().length() == 0)
	              multiList.addTextCell(row + 1, col, no);
	          else
	              multiList.addTextCell(row + 1, col, eq.getEqModel());
	          if(eq.getEqName() == null || eq.getEqName().length() == 0)
	              multiList.addTextCell(row + 2, col, no);
	          else
	              multiList.addTextCell(row + 2, col, eq.getEqName());

	      }else if(!tid.isEmpty()){
	    	  
	    	  multiList.addTextCell(row, 16, "");

	          int allrow = emptyRows(row,col,2);
	          downRow(row, col, 2-allrow,false);


	          multiList.addTextCell(row, 15, eq.getBsoID());
	          multiList.addTextCell(row, col, eq.getEqNum());
	         
	          if(eq.getEqModel() == null || eq.getEqModel().length() == 0)
	              multiList.addTextCell(row + 1, col, no);
	          else
	              multiList.addTextCell(row + 1, col, eq.getEqModel());
	          if(eq.getEqName() == null || eq.getEqName().length() == 0)
	              multiList.addTextCell(row + 2, col, no);
	          else
	              multiList.addTextCell(row + 2, col, eq.getEqName());
	    	  
	      }

	  }
	  
	  
	  /**
	     * 下穿行
	     * @param row
	     * @param col void
	     * 
	     */
	    private void downRow(int row, int col, int count,boolean isTool)
	    {
	        if(count<=0)
	            return;
	     // 下移数据集合
	        Vector valueDownVec = new Vector();
	        Vector idVec1 = new Vector();
	        Vector idVec2 = new Vector();
	        Vector idVec3 = new Vector();
	        int allRowCount = multiList.getRowCount();
	        for(int a = row + 1;a < allRowCount;a++)
	        {
	            String nexRowStrData = multiList.getCellAt(a, col).getStringValue();
	            valueDownVec.add(nexRowStrData);
	        }
	        if(!isTool)
	        {
	            for(int c = row + 1;c < allRowCount;c++)
	            {
	                String nexRowStrData = multiList.getCellAt(c, 15).getStringValue();
	                idVec1.add(nexRowStrData);
	                multiList.addTextCell(c, 15, "");
	            }
	            for(int d = row + 1;d < allRowCount;d++)
	            {
	                String nexRowStrData = multiList.getCellAt(d, 17).getStringValue();
	                idVec2.add(nexRowStrData);
	                multiList.addTextCell(d, 16, "");
	            }
	        }
	        if(isTool)
	        {
	            for(int e = row + 1;e < allRowCount;e++)
	            {
	                String nexRowStrData = multiList.getCellAt(e, 16).getStringValue();
	                idVec3.add(nexRowStrData);
	                multiList.addTextCell(e, 16, "");
	            }
	        }
	        // 在列表末尾添加折行所需要的行
	        this.addNewRowOnLast(count);
	        allRowCount = multiList.getRowCount();
	        for(int b = valueDownVec.size();b > 0;b--)
	        {
	            String value = (String)valueDownVec.get(b - 1);
	            multiList.addTextCell(allRowCount + b - valueDownVec.size() - 1, col, value);
	        }
	        if(!isTool)
	        {
	            for(int f = idVec1.size();f > 0;f--)
	            {
	                String value = (String)idVec1.get(f - 1);
	                multiList.addTextCell(allRowCount + f - idVec1.size() - 1, 15, value);
	            }
	            for(int g = idVec2.size();g > 0;g--)
	            {
	                String value = (String)idVec2.get(g - 1);
	                multiList.addTextCell(allRowCount + g - idVec2.size() - 1,17, value);
	            }
	        }
	        if(isTool)
	        {
	            for(int h = idVec3.size();h > 0;h--)
	            {
	                String value = (String)idVec3.get(h - 1);
	                multiList.addTextCell(allRowCount + h - idVec3.size() - 1, 16, value);
	            }
	        }
	    }
	  
	  
	  //CCEnd SS4
	  
	  
	 
	  /**
	   * 启动选择界面，用户可以选择业务对象加到多列列表中，其后编辑其关联
	   * @throws QMRemoteException
	   * 问题(5)20080602 徐春英修改  修改原因:添加工序关联文档时,普通用户能搜索出全部文档,因为
	   * 搜索出来的是文档master没有权限控制,现在要求搜索结果显示文档,这样就有权限控制了
	   */
	  protected void launchChooserDialog(String bsoName)
	  throws QMRemoteException
	 {

			String s = display("20", null);
			String s1 = bsoName; //getOtherSideClassName();
			
			CappChooser qmchooser = new CappChooser(s1, s, this
					.getTopLevelAncestor());//CR8

			qmchooser.setLastIteration(false);
			qmchooser.setSize(650, 500);
			String s2 = display("57", null);
			try {
				
				qmchooser.setMultipleMode(false);
				
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			qmchooser.setChildQuery(true);
//			CCBegin SS9
			qmchooser.addExttrPanel("QMTool", null);
//			CCEnd SS9
			qmchooser.addListener(new CappQueryListener() {
				public void queryEvent(CappQueryEvent qmqueryevent) {
					if (qmqueryevent.getType().equals(CappQueryEvent.COMMAND)
							&& qmqueryevent.getCommand().equals(CappQuery.OkCMD)) {
						CappChooser qmchooser1 = (CappChooser) qmqueryevent
								.getSource();
						BaseValueIfc awtobject[] = qmchooser1.getSelectedDetails();
						BaseValueIfc finalawtobject[];
						//问题(5) 20080602 徐春英修改
						ArrayList list = new ArrayList();
						try {
							int selectRow=multiList.getSelectedRow();
							
							if (awtobject != null&&multiList.getSelectedRow()!=-1) {
								int length = awtobject.length;
								for (int j = 0; j < length; j++) {
									
									Object obj =awtobject[j];
									
									if (obj instanceof QMEquipmentInfo) {
										QMEquipmentInfo equiInfo = (QMEquipmentInfo) obj;

										  
										  if(groupname.equals("特性控制")){
											  
											  //CCBegin SS2
											  multiList.addTextCell(selectRow, 12, equiInfo.getBsoID());
											  //CCEnd SS2
										      multiList.addTextCell(selectRow, 3, equiInfo.getEqNum());
										      multiList.addTextCell(selectRow, 4, equiInfo.getEqModel());
										      multiList.addTextCell(selectRow, 5, equiInfo.getEqName());
										      
										  }
									      if(groupname.equals("控制计划")){
									    	  //CCBegin SS4
//											    multiList.addTextCell(selectRow, 15, equiInfo.getBsoID());
//											    autoAddRowViewDate(selectRow,2,equiInfo.getEqNum());
//											    addNewRow();
//												 multiList.setSelectedRow(multiList.getSelectedRow() + 1);
//											    autoAddRowViewDate(multiList.getSelectedRow(),2,equiInfo.getEqModel());
//											    addNewRow();
//												multiList.setSelectedRow(multiList.getSelectedRow() + 1);
//											    autoAddRowViewDate(multiList.getSelectedRow(),2,equiInfo.getEqName());
											    
									    	  insertEq(equiInfo,selectRow,2);
									    	  //CCEnd SS4
										  
										}


									      
									}else if(obj instanceof QMMaterialInfo){
										
										QMMaterialInfo Materia = (QMMaterialInfo) obj;
										String materName = Materia.getMaterialName();
										
										 if (groupname.equals("控制计划")) {
										       
										       //CCBegin SS4
//										       autoAddRowViewDate(selectRow,2,Materia.getMaterialNumber());
//										       addNewRow();
//											   multiList.setSelectedRow(multiList.getSelectedRow() + 1);
//										       autoAddRowViewDate(multiList.getSelectedRow(),2,Materia.getMaterialSpecial());
//										       addNewRow();
//											   multiList.setSelectedRow(multiList.getSelectedRow() + 1);
//										       autoAddRowViewDate(multiList.getSelectedRow(),2,materName);
//
//										       multiList.addTextCell(selectRow, 17,Materia.getBsoID());
											 
											 
											 insertMa(Materia,selectRow,2);
											 
											 //CCEnd SS4
											 
									      }
										
									}else if(obj instanceof QMToolInfo){
										
									 if (groupname.equals("控制计划")) {
										QMToolInfo tool = (QMToolInfo) obj;
										
										//CCBegin SS4
//										String toolName=tool.getToolName();
//										 multiList.addTextCell(selectRow, 16, tool.getBsoID());
//										
//										autoAddRowViewDate(selectRow,2,tool.getToolNum());
//										addNewRow();
//										 multiList.setSelectedRow(multiList.getSelectedRow() + 1);
//										autoAddRowViewDate(multiList.getSelectedRow(),2,tool.getToolSpec());
//										
//										addNewRow();
//										 multiList.setSelectedRow(multiList.getSelectedRow() + 1);
//										autoAddRowViewDate(multiList.getSelectedRow(),2,toolName);
//										CCBegin SS8
//										insertTool(tool,selectRow, 2);
										if(!tool.getToolCf().getCodingClassification().getParent().getCodeSort().equals("轴齿量具(通用、专用)、检具、检辅具")){
											insertTool(tool,selectRow, 2);
										}
										else{
											insertTool(tool,selectRow, 9);
										}
//										CCEnd SS8
									   //CCEnd SS4
										
									 }
										
									}
								}
								

							}else{
								 String message = "请选择要编辑的行!";
				                  String title = QMMessage.getLocalizedMessage(RESOURCE,
				                          "information", null);
				                  JOptionPane.showMessageDialog(parentJFrame,
				                                                message,
				                                                title,
				                                                JOptionPane.INFORMATION_MESSAGE);
				                  return;
							}
//							int k = multiList.getNumberOfRows() - 1;
//							multiList.selectRow(k);
							return;
						} catch (Exception _e) {
							_e.printStackTrace();
							return;
						}
					} else {
						return;
					}
				}
				

			});
			setLocation(qmchooser);
			qmchooser.setVisible(true);
			setEnabled(true);
	}
	  /**
	   * 自动折行
	   * @param row
	   * @param column
	   * @param value
	   */
     private void autoAddRowViewDate(int row,int column,String value){
    	 
    	  //每列内容的最大像素
			int maxLen= new Integer(RemoteProperty.getProperty(
		            "com.faw_qm.extend.columnNum-"+column, "55")).intValue();
    	 
    		String editStrData = value;
			
    		MultiLineController mlc = new MultiLineController();
    		
    		if(editStrData==null||editStrData.equals("")){
    			
    			multiList.addTextCell(row, column, "");
    			
    			return;
    		}
    		
			Vector dataVec = mlc.doChangeLine(editStrData, maxLen);
			if(dataVec.size()>1)
			{
				
					
					for (int k = 0; k < dataVec.size(); k++) {
						
						String str = (String) dataVec.get(k);
						
						if (k != 0) {
							
							String nexRowText=multiList.getCellText(row+k, column);
							if(nexRowText==null){
								
								addNewRow();
								 multiList.setSelectedRow(multiList.getSelectedRow() + 1);
								
							}else if(nexRowText!=null&&!nexRowText.equals("")){
								
								addNewRow();
								 multiList.setSelectedRow(multiList.getSelectedRow() + 1);
							}
							
							 multiList.addTextCell(row+k, column,
									  str);

						}else {
							
							
							multiList.addTextCell(row, column, str);
						}
					}
					
				
			}
			else if(dataVec.size()==1){
				String str = (String) dataVec.get(0);
				multiList.addTextCell(row, column, str);
			}
			
			
     }
	  
	  /**
	   * 设置组件的位置
	   * @param comp 组件
	   */
	  private void setLocation(Component comp)
	  {
	      Dimension compSize = comp.getSize();
	      int compH = compSize.height;
	      int compW = compSize.width;
	      Dimension screenS = Toolkit.getDefaultToolkit().getScreenSize();
	      int screenH = screenS.height;
	      int screenW = screenS.width;
	      comp.setLocation(Math.abs((screenW - compW) / 2),
	                       Math.abs((screenH - compH) / 2));
	  }
	  
	 
	  /**
	   * 执行手工录入设备操作
	   * @param e ActionEvent
	   */
	  void extend_actionPerformed(ActionEvent e){
		  
		  
//		  System.out.println("ccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
		  
		  int row=0;
		  int col=0;
		  Object obj = e.getSource();
	      String actionCommand = e.getActionCommand();
//	      System.out.println("actionCommand========================="+actionCommand);
	      int t = actionCommand.indexOf(";");
	      if (t != -1)
	      {
	          //得到行
	          String rowString = actionCommand.substring(0, t);
//	          System.out.println("rowString========================="+rowString);
	          int t1 = rowString.indexOf(":");
	          row = Integer.parseInt(rowString.substring(t1 + 1, rowString.length()));
	          //得到列
	          String colString = actionCommand.substring(t + 1,
	                  actionCommand.length());
//	          System.out.println("colString========================="+colString);
	          int t2 = colString.indexOf(":");
	          col = Integer.parseInt(colString.substring(t2 + 1, colString.length()));
	      }
	      if(groupname.equals("特性控制")){
	    	//CCBegin SS3
	         if (col == 3)
	       //CCEnd SS3
	        {
	          if (obj instanceof JTextField)
	          {
	              JTextField textField = (JTextField) obj;
	              if (textField.getText().trim() != null &&
	                  !textField.getText().trim().equals(""))
	              {
	                  handworkAddEquipNumber(textField, row);
	              }
	              
	            }
	         }
	         
	         
	      }
	      
	      if(groupname.equals("控制计划")){
	    	  
	    	  
	    	  if (col == 1)
		        {
		          if (obj instanceof JTextField)
		          {
		              JTextField textField = (JTextField) obj;
		              if (textField.getText().trim() != null &&
		                  !textField.getText().trim().equals(""))
		              {
		            	  //CCBegin SS3
//		                  handworkAddEquipNumber(textField, row);
		                  //CCEnd SS3
		              }
		              
		            }
		         }
	    	  
	    	  
	    	  
	    	  if (col == 9){
	    	  if (obj instanceof JTextField)
	          {
	              JTextField textField = (JTextField) obj;
	              if (textField.getText().trim() != null &&
	                  !textField.getText().trim().equals(""))
	              {
	            	  //CCBegin SS3
//	            	  handworkAddMertaNumber(textField, row);
	            	  //CCEnd SS3
	              }
	              
	            }
	    	  }
	    	  
		      if(col==5){
	    	  
	    	  if (obj instanceof JTextField)
	          {
	              JTextField textField = (JTextField) obj;
	              if (textField.getText().trim() != null &&
	                  !textField.getText().trim().equals(""))
	              {
	            	//CCBegin SS3
//	            	  handworkAddToolNumber(textField, row,col);
	            	  //CCEnd SS3
	              }
	              
	          }
	    	  
	      }
	    	  
	      }
	      
	      
	      

	      
		  
		  
		  
	  }
	  
	  /**
	   * 手工添加材料信息到指定的文本框中
	   * @param textField 填写设备编号的文本框
	   */
	  private void handworkAddMertaNumber(JTextField textField,int row){
		  
		   number = textField.getText().trim();
		  QMMaterialInfo mater = checkMaterialIsExist(number);
		  
		  if(mater!=null){
	    	  
			  if(groupname.equals("控制计划")){
	    	    multiList.addTextCell(row, 25, mater.getBsoID());
				  
	    	    //CCBegin SS12
	    	    //multiList.addTextCell(row, 10, mater.getMaterialSpecial());
	    	    multiList.addTextCell(row, 10, mater.getMaterialState());
	    	    //CCEnd SS12
	    	    
				multiList.addTextCell(row, 11, mater.getMaterialName());
			  }
				
	      }
		  
		  else
	        {
	            CreateResource t = new CreateResource("新建材料","material",row);
	            new Thread(t).start();
	        }
		  
	  }
	  
	  
	  /**
	   * 手工添加设备信息到指定的文本框中
	   * @param textField 填写设备编号的文本框
	   */
	  private void handworkAddEquipNumber(JTextField textField,int row)
	  {
		  number = textField.getText().trim();
		  
		  //CCBegin SS13
		  if(number==null||number.length()==0)
		  {
		  	String eqID="";
		  	if(groupname.equals("控制计划"))
		  	{
		  		eqID=multiList.getCellText(row, 15);
		  	}
		  	if(groupname.equals("特性控制"))
		  	{
		  		eqID=multiList.getCellText(row, 12);
		  	}
		  	if(eqID != null && eqID.length() > 0)
		  	{
		  		//清空去掉的设备
		  		//multiList.addTextCell(row, 20, "");
		  		//if(multiList.getCellText(row + 1, col)!=null)
          Hashtable eqMap = new Hashtable();
          if (!eqMap.containsKey(eqID))
            eqMap.put(eqID, 1);
          eqDeleteVec.add(eqMap);
        }
      }
      //SSEnd SS13
		  
	      //如果输入的设备编号在系统中存在，则系统把该设备添加入列表中
	      QMEquipmentInfo eq = checkEquipmentIsExist(number);
	      
	      
	      
	      if (eq != null)
	      {
	    	  
	    	  if(groupname.equals("控制计划")){
	    	    //multiList.addTextCell(row, 23, eq.getBsoID());
	    	    multiList.addTextCell(row, 15, eq.getBsoID());
	    	  
				multiList.addTextCell(row, 1, eq.getEqNum());
				
				multiList.addTextCell(row, 2, eq.getEqModel());
				
				multiList.addTextCell(row, 3, eq.getEqName());
				
	    	   }
	    	  if(groupname.equals("特性控制")){
	    		  
	    		  //CCBegin SS3
	    		    multiList.addTextCell(row, 12, eq.getBsoID());
		    	  
					multiList.addTextCell(row, 3, eq.getEqNum());
					
					multiList.addTextCell(row, 4, eq.getEqModel());
					
					multiList.addTextCell(row, 5, eq.getEqName());
				//CCEnd SS3
	    	  }
	         
	      }
	      
	        else
	        {
	            CreateResource t = new CreateResource("新建设备","equipment",row);
	            new Thread(t).start();
	        }
	      


	  }
	  
	  /**
	   * 手工添加工装编号到指定的文本框中
	   * @param textField 填写工装编号的文本框
	   */
	  private void handworkAddToolNumber(JTextField textField,int row,int col)
	  {
			 number = textField.getText().trim();
			QMToolInfo tool = checkToolIsExist(number);
			if (tool != null) {



						multiList.addTextCell(row, 5, tool
								.getToolNum());

						multiList.addTextCell(row, 6, tool
								.getToolSpec());

						multiList.addTextCell(row, 7, tool
								.getToolName());
						
						multiList.addTextCell(row, 24, tool
								.getBsoID());
					

				
			}
			 else
		        {
		            CreateResource t = new CreateResource("新建工装","tool", row);
		            new Thread(t).start();
		        }
		}
	  
	  
	  class CreateResource extends Thread
	    {
		  
		  String myResourceName="";
		  String myBsoName="";
		  int myrow;
		  
		  public CreateResource(String resourceName ,String bsoName,int row){
			  
			  myResourceName=resourceName;
			  
			  myBsoName= bsoName;
			  
			  myrow=row;
		  }
		  
	        public void run()
	        {
	        	
	            canSave = false;
	            //如果系统中不存在列表中输入的设备编号，则返回对应的消息，询问是否新建
	            //设备，如果是，显示设备新建界面，根据访问权限创建申请设备；如果否，返回原位置.
	           String s="";
	            
	            if(myBsoName.equals("equipment")){
	            	
	            	  s = QMMessage.getLocalizedMessage(
	 	                    RESOURCE, CappLMRB.IS_CREATE_EQUIPMENT, null);
	            	
	            	
	            }
	            else if(myBsoName.equals("tool")){
	            	
	            	  s = QMMessage.getLocalizedMessage(
		 	                    RESOURCE, CappLMRB.IS_CREATE_TOOL, null);
	            	
	            }
	            else if(myBsoName.equals("material")){
	            	
	            	 s = QMMessage.getLocalizedMessage(
		 	                    RESOURCE, CappLMRB.IS_CREATE_MATERIAL, null);
	            }
	            
	            
	            if (confirmAction(s))
	            {
	                getParentJFrame().setCursor(Cursor.getPredefinedCursor(Cursor.
	                        WAIT_CURSOR));
	                //显示设备新建界面
	                ResourceDialog d = new ResourceDialog(getParentJFrame(),
	                		myBsoName, "CREATE", number);

	                d.setSize(650, 500);
	                setViewLocation(d);
	                d.setTitle(myResourceName);
	                d.addQuitListener(new ActionListener()
	                {
	                    public void actionPerformed(ActionEvent e)
	                    {
	                    	 multiList.undoCell();
	                    }

	                });
	                d.setVisible(true);
	                d.setModal(true);
	                getParentJFrame().setCursor(Cursor.getDefaultCursor());
	                try
	                {
	                    Object obj = d.getObject();
	                    if (obj != null)
	                    {
	                    	
	                    	
	                    	if(obj instanceof QMEquipmentInfo){
	                    		
	                    	 QMEquipmentInfo eq=(QMEquipmentInfo)obj;
	                    	
	                    	 if(groupname.equals("控制计划")){
	                 	    	
	             	    	    //multiList.addTextCell(myrow, 23, eq.getBsoID());
	             	    	    multiList.addTextCell(myrow, 15, eq.getBsoID());
	             	    	  
	             				multiList.addTextCell(myrow, 1, eq.getEqNum());
	             				
	             				multiList.addTextCell(myrow, 2, eq.getEqModel());
	             				
	             				multiList.addTextCell(myrow, 3, eq.getEqName());
	             				
	             	    	   }
	             	    	  if(groupname.equals("特性控制")){
	             	    		  
	             	    		  //CCBegin SS13
	             	    		  //multiList.addTextCell(myrow, 11, eq.getBsoID());
	             	    		  multiList.addTextCell(myrow, 12, eq.getBsoID());
	             	    		  //CCEnd SS13
	             		    	  
	             					multiList.addTextCell(myrow, 2, eq.getEqNum());
	             					
	             					multiList.addTextCell(myrow, 3, eq.getEqModel());
	             					
	             					multiList.addTextCell(myrow, 4, eq.getEqName());
	             	    	  }
	                    	}
	                    	else if(obj instanceof QMMaterialInfo){
	                    		
	                    		QMMaterialInfo mater=(QMMaterialInfo)obj;
	                    		
	                    		if(groupname.equals("控制计划")){
	                	    	    //multiList.addTextCell(myrow, 25, mater.getBsoID());
	                	    	    multiList.addTextCell(myrow, 17, mater.getBsoID());
	                				  
	                	    	    //CCBegin SS12
	                	    	    //multiList.addTextCell(myrow, 10, mater.getMaterialSpecial());
	                	    	    multiList.addTextCell(myrow, 10, mater.getMaterialState());
	                	    	    //CCEnd SS12
	                	    	    
	                				multiList.addTextCell(myrow, 11, mater.getMaterialName());
	                			  }
	                    	}
	                    	else if(obj instanceof QMToolInfo){
	                    		
	                    		QMToolInfo tool =(QMToolInfo)obj;
	                    		
	                    		multiList.addTextCell(myrow, 5, tool
	    								.getToolNum());

	    						multiList.addTextCell(myrow, 6, tool
	    								.getToolSpec());

	    						multiList.addTextCell(myrow, 7, tool
	    								.getToolName());
	    						
	    						//multiList.addTextCell(myrow, 24, tool
	    						//		.getBsoID());
	    						multiList.addTextCell(myrow, 16, tool
	    								.getBsoID());
	                    		
	                    	}
	                    }
	                    else
	                    {
	                    	multiList.undoCell();
	                    }
	                }
	                catch (Exception ex)
	                {
	                    ex.printStackTrace();
	                }
	            }
	            else
	            {
	                multiList.undoCell();
	            }
	            canSave = true;
	        }
	    }
	  
	  /**
	     * 设置界面的显示位置
	     */
	    private void setViewLocation(JDialog d)
	    {
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	        
	        d.setLocation((screenSize.width - d.getWidth()) / 2,
	                      (screenSize.height - d.getHeight()) / 2);

	    }
	    
	    /**
	     * 显示确认对话框
	     * @param s 在对话框中显示的信息
	     * @return  如果用户选择了“确定”按钮，则返回true;否则返回false
	     */
	    private boolean confirmAction(String s)
	    {
	        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
	        return JOptionPane.showConfirmDialog(getParentJFrame(),
	                                             s, title,
	                                             JOptionPane.YES_NO_OPTION) ==
	                JOptionPane.YES_OPTION;
	    }

	  
	  /**
		 * 调用资源服务，通过指定的设备编号在数据库中查找设备对象
		 * 
		 * @param eqNumber
		 *            指定的设备编号
		 * @return 查找到的设备对象
		 */
	  private QMEquipmentInfo checkEquipmentIsExist(String eqNumber)
	  {
	      Class[] c =
	              {String.class};
	      Object[] obj =
	              {eqNumber};
	      try
	      {
	          return (QMEquipmentInfo) useServiceMethod(
	                  "ResourceService", "findEquipmentByNumber", c, obj);
	      }
	      catch (QMRemoteException ex)
	      {
	          //ex.printStackTrace();
	          return null;
	      }
	  }
	  
	  /**
	   * 调用资源服务，通过指定的材料编号在数据库中查找材料对象
	   * @param eqNumber 指定的材料编号
	   * @return 查找到的材料对象
	   */
	  private QMMaterialInfo checkMaterialIsExist(String eqNumber)
	  {
	      Class[] c =
	              {String.class};
	      Object[] obj =
	              {eqNumber};
	      try
	      {
	          return (QMMaterialInfo) useServiceMethod(
	                  "ResourceService", "findMaterialByNumber", c, obj);
	      }
	      catch (QMRemoteException ex)
	      {
	          //ex.printStackTrace();
	          return null;
	      }
	  }
	  /**
	   * 调用资源服务，通过指定的工装编号在数据库中查找工装对象
	   * @param eqNumber 指定的工装编号
	   * @return 查找到的工装对象
	   */
	  private QMToolInfo checkToolIsExist(String toolNumber)
	  {
	      Class[] c =
	              {String.class};
	      Object[] obj =
	              {toolNumber};
	      try
	      {
	          return (QMToolInfo) useServiceMethod(
	                  "ResourceService", "findToolByNumber", c, obj);
	      }
	      catch (QMRemoteException ex)
	      {
	          return null;
	      }
	  }
	  
	  /**
		 * 添加空行
		 */
	public void addNewRow(){
		
		 int row = multiList.getNumberOfRows();
	      int row1 = multiList.getSelectedRow();
	      if (row1 < 0)
	      {
	          for (int i = 0; i < group.getCount(); i++)
	          {
	              addDefalutAttsToTable(row, i, group.getAttributeAt(i));
//	              multiList.setSelectedRow(row);
	          }
	      }
	      else
	      {
	          for (int i = 0; i < group.getCount(); i++)
	          {
	              addDefalutAttsToTable(row, i, group.getAttributeAt(i));
	          }
	          for (int i = row - 1; i > row1; i--)
	          {
	              multiList.moveDown(i);
	          }

//	          multiList.setSelectedRow(row1 + 1);
	      }  
	}
	  
	  
	  
	  /**
	   * 本方法用于客户端远程调用服务端方法
	   * @param serviceName 要调用的服务类名称
	   * @param methodName 要调用的服务方法名称
	   * @param paraClass  要调用的服务方法的参数的类型
	   * @param paraObject 要调用的服务方法的参数值
	   * @return
	   */
	  public Object useServiceMethod(String serviceName, String methodName,
	                                 Class[] paraClass, Object[] paraObject)
	          throws QMRemoteException
	  {
	      if (verbose)
	      {
	          System.out.println(
	                  "cappclients.capp.view.ParentJPanel.useServiceMethod() begin...");
	      }
	      RequestServer server = RequestServerFactory.getRequestServer();
	      ServiceRequestInfo info1 = new ServiceRequestInfo();
	      info1.setServiceName(serviceName);
	      info1.setMethodName(methodName);
	      Class[] paraClass1 = paraClass;
	      info1.setParaClasses(paraClass1);
	      Object[] objs1 = paraObject;
	      info1.setParaValues(objs1);
	      Object obj = null;
	      obj = server.request(info1);
	      if (verbose)
	      {
	          System.out.println(
	                  "cappclients.capp.view.ParentJPanel.useServiceMethod() end...return: " +
	                  obj);
	      }
	      return obj;
	  }
	  

	  //变速箱简易所见即所得 添加 end
	  
	//CCBegin SS10
	  /**
	   * 轴齿只在控制计划中维护设备，通过此按钮将维护好的设备信息传递到过程流程中，
	   * 规则为：将控制计划中所有设备信息传递到过程流程中，并从过程流程第一行开始依次向下排列，
	   * 覆盖原有行的对应列，而不是新插入行
	   * @param e ActionEvent
	 * @throws QMRemoteException 
	   */
	  void EQInheritButton_actionPerformed(ActionEvent e) {
		  if (groupname.equals("特性控制")) {
			  QMProcedureInfo info = container.getProIfc();
			  if(info == null || info.getProcessControl() == null)
			  {
				  return;    
			  }
			  ExtendAttContainer con = info.getProcessControl();

			  //控制计划产品特性
			  Vector productIdentityValue = getExtendAttValue(con,"eqBsoID");
			  //控制计划过程特性
			  int ii=productIdentityValue.size();
			  int row = this.multiList.getRowCount();
			  Vector v = new Vector();
			  for(int i = 0;i<ii;i++){
				  String ss = (String) productIdentityValue.elementAt(i);
				  if(ss !=null && !ss.equals(""))
					  v.add(ss);
			  }
			  if(row==0){
				  for (int i = row; i < row + v.size(); i++) {
					  for (int j = 0; j < group.getCount(); j++) {
						  addDefalutAttsToTable(i, j, group.getAttributeAt(j));
					  }
				  }
				  //传递内容      
				  for (int k = row, n = 0; k < row + v.size(); k++, n++) {
					  Class[] c = { String.class };
					  Object[] obj = { v.elementAt(n)};
					  try {
						  QMEquipmentInfo eqInfo = (QMEquipmentInfo) useServiceMethod("PersistService", "refreshInfo", c, obj);
						  multiList.addTextCell(k, 3, eqInfo.getEqNum());
						  if(eqInfo.getEqModel() == null || eqInfo.getEqModel().length() == 0)
							  multiList.addTextCell(k, 4, "--");
						  else
							  multiList.addTextCell(k, 4, eqInfo.getEqModel());
						  multiList.addTextCell(k, 5, eqInfo.getEqName());
						  multiList.addTextCell(k, 12, eqInfo.getBsoID());
					  } catch (QMRemoteException e1) {
						  // TODO Auto-generated catch block
						  e1.printStackTrace();
					  }
				  }
			  }
			  else{
				  for(int i = 0; i < row; i++){
					  multiList.addTextCell(i, 3, "");
					  multiList.addTextCell(i, 4, "");
					  multiList.addTextCell(i, 5, "");
					  multiList.addTextCell(i, 6, "");
					  multiList.addTextCell(i, 7, "");
					  multiList.addTextCell(i, 12, "");
				  }
				  if(v.size()>row){
					  for (int i = row; i < v.size(); i++) {
						  for (int j = 0; j < group.getCount(); j++) {
							  addDefalutAttsToTable(i, j, group.getAttributeAt(j));
						  }
					  }
				  }
				  for (int k = 0, n = 0; k < v.size(); k++, n++) {
					  Class[] c = { String.class };
					  Object[] obj = { v.elementAt(n)};
					  try {
						  QMEquipmentInfo eqInfo = (QMEquipmentInfo) useServiceMethod("PersistService", "refreshInfo", c, obj);
						  multiList.addTextCell(k, 3, eqInfo.getEqNum());
						  if(eqInfo.getEqModel() == null || eqInfo.getEqModel().length() == 0)
							  multiList.addTextCell(k, 4, "--");
						  else
							  multiList.addTextCell(k, 4, eqInfo.getEqModel());
						  multiList.addTextCell(k, 5, eqInfo.getEqName());
						  multiList.addTextCell(k, 12, eqInfo.getBsoID());
					  } catch (QMRemoteException e1) {
						  // TODO Auto-generated catch block
						  e1.printStackTrace();
					  }
				  }
			  }
		  }
	  }
	 // CCEnd SS10
	
}
