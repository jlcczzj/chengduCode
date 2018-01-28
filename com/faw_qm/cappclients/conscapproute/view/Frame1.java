/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * <p> Title: </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class Frame1 extends JFrame
{
    ImageIcon image3 = new ImageIcon(getClass().getResource("/images/rg.gif"));

    private JLabel jLabel1 = new JLabel();

    private JPanel jPanel1 = new JPanel();

    /**
     * ���캯��
     */
    public Frame1()
    {
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        Frame1 frame1 = new Frame1();
        frame1.setSize(400, 300);
        frame1.show();
    }

    /**
     * ��ʼ��
     * @throws Exception
     */
    private void jbInit()
    {

        //RichToThinUtil.toWebPage("route_search_routeList.screen",null);
        this.getContentPane().setLayout(null);

        jLabel1.setBorder(BorderFactory.createLineBorder(Color.black));
        jLabel1.setText("jLabel1");
        jPanel1.addKeyListener(new Frame1_jLabel1_keyAdapter(this));
        jPanel1.setBounds(new Rectangle(94, 42, 147, 90));
        this.getContentPane().add(jPanel1, null);
        jPanel1.add(jLabel1, null);
    }

    /**
     * ��ӡ��������ֵ
     * @param e
     */
    void jLabel1_keyTyped(KeyEvent e)
    {
        System.out.println(e.getKeyCode());
    }

    /**
     * ��ӡ��������
     * @param e
     */
    void jLabel1_keyPressed(KeyEvent e)
    {
        System.out.println(e.getKeyCode());
    }

    /**
     * ��ӡ��������
     * @param e
     */
    void jButton1_keyPressed(KeyEvent e)
    {
        //delete : 127
        System.out.println(e.getKeyCode());
    }

}

/**
 * <p>Title:Frame1_jLabel1_keyAdapter</p> <p>Description: Label1�ļ�����</p> <p>Package:com.faw_qm.cappclients.conscapproute.view</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author
 * @version 1.0
 */
class Frame1_jLabel1_keyAdapter extends java.awt.event.KeyAdapter
{
    private Frame1 adaptee;

    Frame1_jLabel1_keyAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void keyTyped(KeyEvent e)
    {
        adaptee.jLabel1_keyTyped(e);
    }

    public void keyPressed(KeyEvent e)
    {
        adaptee.jLabel1_keyPressed(e);
    }
}

/**
 * <p>Title:Frame1_jButton1_keyAdapter</p> <p>Description: Button������</p> <p>Package:com.faw_qm.cappclients.conscapproute.view</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author
 * @version 1.0
 */
class Frame1_jButton1_keyAdapter extends java.awt.event.KeyAdapter
{
    private Frame1 adaptee;

    Frame1_jButton1_keyAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void keyPressed(KeyEvent e)
    {
        adaptee.jButton1_keyPressed(e);
    }
}
