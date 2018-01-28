/** 生成程序 PartUsageLink.java    1.0    2003/02/17
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 *  SS1 增加BOM行项和子组 liuyang 2014-6-20
 *  SS2 增加生产版本 xianglx 2014-8-12
 */

package com.faw_qm.part.ejb.entity;

import com.faw_qm.build.ejb.entity.BuildableLink;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.struct.ejb.entity.IteratedUsageLink;


/**
 * 零部件与零部件主信息的关联类，主要记录了零部件之间的使用关系及使用的数量和单位。
 * @author 吴先超
 * @version 1.0
 */

public interface PartUsageLink extends IteratedUsageLink, BuildableLink
{
    /**
     * 获得使用数量。
     * @return float
     */
    public float getQuantity();


    /**
     * 设置使用数量。
     * @param quantity float
     */
    public void setQuantity(float quantity);


    /**
     * 获得使用单位。受CMP管理
     * @return String
     */
    public abstract String getDefaultUnitStr();


    /**
     * 获取使用的单位。
     * @return Unit
     */
    public Unit getDefaultUnit();


    /**
     * 设置使用单位。受CMP管理。
     * @param unit String
     */

    public abstract void setDefaultUnitStr(String unit);


    /**
     * 设置使用单位。
     * @param unit Unit
     */
    public void setDefaultUnit(Unit unit);


    /**
     * 获取使用数量类的对象。该对象包含了使用数量和默认的使用单位。
     * @return QMQuantity
     */
    public QMQuantity getQMQuantity();


    /**
     * 设置使用单位类的对象。
     * @param qmQuantity1 QMQuantity
     */
    public void setQMQuantity(QMQuantity qmQuantity1);


    /**
     * 访问选装标识。
     *
     * @return boolean
     */
    public boolean getOptionFlag();


    /**
     * 设置选装标识。
     *
     * @param flag boolean
     */
    public void setOptionFlag(boolean flag);
    //CCBegin SS1
    public String getSubUnitNumber();
    public void setSubUnitNumber(String subUnitNumber);    
    
    public String getBomItem();
    public void setBomItem(String bomItem); 
    //CCEnd SS1
		//CCBegin SS2
    public String getProVersion();
    public void setProVersion(String proVersion); 
		//CCEnd SS2
}
