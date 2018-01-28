package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;


import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.technics.consroute.model.SupplierInfo;

//SS1 长特供应商，增加上移、下移按钮。新添加供应商，不覆盖“搜索供应商”页面中的供应商  文柳 2015-3-10

public class EditSupplierJDialog extends JDialog
{
	
	//private JTable table;
	private RouteListPartLinkJPanel panel1 ;
    private String partNum = "";
    public ComponentMultiList qMMultiList = new ComponentMultiList();
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private JFrame parentFrame = null;
	public EditSupplierJDialog( JFrame frame,JPanel panel, String partNum) {
		    super(frame);
	        this.panel1 = (RouteListPartLinkJPanel)panel;
	        this.partNum = partNum;
	        try
	        {
	            jbInit();
	            setViewLocation();
	        }catch(Exception ex)
	        {
	            ex.printStackTrace();
	        }

	}
	 /**
     * 组件初始化
     * @throws Exception
     */
    private void jbInit()
    {
//		
    	 this.setTitle("搜索供应商");
         this.setSize(800, 600);
    	
        getContentPane().setLayout(new GridBagLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		getContentPane().add(panel, gridBagConstraints);
      
	
		final JLabel label = new JLabel();
		label.setText("零部件编号");
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints_5.gridx = 0;
		gridBagConstraints_5.gridy = 0;
		gridBagConstraints_5.insets = new Insets(5, 5, 5, 5);
		panel.add(label, gridBagConstraints_5);
		
		final JLabel label_1 = new JLabel();
		label_1.setText(partNum);
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints_7.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints_7.weightx = 1.0;
		gridBagConstraints_7.gridx = 1;
		gridBagConstraints_7.gridy = 0;
		gridBagConstraints_7.insets = new Insets(5, 0, 5, 5);
		panel.add(label_1, gridBagConstraints_7);

		final JLabel label_2 = new JLabel();
		label_2.setText("供应商信息");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.gridy = 1;
		gridBagConstraints_6.insets = new Insets(5, 5, 5, 0);
		panel.add(label_2, gridBagConstraints_6);

		//table = new JTable();
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.fill = GridBagConstraints.BOTH;
		gridBagConstraints_8.weighty = 1.0;
		gridBagConstraints_8.weightx = 1.0;
		gridBagConstraints_8.gridx = 1;
		gridBagConstraints_8.gridy = 1;
		gridBagConstraints_8.insets = new Insets(5, 5, 5, 5);
		panel.add(qMMultiList, gridBagConstraints_8);
		qMMultiList.setCellEditable(false);
   
        String[] headings = {"id", "顺序号", "供应商代码", "供应商名称","供应商简称"};
        qMMultiList.setHeadings(headings);
        qMMultiList.setRelColWidth(new int[]{0, 1, 1, 1,0});
        qMMultiList.setMultipleMode(false);
        int[] editCol = new int[]{1};
        qMMultiList.setColsEnabled(editCol, true);
        qMMultiList.setEnabled(false);

		
		final JPanel panel_1 = new JPanel();
		panel_1.setLayout(new GridBagLayout());
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints_1.anchor = GridBagConstraints.NORTHEAST;
		gridBagConstraints_1.weighty = 1.0;
		gridBagConstraints_1.gridx = 1;
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.insets = new Insets(5, 0, 5, 5);
		getContentPane().add(panel_1, gridBagConstraints_1);
//CCBegin SS1
		final JButton upbutton = new JButton();
		upbutton.setText("上移");
		final GridBagConstraints gridBagConstraints_up = new GridBagConstraints();
		gridBagConstraints_up.gridx = 0;
		gridBagConstraints_up.gridy = 3;
		gridBagConstraints_up.insets = new Insets(5, 5, 5, 5);
		panel_1.add(upbutton, gridBagConstraints_up);
		upbutton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				upbutton_actionPerformed(e);
			}
		});
		final JButton downbutton = new JButton();
		downbutton.setText("下移");
		final GridBagConstraints gridBagConstraints_down = new GridBagConstraints();
		gridBagConstraints_down.gridx = 0;
		gridBagConstraints_down.gridy = 4;
		gridBagConstraints_down.insets = new Insets(5, 5, 5, 5);
		panel_1.add(downbutton, gridBagConstraints_down);
		downbutton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				downbutton_actionPerformed(e);
			}
		});
		
	 
//CCEnd SS1
		
		
		final JButton button = new JButton();
		button.setText("浏览");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.gridx = 0;
		gridBagConstraints_2.gridy = 0;
		gridBagConstraints_2.insets = new Insets(5, 5, 5, 5);
		panel_1.add(button, gridBagConstraints_2);
		button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				button_actionPerformed(e);
			}
		});
		final JButton button_1 = new JButton();
		button_1.setText("删除");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridx = 0;
		gridBagConstraints_3.gridy = 1;
		gridBagConstraints_3.insets = new Insets(5, 5, 5, 5);
		panel_1.add(button_1, gridBagConstraints_3);
		button_1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				removeJButton_actionPerformed(e);
			}
		});
		final JButton button_2 = new JButton();
		button_2.setText("添加");
		button_2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				button_2_actionPerformed(e);
			}
		});
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.gridx = 0;
		gridBagConstraints_4.gridy = 2;
		gridBagConstraints_4.insets = new Insets(5, 5, 5, 5);
		panel_1.add(button_2, gridBagConstraints_4);

    }
	//CCBegin SS1
    private void upbutton_actionPerformed(ActionEvent e) {
    	int[] selected = qMMultiList.getSelectedRows();
    	 if(selected.length>1){
         	JOptionPane.showMessageDialog(this.getParent(),"只能单选上移属性！");
 		    setCursor(Cursor.getDefaultCursor());
 		    return ;
         }
    	 if(selected.length==0){
          	JOptionPane.showMessageDialog(this.getParent(),"请选择需要上移的属性");
  		    setCursor(Cursor.getDefaultCursor());
  		    return ;
          }
    	int rowcount = qMMultiList.getRowCount();
    	int moveIndex = selected[0];
    	if(moveIndex==0){
    		return;
    	}else{
    		qMMultiList.moveUp(moveIndex);
    	}
    	qMMultiList.setSelectedRow(moveIndex-1);
    }
    private void downbutton_actionPerformed(ActionEvent e) {
    	 int rowcount = qMMultiList.getRowCount();
	     int[] selected = qMMultiList.getSelectedRows();
	   	 if(selected.length>1){
	        	JOptionPane.showMessageDialog(this.getParent(),"只能单选下移属性！");
			    setCursor(Cursor.getDefaultCursor());
			    return ;
	        }
	   	 if(selected.length==0){
	          	JOptionPane.showMessageDialog(this.getParent(),"请选择需要下移的属性");
	  		    setCursor(Cursor.getDefaultCursor());
	  		    return ;
	          }
	   	
	   	int moveIndex = selected[0];
	   	if(moveIndex==rowcount-1){
  		    return ;
	   	}else{
	   		qMMultiList.moveDown(moveIndex);
	   	}
	   	qMMultiList.setSelectedRow(moveIndex+1);
		
    }
    //CCEnd SS1
    private void button_actionPerformed(ActionEvent e) {
		searthSupplierJDailog s= new searthSupplierJDailog(this
					.getParentJFrame(),this);
		s.setVisible(true);
		
    }
	/**
	 * 删除所选择的零部件
	 * 
	 * @param e
	 *            ActionEvent
	 */
	void removeJButton_actionPerformed(ActionEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		int[] index = qMMultiList.getSelectedRows();
	        if(index!=null&&index.length>0){
	            for(int i = 0 ; i < index.length;i++){
	            	qMMultiList.removeRow(index[i]-i);
	            }
	            //删除后列表内容不为空，则默认选择第一个
	         if(qMMultiList.getRowCount()>0){
	        	 qMMultiList.selectRow(0);
	         }  	
	        }
		setCursor(Cursor.getDefaultCursor());
	}
    private void button_2_actionPerformed(ActionEvent e) {
    	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		this.dispose();	
		HashMap map=new HashMap();
		HashMap bosoIdmap=new HashMap();
		Collection v = new Vector();
	
		for(int i=0;i<qMMultiList.getRowCount();i++){
			String useCount = qMMultiList.getCellText(i, 1);
			if(useCount!=null&&!useCount.equals("")){
				if(!isInteger(useCount)){
					JOptionPane.showMessageDialog(this, "列表中第"+(i+1)+"行顺序号为非数字类型！",
							"提示信息", JOptionPane.INFORMATION_MESSAGE);
					return;
	    		}			   
			}
			if(!map.containsKey(useCount)){
				v.add(useCount);
				map.put(useCount, qMMultiList.getCellText(i, 4));
				bosoIdmap.put(useCount,qMMultiList.getCellText(i, 0));
			}else{
				String code=map.get(useCount)+";"+qMMultiList.getCellText(i, 4);
				String bosoId=bosoIdmap.get(useCount)+";"+qMMultiList.getCellText(i, 0);
				map.put(useCount,code);
				bosoIdmap.put(useCount,bosoId);
			}	
		}
		String codeString="";
		String codeBsoid="";
		if(v!=null&&v.size()>0){
			if(v.size()==1){
        	  for(Iterator ites = v.iterator();ites.hasNext();){
        		 String num =(String)ites.next();
        		 codeString= (String)map.get(num);
        		 codeBsoid=(String)bosoIdmap.get(num);
        	  }
			}else{
				 for(Iterator ites = v.iterator();ites.hasNext();){
					 String num =(String)ites.next();
					 if(!codeString.equals("")){
					   codeString =codeString+";"+num+" "+(String)map.get(num);
					   codeBsoid=codeBsoid+";"+num+" "+(String)bosoIdmap.get(num);
					 }else{
						codeString=num+" "+(String)map.get(num); 
						codeBsoid=num+" "+(String)bosoIdmap.get(num);
					 }
				 }
			}
		}
		this.panel1.addSupplier(codeString,codeBsoid);
		
    }
    /**   * 判断字符串是否是整数   */
    public static boolean isInteger(String value) {
    	try {  
    		Integer.parseInt(value); 
    		return true; 
    		} 
    	catch (NumberFormatException e){ 
    		return false; 
    		} 
    	}

    /**
     * 将数据添加到列表中
     * @param infos
     */
 public void addData(SupplierInfo[] infos){
	 int[] editCol = new int[]{1};
	  qMMultiList.setColsEnabled(editCol, true);
	  //CCBegin SS1
	  int rows = qMMultiList.getRowCount();
	 if(infos!=null&&infos.length>0){
		 for(int i = 0 ; i <infos.length;i++){
			 SupplierInfo info = infos[i]; 
			 qMMultiList.addTextCell(i+rows, 0, info.getBsoID());
			 qMMultiList.addTextCell(i+rows,1,"1");
			 qMMultiList.addTextCell(i+rows, 2, info.getCode());
			 qMMultiList.addTextCell(i+rows, 3, info.getCodename());	
			 qMMultiList.addTextCell(i+rows, 4, info.getJName());	
		 }
	 }
//CCEnd SS1
	  
    }
	/**
	 * 设置界面的显示位置
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
		this.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

	}
    /**
     * 获得父窗口
     * @return javax.swing.JFrame
     * @roseuid 402A11F40212
     */
    public JFrame getParentJFrame()
    {
        Component parent = getParent();
        while(!(parent instanceof JFrame))
        {
            parent = parent.getParent();
        }
        return (JFrame)parent;
    }
}
