/** 生成程序时间 2016-5-16
  * 版本         1.0
  * 作者         doc
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  */
package com.faw_qm.gybom.ejb.service;

import com.faw_qm.framework.service.BaseServiceHome;
import javax.ejb.*;

/**
  * 工艺BOM管理平台服务的Home接口。
  * @author  刘楠  修改时间  2016-5-16
  */
public interface GYBomServiceHome extends BaseServiceHome
{
	
	public GYBomService create() throws CreateException;
	
}
