/**
 * 生成程序QMCompositiveTypeRB_zh_CN.java	1.0              2007-9-24
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.util;

import java.util.ListResourceBundle;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class IntePackSourceTypeRB_zh_CN extends ListResourceBundle
{
    public IntePackSourceTypeRB_zh_CN()
    {
    }

    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][];
    static
    {
        contents = (new Object[][]{
        		{"com.faw_qm.cderp.QMCompositiveType.PROMULGATENOTIFY",
                "promulgateNotify,采用通知书,采用通知书状态,10,true,true"}
        		,
                {"com.faw_qm.cderp.QMCompositiveType.ADOPTNOTICE",
                "adoptNotice,更改采用通知书,更改采用通知书状态,20,false,true"}
        		,
                {"com.faw_qm.cderp.QMCompositiveType.BASELINE",
                "baseLine,基线,基线状态,30,false,true"}
        		,
        		{"com.faw_qm.cderp.QMCompositiveType.technicsRouteList",
                "technicsRouteList,路线,路线状态,40,false,true"}});
    }
}
