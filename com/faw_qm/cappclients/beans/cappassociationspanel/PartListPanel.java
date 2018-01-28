package com.faw_qm.cappclients.beans.cappassociationspanel;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import com.faw_qm.framework.service.BinaryLinkIfc;

public class PartListPanel extends CappAssociationsPanel {
	private String[] partLists = null;
	public String[] getPartLists(){
		
		return partLists;
	}
	
	public  PartListPanel(){
		
		
	}
	
	 /**
	   *  用户点击删除按钮时响应
	   * @param actionevent
	   */
	  void removeButton_ActionPerformed(ActionEvent actionevent) {
	    try {
	    	this.modifyFlag=true;
	      BinaryLinkIfc[] binarylink = getSelectedLinks();
	      int[] rows = multiList.getSelectedRows();
	      partLists = new String[rows.length];
	      for(int x = 0;x < rows.length; x++){
	    	  partLists[x] = multiList.getCellText(rows[x], multiList.getNumberOfCols()-1);
//	    	  System.out.println("mario++"+partLists[x]);
	      }
	      //modified by dikef
	      if (!isGetResouceByProcedure) {
	        int kk = JOptionPane.showConfirmDialog(getParentJFrame(),
	                                               "删除后将无法恢复,是否确认删除?",
	                                               "提示框",
	                                               JOptionPane.OK_CANCEL_OPTION);
	        if (kk != 0) {

	          return;
	        }
	      }
	      //modified by dikef end
	      if (binarylink != null) {
	        for (int k = 0; k < binarylink.length; k++) {
	          if (binarylink[k] != null) {
	            removeSelectedObject(binarylink[k]);
	          }
	          else {
	            multiList.removeRow(multiList.getSelectedRow());
	          }
	        }
	      }
	      else {
	        for (int j = 0; j < rows.length; j++) {
	          i = rows[j];
	          if (i != -1) {
	            multiList.removeRow(i);
	          }
	        }
	      }
	      if (rows != null && rows.length > 0) {
	        if (rows[0] > 0) {
	          multiList.selectRow(rows[0] - 1);
	        }
	        else
	        if (multiList.getTable().getRowCount() > 0) {
	          multiList.selectRow(0);
	        }
	      }
	    }
	    catch (Exception _e) {
	      _e.printStackTrace();
	    }
	  }
}
