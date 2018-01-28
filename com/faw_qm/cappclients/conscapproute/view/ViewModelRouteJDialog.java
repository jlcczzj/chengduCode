package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.*;
import com.faw_qm.part.model.*;
import com.faw_qm.technics.consroute.util.RouteWrapData;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.cappclients.conscapproute.controller.CappRouteAction;
//CCBegin SS1
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
//CCEnd SS1

/**
 * <p>Title:�鿴����·�� </p> <p>Description: </p> <p>Copyright: Copyright (c) 2007</p> <p>Company: һ������</p>
 * @author 
 * @version 1.0
 * SS1 A032-2016-0116 �ɶ������㲿������·�߲���ʹ�� liunan 2016-9-12
 */

public class ViewModelRouteJDialog extends JDialog
{
    private JPanel panel1 = new JPanel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanel1 = new JPanel();
    private JLabel numLabel = new JLabel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel jPanel2 = new JPanel();
    private JButton searchJButton = new JButton();
    private JButton stopJButton = new JButton();
    private QMMultiList qMMultiList = new QMMultiList();
    private JPanel jPanel3 = new JPanel();
    //private JButton okJButton = new JButton();
    private JButton cancelJButton = new JButton();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JLabel qMStatus = new JLabel();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();

    /** ���ڱ����Դ�ļ�·�� */
    protected static String RESOURCE = "com.faw_qm.qq.util.OrderKindResource";

    /** ���Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.qq.verbose", "true")).equals("true");

    /** ���ڱ����Դ��Ϣ */
    protected static ResourceBundle resource = null;

    /** ��ֹ���̱�־ */
    protected boolean cancelInProgress = false;

    /** �߳��� */
    private static ThreadGroup theThreadGroup = Thread.currentThread().getThreadGroup();

    /** ���������� */
    private SearchThread searchThread;

    private HashMap resultVector = new HashMap();
   private Vector dataVec=new Vector();
    private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea numjTextArea1 = new JTextArea();

    /**
     * ���캯��
     * @param frame ������
     */
    public ViewModelRouteJDialog(JFrame frame)
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
    public ViewModelRouteJDialog()
    {
        this(null);
    }

    /**
     * �����ʼ��
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("��������·��");
        this.setSize(650, 500);
        this.setModal(true);
        panel1.setLayout(gridBagLayout3);
        panel1.setBounds(new Rectangle(0, 0, 650, 500));
        numLabel.setMaximumSize(new Dimension(24, 22));
        numLabel.setMinimumSize(new Dimension(24, 22));
        numLabel.setPreferredSize(new Dimension(24, 22));
        numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numLabel.setText("������");
        jPanel1.setLayout(gridBagLayout1);
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
        searchJButton.addActionListener(new ViewModelRouteJDialog_searchJButton_actionAdapter(this));
        stopJButton.setMaximumSize(new Dimension(75, 23));
        stopJButton.setMinimumSize(new Dimension(75, 23));
        stopJButton.setPreferredSize(new Dimension(75, 23));
        stopJButton.setToolTipText("Stop");
        stopJButton.setMnemonic('S');
        stopJButton.setText("ֹͣ(S)");
        stopJButton.addActionListener(new ViewModelRouteJDialog_stopJButton_actionAdapter(this));
        jPanel3.setLayout(gridBagLayout2);
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("�ر�(C)");
        cancelJButton.addActionListener(new ViewModelRouteJDialog_cancelJButton_actionAdapter(this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        jLabel1.setRequestFocusEnabled(true);
        jLabel1.setText("��һ�������һ�У�");
        numjTextArea1.setEditable(true);
        jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
        panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
        panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 10), 0, 0));
        jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 6, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 0), 0, 28));
        panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(22, 20, 0, 10), 10, 0));
        jTabbedPane1.add(jPanel1, "��������·��");
        getContentPane().add(panel1, null);
        jPanel1.add(numLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 7), 25, 22));
        jPanel1.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 10));
        jPanel1.add(jScrollPane1, new GridBagConstraints(1, 0, 5, 5, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(1, 0, 6, 0), 299, 119));
        jScrollPane1.getViewport().add(numjTextArea1, null);
        panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
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
        //CCBegin SS1
        //String[] headings = {"id", "���", "����","��Ҫ·��","��Ҫ·��"};
        //qMMultiList.setHeadings(headings);
        //qMMultiList.setRelColWidth(new int[]{0, 1, 1,1,1});
        String comp = "";
        try
        {
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	info1.setClassName("com.faw_qm.doc.util.DocServiceHelper");
        	info1.setMethodName("getUserFromCompany");
        	Class[] classes = {};
        	info1.setParaClasses(classes);
        	Object[] objs = {};
        	info1.setParaValues(objs);
        	comp=(String)server.request(info1);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        if(comp.equals("cd"))
        {
        	String[] headings = {"���","��Ҫ·��","��Ҫ·��"};
        	qMMultiList.setHeadings(headings);
        	qMMultiList.setRelColWidth(new int[]{1,1,1});
        }
        else
        {
        	String[] headings = {"id", "���", "����","��Ҫ·��","��Ҫ·��"};
        	qMMultiList.setHeadings(headings);
        	qMMultiList.setRelColWidth(new int[]{0, 1, 1,1,1});
        }
        //CCEnd SS1
        qMMultiList.setMultipleMode(true);
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
        cancelJButton.setEnabled(flag);
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
     * 
     */
    private synchronized void clearResults()
    {

        qMMultiList.clear();
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
    private Object[] getCondition()
    {
        int rows = numjTextArea1.getLineCount();
        Object[] objs = new Object[rows];
        String text = numjTextArea1.getText().trim();
        text = text.replaceAll("\n", ";");
        StringTokenizer parts = new StringTokenizer(text, ";");
        int i = 0;
        while(i < rows && parts.hasMoreTokens())
        {
            objs[i] = parts.nextToken().toString().trim();
            i++;
        }

        return objs;
    }

    /**
     * ����ҵ��������Ժ�����ֵ�������ѯ������Ȼ��ӳ־û������в�ѯ�����ݡ�
     * @param flag ��flag=true������ԭ�������������ϴβ�ѯ���
     */
    private void processSearchThread()
    {
        if(verbose)
        {
            System.out.println(" processSearchThread()...begin");
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //�Ƿ����ԭ�����
        clearResults();
         paginatePanel.clearResultCache();
         resultVector.clear();
        this.setStatus("�����������ݿ�...");
        //��ò�ѯ����
        Object[] condition = getCondition();
        try
        {
            //���÷��񷽷���ѯ����
            Class[] paraClass = {Object[].class};
            Object[] paraObj = {condition};
            Vector queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "ViewModelRoute", paraClass, paraObj);
            this.postHandleResult(queryresult);
        }catch(Exception e)
        {
            e.printStackTrace();
            String message = QMExceptionHandler.handle(e);
            DialogFactory.showInformDialog(this, message);
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
     * @param searchResult �������(���)
     * @throws QMRemoteException
     */
    private void postHandleResult(Vector searchResult)
    {
        if(searchResult == null || searchResult.size() == 0)
        {
            setStatus("�ҵ� 0 ���㲿��");
            return;
        }
        //����б��е��������
        int oldSize = resultVector.size();
        //��ò�ѯ����
//        Object[] condition = getCondition();
//        if(condition != null && condition.length > 0)
//        {
//            for(int i = 0;i < condition.length;i++)
//            {
//                if(condition[i] != null && condition[i].toString().trim().length() > 0)
//                {
//                    Class[] paraClass = {String.class};
//                    Object[] paraObj = {condition[i].toString()};
//                    QMPartMasterInfo partinfo = (QMPartMasterInfo)RequestHelper.request("TechnicsRouteService", "findPart", paraClass, paraObj);
//                    RouteWrapData wrapdata=(RouteWrapData)searchResult.get(condition[i].toString());
//                    if(wrapdata!=null)
//                    {
//                        resultVector.put(condition[i], wrapdata);
//                    }
//                }
//            }
//        }
//        for(int i = 0;i < searchResult.size();i++)
//        {
//            if(isCancelInProgress())
//            {
//                break;
//            }
//            QMPartMasterInfo orderlist = (QMPartMasterInfo)searchResult.elementAt(i);
//
//            //�ж��б����Ƿ��Ѵ��ڴ˼�¼�������ڣ����ý���������������б���
//            boolean flag = false;
//            if(resultVector.size() != 0)
//            {
//                Object[] ids = resultVector.keySet().toArray();
//                for(int k = 0;k < resultVector.size();k++)
//                {
//                    if(orderlist.getBsoID().equals(ids[k]))
//                    {
//                        flag = true;
//                        break;
//                    }
//                }
//            }
//            if(flag == true)
//            {
//                continue;
//            }
//
//            resultVector.put(orderlist.getBsoID(), orderlist);
//
//        }
        this.dataVec=searchResult;
            setStatus("�����ҵ� " + (searchResult.size() - oldSize) + " �����");

        if(verbose)
        {
            System.out.println("postHandleResult() end");
        }
    }

    private synchronized void addResultToMultiList()
    {
        Vector newV = new Vector();
        newV.addAll(dataVec);
        //��ҳ
        Vector firstPageVector = paginatePanel.paginate(newV);
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
            RouteWrapData wrapdata = (RouteWrapData)objs.elementAt(i);
            //CCBegin SS1
            if(wrapdata.getPartMasterID().indexOf("QMPartMaster_")!=-1)
            {
            //CCEnd SS1
            Class[] c = {String.class};
            Object[] obj = {wrapdata.getPartMasterID()};
            QMPartMasterInfo part=null;
			try {
				part = (QMPartMasterInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
			} catch (QMRemoteException e) {
				e.printStackTrace();
			}
            Image image = this.getStandardIcon(part);
            qMMultiList.addTextCell(i, 0, part.getBsoID());
            qMMultiList.addCell(i, 1, part.getPartNumber(), image);
            qMMultiList.addTextCell(i, 2, part.getPartName());
            qMMultiList.addTextCell(i, 3, wrapdata.getMainStr());
            qMMultiList.addTextCell(i, 4, wrapdata.getSecondStr());
          //CCBegin SS1
          }
          else if(wrapdata.getPartMasterID().indexOf("QMPart_")!=-1)
          {
            Class[] c = {String.class};
            Object[] obj = {wrapdata.getPartMasterID()};
            QMPartInfo part=null;
			try {
				part = (QMPartInfo)RequestHelper.request("PersistService", "refreshInfo", c, obj);
			} catch (QMRemoteException e) {
				e.printStackTrace();
			}
            Image image = this.getStandardIcon(part);
            qMMultiList.addTextCell(i, 0, part.getBsoID());
            qMMultiList.addCell(i, 1, part.getPartNumber(), image);
            qMMultiList.addTextCell(i, 2, part.getPartName());
            qMMultiList.addTextCell(i, 3, wrapdata.getMainStr());
            qMMultiList.addTextCell(i, 4, wrapdata.getSecondStr());
          }
          else
          {
            qMMultiList.addTextCell(i, 0, wrapdata.getPartMasterID());
            qMMultiList.addTextCell(i, 1, wrapdata.getMainStr());
            qMMultiList.addTextCell(i, 2, wrapdata.getSecondStr());
          }
            //CCEnd SS1
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * ���ͼ��
     * @param basevalueinfo
     * @return
     */
    public Image getStandardIcon(BaseValueIfc basevalueinfo)
    {
        Image image = null;
        if(basevalueinfo != null)
        {
            try
            {
                image = QM.getStandardIcon(basevalueinfo.getBsoName());
            }catch(Exception _ex)
            {
                image = null;
            }
        }
        return image;
    }

    /**
     * ִ����������
     * @param e ActionEvent
     */
    void searchJButton_actionPerformed(ActionEvent e)
    {
        setButtons(false);
        //���������߳�
        searchThread = new SearchThread(theThreadGroup, this);
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

        /** ����������� */
        private ViewModelRouteJDialog myDialog;

        /**
         * �����ѯʵ��
         * @param threadgroup �߳���
         * @param dialog ����װ�����������
         * @param flag �ж��Ƿ���ԭ�����������
         */
        public SearchThread(ThreadGroup threadgroup, ViewModelRouteJDialog dialog)
        {
            super(threadgroup);
            myDialog = dialog;
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
                myDialog.processSearchThread();
            }catch(Throwable throwable)
            {
                if(!myDialog.isCancelInProgress())
                {
                    throwable.printStackTrace();
                }
            }finally
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

    void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e)
    {
        this.addObjectToMultiList(paginatePanel.getCurrentObjects());
    }

}

class ViewModelRouteJDialog_searchJButton_actionAdapter implements java.awt.event.ActionListener
{
    private ViewModelRouteJDialog adaptee;

    ViewModelRouteJDialog_searchJButton_actionAdapter(ViewModelRouteJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.searchJButton_actionPerformed(e);
    }
}

class ViewModelRouteJDialog_stopJButton_actionAdapter implements java.awt.event.ActionListener
{
    private ViewModelRouteJDialog adaptee;

    ViewModelRouteJDialog_stopJButton_actionAdapter(ViewModelRouteJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.stopJButton_actionPerformed(e);
    }
}


class ViewModelRouteJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private ViewModelRouteJDialog adaptee;

    ViewModelRouteJDialog_cancelJButton_actionAdapter(ViewModelRouteJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

