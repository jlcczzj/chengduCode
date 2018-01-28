/** 生成程序 RationTreeObject.java    1.0    2006/06/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.gybomNotice.client.util;

import java.awt.Image;


/**
 * <p>Title:树节点封装对象的总接口 </p>
 * <p>Description:主要抽象封装了节点对象，节点的图标，节点显示，节点内容和节点的唯一标识信息 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */


public interface GYBomNoticeTreeObject
{

    /**
     * 设置封装对象
     * @param obj 封装对象
     */
    public void setObject(Object obj);


    /**
     *得到封装对象
     *@return Object
     */
    public Object getObject();


    /**
     * 得到唯一标识
     *@return java.lang.String
     */
    public String getUniqueIdentity();


    /**
     * 设置唯一标识
     * @param uni String
     */
    public void setUniqueIdentity(String uni);


    /**
     * 设置节点显示文本
     * @param str 节点显示文本
     */
    public void setNoteText(String str);


    /**
     * 得到节点显示文本
     * @return java.lang.String
     */
    public String getNoteText();


    /**
     * 设置节点关闭图标
     * @param image 节点关闭图标
     */
    public void setCloseImage(Image image);


    /**
     * 得到节点关闭图标
     * @return java.awt.Image
     */
    public Image getCloseImage();


    /**
     * 设置节点展开图标
     * @param image 节点展开图标
     */
    public void setOpenImage(Image image);


    /**
     * 得到节点展开图标
     * @return java.awt.Image
     */
    public Image getOpenImage();


}
