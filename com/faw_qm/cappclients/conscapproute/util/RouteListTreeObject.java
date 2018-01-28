/**
 * ���ɳ��� RouteListTreeObject.java    1.0    2004/02/19

 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 �촺Ӣ   2011/12/15  ԭ���޸Ļ�ȡͼ�귽ʽ     �μ�����ʡר�����˵��_Ѧ��.doc
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
 * Title:·�����е�·�߱�ڵ���� Description: Copyright: Copyright (c) 2004 Company: һ������
 * @author ����
 * @version 1.0 (����һ)zz 20060922 ����ֻ������°汾��·�߱����,������ID��Ψһ��ʶ�������ֳ��汾�� ������¾�С�汾ͬʱ���ֵ����������ñ����Ψһ��ʶ�����ܱ�֤�������������Ȱ汾����ԭ���ľ�С�汾���� zz 20060922 �����������¾ɴ�汾����ͬʱ�ҵ����ϣ���Ϊ��żӴ�汾��
 */

public class RouteListTreeObject implements RouteTreeObject
{

    /**
     * ��װ�������ҵ�����(·�߱�ֵ����)
     */
    private BaseValueIfc object;

    /**
     * �ر�ͼ��
     */
    private Image closeImage;

    /**
     * չ��ͼ��
     */
    private Image openImage;

    /**
     * ��׼ͼ��
     */
    private static Image startandImage;

    /**
     * ���ͼ��
     */
    private static Image checkOutImage;

    /**
     * ����ͼ��
     */
    private static Image workingImage;

    /**
     * �ڵ����ʾ����
     */
    private String noteText;

    /**
     * Ψһ��ʶ��·�߱�ֵ�����BsoID
     */
    private String uniqueIdentify;

    /**
     * ����·�߱����ڵ����
     */
    public RouteListTreeObject()
    {

    }

    /**
     * �����װ��ָ����ҵ������·�߱�ڵ����
     * @param rt ·�߱�ֵ����
     */
    public RouteListTreeObject(BaseValueIfc rt)
    {
        this();
        this.setObject(rt);
        //  this.uniqueIdentify = rt.getBsoID();ԭ����
        //(����һ)zz 20060922�޸� �¾ɰ汾��ID��ͬ��Ӧ��ֻ�����°汾�������汾��·�߱��������ͬ�������ñ����Ψһ��ʾ��begin
        //20061207@zz this.uniqueIdentify = ((TechnicsRouteListInfo)rt).getRouteListNumber();//end
        // ֻ��RouteListNumber��Ψһ��ʶ���¾ɴ�汾����ͬʱ�������ϣ��ָ�Ϊ��żӴ�汾��
        this.uniqueIdentify = ((TechnicsRouteListInfo)rt).getRouteListNumber() + "@" + ((TechnicsRouteListInfo)rt).getVersionID();//end
        //this.setNoteText(rt.toString());
    }

    /**
     * ���ýڵ��װ���������
     * @param info �ڵ��װ����
     */
    private void prepareAttrs(BaseValueIfc info)
    {
        TechnicsRouteListInfo i = (TechnicsRouteListInfo)info;
        this.setNoteText(i.getRouteListNumber() + "_" + i.getRouteListName() + "_" + i.getRouteListLevel() + "_" + i.getVersionValue());
        // this.setUniqueIdentity(i.getBsoID());ԭ����
        //(����һ)zz 20060922�޸� �¾�С�汾��ID��ͬ��Ӧ��ֻ�����°汾�������汾��·�߱��������ͬ�������ñ����Ψһ��ʾ��begin
        // 20061207@ zz���ֻ�ñȱ����Ψһ��ʶ����汾����ͬʱ���������ϣ��ָ�Ϊ��żӴ�汾��
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
     * ���÷�װ����
     * @param obj Object
     */
    public void setObject(Object obj)
    {
        this.object = (BaseValueIfc)obj;
        this.prepareAttrs(object);
    }

    /**
     * �õ���װ����
     * @return Object
     */
    public Object getObject()
    {
        return this.object;
    }

    /**
     * �õ�Ψһ��ʶ
     * @return java.lang.String
     */
    public String getUniqueIdentity()
    {
        return this.uniqueIdentify;
    }

    /**
     * ����Ψһ��ʶ
     * @param uni Ψһ��ʶ
     */
    public void setUniqueIdentity(String uni)
    {
        this.uniqueIdentify = uni;
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
     * �õ��ڵ���ʾ�ı�
     * @return java.lang.String
     */
    public String getNoteText()
    {
        return this.noteText;
    }

    /**
     * ���ö���������������
     * @param cont Collection
     */
    public void setContests(Collection cont)
    {

    }

    /**
     * �õ�����������������
     * @return java.util.Collection
     */
    public Collection getContests()
    {
        return null;
    }

    /**
     * ���ýڵ�ر�ͼ��
     * @param image �ر�ͼ��
     */
    public void setCloseImage(Image image)
    {
        this.closeImage = image;
    }

    /**
     * �õ��ڵ�ر�ͼ��
     * @return java.awt.Image
     */
    public Image getCloseImage()
    {
        return this.closeImage;
    }

    /**
     * ���ýڵ�չ��ͼ��
     * @param image չ��ͼ��
     */
    public void setOpenImage(Image image)
    {
        this.openImage = image;
    }

    /**
     * �õ��ڵ�չ��ͼ��
     * @return java.awt.Image
     */
    public Image getOpenImage()
    {
        return this.openImage;
    }
}
