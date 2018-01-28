/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/20 吕航 统一重命名
 * CR2 2011/12/20 徐春英       原因：统一检入
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.cappclients.conscapproute.util.CappRouteRB;
import com.faw_qm.clients.rename.model.RenameIfc;
import com.faw_qm.enterprise.model.MasterInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;

/**
 * 工艺路线表主信息值对象
 * @author 管春元
 * @version 1.0
 */
public class TechnicsRouteListMasterInfo extends MasterInfo implements TechnicsRouteListMasterIfc, RenameIfc//CR1
{

    //路线表名
    private String name;

    //路线表编号
    private String number;

    //产品主信息id（PastMasrer）
    private String productMasterID;
    private static final long serialVersionUID = 1L;
    //CR1
    /** 资源文件路径 */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /**
     * 构造函数
     */
    public TechnicsRouteListMasterInfo()
    {

    }

    /**
     *得到业务类名
     * @return String TechnicsRouteListMaster
     */
    public String getBsoName()
    {
        return "consTechnicsRouteListMaster";
    }

    /**
     * 得到路线表的编号
     * @return String 路线表的编号
     */
    public java.lang.String getRouteListNumber()
    {
        return number;
    }

    /**
     * 设置路线表的编号
     * @param number String 路线表的编号
     */
    public void setRouteListNumber(String number)
    {
        this.number = number;
    }

    /**
     * 得到路线表名
     * @return String 路线表名
     */
    public java.lang.String getRouteListName()
    {
        return name;
    }

    /**
     * 设置路线表名
     * @param name String 路线表名
     */
    public void setRouteListName(String name)
    {
        this.name = name;
    }

    /**
     * 获得工艺路线表对应的产品ID.
     * @return String 工艺路线表对应的产品ID.
     */
    public String getProductMasterID()
    {
        return this.productMasterID;
    }

    /**
     * 设置工艺路线表对应的产品ID.
     * @param productMasterID - 零部件ID.
     */
    public void setProductMasterID(String productMasterID)
    {
        this.productMasterID = productMasterID;
    }

    //begin CR2
    /**
     * 获得显示标识
     */
    public String getIdentity()
    {
        return this.getRouteListNumber() + "(" + this.getRouteListName() + ")";
    }

    //end CR2

    //begin CR1
    public String getNameLabel()
    {
        return "路线表名称";
    }

    public String getNameText()
    {
        return this.getRouteListName();
    }

    public String getNumberLabel()
    {
        return "路线表编号";
    }

    public String getNumberText()
    {
        return this.getRouteListNumber();
    }

    public boolean isRenameTwoAttribute()
    {
        return true;
    }

    public Object rename(String number, String name) throws QMException
    {
        TechnicsRouteListMasterIfc routelist = (TechnicsRouteListMasterIfc)RequestHelper.request("PersistService", "refreshInfo", new Class[]{BaseValueIfc.class}, new Object[]{this});
        //客户端掉服务端方法进行重命名
        if(routelist.getRouteListNumber().equals(number) && routelist.getRouteListName().equals(name))
        {
            // 通过标识工厂获得与给定值对象对应的显示标识对象。
            DisplayIdentity displayidentity = IdentityFactory.getDisplayIdentity(routelist);
            String s = displayidentity.getLocalizedMessage(null);
            Object aobj[] = {s};
            throw new QMException(RESOURCE, CappRouteRB.NO_CHANGE_MADE, aobj);
        }
        TechnicsRouteListMasterIfc masterObj;
        try
        {
            routelist.setRouteListNumber(number);
            routelist.setRouteListName(name);
            masterObj = (TechnicsRouteListMasterIfc)RequestHelper.request("consTechnicsRouteService", "rename", new Class[]{TechnicsRouteListMasterIfc.class}, new Object[]{routelist});
        }catch(Exception ex)
        {
            throw new QMException(ex);
        }
        return masterObj;
    }
    //end CR1

}
