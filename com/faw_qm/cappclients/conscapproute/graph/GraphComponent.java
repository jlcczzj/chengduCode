/** 
 * 生成程序 GraphComponent.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import javax.swing.JComponent;
import java.awt.Graphics;

/**
 * <p> 图元组件。节点图元和连接图元都继承了本类 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class GraphComponent extends JComponent
{
    boolean selected;

    /**
     * 构造器
     * @roseuid 3DB672CC006B
     */
    public GraphComponent()
    {
        selected = false;
        setSize(0, 0);
    }

    /**
     * 是否可选
     * @param falg - boolean型，决定组件是否可选
     * @roseuid 3DB672CC0075
     */
    public void setSelected(boolean falg)
    {
        selected = falg;
        repaint();
    }

    /**
     * 是否被选中，返回布尔型
     * @return boolean
     * @roseuid 3DB672CC0080
     */
    public boolean isSelected()
    {
        return selected;
    }

    /**
     * 更新组件
     * @param g 画笔
     * @roseuid 3DB672CC0089
     */
    public void update(Graphics g)
    {
        paint(g);
    }
}