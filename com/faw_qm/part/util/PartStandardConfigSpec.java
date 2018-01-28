/** ���ɳ��� PartStandardConfigSpec.java    1.0    2003/02/18
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.part.util;

import java.io.Serializable;

import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.viewmanage.model.ViewObjectIfc;


/*
 * ��׼�����
 * PartStandardConfigSpec����Ҳ����һ���־û����ݣ�����װһ����������״̬��
 * ����һ��ö�����ͣ�workingIncluded��һ��booleanֵ��viewObjectIfc��һ����ͼ����
 * ��Ӧ�־û����ݡ�
 */

/**
 * ��׼�����
 * @author ���ȳ�
 * @version 1.0
 */

public class PartStandardConfigSpec implements Serializable
{
    private LifeCycleState lifeCycleState; //��������״̬��
    private boolean workingIncluded = true;
    private ViewObjectIfc viewObjectIfc;
    //CCBegin by zhangq 20080626
    //�ͽ�ŵ����л�ID��һ�£�Ϊ��ȷ����������ȷ�����л����޸����л�ID��
    //static final long serialVersionUID = 1L;
    static final long serialVersionUID = -1047345799102852976L;
    //CCEnd by zhangq 20080626


    /**
     * ���캯��,Ϊ�ա�
     */
    public PartStandardConfigSpec()
    {

    }


    /**
     * ������������״̬��
     * @param state :LifeCycleState
     */
    public void setLifeCycleState(LifeCycleState state)
    {
        lifeCycleState = state;
    }


    /**
     * ��ȡ��������״̬��
     * @return LifeCycleState
     */
    public LifeCycleState getLifeCycleState()
    {
        return lifeCycleState;
    }


    /**
     * �����Ƿ��ڸ����ļ����С�
     * @param flag boolean
     */
    public void setWorkingIncluded(boolean flag)
    {
        workingIncluded = flag;
    }


    /**
     * ��ȡ�Ƿ��ڸ����ļ����С�
     * @return boolean
     */
    public boolean getWorkingIncluded()
    {
        return workingIncluded;
    }


    /**
     * ������ͼ��
     * viewIfc������һ���־û���ֵ����
     * @param viewIfc ViewObjectIfc
     */
    public void setViewObjectIfc(ViewObjectIfc viewIfc)
    {
        viewObjectIfc = viewIfc;
    }


    /**
     * ��ȡ��ͼ��
     * @return ViewObjectIfc
     */
    public ViewObjectIfc getViewObjectIfc()
    {
        return viewObjectIfc;
    }
}
