/**
 * 生成程序MaterialStructureMap.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.model;

import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
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
     * @return 返回 parentPartNumber。
     */
    public String getPartBsoId()
    {
        return PartBsoid;
    }

    /**
     * @param parentPartNumber 要设置的 parentPartNumber。
     */
    public void setPartBsoId(String PartBsoid)
    {
        this.PartBsoid = PartBsoid;
    }

    /**
     * @return 返回 parentPartVersion。
     */
    public String getPartState()
    {
        return partState;
    }
    /**
     * @param parentPartVersion 要设置的 parentPartVersion。
     */
    public void setPartState(String partState)
    {
        this.partState = partState;
    }

    /**
     * @return 返回 quantity。
     */
    public String getZhunBei()
    {
        return zhunBei;
    }

    /**
     * @param quantity 要设置的 quantity。
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
     * @param quantity 要设置的 quantity。
     */
    public void setZhunBei1(int zhunBei1)
    {
        this.zhunBei1 = zhunBei1;
    }
}
