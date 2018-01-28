/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.gybomNotice.client.util;

import java.awt.Image;
import java.util.Collection;

import javax.swing.ImageIcon;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterInfo;


/**
 * <p>Title:零件节点 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 刘 明
 * @version 1.0
 */

public class GYPartTreeObject implements GYBomNoticeTreeObject
{
    /**
     * 标准图标
     */
    private static Image startandImage;
    private BaseValueIfc object;


    /**
     * 节点的显示文字
     */
    private String noteText;


    /**
     * 唯一标识：路线表值对象的BsoID
     */
    private String uniqueIdentify;

    public GYPartTreeObject(GYProductTreeNode pnode,BaseValueIfc info)
    {
        QMPartInfo i = (QMPartInfo) info;
        this.setNoteText(i.getIdentity());
        if(pnode.getObject()==null)
        {
        	this.setUniqueIdentity(info.getBsoID());
        }
        else
        {
        	this.setUniqueIdentity((pnode.getObject()).getUniqueIdentity()+info.getBsoID());
        }
        this.setObject(info);
        if (startandImage == null)
        {
            startandImage = new ImageIcon(getClass().getResource(i.getIconName(
                    "StandardIcon"))).getImage();
        }
        this.setCloseImage(startandImage);
        this.setOpenImage(startandImage);

    }


    /**
     * 设置封装对象
     * @param obj
     */
    public void setObject(Object obj)
    {
        this.object = (BaseValueIfc) obj;
    }


    /**
     *得到封装对象
     *@return Object
     */
    public Object getObject()
    {
        return this.object;
    }


    /**
     * 得到唯一标识
     *@return java.lang.String
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
     *  @return java.util.Collection
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
        this.startandImage = image;
    }


    /**
     * 得到节点关闭图标
     * @return java.awt.Image
     */
    public Image getCloseImage()
    {
        return this.startandImage;
    }


    /**
     * 设置节点展开图标
     * @param image 展开图标
     */
    public void setOpenImage(Image image)
    {
        this.startandImage = image;
    }


    /**
     * 得到节点展开图标
     * @return java.awt.Image
     */
    public Image getOpenImage()
    {
        return this.startandImage;
    }

}
