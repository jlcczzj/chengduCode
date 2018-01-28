package com.faw_qm.gybomNotice.ejb.entity;


import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoHomeDelegate;
import com.faw_qm.framework.service.BsoReference;

/**
 
 * <p>Title:�������Home��������ʵ���� </p>
 * <p>Description: �������Home��������ʵ����</p>
 * @version 1.0
 */
public class GYBomAdoptNoticeHomeDelegate implements BsoHomeDelegate{
	
	
	private GYBomAdoptNoticeHome home = null;

	 /**
     * ����ֵ������ҵ�����
     * @param info ֵ����
     * @return  ������ҵ�����
     * @throws CreateException
     */
	public BsoReference create(BaseValueIfc info) throws CreateException 
	{
		
		 return home.create(info);
	}

	 /**
     * ����ֵ�����ָ��ʱ�������ҵ�����
     * @param info ֵ����
     * @param ct ����ʱ���
     * @param mt �޸�ʱ���
     * @return ������ҵ�����
     * @throws CreateException
     */
	public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt)
			throws CreateException 
	{
		 return home.create(info, ct, mt);
	}
	
	 /**
     * ���ڸ��������ָ�����
     * @param bsoID ҵ������ֵ���������
     * @return �ָ���ҵ�����
     * @throws FinderException
     */

	public BsoReference findByPrimaryKey(String bsoID) throws FinderException 
	{
		 return home.findByPrimaryKey(bsoID);
	}

	/**
     * ʵ��HomeDelegate�ӿڷ�������һ�������ʼ��Home�ӿ�
     * @param obj ��ʼ������
     */
	public void init(Object obj)  
	{
        if (!(obj instanceof GYBomAdoptNoticeHome))
        {
            Object[] objs ={obj.getClass(), "GYBomAdoptNoticeHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (GYBomAdoptNoticeHome) obj;
		
	}

}