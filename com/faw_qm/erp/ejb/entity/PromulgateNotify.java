/**
 * 生成程序PromulgateNotify.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
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
 * <p>Title: 采用通知书接口</p>
 * <p>Description: 采用通知书接口</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public interface PromulgateNotify extends BsoReference, AccessControlled,
        DomainAdministered, LifeCycleManaged, Notifiable, Creator, Foldered
{
    /**
     * 采用通知标识编号
     * @return String
     */
    public abstract String getPromulgateNotifyNumber();

    public abstract void setPromulgateNotifyNumber(String number);

    /**
     * 采用通知名称
     * @return String
     */
    public abstract String getPromulgateNotifyName();

    public abstract void setPromulgateNotifyName(String name);

    /**
     * 采用通知类别
     * @return String
     */
    public abstract String getPromulgateNotifyType();

    public abstract void setPromulgateNotifyType(String type);

    /**
     * 采用标记
     * @return String
     */
    public abstract String getPromulgateNotifyFlag();

    public abstract void setPromulgateNotifyFlag(String flag);

    /**
     * 采用说明
     * @return String
     */
    public abstract String getPromulgateNotifyDescription();

    public abstract void setPromulgateNotifyDescription(String description);

    public abstract String getCreator();

    public abstract void setCreator(String creator);

    public abstract String getHasPromulgate();

    public abstract void setHasPromulgate(String has);
}
