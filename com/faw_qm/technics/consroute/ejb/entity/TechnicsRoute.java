/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/21 �촺Ӣ      ԭ�򣺹���·�߲�������˵����ʵ�֣���·�߶���������"���ı��"����,������Ԥ����������
 */

package com.faw_qm.technics.consroute.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: </p> <p>Description: ����·�߷������</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: һ��������˾</p>
 * @author ������
 * @version 1.0
 */
public interface TechnicsRoute extends BsoReference
{

    /**
     * ·��˵��
     */
    public java.lang.String getRouteDescription();

    /**
     * ·��˵����
     */
    public void setRouteDescription(String description);

    //begin CR1
    /**
     * ��ø��ı��
     */
    public String getModifyIdenty();

    /**
     * ���ø��ı��
     */
    public void setModifyIdenty(String identy);

    /**
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1);

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1();

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2);

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2();
    //end CR1

}
