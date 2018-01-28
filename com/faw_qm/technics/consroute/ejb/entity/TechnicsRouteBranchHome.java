/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

/**
 * ����·�߷�֧����·�ߴ� <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface TechnicsRouteBranchHome extends BsoReferenceHome
{
    /**
     * ���ڸ��������ָ�����
     */
    public TechnicsRouteBranch findByPrimaryKey(String bsoID) throws FinderException;

    /**
     * ����ֵ������ҵ�����
     */
    public TechnicsRouteBranch create(BaseValueIfc info) throws CreateException;

    /**
     * ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public TechnicsRouteBranch create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException;
}
