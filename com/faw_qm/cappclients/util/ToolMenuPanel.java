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
	
//    	// �����Ʊ߿�
//    	button.setBorderPainted(false);
//        // �����ƽ���
//    	button.setFocusPainted(false);
//        // ������������
//    	button.setContentAreaFilled(false);
//        // ���ý������
//    	button.setFocusable(true);
//        // ���ð�ť�߿���߿�����֮���������
//    	button.setMargin(new Insets(0, 0, 0, 0));
}