/** ���ɳ��� BomNoticeTreeObject.java    1.0
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.gybomNotice.client.util;

import java.awt.Image;
import java.util.Collection;

import javax.swing.ImageIcon;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;


/**
 * <p>Title:���õ������� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class GYBomAdoptNoticeTreeObject implements GYBomNoticeTreeObject
{

    /**
     * ��װ�������ҵ�����(���õ�ֵ����)
     */
    private BaseValueIfc object;


    /**
     * ��׼ͼ��
     */
    private Image startandImage;


    /**
     * �ڵ����ʾ����
     */
    private String noteText;


    /**
     * Ψһ��ʶ�����õ�ֵ�����BsoID
     */
    private String uniqueIdentify;


    /**
     * ������õ����ڵ����
     */
    public GYBomAdoptNoticeTreeObject()
    {

    }


    /**
     * �����װ��ָ����ҵ������·�߱�ڵ����
     * @param rt ·�߱�ֵ����
     */
    public GYBomAdoptNoticeTreeObject(BaseValueIfc info)
    {
        this();
        this.setObject(info);
        GYBomAdoptNoticeInfo i = (GYBomAdoptNoticeInfo) info;
        
        this.setNoteText(i.getAdoptnoticenumber());
        this.setUniqueIdentity(i.getBsoID());
       
        if (startandImage == null)
        {
        	startandImage = new ImageIcon(getClass().getResource(i.getIconName("StandardIcon"))).getImage();

        }
        this.setCloseImage(startandImage);
        this.setOpenImage(startandImage);
    }


    /**
     * ���÷�װ����
     * @param obj Object
     */
    public void setObject(Object obj)
    {
        this.object = (BaseValueIfc) obj;
    }


    /**
     *�õ���װ����
     *@return Object
     */
    public Object getObject()
    {
        return this.object;
    }


    /**
     * �õ�Ψһ��ʶ
     *@return java.lang.String
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
     *  @return java.util.Collection
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
        this.startandImage = image;
    }


    /**
     * �õ��ڵ�ر�ͼ��
     * @return java.awt.Image
     */
    public Image getCloseImage()
    {
        return this.startandImage;
    }


    /**
     * ���ýڵ�չ��ͼ��
     * @param image չ��ͼ��
     */
    public void setOpenImage(Image image)
    {
        this.startandImage = image;
    }


    /**
     * �õ��ڵ�չ��ͼ��
     * @return java.awt.Image
     */
    public Image getOpenImage()
    {
        return this.startandImage;
    }
}
