package com.faw_qm.gybom.util;
/**
 * SS1 ��ӡ����滻����id�� liuyuzhu 2017-12-18
 *
 */
public class BOMChangeContentBean
{
//  id   id
    private String id;
//  ���ͺ�  carModelCode
    private String carModelCode;
//  ����   dwbs
    private String dwbs;
//  ����   parentPartID
    private String parentPartID;
//  �Ӽ�   subPartID
    private String subPartID;
//  �޸�ǰ����    quantity1
    private String quantity1;
//  �޸ĺ�����    quantity2
    private String quantity2;
//  ��ɾ�ĵı�ǣ�A,D,U��    sign
    private String sign;
    //CCBegin SS1
//  ���滻����� tsubPartID
    private String tsubPartID;
    //CCEnd SS1
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getCarModelCode()
    {
        return carModelCode;
    }
    public void setCarModelCode(String carModelCode)
    {
        this.carModelCode = carModelCode;
    }
    public String getDwbs()
    {
        return dwbs;
    }
    public void setDwbs(String dwbs)
    {
        this.dwbs = dwbs;
    }
    public String getParentPartID()
    {
        return parentPartID;
    }
    public void setParentPartID(String parentPartID)
    {
        this.parentPartID = parentPartID;
    }
    public String getSubPartID()
    {
        return subPartID;
    }
    public void setSubPartID(String subPartID)
    {
        this.subPartID = subPartID;
    }
    public String getQuantity1()
    {
        return quantity1;
    }
    public void setQuantity1(String quantity1)
    {
        this.quantity1 = quantity1;
    }
    public String getQuantity2()
    {
        return quantity2;
    }
    public void setQuantity2(String quantity2)
    {
        this.quantity2 = quantity2;
    }
    public String getSign()
    {
        return sign;
    }
    public void setSign(String sign)
    {
        this.sign = sign;
    }
    //CCBegin SS1
    public String getTSubPartID()
    {
        return tsubPartID;
    }
    public void setTSubPartID(String tsubPartID)
    {
        this.tsubPartID = tsubPartID;
    }
    //CCEnd SS1
    
    
    
}
