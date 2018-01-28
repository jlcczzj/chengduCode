package com.faw_qm.part.client.other.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.part.client.main.view.QMProductManagerJFrame;
import com.faw_qm.part.model.QMPartIfc;

public class SelectViewNameDialog extends JDialog {
	private JComboBox box;
	
	private QMProductManagerJFrame parentFrame;
	private QMPartIfc part;
	
	public SelectViewNameDialog(QMProductManagerJFrame frame,QMPartIfc part)
	{
        super(frame,"��ʾ", true);
		parentFrame = frame;
		this.part=part;
		JButton button = new JButton("ȷ��");
  	    button.addActionListener(new ActionListener()
  	    {
			public void actionPerformed(ActionEvent e) 
			{
				okJButton_actionPerformed(e);
			}
		});
		
  	    this.getContentPane().setLayout(new BorderLayout());
  	    JPanel panel1 = new JPanel();
  	    panel1.add(new JLabel("��ͼ"));
  	    
  	    Object[] obj = new Object[2];
  	    obj[0] = "������ͼ";
  	    obj[1] = "���������ͼ";
  	    box = new JComboBox(obj);
  	    panel1.add(box);
  	    this.getContentPane().add(panel1,BorderLayout.CENTER);
  	    
		
  	    button.setMaximumSize(new Dimension(91, 23));
        button.setMinimumSize(new Dimension(91, 23));
        button.setPreferredSize(new Dimension(91, 23));
  	    JPanel panel2 = new JPanel();
  	    panel2.add(button);
  	    this.getContentPane().add(panel2,BorderLayout.SOUTH);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	  	this.setBounds(dimension.width/2-125, dimension.height/2-50, 250, 100);
  	    this.setResizable(false);
  	    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  	    
  	    this.setVisible(true);
	}
	 public void okJButton_actionPerformed(ActionEvent e) {
		    this.dispose();
			String  makeV="";
		    HashMap map = new HashMap();
		    map.put("PartID", this.part.getBsoID());
		    map.put("ViewName", box.getSelectedItem());
		    RichToThinUtil.toWebPage("Part-Other-SiglePartStatistics-001.screen", map);
		
		    this.setVisible(false);
		 
		  }
	
	public static void main(String args[])
	{
		new SelectViewNameDialog(null,null);
	}
}
