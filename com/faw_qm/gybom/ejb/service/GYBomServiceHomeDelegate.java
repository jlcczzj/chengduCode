package com.faw_qm.gybom.ejb.service;

import javax.ejb.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.framework.exceptions.*;

/**
 * <p>Title:����BOM����ƽ̨��׼����Home��������ʵ���� </p>
 * <p>Description: ����BOM����ƽ̨��׼����Home��������ʵ����</p>
 * <p>Copyright: Copyright (c) 2016-5-16</p>
 * <p>Company:FAW_QM </p>
 * @author ���
 * @version 1.0
 */

public class GYBomServiceHomeDelegate  implements ServiceHomeDelegate{
  public GYBomServiceHomeDelegate() {
  }

  private GYBomServiceHome home=null;

  /**
   * ʵ��HomeDelegate�ӿڷ�������һ�������ʼ����׼�ļ�����Home�ӿ�
   * @param obj ��ʼ������
   */
  public void init(Object obj){
    if(!(obj instanceof GYBomServiceHome)){
       Object[] objs={obj.getClass(),"GYBomServiceHome"};
       throw new QMRuntimeException("com.faw_qm.framework.util.FrameworkResource","70",objs);
    }
       home=(GYBomServiceHome)obj;
  }

  /**
   * ʵ��ServiceHomeDelegate�ӿڷ�����������׼�ļ�����Beanʵ��
   * @return ��׼�ļ�����Beanʵ��
   * @throws CreateException
   */
  public BaseService create() throws CreateException {
    return home.create();
  }

}