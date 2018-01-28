/** 
 * ���ɳ��� ToolbarButton.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p> Title:��������ť </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public final class ToolbarButton extends JLabel implements java.awt.event.MouseListener, MouseMotionListener
{

    /**
     * int�ͳ��������ڱ�־��ť������״̬
     */
    public static final int FLAT = 0;

    /**
     * int�ͱ��������ڱ�־��ť�İ�ť ״̬
     */
    public static final int RAISED = 1;

    /**
     * int�ͳ�������־��ť���������߽�ľ���
     */
    private int thickness;

    /**
     * �����ͱ�������־��ť��ѡ��״̬
     */
    boolean selected;

    /**
     * string�ͱ�������־��ť�Ķ�������
     */
    java.lang.String actionCommand;

    /**
     * ˽�е�int �ͱ�������־��ť����ʾ��ʽ
     */
    private int look;

    /**
     * �����Ա�������־��ť�Ƿ���enbale״̬
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
     * ȱʡ�Ĺ���������
     * @roseuid 3DAF726803BC
     */
    public ToolbarButton()
    {

        this(null);
    }

    /**
     * ����������
     * @param icon1 - ��ť����ʾ��ͼ��
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
     * ����������
     * @param icon1 - ��ť����ʾ��ͼ��
     * @param i - ��ť����ʾ��ʽ
     * @roseuid 3DAF72680394
     */
    public ToolbarButton(ImageIcon icon1, int i)
    {
        this(icon1);
        look = i;
    }

    /**
     * ���ö�����������
     * @param s - ��������
     * @roseuid 3DAF726803C6
     */
    public void setActionCommand(java.lang.String s)
    {
        actionCommand = s;
    }

    /**
     * ��ü����Ķ�������
     * @return java.lang.String
     * @roseuid 3DAF726803DA
     */
    public String getActionCommand()
    {
        return actionCommand;
    }

    /**
     * ���ð�ť����ʾͼ��
     * @param icon1 - ��ť����ʾ��ͼ��
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
     * �жϰ�ť�Ƿ���ѡ��״̬
     * @return boolean
     * @roseuid 3DAF72690038
     */
    public boolean isSelected()
    {
        return selected;
    }

    /**
     * ���ð�ť��״̬
     * @param flag - �����ͱ�������־��ť��״̬
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
     * ���ð�ť�Ƿ��ڿ���״̬
     * @param flag - �����ͱ�������־��ť�Ƿ��ڿ���״̬
     * @roseuid 3DAF72690056
     */
    public void setEnabled(boolean flag)
    {
        enabled = flag;
        if(isShowing())
            repaint();
    }

    /**
     * �жϰ�ť�Ƿ��ڿ���״̬
     * @return boolean
     * @roseuid 3DAF7269006A
     */
    public boolean isEnabled()
    {
        return enabled;
    }

    /**
     * �÷�������ȷ����ˢ�½������ʾ
     * @param g - ͼ�ι���
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
     * ����״̬�£����°�ťʱ��ˢ�°�ť�������ʾ
     * @roseuid 3DAF7269009C
     */

    public void paintInset()
    {
        Point point = findUpperLeft();
        Graphics g = getGraphics();
        Dimension dimension = getSize();
        //������ͼ�󣬷���
        if(image == null)
            return;
        //���ڵõ���ͼ��
        if(g != null)
        {
            //��ť������ʱ��ʾ״̬�ĸı�
            if(look != 0)
            {
                g.setColor(Color.black);
                g.drawLine(1, 0, dimension.width - 2, 0);
                g.drawLine(dimension.width - 1, 1, dimension.width - 1, dimension.height - 2);
                g.drawLine(dimension.width - 2, dimension.height - 1, 1, dimension.height - 1);
                g.drawLine(0, dimension.height - 2, 0, 1);
            }
            //������ʱ��״̬
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
     * ����״̬�£��ڰ�ť���ͷ����ʱˢ�°�ť�������ʾ
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
     * ������Ƴ���ť��ʱ��ˢ�°�ť�������ʾ
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
     * �����¼��Ĵ�����
     * @roseuid 3DAF726900CE
     */
    public void processActionEvent()
    {
        if(actionListener != null)
            actionListener.actionPerformed(new ActionEvent(this, 1001, actionCommand));
    }

    /**
     * ��Ӷ���������
     * @param actionlistener - ����������
     * @roseuid 3DAF726900D8
     */
    public synchronized void addActionListener(java.awt.event.ActionListener actionlistener)
    {
        actionListener = AWTEventMulticaster.add(actionListener, actionlistener);
    }

    /**
     * ɾ������������
     * @param actionlistener - ����������
     * @roseuid 3DAF726900F6
     */
    public synchronized void removeActionListener(ActionListener actionlistener)
    {
        actionListener = AWTEventMulticaster.remove(actionListener, actionlistener);
    }

    /**
     * ��ð�ť�����϶˵Ķ˵�
     * @return java.awt.Point
     * @roseuid 3DAF7269010A
     */
    private java.awt.Point findUpperLeft()
    {
        Dimension dimension = getSize();
        return new Point(dimension.width / 2 - prefSize.width / 2, dimension.height / 2 - prefSize.height / 2);
    }

    /**
     * ��갴���¼�
     * @param mouseevent - ����¼�
     * @roseuid 3DAF7269011E
     */
    public void mousePressed(MouseEvent mouseevent)
    {
        if(enabled)
            paintInset();
        // setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

    }

    /**
     * ������¼�
     * @param mouseevent - ����¼�
     * @roseuid 3DAF72690132
     */
    public void mouseClicked(MouseEvent mouseevent)
    {
    // setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
    // setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

    /**
     * �ͷ�����¼�
     * @param mouseevent - ����¼�
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
     * ��ק����¼�
     * @param mouseevent - ����¼�
     * @roseuid 3DAF7269016F
     */
    public void mouseDragged(MouseEvent mouseevent)
    {

    }

    /**
     * ����ƶ��¼�
     * @param mouseevent - ����¼�
     * @roseuid 3DAF72690183
     */
    public void mouseMoved(MouseEvent mouseevent)
    {

    }

    /**
     * ��������¼�
     * @param mouseevent - ����¼�
     * @roseuid 3DAF726901A1
     */
    public void mouseEntered(MouseEvent mouseevent)
    {
        if(!isSelected() && enabled)
            paintRaised();
    }

    /**
     * ����¼�
     * @param mouseevent - ����¼�
     * @roseuid 3DAF726901BF
     */
    public void mouseExited(MouseEvent mouseevent)
    {
        if(!isSelected() && look == 0)
            paintFlat();
    }

}