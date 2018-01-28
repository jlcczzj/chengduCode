/**
 * 生成程序QMXMLProcess.java	1.0              2007-10-30
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.jferp.exception.QMXMLException;

/**
 * <p>Title: 工艺规程信息。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class QMXMLProcess extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLProcess.class);

    //工序号
    private int stepSerNumber;

    //工序编号
    private String stepNumber;

    //工序名称
    private String stepName;

    //部门代码
    private String routeCode;

    //工序种类
    private String stepCategory;

    //工序类别
    private String importantLevel;

    //加工类型
    private String stepType;

    //工时定额
    private double timeRation;

    //加工工时
    private double cureTime;

    //辅助工时
    private double assistant;

    //工作中心代码
    private String workCenter;

    //定员
    private String operatorNum;

    /**
     * 获取辅助工时。
     * @return 返回 assistant。
     */
    public double getAssistant()
    {
        return assistant;
    }

    /**
     * 设置辅助工时。
     * @param assistant 要设置的 assistant。
     */
    public void setAssistant(double assistant)
    {
        this.assistant = assistant;
    }

    /**
     * 获取加工工时。
     * @return 返回 cureTime。
     */
    public double getCureTime()
    {
        return cureTime;
    }

    /**
     * 设置加工工时。
     * @param cureTime 要设置的 cureTime。
     */
    public void setCureTime(double cureTime)
    {
        this.cureTime = cureTime;
    }

    /**
     * 获取工序类别。
     * @return 返回 importantLevel。
     */
    public String getImportantLevel()
    {
        return importantLevel;
    }

    /**
     * 设置工序类别。
     * @param importantLevel 要设置的 importantLevel。
     */
    public void setImportantLevel(String importantLevel)
    {
        this.importantLevel = importantLevel;
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
     * 获取工序种类。
     * @return 返回 stepCategory。
     */
    public String getStepCategory()
    {
        return stepCategory;
    }

    /**
     * 设置工序种类。
     * @param stepCategory 要设置的 stepCategory。
     */
    public void setStepCategory(String stepCategory)
    {
        this.stepCategory = stepCategory;
    }

    /**
     * 获取工序名称。
     * @return 返回 stepName。
     */
    public String getStepName()
    {
        return stepName;
    }

    /**
     * 设置工序名称。
     * @param stepName 要设置的 stepName。
     */
    public void setStepName(String stepName)
    {
        this.stepName = stepName;
    }

    /**
     * 获取工序编号。
     * @return 返回 stepNumber。
     */
    public String getStepNumber()
    {
        return stepNumber;
    }

    /**
     * 设置工序编号。
     * @param stepNumber 要设置的 stepNumber。
     */
    public void setStepNumber(String stepNumber)
    {
        this.stepNumber = stepNumber;
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
     * 获取加工类型。
     * @return 返回 stepType。
     */
    public String getStepType()
    {
        return stepType;
    }

    /**
     * 设置加工类型。
     * @param stepType 要设置的 stepType。
     */
    public void setStepType(String stepType)
    {
        this.stepType = stepType;
    }

    /**
     * 获取工时定额。
     * @return 返回 timeRation。
     */
    public double getTimeRation()
    {
        return timeRation;
    }

    /**
     * 设置工时定额。
     * @param timeRation 要设置的 timeRation。
     */
    public void setTimeRation(double timeRation)
    {
        this.timeRation = timeRation;
    }

    /**
     * 获取工作中心代码。
     * @return 返回 workCenter。
     */
    public String getWorkCenter()
    {
        return workCenter;
    }

    /**
     * 设置工作中心代码。
     * @param workCenter 要设置的 workCenter。
     */
    public void setWorkCenter(String workCenter)
    {
        this.workCenter = workCenter;
    }

    /**
     * 获取定员。
     * @return 返回 operatorNum。
     */
    public String getOperatorNum()
    {
        return operatorNum;
    }
    /**
     * 获取定员。
     * @return 返回 operatorNum的数值。
     */
    public long getOperatorNumLong()
    {
        NumberFormat nf = NumberFormat.getInstance();
        long operNum = 1L;
        try
        {
            Number number = nf.parse(getOperatorNum());
            operNum = number.longValue();
        }
        catch (ParseException e)
        {
            final SimpleDateFormat simple = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss ");
            //"定员的数据定义格式有误！"
            logger.debug(simple.format(new Date())
                    + Messages.getString("Util.85"), e); //$NON-NLS-1$
        }
        return operNum;
    }
    /**
     * 设置定员。
     * @param operatorNum 要设置的 operatorNum。
     */
    public void setOperatorNum(String operatorNum)
    {
        this.operatorNum = operatorNum;
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
        final List basicPropList = new ArrayList(11);
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
            else if(property.getName().equals("step_number"))
            {
                col.setId(property.getId());
                col.setValue(getStepNumber());
            }
            else if(property.getName().equals("step_name"))
            {
                col.setId(property.getId());
                col.setValue(getStepName());
            }
            else if(property.getName().equals("route_code"))
            {
                col.setId(property.getId());
                col.setValue(getRouteCode());
            }
            else if(property.getName().equals("step_category"))
            {
                col.setId(property.getId());
                col.setValue(getStepCategory());
            }
            else if(property.getName().equals("important_level"))
            {
                col.setId(property.getId());
                col.setValue(getImportantLevel());
            }
            else if(property.getName().equals("step_type"))
            {
                col.setId(property.getId());
                col.setValue(getStepType());
            }
            else if(property.getName().equals("time_ration"))
            {
                col.setId(property.getId());
                //20080303 begin
                //col.setValue(Double
                //        .toString(getTimeRation() * getOperatorNumLong()));
                col.setValue(Float
                        .toString((float)getTimeRation() * getOperatorNumLong()));
                //20080303 end
            }
            else if(property.getName().equals("cure_time"))
            {
                col.setId(property.getId());
                col.setValue(Double.toString(getCureTime()));
            }
            else if(property.getName().equals("assistant"))
            {
                col.setId(property.getId());
                col.setValue(Double.toString(getAssistant()));
            }
            else if(property.getName().equals("workCenter"))
            {
                col.setId(property.getId());
                col.setValue(getWorkCenter());
            }
            colList.add(col);
        }
        for (int i = 0; i < ibaPropList.size(); i++)
        {
            property = (QMXMLProperty) ibaPropList.get(i);
            col = new QMXMLColumn();
            if(property.getName().equals("operator_num"))
            {
                col.setId(property.getId());
                col.setValue(Long.toString(getOperatorNumLong()));
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
