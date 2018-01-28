/**
 * 生成程序MaterialStructureEJB.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.ejb.entity;

import javax.ejb.CreateException;
import com.faw_qm.jferp.model.PublishPartsForErpInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public abstract class PublishPartsForErpEJB extends BsoReferenceEJB
{
    /**
     * 设置父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @param parentPartNumber 父件号。
     */
    public abstract void setPartBsoId(String partBsoid);

    /**
     * 获取父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @return 父件号。
     */
    public abstract String getPartBsoId();
    public abstract void setPartState(String partState);

    /**
     * 获取父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @return 父件号。
     */
    public abstract String getPartState();

    
    public abstract void setZhunBei(String zhunBei);

    /**
     * 获取父物料，记录拆分后的物料父项号。
     * @return 父物料。
     */
    public abstract String getZhunBei();

    /**
     * 设置子物料，记录拆分后的物料子项号。
     * @param childNumber 子物料。
     */
    public abstract void setZhunBei1(int zhunBei1);

    /**
     * 获取子物料，记录拆分后的物料子项号。
     * @return 子物料。
     */
    public abstract int getZhunBei1();

    
    
    public BaseValueIfc getValueInfo() throws QMException
    {
    	PublishPartsForErpInfo info = new PublishPartsForErpInfo();
        setValueInfo(info);
        return info;
    }

    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        PublishPartsForErpInfo info1 = (PublishPartsForErpInfo) info;
        info1.setPartBsoId(getPartBsoId());
        info1.setPartState(getPartState());
        info1.setZhunBei(getZhunBei());
        info1.setZhunBei1(getZhunBei1());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        PublishPartsForErpInfo info1 = (PublishPartsForErpInfo) info;
        setPartBsoId(info1.getPartBsoId());
        setPartState(info1.getPartState());
        setZhunBei(info1.getZhunBei());
        setZhunBei1(info1.getZhunBei1());
    }

    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        PublishPartsForErpInfo info1 = (PublishPartsForErpInfo) info;
        setPartBsoId(info1.getPartBsoId());
        setPartState(info1.getPartState());
        setZhunBei(info1.getZhunBei());
        setZhunBei1(info1.getZhunBei1());
    }

    public String getBsoName()
    {
        return "JFPublishPartsForErp";
    }
}
