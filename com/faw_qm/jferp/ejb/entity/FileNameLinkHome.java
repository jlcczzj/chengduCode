/**
 * 生成程序PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

/**
 * <p>Title: 发布和文件名称的关联接口</p>
 * <p>Description: 发布和文件名称的关联接口</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public interface FileNameLinkHome extends BsoReferenceHome
{
    public FileNameLink create(BaseValueIfc basevalueifc)
            throws CreateException;

    public FileNameLink create(BaseValueIfc basevalueifc, Timestamp timestamp,
            Timestamp timestamp1) throws CreateException;

    public FileNameLink findByPrimaryKey(String s) throws FinderException;
}
