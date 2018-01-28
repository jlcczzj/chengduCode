/**
 * 生成程序 RouteResource.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * 参考文档： 1.消息表V1.0.xls：(1--12) 2.Phos-REQ-CAPP-BR02(工艺路线业务规则)V2.0.doc
 * <p>
 * Title:RouteResource.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Package:com.faw_qm.technics.consroute.util
 * </p>
 * <p>
 * ProjectName:CAPP
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:一汽启明
 * </p>
 *
 * @author unascribed
 * @version 1.0
 * <code>ListResourceBundle</code> is an abstract subclass of
 * <code>ResourceBundle</code> that manages resources for a locale
 * in a convenient and easy to use list. See <code>ResourceBundle</code> for
 * more information about resource bundles in general.
 *
 * <P>
 * Subclasses must override <code>getContents</code> and provide an array,
 * where each item in the array is a pair of objects.
 * The first element of each pair is the key, which must be a
 * <code>String</code>, and the second element is the value associated with
 * that key.
 */

/**
 *CR1 2011/4/21 郭晓亮 参见：测试域:product;TD号2386
 */
public class RouteResource extends ListResourceBundle
{

    /**
     * 构造函数
     */
    public RouteResource()
    {

    }

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] = {{"1", "工艺路线表名称不能为空。"}, {"2", "工艺路线表编号不能为空。"}, {"3", "工艺路线表\"*\"的编号\"*\"已经存在，请重新设置编号。"}, {"4", "此零件已有路线。如想复制其它路线，请先删除已有路线。"}, {"5", "产品号:\"*\"不正确，请重新输入产品号。"},
            {"6", "*(*)的工艺路线报表"}, {"7", "用于产品：* *"}, {"8", "报表日期：*年*月*日"}, {"9", "*: *(*)的二级工艺路线报表"}, {"10", "导入数据错误，因为您未获得修订工艺路线表*的许可。"}, {"11", "导入数据错误，因为您未获得升级工艺路线表小版本的许可。"},
            {"12", "此零件有路线，不能被删除！"}//CR1
    };
}