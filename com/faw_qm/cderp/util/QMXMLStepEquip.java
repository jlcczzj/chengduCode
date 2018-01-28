/**
 * 生成程序QMXMLStepEqui.java	1.0              2007-10-30
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.faw_qm.cderp.exception.QMXMLException;

/**
 * <p>Title: 工序关联的设备信息。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class QMXMLStepEquip extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLStepEquip.class);

    /**
     * 缺省构造函数。
     */
    public QMXMLStepEquip()
    {
        super();
    }

    //工序号
    private int stepSerNumber;

    //部门代码
    private String routeCode;

    //设备编号
    private String equipNumber;

    //设备名称
    private String equipName;

    //设备型号
    private String modelNumber;

    //平面图号
    private String posNumber;

    //使用数量
    private int useQuantity;

    /**
     * 获取设备名称。
     * @return 返回 equiName。
     */
    public String getEquipName()
    {
        return equipName;
    }

    /**
     * 设置设备名称。
     * @param equiName 要设置的 equiName。
     */
    public void setEquipName(String equipName)
    {
        this.equipName = equipName;
    }

    /**
     * 获取设备编号。
     * @return 返回 equiNumber。
     */
    public String getEquipNumber()
    {
        return equipNumber;
    }

    /**
     * 设置设备编号。
     * @param equiNumber 要设置的 equiNumber。
     */
    public void setEquipNumber(String equipNumber)
    {
        this.equipNumber = equipNumber;
    }

    /**
     * 获取设备型号。
     * @return 返回 modelNumber。
     */
    public String getModelNumber()
    {
        return modelNumber;
    }

    /**
     * 设置设备型号。
     * @param modelNumber 要设置的 modelNumber。
     */
    public void setModelNumber(String modelNumber)
    {
        this.modelNumber = modelNumber;
    }

    /**
     * 获取平面图号。
     * @return 返回 posNumber。
     */
    public String getPosNumber()
    {
        return posNumber;
    }

    /**
     * 设置平面图号。
     * @param posNumber 要设置的 posNumber。
     */
    public void setPosNumber(String posNumber)
    {
        this.posNumber = posNumber;
    }

    /**
     * 获取部门代码。
     * @return 返回 routeCode。
     */
    public String getRouteCode()
    {
        return routeCode;
    }

    /**
     * 设置部门代码。
     * @param routeCode 要设置的 routeCode。
     */
    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }

    /**
     * 获取工序号。
     * @return 返回 stepSerNumber。
     */
    public int getStepSerNumber()
    {
        return stepSerNumber;
    }

    /**
     * 设置工序号。
     * @param stepSerNumber 要设置的 stepSerNumber。
     */
    public void setStepSerNumber(int stepSerNumber)
    {
        this.stepSerNumber = stepSerNumber;
    }

    /**
     * 获取使用数量。
     * @return 返回 useQuantity。
     */
    public int getUseQuantity()
    {
        return useQuantity;
    }

    /**
     * 设置使用数量。
     * @param useQuantity 要设置的 useQuantity。
     */
    public void setUseQuantity(int useQuantity)
    {
        this.useQuantity = useQuantity;
    }

    /**
     * 设置record元素。
     * @return QMXMLRecord
     * @throws QMXMLException
     */
    public final QMXMLRecord getRecord() throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getRecord() - start"); //$NON-NLS-1$
        }
        final QMXMLRecord record = new QMXMLRecord();
        final List propertyList = getPropertyList();
        QMXMLProperty property = null;
        QMXMLColumn col = null;
        final List colList = new ArrayList();
        final List extensionPropList = new ArrayList(1);
        final List basicPropList = new ArrayList(7);
        final List ibaPropList = new ArrayList(5);
        for (int i = 0; i < propertyList.size(); i++)
        {
            property = (QMXMLProperty) propertyList.get(i);
            if(property.getType().equals("extension"))
                extensionPropList.add(property);
            else if(property.getType().equals("basic"))
                basicPropList.add(property);
            else if(property.getType().equals("iba"))
                ibaPropList.add(property);
        }
        for (int i = 0; i < basicPropList.size(); i++)
        {
            property = (QMXMLProperty) basicPropList.get(i);
            col = new QMXMLColumn();
            if(property.getName().equals("step_serial_number"))
            {
                col.setId(property.getId());
                col.setValue(Integer.toString(getStepSerNumber()));
            }
            else if(property.getName().equals("route_code"))
            {
                col.setId(property.getId());
                col.setValue(getRouteCode());
            }
            else if(property.getName().equals("equi_number"))
            {
                col.setId(property.getId());
                col.setValue(getEquipNumber());
            }
            else if(property.getName().equals("equi_name"))
            {
                col.setId(property.getId());
                col.setValue(getEquipName());
            }
            else if(property.getName().equals("model_number"))
            {
                col.setId(property.getId());
                col.setValue(getModelNumber());
            }
            else if(property.getName().equals("position_number"))
            {
                col.setId(property.getId());
                col.setValue(getPosNumber());
            }
            else if(property.getName().equals("use_quantity"))
            {
                col.setId(property.getId());
                col.setValue(Integer.toString(getUseQuantity()));
            }
            colList.add(col);
        }
        record.setColList(colList);
        if(logger.isDebugEnabled())
        {
            logger.debug("getRecord() - end" + record); //$NON-NLS-1$
        }
        return record;
    }
}
