/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司范围内,使用本程序
 * 保留所有权利
 * 生成时间 2003/09/02
 */
package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.*;


/**
 * 材料文档关联Home代理类
 * @version 1.0
 * @author 罗桥
 */
public class CompletDocLinkHomeDelegate implements BsoHomeDelegate
{

    //home接口
    private CompletDocLinkHome home = null;


    /**
     * 初始化home接口
     * @param obj
     */
    public void init(Object obj)
    {
        if (!(obj instanceof CompletDocLinkHome))
        {
            Object[] objs =
                    {
                    obj.getClass(), "MaterialDocLinkHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkMaterial", "70", objs);
        }
        home = (CompletDocLinkHome) obj;
    }


    /**
     * 用于根据主键恢复对象
     */
    public BsoReference findByPrimaryKey(String bsoID)
            throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }


    /**
     * 根据值对象建立业务对象
     */
    public BsoReference create(BaseValueIfc info)
            throws CreateException
    {
        return home.create(info);
    }


    /**
     * 根据值对象和指定时间戳建立业务对象
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt)
            throws
            CreateException
    {
        return home.create(info, ct, mt);
    }

}
