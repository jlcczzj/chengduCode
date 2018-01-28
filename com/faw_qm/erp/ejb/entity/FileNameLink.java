/**
 * 生成程序PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: 发布和文件名称的关联接口</p>
 * <p>Description:发布和文件名称的关联接口 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public interface FileNameLink extends BsoReference
{
    /**
     * 设置文件名
     * @param fileName String
     */
    public void setFileName(String fileName);

    /**
     * 获得文件名
     * @return String
     */
    public String getFileName();

    public void setNotice(String notice);

    public String getNotice();
}
