/**
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
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterInfo;


/**
 * <p>Title:����ڵ� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author �� ��
 * @version 1.0
 */

public class GYPartTreeObject implements GYBomNoticeTreeObject
{
    /**
     * ��׼ͼ��
     */
    private static Image startandImage;
    private BaseValueIfc object;


    /**
     * �ڵ����ʾ����
     */
    private String noteText;


    /**
     * Ψһ��ʶ��·�߱�ֵ�����BsoID
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
     * ���÷�װ����
     * @param obj
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
