/**
 * 生成程序FilterPartInfo.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: 过滤发布零件实现</p>
 * <p>Description:过滤发布零件实现 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public class FilterPartInfo extends BaseValueInfo implements FilterPartIfc
{
    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getIdentity()
     */
    public String getIdentity()
    {
        return "FilterPart:" + partNumber + versionValue + state;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String partNumber;

    private String versionValue;

    private String state;

    private String noticeNumber;

    private String noticeType;
    
    private String route;

    public String getNoticeType()
    {
        return noticeType;
    }

    public String getNoticeNumber()
    {
        return noticeNumber;
    }

    public String getState()
    {
        return state;
    }

    public String getVersionValue()
    {
        return versionValue;
    }

    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
    }

    public void setNoticeType(String noticeType)
    {
        this.noticeType = noticeType;
    }

    public void setNoticeNumber(String noticeNumber)
    {
        this.noticeNumber = noticeNumber;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setVersionValue(String versionValue)
    {
        this.versionValue = versionValue;
    }

    public String getPartNumber()
    {
        return partNumber;
    }
    
    public String getRoute()
    {
    	return route;
    }
    
    public void setRoute(String rout)
    {
    	route=rout;
    }

    public FilterPartInfo()
    {
    }

    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "JFFilterPart";
    }
}
