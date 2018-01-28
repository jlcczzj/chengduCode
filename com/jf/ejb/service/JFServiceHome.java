package com.jf.ejb.service;

import com.faw_qm.framework.service.BaseServiceHome;
import javax.ejb.CreateException;

/**
 * <p>Title: 物料清单</p>
 * <p>Description: 零部件报表中转服务。</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 启明</p>
 * @author liunan 2009-02-11
 * @version 1.0
 */

public interface JFServiceHome extends BaseServiceHome
{
  public abstract JFService create() throws CreateException;
}
