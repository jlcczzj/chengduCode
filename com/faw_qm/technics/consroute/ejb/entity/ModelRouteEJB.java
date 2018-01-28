/** ���ɳ��� ListRoutePartLinkEJB 1.0 2005.3.2
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/22 �촺Ӣ      ԭ��:����Ԥ������
 * CR2 2011/12/22 �촺Ӣ       ԭ�����Ӳ�Ʒ��Ϣ��·����Ϣ
 * CR3 2011/12/30 �촺Ӣ      ԭ��ͨ������������·��ֵ����
 * CR4 2012/01/10 �촺Ӣ      ԭ������·����Чʱ���ʧЧʱ��
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;

import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;
import com.faw_qm.part.model.PartAlternateLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.ModelRouteInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.util.RouteAdoptedType;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * ���͹���
 * @author ����
 * @version 1.0 2012.01.13
 */
abstract public class ModelRouteEJB extends BinaryLinkEJB
{
    /**
     * ���췽��
     */
    public ModelRouteEJB()
    {
        super();
    }
    /**
     * ��ȡ������
     * @return String
     */
    public abstract String getDomain();


    /**
     * ����������
     * @param domain String
     */
    public abstract void setDomain(String domain);

    /**
     * �õ�ҵ������
     * @return String ListRoutePartLink
     */
    public String getBsoName()
    {
        return "consModelRoute";
    }

    /**
     * ���ù������ֵ����
     * @param info BaseValueIfc
     * @throws QMException 
     
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
          
    {
        super.setValueInfo(info);
        ModelRouteInfo mInfo = (ModelRouteInfo) info;
        mInfo.setDomain(getDomain());
    }
    /**
     * ���ֵ����
     * @throws QMException
     * @return BaseValueIfc
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        ModelRouteInfo info = new ModelRouteInfo();
        setValueInfo(info);
        return info;
    }
    /**
     * ���ݹ�����ֵ���󴴽��������ҵ�����
     * @param info BaseValueIfc
     * @throws CreateException 
     *
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
          
    {
        super.createByValueInfo(info);
        ModelRouteInfo linkInfo = (ModelRouteInfo) info;
        setDomain(linkInfo.getDomain());
    }


    /**
     * ����ֵ������¹������ҵ�����
     * @param info BaseValueIfc
     * @throws QMException 
     * 
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
            
    {
        super.updateByValueInfo(info);
        ModelRouteInfo linkInfo = (ModelRouteInfo) info;
        setDomain(linkInfo.getDomain());
    }
}
