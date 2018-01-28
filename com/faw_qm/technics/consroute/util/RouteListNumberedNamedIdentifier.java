/**
 * ���ɳ��� RouteListNumberedNamedIdentifier.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.identity.StandardNumberedNamedIdentifier;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterIfc;

/**
 * <p>Title:RouteListNumberedNamedIdentifier.java</p> <p>Description:����·�߱�����Ʊ�ʶ </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:һ������</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteListNumberedNamedIdentifier extends StandardNumberedNamedIdentifier
{
    /**
     * ���캯��
     * @param obj
     */
    public RouteListNumberedNamedIdentifier(Object obj) throws QMException
    {
        number = ((TechnicsRouteListMasterIfc)obj).getRouteListNumber();
        try
        {
            name = ((TechnicsRouteListMasterIfc)obj).getRouteListName();
            return;
        }catch(Exception qmruntimeexception1)
        {
            System.out.println(qmruntimeexception1);
        }
        System.out.println("ignoring - set name to null ");
        name = null;
    }

}
