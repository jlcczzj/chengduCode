/**
 * ���ɳ���MaterialStructureMap.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.model;

import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class RouteAndBomYiQiMap implements ObjectMappable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String partNumber;

    private String partVersion;

    private String routeTZSNumber;
    private String zhunBei;

    private String zhunBei1;

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
    public String getPartNumber()
    {
        return partNumber;
    }

    /**
     * @param parentPartNumber Ҫ���õ� parentPartNumber��
     */
    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
    }

    /**
     * @return ���� parentPartVersion��
     */
    public String getPartVersion()
    {
        return partVersion;
    }
    public void setRouteTZSNumber(String routeTZSNumber)
    {
        this.routeTZSNumber = routeTZSNumber;
    }

    /**
     * @return ���� parentPartVersion��
     */
    public String getRouteTZSNumber()
    {
        return routeTZSNumber;
    }
    /**
     * @param parentPartVersion Ҫ���õ� parentPartVersion��
     */
    public void setPartVersion(String partVersion)
    {
        this.partVersion = partVersion;
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
    public String getZhunBei1()
    {
        return zhunBei1;
    }

    /**
     * @param quantity Ҫ���õ� quantity��
     */
    public void setZhunBei1(String zhunBei1)
    {
        this.zhunBei1 = zhunBei1;
    }
}
