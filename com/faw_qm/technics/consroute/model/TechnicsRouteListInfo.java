/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/20 徐春英       原因：统一检入
 * CR2 2011/12/22 徐春英       原因：艺准增加预留属性
 * CR3 吕航  具体对象里不保存编号名称
 * SS1 原来代码赋值错误 pante 20130720
 */

package com.faw_qm.technics.consroute.model;

import java.util.Vector;

import com.faw_qm.capp.model.QMTechnicsMasterInfo;
import com.faw_qm.enterprise.model.RevisionControlledInfo;
import com.faw_qm.framework.exceptions.QMException;

//工艺路线表的编号，唯一标识。注意在sql中对对应的master建立唯一索引。服务在处理保存时要封装oracle异常。

/**
 * 工艺路线表值对象
 * @author 管春元
 * @version 1.0
 */
public class TechnicsRouteListInfo extends RevisionControlledInfo implements TechnicsRouteListIfc
{
    private static final long serialVersionUID = 1L;

    /**
     * 获得业务对象名。
     * @return String TechnicsRouteList
     */
    public String getBsoName()
    {
        return "consTechnicsRouteList";
    }

    /**
     * 工艺路线表的名称，需要进行非空检查.
     */
    private String name;

    /**
     * 工艺路线表的编号，唯一标识。进行非空检查。
     */
    private String number;

    /**
     * 路线表说明
     */
    private String description;

    /**
     * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
     */
    private String level;

    // 工艺路线表的状态
    private String state;

    /**
     * 二级路线表的单位ID.
     */
    private String department;

    /**
     * 二级路线表的单位名称.
     */
    private String departmentName;

    /**
     * 获得工艺路线表对应的产品ID.
     */
    private String productMasterID;

    private Vector partIndex;
    //begin CR2
    private String attribute1;
    private String attribute2;

    //end CR2

    /**
     * 构造函数
     */
    public TechnicsRouteListInfo()
    {
        TechnicsRouteListMasterInfo masterInfo = new TechnicsRouteListMasterInfo();
        this.setMaster(masterInfo);
    }

    /**
     * 获得工艺路线表的编号，唯一标识。
     * @return String 工艺路线表的编号
     */
    public String getRouteListNumber()
    {
        //CR3 begin
        return ((TechnicsRouteListMasterInfo) getMaster()).getRouteListNumber();
        //CR3 end
    }

    /**
     * 设置工艺路线表的编号，唯一标识
     * @param number -工艺路线表的编号，字符长度不超过30
     * @throws QMException 
     */
    public void setRouteListNumber(String number) throws QMException
    {
        if(number == null || number.trim().length() == 0)
        {
            throw new QMException("2", null,null);
        }
        ((TechnicsRouteListMasterInfo)getMaster()).setRouteListNumber(number);
        this.number = number;
    }

    /**
     * 得到零部件的顺序
     * @return Vector 零部件的顺序
     */
    public Vector getPartIndex()
    {
        return this.partIndex;
    }

    /**
     * 设置零部件的顺序
     * @param partIndex Vector 零部件的顺序
     */
    public void setPartIndex(Vector partIndex)
    {
        this.partIndex = partIndex;
    }

    /**
     * 获得工艺路线表的名称，需要进行非空检查
     * @return String 工艺路线表的名称
     */
    public String getRouteListName()
    {
        //CR3 begin
    	//SSBegin SS1
    	//return ((TechnicsRouteListMasterInfo) getMaster()).getRouteListNumber();
        return ((TechnicsRouteListMasterInfo) getMaster()).getRouteListName();
      //SSEnd SS1
        //CR3 end
    }

    /**
     * 设置工艺路线表的名称，需要进行非空检查
     * @param name String 工艺路线表的名称
     * @throws QMException 
     * @throws TechnicsRouteException
     */
    public void setRouteListName(String name) throws QMException
    {
        if(name == null || name.trim().length() == 0)
        {
            throw new QMException("1", null,null);
        }
        ((TechnicsRouteListMasterInfo)getMaster()).setRouteListName(name);
        this.name = name;
    }

    /**
     *获得 路线表说明
     * @return String 路线表说明
     */
    public String getRouteListDescription()
    {
        return description;
    }

    /**
     * 设置路线表说明
     * @param description 路线表说明
     */
    public void setRouteListDescription(String description)
    {
        this.description = description;
    }

    /**
     * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
     * @return String
     */
    public String getRouteListLevel()
    {
        return level;
    }

    /**
     * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
     * @param level 路线级别（是一级路线还是二级路线）
     */
    public void setRouteListLevel(String level)
    {
        this.level = level;
    }

    /**
     * 获得工艺路线表的状态
     * @return String 工艺路线表的状态
     */
    public String getRouteListState()
    {
        return state;
    }

    /**
     * 设置工艺路线表的状态
     * @param state 工艺路线表的状态
     */
    public void setRouteListState(String state)
    {
        this.state = state;
    }

    /**
     * 获得二级路线表的单位ID.
     * @return String 二级路线表的单位ID.
     */
    public String getRouteListDepartment()
    {
        return department;
    }

    /**
     * 设置二级路线表的单位ID.
     * @param department - 二级路线表的单位ID.
     */
    public void setRouteListDepartment(String department)
    {
        this.department = department;
    }

    /**
     * 获得二级路线表的单位名称.
     * @return String 二级路线表的单位名称.
     */
    public String getDepartmentName()
    {
        return departmentName;
    }

    /**
     * 设置二级路线表的单位名称.此方法有EJB调用。
     * @param department - 二级路线表的单位名称.
     */
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    /**
     * 获得工艺路线表对应的产品ID.
     * @return String 工艺路线表对应的产品ID.
     */
    public String getProductMasterID()
    {
        return productMasterID;
    }

    /**
     * 设置工艺路线表对应的产品ID.
     * @param productMasterID - 零部件ID.
     */
    public void setProductMasterID(String productMasterID)
    {
        ((TechnicsRouteListMasterInfo)getMaster()).setProductMasterID(productMasterID);
        this.productMasterID = productMasterID;
    }

    //begin CR1
    /**
     * 获得显示标识
     */
    public String getIdentity()
    {
        return this.getRouteListNumber() + "(" + this.getRouteListName() + ")" + this.getVersionValue();
    }

    //end CR1

    //begin CR2
    /**
     * 设置预留属性1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * 获得预留属性1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * 设置预留属性2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * 获得预留属性2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }
    //end CR2
}
