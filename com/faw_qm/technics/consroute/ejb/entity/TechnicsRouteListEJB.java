/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/22 徐春英      原因:增加预留属性
 * CR2 吕航  具体对象里不保存编号名称
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.util.Vector;

import javax.ejb.CreateException;

import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.enterprise.ejb.entity.RevisionControlledEJB;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.util.EJBServiceHelper;

/**
 * 工艺路线服务端类,提供路线表的基本属性信息
 * @author 赵立彬
 * @version 1.0
 */
public abstract class TechnicsRouteListEJB extends RevisionControlledEJB
{

    public TechnicsRouteListEJB()
    {

    }

    //   * 工艺路线表的编号，唯一标识。需要进行非空检查。
    //   * 工艺路线表有版本，编号的唯一性在对应的Master中保证。

    /**
     * 获得工艺路线表编号
     * @return String 工艺路线表编号
     */
    public abstract java.lang.String getRouteListNumber();

    //  * 工艺路线表的编号，唯一标识。工艺路线表有版本，编号的唯一性在对应的Master中保证。

    /**
     * 设置工艺路线表编号
     * @param number String 工艺路线表编号
     */
    public abstract void setRouteListNumber(String number);

    //   * 工艺路线表的名称，需要进行非空检查。

    /**
     * 获得工艺路线表的名称
     * @return String 工艺路线表的名称
     */
    public abstract java.lang.String getRouteListName();

    //  * 工艺路线表的名称，需要进行非空检查

    /**
     * 设置工艺路线表的名称
     * @param name String 工艺路线表的名称
     */
    public abstract void setRouteListName(String name);

    /**
     * 获得路线表说明
     * @return String 路线表说明
     */
    public abstract java.lang.String getRouteListDescription();

    /**
     * 设置路线表说明
     * @param description String 路线表说明
     */

    public abstract void setRouteListDescription(String description);

    /**
     * 获得路线表级别(一级路线还是二级路线) 值取自枚举集(一级、二级).
     * @return String 路线表级别
     */
    public abstract java.lang.String getRouteListLevel();

    /**
     * 设置路线表级别(一级路线还是二级路线) 值取自枚举集(一级、二级).
     * @return String 路线表级别
     */

    public abstract void setRouteListLevel(String level);

    /**
     * 获得路线表状态
     * @return String 路线表状态
     */
    public abstract java.lang.String getRouteListState();

    /**
     * 设置路线表状态
     * @return String 路线表状态
     */

    public abstract void setRouteListState(String state);

    /**
     * 获得二级路线表的单位标识
     * @return String 二级路线表的单位标识
     */
    public abstract java.lang.String getRouteListDepartment();

    /**
     * 获得二级路线表的单位标识
     * @return String 二级路线表的单位标识
     */

    public abstract void setRouteListDepartment(String department);

    /**
     * 获得工艺路线表对应的产品ID.
     * @return String 工艺路线表对应的产品ID.
     */
    public abstract java.lang.String getProductMasterID();

    /**
     * 设置工艺路线表对应的产品ID.
     * @return String 工艺路线表对应的产品ID.
     */

    public abstract void setProductMasterID(String productMasterID);

    /**
     * 得到零部件的序列
     * @return Vector 零部件的序列
     */
    public abstract Vector getPartIndex();

    /**
     * 设置零部件的序列
     * @return Vector 零部件的序列
     */

    public abstract void setPartIndex(Vector partIndex);

    //begin CR1
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
     * @return String TechnicsRouteList
     */
    public String getBsoName()
    {
        return "consTechnicsRouteList";
    }

    /**
     * 获得值对象。
     * @throws QMException
     * @return BaseValueIfc
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        TechnicsRouteListInfo info = new TechnicsRouteListInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * 对新建值对象进行设置。
     * @param info BaseValueIfc 要设置的值对象
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo)info;
        //CR2 begin
//        listinfo.setRouteListName(this.getRouteListName());
//        listinfo.setRouteListNumber(this.getRouteListNumber());
        //CR2 end
        listinfo.setRouteListDescription(this.getRouteListDescription());
        listinfo.setRouteListDepartment(this.getRouteListDepartment());
        if(this.getRouteListDepartment() != null)
        {
            listinfo.setDepartmentName(getDepartmentName(this.getRouteListDepartment()));
        }else
        {
            listinfo.setDepartmentName(getFirstDepartmentID());
            //throw new TechnicsRouteException("单位ID不应该为空。");
        }
        //保存枚举类的值。
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("TechnicsRouteListEJB's setValueInfo RouteListLevelType.display = " + this.getRouteListLevel());
        }
        listinfo.setRouteListLevel(RouteListLevelType.toRouteListLevelType(this.getRouteListLevel()).getDisplay());
        listinfo.setRouteListState(this.getRouteListState());
        listinfo.setProductMasterID(this.getProductMasterID());
        listinfo.setPartIndex(this.getPartIndex());
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        if(this.getMasterBsoID() == null)
        {
            throw new QMException("MasterBsoID不应为空。");
        }
        MasterIfc masterInfo = (MasterIfc)pservice.refreshInfo(this.getMasterBsoID());
        listinfo.setMaster(masterInfo);
        //begin CR1
        listinfo.setAttribute1(this.getAttribute1());
        listinfo.setAttribute2(this.getAttribute2());
        //end CR1
    }

    private String getFirstDepartmentID()
    {
        return null;
    }

    /**
     * 获得单位名称。
     * @param departmentID String 单位ID
     * @throws QMException
     * @return String 单位名称
     * @throws QMException 
     */
    private String getDepartmentName(String departmentID) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        BaseValueIfc codeInfo = pservice.refreshInfo(departmentID);
        String departmentName = null;
        if(codeInfo instanceof CodingIfc)
        {
            departmentName = ((CodingIfc)codeInfo).getCodeContent();
        }
        if(codeInfo instanceof CodingClassificationIfc)
        {
            departmentName = ((CodingClassificationIfc)codeInfo).getCodeSort();
        }
        return departmentName;
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
            System.out.println("enter TechnicsRouteListEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo)info;
        //CR2 begin
//        this.setRouteListName(listinfo.getRouteListName());
//        this.setRouteListNumber(listinfo.getRouteListNumber());
        //CR2 end
        this.setRouteListDescription(listinfo.getRouteListDescription());
        this.setRouteListDepartment(listinfo.getRouteListDepartment());
        String value = RouteListLevelType.getValue(listinfo.getRouteListLevel());
        this.setRouteListLevel(value);
        this.setPartIndex(listinfo.getPartIndex());
        this.setRouteListState(listinfo.getRouteListState());
        this.setProductMasterID(listinfo.getProductMasterID());
        //begin CR1
        this.setAttribute1(listinfo.getAttribute1());
        this.setAttribute2(listinfo.getAttribute2());
        //end CR1
    }

    /**
     * 根据值对象进行更新
     * @param info BaseValueIfc 值对象
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter TechnicsRouteListEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo)info;
        //CR2 begin
//        this.setRouteListName(listinfo.getRouteListName());
//        this.setRouteListNumber(listinfo.getRouteListNumber());
        //CR2 end
        this.setRouteListDescription(listinfo.getRouteListDescription());
        this.setRouteListDepartment(listinfo.getRouteListDepartment());
        this.setRouteListLevel(RouteHelper.getValue(RouteListLevelType.getRouteListLevelTypeSet(), listinfo.getRouteListLevel()));
        this.setRouteListState(listinfo.getRouteListState());
        this.setPartIndex(listinfo.getPartIndex());
        this.setProductMasterID(listinfo.getProductMasterID());
        //begin CR1
        this.setAttribute1(listinfo.getAttribute1());
        this.setAttribute2(listinfo.getAttribute2());
        //end CR1
    }
}
