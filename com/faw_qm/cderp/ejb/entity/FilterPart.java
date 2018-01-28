/**
 * 生成程序PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: 发布的零件接口</p>
 * <p>Description: 发布的零件接口</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public interface FilterPart extends BsoReference
{
    public void setNoticeType(String noticeType);

    public String getNoticeType();

    public void setNoticeNumber(String noticeNumber);

    public String getNoticeNumber();

    public void setState(String state);

    public String getState();

    public void setVersionValue(String versionValue);

    public String getVersionValue();

    public void setPartNumber(String partNumber);

    public String getPartNumber();
    
    public String getRoute();
    
    public void setRoute(String route);
}
