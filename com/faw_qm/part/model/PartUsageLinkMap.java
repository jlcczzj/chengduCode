/** 生成程序 PartUsageLinkMap.java    1.0    2003/02/17
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 增加BOM行项和子组 liuyang 2014-6-20
 *  SS2 增加生产版本 xianglx 2014-8-12
 */

package com.faw_qm.part.model;

import com.faw_qm.framework.service.ObjectMappable;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;


/**
 * 零部件与零部件主信息的关联类的值对象接口的辅助类，
 * @author 吴先超
 * @version 1.0
 */

public class PartUsageLinkMap implements ObjectMappable
{
    private float quantity;
    private Unit defaultUnit = Unit.getUnitDefault();
    private QMQuantity qmQuantity = new QMQuantity();
    private boolean optionFlag;
    static final long serialVersionUID = -1L;


    /**
     * 构造函数。
     */
    public PartUsageLinkMap()
    {
        super();
    }


    /**
     * 获得使用数量。
     * @return float
     */
    public float getQuantity()
    {
        return quantity;
    }


    /**
     * 设置使用数量。
     * @param quantity1 float
     */
    public void setQuantity(float quantity1)
    {
        quantity = quantity1;
    }


    /**
     * 获得使用单位。
     * @return String
     */
    public Unit getDefaultUnit()
    {
        return defaultUnit;
    }


    /**
     * 设置使用单位。
     * @param unit Unit
     */
    public void setDefaultUnit(Unit unit)
    {
        defaultUnit = unit;
    }
    
    /**
     * 设置使用单位。
     * @param unit Unit
     */
    public void setDefaultUnit(String unit)
    {
        defaultUnit = Unit.toUnit(unit);
    }


    /**
     * 获取使用数量类对象。
     * @return QMQuantity
     */
    public QMQuantity getQMQuantity()
    {
        return qmQuantity;
    }


    /**
     * 设置使用数量类对象。
     * @param qmQuantity1 QMQuantity
     */
    public void setQMQuantity(QMQuantity qmQuantity1)
    {
        qmQuantity = qmQuantity1;
    }


    /**
     * 访问选装标识。
     *
     * @return boolean
     */
    public boolean getOptionFlag()
    {
        return optionFlag;
    }


    /**
     * 设置选装标识。
     *
     * @param flag boolean
     */
    public void setOptionFlag(boolean flag)
    {
        optionFlag = flag;
    }
//CCBegin SS1
    private String subUnitNumber;
    private String bomItem;
    public String getSubUnitNumber()
    {
    	return subUnitNumber;
    }
    public void setSubUnitNumber(String subUnitNumber1)
    {
    	subUnitNumber = subUnitNumber1;
    }
    public String getBomItem()
    {
    	return bomItem;
    }
    public void setBomItem(String bomItem1)
    {
    	bomItem = bomItem1;
    }
    //CCEnd SS1
//CCBegin SS2
    private String proVersion;
    public String getProVersion()
    {
    	return proVersion;
    }
    public void setProVersion(String proVersion1)
    {
    	proVersion = proVersion1;
    }
    //CCEnd SS2
}
