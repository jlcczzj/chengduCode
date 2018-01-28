package com.faw_qm.gybom.util;
/**
 * SS1 添加“被替换件的id” liuyuzhu 2017-12-18
 *
 */
public class BOMChangeContentBean
{
//  id   id
    private String id;
//  车型号  carModelCode
    private String carModelCode;
//  工厂   dwbs
    private String dwbs;
//  父件   parentPartID
    private String parentPartID;
//  子件   subPartID
    private String subPartID;
//  修改前数量    quantity1
    private String quantity1;
//  修改后数量    quantity2
    private String quantity2;
//  增删改的标记（A,D,U）    sign
    private String sign;
    //CCBegin SS1
//  被替换的零件 tsubPartID
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
