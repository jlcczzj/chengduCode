/**
 * ���ɳ���PromulgateNotifyIfc.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.model;

import com.faw_qm.acl.ejb.entity.AccessControlled;
import com.faw_qm.domain.model.DomainAdministeredIfc;
import com.faw_qm.folder.model.FolderedIfc;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.Creator;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.notify.model.NotifiableIfc;

/**
 * <p>Title: ����֪ͨ��ӿ�</p>
 * <p>Description: ����֪ͨ��ӿ�</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public interface PromulgateNotifyIfc extends BaseValueIfc,
        DomainAdministeredIfc, AccessControlled, LifeCycleManagedIfc,
        NotifiableIfc, Creator, FolderedIfc
{
    /**
     * ����֪ͨ��ʶ���
     * @return String
     */
    public String getPromulgateNotifyNumber();

    public void setPromulgateNotifyNumber(String number);

    /**
     * ����֪ͨ����
     * @return String
     */
    public String getPromulgateNotifyName();

    public void setPromulgateNotifyName(String name);

    /**
     * ����֪ͨ���
     * @return String
     */
    public String getPromulgateNotifyType();

    public void setPromulgateNotifyType(String type);

    /**
     * ���ñ��
     * @return String
     */
    public String getPromulgateNotifyFlag();

    public void setPromulgateNotifyFlag(String flag);

    /**
     * ����˵��
     * @return String
     */
    public String getPromulgateNotifyDescription();

    public void setPromulgateNotifyDescription(String description);

    public String getHasPromulgate();

    public void setHasPromulgate(String has);

    public String getCreator();

    public void setCreator(String creator);

    public String getIdentity();
}
