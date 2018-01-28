/** ���ɳ���QMQuery.java    1.0  2003/02/06
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 *  CR1 2009/04/21 ��־��    ԭ�򣺹��������������´�汾ʱ����
 *                          ������1����get�������Եķ���
 *                                2���Ż������з���ȱ�ݡ�
 *                          ��ע�����ܲ����������ƣ��������������ʿͻ�������
 * CCBegin by liunan 2011-08-25 �򲹶�P035
 * CR4 2011/07/15 ���� �μ�TD2421
 * CCEnd by liunan 2011-08-25
 * SS1 ���÷ָ���λ�� Liuyang 2013.1.31
 * SS2 ���϶�������������ʱ�ò��Ϸ��ࣨ�ǽ������ϡ���Ʒ������������ʱ�������ȷ liuyang 2013.2.19
 * SS3 ���Ϲ���·�����ȱ�ٵķ���  guoxiaoliang  2013-02-04
 * SS4 �����Ƿ���Դ�Сд��ʼֵ liuyang 2013-3-13
 * SS5 �򲹶� TD����2273 liuyang 2013-3-13
 * SS6 ����˳���Ϊ�����Ա������㲿��ʱ����ʱ����ǰ liunan 2014-7-11
 */

package com.faw_qm.cappclients.beans.query;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;   
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//CR4 begin
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
//CR4 end

import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.beans.query.QMPanel;
import com.faw_qm.clients.beans.query.QMStatus;
import com.faw_qm.clients.util.PaginateEvent;
import com.faw_qm.clients.util.PaginateListener;
import com.faw_qm.clients.util.PaginatePanel;
import com.faw_qm.clients.util.QMContext;
import com.faw_qm.clients.util.QMStandardDateFormat;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.extend.exception.ExtendAttException;
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
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.help.QMHelpContext;
import com.faw_qm.help.QMHelpSystem;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.project.model.ProjectInfo;
import com.faw_qm.users.model.ActorInfo;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.util.VersionControlHelper;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
import com.faw_qm.clients.util.DateHelper;
import com.faw_qm.clients.util.QMPropertyVetoException;


/**
 * <p>Title:QmQuery</p>
 * <p>Description:CappQuery ��Ϊbean��CappQuery�дӱ������ݿ��м���ҵ������������
 * �������ɸ���ѡ�ı�ǩ�����ϡ�CappQuery������ҵ���������Ծ������ģ�
 * һ��CappSchema����QmQuery����������Ծ�����༰�����Եļ�����������ǩ���
 * �ͼ�����������CappSchema�����졣����ʹ��CappQueryʱ������CappSchema�Ǳ���ġ�
 * ��������Զ����б����ʽ��ʾ���û����Ե�����������ť��Ĭ�Ͻ���������ѡ��
 * ���Ե���setMultipleMode�������Ƶ�ѡ�����ʶ����б��б�ѡ�ж������ʹ��
 * getSelectedDetails()(��ѡ)��getSelectedDetail()(��ѡ)��</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: FAW_QM Co Ltd</p>
 * @author ������
 * @version 1.0
 *��1�� 20060816Ѧ���޸ģ���ֻ�ܷ�ҳ���ظĳɼ��ɷ�ҳҲ�ɲ���ҳ���޸ķ���processQuery
 *��2��20060816Ѧ���޸ģ��޸�cancel()���������жϵ���stop�ĳɵ���interrupt
 *��3��20060824Ѧ���޸� �޸�ԭ��Ϊ�˼��ٿͻ����ڴ��������ͷŵ����õĶ���
 *��6��20080327 ��־���޸� �޸�ԭ�򣺸߼���ʱ������ʱ�����ĵײ㷽������֧��У��ʱ���ʽ����ַ�����
 *                       Ӧ��Ϊ��Ӧ��Ϊ���ײ���һ����DateHelper��֧�ֶ���У�顣
 *��7��20081111 xucy �޸�  �޸�ԭ��TD����1876���������ӿ��ƣ�����������������������
 */

public class CappQuery extends QMPanel implements ActionListener, ItemListener,
        PropertyChangeListener,
        Serializable
{
    /**
     * ��ʶ������ť�����¼������͡�������ť�����ʱ���ɷ�CappQueryEvent�¼���������ΪFindCMD
     */
    public static final String FindCMD = "Find";

    public static final String ViewCMD = "View";


    /**
     * ��ʶ��ֹ��ť�����¼������͡���ֹ��ť�����ʱ���ɷ�CappQueryEvent�¼���������ΪStopCMD
     */
    public static final String StopCMD = "Stop";


    /**
     * ��ʶ�����ť�����¼������͡������ť�����ʱ���ɷ�CappQueryEvent�¼���������ΪClearCMD
     */
    public static final String ClearCMD = "Clear";


    /**
     * ��ʶȷ����ť�����¼������͡�ȷ����ť�����ʱ���ɷ�CappQueryEvent�¼���������ΪOkCMD
     */
    public static final String OkCMD = "OK";


    /**
     * ��ʶȡ����ť�����¼������͡������ť�����ʱ���ɷ�CappQueryEvent�¼���������ΪQuitCMD
     */
    public static final String QuitCMD = "Close";


    /**
     * ��ʶ������ť�����¼������͡������ť�����ʱ���ɷ�CappQueryEvent�¼���������ΪHelpCMD
     */
    /**
     * ��ʶ�鿴��ť�����¼������͡��鿴��ť�����ʱ���ɷ�CappQueryEvent�¼���������ΪViewCMD
     */
    public static final String HelpCMD = "Help";
    private JButton myFB; //���Ұ�ť
    private JButton mySB; //��ֹ��ť

    private JButton myCB; //�����ť
    private JButton myVB; //�鿴��ť

    private JButton myOB; //ȷ����ť
    private JButton myQB; //ȡ����ť
    private JButton myHB; //������ť


    // ���Ƿ�
    private char myFBMne = 'F'; //���Ұ�ť
    private char mySBMne = 'S'; //��ֹ��ť
    private char myCBMne = 'L'; //�����ť
    private char myVBMne = 'V'; //�鿴��ť
    private char myOBMne = 'Y'; //ȷ����ť
    private char myQBMne = 'C'; //ȡ����ť


    private JLabel myQP;
    private JCheckBox myMB; //�ж��Ƿ���ԭ���Ľ��

    //2007.05.21 xucy ���
    private JCheckBox myIgnoreCase; //�ж��Ƿ����ִ�Сд
    private CappTabPanel myTP; //������ǩҳ
    private CappExAttrPanel myExttrpanel;
    
    //CCBegin by liunan 2011-08-25 �򲹶�P035
    // CR4 begin
    //�ָ����
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JPanel topJpanel;
    private JPanel BottomJpanel;
    //CR4 end
    //CCEnd by liunan 2011-08-25

    //JScrollPane jScrollPane; //������
    private boolean myDialogButtonsVisible = true; //����ȷ�����رա�������ť��ǰ�ɼ���
    private boolean myStatusVisible = true; //״̬���ɼ���


    //private int columnWidth = 20; //�п�
    protected boolean cancelInProgress = false; //��ֹ���̱�־


    //private String my_search_filter;
    private CappSchema mySchema; //��������
    private ComponentMultiList myList; //���������б�
    private QMStatus myStatus; //״̬��
    private Vector myListener; //��������
    private CappProcessQuery myPQ; //����������
    private static ThreadGroup theThreadGroup =
            Thread.currentThread().getThreadGroup(); //�߳���
    private QueryRefreshListener refreshListener; //���¼���
    private QMHelpSystem helpSystem; //����ϵͳ
    private QMHelpContext helpContext; //����������
    private boolean ibaTabVisible; //IBA���Կɼ��Ա�ǩ
    private QueryCondition additionalQueryConditions[]; //���Ӳ�ѯ������
    private Vector conditions; //���Ӳ�ѯ������
    private static boolean verbose = QM.getVerbose(); //debug��ʶ
    private boolean check; //�Ƿ񸽼ӽ��

    //2007.05.22 xucy ���
    //CCBegin SS4
//    private boolean ignoreCase; //�Ƿ���Դ�Сд
    private boolean ignoreCase = true; //�Ƿ���Դ�Сд
    //CCEnd SS4
    private boolean lastIteration; //�Ƿ�������°汾
    private boolean childQuery = true;
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";
    private static String RESOURCE2 = "com.faw_qm.clients.beans.BeansRB";

    private static final String SCREEN_ID = "com.faw_qm.clients.richtothin."; //ע���ļ��е�����ͷ

    private static final String PARAM = "param"; //ע���ļ��е�����β
    private boolean notAcessPersonalFolder = false;

    //20060824Ѧ���޸�,��private�͸ĳ�protected
    protected PaginatePanel paginatePanel; //��ҳ��ʾ������
    private Collection results = new Vector();
    private String bsoName;
    private String orderAttr;
    private int numberInPage = 100; //ÿҳ��ʾ�Ľ������
    private boolean isAppend = false; //�Ƿ��Ǹ��ӽ��
    private boolean isFirstPage = true; //�ж�ȡ�صĽ���Ƿ��ǵ�һҳ
    private boolean isSeries = true;
//CCBegin by leixiao 2009-12-23 �򲹶�v4r3_p002_20091223��TD2709
    //Begin CR2
    private boolean summaryflag = false;
    
    public void setSummaryFlag(boolean bool)
    {
        summaryflag = bool;
    }
    //end CR2
//CCEnd by leixiao 2009-12-23 �򲹶�v4r3_p002_20091223��TD2709
    /**
     * 20060816Ѧ���޸ģ������־û����ҵ�������������
     */
    private ServiceRequestInfo info1;


    /**
     * �����б���Գߴ�
     * @param relWidths �ߴ����ֵ
     **/
    protected void setRelColWidth(int[] relWidths)
    {
        if (myList != null)
        {
            myList.setRelColWidth(relWidths);
        }
    }


    /**
     * �����б���Գߴ�
     * @return
     */
    public int[] getRelColWidth()
    {
        return myList.getRelColWidth();
    }


    /**
     * �����Ƿ��ѯ�ӱ�
     * @param flag boolean
     */
    public void setChildQuery(boolean flag)
    {
        childQuery = flag;
    }

    public boolean getChildQuery()
    {
        return childQuery;
    }


    /**
     * �������°汾
     **/
    public void setLastIteration(boolean isLast)
    {
        lastIteration = isLast;
        handleLast(isLast);
    }


    /**
     * �Ƿ�������°汾
     **/
    public boolean isLastIteration()
    {
        return lastIteration;
    }


    /**
     * ����������°汾
     **/
    private void handleLast(boolean isLast)
    {
        if (isLast)
        {
            QueryCondition[] qc =
                    {
                    VersionControlHelper.getCondForLatest(true)};
            setAdditionalSearchConditions(qc);
        }
    }


    /**
     * �ж��Ƿ񸽼ӽ��
     **/
    private boolean isCheck()
    {
        return check;
    }


    /**
     * �����Ƿ񸽼ӽ��
     **/
    private void setCheck(boolean isCheck)
    {
        check = isCheck;
    }


    /**
     * �ж��Ƿ���Դ�Сд
     * 2007.05.21 xucy add
     */
    private boolean isIgnoreCase()
    {
        return ignoreCase;
    }


    /**
     * �����Ƿ���Դ�Сд
     * 2007.05.21 xucy add
     */
    private void setIgnoreCase(boolean isIgnore)
    {
        ignoreCase = isIgnore;
    }


    /**
     *  �����࣬�������Ȥ����ɾ�Ĳ����ɷ�ˢ���¼�
     * <p>Title:QueryRefreshListener </p>
     * <p>Description: </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    class QueryRefreshListener implements RefreshListener
    {
        public void refreshObject(RefreshEvent refreshevent)
        {
            int i = refreshevent.getAction();
            if (verbose)
            {
                System.out.println("Refresh action = " + i);
            }
            switch (i)
            {
                case RefreshEvent.CREATE: // '\0'
                    try {
						handleCreateEvent(refreshevent);
					} catch (QMException e) {
						e.printStackTrace();
						return;
					}
                    return;
                case RefreshEvent.UPDATE: // '\001'
                    handleUpdateEvent(refreshevent);
                    return;
                case RefreshEvent.DELETE: // '\002'
                    handleDeleteEvent(refreshevent);
                    return;
            }
        }

        QueryRefreshListener()
        {
        }
    }


    /**
     * ������ҵ�������ˢ���߼�
     * @param refreshevent
     * @throws QMException 
     */
    protected void handleCreateEvent(RefreshEvent refreshevent) throws QMException
    {
        Object obj = refreshevent.getTarget();
        if (!(obj instanceof WorkableIfc))
        {
            return;
        }
        WorkableIfc workable = (WorkableIfc) obj;
        try
        {
            WorkableIfc workable1 = getOriginalCopy(workable);
            if (myList.getObjectRow((BaseValueIfc) workable1) >= 0)
            {
                myList.addObject((BaseValueIfc) workable);
                return;
            }
        }
        catch (QMRemoteException exception)
        {
            exception.printStackTrace();
        }
    }


    /**
     * ��þ���WorkableIfc�����ԭ�汾����
     * @param workable
     * @return
     * @throws QMException 
     */
    public static WorkableIfc getOriginalCopy(WorkableIfc workable)
            throws
            QMException
    {
        WorkableIfc workable1 = null;
        if (WorkInProgressHelper.isWorkingCopy(workable))
        {
            ServiceRequestInfo info1 = new ServiceRequestInfo();
            info1.setServiceName("WorkInProgressService");
            info1.setMethodName("originalCopyOf");
            Class[] paraClass =
                    {
                    WorkableIfc.class};
            info1.setParaClasses(paraClass);
            Object[] objs =
                    {
                    workable};
            info1.setParaValues(objs);
            workable1 = (WorkableIfc) QMContext.getRequestServer().
                        request(info1);
        }
        else
        {
            workable1 = workable;
        }
        return workable1;
    }


    /**
     * �������ҵ�������ˢ���߼�
     * @param refreshevent
     */
    protected void handleUpdateEvent(RefreshEvent refreshevent)
    {
        Object obj = refreshevent.getTarget();
        if (verbose)
        {
            System.out.println("----> UPDATE EVENT RECEIVED:  " +
                               refreshevent.toString());
        }
        myList.updateObject((BaseValueIfc) obj);
    }


    /**
     * ����ɾ��ҵ�������ˢ���߼�
     * @param refreshevent
     */
    protected void handleDeleteEvent(RefreshEvent refreshevent)
    {
        Object obj = refreshevent.getTarget();
        if (verbose)
        {
            System.out.println("----> DELETE EVENT RECEIVED:  " +
                               refreshevent.toString());
        }
        int i = myList.getObjectRow((BaseValueIfc) obj);
        if (i > 0)
        {
            myList.removeRow(i);
        }
    }


    /*
     * ���̼���,�������Ȥ�ļ����¼�
     */
    class ButtonKeyAdapter extends KeyAdapter
    {
        public void keyPressed(KeyEvent keyevent)
        {
            Object obj = keyevent.getSource();
            if (obj == myFB)
            {
                myFBEnterKeyPress(keyevent);
            }
            else if (obj == mySB)
            {
                mySBEnterKeyPress(keyevent);
            }
            else if (obj == myCB)
            {
                myCBEnterKeyPress(keyevent);
            }
            else if (obj == myVB)
            {
                myVBEnterKeyPress(keyevent);
            }
            else if (obj == myOB)
            {
                myOBEnterKeyPress(keyevent);
            }
            else if (obj == myQB)
            {
                myQBEnterKeyPress(keyevent);
            }
            else if (obj == myHB)
            {
                myHBEnterKeyPress(keyevent);
            }
        }

        ButtonKeyAdapter()
        {
        }
    }


    /** ��ѡ����� */
    class CheckBoxListener implements ItemListener
    {
        public void itemStateChanged(ItemEvent e)
        {
      	  setCheck(!isCheck());
      	  //CCBegin SS5
          if(e.getSource()==myMB)
          {
              setCheck(!isCheck());
          }
          if(e.getSource()==myIgnoreCase)
          {
           	setIgnoreCase(!isIgnoreCase());
          }
          //CCEnd SS5
        }
    }


    /** ��ѡ�����
     * 2007.05.21 xucy add
     */
    class CheckBoxListener1 implements ItemListener
    {
        public void itemStateChanged(ItemEvent e)
        {
        	  setIgnoreCase(!isIgnoreCase());        
        }
    }


    /**
     * Ĭ�Ϲ��췽��
     */
    public CappQuery()
    {
        this("", true);
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

//CCBegin by liunan 2011-08-25 �򲹶�P035
//   CR4 begin
    /**
     * �ñ��⣬�����������ͣ��Ƿ����״̬������ʵ��
     * @param title ����
     * @param flag ��(true)��(false)����״̬��
     */
    /*public CappQuery(String title, boolean show_status)
    { //String search_filter,
        try
        {
            conditions = new Vector();
            myListener = new Vector();
            //my_search_filter = search_filter;
            myTP = new CappTabPanel(this);

            myList = new ComponentMultiList();
            try
            {
                myList.setMultipleMode(true);
            }
            catch (Exception propertyvetoexception)
            {
                propertyvetoexception.printStackTrace();
            }
            if (show_status)
            {
                myStatus = new QMStatus();
                myStatus.setPreferredSize(new Dimension(WIDTH, 22));
            }
            QM.initializeResources(this);
            ResourceBundle resourcebundle = QM.getMessagesResource();
            ResourceBundle rb2 = ResourceBundle.getBundle(RESOURCE2,
                    Locale.getDefault());
            myFB = addButton(resourcebundle.getString("0"), FindCMD, myFBMne);
            mySB = addButton(resourcebundle.getString("1"), StopCMD, mySBMne);
            myCB = addButton(resourcebundle.getString("2"), ClearCMD, myCBMne);
            myVB = addButton(rb2.getString("19") + "(" + myVBMne + ")", ViewCMD,
                             myVBMne);
            myOB = addButton(resourcebundle.getString("3"), OkCMD, myOBMne);
            myQB = addButton(resourcebundle.getString("4"), QuitCMD, myQBMne);
            //myHB = addButton(resourcebundle.getString("5"), HelpCMD);
            myQP = QMPanel.PAD();
            myMB = new JCheckBox(resourcebundle.getString("6"));
            myMB.addItemListener(new CheckBoxListener());
            //2007.05.21 �촺Ӣ���
            myIgnoreCase = new JCheckBox("���Դ�Сд");
            myIgnoreCase.addItemListener(new CheckBoxListener1());
            //       CCBegin by leixiao 2008-10-10 ԭ�򣺽������,���Դ�СдĬ��ѡ��
            myIgnoreCase.setSelected(true);
            //       CCEnd by leixiao 2008-10-10 ԭ�򣺽������
            paginatePanel = new PaginatePanel();
            paginatePanel.addListener(new PaginateListener()
            {
                public void paginateEvent(PaginateEvent e)
                {
                    paginatePanel_paginateEvent(e);
                }
            });

            setButtons(true);
            initializeHelp();
            setLayout(new GridBagLayout());
            setPad(2, 2, 2, 0);
            add(myTP, 0, 0, 2, 5, 0.97999999999999998D,
                0.29999999999999999D, 1);
            Insets insert = new Insets(27, 5, 0, 0);
            add(myFB, 2, 0, 1, 1, 0.0D, 0.0D, GridBagConstraints.NONE,
                GridBagConstraints.SOUTH, insert, 0, -6); //1
            Insets insert1 = new Insets(8, 5, 0, 0);
            add(mySB, 2, 1, 1, 1, 0.0D, 0.0D, GridBagConstraints.NONE,
                GridBagConstraints.SOUTH, insert1, 0, -6); //1
            Insets insert2 = new Insets(8, 5, 0, 0);
            add(myCB, 2, 2, 1, 1, 0.0D, 0.0D, GridBagConstraints.NONE,
                GridBagConstraints.SOUTH, insert2, 0, -6); //1
            Insets insertV = new Insets(8, 5, 0, 0);
            add(myVB, 2, 3, 1, 1, 0.0D, 0.0D, GridBagConstraints.NONE,
                GridBagConstraints.SOUTH, insertV, 0, -6); //1
            //2007.05.21 xucy ���
            add(myMB, 0, 5, 1, 1, 0.0D, 0.0D, GridBagConstraints.BOTH);
            add(myIgnoreCase, 1, 5, 1, 1, 0.01D, 0.0D, GridBagConstraints.BOTH);
            JPanel buttomPanel = new JPanel();
            buttomPanel.setLayout(new GridBagLayout());
            Insets insertb = new Insets(2, 0, 0, 0);
            add(buttomPanel, 1, 7, 2, 1, 1.0D, 0.0D,
                GridBagConstraints.HORIZONTAL,
                GridBagConstraints.EAST, insertb, 0, 0);
            buttomPanel.add(paginatePanel,
                            new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                    , GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(0, 6, 0, 0), 0, 0));
            buttomPanel.add(myOB, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
                    , GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(10, 0, 10, 0), 0, 0));
            buttomPanel.add(myQB, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                    , GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(10, 8, 10, 0), 0, 0));

            /**Insets insert3 = new Insets ( 2 , 0 , 0 , 8 ) ;
             add ( myOB , 1 , 7 , 1 , 1 , 1.0D , 0.0D , GridBagConstraints.NONE ,
                  GridBagConstraints.EAST , insert3 , 0 , -6 ) ;
                         Insets insert4 = new Insets ( 2 , 0 , 0 , 0 ) ;
                         add ( myQB , 2 , 7 , 1 , 1 , 0.0D , 0.0D ,
                  GridBagConstraints.HORIZONTAL , GridBagConstraints.EAST ,
                  insert4 , 0 , -6 ) ;*/
            /**...................
                   Insets insert4 = new Insets(2, 0, 0, 8);
                   add(myQB, 1, 7, 1, 1, 0.0D, 0.0D, GridBagConstraints.HORIZONTAL,  GridBagConstraints.EAST,insert4,0,-6);
                   Insets insert5 = new Insets(2, 0, 0, 0);
                   add(myHB, 2, 7, 1, 1, 0.0D, 0.0D, GridBagConstraints.HORIZONTAL,  GridBagConstraints.EAST,insert5,0,-6);
                   .............................**/
            //jScrollPane = new JScrollPane();
            //add(jScrollPane, 0, 6, 3, 1, 1.0D, 0.69999999999999996D, 1);
            //jScrollPane.getViewport().add(myList, null);
            /*add(myList, 0, 6, 3, 1, 1.0D, 0.69999999999999996D, 1);
            myList.setCellEditable(false);
            ButtonKeyAdapter buttonkeyadapter = new ButtonKeyAdapter();
            myFB.addKeyListener(buttonkeyadapter);
            mySB.addKeyListener(buttonkeyadapter);
            myCB.addKeyListener(buttonkeyadapter);
            myVB.addKeyListener(buttonkeyadapter);
            myOB.addKeyListener(buttonkeyadapter);
            myQB.addKeyListener(buttonkeyadapter);
            //myHB.addKeyListener(buttonkeyadapter);
            if (myStatus != null)
            {
                add(myStatus, new GridBagConstraints(0, 9, 3, 1, 1.0, 0.0
                        , GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
                        0, 0));
                //myStatus.setValue("    ");
            }
            myList.addActionListener(this);
            myList.addItemListener(this);
            if (refreshListener == null)
            {
                RefreshService.getRefreshService().addRefreshListener(
                        refreshListener = new
                                          QueryRefreshListener());
            }
            myList.setSize(600, 300);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }*/
    /**
     * �ñ��⣬�����������ͣ��Ƿ����״̬������ʵ��
     * @param title ����
     * @param flag ��(true)��(false)����״̬��
     */
    public CappQuery(String title, boolean show_status)
    { 
            conditions = new Vector();
            myListener = new Vector();
            myTP = new CappTabPanel(this);
            myList = new ComponentMultiList();
            myList.setMultipleMode(true);
            JPanel buttomPanel = new JPanel();    
            if (show_status)
            {
                myStatus = new QMStatus();
                myStatus.setPreferredSize(new Dimension(WIDTH, 22));
            }
            QM.initializeResources(this);
            ResourceBundle resourcebundle = QM.getMessagesResource();
            ResourceBundle rb2 = ResourceBundle.getBundle(RESOURCE2,
                    Locale.getDefault());
            myFB = addButton(resourcebundle.getString("0"), FindCMD, myFBMne);//����
            mySB = addButton(resourcebundle.getString("1"), StopCMD, mySBMne);//ֹͣ
            myCB = addButton(resourcebundle.getString("2"), ClearCMD, myCBMne);//���
            myVB = addButton(rb2.getString("19") + "(" + myVBMne + ")", ViewCMD,//�鿴
                             myVBMne);
            myOB = addButton(resourcebundle.getString("3"), OkCMD, myOBMne);//ȷ��
            myQB = addButton(resourcebundle.getString("4"), QuitCMD, myQBMne);//ȡ��
            myQP = QMPanel.PAD();           
            myMB = new JCheckBox(resourcebundle.getString("6"));//���ӽ��
            myMB.addItemListener(new CheckBoxListener());          
            myIgnoreCase = new JCheckBox("���Դ�Сд");
            //       CCBegin by leixiao 2008-10-10 ԭ�򣺽������,���Դ�СдĬ��ѡ��
            myIgnoreCase.setSelected(true);
            //       CCEnd by leixiao 2008-10-10 ԭ�򣺽������
            myIgnoreCase.addItemListener(new CheckBoxListener());

            paginatePanel = new PaginatePanel();
            paginatePanel.addListener(new PaginateListener()
            {
                public void paginateEvent(PaginateEvent e)
                {
                    paginatePanel_paginateEvent(e);
                }
            });
            setButtons(true);
            initializeHelp();
            setLayout(new GridBagLayout());
            setPad(2, 2, 2, 0);
            buttomPanel.setLayout(new GridBagLayout());
            buttomPanel.add(paginatePanel,
                            new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                    , GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(0, 6, 0, 0), 0, 0));
            buttomPanel.add(myOB, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
                    , GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(10, 0, 10, 0), 0, 0));
            buttomPanel.add(myQB, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                    , GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(10, 8, 10, 0), 0, 0));
            myList.setCellEditable(false);
            ButtonKeyAdapter buttonkeyadapter = new ButtonKeyAdapter();
            myFB.addKeyListener(buttonkeyadapter);
            mySB.addKeyListener(buttonkeyadapter);
            myCB.addKeyListener(buttonkeyadapter);
            myVB.addKeyListener(buttonkeyadapter);
            myOB.addKeyListener(buttonkeyadapter);
            myQB.addKeyListener(buttonkeyadapter);
            
            if (myStatus != null)
            {
                add(myStatus, new GridBagConstraints(0, 9, 3, 1, 1.0, 0.0
                        , GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0),
                        0, 0));
               
            }
            myList.addActionListener(this);
            myList.addItemListener(this);
            if (refreshListener == null)
            {
                RefreshService.getRefreshService().addRefreshListener(
                        refreshListener = new
                                          QueryRefreshListener());
            }
            myList.setSize(600, 300);
            
            GridBagLayout griLayout=new GridBagLayout();
            
            topJpanel=new JPanel();
            topJpanel.setLayout(griLayout);
            
            topJpanel.add(myTP,new GridBagConstraints(0, 0, 2,4,  
            		0,0
                    , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 0), 0, 0));

        
            topJpanel.add(myFB,new GridBagConstraints(2,0,1,1,0.0,0.0,GridBagConstraints.NORTH,
                 GridBagConstraints.NONE,  new Insets(35, 0, 0, 0), 0, 0));//����

            topJpanel.add(mySB,new GridBagConstraints(2,0,1,1,0.0,0.0,GridBagConstraints.NORTH,
                    GridBagConstraints.NONE,  new Insets(70, 0, 0, 0), 0, 0));//ֹͣ
            topJpanel.add(myCB,new GridBagConstraints(2,0,1,1,0.0,0.0,GridBagConstraints.NORTH,
                    GridBagConstraints.NONE,  new Insets(105, 0, 0, 0), 0, 0));//���
            topJpanel.add(myVB,new GridBagConstraints(2,0,1,1,0.0,0.0,GridBagConstraints.NORTH,
                    GridBagConstraints.NONE,  new Insets(140,0, 0, 0), 0, 0));//�鿴
            
            topJpanel.add(myMB,new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D,GridBagConstraints.NORTH,
                    GridBagConstraints.NONE,  new Insets(0,0, 0, 0), 0, 0));//���ӽ��
            
          topJpanel.add(myIgnoreCase,new GridBagConstraints(1, 4, 1, 1, 0.01D, 0.0D,
        		  GridBagConstraints.NORTH,
            GridBagConstraints.HORIZONTAL,  new Insets(0, 0, 0, 0), 0, 0));//���Դ�Сд
          
          topJpanel.setBorder(BorderFactory.createLoweredBevelBorder());
          
            BottomJpanel=new JPanel();
           
            BottomJpanel.setLayout(griLayout);
            
            BottomJpanel.add(myList, new GridBagConstraints(0, 4, 3, 1, 1.0, 0.69999999999999996D,
            		GridBagConstraints.WEST,
                    GridBagConstraints.BOTH,  new Insets(0, 0, 0, 0),0,0));
         
            BottomJpanel.add(buttomPanel,new GridBagConstraints(1,7,2,1,1.0,0.0,
                  GridBagConstraints.CENTER,
                  GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            
            jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
            
            jSplitPane1.setBorder(null);
            jSplitPane1.setOpaque(true);
            jSplitPane1.setContinuousLayout(true);
            jSplitPane1.setDividerSize(5);
            jSplitPane1.setOneTouchExpandable(true);
            //jSplitPane1.setResizeWeight(1);
            jSplitPane1.setLastDividerLocation(100);
            //jSplitPane1.setDividerLocation(400);//���÷ָ��ĳ�ʼ��С
                        
            //JScrollPane topJScrollP=new JScrollPane();
            //topJScrollP.getViewport().add(topJpanel);
            
            //jSplitPane1.add(topJScrollP,JSplitPane.TOP);
            jSplitPane1.add(topJpanel,JSplitPane.TOP);
            jSplitPane1.add(BottomJpanel,JSplitPane.BOTTOM);
            
            
            this.add(jSplitPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                    , GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            
    }

//CR4 end    
//CCEnd by liunan 2011-08-25

    /**
     * ���ò���ʱ������˽�����ϼеĶ���
     */
    public void notAcessInPersonalFolder()
    {
        notAcessPersonalFolder = true;
    }


    /**
     * ������չ�������
     * @param bsoName
     * @param secondValue
     */
    public void addExttrPanel(String bsoName, String secondValue)
    {
        this.bsoName = bsoName;
        try
        {
            myExttrpanel = new CappExAttrPanel(bsoName, secondValue,
                                               CappExAttrPanel.SEARCH_MODEL);
            myTP.addTab("��չ����", myExttrpanel);
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * ��MultiListע��ItemListener
     * @param il
     */
    public void addMulitListItemListener(ItemListener il)
    {
        myList.addItemListener(il);
    }


    /**
     * ��ɫ��ʼ��
     */
    private void initColor()
    {
        if (verbose)
        {
            System.out.println(
                    "set the color of the components starting......");
        }
        setForeground(QM.getFGColor());
        setBackground(QM.getBGColor());
        setCanvasForeground(QM.getCanvasFGColor());
        setCanvasBackground(QM.getCanvasBGColor());
    }


    /**
     * ��ť��������
     * @param text ��ť��ʾ�ı�
     * @param actcommand ����ָ��
     * @return
     */
    private JButton addButton(String text, String actcommand)
    {
        JButton button = new JButton(text);
        button.setActionCommand(actcommand);
        button.addActionListener(this);
        button.setSize(65, 23);
        return button;
    }


    /**
     * ��ť��������
     * @param text ��ť��ʾ�ı�
     * @param actcommand ����ָ��
     * @param mne char ���Ƿ�
     * @return
     */
    private JButton addButton(String text, String actcommand, char mne)
    {
        JButton button = new JButton(text);
        button.setMnemonic(mne);
        button.setActionCommand(actcommand);
        button.addActionListener(this);
        button.setSize(65, 23);
        return button;
    }


    /**
     * ˢ�½���
     */
    public void refresh()
    {
        invalidate();
        doLayout();
        if (myList != null)
        {
            myList.repaint();
        }
        if (myTP != null)
        {
            myTP.refresh();
            //repaint();
        }
    }


    /**
     * ������������ǰ��ɫ
     * @return
     */
    public Color getCanvasForeground()
    {
        return myList.getCellFg();
    }


    /**
     * ��������ǰ��ɫ
     * @param color
     */
    public void setCanvasForeground(Color color)
    {
        if (verbose)
        {
            System.out.println("set CellFG is:::" + color.toString());
        }
        myList.setCellFg(color);
        if (verbose)
        {
            System.out.println("setCellFG end....");
        }
        myTP.setCanvasForeground(color);
        refresh();
        return;
    }


    /**
     * ������������ɫ
     * @return
     */
    public Color getCanvasBackground()
    {
        return myList.getCellBg();
    }


    /**
     * ������������ɫ
     * @param color
     */
    public void setCanvasBackground(Color color)
    {
        if (verbose)
        {
            System.out.println("set CellBG is:::" + color.toString());
        }
        myList.setCellBg(color);
        //myList.setBackground(color);
        if (verbose)
        {
            System.out.println("setCellBG end....");
        }
        myTP.setCanvasBackground(color);
        refresh();
        return;
    }


    /**
     * ���ñ���ɫ
     * @param color
     */
    public void setBackground(Color color)
    {
        super.setBackground(color);
        if (myList != null)
        {
            myList.setHeadingBg(color);
        }
        if (myTP != null)
        {
            myTP.setBackground(color);
        }
        if (myFB != null)
        {
            myFB.setBackground(color);
        }
        if (mySB != null)
        {
            mySB.setBackground(color);
        }
        if (myCB != null)
        {
            myCB.setBackground(color);
        }
        if (myVB != null)
        {
            myVB.setBackground(color);
        }
        if (myOB != null)
        {
            myOB.setBackground(color);
        }
        if (myQB != null)
        {
            myQB.setBackground(color);
            //if (myHB != null)
            //  myHB.setBackground(color);
        }
        if (myMB != null)
        {
            myMB.setBackground(color);
        }
        if (myStatus != null)
        {
            myStatus.setBackground(color);
        }
        refresh();
    }


    /**
     * ����ǰ��ɫ
     * @param color
     */
    public void setForeground(Color color)
    {
        super.setForeground(color);
        if (myList != null)
        {
            myList.setHeadingFg(color);
        }
        if (myTP != null)
        {
            myTP.setForeground(color);
        }
        if (myFB != null)
        {
            myFB.setForeground(color);
        }
        if (mySB != null)
        {
            mySB.setForeground(color);
        }
        if (myCB != null)
        {
            myCB.setForeground(color);
        }
        if (myVB != null)
        {
            myVB.setForeground(color);
        }
        if (myOB != null)
        {
            myOB.setForeground(color);
        }
        if (myQB != null)
        {
            myQB.setForeground(color);
            //if (myHB != null)
            //  myHB.setForeground(color);
        }
        if (myMB != null)
        {
            myMB.setForeground(color);
        }
        if (myStatus != null)
        {
            myStatus.setForeground(color);
        }
        refresh();
    }


    /**
     * �жϽ��������б��Ƿ��ѡģʽ
     * @return
     */
    public boolean getMultipleMode()
    {
        if (myList == null)
        {
            return false;
        }
        else
        {
            return myList.isMultipleMode();
        }
    }


    /**
     * ����ҵ����ļ�������
     * @param CappSchema
     * @throws QMException 
     */
    public void setSchema(CappSchema qmschema)
    {
        try {
			cancel();
		} catch (QMException e) {
			e.printStackTrace();
		}
        mySchema = qmschema;
        myTP.setSchema(mySchema);
        if (verbose)
        {
            System.out.println("Calling setSchema in list from query......");
        }
        myList.setSchema(mySchema);
        clearResults(true);
        updateColumnWidth();

    }


    /**
     * ���ȷ�����رա�������ť��ǰ�ɼ��򷵻�true,���򷵻�false
     * @return
     */
    public boolean isDialogButtonsVisible()
    {
        return myDialogButtonsVisible;
    }


    /**
     * ���ÿ�ѡ(ȷ�����رա�����)��ť�Ƿ�ɼ�
     * @param flag true-->�ɼ�,flag-->���ɼ�
     */
    public void setDialogButtonsVisible(boolean flag)
    {
        if (!myDialogButtonsVisible && flag)
        {
            myOB.setVisible(true);
            myVB.setVisible(true);
            myQB.setVisible(true);
            //myHB.setVisible(true);
        }
        else
        if (myDialogButtonsVisible && !flag)
        {
            myOB.setVisible(false);
            myVB.setVisible(false);
            myQB.setVisible(false);
            //myHB.setVisible(false);
        }
        myDialogButtonsVisible = flag;
        refresh();
    }


    /**
     * �ж�״̬���Ƿ�ɼ�
     * @return true-->�ɼ�,false-->���ɼ�
     */
    public boolean isStatusVisible()
    {
        return myStatusVisible;
    }


    /**
     * ����״̬���Ƿ�ɼ�
     * @param flag true-->�ɼ�,false-->���ɼ�
     */
    public void setStatusVisible(boolean flag)
    {
        if (!myStatusVisible && flag && myStatus != null)
        {
            add(myStatus, new GridBagConstraints(0, 9, 3, 1, 0.0, 0.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 0, 0, 0), 0, 0));
            //myStatus.setValue("    ");
        }
        else
        if (myStatusVisible && !flag && myStatus != null)
        {
            remove(myStatus);
        }
        myStatusVisible = flag;
        refresh();
    }


    /**
     * �����п�
     */
    private void updateColumnWidth()
    {
        if (myList != null && mySchema != null)
        {
            try
            {
                int i = mySchema.totalHeadings();
                int as[] = new int[i];
                for (int j = 0; j < i; j++)
                {
                    as[j] = 1;

                }
                myList.setRelColWidth(as);
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
     * ɾ��ѡ�е���
     */
    public void removeSelectedRows()
    {
        int[] i = myList.getSelectedRows();
        for (int l = 0; l < i.length; l++)
        {
            myList.removeRow(i[l]);
        }
    }


    /**
     * ɾ��ѡ�е���
     */
    public void removeSelectedRow()
    {
        int i = myList.getSelectedRow();
        myList.removeRow(i);
    }


    /**
     * ���ʼ�������
     * @return
     */
    public CappSchema getSchema()
    {
        if (mySchema == null)
        {
            mySchema = new CappSchema();
        }
        return mySchema;
    }


    /**
     * ��ֹ��������
     * @throws QMException 
     */
    private synchronized void cancel() throws QMException
    {
        if (isSeries && info1 != null)
        {
            QMContext.getRequestServer().close(info1);
        }

        if (myPQ != null && myPQ.isAlive())
        {
            setCancelInProgress(true);
            //20060816Ѧ���޸ģ���stop�ĳ�interrupt
            myPQ.interrupt();
            myPQ = null;
            setStatus("");
            //20060816Ѧ���޸ģ��˴��������ð�ť״̬
            // setButtons(true);
        }
    }


    /**
     * ��ս����
     * @param flag true-->��գ�false-->����
     */
    public void clearResults(boolean flag)
    {
        if (flag)
        {
            //20060824Ѧ���޸ģ�ԭ�򣺴˴��建��
            paginatePanel.clearResultCache();
            myList.clear();
        }
        setStatus("");
    }


    /**
     * ����״̬����Ϣ
     * @param text
     */
    public void setStatus(String text)
    {
        if (myStatus != null)
        {
            myStatus.setValue(text);
            //return;
        }
        //else {
        processEvent(new CappQueryEvent(this, CappQueryEvent.STATUS, text));
        //  return;
        //}
    }


    /**
     * �����Ƿ���ֹ�߳�
     * @param flag true
     */
    protected synchronized void setCancelInProgress(boolean flag)
    {
        if (verbose)
        {
            System.out.println("Setting cancelInProgress to: " + flag);
        }
        cancelInProgress = flag;
    }


    /**
     * �ж��Ƿ���ֹ�߳�
     * @return
     */
    protected boolean isCancelInProgress()
    {
        return cancelInProgress;
    }


    /**
     * ���ð�ť�ı༭״̬�������ڲ�ʹ��
     * @param flag
     */
    protected void setButtons(boolean flag)
    {
        myFB.setEnabled(flag);
        mySB.setEnabled(!flag);
        myCB.setEnabled(flag);
        if (getSelectedDetail() == null)
        {
            myOB.setEnabled(false);
            myVB.setEnabled(false);
        }
        else
        {
            myOB.setEnabled(flag);
            myVB.setEnabled(flag);
        }
        myQB.setEnabled(flag);
        //myHB.setEnabled(flag);
        myMB.setEnabled(flag);
    }


    /**
     * ���ʽ�����ض��е�ҵ�����
     * @param i ������
     * @return
     */
    public BaseValueIfc getDetail(int i)
    {
        return myList.getObject(i);
    }


    /**
     * ���ʽ����ѡ�е�ҵ�����
     * @return
     */
    public BaseValueIfc getSelectedDetail()
    {
        return myList.getSelectedObject();

    }


    /**
     * ��multilist��ѡ���з���ֵ����
     * @return
     */
    public BaseValueIfc[] getSelectedDetails()
    {
        //if(verbose)
        //  System.out.println("the selected object's type is:::"+myList.getSelectedObjects().getClass());
        return (BaseValueIfc[]) myList.getSelectedObjects();
    }


    /**
     * ˢ�¶����б�һ����
     * @param aInfo
     */
    public void refreshObject(BaseValueIfc aInfo)
    {
        myList.updateObject(aInfo);
    }


    /**
     * �˷��������ڲ�ʹ��
     * @param actionevent
     */
    public void actionPerformed(ActionEvent actionevent)
    {
        try
        {
            if (actionevent.getSource() == myList)
            {
                BaseValueIfc qmobject = myList.getSelectedObject();
                if (qmobject != null)
                {
                    processEvent(new CappQueryEvent(this, CappQueryEvent.OPEN,
                            qmobject));
                }
            }
            else
            if (actionevent.getActionCommand().equalsIgnoreCase(FindCMD))
            {
                setButtons(false);
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                FindCMD));
                //2007.05.22 xucy add
                myPQ = new CappProcessQuery(theThreadGroup, this, isCheck(),
                                            isIgnoreCase()); //myMB.getState()
                myPQ.start();
            }
            else
            if (actionevent.getActionCommand().equalsIgnoreCase(StopCMD))
            {
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                StopCMD));
                mySB.setEnabled(false);
                try {
					cancel();
				} catch (QMException e) {
					e.printStackTrace();
					return;
				}
            }
            else
            if (actionevent.getActionCommand().equalsIgnoreCase(ClearCMD))
            {
                clearResults(true);
                myTP.clear();
                if (myExttrpanel != null)
                {
                    myExttrpanel.clear();
                }
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                ClearCMD));
                myOB.setEnabled(false);
                myVB.setEnabled(false);
            }
            else
            if (actionevent.getActionCommand().equalsIgnoreCase(OkCMD))
            {
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                OkCMD));
            }
            else
            if (actionevent.getActionCommand().equalsIgnoreCase(ViewCMD))
            {
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                ViewCMD));
                viewSelectedObj();
            }
            else
            if (actionevent.getActionCommand().equalsIgnoreCase(QuitCMD))
            {
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                QuitCMD));
                if (refreshListener != null)
                {
                    RefreshService.getRefreshService().removeRefreshListener(
                            refreshListener);
                }
            }
            else
            if (actionevent.getActionCommand().equalsIgnoreCase(HelpCMD))
            {
                getHelpSystem().showHelp("QueryHelp");
            }
        }
        finally
        {
        }
    }


    /**
     * �˷��������ڲ�ʹ��
     * @param itemevent
     */
    public void itemStateChanged(ItemEvent itemevent)
    {
        if (myList == itemevent.getSource())
        {
            String s;
            if (itemevent.getStateChange() == 1)
            {
                s = CappQueryEvent.SELECT;
                myOB.setEnabled(true);
                myVB.setEnabled(true);
            }
            else
            {
                s = CappQueryEvent.DESELECT;
            }
            processEvent(new CappQueryEvent(this, s,
                                            (BaseValueIfc) itemevent.
                                            getItem()));
        }
    }

    public void propertyChange(PropertyChangeEvent propertychangeevent)
    {

    }


    /**
     * ��Ӽ�����
     * @param CappQuerylistener
     */
    public synchronized void addListener(CappQueryListener qmquerylistener)
    {
        myListener.addElement(qmquerylistener);
    }

    public void removeViewButton()
    {
        myVB.setVisible(false);
    }


    /**
     * ɾ��������
     * @param qmquerylistener
     */
    public synchronized void removeListener(CappQueryListener qmquerylistener)
    {

        myListener.removeElement(qmquerylistener);
    }


    /**
     * ����CappQueryEvent�¼�
     * @param CappQueryevent
     */
    private synchronized void processEvent(CappQueryEvent qmqueryevent)
    {
        for (int i = 0; i < myListener.size(); i++)
        {
            CappQueryListener qmquerylistener = (CappQueryListener) myListener.
                                                elementAt(
                    i);

            qmquerylistener.queryEvent(qmqueryevent);
        }
    }


    /**
     * ����ҵ��������ԣ�������ֵ�������ѯ������Ȼ��ӳ־û������в�ѯ�����ݡ�
     * @param flag ��true���ԭ�������false��Դ��������
     * 2007.05.22 xucy add
     * ����(4)2008.02.20 ��־���޸� �޸�ԭ��:�߼�����������������ʱ����ǹ涨���ڸ�ʽ�Լ�������
     *                            Ӧ��Ϊ:ֹͣ����,������ʾ��Ϣ,���û�����������ȷ���ڸ�ʽ.
     * ���⣨5��2008.02.25 Ѧ���޸� �޸�ԭ�򣺰��ղ�Ʒ״̬�����������չ��,û��У���޲�Ʒ״̬������
     */
    protected void processQuery(boolean flag, boolean isIgnoreCase)
    {

        try
        {
            if (myExttrpanel != null)
            {
                if (!myExttrpanel.check())
                {
                    return;
                }
            }
            ResourceBundle resourcebundle = QM.getMessagesResource();
            clearResults(!flag);
            //clearResults(!isIgnoreCase);
            setStatus(resourcebundle.getString("19"));
            QMQuery myquery = new QMQuery(mySchema.getBsoName());
            //2007.05.22 XUCY ADD
            myquery.setIgnoreCase(isIgnoreCase);
            if (mySchema.hasMaster())
            {
                myquery.appendBso(mySchema.getMasterBsoName(), false);
                QueryCondition cond = new QueryCondition("masterBsoID",
                        "bsoID");
                myquery.addCondition(0, 1, cond);
            }

            Vector scv = new Vector(); //���SearchCondition
            //����������ǵڼ������
            //��7��20081111 xucy �ж����������Ƿ�Ϊ�յı�ʶ
            boolean qmattrisnull = true;
            for (int i = 0; i < myTP.total(); i++)
            {
                CappTabGroup qmtabgroup = myTP.get(i);
                for (int j = 0; j < qmtabgroup.total(); j++)
                {
                    CappAttribute qmattribute = qmtabgroup.get(j);
                    if (!qmattribute.isNull())
                    {
                    	//��7��20081111 xucy ������������Ϊ�գ������ñ�ʶΪtrue��
                  	    qmattrisnull = false;
                    	// start if qmattribute.isNull
                        int location = mySchema.isIteratedProperty(
                                qmattribute.
                                getAttributeName()) ? 0 : 1;
                        try
                        {
                            String an = qmattribute.getAttributeName();
                            String at = qmattribute.getType();
                            boolean notF = qmattribute.getNot();
                            if (at.equalsIgnoreCase(CappBusinessInfo.
                                    BoolTYPE))
                            {
                                QueryCondition booleansearch = null;
                                if (qmattribute.getValue().equalsIgnoreCase(
                                        resourcebundle.
                                        getString("17")))
                                {
                                    booleansearch = new QueryCondition(an, true);
                                }
                                else
                                {
                                    booleansearch = new QueryCondition(an, false);
                                }
                                CappSearchCondition sc = new
                                        CappSearchCondition(
                                        booleansearch,
                                        notF, location,
                                        CappSearchCondition.andType);
                                scv.addElement(sc);
                            }
                            else if (at.equalsIgnoreCase(CappBusinessInfo.
                                    TimeTYPE) ||
                                     at.equalsIgnoreCase(CappBusinessInfo.
                                    FltTYPE) ||
                                     at.equalsIgnoreCase(CappBusinessInfo.
                                    LongTYPE) ||
                                     at.equalsIgnoreCase(CappBusinessInfo.
                                    IntTYPE) ||
                                     at.equalsIgnoreCase(CappBusinessInfo.
                                    DoubTYPE)
                                     )
                            {
                                QueryCondition timesearch = null;
                                QueryCondition timesearch1 = null;
                                String from = qmattribute.getFrom();
                                String thru = qmattribute.getThru();
                                boolean hasFrom = from.length() != 0;
                                boolean hasThru = thru.length() != 0;
                                if (at.equalsIgnoreCase(CappBusinessInfo.
                                        TimeTYPE))
                                {
                                    ResourceBundle resourceb = ResourceBundle.
                                            getBundle(
                                            "com.faw_qm.clients.beans.query.dateHelperResource",
                                            Locale.getDefault());
                                    String dateformat = resourceb.getString(
                                            CappAttribute.
                                            QueryDateInputFormat);
                                    Timestamp ts = null, ts1 = null;
                                    //��6��20080327 ��־���޸� �޸�ԭ�򣺸߼���ʱ������ʱ�����ĵײ㷽������֧��У��ʱ���ʽ����ַ�����
                                    //                       Ӧ��Ϊ�����ײ���һ����DateHelper��֧�ֶ���У�顣
                                    if (hasFrom)
                                    {
                                        ts = new Timestamp(
                                                (new DateHelper(from.trim())).getDate(). getTime());
                                    }
                                    if (hasThru)
                                    {
                                        ts1 = new Timestamp(
                                                (new DateHelper(thru.trim())).getDate(). getTime());
                                    }
                                    if (qmattribute.getRange().
                                        equalsIgnoreCase(CappAttribute.
                                            BETWEEN))
                                    {
                                        if (hasFrom)
                                        {
                                            timesearch = new QueryCondition(
                                                    an, CappAttribute.GREATER, //
                                                    ts);
                                            CappSearchCondition sc = new
                                                    CappSearchCondition(
                                                    timesearch,
                                                    notF, location,
                                                    CappSearchCondition.andType);
                                            scv.addElement(sc);
                                        }
                                        if (hasThru)
                                        {
                                            timesearch1 = new QueryCondition(
                                                    an, CappAttribute.LESS,
                                                    ts1);
                                            CappSearchCondition sc1 = new
                                                    CappSearchCondition(
                                                    timesearch1,
                                                    notF, location,
                                                    notF ?
                                                    CappSearchCondition.orType :
                                                    CappSearchCondition.andType);
                                            scv.addElement(sc1);
                                        }
                                    }
                                    else if (qmattribute.getRange().
                                             equalsIgnoreCase(CappAttribute.
                                            EQUAL))
                                    {
                                        long increment = 24 * 60 * 60 * 1000;
                                        if (hasFrom)
                                        {
                                            timesearch = new QueryCondition(
                                                    an, CappAttribute.GREATER,
                                                    ts);
                                            CappSearchCondition sc = new
                                                    CappSearchCondition(
                                                    timesearch,
                                                    notF, location,
                                                    CappSearchCondition.andType);
                                            scv.addElement(sc);
                                            timesearch1 = new QueryCondition(
                                                    an, CappAttribute.LESS,
                                                    new Timestamp(ts.getTime() +
                                                    increment));
                                            CappSearchCondition sc1 = new
                                                    CappSearchCondition(
                                                    timesearch1,
                                                    notF, location,
                                                    notF ?
                                                    CappSearchCondition.orType :
                                                    CappSearchCondition.andType);
                                            scv.addElement(sc1);
                                        }
                                        if (hasThru)
                                        {
                                            QueryCondition qc = new
                                                    QueryCondition(an,
                                                    CappAttribute.GREATER,
                                                    ts1);
                                            CappSearchCondition sc = new
                                                    CappSearchCondition(qc,
                                                    notF, location,
                                                    CappSearchCondition.orType);
                                            scv.addElement(sc);
                                            QueryCondition qc1 = new
                                                    QueryCondition(an,
                                                    CappAttribute.LESS,
                                                    new Timestamp(ts1.getTime() +
                                                    increment));
                                            CappSearchCondition sc1 = new
                                                    CappSearchCondition(qc1,
                                                    notF,
                                                    location,
                                                    notF ?
                                                    CappSearchCondition.orType :
                                                    CappSearchCondition.andType);
                                            scv.addElement(sc1);
                                        }
                                    }
                                    else
                                    {
                                        String attro = qmattribute.getRange();
                                        if (hasFrom)
                                        {
                                            timesearch = new QueryCondition(
                                                    an, attro, ts);
                                            CappSearchCondition sc = new
                                                    CappSearchCondition(
                                                    timesearch,
                                                    notF, location,
                                                    CappSearchCondition.andType);
                                            scv.addElement(sc);
                                        }
                                        if (hasThru)
                                        {
                                            timesearch1 = new QueryCondition(
                                                    an, attro, ts1);
                                            CappSearchCondition sc1 = new
                                                    CappSearchCondition(
                                                    timesearch1,
                                                    notF, location,
                                                    CappSearchCondition.andType); //???
                                            scv.addElement(sc1);
                                        }
                                    }
                                }
                                else
                                {
                                    if (qmattribute.getRange().
                                        equalsIgnoreCase(CappAttribute.
                                            BETWEEN))
                                    {
                                        if (hasFrom)
                                        {
                                            if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.IntTYPE))
                                            {
                                                int intfrom = Integer.parseInt(
                                                        from);
                                                timesearch = new QueryCondition(
                                                        an,
                                                        CappAttribute.GREATER, //
                                                        intfrom);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.FltTYPE))
                                            {
                                                float floatfrom = Float.
                                                        parseFloat(from);
                                                timesearch = new QueryCondition(
                                                        an,
                                                        CappAttribute.GREATER, //
                                                        floatfrom);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.DoubTYPE))
                                            {
                                                double doublefrom = Double.
                                                        parseDouble(from);
                                                timesearch = new QueryCondition(
                                                        an,
                                                        CappAttribute.GREATER, //
                                                        doublefrom);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.LongTYPE))
                                            {
                                                long longfrom = Long.parseLong(
                                                        from);
                                                timesearch = new QueryCondition(
                                                        an,
                                                        CappAttribute.GREATER, //
                                                        longfrom);
                                            }
                                            CappSearchCondition sc = new
                                                    CappSearchCondition(
                                                    timesearch,
                                                    notF, location,
                                                    CappSearchCondition.andType);
                                            scv.addElement(sc);
                                        }
                                        if (hasThru)
                                        {
                                            if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.IntTYPE))
                                            {
                                                int intthru = Integer.parseInt(
                                                        thru);
                                                timesearch1 = new
                                                        QueryCondition(an,
                                                        CappAttribute.LESS, //
                                                        intthru);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.FltTYPE))
                                            {
                                                float floatthru = Float.
                                                        parseFloat(thru);
                                                timesearch1 = new
                                                        QueryCondition(an,
                                                        CappAttribute.LESS, //
                                                        floatthru);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.DoubTYPE))
                                            {
                                                double doublethru = Double.
                                                        parseDouble(thru);
                                                timesearch1 = new
                                                        QueryCondition(an,
                                                        CappAttribute.LESS, //
                                                        doublethru);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.LongTYPE))
                                            {
                                                long longthru = Long.parseLong(
                                                        thru);
                                                timesearch1 = new
                                                        QueryCondition(an,
                                                        CappAttribute.LESS, //
                                                        longthru);
                                            }
                                            CappSearchCondition sc1 = new
                                                    CappSearchCondition(
                                                    timesearch1,
                                                    notF, location,
                                                    notF ?
                                                    CappSearchCondition.orType :
                                                    CappSearchCondition.andType);
                                            scv.addElement(sc1);
                                        }
                                    }
                                    else
                                    {
                                        String attro = qmattribute.getRange();
                                        if (hasFrom)
                                        {
                                            if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.IntTYPE))
                                            {
                                                int intfrom = Integer.parseInt(
                                                        from);
                                                timesearch = new QueryCondition(
                                                        an, attro, //
                                                        intfrom);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.FltTYPE))
                                            {
                                                float floatfrom = Float.
                                                        parseFloat(from);
                                                timesearch = new QueryCondition(
                                                        an, attro, //
                                                        floatfrom);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.DoubTYPE))
                                            {
                                                double doublefrom = Double.
                                                        parseDouble(from);
                                                timesearch = new QueryCondition(
                                                        an, attro, //
                                                        doublefrom);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.LongTYPE))
                                            {
                                                long longfrom = Long.parseLong(
                                                        from);
                                                timesearch = new QueryCondition(
                                                        an, attro, //
                                                        longfrom);
                                            }
                                            CappSearchCondition sc = new
                                                    CappSearchCondition(
                                                    timesearch,
                                                    notF, location,
                                                    CappSearchCondition.andType);
                                            scv.addElement(sc);
                                        }
                                        if (hasThru)
                                        {
                                            if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.IntTYPE))
                                            {
                                                int intthru = Integer.parseInt(
                                                        thru);
                                                timesearch1 = new
                                                        QueryCondition(an,
                                                        attro, //
                                                        intthru);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.FltTYPE))
                                            {
                                                float floatthru = Float.
                                                        parseFloat(thru);
                                                timesearch1 = new
                                                        QueryCondition(an,
                                                        attro, //
                                                        floatthru);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.DoubTYPE))
                                            {
                                                double doublethru = Double.
                                                        parseDouble(thru);
                                                timesearch1 = new
                                                        QueryCondition(an,
                                                        attro, //
                                                        doublethru);
                                            }
                                            else if (at.equalsIgnoreCase(
                                                    CappBusinessInfo.LongTYPE))
                                            {
                                                long longthru = Long.parseLong(
                                                        thru);
                                                timesearch1 = new
                                                        QueryCondition(an,
                                                        attro, //
                                                        longthru);
                                            }
                                            CappSearchCondition sc1 = new
                                                    CappSearchCondition(
                                                    timesearch1,
                                                    notF, location,
                                                    CappSearchCondition.andType); //???
                                            scv.addElement(sc1);
                                        }
                                    }
                                }
                            }

                            else if (at.equalsIgnoreCase(CappBusinessInfo.
                                    EnumTYPE))
                            {
                                EnumeratedType enumType = qmattribute.
                                        getEnumValue();
                                QueryCondition stringsearch = new
                                        QueryCondition(an,
                                        QueryCondition.EQUAL, enumType.toString());
                                CappSearchCondition sc = new
                                        CappSearchCondition(
                                        stringsearch,
                                        notF, location,
                                        CappSearchCondition.andType);
                                scv.addElement(sc);
                            }
                            else if (at.equalsIgnoreCase(CappBusinessInfo.
                                    StrTYPE)
                                     ||
                                     at.equalsIgnoreCase(CappBusinessInfo.
                                    ViewRefTYPE)
                                     ||
                                     at.equalsIgnoreCase(CappBusinessInfo.
                                    DataFormatRefTYPE)
                                     ||
                                     at.equalsIgnoreCase(CappBusinessInfo.
                                    CRefTYPE)
                                     ||
                                     at.equalsIgnoreCase(CappBusinessInfo.
                                    LifeCycleRefTYPE)
                                     ||
                                     at.equalsIgnoreCase(CappBusinessInfo.
                                    VerIdTYPE)
                                     )
                            {
                                //---------------add wildword support start-------------
                                String attoperator = qmattribute.likeClaus();
                                String attvalue = qmattribute.getValue();
                                if (attoperator.equalsIgnoreCase(
                                        QueryCondition.LIKE))
                                {
                                    attvalue = replaceWildCard(attvalue);
                                }
                                /*QueryCondition stringsearch = new
                                        QueryCondition(an,
                                        attoperator, attvalue);*/

                                QueryCondition stringsearch = attvalue == null ?
                                        new QueryCondition(an,
                                        QueryCondition.IS_NULL) : new
                                        QueryCondition(an,
                                        attoperator, attvalue);

                                //---------------add wildword support end---------------
                                CappSearchCondition sc = new
                                        CappSearchCondition(
                                        stringsearch,
                                        notF, location,
                                        CappSearchCondition.andType);
                                scv.addElement(sc);
                            }
                            else
                            if (at.equalsIgnoreCase(CappBusinessInfo.
                                    PRefTYPE))
                            {
                                ActorInfo actorinfo = (ActorInfo) qmattribute.
                                        getSelectedDetail();
                                if (actorinfo != null)
                                {
                                    QueryCondition principalreferencesearch = new
                                            QueryCondition(
                                            an, QueryCondition.EQUAL,
                                            actorinfo.getBsoID().trim()); //"owner"
                                    CappSearchCondition sc = new
                                            CappSearchCondition(
                                            principalreferencesearch,
                                            notF, location,
                                            CappSearchCondition.andType);
                                    scv.addElement(sc);
                                }
                            }
                            else
                            if (at.equalsIgnoreCase(CappBusinessInfo.
                                    ProjectRefTYPE))
                            {
                                ProjectInfo projectinfo = (ProjectInfo)
                                        qmattribute.
                                        getSelectedDetail();
                                if (projectinfo != null)
                                {
                                    QueryCondition projectreferencesearch = new
                                            QueryCondition(
                                            an, QueryCondition.EQUAL,
                                            projectinfo.getBsoID()); //"projectId"
                                    CappSearchCondition sc = new
                                            CappSearchCondition(
                                            projectreferencesearch,
                                            notF, location,
                                            CappSearchCondition.andType);
                                    scv.addElement(sc);
                                }
                            }
                            else
                            if (at.equalsIgnoreCase(CappBusinessInfo.
                                    CodingTYPE))
                            {
                                BaseValueIfc coding = (BaseValueIfc)
                                        qmattribute.getSelectedDetail();
                                if (coding != null)
                                {
                                    if (coding instanceof CodingIfc)
                                    {
                                        QueryCondition projectreferencesearch = new
                                                QueryCondition(an,
                                                QueryCondition.EQUAL,
                                                coding.getBsoID()); //"projectId"
                                        CappSearchCondition sc = new
                                                CappSearchCondition(
                                                projectreferencesearch, notF,
                                                location,
                                                CappSearchCondition.andType);
                                        scv.addElement(sc);
                                    }
                                    else
                                    if (coding instanceof
                                        CodingClassificationIfc)
                                    {
                                        Collection col = CappTreeHelper.
                                                getOnlyCoding((
                                                CodingClassificationIfc) coding);
                                        if (col != null && col.size() > 0)
                                        {
                                            Object[] codings = col.toArray();
                                            CodingIfc code;
                                            if (codings.length > 1)
                                            {
                                                code = (CodingIfc) codings[0];
                                                QueryCondition
                                                        projectreferencesearch = new
                                                        QueryCondition(an,
                                                        QueryCondition.EQUAL,
                                                        code.getBsoID()); //"projectId"
                                                CappSearchCondition sc = new
                                                        CappSearchCondition(
                                                        projectreferencesearch,
                                                        notF, location,
                                                        CappSearchCondition.
                                                        andType);
                                                sc.addLeftParentheses();
                                                scv.addElement(sc);
                                                for (int ii = 1;
                                                        ii < codings.length - 1;
                                                        ii++)
                                                {
                                                    code = (CodingIfc) codings[
                                                            ii];
                                                    projectreferencesearch = new
                                                            QueryCondition(an,
                                                            QueryCondition.
                                                            EQUAL,
                                                            code.getBsoID()); //"projectId"
                                                    sc = new
                                                            CappSearchCondition(
                                                            projectreferencesearch,
                                                            notF, location,
                                                            CappSearchCondition.
                                                            orType);
                                                    scv.addElement(sc);
                                                }
                                                code = (CodingIfc) codings[
                                                        codings.length - 1];
                                                projectreferencesearch = new
                                                        QueryCondition(an,
                                                        QueryCondition.EQUAL,
                                                        code.getBsoID()); //"projectId"
                                                sc = new CappSearchCondition(
                                                        projectreferencesearch,
                                                        notF, location,
                                                        CappSearchCondition.
                                                        orType);
                                                sc.addRightParentheses();
                                                scv.addElement(sc);
                                            }
                                            else
                                            {
                                                code = (CodingIfc) codings[0];
                                                QueryCondition
                                                        projectreferencesearch = new
                                                        QueryCondition(an,
                                                        QueryCondition.EQUAL,
                                                        code.getBsoID()); //"projectId"
                                                CappSearchCondition sc = new
                                                        CappSearchCondition(
                                                        projectreferencesearch,
                                                        notF, location,
                                                        CappSearchCondition.
                                                        andType);
                                                scv.addElement(sc);
                                            }
                                        }
                                         //CCBegin SS2
                                        else
                                        {
                                                this.setStatus("����������0����¼");
                                                return;  
                                        }
                                        //CCEnd SS2
                                    }
                                }
                            }
                            //Ѧ�� �޸� 20080326  �޸�ԭ��������������ȷ
                            //���⣨5��2008.02.25 Ѧ���޸� �޸�ԭ�򣺰��ղ�Ʒ״̬�����������չ��,û��У���޲�Ʒ״̬������
                           /* if(notF)
                            {
                                QueryCondition stateIsNull = new
                                    QueryCondition(an, QueryCondition.IS_NULL);
                                CappSearchCondition cappState = new
                                    CappSearchCondition(stateIsNull, false,
                                    0, CappSearchCondition.orType);
                                scv.addElement(cappState);
                            }*/
                            //Ѧ�� ����
                            //Ѧ�� 20080326 �޸Ľ���
                        } //end try
                        catch (Exception _ex)
                        {
                            //����(4)2008.02.20 ��־���޸�  ��ʼ.
                            throw _ex;
                        }
                    } //end if qmattribute.isNull()
                } //end for
            } //end for
            //(7) 20081111 xucy�޸� 
            if(qmattrisnull){
            	JOptionPane.showMessageDialog(this.getParent(),
                        resourcebundle.getString("58"),
                        resourcebundle.getString("56"),
                        JOptionPane.
                        INFORMATION_MESSAGE);
                    return;  
            //(7) 20081111 xucy�޸�
              
            } //end 
            int length = scv.size();
            for (int j = 0; j < length; j++)
            {
                myquery = ((CappSearchCondition) scv.elementAt(j)).addCondition(
                        myquery);

            }
            if (getSchema().getBsoName() != null)
            { //getSchema.getBusinessClass()???need or no need
                myquery = appendConditions(myquery);
                try
                {
                    setStatus(resourcebundle.getString("22"));
                    if (childQuery == false)
                    {
                        myquery.setChildQuery(false);

                    }
                    if (getOrderAttr() != null)
                    {
                        int ff = mySchema.isIteratedProperty(
                                getOrderAttr()) ? 0 : 1;
                        //CCBegin SS6
                        //myquery.addOrderBy(ff, getOrderAttr(), false);
                        myquery.addOrderBy(ff, getOrderAttr(), true);
                        //CCEnd SS6

                    }

                    info1 = new ServiceRequestInfo();
                    info1.setServiceName("PersistService");
                    info1.setMethodName("findValueInfo");
                    Class[] paraClass =
                            {
                            QMQuery.class};
                    info1.setParaClasses(paraClass);
                    Object[] objs =
                            {
                            myquery};
                    info1.setParaValues(objs);
                    if (verbose)
                    {
                        System.out.println(
                                "start call the persist server's method......");
                    }
                    RequestServer server = QMContext.getRequestServer();
                    paginatePanel.clearResultCache();
                    isFirstPage = true;

                    int k = myList.totalObjects();
                    if (k > 0)
                    {
                        isAppend = true;
                    }
                    else
                    {
                        isAppend = false;
                    }
                    //20060816Ѧ���޸ģ���ֻ�ܷ�ҳ���ظĳɼ��ɷ�ҳҲ�ɲ���ҳ
                    if (isSeries)
                    {
                        myquery.setIsSeries(true);
                        myquery.setSeriesCount(numberInPage);
                        try
                        {
                            Vector totalResult = new Vector(1000);
                            while (true)
                            {
                                Object result = server.seriesRequest(info1);
                                if (result.equals("over"))
                                {
                                    postHandleResultSeries(totalResult,
                                            isFirstPage, false);
                                    break;
                                }
                                //����result�߼�
                                if (myExttrpanel != null)
                                {
                                    result = filteredExtendAttOf((Collection)
                                            result);
                                }
                                //����е����汾,����
                                if (notAcessPersonalFolder &&
                                    ((Collection) result).size() > 0)
                                {
                               //CCBegin by leixiao 2009-12-23 �򲹶�v4r3_p002_20091223��TD2709                                	
                                	//begin CR2
               			//Class[] paraClass1 = {                //Begin CR1:2���Ż������з���ȱ�ݡ�
             			//	Collection.class};
            			//	Object[] obj = {
         			//  result};
         			Class[] paraClass1 = {Collection.class,Boolean.TYPE};
            			Object[] obj = {result,new Boolean(summaryflag)};
         			//end CR2
         			//CCEnd by leixiao 2009-12-23 �򲹶�v4r3_p002_20091223��TD2709
       				result = (Collection) TechnicsAction.useServiceMethod(
                                                                     "StandardCappService", "filter", paraClass1, obj);
//                								  result = process( (Collection) result);//End CR1
                                }  
                                if (((Collection) result).size() > 0)
                                {
                                    totalResult.addAll((Collection) result);
                                    postHandleResultSeries(totalResult,
                                            isFirstPage, true);
                                }
                            }

                            Object aobj1[] = new Object[3];
                            aobj1[0] = Integer.toString(myList.totalObjects());
                            aobj1[1] = mySchema.getBusinessClassDisplayName();
                            String s4 = resourcebundle.getString("24");
                            aobj1[2] = s4;

                            /* setStatus(MessageFormat.format(resourcebundle.
                                         getString("25"),
                                         aobj1));*/
                            server.close(info1);
                        }
                        catch (ExtendAttException ee)
                        {
                            ee.printStackTrace();
                            if (!isCancelInProgress())
                            {
                                return;
                            }
                        }

                        catch (Exception e)
                        {
                            server.close(info1); //һ��Ҫ�ڴ˴��ر����ӷ���
                        }
                    }
                    else
                    {
                        try
                        {

                            Collection queryresult = (Collection) QMContext.
                                    getRequestServer().
                                    request(info1);
                            postHandleResult(queryresult);
                        }
                        catch (Exception _ex)
                        {
                            _ex.printStackTrace();
                            if (!isCancelInProgress())
                            {
                                throw _ex;
                            } //end if

                        } //end exception
                    }

                }

                catch (Exception _ex)
                {
                    _ex.printStackTrace();
                    if (!isCancelInProgress())
                    {
                        throw _ex;
                    } //end if
                } //end exception
            } //end if bsoName != null
        } //end try
        catch (Exception _e)
        {
            //��6��20080327 ��־���޸� �޸�ԭ�򣺸߼���ʱ������ʱ�����ĵײ㷽������֧��У��ʱ���ʽ����ַ�����
            //                       Ӧ��Ϊ�����ײ���һ����DateHelper��֧�ֶ���У�顣
            //����(4)2008.02.20 ��־���޸�
            if(_e instanceof QMPropertyVetoException)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
                String content = QMMessage.getLocalizedMessage(RESOURCE,
                    "dateCreatedSearch", null);
                JOptionPane.showMessageDialog(this,
                                            content,
                                            title,
                                            JOptionPane.INFORMATION_MESSAGE);

                return;
            }
            //����(4)2008.02.20 ��־���޸� ����.
            _e.printStackTrace();
            QM.showExceptionDialog(this, _e.getLocalizedMessage());

        }
    }


    /**
     * ���������
     * @param vec Vector
     * @param bool boolean ��ʶ�Ƿ�Ϊ��һ����ʾ
     * @param flag boolean ��ʶ�Ƿ�����û�н�����true��ʾû�н���
     */
    protected void postHandleResultSeries(Vector queryresult, boolean bool,
                                          boolean flag)
    {
        // int p=queryresult.size();
        Vector firstPageVector = null;
        ResourceBundle resourcebundle = QM.getMessagesResource();
        //����е����汾,����
        /*  if (notAcessPersonalFolder)
          {
              queryresult = (Vector) process(queryresult);
          }*/
        if (flag)
        {

            firstPageVector = paginatePanel.paginate(queryresult, numberInPage,
                    bool);
        }
        else
        {
            firstPageVector = paginatePanel.getCurrentObjects();
        }
        if (firstPageVector != null && bool &&
            (firstPageVector.size() >= numberInPage || !flag))
        {
            for (int i = 0, j = firstPageVector.size(); i < j; i++)
            {
                if (isCancelInProgress())
                {
                    break;
                }
                if (verbose)
                {
                    System.out.println(
                            "add the object to MultiList start......");
                }
                BaseValueIfc awtobject = (BaseValueIfc) firstPageVector.
                                         elementAt(i);
                myList.addObject(awtobject);

                if (verbose)
                {
                    System.out.println("the BaseValueIfc's bsoID is :::" +
                                       awtobject.getBsoID());
                }

            }
            isFirstPage = false;
        }
        if (verbose)
        {
            System.out.println("the result size is:::" + queryresult.size());
        }
        //int j1 = myList.totalObjects() - k;
        //int j1 = vec.size() ;
        int j1 = queryresult.size();
        String s4;
        if (j1 == 1)
        {
            s4 = resourcebundle.getString("23");
        }
        else
        {
            s4 = resourcebundle.getString("24");
        }
        Object aobj1[] = new Object[3];
        aobj1[0] = s4;
        aobj1[1] = Integer.toString(j1);
        aobj1[2] = resourcebundle.getString("51");

        //mySchema.getBusinessClassDisplayName () ;
        if (isAppend)
        {
            setStatus(MessageFormat.format(resourcebundle.getString("26"),
                                           aobj1));

        }
        else
        {
            setStatus(MessageFormat.format(resourcebundle.getString("25"),
                                           aobj1));
        }

    }

    private IteratedIfc getLastedVersion(MasteredIfc masterInfo) throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsSearchJDialog:getLastedIterations() begin...");
        }
        IteratedIfc ite = null;
        //\u00B5��\u00D3\u00C3��\u00FE\u00CE\u00F1��\u00BD����\u00A3\u00AC\u00BB\u00F1\u00B5\u00C3\u00B9��\u00D2\u00D5\u00BF��\u00B5\u00C4\u00CB��\u00D3\u00D0\u00D0\u00A1��\u00E6��\u00BE\u00A3���㨹\u00C0��\u00B2\u00BB\u00CD\u00AC��\u00D6\u00D6\u00A6\u00A3\u00A9
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        Class[] paraClass =
                {MasteredIfc.class};
        Object[] objs =
                {masterInfo};
        info1.setServiceName("VersionControlService");
        info1.setMethodName("allIterationsOf");
        info1.setParaClasses(paraClass);
        info1.setParaValues(objs);
        Collection collection = null;
        collection = (Collection) RequestServerFactory.getRequestServer().
		             request(info1);
        Iterator iterator = collection.iterator();
        if (iterator.hasNext())
        {
            ite = (IteratedIfc) iterator.next();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsSearchJDialog:getLastedIterations() end...return " +
                               ite);
        }
        return ite;
    }

    protected Collection filteredExtendAttOf(Collection queryresult)
            throws ExtendAttException, QMException
    {
        if (queryresult == null)
        {
            return queryresult;
        }
        if (queryresult.size() == 0)
        {
            return queryresult;
        }
        Vector vec = new Vector();
        Vector vec1 = new Vector();
        vec.addAll(queryresult);
        if (vec.elementAt(0) instanceof MasteredIfc)
        {
            for (int i = 0; i < vec.size(); i++)
            {
                MasteredIfc master = (MasteredIfc) vec.elementAt(i);
                vec1.add(getLastedVersion(master));
            }
        }
        else
        {
            vec1 = vec;
        }
        if (!myExttrpanel.check())
        {
            return vec1;
        }
        else
        {
            HashMap conMap = myExttrpanel.getCondition();
            if (conMap.size() == 0)
            {
                return vec1;
            }
            try
            {
                //   if(conMap.size()==2)
                //   return vec1;
                return ExtendAttributeHandler.filteredExtendAttOf(vec1, conMap);
            }
            catch (ExtendAttException e)
            {
                throw e;
            }
        }
    }


    /**
     * �����а汾�Ķ���Ľ��
     * @param queryresult Collection
     * @return Collection
     * @throws QMException 
     */
    private Collection process(Collection queryresult) throws QMException
    {

        ServiceRequestInfo info1 = new ServiceRequestInfo();
        Class[] paraClass =
                {Collection.class};
        Object[] objs =
                {queryresult};
        info1.setServiceName("ConfigService");
        info1.setMethodName("process");
        info1.setParaClasses(paraClass);
        info1.setParaValues(objs);
        Collection collection = null;
        collection = (Collection) RequestServerFactory.getRequestServer().
		             request(info1);
        Vector vec = new Vector();
        Iterator i = collection.iterator();
        while (i.hasNext())
        {
            vec.add(((Object[]) i.next())[0]);
        }
        return vec;
    }


    /**
     * ���������
     * @param queryresult
     * @throws QMException 
     */
    protected void postHandleResult(Collection queryresult) throws QMException
    {
        int j1 = 0, k = 0;
        if (queryresult != null && queryresult.size() != 0)
        {
            //����е����汾,����
            if (notAcessPersonalFolder)
            {
                queryresult = process(queryresult);
            }
            if (queryresult == null || queryresult.size() == 0)
            {
                return;
            }
            Vector vec = new Vector(queryresult);
            k = myList.totalObjects();

            if (verbose)
            {
                System.out.println("the result size is:::" + queryresult.size());

            }
            Vector firstPageVector = paginatePanel.paginate(vec, 100);
            if (firstPageVector != null)
            {
                for (int i = 0; i < firstPageVector.size(); i++)
                {
                    if (isCancelInProgress())
                    {
                        break;
                    }
                    myList.addObject((BaseValueIfc) firstPageVector.elementAt(i));
                }
            }

            j1 = vec.size();
        }
        String s4;
        ResourceBundle resourcebundle = QM.getMessagesResource();
        if (j1 == 1)
        {
            s4 = resourcebundle.getString("23");
        }
        else
        {
            s4 = resourcebundle.getString("24");
        }
        Object aobj1[] = new Object[3];
        aobj1[0] = Integer.toString(j1);
        aobj1[1] = mySchema.getBusinessClassDisplayName();
        aobj1[2] = s4;
        if (k > 0)
        {
            setStatus(MessageFormat.format(resourcebundle.getString("26"),
                                           aobj1));

        }
        else
        {
            setStatus(MessageFormat.format(resourcebundle.getString("25"),
                                           aobj1));

        }

    }

    private String replaceWildCard(String value)
    {

        if (value.indexOf("*") != -1)
        {
            value = value.replace('*', '%');
        }
        if (value.indexOf("?") != -1)
        {
            value = value.replace('?', '_');
        }
        if (verbose)
        {
            System.out.println(
                    "end replaceWildWord method.the return value is:::" +
                    value);
        }
        return value;
    }


    /**
     * ׷�Ӳ�ѯ����
     * @param myquery
     * @return
     * @throws QueryException
     */
    private QMQuery appendConditions(QMQuery myquery)
            throws QueryException
    {
        if (additionalQueryConditions != null)
        { //???
            if (verbose)
            {
                System.out.println("start append conditions......");
            }
            for (int m = 0; m < additionalQueryConditions.length; m++)
            {
                myquery.addAND();
                myquery.addCondition(additionalQueryConditions[m]);
            }
        }
        int length = conditions.size();
        for (int n = 0; n < length; n++)
        {
            CappSearchCondition c = (CappSearchCondition) conditions.elementAt(
                    n);
            myquery = c.addCondition(myquery);
        }
        return myquery;
    }


    /**
     * ������̴��������¼�
     * @param keyevent
     */
    private void myFBEnterKeyPress(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == 10)
        {
            try
            {
                if (verbose)
                {
                    System.out.println(
                            "call myFBEnterKeyPress method perform the find ......");
                }
                setButtons(false);
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                FindCMD));
                //2007.05.22 xucy add
                myPQ = new CappProcessQuery(theThreadGroup, this, isCheck(),
                                            isIgnoreCase()); //myMB.getState()
                myPQ.start();
            }
            finally
            {
            }
            return;
        }
        else
        {
            return;
        }
    }


    /**
     * ������̴�����ֹ�¼�
     * @param keyevent
     */
    private void mySBEnterKeyPress(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == 10)
        {
            try
            {
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                StopCMD));
                mySB.setEnabled(false);
              
					cancel();
				
            } catch (QMException e) {
				e.printStackTrace();
				return;
			}
            finally
            {
            }
            return;
        }
        else
        {
            return;
        }
    }


    /**
     * ������̴�������¼�
     * @param keyevent
     */
    private void myCBEnterKeyPress(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == 10)
        {
            try
            {
                clearResults(true);
                myTP.clear();
                myExttrpanel.clear();
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                ClearCMD));
                myOB.setEnabled(false);
                myVB.setEnabled(false);
            }
            finally
            {
            }
            return;
        }
        else
        {
            return;
        }
    }

    private void viewSelectedObj()
    {

        try
        {
            BaseValueIfc persistable = getSelectedDetail();
            if (persistable == null)
            {
                return;
            }
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            /* if (persistable instanceof ResourceManagedIfc)
             {
                 ResourceDialog dialog = new ResourceDialog(getParentJFrame(),
                         persistable, "VIEW");
                 dialog.setSize(650, 500);
                 // dialog.setTitle("�鿴��Դ");
                 dialog.setLocation((int) (screenSize.getWidth() - 650) / 2,
                                    (int) (screenSize.getHeight() - 500) / 2);
                 dialog.setVisible(true);
             }
             */
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

        }
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


    /**
     * ������̴�������¼�
     * @param keyevent
     */
    private void myVBEnterKeyPress(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == 10)
        {
            try
            {
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                ViewCMD));
                viewSelectedObj();
            }
            finally
            {
            }
            return;
        }
        else
        {
            return;
        }
    }


    /**
     * ������̴���ȷ���¼�
     * @param keyevent
     */
    private void myOBEnterKeyPress(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == 10)
        {
            try
            {
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                OkCMD));
            }
            finally
            {
            }
            return;
        }
        else
        {
            return;
        }
    }


    /**
     * ������̴���ɾ���¼�
     * @param keyevent
     */
    private void myQBEnterKeyPress(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == 10)
        {
            try
            {
                processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                                QuitCMD));
                if (refreshListener != null)
                {
                    RefreshService.getRefreshService().removeRefreshListener(
                            refreshListener);
                }
            }
            finally
            {
            }
            return;
        }
        else
        {
            return;
        }
    }


    /**
     * ������̴��������¼�
     * @param keyevent
     */
    private void myHBEnterKeyPress(KeyEvent keyevent)
    {
        if (keyevent.getKeyCode() == 10)
        {
            try
            {
                getHelpSystem().showHelp("QueryHelp");
            }
            finally
            {
            }
            return;
        }
        else
        {
            return;
        }
    }


    /**
     * ����֪ͨ
     */
    public void addNotify()
    {
        super.addNotify();
        myFB.requestFocus();
    }


    /**
     * ��������ѡ��
     */
    public void deselectReselect()
    {
        int i = 0;
        i = myList.getSelectedRow();
        myList.deselectRow(i);
        myList.selectRow(i);
    }


    /**
     * ��Ӹ��ӵļ�������
     * @param aquerycondition
     */
    public void setAdditionalSearchConditions(QueryCondition aquerycondition[])
    {
        additionalQueryConditions = aquerycondition;
    }


    /**
     * ����˫���ѯ�� ��Ӹ���һ�����������������������Ӧ��bsoName
     * @param position
     * @param qc
     */
    public void addAdditionlSearchConditions(CappSearchCondition sc)
    {
        conditions.addElement(sc);
    }


    /**
     * ����IBA�������Ŀɼ���
     * @param flag
     */
    public void setIbaTabVisible(boolean flag)
    {
        ibaTabVisible = flag;
        if (myTP != null)
        {
            myTP.setIbaTabVisible(ibaTabVisible); //for addition in the future???
        }
    }


    /**
     * �ж�IBA�������Ŀɼ���
     * @return
     */
    public boolean isIbaTabVisible()
    {
        return ibaTabVisible;
    }


    /**
     * ���ʶ����б�
     * @return
     */
    protected ComponentMultiList getMyList()
    {
        return myList;
    }


    /**
     * ���ʼ�������
     * @return
     */
    protected Vector getMyListener()
    {
        return myListener;
    }


    /**
     * ���ý����ĵ�(��)ѡģʽ
     * @param flag
     * @throws PropertyVetoException
     */
    public void setMultipleMode(boolean flag)
            throws PropertyVetoException
    {
        myList.setMultipleMode(flag);
    }


    /**
     * �жϽ����ѡ(false)ģʽ,��ѡ(true)ģʽ
     * @return
     */
    public boolean isMultipleMode()
    {
        if (myList == null)
        {
            return false;
        }
        else
        {
            return myList.isMultipleMode();
        }
    }


    /**
     * ��ý�����������
     * @return
     */
    public int totalDetails()
    {
        return myList.totalObjects();
    }


    /**
     * ��ʼ������ϵͳ
     */
    private void initializeHelp()
    { //???
        try
        {
            helpSystem = new QMHelpSystem("query",
                                          null, //URL???
                                          "OnlineHelp",
                                          ResourceBundle.getBundle(
                    "com.faw_qm.clients.beans.query.QueryHelpResources",
                    Locale.getDefault()));
            helpContext = new QMHelpContext(this, helpSystem, "query");
            if (helpContext != null)
            {
                helpContext.addComponentHelp(myFB, FindCMD);
                helpContext.addComponentHelp(mySB, StopCMD);
                helpContext.addComponentHelp(myCB, ClearCMD);
                helpContext.addComponentHelp(myQB, QuitCMD);
                helpContext.addComponentHelp(myOB, OkCMD);
                helpContext.addComponentHelp(myMB, "More");
                //helpContext.addComponentHelp(myHB, HelpCMD);
                return;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }


    /**
     * ���ʰ���ϵͳ
     * @return
     */
    private com.faw_qm.help.HelpSystem getHelpSystem()
    {
        if (helpSystem == null)
        {
            initializeHelp();
        }
        return helpSystem;
    }


    /**
     * �����ϵͳ���PropertyChangeListener
     * @param propertychangelistener
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
     * �Ӱ���ϵͳ�Ƴ�PropertyChangeListener
     * @param propertychangelistener
     */

    public synchronized void removeHelpListener(PropertyChangeListener
                                                propertychangelistener)
    {
        if (helpContext != null)
        {
            helpContext.removePropertyChangeListener(propertychangelistener);
        }
    }

    private void writeObject(ObjectOutputStream oos)
            throws IOException
    {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois)
            throws
            ClassNotFoundException,
            IOException
    {
        ois.defaultReadObject();
    }

    private void jbInit()
            throws Exception
    {
        //myQP.setOpaque(true);
        //mySchema.setRequestFocusEnabled(true);
        //mySchema.setToolTipText("");
        //mySchema.setVerifyInputWhenFocusTarget(true);
    }

    void paginatePanel_paginateEvent(PaginateEvent e)
    {
        Vector firstPageVector = paginatePanel.getCurrentObjects();
        myList.clear();
        for (int i = 0; i < firstPageVector.size(); i++)
        {
            if (isCancelInProgress())
            {
                break;
            }
            if (verbose)
            {
                System.out.println("add the object to MultiList start......");
            }
            BaseValueIfc awtobject = (BaseValueIfc) firstPageVector.elementAt(i);
            myList.addObject(awtobject);
            if (verbose)
            {
                System.out.println("the BaseValueIfc's bsoID is :::" +
                                   awtobject.getBsoID());
            }
        }

    }

    public void refreshExttrPanel(String secondValue)
    {
        if (bsoName != null && secondValue != null)
        {
            try
            {
                myTP.remove(myExttrpanel);
                myExttrpanel = new CappExAttrPanel(bsoName, secondValue,
                        CappExAttrPanel.SEARCH_MODEL);
                myTP.addTab("��չ����", myExttrpanel);
                myTP.validate();
                myTP.refresh();
            }
            catch (QMException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            myTP.remove(myExttrpanel);
            myTP.validate();
            myTP.refresh();
        }

    }


    /**
     * ������������
     * @param attr String
     */
    public String getOrderAttr()
    {
        if (orderAttr == null)
        {
            orderAttr = RemoteProperty.getProperty("queryOrderAttr_" +
                    mySchema.getBsoName());
            if (orderAttr != null)
            {
                orderAttr = orderAttr.trim();
            }
        }
        return orderAttr;
    }

    private Object getOrderAttrValue(BaseValueInfo info)
    {
        Object value = null;
        PropertyDescriptor propertydescriptor = null;
        try
        {
            propertydescriptor = new PropertyDescriptor(getOrderAttr(),
                    info.getClass());
            Method method = propertydescriptor.getReadMethod();
            value = method.invoke(info, null);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return value;

    }

    public void addMultiListActionListener(ActionListener lis)
    {
        getMyList().addActionListener(lis);
    }

    /**
     *Ѧ�� ��� 20080411
     *�����ڻ��ܣ���ֹѡ�������չ�̵��������
     */
    public void setAllowSelectMore(boolean bool)
    {
        myList.setMultipleMode(bool);
    }
  //CCBegin SS1
    //CCBegin SS3
    /**
     * ���÷ָ���λ�á�
     * */
    public void setSplitDividerLocation(int location)
    {
        jSplitPane1.setDividerLocation(location);
    }
    //CCEnd SS3
  //CCEnd SS1
}
