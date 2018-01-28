/**
 * 生成程序GraphLabelComponent.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司范围内,使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.*;
import javax.swing.*;
import java.util.StringTokenizer;

/**
 * <p> 标签组件。节点的显示标签是它的实例。连接线继承了本标签组件。维护标签的图形、字体、位置及大小等属性。 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class GraphLabelComponent extends GraphComponent
{
    String label;

    int ascent;

    int descent;

    JComponent component;

    Color stringColor;

    private static final Font BOLD_FONT = new Font("Dialoge", 1, 15);

    private static final Font NORMAL_FONT = new Font("Dialoge", 0, 15);

    /**
     * 构造器
     * @roseuid 3DB672EA02DB
     */
    public GraphLabelComponent()
    {
        this.setSize(new Dimension(1, 1));
        //stringColor=Color.blue; //lm 20040520
        this.setBackground(Color.white);
    }

    /**
     * 确定label相对于component的位置
     * @param component1 - 传入Component的对象
     * @roseuid 3DB672EA02E5
     */
    public void position(JComponent component1)
    {
        if(component1 != null)
        {
            component = component1;
            Rectangle rectangle = component1.getBounds();
            int i = (rectangle.x + rectangle.width / 2) - getSize().width / 2;
            setLocation(i, rectangle.y + rectangle.height);
        }
    }

    /**
     * 设置字符的颜色
     * @param color - 传入Color对象
     * @roseuid 3DB672EA02EF
     */
    public void setColor(Color color)
    {
        stringColor = color;
        repaint();
    }

    /**
     * 设置选定标签的属性
     * @param g - 传入Graphics对象
     * @roseuid 3DB672EA0303
     */

    public void paint(Graphics g)
    {

        if(label != null)
        {
            if(selected)//选中状态下
                g.setFont(BOLD_FONT);
            else
                g.setFont(NORMAL_FONT);
            int i = 0;
            int j = 0;
            FontMetrics fontmetrics = g.getFontMetrics();
            ascent = fontmetrics.getAscent();
            descent = fontmetrics.getDescent();
            for(StringTokenizer stringtokenizer = new StringTokenizer(label, "\n");stringtokenizer.hasMoreTokens();)
            {
                j = j + ascent + descent + 5;
                int k = fontmetrics.stringWidth(stringtokenizer.nextToken());
                if(i < k)
                    i = k + 29;
            }

            setSize(i, j);//设置大小
            position(component);//定位
            super.paint(g);
            g.clearRect(0, 0, i, j);
            g.setColor(stringColor);
            int l = ascent;
            boolean flag = false;
            StringTokenizer stringtokenizer1 = new StringTokenizer(label, "\n");
            boolean flag1 = true;
            while(stringtokenizer1.hasMoreTokens())
            {
                String s = stringtokenizer1.nextToken();
                int j1 = fontmetrics.stringWidth(s);
                int i1 = (i - j1) / 2;
                if(flag1)
                {
                    g.setColor(stringColor);
                    //在标签下面画线
                    //g.drawLine(i1, ascent + 2, i1 + j1, ascent + 2); //lm
                    // 20040520
                    flag1 = false;
                }else
                {
                    g.setColor(Color.black);
                }

                g.drawString(s, i1, l);
                l = l + ascent + 2;
            }
        }
    }

    /**
     * 更新标签的大小
     * @roseuid 3DB672EA0317
     */
    void updateSize()
    {
        Graphics g = getGraphics();
        if(g != null && label != null)
        {
            FontMetrics fontmetrics = g.getFontMetrics();
            ascent = fontmetrics.getAscent();
            StringTokenizer stringtokenizer = new StringTokenizer(label, "\n");
            int i = stringtokenizer.countTokens();
            setSize(fontmetrics.stringWidth(label), (ascent + fontmetrics.getDescent() + 5) * i);
            position(component);
        }
    }

    /**
     * 设置标签
     * @param s - String型
     * @roseuid 3DB672EA032B
     */
    void setLabel(String s)
    {
        label = s;
        repaint();
    }

    /**
     * 获取标签
     * @return java.lang.String
     * @roseuid 3DB672EA033F
     */
    String getLabel()
    {
        return label;
    }

    /**
     * 返回得到的标签的字符串
     * @return java.lang.String
     * @roseuid 3DB672EA0349
     */
    public String toString()
    {
        return getLabel();
    }

}