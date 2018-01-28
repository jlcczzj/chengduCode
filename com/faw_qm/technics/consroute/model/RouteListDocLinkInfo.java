/**
 * 生成程序 RouteListDocLinkInfo.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BinaryLinkInfo;

/**
 * <p>Title:RouteListDocLinkInfo.java</p> <p>Description:资源文档关联值对象 </p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:一汽启明</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteListDocLinkInfo extends BinaryLinkInfo
{
    /**
     * 缺省构造器
     */
    public RouteListDocLinkInfo()
    {}

    /**
     * 参数化构造器
     * @param equipment 设备的BsoID
     * @param docMaster 文档Master的BsoID
     */
    public RouteListDocLinkInfo(String equipment, String docMaster)
    {
        super(equipment, docMaster);
    }

    /**
     * 获得BsoName
     * @return String BsoName
     */
    public String getBsoName()
    {
        return "consEquipmentDocLink";
    }

    static final long serialVersionUID = 1L;

}
