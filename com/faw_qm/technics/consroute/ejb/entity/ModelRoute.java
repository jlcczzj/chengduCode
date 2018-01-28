/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/22 徐春英      原因:增加预留属性
 * CR2 2011/12/22 徐春英       原因：增加产品信息和路线信息
 * CR3 2012/01/10 徐春英      原因：增加路线生效时间和失效时间
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.faw_qm.domain.ejb.entity.DomainAdministered;
import com.faw_qm.framework.service.BinaryLink;

/**
 * 典型工艺
 * @author 吕航
 * @version 1.0 2012.01.13
 */
public interface ModelRoute extends DomainAdministered,BinaryLink
{
}
