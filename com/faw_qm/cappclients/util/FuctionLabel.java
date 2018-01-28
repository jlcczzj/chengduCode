package com.faw_qm.cappclients.util;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FuctionLabel extends JLabel
{
	public FuctionLabel(String name,boolean isFloor)
	{
		super(name);
		if(isFloor)
		{
//	        setBorder(BorderFactory.createEtchedBorder());
	        setBorder(BorderFactory.createLineBorder(new Color(249,249,249)));
			setOpaque(true);
//			setBackground(Color.lightGray);
			setBackground(new Color(225,225,225));

		}
		setHorizontalAlignment(SwingConstants.CENTER);
	}
}