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
 * <p>Title: </p> <p>Description: ����·�߷������</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: һ��������˾</p>
 * @author ������
 * @version 1.0
 */
public interface RouteListProductLinkHome extends BsoReferenceHome
{
    /**
     * ���ڸ��������ָ�����
     */
    public RouteListProductLink findByPrimaryKey(String bsoID) throws FinderException;

    /**
     * ����ֵ������ҵ�����
     */
    public RouteListProductLink create(BaseValueIfc info) throws CreateException;

    /**
     * ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public RouteListProductLink create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException;
}
