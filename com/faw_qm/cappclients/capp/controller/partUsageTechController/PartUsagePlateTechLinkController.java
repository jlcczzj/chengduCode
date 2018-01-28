/** 生成程序PartUsageDrillTechLinkController.java	1.1  2004/08/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capp.controller.partUsageTechController;

import javax.swing.JOptionPane;

import com.faw_qm.cappclients.capp.controller.PartUsageTechLinkController;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.util.RegistryException;


/**
 * <p>Title:零件使用电镀工艺关联控制类 </p>
 * <p>Description:主要功能有得到零件主要标识列数,工艺主要标识列数,单选框列数
 * ,计算 </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 薛静
 * @version 1.0
 */

public class PartUsagePlateTechLinkController extends
        PartUsageTechLinkController
{

    /**
     * 构造方法
     * @param p0 String 工艺种类
     */
    public PartUsagePlateTechLinkController(String type)
    {
        super(type);
    }


    /**
     * 获得单选框列数
     * @return int[] 单选框列数
     */
    public int[] getRadioButtonCols()
    {
        return new int[]
                {3};
    }


    /**
     * 获得零件主要标识列数
     * @return int 零件主要标识列数
     */
    public int getMajorpartMarkColum()
    {
        return 3;
    }


    /**
     * 计算
     */
    public void calculate()
    {
        QMPartIfc part = null;
        //得到选择的零部件
        try
        {
            part = (QMPartIfc) cappAssociationsPanel.getSelectedObject();
            if (part == null)
            {
                return;
            }
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
        }
        catch (RegistryException ex)
        {
            ex.printStackTrace();
        }
        //则调服务计算每车件数
        Class[] paraClass =
                {QMPartIfc.class};
        Object[] paraObj =
                {part};

        Float num = null;
        try
        {
            num = (Float) CappClientHelper.useServiceMethod(
                    "StandardCappService",
                    "calculatePiecePerMobile", paraClass, paraObj);
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(panel.getParentJFrame(),
                                          ex.getClientMessage(), title,
                                          JOptionPane.WARNING_MESSAGE);

        }
        if (num != null)
        {
            int row = cappAssociationsPanel.getSelectedRow();
            //设置每车件数值
            cappAssociationsPanel.setCellTextValue(row, 6, num.toString());
        }

    }


    /**
     * 获得工艺主要标识列数
     * @return int 工艺主要标识列数
     */
    public int getmMajortechnicsMarkColum()
    {
        return 4;
    }

}
