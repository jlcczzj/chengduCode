/**
 * 生成程序 RoutePartTreeObject.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.awt.Image;
import java.util.Collection;

import javax.swing.ImageIcon;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;

/**
 * <p> Title:工艺路线树中的零部件节点对象.所封装的业务对象为路线表的零部件关联 ListRoutePartLinkIfc. </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class RoutePartTreeObject implements RouteTreeObject
{

    private BaseValueInfo object;

    private Image closeImage;

    private Image openImage;

    private String noteText;

    private int type;

    private String uniqueIdentify;

    /**
     * 有路线图标
     */
    private static Image startandImage;

    /**
     * 无路线图标
     */
    private static Image emptyIcon;

    /**
     * 构造函数
     */
    public RoutePartTreeObject()
    {}

    /**
     * 构造封装了指定的业务对象的节点对象
     * @param rt 零部件值对象
     */
    public RoutePartTreeObject(BaseValueIfc rt)
    {
        this();
        this.setObject(rt);
        this.uniqueIdentify = rt.getBsoID();
        //this.setNoteText(rt.toString());
    }

    /**
     * 设置节点封装对象的属性
     * @param info 节点封装对象
     */
    private void prepareAttrs(BaseValueIfc info)
    {
        ListRoutePartLinkIfc i = (ListRoutePartLinkIfc)info;
        QMPartMasterIfc partmaster = i.getPartMasterInfo();
        String parentNum = "";
        String parentName = "";
        if(i.getParentPartID() != null)
        {
            parentNum = i.getParentPartNum();
            parentName = i.getParentPartName();
            this.setNoteText(partmaster.getPartNumber() + "_" + partmaster.getPartName() + "父件" + parentNum + "_" + parentName);
        }else
            this.setNoteText(partmaster.getPartNumber() + "_" + partmaster.getPartName() + "父件 无");
        this.setUniqueIdentity(i.getBsoID());
        if(i.getRouteID() != null && !i.getRouteID().equals(""))
        {
            if(startandImage == null)
                startandImage = new ImageIcon(getClass().getResource("/images/route.gif")).getImage();
            this.setCloseImage(startandImage);
            this.setOpenImage(startandImage);
        }else
        //如果没有路线,则显示空白图标
        {
            if(emptyIcon == null)
                emptyIcon = new ImageIcon(getClass().getResource("/images/route_emptyRoute.gif")).getImage();
            this.setCloseImage(emptyIcon);
            this.setOpenImage(emptyIcon);
        }
    }

    /**
     * 设置封装对象
     * @param obj 封装对象
     */
    public void setObject(Object obj)
    {
        this.object = (BaseValueInfo)obj;
        this.prepareAttrs(object);
    }

    /**
     * 得到封装对象
     * @return Object 封装对象
     */
    public Object getObject()
    {
        return this.object;
    }

    /**
     * 得到唯一标识
     * @return java.lang.String
     */
    public String getUniqueIdentity()
    {
        return this.uniqueIdentify;
    }

    /**
     * 设置唯一标识
     * @param uni 唯一标识
     */
    public void setUniqueIdentity(String uni)
    {
        this.uniqueIdentify = uni;
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
     * 得到节点显示文本
     * @return java.lang.String
     */
    public String getNoteText()
    {
        return this.noteText;
    }

    /**
     * 设置对象包含的相关内容
     * @param cont Collection
     */
    public void setContests(Collection cont)
    {

    }

    /**
     * 得到对象包含的相关内容
     * @return java.util.Collection
     * @throws Exception
     */
    public Collection getContests()
    {
        return null;
    }

    /**
     * 设置节点关闭图标
     * @param image 节点关闭图标
     */
    public void setCloseImage(Image image)
    {
        this.closeImage = image;
    }

    /**
     * 得到节点关闭图标
     * @return java.awt.Image
     */
    public Image getCloseImage()
    {
        return this.closeImage;
    }

    /**
     * 设置节点展开图标
     * @param image 节点展开图标
     */
    public void setOpenImage(Image image)
    {
        this.openImage = image;
    }

    /**
     * 得到节点展开图标
     * @return java.awt.Image
     */
    public Image getOpenImage()
    {
        return this.openImage;
    }

    /**
     * 设置节点的图标为有路线图标
     */
    public void setStandardImage()
    {
        if(startandImage == null)
            startandImage = new ImageIcon(getClass().getResource(((ListRoutePartLinkIfc)this.getObject()).getIconName("StandardIcon"))).getImage();
        this.setCloseImage(startandImage);
        this.setOpenImage(startandImage);
    }

    /**
     * 设置节点图标为无路线图标
     */
    public void setEmptyImage()
    {
        if(emptyIcon == null)
            emptyIcon = new ImageIcon(getClass().getResource("/images/route_emptyRoute.gif")).getImage();
        this.setCloseImage(emptyIcon);
        this.setOpenImage(emptyIcon);
    }

}
