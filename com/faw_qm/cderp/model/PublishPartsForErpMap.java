/**
 * ���ɳ���MaterialStructureMap.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.model;

import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class PublishPartsForErpMap implements ObjectMappable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String PartBsoid;

    private String partState;

    private String zhunBei;

    private int zhunBei1;

//    private float quantity;
//
//    private String levelNumber;
//
//    private String defaultUnit;
//
//    private boolean optionFlag;

   

    


   

    /**
     * @return ���� parentPartNumber��
     */
    public String getPartBsoId()
    {
        return PartBsoid;
    }

    /**
     * @param parentPartNumber Ҫ���õ� parentPartNumber��
     */
    public void setPartBsoId(String PartBsoid)
    {
        this.PartBsoid = PartBsoid;
    }

    /**
     * @return ���� parentPartVersion��
     */
    public String getPartState()
    {
        return partState;
    }
    /**
     * @param parentPartVersion Ҫ���õ� parentPartVersion��
     */
    public void setPartState(String partState)
    {
        this.partState = partState;
    }

    /**
     * @return ���� quantity��
     */
    public String getZhunBei()
    {
        return zhunBei;
    }

    /**
     * @param quantity Ҫ���õ� quantity��
     */
    public void setZhunBei(String zhunBei)
    {
        this.zhunBei = zhunBei;
    }
    public int getZhunBei1()
    {
        return zhunBei1;
    }

    /**
     * @param quantity Ҫ���õ� quantity��
     */
    public void setZhunBei1(int zhunBei1)
    {
        this.zhunBei1 = zhunBei1;
    }
}
