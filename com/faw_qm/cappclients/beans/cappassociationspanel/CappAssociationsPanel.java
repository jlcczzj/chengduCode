/** ���ɳ���CappAssociationsPanel.java    1.0  2003/02/06
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * 
 * CR1 ��������2009/04/21   ԭ��:���������������Դ���ܽ��ж�ѡɾ��.
 *                          ����:����Դ�б��޸�Ϊ����ѡ���������,�����޸���ɾ����ť��
 *                               �߼�.
 *                          ��ע:�����¼���"CRSS-009"   
 * CR2 �촺Ӣ  2009/04/29   ԭ�������豸����װ�����ϵ�����и�CappAssociationsPanel�����˶�ѡģʽ
 *                               �������ơ�����ʱ���б�ѡ��
 *                          ������������ʱѡ����һ�У���ѡ�е�ǰ�С�����ʱѡ����һ�У���ѡ�е�ǰ��
 *                          ��ע�������¼���"CRSS-009" 
 *                         
 * CR3 2009/04/30 Ѧ��      ԭ�����ݸ��²���ʾ�Ƿ񱣴档
 *                          ���������Ӽ�����������Ƿ���¡�
 *                          ��ע��CRSS-004  
 * CR4 2009/05/21 ������     ԭ��ɾ�����򣨹���������Դ��������ɾ����ӵĿ���ʱ�����ֿ�ָ�����
 *                           �������жϴӵ�Ԫ��ȡ������Դid���Ƿ�Ϊ�գ���Ϊ��ʱ����ͨ�����id��ȡֵ                      
 * CR5 2009/05/21 ������     ԭ��ɾ�����򣨹���������Դ�������ڲ�ѡ�����ʱ���ɾ����ť��ϵͳ��Ȼ��ʾ
 *                              �Ƿ�ɾ������
 *                          �������ж��Ƿ���ѡ���У�û��ѡ�����򷵻�
 * CR6 2009/06/02 �촺Ӣ     �μ�DefectID=2231 ,2510 
 * CR7 2009/07/08 �촺Ӣ     ���������¹��������Դ����ʱ������ı���ӵĶ���ֵ�������ָ��
 * CR8 2009/12/31 ������    TD����2732
 * CR9 2011/02/21 Ѧ��  �μ�TD3637
 * CR10 2011/02/23 Ѧ��  �μ�TD3655
 * CCBegin by liunan 2011-08-25 �򲹶�PO35
 * CR11 2011/06/25 ���� �μ�TD2408
 * CCEnd by liunan 2011-08-25
 * CCBegin by liunan 2012-02-27 �򲹶�PO41
 * CR15 2011/12/09 ���� �μ�TD 2466
 * CCEnd by liunan 2012-02-27
 * SS1 �����豸�����ϡ��㲿������˳��  liuyang 2013-3-18
 * SS2 ȡ����ȡ���ť liuyang 2013-3-19
 * SS3 ������������ƺ������λ��ʾ����  liuyang 2013-3-19
 * SS4 ���������ӹ������ۼӵ����򡱲��Ҳ��ڹ�������ʾ leixiao 2013-10-14
 * SS5 �޸ķ���ƽ̨���⣺A005-2014-2843�� ������  2014-05-14
 * SS6 �ɶ��������ӽṹ�ʹ�·�߹���  guoxiaoliang 2016-7-14
 * SS7 �ɶ�������ӻ���Ӽ�����  guoxiaoliang 2016-7-28   
 * SS8 �ɶ�����������Դ��ӹ�����Դ���� guoxiaoliang 2016-8-2
 * SS9 �ɶ���ӵ���������  guoxiaoliang 2016-8-3
 * SS10 �ɶ������޷�����㲿�� liuyuzhu 2016-09-19
 * SS11 A004-2016-3437 ����ͬ�汾���������ֻ������һ��ͬ���͹��յ����� liunan 2017-1-13
 */
package com.faw_qm.cappclients.beans.cappassociationspanel;

import java.awt.AWTEvent;
import java.awt.AWTEventMulticaster;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.ItemSelectable;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Beans;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.faw_qm.affixattr.model.AffixIfc;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureQMEquipmentLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMMaterialLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMPartLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMToolLinkIfc;
import com.faw_qm.capp.model.QMProcedureUsageResourceLinkIfc;
import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.capp.view.ProcedureUsagePartJPanel;
import com.faw_qm.cappclients.capp.view.SelectStructPartJDialog;
import com.faw_qm.cappclients.capp.view.SonPartSelectJDialog;
import com.faw_qm.cappclients.capp.view.TstSearchRouteListJDialog;
import com.faw_qm.cappclients.util.CappTextAndImageCell;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.beans.FindObjectsPanel;
import com.faw_qm.clients.beans.Schema;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.util.BeanRequestServer;
import com.faw_qm.clients.util.QMContext;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.extend.model.ExtendAttriedIfc;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.extend.util.ExtendAttModel;
import com.faw_qm.extend.util.ExtendAttributeHandler;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.service.BinaryLinkInfo;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.help.HelpContext;
import com.faw_qm.help.HelpSystem;
import com.faw_qm.help.QMHelpContext;
import com.faw_qm.help.QMHelpSystem;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.JNDIUtil;
import com.faw_qm.util.PropertyDescript;
import com.faw_qm.util.RegistryException;
import com.faw_qm.version.model.MasteredIfc;
//end CR9

/**
 * <p>Title:CappAssociationsPanel </p>
 * <p>Description:CappAssociationsPanel����ά����ǰ����Ĺ�����
 * ��ʾ��multilist�С�CappAssociationsPanel����EDIT��VIEW����ģʽ��
 * EDITģʽ�¿�������ɾ���Ĺ�����VIEWģʽ����ֻ������� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author �ܴ�Ԫ
 */
//���⣨1��20060710Ѧ���޸ģ���updateLinkAtts(Hashtable theLinks)���������double���͵Ĵ���
// ���⣨2��20060720Ѧ���޸ģ��޸ķ���populate()��ԭ��CappAssociationsLogic��getRelations�����ĳɷ���
//         ��������һ�߶���ļ��ϣ� populate()��Ӧ�����޸ģ��������Ա���ͨ��������һ��idˢ�³���һ�߶���
// ���⣨3��20060725 Ѧ���޸� �����������ϣ�����ÿ�ζ�Ҫ�������ô�����ϣ��޸ķ�����
//          addLinkAttrs(int i, int j, BinaryLinkIfc info, String attName)
// ���⣨4��20060926Ѧ���޸ģ��޸ķ���launchChooserDialog������������ȥ���Ӳ�ѯ������
// ����(5)20080602 �촺Ӣ�޸�  �޸�ԭ��:��ӹ�������ĵ�ʱ,��ͨ�û���������ȫ���ĵ�,��Ϊ
// �������������ĵ�masterû��Ȩ�޿���,����Ҫ�����������ʾ�ĵ�,��������Ȩ�޿�����
//���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
//��������������������ư�ť  
//���⣨7��20081230 �촺Ӣ�޸�  �޸�ԭ�򣺱�����¹��򣨹�����֮�󣬱��ֽ���Ϊ����״̬
//��ʱ�ٵ㱣�湤�򣨹�������������Դû��
//SS1 20130711 Ϊ��������������������BSOID���е���

public class CappAssociationsPanel extends JComponent implements ItemSelectable, //Container
        Serializable
{

    /**
     * �༭ģʽ
     */
    public static final String EDIT_MODE = "Edit";


    /**
     * �鿴ģʽ
     */
    public static final String VIEW_MODE = "View";


    /**
     * ��ʶ��������������
     */
    public static final int POPULATE = 0;


    /**
     * ��ʶ�鿴ѡ�еĹ���
     */
    public static final int VIEW = 1;


    /**
     * ��ʶ��ӹ���
     */
    public static final int ADD = 2;

    //���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
	// ��������������������ư�ť 
	public static final int UP = 4;
	public static final int DOWN = 5;
    /**
     * ��ʶ���¹���
     */
    public static final int UPDATE = 3;
    private String labels[]; //�����б���б��⼯��
    private BaseValueIfc object; //ҵ�����
    private String objectClassName; //ҵ����������
    private Class objectClass; //ҵ���������
    
    //CCBegin SS6
    
    private boolean isStruct = false;
    private boolean isRouteList = false;
    boolean hasSon = false;
    Component component = null;
    private CodingIfc techTypeIfc;
    /**
     * ��ʶ���¹���
     */
    public boolean modifyFlag=false;
    
    
    protected boolean isGetResouceByProcedure = false;
    
    //CCEnd SS6

    private String role; //ҵ�������ڹ����н�ɫ��
    private String otherSideClassName; //ҵ������������һ��ҵ����������
    private Class otherSideClass; //ҵ������������һ��ҵ����������
    private String otherSideRole; //��һ�߽�ɫ��

    private String otherSideAttributes[]; //ҵ������������һ��ҵ�������ʾ�ڶ����б��е����Լ�
    private String linkClassName; //��������
    private String linkAttributes[]; //����ʾ�ڶ����б��еĹ���������Լ�
    private String multiListLinkAttributes[]; //�����б���ʾ����������
    private Schema linkSchema; //�����෽��
    private Schema otherSideSchema; //��һ��ҵ����󷽰�
    private String chooserOptions; //ChooserOptionsע���key֮һ
    private String mode; //ģʽ:EDIT_MODE or VIEW_MODE
    private Hashtable links; //������еĹ���ʵ��
    private Hashtable updatedLinks; //��Ŵ����µĹ���ʵ��
    private Hashtable removedLinks; //��Ŵ�ɾ���Ĺ���ʵ��
    private Hashtable otherSideObjects; //��Ź�������һ�ζ���
    private Frame frame; //������
    private CappAssociationsLogic taskLogic; //��װ��������ص��߼�
    private JDialog queryDialog; //ChooserOptionsע���key֮һ
    private CappAssociationsModel model; //ģ�ͽӿ�
    private boolean dirty; //bean��״̬�Ƿ񱻸ı�
    private boolean currentLinkAttributeDirty; //bean�е�ǰ�����������Ƿ񱻸ı�
    private boolean attributesFormSetObjectInProcess; //���Ա��Ƿ����ڴ�����
    private boolean saveUpdatesOnly; //�Ƿ�ֻ������¹���
    private int oldSelection; //����ڵ�ǰѡ���ǰһ��ѡ�е�����
    private static ResourceBundle resource = null;
    private static String RESOURCE = "com.faw_qm.clients.beans.BeansRB";
    protected static ThreadGroup contextGroup = Thread.currentThread().
                                                getThreadGroup(); //����������߳���
    transient ItemListener itemListener; //��Ŀ����
    private static final boolean DEBUG = false; //debug��ǩ
    //CCBegin SS6
//    private ComponentMultiList multiList; //�����б���ʾ���
    public ComponentMultiList multiList; //�����б���ʾ���
    //CCEnd SS6

    private JPanel buttonPanel; //��ť����
    private JButton viewButton; //�鿴��ť
    private JButton insertButton; //����հ���
    private int insertButtonLocation; //��Ӱ�ť�ǵڼ���button
    private JButton calculateButton; //���㰴ť����ͼװ���ղ�������Ϣ����û���������ʹ��
    private int calculateButtonLocation; //���㰴ť�ǵڼ���button
    private JButton browseButton; //��Ӱ�ť
    private JButton removeButton; //�Ƴ���ť
    private boolean isInsert = false;
    private boolean isCalculate = false;
//  CCBeginby leixiao 2009-6-24 ԭ�򣺹������������������ƶ�    
    private boolean isRation = false;
  //CCBegin SS2
//    private JButton rationJButton; //���϶��ť
   //CCEnd SS2
    private int rationButtonLocation; //���ư�ť�ǵڼ���button
//  CCEndby leixiao 2009-6-24  
    //��ť�����Ƿ�
    private int insertButtonMne; //��Ӱ�ť�����Ƿ�
    private int removeButtonMne; //ɾ����ť�����Ƿ�
    private int calculateButtonMne; //���㰴ť�����Ƿ�
    private int viewButtonMne; //�鿴��ť�����Ƿ�
    private int browseButtonMne; //�����ť�����Ƿ�


    //��ť���Ƿ�����ʾ,�����Ƿ���'V',����ʾ������"RV"
    private String insertButtonMneDisp; //��Ӱ�ť�����Ƿ�����ʾ
    private String removeButtonMneDisp; //ɾ����ť�����Ƿ�����ʾ
    private String calculateButtonMneDisp; //���㰴ť�����Ƿ�����ʾ
    private String viewButtonMneDisp; //�鿴��ť�����Ƿ�����ʾ
    private String browseButtonMneDisp; //�����ť�����Ƿ�����ʾ

    
    //���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
	// ��������������������ư�ť  
	private JButton upButton;
	private JButton downButton;
	private boolean isUpDown = false;
	private int upButtonLocation;
	private int downButtonLocation;
	private int upButtonMne; // ���ư�ť�����Ƿ�
	private int downButtonMne; // ���ư�ť�����Ƿ�
	private String upButtonMneDisp; // ���ư�ť�����Ƿ�����ʾ
	private String downButtonMneDisp; // ���ư�ť�����Ƿ�����ʾ
	//���⣨6��end
	
	//CCBegin SS6
	  private JButton structButton; //�ṹ����
	  private JButton routelistButton; //��׼����
	  private int structButtonLocation; //�ṹ������ť�ǵڼ���button
	  private int routelistButtonLocation; //��׼������ť�ǵڼ���button
	  private int structButtonMne; //�ṹ������ť�����Ƿ�
	  private int routelistButtonMne; //��׼������ť�����Ƿ�
	  private String structButtonMneDisp; //�ṹ������ť�����Ƿ�����ʾ
	  private String routelistButtonMneDisp; //��׼������ť�����Ƿ�����ʾ
	//CCCEnd SS6
	
	  //CCBegin SS7
	  private JButton sonButton;
	  
	  //CCEnd SS7
	  
	  //CCBegin SS9
	  private JButton partaddButton;//��part����������Ҫ��Ϣ������
	  private int partaddButtonMne;
	  private String partaddButtonMneDisp;
	  //CCEnd SS9
	
    //�����Ƿ���ӱ�
    private boolean childQuery = true;
    HelpSystem helpSystem; //����ϵͳ
    HelpContext helpContext; //����������
    private static final String SCREEN_ID = "com.faw_qm.clients.richtothin."; //ע���ļ��е�����ͷ
    private static final String PARAM = "param"; //ע���ļ��е�����β
    private String secondeTypeValue;
    private ObjectFilter objectFilter = null;
    static boolean VERBOSE;
    static
    {
        VERBOSE = (new Boolean(RemoteProperty.getProperty(
                "com.faw_qm.cappclients.verbose", "true"))).booleanValue();
    }

    ResourceBundle checkRB = ResourceBundle.getBundle(
            "com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanelRB",
            RemoteProperty.getVersionLocale());

    private Locale local = RemoteProperty.getVersionLocale();
    private boolean lastIteration; //�Ƿ�������°汾
    int[] editCols;
    boolean editflag;
    protected Vector actionListeners = new Vector(); //�����¼�����


    /**20060725 Ѧ���޸� �����������ϣ�����ÿ�ζ�Ҫ�������ô������*/
    private Hashtable codingMap = new Hashtable();
    private ExtendAttContainer container;


    /**��ѡ��ť������*/
    private int[] rds;


    /**ö�����ͱ�*/
    private Hashtable enumeratedTypes = new Hashtable();


    /**�߳���*/
    private Object addAndRemove = new Object();
    
    //CCBegin SS8
    private JButton getResoucebyProcedureButton; //�ӹ���̳���Դ
    private int getResoucebyProcedureButtonMne;
    private String getResoucebyProcedureButtonMneDisp;
    //CCEnd SS8


    //add by wangh on 20080228 ���ڰ�˳�򱣴����ʵ���ļ�ֵ.
    Vector kv = new Vector();
    Vector sortV = new Vector();
    //add end
    Vector upDownVec = new Vector();
    
    
    /**
     * ���ö�������
     * @param value String ����������
     */
    public void setSecondTypeValue(String value)
    {
        //��������
        secondeTypeValue = value;
        taskLogic.setSecondTypeValue(value);
        try
        {
            //�õ���չ��������
            container = ExtendAttributeHandler.getExtendAtts(
                    taskLogic.getBsoName(linkClassName), secondeTypeValue);
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e2)
        {
            e2.printStackTrace();
        }

    }


    /**
     * ��ö�������
     * @return String ��������
     */
    public String getSecondTypeValue()
    {
        return secondeTypeValue;
    }


    /**
     * �Ƿ�������°汾
     * @param isLast boolean �Ƿ�������°汾
     */
    public void setLastIteration(boolean isLast)
    {
        lastIteration = isLast;
        
    }


    /**
     * �Ƿ�������°汾
     */
    public boolean isLastIteration()
    {
        return lastIteration;
    }


    /**
     * ��������������
     * @param obj ObjectFilter ����������
     */
    public void setObjectFilter(ObjectFilter obj)
    {
        objectFilter = obj;
    }


    /**
     * <p>Title: AddObjectListener</p>
     * <p>Description:��������QmChooser�Ĳ�ѯ�¼���
     * ����ѡ�е���һ��������뵽multilist�� </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: qm</p>
     * @author �ܴ�Ԫ
     * @version 1.0
     */
    public class AddObjectListener implements QMQueryListener
    {
        /**
         * �¼�ִ�з���
         * @param qmqueryevent QMQueryEvent ��ѯ�¼�
         */
        public void queryEvent(QMQueryEvent qmqueryevent)
        {
            if (qmqueryevent.getType().equalsIgnoreCase(QMQueryEvent.COMMAND))
            {
                // �˳�
                if (qmqueryevent.getCommand().equalsIgnoreCase(QmQuery.QuitCMD))
                {
                    frame.dispose();
                }
                // ȷ��
                if (qmqueryevent.getCommand().equalsIgnoreCase(QmQuery.OkCMD))
                {
                    QmQuery qmquery = (QmQuery) qmqueryevent.getSource();
                    // ѡ����������
                    BaseValueIfc awtobject[] = qmquery.getSelectedDetails();
                    //�����������
                    try
                    {
                        if (objectFilter != null)
                        {
                            awtobject = objectFilter.doFillter(awtobject);
                        }
                        for (int i = 0; i < awtobject.length; i++)
                        {
                            BaseValueIfc basevalueinfoobject = awtobject[i];

                            debug("CappAssociationsPanel.queryEvent() object: " +
                                  basevalueinfoobject);
                            addSelectedObject(basevalueinfoobject);
                        }
                        int j = multiList.getNumberOfRows() - 1;
                        multiList.selectRow(j);
                        if (frame != null)
                        {
                            frame.dispose();
                            return;
                        }
                    }
                    catch (Exception introspectionexception)
                    {
                        introspectionexception.printStackTrace();
                        return;
                    }
                }
            }
        }

        private Frame frame;
        AddObjectListener(Frame frame1)
        {
            frame = frame1;
        }
    }


    /**
     * <p>Title: AddCappObjectListener</p>
     * <p>Description:��������QmChooser�Ĳ�ѯ�¼���
     * ����ѡ�е���һ��������뵽multilist�� </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: qm</p>
     * @author �ܴ�Ԫ
     * @version 1.0
     */
    public class AddCappObjectListener implements CappQueryListener
    {
        /**
         * �¼�ִ�з���
         * @param qmqueryevent QMQueryEvent ��ѯ�¼�
         */
        public void queryEvent(CappQueryEvent qmqueryevent)
        {
            if (qmqueryevent.getType().equalsIgnoreCase(CappQueryEvent.COMMAND))
            {
                // �˳�
                if (qmqueryevent.getCommand().equalsIgnoreCase(CappQuery.
                        QuitCMD))
                {
                    frame.dispose();
                }
                //ȷ��
                if (qmqueryevent.getCommand().equalsIgnoreCase(CappQuery.OkCMD))
                {
                    CappQuery qmquery = (CappQuery) qmqueryevent.getSource();
                    BaseValueIfc awtobject[] = qmquery.getSelectedDetails();
                    //�����������
                    try
                    {
                        if (objectFilter != null)
                        {
                            awtobject = objectFilter.doFillter(awtobject);
                        }
                        for (int i = 0; i < awtobject.length; i++)
                        {
                            BaseValueIfc basevalueinfoobject = awtobject[i];
                            debug("CappAssociationsPanel.queryEvent() object: " +
                                  basevalueinfoobject);
                            addSelectedObject(basevalueinfoobject);
                        }
                        int j = multiList.getNumberOfRows() - 1;
                        multiList.selectRow(j);
                        if (frame != null)
                        {
                            frame.dispose();
                            return;
                        }
                    }
                    catch (Exception introspectionexception)
                    {
                        introspectionexception.printStackTrace();
                        return;
                    }
                }
            }
        }

        private Frame frame;
        AddCappObjectListener(Frame frame1)
        {
            frame = frame1;
        }
    }


    /**
     * <p>Title: OKKeyListener</p>
     * <p>Description:�����¼����� </p>
     * <p>Copyright: Copyright (c) 2003</p>
     * <p>Company:faw_qm </p>
     * @author �ܴ�Ԫ
     * @version 1.0
     */
    class OKKeyListener implements KeyListener
    {
        public void keyPressed(KeyEvent e)
        {
            Component jc = e.getComponent();
            if (jc instanceof JTextField)
            {

                BinaryLinkIfc li = getSelectedLink();
                if (saveUpdatesOnly)
                {
                    addToUpdatedTable(li);
                }
                setDirty(true);

            }

        }

        public void keyReleased(KeyEvent e)
        {

        }

        public void keyTyped(KeyEvent e)
        {

        }

        OKKeyListener()
        {

        }
    }


    /**
     * <p>Title: WorkThread</p>
     * <p>Description:������̴߳����û��Ĳ�������ӡ�ɾ�����鿴�� </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: qm</p>
     * @author �ܴ�Ԫ
     * @version 1.0
     */
    class WorkThread extends QMThread
    {

        public void run()
        {
            switch (action)
            {
                case POPULATE: // '\0'
                    populate();
                    return;
                case VIEW: // '\001'
                    viewSelectedObject();
                    return;
                case ADD: // '\002'
                    addObject();
                    return;
               //���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
        	   // ��������������������ư�ť 
        	    case UP:
        			upRow();
        			return;
        		case DOWN:
        			downRow();
        			return;
            }
        }

        int action;
        public WorkThread(int i)
        {
            action = i;
        }
    }


    /**
     *
     * <p>Title:SymAction </p>
     * <p>Description:ʵ��ActionListener�ӿڣ����ڶԽ���İ�ť�������� </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: qm</p>
     * @author �ܴ�Ԫ
     * @version 1.0
     */
    class SymAction implements ActionListener
    {
        public void actionPerformed(ActionEvent actionevent)
        {
            Object obj = actionevent.getSource();
            //�鿴
            if ("view".equals(actionevent.getActionCommand()))
            {
                viewButton_ActionPerformed(actionevent);
            }
            //���
            if ("browse".equals(actionevent.getActionCommand()))
            {

                browseButton_ActionPerformed(actionevent);
                return;
            }
            //ɾ��
            if ("remove".equals(actionevent.getActionCommand()))
            {
                removeButton_ActionPerformed(actionevent);
                return;
            }
            //����
            if ("insert".equals(actionevent.getActionCommand()))
            {
                insertButton_ActionPerformed(actionevent);
                return;
            }
            
            //CCBegin SS6
            
            //�ṹ����
            if ("struct".equals(actionevent.getActionCommand())) {
              structButton_ActionPerformed(actionevent);
              return;
            }
            //��׼����
            if ("routelist".equals(actionevent.getActionCommand())) {
              routelistButton_ActionPerformed(actionevent);
              return;
            }
            
            //CCEnd SS6
            
            //CCBegin SS7
            if ("son".equals(actionevent.getActionCommand())) {
                sonButton_ActionPerformed(actionevent);
                return;
              }
            
            //CCEnd SS7
            
            //CCBegin SS8
            //�ӹ�������Դ��ӵ�������
            if ("getResoucebyProcedure".equals(actionevent.getActionCommand())) {
              getResoucebyProcedureButton_ActionPerformed(actionevent);
              return;
            }
            
            //CCEnd SS8
            
            //CCBegin SS9
            
          //���㲿���л����Ҫ��Ϣ�ӵ�������
            if ("partadd".equals(actionevent.getActionCommand())) {
              partadd_ActionPerformed(actionevent);
              return;
            }
            //CCEnd SS9
            
            
          //CCBegin SS2
//          CCBeginby leixiao 2009-7-6 ԭ�򣺹��ղ���������ӻ�ȡ����
//            if ("ration".equals(actionevent.getActionCommand()))
//            {
////            	rationJButton_actionPerformed(actionevent);
//                notifyActionListeners(actionevent);
//            	System.out.println("���϶���!");
//                return;
//            }
//          CCEndby leixiao 2009-7-6 ԭ�򣺹��ղ���������ӻ�ȡ����
            ////CCEnd SS2
            //����
            if ("calculate".equals(actionevent.getActionCommand()))
            {
                notifyActionListeners(actionevent);
                BinaryLinkIfc li = getSelectedLink();
                if (li == null)
                {
                    return;
                }
                if (saveUpdatesOnly)
                {

                    addToUpdatedTable(li);
                }
                setDirty(true);
                return;
            }
            //���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
			// ��������������������ư�ť  
			if ("up".equals(actionevent.getActionCommand())) {

				upButton_ActionPerformed(actionevent);
				return;
			}
			if ("down".equals(actionevent.getActionCommand())) {

				 downButton_ActionPerformed(actionevent);
				return;
			}
			//���⣨6��end
            if (obj == multiList)
            {
                return;
            }
            if (obj instanceof JCheckBox || obj instanceof JRadioButton ||
                obj instanceof JComboBox)
            {
                notifyActionListeners(actionevent);
                BinaryLinkIfc li = getSelectedLink();
                if (li != null)
                {
                    if (saveUpdatesOnly)
                    {
                        addToUpdatedTable(li);
                    }
                    setDirty(true);
                }

            }
            if (obj instanceof JTextField)
            {
                notifyActionListeners(actionevent);
                /*BinaryLinkIfc li = getSelectedLink();
                                 if (li == null)
                                 {
                   return;
                                 }
                                if (saveUpdatesOnly)
                                 {

                   addToUpdatedTable(li);
                                 }*/
                setDirty(true);

            }
        }

        SymAction()
        {
        }
    }


    /**
     * <p>Title: </p>
     * <p>Description:ʵ����ComponentAdapter�ӿ�,���ڶ�multilist�Ĵ�С�ı�������� </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: qm</p>
     * @author �ܴ�Ԫ
     * @version 1.0
     */
    class SymComponent extends ComponentAdapter
    {

        public void componentResized(ComponentEvent componentevent)
        {
            Object obj = componentevent.getSource();
            if (obj == multiList)
            {
                multiList_componentResized(componentevent);
            }
        }

        SymComponent()
        {
        }
    }


    /**
     * <p>Title: </p>
     * <p>Description:ʵ����ItemListener�ӿ�,
     * ���ڶ�multilist����Ŀѡ��ĸı�������� </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: qm</p>
     * @author �ܴ�Ԫ
     * @version 1.0
     */
    class SymItem implements ItemListener
    {

        public void itemStateChanged(ItemEvent itemevent)
        {
            Object obj = itemevent.getSource();
            if (obj == multiList)
            {
                multiList_itemStateChanged(itemevent);
            }
        }

        SymItem()
        {
        }
    }


    /**
     * <p>Title: </p>
     * <p>Description:ʵ����PropertyChangeListener�ӿ�,
     * ���ڶ�AttributesForm���Եĸı�������� </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: qm</p>
     * @author �ܴ�Ԫ
     * @version 1.0
     */
    class SymPropertyChange implements PropertyChangeListener
    {
        public void propertyChange(PropertyChangeEvent propertychangeevent)
        {
            Object obj = propertychangeevent.getSource();
            // if (obj == attributesForm1)
            //   attributesForm1_propertyChange(propertychangeevent);
        }

        SymPropertyChange()
        {
        }
    }


    /**
     * Ĭ�Ϲ��췽��
     */
    public CappAssociationsPanel()
    {
    	
    	//CCBegin SS6
    	
    	component = this;
    	//CCEnd SS6
    	
        try
        {
            if (!Beans.isDesignTime())
            {
                UIManager.setLookAndFeel(UIManager.
                                         getSystemLookAndFeelClassName());
            }
        }
        catch (UnsupportedLookAndFeelException unsupportedlookandfeelexception)
        {
            unsupportedlookandfeelexception.printStackTrace();
        }
        catch (ClassNotFoundException classnotfoundexception)
        {
            classnotfoundexception.printStackTrace();
        }
        catch (InstantiationException instantiationexception)
        {
            instantiationexception.printStackTrace();
        }
        catch (IllegalAccessException illegalaccessexception)
        {
            illegalaccessexception.printStackTrace();
        }
        labels = new String[0];
        //relativeColumnWidths = new String[0];
        objectClassName = "";
        otherSideClassName = "";
        otherSideAttributes = new String[0];
        linkClassName = "";
        linkAttributes = new String[0];
        multiListLinkAttributes = new String[0];
        linkSchema = new Schema();
        otherSideSchema = new Schema();
        otherSideObjects = new Hashtable();
        chooserOptions = "";
        mode = VIEW_MODE;
        links = new Hashtable();
        updatedLinks = new Hashtable();
        removedLinks = new Hashtable();
        dirty = false;
        currentLinkAttributeDirty = false;
        attributesFormSetObjectInProcess = false;
        saveUpdatesOnly = false;
        oldSelection = -1;
        GridBagLayout gridbaglayout = new GridBagLayout();
        setLayout(gridbaglayout);
        setSize(590, 236);
        multiList = new ComponentMultiList();

        try
        {
            String as[] = new String[1];
            as[0] = new String("one");
            multiList.setHeadings(as);
        }
        catch (Exception _ex)
        {_ex.printStackTrace();
        }
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 1;
        gridbagconstraints.weightx = 1.0D;
        gridbagconstraints.weighty = 1.0D;
        gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;
        gridbagconstraints.fill = GridBagConstraints.BOTH;
        gridbagconstraints.insets = new Insets(0, 0, 0, 0);
        gridbagconstraints.ipady = -4;
        ((GridBagLayout) getLayout()).setConstraints(multiList,
                gridbagconstraints);
        add(multiList);
        // jScrollPane.getViewport().add(multiList, null);
        multiList.setCellEditable(false);
        multiList.setCheckboxEditable(true);
        multiList.setCheckboxEditable(true);
        buttonPanel = new JPanel();
        gridbaglayout = new GridBagLayout();
        buttonPanel.setLayout(gridbaglayout);
        buttonPanel.setBounds(545, 72, 45, 104); //104
        //buttonPanel.setBackground(QM.getBGColor());
        gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = 1;
        gridbagconstraints.gridy = 1;
        gridbagconstraints.weighty = 1.0D;
        gridbagconstraints.anchor = GridBagConstraints.NORTHEAST;
        gridbagconstraints.fill = GridBagConstraints.VERTICAL;
        gridbagconstraints.insets = new Insets(0, 0, 0, 0);
        ((GridBagLayout) getLayout()).setConstraints(buttonPanel,
                gridbagconstraints);
        add(buttonPanel);
        viewButton = new JButton();
        viewButton.setActionCommand("view");
        viewButton.setLabel("View");
        viewButton.setBounds(0, 5, 65, 23); //

        gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 1;
        gridbagconstraints.weighty = 1.0D;
        gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;
        gridbagconstraints.fill = GridBagConstraints.HORIZONTAL;
        gridbagconstraints.insets = new Insets(5, 5, 0, 5);
        ((GridBagLayout) buttonPanel.getLayout()).setConstraints(viewButton,
                gridbagconstraints);
        buttonPanel.add(viewButton);

        taskLogic = new CappAssociationsLogic();
        localize();
        initializeHelp();

        SymAction symaction = new SymAction();
        viewButton.addActionListener(symaction);
        SymItem symitem = new SymItem();
        multiList.addItemListener(symitem);
        SymComponent symcomponent = new SymComponent();
        multiList.addComponentListener(symcomponent);
        multiList.addActionListener(symaction);
        multiList.addKeyListener(new OKKeyListener());

        SymPropertyChange sympropertychange = new SymPropertyChange();

        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * �����б���Կ��
     * @param relWidths �б���Կ��
     */
    protected void setRelColWidth(int[] relWidths)
    {
        int rl = relWidths.length;
        int[] rw = new int[rl + 1];
        for (int i = 0; i < rl; i++)
        {
            rw[i] = relWidths[i];
        }
        rw[rl] = 0;
        multiList.setRelColWidth(rw);
    }


    /**
     * �����б���Կ��
     * @return int[] �б���Կ�ȵļ���
     */
    public int[] getRelColWidth()
    {
        int[] rw = multiList.getRelColWidth();
        int[] newrw = new int[rw.length - 1];
        for (int i = 0; i < newrw.length - 1; i++)
        {
            newrw[i] = rw[i];
        }
        return newrw;
    }


    /**
     * ��ɫ��ʼ��
     */
    private void initColor()
    {
        if (VERBOSE)
        {
            System.out.println("set the color of the components starting......");
        }
        setForeground(QM.getFGColor());
        setBackground(QM.getBGColor());
    }


    /**
     * ����ǰ��ɫ
     * @param color
     */
    public void setBackground(Color color)
    {
        if (VERBOSE)
        {
            System.out.println("perform setBackground......������������������" +
                               color.toString());
        }
        super.setBackground(color);
        if (multiList != null)
        {
            multiList.setBackground(color);
        }
        if (buttonPanel != null)
        {
            buttonPanel.setBackground(color);
        }
        if (viewButton != null)
        {
            viewButton.setBackground(color);
        }
        refresh();
    }


    /**
     * ���ñ���ɫ
     * @param colorҪ���õ���ɫ
     */
    public void setForeground(Color color)
    {
        super.setForeground(color);
        if (multiList != null)
        {
            multiList.setForeground(color);
        }
        if (buttonPanel != null)
        {
            buttonPanel.setForeground(color);
        }
        if (viewButton != null)
        {
            viewButton.setForeground(color);
        }
        refresh();
    }


    /**
     * ���ض���mode(EDIT or VIEW)����ʵ��
     * @param mode �ض�ģʽ
     */
    public CappAssociationsPanel(String mode)
    {
        this();
        setMode(mode);
    }


    /**
     * ����CappMultiList��ѡ��ʽ
     * @param flag boolean �Ƿ�����,trueΪ����,false������
     */
    public void setMutliSelectedModel(boolean flag)
    {
        multiList.setMultipleMode(flag);
    }


    /**
     * ���ػ������Ϣ
     */
    protected void localize()
    {
        viewButton.setLabel(display("19", null));
    }


    /**
     * �����С�ߴ�
     * @return Dimension ��С�ߴ�
     */
    public Dimension getMinimumSize()
    {
        return new Dimension(350, 160);
    }


    /**
     * �����С�ߴ�
     * @param i ����ֵ
     * @param j ���ֵ
     * @return Dimension ��С�ߴ�
     */
    public Dimension getMinimumSize(int i, int j)
    {
        return new Dimension(300, 200);
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(300, 200);
    }

    public Dimension getPreferredSize(int i, int j)
    {
        return new Dimension(300, 200);
    }


    /**
     * �ж��û��Ƿ��Ѿ������޸�
     * @return boolean
     */
    public boolean isDirty()
    {
        return dirty || isCurrentLinkAttributeDirty();
    }


    /**
     * ��bean��״̬�����ı�ʱ����
     * @param flag
     */
    protected void setDirty(boolean flag)
    {
        dirty = flag;
        if (!flag)
        {
            setCurrentLinkAttributeDirty(false);
        }
    }


    /**
     * �ж��û��Ƿ��Ѿ��޸��˵�ǰ�Ĺ���
     * @return boolean
     */
    protected boolean isCurrentLinkAttributeDirty()
    {
        return currentLinkAttributeDirty;
    }


    /**
     * ��bean��״̬�����ı�ʱ����
     * @param flag
     */
    protected void setCurrentLinkAttributeDirty(boolean flag)
    {
        currentLinkAttributeDirty = flag;
    }


    /**
     * �ж����Ա����(true)��(false)���ڴ�����
     * @return boolean
     */
    protected boolean isAttributesFormSetObjectInProcess()
    {
        return attributesFormSetObjectInProcess;
    }


    /**
     * �������Ա����(true)��(false)���ڴ�����
     * @param flag
     */
    protected void setAttributesFormSetObjectInProcess(boolean flag)
    {
        attributesFormSetObjectInProcess = flag;
    }


    /**
     * ���ý���ģʽ
     * @param mode ����ģʽ
     */
    public void setMode(String mode)
    {
        if (mode.endsWith(VIEW_MODE))
        {
            int col = multiList.getCol();
            int[] cols = new int[col];
            for (int j = 0; j < col; j++)
            {
                cols[j] = j;
            }
            multiList.setColsEnabled(cols, true);
        }
        else
        {
            multiList.setColsEnabled(editCols, editflag);
        }
        if (!this.mode.equals(mode))
        {
            this.mode = mode;
            try
            {
                createUI();
                return;
            }
            catch (Exception propertyvetoexception)
            {
                propertyvetoexception.printStackTrace();
            }
            return;
        }
        else
        {
            return;
        }
    }


    /**
     * ���ʽ���ģʽ
     * @return String ����ģʽ
     */
    public String getMode()
    {
        return mode;
    }


    /**
     * ���û����Add��ťʱ�������������ÿ�ѡ��������б�
     * @param options  ChooserOptionsע���key֮һ
     */
    public void setChooserOptions(String options)
    {
        chooserOptions = options;
    }


    /**
     * ���û����Add��ťʱ�������������ʿ�ѡ��������б�
     * @return  ChooserOptionsע���key֮һ
     */
    public String getChooserOptions()
    {
        return chooserOptions;
    }


    /**
     * �������ڱ��鿴��༭��ҵ�����
     * @return BaseValueIfc ���ڱ��鿴��༭��ҵ�����
     */
    public BaseValueIfc getObject()
    {
        return object;
    }


    /**
     * ���ý�Ҫ���鿴��༭��ҵ�����
     * @param obj ��Ҫ���鿴��༭��ҵ�����
     */
    synchronized public void setObject(BaseValueIfc obj)
    {
        if (obj != null)
        {
            object = obj;
            multiList.clear();
            links.clear();
            removedLinks.clear();
            updatedLinks.clear();
            taskLogic.setObject(obj);
            if (taskLogic.isPersistent(obj))
            {
                WorkThread workthread = new WorkThread(POPULATE);
                workthread.start();
            }
        }
    }
	//CCBegin SS4 
    public void setIsProcedure(boolean flag){
    	if(flag){
            int [] le=	multiList.getRelColWidth();
        	le[le.length-2]=0;
        	multiList.setRelColWidth(le);
    	}
    }
	//CCEnd SS4

    /**
     * ����ҵ�����ڹ����еĽ�ɫ
     * @param role ҵ�����ڹ����еĽ�ɫ
     */
    public void setRole(String role)
    {
        this.role = role;
        taskLogic.setRole(role);
    }


    /**
     * ���ʽ�ɫ
     * @return String ��ɫ��
     */
    public String getRole()
    {
        return role;
    }


    /**
     * ����ҵ��������
     * @param classType ҵ��������
     */
    public void setObjectClass(Class classType)
    {
        objectClass = classType;
        //objectBsoName = taskLogic.getBsoName(classType);
        taskLogic.setObjectClass(classType);
    }


    /**
     * ���ҵ��������
     * @return Class ҵ��������
     */
    public Class getObjectClass()
    {
        return objectClass;
    }


    /**
     * ����ҵ������
     * @param className ҵ������
     * @throws ClassNotFoundException
     */
    public void setObjectClassName(String className)
            throws
            ClassNotFoundException
    {
        if (className == null)
        {
            return;
        }
        if (className.equals(""))
        {
            return;
        }
        if (className.equals(objectClassName))
        {
            return;
        }
        else
        {
            objectClassName = className;
            setObjectClass(Class.forName(className));
            //objectBsoName = taskLogic.getBsoName(getObjectClass());
            return;
        }
    }


    /**
     * ����ҵ������
     * @return String ҵ������
     */
    public String getObjectClassName()
    {
        return objectClassName;
    }


    /**
     * ���ʹ���ҵ���������
     * @return Class ����ҵ���������
     */
    public Class getOtherSideClass()
    {
        return otherSideClass;
    }


    /**
     * ���ù���ҵ���������
     * @param classType ����ҵ���������
     */
    public void setOtherSideClass(Class classType)
    {
        otherSideClass = classType;
        //otherSideBsoName = taskLogic.getBsoName(classType);
        otherSideClassName = classType.getName();
        taskLogic.setOtherSideClass(classType);
    }


    /**
     * ���ʹ���ҵ���������
     * @return String ����ҵ���������
     */
    public String getOtherSideClassName()
    {
        return otherSideClassName;
    }


    /**
     * ���ù���ҵ���������
     * @param className ����ҵ���������
     * @throws ClassNotFoundException
     */
    public void setOtherSideClassName(String className)
            throws
            ClassNotFoundException
    {
        if (className == null)
        {
            return;
        }
        if (className.equals(""))
        {
            return;
        }
        if (className.equals(otherSideClassName))
        {
            return;
        }
        else
        {
            otherSideClassName = className;
            setOtherSideClass(Class.forName(className));
            //otherSideBsoName = taskLogic.getBsoName(getOtherSideClass());
            return;
        }
    }


    /**
     * ���ù�������
     * @param className ��������������
     * @throws Exception
     */
    public void setLinkClassName(String className)
            throws Exception
    {
        linkClassName = className;
        //linkBsoName = taskLogic.getBsoName(linkClassName);
        taskLogic.setLinkClassName(className);
        // if (attributesForm1 != null)
        //   attributesForm1.setObjectClassName(className);
        try
        {
            resetLabels();
            return;
        }
        catch (PropertyVetoException _e)
        {
            _e.printStackTrace();
        }
    }


    /**
     * ���ʹ�������
     * @return String ��������
     */
    public String getLinkClassName()
    {
        return linkClassName;
    }


    /**
     * ���ý���ʾ�ڶ����б��й������Լ�
     * @param as �����б��й������Լ�
     * @throws Exception
     */
    public void setLinkAttributes(String as[])
            throws Exception
    {
        linkAttributes = as;
        // if (attributesForm1 != null)
        //    attributesForm1.setAttributes(as);
    }


    /**
     * ������ʾ�ڶ����б��й������Լ�
     * @return String[] �����б��й������Լ�
     */
    public String[] getLinkAttributes()
    {
        return linkAttributes;
    }


    /**
     * ���ö����б���ʾ����������
     * @param as �����б���ʾ����������
     * @throws PropertyVetoException
     * @throws ClassNotFoundException
     */
    public void setMultiListLinkAttributes(String as[])
            throws
            PropertyVetoException, ClassNotFoundException
    {
        multiListLinkAttributes = as;
        resetLabels();
    }


    /**
     * ���ʶ����б���ʾ����������
     * @return String[] �����б���ʾ����������
     */
    public String[] getMultiListLinkAttributes()
    {
        return multiListLinkAttributes;
    }


    /**
     * �������ʱ�䣬������ʹ�÷����༭�����ù����༰������
     * @param schema
     * @throws PropertyVetoException
     */
    /* public void setLinkSchema(Schema schema) throws PropertyVetoException {
       try {
         linkSchema = schema;
         String s = schema.getObjectClassName();
         setLinkClassName(s);
         String as[] = schema.getAttributeNames();
         setLinkAttributes(as);
       }
       catch (Exception _e) {
         _e.printStackTrace();
       }
     }*/

    /**
     * ���ʿ����������ʱ��ѡ��Ĺ�������
     * @return Schema �����������ʱ��ѡ��Ĺ�������
     */
    public Schema getLinkSchema()
    {
        return linkSchema;
    }


    /**
     * ��÷���ģ��
     * @return CappAssociationsModel ����ģ��
     */
    public CappAssociationsModel getModel()
    {
        return model;
    }
//CCBegin SS8
    
    private QMProcedureIfc info;

    /**
     * @param info QMProcedureIfc ����ֵ����
     */
    public void setProcedurePace(QMProcedureIfc info) {
      this.info = info;
    } //add by ll in 20080114 for �ڹ���ʱ��ӹ�����Դ ----------end
//CCEnd SS8
    
//CCBegin SS6
    
    /**
     * 
     * �����Ƿ���Ҫ��ȡ�Ӽ���ť
     */
    public void setHasSon(boolean falg) {
      this.hasSon = falg;
    }
    /**
     * ���ýṹ������ť�����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     */

    public void setStructButtonMnemonic(int mne) {
      if (structButton != null) {
        structButton.setMnemonic(mne);
        structButton.setText("�ṹ����" + "(" + (char) mne + ")");
      }
      else {
        structButtonMne = mne;
        structButtonMneDisp = String.valueOf( (char) mne);
      }
    }
    
    public void setTechTypeCodingIfc(CodingIfc ifc) {
        techTypeIfc = ifc;
      }
    
//CCEnd SS6    
    
    
    //CCBegin SS8
    /**
     * ���û�ù�����Դ��ť�����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     */

    public void setgetResoucebyProcedureButtonMnemonic(int mne) {
      if (getResoucebyProcedureButton != null) {
        getResoucebyProcedureButton.setMnemonic(mne);
        getResoucebyProcedureButton.setText("������Դ" + "(" + (char) mne + ")");
      }
      else {
        getResoucebyProcedureButtonMne = mne;
        getResoucebyProcedureButtonMneDisp = String.valueOf( (char) mne);
      }
    }
    
    //CCEnd SS8
    
    /**
     * ����ģ��
     * @param CappAssociationsModel CappAssociatiosModelģ�ͽӿ�
     * @throws PropertyVetoException
     */
    public void setModel(CappAssociationsModel associationsmodel)
            throws
            PropertyVetoException
    {
        model = associationsmodel;
        taskLogic.setModel(associationsmodel);
    }


    /**
     * �������ʱ�䣬������ʹ�÷����༭��ѡ��ҵ���༰������
     * @param schema
     * @throws PropertyVetoException
     * @throws ClassNotFoundException
     */
    /*  public void setOtherSideSchema(Schema schema) throws PropertyVetoException,
      ClassNotFoundException {
        otherSideSchema = schema;
        String s = schema.getObjectClassName();
        setOtherSideClassName(s);
        String as[] = schema.getAttributeNames();
        setOtherSideAttributes(as);
      }*/

    /**
     * ���ʿ����������ʱ��ѡ��Ĺ���ҵ����ķ���
     * @return Schema �����������ʱ��ѡ��Ĺ���ҵ����ķ���
     */
    public Schema getOtherSideSchema()
    {
        return otherSideSchema;
    }


    /**
     * ���ø�����
     * @param frame1 ������
     */
    public void setFrame(Frame frame1)
    {
        frame = frame1;
    }


    /**
     * ���ʸ�����
     * @return Frame ������
     */
    public Frame getFrame()
    {
        if (frame == null)
        {
            frame = getTopLevelParent(this);
        }
        return frame;
    }


    /**
     * ���ý���ʾ�ڶ����б��й�����ҵ���������Լ�
     * @param as �����б��й�����ҵ���������Լ�
     */
    public void setOtherSideAttributes(String as[])
    {
        otherSideAttributes = as;
        taskLogic.setOtherSideAttributes(as);
    }


    /**
     * ������ʾ�ڶ����б��й�����ҵ���������Լ�
     * @return String[] �����б��й�����ҵ���������Լ�
     */
    public String[] getOtherSideAttributes()
    {
        return otherSideAttributes;
    }


    /**
     * ���ö����б���б���
     * @param as �����б���б��⼯��
     * @throws PropertyVetoException
     */
    public void setLabels(String as[])
            throws PropertyVetoException
    {
        labels = as;
        resetLabels();
    }


    /**
     * ���ʶ����б���б���
     * @return String[] �����б���б��⼯��
     */
    public String[] getLabels()
    {
        return labels;
    }


    /**
     * �ж��Ƿ�ֻ������Ĺ���
     * @return boolean trueΪ�������
     */
    public boolean getSaveUpdatesOnly()
    {
        return saveUpdatesOnly;
    }


    /**
     * �����Ƿ�ֻ������Ĺ���
     * @param flag true-->ֻ������Ĺ��ģ�false-->����ȫ��
     */
    public void setSaveUpdatesOnly(boolean flag)
    {
        saveUpdatesOnly = flag;
    }


    /**
     * ���ø������1(��λ���)����Կ��
     * @param as
     * @throws PropertyVetoException
     */
    /**
      public void setRelativeColumnWidths(String as[]) throws
          PropertyVetoException {
          relativeColumnWidths = as;
          multiList.setRelativeColumnWidths(as);
      }
     **/

    /**
     * �õ���Կ��
     * @return
     */
    /**
     public String[] getRelativeColumnWidths() {
         return relativeColumnWidths;
     }
     **/

    /**
     * �ػ������б�
     * @throws PropertyVetoException
     */
    public void refresh()
    {
        invalidate();
        doLayout();
        repaint();
    }


    /**
     * �����������ɾ���
     * @param baseifc ҵ�����
     * @return BaseValueIfc ��ɾ���ҵ�����
     * @throws QMRemoteException
     */
    public BaseValueIfc save(BaseValueIfc baseifc)
    {
        try
        {
            //   if (oldSelection >= 0 && attributesForm1 != null)
            //   attributesForm1.setObjectAttributeValues();
            object = baseifc;
            updateLinks(); //???
            if (!saveUpdatesOnly)
            {
                if (updateLinkAtts(links))
                {
                    taskLogic.save(removedLinks.elements(), links);
                }
            }
            else
            {
                debug("CappAssociationsPanel.save() updatedLinks: " +
                      updatedLinks);
                if (updateLinkAtts(updatedLinks))
                {
                    taskLogic.save(removedLinks.elements(), updatedLinks);
                }
            }
            resetHashtables();
            resetCurrentRow();
            setDirty(false);
        }
        catch (Exception _e)
        {
            QM.showExceptionDialog(this, _e.getLocalizedMessage());
            _e.printStackTrace();
        }
        return object;
    }


    /**
     * ���ɾ���Ĺ���
     * @return Enumeration ɾ���Ĺ�����ö��
     */
    public Enumeration getRemoveLinks()
    {
        return removedLinks.elements();
    }


    /**
     * �õ����еĹ����༯��
     * @return Ϊnull������쳣��Ϊ�մ���û��ֵ
     */
    public Vector getLinks()
    {
        Vector lis = new Vector();
        BinaryLinkIfc li = null;
        if (!updateLinkAtts(links))
        {
            return null;
        }
        //modify by wangh on 20080228 ʹ����ʵ����ѡ��˳�򱣴�
        Enumeration keys = links.keys();
        while (keys.hasMoreElements())
        {
            li = (BinaryLinkIfc) links.get(keys.nextElement());
            //���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
			// ��������������������ư�ť   �������������ư�ť֮��Ӧʹ����ʵ����ѡ��˳�򱣴�
//            if ((li.getClass().getName()).equals(
//                    "com.faw_qm.capp.model.QMProcedureQMPartLinkInfo"))
//            {
                Vector temp = new Vector();
                int i = kv.size();
              //���⣨7��20081230 �촺Ӣ�޸�  �޸�ԭ�򣺱�����¹��򣨹�����֮�󣬱��ֽ���Ϊ����״̬
              //��ʱ�ٵ㱣�湤�򣨹�������������Դû��
                if(i > 0)
                {
	                for (int t = 0; t < i; t++)
		            {
		                if (!temp.contains(kv.get(t)))
		                {
		                    temp.add(kv.get(t));
		                }
		            }
		            if (temp.size() > 0)
		            {
		                for (int k = 0; k < temp.size(); k++)
		                {
		                    Object key = temp.get(k);
		                    li = (BinaryLinkIfc) links.get(key);
		                    if(!lis.contains(li) && li != null)//CR7
		                    lis.add(li);
		                }
		            }
                }
              //���⣨7��20081230 �촺Ӣ�޸�  �޸�ԭ�򣺱�����¹��򣨹�����֮�󣬱��ֽ���Ϊ����״̬
              //��ʱ�ٵ㱣�湤�򣨹�������������Դû��
                else
                {
                	if(!lis.contains(li) && li != null)//CR7
                	lis.add(li);
                }
                //CCBegin by liunan 2012-02-27 �򲹶�PO41
                //CR15 begin
                if(this.getLinkClassName().indexOf("Procedure")==-1)
    		{
	            kv.clear();//CR6
    		}
    		//CR15 end
    		//CCEnd by liunan 2012-02-27
	            temp.clear();       
//            }
//            else
//            {
//                lis.add(li);
//            }
        }

        return lis;

    }

    private void ShowDialog()
    {

    }


    /**
     * �����������
     * @param defName String ���������
     * @param type String �����������
     */
    private void handleWrongType(String defName, String type)
    {
        String msg = defName + checkRB.getString("87") + type +
                     checkRB.getString("88");
        String title = checkRB.getString("exception");
        JOptionPane.showMessageDialog(getParentJFrame(),
                                      msg,
                                      title,
                                      JOptionPane.INFORMATION_MESSAGE);

    }

    private void handleWrongRange(String defName, String l, String u)
    {
        Object[] obj =
                {defName, l, u};
        String msg = QMMessage.getLocalizedMessage(
                "com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanelRB",
                "89", obj);
        String title = checkRB.getString("exception");
        JOptionPane.showMessageDialog(getParentJFrame(),
                                      msg,
                                      title,
                                      JOptionPane.INFORMATION_MESSAGE);

    }


    /**
     * ���panel�����ݵ���Ч��
     * @return boolean ��ЧΪtrue
     */
    public boolean check()
    {
        boolean flag = true;
        int i = otherSideAttributes.length;
        int j = multiListLinkAttributes.length;

        BinaryLinkIfc binarylinkinfo;
        for (Enumeration enumeration1 = links.elements();
                                        enumeration1.hasMoreElements(); )
        {
            binarylinkinfo = (BinaryLinkIfc) enumeration1.nextElement();
            String obj = taskLogic.getOtherSideBsoID(binarylinkinfo);
            for (int k = i; k < i + j; k++)
            {
                String mtype = null;
                String maxV = null;
                String minV = null;
                try
                {
                    mtype = taskLogic.getPropertyType(multiListLinkAttributes[k -
                            i], binarylinkinfo);
                    ExtendAttModel model = null;
                    if (container != null)
                    {
                        model = container.findExtendAttModel(
                                multiListLinkAttributes[k - i]);
                        if (mtype == null)
                        {
                            mtype = model.getAttType();

                        }
                    }
                    PropertyDescript pt = taskLogic.getPropertyDescript(
                            multiListLinkAttributes[k - i],
                            getLinkClassName());
                    if (pt != null)
                    {
                        maxV = pt.getLimitValue("UpperLimit");
                        minV = pt.getLimitValue("LowerLimit");
                    }
                    else
                    {
                        if (model.getMaxValue() != null)
                        {
                            maxV = model.getMaxValue();
                        }
                        if (model.getMinValue() != null)
                        {
                            minV = model.getMinValue();
                        }
                    }
                }
                catch (QMRemoteException ee)
                {
                    JOptionPane.showMessageDialog(getParent(),
                                                  ee.getClientMessage(),
                                                  checkRB.getString("exception"),
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return false;
                }
                catch (ClassNotFoundException cnfe)
                {
                    return false;
                }
                System.out.println("obj========================"+obj);
                int row = getObjectRow(obj);
                String defName = multiList.getHeading(k);
                System.out.println("row==============="+row);
                System.out.println("k==============="+k);
                Object theObj = multiList.getSelectedObject(row, k);
                if (mtype.equals("java.lang.String") || mtype.equals("String"))
                {
                }
                else
                if (mtype.equals("java.lang.Boolean") ||
                    mtype.endsWith("boolean"))
                {
                }
                else
                if (mtype.equals("int") || mtype.equals("java.lang.Integer"))
                {
                    if (!(theObj == null || theObj.equals("")))
                    {
                        int d;
                        try
                        {
                            d = Integer.parseInt((String) theObj);

                        }
                        catch (NumberFormatException e)
                        {
                            handleWrongType(defName, "����");
                            return false;
                        }
                        if (maxV != null && minV != null)
                        {
                            if (d > Integer.parseInt(maxV) ||
                                d < Integer.parseInt(minV))
                            {
                                handleWrongRange(defName, minV, maxV);
                                return false;
                            }
                        }
                    }
                    //add by wangh on 20080218(���theObjΪ��ʱ�Ĵ�����ʾ)
                    else
                    {
                        handleWrongRange(defName, minV, maxV);
                        return false;
                    }
                }
                else
                if (mtype.equals("long") || mtype.equals("java.lang.Long"))
                {
                    if (!(theObj == null || theObj.equals("")))
                    {
                        long d;
                        try
                        {
                            d = Long.parseLong((String) theObj);
                        }
                        catch (NumberFormatException e)
                        {
                            handleWrongType(defName, "����");
                            return false;
                        }
                        if (maxV != null && minV != null)
                        {
                            if (d > Long.parseLong(maxV) ||
                                d < Long.parseLong(minV))
                            {
                                handleWrongRange(defName, minV, maxV);
                                return false;
                            }
                        }

                    }
//                  add by wangh on 20080218(���theObjΪ��ʱ�Ĵ�����ʾ)
                    else
                    {
                        handleWrongRange(defName, minV, maxV);
                        return false;
                    }
                }
                else
                if (mtype.equals("double") || mtype.equals("java.lang.double"))
                {
                    if (!(theObj == null || theObj.equals("")))
                    {
                        double d;
                        try
                        {
                            d = Double.parseDouble((String) theObj);
                        }
                        catch (NumberFormatException e)
                        {
                            handleWrongType(defName, "����");
                            return false;
                        }
                        if (maxV != null && minV != null)
                        {
                            if (d > Double.parseDouble(maxV) ||
                                d < Double.parseDouble(minV))
                            {
                                handleWrongRange(defName, minV, maxV);
                                return false;
                            }
                        }

                    }
//                  add by wangh on 20080218(���theObjΪ��ʱ�Ĵ�����ʾ)
                    else
                    {
                        handleWrongRange(defName, minV, maxV);
                        return false;
                    }
                }
                else
                if (mtype.equals("double*double"))
                {
                    if (!(theObj == null || theObj.equals("")))
                    {
                        StringTokenizer nizer = new StringTokenizer((String)
                                theObj, "*");
                        if (nizer.countTokens() != 2)
                        {
                            handleWrongType(defName, "����");
                            return false;
                        }
                        else
                        {
                            String s1 = nizer.nextToken();
                            try
                            {
                                Double.parseDouble(s1);
                            }
                            catch (NumberFormatException e)
                            {
                                handleWrongType(defName, "����");
                                return false;
                            }
                            String s2 = nizer.nextToken();
                            try
                            {
                                Double.parseDouble(s2);
                            }
                            catch (NumberFormatException e)
                            {
                                handleWrongType(defName, "����");
                                return false;
                            }

                        }

                    }
                    //add by wangh on 20080218(���theObjΪ��ʱ�Ĵ�����ʾ)
                    else
                    {
                        handleWrongRange(defName, minV, maxV);
                        return false;
                    }
                }

                else
                if (mtype.equals("float") || mtype.equals("java.lang.Float"))
                {
                    if (!(theObj == null || theObj.equals("")))
                    {
                        float d;
                        try
                        {
                            d = Float.parseFloat((String) theObj);
                        }
                        catch (NumberFormatException e)
                        {
                            handleWrongType(defName, "����");
                            return false;
                        }
                        if (maxV != null && minV != null)
                        {
                            if (d > Float.parseFloat(maxV) ||
                                d < Float.parseFloat(minV))
                            {
                                handleWrongRange(defName, minV, maxV);
                                return false;
                            }
                        }

                    }
//                  add by wangh on 20080218(���theObjΪ��ʱ�Ĵ�����ʾ)
                    else
                    {
                        handleWrongRange(defName, minV, maxV);
                        return false;
                    }
                }
                else
                if (mtype.endsWith("com.faw_qm.codemanage.model.CodingIfc"))
                {
                }

            }
        }
        return flag;

    }


    /**
     * ���¹����������
     * @param theLinks ��Ҫ���µĹ�������
     * @return boolean ���³ɹ�Ϊtrue
     */
    synchronized private boolean updateLinkAtts(Hashtable theLinks)
    {
        String obj = null;
        BinaryLinkIfc binarylinkinfo = null;
        int i = otherSideAttributes.length;
        int j = multiListLinkAttributes.length;
        for (Enumeration enumeration1 = theLinks.elements();
                                        enumeration1.hasMoreElements();
                                        theLinks.put(obj, binarylinkinfo))
        {
            try
            {
            	
                binarylinkinfo = (BinaryLinkIfc) enumeration1.nextElement();
                obj = taskLogic.getOtherSideBsoID(binarylinkinfo);
                ExtendAttContainer container1 = null;
                if (binarylinkinfo instanceof ExtendAttriedIfc)
                {
                    if (((ExtendAttriedIfc) binarylinkinfo).getExtendAttributes() != null)
                    {
                        container1 = ((ExtendAttriedIfc) binarylinkinfo).
                                     getExtendAttributes();
                    }
                }
                if (container1 == null && container != null)
                {
                    container1 = container.duplicate();
                }
                for (int k = i; k < i + j; k++)
                {
                    Method method = taskLogic.getSetterMethod(
                            multiListLinkAttributes[k - i], binarylinkinfo);
                    String mtype = taskLogic.getPropertyType(
                            multiListLinkAttributes[k - i], binarylinkinfo);
                    ExtendAttModel model = null;
                    if (secondeTypeValue != null)
                    {
                        model = container1.findExtendAttModel(
                                multiListLinkAttributes[k - i]);
                        if (mtype == null)
                        {
                            mtype = model.getAttType();
                        }
                    }
                    int row = getObjectRow(obj);
                    Object s1 = "";
                    Object[] attrs = new Object[1];
                    Object theObj = multiList.getSelectedObject(row, k);

                    if (method == null)
                    {
                        if (model != null)
                        {
                            model.setAttValue(theObj);
                        }
                    }
                    if (method != null)
                    {
                        if (mtype.equals("java.lang.String"))
                        {
                            attrs[0] = (String) theObj;
                            method.invoke(binarylinkinfo, attrs);

                        }
                        else
                        if (mtype.equals("java.lang.Boolean") ||
                            mtype.endsWith("boolean"))
                        {
                            attrs[0] = (Boolean) theObj;
                            method.invoke(binarylinkinfo, attrs);
                        }
                        else
                        if (mtype.equals("int") ||
                            mtype.equals("java.lang.Integer"))
                        {
                            if (theObj == null || theObj.equals(""))
                            {
                                attrs[0] = new Integer(0);
                            }
                            else
                            {
                                String str1 = (String) theObj;
                                attrs[0] = new Integer(str1);
                            }
                        }
                        else
                        if (mtype.equals("long") ||
                            mtype.equals("java.lang.Long"))
                        {
                            if (theObj == null || theObj.equals(""))
                            {
                                attrs[0] = new Long(0);
                            }
                            else
                            {
                                String str1 = (String) theObj;
                                attrs[0] = new Long(str1);
                            }
                        }
                        else
                        if (mtype.equals("float") ||
                            mtype.equals("java.lang.Float"))
                        {
                            if (theObj == null || theObj.equals(""))
                            {
                                attrs[0] = new Float(0);
                            }
                            else
                            {
                                String str1 = (String) theObj;
                                attrs[0] = new Float(str1);
                            }
                        }
                        //20060710Ѧ���޸ģ��˴���ж�double���͵�
                        else
                        if (mtype.equals("double") ||
                            mtype.equals("java.lang.Double"))
                        {
                            if (theObj == null || theObj.equals(""))
                            {
                                attrs[0] = new Double(0);
                            }
                            else
                            {
                                String str1 = (String) theObj;
                                attrs[0] = new Double(str1);
                            }
                        }

                        else
                        if (mtype.endsWith(
                                "com.faw_qm.codemanage.model.CodingIfc"))
                        {
                            String str1 = (String) theObj;
                            Collection col = (Collection) codingMap.get(new
                                    Integer(k));
                            Iterator ite = col.iterator();
                            // CodingIfc code;
                            while (ite.hasNext())
                            {
                                CodingIfc code1 = (CodingIfc) ite.next();
                                if (code1.getCodeContent().equals(str1))
                                {
                                    attrs[0] = code1;
                                }
                            }
                        }
                        else
                        {
                            attrs[0] = theObj;
                        }
                        method.invoke(binarylinkinfo, attrs);
                    }
                }
                if (binarylinkinfo instanceof ExtendAttriedIfc)
                {
                    ((ExtendAttriedIfc) binarylinkinfo).setExtendAttributes(
                            container1);
                }
                
                //begin CR9
                if(binarylinkinfo instanceof QMProcedureUsageResourceLinkIfc)//CR10
                {
                for(int n = 0; n < upDownVec.size(); n++)
                {
                    String bvi = (String)upDownVec.get(n);
                    if(binarylinkinfo.getRightBsoID().equals(bvi))
                    {
                        if(binarylinkinfo instanceof QMProcedureQMToolLinkIfc)
                        {
                            QMProcedureQMToolLinkIfc pt = (QMProcedureQMToolLinkIfc)binarylinkinfo;
                            pt.setSeq(n);
                        }
                        //CCBegin SS1
                		else if(binarylinkinfo instanceof QMProcedureQMEquipmentLinkIfc)
                		{
                			QMProcedureQMEquipmentLinkIfc pe = (QMProcedureQMEquipmentLinkIfc)binarylinkinfo;
                			pe.setSeq(n);  			
                		}
                		else if(binarylinkinfo instanceof QMProcedureQMMaterialLinkIfc)
                		{
                			QMProcedureQMMaterialLinkIfc pm = (QMProcedureQMMaterialLinkIfc)binarylinkinfo;
                			pm.setSeq(n);
                		}
                		else if(binarylinkinfo instanceof QMProcedureQMPartLinkIfc)
                		{
                			QMProcedureQMPartLinkIfc pp = (QMProcedureQMPartLinkIfc)binarylinkinfo;
                			pp.setSeq(n);
                		}
                        //CCEnd SS1
                        break;
                    }
                }
              }
                //end CR9
            }
            catch (Exception _e)
            {
                _e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    /**
     * �õ��������ڵ��к�
     * @param id ����id
     * @return int �������ڵ��к�
     */
    private int getObjectRow(String id)
    {
        int col = labels.length + multiListLinkAttributes.length;
        int rows = multiList.totalObjects();
        int row = -1;
        String rowID = null;
        //CCBegin SS10
//        int s = multiList.tableColumn;
//        int a = multiList.getCol();
//        System.out.println("�е�����===="+s+"------"+a);
        System.out.println("col======================="+col);
        System.out.println("rows======================="+rows);
        for(int i = 0;i<col;i++){
        	System.out.println(i+"======================="+multiList.getCellText(0, i));
        }
//        System.out.println("1======================="+multiList.getCellText(0, 1));
//        System.out.println("2======================="+multiList.getCellText(0, 2));
//        System.out.println("3======================="+multiList.getCellText(0, 3));
//        System.out.println("4======================="+multiList.getCellText(0, 4));
        //CCEnd SS10
        
        for (int i = 0; i < rows; i++)
        {

            rowID = multiList.getCellText(i, col);
            System.out.println("rowID============="+rowID);
            if (rowID != null && rowID.equals(id))
            {
                row = i;
                break;
            }
        }
        return row;
    }


    /**
     * ����ѡ�е���
     */
    protected void resetCurrentRow()
    {
        int i = multiList.getSelectedRow();
        try
        {
            if (i >= 0)
            {
                // multiList.getCellText(i, 0);
                oldSelection = i;
                attributesFormSetObjectInProcess = true;
                //  if (attributesForm1 != null)
                //    attributesForm1.setObject(getSelectedLink());
                attributesFormSetObjectInProcess = false;
                setCurrentLinkAttributeDirty(false);
                return;
            }
        }
        catch (Exception _e)
        {
            _e.printStackTrace();
        }
    }


    /**
     * ���ݹ��캯������Ĳ��������û�����
     * @throws Exception
     */
    protected void createUI()
            throws Exception
    {
        if (mode.equals(VIEW_MODE))
        {
            removeEditButtons();

            return;
        }
        if (mode.equals(EDIT_MODE))
        {
            addEditButtons();

        }
    }


    /**
     * �Ƴ��༭��ť
     */
    protected void removeEditButtons()
    {

        buttonPanel.remove(browseButton);
        buttonPanel.remove(removeButton);
        
        //CCBegin SS6
        if (structButton != null) {
            buttonPanel.remove(structButton);
          }
          if (routelistButton != null) {
            buttonPanel.remove(routelistButton);
          }
        
        //CCEnd SS6
          
          //CCBegin SS9
          if (partaddButton != null) {
              buttonPanel.remove(partaddButton);
            }
          //CCEnd SS9
          
          
          //CCBegin SS8
          if (getResoucebyProcedureButton != null) {
              buttonPanel.remove(getResoucebyProcedureButton);
            }
          
          //CCEnd SS8
          
        
        //CCBegin SS5
        if(upButton!=null)
        {
        	buttonPanel.remove(upButton);
        	buttonPanel.remove(downButton);
        }
        //CCEnd SS5
        
        if (insertButton != null)
        {
            buttonPanel.remove(insertButton);
        }
        if (calculateButton != null)
        {
            buttonPanel.remove(calculateButton);
        }
        
        //CCBegin SS7
        
        if (sonButton != null) {
            buttonPanel.remove(sonButton);
          }
        //CCEnd SS7
        
        //CCBegin SS2
//      CCBeginby leixiao 2009-7-8 ���Ӱ�ť  
//        if (rationJButton != null)
//        {
//            buttonPanel.remove(rationJButton);
//        }
//      CCEndby leixiao 2009-7-8 ���Ӱ�ť 
        //CCEnd SS2
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 1;
        gridbagconstraints.weighty = 1.0D;
        gridbagconstraints.anchor = 18;
        gridbagconstraints.fill = 2;
        gridbagconstraints.ipady = -4;
        gridbagconstraints.insets = new Insets(5, 5, 0, 5);
        ((GridBagLayout) buttonPanel.getLayout()).setConstraints(viewButton,
                gridbagconstraints);
        buttonPanel.doLayout();
        buttonPanel.repaint();
        doLayout();
        repaint();
    }


    /**
     * ��ӱ༭��ť
     */
    protected void addEditButtons()
    {
        if (browseButton == null)
        {
            createEditButtons();
        }
        //�����ť
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 0;
        gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;
        gridbagconstraints.fill = GridBagConstraints.HORIZONTAL;
        gridbagconstraints.insets = new Insets(5, 5, 0, 5);
        gridbagconstraints.ipady = -4;
        ((GridBagLayout) buttonPanel.getLayout()).setConstraints(
                browseButton,
                gridbagconstraints);
        buttonPanel.add(browseButton, gridbagconstraints);
        //ɾ����ť

        GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
        gridbagconstraints1.gridx = 0;
        gridbagconstraints1.gridy = 2;
        if (!(isInsert || isCalculate))
        {
            gridbagconstraints1.weighty = 1.0D;
        }
        gridbagconstraints1.ipady = -4;
        gridbagconstraints1.anchor = GridBagConstraints.NORTHWEST;
        gridbagconstraints1.fill = GridBagConstraints.HORIZONTAL;
        gridbagconstraints1.insets = new Insets(5, 5, 0, 5);
        buttonPanel.add(removeButton, gridbagconstraints1);
        
        //CCBegin SS6
        
        
      //�ṹ������ť
        if (isStruct) 
        {
          GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
          gridbagconstraints2.gridx = 0;
          
          if (hasSon){
        	  gridbagconstraints2.gridy = 6;
          }else{
             gridbagconstraints2.gridy = structButtonLocation;
          }
          if (! (isCalculate || isRouteList || isInsert || hasSon)) {
            gridbagconstraints2.weighty = 1.0D;
          }
          gridbagconstraints2.ipady = -4;
          gridbagconstraints2.anchor = GridBagConstraints.NORTH;
          gridbagconstraints2.fill = GridBagConstraints.HORIZONTAL;
          gridbagconstraints2.insets = new Insets(5, 5, 0, 5);
          buttonPanel.add(structButton, gridbagconstraints2);
          
        }
        //��׼������ť
        if (isRouteList) 
        {
          GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
          gridbagconstraints2.gridx = 0;
          gridbagconstraints2.gridy = 4;
          if (! (isCalculate || isInsert || hasSon)) {
            gridbagconstraints2.weighty = 1.0D;
          }
          gridbagconstraints2.ipady = -4;
          gridbagconstraints2.anchor = GridBagConstraints.NORTH;
          gridbagconstraints2.fill = GridBagConstraints.HORIZONTAL;
          gridbagconstraints2.insets = new Insets(5, 5, 0, 5);
          buttonPanel.add(routelistButton, gridbagconstraints2);
        }
        
        //CCEnd SS6
        
        //CCBegin SS9
      //��part�����Ϣ��ӹ�����ť
        if (isPartadd) {
          GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
          gridbagconstraints2.gridx = 0;
          gridbagconstraints2.gridy = partaddButtonLocation;
         if (! (isInsert || isCalculate || isStruct || isRouteList || hasSon)) {
            gridbagconstraints2.weighty = 1.0D;
          }
          gridbagconstraints2.ipady = -4;
          gridbagconstraints2.anchor = GridBagConstraints.NORTHWEST;
          gridbagconstraints2.fill = GridBagConstraints.HORIZONTAL;
          gridbagconstraints2.insets = new Insets(5, 5, 0, 5);
          buttonPanel.add(partaddButton, gridbagconstraints2);
        }
        
        //CCEnd SS9
        
        //CCBegin SS7
        if (hasSon) {
        	
            GridBagConstraints gridbagconstraints3 = new GridBagConstraints();
            gridbagconstraints3.gridx = 0;
            gridbagconstraints3.gridy = 7;
            if (! (isCalculate || isInsert)) {
              gridbagconstraints3.weighty = 1.0D;
            }
            gridbagconstraints3.ipady = -4;
            gridbagconstraints3.anchor = GridBagConstraints.NORTH;
            gridbagconstraints3.fill = GridBagConstraints.HORIZONTAL;
            gridbagconstraints3.insets = new Insets(5, 5, 50, 5);
            buttonPanel.add(sonButton, gridbagconstraints3);


        }
        
        //CCEnd SS7
        
        //CCBegin SS8
        //�ӹ�������Դ��������ť
        if (isGetResouceByProcedure) {
          GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
          gridbagconstraints2.gridx = 0;
          gridbagconstraints2.gridy = getResoucebyProcedureButtonLocation;
          if (! (isInsert || isCalculate || isStruct || isRouteList || hasSon)) {
            gridbagconstraints2.weighty = 1.0D;
          }
          gridbagconstraints2.ipady = -4;
          gridbagconstraints2.anchor = GridBagConstraints.NORTH;
          gridbagconstraints2.fill = GridBagConstraints.HORIZONTAL;
          gridbagconstraints2.insets = new Insets(5, 5, 150, 5);
          buttonPanel.add(getResoucebyProcedureButton, gridbagconstraints2);
          
        }
        
        
        //CCEnd SS8

        //���밴ť
        if (isInsert)
        {GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
        gridbagconstraints2.gridx = 0;
        gridbagconstraints2.gridy = insertButtonLocation;
		//���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
		// ��������������������ư�ť   ��Ӱ�ť������Ϊ���һ����ť
//		if (!isCalculate) {
//			gridbagconstraints2.weighty = 1.0D;
//		}
        gridbagconstraints2.ipady = -4;
        gridbagconstraints2.anchor = GridBagConstraints.NORTHWEST;
        gridbagconstraints2.fill = GridBagConstraints.HORIZONTAL;
        gridbagconstraints2.insets = new Insets(5, 5, 0, 5);

        buttonPanel.add(insertButton, gridbagconstraints2);
        }
        //���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
		// ��������������������ư�ť  begin
       
        
        
		if (isUpDown)
		{
			GridBagConstraints gridbagconstraints3 = new GridBagConstraints();
			gridbagconstraints3.gridx = 0;
			gridbagconstraints3.gridy = upButtonLocation;
//			if (!isCalculate) {
//				gridbagconstraints2.weighty = 1.0D;
//			}
			gridbagconstraints3.ipady = -4;
			gridbagconstraints3.anchor = GridBagConstraints.NORTHWEST;
			gridbagconstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridbagconstraints3.insets = new Insets(5, 5, 0, 5);

			buttonPanel.add(upButton, gridbagconstraints3);
			
			GridBagConstraints gridbagconstraints4 = new GridBagConstraints();
			gridbagconstraints4.gridx = 0;
			gridbagconstraints4.gridy = downButtonLocation;
			gridbagconstraints4.weighty = 1.0D;
			gridbagconstraints4.ipady = -4;
			gridbagconstraints4.anchor = GridBagConstraints.NORTHWEST;
			gridbagconstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridbagconstraints4.insets = new Insets(5, 5, 0, 5);
			buttonPanel.add(downButton, gridbagconstraints4);
			
		}
		  //CCBegin SS2
//      CCBeginby leixiao 2009-7-6 ԭ�򣺹��ղ���������ӻ�ȡ����
//        if (isRation)
//        {        	
//        GridBagConstraints gridbagconstraints5 = new GridBagConstraints();
//        gridbagconstraints5.gridx = 0;
//        gridbagconstraints5.gridy = rationButtonLocation;
//        if (!isCalculate)
//        {
//        	gridbagconstraints5.weighty = 1.0D;
//        }
//        gridbagconstraints5.ipady = -4;
//        gridbagconstraints5.anchor = GridBagConstraints.NORTHWEST;
//        gridbagconstraints5.fill = GridBagConstraints.HORIZONTAL;
//        gridbagconstraints5.insets = new Insets(5, 5, 0, 5);
//
////        buttonPanel.add(rationJButton, gridbagconstraints5);
//        }
//      CCEndby leixiao 2009-7-6 
		  //CCEnd SS2
		
        //delete by wangh on 20080320 ȥ��û��ʵ�ּ��㹦�ܵļ��㰴ť
        //���㰴ť
//        if (isCalculate)
//        {
//            GridBagConstraints gridbagconstraints3 = new GridBagConstraints();
//            gridbagconstraints3.gridx = 0;
//            gridbagconstraints3.gridy = calculateButtonLocation;
//            gridbagconstraints3.weighty = 1.0D;
//            gridbagconstraints3.ipady = -4;
//            gridbagconstraints3.anchor = GridBagConstraints.NORTHWEST;
//            gridbagconstraints3.fill = GridBagConstraints.HORIZONTAL;
//            gridbagconstraints3.insets = new Insets(5, 5, 0, 5);
//            buttonPanel.add(calculateButton, gridbagconstraints3);
//        }//delete end
        //�鿴��ť
        GridBagConstraints gridbagconstraints4 = new GridBagConstraints();
        gridbagconstraints4.gridx = 0;
        gridbagconstraints4.gridy = 1;
        gridbagconstraints4.ipady = -4;
        gridbagconstraints4.anchor = GridBagConstraints.NORTHWEST;
        gridbagconstraints4.fill = GridBagConstraints.HORIZONTAL;
        gridbagconstraints4.insets = new Insets(5, 5, 0, 5);
        ((GridBagLayout) buttonPanel.getLayout()).setConstraints(viewButton,
                gridbagconstraints4);
        buttonPanel.doLayout();
        buttonPanel.repaint();
        doLayout();
        repaint();
    }


    /**
     * �����༭��ť
     */
    protected void createEditButtons()
    {

        SymAction symaction = null;
        if (browseButton == null)
        {
            symaction = new SymAction();
            browseButton = new JButton();
            browseButton.setLabel(display("48", null));
            browseButton.setActionCommand("browse");

            //���Ƿ�
            if ((int) browseButtonMne != 0)
            {
                browseButton.setMnemonic(browseButtonMne);
                browseButton.setText(display("48", null) + "(" +
                                     browseButtonMneDisp + "). . .");
            }
            browseButton.addActionListener(symaction);
            helpContext.addComponentHelp(browseButton, "Add");
            browseButton.setBounds(5, 5, 65, 23); //5, 5, 40, 22
        }

        if (removeButton == null)
        {
            removeButton = new JButton();
            removeButton.setActionCommand("remove");
            removeButton.setLabel(display("18", null));
            removeButton.setBounds(5, 5, 65, 23);
            //���Ƿ�
            if ((int) removeButtonMne != 0)
            {
                removeButton.setMnemonic(removeButtonMne);
                removeButton.setText(removeButton.getText() + "(" +
                                     removeButtonMneDisp + ")");
            }

            removeButton.addActionListener(symaction);
            helpContext.addComponentHelp(removeButton, "Remove");
        }
        
        //CCBegin SS6
        
        if (isStruct) 
        {
            if (structButton == null) {
              structButton = new JButton();
              structButton.setActionCommand("struct");
              structButton.setText("�ṹ����");
              structButton.setBounds(5, 5, 65, 23);
              //���Ƿ�
              if ( (int) structButtonMne != 0) {
                structButton.setMnemonic(structButtonMne);
                structButton.setText(structButton.getText() + "(" +
                                     structButtonMneDisp + ")");
              }

              structButton.addActionListener(symaction);
              helpContext.addComponentHelp(structButton, "struct");
            }
          }
        
         if (isRouteList) 
          {
            if (routelistButton == null) {
              routelistButton = new JButton();
              routelistButton.setActionCommand("routelist");
              routelistButton.setText("��׼����");
              routelistButton.setBounds(5, 5, 65, 23);
              //���Ƿ�
              if ( (int) routelistButtonMne != 0) {
                routelistButton.setMnemonic(routelistButtonMne);
                routelistButton.setText(routelistButton.getText() + "(" +
                                        routelistButtonMneDisp + ")");
              }

              routelistButton.addActionListener(symaction);
              helpContext.addComponentHelp(routelistButton, "routelist");
            }

          }
        
        //CCEnd SS6
         
         //CCBegin SS7
         if (hasSon && (sonButton == null)) {
             sonButton = new JButton();
             sonButton.setActionCommand("son");
             sonButton.setText("��ȡ�Ӽ�");
             sonButton.setBounds(5, 5, 65, 23);
             //���Ƿ�
             if ( (int) insertButtonMne != 0) {
               sonButton.setMnemonic(insertButtonMne);
               sonButton.setText(sonButton.getText());
             }

             sonButton.addActionListener(symaction);
             helpContext.addComponentHelp(sonButton, "son");
           }
         
         //CCEnd SS7

        if (isInsert)
        {
            if (insertButton == null)
            {
                insertButton = new JButton();
                insertButton.setActionCommand("insert");
                insertButton.setLabel(display("17", null));
                insertButton.setBounds(5, 5, 65, 23);
                //���Ƿ�
                if ((int) insertButtonMne != 0)
                {
                    insertButton.setMnemonic(insertButtonMne);
                    insertButton.setText(insertButton.getText() + "(" +
                                         insertButtonMneDisp + ")");
                }

                insertButton.addActionListener(symaction);
                helpContext.addComponentHelp(insertButton, "Insert");
            }

        }
        if (isCalculate)
        {
            if (calculateButton == null)
            {
                calculateButton = new JButton();
                calculateButton.setActionCommand("calculate");
                calculateButton.setLabel(display("calculate", null));
                calculateButton.setBounds(5, 5, 65, 23);
                //���Ƿ�
                if ((int) calculateButtonMne != 0)
                {
                    calculateButton.setMnemonic(calculateButtonMne);
                    calculateButton.setText(calculateButton.getText() + "(" +
                                            calculateButtonMneDisp + ")");
                }

                calculateButton.addActionListener(symaction);
                helpContext.addComponentHelp(calculateButton, "Calculate");
            }

        }
      //���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
		// ��������������������ư�ť   
		if (isUpDown) 
		{
			if (upButton == null) {
				upButton = new JButton();
				upButton.setActionCommand("up");
				upButton.setLabel("����");
				upButton.setBounds(5, 5, 65, 23);
				// ���Ƿ�
				if ((int) upButtonMne != 0) {
					upButton.setMnemonic(upButtonMne);
					upButton.setText(upButton.getText() + "("
							+ upButtonMneDisp + ")");
				}

				upButton.addActionListener(symaction);
				helpContext.addComponentHelp(upButton, "Up");
			}

			if (downButton == null) {
				downButton = new JButton();
				downButton.setActionCommand("down");
				downButton.setLabel("����");
				downButton.setBounds(5, 5, 65, 23);
				// ���Ƿ�
				if ((int) downButtonMne != 0) {
					downButton.setMnemonic(downButtonMne);
					downButton.setText(downButton.getText() + "("
							+ downButtonMneDisp + ")");
				}

				downButton.addActionListener(symaction);
				helpContext.addComponentHelp(downButton, "Down");
			}
		}
		
		//CCBegin SS9
		  if (isPartadd) {
		         if (partaddButton == null) {
		           partaddButton = new JButton();
		           partaddButton.setActionCommand("partadd");
		           partaddButton.setText("��ӵ�����");
		           partaddButton.setBounds(5, 5, 65, 23);
		           //���Ƿ�
		           if ( (int) partaddButtonMne != 0) {
		             partaddButton.setMnemonic(
		                 partaddButtonMne);
		             partaddButton.setText(partaddButton.
		                                                 getText() + "(" +
		                                                 partaddButtonMneDisp +
		                                                 ")");
		           }
		           partaddButton.addActionListener(symaction);
		           helpContext.addComponentHelp(partaddButton,
		                                        "partadd");
		         }
		       }
		//CCEnd SS9
		
		//CCBegin SS8
		if (isGetResouceByProcedure) {
		      if (getResoucebyProcedureButton == null) {
		        getResoucebyProcedureButton = new JButton();
		        getResoucebyProcedureButton.setActionCommand("getResoucebyProcedure");
		        getResoucebyProcedureButton.setText("������Դ");
		        getResoucebyProcedureButton.setBounds(5, 5, 65, 23);
		        //���Ƿ�
		        if ( (int) getResoucebyProcedureButtonMne != 0) {
		          getResoucebyProcedureButton.setMnemonic(
		              getResoucebyProcedureButtonMne);
		          getResoucebyProcedureButton.setText(getResoucebyProcedureButton.
		                                              getText() + "(" +
		                                              getResoucebyProcedureButtonMneDisp +
		                                              ")");
		        }

		        getResoucebyProcedureButton.addActionListener(symaction);
		        helpContext.addComponentHelp(getResoucebyProcedureButton,
		                                     "getResoucebyProcedure");
		      }
		    }
		
		//CCEnd SS8
		
		
		  //CCBegin SS2
//      CCBeginby leixiao 2009-7-8 ԭ�򣺹����������壬���ӻ�ȡ���϶���
//        if (isRation)
//        {
//            if (rationJButton == null)
//            {
//            	rationJButton = new JButton();
//            	rationJButton.setActionCommand("ration");
//            	rationJButton.setLabel("��ȡ����");
//            	rationJButton.setBounds(5, 5, 65, 23);
////                //���Ƿ�
//                if ((int) insertButtonMne != 0)
//                {
//                	rationJButton.setMnemonic(insertButtonMne);
//                	rationJButton.setText(rationJButton.getText() + "(" +
//                                         "R" + ")");
//                }
//
//                rationJButton.addActionListener(symaction);
//                helpContext.addComponentHelp(rationJButton, "Ration");
//            }           
//        }
//      CCEndby leixiao 2009-7-8 ԭ�򣺹����������壬���ӻ�ȡ���϶���
// 
		  //CCEnd SS2
    }

   //CCBegin SS7
    
    void sonButton_ActionPerformed(ActionEvent actionevent) {
        try {
          for (; ! (component instanceof Frame);
               component = component.getParent()) {
            ;
          }
          SonPartSelectJDialog selectpart = new SonPartSelectJDialog( (
              Frame) component, "ѡ�����", true);
          selectpart.setBaseValueIfc(this.linkedTechnics);
          selectpart.setDikefTech(this.linkedTechnics);
          selectpart.setCodingIfc( (CodingIfc) techTypeIfc);
          setPanel1(selectpart);
          selectpart.insertPartToMultilist();
          selectpart.setVisible(true);
          return;

        }
        catch (Exception _e) {
          _e.printStackTrace();
        }
      }
    
    public void setPanel1(SonPartSelectJDialog sonpart) {
        sonpart.setCappAssociationsPanel(this);
      }

    //CCEnd SS7
    /**
     * ���ò��밴ť
     * @param g boolean �Ƿ����
     * @param buttonLocation int ���밴ť�ǵڼ�����ť
     */
    public void setIsInsert(boolean g, int buttonLocation)
    {
        insertButtonLocation = buttonLocation;
        isInsert = g;
    }
    

    
    /**  
     * ��ȡ���϶��ť
     * @param g boolean �Ƿ����
     * @param buttonLocation int ���밴ť�ǵڼ�����ť
     */
    public void setIsRation(boolean g, int buttonLocation)
    {
        rationButtonLocation = buttonLocation;
        isRation = g;
    }
    

    /**
     * ���ü��㹦��
     * @param g boolean �Ƿ������
     * @param calculateButtonLocation int ���㰴ť�ǵڼ�����ť
     */

    public void setIsCalculate(boolean g, int calculateButtonLocation)
    {
        this.calculateButtonLocation = calculateButtonLocation;
        isCalculate = g;
    }
    
    

    //CCBegin SS6
    
    /**
     * ���ýṹ������ť
     * @param g boolean �Ƿ����
     * @param buttonLocation int ���밴ť�ǵڼ�����ť
     */
    public void setIsStruct(boolean g, int buttonLocation) {
      structButtonLocation = buttonLocation;
      isStruct = g;
    }

    /**
     * ������׼������ť
     * @param g boolean �Ƿ����
     * @param buttonLocation int ���밴ť�ǵڼ�����ť
     */
    public void setIsRouteList(boolean g, int buttonLocation) {
      routelistButtonLocation = buttonLocation;
      isRouteList = g;
    }

    
    //CCEnd SS6

    /**
     * ����(���)����
     */
    protected void resetHashtables()
    {
        removedLinks = new Hashtable();
        updatedLinks = new Hashtable();
    }


    /**
     * ���û������Ľ�����¹���
     * @throws Exception
     * @throws QMException
     */
    protected void updateLinks()
            throws QMRemoteException
    { //delete it ???
        for (Enumeration enumeration = links.elements();
                                       enumeration.hasMoreElements(); )
        {
            BinaryLinkIfc binarylinkinfo = (BinaryLinkIfc) enumeration.
                                           nextElement();
            taskLogic.setRoleBsoID(binarylinkinfo,
                                   ((BaseValueIfc) object).getBsoID(),
                                   role);
        }
    }


    /**
     *  ��ȥAttributeForm���
     */
    public void removeAttributeForm()
    {
        //  remove(panel1);
        refresh();
    }


    /**
     * ��ö���������
     * @param component
     * @return
     */
    Frame getTopLevelParent(Component component)
    {
        Object obj;
        for (obj = component; ((Component) (obj)).getParent() != null;
                   obj = ((Component) (obj)).getParent())
        {
            ;
        }
        if (obj instanceof Frame)
        {
            return (Frame) obj;
        }
        else
        {
            return null;
        }
    }


    /**
     * �����б���
     * @throws PropertyVetoException
     */
    protected void resetLabels()
            throws PropertyVetoException
    {
        int i = labels.length + multiListLinkAttributes.length;
        String as[] = new String[i + 1];
        for (int j = 0; j < labels.length; j++)
        {
            as[j] = labels[j];
        }
        for (int k = 0; k < multiListLinkAttributes.length; k++)
        {
            int l = k + labels.length;
            if (linkClassName == null || linkClassName.length() == 0)
            {
                as[l] = "";
            }
            else
            {
                try
                {
                    as[l] = taskLogic.getDisplayName(multiListLinkAttributes[k],
                            linkClassName);
                    // as[l] = multiListLinkAttributes[k];
                    // taskLogic.getDisplayName(multiListLinkAttributes[k],linkClassName);
                }
                catch (ClassNotFoundException classnotfoundexception)
                {
                    classnotfoundexception.printStackTrace();
                    PropertyChangeEvent propertychangeevent = new
                            PropertyChangeEvent(this,
                                                "multiListLinkAttributes", null,
                                                multiListLinkAttributes[k]);
                    throw new PropertyVetoException(classnotfoundexception.
                            getLocalizedMessage(), propertychangeevent);
                }
                catch (Exception introspectionexception)
                {
                    PropertyChangeEvent propertychangeevent1 = new
                            PropertyChangeEvent(this,
                                                "multiListLinkAttributes", null,
                                                multiListLinkAttributes[k]);
                }
            }
        }
        as[i] = "";
        multiList.setHeadings(as);
        if (VERBOSE)
        
        {
            System.out.println("���Գ��ȣ�" + as.length);

        }

        defaultColumnWidths();

        if (VERBOSE)
        {
            System.out.println("the QMMultiList's Headings count is:::" +
                               as.length);
        }
    }


    /**
     * Ӧ��Ĭ���п�
     * @throws PropertyVetoException
     */
    protected void defaultColumnWidths()
            throws PropertyVetoException
    {
        int i = multiList.getNumberOfCols();
        int j = i - 1;
        int as[] = new int[i];
        for (int k = 0; k < j; k++)
        {
            as[k] = 1;
        }
        as[j] = 0;
        multiList.setRelColWidth(as);
        
        
    }

    /**
     * ���þ���Ĺ����������б���
     * @param c Collection ��������
     */
    public void setLinks(Collection c)

    {
        try
        {
            for (Iterator iterator = c.iterator(); iterator.hasNext(); )
            {
                BaseValueIfc bvi = (BaseValueIfc) iterator.next();
                if (bvi instanceof BinaryLinkIfc)
                {
                    addToTable((BinaryLinkIfc) bvi, false);
                }
            }
            if (multiList.getNumberOfRows() > 0)
            {
                multiList.selectRow(0);
                return;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

        }

    }


//add by wangh on 20080229
    /**
     *���򷽷���
     *���磬vec���Ԫ����QMPart,�������䰴����Ž������У������˵��ã�
     *Vector vec=getSortingResults(vec,Class.forName("fawcapp.bom.QMPart"),"getNumber",false);
     *@param  vec  ��Ҫ�������Ԫ�����������
     *@param  class1   ����Ԫ�ص�����
     *@param  methodName ����ķ�����
     *@param  ascending ���Ϊ�棬���������У�Ϊ�٣��򰴽�������
     *@return Vector ����������
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws java.lang.reflect.InvocationTargetException
     * ���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
	 * ��������������������ư�ť
     */
    public static Vector getSortingResults(Vector vec, Class class1,
                                           String methodName, boolean ascending)
            throws NoSuchMethodException, IllegalAccessException,
            java.lang.reflect.InvocationTargetException
    {
        if (null == vec || vec.isEmpty() || vec.size() == 1)
        {
            return vec;
        }

        class Com implements Comparator
        {
            Class class1;
            String methodName;
            boolean ascending;
            Com(Class class1, String methodName, boolean ascending)
            {
                this.class1 = class1;
                this.methodName = methodName;
                this.ascending = ascending;
            }


            /**
             * �ȽϷ���
             * @param obj1 Object ���Ƚϲ���
             * @param obj2 Object ���Ƚϲ���
             * @return int
             */
            public int compare(Object obj1, Object obj2)
            {
                int compare = 0;
                try
                {
                    Object[] object =
                            {};
                    Class[] class2 =
                            {};
                    java.lang.reflect.Method method1 = class1.getMethod(
                            methodName, class2);

                    if (method1.invoke(obj1, object) instanceof Number)
                    {
                        double num1 = ((Number) method1.invoke(obj1, object)).
                                      doubleValue();
                        double num2 = ((Number) method1.invoke(obj2, object)).
                                      doubleValue();
                        if (num1 > num2)
                        {
                            compare = 1;
                        }
                        else if (num1 < num2)
                        {
                            compare = -1;
                        }
                        else
                        {
                            compare = 0;
                        }
                    }
                    else
                    {
                        if (null == method1.invoke(obj1, object))
                        {
                            compare = 1;
                        }
                        else if (null == method1.invoke(obj2, object))
                        {
                            compare = -1;
                        }
                        else
                        {
                        	//���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
							//��������������������ư�ť  �����޸İ�id�ȽϵĹ���
							String str1 = (String) method1.invoke(obj1, object);
							String str2 = (String) method1.invoke(obj2, object);
							//CCBegin SS1
							if(str1.contains("(BSXUP)")){
								str1=str1.substring(0,str1.indexOf("(BSXUP)"));
					        }
					        if(str2.contains("(BSXUP)")){
					        	str2=str2.substring(0,str2.indexOf("(BSXUP)"));
					        }
					        //CCEnd SS1
							if(str1.indexOf("_")!=-1&&str2.indexOf("_")!=-1)
							{
								int i1 = str1.indexOf("_");
								String  strg1 = str1.substring(i1+1);
								long number1 = Integer.parseInt(strg1);
								int i2 = str1.indexOf("_");
								String  strg2 = str2.substring(i2+1);
								long number2 = Integer.parseInt(strg2);
								if (number1 > number2) {
									compare = 1;
								} else if (number1 < number2) {
									compare = -1;
								} else {
									compare = 0;
								}
							}
							else
							{
								compare = str1.compareTo(str2);
							}
                        }
                    }
                }
                catch (Exception e)
                {e.printStackTrace();
                }
                if (ascending)
                {
                    return compare;
                }
                else
                {
                    return -compare;
                }
            }
        }


        Collections.sort(vec, new Com(class1, methodName, ascending));

        return vec;

    }


//add end

    /**
     *  ���ʵ�ǰ�����������,����ʾ��multilist��
     */
    synchronized protected void populate()
    {
        //long d1=System.currentTimeMillis();
        try
        {
            Collection queryresult = (Collection) taskLogic.getRelations();

            if (VERBOSE)
            {
                System.out.println("�ҵ��Ĺ�����һ�߶�������������" + queryresult.size());
            }
            if (queryresult == null || queryresult.size() == 0)
            {
                return;
            }
            Collection links = new ArrayList();
            //���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
			// ��������������������ư�ť  
            Class cn = null;
            Vector vec = null;
            //20060720Ѧ���޸ģ�ԭ��queryresult��ԭ���Ĺ������ϸ�Ϊ��������һ�߶���ļ���
            Object[] linkAndOther;
            //���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
			// ��������������������ư�ť   �������������ư�ť֮��Ӧʹ����ʵ����ѡ��˳�򱣴� begin
            for (Iterator it = queryresult.iterator(); it.hasNext(); )
            {
                linkAndOther = (Object[]) it.next();
                //CCBegin SS1
                if(linkAndOther[0] instanceof QMProcedureQMEquipmentLinkIfc)//�豸
                {
                	if(((QMProcedureQMEquipmentLinkIfc) linkAndOther[0]).getShowinfo()!=null)
                	{
                		otherSideObjects.put(((BaseValueIfc) linkAndOther[1]).getBsoID(),((QMProcedureQMEquipmentLinkIfc) linkAndOther[0]).getShowinfo());
                	}
                	else
                	{
                		otherSideObjects.put( ( (BaseValueIfc) linkAndOther[1]).getBsoID(),
                                     linkAndOther[1]);
                  }
                }
                else if(linkAndOther[0] instanceof QMProcedureQMToolLinkIfc)//��װ
                {
                	if(((QMProcedureQMToolLinkIfc) linkAndOther[0]).getShowinfo()!=null)
                	{
                		otherSideObjects.put(((BaseValueIfc) linkAndOther[1]).getBsoID(),((QMProcedureQMToolLinkIfc) linkAndOther[0]).getShowinfo());
                	}
                	else
                	{
                		otherSideObjects.put( ( (BaseValueIfc) linkAndOther[1]).getBsoID(),
                                     linkAndOther[1]);
                  }
                }
                else if(linkAndOther[0] instanceof QMProcedureQMMaterialLinkIfc)//����
                {
                	if(((QMProcedureQMMaterialLinkIfc) linkAndOther[0]).getShowinfo()!=null)
                	{
                		otherSideObjects.put(((BaseValueIfc) linkAndOther[1]).getBsoID(),((QMProcedureQMMaterialLinkIfc) linkAndOther[0]).getShowinfo());
                	}
                	else
                	{
                		otherSideObjects.put( ( (BaseValueIfc) linkAndOther[1]).getBsoID(),
                                     linkAndOther[1]);
                  }
                }
                else
                //CCEnd SS1
                otherSideObjects.put(((BaseValueIfc) linkAndOther[1]).getBsoID(),
                                     linkAndOther[1]);
//            modify by wangh on 20080229 ʹ����ʵ���ڹ����а�����˳����ʾ
                sortV.add(linkAndOther[0]);
                cn = linkAndOther[0].getClass();
            }
				//if (cn.equals("com.faw_qm.capp.model.QMProcedureQMToolLinkInfo")) {
				vec = getSortingResults(
						sortV,
						cn,
						"getBsoID", true);

				//CCbegin SS1
				if(vec!=null && vec.size()>0)
			     {
			     	  if(vec.get(0) instanceof QMProcedureUsageResourceLinkIfc)
			     	{
			     		vec=getSortingResults(sortV, cn, "getSeq", true);
			     		}
			     }
                
                //CCEnd SS1
                
				for (int v = 0; v < vec.size(); v++) {
					addToTable((BinaryLinkIfc) vec.get(v), false);
				}
				vec.clear();
				//���⣨6��end
//			} else {
//				for (Iterator it = queryresult.iterator(); it.hasNext();) {
//					linkAndOther = (Object[]) it.next();
//					System.out.println("(BinaryLinkIfc) linkAndOther[0]:"
//							+ (BinaryLinkIfc) linkAndOther[0]);
//					addToTable((BinaryLinkIfc) linkAndOther[0], false);
//				}
//			}
            //modify end
            if (multiList.getNumberOfRows() > 0)
            {
                multiList.selectRow(0);
                // return;
            }
        }
        catch (Exception runtimeexception)
        {
            runtimeexception.printStackTrace();
            return;
        }
        //begin CR3
        finally
        {
        	multiList.intAfterThread();
        }
        //end CR3
        // long d2=System.currentTimeMillis();
        // System.out.println(this.getOtherSideClassName()+"��ӹ���ʱ��="+(d2-d1));
    }


    /**
     * ��Ӿ���Ĺ����������б���
     * @param binarylinkinfo ����ֵ����
     * @param row ��ǰ����
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @see BinaryLinkInfo
     */
    public void addToTable(BinaryLinkIfc binarylinkinfo, int row)
            throws
            InvocationTargetException, IllegalAccessException,
            NoSuchMethodException
    {
        synchronized (addAndRemove)
        {
            try
            {
                //����һ�����������������һ������
                if (otherSideRole == null)
                {
                    otherSideRole = taskLogic.getOtherSideRole();
                    //String othersideobjectid = binarylinkinfo.getRoleBsoID(otherrole);//??? test
                }
                if (VERBOSE)
                {
                    System.out.println(
                            "addToTable method ...otherSideRole is:::" +
                            otherSideRole);
                }
                String othersideobjectid = taskLogic.getRoleBsoID(
                        binarylinkinfo,
                        otherSideRole); //binarylinkinfo.getRoleBsoID(otherrole);

                BaseValueIfc persistable = (BaseValueIfc) otherSideObjects.get(
                        othersideobjectid);
                if (persistable == null)
                {
                    persistable = taskLogic.refreshInfo(othersideobjectid);
                    otherSideObjects.put(othersideobjectid, persistable);
                }
                String s = othersideobjectid; //(new Integer(binarylinkinfo.hashCode())).toString();
                //              add by wangh on 20080228 ��˳�򱣴��ֵ.
                kv.add(s);
                upDownVec.add(s);//CR6
//                System.out.println("s===="+s);
                //add end
                debug("CappAssociationsPanel.addToTable() link: " +
                      binarylinkinfo);
                debug("CappAssociationsPanel.addToTable() id: " + s);
                //if (!links.containsKey(s)) {
                int i = otherSideAttributes.length;
                int j = multiListLinkAttributes.length;
                int k = 0;
                int i1;
                i1 = row;
//                Object obj = null;
                Method method = taskLogic.getGetterMethod(
                        otherSideAttributes[k], persistable);
                Object s1 = "";
                if (method != null)
                {
                    s1 = taskLogic.getAttrValue(persistable, method);
                }
                if (persistable instanceof BaseValueIfc)
                {
                    Image image = taskLogic.getStandardIcon(persistable);
                    if (image != null)
                    {
                        multiList.addCell(i1, k, s1.toString(), image);
                    }
                    else
                    {
                        multiList.addCell(i1, k,
                                          new CappTextAndImageCell(s1.toString())); //???
                    }
                }
                for (int l = 1; l < i; l++)
                {
                    Object s2 = null;
                    Method method1 = taskLogic.getGetterMethod(
                            otherSideAttributes[l], persistable);
                    if (method1 != null)
                    {
                        s2 = taskLogic.getAttrValue(persistable, method1);
                    }
                    else
                    if (persistable instanceof IBAHolderIfc)
                    {
                        s2 = taskLogic.getIBAAttValue((IBAHolderIfc)
                                persistable, otherSideAttributes[l]);
                    }
                    else
                    if (persistable instanceof ExtendAttriedIfc)
                    {
                        s2 = taskLogic.getExtendAttValue((ExtendAttriedIfc)
                                persistable, otherSideAttributes[l]);
                    }
                    else
                    if (persistable instanceof AffixIfc)
                    {
                        s2 = taskLogic.getAffValue((AffixIfc) persistable,
                                otherSideAttributes[l]);
                    }
                    addToList(i1, l, s2);
                }
                HashMap m = new HashMap();
                m.put(othersideobjectid, binarylinkinfo);
                for (int j1 = 0; j1 < j; j1++)
                {
                    int k1 = j1 + i;
                    //  Method method2 = taskLogic.getGetterMethod(
                    //      multiListLinkAttributes[j1], binarylinkinfo);
                    //  if (method2 != null)
                    //   s3 = taskLogic.getAttrValue(binarylinkinfo, method2);
                    if (binarylinkinfo.getBsoName().equals(
                            "QMProcedureQMPartLink"))
                    {
                        BinaryLinkIfc t = (BinaryLinkIfc) m.get(kv.get(i1));
//                    System.out.println("i1============"+i1+"==============="+kv.size());
//                    System.out.println("t======="+t);
                        addLinkAttrs(i1, k1, t,
                                     multiListLinkAttributes[j1]);
                    }
                    else
                    {
//                                	System.out.println("binarylinkinfo======="+binarylinkinfo);
                        addLinkAttrs(i1, k1, binarylinkinfo,
                                     multiListLinkAttributes[j1]);
                    }
                    // multiList.addTextCell(i1, k1, s3);
                }
                int l1 = i + j;
                debug("CappAssociationsPanel.addToTable() last_column: " + l1);
                //ɾ����row��ԭ�����ڵ�����
                String oldBsoID = multiList.getCellText(i1, l1);
                if (oldBsoID != null)
                {
                    links.remove(oldBsoID);

                }
                System.out.println("i1==================="+i1);
                System.out.println("l1==================="+l1);
                System.out.println("s==================="+s);
                //�ѵ�ǰ�����id�ӵ����һ��
                multiList.addTextCell(i1, l1, s);
                links.put(s, binarylinkinfo);
//                System.out.println("key===="+s);
                
                if (multiList.getTable().getRowCount() == 1)
                {
                    notifyActionListeners(new ActionEvent(binarylinkinfo,
                            200,
                            "FirstRow"));
                }
            }
            catch (Exception _e)
            {
                _e.printStackTrace();
            }
        }
    }


    /**
     * ��Ӿ���Ĺ����������б���
     * @param binarylinkinfo ����ֵ����
     * @param onSelectedRow �Ƿ���ѡ�������
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @see BinaryLinkInfo
     */
    public void addToTable(BinaryLinkIfc binarylinkinfo, boolean onSelectedRow)
            throws
            InvocationTargetException, IllegalAccessException,
            NoSuchMethodException
    {

        int i1;
        if (onSelectedRow)
        {
            i1 = multiList.getSelectedRow();
        }
        else
        {
            i1 = multiList.getNumberOfRows();
        }
        addToTable(binarylinkinfo, i1);
        /* try {
           //����һ�����������������һ������
           if (otherSideRole == null)
             otherSideRole = taskLogic.getOtherSideRole();
           //String othersideobjectid = binarylinkinfo.getRoleBsoID(otherrole);//??? test
           if (VERBOSE)
             System.out.println("addToTable method ...otherSideRole is:::" +
                                otherSideRole);
           String othersideobjectid = taskLogic.getRoleBsoID(binarylinkinfo,
               otherSideRole); //binarylinkinfo.getRoleBsoID(otherrole);
           BaseValueIfc persistable = (BaseValueIfc) otherSideObjects.get(
               othersideobjectid);
           if (persistable == null) {
             persistable = taskLogic.refreshInfo(othersideobjectid);
             otherSideObjects.put(othersideobjectid, persistable);
           }
           String s = othersideobjectid; //(new Integer(binarylinkinfo.hashCode())).toString();
           debug("CappAssociationsPanel.addToTable() link: " + binarylinkinfo);
           debug("CappAssociationsPanel.addToTable() id: " + s);
           //if (!links.containsKey(s)) {
           int i = otherSideAttributes.length;
           int j = multiListLinkAttributes.length;
           int k = 0;
           int i1;
           if(onSelectedRow)
             i1 = multiList.getSelectedRow();
           else
             i1 = multiList.getNumberOfRows();
           Object obj = null;
           Method method = taskLogic.getGetterMethod(
               otherSideAttributes[k], persistable);
           Object s1 = "";
           if (method != null)
             s1 = taskLogic.getAttrValue(persistable, method);
           if (persistable instanceof BaseValueIfc) {
             Image image = taskLogic.getStandardIcon(persistable);
             if (image != null)
               multiList.addCell(i1, k, s1.toString(), image);
             else
         multiList.addCell(i1, k, new CappTextAndImageCell(s1.toString())); //???
           }
           for (int l = 1; l < i; l++) {
             Object s2 = null;
             Method method1 = taskLogic.getGetterMethod(
                 otherSideAttributes[l], persistable);
             if (method1 != null)
               s2 = taskLogic.getAttrValue(persistable, method1);
             else
               if(persistable instanceof AffixIfc)
               {
         s2 =taskLogic.getAffValue((AffixIfc)persistable,otherSideAttributes[l]);
               }
             addToList(i1,l,s2);
           }
           for (int j1 = 0; j1 < j; j1++) {
             Object s3 = "";
             int k1 = j1 + i;
             //  Method method2 = taskLogic.getGetterMethod(
             //      multiListLinkAttributes[j1], binarylinkinfo);
             //  if (method2 != null)
             //   s3 = taskLogic.getAttrValue(binarylinkinfo, method2);
             addLinkAttrs(i1,k1,binarylinkinfo,multiListLinkAttributes[j1]);
             // multiList.addTextCell(i1, k1, s3);
           }
           int l1 = i + j;
           debug("CappAssociationsPanel.addToTable() last_column: " + l1);
           //�ѵ�ǰ�����id�ӵ����һ��
           multiList.addTextCell(i1, l1, s);
           links.put(s, binarylinkinfo);
           //}
           resetLabels();
           if(multiList.getTable().getRowCount()==1)
             notifyActionListeners(new ActionEvent(this,200,"FirstRow"));
         }
         catch (Exception _e) {
           _e.printStackTrace();
         }*/
    }


    /**
     * �ѹ���������Լӵı���
     * @param i ��
     * @param j ��
     * @param info ����ֵ����
     * @param attName ������
     * @throws Exception
     * @see BinaryLinkInfo
     */
    private void addLinkAttrs(int i, int j, BinaryLinkIfc info, String attName)
            throws Exception
    {
        Object obj1 = null;
        Method method2 = taskLogic.getGetterMethod(attName, info);
        if (method2 != null)
        {
            obj1 = taskLogic.getAttrValue(info, method2);
        }
        else
        if (secondeTypeValue != null)
        {
            if (((ExtendAttriedIfc) info).getExtendAttributes() != null)
            {
                obj1 = ((ExtendAttriedIfc) info).getExtendAttributes().
                       findExtendAttModel(attName).getAttValue();
            }
            else
            {
                obj1 = container.findExtendAttModel(attName).getAttValue();
            }
        }
        String ptype = null;
        ptype = taskLogic.getPropertyType(attName, info);
        System.out.println("ptype===================="+ptype);
        System.out.println("attName===================="+attName);
        System.out.println("info===================="+info.getClass().getName());
        
        if (ptype == null)
        {
            ptype = container.findExtendAttModel(attName).getAttType();

        }
        if (ptype.equals("java.lang.String"))
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
        if (ptype.equals("java.lang.Boolean") || ptype.endsWith("boolean"))
        {
            boolean flag = true;
            System.out.println("rds========length============"+rds.length);
            
            for(int a=0;a<rds.length;a++){
              System.out.println("rds===================="+rds[a]);
            }
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
            System.out.println("flag====================="+flag);
            if (flag)
            {
            	
            	//CCBegin SS7
            	if(info.getBsoID() == null){
				  multiList.addCheckboxCell(i, j, Boolean.TRUE.booleanValue());
			    }else//CCEnd SS7
                  multiList.addCheckboxCell(i, j, ((Boolean) obj1).booleanValue());
            }
            else
            {
                multiList.addRadioButtonCell(i, j,
                                             ((Boolean) obj1).booleanValue());
            }
        }
        else
        if (ptype.endsWith("com.faw_qm.codemanage.model.CodingIfc"))
        {
            //20060725 Ѧ���޸� �����������ϣ�����ÿ�ζ�Ҫ�������ô������
            Collection sorts = (Collection) codingMap.get(new Integer(j));
            if (sorts == null)
            {
                PropertyDescript pt = taskLogic.getPropertyDescript(attName,
                        getLinkClassName());
                String sortType = pt.getLimitValue("SortType");
                String para1 = "";
                String para2 = "";
                String para3 = "";
                if (sortType != null)
                {
                    StringTokenizer ston = new StringTokenizer(sortType, ":");
                    if (ston.hasMoreTokens())
                    {
                        para1 = ston.nextToken();
                    }
                    if (VERBOSE)
                    {
                        System.out.println("�Ʒ�1" + para1);
                    }
                    if (ston.hasMoreTokens())
                    {
                        para2 = ston.nextToken();
                    }
                    if (VERBOSE)
                    {
                        System.out.println("�Ʒ�2" + para2);
                    }
                    if (ston.hasMoreTokens())
                    {
                        para3 = ston.nextToken();
                    }
                    if (VERBOSE)
                    {
                        System.out.println("�Ʒ�3" + para3);

                    }
                }
                sorts = CappTreeHelper.getCoding(para1, para2, para3);
                codingMap.put(new Integer(j), sorts);
            }
            //20070725 Ѧ���޸����

            if (VERBOSE)
            {
                System.out.println("���ϵĴ�СΪ" + sorts.size());
            }
            if (sorts == null)
            {
                multiList.addTextCell(i, j, "");
                return;
            }
            Iterator ite = sorts.iterator();

            Vector vec = new Vector();
            while (ite.hasNext())
            {
                CodingIfc info1 = (CodingIfc) ite.next();
                vec.add(info1.getCodeContent());
            }
            if (obj1 != null)
            {
                multiList.addComboBoxCell(i, j,
                                          ((CodingIfc) obj1).getCodeContent(),
                                          vec);
            }
            else
            {
                multiList.addComboBoxCell(i, j, vec.elementAt(0), vec);
            }
        }
        else
        if (obj1 != null)
        {
            //modify by wangh on 20080522
            //multiList.addTextCell(i,j,obj1.toString());
            multiList.addNumberTextCell(i, j, obj1);
            //modify end
        }
        else
        {
            multiList.addTextCell(i, j, "");
        }
    }


    /**
     * �Ѷ���������
     * @param i ����
     * @param j ����
     * @param obj1 Ҫ��ӵĶ���
     */
    public void addToList(int i, int j, Object obj1)
    {

        if (obj1 != null)
        {
            if (obj1 instanceof EnumeratedType)
            {
                EnumeratedType et1 = (EnumeratedType) obj1;
                EnumeratedType[] ets = et1.getValueSet();
                String value = et1.getDisplay(local);
                Vector values = new Vector();
                for (int k = 0; k < ets.length; k++)
                {
                    values.add(ets[k].getDisplay(local));
                }
                multiList.addComboBoxCell(i, j, value, values);
                enumeratedTypes.put(new Integer(j), ets);
                //return ((EnumeratedType) obj1).getDisplay();
            }
            else
            if (obj1 instanceof String)
            {
                multiList.addTextCell(i, j, obj1.toString());
                // multiList.addNumberTextCell(i, j, obj1);
            }
            else
            if (obj1 instanceof Boolean)
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
                if (flag)
                {
                    multiList.addCheckboxCell(i, j,
                                              ((Boolean) obj1).booleanValue());
                }
                else
                {
                    multiList.addRadioButtonCell(i, j,
                                                 ((Boolean) obj1).booleanValue());
                }
            }
            else
            {
                multiList.addTextCell(i, j, obj1.toString());
                //multiList.addNumberTextCell(i, j, obj1);
            }
        }
        else
        {
            multiList.addTextCell(i, j, "");
        }

    }


    /**
     * ˢ�¶����б�����i��Ӧ�����ض���binarylininfo��ص���ʾ��Ϣ
     * @param i ��Ӧ���к�
     * @param binarylinkinfo �ض���binarylininfo��������
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @see binarylinkinfo
     */
    protected void updateTableLinkValues(int i, BinaryLinkIfc binarylinkinfo)
            throws
            InvocationTargetException, IllegalAccessException,
            NoSuchMethodException
    {
        String s = "";
        int j = otherSideAttributes.length;
        int k = multiListLinkAttributes.length;
        Object obj = null;
        try
        {
            //  if (attributesForm1 != null)
            //     attributesForm1.setObjectAttributeValues();
            for (int l = 0; l < k; l++)
            {
                Object s1 = "";
                int i1 = l + j;
                Method method = taskLogic.getGetterMethod(
                        multiListLinkAttributes[l], binarylinkinfo);
                if (method != null)
                {
                    s1 = taskLogic.getAttrValue(binarylinkinfo, method);
                }
                addLinkAttrs(i, i1, binarylinkinfo, getLinkClassName());
                //  multiList.addTextCell(i, i1, s1);
            }
        }
        catch (Exception _e)
        {
            _e.printStackTrace();
        }
    }


    /**
     * �������ض���ҵ�����basevalueinfo�Ĺ���������ӵ������б���
     * @param basevalueinfo BaseValueIfc����
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @see BaseValueInfo
     * ����(5)20080602 �촺Ӣ�޸�  �޸�ԭ��:��ӹ�������ĵ�ʱ,��ͨ�û���������ȫ���ĵ�,��Ϊ
     * �������������ĵ�masterû��Ȩ�޿���,����Ҫ�����������ʾ�ĵ�,��������Ȩ�޿�����
     */
    public void addSelectedObject(BaseValueIfc basevalueinfo)
            throws
            IllegalAccessException, NoSuchMethodException,
            InstantiationException,
            ClassNotFoundException, InvocationTargetException
    {
        debug("CappAssociationsPanel.addSelectedObject() - added_object: " +
              basevalueinfo);
        if (objectFilter != null)
        {
            BaseValueIfc[] awtobject = objectFilter.doFillter(new BaseValueIfc[]
                    {basevalueinfo});
            if (awtobject != null && awtobject.length != 0)
            {
                basevalueinfo = awtobject[0];
            }
            else
            {
                return;
            }
        }
        String str = "";
        //����(5)20080602 �촺Ӣ�޸�  �޸�ԭ��:��ӹ�������ĵ�ʱ,��ͨ�û���������ȫ���ĵ�,��Ϊ
        //�������������ĵ�masterû��Ȩ�޿���,����Ҫ�����������ʾ�ĵ�,��������Ȩ�޿�����
        if (basevalueinfo instanceof DocInfo)
        {
            str = ((DocInfo) basevalueinfo).getMasterBsoID();
        }
        else
        {
            str = basevalueinfo.getBsoID();
        }
        if (!links.containsKey(str))
        {
            BinaryLinkIfc binarylinkinfo = taskLogic.createNewLink(
                    basevalueinfo);
            addToTable(binarylinkinfo, false);
            if (saveUpdatesOnly)
            {
                addToUpdatedTable(binarylinkinfo);
            }
            otherSideObjects.put(basevalueinfo.getBsoID(), basevalueinfo);
            setDirty(true);

        }

    }


    /**
     * �������ض���ҵ�����basevalueinfo�Ĺ���������ӵ������б���
     * @param basevalueinfo BaseValueIfc����
     * @param row int �к�
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @see BaseValueInfo
     */
    public boolean addObjectToRow(BaseValueIfc basevalueinfo, int row)
            throws
            IllegalAccessException, NoSuchMethodException,
            InstantiationException,
            ClassNotFoundException, InvocationTargetException
    {
        debug("CappAssociationsPanel.addSelectedObject() - added_object: " +
              basevalueinfo);
        if (!links.containsKey(basevalueinfo.getBsoID()))
        {
            BinaryLinkIfc binarylinkinfo = taskLogic.createNewLink(
                    basevalueinfo);
            addToTable(binarylinkinfo, row);
            if (saveUpdatesOnly)
            {
                addToUpdatedTable(binarylinkinfo);
            }
            otherSideObjects.put(basevalueinfo.getBsoID(), basevalueinfo);
            setDirty(true);
            return true;
        }
        else
        {
        	//CCBegin by liunan 2011-08-25 �򲹶�PO35
        	//CR11 start 
			int count = this.multiList.getRowCount();
			String newid = this.multiList.getCellText(row, 0);
			for (int i = 0; i < count; i++) {				
				String oldid = this.multiList.getCellText(i, 0);
				if (newid.equals(oldid)) {
						if (i == row) {
							continue;
						} else {
							if(!this.multiList.getCellText(row, 1).equals(""))
							{
								
								DialogFactory.showWarningDialog(frame,
								"�ö����Ѿ�����,���������!");
			                    this.undoCell();
			                    return false;
							}else
							{
							DialogFactory.showWarningDialog(frame,
									"�ö����Ѿ�����,���������!");
							this.multiList.addNumberTextCell(row, 0, "");
							return false;
							}
						}
					}
			}
           // CR11 end
        	//CCEnd by liunan 2011-08-25
            return false;
        }
    }


    /**
     * �Ѷ�����ӵ���ǰѡ�е���
     * @param basevalueinfo BaseValueIfc����
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @see BaseValueInfo
     */
    public void addObjectToSelectRow(BaseValueIfc basevalueinfo)
            throws
            IllegalAccessException, NoSuchMethodException,
            InstantiationException,
            ClassNotFoundException, InvocationTargetException
    {
        debug("CappAssociationsPanel.addSelectedObject() - added_object: " +
              basevalueinfo);
        int row = multiList.getSelectedRow();
        int col = multiList.getNumberOfCols() - 1;
        String obj = (String) multiList.getSelectedObject(row, col);
        //if(obj!=null)
        //   return ;
        if (!links.containsKey(basevalueinfo.getBsoID()))
        {
            BinaryLinkIfc binarylinkinfo = taskLogic.createNewLink(
                    basevalueinfo);
            addToTable(binarylinkinfo, true);
            if (saveUpdatesOnly)
            {
                addToUpdatedTable(binarylinkinfo);
            }
            links.remove(obj);
            otherSideObjects.put(basevalueinfo.getBsoID(), basevalueinfo);
            otherSideObjects.remove(obj);
            setDirty(true);
        }
    }


    /**
     * ɾ������binarylinkinfo
     * @param binarylinkinfo BaseValueIfc����
     * @throws QMException
     * @see BinaryLinkInfo
     */
    public void removeSelectedObject(BinaryLinkIfc binarylinkinfo)
            throws
            QMException
    {
        //Object obj = getLinkInTable(binarylinkinfo, links);???

        if (binarylinkinfo != null)
        {
            int i = multiList.getSelectedRow();
            multiList.removeRow(i);
            String otherSideBsoID = taskLogic.getOtherSideBsoID(
                    binarylinkinfo);
            links.remove(otherSideBsoID);
            removedLinks.put(otherSideBsoID, binarylinkinfo);
            //CCBegin SS1
            otherSideObjects.remove(otherSideBsoID);
            //CCEnd by SS1
            //begin CR9
//            if(binarylinkinfo instanceof QMProcedureQMToolLinkIfc)
            upDownVec.remove(otherSideBsoID);
            //end CR9
            
            if (VERBOSE)
            {
                debug(
                        "removeSelectedObject method perform ... the id is :::" +
                        otherSideBsoID);
            }
            if (saveUpdatesOnly)
            {
                String obj1 = getLinkInTable(binarylinkinfo, updatedLinks);
                if (obj1 != null)
                {
                    updatedLinks.remove(obj1);
                }
            }
            //if(i != 0)
            //   multiList.selectRow(i);
            setDirty(true);
        }
    }


    /**
     * �����������棬�û�����ѡ��ҵ�����ӵ������б��У����༭�����
     * @throws QMRemoteException
     */
    protected void launchQueryDialog()
            throws QMRemoteException
    {
        String s = display("20", null);
        final JFrame f = new JFrame(s);
        f.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent windowevent)
            {
                f.dispose();
            }
        });
        f.setSize(650, 500);
        FindObjectsPanel findobjectspanel = new FindObjectsPanel(chooserOptions);
        findobjectspanel.addListener(new AddObjectListener(f));
        findobjectspanel.setLastIteration(isLastIteration());
        f.getContentPane().add(findobjectspanel);
        f.pack();
        setLocation(f);
        f.show();
    }


    /**
     * ����ѡ����棬�û�����ѡ��ҵ�����ӵ������б��У����༭�����
     * @throws QMRemoteException
     * ����(5)20080602 �촺Ӣ�޸�  �޸�ԭ��:��ӹ�������ĵ�ʱ,��ͨ�û���������ȫ���ĵ�,��Ϊ
     * �������������ĵ�masterû��Ȩ�޿���,����Ҫ�����������ʾ�ĵ�,��������Ȩ�޿�����
     */
    protected void launchChooserDialog()
            throws QMRemoteException
    {
        browseButton.setEnabled(false);
        String s = display("20", null);
        String s1 = taskLogic.getOtherSideBsoName(); //getOtherSideClassName();
        //����(5)20080602 �촺Ӣ�޸�  �޸�ԭ��:��ӹ�������ĵ�ʱ,��ͨ�û���������ȫ���ĵ�,��Ϊ
        //�������������ĵ�masterû��Ȩ�޿���,����Ҫ�����������ʾ�ĵ�,��������Ȩ�޿�����
        if (s1.equals("DocMaster"))
        {
            s1 = "Doc";
        }
          //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 TD2732  
//        CappChooser qmchooser = new CappChooser(s1, s, this);//CR8
        CappChooser qmchooser = new CappChooser(s1, s, this.getTopLevelAncestor());//CR8
        //CCEnd by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 TD2732   
        //20060926Ѧ���޸ģ�����������ȥ���Ӳ�ѯ������
        //����(5) 20080602 �촺Ӣ�޸�
        if (s1.equals("QMPart") || s1.equals("QMPartMaster") || s1.equals("Doc"))
        {
            childQuery = false;
        }

        qmchooser.setLastIteration(this.isLastIteration());
        //qmchooser.setLastIteration(true);
        qmchooser.setSize(650, 500);
        String s2 = display("57", null);
        String s3 = "C:" + s1 + "; G:" + s2 + ";";
        String as[] = getOtherSideAttributes();
        /*int[] relcols = new int[as.length];
             for (int i = 0; i < as.length; i++)
             {

          s3 = s3 + " A:" + as[i] + ";";
          relcols[i] = 1;
             }*/
        if (VERBOSE)
        {
            System.out.println("�������s:" + s + "  s1:" + s1 + "  s2:" + s2 +
                               "  s3:" + s3);
            //qmchooser.setSchema(new QMSchema(s3));
        }
        qmchooser.setChildQuery(getChildQuery());
        //qmchooser.setRelColWidth(relcols);
        qmchooser.addListener(new CappQueryListener()
        {
            public void queryEvent(CappQueryEvent qmqueryevent)
            {
                if (qmqueryevent.getType().equals(CappQueryEvent.COMMAND) &&
                    qmqueryevent.getCommand().equals(CappQuery.OkCMD))
                {
                    CappChooser qmchooser1 = (CappChooser) qmqueryevent.
                                             getSource();
                    BaseValueIfc awtobject[] = qmchooser1.getSelectedDetails();
                    BaseValueIfc finalawtobject[];
                    //����(5) 20080602 �촺Ӣ�޸�
                    ArrayList list = new ArrayList();
                    BaseValueIfc object = null;
                    try
                    {
                        if (objectFilter != null)
                        {
                            finalawtobject = objectFilter.doFillter(awtobject);
                        }
                        else
                        {
                            finalawtobject = awtobject;
                        }
                        if (finalawtobject != null)
                        {
                            int length = finalawtobject.length;
                            for (int j = 0; j < length; j++)
                            {
                                object = finalawtobject[j];
                                //list.add(object);
                                //����(5) 20080602 �촺Ӣ�޸� ���������ĵ���ֻȡ��ͬmaster���ĵ�
                                //CCBegin by liunan 2011-06-02 ���������ӵ��㲿���ڸ������ϼУ���������ӡ�
                                if(object instanceof QMPartIfc)
                                {
                                	System.out.println("ggggggggggggggggggggggggggggggggggggggg"+techTypeIfc);
                                	QMPartIfc part = (QMPartIfc)object;
                                	if(part.getOwner()!=null&&!(part.getLocation().endsWith("Check_Out")))
                                	{
                                		String mes = "�㲿��"+part.getPartNumber()+"�ڸ������ϼ��У�������ӡ�";
                                		JOptionPane.showMessageDialog(getParentJFrame(),mes,"��ʾ",JOptionPane.INFORMATION_MESSAGE);
                                		return;
                                	} 
                                	
                                	//CCBegin SS11
                                	String s = "";
                                	ServiceRequestInfo info = new ServiceRequestInfo();
                                	info.setServiceName("StandardCappService");
                                	info.setMethodName("checkPartBranchUsedForZCZX");
                                	Class[] cla = {QMPartIfc.class,CodingIfc.class};
                                	Object[] obj = {part,techTypeIfc};
                                	info.setParaClasses(cla);
                                	info.setParaValues(obj);
                                	RequestServer server = null;
                                	try
                                	{
                                		//ͨ������������Ĺ������server
                                		server = RequestServerFactory.getRequestServer();
                                		s = (String) server.request(info);
                                		s = "";
                                	}
                                	catch (QMRemoteException e)
                                	{
                                		e.printStackTrace();
                                	}
                                	if(!s.equals(""))
                                	{
                                		JOptionPane.showMessageDialog(getParentJFrame(),s,"��ʾ",JOptionPane.INFORMATION_MESSAGE);
                                		return;
                                	} 
                                	//CCEnd SS11
                                }
                                //CCEnd by liunan 2011-06-02
                                if (object instanceof DocInfo)
                                {
                                     boolean isExist = false ;
                                    for (int i = 0, k = list.size(); i < k; i++)
                                    {
                                        if (((DocInfo) list.get(i)).
                                            getMasterBsoID().equals(((DocInfo)
                                                object).getMasterBsoID()))
                                        {
                                            isExist = true;
                                            break;
                                        }

                                    }

                                    if (!isExist)
                                    {
                                        list.add(object);
                                    }

                                }
                                else
                                {
                                    list.add(object);
                                }

                            }
                            for (int ii = 0, jj = list.size(); ii < jj; ii++)
                            {
                                addSelectedObject((BaseValueIfc) list.get(ii));
                                ActionEvent a = new ActionEvent((BaseValueIfc) list.get(ii),
                                        ActionEvent.ACTION_PERFORMED,
                                        "MultiListAdd");
                                notifyActionListeners(a);

                            }

                        }
                        int k = multiList.getNumberOfRows() - 1;
                        multiList.selectRow(k);
//                      CCBeginby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����
                        if(!childQuery){
                            notifyActionListeners(new ActionEvent(multiList,
                                    200,   
                                    "buildPartNumber"));  
//                          CCEndby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����
                        }
//                      CCBeginby leixiao 2009-3-31 ԭ�򣺽����������,��Ű����ͳ�                       
                        return;
                    }
                    catch (Exception _e)
                    {
                        _e.printStackTrace();
                        return;
                    }
                }
                else
                {
                    return;
                }
            }
        });
        setLocation(qmchooser);
        qmchooser.setVisible(true);
        setEnabled(true);
    }


    /**
     * ���������λ��
     * @param comp ���
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
     * �û������Ӱ�ťʱ��Ӧ
     */
    protected void addObject()
    {
        getFrame().setCursor(Cursor.getPredefinedCursor(3));
        try
        {
            String si = getChooserOptions();
            if (si == null || si.length() == 0)
            {
                launchChooserDialog();
            }
            else
            {
                launchQueryDialog();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        getFrame().setCursor(Cursor.getDefaultCursor());
    }


    /**
     * �û�����鿴��ťʱ��Ӧ
     * @param actionevent
     */
    void viewButton_ActionPerformed(ActionEvent actionevent)
    {
        try
        {
            WorkThread workthread = new WorkThread(VIEW);
            workthread.start();
        }
        finally
        {
        }
    }


    /**
     *  �û������Ӱ�ťʱ��Ӧ
     * @param actionevent
     */
    void browseButton_ActionPerformed(ActionEvent actionevent)
    {
        try
        {
        	//CCBegin SS6
        	this.modifyFlag = true;
        	//CCEnd SS6
        	
            WorkThread workthread = new WorkThread(ADD);
            workthread.start();
        }
        catch (Exception _e)
        {
            _e.printStackTrace();
        }
    }


    /**
     * �����������
     */
    public void clear()
    {
        multiList.clear();
        links.clear();
        //CCBegin by liunan 2012-02-27 �򲹶�PO41
         //CR15 begin
        if(this.getLinkClassName().indexOf("Procedure")==-1)
	{
        kv.clear();
	}
	 //CR15 end
	 //CCEnd by liunan 2012-02-27
        sortV.clear();
        removedLinks.clear();
        updatedLinks.clear();
        otherSideObjects.clear();
        //codingMap.clear();
    }


    /**
     *  �û����ɾ����ťʱ��Ӧ
     * @param actionevent
     */
//    void removeButton_ActionPerformed(ActionEvent actionevent)����������//Begin CR1
//    {
//        try
//        {
//
//            BinaryLinkIfc binarylink = getSelectedLink();
//            //2008.03.07 �������
//            if (binarylink != null)
//            {
//                String othersideobjectid = taskLogic.getRoleBsoID(
//                        binarylink,
//                        otherSideRole);
//                kv.removeElement(othersideobjectid);
//            }
//            //end mario
//
//            int i = multiList.getSelectedRow();
//            if (i != -1)
//            {
//                if (binarylink != null)
//                {
//                    removeSelectedObject(binarylink);
//                }
//                else
//                {
//                    multiList.removeRow(i);
//                }
//                if (i > 0)
//                {
//                    multiList.selectRow(i - 1);
//                }
//                else
//                if (multiList.getTable().getRowCount() > 0)
//                {
//                    multiList.selectRow(i);
//                }
//            }
//        }
//        catch (Exception _e)
//        {
//            _e.printStackTrace();
//        }
//    }
    
    void removeButton_ActionPerformed(ActionEvent actionevent)
	{
		try
		{
			BinaryLinkIfc[] binarylink = getSelectedLinks();
			int[] rows = multiList.getSelectedRows();
           System.out.println("binarylink=="+binarylink);
           //Begin CR5
           if(null==rows||rows.length==0)
        	   return;
           //End CR5
			if (binarylink != null)
			{
				int kk = JOptionPane
				        .showConfirmDialog(getParentJFrame(),
				                "ɾ�����޷��ָ�,�Ƿ�ȷ��ɾ��?", "��ʾ��",
				                JOptionPane.OK_CANCEL_OPTION);
				if (kk != 0)
				{
					return;
				}
				//CCBegin SS6
				this.modifyFlag = true;
				//CCEnd SS6
                   
				for (int k = 0; k < binarylink.length; k++)
				{
					if (binarylink[k] != null)
					{
						String othersideobjectid = taskLogic.getRoleBsoID(
						        binarylink[k], otherSideRole);
						kv.removeElement(othersideobjectid);

						removeSelectedObject(binarylink[k]);
					}
					else
					{
						multiList.removeRow(multiList.getSelectedRow());
					}
				}
			}
			else
			{
				for (int j = 0; j < rows.length; j++)
				{
					i = rows[j];
					if (i != -1)
					{
						multiList.removeRow(i);
					}
				}
			}
			if (rows != null && rows.length > 0)
			{
				if (rows[0] > 0)
				{
					multiList.selectRow(rows[0] - 1);
				}
				else if (multiList.getTable().getRowCount() > 0)
				{
					multiList.selectRow(0);
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}                                                           // End CR1


    /**
	 * ɾ��ָ����
	 * 
	 * @param row
	 *            int ��
	 */
    public void removeRow(int row)
    {
        synchronized (addAndRemove)
        {
            try
            {

                int j = multiList.getNumberOfCols() - 1;
                String s = multiList.getCellText(row, j);
                BinaryLinkIfc binarylink = null;
                if (s != null)
                {
                    binarylink = (BinaryLinkIfc) links.get(s);

                }
                if (binarylink != null)
                {
                    multiList.removeRow(i);
                    String otherSideBsoID = taskLogic.getOtherSideBsoID(
                            binarylink);
                    links.remove(otherSideBsoID);
                    removedLinks.put(otherSideBsoID, binarylink);
                    if (VERBOSE)
                    {
                        debug(
                                "removeSelectedObject method perform ... the id is :::" +
                                otherSideBsoID);
                    }
                    if (saveUpdatesOnly)
                    {
                        String obj1 = getLinkInTable(binarylink, updatedLinks);
                        if (obj1 != null)
                        {
                            updatedLinks.remove(obj1);
                        }
                    }
                    setDirty(true);
                }

                else
                {
                    multiList.removeRow(row);
                }
            }
            catch (Exception _e)
            {
                _e.printStackTrace();
            }
        }

    }


    /**
     *  �û�������밴ťʱ��Ӧ
     * @param actionevent
     */
    void insertButton_ActionPerformed(ActionEvent actionevent)
    {
        try
        {
        	//CCBegin SS6
        	
        	 this.modifyFlag = true;
        	//CCEnd SS6
        	
            int row = multiList.getNumberOfRows();
            //  int j = multiList.getNumberOfCols();
            int oLenth = otherSideAttributes.length;
            int lLenth = multiListLinkAttributes.length;
            for (int i = 0; i < oLenth; i++)
            {
                multiList.addTextCell(row, i, "");
            }
            for (int j = 0; j < lLenth; j++)
            {
                multiList.addTextCell(row, j + oLenth, "");
            }
        }
        catch (Exception _e)
        {
            _e.printStackTrace();
        }
    }
    

    
    
   //CCBegin SS6
    
    /**
     *  �û�����ṹ������ťʱ��Ӧ
     * @param actionevent
     */
    void structButton_ActionPerformed(ActionEvent actionevent) {
      structButton.setEnabled(false);
      for (; ! (component instanceof Frame);
           component = component.getParent()) {
        ;
      }
      String s = display("20", null);
      String s1 = taskLogic.getOtherSideBsoName();
      CappChooser qmchooser = new CappChooser(s1, s, this);
      //20060926Ѧ���޸ģ�����������ȥ���Ӳ�ѯ������
      if (s1.equals("QMPart") || s1.equals("QMPartMaster")) {
        childQuery = false;
      }
  	this.modifyFlag = true;
      qmchooser.setLastIteration(isLastIteration());
      qmchooser.setSize(650, 500);
      qmchooser.setChildQuery(getChildQuery());
      qmchooser.addListener(new CappQueryListener() {
        public void queryEvent(CappQueryEvent qmqueryevent) {
          if (qmqueryevent.getType().equals(CappQueryEvent.COMMAND) &&
              qmqueryevent.getCommand().equals(CappQuery.OkCMD)) {
            CappChooser qmchooser1 = (CappChooser) qmqueryevent.
                getSource();
            BaseValueIfc awtobject[] = qmchooser1.getSelectedDetails();
            BaseValueIfc finalawtobject[];
            try {
              if (objectFilter != null) {
                finalawtobject = objectFilter.doFillter(awtobject);
              }
              else {
                finalawtobject = awtobject;
              }

              SelectStructPartJDialog selectpart = new SelectStructPartJDialog( (
                  Frame) component, "ѡ�����", true);
              selectpart.setBaseValueIfc(finalawtobject);
              selectpart.setCodingIfc( (CodingIfc) techTypeIfc);
              setPanel(selectpart);
              selectpart.insertPartToMultilist();
              selectpart.setVisible(true);
              return;
            }
            catch (Exception _e) {
              _e.printStackTrace();
              return;
            }
          }
          else {
            return;
          }
        }
      });
      setLocation(qmchooser);
      qmchooser.setVisible(true);
      setEnabled(true);

    }
    
    //CCBegin SS9
    
    /**
     *  �û�������㲿����������Ϣ��ťʱ��Ӧadded by lil
     * @param actionevent
     */
    void partadd_ActionPerformed(ActionEvent actionevent) {
        int i = multiList.getSelectedRow();
        String number = multiList.getCellText(i, 0);
        String name = multiList.getCellText(i, 1);
        String count = multiList.getCellText(i, 2);
        StringBuffer sb = new StringBuffer();
        sb.append(name).append("(").append(number).append(";").append(count).append("��)");
        pJPanel.setTextValue(sb.toString());
      }
    
    private ProcedureUsagePartJPanel pJPanel = null;
    public void setProcedureUsagePartJPanel(ProcedureUsagePartJPanel pjPanel){
      this.pJPanel = pjPanel;
    }
    //CCEnd SS9
    
    
    
//CCBegin SS8
    
    /**
     *  �û�����ӹ�������Դ��ťʱ��Ӧ
     * @param actionevent
     */
    void getResoucebyProcedureButton_ActionPerformed(ActionEvent actionevent) {
      try {
        String temp = null;
        if (otherSideClassName.equals(
            "com.faw_qm.resource.support.model.DrawingInfo")) {
          temp = "QMProcedureDrawingLink";
        }
        if (otherSideClassName.equals(
            "com.faw_qm.resource.support.model.QMEquipmentInfo")) {
          temp = "QMProcedureQMEquipmentLink";
        }
        else if (otherSideClassName.equals(
            "com.faw_qm.resource.support.model.QMToolInfo")) {
          temp = "QMProcedureQMToolLink";
        }
        else if (otherSideClassName.equals(
            "com.faw_qm.resource.support.model.QMMaterialInfo")) {
          temp = "QMProcedureQMMaterialLink";
        }
        else if (otherSideClassName.equals(
            "com.faw_qm.part.model.QMPartInfo")) {
          temp = "QMProcedureQMPartLink";
        }
        else if (otherSideClassName.equals(
            "com.faw_qm.part.model.QMPartInfo")) {
          temp = "QMProcedureQMPartLink";
        }
        else {
          return;
        }
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        info1.setServiceName("PersistService");
        info1.setMethodName("navigateValueInfo");
        Class[] paraClass = {
            BaseValueIfc.class, String.class, String.class, boolean.class};
        info1.setParaClasses(paraClass);
        Object[] objs = {
            info, "procedure", temp, new Boolean(true)};
        info1.setParaValues(objs);
//  	  2009.04.15������ģ�ԭ���޸Ĵӹ��򴫹����Ķ���˳���������
//        Collection c = (Collection) QMContext.getRequestServer().request(info1);
        Method method1 = otherSideClass.getMethod("getBsoName", null);

        Collection c = getResFromPro(taskLogic.getBsoName(linkClassName),(String)method1.invoke(otherSideClass.newInstance(), null),info,role);
        for (Iterator i = c.iterator(); i.hasNext(); ) {
      	  Object[] objects = (Object[])i.next();
      	  BaseValueIfc obj = (BaseValueIfc)objects[1];
//      	  2009.05.19������ġ�
      	  addSelectedObject(obj,objects[0]);
//      	  addSelectedObject(obj);
//      	  end modify by mario in 2009.05.19
        }

      }
      catch (Exception _e) {
        _e.printStackTrace();
      }

    }
    
    
    /**
     * �������ض���ҵ�����basevalueinfo�Ĺ���������ӵ������б���
     * 2009.05.19 �������д��������������ڱ���������Դ�����á�
     * @param basevalueinfo
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     */
    public void addSelectedObject(BaseValueIfc basevalueinfo, Object binarylinkinfo) throws
        IllegalAccessException, NoSuchMethodException,
        InstantiationException,
        ClassNotFoundException, InvocationTargetException {
      debug("CappAssociationsPanel.addSelectedObject() - added_object: " +
            basevalueinfo);
      if (objectFilter != null) {
        BaseValueIfc[] awtobject = objectFilter.doFillter(new BaseValueIfc[] {
            basevalueinfo});
        if (awtobject != null && awtobject.length != 0) {
          basevalueinfo = awtobject[0];
        }
        else {
          return;
        }
      }

      if (!links.containsKey(basevalueinfo.getBsoID())) {

        addToTable(((BinaryLinkIfc)binarylinkinfo), false);
        if (saveUpdatesOnly) {
          addToUpdatedTable(((BinaryLinkIfc)binarylinkinfo));
        }
        otherSideObjects.put(basevalueinfo.getBsoID(), basevalueinfo);
        setDirty(true);
      }
    }
    
    //CCEnd SS8
    
    /**
     *  �û������׼������ťʱ��Ӧ
     * @param actionevent
     */
    void routelistButton_ActionPerformed(ActionEvent actionevent) {
      try {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (; ! (component instanceof Frame);
             component = component.getParent()) {
          ;
        }
  	  this.modifyFlag = true;
        TstSearchRouteListJDialog d = new TstSearchRouteListJDialog( (Frame)
            component, "����·�߱�", this);
        d.setQMFawTechnics( (CodingIfc) techTypeIfc);
        d.setVisible(true);
        setCursor(Cursor.getDefaultCursor());

      }
      catch (Exception _e) {
        _e.printStackTrace();
      }
    }
    
    public void setPanel(SelectStructPartJDialog selectpart) {
        selectpart.setCappAssociationsPanel(this);
      }
    /**
     * 
     * ��ñ�������
     * @return int
     */
    public int getNumberOfRows() {
      int i = 0;
      i = multiList.getNumberOfRows();
      return i;
    }
    //CCEnd SS6
    
    public void addObjectToMultilist(BaseValueIfc finalawtobject) {
      if (finalawtobject != null) {
        try {
          addSelectedObject(finalawtobject);
        }
        catch (InvocationTargetException ex) {
          ex.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
          ex.printStackTrace();
        }
        catch (InstantiationException ex) {
          ex.printStackTrace();
        }
        catch (NoSuchMethodException ex) {
          ex.printStackTrace();
        }
        catch (IllegalAccessException ex) {
          ex.printStackTrace();
        }
        if (object != null) {
          ActionEvent a = new ActionEvent(object,
                                          ActionEvent.ACTION_PERFORMED,
                                          "MultiListAdd");
          notifyActionListeners(a);
        }

      }
      int k = multiList.getNumberOfRows() - 1;
      multiList.selectRow(k);

    }
    
    
   

    /**
     *  �û�������밴ťʱ��Ӧ
     * @param actionevent
     */
    void calculateButton_ActionPerformed(ActionEvent actionevent)
    {

    }


    /**
     * ִ�������¼�
     *
     * @param e
     *            ActionEvent
     */
    void upJButton_actionPerformed(ActionEvent e) {
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      upRow();
      setCursor(Cursor.getDefaultCursor());
    }
    
    /**
     * ִ�������¼�
     *
     * @param e
     *            ActionEvent
     */
    void downJButton_actionPerformed(ActionEvent e) {
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      downRow();
      setCursor(Cursor.getDefaultCursor());
    }

  
    /**
     * �����Ŀ����
     * @param itemlistener
     */
    public void addItemListener(ItemListener itemlistener)
    {
        itemListener = AWTEventMulticaster.add(itemListener, itemlistener);
    }


    /**
     * ��ȥ��Ŀ����
     * @param itemlistener
     */
    public synchronized void removeItemListener(ItemListener itemlistener)
    {
        itemListener = AWTEventMulticaster.remove(itemListener, itemlistener);
    }


    /**
     *  ���ʶ����б�ѡ�ж��󼯺�
     * @return Object[] �����б�ѡ�ж��󼯺�
     */
    public Object[] getSelectedObjects()
    {
        int[] rows = multiList.getSelectedRows();
        Object[] objs = new Object[rows.length];
        for (int k = 0; k < rows.length; k++)
        {
            int i = rows[k];
            if (i >= 0)
            {
                debug(
                        "CappAssociationsPanel.getSelectedObject() number of cols: " +
                        multiList.getNumberOfCols());
                int j = multiList.getNumberOfCols() - 1;
                debug(
                        "CappAssociationsPanel.getSelectedObject() hidden_column: " +
                        j);
                String s = multiList.getCellText(i, j);
                debug("CappAssociationsPanel.getSelectedObject() object_id: " +
                      s);
                BaseValueIfc persistable = (BaseValueIfc) taskLogic.refreshInfo(
                        s);
                objs[k] = persistable;
            }
            else
            {
                objs[k] = null;
            }
        }
        return objs;
    }


    /**
     * �õ�������һ�˵Ķ���
     * @return Enumeration ���󼯺�
     */
    public Enumeration getOtherSideObjects()
    {
        return otherSideObjects.elements();
    }


    /**
     * ���ʶ����б�ѡ�ж���
     * @return BaseValueIfc �����б�ѡ�ж���
     * @throws QMRemoteException
     * @throws RegistryException
     */
    public BaseValueIfc getSelectedObject()
            throws QMRemoteException,
            RegistryException
    {
        int i = multiList.getSelectedRow();
        if (i >= 0)
        {
            debug("CappAssociationsPanel.getSelectedObject() number of cols: " +
                  multiList.getNumberOfCols());
            int j = multiList.getNumberOfCols() - 1;
            debug("CappAssociationsPanel.getSelectedObject() hidden_column: " +
                  j);
            String s = multiList.getCellText(i, j);
            debug("CappAssociationsPanel.getSelectedObject() object_id: " + s);
            /**
             BinaryLinkIfc binarylinkinfo = (BinaryLinkIfc) links.get(s);
             debug("CappAssociationsPanel.getSelectedObject() link is: " + binarylinkinfo);
                         if (binarylinkinfo == null)
             debug("CappAssociationsPanel.getSelectedObject() link was null!! ");
                    //����һ�����������������һ������
                         if(otherSideRole == null)
                    otherSideRole = taskLogic.getOtherSideRole();
                    //String othersideobjectid = binarylinkinfo.getRoleBsoID(role);??? test
             String othersideobjectid = taskLogic.getRoleBsoID(binarylinkinfo,role);
                         BaseValueIfc persistable = (BaseValueIfc)otherSideObjects.get(othersideobjectid);
                         if(persistable == null){
                    persistable = taskLogic.refreshInfo(othersideobjectid);
                         }
             **/
            if (s == null || s.trim().equals(""))
            {
                return null;
            }
            BaseValueIfc persistable = (BaseValueIfc) taskLogic.refreshInfo(s);
            return persistable;
        }
        else
        {
            return null;
        }
    }


    /**
     * ���ѡ���˵Ĺ�������
     * @return BinaryLinkIfc ��������
     */
    public BinaryLinkIfc getSelectedLink()
    {
        int i = multiList.getSelectedRow();
        if (i >= 0)
        {
            int j = multiList.getNumberOfCols() - 1;
            String s = multiList.getCellText(i, j);
            BinaryLinkIfc link = null;
            if (s != null)
            {
                link = (BinaryLinkIfc) links.get(s);
            }
            return link;
        }
        else
        {
            return null;
        }
    }


    /**
     * ���ѡ���˵Ĺ������󼯺�
     * @return BinaryLinkIfc[] �������󼯺�
     */
    public BinaryLinkIfc[] getSelectedLinks()
    {
    	//Begin CR4
        int[] rows = multiList.getSelectedRows();
        BinaryLinkIfc[] objs = new BinaryLinkIfc[rows.length];
     
        for (int k = 0; k < rows.length; k++)
        {
            int i = rows[k];
            if (i >= 0)
            {
                int j = multiList.getNumberOfCols() - 1;
                String s = multiList.getCellText(i, j);
                if(s != null)
                {
                objs[k] = (BinaryLinkIfc) links.get(s);
                }
            }
            else
            {
                objs[k] = null;
            }
          //End CR4
        }
        return objs;
    }


    /*
     *�鿴ѡ���˵Ķ��������漰���˷ʿͻ��˵��ݿͻ��˵�ת��
     */
    protected void viewSelectedObject()
    {
        getFrame().setCursor(Cursor.getPredefinedCursor(3));
        try
        {
            BaseValueIfc persistable = getSelectedObject();
//          Ѧ����� 20080220 add
            //���ԭ�򣺹��չ���в鿴�ĵ�ʱ�򿪵��ĵ�������ı������
            //�޸ķ����������ĵ�ֵ��������ĵ������°汾
            if (persistable instanceof DocMasterIfc)
            {
                ServiceRequestInfo info = new ServiceRequestInfo();
                info.setServiceName("VersionControlService");
                info.setMethodName("allIterationsOf");
                Class[] cla =
                        {MasteredIfc.class};
                Object[] obj =
                        {persistable};
                info.setParaClasses(cla);
                info.setParaValues(obj);
                RequestServer server = null;
                Collection col = null;
                Object myObject = null;
                try
                {
                    //ͨ������������Ĺ������server
                    server = RequestServerFactory.getRequestServer();
                    col = (Collection) server.request(info);
                    Iterator iterator1 = col.iterator();
                    if (iterator1.hasNext())
                    {
                        myObject = iterator1.next();
                    }
                }
                catch (QMRemoteException e)
                {
                    e.printStackTrace();
                    throw e;
                }
                if (myObject instanceof BaseValueIfc)
                {

                    persistable = (BaseValueIfc) myObject;
                }
                else
                {
                    persistable = null;
                }
            }
            //Ѧ����ӽ���
            if (persistable == null)
            {
                return;
            }
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            /*if(persistable instanceof ResourceManagedIfc)
                   {
              viewButton.setEnabled(false);
              if(!getMode().equals(VIEW_MODE))
                 browseButton.setEnabled(false);
              ResourceDialog dialog = new ResourceDialog(getParentJFrame(),persistable,"VIEW");
              dialog.setSize(650,500);
              dialog.setLocation((int)(screenSize.getWidth()-650)/2,(int)(screenSize.getHeight()-500)/2);
              dialog.setVisible(true);
              dialog.setModal(true);
              viewButton.setEnabled(true);
              if(!getMode().equals(VIEW_MODE))
                 browseButton.setEnabled(true);
                   }*/
            String screenID = RemoteProperty.getProperty(SCREEN_ID +
                    persistable.getBsoName());
            if (null == screenID)
            {
                return;
            }
            HashMap takeParams = new HashMap();
            String bsoID = RemoteProperty.getProperty(SCREEN_ID +
                    persistable.getBsoName() + "." + PARAM, "bsoID");
            if (null == bsoID)
            {
                return;
            }
            takeParams.put(bsoID, persistable.getBsoID());
            if (persistable instanceof QMPartIfc)
            {
                takeParams.put("flag", "0");
            }
            RichToThinUtil.toWebPage(screenID, takeParams);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        finally
        {
            getFrame().setCursor(Cursor.getDefaultCursor());
        }
    }


    /**
     * �����б��С�ı�ʱ��ˢ��
     * @param componentevent
     */
    void multiList_componentResized(ComponentEvent componentevent)
    {
        multiList.redrawColumns();
    }


    /**
     * ����ѡ�е���Ŀ�ı�ʱ�Ĵ���
     * @param itemevent
     */
    void multiList_itemStateChanged(ItemEvent itemevent)
    {
        try
        {
            if (oldSelection >= 0)
            {
                if (isCurrentLinkAttributeDirty())
                {
                    setDirty(true);
                }
                try
                {
                    //    if (attributesForm1 != null)
                    //    attributesForm1.setObjectAttributeValues();
                }
                catch (Exception exception)
                {
                }
            }
            resetCurrentRow();
            notifyItemListeners(itemevent);
        }
        catch (Exception e)
        {e.printStackTrace();
        }
    }


    /**
     * �ַ�itemevent�¼�
     * @param itemevent
     */
    protected void notifyItemListeners(ItemEvent itemevent)
    {
        ItemEvent itemevent1 = new ItemEvent(this, itemevent.getID(), this,
                                             itemevent.getStateChange());
        if (itemListener != null)
        {
            itemListener.itemStateChanged(itemevent1);
        }
    }


    /**
     * ���ñ���еĿɱ༭״̬
     * @param cols ���õ������ļ���
     * @param flag �Ƿ�ɱ༭
     */
    public void setColsEnabled(int[] cols, boolean flag)
    {
        editCols = cols;
        editflag = flag;
        multiList.setColsEnabled(cols, flag);

    }


    /**
     * ����������ʾΪ��ѡ��ť
     * @param cols ����������
     */
    public void setRadionButtons(int[] cols)
    {
        rds = cols;
    }


    /**
     * ���Է���
     * @param args
     */
    public static void main(String args[])
    {
        final JFrame f = new JFrame("Edit QMPart Relationships");
        f.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent windowevent)
            {
                f.dispose();
            }
        });

        RequestServer rs = new BeanRequestServer("pdm_gcy", "80",
                                                 "ApPTA3LBAoZFxHquOr9xRN41ReKEl2g7Jw7SNzygLcsjHr11GLJl!-2127984855!1080627027694");
        QMContext.setRequestServer(rs);
        final CappAssociationsPanel associationspanel = new
                CappAssociationsPanel(
                EDIT_MODE);
        f.setSize(650, 500);
        f.getContentPane().setLayout(new BorderLayout());
        Object obj = null;
        Class class1 = com.faw_qm.capp.model.QMFawTechnicsInfo.class;
        String partBsoID = "QMFawTechnics_2603";
        try
        {
            ServiceRequestInfo info1 = new ServiceRequestInfo();
            info1.setServiceName("PersistService");
            info1.setMethodName("refreshInfo");
            Class[] paraClass =
                    {
                    String.class};
            info1.setParaClasses(paraClass);
            Object[] objs =
                    {
                    partBsoID};
            info1.setParaValues(objs);
            final com.faw_qm.capp.model.QMFawTechnicsInfo partInfo = (com.
                    faw_qm.capp.model.QMFawTechnicsInfo)
                    QMContext.getRequestServer().request(info1);
            associationspanel.setRole("technics"); //describedBy
            String args1[] = new String[2];
            args1[0] = "�����";
            args1[1] = "���ó���";
            associationspanel.setLabels(args1);
            associationspanel.setLinkClassName(
                    "com.faw_qm.capp.model.PartUsageQMTechnicsLinkInfo");
            associationspanel.setSecondTypeValue("Ϳװ����");
            associationspanel.setObjectClassName(class1.getName());
            DocInfo info = new DocInfo();
            info.getLifeCycleState();
            String args2[] = new String[2];
            args2[0] = "bsoID";
            args2[1] = "originalMobileCategory";
            associationspanel.setOtherSideAttributes(args2);
            associationspanel.setMutliSelectedModel(true);
            associationspanel.setOtherSideClassName(
                    "com.faw_qm.part.model.QMPartInfo");
            String args4[] = new String[2];
            args4[1] = "blueprint";
            args4[0] = "areaPerPiece";
            associationspanel.setMultiListLinkAttributes(args4);
            associationspanel.setObject(partInfo);
            associationspanel.setFrame(f);
            associationspanel.setSaveUpdatesOnly(true);
            // associationspanel.setIsInsert(true);
            int[] is =
                    {1, 2, 3};
            int[] rds =
                    {4};
            //  associationspanel.setRadionButtons(rds);
            associationspanel.setColsEnabled(is, true);
            associationspanel.setMode(associationspanel.VIEW_MODE);
            associationspanel.setMode(associationspanel.EDIT_MODE);
            associationspanel.setIsCalculate(true, 3);
            f.getContentPane().add("Center", associationspanel);
            JButton button = new JButton("SAVE");
            f.getContentPane().add("South", button);
            button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent actionevent)
                {
                    Object[] objss = associationspanel.getSelectedObjects();
                    BinaryLinkIfc[] links = associationspanel.getSelectedLinks();
                    //  for(int i =0;i<objss.length;i++)
                    // {

                    // }
                    if (associationspanel.check())
                    {
                        associationspanel.save(partInfo);
                    }
                }
            });

            f.pack();
            f.show();
            return;
        }
        catch (QMRemoteException exception)
        {
            exception.printStackTrace();
            return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
    }

    static int i = 0;


    /**
     * ��ʼ����Դ
     */
    private void initResources()
    {
        try
        {
            if (resource == null)
            {
                resource = ResourceBundle.getBundle(RESOURCE, Locale.getDefault());
                return;
            }
        }
        catch (MissingResourceException _exception)
        {
            _exception.printStackTrace();
        }
    }


    /**
     * ��ñ��ػ���ʾ��Ϣ
     * @param s ��Դ�ļ��е�key
     * @param aobj ��������
     * @return String ���ػ���ʾ��Ϣ
     */
    private String display(String s, Object aobj[])
    {
        String s1 = "";
        if (resource == null)
        {
            initResources();
        }
        s1 = QM.getLocalizedMessage(RESOURCE, s, aobj, null);

        return s1;
    }


    /**
     * ���ÿɱ༭��
     * @param flag �Ƿ�ɱ༭,trueΪ�ɱ༭
     */
    public void setEnabled(boolean flag)
    {
        setFieldState(flag, this);
    }


    /**
     * ���þ��������е���������Ŀɱ༭��
     * @param flag �Ƿ�ɱ༭
     * @param container ��ǰ����
     * @see Container
     */
    protected void setFieldState(boolean flag, Container container)
    {
        Component acomponent[] = container.getComponents();
        int i = acomponent.length;
        for (int j = 0; j < i; j++)
        {
            Component component = acomponent[j];
            if (component instanceof Container)
            {
                setFieldState(flag, (Container) component);
            }
            if (!(component instanceof Label))
            {
                component.setEnabled(flag);
            }
        }
    }


    /**
     * ��ʼ������ϵͳ
     */
    void initializeHelp()
    {
        try
        {
            helpContext = new QMHelpContext(this, getHelpSystem(),
                                            "AssociationsPanel");
            helpContext.addComponentHelp(multiList, "MultiList");
            helpContext.addComponentHelp(viewButton, "View");
            return;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }


    /**
     * ��ʼ������ϵͳ
     */
    private void initHelpSystem()
    {
        try
        {
            if (helpSystem == null)
            {
                helpSystem = new QMHelpSystem("beans", null, "OnlineHelp",
                                              ResourceBundle.getBundle(
                        "com.faw_qm.clients.beans.BeansHelpRB",
                        Locale.getDefault()));
                //getContext().put("HelpSystem", helpSystem);
                return;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            //displayException(exception);
        }
    }


    /**
     * ��ð����ĵ�
     * @return HelpContext �����ĵ�
     */
    public HelpContext getHelpContext()
    {
        return helpContext;
    }


    /**
     * ���ϵͳ����
     * @return HelpSystem ϵͳ����
     */
    public HelpSystem getHelpSystem()
    {
        if (helpSystem == null)
        {
            initHelpSystem();
        }
        return helpSystem;
    }


    /**
     * ��Ӱ�������
     * @param propertychangelistener PropertyChangeListener
     */
    public synchronized void addHelpListener(PropertyChangeListener
                                             propertychangelistener)
    {
        if (helpContext == null)
        {
            initializeHelp();
        }
        helpContext.addPropertyChangeListener(propertychangelistener);
    }


    /**
     * �Ƴ���������
     * @param propertychangelistener PropertyChangeListener
     */
    public synchronized void removeHelpListener(PropertyChangeListener
                                                propertychangelistener)
    {
        if (helpContext != null)
        {
            helpContext.removePropertyChangeListener(propertychangelistener);
        }
    }


    /**
     * ��Ӧ�����б�ĵ����¼�
     * @param actionevent
     */
    void multiList_actionPerformed(ActionEvent actionevent)
    {
        try
        {
            WorkThread workthread = new WorkThread(VIEW);

            workthread.start();
        }
        finally
        {
        }
    }


    /**
     * ʹ�����б��ý���
     */
    public void requestFocus()
    {
        multiList.requestFocus();
    }


    /**
     * ����bean��PropertyChangeEvent�¼�
     * @param propertychangeevent
     */
    void attributesForm1_propertyChange(PropertyChangeEvent propertychangeevent)
    {
        if (!isAttributesFormSetObjectInProcess())
        {
            setCurrentLinkAttributeDirty(true);
            try
            {
                updateTableLinkValues(getSelectedRow(), getSelectedLink());
                debug(
                        "CappAssociationsPanel.attributesForm1_propertyChange() getSelectedLink(): " +
                        getSelectedLink());
                if (saveUpdatesOnly)
                {
                    addToUpdatedTable(getSelectedLink());
                    return;
                }
            }
            catch (Throwable throwable)
            {
                throwable.printStackTrace();
                return;
            }
        }
    }


    /**
     * ���б�ĵ�i�е�j�����ַ�
     * @param i int �к�
     * @param j int �к�
     * @param str String �ַ�
     */
    public void setCellTextValue(int i, int j, String str)
    {
        multiList.addTextCell(i, j, str);
    }


    /**
     * ���б�ĵ�i�е�j���õ�ѡ��booleanֵ
     * @param i int �к�
     * @param j int �к�
     * @param str booleanֵ
     */
    public void setRadionButtonValue(int i, int j, boolean str)
    {
        multiList.addRadioButtonCell(i, j, str);
    }


    /**
     * ���б�ĵ�i�е�j���ø�ѡ��booleanֵ
     * @param i int �к�
     * @param j int �к�
     * @param str booleanֵ
     */
    public void setCheckboxValue(int i, int j, boolean str)
    {
        multiList.addCheckboxCell(i, j, str);
    }


    /**
     *  ���б�ĵ�i�е�j�����������ֵ
     * @param i int �к�
     * @param j int �к�
     * @param str Object ��������ʾ����
     * @param vec Vector �������п���ʾ����ļ���
     */
    public void setComboBoxValue(int i, int j, Object str, Vector vec)
    {
        multiList.addComboBoxCell(i, j, str, vec);
    }


    /**
     * ���ѡ���������
     * @return int ������
     */
    public int getSelectedRow()
    {
        return multiList.getSelectedRow();
    }

    private void debug(String s)
    {
        if (VERBOSE)
        {
            System.out.println(s);
        }
    }


    /**
     * �Ѿ���Ĺ����ӵ������µĹ���������
     * @param binarylinkinfo BinaryLinkIfc����
     * @see BinaryLinkInfo
     */
    private void addToUpdatedTable(BinaryLinkIfc binarylinkinfo)
    {
        String obj = getLinkInTable(binarylinkinfo, links);
        if (VERBOSE)
        {
            System.out.println("perform addToUdatedTable ...the key is: " + obj);
        }
        if (obj != null && !updatedLinks.containsKey(obj))
        {
            debug("CappAssociationsPanel.addToUpdatedTable() - aLink: " +
                  binarylinkinfo);
            updatedLinks.put(obj, binarylinkinfo);
        }
    }


    /**
     * �ӹ�������(hashtable)�л�������Ĺ���(binarylinkinfo)��������Ķ���
     * @param binarylinkinfo ��������
     * @param hashtable ��������
     * @return String �����Ĺ���(binarylinkinfo)��������Ķ���
     * @see BinaryLinkInfo
     */
    private String getLinkInTable(BinaryLinkIfc binarylinkinfo,
                                  Hashtable hashtable)
    {
        Enumeration enumeration = hashtable.keys();
        String obj = null;
        String otherSideBsoID = taskLogic.getOtherSideBsoID(binarylinkinfo);
        for (; enumeration.hasMoreElements(); )
        {
            String obj1 = (String) enumeration.nextElement();
            if (obj1.equalsIgnoreCase(otherSideBsoID))
            {
                obj = obj1;
                break;
            }
        }
        return obj;
    }


    /**
     * ������һ�ߵĽ�ɫ��
     * @param role String ��ɫ��
     */
    public void setOtherSideRole(String role)
    {
        otherSideRole = role;
        taskLogic.setOtherSideRole(role);
    }


    /**
     * �õ�ѡ�е�����
     * @return int ѡ�е�����
     */
    public int getSelectedColumn()
    {
        return multiList.getSelectedColumn();
    }


    /**
     * ���Ӷ����¼�����
     * @param actionlisener �����¼�����
     */
    public synchronized void addActionListener(ActionListener actionlistener)
    {
        if (actionListeners == null)
        {
            actionListeners = new Vector();

        }
        actionListeners.addElement(actionlistener);
    }


    /**
     * ��ȥ�����¼�����
     * @param actionlisener �����¼�����
     */
    public synchronized void removeActionListener(ActionListener actionlistener)
    {
        if (actionListeners != null)
        {
            actionListeners.removeElement(actionlistener);
        }
    }

    private void notifyActionListeners(AWTEvent awtevent)
    {
        Vector vector;
        synchronized (actionListeners)
        {
            vector = (Vector) actionListeners.clone();
        }
        for (int i = 0; i < vector.size(); i++)
        {
            Object listener = (Object) vector.elementAt(i);
            if (listener instanceof ActionListener)
            {
                //processActionEvent(listener, (ActionEvent)awtevent);
                ((ActionListener) listener).actionPerformed((ActionEvent)
                        awtevent);
            }
        }

    }
//CCBegin SS8
    
    /**
     * ��˳��ӹ����ö���
     * @param linkBsoName
     * @param otherSideBsoName
     * @param proName
     * @param role
     * @return
     * @throws QMRemoteException
     * @throws ClassNotFoundException
     * @author �����
     * @date 2009.04.15
     */
    private Collection getResFromPro(String linkBsoName,String otherSideBsoName,QMProcedureIfc proIfc,String role)throws QMRemoteException,
    ClassNotFoundException {

  	    try {
  	      if (getModel() != null) {
  	        return getModel().getAssociations();
  	      }
  	    }
  	    catch (Exception ee) {
  	      ee.printStackTrace();
  	    }
  	    //��ѯ�������Ķ�������һ�ߵĶ���
  	    Collection queryresult = null;
  	    ServiceRequestInfo info1 = new ServiceRequestInfo();
  	    info1.setServiceName("PersistService");
  	    info1.setMethodName("findValueInfo");
  	    try {
  	    	String otherrole = null;
  	    	//��ȡ��һ�˶���Ľ�ɫ����
  	    	if (otherSideRole != null) {
  	    		otherrole = otherSideRole;
  	    	}else{
  	    		otherrole = JNDIUtil.getLinkRole(otherSideBsoName.trim(), linkBsoName.trim());
  	    		otherSideRole = otherrole;
  	    	}
  	      QMQuery qmquery = new QMQuery(linkBsoName, otherSideBsoName);
  	      System.out.println("proIfc==================="+proIfc);
  	      System.out.println("role==================="+role);
  	    System.out.println("linkBsoName==================="+linkBsoName);
  	      String oneAttr = JNDIUtil.getBinaryLinkAttr(proIfc.getBsoName(), role,
  	                                                  linkBsoName);
  	      QueryCondition cond = new QueryCondition(oneAttr, "=", proIfc.getBsoID());
  	      qmquery.addCondition(0, cond);
  	      qmquery.addAND();
  	      String otherAttr = JNDIUtil.getBinaryLinkAttr(otherSideBsoName,
  	    		  otherrole, linkBsoName);
  	      QueryCondition cond1 = new QueryCondition(otherAttr, "bsoID");
  	      qmquery.addCondition(0, 1, cond1);
  	      qmquery.addOrderBy(0,"bsoID");
  	      Class[] paraClass = {
  	          QMQuery.class};
  	      info1.setParaClasses(paraClass);
  	      Object[] objs = {
  	          qmquery};

  	      info1.setParaValues(objs);
  	      queryresult = (Collection) QMContext.getRequestServer().request(
  	    		  info1);
  	    }
  	    catch (QMException ex) {
  	      ex.printStackTrace();
  	    }
  	    return queryresult;

    }
    //CCEnd SS8

    /**
     * �����Ƿ��ѯ�ӱ�
     * @param flag boolean �Ƿ��ѯ,trueΪ��ѯ
     */
    public void setChildQuery(boolean flag)
    {
        childQuery = flag;
    }


    /**
     * �����Ƿ��ѯ�ӱ�
     * @return boolean �Ƿ��ѯ�ӱ�trueΪ��ѯ
     */
    public boolean getChildQuery()
    {
        return childQuery;
    }


    /**
     * ����ϼ�frame
     * @return �ϼ�frame
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

    private void jbInit()
            throws Exception
    {
    }


    /**
     * ��õ�Ԫ���еĶ���
     * @param row int ��
     * @param col int ��
     * @return Object ��Ԫ���еĶ���
     */
    public Object getCellAt(int row, int col)
    {
        return multiList.getSelectedObject(row, col);
    }

    
    //CCBegin SS6
    /**
     * ������׼������ť�����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     */

    public void setRouteListButtonMnemonic(int mne) {
      if (routelistButton != null) {
        routelistButton.setMnemonic(mne);
        routelistButton.setText("��׼����" + "(" + (char) mne + ")");
      }
      else {
        routelistButtonMne = mne;
        routelistButtonMneDisp = String.valueOf( (char) mne);
      }
    }
    
    //CCEnd SS6
    

    /**
     * ������Ԫ�����,�ָ�ԭֵ
     */
    public void undoCell()
    {
        multiList.undoCell();
    }


    /**
     * ���������ť�����Ƿ�
     * @param mne String ���Ƿ�,��"F"
     */
    public void setBrowseButtonMnemonic(int mne)
    {
        if (browseButton != null)
        {
            browseButton.setMnemonic(mne);
            browseButton.setText(display("48", null) + "(" + (char) mne +
                                 "). . .");
        }
        else
        {
            browseButtonMne = mne;
            browseButtonMneDisp = String.valueOf((char) mne);
        }

    }


    /**
     * ���������ť�����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     * @param disp String ���Ƿ�����ʾ,��"RF"
     */
    public void setBrowseButtonMnemonic(int mne, String disp)
    {
        if (browseButton != null)
        {
            browseButton.setMnemonic(mne);
            browseButton.setText(display("48", null) + "(" + disp + "). . .");
        }
        else
        {
            browseButtonMne = mne;
            browseButtonMneDisp = disp;
        }
    }


    /**
     * ���ò��밴ť�����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     */

    public void setInsertButtonMnemonic(int mne)
    {
        if (insertButton != null)
        {
            insertButton.setMnemonic(mne);
            insertButton.setText(display("17", null) + "(" + (char) mne + ")");
        }
        else
        {
            insertButtonMne = mne;
            insertButtonMneDisp = String.valueOf((char) mne);
        }
    }


    /**
     * ���ò��밴ť�����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     * @param disp String ���Ƿ�����ʾ,��'RF'
     */
    public void setInsertButtonMnemonic(int mne, String disp)
    {
        if (insertButton != null)
        {
            insertButton.setMnemonic(mne);
            insertButton.setText(display("17", null) + "(" + disp + ")");
        }
        else
        {
            insertButtonMne = mne;
            insertButtonMneDisp = disp;
        }
    }


    /**
     * ���ü��㰴ť�����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     */
    public void setCalculateButtonMnemonic(int mne)
    {
        if (calculateButton != null)
        {
            calculateButton.setMnemonic(mne);
            calculateButton.setText(display("calculate", null) + "(" +
                                    (char) mne + ")");
        }
        else
        {
            calculateButtonMne = mne;
            calculateButtonMneDisp = String.valueOf((char) mne);
        }
    }


    /**
     * ���ü��㰴ť�����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     * @param disp String ���Ƿ�����ʾ,��'RF'
     */
    public void setCalculateButtonMnemonic(int mne, String disp)
    {
        if (calculateButton != null)
        {
            calculateButton.setMnemonic(mne);
            calculateButton.setText(display("calculate", null) + "(" + disp +
                                    ")");
        }
        else
        {
            calculateButtonMne = mne;
            calculateButtonMneDisp = disp;
        }
    }


    /**
     * ���ò鿴��ť�����Ƿ�
     * @param mne String ���Ƿ�,��"F"
     * @param disp String ���Ƿ�����ʾ,��'RF'
     */

    public void setViewButtonMnemonic(int mne, String disp)
    {
        if (viewButton != null)
        {
            viewButton.setMnemonic(mne);
            viewButton.setText(display("19", null) + "(" + disp + ")");
        }
        else
        {
            viewButtonMne = mne;
            viewButtonMneDisp = disp;
        }
    }


    /**
     * ���ò鿴��ť�����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     */

    public void setViewButtonMnemonic(int mne)
    {
        if (viewButton != null)
        {
            viewButton.setMnemonic(mne);
            viewButton.setText(display("19", null) + "(" + (char) mne + ")");
        }
        else
        {
            viewButtonMne = mne;
            viewButtonMneDisp = String.valueOf((char) mne);
        }
    }


    /**
     * ����ɾ����ť�����Ƿ�
     * @param mne String ���Ƿ�,��"F"
     */

    public void setRemoveButtonMnemonic(int mne)
    {
        if (removeButton != null)
        {
            removeButton.setMnemonic(mne);
            removeButton.setText(display("18", null) + "(" + (char) mne + ")");
        }
        else
        {
            removeButtonMne = mne;
            removeButtonMneDisp = String.valueOf((char) mne);
        }

    }


    /**
     * ����ɾ����ť�����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     * @param disp String ���Ƿ�����ʾ,��'RF'
     */

    public void setRemoveButtonMnemonic(int mne, String disp)
    {
        if (removeButton != null)
        {
            removeButton.setMnemonic(mne);
            removeButton.setText(display("18", null) + "(" + disp + ")");
        }
        else
        {
            removeButtonMne = mne;
            removeButtonMneDisp = disp;
        }

    }
    
    /**
	 * �����������ƹ���
	 * @param g  boolean �Ƿ���������
	 * @param upButtonLocation
	 * upButtonLocation ���㰴ť�ǵڼ�����ť
	 * ���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
	 * ��������������������ư�ť
	 */

	public void setIsUpDown(boolean g, int upButtonLocation, int downButtonLocation) 
	{
		this.upButtonLocation = upButtonLocation;
		this.downButtonLocation = downButtonLocation;
		isUpDown = g;
	}

	 //���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
	//��������������������ư�ť  begin
	void upButton_ActionPerformed(ActionEvent actionevent)
	{
		try {
			
			WorkThread workthread = new WorkThread(UP);
			workthread.start();
		} catch (Exception _e) {
			_e.printStackTrace();
		}
	}
	void downButton_ActionPerformed(ActionEvent actionevent)
	{
		try {
			
			WorkThread workthread = new WorkThread(DOWN);
			workthread.start();
		} catch (Exception _e) {
			_e.printStackTrace();
		}
	}
	
	
	/**
	 * ���ƹ���
	 */
	protected void upRow()
	{
		int row = multiList.getSelectedRow();
		int colCount = multiList.getNumberOfCols();
        if (row == 0 || row == -1)
        {
            return;
        }
        Object obj = upDownVec.elementAt(row);//begin CR6
        upDownVec.setElementAt(upDownVec.elementAt(row - 1), row);
        upDownVec.setElementAt(obj, row - 1);//end CR6
        for(int i = 0; i < colCount; i++)
        {
        	//CCBegin SS3
//            obj = multiList.getCellText(row, i);
//            multiList.addTextCell(row, i, multiList.getCellText(row - 1, i));
//            multiList.addTextCell(row - 1, i, (String) obj);
            obj = multiList.getCellAt(row, i);
        	multiList.addCell(row,i,multiList.getCellAt(row - 1, i));
        	multiList.addCell(row - 1,i,(CappTextAndImageCell)obj);
        	//CCEnd SS3
        }
        multiList.setSelectedRow(row - 1);
        multiList.deselectRow(row);//CR2
	}
	
	/**
	 * ���ƹ���
	 */
	protected void downRow()
	{
	    int row = multiList.getSelectedRow();
	    int rowCount = multiList.getNumberOfRows();
		int colCount = multiList.getNumberOfCols();  
	    if (row == rowCount - 1 || row == -1)
	    {
	        return;
	    }
	    Object obj = upDownVec.elementAt(row);//begin CR6
	    upDownVec.setElementAt(upDownVec.elementAt(row + 1), row);
	    upDownVec.setElementAt(obj, row + 1);//end CR6
	    for(int i = 0; i < colCount; i++)
        {
//	        obj = multiList.getCellText(row, i);
//	        multiList.addTextCell(row, i,
//	        		multiList.getCellText(row + 1, i));
//	        multiList.addTextCell(row + 1, i, (String) obj);
	    	//CCBegin SS3
	        obj = multiList.getCellAt(row, i);
        	multiList.addCell(row,i,multiList.getCellAt(row + 1, i));
        	multiList.addCell(row + 1,i,(CappTextAndImageCell)obj);
        	//CCEnd SS3
        }
	    multiList.setSelectedRow(row + 1);
	    multiList.deselectRow(row);//CR2
	}
	
	/**
	 * ���multiList
	 * @return
	 */
	public  ComponentMultiList  getMultiList()
	{
		return multiList;
	}
	
	
	
	/**
	 * �������ư�ť�����Ƿ�
	 * 
	 * @param mne
	 *            String ���Ƿ�,��'F'
	 */

	public void setUpButtonMnemonic(int mne)
	{
		if (upButton != null) {
			upButton.setMnemonic(mne);
			upButton.setText(display("17", null) + "(" + (char) mne + ")");
		} else {
			upButtonMne = mne;
			upButtonMneDisp = String.valueOf((char) mne);
		}
	}

	/**
	 * �������ư�ť�����Ƿ�
	 * 
	 * @param mne
	 *            String ���Ƿ�,��'F'
	 * @param disp
	 *            String ���Ƿ�����ʾ,��'RF'
	 */
	public void setUpButtonMnemonic(int mne, String disp)
	{
		if (upButton != null) {
			upButton.setMnemonic(mne);
			upButton.setText(display("17", null) + "(" + disp + ")");
		} else {
			upButtonMne = mne;
			upButtonMneDisp = disp;
		}
	}

	
	/**
	 * �������ư�ť�����Ƿ�
	 * 
	 * @param mne
	 *            String ���Ƿ�,��'F'
	 */

	public void setDownButtonMnemonic(int mne) 
	{
		if (downButton != null) {
			downButton.setMnemonic(mne);
			downButton.setText(display("17", null) + "(" + (char) mne + ")");
		} else {
			downButtonMne = mne;
			downButtonMneDisp = String.valueOf((char) mne);
		}
	}

	/**
	 * �������ư�ť�����Ƿ�
	 * 
	 * @param mne
	 *            String ���Ƿ�,��'F'
	 * @param disp
	 *            String ���Ƿ�����ʾ,��'RF'
	 */
	public void setDownButtonMnemonic(int mne, String disp)
	{
		if (downButton != null) {
			downButton.setMnemonic(mne);
			downButton.setText(display("17", null) + "(" + disp + ")");
		} else {
			downButtonMne = mne;
			downButtonMneDisp = disp;
		}
	}
	//���⣨6��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
	// ��������������������ư�ť  end
	    /**
     * ccBegin by liujiakun for jf 20090225
     * @param flag boolean
     */
    public void setMultilistAllowSorting(boolean flag){
    this.multiList.setAllowSorting(flag);
  }
	
//  CCBeginby leixiao 2009-6-24 ԭ���������ID,�������ȡ��  
    /**
     * �������id���������
     * @param m
     * leix for zyc 
     */
    public void setAssociatpart(String  partid)
    {
    	associatpart=partid;
    }

    public String  getAssociatpart()
    {
    	return associatpart;
    }


//CCBegin SS7
    
    private BaseValueIfc linkedTechnics = null;
    private int getResoucebyProcedureButtonLocation;
    private int partaddButtonLocation;
    private boolean isPartadd = false;
    
    public void setLinkedTechnics(BaseValueIfc base) {
        linkedTechnics = base;
      }
    
    
    /**
     * �ӹ�������Դ��ť
     * @param g boolean �Ƿ����
     * @param buttonLocation int ���밴ť�ǵڼ�����ť
     */
    public void setIsGetResouceByProcedure(boolean g, int buttonLocation) {
      getResoucebyProcedureButtonLocation = buttonLocation;
      isGetResouceByProcedure = g;
    }
    
    /**
     * ���㲿���������Ϣ��������
     * @param g boolean �Ƿ����
     * @param buttonLocation int ���밴ť�ǵڼ�����ť
     */
    public void setIsPartadd(boolean g, int buttonLocation) {
      partaddButtonLocation = buttonLocation;
      isPartadd = g;
    }
    
//CCEnd SS7

//CCBegin SS9
    
    /**
     * ���û�ô��㲿���������Ϣ�������е����Ƿ�
     * @param mne String ���Ƿ�,��'F'
     */

    public void setpartaddButtonMnemonic(int mne) {
      if (partaddButton != null) {
        partaddButton.setMnemonic(mne);
        partaddButton.setText("���" + "(" + (char) mne + ")");
      }
      else {
        partaddButtonMne = mne;
        partaddButtonMneDisp = String.valueOf( (char) mne);
      }
    }
    //CCEnd SS9


    private static String  associatpart;
//  CCEndby leixiao 2009-6-24 ԭ���������ID,�������ȡ��  

}
