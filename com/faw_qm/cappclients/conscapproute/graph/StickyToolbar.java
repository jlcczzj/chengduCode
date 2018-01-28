/**
 * 生成程序 StickyToolbar.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * <p> 编辑路线图面板左侧的按钮组面板 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class StickyToolbar extends JPanel implements ActionListener
{
    public java.awt.event.ActionListener actionListener;

    private GridLayout gridLayout1 = new GridLayout(5, 1, 10, 10);

    /**
     * 构造函数
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
     * 初始化
     * @throws Exception
     */
    void jbInit()
    {
        this.setLayout(gridLayout1);
    }

    /**
     * 该方法用来向工具栏面板添加图元按钮
     * @param s - 图元按钮的动作标识
     * @param image - 图元按钮的图象标识
     * @param s1 - 图元按钮的帮助提示
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
     * 该方法用来向工具栏面板添加普通动作按钮
     * @param s - 按钮的动作标识
     * @param image - 按钮的图象标识
     * @param s1 - 按钮的帮助提示
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
     * 给图元按钮添加分隔条
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
     * 设置按钮是否被选中
     * @param s - 按钮的动作标识
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
     * 取消按钮的选中状态
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
     * 添加一个动作监听器
     * @param actionlistener - 动作监听器
     * @roseuid 3DAF6E760114
     */
    public void addActionListener(ActionListener actionlistener)
    {
        actionListener = actionlistener;

    }

    /**
     * 动作事件的处理方法
     * @param actionevent - 欲处理的动作事件
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
     * 内部类，用来显示工具栏上的分隔条
     */
    class Separator extends JSeparator
    {

        /**
         * 内部类的构造器
         * @roseuid 3DAF6E760145
         */
        Separator()
        {

        }

        /**
         * 定义分割条的最小尺寸
         * @return java.awt.Dimension
         * @roseuid 3DAF6E760146
         */
        public Dimension getMinimumSize()
        {
            return new Dimension(2, 2);
        }
    }

}