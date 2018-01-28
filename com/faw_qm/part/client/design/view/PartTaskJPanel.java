/** ���ɳ���PartTaskJPanel.java	1.1  2003/02/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/02 л�� ԭ��:���㲿������״̬ʱ����UsesInterfaceList������״̬������
 *                     ����:�Ż���ʼ���Ҳ����
 *                     ��ע:���v3r11-չ���㲿�����ڵ������Ż�
 * CR2 2009/04/02 л�� ԭ��:���ֶ�ε�����ͬ���������Ը���
 *                     ����:ȥ���ظ��ķ���
 *                     ��ע:���v3r11-չ���㲿�����ڵ������Ż�
 * CR3 2009/06/19 ����  TD��2443�ڲ�Ʒ��Ϣ�����У�ѡ���㲿�������Ҳ���������TABҳ�ϣ������ࡱ��ť�͡�ɾ�����ࡱ��ť����ʾ
 *                      �������ػ���������TABҳ��
 *                      
 * CR4 2009/06/21 л�� ԭ�򣺴���һ�����Բ����Ĺ��岿�������������������������������״̬Ϊ���ģ�������ȷ��ӡ�TD-2330
 *                    ���������ڽ�dialog�ĸ��������Ϊ��Frame������ʹ���������뷨���dialog���丸���ڸ��ǵ����⣻���ڽ�dialog�ĸ��������Ϊ�����ڼ��ɽ����
 * CR5 2009/07/02 л�� ԭ�����������㲿�����л���ͬ�㲿����������TABҳ�������ɾ����ť�����ظ����֡�TD-2443
 *                    �����������������ڵ�������죬������ظ���ʼ������ӡ��Ƚ����е�����Ƴ�����Ӽ��ɡ�
 * CR6 2009/07/06 ��� ԭ��:TD2543�ڲ�Ʒ��Ϣ�������в˵����պͽ����Ҳ������µĻ�ԭ��ť��ݼ������ظ�
 *                     ����:�Ҳ������µĻ�ԭ��ť��ݼ�����ΪAIT+Z 
 * CR7 2009/07/20 ���� ԭ�򣺲�Ʒ��Ϣ�������Ҳ�TABҳ�����ĵ��Ͳο��ĵ�����½����TABҳ������ϵ�Ͳο���ϵ��һ�¡�
 *                    �����������½����TABҳ������ϵ�Ͳο���ϵ��Ϊ�����ĵ��Ͳο��ĵ���
 * CR8 2009/11/25 ���� ԭ�򣺲�Ʒ��Ϣ����������Tabҳ��Ϣһ��ȫ����ʼ����
 *                     ��������ע�ĸ�Tabҳ�ͳ�ʼ���ĸ�Tabҳ��
 * CR9 2009/11/26 ���� ԭ��û�ж��㲿�������޸�Ҳ�ᵯ���Ի����Ƿ񱣴��*�㲿�����޸ġ���   
 * SS1  ����BOM��������� liuyang 2014-6-20             
 *  SS2 ���������汾 xianglx 2014-8-12
 *  SS3 ��ӽṹ��ʱ������������������ͼ�����зֱ������ĵļ����ǽ�ŵļ� guoxiaoliang 2016-4-20
 */
package com.faw_qm.part.client.design.view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.query.CappSchema;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.clients.beans.explorer.QMExplorer;
import com.faw_qm.clients.beans.explorer.QMStatusBar;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.prodmgmt.HelperPanel;
import com.faw_qm.clients.vc.controller.CheckInOutTaskLogic;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.csm.navigation.NavigationUtil;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.iba.client.container.main.ClassifierControl;
import com.faw_qm.iba.client.container.main.IBAContainerEditor;
import com.faw_qm.iba.client.container.main.IBADialog;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.lite.AbstractLiteObject;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.design.model.PartMasterItem;
import com.faw_qm.part.client.design.model.ProgressService;
import com.faw_qm.part.client.design.model.UsageInterfaceItem;
import com.faw_qm.part.client.design.model.UsageItem;
import com.faw_qm.part.client.design.model.UsesInterfaceList;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.design.util.QuantityEditor;
import com.faw_qm.part.client.design.util.QuantityRenderer;
import com.faw_qm.part.client.design.util.UnitEditor;
import com.faw_qm.part.client.main.controller.QMProductManager;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.main.view.QMPartList;
import com.faw_qm.part.client.main.view.QMProductManagerJFrame;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.PartUsageList;
import com.faw_qm.part.util.Unit;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.units.util.MeasurementSystemDefaultView;
import com.faw_qm.util.TextValidCheck;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.viewmanage.model.ViewObjectInfo;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * <p>Title: ���������£��㲿�����������塣</p>
 * <p>Description:PartTaskJPanel��һ�����㲿�����в����Ľ����ࣻ</p>
 * <p>ʵ���㲿���Ĵ��������ºͲ鿴��</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 * @see UsesJPanel,DescribedByJPanel,ReferenceJPanel
 * �޸�1��2007-10-29��By ������
 * ����ԭ�򣺴����㲿��ʱ�����ϼд�����Ĳ����ڵ����ϼ�û�н��й��ˡ�
 * ����������ڱ����㲿��ʱ��ֱ��ȡ����Ч·�������ο��ܳ��ֵĲ����ڵ����ϼе������
 * ����(2)20080306 ��ǿ�޸� �޸�ԭ��:����������ǰ�޸ĵ��㲿��ʱ�������ĵ����ĸ��ı�־��Ȼ��true��TD-1516����
 */

public class PartTaskJPanel extends HelperPanel
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;

    /**��������ʾģʽ������ģʽ�����*/
    public final static int UPDATE_MODE = 0;

    /**��������ʾģʽ������ģʽ�����*/
    public final static int CREATE_MODE = 1;

    /**��������ʾģʽ���鿴ģʽ�����*/
    public final static int VIEW_MODE = 2;

    /**�����ж��Ƿ�ر��Ӵ�*/
    protected boolean disposeWindow = false;

    /**�����жϽ���ģʽ*/
    protected boolean updateMode = false;

    protected boolean isOK = false;

    /**����ģʽ--�鿴*/
    protected int mode = VIEW_MODE;

    /**������ϵ��壨ʹ�ýṹ�����ڱ༭������ʹ�á�*/
    private UsesJPanel usesJPanel = null;

//  modify by muyp 20081118 begin
    /**������ϵ��壨�ο���ϵ��*/
    private ReferenceJPanel referencesJPanel = new ReferenceJPanel(true);

    /**������ϵ��壨������ϵ��*/
    private DescribedByJPanel describedByJPanel = new DescribedByJPanel(true);
    //end

    /**ȷ����ť*/
    private JButton okJButton = new JButton();

    /**���水ť*/
    private JButton saveJButton = new JButton();

    /**ȡ����ť*/
    private JButton cancelJButton = new JButton();

    /**�༭���԰�ť*/
    private JButton editAttributesJButton = new JButton();

    /**�㲿�������������*/
    private BaseAttributeJPanel attributeJPanel ;

    /**���ڷ����㲿�������������*/
    private JTabbedPane contentsJTabbedPane = new JTabbedPane();

    /**�㲿���������ԣ�ʹ�ýṹ���ο���ϵ��������ϵ�����*/
    private JPanel relationsJPanel = new JPanel(new GridBagLayout());

    /**���ð�ť��ȷ�������桢ȡ�����༭���ԣ������*/
    private JPanel createButtonJPanel = new JPanel(new GridBagLayout());

    /**�������ʹ�ýṹ���*/
    private JPanel addUsesJPanel = null;

    /**������Ӳο���ϵ���*/
    private JPanel addRefJPanel = new JPanel(new GridBagLayout());

    /**�������������ϵ���*/
    private JPanel addDesJPanel = new JPanel(new GridBagLayout());

    /**���ڻ��ϵͳ�ĵ�ǰ�汾*/
    private static String PART_CLIENT = RemoteProperty.getProperty(
            "part_client_customize_earmark", "A");
    /**״̬��*/
    private QMStatusBar statusBar = new QMStatusBar();


    private IBAHolderIfc ibaHolder = null;

    /**�����true����ʾ���������ʾ�ڲ�Ʒ��Ϣ������������*/
    private boolean mainPart = false;

    /**���������ʾ�������Ե����*/
    private JPanel addEditAttributesJPanel = null;

    /**������ʾ�������Ե����*/
    private IBAContainerEditor ibaAttributesJPanel = null;

    /**����addʹ�ýṹ����壬���Ҳ������ʹ�á�*/
    private JPanel addStructJPanel = null;

    /**��ť���*/
    JPanel useButtonJPanel = null;

    /**��Ӱ�ť*/
    private JButton addUsageJB = null;

    /**��ȥ��ť*/
    private JButton removeUsageJB = null;

    /**�鿴��ť*/
    private JButton viewUsageJB = null;

    AbstractLiteObject classificationstructdefaultview = null;

    private ClassifierControl classifiercontrol;

    private ClassifierControl classifiercontrol2;

    /**���� PartItem����*/
    private Object cursorLock = new Object();

    private boolean usagechange = false;

    private boolean ibachange = false;

    private boolean basechange = false;

    private boolean descrichange = false;

    private boolean refchange = false;
    private boolean isReference=false;
  

	//��ʾ���ð�ť���
	private JPanel partAttrSetJPanel = new JPanel();
	//��ʾ���ð�ť
	private JButton partAttrSetJButton = null;
	//�������
	private TechnicsRoutePanel routeJPanel = new TechnicsRoutePanel();
	private JPanel addRouteJPanel = new JPanel(new GridBagLayout());
	private TechnicsSummaryPanel summaryJPanel = new TechnicsSummaryPanel();
	private JPanel addSummaryJPanel = new JPanel(new GridBagLayout());
	private TechnicsRegulateionPanel regulateionJPanel = new TechnicsRegulateionPanel();	
	private JPanel addRegulateionJPanel = new JPanel(new GridBagLayout());
	//tabҳ����
	private int usageIndex=0;
	private int baseIndex=0;
	private int ibaIndex=0;
	private int descIndex=0;
	private int refIndex=0;
    private TabListener tablistener = new TabListener();//Tabҳ����//CR8
    private boolean isCreate = false;//�����ڴ����͸���ʱ����ʾ�����ĵ��Ͳο��ĵ���//CR8
	    
    /**
     * ���캯����
     */
    public PartTaskJPanel()
    {
        super();
        try
        {        	
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * ���캯����
     */
    public PartTaskJPanel(boolean flag)
    {
        super();
        mainPart = flag;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * ��ʼ����
     * @throws Exception
     */
    private void jbInit() throws Exception
    {
        setLayout(new GridBagLayout());
        setSize(650, 520);
        //��ʼ��PartAttributesPanel Bean
        attributeJPanel = new BaseAttributeJPanel(mainPart);
        attributeJPanel.setName("attributeJPanel");//CR8
        okJButton.setBounds(new Rectangle(93, 0, 65, 23));
        okJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        okJButton.setMaximumSize(new Dimension(65, 23));
        okJButton.setMinimumSize(new Dimension(65, 23));
        okJButton.setPreferredSize(new Dimension(83, 23));
        okJButton.setMnemonic('Y');
        okJButton.setText("Ok");
        saveJButton.setBounds(new Rectangle(26, 0, 65, 23));
        saveJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        saveJButton.setMaximumSize(new Dimension(65, 23));
        saveJButton.setMinimumSize(new Dimension(65, 23));
        saveJButton.setOpaque(true);
        saveJButton.setPreferredSize(new Dimension(83, 23));
        saveJButton.setMnemonic('S');
        cancelJButton.setText("Cancel");
        cancelJButton.setBounds(new Rectangle(160, 0, 65, 23));
        cancelJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        cancelJButton.setMaximumSize(new Dimension(65, 23));
        cancelJButton.setMinimumSize(new Dimension(65, 23));
        cancelJButton.setPreferredSize(new Dimension(83, 23));
//CR6 Begin        
        cancelJButton.setMnemonic('Z');
        cancelJButton.setMnemonic('Z');
//CR6 End         
        editAttributesJButton.setText("Edit Attributes");
        editAttributesJButton.setBounds(new Rectangle(67, 3, 83, 22));
        editAttributesJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        editAttributesJButton.setMaximumSize(new Dimension(90, 25));
        editAttributesJButton.setMinimumSize(new Dimension(83, 25));
        editAttributesJButton.setPreferredSize(new Dimension(98, 23));
        editAttributesJButton.setMnemonic('I');
       
        //other
        statusBar.setText("  ");
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        statusBar.setMaximumSize(new Dimension(40, 18));
        statusBar.setMinimumSize(new Dimension(40, 18));
       
        contentsJTabbedPane.doLayout();
        contentsJTabbedPane.addChangeListener(tablistener);//CR8
        //�������Ϊ�Ҳ���塣
        if(mainPart)
        {
            ibaAttributesJPanel = new IBAContainerEditor(false, true);
            addEditAttributesJPanel = new JPanel(new GridBagLayout());
            addEditAttributesJPanel.setName("addEditAttributesJPanel");//CR8
            addEditAttributesJPanel.add(ibaAttributesJPanel,
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0)); 
            addStructJPanel = new JPanel(new GridBagLayout());
            viewUsageJB = new JButton();
            viewUsageJB.setBounds(new Rectangle(19, 84, 59, 23));
            viewUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
            viewUsageJB.setMaximumSize(new Dimension(65, 23));
            viewUsageJB.setMinimumSize(new Dimension(65, 23));
            viewUsageJB.setPreferredSize(new Dimension(83, 23));
            viewUsageJB.setMnemonic('V');
            viewUsageJB.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    viewUsageJButton_actionPerformed(e);
                }
            });
            addUsageJB = new JButton();
            addUsageJB.setBounds(new Rectangle(19, 0, 59, 23));
            addUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
            addUsageJB.setMaximumSize(new Dimension(65, 23));
            addUsageJB.setMinimumSize(new Dimension(65, 23));
            addUsageJB.setPreferredSize(new Dimension(83, 23));
            addUsageJB.setMnemonic('A');
            addUsageJB.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    addUsageJButton_actionPerformed(e);
                }
            });
            removeUsageJB = new JButton();
            removeUsageJB.setBounds(new Rectangle(19, 42, 59, 23));
            removeUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
            removeUsageJB.setMaximumSize(new Dimension(65, 23));
            removeUsageJB.setMinimumSize(new Dimension(65, 23));
            removeUsageJB.setPreferredSize(new Dimension(83, 23));
            removeUsageJB.setMnemonic('R');
            removeUsageJB.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    removeUsageJButton_actionPerformed(e);
                }
            });
            //liyz add
            partAttrSetJButton = new JButton();
            partAttrSetJButton.setBounds(new Rectangle(19, 126, 59, 23));
            partAttrSetJButton.setFont(new java.awt.Font("Dialog", 0, 12));
            partAttrSetJButton.setMaximumSize(new Dimension(65, 23));
            partAttrSetJButton.setMinimumSize(new Dimension(65, 23));
            partAttrSetJButton.setPreferredSize(new Dimension(83, 23));
            partAttrSetJButton.setMnemonic('P');
            partAttrSetJButton.addActionListener(new AttrSetListener("usage"));//end
            //liyz add �����ĵ��Ͳο��ĵ�����ʾ���õļ���
            describedByJPanel.getShowAttrSettedJButton().addActionListener(new AttrSetListener("description"));
            referencesJPanel.getShowAttrSettedJButton().addActionListener(new AttrSetListener("reference"));
            regulateionJPanel.getShowAttrSettedJButton().addActionListener(new AttrSetListener("regulation"));
            summaryJPanel.getShowAttrSettedJButton().addActionListener(new AttrSetListener("summary"));
            routeJPanel.getShowAttrSettedJButton().addActionListener(new AttrSetListener("route"));
            //end
            useButtonJPanel = new JPanel(new GridBagLayout());
//            useButtonJPanel.add(partAttrSetJButton, new GridBagConstraints(0, 0, 1, 1,
//                    0.0, 0.0, GridBagConstraints.CENTER,
//                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));
            useButtonJPanel.add(addUsageJB, new GridBagConstraints(1, 0, 1, 1,
                    0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));
            useButtonJPanel.add(removeUsageJB, new GridBagConstraints(2, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));//(7, 0, 10, 13)
            useButtonJPanel.add(viewUsageJB, new GridBagConstraints(3, 0, 1, 1,
                    0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));
            
            addStructJPanel.add(useButtonJPanel, new GridBagConstraints(0, 1,
                    2, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));//(0, 0, 0, 20)
            addDesJPanel.add(describedByJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            addDesJPanel.setName("addDesJPanel");//CR8
            addRefJPanel.add(referencesJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            addRefJPanel.setName("addRefJPanel");//CR8
            addRegulateionJPanel.add(regulateionJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            addRegulateionJPanel.setName("addRegulateionJPanel");//CR8
            addSummaryJPanel.add(summaryJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            addSummaryJPanel.setName("addSummaryJPanel");//CR8
            addRouteJPanel.add(routeJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            addRouteJPanel.setName("addRouteJPanel");//CR8
            //liyz add
            partAttrSetJPanel = new JPanel(new GridBagLayout());
            addStructJPanel.add(partAttrSetJPanel, new GridBagConstraints(0, 1,
                    1, 1, 0.0, 0.0, GridBagConstraints.SOUTHWEST,
                    GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            partAttrSetJPanel.add(partAttrSetJButton, new GridBagConstraints(0, 0, 1, 1,
                    0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.NONE, new Insets(0, 0, 5, 15), 0, 0));
            
            
            //end           
            
            int i = 0;
//            //�����ã����𻷾�ע��begin
//            java.util.Properties   prop=new Properties();
//            FileInputStream fis = 
//                new FileInputStream("F:/PDMV4/product/productfactory/phosphor/cpdm/classes/properties/part.properties");
//            prop.load(fis);
//            //end
            while (true)
            {

                String s = RemoteProperty.getProperty(
                        "com.faw_qm.part.manager.JTab." +
                        String.valueOf(i));            	
             
//                  String s=prop.getProperty( "com.faw_qm.part.manager.JTab." +
//                        String.valueOf(i));            
              
                if (s == null)
                {
                    break;
                }
                if(s.equals("usage"))
                {
                	contentsJTabbedPane.add(addStructJPanel, getLabelsRB().getString(
                             "usage"), i);
                	usageIndex=i;
                }                
                if(s.equals("base"))
                {
			        contentsJTabbedPane.add(attributeJPanel, getLabelsRB().getString(
			                "attribute"),i);
                    baseIndex=i;
                }
                if(s.equals("iba"))
                {
				    contentsJTabbedPane.add(addEditAttributesJPanel, getLabelsRB()
				                .getString("editAttributes"), i);
                    ibaIndex=i;
                }
                if(s.equals("description"))
                {
                	contentsJTabbedPane.add(addDesJPanel, getLabelsRB().getString(
            		"describedDoc"),i);
                	descIndex=i;
                }                	
                if(s.equals("reference"))
                {
                	contentsJTabbedPane.add(addRefJPanel, getLabelsRB().getString(
            		"referenceDoc"),i);
                	refIndex=i;
                }                	
                if(s.equals("capproute"))
                	contentsJTabbedPane.add(addRouteJPanel, getLabelsRB().getString(
                    "cappRoute"),i);
                if(s.equals("cappsummary"))
                	contentsJTabbedPane.add(addSummaryJPanel, getLabelsRB().getString(
                    "cappSummary"),i);
                if(s.equals("cappregulation"))
                	contentsJTabbedPane.add(addRegulateionJPanel, getLabelsRB().getString(
                    "cappRegulation"),i);
                i++;

            }
            
          
            createButtonJPanel.add(new JLabel(),
                    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
                            GridBagConstraints.SOUTHEAST,
                            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,
                                    0), 0, 0));//(0, 0, 0, 13)21, 1
            createButtonJPanel.add(saveJButton, new GridBagConstraints(2, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.SOUTHEAST,
                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));//(0, 0, 0, 13)21, 1
            createButtonJPanel.add(cancelJButton, new GridBagConstraints(3, 0,
                    1, 1, 0.0, 0.0, GridBagConstraints.SOUTHEAST,
                    GridBagConstraints.NONE, new Insets(5, 0, 5, 8), 0, 0));//(0, 0, 0, 13)21, 1
            add(createButtonJPanel,
                    new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0,
                            GridBagConstraints.SOUTHEAST,
                            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,
                                    3), 0, 0));//CENTER BOTH(5, 0, 5, 15), 51, 0
            //�޸ģ��ѱ�ǩҳ����Ϊ�����
            add(contentsJTabbedPane, new GridBagConstraints(0, 1, 2, 1, 1.0,
                    1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        else
        {
        	  /**������ϵ��壨�ο���ϵ��*/
            referencesJPanel = new ReferenceJPanel(false);

            /**������ϵ��壨������ϵ��*/
            describedByJPanel = new DescribedByJPanel(false);
            usesJPanel = new UsesJPanel();
            addUsesJPanel = new JPanel(new GridBagLayout());
            addUsesJPanel
                    .add(usesJPanel, new GridBagConstraints(0, 0, 1, 1, 1.0,
                            1.0, GridBagConstraints.CENTER,
                            GridBagConstraints.BOTH,
                            new Insets(-3, -10, -5, -3), 0, 0));
            addDesJPanel.add(describedByJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));//(-3, -10, -5, 2)
            addRefJPanel.add(referencesJPanel, new GridBagConstraints(0, 0, 1,
                    1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));//(-3, -10, -5, -10)
            contentsJTabbedPane.add(addUsesJPanel, getLabelsRB().getString(
                    "usage"));
            contentsJTabbedPane.add(addDesJPanel, getLabelsRB().getString(
                    "describedDoc"));//CR7
            contentsJTabbedPane.add(addRefJPanel, getLabelsRB().getString(
                    "referenceDoc"));//CR7
            createButtonJPanel.add(saveJButton, new GridBagConstraints(1, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 21,
                    1));
            createButtonJPanel.add(cancelJButton, new GridBagConstraints(3, 0,
                    1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                    GridBagConstraints.BOTH, new Insets(0, 6, 0, 0), 21, 1));
            createButtonJPanel.add(okJButton, new GridBagConstraints(2, 0, 1,
                    1, 0.0, 0.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 6, 0, 0), 21, 1));
            /**���ڱ�ǡ��Ƿ�ȴ����������̵߳��������򣬷�ֹ�߳�֮����ֵ��ó�ͻ*/
            createButtonJPanel.add(new JLabel("    "),
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                            GridBagConstraints.EAST,
                            GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0,
                                    0), 0, 0));
          
            relationsJPanel.add(contentsJTabbedPane, new GridBagConstraints(0,
                    0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(0, 6, 0, 13), 0, 0));
           
            add(createButtonJPanel, new GridBagConstraints(1, 2, 1, 1, 0.0,
                    0.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL,
                    new Insets(5, 0, 5, 15), 51, 0));
            add(attributeJPanel, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 2));
            add(relationsJPanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            add(statusBar, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
                    GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 520, 0));
        }
        //ע�����
        PTJAction ptjAction = new PTJAction();
        saveJButton.addActionListener(ptjAction);
        cancelJButton.addActionListener(ptjAction);
        okJButton.addActionListener(ptjAction);
        editAttributesJButton.addActionListener(ptjAction);
       
        initialize();
    }

    //liyz add ��ʾ���ð�ť�ļ���
    void partAttrSetJButton_actionPerformed(ActionEvent event,String tab)
    {
    	 PartItem part = getPartItem();    	
    	 boolean flag = false;
    	 if(part==null)
    	 {
    		 JFrame parentFrame = getFrame();
    	     PartAttrSetJDialog dialog = new PartAttrSetJDialog(tab,parentFrame);
    	     PartScreenParameter.setLocationCenter(dialog);
    	     dialog.setVisible(true); 
    	 }
    	 else 
    		 {    		
    		 boolean isChange = isChange();
             if(part != null && part.getPart().getPartNumber() != null && partData != null && dtm != null)
             {                
                 //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
                 DisplayIdentity displayidentity = IdentityFactory
                         .getDisplayIdentity(part.getPart());
                 //��ö������� + ��ʶ
                 String s = displayidentity.getLocalizedMessage(null);
                 Object[] params = {s};
                 String confirmExitMsg = QMMessage.getLocalizedMessage(RESOURCE,
                         "confirmsave", params);
                 String warningDialogTitle = QMMessage.getLocalizedMessage(
                         RESOURCE, "errorTitle", null);
                 /**����ı䣬��ʾ�Ƿ񱣴�*/
                 if(isChange)
                 {
                     int i = DialogFactory.showYesNoDialog(this, confirmExitMsg,
                             warningDialogTitle);
                     //����(2)20080306 zhangq begin:����������ǰ�޸ĵ��㲿��ʱ��
                     //�����ĵ����ĸ��ı�־��Ȼ��true��TD-1516����
                     if(i == DialogFactory.YES_OPTION)
                     {
                         flag = true;
                         saveInThread(isChange);
                         //describedByJPanel.setChanged(false);
                     }
                     describedByJPanel.setChanged(false);
                     //����(2)20080306 zhangq end
                 }
             }
    		 
    		 if(flag)
    		 {
    			 JFrame parentFrame = getFrame();
    		     PartAttrSetJDialog dialog = new PartAttrSetJDialog(tab,parentFrame);
    		     PartScreenParameter.setLocationCenter(dialog);
    		     dialog.setVisible(true); 
    		 }    	
    		else if(!isChange)
    		{
    			JFrame parentFrame = getFrame();
    	    	PartAttrSetJDialog dialog = new PartAttrSetJDialog(tab,parentFrame);
    	    	PartScreenParameter.setLocationCenter(dialog);
    	    	dialog.setVisible(true);
    		}
    	}
    }
    /**
     * ��ø�����
     * @return JFrame �����ڡ�
     */
    protected JFrame getFrame()
    {
        Component parent = getParent();        
        while (!(parent instanceof JFrame))
        {
            parent = parent.getParent();
        }
        JFrame returnJFrame = (JFrame) parent;
        return returnJFrame;
    }
    //end

    /**
     * ��ý���ģʽ
     * @return ����ģʽ�����ģʽ
     */
    public int getUpdateMode()
    {
        return mode;
    }

    /**
     * ���ý���ģʽ�����������»�鿴����
     * @param aMode �½���ģʽ��
     * @exception java.beans.PropertyVetoException ���ģʽMode��Ч�����׳��쳣��
     */
    public void setUpdateMode(int aMode) throws PropertyVetoException
    {
        PartDebug.debug("setUpdateMode(int aMode) begin ....", this,
                PartDebug.PART_CLIENT);
        if((aMode == PartTaskJPanel.UPDATE_MODE) || (aMode == PartTaskJPanel.CREATE_MODE)
                || (aMode == PartTaskJPanel.VIEW_MODE))
        {
            mode = aMode;
        }
        else
        {
            //��Ϣ����Чģʽ
            throw (new PropertyVetoException(
                    getLocalizedValue(PartDesignViewRB.INVALID_MODE),
                    new PropertyChangeEvent(this, "mode", new Integer(
                            getUpdateMode()), new Integer(aMode))));
        }
        switch (aMode)
        {
            case UPDATE_MODE:
            { //����ģʽ
                enableUpdateFields();
                updateMode = true;
                break;
            }

            case CREATE_MODE:
            { //����ģʽ
                updateMode = true;
                enableCreateFields();
                try
                {
                    isCreate = true;//CR8
                    setPartItem(new PartItem());
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "errorTitle", null);
                    JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                            title, JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                    return;
                }
            }

            case VIEW_MODE:
            { //�鿴ģʽ
                updateMode = false;
                enableViewFields();
                break;
            }
        }
        PartDebug.debug("setUpdateMode(int aMode) end ....return is void",
                this, PartDebug.PART_CLIENT);
    }


    /**
     * ����ˢ��֪ͨ��
     */
    public void addNotify()
    {
        super.addNotify();
    }


    /**
     * ������������Ƿ�������Чֵ��
     * @return  �����������������Чֵ���򷵻�Ϊ�档
     */
    protected boolean checkRequiredFields()
    {
        PartDebug.debug("checkRequiredFields() begin....", this,
                PartDebug.PART_CLIENT);
        isOK = true;
        String message = "fell through ";
        String title = "";
        if((getUpdateMode() == CREATE_MODE))
        { //����ģʽ
            //�������Ƿ�Ϊ��,���Ҳ�����ͨ���
            try
            {
                //2007.05.29 whj
                // �������Ƿ�Ϊ�գ����Ҳ�����ͨ���
            	   //	CCBegin by leixiao 2010-9-28  �ſ��Ա�����Ƶ���������,���Ƴ������ݿ�һ��200
                TextValidCheck validate = new TextValidCheck(getLabelsRB()
                        .getString("numberLbl"), 1, 200);
                validate.check(getPartItem().getNumber().trim(), true);
                //              ���������Ƿ�Ϊ�գ����Ҳ�����ͨ���
                TextValidCheck validatename = new TextValidCheck(getLabelsRB()
                        .getString("nameLbl"), 1, 200);
                validatename.check(getPartItem().getName().trim(), true);
                //	CCEnd by leixiao 2010-9-28  �ſ��Ա�����Ƶ���������,���Ƴ������ݿ�һ��200
//            }
//            catch (QMRemoteException re)
//            {
//                message = re.getClientMessage();
//                isOK = false;
//            }
//            try
//            {
                //�������ϼ��Ƿ�Ϊ��
                if(attributeJPanel.checkFolderLocation() == null)
                {
                    message = getLocalizedValue(PartDesignViewRB.NO_LOCAL_ENTER);
                    isOK = false;
                }
            }
            catch (QMRemoteException qre)
            {
                //��ʾ��Ϣ����ָ�������ϼв��Ǹ������ϼ�
//                title = getLocalizedValue("errorTitle");
//                JOptionPane.showMessageDialog(getParentFrame(), qre
//                        .getClientMessage(), title,
//                        JOptionPane.INFORMATION_MESSAGE);
                qre.printStackTrace();
                message=qre.getClientMessage();
                isOK = false;
            }
        }
        if(!isOK)
        {
            //��ʾ��Ϣ��ȱ�ٱ�����ֶ�
            title = getLocalizedValue("errorTitle");
            JOptionPane.showMessageDialog(getParentFrame(), message, title,
                    JOptionPane.ERROR_MESSAGE);
        }
        PartDebug.debug("checkRequiredFields() end...return " + isOK, this,
                PartDebug.PART_CLIENT);
        return isOK;
    }

    /**
     * ʹ�������ڴ����㲿��״̬��
     */
    protected void enableCreateFields()
    {
        PartDebug.debug("enableCreateFields() begin....", this,
                PartDebug.PART_CLIENT);
        attributeJPanel.enableCreateFields();
        if(usesJPanel != null)
        {
            usesJPanel.setUpdateMode(true);
        }
        if(referencesJPanel != null)
        {
            referencesJPanel.setUpdateMode(true);
        }
        PartDebug.debug("enableCreateFields() end...return is void", this,
                PartDebug.PART_CLIENT);
    }


    /**
     * ʹ���洦�ڲ鿴״̬(�շ���)��
     */
    protected void enableViewFields()
    {
        if(mainPart)
        {
            PartDebug.debug("enableUpdateFields() begin....", this,
                    PartDebug.PART_CLIENT);
            attributeJPanel.enableViewFields();
           
            if(mainPart)
            {
                viewUsageJB.setEnabled(false);
                addUsageJB.setEnabled(false);
                removeUsageJB.setEnabled(false);
                //liyz add
                //partAttrSetJButton.setEnabled(false);//end
            }
            setButtonWhenSave(false);
            structList.setCellEditable(false);
          
            if(usesJPanel != null)
            {
                usesJPanel.setUpdateMode(false);
            }
            if(referencesJPanel != null)
            {
                referencesJPanel.setUpdateMode(false);
            }
            if(describedByJPanel != null)
            {
                describedByJPanel.setUpdateMode(false);
            }
            if(ibaAttributesJPanel != null)
            {
                ibaAttributesJPanel.updateEditor(null, false);
                ibaAttributesJPanel.setUpdateMode(false);
            }
           
            PartDebug.debug("enableUpdateFields() end...return is void", this,
                    PartDebug.PART_CLIENT);
        }
    }


    /**
     * ʹ���洦�ڸ����㲿��״̬��
     */
    protected void enableUpdateFields()
    {
        PartDebug.debug("enableUpdateFields() begin....", this,
                PartDebug.PART_CLIENT);
        attributeJPanel.enableUpdateFields();
        if(mainPart)
        {
            viewUsageJB.setEnabled(false);
            
            QMProductManager explor = ((QMProductManagerJFrame) this
                    .getParentFrame()).getMyExplorer();
            /**
             * set for capp ���ε���������2007.06.29 ������
             * 
             */
            referencesJPanel.setManager(explor);
            describedByJPanel.setManager(explor);
            if(explor.getFromCapp())
            {
            	 addUsageJB.setEnabled(false);
            }
            //update whj 07/12/6
//            else
//           addUsageJB.setEnabled(true);
//          update whj 07/12/6
            removeUsageJB.setEnabled(false);
            usesMultiList_itemStateChanged(null);
            setButtonWhenSave(true);
          
            if(quantityEditor != null)
            {
                quantityEditor.cancelCellEditing();
            }
            if(unitEditor != null)
            {
                unitEditor.cancelCellEditing();
            }
            //CCBegin SS1
            
            //CCEnd SS1
            if(describedByJPanel != null)
            {
                describedByJPanel.setUpdateMode(true);
            }
            if(ibaAttributesJPanel != null)
            {
                ibaAttributesJPanel.updateEditor(null, true);
                ibaAttributesJPanel.setUpdateMode(true);
            }
        }
       
        if(usesJPanel != null)
        {
            usesJPanel.setUpdateMode(true);
        }
        if(referencesJPanel != null)
        {
            referencesJPanel.setUpdateMode(true);
        }
      
        PartDebug.debug("enableUpdateFields() end...return is void", this,
                PartDebug.PART_CLIENT);
    }

    //CR7 Begin zhangq �޸�ԭ��TD2515
    public void setPartItem(PartItem part,boolean isDispalyEmpty){
        synchronized (cursorLock)
        {
            PartDebug.debug("setPartItem() begin....", this,
                    PartDebug.PART_CLIENT);
            super.setPartItem(part);
            attributeJPanel.setBeanAttributes(part);//���ñ�š����ơ���Դ�����͡�//CR8
            JPanel panel = (JPanel)contentsJTabbedPane.getSelectedComponent();//CR8
            if(panel.getName() == "attributeJPanel" || isCreate == true)
            {
                attributeJPanel.setPartItem(part, isDispalyEmpty);
                attributeJPanel.setIsShown(true);
            }
            if(mainPart)
            {
                //���ѡ��������ĸ��ڵ㣬���������Ϊ�鿴ģʽ��
                if(part.getPart().getPartNumber() == null)
                    try
                    {
                        setUpdateMode(PartTaskJPanel.VIEW_MODE);
                    }
                    catch (PropertyVetoException e)
                    {
                        e.printStackTrace();
                    }
                if(panel.getName() == "addEditAttributesJPanel" || isCreate == true)//CR8
                {
                    IBAHolderIfc ibaHolder = (IBAHolderIfc) part.getPart();
                    if(ibaHolder.getAttributeContainer() == null)
                    {
                        try
                        {
                            //Ҫ���õķ����еĲ�������ļ���
                            Class[] classes = {IBAHolderIfc.class, Object.class,
                                    Locale.class,
                                    MeasurementSystemDefaultView.class};
                            //Ҫ���õķ����еĲ����ļ���
                            Object[] objects = {ibaHolder, null,
                                    RemoteProperty.getVersionLocale(), null};
                            //����񷢳�����,ȡ�ý�����ϣ��û�ֵ����UserInfo�ļ��ϣ�
                            ibaHolder = (IBAHolderIfc) IBAUtility
                                    .invokeServiceMethod("IBAValueService",
                                            "refreshAttributeContainer", classes,
                                            objects);
                        }
                        catch (QMRemoteException remoteexception)
                        {
                            remoteexception.printStackTrace();
                            ibaHolder
                                    .setAttributeContainer(new DefaultAttributeContainer());
                        }
                    }
                    
                    ibaAttributesJPanel.updateEditor(ibaHolder, mode == VIEW_MODE);
                    
                    addClassificationButtons();
                    refreshClassificationControl();
                    
                    ibaAttributesJPanel.setUpdateMode(updateMode);
                    ibaAttributesJPanel.setIsShown(true);//CR8
                }
                //��õ�ǰPartItem ʹ�õ��㲿�����б�
                //����ƿ����
                if (updateMode) {//Begin CR1
                    usesList = part.getUsesInterfaceList();

                    //�����Խ��г�ʼ��,��������
                    usesList.startFrame();
                    //����Ĭ��ѡ�е�һ��                
                    String[] usesMultiList = usesList.toMultiList();//Begin:CR2 
                    if (structList != null && usesMultiList != null && usesMultiList.length != 0)//End:CR2
                    {
                        usesMultiList_itemStateChanged(null);
                    }

                    if (quantityEditor != null) {
                        quantityEditor.cancelCellEditing();
                    }
                    if (unitEditor != null) {
                        unitEditor.cancelCellEditing();
                    }
                    if(structList != null)
                    {
                        JTable table1 = structList.getTable();
                        DefaultTableModel dtm1 = (DefaultTableModel) table1
                                .getModel();
                        int rowCount = dtm1.getRowCount();
                        //CCBegin SS1
                        //CCBegin SS2
                       // partData = new Object[rowCount][3];
                        //partData = new Object[rowCount][5];
                        partData = new Object[rowCount][6];
                        //CCEnd SS2
                        //CCEnd SS1
                        for (int row = 0; row < rowCount; row++)
                        {
                            //����ɵ����ݣ����ж�ʱʹ�á�
                            //����ʹ�ýṹ�е���������λ���Ӳ�����BsoID��                      
//                            partData[row][0] = dtm1.getValueAt(row, 4);
//                            partData[row][1] = dtm1.getValueAt(row, 5);
//                            partData[row][2] = dtm1.getValueAt(row, 9);
                            //liyz add
                            PartUsageList quantity = PartUsageList.toPartUsageList("quantityString");
                            String[] quanHead = structList.getHeadings();
                            int quanCol = 0;
                            for(int m=0;m<quanHead.length;m++)
                            {
                                String s = quanHead[m];
                                if(s.equals(quantity.getDisplay()))
                                {
                                    quanCol = m;
                                }
                            }
                            PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                            String[] unitHead = structList.getHeadings();
                            int unitCol = 0;
                            for(int n=0;n<unitHead.length;n++)
                            {
                                String s =unitHead[n];
                                if(s.equals(unit.getDisplay()))
                                {
                                    unitCol = n;
                                }
                            }
                            //CCBegin SS1
                            PartUsageList subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber");
                            String[] subHead=structList.getHeadings();
                            int sunCol = 0;
                            for(int m=0;m<subHead.length;m++)
            	            {
            	            	String s =subHead[m];
            	               if(s.equals(subUnitNumber.getDisplay()))
            	            	{
            	            		sunCol=m;
            	            	}  
            	            	
            	            } 
                            PartUsageList bomItem = PartUsageList.toPartUsageList("bomItem");
                            String[] bomHead=structList.getHeadings();
            	            int bomCol = 0;
            	            for(int m=0;m<bomHead.length;m++)
            	            {
            	            	String s =bomHead[m];
            	               if(s.equals(bomItem.getDisplay()))
            	            	{
            	            		bomCol=m;
            	            	}   
            	            	
            	            } 
                            //CCEnd SS1
                            //CCBegin SS2
                            PartUsageList proVersion = PartUsageList.toPartUsageList("proVersion");
                            String[] proHead=structList.getHeadings();
                            int proCol = 0;
                            for(int m=0;m<proHead.length;m++)
            	            {
            	            	String s =proHead[m];
            	               if(s.equals(proVersion.getDisplay()))
            	            	{
            	            		proCol=m;
            	            	}  
            	            	
            	            } 
                            //CCEnd SS2
                            String[] head=structList.getHeadings();
                            int col = 0;
                            for(int k=0;k<head.length;k++)
                            {                                   
                              col=k;                    
                            }
                            partData[row][0] = dtm1.getValueAt(row, quanCol);
                            partData[row][1] = dtm1.getValueAt(row, unitCol);                        
                            partData[row][2] = dtm1.getValueAt(row, col+1);
                            //end
                            //CCBegin SS1
                            partData[row][3] = dtm1.getValueAt(row, sunCol);
                            partData[row][4] = dtm1.getValueAt(row, bomCol);
                            //CCEnd SS1
                            //CCBegin SS2
                            partData[row][5] = dtm1.getValueAt(row, proCol);
                            //CCEnd SS2
                        }
                    }
                }//End CR1
            }
            else if(usesJPanel != null)
            {   
                //����ƿ����
                usesJPanel.setPartItem(part);
            }
           
           
            if(PART_CLIENT.equals("C"))
            {
               
                if(mainPart)
                {
                    if(updateMode)
                    {
                        setButtonWhenSave(true);
                    }
                }
                else
                {
                    setButtonWhenSave(true);
                }
            }            
            //��ʼ�����������ı���
            if(describedByJPanel != null)
            {
                if(panel.getName() == "addDesJPanel" || isCreate == true)//CR8
                {
                    describedByJPanel.setReference(getReference());
                    describedByJPanel.setPartItem(part);
                    describedByJPanel.setIsShown(true);
                }
            }
            //��ʼ���ο������ı���
            if(referencesJPanel != null)
            {
                if(panel.getName() == "addRefJPanel" || isCreate == true)//CR8
                {
                    referencesJPanel.setPartItem(part);
                    if(mainPart)
                    {
                        // ����ο��ĵ��ţ����ж�ʱʹ��
                        docBsoID = referencesJPanel.getDocumentBsoID();
                    }
                    referencesJPanel.setIsShown(true);
                }
            }
            //liyz add ����������
            if(summaryJPanel != null)
            {
                if(panel.getName() == "addSummaryJPanel")//CR8
                {
                    summaryJPanel.setPartItem(part);
                    summaryJPanel.setIsShown(true);
                }
            }
            if(regulateionJPanel != null)
            {
                if(panel.getName() == "addRegulateionJPanel")//CR8
                {
                    regulateionJPanel.setPartItem(part);
                    regulateionJPanel.setIsShown(true);
                }
            }
            if(routeJPanel!=null&&part.getPart().getMasterBsoID()!=null)
            {
                if(panel.getName() == "addRouteJPanel")//CR8
                {
                    routeJPanel.setPartItem(part);
                    routeJPanel.setIsShown(true);
                }
            }
            //end
            PartDebug.debug("setPartItem() end...return is void", this,
                    PartDebug.PART_CLIENT);            
        }
    }
    
    /**
     * ���� PartItem ����һ���ؽ����񣬱��ڲ��̵߳��á�
     * @param part PartItem
     */
    public void setPartItem(PartItem part)
    {
        setPartItem(part,false);
    }
    //CR7 End zhangq
    
    /**
     * ����һ�����̣߳��Ա������޸ĵ��㲿����
     */
    //2003/12/15
    public void save()
    {
        saveInThread();
    }

    /**
     * �����̣߳����汻�޸ĵ��㲿����
     */
    protected void saveInThread()
    {
        saveInThread(false);
    }

    /**
     * �����̣߳����汻�޸ĵ��㲿����
     * �㡰���桱��ť������ʾ����Ի�����ѡ�񱣴棬����isDialog���������������
     * @param isDialog boolean
     */
    protected void saveInThread(boolean isDialog)
    {
        /*
         * 070511������
         * ����ǰУ����Щ���Ըı䣬ֻ���ڽṹ���������Է����ı�ʱ�ŷ���ˢ���¼����������ڵ��ˢ��
         */
        PartItem partItem = getPartItem();
        if(!isDialog)
        {
            boolean per = PersistHelper.isPersistent(partItem.getPart());
            if(per)
            {
                boolean flag = false;
                try
                {
                    Class[] paraClass = {BaseValueIfc.class};
                    Object[] objs = {(BaseValueIfc) partItem.getPart()};
                    QMPartIfc partIfc = (QMPartIfc) IBAUtility
                            .invokeServiceMethod("PersistService",
                                    "refreshInfo", paraClass, objs);
                    flag = CheckInOutTaskLogic.isInVault((WorkableIfc) partIfc);
                }
                catch (QMException e)
                {
                    flag = true;
                }
                if(flag)
                {
                    partItem.dispatchRefresh();
                    if(getParentFrame() instanceof QMProductManagerJFrame)
                    {
                        QMProductManager explor = ((QMProductManagerJFrame) this
                                .getParentFrame()).getMyExplorer();
                        explor.refreshSelectedObject();
                    }
                    String message = "��ѡ�����û�м�������˼��룬����ʧ�ܣ�";
                    String title2 = getLocalizedValue("informationTitle");
                    JOptionPane.showMessageDialog(getParentFrame(), message,
                            title2, JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            isChange();
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        PartDebug.debug("saveInThread() begin ....", this,
                PartDebug.PART_CLIENT);
        setButtonWhenSave(false);
        boolean requiredFieldsFilled;
        //�Ƿ���³ɹ������Ƿ񱣴�ɹ���
        boolean updated = false;
        try
        {
        	attributeJPanel.save(partItem, getUpdateMode());
            if(getUpdateMode() == CREATE_MODE)
            {
                requiredFieldsFilled = checkRequiredFields();
                if(!requiredFieldsFilled)
                {
                    setCursor(Cursor.getDefaultCursor());
                    setButtonWhenSave(true);
                    return;
                }
                updated = true;
                ViewObjectInfo view = attributeJPanel.getViewObject();
                if(view==null)
                {
                	 String message ="�㲿����ͼ����Ϊ�գ���ȷ���Ƿ�����ͼ�Ķ�ȡȨ�ޣ�";
     	            String title2 = this.getLocalizedValue("informationTitle");
     	            JOptionPane.showMessageDialog(getParentFrame(), message, title2,
     	                    JOptionPane.INFORMATION_MESSAGE);
     	           setButtonWhenSave(true);
     	            return;
                }
               
              
            }
            if(ibaHolder != null)
            {
                partItem.getPart().setAttributeContainer(
                        ibaHolder.getAttributeContainer());
                ibaHolder = null;
            }
            else
            {
                updated = true;
            }
            partItem.setFields();
            ProgressService
                    .setProgressText(getLocalizedValue(PartDesignViewRB.SAVING));
            ProgressService.showProgress();
            if(mainPart)
            {
                /*
                 * 070511������
                 * �Ż���������ı��淽��������part ���������Ե��޸ġ�
                 */
                if(ibachange || basechange)
                {
                    ibaAttributesJPanel.currentItem = partItem.getPart();
                    ibaAttributesJPanel.getItem();
                    QMPartIfc partIfc = (QMPartIfc) IBAUtility
                            .invokeServiceMethod("PersistService",
                                    "saveValueInfo",
                                    new Class[]{BaseValueIfc.class},
                                    new Object[]{partItem.getPart()});
                    partItem.setPartSimply(partIfc);
                    ibaAttributesJPanel.setUpdateMode(true);
                    updated = true;
                }
                //����ʹ�ýṹ
                if(usagechange)
                {
                    //����ʹ�ýṹ�б��е������㲿���������͵�λ
                    setValues();
                    updated = usesList.saveFrame() || updated;
                    usesList.startFrame();                   
//                    //liyz modify ����ʹ�ýṹʱ�����Ƿ���������͵�λ�н����ж�
//                    String[] heads=structList.getHeadings();
//                    boolean flag1=false; 
//                    boolean flag2=false;
//                	for(int k=0;k<heads.length;k++)
//                	{
//                		String list = heads[k];
//                		if(list.equals(PartUsageList.toPartUsageList("unitName").getDisplay()))
//                		{
//                	      flag1 = true;                	             	      
//                		}
//                		if(list.equals(PartUsageList.toPartUsageList("quantityString").getDisplay()))
//                		{
//                			flag2 = true;       	      
//                		}
//                	}
//                	//�����������ߵ�λ
//                	if(flag1 || flag2)
//                	{                   		
//                		usesList.setUnitExist(flag1);
//                		usesList.setQuantityExist(flag2);
//                        updated = usesList.saveFrame() || updated;
//                        usesList.startFrame();
//                	}
//                	//�����͵�λ��������
//                	else if((!flag1)&&!(flag2))
//                	{                		
//                		updated = usesList.saveFrame() || updated;
//                        usesList.startFrame();
//                	}
//                	//end
                    if(quantityEditor != null)
                    {
                        quantityEditor.cancelCellEditing();
                    }
                    if(unitEditor != null)
                    {
                        unitEditor.cancelCellEditing();
                    }                	
                }
                if(refchange)
                    updated = referencesJPanel.save() || updated;
                if(descrichange)
                    updated = describedByJPanel.save(partItem) || updated;
            }
            else
            {
                partItem.update();
                //����ʹ�ýṹ
                updated = usesJPanel.save() || updated;
                //����ο���ϵ
                updated = referencesJPanel.save() || updated;
                //����������ϵ
                updated = describedByJPanel.save(partItem) || updated;
            }
            if(updated)
            {
                if(isDialog)
                {
                    //ˢ�¸ղŸ��ĵĽڵ㡣���ڽڵ�ı䣬���ܷ���ˢ���¼���
                    ((QMExplorer) getRootPane()).getManager().refreshPart(
                            partItem.getPart());
                }
                else
                {
                    //����ˢ���¼�
                    if(mainPart)
                    {
                        if(usagechange || ibachange || basechange || descrichange || refchange)
                            partItem.dispatchRefresh();
                        docBsoID = referencesJPanel.getDocumentBsoID();
                    }
                    else
                        partItem.dispatchRefresh();
                }
                if(disposeWindow)
                {
                    //�ڸ��½����У���ȷ��֮��
                    getParentFrame().dispose();
                }
                else
                {
                    //add by 0428 �޸ı�����һ����ఴť������
                    if(!isDialog && !mainPart)
                    {
                        //end
                        if(getUpdateMode() == CREATE_MODE)
                        {
                            setUpdateMode(UPDATE_MODE);
                            partItem.setNew(false);
                        }
                        partItem.setObject(PartHelper.refresh(partItem.getPart()));
                        setPartItem(partItem);
                    }
                }
            }
            ProgressService.hideProgress();
            basechange = false;
            ibachange = false;
            usagechange = false;
            descrichange = false;
            refchange = false;
            //add 0428 ����������ɡ������㲿������Ϊ�������㲿����
            String title = getLocalizedValue("updatePartTitle");
            if(!(getParentFrame() instanceof QMProductManagerJFrame))
            getParentFrame().setTitle(title);
            setCursor(Cursor.getDefaultCursor());
        }
        catch (QMException ex)
        {
            ProgressService.hideProgress();
            setButtonWhenSave(true);            
            ex.printStackTrace();
            DisplayIdentity displayidentity = IdentityFactory
                    .getDisplayIdentity(getPartItem().getPart());
            String s = displayidentity.getLocalizedMessage(null);
            Object[] params = {s};
            String message = "";
            if(ex.getLocalizedMessage() != null)
            {
                message = getLocalizedMessage(PartDesignViewRB.SAVE_FAILURE,
                        params)
                        + ex.getLocalizedMessage();
            }
            else
            {
                message = getLocalizedMessage(PartDesignViewRB.SAVE_FAILURE,
                        params)
                        + getLocalizedMessage(PartDesignViewRB.CONF_ER_OPER,
                                null);
            }
            String title2 = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane.showMessageDialog(getParentFrame(), message, title2,
                    JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex)
        {
            ProgressService.hideProgress();
            setButtonWhenSave(true);
            ex.printStackTrace();
            DisplayIdentity displayidentity = IdentityFactory
                    .getDisplayIdentity(getPartItem().getPart());
            String s = displayidentity.getLocalizedMessage(null);
            Object[] params = {s};
            String message = "";
            if(ex.getLocalizedMessage() != null)
            {
                message = getLocalizedMessage(PartDesignViewRB.SAVE_FAILURE,
                        params)
                        + ex.getLocalizedMessage();
            }
            else
            {
                message = getLocalizedMessage(PartDesignViewRB.SAVE_FAILURE,
                        params)
                        + getLocalizedMessage(PartDesignViewRB.CONF_ER_OPER,
                                null);
            }
            String title2 = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane.showMessageDialog(getParentFrame(), message, title2,
                    JOptionPane.ERROR_MESSAGE);
        }
        catch (Throwable t)
        {
            setButtonWhenSave(true);
            t.printStackTrace();
        }
        finally
        {
            //������ʧ�ܣ�ȡ��������
            basechange = false;
            ibachange = false;
            usagechange = false;
            descrichange = false;
            refchange = false;
            ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
        }
        //add by 0428 �޸��ڴ������㲿�������˼���󣬱���ͻ�ԭ��ť��Ч�����⡣
        if(mainPart)
            setButtonWhenSave(updateMode);
        //end
        if(mainPart && !isDialog)
        {
            //���������ĵ��ĸı��־
            describedByJPanel.setChanged(false);
        }
        PartDebug.debug("saveInThread() end ....return is void", this,
                PartDebug.PART_CLIENT);
       
    }


    /**
     * ��ʼ������ϵͳ
     */
    public void initializeHelp()
    {
        try
        {
            super.initializeHelp();
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }

    }

    /**
     * ��ʼ����
     */
    protected void initialize()
    {
        PART_CLIENT = "C"; //��ʱ
        localize();
       
        //ʹ�༭���԰�ť���ɼ�
        editAttributesJButton.setVisible(true);

      
        if(!mainPart)
        {
            add(editAttributesJButton, new GridBagConstraints(0, 2, 1, 1,
                    0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(5, 7, 5, 5), 25, 0));
        }

    }


    /**
     * ʵ�ֶ������������ñ��桢ȷ����ȡ���ͱ༭���԰�ť�Ķ����¼�������
     */
    class PTJAction implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            Object object = event.getSource();
            if(object == saveJButton)
            {
                saveJButton_actionPerformed(event);
            }
            else if(object == cancelJButton)
            {
                cancelJButton_actionPerformed(event);
            }
            else if(object == okJButton)
            {
                okJButton_actionPerformed(event);
            }
            else if(object == editAttributesJButton)
            {
                editAttributesJButton_actionPerformed(event);
            }
        }
    }


    /**
     * ȷ����ť�Ķ�����
     * @param event  ActionEvent
     */
    protected void okJButton_actionPerformed(ActionEvent event)
    {
        if(!mainPart)
        {
            disposeWindow = true;
        }
        save();

    }


    /**
     * ���水ť�Ķ�����
     * @param event  ActionEvent
     */
    protected void saveJButton_actionPerformed(ActionEvent event)
    {
        if(!mainPart)
        {
            disposeWindow = false;
        }
        save();

    }


    /**
     * ���ð�ť����ʾ״̬����Ч��ʧЧ����
     * @param flag  flagΪTrue����ť��Ч������ťʧЧ��
     */
    protected void setButtonWhenSave(boolean flag)
    {
        okJButton.setEnabled(flag);
        saveJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
    }


    /**
     * ȡ����ť�Ķ�����
     * @param event  ActionEvent
     */
    protected void cancelJButton_actionPerformed(ActionEvent event)
    {
        if(!mainPart)
        {
            getParentFrame().setVisible(false);
        }
        else
        {
            PartItem part = getPartItem();
            //�����Խ��г�ʼ��,��������
            usesList.startFrame();
            if(quantityEditor != null)
            {
                quantityEditor.cancelCellEditing();
            }
            if(unitEditor != null)
            {
                unitEditor.cancelCellEditing();
            }
            part.dispatchRefresh();
            try
            {
                part.setObject(PartHelper.refresh(part.getPart()));
            }
            catch (QMRemoteException qre)
            {
                qre.printStackTrace();
                DisplayIdentity displayidentity = IdentityFactory
                        .getDisplayIdentity(getPartItem().getPart());
                String s = displayidentity.getLocalizedMessage(null);
                Object[] params = {s};
                String title = getLocalizedMessage(
                        PartDesignViewRB.SAVE_PART_FAIL, params);
                JOptionPane.showMessageDialog(getParentFrame(), qre
                        .getClientMessage(), title,
                        JOptionPane.ERROR_MESSAGE);
            }
            setListEditor();
        }
    }


    /**
     * �༭���԰�ť�Ķ�����
     *
     * @param e ActionEvent
     */
    void editAttributesJButton_actionPerformed(ActionEvent e)
    {
        if(ibaHolder == null)
        {
            ibaHolder = (IBAHolderIfc) getPartItem().getPart();
            if(ibaHolder.getAttributeContainer() == null)
            {
                try
                {
                    //Ҫ���õķ����еĲ�������ļ���
                    Class[] classes = {IBAHolderIfc.class, Object.class,
                            Locale.class, MeasurementSystemDefaultView.class};
                    //Ҫ���õķ����еĲ����ļ���
                    Object[] objects = {ibaHolder, null,
                            RemoteProperty.getVersionLocale(), null};
                    //����񷢳�����,ȡ�ý�����ϣ��û�ֵ����UserInfo�ļ��ϣ�
                    ibaHolder = (IBAHolderIfc) IBAUtility.invokeServiceMethod(
                            "IBAValueService", "refreshAttributeContainer",
                            classes, objects);
                }
                catch (QMRemoteException remoteexception)
                {
                    remoteexception.printStackTrace();
                    ibaHolder
                            .setAttributeContainer(new DefaultAttributeContainer());
                }
            }
        }
        AbstractLiteObject classificationstructdefaultview = null;
        try
        {
            classificationstructdefaultview = NavigationUtil
                    .getClassificationStructure(getPartItem().getPart()
                            .getClass().getName());
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        try
        {
            Component parentComponent = IBAUtility.getParentComponent(this);//Begin CR4
            IBADialog ibadialog = null;
            if(parentComponent instanceof JFrame)
                ibadialog = new IBADialog((JFrame)parentComponent, ResourceBundle.getBundle("com.faw_qm.iba.value.util.ValueResource", Locale.getDefault()), ibaHolder,
                        classificationstructdefaultview, mode != VIEW_MODE);
            else
                ibadialog = new IBADialog((JDialog)parentComponent, ResourceBundle.getBundle("com.faw_qm.iba.value.util.ValueResource", Locale.getDefault()), ibaHolder,
                        classificationstructdefaultview, mode != VIEW_MODE);//End CR4
            ibaHolder = (IBAHolderIfc)ibadialog.showDialog();
        }catch(Throwable _throw)
        {
            _throw.printStackTrace();
        }
    }


   

    /**
     * ���ػ���
     */
    protected void localize()
    {
        try
        {
           
            // command buttons
            okJButton.setText(getLabelsRB().getString("okJButton"));
            saveJButton.setText(getLabelsRB().getString("saveJButton"));            
            if(mainPart)
            {
                viewUsageJB
                        .setText(getLabelsRB().getString("viewUsageJButton"));
                addUsageJB.setText(getLabelsRB().getString("addUsageJButton"));
                removeUsageJB.setText(getLabelsRB().getString(
                        "removeUsageJButton"));
                cancelJButton.setText(getLabelsRB().getString("revertJButton"));
                //liyz add ��ʾ���ð�ť
                partAttrSetJButton.setText(getLabelsRB().getString("partAttrSetJButton"));//end
            }
            else
            {
                cancelJButton.setText(getLabelsRB().getString("cancelJButton"));
            }
            editAttributesJButton.setText(getLabelsRB().getString(
                    "editAttributesJButton"));
           
            
        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
    }


    /**
     * ������󳤶ȡ�
     */
    protected void setMaxLengths()
    {
    }


    /**
     * ����㲿������Դ���Ե�������
     * @return  ������
     */
    protected String getSourceClassName()
    {
        return getLocalizedValue(PartDesignViewRB.QMPART_SOURCE);
    }


    /**
     * ����㲿�����������Ե�������
     * @return  ������
     */
    protected String getTypeClassName()
    {
        return getLocalizedValue(PartDesignViewRB.QMPART_TYPE);
    }


    /**
     * ����㲿�������Ե���塣
     * @return  ��ǩҳ��塣
     */
    public JPanel getTab()
    {
        return this;
    }

    /**
     * ��ӷ��ఴť��
     */
    public synchronized void addClassificationButtons()
    {
        try
        {
            classificationstructdefaultview = NavigationUtil
                    .getClassificationStructure(QMPartInfo.class.getName());
            if(classificationstructdefaultview != null)
            {
                classifiercontrol = (ClassifierControl) Class
                        .forName(
                                "com.faw_qm.csm.client.classification.model.ClassifierClassifyButton")
                        .newInstance();
                classifiercontrol2 = (ClassifierControl) Class
                        .forName(
                                "com.faw_qm.csm.client.classification.model.ClassifierRemoveButton")
                        .newInstance();
                classifiercontrol.setStructure(classificationstructdefaultview);
                classifiercontrol2
                        .setStructure(classificationstructdefaultview);
                ibaAttributesJPanel.addControl(classifiercontrol, classifiercontrol2);//CR5
                contentsJTabbedPane.repaint();
//                ibaAttributesJPanel.refreshControls();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    /**
     * ˢ�·�����ơ�
     */
    public synchronized void refreshClassificationControl()
    {
        ibaAttributesJPanel.refreshControls();
    }
//    /**
//     * ��ʼ���������
//     */
//    public synchronized void initializeClassificationControl()
//    {
//        ibaAttributesJPanel.initializeControls();
//    }

    /**
     * ��list�ӵ�ʹ�ýṹ����У���list�а���ʹ�ýṹ�����ݡ�
     *
     * @param  list QMPartList
     */
    public void initList(QMPartList list, boolean b)
    {
        structList = list;
        structList.setTaskPanel(this);
        if(b)
        {
            setListEditor();
        }
        addStructJPanel.add(structList, new GridBagConstraints(0, 0, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        addStructJPanel.add(useButtonJPanel, new GridBagConstraints(0, 1, 1, 1,
                0.0, 0.0, GridBagConstraints.SOUTHEAST,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    }

    public void addList(QMPartList list)
    {
        structList = list;
        structList.setTaskPanel(this);
        addStructJPanel.add(structList, new GridBagConstraints(0, 0, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        addStructJPanel.add(useButtonJPanel, new GridBagConstraints(0, 1, 1, 1,
                0.0, 0.0, GridBagConstraints.SOUTHEAST,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    }

    /**����ʹ�ýṹ�е���������λ���Ӳ�����BsoID*/
    private Object[][] partData;

    /**����ο���ϵ���ĵ���BsoID*/
    private String[] docBsoID;

    /**�㲿����ʹ���б�*/
    private QMPartList structList = new QMPartList(10);   

    /**
     * �ı���ʾ�ı�ǩҳ��
     *
     * @param i int:Ҫ��ʾ�ı�ǩҳ���루��0��ʼ����
     */
    public void updateTabbed(int i)
    {
        contentsJTabbedPane.setSelectedIndex(i);
    }   

    /**
     * ��������������Ҫʹ�õ����㲿����
     * @param event the action event
     */
    void addUsageJButton_actionPerformed(ActionEvent event)
    {
    	
    	//CCBegin SS3
    	
//        PartDebug.debug("addUsageJButton_actionPerformed(e) begin...", this,
//                PartDebug.PART_CLIENT);
//        //��ø�����
//        JFrame frame = getParentFrame();
//        String partBsoName = QMMessage.getLocalizedMessage(RESOURCE,
//                PartDesignViewRB.QMPM_BSONAME, null);
//        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle",
//                null);
//        //����������
//        QmChooser qmChooser = new QmChooser(partBsoName, title, frame);
//        qmChooser.setChildQuery(false);
//        qmChooser.setRelColWidth(new int[]{1, 1});
//        PartDebug.debug("************���������㲿���ɹ���������qmChooser = " + qmChooser,
//                PartDebug.PART_CLIENT);
//        //���ո���������ִ������
//        qmChooser.addListener(new QMQueryListener()
//        {
//            public void queryEvent(QMQueryEvent e)
//            {
//                qmChooser_queryEvent(e);
//            }
//        });
//        qmChooser.setVisible(true);
//        PartDebug.debug(
//                "addUsageJButton_actionPerformed(e) end...return is void",
//                this, PartDebug.PART_CLIENT);
    	
    	
    	
    	 AddSearchJDialog searchDialog = new AddSearchJDialog( getParentFrame());
         searchDialog.setSize(700, 650);
         //ʹ��������Ļ������ʾ
         PartScreenParameter.setLocationCenter(searchDialog);
         searchDialog.setVisible(true);

    	//CCEnd SS3
    	
    	
    	
    }
    
    

    
  //CCBegin SS3
    
    /**��չ�����Ŀ����ʹ��ѯ����б�֧�����˫������*/
    class MyQuery extends CappQuery {
        public CappMultiList getResultList() {
            return this.getMyList();
        }
    }
    
    //CCEnd SS3
    
    /**
     * �����㲿�������¼�������
     * @param e ���������¼���
     */
    public void qmChooser_queryEvent2(CappQueryEvent e)
    {
        PartDebug.debug("qmChooser_queryEvent(e) begin...", this,
                PartDebug.PART_CLIENT);
        //��ǰ��������µ��㲿��ֵ����
        //2003/12/18
        if(getPartItem() != null)
        {
            partIfc1 = getPartItem().getPart();
        }
        PartItem[] partItem;
        Explorable[] usageItem;
        String[][] result = null;
        int begin = usesList.size();
        int len = 0;
        int m = 0;
        PartDebug.debug("��ǰ��������µ��㲿��ֵ���� partIfc1 = " + partIfc1,
                PartDebug.PART_CLIENT);
        if(e.getType().equals(CappQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(CappQuery.OkCMD))
            {
                //��������������������������㲿��
            	MyQuery c = (MyQuery) e.getSource();
                BaseValueIfc[] bvi = c.getSelectedDetails();
                PartDebug.debug("�����ѡ��������㲿�� bvi = " + bvi,
                        PartDebug.PART_CLIENT);
                if(bvi != null)
                {
                    partItem = new PartItem[bvi.length];
                    //CCBegin SS1
                    //CCBegin SS2
                    //add by zhangqiang 070104 begin
                   // result = new String[bvi.length][10];
                    //result = new String[bvi.length][12];
                    result = new String[bvi.length][13];
                    //CCEnd SS2
                    //CCEnd SS1
                    //add by zhangqiang 070104 end
                      usageItem = new Explorable[bvi.length];
                    for (int i = 0; i < bvi.length; i++)
                    {
                        PartDebug.debug("************partIfc1 = " + partIfc1,
                                PartDebug.PART_CLIENT);
                        //��ѡ���ĳһ�㲿��
                        BaseValueIfc newPart = bvi[i];
                        
                        
                        QMPartIfc partIfc2 = (QMPartIfc)newPart;
                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc)partIfc2.getMaster();
                        
                        
                        
                        PartDebug.debug("���Ҫ��ӵ��㲿��������С�汾 partIfc2 = "
                                + partIfc2, PartDebug.PART_CLIENT);
                        //�����ж�partIfc1��partIfc2�Ƿ�ʹ�ýṹǶ��
                        boolean flag = false;
                        //�����ǰ�Ǹ����㲿�����棬�����partIfc1��partIfc2�Ƿ�ʹ�ýṹǶ��
                        if(partIfc1.getBsoID() != null && partIfc2 != null)
                        {
                            flag = isParentPart(partIfc1, partIfc2);
                        }
                        //�������Ϊ�棬��ʾʹ�ýṹǶ�ס����򣬽�һ������Ƿ��ظ����
                        if(flag == true)
                        {
                            //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
                            DisplayIdentity displayidentity = IdentityFactory
                                    .getDisplayIdentity(partIfc2);
                            //��ö������� + ��ʶ
                            String s = displayidentity
                                    .getLocalizedMessage(null);
                            Object[] params = {s};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.PART_CF_NESTING, params);
                            String title = getLocalizedValue(PartDesignViewRB.USE_CF_NESTING);
                            JOptionPane
                                    .showMessageDialog(getParentFrame(),
                                            message, title,
                                            JOptionPane.WARNING_MESSAGE);
                            m++;
                        }
                        else
                        {
                            //�����㲿��ʹ�ù�ϵ��ģ����
                            UsageInterfaceItem usageInterfaceItem = new UsageInterfaceItem(
                                    getPartItem(), newPartMaster);
                            //Ϊ����ӵ��㲿������Ĭ�ϵ�λ
                            usageInterfaceItem.setUnit(newPartMaster
                                    .getDefaultUnit());
                            //��ͨ�������㲿����������
                            usesList.insertElementAt(usageInterfaceItem, 0);
                            //add 0429 begin ���޸����MasterΪ�յ������
                          if(partIfc2 == null)
                          {
                              usageItem[i - m]= new PartMasterItem(
                                          newPartMaster);
//                                  result[i - m][0] = newPartMaster.getPartNumber();
//                                  result[i - m][1] = newPartMaster.getPartName();
//                                  result[i - m][5] = usageInterfaceItem.getUnits()
//                                          .getDisplay();
//                                  result[i - m][9] = usageInterfaceItem.getId();
                              
                              /********liyz add begin*******/ 
                              //��ʹ�ýṹ�б�������㲿��ʱ,�б�ͷ�������е�λ�����Ӧ
                              //add number
                              PartUsageList number = PartUsageList.toPartUsageList("number");
                              String[] numHead = structList.getHeadings();
                              int numCol = 0;
                              for(int num=0;num<numHead.length;num++)
                              {
                            	  String s = numHead[num];
                            	  if(s.equals(number.getDisplay()))
                            	  {
                            		numCol = num;  
                            	  }
                              }
                              result[i - m][numCol] = newPartMaster.getPartNumber();
                              //add name
                              PartUsageList name = PartUsageList.toPartUsageList("name");
                              String[] nameHead = structList.getHeadings();
                              int nameCol = 0;
                              for(int n=0;n<nameHead.length;n++)
                              {
                            	  String s = nameHead[n];
                            	  if(s.equals(name.getDisplay()))
                            	  {
                            		  nameCol = n;
                            	  }
                              }
                              result[i - m][nameCol] = newPartMaster.getPartName();
                              //add unit
                              PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                              String[] unitHead=structList.getHeadings();
                              int unitCol = 0;
                              for(int un=0;un<unitHead.length;un++)
                              {
                              	String s =unitHead[un];
                              	if(s.equals(unit.getDisplay()))
                              	{
                              		unitCol=un;
                              	}            	                	
                              }
                              result[i - m][unitCol] = usageInterfaceItem.getUnits().getDisplay();
                              //add usageLinkId
                              String[] head=structList.getHeadings();
                              int col = 0;
                              for(int k=0;k<head.length;k++)
                              {            	                	
                                col=k;                	
                              }
                              result[i - m][col+1] = usageInterfaceItem.getId();
                              
                              /************** liyz add end ******************/
                          }
                          else
                          {
                              partItem[i - m] = new PartItem(partIfc2);
                              PartItem partItem1 = new PartItem(partIfc1);
                              usageItem[i - m] = new UsageItem(partItem[i - m],
                                      partItem1);
                              ((UsageItem)usageItem[i - m]).setUnit(newPartMaster
                                      .getDefaultUnit());

                              /********liyz add begin*******/ 
                              //��ʹ�ýṹ�б�������㲿��ʱ,�б�ͷ�������е�λ�����Ӧ
                              //add number
                              PartUsageList number = PartUsageList.toPartUsageList("number");
                              String[] numHead = structList.getHeadings();
                              int numCol = 0;
                              for(int num=0;num<numHead.length;num++)
                              {
                            	  String s = numHead[num];
                            	  if(s.equals(number.getDisplay()))
                            	  {
                            		numCol = num;  
                            	  }
                              }
                              result[i - m][numCol] = newPartMaster.getPartNumber();
                              //add name
                              PartUsageList name = PartUsageList.toPartUsageList("name");
                              String[] nameHead = structList.getHeadings();
                              int nameCol = 0;
                              for(int n=0;n<nameHead.length;n++)
                              {
                            	  String s = nameHead[n];
                            	  if(s.equals(name.getDisplay()))
                            	  {
                            		  nameCol = n;
                            	  }
                              }
                              result[i - m][nameCol] = newPartMaster.getPartName();
                              //add version
                              PartUsageList version = PartUsageList.toPartUsageList("iterationID");
                              String[] versionHead=structList.getHeadings();
                              int versionCol = 0;
                              for(int ver=0;ver<versionHead.length;ver++)
                              {
                              	String s =versionHead[ver];
                              	if(s.equals(version.getDisplay()))
                              	{
                              		versionCol=ver;
                              	}            	                	
                              }
                              result[i - m][versionCol] = partIfc2.getVersionValue();
                              //add view
                              PartUsageList view = PartUsageList.toPartUsageList("viewName");
                              String[] viewHead = structList.getHeadings();
                              int viewCol =0;
                              for(int vi=0;vi<viewHead.length;vi++)
                              {
                            	String s = viewHead[vi];
                            	if(s.equals(view.getDisplay()))
                            	{
                            		viewCol = vi;
                            	}
                              }
                              result[i - m][viewCol] = partIfc2.getViewName();
                              //add unit
                              PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                              String[] unitHead=structList.getHeadings();
                              int unitCol = 0;
                              for(int un=0;un<unitHead.length;un++)
                              {
                              	String s =unitHead[un];
                              	if(s.equals(unit.getDisplay()))
                              	{
                              		unitCol=un;
                              	}            	                	
                              }
                              result[i - m][unitCol] = usageInterfaceItem.getUnits().getDisplay();
                              //add type
                              PartUsageList type = PartUsageList.toPartUsageList("type");
                              String[] typeHead = structList.getHeadings();
                              int typeCol = 0;
                              for(int t=0;t<typeHead.length;t++)
                              {
                            	  String s =typeHead[t];
                            	  if(s.equals(type.getDisplay()))
                            	  {
                            		  typeCol = t;
                            	  }
                              }
                              result[i - m][typeCol] = partIfc2.getPartType().getDisplay();
                              //add producedBy
                              PartUsageList produced =PartUsageList.toPartUsageList("producedBy");
                              String[] producedHead = structList.getHeadings();
                              int producedCol = 0;
                              for(int p=0;p<producedHead.length;p++)
                              {
                            	  String s = producedHead[p];
                            	  if(s.equals(produced.getDisplay()))
                            	  {
                            		  producedCol =p;
                            	  }
                              }
                              result[i - m][producedCol] = partIfc2.getProducedBy().getDisplay();
                              //add lifeCycleState
                              PartUsageList state =PartUsageList.toPartUsageList("state");
                              String[] stateHead = structList.getHeadings();
                              int stateCol = 0;
                              for(int l=0;l<stateHead.length;l++)
                              {
                            	  String s = stateHead[l];
                            	  if(s.equals(state.getDisplay()))
                            	  {
                            		  stateCol =l;
                            	  }
                              }
                              result[i - m][stateCol] = partIfc2.getLifeCycleState().getDisplay();
                              //add usageLinkId
                              String[] head=structList.getHeadings();
                              int col = 0;
                              for(int k=0;k<head.length;k++)
                              {            	                	
                                col=k;                	
                              }
                              result[i - m][col+1] = usageInterfaceItem.getId();
                              /************** liyz add end ******************/
                              //CCBegin SS1
                              PartUsageList bomItem = PartUsageList.toPartUsageList("bomItem");
                              String[] bomHead = structList.getHeadings();
                              int bomCol =0;
                              for(int b=0;b<bomHead.length;b++)
                              {
                            	String s = bomHead[b];
                            	if(s.equals(bomItem.getDisplay()))
                            	{
                            		bomCol = b;
                            	}
                              }
                              result[i - m][bomCol] = usageInterfaceItem.getBomItem();
                              PartUsageList subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber");
                              String[] subHead = structList.getHeadings();
                              int subCol =0;
                              for(int z=0;z<subHead.length;z++)
                              {
                            	String s = bomHead[z];
                            	if(s.equals(subUnitNumber.getDisplay()))
                            	{
                            		subCol = z;
                            	}
                              }
                              result[i - m][subCol] = usageInterfaceItem.getSubUnitNumber();
                              //CCEnd SS1
                               //CCBegin SS2
                              PartUsageList proVersion = PartUsageList.toPartUsageList("proVersion");
                              String[] proHead = structList.getHeadings();
                              int proCol =0;
                              for(int b=0;b<proHead.length;b++)
                              {
                            	String s = proHead[b];
                            	if(s.equals(proVersion.getDisplay()))
                            	{
                            		proCol = b;
                            	}
                              }
                              result[i - m][proCol] = usageInterfaceItem.getProVersion();
                              //CCEnd SS2
                         }
                           String quan = String.valueOf(usageInterfaceItem
                                   .getQuantityString());
                           if(quan.endsWith(".0"))
                           {
                               quan = quan.substring(0, quan.length() - 2);
                           }
                           //result[i - m][4] = quan;
                           //liya add quantity
                           PartUsageList quantity = PartUsageList.toPartUsageList("quantityString");
                           String[] quanHead=structList.getHeadings();
                           int quanCol = 0;
                           for(int q=0;q<quanHead.length;q++)
                           {
                           	String s =quanHead[q];
                           	if(s.equals(quantity.getDisplay()))
                           	{
                           		quanCol=q;
                           	}            	                	
                           }
                           result[i - m][quanCol] = quan;
                           //end
                        }
                    }
                    len = bvi.length - m;
                }
                else
                {
                    return;
                }
                setValues();
                	try
					{
						setMethods();
					} catch (QMException e1)
					{						
						e1.printStackTrace();
					}
                structList.addDetail(usageItem, result, begin, len);
                usesMultiList_itemStateChanged(null);
            }
        }
        PartDebug.debug("qmChooser_queryEvent(e) end...return is void", this,
                PartDebug.PART_CLIENT);
    }
    
    //CCBegin SS3
    class AddSearchJDialog extends JDialog {
        /** ��ѯ�� */
        private MyQuery cappQuery = new MyQuery();

        /** Ҫ��ѯ��ҵ����� */
        public  String SCHEMA;

        /** ��ѯ���� */
        private CappSchema mySchema;

        private GridBagLayout gridBagLayout1 = new GridBagLayout();

        /**��Ʒ�ṹ������������*/
        private JFrame managerJFrame = new JFrame();

        /** ���ڱ����Դ�ļ�·�� */
        protected  String RESOURCE = "com.faw_qm.cappclients.capprouye.util.CappRouteRB";

        /** ���Ա��� */
        private  boolean verbose = (RemoteProperty.getProperty(
                "com.faw_qm.cappclients.verbose", "true")).equals("true");
        
        Hashtable copyCodingClassifications=null;
        
        public AddSearchJDialog(JFrame frame){
        	
        	  super(frame, "", true);
              
              
              SCHEMA = "C:QMPart; G:��������;A:partNumber;A:partName;A:versionValue;A:viewName";
              //�����ѯ����
              try {
                mySchema = new CappSchema(SCHEMA);
              }
              catch (QMRemoteException ex) {
                String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                        null);
                JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                        JOptionPane.INFORMATION_MESSAGE);
               return ;
              }
              //���ò�ѯ����
              cappQuery.setSchema(mySchema);
//              cappQuery.notAcessInPersonalFolder();
              cappQuery.setLastIteration(true);

              
              
              setSingleSelectMode();
              
              managerJFrame = frame;
              try {
                  jbInit();
                  
                  addDefaultListener();
                  
              } catch (Exception e) {
                  e.printStackTrace();
              }

        	
        }

      

        /**
         * ��ʼ��
         *
         * @throws Exception
         */
        private void jbInit() throws Exception {
            this.setTitle("�������");
            this.setSize(650, 500);
            this.getContentPane().setLayout(gridBagLayout1);
            //���沼�ֹ���
            this.getContentPane().add(
                    cappQuery,
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 10, 0, 10), 0, 0));
        }

        /**
         * ���Ĭ�ϲ�ѯ����
         */
        public void addDefaultListener() {
            cappQuery.addListener(new CappQueryListener() {
                public void queryEvent(CappQueryEvent e) {
                    cappQuery_queryEvent(e);
                }
            });
        }

        /**
         * ��Ӳ�ѯ����
         *
         * @param s
         *            ��ѯ����
         */
        public void addQueryListener(CappQueryListener s) {
            cappQuery.addListener(s);
        }

        /**
         * ɾ����ѯ����
         *
         * @param s
         *            ��ѯ����
         */
        public void removeQueryListener(CappQueryListener s) {
            cappQuery.removeListener(s);
        }
  

        /**
         * ���������¼�����
         *
         * @param e
         *            ���������¼�
         */
        public void cappQuery_queryEvent(CappQueryEvent e) {
        	
        	PartTaskJPanel.this.qmChooser_queryEvent2(e);
          

            
        }
        
       
        

        /**
         * �����б�Ϊ��ѡģʽ
         */
        public void setSingleSelectMode() {
            try {
                cappQuery.setMultipleMode(false);
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * ���ý������ʾλ��
         */
        private void setViewLocation() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = this.getSize();
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }
            this.setLocation((screenSize.width - frameSize.width) / 2,
                    (screenSize.height - frameSize.height) / 2);

        }

        /**
         * ���ظ��෽����ʹ������ʾ����Ļ����
         *
         * @param flag
         */
        public void setVisible(boolean flag) {
            this.setViewLocation();
            super.setVisible(flag);
        }



        /**
         * MultiList����¼�
         * @param lis
         */
        public void addMultiListActionListener(ActionListener lis) {
            this.cappQuery.getResultList().addActionListener(lis);
        }

    }
  //CCEnd SS3
    

    /**
     * �����㲿��������Ϣ������㲿��������С�汾��
     * @param partMaster  �㲿��������Ϣ��
     * @return  �㲿��������С�汾��
     */
    private QMPartIfc getLastedIterations(QMPartMasterIfc partMaster)
    {
        PartDebug.debug("getLastedIterations() begin...", this,
                PartDebug.PART_CLIENT);
        QMPartIfc partIfc = null;
        //���÷��񷽷�������㲿��������С�汾
        Class[] paraClass = {MasteredIfc.class};
        Object[] objs = {partMaster};
        Collection collection = null;
        try
        {
            collection = (Collection) IBAUtility
                    .invokeServiceMethod("VersionControlService",
                            "allIterationsOf", paraClass, objs);
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane
                    .showMessageDialog(getParentFrame(), ex.getClientMessage(),
                            title, JOptionPane.ERROR_MESSAGE);
        }
        Iterator iterator = collection.iterator();
        if(iterator.hasNext())
        {
            //����㲿��������С�汾
            partIfc = (QMPartIfc) iterator.next();
        }
        PartDebug.debug("getLastedIterations() end...return " + partIfc, this,
                PartDebug.PART_CLIENT);
        return partIfc;
    }

    /**
     * �����㲿��part2�Ƿ����㲿��part1�����Ȳ�������part1����
     * @param part1  ָ�����㲿����ֵ����
     * @param part2  ��������㲿����ֵ����
     * @return ����㲿��part2���㲿��part1�����Ȳ�������part1�����򷵻�true�����򷵻�false��
     */
    private  boolean isParentPart(QMPartIfc part1, QMPartIfc part2)
    {
        PartDebug.debug("isParentPart() begin...", this, PartDebug.PART_CLIENT);
        Boolean flag1 = new Boolean(false);
        try
        {
            //���÷��񷽷����ж�partIfc1��partIfc2�Ƿ�ʹ�ýṹǶ��
            flag1 = Boolean.valueOf(PartServiceRequest.isParentPart(part1,
                    part2));
            PartDebug.debug("�Ƿ�ʹ�ýṹǶ��(Boolean) flag1 = " + flag1,
                    PartDebug.PART_CLIENT);
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane
                    .showMessageDialog(getParentFrame(), ex.getClientMessage(),
                            title, JOptionPane.ERROR_MESSAGE);
        }
        boolean flag = flag1.booleanValue();
        PartDebug.debug("isParentPart() end...return is " + flag, this,
                PartDebug.PART_CLIENT);
        return flag;
    }

    /**
     * ����ʹ�ýṹ�б��е�ÿ���㲿���������͵�λ��
     */
    protected void setValues()
    {
        PartDebug.debug("setValues() begin ....", this, PartDebug.PART_CLIENT);
        //ʹ���б������
        int size = structList.getNumberOfRows();
        //����ʹ���б��е������͵�λֵ
        for (int row = 0; row < size; row++)
        {
            //���ݵ�ǰ�㲿����ID��ȡ�����еĶ�Ӧ��ʹ�ù�����UsageInterfaceItem
//            UsageInterfaceItem usage = usesList.interfaceElementAt(structList
//                    .getCellText(row, 9));
            //liyz add            
            String[] head=structList.getHeadings();
            int col = 0;
            for(int k=0;k<head.length;k++)
            {            	                	
            	col=k;                	
            }
            UsageInterfaceItem usage = usesList.interfaceElementAt(structList
                    .getCellText(row, col+1));            
            //end
            float gui_quantity = 0; //�����ĸ���ֵ
            //��������ֵ
            //gui_quantity = Float.parseFloat(dtm.getValueAt(row, 4).toString());
            //liyz add
            PartUsageList quantity = PartUsageList.toPartUsageList("quantityString");
            String[] quanHead=structList.getHeadings();
            int quanCol = 0;
            boolean flag = false;
            for(int m=0;m<quanHead.length;m++)
            {
            	String s =quanHead[m];
            	if(s.equals(quantity.getDisplay()))
            	{
            		flag = true;
            		quanCol=m;
            	} 
            
            }
            //CCBegin SS1
            boolean sunflag = false;
            PartUsageList subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber");
            String[] subHead=structList.getHeadings();
            int sunCol = 0;
            for(int m=0;m<subHead.length;m++)
            {
            	String s =subHead[m];
               if(s.equals(subUnitNumber.getDisplay()))
            	{
            	   sunflag=true;
            	   sunCol=m;
            	}  
            	
            } 
            PartUsageList bomItem = PartUsageList.toPartUsageList("bomItem");
            String[] bomHead=structList.getHeadings();
            int bomCol = 0;
            boolean  bomflag = false;
            for(int m=0;m<bomHead.length;m++)
            {
            	String s =bomHead[m];
               if(s.equals(bomItem.getDisplay()))
            	{
            	   bomflag=true;
            		bomCol=m;
            	}   
            	
            } 
         
           
            
          //CCend SS1
            //CCBegin SS2
            boolean proflag = false;
            PartUsageList proVersion = PartUsageList.toPartUsageList("proVersion");
            String[] proHead=structList.getHeadings();
            int proCol = 0;
            for(int m=0;m<proHead.length;m++)
            {
            	String s =proHead[m];
               if(s.equals(proVersion.getDisplay()))
            	{
            	   proflag=true;
            	   proCol=m;
            	}  
            	
            } 
          //CCend SS2
            //�ж�ʹ�ýṹ�б����Ƿ���������
            if(flag)
            {
            	gui_quantity = Float.parseFloat(dtm.getValueAt(row, quanCol).toString());
            }            
            //end
            //���ʹ�ù������е����������ڵ�ǰ�㲿��������ֵ
            if(usage.getQuantity() != gui_quantity)
            {
                //��������Ϊ��ǰ�㲿��������
                usage.setQuantity(gui_quantity);
            }
            //���õ�λֵ
            //��ǰ��ѡ�еĵ�λ
            Unit gui_unit = getSelectedUnit(row);
            //          Unit gui_unit = Unit.toUnit(dtm.getValueAt(row, 5).toString().trim());
            //ʹ�ù������е�������λ
            Unit saved_unit = usage.getUnits();
            //���ʹ�ù������е�������λΪ��,���߲����ڵ�ǰ�㲿���ĵ�λ��������
            //ʹ�ù������е�������λΪ��ǰ�㲿���ĵ�λ
            if(saved_unit == null)
            {
                //���õ�λΪ��ǰ�㲿���ĵ�λ
                usage.setUnit(gui_unit);
            }
            else if(!saved_unit.equals(gui_unit))
            {
                usage.setUnit(gui_unit);
            }
            //CCBegin SS1
            String gui_sun ="";
            Object value = null;
            if(sunflag)
            {
            	value = dtm.getValueAt(row, sunCol);
            	if(value!=null){
            		gui_sun = value.toString();
            	}
            }
            String gui_bomItem ="";
            if(bomflag)
            {
            	 value = dtm.getValueAt(row, bomCol);
              	if(value!=null){
              		gui_bomItem = value.toString();
              	}
            }

        
            if(usage.getSubUnitNumber() != gui_sun)
            {
                usage.setSubUnitNumber(gui_sun);
            }
            if(usage.getBomItem() != gui_bomItem)
            {
                usage.setBomItem(gui_bomItem);
            }
            //CCEnd SS1
             //CCBegin SS2
            String gui_pro ="";
            if(proflag)
            {
            	value = dtm.getValueAt(row, proCol);
            	if(value!=null){
            		gui_pro = value.toString();
            	}
            }
             if(usage.getProVersion() != gui_pro)
            {
                usage.setProVersion(gui_pro);
            }
           //CCEnd SS2
        }
        PartDebug.debug("setValues() end ....return is void", this,
                PartDebug.PART_CLIENT);
    }

    /**
     * ���ʹ�ýṹ�б���ָ���еĵ�λ��
     * @param row ʹ�ýṹ�б���ָ�����С�
     * @return foundUnit ʹ�ýṹ�б���ָ���еĵ�λ��
     */
    public Unit getSelectedUnit(int row)
    {
        PartDebug.debug("getSelectedUnit(int row) begin ...", this,
                PartDebug.PART_CLIENT);
        Unit foundUnit = null;
        //ָ���еĵ�λֵ���ַ�����
        //String stringUnit = dtm.getValueAt(row, 5).toString();
        //liyz add
        PartUsageList unit =PartUsageList.toPartUsageList("unitName");
        String[] unitHead = structList.getHeadings();
        int unitCol = 0;
        boolean flag = false;
        for(int un=0;un<unitHead.length;un++)
        {
        	String s =unitHead[un];
        	if(s.equals(unit.getDisplay()))
        	{
        		flag = true;
        		unitCol = un;
        	}        	
        }
        if(flag)
        {
        String stringUnit = dtm.getValueAt(row, unitCol).toString();
        //end
        
        //��õ�λ��������������
        Unit[] types = Unit.getUnitSet();
        //���ָ���㲿���ĵ�λ��ĳ��λ����������������øõ�λ��������
        for (int i = 0; i < types.length; i++)
        {
            if(stringUnit.equals(types[i].getDisplay()))
            {
                foundUnit = types[i];
                break;
            }
        }
        }
        PartDebug.debug("getSelectedUnit(int row) end...return " + foundUnit,
                this, PartDebug.PART_CLIENT);
        return foundUnit;
    }

    /**��ǰ��������µ��㲿��ֵ����*/
    private QMPartIfc partIfc1 = null;

    /**ʹ����Ӻ�ɾ��������*/
    protected UsesInterfaceList usesList = null;

    /**
     * �����㲿�������¼�������
     * @param e ���������¼���
     */
    public void qmChooser_queryEvent(QMQueryEvent e)
    {
        PartDebug.debug("qmChooser_queryEvent(e) begin...", this,
                PartDebug.PART_CLIENT);
        //��ǰ��������µ��㲿��ֵ����
        //2003/12/18
        if(getPartItem() != null)
        {
            partIfc1 = getPartItem().getPart();
        }
        PartItem[] partItem;
        Explorable[] usageItem;
        String[][] result = null;
        int begin = usesList.size();
        int len = 0;
        int m = 0;
        PartDebug.debug("��ǰ��������µ��㲿��ֵ���� partIfc1 = " + partIfc1,
                PartDebug.PART_CLIENT);
        if(e.getType().equals(QMQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(QmQuery.OkCMD))
            {
                //��������������������������㲿��
                QmChooser c = (QmChooser) e.getSource();
                BaseValueIfc[] bvi = c.getSelectedDetails();
                PartDebug.debug("�����ѡ��������㲿�� bvi = " + bvi,
                        PartDebug.PART_CLIENT);
                if(bvi != null)
                {
                    partItem = new PartItem[bvi.length];
                    //CCBegin SS1
                    //CCBegin SS2
                    //add by zhangqiang 070104 begin
                   // result = new String[bvi.length][10];
                    //result = new String[bvi.length][12];
                    result = new String[bvi.length][13];
                    //CCEnd SS2
                    //CCEnd SS1
                    //add by zhangqiang 070104 end
                      usageItem = new Explorable[bvi.length];
                    for (int i = 0; i < bvi.length; i++)
                    {
                        PartDebug.debug("************partIfc1 = " + partIfc1,
                                PartDebug.PART_CLIENT);
                        //��ѡ���ĳһ�㲿��
                        BaseValueIfc newPart = bvi[i];
                        //����㲿��������Ϣ
                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc) newPart;
                        //���Ҫ��ӵ��㲿��������С�汾
                        QMPartIfc partIfc2 = this
                                .getLastedIterations(newPartMaster);
                        PartDebug.debug("���Ҫ��ӵ��㲿��������С�汾 partIfc2 = "
                                + partIfc2, PartDebug.PART_CLIENT);
                        //�����ж�partIfc1��partIfc2�Ƿ�ʹ�ýṹǶ��
                        boolean flag = false;
                        //�����ǰ�Ǹ����㲿�����棬�����partIfc1��partIfc2�Ƿ�ʹ�ýṹǶ��
                        if(partIfc1.getBsoID() != null && partIfc2 != null)
                        {
                            flag = isParentPart(partIfc1, partIfc2);
                        }
                        //�������Ϊ�棬��ʾʹ�ýṹǶ�ס����򣬽�һ������Ƿ��ظ����
                        if(flag == true)
                        {
                            //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
                            DisplayIdentity displayidentity = IdentityFactory
                                    .getDisplayIdentity(partIfc2);
                            //��ö������� + ��ʶ
                            String s = displayidentity
                                    .getLocalizedMessage(null);
                            Object[] params = {s};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.PART_CF_NESTING, params);
                            String title = getLocalizedValue(PartDesignViewRB.USE_CF_NESTING);
                            JOptionPane
                                    .showMessageDialog(getParentFrame(),
                                            message, title,
                                            JOptionPane.WARNING_MESSAGE);
                            m++;
                        }
                        else
                        {
                            //�����㲿��ʹ�ù�ϵ��ģ����
                            UsageInterfaceItem usageInterfaceItem = new UsageInterfaceItem(
                                    getPartItem(), newPartMaster);
                            //Ϊ����ӵ��㲿������Ĭ�ϵ�λ
                            usageInterfaceItem.setUnit(newPartMaster
                                    .getDefaultUnit());
                            //��ͨ�������㲿����������
                            usesList.insertElementAt(usageInterfaceItem, 0);
                            //add 0429 begin ���޸����MasterΪ�յ������
                          if(partIfc2 == null)
                          {
                              usageItem[i - m]= new PartMasterItem(
                                          newPartMaster);
//                                  result[i - m][0] = newPartMaster.getPartNumber();
//                                  result[i - m][1] = newPartMaster.getPartName();
//                                  result[i - m][5] = usageInterfaceItem.getUnits()
//                                          .getDisplay();
//                                  result[i - m][9] = usageInterfaceItem.getId();
                              
                              /********liyz add begin*******/ 
                              //��ʹ�ýṹ�б�������㲿��ʱ,�б�ͷ�������е�λ�����Ӧ
                              //add number
                              PartUsageList number = PartUsageList.toPartUsageList("number");
                              String[] numHead = structList.getHeadings();
                              int numCol = 0;
                              for(int num=0;num<numHead.length;num++)
                              {
                            	  String s = numHead[num];
                            	  if(s.equals(number.getDisplay()))
                            	  {
                            		numCol = num;  
                            	  }
                              }
                              result[i - m][numCol] = newPartMaster.getPartNumber();
                              //add name
                              PartUsageList name = PartUsageList.toPartUsageList("name");
                              String[] nameHead = structList.getHeadings();
                              int nameCol = 0;
                              for(int n=0;n<nameHead.length;n++)
                              {
                            	  String s = nameHead[n];
                            	  if(s.equals(name.getDisplay()))
                            	  {
                            		  nameCol = n;
                            	  }
                              }
                              result[i - m][nameCol] = newPartMaster.getPartName();
                              //add unit
                              PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                              String[] unitHead=structList.getHeadings();
                              int unitCol = 0;
                              for(int un=0;un<unitHead.length;un++)
                              {
                              	String s =unitHead[un];
                              	if(s.equals(unit.getDisplay()))
                              	{
                              		unitCol=un;
                              	}            	                	
                              }
                              result[i - m][unitCol] = usageInterfaceItem.getUnits().getDisplay();
                              //add usageLinkId
                              String[] head=structList.getHeadings();
                              int col = 0;
                              for(int k=0;k<head.length;k++)
                              {            	                	
                                col=k;                	
                              }
                              result[i - m][col+1] = usageInterfaceItem.getId();
                              
                              /************** liyz add end ******************/
                          }
                          else
                          {
                              partItem[i - m] = new PartItem(partIfc2);
                              PartItem partItem1 = new PartItem(partIfc1);
                              usageItem[i - m] = new UsageItem(partItem[i - m],
                                      partItem1);
                              ((UsageItem)usageItem[i - m]).setUnit(newPartMaster
                                      .getDefaultUnit());
//                              result[i - m][0] = newPartMaster.getPartNumber();
//                              result[i - m][1] = newPartMaster.getPartName();
//                              result[i - m][2] = partIfc2.getVersionValue();
//                              result[i - m][3] = partIfc2.getViewName();
//                              result[i - m][5] = usageInterfaceItem.getUnits()
//                                      .getDisplay();
//                              result[i - m][6] = partIfc2.getPartType()
//                                      .getDisplay();
//                              result[i - m][7] = partIfc2.getProducedBy()
//                                      .getDisplay();
//                              result[i - m][8] = partIfc2.getLifeCycleState()
//                                      .getDisplay();
//                              result[i - m][9] = usageInterfaceItem.getId();
                              /********liyz add begin*******/ 
                              //��ʹ�ýṹ�б�������㲿��ʱ,�б�ͷ�������е�λ�����Ӧ
                              //add number
                              PartUsageList number = PartUsageList.toPartUsageList("number");
                              String[] numHead = structList.getHeadings();
                              int numCol = 0;
                              for(int num=0;num<numHead.length;num++)
                              {
                            	  String s = numHead[num];
                            	  if(s.equals(number.getDisplay()))
                            	  {
                            		numCol = num;  
                            	  }
                              }
                              result[i - m][numCol] = newPartMaster.getPartNumber();
                              //add name
                              PartUsageList name = PartUsageList.toPartUsageList("name");
                              String[] nameHead = structList.getHeadings();
                              int nameCol = 0;
                              for(int n=0;n<nameHead.length;n++)
                              {
                            	  String s = nameHead[n];
                            	  if(s.equals(name.getDisplay()))
                            	  {
                            		  nameCol = n;
                            	  }
                              }
                              result[i - m][nameCol] = newPartMaster.getPartName();
                              //add version
                              PartUsageList version = PartUsageList.toPartUsageList("iterationID");
                              String[] versionHead=structList.getHeadings();
                              int versionCol = 0;
                              for(int ver=0;ver<versionHead.length;ver++)
                              {
                              	String s =versionHead[ver];
                              	if(s.equals(version.getDisplay()))
                              	{
                              		versionCol=ver;
                              	}            	                	
                              }
                              result[i - m][versionCol] = partIfc2.getVersionValue();
                              //add view
                              PartUsageList view = PartUsageList.toPartUsageList("viewName");
                              String[] viewHead = structList.getHeadings();
                              int viewCol =0;
                              for(int vi=0;vi<viewHead.length;vi++)
                              {
                            	String s = viewHead[vi];
                            	if(s.equals(view.getDisplay()))
                            	{
                            		viewCol = vi;
                            	}
                              }
                              result[i - m][viewCol] = partIfc2.getViewName();
                              //add unit
                              PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                              String[] unitHead=structList.getHeadings();
                              int unitCol = 0;
                              for(int un=0;un<unitHead.length;un++)
                              {
                              	String s =unitHead[un];
                              	if(s.equals(unit.getDisplay()))
                              	{
                              		unitCol=un;
                              	}            	                	
                              }
                              result[i - m][unitCol] = usageInterfaceItem.getUnits().getDisplay();
                              //add type
                              PartUsageList type = PartUsageList.toPartUsageList("type");
                              String[] typeHead = structList.getHeadings();
                              int typeCol = 0;
                              for(int t=0;t<typeHead.length;t++)
                              {
                            	  String s =typeHead[t];
                            	  if(s.equals(type.getDisplay()))
                            	  {
                            		  typeCol = t;
                            	  }
                              }
                              result[i - m][typeCol] = partIfc2.getPartType().getDisplay();
                              //add producedBy
                              PartUsageList produced =PartUsageList.toPartUsageList("producedBy");
                              String[] producedHead = structList.getHeadings();
                              int producedCol = 0;
                              for(int p=0;p<producedHead.length;p++)
                              {
                            	  String s = producedHead[p];
                            	  if(s.equals(produced.getDisplay()))
                            	  {
                            		  producedCol =p;
                            	  }
                              }
                              result[i - m][producedCol] = partIfc2.getProducedBy().getDisplay();
                              //add lifeCycleState
                              PartUsageList state =PartUsageList.toPartUsageList("state");
                              String[] stateHead = structList.getHeadings();
                              int stateCol = 0;
                              for(int l=0;l<stateHead.length;l++)
                              {
                            	  String s = stateHead[l];
                            	  if(s.equals(state.getDisplay()))
                            	  {
                            		  stateCol =l;
                            	  }
                              }
                              result[i - m][stateCol] = partIfc2.getLifeCycleState().getDisplay();
                              //add usageLinkId
                              String[] head=structList.getHeadings();
                              int col = 0;
                              for(int k=0;k<head.length;k++)
                              {            	                	
                                col=k;                	
                              }
                              result[i - m][col+1] = usageInterfaceItem.getId();
                              /************** liyz add end ******************/
                              //CCBegin SS1
                              PartUsageList bomItem = PartUsageList.toPartUsageList("bomItem");
                              String[] bomHead = structList.getHeadings();
                              int bomCol =0;
                              for(int b=0;b<bomHead.length;b++)
                              {
                            	String s = bomHead[b];
                            	if(s.equals(bomItem.getDisplay()))
                            	{
                            		bomCol = b;
                            	}
                              }
                              result[i - m][bomCol] = usageInterfaceItem.getBomItem();
                              PartUsageList subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber");
                              String[] subHead = structList.getHeadings();
                              int subCol =0;
                              for(int z=0;z<subHead.length;z++)
                              {
                            	String s = bomHead[z];
                            	if(s.equals(subUnitNumber.getDisplay()))
                            	{
                            		subCol = z;
                            	}
                              }
                              result[i - m][subCol] = usageInterfaceItem.getSubUnitNumber();
                              //CCEnd SS1
                               //CCBegin SS2
                              PartUsageList proVersion = PartUsageList.toPartUsageList("proVersion");
                              String[] proHead = structList.getHeadings();
                              int proCol =0;
                              for(int b=0;b<proHead.length;b++)
                              {
                            	String s = proHead[b];
                            	if(s.equals(proVersion.getDisplay()))
                            	{
                            		proCol = b;
                            	}
                              }
                              result[i - m][proCol] = usageInterfaceItem.getProVersion();
                              //CCEnd SS2
                         }
                           String quan = String.valueOf(usageInterfaceItem
                                   .getQuantityString());
                           if(quan.endsWith(".0"))
                           {
                               quan = quan.substring(0, quan.length() - 2);
                           }
                           //result[i - m][4] = quan;
                           //liya add quantity
                           PartUsageList quantity = PartUsageList.toPartUsageList("quantityString");
                           String[] quanHead=structList.getHeadings();
                           int quanCol = 0;
                           for(int q=0;q<quanHead.length;q++)
                           {
                           	String s =quanHead[q];
                           	if(s.equals(quantity.getDisplay()))
                           	{
                           		quanCol=q;
                           	}            	                	
                           }
                           result[i - m][quanCol] = quan;
                           //end
                        }
                    }
                    len = bvi.length - m;
                }
                else
                {
                    return;
                }
                setValues();
                	try
					{
						setMethods();
					} catch (QMException e1)
					{						
						e1.printStackTrace();
					}
                structList.addDetail(usageItem, result, begin, len);
                usesMultiList_itemStateChanged(null);
            }
        }
        PartDebug.debug("qmChooser_queryEvent(e) end...return is void", this,
                PartDebug.PART_CLIENT);
    }
    
    private void setMethods() throws QMException
    {
    	String[] listhead=PartServiceRequest.getListHead("usage");
		String[] headMethod=PartServiceRequest.getListMethod("usage");
		structList.setHeadings(listhead);
    	try
		{			
			structList.setHeadingMethods(headMethod);
		} catch (PropertyVetoException e)
		{
			e.printStackTrace();
		}
		
    }
    
    
    /**
     * �����ȥ��ťʵ�ֵĹ��ܣ�ɾ��ѡ�е��㲿�������û��ѡ�У�����ȥ�Ͳ鿴��ť
     * ʧЧ�������͵�λ����ʧЧ������Ϊ0��
     * @param event ActionEvent
     */
    void removeUsageJButton_actionPerformed(java.awt.event.ActionEvent event)
    {
        removeUsage();
    }

    public void removeUsage()
    {
        PartDebug.debug("removeUsageJButton_actionPerformed��event�� begin ...",
                this, PartDebug.PART_CLIENT);
        //�����ѡ�е��㲿��
        //modify by skx 2004.9.21 ֧������ɾ��ʹ�ù�ϵ
        int[] i = structList.getSelectedRows();
        int m = 0;
        for (int j = 0; j < i.length; j++)
        {
            if(i[j] != -1)
            {
                //��ѡ�е��㲿�����������Ƴ�
                //usesList.removeElementAt(structList.getCellText(i[j] - m, 9));
                //liyz add �б�ͷ��ӦҪɾ������                
                String[] head=structList.getHeadings();
                int col = 0;
                for(int k=0;k<head.length;k++)
                {               	                	
                	col=k;                	
                }
                usesList.removeElementAt(structList.getCellText(i[j] - m, col+1));                
                //end
                //��ѡ�е��㲿����ʹ�ýṹ�б���ɾ��
                dtm.removeRow(i[j] - m);
                m++;
            }
        }
        usesMultiList_itemStateChanged(null);
        PartDebug
                .debug(
                        "removeUsageJButton_actionPerformed��event�� end...return is void",
                        this, PartDebug.PART_CLIENT);
    }

    /**
     * �鿴�㲿�����ԡ�
     * @param event ActionEvent
     */
    void viewUsageJButton_actionPerformed(java.awt.event.ActionEvent event)
    {
        viewSelectedItem();
    }

    /**
     * �鿴ʹ�ýṹ�б��б�ѡ�е��
     */
    public void viewSelectedItem()
    {
        PartDebug.debug("viewSelectedItem() begin....", this,
                PartDebug.PART_CLIENT);
        try
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            structList
                    .setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //ʹ�ýṹ�б��б�ѡ�е���
            int i = structList.getSelectedRow();
            if(i != -1)
            {
                //����ʹ���б�����ѡ���ID��������ж�Ӧ��ʹ�ù�����
                Object ob = structList.getDetail(i).getObject();
                QMPartMasterIfc master = null;
                if(ob instanceof QMPartIfc)
                {
                    master = (QMPartMasterIfc) ((QMPartIfc) ob).getMaster();
                }
                if(ob instanceof QMPartMasterIfc)
                {
                    master = (QMPartMasterIfc) ob;
		            String masterBsoID = master.getBsoID();
		            ///////////////////////modify begin////////////////////////////
		            //modify by muyp 20080507
		            //�޸�ԭ�򣺶��㲿������Ϣ������в鿴��ҳ����ʾ����ȷ(TD1754)
		            ServiceRequestInfo info = new ServiceRequestInfo();
		            info.setServiceName("StandardPartService");
		            info.setMethodName("findPart");
		            Class[] theClass = {QMPartMasterIfc.class};
		            info.setParaClasses(theClass);
		            Object[] objs = {master};
		            info.setParaValues(objs);
		            RequestServer server = RequestServerFactory.getRequestServer();
		            try {
						Collection links = (Collection) server.request(info);
						if(links != null)
						{
		                    Iterator iter = links.iterator();
		                    String partID = "";
		                    if (iter.hasNext())
		                    {
		                    	QMPartIfc link = (QMPartIfc) iter.next();
		                        partID = link.getBsoID();
		                    }
		                    HashMap hashmap = new HashMap();
                            //modify by shf 2003/09/13
        		            hashmap.put("BsoID", masterBsoID);;
                            hashmap.put("bsoID", partID);
                            //ת��ҳ��鿴�㲿������Ϣ�����ԡ�
        		            //modify by shf 2003/09/13
        		            RichToThinUtil.toWebPage("part_version_iterationsViewMain.screen",
        		                    hashmap);
        		            master = null;
        		            masterBsoID = null;
                            partID = "";
                            hashmap = null;
		                }
					} catch (QMRemoteException e) {
						// TODO �Զ����� catch ��
						e.printStackTrace();
					}
                    //////////////////////////modify end////////////////////////////////
		            return;
                    
                }
                //��õ�ǰ��ɸѡ������
                ConfigSpecItem configSpecItem = getPartItem()
                        .getConfigSpecItem();
                //���ɸѡ������Ϊ�գ���õ�ǰ�ͻ��˵�ɸѡ����
                if(configSpecItem == null)
                {
                    PartConfigSpecIfc partConfigSpecIfc = null;
                    // �����������ɸѡ����
                    try
                    {
                        partConfigSpecIfc = (PartConfigSpecIfc) PartHelper
                                .getConfigSpec();
                    }
                    catch (QMRemoteException qre)
                    {
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "errorTitle", null);
                        JOptionPane.showMessageDialog(getParentFrame(), qre
                                .getClientMessage(), title,
                                JOptionPane.ERROR_MESSAGE);
                    }
                    //���ɸѡ������Ϊ�գ���ɸѡ��������ɸѡ����������½�ɸѡ������
                    if(partConfigSpecIfc != null)
                    {
                        configSpecItem = new ConfigSpecItem(partConfigSpecIfc);
                    }
                    else
                    {
                        configSpecItem = new ConfigSpecItem();
                    }
                }
                //�����㲿������Ϣ��ȡ���㲿�������з������ù淶�İ汾
                Vector partInfoVector = PartHelper.getAllVersions(master,
                        configSpecItem.getConfigSpecInfo());
                PartDebug.debug("*** ���㲿�������з������ù淶�İ汾:" + partInfoVector,
                        PartDebug.PART_CLIENT);
                Object[] qmPartIfc = partInfoVector.toArray();
                PartDebug.debug("****************" + qmPartIfc, this,
                        PartDebug.PART_CLIENT);
                //���Ϊ�գ���ʾ��Ϣ����û�з��������İ汾��
                if(qmPartIfc == null)
                {
                    try
                    {
                        Object[] params = {((QMPartMasterInfo) master)
                                .getIdentifyObject().getIdentity()};
                        String message = getLocalizedMessage(
                                PartDesignViewRB.NO_QUA_VERSION, params);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "errorTitle", null);
                        JOptionPane.showMessageDialog(getParentFrame(),
                                message, title, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch (QMException ex)
                    {
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "exception", null);
                        JOptionPane.showMessageDialog(getParentFrame(), ex
                                .getClientMessage(), title,
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if(qmPartIfc.length == 0)
                {
                	
                    try
                    {
                        Object[] params = {((QMPartMasterInfo) master)
                                .getIdentifyObject().getIdentity()};
                        String message = getLocalizedMessage(
                                PartDesignViewRB.NO_QUA_VERSION, params);
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "errorTitle", null);
                        JOptionPane.showMessageDialog(getParentFrame(),
                                message, title, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    catch (QMException ex)
                    {
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "exception", null);
                        JOptionPane.showMessageDialog(getParentFrame(), ex
                                .getClientMessage(), title,
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                //��ȡ��ѡ���㲿�������°汾
                QMPartIfc part = null;
                PartDebug.debug("$$$$$$$$ ��ѡ���㲿�������°汾: " + qmPartIfc[0],
                        PartDebug.PART_CLIENT);
                if(qmPartIfc[0] instanceof Object[])
                {
                    Object[] partObj = (Object[]) qmPartIfc[0];
                    part = (QMPartIfc) partObj[0];
                    PartDebug.debug("+++++++ ��ѡ���㲿�������°汾:" + part,
                            PartDebug.PART_CLIENT);
                }
                //�����Ϊ�գ��鿴���㲿�������°汾�������ԣ�ת���鿴�㲿������ҳ�棩
                if(part != null)
                {
                    PartDebug.debug("-----------����鿴�㲿������ҳ��------------",
                            PartDebug.PART_CLIENT);
                    String partID = part.getBsoID();
                    HashMap map = new HashMap();
                    map.put("bsoID", partID);
                    map.put("flag", "0");
                    RichToThinUtil.toWebPage(
                            "Part-Other-PartLookOver-001-0A.do", map);
                    PartDebug.debug("UsesJPanel:RichToThinUtil Successed !",
                            PartDebug.PART_CLIENT);
                }
            }
        }
        catch (QMRemoteException qre)
        {
            qre.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane
                    .showMessageDialog(getParentFrame(),
                            qre.getClientMessage(), title,
                            JOptionPane.ERROR_MESSAGE);
        }
        finally
        {
            structList.setCursor(Cursor.getDefaultCursor());
            setCursor(Cursor.getDefaultCursor());
        }
        PartDebug.debug("viewSelectedItem() end ....return is void", this,
                PartDebug.PART_CLIENT);
    }

    private JTable table = structList.getTable();

    private DefaultTableModel dtm;

    /**��λ����*/
    private TableColumn unitColumn;

    /**ʹ����������*/
    private TableColumn quantityColumn;

    /**ʹ�������ĵ�Ԫ��༭��*/
    private QuantityEditor quantityEditor;

    /**��λ�ĵ�Ԫ��༭��*/
    private UnitEditor unitEditor;

    /**
     * �ڸ���ģʽ�£�����λ���������б�Ϊ�ɱ༭�ģ�
     * ���ҷֱ���õ�λѡ��������΢�������б༭��
     */
    public void setListEditor()
    {
        table = structList.getTable();        
        dtm = (DefaultTableModel) table.getModel();
        /*liyz modify ��ʹ�ýṹ�б��пɸ���״̬�£������޵�λ�����������жϣ�
        ��ʹ��λ�����������кͱ༭����Ӧ*/
        String[] heads=structList.getHeadings();
        int col1=0;//��λ��
        int col2=0;//������
        //CCBegin SS1
        int col3=0;
        int col4=0;
        //CCEnd SS1
        //CCBegin SS2
        int col5=0;
        //CCEnd SS2
        if(updateMode)
        {
        	boolean unitflag=false;
        	boolean quanflag=false;
        	//CCBegin SS1
        	boolean snflag=false;
        	boolean bomflag=false;
        	//CCend SS1
        	//CCBegin SS2
        	boolean proflag=false;
        	//CCend SS2
        	for(int k=0;k<heads.length;k++)
        	{
        		String list = heads[k];
        		if(list.equals(getLabelsRB().getString("unitHeading")))
        		{
        	      unitflag=true;
        	      col1=k;        	      
        		}
        		if(list.equals(getLabelsRB().getString("quantityHeading")))
        		{
        			quanflag=true;
        			col2=k;        	      
        		}
        		//CCBegin SS1
        		if(list.equals(PartUsageList.toPartUsageList("subUnitNumber").getDisplay()))
        		{
        			snflag=true;
        			col3=k;        	      
        		}
        		if(list.equals(PartUsageList.toPartUsageList("bomItem").getDisplay()))
        		{
        			bomflag=true;
        			col4=k;        	      
        		}
        		//CCEnd SS1
         		//CCBegin SS2
        		if(list.equals(PartUsageList.toPartUsageList("proVersion").getDisplay()))
        		{
        			proflag=true;
        			col5=k;        	      
        		}
        		//CCEnd SS2
       	}
        	if(unitflag)
        	{
            DefaultTableCellRenderer unitColumnRenderer = new DefaultTableCellRenderer()
            {
                /**���л�ID*/
                static final long serialVersionUID = 1L;

                public void setValue(Object value)
                {
                    setText((value == null) ? "" : value.toString());
                }
            };
            
            unitColumnRenderer.setHorizontalAlignment(JLabel.LEFT);            
            unitColumn = table.getColumn(getLabelsRB().getString("unitHeading"));
            //����"��λ"һ�в��õ�λѡ�����Ϊ�༭����
            unitEditor = new UnitEditor(new JComboBox(), structList);
            unitColumn.setCellEditor(unitEditor);
            unitColumn.setCellRenderer(unitColumnRenderer);
        	}
//            if(col1!=0)
//            {
//            	int[] cols={col1};
//            	structList.setColsEnabled(cols, true);            
//            }            
            if(quanflag)
            {
            quantityColumn = table.getColumn(getLabelsRB().getString("quantityHeading"));
            //����"����"һ�в�������΢������Ϊ�༭����
            quantityEditor = new QuantityEditor(new JComboBox(), structList);
            quantityColumn.setCellEditor(quantityEditor);
            quantityColumn.setCellRenderer(new QuantityRenderer());
            }
            
            //CCBegin SS1
            //CCBegin SS2
            //	int[] cols={col1,col2};
            //int[] cols={col1,col2,col3,col4}; 	
            int[] cols={col1,col2,col3,col4,col5}; 	
            //CCEnd SS2
            //CCEnd SS1
            	structList.setColsEnabled(cols, true);            
                      
        	
        }
        //end
        else
        {
            structList.setCellEditable(false);
            int[] coloumns = {0, 1, 2, 3, 4, 5, 6, 7, 8};
            structList.setColsEnabled(coloumns, false);
        }
    }

    /**
     * ���ѡ��һ���㲿������ʹ��ȥ��ť�Ͳ鿴��ť��Ч������ʧЧ��
     * @param event ItemEvent δʹ�á�
     */
    public void usesMultiList_itemStateChanged(java.awt.event.ItemEvent event)
    {
        PartDebug.debug("usesMultiList_itemStateChanged��event�� begin ...",
                this, PartDebug.PART_CLIENT);
        int i = structList.getSelectedRow();
        if(i != -1)
        {
            viewUsageJB.setEnabled(true);
            //liyz add ѡ���㲿������ʾ���ð�ť����
            //partAttrSetJButton.setEnabled(true);//end
            if(updateMode)
            {
            	QMProductManager explor = ((QMProductManagerJFrame) this
                        .getParentFrame()).getMyExplorer();
            	boolean fromcapp=explor.getFromCapp();
            	if(!fromcapp)
                removeUsageJB.setEnabled(true);
            }
        }
        else
        {
            viewUsageJB.setEnabled(false);
            removeUsageJB.setEnabled(false);
            //liyz add
            //partAttrSetJButton.setEnabled(false);
        }
        PartDebug.debug(
                "usesMultiList_itemStateChanged��event�� end...return is void",
                this, PartDebug.PART_CLIENT);
    }

    /**
     * �ڴ���������㲿��������������Ϊ�ʼ�Ľ��档
     */
    public void clear()
    {
        if(ibaAttributesJPanel != null)
        {
            ibaAttributesJPanel.updateEditor(null, false);
            ibaAttributesJPanel.setUpdateMode(false);
        }
        try
        { 
            setPartItem(new PartItem());
            describedByJPanel.setPartItem(new PartItem());//��������ο��ĵ�Tabҳ�������ĵ�Tabҳ��//CR9
            referencesJPanel.setPartItem(new PartItem());
            
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "errorTitle", null);
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        attributeJPanel.clear();
    }

  
    /**
     * ����Ƿ�ı䣬����ı���ʾ�Ƿ񱣴棺
     * ѡ�񡮱��桯�򱣴棬ѡ��ȡ�����򲻱��档
     */
    public boolean saveChange()
    {
        synchronized (cursorLock)
        {
            boolean flag = false;
            PartItem part = getPartItem();
            if(part != null && part.getPart().getPartNumber() != null && partData != null && dtm != null)
            {
                //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
                DisplayIdentity displayidentity = IdentityFactory
                        .getDisplayIdentity(part.getPart());
                //��ö������� + ��ʶ
                String s = displayidentity.getLocalizedMessage(null);
                Object[] params = {s};
                String confirmExitMsg = QMMessage.getLocalizedMessage(RESOURCE,
                        "confirmsave", params);
                String warningDialogTitle = QMMessage.getLocalizedMessage(
                        RESOURCE, "errorTitle", null);
                /**��ʾ�Ƿ񱣴�*/
                int i = DialogFactory.showYesNoDialog(this, confirmExitMsg,
                        warningDialogTitle);
                //����(2)20080306 zhangq begin:����������ǰ�޸ĵ��㲿��ʱ��
                //�����ĵ����ĸ��ı�־��Ȼ��true��TD-1516����
                if(i == DialogFactory.YES_OPTION)
                {
                    flag = true;
                    saveInThread(true);
                    //describedByJPanel.setChanged(false);
                }
                describedByJPanel.setChanged(false);
                //����(2)20080306 zhangq end
            }
            return flag;
        }
    }

    /*
     * 070511������
     *  ���ж��Ƿ�ı䷽����chang()����ȡ�������ڱ���ʱ����У�顣
     */
    public boolean isChange()
    {
        /**�㲿���Ƿ�ı�ı�־*/
        boolean isChange = false;
        PartItem partItem = getPartItem();
        if(partItem != null && partData != null && dtm != null)
        {
                isChange = attributeJPanel.ischange(partItem);
                basechange = attributeJPanel.ischange(partItem);
           
            /**Ȼ����ʹ�ýṹ�Ƿ�ı�*/
            boolean ss = partData.length != dtm.getRowCount();
            if(ss)
            {
                isChange = true;
                usagechange = true;
            }
            else
            {
                for (int row = 0; row < dtm.getRowCount(); row++)
                {
                    boolean in = false;
                    for (int i = 0; i < dtm.getRowCount(); i++)
                    {
//                        boolean hh = partData[row][2].toString().equals(
//                                dtm.getValueAt(i, 9).toString());
//                        if(hh)
//                        {
//                            if(!partData[row][0].toString().trim().equals(
//                                    dtm.getValueAt(i, 4).toString().trim()))
//                            {
//                                isChange = true;
//                                usagechange = true;
//                                break;
//                            }
//                            else if(!partData[row][1].toString().trim().equals(
//                                    dtm.getValueAt(i, 5).toString().trim()))
//                            {
//                                isChange = true;
//                                usagechange = true;
//                                break;
//                            }
                    	
                    	//liyz add
                    	PartUsageList quantity = PartUsageList.toPartUsageList("quantityString");
                    	String[] quanHead = structList.getHeadings();
                    	int quanCol = 0;
                    	for(int m=0;m<quanHead.length;m++)
                    	{
                    		String s = quanHead[m];
                    		if(s.equals(quantity.getDisplay()))
                    		{
                    			quanCol = m;
                    		}
                    	}
                    	//CCBegin SS1
                        PartUsageList subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber");
                        String[] subHead=structList.getHeadings();
                        int sunCol = 0;
                        for(int m=0;m<subHead.length;m++)
        	            {
        	            	String s =subHead[m];
        	               if(s.equals(subUnitNumber.getDisplay()))
        	            	{
        	            		sunCol=m;
        	            	}  
        	            	
        	            } 
                        PartUsageList bomItem = PartUsageList.toPartUsageList("bomItem");
                        String[] bomHead=structList.getHeadings();
        	            int bomCol = 0;
        	            for(int m=0;m<bomHead.length;m++)
        	            {
        	            	String s =bomHead[m];
        	               if(s.equals(bomItem.getDisplay()))
        	            	{
        	            		bomCol=m;
        	            	}   
        	            	
        	            } 
                    	//CCend SS1
                    	//CCBegin SS2
                        PartUsageList proVersion = PartUsageList.toPartUsageList("proVersion");
                        String[] proHead=structList.getHeadings();
                        int proCol = 0;
                        for(int m=0;m<proHead.length;m++)
        	            {
        	            	String s =proHead[m];
        	               if(s.equals(proVersion.getDisplay()))
        	            	{
        	            		proCol=m;
        	            	}  
        	            	
        	            } 
                    	//CCend SS2
                    	PartUsageList unit = PartUsageList.toPartUsageList("unitName");
                    	String[] unitHead = structList.getHeadings();
                    	int unitCol = 0;
                    	for(int n=0;n<unitHead.length;n++)
                    	{
                    		String s =unitHead[n];
                    		if(s.equals(unit.getDisplay()))
                    		{
                    			unitCol = n;
                    		}
                    	}                    	
                        String[] head=structList.getHeadings();
                        int col = 0;
                        for(int k=0;k<head.length;k++)
                        {            	                	
                          col=k;                	
                        }
                        boolean hh = partData[row][2].toString().equals(
                        		dtm.getValueAt(i, col+1).toString());
                        if(hh)
                        {
                    	if(!partData[row][0].toString().trim().equals(
                                dtm.getValueAt(i, quanCol).toString().trim()))
                       	{
                       		isChange = true;
                       		usagechange = true;
                       		break;
                       	}
                    	else if(!partData[row][1].toString().trim().equals(
                                dtm.getValueAt(i, unitCol).toString().trim()))
                        {
                            isChange = true;
                            usagechange = true;
                            break;
                        }else if(!partData[row][3].toString().trim().equals(
                                dtm.getValueAt(i, sunCol).toString().trim()))
                        {
                            isChange = true;
                            usagechange = true;
                            break;
                        }else if(!partData[row][4].toString().trim().equals(
                                dtm.getValueAt(i, bomCol).toString().trim()))
                        {
                            isChange = true;
                            usagechange = true;
                            break;
                        }
                     	//CCBegin SS2
                        else if(!partData[row][5].toString().trim().equals(
                                dtm.getValueAt(i, proCol).toString().trim()))
                        {
                            isChange = true;
                            usagechange = true;
                            break;
                        }
                    	//CCEnd SS2
                           //end
                            
                            in = true;
                            break;
                        }
                    }
                    if(in == false)
                    {
                        isChange = true;
                        usagechange = true;
                        break;
                    }
                }
            }
            if(isChange)
            {
                /**���ż�����������Ƿ�ı�*/
                if(ibaAttributesJPanel.isChanged())
                    ibachange = true;
                /**��������ĵ��Ƿ�ı�*/
                if(describedByJPanel.isChanged())
                    descrichange = true;
                /**���ο��ĵ��Ƿ�ı�*/
                if(docBsoID != null && referencesJPanel.isChanged(docBsoID))
                    refchange = true;
            }
            else
            {
                
                isChange = ibaAttributesJPanel.isChanged() || isChange;
                if(ibaAttributesJPanel.isChanged())
                    ibachange = true;
                isChange = describedByJPanel.isChanged() || isChange;
                if(describedByJPanel.isChanged())
                    descrichange = true;
                if(docBsoID != null)
                {
                    isChange = referencesJPanel.isChanged(docBsoID) || isChange;
                    if(referencesJPanel.isChanged(docBsoID))
                        refchange = true;
                }
            }
        }
        return isChange;
    }
//  update whj 07/12/6
    public void setButtonUpdate()
    {
    	 QMProductManager explor = ((QMProductManagerJFrame) this
                 .getParentFrame()).getMyExplorer();
         /**
          * set for capp ���ε���������2007.06.29 ������
          * 
          */
        
         if(explor.getFromCapp())
         {
         	 addUsageJB.setEnabled(false);
         }
         else
         addUsageJB.setEnabled(true);
         removeUsageJB.setEnabled(false);
    }
    public void setButtonFalse()
    {
    	 addUsageJB.setEnabled(false);
    }
//  update whj 07/12/6
    public void setReference(boolean flag)
    {
    	isReference=flag;
    }
    public boolean getReference()
    {
    	return isReference;
    }
    
    //liyz add ��ʾ���ü���
    class AttrSetListener implements ActionListener
    {
    	private String tab;
    	AttrSetListener(String s)
    	{
    		tab=s;
    	}
    	public void actionPerformed(ActionEvent e)
        {
        	partAttrSetJButton_actionPerformed(e,tab);
        }
    }
    
//  muyp 20080618 begin
    /**
     * ��ȡ��ǰѡ��TABҳ������(Ĭ�����)
     * 0��ʹ�ýṹ��1�ǻ������ԣ�2���������ԣ�3�������ĵ���
     * 4�ǲο��ĵ�,5�ǹ���·��,6�ǹ��ջ���,7�ǹ��չ��
     */
    public int getSelectTabbed()
    {
    	return contentsJTabbedPane.getSelectedIndex();
    }
    
    public DescribedByJPanel getDescJPanel()
    {
    	return describedByJPanel;
    }
    
    public ReferenceJPanel getRefJPanel()
    {
    	return referencesJPanel;
    }
    
    public IBAContainerEditor getIBAAttributesJPanel()
    {
    	return ibaAttributesJPanel;
    }
    //end
    
    //liyz add ����������
    
    public TechnicsRegulateionPanel getRegulationJPanel()
    {
    	return regulateionJPanel;
    }
    
    public TechnicsSummaryPanel getSummaryJPanel()
    {
    	return summaryJPanel;
    }
    
    public TechnicsRoutePanel getRouteJPanel()
    {
    	return routeJPanel;
    }
    //liyz add ��ȡʹ�ýṹ�������ԡ��������ԡ������ĵ��Ͳο��ĵ���tabҳ����
    public int getUsageIndex()
    {
    	return usageIndex;
    }
    public int getBaseIndex()
    {
    	return baseIndex;
    }
    public int getIbaIndex()
    {
    	return ibaIndex;
    }
    public int getDescIndex()
    {
    	return descIndex;
    }
    public int getRefIndex()
    {
    	return refIndex;
    }
    //Begin CR8
    /**
     * ��Ʒ��Ϣ������Tabҳ������
     * @author ����
     */
    class TabListener implements ChangeListener
    {
        TabListener()
        {
            super();
        }

        public void stateChanged(ChangeEvent e)
        {
            PartItem partitem = getPartItem();
            if(partitem != null)
            {
                if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "attributeJPanel")
                {
                    if(attributeJPanel.getIsShown() == false)
                    {
                        attributeJPanel.setPartItem(partitem, false);
                        attributeJPanel.setIsShown(true);
                    }
                }else if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "addEditAttributesJPanel")
                {
                    if(ibaAttributesJPanel.getIsShown() == false)
                    {
                        IBAHolderIfc ibaHolder = (IBAHolderIfc)partitem.getPart();
                        if(ibaHolder.getAttributeContainer() == null)
                        {
                            try
                            {
                                Class[] classes = {IBAHolderIfc.class, Object.class, Locale.class, MeasurementSystemDefaultView.class};
                                Object[] objects = {ibaHolder, null, RemoteProperty.getVersionLocale(), null};
                                ibaHolder = (IBAHolderIfc)IBAUtility.invokeServiceMethod("IBAValueService", "refreshAttributeContainer", classes, objects);
                            }catch(QMRemoteException remoteexception)
                            {
                                remoteexception.printStackTrace();
                                ibaHolder.setAttributeContainer(new DefaultAttributeContainer());
                            }
                        }
                        ibaAttributesJPanel.updateEditor(ibaHolder, mode == VIEW_MODE);

                        addClassificationButtons();//CR9
                        refreshClassificationControl();

                        ibaAttributesJPanel.setUpdateMode(updateMode);
                        ibaAttributesJPanel.setIsShown(true);
                    }
                }else if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "addDesJPanel")
                {
                    if(describedByJPanel.getIsShown() == false)
                    {
                        describedByJPanel.setReference(getReference());
                        describedByJPanel.setPartItem(partitem);
                        describedByJPanel.setIsShown(true);
                    }
                }else if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "addRefJPanel")
                {
                    if(referencesJPanel.getIsShown() == false)
                    {
                        referencesJPanel.setPartItem(partitem);
                        if(mainPart)
                        {
                            docBsoID = referencesJPanel.getDocumentBsoID();
                        }
                        referencesJPanel.setIsShown(true);
                    }
                }else if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "addRouteJPanel")
                {
                    if(routeJPanel.getIsShown() == false)
                    {
                        if(routeJPanel != null && partitem.getPart().getMasterBsoID() != null)
                            routeJPanel.setPartItem(partitem);
                        routeJPanel.setIsShown(true);
                    }
                }else if(((JPanel)contentsJTabbedPane.getSelectedComponent()).getName() == "addSummaryJPanel")
                {
                    if(summaryJPanel.getIsShown() == false)
                    {
                        if(summaryJPanel != null)
                            summaryJPanel.setPartItem(partitem);
                        summaryJPanel.setIsShown(true);
                    }
                }else
                {
                    if(regulateionJPanel.getIsShown() == false)
                    {
                        if(regulateionJPanel != null)
                            regulateionJPanel.setPartItem(partitem);
                        regulateionJPanel.setIsShown(true);
                    }
                }
            }
        }
    }
    /**
     * ���ò�Ʒ��Ϣ������������Tabҳ����ʾ��־
     * @param flag
     */
    public void setIsShown(boolean flag)
    {
        if(attributeJPanel != null)
            attributeJPanel.setIsShown(flag);
        if(ibaAttributesJPanel != null)
            ibaAttributesJPanel.setIsShown(flag);
        if(describedByJPanel != null)
            describedByJPanel.setIsShown(flag);
        if(referencesJPanel != null)
            referencesJPanel.setIsShown(flag);
        if(routeJPanel != null)
            routeJPanel.setIsShown(flag);
        if(summaryJPanel != null)
            summaryJPanel.setIsShown(flag);
        if(regulateionJPanel != null)
            regulateionJPanel.setIsShown(flag);
    }
    /**
     * ����IsCreate��־,���ڸ���ʱ����Ҫ��ʾ��Tabҳ��ϢҪ����ʹ�ýṹ���ο��ĵ��������ĵ���
     * @param flag
     */
    public void setIsCreate(boolean flag)
    {
        this.isCreate = flag;
    }
    //End CR8
}
