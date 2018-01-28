/** 生成程序PartUsageMachineTechLinkController.java	1.1  2004/08/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.cappclients.capp.controller.partUsageTechController;

import com.faw_qm.cappclients.capp.controller.PartUsageTechLinkController;


/**
 * <p>Title:零件使用机械加工工艺的关联控制类</p>
 * <p>Description: 主要功能有得到零件主要标识列数,工艺主要标识列数,单选框列数</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 薛静
 * @version 1.0
 */

public class PartUsageMachineTechLinkController extends
        PartUsageTechLinkController
{

    /**
     * 构造方法
     * @param type String 工艺种类
     */
    public PartUsageMachineTechLinkController(String type)
    {
        super(type);
    }


    /**
     * 获得单选框
     * @return int[]
     */
    public int[] getRadioButtonCols()
    {
        return new int[]
                {4};//CC by liuzc 2009-11-29 原因：解放系统升级。
    }


    /**
     * 获得零件主要标识的列数
     * @return int 零件主要标识的列数
     */
    public int getMajorpartMarkColum()
    {
        return 4;//CC by liuzc 2009-11-29 原因：解放系统升级。
    }


    /**  
     * 计算,不用实现,机械加工工艺的零件关联无计算
     */
    public void calculate()
    {
    }


    /**
     * 获得工艺主要标识的列数
     * @return int 工艺主要标识的列数
     */
    public int getmMajortechnicsMarkColum()
    {
        return 5;//CC by liuzc 2009-11-29 原因：解放系统升级。
    }

}
