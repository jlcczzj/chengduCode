/**
 * ���ɳ���QMXMLStepTool.java	1.0              2007-10-30
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.erp.exception.QMXMLException;

/**
 * <p>Title: ��������Ĺ�װ��Ϣ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class QMXMLStepTool extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLStepTool.class);

    /**
     * ȱʡ���캯����
     */
    public QMXMLStepTool()
    {
        super();
    }

    //    1   step_serial_number  basic   �����
    //    2   route_code  basic   ���Ŵ���
    //    3   tool_number basic   ��װ���
    //    4   tool_name   basic   ��װ����
    //    5   use_quantity    basic   ʹ������
    //�����
    private int stepSerNumber;

    //���Ŵ���
    private String routeCode;

    //��װ���
    private String toolNumber;

    //��װ����
    private String toolName;

    //ʹ������
    private int useQuantity;

    /**
     * ��ȡ��װ���ơ�
     * @return ���� toolName��
     */
    public String getToolName()
    {
        return toolName;
    }

    /**
     * ���ù�װ���ơ�
     * @param toolName Ҫ���õ� toolName��
     */
    public void setToolName(String toolName)
    {
        this.toolName = toolName;
    }

    /**
     * ��ȡ��װ��š�
     * @return ���� toolNumber��
     */
    public String getToolNumber()
    {
        return toolNumber;
    }

    /**
     * ���ù�װ��š�
     * @param toolNumber Ҫ���õ� toolNumber��
     */
    public void setToolNumber(String toolNumber)
    {
        this.toolNumber = toolNumber;
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
     * ��ȡʹ��������
     * @return ���� useQuantity��
     */
    public int getUseQuantity()
    {
        return useQuantity;
    }

    /**
     * ����ʹ��������
     * @param useQuantity Ҫ���õ� useQuantity��
     */
    public void setUseQuantity(int useQuantity)
    {
        this.useQuantity = useQuantity;
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
        final List basicPropList = new ArrayList(5);
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
            else if(property.getName().equals("tool_number"))
            {
                col.setId(property.getId());
                col.setValue(getToolNumber());
            }
            else if(property.getName().equals("tool_name"))
            {
                col.setId(property.getId());
                col.setValue(getToolName());
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
