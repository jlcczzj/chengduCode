package com.jf.ejb.service;

import com.faw_qm.framework.service.BaseServiceHome;
import javax.ejb.CreateException;

/**
 * <p>Title: �����嵥</p>
 * <p>Description: �㲿��������ת����</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ����</p>
 * @author liunan 2009-02-11
 * @version 1.0
 */

public interface JFServiceHome extends BaseServiceHome
{
  public abstract JFService create() throws CreateException;
}
