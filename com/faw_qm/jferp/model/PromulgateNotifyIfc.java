/**
 * 生成程序PromulgateNotifyIfc.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
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
 * <p>Title: 采用通知书接口</p>
 * <p>Description: 采用通知书接口</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public interface PromulgateNotifyIfc extends BaseValueIfc,
        DomainAdministeredIfc, AccessControlled, LifeCycleManagedIfc,
        NotifiableIfc, Creator, FolderedIfc
{
    /**
     * 采用通知标识编号
     * @return String
     */
    public String getPromulgateNotifyNumber();

    public void setPromulgateNotifyNumber(String number);

    /**
     * 采用通知名称
     * @return String
     */
    public String getPromulgateNotifyName();

    public void setPromulgateNotifyName(String name);

    /**
     * 采用通知类别
     * @return String
     */
    public String getPromulgateNotifyType();

    public void setPromulgateNotifyType(String type);

    /**
     * 采用标记
     * @return String
     */
    public String getPromulgateNotifyFlag();

    public void setPromulgateNotifyFlag(String flag);

    /**
     * 采用说明
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
