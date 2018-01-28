/**
 * ���ɳ���MaterialSplit.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.ejb.entity;

import java.sql.Timestamp;
import com.faw_qm.enterprise.ejb.entity.Item;

/**
 * <p>Title: ���ϲ�֡�</p>
 * <p>Description: �㲿����·�߽ڵ���Ϊ���ϡ�</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public interface MaterialSplit extends Item
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
    void setRCode(String r);

    /**
     * ��ȡ����ţ������Ϊ���ϵ�����š�
     * @return ����š�
     */
    String getRCode();
    void setMaterialSplitType(String materialSplitType);

    /**
     * ��ȡ����ţ������Ϊ���ϵ�����š�
     * @return ����š�
     */
    String getMaterialSplitType();
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
     * @param state ״̬��
     */
    void setState(String state);

    /**
     * ��ȡ״̬�������Ϊ���ϵ�������ʱ����������״̬��
     * @return ״̬��
     */
    String getState();

    /**
     * �������Ϻţ���ֺ�����Ϻţ��������+��-��+·�ߴ������,��ͷ���ڶ��γ��ּ�β��"-1"��
     * @param materialNumber ���Ϻš�
     */
    void setMaterialNumber(String materialNumber);

    /**
     * ��ȡ���Ϻţ���ֺ�����Ϻţ��������+��-��+·�ߴ������,��ͷ���ڶ��γ��ּ�β��"-1"��
     * @return ���Ϻš�
     */
    String getMaterialNumber();

    /**
     * ����ת����ʶ������Ƿ�ת��Ϊ���ϣ�0��δת����1����ת����
     * @param splited ת����ʶ��
     */
    void setSplited(boolean splited);

    /**
     * ��ȡת����ʶ������Ƿ�ת��Ϊ���ϣ�0��δת����1����ת����
     * @return ת����ʶ��
     */
    boolean getSplited();

    /**
     * ���ò㼶״̬�������Ƿ񾭹���֣�0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
     * @param status �㼶״̬��
     */
    void setStatus(int status);

    /**
     * ��ȡ�㼶״̬�������Ƿ񾭹���֣�0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
     * @return �㼶״̬��
     */
    int getStatus();

    /**
     * ����·�ߴ��룬��������йص����ϲ�ֺ��·�ߴ��롣
     * @param routeCode ·�ߴ��롣
     */
    void setRouteCode(String routeCode);

    /**
     * ��ȡ·�ߴ�����������йص����ϲ�ֺ��·�ߴ��롣
     * @return ·�ߴ��롣
     */
    String getRouteCode();

    /**
     * ����·�ߣ��������Ա���һ���������������·��,��·��ʱ�÷ֺš�;���ָ���
     * @param route ·�ߡ�
     */
    void setRoute(String route);

    /**
     * ��ȡ·�ߣ��������Ա���һ���������������·��,��·��ʱ�÷ֺš�;���ָ���
     * @return ·�ߡ�
     */
    String getRoute();

    /**
     * ����������ƣ������Ϊ���ϵ��������,Ҳ��Ϊ���ϵ����ơ�
     * @param partName ������ơ�
     */
    void setPartName(String partName);

    /**
     * ��ȡ������ƣ������Ϊ���ϵ��������,Ҳ��Ϊ���ϵ����ơ�
     * @return ������ơ�
     */
    String getPartName();

    /**
     * ����Ĭ�ϵ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס���,ɢ����ֻʹ��"��",����Ϊ"��"��
     * @param defaultUnit Ĭ�ϵ�λ��
     */
    void setDefaultUnit(String defaultUnit);

    /**
     * ��ȡĬ�ϵ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס���,ɢ����ֻʹ��"��",����Ϊ"��"��
     * @return Ĭ�ϵ�λ��
     */
    String getDefaultUnit();

    /**
     * ������Դ��ö��ֵΪ�����ơ��⹺������,Ĭ��ֵΪ��������
     * @param producedBy ��Դ��
     */
    void setProducedBy(String producedBy);

    /**
     * ��ȡ��Դ��ö��ֵΪ�����ơ��⹺������,Ĭ��ֵΪ��������
     * @return ��Դ��
     */
    String getProducedBy();

    /**
     * �������ͣ�ö��ֵ�У�������ܳɡ���׼������Ʒ,���ɰ���:��ѹ�����������ܼ����ǽ���������׼����װ�ü���������߼��ܳɡ����͡����͡����ӡ�װ�䡢��ϴ�����ơ��������
     * @param partType ���͡�
     */
    void setPartType(String partType);

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
     * @param optionCode ѡװ�����롣
     */
    void setOptionCode(String optionCode);

    /**
     * ��ȡѡװ�����룬��PDM������ע������㲿����¼���롣
     * @return ѡװ�����롣
     */
    String getOptionCode();

    /**
     * ������ɫ����ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @param colorFlag ��ɫ����ʶ��
     */
    void setColorFlag(boolean colorFlag);

    /**
     * ��ȡ��ɫ����ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @return ��ɫ����ʶ��
     */
    boolean getColorFlag();
    
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
    
    /**
     * �����������ϱ�ʶ
     * @param flag �������ϱ�ʶ
     */
    public void setVirtualFlag(boolean flag);
    
    /**
     * ��ȡ�������ϱ�ʶ
     * @return �������ϱ�ʶ
     */
    public boolean getVirtualFlag();
    
    /**
     * ��ȡ��·�߱�ʶ
     * @return ��·�߱�ʶ
     */
    public boolean getIsMoreRoute();
    
    /**
     * ���ö�·�߱�ʶ
     * @param flag ��·�߱�ʶ
     */
    public void setIsMoreRoute(boolean flag);
}
