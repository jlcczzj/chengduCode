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

import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.*;


/**
 * �����ĵ�����Home������
 * @version 1.0
 * @author ����
 */
public class CompletDocLinkHomeDelegate implements BsoHomeDelegate
{

    //home�ӿ�
    private CompletDocLinkHome home = null;


    /**
     * ��ʼ��home�ӿ�
     * @param obj
     */
    public void init(Object obj)
    {
        if (!(obj instanceof CompletDocLinkHome))
        {
            Object[] objs =
                    {
                    obj.getClass(), "MaterialDocLinkHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkMaterial", "70", objs);
        }
        home = (CompletDocLinkHome) obj;
    }


    /**
     * ���ڸ��������ָ�����
     */
    public BsoReference findByPrimaryKey(String bsoID)
            throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }


    /**
     * ����ֵ������ҵ�����
     */
    public BsoReference create(BaseValueIfc info)
            throws CreateException
    {
        return home.create(info);
    }


    /**
     * ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt)
            throws
            CreateException
    {
        return home.create(info, ct, mt);
    }

}
