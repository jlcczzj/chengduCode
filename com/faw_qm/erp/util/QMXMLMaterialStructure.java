/**
 * ���ɳ���QMXMLMaterialStructure.java	1.0              2007-9-28
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
import com.faw_qm.erp.model.MaterialStructureIfc;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
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
     * ȱʡ���캯����
     */
    public QMXMLMaterialStructure()
    {
        super();
    }

    /**
     * ���캯������һ��������
     * @param part �㲿����
     */
    public QMXMLMaterialStructure(final MaterialStructureIfc materialStructure)
    {
        this.materialStructure = materialStructure;
        parentPartVersionId = materialStructure.getParentPartVersion() == null ? ""
                : materialStructure.getParentPartVersion();
    }

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    public final String getParentPartNumber()
    {
        return materialStructure.getParentPartNumber() == null ? ""
                : materialStructure.getParentPartNumber();
    }

    /**
     * ��ȡ�����汾�������ϲ��ǰ����汾�����ڼ��ṹ�Ƿ���ġ�
     * @return �����汾��
     */
    private final String getParentPartVersion()
    {
        return parentPartVersionId == null ? "" : parentPartVersionId;
    }

    /**
     * ���ø����汾�������ϲ��ǰ����汾�����ڼ��ṹ�Ƿ���ġ�
     * @param partVersionId �����汾��
     */
    public final void setParentPartVersion(String parentPartVersionId)
    {
        this.parentPartVersionId = parentPartVersionId;
    }

    /**
     * ��ȡ�����ϣ���¼��ֺ�����ϸ���š�
     * @return �����ϡ�
     */
    private final String getParentNumber()
    {
        return materialStructure.getParentNumber() == null ? ""
                : materialStructure.getParentNumber();
    }

    /**
     * ��ȡ�����ϣ���¼��ֺ����������š�
     * @return �����ϡ�
     */
    private final String getChildNumber()
    {
        return materialStructure.getChildNumber() == null ? ""
                : materialStructure.getChildNumber();
    }

    /**
     * ��ȡ�������������ڸ����е�ʹ�������������ʹ�ù�ϵ�е����������ϲ������Ϊ��1����
     * @return ������
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
     * ��ȡ�㼶��������Ϊ���ϵĲ㼶�ţ���0��ʼ��
     * @return �㼶��
     */
    private final String getLevelNumber()
    {
        return materialStructure.getLevelNumber() == null ? ""
                : materialStructure.getLevelNumber();
    }

    /**
     * ��ȡʹ�õ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס�����
     * @return ʹ�õ�λ��
     */
    private final String getDefaultUnit()
    {
        return materialStructure.getDefaultUnit() == null ? ""
                : "��";//Ĭ��ֵҪ���Ǽ� ������ 20140221
    }

    /**
     * ��ȡѡװ��ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @return ѡװ��ʶ��
     */
    private final String getOptionFlagStr()
    {
        return String.valueOf(materialStructure.getOptionFlag()) == null ? ""
                : String.valueOf(materialStructure.getOptionFlag());
    }

    /**
     * �������ø��ı�ǣ��ṹ�Ƿ����(N��D��U��O) ������N������O������U��ȡ��D��
     * @param publishType ���ı�ǡ�
     */
    public final void setStructurePublishType(String publishType)
    {
        this.publishType = publishType;
    }

    /**
     * ��ȡ���ı�ǣ��ṹ�Ƿ����(N��D��U��O) ������N������O������U��ȡ��D��
     * @return ���ı�ǡ�
     */
    public final String getStructurePublishType()
    {
        return publishType == null ? "" : publishType;
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
     * �������Ͻṹֵ����
     * @return ���� materialStructure��
     */
    public final MaterialStructureIfc getMaterialStructure()
    {
        return materialStructure;
    }

    /**
     * ������Ͻṹֵ����
     * @param materialStructure Ҫ���õ� materialStructure��
     */
    public final void setMaterialStructure(
            final MaterialStructureIfc materialStructure)
    {
        this.materialStructure = materialStructure;
    }
}
