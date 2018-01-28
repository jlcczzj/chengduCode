/**
 * 生成程序MaterialSplitServiceHomeDelegate.java 1.0              2007-10-7
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
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public class MaterialSplitServiceHomeDelegate implements ServiceHomeDelegate
{
    public MaterialSplitServiceHomeDelegate()
    {
    }

    private MaterialSplitServiceHome home = null;

    /**
     * 实现HomeDelegate接口方法。
     * @param obj 初始化对象
     */
    public void init(Object obj)
    {
        if(!(obj instanceof MaterialSplitServiceHome))
        {
            Object[] objs = {obj.getClass(), "CDMaterialSplitServiceHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (MaterialSplitServiceHome) obj;
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
