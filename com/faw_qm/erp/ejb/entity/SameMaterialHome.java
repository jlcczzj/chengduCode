package com.faw_qm.erp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.enterprise.ejb.entity.ItemHome;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

public interface SameMaterialHome extends ItemHome
{
    public SameMaterial create(BaseValueIfc basevalueifc) throws CreateException;

    public SameMaterial create(BaseValueIfc basevalueifc, Timestamp timestamp,
            Timestamp timestamp1) throws CreateException;

    public SameMaterial findByPrimaryKey(String s) throws FinderException;
}
