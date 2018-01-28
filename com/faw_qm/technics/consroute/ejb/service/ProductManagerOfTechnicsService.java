package com.faw_qm.technics.consroute.ejb.service;

import java.util.Collection;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;

public interface ProductManagerOfTechnicsService extends BaseService
{

    public Collection getRouteListByPart(String partMasterBsoid);

    public Collection getSummaryByPart(String partID) throws QMException;

    public Collection getTechnicsBypart(String partID) throws QMException;
}
