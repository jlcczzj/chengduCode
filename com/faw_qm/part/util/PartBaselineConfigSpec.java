/** ���ɳ��� PartBaselineConfigSpec.java    1.0    2003/02/18
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.part.util;

import java.io.Serializable;

import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.framework.exceptions.QMException;


/**
 * ��׼�������
 * PartBaselineConfigSpec����һ���־û����ݣ���������װ��һ��BaselineIfc�������ԣ�
 * �����������ǳ־û�����ġ�
 * @author ���ȳ�
 * @version 1.0
 */

public class PartBaselineConfigSpec implements Serializable
{
    private BaselineIfc baselineIfc;

    /**
     * �������кš�
     */
    //CCBegin by zhangq 20080626
    //�ͽ�ŵ����л�ID��һ�£�Ϊ��ȷ����������ȷ�����л����޸����л�ID��
    //static final long serialVersionUID = 1L;
    static final long serialVersionUID = 1406440305523123508L;
    //CCEnd by zhangq 20080626

    /**
     * ���캯����
     */
    public PartBaselineConfigSpec()
    {

    }


    /**
     * ���캯��������һ��������
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
     * ���û�׼�ߡ�
     * ��ʾ��Ϣ����׼�߲���Ϊ�գ���Ϊ����һ����������ԡ�
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
     * ��ȡ��׼�ߡ�
     * @return BaselineIfc
     */
    public BaselineIfc getBaselineIfc()
    {
        return baselineIfc;
    }
}
