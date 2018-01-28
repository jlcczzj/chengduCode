/** ���ɳ���ProcessHoursJPanel.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ��Ź�ʱĬ��Ϊ��ͱ����乤ʱĬ��Ϊ��leixiao 2013-9-11
 * SS2 �޸ķ���ƽ̨����A034-2014-0114 ������ 2014-07-07
 * SS3 ��ŵļӹ���ʱ�͸�����ʱ���ڸ���ʱ���Ƿ֣�Ҫ�ĳ��롣 liunan 2014-11-3
 * SS4 �ɶ��Ӷ�Ա��Ϣ guoxiaoliang 2016-7-25
 * SS5 �ɶ�ȥ��������ʱ guoxiaoliang 2016-11-30
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.util.QMMessage;


/**
 * <p>Title: ��ʱͳ�����</p>
 * <p>������ʱ���ڼӹ���ʱ���ϸ�����ʱ</p>
 * <p>�йع�ʱ��Ϣά��:���ѡ���˹������գ���ʱ��Ϣ����ά����
 * <p>����������й����ڵ㣬�ҹ����Ĺ�ʱ��Ϣ��Ϊ��0����գ������еĹ�ʱ��ϢӦΪ</p>
 * <p>������ʱ�ĺͣ������Թ������ֹ�¼���ֵΪ׼��</p>
 * <p>��������а�����������������й�ʱΪ�ջ�0�����򹤲��й�ʱ��Ϣ��ά����</p>
 * <p>�����й�ʱ�ǹ����ĺϼơ���������й�ʱ��Ϊ�ջ�0���������й�ʱ��Ϣ����ά����</p>
 * <p>��������в������κι���������ʱ��Ϣ��ά����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 *��1��20060818 Ѧ���޸ģ��޸ķ�����setEditMode��ԭ���п�ָ���쳣
 */

public class ProcessHoursJPanel extends JPanel
{

    /**�ӹ���ʱ*/
    private JTextField machineHourJTextField = new JTextField();
    private JLabel machineHourJLabel = new JLabel();
    private JComboBox machineHourJComboBox = new JComboBox();
    private JLabel machineHourDisJLabel = new JLabel();
    private JLabel machineUnitJLabel = new JLabel();


    /**������ʱ*/
    private JLabel aidTimeJLabel = new JLabel();
    private JTextField aidTimeJTextField = new JTextField();
    private JComboBox aidTimeJComboBox = new JComboBox();
    private JLabel aidTimeDisJLabel = new JLabel();
    private JLabel aidUnitJLabel = new JLabel();


    /**������ʱ*/
    private JLabel partProcessHourJLabel = new JLabel();
    private JLabel partHourDisLabel = new JLabel();
    private JLabel unitJLabel = new JLabel();


    /**ʱ�䵥λ*/
    //CCBegin SS3
    private String second = "second";
    //CCEnd SS3
    private String minute = "minute";
    //private String hour = "hour";


    /**��Ǽӹ���ʱ*/
    private double f1 = 0;


    /**��Ǹ�����ʱ*/
    private double f2 = 0;


    /**��ǵ�����ʱ*/
    private double f3 = 0;


    /**���ڱ����Դ�ļ�·��*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**���򣨲�������*/
    private QMProcedureInfo procedureInfo;


    /**��ǽ���ģʽ*/
    private String mode = "";


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    private JFrame parentFrame;

//CCBegin SS1
    /**
     * ���캯��
     */
    public ProcessHoursJPanel(JFrame parent)
    {
        parentFrame = parent;
        try
        {
            jbInit(false);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    
    }
    
    public ProcessHoursJPanel(JFrame parent,boolean isbsx)
    {
        parentFrame = parent;
        try
        {
            jbInit(isbsx);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * ��ʼ��
     * @throws Exception
     */
    void jbInit(boolean isbsx)
            throws Exception
    {
    	
        unitJLabel.setMaximumSize(new Dimension(20, 18));
        unitJLabel.setMinimumSize(new Dimension(20, 18));
        unitJLabel.setPreferredSize(new Dimension(20, 18));
        unitJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        if(isbsx){
        unitJLabel.setText("��");
        }
        else{
        unitJLabel.setText("��");	
        }
        machineUnitJLabel.setMaximumSize(new Dimension(18, 18));
        machineUnitJLabel.setMinimumSize(new Dimension(18, 18));
        machineUnitJLabel.setPreferredSize(new Dimension(18, 18));
        if(isbsx){
        machineUnitJLabel.setText("��");
        }
        else{
        machineUnitJLabel.setText("��");	
        }
        aidUnitJLabel.setMaximumSize(new Dimension(18, 18));
        aidUnitJLabel.setMinimumSize(new Dimension(18, 18));
        aidUnitJLabel.setPreferredSize(new Dimension(18, 18));
        if(isbsx){
        aidUnitJLabel.setText("��");
        }
        else{
        aidUnitJLabel.setText("��");		
        }
        //partHourDisLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        partHourDisLabel.setMaximumSize(new Dimension(100, 22));
        partHourDisLabel.setMinimumSize(new Dimension(100, 22));
        partHourDisLabel.setPreferredSize(new Dimension(100, 22));
        partHourDisLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        partProcessHourJLabel.setText("������ʱ");
        partProcessHourJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        partProcessHourJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        machineHourJLabel.setText("�ӹ���ʱ");
        machineHourJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        machineHourJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        //CCBegin SS4
        if(getUserFromCompany().equals("cd")){
        	
             aidTimeJLabel.setText("��Ա");
         
        }else{
        	
        	 aidTimeJLabel.setText("������ʱ");
        }
        //CCEnd SS4
        
        aidTimeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        aidTimeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        setLayout(gridBagLayout1);
        setMinimumSize(new Dimension(337, 22));
        setSize(new Dimension(500, 25));

        machineHourJTextField.addFocusListener(new FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
                machineHourJTextField_caretUpdate(e);
            }
        });
        aidTimeJTextField.addFocusListener(new FocusAdapter()
        {
            public void focusLost(FocusEvent e)
            {
            	//CCBegin SS4
            	 try {
					if(ProcessHoursJPanel.this.getUserFromCompany().equals("cd")){
						 
						aidTimeJTextField_caretUpdateForCd(e);
						
					}else{
					
					
					   aidTimeJTextField_caretUpdate(e);
					}
				} catch (QMException e1) {
					e1.printStackTrace();
				}
				
				//CCEnd SS4
            }
        });
        machineHourJTextField.setMaximumSize(new Dimension(60, 22));
        machineHourJTextField.setMinimumSize(new Dimension(60, 22));
        machineHourJTextField.setPreferredSize(new Dimension(60, 22));
        aidTimeJTextField.setMaximumSize(new Dimension(60, 22));
        aidTimeJTextField.setMinimumSize(new Dimension(60, 22));
        aidTimeJTextField.setPreferredSize(new Dimension(60, 22));
        add(machineHourJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
        add(machineHourJComboBox, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        add(machineHourJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        add(aidTimeJLabel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.EAST,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(0, 15, 0, 0), 0, 0));
        add(aidTimeJTextField, new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
        
        //CCBegin SS4
        if(!getUserFromCompany().equals("cd")){
        	
           add(aidTimeJComboBox, new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        }
        
        //CCEnd SS4
        //CCBegin SS5
        if(!getUserFromCompany().equals("cd")){
        add(partProcessHourJLabel, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 15, 0, 0), 0, 0));
        }
        //CCEnd SS5
        add(unitJLabel, new GridBagConstraints(8, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.EAST,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(0, 4, 0, 0), 0, 0));
        //CCBegin SS5
        if(!getUserFromCompany().equals("cd")){
        add(partHourDisLabel, new GridBagConstraints(7, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 8, 0, 0), 0, 0));
        }
        //CCEnd SS5
        //����滻��������ڲ鿴״̬��
        add(machineHourDisJLabel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.BOTH,
                new Insets(0, 8, 0, 0), 60, 0));
        add(machineUnitJLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 8, 0, 0), 0, 0));
        add(aidTimeDisJLabel, new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.BOTH,
                new Insets(0, 8, 0, 0), 60, 0));
        add(aidUnitJLabel, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.WEST,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(0, 8, 0, 0), 0, 0));

        //CCBegin SS3
        //localize();
        localize(isbsx);
        //CCEnd SS3
    }
  //CCEnd SS1

    
  //CCBegin SS4
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
    
    
    
    /**
     * ������ʱ��ֵ�����ı�ʱ������������
     * @param e
     */
    void aidTimeJTextField_caretUpdateForCd(FocusEvent e) {

      //�ж�ֵ��
      String aidHourString = aidTimeJTextField.getText().trim();
      if (aidHourString != null && !aidHourString.equals("")) {
        try {
          double value = isDoubleValue(aidHourString);
          if (value > 9999 || value < 0) {
            aidTimeJTextField.setText("0.0");
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                "106", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                "107", null);
            JOptionPane.showMessageDialog(parentFrame, "��ԱӦ��0-9999֮��,����������",
                                          title,
                                          JOptionPane.WARNING_MESSAGE);
            return;
          }

        }
        catch (NumberFormatException ex) {
          aidTimeJTextField.setText("0.0");
          String title = QMMessage.getLocalizedMessage(RESOURCE,
              "information", null);
          String message = QMMessage.getLocalizedMessage(RESOURCE,
              CappLMRB.HOURS_TYPE_INVALID, null);
          JOptionPane.showMessageDialog(parentFrame, "��ԱӦΪ����ֵ,����������", title,
                                        JOptionPane.INFORMATION_MESSAGE);
          aidTimeJTextField.grabFocus();
          return;
        } //ֵ���жϽ���

      }

    }
    
  	//CCEnd SS4
    
    
    /**
     * ������Ϣ���ػ�
     */
    //CCBegin SS3
    //private void localize()
    private void localize(boolean isbsx)
    {

        second = QMMessage.getLocalizedMessage(RESOURCE, "SECOND", null);
    //CCEnd SS3
        minute = QMMessage.getLocalizedMessage(RESOURCE, "MINUTE", null);
       // hour = QMMessage.getLocalizedMessage(RESOURCE, "HOUR", null);
        aidTimeJComboBox.setMaximumSize(new Dimension(50, 22));
        aidTimeJComboBox.setMinimumSize(new Dimension(50, 22));
        aidTimeJComboBox.setPreferredSize(new Dimension(50, 22));
        machineHourJComboBox.setMaximumSize(new Dimension(50, 22));
        machineHourJComboBox.setMinimumSize(new Dimension(50, 22));
        machineHourJComboBox.setPreferredSize(new Dimension(50, 22));
        //machineHourJComboBox.addItem(second);
        //CCBegin SS3
        //machineHourJComboBox.addItem(minute);
        if(isbsx){
        machineHourJComboBox.addItem(minute);
        }
        else{
        machineHourJComboBox.addItem(second);
        }
        //machineHourJComboBox.addItem(hour);
        //aidTimeJComboBox.addItem(second);
        //aidTimeJComboBox.addItem(minute);
        if(isbsx){
        aidTimeJComboBox.addItem(minute);
        }
        else{
        aidTimeJComboBox.addItem(second);
        }
        //CCEnd SS3
        //aidTimeJComboBox.addItem(hour);

        machineHourJComboBox.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                machineHourJComboBox_itemStateChanged(e);
            }
        });
        aidTimeJComboBox.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                aidTimeJComboBox_itemStateChanged(e);
            }
        });
    }


    /**
     * ���ý���Ϊ����ģʽ
     */
    public void setCreateMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.ProcessHoursJPanel.setCreateMode() begin...");
        }
        System.out.println("ffffffffffffffffff");
        machineHourJTextField.setVisible(true);
        machineHourJComboBox.setVisible(true);
        aidTimeJTextField.setVisible(true);
        aidTimeJComboBox.setVisible(true);
        machineHourDisJLabel.setVisible(false);
        machineUnitJLabel.setVisible(false);
        aidTimeDisJLabel.setVisible(false);
        aidUnitJLabel.setVisible(false);

        machineHourJTextField.setText("");
        aidTimeJTextField.setText("");
        partHourDisLabel.setText("");
        partHourDisLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        mode = "EDIT";
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.ProcessHoursJPanel.setCreateMode() end...return is void");
        }
    }


    /**
     * ���ý���Ϊ�鿴ģʽ���������򣨹������ĸ���ʱ��ʾ����Ӧ���������
     */
    public void setViewMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.ProcessHoursJPanel.setViewMode() begin...");
        }
        //System.out.println("dddddddddddddddddddddddddddddd");
        machineHourJTextField.setVisible(false);
        machineHourJComboBox.setVisible(false);
        aidTimeJTextField.setVisible(false);
        aidTimeJComboBox.setVisible(false);
        machineHourDisJLabel.setVisible(true);
        machineUnitJLabel.setVisible(true);
        aidTimeDisJLabel.setVisible(true);
        aidUnitJLabel.setVisible(true);

        machineHourDisJLabel.setText(String.valueOf(getProcedure().
                getMachineHour()));
        aidTimeDisJLabel.setText(String.valueOf(getProcedure().getAidTime()));
        partHourDisLabel.setText(String.valueOf(getProcedure().getStepHour()));
        partHourDisLabel.setBorder(null);
        mode = "VIEW";
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.ProcessHoursJPanel.setViewMode() end...return is void");
        }
    }


    /**
     * ���ý���Ϊ��ά��ģʽ���������򣨹������ĸ���ʱ��ʾ����Ӧ���������
     */
    public void setEditMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.ProcessHoursJPanel.setUpdateAndViewMode() begin...");
        }
        setEnabled(true);
//        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvv");
        machineHourJTextField.setVisible(true);
        machineHourJComboBox.setVisible(true);
        aidTimeJTextField.setVisible(true);
        aidTimeJComboBox.setVisible(true);
        machineHourDisJLabel.setVisible(false);
        machineUnitJLabel.setVisible(false);
        aidTimeDisJLabel.setVisible(false);
        aidUnitJLabel.setVisible(false);
        //20060818Ѧ���޸ģ�ԭ�򣺴˴��п�ָ���쳣
        if(getProcedure()!=null)
        {
            machineHourJTextField.setText(String.valueOf(getProcedure().
                    getMachineHour()));
            aidTimeJTextField.setText(String.valueOf(getProcedure().getAidTime()));
            partHourDisLabel.setText(String.valueOf(getProcedure().getStepHour()));
        }
        partHourDisLabel.setBorder(null);
        mode = "EDIT";

        if (verbose)
        {
            System.out.println("cappclients.capp.view.ProcessHoursJPanel.setUpdateAndViewMode() end...return is void");
        }
    }


    /**
     * ��ý���ģʽ
     * @return "EDIT"��"VIEW"
     */
    public String getMode()
    {
        return mode;
    }


    /**
     * ���ù��򣨹�����ֵ����
     * @param info
     */
    public void setProcedure(QMProcedureInfo info)
    {
        procedureInfo = info;
    }


    /**
     * ��ù��򣨹�����ֵ����
     * @return
     */
    public QMProcedureInfo getProcedure()
    {
        return procedureInfo;
    }


    /**
     * ���㹤�򣨹������ĵ�����ʱ,����������ʱ��ʾ�ڽ�����
     * <p>���������������½��͸���ģʽ��</p>
     */
    public void calculate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.ProcessHoursJPanel.calculate() begin...");
            //����ӹ���ʱ
        }
        String machineHourString = machineHourJTextField.getText().trim();
        double machineHourF = 0;
        if (!machineHourString.equals("") && machineHourString != null)
        {
            machineHourF = Double.valueOf(machineHourString).doubleValue();
        }
        else
        {
            machineHourF = 0;
        }
//        String machineHourUnit = machineHourJComboBox.getSelectedItem().
//                                 toString();
//        double machineHour = 0;
//        if (machineHourUnit == second)
//        {
//            machineHour = machineHourF;
//        }
//        if (machineHourUnit == minute)
//        {
//            machineHour = (machineHourF) * 60;
//        }
//        if (machineHourUnit == hour)
//        {
//            machineHour = (machineHourF) * 3600;
//        }

        f1 = machineHourF;

        //���㸨����ʱ
        String aidTimeString = aidTimeJTextField.getText().trim();

        double aidTimeF = 0;
        if (aidTimeString != null && !aidTimeString.equals(""))
        {
            aidTimeF = Double.valueOf(aidTimeString).doubleValue();
        }
        else
        {
            aidTimeF = 0;
        }
//        String aidTimeUnit = aidTimeJComboBox.getSelectedItem().toString();
//        double aidTime = 0;
//        if (aidTimeUnit == second)
//        {
//            aidTime = aidTimeF;
//        }
//        if (aidTimeUnit == minute)
//        {
//            aidTime = (aidTimeF) * 60;
//        }
//        if (aidTimeUnit == hour)
//        {
//            aidTime = (aidTimeF) * 3600;
//        }

        f2 = aidTimeF;
        System.out.println(f1+"=====aaa====="+f2);
        //���㵥����ʱ
        double partHour = Math.round((machineHourF + aidTimeF) * 10000000000d) /
                          10000000000d;
        f3 = partHour;

        partHourDisLabel.setText(String.valueOf(partHour));
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.ProcessHoursJPanel.calculate() end...return is void");
        }
    }


    /**
     * ���ù��򣨲����ļӹ���ʱ��������ʱ�͵�����ʱ����
     */
    public void setHours()
            throws QMRemoteException
    {

        calculate();
        getProcedure().setMachineHour(f1);
        getProcedure().setAidTime(f2);
        
        System.out.println(f1+"=====ccc====="+f2);
        double partHour = Math.round(f3 * 10000000000d) /
                          10000000000d;
        getProcedure().setStepHour(f3);
        if (verbose)
        {
            System.out.println("����ʱ: " + getProcedure().getMachineHour() + "  " +
                               getProcedure().getAidTime() + "  "
                               + getProcedure().getStepHour() + "");
            System.out.println(
                    "cappclients.capp.view.ProcessHoursJPanel.setHours() end...return is void");
        }
    }


    /**
     * ���ø�����ʱΪָ��ֵ������ʾ����Ӧ���������
     * @param aidTime
     */
    public void setAidTime(double aidTime)
    {
        f2 = aidTime;
        System.out.println(f1+"=====bbb====="+f2);
        aidTimeJTextField.setText(String.valueOf(aidTime));
        aidTimeDisJLabel.setText(String.valueOf(aidTime));
    }


    /**
     * ���û��ӹ�ʱΪָ��ֵ������ʾ����Ӧ���������
     * @param machineHour
     */
    public void setMachineHour(double machineHour)
    {
        f1 = machineHour;
        machineHourJTextField.setText(String.valueOf(machineHour));
        machineHourDisJLabel.setText(String.valueOf(machineHour));
    }


    /**
     * ���õ�����ʱΪָ��ֵ������ʾ����Ӧ���������
     * @param partProcessHour
     */
    public void setPartProcessHour(double partProcessHour)
    {
        f3 = partProcessHour;
        partHourDisLabel.setText(String.valueOf(partProcessHour));
    }


    /**
     * ��õ�ǰ�ļӹ���ʱ
     * @return �ӹ���ʱ
     */
    public double getMachineHour()
    {
        return f1;
    }


    /**
     * ��õ�ǰ�ĸ�����ʱ
     * @return ������ʱ
     */
    public double getAidTime()
    {
        return f2;
    }


    /**
     * ��õ�ǰ�ĵ�����ʱ
     * @return ������ʱ
     */
    public double getPartProcessHour()
    {
        return f3;
    }


    /**
     * ��ս�������
     */
    public void clear()
    {
        machineHourJTextField.setText("");
        aidTimeJTextField.setText("");
        machineHourDisJLabel.setText("");
        aidTimeDisJLabel.setText("");
        partHourDisLabel.setText("");
        machineHourJComboBox.setSelectedItem("��");
        aidTimeJComboBox.setSelectedItem("��");
    }


    /**
     * �ӹ���ʱ�ĵ�λ�����ı�ʱ������������
     * @param e
     */
    void machineHourJComboBox_itemStateChanged(ItemEvent e)
    {
        calculate();
    }


    /**
     * ������ʱ�ĵ�λ�����ı�ʱ������������
     * @param e
     */
    void aidTimeJComboBox_itemStateChanged(ItemEvent e)
    {
        calculate();
    }


    /**
     * �ӹ���ʱ��ֵ�����ı�ʱ������������
     * @param e
     */
    void machineHourJTextField_caretUpdate(FocusEvent e)
    {

        //�ж�ֵ��
        String machineHourString = machineHourJTextField.getText().trim();
        if (machineHourString != null && !machineHourString.equals(""))
        {
            try
            {
                double value = isDoubleValue(machineHourString);

                if (value > 9999999.999 || value < 0)
                {
                    machineHourJTextField.setText("0.0");
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "106", null);
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "107", null);
                    JOptionPane.showMessageDialog(parentFrame, message,
                                                  title,
                                                  JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            catch (NumberFormatException ex)
            {
                machineHourJTextField.setText("0.0");
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.HOURS_TYPE_INVALID, null);
                JOptionPane.showMessageDialog(parentFrame, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
                machineHourJTextField.grabFocus();
                return;
            } //ֵ���жϽ���

        }
        calculate();
    }


    /**
     * ������ʱ��ֵ�����ı�ʱ������������
     * @param e
     */
    void aidTimeJTextField_caretUpdate(FocusEvent e)
    {

        //�ж�ֵ��
        String aidHourString = aidTimeJTextField.getText().trim();
        if (aidHourString != null && !aidHourString.equals(""))
        {
            try
            {
                double value = isDoubleValue(aidHourString);
                if (value > 9999999.999 || value < 0)
                {
                    aidTimeJTextField.setText("0.0");
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "106", null);
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "107", null);
                    JOptionPane.showMessageDialog(parentFrame, message,
                                                  title,
                                                  JOptionPane.WARNING_MESSAGE);
                    return;
                }

            }
            catch (NumberFormatException ex)
            {
                aidTimeJTextField.setText("0.0");
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.HOURS_TYPE_INVALID, null);
                JOptionPane.showMessageDialog(parentFrame, message, title,
                                              JOptionPane.INFORMATION_MESSAGE);
                aidTimeJTextField.grabFocus();
                return;
            } //ֵ���жϽ���

        }
        calculate();
    }


    /**
     * �������״̬
     * @param flag
     */
    public void setEnabled(boolean flag)
    {
        super.setEnabled(flag);
        machineHourJTextField.setEnabled(flag);
        machineHourJComboBox.setEnabled(flag);
        aidTimeJTextField.setEnabled(flag);
        aidTimeJComboBox.setEnabled(flag);
        machineHourDisJLabel.setEnabled(flag);
        machineUnitJLabel.setEnabled(flag);
        aidTimeDisJLabel.setEnabled(flag);
        aidUnitJLabel.setEnabled(flag);
    }


    public static void main(String[] s)
    {
        JFrame f = new JFrame();
        f.setSize(500, 400);
        ProcessHoursJPanel panel = new ProcessHoursJPanel(f);
        QMProcedureInfo info = null;
        panel.setCreateMode();
        panel.setProcedure(info);
        f.getContentPane().add(panel);
        f.setVisible(true);

    }


    /**
     * �ж�ָ�����ַ����Ƿ�������
     * @param s
     * @return ��������ͣ��򷵻��棻���򷵻ؼ٣�����ʾ��
     */
    public double isDoubleValue(String s)
            throws NumberFormatException
    {
        double doublevalue = Double.parseDouble(s);
        return doublevalue;
    }
    //CCBegin SS2
    public  JTextField getMachineHourJTextField(){
    	
    	return machineHourJTextField;
    }
    
    public  JLabel getPartProcessHourJLabel(){
    	
    	return partHourDisLabel;
    }
    public JTextField getAidTimeJTextField(){
    	
    	return aidTimeJTextField;
    }
    //CCEnd SS2

}
