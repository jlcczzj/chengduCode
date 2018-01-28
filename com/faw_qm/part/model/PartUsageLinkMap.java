/** ���ɳ��� PartUsageLinkMap.java    1.0    2003/02/17
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ����BOM��������� liuyang 2014-6-20
 *  SS2 ���������汾 xianglx 2014-8-12
 */

package com.faw_qm.part.model;

import com.faw_qm.framework.service.ObjectMappable;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;


/**
 * �㲿�����㲿������Ϣ�Ĺ������ֵ����ӿڵĸ����࣬
 * @author ���ȳ�
 * @version 1.0
 */

public class PartUsageLinkMap implements ObjectMappable
{
    private float quantity;
    private Unit defaultUnit = Unit.getUnitDefault();
    private QMQuantity qmQuantity = new QMQuantity();
    private boolean optionFlag;
    static final long serialVersionUID = -1L;


    /**
     * ���캯����
     */
    public PartUsageLinkMap()
    {
        super();
    }


    /**
     * ���ʹ��������
     * @return float
     */
    public float getQuantity()
    {
        return quantity;
    }


    /**
     * ����ʹ��������
     * @param quantity1 float
     */
    public void setQuantity(float quantity1)
    {
        quantity = quantity1;
    }


    /**
     * ���ʹ�õ�λ��
     * @return String
     */
    public Unit getDefaultUnit()
    {
        return defaultUnit;
    }


    /**
     * ����ʹ�õ�λ��
     * @param unit Unit
     */
    public void setDefaultUnit(Unit unit)
    {
        defaultUnit = unit;
    }
    
    /**
     * ����ʹ�õ�λ��
     * @param unit Unit
     */
    public void setDefaultUnit(String unit)
    {
        defaultUnit = Unit.toUnit(unit);
    }


    /**
     * ��ȡʹ�����������
     * @return QMQuantity
     */
    public QMQuantity getQMQuantity()
    {
        return qmQuantity;
    }


    /**
     * ����ʹ�����������
     * @param qmQuantity1 QMQuantity
     */
    public void setQMQuantity(QMQuantity qmQuantity1)
    {
        qmQuantity = qmQuantity1;
    }


    /**
     * ����ѡװ��ʶ��
     *
     * @return boolean
     */
    public boolean getOptionFlag()
    {
        return optionFlag;
    }


    /**
     * ����ѡװ��ʶ��
     *
     * @param flag boolean
     */
    public void setOptionFlag(boolean flag)
    {
        optionFlag = flag;
    }
//CCBegin SS1
    private String subUnitNumber;
    private String bomItem;
    public String getSubUnitNumber()
    {
    	return subUnitNumber;
    }
    public void setSubUnitNumber(String subUnitNumber1)
    {
    	subUnitNumber = subUnitNumber1;
    }
    public String getBomItem()
    {
    	return bomItem;
    }
    public void setBomItem(String bomItem1)
    {
    	bomItem = bomItem1;
    }
    //CCEnd SS1
//CCBegin SS2
    private String proVersion;
    public String getProVersion()
    {
    	return proVersion;
    }
    public void setProVersion(String proVersion1)
    {
    	proVersion = proVersion1;
    }
    //CCEnd SS2
}
