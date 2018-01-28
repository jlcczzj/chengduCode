/** 生成程序MachineTechUseMController.java      1.1  2004/10/18
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capp.controller.TechUsageMaterial;

import com.faw_qm.cappclients.capp.controller.TechUsageMaterialLinkController;


/**
 * <p>Title: 机械加工工艺材料关联的控制类</p>
 * <p>Description:主要功能有处理关联面板,获得主要材料标识列 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 薛静
 * @version 1.0
 */

public class MachineTechUseMController extends TechUsageMaterialLinkController
{

    /**
     * 构造方法
     * @param type String 工艺种类
     */
    public MachineTechUseMController(String type)
    {
        super(type);
    }


    /**
     * 处理关联面板
     */
    public void handelCappAssociationsPanel()
    {
        super.handelCappAssociationsPanel();
        int[] rds =
                {
                3};
        //设置第3列显示JRadioButton
        cappAssociationsPanel.setRadionButtons(rds);
        int[] is =
                {
                0, 2, 3};
        //设置列可以编辑
        cappAssociationsPanel.setColsEnabled(is, true);

    }


    /**
     * 获得材料主要标识列
     * @return int
     */
    public int getMajorMCol()
    {
        return 3;
    }

}
