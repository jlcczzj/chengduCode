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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;


import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshListener;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.technics.consroute.model.SupplierInfo;
import com.faw_qm.technics.consroute.util.RouteListLevelType;



public class SelectLevelRouteJDialog extends JDialog
{
	private RouteListTaskJPanel panel1 ;
    private String partNum = "";
    private JLabel levelJLabel = new JLabel();
    public JComboBox levelJComboBox = new JComboBox();
    public ComponentMultiList qMMultiList = new ComponentMultiList();
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private CappRouteListManageJFrame parentFrame = null;
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel(); 
    JButton okJButton = new JButton();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    GridBagLayout gridBagLayout3 = new GridBagLayout();
   SelectLevelRouteJDialog( JFrame frame,JPanel panel) {
		    super(frame,"选择路线级别", true);
	        this.panel1 = (RouteListTaskJPanel)panel;
	        this.parentFrame=(CappRouteListManageJFrame)frame;
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
	
//    	 this.setTitle("选择路线级别");
         this.setSize(363, 235);
         this.getRootPane().setDefaultButton(this.okJButton);
         this.getContentPane().setLayout(gridBagLayout3);
         this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
         jPanel1.setLayout(gridBagLayout2);
         jPanel2.setLayout(gridBagLayout1);
         okJButton.setMaximumSize(new Dimension(75, 23));
         okJButton.setMinimumSize(new Dimension(75, 23));
         okJButton.setPreferredSize(new Dimension(75, 23));
         okJButton.setRequestFocusEnabled(true);
         okJButton.setMnemonic('Y');
         okJButton.setText("确定(Y)");
         okJButton.addActionListener(new java.awt.event.ActionListener()
         {
             public void actionPerformed(ActionEvent e)
             {
                 okJButton_actionPerformed(e);
             }
         });

         levelJLabel.setText("级  别");
         levelJComboBox.addItem("一级路线");
         levelJComboBox.addItem("二级路线");
         this.getContentPane().add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.VERTICAL, new Insets(0, 20, 0, 0), -180, -90));
         this.getContentPane().add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(9, 225, 14, 10), 4, 0));
         jPanel2.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 0, 20, 45), 40, 10));
         jPanel1.add(levelJLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH, new Insets(0, 30, 10, 58), 372, 253));
         jPanel1.add(levelJComboBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 50), 45, 10));
        levelJComboBox.addActionListener(new SelectLevelRouteJDialog_levelJComboBox_actionAdapter(this));
    }

    /**
     * 确定按钮处理事件
     * @param e
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        if(levelJComboBox.getSelectedItem() != null)
        {
        	//parentFrame.closeContentPanel();	
        	this.panel1.flag=false;
        	this.panel1.levelJComboBox.setSelectedItem(levelJComboBox.getSelectedItem());
        	this.panel1.levelJComboBox.setEditable(false);
        	this.panel1.levelJComboBox.setEnabled(false);
        	
           	if(levelJComboBox.getSelectedItem().equals("二级路线")){
        		this.panel1.flag=true;		
        	}else{
        		this.panel1.flag=false;	
        	}
           	
        }
        this.setVisible(false);
        this.dispose();
        setCursor(Cursor.getDefaultCursor());
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
	 * <p>Title:级别Combo事件响应适配器</p> <p>Description: </p>
	 */
	class SelectLevelRouteJDialog_levelJComboBox_actionAdapter implements java.awt.event.ActionListener
	{
	    private SelectLevelRouteJDialog adaptee;

	    SelectLevelRouteJDialog_levelJComboBox_actionAdapter(SelectLevelRouteJDialog adaptee)
	    {
	        this.adaptee = adaptee;
	    }

	    public void actionPerformed(ActionEvent e)
	    {
	        adaptee.levelJComboBox_actionPerformed(e);
	    }
	}
    void levelJComboBox_actionPerformed(ActionEvent e)
    {

    	levelJComboBox.setSelectedItem(levelJComboBox.getSelectedItem());
 
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
