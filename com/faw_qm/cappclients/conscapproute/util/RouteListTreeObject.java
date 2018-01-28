/**
 * 生成程序 RouteListTreeObject.java    1.0    2004/02/19

 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 徐春英   2011/12/15  原因：修改获取图标方式     参见：自省专题更改说明_薛凯.doc
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.awt.Image;
import java.util.Collection;

import javax.swing.ImageIcon;

import com.faw_qm.enterprise.util.PDMIcons;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * Title:路线树中的路线表节点对象 Description: Copyright: Copyright (c) 2004 Company: 一汽启明
 * @author 刘明
 * @version 1.0 (问题一)zz 20060922 树上只需挂最新版本的路线表对象,所以用ID作唯一标识不能区分出版本， 会出现新旧小版本同时出现的情况。如果用编号作唯一标识，就能保证搜索出来的最先版本覆盖原来的旧小版本对象 zz 20060922 但是这样又新旧大版本不能同时挂到树上，改为编号加大版本号
 */

public class RouteListTreeObject implements RouteTreeObject
{

    /**
     * 封装类包含的业务对象(路线表值对象)
     */
    private BaseValueIfc object;

    /**
     * 关闭图标
     */
    private Image closeImage;

    /**
     * 展开图标
     */
    private Image openImage;

    /**
     * 标准图标
     */
    private static Image startandImage;

    /**
     * 检出图标
     */
    private static Image checkOutImage;

    /**
     * 检入图标
     */
    private static Image workingImage;

    /**
     * 节点的显示文字
     */
    private String noteText;

    /**
     * 唯一标识：路线表值对象的BsoID
     */
    private String uniqueIdentify;

    /**
     * 构造路线表树节点对象
     */
    public RouteListTreeObject()
    {

    }

    /**
     * 构造封装了指定的业务对象的路线表节点对象
     * @param rt 路线表值对象
     */
    public RouteListTreeObject(BaseValueIfc rt)
    {
        this();
        this.setObject(rt);
        //  this.uniqueIdentify = rt.getBsoID();原代码
        //(问题一)zz 20060922修改 新旧版本的ID不同，应该只挂最新版本，各个版本的路线表对象编号相同，所以用编号作唯一标示。begin
        //20061207@zz this.uniqueIdentify = ((TechnicsRouteListInfo)rt).getRouteListNumber();//end
        // 只用RouteListNumber作唯一标识，新旧大版本不能同时挂在树上，又改为编号加大版本号
        this.uniqueIdentify = ((TechnicsRouteListInfo)rt).getRouteListNumber() + "@" + ((TechnicsRouteListInfo)rt).getVersionID();//end
        //this.setNoteText(rt.toString());
    }

    /**
     * 设置节点封装对象的属性
     * @param info 节点封装对象
     */
    private void prepareAttrs(BaseValueIfc info)
    {
        TechnicsRouteListInfo i = (TechnicsRouteListInfo)info;
        this.setNoteText(i.getRouteListNumber() + "_" + i.getRouteListName() + "_" + i.getRouteListLevel() + "_" + i.getVersionValue());
        // this.setUniqueIdentity(i.getBsoID());原代码
        //(问题一)zz 20060922修改 新旧小版本的ID不同，应该只挂最新版本，各个版本的路线表对象编号相同，所以用编号作唯一标示。begin
        // 20061207@ zz如果只用比编号作唯一标识，大版本不能同时出现在树上，又改为编号加大版本号
        //  this.setUniqueIdentity(i.getRouteListNumber());// end
        this.setUniqueIdentity(i.getRouteListNumber() + "@" + i.getVersionID());
        //skybird
        //begin CR1
        //        if(((WorkableIfc)info).getWorkableState().equals("c/o"))
        //        {
        //            if(checkOutImage == null)
        //            {
        //                checkOutImage = new ImageIcon(getClass().getResource(i.getIconName("CheckOutIcon"))).getImage();
        //            }
        //            this.setCloseImage(checkOutImage);
        //            this.setOpenImage(checkOutImage);
        //        }else if(((WorkableIfc)info).getWorkableState().equals("c/i"))
        //        {
        //            if(startandImage == null)
        //                startandImage = new ImageIcon(getClass().getResource(i.getIconName("StandardIcon"))).getImage();
        //            this.setCloseImage(startandImage);
        //            this.setOpenImage(startandImage);
        //        }else if(((WorkableIfc)info).getWorkableState().equals("wrk"))
        //        {
        //            if(workingImage == null)
        //                workingImage = new ImageIcon(getClass().getResource(i.getIconName("WorkingIcon"))).getImage();
        //            this.setCloseImage(workingImage);
        //            this.setOpenImage(workingImage);
        //        }else
        //        {
        //            if(startandImage == null)
        //                startandImage = new ImageIcon(getClass().getResource(i.getIconName("StandardIcon"))).getImage();
        //            this.setCloseImage(startandImage);
        //            this.setOpenImage(startandImage);
        //        }
        ImageIcon img = PDMIcons.getClientBsoIcon(i);
        this.setCloseImage(img.getImage());
        this.setOpenImage(img.getImage());
        //end CR1
    }

    /**
     * 设置封装对象
     * @param obj Object
     */
    public void setObject(Object obj)
    {
        this.object = (BaseValueIfc)obj;
        this.prepareAttrs(object);
    }

    /**
     * 得到封装对象
     * @return Object
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
     */
    public Collection getContests()
    {
        return null;
    }

    /**
     * 设置节点关闭图标
     * @param image 关闭图标
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
     * @param image 展开图标
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
}
