/**
 * 生成程序PromulgateNotifyServiceHomeDelegate.java   1.0              2006-11-6
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
 * <p>Title:采用通知服务代理实现类 </p>
 * <p>Description: 采用通知服务代理实现类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public class PromulgateNotifyServiceHomeDelegate implements ServiceHomeDelegate
{
    public PromulgateNotifyServiceHomeDelegate()
    {
    }

    private PromulgateNotifyServiceHome home = null;

    /**
     * 实现HomeDelegate接口方法。用一个对象初始化标准文件服务Home接口
     * @param obj 初始化对象
     */
    public void init(Object obj)
    {
        if(!(obj instanceof PromulgateNotifyServiceHome))
        {
            Object[] objs = {obj.getClass(), "PromulgateNotifyServiceHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (PromulgateNotifyServiceHome) obj;
    }

    /**
     * 实现ServiceHomeDelegate接口方法。创建标准文件服务Bean实例
     * @return 标准文件服务Bean实例
     * @throws CreateException
     */
    public BaseService create() throws CreateException
    {
        return home.create();
    }
}
