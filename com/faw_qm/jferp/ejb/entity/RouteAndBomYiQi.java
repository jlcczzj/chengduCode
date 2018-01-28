/**
 * 生成程序MaterialStructure.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: 物料结构。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public interface RouteAndBomYiQi extends BsoReference
{
    /**
     * 设置父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @param parentPartNumber 父件号。
     */
    void setPartNumber(String partNumber);

    /**
     * 获取父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @return 父件号。
     */
    String getPartNumber();
    void setPartVersion(String partVersion);

    /**
     * 获取父件版本，父物料拆分前零件版本，用于检查结构是否更改。
     * @return 父件版本。
     */
    String getPartVersion();

    /**
     * 设置父物料，记录拆分后的物料父项号。
     * @param parentNumber 父物料。
     */
    void setRouteTZSNumber(String routeTZSNumber);

    /**
     * 获取父物料，记录拆分后的物料父项号。
     * @return 父物料。
     */
    String getRouteTZSNumber();

    /**
     * 设置子物料，记录拆分后的物料子项号。
     * @param childNumber 子物料。
     */
    void setZhunBei(String zhunBei);

    /**
     * 获取子物料，记录拆分后的物料子项号。
     * @return 子物料。
     */
    String getZhunBei();
    void setZhunBei1(String zhunBei1);

    /**
     * 获取父件号，父物料拆分前的零件号，用于检查结构是否更改。
     * @return 父件号。
     */
    String getZhunBei1();

}
