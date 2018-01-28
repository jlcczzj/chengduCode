/** ���ɳ��� PartUsageLink.java    1.0    2003/02/17
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 *  SS1 ����BOM��������� liuyang 2014-6-20
 *  SS2 ���������汾 xianglx 2014-8-12
 */

package com.faw_qm.part.ejb.entity;

import com.faw_qm.build.ejb.entity.BuildableLink;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.struct.ejb.entity.IteratedUsageLink;


/**
 * �㲿�����㲿������Ϣ�Ĺ����࣬��Ҫ��¼���㲿��֮���ʹ�ù�ϵ��ʹ�õ������͵�λ��
 * @author ���ȳ�
 * @version 1.0
 */

public interface PartUsageLink extends IteratedUsageLink, BuildableLink
{
    /**
     * ���ʹ��������
     * @return float
     */
    public float getQuantity();


    /**
     * ����ʹ��������
     * @param quantity float
     */
    public void setQuantity(float quantity);


    /**
     * ���ʹ�õ�λ����CMP����
     * @return String
     */
    public abstract String getDefaultUnitStr();


    /**
     * ��ȡʹ�õĵ�λ��
     * @return Unit
     */
    public Unit getDefaultUnit();


    /**
     * ����ʹ�õ�λ����CMP����
     * @param unit String
     */

    public abstract void setDefaultUnitStr(String unit);


    /**
     * ����ʹ�õ�λ��
     * @param unit Unit
     */
    public void setDefaultUnit(Unit unit);


    /**
     * ��ȡʹ��������Ķ��󡣸ö��������ʹ��������Ĭ�ϵ�ʹ�õ�λ��
     * @return QMQuantity
     */
    public QMQuantity getQMQuantity();


    /**
     * ����ʹ�õ�λ��Ķ���
     * @param qmQuantity1 QMQuantity
     */
    public void setQMQuantity(QMQuantity qmQuantity1);


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
