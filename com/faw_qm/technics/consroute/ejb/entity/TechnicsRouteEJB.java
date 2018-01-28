/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/21 徐春英      原因：工艺路线补充需求说明的实现，在路线对象中增加"更改标记"属性,另外再预留两个属性
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.util.Collection;

import java.util.Iterator;

import javax.ejb.CreateException;

import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.TechnicsRouteInfo;
import com.faw_qm.util.EJBServiceHelper;

/**
 * 工艺路线 <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author 管春元
 * @version 1.0
 */
public abstract class TechnicsRouteEJB extends BsoReferenceEJB
{

    public TechnicsRouteEJB()
    {

    }

    /**
     * 路线说明
     */
    public abstract java.lang.String getRouteDescription();

    /**
     * 路线说明。
     */
    public abstract void setRouteDescription(String description);

    //begin CR1
    /**
     * 获得更改标记
     */
    public abstract String getModifyIdenty();

    /**
     * 设置更改标记
     */
    public abstract void setModifyIdenty(String identy);

    /**
     * 设置预留属性1
     */
    public abstract void setAttribute1(String attribute1);

    /**
     * 获得预留属性1
     */
    public abstract String getAttribute1();

    /**
     * 设置预留属性2
     */
    public abstract void setAttribute2(String attribute2);

    /**
     * 获得预留属性2
     */
    public abstract String getAttribute2();

    //end CR1
    /**
     * 获得业务对象名。
     */
    public String getBsoName()
    {
        return "consTechnicsRoute";
    }

    /**
     * 获得值对象。
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        TechnicsRouteInfo info = new TechnicsRouteInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * 对新建值对象进行设置。
     * @throws QMException 
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        TechnicsRouteInfo routeInfo = (TechnicsRouteInfo)info;
        routeInfo.setRouteDescription(this.getRouteDescription());
        //routeInfo.setRouteListID(this.getRouteListID());
        //routeInfo.setPartMasterID(this.getPartMasterID());
        //begin CR1
        if(this.getModifyIdenty() != null)
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            CodingIfc codInfo = (CodingIfc)pservice.refreshInfo(this.getModifyIdenty());
            routeInfo.setModifyIdenty(codInfo.getCodeContent());
        }
        routeInfo.setAttribute1(this.getAttribute1());
        routeInfo.setAttribute2(this.getAttribute2());
        //end CR1
    }

    /**
     * 根据值对象进行持久化。
     * @throws CreateException 
     * @throws ServiceLocatorException 
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter TechnicsRouteEJB's updateByValueInfo");
        }
        super.createByValueInfo(info);
        TechnicsRouteInfo routeInfo = (TechnicsRouteInfo)info;
        this.setRouteDescription(routeInfo.getRouteDescription());
        //this.setRouteListID(routeInfo.getRouteListID());
        //this.setPartMasterID(routeInfo.getPartMasterID());
        //begin CR1
        String identy = routeInfo.getModifyIdenty();
        System.out.println("identy===" + identy);
        try {
        CodingManageService codeService;
		
			codeService = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
		
        Collection codes = codeService.findCoding("consTechnicsRoute", "modifyIdenty");
        if(codes != null && codes.size() > 0)
        {
            Iterator i = codes.iterator();
            String codeID = null;
            while(i.hasNext())
            {
                CodingIfc code = (CodingIfc)i.next();
                if(code.getCodeContent().equals(identy))
                    codeID = code.getBsoID();
            }
            System.out.println("codeID===" + codeID);
            this.setModifyIdenty(codeID);
        }
        this.setAttribute1(routeInfo.getAttribute1());
        this.setAttribute2(routeInfo.getAttribute2());
        //end CR1
		} catch (QMException e) {
			e.printStackTrace();
			return;
		}
    }

    /**
     * 根据值对象进行更新。
     * @throws QMException 
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter TechnicsRouteEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        TechnicsRouteInfo routeInfo = (TechnicsRouteInfo)info;
        this.setRouteDescription(routeInfo.getRouteDescription());
        //this.setRouteListID(routeInfo.getRouteListID());
        //this.setPartMasterID(routeInfo.getPartMasterID());
        //begin CR1
        String identy = routeInfo.getModifyIdenty();
        CodingManageService codeService = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
        Collection codes = codeService.findCoding("consTechnicsRoute", "modifyIdenty");
        if(codes != null && codes.size() > 0)
        {
            Iterator i = codes.iterator();
            String codeID = null;
            while(i.hasNext())
            {
                CodingIfc code = (CodingIfc)i.next();
                if(code.getCodeContent().equals(identy))
                    codeID = code.getBsoID();
            }
            this.setModifyIdenty(codeID);
        }
        this.setAttribute1(routeInfo.getAttribute1());
        this.setAttribute2(routeInfo.getAttribute2());
        //end CR1
    }
}
