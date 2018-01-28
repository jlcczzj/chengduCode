/**
 * 生成程序IntePackService.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.ejb.service;

import java.util.Collection;
import java.util.Vector;

import com.faw_qm.erp.exception.IntePackException;
import com.faw_qm.erp.model.IntePackIfc;
import com.faw_qm.erp.model.IntePackInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;

/**
 * <p>Title: 集成包服务接口。</p>
 * <p>Description: 集成包服务接口。</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public interface IntePackService extends BaseService
{
    /**
     * 创建集成包
     * @param intePackInfo IntePackInfo
     * @return IntePackInfo
     */
    public IntePackIfc createIntePack(IntePackIfc intePackInfo)
            throws QMException;

    /**
     * 删除集成包
     * @param intePackIfc IntePackIfc
     * @throws QMException
     */
    public void deleteIntePack(IntePackIfc intePackIfc) throws QMException;

    /**
     * 删除集成包
     * @param id String
     * @throws QMException
     */
    public void deleteIntePack(String id) throws QMException;

    /**
     * 根据集成包搜索零部件
     * @param bsoid String
     * @return Collection
     */
    public Collection getProductsByIntePackID(String bsoid) throws QMException;

    /**
     * 判断当前用户是否有权修改此文档
     * 如果对该文档无QMPermission.MODIFY权限，返回 false
     * 如果当前用户具有Administrator权限，返回 true
     * @param intePackInfo 文档值对象
     * @return true(有权限) or false(无权限)
     * @throws AdoptNoticeException
     */
    public boolean isIntePackPublish(IntePackInfo intePackInfo)
            throws IntePackException;
    
    /**
     * 青汽ERP数据发布
     * @param id 数据集成包标识
     * @param xmlName 发布XML文件的名称
     * @return IntePackInfo
     * @throws QMExcepiton
     */
    public IntePackInfo publishIntePack(String id ,String xmlName,Collection coll)throws QMException;
    /**
     * 青汽ERP数据发布
     * added by dikefeng 20090422，为了使得在发布的时候知道本次新发的物料都有哪些，需要在这里把过滤后的零件清单返回一个
     * @param id 数据集成包标识
     * @param xmlName 发布XML文件的名称
     * @return IntePackInfo
     * @throws QMExcepiton
     */
    public IntePackInfo publishIntePack(String id ,String xmlName,Collection coll,Vector filterParts)throws QMException;
    /**
     * 发布集成包，暂时没写
     * @param intePackInfo IntePackInfo
     * @throws QMException
     * @return IntePackInfo
     */
    public IntePackInfo publishIntePack(IntePackInfo intePackInfo)
            throws QMException;

    /**
     * 发布集成包
     * @param id String
     * @throws QMException
     * @return IntePackInfo
     */
    public IntePackInfo publishIntePack(String id) throws QMException;

    /**
     * 搜索集成包
     * @param id String
     * @throws QMException
     * @return IntePackInfo
     */
    public IntePackInfo searchIntePackByID(String id) throws QMException;

    /**
     * 搜索集成包
     * @param name String:集成包的名称
     * @param sourceType String：集成包的来源类型
     * @param source String：集成包的来源
     * @param state String：集成包的状态
     * @param creator String：集成包的创建者
     * @param createTime String：集成包的创建时间
     * @param publisher String：集成包的发布者
     * @param publishTime String：集成包的发布时间
     * @throws QMException
     * @return Collection：查询结果集合
     */
    public Collection searchIntePackByID(String name, String sourceType,
            String source, String state, String creator, String createTime,
            String publisher, String publishTime) throws QMException;

    /**
     * 搜索集成包
     * @param name String:集成包的名称
     * @param checkboxName String：名称的查询类型：包含或者不包含
     * @param sourceType String：集成包的来源类型
     * @param checkboxSourceType String：来源类型的查询类型：包含或者不包含
     * @param source String：集成包的来源
     * @param checkboxSource String：来源的查询类型：包含或者不包含
     * @param state String：集成包的状态
     * @param checkboxState String：状态的查询类型：包含或者不包含
     * @param creator String：集成包的创建者
     * @param checkboxCreator String：创建者的查询类型：包含或者不包含
     * @param createTime String：集成包的创建时间
     * @param checkboxCreateTime String：创建时间的查询类型：包含或者不包含
     * @param publisher String：集成包的发布者
     * @param checkboxPublisher String：发布者的查询类型：包含或者不包含
     * @param publishTime String：集成包的发布时间
     * @param checkboxPublishTime String：发布时间的查询类型：包含或者不包含
     * @throws QMException
     * @return Collection：查询结果集合
     */
    public Collection searchIntePackByID(String name, String checkboxName,
            String sourceType, String checkboxSourceType, String source,
            String checkboxSource, String state, String checkboxState,
            String creator, String checkboxCreator, String createTime,
            String checkboxCreateTime, String publisher,
            String checkboxPublisher, String publishTime,
            String checkboxPublishTime) throws QMException;

    /**
     * 获得相关的物料结构。
     * @param parentNumber:父物料号
     * @param partNumber:父件号
     * @param partVersionID:父件版本
     * @return 相关的物料结构
     * @throws QMException
     */
    public Collection getStructureLinkMap(String parentNumber,
            String partNumber, String partVersionID) throws QMException;
    
    /**
     * 发布集成包中采用部件的工艺。
     * @param id String
     * @throws QMException
     * @return IntePackInfo
     */
    public void publishTechByIntePack(String intePackID) throws QMException;
    
}
