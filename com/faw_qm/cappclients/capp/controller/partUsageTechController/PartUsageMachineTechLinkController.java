/** ���ɳ���PartUsageMachineTechLinkController.java	1.1  2004/08/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.cappclients.capp.controller.partUsageTechController;

import com.faw_qm.cappclients.capp.controller.PartUsageTechLinkController;


/**
 * <p>Title:���ʹ�û�е�ӹ����յĹ���������</p>
 * <p>Description: ��Ҫ�����еõ������Ҫ��ʶ����,������Ҫ��ʶ����,��ѡ������</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: һ������</p>
 * @author Ѧ��
 * @version 1.0
 */

public class PartUsageMachineTechLinkController extends
        PartUsageTechLinkController
{

    /**
     * ���췽��
     * @param type String ��������
     */
    public PartUsageMachineTechLinkController(String type)
    {
        super(type);
    }


    /**
     * ��õ�ѡ��
     * @return int[]
     */
    public int[] getRadioButtonCols()
    {
        return new int[]
                {4};//CC by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
    }


    /**
     * ��������Ҫ��ʶ������
     * @return int �����Ҫ��ʶ������
     */
    public int getMajorpartMarkColum()
    {
        return 4;//CC by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
    }


    /**  
     * ����,����ʵ��,��е�ӹ����յ���������޼���
     */
    public void calculate()
    {
    }


    /**
     * ��ù�����Ҫ��ʶ������
     * @return int ������Ҫ��ʶ������
     */
    public int getmMajortechnicsMarkColum()
    {
        return 5;//CC by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
    }

}
