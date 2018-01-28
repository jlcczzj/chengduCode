/**
 * ���ɳ���QMXMLAstMaterial.java	1.0              2007-10-30
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
 * <p>Title: ��������ĸ���������Ϣ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
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
     * ȱʡ���캯����
     */
    public QMXMLAstMaterial()
    {
        super();
    }

    //�����
    private int stepSerNumber;

    //���Ŵ���,��·�ߴ���
    private String routeCode;

    //���ϱ��
    private String matNumber;

    //��������
    private String matName;

    //�����ƺ�
    private String matBrand;

    //���Ϲ��
    private String matSpecs;

    //����
    private float ration;

    //��λ
    private String unit;
    
    //����״̬
    private String materialFunction;
    
    //���ϱ�׼
    private String materialCrision;

    /**
     * ��ȡ���Ŵ��롣
     * @return ���� mainCode��
     */
    public String getRouteCode()
    {
        return routeCode;
    }

    /**
     * ���ò��Ŵ��롣
     * @param mainCode Ҫ���õ� mainCode��
     */
    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }

    /**
     * ��ȡ�����ƺš�
     * @return ���� matBrand��
     */
    public String getMatBrand()
    {
        return matBrand;
    }

    /**
     * ���ò����ƺš�
     * @param matBrand Ҫ���õ� matBrand��
     */
    public void setMatBrand(String matBrand)
    {
        this.matBrand = matBrand;
    }

    /**
     * ��ȡ�������ơ�
     * @return ���� matName��
     */
    public String getMatName()
    {
        return matName;
    }

    /**
     * ���ò������ơ�
     * @param matName Ҫ���õ� matName��
     */
    public void setMatName(String matName)
    {
        this.matName = matName;
    }

    /**
     * ��ȡ���ϱ�š�
     * @return ���� matNumber��
     */
    public String getMatNumber()
    {
        return matNumber;
    }

    /**
     * ���ò��ϱ�š�
     * @param matNumber Ҫ���õ� matNumber��
     */
    public void setMatNumber(String matNumber)
    {
        this.matNumber = matNumber;
    }

    /**
     * ��ȡ���Ϲ��
     * @return ���� matSpecs��
     */
    public String getMatSpecs()
    {
        return matSpecs;
    }

    /**
     * ���ò��Ϲ��
     * @param matSpecs Ҫ���õ� matSpecs��
     */
    public void setMatSpecs(String matSpecs)
    {
        this.matSpecs = matSpecs;
    }

    /**
     * ��ȡ���
     * @return ���� ration��
     */
    public float getRation()
    {
        return ration;
    }

    /**
     * ���ö��
     * @param ration Ҫ���õ� ration��
     */
    public void setRation(float ration)
    {
        this.ration = ration;
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
     * ��ȡ��λ��
     * @return ���� unit��
     */
    public String getUnit()
    {
        return unit;
    }

    /**
     * ���õ�λ��
     * @param unit Ҫ���õ� unit��
     */
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    
    /**
     * ��ȡ����״̬��
     * @return ���� materialFunction��
     */
	public String getMaterialFunction() {
		return materialFunction;
	}

	/**
	 * ���ò���״̬��
	 * @param materialFunction Ҫ���õ� materialFunction��
	 */
	public void setMaterialFunction(String materialFunction) {
		this.materialFunction = materialFunction;
	}
	
	/**
	 * ��ȡ���ϱ�׼��
	 * @return ���� materialCrision
	 */
	public String getMaterialCrision() {
		return materialCrision;
	}

	/**
	 * ���ò��ϱ�׼��
	 * @param materialCrision Ҫ���õ� materialCrision
	 */
	public void setMaterialCrision(String materialCrision) {
		this.materialCrision = materialCrision;
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
