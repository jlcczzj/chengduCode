/**
 * 生成程序QMXMLStructure.java	1.0              2006-10-27
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.faw_qm.cderp.exception.QMXMLException;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title: 属性配置文件中名称为“structure”的“data”XML元素。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public final class QMXMLStructure extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLStructure.class);

    private PartUsageLinkIfc usageLinkIfc;

    private String structure_publish_type = "";

    /**
     * 缺省构造函数。
     */
    public QMXMLStructure()
    {
        super();
    }

    /**
     * 构造函数。带一个参数。
     * @param usageLinkIfc 零部件关联类。
     */
    public QMXMLStructure(final PartUsageLinkIfc usageLinkIfc)
    {
        this.usageLinkIfc = usageLinkIfc;
    }

    public final void setStructure_publish_type(final String type)
    {
        this.structure_publish_type = type;
    }

    private final String getStructure_publish_type()
    {
        return structure_publish_type;
    }

    /**
     * @return
     */
    private final String getOptionFlag()
    {
        return String.valueOf(usageLinkIfc.getOptionFlag());
    }

    /**
     * @return
     */
    private final String getDefaultUnitStr()
    {
        return usageLinkIfc.getDefaultUnit().getDisplay();
    }

    /**
     * @return
     */
    private final String getQuantity()
    {
        String quantityStr = Float.toString(usageLinkIfc.getQuantity());
        if(quantityStr.endsWith(".0"))
        {
            quantityStr = quantityStr.substring(0, quantityStr.length() - 2);
        }
        return quantityStr;
    }

    /**
     * 获取子零部件编号。
     * @return String 子零部件编号。
     * @throws QMXMLException 
     */
    private final String getChildPart() throws QMXMLException
    {
        final String parentBsoID = usageLinkIfc.getLeftBsoID();
        QMPartMasterIfc partMaster = null;
        try
        {
        	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        	partMaster=(QMPartMasterIfc)pservice.refreshInfo(parentBsoID);
            if(logger.isDebugEnabled())
            {
                logger.debug("ChildPart==" + partMaster.getPartNumber());
            }
        }
        catch (QMException e)
        {
            //"获取子零部件时出错！"
            logger.error(Messages.getString("Util.13") + e);
            throw new QMXMLException(e);
        }
        return partMaster.getPartNumber();
    }

    /**
     * 获取父零部件编号。
     * @return String 父零部件编号。
     * @throws QMXMLException 
     */
    private final String getParentPart() throws QMXMLException
    {
        final String childBsoID = usageLinkIfc.getRightBsoID();
        QMPartIfc part = null;
        try
        {
        	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        	part=(QMPartIfc)pservice.refreshInfo(childBsoID);
            if(logger.isDebugEnabled())
            {
                logger.debug("ParentPart==" + part.getPartNumber());
            }
        }
        catch (QMException e)
        {
            //"获取父零部件时出错！"
            logger.error(Messages.getString("Util.14") + e);
            throw new QMXMLException(e);
        }
        return part.getPartNumber();
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
        final List basicPropList = new ArrayList(10);
        for (int i = 0; i < propertyList.size(); i++)
        {
            property = (QMXMLProperty) propertyList.get(i);
            if(property.getType().equals("extension"))
                extensionPropList.add(property);
            else if(property.getType().equals("basic"))
                basicPropList.add(property);
        }
        for (int i = 0; i < extensionPropList.size(); i++)
        {
            property = (QMXMLProperty) extensionPropList.get(i);
            col = new QMXMLColumn();
            if(property.getName().equals("structure_publish_type"))
            {
                col.setId(property.getId());
                col.setValue(getStructure_publish_type());
            }
            colList.add(col);
        }
        for (int i = 0; i < basicPropList.size(); i++)
        {
            property = (QMXMLProperty) basicPropList.get(i);
            col = new QMXMLColumn();
            if(property.getName().equals("rightBsoID"))
            {
                col.setId(property.getId());
                col.setValue(getParentPart());
            }
            else if(property.getName().equals("leftBsoID"))
            {
                col.setId(property.getId());
                col.setValue(getChildPart());
            }
            else if(property.getName().equals("quantity"))
            {
                col.setId(property.getId());
                col.setValue(getQuantity());
            }
            else if(property.getName().equals("defaultUnitStr"))
            {
                col.setId(property.getId());
                col.setValue(getDefaultUnitStr());
            }
            else if(property.getName().equals("optionFlag"))
            {
                col.setId(property.getId());
                col.setValue(getOptionFlag());
            }
            colList.add(col);
        }
        record.setColList(colList);
        if(logger.isDebugEnabled())
        {
            logger.debug("getRecord() - end"); //$NON-NLS-1$
        }
        return record;
    }
}
