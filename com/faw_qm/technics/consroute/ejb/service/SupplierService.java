/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * <p>Title: 材料定额服务</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 刘 明
 * @version 1.0
 */

public interface SupplierService
    extends BaseService {
	/**
	   * 保存文档自动编号
	   * @return info 文档自动编号值对象
	   */
	public SupplierInfo createsupplier(SupplierFormData formData) throws QMException;
	
    /**
     * 创建供应商
     * @param supplierInfo 供应商值对象
     * @return SupplierInfo 创建之后的供应商值对象
     * @see SupplierInfo
     * @throws SupplierException
     */
    public SupplierInfo createsupplier(SupplierInfo supplierInfo)
            throws QMException;
   /**
    * 根据SupplierFormData更新文档
    * @param SupplierFormData 文档表单数据
    * @return SupplierInfo 更新之后的文档
    * @throws DocException
    * @throws ServiceLocatorException
    * @throws QMException
    * <br>如果文档值对象为空，则抛出："出现 com.faw_qm.doc 错误。系统信息如下: *"。
    */
   public SupplierInfo updateSupplier(SupplierFormData formData)
           throws QMException,
           ServiceLocatorException, QMException;
   /**
    *  根据DocFormData删除供应商
    *  @param  String id 要删除供应商的BsoID
    *  @throws DocException
    */
   public void deleteSupplier(String id)
           throws QMException;

   /**
     * 根据供应商代码查询数据库
     * @param  QMQuery query1,String projectteam,String checkboxprojTeam
     * @return QMQuery
     * @exception Exception
     */
    public SupplierInfo findSupplierByCode(String code,String name, String people,String address,String telephone) throws
        Exception ;
    /**
     * 搜索供应商
     * @param code
     * @param codename
     * @param codeFlag
     * @param nameFlag
     * @return
     * @throws QMException
     */
    public Vector findSupplier(String code, String codename, boolean codeFlag, boolean nameFlag)throws QMException;
}
