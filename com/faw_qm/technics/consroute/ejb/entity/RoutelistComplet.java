/**
 * 生成程序 RoutelistComplet
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.technics.consroute.ejb.entity;

import com.faw_qm.enterprise.ejb.entity.Managed;
//CCBegin by wanghonglian 2008-07-30 
//注释掉不使用的导入路径
//import com.faw_qm.extend.ejb.entity.ExtendAttried;
//CCEnd by wanghonglian 2008-07-30
import com.faw_qm.lock.ejb.entity.Lock;
import com.faw_qm.unique.ejb.entity.UniqueIdentified;
import com.faw_qm.content.ejb.service.FormatContentHolder;


/**
 * 艺毕通知书
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw_qm</p>
 * @author 管春元
 * @version 1.0
 */
public interface RoutelistComplet extends Managed, UniqueIdentified,FormatContentHolder,
         Lock
{
       public String getCompletNum();


       public void setCompletNum(String num);


       public String getCompletName();


       public void setCompletName(String name);


       public String getCompletType();


       public void setCompletType(String type);


       public String getCompletNote();


       public void setCompletNote(String note);


       public String getCompletState();

       public void setCompletState(String state);

}
