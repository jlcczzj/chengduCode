/**
 * ���ɳ���QMXMLProcess.java	1.0              2007-10-30
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ���չ����Ϣ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class QMXMLProcess extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLProcess.class);

    //�����
    private int stepSerNumber;

    //������
    private String stepNumber;

    //��������
    private String stepName;

    //���Ŵ���
    private String routeCode;

    //��������
    private String stepCategory;

    //�������
    private String importantLevel;

    //�ӹ�����
    private String stepType;

    //��ʱ����
    private double timeRation;

    //�ӹ���ʱ
    private double cureTime;

    //������ʱ
    private double assistant;

    //�������Ĵ���
    private String workCenter;

    //��Ա
    private String operatorNum;

    /**
     * ��ȡ������ʱ��
     * @return ���� assistant��
     */
    public double getAssistant()
    {
        return assistant;
    }

    /**
     * ���ø�����ʱ��
     * @param assistant Ҫ���õ� assistant��
     */
    public void setAssistant(double assistant)
    {
        this.assistant = assistant;
    }

    /**
     * ��ȡ�ӹ���ʱ��
     * @return ���� cureTime��
     */
    public double getCureTime()
    {
        return cureTime;
    }

    /**
     * ���üӹ���ʱ��
     * @param cureTime Ҫ���õ� cureTime��
     */
    public void setCureTime(double cureTime)
    {
        this.cureTime = cureTime;
    }

    /**
     * ��ȡ�������
     * @return ���� importantLevel��
     */
    public String getImportantLevel()
    {
        return importantLevel;
    }

    /**
     * ���ù������
     * @param importantLevel Ҫ���õ� importantLevel��
     */
    public void setImportantLevel(String importantLevel)
    {
        this.importantLevel = importantLevel;
    }

    /**
     * ��ȡ���Ŵ��롣
     * @return ���� routeCode��
     */
    public String getRouteCode()
    {
        return routeCode;
    }

    /**
     * ���ò��Ŵ��롣
     * @param routeCode Ҫ���õ� routeCode��
     */
    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }

    /**
     * ��ȡ�������ࡣ
     * @return ���� stepCategory��
     */
    public String getStepCategory()
    {
        return stepCategory;
    }

    /**
     * ���ù������ࡣ
     * @param stepCategory Ҫ���õ� stepCategory��
     */
    public void setStepCategory(String stepCategory)
    {
        this.stepCategory = stepCategory;
    }

    /**
     * ��ȡ�������ơ�
     * @return ���� stepName��
     */
    public String getStepName()
    {
        return stepName;
    }

    /**
     * ���ù������ơ�
     * @param stepName Ҫ���õ� stepName��
     */
    public void setStepName(String stepName)
    {
        this.stepName = stepName;
    }

    /**
     * ��ȡ�����š�
     * @return ���� stepNumber��
     */
    public String getStepNumber()
    {
        return stepNumber;
    }

    /**
     * ���ù����š�
     * @param stepNumber Ҫ���õ� stepNumber��
     */
    public void setStepNumber(String stepNumber)
    {
        this.stepNumber = stepNumber;
    }

    /**
     * ��ȡ����š�
     * @return ���� stepSerNumber��
     */
    public int getStepSerNumber()
    {
        return stepSerNumber;
    }

    /**
     * ���ù���š�
     * @param stepSerNumber Ҫ���õ� stepSerNumber��
     */
    public void setStepSerNumber(int stepSerNumber)
    {
        this.stepSerNumber = stepSerNumber;
    }

    /**
     * ��ȡ�ӹ����͡�
     * @return ���� stepType��
     */
    public String getStepType()
    {
        return stepType;
    }

    /**
     * ���üӹ����͡�
     * @param stepType Ҫ���õ� stepType��
     */
    public void setStepType(String stepType)
    {
        this.stepType = stepType;
    }

    /**
     * ��ȡ��ʱ���
     * @return ���� timeRation��
     */
    public double getTimeRation()
    {
        return timeRation;
    }

    /**
     * ���ù�ʱ���
     * @param timeRation Ҫ���õ� timeRation��
     */
    public void setTimeRation(double timeRation)
    {
        this.timeRation = timeRation;
    }

    /**
     * ��ȡ�������Ĵ��롣
     * @return ���� workCenter��
     */
    public String getWorkCenter()
    {
        return workCenter;
    }

    /**
     * ���ù������Ĵ��롣
     * @param workCenter Ҫ���õ� workCenter��
     */
    public void setWorkCenter(String workCenter)
    {
        this.workCenter = workCenter;
    }

    /**
     * ��ȡ��Ա��
     * @return ���� operatorNum��
     */
    public String getOperatorNum()
    {
        return operatorNum;
    }
    /**
     * ��ȡ��Ա��
     * @return ���� operatorNum����ֵ��
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
            //"��Ա�����ݶ����ʽ����"
            logger.debug(simple.format(new Date())
                    + Messages.getString("Util.85"), e); //$NON-NLS-1$
        }
        return operNum;
    }
    /**
     * ���ö�Ա��
     * @param operatorNum Ҫ���õ� operatorNum��
     */
    public void setOperatorNum(String operatorNum)
    {
        this.operatorNum = operatorNum;
    }

    /**
     * ����recordԪ�ء�
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
