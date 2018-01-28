/** 
 * ���ɳ��� GraphComponent.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import javax.swing.JComponent;
import java.awt.Graphics;

/**
 * <p> ͼԪ������ڵ�ͼԪ������ͼԪ���̳��˱��� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class GraphComponent extends JComponent
{
    boolean selected;

    /**
     * ������
     * @roseuid 3DB672CC006B
     */
    public GraphComponent()
    {
        selected = false;
        setSize(0, 0);
    }

    /**
     * �Ƿ��ѡ
     * @param falg - boolean�ͣ���������Ƿ��ѡ
     * @roseuid 3DB672CC0075
     */
    public void setSelected(boolean falg)
    {
        selected = falg;
        repaint();
    }

    /**
     * �Ƿ�ѡ�У����ز�����
     * @return boolean
     * @roseuid 3DB672CC0080
     */
    public boolean isSelected()
    {
        return selected;
    }

    /**
     * �������
     * @param g ����
     * @roseuid 3DB672CC0089
     */
    public void update(Graphics g)
    {
        paint(g);
    }
}