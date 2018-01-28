/** 生成程序 PartBaselineConfigSpec.java    1.0    2003/02/18
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.part.util;

import java.io.Serializable;

import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.framework.exceptions.QMException;


/**
 * 基准线配置项。
 * PartBaselineConfigSpec不是一个持久化数据，但是它封装了一个BaselineIfc对象属性，
 * 这个对象必须是持久化保存的。
 * @author 吴先超
 * @version 1.0
 */

public class PartBaselineConfigSpec implements Serializable
{
    private BaselineIfc baselineIfc;

    /**
     * 设置序列号。
     */
    //CCBegin by zhangq 20080626
    //和解放的序列化ID不一致，为了确保对象能正确的序列化，修改序列化ID。
    //static final long serialVersionUID = 1L;
    static final long serialVersionUID = 1406440305523123508L;
    //CCEnd by zhangq 20080626

    /**
     * 构造函数。
     */
    public PartBaselineConfigSpec()
    {

    }


    /**
     * 构造函数。含有一个参数。
     * setBaselineIfc(tempIfc);
     * @param tempIfc1 BaselineIfc
     */
    public PartBaselineConfigSpec(BaselineIfc tempIfc1)
    {
        try
        {
            setBaselineIfc(tempIfc1);
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 设置基准线。
     * 提示信息：基准线不能为空，因为它是一个必须的属性。
     * @param tempIfc BaselineIfc
     * @throws QMException
     */
    public void setBaselineIfc(BaselineIfc tempIfc)
            throws QMException
    {
        if (tempIfc == null)
        {
            Object[] obj =
                           {"BaselineIfc"};
            throw new QMException("com.faw_qm.part.util.PartResource",
                                  "CP00001", obj);
        }
        baselineIfc = tempIfc;
    }


    /**
     * 获取基准线。
     * @return BaselineIfc
     */
    public BaselineIfc getBaselineIfc()
    {
        return baselineIfc;
    }
}
