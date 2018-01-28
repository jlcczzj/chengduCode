/** ���ɳ���ProcedureUsageDrawingPanel.java      1.1  2006/3/7
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/05 ��־��    ԭ���Ż����򡢹����鿴�����ٲ�ѯ���ݿ������
 *                         ����������ˢ��ͼֵ����
 *                         ��ע�����ܲ����������ƣ�"�������ڵ��л������ͼ���򡢹����鿴��"��
 *                               ���ڸ����߼�����ȫ���ı䣬����û�о����ע��
 * CR2 2009/04/30 Ѧ��      ԭ�����ݸ��²���ʾ�Ƿ񱣴档
 *                         ���������Ӽ�����������Ƿ���¡�
 *                         ��ע��CRSS-004
 * CR3 2009/05/31 �촺Ӣ   �μ�DefectID=2164 
 * SS1 ���򹤲�������ͼ,���˳��� liuyang 2014-4-1
 * SS2 �ɱ༭��ͼ����Ķ�ͼ���� liunan 2014-6-12
 * SS3 �ɱ༭���鿴��ͼ����Ķ�ͼ���� liunan 2014-6-12
 * SS4 �༭��ͼ������ͼ����ͼ�����⣬��Ϊ��ʼ������ʱɾ�����ɵ�dxf��ʱ�ļ� xuekai 2014-6-19
 * SS5 �����ͼ����  leixiao 2015-3-23 
 * SS6 �ɶ�����������Դ��ӹ�����Դ���� guoxiaoliang 2016-8-2
 */
   
package com.faw_qm.cappclients.capp.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.faw_qm.capp.model.PDrawingIfc;
import com.faw_qm.capp.model.PDrawingInfo;
import com.faw_qm.capp.model.QMProcedureDrawingLinkIfc;
import com.faw_qm.capp.model.QMProcedureDrawingLinkInfo;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.cappclients.beans.drawingpanel.ToolSelectedDialog;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.resource.support.model.DrawingIfc;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import com.faw_qm.persist.util.*;

//CCBegin SS1
import java.util.Arrays;
import javax.swing.JTextField;
//CCEnd SS1
/**
 * <p>Title:����ʹ�ü�ͼ�ͻ���bean</p>
 * <p>Description: ��panel����ά������ʹ�ü�ͼ�Ĺ�����ϵ
 * </p> ά�����ּ�ͼ��ϵ�������ͼ����Դ��ͼ
 * ����û�п��ǵ���Դ��ͼ��Ϊ�����ͼ��״��
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: һ������</p>
 * @author lil
 * @version 1.0
 */
public class ProcedureUsageDrawingPanel
    extends ParentJPanel {
  private ComponentMultiList cappMultiList = new ComponentMultiList();
  private JButton addButton = new JButton();
  private JButton editButton = new JButton();
  private JButton viewButton = new JButton();
  private JButton deleteButton = new JButton();
  private JLabel jLabel1 = new JLabel();

  /**���ڱ����Դ�ļ�·��*/
  protected static String RESOURCE
      = "com.faw_qm.cappclients.capp.util.CappLMRB";
  private JFrame parentJFrame;
  private ResourceBundle rb = ResourceBundle.getBundle(
      "com.faw_qm.cappclients.beans.drawingpanel.DrawingPanelRB",
      RemoteProperty.getVersionLocale());

  //ѡ���ͼ���ͶԻ���
  private ToolSelectedDialog selectDialog;

  /**bean�Ĺ���ģʽ��ʶ*/
  private int model = 2;

  /**�鿴ģʽ*/
  public static int VIEW = 1;

  /**�༭ģʽ*/
  public static int EDIT = 2;

  //��ͼ�������кţ�ֵ��ͼ�������ݿ��б��������
  private HashMap drawingDataTable = new HashMap();

  //����İ�ť��0Ϊ��ӣ�1Ϊ�༭��2Ϊ�鿴��3Ϊɾ��
  private int selectedButton;

  //���ͼҳ��ʽ�������������ݣ�ֵ����������ڴ洢�ڼ�ͼ������ֶ���
  private HashMap drawingFormatHash;

  //���򣨹���)ֵ����
  private QMProcedureIfc procedure;

  //ͼҳ��ʽ�ļ��ϣ����ڱ���е���Ͽ򣬹��û�ѡ��
  private Vector drawingFormatVec;

  //������߹���ֵ����
  private QMProcedureIfc info;

  //Ϊ�ɸ��µĶ�������ǹ����ͼ����Ϊ��ͼ�����������Դ��ͼ����Ϊ��������
  private ArrayList updatedLinks;

  //Ϊ��ɾ���Ķ�������ǹ����ͼ����Ϊ��ͼ�����������Դ��ͼ����Ϊ��������
  private ArrayList removedLinks;

  //Ϊ���ݻ��棬�����кţ�ֵ����ǹ����ͼ����Ϊ��ͼ�����������Դ��ͼ����Ϊ��������
  private HashMap rowMap;
  
  
  //CCBegin SS6
  boolean isprocedure = false;
  JButton getProDrawButton = new JButton();
  private boolean modifyFlag = false;
  private String newbsoid = "";
  //CCEnd SS6

  /**
   * ������
   * @param parentFrame JFrame ������
   */
  public ProcedureUsageDrawingPanel(JFrame parentFrame) {
    this.parentJFrame = parentFrame;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * ������
   * @param parentFrame JFrame ������
   * @param isprocedure boolean �Ƿ�Ϊ����
   */
  public ProcedureUsageDrawingPanel(JFrame parentFrame, boolean isprocedure) {
	  
	  //CCBegin SS6
	  
	  this.isprocedure = isprocedure;
	  //CCEnd SS6
	  
    parentJFrame = parentFrame;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * ��ʼ��
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(new GridBagLayout());
    this.add(cappMultiList, new GridBagConstraints(0, 0, 1, 7, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(10, 10, 35, 0), 119, 130));
    this.add(addButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.NONE,
                                               new Insets(8, 8, 0, 8), 0, 0));
    this.add(editButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(8, 8, 0, 8), 0, 0));
    this.add(viewButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(8, 8, 0, 8), 0, 0));
    this.add(deleteButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(8, 8, 0, 8), 0, 0));
    this.add(jLabel1, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.BOTH,
                                             new Insets(0, 0, 0, 0), 0, 0));
    //CCBegin  SS1  ���˳����еļ���
    cappMultiList.addActionListener(new java.awt.event.
            ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		cappMultiList_actionPerformed(e);
    	}
    });
	  
    //CCBegin SS6
    if (!isprocedure) {
    	  this.add(getProDrawButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
    	      , GridBagConstraints.CENTER, GridBagConstraints.NONE,
    	      new Insets(8, 8, 0, 8), 0, 0));
    	}
    //CCEnd SS6
	  
  //CCEnd  SS1
    localize();
    updatedLinks = new ArrayList();
    removedLinks = new ArrayList();
    rowMap = new HashMap();
  }

  //CCBegin  SS1
  /**
   * ˳�����֤
   */
  public void cappMultiList_actionPerformed(ActionEvent e)
  {
	  int row = 0;
	  int col = 0;
      Object obj = e.getSource();
      String actionCommand = e.getActionCommand();
      int t = actionCommand.indexOf(";");
      if (t != -1)
      {
          //�õ���
          String rowString = actionCommand.substring(0, t);
          int t1 = rowString.indexOf(":");
          row = Integer.parseInt(rowString.substring(t1 + 1, rowString.length()));
          //�õ���
          String colString = actionCommand.substring(t + 1,
                  actionCommand.length());
          int t2 = colString.indexOf(":");
          col = Integer.parseInt(colString.substring(t2 + 1, colString.length()));

      }


      
      //���ѡ�е���˳�����,���е����ݸ�ʽ������Ҫ�����Զ�����ֵ"1"
      if (col == 0)
      {
          if (obj instanceof JTextField)
          {
              JTextField textField = (JTextField) obj;
              String number = textField.getText().trim();
              if(number ==null || number.equals(""))
              {
            	  
            	  JOptionPane.showMessageDialog(null, "˳��Ų���Ϊ��");
            	  cappMultiList.addTextCell(row, col, "1");
              }
              try
              {
                  int f = Integer.parseInt(number);
                  if(f<=0)
                  {
                	  JOptionPane.showMessageDialog(null, "˳��Ų���Ϊ����߸���"); 
                	  cappMultiList.addTextCell(row, col, "1");
                  }

              }
              catch (NumberFormatException ex)
              {
            	  cappMultiList.addTextCell(row, col, "1");
            	  JOptionPane.showMessageDialog(null, "˳��ű����Ǵ������������");

              }
          }
      }
  }
  //CCEnd  SS1
  
  /**
   * ������Ϣ���ػ�
   */
  protected void localize() {
    addButton.setMaximumSize(new Dimension(89, 23));
    addButton.setMinimumSize(new Dimension(89, 23));
    addButton.setPreferredSize(new Dimension(89, 23));
    addButton.setText("���(F)...");
    addButton.addActionListener(new java.awt.event.
                                ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addButton_actionPerformed(e);
      }
    });
    addButton.setMnemonic('F');
    editButton.setMaximumSize(new Dimension(89, 23));
    editButton.setMinimumSize(new Dimension(89, 23));
    editButton.setPreferredSize(new Dimension(89, 23));
    editButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        "editJButton", null));
    editButton.setText("�༭(J)...");
    editButton.addActionListener(new java.awt.event.
                                 ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editButton_actionPerformed(e);
      }
    });
    editButton.setMnemonic('J');
    viewButton.setMaximumSize(new Dimension(89, 23));
    viewButton.setMinimumSize(new Dimension(89, 23));
    viewButton.setPreferredSize(new Dimension(89, 23));
    viewButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        "jMenuSelectView", null));
    viewButton.addActionListener(new java.awt.event.
                                 ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewButton_actionPerformed(e);
      }
    });
    viewButton.setMnemonic('V');
    deleteButton.setMaximumSize(new Dimension(89, 23));
    deleteButton.setMinimumSize(new Dimension(89, 23));
    deleteButton.setPreferredSize(new Dimension(89, 23));
    deleteButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        "jMenuSelectDelete", null));
    deleteButton.addActionListener(new java.awt.event.
                                   ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteButton_actionPerformed(e);
      }
    });
    deleteButton.setMnemonic('D');
    
    //CCBegin SS6
    
    getProDrawButton.setMaximumSize(new Dimension(89, 23));
    getProDrawButton.setMinimumSize(new Dimension(89, 23));
    getProDrawButton.setPreferredSize(new Dimension(89, 23));
    getProDrawButton.setText("������Դ(X)");
//    getProDrawButton.addActionListener(new
//                                       ProcedureUsageDrawingPanel_getProDrawButton_actionAdapter(this));
    getProDrawButton.setMnemonic('X');
    //CCEnd SS6

    //CCBegin SS1
//    String[] label = new String[7];
    String[] label = new String[8];
    //CCEnd SS1
    label[0] = "��ͼ";
    label[1] = "����";
    label[2] = "��ͼ��Դ���";
    label[3] = "ͼҳ��ʽ";
    label[4] = "������ͼ��Դ";
    //����Ϊbsoid������ǹ����ͼ��Ϊ����bsoid���������Դ��ͼ�����ǹ���bsoid
    label[5] = "bsoid";
    label[6]= "relationbsoid";
    //CCBegin SS1
    label[7]= "˳���";
 
    //CCEnd SS1
    cappMultiList.setHeadings(label);
    //CCBegin SS1
//    cappMultiList.setRelColWidth(new int[] {1, 1, 1, 1, 1, 0,0});
    cappMultiList.setRelColWidth(new int[] {1, 1, 1, 1, 1, 0,0,1});
    //CCEnd SS1
    cappMultiList.setAllowSorting(false);
    cappMultiList.setColsEnabled(new int[] {0, 1, 2, 4}
                                 , false);
    cappMultiList.setMultipleMode(true);
  }

  /**
   * ��Ӱ�ť��ִ���¼�
   * @param e ActionEvent
   */
  private void addButton_actionPerformed(ActionEvent e) {
    selectedButton = 0;
    int result = JOptionPane.showConfirmDialog(parentJFrame,
                                               "��Ҫ������ͼô��", rb.getString("2"),
                                               JOptionPane.YES_NO_OPTION,
                                               JOptionPane.INFORMATION_MESSAGE);
    if (result == JOptionPane.YES_OPTION) {
    	//CCBegin SS6
    	this.modifyFlag = true;
    	//CCEnd SS6
      EditThread thread = new EditThread();
      thread.start();
    }
  }

  /**
   * �༭��ť��ִ���¼�
   * @param e ActionEvent
   */
  private void editButton_actionPerformed(ActionEvent e) {
    if (cappMultiList.getSelectedRow() == -1) {
      return;
    }
    int row = cappMultiList.getSelectedRow();
    //��ǰѡ�е�������Դ��ͼ���ҹ�����ͼ��Դ��ѡ�У��򲻿ɱ༭
    if (!cappMultiList.getCellText(row, 2).equals("")) {
      if ( ( (Boolean) cappMultiList.getCellAt(row, 4).getValue()).
          booleanValue()) {
        JOptionPane.showMessageDialog(parentJFrame, "��Դ��ͼ������Դģ����ά����", "��ʾ",
                                      JOptionPane.INFORMATION_MESSAGE);
        return;
      }
    }
    selectedButton = 1;
    int result = JOptionPane.showConfirmDialog(parentJFrame,
                                               "��Ҫ�༭��ͼô��", rb.getString("2"),
                                               JOptionPane.YES_NO_OPTION,
                                               JOptionPane.INFORMATION_MESSAGE);
    if (result == JOptionPane.YES_OPTION) {
    	//CCBegin SS6
    	this.modifyFlag = true;
    	//CCEnd SS6
      EditThread thread = new EditThread();
      thread.start();
    }
  }

  /**
   * �鿴��ť��ִ���¼�
   * @param e ActionEvent
   */
  private void viewButton_actionPerformed(ActionEvent e) {
    int selectedRow = cappMultiList.getSelectedRow();
    if (selectedRow == -1) {
      return;
    }
    selectedButton = 2;
    Vector data = new Vector();
    if(drawingDataTable.containsKey(String.valueOf(selectedRow))){
    	data = (Vector) drawingDataTable.get(String.valueOf(selectedRow));
    }else{
    	BaseValueIfc bvi = null;
    	 try{
    		 bvi=  (BaseValueIfc)CappClientHelper.refresh((String)(cappMultiList.getCellText(selectedRow, 5)));
    	    }
    	 catch(QMRemoteException ee){
    		 ee.printStackTrace();
    	 }
    	 if (bvi instanceof DrawingIfc)
		    {
    		 data .add(0,((DrawingIfc) bvi).getDrawingByte());
    		 data .add(1,((DrawingIfc) bvi).getDrawingStreamType());
		    }
		 else 
		    if (bvi instanceof PDrawingIfc)
		    {
		      data .add(0,((PDrawingIfc) bvi).getDrawingByte());
		      data .add(1,((PDrawingIfc) bvi).getDrawingType());
		    }
    	 drawingDataTable.put(String.valueOf(selectedRow), data);
    	 rowMap.put(String.valueOf(selectedRow), bvi);
    }
     
    if (data != null) {
      if (selectDialog == null) {
        selectDialog = new ToolSelectedDialog(parentJFrame, "", true);
      }
      selectDialog.setDrawingData(data);
      //�鿴��ͼ
      selectDialog.view();
    }
  }

  /**
   * ɾ����ť��ִ���¼�
   * @param e ActionEvent
   */
  private void deleteButton_actionPerformed(ActionEvent e) {
    selectedButton = 3;
    //CCBegin SS6
    this.modifyFlag = true;
    //CCEnd SS6
    int[] rows = cappMultiList.getSelectedRows();
    for (int rownumber = rows.length; rownumber > 0; rownumber--) {
      int row = rows[rownumber - 1];
      if (row != -1) {
        drawingDataTable.remove(String.valueOf(row));
        BaseValueIfc bvi = (BaseValueInfo) rowMap.get(String.valueOf(row));
        if(cappMultiList.getCellText(row, 5).startsWith("Drawing")){
        	removedLinks.add(cappMultiList.getCellText(row, 6));
        }
        else if(cappMultiList.getCellText(row, 5).startsWith("PDrawing")){
        	if(bvi == null){
                try{
               		 bvi=  (BaseValueIfc)CappClientHelper.refresh((String)(cappMultiList.getCellText(row, 5)));
               	    }
               	 catch(Exception ee){
               		 ee.printStackTrace();
               	 }
                }//end == null	
        	if (bvi!=null&&bvi.getBsoID() != null && !bvi.getBsoID().equals("")) {
        		//CCBegin by liunan 2011-01-20 �޸Ķ��� �޷������ͼ���͵�����ʱ���ĵ�����bvi��������link������PDrawing��
                if (bvi instanceof PDrawingIfc)
                {
                	removedLinks.add(bvi);
                	}
                //CCEnd by liunan 2011-01-20
                removedLinks.add((String)(cappMultiList.getCellText(row, 6)));
              }
        	  //���ɾ���Ķ���û�б��־û�,��û�б�Ҫ���뵽��ɾ���ļ�����
              if (updatedLinks.contains(bvi)) {
                updatedLinks.remove(bvi);
              }
        } //end pdrawing    
        else{
        	if (updatedLinks.contains(bvi)) {
                updatedLinks.remove(bvi);
              }
        }
        rowMap.remove(String.valueOf(row));
        int rowcount = cappMultiList.getRowCount();
        if ( (rowcount - 1) > row) {
          for (int i = row + 1; i <= (rowcount - 1); i++) {
            Object obj = drawingDataTable.get(String.valueOf(i));
            drawingDataTable.remove(String.valueOf(i));
            drawingDataTable.put(String.valueOf(i - 1), obj);
            cappMultiList.addTextCell(i, 0, "��ͼ" + i);
            BaseValueInfo bv = (BaseValueInfo) rowMap.get(String.valueOf(i));
            rowMap.remove(String.valueOf(i));
            rowMap.put(String.valueOf(i - 1), bv);
          } //forѭ������
        }
        cappMultiList.removeRow(row);
      }
    } //�����forѭ������
    //log();����ɸ��¶��󣬿�ɾ�������л��档
  }

  /**
   * ɾ���༭ʱ�ı����ļ�
   */
  private synchronized void deleteEditTempFile() {
    File dxfFile = new File(ToolSelectedDialog.tempDxfPath);
    if (dxfFile.exists()) {
      dxfFile.delete();
    }
  }

  /**
   * ����ͼҳ��ʽ,��Ϊ��ͼ��ȫͼ
   */
  private void setDrawingFormat() {
    drawingFormatVec = new Vector();
    drawingFormatHash = new HashMap();
    Class[] paraclass = new Class[] {
        String.class, String.class};
    Object[] paraobj = new Object[] {
        "ͼҳ��ʽ", "ģ��"};
    Collection col = null;
    try {
      col = (Collection) useServiceMethod("CodingManageService",
                                          "findDirectClassificationByName",
                                          paraclass, paraobj);
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
    }  
    Iterator iterator = col.iterator();
    while (iterator.hasNext()) {
      CodingIfc info1 = (CodingIfc) iterator.next();
      if (info1.getCodeContent().equals("ϸ��ҳ")) {
        continue;
      }
      drawingFormatVec.add(info1.getCodeContent());
      drawingFormatHash.put(info1.getCodeContent(),
                            info1);
    }
  }

  /**
   * ���ù������
   * @param ifc QMProcedureIfc
   */
  public void setQMProcedureIfc(QMProcedureIfc ifc) {
    info = ifc;
  }

  /**
   *   �ڲ��࣬����߳�
   */
  class EditThread
      extends Thread {
    /**
     * �����Դ
     */
    public EditThread() {

    }

    public void run() {
      Dimension screenSize = Toolkit.getDefaultToolkit().
          getScreenSize();
      selectDialog = new ToolSelectedDialog(parentJFrame, "", true);
      selectDialog.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Object ob = e.getSource();
          if (ob != null && ob instanceof Vector) {
            addPDrawing( (Vector) ob);
          }
        }
      });
      //CCBegin SS4
      selectDialog.deleteTempFile();
      //CCEnd SS4
      //�༭��ťѡ��ʱ�����ü�ͼ����
      if (selectedButton == 1) {
    	  int r = cappMultiList.getSelectedRow();
    	  Vector drawing = new Vector();
    	  if(drawingDataTable.containsKey(String.valueOf(r))){
    		  drawing = (Vector) drawingDataTable.get(String.valueOf(r));
    	    }else{
    	    	BaseValueIfc bvi = null;
    	    	 try{
    	    		 bvi=  (BaseValueIfc)CappClientHelper.refresh((String)(cappMultiList.getCellText(r, 5)));
    	    	    }
    	    	 catch(QMRemoteException ee){
    	    		 ee.printStackTrace();
    	    	 }
    	    	 if (bvi instanceof DrawingIfc)
    			    {
    	    		 drawing .add(0,((DrawingIfc) bvi).getDrawingByte());
    	    		 drawing .add(1,((DrawingIfc) bvi).getDrawingStreamType());
    			    }
    			 else 
    			    if (bvi instanceof PDrawingIfc)
    			    {
    			    	drawing .add(0,((PDrawingIfc) bvi).getDrawingByte());
    			    	drawing .add(1,((PDrawingIfc) bvi).getDrawingType());
    			    }
    	    	 drawingDataTable.put(String.valueOf(r), drawing);
    	    	 rowMap.put(String.valueOf(r), bvi);
    	    }  
        selectDialog.setDrawingData(drawing);
      }
      selectDialog.setLocation( (int) (screenSize.getWidth() -
                                       selectDialog.getWidth()) / 2,
                               (int) (screenSize.getHeight() -
                                      selectDialog.getHeight()) / 2);
      selectDialog.setVisible(true);
    }
  }

  /**
   * ��multiList����Ӽ�ͼ����,���ڴ�����༭��ͼ֮��,ֻ���ڹ����ͼ
   * @param drawing Vector ��ͼ����
   */
  private void addPDrawing(Vector drawing) {
    //����ʱ
    if (selectedButton == 0) {
      String picType = (String) drawing.elementAt(1);
      int row = cappMultiList.getRowCount();
      cappMultiList.addTextCell(row, 0, "��ͼ" + (row + 1));
      cappMultiList.addTextCell(row, 1, picType);
      cappMultiList.addTextCell(row, 2, "");
      //���ͼҳ��ʽ
      if (drawingFormatVec == null) {
        setDrawingFormat();
      }
      //CCBegin by liunan 2011-01-21 �ĳ� ��ȫͼҳ�� ΪĬ��
      //String drawingFormat = (String) drawingFormatVec.get(0);
      String drawingFormat = (String) drawingFormatVec.get(1);
      //CCEnd by liunan 2011-01-21
      if (drawingFormatVec != null) {
        cappMultiList.addComboBoxCell(row, 3,
                                      drawingFormat,
                                      drawingFormatVec);
      }
      else {
        JOptionPane.showMessageDialog(parentJFrame,
                                      "��������е�ͼҳ��ʽΪ�գ�", "��ʾ",
                                      JOptionPane.INFORMATION_MESSAGE);
      }
      cappMultiList.addTextCell(row, 4, "");
      cappMultiList.addTextCell(row, 5, "");
      //CCBegin  SS1
      cappMultiList.addTextCell(row, 7,row +1+ "");
    //CCEnd  SS1
      PDrawingInfo pdrawing = new PDrawingInfo();
      pdrawing.setDrawingFormat( (CodingIfc) drawingFormatHash.get(
          drawingFormat));
      pdrawing.setDrawingByte((byte[])drawing.elementAt(0));
      pdrawing.setDrawingType((String)drawing.elementAt(1));
      //�����cadͼ
      if (picType.equals("Autocad")) {
        try {
          String[] strs = getLengthAndWidth(picType);
          pdrawing.setLength(strs[0]);
          pdrawing.setWidth(strs[1]);
          pdrawing.setX(strs[2]);
          pdrawing.setY(strs[3]);
        }
        catch (QMException ex) {
          ex.printStackTrace();
        }
        catch (IOException ee) {
          ee.printStackTrace();
        }
      }
      updatedLinks.add(pdrawing);
      rowMap.put(String.valueOf(row), pdrawing);
      drawingDataTable.put(String.valueOf(row), drawing);
    }
    //�༭ʱ
    else
    if (selectedButton == 1) {
      int row = cappMultiList.getSelectedRow();
      String picType = (String) drawing.elementAt(1);
      drawingDataTable.put(String.valueOf(row), drawing);
      BaseValueInfo bvi = (BaseValueInfo)rowMap.get(String.valueOf(row));
      if (bvi !=null&&bvi.getBsoID()!= null && !bvi.getBsoID().equals("")) {
        //CCBegin SS2
        //removedLinks.add(bvi);
        //CCEnd SS2
        removedLinks.add((String) cappMultiList.getCellText(row, 6));
      }
      else {
        if (updatedLinks.contains(bvi)) {
          updatedLinks.remove(bvi);
        }
      }
      PDrawingInfo pdrawing = new PDrawingInfo();
      String drawingFormatConten = (String) cappMultiList.getCellAt(
          row, 3).getValue();
      pdrawing.setDrawingFormat( (CodingIfc) drawingFormatHash.get(
          drawingFormatConten));
      pdrawing.setDrawingByte((byte[])drawing.elementAt(0));
      pdrawing.setDrawingType((String)drawing.elementAt(1));
      if (picType.equals("Autocad")) {
        try {
          String[] strs = getLengthAndWidth(picType);
          pdrawing.setLength(strs[0]);
          pdrawing.setWidth(strs[1]);
          pdrawing.setX(strs[2]);
          pdrawing.setY(strs[3]);
        }
        catch (QMException ex) {
          ex.printStackTrace();
        }
        catch (IOException ee) {
          ee.printStackTrace();
        }
      }
      updatedLinks.add(pdrawing);
      rowMap.put(String.valueOf(row), pdrawing);
    }
    log();
  }

  /**
   * �����Դ��ͼ������Դ�������
   * @param drawingIfc DrawingIfc ��Դ��ͼ
   */
  public void addDrawingToTable(DrawingIfc drawingIfc) {
    int row = cappMultiList.getRowCount();
    cappMultiList.addTextCell(row, 0, "��ͼ" + (row + 1));
    if (drawingIfc.getDrawingStreamType() == null) {
      cappMultiList.addTextCell(row, 1, "");
    }
    else {
      cappMultiList.addTextCell(row, 1,
                                (String) drawingIfc.getDrawingStreamType());
    }
    cappMultiList.addTextCell(row, 2, drawingIfc.getDrawingNumber());
    //���ͼҳ��ʽ
    if (drawingFormatVec == null) {
      setDrawingFormat();
    }
    String drawingformat = (String) drawingFormatVec.get(0);
    if (drawingFormatVec != null) {
      cappMultiList.addComboBoxCell(row, 3, drawingformat, drawingFormatVec);
    }
    else {
      JOptionPane.showMessageDialog(parentJFrame,
                                    "��������е�ͼҳ��ʽΪ�գ�", "��ʾ",
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    cappMultiList.addCheckboxCell(row, 4, true);
    cappMultiList.addTextCell(row, 5, drawingIfc.getBsoID());
    cappMultiList.addTextCell(row, 6, "");
    //CCBegin  SS1
    cappMultiList.addTextCell(row, 7, row + 1 + "");
  //CCEnd  SS1
    QMProcedureDrawingLinkInfo link = new
        QMProcedureDrawingLinkInfo();  
    link.setDrawingFormat( (CodingIfc) drawingFormatHash.get(
        drawingformat));
    link.setRightBsoID(drawingIfc.getBsoID());
    if (procedure != null) {
      link.setLeftBsoID(procedure.getBsoID());
    }
    link.setDrawingNumber(drawingIfc.getDrawingNumber());//CR3
    link.setIsDrawingLink(true);
    link.setDrawingType((String) drawingIfc.getDrawingStreamType());
    updatedLinks.add(link);
    rowMap.put(String.valueOf(row), link);
    log();
  }

  /**
   * ���ý���ģʽ
   * @param mode int ����ģʽ
   */
  public void setModel(int mode) {
    model = mode;
    if (model == VIEW) {
      setEditMode(false);
    }
    else {
      setEditMode(true);
    }
  }

  /**
   * �������״̬
   * @param flag boolean �Ƿ�ɱ༭��ɼ�
   */
  private void setEditMode(boolean flag) {
    if (flag) {
      cappMultiList.setColsEnabled(new int[] {0, 1, 2}
                                   , false);
      //CCBegin SS1
//      cappMultiList.setColsEnabled(new int[] {3, 4}
//                                   , true);

      cappMultiList.setColsEnabled(new int[] {3,4,7}
      , true);
      //CCEnd SS1
    }
    else {
      cappMultiList.setColsEnabled(new int[] {0, 1, 2, 3, 4}
                                   , false);
    }
    //CCBegin SS6
    getProDrawButton.setVisible(flag);
    //CCEnd SS6
    addButton.setVisible(flag);
    editButton.setVisible(flag);
    deleteButton.setVisible(flag);
  }
//CCBegin SS6
  void getProDrawButton_actionPerformed(ActionEvent e) {
//	  20090917 ������ӣ�������ʾ����
	  this.modifyFlag = true;
//	  end add by mario in 20090917
//    setProcedure1(info);
  }
  //CCEnd SS6
  /**
   * ���ù���ֵ����
   * @param qmProcedure QMProcedureIfc ����ֵ����
   */
  public void setProcedure(QMProcedureIfc qmProcedure) {
    procedure = qmProcedure;
    rowMap.clear();
    drawingDataTable.clear();
    cappMultiList.clear();
    if (qmProcedure.getBsoID() != null) {
    try { 
    	  Collection links= getRelations(procedure);
    	  QMProcedureDrawingLinkIfc procedureDrawLink;  
    	  Iterator i = links.iterator();
    	  //CCBegin  SS1
    	  int k=0;
    	  //CCEnd  SS1
  		  while (i.hasNext()) 
  		 {
  		    procedureDrawLink = (QMProcedureDrawingLinkIfc) i.next();
		    int rowCount = cappMultiList.getRowCount();
            cappMultiList.addTextCell(rowCount, 0,
                                      "��ͼ" + (rowCount + 1));
            cappMultiList.addTextCell(rowCount, 1, procedureDrawLink.getDrawingType());
            cappMultiList.addTextCell(rowCount, 2, procedureDrawLink.getDrawingNumber());
            //���ͼҳ��ʽ
            if (drawingFormatVec == null)
            {
                setDrawingFormat();
            }
            if (drawingFormatVec != null)
            {
            	cappMultiList.addComboBoxCell(rowCount, 3,
            			procedureDrawLink.getDrawingFormat().
                        getCodeContent(),
                        drawingFormatVec);
            }
            else
            {
               JOptionPane.showMessageDialog(parentJFrame,
                          "��������е�ͼҳ��ʽΪ�գ�", "��ʾ",
                          JOptionPane.INFORMATION_MESSAGE);
            }
            cappMultiList.addComboBoxCell(rowCount, 3,
            		procedureDrawLink.getDrawingFormat().getCodeContent(),
            		drawingFormatVec);  
            if (procedureDrawLink.getIsDrawingLink())
            {
            	cappMultiList.addCheckboxCell(rowCount, 4, procedureDrawLink.getIsDrawingLink());
            }
            else
            {
            	cappMultiList.addTextCell(rowCount, 4, "");
            }
            cappMultiList.addTextCell(rowCount, 5, procedureDrawLink.getRightBsoID());
            cappMultiList.addTextCell(rowCount, 6, procedureDrawLink.getBsoID());
            //CCBegin  SS1
            int seq =  procedureDrawLink.getSeq();
            if(seq==0){
            	k++;
            	seq = k;
            	
            }
            cappMultiList.addTextCell(rowCount, 7, seq+"");
            //CCEnd  SS1
            //CCBegin by liunan 2011-01-18 Ѧ�������޸ģ��޷������ͼ���͵�����
            //Ѧ��  ��Ŷ���   ��ʼ  2011/01/17
                    System.out.println("rowCount====" + rowCount + "====" + procedureDrawLink.getBsoID());
                    rowMap.put(String.valueOf(rowCount), procedureDrawLink);
                    //Ѧ�� ��Ŷ��� ����
            //CCEnd by liunan 2011-01-18
            log() ;
         } 
      }
      catch (QMException ex) {
        ex.printStackTrace();
      }
      //begin CR2
      finally {
        cappMultiList.intAfterThread();
        //CCBegin SS6
        this.modifyFlag = false;
        //CCEnd SS6
        log();
      }
      //end CR2
    }
  }

  //CCBegin SS6
  
  public boolean getModifyFlag() {
	    return this.modifyFlag;
	  }
  
  public void setModifyFlag(boolean flag) {
    modifyFlag = flag;
  }
  //CCEnd SS6
  
  /**
   * ���������Ϣ
   */
  public void clear() {
    cappMultiList.clear();
    if (selectDialog != null) {
      selectDialog.clear();
    }
    procedure = null;
    drawingDataTable.clear();
    deleteEditTempFile();
  }

//CCBegin  SS1
  /**
   * �ڱ��湤����߹���ʱ,��֤˳����Ƿ��ظ�
   */
  public boolean checkSeqNum() {
	  boolean flag = false;
	  int row = cappMultiList.getNumberOfRows();
	  Vector<Integer> v = new Vector<Integer>();
	  
	  for (int i = 0; i < row; i++) {
		  int seqNum = Integer.valueOf(cappMultiList.getCellText(i, 7));
		  v.addElement(seqNum);
	  }
	  
	  for(int j =0;j<v.size();j++)
	  {
		  int seqCount = v.get(j);
		  v.remove(j);
		  if(v.contains(seqCount))
		  {
			  flag = true;
			  JOptionPane.showMessageDialog(null, "˳����ظ�,���޸ļ�ͼ�������ظ���˳���");
			  break;
		  }
	  }
	  
	  return flag;
  }
 //CCEnd  SS1
  /**
   * ��һ��Ԫ���ǿɸ��µļ���
   * �ڶ���Ԫ���ǿ�ɾ���ļ���
   * @return Object[]
   */
  public Object[] getDrawings() {
  	//CCBegin by liunan 2011-01-18 Ѧ�������޸ģ��޷������ͼ���͵�����
  	//Ѧ��  ��Ŷ���   ��ʼ  2011/01/17
    	checkMutliLIstValue();
    	//Ѧ�� ��Ŷ��� ����
  	//CCEnd by liunan 2011-01-18
    Object[] obj = new Object[2];
	//CCBegin  SS1   
    updatedLinks.clear(); 
	  //���к���������Ӧ��˳��Ž���HashMap,����˳���,ֵ���к�
		int i = cappMultiList.getTable().getModel().getRowCount();	
		HashMap rowSeq = new HashMap();
		int seq[] = new int[i];
			
			for(int j = 0; j< i ; j++)
			{
				//˳����ǵ�1��
				int seqNum =Integer.valueOf(cappMultiList.getCellText(j, 7)) ;
				seq[j] = seqNum;
		    	rowSeq.put(seqNum, j);
			}
			//��������
			Arrays.sort(seq);
			for( int n = 0; n < seq.length; n++)
			{
				int num =(Integer)rowSeq.get(seq[n]) ;
				//��biosid, ���б����8��
				String ID = cappMultiList.getCellText(num, 6);
				if(ID != null && !(ID.equals("")))
				{
					//CCBegin SS3 SS5
					BaseValueIfc bvi = (BaseValueInfo) rowMap.get(String.valueOf(num));
					//����м�ͼ�����Ҽ�ͼû��bsoid��˵�������޸ĵģ���û�г־û��������
					//��ʱ��Ҫ��PDrawing�ӵ�updatedLinks�С�
					if(bvi!=null){
					if((bvi instanceof PDrawingIfc) && bvi.getBsoID()==null)
					{
						updatedLinks.add(rowMap.get(String.valueOf(num)));
					}
					else
					//CCEnd SS3 SS5
					updatedLinks.add(ID);
					}
				}
					
				else{
					//CCBegin SS5
					BaseValueIfc bvi = (BaseValueInfo) rowMap.get(String.valueOf(num));
					if(bvi!=null)
					{
					updatedLinks.add(rowMap.get(String.valueOf(num)));
					}
					//CCEnd SS5
				}	
				
				
			}
//CCEnd  SS1
    obj[0] = updatedLinks;
    obj[1] = removedLinks;
    return obj;
  }

  /**
   * ��չ���
   */
  public void resetArrayList() {
    removedLinks = new ArrayList();
    updatedLinks = new ArrayList();
    //CCBegin SS5
    rowMap.clear();
    //CCEnd SS5
  }

  /**
   * �������
   */
  private void log() {
    System.out.println("��ɾ���ļ�����__________" + removedLinks);
    System.out.println("�ɸ��µļ�����__________" + updatedLinks);
    System.out.println("�м�����__________" + rowMap);
  }

  /**
   * ͨ���ļ�_te_eo%fs$ie@o$%#.info��ȡ��ͼ�Ļ�������
   * @param type String
   * @throws QMException
   * @throws IOException
   * @throws CappException
   * @return String[] ������ͼ�ĳ�����x��y����
   */
  private String[] getLengthAndWidth(String type) throws QMException,
      IOException {
    String[] lengthandwidth = new String[4];
    String length = "";
    String width = "";
    String x = "";
    String y = "";
    //·��Ϊc:/_te_eo%fs$ie@o$%#.info
    String path = rb.getString("11");
    File file = new File(path);
    if (!file.exists() && type.equals("Autocad")) {
      JOptionPane.showMessageDialog(this, "��C:/��δ�ҵ�_te_eo%fs$ie@o$%#.info�ļ�",
                                    "��ʾ",
                                    JOptionPane.INFORMATION_MESSAGE);
      return null;
    }
    BufferedReader bufferReader = new BufferedReader(new FileReader(path));
    String temp = bufferReader.readLine();
    String temp1 = bufferReader.readLine();
    StringTokenizer token = new StringTokenizer(temp, ",");
    while (token.hasMoreElements()) {
      length = (String) token.nextElement();
      width = (String) token.nextElement();
    }
    StringTokenizer token1 = new StringTokenizer(temp1, ",");
    while (token1.hasMoreElements()) {
      x = (String) token1.nextElement();
      y = (String) token1.nextElement();
    }
    lengthandwidth[0] = length;
    lengthandwidth[1] = width;
    lengthandwidth[2] = x;
    lengthandwidth[3] = y;
    bufferReader.close();
    return lengthandwidth;
  }
  
  /**
	 * ���ݵ�ǰ�����ѯ�뵱ǰ���������ͼ�Ĺ��������ļ��ϡ�
	 * @return Collection ��ǰ�������ͼ�Ĺ��������ļ���
	 * @throws QMRemoteException
	 * @throws ClassNotFoundException
	 */
	private Collection getRelations(QMProcedureIfc qmProcedure)
	        throws QMRemoteException    
	{
	    Collection queryresult = null;
	    try
	    {
	    	QMQuery query = new QMQuery("QMProcedureDrawingLink");
	    	query.addCondition(new QueryCondition("leftBsoID","=",qmProcedure.getBsoID()));
	    	//CCBegin  SS1 �����ѯ���,�����ݿ��а���˳��Ŵ�С����ȡ����
	    	query.addOrderBy("seq", false);
	    	//CCEnd  SS1
	        Class[] paraClass =
	               {QMQuery.class,Boolean.TYPE};
	        Object[] objs =
	               {query, new Boolean(false)}; 
	        queryresult = (Collection) useServiceMethod("PersistService",
	                "findValueInfo", paraClass, objs);
	    }
	    catch (QMException ex)
	    {
	        ex.printStackTrace();
	    }
	    return queryresult;
	}
	
	//CCBegin by liunan 2011-01-18 Ѧ�������޸ģ��޷������ͼ���͵�����
    //Ѧ��  ��Ŷ���   ��ʼ  2011/01/17
    private void checkMutliLIstValue()
    {
    	System.out.println("updatedLinks1=======" + updatedLinks);
    	System.out.println("rowMap1=======" + rowMap);
    	System.out.println("removedLinks1=======" + removedLinks);
    	int row = cappMultiList.getRowCount();
    	System.out.println("row=======" + row);
    	for(int i = 0; i < row; i++)
    	{
    		String df = cappMultiList.getCellAt(i, 3).getValue().toString();
    	System.out.println("df=======" + df);
    		CodingIfc code = (CodingIfc)drawingFormatHash.get(df);
    		BaseValueIfc bvi = (BaseValueIfc)rowMap.get(String.valueOf(i));
    		System.out.println("bvi===="+bvi);
    		if (bvi instanceof PDrawingIfc)
			{
    			PDrawingIfc pd = (PDrawingIfc)bvi;
    			int index = updatedLinks.indexOf(pd);;
    			pd.setDrawingFormat(code);
    			if(index != -1)
    			{
    				updatedLinks.remove(index);
    				updatedLinks.add(index, pd);
    			}
			}
			else if (bvi instanceof QMProcedureDrawingLinkIfc)
			{
				QMProcedureDrawingLinkIfc qmpdlink = (QMProcedureDrawingLinkIfc)bvi;
    			int index = updatedLinks.indexOf(qmpdlink);;
    			qmpdlink.setDrawingFormat(code);
    			if(index != -1)
    			{
    				updatedLinks.remove(index);
    				updatedLinks.add(index, qmpdlink);
    			}
    			else if(index == -1)
    			{
    				updatedLinks.add(qmpdlink);
    			}
			}
    	}
    	System.out.println("updatedLinks2=======" + updatedLinks);
    	System.out.println("rowMap2=======" + rowMap);
    	System.out.println("removedLinks2=======" + removedLinks);
    }
    //Ѧ�� ��Ŷ��� ����	
	//CCEnd by liunan 2011-01-18
    
  
    
    
}
