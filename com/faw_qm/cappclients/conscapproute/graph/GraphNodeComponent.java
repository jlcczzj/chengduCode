/**
 * 生成程序GraphNodeComponent.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司范围内,使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.util.RouteCategoryType;

/**
 * <p> 图元结点组件，用来在GraphPanel上显示 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class GraphNodeComponent extends GraphComponent
{

    public Color color;

    public ImageIcon image;

    GraphNode node;

    GraphLabelComponent labelComponent;

    Point drawAt;

    transient java.awt.event.ActionListener actionListener;

    /**
     * 构造器
     */
    public GraphNodeComponent()
    {
        setSize(0, 0);
    }

    /**
     * 构造器
     * @param graphnode -
     */
    public GraphNodeComponent(GraphNode graphnode)
    {
        setSize(new Dimension(32, 32));
        //设置图符节点
        setGraphNode(graphnode);
        //定义图象
        String s = "/images/" + graphnode.getIcon();
        image = (new ImageIcon(getClass().getResource(s)));

        NameMouseListener namemouselistener = new NameMouseListener();
        labelComponent = new GraphLabelComponent();
        labelComponent.setLabel(graphnode.getDepartmentName());
        //给节点下的链接添加鼠标监听器
        //labelComponent.addMouseListener(namemouselistener); //lm 20040520

    }

    /**
     * 设置图元接点
     * @param graphnode 传入图元节点接口
     */
    public void setGraphNode(GraphNode graphnode)
    {
        node = graphnode;
    }

    /**
     * 得到接点
     * @return GraphNode
     */
    public GraphNode getGraphNode()
    {
        return node;
    }

    /**
     * 取得标签组件
     * @return GraphLabelComponent
     */
    public GraphLabelComponent getLabelComponent()
    {
        return labelComponent;
    }

    /**
     * 增加行为监听方法
     * @param actionlistener 行为监听器
     */
    public void addActionListener(java.awt.event.ActionListener actionlistener)
    {
        actionListener = AWTEventMulticaster.add(actionListener, actionlistener);
    }

    /**
     * 重载父类方法addNotify
     */
    public void addNotify()
    {
        super.addNotify();
        setLocation(node.getPosition());
    }

    /**
     * 设置当前位置
     * @param point -当前选中的点坐标
     */
    public void setLocation(java.awt.Point point)
    {
        super.setLocation(point);
        labelComponent.position(this);
    }

    /**
     * 更新图象
     */
    public void updateImage()
    {
        String routeType = ((RouteNodeInfo)node.getRouteItem().getObject()).getRouteType();
        if(routeType.equals(RouteCategoryType.MANUFACTUREROUTE.getDisplay())) //制造单位
        {
            image = new ImageIcon(getClass().getResource("/images/route_makeNode.gif"));
        }else
        {
            image = new ImageIcon(getClass().getResource("/images/route_assemNode.gif"));
        }
        drawAt = new Point(32 - image.getIconHeight() / 2, 32 - image.getIconWidth() / 2);
        repaint();
    }

    /**
     * 更新标签说明(路线单位)
     */
    public void updateDescription()
    {
        labelComponent.setLabel(node.getDepartmentName());
        labelComponent.position(this);
    }

    /**
     * 得到起始点
     * @return Point 起点
     */
    Point getFromPoint()
    {
        //构造组件包 的矩形标志
        Rectangle rectangle = getBounds();
        //返回新坐标
        return new Point(rectangle.x + rectangle.width + 4, rectangle.y + rectangle.height / 2);
    }

    /**
     * 得到终节点
     * @return Point 终点
     */
    Point getToPoint()
    {
        Rectangle rectangle = getBounds();
        return new Point(rectangle.x - 4, rectangle.y + rectangle.height / 2);
    }

    /**
     * 重载父类的画方法
     * @param g 传入图片
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(color != null)
        {
            g.setColor(color);
            g.fill3DRect(1, 1, 30, 30, true);
        }

        g.drawImage(image.getImage(), 16 - image.getIconHeight() / 2, 16 - image.getIconWidth() / 2, this);
        if(selected)
        {
            g.setColor(Color.black);
            g.drawRect(0, 0, 31, 31);
            g.drawRect(1, 1, 29, 29);
        }
    }

    /**
     * 设置节点是否被选择
     * @param flag -是否被选中
     */
    public void setSelected(boolean flag)
    {
        super.setSelected(flag);
        labelComponent.setSelected(flag);
    }

    /**
     * 取得标签的说明文字
     * @return String
     */
    public String toString()
    {
        return labelComponent.getLabel();
    }

    /**
     * 判断某点是否在节点范围内
     * @param i -整型 横坐标
     * @param j -整型 纵坐标
     * @return boolean
     */
    boolean containsGlobal(int i, int j)
    {
        Rectangle rectangle = getBounds();
        return i >= rectangle.x && i <= rectangle.x + rectangle.width && j >= rectangle.y && j <= rectangle.y + rectangle.height;
    }

    /**
     * 内部类，监听标签的鼠标事件
     */
    class NameMouseListener extends MouseAdapter
    {
        boolean mouse_pressed;

        NameMouseListener()
        {
            mouse_pressed = false;
        }

        /**
         * 释放鼠标行为激发的事件
         * @param mouseevent 鼠标事件
         */
        public void mouseReleased(MouseEvent mouseevent)
        {
            if(mouse_pressed)
            {
                //在节点上按下鼠标时，节点下的标签设置成深蓝色
                labelComponent.setColor(Color.blue);
            }
            mouse_pressed = false;
        }

        /**
         * 按下鼠标行为激发的事件
         * @param mouseevent -鼠标事件
         */
        public void mousePressed(MouseEvent mouseevent)
        {
            mouse_pressed = true;
            labelComponent.setColor(Color.red);
        }
    }

}