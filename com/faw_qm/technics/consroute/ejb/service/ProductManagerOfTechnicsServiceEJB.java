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
     * 获得指定零部件的工艺路线
     * @param partMasterBsoid　
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
     * 获得指定零部件的汇总信息
     * @param partID　零件ID
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
     * 获得指定零件的工艺信息
     * @param partID　零件ID
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
