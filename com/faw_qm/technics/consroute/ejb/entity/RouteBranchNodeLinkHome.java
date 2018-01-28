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
 * ·�ߴ��ͽڵ�Ĺ��� <p>Title:leftID = branchID , rightID = routeNodeID </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw_qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface RouteBranchNodeLinkHome extends BsoReferenceHome
{
    /**
     * ���ڸ��������ָ�����
     */
    public RouteBranchNodeLink findByPrimaryKey(String bsoID) throws FinderException;

    /**
     * ����ֵ������ҵ�����
     */
    public RouteBranchNodeLink create(BaseValueIfc info) throws CreateException;

    /**
     * ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public RouteBranchNodeLink create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException;
}
