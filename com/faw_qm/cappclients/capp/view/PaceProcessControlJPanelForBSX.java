package com.faw_qm.cappclients.capp.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.TermTextField;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.util.QMMessage;

public class PaceProcessControlJPanelForBSX extends ParentJPanel{
	
	  private JLabel paceNumberJLabel = new JLabel();
	  public CappTextField paceNumJTextField;
	  private JLabel paceNameJLabel = new JLabel();
	  public CappTextField paceNameJTextField;
	  private JLabel insertJLabel=new JLabel();
	  public JCheckBox insertCheckBox=new JCheckBox();
	  
	  
	 
	  public CappSortingSelectedPanel stepClassifiSortingSelectedPanel = null;
	  private CodingIfc stepType;
	  /**工时统计面板*/
	  public ProcessHoursJPanel processHoursJPanel;
	  
	  
	  private GridBagLayout gridBagLayout6 = new GridBagLayout();
	  
	  private JFrame parentFrame;
	  
	  public PaceProcessControlJPanelForBSX(JFrame pFrame) {
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
		   
		   String processType = QMMessage.getLocalizedMessage(RESOURCE,
	                CappLMRB.PROCESSTYPE, null);
		   
		   
		   paceNumberJLabel.setMaximumSize(new Dimension(41, 15));
		   paceNumberJLabel.setMinimumSize(new Dimension(41, 15));
		   paceNumberJLabel.setPreferredSize(new Dimension(41, 15));
		   paceNumberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		   paceNumberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		   paceNumberJLabel.setText("*工步号");
		   paceNumJTextField = new CappTextField(parentFrame, procedureNumDisp, 5, false);
		   paceNumJTextField.setMaximumSize(new Dimension(6, 24));
	        
	        
	        
		   paceNameJLabel.setMaximumSize(new Dimension(53, 22));
		   paceNameJLabel.setMinimumSize(new Dimension(53, 22));
		   paceNameJLabel.setPreferredSize(new Dimension(53, 22));
		   paceNameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		   paceNameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		   paceNameJLabel.setText("*工步名称");
		   paceNameJTextField = new CappTextField(parentFrame, procedureNumDisp1, 10, false);
		   paceNameJTextField.setMaximumSize(new Dimension(6, 24));
		   
		    
		   insertJLabel.setMaximumSize(new Dimension(53, 22));
		   insertJLabel.setMinimumSize(new Dimension(53, 22));
		   insertJLabel.setPreferredSize(new Dimension(53, 22));
		   insertJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		   insertJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		   insertJLabel.setText("插入工步");
		   insertCheckBox.setSelected(true);
		   insertCheckBox.setMaximumSize(new Dimension(6, 24));
		    
			
			
			processHoursJPanel = new ProcessHoursJPanel(parentFrame,true);
			
			
			this.setMinimumSize(new Dimension(600, 90));
			this.setPreferredSize(new Dimension(600, 125));
			this.setLayout(gridBagLayout6);
			
			
		    add(paceNumberJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	                , GridBagConstraints.EAST, GridBagConstraints.NONE,
	                new Insets(0, 21, 0, 0), 0, 0));
		    add(paceNumJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
	                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
	                new Insets(0, 8, 0, 0), 0, 0));
		    
		    
		    
		    add(paceNameJLabel,
                    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                   ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 21, 0, 0), 0, 0));
		    add(paceNameJTextField,  new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
		            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 0), 0, 0));
		    
		    
		    add(insertJLabel,   new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
		            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
		    add(insertCheckBox,
                    new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0
                    ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
		    
		    
		    
           add(processHoursJPanel,
                   new GridBagConstraints(0, 1, 4, 1, 1.0, 0.0
                                          , GridBagConstraints.NORTH,
                                          GridBagConstraints.HORIZONTAL,
                                          new Insets(7, 13, 0, 7), 0, 0));
          

           processHoursJPanel.setEnabled(true);
           processHoursJPanel.setCreateMode();
		    
	  }
	
	

}
