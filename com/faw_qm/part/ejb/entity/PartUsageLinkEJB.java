/** ���ɳ��� PartUsageLinkEJB.java    1.0    2003/02/17
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 *  SS1 ����BOM��������� liuyang 2014-6-20
 *  SS2 ���������汾 xianglx 2014-8-12
 */

package com.faw_qm.part.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.build.util.BuildReference;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.struct.ejb.entity.IteratedUsageLinkEJB;


/**
 * �㲿�����㲿������Ϣ�Ĺ����࣬��Ҫ��¼���㲿��֮���ʹ�ù�ϵ��ʹ�õ������͵�λ��
 * @author ���ȳ�
 * @version 1.0
 */

public abstract class PartUsageLinkEJB extends IteratedUsageLinkEJB
{
    private QMQuantity qmQuantity;


    /**
     * ���ʹ��������
     * @return float
     */
    public abstract float getQuantity();


    /**
     * ����ʹ��������
     * @param quantity float
     */
    public abstract void setQuantity(float quantity);


    /**
     * ���ʹ�õ�λ����CMP����
     * @return String
     */
    public abstract String getDefaultUnitStr();


    /**
     * ��ȡʹ�õĵ�λ��
     * @return Unit
     * @see Unit
     */
    public Unit getDefaultUnit()
    {
        return Unit.toUnit(getDefaultUnitStr());
    }


    /**
     * ����ʹ�õ�λ����CMP����
     * @param unit String
     */

    public abstract void setDefaultUnitStr(String unit);


    /**
     * ����ʹ�õ�λ��
     * @param unit Unit
     * @see Unit
     */
    public void setDefaultUnit(Unit unit)
    {
        setDefaultUnitStr(unit.toString());
    }


    /**
     * ��ȡʹ��������Ķ��󡣸ö��������ʹ��������Ĭ�ϵ�ʹ�õ�λ��
     * @return QMQuantity
     * @see QMQuantity
     */
    public QMQuantity getQMQuantity()
    {
        if (qmQuantity == null)
        {
            qmQuantity = new QMQuantity();
            qmQuantity.setDefaultUnit(getDefaultUnit());
            qmQuantity.setQuantity(getQuantity());
        }
        return qmQuantity;
    }


    /**
     * ����ʹ�õ�λ��Ķ���
     * @param qmQuantity1 QMQuantity
     * @see QMQuantity
     */
    public void setQMQuantity(QMQuantity qmQuantity1)
    {
        qmQuantity = qmQuantity1;
    }


    /**
     * ���캯����
     */
    public PartUsageLinkEJB()
    {
        super();
    }


    /**
     * ��ȡҵ�������ơ�
     * @return "PartUsageLink"
     */
    public String getBsoName()
    {
        return "PartUsageLink";
    }


    /**
     * ����ѡװ��ʶ��
     *
     * @return boolean
     */
    public abstract boolean getOptionFlag();


    /**
     * ����ѡװ��ʶ��
     *    
     * @param flag boolean
     */
    public abstract void setOptionFlag(boolean flag);
        
//CCBegin SS1
    private String subUnitNumber;
    private String bomItem;
  public abstract String getSubUnitNumber();
    
    public abstract void setSubUnitNumber(String subUnitNumber);
    
    public abstract String getBomItem();
   
    public abstract void  setBomItem(String bomItem);
    //CCEnd SS1
//CCBegin SS2
    public abstract String getProVersion();
   
    public abstract void  setProVersion(String proVersion);
//CCEnd SS1
    /**
     * ���ù������ֵ����
     * @param info BaseValueIfc
     * @throws QMException
     * @see BaseValueIfc
     */
    public void setValueInfo(BaseValueIfc info)
            throws QMException
    {
        super.setValueInfo(info);
        PartUsageLinkInfo pInfo = (PartUsageLinkInfo) info;
        pInfo.setQuantity(getQuantity());
        pInfo.setDefaultUnit(getDefaultUnit());
        pInfo.setQMQuantity(getQMQuantity());
        pInfo.setSourceIdentification(getSourceIdentification());
        pInfo.setOptionFlag(getOptionFlag());
        //CCBegin SS1
        pInfo.setSubUnitNumber(getSubUnitNumber());
        pInfo.setBomItem(getBomItem());
        //CCEnd SS1
        //CCBegin SS2
        pInfo.setProVersion(getProVersion());
        //CCEnd SS2
    }
    


    /**
     * ��ȡ�������ֵ����
     * @return BaseValueIfc
     * @throws QMException
     * @see BaseValueIfc
     */
    public BaseValueIfc getValueInfo()
            throws QMException
    {
        PartUsageLinkInfo linkInfo = new PartUsageLinkInfo();
        setValueInfo(linkInfo);
        return linkInfo;
    }


    /**
     * ���ݹ�����ֵ���󴴽��������ҵ�����
     * @param info BaseValueIfc
     * @throws CreateException
     * @see BaseValueIfc
     */
    public void createByValueInfo(BaseValueIfc info)
            throws CreateException
    {
        super.createByValueInfo(info);
        PartUsageLinkInfo linkInfo = (PartUsageLinkInfo) info;
        setQuantity(linkInfo.getQuantity());
        setDefaultUnit(linkInfo.getDefaultUnit());
        setQMQuantity(linkInfo.getQMQuantity());
        BuildReference build = linkInfo.getSourceIdentification();
        setSourceIdentification(build);
        setOptionFlag(linkInfo.getOptionFlag());
        //CCBegin SS1
        setSubUnitNumber(linkInfo.getSubUnitNumber());
        setBomItem(linkInfo.getBomItem());
        //CCEnd SS1
        //CCBegin SS2
        setProVersion(linkInfo.getProVersion());
        //CCEnd SS2
    }


    /**
     * ����ֵ������¹������ҵ�����
     * @param info BaseValueIfc
     * @throws QMException
     * @see BaseValueIfc
     */
    public void updateByValueInfo(BaseValueIfc info)
            throws QMException
    {
        super.updateByValueInfo(info);
        PartUsageLinkInfo linkInfo = (PartUsageLinkInfo) info;
        setQuantity(linkInfo.getQuantity());
        setDefaultUnit(linkInfo.getDefaultUnit());
        setQMQuantity(linkInfo.getQMQuantity());
        BuildReference build = linkInfo.getSourceIdentification();
        setSourceIdentification(build);
        setOptionFlag(linkInfo.getOptionFlag());
        //CCBegin SS1
        setSubUnitNumber(linkInfo.getSubUnitNumber());
        setBomItem(linkInfo.getBomItem());
        //CCEnd SS1
        //CCBegin SS2
        setProVersion(linkInfo.getProVersion());
        //CCEnd SS2
    }


    /**
     * ����BuildReference��
     *
     * @return BuildReference
     * @see BuildReference
     */
    public BuildReference getSourceIdentification()
    {
        BuildReference build = new BuildReference(getApplicationTag(),
                                                  getUniqueID());
        build.setBuiltByApplication(getBuiltByApplication());
        return build;
    }


    /**
     * ����BuildReference�е����ݡ�
     *
     * @param build BuildReference
     * @see BuildReference
     */
    public void setSourceIdentification(BuildReference build)
    {
        if (build != null)
        {
            setBuiltByApplication(build.isBuiltByApplication());
            setApplicationTag(build.getApplicationTag());
            setUniqueID(build.getUniqueID());
        }
    }
/**
 * ���ò�κţ��������ֽṹ��Σ�����Ҫ�ֶ����
 * @param uid
 */
    public abstract void setUniqueID(String uid);
/**
 * ��ȡ��κ�
 * @return
 */
    public abstract String getUniqueID();
/**
 * ����ʹ��EPM�ĵ�Ŀ��
 * @param atag
 */
    public abstract void setApplicationTag(String atag);
/**
 * ��ȡʹ��EPM�ĵ�Ŀ��
 * @return �����ִ����磺com.faw_qm.epm.build.model.EPMBuildLinksRuleInfo:131
 */
    public abstract String getApplicationTag();

    public abstract void setBuiltByApplication(boolean by);

    public abstract boolean getBuiltByApplication();


}
