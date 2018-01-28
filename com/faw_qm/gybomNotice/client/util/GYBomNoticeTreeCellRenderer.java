/** ���ɳ���RationTreeCellRenderer.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */
public class GYBomNoticeTreeCellRenderer extends DefaultTreeCellRenderer
{

	private Vector vec=new Vector();
    /**
     *Ĭ�Ϲ��캯��
     *@param Vector vec �����������
     */

	   public GYBomNoticeTreeCellRenderer(Vector vec)
	    {
		   this.vec = vec;
	    }
    /**
     * �������Ԫ����(���ظ���ķ���)
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
				//���������ʾ��ɫ
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
