/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.enterprise.ejb.entity.MasterEJB;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterInfo;

/**
 * 路线表主信息 编号唯一。不继承唯一性包的Identified接口。通过在数据库中设置唯一性约束保证其唯一。 在客户端截取异常时需做特殊处理。 编号与名称始终与对应的最新版本一致。 注意撤销检出时保持与最新版本的数据一致性。
 * @author 赵立彬
 * @version 1.0
 */
public abstract class TechnicsRouteListMasterEJB extends MasterEJB
{

    public TechnicsRouteListMasterEJB()
    {}

    /**
     * 获得工艺路线表的编号，唯一标识 工艺路线表有版本，编号的唯一性在对应的Master中保证。
     * @return String 工艺路线表的编号
     */
    public abstract java.lang.String getRouteListNumber();

    /**
     * 获得工艺路线表的编号，唯一标识 工艺路线表有版本，编号的唯一性在对应的Master中保证。
     *@param number String 工艺路线表的编号
     */

    public abstract void setRouteListNumber(String number);

    /**
     * 获得工艺路线表的名称。
     * @return String 工艺路线表的名称。
     */
    public abstract java.lang.String getRouteListName();

    /**
     * 设置工艺路线表的名称
     * @param name String 工艺路线表的名称
     */
    public abstract void setRouteListName(String name);

    /**
     * 获得工艺路线表对应的产品ID.
     * @return String 工艺路线表对应的产品ID.
     */
    public abstract String getProductMasterID();

    /**
     * 设置工艺路线表对应的产品ID.
     * @param productMasterID String 工艺路线表对应的产品ID.
     */
    public abstract void setProductMasterID(String productMasterID);

    /**
     * 获得业务对象名。
     * @return String TechnicsRouteListMaster
     */
    public String getBsoName()
    {
        return "consTechnicsRouteListMaster";
    }

    /**
     * 获得值对象。
     * @throws QMException
     * @return BaseValueIfc 值对象。
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        TechnicsRouteListMasterInfo info = new TechnicsRouteListMasterInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * 对新建值对象进行设置。
     * @param info BaseValueIfc 值对象
     * @throws QMException
     * @see BaseValueInfo
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        TechnicsRouteListMasterInfo listinfo = (TechnicsRouteListMasterInfo)info;
        listinfo.setRouteListName(this.getRouteListName());
        listinfo.setRouteListNumber(this.getRouteListNumber());
        listinfo.setProductMasterID(this.getProductMasterID());
    }

    /**
     * 根据值对象进行持久化。
     * @param info BaseValueIfc 值对象
     * @throws CreateException 
     * @throws CreateException
     * @see BaseValueInfo
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter TechnicsRouteListMasterEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        TechnicsRouteListMasterInfo listinfo = (TechnicsRouteListMasterInfo)info;
        this.setRouteListName(listinfo.getRouteListName());
        this.setRouteListNumber(listinfo.getRouteListNumber());
        this.setProductMasterID(listinfo.getProductMasterID());
    }

    /**
     * 根据值对象进行更新。
     * @param info BaseValueIfc 值对象
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter TechnicsRouteListMasterEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        TechnicsRouteListMasterInfo listinfo = (TechnicsRouteListMasterInfo)info;
        this.setRouteListName(listinfo.getRouteListName());
        this.setRouteListNumber(listinfo.getRouteListNumber());
        this.setProductMasterID(listinfo.getProductMasterID());
    }
}
