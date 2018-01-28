package com.faw_qm.cappclients.capp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.TermTextField;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.util.QMMessage;

public class StepProcessControlJPanelForBSX extends ParentJPanel{
	
	
	  public JLabel stepClassiDisJLabel = new JLabel();
	  private JLabel stepKindLabel=new JLabel();
	  public JLabel jgTypeTextField = new JLabel();
	  private JLabel jgTypeLabel=new JLabel();
	  public JLabel stepTypeDisJLabel = new JLabel();
	  private JLabel stepTypeLabel = new JLabel();
	  public TermTextField stepNameTextField;
	  private JLabel stepNameLabel=new JLabel();
	  private JLabel stepCountLabel=new JLabel();
	  public CappTextField stepCountText;
	  public CappTextField stepNumText;
	  private JLabel stepNumLabel=new JLabel();
	  public CappSortingSelectedPanel stepClassifiSortingSelectedPanel = null;
	  public CappSortingSelectedPanel processTypeSortingSelectedPanel = null;
	  private CodingIfc stepType;
	  /**工时统计面板*/
	  public ProcessHoursJPanel processHoursJPanel;
	  
	  
	  private GridBagLayout gridBagLayout6 = new GridBagLayout();
	  
	  private JFrame parentFrame;
	  
	  public StepProcessControlJPanelForBSX(JFrame pFrame) {
			super();
			setLayout(null);
			parentFrame=pFrame;
			jbInit();
		}
	  
	  void jbInit(){
		  
		   String procedureNumDisp1 = QMMessage.
           getLocalizedMessage(RESOURCE,
           "procedureNum1", null);
		   String procedureNumDisp = QMMessage.
           getLocalizedMessage(RESOURCE,
           "procedureNum", null);
		   
		   String title2 = QMMessage.getLocalizedMessage(RESOURCE,
	                CappLMRB.PROCESSTYPE, null);
	        String title3 = QMMessage.getLocalizedMessage(RESOURCE,
	                CappLMRB.STEPCLASSIFI, null);
		   processTypeSortingSelectedPanel = new CappSortingSelectedPanel(title2,
	                "QMProcedure", "processType");
	        stepClassifiSortingSelectedPanel = new CappSortingSelectedPanel(title3,
	                "QMProcedure", "stepClassification");
	        
	        processTypeSortingSelectedPanel.setDialogTitle(title2);
	        stepClassifiSortingSelectedPanel.setDialogTitle(title3);
	        processTypeSortingSelectedPanel.setButtonSize(89, 23);
	        stepClassifiSortingSelectedPanel.setButtonSize(89, 23);

	        processTypeSortingSelectedPanel.setSelectBMnemonic('R');
	        stepClassifiSortingSelectedPanel.setSelectBMnemonic('G');
		   
		   String processType = QMMessage.getLocalizedMessage(RESOURCE,
	                CappLMRB.PROCESSTYPE, null);
//	        processTypeSortingSelectedPanel.setDefaultCoding(processType,
//	                stepType.getCodeContent());
//	        String stepClassi = QMMessage.getLocalizedMessage(RESOURCE,
//	                CappLMRB.STEPCLASSIFI, null);
//	        stepClassifiSortingSelectedPanel.setDefaultCoding(stepClassi,
//	                stepType.getCodeContent());
		   
		   
		    stepCountLabel.setMaximumSize(new Dimension(41, 15));
		    stepCountLabel.setMinimumSize(new Dimension(41, 15));
	        stepCountLabel.setPreferredSize(new Dimension(41, 15));
	        stepCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	        stepCountLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
	        stepCountLabel.setText("*工序号");
	        stepCountText = new CappTextField(parentFrame, procedureNumDisp, 5, false);
	        stepCountText.setMaximumSize(new Dimension(6, 24));
	        
	        
	        
	        stepNumLabel.setMaximumSize(new Dimension(53, 22));
	        stepNumLabel.setMinimumSize(new Dimension(53, 22));
	        stepNumLabel.setPreferredSize(new Dimension(53, 22));
	        stepNumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	        stepNumLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		    stepNumLabel.setText("*工序编号");
		    stepNumText = new CappTextField(parentFrame, procedureNumDisp1, 10, false);
		    stepNumText.setMaximumSize(new Dimension(6, 24));
		   
		    
		    stepNameLabel.setMaximumSize(new Dimension(53, 22));
		    stepNameLabel.setMinimumSize(new Dimension(53, 22));
		    stepNameLabel.setPreferredSize(new Dimension(53, 22));
		    stepNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		    stepNameLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		    stepNameLabel.setText("*工序名称");
		    stepNameTextField = new TermTextField(parentFrame,
                    QMMessage.getLocalizedMessage(
                       RESOURCE, "procedureName", null), 99, false);
		    stepNameTextField.setMaximumSize(new Dimension(6, 24));
		    
		    
		    stepTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		    stepTypeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
	        stepTypeLabel.setText("*工序种类");
	        stepTypeDisJLabel.setMaximumSize(new Dimension(4, 22));
	        stepTypeDisJLabel.setMinimumSize(new Dimension(4, 22));
	        stepTypeDisJLabel.setOpaque(false);
	        stepTypeDisJLabel.setPreferredSize(new Dimension(4, 22));
			
			
			jgTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			jgTypeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
			jgTypeLabel.setText("*加工类型");
			jgTypeTextField.setMaximumSize(new Dimension(4, 22));
			jgTypeTextField.setMinimumSize(new Dimension(4, 22));
			jgTypeTextField.setPreferredSize(new Dimension(4, 22));
			
			
			
			stepKindLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			stepKindLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
			stepKindLabel.setText("工序类别");
			
			 stepClassiDisJLabel.setMaximumSize(new Dimension(4, 22));
		     stepClassiDisJLabel.setMinimumSize(new Dimension(4, 22));
		     stepClassiDisJLabel.setPreferredSize(new Dimension(4, 22));
			
			
			processHoursJPanel = new ProcessHoursJPanel(parentFrame,true);
			
			
			this.setMinimumSize(new Dimension(600, 90));
			this.setPreferredSize(new Dimension(600, 125));
			this.setLayout(gridBagLayout6);
			
			
		    add(stepCountLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	                , GridBagConstraints.EAST, GridBagConstraints.NONE,
	                new Insets(7, 21, 0, 0), 0, 0));
		    add(stepCountText, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
	                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
	                new Insets(7, 8, 0, 0), 0, 0));
		    
		    
		    
		    add(stepNumLabel,
                    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                   ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 21, 0, 0), 0, 0));
		    add(stepNumText,  new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
		            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 8, 0, 0), 0, 0));
		    
		    
		    add(stepNameLabel,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
		            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 9, 0, 0), 0, 0));
		    add(stepNameTextField,
                    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 8, 0, 0), 0, 0));
		    
		    
		    add(stepTypeLabel,
                    new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.EAST,
                                           GridBagConstraints.NONE,
                                           new Insets(7, 9, 0, 0), 0, 0));
		    add(stepTypeDisJLabel,
                    new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.BOTH,
                                           new Insets(7, 8, 0, 0), 0, 0));
		    
           add(stepKindLabel,
                   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
               ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(7, 0, 0, 0), 0, 0));
           
           add(stepClassiDisJLabel,
                   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                 ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(7, 8, 0, 7), 0, 0));
           add(stepClassifiSortingSelectedPanel,
                   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                    ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 0, 2, 8), 0, 0));
		    
           
           add(jgTypeLabel,
                   new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
                                          , GridBagConstraints.EAST,
                                          GridBagConstraints.NONE,
                                          new Insets(7, 9, 0, 0), 0, 0));
		   add(jgTypeTextField,
                   new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
                   ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(7, 8, 0, 0), 0, 0));
          add(processTypeSortingSelectedPanel,
                   new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
                   ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 2, 0), 0, 0));
           add(processHoursJPanel,
                   new GridBagConstraints(0, 3, 4, 1, 1.0, 0.0
                                          , GridBagConstraints.NORTH,
                                          GridBagConstraints.HORIZONTAL,
                                          new Insets(7, 13, 0, 7), 0, 0));
          

           processHoursJPanel.setEnabled(true);
           processHoursJPanel.setCreateMode();
		    
	  }
	
	

	

}
