/**
 * 生成程序IntePackServiceHomeDelegate.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.ejb.service;

import javax.ejb.CreateException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.ServiceHomeDelegate;

/**
 * <p>Title: 集成包服务代理实现类。</p>
 * <p>Description: 集成包服务代理实现类。</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class IntePackServiceHomeDelegate implements ServiceHomeDelegate
{
    public IntePackServiceHomeDelegate()
    {
    }

    private IntePackServiceHome home = null;

    /**
     * 实现HomeDelegate接口方法。
     * @param obj 初始化对象
     */
    public void init(Object obj)
    {
        if(!(obj instanceof IntePackServiceHome))
        {
            Object[] objs = {obj.getClass(), "IntePackServiceHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (IntePackServiceHome) obj;
    }

    /**
     * 实现ServiceHomeDelegate接口方法。
     * @return 标准文件服务Bean实例
     * @throws CreateException
     */
    public BaseService create() throws CreateException
    {
        return home.create();
    }
}
