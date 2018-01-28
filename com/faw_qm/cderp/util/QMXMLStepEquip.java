/**
 * ���ɳ���QMXMLStepEqui.java	1.0              2007-10-30
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
 * <p>Title: ����������豸��Ϣ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class QMXMLStepEquip extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLStepEquip.class);

    /**
     * ȱʡ���캯����
     */
    public QMXMLStepEquip()
    {
        super();
    }

    //�����
    private int stepSerNumber;

    //���Ŵ���
    private String routeCode;

    //�豸���
    private String equipNumber;

    //�豸����
    private String equipName;

    //�豸�ͺ�
    private String modelNumber;

    //ƽ��ͼ��
    private String posNumber;

    //ʹ������
    private int useQuantity;

    /**
     * ��ȡ�豸���ơ�
     * @return ���� equiName��
     */
    public String getEquipName()
    {
        return equipName;
    }

    /**
     * �����豸���ơ�
     * @param equiName Ҫ���õ� equiName��
     */
    public void setEquipName(String equipName)
    {
        this.equipName = equipName;
    }

    /**
     * ��ȡ�豸��š�
     * @return ���� equiNumber��
     */
    public String getEquipNumber()
    {
        return equipNumber;
    }

    /**
     * �����豸��š�
     * @param equiNumber Ҫ���õ� equiNumber��
     */
    public void setEquipNumber(String equipNumber)
    {
        this.equipNumber = equipNumber;
    }

    /**
     * ��ȡ�豸�ͺš�
     * @return ���� modelNumber��
     */
    public String getModelNumber()
    {
        return modelNumber;
    }

    /**
     * �����豸�ͺš�
     * @param modelNumber Ҫ���õ� modelNumber��
     */
    public void setModelNumber(String modelNumber)
    {
        this.modelNumber = modelNumber;
    }

    /**
     * ��ȡƽ��ͼ�š�
     * @return ���� posNumber��
     */
    public String getPosNumber()
    {
        return posNumber;
    }

    /**
     * ����ƽ��ͼ�š�
     * @param posNumber Ҫ���õ� posNumber��
     */
    public void setPosNumber(String posNumber)
    {
        this.posNumber = posNumber;
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
