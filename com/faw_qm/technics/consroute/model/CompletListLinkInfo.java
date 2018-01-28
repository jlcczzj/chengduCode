/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BinaryLinkInfo;


/**
 * <p>Title: </p>
 * <p>资源文档关联值对象</p>
 * @author 赵辉
 * @version 1.0
 */

public class CompletListLinkInfo extends BinaryLinkInfo
{
    /**
     * 缺省构造器
     */
    public CompletListLinkInfo()
    {
    }


    /**
     * 参数化构造器
     * @param equipment 设备的BsoID
     * @param docMaster 文档Master的BsoID
     */
    public CompletListLinkInfo(String equipment, String docMaster)
    {
        super(equipment, docMaster);
    }


    /**
     * 获得BsoName
     * @return String BsoName
     */
    public String getBsoName()
    {
        return "consCompletListLink";
    }

    static final long serialVersionUID = 1L;

}
