/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

/**
 * ·�߱���ĵ����� <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface RouteListDocLinkHome extends BsoReferenceHome
{
    /**
     * �����������Ҷ���
     */
    public RouteListDocLink findByPrimaryKey(String bsoID) throws FinderException;

    /**
     * ����������������
     */
    public RouteListDocLink create(BaseValueIfc info) throws CreateException;

    /**
     * ����������ʱ���
     */
    public RouteListDocLink create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException;

}
