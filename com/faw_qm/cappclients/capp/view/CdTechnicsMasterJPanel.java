/** ���ɳ���TechnicsMasterJPanel.java	1.1  2003/8/6
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��     
 * 
 *CR1 2009/04/28 ������      ԭ�򣺹��չ�̵ķʿͻ�����û�й��յĴ����ߡ���ǰ״̬�ȵ���Ϣ
 *                           ������������ԣ��������ߡ�������ʱ�䡱���޸��ߡ����޸�ʱ�䡱���汾����
 * CR2 2009/04/29 ��־��    ԭ�򣺹����ͼ���޸ģ���ͼ�ֶηֿ�Ϊ����
 *                         ����������ͼ�ֶηֿ��洢                            
 * CR3 2009/05/05  ������     ԭ�򣺴��������½��治���֡������ߡ�������ʱ�䡱������
 *                           �������ڴ������½����������Բ��ɼ�������������  
 * CR4 2009/05/19  �촺Ӣ   ԭ�򣺴�����������Ϣ���������ݿ������й��ձ�ţ�����������ڵ㣬���ֱ�Ų�Ψһ����ʾ��Ϣ��
 *                                �ر���ʾ��֮�󣬽��������رա�  
 *                          ��������catch��ͷ���رս�����     
 * CR5 2009/05/21 �촺Ӣ    ԭ�����½���������Ϣ������ȡ����ť�����ź���������ûȡ��
 *                          ����������clear()����ִ���½������ȡ������   
 * CR6 2009/05/31 �촺Ӣ   �μ�DefectID=2169  
 * CR7 2009/07/01 ������   �μ�DefactID=2498   
 * SS1 �޸Ĳ����������û�У��������ʾ""����������쳣�����޷�������ʾ�� liunan 2012-9-11
 * SS2 ���ż�飬�����ǰѡ�и�������ҳ���㱣�棬���޷��жϳ������ǿա� liunan 2012-9-13
 * SS3 �����乤���Զ���� zhaoqiuying 2013-1-23
 * SS4 ��������Դ�嵥һ���� zhaoqiuying 2013-01-23
 * SS5 ���ӱ����� �����Ϊ��ģʽ����Ķ��� liuyang  2013-03-15
 * SS6 ���ݲ�ͬ��˾ѡ��ͬ��ʵʩTS16949������� liuyang 2013-3-22
 * SS7 �����乤�����й���ʱ�����Ų����޸� liuyang 2013-2-22
 * SS8 �����乫˾��ʾ��עΪPFEMA��� liuyang 2013-3-27
 * SS9 �����乫˾��֯������չ������´������� pant 2013-4-17
 * SS10 ����ļ��������ϴ�������֧ jiahx 2013-11-05
 * SS11 ��ݹ����Զ����  ���� 2014-1-9
 * SS12 �������"�ܳ��ͺš������ܳ����ơ���������������  ���� 2014-2-17
 * SS13 ������Ĭ��ʹ���Լ����������ڡ� liunan 2014-5-9
 * SS14 ��������²���ʱ������ѡ���ӷ��ࡣ liunan 2014-5-26
 * SS15 �޸���ݹ�����������Ĭ��ֵ,��ʹ���ѡ pante 2014-05-16
 * SS16 �޸���ݹ���ѡ���Ʒƽ̨�������� pante 2014-06-19
 * SS17 ���û�Ҫ���Ϊ�ܳ��ͺ��ܳ����ƺ����� pante 2014-06-23
 * SS18 �ڲ鿴��������Ϣ��������������ʱ,�½������в�Ʒƽ̨����ı��򼰰�ť������ pante 2014-07-31
 * SS19 ��ݹ��ղ�Ʒ״̬����ʾ������� pante 2014-10-09
 * SS20 ��ݹ��������Ҫ���ʱ���������в����ƺž��Զ���ӵ����������Ϣ�� pante 2014-10-23
 * SS21 ���ó���Ĭ���������� guoxiaoliang 2014-08-21
 * SS22 �������ӹ�װ��ϸ���豸�嵥��ģ���嵥�� guoxiaoliang 2014-08-22
 * SS23 ����TS16949�����ݱ�ű仯 ���� 2014-10-20
 * SS24 �������װ�乤�գ�Ҫ�������̣������˿ͻ���ǩ���� liunan 2014-12-10
 * SS25 ���ع�������Ϣ��Ʒ״̬��ȥ��������״̬ ���� 2014-12-22
 * SS26 ���������ΪʱҲĬ��ʹ���Լ����������ڡ� liunan 2015-2-25
 * SS27 ����Ĭ�ϲ��� ���� 2015-3-26
 * SS28 td12008 �����ɶ����������չ��ff���޷�ѡ���鹤�����ࣻ�����û�ѡ�����ó��ͺͼ�鹤�������Զ����ɹ��ձ�ţ� guoxiaoliang 2016-11-2
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.TextEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.capp.model.PDrawingInfo;
import com.faw_qm.capp.model.PartUsageQMTechnicsLinkInfo;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsQMDocumentLinkInfo;
import com.faw_qm.capp.model.QMTechnicsQMMaterialLinkInfo;
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.beans.drawingpanel.DrawingPanel;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.resource.view.ResourcePanel;
import com.faw_qm.cappclients.util.CappCharTextField;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.clients.beans.lifecycle.LifeCycleInfo;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.folder.model.SubFolderInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
//CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3��
import com.faw_qm.ration.client.view.AddPartByRouteListJDialog;
import com.faw_qm.ration.client.view.RationListPartLinkPanel;
import com.faw_qm.ration.exception.RationException;  
import com.faw_qm.ration.util.RationHelper;
//CCEnd by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3��
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMTermInfo;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
//CCBegin SS27
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.codemanage.model.CodingClassificationInfo;
//CCEnd SS27

/**
 * <p>Title: ���տ�ά�����</p>
 * <p>Description: ���տ��Ĵ��������¡��鿴�����Ϊ�������ڱ����ά����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 * ��1�� 20060814Ѧ���޸ģ��޸ķ���cancelWhenCreate���������ĵ�����������Ϲ������
 * (2)20070201Ѧ���޸ģ����ӻ������ԣ���Ʒ״̬������TS16949����
 * ���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
 * Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
 * ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
 */
 
public class CdTechnicsMasterJPanel extends TechnicsMasterJPanel 
{
    /**���水ť*/
    private JButton saveJButton = new JButton();


    /**ȡ����ť*/
    private JButton cancelJButton = new JButton();


    /**�˳���ť*/
    private JButton quitJButton = new JButton();
//CCBegin SS11
    //��¼��Ҫ�����ţ�Ϊ����װ�����Զ����
    public JTextField hideMainPart = new JTextField();
//CCEnd SS11
//CCBegin SS12
    //��¼��Ҫ���bsoID,Ϊ�����ѡ���Ʒƽ̨
    public JTextField hideMainPartBsoID = new JTextField();
//CCEnd SS12   
    private JPanel buttonJPanel = new JPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel masterJPanel = new JPanel();
    private JLabel numberJLabel = new JLabel();
    private JLabel nameJLabel = new JLabel();
    private JLabel typeJLabel = new JLabel();
//  CCBeginby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����
    public llTextField numberJTextField;
//  CCEndby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����  
//  CCBeginby leixiao 2010-4-20 ԭ��������������ʱ�����������Զ�����
    public CappCharTextField nameJTextField;
//  CCEndby leixiao 2010-4-20 ԭ��������������ʱ�����������Զ�����
    private JLabel remarkJLabel = new JLabel();
    private CappCharTextField remarkJTextField;
    private FolderPanel folderPanel = new FolderPanel();

    private JLabel numberDisplayJLabel = new JLabel();
    private JLabel nameDisplayJLabel = new JLabel();
    private JLabel typeDisplayJLabel = new JLabel();
    private JLabel remarkDisplayJLabel = new JLabel();
    //CCBegin SS12
    public JTextField separableNumber = new JTextField();
    private JLabel separableNumberJLabel = new JLabel();
    private JLabel separableNumberDisplayJLabel = new JLabel();
    public JTextField separableName = new JTextField();
    private JLabel separableNameJLabel = new JLabel();
    private JLabel separableNameDisplayJLabel = new JLabel();
    public JTextField separableCount = new JTextField();
    private JLabel separableCountJLabel = new JLabel();
    private JLabel separableCountDisplayJLabel = new JLabel();
    private JButton selectPartJButton = new JButton();
    private JPanel separableJPanel = new JPanel();
    //CCEnd SS12
    
	// Begin CR1
	private JLabel creatorJLabel = new JLabel();
	private JLabel creatorTextField = new JLabel();
	private JLabel creatTimeJLabel = new JLabel();
	private JLabel creatTimeTextField = new JLabel();
	private JLabel moderfilerJLabel = new JLabel();
	private JLabel moderfilerTextField = new JLabel();
	private JLabel modifyTimeJLabel = new JLabel();
	private JLabel modifyTimeTextField = new JLabel();
	private JLabel iterationJLabel = new JLabel();
	private JLabel iterationTextField = new JLabel();
    // End CR1
	
	//Begin CR7
	private JLabel workStateJLabel = new JLabel();
	private JLabel workStateTextField = new JLabel();
    //End CR7
	
    /**�����ڽ��沼�ַ��㣬û��ʵ������*/
    // private JPanel extraJPanel = new JPanel();
    /**������ʾģʽ������ģʽ�����*/
    public final static int UPDATE_MODE = 0;


    /**������ʾģʽ������ģʽ�����*/
    public final static int CREATE_MODE = 1;


    /**������ʾģʽ���鿴ģʽ�����*/
    public final static int VIEW_MODE = 2;


    /**������ʾģʽ�����Ϊģʽ�����*/
    public final static int SAVEAS_MODE = 3;


    /**����ģʽ--�鿴*/
    private int mode = VIEW_MODE;


    /**���տ�ֵ����*/
    private QMFawTechnicsInfo technicsInfo;


    /**������*/
    private JFrame parentJFrame;


    /**��ǰѡ��Ĺ������ڵ�*/
    private CappTreeNode selectedNode;


    private JTabbedPane relationsJTabbedPane = new JTabbedPane();


    /**����ʹ���ĵ�������ά�����*/
    private TechUsageDocLinkJPanel doclinkJPanel = new
            TechUsageDocLinkJPanel();


    /**����ʹ�ò��Ϲ�����ά�����*/
    private TechUsageMaterialLinkJPanel materialLinkJPanel;


    /**�㲿��ʹ�ù��տ�������ά�����*/
    private PartUsageTechLinkJPanel partLinkJPanel;
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel extendJPanel = new JPanel();
    private CappExAttrPanel cappExAttrPanel;
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private LifeCycleInfo lifeCycleInfo = new LifeCycleInfo();

    /**���棺ִ�����Ϊ֮ǰ��Դ���տ�*/
    private QMTechnicsInfo existTechnicsInfo;


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel jpanel = new JPanel();


    /**����Ƿ�����˳�*/
    private boolean isSave = true;
    private JLabel workShopJLabel = new JLabel();
    //CCBegin SS11
    public CappSortingSelectedPanel workshopSortingSelectedPanel = null;
    //CCEnd SS11
    private JLabel workShopDisplayjLabel = new JLabel();

    private String existTechnicsType = "";


    /**��¼�Ƿ��һ�ν���˽���*/
    private boolean firstInFlag = true;


    /**�����չ����bean;��:�������� ֵ:��չ����bean*/
    private Hashtable extendTable = new Hashtable();
    private Hashtable materialLinkTable = new Hashtable();
    private Hashtable partLinkTable = new Hashtable();
    public CodingIfc technicsType;
    private DrawingPanel drawingpanel;
    private JLabel flowDrawingDisp;
//    private CappWrapData data;
    /**
     * ��Ʒ״̬ѡ���
     * 20070201Ѧ�����
     */
    private CappSortingSelectedPanel productStateSortingSelectedPanel = null;
    private JLabel productStateJLabel;
    private JLabel productStateDisplJLabel;
    
    private JLabel checkTechTypeDisplJLabel;

    /**
     * �Ƿ�ʵʩTS16949����
     */
    private static boolean ts16949 = (RemoteProperty.getProperty(
            "TS16949", "true")).equals("true");
    //CCBegin SS11
    public MasterTS16949Panel masterTS16949Panel;
    //CCEnd SS11
    //CCBegin SS6
    private CONSMasterTS16949Panel consmasterTS16949Panel;
    //CCEnd SS6
    //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
    private JComboBox productStateComboBox = new JComboBox();
    
    private JLabel checkTechTypeLabel=new JLabel("��鹤������");
    private JComboBox checkTechTypeComboBox = new JComboBox();
    
    
//  CCBeginby leixiao 2009-3-31 ԭ�򣺽����������,��Ű����ͳ�
    public static final Map typeMap = new HashMap(4);
    static {
    	typeMap.put("�����ѹ����", "YB-");
    	typeMap.put("����ѹ����", "YH-");
    	typeMap.put("��ʻ�Һ�װ����", "HZ-");
    	typeMap.put("��ʻ��Ϳװ����", "TJ-");
    	typeMap.put("����װ�乤��", "NS-");
    	typeMap.put("����װ�乤��", "ZC-");
    	typeMap.put("����װ�乤��", "CJ-");
    	typeMap.put("����Ϳװ����", "TC-");
    	typeMap.put("�꺸����", "HK-");
    	//CCBegin by liunan 2010-11-17 ��������ӡ����ӹ��ա��͡����⹤�ա� Ԥ��λ�á�
    	typeMap.put("���ӹ���", "");
    	typeMap.put("���⹤��", "");
    	//CCEnd by liunan 2010-11-17
      }
//  CCEndby leixiao 2009-3-31 ԭ�򣺽����������·��

//CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
      private UpFilePanel upFilePanel;
//CCEnd by liunan 2009-10-12
      
      //CCBegin SS10
      static boolean fileVaultUsed = (RemoteProperty.getProperty(
              "registryFileVaultStoreMode", "true")).equals("true");
      //CCEnd SS10
    /**
     * ���캯��
     * @param parentFrame ���ñ���ĸ�����
     * @param parentnode  ���ڵ�
     */
    public CdTechnicsMasterJPanel(JFrame parentFrame)
    {
    	super(parentFrame);
    	
        try
        {
            parentJFrame = parentFrame;

            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
  
    
//CCBegin SS11
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
//CCEnd SS11
    
    
    //CCBegin SS28
    /**
     * ������������ձ����ˮ��
     */
    public String getTechnicsAutoNum() throws QMException 
    {
    	 Class[] paraclass ={String.class,String.class,boolean.class};
         Object[] paraobj ={"cdZLJCListTechnics","cdZLJCList",false};
         String returnStr = String.valueOf( CappClientHelper.useServiceMethod(
                 "StandardCappService", "getNextSortNumber", paraclass, paraobj));
         return returnStr;
	}
	//CCEnd SS28
    

    /**
     * �����ʼ��
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        initResources();
        setLayout(gridBagLayout4);
        String technicsNumdisp = QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNumber", null);
//      CCBegin by leixiao 2009-6-18 ԭ�򣺽��ϵͳ���� ,��ų���20->50
        numberJTextField = new llTextField(parentJFrame, technicsNumdisp, 50, false);
//      CCEnd by leixiao 2009-6-18 ԭ�򣺽��ϵͳ���� ,��ų���20->50
        String technicsNameNamedisp = QMMessage.getLocalizedMessage(RESOURCE,
                "technicsName", null);
        nameJTextField = new CappCharTextField(parentJFrame,
                                               technicsNameNamedisp, 40, false);
        String remarkdisp = QMMessage.getLocalizedMessage(RESOURCE,
                "remarkJLabel", null);
        //CCBegin by liuzhicheng 2010-02-25 ԭ�򣺽��Ҫ������ע���볤�ȡ�
        remarkJTextField = new CappCharTextField(parentJFrame, remarkdisp, 100, true);
        //CCEnd by liuzhicheng 2010-02-25��
        productStateJLabel=new JLabel("��Ʒ״̬");
        productStateDisplJLabel=new JLabel();
        
        checkTechTypeDisplJLabel=new JLabel();
        
        String title2 = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.WORKSHOP, null);
         flowDrawingDisp = new JLabel(getMessage("flowdrawing"));
         //���ţ���������ѡ������ֻ��ѡ�񳧺ͷֳ���
         //CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
         //CCBegin SS11
         hideMainPart.setText("");//��ʼ��ʱ���������
         if(getUserFromCompany().equals("zczx")){
//             workshopSortingSelectedPanel = new CappSortingSelectedPanel(
//                     title2, "QMTechnics", "workShop",this); 
         }else{
             workshopSortingSelectedPanel = new CappSortingSelectedPanel(
                     title2, "QMTechnics", "workShop");
         }
         //CCEnd SS11

         productStateSortingSelectedPanel=new CappSortingSelectedPanel("��Ʒ״̬","QMTechnics","productState");
         productStateSortingSelectedPanel.setDialogTitle("��Ʒ״̬");
         //CCEnd by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
           
        // Begin CR1
 		creatorJLabel = new JLabel("������");
 		creatTimeJLabel = new JLabel("����ʱ��");
 		moderfilerJLabel = new JLabel("�޸���");
 		modifyTimeJLabel = new JLabel("�޸�ʱ��");
 		iterationJLabel = new JLabel("�汾");
        //End CR1
 
 		workStateJLabel = new JLabel("����״̬");//CR7
         
        //��������ѡ
//        workshopSortingSelectedPanel.setIsSelectCC(true);
//        workshopSortingSelectedPanel.setButtonSize(89, 23);
//        workshopSortingSelectedPanel.setDialogTitle(title2);
        localize();
        folderPanel.setIsPersonalFolder(true);
        folderPanel.setIsPublicFolders(false);
        
        
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(new
                Dimension(89, 23));
        lifeCycleInfo.getProjectPanel().setMnemonicAndText('R',
                "R");
        //CCBegin SS6
        Boolean yy = false; 	
      	try {
  	    	Class[] paraClass = {};
  	        Object[] objs = {};
  	        String className = "com.faw_qm.doc.util.DocServiceHelper";
  	        String methodName = "isBSXGroup";
  	        StaticMethodRequestInfo in = new StaticMethodRequestInfo();
  	        in.setClassName(className);
  	        in.setMethodName(methodName);
  	        in.setParaClasses(paraClass);
  	        in.setParaValues(objs);
  	        RequestServer server = null;
  	        server = RequestServerFactory.getRequestServer();
  		
  			yy = (Boolean) server.request(in);
  		} catch (QMRemoteException e) {
  			e.printStackTrace();
  		}
        if(ts16949){

        	 if(yy){
       		  consmasterTS16949Panel=new CONSMasterTS16949Panel();
       		  System.out.println("ts16949====="+ts16949);
       	     }else{
                 masterTS16949Panel=new MasterTS16949Panel();
                 System.out.println("yy====="+yy);
       	     }
        	 
        }
        //CCEnd SS6
        //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
        upFilePanel = new UpFilePanel(this.parentJFrame);
        //CCEnd by liunan 2009-10-12
        saveJButton.setMaximumSize(new Dimension(75, 23));
        saveJButton.setMinimumSize(new Dimension(75, 23));
        saveJButton.setPreferredSize(new Dimension(75, 23));
        saveJButton.setVerifyInputWhenFocusTarget(true);
        saveJButton.setActionCommand("SAVE");
        saveJButton.setMnemonic('S');
        saveJButton.addActionListener(new java.awt.event.
                                      ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                saveJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
        cancelJButton.addActionListener(new java.awt.event.
                                        ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        quitJButton.setMaximumSize(new Dimension(75, 23));
        quitJButton.setMinimumSize(new Dimension(75, 23));
        quitJButton.setPreferredSize(new Dimension(75, 23));
        quitJButton.setActionCommand("QUIT");
        quitJButton.setMnemonic('Q');
        quitJButton.addActionListener(new java.awt.event.
                                      ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quitJButton_actionPerformed(e);
            }
        });
        buttonJPanel.setLayout(gridBagLayout1);
        masterJPanel.setLayout(gridBagLayout2);
        numberJLabel.setMaximumSize(new Dimension(53, 22));
        numberJLabel.setMinimumSize(new Dimension(53, 22));
        numberJLabel.setPreferredSize(new Dimension(53, 22));
        numberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        typeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        typeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        remarkJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        remarkJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        extendJPanel.setLayout(gridBagLayout3);
        numberDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        numberDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        numberDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        nameDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        nameDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        nameDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        typeDisplayJLabel.setMaximumSize(new Dimension(4, 22));
        typeDisplayJLabel.setMinimumSize(new Dimension(4, 22));
        typeDisplayJLabel.setPreferredSize(new Dimension(4, 22));
        remarkDisplayJLabel.setMaximumSize(new Dimension(6, 23));
        remarkDisplayJLabel.setMinimumSize(new Dimension(6, 23));
        remarkDisplayJLabel.setPreferredSize(new Dimension(6, 23));
        jTabbedPane1.setMaximumSize(new Dimension(405, 32767));
        jTabbedPane1.setMinimumSize(new Dimension(405, 78));
        jTabbedPane1.setPreferredSize(new Dimension(405, 536));
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        scrollPane.setMaximumSize(new Dimension(405, 32767));
        scrollPane.setMinimumSize(new Dimension(405, 24));
        scrollPane.setPreferredSize(new Dimension(405, 507));
        setMaximumSize(new Dimension(2000, 2147483647));
        setMinimumSize(new Dimension(405, 121));
        setPreferredSize(new Dimension(405, 800));
        masterJPanel.setBorder(null);
        masterJPanel.setMaximumSize(new Dimension(370, 2147483647));
        masterJPanel.setMinimumSize(new Dimension(370, 463));
        masterJPanel.setPreferredSize(new Dimension(370, 487));
        numberJTextField.setMaximumSize(new Dimension(200, 22));
        jpanel.setMaximumSize(new Dimension(405, 2147483647));
        jpanel.setPreferredSize(new Dimension(405, 470));
        //CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
        lifeCycleInfo.setMaximumSize(new Dimension(200, 60));
        //CCEnd by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
        lifeCycleInfo.setPreferredSize(new Dimension(20, 60));
        lifeCycleInfo.setMinimumSize(new Dimension(20, 60));
        extendJPanel.setBorder(BorderFactory.createEtchedBorder());
        masterJPanel.add(numberJLabel,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.
                                                NONE,
                                                new Insets(10, 15, 5,
                0), 0, 0));
        masterJPanel.add(nameJLabel,
                         new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.
                                                NONE,
                                                new Insets(5, 15, 5,
                0), 0, 0));
        masterJPanel.add(typeJLabel,
                         new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.
                                                NONE,
                                                new Insets(0, 0, 0, 0),
                                                0, 0));
        masterJPanel.add(numberJTextField,
                          new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0//CR1
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 8, 5, 15), 0, 0));
        masterJPanel.add(nameJTextField,
                          new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0//CR1
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 5, 15), 0, 0));

        //����滻���(���¹��տ�ʱ����š����ơ�������ʾ��ǩ)
        masterJPanel.add(numberDisplayJLabel,
                          new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(10, 8, 5, 15), 0, 0));
        masterJPanel.add(nameDisplayJLabel,
                          new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 8, 5, 15), 0, 0));
        masterJPanel.add(typeDisplayJLabel,
                         new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                                ,
                                                GridBagConstraints.WEST,
                                                GridBagConstraints.
                                                HORIZONTAL,
                                                new Insets(5, 8, 5, 8),
                                                0, 0));
        masterJPanel.add(workShopJLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 100, 0, 0), 0, 0));//CR1
//        masterJPanel.add(workshopSortingSelectedPanel, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
//            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 16), 0, 0));
        masterJPanel.add(workShopDisplayjLabel, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 40, 5, 0), 0, 0));//CR1 
       
        //CCBegin SS11
        if(getUserFromCompany().equals("zczx")){
//            workshopSortingSelectedPanel = new CappSortingSelectedPanel(
//                    title2, "QMTechnics", "workShop",this); 
        }else{
        	 //���ţ���������ѡ������ֻ��ѡ�񳧺ͷֳ�)
            workshopSortingSelectedPanel = new CappSortingSelectedPanel(
                    title2, "QMTechnics", "workShop");
        }
        //CCEnd SS11
        workshopSortingSelectedPanel.setIsSelectCC(true);
        workshopSortingSelectedPanel.setButtonSize(89, 23);
        workshopSortingSelectedPanel.setDialogTitle(title2);
        workshopSortingSelectedPanel.setSelectBMnemonic('W');
        masterJPanel.add(workshopSortingSelectedPanel, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 16), 0, 0));
//        masterJPanel.add(relationsJTabbedPane,
//            new GridBagConstraints(0, 3, 4, 1, 1.0, 1.0,
//                                   GridBagConstraints.EAST,
//                                   GridBagConstraints.BOTH, new Insets(5, 8, 5, 8), 0, 0));
        //add by guoxl on 20080226(���沼������)
            masterJPanel.add(relationsJTabbedPane,
               new GridBagConstraints(0, 3, 4, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 9, 5, 7), 0, -1000));
        //add end

	    //����滻���(�鿴���տ�ʱ����ע��ʾ��ǩ)
	   

	    masterJPanel.add(lifeCycleInfo,
	    		 new GridBagConstraints(0, 5, 2, 2, 1.0, 0.0//CR1
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 15, -4, 15), 0, 0));
	    masterJPanel.add(folderPanel,
	            new GridBagConstraints(0, 7, 2, 1, 0, 0,GridBagConstraints.CENTER, 
	            		GridBagConstraints.HORIZONTAL, new Insets(5, 27, 0, 15), 0, 0));
	    //Begin CR7
	    masterJPanel.add(workStateJLabel,
	            new GridBagConstraints(2, 7, 1, 1, 0, 0,GridBagConstraints.CENTER, 
	            		GridBagConstraints.HORIZONTAL, new Insets(5, 100, 10, 15), 0, 0));
	    masterJPanel.add(workStateTextField,
	            new GridBagConstraints(3, 7, 1, 1, 0, 0,GridBagConstraints.CENTER, 
	            		GridBagConstraints.HORIZONTAL, new Insets(5, 40, 10, 15), 0, 0));
	    //End CR7
	    
	    masterJPanel.add(flowDrawingDisp, new GridBagConstraints(0, 8, 1, 1, 0.0,
	                                                0.0
	                                                , GridBagConstraints.EAST,
	                                                GridBagConstraints.NONE,
	                                                new Insets(5, 10, 5, 0), 0, 0));
	    masterJPanel.add(productStateJLabel,  new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
	    
	    masterJPanel.add(checkTechTypeLabel,  new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
	    
//	    masterJPanel.add(productStateSortingSelectedPanel,   new GridBagConstraints(1, 9, 3, 1, 1.0, 0.0
//	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 16), 0, 0));
	    masterJPanel.add(productStateDisplJLabel,   new GridBagConstraints(1, 9, 3, 1, 1.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 0, 16), 0, 0));
	    masterJPanel.add(checkTechTypeDisplJLabel,   new GridBagConstraints(1, 11, 3, 1, 1.0, 0.0
	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 0, 16), 0, 0));
	    
	    masterJPanel.add(remarkDisplayJLabel,
                new GridBagConstraints(1, 10, 1, 1, 1.0, 0.0//CR1
               ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 15), 0, 0));//CR3
        masterJPanel.add(remarkJTextField,
                new GridBagConstraints(1, 10, 1, 1, 1.0, 0.0//CR1
               ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 20), 0, 0));//CR3
        masterJPanel.add(remarkJLabel,
              new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0
                                     ,
                                     GridBagConstraints.EAST,
                                     GridBagConstraints.
                                     NONE,
                                     new Insets(0, 0, 0, 0),
                                     0, 0));
	    
	    
	    //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
	    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
	    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        ResourcePanel  rsPanel = new ResourcePanel();
        Collection col = rsPanel.getCoding("QMTechnics", "productState", "SortType");
        if (col == null || col.size() == 0)
        {
            productStateComboBox.addItem("");
        }
        else
        {
        	productStateComboBox.addItem("");
            for (Iterator iter = col.iterator(); iter.hasNext();)
            {
                CodingIfc code = (CodingIfc) iter.next();
                //                CCBegin SS19
                if(getUserFromCompany().equals("zczx")){
                	if(!code.getCodeContent().toString().contains("������"))
                		productStateComboBox.addItem(code);
                }
                //CCBegin SS25
                else if(getUserFromCompany().equals("ct")){
                	if(!code.getCodeContent().toString().contains("������"))
                		productStateComboBox.addItem(code);
                }
                //CCEnd SS25
                else
//                	CCEnd SS19
                productStateComboBox.addItem(code);
            }
        }
        productStateComboBox.setMaximumSize(new Dimension(80, 22));
        productStateComboBox.setMinimumSize(new Dimension(80, 22));
        productStateComboBox.setPreferredSize(new Dimension(80, 22));
        masterJPanel.add(productStateComboBox,   new GridBagConstraints(1, 9, 3, 1, 1.0, 0.0
    	        ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 0, 16), 0, 0));
        
        
        
        Collection cTypecol = rsPanel.getCoding("QMTechnics", "checkTechType", "SortType");
        if (cTypecol == null || cTypecol.size() == 0)
        {
        	checkTechTypeComboBox.addItem("");
        }
        else
        {
        	checkTechTypeComboBox.addItem("");
            for (Iterator iter = cTypecol.iterator(); iter.hasNext();)
            {
                CodingIfc code = (CodingIfc) iter.next();
  
               
                checkTechTypeComboBox.addItem(code);
            }
            
        }
        checkTechTypeComboBox.setMaximumSize(new Dimension(80, 22));
        checkTechTypeComboBox.setMinimumSize(new Dimension(80, 22));
        checkTechTypeComboBox.setPreferredSize(new Dimension(80, 22));
        masterJPanel.add(checkTechTypeComboBox,   new GridBagConstraints(1, 11, 3, 1, 1.0, 0.0
    	        ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 0, 16), 0, 0));
        
        //CCBegin SS28
        checkTechTypeComboBox.addItemListener(new ItemListener()
        {
			public void itemStateChanged(ItemEvent event) {
				 Date dt=new Date();
			     SimpleDateFormat matter1=new SimpleDateFormat("yyyyMMdd");
			     String liushuiNum="";
			     try {
			    	 liushuiNum=getTechnicsAutoNum();
				} catch (QMException e) {
					e.printStackTrace();
				}
				 Object obj=event.getItem();
				 String technicsAutoNum="CD-JC-"+obj+"-"+remarkJTextField.getText()+"-"+matter1.format(dt)+"-"+liushuiNum;
				 numberJTextField.setText(technicsAutoNum);
//				 System.out.println("technicsAutoNum=======1111111111111========"+technicsAutoNum);
			}
        });
        //CCEnd SS28
        
		// Begin CR1
		masterJPanel.add(iterationJLabel, new GridBagConstraints(2, 6, 1, 1,
				0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,//CR3
				new Insets(5, 100, 5, 8), 0, 0));
		masterJPanel.add(iterationTextField, new GridBagConstraints(3, 6, 1, 1,
				1.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.HORIZONTAL, new Insets(5, 40, 5, 8), 0, 0));
		
		masterJPanel.add(creatorJLabel, new GridBagConstraints(2, 0, 1, 1, 0,//CR3
				0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(5, 100, 10, 15), 0, 0));
		masterJPanel.add(creatorTextField, new GridBagConstraints(3, 0, 1, 1,//CR3
				0, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(5, 40, 10, 15), 0, 0));

		masterJPanel.add(creatTimeJLabel, new GridBagConstraints(2, 1, 1, 1,//CR3
				0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(5, 100, 10, 15), 0, 0));
		masterJPanel.add(creatTimeTextField, new GridBagConstraints(3, 1, 1,//CR3
				1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(5, 40, 10, 15), 0, 0));

		masterJPanel.add(moderfilerJLabel, new GridBagConstraints(2, 4, 1, 1,//CR3
				0, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(5, 100, 10, 15), 0, 0));
		masterJPanel.add(moderfilerTextField, new GridBagConstraints(3,4, 1,//CR3
				1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.BOTH,
				new Insets(5, 40, 10, 15), 0, 0));

		masterJPanel.add(modifyTimeJLabel, new GridBagConstraints(2, 5, 1, 1,//CR3
				0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(5, 100, 10, 15), 0, 0));
		masterJPanel.add(modifyTimeTextField, new GridBagConstraints(3, 5, 1,//CR3
				1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
				new Insets(5, 40, 10, 15), 0, 0));
        //End CR1
		//CCBegin SS6
	    if(ts16949){
	    	 if(yy){ 
	    		 
	    		
	    		 
	    	    masterJPanel.add(consmasterTS16949Panel,   new GridBagConstraints(0, 14, 4, 1, 1.0, 0.0
	      	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 4, 7), 0, 0));
       	     }else{
       	    	 
       	   //guoguo
// 	               masterJPanel.add(masterTS16949Panel,   new GridBagConstraints(0, 14, 4, 1, 1.0, 0.0
// 	   	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 4, 7), 0, 0));
       	    	 
       	    	 //guoguo
 	   	       }
             	
       	   
	    	 
	    	
	    }
	    //CCEnd SS6
	    //CCBegin SS12
	    //�������
	    hideMainPartBsoID.setText("");
	   // String techtype=technicsType.getCodeContent();
	    separableJPanel.setLayout(new GridBagLayout());
	    //CCBegin SS17
	    //separableNumberJLabel.setText("ƽ̨���:");
	    //separableNameJLabel.setText("ƽ̨����:");
	    //separableCountJLabel.setText("ƽ̨����:");
	    separableNumberJLabel.setText("�ܳ��ͺ�:");
	    separableNameJLabel.setText("�ܳ�����:");
	    separableCountJLabel.setText("����:");
	  //CCEnd SS17
	    selectPartJButton.setText("ѡ��..");
	    separableNumberJLabel.setMaximumSize(new Dimension(30, 22));
	    separableNumberJLabel.setMinimumSize(new Dimension(30, 22));
	    separableNumberJLabel.setPreferredSize(new Dimension(30, 22));

	    separableNumber.setMaximumSize(new Dimension(140, 22));
	    separableNumber.setMinimumSize(new Dimension(140, 22));
	    separableNumber.setPreferredSize(new Dimension(140, 22));

	    separableNameJLabel.setMaximumSize(new Dimension(30, 22));
	    separableNameJLabel.setMinimumSize(new Dimension(30, 22));
	    separableNameJLabel.setPreferredSize(new Dimension(30, 22));

	    separableName.setMaximumSize(new Dimension(140, 22));
	    separableName.setMinimumSize(new Dimension(140, 22));
	    separableName.setPreferredSize(new Dimension(140, 22));

	    separableCountJLabel.setMaximumSize(new Dimension(30, 22));
	    separableCountJLabel.setMinimumSize(new Dimension(30, 22));
	    separableCountJLabel.setPreferredSize(new Dimension(30, 22));

	    separableCount.setMaximumSize(new Dimension(140, 22));
	    separableCount.setMinimumSize(new Dimension(140, 22));
	    separableCount.setPreferredSize(new Dimension(140, 22));
	    
	    
	    selectPartJButton.setMaximumSize(new Dimension(20, 22));
	    selectPartJButton.setMinimumSize(new Dimension(20, 22));
	    selectPartJButton.setPreferredSize(new Dimension(20, 22));
	    
	    selectPartJButton.addActionListener(new TechnicsMasterJPanel_productSelectButton_actionAdapter(this));
	    
	    separableJPanel.add(separableNumberJLabel,   new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 21, 2, 4), 0, 0));
	    separableJPanel.add(separableNumber,   new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 2, 4), 0, 0));
	    separableJPanel.add(separableNameJLabel,   new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 21, 2, 4), 0, 0));
	    separableJPanel.add(separableName,   new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 2, 4), 0, 0));
	    separableJPanel.add(separableCountJLabel,   new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 21, 2, 4), 0, 0));
	    separableJPanel.add(separableCount,   new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 2, 4), 0, 0));
	    separableJPanel.add(selectPartJButton,   new GridBagConstraints(6, 0, 1, 1, 1.0, 0.0
  	            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 21, 2, 4), 0, 0));
	    //CCEnd SS12
	    relationsJTabbedPane.add(doclinkJPanel, "�ĵ�");
	    //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
	    //guoguo
	    //relationsJTabbedPane.add(upFilePanel, "����");
	    //guoguo
	    //CCEnd by liunan 2009-10-12
	        jpanel.setLayout(new BorderLayout());
	        jTabbedPane1.add(jpanel, "��������Ϣ");
	    jpanel.add(scrollPane,  BorderLayout.CENTER);
	    scrollPane.getViewport().add(masterJPanel);
        jTabbedPane1.add(extendJPanel, "���������Ϣ");

        buttonJPanel.add(saveJButton,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.
                                                NONE,
                                                new Insets(0, 0, 0, 0),
                                                0, 0));
        buttonJPanel.add(quitJButton,
                         new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton,
                         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                ,
                                                GridBagConstraints.EAST,
                                                GridBagConstraints.
                                                NONE,
                                                new Insets(0, 8, 0, 0),
                                                0, 0));
        add(jTabbedPane1,
            new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                   , GridBagConstraints.CENTER,
                                   GridBagConstraints.BOTH,
                                   new Insets(0, 2, 0, 0), 0, 0));

        lifeCycleInfo.setBsoName("QMFawTechnics");
        folderPanel.setButtonMnemonic('B');
        workshopSortingSelectedPanel.setSelectBMnemonic('W');
        //CCBegin add by lil in 20080903-----------begin
        numberJTextField.addTextListener(new java.awt.event.TextListener()
        {
        public void textValueChanged(TextEvent e)
        {
                   text_textValueChanged(e);
        }
        });
    }

    private void text_textValueChanged(TextEvent e)  
    {
    	//CCBegin by liunan 2010-11-17 �������ԡ����ӹ��ա������⹤�ա������˲�����
      //Դ��
      //masterTS16949Panel.setAttribute(numberJTextField.getText());
      String type=technicsType.getCodeContent();
      //CCBegin SS6
      //CCBegin SS11
      if (!isBSXAutoNum(type)){
    	  try {
			if(getUserFromCompany().equals("zczx")){
				if(type.equals("��ݻ��ӹ���")||type.equals("����ȴ�����")){
					//�������Fema�ȱ�Ų����ݹ��ձ�ű仯�����Բ�����masterTS16949Panelҳ��
				}
			}
			//CCBegin SS23
			else if(getUserFromCompany().equals("ct")){
				masterTS16949Panel.setAttribute(numberJTextField.getText());
			}
			//CCEnd SS23
			else{
		         if(!type.equals("���ӹ���")&&!type.equals("���⹤��"))
		           {
		      	   masterTS16949Panel.setAttribute(numberJTextField.getText());
		           }
			}
		} catch (QMException e1) {
			e1.printStackTrace();
		}

      }
      //CCEnd SS11
      //CCEnd SS6
      //CCEnd by liunan 2010-11-17
    }
    //CCEnd add by lil in 20080903-----------end
    /**
     * ������Ϣ���ػ�
     */
    protected void localize()
    {
        initResources();
        try
        {
        	//CCBegin SS8
        	Boolean BSX = false; 	
          	try {
      	    	Class[] paraClass = {};
      	        Object[] objs = {};
      	        String className = "com.faw_qm.doc.util.DocServiceHelper";
      	        String methodName = "isBSXGroup";
      	        StaticMethodRequestInfo in = new StaticMethodRequestInfo();
      	        in.setClassName(className);
      	        in.setMethodName(methodName);
      	        in.setParaClasses(paraClass);
      	        in.setParaValues(objs);
      	        RequestServer server = null;
      	        server = RequestServerFactory.getRequestServer();	
      	        BSX = (Boolean) server.request(in);
      		} catch (QMRemoteException e) {
      			e.printStackTrace();
      		}
            if(BSX){
            	remarkJLabel.setText("PFEMA���");
            }else{
                remarkJLabel.setText("���ó���");
            }
            //CCEnd SS8
            saveJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "SaveJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "CancelJButton", null));
            quitJButton.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "QuitJButton", null));
            numberJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "technicsNumberJLabel", null));
            nameJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "technicsNameJLabel", null));
            typeJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "technicsTypeJLabel", null));

            workShopJLabel.setText(QMMessage.getLocalizedMessage(
                    RESOURCE, "workshopJLabel", null));
        }
        catch (Exception ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.MISSING_RESOURCER, null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        add(buttonJPanel,
            new GridBagConstraints(0, 1,
                                   GridBagConstraints.REMAINDER,
                                   GridBagConstraints.REMAINDER,
                                   0.0, 0.0
                                   , GridBagConstraints.EAST,
                                   GridBagConstraints.NONE,
                                   new Insets(10, 0, 10, 5), 0,
                                   0));

    }
//CCBegin SS12
    /**
     * ��Ӳ�Ʒ��ťƽ̨�ڲ���
     */
    class TechnicsMasterJPanel_productSelectButton_actionAdapter implements java.awt.event.ActionListener
    {
    	TechnicsMasterJPanel adaptee;

    	TechnicsMasterJPanel_productSelectButton_actionAdapter(TechnicsMasterJPanel adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.productSelectButton_actionPerformed(e);
        }
    }
    /**
     * ������Ҫ�����Ӳ�Ʒƽ̨
     * @param e ActionEvent
     */
    void productSelectButton_actionPerformed(ActionEvent e) {
		//CCBegin SS16
//    	if (mainPart == null || mainPart.equals("")) 
//			JOptionPane.showMessageDialog(this.getParentJFrame(),
//					"����ѡ����Ҫ�������ѡ���Ʒƽ̨��");
//			setCursor(Cursor.getDefaultCursor());
//			return;
//		}
//		String mainPart = hideMainPartBsoID.getText();
//    	Class[] paraclass = {QMTechnicsIfc.class};
//    	Object[] paraobj = {getTechnics()};
//    	QMPartIfc c = null;
    	String mainPart = null;
//    	try {
//			c = (QMPartIfc) useServiceMethod("StandardCappService","getQMPart",paraclass,paraobj);
//		} catch (QMRemoteException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
    	try {
			Vector v = partLinkJPanel.getAllLinks();
	    	for(int i = 0;i<v.size();i++){
	    		PartUsageQMTechnicsLinkInfo p = (PartUsageQMTechnicsLinkInfo)v.get(i);
	    		if(p.getMajorpartMark())
	    		{
	    			mainPart = p.getLeftBsoID();
	    		}
	    	}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (mainPart == null) {
			JOptionPane.showMessageDialog(this.getParentJFrame(),
					"����ѡ����Ҫ�������ѡ���Ʒƽ̨��");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
//    	String mainPart = c.getBsoID();
    	//CCEnd SS16
		
		// //��ѯ��Ҫ����ı����ڲ�Ʒ�б�
		Vector partUsedByProductVec = new Vector();
		RequestServer server = RequestServerFactory.getRequestServer();
		
		//��ǰ�û������ù淶
		PartConfigSpecIfc partConfigSpecIfc = null;
		StaticMethodRequestInfo info1 = new StaticMethodRequestInfo();
		info1.setClassName("com.faw_qm.part.util.PartServiceRequest");
		info1.setMethodName("findPartConfigSpecIfc");
		Class[] paraClass = { };
		info1.setParaClasses(paraClass);
		Object[] obj = { };
		info1.setParaValues(obj);
		try {
			partConfigSpecIfc = ((PartConfigSpecIfc) server.request(info1));
		} catch (QMException ee) {
			ee.printStackTrace();
		}
		//���PartInfo
		QMPartIfc partIfc = null;
		ServiceRequestInfo info2 = new ServiceRequestInfo();
		info2.setServiceName("PersistService");
		info2.setMethodName("refreshInfo");
		Class[] theClass = { String.class };
		Object[] obj2 = { mainPart };
		info2.setParaClasses(theClass);
		info2.setParaValues(obj2);
		try {
		 partIfc = (QMPartIfc) server.request(info2);
		} catch (QMException ee) {
			ee.printStackTrace();
		}
		//�жϻ�ò����Ƿ���ȷ
		if(partConfigSpecIfc==null){
			JOptionPane.showMessageDialog(this.getParentJFrame(),"�û����ù淶��ʼ������");
			setCursor(Cursor.getDefaultCursor());
			return;
		}
		if(partIfc==null){
			JOptionPane.showMessageDialog(this.getParentJFrame(),"��Ҫ�����ʼ������");
			setCursor(Cursor.getDefaultCursor());
			return;	
		}
		
		//��õ�ǰ����ı����ڲ�Ʒ
		StaticMethodRequestInfo info3 = new StaticMethodRequestInfo();
		info3.setClassName("com.faw_qm.part.util.PartServiceRequest");
		info3.setMethodName("setUsageList");
		Class[] paraClass3 = {QMPartIfc.class,PartConfigSpecIfc.class };
		info3.setParaClasses(paraClass3);
		Object[] obj3 = {partIfc, partConfigSpecIfc};
		info3.setParaValues(obj3);
		try {
			partUsedByProductVec = ((Vector) server.request(info3));
		} catch (QMException ee) {
			ee.printStackTrace();
		}
		
		if(partUsedByProductVec==null||partUsedByProductVec.size()<=0){
			JOptionPane.showMessageDialog(this.getParentJFrame(),"��Ҫ��������ڲ�Ʒƽ̨��Ϣ��");
			setCursor(Cursor.getDefaultCursor());
			return;	
		}
		ProductSelectJDialog partJDialog = new ProductSelectJDialog(this);
		partJDialog.setPartVec(partUsedByProductVec);
		partJDialog.setVisible(true);

    }
//CCEnd SS12

    /**
     * �����ڹ�������ѡ��ĸ��ڵ�
     * @param parent ���ڵ�
     */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
    }
    /**
     * ����CAPP.property �������ж��Ƿ����ڱ������Զ����
     * @param techType
     * @return
     *  CCBegin SS3
     */
    public boolean isBSXAutoNum(String techType)
    {
        String techAllType = RemoteProperty.getProperty("bsx_technics_autoNum");
        String[] typeVec = techAllType.split(",");
        for(int i=0;i<typeVec.length;i++)
        {
            if(typeVec[i].equals(techType))
                return true; 
        }
        return false;
    }
  //CCEnd SS3
    /**
     * ���ý���Ϊ����ģʽ
     */
    private void setCreateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setCreateMode() begin...");
        }
        try
        {
            QMFawTechnicsInfo info = new QMFawTechnicsInfo();
            setTechnics(info);
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        clear();
        WorkThread wt = new WorkThread(5);
        wt.start();
        // refreshObject();
//      CCBeginby leixiao 2009-3-31 ԭ�򣺽����������,��Ű����ͳ�
        String type=technicsType.getCodeContent();
        //CCBegin SS3
        //CCBegin SS23
        try{
        if(isBSXAutoNum(type))
        {
            numberJTextField.setText("�Զ����");
            numberJTextField.setEditable(false);
        }
    	 else if(getUserFromCompany().equals("ct")){
    		 numberJTextField.setText((String)typeMap.get(type));
         	 numberJTextField.setEditable(true);
         }
        else
        {
        if (typeMap.get(type)!=null){
        	numberJTextField.setText((String)typeMap.get(type));
        	numberJTextField.setEditable(true);
        }
//      CCEndby leixiao 2009-3-31 ԭ�򣺽����������,��Ű����ͳ�
        }
        }catch(QMException e){
        	e.printStackTrace();
        }
      //CCEnd SS23
      //CCEnd SS3
        numberJTextField.setVisible(true);
        nameJTextField.setVisible(true);
        remarkJTextField.setVisible(true);
        remarkDisplayJLabel.setVisible(false);
        numberDisplayJLabel.setVisible(false);
        nameDisplayJLabel.setVisible(false);
        typeDisplayJLabel.setVisible(true);
        //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
        //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
        //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        workshopSortingSelectedPanel.setVisible(true);
        workShopDisplayjLabel.setVisible(false);
        //CCBegin SS27
        try
        {
	    	 if(getUserFromCompany().equals("ct")){
	    		 Class[] paraclass ={String.class,String.class};
	             Object[] paraobj ={"���طֹ�˾","��֯����"};
	             CodingClassificationInfo coding = (CodingClassificationInfo) CappClientHelper.useServiceMethod(
	                     "CodingManageService", "findClassificationByName", paraclass, paraobj);
	        	if(coding!=null){
	        		workshopSortingSelectedPanel.setCoding(coding);
	        		workshopSortingSelectedPanel.setVisible(true);
	        		workshopSortingSelectedPanel.setEnabled(true);
	        	} 
	         }
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        //CCEnd SS27
        //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
	    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
	    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        productStateComboBox.setVisible(true);
//      CCBegin by liuzc 2009-12-23 ԭ�򣺸��ݽ��Ҫ�󣬹�������Ϣ��Ʒ״̬Ĭ��Ϊ������
        productStateComboBox.setSelectedIndex(2);
//      CCEnd by liuzc 2009-12-23 ԭ�򣺸��ݽ��Ҫ�󣬹�������Ϣ��Ʒ״̬Ĭ��Ϊ������
        productStateDisplJLabel.setVisible(false);
        checkTechTypeDisplJLabel.setVisible(false);
        folderPanel.setViewState(true);
        ////add by wangh on 20070514 �������ϼ��ı����ڴ���ģʽ�²���д.
        folderPanel.setTextFielEnable(false);
        //productStateDisplJLabel.setLocale();
        
        //Begin CR7
        workStateJLabel.setVisible(true);
        workStateTextField.setVisible(false);
        workStateTextField.setText(null);
        //End CR7
        //CCBegin SS12
        separableNumber.setVisible(true);
        separableNumberJLabel.setVisible(true);
        separableNumberDisplayJLabel.setVisible(false);
        separableName.setVisible(true);
        separableNameJLabel.setVisible(true);
        separableNameDisplayJLabel.setVisible(false);
        separableCount.setVisible(true);
        separableCountJLabel.setVisible(true);
        separableCountDisplayJLabel.setVisible(false);
        selectPartJButton.setVisible(true);
        //CCBegin SS18
        separableNumber.setEditable(true);
        separableName.setEditable(true);
        separableCount.setEditable(true);
        selectPartJButton.setEnabled(true);
      //CCEnd SS18
        String techtype = technicsType.getCodeContent();
	    try {
			if(getUserFromCompany().equals("zczx")&&(techtype.equals("��ݻ��ӹ���")||techtype.equals("����ȴ�����"))){
			    masterJPanel.add(separableJPanel,   new GridBagConstraints(0, 16, 4, 1, 1.0, 0.0
			            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 4, 7), 0, 0));
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS12
        
        //Begin CR3
        
        creatorJLabel.setVisible(false);
        creatorTextField.setVisible(false);
        creatTimeJLabel.setVisible(false);
        creatTimeTextField.setVisible(false);
        moderfilerJLabel.setVisible(false);
        moderfilerTextField.setVisible(false);
        modifyTimeJLabel.setVisible(false);
        modifyTimeTextField.setVisible(false);
        iterationJLabel.setVisible(false);
        iterationTextField.setVisible(false);

        masterJPanel.add(lifeCycleInfo,
	    		 new GridBagConstraints(0, 5, 4, 2, 1.0, 0.0
	    				 ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 15, -4, 15), 0, 0));
        
        masterJPanel.add(remarkJTextField,
                new GridBagConstraints(1, 10, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 20), 0, 0));
        
        masterJPanel.add(numberJTextField,
                new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 8, 5, 15), 0, 0));
        masterJPanel.add(nameJTextField,
                new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 5, 15), 0, 0));
        //End CR3
        
        
         
        try
        {
            folderPanel.setLabelText(getPersionalFolder());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setTechnics(getTechnics());
        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);
//      CCBeginby leixiao 2009-10-23 ԭ��
        String defaultlifecycle = RemoteProperty.getProperty(
				"Ĭ�ϵĹ�����������", "������������");
				//CCBegin by liunan 2010-11-17 �������Ļ��Ӻ����⹤��ʱ���á����������տ��������ڡ�
				if(type.equals("���ӹ���")||type.equals("���⹤��"))
				{
					defaultlifecycle = RemoteProperty.getProperty("com.faw_qm.lifecycle.QMFawTechnics.fdjLifeCycle", "���������տ���������");
				}
				//CCEnd by liunan 2010-11-17
				//CCBegin SS15
				if(type.equals("����ȴ�����")||type.equals("��ݻ��ӹ���"))
				{
					defaultlifecycle = RemoteProperty.getProperty("com.faw_qm.lifecycle.QMFawTechnics.fdjLifeCycle", "������Ĺ��չ����������");
				}
				//CCEnd SS15
				//CCBegin SS13
				if(type.startsWith("������"))
				{
					defaultlifecycle = RemoteProperty.getProperty("com.faw_qm.lifecycle.QMFawTechnics.bsxLifeCycle", "������(����)��У��������������");
				}
				//CCEnd SS13
					//CCBegin SS21
				if(type.startsWith("����"))
				{
					defaultlifecycle = RemoteProperty.getProperty("com.faw_qm.lifecycle.QMFawTechnics.ctLifeCycle", "���ع�����������");
				}
				
				//CCEnd SS21
				//CCBegin SS24
				if(type.equals("����װ�乤��"))
				{
					defaultlifecycle = "����װ�乤����������";
				}
				//CCEnd SS24
				
				
				if(type.startsWith("�ɶ�"))
				{
					defaultlifecycle = "�ɶ����չ����������";
				}
				
				
				
        lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle(defaultlifecycle);
      //CCBegin SS15
        //lifeCycleInfo.getLifeCyclePanel().setEnabled(false);
        if(type.equals("����ȴ�����")||type.equals("��ݻ��ӹ���"))
		{
        	lifeCycleInfo.getLifeCyclePanel().setEnabled(true);
		}
        else{
        	lifeCycleInfo.getLifeCyclePanel().setEnabled(false);
        }
      //CCEnd SS15
//      CCEndby leixiao 2009-10-23 ԭ�� 
        setButtonVisible(true);
        typeDisplayJLabel.setText(technicsType.getCodeContent());
        workshopSortingSelectedPanel.setDefaultCoding("����",
                technicsType.getCodeContent());
//      CCBeginby leixiao 2010-4-2 ԭ�򣺹��ղ��ſɸ��ģ�������ȡ���ղ���
		//CCBegin by liunan 2012-03-15 ���ݽ�� ��ޱ Ҫ�� �������γ��� ��ѡ�¼���
		//if(type.equals("���ӹ���")||type.equals("���⹤��"))
        //SSBegin SS9
//        if(type.equals("���ӹ���")||type.equals("���⹤��")||type.equals("����װ�乤��"))
		if(type.equals("���ӹ���")||type.equals("���⹤��")||type.equals("����װ�乤��")||type.contains("������"))
//		SSEnd SS9
			//CCEnd by liunan 2012-03-15
		{
		workshopSortingSelectedPanel.setIsOnlyCC(false);	
		}
		else{
        workshopSortingSelectedPanel.setIsOnlyCC(true);
		}
//      CCEndby leixiao 2010-4-2 
        //20070202Ѧ��add
		//CCBegin SS6
         if(ts16949){
        	 // CCBegin SS6
        	 if(isBSXAutoNum(type)){
        		  consmasterTS16949Panel.setCreateMode();
        	 }else{
             masterTS16949Panel.setCreateMode();
             }
         } 
         ////CCEnd SS6
         //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
         this.getUpFilePanel().setAButtonVisable(true);
         this.getUpFilePanel().setDButtonVisable(true);
         this.getUpFilePanel().setVButtonVisable(false);
         this.getUpFilePanel().setDLButtonVisable(false);
         //this.getUpFilePanel().getMultiList().clear();
         this.getUpFilePanel().setRow(0);
         //CCEnd by liunan 2009-10-12
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.setCreateMode() end...return is void");
        }
    }


    /**
     * ���ý���Ϊ����ģʽ(ֻ���Ը��±�ע�͸��ֹ���)
     */
    private void setUpdateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setUpdateMode() begin...");
        }
        clear();
        //����������
        setTechnicsType(getTechnics().getTechnicsType());
        WorkThread wt = new WorkThread(5);
        wt.start();
        //refreshObject();
        numberJTextField.setVisible(false);
        nameJTextField.setVisible(false);
        //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
	    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
	    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        this.productStateComboBox.setVisible(true);
        checkTechTypeComboBox.setVisible(true);
        productStateDisplJLabel.setVisible(false);
        checkTechTypeDisplJLabel.setVisible(false);
        //�ҵ����տ�����Ĺ���
//      CCBeginby leixiao 2010-4-2 ԭ�򣺹��ղ��ſɸ��ģ�������ȡ���ղ���
//        Class[] paraclass =
//                {
//                String.class, String.class};
//        Object[] paraobj =
//                {
//                getTechnics().getBsoID(),
//                getTechnics().getBsoID()};
//        Collection c = null;
//        try
//        {
//            c = (Collection) useServiceMethod("StandardCappService",
//                                              "browseProcedures",
//                                              paraclass,
//                                              paraobj);
//        }
//        catch (QMRemoteException ex)
//        {
//            ex.printStackTrace();
//            return;
//        }
        //û�й�����ɸ��²���
//        if (c == null || c.size() == 0)
//        {
            workshopSortingSelectedPanel.setVisible(true);
            workshopSortingSelectedPanel.setCoding(getTechnics().getWorkShop());
//          CCBeginby leixiao 2010-4-2 ԭ�򣺹��ղ��ſɸ��ģ�������ȡ���ղ���
            //CCBegin by liunan 2012-03-15 ���ݽ�� ��ޱ Ҫ�� �������γ��� ��ѡ�¼���
            //if(getTechnics().getTechnicsType().getCodeContent().equals("���ӹ���")||getTechnics().getTechnicsType().getCodeContent().equals("���⹤��"))
            //CCBegin SS14
            String ttype = getTechnics().getTechnicsType().getCodeContent();
            //if(getTechnics().getTechnicsType().getCodeContent().equals("���ӹ���")||getTechnics().getTechnicsType().getCodeContent().equals("���⹤��")||getTechnics().getTechnicsType().getCodeContent().equals("����װ�乤��"))
            if(ttype.equals("���ӹ���")||ttype.equals("���⹤��")||ttype.equals("����װ�乤��")||ttype.contains("������"))
            //CCEnd SS14
            //CCEnd by liunan 2012-03-15
            {
            workshopSortingSelectedPanel.setIsOnlyCC(false);
            }
            else{
            workshopSortingSelectedPanel.setIsOnlyCC(true);// ���ղ���ȡ������
            }
//          CCEndby leixiao 2010-4-2 ԭ�򣺹��ղ��ſɸ��ģ�������ȡ���ղ���
            workShopDisplayjLabel.setVisible(false);
//        }
        //���򲻿ɸ��²���
//        else
//        {
//        	
//            workshopSortingSelectedPanel.setVisible(false);
//            workShopDisplayjLabel.setVisible(true);
//            workShopDisplayjLabel.setText(getTechnics().
//                                          getWorkShop().toString());
//
//        }
//          CCEndby leixiao 2010-4-2 ԭ�򣺹��ղ��ſɸ��ģ�������ȡ���ղ���
        //Begin CR7
        workStateJLabel.setVisible(true);
        workStateTextField.setVisible(true);
        //CCBegin by liuzc 2009-12-19 ԭ�򣺸���ʱû������״̬���μ�DefectID=2689
	    String workState;
		try {
	        WorkInProgressHelper wip = new WorkInProgressHelper();
			workState = wip.getStatus((WorkableIfc)getTechnics());
			workStateTextField.setText(workState);
		} catch (QMException e) {
			e.printStackTrace();
		}
		//CCEnd by liuzc 2009-12-19 ԭ�򣺸���ʱû������״̬���μ�DefectID=2689
        //End CR7
        
        //Begin CR3
        
        creatorJLabel.setVisible(false);
        creatorTextField.setVisible(false);
        creatTimeJLabel.setVisible(false);
        creatTimeTextField.setVisible(false);
        moderfilerJLabel.setVisible(false);
        moderfilerTextField.setVisible(false);
        modifyTimeJLabel.setVisible(false);
        modifyTimeTextField.setVisible(false);
        iterationJLabel.setVisible(false);
        iterationTextField.setVisible(false);
        
        //CCBegin SS12
        separableNumber.setVisible(true);
        separableNumber.setText(getTechnics().getSeparableNumber());
        separableNumber.setEditable(true);
        separableNumberJLabel.setVisible(true);
        separableNumberDisplayJLabel.setVisible(false);
        separableName.setVisible(true);
        separableName.setText(getTechnics().getSeparableName());
        separableName.setEditable(true);
        separableNameJLabel.setVisible(true);
        separableNameDisplayJLabel.setVisible(false);
        separableCount.setVisible(true);
        separableCount.setText(getTechnics().getSeparableCount()); 
        separableCount.setEditable(true);
        separableCountJLabel.setVisible(true);
        separableCountDisplayJLabel.setVisible(false);
        selectPartJButton.setVisible(true);
        selectPartJButton.setEnabled(true);
        String techtype = technicsType.getCodeContent();
	    try {
			if(getUserFromCompany().equals("zczx")&&(techtype.equals("��ݻ��ӹ���")||techtype.equals("����ȴ�����"))){
			    masterJPanel.add(separableJPanel,   new GridBagConstraints(0, 16, 4, 1, 1.0, 0.0
			            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 4, 7), 0, 0));
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS12
        masterJPanel.add(lifeCycleInfo,
	    		 new GridBagConstraints(0, 5, 4, 2, 1.0, 0.0
	    				 ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 15, -4, 15), 0, 0));
        
        masterJPanel.add(remarkJTextField,
                new GridBagConstraints(1, 10, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 20), 0, 0));
        
        masterJPanel.add(numberJTextField,
                new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 8, 5, 15), 0, 0));
        masterJPanel.add(nameJTextField,
                new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 5, 15), 0, 0));
        //End CR3
        
        
        remarkJTextField.setVisible(true);
        numberDisplayJLabel.setVisible(true);
        nameDisplayJLabel.setVisible(true);
        typeDisplayJLabel.setVisible(true);
        remarkDisplayJLabel.setVisible(false);
        folderPanel.setViewState(true);
        //add by wangh on 20070514 �������ϼ��ı����ڸ���ʱ����д.
        folderPanel.setTextFielEnable(false);
        folderPanel.getFolderPanelLabel();
        numberDisplayJLabel.setText(getTechnics().
                                    getTechnicsNumber());

        String name = getTechnics().getTechnicsName();
        nameDisplayJLabel.setText(name);

        typeDisplayJLabel.setText(technicsType.getCodeContent()); //��������
        workshopSortingSelectedPanel.setCoding(getTechnics().
                                               getWorkShop());
        String remark = getTechnics().getRemark();
        remarkJTextField.setText(remark);
        folderPanel.setLabelText(getTechnics().getLocation());

        lifeCycleInfo.getLifeCyclePanel().setLifeCycle((
                LifeCycleManagedIfc) getTechnics());
        lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setTechnics(getTechnics());
        setButtonVisible(true);
        //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
	    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
	    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        setComboBox(productStateComboBox, getTechnics().getProductState());
        setComboBox(checkTechTypeComboBox, getTechnics().getCheckTechType());
        
        //CCBegin SS6
        String type=technicsType.getCodeContent();
        if(ts16949)
        	 if(isBSXAutoNum(type)){
       		  consmasterTS16949Panel.setUpdateMode(getTechnics());
       	     }else{
             masterTS16949Panel.setUpdateMode(getTechnics());
       	     }
         //CCEnd SS6
         //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
         setUpFileAccessaryName((QMFawTechnicsInfo) getTechnics());
         this.getUpFilePanel().setAButtonVisable(true);
         this.getUpFilePanel().setDButtonVisable(true);
         this.getUpFilePanel().setVButtonVisable(false);
         this.getUpFilePanel().setDLButtonVisable(true);
         //CCEnd by liunan 2009-10-12
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.setUpdateMode() end...return is void");
        }
    }


    /**
     * ���ý���Ϊ�鿴ģʽ
     */
    private void setViewMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setViewMode() begin...");
        }
        clear();
       /* Class[] paraclass=new Class[]{String.class};
        Object[] paraobj=new Object[]{getTechnics().getBsoID()};
        try
        {
           data=  (CappWrapData)useServiceMethod("StandardCappService", "getCappWrapData",
                             paraclass, paraobj);

        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
        }*/
        //����������
        setTechnicsType(getTechnics().getTechnicsType());
        //modify by wangh on 20080255
//        WorkThread wt = new WorkThread(5);
//        wt.start();
         refreshObject();
//modify end
        numberJTextField.setVisible(false);
        nameJTextField.setVisible(false);
        workshopSortingSelectedPanel.setVisible(false);
        workShopDisplayjLabel.setVisible(true);
        remarkJTextField.setVisible(false);

        numberDisplayJLabel.setVisible(true);
        nameDisplayJLabel.setVisible(true);
        typeDisplayJLabel.setVisible(true);
        remarkDisplayJLabel.setVisible(true);
        folderPanel.setViewState(false);
        folderPanel.getFolderPanelLabel();
        //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
	    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
	    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        productStateComboBox.setVisible(false);
        checkTechTypeComboBox.setVisible(false);
        productStateDisplJLabel.setVisible(true);
        checkTechTypeDisplJLabel.setVisible(true);
        
        //CCBegin SS12
        separableNumber.setVisible(true);
        separableNumber.setEditable(false);
        separableNumberJLabel.setVisible(true);
        separableNumberDisplayJLabel.setVisible(false);
        separableName.setVisible(true);
        separableName.setEditable(false);
        separableNameJLabel.setVisible(true);
        separableNameDisplayJLabel.setVisible(false);
        separableCount.setVisible(true);
        separableCount.setEditable(false);
        separableCountJLabel.setVisible(true);
        separableCountDisplayJLabel.setVisible(false);
        selectPartJButton.setVisible(true);
        selectPartJButton.setEnabled(false);
        separableNumber.setText(getTechnics().getSeparableNumber());
        separableName.setText(getTechnics().getSeparableName());
        separableCount.setText(getTechnics().getSeparableCount()); 
        String techtype = technicsType.getCodeContent();
	    try {
			if(getUserFromCompany().equals("zczx")&&(techtype.equals("��ݻ��ӹ���")||techtype.equals("����ȴ�����"))){
			    masterJPanel.add(separableJPanel,   new GridBagConstraints(0, 16, 4, 1, 1.0, 0.0
			            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 4, 7), 0, 0));
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS12
        
        //Begin CR7
        WorkInProgressHelper wip = new WorkInProgressHelper();
        workStateJLabel.setVisible(true);
        workStateTextField.setVisible(true);
	    String workState;
			try {
				workState = wip.getStatus((WorkableIfc)getTechnics());
				workStateTextField.setText(workState);
			} catch (QMException e) {
				e.printStackTrace();
			}
	    //End CR7


        
        // Begin CR1
		creatorJLabel.setVisible(true);
		creatorTextField.setVisible(true);
		if (getTechnics().getVersionValue() != null) 
		{
			String creator = this.getCreator(getTechnics().getIterationCreator());
			creatorTextField.setText(creator);
		}

		creatTimeJLabel.setVisible(true);
		creatTimeTextField.setVisible(true);
		if (getTechnics().getVersionValue() != null) 
		{
			String creattime = getTechnics().getCreateTime().toString();
			creatTimeTextField.setText(creattime);
		}

		moderfilerJLabel.setVisible(true);
		moderfilerTextField.setVisible(true);
		if (getTechnics().getVersionValue() != null) 
		{
			String moderfiler = this.getCreator(getTechnics()
					.getIterationModifier());
			moderfilerTextField.setText(moderfiler);
		}

		modifyTimeJLabel.setVisible(true);
		modifyTimeTextField.setVisible(true);
		if (getTechnics().getVersionValue() != null) 
		{
			String modifyTime = getTechnics().getModifyTime().toString();
			modifyTimeTextField.setText(modifyTime);
		}

		iterationJLabel.setVisible(true);
		iterationTextField.setVisible(true);
		if (getTechnics().getVersionValue() != null) 
		{
			String iteration = getTechnics().getVersionValue();
			iterationTextField.setText(iteration);
		}
        //End CR1
        doclinkJPanel.setMode("View");
        doclinkJPanel.setTechnics(getTechnics());
        numberDisplayJLabel.setText(getTechnics().
                                    getTechnicsNumber());
        nameDisplayJLabel.setText(getTechnics().getTechnicsName());

        typeDisplayJLabel.setText(technicsType.getCodeContent());
        //CCBegin SS1
        if(getTechnics().getWorkShop()==null)
        {
        	workShopDisplayjLabel.setText("");
        }
        else
        {
          workShopDisplayjLabel.setText(getTechnics().
                                      getWorkShop().toString());
        }
        //CCEnd SS1
        remarkDisplayJLabel.setText(getTechnics().getRemark());
        folderPanel.setLabelText(getTechnics().getLocation());
        //20080807 xucy
        lifeCycleInfo.getLifeCyclePanel().setLifeCycle(getTechnics());
        lifeCycleInfo.getProjectPanel().setObject((
                LifeCycleManagedIfc) getTechnics());
        lifeCycleInfo.setMode(LifeCycleInfo.VIEW_MODE);
        //20070202Ѧ��add
        //CCBegin SS6
        String type=technicsType.getCodeContent();
        if(ts16949)
        	if(isBSXAutoNum(type)){
        		consmasterTS16949Panel.setViewMode( getTechnics());
        	}else{
        		masterTS16949Panel.setViewMode( getTechnics());
        	}
        //CCEnd SS6 
        if(getTechnics().getProductState()!=null)
        productStateDisplJLabel.setText(getTechnics().getProductState().getCodeContent());
        
        if(getTechnics().getCheckTechType()!=null)
        checkTechTypeDisplJLabel.setText(getTechnics().getCheckTechType().getCodeContent());
        
        
        setButtonVisible(false);
        //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
        setUpFileAccessaryName((QMFawTechnicsInfo) getTechnics());
        this.getUpFilePanel().setAButtonVisable(false);
        this.getUpFilePanel().setDButtonVisable(false);
        this.getUpFilePanel().setVButtonVisable(false);
        this.getUpFilePanel().setDLButtonVisable(true);
        //CCEnd by liunan 2009-10-12
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setViewMode() end...return is void");
        }
    }


    /**
     * ���ý���Ϊ�����Ϊ��ģʽ(ֻ�й������಻���޸�)
     */
	private void setSaveAsMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setSaveAsMode() begin...");
        }
        clear();
        existTechnicsInfo = getTechnics();
        numberJTextField.setVisible(true);
        nameJTextField.setVisible(true);
        //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
	    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
	    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        productStateComboBox.setVisible(true);
        productStateDisplJLabel.setVisible(false);
        checkTechTypeComboBox.setVisible(true);
        checkTechTypeDisplJLabel.setVisible(false);
       
        //�ҵ����տ�����Ĺ���
//		CCBeginby leixiao 2010-04-01
//        Class[] paraclass =
//                {
//                String.class, String.class};
//        Object[] paraobj =
//                {
//                getTechnics().getBsoID(),
//                getTechnics().getBsoID()};
//        Collection c = null;
//        try
//        {
//            c = (Collection) useServiceMethod("StandardCappService",
//                                              "browseProcedures",
//                                              paraclass,
//                                              paraobj);
//        }
//        catch (QMRemoteException ex)
//        {
//            ex.printStackTrace();
//            return;
//        }
//        //û�й�����ɸ��²���
//        if (c == null || c.size() == 0)
//        {
        //CCBegin SS5
        String type=technicsType.getCodeContent();
        if(isBSXAutoNum(type)){
			 //�ҵ����տ�����Ĺ���
	        Class[] paraclass =
	                {
	                String.class, String.class};
	        Object[] paraobj =
	                {
	                getTechnics().getBsoID(),
	                getTechnics().getBsoID()};
	        Collection c = null;
	        try
	        {
	            c = (Collection) useServiceMethod("StandardCappService",
	                                              "browseProcedures",
	                                              paraclass,
	                                              paraobj);
	        }
	        catch (QMRemoteException ex)
	        {
	            ex.printStackTrace();
	            return;
	        }
	        //û�й�����ɸ��²���
	        if (c == null || c.size() == 0)
	        {
	            workshopSortingSelectedPanel.setVisible(true);
	            workShopDisplayjLabel.setVisible(false);
	            workshopSortingSelectedPanel.setCoding(getTechnics().
	                    getWorkShop());
	        }
	        //���򲻿ɸ��²���
	        else
	        {
	            workshopSortingSelectedPanel.setVisible(false);
	            workShopDisplayjLabel.setVisible(true);
	            workShopDisplayjLabel.setText(getTechnics().
	                                          getWorkShop().toString());
	        }
	       
	        lifeCycleInfo.getLifeCyclePanel().setLifeCycle((
	                LifeCycleManagedIfc) getTechnics());
	      
	        numberJTextField.setVisible(false); 
	        numberJTextField.setText(getTechnics().getTechnicsNumber());
		   }else{
        	workShopDisplayjLabel.setVisible(false);
            workshopSortingSelectedPanel.setVisible(true);
            workshopSortingSelectedPanel.setCoding(getTechnics().
                        getWorkShop());
            //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
            setUpFileAccessaryName((QMFawTechnicsInfo) getTechnics());
            this.getUpFilePanel().setAButtonVisable(true);
            this.getUpFilePanel().setDButtonVisable(true);
            this.getUpFilePanel().setVButtonVisable(false);
            this.getUpFilePanel().setDLButtonVisable(true);
            numberJTextField.setText(getTechnics().getTechnicsNumber());
            //CCEnd by liunan 2009-10-12
		}
        //CCEnd SS5
//        }
//        //���򲻿ɸ��²���
//        else
//        {
//        	workshopSortingSelectedPanel.setVisible(false);
//            workShopDisplayjLabel.setVisible(true);
//            workShopDisplayjLabel.setText(getTechnics().
//                                          getWorkShop().toString());
//        }
//    		CCEndby leixiao 2010-04-01
        remarkJTextField.setVisible(true);
        numberDisplayJLabel.setVisible(false);
        nameDisplayJLabel.setVisible(false);
        typeDisplayJLabel.setVisible(true);
        remarkDisplayJLabel.setVisible(false);
        folderPanel.setViewState(true);
//		CCBeginby liuzhicheng 2010-02-08 ԭ�������������Ϊ״̬ʱ���ɱ༭��        
//        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);
		//      lifeCycleInfo.getLifeCyclePanel().setLifeCycle((
		//      LifeCycleManagedIfc) getTechnics());
		//CCBeginby leixiao 2009-10-23 ԭ��
//		String defaultlifecycle = RemoteProperty.getProperty(
//				"Ĭ�ϵĹ�����������", "������������");
//		lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle(defaultlifecycle);
//		lifeCycleInfo.getLifeCyclePanel().setEnabled(false);
		//CCEndby leixiao 2009-10-23 ԭ��
//		CCEndby liuzhicheng 2010-02-08
 

        String name = getTechnics().getTechnicsName();
        nameJTextField.setText(name);
        String remark = getTechnics().getRemark();
        remarkJTextField.setText(remark);
        
        //Begin CR3
        
        //Begin CR7
        workStateJLabel.setVisible(true);
        workStateTextField.setVisible(true);
        //End CR7
        //CCBegin SS12
        separableNumber.setVisible(true);
        separableNumberJLabel.setVisible(true);
        separableNumber.setEditable(true);
        separableNumber.setText(getTechnics().getSeparableNumber());
        separableNumberDisplayJLabel.setVisible(false);
        separableName.setVisible(true);
        separableName.setEditable(true);
        separableName.setText(getTechnics().getSeparableName());
        separableNameJLabel.setVisible(true);
        separableNameDisplayJLabel.setVisible(false);
        separableCount.setVisible(true);
        separableCount.setEditable(true);
        separableCount.setText(getTechnics().getSeparableCount());
        separableCountJLabel.setVisible(true);
        separableCountDisplayJLabel.setVisible(false);
        selectPartJButton.setVisible(true);
        selectPartJButton.setEnabled(true);
        String techtype = technicsType.getCodeContent();
	    try {
			if(getUserFromCompany().equals("zczx")&&(techtype.equals("��ݻ��ӹ���")||techtype.equals("����ȴ�����"))){
			    masterJPanel.add(separableJPanel,   new GridBagConstraints(0, 16, 4, 1, 1.0, 0.0
			            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 4, 7), 0, 0));
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS12  
        creatorJLabel.setVisible(false);
        creatorTextField.setVisible(false);
        creatTimeJLabel.setVisible(false);
        creatTimeTextField.setVisible(false);
        moderfilerJLabel.setVisible(false);
        moderfilerTextField.setVisible(false);
        modifyTimeJLabel.setVisible(false);
        modifyTimeTextField.setVisible(false);
        iterationJLabel.setVisible(false);
        iterationTextField.setVisible(false);

        masterJPanel.add(lifeCycleInfo,
	    		 new GridBagConstraints(0, 5, 4, 2, 1.0, 0.0
	    				 ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(5, 15, -4, 15), 0, 0));
        
        masterJPanel.add(remarkJTextField,
                new GridBagConstraints(1, 10, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 20), 0, 0));
        
        masterJPanel.add(numberJTextField,
                new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 8, 5, 15), 0, 0));
        masterJPanel.add(nameJTextField,
                new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0
                		,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 5, 15), 0, 0));
        //End CR3
        

        //����������
        setTechnicsType(getTechnics().getTechnicsType());
        typeDisplayJLabel.setText(technicsType.getCodeContent());

        try
        {
            folderPanel.setLabelText(getPersionalFolder());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setTechnics(getTechnics());
        refreshObject();
//		CCBeginby liuzhicheng 2010-02-08 ԭ�������������Ϊ״̬ʱ���ɱ༭��        
        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);  
		String defaultlifecycle = RemoteProperty.getProperty(
				"Ĭ�ϵĹ�����������", "������������");
				
				//CCBegin SS26
				if(isBSXAutoNum(type))
				{
					defaultlifecycle = RemoteProperty.getProperty("com.faw_qm.lifecycle.QMFawTechnics.bsxLifeCycle", "������(����)��У��������������");
				}
				//CCEnd SS26
				
		lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle(defaultlifecycle);
		lifeCycleInfo.getLifeCyclePanel().setEnabled(false);
//		CCEndby liuzhicheng 2010-02-08 
        setButtonVisible(true);
        //20060202Ѧ��add
        //CCBegin SS6
         if(ts16949)
        	 if(isBSXAutoNum(type)){
       		  consmasterTS16949Panel.setSaveAsMode(getTechnics());
       	     }else{
             masterTS16949Panel.setSaveAsMode(getTechnics());
       	     }
         //CCEnd SS6
        //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
 	    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
 	    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        setComboBox(productStateComboBox, getTechnics().getProductState());
        setComboBox(checkTechTypeComboBox, getTechnics().getCheckTechType());
        
//        //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
//        setUpFileAccessaryName((QMFawTechnicsInfo) getTechnics());
//        this.getUpFilePanel().setAButtonVisable(true);
//        this.getUpFilePanel().setDButtonVisable(true);
//        this.getUpFilePanel().setVButtonVisable(false);
//        this.getUpFilePanel().setDLButtonVisable(true);
//        numberJTextField.setText(getTechnics().getTechnicsNumber());
//        //CCEnd by liunan 2009-10-12
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.setSaveAsMode() end...return is void");
        }
    }


    /**
     * ���õ�ǰѡ��Ĺ��տ�����Ϣ��ʾ�ڽ�����
     * @param info ���տ�����Ϣ
     */
    public void setTechnics(QMFawTechnicsInfo info)
    {
        technicsInfo = info;
    }


    /**
     * ��ù��տ�����Ϣ
     * @return ���տ�����Ϣ
     */
    public QMFawTechnicsInfo getTechnics()
    {
        return technicsInfo;
    }


    /**
     * ���ý���ģʽ�����������»�鿴����
     * @param aMode �½���ģʽ
     * @exception java.beans.PropertyVetoException ���ģʽMode��Ч�����׳��쳣��
     */
    public void setViewMode(int aMode)
            throws PropertyVetoException
    {
    	
    	
    	//CCBegin SS11
    	hideMainPart.setText("");//��ʼ��ʱ���������
    	//CCEnd SS11
    	//CCBegin SS12
    	hideMainPartBsoID.setText("");//��ʼ��ʱ���������
    	//CCEnd SS12	
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setViewMode() begin...");
        }
        jTabbedPane1.setSelectedIndex(0);
        if ((aMode == UPDATE_MODE) ||
            (aMode == CREATE_MODE) ||
            (aMode == VIEW_MODE) ||
            (aMode == SAVEAS_MODE))
        {
            mode = aMode;
        }
        else
        {
            //��Ϣ����Чģʽ
            throw (new PropertyVetoException(QMMessage.
                                             getLocalizedMessage(
                    RESOURCE,
                    "invalid Mode", null),
                                             new PropertyChangeEvent(this,
                    "mode",
                    new Integer(getViewMode()),
                    new Integer(aMode))));
        }

        switch (aMode)
        {

            case CREATE_MODE:
            { //����ģʽ
                setCreateMode();
                break;
            }

            case UPDATE_MODE:
            { //����ģʽ
                setUpdateMode();
                break;
            }

            case VIEW_MODE:
            { //�鿴ģʽ
                setViewMode();
                break;
            }

            case SAVEAS_MODE:
            { //���Ϊģʽ
                setSaveAsMode();
                break;
            }

        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.setViewMode() end...return is void");
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

    void saveJButton_actionPerformed(ActionEvent e)
    {
        WorkThread thread = new WorkThread(6);
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
        else if (getViewMode() == UPDATE_MODE)
        {
            saveWhenUpdate();
        }
        else if (getViewMode() == SAVEAS_MODE)
        {
            saveWhenSaveAs();
        }
        ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();

    }


    /**
     * ���ù��տ����������Ժ͹������������Ϣ��װ����
     * @return  ��Ϣ��װ����
     */
    private CappWrapData commitAllAttributes()
    {
        //���ù��տ�����(��š����ơ����͡���ע�����ϼ�)
        if (getViewMode() == CREATE_MODE ||
            getViewMode() == SAVEAS_MODE)
        {
            technicsInfo.setTechnicsNumber(numberJTextField.
                                           getText().trim());
            technicsInfo.setTechnicsName(nameJTextField.getText().
                                         trim());
        }
        //�������ϼ�
        technicsInfo.setLocation(folderPanel.getFolderLocation());
        //CCBegin SS12
        String techtype = technicsType.getCodeContent();
	    try {
			if(getUserFromCompany().equals("zczx")&&(techtype.equals("��ݻ��ӹ���")||techtype.equals("����ȴ�����"))){
		        if(separableName.getText()!=null){
		            technicsInfo.setSeparableName(separableName.getText());
		        }
		        if(separableNumber.getText()!=null){
		            technicsInfo.setSeparableNumber(separableNumber.getText());	
		        }
		        if(separableCount.getText()!=null){
		            technicsInfo.setSeparableCount(separableCount.getText());  	
		        }
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS12
		
        //���ù�������
        //CCBegin SS5
        //if (getViewMode() == CREATE_MODE)
        if (getViewMode() == CREATE_MODE ||
                getViewMode() == SAVEAS_MODE)
        {
            technicsInfo.setTechnicsType(technicsType);
        }
        //CCEnd SS5
        //�����������ں���Ŀ��
        if (getViewMode() == CREATE_MODE ||
            getViewMode() == SAVEAS_MODE)
        {
            try
            {
                LifeCycleManagedIfc lcm = lifeCycleInfo.assign((
                        LifeCycleManagedIfc) getTechnics());
                setTechnics((QMFawTechnicsInfo) lcm);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
               return null;
            }
        }        
        if (workshopSortingSelectedPanel.isVisible())
        {
            technicsInfo.setWorkShop((BaseValueInfo)
                                     workshopSortingSelectedPanel.
                                     getCoding());
        }
        if (remarkJTextField.getText() != null &&
            remarkJTextField.getText() != "")
        {
            technicsInfo.setRemark(remarkJTextField.getText());
        }
        //ll modify in 20071101 begin
        ArrayList drawingList =null;
        //�Ƽ�����ͼ
        if (drawingpanel != null && drawingpanel.isVisible())
        {
            //technicsInfo.setFlowDrawing(drawingpanel.getDrawingDate());
            if(drawingpanel.getDrawingDate()!=null){
                drawingList = new ArrayList(2);
                PDrawingInfo pdrawing = new PDrawingInfo();
                pdrawing.setDrawingByte((byte[])drawingpanel.getDrawingDate().elementAt(0));//CR2
                pdrawing.setDrawingType((String)drawingpanel.getDrawingDate().elementAt(1));//CR6
                drawingList.add(pdrawing);
            }
        }
        //ll modify in 20071101 end
        //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
	    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
	    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        if(!productStateComboBox.getSelectedItem().equals(""))
        {
        	technicsInfo.setProductState((CodingIfc)productStateComboBox.getSelectedItem());
        }
        if(!checkTechTypeComboBox.getSelectedItem().equals(""))
        {
        	technicsInfo.setCheckTechType((CodingIfc)checkTechTypeComboBox.getSelectedItem());
        }
        
        
        //20070202Ѧ��add
        //CCBegin SS6
        String type=technicsType.getCodeContent();
         if(ts16949)
         {
        	 if(isBSXAutoNum(type)){
        	    if (consmasterTS16949Panel.check())
                {
                    consmasterTS16949Panel.commitAttributes(technicsInfo);
                }
                 else
                {
                    isSave = false;
                    return null;
                }
         }
        	 else{
        		 if (masterTS16949Panel.check())
                 {
                     masterTS16949Panel.commitAttributes(technicsInfo);
                 }
                 else
                 {
                     isSave = false;
                     return null;
                 }
        	 }
         }
         //CCEnd SS6
        if (cappExAttrPanel.check())
        {
            //������չ����
            technicsInfo.setExtendAttributes(cappExAttrPanel.
                                             getExAttr());
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
        Vector docLinks = new Vector();
        Vector partLinks = new Vector();
        Vector materialLinks = new Vector();
        //20080619 xucy add
        Vector docMasterLinks = new Vector();
        //���������Ӻ͸��µĹ���
        try
        {
        	
            docLinks = doclinkJPanel.getAllLinks();
            int size = docLinks.size();
            for (int j = 0; j < size; j++)
            {
                String docId = ((QMTechnicsQMDocumentLinkInfo) docLinks.
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
                        QMTechnicsQMDocumentLinkInfo linkInfo = new
                        QMTechnicsQMDocumentLinkInfo();
                        linkInfo.setRightBsoID(masterID);
                        linkInfo.setLeftBsoID(((
                        		QMTechnicsQMDocumentLinkInfo)
                                               docLinks.elementAt(j)).
                                              getLeftBsoID());
                        docMasterLinks.add(linkInfo);
                    }
                    else
                    if (docInfo instanceof DocMasterInfo)
                    {
                        docMasterLinks.add((QMTechnicsQMDocumentLinkInfo)
                                           docLinks.elementAt(j));
                    }
                    //displayString = getIdentity(relationTechnics);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(),
                                                  ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }

            }     

            partLinks = partLinkJPanel.getAllLinks();
            if (verbose)
            {
                System.out.println(
                        " partLinks.size() = " +
                        partLinks.size());
            }
            materialLinks = materialLinkJPanel.getAllLinks();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            isSave = false;
            return null;
        }
        //���Ϊģʽ,ȥ���־û���Ϣ
        if (mode == SAVEAS_MODE)
        {
            QMTechnicsQMDocumentLinkInfo dlink;
            for (int i = 0; i < docLinks.size(); i++)
            {
                dlink = (
                        QMTechnicsQMDocumentLinkInfo) docLinks.elementAt(i);
                dlink.setBsoID(null);
            }
            QMTechnicsQMMaterialLinkInfo mlink;
            for (int i = 0; i < materialLinks.size(); i++)
            {
                mlink = (
                        QMTechnicsQMMaterialLinkInfo) materialLinks.
                        elementAt(i);
                mlink.setBsoID(null);
            }
            PartUsageQMTechnicsLinkInfo plink;
            for (int i = 0; i < partLinks.size(); i++)
            {
                plink = (
                        PartUsageQMTechnicsLinkInfo) partLinks.elementAt(i);
                plink.setBsoID(null);
            }
        }
        //���ĵ������Ͳ��Ϲ����ϲ�
        Vector resourceLinks = new Vector();
        for (int i = 0; i < docMasterLinks.size(); i++)
        {
            resourceLinks.addElement(docMasterLinks.elementAt(i));
        }
        for (int j = 0; j < materialLinks.size(); j++)
        {
            resourceLinks.addElement(materialLinks.elementAt(j));
        }

        //��װ������Ϣ
        CappWrapData cappWrapData = new CappWrapData();
        cappWrapData.setQMTehnicsIfc(technicsInfo);
        cappWrapData.setPartUsageQMTechnics(partLinks);
        cappWrapData.setQMTechnicsUsageResource(resourceLinks);
        //ll add in 20071101 begin
        cappWrapData.setUpdateDrawing(drawingList);
         //ll add in 20071101 end
        return cappWrapData;
    }
  //CCBegin SS4
    /**
     * ��ù����㲿��
     * @return
     * 
     * PartUsageTechLinkJPanel
     */
   public PartUsageTechLinkJPanel getPartPanel()
   {
       return partLinkJPanel;
   }
 //CCEnd SS4
   //CCBegin SS22
   
   /**
    * ��ù�������
    * @return
    * 
    * PartUsageTechLinkJPanel
    */
  public TechUsageMaterialLinkJPanel  getMaterialPanel()
  {
      return materialLinkJPanel;
  }
   
   //CCEnd SS22
    /**
     * �������տ�ʱ�������½��Ĺ��տ�
     */
    private void saveWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.saveWhenCreate() begin...");
        }
        setButtonWhenSave(false);
        //�����жϱ��������Ƿ�����
        boolean requiredFieldsFilled;
        //���������״Ϊ�ȴ�״̬
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //�����������Ƿ�����
        requiredFieldsFilled = checkRequiredFields();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //���ù��տ����������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAllAttributes();

        /*ProgressService.setProgressText(QMMessage.getLocalizedMessage(
                RESOURCE, CappLMRB.SAVING, null));
                 ProgressService.showProgress();*/

        if (cappWrapData == null)
        {
            //ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
        //��ø�����Ϣ
        ArrayList arrayList = getArrayList();        
        //��ʾ�������
        ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
        //ԭ���淽�����ĳ���Ӹ����ı��淽����
        /*Class[] paraClass =
                {
                CappWrapData.class};
        Object[] obj =
                {
                cappWrapData};*/
        Class[] paraClass ={CappWrapData.class, ArrayList.class};
        Object[] obj ={cappWrapData, arrayList};
        //CCEnd by liunan 2009-10-12

            QMFawTechnicsInfo returnTechnics = null;

            try
            {
                returnTechnics = (QMFawTechnicsInfo) useServiceMethod(
                        "StandardCappService", "createQMTechnics", paraClass,
                        obj);

            }
            catch (QMRemoteException ex)
            {
            	
            	((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();//CR4
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                //CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3��
                ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
                //CCEnd by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3��
                //ProgressService.hideProgress();
                setCursor(Cursor.getDefaultCursor());
                setButtonWhenSave(true);
                isSave = false;    
                return;
            }
       ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
        TechnicsTreeObject treeObject = new TechnicsTreeObject(
                returnTechnics);
        //��treeObject�ҵ���������
        ((TechnicsRegulationsMainJFrame) parentJFrame).
                getProcessTreePanel().addProcess(treeObject);
        //���ؽ�����
        //ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        //���ر����ȡ����ť
        setVisible(false);
        //���ý���
        ((TechnicsRegulationsMainJFrame) parentJFrame).
                getProcessTreePanel().setNodeSelected(treeObject);
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.saveWhenCreate() end...return is true");
        }
    }


    /**
     * ���¹��տ�ʱ��������º�Ĺ��տ�
     * ���ɸ������ϼУ�����ͨ�����Ĵ��λ�ã��ı乤�չ�̴�ŵ����ϼ�
     */
    private void saveWhenUpdate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.saveWhenUpdate() begin...");

        }
        setButtonWhenSave(false);
        //�����������Ƿ�����
        boolean requiredFieldsFilled = checkRequiredFields();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
//      CCBeginby leixiao 2010-4-2 ԭ�򣺹��ղ��ſ��޸�,�޸Ĺ��ղ���ʱ�޸��乤��Ĳ���
        boolean changworkshop = checkChangeWorkshop(); 
//      CCEndby leixiao 2010-4-2 ԭ�򣺹��ղ��ſɸ���
        //���ù��տ����������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAllAttributes();
        if (cappWrapData == null)
        {
            setButtonWhenSave(true);
            return;
        }
        //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
        ArrayList arrayList = getArrayListupdate();
        Collection deleteContentCol = (Collection)getUpFilePanel().
		                            getDeleteContentMap().values();
		    Collection vec = new Vector(deleteContentCol);
		    getUpFilePanel().getDeleteContentMap().clear();  
			    
        //���������״Ϊ�ȴ�״̬
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //��ʾ�������
        /* ProgressService.setProgressText(QMMessage.getLocalizedMessage(
                 RESOURCE, CappLMRB.SAVING, null));
         ProgressService.showProgress();
         */
        ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
        //ԭ���ձ��淽�����ĳɱ��渽���ķ�����
        /*Class[] paraClass =
                {
                CappWrapData.class};
        Object[] obj =
                {
                cappWrapData};*/
//      CCBeginby leixiao 2010-4-2 ԭ�򣺹��ղ��ſ��޸�,�޸Ĺ��ղ���ʱ�޸��乤��Ĳ���
        Class[] paraClass ={CappWrapData.class, ArrayList.class, Vector.class,boolean.class};
        Object[] obj ={cappWrapData, arrayList, vec,new Boolean(changworkshop)};
//      CCEndby leixiao 2010-4-2 ԭ�򣺹��ղ��ſ��޸�,�޸Ĺ��ղ���ʱ�޸��乤��Ĳ���
        //CCEnd by liunan 2009-10-12
        QMFawTechnicsInfo returnTechnics;
        try
        {
            returnTechnics = (QMFawTechnicsInfo) useServiceMethod(
                    "StandardCappService", "updateQMTechnics", paraClass,
                    obj);
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
            setVisible(false);
            isSave = false;
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            return;
        }
        ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
        TechnicsTreeObject treeObject = new TechnicsTreeObject(
                returnTechnics);
        //��treeObject�ҵ���������,ˢ�¹�����
        ((TechnicsRegulationsMainJFrame) parentJFrame).
                getProcessTreePanel().updateNode(
                treeObject);

        //���ؽ�����
        // ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);

        technicsInfo = returnTechnics;
        try
        {
            setViewMode(VIEW_MODE);
        }
        catch (PropertyVetoException ex1)
        {
            ex1.printStackTrace();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.saveWhenUpdate() end...return is true");

        }
    }

//  CCBeginby leixiao 2010-4-2 ԭ�򣺹��ղ��ſ��޸�,�޸Ĺ��ղ���ʱ�޸��乤��Ĳ���
    private boolean checkChangeWorkshop() {
    	boolean changworkshop=false;
        if (workshopSortingSelectedPanel.isVisible())
        {
        	if(technicsInfo.getWorkShop()!=(BaseValueInfo)workshopSortingSelectedPanel.getCoding()){
        		System.out.println("------��λ�б䶯");
            changworkshop=true;
        	}
        }
		return changworkshop;
	}
//  CCEndby leixiao 2010-4-2 ԭ�򣺹��ղ��ſ��޸�,�޸Ĺ��ղ���ʱ�޸��乤��Ĳ���



	/**
     * ���տ����Ϊ
     */
    private void saveWhenSaveAs()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.saveWhenSaveAs() begin...");

        }
        setButtonWhenSave(false);
        //�����жϱ��������Ƿ�����
        boolean requiredFieldsFilled;
        //���������״Ϊ�ȴ�״̬
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //�����������Ƿ�����
        requiredFieldsFilled = checkRequiredFields();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //���Ϊ��Ĺ��տ�
        try
        {
            QMFawTechnicsInfo saveasInfo = new QMFawTechnicsInfo();
            setTechnics(saveasInfo);
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
            return;
        }
        //�蹤������
        getTechnics().setTechnicsType(existTechnicsInfo.
                                      getTechnicsType());
        if (!workshopSortingSelectedPanel.isVisible())
        {
            getTechnics().setWorkShop(existTechnicsInfo.
                                          getWorkShop());
        }
        //���ù��տ����������Ժ͹������������Ϣ��װ����
        CappWrapData cappWrapData = commitAllAttributes();
        if (cappWrapData == null)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            return;
        }
        //��ʾ�������
        //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
        //����¼Ӹ�����ApplicationDataInfo���ϡ�
        ArrayList arrayList = getArrayListupdate();
        //���ԭ������ӵ�еĸ���ApplicationDataInfo���ϡ�
		    Vector vec = getVectorSaveAs();
		    getUpFilePanel().getDeleteContentMap().clear();
        ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();
        //���÷��񣬱��湤�տ�
        //ԭ���ձ��淽�����ĳɱ��渽���ķ�����
        /*Class[] paraClass =
                {
                CappWrapData.class, QMTechnicsIfc.class};
        Object[] obj =
                {
                cappWrapData, existTechnicsInfo};*/
        Class[] paraClass ={CappWrapData.class, QMTechnicsIfc.class, ArrayList.class, Vector.class};
        Object[] obj ={cappWrapData, existTechnicsInfo, arrayList, vec};
        //CCEnd by liunan 2009-10-12
        QMFawTechnicsInfo returnTechnics=null;
        try
        {
           // for(int i=381;i<400;i++)
           // {
              //  cappWrapData.getQMTechnicsIfc().setTechnicsNumber(technicsType.getCodeContent()+i);
            returnTechnics = (QMFawTechnicsInfo) useServiceMethod(
                    "StandardCappService", "saveAsTechnics", paraClass,
                    obj);
          // }
        }
        catch (QMRemoteException ex)
        {
            if(verbose)
            ex.printStackTrace();
             ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            setTechnics((QMFawTechnicsInfo)existTechnicsInfo);
            //   ProgressService.hideProgress();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            return;
        }
        ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
        //ˢ�¾ɽڵ�
        //((TechnicsRegulationsMainJFrame) parentJFrame).updateNode(
        //      existTechnicsInfo, null);
        //���½ڵ�
        TechnicsTreeObject treeObject = new TechnicsTreeObject(
                returnTechnics);
        ((TechnicsRegulationsMainJFrame) parentJFrame).
                getProcessTreePanel().addProcess(treeObject);
        //���ؽ�����
        // ProgressService.hideProgress();
        setCursor(Cursor.getDefaultCursor());
        setButtonWhenSave(true);
        setVisible(false);
        ((TechnicsRegulationsMainJFrame) parentJFrame).
                getProcessTreePanel().setNodeSelected(treeObject);
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.saveWhenSaveAs() end...return is void");
        }
    }


    /**
     * ȡ����ť�Ķ����¼�����
     * <p>���û�¼�����Ϣɾ��</p>
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
        else if (getViewMode() == SAVEAS_MODE)
        {
            cancelWhenSaveAs();
        }

    }


    /**
     * ����ģʽ�£�ȡ����ť��ִ�з���
     * <p>���Ѿ�¼������ݶ��ÿ�</p>
     */
    private void cancelWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.CancelWhenCreate() begin...");
        }
        numberJTextField.setText("");
        nameJTextField.setText("");
        remarkJTextField.setText("");
        //folderPanel.setTextFieldNull();//CR5
        //20060814Ѧ���޸ģ����ĵ�����������Ϲ������
        doclinkJPanel.clear();
        partLinkJPanel.clear();
        materialLinkJPanel.clear();
        //20070202Ѧ��add
        //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
	    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
	    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        this.productStateComboBox.setSelectedIndex(0);
        this.checkTechTypeComboBox.setSelectedIndex(0);
        
        //CCBegin SS6
        String type=technicsType.getCodeContent();
         if(ts16949){
        	 if(isBSXAutoNum(type)){
                 this.consmasterTS16949Panel.clear();
        	 }else{
        		 this.masterTS16949Panel.clear();
        	 }
         }
         //CCEnd SS6
        workshopSortingSelectedPanel.setCoding(null);//begin CR5
        lifeCycleInfo.clear();
        lifeCycleInfo.getLifeCyclePanel().setLifeCycle(null);
        lifeCycleInfo.getProjectPanel().setObject(null);
        try
		{
			folderPanel.setLabelText(getPersionalFolder());
		}
		catch (QMRemoteException e)
		{
			e.printStackTrace();
		}//end CR5
        
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.CancelWhenCreate() end...return is void");
        }
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
     * ���Ϊģʽ�£�ȡ����ť��ִ�з���
     * <p>������¼������ݳ����������ϴ�¼�������</p>
     */
    private void cancelWhenSaveAs()
    {
        setSaveAsMode();
    }


    /**
     * �˳���ť�Ķ��������¼�����
     * @param e
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();
    }


    /**
     * �˳�
     * @return boolean �Ƿ�ִ�����˳�
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
        else if (getViewMode() == SAVEAS_MODE)
        {
            quitWhenSaveAs();
        }
        else if (getViewMode() == VIEW_MODE)
        {
            quitWhenView();
        }
        return isSave;
    }


    /**
     * ����ģʽ�£��˳���ť��ִ�з���
     */
    private void quitWhenCreate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.QuitWhenCreate() begin...");
        }
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.IS_SAVE_QMTECHNICS, null);
        if (confirmAction(s))
        {
            saveWhenCreate();
        }
        else
        {
            setVisible(false);
            isSave = true;
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.QuitWhenCreate() end...return is void");

        }
    }


    /**
     * ����ģʽ�£��˳���ť��ִ�з���
     */
    private void quitWhenUpdate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.QuitWhenUpdate() begin...");
        }
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.
                                                 IS_SAVE_QMTECHNICS_UPDATE, null);
        if (confirmAction(s))
        {
            saveWhenUpdate();
        }
        else
        {
            setVisible(false);
            isSave = true;
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.QuitWhenUpdate() end...return is void");
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
     * ���Ϊģʽ�£��˳���ť��ִ�з���
     */
    private void quitWhenSaveAs()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.QuitWhenSaveAs() begin...");
        }
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.IS_SAVE_QMTECHNICS, null);
        if (confirmAction(s))
        {
            saveWhenSaveAs();
        }
        else
        {
            setVisible(false);
            isSave = true;
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.QuitWhenSaveAs() end...return is void");

        }
    }


    /**
     * ��ʾȷ�϶Ի���
     * @param s �ڶԻ�������ʾ����Ϣ
     * @return  ����û�ѡ���ˡ�ȷ������ť���򷵻�true;���򷵻�false
     */
    private boolean confirmAction(String s)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE,
                "information", null);
//        JOptionPane okCancelPane = new JOptionPane();
        return JOptionPane.showConfirmDialog(getParentJFrame(),
                                              s, title,
                                              JOptionPane.YES_NO_OPTION) ==
                JOptionPane.YES_OPTION;
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
        //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
        //this.getUpFilePanel().setVButtonVisable(flag);
        this.getUpFilePanel().setDLButtonVisable(flag);
        //CCEnd by liunan 2009-10-12

    }


    /**
     * ���ð�ť�Ŀɼ���
     * @param flag
     */
    private void setButtonVisible(boolean flag)
    {
        saveJButton.setVisible(flag);
        cancelJButton.setVisible(flag);
    }


    /**
     * ������������Ƿ�������Чֵ
     * @return  �����������������Чֵ���򷵻�Ϊ��
     */
    private boolean checkRequiredFields()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.checkRequiredFields() begin...");
        }
        boolean isOK = true;
        String message = null;
        String title = "";
        if ((getViewMode() == CREATE_MODE) ||
            getViewMode() == SAVEAS_MODE)
        {
//          CCBeginby leixiao 2009-7-30 ԭ��ȥ���Ա�����������ַ�������
          //CCBegin by liuzc 2009-12-19 ԭ�򣺹��ձ�Ų���Ϊ�գ��μ�DefectID=2658
          isOK = numberJTextField.check();
          if (isOK)
          {
              isOK = nameJTextField.check();

          }
//        CCEndby leixiao 2009-7-30 ԭ��ȥ���Ա�������ַ�������
        //CCBegin by liunan 2009-09-17 ������Ҫ�������Ƿ�Ϊ�ս��м��顣
        if(nameJTextField.getText().trim().equals(""))
        {
          isOK = false;
          message="�������Ʋ���Ϊ�գ�";
          return false;
        }
//        else
//      	  isOK=true;
        //CCEnd by liuzc 2009-12-19 ԭ�򣺹��ձ�Ų���Ϊ�գ��μ�DefectID=2658
      	//CCEnd by liunan 2009-09-17

        }
        if (isOK)
        {
            isOK = remarkJTextField.check();
        }

        //���鲿���Ƿ�Ϊ��
        //CCBegin SS2
        //if (isOK && workshopSortingSelectedPanel.isShowing() &&
//        if (isOK &&
        //CCBegin SS7
//        //CCEnd SS2
//            workshopSortingSelectedPanel.getCoding() == null)
     
        if (isOK && workshopSortingSelectedPanel.isShowing() &&
                workshopSortingSelectedPanel.getCoding() == null)
       //CCEnd SS7
        {
            message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.NO_WORKSHOP_ENTERED,
                    null);
            isOK = false;
            workshopSortingSelectedPanel.getJButton().
                    grabFocus();
        }

        //�������ϼ��Ƿ�Ϊ��
        else if (isOK && !checkFolderLocation())
        {
            message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.NO_LOCATION_ENTERED,
                    null);
            isOK = false;
            folderPanel.grabFocus();
        }

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
                                          JOptionPane.
                                          INFORMATION_MESSAGE);
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsMasterJPanel.checkRequiredFields() end...return: " +
                               isOK);
        }
        return isOK;

    }
    

    /**
     * �����Ƿ���ָ�����ϼ�
     * @return �����ָ�����ϼ�·�����򷵻����ϼС�
     * @throws QMRemoteException
     */
    private boolean checkFolderLocation()

    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.checkFolderLocation() begin...");
        }
        String location = "";
        //������ϼ�·��
        location = folderPanel.getFolderLocation();
        if (location == null || location.length() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }


    /**
     * �Ѹ�����ҵ�������ӵ���Ӧ�Ĺ����б���
     * @param info ������ҵ������豸����װ�����ϡ���ͼ������ȣ�
     */
    public void addObjectToTable(BaseValueInfo[] info)
    {
        if (info[0] instanceof QMMaterialInfo)
        {
            materialLinkJPanel.addMaterialToTable((
                    QMMaterialInfo) info[0]);
        }
        else if (info[0] instanceof QMTermInfo)
        {
            String termName = ((QMTermInfo) info[0]).getTermName();
            nameJTextField.setInsertText(termName);
            remarkJTextField.setInsertText(termName);
        }
        else if (info[0] instanceof QMPartInfo)
        {
            for (int i = 0; i < info.length; i++)
            {
                partLinkJPanel.addPartToTable((QMPartInfo) info[i]);
            }
        }
    }


    /**
     * ��õ�ǰ�û��ĸ������ϼ�λ��
     * @return ��ǰ�û��ĸ������ϼ�λ��
     * @throws QMRemoteException
     */
    public String getPersionalFolder()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.getPersionalFolder() begin...");
        }
        Class[] c =
                {};
        Object[] objs =
                {};
        UserInfo user = (UserInfo) useServiceMethod(
                "SessionService", "getCurUserInfo", c, objs);
        Class[] c1 =
                {
                UserInfo.class};
        Object[] objs1 =
                {
                user};
        SubFolderInfo folder = (SubFolderInfo) useServiceMethod(
                "FolderService", "getPersonalFolder", c1, objs1);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMasterJPanel.getPersionalFolder() end...return: "
                    + folder.getPath());
        }
        return folder.getPath();
    }

    //add by wangh on 20070518(�õ���Ʒ״̬�Ĵ������ĵ�һ��ֵ)
//    public String getFirstCoding()
//    throws QMRemoteException
//    {
//    	Strin code = technicsType.getCodeContent();
//
//    	return code;
//    }


    /**
     * ���
     */

    public void clear()
    {
        if (firstInFlag)
        {
            firstInFlag = false;
            return;
        }
        numberJTextField.setText("");
        nameJTextField.setText("");
        remarkJTextField.setText("");
        remarkDisplayJLabel.setText("");
        numberDisplayJLabel.setText("");
        nameDisplayJLabel.setText("");
        typeDisplayJLabel.setText("");
        workshopSortingSelectedPanel.setCoding(null);
        workShopDisplayjLabel.setText("");
        folderPanel.setLabelText("");
        doclinkJPanel.clear();
        //CCBegin SS12
        separableNumber.setText("");
        separableName.setText("");
        separableCount.setText("");
        masterJPanel.remove(separableJPanel);
        //CCEnd SS12
        if (partLinkJPanel != null)
        {
            partLinkJPanel.clear();
        }
        if (materialLinkJPanel != null)
        {
            materialLinkJPanel.clear();
        }
        lifeCycleInfo.clear();
        lifeCycleInfo.getLifeCyclePanel().setLifeCycle(null);
        lifeCycleInfo.getProjectPanel().setObject(null);
        //20070202Ѧ��add
      //CCBegin SS6
        String type=technicsType.getCodeContent();
        if(ts16949){
       	 if(isBSXAutoNum(type)){
               this.consmasterTS16949Panel.clear();
       	 }else{
       		   this.masterTS16949Panel.clear();
       	 }
        }
        //CCEnd SS6
        isSave = true;
        //���⣨3�� 20080811  �촺Ӣ�޸�  �޸�ԭ����õġ�����������ݽṹΪ���ʱ��Ĭ�ϵ�һ��ṹΪչ��״̬��
	    //Ȼ��˫����һ��ṹ�ڵ��չ���ڶ���ṹ
	    //ֻ��һ���˵���ѡ�������Ŀ����Ϊ�����б�ʽ���
        productStateComboBox.setSelectedIndex(0);
        checkTechTypeComboBox.setSelectedIndex(0);
        
        productStateDisplJLabel.setText("");
        checkTechTypeDisplJLabel.setText("");
        //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
        this.getUpFilePanel().getMultiList().clear();
        //CCEnd by liunan 2009-10-12

    }


    /**
     * ���ð�ť�ɼ�
     * @param flag boolean
     */
    public void setAllButonVisible(boolean flag)
    {
        if (!flag)
        {
            saveJButton.setVisible(false);
            quitJButton.setVisible(false);
            quitJButton.setVisible(false);
        }
    }


    /**
     *���ݹ���������������и����齨
     */
    public void refreshObject()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMaster.refreshObject bedin ");
        }
        String processType = technicsType.getCodeContent().trim();
        newCappExAttrPanel(processType);

        //�ı��㲿��ʹ�ù��տ�panel
        newPartLinkPanel(processType);
        //���Ĳ��Ϲ������
        newMaterialLinkJPanel(processType);
        //�����Ƽ�����ͼ���
        newDrawingpanel(processType);
        existTechnicsType = processType;

        /*
                    //�ı��㲿��ʹ�ù��տ�panel����Ϲ������
                    WorkThread partAndMaththread = new WorkThread(0);
                    partAndMaththread.start();
                   //������չ�������
                    WorkThread extthread = new WorkThread(2);
                    extthread.start();
                    //�����Ƽ�����ͼ���
                    WorkThread drawthread = new WorkThread(3);
                    drawthread.start();
         */

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsMaster.refreshObject end");
        }
    }


    /**
     * ʵ�����������
     * @param technicsType String ��������
     */
    private void newMaterialLinkJPanel(String technicsType)
    {
        if (!technicsType.equals(existTechnicsType))
        {
            relationsJTabbedPane.remove(materialLinkJPanel);
            materialLinkJPanel = (TechUsageMaterialLinkJPanel)
                                 materialLinkTable.get(technicsType);
            if (materialLinkJPanel == null)
            {
                materialLinkJPanel = new TechUsageMaterialLinkJPanel(
                        technicsType);
                materialLinkTable.put(technicsType, materialLinkJPanel);
                ((PartUsageTechLinkJPanel) partLinkTable.get(technicsType)).
                        setMaterialLinkJPanel(materialLinkJPanel);

            }
        }
        materialLinkJPanel.clear();
        materialLinkJPanel.setTechnics(getTechnics());
        //materialLinkJPanel.setLinks(data.getQMTechnicsUsageResource());
        if (mode == VIEW_MODE)
        {
            materialLinkJPanel.setMode("View");
        }
        else
        {
            materialLinkJPanel.setMode("Edit");
        }
        if (!technicsType.equals(existTechnicsType))
        {
        
        	//guoguo
//              relationsJTabbedPane.add(materialLinkJPanel,
//                                     QMMessage.
//                                     getLocalizedMessage(RESOURCE,
//                    "materialTitle", null), 2);
            //guoguo
        }

    }


    /**
     * ����¼��Ĺ��տ����࣬������ͬ������������
     * @param technicsType String ��������
     */
    public void newPartLinkPanel(String technicsType)
    {
        if (!technicsType.equals(existTechnicsType))
        {
            relationsJTabbedPane.remove(partLinkJPanel);
            partLinkJPanel = (PartUsageTechLinkJPanel) partLinkTable.get(
                    technicsType);

            if (partLinkJPanel == null)
            {
                //CCBegin SS3
                if(isBSXAutoNum(technicsType))
                {
                    partLinkJPanel = new PartUsageTechLinkJPanel(technicsType);
                    
                }
                else
                {
                  
                	
//              CCBeginby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����
                partLinkJPanel = new PartUsageTechLinkJPanel(this,technicsType);
//              CCEndby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����
                
                //CCBegin guoguo
                System.out.println("technicsType=========mmm==========="+technicsType);
                partLinkJPanel.setCoding(this.technicsType);
                //CCEnd guoguo
                
                
                }
                //CCEnd SS3
                partLinkTable.put(technicsType, partLinkJPanel);
                //�Ӵ˼�����ԭ�򣺵��㲿��������������Ҫ�㲿��,������Ϲ������
                partLinkJPanel.addListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        materialLinkJPanel.
                                actionPerformd_whenAddedMajorPark(e);
                        
                    }
                });

            }
        }

        partLinkJPanel.clear();
        partLinkJPanel.setTechnics(getTechnics());
        //partLinkJPanel.setLinks(data.getPartUsageQMTehnics());
        if (mode == VIEW_MODE)
        {
            partLinkJPanel.setMode("View");
        }
        else
        {
            partLinkJPanel.setMode("Edit");
        }
        if (!technicsType.equals(existTechnicsType))
        {
            relationsJTabbedPane.add(partLinkJPanel,
                                     QMMessage.
                                     getLocalizedMessage(RESOURCE,
                    "partTitle", null), 1);
        }

    }


    /**
     * ����¼��Ĺ��տ����࣬������ͬ����չ����
     * @param technicsType String ���տ�����
     */
    private void newCappExAttrPanel(String technicsType)
    {
//CCBegin by liuzhicheng 2010-01-15 ԭ�򣺹��տ�����������Ϣ���鿴ʱ�������������á�
//        if (!technicsType.equals(existTechnicsType))
//        {
//CCBegin by liuzhicheng 2010-01-15 ԭ�򣺹��տ�����������Ϣ���鿴ʱ�������������á�
            if (cappExAttrPanel != null)
            {
                extendJPanel.remove(cappExAttrPanel);
            }
//CCBegin by liuzhicheng 2010-01-15 ԭ�򣺹��տ�����������Ϣ���鿴ʱ�������������á�
//            if (extendTable.get(technicsType) == null)
//            {
//CCBegin by liuzhicheng 2010-01-15 ԭ�򣺹��տ�����������Ϣ���鿴ʱ�������������á�
                try
                {
                    cappExAttrPanel = new CappExAttrPanel("QMFawTechnics",
                            technicsType, 1);
                }
                catch (QMException ex)
                {
                    ex.printStackTrace();
                }
//CCBegin by liuzhicheng 2010-01-15 ԭ�򣺹��տ�����������Ϣ���鿴ʱ�������������á�                
//                extendTable.put(technicsType, cappExAttrPanel);
//            }
//            else
//            {
//                cappExAttrPanel = (CappExAttrPanel) extendTable.get(
//                        technicsType);
//
//            }
//        }
//CCBegin by liuzhicheng 2010-01-15 ԭ�򣺹��տ�����������Ϣ���鿴ʱ�������������á�                
        cappExAttrPanel.clear();
        if (mode == CREATE_MODE ||
            mode == UPDATE_MODE || mode == SAVEAS_MODE)
        {
            cappExAttrPanel.setModel(CappExAttrPanel.EDIT_MODEL);
        }
        else
        {
            cappExAttrPanel.setModel(CappExAttrPanel.VIEW_MODEL);

        }
        if (mode != CREATE_MODE)
        {
            cappExAttrPanel.show(getTechnics());
        }
        extendJPanel.add(cappExAttrPanel,
                         new GridBagConstraints(0, 0, 1, 1, 1.0,
                                                1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
        repaint();
    }


    public int getMode()
    {
        return mode;
    }


    /**
     * ���ù�������
     * @param type CodingIfc ��������
     */
    public void setTechnicsType(CodingIfc type)
    {
        technicsType = type;
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
     * ���ݹ����������»���Ƽ�����ͼpanel
     * @param technicsType String ��������
     */
    private void newDrawingpanel(String technicsType)
    {
        String has = RemoteProperty.getProperty("�Ƽ�����ͼ" +
                                                technicsType, "false").
                     trim();
        //�˹�����������Ƽ�����ͼ
        if (has.equals("true"))
        {
            //�Ƽ�����ͼpanle��δ����ʼ��,�����ʼ��
            if (drawingpanel == null)
            {
                drawingpanel = new DrawingPanel(true);
                drawingpanel.setEditButtonMnemonic('E', "E");
                drawingpanel.setEditButtonSize(89, 23);
                drawingpanel.setDeleteButtonMnemonic('T');
                drawingpanel.setDeleteButtonSize(89, 23);
                drawingpanel.setViewButtonMnemonic('K');
                drawingpanel.setViewButtonSize(89, 23);

                masterJPanel.add(drawingpanel,
                                 new
                                 GridBagConstraints(1, 8, 5, 1,
                        1.0, 0.0
                        , GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        new
                        Insets(5, 0, 10, 15),
                        0, 0));
            }
            //���Ƽ�����ͼpanel��ʾ
            drawingpanel.setVisible(true);
            if (mode == VIEW_MODE)
            {
                drawingpanel.setModel(DrawingPanel.VIEW);
            }
            else
            {
                drawingpanel.setModel(DrawingPanel.EDIT);
            }
            if (mode != CREATE_MODE)
            {
              // ll modify in 20071101 begin
                //drawingpanel.setDrawingDate(getTechnics().
                                           // getFlowDrawing());
             try{
                 drawingpanel.setDrawingDate(getTechnics().getCappFlowing());
             }catch(QMException e){
                 e.printStackTrace();
             }
              // ll modify in 20071101 end
            }
            else
            {
                drawingpanel.setDrawingDate(null);
            }
            flowDrawingDisp.setVisible(true);
        }
        //�˹������಻�����Ƽ�����ͼ,�ڽ����в���ʾ�Ƽ�����ͼpanel
        else
        {
            if (drawingpanel != null &&
                drawingpanel.getParent() == masterJPanel)
            {
                drawingpanel.setVisible(false);
            }
            flowDrawingDisp.setVisible(false);
        }
    }

    
    /**
     * ����ѡ��ĵ�λ
     * @param comboBox �б��
     * @param coding ������
     * ���⣨1�� 20080812 xucy �޸�
     */
    public void setComboBox(JComboBox box, BaseValueIfc coding)
    {
        int j = box.getItemCount();
        BaseValueIfc temp = null;
        for (int i = 0; i < j; i++)
        {
        	//���⣨1�� 20080812 xucy �޸�
        	if(!box.getItemAt(i).equals(""))
        	{
        	    temp = (BaseValueIfc) box.getItemAt(i);
                if (coding != null && coding.getBsoID().equals(temp.getBsoID()))
                {
                    box.setSelectedIndex(i);
                    break;
                }
        	}
        	
        }
    }

 

    /**
     * <p>Title:�����߳� </p>
     * <p>Description: �ڲ���,���߳�����Ҫ�������,�����̵߳�Ŀ����ʹ�����
     * ��������н���,����߹���Ч��</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: һ������</p>
     * @author not Ѧ��
     * @version 1.0
     */
    class WorkThread extends Thread
    {
        /**
         * ������ʶ
         */
        int workaction;


        public WorkThread(int action)
        {
            workaction = action;
        }

        public void run()
        {
            switch (workaction)
            {
                //�������������壬���Ϲ������
                case 0:
                    newPartLinkPanel(technicsType.getCodeContent().trim());
                    newMaterialLinkJPanel(technicsType.getCodeContent().trim());
                    break;
                    //������չ�������
                case 2:
                    newCappExAttrPanel(technicsType.getCodeContent().trim());
                    break;

                    //�����Ƽ�����ͼ���
                case 3:
                    newDrawingpanel(technicsType.getCodeContent().trim());
                    break;
                    //���������������
                case 4:
                    lifeCycleInfo.getLifeCyclePanel().setLifeCycle((
                            LifeCycleManagedIfc) getTechnics());
                    lifeCycleInfo.getProjectPanel().setObject((
                            LifeCycleManagedIfc) getTechnics());
                    break;
                case 5:
                    refreshObject();
                    break;
                case 6:
                    save();
                    break;
                default:
                    break;
            }
        }
    }
    // Begin CR1
	/**��ô�����
	 * @param s  ����bsoid
	 * @return   ���ش�����
	 */
	private String getCreator(String s) 
	{
		String creator = "";
		try {
			RequestServer server = RequestServerFactory.getRequestServer();
			ServiceRequestInfo info1 = new ServiceRequestInfo();
			info1.setServiceName("PersistService");
			info1.setMethodName("refreshInfo");
			Class[] theClass = { String.class };
			Object[] obj = { s };
			info1.setParaClasses(theClass);
			info1.setParaValues(obj);
			UserIfc ifc = (UserIfc) server.request(info1);
			creator = ifc.getUsersDesc();
		} catch (QMRemoteException ex) {
			ex.printStackTrace();
		}

		return creator;
	}
	//End CR1
	
	
	 //CCBegin by liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
    /**
     * ������еĸ�����Ϣ
     */
    private ArrayList getArrayList() 
    {
	    /**������ֵ����*/
	    ApplicationDataInfo applicationData = null;
	    ArrayList arrayList = new ArrayList();
      int j = getUpFilePanel().getMultiList().getTable().getRowCount();
	  //CCBegin SS10
	  if(fileVaultUsed)
	  {
		  ContentClientHelper helper = new ContentClientHelper();
	      for (int i = 0; i < j; i++)
	      {
	    	  String path = (String)getUpFilePanel().getMultiList().getCellText(i, 1);
	    	  try 
	    	  {
				applicationData =  helper.requestUpload(new File(path));
	    	  } catch (QMException e) 
	    	  {
				e.printStackTrace();
	    	  }
	    	  arrayList.add(applicationData);
	      }
	  }
	  else
	  {
	      for (int i = 0; i < j; i++)
	      {
	    	//����(3)2007.01.17 �촺Ӣ�޸�  �޸�ԭ��:�ֽ���û�б��浽���ݿ�,
	          //�����ά������ApplicationDataInfo������ֽ���.
	          Object[] object = new Object[2];
	          String fileName = (String)getUpFilePanel().
	                            getMultiList().getCellText(i, 0);
	          String path = (String)getUpFilePanel().
	                        getMultiList().
	                        getCellText(i, 1);
	          String length = (String) getUpFilePanel().
	                          getMultiList().
	                          getCellText(i, 2);
	          applicationData = new ApplicationDataInfo();
	          applicationData.setFileName(fileName);
	          applicationData.setUploadPath(path);
	          applicationData.setFileSize(Long.parseLong(length));
	          //����(3)2007.01.17 �촺Ӣ�޸� ����ļ��ֽ���
	          byte[] byteStream = getFileByte(path);
	          object[0] = applicationData;
	          object[1] = byteStream;
	          //����(3)2007.01.17 �촺Ӣ�޸�  ��object����ŵ�arrayList1��,���ͻ��˱�����
	          arrayList.add(object);
	      }
	  }
	  //CCEnd SS10
      return arrayList;
    }
    
    /**
     * ��ø���ʱ���еĸ�����Ϣ�����������ĸ�����
     */
    private ArrayList getArrayListupdate() 
    {
	    /**������ֵ����*/
	    ApplicationDataInfo applicationData = null;

        int j = getUpFilePanel().getMultiList().getTable().
                getRowCount();
        ArrayList arrayList1 = new ArrayList();
        //CCBegin SS10
    	if(fileVaultUsed)
   	  	{
    		ContentClientHelper helper = new ContentClientHelper();
    		for (int i = 0; i < j; i++)
            {
                String appDataID = (String) getUpFilePanel().
                                   getMultiList().getCellText(i, 3);
                if (appDataID == null)
                {
                    String path = (String) getUpFilePanel().
                                  getMultiList().
                                  getCellText(i, 1);
                    try 
      	    	  	{
	      				applicationData =  helper.requestUpload(new File(path));
	      	    	} catch (QMException e) 
	      	    	{
	      	    		e.printStackTrace();
	      	    	}
	      	    	arrayList1.add(applicationData);
                }
            }
   	  	}
    	else
    	{
    		for (int i = 0; i < j; i++)
            {
                String appDataID = (String) getUpFilePanel().
                                   getMultiList().getCellText(i, 3);
                if (appDataID == null)
                {
                    //����(3)2007.01.17 �촺Ӣ�޸�  �޸�ԭ��:�ֽ���û�б��浽���ݿ�,
                    //�����ά������ApplicationDataInfo������ֽ���.
                    Object[] object = new Object[2];
                    String fileName = (String)getUpFilePanel().
                                      getMultiList().
                                      getCellText(i, 0);
                    String path = (String) getUpFilePanel().
                                  getMultiList().
                                  getCellText(i, 1);
                    String length = (String)getUpFilePanel().
                                    getMultiList().
                                    getCellText(i, 2);
                    applicationData = new ApplicationDataInfo();
                    applicationData.setFileName(fileName);
                    applicationData.setUploadPath(path);
                    applicationData.setFileSize(Long.parseLong(length));
                    //����(3)2007.01.17 �촺Ӣ�޸� ����ļ��ֽ���
                    byte[] byteStream = getFileByte(path);
                    object[0] = applicationData;
                    object[1] = byteStream;
                    //����(3)2007.01.17 �촺Ӣ�޸�  ��object����ŵ�arrayList1��,���ͻ��˱�����
                    arrayList1.add(object);
                }
            }
    	}
    	//CCEnd SS10
      return arrayList1;
    }
    
    /**
     * ������Ϊʱԭ���տ���ӵ�еĸ�����Ϣ��
     */
    private Vector getVectorSaveAs() 
    {
    	  int j = getUpFilePanel().getMultiList().getTable().
                getRowCount();
        Vector vec = new Vector();
        for (int i = 0; i < j; i++)
        {
            String appDataID = (String) getUpFilePanel().
                               getMultiList().getCellText(i, 3);
            if (appDataID != null)
            {
                vec.add(appDataID);
            }
        }
      return vec;
    }
    
    /**
     * �����ļ�·������ļ���
     * @param path String
     * @return byte[]
     */
    private byte[] getFileByte(String path)
    {
      File file = new File(path);
      long length = file.length();
      byte[] b = new byte[(int) length];
      try
      {
          FileInputStream in = new FileInputStream(file);
          in.read(b);
          in.close();
      }
      catch (FileNotFoundException ex1)
      {
      	ex1.printStackTrace();
      }
      catch (IOException ex2)
      {
      	ex2.printStackTrace();
      }
      return b;
    }
    
    
    /**
     * ���ö����������Ӹ�������Ϣ
     * @param equip QMEquipmentInfo
     */                                 
    private void setUpFileAccessaryName(QMFawTechnicsInfo upFileList)
    {
      Vector vec = null;
      try 
      {
      	vec = getContents(upFileList);
		  }
		  catch (QMRemoteException e)
		  {
		  	e.printStackTrace();
		  }
		  if (vec == null)
		  {
		  	return;
		  }
		  int size = vec.size();
		  for (int m = 0; m < size; m++)
		  {
          ApplicationDataInfo applicationData = (ApplicationDataInfo)
                                                vec.elementAt(m);
          this.getUpFilePanel().getMultiList().addTextCell(m, 0,
                  applicationData.getFileName());
          this.getUpFilePanel().getMultiList().addTextCell(m, 1,
                  applicationData.getUploadPath());
          this.getUpFilePanel().getMultiList().addTextCell(m, 2,
                  String.valueOf(applicationData.getFileSize()));
          this.getUpFilePanel().getMultiList().addTextCell(m, 3,
                  applicationData.getBsoID());
          this.getUpFilePanel().setApplication(
                  applicationData);
      }
      this.getUpFilePanel().setRow(size);
    }
    
    /**
     * �õ�����������ָ����������
     * @param priInfo QMEquipmentInfo ��������
     * @return Vector ApplicationDataInfo ������
     * @throws RationException 
     */
    private Vector getContents(QMFawTechnicsInfo priInfo) throws QMRemoteException
    {
    	Class[] paraClass ={ContentHolderIfc.class};
    	Object[] obj ={priInfo};
    	Vector  c = (Vector) TechnicsAction.useServiceMethod(
                    "ContentService", "getContents", paraClass, obj);
		  return c;
    }
    
    private UpFilePanel getUpFilePanel()
    {
      return upFilePanel;
    }
    //CCEnd by liunan 2009-10-12
    
    //    CCBegin SS20
    public CappExAttrPanel getCappExAttrPanel(){
    	return cappExAttrPanel;
    }
//    CCEnd SS20
}
