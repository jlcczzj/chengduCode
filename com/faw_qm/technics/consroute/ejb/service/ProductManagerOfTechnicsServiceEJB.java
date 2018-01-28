package com.faw_qm.technics.consroute.ejb.service;

import java.util.Collection;

import java.util.*;

import com.faw_qm.capp.ejb.standardService.StandardCappService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.summary.ejb.service.TotalService;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.persist.ejb.service.PersistService;

public class ProductManagerOfTechnicsServiceEJB extends BaseServiceImp
{

    /**
     * ���ָ���㲿���Ĺ���·��
     * @param partMasterBsoid��
     * @return
     * @throws QMException
     */
    public Collection getRouteListByPart(String partMasterBsoid) throws QMException
    {
        Collection coll = null;

        TechnicsRouteService routeServer = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");

        coll = routeServer.getListsByPart(partMasterBsoid);

        return coll;
    }

    public String getServiceName()
    {

        return "ProductManagerOfTechnicsService";
    }

    /**
     * ���ָ���㲿���Ļ�����Ϣ
     * @param partID�����ID
     * @return
     * @throws QMException
     */
    public Collection getSummaryByPart(String partID) throws QMException
    {
        Collection coll = null;
        TotalService ts = (TotalService)EJBServiceHelper.getService("TotalService");
        coll = ts.getTotalSchemasByPart(partID);
        return coll;
    }

    /**
     * ���ָ������Ĺ�����Ϣ
     * @param partID�����ID
     * @return
     * @throws QMException
     */
    public Collection getTechnicsBypart(String partID) throws QMException
    {

        Collection coll = null;
        StandardCappService standardCappserver = (StandardCappService)EJBServiceHelper.getService("StandardCappService");
        coll = standardCappserver.browseQMTechnicsByPart(partID, false);
        return coll;
    }

}
