/** 生成程序TechnicsRegulationsMainJFrame.java	1.1  2003/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * CR1 2009/04/28  刘学宇   原因：工步无重新生成工步号的功能
 *                          方案：添加了用于重新生成工步号功能的组件
 *                          备注：变更记录标识为CRSS-007
 *                          
 *CR2 2009/05/05   郭晓亮   原因: 点工艺树右键后，出现快捷菜单，但焦点与当前
 *                                鼠标位置不一致，容易造成用户误操作        
 *                          方案: 点右键时清除当前树节点的选择,通过坐标选中节
 *                                点并弹出快捷菜单.   
 *                                
 *                          备注:TD号:"2119"
 *                          
 *  CR3 2009/04/29  刘志城   原因：工艺规程编辑界面添加移除按钮
 *                          方案：工艺规程从工艺树中擦去不显示但不删除。
 *                          备注：变更记录标识为"CRSS-012"       
 *                          
 *  CR4 2009/06/02  郭晓亮  参见:测试域:v4r3FunctionTest;TD号2235   
 *  CR5 2009/05/31  徐春英   原因：查看工序的简图之后，关闭工艺主界面，c盘上的图形文件没有删除
 *                           方案:退出工艺主界面时，判断如果简图文件存在硬盘上，则将其删除           
 *
 *  CR6 2009/12/29  刘玉柱  TD问题2676
 *  CR7 2010/04/13  徐春英  原因:参见TD问题2253
 *  SS1 变速箱资源清单一览表 zhaoqiuying 2013-01-29
 *  SS2 获取当前用户,按照该用户组显示派生文件相关信息(变速箱显示,解放不显示) pante 20130720
 *  SS3 轴齿中心增加工具一览表 文柳 2014-2-13
 *  SS4 轴齿中心增加工艺派生文件 pante 2014-02-19
 *  SS5 新增“万能量具清单”和“工位器具清单”两个一览表。 liunan 2014-7-28
 *  SS6 轴齿中心新需求，将刀具及其辅具分开 pante 2014-09-10
 *  SS7 长特增加工装明细，设备清单，模具清单。 guoxiaoliang 2014-08-22
 *  SS8 增加合并工艺功能 徐德政 2014-12-15
 *  SS9 为避免用户因为未选中文本框，而粘贴文字时，调用到工序粘贴的功能，将工艺、工序粘贴的快捷键改为”Ctrl+P” liunan 2015-3-30
 *  SS10 成都检出所有工序工步 guoxiaoliang 2016-7-13 
 *  SS11 成都按结构搜索工艺 guoxiaoliang 2016-7-19
 *  SS12 成都关键工序筛选  guoxiaoliang 2016-8-5
 *  SS13 成都成都质量检查表工艺 guoxiaoliang 2016-8-5
 *  SS14 成都检查作业指导书工艺和工序的维护界面修改  guoxiaoliang 2016-11-23
 *  SS15 轴齿导出工序控制计划 liunan 2016-11-30
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.cappclients.beans.drawingpanel.ToolSelectedDialog;
import com.faw_qm.cappclients.beans.processtreepanel.OperationTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.PartTreePanel;
import com.faw_qm.cappclients.beans.processtreepanel.ProcessTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.StepTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreePanel;
import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.resourcetreepanel.DrawingTreeObject;
import com.faw_qm.cappclients.beans.resourcetreepanel.EquipTreeObject;
import com.faw_qm.cappclients.beans.resourcetreepanel.MaterialTreeObject;
import com.faw_qm.cappclients.beans.resourcetreepanel.ResourceTreePanel;
import com.faw_qm.cappclients.beans.resourcetreepanel.TermTreeObject;
import com.faw_qm.cappclients.beans.resourcetreepanel.ToolTreeObject;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.controller.CopyAction;
import com.faw_qm.cappclients.capp.controller.PasteAction;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.controller.TechnicsRegulationsMainController;
import com.faw_qm.cappclients.capp.controller.TechnicsRegulationsMainController.SchDlg;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.QMCt;
import com.faw_qm.cappclients.util.BusinessTreeObject;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.cappclients.util.CappTree;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.cappclients.util.CappTreeObject;
import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.clients.beans.explorer.ProgressDialog;
import com.faw_qm.clients.beans.explorer.QMMenu;
import com.faw_qm.clients.beans.explorer.QMMenuItem;
import com.faw_qm.clients.beans.explorer.QMNode;
import com.faw_qm.clients.beans.explorer.QMToolBar;
import com.faw_qm.clients.beans.explorer.QMTree;
import com.faw_qm.clients.util.MessageDialog;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.help.QMHelpSystem;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.resource.support.model.DrawingIfc;
import com.faw_qm.resource.support.model.DrawingInfo;
import com.faw_qm.resource.support.model.QMEquipmentIfc;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMMaterialIfc;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMTermInfo;
import com.faw_qm.resource.support.model.QMToolIfc;
import com.faw_qm.resource.support.model.QMToolInfo;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.users.model.GroupIfc;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.clients.util.*;


/**
 * <p>Title: 工艺维护主界面</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 * @version 1.0
 * (1)20060926薛静修改，修改方法 SearchPT()，加上不查询子表的条件
 * 问题（2）20081218 徐春英修改       修改原因：在工艺规程肥客户的工艺树上按住Ctrl用鼠标选择2个或更多的工序，
 * 鼠标右键选择复制，然后粘贴到其他工艺下，这时只能粘贴所选多个工序中的一个（第一个选择的那个）
 * 问题（3）20081226 xucy  修改原因：优化更新时保存工序
 */

public class TechnicsRegulationsMainJFrame extends JFrame implements
        ActionListener, TechnicsViewIfc
{
    private JPanel contentPane;    

   
    /**工具条*/
    private QMToolBar qmToolBar = new QMToolBar();


    /**状态条*/
    private JLabel statusBar = new JLabel();

    private BorderLayout borderLayout1 = new BorderLayout();


    /**界面分割面板*/
    private JSplitPane jSplitPane = new JSplitPane();
    private JPanel rightJPanel = new JPanel();
    private JPanel leftJPanel = new JPanel();


    /**工艺树及资源分类树的选项卡面板*/
    private JTabbedPane treeJTabbedPane = new JTabbedPane();


    /**搜索按钮*/
    private JButton searchTreeJButton = new JButton();
    private JLabel leftJLabel = new JLabel();
    private JLabel rightJLabel = new JLabel();


    /**工艺内容面板*/
    private TechnicsContentJPanel contentJPanel;

    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();


    /**菜单项*/
    private JMenuBar jMenuBar1;
    private JMenu jMenuFile;
    private QMMenuItem jMenuFileExit;
    private JMenu jMenuHelp;
    private QMMenuItem jMenuHelpAbout;
    private QMMenuItem jMenuFileCollect;
    //CCBegin SS11
    private QMMenuItem jMenuStructSearchTechnics;
    //CCEnd SS11

    //private QMMenuItem jMenuFileImport = new QMMenuItem();
    private QMMenuItem jMenuFileExport;
    private QMMenuItem jMenuFileRefresh;
    private QMMenu jMenuFileCreate;
    private QMMenuItem jMenuCreateTechnics;
    private QMMenuItem jMenuCreateStep;
    private QMMenuItem jMenuCreatePace;
    private JMenu jMenuSelect;
    private JMenu jMenuVersion;
    private JMenu jMenuLifeCycle;
    //CCBegin SS1
    private JMenu jMenuSchedule;
    private QMMenuItem jMItemjiaju;
    private QMMenuItem jMItemwanneng;
    //CCBegin SS5
    private QMMenuItem jMItemwannengliangju;
    private QMMenuItem jMItemgongweiqiju;
    //CCEnd SS5
    private QMMenuItem jMItemequip;
    private QMMenuItem jMItemmoju;
    private QMMenuItem jMItemdaoju;
    private QMMenuItem jMItemjiafuju;
    private QMMenuItem jMItemzhuangpei;
    //CCEnd SS1
    //CCBegin SS4
    private QMMenuItem jMItemzcjiaju;
    private QMMenuItem jMItemzcwangong;
    private QMMenuItem jMItemzcwanliang;
    private QMMenuItem jMItemzcmoju;
    private QMMenuItem jMItemzcdaoju;
    //    CCBegin SS6
    private QMMenuItem jMItemzcdaofuju;
//    CCEnd SS6
    private QMMenuItem jMItemzcjiafuju;
    private QMMenuItem jMItemzcjianfuju;
    private QMMenuItem jMItemzcliangju;
    private QMMenuItem jMItemzcjianju;
    private QMMenuItem jMItemzcsb;
    //CCEnd SS4
    //CCBegin SS7
    private QMMenuItem jMItemCtTool;
    private QMMenuItem jMItemCtSheBei;
    private QMMenuItem jMItemCtMoJu;
    //CCEnd SS7
    private QMMenuItem jMenuSelectMadeTech;
    private QMMenuItem jMenuSelectMadeStep;
    private QMMenuItem jMenuSelectCopy;
    private QMMenuItem jMenuSelectPaste;
    private QMMenuItem jMenuVersionCheckIn;
    private QMMenuItem jMenuVersionCheckOut;
    private QMMenuItem jMenuVersionCancel;
    private QMMenuItem jMenuVersionRevise;
    private QMMenuItem jMenuVersionVersion;
    private QMMenuItem jMenuVersionIteration;
    private QMMenuItem jMenuSetLifeCycleState;
    private QMMenuItem jMenuLifeCycleSelect;
    private QMMenuItem jMenuLifeCycleView;
    private QMMenuItem jMenuLifeCycleGroup;
    private QMMenuItem jMenuIntellectPart;
    private QMMenuItem jMenuIntellectRoot;
    private QMMenuItem jMenuSearchEquip;
    private QMMenuItem jMenuSearchTool;
    private QMMenuItem jMenuSearchMaterial;
    private QMMenuItem jMenuSearchTechnics;
    private QMMenuItem jMenuHelpManage;
    private QMMenuItem jMenuSelectDelete;
    private QMMenuItem jMenuAddObject;
    //Begin CR3
    private QMMenuItem jMenuSelectMoveOut;
    //End CR3

    //右健快捷菜单
    private JMenu jMenuFileCreate1;
    private QMMenuItem jMenuCreateTechnics1;
    private QMMenuItem jMenuCreateStep1;
    private QMMenuItem jMenuCreatePace1;
    private QMMenuItem jMenuFileCollect1;
//    CCBegin SS8
    private QMMenuItem jMenuCTFileCollect1;
    private QMMenuItem jMenuCTFileCollect;
//    CCEnd SS8
    private QMMenuItem jMenuSelectView1;
    private QMMenuItem jMenuSelectUpdate1;
    private QMMenuItem jMenuSelectDelete1;
    //Begin CR3	
    private QMMenuItem jMenuSelectMoveOut1;
    //End CR3
    private QMMenuItem jMenuSelectBrowse1;
    private QMMenuItem jMenuSelectCopy1;
    private QMMenuItem jMenuSelectPaste1;
    private QMMenuItem jMenuSelectSaveAs1;

    //CCBegin SS15
    private QMMenuItem jMenuSelectExport;
    //CCEnd SS15

    // private JMenu jMenuIntellect = new JMenu();
    private JMenu jMenuSearch;
    private QMMenuItem jMenuSelectView;
    private QMMenuItem jMenuSelectUpdate;
    private QMMenuItem jMenuSelectFormStepNum;
    //CR1 begin
    private QMMenuItem jMenuSelectFormPaceNum;
    //CR1 end
    private QMMenuItem jMenuSelectBrowse;
    private QMMenuItem jMenuSelectChangeLocation;
    private QMMenuItem jMenuSelectRename;
    private QMMenuItem jMenuSelectSaveAs;
    private QMMenuItem jMenuSelectUseTech;
    private QMMenuItem jMenuSelectUseStep;
    private QMMenuItem jMenuConfigCrit;
    private QMMenuItem jMenuHelpItem;
    
    //CCBegin SS10
    private QMMenuItem jMenuCheckAll;
    //CCEnd SS10
    
    //CCBegin SS12
    private QMMenuItem jMenuFindMainStep;
    //CCEnd SS12

    /**
     * 当前用户
     */
    public static UserIfc currentUser;


    /**
     * 鼠标监听
     */
    private MyMouseListener mouselistener = new MyMouseListener();


    /**界面控制类*/
    private TechnicsRegulationsMainController mainController;


    /**界面显示模式（更新模式）标记*/
    public final static int UPDATE_MODE = 0;


    /**界面显示模式（创建模式）标记*/
    public final static int CREATE_MODE = 1;


    /**界面显示模式（查看模式）标记*/
    public final static int VIEW_MODE = 2;


    /**界面显示模式（另存为模式）标记*/
    public final static int SAVEAS_MODE = 3;


    /**工艺树面板*/
    private TechnicsTreePanel processTreePanel;
    private ResourceTreePanel equipmentTreePanel;
    private ResourceTreePanel toolTreePanel;
    private ResourceTreePanel materialTreePanel;
    private ResourceTreePanel termTreePanel;
    private ResourceTreePanel drawingTreePanel;
    private ProgressDialog progressDialog;

    /**用于标记资源文件路径*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**用于标记资源*/
    protected static ResourceBundle resource = null;


    /**弹出菜单*/
    private JPopupMenu popup = new JPopupMenu();


    /**弹出菜单，用于工艺树*/
    private JPopupMenu technicsTreePopup = new JPopupMenu();


    /**代码测试变量*/
   private static boolean verbose = (RemoteProperty.getProperty(
          "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private boolean notExitSystem = false;
    protected Object lock2 = new Object();
    private QMHelpSystem helpSystem;


    /**工艺树上选择的节点*/
    private CappTreeNode existNode;


    /**
     * 存放工具栏按钮的功能描述.键:工具栏按钮的actionCommand,
     * 值:按钮的功能描述
     */
    HashMap toolFunction = new HashMap();
    /**
     * 构造函数
     */
    public TechnicsRegulationsMainJFrame()
    {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);


        try
        {
              //20070118薛静修改，打印已经不使用jview
//            org.jcad.JCad.SymbolDelgateHelper.creatSession(RemoteProperty.
//                    getProperty("jvueServer", "ggldd"));
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 构造函数
     * @param flag 如果flag = true,则界面退出时，系统不退出
     */
    public TechnicsRegulationsMainJFrame(boolean flag)
    {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        try
        {
             //20070118薛静修改，打印已经不使用jview
//            org.jcad.JCad.SymbolDelgateHelper.creatSession(RemoteProperty.
//                    getProperty("jvueServer", "ggldd"));
            jbInit();
            notExitSystem = flag;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 界面初始化
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        ResourceBundle rb = getPropertiesRB();
        titledBorder1 = new TitledBorder("");
        initMenuItem();
        String str1[] = getValueSet(rb, "toolbar.icons");
        String str2[] = getValueSet(rb, "toolbar.text");
        setTools(str1, str2);
        contentJPanel = new TechnicsContentJPanel(this);
        //{{初始化工艺树
        String name = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeRB",
                "process Tree", null);
        processTreePanel = new TechnicsTreePanel(name, "QMFawTechnics");
        partTreeJPanel = new PartTreePanel(this);
        equipmentTreePanel = new ResourceTreePanel(getMessage("equipmentTitle"),
                "QMEquipment");
        toolTreePanel = new ResourceTreePanel(getMessage("toolTitle"), "QMTool");
        materialTreePanel = new ResourceTreePanel(getMessage("materialTitle"),
                                                  "QMMaterial");
        termTreePanel = new ResourceTreePanel(getMessage("termTreeTitle"),
                                              "QMTerm");
        drawingTreePanel = new ResourceTreePanel(getMessage("drawingTreeTitle"),
                                                 "Drawing");
        mainController = new TechnicsRegulationsMainController(this);
        currentUser=mainController.getCurrentUser();
        copyAction = new CopyAction(this, mainController);
        pasteAction = new PasteAction(this, mainController);

        //}}
        //用于控制菜单显示状态
        processTreePanel.addTreeSelectionListener(new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
//                long d1=System.currentTimeMillis();
                CappTreeNode node = ((CappTree) e.getSource()).getSelectedNode();
                //如果选中节点是BusinessTreeObject,则刷新
                if (node != null &&
                    node.getObject() instanceof BusinessTreeObject)
                {
                    if (!TechnicsRegulationsMainJFrame.this.closeContentPanel())
                    {
                        return;
                    }
                    try
                    {
                    	//问题（3）20081226 xucy  修改原因：优化更新时保存工序 begin
                    	if( contentJPanel.techStepIsShowing())
                    	{
                    		TechnicsRegulationsMainJFrame.this.setStepJpanel(false);
                    	}
                    	if(contentJPanel.techPaceIsShowing())
                    	{
                    		TechnicsRegulationsMainJFrame.this.setPaceJpanel(false);
                    	}
                    	//问题（3）20081226 xucy  修改原因：优化更新时保存工序 end
                    	//CCBegin by leixiao 2010-5-7 v4r3_p015_20100415  TD2253
                         mainController.refreshSelectedObject1(node);//CR7
                         //CCEnd by leixiao 2010-5-7 v4r3_p015_20100415 TD2253
                    }
                    catch (QMException ex)
                    {
                        TechnicsRegulationsMainJFrame.this.setCursor(Cursor.getDefaultCursor());
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);
                        JOptionPane.showMessageDialog(
                                TechnicsRegulationsMainJFrame.this,
                                ex.getClientMessage(), title,
                                JOptionPane.INFORMATION_MESSAGE);

                    }
                    //设置状态栏
                    statusBar.setText(ParentJPanel.getIdentity(node.getObject().
                            getObject()));
                }
                else
                {
                    setNullMenu();
                }
//                 long d2=System.currentTimeMillis();
//                 System.out.println("查看时间="+(d2-d1));
            }
        });

        //添加监听：在工艺树中点击鼠标右键，显示弹出菜单。
        processTreePanel.addTreeMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                final MouseEvent e1 = e;
                Thread t = new Thread(new Runnable()
                {
                    public void run()
                    {
                        processTreePanel_mouseReleased(e1);

                    }
                });
                t.start();

            }
            public void mouseClicked(MouseEvent e)              //Begin CR2
			{

				if (e.getButton() == 3)
				{

					if (e.getSource() instanceof CappTree)
					{
						CappTree tree = (CappTree) e.getSource();
						tree.getSelectionModel().clearSelection();
						int selRow = tree.getRowForLocation(e.getX(), e.getY());
						if (selRow != -1)
						{
							tree.addSelectionRow(selRow);
						}

						technicsTreePopup.repaint();

					}

				}

			}                                                       //End CR2
            
            

        });
        //添加监听：当设备对象被选中时，如果点击鼠标右键，可以把设备添加到关联表中。
        equipmentTreePanel.addTreeMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                if (verbose)
                {
                    System.out.println(
                            "--------------------添加设备树鼠标监听------------------");
                }
                treePanel_mouseReleased(e);
                
                if (e.getButton() == 3)                //Begin CR2
				{

					if (e.getSource() instanceof CappTree)
					{
						CappTree tree = (CappTree) e.getSource();
						tree.getSelectionModel().clearSelection();
						int selRow = tree.getRowForLocation(e.getX(), e.getY());
						if (selRow != -1)
						{
							tree.addSelectionRow(selRow);
						}

					}

				}                                       //End CR2
            }


            //用于控制“添加选中对象”菜单
            public void mouseClicked(MouseEvent e)
            {
                if (e.getSource() instanceof CappTree)
                {CappTree tree = (CappTree) e.getSource();
                CappTreeObject treeObj = tree.getSelectedObject();
                if (treeObj != null &&
                    treeObj.getObject() instanceof QMEquipmentIfc
                    && (getContentJPanel().techStepIsShowing() ||
                        getContentJPanel().techPaceIsShowing()))
                {
                    jMenuAddObject.setEnabled(true);
                }
                else
                {
                    jMenuAddObject.setEnabled(false);
                }
                }
            }

        });
        //添加监听：当零件对象被选中时，如果点击鼠标右键，可以把零件添加到关联表中。
        partTreeJPanel.addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                if (verbose)
                {
                    System.out.println(
                            "--------------------添加零件树鼠标监听------------------");
                }
                treePanel_mouseReleased(e);
                
                
                if (e.getButton() == 3)                               //Begin CR2
				{

					if (e.getSource() instanceof CappTree)
					{
						CappTree tree = (CappTree) e.getSource();
						tree.getSelectionModel().clearSelection();
						int selRow = tree.getRowForLocation(e.getX(), e.getY());
						if (selRow != -1)
						{
							tree.addSelectionRow(selRow);
						}

					}

				}                                                       //End CR2
            }


            //用于控制“添加选中对象”菜单
            public void mouseClicked(MouseEvent e)
            {
                if (e.getSource() instanceof QMTree)
                {
                    QMTree tree = (QMTree) e.getSource();
                    QMNode node = tree.getSelected();
                    if (node != null)
                    {
                        Explorable object = node.getObj();
                        if (object != null &&
                            object.getObject() instanceof QMPartIfc
                            &&
                            (getContentJPanel().techMasterIsShowing() ||
                             getContentJPanel().techStepIsShowing() ||
                             getContentJPanel().techPaceIsShowing() ||
                             //CCBeginSS13
                             getContentJPanel().techMasterIsShowingForCD()))
                        	//CCEnd SS13
                        {
                            jMenuAddObject.setEnabled(true);
                        }
                        else
                        {
                            jMenuAddObject.setEnabled(false);
                        }
                    }
                }
            }
        });
        //添加监听：当工装对象被选中时，如果点击鼠标右键，可以把工装添加到关联表中。
        toolTreePanel.addTreeMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                treePanel_mouseReleased(e);
                
                if (e.getButton() == 3)                     //Begin CR2
				{

					if (e.getSource() instanceof CappTree)
					{
						CappTree tree = (CappTree) e.getSource();
						tree.getSelectionModel().clearSelection();
						int selRow = tree.getRowForLocation(e.getX(), e.getY());
						if (selRow != -1)
						{
							tree.addSelectionRow(selRow);
						}

					}

				}                                            //End CR2
            }


            //用于控制“添加选中对象”菜单
            public void mouseClicked(MouseEvent e)
            {
                if (e.getSource() instanceof CappTree)
                {CappTree tree = (CappTree) e.getSource();
                CappTreeObject treeObj = tree.getSelectedObject();
                if (treeObj != null && treeObj.getObject() instanceof QMToolIfc
                    && (getContentJPanel().techStepIsShowing() ||
                        getContentJPanel().techPaceIsShowing()))
                {
                    jMenuAddObject.setEnabled(true);
                }
                else
                {
                    jMenuAddObject.setEnabled(false);
                }
                }
            }
        });
        //添加监听：当材料对象被选中时，如果点击鼠标右键，可以把材料添加到关联表中。
        materialTreePanel.addTreeMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                treePanel_mouseReleased(e);
                
				if (e.getButton() == 3)                       //Begin CR2
				{

					if (e.getSource() instanceof CappTree)
					{
						CappTree tree = (CappTree) e.getSource();
						tree.getSelectionModel().clearSelection();
						int selRow = tree.getRowForLocation(e.getX(), e.getY());
						if (selRow != -1)
						{
							tree.addSelectionRow(selRow);
						}

					}

				}                                            //End CR2
            }


            //用于控制“添加选中对象”菜单
            public void mouseClicked(MouseEvent e)
            {
                if (e.getSource() instanceof CappTree)
                {CappTree tree = (CappTree) e.getSource();
                CappTreeObject treeObj = tree.getSelectedObject();
                if (treeObj != null &&
                    treeObj.getObject() instanceof QMMaterialIfc
                    && (getContentJPanel().techStepIsShowing() ||
                        getContentJPanel().techPaceIsShowing() ||
                        getContentJPanel().techMasterIsShowing()||
                        //CCBegin SS13
                        getContentJPanel().techMasterIsShowingForCD()))
                	//CCEnd SS13
                {
                    jMenuAddObject.setEnabled(true);
                }
                else
                {
                    jMenuAddObject.setEnabled(false);
                }
                }
            }
        });
        //添加监听：当术语对象被选中时，如果点击鼠标右键，可以把术语添加到指定文本中。
        termTreePanel.addTreeMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                treePanel_mouseReleased(e);
                
                if (e.getButton() == 3)                       //Begin CR2
				{

					if (e.getSource() instanceof CappTree)
					{
						CappTree tree = (CappTree) e.getSource();
						tree.getSelectionModel().clearSelection();
						int selRow = tree.getRowForLocation(e.getX(), e.getY());
						if (selRow != -1)
						{
							tree.addSelectionRow(selRow);
						}

					}

				}                                                //End CR2
                
            }
        });
        //添加监听：当简图对象被选中时，如果点击鼠标右键，可以新建简图并添加工序（步）中。
        drawingTreePanel.addTreeMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                treePanel_mouseReleased(e);
                
                
                if (e.getButton() == 3)                       //Begin CR2
				{

					if (e.getSource() instanceof CappTree)
					{
						CappTree tree = (CappTree) e.getSource();
						tree.getSelectionModel().clearSelection();
						int selRow = tree.getRowForLocation(e.getX(), e.getY());
						if (selRow != -1)
						{
							tree.addSelectionRow(selRow);
						}

					}

				}                                             //End CR2
            }


            //用于控制“添加选中对象”菜单
            public void mouseClicked(MouseEvent e)
            {
                if (e.getSource() instanceof CappTree)
                {CappTree tree = (CappTree) e.getSource();
                CappTreeObject treeObj = tree.getSelectedObject();
                if (treeObj != null &&
                    treeObj.getObject() instanceof DrawingIfc
                    && (getContentJPanel().techStepIsShowing() ||
                        getContentJPanel().techPaceIsShowing()))
                {
                    jMenuAddObject.setEnabled(true);
                }
                else
                {
                    jMenuAddObject.setEnabled(false);
                }
                }
            }
        });

        //添加监听：若选择工艺树，则隐藏searchTreeJButton。若选择资源树，则显示searchTreeJButton，
        //且设置菜单
        treeJTabbedPane.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                Component c = treeJTabbedPane.getSelectedComponent();
                if (c instanceof TechnicsTreePanel)
                {
                    searchTreeJButton.setVisible(false);
                    CappTreeObject obj = TechnicsRegulationsMainJFrame.this.
                                         getSelectedObject();
                    boolean ispersonal = false;
                    if (obj != null && obj instanceof BusinessTreeObject)
                    {
                        try
                        {
                            ispersonal = !CheckInOutCappTaskLogic.isInVault((
                                    WorkableIfc) obj.getObject());
                        }
                        catch (QMRemoteException ex)
                        {
                            ex.printStackTrace();
                            return;
                        }

                    }
                    enableMenuItems(obj, ispersonal);
                }
                else
                {
                    searchTreeJButton.setVisible(true);
                    setNullMenu();
                    if (c instanceof ResourceTreePanel)
                    {
                        CappTreeObject treeObj = ((ResourceTreePanel) c).
                                                 getSelectedObject();
                        if (treeObj != null)
                        {
                            //资源树上选中的节点是材料
                            boolean flag1 = treeObj.getObject() instanceof
                                            QMMaterialIfc
                                            &&
                                            (getContentJPanel().
                                             techStepIsShowing() ||
                                             getContentJPanel().
                                             techPaceIsShowing() ||
                                             getContentJPanel().
                                             techMasterIsShowing() ||
                                             //CCBegin SS13
                                             getContentJPanel().techMasterIsShowingForCD());
                                             //CCEnd SS13
                            //资源树上选中的节点是设备或工装或简图
                            boolean flag2 = (treeObj.getObject() instanceof
                                             QMEquipmentIfc ||
                                             treeObj.getObject() instanceof
                                             QMToolIfc ||
                                             treeObj.getObject() instanceof
                                             DrawingIfc)
                                            &&
                                            (getContentJPanel().
                                             techStepIsShowing() ||
                                             getContentJPanel().
                                             techPaceIsShowing());

                            if (flag1 || flag2)
                            {
                                jMenuAddObject.setEnabled(true);
                            }
                            else
                            {
                                jMenuAddObject.setEnabled(false);
                            }
                        }
                    }
                    if (c instanceof PartTreePanel)
                    {
                        QMNode node = ((PartTreePanel) c).getSelected();
                        if (node != null)
                        {
                            Explorable object = node.getObj();
                            if (object != null &&
                                object.getObject() instanceof QMPartIfc
                                && (getContentJPanel().techMasterIsShowing()
                                    || getContentJPanel().techStepIsShowing() ||
                                    getContentJPanel().techPaceIsShowing()))
                            {
                                jMenuAddObject.setEnabled(true);
                            }
                            else
                            {
                                jMenuAddObject.setEnabled(false);
                            }
                        }
                    }
                }
            }
        }
        );

        //setIconImage(Toolkit.getDefaultToolkit().createImage(TechnicsRegulationsMainJFrame.class.getResource("[Your Icon]")));
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);

        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setTitle("工艺规程管理器");
        setIconImage(new ImageIcon(getClass().getResource(
                "/images/technics.gif")).getImage());
        setNullMenu();

        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        jMenuFile.setDoubleBuffered(false);
        jMenuFile.setMnemonic('F');
        jMenuFile.setText("文件(F)");
        jMenuFileExit.setText("退出(X)");
        jMenuFileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke('Q',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuFileExit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jMenuFileExit_actionPerformed();
            }
        });
        jMenuHelp.setMnemonic('H');
        jMenuHelp.setText("帮助(H)");
        jMenuHelpAbout.setMnemonic('A');
        jMenuHelpAbout.setText("关于(A)");
        jMenuHelpAbout.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jMenuHelpAbout_actionPerformed(e);
            }
        });

        jMenuHelpItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jMenuHelpItem_actionPerformed(e);
            }
        });

        leftJPanel.setLayout(gridBagLayout3);
        searchTreeJButton.setMaximumSize(new Dimension(75, 23));
        searchTreeJButton.setMinimumSize(new Dimension(75, 23));
        searchTreeJButton.setPreferredSize(new Dimension(75, 23));
        searchTreeJButton.setMnemonic('T');
        searchTreeJButton.setText("搜索(T)");
        searchTreeJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                searchTreeJButton_actionPerformed(e);
            }
        });
        leftJLabel.setBorder(BorderFactory.createEtchedBorder());
        leftJLabel.setToolTipText("");
        leftJLabel.setText("工艺树及资源分类树");
        rightJLabel.setBorder(BorderFactory.createEtchedBorder());
        rightJLabel.setText("工艺内容");
        rightJPanel.setLayout(gridBagLayout2);
        contentJPanel.setEnabled(true);
        contentJPanel.setBorder(BorderFactory.createEtchedBorder());
        contentJPanel.setDebugGraphicsOptions(0);

        jSplitPane.setResizeWeight(1.0);

        jMenuFileCollect.setText("合并(U)");
//        CCBegin SS8
        jMenuCTFileCollect.setText("长特工艺合并");
//        CCEnd SS8
        //jMenuFileImport.setText("导入");
        jMenuFileExport.setText("导出(R)");
        jMenuFileRefresh.setText("刷新(E)");
        jMenuFileCreate.setActionCommand("新建(N)");
        jMenuFileCreate.setMnemonic('N');
        jMenuFileCreate.setText("新建(N)");
        jMenuCreateTechnics.setText("工艺主信息");
        jMenuCreateStep.setText("工序");
        jMenuCreatePace.setText("工步...");
        jMenuSelect.setMnemonic('P');
        jMenuSelect.setText("选中(P)");
        jMenuSelectView.setText("查看(V)");
        jMenuSelectUpdate.setText("更新(U)");
        jMenuSelectFormStepNum.setText("重新生成工序号(R)");
        //CR1 begin
        jMenuSelectFormPaceNum.setText("重新生成工步号(B)");
        //CR1 end
        jMenuSelectBrowse.setText("打印预览(Y)");
        jMenuSelectChangeLocation.setText("更改存储位置(L)");
        jMenuSelectRename.setText("重命名(M)");
        jMenuSelectSaveAs.setText("另存为(G)");
        jMenuSelectUseTech.setText("应用典型工艺(H)");
        jMenuSelectUseStep.setText("应用典型工序(I)");
        jMenuSelectMadeTech.setText("生成典型工艺(J)");
        jMenuSelectMadeStep.setText("生成典型工序(K)");
        jMenuSelectCopy.setText("复制(C)");
        jMenuSelectCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke('C',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuSelectPaste.setText("粘贴(P)");
        //CCBegin SS9
        //jMenuSelectPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke('V',
        jMenuSelectPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke('P',
        //CCEnd SS9
                java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuVersion.setMnemonic('V');
        jMenuVersion.setText("版本(V)");
        jMenuVersionCheckIn.setText("检入(I)");
        jMenuVersionCheckIn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                'R', java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuVersionCheckOut.setText("检出(O)");
        jMenuVersionCheckOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                'T', java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuVersionCancel.setText("撤销检出(U)");
        jMenuVersionCancel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                'Z', java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuVersionRevise.setText("修订(R)");
        jMenuVersionRevise.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                'E', java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuVersionVersion.setText("查看版本历史(N)");
        jMenuVersionIteration.setText("查看版序历史(T)");
        jMenuLifeCycle.setMnemonic('L');
        jMenuLifeCycle.setText("生命周期(L)");
        jMenuSetLifeCycleState.setMnemonic('S');
        jMenuSetLifeCycleState.setText("重新指定生命周期状态(S)");
        jMenuLifeCycleSelect.setText("重新指定生命周期(L)");
        jMenuLifeCycleView.setText("查看生命周期历史(H)");
        //2008.03.09 徐德政改,将“项目组”改为“工作组”
        jMenuLifeCycleGroup.setText("重新指定工作组(P)");
        //end mario
        //jMenuIntellect.setText("智能工艺");
        jMenuIntellectPart.setText("零件族工艺派生");
        jMenuIntellectRoot.setText("加工路线工艺派生");
        //CCBegin SS1
        if(isBsxGroupUser()){
        	jMenuSchedule.setMnemonic('M');
        	jMenuSchedule.setText("生成派生文件(M)");
        	jMItemjiaju.setText("夹具明细表");
        	jMItemwanneng.setText("万能工具清单");
        	//CCBegin SS5
        	jMItemwannengliangju.setText("万能量具清单");
        	jMItemgongweiqiju.setText("工位器具清单");
        	//CCEnd SS5
        	jMItemequip.setText("设备清单");
        	jMItemmoju.setText("磨具一览表");
        	jMItemdaoju.setText("刀具一览表");
        	jMItemjiafuju.setText("夹辅具一览表");
        	jMItemzhuangpei.setText("装配工具一览表");
        }
        //CCBegin SS4
        if(getUserFromCompany().equals("zczx")){
        	jMenuSchedule.setMnemonic('M');
        	jMenuSchedule.setText("生成派生文件(M)");
        	jMItemzcjiaju.setText("轴齿夹具明细表");
        	jMItemzcwangong.setText("轴齿万能工具明细表");
        	jMItemzcwanliang.setText("轴齿万能量具明细表");
        	jMItemzcjianju.setText("轴齿检具明细表");
        	jMItemzcsb.setText("轴齿设备清单");
        	jMItemzcmoju.setText("轴齿磨具一览表");
        	jMItemzcdaoju.setText("轴齿刀具一览表");
        	//        	CCBegin SS6
        	jMItemzcdaofuju.setText("轴齿刀辅具一览表");
//        	CCEnd SS6
        	jMItemzcjiafuju.setText("轴齿夹辅具一览表");
        	jMItemzcliangju.setText("轴齿量具一览表");
        	jMItemzcjianfuju.setText("轴齿检辅具一览表");
        }
      //CCEnd SS4
            //CCBegin SS7
        if(getUserFromCompany().equals("ct")){
        	jMenuSchedule.setMnemonic('M');
        	jMenuSchedule.setText("生成派生文件(M)");
        	jMItemCtTool.setText("长特工装明细表");
        	jMItemCtSheBei.setText("长特设备清单");
        	jMItemCtMoJu.setText("长特模具清单");
        	
        }
      //CCEnd SS7
        //CCEnd SS1
        jMenuSearch.setMnemonic('S');
        jMenuSearch.setText("搜索(S)");
        jMenuSearchEquip.setText("按设备搜索工艺(E)");
        jMenuSearchTool.setText("按工装搜索工艺(T)");
        jMenuSearchMaterial.setText("按材料搜索工艺(M)");
        jMenuSearchTechnics.setText("搜索工艺规程(S)");
        jMenuSearchTechnics.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                'F', java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuHelpManage.setText("工艺规程管理");
        jMenuHelpManage.setEnabled(false);
        jMenuSelectDelete.setText("删除(D)");
        jMenuSelectDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke('D',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        //Begin CR3
        jMenuSelectMoveOut.setText("移除(K)");  
        jMenuSelectMoveOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke('K',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        //End CR3 
      //CCBegin SS11
        jMenuStructSearchTechnics.setText("结构搜索工艺规程");
        //CCEnd SS11
        jSplitPane.setMinimumSize(new Dimension(100, 226));
        jSplitPane.setContinuousLayout(true);
        jSplitPane.setDividerSize(5);
        jSplitPane.setOneTouchExpandable(false);
        leftJPanel.setBorder(null);
        leftJPanel.setDebugGraphicsOptions(0);
        leftJPanel.setMinimumSize(new Dimension(100, 224));
        leftJPanel.setPreferredSize(new Dimension(280, 524));
        jMenuAddObject.setEnabled(false);
        jMenuAddObject.setText("添加选中对象(A)");
        jMenuConfigCrit.setActionCommand("配置规则");  
        jMenuConfigCrit.setMnemonic('C');
        jMenuConfigCrit.setText("配置规范(C)");
        jMenuHelpItem.setMinimumSize(new Dimension(0, 0));
        jMenuHelpItem.setMnemonic('P');
        jMenuHelpItem.setText("工艺规程信息管理器(P)");
        jMenuHelpItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                KeyEvent.VK_F1, 0));
        treeJTabbedPane.setBorder(BorderFactory.createEtchedBorder());
        jMenuExportAll.setMinimumSize(new Dimension(0, 0));
        jMenuExportAll.setMnemonic('P');
        jMenuExportAll.setText("导出全部工艺(P)");
        jMenuFile.add(jMenuFileCreate);
        jMenuFile.add(jMenuFileCollect);
//        CCBegin SS8
        if(this.isCTGroupUser())
        	jMenuFile.add(jMenuCTFileCollect);
//        CCEnd SS8
        // jMenuFile.add(jMenuFileImport);
        jMenuFile.add(jMenuFileExport);
        jMenuFile.add(jMenuExportAll);
        jMenuFile.addSeparator();
        jMenuFile.add(jMenuFileRefresh);
        jMenuFile.add(jMenuFileExit);
        //jMenuHelp.add(jMenuHelpManage);
        jMenuHelp.add(jMenuHelpItem);
        jMenuHelp.add(jMenuHelpAbout);
        jMenuBar1.add(jMenuFile);
        jMenuBar1.add(jMenuSelect);
        jMenuBar1.add(jMenuVersion);
        jMenuBar1.add(jMenuLifeCycle);
        
        
        
        //SSBegin SS2
//        jMenuBar1.add(jMenuSchedule);
        //CCBegin SS7
        //CCBegin SS3
        if(isBsxGroupUser()||getUserFromCompany().equals("zczx")||getUserFromCompany().equals("ct"))
        //CCEnd SS3
        //CCEnd SS7
        {
        	jMenuBar1.add(jMenuSchedule);
        }
        //SSEnd SS2
        jMenuBar1.add(jMenuSearch);
        jMenuBar1.add(jMenuHelp);
        setJMenuBar(jMenuBar1);
        contentPane.add(qmToolBar, BorderLayout.NORTH);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(jSplitPane, BorderLayout.CENTER);
        jSplitPane.add(rightJPanel, JSplitPane.RIGHT);
        jSplitPane.add(leftJPanel, JSplitPane.LEFT);

        leftJPanel.add(searchTreeJButton,
                       new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(10, 0, 10, 0), 0, 0));
        leftJPanel.add(leftJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        leftJPanel.add(treeJTabbedPane,
                       new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(0, 0, 0, 0), 0, 0));
        treeJTabbedPane.add(processTreePanel,
                            QMMessage.getLocalizedMessage(RESOURCE,
                "technicsTreeTitle", null));
        treeJTabbedPane.add(equipmentTreePanel,
                            QMMessage.getLocalizedMessage(RESOURCE,
                "equipmentTitle", null));
        treeJTabbedPane.add(toolTreePanel,
                            QMMessage.getLocalizedMessage(RESOURCE, "toolTitle", null));
        treeJTabbedPane.add(materialTreePanel,
                            QMMessage.getLocalizedMessage(RESOURCE,
                "materialTitle", null));
        treeJTabbedPane.add(termTreePanel,
                            QMMessage.getLocalizedMessage(RESOURCE,
                "termTreeTitle", null));
        treeJTabbedPane.add(drawingTreePanel,
                            QMMessage.getLocalizedMessage(RESOURCE,
                "drawingTreeTitle", null));
        treeJTabbedPane.add(partTreeJPanel, "零部件");

        jSplitPane.setDividerLocation(280);
        rightJPanel.add(rightJLabel,
                        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(0, 0, 0, 0), 0, 0));
        rightJPanel.add(contentJPanel,
                        new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               new Insets(0, 0, 0, 0), 0, 0));

        jMenuFileCreate.add(jMenuCreateTechnics);
        jMenuFileCreate.add(jMenuCreateStep);
        jMenuFileCreate.add(jMenuCreatePace);
        jMenuSelect.add(jMenuSelectView);
        jMenuSelect.add(jMenuSelectUpdate);
        jMenuSelect.add(jMenuSelectDelete);
        //Begin CR3	
        jMenuSelect.add(jMenuSelectMoveOut);
        //End CR3
        jMenuSelect.addSeparator();
        jMenuSelect.add(jMenuAddObject);
        jMenuSelect.add(jMenuSelectFormStepNum);
        //CR1 begin
        jMenuSelect.add(jMenuSelectFormPaceNum);
        //CR1 end
        jMenuSelect.add(jMenuSelectBrowse);
        jMenuSelect.addSeparator();
        jMenuSelect.add(jMenuSelectChangeLocation);
        jMenuSelect.add(jMenuSelectRename);
        jMenuSelect.add(jMenuSelectSaveAs);
        jMenuSelect.addSeparator();
//        jMenuSelect.add(jMenuSelectUseTech);
//        jMenuSelect.add(jMenuSelectUseStep);
//        jMenuSelect.addSeparator();
//        jMenuSelect.add(jMenuSelectMadeTech);
//        jMenuSelect.add(jMenuSelectMadeStep);
//        jMenuSelect.addSeparator();
        jMenuSelect.add(jMenuSelectCopy);
        jMenuSelect.add(jMenuSelectPaste);
        jMenuVersion.add(jMenuVersionCheckIn);
        jMenuVersion.add(jMenuVersionCheckOut);
        jMenuVersion.add(jMenuVersionCancel);
        jMenuVersion.addSeparator();
        jMenuVersion.add(jMenuVersionRevise);
        jMenuVersion.addSeparator();
        jMenuVersion.add(jMenuVersionVersion);
        jMenuVersion.add(jMenuVersionIteration);
        jMenuLifeCycle.add(jMenuSetLifeCycleState);
        jMenuLifeCycle.addSeparator();
        jMenuLifeCycle.add(jMenuLifeCycleSelect);
        jMenuLifeCycle.add(jMenuLifeCycleView);
        jMenuLifeCycle.addSeparator();
        jMenuLifeCycle.add(jMenuLifeCycleGroup);
        //CCBegin SS1
        if(isBsxGroupUser()){
        	jMenuSchedule.add(jMItemjiaju);
        	jMenuSchedule.add(jMItemwanneng);
        	//CCBegin SS5
        	jMenuSchedule.add(jMItemwannengliangju);
        	jMenuSchedule.add(jMItemgongweiqiju);
        	//CCEnd SS5
        	jMenuSchedule.add(jMItemequip);
        	jMenuSchedule.add(jMItemmoju);
        	jMenuSchedule.add(jMItemdaoju);
        	jMenuSchedule.add(jMItemjiafuju);
        	jMenuSchedule.add(jMItemzhuangpei);
        }
        //CCEnd SS1
        //CCBegin SS4
        if(getUserFromCompany().equals("zczx")){
        	jMenuSchedule.add(jMItemzcjiaju);
        	jMenuSchedule.add(jMItemzcwangong);
        	jMenuSchedule.add(jMItemzcwanliang);
        	jMenuSchedule.add(jMItemzcjianju);
        	jMenuSchedule.add(jMItemzcsb);
        	jMenuSchedule.add(jMItemzcmoju);
        	jMenuSchedule.add(jMItemzcdaoju);
        	//        	CCBegin SS6
        	jMenuSchedule.add(jMItemzcdaofuju);
//        	CCEnd SS6
        	jMenuSchedule.add(jMItemzcjiafuju);
        	jMenuSchedule.add(jMItemzcliangju);
        	jMenuSchedule.add(jMItemzcjianfuju);
        }
        //CCEnd SS4
        
        //CCBegin SS7
        if(getUserFromCompany().equals("ct")){
        	jMenuSchedule.add(jMItemCtTool);
        	jMenuSchedule.add(jMItemCtSheBei);
        	jMenuSchedule.add(jMItemCtMoJu);
        }
        //CCEnd SS7
        
        //jMenuIntellect.add(jMenuIntellectPart);
        //jMenuIntellect.add(jMenuIntellectRoot);
        jMenuSearch.add(jMenuConfigCrit);
        jMenuSearch.addSeparator();
        jMenuSearch.add(jMenuSearchEquip);
        jMenuSearch.add(jMenuSearchTool);
        jMenuSearch.add(jMenuSearchMaterial);
        jMenuSearch.addSeparator();
        jMenuSearch.add(jMenuSearchTechnics);
        
        //CCBegin SS11
        jMenuSearch.add(jMenuStructSearchTechnics);
        //CCEnd SS11

        //{{注册监听
        RMWindow rmWindow = new RMWindow();
        addWindowListener(rmWindow);
        RMAction rmAction = new RMAction();
        jMenuFileCollect.addActionListener(rmAction);
//        CCBegin SS8
         jMenuCTFileCollect.addActionListener(rmAction);
//        CCEnd SS8
        //jMenuFileImport.addActionListener(rmAction);
         
         //CCBegin SS10
         jMenuCheckAll.setMnemonic('B');
         jMenuCheckAll.addActionListener(rmAction);
         //CCEnd SS10
         
         //CCBegin SS12
         jMenuFindMainStep.setMnemonic('M');
         jMenuFindMainStep.addActionListener(rmAction);
         //CCEnd SS12
         
         //CCBegin SS11
         jMenuStructSearchTechnics.addActionListener(rmAction);
         //CCEnd SS11
         
        jMenuFileExport.addActionListener(rmAction);
        jMenuExportAll.addActionListener(rmAction);
        jMenuFileRefresh.addActionListener(rmAction);
        jMenuCreateTechnics.addActionListener(rmAction);
        jMenuCreateStep.addActionListener(rmAction);
        jMenuCreatePace.addActionListener(rmAction);
        jMenuSelectView.addActionListener(rmAction);
        jMenuSelectUpdate.addActionListener(rmAction);
        jMenuSelectFormStepNum.addActionListener(rmAction);
        //CR1 begin
        jMenuSelectFormPaceNum.addActionListener(rmAction);
        //CR1 end
        jMenuSelectBrowse.addActionListener(rmAction);
        jMenuSelectChangeLocation.addActionListener(rmAction);
        jMenuSelectRename.addActionListener(rmAction);
        jMenuSelectSaveAs.addActionListener(rmAction);
        jMenuSelectUseTech.addActionListener(rmAction);
        jMenuSelectUseStep.addActionListener(rmAction);
        jMenuSelectMadeTech.addActionListener(rmAction);
        jMenuSelectMadeStep.addActionListener(rmAction);
        jMenuSelectCopy.addActionListener(rmAction);
        jMenuSelectPaste.addActionListener(rmAction);
        jMenuVersionCheckIn.addActionListener(rmAction);
        jMenuVersionCheckOut.addActionListener(rmAction);
        jMenuVersionCancel.addActionListener(rmAction);
        jMenuVersionRevise.addActionListener(rmAction);
        jMenuVersionVersion.addActionListener(rmAction);
        jMenuVersionIteration.addActionListener(rmAction);
        jMenuSetLifeCycleState.addActionListener(rmAction);
        jMenuLifeCycleSelect.addActionListener(rmAction);
        jMenuLifeCycleView.addActionListener(rmAction);
        jMenuLifeCycleGroup.addActionListener(rmAction);
        jMenuIntellectPart.addActionListener(rmAction);
        jMenuIntellectRoot.addActionListener(rmAction);
        jMenuSearchEquip.addActionListener(rmAction);
        jMenuSearchTool.addActionListener(rmAction);
        jMenuSearchMaterial.addActionListener(rmAction);
        jMenuSearchTechnics.addActionListener(rmAction);
        jMenuHelpManage.addActionListener(rmAction);
        jMenuSelectDelete.addActionListener(rmAction);
      //CCBegin SS1
        if(isBsxGroupUser()){
        	jMItemjiaju.addActionListener(rmAction);
        	jMItemwanneng.addActionListener(rmAction);
        	//CCBegin SS5
        	jMItemwannengliangju.addActionListener(rmAction);
        	jMItemgongweiqiju.addActionListener(rmAction);
        	//CCEnd SS5
        	jMItemequip.addActionListener(rmAction);
        	jMItemmoju.addActionListener(rmAction);
        	jMItemdaoju.addActionListener(rmAction);
        	jMItemjiafuju.addActionListener(rmAction);
        	jMItemzhuangpei.addActionListener(rmAction);
        }
      //CCEnd SS1
        //CCBegin SS4
        if(getUserFromCompany().equals("zczx")){
        	jMItemzcjiaju.addActionListener(rmAction);
        	jMItemzcwangong.addActionListener(rmAction);
        	jMItemzcwanliang.addActionListener(rmAction);
        	jMItemzcjianju.addActionListener(rmAction);
        	jMItemzcsb.addActionListener(rmAction);
        	jMItemzcmoju.addActionListener(rmAction);
        	jMItemzcdaoju.addActionListener(rmAction);
        	//        	CCBegin SS6
        	jMItemzcdaofuju.addActionListener(rmAction);
//        	CCEnd SS6
        	jMItemzcjiafuju.addActionListener(rmAction);
        	jMItemzcliangju.addActionListener(rmAction);
        	jMItemzcjianfuju.addActionListener(rmAction);
        }
        //CCEnd SS4
        //CCBegin SS7
        if(getUserFromCompany().equals("ct")){
        	jMItemCtTool.addActionListener(rmAction);
        	jMItemCtSheBei.addActionListener(rmAction);
        	jMItemCtMoJu.addActionListener(rmAction);
        }
        
        //CCEnd SS7
        
        //Begin CR3
        jMenuSelectMoveOut.addActionListener(rmAction);
        //End CR3
        jMenuCreateTechnics1.addActionListener(rmAction);
        jMenuCreateStep1.addActionListener(rmAction);
        jMenuCreatePace1.addActionListener(rmAction);
        // jMenuAddObject.addActionListener(rmAction);
        jMenuSelectView1.addActionListener(rmAction);
        //CCBegin SS15
        jMenuSelectExport.addActionListener(rmAction);
        //CCEnd SS15
        jMenuSelectUpdate1.addActionListener(rmAction);
        jMenuSelectDelete1.addActionListener(rmAction);
        //Begin CR3
        jMenuSelectMoveOut1.addActionListener(rmAction);
        //End CR3
        jMenuSelectSaveAs1.addActionListener(rmAction);
        jMenuSelectBrowse1.addActionListener(rmAction);
        jMenuFileCollect1.addActionListener(rmAction);
//        CCBegin SS8
        jMenuCTFileCollect1.addActionListener(rmAction);
//        CCEnd SS8
        jMenuSelectCopy1.addActionListener(rmAction);
        jMenuSelectPaste1.addActionListener(rmAction);
        jMenuConfigCrit.addActionListener(rmAction);
        AddAction add = new AddAction(QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuAddObject", null));
        jMenuAddObject.addActionListener(add);
        initPopup();
        initTechnicsTreePopup();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        localize();
        jMenuFileCollect.setMnemonic('O');
        jMenuFileExport.setMnemonic('R');
        jMenuFileRefresh.setMnemonic('E');
        jMenuFileExit.setMnemonic('X');
        jMenuSelectView.setMnemonic('V');
        jMenuSelectUpdate.setMnemonic('U');
        jMenuSelectRename.setMnemonic('M');
        jMenuSelectSaveAs.setMnemonic('G');
        jMenuSelectCopy.setMinimumSize(new Dimension(0, 0));
        jMenuSelectCopy.setMnemonic('C');
        jMenuSelectPaste.setMnemonic('P');
        jMenuSelectBrowse.setMnemonic('V');
        jMenuSelectDelete.setMnemonic('D');
        jMenuSelectBrowse.setMnemonic('Y');
        jMenuAddObject.setMnemonic('A');
        jMenuSelectFormStepNum.setMnemonic('R');
        //CR1 begin
        jMenuSelectFormPaceNum.setMnemonic('B');
        //CR1 end
        jMenuSelectBrowse.setMinimumSize(new Dimension(0, 0));
        jMenuSelectChangeLocation.setMnemonic('L');
        jMenuSelectUseStep.setMnemonic('I');
        jMenuSelectUseTech.setMnemonic('H');
        jMenuSelectMadeTech.setMnemonic('J');
        jMenuSelectMadeStep.setMnemonic('K');
        jMenuVersionCheckIn.setMnemonic('I');
        jMenuVersionCheckIn.setToolTipText("");
        jMenuVersionCheckOut.setMnemonic('O');
        jMenuVersionCancel.setMnemonic('U');
        jMenuLifeCycleView.setMnemonic('H');
        jMenuSearchEquip.setMnemonic('E');
        jMenuSearchTool.setMnemonic('T');
        jMenuSearchMaterial.setMnemonic('M');
        jMenuSearchTechnics.setMinimumSize(new Dimension(0, 0));
        jMenuSearchTechnics.setMnemonic('S');
        jMenuVersionRevise.setMnemonic('R');
        jMenuVersionVersion.setMnemonic('N');
        jMenuVersionIteration.setMnemonic('T');
        jMenuLifeCycleGroup.setMnemonic('P');
        jMenuLifeCycleSelect.setMnemonic('L');
        jMenuVersionVersion.setMinimumSize(new Dimension(0, 0));
        jMenuCreateTechnics.setMnemonic('M');
        jMenuCreateStep.setMnemonic('S');
        jMenuCreatePace.setMnemonic('P');

        jMenuFileCreate1.setMnemonic('N');
        jMenuCreateTechnics1.setMnemonic('M');
        jMenuCreateStep1.setMnemonic('S');
        jMenuCreatePace1.setMnemonic('P');
        jMenuFileCollect1.setMnemonic('O');
        jMenuSelectView1.setMnemonic('V');
        jMenuSelectUpdate1.setMnemonic('U');
        jMenuSelectDelete1.setMnemonic('D');
        jMenuSelectBrowse1.setMnemonic('Y');
        jMenuSelectCopy1.setMnemonic('C');
        jMenuSelectPaste1.setMnemonic('P');
        jMenuSelectSaveAs1.setMnemonic('G');

        //搜索按钮的鼠标键听,设置状态栏
        searchTreeJButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent e)
            {
                String object = null;
                switch (treeJTabbedPane.getSelectedIndex())
                {
                    case 1:
                        object = QMMessage.getLocalizedMessage(RESOURCE,
                                "equipmentTitle", null);
                        break;
                    case 2:
                        object = QMMessage.getLocalizedMessage(RESOURCE,
                                "toolTitle", null);
                        break;
                    case 3:
                        object = QMMessage.getLocalizedMessage(RESOURCE,
                                "materialTitle", null);
                        break;
                    case 4:
                        object = QMMessage.getLocalizedMessage(RESOURCE,
                                "termTreeTitle", null);
                        break;
                    case 5:
                        object = QMMessage.getLocalizedMessage(RESOURCE,
                                "drawingTreeTitle", null);
                        break;
                    case 6:
                        object = QMMessage.getLocalizedMessage(RESOURCE,
                                "partTitle", null);
                        break;
                }
                statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
                        "searchTreeJButton_status", new Object[]
                        {object}));
            }

            public void mouseExited(MouseEvent e)
            {
                statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
                        "default_status", null));
            }
        });

        //设置状态栏
        statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "default_status", null));
        progressDialog = new ProgressDialog(this);
    } //End jbInit()


    /**
     * 界面信息本地化
     */
    public void localize()
    {
        initResources();

        try
        {
            setTitle(QMMessage.getLocalizedMessage(RESOURCE,
                    "technicsReguMainTitle", null));
            jMenuFile.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuFile", null));
            jMenuFileExit.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuFileExit", null));
            jMenuHelp.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuHelp", null));
            jMenuHelpAbout.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuHelpAbout", null));
//            jMenuHelpItem.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                    "jMenuHelpItem", null));
            searchTreeJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "searchTreeJButton", null));
            leftJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "leftJLabel", null));
            rightJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "rightJLabel", null));
            jMenuFileCollect.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuFileCollect", null));
            jMenuFileCollect1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuFileCollect1", null));
//            CCBegin SS8
            jMenuCTFileCollect1.setText("长特工艺合并");
            jMenuCTFileCollect.setText("长特工艺合并");
//            CCEnd SS8
            // jMenuFileImport.setText(QMMessage.getLocalizedMessage(RESOURCE,"jMenuFileImport",null));
            jMenuFileExport.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuFileExport", null));
            jMenuExportAll.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuExportAll", null));
            jMenuFileRefresh.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuFileRefresh", null));
            jMenuFileCreate.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuFileCreate", null));
            jMenuFileCreate1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuFileCreate", null));
            jMenuCreateTechnics.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuCreateTechnics", null));
            jMenuCreateTechnics1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuCreateTechnics", null));
            jMenuCreateStep.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuCreateStep", null));
            jMenuCreateStep1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuCreateStep", null));
            jMenuCreatePace.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuCreatePace", null));
            jMenuCreatePace1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuCreatePace", null));
            jMenuSelect.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelect", null));
            jMenuSelectView.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectView", null));
            jMenuSelectView1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectView", null));
            jMenuSelectUpdate.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectUpdate", null));
            jMenuSelectUpdate1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectUpdate", null));
            jMenuSelectFormStepNum.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "jMenuSelectFormStepNum", null));
            //CR1 begin
			jMenuSelectFormPaceNum.setText(QMMessage.getLocalizedMessage(
					RESOURCE, "jMenuSelectFormPaceNum", null));
			//CR1 end
            jMenuSelectBrowse.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectBrowse", null));
            jMenuSelectBrowse1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectBrowse", null));
            jMenuSelectChangeLocation.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "jMenuSelectChangeLocation", null));
            jMenuSelectRename.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectRename", null));
            jMenuSelectSaveAs.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectSaveAs", null));
            jMenuSelectSaveAs1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectSaveAs", null));
            jMenuSelectUseTech.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectUseTech", null));
            jMenuSelectUseStep.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectUseStep", null));
            jMenuSelectMadeTech.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectMadeTech", null));
            jMenuSelectMadeStep.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectMadeStep", null));
            jMenuSelectCopy.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectCopy", null));
            jMenuSelectCopy1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectCopy", null));
            jMenuSelectPaste.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectPaste", null));
            jMenuSelectPaste1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectPaste", null));
            jMenuVersion.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuVersion", null));
            jMenuVersionCheckIn.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuVersionCheckIn", null));
            jMenuVersionCheckOut.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuVersionCheckOut", null));
            jMenuVersionCancel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuVersionCancel", null));
            jMenuVersionRevise.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuVersionRevise", null));
            jMenuVersionVersion.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuVersionVersion", null));
            jMenuVersionIteration.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "jMenuVersionIteration", null));
            jMenuLifeCycle.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuLifeCycle", null));
            jMenuSetLifeCycleState.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "jMenuSetLifeCycleState", null));
            jMenuLifeCycleSelect.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuLifeCycleSelect", null));
            jMenuLifeCycleView.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuLifeCycleView", null));
            jMenuLifeCycleGroup.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuLifeCycleGroup", null));
            //jMenuIntellect.setText(QMMessage.getLocalizedMessage(RESOURCE,"jMenuIntellect",null));
            jMenuIntellectPart.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuIntellectPart", null));
            jMenuIntellectRoot.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuIntellectRoot", null));
            jMenuSearch.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSearch", null));
            jMenuSearchEquip.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSearchEquip", null));
            jMenuSearchTool.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSearchTool", null));
            jMenuSearchMaterial.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSearchMaterial", null));
            jMenuSearchTechnics.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSearchTechnics", null));
            jMenuHelpManage.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuHelpManage", null));
            jMenuSelectDelete.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectDelete", null));
            jMenuSelectDelete1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectDelete", null));
            //Begin CR3
            jMenuSelectMoveOut.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectMoveOut", null));
            jMenuSelectMoveOut1.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuSelectMoveOut", null));
            //End CR3
            jMenuAddObject.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuAddObject", null));
            jMenuConfigCrit.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "jMenuConfigCrit", null));
            partTreeJPanel.getRoot().setLabel(QMMessage.getLocalizedMessage(
                    RESOURCE, "productStructure", null));
            
            //CCBegin SS15
            jMenuSelectExport.setText("导出");
            //CCEnd SS15
            
            //CCBegin SS10
            
            jMenuCheckAll.setText("检出所有工序工步");
            
            //CCEnd SS10
            
            //CCBegin SS12
            
            jMenuFindMainStep.setText("关键工序筛选");
            //CCEnd SS12
            
            //CCBegin SS11
            
            jMenuStructSearchTechnics.setText("结构搜索工艺规程");
            
            //CCEnd SS11

        }
        catch (Exception ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.MISSING_RESOURCER, null);
            JOptionPane.showMessageDialog(this, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

    }


    /**
     * 设置界面的显示位置
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2,
                    (screenSize.height - frameSize.height) / 2);

    }


    /**
     * 重载父类方法
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        setViewLocation();
        super.setVisible(flag);
    }


    /**
     * “退出“菜单的动作事件方法
     * @param e
     */
    public void jMenuFileExit_actionPerformed()
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        int i = JOptionPane.showConfirmDialog(this, getMessage("ifQuitMessage"),
                                              title,
                                              JOptionPane.WARNING_MESSAGE);
        switch (i)

        {
            case JOptionPane.CANCEL_OPTION:
                return;
            default:
                if (notExitSystem)
                {
                    dispose(); // dispose of the Frame.
                }
                else
                {
                	//20060323薛静删除
//                    try
//                    {
//                        org.jcad.JCad.SymbolDelgateHelper.my_vueBean.
//                                getServerControl().sessionClose();
//                    }
//                    catch (Exception ie)
//                    {
//                        ie.printStackTrace();
//                    }
                	//begin CR4
                    File file = new File(ToolSelectedDialog.tempViewDxfPath);
                    if (file.exists())
                    {
                        file.delete();
                    }
                    
                    File file1 = new File(ToolSelectedDialog.tempViewPicPath+".jpg");
                    if (file1.exists())
                    {
                        file1.delete();
                    }
                    //end CR4
                    System.exit(0);
                }
        }

    }


    /**
     * “关于”菜单的动作事件方法
     * @param e
     */
    public void jMenuHelpAbout_actionPerformed(ActionEvent e)
    {
//        TechnicsRegulationsMainJFrame_AboutBox dlg = new
//                TechnicsRegulationsMainJFrame_AboutBox(this);
        //modify by wangh on 20070611(调用架构给的新方法用于弹出关于界面)
        IntroduceDialog dlg = new IntroduceDialog(this,"工艺规程管理器");
        dlg.setVisible();
        //dlg.setModal(true);
        //dlg.pack();
        //dlg.setVisible(true);
    }


    /**
     * “帮助”菜单的动作事件方法
     * @param e
     */
    public void jMenuHelpItem_actionPerformed(ActionEvent e)
    {
        if (helpSystem == null)
        {
            try
            {
                helpSystem = new QMHelpSystem("Capp", null, "OnlineHelp",
                                              ResourceBundle.getBundle(
                        "com.faw_qm.cappclients.capp.util.CappLMRB",
                        RemoteProperty.getVersionLocale()));
            }
            catch (Exception exception)
            {
                (new MessageDialog(this, true,
                                   exception.getLocalizedMessage())).setVisible(true);
            }
        }
        helpSystem.showHelp("QMTechnics");

    }


    /**
     * 处理工具条上的按钮的命令.当用户点击工具条上的按钮时会发出一个命令,命令名称
     * 与按钮图片的名称一样
     * @param e
     */
    public void actionPerformed(ActionEvent actionevent)
    {
        refresh();
        if (!closeContentPanel())
        {
            return;
        }
        String name = actionevent.getActionCommand();
        if (actionevent.getActionCommand().equals("public_createObj"))
        {
            mainController.processCreateObjectCommond();
        }
        else if (actionevent.getActionCommand().equals("public_updateObj"))
        {
            mainController.processUpdateCommand();
        }
        else if (actionevent.getActionCommand().equals("public_deleteObj"))
        {
            mainController.processDeleteCommand();
        }
        else if (actionevent.getActionCommand().equals("public_viewObj"))
        {
            mainController.processViewCommand();
        }
        else if (actionevent.getActionCommand().equals("public_checkInObj"))
        {
            mainController.processCheckInCommand();
        }
        else if (actionevent.getActionCommand().equals("public_checkOutObj"))
        {
            mainController.processCheckOutCommand();
        }
        else if (actionevent.getActionCommand().equals("public_repealObj"))
        {
            mainController.processCancelCheckOutCommand();
        }
        else if (actionevent.getActionCommand().equals("public_search"))
        {
            mainController.processSearchTechnicsCommand();
        }
        //Begin CR3
        else if (actionevent.getActionCommand().equals("public_clear"))
        {
        	mainController.moveOutQMTechnics();
        }
        //End CR3
        else if (actionevent.getActionCommand().equals("term")) //术语
        {
            //processCommitCommand();
        }
        else if (actionevent.getActionCommand().equals("sign")) //符号
        {
            //processSearchCommand();
        }
        else if (actionevent.getActionCommand().equals("public_copy"))
        {
            mainController.processCopyCommand();
        }
        else if (actionevent.getActionCommand().equals("public_paste"))
        {
            mainController.processPasteCommand();
        }

    }


    /**
     * 更新界面(模型发生变化时通知界面，该函数被调用)
     * 设置界面已录入的数据
     * @param observable 被观察者对象
     * @param obj 被观察对象发来的参数
     */
    public void update(Observable observable, Object obj)
    {
    }


    /**
     * 界面中所有的菜单操作在此注册
     * <p>根据当前所选择的菜单和业务对象，确定界面的显示内容</p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: 一汽启明</p>
     * @author 刘明
     * @version 1.0
     */
    class RMAction implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            //try
            //{

            Object object = e.getSource();
            TechnicsRegulationsMainJFrame.this.refresh();
            if (!TechnicsRegulationsMainJFrame.this.closeContentPanel())
            {
                return;
            }

            if (object == jMenuCreateTechnics || object == jMenuCreateTechnics1) //新建工艺主信息
            {
                mainController.processCreateTechnicsCommand();
            }
            else if (object == jMenuCreateStep || object == jMenuCreateStep1) //新建工序
            {
                mainController.processCreateProcedureStepCommand();
            }
            else if (object == jMenuCreatePace || object == jMenuCreatePace1) //新建工步
            {
                mainController.processCreateProcedurePaceCommand();
            }
            else if (object == jMenuFileCollect || object == jMenuFileCollect1) //合并
            {
                mainController.processCollectTechnicsCommand();
            }
//            CCBegin SS8
            else if (object == jMenuCTFileCollect || object == jMenuCTFileCollect1) //合并
            {
                mainController.processCTCollectTechnicsCommand();
            }
//            CCEnd SS8
            /* else if ( object == jMenuFileImport )                             //导入
             {
               mainController.processImportCommond();
             }*/
            else if (object == jMenuFileExport) //导出
            {
                mainController.processExportCommond();
            }
            else if (object == jMenuExportAll) //导出全部工艺
            {
                mainController.processExportAllCommond();
            }
            else if (object == jMenuFileRefresh) //刷新
            {
                mainController.processRefreshCommand();
            }

            else if (object == jMenuSelectView || object == jMenuSelectView1) //查看
            {
                mainController.processViewCommand();
            }
            else if (object == jMenuSelectUpdate ||
                     object == jMenuSelectUpdate1) //更新
            {
                mainController.processUpdateCommand();
            }
            else if (object == jMenuSelectDelete ||
                     object == jMenuSelectDelete1) //删除
            {
                mainController.processDeleteCommand();
            }
            //Begin CR3
            else if (object == jMenuSelectMoveOut ||
                    object == jMenuSelectMoveOut1) //移除
	        {
	            mainController.moveOutQMTechnics();
	        }//End CR3
            else if (object == jMenuSelectFormStepNum) //重新生成工序号
            {
                mainController.processFormNewStepnumCommand();
            }
            //CR1 begin
			else if (object == jMenuSelectFormPaceNum) // 重新生成工步号
			{
				mainController.processFormNewPacenumCommand();
			}
            //CR1 end  
            else if (object == jMenuSelectBrowse ||
                     object == jMenuSelectBrowse1) //打印预览
            {
                mainController.processPrintBrowseCommand();
            }
            else if (object == jMenuSelectChangeLocation) //更改存储位置
            {
                mainController.processChangeLocationCommand();
            }
            else if (object == jMenuSelectSaveAs ||
                     object == jMenuSelectSaveAs1) //另存为
            {
                mainController.processSaveAsCommand();
            }
            else if (object == jMenuSelectRename) //重命名
            {
                mainController.processRenameCommand();
            }
            else if (object == jMenuSelectUseTech) //应用典型工艺
            {
                mainController.processUsageModelTechnicsCommand();
            }
            else if (object == jMenuSelectUseStep) //应用典型工序
            {
                mainController.processUsageModelProcedureCommand();
            }
            else if (object == jMenuSelectMadeTech) //生成典型工艺
            {
                mainController.processBuildModelTechnicsCommand();
            }
            else if (object == jMenuSelectMadeStep) //生成典型工序
            {
                mainController.processBuildModelProcedureCommand();
            }
            else if (object == jMenuSelectCopy || object == jMenuSelectCopy1) //复制
            {
                mainController.processCopyCommand();
            }
            else if (object == jMenuSelectPaste || object == jMenuSelectPaste1) //粘贴
            {
                mainController.processPasteCommand();
            }

            else if (object == jMenuVersionCheckIn) //检入
            {
                mainController.processCheckInCommand();
            }
            else if (object == jMenuVersionCheckOut) //检出
            {
                mainController.processCheckOutCommand();
            }
            else if (object == jMenuVersionCancel) //撤销检出
            {
                mainController.processCancelCheckOutCommand();
            }
            else if (object == jMenuVersionRevise) //修订
            {
                mainController.processReviseCommand();
            }
            else if (object == jMenuVersionVersion) //查看版本历史
            {
                mainController.processViewVersionHistoryCommand();
            }
            else if (object == jMenuVersionIteration) //查看版序历史
            {
                mainController.processViewIterationHistoryCommand();
            }
            else if (object == jMenuSetLifeCycleState) //重新指定生命周期状态
            {
                mainController.processSetLifeCycleStateCommand();
            }
            else if (object == jMenuLifeCycleSelect) //重新指定生命周期
            {
                mainController.processAfreshAppointLifecycleCommand();
            }
            else if (object == jMenuLifeCycleView) //查看生命周期历史
            {
                mainController.processViewLifecycleHistoryCommand();
            }
            else if (object == jMenuLifeCycleGroup) //重新指定工作组
            {
                mainController.processAfreshAppointProjectCommand();
            }

            else if (object == jMenuIntellectPart) //零件族工艺派生
            {
                mainController.processPartCappDeriveCommand();
            }
            else if (object == jMenuIntellectRoot) //加工路线工艺派生
            {
                mainController.processMachineCappDeriveCommand();
            }
            else if (object == jMenuConfigCrit) //配置规范
            {
                mainController.processConfigureCriterionCommand();
            }
            else if (object == jMenuSearchEquip) //按设备搜索工艺
            {
                mainController.processSearchInEquipmentCommand();
            }
            else if (object == jMenuSearchTool) //按工装搜索工艺
            {
                mainController.processSearchInToolsCommand();
            }
            else if (object == jMenuSearchMaterial) //按材料搜索工艺
            {
                mainController.processSearchInMaterialCommand();
            }
            else if (object == jMenuSearchTechnics) //搜索工艺规程
            {
                mainController.processSearchTechnicsCommand();
            }
            //CCBegin SS11
            
            else if (object == jMenuStructSearchTechnics) { //搜索工艺规程
                mainController.processStructSearchTechnicsCommand();
              }
            //CCEnd SS11

            else if (object == jMenuHelpManage) //工艺规程管理
            {
                mainController.processHelpManagerCommand();
            }
            //CCBegin SS1
            else if (object == jMItemjiaju) 
            {
                mainController.jMItemjiaju();
            }
            else if (object == jMItemwanneng) 
            {
                mainController.jMItemwanneng();
            }
            //CCBegin SS5
            else if (object == jMItemwannengliangju) 
            {
                mainController.jMItemwannengliangju();
            }
            else if (object == jMItemgongweiqiju) 
            {
                mainController.jMItemgongweiqiju();
            }
            //CCEnd SS5
            else if (object == jMItemequip) 
            {
                mainController.jMItemequip();
            }
            else if (object == jMItemmoju) 
            {
                mainController.jMItemmoju();
            }
            else if (object == jMItemdaoju) 
            {
                mainController.jMItemdaoju();
            }
            else if (object == jMItemjiafuju) 
            {
                mainController.jMItemjiafuju();
            }
            else if (object == jMItemzhuangpei) 
            {
                mainController.jMItemzhuangpei();
            }
           //CCEnd SS1
          //CCBegin SS4
            else if (object == jMItemzcjiaju) 
            {
                mainController.jMItemzcjiaju();
            }
            else if (object == jMItemzcwangong) 
            {
                mainController.jMItemzcwangong();
            }
            else if (object == jMItemzcwanliang) 
            {
                mainController.jMItemzcwanliang();
            }
            else if (object == jMItemzcjianju) 
            {
                mainController.jMItemzcjianju();
            }
            else if (object == jMItemzcsb) 
            {
                mainController.jMItemzcsb();
            }
            else if (object == jMItemzcmoju) 
            {
                mainController.jMItemzcmoju();
            }
            else if (object == jMItemzcdaoju) 
            {
                mainController.jMItemzcdaoju();
            }
            //            CCBegin SS6
            else if (object == jMItemzcdaofuju) 
            {
                mainController.jMItemzcdaofuju();
            }
//            CCEnd SS6
            else if (object == jMItemzcjiafuju) 
            {
                mainController.jMItemzcjiafuju();
            }
            else if (object == jMItemzcliangju) 
            {
                mainController.jMItemzcliangju();
            }
            else if (object == jMItemzcjianfuju) 
            {
                mainController.jMItemzcjianfuju();
            }
           //CCEnd SS4
            //CCBegin SS7
            else if (object == jMItemCtTool) 
            {
                mainController.jMItemCtTool();
            }
            else if (object == jMItemCtSheBei) 
            {
                mainController.jMItemCtSheBei();
            }
            else if (object == jMItemCtMoJu) 
            {
                mainController.jMItemCtMoJu();
            }
            
            //CCEnd SS7
            //CCBegin SS10
            else if(object==jMenuCheckAll){
                mainController.processCheckAllCommond();
              }
            
            //CCEnd SS10
            
            //CCBegin SS12
            else if(object==jMenuFindMainStep){
                mainController.processFindMainStepCommond();
              }
            //CCEnd SS12
            //CCBegin SS15
            else if(object == jMenuSelectExport)
            {
                    mainController.exportQMProcedure();
            }
            //CCEnd SS15
        }
    }


    class RMWindow extends java.awt.event.WindowAdapter
    {
        public void windowClosing(java.awt.event.WindowEvent event)
        {
            Object object = event.getSource();
            if (object == TechnicsRegulationsMainJFrame.this)
            {
                jMenuFileExit_actionPerformed();
            }
        }
    }


    /**
     * 获得从树上所选择的节点。
     * @return 所选择的节点(工艺卡、工序、工步、标签节点)
     */
    public CappTreeNode getSelectedNode()
    {
        return processTreePanel.getSelectedNode();
    }


    /**
     * 获得从树上所选择的所有节点。
     * @return 所选择的节点(工艺卡、工序、工步、标签节点)
     * 问题（2）20081218 徐春英修改       修改原因：在工艺规程肥客户的工艺树上按住Ctrl用鼠标选择2个或更多的工序，
     * 鼠标右键选择复制，然后粘贴到其他工艺下，这时只能粘贴所选多个工序中的一个（第一个选择的那个）
     */
    public CappTreeNode[] getSelectedNodes()
    {
        return processTreePanel.getSelectedNodes();
    }
    
    
    /**
     * 获得从树上所选择的工序（或工步）节点的（父）工艺卡节点
     * @return （父）工艺卡节点
     */
    public CappTreeNode getParentTechnicsNode()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getParentTechnicsNode() begin...");
        }
        CappTreeObject treeObj = getSelectedNode().getObject();
        if (treeObj instanceof StepTreeObject ||
            treeObj instanceof OperationTreeObject)
        {
            CappTreeNode parent = (CappTreeNode) getSelectedNode().
                                  getParent();
            CappTreeObject obj = parent.getObject();
            while (!(obj instanceof TechnicsTreeObject))
            {
                parent = (CappTreeNode) parent.getParent();
                obj = parent.getObject();
            }
            if (verbose)
            {
                System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getParentTechnicsNode() end...return: " +
                                   parent);

            }
            return (CappTreeNode) parent;
        }
        else if (treeObj instanceof TechnicsTreeObject)
        {
            return getSelectedNode();
        }
        else
        {
            return null;
        }

    }


    /**
     * 获得指定节点的（父）工艺卡节点
     * @param currentNode 指定节点
     * @return （父）工艺卡节点
     */
    public CappTreeNode getParentTechnicsNode(CappTreeNode currentNode)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getParentTechnicsNode() begin...");
        }
        CappTreeObject treeObj = currentNode.getObject();
        if (treeObj instanceof StepTreeObject ||
            treeObj instanceof OperationTreeObject)
        {
            CappTreeNode parent = (CappTreeNode) currentNode.getParent();
            CappTreeObject obj = parent.getObject();
            while (!(obj instanceof TechnicsTreeObject))
            {
                parent = (CappTreeNode) parent.getParent();
                obj = parent.getObject();
            }
            if (verbose)
            {
                System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getParentTechnicsNode() end...return: " +
                                   parent);

            }
            return (CappTreeNode) parent;
        }
        else if (treeObj instanceof TechnicsTreeObject)
        {
            return currentNode;
        }
        else
        {
            return null;
        }

    }


    /**
     * 重新到树上去获得选中对象,将获得的对象付给existNode
     * @return 所选择的业务对象(工艺卡、工序、工步、标签节点)
     */
    public CappTreeObject getSelectedObject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getSelectedObject() begin...");
        }
        CappTreeObject cappTreeObject = null;
        CappTreeNode node = null;
        //节点
        node = processTreePanel.getSelectedNode();

        if (node != null)
        {
            existNode = node;
            cappTreeObject = node.getObject();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getSelectedObject() end...return: " +
                               cappTreeObject);
        }
        return cappTreeObject;
    }


    /**
     * 设置界面模式（工艺卡、工序或工步）
     * @param optionMode 由菜单决定的界面显示状态（只用于查看、另存为状态；新建和更新状态另有处理）
     */
    public void setViewMode(int optionMode, CappTreeObject obj)
    {
    	System.out.println("naubhFrane setViewMode====="+optionMode);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRegulationsMainJFrame.setViewMode() begin...");
        }
        if ((obj != null)
            && (obj instanceof BusinessTreeObject))
        {

            contentJPanel.setSelectedObject((BaseValueInfo) obj.getObject());
            contentJPanel.setSelectedNode(processTreePanel.findNode(obj));
            if (obj instanceof TechnicsTreeObject)
            {
            	//CCBegin SS13
            	String type=((QMFawTechnicsInfo)((TechnicsTreeObject)obj).getObject()).getTechnicsType().getCodeContent();

            	//CCBegin SS14
            	if(type.equals("成都检查作业指导书工艺")){
            		//CCEnd SS14
            		contentJPanel.setTechnicsModeForCd(optionMode);
            		
            	}else{
            		
            		contentJPanel.setTechnicsMode(optionMode);
            	}
               // contentJPanel.setTechnicsMode(optionMode);
            	
            	 //CCEnd  SS13
            }
            else if (obj instanceof StepTreeObject)
            {
                contentJPanel.setStepMode(optionMode);
            }
            else if (obj instanceof OperationTreeObject)
            {
                contentJPanel.setPaceMode(optionMode);
            }
            refresh();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.setViewMode() end...return is void");
        }
    }


    /**
     * 设置显示生成典型工艺（或典型工序）界面
     */
    public void setModelTechnicsViewMode()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.setModelTechnicsViewMode() begin...");
        }
        if ((getSelectedObject() != null)
            && (getSelectedObject() instanceof BusinessTreeObject))
        {
            BusinessTreeObject selectedObject = (BusinessTreeObject)
                                                getSelectedObject();
            contentJPanel.setSelectedObject((BaseValueInfo) selectedObject.
                                            getObject());
            if (selectedObject instanceof TechnicsTreeObject)
            {
                contentJPanel.setModelTechMode();
            }
            else if (selectedObject instanceof StepTreeObject)
            {
                contentJPanel.setModelStepMode();
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.setModelTechnicsViewMode() end...return is void");
        }
    }


    /**
     * 获得内容面板(TechnicsContentJPanel)
     * @return TechnicsContentJPanel
     */
    public TechnicsContentJPanel getContentJPanel()
    {
        return contentJPanel;
    }


    /**
     * 获得工艺树显示面板
     * @return  ProcessTreePanel
     */
    public TechnicsTreePanel getProcessTreePanel()
    {
        return processTreePanel;
    }


    /**
     * 刷新主界面
     */
    public void refresh()
    {
        validate();
        doLayout();
        repaint();
    }


    /**
     * 关闭工艺内容面板
     * 此方法用于每次进行某一菜单操作时更换界面
     * @return 更换界面之前是否执行了保存操作。如果执行了保存，则返回真。
     */
    public boolean closeContentPanel()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.closeContentPanel() begin...");

        }
        boolean flag = contentJPanel.setNullMode();
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.closeContentPanel() end...return: " +
                               flag);
        }
        return flag;

    }
    
    
    //问题（3）20081226 xucy  修改原因：优化更新时保存工序 begin
    public void  setStepJpanel(boolean flag)
    {
    	contentJPanel.setStepJpanel(flag);
    }
    public void  setPaceJpanel(boolean flag)
    {
    	contentJPanel.setPaceJpanel(flag);
    }
    //问题（3）20081226 xucy  修改原因：优化更新时保存工序 end
    
    
    public void setMenuItemEnable(boolean flag, String menuname)
    {
        qmToolBar.setEnabled(menuname, flag);
    }


    /**
     * 根据所取对象的类型确定菜单项的有效性
     * @param flag是否在个人在个人资料夹
     * @param obj
     */
    public void enableMenuItems(CappTreeObject obj, boolean flag)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRegulationsMainJFrame.enableMenuItems() begin...");
            //在管理器中没有选择对象时菜单的有效性
        }
        if (obj == null)
        {
            setNullMenu();
        }
        //在管理器中选择对象为工艺卡时菜单的有效性
        else if (obj instanceof TechnicsTreeObject)
        {
            setTechnicsMenu((TechnicsTreeObject) obj, flag);
        }
        //在管理器中选择对象为工序时菜单的有效性
        else if (obj instanceof StepTreeObject)
        {
            setProcedureStepMenu((StepTreeObject) obj, flag);
        }
        //在管理器中选择对象为工步时菜单的有效性
        else if (obj instanceof OperationTreeObject)
        {
            setProcedurePaceMenu((OperationTreeObject) obj, flag);
        }
        else
        {
            setNullMenu();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.enableMenuItems() end...return is void");
        }
    }


    /* private void setjMenuFileCollectEnabled(boolean flag)
     {
       jMenuFileCollect.setEnabled(flag);
       jMenuFileCollect1.setEnabled(flag);
     }*/

    /**
     * 设置"检入,检出,撤消检出,修订"菜单项和qmToolBar的有效性
     * @param   checkOutflag 控制"检出"和"修订"的有效性.
     * @param   otherFlag 控制"检入"和"撤消检出"菜单项的有效性
     * @author 郭晓亮  CR4
     */
        public void setMenuAndToolBar(boolean checkOutflag,boolean otherFlag){
        	jMenuVersionCheckIn.setEnabled(otherFlag);
            jMenuVersionCheckOut.setEnabled(checkOutflag);
            jMenuVersionCancel.setEnabled(otherFlag);
            jMenuVersionRevise.setEnabled(checkOutflag);
            
            qmToolBar.setEnabled("public_checkInObj", otherFlag);
            qmToolBar.setEnabled("public_checkOutObj", checkOutflag);
            qmToolBar.setEnabled("public_repealObj", otherFlag);
        }
    /**
     * 设置在管理器中没有选择对象时菜单的有效性
     */
    private void setNullMenu()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRegulationsMainJFrame.setNullMenu() begin...");
        }
        jMenuCreateTechnics.setEnabled(true);
        jMenuFileExit.setEnabled(true);
        jMenuFileCollect.setEnabled(true);
//        CCBegin SS8
        jMenuCTFileCollect.setEnabled(true);
//        CCEnd SS8
        // jMenuFileImport.setEnabled(false);
        jMenuFileExport.setEnabled(false);
        jMenuFileRefresh.setEnabled(false);
        jMenuCreateStep.setEnabled(false);
        jMenuCreatePace.setEnabled(false);
        jMenuSelectView.setEnabled(false);
        jMenuSelectUpdate.setEnabled(false);
        jMenuSelectFormStepNum.setEnabled(false);
        //CR1 begin
        jMenuSelectFormPaceNum.setEnabled(false);
        //CR1 end
        jMenuSelectBrowse.setEnabled(false);
        jMenuSelectChangeLocation.setEnabled(false);
        jMenuSelectRename.setEnabled(false);
        jMenuSelectSaveAs.setEnabled(false);
        jMenuSelectUseTech.setEnabled(false);
        jMenuSelectUseStep.setEnabled(false);
        jMenuSelectMadeTech.setEnabled(false);
        jMenuSelectMadeStep.setEnabled(false);
        jMenuSelectCopy.setEnabled(false);
        jMenuSelectPaste.setEnabled(false);
        jMenuSelectDelete.setEnabled(false);
        jMenuVersionCheckIn.setEnabled(false);
        jMenuVersionCheckOut.setEnabled(false);
        jMenuVersionCancel.setEnabled(false);
        jMenuVersionRevise.setEnabled(false);
        jMenuVersionVersion.setEnabled(false);
        jMenuVersionIteration.setEnabled(false);
        jMenuSetLifeCycleState.setEnabled(false);
        jMenuLifeCycleSelect.setEnabled(false);
        jMenuLifeCycleView.setEnabled(false);
        jMenuLifeCycleGroup.setEnabled(false);
        jMenuIntellectPart.setEnabled(false);
        jMenuIntellectRoot.setEnabled(false);
        jMenuSearchEquip.setEnabled(true);
        jMenuSearchTool.setEnabled(true);
        jMenuSearchMaterial.setEnabled(true);
        jMenuSearchTechnics.setEnabled(true);
        //CCBegin SS10
        jMenuCheckAll.setEnabled(false);
        //CCEnd SS10
        //CCBegin SS11
        jMenuStructSearchTechnics.setEnabled(true);
        //CCEnd SS11
        
        //CCBegi SS12
        jMenuFindMainStep.setEnabled(true);
        //CCEnd SS12
        
        //CCBegin SS15
        jMenuSelectExport.setEnabled(true);
        //CCEnd SS15
        
        
        //CCBegin SS1
        try {
        	if(isBsxGroupUser()){
        		jMItemjiaju.setEnabled(false);
        		jMItemwanneng.setEnabled(false);
        		//CCBegin SS5
        		jMItemwannengliangju.setEnabled(false);
        		jMItemgongweiqiju.setEnabled(false);
        		//CCEnd SS5
        		jMItemequip.setEnabled(false);
        		jMItemmoju.setEnabled(false);
        		jMItemdaoju.setEnabled(false);
        		jMItemjiafuju.setEnabled(false);
        		jMItemzhuangpei.setEnabled(false);
        	}
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //CCEnd SS1
        //CCBegin SS4
        try {
			if(getUserFromCompany().equals("zczx")){
				jMItemzcjiaju.setEnabled(false);
        		jMItemzcwangong.setEnabled(false);
        		jMItemzcwanliang.setEnabled(false);
        		jMItemzcjianju.setEnabled(false);
        		jMItemzcsb.setEnabled(false);
        		jMItemzcmoju.setEnabled(false);
        		jMItemzcdaoju.setEnabled(false);
        		//        		CCBegin SS6
        		jMItemzcdaofuju.setEnabled(false);
//        		CCEnd SS6
        		jMItemzcjiafuju.setEnabled(false);
        		jMItemzcliangju.setEnabled(false);
        		jMItemzcjianfuju.setEnabled(false);
			}
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //CCEnd SS4
        jMenuCreateTechnics1.setEnabled(true);
        jMenuCreateStep1.setEnabled(false);
        jMenuCreatePace1.setEnabled(false);
        jMenuFileCollect1.setEnabled(true);
//        CCBegin SS8
        jMenuCTFileCollect1.setEnabled(true);
//        CCEnd SS8
        jMenuSelectView1.setEnabled(false);
        jMenuSelectUpdate1.setEnabled(false);
        jMenuSelectCopy1.setEnabled(false);
        jMenuSelectPaste1.setEnabled(false);
        jMenuSelectDelete1.setEnabled(false);
        jMenuSelectSaveAs1.setEnabled(false);
        jMenuSelectBrowse1.setEnabled(false);
        jMenuAddObject.setEnabled(false);
        techAction.setEnabled(false);
        qmToolBar.setEnabled("public_updateObj", false);
        qmToolBar.setEnabled("public_deleteObj", false);
        qmToolBar.setEnabled("public_checkInObj", false);
        qmToolBar.setEnabled("public_checkOutObj", false);
        qmToolBar.setEnabled("public_repealObj", false);
        qmToolBar.setEnabled("public_paste", false);
        qmToolBar.setEnabled("public_createObj", true);
        qmToolBar.setEnabled("public_viewObj", false);
        qmToolBar.setEnabled("public_copy", false);
        qmToolBar.setEnabled("public_search", true);
        //Begin CR3
        jMenuSelectMoveOut.setEnabled(false);
        qmToolBar.setEnabled("public_clear", false);
        jMenuSelectMoveOut1.setEnabled(false);
        //End CR3
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.setNullMenu() end...return is void");
        }
    }


    /**
     * 设置在管理器中选择对象为工艺卡时菜单的有效性
     * @param obj TechnicsTreeObject 工艺树对象
     * @param flag 是否在个人在个人资料夹
     */
    private void setTechnicsMenu(TechnicsTreeObject obj, boolean flag)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRegulationsMainJFrame.setTechnicsMenu() begin...");

        }
        //Begin CR3
        jMenuSelectMoveOut.setEnabled(true);
        qmToolBar.setEnabled("public_clear", true);
        jMenuSelectMoveOut1.setEnabled(true);
        //End CR3
        
        
        //CCBegin SS10
        
        jMenuCheckAll.setEnabled(true);
        //CCEnd SS10
        
        //CCbegin SS12
        jMenuFindMainStep.setEnabled(true);
        //CCEnd SS12
        
        //CCBegin SS11
        jMenuStructSearchTechnics.setEnabled(true);
        //CCEnd SS11
        
        String state = ((WorkableIfc) obj.getObject()).getWorkableState();

        if (state.equals("c/o"))
        {
            jMenuCreateStep.setEnabled(false);
            jMenuSelectFormStepNum.setEnabled(false);
            //CR1 begin
            jMenuSelectFormPaceNum.setEnabled(false);
            //CR1 end
            jMenuSelectUseTech.setEnabled(false);
            jMenuSelectUseStep.setEnabled(false);
            jMenuSelectPaste.setEnabled(false);
            jMenuSelectDelete.setEnabled(false);
            jMenuSelectUpdate.setEnabled(false);
            jMenuSelectChangeLocation.setEnabled(false);
            jMenuSelectRename.setEnabled(false);
            jMenuVersionCheckIn.setEnabled(false);
            jMenuVersionCheckOut.setEnabled(false);
            jMenuVersionCancel.setEnabled(false);
            jMenuVersionRevise.setEnabled(false);
            jMenuLifeCycleSelect.setEnabled(false);
            jMenuAddObject.setEnabled(false);
            jMenuSelectSaveAs.setEnabled(false);
            jMenuCreateStep1.setEnabled(false);
            jMenuSelectUpdate1.setEnabled(false);
            jMenuSelectPaste1.setEnabled(false);
            jMenuSelectDelete1.setEnabled(false);
            techAction.setEnabled(false);
            jMenuSelectSaveAs1.setEnabled(false);
            jMenuSelectCopy.setEnabled(false);
            jMenuSelectCopy1.setEnabled(false);
        //CCBegin SS15
        jMenuSelectExport.setEnabled(true);
        //CCEnd SS15
            qmToolBar.setEnabled("public_updateObj", false);
            qmToolBar.setEnabled("public_deleteObj", false);
            qmToolBar.setEnabled("public_checkInObj", false);
            qmToolBar.setEnabled("public_checkOutObj", false);
            qmToolBar.setEnabled("public_repealObj", false);
            qmToolBar.setEnabled("public_paste", false);
            qmToolBar.setEnabled("public_copy", false);

        }
        else
        {
            if (state.equals("wrk"))
            {
                jMenuLifeCycleSelect.setEnabled(false);
                jMenuSelectChangeLocation.setEnabled(false);
                jMenuVersionCancel.setEnabled(true);
                qmToolBar.setEnabled("public_repealObj", true);
                jMenuVersionCheckIn.setEnabled(true);
                qmToolBar.setEnabled("public_checkInObj", true);
                jMenuVersionCheckOut.setEnabled(false);
                qmToolBar.setEnabled("public_checkOutObj", false);
                jMenuVersionRevise.setEnabled(false);
                jMenuSelectDelete.setEnabled(true);
                jMenuSelectDelete1.setEnabled(true);
                qmToolBar.setEnabled("public_deleteObj", true);
                jMenuSetLifeCycleState.setEnabled(false);

            }
            //c/i状态
            else
            {
                jMenuLifeCycleSelect.setEnabled(true);
                jMenuVersionCancel.setEnabled(false);
                qmToolBar.setEnabled("public_repealObj", false);
                //在公共资料夹
                if (!flag)
                {
                    jMenuSelectChangeLocation.setEnabled(true);
                    jMenuVersionCheckIn.setEnabled(false);
                    qmToolBar.setEnabled("public_checkInObj", false);
                    jMenuVersionCheckOut.setEnabled(true);
                    qmToolBar.setEnabled("public_checkOutObj", true);
                    jMenuVersionRevise.setEnabled(true);
                    jMenuSetLifeCycleState.setEnabled(true);
                    //需判断权限的:
                    jMenuSelectDelete.setEnabled(true);
                    jMenuSelectDelete1.setEnabled(true);
                    qmToolBar.setEnabled("public_deleteObj", true);

                }
                //在个人资料夹
                else
                {
                    jMenuSelectChangeLocation.setEnabled(false);
                    jMenuVersionCheckIn.setEnabled(true);
                    qmToolBar.setEnabled("public_checkInObj", true);
                    jMenuVersionCheckOut.setEnabled(false);
                    qmToolBar.setEnabled("public_checkOutObj", false);
                    jMenuVersionRevise.setEnabled(false);
                    jMenuSelectDelete.setEnabled(true);
                    jMenuSelectDelete1.setEnabled(true);
                    qmToolBar.setEnabled("public_deleteObj", true);
                    jMenuSetLifeCycleState.setEnabled(false);
                }
            }
            jMenuCreateStep.setEnabled(true);
            jMenuCreateStep1.setEnabled(true);
            jMenuSelectFormStepNum.setEnabled(true);
            //CR1 begin
            jMenuSelectFormPaceNum.setEnabled(false);
            //CR1 end
            jMenuSelectUseTech.setEnabled(true);
            jMenuSelectUseStep.setEnabled(true);
            jMenuSelectPaste.setEnabled(true);
            jMenuSelectPaste1.setEnabled(true);
            jMenuSelectSaveAs.setEnabled(true);
            jMenuSelectSaveAs1.setEnabled(true);
            qmToolBar.setEnabled("public_paste", true);
            jMenuSelectRename.setEnabled(true);
            jMenuAddObject.setEnabled(true);
            techAction.setEnabled(true);
            jMenuSelectUpdate.setEnabled(true);
            jMenuSelectUpdate1.setEnabled(true);
            qmToolBar.setEnabled("public_updateObj", true);
            jMenuSelectCopy.setEnabled(true);
            jMenuSelectCopy1.setEnabled(true);
            qmToolBar.setEnabled("public_copy", true);
        }
        jMenuCreateTechnics.setEnabled(true);

        jMenuFileExit.setEnabled(true);
        jMenuFileCollect.setEnabled(true);
//      CCBegin SS8
         jMenuCTFileCollect.setEnabled(true);
//      CCEnd SS8
        // jMenuFileImport.setEnabled(false);//lin
        jMenuFileExport.setEnabled(true);
        jMenuFileRefresh.setEnabled(true);

        jMenuCreatePace.setEnabled(false);
        jMenuSelectView.setEnabled(true);
        jMenuSelectBrowse.setEnabled(true); //lin
        jMenuSelectMadeTech.setEnabled(true);
        jMenuSelectMadeStep.setEnabled(false);

        jMenuVersionVersion.setEnabled(true);
        jMenuVersionIteration.setEnabled(true);
        //jMenuSetLifeCycleState.setEnabled(true);
        //CCBegin SS1
        try {
			if(isBsxGroupUser()){
				jMItemjiaju.setEnabled(true);
				jMItemwanneng.setEnabled(true);
        //CCBegin SS5
        jMItemwannengliangju.setEnabled(true);
        jMItemgongweiqiju.setEnabled(true);
        //CCEnd SS5
				jMItemequip.setEnabled(true);
				jMItemmoju.setEnabled(true);
				jMItemdaoju.setEnabled(true);
				jMItemjiafuju.setEnabled(true);
				jMItemzhuangpei.setEnabled(true);
			}
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //CCEnd SS1
      //CCBegin SS4
        try {
			if(getUserFromCompany().equals("zczx")){
				jMItemzcjiaju.setEnabled(true);
        		jMItemzcwangong.setEnabled(true);
        		jMItemzcwanliang.setEnabled(true);
        		jMItemzcjianju.setEnabled(true);
        		jMItemzcsb.setEnabled(true);
        		jMItemzcmoju.setEnabled(true);
        		jMItemzcdaoju.setEnabled(true);
        		//        		CCBegin SS6
        		jMItemzcdaofuju.setEnabled(true);
//        		CCEnd SS6
        		jMItemzcjiafuju.setEnabled(true);
        		jMItemzcliangju.setEnabled(true);
        		jMItemzcjianfuju.setEnabled(true);
			}
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //CCEnd SS4
        jMenuLifeCycleView.setEnabled(true);
        jMenuLifeCycleGroup.setEnabled(true);
        jMenuIntellectPart.setEnabled(false);
        jMenuIntellectRoot.setEnabled(false);
        jMenuSearchEquip.setEnabled(true); //lin
        jMenuSearchTool.setEnabled(true); //lin
        jMenuSearchMaterial.setEnabled(true); //lin
        jMenuSearchTechnics.setEnabled(true);

        jMenuCreateTechnics1.setEnabled(true);
        jMenuCreatePace1.setEnabled(false);
        jMenuFileCollect1.setEnabled(true);
//      CCBegin SS8
        jMenuCTFileCollect1.setEnabled(true);
//      CCEnd SS8
//      CCBegin SS8
      jMenuCTFileCollect1.setEnabled(true);
//      CCEnd SS8
        jMenuSelectView1.setEnabled(true);

        jMenuSelectBrowse1.setEnabled(true);
        qmToolBar.setEnabled("public_createObj", true);
        qmToolBar.setEnabled("public_viewObj", true);
        qmToolBar.setEnabled("public_search", true);

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.setTechnicsMenu() end...return is void");
        }
    }


    /**
     * 设置在管理器中选择对象为工序时菜单的有效性
     */
    private void setProcedureStepMenu(StepTreeObject obj, boolean flag)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.setProcedureStepMenu() begin...");
        }
        //Begin CR3  
        jMenuSelectMoveOut.setEnabled(false);
        qmToolBar.setEnabled("public_clear", false);
        jMenuSelectMoveOut1.setEnabled(false);
        //End CR3
        String state = ((QMProcedureInfo) obj.getObject()).getWorkableState();
        jMenuCreateTechnics.setEnabled(true);
        jMenuFileExit.setEnabled(true);
        jMenuFileCollect.setEnabled(true);
//      CCBegin SS8
        jMenuCTFileCollect.setEnabled(true);
//      CCEnd SS8
        //jMenuFileImport.setEnabled(false);//lin
        jMenuFileExport.setEnabled(false);
        jMenuFileRefresh.setEnabled(true);
        jMenuCreateStep.setEnabled(false);

        jMenuSelectView.setEnabled(true);
        
        
        //CCBegin SS10
        jMenuCheckAll.setEnabled(false);
        
        //CCEnd SS10
        //CCBegin SS12
        jMenuFindMainStep.setEnabled(false);
        //CCEnd SS12
        
        //CCBegin SS11
        jMenuStructSearchTechnics.setEnabled(true);
        //CCEnd SS11
        //非c/o
        if (state.equals("c/o"))
        {
            jMenuSelectUpdate.setEnabled(false);
            jMenuSelectPaste.setEnabled(false);
            jMenuSelectDelete.setEnabled(false);
            jMenuSelectUpdate1.setEnabled(false);
            jMenuSelectPaste1.setEnabled(false);
            jMenuSelectDelete1.setEnabled(false);
            jMenuCreatePace.setEnabled(false);
            jMenuCreatePace1.setEnabled(false);
            qmToolBar.setEnabled("public_updateObj", false);
            qmToolBar.setEnabled("public_deleteObj", false);
            qmToolBar.setEnabled("public_paste", false);

        }
        else
        {
            boolean flag1 = true;
            //不可以建工步的工序
            String proceduresHaveNoPace = RemoteProperty.getProperty(
                    "proceduresHaveNoPace");
            if (proceduresHaveNoPace != null)
            {
                StringTokenizer stringToken = new StringTokenizer(
                        proceduresHaveNoPace,
                        ",");
                while (stringToken.hasMoreTokens())
                {
                    String s = stringToken.nextToken();
                    if (s.equals(((QMProcedureInfo) obj.getObject()).
                                 getTechnicsType().
                                 getCodeContent()))
                    {
                        flag1 = false;
                        break;
                    }
                }
            }
            if (flag1)
            {
                jMenuCreatePace.setEnabled(true);
                jMenuCreatePace1.setEnabled(true);
            }
            else
            {
                jMenuCreatePace.setEnabled(false);
                jMenuCreatePace1.setEnabled(false);
            }

            jMenuSelectUpdate.setEnabled(true);
            jMenuSelectUpdate1.setEnabled(true);
            qmToolBar.setEnabled("public_updateObj", true);
            jMenuSelectPaste.setEnabled(true);
            jMenuSelectPaste1.setEnabled(true);
            qmToolBar.setEnabled("public_paste", true);
            //判断权限
            jMenuSelectDelete.setEnabled(true);
            jMenuSelectDelete1.setEnabled(true);
            qmToolBar.setEnabled("public_deleteObj", true);

        }
        jMenuSelectFormStepNum.setEnabled(false);
        //CR1 begin
        jMenuSelectFormPaceNum.setEnabled(true);
        //CR1 end
        jMenuSelectBrowse.setEnabled(true); //lin
        jMenuSelectChangeLocation.setEnabled(false);
        jMenuSelectRename.setEnabled(false);
        jMenuSelectSaveAs.setEnabled(false);
        jMenuSelectUseTech.setEnabled(false);
        jMenuSelectUseStep.setEnabled(false);
        jMenuSelectMadeTech.setEnabled(false);
        jMenuSelectMadeStep.setEnabled(true);
        jMenuSelectCopy.setEnabled(true);

        jMenuVersionCheckIn.setEnabled(false);
        jMenuVersionCheckOut.setEnabled(false);
        jMenuVersionCancel.setEnabled(false);
        jMenuVersionRevise.setEnabled(false);
        jMenuVersionVersion.setEnabled(false);
        jMenuVersionIteration.setEnabled(true);
        jMenuSetLifeCycleState.setEnabled(false);
        jMenuLifeCycleSelect.setEnabled(false);
        jMenuLifeCycleView.setEnabled(false);
        jMenuLifeCycleGroup.setEnabled(false);
        jMenuIntellectPart.setEnabled(false);
        jMenuIntellectRoot.setEnabled(false);
        jMenuSearchEquip.setEnabled(true); //lin
        jMenuSearchTool.setEnabled(true); //lin
        jMenuSearchMaterial.setEnabled(true); //lin
        jMenuSearchTechnics.setEnabled(true);
        //CCBegin SS1
        try {
			if(isBsxGroupUser()){
				jMItemjiaju.setEnabled(false);
				jMItemwanneng.setEnabled(false);
        //CCBegin SS5
        jMItemwannengliangju.setEnabled(false);
        jMItemgongweiqiju.setEnabled(false);
        //CCEnd SS5
				jMItemequip.setEnabled(false);
				jMItemmoju.setEnabled(false);
				jMItemdaoju.setEnabled(false);
				jMItemjiafuju.setEnabled(false);
				jMItemzhuangpei.setEnabled(false);
			}
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //CCEnd SS1
      //CCBegin SS4
        try {
			if(getUserFromCompany().equals("zczx")){
				jMItemzcjiaju.setEnabled(false);
        		jMItemzcwangong.setEnabled(false);
        		jMItemzcwanliang.setEnabled(false);
        		jMItemzcjianju.setEnabled(false);
        		jMItemzcsb.setEnabled(false);
        		jMItemzcmoju.setEnabled(false);
        		jMItemzcdaoju.setEnabled(false);
        		//        		CCBegin SS6
        		jMItemzcdaofuju.setEnabled(false);
//        		CCEnd SS6
        		jMItemzcjiafuju.setEnabled(false);
        		jMItemzcliangju.setEnabled(false);
        		jMItemzcjianfuju.setEnabled(false);
			}
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //CCEnd SS4
        jMenuCreateTechnics1.setEnabled(true);
        jMenuCreateStep1.setEnabled(false);

        jMenuFileCollect1.setEnabled(true);
//      CCBegin SS8
      jMenuCTFileCollect1.setEnabled(true);
//      CCEnd SS8
        //CCBegin SS15
        jMenuSelectExport.setEnabled(true);
        //CCEnd SS15
        jMenuSelectView1.setEnabled(true);
        jMenuSelectCopy1.setEnabled(true);
        jMenuSelectSaveAs1.setEnabled(false);
        jMenuSelectBrowse1.setEnabled(true);
        jMenuAddObject.setEnabled(false);
        techAction.setEnabled(false);

        qmToolBar.setEnabled("public_checkInObj", false);
        qmToolBar.setEnabled("public_checkOutObj", false);
        qmToolBar.setEnabled("public_repealObj", false);

        qmToolBar.setEnabled("public_createObj", true);
        qmToolBar.setEnabled("public_viewObj", true);
        qmToolBar.setEnabled("public_copy", true);
        qmToolBar.setEnabled("public_search", true);

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.setProcedureStepMenu() end...return is void");
        }
    }


    /**
     * 设置在管理器中选择对象为工步时菜单的有效性
     */
    private void setProcedurePaceMenu(OperationTreeObject obj, boolean flag)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.setProcedurePaceMenu() begin...");
        }
        String state = ((QMProcedureInfo) obj.getObject()).getWorkableState();
        jMenuCreateTechnics.setEnabled(true);
        jMenuFileExit.setEnabled(true);
        jMenuFileCollect.setEnabled(true);
//      CCBegin SS8
        jMenuCTFileCollect.setEnabled(true);
//      CCEnd SS8
        //jMenuFileImport.setEnabled(false);//lin
        jMenuFileExport.setEnabled(false);
        jMenuFileRefresh.setEnabled(true);
        jMenuCreateStep.setEnabled(false);
        //Begin CR3
        jMenuSelectMoveOut.setEnabled(false);
        qmToolBar.setEnabled("public_clear", false);
        jMenuSelectMoveOut1.setEnabled(false);
        //End CR3
        jMenuSelectView.setEnabled(true);
        
        //CCBegin SS10
        jMenuCheckAll.setEnabled(false);
        
        //CCEnd SS10
        
        //CCBegin SS12
        jMenuFindMainStep.setEnabled(false);
        //CCEnd SS12
        
        //CCBegin SS11
        jMenuStructSearchTechnics.setEnabled(true);
        //CCEnd SS11
        
        //非c/o
        if (state.equals("c/o"))
        {
            jMenuSelectUpdate.setEnabled(false);
            jMenuSelectPaste.setEnabled(false);
            jMenuSelectDelete.setEnabled(false);
            jMenuSelectUpdate1.setEnabled(false);
            jMenuSelectPaste1.setEnabled(false);
            jMenuSelectDelete1.setEnabled(false);
            jMenuCreatePace.setEnabled(false);
            jMenuCreatePace1.setEnabled(false);
            qmToolBar.setEnabled("public_updateObj", false);
            qmToolBar.setEnabled("public_deleteObj", false);
            qmToolBar.setEnabled("public_paste", false);

        }
        else
        {
            jMenuCreatePace.setEnabled(true);
            jMenuCreatePace1.setEnabled(true);
            jMenuSelectUpdate.setEnabled(true);
            jMenuSelectUpdate1.setEnabled(true);
            qmToolBar.setEnabled("public_updateObj", true);
            jMenuSelectPaste.setEnabled(true);
            jMenuSelectPaste1.setEnabled(true);
            qmToolBar.setEnabled("public_paste", true);
            //判断权限
            jMenuSelectDelete.setEnabled(true);
            jMenuSelectDelete1.setEnabled(true);
            qmToolBar.setEnabled("public_deleteObj", true);

        }
        jMenuSelectFormStepNum.setEnabled(false);
        //CR1 begin
        jMenuSelectFormPaceNum.setEnabled(true);
        //CR1 end
        jMenuSelectBrowse.setEnabled(false); //lin
        jMenuSelectChangeLocation.setEnabled(false);
        jMenuSelectRename.setEnabled(false);
        jMenuSelectSaveAs.setEnabled(false);
        jMenuSelectUseTech.setEnabled(false);
        jMenuSelectUseStep.setEnabled(false);
        jMenuSelectMadeTech.setEnabled(false);
        jMenuSelectMadeStep.setEnabled(false);
        jMenuSelectCopy.setEnabled(true);

        jMenuVersionCheckIn.setEnabled(false);
        jMenuVersionCheckOut.setEnabled(false);
        jMenuVersionCancel.setEnabled(false);
        jMenuVersionRevise.setEnabled(false);
        jMenuVersionVersion.setEnabled(false);
        jMenuVersionIteration.setEnabled(true);
        jMenuSetLifeCycleState.setEnabled(false);
        jMenuLifeCycleSelect.setEnabled(false);
        jMenuLifeCycleView.setEnabled(false);
        jMenuLifeCycleGroup.setEnabled(false);
        jMenuIntellectPart.setEnabled(false);
        jMenuIntellectRoot.setEnabled(false);
        jMenuSearchEquip.setEnabled(true); //lin
        jMenuSearchTool.setEnabled(true); //lin
        jMenuSearchMaterial.setEnabled(true); //lin
        jMenuSearchTechnics.setEnabled(true);
        //CCBegin SS1
        try {
			if(isBsxGroupUser()){
				jMItemjiaju.setEnabled(false);
				jMItemwanneng.setEnabled(false);
        //CCBegin SS5
        jMItemwannengliangju.setEnabled(false);
        jMItemgongweiqiju.setEnabled(false);
        //CCEnd SS5
				jMItemequip.setEnabled(false);
				jMItemmoju.setEnabled(false);
				jMItemdaoju.setEnabled(false);
				jMItemjiafuju.setEnabled(false);
				jMItemzhuangpei.setEnabled(false);
			}
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //CCEnd SS1
      //CCBegin SS4
        try {
			if(getUserFromCompany().equals("zczx")){
				jMItemzcjiaju.setEnabled(false);
        		jMItemzcwangong.setEnabled(false);
        		jMItemzcwanliang.setEnabled(false);
        		jMItemzcjianju.setEnabled(false);
        		jMItemzcsb.setEnabled(false);
        		jMItemzcmoju.setEnabled(false);
        		jMItemzcdaoju.setEnabled(false);
        		//        		CCBegin SS6
        		jMItemzcdaofuju.setEnabled(false);
//        		CCEnd SS6
        		jMItemzcjiafuju.setEnabled(false);
        		jMItemzcliangju.setEnabled(false);
        		jMItemzcjianfuju.setEnabled(false);
			}
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //CCEnd SS4
        jMenuCreateTechnics1.setEnabled(true);
        jMenuCreateStep1.setEnabled(false);

        jMenuFileCollect1.setEnabled(true);
//      CCBegin SS8
      jMenuCTFileCollect1.setEnabled(true);
//      CCEnd SS8
        //CCBegin SS15
        jMenuSelectExport.setEnabled(true);
        //CCEnd SS15
        jMenuSelectView1.setEnabled(true);
        jMenuSelectCopy1.setEnabled(true);
        jMenuSelectSaveAs1.setEnabled(false);
        jMenuSelectBrowse1.setEnabled(false);
        jMenuAddObject.setEnabled(false);
        techAction.setEnabled(false);

        qmToolBar.setEnabled("public_checkInObj", false);
        qmToolBar.setEnabled("public_checkOutObj", false);
        qmToolBar.setEnabled("public_repealObj", false);

        qmToolBar.setEnabled("public_createObj", true);
        qmToolBar.setEnabled("public_viewObj", true);
        qmToolBar.setEnabled("public_copy", true);
        qmToolBar.setEnabled("public_search", true);

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.setProcedurePaceMenu() end...return is void");

        }
    }


    /**
     * 为Bom管理器提供的方法。把从BOM树选择的工艺卡根据工艺类型挂在工艺树的标签节点下
     * @param info 从BOM树选择的工艺卡值对象
     */
    public void addBomTechnics(QMFawTechnicsInfo info)
    {
        TechnicsTreeObject treeObject = new TechnicsTreeObject(info);
        //把treeObject挂到工艺树上
        getProcessTreePanel().addProcess(treeObject);
    }


    /**
     *初始化所使用的资源绑定信息类
     */
    protected void initResources()
    {
        try
        {
            if (resource == null)
            {
                resource = ResourceBundle.getBundle(RESOURCE,
                        QMCt.getContext().getLocale());

            }
        }
        catch (MissingResourceException mre)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(this,
                                          CappLMRB.MISSING_RESOURCER,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }


    /**
     * 获得资源信息
     * @return
     */
    protected ResourceBundle getPropertiesRB()
    {
        if (resource == null)
        {
            initResources();
        }
        return resource;
    }


    /**
     * 获得按钮信息显示出来,带浮动文字
     * @param as1
     * @param as2
     */
    public void setTools(String as1[], String as2[])
    {
        String myTools[] = as1;
        for (int i = 0; i < myTools.length; i++)
        {
            qmToolBar.addButton(myTools[i], as2[i], this, new MyMouseListener());
        }
        for (int ii = 0; ii < qmToolBar.getComponentCount(); ii++)
        {
            if (qmToolBar.getComponentAtIndex(ii) instanceof JButton)
            {
                JButton jb = (JButton) (qmToolBar.getComponentAtIndex(ii));
                jb.setBorder(BorderFactory.createEtchedBorder());
            }
        }
        //将各个按钮的功能描述放入toolFunction中
        toolFunction.put("public_createObj", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuFileCreate_status", null));
        toolFunction.put("public_updateObj", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuSelectUpdate_status", null));
        toolFunction.put("public_deleteObj", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuSelectDelete_status", null));
        toolFunction.put("public_viewObj", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuSelectView_status", null));
        toolFunction.put("public_checkInObj", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuVersionCheckIn_status", null));
        toolFunction.put("public_checkOutObj", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuVersionCheckOut_status", null));
        toolFunction.put("public_repealObj", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuVersionCancel_status", null));
        toolFunction.put("public_copy", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuSelectCopy_status", null));
        toolFunction.put("public_paste", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuSelectPaste_status", null));
        toolFunction.put("public_search", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuSearchTechnics_status", null));
        //Begin CR3
        toolFunction.put("public_clear", QMMessage.getLocalizedMessage(
                RESOURCE, "jMenuMoveOutTechnics_status", null));
        //End CR3
        refresh();
    }

   
    /**
     * 获得资源文件中的值
     * @param rb ResourceBundle 资源文件
     * @param key String 键
     * @return String[] 数组中每个元素是值中以","分割的每一段
     */
    protected String[] getValueSet(ResourceBundle rb, String key)
    {
        String[] values = null;
        try
        {
            String value = rb.getString(key);
            StringTokenizer st = new StringTokenizer(value, ",");

            int count = st.countTokens();
            values = new String[count];

            for (int i = 0; i < count; i++)
            {
                values[i] = st.nextToken();
            }
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return values;
    }

    public static void main(String[] args)
    {
        try
        {
            System.setProperty("swing.useSystemFontSettings", "0");
            System.setProperty("swing.handleTopLevelPaint", "false");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        CappClientRequestServer server = null;
        if(args.length == 0)
        {
            String id;
            try
            {
                id = RequestServer.getSessionID("localhost", "7001", "Administrator", "Administrator");
                server = new CappClientRequestServer("localhost", "7001", id);
                TechnicsRegulationsMainJFrame f = new
                TechnicsRegulationsMainJFrame();
                f.setVisible(true);
            }catch(QMRemoteException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
        if (args.length == 3)
        {
            try
            {
            	
                CappClientRequestServer partServer = new  
                        CappClientRequestServer(  
                        args[0], args[1], args[2]);
                RequestServerFactory.setRequestServer(partServer);
                TechnicsRegulationsMainJFrame f = new
                                                  TechnicsRegulationsMainJFrame();
                f.setVisible(true);  
            }    
            catch (ArrayIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }  
        if (args.length == 4)
        {
            try
            {
                if (verbose)
                {
                    System.out.println("工艺规程主界面:" + "args[0]=" + args[0] +
                                       " args[1]=" + args[1] + " args[2]=" +
                                       args[2]
                                       + " args[3=]" + args[3]);

                }
                CappClientRequestServer partServer = new
                        CappClientRequestServer(
                        args[0], args[1], args[2]);
                RequestServerFactory.setRequestServer(partServer);
                TechnicsRegulationsMainJFrame f = new
                                                  TechnicsRegulationsMainJFrame();
                f.setVisible(true);
                BaseValueInfo info = CappClientHelper.refresh(args[3]);
                if (info != null && info instanceof QMTechnicsInfo)
                {
                	//CCBegin by leixiao 2010-5-7 v4r3_p015_20100415  TD2253
                //begin CR7
                	if((((WorkableIfc)info).getWorkableState()).equals("c/o"))
                {
                    if(currentUser.getUsersName().equals("Administrator") || 
                    		 currentUser.getBsoID().equals(((QMFawTechnicsInfo)info).getLocker()))//CR11
                    {
                    	info = (BaseValueInfo)CheckInOutCappTaskLogic.getWorkingCopy((WorkableIfc)info);
                    }
                }                
                //end CR7
                //CCEnd by leixiao 2010-5-7 v4r3_p015_20100415  TD2253
                    TechnicsTreeObject treeobj = new TechnicsTreeObject((
                            QMTechnicsInfo) info);
                    Vector vec = new Vector();
                    vec.add(treeobj);
                    f.getProcessTreePanel().addProcesses(vec);
                    f.getProcessTreePanel().setNodeSelected(treeobj);
                }
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }


    /**
     * 添加弹出菜单项
     */
    private void initPopup()
    {
        popup.add(eqAction);
        popup.add(toAction);
        popup.add(maAction);
        popup.addSeparator();
        popup.add(termAction);
        popup.add(partAction);
        popup.addSeparator();
        popup.add(drawAction);
    }


    /**初始化工艺树面板上的右健菜单*/
    private void initTechnicsTreePopup()
    {
        jMenuFileCreate1.add(jMenuCreateTechnics1);
        jMenuFileCreate1.add(jMenuCreateStep1);
        jMenuFileCreate1.add(jMenuCreatePace1);
        technicsTreePopup.add(jMenuFileCreate1);
        technicsTreePopup.add(jMenuSelectView1);
        technicsTreePopup.add(jMenuSelectUpdate1);
        technicsTreePopup.add(jMenuSelectDelete1);
        //Begin CR3
        technicsTreePopup.add(jMenuSelectMoveOut1);
        //End CR3
        technicsTreePopup.add(jMenuSelectSaveAs1);
        technicsTreePopup.add(jMenuSelectBrowse1);
        technicsTreePopup.add(jMenuFileCollect1);
        
        //CCBegin SS10
        technicsTreePopup.add(jMenuCheckAll);
        
        //CCEnd SS10
        
        //CCBegin SS12
        
        technicsTreePopup.add(jMenuFindMainStep);
        //CCEnd SS12
        
        //CCBegin SS15
        technicsTreePopup.add(jMenuSelectExport);
        //CCEnd SS15
        
//        CCBegin SS8
        try{
        if(this.isCTGroupUser())
        	technicsTreePopup.add(jMenuCTFileCollect1);
        }catch(Exception exc){
        	exc.printStackTrace();
        }
//        CCEnd SS8
        technicsTreePopup.add(jMenuSelectCopy1);
        technicsTreePopup.add(jMenuSelectPaste1);
        //technicsTreePopup.add(techAction);
    }

    private CopyAction copyAction = null;
    private PasteAction pasteAction = null;
    private AddAction techAction = new AddAction(getMessage("techAction"));
    private AddAction eqAction = new AddAction(getMessage("eqAction"));
    private AddAction toAction = new AddAction(getMessage("toAction"));
    private AddAction maAction = new AddAction(getMessage("maAction"));
    private AddAction termAction = new AddAction(getMessage("termAction"));
    private AddAction drawAction = new AddAction(getMessage("drawAction"));
    private AddAction partAction = new AddAction(getMessage("partAction"));
    private JMenuItem jMenuItem1 = new JMenuItem();
    private PartTreePanel partTreeJPanel;
    TitledBorder titledBorder1;
    JMenuItem jMenuExportAll = new JMenuItem();


    /**
     * <p>弹出式菜单项</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: 一汽启明</p>
     * @author 刘明
     * @version 1.0
     */
    class AddAction extends AbstractAction
    {
        /**
         * 构造函数
         * @param name 菜单项的显示名称
         */
        public AddAction(String name)
        {
            super(name);
        }


        /**
         * 把从资源树选择的资源对象添加到对应的关联列表中
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e)
        {
            BaseValueInfo[] info = TechnicsRegulationsMainJFrame.this.
                                   getSelectedTreeObject();
            TechnicsRegulationsMainJFrame.this.getContentJPanel().
                    addObjectToTable(info);
        }
    }


    /**
     * 当在树中选中业务对象后鼠标点击右键并松开时，显示弹出菜单。
     * @param e MouseEvent
     */
    void treePanel_mouseReleased(MouseEvent e)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.resourceTreePanel_mouseReleased begin...");
        }
        if (e.isPopupTrigger())
        {
            if (e.getSource() instanceof CappTree)
            {
                CappTree tree = (CappTree) e.getSource();
                CappTreeObject treeObj = tree.getSelectedObject();

                if (treeObj != null &&
                    treeObj.getType() == CappTreeObject.BUSINESSOBJECT)
                {
                    popup.show(tree, e.getX(), e.getY());
                    setPopupState();
                }

            }
            else if (e.getSource() instanceof QMTree)
            {
                QMTree tree = (QMTree) e.getSource();
                Vector nodes = partTreeJPanel.getSelectedNodes();

                if (nodes != null && nodes.size() != 0)
                {
                    popup.show(tree, e.getX(), e.getY());
                    setPopupState();
                }

            }
        }
        popup.validate();
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.resourceTreePanel_mouseReleased end...弹出菜单");
        }
    }


    /**
     * 当在工艺树中选中业务对象后鼠标点击右键并松开时，显示弹出菜单。
     * @param e MouseEvent
     */
    private void processTreePanel_mouseReleased(MouseEvent e)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.processTreePanel_mouseReleased begin...");
        }
        if (e.isPopupTrigger())
        {
            if (e.getSource() instanceof CappTree)
            {
                CappTree tree = (CappTree) e.getSource();
                technicsTreePopup.show(tree, e.getX(), e.getY());
                technicsTreePopup.repaint();
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.processTreePanel_mouseReleased end...弹出菜单");
        }
    }


    /**
     * 设置弹出菜单的显示状态
     */
    private void setPopupState()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRegulationsMainJFrame.setPopupState() begin...");

        }
        JPanel treePanel = getSelectedTreePanel();
        if (treePanel != null && getContentJPanel().getMode() != VIEW_MODE)
        {
            if (treePanel.equals(equipmentTreePanel) &&
                (getContentJPanel().techStepIsShowing() ||
                 getContentJPanel().techPaceIsShowing()))
            {
                eqAction.setEnabled(true);
                toAction.setEnabled(false);
                maAction.setEnabled(false);
                termAction.setEnabled(false);
                drawAction.setEnabled(false);
                techAction.setEnabled(false);
                copyAction.setEnabled(false);
                pasteAction.setEnabled(false);
                partAction.setEnabled(false);
            }
            else if (treePanel.equals(toolTreePanel) &&
                     (getContentJPanel().techStepIsShowing() ||
                      getContentJPanel().techPaceIsShowing()))
            {
                eqAction.setEnabled(false);
                toAction.setEnabled(true);
                maAction.setEnabled(false);
                termAction.setEnabled(false);
                drawAction.setEnabled(false);
                techAction.setEnabled(false);
                copyAction.setEnabled(false);
                pasteAction.setEnabled(false);
                partAction.setEnabled(false);
            }
            else if (treePanel.equals(materialTreePanel) &&
                     (getContentJPanel().techMasterIsShowing() ||
                      getContentJPanel().techStepIsShowing() ||
                      getContentJPanel().techPaceIsShowing()||
                    //CCBegin SS13
                      getContentJPanel().techMasterIsShowingForCD()))
            	     //CCEnd SS13))
            {
                eqAction.setEnabled(false);
                toAction.setEnabled(false);
                maAction.setEnabled(true);
                termAction.setEnabled(false);
                drawAction.setEnabled(false);
                techAction.setEnabled(false);
                copyAction.setEnabled(false);
                pasteAction.setEnabled(false);
                partAction.setEnabled(false);
            }
            else if (treePanel.equals(termTreePanel) &&
                     (getContentJPanel().techMasterIsShowing() ||
                      getContentJPanel().techStepIsShowing() ||
                      getContentJPanel().techPaceIsShowing() ||
                      //CCBegin SS13
                      getContentJPanel().techMasterIsShowingForCD()))
            	     //CCEnd SS13
            {
                eqAction.setEnabled(false);
                toAction.setEnabled(false);
                maAction.setEnabled(false);
                termAction.setEnabled(true);
                drawAction.setEnabled(false);
                techAction.setEnabled(false);
                copyAction.setEnabled(false);
                pasteAction.setEnabled(false);
                partAction.setEnabled(false);
            }
            else if (treePanel.equals(drawingTreePanel) &&
                     (getContentJPanel().techStepIsShowing() ||
                      getContentJPanel().techPaceIsShowing()))
            {
                eqAction.setEnabled(false);
                toAction.setEnabled(false);
                maAction.setEnabled(false);
                termAction.setEnabled(false);
                drawAction.setEnabled(true);
                techAction.setEnabled(false);
                copyAction.setEnabled(false);
                pasteAction.setEnabled(false);
                partAction.setEnabled(false);
            }
            else if (treePanel.equals(partTreeJPanel)
                     && (getContentJPanel().techMasterIsShowing() ||
                         getContentJPanel().techStepIsShowing() ||
                         getContentJPanel().techPaceIsShowing()))
            {
                eqAction.setEnabled(false);
                toAction.setEnabled(false);
                maAction.setEnabled(false);
                termAction.setEnabled(false);
                drawAction.setEnabled(false);
                techAction.setEnabled(false);
                copyAction.setEnabled(false);
                pasteAction.setEnabled(false);
                partAction.setEnabled(true);
            }
            else
            {
                eqAction.setEnabled(false);
                toAction.setEnabled(false);
                maAction.setEnabled(false);
                termAction.setEnabled(false);
                drawAction.setEnabled(false);
                techAction.setEnabled(false);
                copyAction.setEnabled(false);
                pasteAction.setEnabled(false);
                partAction.setEnabled(false);
            }
        }
        else
        {

            eqAction.setEnabled(false);
            toAction.setEnabled(false);
            maAction.setEnabled(false);
            termAction.setEnabled(false);
            drawAction.setEnabled(false);
            techAction.setEnabled(false);
            copyAction.setEnabled(false);
            pasteAction.setEnabled(false);
            partAction.setEnabled(false);

        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.setPopupState() end...return is void");
        }
    }


    /**
     * 获得树节点对象
     * @return 树节点对象
     */
    private BaseValueInfo[] getSelectedTreeObject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getResourceTreeObject() begin...");
        }
        JPanel treePanel = getSelectedTreePanel();
        BaseValueInfo[] obj = null;
        if (treePanel != null && treePanel instanceof ResourceTreePanel)
        {
            CappTreeNode[] nodes = ((ResourceTreePanel) treePanel).
                                   getSelectedNodes();
            obj = new BaseValueInfo[nodes.length];
            for (int i = 0, j = nodes.length; i < j; i++)
            {
                obj[i] = (BaseValueInfo) nodes[i].getObject().getObject();
            }
        }
        else if (treePanel != null && treePanel instanceof TechnicsTreePanel)
        {
            obj = new BaseValueInfo[1];
            obj[0] = (BaseValueInfo) ((TechnicsTreePanel) treePanel).
                     getSelectedObject().getObject();
        }
        //零部件
        else if (treeJTabbedPane.getSelectedComponent().equals(
                partTreeJPanel))
        {
            Vector nodes = partTreeJPanel.getSelectedNodes();
            obj = new BaseValueInfo[nodes.size()];
            for (int i = 0, j = nodes.size(); i < j; i++)
            {
                obj[i] = (BaseValueInfo) ((QMNode) nodes.elementAt(i)).
                         getObj().getObject();
            }
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getResourceTreeObject() end...return: " +
                               obj);
        }
        return obj;
    }


    /**
     * 获得当前被选中的树
     * @return 工艺树、设备树、工装树、术语树、简图树或材料树等
     */
    public JPanel getSelectedTreePanel()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getSelectedResourceTreePanel() begin...");
        }
        Component c = treeJTabbedPane.getSelectedComponent();
        JPanel selected = null;
        if (c instanceof JPanel)
        {
            selected = (JPanel) c;

        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getSelectedResourceTreePanel() end...return: " +
                               selected);

        }
        return selected;
    }


    /**
     * 搜索当前工艺或资源
     * @param e
     */
    public void searchTreeObject()
    {
        Component c = treeJTabbedPane.getSelectedComponent();
        if (c instanceof TechnicsTreePanel)
        {
            mainController.processSearchTechnicsCommand();
        }
        if (c instanceof ResourceTreePanel)
        {
            ResourceTreePanel treePanel = (ResourceTreePanel) c;
            if (treePanel.equals(equipmentTreePanel))
            {
                SearchEQ();
            }
            if (treePanel.equals(toolTreePanel))
            {
                SearchTL();
            }
            if (treePanel.equals(materialTreePanel))
            {
                SearchMT();
            }
            if (treePanel.equals(termTreePanel))
            {
                SearchTM();
            }
            if (treePanel.equals(drawingTreePanel))
            {
                SearchDW();
            }
        }
        if (c.equals(partTreeJPanel))
        {
            SearchPT();
        }
    }

    void searchTreeJButton_actionPerformed(ActionEvent e)
    {
        mainController.processSearchTreeObjectCommond();
    }


    /**
     * 更新并展开指定的工艺树节点,
     * @param info 工艺对象（工艺卡、工序、工步均可）
     * @param parentTechnicsID  该工艺节点所属的工艺卡头的BsoID。如果指定的节点info为工
     * 艺卡，则parentTechnicsID可以为空
     * @return 更新后的新节点
     */
    public CappTreeNode updateNode(BaseValueInfo info, String parentTechnicsID)
    {
        CappTreeNode node = null;
        Class[] c =
                {BaseValueIfc.class};
        Object[] obj =
                {info};
        try
        {
            info = (BaseValueInfo) TechnicsAction.useServiceMethod(
                    "PersistService", "refreshInfo", c, obj);
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        ProcessTreeObject treeobj = null;
        if (info instanceof QMTechnicsInfo)
        {
            treeobj = new TechnicsTreeObject((QMTechnicsInfo) info);
            node = getProcessTreePanel().updateNode(treeobj);
        }
        else if (info instanceof QMProcedureInfo)
        {
            if (((QMProcedureInfo) info).getIsProcedure())
            {
                treeobj = new StepTreeObject((QMProcedureInfo) info);
                treeobj.setParentID(parentTechnicsID);
                node = getProcessTreePanel().updateNode(treeobj);
            }
            else
            {
                treeobj = new OperationTreeObject((QMProcedureInfo) info);
                treeobj.setParentID(parentTechnicsID);
                node = getProcessTreePanel().updateNode(treeobj);
            }
        }
        return node;
    }


    /**
     * 检入时，把指定的新工序（步）树节点挂在工艺树上
     * @param parentNode 父节点
     * @param info 工序（步）对象
     */
    public void addProcedureNode(CappTreeNode parentNode, QMProcedureInfo info)
    {
        Class[] c =
                {BaseValueIfc.class};
        Object[] obj =
                {info};
        try
        {
            info = (QMProcedureInfo) TechnicsAction.useServiceMethod(
                    "PersistService", "refreshInfo", c, obj);
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        BusinessTreeObject treeobj = null;

        if (((QMProcedureInfo) info).getIsProcedure())
        {
            treeobj = new StepTreeObject((QMProcedureInfo) info);
            ((StepTreeObject) treeobj).setParentID(
                    ((ProcessTreeObject) (parentNode.getObject())).getParentID());
            getProcessTreePanel().addNode(parentNode, treeobj);
        }
        else
        {
            treeobj = new OperationTreeObject((QMProcedureInfo) info);
            ((OperationTreeObject) treeobj).setParentID(
                    ((ProcessTreeObject) (parentNode.getObject())).getParentID());
            getProcessTreePanel().addNode(parentNode, treeobj);
        }

    }


    /**
     * 检出时，添加指定的工艺卡（副本）到工艺树上
     * @param info
     * @param flag 如果flag = true，则更改图标
     */
    public void addProcess(QMTechnicsInfo info)
    {
        Class[] c =
                {BaseValueIfc.class};
        Object[] obj =
                {info};
        try
        {
            info = (QMTechnicsInfo) TechnicsAction.useServiceMethod(
                    "PersistService", "refreshInfo", c, obj);
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        TechnicsTreeObject treeobj = new TechnicsTreeObject(info);
        getProcessTreePanel().addProcess(treeobj);

    }


    /**
     * 检出时，添加指定的工序（步）节点（副本）到父节点上
     * @param parentNode 父节点
     * @param info
     * @param flag 如果flag = true，则更改图标
     */
    public void addNode(CappTreeNode parentNode, QMProcedureInfo info)
    {
        Class[] c =
                {BaseValueIfc.class};
        Object[] obj =
                {info};
        try
        {
            info = (QMProcedureInfo) TechnicsAction.useServiceMethod(
                    "PersistService", "refreshInfo", c, obj);
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (info.getIsProcedure())
        {
            StepTreeObject treeobj = new StepTreeObject(info);
            ((ProcessTreeObject) treeobj).setParentID(
                    ((ProcessTreeObject) parentNode.getObject()).getParentID());
            getProcessTreePanel().addNode(parentNode, treeobj);
        }
        else
        {
            OperationTreeObject treeobj = new OperationTreeObject(info);
            ((ProcessTreeObject) treeobj).setParentID(
                    ((ProcessTreeObject) parentNode.getObject()).getParentID());
            getProcessTreePanel().addNode(parentNode, treeobj);
        }
    }


    public void treeButtonstate()
    {

    }


    /**
     * 检入检出时，删除指定的工艺树节点
     * @param info 指定的对象
     */
    public void removeNode(BaseValueInfo info)
    {
        if (info instanceof QMTechnicsInfo)
        {
            getProcessTreePanel().removeNode(
                    new TechnicsTreeObject((QMTechnicsInfo) info));
        }
        else if (info instanceof QMProcedureInfo)
        {
            if (((QMProcedureInfo) info).getIsProcedure())
            {
                getProcessTreePanel().removeNode(
                        new StepTreeObject((QMProcedureInfo) info));
            }
            else
            {
                getProcessTreePanel().removeNode(
                        new OperationTreeObject((QMProcedureInfo) info));
            }
        }
    }


    /**
     * 根据指定的对象获得其树节点
     * @param obj 指定的对象
     * @return 对象的树节点
     */
    public CappTreeNode findNode(CappTreeObject obj)
    {
        return getProcessTreePanel().findNode(obj);
    }


    /**
     * 获得指定节点的所有处于检入状态的父节点
     * @param currentNode  指定节点
     * @return 节点集合
     */
    public Vector getParentCheckInNodes(CappTreeNode currentNode)
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getParentCheckInNodes() begin...currentNode=" +
                               currentNode);
        }
        Vector nodes = new Vector();
        Vector turnNodes = new Vector();
        CappTreeObject treeObj = currentNode.getObject();
        if (treeObj instanceof StepTreeObject ||
            treeObj instanceof OperationTreeObject)
        {
            CappTreeNode parent = (CappTreeNode) currentNode.getParent();
            BaseValueInfo obj = (BaseValueInfo) parent.getObject().getObject();

            while ((!(obj instanceof QMTechnicsInfo)) &&
                   CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc) obj) ) //在公共资料夹中
            {
                nodes.addElement(parent);
                parent = (CappTreeNode) parent.getParent();
                obj = (BaseValueInfo) parent.getObject().getObject();
            }
            if ((obj instanceof QMTechnicsInfo) &&
                CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc) obj) )
            {
                nodes.addElement(parent);
            }

            for (int i = 0; i < nodes.size(); i++)
            {
                turnNodes.add(nodes.elementAt(nodes.size() - i - 1));
            }
            if (verbose)
            {
                System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getParentCheckInNodes() end...return: " +
                                   turnNodes);
            }
            return turnNodes;
        }
        else
        {
            return null;
        }

    }


    /**
     * 获得当前所选择对象及其所有处于检入状态的父节点
     * @param currentNode  指定节点
     * @return 节点集合
     */
    public Vector getCheckInStateNodes(CappTreeNode currentNode)
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getCheckInStateNodes() begin...currentNode=" +
                               currentNode);
        }
        Vector nodes = new Vector();
        Vector turnNodes = new Vector();
        CappTreeObject treeObj = currentNode.getObject();
        if (CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc) treeObj.getObject()))
        {
            nodes.addElement(currentNode);
        }
        if (treeObj instanceof StepTreeObject ||
            treeObj instanceof OperationTreeObject)
        {
            CappTreeNode parent = (CappTreeNode) currentNode.getParent();
            BaseValueInfo obj = (BaseValueInfo) parent.getObject().getObject();

            while ((!(obj instanceof QMTechnicsInfo)) &&
                   CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc) obj)) //在公共资料夹中
            {
                nodes.addElement(parent);
                parent = (CappTreeNode) parent.getParent();
                obj = (BaseValueInfo) parent.getObject().getObject();
            }
            if ((obj instanceof QMTechnicsInfo) &&
               CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc) obj))
            {
                nodes.addElement(parent);
            }

            for (int i = 0; i < nodes.size(); i++)
            {
                turnNodes.add(nodes.elementAt(nodes.size() - i - 1));
            }
            if (verbose)
            {
                System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getCheckInStateNodes() end...return: " +
                                   turnNodes);

            }
            return turnNodes;
        }
        else if (treeObj instanceof TechnicsTreeObject)
        {
            return nodes;
        }
        else
        {
            return null;
        }

    }


    /**
     *搜索设备
     */
    public void SearchEQ()
    {
        String searchEq = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.resource.util.ResourceRB", "searchEq", null);
        CappChooser qmchooser = new CappChooser("QMEquipment", searchEq, this);
        qmchooser.addExttrPanel("QMEquipment", null);
        qmchooser.addListener(new CappQueryListener()
        {

            public void queryEvent(CappQueryEvent qmqueryevent)
            {
                if (qmqueryevent.getType().equals("Command") &&
                    qmqueryevent.getCommand().equals("OK"))
                {
                    CappChooser qmchooser1 = (CappChooser) qmqueryevent.
                                             getSource();
                    BaseValueIfc[] lequips = qmchooser1.getSelectedDetails();
                    Vector vecs = new Vector();
                    for (int d = 0; d < lequips.length; d++)
                    {
                        QMEquipmentInfo equip = (QMEquipmentInfo) lequips[d];
                        EquipTreeObject treeObject = new EquipTreeObject(equip);
                        vecs.add(treeObject);
                    }
                    ((ResourceTreePanel) getSelectedTreePanel()).addResources(
                            vecs);
                }
            }
        });
        qmchooser.setVisible(true);

    }


    /**
     * 搜索工装
     */
    private void SearchTL()
    {
        if (verbose)
        {
            System.out.println(" ooooooooooo  搜索工装oooooooooo");
        }
        String searchTl = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.resource.util.ResourceRB", "searchTl", null);
        CappChooser qmchooser = new CappChooser("QMTool", searchTl, this);
        qmchooser.addExttrPanel("QMTool", null);
        qmchooser.addListener(new CappQueryListener()
        {
            public void queryEvent(CappQueryEvent qmqueryevent)
            {
                if (qmqueryevent.getType().equals("Command") &&
                    qmqueryevent.getCommand().equals("OK"))
                {
                    CappChooser qmchooser1 = (CappChooser) qmqueryevent.
                                             getSource();
                    BaseValueIfc[] tools = qmchooser1.getSelectedDetails();
                    Vector vecs = new Vector();
                    for (int d = 0; d < tools.length; d++)
                    {
                        QMToolInfo tool = (QMToolInfo) tools[d];
                        vecs.add(new ToolTreeObject((QMToolInfo) tool));

                    }
                    ((ResourceTreePanel) getSelectedTreePanel()).addResources(
                            vecs);
                }

            }
        });
        qmchooser.setVisible(true);
    }


    /**
     * 搜索材料
     */
    private void SearchMT()
    {
        String searchMt = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.resource.util.ResourceRB", "searchMt", null);
        CappChooser qmchooser = new CappChooser("QMMaterial", searchMt, this);

        qmchooser.addExttrPanel("QMMaterial", null);
        qmchooser.addListener(new CappQueryListener()
        {
            public void queryEvent(CappQueryEvent qmqueryevent)
            {
                if (qmqueryevent.getType().equals("Command") &&
                    qmqueryevent.getCommand().equals("OK"))
                {
                    CappChooser qmchooser1 = (CappChooser) qmqueryevent.
                                             getSource();
                    BaseValueIfc[] materias = qmchooser1.getSelectedDetails();
                    Vector vecs = new Vector();
                    for (int d = 0; d < materias.length; d++)
                    {
                        QMMaterialInfo materia = (QMMaterialInfo) materias[d];
                        vecs.add(new MaterialTreeObject((QMMaterialInfo)
                                materia));
                    }
                    ((ResourceTreePanel) getSelectedTreePanel()).addResources(
                            vecs);
                }
            }
        });
        qmchooser.setVisible(true);
    }


    /**
     * 搜索简图
     */
    private void SearchDW()
    {
        String searchDw = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.resource.util.ResourceRB", "searchDw", null);
        CappChooser qmchooser = new CappChooser("Drawing", searchDw, this);
        qmchooser.removeViewButton();

        // int[] aa={1,1,1,1};
        //qmchooser.setRelColWidth(aa);

        qmchooser.addListener(new CappQueryListener()
        {

            public void queryEvent(CappQueryEvent qmqueryevent)
            {
                if (qmqueryevent.getType().equals("Command") &&
                    qmqueryevent.getCommand().equals("OK"))
                {
                    CappChooser qmchooser1 = (CappChooser) qmqueryevent.
                                             getSource();
                    BaseValueIfc[] dws = qmchooser1.getSelectedDetails();
                    Vector vecs = new Vector();
                    for (int d = 0; d < dws.length; d++)
                    {
                        DrawingInfo ds = (DrawingInfo) dws[d];
                        vecs.add(new DrawingTreeObject((DrawingInfo) ds));

                    }
                    ((ResourceTreePanel) getSelectedTreePanel()).addResources(
                            vecs);
                }
            }
        });
        qmchooser.setVisible(true);
    }


    /**
     * 搜索工艺术语
     */
    private void SearchTM()
    {
        String searchTm = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.resource.util.ResourceRB", "searchTm", null);
        CappChooser qmchooser = new CappChooser("QMTerm", searchTm, this);
        qmchooser.removeViewButton();
        qmchooser.addListener(new CappQueryListener()
        {

            public void queryEvent(CappQueryEvent qmqueryevent)
            {
                if (qmqueryevent.getType().equals("Command") &&
                    qmqueryevent.getCommand().equals("OK"))
                {
                    CappChooser qmchooser1 = (CappChooser) qmqueryevent.
                                             getSource();
                    BaseValueIfc[] tes = qmchooser1.getSelectedDetails();
                    Vector vecs = new Vector();
                    for (int d = 0; d < tes.length; d++)
                    {
                        QMTermInfo te = (QMTermInfo) tes[d];
                        vecs.add(new TermTreeObject((QMTermInfo) te));
                    }

                    ((ResourceTreePanel) getSelectedTreePanel()).addResources(
                            vecs);
                }
            }
        });
        qmchooser.setVisible(true);
    }


    /**
     * 搜索零部件
     */
    private void SearchPT()
    {
        String title = QMMessage.getLocalizedMessage(
                "com.faw_qm.part.client.main.util.QMProductManagerRB",
                QMProductManagerRB.GENENAL_SEARCH, null);
        CappChooser d = new CappChooser("QMPartMaster", title, this);
        //20060926薛静修改，加上不查询子表的条件
        d.setChildQuery(false);
        d.addListener(new CappQueryListener()
        {
            public void queryEvent(CappQueryEvent e)
            {
                if (e.getType().equals(CappQueryEvent.COMMAND))
                {
                    //确定
                    if (e.getCommand().equals(CappQuery.OkCMD))
                    {
                        jMenuConfigCrit.setEnabled(false);
                        CappChooser c = (CappChooser) e.getSource();
                        //获得选择的零件数组
                        BaseValueIfc[] objs = (BaseValueIfc[]) c.
                                              getSelectedDetails();
                        //定义一个paster数组
                        QMPartMasterInfo[] partMasters = new QMPartMasterInfo[
                                objs.length];
                        for (int i = 0; i < objs.length; i++)
                        {
                            QMPartMasterInfo part = (QMPartMasterInfo) objs[i];
                            partMasters[i] = part;
                        }
                        partTreeJPanel.addPartMasters(partMasters);
                        jMenuConfigCrit.setEnabled(true);
                    }
                }
            }
        });

        //弹出搜索界面
        d.setVisible(true);
    }


    /**
     * 把选中的工艺卡挂到树上
     */
    public void myList_actionPerformed(ActionEvent e)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.myList_actionPerformed begin..");
        }

        CappMultiList c = (CappMultiList) e.getSource();
        Object[] bvi = c.getSelectedObjects();
        addTechnics(bvi);

    }

    public void addTechnics( Object[] bvi)
    {
        AddTechnicsThread thread = new AddTechnicsThread(bvi);
        thread.start();
    }

    /**
     *
     * <p>Title:添加工艺现成 </p>
     * <p>Description:将给出的工艺数组添加到树上 </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: 一汽启明</p>
     * @author 薛静
     * @version 1.0
     */
    class AddTechnicsThread extends Thread
    {
        private Object[] technics;
        public AddTechnicsThread(Object[] technicsO)
        {
            technics = technicsO;
        }

        public void run()
        {
            if (technics != null)
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                startProgress();

                Vector v = new Vector();
                //把从最新版本的工艺卡加入产品结构树中
                for (int i = 0; i < technics.length; i++)
                {
                    TechnicsTreeObject treeObject = new TechnicsTreeObject((
                            QMTechnicsInfo) technics[i]);
                    v.addElement(treeObject);
                }
                getProcessTreePanel().addProcesses(v);
                stopProgress();
                getProcessTreePanel().setNodeSelected((TechnicsTreeObject) v.
                        elementAt(0));

                setCursor(Cursor.getDefaultCursor());

            }


        }

    }


    /**
     * 获得零件树
     * @return PartTreePanel 零件树
     */
    public PartTreePanel getPartTreePanel()
    {
        return partTreeJPanel;
    }


    /**
     * 获得资源文件中信息的封装方法
     * @param s String 资源文件中的键
     * @param obj 插入的内容
     * @return String 资源文件中的信息
     */
    private String getMessage(String s, Object[] obj)
    {
        return QMMessage.getLocalizedMessage(RESOURCE, s, obj);
    }


    /**
     * 获得资源文件中信息的封装方法
     * @param s String 资源文件中的键
     * @return String 资源文件中的信息
     */
    private String getMessage(String s)
    {
        return QMMessage.getLocalizedMessage(RESOURCE, s, null);
    }


    /**
     * 初始化菜单与菜单项
     */
    private void initMenuItem()
    {
        jMenuBar1 = new JMenuBar();
        jMenuFile = new JMenu();
        jMenuFileExit = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuFileExit_status", null), mouselistener);
        jMenuHelp = new JMenu();
        jMenuHelpAbout = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuHelpAbout_status", null), mouselistener);
        jMenuFileCollect = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuFileCollect_status", null), mouselistener);
//      CCBegin SS8
        jMenuCTFileCollect = new QMMenuItem("长特工艺合并", mouselistener);
//      CCEnd SS8
        // jMenuFileImport = new QMMenuItem();
        jMenuFileExport = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuFileExport_status", null), mouselistener);
        jMenuFileRefresh = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuFileRefresh_status", null), mouselistener);
        jMenuFileCreate = new QMMenu(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuFileCreate_status", null), mouselistener);
        jMenuCreateTechnics = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuCreateTechnics_status", null), mouselistener);
        jMenuCreateStep = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuCreateStep_status", null), mouselistener);
        jMenuCreatePace = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuCreatePace_status", null), mouselistener);
        jMenuSelect = new JMenu();
        jMenuVersion = new JMenu();
        jMenuLifeCycle = new JMenu();
        
        //CCBegin SS10
        jMenuCheckAll=new QMMenuItem("检出所有工序工步",mouselistener);
        //CCEnd SS10
        
        //CCBegin SS12
        jMenuFindMainStep=new QMMenuItem("关键工序筛选",mouselistener);
        //CCEnd SS12
        
        //CCBegin SS11
        jMenuStructSearchTechnics = new QMMenuItem("结构搜索工艺规程", mouselistener);
        //CCEnd SS11
        //CCBegin SS1
        try {
			if(isBsxGroupUser()){
				jMenuSchedule = new JMenu();
				jMItemjiaju = new QMMenuItem("夹具明细表", mouselistener);   
				jMItemwanneng = new QMMenuItem("万能工具清单", mouselistener);   
        //CCBegin SS5
        jMItemwannengliangju = new QMMenuItem("万能量具清单", mouselistener);
        jMItemgongweiqiju = new QMMenuItem("工位器具清单", mouselistener);
        //CCEnd SS5
				jMItemequip = new QMMenuItem("设备清单", mouselistener);   
				jMItemmoju = new QMMenuItem("磨具一览表", mouselistener);   
				jMItemdaoju = new QMMenuItem("刀具一览表", mouselistener);   
				jMItemjiafuju = new QMMenuItem("夹辅具一览表", mouselistener);   
				jMItemzhuangpei = new QMMenuItem("装配工具一览表", mouselistener);  
			}
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //CCEnd SS1
        //CCBegin SS4
        try {
			if(getUserFromCompany().equals("zczx")){
				jMenuSchedule = new JMenu();
				jMItemzcjiaju = new QMMenuItem("轴齿夹具明细表", mouselistener);   
				jMItemzcwangong = new QMMenuItem("轴齿万能工具明细表", mouselistener);   
				jMItemzcwanliang = new QMMenuItem("轴齿万能量具明细表", mouselistener);
				jMItemzcjianju = new QMMenuItem("轴齿检具明细表", mouselistener);
				jMItemzcsb = new QMMenuItem("轴齿设备清单", mouselistener);   
				jMItemzcmoju = new QMMenuItem("轴齿磨具一览表", mouselistener);   
				jMItemzcdaoju = new QMMenuItem("轴齿刀具一览表", mouselistener);   
				//				CCBegin SS6
				jMItemzcdaofuju = new QMMenuItem("轴齿刀辅具一览表", mouselistener);   
//				CCEnd SS6
				jMItemzcjiafuju = new QMMenuItem("轴齿夹辅具一览表", mouselistener);   
				jMItemzcliangju = new QMMenuItem("轴齿量具一览表", mouselistener);  
				jMItemzcjianfuju = new QMMenuItem("轴齿检辅具一览表", mouselistener);   
			}
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //CCEnd SS4
		 //CCBegin SS7
        try {
			if(getUserFromCompany().equals("ct")){
				jMenuSchedule = new JMenu();
				jMItemCtTool = new QMMenuItem("长特工装明细", mouselistener);   
				jMItemCtSheBei = new QMMenuItem("长特设备清单", mouselistener);   
				jMItemCtMoJu = new QMMenuItem("长特模具清单", mouselistener);
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS7
        
        jMenuSelectMadeTech = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectMadeTech_status", null), mouselistener);
        jMenuSelectMadeStep = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectMadeStep_status", null), mouselistener);
        jMenuSelectCopy = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuSelectCopy_status", null), mouselistener);
        jMenuSelectPaste = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectPaste_status", null), mouselistener);
        jMenuVersionCheckIn = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuVersionCheckIn_status", null), mouselistener);
        jMenuVersionCheckOut = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuVersionCheckOut_status", null), mouselistener);
        jMenuVersionCancel = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuVersionCancel_status", null), mouselistener);
        jMenuVersionRevise = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuVersionRevise_status", null), mouselistener);
        jMenuVersionVersion = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuVersionVersion_status", null), mouselistener);
        jMenuVersionIteration = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuVersionIteration_status", null), mouselistener);
        jMenuSetLifeCycleState = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSetLifeCycleState_status", null), mouselistener);
        jMenuLifeCycleSelect = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuLifeCycleSelect_status", null), mouselistener);
        jMenuLifeCycleView = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuLifeCycleView_status", null), mouselistener);
        jMenuLifeCycleGroup = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuLifeCycleGroup_status", null), mouselistener);
        jMenuIntellectPart = new QMMenuItem();
        jMenuIntellectRoot = new QMMenuItem();
        jMenuSearchEquip = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSearchEquip_status", null), mouselistener);
        jMenuSearchTool = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuSearchTool_status", null), mouselistener);
        jMenuSearchMaterial = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSearchMaterial_status", null), mouselistener);
        jMenuSearchTechnics = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSearchTechnics_status", null), mouselistener);
        jMenuHelpManage = new QMMenuItem();
        jMenuSelectDelete = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectDelete_status", null), mouselistener);
        //Begin CR3
        jMenuSelectMoveOut = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuMoveOutTechnics_status", null), mouselistener);
        jMenuSelectMoveOut1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuMoveOutTechnics_status", null), mouselistener);
        //End CR3
        jMenuAddObject = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuAddObject_status", null), mouselistener);

        //jMenuIntellect = new JMenu();
        jMenuSearch = new JMenu();
        jMenuSelectView = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuSelectView_status", null), mouselistener);
        jMenuSelectUpdate = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectUpdate_status", null), mouselistener);
        jMenuSelectFormStepNum = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectFormStepNum_status", null), mouselistener);
        //CR1 begin
		jMenuSelectFormPaceNum = new QMMenuItem(QMMessage.getLocalizedMessage(
				RESOURCE, "jMenuSelectFormPaceNum_status", null), mouselistener);
        //CR1 end
        jMenuSelectBrowse = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectBrowse_status", null), mouselistener);
        jMenuSelectChangeLocation = new QMMenuItem(QMMessage.
                getLocalizedMessage(RESOURCE,
                                    "jMenuSelectChangeLocation_status", null),
                mouselistener);
        jMenuSelectRename = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectRename_status", null), mouselistener);
        jMenuSelectSaveAs = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectSaveAs_status", null), mouselistener);
        jMenuSelectUseTech = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectUseTech_status", null), mouselistener);
        jMenuSelectUseStep = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectUseStep_status", null), mouselistener);
        jMenuConfigCrit = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuConfigCrit_status", null), mouselistener);
        jMenuHelpItem = new QMMenuItem(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuHelpItem_status", null), mouselistener);
        //右健快捷菜单
        jMenuFileCreate1 = new JMenu(QMMessage.getLocalizedMessage(RESOURCE,
                "jMenuFileCreate_status", null));
        jMenuCreateTechnics1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuCreateTechnics_status", null), mouselistener);
        jMenuCreateStep1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuCreateStep_status", null), mouselistener);
        jMenuCreatePace1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuCreatePace_status", null), mouselistener);
        jMenuFileCollect1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuFileCollect_status", null), mouselistener);
//        CCBegin SS8
        jMenuCTFileCollect1 = new QMMenuItem("长特工艺合并", mouselistener);
//        CCEnd SS8
        jMenuSelectView1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectView_status", null), mouselistener);
        jMenuSelectUpdate1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectUpdate_status", null), mouselistener);
        jMenuSelectDelete1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectDelete_status", null), mouselistener);
        jMenuSelectBrowse1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectBrowse_status", null), mouselistener);
        jMenuSelectCopy1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectCopy_status", null), mouselistener);
        jMenuSelectPaste1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectPaste_status", null), mouselistener);
        jMenuSelectSaveAs1 = new QMMenuItem(QMMessage.getLocalizedMessage(
                RESOURCE,
                "jMenuSelectSaveAs_status", null), mouselistener);
        
        //CCBegin SS15
        jMenuSelectExport = new QMMenuItem("导出",mouselistener);
        //CCEnd SS15
    }


    /**
     * 开始进度条
     */
    public void startProgress()
    {
        progressDialog.startProcess();
    }


    /**
     * 结束进度条
     */
    public void stopProgress()
    {
        progressDialog.stopProcess();
    }


    /**
     *
     * <p>Title: 鼠标监听类</p>
     * <p>Description:鼠标监听类 </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: 一汽启明</p>
     * @author 薛静
     * @version 1.0
     */
    class MyMouseListener extends MouseAdapter
    {
        public void mouseEntered(MouseEvent e)
        {
            Object obj = e.getSource();
            //按钮
            if (obj instanceof JButton)
            {
                JButton button = (JButton) obj;
                statusBar.setText((String) toolFunction.get(button.
                        getActionCommand()));
            }
            //菜单项
            if (obj instanceof QMMenuItem)
            {
                QMMenuItem item = (QMMenuItem) obj;
                //将菜单文字去掉助记符
                String s = item.getExplainText();
                statusBar.setText(s);
            }
            //菜单
            if (obj instanceof QMMenu)
            {
                QMMenu item = (QMMenu) obj;
                statusBar.setText(item.getExplainText());
            }

        }


        /**
         * 鼠标离开
         * @param e MouseEvent
         */
        public void mouseExited(MouseEvent e)
        {
            statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "default_status", null));
        }
    }

    /**
     * 刷新工艺树对象
     * @param object CappTreeObject 工艺树对象
     * @return CappTreeObject 刷新后的对象
     */
    public CappTreeObject update(CappTreeObject object)throws QMRemoteException
    {
        BaseValueInfo newinfo = (BaseValueInfo) CappClientHelper.refresh(
               (BaseValueInfo)object.getObject());
        object.setObject(newinfo);
        processTreePanel.updateNode(object);
        return object;

    }
    
    //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2676 
        //Begin CR6
    public void closeNode(CappTreeNode node)
    {
        if (node.getObject() == null)
        {
            return;
        }
        if (node.getObject().getObject() instanceof BaseValueInfo)
        {
            node.removeAllChildren();
            processTreePanel.getTree().myModel().reload(node);
        }
    }//End CR6
    //CCEnd by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 td2676 
  //CCBegin SS2
    /**
     * 获得当前用户
     * @return String 当前用户
     */
    public UserIfc getCurrentUser()
            throws QMRemoteException
    {
        UserIfc sysUser = (UserIfc) TechnicsAction.useServiceMethod("SessionService",
                "getCurUserInfo", null, null);
        return sysUser;
    }
    
  	public boolean isBsxGroupUser() throws QMException 
  	{
  		Vector groupVec = new Vector();  		
  		UserIfc sysUser = getCurrentUser();
  		Class[] paraclass1 = {UserInfo.class, boolean.class};
  		Object[] paraobj1 = {(UserInfo)sysUser, Boolean.TRUE};
  		Enumeration groups = (Enumeration) TechnicsAction.useServiceMethod("UsersService","userMembers", paraclass1,paraobj1);
  		for (; groups.hasMoreElements();) 
  		{			
  			GroupIfc group = (GroupIfc) groups.nextElement();
  			String groupName = group.getUsersName();
  			if (groupName.equals("变速箱用户组")) 
  			{
  				return true;
  			}
  		}
  		return false;
  	}
  //CCEnd SS2
  	
//  	CCBegin SS8
  	public boolean isCTGroupUser() throws QMException 
  	{
  		Vector groupVec = new Vector();  		
  		UserIfc sysUser = getCurrentUser();
  		Class[] paraclass1 = {UserInfo.class, boolean.class};
  		Object[] paraobj1 = {(UserInfo)sysUser, Boolean.TRUE};
  		Enumeration groups = (Enumeration) TechnicsAction.useServiceMethod("UsersService","userMembers", paraclass1,paraobj1);
  		for (; groups.hasMoreElements();) 
  		{			
  			GroupIfc group = (GroupIfc) groups.nextElement();
  			String groupName = group.getUsersName();
  			if (groupName.indexOf("长特") >= 0 ) 
  			{
  				return true;
  			}
  		}
  		return false;
  	}
//  	CCEnd SS8
  	
  	//CCBegin SS3
    /**
     * 判断用户所属公司
     * @return String 获得用户所属公司
     * @author wenl
     */
    public String getUserFromCompany() throws QMException {
		String returnStr = "";
		RequestServer server = RequestServerFactory.getRequestServer();
		StaticMethodRequestInfo info = new StaticMethodRequestInfo();
		info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
		info.setMethodName("getUserFromCompany");
		Class[] paraClass = {};
		info.setParaClasses(paraClass);
		Object[] obj = {};
		info.setParaValues(obj);
		boolean flag = false;
		try {
			returnStr = ((String) server.request(info));
		} catch (QMRemoteException e) {
			throw new QMException(e);
		}
		return returnStr;
	}
  	//CCEnd SS3
}
