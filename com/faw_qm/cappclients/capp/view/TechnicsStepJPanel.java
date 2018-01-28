/** ���ɳ���TechnicsStepJPanel.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/27 �촺Ӣ  ԭ���ڹ���ġ��Ƿ񱣴桱��ʾ���������ȡ����ť��ĿǰΪ���ǡ��͡���
 *                       �������ڴ�һ��������и���ʱ������֮������л���ر�ʱ���ڵ����ġ��Ƿ񱣴桱��ʾ�������һ����ȡ������ť��
 *                       ��ע�������¼���ΪCRSS-005    
 *                               
 * CR2  ������ 2009/01/15 ԭ��:�û��鿴����ʱ�������˹������գ��޷�����ĶԱ��������յ�����н�һ���鿴                             
 *                       ����:
 *                       ��ע:�����¼���"CRSS-006"     
 *                       
 * CR3 2009/04/05 ��־��  ԭ���Ż����򡢹����鿴�����ٲ�ѯ���ݿ������
 *                       ����������ˢ��ͼֵ����
 *                       ��ע�����ܲ����������ƣ�"�������ڵ��л������ͼ���򡢹����鿴��"�� 
 *                         
 * CR4 2009/04/29 ����     ԭ�򣺹��򱣴�ʱ�жϼ�ͼ�Ƿ����
 *                        ������
 *                        ��ע�����ܲ����������ƣ�"�������"��
 * CR5 2009/04/27 ��ѧ��    ԭ��:�������й��������ϡ�tabҳ���Ƹ�Ϊ�����ϡ���ʵʩ��������⣩
 *                          ����:�������й��������ϡ�tabҳ���Ƹ�Ϊ�����ϡ�  
 *                          ��ע:�����¼���"CRSS-010"
 * CR6 2009/04/05 ��־��    ԭ���Ż���������߼������ٲ�ѯ���ݿ������
 *                         ������ȥ���ж��Ƿ񱻱�Ĺ������Ż�������������߼���
 *                         ��ע�����ܲ����������ƣ������ճ����������  
 *CR7 2010/04/02 �촺Ӣ   ԭ�򣺲μ�TD����2245                            
 * CR8 2010/06/04  �촺Ӣ ԭ��:�μ�TD����2263                
 * CCBegin by liunan 2011-08-25 �򲹶�P035   
 * CR11 20110706  lvh      �μ�TD2419
 * CR12 2011/07/14 ���� �μ�TD2423      
 * CCEnd by liunan 2011-08-25      
 * SS1 ���������ӹ������ۼӵ����򡱲��Ҳ��ڹ�������ʾ leixiao 2013-10-14      
 * SS2 ���򹤲�������ͼ,���˳��� liuyang 2014-04-03      
 * SS3 �ɶ�Ԥ�����򣬹��չ��� guoxiaoliang 2016-7-25         
 * SS4 �ɶ�������ӻ���Ӽ�����  guoxiaoliang 2016-7-28     
 * SS5 �ɶ����������ӹ���������  guoxiaoliang 2016-7-28    
 * SS6 �ɶ�����������Դ��ӹ�����Դ���� guoxiaoliang 2016-8-2         
 * SS7 �ɶ���ӷ�װ�����������  guoxiaoliang  2016-10-8     
 * SS8 �ɶ������ҵָ����ӹ�����Ĭ��ѡ���鹤�򣬹�������Ĭ��һ�㹤��  guoxiaoliang 2016-11-11
 * SS9 �ɶ���λ�ſ���������ĸ  guoxiaoliang 2016-11-12
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;

import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMFawTechnicsMasterInfo;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMProcedureQMDocumentLinkInfo;
import com.faw_qm.capp.model.QMProcedureQMEquipmentLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMMaterialLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMPartLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMToolLinkIfc;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.beans.processtreepanel.StepTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreePanel;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.TermTextField;
import com.faw_qm.cappclients.resource.view.ResourcePanel;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.print.lightweightfile.util.LightweightFileTool;
import com.faw_qm.resource.support.client.model.CEquipment;
import com.faw_qm.resource.support.client.model.CTool;
import com.faw_qm.resource.support.model.DrawingInfo;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMTermInfo;
import com.faw_qm.resource.support.model.QMToolInfo;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.wip.model.WorkableIfc;


/**
 * <p>Title:����ά�����</p>
 * <p>Ϊ���չ��������ṩ����</p>
 * <p>�ṩ������ʾģʽ(���������¡��鿴)����ɹ���Ĵ��������¡��鿴������</p>
 * <p>�йع�ʱ��Ϣά��:���ѡ���˹������գ���ʱ��Ϣ����ά��������������й����ڵ㣬</p>
 * <p>�ҹ����Ĺ�ʱ��Ϣ��Ϊ��0����գ������еĹ�ʱ��ϢӦΪ������ʱ�ĺͣ������Թ�����</p>
 * <p>�ֹ�¼���ֵΪ׼��</p>
 * <p>������������Ƕ༶�ģ���һ���ǹ������࣬�ڶ����ǹ�������</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 *��1��20060727Ѧ���޸ģ��޸ķ���saveWhenCreate(),ȥ��ˢ�¹��սڵ�Ĳ���
 *��2��20060728Ѧ���޸ģ�ԭ����ά�������Ĺ�ʱ����Դ����ʱ���Զ�ά������Ĺ�ʱ����Դ������������ʱ�������й�����ʱ�ĺͣ�
 *      ��ԴҲһ�����������ɿ����õ�,���������ļ��������Ƿ�Ҫά�����ֹ�ϵ.�޸ķ��� setRelatedEff()
 * ���⣨3�� 20080704 �촺Ӣ�޸� �޸�ԭ�򣺹�������������Ԥ��������
 * ���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
 * Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
 * ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
 * ���⣨5��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
 * ���⣨6��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤��
 * ���⣨7��20090108  �촺Ӣ�޸�    ��ʼ���豸��������ʱ��Ҳ��ʼ����װ�������   ��Ȼ����ֿ�ָ���쳣
 * ���⣨8��20090112  ��־���޸� �޸�ԭ���Ż�����鿴�ٶȣ�Ӧ��Ϊ���ڲ鿴ģʽ�²�ˢ��ͼֵ����blob����
 *                                  ���������ͼ����ϵĲ鿴��ťʱ�����ײ�ˢ�¼�ͼ������
 *  
 */

public class TechnicsStepJPanel extends ParentJPanel
{
    /**����ά�����*/
//  private JPanel stepJPanel = new JPanel();
    /**��ť���*/
    private JPanel buttonJPanel = new JPanel();


    /**���*/
    private JLabel numJLabel = new JLabel();
    //CCBegin SS7
    //��װ���������Ϣ
    private JLabel sonTeamDateJLabel = new JLabel();
    private JTextField sonTeamDateTextField= new JTextField();
    private JLabel sonTeamDateDisJLabel = new JLabel();
    //CCEnd SS7

    //add by wangh on 20070131(���빤���ű�ǩ���ı������һ��JTabbedPane����3����JPanel��);
    private JLabel descStepNumberJLabel = new JLabel();
    private JLabel descNumDisplayJLabel = new JLabel();
    private CappTextField descNumJTextField;
    private JTabbedPane jTabbedPanel = new JTabbedPane();
    private JPanel extendJPanel = new JPanel();
    private JPanel extendJPanel2 = new JPanel();
    private JPanel extendJPanel3 = new JPanel();
    private JPanel extendJPanel4 = new JPanel();


    private CappTextField numJTextField;
    private JLabel numDisplayJLabel = new JLabel();
    
   


    /**��������*/
    private JLabel relationTechJLabel = new JLabel();
    private JTextField relationTechJTextField = new JTextField();
    private JButton searchTechJButton = new JButton();
    private JLabel relationTechDisJLabel = new JLabel();
    private JPanel rbJPanel = new JPanel();
    private JButton deleteTechJButton = new JButton();


    /**����*/
    private JLabel nameJLabel = new JLabel();
    private TermTextField nameJTextField;
    private JLabel nameDisplayJLabel = new JLabel();


    /**��������*/
    private JLabel stepTypeJLabel = new JLabel();
    private JLabel stepTypeDisJLabel = new JLabel();


    /**�������*/
    private JLabel stepClassifiJLabel = new JLabel();
    private JLabel stepClassiDisJLabel = new JLabel();


    /**�ӹ�����*/
    private JLabel processTypeJLabel = new JLabel();
    private JLabel processTypeDisJLabel = new JLabel();


    /**����*/
    private JLabel workshopJLabel = new JLabel();
    private JLabel workshopDisJLabel = new JLabel();


    /**��ͼά��*/
    private ProcedureUsageDrawingPanel drawingLinkPanel;


    /**��������*/
    private JLabel contentJLabel = new JLabel();
    private SpeCharaterTextPanel contentPanel;


    /**�������*/
    private JTabbedPane relationsJPanel = new JTabbedPane();
    
    //CCBegin SS5
    
    private JLabel relationJLabel = new JLabel();
    
    private JButton searchTechJButton1 = new JButton();
    private JButton deleteTechJButton1 = new JButton();
    private JButton viewProcedureJButton = new JButton();
    private JTextField relationJTextField = new JTextField();
    private JLabel relationDisJLabel = new JLabel();
    public QMFawTechnicsInfo technicsinfo;
    
    private JPanel relationJPanel = new JPanel();
    private GridBagLayout gridBagLayout8 = new GridBagLayout();
    //CCEnd SS5


    /**��ť��*/
    private JButton paraJButton = new JButton();
    private JButton saveJButton = new JButton();
    private JButton cancelJButton = new JButton();
    private JButton quitJButton = new JButton();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();


//    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**��ʱͳ�����*/
    private ProcessHoursJPanel processHoursJPanel;


    /**������ʾģʽ������ģʽ�����*/
    public final static int UPDATE_MODE = 0;


    /**������ʾģʽ������ģʽ�����*/
    public final static int CREATE_MODE = 1;


    /**������ʾģʽ���鿴ģʽ�����*/
    public final static int VIEW_MODE = 2;


    /**����ģʽ--�鿴*/
    private int mode = VIEW_MODE;
    private GridBagLayout gridBagLayout3 = new GridBagLayout();


    /**����*/
    private QMProcedureInfo procedureInfo;
    private GridBagLayout gridBagLayout4 = new GridBagLayout();


    /**������*/
    private JFrame parentJFrame;


    /**��ѡ�еĽڵ�*/
    private CappTreeNode selectedNode;


    /**�豸������ά�����*/
    private ProcedureUsageEquipmentJPanel equiplinkJPanel;


    /**��װ������ά�����*/
    private ProcedureUsageToolJPanel toollinkJPanel ;


    /**���Ϲ�����ά�����*/
    private ProcedureUsageMaterialJPanel materiallinkJPanel;


    /**�ĵ�������ά�����*/
    private ProcedureUsageDocJPanel doclinkJPanel = new ProcedureUsageDocJPanel();


    /**��ⰴť*/
    private JButton storageJButton = new JButton();

    private GridBagLayout gridBagLayout5 = new GridBagLayout();

    private JScrollPane scrollpane = new JScrollPane();


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private JPanel jPanel1 = new JPanel();


    /**��չ���ݽ������*/
    private TParamJDialog d = null;
    private EquipmentToolMaintainJDialog f1 = null;
    
    //CCBegin SS5
    
    public QMProcedureIfc relatedProcedure = null;
    //CCEnd SS5


    /**
     * ���տ�
     */
    private QMTechnicsIfc parentTechnics;


    /**c�д�Ź����µ�ֱ�ӹ���*/
    private Collection c = null;


    /**��¼�Ƿ��һ�ν���˽���*/
    private boolean firstInFlag = true;


    /**
     *��������� ��������\���������µĴ�����
     * ��:������������  ֵ:�������������
     */
    private Hashtable stepTypetable = null;
    private CodingIfc stepType;
    private JSplitPane splitJPanel = new JSplitPane();
    private JPanel upJPanel = new JPanel();
    private JPanel downJPanel = new JPanel();

    private GridBagLayout gridBagLayout6 = new GridBagLayout();
    private GridBagLayout gridBagLayout7 = new GridBagLayout();

    //add by wangh on 20070207(�����������,����FMEA�Ϳ��Ƽƻ�Panel,����ÿ��Panel����һ��Hashtable)
    private Hashtable processFlowTable = new Hashtable();
    private Hashtable femaTable = new Hashtable();
    private Hashtable processControlTable = new Hashtable();
    private CappExAttrPanel processFlowJPanel;
    private CappExAttrPanel femaJPanel;
    private CappExAttrPanel processControlJPanel;

    //add by wangh on 20070326(�Ƿ���ʾTS16949�Ĺ�����߹�����Ϣ��)
    private static boolean ts16949 = (RemoteProperty.getProperty(
            "TS16949", "true")).equals("true");
    

    //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
    private JComboBox processTypeComboBox = new JComboBox();
    private JComboBox stepTypeComboBox = new JComboBox();
    
    
    //CCBegin SS3
    //Ԥ��һ������ĸ�����Ƭ
    private JButton viewproJButton = new JButton();

    //Ԥ�����������ڹ��յĸ�����Ƭ
    private JButton viewTechnicsJButton = new JButton();
    
    //������ʶ�Ƿ�ִ��Ԥ����ť
    boolean isview = false;
    //CCEnd SS3
    
    public TechnicsStepJPanel(){
    	super();}
    
    /**
     * ���캯��
     * @param parent ���ñ���ĸ�����
     */
    public TechnicsStepJPanel(JFrame parent)
    {
        try
        {
            parentJFrame = parent;
            nameJTextField = new TermTextField(parentJFrame,
                                               QMMessage.getLocalizedMessage(
                    //RESOURCE, "procedureName", null), 40, false);liunan 2011-11-01 �ĳ�30�����֣�60���ַ���
                    RESOURCE, "procedureName", null), 60, false);
            //CCBegin by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415        
            contentPanel = new SpeCharaterTextPanel(parent,true);//CR7
            //CCEnd by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
            initSpeCharaterTextPanel();
            contentPanel.speCharaterTextBean.setFont(new java.awt.Font("Dialog",
                    0,
                    18));
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    //CCBegin SS8
    
    String createStepType="";
    /**
     * ���캯��
     * @param parent ���ñ���ĸ�����
     */
    public TechnicsStepJPanel(JFrame parent,String stepType)
    {
        try
        {
            parentJFrame = parent;
            nameJTextField = new TermTextField(parentJFrame,
                                               QMMessage.getLocalizedMessage(
                    //RESOURCE, "procedureName", null), 40, false);liunan 2011-11-01 �ĳ�30�����֣�60���ַ���
                    RESOURCE, "procedureName", null), 60, false);
            //CCBegin by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415        
            contentPanel = new SpeCharaterTextPanel(parent,true);//CR7
            //CCEnd by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
            initSpeCharaterTextPanel();
            contentPanel.speCharaterTextBean.setFont(new java.awt.Font("Dialog",
                    0,
                    18));
            createStepType=stepType;
            
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    //CCEnd SS8


    /**
     * ���캯��
     * @param parent ���ñ���ĸ�����
     * @param parentnode ���ڵ�
     */
    public TechnicsStepJPanel(JFrame parent, BaseValueIfc technics)
    {
        try
        {
            parentJFrame = parent;
            nameJTextField = new TermTextField(parentJFrame,
                                               QMMessage.getLocalizedMessage(
                    //RESOURCE, "procedureName", null), 40, false);liunan 2011-11-01 �ĳ�30�����֣�60���ַ���
                    RESOURCE, "procedureName", null), 60, false);
            //CCBegin by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
            contentPanel = new SpeCharaterTextPanel(parent,true);
            //CCEnd by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
            initSpeCharaterTextPanel();
            if (technics instanceof QMTechnicsIfc)
            {
                parentTechnics = (QMTechnicsIfc) technics;
            }
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    
   
    /**
     * �����ʼ��
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        String title2 = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.PROCESSTYPE, null);
        String title3 = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.STEPCLASSIFI, null);
        //���⣨3�� 20080704 �촺Ӣ�޸� �޸�ԭ�򣺹�������������Ԥ��������
        //equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepTypeContent);
        //toollinkJPanel = new ProcedureUsageToolJPanel(stepTypeContent);
        //materiallinkJPanel = new  ProcedureUsageMaterialJPanel(stepTypeContent);
        //CCBegin SS6
        drawingLinkPanel = new ProcedureUsageDrawingPanel(parentJFrame,true); 
        //CCEnd SS6
        f1 = new EquipmentToolMaintainJDialog(parentJFrame);
        setLayout(gridBagLayout4);
        setSize(550, 450);
        upJPanel.setLayout(gridBagLayout3);
        buttonJPanel.setLayout(gridBagLayout2);
        numJLabel.setMaximumSize(new Dimension(41, 22));
        numJLabel.setMinimumSize(new Dimension(41, 22));
        numJLabel.setPreferredSize(new Dimension(41, 22));
        numJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        numJLabel.setText("*�����");

        //add by wangh on 20070131
        //Ѧ�� �޸�
//        descStepNumberJLabel.setMaximumSize(new Dimension(48, 22));
//        descStepNumberJLabel.setMinimumSize(new Dimension(48, 22));
//        descStepNumberJLabel.setPreferredSize(new Dimension(48, 22));
        descStepNumberJLabel.setMaximumSize(new Dimension(53, 22));
        descStepNumberJLabel.setMinimumSize(new Dimension(53, 22));
        descStepNumberJLabel.setPreferredSize(new Dimension(53, 22));
        //Ѧ�� �޸Ľ���
        descStepNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descStepNumberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
//      Ѧ���޸� 20080219 �޸�ԭ���ڹ��չ�����ڹ����´�������,������Ϊ�������ֶ�ǰ��*
        //descStepNumberJLabel.setText("������");
        //CCBegin SS9
        if((getUserFromCompany().equals("cd")))
        	descStepNumberJLabel.setText("*��λ");
        else
            descStepNumberJLabel.setText("*������");
        //CCEnd SS9
        
        //Ѧ���޸Ľ���
        extendJPanel.setLayout(gridBagLayout3);
        extendJPanel2.setLayout(gridBagLayout3);
        extendJPanel3.setLayout(gridBagLayout3);
        extendJPanel4.setLayout(gridBagLayout3);
        extendJPanel.setBorder(BorderFactory.createEtchedBorder());
        extendJPanel2.setBorder(BorderFactory.createEtchedBorder());
        extendJPanel3.setBorder(BorderFactory.createEtchedBorder());
        extendJPanel4.setBorder(BorderFactory.createEtchedBorder());
        jTabbedPanel.setMaximumSize(new Dimension(405, 32767));
        jTabbedPanel.setMinimumSize(new Dimension(405, 78));
        jTabbedPanel.setPreferredSize(new Dimension(405, 536));
        
        //CCBegin SS5
        relationJPanel.setLayout(gridBagLayout8);
        relationDisJLabel.setMaximumSize(new Dimension(10, 22));
        relationDisJLabel.setMinimumSize(new Dimension(10, 22));
        relationDisJLabel.setPreferredSize(new Dimension(10, 22));
        
        relationJLabel.setMaximumSize(new Dimension(90, 22));
        relationJLabel.setMinimumSize(new Dimension(90, 22));
        relationJLabel.setPreferredSize(new Dimension(90, 22));
        relationJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        relationJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        relationJLabel.setText("��������");
        
        //CCBegin SS7
        
        
        sonTeamDateJLabel.setMaximumSize(new Dimension(90, 22));
        sonTeamDateJLabel.setMinimumSize(new Dimension(90, 22));
        sonTeamDateJLabel.setPreferredSize(new Dimension(90, 22));
        sonTeamDateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sonTeamDateJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        sonTeamDateJLabel.setText("��װ�������������Ϣ");
        
        //CCEnd SS7
        
        
        searchTechJButton1.setMaximumSize(new Dimension(89, 23));
        searchTechJButton1.setMinimumSize(new Dimension(89, 23));
        searchTechJButton1.setPreferredSize(new Dimension(89, 23));
        searchTechJButton1.setToolTipText("");
        searchTechJButton1.setMnemonic('D');
        searchTechJButton1.setText("���(D)");
        searchTechJButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
              searchTechJButton1_actionPerformed(e);
            }
          });
        
        deleteTechJButton1.setMaximumSize(new Dimension(75, 23));
        deleteTechJButton1.setMinimumSize(new Dimension(75, 23));
        deleteTechJButton1.setPreferredSize(new Dimension(75, 23));
        deleteTechJButton1.setMnemonic('W');
        deleteTechJButton1.setText("ɾ��(W)");
        deleteTechJButton1.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            deleteTechJButton1_actionPerformed(e);
          }
        });


        
        viewProcedureJButton.setMaximumSize(new Dimension(75, 23));
        viewProcedureJButton.setMinimumSize(new Dimension(75, 23));
        viewProcedureJButton.setPreferredSize(new Dimension(75, 23));
        viewProcedureJButton.setMnemonic('L');
        viewProcedureJButton.setText("�鿴(L)");
        viewProcedureJButton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            viewProcedureJButton_actionPerformed(e);
          }
        });
        
        //CCEnd SS5

        String procedureNumDisp = QMMessage.
                                  getLocalizedMessage(RESOURCE,
                "procedureNum", null);
        String procedureNumDisp1 = QMMessage.
                                   getLocalizedMessage(RESOURCE,
                "procedureNum1", null);

        numJTextField = new CappTextField(parentJFrame, procedureNumDisp, 5, false);
        
     
        
        //add by wangh on 20070201
        descNumJTextField = new CappTextField(parentJFrame, procedureNumDisp1,
                                              10, false);
        relationTechJLabel.setMaximumSize(new Dimension(48, 22));
        relationTechJLabel.setMinimumSize(new Dimension(48, 22));
        relationTechJLabel.setPreferredSize(new Dimension(48, 22));
        relationTechJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        relationTechJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        relationTechJLabel.setText("��������");
        searchTechJButton.setMaximumSize(new Dimension(89, 23));
        searchTechJButton.setMinimumSize(new Dimension(89, 23));
        searchTechJButton.setPreferredSize(new Dimension(89, 23));
        searchTechJButton.setToolTipText("");
        searchTechJButton.setMnemonic('B');
        searchTechJButton.setText("���(B)");
        searchTechJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                searchTechJButton_actionPerformed(e);
            }
        });
        deleteTechJButton.setMaximumSize(new Dimension(75, 23));
        deleteTechJButton.setMinimumSize(new Dimension(75, 23));
        deleteTechJButton.setPreferredSize(new Dimension(75, 23));
        deleteTechJButton.setMnemonic('R');
        deleteTechJButton.setText("ɾ��(R)");
        deleteTechJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteTechJButton_actionPerformed(e);
            }
        });

        nameJLabel.setMaximumSize(new Dimension(53, 22));
        nameJLabel.setMinimumSize(new Dimension(53, 22));
        nameJLabel.setPreferredSize(new Dimension(53, 22));
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        nameJLabel.setText("*��������");
        stepTypeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        stepTypeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        stepTypeJLabel.setText("*��������");
        stepClassifiJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        stepClassifiJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        stepClassifiJLabel.setText("*�������");
        processTypeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        processTypeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        processTypeJLabel.setText("*�ӹ�����");
        workshopJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        workshopJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        workshopJLabel.setText("*����");
        contentJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contentJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        contentJLabel.setText("��������");

        paraJButton.setMaximumSize(new Dimension(114, 23));
        paraJButton.setMinimumSize(new Dimension(114, 23));
        paraJButton.setPreferredSize(new Dimension(114, 23));
        paraJButton.setMnemonic('E');
        paraJButton.setText("������Ϣ. . .");
        paraJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                paraJButton_actionPerformed(e);
            }
        });
        saveJButton.setMaximumSize(new Dimension(75, 23));
        saveJButton.setMinimumSize(new Dimension(75, 23));
        saveJButton.setPreferredSize(new Dimension(75, 23));
        saveJButton.setActionCommand("SAVE");
        saveJButton.setMnemonic('S');
        saveJButton.setText("����");
        saveJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	
                saveJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        quitJButton.setMaximumSize(new Dimension(75, 23));
        quitJButton.setMinimumSize(new Dimension(75, 23));
        quitJButton.setPreferredSize(new Dimension(75, 23));
        quitJButton.setMnemonic('Q');
        quitJButton.setText("�˳�");
        quitJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quitJButton_actionPerformed(e);
            }
        });
        processHoursJPanel = new ProcessHoursJPanel(parentJFrame);
        storageJButton.setMaximumSize(new Dimension(97, 23));
        storageJButton.setMinimumSize(new Dimension(97, 23));
        storageJButton.setPreferredSize(new Dimension(97, 23));
        storageJButton.setMnemonic('T');
        storageJButton.setText("���");
        storageJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                storageJButton_actionPerformed(e);
            }
        });
        //CCBegin SS5
        relationJTextField.setEditable(false);
        //CCEnd SS5
        relationTechJTextField.setEditable(false);
        relationTechJTextField.addCaretListener(new javax.swing.event.
                                                CaretListener()
        {
            public void caretUpdate(CaretEvent e)
            {
                relationTechJTextField_caretUpdate(e);
            }
        });
        
        
        //CCBegin SS3
        
        viewproJButton.setMaximumSize(new Dimension(100, 23));
        viewproJButton.setMinimumSize(new Dimension(100, 23));
        viewproJButton.setPreferredSize(new Dimension(100, 23));
        viewproJButton.setMnemonic('E');
        viewproJButton.setText("Ԥ������(E)");
        viewproJButton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            viewproJButton_actionPerformed(e);
          }
        });

        viewTechnicsJButton.setMaximumSize(new Dimension(100, 23));
        viewTechnicsJButton.setMinimumSize(new Dimension(100, 23));
        viewTechnicsJButton.setPreferredSize(new Dimension(100, 23));
        viewTechnicsJButton.setMnemonic('H');
        viewTechnicsJButton.setText("Ԥ������(H)");
        viewTechnicsJButton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(ActionEvent e) {
            viewTechnicsJButton_actionPerformed(e);
          }
        });
        
        //CCEnd SS3
        
       
        //Begin CR2
        MouseListener mouseListener= new MouseListener();
        relationTechJTextField.addMouseListener(mouseListener);
        //End CR2
        numDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        numDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        numDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        nameDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        nameDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        nameDisplayJLabel.setPreferredSize(new Dimension(4, 22));

        //add by wangh on 20070201
        descNumDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        descNumDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        descNumDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        
        //CCBegin SS7
        sonTeamDateDisJLabel.setMaximumSize(new Dimension(4, 22));
        sonTeamDateDisJLabel.setMinimumSize(new Dimension(4, 22));
        sonTeamDateDisJLabel.setPreferredSize(new Dimension(4, 22));
        
        //CCEnd SS7
        

        stepTypeDisJLabel.setMaximumSize(new Dimension(4, 22));
        stepTypeDisJLabel.setMinimumSize(new Dimension(4, 22));
        stepTypeDisJLabel.setOpaque(false);
        stepTypeDisJLabel.setPreferredSize(new Dimension(4, 22));
        processTypeDisJLabel.setMaximumSize(new Dimension(4, 22));
        processTypeDisJLabel.setMinimumSize(new Dimension(4, 22));
        processTypeDisJLabel.setPreferredSize(new Dimension(4, 22));
        relationTechDisJLabel.setMaximumSize(new Dimension(10, 22));
        relationTechDisJLabel.setMinimumSize(new Dimension(10, 22));
        relationTechDisJLabel.setPreferredSize(new Dimension(10, 22));
        //Begin CR2
    	relationTechDisJLabel.addMouseListener(mouseListener);
    	//End CR2
        stepClassiDisJLabel.setMaximumSize(new Dimension(4, 22));
        stepClassiDisJLabel.setMinimumSize(new Dimension(4, 22));
        stepClassiDisJLabel.setPreferredSize(new Dimension(4, 22));
        workshopDisJLabel.setMaximumSize(new Dimension(4, 22));
        workshopDisJLabel.setMinimumSize(new Dimension(4, 22));
        workshopDisJLabel.setPreferredSize(new Dimension(4, 22));
        rbJPanel.setLayout(gridBagLayout5);
        contentPanel.setMaximumSize(new Dimension(32767, 80));
        contentPanel.setMinimumSize(new Dimension(10, 10));
        contentPanel.setPreferredSize(new Dimension(100, 50));
        splitJPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitJPanel.setBorder(null);
        splitJPanel.setMinimumSize(new Dimension(337, 357));
        splitJPanel.setOpaque(true);
        splitJPanel.setPreferredSize(new Dimension(337, 500));
        splitJPanel.setContinuousLayout(true);
        splitJPanel.setDividerSize(5);
        splitJPanel.setOneTouchExpandable(true);
        splitJPanel.setResizeWeight(1.0);
        upJPanel.setMinimumSize(new Dimension(337, 200));
        upJPanel.setPreferredSize(new Dimension(337, 300));
        downJPanel.setLayout(gridBagLayout7);
        downJPanel.setMinimumSize(new Dimension(337, 100));
        downJPanel.setPreferredSize(new Dimension(337, 120));
        upJPanel.setLayout(gridBagLayout6);
        numJTextField.setMaximumSize(new Dimension(6, 24));
        
        nameJTextField.setMaximumSize(new Dimension(6, 24));

        //add by wangh on 20070201
        descNumJTextField.setMaximumSize(new Dimension(6, 24));
        descNumJTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                descNumJTextField_actionPerformed(e);
            }
        });
//    descNumJTextField.addActionListener(new java.awt.event.ActionListener() {
//      public void actionPerformed(ActionEvent e) {
//        descNumJTextField_actionPerformed(e);
//      }
//    });

        scrollpane.setBorder(null);
        extendJPanel.add(scrollpane,
                         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
        splitJPanel.add(upJPanel, JSplitPane.TOP);

        splitJPanel.add(downJPanel, JSplitPane.BOTTOM);

        rbJPanel.add(searchTechJButton,
                     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.NONE,
                                            new Insets(0, 8, 0, 0), 0, 0));
        rbJPanel.add(deleteTechJButton,
                     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.NONE,
                                            new Insets(0, 8, 0, 0), 0, 0));
        rbJPanel.add(relationTechJTextField,
                     new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 8, 0, 0), 0, 0));
        rbJPanel.add(relationTechDisJLabel,
                     new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(0, 8, 0, 0), 0, 0));
        //CCBegin SS7
       if((getUserFromCompany().equals("cd"))){
    	   
    	      upJPanel.add(sonTeamDateJLabel,  new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
    	                ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 8, 0, 0), 0, 0));
    	      
    	      upJPanel.add(sonTeamDateTextField,  new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
  	                ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 8, 0, 0), 0, 0));
    	      
    	      
    	      upJPanel.add(sonTeamDateDisJLabel,  new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
    	                ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 8, 0, 0), 0, 0));
    	      
    	      
    	      upJPanel.add(nameJTextField,
                     new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(7, 8, 0, 7), 0, 0));
       }else{
        upJPanel.add(nameJTextField,
                new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(7, 8, 0, 7), 0, 0));
       }
       
       //CCEnd SS7
        
        //���ڲ鿴ģʽ
        upJPanel.add(nameDisplayJLabel,
                     new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 7), 0, 0));

        //���ڸ��ºͲ鿴ģʽ
        upJPanel.add(stepTypeDisJLabel,
                     new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 0), 0, 0));
        //���ڲ鿴ģʽ
        upJPanel.add(processTypeDisJLabel,
                     new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 0), 0, 0));
        
       
        
        

        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        ResourcePanel  rsPanel = new ResourcePanel();
        Collection processTypeCol = rsPanel.getCoding("QMProcedure", "processType", "SortType");
        if (processTypeCol == null || processTypeCol.size() == 0)
        {
            processTypeComboBox.addItem("");
        }
        else
        {
            for (Iterator iter = processTypeCol.iterator(); iter.hasNext(); )
            {
                CodingIfc code = (CodingIfc) iter.next();
                processTypeComboBox.addItem(code);
            }
            //CCBegin SS8
            
            if(createStepType.equals("�ɶ������ҵָ���鹤��"))
               processTypeComboBox.setSelectedIndex(1);
            //CCEnd SS8
        }
        processTypeComboBox.setMaximumSize(new Dimension(93, 22));
        processTypeComboBox.setMinimumSize(new Dimension(93, 22));
        processTypeComboBox.setPreferredSize(new Dimension(93, 22));
        upJPanel.add(processTypeComboBox,
                new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(2, 8, 0, 0), 0, 0));
        
        upJPanel.add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(7, 8, 0, 0), 0, 0));
        
       
      
        
        //CCBegin SS5
        
        upJPanel.add(relationJLabel,  new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 8, 0, 0), 0, 0));
        
      
      
        relationJPanel.add(relationJTextField,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(0, 8, 0, 0), 0, 0));
        
        
        relationJPanel.add(relationDisJLabel,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(0, 8, 0, 0), 0, 0));
        
        
        
        relationJPanel.add(viewProcedureJButton,
                new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 8, 0, 0), 0, 0));

        relationJPanel.add(deleteTechJButton1,
                new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 8, 0, 0), 0, 0));

         relationJPanel.add(searchTechJButton1,
                new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 8, 0, 0), 0, 0));



        
        upJPanel.add(relationJPanel,  new GridBagConstraints(3, 1, 3, 1, 1.0, 0.0
                ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 7), 0, 0));
        
        //CCEnd SS5
        
        
        
        
        //add by wangh on 20070201
        upJPanel.add(descNumJTextField,
                     new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(7, 8, 0, 0), 0, 0));
        
        

        //���ڲ鿴ģʽ
        upJPanel.add(numDisplayJLabel,
                     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(10, 8, 0, 0), 0, 0));
        //add by wangh on 20070201
        upJPanel.add(descNumDisplayJLabel,
                     new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(10, 8, 0, 0), 0, 0));

        //���ڲ鿴ģʽ

        upJPanel.add(relationTechJLabel,
                     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 20, 0, 0), 0, 0));
        //���ڲ鿴ģʽ
        upJPanel.add(stepClassiDisJLabel,
                     new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 7), 0, 0));
        //20080811 XUCY
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        Collection stepCol = rsPanel.getCoding("QMProcedure", "stepClassification", "SortType");
        if (stepCol == null || stepCol.size() == 0)
        {
        	stepTypeComboBox.addItem("");
        }
        else
        {
            for (Iterator iter = stepCol.iterator(); iter.hasNext(); )
            {
                CodingIfc code = (CodingIfc) iter.next();
                stepTypeComboBox.addItem(code);
            }
            
          //CCBegin SS8
            System.out.println("ttttttttttttttt============================="+createStepType);
            if(createStepType.equals("�ɶ������ҵָ���鹤��"))
            	stepTypeComboBox.setSelectedIndex(1);
            //CCEnd SS8
            
            
        }
        stepTypeComboBox.setMaximumSize(new Dimension(80, 22));
        stepTypeComboBox.setMinimumSize(new Dimension(80, 22));
        stepTypeComboBox.setPreferredSize(new Dimension(80, 22));
        upJPanel.add(stepTypeComboBox,
                new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(2, 8, 0, 8), 0, 0));
        upJPanel.add(stepClassifiJLabel,
                     new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 0, 0, 0), 0, 0));
        //���ڲ鿴ģʽ
        upJPanel.add(workshopDisJLabel,
                     new GridBagConstraints(3, 4, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(7, 8, 0, 7), 0, 0));

        upJPanel.add(workshopJLabel,
                     new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 0, 0, 0), 0, 0));
        upJPanel.add(processTypeJLabel,
                     new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 9, 0, 0), 0, 0));
        upJPanel.add(stepTypeJLabel,
                     new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 9, 0, 0), 0, 0));
        upJPanel.add(nameJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(7, 9, 0, 0), 0, 0));
        upJPanel.add(numJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(7, 21, 0, 0), 0, 0));

        //add by wangh on 20070131
        upJPanel.add(descStepNumberJLabel,
                     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.EAST,
                                            GridBagConstraints.NONE,
                                            new Insets(7, 21, 0, 0), 0, 0));

        upJPanel.add(rbJPanel, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(10, 0, 0, 7), 0, 0));

        upJPanel.add(contentJLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(7, 0, 0, 0), 0, 0));
        downJPanel.add(relationsJPanel,
                       new GridBagConstraints(0, 0, 4, 1, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(5, 9, 5, 7), 0, 0));

        upJPanel.add(processHoursJPanel,
                     new GridBagConstraints(0, 7, 4, 1, 1.0, 0.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(7, 13, 0, 7), 0, 0));
        upJPanel.add(contentPanel,
                     new GridBagConstraints(1, 6, 3, 1, 1.0, 1.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(5, 8, 5, 7), 0, 0));

        add(buttonJPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(10, 5, 10, 5), 0, 0));
        buttonJPanel.add(paraJButton,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(quitJButton,
                         new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton,
                         new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(saveJButton,
                         new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(storageJButton,
                         new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(jPanel1, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        
        //CCBegin SS3
        
        
        buttonJPanel.add(viewproJButton,
                new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.EAST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(0, 8, 0, 0), 0, 0));
        
        buttonJPanel.add(viewTechnicsJButton,
                new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.EAST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(0, 8, 0, 0), 0, 0));

        
        //CCEnd SS3
        
        
        
        //add by wangh on 20070202
        this.add(jTabbedPanel,
                 new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                        , GridBagConstraints.CENTER,
                                        GridBagConstraints.BOTH,
                                        new Insets(0, 2, 0, 0), 0, 0));
//20080820 xucy 
//        //�Ӵ˼�����ԭ�򣺵��豸�����������豸ʱ����װ�������Ҫ�������豸��Ҫ�����Ĺ�װ
//        equiplinkJPanel.addListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                toollinkJPanel.addRelationTools(e);
//            }
//        });
        //�Ӵ˼�����ԭ�򣺵���װ���������빤װʱ���豸�������Ҫ�����빤װ��Ҫ�������豸
//        toollinkJPanel.addListener(new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                equiplinkJPanel.addRelationEquipments(e);
//            }
//        });
        //add by wangh on 20070201(��������Ϣ,��������,����FMEA�Ϳ��Ƽƻ�Panel���뵽jTabbedPanel��)
        jTabbedPanel.add(extendJPanel, "������Ϣ");
        if (ts16949)
        {
            jTabbedPanel.add(extendJPanel2, "��������");
            jTabbedPanel.add(extendJPanel3, "����FMEA");
            jTabbedPanel.add(extendJPanel4, "���Ƽƻ�");
        }
//20080820
//        equiplinkJPanel.setToolPanel(toollinkJPanel);
//        toollinkJPanel.setEquipmentPanel(equiplinkJPanel);
        //20080820
        //relationsJPanel.add(equiplinkJPanel, "�豸");
//        relationsJPanel.add(toollinkJPanel,
//                            "��װ");
//        relationsJPanel.add(materiallinkJPanel,
//                            "����");
//        relationsJPanel.add(doclinkJPanel,
//                            "�ĵ�");
//        relationsJPanel.add(drawingLinkPanel, "��ͼ");
//        upJPanel.add(stepClassifiSortingSelectedPanel,
//                     new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0
//                                            , GridBagConstraints.WEST,
//                                            GridBagConstraints.HORIZONTAL,
//                                            new Insets(7, 0, 2, 8), 0, 0));
//        upJPanel.add(processTypeSortingSelectedPanel,
//                     new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
//                                            , GridBagConstraints.WEST,
//                                            GridBagConstraints.HORIZONTAL,
//                                            new Insets(2, 0, 2, 0), 0, 0));

        scrollpane.getViewport().add(splitJPanel, null);

        localize();
        initStepTypeTable();
        splitJPanel.setDividerLocation(300);

        
        
        
    }


    /**
     * ������Ϣ���ػ�
     */
    protected void localize()
    {
        try
        {
            //JLabel
//            numJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                    "stepNumberJLabel", null));
        	
        	 
        		 numJLabel.setText("�����");
        	
        	

            //add by wangh on 20070131
            descStepNumberJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "stepNumberJLabel2", null));

            relationTechJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "relationTechnicsJLabel", null));
            nameJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "stepNameJLabel", null));
            stepTypeJLabel.setText("*��������");
            stepClassifiJLabel.setText("*�������");
            processTypeJLabel.setText("*�ӹ�����");
            workshopJLabel.setText("*����");
            //Ѧ���޸� 20080219 �޸�ԭ���ڹ��չ�����ڹ����´�������,������Ϊ�������ֶ�ǰ��*
            //CCBegin SS9
            if((getUserFromCompany().equals("cd")))
            	descStepNumberJLabel.setText("*��λ");
            else
                descStepNumberJLabel.setText("*������");
            //CCEnd SS9
            //Ѧ���޸Ľ���
            contentJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "mtechContentJLabel", null));
            //JButton
            paraJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "ParaJButton", null));
            saveJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "SaveJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "CancelJButton", null));
            quitJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "QuitJButton", null));
            storageJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "storageJButton", null));
            searchTechJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "browseJButton", null));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.MISSING_RESOURCER, null);
            JOptionPane.showMessageDialog(parentJFrame, message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }  
    }
/**
 * �������ڲ���
 * @author new
 * CR2 ������(guoxl) on 2009-1-15(������������ı���Ťת���˹��յĲ鿴����) 
 * 
 */
	class MouseListener extends MouseAdapter {

		TechnicsRegulationsMainJFrame tecframe;

		// ������
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() instanceof JTextField) {
				relationTechJTextField.setForeground(Color.blue);
				relationTechJTextField.setToolTipText("�����ɲ鿴�˹���");
				// ���Ϊ����
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else if (e.getSource() instanceof JLabel) {
				relationTechDisJLabel.setForeground(Color.blue);
				relationTechDisJLabel.setToolTipText("�����ɲ鿴�˹���");
				// ���Ϊ����
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

		}

		// ����뿪
		public void mouseExited(MouseEvent e) {
			if (e.getSource() instanceof JTextField) {
				relationTechJTextField.setForeground(Color.black);
				setCursor(Cursor.getDefaultCursor());
			} else if (e.getSource() instanceof JLabel) {
				relationTechDisJLabel.setForeground(Color.black);
				setCursor(Cursor.getDefaultCursor());
			}
		}

		// ����ͷ�
		public void mouseReleased(MouseEvent e) {
			// ��ù�������bsoid
			String RelationCardBsoID = getProcedure().getRelationCardBsoID();

			Class[] c = { String.class };
			Object[] obj = { RelationCardBsoID };

			try {
				if (null != RelationCardBsoID
						&& !(RelationCardBsoID.equals(""))) {
					BaseValueIfc baseInfo = (BaseValueIfc) useServiceMethod(
							"PersistService", "refreshInfo", c, obj);

					if (tecframe == null) {

						tecframe = new TechnicsRegulationsMainJFrame(true);

					}

					Object[] obj1 = { baseInfo };

					// ���������չҵ���һ�����չ�����������,����ʾ�˹��յĲ鿴����.
					tecframe.addTechnics(obj1);
					tecframe.setVisible(true);

				} else {
					return;
				}

			} catch (Exception ee) {

				ee.printStackTrace();

			}
		}

	}
    /**
	 * �����ڹ�������ѡ��Ľڵ�
	 * 
	 * @param parentnode
	 */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
        setTechnics();
    }


    /**
	 * ��ȡ��ǰѡ��Ĺ���Ĺ��տ�ͷ
	 * 
	 * @return
	 */
    private void setTechnics()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setTechnics() begin...");
        }
//        CappTreeNode parentNode;
        if (selectedNode.getObject() instanceof StepTreeObject)
        {
            parentTechnics = (QMTechnicsInfo) selectedNode.getP().getObject().
                             getObject();
        }
        else
        {
            parentTechnics = (QMTechnicsInfo) selectedNode.getObject().
                             getObject();

            /*
             if(CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc)parentTechnics)
             &&!CheckInOutCappTaskLogic.isWorkingCopy((WorkableIfc)parentTechnics))
              {
                try {
             parentTechnics=(QMTechnicsInfo)CheckInOutCappTaskLogic.getWorkingCopy(
                      (WorkableIfc)parentTechnics);
                }
                catch (QMRemoteException ex) {
                  ex.printStackTrace();
             String title = QMMessage.getLocalizedMessage(RESOURCE,"information",null);
                  JOptionPane.showMessageDialog(getParentJFrame(),
                                                ex.getClientMessage(),title,
             JOptionPane.INFORMATION_MESSAGE);
                }
              }*/
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setTechnics() end...return:void ");
        }
    }


    /**
     * ���ý���Ϊ����ģʽ
     */
    private void setCreateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setCreateMode() begin...");
        }
        clear();
        changeWorkShopSortingSelectedPanel();
        numJTextField.setVisible(true);
       
        
        //add by wangh on 20070201
        descNumJTextField.setVisible(true);
        descNumDisplayJLabel.setVisible(false);
        //CCBegin SS7
        sonTeamDateTextField.setVisible(true);
        sonTeamDateDisJLabel.setVisible(false);
        //CCEnd SS7
        //CCBegin SS5
        relationJTextField.setVisible(true);
        relationDisJLabel.setVisible(false);
        //CCEnd SS5

        numDisplayJLabel.setVisible(false);
        relationTechJTextField.setVisible(true);
        relationTechDisJLabel.setVisible(false);
        searchTechJButton.setVisible(true);
        nameJTextField.setVisible(true);
        nameDisplayJLabel.setVisible(false);
        stepTypeDisJLabel.setText(stepType.getCodeContent());
        stepClassiDisJLabel.setVisible(false);
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        processTypeComboBox.setVisible(true);
        stepTypeComboBox.setVisible(true);
        processTypeDisJLabel.setVisible(false);
        //workshopSortingSelectedPanel.setVisible(true);
        // workshopDisJLabel.setVisible(false);
        
        contentPanel.setEditable(true);
        drawingLinkPanel.setModel(2); //EDIT

        doclinkJPanel.setMode("Edit");
        //20080820
        //equiplinkJPanel.setMode("Edit");
        //toollinkJPanel.setMode("Edit");
        //materiallinkJPanel.setMode("Edit");
        f1.setEditMode();
        processHoursJPanel.setEnabled(true);
        processHoursJPanel.setCreateMode();
        //{{��ʼ�������
        QMFawTechnicsInfo pTechnics = (QMFawTechnicsInfo) selectedNode.
                                      getObject().
                                      getObject();
        Class[] c =
                {String.class, String.class};
        Object[] objs =
                {pTechnics.getBsoID(), pTechnics.getBsoID()};
        try
        {
            Integer maxNum = (Integer) useServiceMethod(
                    "StandardCappService", "getMaxNumber", c, objs);
            int number = maxNum.intValue();
            if (number == 0)
            {
                numJTextField.setText(String.valueOf(getStepInitNumber()));
                
                
                //add by wangh on 20070201
                descNumJTextField.setText(String.valueOf(getStepInitNumber()));
            }
            else
            {
                if (number >= 99999)
                {
                    return;
                }
                numJTextField.setText(String.valueOf(number + getStepLong()));
                //add by wangh on 20070201
                descNumJTextField.setText(String.valueOf(number + getStepLong()));

            }
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        //����Ĭ��ֵ
        String processType = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.PROCESSTYPE, null);
        //20080811 xucy
//        processTypeSortingSelectedPanel.setDefaultCoding(processType,
//                stepType.getCodeContent());
        String stepClassi = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.STEPCLASSIFI, null);
//        stepClassifiSortingSelectedPanel.setDefaultCoding(stepClassi,
//                stepType.getCodeContent());
        String workshop = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.WORKSHOP, null);
        if (workshopSortingSelectedPanel != null)
        {
            workshopSortingSelectedPanel.setDefaultCoding(workshop,
                    stepType.getCodeContent());
        }
        setButtonVisible(true);
        // paraJButton.setVisible(false);
        refreshObject();
        repaint();
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setCreateMode() end...return is void");
        }
    }


    /**
     * ���ý���Ϊ����ģʽ����������������ʾ�ڽ�����
     */
    private void setUpdateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setUpdateMode() begin...");
        }
        clear();
        changeWorkShopSortingSelectedPanel();
        stepTypeDisJLabel.setText(getProcedure().getTechnicsType().
                                  getCodeContent());
        //������
        numJTextField.setVisible(true);
        numJTextField.setText(Integer.toString(getProcedure().getStepNumber()));
        numDisplayJLabel.setVisible(false);
        //add by wangh on 20070201
        descNumJTextField.setVisible(true);
        descNumJTextField.setText(String.valueOf(getProcedure().
                                                 getDescStepNumber()));
        descNumDisplayJLabel.setVisible(false);
        
       

        //��������
        //���������û�й�������������տ�ά�������򲻿�ά��
        String technicsID = parentTechnics.getBsoID();
        try
        {
            c = CappTreeHelper.browseProcedures(technicsID,
                                                getProcedure().getBsoID());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        //String relationCardBsoID = getProcedure().getRelationCardBsoID();
        relatedTechnicsID = getProcedure().getRelationCardBsoID();
        if (c != null && c.size() != 0)
        {
            relationTechJTextField.setVisible(false);
            relationTechDisJLabel.setVisible(true);
            searchTechJButton.setVisible(false);
            deleteTechJButton.setVisible(false);
        }
        else
        {
            relationTechJTextField.setVisible(true);
            relationTechDisJLabel.setVisible(false);
            searchTechJButton.setVisible(true);
            deleteTechJButton.setVisible(true);
            if (relatedTechnicsID != null && !relatedTechnicsID.equals(""))
            {
                Class[] paraClass1 =
                        {String.class};
                Object[] objs1 =
                        {relatedTechnicsID};
                String displayString = "";
                try
                {
                    QMFawTechnicsInfo relationTechnics = (QMFawTechnicsInfo)
                            useServiceMethod(
                            "PersistService", "refreshInfo", paraClass1, objs1);
                    displayString = getIdentity(relationTechnics);
                    relationTechJTextField.setText(displayString);
                 
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(),
                                                  ex.getClientMessage(), title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }

            }
        }
        
        //CCBegin SS5
        
        String techname = getProcedure().getName();
        relationJTextField.setVisible(true);
        relationDisJLabel.setVisible(false);
        searchTechJButton1.setVisible(true);
        deleteTechJButton1.setVisible(true);
        
        
        if (techname != null && !techname.equals("")) {
            Class[] paraClass1 = {
                String.class};
            Object[] objs1 = {
                techname};
            String displayString = "";
            try {
              Vector techandpro = (Vector)
                  useServiceMethod(
                  "StandardCappService", "getTechInfoAndProInfoByProID", paraClass1,
                  objs1);
              if (techandpro != null && techandpro.size() > 0) {
                QMFawTechnicsIfc techifc = (QMFawTechnicsIfc) techandpro.elementAt(0);
                String techString = getIdentity1(techifc);
                QMProcedureIfc relationProcedure = (QMProcedureIfc) techandpro.
                    elementAt(1);
                String proString = relationProcedure.getDescStepNumber() +"("+
                    relationProcedure.getStepName()+")"+relationProcedure.getVersionValue();
                displayString = techString + "--" + proString;
                this.relatedProcedure = relationProcedure;
                this.technicsinfo = (QMFawTechnicsInfo) techifc;
                relationJTextField.setText(displayString);
              }
            }
            catch (QMRemoteException ex) {
              String title = QMMessage.getLocalizedMessage(RESOURCE,
                  "information", null);
              JOptionPane.showMessageDialog(getParentJFrame(),
                                            ex.getClientMessage(), title,
                                            JOptionPane.
                                            INFORMATION_MESSAGE);
            }
            relationDisJLabel.setText(displayString);
        }
        else {
          relationDisJLabel.setText("");
        }
        
        
        //CCEnd SS5
        
        
         //CCBegin SS7
        
        sonTeamDateTextField.setVisible(true);
        sonTeamDateDisJLabel.setVisible(false);
        sonTeamDateTextField.setText(getProcedure().getSonTeamDate());
        //CCEnd SS7
        
        //��������
        nameJTextField.setVisible(true);
        String name = getProcedure().getStepName();
        //if(name != null && !name.equals(""))
        //  nameJTextField.resumeDataDisplay(name);
        nameJTextField.setText(name);
        nameDisplayJLabel.setVisible(false);
        this.setComboBox(stepTypeComboBox, getProcedure().getStepClassification());     
        stepClassiDisJLabel.setVisible(false);
        //�ӹ�����
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ��� begin
        processTypeComboBox.setVisible(true);
        this.stepTypeComboBox.setVisible(true);
        ResourcePanel  rsPanel = new ResourcePanel();
        rsPanel.setComboBox(processTypeComboBox,getProcedure().getProcessType());
        processTypeDisJLabel.setVisible(false);
        //����
        if (workshopSortingSelectedPanel != null)
        {
            workshopSortingSelectedPanel.setCoding(getProcedure().
                    getWorkShop());
            workshopDisJLabel.setVisible(false);
        }
        else
        {
        	try
        	{
            workshopDisJLabel.setVisible(true);
          //CCBegin by leixiao 2010-4-1 ������ȡ���ղ���
            String workshop="";
            if(getProcedure().getWorkShop() instanceof CodingClassificationIfc){
            	workshop=((CodingClassificationIfc)getProcedure().getWorkShop()).getCodeSort();
            }
            else{
            	workshop=((CodingIfc)getProcedure().getWorkShop()).getCodeContent();
            }
            workshopDisJLabel.setText(workshop);
          //CCBegin by leixiao 2010-4-1 ������ȡ���ղ���
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        }
       
        //Ѧ�� �޸� 20080428 �����Ż����¹��գ�����ٶ�
        drawingLinkPanel.setProcedure(getProcedure());
//        DrawThread dt = new DrawThread();
//        dt.start();
        //Ѧ�� �޸Ľ��� 20080428

        //��������
        contentPanel.setEditable(true);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            contentPanel.clearAll();
            contentPanel.resumeData(v);
        }
        //��ʱ
        processHoursJPanel.setProcedure(getProcedure());
      
        //20080820 xucy
        newEquiplinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(equiplinkJPanel != null)
        {
            equiplinkJPanel.setProcedure(getProcedure());
        }
        newToollinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(toollinkJPanel != null)
        {
        toollinkJPanel.setProcedure(getProcedure());
        }
        newMateriallinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(materiallinkJPanel != null)
        {
        materiallinkJPanel.setProcedure(getProcedure());
        }
        //����
        //20081120 �촺Ӣ�޸�   ����״̬���ĵ�Ӧ�ÿɱ༭
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel, "�ĵ�");
        drawingLinkPanel.setModel(2); //EDIT
        relationsJPanel.add(drawingLinkPanel, "��ͼ");
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setProcedure(getProcedure());

        }
        //�����Ƿ���й������գ�ȷ��processHoursJPanel�͸���������״̬
        setRelatedEff();
        //modify by wangh on 20070615(ȥ�����й�������ʱ�ĵ����ֶ��ఴŦ)
        //doclinkJPanel.setMode("Edit");
        f1.setEditMode();
        //add by wangh on 20070310
        newExtendPanel(getProcedure().getTechnicsType().getCodeContent());
        //CCBegin SS3
        viewproJButton.setEnabled(true);
        viewTechnicsJButton.setEnabled(true);
        //CCEnd SS3
        setButtonVisible(true);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setUpdateMode() end...return is void");
        }
        
        System.out.println("aaaaaaaaaaaaa");//anan
      //add by guoxl on 2009-1-7(�����½�����Ӽ��������������Ϣ�ı�������Ƿ񱣴���ʾ��������ʾ)
        TechnicsContentJPanel.addFocusLis.setCompsFocusListener(this);
        //add by guoxl end
    }

    //CCBegin SS5
    
    private String getIdentity1(QMFawTechnicsIfc technics)
    {
      String result="";
      result=technics.getTechnicsNumber()+"_"+technics.getVersionValue();
      return result;
    }
    
    //CCEnd SS5

    /**
     * ���ý���Ϊ�鿴ģʽ����������������ʾ�ڽ�����
     * ���⣨8��20090112  ��־���޸� �޸�ԭ���Ż�����鿴�ٶȣ�Ӧ��Ϊ���ڲ鿴ģʽ�²�ˢ��ͼֵ����blob����
     *                              ���������ͼ����ϵĲ鿴��ťʱ�����ײ�ˢ�¼�ͼ������
     */
    private void setViewMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setViewMode() begin...");
        }
        clear();

        stepTypeDisJLabel.setVisible(true);
        stepTypeDisJLabel.setText(getProcedure().getTechnicsType().
                                  getCodeContent());
        //�����
        numJTextField.setVisible(false);
        numDisplayJLabel.setVisible(true);
        numDisplayJLabel.setText(Integer.toString(getProcedure().
                                                  getStepNumber()));
        //add by wangh on 20070201
        descNumJTextField.setVisible(false);
        descNumDisplayJLabel.setVisible(true);
        descNumDisplayJLabel.setText(String.valueOf(getProcedure().
                getDescStepNumber()));
       //CCBegin SS5
        
        relationJTextField.setVisible(false);
        relationDisJLabel.setVisible(true);
        //CCEnd SS5
        relationTechJTextField.setVisible(false);
        relationTechDisJLabel.setVisible(true);
        //��������
        //String relationCardBsoID = getProcedure().getRelationCardBsoID();
        relatedTechnicsID = getProcedure().getRelationCardBsoID();
        if (relatedTechnicsID != null && !relatedTechnicsID.equals(""))
        {
            Class[] paraClass1 =
                    {String.class};
            Object[] objs1 =
                    {relatedTechnicsID};
            String displayString = "";
            try
            {
                QMFawTechnicsInfo relationTechnics = (QMFawTechnicsInfo)
                        useServiceMethod(
                        "PersistService", "refreshInfo", paraClass1, objs1);
                displayString = getIdentity(relationTechnics);
                
                //CCBegin SS5
                relationJTextField.setText(displayString);
                
                //CCEnd SS5
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
            relationTechDisJLabel.setText(displayString);
        }
        else
        {
            relationTechDisJLabel.setText("");
        }

        //CCBegin SS7
        
        sonTeamDateTextField.setVisible(false);
        sonTeamDateDisJLabel.setVisible(true);
        sonTeamDateDisJLabel.setText(getProcedure().getSonTeamDate());
        
        //CCEnd SS7
        
        searchTechJButton.setVisible(false);
        deleteTechJButton.setVisible(false);
        //��������
        nameJTextField.setVisible(false);
        nameDisplayJLabel.setVisible(true);
        String name = getProcedure().getStepName();
        nameDisplayJLabel.setText(name);
        stepClassiDisJLabel.setVisible(true);
        stepClassiDisJLabel.setText(
                getProcedure().getStepClassification().getCodeContent());
        //�ӹ�����
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ��� begin
        processTypeComboBox.setVisible(false);
        //�������
        stepTypeComboBox.setVisible(false);
        processTypeDisJLabel.setVisible(true);
        processTypeDisJLabel.setText(getProcedure().getProcessType().
                                     getCodeContent());
        //������
        if (workshopSortingSelectedPanel != null)
        {
            workshopSortingSelectedPanel.setVisible(false);
        }
        workshopDisJLabel.setVisible(true);
        //CCBegin by leixiao 2010-4-1 ������ȡ���ղ���
        String workshop="";
        if(getProcedure().getWorkShop() instanceof CodingClassificationIfc){
        	workshop=((CodingClassificationIfc)getProcedure().getWorkShop()).getCodeSort();
        }
        else{
        	workshop=((CodingIfc)getProcedure().getWorkShop()).getCodeContent();
        }
        	
        workshopDisJLabel.setText(workshop);
      //CCEnd by leixiao 2010-4-1 ������ȡ���ղ��� 

        //��������
        contentPanel.setEditable(false);
        Vector v = getProcedure().getContent();
        if (v.size() > 0)
        {
            contentPanel.resumeData(v);
        }
        //��ʱ����
        processHoursJPanel.setProcedure(getProcedure());
        processHoursJPanel.setViewMode();

        setButtonVisible(false);
        //Ѧ�� �޸� 20080428 �����Ż����¹��գ�����ٶ�
        //���⣨8��20090112  ��־���޸� �޸�ԭ���Ż�����鿴�ٶȣ�Ӧ��Ϊ���ڲ鿴ģʽ�²�ˢ��ͼֵ����blob����
        //                           ���������ͼ����ϵĲ鿴��ťʱ�����ײ�ˢ�¼�ͼ������
        drawingLinkPanel.setProcedure(getProcedure());//CR3
//        DrawThread dt = new DrawThread();
//        dt.start();
        //20080820 xucy
        newEquiplinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(equiplinkJPanel != null)
        {
            equiplinkJPanel.setMode("View");
            equiplinkJPanel.setProcedure(getProcedure());
        }
        newToollinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(toollinkJPanel != null)
        {
        toollinkJPanel.setMode("View");
        toollinkJPanel.setProcedure(getProcedure());
        }
        newMateriallinkJPanel(getProcedure().getTechnicsType().
                getCodeContent());
        if(materiallinkJPanel != null)
        {
        materiallinkJPanel.setMode("View");
        materiallinkJPanel.setProcedure(getProcedure());
        }
        //Ѧ�� �޸Ľ��� 20080428
        //����
        doclinkJPanel.setMode("View");
        doclinkJPanel.setProcedure(getProcedure());
        relationsJPanel.add(doclinkJPanel, "�ĵ�");
        //��ͼ
        drawingLinkPanel.setModel(1); //VIEW
        relationsJPanel.add(drawingLinkPanel, "��ͼ");
        //part����
        newPartlinkJPanel(getProcedure().getTechnicsType().
                          getCodeContent());
        if (partlinkJPanel != null)
        {
            partlinkJPanel.setProcedure(getProcedure());
            partlinkJPanel.setMode("View");
        }
        f1.setViewMode();
        newExtendPanel(getProcedure().getTechnicsType().getCodeContent());
        //CCBegin SS3
        viewproJButton.setEnabled(true);
        viewTechnicsJButton.setEnabled(true);
        //CCEnd SS3
        upJPanel.repaint();
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setViewMode() end...return is void");

        }
    }


    /**
     * ���õ�ǰѡ��Ĺ���
     * @param info
     */
    public void setProcedure(QMProcedureInfo info)
    {
        procedureInfo = info;
    }


    /**
     * ��õ�ǰѡ��Ĺ���
     * @return
     */
    public QMProcedureInfo getProcedure()
    {
        return procedureInfo;
    }


    /**
     * �����������(��š�����)�Ƿ�������Чֵ
     * @return  �����������������Чֵ���򷵻�Ϊ��
     */
    private boolean checkIsEmpty()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.checkIsEmpty() begin...");
        }
        boolean isOK = true;
        String message = null;
        String title = "";
       
		isOK = numJTextField.check();
        
        if (isOK)
        {
            isOK = nameJTextField.check();
        }
        //add by wangh on 20070201
        if (isOK)
        {
            isOK = descNumJTextField.check();
        }
        if (isOK)
        {

//            //����ӹ������Ƿ�Ϊ��
//            if (processTypeSortingSelectedPanel.getCoding() == null)
//            {
//                message = QMMessage.getLocalizedMessage(RESOURCE,
//                        CappLMRB.NO_PROCESSTYPE_ENTERED,
//                        null);
//                isOK = false;
//                processTypeSortingSelectedPanel.getJButton().grabFocus();
//            }
        	//���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
            //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
            //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        	if(processTypeComboBox.getSelectedItem().equals("") || processTypeComboBox.getSelectedItem() == null)
        	{
                message = QMMessage.getLocalizedMessage(RESOURCE,
                       CappLMRB.NO_PROCESSTYPE_ENTERED,
                       null);
                isOK = false;
                processTypeComboBox.grabFocus();
        	}
            //���鹤�������Ƿ�Ϊ��
            else if (contentPanel.save() == null ||
                     contentPanel.save().trim().equals(""))
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
                        null);
                isOK = false;
                contentPanel.getTextComponent().grabFocus();
            }
            //���鹤������Ƿ�Ϊ��
        	//���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
            //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
            //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ��� begin
            else if (this.stepTypeComboBox.getSelectedItem().equals("") || stepTypeComboBox.getSelectedItem() == null)
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_STEPCLASSIFI_ENTERED,
                        null);
                isOK = false;
                stepTypeComboBox.grabFocus();
            }
            //���鲿���Ƿ�Ϊ��
            else if (workshopSortingSelectedPanel != null &&
                    workshopSortingSelectedPanel.getCoding() == null)
           {
               message = QMMessage.getLocalizedMessage(RESOURCE,
                       CappLMRB.NO_WORKSHOP_ENTERED,
                       null);
               isOK = false;
               workshopSortingSelectedPanel.getJButton().grabFocus();
            }
            else
            {
                String tempString = contentPanel.save().trim();
                if (1 == tempString.length())
                {
                    int tempChar = tempString.charAt(0);
                    if (tempChar == 128)
                    {
                        message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.NO_TECHNICS_CONTENT_ENTERED,
                                null);
                        isOK = false;
                        contentPanel.getTextComponent().grabFocus();
                    }
                }
            }
        }
        //�����ͼ�����ʽ�Ƿ�Ϊ��
        /* else if ( drawingExportJComboBox.isVisible()&& drawingExportSortingSelectedPanel.getCoding()==null)
         {
           message = QMMessage.getLocalizedMessage(RESOURCE,
               CappLMRB.NO_DRAWINGEXPORT_ENTERED,
               null);
           isOK = false;
           drawingExportSortingSelectedPanel.getJButton().grabFocus();
          }*/
        if (!isOK && message != null)
        {
            //��ʾ��Ϣ��ȱ�ٱ�����ֶ�
            title = QMMessage.getLocalizedMessage(RESOURCE,
                                                  CappLMRB.
                                                  REQUIRED_FIELDS_MISSING,
                                                  null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.checkIsEmpty() end...return: " +
                    isOK);
        }
        return isOK;
    }


    /**
     * ���ý���ģʽ�����������»�鿴����
     * @param aMode �½���ģʽ
     * @exception java.beans.PropertyVetoException ���ģʽMode��Ч�����׳��쳣��
     */
    public void setViewMode(int aMode)
            throws PropertyVetoException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setViewMode() begin...");
        }
        if ((aMode == UPDATE_MODE) ||
            (aMode == CREATE_MODE) ||
            (aMode == VIEW_MODE))
        {
            mode = aMode;
        }
        else
        {
            //��Ϣ����Чģʽ
            throw (new PropertyVetoException(QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "invalid Mode", null),
                                             new PropertyChangeEvent(this,
                    "mode",
                    new Integer(getViewMode()),
                    new Integer(aMode))));
        }

        switch (aMode)
        {

            case CREATE_MODE: //����ģʽ
            {
                setCreateMode();
                break;
            }

            case UPDATE_MODE: //����ģʽ
            {
                setUpdateMode();
                break;
            }

            case VIEW_MODE: //�鿴ģʽ
            {
                setViewMode();
                break;
            }
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.setViewMode() end...return is void");
        }
    }


    /**
     * ��ý���ģʽ
     * @return ���������¡��鿴�����Ϊģʽ
     */
    public int getViewMode()
    {
        return mode;
    }


    /**
     * ִ�б������
     * @param e
     */
    void saveJButton_actionPerformed(ActionEvent e)
    {
        WorkThread thread = new WorkThread();
        thread.start();

    }


    /**
     * ����
     */
    private void save()
    {

        if (getViewMode() == CREATE_MODE)
        {
            saveWhenCreate();
        }
        else if (getViewMode() == UPDATE_MODE)//CR1
        {
            saveWhenUpdate();
        }
        ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();

    }


    /**
     * ִ��ȡ������
     * @param e
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        if (getViewMode() == CREATE_MODE)
        {
            cancelWhenCreate();
        }
        else if (getViewMode() == UPDATE_MODE)
        {
            cancelWhenUpdate();
        }
    }


    /**
     * ����ģʽ�£�ȡ����ť��ִ�з���
     * <p>���Ѿ�¼������ݶ��ÿ�</p>
     */
    private void cancelWhenCreate()
    {
        String num = numJTextField.getText();
        setCreateMode();
        numJTextField.setText(num);
        //add by wangh on 20070201
        descNumJTextField.setText(num);
        /*numJTextField.setText("");
                 nameJTextField.setText("");
                 relationTechJTextField.setText("");
                 stepClassifiSortingSelectedPanel.setViewTextField(null);
                 processTypeSortingSelectedPanel.setViewTextField(null);
                 workshopSortingSelectedPanel.setViewTextField(null);
                 drawingExportJComboBox.setSelectedIndex(0);
                 contentPanel.clearAll();
                 processHoursJPanel.clear();
                 equiplinkJPanel.clear();
                 materiallinkJPanel.clear();
                 toollinkJPanel.clear();
                 doclinkJPanel.clear();
                 if(partlinkJPanel!=null)
            partlinkJPanel.clear();
                 drawingPanel.setDrawingDate(null);*/

    }


    /**
     * ����ģʽ�£�ȡ����ť��ִ�з���
     * <p>������¼������ݳ����������ϴ�¼�������</p>
     */
    private void cancelWhenUpdate()
    {
        setUpdateMode();
    }


    /**
     * ִ���˳�����
     * @param e
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();
    }


    /**
     * �˳�
     * @return boolean
     */
    protected boolean quit()
    {

        if (getViewMode() == CREATE_MODE)
        {
            quitWhenCreate();
        }
        else if (getViewMode() == UPDATE_MODE)
        {
            quitWhenUpdate();
        }
        else if (getViewMode() == VIEW_MODE)
        {
            quitWhenView();

        }
        return isSave;

    }


    /**����Ƿ�����˳�*/
    private boolean isSave = true;


    /**
     * ����ģʽ�£��˳���ť��ִ�з���
     */
    private void quitWhenCreate()
    {
        String s = QMMessage.getLocalizedMessage(
                RESOURCE, CappLMRB.IS_SAVE_QMPROCEDURE, null);
        int i = confirmAction(s);//Begin CR1
		if (i == JOptionPane.YES_OPTION)
		{
			saveWhenCreate();
		}
		if (i == JOptionPane.NO_OPTION)
		{
			setVisible(false);
			isSave = true;
		}
		if (i == JOptionPane.CANCEL_OPTION)
		{
			isSave = false;
		}//End CR1

    }


    /**
     * ����ģʽ�£��˳���ť��ִ�з���
     * add by guoxl 2009-1-7(ֻ�и��½��������޸���,�ŵ����Ƿ񱣴�Ի���)
     */
//    private void quitWhenUpdate()
//    {
//        String s = QMMessage.getLocalizedMessage(
//                RESOURCE, CappLMRB.IS_SAVE_QMPROCEDURE_UPDATE, null);
//
//        if (confirmAction(s))
//        {
//            saveWhenUpdate();
//        }
//        else
//        {
//            setVisible(false);
//            isSave = true;
//        }
//    }

    private void quitWhenUpdate()
    {
        String s = QMMessage.getLocalizedMessage(
                RESOURCE, CappLMRB.IS_SAVE_QMPROCEDURE_UPDATE, null);
        
        boolean ischange=TechnicsContentJPanel.addFocusLis.finalChangeValue();
        System.out.println("quitWhenUpdate!!!!!!!!!!");
        if(ischange){
        	int i = confirmAction(s);//Begin CR1
    		if (i == JOptionPane.YES_OPTION)
    		{
    			saveWhenUpdate();
    		}
    		if ((i == JOptionPane.NO_OPTION))
    		{
    			setVisible(false);
    			isSave = true;
    		}
    		if ((i == JOptionPane.CANCEL_OPTION))
    		{
    			isSave = false;
    		}//End CR1
        }else{
        	
        	setVisible(false);
            isSave = true;
        	
        }
    }


    /**
     * �鿴ģʽ�£��˳���ť��ִ�з���
     */
    private void quitWhenView()
    {
        setVisible(false);
        isSave = true;
    }


    /**
     * ��ʾȷ�϶Ի���
     * @param s �ڶԻ�������ʾ����Ϣ
     * @return  ����û�ѡ���ˡ�ȷ������ť���򷵻�true;���򷵻�false
     */
    private int confirmAction(String s)//CR1
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
//        JOptionPane okCancelPane = new JOptionPane();
        return JOptionPane.showConfirmDialog(getParentJFrame(), s, title,
				JOptionPane.YES_NO_CANCEL_OPTION);//CR1
    }


    /**
     * ���ù��򿨵��������Ժ͹������������Ϣ��װ����
     * @return  ��Ϣ��װ����
     * ����(3) 20080602 �촺Ӣ�޸�  �޸�ԭ��:��ӹ�������ĵ�ʱ,��ͨ�û���������ȫ���ĵ�,��Ϊ
     * �������������ĵ�masterû��Ȩ�޿���,����Ҫ�����������ʾ�ĵ�,��������Ȩ�޿�����
     * �����浽���ݿ�ʱ,Ӧ��ת�����ĵ�master�͹���Ĺ���
     */
    private CappWrapData commitAttributes()
    {
        //���ù�������(��š����ơ��������ࡢ������𡢼ӹ����͡����š��������ա����ռ�ͼ��
        //��ͼ�����ʽ)
        //�����ǹ���,�����ù�������
        processHoursJPanel.setProcedure(getProcedure());
        
        //CCBegin SS4
        getProcedure().setIsProcedure(true);
        //CCEnd SS4
        
        getProcedure().setIsProcedure(true);
        if (getViewMode() == CREATE_MODE)
        {
            CodingIfc code = stepType;
            getProcedure().setTechnicsType(code);
        }
        //���ñ��
        if (numJTextField.getText().length() > 5)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.STEP_NUMBER_INVALID, null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.STEPNUMBER_TOO_LONG, null);
            JOptionPane.showMessageDialog(getParentJFrame(), message,
                                          title,
                                          JOptionPane.WARNING_MESSAGE);
            numJTextField.grabFocus();
            return null;
        }
       
        Integer i = Integer.valueOf(numJTextField.getText().trim());
        getProcedure().setStepNumber(i.intValue());
        //add by wangh on 20070208(�õ������ù�����)
        String s = String.valueOf(descNumJTextField.getText().trim());
        getProcedure().setDescStepNumber(s.toString());
        //��������
        getProcedure().setStepName(nameJTextField.getText());
        
        //CCBegin SS7
        System.out.println("1111111=============="+sonTeamDateTextField.getText());
        getProcedure().setSonTeamDate(sonTeamDateTextField.getText());
        //CCEnd SS7
        
        //���ù������
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ��� begin
        getProcedure().setStepClassification((CodingIfc)stepTypeComboBox.getSelectedItem());
        getProcedure().setProcessType((CodingIfc)processTypeComboBox.getSelectedItem());
        //���ò���
        //���ò���
        if (workshopSortingSelectedPanel != null)
        {
            getProcedure().setWorkShop(
                    (CodingIfc) workshopSortingSelectedPanel.getCoding());
        }
        else
        {
            if (mode == CREATE_MODE)
            {
            //  CCBeginby leixiao 2010-4-2 ԭ�򣺹��ղ��ſ��޸�,������ȡ���ղ���
                getProcedure().setWorkShop((BaseValueIfc)
                                           parentTechnics.getWorkShop());
            //  CCEndby leixiao 2010-4-2 ԭ�򣺹��ղ��ſ��޸�,������ȡ���ղ���
            }
        }
        //���ù�������
        //if (relatedTechnics != null)
        //{
        //getProcedure().setRelationCardBsoID(relatedTechnics.
        //                                      getBsoID());
        //}
        //else
        //{
        //getProcedure().setRelationCardBsoID(null);
        // }
        //if(relationTechJTextField.getText()!=null&&relationTechJTextField.getText().trim().length()!=0){
        getProcedure().setRelationCardBsoID(relatedTechnicsID);
        // }
        
        //CCBegin SS5
        if (relatedProcedure != null) {
            getProcedure().setName(relatedProcedure.
                                   getBsoID());
          }
          else {
            getProcedure().setName(null);
          }
        
        //CCEnd SS5
        
        
        //���ù�������
        Vector v = new Vector();
        v.addElement(contentPanel.save());
        getProcedure().setContent(v);
        //���㵥����ʱ(�����ʱ����ά������û�й�������)
        if (processHoursJPanel.getMode() == "EDIT" &&
            processHoursJPanel.isEnabled())
        {
            try
            {
                processHoursJPanel.setHours();
            }
            catch (QMRemoteException ex)
            {
            	ex.printStackTrace();
                String title = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.SAVE_STEP_FAILURE, null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(), title,
                                              JOptionPane.INFORMATION_MESSAGE);
                processHoursJPanel.clear();
                return null;
            }
        }

        //�������ϼ�
        if (getViewMode() == CREATE_MODE)
        {

            QMFawTechnicsInfo pTechnics = (QMFawTechnicsInfo) selectedNode.
                                          getObject().getObject();
            procedureInfo.setLocation(pTechnics.getLocation());

        }

        //ά�����ղ���
        if (d != null)
        {
            ExtendAttContainer c = d.getExtendAttributes();
            if (c != null)
            {
                if (d.check())
                {
                    getProcedure().setExtendAttributes(c);
                }
                else
                {
                    if (verbose)
                    {
                        System.out.println("��չ����¼�����");
                    }
                    isSave = false;
                    return null;
                }
            }
        }
        //add by wangh on 20070212
        if (processFlowJPanel.check())
        {
            //���ù�������
            procedureInfo.setProcessFlow(processFlowJPanel.
                                         getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("�����������¼�����");
            }
            isSave = false;
            return null;
        }
        if (femaJPanel.check())
        {
            //����FMEA
            procedureInfo.setFema(femaJPanel.getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("����FMEA¼�����");
            }
            isSave = false;
            return null;
        }
        if (processControlJPanel.check())
        {
            //���ù��̿���
            procedureInfo.setProcessControl(processControlJPanel.
                                            getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("������̿���¼�����");
            }
            isSave = false;
            return null;
        }

        //�����й����ϲ�
        Vector resourceLinks = new Vector();

        //������й���(�豸����װ�����ϡ��ĵ�)
        Vector docLinks;
        //20080820
        Vector equipLinks = null;
        Vector toolLinks = null;
        Vector materialLinks = null;
        Vector partLinks = null;
//        ArrayList pDrawings = null;
//        ArrayList drawingLinks = null;
		ArrayList updatedrawings = null;//Begin CR3
		ArrayList deletedrawings = null;//End CR3
        //����(3) 20080602 �촺Ӣ�޸�  �����������ĵ�ת�����ĵ�master�͹���Ĺ���
        Vector docMasterLinks = new Vector();
        try
        {
            docLinks = doclinkJPanel.getAllLinks();
            int size = docLinks.size();
            for (int j = 0; j < size; j++)
            {
                String docId = ((QMProcedureQMDocumentLinkInfo) docLinks.
                                elementAt(j)).getRightBsoID();
                Class[] paraClass1 =
                        {String.class};
                Object[] objs1 =
                        {docId};
                //DocMasterInfo masterInfo = new DocMasterInfo();
                String masterID = "";
                try
                {
                    BaseValueInfo docInfo = (BaseValueInfo)
                                            useServiceMethod(
                            "PersistService", "refreshInfo", paraClass1,
                            objs1);
                    if (docInfo instanceof DocInfo)
                    {
                        masterID = ((DocInfo) docInfo).getMasterBsoID();
                        QMProcedureQMDocumentLinkInfo linkInfo = new
                                QMProcedureQMDocumentLinkInfo();
                        linkInfo.setRightBsoID(masterID);
                        linkInfo.setLeftBsoID(((
                                QMProcedureQMDocumentLinkInfo)
                                               docLinks.elementAt(j)).
                                              getLeftBsoID());
                        docMasterLinks.add(linkInfo);
                    }
                    else
                    if (docInfo instanceof DocMasterInfo)
                    {
                        docMasterLinks.add((QMProcedureQMDocumentLinkInfo)
                                           docLinks.elementAt(j));
                    }
                    //displayString = getIdentity(relationTechnics);
                }
                catch (QMRemoteException ex)
                {
                	ex.printStackTrace();
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(),
                                                  ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }

            }
            //20080820 xucy
            if(equiplinkJPanel != null)
            {
                equipLinks = equiplinkJPanel.getAllLinks();
            }
            if(toollinkJPanel != null)
            {
            toolLinks = toollinkJPanel.getAllLinks();
            }
            if(materiallinkJPanel != null)
            {
            materialLinks = materiallinkJPanel.getAllLinks();
            }
        

            if (partlinkJPanel != null)
            {
                partLinks = partlinkJPanel.getAllLinks();

            }
            //CCBegin SS2 �ж�˳����Ƿ��ظ�
            boolean checkFlag = drawingLinkPanel.checkSeqNum();
            System.out.println("sssssssssssssssssssS===="+checkFlag);
            if (checkFlag) {
            	return null;
            }
            //CCEnd  SS2
            Object[] obj = drawingLinkPanel.getDrawings();
            if (obj != null)
            {
				updatedrawings = (ArrayList) obj[0];//Begin CR3
				deletedrawings = (ArrayList) obj[1];//End CR3
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
            isSave = false;
            return null;
        }

        //�ϲ��ĵ�����
        if (docMasterLinks != null)
        {
            for (int k = 0; k < docMasterLinks.size(); k++)
            {
                resourceLinks.addElement(docMasterLinks.elementAt(k));
            }
        }

        //�ϲ����Ϲ���
        if (materialLinks != null)
        {
            for (int j = 0; j < materialLinks.size(); j++)
            {
                resourceLinks.addElement(materialLinks.elementAt(j));
            }
        }
        
        //�ϲ��豸����
        if (equipLinks != null)
        {
            for (int m = 0; m < equipLinks.size(); m++)
            {
                resourceLinks.addElement(equipLinks.elementAt(m));
            }
        }

        //�ϲ���װ����
        if (toolLinks != null)
        {
            for (int n = 0; n < toolLinks.size(); n++)
            {
                resourceLinks.addElement(toolLinks.elementAt(n));
            }
        }
		// �ϲ���ͼ��Դ����   Begin CR3
		/*if (drawingLinks != null)
		{
			for (int n = 0; n < drawingLinks.size(); n++)
			{
				resourceLinks.addElement(drawingLinks.get(n));
			}
		}*/  
        //End CR3

        //�ϲ��㲿������
        if (partLinks != null)
        {
            for (int n = 0; n < partLinks.size(); n++)
            {
                resourceLinks.addElement(partLinks.elementAt(n));
            }
        }

        //��װ������Ϣ
        CappWrapData cappWrapData = new CappWrapData();
        //���ù���
        cappWrapData.setQMProcedureIfc(getProcedure());
        //��װ����
        if (resourceLinks != null)
        {
            cappWrapData.setQMProcedureUsageResource(resourceLinks);

        }
//        cappWrapData.setPDrawings(pDrawings);
		cappWrapData.setUpdateDrawing(updatedrawings);//Begin CR3
	    cappWrapData.setDeleteDrawing(deletedrawings);//End CR3

        return cappWrapData;

    }


//add by wangh on 20070207(���ݲ�ͬ���������ò�ͬ�������̣�FMEA�͹��̿���)
    //���⣨5��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
    private void newExtendPanel(String processType)
    {
        if (!processType.equals(existProcessType))
        {
            if (processFlowJPanel != null)
            {
                extendJPanel2.remove(processFlowJPanel);
            }
            if (processFlowTable.get(processType) == null)
            {
                try
                {
                    processFlowJPanel = new CappExAttrPanel(procedureInfo.
                            getBsoName(),
                            "��������", 1);
                    
                }
                catch (QMException ex)
                {
                    ex.printStackTrace();
                }
                processFlowTable.put(processType, processFlowJPanel);
                
            }
            else
            {
                processFlowJPanel = (CappExAttrPanel) processFlowTable.get(
                        processType);
            }
            // CCBegin by leixiao 2008-10-28 ԭ�򣺽��ϵͳ����,�������ݴ��� 
            processFlowJPanel.setProIfc(procedureInfo);
//          CCEnd by leixiao 2008-10-28 ԭ�򣺽��ϵͳ���� 
            if (femaJPanel != null)
            {
                extendJPanel3.remove(femaJPanel);
            }
            if (femaTable.get(processType) == null)
            {
                try
                {
                    femaJPanel = new CappExAttrPanel(procedureInfo.getBsoName(),
                            "����FMEA", 1);
                }
                catch (QMException ex)
                {
                    ex.printStackTrace();
                }
                femaTable.put(processType, femaJPanel);
            }
            else
            {
                femaJPanel = (CappExAttrPanel) femaTable.get(
                        processType);
            }
            // CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
            femaJPanel.setProIfc(procedureInfo);
            // CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
            if (processControlJPanel != null)
            {
                extendJPanel4.remove(processControlJPanel);
            }
            if (processControlTable.get(processType) == null)
            {
                try
                {
                    processControlJPanel = new CappExAttrPanel(procedureInfo.
                            getBsoName(),
                            "���Ƽƻ�", 1);
                }
                catch (QMException ex)
                {
                    ex.printStackTrace();
                }
                processControlTable.put(processType, processControlJPanel);
            }
            else
            {
                processControlJPanel = (CappExAttrPanel) processControlTable.
                                       get(
                        processType);
            }
            // CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
            processControlJPanel.setProIfc(procedureInfo);
            // CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ����
        }
        processFlowJPanel.clear();
        femaJPanel.clear();
        processControlJPanel.clear();
        if (mode == CREATE_MODE ||
            mode == UPDATE_MODE)
        {
            processFlowJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
            femaJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
            processControlJPanel.setModel(CappExAttrPanel.EDIT_MODEL);
        }
        else
        {
            processFlowJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
            femaJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
            processControlJPanel.setModel(CappExAttrPanel.VIEW_MODEL);
        }
        if (mode != CREATE_MODE)
        {
            processFlowJPanel.show(getProcedure().getProcessFlow());
            femaJPanel.show(getProcedure().getFema());
            processControlJPanel.show(getProcedure().getProcessControl());
        }
        extendJPanel2.add(processFlowJPanel,
                          new GridBagConstraints(0, 0, 1, 1, 1.0,
                                                 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), 0, 0));
        extendJPanel3.add(femaJPanel,
                          new GridBagConstraints(0, 0, 1, 1, 1.0,
                                                 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), 0, 0));
        extendJPanel4.add(processControlJPanel,
                          new GridBagConstraints(0, 0, 1, 1, 1.0,
                                                 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), 0, 0));

        //add by wangh on 200726(���ù������̣�����FMEA�Ϳ��Ƽƻ��Ƿ�ɼ�)
//       if (!ts16949) {
//         processFlowJPanel.setVisible(false);
//         femaJPanel.setVisible(false);
//         processControlJPanel.setVisible(false);
//       }
        //���⣨5��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
        processControlJPanel.groupPanel.flowMultiList = processFlowJPanel.groupPanel.multiList;
        //processControlJPanel.groupPanel.processFlowJPanel=processFlowJPanel;
        repaint();
        processType = "";
    }


    /**
     * �����½��Ĺ���
     */
    private void saveWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenCreate() begin...");
        }
        setButtonWhenSave(false);
        //�����жϱ��������Ƿ�����
        boolean requiredFieldsFilled;
        //���������״Ϊ�ȴ�״̬
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //�����������Ƿ�����
        requiredFieldsFilled = checkIsEmpty();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
       
        
			//�������Ƿ�Ϊ����
			if (!checkIsInteger(numJTextField.getText().trim()))
			{
			    numJTextField.setText("");
			    setCursor(Cursor.getDefaultCursor());
			    setButtonWhenSave(true);
			    isSave = false;
			    numJTextField.grabFocus();
			    return;
			}
		

		
        //���ù��򿨵��������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        QMProcedureInfo returnProce;//CR1
        try
        {
            //��ÿ�ͷ�����ѡ��ڵ�ʱ�ڵ��ڹ������ϼУ���ԭ�����������丱��
            QMTechnicsInfo pTechnics =
                    (QMTechnicsInfo) selectedNode.getObject().getObject();
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc) pTechnics))
            {
                pTechnics = (QMTechnicsInfo) CheckInOutCappTaskLogic.
                            getWorkingCopy(
                        (WorkableIfc) pTechnics);

                //���÷��񣬱��湤��
            }
            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Class[] paraClass =
                    {String.class, String.class, CappWrapData.class};
            //��������Ϊ�� �ù��������Ĺ��տ�BsoID,���ڵ�BsoID,���򿨶���ķ�װ�����
            Object[] obj =
                    {pTechnics.getBsoID(), pTechnics.getBsoID(), cappWrapData};
            returnProce = (QMProcedureInfo) useServiceMethod(
                    "StandardCappService", "createQMProcedure", paraClass, obj);
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //20060727Ѧ���޸ģ�ȥ��ˢ�¹��սڵ�Ĳ���
            //ˢ�����ڵ�
            //((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
            //   pTechnics, pTechnics.getBsoID());
            //begin cr4
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }//end cr4
            //���½ڵ�
            StepTreeObject treeObject = new StepTreeObject(returnProce);
            treeObject.setParentID(pTechnics.getBsoID());
            if (parentJFrame instanceof TechnicsRegulationsMainJFrame)
            {
                ((TechnicsRegulationsMainJFrame) parentJFrame).
                        getProcessTreePanel().
                        addNode(selectedNode.getObject(), treeObject);
            }
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            //ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }

        //���ؽ�����
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        //��ʾ�Ƿ�������
        String s = QMMessage.getLocalizedMessage(RESOURCE, "108", null);//Begin CR1
		int i = confirmAction(s);
    	System.out.println("11111111111111");
		if (i == JOptionPane.YES_OPTION)
		{
    	System.out.println("222222");
			setCreateMode();
			isSave = false;
			setButtonWhenSave(true);
			return;
		}
		if ((i == JOptionPane.NO_OPTION))
		{
    	System.out.println("33333333333");
			try
			{
				setProcedure(returnProce);
				mode = 0; 
			}
			catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			TechnicsContentJPanel.addFocusLis.initFlag();//anan
		}
		if ((i == JOptionPane.CANCEL_OPTION))
		{
    	System.out.println("44444444444");
			isSave = false;
		}//End CR1
    	System.out.println("55555555555");
        setButtonWhenSave(true);
        isSave = true;
    	System.out.println("66666666666");
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenCreate() end...return is void");
        }
    }


    /**
     * ������º�Ĺ���
     */
    private void saveWhenUpdate()
    {
    	System.out.println("77777777777");
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() begin...");
        }
        //add by guoxl on 2009-1-8(�����򣨹��������½�����Ӽ��������������Ϣ�����������
        // �Ƿ񱣴���ʾ��������ʾ)
         TechnicsContentJPanel.addFocusLis.initFlag();
         //add by guoxl end
        setButtonWhenSave(false);
        //�����жϱ��������Ƿ�����
        boolean requiredFieldsFilled;
        //���������״Ϊ�ȴ�״̬
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //�����������Ƿ�����
        requiredFieldsFilled = checkIsEmpty();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
       
			//�������Ƿ�Ϊ����
			if (!checkIsInteger(numJTextField.getText().trim()))
			{
			    numJTextField.setText("");
			    setCursor(Cursor.getDefaultCursor());
			    setButtonWhenSave(true);
			    isSave = false;
			    numJTextField.grabFocus();
			    return;
			}
		
		
        //���ù��򿨵��������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        //��ʾ�������
        //ProgressService.setProgressText(QMMessage.getLocalizedMessage(
        //       RESOURCE, CappLMRB.SAVING, null));
        //ProgressService.showProgress();
        try
        {
            parentTechnics = (QMTechnicsInfo) refreshInfo(parentTechnics);
            if (CheckInOutCappTaskLogic.isInVault((WorkableIfc) parentTechnics))
            {
                parentTechnics = (QMTechnicsInfo) CheckInOutCappTaskLogic.
                                 getWorkingCopy(
                        (WorkableIfc) parentTechnics);
                //���÷��񣬱��湤��
            }

            ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
            Class[] paraClass =
                    {String.class, String.class, CappWrapData.class};
            Object[] obj =
                    {parentTechnics.getBsoID(), parentTechnics.getBsoID(),
                    cappWrapData};
            QMProcedureInfo returnProce;

            returnProce = (QMProcedureInfo) useServiceMethod(
                    "StandardCappService", "updateQMProcedure", paraClass, obj);
            //begin cr4
            if(drawingLinkPanel!=null){
            	drawingLinkPanel.resetArrayList();
            }
            //end cr4
            //CCBegin SS2 �����,����ˢ�½���
            drawingLinkPanel.setProcedure(getProcedure());
            //CCEnd  SS2
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            //ˢ�����ڵ�
            ((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
                    returnProce, parentTechnics.getBsoID());
            procedureInfo = returnProce;
        }
        catch (QMRemoteException ex)
        {
        	ex.printStackTrace();
        	//20081119 xucy �޸�   �޸�ԭ�򣺳��쳣��ʱ��ѽ������ص�
        	((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            //  ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            setVisible(false);
            return;
        }

        //���ؽ�����
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        isSave = true;  
        //���⣨6��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤��    begin 
        //ת���ɲ鿴����
//        try
//        {
//            setViewMode(VIEW_MODE);
//        }
//        catch (PropertyVetoException ex1)
//        {
//        	//20081119 xucy �޸�   �޸�ԭ�򣺳��쳣��ʱ��ѽ������ص�
//        	((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
//            ex1.printStackTrace();
//        }
        //���⣨6��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤��   end
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel.saveWhenUpdate() end...return is void");
        }
    }


    /**
     * ���ð�ť����ʾ״̬����Ч��ʧЧ��
     * @param flag  flagΪTrue����ť��Ч������ťʧЧ
     */
    private void setButtonWhenSave(boolean flag)
    {
        quitJButton.setEnabled(flag);
        saveJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
        paraJButton.setEnabled(flag);
        storageJButton.setEnabled(flag);
    }


    /**
     * ���ð�ť�Ŀɼ���
     * @param flag
     */
    private void setButtonVisible(boolean flag)
    {
        saveJButton.setVisible(flag);
        cancelJButton.setVisible(flag);
        storageJButton.setVisible(flag);

    }


    /**
     * ��������
     * @param e
     */
    void searchTechJButton_actionPerformed(ActionEvent e)
    {

        searchRelatedTechnics();
    }


    /**
     * ������������
     */
    private void searchRelatedTechnics()
    {
        try
        {
            sd = new TechnicsSearchJDialog((TechnicsRegulationsMainJFrame)
                                           parentJFrame);
            sd.setModal(false);
            sd.setSingleSelectMode();
            sd.addQueryListener(new CappQueryListener()
            {
                public void queryEvent(CappQueryEvent e)
                {
                    qmQuery_queryEvent(e);
                }
            });
            //���TechnicsSearchJDialog�е�mutilist���е�˫������
            sd.addMultiListActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    myList_actionPerformed(e);
                }
            }
            );
            sd.setVisible(true);
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
    }


    /**�����������յĽ������*/
    private TechnicsSearchJDialog sd = null;


    /**�������գ����ڻ��棩*/
    //private QMFawTechnicsInfo relatedTechnics = null;
    private String relatedTechnicsID = null;


    /**
     * �������ռ����¼�����
     * @param e ���������¼�
     */
    public void qmQuery_queryEvent(CappQueryEvent e)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel:qmQuery_queryEvent(e) begin...");
        }
        if (e.getType().equals(CappQueryEvent.COMMAND))
        {
            if (e.getCommand().equals(CappQuery.OkCMD))
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                CappQuery c = (CappQuery) e.getSource();
                BaseValueIfc[] bvi = c.getSelectedDetails();
                actionPerformed(bvi);
                setCursor(Cursor.getDefaultCursor());
            }
            if (e.getCommand().equals(CappQuery.QuitCMD))
            {
                sd.setVisible(false);
            }
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsStepJPanel:qmQuery_queryEvent(e) end...return is void");
        }
    }


    /**
     *�������еĹ��չ�̼���������ա��ı����У��˷����ṩ��TechnicsSearchJDialog�����������¼�
     */
    private void actionPerformed(Object[] bvi)
    {
        if (bvi != null)
        {
            //�Ѵӽ����ѡ�е�ҵ�������롰�������ա��ı���
            for (int i = 0; i < bvi.length; i++)
            {
                QMFawTechnicsInfo info = (QMFawTechnicsInfo) bvi[i];
                String compareBsoID = "";
                //Begin CR6
                if(info.getWorkableState().equals("wrk"))
                {
                    String s = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.RELATIONTECHNICS_CANTBE_CHECK_OUT_OBJECT, null);
                    String ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(), s, ss1,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    setCursor(Cursor.getDefaultCursor());
                    return;
                }
                //End CR6
                if (getViewMode() == CREATE_MODE)
                {
                    compareBsoID = ((BaseValueInfo) selectedNode.getObject().
                                    getObject()).getBsoID();
                }
                if (getViewMode() == UPDATE_MODE)
                {
                    compareBsoID = parentTechnics.getBsoID();
                }
                if (info.getBsoID().equals(compareBsoID))
                {
                    String s = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.RELATIONTECHNICS_CANTBE_THISTECHNICS, null);
                    String ss1 = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(), s, ss1,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    setCursor(Cursor.getDefaultCursor());
                    return;
                }
                relationTechJTextField.setText(getIdentity(info));
                //relatedTechnics = info;
                relatedTechnicsID = info.getBsoID();
                if (sd != null)
                {
                    sd.setVisible(false);
                }
            }
        }
    }


    /**�����������������˫���¼�*/
    private void myList_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        CappMultiList c = (CappMultiList) e.getSource();
        Object[] bvi = c.getSelectedObjects();
        
        actionPerformed(bvi);
        setCursor(Cursor.getDefaultCursor());
    }

    private String existProcessType = "";
    private CappSortingSelectedPanel workshopSortingSelectedPanel = null;
    private ProcedureUsagePartJPanel partlinkJPanel;
    
    //CCBegin SS3
    
    /**
     * ��ӡԤ����ť,����ѡ�е�tabҳ�Ĳ�ͬԤ����ͬ�Ŀ�������
     * @param e ActionEvent
     */
    void viewproJButton_actionPerformed(ActionEvent e) {
      parentJFrame.setCursor(Cursor.WAIT_CURSOR);
      int i = jTabbedPanel.getSelectedIndex();
      isview = true;
      //��鹤���ÿ���Ԥ��
      // if (!bsoname.equals("QMCheckProcedure")) {
      //û��ѡ�й�����Ϣtabҳ
      if (i != 0) {
        //����ģʽ
        if (getMode() == UPDATE_MODE) {
          //�ȱ���
          saveWhenUpdate();
          QMProcedureIfc proifc = getProcedure();
          startPreviewJFrame(i, proifc);
        }
        //����ģʽ
        else if (getMode() == CREATE_MODE) {
          //�ȱ���
          saveWhenCreate();
          QMProcedureIfc proifc = getProcedure();
          startPreviewJFrame(i, proifc);
        }
        //�鿴ģʽ
        else if (getMode() == VIEW_MODE) {
          QMProcedureIfc proifc = getProcedure();
          startPreviewJFrame(i, proifc);
        }
        //}
      }
      isview = false;
      parentJFrame.setCursor(Cursor.DEFAULT_CURSOR);
    }
    
    /**
     * ���ݲ�ͬ�����ͽ��й���Ԥ��
     * @param i int
     * @param proifc QMProcedureIfc
     */
    public void startPreviewJFrame(int i, QMProcedureIfc proifc) {
      String cardType = "";
      if (i == 1) {
        cardType = "��������ͼ";
      }
      if (i == 2) {
        cardType = "����FMEA";
      }
      if (i == 3) {
        cardType = "���Ƽƻ�";
      }
      LightweightFileTool.templatesSizeArray = new String[1];
      LightweightFileTool.templatesSizeArray[0] = "A4";
      LightweightFileTool.isPrintQMTechnics = true;
      LightweightFileTool.init(parentJFrame, parentTechnics, "");
      String[] stringArray = {
          cardType};
      try {
        LightweightFileTool.previewOneStep(proifc, stringArray);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

    }
    
    
    /**
     * ��ӡԤ����ť,����ѡ�е�tabҳ�Ĳ�ͬԤ����ͬ�Ŀ�������
     * @param e ActionEvent
     */
    void viewTechnicsJButton_actionPerformed(ActionEvent e) {
      parentJFrame.setCursor(Cursor.WAIT_CURSOR);
      int i = jTabbedPanel.getSelectedIndex();
      isview = true;
      QMProcedureIfc prifc = getProcedure();
      String bsoname = "";
      if (prifc != null) {
        bsoname = prifc.getBsoName();
      }
      //��鹤���ÿ���Ԥ��
      //if (!bsoname.equals("QMCheckProcedure")) {
      //û��ѡ�й�����Ϣtabҳ
      if (i != 0) {
        //����ģʽ
        if (getMode() == UPDATE_MODE) {
          //�ȱ���
          saveWhenUpdate();
          startPreviewJFrame(i);
        }
        //����ģʽ
        else if (getMode() == CREATE_MODE) {
          //�ȱ���
          saveWhenCreate();
          startPreviewJFrame(i);
        }
        //�鿴ģʽ
        else if (getMode() == VIEW_MODE) {
          startPreviewJFrame(i);
        }
        // }
      }
      isview = false;
      parentJFrame.setCursor(Cursor.DEFAULT_CURSOR);
    }
    
    
    /**
     * ���ݲ�ͬ�����ͽ��й���Ԥ��
     * @param i int
     * @param proifc QMProcedureIfc
     */
    public void startPreviewJFrame(int i) {
      String cardType = "";
      if (i == 1) {
        cardType = "��������ͼ";
      }
      if (i == 2) {
        cardType = "����FMEA";
      }
      if (i == 3) {
        cardType = "���Ƽƻ�";
      }
      LightweightFileTool.templatesSizeArray = new String[1];
      LightweightFileTool.templatesSizeArray[0] = "A4";
      LightweightFileTool.isPrintQMTechnics = false;
      LightweightFileTool.init(parentJFrame, parentTechnics, "");
      String[] stringArray = {
          cardType};
      try {
        LightweightFileTool.createLightweightFiles(stringArray);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }

    }
    
    
    //CCEnd SS3


    /**
     * ���ղ���ά��
     * <p>���ղ���ά��Ϊ�������ڣ�������������Ǹ��ݹ������಻ͬ����ʾ���ݲ�ͬ��</p>
     * @param e
     */
    void paraJButton_actionPerformed(ActionEvent e)
    {

        String processType = "";
        //��ù�������
        if (getViewMode() == CREATE_MODE)
        {
            processType = stepType.getCodeContent().trim();
        }
        else if (getViewMode() == UPDATE_MODE ||
                 getViewMode() == VIEW_MODE)
        {
            processType = getProcedure().getTechnicsType().getCodeContent().
                          trim();
        }
        if (!processType.equals(""))
        {
            //���������ģʽ��
            if (mode == CREATE_MODE || mode == UPDATE_MODE)
            {
                //���existProcessType��Ϊ�գ��ı��˹������࣬��ѹ���������չ�����ÿ�
                if (!existProcessType.equals("") &&
                    !existProcessType.equals(processType))
                {
                    getProcedure().setExtendAttributes(null);
                }

                d = new TParamJDialog(procedureInfo.getBsoName(), parentJFrame,
                                      "");
                d.setProcedure(getProcedure());
                
                //CCBegin by liunan 2011-08-25 �򲹶�P035
                //CR12 begin
                TechnicsContentJPanel.addFocusLis.setCompsFocusListener(d.getContentPane());
                //CR12 end
                //CCEnd by liunan 2011-08-25
                
                d.setEditMode();
                d.setVisible(true);
                //CCBegin by liunan 2011-08-25 �򲹶�P035
                //CR11 start
                if(d.getIsOk())
                {
                //CCEnd by liunan 2011-08-25
                //������չ����
                ExtendAttContainer c = d.getExtendAttributes();
//                System.out.println("ssss==="+c.getSecondClassify());
//                System.out.println("ddddddd==="+c.getSecondClassifyValue());
                if (c != null)
                {
                    if (d.check())
                    {
                        getProcedure().setExtendAttributes(c);
                    }
                    else
                    {
                        if (verbose)
                        {
                            System.out.println("��չ����¼�����");
                        }
                        return;
                    }
                }
                //CCBegin by liunan 2011-08-25 �򲹶�P035
              }
                //CCEnd by liunan 2011-08-25
                existProcessType = processType;
            }
            //�鿴ģʽ
            if (getViewMode() == VIEW_MODE)
            {
                d = new TParamJDialog(procedureInfo.getBsoName(), parentJFrame,
                                      "");
                d.setProcedure(getProcedure());
                d.setViewMode();
                d.setVisible(true);
            }
        }
    }


    /**
     * ִ���������������ձ����з��ֵ��豸�빤װ�Ĺ�ϵ����ع�װά����.
     * ���豸�빤װ�Ĺ������浽�豸���С��豸�б�͹�װ�б���������һ���б�����һ����
     * �ݣ��豸��װ��������豸����װ��ѡ���˶������ݣ�ѡ��������ʱ��������ʾ��Ҫ������ѡ��
     * ����豸�б��е��豸��һ������ϵͳ�����豸�빤װ�б��е����й�װ����������ϵ��
     * ����豸�б��е��豸�Ƕ�������Ȼ��װ�б�����һ������ϵͳ�ֱ�ÿ���豸��ù�װ����������ϵ��
     * @param e
     */
    void storageJButton_actionPerformed(ActionEvent e)
    {
        //�����ж�ѡ����豸����װ��������һ����ֻѡ��һ��ʵ��
        Vector equipLinks = equiplinkJPanel.getSelectedObjects();
        Vector toolLinks = toollinkJPanel.getSelectedObjects();

        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        if (equipLinks.size() > 0 && toolLinks.size() > 0)
        {
            if (equipLinks.size() == 1 || toolLinks.size() == 1)
            {
                //ϵͳ�����豸�빤װ�б��е����й�װ����������ϵ��
                f1.populateEquipments(equipLinks);
                f1.populateTools(toolLinks);
                f1.setVisible(true);
            }
            else
            {
                //�豸����װ�б���������һ���б���һ������
                String message = QMMessage.getLocalizedMessage(
                        RESOURCE, CappLMRB.EQUIPMENT_OR_TOOL_NOT_ONLYONE, null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              message,
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else
        {
            //"�豸�б�͹�װ�б���붼��������ݣ�����ִ������������������ݡ�"
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.EQUIPMENT_OR_TOOL_NOT_ONLYONE, null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     * ���������ı�����ַ����¼����¼�����
     * <p>���ѡ���˹������գ���ʱ��Ϣ����ά����</p>
     * <p>���ѡ���˹������գ�������װ���豸�����ϵȶ�Ϊ�����ã����ڹ����²��ܴ���������</p>
     * @param e CaretEvent
     */
    void relationTechJTextField_caretUpdate(CaretEvent e)
    {
        setRelatedEff();
    }


    /**
     * <p>���ѡ���˹������գ���ʱ��Ϣ����ά����</p>
     * <p>���ѡ���˹������գ�������װ���豸�����ϵȶ�Ϊ�����ã����ڹ����²��ܴ���������</p>
     */
    private void setRelatedEff()
    {
        String s = relationTechJTextField.getText().trim();
        if (s != null && !s.equals(""))
        {
            processHoursJPanel.setEnabled(false);
            //20080820
            //equiplinkJPanel.clear();
            //toollinkJPanel.clear();
            //materiallinkJPanel.clear();
            //doclinkJPanel.clear();
            //20080820 xucy
            if(equiplinkJPanel != null)
            {
            equiplinkJPanel.setMode("View");
            }
            if(toollinkJPanel != null)
            {
            toollinkJPanel.setMode("View");
            }
            if(materiallinkJPanel != null)
            {
            materiallinkJPanel.setMode("View");
            }
            //modify by wangh on 20080226 ��ԭ���Ĳ鿴ģʽ��Ϊ�ɱ༭ģʽ
            doclinkJPanel.setMode("Edit");
            //modify end
            if (partlinkJPanel != null)
            {
                partlinkJPanel.setMode("View");
            }
        }
        else
        {
            processHoursJPanel.setEnabled(true);
            //�޹��������ҹ������й���,��ʱ����ά����ʹ����Դ������岻��ά��
            if (c != null && c.size() != 0)
            {
                //20060728Ѧ���޸ģ��Ƿ��Զ�ά�����ڵ�Ĺ�ʱ���ɿ����õ�
                String hourUpdateflag = RemoteProperty.getProperty(
                        "updateMachineHour", "true");
                if (hourUpdateflag.equals("true"))
                {
                    //�����еĹ�ʱ�Ƿ������
                    boolean hourflag = false;
                    for (Iterator it = c.iterator(); it.hasNext(); )
                    {
                        QMProcedureInfo procedure = (QMProcedureInfo) it.next();
                        if (procedure.getStepHour() > 0)
                        {
                            hourflag = true;
                            break;
                        }
                    }
                    if (hourflag)
                    {
                        processHoursJPanel.setViewMode();
                    }
                    else
                    {
                        processHoursJPanel.setEditMode();
                        //�������ӽڵ㣬���ӽڵ�ʹ������Դ���������岻��ά��
                        //�������ӽڵ㣬���ӽڵ�ʹ������Դ���������岻��ά��

                    }
                }
                else
                {
                    processHoursJPanel.setEditMode();
                }
                //20060728Ѧ���޸ģ��Ƿ��Զ�ά�����ڵ����Դ���ɿ����õ�
                String resourceUpdateflag = RemoteProperty.getProperty(
                        "updateResourceLink", "true");                
                if (resourceUpdateflag.equals("true"))
                {
                    //�ӽڵ�ʹ����Դ�ļ���
                    Collection linkCollection = null;
                    //��־�ӽڵ��Ƿ�ʹ������Դ
                    boolean eqflag = false;
                    boolean toolflag = false;
                    boolean matflag = false;
                    boolean partflag = false;
                    for (Iterator i = c.iterator(); i.hasNext(); )
                    {
                        try
                        {
                            linkCollection = CappClientHelper.getUsageResources(((
                                    BaseValueInfo) i.next())
                                    .getBsoID());
                        }
                        catch (QMRemoteException ex)
                        {
                            ex.printStackTrace();
                            return;
                        }
                        if (linkCollection != null &&
                            linkCollection.size() != 0)
                        {
                            for (Iterator it = linkCollection.iterator();
                                               it.hasNext(); )
                            {
                                BinaryLinkIfc link = (BinaryLinkIfc) it.next();
                                if (link instanceof
                                    QMProcedureQMEquipmentLinkIfc)
                                {
                                    eqflag = true;
                                }
                                else
                                if (link instanceof QMProcedureQMToolLinkIfc)
                                {
                                    toolflag = true;
                                }
                                else
                                if (link instanceof
                                    QMProcedureQMMaterialLinkIfc)
                                {
                                    matflag = true;
                                }
                                else
                                if (link instanceof QMProcedureQMPartLinkIfc)
                                {
                                    partflag = true;
                                }
                                if (eqflag && toolflag && matflag && partflag)
                                {
                                    break;
                                }
                            }
                        }
                        if (eqflag && toolflag && matflag)
                        {
                            break;
                        }
                    }
                    //20080820 xucy
                    if(equiplinkJPanel != null)
                    {
                    if (eqflag)
                    {
                        equiplinkJPanel.setMode("View");
                    }
                    else
                    { 
                        equiplinkJPanel.setMode("Edit");
                    }
                    }
                    if(toollinkJPanel != null)
                    {
                    if (toolflag)
                    {
                        toollinkJPanel.setMode("View");
                    }
                    else
                    {
                        toollinkJPanel.setMode("Edit");
                    }
                    }
                   if(materiallinkJPanel != null)
                   {
                    if (matflag)
                    {
                        materiallinkJPanel.setMode("View");
                    }
                    else
                    {
                        materiallinkJPanel.setMode("Edit");
                    }
                   }
                    if (partlinkJPanel != null)
                    {
                        if (partflag)
                        {
                            partlinkJPanel.setMode("View");
                        }
                        else
                        {
                            partlinkJPanel.setMode("Edit");
                        }
                    }
                }
                else
                {
                    equiplinkJPanel.setMode("Edit");
                    toollinkJPanel.setMode("Edit");
                    materiallinkJPanel.setMode("Edit");
                    //20081205 �촺Ӣ�޸�   �޸�ԭ���еĹ���û�к�part�Ĺ�����
                    //����partlinkJPanel����Ϊ��
                    if(partlinkJPanel != null)
                    partlinkJPanel.setMode("Edit");
                    //add by wangh on 20080226 �����ĵ��ɱ༭.
                    doclinkJPanel.setMode("Edit");
                    //add end
                }
            }

            //�޹��������ҹ������޹�������ʱ��ά����ʹ����Դ��������ά��
            else
            {
                processHoursJPanel.setEditMode();
                //20080820 xucy
                if(equiplinkJPanel != null)
                {
                equiplinkJPanel.setMode("Edit");
                }
                if(toollinkJPanel != null)
                {
                toollinkJPanel.setMode("Edit");
                }
                if(materiallinkJPanel != null)
                {
                materiallinkJPanel.setMode("Edit");
                }
                doclinkJPanel.setMode("Edit");
                if (partlinkJPanel != null)
                {
                    partlinkJPanel.setMode("Edit");
                }
            }
        }
    }


    /**
     * �Ѹ�����ҵ�������ӵ���Ӧ�Ĺ����б���
     * @param info ������ҵ�������Դ��
     */
     //CCBegin by leixiao 2010-6-30 �򲹶�v4r3_p017_20100617 ����ʶCR8
    public void addObjectToTable(BaseValueInfo[] info)
    {
        if (mode != VIEW_MODE)
        {
            if (info[0] instanceof QMEquipmentInfo)
            {
            	Vector tools = null;//begin CR8
            	try
            	{//end CR8
            	if(equiplinkJPanel != null)
            	{
                for (int i = 0; i < info.length; i++)
                {
                    equiplinkJPanel.addEquipmentToTable((QMEquipmentInfo)
                            info[i]);
                     //begin CR8
                     CEquipment cequip = new CEquipment((QMEquipmentInfo)info[i]);
                     // �õ�����豸�����Ĺ�װ
                     tools = cequip.getTools();
                     if(tools != null && tools.size() != 0)
                     {
                         // ������װ
                         for(int j = 0;j < tools.size();j++)
                         {
                             // ��������Ǳ�Ҫ��
                             if(((Boolean)((Object[])tools.elementAt(j))[1]).booleanValue() == true)
                             {
                                 // �ѹ�װ�����б���
                                 if(toollinkJPanel != null)
                                     toollinkJPanel.addToolToTable((QMToolInfo)((Object[])tools.elementAt(j))[0]);
                             }

                         }
                     }
                        //end CR8
                }
            	}
            	 }//begin CR8
            catch (QMException ex)
            {
                ex.printStackTrace();
            }//end CR8
            }
            else if (info[0] instanceof QMToolInfo)
            {
            	Vector equips = null;//begin CR8
            	try
            	{//end CR8
            	if(toollinkJPanel != null)
            	{
                for (int i = 0; i < info.length; i++)
                {
                    toollinkJPanel.addToolToTable((QMToolInfo) info[i]);
                     //begin CR8
                        CTool ctool = new CTool((QMToolInfo)info[i]);
                        // �õ�����豸�����Ĺ�װ
                        equips = ctool.getEquips();
                        if(equips != null && equips.size() != 0)
                        {
                            // ������װ
                            for(int j = 0;j < equips.size();j++)
                            {
                                // ��������Ǳ�Ҫ��
                                if(((Boolean)((Object[])equips.elementAt(j))[1]).booleanValue() == true)
                                {
                                    // ���豸�����б���
                                	if(equiplinkJPanel != null)
                                		equiplinkJPanel.addEquipmentToTable((QMEquipmentInfo)((Object[])equips.elementAt(j))[0]);

                                }

                            }
                        }
                        //end CR8
                }
            	}
            	 }//begin CR8
            catch (QMException ex)
            {
                ex.printStackTrace();
            }//end CR8
            }
            else if (info[0] instanceof QMMaterialInfo)
            {
            	if(materiallinkJPanel != null)
            	{
                for (int i = 0; i < info.length; i++)
                {
                    materiallinkJPanel.addMaterialToTable((QMMaterialInfo)
                            info[i]);
                }
            	}
            }
            else if (info[0] instanceof QMTermInfo)
            {
                String termName = ((QMTermInfo) info[0]).getTermName();
                nameJTextField.setInsertText(termName);
                contentPanel.setInsertText(termName);
            }
            else if (info[0] instanceof DrawingInfo)
            {
                for (int i = 0; i < info.length; i++)
                {
                    drawingLinkPanel.addDrawingToTable(((DrawingInfo) info[i]));
                }
            }
            else if (info[0] instanceof QMTechnicsInfo)
            {

                if (mode == CREATE_MODE)
                {
                    Object[] obj =
                            {info[0]};
                    actionPerformed(obj);
                    return;
                }
                else if (mode == UPDATE_MODE)
                {
                    if (c == null || c.size() == 0)
                    {
                        Object[] obj =
                                {info[0]};
                        actionPerformed(obj);
                    }

                }

            }
            else if (info[0] instanceof QMPartInfo)
            {
                if (partlinkJPanel != null)
                {
                    for (int i = 0; i < info.length; i++)
                    {
                        partlinkJPanel.addPartToTable((QMPartInfo) info[i]);
                    }
                }
            }

        }

    }
  //CCEnd by leixiao 2010-6-30 �򲹶�v4r3_p017_20100617

    /**
     * ������������Ϣ����ù��տ������а汾�е�����С�汾
     * @param masterInfo ��������Ϣ
     * @return ���տ������а汾�е�����С�汾
     */
    private QMFawTechnicsInfo getLastedVersion(QMFawTechnicsMasterInfo
                                               masterInfo)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel:getLastedIterations() begin...");
        }
        QMFawTechnicsInfo technicsInfo = null;
        //���÷��񷽷�����ù��տ�������С�汾��������ͬ��֦��
//        ServiceRequestInfo info1 = new ServiceRequestInfo();
        Class[] paraClass =
                {MasteredIfc.class};
        Object[] objs =
                {masterInfo};
        Collection collection = null;
        try
        {
            collection = (Collection) TechnicsAction.useServiceMethod(
                    "VersionControlService", "allIterationsOf", paraClass, objs);
        }
        catch (QMRemoteException ex)
        {
            if (verbose)
            {
                ex.printStackTrace();
            }
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(parentJFrame,
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        Iterator iterator = collection.iterator();
        if (iterator.hasNext())
        {
            //��ù��տ�������С�汾
            technicsInfo = (QMFawTechnicsInfo) iterator.next();
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsStepJPanel:getLastedIterations() end...return " +
                    technicsInfo);
        }
        return technicsInfo;
    }


    /**
     * ɾ����������
     * @param e ActionEvent
     */
    void deleteTechJButton_actionPerformed(ActionEvent e)
    {
        relationTechJTextField.setText("");
        //relatedTechnics = null;
        relatedTechnicsID = null;
        processHoursJPanel.setEnabled(true);
        setRelatedEff();
        //20080820
//        equiplinkJPanel.setMode("Edit");
//        equiplinkJPanel.repaint();
//        toollinkJPanel.setMode("Edit");
//        toollinkJPanel.repaint();
        //materiallinkJPanel.setMode("Edit");
        //materiallinkJPanel.repaint();
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.repaint();

    }

//CCBegin SS5
    /**
     * ʵ��ʹ����ҵָ����鿴����
     * @param e ActionEvent
     */
    void viewProcedureJButton_actionPerformed(ActionEvent e) {
      if (relatedProcedure != null && technicsinfo != null) {
        TechnicsTreePanel techTreePanel = ( (TechnicsRegulationsMainJFrame)
                                           parentJFrame).getProcessTreePanel();
        StepTreeObject stepobj = new StepTreeObject( (QMProcedureInfo)
                                                    relatedProcedure);
        TechnicsTreeObject techobj = new TechnicsTreeObject(technicsinfo);
        CappTreeNode nNode = null;
        if (selectedNode.getObject() instanceof StepTreeObject) {
          nNode = selectedNode.getP().getP();
        }
        else {
          nNode = selectedNode.getP();
        }
//        nNode.get
        //�Ȳ鿴���սڵ�
        techTreePanel.addNodeNotExpand(nNode, techobj);
        ( (TechnicsRegulationsMainJFrame) parentJFrame).getProcessTreePanel().
            setNodeSelected(techobj);
        nNode = ( (TechnicsRegulationsMainJFrame) parentJFrame).
            getProcessTreePanel().getSelectedNode();
        //�ٲ鿴����ڵ�
        //techTreePanel.nodeExpaned(nNode);
        techTreePanel.addNodeNotExpand(nNode, stepobj);
        //techTreePanel.addNode(nNode, stepobj);
        ( (TechnicsRegulationsMainJFrame) parentJFrame).getProcessTreePanel().
            setNodeSelected(stepobj);
        ( (TechnicsRegulationsMainJFrame) parentJFrame).refresh();
      }

    }
    
    //CCEnd SS5
    /**
     *�˷��������������ά�������е�����
     */
    public void clear()
    {
        if (firstInFlag)
        {
            firstInFlag = false;
            return;
        }
        numJTextField.setText("");
        //add by wangh on 20070201
        descNumJTextField.setText("");
        descNumDisplayJLabel.setText("");

        numDisplayJLabel.setText("");
        this.relatedTechnicsID = null;
        //relatedTechnics = null;
        relationTechJTextField.setText("");
        relationTechDisJLabel.setText("");
        nameJTextField.setText("");
        nameDisplayJLabel.setText("");
        contentPanel.clearAll();
        drawingLinkPanel.clear();
        
        //CCBegin SS7
        this.sonTeamDateDisJLabel.setText("");
        this.sonTeamDateTextField.setText("");
        
        //CCEnd SS7
        
        
        //CCBegin SS5
        
        relationJTextField.setText("");
        //CCEnd SS5
        
        //���⣨4�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        //CCBegin SS8
//        stepTypeComboBox.setSelectedIndex(0);
//        processTypeComboBox.setSelectedIndex(0);
      
        if(createStepType.equals("�ɶ������ҵָ���鹤��")){
        	
        	stepTypeComboBox.setSelectedIndex(1);
            processTypeComboBox.setSelectedIndex(1);
        }else{
        	
        	stepTypeComboBox.setSelectedIndex(0);
            processTypeComboBox.setSelectedIndex(0);
        }
        //CCEnd SS8
        processHoursJPanel.clear();
        //20080820 xucy
        if(equiplinkJPanel != null)
        {
        	relationsJPanel.remove(equiplinkJPanel);
        	equiplinkJPanel = null;
        }
       
        if(toollinkJPanel != null)
        {
        	relationsJPanel.remove(toollinkJPanel);
        	toollinkJPanel = null;
        }
        if(materiallinkJPanel != null)
        {
        	relationsJPanel.remove(materiallinkJPanel);
        	materiallinkJPanel = null;
        }
        doclinkJPanel.clear();
        existProcessType = "";
        d = null;
        if (partlinkJPanel != null)
        {
            relationsJPanel.remove(partlinkJPanel);
            partlinkJPanel = null;
        }

        //����ʵ������������
        upJPanel.remove(contentPanel);
        //CCBegin by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
        contentPanel = new SpeCharaterTextPanel(parentJFrame,true);//CR7
        //CCEnd by leixiao 2010-5-4 �򲹶� v4r3_p014_20100415  
        initSpeCharaterTextPanel();
        contentPanel.speCharaterTextBean.setFont(new java.awt.Font("Dialog", 0,
                18));

        contentPanel.setMaximumSize(new Dimension(32767, 80));
        contentPanel.setMinimumSize(new Dimension(10, 10));
        contentPanel.setPreferredSize(new Dimension(100, 50));
        upJPanel.add(contentPanel,
                     new GridBagConstraints(1, 6, 3, 1, 1.0, 1.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(2, 8, 2, 7), 0, 0));
        upJPanel.repaint();
        paraJButton.setVisible(true);
        //20080811 �촺Ӣ�޸�
        //jTabbedPanel.setSelectedIndex(0);
    }


    /**
     * ����ģʽʱ���ô˷���.����ʵ��������ֵ����,�����ͼ����齨
     * �ı�part����,��̬����
     */
    public void refreshObject()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.refreshObject ");

        }
        String processType = stepType.getCodeContent().trim();

        if (!existProcessType.equals(processType))
        {
            if (getViewMode() == CREATE_MODE)
            { //����¼��Ĺ������࣬������ͬ�Ĺ���
                try
                {
                    QMProcedureInfo procedureinfo = CappServiceHelper.
                            instantiateQMProcedure(processType);
                    setProcedure(procedureinfo);
                }
                catch (ClassNotFoundException ex)
                {
                    if (verbose)
                    {
                        ex.printStackTrace();
                    }
                    String message = QMMessage.getLocalizedMessage(
                            RESOURCE, CappLMRB.SYSTEM_ERROR, null);
                    String title = QMMessage.getLocalizedMessage(
                            RESOURCE, CappLMRB.SAVE_STEP_FAILURE, null);
                    JOptionPane.showMessageDialog(getParentJFrame(),
                                                  message, title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }
            }
            //add by wangh on 20070208
            newExtendPanel(processType);
          //20080820
            newEquiplinkJPanel(processType);
            if (equiplinkJPanel != null)
            {
            	equiplinkJPanel.setMode("Edit");
            }
            newToollinkJPanel(processType);
            if (toollinkJPanel != null)
            {
            	toollinkJPanel.setMode("Edit");
            }
            newMateriallinkJPanel(processType);
            if (materiallinkJPanel != null)
            {
            	materiallinkJPanel.setMode("Edit");
            }
            relationsJPanel.add(doclinkJPanel, "�ĵ�");
            relationsJPanel.add(drawingLinkPanel, "��ͼ");
            newPartlinkJPanel(processType);
            if (partlinkJPanel != null)
            {
                partlinkJPanel.setMode("Edit");
            }
            getProcedure().setExtendAttributes(null);
            existProcessType = processType;
        }
        if (processType != null)
        {
            paraJButton.setVisible(true);

        }

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.refreshObject end");
        }
    }
  //CCBegin SS4
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
//CCEnd SS4

    /**
     * ����ʵ����part����
     * @param stepType String
     */
    private synchronized void newPartlinkJPanel(String stepType)
    {
        //��̬���ù���ʹ���㲿������
        String link = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.capp.view" + stepType);
        if (link == null || link.trim().equals("null") || link.equals(""))
        {
            if (partlinkJPanel != null)
            {
                relationsJPanel.remove(partlinkJPanel);
            }
            partlinkJPanel = null;
        }
        else
        {
            if (partlinkJPanel != null)
            {
                relationsJPanel.remove(partlinkJPanel);
            }
            //CCBegin SS4
            try {
				if(getUserFromCompany().equals("cd")){
					
					QMTechnicsIfc tech = null;
				    if (selectedNode.getObject().getObject() instanceof QMProcedureIfc) {
				      tech = (QMTechnicsIfc) selectedNode.getP().getObject().getObject();
				    }
				    else {
				      tech = (QMTechnicsIfc) selectedNode.getObject().getObject();
				    }
				    partlinkJPanel = new ProcedureUsagePartJPanel(stepType, tech);
				    partlinkJPanel.setLinkedTechnics(tech);
					
				}else{
					//CCBegin SS6
				   partlinkJPanel = new ProcedureUsagePartJPanel(stepType,true);
				   //CCEnd SS6
				
				}
			} catch (QMException e) {
				e.printStackTrace();
			}
			//CCEnd SS4
            
            //CCBegin SS1
            partlinkJPanel.setIsProcedure(true);
            //CCEnd SS1
            relationsJPanel.add(partlinkJPanel, "�㲿��");
        }
    }
    
    /**
     * ����ʵ����part����
     * @param stepType String
     */
    private  void newDoclinkJPanel(String stepType)
    {
        
        relationsJPanel.add(doclinkJPanel, "�ĵ�");
        
    }
    
    /**
     * ����ʵ����part����
     * @param stepType String
     * 20080820 xucy
     */
    private  void newEquiplinkJPanel(String stepType)
    {
       
            if (equiplinkJPanel != null)
            {
                relationsJPanel.remove(equiplinkJPanel);
            }
            //CCBegin SS6
            equiplinkJPanel = new ProcedureUsageEquipmentJPanel(stepType,true);
            //CCEnd SS6
            //CCBegin SS1
            equiplinkJPanel.setIsProcedure(true);
            //CCEnd SS1
            //���⣨7��20090108  �촺Ӣ�޸�    ��ʼ���豸��������ʱ��Ҳ��ʼ����װ�������   ��Ȼ����ֿ�ָ���쳣
            //CCBegin SS6
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType,true);
            //CCEnd SS6
            //�Ӵ˼�����ԭ�򣺵��豸�����������豸ʱ����װ�������Ҫ�������豸��Ҫ�����Ĺ�װ
            equiplinkJPanel.addListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    toollinkJPanel.addRelationTools(e);
                }
            });
            
            equiplinkJPanel.setToolPanel(toollinkJPanel);
            //relationsJPanel.add(equiplinkJPanel, "�豸");
        
        relationsJPanel.add(equiplinkJPanel, "�豸");
    }
    /**
     * ����ʵ����part����
     * @param stepType String
     * 20080820 xucy
     */
    private  void newToollinkJPanel(String stepType)
    {
       
            if (toollinkJPanel != null)
            {
                relationsJPanel.remove(toollinkJPanel);
            }
            //CCBegin SS6
            toollinkJPanel = new ProcedureUsageToolJPanel(stepType,true);
            //CCEnd SS6
            //CCBegin SS1
            toollinkJPanel.setIsProcedure(true);
            //CCEnd SS1
            //�Ӵ˼�����ԭ�򣺵��豸�����������豸ʱ����װ�������Ҫ�������豸��Ҫ�����Ĺ�װ
            toollinkJPanel.addListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    equiplinkJPanel.addRelationEquipments(e);
                }
            });
            toollinkJPanel.setEquipmentPanel(equiplinkJPanel);
            
        relationsJPanel.add(toollinkJPanel, "��װ");
    }
    
    /**
     * ����ʵ����part����
     * @param stepType String
     */
    private  void newMateriallinkJPanel(String stepType)
    {
        
            if (materiallinkJPanel != null)
            {
                relationsJPanel.remove(materiallinkJPanel);
            }
            //CCBegin SS6
            materiallinkJPanel = new ProcedureUsageMaterialJPanel(stepType,true);
            //CCEnd SS6
            
            //CCBegin SS1
            materiallinkJPanel.setIsProcedure(true);
            //CCEnd SS1
            //CR5 begin
            relationsJPanel.add(materiallinkJPanel, "����");
            //CR5 end        
    }
    
    /**
     * ɾ��ԭ���Ĳ��ţ�����ʵ������ԭ���ǹ��տ������п��ܸ��£�
     */
    private void changeWorkShopSortingSelectedPanel()
    {

        if (workshopSortingSelectedPanel != null)
        {
            upJPanel.remove(workshopSortingSelectedPanel);
            workshopSortingSelectedPanel = null;
        }
        //CCBegin by leixiao 2010-4-1 ������ȡ���ղ���
//        if (parentTechnics.getWorkShop() instanceof CodingClassificationIfc)
//        {
//            workshopSortingSelectedPanel = new CappSortingSelectedPanel(
//                    (CodingClassificationIfc) parentTechnics.getWorkShop());
//            String title4 = QMMessage.getLocalizedMessage(RESOURCE,
//                    CappLMRB.WORKSHOP, null);
//            workshopSortingSelectedPanel.setDialogTitle(title4);
//            workshopSortingSelectedPanel.setButtonSize(89, 23);
//            upJPanel.add(workshopSortingSelectedPanel,
//                         new GridBagConstraints(3, 4, 1, 1, 1.0, 0.0
//                                                , GridBagConstraints.WEST,
//                                                GridBagConstraints.HORIZONTAL,
//                                                new Insets(2, 0, 2, 8), 0, 0));
//            workshopSortingSelectedPanel.setSelectBMnemonic('W');
//            workshopDisJLabel.setVisible(false);
//        }
//        else
//        {
            workshopDisJLabel.setVisible(true);
            String workshop="";
            if (parentTechnics.getWorkShop() instanceof CodingClassificationIfc){
            	workshop=((CodingClassificationIfc) parentTechnics.getWorkShop()).getCodeSort();

            }
            else{
            	workshop=((CodingIfc) parentTechnics.getWorkShop()).getCodeContent();	
                
            }
            	
            workshopDisJLabel.setText(workshop);
            
//        }
          //CCEnd by leixiao 2010-4-1 ������ȡ���ղ���
    }


    public JPanel getPartLinkJPanel()
    {
        return partlinkJPanel;
    }

    public JPanel getEquipLinkJPanel()
    {
        return equiplinkJPanel;
    }
    /**
     * ��ʼ����������
     */
    public void initStepTypeTable()
    {
        String name = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.capp.util.CappLMRB", "86", null);
        String parent = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.capp.util.CappLMRB", "112", null);
        Class[] paraclass =
                {String.class, String.class};
        Object[] paraobj =
                {name, parent};
        Collection c = null;
        try
        {
            c = (Collection) useServiceMethod("CodingManageService",
                                              "getCoding",
                                              paraclass, paraobj);
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
        }

        if (c != null && c.size() != 0)
        {
            stepTypetable = new Hashtable();
            for (Iterator i = c.iterator(); i.hasNext(); )
            {
                CodingIfc coding = (CodingIfc) i.next();
                stepTypetable.put(coding.getCodeContent(), coding);
            }
        }
    }

    public int getMode()
    {
        return mode;
    }


    /**
     * ���ù�������
     * @param codeContent String
     */
    public void setStepType(String codeContent)
    {
        stepType = (CodingIfc) stepTypetable.get(codeContent);
    }


    /**
     * Ϊ���������������������
     */
    private void initSpeCharaterTextPanel()
    {
        contentPanel.setDrawInfo(CappClientHelper.getSpechar());
        String path = RemoteProperty.getProperty("spechar.image.path");
        if (path == null)
        {
            contentPanel.setFilePath("/spechar/");
        }
        else
        {
            contentPanel.setFilePath(path.trim());
        }
    }

    
    /**
     * ����ѡ��ĵ�λ
     * @param comboBox �б��
     * @param coding ������
     */
    public void setComboBox(JComboBox box, CodingIfc coding)
    {
        int j = box.getItemCount();
        for (int i = 0; i < j; i++)
        {
            BaseValueIfc temp = (BaseValueIfc) box.getItemAt(i);
            if (coding != null && coding.getBsoID().equals(temp.getBsoID()))
            {
                box.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     *
     * <p>Title:�����߳� </p>
     * <p>Description: ����������߳�</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: һ������</p>
     * @author not Ѧ��
     * @version 1.0
     */
    class WorkThread extends Thread
    {
        public void run()
        {
            save();
        }
    }
    
    //CCBegin SS5
    public void setRelationJTextField() {
        if (technicsinfo != null && relatedProcedure != null) {
          String techString = getIdentity1(technicsinfo);
          String displayString = relatedProcedure.getDescStepNumber() +
              relatedProcedure.getStepName();
          relationJTextField.setText(techString + "--" + displayString);
        }
      }
    
    void searchTechJButton1_actionPerformed(ActionEvent e) {
        try {
          sd = new TechnicsSearchJDialog( (TechnicsRegulationsMainJFrame)
                                         parentJFrame);
          sd.setModal(false);
          sd.setSingleSelectMode();
          sd.addQueryListener(new CappQueryListener() {
            public void queryEvent(CappQueryEvent e) {
              qmQuery_queryEvent1(e);
            }
          });
          //���TechnicsSearchJDialog�е�mutilist���е�˫������
          sd.addMultiListActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              myList_actionPerformed1(e);
            }
          }
          );
          sd.setVisible(true);
        }
        catch (QMRemoteException ex) {
          String title = QMMessage.getLocalizedMessage(RESOURCE,
              "information", null);
          JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(),
                                        title,
                                        JOptionPane.INFORMATION_MESSAGE);

        }

      }

      void deleteTechJButton1_actionPerformed(ActionEvent e) {
        relationJTextField.setText("");
        relatedProcedure = null;


      }
    
    //CCEnd SS5


    void descNumJTextField_actionPerformed(ActionEvent e)
    {

    }


    /**
     * <p>Title:�����߳� </p>
     * <p>Description: ���ڵ��ü�ͼ�����Ż����¹��գ�����ٶ�</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: һ������</p>
     * @author Ѧ�� 2008 04 28 ���
     * @version 1.0
     */
//    class DrawThread extends Thread
//    {
//        public void run()
//        {
//            drawingLinkPanel.setProcedure(getProcedure());
//        }
//    }
    
    //CCBegin SS5
    /**
     * �������ռ����¼�����
     * @param e ���������¼�
     */
    public void qmQuery_queryEvent1(CappQueryEvent e) {
      if (verbose) {
        System.out.println(
            "cappclients.capp.view.TechnicsStepJPanel:qmQuery_queryEvent(e) begin...");
      }
      if (e.getType().equals(CappQueryEvent.COMMAND)) {
        if (e.getCommand().equals(CappQuery.OkCMD)) {
          setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
          CappQuery c = (CappQuery) e.getSource();
          BaseValueIfc[] bvi = c.getSelectedDetails();
          actionPerformed1(bvi);
          setCursor(Cursor.getDefaultCursor());
        }
        if (e.getCommand().equals(CappQuery.QuitCMD)) {
          sd.setVisible(false);
        }
      }

      if (verbose) {
        System.out.println("cappclients.capp.view.TechnicsStepJPanel:qmQuery_queryEvent(e) end...return is void");
      }
    }
    
    
    /**
     *�������еĹ��չ�̼���������ա��ı����У��˷����ṩ��TechnicsSearchJDialog�����������¼�
     */
    private void actionPerformed1(Object[] bvi) {
      if (bvi != null) {
        //�Ѵӽ����ѡ�е�ҵ�������롰�������ա��ı���
        for (int i = 0; i < bvi.length; i++) {
          QMFawTechnicsInfo info = (QMFawTechnicsInfo) bvi[i];
          String compareBsoID = "";
          if (getViewMode() == CREATE_MODE) {
            compareBsoID = ( (BaseValueInfo) selectedNode.getObject().
                            getObject()).getBsoID();
          }
          if (getViewMode() == UPDATE_MODE) {
            compareBsoID = parentTechnics.getBsoID();
          }
          if (info.getBsoID().equals(compareBsoID)) {
            JOptionPane.showMessageDialog(getParentJFrame(), "���ܹ����������еĹ���", "��ʾ",
                                          JOptionPane.
                                          INFORMATION_MESSAGE);
            setCursor(Cursor.getDefaultCursor());
            return;
          }
//          relationJTextField.setText(getIdentity(info));
//          relatedTechnics1 = info;
          if (sd != null) {
            sd.setVisible(false);
          }
          //tangshutao 2008.01.21
          SelectProcedureJDialog dialog = new SelectProcedureJDialog(this.
              parentJFrame, "", true, info, this);
          dialog.initTable();
          dialog.setVisible(true);
          //end
        }
      }
    }
    
    
    /**
     * ����״̬�£���õ�ǰ�������ڵĸ����
     * added by dikefeng 20090706
     * @param bvi Object[]
     */
    public CappTreeNode getSelectedTreeNode()
    {
      return this.selectedNode;
    }
    
    /**
     * ����״̬�£���õ�ǰ�������ڵĸ����
     * added by dikefeng 20090706
     * @param bvi Object[]
     */
    public QMTechnicsIfc getSelectedParentTechnics()
    {
      return this.parentTechnics;
    }
    
    /**�����������������˫���¼�*/
    private void myList_actionPerformed1(ActionEvent e) {
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      CappMultiList c = (CappMultiList) e.getSource();
      Object[] bvi = c.getSelectedObjects();
      actionPerformed1(bvi);
      setCursor(Cursor.getDefaultCursor());
    }
    
    
    //CCend SS5
    

}
