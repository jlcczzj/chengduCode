package com.faw_qm.cappclients.conscapproute.view;
//SS1 �ѡ�·�ߡ��޸ĳɡ�·�ߵ�λ�� liuyang 2014-2-11
//SS2 �ѽ���������������Ӹ��µġ���ʼʱ�䡱�ͽ���ʱ��  liuyang 2014-2-24
//SS3 �����㲿��Id�� liuyang 2013-2-25
//SS4 ������Ϊ��ʾ�� Liuyang 2013-2-25
/**
 * SS5 �������׼����֧��ģ������ liunan 2014-2-17
 * SS6 �������100ʱ��ҳ��ʾ�����⣬����ÿҳ��С��Ϊ10�� liunan 2014-2-17
 */
import java.awt.*;


import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.part.model.*;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.cappclients.resource.view.SortingSelectedPanel;
import com.faw_qm.codemanage.model.CodingClassificationIfc;


/**
 * <p>Title:���������׼֪ͨ��������ȡ�����㲿�� </p>
 * @author ��� 2013-10-23
 * @version 1.0
 */

public class JFRouteListSearchJDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private CalendarSelectedPanel beginTime = new CalendarSelectedPanel();
    private CalendarSelectedPanel endTime = new CalendarSelectedPanel();
    //CCBegin SS2
    private CalendarSelectedPanel updateBeginTime = new CalendarSelectedPanel();
    private CalendarSelectedPanel updateEndTime = new CalendarSelectedPanel();
    //CCEnd SS2
    private JPanel panel1 = new JPanel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanel1 = new JPanel();
    private JLabel numLabel = new JLabel();
    private JTextField numJTextField = new JTextField();
    private JCheckBox numJCheckBox = new JCheckBox();
    private JLabel nameLabel = new JLabel();
    private JTextField nameJTextField = new JTextField();
    private JCheckBox nameJCheckBox = new JCheckBox();
    private JLabel creatorLabel = new JLabel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel jPanel2 = new JPanel();
    private JButton searchJButton = new JButton();
    private JButton stopJButton = new JButton();
    private QMMultiList qMMultiList = new QMMultiList();
    private JPanel jPanel3 = new JPanel();
    
    private JLabel routeLabel = new JLabel();
    private SortingSelectedPanel routeSortingSelectedPanel = null;
    private JPanel jPanel4 = new JPanel();
    
    private JButton okJButton = new JButton();
    private JButton cancelJButton = new JButton();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JLabel qMStatus = new JLabel();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    
    

    /** ���ڱ����Դ�ļ�·�� */
    protected static String RESOURCE = "com.faw_qm.qq.util.OrderKindResource";

    /** ���Ա��� */
    private static boolean verbose = true;

    /** ���ڱ����Դ��Ϣ */
    protected static ResourceBundle resource = null;

    /** ��ֹ���̱�־ */
    protected boolean cancelInProgress = false;

    /** �߳��� */
    private static ThreadGroup theThreadGroup = Thread.currentThread().getThreadGroup();

    /** ���������� */
    private SearchThread searchThread;

    private HashMap resultVector = new HashMap();

    private JCheckBox appendResultJCheckBox = new JCheckBox();
    private JCheckBox ignoreCaseCheckBox = new JCheckBox();
    private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
    JLabel jLabelBeginTime = new JLabel();
    JLabel jLabelEndTime = new JLabel();
    //CCBegin SS2
    JLabel jLabelUpdateBeginTime = new JLabel();
    JLabel jLabelUpdateEndTime = new JLabel();
    //CCEnd SS2

    /**
     * ���캯��
     * @param frame ������
     */
    public JFRouteListSearchJDialog(JFrame frame)
    {
        super(frame);
        try
        {
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * ȱʡ�Ĺ��캯��
     */
    public JFRouteListSearchJDialog()
    {
        this(null);
    }

    /**
     * �����ʼ��
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("���������׼֪ͨ��");
        this.setSize(800, 600);
        panel1.setLayout(gridBagLayout3);
        panel1.setBounds(new Rectangle(0, 0, 650, 500));
        numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numLabel.setText("�����׼���");
        jPanel1.setLayout(gridBagLayout1);
        numJCheckBox.setMaximumSize(new Dimension(37, 22));
        numJCheckBox.setMinimumSize(new Dimension(37, 22));
        numJCheckBox.setPreferredSize(new Dimension(37, 22));
        numJCheckBox.setText("��");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setText("�����׼����");
        nameJCheckBox.setMaximumSize(new Dimension(37, 22));
        nameJCheckBox.setMinimumSize(new Dimension(37, 22));
        nameJCheckBox.setPreferredSize(new Dimension(37, 22));
        nameJCheckBox.setText("��");
        jPanel2.setMaximumSize(new Dimension(70, 70));
        jPanel2.setMinimumSize(new Dimension(70, 70));
        jPanel2.setPreferredSize(new Dimension(70, 70));
        jPanel2.setLayout(gridBagLayout4);
        searchJButton.setMaximumSize(new Dimension(75, 23));
        searchJButton.setMinimumSize(new Dimension(75, 23));
        searchJButton.setPreferredSize(new Dimension(75, 23));
        searchJButton.setToolTipText("Search");
        searchJButton.setMnemonic('F');
        searchJButton.setText("����(F)");
        searchJButton.addActionListener(new JFRouteListSearchJDialog_searchJButton_actionAdapter(this));
        stopJButton.setMaximumSize(new Dimension(75, 23));
        stopJButton.setMinimumSize(new Dimension(75, 23));
        stopJButton.setPreferredSize(new Dimension(75, 23));
        stopJButton.setToolTipText("Stop");
        stopJButton.setMnemonic('S');
        stopJButton.setText("ֹͣ(S)");
        stopJButton.addActionListener(new JFRouteListSearchJDialog_stopJButton_actionAdapter(this));
        jPanel3.setLayout(gridBagLayout2);
        
        //CCBegin SS1
//        routeLabel.setText("·��"); 
        routeLabel.setText("·�ߵ�λ"); 
        //CCEnd SS1
        routeSortingSelectedPanel = new SortingSelectedPanel("·����֯����", "�������");
        routeSortingSelectedPanel.setButtonSize(89, 23);
        //CCBegin SS1
//        routeSortingSelectedPanel.setDialogTitle("·��ѡ��");
        routeSortingSelectedPanel.setDialogTitle("·�ߵ�λѡ��");
        //CCEnd SS1
        routeSortingSelectedPanel.setIsSelectCC(true);
        routeSortingSelectedPanel.setIsShorten(true);
        jPanel4.setLayout(gridBagLayout5);
        
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
        okJButton.setEnabled(false);
        okJButton.addActionListener(new JFRouteListSearchJDialog_okJButton_actionAdapter(this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        cancelJButton.addActionListener(new JFRouteListSearchJDialog_cancelJButton_actionAdapter(this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        appendResultJCheckBox.setText("���ӽ��");
        ignoreCaseCheckBox.setText("���Դ�Сд");
        ignoreCaseCheckBox.setSelected(true);
        //CCBegin SS2
//        jLabelBeginTime.setText("��ʼʱ��");
//        jLabelEndTime.setText("����ʱ��");
        jLabelBeginTime.setText("������ʼʱ��");
        jLabelEndTime.setText("��������ʱ��");
        jLabelUpdateBeginTime.setText("���¿�ʼʱ��");
        jLabelUpdateEndTime.setText("���½���ʱ��");
        //CCEnd SS2
        jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
        panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
        panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 10), 0, 0));
        jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 6, 0, 0), 0, 0));
        
        
        jPanel4.add(routeLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        jPanel4.add(routeSortingSelectedPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        jPanel3.add(jPanel4, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 20), 0, 0));
        
        jPanel3.add(okJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 0), 0, 0));
        panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(22, 20, 0, 10), 10, 0));
        jTabbedPane1.add(jPanel1, "���������׼֪ͨ��");
        getContentPane().add(panel1, null);
        jPanel1.add(nameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel1.add(numLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 10, 0));
        jPanel1.add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(6, 8, 3, 0), 0, 0));
        jPanel1.add(numJCheckBox, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 10, 3, 10), 4, 0));
        jPanel1.add(nameJTextField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 3, 0), 0, 0));
        jPanel1.add(nameJCheckBox, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 11, 0, 9), 6, 0));
        jPanel1.add(jLabelBeginTime, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(beginTime, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        jPanel1.add(jLabelEndTime, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(endTime, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        //CCBegin SS2
        jPanel1.add(jLabelUpdateBeginTime, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(updateBeginTime, new GridBagConstraints(1, 6, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        jPanel1.add(jLabelUpdateEndTime, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(3, 0, 6, 0), 0, 0));
        jPanel1.add(updateEndTime, new GridBagConstraints(1, 7, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(3, 8, 6, 0), 0, 0));
        //CCEnd SS2
        panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(appendResultJCheckBox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        panel1.add(ignoreCaseCheckBox, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        localize();

        paginatePanel.addListener(new com.faw_qm.clients.util.PaginateListener()
        {
            public void paginateEvent(com.faw_qm.clients.util.PaginateEvent e)
            {
                paginatePanel_paginateEvent(e);
            }
        });
    }

    /**
     * ���ػ�
     */
    private void localize()
    {
        qMMultiList.setCellEditable(false);
        qMMultiList.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                setOkButtonEnabled();
            }
        });
        String[] headings = {"id", "��׼���", "��׼����", "������", "����ʱ��", "״̬"};
        qMMultiList.setHeadings(headings);
        //CCBegin SS3
//        qMMultiList.setRelColWidth(new int[]{1, 1, 1, 1, 1, 1});
        qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1, 1, 1});
        //CCEnd SS3
        qMMultiList.setMultipleMode(true);
        qMMultiList.addActionListener(new JFRouteListSearchJDialog_qMMultiList_actionAdapter(this));
        qMMultiList.setEnabled(false);

    }

    /**
     * ���ý������ʾλ��
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if(frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if(frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    }

    /**
     * ���ظ��෽����ʹ������ʾ����Ļ����
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * ���ð�ť�ı༭״̬�������ڲ�ʹ��
     * @param flag
     */
    private void setButtons(boolean flag)
    {
        searchJButton.setEnabled(flag);
        stopJButton.setEnabled(!flag);

        if(qMMultiList.getSelectedRows() == null)
        {
            okJButton.setEnabled(false);
        }
        cancelJButton.setEnabled(flag);
    }

    /**
     * ����ȷ����ť��״̬
     */
    private void setOkButtonEnabled()
    {
        if(qMMultiList.getSelectedRows() == null)
        {
            okJButton.setEnabled(false);
        }else
        {
            okJButton.setEnabled(true);
        }
    }

    /**
     * ����״̬����Ϣ
     * @param text ״̬��Ҫ��ʾ���ı�
     */
    private void setStatus(String text)
    {
        qMStatus.setText(text);
    }

    /**
     * ��ս����
     * @param flag ���flag = true,����գ�������
     */
    private synchronized void clearResults(boolean flag)
    {
        if(flag)
        {
            qMMultiList.clear();
            okJButton.setEnabled(false);
        }
        setStatus(" ");
    }

    /**
     * �ж��Ƿ���ֹ�����߳�
     * @return �������True������ֹ�̡߳�
     */
    private boolean isCancelInProgress()
    {
        return cancelInProgress;
    }

    /**
     * �����Ƿ���ֹ�߳�
     * @param flag ���flag=True������ֹ�̡߳�
     */
    private synchronized void setCancelInProgress(boolean flag)
    {
        cancelInProgress = flag;
    }

    /**
     * ��ֹ��������
     */
    private synchronized void cancel()
    {
        if(searchThread != null && searchThread.isAlive())
        {
            setCancelInProgress(true);
            //searchThread.interrupt(); //�����ȥ��������Ϊ��ʹ�����˳��ˣ��ⲻ��ҵ��
            searchThread = null;
        }
    }

    /**
     * ����û�¼�����������
     * @return ��������
     */
    private Object[][] getCondition()
    {
        //֪ͨ�����Ͳ��û�ȡ ֱ���ж�
    	//CCBegin SS2
//        Object[][] objs = new Object[5][2];
        Object[][] objs = new Object[7][2];
        //CCEnd SS2
        objs[0][0] = numJTextField.getText().trim();
        objs[0][1] = new Boolean(!numJCheckBox.isSelected());
        objs[1][0] = nameJTextField.getText().trim();
        objs[1][1] = new Boolean(!nameJCheckBox.isSelected());
        //CCBegin SS5
        objs[2][0] = new Boolean(ignoreCaseCheckBox.isSelected());
        //CCEnd SS5
        objs[3][0] = beginTime.getDate();
        objs[4][0] = endTime.getDate();
        //CCBegin SS2
        objs[5][0] = updateBeginTime.getDate();
        objs[6][0] = updateEndTime.getDate();
        //CCEnd SS2
        if(verbose)
        {
            System.out.println(objs[3][0].toString());
            System.out.println(objs[4][0].toString());
        }
        return objs;
    }

    /**
     * ����ҵ��������Ժ�����ֵ�������ѯ������Ȼ��ӳ־û������в�ѯ�����ݡ�
     * @param flag ��flag=true������ԭ�������������ϴβ�ѯ���
     */
    private void processSearchThread(boolean flag, boolean flag1)
    {
        if(verbose)
        {
            System.out.println(" processSearchThread()...begin");
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //�Ƿ����ԭ�����
        clearResults(!flag);
        
				//CCBegin SS6
        paginatePanel.clearResultCache();
        //CCEnd SS6

        if(!flag)
        {
            resultVector.clear();
        }
        this.setStatus("�����������ݿ�...");
        //��ò�ѯ����
        Object[][] condition = getCondition();
        Vector queryresult = null;
        try
        {
            //����0ʱΪ���õ�
//                //���÷��񷽷���ѯ����
                Class[] paraClass = {Object[][].class};
                Object[] paraObj = {condition};
                queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "getJFRouteList", paraClass, paraObj);
            this.postHandleResult(queryresult);
        }catch(Exception e)
        {
            e.printStackTrace();
            String message = e.getMessage();
            DialogFactory.showInformDialog(this, message);
            setCursor(Cursor.getDefaultCursor());
            if(!isCancelInProgress())
            {
                return;
            }
        }
        setCursor(Cursor.getDefaultCursor());
        if(verbose)
        {
            System.out.println("processSearchThread() end... ");
        }
    }

    /**
     * ���������,����������������б���
     * @param searchResult �������(����·�߱�ֵ����)
     * @throws QMRemoteException
     */
    private void postHandleResult(Vector searchResult)
    {
        if(verbose)
        {
            System.out.println(" postHandleResult() begin...");
        }
        if(searchResult == null || searchResult.size() == 0)
        {
            setStatus("�ҵ� 0 �������׼֪ͨ��");
            return;
        }
        //����б��е�·�߱�����
        if(verbose)
        {
            System.out.println("the result size is:::" + searchResult.size());
        }
        int oldSize = resultVector.size();
        for(int i = 0;i < searchResult.size();i++)
        {
            if(isCancelInProgress())
            {
                break;
            }
            if(verbose)
            {
                //System.out.println("add the object to MultiList start......");
            }
            BaseValueInfo orderlist = null;
            orderlist = (com.faw_qm.technics.route.model.TechnicsRouteListInfo)searchResult.elementAt(i);
            //�ж��б����Ƿ��Ѵ��ڴ˼�¼�������ڣ����ý���������������б���
            boolean flag = false;
            if(resultVector.size() != 0)
            {
                Object[] ids = resultVector.keySet().toArray();
                for(int k = 0;k < resultVector.size();k++)
                {
                    if(orderlist.getBsoID().equals(ids[k]))
                    {
                        flag = true;
                        break;
                    }
                }
            }
            if(flag == true)
            {
                continue;
            }

            resultVector.put(orderlist.getBsoID(), orderlist);

        }

        if(!appendResultJCheckBox.isSelected())
        {
            setStatus("�ҵ� " + resultVector.size() + " �������׼֪ͨ��");
        }else
        {
            setStatus("�����ҵ� " + (resultVector.size() - oldSize) + " �������׼֪ͨ��");
        }

        if(verbose)
        {
            System.out.println("postHandleResult() end");
        }
    }

    private synchronized void addResultToMultiList()
    {
        Vector newV = new Vector();
        newV.addAll(resultVector.values());
        //CCBegin SS6
        //Vector firstPageVector = paginatePanel.paginate(newV);
        Vector firstPageVector = paginatePanel.paginate(newV,10,true);
        //CCEnd SS6
        addObjectToMultiList(firstPageVector);
    }

    /**
     * �������ӵ��б���
     * @param objs Vector
     */
    private void addObjectToMultiList(Vector objs)
    {

        qMMultiList.clear();
        if(objs == null || objs.size() == 0)
        {
            return;
        }
        //����������������б���
        int p = objs.size();
        for(int i = 0;i < p;i++)
        {
            	com.faw_qm.technics.route.model.TechnicsRouteListInfo qqadoptorder = (com.faw_qm.technics.route.model.TechnicsRouteListInfo)objs.elementAt(i);
                qMMultiList.addTextCell(i, 0, qqadoptorder.getBsoID());
                qMMultiList.addTextCell(i, 1, qqadoptorder.getRouteListNumber());    
                qMMultiList.addTextCell(i, 2, qqadoptorder.getRouteListName());
                //CCBegin SS4
//              qMMultiList.addTextCell(i, 3, qqadoptorder.getIterationCreator());
                Class[] c1 = {String.class};
                Object[] obj1 = {qqadoptorder.getIterationCreator()};
                UserInfo userInfo=null;
				try {
					userInfo = (UserInfo)RequestHelper.request("PersistService", "refreshInfo", c1, obj1);
				
				} catch (QMRemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				 String userName = userInfo.getUsersDesc();
			       if (userName == null || userName.trim().length() == 0) {
				      userName = userInfo.getUsersName();
			       }
                qMMultiList.addTextCell(i, 3, userName);
                //CCEnd SS4
                qMMultiList.addTextCell(i, 4, String.valueOf(qqadoptorder.getCreateTime()));
                qMMultiList.addTextCell(i, 5, qqadoptorder.getLifeCycleState().getDisplay());             
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * ִ����������
     * @param e ActionEvent
     */
    void searchJButton_actionPerformed(ActionEvent e)
    {
        setButtons(false);
        //���������߳�
        searchThread = new SearchThread(theThreadGroup, this, appendResultJCheckBox.isSelected(), ignoreCaseCheckBox.isSelected());
        searchThread.start();

    }

    /**
     * ִ�С���ֹ������
     * @param e ActionEvent
     */
    void stopJButton_actionPerformed(ActionEvent e)
    {
        stopJButton.setEnabled(false);
        cancel();
    }


    /**
     * ִ�С�ȷ��������
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        processOKCommond();
    }

    /**
     * ���ȷ����ť�󣬽�ѡ�еĽ����׼֪ͨ��������㲿����ӵ��б���
     */
    private void processOKCommond()
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.dispose();
        BaseValueInfo[] objs = null;
        Vector queryresult = null;
        objs = (com.faw_qm.technics.route.model.TechnicsRouteListInfo[])this.getSelectedDetails();
        //��ѡ�еĽ����׼֪ͨ���װ��һ��Vector����������˽��д�������QMPartMasterIfc[]
        Vector ordervector = new Vector();
        for(int i = 0;i < objs.length;i++)
        {
            ordervector.add(objs[i]);
        }
        Class[] c1 = {Vector.class, String.class};
        Object[] obj1 = {ordervector, ((CodingClassificationIfc)(routeSortingSelectedPanel.getCoding())).getClassSort()};
        System.out.println("ordervector="+ordervector);
        try
        {
            queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findQMPartByJFRouteList", c1, obj1);
            Vector v = new Vector();
            Iterator iter = queryresult.iterator();
            while(iter.hasNext())
            {
                QMPartInfo info = (QMPartInfo)iter.next();
                v.add(info);
            }
            System.out.println("v="+v);
            PartListJDialog p = new PartListJDialog(this,v);
            p.setVisible(true);
            this.setVisible(false);

        }catch(Exception e1)
        {
            e1.printStackTrace();
        }

        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * ��ý����ѡ�е�ҵ�����
     * @return ѡ�е�ҵ�����
     */
    private BaseValueInfo[] getSelectedDetails()
    {
        int[] rows = qMMultiList.getSelectedRows();
        BaseValueInfo[] values = null;
            values = new com.faw_qm.technics.route.model.TechnicsRouteListInfo[rows.length];
        for(int i = 0;i < rows.length;i++)
        {
            int xx = rows[i];
            String bsoid = qMMultiList.getCellText(xx, 0);
            values[i] = (com.faw_qm.technics.route.model.TechnicsRouteListInfo)resultVector.get(bsoid);
            if(verbose)
            {
                System.out.println("ѡ�еĵ����ǣ�" + values[i]);
            }
        }
        return values;
    }

    /**
     * ִ�С�ȡ��������
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }

    /**
     * <p>Title: �ڲ������߳�</p> <p>Description: </p> <p>Copyright: Copyright (c) 2007</p> <p>Company: һ������</p>
     * @author
     * @version 1.0
     */
    class SearchThread extends QMThread
    {
        /** �ж��Ƿ���ԭ����������� */
        private boolean isLeave;
        private boolean isIgnoreCase;

        /** ����������� */
        private JFRouteListSearchJDialog myDialog;

        /**
         * �����ѯʵ��
         * @param threadgroup �߳���
         * @param dialog ����װ�����������
         * @param flag �ж��Ƿ���ԭ�����������
         */
        public SearchThread(ThreadGroup threadgroup, JFRouteListSearchJDialog dialog, boolean flag, boolean flag1)
        {
            super(threadgroup);
            myDialog = dialog;
            isLeave = flag;
            isIgnoreCase = flag1;
        }

        /**
         * ʹ�߳��жϣ�ֹͣ��������
         */
        public synchronized void interrupt()
        {
            if(verbose)
            {
                System.out.println("Inside Interrupt......");
            }
            this.interrupt();
        }

        /**
         * �߳����з�����ִ��������
         */
        public synchronized void run()
        {
            if(verbose)
            {
                System.out.println("SearchThread: run() begin...");
            }
            try
            {
                //ִ������
                myDialog.processSearchThread(isLeave, isIgnoreCase);
            }
            catch(Throwable throwable)
            {
                if(!myDialog.isCancelInProgress())
                {
                    throwable.printStackTrace();
                }
            }
            finally
            {
                if(verbose)
                {
                    System.out.println("Inside run finally......");
                }
                //���ý��水ť����ʾ״̬
                myDialog.setButtons(true);
                //����û�д����ж�״̬
                myDialog.setCancelInProgress(false);
                myDialog.addResultToMultiList();
            }

            if(verbose)
            {
                System.out.println("SearchThread: run() end...return is void");
            }
        }
    }

    void qMMultiList_actionPerformed(ActionEvent e)
    {
        processOKCommond();
    }

    void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e)
    {
        this.addObjectToMultiList(paginatePanel.getCurrentObjects());
    }

}

class JFRouteListSearchJDialog_searchJButton_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_searchJButton_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.searchJButton_actionPerformed(e);
    }
}

class JFRouteListSearchJDialog_stopJButton_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_stopJButton_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.stopJButton_actionPerformed(e);
    }
}

/*class JFRouteListSearchJDialog_routeJButton_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_routeJButton_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.routeJButton_actionPerformed(e);
    }
}*/

class JFRouteListSearchJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_okJButton_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okJButton_actionPerformed(e);
    }
}

class JFRouteListSearchJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_cancelJButton_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

class JFRouteListSearchJDialog_qMMultiList_actionAdapter implements java.awt.event.ActionListener
{
    private JFRouteListSearchJDialog adaptee;

    JFRouteListSearchJDialog_qMMultiList_actionAdapter(JFRouteListSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.qMMultiList_actionPerformed(e);
    }
}
