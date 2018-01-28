/** 生成程序 ListRoutePartLinkEJB 1.0 2005.3.2
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/22 徐春英      原因:增加预留属性
 * CR2 2011/12/22 徐春英       原因：增加产品信息和路线信息
 * CR3 2011/12/30 徐春英      原因：通过关联对象获得路线值对象
 * CR4 2012/01/10 徐春英      原因：增加路线生效时间和失效时间
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
 * 典型工艺
 * @author 吕航
 * @version 1.0 2012.01.13
 */
abstract public class ModelRouteEJB extends BinaryLinkEJB
{
    /**
     * 构造方法
     */
    public ModelRouteEJB()
    {
        super();
    }
    /**
     * 获取域名。
     * @return String
     */
    public abstract String getDomain();


    /**
     * 设置域名。
     * @param domain String
     */
    public abstract void setDomain(String domain);

    /**
     * 得到业务类名
     * @return String ListRoutePartLink
     */
    public String getBsoName()
    {
        return "consModelRoute";
    }

    /**
     * 设置关联类的值对象。
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
     * 获得值对象。
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
     * 根据关联类值对象创建关联类的业务对象。
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
     * 根据值对象更新关联类的业务对象。
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
