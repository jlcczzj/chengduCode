/**
 * ���ɳ��� RoutelistComplet
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.technics.consroute.ejb.entity;

import com.faw_qm.enterprise.ejb.entity.Managed;
//CCBegin by wanghonglian 2008-07-30 
//ע�͵���ʹ�õĵ���·��
//import com.faw_qm.extend.ejb.entity.ExtendAttried;
//CCEnd by wanghonglian 2008-07-30
import com.faw_qm.lock.ejb.entity.Lock;
import com.faw_qm.unique.ejb.entity.UniqueIdentified;
import com.faw_qm.content.ejb.service.FormatContentHolder;


/**
 * �ձ�֪ͨ��
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw_qm</p>
 * @author �ܴ�Ԫ
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
