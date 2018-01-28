/**
 * ��Ȩ��һ��������˾����

 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ȥ��������BOm��ѡ��  TD8461 ���� 2014-7-18
 * SS2 �Զ�����BOM������ lishu 2017-5-12
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
 * <p> Title: ����BOM������������ģ�������� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2014 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class GYBomNoticeMainJFrame extends JFrame implements ActionListener
{
    private static final boolean verbose = GYNoticeHelper.VERBOSE;
    JPanel contentPane;
    JMenuBar jMenuBar1 = new JMenuBar();

    /**�����˵��������Ҽ�ɾ��������*/
    private JPopupMenu gyBomNoticeTreePopup = new JPopupMenu();
    private JMenuItem jMenuRightDelete = new JMenuItem("ɾ��");
    private JMenuItem jMenuRightUpdate = new JMenuItem("����");
   
    
   
    /** ϵͳ״̬�� */
    JLabel statusBar = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    /** ���õ�֪ͨ�����༭���鿴 */
    JMenu jMenuNotice = new JMenu();
    JMenu jMenuEdit = new JMenu();
    JMenu jMenuView = new JMenu();

    /** �༭������֪ͨ�������¡�ά��ר�ü��� */
    JMenuItem jMenuDisuseNotice = new JMenuItem();
    JMenuItem jMenuUpdateNotice = new JMenuItem();
    JMenuItem jMenuZhuanYongJian = new JMenuItem();


    //����֪ͨ��
    JMenu jMenuCreateNotice = new JMenu();
    //��������
    JMenuItem jMenuCreateCarload = new JMenuItem();
    //��������
    JMenuItem jMenuCreateCarFrame = new JMenuItem();
    //��������
    JMenuItem jMenuCreateCarBody = new JMenuItem();
   
    //����֪ͨ��
    JMenuItem jMenuSave = new JMenuItem();
    //����֪ͨ��
    JMenuItem jMenuSaveAs = new JMenuItem();
    //��֪ͨ��
    JMenuItem jMenuSearch = new JMenuItem();
    //����BOM
    JMenuItem jMenuExportBOM = new JMenuItem();
    //�鿴BOM���ܲ�Ҫ�ˣ�����ע����houhf
    //�鿴BOM
//    JMenuItem jMenuViewBOM = new JMenuItem();
    
    //��������BOM
    JMenuItem jMenuExportCarloadBOM = new JMenuItem();
    //�鿴����BOM���ܲ�Ҫ������ע����houhf
    //�鿴����BOM
//    JMenuItem jMenuViewCarloadBOM = new JMenuItem();
    
    //�鿴֪ͨ��
    JMenuItem jMenuViewNotice = new JMenuItem();
    
   
    JSplitPane jSplitPane1 = new JSplitPane();
    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel rightPanel = new JPanel();
    GYBomNoticeTreePanel noticeTreePanel = new GYBomNoticeTreePanel();
 
    private ProgressDialog progressDialog;
    MainController controller = new MainController(this);

    /** ����Ƿ�Ĭ����ʾ�鿴���õ����� */
    public static boolean isViewBomAdoptNotice = true;
    //��ҳ���Ҳ���Ϣҳ��
    RightPanel taskPanel = null;
    GridLayout gridLayout1 = new GridLayout();
    TitledBorder titledBorder1;
    BorderLayout borderLayout2 = new BorderLayout();

    /** ���캯��
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
    /**��ʼ���ҽ��˵�
     * ���õ���ɾ��������
     * */
    private void initGYBomNoticeTreePopup()
    {
    	gyBomNoticeTreePopup.add(jMenuRightUpdate);
    	gyBomNoticeTreePopup.add(jMenuRightDelete);

    }
  
    
    /** 
     * ��ʼ������ 
     * */
    private void jbInit()
    {
        contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(borderLayout1);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, (dimension.width-100), (dimension.height-60));
        setTitle("����BOM����������");
        setIconImage(new ImageIcon(getClass().getResource("/images/cappBom_cappDesign.gif")).getImage());
       
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jMenuNotice.setText("������");
        jMenuCreateNotice.setText("�½�");
        jMenuCreateCarload.setText("����������");
        jMenuCreateCarFrame.setText("���ܷ�����");
        jMenuCreateCarBody.setText("��ʻ�ҷ�����");
        jMenuSearch.setText("��");
        jMenuSave.setText("����");
        jMenuSaveAs.setText("���Ϊ");
        
        jMenuEdit.setText("�༭");
        jMenuDisuseNotice.setText("����������");
        jMenuUpdateNotice.setText("����");
        jMenuZhuanYongJian.setText("ά��ר�ü�");

        jMenuView.setText("�鿴");
        jMenuExportBOM.setText("����BOM");
//        jMenuViewBOM.setText("�鿴BOM");
        jMenuExportCarloadBOM.setText("��������BOM");
//        jMenuViewCarloadBOM.setText("�鿴����BOM");
        jMenuViewNotice.setText("�鿴������");
       
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
        jTabbedPane1.add("����BOM������", noticeTreePanel);
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
//            			JOptionPane.showMessageDialog(null, "��ѡ��һ��������!", "��ʾ",
//        											JOptionPane.INFORMATION_MESSAGE);
//            			return;
//            		}
//					exportAllBOM();
//				}
//        		catch (IOException e)
//        		{
//        			JOptionPane.showMessageDialog(null, "����ʧ��!", "��ʾ",
//        					JOptionPane.INFORMATION_MESSAGE);
//					e.printStackTrace();
//					return;
//				}
//        		catch (QMException e)
//        		{
//        			JOptionPane.showMessageDialog(null, "����ʧ��!", "��ʾ",
//        					JOptionPane.INFORMATION_MESSAGE);
//					e.printStackTrace();
//					return;
//				}
//        	}
        	//houhf add end
//        });
//        this.jMenuViewCarloadBOM.addActionListener(rmAction);
        this.jMenuViewNotice.addActionListener(rmAction);
        //�Ҽ�
        this.jMenuRightDelete.addActionListener(rmAction);
        this.jMenuRightUpdate.addActionListener(rmAction);
        initGYBomNoticeTreePopup();

        jSplitPane1.setDividerLocation(300);

        // ���õ���ע�����
        RootListTreeSelectionListener treeSelectListener = new RootListTreeSelectionListener();
        noticeTreePanel.addTreeSelectionListener(treeSelectListener);
        NoticeTreeMouseListener rtml = new NoticeTreeMouseListener();
        noticeTreePanel.addTreeMouseListener(rtml);
        
        //��Ӽ������ڲ��õ����е������Ҽ�����ʾ�����˵���
        noticeTreePanel.addTreeMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)            
			{
            	//�Ҽ�
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
         * Ӧ�ó���رճ���
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
     * ���ڲ��õ�����ѡ��ҵ������������Ҽ����ɿ�ʱ����ʾ�����˵���
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
     * �ر�Ӧ�ó���
     * @param e �����¼�
     */
    private void this_windowClosing(WindowEvent e)
    {
        exit();
    }

    /**
     * �˳�������
     */
    public void exit()
    {
        
        String information = "�Ƿ�Ҫ�˳�";
        String exitDialog = "�˳�";
        
        // ��ʾȷ�϶Ի���
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
                            return; //���û�м��� ֱ�ӷ���
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
     * ��ʼ������
     */
    public void startProgress()
    {
        progressDialog.startProcess();
    }

    /**
     * ����������
     */
    public void stopProgress()
    {
        progressDialog.stopProcess();
    }


    /**
     * ���������ϵİ�ť������.���û�����������ϵİ�ťʱ�ᷢ��һ������,�������� �밴ťͼƬ������һ��
     * @param e
     */
    public void actionPerformed(ActionEvent e)
    {
    }




   


    /**
     * ���������еĲ˵������ڴ�ע�� <p> ���ݵ�ǰ��ѡ��Ĳ˵���ҵ�����ȷ���������ʾ���� </p> <p> Copyright: Copyright (c) 2003 </p> <p> Company: һ������ </p>
     * @author ����
     * @version 1.0
     */
    class RMAction implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            
            Object object = e.getSource();
            if(object == jMenuCreateCarload)//��������������
            {
                controller.processCreateCarloadCommand();
                return;
            }else if(object == jMenuCreateCarFrame)//�������ܷ�����
            {
            	controller.processCreateCarFrameCommand();
            }else if(object == jMenuCreateCarBody)//��������
            {
            	controller.processCreateCarBodyCommand();
            }else if(object == jMenuSearch)//��
            {
            	controller.processSearchNoticeCommand();
            }else if(object == jMenuSave)//����
            {
            	controller.processSaveNoticeCommand();
            }else if(object == jMenuSaveAs)//���
            {
            	controller.processSaveAsNoticeCommand();
            }else if(object == jMenuDisuseNotice)//����
            {
            	controller.processDisuseNoticeCommond();
            }else if(object == jMenuUpdateNotice)//����
            {
            	controller.processUpdateCommond();
            }else if(object == jMenuZhuanYongJian)//ר�ü�
            {
            	controller.processZhuanYongJianCommond();
            }else if(object == jMenuExportBOM)//����BOM
            {
            	controller.processExportBomCommond();
            }
//            else if(object == jMenuViewBOM)//�鿴BOM
//            {
//            	controller.processViewBomCommond();
//            }
            else if(object == jMenuExportCarloadBOM)//��������BOm
            {
            	controller.processExportCarloadBomCommond();
            }
//            else if(object == jMenuViewCarloadBOM)//�鿴����BOM
//            {
//            	controller.processViewCarloadBOMCommond();
//            }
            else if(object == jMenuViewNotice)//�鿴֪ͨ��
            {
            	controller.processViewNoticeCommond();
            }
            else if(object == jMenuRightDelete)//�Ҽ�ɾ��
            {
            	controller.processRightDeleteCommond();
            }
            else if(object == jMenuRightUpdate)//�Ҽ�ɾ��
            {
            	controller.processRightUpdateCommond();
            }

        }
    }

    /**
     * <p> Title: �������� </p> <p> Description:�������� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
     * @author liuming
     * @version 1.0
     */
    class MyMouseListener extends MouseAdapter
    {
        public void mouseEntered(MouseEvent e)
        {
            Object obj = e.getSource();
            // ��ť
            if(obj instanceof JButton)
            {
                JButton button = (JButton)obj;
            }
            // �˵���
            if(obj instanceof QMMenuItem)
            {
                QMMenuItem item = (QMMenuItem)obj;
                // ���˵�����ȥ�����Ƿ�
                String s = item.getExplainText();
                statusBar.setText(s);
            }
            // �˵�
            if(obj instanceof QMMenu)
            {
                QMMenu item = (QMMenu)obj;
                statusBar.setText(item.getExplainText());
            }

        }

        /**
         * ����뿪
         * @param e MouseEvent
         */
        public void mouseExited(MouseEvent e)
        {
            statusBar.setText("Ҫ��ð������������������˵���F1����");
        }
    }

    /**
     * ��ʼ������
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
     * ��ʼ���ܡ���ʻ��
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
     * �ر��Ҳ�������� �˷�������ÿ�ν���ĳһ�˵�����ʱ��������
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
     * ������
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
     * <p> Title:���õ������Ľڵ�ѡ����� </p> <p> �����ڵ��ѡ��ֵ�仯ʱ,���²˵�״̬,�����鿴���õ����� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
     * @author ����
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
     * �鿴������
     * @throws QMException
     */
    public void viewNoticeTreeObject() throws QMException
    {
        GYBomNoticeTreeObject obj = GYBomNoticeMainJFrame.this.noticeTreePanel.getSelectedObject();

        if(taskPanel != null)
        {
            if(taskPanel instanceof ZCBomAdoptNoticeViewPanel)
            {
                // �Ҳ���廹����ʱ  isHaveSave=trueʱִ��
                if(!((ZCBomAdoptNoticeViewPanel)taskPanel).isHaveSave)
                {
                    if(((ZCBomAdoptNoticeViewPanel)taskPanel).getViewMode() != 2)
                    {
                        ((ZCBomAdoptNoticeViewPanel)taskPanel).quit();
                    }
                }
            }else if(taskPanel instanceof CJAdoptNoticeViewPanel)
            {
                // �Ҳ���廹����ʱ  isHaveSave=trueʱִ��
                if(!((CJAdoptNoticeViewPanel)taskPanel).isHaveSave)
                {
                    if(((CJAdoptNoticeViewPanel)taskPanel).getViewMode() != 2)
                    {
                        ((CJAdoptNoticeViewPanel)taskPanel).quit();
                    }
                }
            }
        }
        // ���ѡ�нڵ���BomNoticeTreeObject,�򵯳��鿴����
        if(obj != null && obj instanceof GYBomNoticeTreeObject)
        {
                closeContentPanel();
                GYBomAdoptNoticeIfc tmp = (GYBomAdoptNoticeIfc)obj.getObject();
                statusBar.setText("��ǰѡ�еĲ��õ��� " + tmp.getAdoptnoticenumber());
                controller.viewDefaultBomAdoptNotice(tmp);
            }
        }


    /**
     * ˢ��������
     */
    public void refresh()
    {
        this.invalidate();
        this.doLayout();
        this.repaint();
    }



 





    /**
     * ��ò��õ���
     * @return BomNoticeTreePanel
     */
    public GYBomNoticeTreePanel getBomNoticeListTreePanel()
    {
        return this.noticeTreePanel;
    }




    /**
     * ������
     * @param String[] args
     */
    public static void main(String[] args)
    {
//        System.out.println("��С============="+args.length);
        if(args.length == 3)
        {
//            System.out.println("111111111111111111������111111111111111111111111");
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
//            System.out.println("4444444444444444444������44444444444444444=="+args[3]);
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
                String bsoID = args[3];// ���õ�bsoID
              //���÷��񷽷���ѯ����
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
//                System.out.println("22222222222222222222������2222222222222222222222");
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
////                String bsoID = "GYBomAdoptNotice_105737679";// ���õ�bsoID
//                String bsoID = "GYBomAdoptNotice_143658200";
//              //���÷��񷽷���ѯ����
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
     * ��ò���ά�����
     * @return JPanel
     */
    public JPanel getTaskPanel()
    {
        return this.taskPanel;
    }
    /**
     * ��ʼ������BOM
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
//     * ��ʼ�����õ�ҳ��
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
     * �����������
     * @return
     */
    public MainController getController()
    {
        return this.controller;
    }
    
    //houhf add
    /**
     * ������������BOM
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
//    			JOptionPane.showMessageDialog(null, "��ѡ��һ��������!", "��ʾ",
//											JOptionPane.INFORMATION_MESSAGE);
//    			return;
//    		}
//    		exportAllBOM(ifc,path);
//    		JOptionPane.showMessageDialog(null, "�����ɹ�!", "��ʾ",
//											JOptionPane.INFORMATION_MESSAGE);
//    	}
//    	else
//    	{
//    		return;
//    	}
//    }
//    
//    /**
//	 * ��ָ����excel�ļ�д�����������й���BOM����
//	 * @param path Excel�ļ�ȫ·��
//	 * @param ifc ������
//	 * @author houhf
//	 * @throws IOException,QMException 
//	 */
//	private void exportAllBOM(GYBomAdoptNoticeIfc ifc, String path)
//			throws IOException, QMException {
//		//���ص�BOM����
//		Vector<Object[]> result = new Vector<Object[]>();
//		//���ϵ���BOM��Ϣ
//		StringBuffer backBuffer = new StringBuffer();
//		String head = "��������" + ifc.getAdoptnoticenumber() + "("
//				+ ifc.getAdoptnoticename() + ")" + " �Ĺ���BOM�嵥" + "\n";
//		backBuffer.append(head);
//
//		String table = "����,���,����,�汾,����,����·��,װ��·��,��ͼ,��׼���,��������״̬,ר�ü�,�����ʾ,"
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
//	 * ����û������ļ�·��
//	 * @return ·��
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
//		chooser.setDialogTitle("�����������BOM��...");
//		chooser.setFileFilter(filter);
//		//ɾ��ϵͳ�Դ���AcceptAllFileFilter
//		chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
//		//���򿪡�ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
////		view.setCursor(Cursor.getDefaultCursor());
//		int state = chooser.showSaveDialog(null);
//		if (state == chooser.CANCEL_OPTION)
//		{
//			return null;
//		}
//		//���ѡ����ļ�
//		selectedFile = chooser.getSelectedFile();
//		//���ѡ������׼��ť,������ѡ���ļ���
//		if (selectedFile != null && state == JFileChooser.APPROVE_OPTION)
//		{
//			//�ļ���ʽת��
//			selectedFile = this.translateFile(selectedFile, filter);
//
//			//�ж� 1 δ�����ļ���,�������ļ�����  2 ָ����·�������ڻ򲻿��� 3 �ļ��Ѵ���,������ָ���ļ���
//			if (!filter.accept(selectedFile))
//			{
//				JOptionPane.showMessageDialog(null, "·��������!", "����",
//						JOptionPane.ERROR_MESSAGE);
//				return null;
//			}
//			if (selectedFile.exists())
//			{
//				JOptionPane.showMessageDialog
//					(null, "�ļ��Ѿ����ڣ���ѡ��һ��ȫ���ļ����е���������", "��ʾ",
//						JOptionPane.INFORMATION_MESSAGE);
//				return null;
//			}
//		}
//
//		return selectedFile.getPath();
//	}
//    
//    /**
//     * ���ļ�ת����csv��ʽ����������Ž���㲿���������ġ�����Ϊʲô��ôת�����ö�֪
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
//		 * �����ı��ļ�������
//		 */
//		public TXTFileFilter()
//		{
//		}
//
//		/**
//		 * �ж�ָ�����ļ��Ƿ񱻱�����������
//		 * @param f �ļ�
//		 * @return ������ܣ��򷵻�true
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
//		 * ��ñ���������������Ϣ
//		 * @return Text Files(*.csv)
//		 */
//		public String getDescription()
//		{
//			return "Text Files(*.csv)";
//		}
//
//		/**
//		 * ���ָ���ļ��ĺ�׺
//		 * @param f File
//		 * @return �ļ��ĺ�׺
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





