/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Χ��,ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/02
 */
package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoHomeDelegate;
import com.faw_qm.framework.service.BsoReference;

/**
 * ·�߱���ĵ����� <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class RouteListDocLinkHomeDelegate implements BsoHomeDelegate
{

    //home�ӿ�
    private RouteListDocLinkHome home = null;

    /**
     * ��ʼ��home�ӿ�
     * @param obj
     * @throws QMException 
     */
    public void init(Object obj) 
    {
        if(!(obj instanceof RouteListDocLinkHome))
        {
            Object[] objs = {obj.getClass(), "MaterialDocLinkHome"};
            throw new QMRuntimeException("com.faw_qm.framework.util.FrameworkMaterial", "70", objs);
        }
        home = (RouteListDocLinkHome)obj;
    }

    /**
     * ���ڸ��������ָ�����
     */
    public BsoReference findByPrimaryKey(String bsoID) throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }

    /**
     * ����ֵ������ҵ�����
     */
    public BsoReference create(BaseValueIfc info) throws CreateException
    {
        return home.create(info);
    }

    /**
     * ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException
    {
        return home.create(info, ct, mt);
    }

}
