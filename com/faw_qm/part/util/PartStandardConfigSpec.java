/** 生成程序 PartStandardConfigSpec.java    1.0    2003/02/18
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.part.util;

import java.io.Serializable;

import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.viewmanage.model.ViewObjectIfc;


/*
 * 标准配置项。
 * PartStandardConfigSpec对象也不是一个持久化数据，它封装一个生命周期状态，
 * 他是一个枚举类型，workingIncluded是一个boolean值，viewObjectIfc是一个视图对象，
 * 对应持久化数据。
 */

/**
 * 标准配置项。
 * @author 吴先超
 * @version 1.0
 */

public class PartStandardConfigSpec implements Serializable
{
    private LifeCycleState lifeCycleState; //生命周期状态。
    private boolean workingIncluded = true;
    private ViewObjectIfc viewObjectIfc;
    //CCBegin by zhangq 20080626
    //和解放的序列化ID不一致，为了确保对象能正确的序列化，修改序列化ID。
    //static final long serialVersionUID = 1L;
    static final long serialVersionUID = -1047345799102852976L;
    //CCEnd by zhangq 20080626


    /**
     * 构造函数,为空。
     */
    public PartStandardConfigSpec()
    {

    }


    /**
     * 设置生命周期状态。
     * @param state :LifeCycleState
     */
    public void setLifeCycleState(LifeCycleState state)
    {
        lifeCycleState = state;
    }


    /**
     * 获取生命周期状态。
     * @return LifeCycleState
     */
    public LifeCycleState getLifeCycleState()
    {
        return lifeCycleState;
    }


    /**
     * 设置是否在个人文件夹中。
     * @param flag boolean
     */
    public void setWorkingIncluded(boolean flag)
    {
        workingIncluded = flag;
    }


    /**
     * 获取是否在个人文件夹中。
     * @return boolean
     */
    public boolean getWorkingIncluded()
    {
        return workingIncluded;
    }


    /**
     * 设置视图。
     * viewIfc必须是一个持久化的值对象
     * @param viewIfc ViewObjectIfc
     */
    public void setViewObjectIfc(ViewObjectIfc viewIfc)
    {
        viewObjectIfc = viewIfc;
    }


    /**
     * 获取视图。
     * @return ViewObjectIfc
     */
    public ViewObjectIfc getViewObjectIfc()
    {
        return viewObjectIfc;
    }
}
