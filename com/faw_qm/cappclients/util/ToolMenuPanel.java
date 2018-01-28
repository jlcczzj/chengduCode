package com.faw_qm.cappclients.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;



public class ToolMenuPanel extends JPanel
{
	private JFrame frame;
    private GridBagLayout gridBagLayout = new GridBagLayout();
	
	public ToolMenuPanel(JFrame frame,String fuctionDesc)
	{
		this.frame = frame;
		setLayout(gridBagLayout);
		setBorder(BorderFactory.createEtchedBorder());
	}
	
//    	// 不绘制边框
//    	button.setBorderPainted(false);
//        // 不绘制焦点
//    	button.setFocusPainted(false);
//        // 不绘制内容区
//    	button.setContentAreaFilled(false);
//        // 设置焦点控制
//    	button.setFocusable(true);
//        // 设置按钮边框与边框内容之间的像素数
//    	button.setMargin(new Insets(0, 0, 0, 0));
}