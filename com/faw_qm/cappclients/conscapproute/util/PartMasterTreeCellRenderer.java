/** 
 * 生成程序PartMasterTree.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.util;

import javax.swing.tree.*;
import java.awt.*;
import javax.swing.*;

/**
 * <p> Title: 零部件树绘画类 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class PartMasterTreeCellRenderer extends DefaultTreeCellRenderer
{

    /**
     * 默认构造函数
     */
    public PartMasterTreeCellRenderer()
    {}

    /**
     * 获得树单元描述(重载父类的方法)
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
        PartMasterTreeNode node = (PartMasterTreeNode)value;
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