/**
 * <p>Title: 工艺数据服务代理实现类。</p>
 * <p>Description: 工艺数据服务代理实现类。</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 徐春英
 * @version 1.0
 */

package com.faw_qm.erp.ejb.service;

import com.faw_qm.framework.service.ServiceHomeDelegate;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import javax.ejb.CreateException;
import com.faw_qm.framework.service.BaseService;

public class TechnicDataServiceHomeDelegate implements ServiceHomeDelegate
{
    public TechnicDataServiceHomeDelegate()
    {
    }
    private TechnicDataServiceHome home = null;

   /**
    * 实现HomeDelegate接口方法。
    * @param obj 初始化对象
    */
   public void init(Object obj)
   {
       if(!(obj instanceof TechnicDataServiceHome))
       {
           Object[] objs = {obj.getClass(), "TechnicDataServiceHome"};
           throw new QMRuntimeException(
                   "com.faw_qm.framework.util.FrameworkResource", "70", objs);
       }
       home = (TechnicDataServiceHome) obj;
   }

   /**
    * 实现ServiceHomeDelegate接口方法。
    * @return 标准文件服务Bean实例
    * @throws CreateException
    */
   public BaseService create() throws CreateException
   {
       return home.create();
   }

}
