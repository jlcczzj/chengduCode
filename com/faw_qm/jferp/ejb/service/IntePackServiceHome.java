/**
 * 生成程序IntePackServiceHome.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.ejb.service;

import javax.ejb.CreateException;
import com.faw_qm.framework.service.BaseServiceHome;

/**
 * <p>Title: 集成包本地服务接口。</p>
 * <p>Description: 集成包本地服务接口</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public interface IntePackServiceHome extends BaseServiceHome
{
    public IntePackService create() throws CreateException;
}
