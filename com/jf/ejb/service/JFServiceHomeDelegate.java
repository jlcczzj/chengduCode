package com.jf.ejb.service;

import com.faw_qm.framework.service.ServiceHomeDelegate;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.exceptions.QMRuntimeException;

/**
 * <p>Title: 物料清单</p>
 * <p>Description: 零部件报表中转服务。</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 启明</p>
 * @author liunan 2009-02-11
 * @version 1.0
 */

public class JFServiceHomeDelegate
    implements ServiceHomeDelegate {

  JFServiceHome home = null;

  public JFServiceHomeDelegate() {
  }

  public BaseService create() throws javax.ejb.CreateException {
    return home.create();
  }

  public void init(Object obj) {
    if (! (obj instanceof JFServiceHome)) {
      Object[] objs = {
          obj.getClass(), "JFServiceHome"
      };
      throw new QMRuntimeException(
          "com.faw_qm.framework.util.FrameworkResource", "70", objs);
    }
    home = (JFServiceHome) obj;
  }
}
