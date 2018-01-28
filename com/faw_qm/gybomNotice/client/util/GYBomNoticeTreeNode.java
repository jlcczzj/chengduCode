/** 生成程序 RationTreeNode.java    1.0
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.gybomNotice.client.util;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;


/**
 * <p>Title:采用单树节点 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 文柳
 * @version 1.0
 */

public class GYBomNoticeTreeNode extends DefaultMutableTreeNode
{

    /**
     *节点封装对象
     */
    private GYBomNoticeTreeObject object;


    /**
     * 关闭图标
     */
    private Image closeIamge;


    /**
     * 展开图标
     */
    private Image openImage;

    public static Image rootImage;


    /**
     * 节点显示文字
     */
    private String noteText;


    /**
     * 构造无业务对象的树节点
     */
    public GYBomNoticeTreeNode()
    {
    }


    /**
     * 构造无业务对象的标签节点。默认这里构造的节点允许展开。
     * @param s 标签节点的显示文本
     */
    public GYBomNoticeTreeNode(String s)
    {
        super(s);
        rootImage = new ImageIcon(getClass().getResource("/images/desktop.gif")).
                    getImage();
        this.setObject(null);
        this.noteText = s;
        this.closeIamge = rootImage;
        this.openImage = rootImage;
        this.setAllowsChildren(true);
    }


    /**
     * 构造包含指定业务对象的树节点。默认这里构造的树节点无法展开。
     * @param object  节点包含的封装对象
     */
    public GYBomNoticeTreeNode(GYBomNoticeTreeObject object)
    {
        this.setObject(object);
        this.setAllowsChildren(false);
    }


    /**
     * 得到第一个子节点
     * @return 树上的第一个子节点
     */
    public GYBomNoticeTreeNode getC()
    {
        GYBomNoticeTreeNode nn;
        try
        {
            nn = (GYBomNoticeTreeNode) getFirstChild();
        }
        catch (Exception ex)
        {
            nn = null;
        }
        return nn;
    }


    /**
     * 获得这个节点的下个同级节点
     * @return QMNode:获得这个节点的下个同级节点
     */
    public GYBomNoticeTreeNode getS()
    {
        return (GYBomNoticeTreeNode) getNextSibling();
    }


    /**
     * 得到封装对象
     * @return cappclients.util.CappTree.GYBomNoticeTreeObject
     */
    public GYBomNoticeTreeObject getObject()
    {
        return this.object;

    }


    /**
     * 设置封装对象
     * @param obj 树节点对象
     */
    public void setObject(GYBomNoticeTreeObject obj)
    {
        this.object = obj;
        if (obj != null)
        {
            this.closeIamge = object.getCloseImage();
            this.openImage = object.getOpenImage();
            this.setNoteText(object.getNoteText());
        }
    }


    /**
     * 得到关闭图标
     * @return java.awt.Image
     */
    public Image getCloseImage()
    {
        return this.closeIamge;
    }


    /**
     * 得到展开图标
     * @return java.awt.Image
     */
    public Image getOpenImage()
    {
        return this.openImage;
    }


    /**
     * 得到节点显示文本
     * @return java.lang.String
     */
    public String getNoteText()
    {
        return this.noteText;
    }


    /**
     * 设置节点显示文本
     * @param str 节点显示文本
     */
    public void setNoteText(String str)
    {
        this.noteText = str;
    }


    /**
     *
     * @return
     */
    public String toString()
    {
        return this.getNoteText();
    }
}
