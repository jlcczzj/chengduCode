package com.faw_qm.gybom.ejb.service;

import javax.ejb.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.framework.exceptions.*;

/**
 * <p>Title:工艺BOM管理平台标准服务Home方法代理实现类 </p>
 * <p>Description: 工艺BOM管理平台标准服务Home方法代理实现类</p>
 * <p>Copyright: Copyright (c) 2016-5-16</p>
 * <p>Company:FAW_QM </p>
 * @author 刘楠
 * @version 1.0
 */

public class GYBomServiceHomeDelegate  implements ServiceHomeDelegate{
  public GYBomServiceHomeDelegate() {
  }

  private GYBomServiceHome home=null;

  /**
   * 实现HomeDelegate接口方法。用一个对象初始化标准文件服务Home接口
   * @param obj 初始化对象
   */
  public void init(Object obj){
    if(!(obj instanceof GYBomServiceHome)){
       Object[] objs={obj.getClass(),"GYBomServiceHome"};
       throw new QMRuntimeException("com.faw_qm.framework.util.FrameworkResource","70",objs);
    }
       home=(GYBomServiceHome)obj;
  }

  /**
   * 实现ServiceHomeDelegate接口方法。创建标准文件服务Bean实例
   * @return 标准文件服务Bean实例
   * @throws CreateException
   */
  public BaseService create() throws CreateException {
    return home.create();
  }

}