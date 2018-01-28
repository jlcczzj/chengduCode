/**
 * ���ɳ���QMXMLProcessPart.java	1.0              2007-10-30
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.jferp.exception.QMXMLException;

/**
 * <p>Title:���չ�̹������㲿��,�㲿����תΪ���Ϻš�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class QMXMLProcessPart extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(QMXMLProcessPart.class);

    //���Ϻ�
    private String matNumber;

    //���Ŵ���(����·�ߴ���)
    private String routeCode;

    //�㲿���汾
    private String partVersion;

    //�㲿�����
    private String partNumber;

    //�㲿������
    private String partName;

    //�㲿���Ƿ��Ѳ��Ϊ����
    private String splited;

    //�㲿���Ĺ��̴���
    private String processCode;

    //�㲿������Ҫ�����ʶ
    private boolean mainPartFlag;

    //�㲿������Ҫ���ձ�ʶ
    private boolean mainProcessFlag;

    /**
     * ȱʡ���캯����
     */
    public QMXMLProcessPart()
    {
        super();
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
     * ��ȡ���Ϻš�
     * @return
     */
    public String getMatNumber()
    {
        return matNumber;
    }

    /**
     * �������Ϻš�
     * @param matNumber
     */
    public void setMatNumber(String matNumber)
    {
        this.matNumber = matNumber;
    }

    /**
     * ��ȡ�㲿�����ơ�
     * @return
     */
    public String getPartName()
    {
        return partName;
    }

    /**
     * �����㲿�����ơ�
     * @param partName
     */
    public void setPartName(String partName)
    {
        this.partName = partName;
    }

    /**
     * ��ȡ�㲿����š�
     * @return
     */
    public String getPartNumber()
    {
        return partNumber;
    }

    /**
     * �����㲿����š�
     * @param partNumber
     */
    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
    }

    /**
     * ��ȡ�㲿���汾��
     * @return
     */
    public String getPartVersion()
    {
        return partVersion;
    }

    /**
     * �����㲿���汾��
     * @param partVersion
     */
    public void setPartVersion(String partVersion)
    {
        this.partVersion = partVersion;
    }

    /**
     * ��ȡ���Ŵ��롣
     * @return
     */
    public String getRouteCode()
    {
        return routeCode;
    }

    /**
     * ���ò��Ŵ��롣
     * @param routeCode
     */
    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }

    /**
     * ��ȡ�㲿���Ƿ��ֵı�ǣ��Ѳ��Ϊ��1����δ���Ϊ��0����
     * @return
     */
    public String getSplited()
    {
        return splited;
    }

    /**
     * �����㲿���Ƿ��ֵı�ǣ��Ѳ��Ϊ��1����δ���Ϊ��0����
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
     * ��ȡ��Ҫ�㲿����ʶ��
     * @return ���� mainPartFlag��
     */
    public boolean getMainPartFlag()
    {
        return mainPartFlag;
    }

    /**
     * ������Ҫ�㲿����ʶ��
     * @param mainPartFlag Ҫ���õ� mainPartFlag��
     */
    public void setMainPartFlag(boolean mainPartFlag)
    {
        this.mainPartFlag = mainPartFlag;
    }

    /**
     * ��ȡ��Ҫ���ձ�ʶ��
     * @return ���� mainProcessFlag��
     */
    public boolean getMainProcessFlag()
    {
        return mainProcessFlag;
    }

    /**
     * ������Ҫ���ձ�ʶ��
     * @param mainProcessFlag Ҫ���õ� mainProcessFlag��
     */
    public void setMainProcessFlag(boolean mainProcessFlag)
    {
        this.mainProcessFlag = mainProcessFlag;
    }

    /**
     * ��ȡ���̴��롣
     * @return ���� processCode��
     */
    public String getProcessCode()
    {
        return processCode;
    }

    /**
     * ���ù��̴��롣
     * @param processCode Ҫ���õ� processCode��
     */
    public void setProcessCode(String processCode)
    {
        this.processCode = processCode;
    }
}
