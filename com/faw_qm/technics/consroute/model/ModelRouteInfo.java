package com.faw_qm.technics.consroute.model;

import com.faw_qm.domain.model.DomainAdministeredMap;
import com.faw_qm.framework.service.BinaryLinkInfo;


public class ModelRouteInfo extends BinaryLinkInfo implements ModelRouteIfc
{
    private String domain;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Override
    public String getBsoName()
    {
        return "consModelRoute";
    }
    /**
     * ¹¹Ôìº¯Êý
     */
    public ModelRouteInfo()
    {

    }
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
	@Override
	public DomainAdministeredMap getDomainAdministeredMap() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDomainAdministeredMap(DomainAdministeredMap arg0) {
		// TODO Auto-generated method stub
		
	}
}
