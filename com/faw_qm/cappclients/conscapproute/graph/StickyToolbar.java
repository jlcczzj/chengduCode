/**
 * ���ɳ��� StickyToolbar.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;

/**
 * <p> �༭·��ͼ������İ�ť����� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class StickyToolbar extends JPanel implements ActionListener
{
    public java.awt.event.ActionListener actionListener;

    private GridLayout gridLayout1 = new GridLayout(5, 1, 10, 10);

    /**
     * ���캯��
     */
    public StickyToolbar()
    {
        try
        {
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * ��ʼ��
     * @throws Exception
     */
    void jbInit()
    {
        this.setLayout(gridLayout1);
    }

    /**
     * �÷��������򹤾���������ͼԪ��ť
     * @param s - ͼԪ��ť�Ķ�����ʶ
     * @param image - ͼԪ��ť��ͼ���ʶ
     * @param s1 - ͼԪ��ť�İ�����ʾ
     * @roseuid 3DAF6E7600EB
     */
    public void add(String s, ImageIcon image, String s1)
    {
        ToolbarButton toolbarbutton = new ToolbarButton();
        toolbarbutton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        toolbarbutton.setActionCommand(s);
        toolbarbutton.addActionListener(actionListener);
        toolbarbutton.addActionListener(this);
        toolbarbutton.setIcon(image);
        toolbarbutton.setToolTipText(s1);
        this.add(toolbarbutton);
    }

    /**
     * �÷��������򹤾�����������ͨ������ť
     * @param s - ��ť�Ķ�����ʶ
     * @param image - ��ť��ͼ���ʶ
     * @param s1 - ��ť�İ�����ʾ
     * @roseuid 3DAF6E7600EB
     */
    public void addJButton(String s, ImageIcon image, String s1)
    {
        JButton toolbarbutton = new JButton();
        toolbarbutton.setActionCommand(s);
        toolbarbutton.addActionListener(actionListener);
        toolbarbutton.addActionListener(this);
        toolbarbutton.setIcon(image);
        toolbarbutton.setToolTipText(s1);
        this.add(toolbarbutton);
    }

    /**
     * ��ͼԪ��ť��ӷָ���
     * @roseuid 3DAF6E7600FF
     */
    public void addSeparator()
    {
        JSeparator separator = new JSeparator();
        separator.setOrientation(JSeparator.VERTICAL);
        separator.setSize(2, 10);
        this.add(separator);

    }

    /**
     * ���ð�ť�Ƿ�ѡ��
     * @param s - ��ť�Ķ�����ʶ
     * @roseuid 3DAF6E760109
     */
    public void setSelected(String s)
    {
        Component acomponent[] = getComponents();
        for(int i = 0;i < acomponent.length;i++)
            if(acomponent[i] instanceof ToolbarButton)
                if(((ToolbarButton)acomponent[i]).getActionCommand().equals(s))
                {
                    ((ToolbarButton)acomponent[i]).setSelected(true);
                    ((ToolbarButton)acomponent[i]).setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

                }else
                {
                    ((ToolbarButton)acomponent[i]).setSelected(false);
                    ((ToolbarButton)acomponent[i]).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                }

    }

    /**
     * ȡ����ť��ѡ��״̬
     * @roseuid 3DAF6E760113
     */
    public void unselectAll()
    {
        Component acomponent[] = getComponents();
        for(int i = 0;i < acomponent.length;i++)
            if(acomponent[i] instanceof ToolbarButton)
            {
                ((ToolbarButton)acomponent[i]).setSelected(false);
                ((ToolbarButton)acomponent[i]).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            }

    }

    /**
     * ���һ������������
     * @param actionlistener - ����������
     * @roseuid 3DAF6E760114
     */
    public void addActionListener(ActionListener actionlistener)
    {
        actionListener = actionlistener;

    }

    /**
     * �����¼��Ĵ�����
     * @param actionevent - ������Ķ����¼�
     * @roseuid 3DAF6E760127
     */
    public void actionPerformed(ActionEvent actionevent)
    {
        Object e = actionevent.getSource();
        if(e instanceof ToolbarButton)
        {
            ToolbarButton toolbarbutton = (ToolbarButton)actionevent.getSource();
            Component acomponent[] = getComponents();
            for(int i = 0;i < acomponent.length;i++)
                if(acomponent[i] instanceof ToolbarButton)
                {
                    ((ToolbarButton)acomponent[i]).setSelected(false);
                    ((ToolbarButton)acomponent[i]).setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                }

            toolbarbutton.setSelected(true);
            toolbarbutton.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        }
    }

    /**
     * �ڲ��࣬������ʾ�������ϵķָ���
     */
    class Separator extends JSeparator
    {

        /**
         * �ڲ���Ĺ�����
         * @roseuid 3DAF6E760145
         */
        Separator()
        {

        }

        /**
         * ����ָ�������С�ߴ�
         * @return java.awt.Dimension
         * @roseuid 3DAF6E760146
         */
        public Dimension getMinimumSize()
        {
            return new Dimension(2, 2);
        }
    }

}