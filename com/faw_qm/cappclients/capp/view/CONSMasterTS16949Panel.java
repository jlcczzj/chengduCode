package com.faw_qm.cappclients.capp.view;
//SS1 ��Ʒƽ̨���ַ����� liuyang 2013-3-28

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;

import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingIfc;

;

/**
 * <p>Title: ���տ�ά������е�TS16949�������</p>
 * <p>Description: ά��TS16949���ԡ�</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author  Ѧ��
 * @version 1.0
 */
public class CONSMasterTS16949Panel
    extends ParentJPanel {

  /**
   * ��������ͼ���
   */
  JLabel processFlowNumjLabel = new JLabel();
  //JTextField processFlowNumjTextField = new JTextField();
  private CappTextField processFlowNumjTextField;

  JLabel processFlowNumDispJLabel = new JLabel();
  /**
   * ���Ƽƻ����
   */
  JLabel controlPlanNumjLabel = new JLabel();

  //JTextField controlPlanNumjTextField = new JTextField();
  private CappTextField controlPlanNumjTextField;
  JLabel controlPlanNumDispJLabel = new JLabel();
  /**
   *  ����FMEA���
   */
  JLabel femaNumjLabel = new JLabel();
  //private CappTextField femaNumjTextField;
  private JComboBox femaNumjTextField = new JComboBox();
  //JTextField femaNumjTextField = new JTextField();
  JLabel femaNumDispJLabel = new JLabel();
  /**
   * ��ҵָ�����
   */
  JLabel taskInstructNumjLabel = new JLabel();
  private CappTextField taskInstructNumjTextField;
  //JTextField taskInstructNumjTextField = new JTextField();
  JLabel taskInstructNumDispJLabel = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  //add by wangh on 20070510
  private JFrame parentJFrame;

  public CONSMasterTS16949Panel() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    //add by wangh on 20070510
    String procedureNumDisp = QMMessage.getLocalizedMessage(RESOURCE,
        "processFlowNum", null);

    String controlPlanNumDisp = QMMessage.getLocalizedMessage(RESOURCE,
        "controlPlanNum", null);

    String taskInstructNumDisp = QMMessage.getLocalizedMessage(RESOURCE,
        "taskInstructNum", null);
    processFlowNumjTextField = new CappTextField(parentJFrame, procedureNumDisp,
                                                 20, true);
    controlPlanNumjTextField = new CappTextField(parentJFrame,
                                                 controlPlanNumDisp, 20, true);
    //femaNumjTextField=new CappTextField(parentJFrame, femaNumDisp, 20, true);
    taskInstructNumjTextField = new CappTextField(parentJFrame,
                                                  taskInstructNumDisp, 20, true);

    processFlowNumjLabel.setMaximumSize(new Dimension(84, 18));
    processFlowNumjLabel.setToolTipText("");
    processFlowNumjLabel.setText("��������ͼ���");
    processFlowNumjTextField.setMinimumSize(new Dimension(6, 23));
    processFlowNumjTextField.setText("");
    controlPlanNumjLabel.setText("�ʱ����ļ����");
    this.setLayout(gridBagLayout1);
    femaNumjLabel.setText("��Ʒƽ̨");
    //CCBegin by chudaming 2008-8-15
    //�����乤����ӵ�����
    taskInstructNumjLabel.setText("������");
    //CCEnd by chudaming 2008-8-15
    controlPlanNumjTextField.setText("");

    taskInstructNumjTextField.setText("");
    this.add(processFlowNumjLabel,
             new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                    , GridBagConstraints.EAST,
                                    GridBagConstraints.NONE,
                                    new Insets(8, 8, 4, 8), 0, 0));
    this.add(processFlowNumjTextField,
             new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                    , GridBagConstraints.WEST,
                                    GridBagConstraints.HORIZONTAL,
                                    new Insets(5, 0, 0, 8), 0, 0));
    this.add(processFlowNumDispJLabel,
             new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                    , GridBagConstraints.WEST,
                                    GridBagConstraints.HORIZONTAL,
                                    new Insets(8, 8, 4, 8), 0, 0));
    this.add(controlPlanNumjLabel,
             new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                    , GridBagConstraints.EAST,
                                    GridBagConstraints.NONE,
                                    new Insets(8, 8, 4, 8), 0, 0));
    this.add(controlPlanNumjTextField,
             new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                    , GridBagConstraints.WEST,
                                    GridBagConstraints.HORIZONTAL,
                                    new Insets(5, 0, 0, 8), 116, 0));
    this.add(controlPlanNumDispJLabel,
             new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                    , GridBagConstraints.WEST,
                                    GridBagConstraints.HORIZONTAL,
                                    new Insets(8, 8, 4, 8), 0, 0));

    this.add(femaNumjLabel,
             new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                    , GridBagConstraints.EAST,
                                    GridBagConstraints.NONE,
                                    new Insets(8, 8, 4, 8), 0, 0));
    this.add(femaNumjTextField,
             new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                    , GridBagConstraints.WEST,
                                    GridBagConstraints.HORIZONTAL,
                                    new Insets(5, 0, 0, 8), 0, 0));
    this.add(femaNumDispJLabel,
             new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                    , GridBagConstraints.WEST,
                                    GridBagConstraints.HORIZONTAL,
                                    new Insets(8, 8, 4, 8), 0, 0));
    this.add(taskInstructNumjLabel,
             new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                    , GridBagConstraints.EAST,
                                    GridBagConstraints.NONE,
                                    new Insets(8, 8, 4, 8), 0, 0));
    this.add(taskInstructNumjTextField,
             new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                    , GridBagConstraints.WEST,
                                    GridBagConstraints.HORIZONTAL,
                                    new Insets(5, 0, 0, 8), 0, 0));
    this.add(taskInstructNumDispJLabel,
             new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                    , GridBagConstraints.WEST,
                                    GridBagConstraints.HORIZONTAL,
                                    new Insets(8, 8, 4, 8), 0, 0));
    //CCBegin by liunan 2010-05-26
    /*femaNumjTextField.addItem("CA6T123");
    femaNumjTextField.addItem("CA6T138");
    femaNumjTextField.addItem("CA6T150");
    femaNumjTextField.addItem("CA7T156");
    femaNumjTextField.addItem("CA7T156-2");
    femaNumjTextField.addItem("CA7T156-3");
    femaNumjTextField.addItem("CA8T150");
    femaNumjTextField.addItem("CA8T135");
    femaNumjTextField.addItem("FS10209");
    femaNumjTextField.addItem("CA9T160");
    femaNumjTextField.addItem("CA10T150-2");
    femaNumjTextField.addItem("CA10T150-3");
    femaNumjTextField.addItem("CA10TA");
    //CCBegin by chudaming 20090914
    femaNumjTextField.addItem("CA9TB160A");
    femaNumjTextField.addItem("CA10TA190A");
  //CCEnd by chudaming 20090914
    //CCBegin by chudaming 20091019
    femaNumjTextField.addItem("CA10TA190M");
    femaNumjTextField.addItem("CA9TB160M");
    //CCEnd by chudaming 20091019
  //CCBegin by chudaming 20091103
    femaNumjTextField.addItem("CA12TA");
    //CCEnd by chudaming 20091103
  //CCBegin by liunan 2010-5-25
    femaNumjTextField.addItem("CA6BB160M");
    //CCEndby liunan 2010-5-25
     */
    Class[] paraclass = { String.class, String.class};
    Object[] paraobj = {"��Ʒƽ̨", "�������"};
    try 
    {
    	Collection coll = (Collection)useServiceMethod("CodingManageService","getCoding",paraclass, paraobj);
        Iterator ite = coll.iterator();
        while (ite.hasNext()) 
        {
        	CodingIfc codinginfo = (CodingIfc) ite.next();
        	String value = codinginfo.getCodeContent().toString();
        	femaNumjTextField.addItem(value);
        }
      }
      catch (QMRemoteException ex) 
      {
        ex.printStackTrace();
      }
      //CCEnd by liunan 2010-05-26
  }

  /**
   * ��ս�����Ϣ
   */
  public void clear() {
    processFlowNumjTextField.setText("");
    processFlowNumDispJLabel.setText("");
    controlPlanNumjTextField.setText("");
    controlPlanNumDispJLabel.setText("");
    femaNumjTextField.setSelectedItem("");
    femaNumDispJLabel.setText("");
    taskInstructNumjTextField.setText("");
    taskInstructNumDispJLabel.setText("");

  }

  /**
   * ��������Ϣ�ĺϷ���
   */
  public boolean check() {
    return processFlowNumjTextField.check() && controlPlanNumjTextField.check() &&
        taskInstructNumjTextField.check();
    //femaNumjTextField.check()&&taskInstructNumjTextField.check();
  }

  /**
   * ���ն�������������
   * @param technics QMTechnicsIfc
   */
  public void commitAttributes(QMTechnicsIfc technics) {
    if (this.processFlowNumjTextField.getText() != null) {
      technics.setProcessFlowNum(processFlowNumjTextField.getText().trim());
    }
    if (this.controlPlanNumjTextField.getText() != null) {
      technics.setControlPlanNum(controlPlanNumjTextField.getText().trim());
    }

    if (this.femaNumjTextField.getSelectedItem() != null) {
      technics.setFemaNum(femaNumjTextField.getSelectedItem().toString().trim());
    }

    if (this.taskInstructNumjTextField.getText() != null) {
      technics.setTaskInstructNum(taskInstructNumjTextField.getText().
                                  trim());
    }

  }

  /**
   * ���ò鿴ģʽ
   */
  public void setViewMode(QMTechnicsIfc technics) {
    processFlowNumDispJLabel.setVisible(true);
    controlPlanNumDispJLabel.setVisible(true);
    femaNumDispJLabel.setVisible(true);
    taskInstructNumDispJLabel.setVisible(true);
    processFlowNumjTextField.setVisible(false);
    controlPlanNumjTextField.setVisible(false);
    femaNumjTextField.setVisible(false);
    taskInstructNumjTextField.setVisible(false);
    processFlowNumDispJLabel.setText(technics.getProcessFlowNum());
    controlPlanNumDispJLabel.setText(technics.getControlPlanNum());
    femaNumDispJLabel.setText(technics.getFemaNum());
    taskInstructNumDispJLabel.setText(technics.getTaskInstructNum());

  }

  /**
   * ���ø���ģʽ
   * @param technics QMTechnicsIfc ��������Ϣ����
   */
  public void setUpdateMode(QMTechnicsIfc technics) {
    processFlowNumDispJLabel.setVisible(false);
    controlPlanNumDispJLabel.setVisible(false);
    femaNumDispJLabel.setVisible(false);
    taskInstructNumDispJLabel.setVisible(false);
    processFlowNumjTextField.setVisible(true);
    controlPlanNumjTextField.setVisible(true);
    femaNumjTextField.setVisible(true);
    taskInstructNumjTextField.setVisible(true);

    processFlowNumjTextField.setText(technics.getProcessFlowNum());
    controlPlanNumjTextField.setText(technics.getControlPlanNum());
//    System.out.println("3333333344444444============"+femaNumjTextField.getSelectedItem());
//    System.out.println("22222222222============"+technics.getFemaNum());
//    femaNumjTextField.setSelectedItem(technics.getFemaNum());
  //CCBegin by chudaming 20091103
    //CCBegin SS1
   // femaNumjTextField.setSelectedItem(technics.getFemaNum().toString());
    femaNumjTextField.setSelectedItem(technics.getFemaNum());
    //CCEnd SS1
  //CCEnd by chudaming 20091103
//    System.out.println("33333333333333============"+femaNumjTextField.getSelectedItem());
//    System.out.println("4444444444444============"+technics.getFemaNum());
    taskInstructNumjTextField.setText(technics.getTaskInstructNum());

  }

  /**
   * ���ô���ģʽ
   */
  public void setCreateMode() {
    processFlowNumDispJLabel.setVisible(false);
    controlPlanNumDispJLabel.setVisible(false);
    femaNumDispJLabel.setVisible(false);
    taskInstructNumDispJLabel.setVisible(false);
    processFlowNumjTextField.setVisible(true);
    controlPlanNumjTextField.setVisible(true);
    femaNumjTextField.setVisible(true);
    taskInstructNumjTextField.setVisible(true);

  }

  /**
   * �������Ϊģʽ
   * @param technics QMTechnicsIfc
   */
  public void setSaveAsMode(QMTechnicsIfc technics) {
    setUpdateMode(technics);
  }

}
