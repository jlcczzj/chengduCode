/** ���ɳ���MachineTechUseMController.java      1.1  2004/10/18
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capp.controller.TechUsageMaterial;

import com.faw_qm.cappclients.capp.controller.TechUsageMaterialLinkController;


/**
 * <p>Title: ��е�ӹ����ղ��Ϲ����Ŀ�����</p>
 * <p>Description:��Ҫ�����д���������,�����Ҫ���ϱ�ʶ�� </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: һ������</p>
 * @author Ѧ��
 * @version 1.0
 */

public class MachineTechUseMController extends TechUsageMaterialLinkController
{

    /**
     * ���췽��
     * @param type String ��������
     */
    public MachineTechUseMController(String type)
    {
        super(type);
    }


    /**
     * ����������
     */
    public void handelCappAssociationsPanel()
    {
        super.handelCappAssociationsPanel();
        int[] rds =
                {
                3};
        //���õ�3����ʾJRadioButton
        cappAssociationsPanel.setRadionButtons(rds);
        int[] is =
                {
                0, 2, 3};
        //�����п��Ա༭
        cappAssociationsPanel.setColsEnabled(is, true);

    }


    /**
     * ��ò�����Ҫ��ʶ��
     * @return int
     */
    public int getMajorMCol()
    {
        return 3;
    }

}
