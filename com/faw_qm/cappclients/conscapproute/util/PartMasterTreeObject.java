/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
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
 * <p> Title:���ڵ��װ������ܽӿ� </p> <p> Description:Ҫ�������װ�˽ڵ���󣬽ڵ��ͼ�꣬�ڵ���ʾ���ڵ����ݺͽڵ��Ψһ��ʶ��Ϣ </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class PartMasterTreeObject
{

    /**
     * ��װ�������ҵ�����(�㲿������Ϣֵ����)
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
     * �ڵ����ʾ����
     */
    private String noteText;

    /**
     * Ψһ��ʶ��·�߱�ֵ�����BsoID
     */
    private String uniqueIdentify;

    /**
     * �ڵ��������������
     */
    private Vector contents = new Vector();

    /**
     * ����·�߱����ڵ����
     */
    public PartMasterTreeObject()
    {

    }

    /**
     * �����װ��ָ����ҵ�������㲿���ڵ����
     * @param rt �㲿������Ϣֵ����
     */
    public PartMasterTreeObject(QMPartMasterIfc rt)
    {
        this();
        this.setObject(rt);
        this.uniqueIdentify = rt.getBsoID();
    }

    /**
     * ���ýڵ��װ���������
     * @param info �ڵ��װ����
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
