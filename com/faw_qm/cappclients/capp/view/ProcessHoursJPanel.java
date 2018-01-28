/** 生成程序ProcessHoursJPanel.java	1.1  2003/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 解放工时默认为秒和变速箱工时默认为分leixiao 2013-9-11
 * SS2 修改服务平台问题A034-2014-0114 郭晓亮 2014-07-07
 * SS3 解放的加工工时和辅助工时，在更新时还是分，要改成秒。 liunan 2014-11-3
 * SS4 成都加定员信息 guoxiaoliang 2016-7-25
 * SS5 成都去掉单件工时 guoxiaoliang 2016-11-30
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
 * <p>Title: 工时统计面板</p>
 * <p>单件工时等于加工工时加上辅助工时</p>
 * <p>有关工时信息维护:如果选择了关联工艺，则工时信息不能维护。
 * <p>如果工序下有工步节点，且工步的工时信息不为“0”或空，则工序中的工时信息应为</p>
 * <p>工步工时的和，而不以工序中手工录入的值为准。</p>
 * <p>如果工序中包含工步，如果工序中工时为空或“0”，则工步中工时信息可维护，</p>
 * <p>工序中工时是工步的合计。如果工序中工时不为空或“0”，工步中工时信息不可维护。</p>
 * <p>如果工序中不包含任何工步，工序工时信息可维护。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 * @version 1.0
 *（1）20060818 薛静修改，修改方法：setEditMode，原因有空指针异常
 */

public class ProcessHoursJPanel extends JPanel
{

    /**加工工时*/
    private JTextField machineHourJTextField = new JTextField();
    private JLabel machineHourJLabel = new JLabel();
    private JComboBox machineHourJComboBox = new JComboBox();
    private JLabel machineHourDisJLabel = new JLabel();
    private JLabel machineUnitJLabel = new JLabel();


    /**辅助工时*/
    private JLabel aidTimeJLabel = new JLabel();
    private JTextField aidTimeJTextField = new JTextField();
    private JComboBox aidTimeJComboBox = new JComboBox();
    private JLabel aidTimeDisJLabel = new JLabel();
    private JLabel aidUnitJLabel = new JLabel();


    /**单件工时*/
    private JLabel partProcessHourJLabel = new JLabel();
    private JLabel partHourDisLabel = new JLabel();
    private JLabel unitJLabel = new JLabel();


    /**时间单位*/
    //CCBegin SS3
    private String second = "second";
    //CCEnd SS3
    private String minute = "minute";
    //private String hour = "hour";


    /**标记加工工时*/
    private double f1 = 0;


    /**标记辅助工时*/
    private double f2 = 0;


    /**标记单件工时*/
    private double f3 = 0;


    /**用于标记资源文件路径*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**工序（步）对象*/
    private QMProcedureInfo procedureInfo;


    /**标记界面模式*/
    private String mode = "";


    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    private JFrame parentFrame;

//CCBegin SS1
    /**
     * 构造函数
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
     * 初始化
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
        unitJLabel.setText("分");
        }
        else{
        unitJLabel.setText("秒");	
        }
        machineUnitJLabel.setMaximumSize(new Dimension(18, 18));
        machineUnitJLabel.setMinimumSize(new Dimension(18, 18));
        machineUnitJLabel.setPreferredSize(new Dimension(18, 18));
        if(isbsx){
        machineUnitJLabel.setText("分");
        }
        else{
        machineUnitJLabel.setText("秒");	
        }
        aidUnitJLabel.setMaximumSize(new Dimension(18, 18));
        aidUnitJLabel.setMinimumSize(new Dimension(18, 18));
        aidUnitJLabel.setPreferredSize(new Dimension(18, 18));
        if(isbsx){
        aidUnitJLabel.setText("分");
        }
        else{
        aidUnitJLabel.setText("秒");		
        }
        //partHourDisLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        partHourDisLabel.setMaximumSize(new Dimension(100, 22));
        partHourDisLabel.setMinimumSize(new Dimension(100, 22));
        partHourDisLabel.setPreferredSize(new Dimension(100, 22));
        partHourDisLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        partProcessHourJLabel.setText("单件工时");
        partProcessHourJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        partProcessHourJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        machineHourJLabel.setText("加工工时");
        machineHourJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        machineHourJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        //CCBegin SS4
        if(getUserFromCompany().equals("cd")){
        	
             aidTimeJLabel.setText("定员");
         
        }else{
        	
        	 aidTimeJLabel.setText("辅助工时");
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
        //添加替换组件（用于查看状态）
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
     * 判断用户所属公司
     * @return String 获得用户所属公司
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
     * 辅助工时的值发生改变时，激发本方法
     * @param e
     */
    void aidTimeJTextField_caretUpdateForCd(FocusEvent e) {

      //判断值域
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
            JOptionPane.showMessageDialog(parentFrame, "定员应在0-9999之间,请重新输入",
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
          JOptionPane.showMessageDialog(parentFrame, "定员应为整数值,请重新输入", title,
                                        JOptionPane.INFORMATION_MESSAGE);
          aidTimeJTextField.grabFocus();
          return;
        } //值域判断结束

      }

    }
    
  	//CCEnd SS4
    
    
    /**
     * 界面信息本地化
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
     * 设置界面为创建模式
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
     * 设置界面为查看模式，并将工序（工步）的各工时显示在相应界面组件上
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
     * 设置界面为可维护模式，并将工序（工步）的各工时显示在相应界面组件上
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
        //20060818薛静修改，原因：此处有空指针异常
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
     * 获得界面模式
     * @return "EDIT"或"VIEW"
     */
    public String getMode()
    {
        return mode;
    }


    /**
     * 设置工序（工步）值对象
     * @param info
     */
    public void setProcedure(QMProcedureInfo info)
    {
        procedureInfo = info;
    }


    /**
     * 获得工序（工步）值对象
     * @return
     */
    public QMProcedureInfo getProcedure()
    {
        return procedureInfo;
    }


    /**
     * 计算工序（工步）的单件工时,并将单件工时显示在界面上
     * <p>本方法仅适用于新建和更新模式下</p>
     */
    public void calculate()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.ProcessHoursJPanel.calculate() begin...");
            //计算加工工时
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

        //计算辅助工时
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
        //计算单件工时
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
     * 设置工序（步）的加工工时、辅助工时和单件工时属性
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
            System.out.println("各工时: " + getProcedure().getMachineHour() + "  " +
                               getProcedure().getAidTime() + "  "
                               + getProcedure().getStepHour() + "");
            System.out.println(
                    "cappclients.capp.view.ProcessHoursJPanel.setHours() end...return is void");
        }
    }


    /**
     * 设置辅助工时为指定值，并显示在相应界面组件上
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
     * 设置机加工时为指定值，并显示在相应界面组件上
     * @param machineHour
     */
    public void setMachineHour(double machineHour)
    {
        f1 = machineHour;
        machineHourJTextField.setText(String.valueOf(machineHour));
        machineHourDisJLabel.setText(String.valueOf(machineHour));
    }


    /**
     * 设置单件工时为指定值，并显示在相应界面组件上
     * @param partProcessHour
     */
    public void setPartProcessHour(double partProcessHour)
    {
        f3 = partProcessHour;
        partHourDisLabel.setText(String.valueOf(partProcessHour));
    }


    /**
     * 获得当前的加工工时
     * @return 加工工时
     */
    public double getMachineHour()
    {
        return f1;
    }


    /**
     * 获得当前的辅助工时
     * @return 辅助工时
     */
    public double getAidTime()
    {
        return f2;
    }


    /**
     * 获得当前的单件工时
     * @return 单件工时
     */
    public double getPartProcessHour()
    {
        return f3;
    }


    /**
     * 清空界面数据
     */
    public void clear()
    {
        machineHourJTextField.setText("");
        aidTimeJTextField.setText("");
        machineHourDisJLabel.setText("");
        aidTimeDisJLabel.setText("");
        partHourDisLabel.setText("");
        machineHourJComboBox.setSelectedItem("分");
        aidTimeJComboBox.setSelectedItem("分");
    }


    /**
     * 加工工时的单位发生改变时，激发本方法
     * @param e
     */
    void machineHourJComboBox_itemStateChanged(ItemEvent e)
    {
        calculate();
    }


    /**
     * 辅助工时的单位发生改变时，激发本方法
     * @param e
     */
    void aidTimeJComboBox_itemStateChanged(ItemEvent e)
    {
        calculate();
    }


    /**
     * 加工工时的值发生改变时，激发本方法
     * @param e
     */
    void machineHourJTextField_caretUpdate(FocusEvent e)
    {

        //判断值域
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
            } //值域判断结束

        }
        calculate();
    }


    /**
     * 辅助工时的值发生改变时，激发本方法
     * @param e
     */
    void aidTimeJTextField_caretUpdate(FocusEvent e)
    {

        //判断值域
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
            } //值域判断结束

        }
        calculate();
    }


    /**
     * 设置组件状态
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
     * 判断指定的字符串是否是整型
     * @param s
     * @return 如果是整型，则返回真；否则返回假，并提示。
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
