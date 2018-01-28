/**
 * SS1 �������QMPartMasterIfc��ΪQMPartIfc�� liunan 2014-5-21
 * SS2 �޸ĳɶ���������Ϊ��ȷ���� liuyuzhu 2016-10-11
 * SS3 �����������Ϻ�  xudezheng 2018-01-16
 * SS4 ���������������ӷ�����Ϣ xudezheng 2018-01-18
 */
 
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;

import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.*;
//CCBegin SS1
import com.faw_qm.version.model.MasteredIfc;
//CCEnd SS1
/**
 * <p>Title:�������֪ͨ��������ȡ�����㲿�� </p> <p>Description: </p> <p>Copyright: Copyright (c) 2007</p> <p>Company: һ������</p>
 * @author ������
 * @version 1.0
 */

public class QQMultPartsSearchJDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
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
    private JButton okJButton = new JButton();
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

    private JCheckBox appendResultJCheckBox = new JCheckBox();
    private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.clients.util.PaginatePanel();
    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea numjTextArea1 = new JTextArea();
//    CCBegin SS4
    JScrollPane jScrollPane2 = new JScrollPane();
    JTextArea numjTextArea2 = new JTextArea();
//    CCEnd SS4

//    CCBegin SS3
    String comp = null;
//    CCEnd SS3
    
    /**
     * ���캯��
     * @param frame ������
     */
    public QQMultPartsSearchJDialog(JFrame frame)
    {
        super(frame);
        try
        {
//        	CCBegin SS3
        	comp = getUserFromCompany();
//			CCEnd SS3
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * ȱʡ�Ĺ��캯��
     */
    public QQMultPartsSearchJDialog()
    {
        this(null);
    }

    /**
     * �����ʼ��
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("��������㲿��");
        this.setSize(1024, 768);
        this.setModal(true);
        panel1.setLayout(gridBagLayout3);
        panel1.setBounds(new Rectangle(0, 0, 1024, 768));
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
        searchJButton.addActionListener(new QQMultPartsSearchJDialog_searchJButton_actionAdapter(this));
        stopJButton.setMaximumSize(new Dimension(75, 23));
        stopJButton.setMinimumSize(new Dimension(75, 23));
        stopJButton.setPreferredSize(new Dimension(75, 23));
        stopJButton.setToolTipText("Stop");
        stopJButton.setMnemonic('S');
        stopJButton.setText("ֹͣ(S)");
        stopJButton.addActionListener(new QQMultPartsSearchJDialog_stopJButton_actionAdapter(this));
        jPanel3.setLayout(gridBagLayout2);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
        okJButton.setEnabled(false);
        okJButton.addActionListener(new QQMultPartsSearchJDialog_okJButton_actionAdapter(this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        cancelJButton.addActionListener(new QQMultPartsSearchJDialog_cancelJButton_actionAdapter(this));
        qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
        qMStatus.setMaximumSize(new Dimension(4, 22));
        qMStatus.setMinimumSize(new Dimension(4, 22));
        qMStatus.setPreferredSize(new Dimension(4, 22));
        appendResultJCheckBox.setText("���ӽ��");
        jLabel1.setRequestFocusEnabled(true);
        jLabel1.setText("��һ�������һ�У�");
        numjTextArea1.setEditable(true);
        jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
        panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
        panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 10, 10), 0, 0));
        jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 6, 0, 0), 0, 0));
        jPanel3.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 0), 0, 28));
        panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(22, 20, 0, 10), 10, 0));
        jTabbedPane1.add(jPanel1, "���������㲿��");
        getContentPane().add(panel1, null);
        jPanel1.add(numLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 7), 25, 22));
        jPanel1.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 10));
        jPanel1.add(jScrollPane1, new GridBagConstraints(1, 0, 5, 5, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.WEST, new Insets(1, 0, 6, 0),200, 256));
        jScrollPane1.getViewport().add(numjTextArea1, 0);
//        CCBegin SS4
        jPanel1.add(jScrollPane2, new GridBagConstraints(8, 0, 5, 5, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.EAST, new Insets(2, 1, 7, 1),500, 256));
//        jPanel1.add(jScrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 7), 25, 22));
        jScrollPane2.getViewport().add(numjTextArea2, 1);
        numjTextArea2.setEditable(false);
        numjTextArea2.setLineWrap(true);
        numjTextArea2.setText("��ѯ�����־������������");
//        CCEnd SS4
        panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(appendResultJCheckBox, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
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
//        CCBegin SS3
        if(comp.equals("cd")){
        	String[] headings = {"id", "���", "����","�汾","���⴦��"};
        	qMMultiList.setHeadings(headings);
        	qMMultiList.setRelColWidth(new int[]{0,1,1,1,1});
        	}else{
        		String[] headings = {"id", "���", "����"};
        		qMMultiList.setHeadings(headings);
        		qMMultiList.setRelColWidth(new int[]{0, 1, 1});
        	}
//        CCEnd SS3
        qMMultiList.setMultipleMode(true);
        qMMultiList.addActionListener(new QQMultPartsSearchJDialog_qMMultiList_actionAdapter(this));
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
    
//    CCBegin SS3
    private Vector<String> getConditionForCD()
    {
        int rows = numjTextArea1.getLineCount();
        Vector<String> objs = new Vector<String>();
        String text = numjTextArea1.getText().trim();
        text = text.replaceAll("\n", ";");
        StringTokenizer parts = new StringTokenizer(text, ";");
        int i = 0;
        while(i < rows && parts.hasMoreTokens())
        {
            objs.add(parts.nextToken().toString().trim());
            i++;
        }

        return objs;
    }
//    CCEnd SS3
    

    /**
     * ����ҵ��������Ժ�����ֵ�������ѯ������Ȼ��ӳ־û������в�ѯ�����ݡ�
     * @param flag ��flag=true������ԭ�������������ϴβ�ѯ���
     */
    private void processSearchThread(boolean flag)
    {
        if(verbose)
        {
            System.out.println(" processSearchThread()...begin");
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //�Ƿ����ԭ�����
        clearResults(!flag);

        if(!flag)
        {
            paginatePanel.clearResultCache();
            resultVector.clear();
        }
        this.setStatus("�����������ݿ�...");
        //��ò�ѯ����
        Object[] condition = getCondition();
        try
        {
            //���÷��񷽷���ѯ����
            Class[] paraClass = {Object[].class};
            Object[] paraObj = {condition};
            //CCBegin SS2
//            Vector queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findMultPartsByNumbers", paraClass, paraObj);
            Vector queryresult = null;
//            CCBegin SS3
//            String usercp = getUserFromCompany();
//            CCEnd SS3
            if(comp.equals("cd")){
            	//            	CCBegin SS3
            	Vector<String> conditionFromCD = getConditionForCD();

            	RequestServer server = RequestServerFactory.getRequestServer();
            	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
            	info1.setClassName("com.faw_qm.gybom.util.GYBomTool");
    			info1.setMethodName("getPartByMtlVector");
            	Class[] cdParaClass = {Vector.class};
            	Object[] cdParaObj = {conditionFromCD};
            	info1.setParaClasses(cdParaClass);
            	info1.setParaValues(cdParaObj);
            	queryresult = (Vector)server.request(info1);
            	this.postHandleResultForCD(queryresult);
            	
            }else{
            	queryresult = (Vector)RequestHelper.request("consTechnicsRouteService", "findMultPartsByNumbers", paraClass, paraObj);
            //CCEnd SS2
            this.postHandleResult(queryresult);
            }//           CCEnd SS3
        }catch(Exception e)
        {
            e.printStackTrace();
            String message = e.getMessage();
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
    //CCBegin SS2
    /**
     * �ж��û�������˾
     * @return String ����û�������˾
     * @author wenl
     */
    public String getUserFromCompany() throws QMException
    {
    	RequestServer server = RequestServerFactory.getRequestServer();;
    	String returnStr = "";
         StaticMethodRequestInfo info = new StaticMethodRequestInfo();
         info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
         info.setMethodName("getUserFromCompany");
         Class[] paraClass ={};
         info.setParaClasses(paraClass);
         Object[] obj ={};
         info.setParaValues(obj);
         boolean flag = false;
         try
         {
        	 returnStr = ((String) server.request(info));
         }
         catch (QMRemoteException e)
         {
               throw new QMException(e);
         }
         return returnStr;
    }
    //CCEnd SS2

    /**
     * ���������,����������������б���
     * @param searchResult �������(���)
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
            setStatus("�ҵ� 0 ���㲿��");
            return;
        }
        //����б��е��������
        /*if(verbose)
        {
            System.out.println("the result size is:::" + searchResult.size());
        }*/
        int oldSize = resultVector.size();
        for(int i = 0;i < searchResult.size();i++)
        {
            if(isCancelInProgress())
            {
                break;
            }
            /*if(verbose)
            {
                System.out.println("add the object to MultiList start......");
            }*/
            QMPartMasterInfo orderlist = (QMPartMasterInfo)searchResult.elementAt(i);

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
            setStatus("�ҵ� " + resultVector.size() + " �����");
        }else
        {
            setStatus("�����ҵ� " + (resultVector.size() - oldSize) + " �����");
        }

        if(verbose)
        {
            System.out.println("postHandleResult() end");
        }
    }

//    CCBegin SS3
    private void postHandleResultForCD(Vector searchResult)
    {
    	if(verbose)
    	{
    		System.out.println(" postHandleResult() begin...");
    	}
    	String message = "�ҵ� 0 ���㲿��"; 
    	StringBuffer nopart = new StringBuffer();
    	StringBuffer noversion = new StringBuffer();
    	boolean flag = false;
    	for(int i =0 ;i<searchResult.size();i++){
    		HashMap map = (HashMap)searchResult.get(i);
    		
    		if(map.get("info")==null){
    			if(!map.get("nopart").equals("")){
    				nopart.append(map.get("nopart"));
    			}else if(!map.get("noversion").equals("")){
    				noversion.append(map.get("noversion"));
    			}
    			flag = true;
    			searchResult.remove(i);
    			i--;
    			continue;
    		}
    	}
    	if(flag){
    		if(nopart!=null)
    			message = nopart.toString()+","+noversion.toString();
    		else
    			message = noversion.toString();
    		setStatus("δ�ҵ������㲿����"+message);
    		numjTextArea2.setText("δ�ҵ������㲿����"+message);
    		numjTextArea2.setLineWrap(true);
    		numjTextArea2.setWrapStyleWord(true);
    	}else
    		setStatus(message);
    	if(searchResult.size()==0){
    		return;
    	}

    	//����б��е��������
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
    		/*if(verbose)
            {
                System.out.println("add the object to MultiList start......");
            }*/
    		HashMap result = (HashMap)searchResult.elementAt(i);
    		QMPartInfo partInfo = (QMPartInfo)result.get("info");
    		if(result.get("info")==null){continue;}
    		//�ж��б����Ƿ��Ѵ��ڴ˼�¼�������ڣ����ý���������������б���
    		boolean flag1 = false;
    		if(resultVector.size() != 0)
    		{
    			Object[] ids = resultVector.keySet().toArray();
    			for(int k = 0;k < resultVector.size();k++)
    			{
    				if(partInfo.getBsoID().equals(ids[k]))
    				{
    					flag1 = true;
    					break;
    				}
    			}
    		}
    		if(flag1 == true)
    		{
    			continue;
    		}

    		resultVector.put(partInfo.getBsoID(), result);

    	}

    	if(!appendResultJCheckBox.isSelected())
    	{
    		if(flag)
    			setStatus("�ҵ� " + resultVector.size() + " ���������"+message+"δ�ҵ���");
    		else
    			setStatus("�ҵ� " + resultVector.size() + " �����");
    	}else
    	{
    		setStatus("�����ҵ� " + (resultVector.size() - oldSize) + " �����");
    	}

    	if(verbose)
    	{
    		System.out.println("postHandleResult() end");
    	}
    }
//    CCEnd SS3
    
    private synchronized void addResultToMultiList()
    {
        Vector newV = new Vector();
        newV.addAll(resultVector.values());
        //��ҳ
        Vector firstPageVector = paginatePanel.paginate(newV);
        addObjectToMultiList(firstPageVector);
    }
    
//    CCBegin SS3
    private synchronized void addResultToMultiListForCD()
    {
        Vector newV = new Vector();
        newV.addAll(resultVector.values());
        //��ҳ
        Vector firstPageVector = paginatePanel.paginate(newV);
        addObjectToMultiListForCD(firstPageVector);
    }
//    CCEnd SS3

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
            QMPartMasterInfo qmpart = (QMPartMasterInfo)objs.elementAt(i);
            Image image = this.getStandardIcon(qmpart);
            qMMultiList.addTextCell(i, 0, qmpart.getBsoID());
            qMMultiList.addCell(i, 1, qmpart.getPartNumber(), image);
            qMMultiList.addTextCell(i, 2, qmpart.getPartName());
        }
        setCursor(Cursor.getDefaultCursor());
    }
    
//    CCBegin SS3
    private void addObjectToMultiListForCD(Vector objs)
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
        	HashMap result = (HashMap)objs.elementAt(i);
        	QMPartInfo qmpart = (QMPartInfo)result.get("info");
            Image image = this.getStandardIcon(qmpart);
            qMMultiList.addTextCell(i, 0, qmpart.getBsoID());
            qMMultiList.addCell(i, 1, qmpart.getPartNumber(), image);
            qMMultiList.addTextCell(i, 2, qmpart.getPartName());
            qMMultiList.addTextCell(i, 3, (String)result.get("version"));
            qMMultiList.addTextCell(i, 4, (String)result.get("bs"));
        }
        setCursor(Cursor.getDefaultCursor());
    }
//    CCEnd SS3

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
        searchThread = new SearchThread(theThreadGroup, this, appendResultJCheckBox.isSelected());
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
     * ���ȷ����ť�󣬽�ѡ�е��㲿����ӵ��б���
     */
    private void processOKCommond()
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        this.dispose();
        //����QMPartMasterIfc[]
//        CCBegin SS3
        if(comp.equals("cd")){
        	
        	HashMap<String,Object> map = new HashMap<String,Object>();
        	map = this.getSelectedDetailsForCD();
        	RefreshService.getRefreshService().dispatchRefresh("addSelectedPartsForCD", 0, map);
        }else{
        	QMPartMasterInfo[] objs = (QMPartMasterInfo[])this.getSelectedDetails();
        	for(int i = 0;i < objs.length;i++)
        	{
        		QMPartMasterInfo masterinfo = (QMPartMasterInfo)objs[i];
        		Vector v = new Vector();
        		//CCBegin SS1
        		//v.add(masterinfo);
        		Class[] c = { MasteredIfc.class };
        		Object[] o = { masterinfo };
        		try
        		{
        			Vector nv = (Vector) RequestHelper.request("VersionControlService", "allIterationsOf", c, o);
        			QMPartIfc part = (QMPartIfc) nv.get(0);
        			v.add(part);
        		}
        		catch (Exception ex)
        		{
        			String message = ex.getMessage();
        			DialogFactory.showInformDialog(this, message);
        		}
        		//CCEnd SS3
        		RefreshService.getRefreshService().dispatchRefresh("addSelectedParts", 0, v);
        	}
        }

        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * ��ý����ѡ�е�ҵ�����
     * @return ѡ�е�ҵ�����
     */
    public QMPartMasterInfo[] getSelectedDetails()
    {
        int[] rows = qMMultiList.getSelectedRows();
        QMPartMasterInfo[] values = new QMPartMasterInfo[rows.length];
        for(int i = 0;i < rows.length;i++)
        {
            int xx = rows[i];
            String bsoid = qMMultiList.getCellText(xx, 0);
            values[i] = (QMPartMasterInfo)resultVector.get(bsoid);
            if(verbose)
            {
                System.out.println("ѡ�е��ǣ�" + values[i]);
            }
        }
        return values;
    }
    
//    CCBegin SS3
    public HashMap<String,Object> getSelectedDetailsForCD()
    {
        /*int[] rows = qMMultiList.getSelectedRows();
        Vector<QMPartInfo> values = new Vector<QMPartInfo>();
        for(int i = 0;i < rows.length;i++)
        {
            int xx = rows[i];
            String bsoid = qMMultiList.getCellText(xx, 0);
            HashMap map = (HashMap)resultVector.get(bsoid);
        }*/
        return resultVector;
    }
//    CCEnd SS3

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

        /** ����������� */
        private QQMultPartsSearchJDialog myDialog;

        /**
         * �����ѯʵ��
         * @param threadgroup �߳���
         * @param dialog ����װ�����������
         * @param flag �ж��Ƿ���ԭ�����������
         */
        public SearchThread(ThreadGroup threadgroup, QQMultPartsSearchJDialog dialog, boolean flag)
        {
            super(threadgroup);
            myDialog = dialog;
            isLeave = flag;
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
//            	CCBegin SS4
            	if(comp.equals("cd")) 
            		numjTextArea2.setText("��ѯ�����־������������");
//            	 CCEnd SS4
                //ִ������
                myDialog.processSearchThread(isLeave);
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
//                CCBegin SS3
                if(comp.equals("cd")) {
                	myDialog.addResultToMultiListForCD();
                }else {
                	//                CCEnd SS3
                	myDialog.addResultToMultiList();
                }
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

class QQMultPartsSearchJDialog_searchJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQMultPartsSearchJDialog adaptee;

    QQMultPartsSearchJDialog_searchJButton_actionAdapter(QQMultPartsSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.searchJButton_actionPerformed(e);
    }
}

class QQMultPartsSearchJDialog_stopJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQMultPartsSearchJDialog adaptee;

    QQMultPartsSearchJDialog_stopJButton_actionAdapter(QQMultPartsSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.stopJButton_actionPerformed(e);
    }
}

class QQMultPartsSearchJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQMultPartsSearchJDialog adaptee;

    QQMultPartsSearchJDialog_okJButton_actionAdapter(QQMultPartsSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okJButton_actionPerformed(e);
    }
}

class QQMultPartsSearchJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private QQMultPartsSearchJDialog adaptee;

    QQMultPartsSearchJDialog_cancelJButton_actionAdapter(QQMultPartsSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

class QQMultPartsSearchJDialog_qMMultiList_actionAdapter implements java.awt.event.ActionListener
{
    private QQMultPartsSearchJDialog adaptee;

    QQMultPartsSearchJDialog_qMMultiList_actionAdapter(QQMultPartsSearchJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.qMMultiList_actionPerformed(e);
    }
}
