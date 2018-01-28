/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.cappclients.conscapproute.util;

import java.awt.Image;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;

import com.faw_qm.cappclients.conscapproute.view.RParentJPanel;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;

/**
 * <p> Title:树节点封装对象的总接口 </p> <p> Description:要主抽象封装了节点对象，节点的图标，节点显示，节点内容和节点的唯一标识信息 </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class PartMasterTreeObject
{

    /**
     * 封装类包含的业务对象(零部件主信息值对象)
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
     * 节点的显示文字
     */
    private String noteText;

    /**
     * 唯一标识：路线表值对象的BsoID
     */
    private String uniqueIdentify;

    /**
     * 节点包含的所有内容
     */
    private Vector contents = new Vector();

    /**
     * 构造路线表树节点对象
     */
    public PartMasterTreeObject()
    {

    }

    /**
     * 构造封装了指定的业务对象的零部件节点对象
     * @param rt 零部件主信息值对象
     */
    public PartMasterTreeObject(QMPartMasterIfc rt)
    {
        this();
        this.setObject(rt);
        this.uniqueIdentify = rt.getBsoID();
    }

    /**
     * 设置节点封装对象的属性
     * @param info 节点封装对象
     */
    private void prepareAttrs(BaseValueIfc info)
    {
        QMPartMasterInfo i = (QMPartMasterInfo)info;
        this.setNoteText(i.getPartNumber() + "(" + i.getPartName() + ")");
        this.setUniqueIdentity(i.getBsoID());

        if(startandImage == null)
            startandImage = new ImageIcon(getClass().getResource(i.getIconName("StandardIcon"))).getImage();
        this.setCloseImage(startandImage);
        this.setOpenImage(startandImage);

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
     * @throws Exception 
     */
    public Collection getContests() throws Exception
    {
        Collection results;
        this.contents.removeAllElements();
        try
        {
            Class[] c = {QMPartMasterIfc.class};
            Object[] objs = {(QMPartMasterIfc)getObject()};
            results = (Collection)RequestHelper.request("consTechnicsRouteService", "getSubPartMasters", c, objs);

            if(results != null)
            {
                for(Iterator iter = results.iterator();iter.hasNext();)
                {
                    PartMasterTreeObject obj = new PartMasterTreeObject((QMPartMasterInfo)iter.next());
                    this.contents.add(obj);
                }
            }
        }catch(Exception e)
        {
            throw e;
        }
        return contents;
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
