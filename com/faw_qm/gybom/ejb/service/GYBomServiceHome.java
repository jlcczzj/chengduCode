/** ���ɳ���ʱ�� 2016-5-16
  * �汾         1.0
  * ����         doc
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  */
package com.faw_qm.gybom.ejb.service;

import com.faw_qm.framework.service.BaseServiceHome;
import javax.ejb.*;

/**
  * ����BOM����ƽ̨�����Home�ӿڡ�
  * @author  ���  �޸�ʱ��  2016-5-16
  */
public interface GYBomServiceHome extends BaseServiceHome
{
	
	public GYBomService create() throws CreateException;
	
}
