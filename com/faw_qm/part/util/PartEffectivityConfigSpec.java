/** 生成程序 PartEffectivityConfigSpec.java    1.0    2003/02/18
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * 有效性配置规范。
 PartEffectivityConfigSpec对象也不是一个持久化数据，它封装的四个属性中，effectivi
 eConfigItemIfc和viewObjectIfc必须分别对应数据库中的一个持久化对象，而effectiveUn
 it和effectivtyType是临时变量。如果ConfigItemIfc对象包含有效性类型，那么effectivi
 tyType的数据应该和其一致，否则，effectivityType的数据最初由用户指定。
 */

/**
 * 有效性配置规范。
 * @author 吴先超
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
    //和解放的序列化ID不一致，为了确保对象能正确的序列化，修改序列化ID。
    //static final long serialVersionUID = 1L;
    static final long serialVersionUID = -5093299473645238655L;
    //CCEnd by zhangq 20080626

    /**
     * 构造函数。
     */
    public PartEffectivityConfigSpec()
    {

    }


    /**
     * 获取有效性配置项值对象。
     * @return QMConfigurationItemIfc
     */
    public QMConfigurationItemIfc getEffectiveConfigItemIfc()
    {
        return effectiveConfigItemIfc;
    }


    /**
     * 设置有效性配置项值对象。
     * @param configItemIfc QMConfigurationItemIfc
     */
    public void setEffectiveConfigItemIfc(QMConfigurationItemIfc configItemIfc)
    {
        effectiveConfigItemIfc = configItemIfc;
    }


    /**
     * 设置有效性单位。
     * @param unit String
     */
    public void setEffectiveUnit(String unit)
    {
        effectiveUnit = unit;
    }


    /**
     * 获取有效性单位。
     * @return String
     */
    public String getEffectiveUnit()
    {
        return effectiveUnit;
    }


    /**
     * 获取有效性类型。
     * @return EffectivityType
     */
    public EffectivityType getEffectivityType()
    {
        return effectivityType;
    }


    /**
     * 设置有效性类型。
     * @param type EffectivityType
     */
    public void setEffectivityType(EffectivityType type)
    {
        effectivityType = type;
    }


    /**
     * 设置视图。
     * @param viewIfc ViewObjectIfc
     */
    public void setViewObjectIfc(ViewObjectIfc viewIfc)
    {
        viewObjectIfc = viewIfc;
    }


    /**
     * 获取视图。
     * @return ViewObjectIfc
     */
    public ViewObjectIfc getViewObjectIfc()
    {
        return viewObjectIfc;
    }


    /**
     * 获取当前日期。
     * @return Timestamp
     */
    public Timestamp getCurrentDate()
    {
        return new Timestamp(System.currentTimeMillis());
    }


    /**
     * 根据本地化信息，(TimeZone, Locale)重新设置Timestamp的Hour:Minute:Second。
     * @param timestamp Timestamp
     * @return Timestamp
     */
    private Timestamp stripHMS(Timestamp timestamp)
    {
        PartDebug.trace(this, "stripHMS() begin ....");
        GregorianCalendar gregoriancalendar = new
                                              GregorianCalendar(QMCt.getContext().
                getTimeZone(), QMCt.getContext().getLocale());

        //初始化日历对象，指明时区和国家。
        //Sets this Calendar's current time with the given Date.
        gregoriancalendar.setTime(timestamp);
        /*
                 public final void set(int field, int value)
                 Sets the time field with the given value. 根据给定的value设定时区
                 Parameters:
                 field - the given time field.给定的时区
         value - the value to be set for the given time field. 给指定的时区赋值的信息值
         */
        //GMT --- 格林尼治标准时间
        gregoriancalendar.set(HOUR, 0); //hour设置时间戳的小时
        gregoriancalendar.set(MINUTE, 0); //mm设置时间戳的分钟
        gregoriancalendar.set(SECOND, 0); //ss设置时间戳的秒
        PartDebug.trace(this, "stripHMS() end....return is Timestamp");
        return new Timestamp(gregoriancalendar.getTime().getTime());
    }


    /**
     * 判断输入的参数timestamp是否和当前日期相同。
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
     * 创建有效性配置规范，同时为配置项(EffContextIfc 也叫做有效性解决方案)，有效值
     * 和有效性类型赋值。
     * @param effTypeName 有效性类型名。
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
