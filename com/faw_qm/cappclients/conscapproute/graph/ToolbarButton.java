/** 
 * 生成程序 ToolbarButton.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.FilteredImageSource;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.faw_qm.clients.util.ColorDarkenFilter;
import com.faw_qm.clients.util.ColorFadeFilter;

/**
 * <p> Title:工具拦按钮 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public final class ToolbarButton extends JLabel implements java.awt.event.MouseListener, MouseMotionListener
{

    /**
     * int型常量，用于标志按钮的正常状态
     */
    public static final int FLAT = 0;

    /**
     * int型变量，用于标志按钮的按钮 状态
     */
    public static final int RAISED = 1;

    /**
     * int型常量，标志按钮和其容器边界的距离
     */
    private int thickness;

    /**
     * 布尔型变量，标志按钮的选中状态
     */
    boolean selected;

    /**
     * string型变量，标志按钮的动作名称
     */
    java.lang.String actionCommand;

    /**
     * 私有的int 型变量，标志按钮的显示样式
     */
    private int look;

    /**
     * 布尔性变量，标志按钮是否处于enbale状态
     */
    boolean enabled;

    private static ColorFadeFilter fade;

    private static ColorDarkenFilter darken;

    private Dimension prefSize;

    private Image image;

    private Image disabledImage;

    private Image selectedImage;

    private java.awt.event.ActionListener actionListener;

    /**
     * 缺省的构造器方法
     * @roseuid 3DAF726803BC
     */
    public ToolbarButton()
    {

        this(null);
    }

    /**
     * 构造器方法
     * @param icon1 - 按钮上显示的图标
     * @roseuid 3DAF726803B2
     */
    public ToolbarButton(ImageIcon icon1)
    {
        prefSize = new Dimension(0, 0);
        //setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        this.setSize(1, 1);
        this.setMaximumSize(new Dimension(10, 10));
        this.setMinimumSize(new Dimension(10, 10));
        this.setPreferredSize(new Dimension(10, 10));
        thickness = 2;
        selected = false;
        actionCommand = "?";
        look = 1;
        enabled = true;
        if(icon1 != null)
            setIcon(icon1);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * 构造器方法
     * @param icon1 - 按钮上显示的图象
     * @param i - 按钮的显示样式
     * @roseuid 3DAF72680394
     */
    public ToolbarButton(ImageIcon icon1, int i)
    {
        this(icon1);
        look = i;
    }

    /**
     * 设置动作命令名称
     * @param s - 动作名称
     * @roseuid 3DAF726803C6
     */
    public void setActionCommand(java.lang.String s)
    {
        actionCommand = s;
    }

    /**
     * 获得激发的动作名称
     * @return java.lang.String
     * @roseuid 3DAF726803DA
     */
    public String getActionCommand()
    {
        return actionCommand;
    }

    /**
     * 设置按钮的显示图标
     * @param icon1 - 按钮上显示的图标
     * @roseuid 3DAF72690006
     */

    public void setImage(Image icon1)
    {
        MediaTracker mediatracker = new MediaTracker(this);
        Image image1 = icon1;
        try
        {
            mediatracker.addImage(image1, 0);
            mediatracker.waitForID(0);
        }catch(InterruptedException interruptedexception)
        {
            interruptedexception.printStackTrace();
        }
        image = image1;
        if(fade == null)
            fade = new ColorFadeFilter(0.5D);
        FilteredImageSource filteredimagesource = new FilteredImageSource(image1.getSource(), fade);
        disabledImage = createImage(filteredimagesource);
        mediatracker = new MediaTracker(this);
        try
        {
            mediatracker.addImage(disabledImage, 0);
            mediatracker.waitForID(0);
        }catch(InterruptedException interruptedexception1)
        {
            interruptedexception1.printStackTrace();
        }
        if(darken == null)
            darken = new ColorDarkenFilter(0.25D);
        FilteredImageSource filteredimagesource1 = new FilteredImageSource(image1.getSource(), darken);
        selectedImage = createImage(filteredimagesource1);
        mediatracker = new MediaTracker(this);
        try
        {
            mediatracker.addImage(selectedImage, 0);
            mediatracker.waitForID(0);
        }catch(InterruptedException interruptedexception2)
        {
            interruptedexception2.printStackTrace();
        }
        if(isShowing())
        {
            invalidate();
            getParent().validate();
        }
    }

    /**
     * 判断按钮是否处于选种状态
     * @return boolean
     * @roseuid 3DAF72690038
     */
    public boolean isSelected()
    {
        return selected;
    }

    /**
     * 设置按钮的状态
     * @param flag - 布尔型变量，标志按钮的状态
     * @roseuid 3DAF72690042
     */
    public void setSelected(boolean flag)
    {
        boolean flag1 = selected != flag;
        selected = flag;

        if(flag1 && isShowing())
            repaint();

    }

    /**
     * 设置按钮是否处于可用状态
     * @param flag - 布尔型变量，标志按钮是否处于可用状态
     * @roseuid 3DAF72690056
     */
    public void setEnabled(boolean flag)
    {
        enabled = flag;
        if(isShowing())
            repaint();
    }

    /**
     * 判断按钮是否处于可用状态
     * @return boolean
     * @roseuid 3DAF7269006A
     */
    public boolean isEnabled()
    {
        return enabled;
    }

    /**
     * 该方法用于确定和刷新界面的显示
     * @param g - 图形工具
     * @roseuid 3DAF7269007E
     */

    public void paint(Graphics g)
    {
        super.paint(g);
        if(isSelected())
        {
            paintInset();
            return;
        }
        if(look == 0)
        {
            paintFlat();
            return;
        }
        if(look == 1)
            paintRaised();
    }

    /**
     * 可用状态下，按下按钮时，刷新按钮的外观显示
     * @roseuid 3DAF7269009C
     */

    public void paintInset()
    {
        Point point = findUpperLeft();
        Graphics g = getGraphics();
        Dimension dimension = getSize();
        //不存在图象，返回
        if(image == null)
            return;
        //存在得到的图象
        if(g != null)
        {
            //按钮被按下时显示状态的改变
            if(look != 0)
            {
                g.setColor(Color.black);
                g.drawLine(1, 0, dimension.width - 2, 0);
                g.drawLine(dimension.width - 1, 1, dimension.width - 1, dimension.height - 2);
                g.drawLine(dimension.width - 2, dimension.height - 1, 1, dimension.height - 1);
                g.drawLine(0, dimension.height - 2, 0, 1);
            }
            //被激活时的状态
            if(enabled)
            {
                if(look == 0)
                {
                    g.setColor(Color.lightGray);
                    g.fill3DRect(1, 1, dimension.width - 2, dimension.height - 2, false);
                    g.drawImage(image, point.x + thickness, point.y + thickness, this);
                }else
                {
                    g.setColor(Color.lightGray);
                    g.fill3DRect(1, 1, dimension.width - 2, dimension.height - 2, false);
                    g.drawImage(selectedImage, point.x + thickness, point.y + thickness, this);
                }
            }else
            {
                g.setColor(Color.lightGray);
                g.fill3DRect(1, 1, dimension.width - 2, dimension.height - 2, false);
                g.drawImage(disabledImage, point.x + thickness, point.y + thickness, this);
            }
            g.dispose();
        }
    }

    /**
     * 可用状态下，在按钮上释放鼠标时刷新按钮的外观显示
     * @roseuid 3DAF726900B0
     */

    public void paintRaised()
    {
        Point point = findUpperLeft();
        Graphics g = getGraphics();
        if(image == null)
            return;
        if(g != null)
        {
            Dimension dimension = getSize();
            if(look != 0)
            {
                g.setColor(Color.black);
                g.drawLine(1, 0, dimension.width - 2, 0);
                g.drawLine(dimension.width - 1, 1, dimension.width - 1, dimension.height - 2);
                g.drawLine(dimension.width - 2, dimension.height - 1, 1, dimension.height - 1);
                g.drawLine(0, dimension.height - 2, 0, 1);
            }
            g.setColor(Color.lightGray);
            g.fill3DRect(1, 1, dimension.width - 2, dimension.height - 2, true);
            if(enabled)
                g.drawImage(image, point.x + thickness, point.y + thickness, this);
            else
                g.drawImage(disabledImage, point.x + thickness, point.y + thickness, this);
            g.dispose();
        }
    }

    /**
     * 当鼠标移出按钮上时，刷新按钮的外观显示
     * @roseuid 3DAF726900BA
     */

    public void paintFlat()
    {

        Point point = findUpperLeft();

        Graphics g = getGraphics();
        if(image == null)
            return;
        if(g != null)
        {
            Dimension dimension = getSize();
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, dimension.width, dimension.height);
            if(enabled)
                g.drawImage(image, point.x + thickness, point.y + thickness, this);
            else
                g.drawImage(disabledImage, point.x + thickness, point.y + thickness, this);
            g.dispose();
        }
    }

    /**
     * 动作事件的处理方法
     * @roseuid 3DAF726900CE
     */
    public void processActionEvent()
    {
        if(actionListener != null)
            actionListener.actionPerformed(new ActionEvent(this, 1001, actionCommand));
    }

    /**
     * 添加动作监听器
     * @param actionlistener - 动作监听器
     * @roseuid 3DAF726900D8
     */
    public synchronized void addActionListener(java.awt.event.ActionListener actionlistener)
    {
        actionListener = AWTEventMulticaster.add(actionListener, actionlistener);
    }

    /**
     * 删除动作监听器
     * @param actionlistener - 动作监听器
     * @roseuid 3DAF726900F6
     */
    public synchronized void removeActionListener(ActionListener actionlistener)
    {
        actionListener = AWTEventMulticaster.remove(actionListener, actionlistener);
    }

    /**
     * 获得按钮的左上端的端点
     * @return java.awt.Point
     * @roseuid 3DAF7269010A
     */
    private java.awt.Point findUpperLeft()
    {
        Dimension dimension = getSize();
        return new Point(dimension.width / 2 - prefSize.width / 2, dimension.height / 2 - prefSize.height / 2);
    }

    /**
     * 鼠标按下事件
     * @param mouseevent - 鼠标事件
     * @roseuid 3DAF7269011E
     */
    public void mousePressed(MouseEvent mouseevent)
    {
        if(enabled)
            paintInset();
        // setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

    }

    /**
     * 鼠标点击事件
     * @param mouseevent - 鼠标事件
     * @roseuid 3DAF72690132
     */
    public void mouseClicked(MouseEvent mouseevent)
    {
    // setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    // setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

    /**
     * 释放鼠标事件
     * @param mouseevent - 鼠标事件
     * @roseuid 3DAF72690151
     */
    public void mouseReleased(MouseEvent mouseevent)
    {
        if(enabled)
        {
            processActionEvent();

            // setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

            paintRaised();
        }
    }

    /**
     * 拖拽鼠标事件
     * @param mouseevent - 鼠标事件
     * @roseuid 3DAF7269016F
     */
    public void mouseDragged(MouseEvent mouseevent)
    {

    }

    /**
     * 鼠标移动事件
     * @param mouseevent - 鼠标事件
     * @roseuid 3DAF72690183
     */
    public void mouseMoved(MouseEvent mouseevent)
    {

    }

    /**
     * 鼠标移入事件
     * @param mouseevent - 鼠标事件
     * @roseuid 3DAF726901A1
     */
    public void mouseEntered(MouseEvent mouseevent)
    {
        if(!isSelected() && enabled)
            paintRaised();
    }

    /**
     * 鼠标事件
     * @param mouseevent - 鼠标事件
     * @roseuid 3DAF726901BF
     */
    public void mouseExited(MouseEvent mouseevent)
    {
        if(!isSelected() && look == 0)
            paintFlat();
    }

}