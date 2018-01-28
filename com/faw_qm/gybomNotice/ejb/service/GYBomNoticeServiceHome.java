/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间: Tue Dec 09 12:06:09 CST 2006
 */

package com.faw_qm.gybomNotice.ejb.service;

import javax.ejb.CreateException;

import com.faw_qm.framework.service.BaseServiceHome;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 一汽启明</p>
 * @author 文柳
 * @version 1.0
 */

public interface GYBomNoticeServiceHome extends BaseServiceHome
{

    /**
     * 创建服务接口
     * @return RationService 服务接口实例
     */
    public GYBomNoticeService create()
            throws CreateException;


}
