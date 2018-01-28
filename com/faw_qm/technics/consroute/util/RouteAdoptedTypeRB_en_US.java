/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * <p>Title:工艺路线服务端类 </p> <p>Description: 工艺路线服务端类</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @version 1.0
 */

public class RouteAdoptedTypeRB_en_US extends ListResourceBundle
{
    /**
     * 构造函数
     */
    public RouteAdoptedTypeRB_en_US()
    {}

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = {{"adopt", "ADOPT,adopt,adopt,10,true,true"}, {"cancel", "CANCEL,cancel,cancel,20,false,true"}, {"nothing", "NOTHING,nothing,nothing,30,false,true"},
            {"exist", "EXIST,Exist,Exist,40,false,true"}

    };

}
