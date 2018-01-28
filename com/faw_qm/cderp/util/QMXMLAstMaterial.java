/**
 * 生成程序QMXMLAstMaterial.java	1.0              2007-10-30
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
 * <p>Title: 工序关联的辅助材料信息。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class QMXMLAstMaterial extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(QMXMLAstMaterial.class);

    /**
     * 缺省构造函数。
     */
    public QMXMLAstMaterial()
    {
        super();
    }

    //工序号
    private int stepSerNumber;

    //部门代码,用路线代码
    private String routeCode;

    //材料编号
    private String matNumber;

    //材料名称
    private String matName;

    //材料牌号
    private String matBrand;

    //材料规格
    private String matSpecs;

    //定额
    private float ration;

    //单位
    private String unit;
    
    //材料状态
    private String materialFunction;
    
    //材料标准
    private String materialCrision;

    /**
     * 获取部门代码。
     * @return 返回 mainCode。
     */
    public String getRouteCode()
    {
        return routeCode;
    }

    /**
     * 设置部门代码。
     * @param mainCode 要设置的 mainCode。
     */
    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }

    /**
     * 获取材料牌号。
     * @return 返回 matBrand。
     */
    public String getMatBrand()
    {
        return matBrand;
    }

    /**
     * 设置材料牌号。
     * @param matBrand 要设置的 matBrand。
     */
    public void setMatBrand(String matBrand)
    {
        this.matBrand = matBrand;
    }

    /**
     * 获取材料名称。
     * @return 返回 matName。
     */
    public String getMatName()
    {
        return matName;
    }

    /**
     * 设置材料名称。
     * @param matName 要设置的 matName。
     */
    public void setMatName(String matName)
    {
        this.matName = matName;
    }

    /**
     * 获取材料编号。
     * @return 返回 matNumber。
     */
    public String getMatNumber()
    {
        return matNumber;
    }

    /**
     * 设置材料编号。
     * @param matNumber 要设置的 matNumber。
     */
    public void setMatNumber(String matNumber)
    {
        this.matNumber = matNumber;
    }

    /**
     * 获取材料规格。
     * @return 返回 matSpecs。
     */
    public String getMatSpecs()
    {
        return matSpecs;
    }

    /**
     * 设置材料规格。
     * @param matSpecs 要设置的 matSpecs。
     */
    public void setMatSpecs(String matSpecs)
    {
        this.matSpecs = matSpecs;
    }

    /**
     * 获取定额。
     * @return 返回 ration。
     */
    public float getRation()
    {
        return ration;
    }

    /**
     * 设置定额。
     * @param ration 要设置的 ration。
     */
    public void setRation(float ration)
    {
        this.ration = ration;
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
     * 获取单位。
     * @return 返回 unit。
     */
    public String getUnit()
    {
        return unit;
    }

    /**
     * 设置单位。
     * @param unit 要设置的 unit。
     */
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    
    /**
     * 获取材料状态。
     * @return 返回 materialFunction。
     */
	public String getMaterialFunction() {
		return materialFunction;
	}

	/**
	 * 设置材料状态。
	 * @param materialFunction 要设置的 materialFunction。
	 */
	public void setMaterialFunction(String materialFunction) {
		this.materialFunction = materialFunction;
	}
	
	/**
	 * 获取材料标准。
	 * @return 返回 materialCrision
	 */
	public String getMaterialCrision() {
		return materialCrision;
	}

	/**
	 * 设置材料标准。
	 * @param materialCrision 要设置的 materialCrision
	 */
	public void setMaterialCrision(String materialCrision) {
		this.materialCrision = materialCrision;
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
        final List basicPropList = new ArrayList(8);
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
            else if(property.getName().equals("mat_number"))
            {
                col.setId(property.getId());
                col.setValue(getMatNumber());
            }
            else if(property.getName().equals("mat_name"))
            {
                col.setId(property.getId());
                col.setValue(getMatName());
            }
            else if(property.getName().equals("mat_brand"))
            {
                col.setId(property.getId());
                col.setValue(getMatBrand());
            }
            else if(property.getName().equals("mat_specs"))
            {
                col.setId(property.getId());
                col.setValue(getMatSpecs());
            }
            else if(property.getName().equals("ration"))
            {
                col.setId(property.getId());
                col.setValue(MaterialServiceHelper.sicenToComm(getRation()));
            }
            else if(property.getName().equals("unit"))
            {
                col.setId(property.getId());
                col.setValue(getUnit());
            }
            else if(property.getName().equals("mat_Fun"))
            {
                col.setId(property.getId());
                col.setValue(getMaterialFunction());
            }
            else if(property.getName().equals("mat_Cri"))
            {
                col.setId(property.getId());
                col.setValue(getMaterialCrision());
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
