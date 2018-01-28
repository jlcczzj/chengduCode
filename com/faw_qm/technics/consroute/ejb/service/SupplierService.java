/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.technics.consroute.ejb.service;

  

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.technics.consroute.model.SupplierInfo;
import com.faw_qm.technics.consroute.util.SupplierFormData;

import java.util.*;

/**
 * <p>Title: ���϶������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author �� ��
 * @version 1.0
 */

public interface SupplierService
    extends BaseService {
	/**
	   * �����ĵ��Զ����
	   * @return info �ĵ��Զ����ֵ����
	   */
	public SupplierInfo createsupplier(SupplierFormData formData) throws QMException;
	
    /**
     * ������Ӧ��
     * @param supplierInfo ��Ӧ��ֵ����
     * @return SupplierInfo ����֮��Ĺ�Ӧ��ֵ����
     * @see SupplierInfo
     * @throws SupplierException
     */
    public SupplierInfo createsupplier(SupplierInfo supplierInfo)
            throws QMException;
   /**
    * ����SupplierFormData�����ĵ�
    * @param SupplierFormData �ĵ�������
    * @return SupplierInfo ����֮����ĵ�
    * @throws DocException
    * @throws ServiceLocatorException
    * @throws QMException
    * <br>����ĵ�ֵ����Ϊ�գ����׳���"���� com.faw_qm.doc ����ϵͳ��Ϣ����: *"��
    */
   public SupplierInfo updateSupplier(SupplierFormData formData)
           throws QMException,
           ServiceLocatorException, QMException;
   /**
    *  ����DocFormDataɾ����Ӧ��
    *  @param  String id Ҫɾ����Ӧ�̵�BsoID
    *  @throws DocException
    */
   public void deleteSupplier(String id)
           throws QMException;

   /**
     * ���ݹ�Ӧ�̴����ѯ���ݿ�
     * @param  QMQuery query1,String projectteam,String checkboxprojTeam
     * @return QMQuery
     * @exception Exception
     */
    public SupplierInfo findSupplierByCode(String code,String name, String people,String address,String telephone) throws
        Exception ;
    /**
     * ������Ӧ��
     * @param code
     * @param codename
     * @param codeFlag
     * @param nameFlag
     * @return
     * @throws QMException
     */
    public Vector findSupplier(String code, String codename, boolean codeFlag, boolean nameFlag)throws QMException;
}
