/**
 * <p>Title: �������ݷ������ʵ���ࡣ</p>
 * <p>Description: �������ݷ������ʵ���ࡣ</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author �촺Ӣ
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
    * ʵ��HomeDelegate�ӿڷ�����
    * @param obj ��ʼ������
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
    * ʵ��ServiceHomeDelegate�ӿڷ�����
    * @return ��׼�ļ�����Beanʵ��
    * @throws CreateException
    */
   public BaseService create() throws CreateException
   {
       return home.create();
   }

}
