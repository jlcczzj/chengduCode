/**
 * 生成程序QMXMLProcessPart.java	1.0              2007-10-30
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.jferp.exception.QMXMLException;

/**
 * <p>Title:工艺规程关联的零部件,零部件号转为物料号。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class QMXMLProcessPart extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(QMXMLProcessPart.class);

    //物料号
    private String matNumber;

    //部门代码(采用路线代码)
    private String routeCode;

    //零部件版本
    private String partVersion;

    //零部件编号
    private String partNumber;

    //零部件名称
    private String partName;

    //零部件是否已拆分为物料
    private String splited;

    //零部件的过程代码
    private String processCode;

    //零部件的主要零件标识
    private boolean mainPartFlag;

    //零部件的主要工艺标识
    private boolean mainProcessFlag;

    /**
     * 缺省构造函数。
     */
    public QMXMLProcessPart()
    {
        super();
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
        final List basicPropList = new ArrayList(6);
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
            if(property.getName().equals("material_number"))
            {
                col.setId(property.getId());
                col.setValue(getMatNumber());
            }
            else if(property.getName().equals("route_code"))
            {
                col.setId(property.getId());
                col.setValue(getRouteCode());
            }
            else if(property.getName().equals("part_version"))
            {
                col.setId(property.getId());
                col.setValue(getPartVersion());
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
            else if(property.getName().equals("splited"))
            {
                col.setId(property.getId());
                col.setValue(getSplited());
            }
            else if(property.getName().equals("process_code"))
            {
                col.setId(property.getId());
                col.setValue(getProcessCode());
            }
            else if(property.getName().equals("main_part"))
            {
                col.setId(property.getId());
                col.setValue(Boolean.toString(getMainPartFlag()));
            }
            else if(property.getName().equals("main_process"))
            {
                col.setId(property.getId());
                col.setValue(Boolean.toString(getMainProcessFlag()));
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

    /**
     * 获取物料号。
     * @return
     */
    public String getMatNumber()
    {
        return matNumber;
    }

    /**
     * 设置物料号。
     * @param matNumber
     */
    public void setMatNumber(String matNumber)
    {
        this.matNumber = matNumber;
    }

    /**
     * 获取零部件名称。
     * @return
     */
    public String getPartName()
    {
        return partName;
    }

    /**
     * 设置零部件名称。
     * @param partName
     */
    public void setPartName(String partName)
    {
        this.partName = partName;
    }

    /**
     * 获取零部件编号。
     * @return
     */
    public String getPartNumber()
    {
        return partNumber;
    }

    /**
     * 设置零部件编号。
     * @param partNumber
     */
    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
    }

    /**
     * 获取零部件版本。
     * @return
     */
    public String getPartVersion()
    {
        return partVersion;
    }

    /**
     * 设置零部件版本。
     * @param partVersion
     */
    public void setPartVersion(String partVersion)
    {
        this.partVersion = partVersion;
    }

    /**
     * 获取部门代码。
     * @return
     */
    public String getRouteCode()
    {
        return routeCode;
    }

    /**
     * 设置部门代码。
     * @param routeCode
     */
    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }

    /**
     * 获取零部件是否拆分的标记：已拆分为“1”，未拆分为“0”。
     * @return
     */
    public String getSplited()
    {
        return splited;
    }

    /**
     * 设置零部件是否拆分的标记：已拆分为“1”，未拆分为“0”。
     * @param splited
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
     * 获取主要零部件标识。
     * @return 返回 mainPartFlag。
     */
    public boolean getMainPartFlag()
    {
        return mainPartFlag;
    }

    /**
     * 设置主要零部件标识。
     * @param mainPartFlag 要设置的 mainPartFlag。
     */
    public void setMainPartFlag(boolean mainPartFlag)
    {
        this.mainPartFlag = mainPartFlag;
    }

    /**
     * 获取主要工艺标识。
     * @return 返回 mainProcessFlag。
     */
    public boolean getMainProcessFlag()
    {
        return mainProcessFlag;
    }

    /**
     * 设置主要工艺标识。
     * @param mainProcessFlag 要设置的 mainProcessFlag。
     */
    public void setMainProcessFlag(boolean mainProcessFlag)
    {
        this.mainProcessFlag = mainProcessFlag;
    }

    /**
     * 获取过程代码。
     * @return 返回 processCode。
     */
    public String getProcessCode()
    {
        return processCode;
    }

    /**
     * 设置过程代码。
     * @param processCode 要设置的 processCode。
     */
    public void setProcessCode(String processCode)
    {
        this.processCode = processCode;
    }
}
