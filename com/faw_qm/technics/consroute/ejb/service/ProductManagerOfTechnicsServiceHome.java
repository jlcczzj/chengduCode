package com.faw_qm.technics.consroute.ejb.service;

import javax.ejb.CreateException;

import com.faw_qm.framework.service.BaseServiceHome;

public interface ProductManagerOfTechnicsServiceHome extends BaseServiceHome
{

    public abstract ProductManagerOfTechnicsService create() throws CreateException;
}
