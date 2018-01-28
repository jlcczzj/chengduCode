/** 生成程序RationTreeCellRenderer.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.gybomNotice.client.util;


import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import com.faw_qm.part.model.QMPartInfo;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */
public class GYBomNoticeTreeCellRenderer extends DefaultTreeCellRenderer
{

	private Vector vec=new Vector();
    /**
     *默认构造函数
     *@param Vector vec 采用零件集合
     */

	   public GYBomNoticeTreeCellRenderer(Vector vec)
	    {
		   this.vec = vec;
	    }
    /**
     * 获得树单元描述(重载父类的方法)
     * @param tree
     * @param value
     * @param sel
     * @param expanded
     * @param leaf
     * @param row
     * @param hasFocus
     * @return
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, selected, expanded,
                                           leaf, row, hasFocus);

        if(value instanceof GYBomNoticeTreeNode)
        {
        	GYBomNoticeTreeNode node=(GYBomNoticeTreeNode)value;
        	this.setText(node.getNoteText());
            this.setToolTipText(node.getNoteText());
            if (sel)
            {
                setForeground(getTextSelectionColor());
            }
            else
            {
                setForeground(getTextNonSelectionColor());
            }
            if (expanded)
            {
                this.setIcon(new ImageIcon(node.getOpenImage()));
            }
            else
            {
                this.setIcon(new ImageIcon(node.getCloseImage()));
            }
        }
        if(value instanceof GYProductTreeNode)
        {
        	GYProductTreeNode node=(GYProductTreeNode)value;
        	this.setText(node.getNoteText());
            this.setToolTipText(node.getNoteText());

            if (node.isRoot()) {
				setForeground(Color.BLACK);
			} else {
				//采用零件显示红色
				QMPartInfo part = (QMPartInfo) node.getObject().getObject();
				if (vec.contains(part.getBsoID())) {
					setForeground(Color.RED);
				} else {
					setForeground(Color.BLACK);
				}
			}
  
            if (expanded)
            {
                this.setIcon(new ImageIcon(node.getOpenImage()));
            }
            else
            {
                this.setIcon(new ImageIcon(node.getCloseImage()));
            }
        }

        selected = sel;
        return this;
    }
}
