/**
 * ���ɳ���GraphLabelComponent.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Χ��,ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.*;
import javax.swing.*;
import java.util.StringTokenizer;

/**
 * <p> ��ǩ������ڵ����ʾ��ǩ������ʵ���������߼̳��˱���ǩ�����ά����ǩ��ͼ�Ρ����塢λ�ü���С�����ԡ� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
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
     * ������
     * @roseuid 3DB672EA02DB
     */
    public GraphLabelComponent()
    {
        this.setSize(new Dimension(1, 1));
        //stringColor=Color.blue; //lm 20040520
        this.setBackground(Color.white);
    }

    /**
     * ȷ��label�����component��λ��
     * @param component1 - ����Component�Ķ���
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
     * �����ַ�����ɫ
     * @param color - ����Color����
     * @roseuid 3DB672EA02EF
     */
    public void setColor(Color color)
    {
        stringColor = color;
        repaint();
    }

    /**
     * ����ѡ����ǩ������
     * @param g - ����Graphics����
     * @roseuid 3DB672EA0303
     */

    public void paint(Graphics g)
    {

        if(label != null)
        {
            if(selected)//ѡ��״̬��
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

            setSize(i, j);//���ô�С
            position(component);//��λ
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
                    //�ڱ�ǩ���滭��
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
     * ���±�ǩ�Ĵ�С
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
     * ���ñ�ǩ
     * @param s - String��
     * @roseuid 3DB672EA032B
     */
    void setLabel(String s)
    {
        label = s;
        repaint();
    }

    /**
     * ��ȡ��ǩ
     * @return java.lang.String
     * @roseuid 3DB672EA033F
     */
    String getLabel()
    {
        return label;
    }

    /**
     * ���صõ��ı�ǩ���ַ���
     * @return java.lang.String
     * @roseuid 3DB672EA0349
     */
    public String toString()
    {
        return getLabel();
    }

}