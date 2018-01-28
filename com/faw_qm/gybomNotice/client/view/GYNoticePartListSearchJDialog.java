/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �޸�TD8290 ���� 2014-7-17
 * SS2 �޸�TD8323 ���� 2014-7-17
 */
package com.faw_qm.gybomNotice.client.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;

import com.faw_qm.clients.util.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.gybomNotice.util.GYNoticeHelper;
import com.faw_qm.part.model.*;
/**
 * <p>Title: ����BOM���������������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */
public class GYNoticePartListSearchJDialog extends JDialog{
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

	  private JPanel remarkJPanel = new JPanel();
	  //CCBegin SS1
	  private JFrame parentFrame = null;
	  //CCEnd SS1

	  /**���ڱ����Դ��Ϣ*/
	  protected static ResourceBundle resource = null;

	  /**��ֹ���̱�־*/
	  protected boolean cancelInProgress = false;

	  /**�߳���*/
	  private static ThreadGroup theThreadGroup = Thread.currentThread().
	      getThreadGroup();

	  /**����������*/
	  private SearchThread searchThread;

	  private HashMap resultVector = new HashMap();
	  private ArrayList resultArrayList =new ArrayList();

	  private JCheckBox appendResultJCheckBox = new JCheckBox();
	  private com.faw_qm.clients.util.PaginatePanel paginatePanel = new com.faw_qm.
	      clients.util.PaginatePanel();
	  JLabel jLabel1 = new JLabel();
	  JScrollPane jScrollPane1 = new JScrollPane();
	  JTextArea numjTextArea1 = new JTextArea();
	  //CCBegin��SS1
	  QMPartIfc topPart = null;
	  //CCEnd SS1
	  public JPanel ZCAdoptNoticeAdjustPanel;
	  String type = "";
	  /**
	   * ���캯��
	   * @param frame ������
	   */
	  public GYNoticePartListSearchJDialog(JFrame frame,JPanel listpanel,String type) {
	    super(frame, "", true);
	    //CCBegin SS1
	    this.parentFrame = frame;
	    this.ZCAdoptNoticeAdjustPanel = (ZCAdoptNoticeAdjustPanel)listpanel;
	    this.type = type;

        JPanel parentPanel = ((GYBomNoticeMainJFrame) parentFrame).getTaskPanel();
        if(parentPanel instanceof CJAdoptNoticeViewPanel){
        	topPart = ((CJAdoptNoticeViewPanel)parentPanel).cxPart;
        }else if(parentPanel instanceof ZCBomAdoptNoticeViewPanel){
        	topPart = ((ZCBomAdoptNoticeViewPanel)parentPanel).cxPart;
        }
        //CCEnd SS1
        
	    try {
	      jbInit();
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }

	  /**
	   * ȱʡ�Ĺ��캯��
	   */
	  public GYNoticePartListSearchJDialog() {
	  }

	  /**
	   * �����ʼ��
	   * @throws Exception
	   */
	  private void jbInit() throws Exception {
	    this.setTitle("���������㲿��");
	    this.setSize(650, 500);
	    panel1.setLayout(gridBagLayout3);
	    panel1.setBounds(new Rectangle(0, 0, 650, 500));
	    numLabel.setMaximumSize(new Dimension(200, 22));
	    numLabel.setMinimumSize(new Dimension(200, 22));
	    numLabel.setPreferredSize(new Dimension(200, 22));
	    numLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	    numLabel.setText("������    ��һ�������һ�У�");
	    jPanel1.setLayout(gridBagLayout1);
	    remarkJPanel.setLayout(gridBagLayout1);
	    jPanel2.setMaximumSize(new Dimension(70, 70));
	    jPanel2.setMinimumSize(new Dimension(70, 70));
	    jPanel2.setPreferredSize(new Dimension(70, 70));
	    jPanel2.setLayout(gridBagLayout4);
	    searchJButton.setMaximumSize(new Dimension(65, 23));
	    searchJButton.setMinimumSize(new Dimension(65, 23));
	    searchJButton.setPreferredSize(new Dimension(65, 23));
	    searchJButton.setToolTipText("Search");
	    searchJButton.setText("����");
	    searchJButton.addActionListener(new NoticeMultPartsSearchJDialog_searchJButton_actionAdapter(this));
	    stopJButton.setMaximumSize(new Dimension(65, 23));
	    stopJButton.setMinimumSize(new Dimension(65, 23));
	    stopJButton.setPreferredSize(new Dimension(65, 23));
	    stopJButton.setToolTipText("Stop");
	    stopJButton.setText("ֹͣ");
	    stopJButton.addActionListener(new
	                                  NoticeMultPartsSearchJDialog_stopJButton_actionAdapter(this));
	    jPanel3.setLayout(gridBagLayout2);
	    okJButton.setMaximumSize(new Dimension(75, 23));
	    okJButton.setMinimumSize(new Dimension(75, 23));
	    okJButton.setPreferredSize(new Dimension(75, 23));
	    okJButton.setToolTipText("Ok");
	    okJButton.setMnemonic('Y');
	    okJButton.setText("ȷ��(Y)");
	    okJButton.setEnabled(false);
	    okJButton.addActionListener(new
	                                NoticeMultPartsSearchJDialog_okJButton_actionAdapter(this));
	    cancelJButton.setMaximumSize(new Dimension(75, 23));
	    cancelJButton.setMinimumSize(new Dimension(75, 23));
	    cancelJButton.setPreferredSize(new Dimension(75, 23));
	    cancelJButton.setToolTipText("Cancel");
	    cancelJButton.setMnemonic('C');
	    cancelJButton.setText("ȡ��(C)");
	    cancelJButton.addActionListener(new
	                                    NoticeMultPartsSearchJDialog_cancelJButton_actionAdapter(this));
	    qMStatus.setBorder(BorderFactory.createLoweredBevelBorder());
	    qMStatus.setMaximumSize(new Dimension(4, 22));
	    qMStatus.setMinimumSize(new Dimension(4, 22));
	    qMStatus.setPreferredSize(new Dimension(4, 22));
	    appendResultJCheckBox.setText("���ӽ��");
//	    jLabel1.setRequestFocusEnabled(true);
//	    jLabel1.setText("��һ�������һ�У�");
	    numjTextArea1.setEditable(true);
//	    numjTextArea1.setMaximumSize(new Dimension(50, 50));
//	    numjTextArea1.setMinimumSize(new Dimension(50, 50));
//	    numjTextArea1.setPreferredSize(new Dimension(50, 50));
	    jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	        , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
	        new Insets(0, 0, 0, 0), 0, 0));
	    jPanel2.add(stopJButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
	        , GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
	        new Insets(10, 0, 0, 0), 0, 0));
	    panel1.add(qMMultiList, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,new Insets(0, 10, 0, 10), 0, 0));
	    panel1.add(jPanel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0 , GridBagConstraints.EAST,GridBagConstraints.HORIZONTAL,new Insets(10, 0, 10, 10), 0, 0));
	    jPanel3.add(paginatePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,new Insets(0, 6, 0, 0), 0, 0));
	    jPanel3.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,GridBagConstraints.NONE,new Insets(0, 0, 0, 0), 0, 0));
	    jPanel3.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,new Insets(0, 8, 0, 0), 0, 0));
	    panel1.add(jTabbedPane1,   new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 10), 0, 28));
	    panel1.add(jPanel2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0 , GridBagConstraints.NORTHEAST,GridBagConstraints.NONE,new Insets(22, 20, 0, 10), 0, 0));
	    jTabbedPane1.add(jPanel1, "���������㲿��");
	    getContentPane().add(panel1, null);
	    
	    jPanel1.add(numLabel,new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 2, 8), 0, 0));
	  //  jPanel1.add(jLabel1,new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 4, 0), 0, 10));
	    jScrollPane1.getViewport().add(numjTextArea1, null);
	    jPanel1.add(jScrollPane1,new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 8, 2, 8), 60, 90));

	    panel1.add(qMStatus, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
	                                                , GridBagConstraints.WEST,
	                                                GridBagConstraints.HORIZONTAL,
	                                                new Insets(0, 0, 0, 0), 0, 0));
	    panel1.add(appendResultJCheckBox,
	               new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
	                                      , GridBagConstraints.WEST,
	                                      GridBagConstraints.NONE,
	                                      new Insets(0, 10, 0, 0), 0, 0));
	    localize();

	    paginatePanel.addListener(new com.faw_qm.clients.util.PaginateListener() {
	      public void paginateEvent(com.faw_qm.clients.util.PaginateEvent e) {
	        paginatePanel_paginateEvent(e);
	      }
	    });
	  }

	  /**
	   * ���ػ�
	   */
	  private void localize() {
	    qMMultiList.setCellEditable(false);
	    qMMultiList.addItemListener(new ItemListener() {
	      public void itemStateChanged(ItemEvent e) {
	        setOkButtonEnabled();
	      }
	    });
	    String[] headings = {
	        "id", "���", "����","�汾"};
	    qMMultiList.setHeadings(headings);
	    qMMultiList.setRelColWidth(new int[] {0, 1, 1,1});
	    qMMultiList.setMultipleMode(true);
	    qMMultiList.addActionListener(new
	                                  NoticeMultPartsSearchJDialog_qMMultiList_actionAdapter(this));
	    qMMultiList.setEnabled(false);

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
	    this.setLocation( (screenSize.width - frameSize.width) / 2,
	                     (screenSize.height - frameSize.height) / 2);

	  }

	  /**
	   * ���ظ��෽����ʹ������ʾ����Ļ����
	   * @param flag
	   */
	  public void setVisible(boolean flag) {
	    this.setViewLocation();
	    super.setVisible(flag);
	  }

	  /**
	   * ���ð�ť�ı༭״̬�������ڲ�ʹ��
	   * @param flag
	   */
	  private void setButtons(boolean flag) {
	    searchJButton.setEnabled(flag);
	    stopJButton.setEnabled(!flag);

	    if (qMMultiList.getSelectedRows() == null) {
	      okJButton.setEnabled(false);
	    }
	    cancelJButton.setEnabled(flag);
	  }

	  /**
	   * ����ȷ����ť��״̬
	   */
	  private void setOkButtonEnabled() {
	    if (qMMultiList.getSelectedRows() == null) {
	      okJButton.setEnabled(false);
	    }
	    else {
	      okJButton.setEnabled(true);
	    }
	  }

	  /**
	   * ����״̬����Ϣ
	   * @param text ״̬��Ҫ��ʾ���ı�
	   */
	  private void setStatus(String text) {
	    qMStatus.setText(text);
	  }

	  /**
	   * ��ս����
	   * @param flag ���flag = true,����գ�������
	   */
	  private synchronized void clearResults(boolean flag) {
	    if (flag) {
	      qMMultiList.clear();
	      //CCBegin SS2
	      resultArrayList.clear();
	      //CCEnd SS2
	      okJButton.setEnabled(false);
	    }
	    setStatus(" ");
	  }

	  /**
	   * �ж��Ƿ���ֹ�����߳�
	   * @return �������True������ֹ�̡߳�
	   */
	  private boolean isCancelInProgress() {
	    return cancelInProgress;
	  }

	  /**
	   * �����Ƿ���ֹ�߳�
	   * @param flag  ���flag=True������ֹ�̡߳�
	   */
	  private synchronized void setCancelInProgress(boolean flag) {
	    cancelInProgress = flag;
	  }

	  /**
	   * ��ֹ��������
	   */
	  private synchronized void cancel() {
	    if (searchThread != null && searchThread.isAlive()) {
	      setCancelInProgress(true);
	      //searchThread.interrupt(); //�����ȥ��������Ϊ��ʹ�����˳��ˣ��ⲻ��ҵ��
	      searchThread = null;
	    }
	  }

	  /**
	   * ����û�¼�����������
	   * @return ��������
	   */
	  private Object[] getCondition() {
	    int rows=numjTextArea1.getLineCount();
	    Object[] objs = new Object[rows];
	    String text = numjTextArea1.getText().trim();
	    text=text.replaceAll("\n",";");
	    StringTokenizer parts=new StringTokenizer(text,";");
	    int i=0;
	    while(i<rows&&parts.hasMoreTokens()){
	      objs[i]=parts.nextToken().toString().trim();
	      i++;
	    }

	    return objs;
	  }

	  /**
	   * ����ҵ��������Ժ�����ֵ�������ѯ������Ȼ��ӳ־û������в�ѯ�����ݡ�
	   * @param flag ��flag=true������ԭ�������������ϴβ�ѯ���
	   */
	  private void processSearchThread(boolean flag) {

	    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    //�Ƿ����ԭ�����
	    clearResults(!flag);

	    if (!flag) {
	      resultVector.clear();
	    }
	    this.setStatus("�����������ݿ�...");
	    //��ò�ѯ����
	    Object[] condition = getCondition();
	    try {
	      //���÷��񷽷���ѯ����
	      Class[] paraClass = {
	          Object[].class};
	      Object[] paraObj = {
	          condition};
	      Vector queryresult = (Vector) GYNoticeHelper.requestServer("GYBomNoticeService", "findMultPartsByNumbers", paraClass, paraObj);
	      this.postHandleResult(queryresult);
	    }
	    catch (QMRemoteException e) {
	      String title = "��Ϣ";
	      JOptionPane.showMessageDialog(this, e.getClientMessage(), title,
	                                    JOptionPane.INFORMATION_MESSAGE);
	      setCursor(Cursor.getDefaultCursor());
	      if (!isCancelInProgress()) {
	        return;
	      }
	    }
	    catch(Exception ee){
	      ee.printStackTrace();
	    }
	    setCursor(Cursor.getDefaultCursor());

	  }

	  /**
	   * ���������,����������������б���
	   * @param searchResult �������(���)
	   * @throws QMRemoteException
	   */
	  private void postHandleResult(Vector searchResult) throws QMRemoteException {

	    if (searchResult == null || searchResult.size() == 0) {
	      setStatus("�ҵ� 0 ���㲿��");
	      return;
	    }
	    //����б��е��������

	    int oldSize = resultVector.size();
	    for (int i = 0; i < searchResult.size(); i++) {
	      if (isCancelInProgress()) {
	        break;
	      }

	      QMPartInfo orderlist = (QMPartInfo) searchResult.get(i);

	      //�ж��б����Ƿ��Ѵ��ڴ˼�¼�������ڣ����ý���������������б���
	      boolean flag = false;
	      if (resultVector.size() != 0) {
	        Object[] ids = resultVector.keySet().toArray();
	        for (int k = 0; k < resultVector.size(); k++) {
	          if (orderlist.getBsoID().equals(ids[k])) {
	            flag = true;
	            break;
	          }
	        }
	      }
	      if (flag == true) {
	        continue;
	      }

	      resultVector.put(orderlist.getBsoID(), orderlist);
	      resultArrayList.add(orderlist);
	    }

	    if (!appendResultJCheckBox.isSelected()) {
	      setStatus("�ҵ� " + resultVector.size() + " �����");
	    }
	    else {
	      setStatus("�����ҵ� " + (resultVector.size() - oldSize) + " �����");
	    }


	  }

	  private synchronized void addResultToMultiList() {
	    //Vector newV = new Vector();
	    //newV.addAll(resultArrayList);
	    //ArrayList firstPageVector = paginatePanel.paginate(newV);
	    addObjectToMultiList(resultArrayList);
	  }

	  /**
	   * ��������ӵ��б���
	   * @param objs Vector
	   */
	  private void addObjectToMultiList(ArrayList objs) {

	    qMMultiList.clear();
	    if (objs == null || objs.size() == 0) {
	      return;
	    }
	    //����������������б���
	    int p = objs.size();
	    for (int i = 0; i < p; i++) {
	      QMPartInfo qmpart = (QMPartInfo) objs.get(i);
	      qMMultiList.addTextCell(i, 0, qmpart.getBsoID());
	      qMMultiList.addTextCell(i, 1, qmpart.getPartNumber());
	      qMMultiList.addTextCell(i, 2, qmpart.getPartName());
	      qMMultiList.addTextCell(i, 3, qmpart.getVersionValue());
	    }
	    setCursor(Cursor.getDefaultCursor());
	  }

	  /**
	   * ִ����������
	   * @param e ActionEvent
	   */
	  void searchJButton_actionPerformed(ActionEvent e) {
	    setButtons(false);
	    //���������߳�
	    searchThread = new SearchThread(theThreadGroup, this,appendResultJCheckBox.isSelected());
	    searchThread.start();

	  }

	  /**
	   * ִ�С���ֹ������
	   * @param e ActionEvent
	   */
	  void stopJButton_actionPerformed(ActionEvent e) {
	    stopJButton.setEnabled(false);
	    cancel();
	  }

	  /**
	   * ִ�С�ȷ��������
	   * @param e  ActionEvent
	   */
	  void okJButton_actionPerformed(ActionEvent e) {
	    processOKCommond();
	  }

	  /**
	   * ���ȷ����ť�󣬽�ѡ�е��㲿�����ӵ��б���
	   */
	  private void processOKCommond() {
	    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    this.dispose();
	 //CCBegin SS1
	    QMPartInfo[] objs = (QMPartInfo[])this.getSelectedDetails();
	      Class[] paraClass = {QMPartInfo[].class,QMPartIfc.class};
		  Object[] paraObj = {objs,topPart};
	    try {
			Vector queryresult = (Vector) GYNoticeHelper.requestServer("GYBomNoticeService", "findPartAndRoute", paraClass, paraObj);
		    ((ZCAdoptNoticeAdjustPanel)ZCAdoptNoticeAdjustPanel).setMultiListData(type,queryresult);
	    } catch (QMException e) {
			e.printStackTrace();
		}
	    
    //CCEnd SS1
	    setCursor(Cursor.getDefaultCursor());
	  }

	  /**
	   * ��ý����ѡ�е�ҵ�����
	   * @return ѡ�е�ҵ�����
	   */
	  public QMPartInfo[] getSelectedDetails() {
	    int[] rows = qMMultiList.getSelectedRows();
	    QMPartInfo[] values = new QMPartInfo[rows.length];
	    for (int i = 0; i < rows.length; i++) {
	      int xx = rows[i];
	      String bsoid = qMMultiList.getCellText(xx, 0);
	      values[i] = (QMPartInfo) resultVector.get(bsoid);

	    }
	    return values;
	  }

	  /**
	   * ִ�С�ȡ��������
	   * @param e ActionEvent
	   */
	  void cancelJButton_actionPerformed(ActionEvent e) {
	    this.dispose();
	  }

	  /**
	   * <p>Title: �ڲ������߳�</p>
	   * <p>Description: </p>
	   * <p>Copyright: Copyright (c) 2007</p>
	   * <p>Company: һ������</p>
	   * @author
	   * @version 1.0
	   */
	  class SearchThread
	      extends QMThread {
	    /**�ж��Ƿ���ԭ�����������*/
	    private boolean isLeave;

	    /**�����������*/
	    private GYNoticePartListSearchJDialog myDialog;

	    /**
	     * �����ѯʵ��
	     * @param threadgroup �߳���
	     * @param dialog ����װ�����������
	     * @param flag �ж��Ƿ���ԭ�����������
	     */
	    public SearchThread(ThreadGroup threadgroup,
	    		GYNoticePartListSearchJDialog dialog, boolean flag) {
	      super(threadgroup);
	      myDialog = dialog;
	      isLeave = flag;
	    }

	    /**
	     * ʹ�߳��жϣ�ֹͣ��������
	     */
	    public synchronized void interrupt() {

	      this.interrupt();
	    }

	    /**
	     * �߳����з�����ִ��������
	     */
	    public synchronized void run() {

	      try {
	        //ִ������
	        myDialog.processSearchThread(isLeave);
	      }
	      catch (Throwable throwable) {
	        if (!myDialog.isCancelInProgress()) {
	          throwable.printStackTrace();
	        }
	      }
	      finally {

	        //���ý��水ť����ʾ״̬
	        myDialog.setButtons(true);
	        //����û�д����ж�״̬
	        myDialog.setCancelInProgress(false);
	        myDialog.addResultToMultiList();
	      }


	    }
	  }

	  void qMMultiList_actionPerformed(ActionEvent e) {
	    processOKCommond();
	  }

	  void paginatePanel_paginateEvent(com.faw_qm.clients.util.PaginateEvent e) {
	    //this.addObjectToMultiList(paginatePanel.getCurrentObjects());
	  }

	}

	class NoticeMultPartsSearchJDialog_searchJButton_actionAdapter
	    implements java.awt.event.ActionListener {
	  private GYNoticePartListSearchJDialog adaptee;

	  NoticeMultPartsSearchJDialog_searchJButton_actionAdapter(
			  GYNoticePartListSearchJDialog adaptee) {
	    this.adaptee = adaptee;
	  }

	  public void actionPerformed(ActionEvent e) {
	    adaptee.searchJButton_actionPerformed(e);
	  }
	}

	class NoticeMultPartsSearchJDialog_stopJButton_actionAdapter
	    implements java.awt.event.ActionListener {
	  private GYNoticePartListSearchJDialog adaptee;

	  NoticeMultPartsSearchJDialog_stopJButton_actionAdapter(GYNoticePartListSearchJDialog
	      adaptee) {
	    this.adaptee = adaptee;
	  }

	  public void actionPerformed(ActionEvent e) {
	    adaptee.stopJButton_actionPerformed(e);
	  }
	}

	class NoticeMultPartsSearchJDialog_okJButton_actionAdapter
	    implements java.awt.event.ActionListener {
	  private GYNoticePartListSearchJDialog adaptee;

	  NoticeMultPartsSearchJDialog_okJButton_actionAdapter(GYNoticePartListSearchJDialog
	      adaptee) {
	    this.adaptee = adaptee;
	  }

	  public void actionPerformed(ActionEvent e) {
	    adaptee.okJButton_actionPerformed(e);
	  }
	}

	class NoticeMultPartsSearchJDialog_cancelJButton_actionAdapter
	    implements java.awt.event.ActionListener {
	  private GYNoticePartListSearchJDialog adaptee;

	  NoticeMultPartsSearchJDialog_cancelJButton_actionAdapter(
			  GYNoticePartListSearchJDialog adaptee) {
	    this.adaptee = adaptee;
	  }

	  public void actionPerformed(ActionEvent e) {
	    adaptee.cancelJButton_actionPerformed(e);
	  }
	}

	class NoticeMultPartsSearchJDialog_qMMultiList_actionAdapter
	    implements java.awt.event.ActionListener {
	  private GYNoticePartListSearchJDialog adaptee;

	  NoticeMultPartsSearchJDialog_qMMultiList_actionAdapter(GYNoticePartListSearchJDialog
	      adaptee) {
	    this.adaptee = adaptee;
	  }

	  public void actionPerformed(ActionEvent e) {
	    adaptee.qMMultiList_actionPerformed(e);
	  }
}