/**
 * ���ɳ���PromulgateNotify.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.ejb.entity;

import com.faw_qm.acl.ejb.entity.AccessControlled;
import com.faw_qm.domain.ejb.entity.DomainAdministered;
import com.faw_qm.folder.Foldered;
import com.faw_qm.framework.service.BsoReference;
import com.faw_qm.framework.service.Creator;
import com.faw_qm.lifecycle.ejb.entity.LifeCycleManaged;
import com.faw_qm.notify.ejb.entity.Notifiable;

/**
 * <p>Title: ����֪ͨ��ӿ�</p>
 * <p>Description: ����֪ͨ��ӿ�</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public interface PromulgateNotify extends BsoReference, AccessControlled,
        DomainAdministered, LifeCycleManaged, Notifiable, Creator, Foldered
{
    /**
     * ����֪ͨ��ʶ���
     * @return String
     */
    public abstract String getPromulgateNotifyNumber();

    public abstract void setPromulgateNotifyNumber(String number);

    /**
     * ����֪ͨ����
     * @return String
     */
    public abstract String getPromulgateNotifyName();

    public abstract void setPromulgateNotifyName(String name);

    /**
     * ����֪ͨ���
     * @return String
     */
    public abstract String getPromulgateNotifyType();

    public abstract void setPromulgateNotifyType(String type);

    /**
     * ���ñ��
     * @return String
     */
    public abstract String getPromulgateNotifyFlag();

    public abstract void setPromulgateNotifyFlag(String flag);

    /**
     * ����˵��
     * @return String
     */
    public abstract String getPromulgateNotifyDescription();

    public abstract void setPromulgateNotifyDescription(String description);

    public abstract String getCreator();

    public abstract void setCreator(String creator);

    public abstract String getHasPromulgate();

    public abstract void setHasPromulgate(String has);
}
