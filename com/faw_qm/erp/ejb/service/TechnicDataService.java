/**
 * 生成程序TechnicDataService.java	1.0              2007-10-31
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */

package com.faw_qm.erp.ejb.service;

import com.faw_qm.framework.service.BaseService;
import com.faw_qm.capp.model.QMTechnicsIfc;
import java.util.Collection;
import com.faw_qm.framework.exceptions.QMException;
import java.util.ArrayList;


/**
 * <p>Title: 工艺数据服务接口。</p>
 * <p>Description: 工艺数据服务接口。</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 徐春英
 * @version 1.0
 */
public interface TechnicDataService extends BaseService
{
    /**
     * 通过工艺值对象获得该工艺使用的零部件集合
     * @param technic 工艺值对象
     * @throws QMException
     * @return Collection
     */
    public Collection getPartsByTechnics(QMTechnicsIfc technic)
            throws QMException;


    /**
     * 根据工艺ID获得所使用的工序集合
     * @param bsoID String
     * @throws QMException
     * @return Collection
     */
    public Collection browseProceduresByTechnics(String bsoID)
            throws
            QMException;


    /**
     * 由工艺卡id找到工艺使用材料的关联
     * @param tech techBsoID 工艺id
     * @throws CappException
     * @return Collection 工艺使用材料的关联的集合
     */
    public Collection findTechnicsMaterialsLinkByTech(String techBsoID)
            throws
            QMException;


    /**
     * 将工艺下的工序信息写入到QMXMLProcess中
     * @param bsoID String
     * @throws QMException
     * @return ArrayList
     */
    public ArrayList getTechnicsData(String technicbsoID)
            throws
            QMException;


}
