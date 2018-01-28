/**
 * ���ɳ���RouteTreeCellRenderer.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.cappclients.conscapproute.util;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * <p> Title: ����·�����滭�� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2003 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public class RouteTreeCellRenderer extends DefaultTreeCellRenderer
{

    /**
     * Ĭ�Ϲ��캯��
     */
    public RouteTreeCellRenderer()
    {

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
     * @return The foreground color is set based on the selection and the icon is set based on leaf and expanded.
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        RouteTreeNode node = (RouteTreeNode)value;
        this.setText(node.getNoteText());
        this.setToolTipText(node.getNoteText());
        if(sel)
            setForeground(getTextSelectionColor());
        else
            setForeground(getTextNonSelectionColor());
        if(expanded)
            this.setIcon(new ImageIcon(node.getOpenImage()));
        else
            this.setIcon(new ImageIcon(node.getCloseImage()));
        selected = sel;
        return this;
    }
}