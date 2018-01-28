/**
 * ���ɳ���SearchZXNoticeDialog.java    1.0    2014-6-4
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ������������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ����BOM����������ӽ����Ʊ�����������Ӧ���ݵĲ��úͲ����ü� xianglx 2014-8-28
 */
package com.faw_qm.gybomNotice.client.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


import com.faw_qm.bomNotice.model.BomAdoptNoticeIfc;

import com.faw_qm.bomNotice.model.BomChangeNoticeIfc;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.util.QMThread;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybomNotice.util.GYNoticeHelper;


/**
 * ��ѯ���õ���
 * @author ����
 * @version 1.0
 */
public class SearchJFNoticeDialog extends JDialog
{

    private static final long serialVersionUID = 1L;


    /**
     * ���캯��
     * @param JFrame frame������
     * @param BomNoticeMainJFrame frame
     */
    public SearchJFNoticeDialog(GYBomNoticeMainJFrame frame )
    {
        this.view = frame;

        
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /** ��ֹ���̱�־ */
    protected boolean cancelInProgress = false;

    private GYBomNoticeMainJFrame view;
    
    /** �߳��� */
    private static ThreadGroup theThreadGroup = Thread.currentThread().getThreadGroup();

    /** ���������� */
    private SearchThread searchThread;
    /** ��ҳ������ */
    private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
     /** ��ѯ����� */
    private Vector queryresult = new Vector();
    /** ��ѯ����� */
    private HashMap queryresultMap = new HashMap();
    /** ���б����� */
    public ComponentMultiList qMMultiList = new ComponentMultiList();
    private JPanel panel1 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JPanel panel5 = new JPanel();
    private JPanel panel6 = new JPanel();
    private JPanel panel7 = new JPanel();
    private JLabel qMStatus = new JLabel();//״̬��
    private JLabel label1 = new JLabel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JLabel routeListNumberLable = new JLabel();
    private JLabel routeListNameLable = new JLabel();

    private JLabel noticeType = new JLabel();
    private JComboBox noticeTypeBox = new JComboBox();
    private JTextField routeListNumberField = new JTextField();
    private JTextField routeListNameField = new JTextField();



    //��ť
    private JButton okbutton = new JButton();
    private JButton cancelbutton = new JButton();
    private JButton searchbutton = new JButton();
    private JButton stopbutton = new JButton();
    
    
    //��ͷ������Ӧ����
    int routeListBsoIDindex;
    int routeListNumberindex;
    int routeListNameindex;
    int noticeTypeindex;

    /**
     * �����ʼ��
     * @throws Exception
     */
    void jbInit() throws Exception
    {
        this.setModal(true);
        this.setSize(750, 600);
        this.setTitle("�����Ʊ����");
        setViewLocation();
        
        this.setLayout(new GridBagLayout());
        panel1.setLayout(new GridBagLayout());
        panel3.setLayout(new GridBagLayout());
        panel4.setLayout(new GridBagLayout());
        panel5.setLayout(new GridBagLayout());
        panel6.setLayout(new GridBagLayout());
        panel7.setLayout(new GridBagLayout());

        //��ӹ̶���ͷ
        String[] heads = new String[4];
        heads[0] = "noticeBsoID";
        heads[1] = "֪ͨ�����";
        heads[2] = "֪ͨ������";
        heads[3] = "֪ͨ������";

        int[] relcolwidth = new int[4];
        relcolwidth[0] = 0;//����bsoID����ʾ
        relcolwidth[1] = 1;
        relcolwidth[2] = 1;
        relcolwidth[3] = 1;


        ArrayList headslist = new ArrayList();
        for(int i = 0;i < heads.length;i++)
        {
            headslist.add(i, heads[i]);
        }
        ArrayList relcolwidthlist = new ArrayList();
        for(int m = 0;m < relcolwidth.length;m++)
        {
            relcolwidthlist.add(m, relcolwidth[m]);
        }
        qMMultiList.setHeadings(heads);
        qMMultiList.setRelColWidth(relcolwidth);

        //��ñ�ͷ��Ӧ���У��Ժ���ֵȡֵ��
        qMMultiList.setCellEditable(false);
        qMMultiList.setMultipleMode(false);
        qMMultiList.setAllowSorting(true);
       
        qMMultiList.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                setOkButtonEnabled();
            }
        });

        routeListBsoIDindex = headslist.indexOf("noticeBsoID");
        routeListNumberindex = headslist.indexOf("֪ͨ�����");
        routeListNameindex = headslist.indexOf("֪ͨ������");
        noticeTypeindex = headslist.indexOf("֪ͨ������");
        okbutton.setMaximumSize(new Dimension(75, 23));
        okbutton.setMinimumSize(new Dimension(75, 23));
        okbutton.setPreferredSize(new Dimension(75, 23));
        okbutton.setText("ȷ��(Y)");
        okbutton.setMnemonic('Y');
        okbutton.addActionListener(new AddPartByRouteListJDialog_okbutton_actionAdapter(this));
        cancelbutton.setMaximumSize(new Dimension(75, 23));
        cancelbutton.setMinimumSize(new Dimension(75, 23));
        cancelbutton.setPreferredSize(new Dimension(75, 23));
        cancelbutton.setText("ȡ��(C)");
        cancelbutton.setMnemonic('C');
        cancelbutton.addActionListener(new AddPartByRouteListJDialog_cancelbutton_actionAdapter(this));
        searchbutton.setMaximumSize(new Dimension(75, 23));
        searchbutton.setMinimumSize(new Dimension(75, 23));
        searchbutton.setPreferredSize(new Dimension(75, 23));
        searchbutton.setText("����(F)");
        searchbutton.setMnemonic('F');
        searchbutton.addActionListener(new AddPartByRouteListJDialog_searchbutton_actionAdapter(this));
        stopbutton.setMaximumSize(new Dimension(75, 23));
        stopbutton.setMinimumSize(new Dimension(75, 23));
        stopbutton.setPreferredSize(new Dimension(75, 23));
        stopbutton.setText("ֹͣ(S)");
        stopbutton.setMnemonic('S');
        stopbutton.addActionListener(new AddPartByRouteListJDialog_stopbutton_actionAdapter(this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        

      
        routeListNumberLable.setText("֪ͨ�����");
        routeListNameLable.setText("֪ͨ������");

        noticeType.setText("֪ͨ������");
        noticeTypeBox.addItem("����֪ͨ��");
        noticeTypeBox.addItem("���֪ͨ��");
      
        jTabbedPane1.add(panel1, "��������");
        panel1.add(noticeType, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel1.add(noticeTypeBox, new GridBagConstraints(1, 0, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel1.add(routeListNumberLable, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel1.add(routeListNumberField, new GridBagConstraints(1, 1, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel1.add(routeListNameLable, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel1.add(routeListNameField, new GridBagConstraints(1, 2, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        
        
        
        panel3.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 8, 2, 8), 0, 0));

        panel4.add(label1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel4.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 2, 8), 0, 0));
        panel4.add(okbutton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel4.add(cancelbutton, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel7.add(qMStatus, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel5.add(searchbutton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        panel5.add(stopbutton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));

        panel6.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));
        panel6.add(panel5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(33, 2, 2, 0), 0, 0));
        this.add(panel6, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 2, 8), 0, 0));
        this.add(panel3, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 2, 8), 0, 0));
        this.add(panel4, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 10, 8), 0, 0));
        this.add(panel7, new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    }
    /**
     * ����ҳ���ʼ��
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
     * ������
     */
    public static void main(String[] args)
    {
    	SearchJFNoticeDialog s = new SearchJFNoticeDialog(null);
        s.setVisible(true);
    }

    /**
     * ȡ����ť��������
     */
    class AddPartByRouteListJDialog_cancelbutton_actionAdapter implements java.awt.event.ActionListener
    {
        private SearchJFNoticeDialog adaptee;

        AddPartByRouteListJDialog_cancelbutton_actionAdapter(SearchJFNoticeDialog adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.cancelbutton_actionPerformed(e);
        }
    }
    /**
     * ȡ����ť����
     */
    void cancelbutton_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }
    /**
     * ȷ����ť��������
     */
    class AddPartByRouteListJDialog_okbutton_actionAdapter implements java.awt.event.ActionListener
    {
        private SearchJFNoticeDialog adaptee;

        AddPartByRouteListJDialog_okbutton_actionAdapter(SearchJFNoticeDialog adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.okbutton_actionPerformed(e);
        }
    }
    /**
     * ȷ����ť����
     *@param ActionEvent e
     */
    void okbutton_actionPerformed(ActionEvent e)
    {

       
        int[] index = qMMultiList.getSelectedRows();
    
    	String noticeBsoID = (String)qMMultiList.getCellText(index[0], routeListBsoIDindex);//���õ�bsoID
    	BaseValueIfc objss = (BaseValueIfc)queryresultMap.get(noticeBsoID);
//CCBegin SS1
		if (view.getTaskPanel() instanceof ZCBomAdoptNoticeViewPanel)
   		((ZCBomAdoptNoticeViewPanel)view.getTaskPanel()).setJfBomNoticeIfc(objss);
		if (view.getTaskPanel() instanceof CJAdoptNoticeViewPanel)
   		((CJAdoptNoticeViewPanel)view.getTaskPanel()).setJfNoticeIfc(objss);
//CCEnd SS1
    	
        this.setVisible(false);
    }
    /**
     * ��ѯ��ť�����¼�
     */
    class AddPartByRouteListJDialog_searchbutton_actionAdapter implements java.awt.event.ActionListener
    {
        private SearchJFNoticeDialog adaptee;

        AddPartByRouteListJDialog_searchbutton_actionAdapter(SearchJFNoticeDialog adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.searchbutton_actionPerformed(e);
        }
    }
    /**
     * ֹͣ��ť�����¼�
     */
    class AddPartByRouteListJDialog_stopbutton_actionAdapter implements java.awt.event.ActionListener
    {
        private SearchJFNoticeDialog adaptee;

        AddPartByRouteListJDialog_stopbutton_actionAdapter(SearchJFNoticeDialog adaptee)
        {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e)
        {
            adaptee.stopbutton_actionPerformed(e);
        }
    }
    /**
     * ֹͣ��ť�����¼�
     */
    void stopbutton_actionPerformed(ActionEvent e)
    {
        stopbutton.setEnabled(false);
        cancel();
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
     * ���������߳�
     */
    void searchbutton_actionPerformed(ActionEvent e)
    {

    	
            setButtons(false);
            //���������߳�
            searchThread = new SearchThread(theThreadGroup, this);
            searchThread.start();	
    	

    }
    
   
   /**
     * �����߳�
     *@see QMThread
     */
    class SearchThread extends QMThread
    {
        //        /** �ж��Ƿ���ԭ����������� */
        //        private boolean isLeave;

        private SearchJFNoticeDialog myDialog;

        /**
         * �����ѯʵ��
         * @param threadgroup �߳���
         * @param dialog ����װ�����������
         * @param flag �ж��Ƿ���ԭ�����������
         */
        public SearchThread(ThreadGroup threadgroup, SearchJFNoticeDialog dialog)
        {
            super(threadgroup);
            myDialog = dialog; 
            // isLeave = flag;
        }

        /**
         * ʹ�߳��жϣ�ֹͣ��������
         */
        public synchronized void interrupt()
        {
            this.interrupt();
        }

        /**
         * �߳����з�����ִ��������
         */
        public synchronized void run()
        {
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
                //���ý��水ť����ʾ״̬
                myDialog.setButtons(true);
                //����û�д����ж�״̬
                myDialog.setCancelInProgress(false);
                myDialog.addResultToMultiList();
            }

        }
    }
   /**
     * ��ѯ������뵽���б����
     */
    private synchronized void addResultToMultiList()
    {
        Vector firstPageVector = paginatePanel.paginate(queryresult);
        addObjectToMultiList(firstPageVector);
    }
   /**
     * ������뵽���б����
     */
    private void addObjectToMultiList(Vector objs)
    {

        qMMultiList.clear();
        if(objs == null || objs.size() == 0)
        {
            return;
        }
        for(int i = 0;i < objs.size();i++)
        {
            BaseValueIfc base = (BaseValueIfc)objs.get(i);
            if(base!=null){
            	if(base instanceof BomAdoptNoticeIfc){
                    //��ͷ������Ӧ����
                    qMMultiList.addTextCell(i, routeListBsoIDindex, ((BomAdoptNoticeIfc)base).getBsoID());
                    qMMultiList.addTextCell(i, routeListNumberindex, ((BomAdoptNoticeIfc)base).getAdoptnoticenumber());
                    qMMultiList.addTextCell(i, routeListNameindex, ((BomAdoptNoticeIfc)base).getAdoptnoticename());
                    qMMultiList.addTextCell(i, noticeTypeindex,((BomAdoptNoticeIfc)base).getPublishType());
            	}else if(base instanceof BomChangeNoticeIfc){
                    //��ͷ������Ӧ����
                    qMMultiList.addTextCell(i, routeListBsoIDindex, ((BomChangeNoticeIfc)base).getBsoID());
                    qMMultiList.addTextCell(i, routeListNumberindex, ((BomChangeNoticeIfc)base).getAdoptnoticenumber());
                    qMMultiList.addTextCell(i, routeListNameindex, ((BomChangeNoticeIfc)base).getAdoptnoticename());
                    qMMultiList.addTextCell(i, noticeTypeindex,((BomChangeNoticeIfc)base).getPublishType());
            	}

            }

        }

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
            okbutton.setEnabled(false);
            paginatePanel.clearResultCache();
        }
    }
   /**
     * ��������
     */
    private void processSearchThread()
    {

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //�Ƿ����ԭ�����
        clearResults(true);
        queryresult.clear();
        queryresultMap.clear();
        this.setStatus("�����������ݿ�...");
        try
        {
            //���÷��񷽷���ѯ����
            Class[] paraClass = {HashMap.class};
            Object[] paraObj = {getCondition()};
            queryresult = (Vector)GYNoticeHelper.requestServer("GYBomNoticeService", "searchBomAdoptChangeNotice", paraClass, paraObj);
            setStatus("�ҵ� " + queryresult.size() + " �� ���");
            if(queryresult!=null&&queryresult.size()>0){
            	for(Iterator ite = queryresult.iterator();ite.hasNext();){
            		BaseValueIfc qInfo = (BaseValueIfc)ite.next();
            		queryresultMap.put(qInfo.getBsoID(), qInfo);
            	}
            }
            
        }catch(QMException e)
        {
        	e.printStackTrace();
            String title = "�쳣";
            JOptionPane.showMessageDialog(this, e.getClientMessage(), title, JOptionPane.INFORMATION_MESSAGE);
            setCursor(Cursor.getDefaultCursor());
            if(!isCancelInProgress())
            {
                return;
            }
        }
        setCursor(Cursor.getDefaultCursor());
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
     * ���ð�ť�ı༭״̬�������ڲ�ʹ��
     * @param flag
     */
    private void setButtons(boolean flag)
    {
        searchbutton.setEnabled(flag);
        stopbutton.setEnabled(!flag);

        if(qMMultiList.getSelectedRows() == null)
        {
            okbutton.setEnabled(false);
        }
        cancelbutton.setEnabled(flag);
    }

    /**
     * ����û�¼�����������
     * @return ��������
     */
    private HashMap getCondition()
    {
        HashMap map = new HashMap();
        String routeListNumberStr = "";
        String routeListNameStr = "";
        String routeListStatStr = "";

        //���õ����
        if(routeListNumberField.getText()!=null&&!routeListNumberField.getText().trim().equals("")){
        	routeListNumberStr = routeListNumberField.getText().trim();
        }
        //���õ�����
        if(routeListNameField.getText()!=null&&!routeListNameField.getText().trim().equals("")){
        	routeListNameStr = routeListNameField.getText().trim();
        }
        //��������״̬
        if(noticeTypeBox.getSelectedItem()!=null&&!noticeTypeBox.getSelectedItem().equals("")){
        	routeListStatStr = (String)noticeTypeBox.getSelectedItem().toString();
        }

        
        map.put("routeListNumberStr", routeListNumberStr);
        map.put("routeListNameStr", routeListNameStr);
        map.put("noticeTypeStr", routeListStatStr);


        return map;
    }

    /**
     * ����ȷ����ť��״̬
     */
    private void setOkButtonEnabled()
    {
        if(qMMultiList.getSelectedRows() == null)
        {
            okbutton.setEnabled(false);
        }else
        {
            okbutton.setEnabled(true);
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

}
