/**
 * ���ɳ���MaterielSplitEJB.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import com.faw_qm.enterprise.ejb.entity.ItemEJB;
import com.faw_qm.erp.model.MaterialSplitInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public abstract class MaterialSplitEJB extends ItemEJB
{
    /**
     * ��������ţ������Ϊ���ϵ�����š�
     * @param partNumber ����š�
     */
    public abstract void setPartNumber(String partNumber);

    /**
     * ��ȡ����ţ������Ϊ���ϵ�����š�
     * @return ����š�
     */
    public abstract String getPartNumber();
    public abstract void setRCode(String rCode);

    /**
     * ��ȡ����ţ������Ϊ���ϵ�����š�
     * @return ����š�
     */
    public abstract String getRCode();
    public abstract void setMaterialSplitType(String materialSplitType);

    /**
     * ��ȡ����ţ������Ϊ���ϵ�����š�
     * @return ����š�
     */
    public abstract String getMaterialSplitType();
    /**
     * ���ð汾�������Ϊ���ϵ�����İ汾(��������)��
     * @param partVersion �汾��
     */
    public abstract void setPartVersion(String partVersion);

    /**
     * ��ȡ�汾�������Ϊ���ϵ�����İ汾(��������)��
     * @return �汾��
     */
    public abstract String getPartVersion();

    /**
     * ����״̬�������Ϊ���ϵ�������ʱ����������״̬��
     * @param State ״̬��
     */
    public abstract void setState(String State);

    /**
     * ��ȡ״̬�������Ϊ���ϵ�������ʱ����������״̬��
     * @return ״̬��
     */
    public abstract String getState();

    /**
     * �������Ϻţ���ֺ�����Ϻţ��������+��-��+·�ߴ������,��ͷ���ڶ��γ��ּ�β��"-1"��
     * @param MaterielNumber ���Ϻš�
     */
    public abstract void setMaterialNumber(String MaterielNumber);

    /**
     * ��ȡ���Ϻţ���ֺ�����Ϻţ��������+��-��+·�ߴ������,��ͷ���ڶ��γ��ּ�β��"-1"��
     * @return ���Ϻš�
     */
    public abstract String getMaterialNumber();

    /**
     * ����ת����ʶ������Ƿ�ת��Ϊ���ϣ�0��δת����1����ת����
     * @param Splited ת����ʶ��
     */
    public abstract void setSplited(boolean Splited);

    /**
     * ��ȡת����ʶ������Ƿ�ת��Ϊ���ϣ�0��δת����1����ת����
     * @return ת����ʶ��
     */
    public abstract boolean getSplited();

    /**
     * ���ò㼶״̬�������Ƿ񾭹���֣�0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
     * @param Status �㼶״̬��
     */
    public abstract void setStatus(int Status);

    /**
     * ��ȡ�㼶״̬�������Ƿ񾭹���֣�0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
     * @return �㼶״̬��
     */
    public abstract int getStatus();

    /**
     * ����·�ߴ��룬��������йص����ϲ�ֺ��·�ߴ��롣
     * @param RouteCode ·�ߴ��롣
     */
    public abstract void setRouteCode(String RouteCode);

    /**
     * ��ȡ·�ߴ�����������йص����ϲ�ֺ��·�ߴ��롣
     * @return ·�ߴ��롣
     */
    public abstract String getRouteCode();

    /**
     * ����·�ߣ��������Ա���һ���������������·��,��·��ʱ�÷ֺš�;���ָ���
     * @param Route ·�ߡ�
     */
    public abstract void setRoute(String Route);

    /**
     * ��ȡ·�ߣ��������Ա���һ���������������·��,��·��ʱ�÷ֺš�;���ָ���
     * @return ·�ߡ�
     */
    public abstract String getRoute();

    /**
     * ����������ƣ������Ϊ���ϵ��������,Ҳ��Ϊ���ϵ����ơ�
     * @param PartName ������ơ�
     */
    public abstract void setPartName(String PartName);

    /**
     * ��ȡ������ƣ������Ϊ���ϵ��������,Ҳ��Ϊ���ϵ����ơ�
     * @return ������ơ�
     */
    public abstract String getPartName();

    /**
     * ����Ĭ�ϵ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס���,ɢ����ֻʹ��"��",����Ϊ"��"��
     * @param DefaultUnit Ĭ�ϵ�λ��
     */
    public abstract void setDefaultUnit(String DefaultUnit);

    /**
     * ��ȡĬ�ϵ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס���,ɢ����ֻʹ��"��",����Ϊ"��"��
     * @return Ĭ�ϵ�λ��
     */
    public abstract String getDefaultUnit();

    /**
     * ������Դ��ö��ֵΪ�����ơ��⹺������,Ĭ��ֵΪ��������
     * @param ProducedBy ��Դ��
     */
    public abstract void setProducedBy(String ProducedBy);

    /**
     * ��ȡ��Դ��ö��ֵΪ�����ơ��⹺������,Ĭ��ֵΪ��������
     * @return ��Դ��
     */
    public abstract String getProducedBy();

    /**
     * �������ͣ�ö��ֵ�У�������ܳɡ���׼������Ʒ,���ɰ���:��ѹ�����������ܼ����ǽ���������׼����װ�ü���������߼��ܳɡ����͡����͡����ӡ�װ�䡢��ϴ�����ơ��������
     * @param PartType ���͡�
     */
    public abstract void setPartType(String PartType);

    /**
     * ��ȡ���ͣ�ö��ֵ�У�������ܳɡ���׼������Ʒ,���ɰ���:��ѹ�����������ܼ����ǽ���������׼����װ�ü���������߼��ܳɡ����͡����͡����ӡ�װ�䡢��ϴ�����ơ��������
     * @return ���͡�
     */
    public abstract String getPartType();
    
    /**
     * �����������ʱ�䡣
     * @param partModifyTime �������ʱ�䡣
     */
    public abstract void setPartModifyTime(Timestamp partModifyTime);

    /**
     * ��ȡ�������ʱ�䡣
     * @return �������ʱ�䡣
     */
    public abstract Timestamp getPartModifyTime();

    /**
     * ����ѡװ�����룬��PDM������ע������㲿����¼���롣
     * @param OptionCode ѡװ�����롣
     */
    public abstract void setOptionCode(String OptionCode);

    /**
     * ��ȡѡװ�����룬��PDM������ע������㲿����¼���롣
     * @return ѡװ�����롣
     */
    public abstract String getOptionCode();

    /**
     * ������ɫ����ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @param ColorFlag ��ɫ����ʶ��
     */
    public abstract void setColorFlag(boolean ColorFlag);

    /**
     * ��ȡ��ɫ����ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @return ��ɫ����ʶ��
     */
    public abstract boolean getColorFlag();
    
    /**
     * ���ö������ϱ�ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @param rootFlag �������ϱ�ʶ��
     */
    public abstract void setRootFlag(boolean rootFlag);

    /**
     * ��ȡ�������ϱ�ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @return �������ϱ�ʶ��
     */
    public abstract boolean getRootFlag();
    
    /**
     * �����������ϱ�ʶ
     * @param flag �������ϱ�ʶ
     */
    public abstract void setVirtualFlag(boolean flag);
    
    /**
     * ��ȡ��·�߱�ʶ
     * @return ��·�߱�ʶ
     */
    public abstract boolean getIsMoreRoute();
    
    /**
     * ���ö�·�߱�ʶ
     * @param flag ��·�߱�ʶ
     */
    public abstract void setIsMoreRoute(boolean flag);
    
    /**
     * ��ȡ�������ϱ�ʶ
     * @return �������ϱ�ʶ
     */
    public abstract boolean getVirtualFlag();

    public BaseValueIfc getValueInfo() throws QMException
    {
        MaterialSplitInfo info = new MaterialSplitInfo();
        setValueInfo(info);
        return info;
    }

    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        MaterialSplitInfo info1 = (MaterialSplitInfo) info;
        info1.setPartNumber(getPartNumber());
        info1.setRCode(getRCode());
        info1.setMaterialSplitType(getMaterialSplitType());
        info1.setPartName(getPartName());
        info1.setPartVersion(getPartVersion());
        info1.setState(getState());
        info1.setMaterialNumber(getMaterialNumber());
        info1.setSplited(getSplited());
        info1.setStatus(getStatus());
        info1.setRouteCode(getRouteCode());
        info1.setRoute(getRoute());
        info1.setPartName(getPartName());
        info1.setDefaultUnit(getDefaultUnit());
        info1.setProducedBy(getProducedBy());
        info1.setPartType(getPartType());
        info1.setPartModifyTime(getPartModifyTime());
        info1.setOptionCode(getOptionCode());
        info1.setColorFlag(getColorFlag());
        info1.setRootFlag(getRootFlag());
        info1.setVirtualFlag(getVirtualFlag());
        info1.setIsMoreRoute(getIsMoreRoute());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        MaterialSplitInfo info1 = (MaterialSplitInfo) info;
        setPartNumber(info1.getPartNumber());
        setRCode(info1.getRCode());
        setMaterialSplitType(info1.getMaterialSplitType());
        setPartName(info1.getPartName());
        setPartVersion(info1.getPartVersion());
        setState(info1.getState());
        setMaterialNumber(info1.getMaterialNumber());
        setSplited(info1.getSplited());
        setStatus(info1.getStatus());
        setRouteCode(info1.getRouteCode());
        setRoute(info1.getRoute());
        setPartName(info1.getPartName());
        setDefaultUnit(info1.getDefaultUnit());
        setProducedBy(info1.getProducedBy());
        setPartType(info1.getPartType());
        setPartModifyTime(info1.getPartModifyTime());
        setOptionCode(info1.getOptionCode());
        setColorFlag(info1.getColorFlag());
        setRootFlag(info1.getRootFlag());
        setVirtualFlag(info1.getVirtualFlag());
        setIsMoreRoute(info1.getIsMoreRoute());
    }

    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        MaterialSplitInfo info1 = (MaterialSplitInfo) info;
        setPartNumber(info1.getPartNumber());
        setRCode(info1.getRCode());
        setPartName(info1.getPartName());
        setPartVersion(info1.getPartVersion());
        setState(info1.getState());
        setMaterialNumber(info1.getMaterialNumber());
        setSplited(info1.getSplited());
        setStatus(info1.getStatus());
        setRouteCode(info1.getRouteCode());
        setRoute(info1.getRoute());
        setPartName(info1.getPartName());
        setDefaultUnit(info1.getDefaultUnit());
        setProducedBy(info1.getProducedBy());
        setPartType(info1.getPartType());
        setPartModifyTime(info1.getPartModifyTime());
        setOptionCode(info1.getOptionCode());
        setColorFlag(info1.getColorFlag());
        setRootFlag(info1.getRootFlag());
        setVirtualFlag(info1.getVirtualFlag());
        setIsMoreRoute(info1.getIsMoreRoute());
        //CCBegin by dikefeng 20100422������ط���Ҫ���ϣ��������֮���ٱ���Ļ����ͱ��治��
        setMaterialSplitType(info1.getMaterialSplitType());
        //CCEnd by dikefeng 20100422
    }

    public String getBsoName()
    {
        return "MaterialSplit";
    }
}
