/**
 * ���ɳ��� RoutePartTreeObject.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p> Title:����·�����е��㲿���ڵ����.����װ��ҵ�����Ϊ·�߱���㲿������ ListRoutePartLinkIfc. </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
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
     * ��·��ͼ��
     */
    private static Image startandImage;

    /**
     * ��·��ͼ��
     */
    private static Image emptyIcon;

    /**
     * ���캯��
     */
    public RoutePartTreeObject()
    {}

    /**
     * �����װ��ָ����ҵ�����Ľڵ����
     * @param rt �㲿��ֵ����
     */
    public RoutePartTreeObject(BaseValueIfc rt)
    {
        this();
        this.setObject(rt);
        this.uniqueIdentify = rt.getBsoID();
        //this.setNoteText(rt.toString());
    }

    /**
     * ���ýڵ��װ���������
     * @param info �ڵ��װ����
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
            this.setNoteText(partmaster.getPartNumber() + "_" + partmaster.getPartName() + "����" + parentNum + "_" + parentName);
        }else
            this.setNoteText(partmaster.getPartNumber() + "_" + partmaster.getPartName() + "���� ��");
        this.setUniqueIdentity(i.getBsoID());
        if(i.getRouteID() != null && !i.getRouteID().equals(""))
        {
            if(startandImage == null)
                startandImage = new ImageIcon(getClass().getResource("/images/route.gif")).getImage();
            this.setCloseImage(startandImage);
            this.setOpenImage(startandImage);
        }else
        //���û��·��,����ʾ�հ�ͼ��
        {
            if(emptyIcon == null)
                emptyIcon = new ImageIcon(getClass().getResource("/images/route_emptyRoute.gif")).getImage();
            this.setCloseImage(emptyIcon);
            this.setOpenImage(emptyIcon);
        }
    }

    /**
     * ���÷�װ����
     * @param obj ��װ����
     */
    public void setObject(Object obj)
    {
        this.object = (BaseValueInfo)obj;
        this.prepareAttrs(object);
    }

    /**
     * �õ���װ����
     * @return Object ��װ����
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
     * @throws Exception
     */
    public Collection getContests()
    {
        return null;
    }

    /**
     * ���ýڵ�ر�ͼ��
     * @param image �ڵ�ر�ͼ��
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
     * @param image �ڵ�չ��ͼ��
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

    /**
     * ���ýڵ��ͼ��Ϊ��·��ͼ��
     */
    public void setStandardImage()
    {
        if(startandImage == null)
            startandImage = new ImageIcon(getClass().getResource(((ListRoutePartLinkIfc)this.getObject()).getIconName("StandardIcon"))).getImage();
        this.setCloseImage(startandImage);
        this.setOpenImage(startandImage);
    }

    /**
     * ���ýڵ�ͼ��Ϊ��·��ͼ��
     */
    public void setEmptyImage()
    {
        if(emptyIcon == null)
            emptyIcon = new ImageIcon(getClass().getResource("/images/route_emptyRoute.gif")).getImage();
        this.setCloseImage(emptyIcon);
        this.setOpenImage(emptyIcon);
    }

}
