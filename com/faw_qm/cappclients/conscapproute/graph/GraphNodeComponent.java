/**
 * ���ɳ���GraphNodeComponent.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Χ��,ʹ�ñ�����
 * ��������Ȩ��
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
 * <p> ͼԪ��������������GraphPanel����ʾ </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
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
     * ������
     */
    public GraphNodeComponent()
    {
        setSize(0, 0);
    }

    /**
     * ������
     * @param graphnode -
     */
    public GraphNodeComponent(GraphNode graphnode)
    {
        setSize(new Dimension(32, 32));
        //����ͼ���ڵ�
        setGraphNode(graphnode);
        //����ͼ��
        String s = "/images/" + graphnode.getIcon();
        image = (new ImageIcon(getClass().getResource(s)));

        NameMouseListener namemouselistener = new NameMouseListener();
        labelComponent = new GraphLabelComponent();
        labelComponent.setLabel(graphnode.getDepartmentName());
        //���ڵ��µ����������������
        //labelComponent.addMouseListener(namemouselistener); //lm 20040520

    }

    /**
     * ����ͼԪ�ӵ�
     * @param graphnode ����ͼԪ�ڵ�ӿ�
     */
    public void setGraphNode(GraphNode graphnode)
    {
        node = graphnode;
    }

    /**
     * �õ��ӵ�
     * @return GraphNode
     */
    public GraphNode getGraphNode()
    {
        return node;
    }

    /**
     * ȡ�ñ�ǩ���
     * @return GraphLabelComponent
     */
    public GraphLabelComponent getLabelComponent()
    {
        return labelComponent;
    }

    /**
     * ������Ϊ��������
     * @param actionlistener ��Ϊ������
     */
    public void addActionListener(java.awt.event.ActionListener actionlistener)
    {
        actionListener = AWTEventMulticaster.add(actionListener, actionlistener);
    }

    /**
     * ���ظ��෽��addNotify
     */
    public void addNotify()
    {
        super.addNotify();
        setLocation(node.getPosition());
    }

    /**
     * ���õ�ǰλ��
     * @param point -��ǰѡ�еĵ�����
     */
    public void setLocation(java.awt.Point point)
    {
        super.setLocation(point);
        labelComponent.position(this);
    }

    /**
     * ����ͼ��
     */
    public void updateImage()
    {
        String routeType = ((RouteNodeInfo)node.getRouteItem().getObject()).getRouteType();
        if(routeType.equals(RouteCategoryType.MANUFACTUREROUTE.getDisplay())) //���쵥λ
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
     * ���±�ǩ˵��(·�ߵ�λ)
     */
    public void updateDescription()
    {
        labelComponent.setLabel(node.getDepartmentName());
        labelComponent.position(this);
    }

    /**
     * �õ���ʼ��
     * @return Point ���
     */
    Point getFromPoint()
    {
        //��������� �ľ��α�־
        Rectangle rectangle = getBounds();
        //����������
        return new Point(rectangle.x + rectangle.width + 4, rectangle.y + rectangle.height / 2);
    }

    /**
     * �õ��սڵ�
     * @return Point �յ�
     */
    Point getToPoint()
    {
        Rectangle rectangle = getBounds();
        return new Point(rectangle.x - 4, rectangle.y + rectangle.height / 2);
    }

    /**
     * ���ظ���Ļ�����
     * @param g ����ͼƬ
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
     * ���ýڵ��Ƿ�ѡ��
     * @param flag -�Ƿ�ѡ��
     */
    public void setSelected(boolean flag)
    {
        super.setSelected(flag);
        labelComponent.setSelected(flag);
    }

    /**
     * ȡ�ñ�ǩ��˵������
     * @return String
     */
    public String toString()
    {
        return labelComponent.getLabel();
    }

    /**
     * �ж�ĳ���Ƿ��ڽڵ㷶Χ��
     * @param i -���� ������
     * @param j -���� ������
     * @return boolean
     */
    boolean containsGlobal(int i, int j)
    {
        Rectangle rectangle = getBounds();
        return i >= rectangle.x && i <= rectangle.x + rectangle.width && j >= rectangle.y && j <= rectangle.y + rectangle.height;
    }

    /**
     * �ڲ��࣬������ǩ������¼�
     */
    class NameMouseListener extends MouseAdapter
    {
        boolean mouse_pressed;

        NameMouseListener()
        {
            mouse_pressed = false;
        }

        /**
         * �ͷ������Ϊ�������¼�
         * @param mouseevent ����¼�
         */
        public void mouseReleased(MouseEvent mouseevent)
        {
            if(mouse_pressed)
            {
                //�ڽڵ��ϰ������ʱ���ڵ��µı�ǩ���ó�����ɫ
                labelComponent.setColor(Color.blue);
            }
            mouse_pressed = false;
        }

        /**
         * ���������Ϊ�������¼�
         * @param mouseevent -����¼�
         */
        public void mousePressed(MouseEvent mouseevent)
        {
            mouse_pressed = true;
            labelComponent.setColor(Color.red);
        }
    }

}