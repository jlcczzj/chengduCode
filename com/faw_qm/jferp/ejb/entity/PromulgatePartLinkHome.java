/**
 * ���ɳ���PromulgatePartLinkHome.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

/**
 * <p>Title: ����֪ͨ���������ӿ�</p>
 * <p>Description: ����֪ͨ���������ӿ�</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public interface PromulgatePartLinkHome extends BsoReferenceHome
{
    public abstract PromulgatePartLink create(BaseValueIfc basevalueifc)
            throws CreateException;

    public abstract PromulgatePartLink create(BaseValueIfc basevalueifc,
            Timestamp timestamp, Timestamp timestamp1) throws CreateException;

    public abstract PromulgatePartLink findByPrimaryKey(String s)
            throws FinderException;
}
