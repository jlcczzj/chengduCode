/**
 * 生成程序PromulgateDocLinkInfo.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.model;

import com.faw_qm.framework.service.BinaryLinkInfo;

/**
 * <p>Title: 采用通知书与文档关联</p>
 * <p>Description: 采用通知书与文档关联</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public class PromulgateDocLinkInfo extends BinaryLinkInfo
{
    /**
     * 获得BsoName
     * @return BsoName
     */
    public String getBsoName()
    {
        return "CDPromulgateDocLink";
    }//end getBsoName()

    /**
     * 缺省构造器
     */
    public PromulgateDocLinkInfo()
    {
    }//end DocUsageLinkInfo()

    /**
     * 参数化构造函数，推荐使用
     * @param usedBy 发布通知值对象BsoID
     * @param uses 零件的值对象BsoID
     */
    public PromulgateDocLinkInfo(String usedBy, String uses)
    {
        super(usedBy, uses);
    }//end IteratedUsageLInkInfo(String,String)

    static final long serialVersionUID = -1L;
}//end class DocUsageLinkInfo
