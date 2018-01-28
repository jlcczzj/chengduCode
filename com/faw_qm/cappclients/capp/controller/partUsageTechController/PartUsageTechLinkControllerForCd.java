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
     * ���췽��
     * @param p0 String ��������
     */
    public PartUsageTechLinkControllerForCd(String type)
    {
        super(type);
    }


    /**
     * ��õ�ѡ�������
     * @return int[] ��ѡ�������
     */
    public int[] getRadioButtonCols()
    {
        return new int[]
                {3,5};
    }


    /**
     * ��������Ҫ��ʶ����
     * @return int �����Ҫ��ʶ����
     */
    public int getMajorpartMarkColum()
    {
        return 3;
    }


    /**
     * ����
     */
    public void calculate()
    {
        QMPartIfc part = null;
        //�õ�ѡ����㲿��
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
        //����������ÿ������
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
            //����ÿ������ֵ
            cappAssociationsPanel.setCellTextValue(row, 6, num.toString());
        }

    }


    /**
     * ��ù�����Ҫ��ʶ����
     * @return int ������Ҫ��ʶ����
     */
    public int getmMajortechnicsMarkColum()
    {
        return 4;
    }

}
