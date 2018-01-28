package com.faw_qm.cappclients.capp.controller.partUsageTechController;

import javax.swing.JOptionPane;

import com.faw_qm.cappclients.capp.controller.PartUsageTechLinkController;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.util.RegistryException;

public class PartUsageTechLinkControllerForCd extends
PartUsageTechLinkController{
	
	  /**
     * 构造方法
     * @param p0 String 工艺种类
     */
    public PartUsageTechLinkControllerForCd(String type)
    {
        super(type);
    }


    /**
     * 获得单选框的列数
     * @return int[] 单选框的列数
     */
    public int[] getRadioButtonCols()
    {
        return new int[]
                {3,5};
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
