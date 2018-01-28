/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/21 �촺Ӣ      ԭ�򣺹���·�߲�������˵����ʵ�֣���·�߶���������"���ı��"����,������Ԥ����������
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * ����·��ֵ����
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class TechnicsRouteInfo extends BaseValueInfo implements TechnicsRouteIfc
{
    //����
    private String description;

    private static final long serialVersionUID = 1L;
    //begin CR1
    private String modifyIdenty;
    private String attribute1;
    private String attribute2;

    //end CR1

    /**
     * ���캯��
     */
    public TechnicsRouteInfo()
    {

    }

    /**
     * ���·��˵��
     * @return String ·��˵��
     */
    public String getRouteDescription()
    {
        return this.description;
    }

    /**
     * ����·��˵����
     * @param description ·��˵��
     */
    public void setRouteDescription(String description)
    {
        this.description = description;
    }

    /**
     * �õ���ʶ
     * @return String ��ʶ
     */
    public String getIdentity()
    {
        if(this.getBsoID() == null)
        {
            return this.getBsoName();
        }else
        {
            return this.getBsoID();
        }
    }

    /**
     * �õ�ҵ������
     * @return String TechnicsRoute
     */
    public String getBsoName()
    {
        return "consTechnicsRoute";
    }

    //begin CR1
    /**
     * ��ø��ı��
     * @return ���ı��
     */
    public String getModifyIdenty()
    {
        return this.modifyIdenty;
    }

    /**
     * ���ø��ı��
     * @param identy ���ı��
     */
    public void setModifyIdenty(String identy)
    {
        this.modifyIdenty = identy;
    }

    /**
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }
    //end CR1
}
