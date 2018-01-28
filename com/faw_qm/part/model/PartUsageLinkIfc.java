/** ���ɳ��� PartUsageLinkIfc.java    1.0    2003/02/17
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ����BOM��������� liuyang 2014-6-20
 *  SS2 ���������汾 xianglx 2014-8-12
 */

package com.faw_qm.part.model;

import com.faw_qm.build.model.BuildableLinkIfc;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.struct.model.IteratedUsageLinkIfc;


/**
 * �㲿�����㲿������Ϣ�Ĺ������ֵ����ӿڣ���Ҫ��¼���㲿��֮���ʹ�ù�ϵ��ʹ�õ������͵�λ��
 * @author ���ȳ�
 * @version 1.0
 */

public interface PartUsageLinkIfc extends IteratedUsageLinkIfc,
        BuildableLinkIfc
{
    /**
     * ���ʹ��������
     * @return float
     */
    public float getQuantity();


    /**
     * ����ʹ��������
     * @param quantity :float
     */
    public void setQuantity(float quantity);


    /**
     * ���ʹ�õ�λ��
     * @return Unit
     */
    public Unit getDefaultUnit();


    /**
     * ����ʹ�õ�λ��
     * @param unit Unit
     */
    public void setDefaultUnit(Unit unit);
    
    /**
     * ����ʹ�õ�λ��
     * @param unit Unit
     * @see Unit
     */
    public void setDefaultUnit(String unit);


    /**
     * ��ȡ�����ศ�������
     * @return PartUsageLinkMap
     */
    public PartUsageLinkMap getPartUsageLinkMap();


    /**
     * ���ù����ศ�������
     * @param map PartUsageLinkMap
     */
    public void setPartUsageLinkMap(PartUsageLinkMap map);


    /**
     * ��ȡʹ��������Ķ��󡣸ö��������ʹ��������Ĭ�ϵ�ʹ�õ�λ��
     * @return QMQuantity
     */
    public QMQuantity getQMQuantity();


    /**
     * ����ʹ�õ�λ��Ķ���
     * @param qmQuantity QMQuantity
     */
    public void setQMQuantity(QMQuantity qmQuantity);


    /**
     * ����ѡװ��ʶ��
     *
     * @return boolean
     */
    public boolean getOptionFlag();


    /**
     * ����ѡװ��ʶ��
     *
     * @param flag boolean
     */
    public void setOptionFlag(boolean flag);
    //CCBegin SS1
    public String getSubUnitNumber();
    public void setSubUnitNumber(String subUnitNumber);    
    
    public String getBomItem();
    public void setBomItem(String bomItem); 
    //CCEnd SS1
    //CCBegin SS2
    public String getProVersion();
    public void setProVersion(String proVersion);    
    //CCEnd SS2
}
