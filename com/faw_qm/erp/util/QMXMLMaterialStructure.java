/**
 * 生成程序QMXMLMaterialStructure.java	1.0              2007-9-28
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.erp.exception.QMXMLException;
import com.faw_qm.erp.model.MaterialStructureIfc;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class QMXMLMaterialStructure extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(QMXMLMaterialSplit.class);

    private MaterialStructureIfc materialStructure;

    private String publishType = "";

    private String parentPartVersionId = "";

    /**
     * 缺省构造函数。
     */
    public QMXMLMaterialStructure()
    {
        super();
    }

    /**
     * 构造函数。带一个参数。
     * @param part 零部件。
     */
    public QMXMLMaterialStructure(final MaterialStructureIfc materialStructure)
    {
        this.materialStructure = materialStructure;
        parentPartVersionId = materialStructure.getParentPartVersion() == null ? ""
                : materialStructure.getParentPartVersion();
    }

    /**
     * 获取父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @return 父件号。
     */
    public final String getParentPartNumber()
    {
        return materialStructure.getParentPartNumber() == null ? ""
                : materialStructure.getParentPartNumber();
    }

    /**
     * 获取父件版本，父物料拆分前零件版本，用于检查结构是否更改。
     * @return 父件版本。
     */
    private final String getParentPartVersion()
    {
        return parentPartVersionId == null ? "" : parentPartVersionId;
    }

    /**
     * 设置父件版本，父物料拆分前零件版本，用于检查结构是否更改。
     * @param partVersionId 父件版本。
     */
    public final void setParentPartVersion(String parentPartVersionId)
    {
        this.parentPartVersionId = parentPartVersionId;
    }

    /**
     * 获取父物料，记录拆分后的物料父项号。
     * @return 父物料。
     */
    private final String getParentNumber()
    {
        return materialStructure.getParentNumber() == null ? ""
                : materialStructure.getParentNumber();
    }

    /**
     * 获取子物料，记录拆分后的物料子项号。
     * @return 子物料。
     */
    private final String getChildNumber()
    {
        return materialStructure.getChildNumber() == null ? ""
                : materialStructure.getChildNumber();
    }

    /**
     * 获取数量，子物料在父项中的使用数量，零件的使用关系中的数量，物料拆分数量为“1”。
     * @return 数量。
     */
    private final String getQuantityStr()
    {
        String quantity=Float.toString(materialStructure.getQuantity());
        if(quantity.endsWith(".0"))
        {
            quantity = quantity.substring(0, quantity.length() - 2);
        }
        return quantity;
    }

    /**
     * 获取层级，零件拆分为物料的层级号，从0开始。
     * @return 层级。
     */
    private final String getLevelNumber()
    {
        return materialStructure.getLevelNumber() == null ? ""
                : materialStructure.getLevelNumber();
    }

    /**
     * 获取使用单位，枚举类型，包括：个、按照所需、千克、米、升。
     * @return 使用单位。
     */
    private final String getDefaultUnit()
    {
        return materialStructure.getDefaultUnit() == null ? ""
                : "件";//默认值要求是件 刘家坤 20140221
    }

    /**
     * 获取选装标识，1位数字：0为否、1为是。
     * @return 选装标识。
     */
    private final String getOptionFlagStr()
    {
        return String.valueOf(materialStructure.getOptionFlag()) == null ? ""
                : String.valueOf(materialStructure.getOptionFlag());
    }

    /**
     * 设置设置更改标记，结构是否更改(N、D、U、O) ：新增N、延用O、更改U、取消D。
     * @param publishType 更改标记。
     */
    public final void setStructurePublishType(String publishType)
    {
        this.publishType = publishType;
    }

    /**
     * 获取更改标记，结构是否更改(N、D、U、O) ：新增N、延用O、更改U、取消D。
     * @return 更改标记。
     */
    public final String getStructurePublishType()
    {
        return publishType == null ? "" : publishType;
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
        final List colList = new ArrayList();
        final List basicPropList = new ArrayList(9);
        final List propertyList = getPropertyList();
        QMXMLProperty property = null;
        QMXMLColumn col = null;
        for (int i = 0; i < propertyList.size(); i++)
        {
            property = (QMXMLProperty) propertyList.get(i);
            if(property.getType().equals("basic"))
                basicPropList.add(property);
        }
        for (int i = 0; i < basicPropList.size(); i++)
        {
            property = (QMXMLProperty) basicPropList.get(i);
            col = new QMXMLColumn();
            if(property.getName().equals("structure_publish_type"))
            {
                col.setId(property.getId());
                col.setValue(getStructurePublishType());
            }
            else if(property.getName().equals("parent_num"))
            {
                col.setId(property.getId());
                col.setValue(getParentNumber());
            }
            else if(property.getName().equals("child_num"))
            {
                col.setId(property.getId());
                col.setValue(getChildNumber());
            }
            else if(property.getName().equals("use_quantity"))
            {
                col.setId(property.getId());
                col.setValue(getQuantityStr());
            }
            else if(property.getName().equals("use_unit"))
            {
                col.setId(property.getId());
                col.setValue(getDefaultUnit());
            }
            else if(property.getName().equals("parent_part_number"))
            {
                col.setId(property.getId());
                col.setValue(getParentPartNumber()); 
            }
            else if(property.getName().equals("parent_part_version"))
            {
                col.setId(property.getId());
                //                col.setValue(getParentPartVersion().substring(0, 1));
                col.setValue(getParentPartVersion());
            }
            else if(property.getName().equals("mat_level"))
            {
                col.setId(property.getId());
                col.setValue(getLevelNumber());
            }
//            else if(property.getName().equals("option_flag"))
//            {
//                col.setId(property.getId());
//                col.setValue(getOptionFlagStr());
//            }
            colList.add(col);
        }
        record.setColList(colList);
        if(logger.isDebugEnabled())
        {
            logger.debug("getRecord() - end" + record); //$NON-NLS-1$
        }
        return record;
    }

    /**
     * 设置物料结构值对象
     * @return 返回 materialStructure。
     */
    public final MaterialStructureIfc getMaterialStructure()
    {
        return materialStructure;
    }

    /**
     * 获得物料结构值对象
     * @param materialStructure 要设置的 materialStructure。
     */
    public final void setMaterialStructure(
            final MaterialStructureIfc materialStructure)
    {
        this.materialStructure = materialStructure;
    }
}
