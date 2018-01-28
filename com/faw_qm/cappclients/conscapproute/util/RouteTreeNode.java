/** 
 * ���ɳ��� RouteTreeNode.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.util;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Image;
import javax.swing.*;

/**
 * <p> Title:·�����ڵ� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class RouteTreeNode extends DefaultMutableTreeNode
{

    /**
     * �ڵ��װ����
     */
    private RouteTreeObject object;

    /**
     * �ر�ͼ��
     */
    private Image closeIamge;

    /**
     * չ��ͼ��
     */
    private Image openImage;

    public static Image rootImage;

    /**
     * �ڵ���ʾ����
     */
    private String noteText;

    /**
     * ������ҵ���������ڵ�
     */
    public RouteTreeNode()
    {}

    /**
     * ������ҵ�����ı�ǩ�ڵ㡣Ĭ�����ﹹ��Ľڵ�����չ����
     * @param s ��ǩ�ڵ����ʾ�ı�
     */
    public RouteTreeNode(String s)
    {
        super(s);
        rootImage = new ImageIcon(getClass().getResource("/images/desktop.gif")).getImage();
        this.setObject(null);
        this.noteText = s;
        this.closeIamge = rootImage;
        this.openImage = rootImage;
        this.setAllowsChildren(true);
    }

    /**
     * �������ָ��ҵ���������ڵ㡣Ĭ�����ﹹ������ڵ��޷�չ����
     * @param object �ڵ�����ķ�װ����
     */
    public RouteTreeNode(RouteTreeObject object)
    {
        this.setObject(object);
        this.setAllowsChildren(false);
    }

    /**
     * �õ���һ���ӽڵ�
     * @return ���ϵĵ�һ���ӽڵ�
     */
    public RouteTreeNode getC()
    {
        RouteTreeNode nn;
        try
        {
            nn = (RouteTreeNode)getFirstChild();
        }catch(Exception ex)
        {
            nn = null;
        }
        return nn;
    }

    /**
     * �õ���װ����
     * @return cappclients.util.CappTree.RouteTreeObject
     */
    public RouteTreeObject getObject()
    {
        return this.object;

    }

    /**
     * ���÷�װ����
     * @param obj ���ڵ����
     */
    public void setObject(RouteTreeObject obj)
    {
        this.object = obj;
        if(obj != null)
        {
            this.closeIamge = object.getCloseImage();
            this.openImage = object.getOpenImage();
            this.setNoteText(object.getNoteText());
        }
    }

    /**
     * �õ��ر�ͼ��
     * @return java.awt.Image
     */
    public Image getCloseImage()
    {
        return this.closeIamge;
    }

    /**
     * �õ�չ��ͼ��
     * @return java.awt.Image
     */
    public Image getOpenImage()
    {
        return this.openImage;
    }

    /**
     * �õ��ڵ���ʾ�ı�
     * @return java.lang.String
     */
    public String getNoteText()
    {
        return this.noteText;
    }

    /**
     * ���ýڵ���ʾ�ı�
     * @param str �ڵ���ʾ�ı�
     */
    public void setNoteText(String str)
    {
        this.noteText = str;
    }

    /**
     * ����toString
     * @return
     */
    public String toString()
    {
        return this.getNoteText();
    }
}