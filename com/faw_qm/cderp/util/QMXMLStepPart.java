/**
 * ���ɳ���QMXMLStepPart.java	1.0              2007-10-30
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.faw_qm.cderp.exception.QMXMLException;

/**
 * <p>Title: ����������Ӽ���Ϣ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class QMXMLStepPart extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLStepPart.class);

    //�����
    private int stepSerNumber;

    //���Ϻ�
    private String matNumber;

    //���Ŵ���(����·�ߴ���)
    private String routeCode;

    //�㲿�����
    private String partNumber;

    //�㲿������
    private String partName;

    //ʹ������
    private int useQuantity;

    //�㲿���Ƿ��Ѳ��Ϊ����
    private String splited;

    /**
     * ȱʡ���캯����
     */
    public QMXMLStepPart()
    {
        super();
    }

    /**
     * ��ȡ���Ϻš�
     * @return ���� matNumber��
     */
    public String getMatNumber()
    {
        return matNumber;
    }

    /**
     * �������Ϻš�
     * @param matNumber Ҫ���õ� matNumber��
     */
    public void setMatNumber(String matNumber)
    {
        this.matNumber = matNumber;
    }

    /**
     * ��ȡ�㲿�����ơ�
     * @return ���� partName��
     */
    public String getPartName()
    {
        return partName;
    }

    /**
     * �����㲿�����ơ�
     * @param partName Ҫ���õ� partName��
     */
    public void setPartName(String partName)
    {
        this.partName = partName;
    }

    /**
     * ��ȡ�㲿����š�
     * @return ���� partNumber��
     */
    public String getPartNumber()
    {
        return partNumber;
    }

    /**
     * �����㲿����š�
     * @param partNumber Ҫ���õ� partNumber��
     */
    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
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
     * ��ȡ�㲿���Ƿ��ֵı�ǣ��Ѳ��Ϊ��1����δ���Ϊ��0����
     * @return ���� splited��
     */
    public String getSplited()
    {
        return splited;
    }

    /**
     * �����㲿���Ƿ��ֵı�ǣ��Ѳ��Ϊ��1����δ���Ϊ��0����
     * @param splited Ҫ���õ� splited��
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
