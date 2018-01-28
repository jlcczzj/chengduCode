/** ���ɳ���TechnicsRegulationsMainJFrame.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1 2009/04/28  ��ѧ��   ԭ�򣺹������������ɹ����ŵĹ���
 *                          ����������������������ɹ����Ź��ܵ����
 *                          ��ע�������¼��ʶΪCRSS-007
 *                          
 *CR2 2009/05/05   ������   ԭ��: �㹤�����Ҽ��󣬳��ֿ�ݲ˵����������뵱ǰ
 *                                ���λ�ò�һ�£���������û������        
 *                          ����: ���Ҽ�ʱ�����ǰ���ڵ��ѡ��,ͨ������ѡ�н�
 *                                �㲢������ݲ˵�.   
 *                                
 *                          ��ע:TD��:"2119"
 *                          
 *  CR3 2009/04/29  ��־��   ԭ�򣺹��չ�̱༭��������Ƴ���ť
 *                          ���������չ�̴ӹ������в�ȥ����ʾ����ɾ����
 *                          ��ע�������¼��ʶΪ"CRSS-012"       
 *                          
 *  CR4 2009/06/02  ������  �μ�:������:v4r3FunctionTest;TD��2235   
 *  CR5 2009/05/31  �촺Ӣ   ԭ�򣺲鿴����ļ�ͼ֮�󣬹رչ��������棬c���ϵ�ͼ���ļ�û��ɾ��
 *                           ����:�˳�����������ʱ���ж������ͼ�ļ�����Ӳ���ϣ�����ɾ��           
 *
 *  CR6 2009/12/29  ������  TD����2676
 *  CR7 2010/04/13  �촺Ӣ  ԭ��:�μ�TD����2253
 *  SS1 ��������Դ�嵥һ���� zhaoqiuying 2013-01-29
 *  SS2 ��ȡ��ǰ�û�,���ո��û�����ʾ�����ļ������Ϣ(��������ʾ,��Ų���ʾ) pante 20130720
 *  SS3 ����������ӹ���һ���� ���� 2014-2-13
 *  SS4 ����������ӹ��������ļ� pante 2014-02-19
 *  SS5 ���������������嵥���͡���λ�����嵥������һ���� liunan 2014-7-28
 *  SS6 ������������󣬽����߼��丨�߷ֿ� pante 2014-09-10
 *  SS7 �������ӹ�װ��ϸ���豸�嵥��ģ���嵥�� guoxiaoliang 2014-08-22
 *  SS8 ���Ӻϲ����չ��� ����� 2014-12-15
 *  SS9 Ϊ�����û���Ϊδѡ���ı��򣬶�ճ������ʱ�����õ�����ճ���Ĺ��ܣ������ա�����ճ���Ŀ�ݼ���Ϊ��Ctrl+P�� liunan 2015-3-30
 *  SS10 �ɶ�������й��򹤲� guoxiaoliang 2016-7-13 
 *  SS11 �ɶ����ṹ�������� guoxiaoliang 2016-7-19
 *  SS12 �ɶ��ؼ�����ɸѡ  guoxiaoliang 2016-8-5
 *  SS13 �ɶ��ɶ����������� guoxiaoliang 2016-8-5
 *  SS14 �ɶ������ҵָ���鹤�պ͹����ά�������޸�  guoxiaoliang 2016-11-23
 *  SS15 ��ݵ���������Ƽƻ� liunan 2016-11-30
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
 * <p>Title: ����ά��������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 * (1)20060926Ѧ���޸ģ��޸ķ��� SearchPT()�����ϲ���ѯ�ӱ������
 * ���⣨2��20081218 �촺Ӣ�޸�       �޸�ԭ���ڹ��չ�̷ʿͻ��Ĺ������ϰ�סCtrl�����ѡ��2�������Ĺ���
 * ����Ҽ�ѡ���ƣ�Ȼ��ճ�������������£���ʱֻ��ճ����ѡ��������е�һ������һ��ѡ����Ǹ���
 * ���⣨3��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤��
 */

public class TechnicsRegulationsMainJFrame extends JFrame implements
        ActionListener, TechnicsViewIfc
{
    private JPanel contentPane;    

   
    /**������*/
    private QMToolBar qmToolBar = new QMToolBar();


    /**״̬��*/
    private JLabel statusBar = new JLabel();

    private BorderLayout borderLayout1 = new BorderLayout();


    /**����ָ����*/
    private JSplitPane jSplitPane = new JSplitPane();
    private JPanel rightJPanel = new JPanel();
    private JPanel leftJPanel = new JPanel();


    /**����������Դ��������ѡ����*/
    private JTabbedPane treeJTabbedPane = new JTabbedPane();


    /**������ť*/
    private JButton searchTreeJButton = new JButton();
    private JLabel leftJLabel = new JLabel();
    private JLabel rightJLabel = new JLabel();


    /**�����������*/
    private TechnicsContentJPanel contentJPanel;

    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();


    /**�˵���*/
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

    //�ҽ���ݲ˵�
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
     * ��ǰ�û�
     */
    public static UserIfc currentUser;


    /**
     * ������
     */
    private MyMouseListener mouselistener = new MyMouseListener();


    /**���������*/
    private TechnicsRegulationsMainController mainController;


    /**������ʾģʽ������ģʽ�����*/
    public final static int UPDATE_MODE = 0;


    /**������ʾģʽ������ģʽ�����*/
    public final static int CREATE_MODE = 1;


    /**������ʾģʽ���鿴ģʽ�����*/
    public final static int VIEW_MODE = 2;


    /**������ʾģʽ�����Ϊģʽ�����*/
    public final static int SAVEAS_MODE = 3;


    /**���������*/
    private TechnicsTreePanel processTreePanel;
    private ResourceTreePanel equipmentTreePanel;
    private ResourceTreePanel toolTreePanel;
    private ResourceTreePanel materialTreePanel;
    private ResourceTreePanel termTreePanel;
    private ResourceTreePanel drawingTreePanel;
    private ProgressDialog progressDialog;

    /**���ڱ����Դ�ļ�·��*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**���ڱ����Դ*/
    protected static ResourceBundle resource = null;


    /**�����˵�*/
    private JPopupMenu popup = new JPopupMenu();


    /**�����˵������ڹ�����*/
    private JPopupMenu technicsTreePopup = new JPopupMenu();


    /**������Ա���*/
   private static boolean verbose = (RemoteProperty.getProperty(
          "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private boolean notExitSystem = false;
    protected Object lock2 = new Object();
    private QMHelpSystem helpSystem;


    /**��������ѡ��Ľڵ�*/
    private CappTreeNode existNode;


    /**
     * ��Ź�������ť�Ĺ�������.��:��������ť��actionCommand,
     * ֵ:��ť�Ĺ�������
     */
    HashMap toolFunction = new HashMap();
    /**
     * ���캯��
     */
    public TechnicsRegulationsMainJFrame()
    {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);


        try
        {
              //20070118Ѧ���޸ģ���ӡ�Ѿ���ʹ��jview
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
     * ���캯��
     * @param flag ���flag = true,������˳�ʱ��ϵͳ���˳�
     */
    public TechnicsRegulationsMainJFrame(boolean flag)
    {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        try
        {
             //20070118Ѧ���޸ģ���ӡ�Ѿ���ʹ��jview
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
     * �����ʼ��
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
        //{{��ʼ��������
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
        //���ڿ��Ʋ˵���ʾ״̬
        processTreePanel.addTreeSelectionListener(new TreeSelectionListener()
        {
            public void valueChanged(TreeSelectionEvent e)
            {
//                long d1=System.currentTimeMillis();
                CappTreeNode node = ((CappTree) e.getSource()).getSelectedNode();
                //���ѡ�нڵ���BusinessTreeObject,��ˢ��
                if (node != null &&
                    node.getObject() instanceof BusinessTreeObject)
                {
                    if (!TechnicsRegulationsMainJFrame.this.closeContentPanel())
                    {
                        return;
                    }
                    try
                    {
                    	//���⣨3��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤�� begin
                    	if( contentJPanel.techStepIsShowing())
                    	{
                    		TechnicsRegulationsMainJFrame.this.setStepJpanel(false);
                    	}
                    	if(contentJPanel.techPaceIsShowing())
                    	{
                    		TechnicsRegulationsMainJFrame.this.setPaceJpanel(false);
                    	}
                    	//���⣨3��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤�� end
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
                    //����״̬��
                    statusBar.setText(ParentJPanel.getIdentity(node.getObject().
                            getObject()));
                }
                else
                {
                    setNullMenu();
                }
//                 long d2=System.currentTimeMillis();
//                 System.out.println("�鿴ʱ��="+(d2-d1));
            }
        });

        //��Ӽ������ڹ������е������Ҽ�����ʾ�����˵���
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
        //��Ӽ��������豸����ѡ��ʱ������������Ҽ������԰��豸��ӵ��������С�
        equipmentTreePanel.addTreeMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                if (verbose)
                {
                    System.out.println(
                            "--------------------����豸��������------------------");
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


            //���ڿ��ơ����ѡ�ж��󡱲˵�
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
        //��Ӽ��������������ѡ��ʱ������������Ҽ������԰������ӵ��������С�
        partTreeJPanel.addMouseListener(new MouseAdapter()
        {
            public void mouseReleased(MouseEvent e)
            {
                if (verbose)
                {
                    System.out.println(
                            "--------------------��������������------------------");
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


            //���ڿ��ơ����ѡ�ж��󡱲˵�
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
        //��Ӽ���������װ����ѡ��ʱ������������Ҽ������԰ѹ�װ��ӵ��������С�
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


            //���ڿ��ơ����ѡ�ж��󡱲˵�
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
        //��Ӽ����������϶���ѡ��ʱ������������Ҽ������԰Ѳ�����ӵ��������С�
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


            //���ڿ��ơ����ѡ�ж��󡱲˵�
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
        //��Ӽ��������������ѡ��ʱ������������Ҽ������԰�������ӵ�ָ���ı��С�
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
        //��Ӽ���������ͼ����ѡ��ʱ������������Ҽ��������½���ͼ����ӹ��򣨲����С�
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


            //���ڿ��ơ����ѡ�ж��󡱲˵�
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

        //��Ӽ�������ѡ��������������searchTreeJButton����ѡ����Դ��������ʾsearchTreeJButton��
        //�����ò˵�
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
                            //��Դ����ѡ�еĽڵ��ǲ���
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
                            //��Դ����ѡ�еĽڵ����豸��װ���ͼ
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
        setTitle("���չ�̹�����");
        setIconImage(new ImageIcon(getClass().getResource(
                "/images/technics.gif")).getImage());
        setNullMenu();

        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        jMenuFile.setDoubleBuffered(false);
        jMenuFile.setMnemonic('F');
        jMenuFile.setText("�ļ�(F)");
        jMenuFileExit.setText("�˳�(X)");
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
        jMenuHelp.setText("����(H)");
        jMenuHelpAbout.setMnemonic('A');
        jMenuHelpAbout.setText("����(A)");
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
        searchTreeJButton.setText("����(T)");
        searchTreeJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                searchTreeJButton_actionPerformed(e);
            }
        });
        leftJLabel.setBorder(BorderFactory.createEtchedBorder());
        leftJLabel.setToolTipText("");
        leftJLabel.setText("����������Դ������");
        rightJLabel.setBorder(BorderFactory.createEtchedBorder());
        rightJLabel.setText("��������");
        rightJPanel.setLayout(gridBagLayout2);
        contentJPanel.setEnabled(true);
        contentJPanel.setBorder(BorderFactory.createEtchedBorder());
        contentJPanel.setDebugGraphicsOptions(0);

        jSplitPane.setResizeWeight(1.0);

        jMenuFileCollect.setText("�ϲ�(U)");
//        CCBegin SS8
        jMenuCTFileCollect.setText("���ع��պϲ�");
//        CCEnd SS8
        //jMenuFileImport.setText("����");
        jMenuFileExport.setText("����(R)");
        jMenuFileRefresh.setText("ˢ��(E)");
        jMenuFileCreate.setActionCommand("�½�(N)");
        jMenuFileCreate.setMnemonic('N');
        jMenuFileCreate.setText("�½�(N)");
        jMenuCreateTechnics.setText("��������Ϣ");
        jMenuCreateStep.setText("����");
        jMenuCreatePace.setText("����...");
        jMenuSelect.setMnemonic('P');
        jMenuSelect.setText("ѡ��(P)");
        jMenuSelectView.setText("�鿴(V)");
        jMenuSelectUpdate.setText("����(U)");
        jMenuSelectFormStepNum.setText("�������ɹ����(R)");
        //CR1 begin
        jMenuSelectFormPaceNum.setText("�������ɹ�����(B)");
        //CR1 end
        jMenuSelectBrowse.setText("��ӡԤ��(Y)");
        jMenuSelectChangeLocation.setText("���Ĵ洢λ��(L)");
        jMenuSelectRename.setText("������(M)");
        jMenuSelectSaveAs.setText("���Ϊ(G)");
        jMenuSelectUseTech.setText("Ӧ�õ��͹���(H)");
        jMenuSelectUseStep.setText("Ӧ�õ��͹���(I)");
        jMenuSelectMadeTech.setText("���ɵ��͹���(J)");
        jMenuSelectMadeStep.setText("���ɵ��͹���(K)");
        jMenuSelectCopy.setText("����(C)");
        jMenuSelectCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke('C',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuSelectPaste.setText("ճ��(P)");
        //CCBegin SS9
        //jMenuSelectPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke('V',
        jMenuSelectPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke('P',
        //CCEnd SS9
                java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuVersion.setMnemonic('V');
        jMenuVersion.setText("�汾(V)");
        jMenuVersionCheckIn.setText("����(I)");
        jMenuVersionCheckIn.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                'R', java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuVersionCheckOut.setText("���(O)");
        jMenuVersionCheckOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                'T', java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuVersionCancel.setText("�������(U)");
        jMenuVersionCancel.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                'Z', java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuVersionRevise.setText("�޶�(R)");
        jMenuVersionRevise.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                'E', java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuVersionVersion.setText("�鿴�汾��ʷ(N)");
        jMenuVersionIteration.setText("�鿴������ʷ(T)");
        jMenuLifeCycle.setMnemonic('L');
        jMenuLifeCycle.setText("��������(L)");
        jMenuSetLifeCycleState.setMnemonic('S');
        jMenuSetLifeCycleState.setText("����ָ����������״̬(S)");
        jMenuLifeCycleSelect.setText("����ָ����������(L)");
        jMenuLifeCycleView.setText("�鿴����������ʷ(H)");
        //2008.03.09 �������,������Ŀ�顱��Ϊ�������顱
        jMenuLifeCycleGroup.setText("����ָ��������(P)");
        //end mario
        //jMenuIntellect.setText("���ܹ���");
        jMenuIntellectPart.setText("����幤������");
        jMenuIntellectRoot.setText("�ӹ�·�߹�������");
        //CCBegin SS1
        if(isBsxGroupUser()){
        	jMenuSchedule.setMnemonic('M');
        	jMenuSchedule.setText("���������ļ�(M)");
        	jMItemjiaju.setText("�о���ϸ��");
        	jMItemwanneng.setText("���ܹ����嵥");
        	//CCBegin SS5
        	jMItemwannengliangju.setText("���������嵥");
        	jMItemgongweiqiju.setText("��λ�����嵥");
        	//CCEnd SS5
        	jMItemequip.setText("�豸�嵥");
        	jMItemmoju.setText("ĥ��һ����");
        	jMItemdaoju.setText("����һ����");
        	jMItemjiafuju.setText("�и���һ����");
        	jMItemzhuangpei.setText("װ�乤��һ����");
        }
        //CCBegin SS4
        if(getUserFromCompany().equals("zczx")){
        	jMenuSchedule.setMnemonic('M');
        	jMenuSchedule.setText("���������ļ�(M)");
        	jMItemzcjiaju.setText("��ݼо���ϸ��");
        	jMItemzcwangong.setText("������ܹ�����ϸ��");
        	jMItemzcwanliang.setText("�������������ϸ��");
        	jMItemzcjianju.setText("��ݼ����ϸ��");
        	jMItemzcsb.setText("����豸�嵥");
        	jMItemzcmoju.setText("���ĥ��һ����");
        	jMItemzcdaoju.setText("��ݵ���һ����");
        	//        	CCBegin SS6
        	jMItemzcdaofuju.setText("��ݵ�����һ����");
//        	CCEnd SS6
        	jMItemzcjiafuju.setText("��ݼи���һ����");
        	jMItemzcliangju.setText("�������һ����");
        	jMItemzcjianfuju.setText("��ݼ츨��һ����");
        }
      //CCEnd SS4
            //CCBegin SS7
        if(getUserFromCompany().equals("ct")){
        	jMenuSchedule.setMnemonic('M');
        	jMenuSchedule.setText("���������ļ�(M)");
        	jMItemCtTool.setText("���ع�װ��ϸ��");
        	jMItemCtSheBei.setText("�����豸�嵥");
        	jMItemCtMoJu.setText("����ģ���嵥");
        	
        }
      //CCEnd SS7
        //CCEnd SS1
        jMenuSearch.setMnemonic('S');
        jMenuSearch.setText("����(S)");
        jMenuSearchEquip.setText("���豸��������(E)");
        jMenuSearchTool.setText("����װ��������(T)");
        jMenuSearchMaterial.setText("��������������(M)");
        jMenuSearchTechnics.setText("�������չ��(S)");
        jMenuSearchTechnics.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                'F', java.awt.event.KeyEvent.CTRL_MASK, false));
        jMenuHelpManage.setText("���չ�̹���");
        jMenuHelpManage.setEnabled(false);
        jMenuSelectDelete.setText("ɾ��(D)");
        jMenuSelectDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke('D',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        //Begin CR3
        jMenuSelectMoveOut.setText("�Ƴ�(K)");  
        jMenuSelectMoveOut.setAccelerator(javax.swing.KeyStroke.getKeyStroke('K',
                java.awt.event.KeyEvent.CTRL_MASK, false));
        //End CR3 
      //CCBegin SS11
        jMenuStructSearchTechnics.setText("�ṹ�������չ��");
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
        jMenuAddObject.setText("���ѡ�ж���(A)");
        jMenuConfigCrit.setActionCommand("���ù���");  
        jMenuConfigCrit.setMnemonic('C');
        jMenuConfigCrit.setText("���ù淶(C)");
        jMenuHelpItem.setMinimumSize(new Dimension(0, 0));
        jMenuHelpItem.setMnemonic('P');
        jMenuHelpItem.setText("���չ����Ϣ������(P)");
        jMenuHelpItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                KeyEvent.VK_F1, 0));
        treeJTabbedPane.setBorder(BorderFactory.createEtchedBorder());
        jMenuExportAll.setMinimumSize(new Dimension(0, 0));
        jMenuExportAll.setMnemonic('P');
        jMenuExportAll.setText("����ȫ������(P)");
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
        treeJTabbedPane.add(partTreeJPanel, "�㲿��");

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

        //{{ע�����
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

        //������ť��������,����״̬��
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

        //����״̬��
        statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "default_status", null));
        progressDialog = new ProgressDialog(this);
    } //End jbInit()


    /**
     * ������Ϣ���ػ�
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
            jMenuCTFileCollect1.setText("���ع��պϲ�");
            jMenuCTFileCollect.setText("���ع��պϲ�");
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
            jMenuSelectExport.setText("����");
            //CCEnd SS15
            
            //CCBegin SS10
            
            jMenuCheckAll.setText("������й��򹤲�");
            
            //CCEnd SS10
            
            //CCBegin SS12
            
            jMenuFindMainStep.setText("�ؼ�����ɸѡ");
            //CCEnd SS12
            
            //CCBegin SS11
            
            jMenuStructSearchTechnics.setText("�ṹ�������չ��");
            
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
     * ���ý������ʾλ��
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
     * ���ظ��෽��
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        setViewLocation();
        super.setVisible(flag);
    }


    /**
     * ���˳����˵��Ķ����¼�����
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
                	//20060323Ѧ��ɾ��
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
     * �����ڡ��˵��Ķ����¼�����
     * @param e
     */
    public void jMenuHelpAbout_actionPerformed(ActionEvent e)
    {
//        TechnicsRegulationsMainJFrame_AboutBox dlg = new
//                TechnicsRegulationsMainJFrame_AboutBox(this);
        //modify by wangh on 20070611(���üܹ������·������ڵ������ڽ���)
        IntroduceDialog dlg = new IntroduceDialog(this,"���չ�̹�����");
        dlg.setVisible();
        //dlg.setModal(true);
        //dlg.pack();
        //dlg.setVisible(true);
    }


    /**
     * ���������˵��Ķ����¼�����
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
     * ���������ϵİ�ť������.���û�����������ϵİ�ťʱ�ᷢ��һ������,��������
     * �밴ťͼƬ������һ��
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
        else if (actionevent.getActionCommand().equals("term")) //����
        {
            //processCommitCommand();
        }
        else if (actionevent.getActionCommand().equals("sign")) //����
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
     * ���½���(ģ�ͷ����仯ʱ֪ͨ���棬�ú���������)
     * ���ý�����¼�������
     * @param observable ���۲��߶���
     * @param obj ���۲�������Ĳ���
     */
    public void update(Observable observable, Object obj)
    {
    }


    /**
     * ���������еĲ˵������ڴ�ע��
     * <p>���ݵ�ǰ��ѡ��Ĳ˵���ҵ�����ȷ���������ʾ����</p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: һ������</p>
     * @author ����
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

            if (object == jMenuCreateTechnics || object == jMenuCreateTechnics1) //�½���������Ϣ
            {
                mainController.processCreateTechnicsCommand();
            }
            else if (object == jMenuCreateStep || object == jMenuCreateStep1) //�½�����
            {
                mainController.processCreateProcedureStepCommand();
            }
            else if (object == jMenuCreatePace || object == jMenuCreatePace1) //�½�����
            {
                mainController.processCreateProcedurePaceCommand();
            }
            else if (object == jMenuFileCollect || object == jMenuFileCollect1) //�ϲ�
            {
                mainController.processCollectTechnicsCommand();
            }
//            CCBegin SS8
            else if (object == jMenuCTFileCollect || object == jMenuCTFileCollect1) //�ϲ�
            {
                mainController.processCTCollectTechnicsCommand();
            }
//            CCEnd SS8
            /* else if ( object == jMenuFileImport )                             //����
             {
               mainController.processImportCommond();
             }*/
            else if (object == jMenuFileExport) //����
            {
                mainController.processExportCommond();
            }
            else if (object == jMenuExportAll) //����ȫ������
            {
                mainController.processExportAllCommond();
            }
            else if (object == jMenuFileRefresh) //ˢ��
            {
                mainController.processRefreshCommand();
            }

            else if (object == jMenuSelectView || object == jMenuSelectView1) //�鿴
            {
                mainController.processViewCommand();
            }
            else if (object == jMenuSelectUpdate ||
                     object == jMenuSelectUpdate1) //����
            {
                mainController.processUpdateCommand();
            }
            else if (object == jMenuSelectDelete ||
                     object == jMenuSelectDelete1) //ɾ��
            {
                mainController.processDeleteCommand();
            }
            //Begin CR3
            else if (object == jMenuSelectMoveOut ||
                    object == jMenuSelectMoveOut1) //�Ƴ�
	        {
	            mainController.moveOutQMTechnics();
	        }//End CR3
            else if (object == jMenuSelectFormStepNum) //�������ɹ����
            {
                mainController.processFormNewStepnumCommand();
            }
            //CR1 begin
			else if (object == jMenuSelectFormPaceNum) // �������ɹ�����
			{
				mainController.processFormNewPacenumCommand();
			}
            //CR1 end  
            else if (object == jMenuSelectBrowse ||
                     object == jMenuSelectBrowse1) //��ӡԤ��
            {
                mainController.processPrintBrowseCommand();
            }
            else if (object == jMenuSelectChangeLocation) //���Ĵ洢λ��
            {
                mainController.processChangeLocationCommand();
            }
            else if (object == jMenuSelectSaveAs ||
                     object == jMenuSelectSaveAs1) //���Ϊ
            {
                mainController.processSaveAsCommand();
            }
            else if (object == jMenuSelectRename) //������
            {
                mainController.processRenameCommand();
            }
            else if (object == jMenuSelectUseTech) //Ӧ�õ��͹���
            {
                mainController.processUsageModelTechnicsCommand();
            }
            else if (object == jMenuSelectUseStep) //Ӧ�õ��͹���
            {
                mainController.processUsageModelProcedureCommand();
            }
            else if (object == jMenuSelectMadeTech) //���ɵ��͹���
            {
                mainController.processBuildModelTechnicsCommand();
            }
            else if (object == jMenuSelectMadeStep) //���ɵ��͹���
            {
                mainController.processBuildModelProcedureCommand();
            }
            else if (object == jMenuSelectCopy || object == jMenuSelectCopy1) //����
            {
                mainController.processCopyCommand();
            }
            else if (object == jMenuSelectPaste || object == jMenuSelectPaste1) //ճ��
            {
                mainController.processPasteCommand();
            }

            else if (object == jMenuVersionCheckIn) //����
            {
                mainController.processCheckInCommand();
            }
            else if (object == jMenuVersionCheckOut) //���
            {
                mainController.processCheckOutCommand();
            }
            else if (object == jMenuVersionCancel) //�������
            {
                mainController.processCancelCheckOutCommand();
            }
            else if (object == jMenuVersionRevise) //�޶�
            {
                mainController.processReviseCommand();
            }
            else if (object == jMenuVersionVersion) //�鿴�汾��ʷ
            {
                mainController.processViewVersionHistoryCommand();
            }
            else if (object == jMenuVersionIteration) //�鿴������ʷ
            {
                mainController.processViewIterationHistoryCommand();
            }
            else if (object == jMenuSetLifeCycleState) //����ָ����������״̬
            {
                mainController.processSetLifeCycleStateCommand();
            }
            else if (object == jMenuLifeCycleSelect) //����ָ����������
            {
                mainController.processAfreshAppointLifecycleCommand();
            }
            else if (object == jMenuLifeCycleView) //�鿴����������ʷ
            {
                mainController.processViewLifecycleHistoryCommand();
            }
            else if (object == jMenuLifeCycleGroup) //����ָ��������
            {
                mainController.processAfreshAppointProjectCommand();
            }

            else if (object == jMenuIntellectPart) //����幤������
            {
                mainController.processPartCappDeriveCommand();
            }
            else if (object == jMenuIntellectRoot) //�ӹ�·�߹�������
            {
                mainController.processMachineCappDeriveCommand();
            }
            else if (object == jMenuConfigCrit) //���ù淶
            {
                mainController.processConfigureCriterionCommand();
            }
            else if (object == jMenuSearchEquip) //���豸��������
            {
                mainController.processSearchInEquipmentCommand();
            }
            else if (object == jMenuSearchTool) //����װ��������
            {
                mainController.processSearchInToolsCommand();
            }
            else if (object == jMenuSearchMaterial) //��������������
            {
                mainController.processSearchInMaterialCommand();
            }
            else if (object == jMenuSearchTechnics) //�������չ��
            {
                mainController.processSearchTechnicsCommand();
            }
            //CCBegin SS11
            
            else if (object == jMenuStructSearchTechnics) { //�������չ��
                mainController.processStructSearchTechnicsCommand();
              }
            //CCEnd SS11

            else if (object == jMenuHelpManage) //���չ�̹���
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
     * ��ô�������ѡ��Ľڵ㡣
     * @return ��ѡ��Ľڵ�(���տ������򡢹�������ǩ�ڵ�)
     */
    public CappTreeNode getSelectedNode()
    {
        return processTreePanel.getSelectedNode();
    }


    /**
     * ��ô�������ѡ������нڵ㡣
     * @return ��ѡ��Ľڵ�(���տ������򡢹�������ǩ�ڵ�)
     * ���⣨2��20081218 �촺Ӣ�޸�       �޸�ԭ���ڹ��չ�̷ʿͻ��Ĺ������ϰ�סCtrl�����ѡ��2�������Ĺ���
     * ����Ҽ�ѡ���ƣ�Ȼ��ճ�������������£���ʱֻ��ճ����ѡ��������е�һ������һ��ѡ����Ǹ���
     */
    public CappTreeNode[] getSelectedNodes()
    {
        return processTreePanel.getSelectedNodes();
    }
    
    
    /**
     * ��ô�������ѡ��Ĺ��򣨻򹤲����ڵ�ģ��������տ��ڵ�
     * @return ���������տ��ڵ�
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
     * ���ָ���ڵ�ģ��������տ��ڵ�
     * @param currentNode ָ���ڵ�
     * @return ���������տ��ڵ�
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
     * ���µ�����ȥ���ѡ�ж���,����õĶ��󸶸�existNode
     * @return ��ѡ���ҵ�����(���տ������򡢹�������ǩ�ڵ�)
     */
    public CappTreeObject getSelectedObject()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.getSelectedObject() begin...");
        }
        CappTreeObject cappTreeObject = null;
        CappTreeNode node = null;
        //�ڵ�
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
     * ���ý���ģʽ�����տ�������򹤲���
     * @param optionMode �ɲ˵������Ľ�����ʾ״̬��ֻ���ڲ鿴�����Ϊ״̬���½��͸���״̬���д���
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
            	if(type.equals("�ɶ������ҵָ���鹤��")){
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
     * ������ʾ���ɵ��͹��գ�����͹��򣩽���
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
     * ����������(TechnicsContentJPanel)
     * @return TechnicsContentJPanel
     */
    public TechnicsContentJPanel getContentJPanel()
    {
        return contentJPanel;
    }


    /**
     * ��ù�������ʾ���
     * @return  ProcessTreePanel
     */
    public TechnicsTreePanel getProcessTreePanel()
    {
        return processTreePanel;
    }


    /**
     * ˢ��������
     */
    public void refresh()
    {
        validate();
        doLayout();
        repaint();
    }


    /**
     * �رչ����������
     * �˷�������ÿ�ν���ĳһ�˵�����ʱ��������
     * @return ��������֮ǰ�Ƿ�ִ���˱�����������ִ���˱��棬�򷵻��档
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
    
    
    //���⣨3��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤�� begin
    public void  setStepJpanel(boolean flag)
    {
    	contentJPanel.setStepJpanel(flag);
    }
    public void  setPaceJpanel(boolean flag)
    {
    	contentJPanel.setPaceJpanel(flag);
    }
    //���⣨3��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤�� end
    
    
    public void setMenuItemEnable(boolean flag, String menuname)
    {
        qmToolBar.setEnabled(menuname, flag);
    }


    /**
     * ������ȡ���������ȷ���˵������Ч��
     * @param flag�Ƿ��ڸ����ڸ������ϼ�
     * @param obj
     */
    public void enableMenuItems(CappTreeObject obj, boolean flag)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRegulationsMainJFrame.enableMenuItems() begin...");
            //�ڹ�������û��ѡ�����ʱ�˵�����Ч��
        }
        if (obj == null)
        {
            setNullMenu();
        }
        //�ڹ�������ѡ�����Ϊ���տ�ʱ�˵�����Ч��
        else if (obj instanceof TechnicsTreeObject)
        {
            setTechnicsMenu((TechnicsTreeObject) obj, flag);
        }
        //�ڹ�������ѡ�����Ϊ����ʱ�˵�����Ч��
        else if (obj instanceof StepTreeObject)
        {
            setProcedureStepMenu((StepTreeObject) obj, flag);
        }
        //�ڹ�������ѡ�����Ϊ����ʱ�˵�����Ч��
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
     * ����"����,���,�������,�޶�"�˵����qmToolBar����Ч��
     * @param   checkOutflag ����"���"��"�޶�"����Ч��.
     * @param   otherFlag ����"����"��"�������"�˵������Ч��
     * @author ������  CR4
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
     * �����ڹ�������û��ѡ�����ʱ�˵�����Ч��
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
     * �����ڹ�������ѡ�����Ϊ���տ�ʱ�˵�����Ч��
     * @param obj TechnicsTreeObject ����������
     * @param flag �Ƿ��ڸ����ڸ������ϼ�
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
            //c/i״̬
            else
            {
                jMenuLifeCycleSelect.setEnabled(true);
                jMenuVersionCancel.setEnabled(false);
                qmToolBar.setEnabled("public_repealObj", false);
                //�ڹ������ϼ�
                if (!flag)
                {
                    jMenuSelectChangeLocation.setEnabled(true);
                    jMenuVersionCheckIn.setEnabled(false);
                    qmToolBar.setEnabled("public_checkInObj", false);
                    jMenuVersionCheckOut.setEnabled(true);
                    qmToolBar.setEnabled("public_checkOutObj", true);
                    jMenuVersionRevise.setEnabled(true);
                    jMenuSetLifeCycleState.setEnabled(true);
                    //���ж�Ȩ�޵�:
                    jMenuSelectDelete.setEnabled(true);
                    jMenuSelectDelete1.setEnabled(true);
                    qmToolBar.setEnabled("public_deleteObj", true);

                }
                //�ڸ������ϼ�
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
     * �����ڹ�������ѡ�����Ϊ����ʱ�˵�����Ч��
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
        //��c/o
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
            //�����Խ������Ĺ���
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
            //�ж�Ȩ��
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
     * �����ڹ�������ѡ�����Ϊ����ʱ�˵�����Ч��
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
        
        //��c/o
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
            //�ж�Ȩ��
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
     * ΪBom�������ṩ�ķ������Ѵ�BOM��ѡ��Ĺ��տ����ݹ������͹��ڹ������ı�ǩ�ڵ���
     * @param info ��BOM��ѡ��Ĺ��տ�ֵ����
     */
    public void addBomTechnics(QMFawTechnicsInfo info)
    {
        TechnicsTreeObject treeObject = new TechnicsTreeObject(info);
        //��treeObject�ҵ���������
        getProcessTreePanel().addProcess(treeObject);
    }


    /**
     *��ʼ����ʹ�õ���Դ����Ϣ��
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
     * �����Դ��Ϣ
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
     * ��ð�ť��Ϣ��ʾ����,����������
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
        //��������ť�Ĺ�����������toolFunction��
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
     * �����Դ�ļ��е�ֵ
     * @param rb ResourceBundle ��Դ�ļ�
     * @param key String ��
     * @return String[] ������ÿ��Ԫ����ֵ����","�ָ��ÿһ��
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
                    System.out.println("���չ��������:" + "args[0]=" + args[0] +
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
     * ��ӵ����˵���
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


    /**��ʼ������������ϵ��ҽ��˵�*/
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
     * <p>����ʽ�˵���</p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company: һ������</p>
     * @author ����
     * @version 1.0
     */
    class AddAction extends AbstractAction
    {
        /**
         * ���캯��
         * @param name �˵������ʾ����
         */
        public AddAction(String name)
        {
            super(name);
        }


        /**
         * �Ѵ���Դ��ѡ�����Դ������ӵ���Ӧ�Ĺ����б���
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
     * ��������ѡ��ҵ������������Ҽ����ɿ�ʱ����ʾ�����˵���
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
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.resourceTreePanel_mouseReleased end...�����˵�");
        }
    }


    /**
     * ���ڹ�������ѡ��ҵ������������Ҽ����ɿ�ʱ����ʾ�����˵���
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
            System.out.println("cappclients.capp.view.TechnicsRegulationsMainJFrame.processTreePanel_mouseReleased end...�����˵�");
        }
    }


    /**
     * ���õ����˵�����ʾ״̬
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
     * ������ڵ����
     * @return ���ڵ����
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
        //�㲿��
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
     * ��õ�ǰ��ѡ�е���
     * @return ���������豸������װ��������������ͼ�����������
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
     * ������ǰ���ջ���Դ
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
     * ���²�չ��ָ���Ĺ������ڵ�,
     * @param info ���ն��󣨹��տ������򡢹������ɣ�
     * @param parentTechnicsID  �ù��սڵ������Ĺ��տ�ͷ��BsoID�����ָ���Ľڵ�infoΪ��
     * �տ�����parentTechnicsID����Ϊ��
     * @return ���º���½ڵ�
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
     * ����ʱ����ָ�����¹��򣨲������ڵ���ڹ�������
     * @param parentNode ���ڵ�
     * @param info ���򣨲�������
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
     * ���ʱ�����ָ���Ĺ��տ�������������������
     * @param info
     * @param flag ���flag = true�������ͼ��
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
     * ���ʱ�����ָ���Ĺ��򣨲����ڵ㣨�����������ڵ���
     * @param parentNode ���ڵ�
     * @param info
     * @param flag ���flag = true�������ͼ��
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
     * ������ʱ��ɾ��ָ���Ĺ������ڵ�
     * @param info ָ���Ķ���
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
     * ����ָ���Ķ����������ڵ�
     * @param obj ָ���Ķ���
     * @return ��������ڵ�
     */
    public CappTreeNode findNode(CappTreeObject obj)
    {
        return getProcessTreePanel().findNode(obj);
    }


    /**
     * ���ָ���ڵ�����д��ڼ���״̬�ĸ��ڵ�
     * @param currentNode  ָ���ڵ�
     * @return �ڵ㼯��
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
                   CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc) obj) ) //�ڹ������ϼ���
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
     * ��õ�ǰ��ѡ����������д��ڼ���״̬�ĸ��ڵ�
     * @param currentNode  ָ���ڵ�
     * @return �ڵ㼯��
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
                   CheckInOutCappTaskLogic.isCheckInAndInVault((WorkableIfc) obj)) //�ڹ������ϼ���
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
     *�����豸
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
     * ������װ
     */
    private void SearchTL()
    {
        if (verbose)
        {
            System.out.println(" ooooooooooo  ������װoooooooooo");
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
     * ��������
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
     * ������ͼ
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
     * ������������
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
     * �����㲿��
     */
    private void SearchPT()
    {
        String title = QMMessage.getLocalizedMessage(
                "com.faw_qm.part.client.main.util.QMProductManagerRB",
                QMProductManagerRB.GENENAL_SEARCH, null);
        CappChooser d = new CappChooser("QMPartMaster", title, this);
        //20060926Ѧ���޸ģ����ϲ���ѯ�ӱ������
        d.setChildQuery(false);
        d.addListener(new CappQueryListener()
        {
            public void queryEvent(CappQueryEvent e)
            {
                if (e.getType().equals(CappQueryEvent.COMMAND))
                {
                    //ȷ��
                    if (e.getCommand().equals(CappQuery.OkCMD))
                    {
                        jMenuConfigCrit.setEnabled(false);
                        CappChooser c = (CappChooser) e.getSource();
                        //���ѡ����������
                        BaseValueIfc[] objs = (BaseValueIfc[]) c.
                                              getSelectedDetails();
                        //����һ��paster����
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

        //������������
        d.setVisible(true);
    }


    /**
     * ��ѡ�еĹ��տ��ҵ�����
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
     * <p>Title:��ӹ����ֳ� </p>
     * <p>Description:�������Ĺ���������ӵ����� </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: һ������</p>
     * @author Ѧ��
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
                //�Ѵ����°汾�Ĺ��տ������Ʒ�ṹ����
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
     * ��������
     * @return PartTreePanel �����
     */
    public PartTreePanel getPartTreePanel()
    {
        return partTreeJPanel;
    }


    /**
     * �����Դ�ļ�����Ϣ�ķ�װ����
     * @param s String ��Դ�ļ��еļ�
     * @param obj ���������
     * @return String ��Դ�ļ��е���Ϣ
     */
    private String getMessage(String s, Object[] obj)
    {
        return QMMessage.getLocalizedMessage(RESOURCE, s, obj);
    }


    /**
     * �����Դ�ļ�����Ϣ�ķ�װ����
     * @param s String ��Դ�ļ��еļ�
     * @return String ��Դ�ļ��е���Ϣ
     */
    private String getMessage(String s)
    {
        return QMMessage.getLocalizedMessage(RESOURCE, s, null);
    }


    /**
     * ��ʼ���˵���˵���
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
        jMenuCTFileCollect = new QMMenuItem("���ع��պϲ�", mouselistener);
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
        jMenuCheckAll=new QMMenuItem("������й��򹤲�",mouselistener);
        //CCEnd SS10
        
        //CCBegin SS12
        jMenuFindMainStep=new QMMenuItem("�ؼ�����ɸѡ",mouselistener);
        //CCEnd SS12
        
        //CCBegin SS11
        jMenuStructSearchTechnics = new QMMenuItem("�ṹ�������չ��", mouselistener);
        //CCEnd SS11
        //CCBegin SS1
        try {
			if(isBsxGroupUser()){
				jMenuSchedule = new JMenu();
				jMItemjiaju = new QMMenuItem("�о���ϸ��", mouselistener);   
				jMItemwanneng = new QMMenuItem("���ܹ����嵥", mouselistener);   
        //CCBegin SS5
        jMItemwannengliangju = new QMMenuItem("���������嵥", mouselistener);
        jMItemgongweiqiju = new QMMenuItem("��λ�����嵥", mouselistener);
        //CCEnd SS5
				jMItemequip = new QMMenuItem("�豸�嵥", mouselistener);   
				jMItemmoju = new QMMenuItem("ĥ��һ����", mouselistener);   
				jMItemdaoju = new QMMenuItem("����һ����", mouselistener);   
				jMItemjiafuju = new QMMenuItem("�и���һ����", mouselistener);   
				jMItemzhuangpei = new QMMenuItem("װ�乤��һ����", mouselistener);  
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
				jMItemzcjiaju = new QMMenuItem("��ݼо���ϸ��", mouselistener);   
				jMItemzcwangong = new QMMenuItem("������ܹ�����ϸ��", mouselistener);   
				jMItemzcwanliang = new QMMenuItem("�������������ϸ��", mouselistener);
				jMItemzcjianju = new QMMenuItem("��ݼ����ϸ��", mouselistener);
				jMItemzcsb = new QMMenuItem("����豸�嵥", mouselistener);   
				jMItemzcmoju = new QMMenuItem("���ĥ��һ����", mouselistener);   
				jMItemzcdaoju = new QMMenuItem("��ݵ���һ����", mouselistener);   
				//				CCBegin SS6
				jMItemzcdaofuju = new QMMenuItem("��ݵ�����һ����", mouselistener);   
//				CCEnd SS6
				jMItemzcjiafuju = new QMMenuItem("��ݼи���һ����", mouselistener);   
				jMItemzcliangju = new QMMenuItem("�������һ����", mouselistener);  
				jMItemzcjianfuju = new QMMenuItem("��ݼ츨��һ����", mouselistener);   
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
				jMItemCtTool = new QMMenuItem("���ع�װ��ϸ", mouselistener);   
				jMItemCtSheBei = new QMMenuItem("�����豸�嵥", mouselistener);   
				jMItemCtMoJu = new QMMenuItem("����ģ���嵥", mouselistener);
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
        //�ҽ���ݲ˵�
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
        jMenuCTFileCollect1 = new QMMenuItem("���ع��պϲ�", mouselistener);
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
        jMenuSelectExport = new QMMenuItem("����",mouselistener);
        //CCEnd SS15
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
     *
     * <p>Title: ��������</p>
     * <p>Description:�������� </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: һ������</p>
     * @author Ѧ��
     * @version 1.0
     */
    class MyMouseListener extends MouseAdapter
    {
        public void mouseEntered(MouseEvent e)
        {
            Object obj = e.getSource();
            //��ť
            if (obj instanceof JButton)
            {
                JButton button = (JButton) obj;
                statusBar.setText((String) toolFunction.get(button.
                        getActionCommand()));
            }
            //�˵���
            if (obj instanceof QMMenuItem)
            {
                QMMenuItem item = (QMMenuItem) obj;
                //���˵�����ȥ�����Ƿ�
                String s = item.getExplainText();
                statusBar.setText(s);
            }
            //�˵�
            if (obj instanceof QMMenu)
            {
                QMMenu item = (QMMenu) obj;
                statusBar.setText(item.getExplainText());
            }

        }


        /**
         * ����뿪
         * @param e MouseEvent
         */
        public void mouseExited(MouseEvent e)
        {
            statusBar.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "default_status", null));
        }
    }

    /**
     * ˢ�¹���������
     * @param object CappTreeObject ����������
     * @return CappTreeObject ˢ�º�Ķ���
     */
    public CappTreeObject update(CappTreeObject object)throws QMRemoteException
    {
        BaseValueInfo newinfo = (BaseValueInfo) CappClientHelper.refresh(
               (BaseValueInfo)object.getObject());
        object.setObject(newinfo);
        processTreePanel.updateNode(object);
        return object;

    }
    
    //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2676 
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
    //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 td2676 
  //CCBegin SS2
    /**
     * ��õ�ǰ�û�
     * @return String ��ǰ�û�
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
  			if (groupName.equals("�������û���")) 
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
  			if (groupName.indexOf("����") >= 0 ) 
  			{
  				return true;
  			}
  		}
  		return false;
  	}
//  	CCEnd SS8
  	
  	//CCBegin SS3
    /**
     * �ж��û�������˾
     * @return String ����û�������˾
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
