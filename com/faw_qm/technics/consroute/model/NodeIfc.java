/**
 * ���ɳ��� NodeIfc.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title:NodeIfc.java</p> <p>Description:�ڵ�ӿ� </p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author unascribed
 * @version 1.0
 */
public interface NodeIfc extends BaseValueIfc
{
    /**
     * ��ù���·��ֵ����
     * @return com.faw_qm.technics.consroute.model.TechnicsRouteIfc
     * @roseuid 4032F5BD01BD
     */
    public TechnicsRouteIfc getRouteInfo();

    /**
     * ���ù���·��ֵ����
     * @param routeInfo
     * @roseuid 403AB50F0043
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo);
}
