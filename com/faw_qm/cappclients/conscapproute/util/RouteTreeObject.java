/** 
 * ���ɳ��� RouteTreeObject.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.awt.Image;
import java.util.Collection;

/**
 * <p> Title:���ڵ��װ������ܽӿ� </p> <p> Description:��Ҫ�����װ�˽ڵ���󣬽ڵ��ͼ�꣬�ڵ���ʾ���ڵ����ݺͽڵ��Ψһ��ʶ��Ϣ </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public interface RouteTreeObject
{

    /**
     * ���÷�װ����
     * @param obj ��װ����
     */
    public void setObject(Object obj);

    /**
     * �õ���װ����
     * @return Object
     */
    public Object getObject();

    /**
     * �õ�Ψһ��ʶ
     * @return java.lang.String
     */
    public String getUniqueIdentity();

    /**
     * ����Ψһ��ʶ
     * @param uni String
     */
    public void setUniqueIdentity(String uni);

    /**
     * ���ýڵ���ʾ�ı�
     * @param str �ڵ���ʾ�ı�
     */
    public void setNoteText(String str);

    /**
     * �õ��ڵ���ʾ�ı�
     * @return java.lang.String
     */
    public String getNoteText();

    /**
     * ���ö���������������
     * @param cont Collection
     */
    public void setContests(Collection cont);

    /**
     * �õ�����������������
     * @return java.util.Collection
     * @throws Exception
     */
    public Collection getContests();

    /**
     * ���ýڵ�ر�ͼ��
     * @param image �ڵ�ر�ͼ��
     */
    public void setCloseImage(Image image);

    /**
     * �õ��ڵ�ر�ͼ��
     * @return java.awt.Image
     */
    public Image getCloseImage();

    /**
     * ���ýڵ�չ��ͼ��
     * @param image �ڵ�չ��ͼ��
     */
    public void setOpenImage(Image image);

    /**
     * �õ��ڵ�չ��ͼ��
     * @return java.awt.Image
     */
    public Image getOpenImage();

}