/**���ɳ���GraphLinkComponent.java
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
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.faw_qm.framework.exceptions.QMException;

/**
 * <p> ͼԪ����������������ӵ����� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class GraphLinkComponent extends GraphLabelComponent
{
    /** �Ƿ�ֱ�� */
    boolean strait;

    /** ��ʼ�� */
    public Point fromPoint;

    /** �յ� */
    public Point toPoint;

    public Point fromLoopPoint;

    /** ���� */
    GraphLink link;

    /** ��ʼ����� */
    GraphNodeComponent from;

    /** �յ���� */
    GraphNodeComponent to;

    /** ��Ϊ���� */
    transient java.awt.event.ActionListener actionListener;

    /**
     * ������
     */
    public GraphLinkComponent()
    {
        fromPoint = new Point(0, 0);
        toPoint = new Point(0, 0);
        strait = true;
        fromLoopPoint = new Point();
    }

    /**
     * ������
     * @param graphlink GraphLink��Ķ���
     */
    public GraphLinkComponent(GraphLink graphlink)
    {
        fromPoint = new Point(0, 0);
        toPoint = new Point(0, 0);
        strait = true;
        fromLoopPoint = new Point();
        setGraphLink(graphlink);
        addMouseListener(new NameMouseListener());
    }

    /**
     * ��������
     * @param graphlink GraphLink�Ķ���
     */
    public void setGraphLink(GraphLink graphlink)
    {
        link = graphlink;
    }

    /**
     * �������
     * @return link ����
     * @roseuid 3DB673340075
     */
    public GraphLink getGraphLink()
    {
        return link;
    }

    /**
     * �����Ϊ������
     * @param actionlistener ActionListener�Ķ���
     */
    public void addActionListener(java.awt.event.ActionListener actionlistener)
    {
        actionListener = AWTEventMulticaster.add(actionListener, actionlistener);
    }

    /**
     * �������ӵ�ǰ���ڵ�
     * @param graphnodecomponent GraphNodeComponent�Ķ���
     * @throws QMException 
     * @throws QMRemoteException
     * @roseuid 3DB67334009D
     */
    public void setFrom(GraphNodeComponent graphnodecomponent) throws QMException
    {
        from = graphnodecomponent;
        ((DefaultGraphLink)link).setPredecessor(graphnodecomponent.getGraphNode());
    }

    /**
     * ��ȡǰ���ڵ�
     * @return from ��ʼ�����
     * @roseuid 3DB6733503DD
     */
    public GraphNodeComponent getFrom()
    {
        return from;
    }

    /**
     * ���ú�̽ڵ�
     * @param graphnodecomponent GraphNodeComponent�Ķ���
     * @throws QMException 
     * @throws QMPropertyVetoException
     */
    public void setTo(GraphNodeComponent graphnodecomponent) throws QMException
    {
        to = graphnodecomponent;
        ((DefaultGraphLink)link).setSuccessor(graphnodecomponent.getGraphNode());
    }

    /**
     * ��ȡ��̽ڵ�
     * @return GraphNodeComponent �յ����
     * @roseuid 3DB67337037C
     */
    public GraphNodeComponent getTo()
    {
        return to;
    }

    /**
     * �ж��Ƿ�ֱ��
     * @param flag -�Ƿ�ֱ��
     * @roseuid 3DB673370390
     */
    public void setStraitLine(boolean flag)
    {
        strait = flag;
    }

    /**
     * �ж��Ƿ���ֱ��
     * @return boolean
     * @roseuid 3DB6733703A4
     */
    public boolean isStraitLine()
    {
        return strait;
    }

    /**
     * ������ʼ�ڵ�
     * @param i ��ʼ�ӵ������
     * @param j ��ʼ�ӵ�������
     * @roseuid 3DB6733703B8
     */
    public void setStartPoint(int i, int j)
    {
        from = null;
        fromPoint.x = i;
        fromPoint.y = j;
    }

    /**
     * �������սڵ�
     * @param i -
     * @param j -
     * @roseuid 3DB6733703D6
     */
    public void setEndPoint(int i, int j)
    {
        to = null;
        toPoint.x = i;
        toPoint.y = j;
    }

    /**
     * ����������ɫ
     * @param g -java.awt.Graphics
     * @roseuid 3DB67338000C
     */
    public void setLineColor(Graphics g)
    {
        if(selected)
        {
            //ѡ��״̬�µ�������ɫΪ���ɫ
            g.setColor(Color.red.darker());
        }else
        {
            //��ѡ��״̬�µ�������ɫΪ��ɫ
            g.setColor(Color.black);
        }
    }

    /**
     * Ϊ���ӻ���
     * @param g -java.awt.Graphics
     * @roseuid 3DB67338002A
     */
    public void drawLine(Graphics g)
    {
        super.paintComponent(g);
        if(strait)//��ֱ��
        {
            Dimension dimension = getSize();
            Rectangle rectangle = null;
            Rectangle rectangle1 = null;
            setLineColor(g);
            //��ʼ�ӵ㲻Ϊ��
            if(from != null)
            {
                rectangle = from.getBounds();
                rectangle.grow(3, 3);//����ʼ�ڵ���εĿ������Ϊ��������
            }
            //�սӵ㲻Ϊ��
            if(to != null)
            {
                rectangle1 = to.getBounds();
                rectangle1.grow(3, 3);//���ն˽ڵ���εĿ������Ϊ��������
            }
            //��ʾ�ڵ���ն˽ڵ���ͬһ���㣬����
            if(from == to)
            {
                fromPoint = from.getFromPoint();
                toPoint = to.getToPoint();
                g.drawArc(rectangle1.x - 8, rectangle1.y - 20, rectangle1.width + 16, rectangle1.height + 10, 320, 250);
                setLocation((rectangle.x + rectangle.width / 2) - dimension.width / 2, rectangle.y - rectangle.width / 2 - dimension.height / 2);
            }
            //��ʼ�ڵ���ն˽ڵ㲻ͬ������
            else
            {
                /*
                 * Ϊ��ʼ�ӵ���������
                 */
                if(from != null)
                {
                    fromPoint.x = rectangle.x + rectangle.width / 2;
                    fromPoint.y = rectangle.y + rectangle.height / 2;
                }
                /*
                 * Ϊ�սӵ���������
                 */
                if(to != null)
                {
                    toPoint.x = rectangle1.x + rectangle1.width / 2;
                    toPoint.y = rectangle1.y + rectangle1.height / 2;
                }
                if(from != null)
                    fromPoint = intersection(fromPoint, toPoint, rectangle);
                if(to != null)
                    toPoint = intersection(toPoint, fromPoint, rectangle1);
                /*
                 * �����ӵ�仭��
                 */
                g.drawLine(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
                /*
                 * ���������յ���м�ӵ�������
                 */
                int k1 = fromPoint.y + (toPoint.y - fromPoint.y) / 2;
                /*
                 * ���������յ���м�ӵ������
                 */
                int j2 = fromPoint.x + (toPoint.x - fromPoint.x) / 2;
                /*
                 * �����յ�ĺ��������
                 */
                if(fromPoint.x == toPoint.x)
                {
                    if(fromPoint.y < toPoint.y)
                        setLocation(j2 - dimension.width / 2, k1 - dimension.height - 3);
                    else
                        setLocation(j2 - dimension.width / 2, k1 + 3);
                }else if(fromPoint.x > toPoint.x)
                    setLocation(j2 - dimension.width / 2, k1 + 3);
                else
                    setLocation(j2 - dimension.width / 2, k1 - dimension.height - 3);
            }
        }
        //����ֱ��
        else
        {
            Dimension dimension1 = getSize();
            if(from != null)
                fromPoint = from.getFromPoint();
            if(to != null)
                toPoint = to.getToPoint();
            setLineColor(g);
            if(fromPoint.x > toPoint.x)
            {
                if(fromPoint.y > toPoint.y)
                {
                    int i = fromPoint.y + 50;
                    int l = fromPoint.x + 10;
                    int l1 = toPoint.x - 10;
                    g.drawLine(fromPoint.x, fromPoint.y, l, fromPoint.y);
                    g.drawLine(l, fromPoint.y, l, i);
                    g.drawLine(l, i, l1, i);
                    g.drawLine(l1, i, l1, toPoint.y);
                    g.drawLine(l1, toPoint.y, toPoint.x, toPoint.y);
                    setLocation(l - (l - l1) / 2 - dimension1.width / 2, i - dimension1.height / 2);
                }else
                {
                    int j = fromPoint.y - 50;
                    int i1 = fromPoint.x + 10;
                    int i2 = toPoint.x - 10;
                    g.drawLine(fromPoint.x, fromPoint.y, i1, fromPoint.y);
                    g.drawLine(i1, fromPoint.y, i1, j);
                    g.drawLine(i1, j, i2, j);
                    g.drawLine(i2, j, i2, toPoint.y);
                    g.drawLine(i2, toPoint.y, toPoint.x, toPoint.y);
                    setLocation(i1 - (i1 - i2) / 2 - dimension1.width / 2, j - dimension1.height / 2);
                }
            }else
            {
                int k = fromPoint.y + (toPoint.y - fromPoint.y) / 2;
                int j1 = fromPoint.x + (toPoint.x - fromPoint.x) / 2;
                g.drawLine(fromPoint.x, fromPoint.y, j1, fromPoint.y);
                g.drawLine(j1, fromPoint.y, j1, toPoint.y);
                g.drawLine(j1, toPoint.y, toPoint.x, toPoint.y);
                setLocation(j1 - dimension1.width / 2, k - dimension1.height / 2);
            }
        }
        Polygon polygon;
        if(from == to)
        {
            fromLoopPoint.x = toPoint.x - 10;
            fromLoopPoint.y = toPoint.y - 10;
            polygon = getArrow(fromLoopPoint, toPoint);
        }else
        {
            polygon = getArrow(fromPoint, toPoint);
        }
        if(selected)
            setLineColor(g);
        else
            g.setColor(Color.white);
        g.fillPolygon(polygon);
        setLineColor(g);
        g.drawPolygon(polygon);
        if(selected)
        {
            int ai[] = {toPoint.x - 3, toPoint.x + 3, toPoint.x + 3, toPoint.x - 3, toPoint.x - 3};
            int ai1[] = {toPoint.y - 3, toPoint.y - 3, toPoint.y + 3, toPoint.y + 3, toPoint.y - 3};
            g.setColor(Color.black);
            g.drawPolygon(ai, ai1, 5);
            int ai2[] = {fromPoint.x - 3, fromPoint.x + 3, fromPoint.x + 3, fromPoint.x - 3, fromPoint.x - 3};
            int ai3[] = {fromPoint.y - 3, fromPoint.y - 3, fromPoint.y + 3, fromPoint.y + 3, fromPoint.y - 3};
            g.setColor(Color.white);
            g.fillPolygon(ai2, ai3, 5);
            g.setColor(Color.black);
            g.drawPolygon(ai2, ai3, 5);
        }
    }

    /**
     * �ж�ѡ��ĵ��Ƿ�������ӵ㷶Χ��
     * @param i -������
     * @param j -������
     * @return int �ж���ʲô��Χ��
     * @roseuid 3DB67338003E
     */
    public int getSelectedAt(int i, int j)
    {
        if(strait)
        {
            boolean flag = fromPoint.x + 3 > i && i > fromPoint.x - 3;
            boolean flag1 = fromPoint.y + 3 > j && j > fromPoint.y - 3;
            if(flag && flag1)
                return 0;//0������(i,j)�������������ʼ�ڵ�ķ�Χ��
            flag = toPoint.x + 8 > i && i > toPoint.x - 8;
            flag1 = toPoint.y + 8 > j && j > toPoint.y - 8;
            if(flag && flag1)
                return 2;//2������(i,j)������������ն˽ڵ�ķ�Χ��
            Polygon polygon = getSelectionZone();
            if(polygon.contains(i, j))
                return 1;//1������(i,j)��������
        }else
        {
            QueryPoint querypoint = new QueryPoint(i, j);
            Rectangle rectangle = from.getBounds();
            Point point = new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height / 2);
            rectangle = to.getBounds();
            Point point1 = new Point(rectangle.x, rectangle.y + rectangle.height / 2);
            boolean flag2 = querypoint.x > point.x && querypoint.x < point.x + 8;
            boolean flag3 = querypoint.x > point1.x - 8 && querypoint.x < point1.x;
            if(flag2 && querypoint.y > point.y - 8 && querypoint.y < point.y + 8)
                return 0;
            if(flag3 && querypoint.y > point1.y - 8 && querypoint.y < point1.y + 8)
                return 2;
            if(point.x > point1.x)
            {
                if(point.y > point1.y)
                {
                    int k = point.y + 50;
                    int j1 = point.x + 10;
                    int l1 = point1.x - 10;
                    if(querypoint.onHorizontalLine(point.x, point.y, j1, point.y) || querypoint.onVerticalLine(j1, point.y, j1, k) || querypoint.onHorizontalLine(j1, k, l1, k)
                            || querypoint.onVerticalLine(l1, k, l1, point1.y) || querypoint.onHorizontalLine(l1, point1.y, point1.x, point1.y))
                        return 1;
                }else
                {
                    int l = point.y - 50;
                    int k1 = point.x + 10;
                    int i2 = point1.x - 10;
                    if(querypoint.onHorizontalLine(point.x, point.y, k1, point.y) || querypoint.onVerticalLine(k1, point.y, k1, l) || querypoint.onHorizontalLine(k1, l, i2, l)
                            || querypoint.onVerticalLine(i2, l, i2, point1.y) || querypoint.onHorizontalLine(i2, point1.y, point1.x, point1.y))
                        return 1;
                }
            }else
            {
                int i1 = point.x + (point1.x - point.x) / 2;
                if(querypoint.onHorizontalLine(point.x, point.y, i1, point.y) || querypoint.onVerticalLine(i1, point.y, i1, point1.y) || querypoint.onHorizontalLine(i1, point1.y, point1.x, point1.y))
                    return 1;
            }
        }
        return 3;
    }

    /**
     * ȡ��ѡ�������
     * @return java.awt.Polygon �����
     * @roseuid 3DB67338005C
     */
    public Polygon getSelectionZone()
    {
        if(to == from)
        {
            //            /**
            //             * Gets the bounds of this component in the form of a
            //             * <code>Rectangle</code> object. The bounds specify this
            //             * component's width, height, and location relative to
            //             * its parent.
            //             * @return a rectangle indicating this component's bounds
            //             * @see #setBounds
            //             * @see #getLocation
            //             * @see #getSize
            //             */
            //            public Rectangle getBounds() {
            //        	return bounds();
            //            }
            //
            //            /**
            //             * @deprecated As of JDK version 1.1,
            //             * replaced by <code>getBounds()</code>.
            //             */
            //            public Rectangle bounds() {
            //        	return new Rectangle(x, y, width, height);
            //            }
            Rectangle rectangle = to.getBounds();
            rectangle.grow(3, 3);
            int ai[] = new int[10];
            int ai2[] = new int[10];
            ai[0] = toPoint.x;
            ai2[0] = toPoint.y - 5;
            ai[1] = toPoint.x - 2;
            ai2[1] = toPoint.y - rectangle.height / 2;
            ai[2] = toPoint.x + rectangle.width / 2;
            ai2[2] = toPoint.y - rectangle.height;
            ai[3] = fromPoint.x + 2;
            ai2[3] = ai2[1];
            ai[4] = fromPoint.x;
            ai2[4] = ai2[0];
            ai[5] = fromPoint.x;
            ai2[5] = ai2[4] + 10;
            ai[6] = ai[3] + 15;
            ai2[6] = ai2[3];
            ai[7] = ai[2];
            ai2[7] = ai2[2] - 15;
            ai[8] = ai[1] - 15;
            ai2[8] = ai2[1];
            ai[9] = ai[0];
            ai2[9] = ai2[0] + 10;
            return new Polygon(ai, ai2, 10);
        }else
        {
            boolean flag = Math.abs(toPoint.y - fromPoint.y) > Math.abs(toPoint.x - fromPoint.x);
            int ai1[] = new int[4];
            int ai3[] = new int[4];
            ai1[0] = fromPoint.x + (flag ? 3 : 0);
            ai3[0] = fromPoint.y + (flag ? 0 : 3);
            ai1[1] = fromPoint.x - (flag ? 3 : 0);
            ai3[1] = fromPoint.y - (flag ? 0 : 3);
            ai1[2] = toPoint.x - (flag ? 3 : 0);
            ai3[2] = toPoint.y - (flag ? 0 : 3);
            ai1[3] = toPoint.x + (flag ? 3 : 0);
            ai3[3] = toPoint.y + (flag ? 0 : 3);
            return new Polygon(ai1, ai3, 4);
        }
    }

    /**
     * Ϊ�ӵ����ý���
     * @param point -���
     * @param point1 -�յ�
     * @param rectangle -����
     * @return java.awt.Point
     * @roseuid 3DB673380070
     */
    public Point intersection(Point point, Point point1, Rectangle rectangle)
    {
        Point point2 = new Point(point.x, point.y);
        float f = 0.0F;
        //�����������ͬһֱ����
        if(point.x == point1.x)
            f = 3.402823E+038F;
        else
            //ȡ��б��
            f = (float)(point1.y - point.y) / (float)(point1.x - point.x);
        if(Math.abs(f) < 1.0F)
        {
            if(point1.x < point.x)
            {
                point2.x -= rectangle.width;
                f = -f;
            }
            point2.x += rectangle.height / 2;
            point2.y += f * (float)(rectangle.width / 2);
        }else
        {
            if(point1.y < point.y)
            {
                point2.y -= rectangle.height;
                f = -f;
            }else
            {
                point2.y += 20;
            }
            point2.y += rectangle.width / 2;
            point2.x += (float)(rectangle.height / 2) / f;
        }
        return point2;

    }

    /**
     * ȡ�ü�ͷ
     * @param point -���
     * @param point1 -�յ�
     * @return java.awt.Polygon
     * @roseuid 3DB6733800A2
     */
    public Polygon getArrow(Point point, Point point1)
    {
        Polygon polygon = new Polygon();
        if(strait)
        {
            int i = point1.x - point.x;
            int j = point1.y - point.y;
            double d = Math.sqrt(i * i + j * j);
            double d1 = (double)(5 * i) / d;
            double d2 = (double)(5 * j) / d;
            double d3 = (double)point1.x - d1;
            double d4 = (double)point1.y - d2;
            polygon.addPoint((int)(d3 - d2), (int)(d4 + d1));
            polygon.addPoint((int)(d3 + d1), (int)(d4 + d2));
            polygon.addPoint((int)(d3 + d2), (int)(d4 - d1));
            polygon.addPoint((int)(d3 - d2), (int)(d4 + d1));
        }else
        {
            polygon.addPoint(point1.x, point1.y);
            polygon.addPoint(point1.x - 7, point1.y - 3);
            polygon.addPoint(point1.x - 7, point1.y + 3);
            polygon.addPoint(point1.x, point1.y);
        }
        return polygon;

    }

    /**
     * �ڲ��࣬ʵ��������
     */
    class NameMouseListener extends MouseAdapter
    {

        NameMouseListener()
        {}

        public void mouseReleased(MouseEvent mouseevent)
        {
            //�����ӵ���ɫ����Ϊ��ɫ
            setColor(Color.blue);
        }

        /**
         * ��갴��
         * @param mouseevent -����¼�
         * @roseuid 3DB673380106
         */
        public void mousePressed(MouseEvent mouseevent)
        {
            setColor(Color.red);
        }
    }

    /**
     * �ڲ��࣬��ѯ��
     */
    class QueryPoint extends Point
    {

        QueryPoint(int i, int j)
        {
            super(i, j);
        }

        /**
         * ��ˮƽ�����ϲ�ѯ
         * @param i -��һ����ĺ�����
         * @param j -��һ�����������
         * @param k -�ڶ�����ĺ�����
         * @param l -�ڶ������������
         * @return boolean �����Ƿ���������֮��
         * @roseuid 3DB67338014D
         */
        boolean onHorizontalLine(int i, int j, int k, int l)
        {
            return Math.abs(j - y) < 6 && (x <= k && x >= i || x <= i && x >= k);
        }

        /**
         * �ڴ�ֱ�����ϲ�ѯ
         * @param i -��һ����ĺ�����
         * @param j -��һ�����������
         * @param k -�ڶ�����ĺ�����
         * @param l -�ڶ������������
         * @return boolean - �����Ƿ���������֮��
         * @roseuid 3DB67338016B
         */
        boolean onVerticalLine(int i, int j, int k, int l)
        {
            return Math.abs(i - x) < 6 && (y <= l && y >= j || y <= j && y >= l);
        }
    }
}