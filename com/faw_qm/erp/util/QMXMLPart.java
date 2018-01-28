/**
 * ���ɳ���QMXMLPart.java	1.0              2006-10-27
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.erp.exception.QMXMLException;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title: ���������ļ�������Ϊ��part���ġ�data��XMLԪ�ء�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
 * @version 1.0
 */
public final class QMXMLPart extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(QMXMLPart.class);

    private QMPartIfc part;

    private String part_publish_type = "";

    /**
     * ȱʡ���캯����
     */
    public QMXMLPart()
    {
        super();
    }

    /**
     * ���캯������һ��������
     * @param part �㲿����
     */
    public QMXMLPart(final QMPartIfc part)
    {
        this.part = part;
    }

    public final void setPart_publish_type(final String type)
    {
        this.part_publish_type = type;
    }

    public final String getPart_publish_type()
    {
        return part_publish_type == null ? "" : part_publish_type;
    }

    public final String getPartNumber()
    {
        return part.getPartNumber() == null ? "" : part.getPartNumber();
    }

    private final String getPartName()
    {
        return part.getPartName() == null ? "" : part.getPartName();
    }

    /**
     * @return
     */
    public final String getPartLifeCycleState()
    {
        return part.getLifeCycleState().getDisplay() == null ? "" : part
                .getLifeCycleState().getDisplay();
    }

    /**
     * @return
     */
    private final String getPartColorFlag()
    {
        return String.valueOf(part.getColorFlag()) == null ? "" : String
                .valueOf(part.getColorFlag());
    }

    /**
     * @return
     */
    private final String getPartOptionCode()
    {
        return part.getOptionCode() == null ? "" : part.getOptionCode();
    }

    /**
     * @return
     */
    private final String getPartModifyTime()
    {
        return part.getModifyTime().toString() == null ? "" : part
                .getModifyTime().toString();
    }

    /**
     * @return
     */
    private final String getPartTypeStr()
    {
        return part.getPartType().getDisplay() == null ? "" : part
                .getPartType().getDisplay();
    }

    /**
     * @return
     */
    private final String getPartProducedByStr()
    {
        return part.getProducedBy().getDisplay() == null ? "" : part
                .getProducedBy().getDisplay();
    }

    /**
     * @return
     */
    private final String getPartDefaultUnitStr()
    {
        return part.getDefaultUnit().getDisplay() == null ? "" : part
                .getDefaultUnit().getDisplay();
    }

    /**
     * @return
     */
    public final String getPartVesionID()
    {
        return part.getVersionID() == null ? "" : part.getVersionID();
    }

    /**
     * ���ݸ������������Գ�����,�����iba����ֵ��
     * return HashMap ��Ϊiba����ֵ�����Զ�������ƣ�ֵΪiba���Ե�ֵ��
     * @throws QMXMLException 
     */
    private final HashMap getPartIBA() throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIBA() - start"); //$NON-NLS-1$
        }
        final HashMap nameAndValue = new HashMap();
        try
        {
        	IBAValueService ivservice=(IBAValueService)EJBServiceHelper.getService("IBAValueService");
        	part=(QMPartIfc)ivservice.refreshAttributeContainerWithoutConstraints(part);
        }
        catch (QMException e)
        {
            //"ˢ����������ʱ����"
            logger.error(Messages.getString("Util.7"), e); //$NON-NLS-1$
            throw new QMXMLException(e);
        }
        DefaultAttributeContainer container = (DefaultAttributeContainer) part
                .getAttributeContainer();
        if(container == null)
            container = new DefaultAttributeContainer();
        final AbstractValueView[] values = container.getAttributeValues();
        for (int i = 0; i < values.length; i++)
        {
            final AbstractValueView value = values[i];
            final AttributeDefDefaultView definition = value.getDefinition();
            if(value instanceof AbstractContextualValueDefaultView)
            {
                MeasurementSystemCache.setCurrentMeasurementSystem("SI");
                final String measurementSystem = MeasurementSystemCache
                        .getCurrentMeasurementSystem();
                if(value instanceof UnitValueDefaultView)
                {
                    DefaultUnitRenderer defaultunitrenderer = new DefaultUnitRenderer();
                    String ss = "";
                    try
                    {
                        ss = defaultunitrenderer.renderValue(
                                ((UnitValueDefaultView) value).toUnit(),
                                ((UnitValueDefaultView) value)
                                        .getUnitDisplayInfo(measurementSystem));
                    }
                    catch (UnitFormatException e)
                    {
                        //"������λ��ʽ����"
                        logger.error(Messages.getString("Util.8"), e); //$NON-NLS-1$
                        throw new QMXMLException(e);
                    }
                    catch (IncompatibleUnitsException e)
                    {
                        //"���ֲ����ݵ�λ��"
                        logger.error(Messages.getString("Util.9"), e); //$NON-NLS-1$
                        throw new QMXMLException(e);
                    }
                    final String ddd = ((UnitValueDefaultView) value)
                            .getUnitDefinition().getDefaultDisplayUnitString(
                                    measurementSystem);
                    nameAndValue.put(definition.getDisplayName(), ss + ddd);
                }
                else
                {
                    nameAndValue.put(definition.getDisplayName(),
                            ((AbstractContextualValueDefaultView) value)
                                    .getValueAsString());
                }
            }
            else
            {
                nameAndValue.put(definition.getDisplayName(),
                        ((ReferenceValueDefaultView) value)
                                .getLocalizedDisplayString());
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIBA() - end" + nameAndValue.size()); //$NON-NLS-1$
        }
        return nameAndValue;
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
        HashMap partIBAMap = new HashMap();
        try
        {
            partIBAMap = getPartIBA();
        }
        catch (QMXMLException e)
        {
            //"��ȡ��������ʱ����"
            logger.error(Messages.getString("Util.12") + e);
            throw new QMXMLException(e);
        }
        final List extensionPropList = new ArrayList(1);
        final List basicPropList = new ArrayList(10);
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
        for (int i = 0; i < extensionPropList.size(); i++)
        {
            property = (QMXMLProperty) extensionPropList.get(i);
            col = new QMXMLColumn();
            if(property.getName().equals("part_publish_type"))
            {
                col.setId(property.getId());
                col.setValue(getPart_publish_type());
            }
            colList.add(col);
        }
        for (int i = 0; i < basicPropList.size(); i++)
        {
            property = (QMXMLProperty) basicPropList.get(i);
            col = new QMXMLColumn();
            if(property.getName().equals("partNumber"))
            {
                col.setId(property.getId());
                col.setValue(getPartNumber());
            }
            else if(property.getName().equals("partName"))
            {
                col.setId(property.getId());
                col.setValue(getPartName());
            }
            else if(property.getName().equals("versionID"))
            {
                col.setId(property.getId());
//                col.setValue(getPartVesionID().substring(0, 1));
                col.setValue(getPartVesionID());
            }
            else if(property.getName().equals("defaultUnitStr"))
            {
                col.setId(property.getId());
                col.setValue(getPartDefaultUnitStr());
            }
            else if(property.getName().equals("producedByStr"))
            {
                col.setId(property.getId());
                col.setValue(getPartProducedByStr());
            }
            else if(property.getName().equals("partTypeStr"))
            {
                col.setId(property.getId());
                col.setValue(getPartTypeStr());
            }
            else if(property.getName().equals("modifyTime"))
            {
                col.setId(property.getId());
                col.setValue(getPartModifyTime());
            }
            else if(property.getName().equals("optionCode"))
            {
                col.setId(property.getId());
                col.setValue(getPartOptionCode());
            }
            else if(property.getName().equals("colorFlag"))
            {
                col.setId(property.getId());
                col.setValue(getPartColorFlag());
            }
            else if(property.getName().equals("lifeCycleState"))
            {
                col.setId(property.getId());
                col.setValue(getPartLifeCycleState());
            }
            colList.add(col);
        }
        for (int i = 0; i < ibaPropList.size(); i++)
        {
            property = (QMXMLProperty) ibaPropList.get(i);
            col = new QMXMLColumn();
            col.setId(property.getId());
            if(partIBAMap.containsKey(property.getName()))
            {
                col.setValue(partIBAMap.get(property.getName()).toString());
            }
            else
            {
                col.setValue("");
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
     * @return ���� part��
     */
    public final QMPartIfc getPart()
    {
        return part;
    }

    /**
     * @param part Ҫ���õ� part��
     */
    public final void setPart(final QMPartIfc part)
    {
        this.part = part;
    }
}
