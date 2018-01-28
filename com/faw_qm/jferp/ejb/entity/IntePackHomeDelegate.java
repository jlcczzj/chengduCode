/**
 * 生成程序IntePackHomeDelegate.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoHomeDelegate;
import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class IntePackHomeDelegate implements BsoHomeDelegate
{
    /**
     * 构造函数。
     */
    public IntePackHomeDelegate()
    {
    }

    private IntePackHome home = null;

    /**
     * 初始化。
     * @param obj Object
     */
    public void init(Object obj)
    {
        if(!(obj instanceof IntePackHome))
        {
            Object[] objs = {obj.getClass(), "JFIntePackHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (IntePackHome) obj;
    }

    /**
     * 用于根据主键恢复对象。
     * @param bsoID String
     * @return BsoReference
     * @throws FinderException
     */
    public BsoReference findByPrimaryKey(String bsoID) throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }

    /**
     * 根据值对象建立业务对象。
     * @param info 值对象。
     * @return 创建成功的业务对象。
     * @throws CreateException
     */
    public BsoReference create(BaseValueIfc info) throws CreateException
    {
        return home.create(info);
    }

    /**
     * 根据值对象和指定时间戳建立业务对象。
     * @param info 值对象。
     * @param ct 创建时间戳。
     * @param mt 更新时间戳。
     * @return 创建成功的业务对象。
     * @throws CreateException
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt)
            throws CreateException
    {
        return home.create(info, ct, mt);
    }
}
