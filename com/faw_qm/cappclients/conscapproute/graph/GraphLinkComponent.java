/**生成程序GraphLinkComponent.java
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
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.faw_qm.framework.exceptions.QMException;

/**
 * <p> 图元连接组件，画出连接的连线 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class GraphLinkComponent extends GraphLabelComponent
{
    /** 是否画直线 */
    boolean strait;

    /** 起始点 */
    public Point fromPoint;

    /** 终点 */
    public Point toPoint;

    public Point fromLoopPoint;

    /** 连接 */
    GraphLink link;

    /** 起始点组件 */
    GraphNodeComponent from;

    /** 终点组件 */
    GraphNodeComponent to;

    /** 行为监听 */
    transient java.awt.event.ActionListener actionListener;

    /**
     * 构造器
     */
    public GraphLinkComponent()
    {
        fromPoint = new Point(0, 0);
        toPoint = new Point(0, 0);
        strait = true;
        fromLoopPoint = new Point();
    }

    /**
     * 构造器
     * @param graphlink GraphLink类的对象
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
     * 设置链接
     * @param graphlink GraphLink的对象
     */
    public void setGraphLink(GraphLink graphlink)
    {
        link = graphlink;
    }

    /**
     * 获得链接
     * @return link 连接
     * @roseuid 3DB673340075
     */
    public GraphLink getGraphLink()
    {
        return link;
    }

    /**
     * 添加行为监听器
     * @param actionlistener ActionListener的对象
     */
    public void addActionListener(java.awt.event.ActionListener actionlistener)
    {
        actionListener = AWTEventMulticaster.add(actionListener, actionlistener);
    }

    /**
     * 设置链接的前驱节点
     * @param graphnodecomponent GraphNodeComponent的对象
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
     * 获取前驱节点
     * @return from 起始点组件
     * @roseuid 3DB6733503DD
     */
    public GraphNodeComponent getFrom()
    {
        return from;
    }

    /**
     * 设置后继节点
     * @param graphnodecomponent GraphNodeComponent的对象
     * @throws QMException 
     * @throws QMPropertyVetoException
     */
    public void setTo(GraphNodeComponent graphnodecomponent) throws QMException
    {
        to = graphnodecomponent;
        ((DefaultGraphLink)link).setSuccessor(graphnodecomponent.getGraphNode());
    }

    /**
     * 获取后继节点
     * @return GraphNodeComponent 终点组件
     * @roseuid 3DB67337037C
     */
    public GraphNodeComponent getTo()
    {
        return to;
    }

    /**
     * 判断是否画直线
     * @param flag -是否画直线
     * @roseuid 3DB673370390
     */
    public void setStraitLine(boolean flag)
    {
        strait = flag;
    }

    /**
     * 判断是否是直线
     * @return boolean
     * @roseuid 3DB6733703A4
     */
    public boolean isStraitLine()
    {
        return strait;
    }

    /**
     * 设置起始节点
     * @param i 开始接点横坐标
     * @param j 开始接点纵坐标
     * @roseuid 3DB6733703B8
     */
    public void setStartPoint(int i, int j)
    {
        from = null;
        fromPoint.x = i;
        fromPoint.y = j;
    }

    /**
     * 设置最终节点
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
     * 设置连线颜色
     * @param g -java.awt.Graphics
     * @roseuid 3DB67338000C
     */
    public void setLineColor(Graphics g)
    {
        if(selected)
        {
            //选中状态下的链接颜色为深红色
            g.setColor(Color.red.darker());
        }else
        {
            //非选中状态下的链接颜色为黑色
            g.setColor(Color.black);
        }
    }

    /**
     * 为连接画线
     * @param g -java.awt.Graphics
     * @roseuid 3DB67338002A
     */
    public void drawLine(Graphics g)
    {
        super.paintComponent(g);
        if(strait)//画直线
        {
            Dimension dimension = getSize();
            Rectangle rectangle = null;
            Rectangle rectangle1 = null;
            setLineColor(g);
            //起始接点不为空
            if(from != null)
            {
                rectangle = from.getBounds();
                rectangle.grow(3, 3);//将起始节点矩形的宽高设置为三个像素
            }
            //终接点不为空
            if(to != null)
            {
                rectangle1 = to.getBounds();
                rectangle1.grow(3, 3);//将终端节点矩形的宽高设置为三个像素
            }
            //起示节点和终端节点是同一个点，画弧
            if(from == to)
            {
                fromPoint = from.getFromPoint();
                toPoint = to.getToPoint();
                g.drawArc(rectangle1.x - 8, rectangle1.y - 20, rectangle1.width + 16, rectangle1.height + 10, 320, 250);
                setLocation((rectangle.x + rectangle.width / 2) - dimension.width / 2, rectangle.y - rectangle.width / 2 - dimension.height / 2);
            }
            //起始节点和终端节点不同，画线
            else
            {
                /*
                 * 为起始接点设置坐标
                 */
                if(from != null)
                {
                    fromPoint.x = rectangle.x + rectangle.width / 2;
                    fromPoint.y = rectangle.y + rectangle.height / 2;
                }
                /*
                 * 为终接点设置坐标
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
                 * 在两接点间画线
                 */
                g.drawLine(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
                /*
                 * 设置起点和终点的中间接点纵坐标
                 */
                int k1 = fromPoint.y + (toPoint.y - fromPoint.y) / 2;
                /*
                 * 设置起点和终点的中间接点横坐标
                 */
                int j2 = fromPoint.x + (toPoint.x - fromPoint.x) / 2;
                /*
                 * 起点和终点的横坐标相等
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
        //不画直线
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
     * 判断选择的点是否在组件接点范围内
     * @param i -横坐标
     * @param j -纵坐标
     * @return int 判断在什么范围内
     * @roseuid 3DB67338003E
     */
    public int getSelectedAt(int i, int j)
    {
        if(strait)
        {
            boolean flag = fromPoint.x + 3 > i && i > fromPoint.x - 3;
            boolean flag1 = fromPoint.y + 3 > j && j > fromPoint.y - 3;
            if(flag && flag1)
                return 0;//0表明点(i,j)在链接组件的起始节点的范围内
            flag = toPoint.x + 8 > i && i > toPoint.x - 8;
            flag1 = toPoint.y + 8 > j && j > toPoint.y - 8;
            if(flag && flag1)
                return 2;//2表明点(i,j)在链接组件的终端节点的范围内
            Polygon polygon = getSelectionZone();
            if(polygon.contains(i, j))
                return 1;//1表明点(i,j)在链接上
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
     * 取得选择的区域
     * @return java.awt.Polygon 多边形
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
     * 为接点设置交点
     * @param point -起点
     * @param point1 -终点
     * @param rectangle -矩形
     * @return java.awt.Point
     * @roseuid 3DB673380070
     */
    public Point intersection(Point point, Point point1, Rectangle rectangle)
    {
        Point point2 = new Point(point.x, point.y);
        float f = 0.0F;
        //如果两个点在同一直线上
        if(point.x == point1.x)
            f = 3.402823E+038F;
        else
            //取得斜率
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
     * 取得箭头
     * @param point -起点
     * @param point1 -终点
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
     * 内部类，实现鼠标监听
     */
    class NameMouseListener extends MouseAdapter
    {

        NameMouseListener()
        {}

        public void mouseReleased(MouseEvent mouseevent)
        {
            //将链接的颜色设置为蓝色
            setColor(Color.blue);
        }

        /**
         * 鼠标按下
         * @param mouseevent -鼠标事件
         * @roseuid 3DB673380106
         */
        public void mousePressed(MouseEvent mouseevent)
        {
            setColor(Color.red);
        }
    }

    /**
     * 内部类，查询点
     */
    class QueryPoint extends Point
    {

        QueryPoint(int i, int j)
        {
            super(i, j);
        }

        /**
         * 在水平方向上查询
         * @param i -第一个点的横坐标
         * @param j -第一个点的纵坐标
         * @param k -第二个点的横坐标
         * @param l -第二个点的纵坐标
         * @return boolean 返回是否在两个点之间
         * @roseuid 3DB67338014D
         */
        boolean onHorizontalLine(int i, int j, int k, int l)
        {
            return Math.abs(j - y) < 6 && (x <= k && x >= i || x <= i && x >= k);
        }

        /**
         * 在垂直方向上查询
         * @param i -第一个点的横坐标
         * @param j -第一个点的纵坐标
         * @param k -第二个点的横坐标
         * @param l -第二个点的纵坐标
         * @return boolean - 返回是否在两个点之间
         * @roseuid 3DB67338016B
         */
        boolean onVerticalLine(int i, int j, int k, int l)
        {
            return Math.abs(i - x) < 6 && (y <= l && y >= j || y <= j && y >= l);
        }
    }
}