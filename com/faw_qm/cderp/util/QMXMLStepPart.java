/**
 * 生成程序QMXMLStepPart.java	1.0              2007-10-30
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
 * <p>Title: 工序关联的子件信息。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class QMXMLStepPart extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLStepPart.class);

    //工序号
    private int stepSerNumber;

    //物料号
    private String matNumber;

    //部门代码(采用路线代码)
    private String routeCode;

    //零部件编号
    private String partNumber;

    //零部件名称
    private String partName;

    //使用数量
    private int useQuantity;

    //零部件是否已拆分为物料
    private String splited;

    /**
     * 缺省构造函数。
     */
    public QMXMLStepPart()
    {
        super();
    }

    /**
     * 获取物料号。
     * @return 返回 matNumber。
     */
    public String getMatNumber()
    {
        return matNumber;
    }

    /**
     * 设置物料号。
     * @param matNumber 要设置的 matNumber。
     */
    public void setMatNumber(String matNumber)
    {
        this.matNumber = matNumber;
    }

    /**
     * 获取零部件名称。
     * @return 返回 partName。
     */
    public String getPartName()
    {
        return partName;
    }

    /**
     * 设置零部件名称。
     * @param partName 要设置的 partName。
     */
    public void setPartName(String partName)
    {
        this.partName = partName;
    }

    /**
     * 获取零部件编号。
     * @return 返回 partNumber。
     */
    public String getPartNumber()
    {
        return partNumber;
    }

    /**
     * 设置零部件编号。
     * @param partNumber 要设置的 partNumber。
     */
    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
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
     * 获取零部件是否拆分的标记：已拆分为“1”，未拆分为“0”。
     * @return 返回 splited。
     */
    public String getSplited()
    {
        return splited;
    }

    /**
     * 设置零部件是否拆分的标记：已拆分为“1”，未拆分为“0”。
     * @param splited 要设置的 splited。
     */
    public void setSplited(boolean splited)
    {
        if(splited)
        {
            this.splited = "1";
        }
        else
        {
            this.splited = "0";
        }
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
            else if(property.getName().equals("material_number"))
            {
                col.setId(property.getId());
                col.setValue(getMatNumber());
            }
            else if(property.getName().equals("route_code"))
            {
                col.setId(property.getId());
                col.setValue(getRouteCode());
            }
            else if(property.getName().equals("part_number"))
            {
                col.setId(property.getId());
                col.setValue(getPartNumber());
            }
            else if(property.getName().equals("part_name"))
            {
                col.setId(property.getId());
                col.setValue(getPartName());
            }
            else if(property.getName().equals("use_quantity"))
            {
                col.setId(property.getId());
                col.setValue(Integer.toString(getUseQuantity()));
            }
            else if(property.getName().equals("splited"))
            {
                col.setId(property.getId());
                col.setValue(getSplited());
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
