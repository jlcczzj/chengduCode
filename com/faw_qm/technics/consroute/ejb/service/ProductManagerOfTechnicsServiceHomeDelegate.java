package com.faw_qm.technics.consroute.ejb.service;

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.ServiceHomeDelegate;

public class ProductManagerOfTechnicsServiceHomeDelegate implements ServiceHomeDelegate
{
    private ProductManagerOfTechnicsServiceHome home = null;

    public void init(Object obj) 
    {
        if(!(obj instanceof ProductManagerOfTechnicsServiceHome))
        {
            Object[] objs = {obj.getClass(), "ProductManagerOfTechnicsServiceHome"};
            throw new QMRuntimeException("com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (ProductManagerOfTechnicsServiceHome)obj;
    }

    public BaseService create() throws CreateException
    {
        return home.create();
    }
}
