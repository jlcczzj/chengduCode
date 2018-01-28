/** ���ɳ��� PartEffectivityConfigSpec.java    1.0    2003/02/18
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.part.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import com.faw_qm.eff.model.EffConfigSpecIfc;
import com.faw_qm.eff.model.EffConfigSpecInfo;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.effectivity.util.EffectivityType;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.util.QMCt;
import com.faw_qm.viewmanage.model.ViewObjectIfc;


/*
 * ��Ч�����ù淶��
 PartEffectivityConfigSpec����Ҳ����һ���־û����ݣ�����װ���ĸ������У�effectivi
 eConfigItemIfc��viewObjectIfc����ֱ��Ӧ���ݿ��е�һ���־û����󣬶�effectiveUn
 it��effectivtyType����ʱ���������ConfigItemIfc���������Ч�����ͣ���ôeffectivi
 tyType������Ӧ�ú���һ�£�����effectivityType������������û�ָ����
 */

/**
 * ��Ч�����ù淶��
 * @author ���ȳ�
 * @version 1.0
 */

public class PartEffectivityConfigSpec implements Serializable
{
    private String effectiveUnit;
    private QMConfigurationItemIfc effectiveConfigItemIfc;
    private ViewObjectIfc viewObjectIfc;
    private EffectivityType effectivityType;
    private static final int HOUR = 11;
    private static final int MINUTE = 12;
    private static final int SECOND = 13;
    //CCBegin by zhangq 20080626
    //�ͽ�ŵ����л�ID��һ�£�Ϊ��ȷ����������ȷ�����л����޸����л�ID��
    //static final long serialVersionUID = 1L;
    static final long serialVersionUID = -5093299473645238655L;
    //CCEnd by zhangq 20080626

    /**
     * ���캯����
     */
    public PartEffectivityConfigSpec()
    {

    }


    /**
     * ��ȡ��Ч��������ֵ����
     * @return QMConfigurationItemIfc
     */
    public QMConfigurationItemIfc getEffectiveConfigItemIfc()
    {
        return effectiveConfigItemIfc;
    }


    /**
     * ������Ч��������ֵ����
     * @param configItemIfc QMConfigurationItemIfc
     */
    public void setEffectiveConfigItemIfc(QMConfigurationItemIfc configItemIfc)
    {
        effectiveConfigItemIfc = configItemIfc;
    }


    /**
     * ������Ч�Ե�λ��
     * @param unit String
     */
    public void setEffectiveUnit(String unit)
    {
        effectiveUnit = unit;
    }


    /**
     * ��ȡ��Ч�Ե�λ��
     * @return String
     */
    public String getEffectiveUnit()
    {
        return effectiveUnit;
    }


    /**
     * ��ȡ��Ч�����͡�
     * @return EffectivityType
     */
    public EffectivityType getEffectivityType()
    {
        return effectivityType;
    }


    /**
     * ������Ч�����͡�
     * @param type EffectivityType
     */
    public void setEffectivityType(EffectivityType type)
    {
        effectivityType = type;
    }


    /**
     * ������ͼ��
     * @param viewIfc ViewObjectIfc
     */
    public void setViewObjectIfc(ViewObjectIfc viewIfc)
    {
        viewObjectIfc = viewIfc;
    }


    /**
     * ��ȡ��ͼ��
     * @return ViewObjectIfc
     */
    public ViewObjectIfc getViewObjectIfc()
    {
        return viewObjectIfc;
    }


    /**
     * ��ȡ��ǰ���ڡ�
     * @return Timestamp
     */
    public Timestamp getCurrentDate()
    {
        return new Timestamp(System.currentTimeMillis());
    }


    /**
     * ���ݱ��ػ���Ϣ��(TimeZone, Locale)��������Timestamp��Hour:Minute:Second��
     * @param timestamp Timestamp
     * @return Timestamp
     */
    private Timestamp stripHMS(Timestamp timestamp)
    {
        PartDebug.trace(this, "stripHMS() begin ....");
        GregorianCalendar gregoriancalendar = new
                                              GregorianCalendar(QMCt.getContext().
                getTimeZone(), QMCt.getContext().getLocale());

        //��ʼ����������ָ��ʱ���͹��ҡ�
        //Sets this Calendar's current time with the given Date.
        gregoriancalendar.setTime(timestamp);
        /*
                 public final void set(int field, int value)
                 Sets the time field with the given value. ���ݸ�����value�趨ʱ��
                 Parameters:
                 field - the given time field.������ʱ��
         value - the value to be set for the given time field. ��ָ����ʱ����ֵ����Ϣֵ
         */
        //GMT --- �������α�׼ʱ��
        gregoriancalendar.set(HOUR, 0); //hour����ʱ�����Сʱ
        gregoriancalendar.set(MINUTE, 0); //mm����ʱ����ķ���
        gregoriancalendar.set(SECOND, 0); //ss����ʱ�������
        PartDebug.trace(this, "stripHMS() end....return is Timestamp");
        return new Timestamp(gregoriancalendar.getTime().getTime());
    }


    /**
     * �ж�����Ĳ���timestamp�Ƿ�͵�ǰ������ͬ��
     * @param timestamp Timestamp
     * @return boolean
     */
    private boolean equivalentToCurrentDate(Timestamp timestamp)
    {
        if (timestamp == null)
        {
            return true;
        }
        else
        {
            return stripHMS(getCurrentDate()).equals(stripHMS(timestamp));
        }
    }


    /**
     * ������Ч�����ù淶��ͬʱΪ������(EffContextIfc Ҳ������Ч�Խ������)����Чֵ
     * ����Ч�����͸�ֵ��
     * @param effTypeName ��Ч����������
     * @param value Serializable
     * @return EffConfigSpecIfc
     * @throws PartException
     */
    public EffConfigSpecIfc buildEffConfigSpecInfo(String effTypeName,
            Serializable value)
            throws PartException
    {
        PartDebug.trace(this, "buildEffConfigSpecInfo() begin ....");
        EffConfigSpecInfo effcsInfo = new EffConfigSpecInfo();
        try
        {
            effcsInfo.setEffContextIfc(effectiveConfigItemIfc);
            effcsInfo.setValue(value);
            effcsInfo.setEffTypeName(effTypeName);
            PartDebug.trace(this,
                    "buildEffConfigSpecInfo() end....return is EffConfigSpecIfc ");
            return effcsInfo;
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
            throw new PartException(ex);
        }
    }
}
