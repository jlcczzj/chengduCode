/** ���ɳ��� PartUsageLinkInfo.java    1.0    2003/02/17
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2012/03/16 ���� �޸�ԭ�򣺱����ڲ����ͱ����ڲ�Ʒ��Ϊ��jdbc��ѯ��
 * SS1 ����BOM��������� liuyang 2014-6-20
 *  SS2 ���������汾 xianglx 2014-8-12
 */

package com.faw_qm.part.model;

import com.faw_qm.build.model.BuildTargetIfc;
import com.faw_qm.build.util.BuildReference;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.struct.model.IteratedUsageLinkInfo;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;


/**
 * �㲿�����㲿������Ϣ�Ĺ������ֵ����ʵ���࣬
 * ��Ҫ��¼���㲿��֮���ʹ�ù�ϵ��ʹ�õ������͵�λ��
 * @author ���ȳ�
 * @version 1.0
 */

public class PartUsageLinkInfo extends IteratedUsageLinkInfo implements
        PartUsageLinkIfc
{
    public PartUsageLinkMap thePartUsageLinkMap;
    private BuildReference sourceIdentification;
    static final long serialVersionUID = -1L;


    /**
     * ���캯����
     */
    public PartUsageLinkInfo()
    {
        super();
        thePartUsageLinkMap = new PartUsageLinkMap();
    }


    /**
     * �������������Ĺ��캯����
     * @param leftID :String ��������������������BsoID��
     * @param rightID :String �����������ҹ��������BsoID��
     */
    public PartUsageLinkInfo(String leftID, String rightID)
    {
        super(leftID, rightID);
    }


    /**
     * ���ʹ��������
     * @return float
     */
    public float getQuantity()
    {
        return getPartUsageLinkMap().getQuantity();
    }


    /**
     * ����ʹ��������
     * @param quantity :float
     */
    public void setQuantity(float quantity)
    {
        getPartUsageLinkMap().setQuantity(quantity);
    }


    /**
     * ���ʹ�õ�λ��
     * @return Unit ��λ��ö����
     * @see Unit
     */
    public Unit getDefaultUnit()
    {
        return getPartUsageLinkMap().getDefaultUnit();
    }


    /**
     * ����ʹ�õ�λ��
     * @param unit Unit
     * @see Unit
     */
    public void setDefaultUnit(Unit unit)
    {
        getPartUsageLinkMap().setDefaultUnit(unit);
    }
    /**
     * ����ʹ�õ�λ��
     * @param unit Unit
     * @see Unit
     */
    public void setDefaultUnit(String unit)
    {
        getPartUsageLinkMap().setDefaultUnit(unit);
    }


    /**
     * ��ȡ�����ศ�������
     * @return PartUsageLinkMap
     */
    public PartUsageLinkMap getPartUsageLinkMap()
    {
        if (thePartUsageLinkMap == null)
        {
            thePartUsageLinkMap = new PartUsageLinkMap();
        }
        return thePartUsageLinkMap;
    }


    /**
     * ���ù����ศ�������
     * @param map PartUsageLinkMap
     */
    public void setPartUsageLinkMap(PartUsageLinkMap map)
    {
        thePartUsageLinkMap = map;
    }


    /**
     * ��ȡҵ���������
     * @return "PartUsageLink"
     */
    public String getBsoName()
    {
        return "PartUsageLink";
    }


    /**
     * ��ȡʹ�����������
     * @return QMQuantity �����ķ�װ��
     * @see QMQuantity
     */
    public QMQuantity getQMQuantity()
    {
        return getPartUsageLinkMap().getQMQuantity();
    }


    /**
     * ����ʹ�����������
     * @param qmQuantity QMQuantity
     * @see QMQuantity
     */
    public void setQMQuantity(QMQuantity qmQuantity)
    {
        getPartUsageLinkMap().setQMQuantity(qmQuantity);
    }
   /**
    * ��ȡ��������right����QMPartIfc��
    * @return �����ǹ����ṹ�еĸ��㲿������QMPartIfc,����ΪBuildTargetIfc
    * @see BuildTargetIfc
    */
    public BuildTargetIfc getBuildTarget()
    {
        return (BuildTargetIfc) getUsedBy();
    }
    /**
     * ���ù�������right����QMPartIfc��
     * @param buildtarget �����е�right����QMPartIfc��
     * @see BuildTargetIfc
     */
    public void setBuildTarget(BuildTargetIfc buildtarget)
    {
        setUsedBy((IteratedIfc) buildtarget);
    }
    /**
     * ��ȡ��������left����QMPartMasterIfc��
     * @return �����ǹ����ṹ�е����㲿��������QMPartMasterIfc,����ΪBaseValueIfc
     * @see BaseValueIfc
     */
    public BaseValueIfc getPersistable()
    {
        return getUses();
    }
    /**
     * ���ù�������right����QMPartMasterIfc��
     * @param persistable �����е�left����QMPartMasterIfc��
     * @see BaseValueIfc
     */
    public void setPersistable(BaseValueIfc persistable)
    {
        setUses((MasteredIfc) persistable);
    }
    /**
     * ��ȡ��EPM�ĵ���ص�BuildReference����
     * @return BuildReference ��¼EPM�ĵ���Ϣ
     * @see BuildReference
     */
    public BuildReference getSourceIdentification()
    {
        return sourceIdentification;
    }
    /**
     * ���ú�EPM�ĵ���ص�BuildReference��Ϣ
     * @param buildreference 
     * @see BuildReference
     */
    public void setSourceIdentification(BuildReference buildreference)
    {
        sourceIdentification = buildreference;
    }


    /**
     * ����ѡװ��ʶ��
     *
     * @return boolean trueΪѡװ
     */
    public boolean getOptionFlag()
    {
        return getPartUsageLinkMap().getOptionFlag();
    }


    /**
     * ����ѡװ��ʶ��
     *
     * @param flag boolean trueΪѡװ
     */
    public void setOptionFlag(boolean flag)
    {
        getPartUsageLinkMap().setOptionFlag(flag);
    }
    
    public void setBuiltByApplication(boolean flag)//Begin CR1
    {}
    public boolean getBuiltByApplication()
    {
    	return false;
    }//End CR1


//CCBegin SS1

    public String getSubUnitNumber()
    {
    	return getPartUsageLinkMap().getSubUnitNumber();
    }
    public void setSubUnitNumber(String subUnitNumber)
    {
    	getPartUsageLinkMap().setSubUnitNumber(subUnitNumber);
    }
    public String getBomItem()
    {
    	return getPartUsageLinkMap().getBomItem();
    }
    public void setBomItem(String bomItem)
    {
    	getPartUsageLinkMap().setBomItem(bomItem);
    }
	//CCEnd SS1
//CCBegin SS2
    public String getProVersion()
    {
    	return getPartUsageLinkMap().getProVersion();
    }
    public void setProVersion(String ProVersion)
    {
    	getPartUsageLinkMap().setProVersion(ProVersion);
    }
	//CCEnd SS2
}
