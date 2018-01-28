/**
 * 生成程序PromulgateNotifyService.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.ejb.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.model.StreamDataInfo;
import com.faw_qm.erp.model.PromulgateNotifyIfc;
import com.faw_qm.erp.model.PromulgateNotifyInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: 采用通知书服务接口</p>
 * <p>Description: 采用通知书服务接口</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public interface PromulgateNotifyService extends BaseService
{
    /**
     * 根据采用搜索产品
     * @param bsoid String
     * @return Collection
     */
    public Collection getProductsByProID(String bsoid) throws QMException;

    /**
     * 创建采用通知
     *
     * @param aInfo PromulgateNotifyInfo
     * @param aPartList ArrayList
     * @return PromulgateNotifyInfo
     */
    public PromulgateNotifyIfc createPromulgateNotify(
            PromulgateNotifyInfo newinfo, ArrayList aPartList, ArrayList doclist)
            throws QMException;
    
    /**
     * 创建采用通知
     * @param newinfo PromulgateNotifyInfo
     * @param Products ArrayList
     * @throws QMException
     * @return PromulgateNotifyIfc
     */
    public PromulgateNotifyIfc createPromulgateNotify(
            PromulgateNotifyInfo newinfo, ArrayList aPartList, ArrayList docs,boolean flag)
            throws QMException;

    /**
     * 通过编号搜索零件
     * @param num String
     * @throws QMException
     * @return Vector
     */
    public Vector searchPartByProNumber(String num) throws QMException;

    /**
     * 更新采用通知
     *
     * @param updateBsoid String
     * @param aPartList ArrayList
     */
    public void updatePromulgateNotify(String updateBsoid,
            ArrayList productList, ArrayList docList, ArrayList partList)
            throws QMException;

    public Collection searchPromulgateNotify(String name, String checkboxnName,
            String num, String checkboxnNum, String Flag, String checkboxFlag,
            String textcreator, String checkboxcreator) throws QMException;

    /**
     * 通过采用id搜索关联文档
     * @param id String
     * @return Collection
     */
    public Collection getDocsByProId(String id) throws QMException;

    /**
     * 删除采用通知
     * @param id String
     * @throws QMException
     */
    public void deletePromulgateNotify(String id) throws QMException;

    /**
     * 获得关联零件
     * @param id String
     * @throws QMException
     * @return Vector
     */
    public Vector getPartsByProId(String id) throws QMException;

    /**
     * 根据master标识获得零件
     * @return Collection
     */
    public ArrayList getAllPartsByMaster(String num, String name)
            throws QMException;

    /**
     * 获得关联零件
     * @param id String
     * @throws QMException
     * @return Vector
     */
    public Collection getPartsByProId(PromulgateNotifyIfc info)
            throws QMException;

    /**
     * 变更单提取变更后数据
     * @return ArrayList
     */
    public ArrayList[] obtainDataForChange(BaseValueIfc changeRequest)
            throws QMException;

    /**
     * 根据发布对象获得文件名
     * @param base BaseValueIfc
     * @throws QMException
     * @return String
     */
    public String getFileNameByNotice(BaseValueIfc base) throws QMException;

    /**
     * 为变更请求添加附件,供发布调用
     * @param contentHolder ContentHolderIfc
     * @param file File
     * @throws QMException
     */
    public void setContentForChangeRequest(ContentHolderIfc contentHolder,
            File file, StreamDataInfo streamDataInfo) throws QMException;

    /**
     * 删除关联部件。
     * @param partid String:零部件的BsoID
     */
    public void deletePartLink(String partid) throws QMException;

    /**
     * 删除关联产品。
     * @param productid String:零部件Master的BsoID
     * @throws QMException
     */
    public void deleteProductLink(String productid) throws QMException;

    /**
     * 删除关联文档。
     * @param productid String:文档的BsoID
     * @throws QMException
     */
    public void deleteDocLink(String docID) throws QMException;
}
