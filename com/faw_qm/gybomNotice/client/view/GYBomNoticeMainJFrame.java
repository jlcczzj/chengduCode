/**
 * 版权归一汽启明公司所有

 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 去掉“导出BOm”选项  TD8461 文柳 2014-7-18
 * SS2 自动生成BOM发布单 lishu 2017-5-12
 */

package com.faw_qm.gybomNotice.client.view;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.faw_qm.clients.beans.explorer.ProgressDialog;
import com.faw_qm.clients.beans.explorer.QMMenu;
import com.faw_qm.clients.beans.explorer.QMMenuItem;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.gybomNotice.client.controller.MainController;
import com.faw_qm.gybomNotice.client.util.GYBomAdoptNoticeTreeObject;
import com.faw_qm.gybomNotice.client.util.GYBomNoticeTree;
import com.faw_qm.gybomNotice.client.util.GYBomNoticeTreeObject;
import com.faw_qm.gybomNotice.client.util.GYBomNoticeTreePanel;
import com.faw_qm.gybomNotice.client.util.GYNoticeClientRequestServer;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.gybomNotice.util.GYNoticeHelper;
import com.faw_qm.lock.model.LockIfc;

/**
 * <p> Title: 工艺BOM发布单管理器模块主界面 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2014 </p> <p> Company: 一汽启明 </p>
 * @author 文柳
 * @version 1.0
 */

public class GYBomNoticeMainJFrame extends JFrame implements ActionListener
{
    private static final boolean verbose = GYNoticeHelper.VERBOSE;
    JPanel contentPane;
    JMenuBar jMenuBar1 = new JMenuBar();

    /**弹出菜单，用于右键删除、更新*/
    private JPopupMenu gyBomNoticeTreePopup = new JPopupMenu();
    private JMenuItem jMenuRightDelete = new JMenuItem("删除");
    private JMenuItem jMenuRightUpdate = new JMenuItem("更新");
   
    
   
    /** 系统状态栏 */
    JLabel statusBar = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    /** 采用单通知单、编辑、查看 */
    JMenu jMenuNotice = new JMenu();
    JMenu jMenuEdit = new JMenu();
    JMenu jMenuView = new JMenu();

    /** 编辑【废弃通知单、更新、维护专用件】 */
    JMenuItem jMenuDisuseNotice = new JMenuItem();
    JMenuItem jMenuUpdateNotice = new JMenuItem();
    JMenuItem jMenuZhuanYongJian = new JMenuItem();


    //创建通知单
    JMenu jMenuCreateNotice = new JMenu();
    //创建整车
    JMenuItem jMenuCreateCarload = new JMenuItem();
    //创建车架
    JMenuItem jMenuCreateCarFrame = new JMenuItem();
    //创建车身
    JMenuItem jMenuCreateCarBody = new JMenuItem();
   
    //保存通知单
    JMenuItem jMenuSave = new JMenuItem();
    //保存通知单
    JMenuItem jMenuSaveAs = new JMenuItem();
    //打开通知单
    JMenuItem jMenuSearch = new JMenuItem();
    //导出BOM
    JMenuItem jMenuExportBOM = new JMenuItem();
    //查看BOM功能不要了，所以注释了houhf
    //查看BOM
//    JMenuItem jMenuViewBOM = new JMenuItem();
    
    //导出整车BOM
    JMenuItem jMenuExportCarloadBOM = new JMenuItem();
    //查看整车BOM功能不要了所以注释了houhf
    //查看整车BOM
//    JMenuItem jMenuViewCarloadBOM = new JMenuItem();
    
    //查看通知单
    JMenuItem jMenuViewNotice = new JMenuItem();
    
   
    JSplitPane jSplitPane1 = new JSplitPane();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel rightPanel = new JPanel();
    GYBomNoticeTreePanel noticeTreePanel = new GYBomNoticeTreePanel();
 
    private ProgressDialog progressDialog;
    MainController controller = new MainController(this);

    /** 标记是否默认显示查看采用单界面 */
    public static boolean isViewBomAdoptNotice = true;
    //主页面右侧信息页面
    RightPanel taskPanel = null;
    GridLayout gridLayout1 = new GridLayout();
    TitledBorder titledBorder1;
    BorderLayout borderLayout2 = new BorderLayout();

    /** 构造函数
     * 
     *  */
    public GYBomNoticeMainJFrame()
    {

        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    /**初始化右健菜单
     * 采用单上删除、更新
     * */
    private void initGYBomNoticeTreePopup()
    {
    	gyBomNoticeTreePopup.add(jMenuRightUpdate);
    	gyBomNoticeTreePopup.add(jMenuRightDelete);

    }
  
    
    /** 
     * 初始化方法 
     * */
    private void jbInit()
    {
        contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(borderLayout1);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, (dimension.width-100), (dimension.height-60));
        setTitle("工艺BOM发布单管理");
        setIconImage(new ImageIcon(getClass().getResource("/images/cappBom_cappDesign.gif")).getImage());
       
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jMenuNotice.setText("发布单");
        jMenuCreateNotice.setText("新建");
        jMenuCreateCarload.setText("整车发布单");
        jMenuCreateCarFrame.setText("车架发布单");
        jMenuCreateCarBody.setText("驾驶室发布单");
        jMenuSearch.setText("打开");
        jMenuSave.setText("保存");
        jMenuSaveAs.setText("另存为");
        
        jMenuEdit.setText("编辑");
        jMenuDisuseNotice.setText("废弃发布单");
        jMenuUpdateNotice.setText("更新");
        jMenuZhuanYongJian.setText("维护专用件");

        jMenuView.setText("查看");
        jMenuExportBOM.setText("导出BOM");
//        jMenuViewBOM.setText("查看BOM");
        jMenuExportCarloadBOM.setText("导出整车BOM");
//        jMenuViewCarloadBOM.setText("查看整车BOM");
        jMenuViewNotice.setText("查看发布单");
       
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setLastDividerLocation(260);
        jSplitPane1.setLeftComponent(jTabbedPane1);
        jSplitPane1.setResizeWeight(1.0);
        rightPanel.setLayout(borderLayout2);

        jMenuBar1.add(jMenuNotice);
        jMenuBar1.add(jMenuEdit);
        jMenuBar1.add(jMenuView);
       // jMenuBar1.add(jMenuHelp);
        this.setJMenuBar(jMenuBar1);
        contentPane.add(statusBar, BorderLayout.SOUTH);
        contentPane.add(jSplitPane1, BorderLayout.CENTER);
        jTabbedPane1.add("工艺BOM发布单", noticeTreePanel);
        jSplitPane1.add(jTabbedPane1, JSplitPane.LEFT);
        jSplitPane1.add(rightPanel, JSplitPane.RIGHT);
        
        jMenuNotice.add(jMenuCreateNotice);
        jMenuCreateNotice.add(jMenuCreateCarload);
        jMenuCreateNotice.add(jMenuCreateCarFrame);
        jMenuCreateNotice.add(jMenuCreateCarBody);
        jMenuNotice.add(jMenuSearch);
        jMenuNotice.add(jMenuSave);
        jMenuNotice.add(jMenuSaveAs);
        
        
        jMenuEdit.add(jMenuDisuseNotice);
        jMenuEdit.add(jMenuUpdateNotice);
       // jMenuEdit.add(jMenuZhuanYongJian);

//CCBegin SS1
   //     jMenuView.add(jMenuExportBOM);
//CCEnd SS1
//        jMenuView.add(jMenuViewBOM);
        jMenuView.add(jMenuExportCarloadBOM);
//        jMenuView.add(jMenuViewCarloadBOM);
        jMenuView.add(jMenuViewNotice);
        progressDialog = new ProgressDialog(this);

        RMAction rmAction = new RMAction();
        this.jMenuCreateCarload.addActionListener(rmAction);
        this.jMenuCreateCarFrame.addActionListener(rmAction);
        this.jMenuCreateCarBody.addActionListener(rmAction);
        this.jMenuSearch.addActionListener(rmAction);
        this.jMenuSave.addActionListener(rmAction);
        this.jMenuSaveAs.addActionListener(rmAction);
        this.jMenuDisuseNotice.addActionListener(rmAction);
        this.jMenuUpdateNotice.addActionListener(rmAction);
        this.jMenuZhuanYongJian.addActionListener(rmAction);
        this.jMenuExportBOM.addActionListener(rmAction);
//        this.jMenuViewBOM.addActionListener(rmAction);
        this.jMenuExportCarloadBOM.addActionListener(rmAction);
//        this.jMenuExportCarloadBOM.addActionListener(new ActionListener() {
        	//houhf add
//        	public void actionPerformed(ActionEvent arg0) {
//        		
//        		try {
//        			GYBomAdoptNoticeIfc ifc = 
//            			(GYBomAdoptNoticeIfc)getBomNoticeListTreePanel().getSelectedObject();
//            		if(ifc == null)
//            		{
//            			JOptionPane.showMessageDialog(null, "请选择一个发布单!", "提示",
//        											JOptionPane.INFORMATION_MESSAGE);
//            			return;
//            		}
//					exportAllBOM();
//				}
//        		catch (IOException e)
//        		{
//        			JOptionPane.showMessageDialog(null, "导出失败!", "提示",
//        					JOptionPane.INFORMATION_MESSAGE);
//					e.printStackTrace();
//					return;
//				}
//        		catch (QMException e)
//        		{
//        			JOptionPane.showMessageDialog(null, "导出失败!", "提示",
//        					JOptionPane.INFORMATION_MESSAGE);
//					e.printStackTrace();
//					return;
//				}
//        	}
        	//houhf add end
//        });
//        this.jMenuViewCarloadBOM.addActionListener(rmAction);
        this.jMenuViewNotice.addActionListener(rmAction);
        //右键
        this.jMenuRightDelete.addActionListener(rmAction);
        this.jMenuRightUpdate.addActionListener(rmAction);
        initGYBomNoticeTreePopup();

        jSplitPane1.setDividerLocation(300);

        // 采用单树注册监听
        RootListTreeSelectionListener treeSelectListener = new RootListTreeSelectionListener();
        noticeTreePanel.addTreeSelectionListener(treeSelectListener);
        NoticeTreeMouseListener rtml = new NoticeTreeMouseListener();
        noticeTreePanel.addTreeMouseListener(rtml);
        
        //添加监听：在采用单树中点击鼠标右键，显示弹出菜单。
        noticeTreePanel.addTreeMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)            
			{
            	//右键
				if (e.getButton() == 3)
				{

					if (e.getSource() instanceof GYBomNoticeTree)
					{
						processTreePanel_mouseReleased(e);
					}
				}

			}                                                     
        });
        

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        /**
         * 应用程序关闭程序
         */
        this.addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                this_windowClosing(e);
            }
        });
    }
    /**
     * 当在采用单树中选中业务对象后鼠标点击右键并松开时，显示弹出菜单。
     * @param e MouseEvent
     */
    private void processTreePanel_mouseReleased(MouseEvent e) {

		if (e.getSource() instanceof GYBomNoticeTree) {
			GYBomNoticeTree tree = (GYBomNoticeTree) e.getSource();
			if (tree.getSelectedNode() != null) {
				if (!tree.getSelectedNode().isRoot()) {
					gyBomNoticeTreePopup.show(tree, e.getX(), e.getY());
					gyBomNoticeTreePopup.repaint();
				}
			}
		}
	}
    /**
     * 关闭应用程序
     * @param e 窗口事件
     */
    private void this_windowClosing(WindowEvent e)
    {
        exit();
    }

    /**
     * 退出主界面
     */
    public void exit()
    {
        
        String information = "是否要退出";
        String exitDialog = "退出";
        
        // 显示确认对话框
        int reply = DialogFactory.showYesNoDialog(this, information, exitDialog);
        if(reply == DialogFactory.NO_OPTION)
            return;
        if(reply == DialogFactory.YES_OPTION)
        {
            try
            {
                LockIfc lock = null;
                if(controller.getHashMap() != null)
                {
                    Collection col = null;
                    col = controller.getHashMap().values();
                    Iterator iterator = col.iterator();
                    while(iterator.hasNext())
                    {
                        lock = (LockIfc)iterator.next();
                        if(lock.getLocker() == null || lock.getLocker().equals(""))
                        {
                            return; //如果没有加锁 直接返回
                        }

                        Class[] theClass = {LockIfc.class, String.class};
                        Object[] obj = {lock, lock.getLocker()};
                        GYNoticeHelper.requestServer("GYBomNoticeService", "unlock", theClass, obj);
                    }
                }
            }catch(QMException ex)
            {
                ex.printStackTrace();
                DialogFactory.showWarningDialog(this, ex.getClientMessage());
            }
            System.exit(0);
        }
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
     * 处理工具条上的按钮的命令.当用户点击工具条上的按钮时会发出一个命令,命令名称 与按钮图片的名称一样
     * @param e
     */
    public void actionPerformed(ActionEvent e)
    {
    }




   


    /**
     * 界面中所有的菜单操作在此注册 <p> 根据当前所选择的菜单和业务对象，确定界面的显示内容 </p> <p> Copyright: Copyright (c) 2003 </p> <p> Company: 一汽启明 </p>
     * @author 刘明
     * @version 1.0
     */
    class RMAction implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            
            Object object = e.getSource();
            if(object == jMenuCreateCarload)//创建整车发布单
            {
                controller.processCreateCarloadCommand();
                return;
            }else if(object == jMenuCreateCarFrame)//创建车架发布单
            {
            	controller.processCreateCarFrameCommand();
            }else if(object == jMenuCreateCarBody)//创建车身
            {
            	controller.processCreateCarBodyCommand();
            }else if(object == jMenuSearch)//打开
            {
            	controller.processSearchNoticeCommand();
            }else if(object == jMenuSave)//保存
            {
            	controller.processSaveNoticeCommand();
            }else if(object == jMenuSaveAs)//另存
            {
            	controller.processSaveAsNoticeCommand();
            }else if(object == jMenuDisuseNotice)//废弃
            {
            	controller.processDisuseNoticeCommond();
            }else if(object == jMenuUpdateNotice)//更新
            {
            	controller.processUpdateCommond();
            }else if(object == jMenuZhuanYongJian)//专用件
            {
            	controller.processZhuanYongJianCommond();
            }else if(object == jMenuExportBOM)//导出BOM
            {
            	controller.processExportBomCommond();
            }
//            else if(object == jMenuViewBOM)//查看BOM
//            {
//            	controller.processViewBomCommond();
//            }
            else if(object == jMenuExportCarloadBOM)//导出整车BOm
            {
            	controller.processExportCarloadBomCommond();
            }
//            else if(object == jMenuViewCarloadBOM)//查看整车BOM
//            {
//            	controller.processViewCarloadBOMCommond();
//            }
            else if(object == jMenuViewNotice)//查看通知单
            {
            	controller.processViewNoticeCommond();
            }
            else if(object == jMenuRightDelete)//右键删除
            {
            	controller.processRightDeleteCommond();
            }
            else if(object == jMenuRightUpdate)//右键删除
            {
            	controller.processRightUpdateCommond();
            }

        }
    }

    /**
     * <p> Title: 鼠标监听类 </p> <p> Description:鼠标监听类 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
     * @author liuming
     * @version 1.0
     */
    class MyMouseListener extends MouseAdapter
    {
        public void mouseEntered(MouseEvent e)
        {
            Object obj = e.getSource();
            // 按钮
            if(obj instanceof JButton)
            {
                JButton button = (JButton)obj;
            }
            // 菜单项
            if(obj instanceof QMMenuItem)
            {
                QMMenuItem item = (QMMenuItem)obj;
                // 将菜单文字去掉助记符
                String s = item.getExplainText();
                statusBar.setText(s);
            }
            // 菜单
            if(obj instanceof QMMenu)
            {
                QMMenu item = (QMMenu)obj;
                statusBar.setText(item.getExplainText());
            }

        }

        /**
         * 鼠标离开
         * @param e MouseEvent
         */
        public void mouseExited(MouseEvent e)
        {
            statusBar.setText("要获得帮助，请点击“帮助”菜单或F1键。");
        }
    }

    /**
     * 初始化整车
     */
    public ZCBomAdoptNoticeViewPanel initCarloadNoticeRightPanel()
    {
        rightPanel.removeAll();
        this.taskPanel = new ZCBomAdoptNoticeViewPanel(this);
        rightPanel.add(taskPanel, BorderLayout.CENTER);
        taskPanel.validate();
        taskPanel.requestFocus();
        taskPanel.setVisible(true);
        return (ZCBomAdoptNoticeViewPanel)taskPanel;

    }
    /**
     * 初始车架、驾驶室
     */
    public CJAdoptNoticeViewPanel initFrameAndCabRightPanel(String type)
    {
        rightPanel.removeAll();
        this.taskPanel = new CJAdoptNoticeViewPanel(this,type);
        rightPanel.add(taskPanel, BorderLayout.CENTER);
        taskPanel.validate();
        taskPanel.requestFocus();
        taskPanel.setVisible(true);
        return (CJAdoptNoticeViewPanel)taskPanel;

    }

  
    /**
     * 关闭右侧内容面板 此方法用于每次进行某一菜单操作时更换界面
     */
    public void closeContentPanel()
    {
        if(taskPanel != null && taskPanel.isVisible())
        {
            if(taskPanel instanceof ZCBomAdoptNoticeViewPanel)
            {
          
                if(!((ZCBomAdoptNoticeViewPanel)taskPanel).isHaveSave)
                {
                    ((ZCBomAdoptNoticeViewPanel)taskPanel).quit();
                }
            }else if(taskPanel instanceof CJAdoptNoticeViewPanel)
            {
          
                if(!((CJAdoptNoticeViewPanel)taskPanel).isHaveSave)
                {
                    ((CJAdoptNoticeViewPanel)taskPanel).quit();
                }
            }

        }
        rightPanel.removeAll();
        taskPanel = null;
        refresh();
    }
    /**
     * 鼠标监听
     */
    class NoticeTreeMouseListener implements MouseListener
    {
        /**
         * mouseClicked
         * @param e MouseEvent
         */
        public void mouseClicked(MouseEvent e)
        {
            if(e.getClickCount() == 2)
            {
        
                try
                {
                	viewNoticeTreeObject();
                }catch(QMException e1)
                {
                    e1.printStackTrace();
                }
  
            }
        }

        /**
         * mouseEntered
         * @param e MouseEvent
         */
        public void mouseEntered(MouseEvent e)
        {}

        /**
         * mouseExited
         * @param e MouseEvent
         */
        public void mouseExited(MouseEvent e)
        {}

        /**
         * mousePressed
         * @param e MouseEvent
         */
        public void mousePressed(MouseEvent e)
        {}

        /**
         * mouseReleased
         * @param e MouseEvent
         */
        public void mouseReleased(MouseEvent e)
        {}

    }
  
    /**
     * <p> Title:采用单单树的节点选择监听 </p> <p> 当树节点的选择值变化时,更新菜单状态,弹出查看采用单界面 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
     * @author 文柳
     * @version 1.0
     */
    class RootListTreeSelectionListener implements TreeSelectionListener
    {
        public void valueChanged(TreeSelectionEvent e)
        {
            try
            {
            	
                viewNoticeTreeObject();
            }catch(QMException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 查看发布单
     * @throws QMException
     */
    public void viewNoticeTreeObject() throws QMException
    {
        GYBomNoticeTreeObject obj = GYBomNoticeMainJFrame.this.noticeTreePanel.getSelectedObject();

        if(taskPanel != null)
        {
            if(taskPanel instanceof ZCBomAdoptNoticeViewPanel)
            {
                // 右侧面板还存在时  isHaveSave=true时执行
                if(!((ZCBomAdoptNoticeViewPanel)taskPanel).isHaveSave)
                {
                    if(((ZCBomAdoptNoticeViewPanel)taskPanel).getViewMode() != 2)
                    {
                        ((ZCBomAdoptNoticeViewPanel)taskPanel).quit();
                    }
                }
            }else if(taskPanel instanceof CJAdoptNoticeViewPanel)
            {
                // 右侧面板还存在时  isHaveSave=true时执行
                if(!((CJAdoptNoticeViewPanel)taskPanel).isHaveSave)
                {
                    if(((CJAdoptNoticeViewPanel)taskPanel).getViewMode() != 2)
                    {
                        ((CJAdoptNoticeViewPanel)taskPanel).quit();
                    }
                }
            }
        }
        // 如果选中节点是BomNoticeTreeObject,则弹出查看界面
        if(obj != null && obj instanceof GYBomNoticeTreeObject)
        {
                closeContentPanel();
                GYBomAdoptNoticeIfc tmp = (GYBomAdoptNoticeIfc)obj.getObject();
                statusBar.setText("当前选中的采用单： " + tmp.getAdoptnoticenumber());
                controller.viewDefaultBomAdoptNotice(tmp);
            }
        }


    /**
     * 刷新主界面
     */
    public void refresh()
    {
        this.invalidate();
        this.doLayout();
        this.repaint();
    }



 





    /**
     * 获得采用单树
     * @return BomNoticeTreePanel
     */
    public GYBomNoticeTreePanel getBomNoticeListTreePanel()
    {
        return this.noticeTreePanel;
    }




    /**
     * 主方法
     * @param String[] args
     */
    public static void main(String[] args)
    {
//        System.out.println("大小============="+args.length);
        if(args.length == 3)
        {
//            System.out.println("111111111111111111主方法111111111111111111111111");
            try
            {
                System.out.println("****************start from jnlp*****************");
                System.setProperty("swing.useSystemFontSettings", "0");
                
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                GYNoticeClientRequestServer server = new GYNoticeClientRequestServer(args[0], args[1], args[2]);
                RequestServerFactory.setRequestServer(server);
                GYBomNoticeMainJFrame frame = new GYBomNoticeMainJFrame();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);

            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        //CCBegin SS2
        else if(args.length == 4)
        {
//            System.out.println("4444444444444444444主方法44444444444444444=="+args[3]);
            try
            {
                System.out.println("****************start from jnlp*****************");
                System.setProperty("swing.useSystemFontSettings", "0");
                
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                GYNoticeClientRequestServer server = new GYNoticeClientRequestServer(args[0], args[1], args[2]);
                RequestServerFactory.setRequestServer(server);
                GYBomNoticeMainJFrame frame = new GYBomNoticeMainJFrame();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);

//              bsoID = GYBomAdoptNotice_105737679
                Vector v = new Vector();
                String bsoID = args[3];// 采用单bsoID
              //调用服务方法查询数据
                Class[] theClass =
                        {String.class};
                Object[] theObjs =
                        {bsoID};
                GYBomAdoptNoticeInfo objs = (GYBomAdoptNoticeInfo)GYNoticeHelper.requestServer("PersistService", "refreshInfo",
                                         theClass, theObjs);
//                GYBomAdoptNoticeInfo objs = (GYBomAdoptNoticeInfo) queryresultMap.get(bsoID);
                GYBomAdoptNoticeTreeObject treeObject = new GYBomAdoptNoticeTreeObject(objs);
                v.addElement(treeObject);
                frame.getBomNoticeListTreePanel().addNodes(v);
                frame.getBomNoticeListTreePanel().setNodeSelected(
                        (GYBomAdoptNoticeTreeObject) v.elementAt(0));
                try {
                    frame.viewNoticeTreeObject();
                } catch (QMException e1) {
                    e1.printStackTrace();
                }
                frame.controller.processAutoCreateCommond();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        //CCEnd SS2
        else
        {
            try
            {
//                System.out.println("22222222222222222222主方法2222222222222222222222");
                System.setProperty("swing.useSystemFontSettings", "0");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                String session = null;
                session = RequestServer.getSessionID("localhost", "7001", "Administrator", "Administrator");
                GYNoticeClientRequestServer server = new GYNoticeClientRequestServer("localhost", "7001", session);
                RequestServerFactory.setRequestServer(server);
                GYBomNoticeMainJFrame frame = new GYBomNoticeMainJFrame();
                frame.setVisible(true);
                
//              bsoID = GYBomAdoptNotice_105737679
//                Vector v = new Vector();
////                String bsoID = "GYBomAdoptNotice_105737679";// 采用单bsoID
//                String bsoID = "GYBomAdoptNotice_143658200";
//              //调用服务方法查询数据
//                Class[] theClass =
//                        {String.class};
//                Object[] theObjs =
//                        {bsoID};
//                GYBomAdoptNoticeInfo objs = (GYBomAdoptNoticeInfo)GYNoticeHelper.requestServer("PersistService", "refreshInfo",
//                                         theClass, theObjs);
////                GYBomAdoptNoticeInfo objs = (GYBomAdoptNoticeInfo) queryresultMap.get(bsoID);
//                GYBomAdoptNoticeTreeObject treeObject = new GYBomAdoptNoticeTreeObject(objs);
//                v.addElement(treeObject);
//                frame.getBomNoticeListTreePanel().addNodes(v);
//                frame.getBomNoticeListTreePanel().setNodeSelected(
//                        (GYBomAdoptNoticeTreeObject) v.elementAt(0));
//                try {
//                    frame.viewNoticeTreeObject();
//                } catch (QMException e1) {
//                    e1.printStackTrace();
//                }
//                frame.controller.processAutoCreateCommond();
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }

    }

    /**
     * 获得采用维护面板
     * @return JPanel
     */
    public JPanel getTaskPanel()
    {
        return this.taskPanel;
    }
    /**
     * 初始化整车BOM
     */
    public ZCBomAdoptNoticeViewPanel initZCNoticeRightPanel()
    {
        rightPanel.removeAll();
        this.taskPanel = new ZCBomAdoptNoticeViewPanel(this);
        rightPanel.add(taskPanel, BorderLayout.CENTER);
        taskPanel.validate();
        taskPanel.requestFocus();
        taskPanel.setVisible(true);
        return (ZCBomAdoptNoticeViewPanel)taskPanel;

    }

//    /**
//     * 初始化采用单页面
//     */
//    public CJAdoptNoticeViewPanel initCJNoticeRightPanel()
//    {
//        rightPanel.removeAll();
//        this.taskPanel = new CJAdoptNoticeViewPanel(this);
//        rightPanel.add(taskPanel, BorderLayout.CENTER);
//        taskPanel.validate();
//        taskPanel.requestFocus();
//        taskPanel.setVisible(true);
//        return (CJAdoptNoticeViewPanel)taskPanel;
//
//    }
    /**
     * 获得主控制类
     * @return
     */
    public MainController getController()
    {
        return this.controller;
    }
    
    //houhf add
    /**
     * 导出整车工艺BOM
     * @author houhf
     * @throws QMException 
     * @throws IOException 
     */
//    private void exportAllBOM() throws IOException, QMException
//    {
//    	String path = getSelectedPath();
//    	if(path != null && path.trim().length()>0)
//    	{
//    		GYBomAdoptNoticeIfc ifc = 
//    			(GYBomAdoptNoticeIfc)noticeTreePanel
//    										.getSelectedObject().getObject();
//    		if(ifc == null)
//    		{
//    			JOptionPane.showMessageDialog(null, "请选择一个发布单!", "提示",
//											JOptionPane.INFORMATION_MESSAGE);
//    			return;
//    		}
//    		exportAllBOM(ifc,path);
//    		JOptionPane.showMessageDialog(null, "导出成功!", "提示",
//											JOptionPane.INFORMATION_MESSAGE);
//    	}
//    	else
//    	{
//    		return;
//    	}
//    }
//    
//    /**
//	 * 向指定的excel文件写发布单下所有工艺BOM数据
//	 * @param path Excel文件全路径
//	 * @param ifc 发布单
//	 * @author houhf
//	 * @throws IOException,QMException 
//	 */
//	private void exportAllBOM(GYBomAdoptNoticeIfc ifc, String path)
//			throws IOException, QMException {
//		//返回的BOM集合
//		Vector<Object[]> result = new Vector<Object[]>();
//		//整合导出BOM信息
//		StringBuffer backBuffer = new StringBuffer();
//		String head = "发布单：" + ifc.getAdoptnoticenumber() + "("
//				+ ifc.getAdoptnoticename() + ")" + " 的工艺BOM清单" + "\n";
//		backBuffer.append(head);
//
//		String table = "级别,编号,名称,版本,数量,制造路线,装配路线,视图,艺准编号,生命周期状态,专用件,虚拟标示,"
//				+ "\n";
//		backBuffer.append(table);
//
//		Class[] theClass = {GYBomAdoptNoticeIfc.class};
//        Object[] obj1 = {ifc};
//        result = (Vector<Object[]>) GYNoticeHelper
//        	.requestServer("GYBomNoticeService","exportAllBOM",theClass,obj1);
//        
////		result = exportAllBOM(ifc);
//		if (result != null && result.size() > 0) {
//			for (int i = 0; i < result.size(); i++) {
//				String value = "";
//				Object obj[] = result.get(i);
//				value = (String) obj[0] + ",";
//				value += (String) obj[1] + ",";
//				value += (String) obj[2] + ",";
//				value += (String) obj[3] + ",";
//				value += (String) obj[4] + ",";
//				value += (String) obj[5] + ",";
//				value += (String) obj[6] + ",";
//				value += (String) obj[7] + ",";
//				value += (String) obj[8] + ",";
//				value += (String) obj[9] + ",";
//				value += (String) obj[10] + ",";
//				value += (String) obj[11] + ",";
//				value += "\n";
//				backBuffer.append(value);
//			}
//		}
//
//		FileWriter fw = new FileWriter(path, false);
//		fw.write(backBuffer.toString());
//		fw.close();
//
//	}
//	
//	/**
//	 * 获得用户本地文件路径
//	 * @return 路径
//	 * @author houhf
//	 */
//    private String getSelectedPath()
//    {
//		FileFilter filter;
//		File selectedFile = null;
////		view.setCursor(Cursor.WAIT_CURSOR);
//		JFileChooser chooser = new JFileChooser();
//		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//		chooser.setMultiSelectionEnabled(false);
//		filter = new TXTFileFilter();
//		if (selectedFile != null) {
//			chooser.setSelectedFile(selectedFile);
//		}
//		chooser.setDialogTitle("输出整车工艺BOM至...");
//		chooser.setFileFilter(filter);
//		//删除系统自带的AcceptAllFileFilter
//		chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
//		//“打开”模式文件选择器中选择了批准按钮还是取消按钮
////		view.setCursor(Cursor.getDefaultCursor());
//		int state = chooser.showSaveDialog(null);
//		if (state == chooser.CANCEL_OPTION)
//		{
//			return null;
//		}
//		//获得选择的文件
//		selectedFile = chooser.getSelectedFile();
//		//如果选择了批准按钮,则获得所选择文件名
//		if (selectedFile != null && state == JFileChooser.APPROVE_OPTION)
//		{
//			//文件格式转换
//			selectedFile = this.translateFile(selectedFile, filter);
//
//			//判断 1 未输入文件名,请输入文件名称  2 指定的路径不存在或不可用 3 文件已存在,请重新指定文件名
//			if (!filter.accept(selectedFile))
//			{
//				JOptionPane.showMessageDialog(null, "路径不存在!", "错误",
//						JOptionPane.ERROR_MESSAGE);
//				return null;
//			}
//			if (selectedFile.exists())
//			{
//				JOptionPane.showMessageDialog
//					(null, "文件已经存在！请选择一个全新文件进行导出操作！", "提示",
//						JOptionPane.INFORMATION_MESSAGE);
//				return null;
//			}
//		}
//
//		return selectedFile.getPath();
//	}
//    
//    /**
//     * 将文件转换成csv格式，这个是照着解放零部件导出做的。至于为什么这么转换不得而知
//     * @param file
//     * @param filter
//     * @return
//     * @author houhf
//     */
//    private File translateFile(File file, FileFilter filter)
//    {
//		String path = file.getPath();
//		if (!path.endsWith(".csv"))
//		{
//			path = path + ".csv";
//		}
//		return new File(path);
//	}
//
//	public class TXTFileFilter extends FileFilter
//	{
//		/**
//		 * 构造文本文件过滤器
//		 */
//		public TXTFileFilter()
//		{
//		}
//
//		/**
//		 * 判断指定的文件是否被本过滤器接受
//		 * @param f 文件
//		 * @return 如果接受，则返回true
//		 */
//		public boolean accept(File f)
//		{
//			boolean accept = f.isDirectory();
//			if (!accept) {
//				String suffix = getSuffix(f);
//				if (suffix != null) {
//					accept = suffix.equals("csv");
//				}
//			}
//			return accept;
//		}
//
//		/**
//		 * 获得本过滤器的描述信息
//		 * @return Text Files(*.csv)
//		 */
//		public String getDescription()
//		{
//			return "Text Files(*.csv)";
//		}
//
//		/**
//		 * 获得指定文件的后缀
//		 * @param f File
//		 * @return 文件的后缀
//		 */
//		private String getSuffix(File f)
//		{
//			String s = f.getPath(), suffix = null;
//			int i = s.lastIndexOf('.');
//			if (i > 0 && i < s.length() - 1)
//			{
//				suffix = s.substring(i + 1).toLowerCase();
//			}
//			return suffix;
//		}
//	}
	//houhf add end
}





