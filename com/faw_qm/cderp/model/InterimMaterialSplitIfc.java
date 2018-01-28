/**
 * ���ɳ���InterimMaterialSplitIfc.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.model;

import java.sql.Timestamp;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public interface InterimMaterialSplitIfc extends BaseValueIfc
{
    /**
     * ��������ţ������Ϊ���ϵ�����š�
     * @param partNumber ����š�
     */
    void setPartNumber(String partNumber);

    /**
     * ��ȡ����ţ������Ϊ���ϵ�����š�
     * @return ����š�
     */
    String getPartNumber();

    /**
     * ���ð汾�������Ϊ���ϵ�����İ汾(��������)��
     * @param partVersion �汾��
     */
    void setPartVersion(String partVersion);

    /**
     * ��ȡ�汾�������Ϊ���ϵ�����İ汾(��������)��
     * @return �汾��
     */
    String getPartVersion();

    /**
     * ����״̬�������Ϊ���ϵ�������ʱ����������״̬��
     * @param State ״̬��
     */
    void setState(String State);

    /**
     * ��ȡ״̬�������Ϊ���ϵ�������ʱ����������״̬��
     * @return ״̬��
     */
    String getState();

    /**
     * �������Ϻţ���ֺ�����Ϻţ��������+��-��+·�ߴ������,��ͷ���ڶ��γ��ּ�β��"-1"��
     * @param MaterialNumber ���Ϻš�
     */
    void setMaterialNumber(String MaterialNumber);

    /**
     * ��ȡ���Ϻţ���ֺ�����Ϻţ��������+��-��+·�ߴ������,��ͷ���ڶ��γ��ּ�β��"-1"��
     * @return ���Ϻš�
     */
    String getMaterialNumber();

    /**
     * ����ת����ʶ������Ƿ�ת��Ϊ���ϣ�0��δת����1����ת����
     * @param Splited ת����ʶ��
     */
    void setSplited(boolean Splited);

    /**
     * ��ȡת����ʶ������Ƿ�ת��Ϊ���ϣ�0��δת����1����ת����
     * @return ת����ʶ��
     */
    boolean getSplited();

    /**
     * ���ò㼶״̬�������Ƿ񾭹���֣�0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
     * @param Status �㼶״̬��
     */
    void setStatus(int Status);

    /**
     * ��ȡ�㼶״̬�������Ƿ񾭹���֣�0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
     * @return �㼶״̬��
     */
    int getStatus();

    /**
     * ����·�ߴ��룬��������йص����ϲ�ֺ��·�ߴ��롣
     * @param RouteCode ·�ߴ��롣
     */
    void setRouteCode(String RouteCode);

    /**
     * ��ȡ·�ߴ�����������йص����ϲ�ֺ��·�ߴ��롣
     * @return ·�ߴ��롣
     */
    String getRouteCode();

    /**
     * ����·�ߣ��������Ա���һ���������������·��,��·��ʱ�÷ֺš�;���ָ���
     * @param Route ·�ߡ�
     */
    void setRoute(String Route);

    /**
     * ��ȡ·�ߣ��������Ա���һ���������������·��,��·��ʱ�÷ֺš�;���ָ���
     * @return ·�ߡ�
     */
    String getRoute();

    /**
     * ����������ƣ������Ϊ���ϵ��������,Ҳ��Ϊ���ϵ����ơ�
     * @param PartName ������ơ�
     */
    void setPartName(String PartName);

    /**
     * ��ȡ������ƣ������Ϊ���ϵ��������,Ҳ��Ϊ���ϵ����ơ�
     * @return ������ơ�
     */
    String getPartName();

    /**
     * ����Ĭ�ϵ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס���,ɢ����ֻʹ��"��",����Ϊ"��"��
     * @param DefaultUnit Ĭ�ϵ�λ��
     */
    void setDefaultUnit(String DefaultUnit);

    /**
     * ��ȡĬ�ϵ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס���,ɢ����ֻʹ��"��",����Ϊ"��"��
     * @return Ĭ�ϵ�λ��
     */
    String getDefaultUnit();

    /**
     * ������Դ��ö��ֵΪ�����ơ��⹺������,Ĭ��ֵΪ��������
     * @param ProducedBy ��Դ��
     */
    void setProducedBy(String ProducedBy);

    /**
     * ��ȡ��Դ��ö��ֵΪ�����ơ��⹺������,Ĭ��ֵΪ��������
     * @return ��Դ��
     */
    String getProducedBy();

    /**
     * �������ͣ�ö��ֵ�У�������ܳɡ���׼������Ʒ,���ɰ���:��ѹ�����������ܼ����ǽ���������׼����װ�ü���������߼��ܳɡ����͡����͡����ӡ�װ�䡢��ϴ�����ơ��������
     * @param PartType ���͡�
     */
    void setPartType(String PartType);

    /**
     * ��ȡ���ͣ�ö��ֵ�У�������ܳɡ���׼������Ʒ,���ɰ���:��ѹ�����������ܼ����ǽ���������׼����װ�ü���������߼��ܳɡ����͡����͡����ӡ�װ�䡢��ϴ�����ơ��������
     * @return ���͡�
     */
    String getPartType();
    
    /**
     * �����������ʱ�䡣
     * @param partModifyTime �������ʱ�䡣
     */
    void setPartModifyTime(Timestamp partModifyTime);

    /**
     * ��ȡ�������ʱ�䡣
     * @return �������ʱ�䡣
     */
    Timestamp getPartModifyTime();

    /**
     * ����ѡװ�����룬��PDM������ע������㲿����¼���롣
     * @param OptionCode ѡװ�����롣
     */
    void setOptionCode(String OptionCode);

    /**
     * ��ȡѡװ�����룬��PDM������ע������㲿����¼���롣
     * @return ѡװ�����롣
     */
    String getOptionCode();

    /**
     * ������ɫ����ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @param ColorFlag ��ɫ����ʶ��
     */
    void setColorFlag(boolean ColorFlag);

    /**
     * ��ȡ��ɫ����ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @return ��ɫ����ʶ��
     */
    boolean getColorFlag();
    
    /**
     * ����Դ���ϲ�ֵ�bsoID��
     */
    void setSourceBsoID(String sourceBsoID);
    
    /**
     * ��ȡԴ���ϲ�ֵ�bsoID��
     * @return Դ���ϲ�ֵ�bsoID��
     */
    String getSourceBsoID();
    
    /**
     * ���ø��±�ʶ��
     */
    void setUpdateFlag(String updateFlag);
    
    /**
     * ��ȡ���±�ʶ��
     * @return ���±�ʶ��
     */
    String getUpdateFlag();
    
    /**
     * ���ø����ߡ�
     */
    void setOwner(String userID);
    
    /**
     * ��ȡ�����ߡ�
     * @return �����ߡ�
     */
    String getOwner();
    
    /**
     * ���ö������ϱ�ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @param rootFlag �������ϱ�ʶ��
     */
    void setRootFlag(boolean rootFlag);

    /**
     * ��ȡ�������ϱ�ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @return �������ϱ�ʶ��
     */
    boolean getRootFlag();
}