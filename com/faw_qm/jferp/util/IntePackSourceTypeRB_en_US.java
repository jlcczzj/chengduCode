/**
 * 生成程序QMCompositiveTypeRB_en_US.java	1.0              2007-9-24
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.util.ListResourceBundle;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class IntePackSourceTypeRB_en_US extends ListResourceBundle
{
    public IntePackSourceTypeRB_en_US()
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
        		{"com.faw_qm.jferp.QMCompositiveType.PROMULGATENOTIFY",
                "promulgateNotify,采用通知书,采用通知书状态,10,true,true"}
        		,
                {"com.faw_qm.jferp.QMCompositiveType.ADOPTNOTICE",
                "adoptNotice,更改采用通知书,更改采用通知书状态,20,false,true"}
        		,
                {"com.faw_qm.jferp.QMCompositiveType.BASELINE",
                "baseLine,基线,基线状态,30,false,true"}
        		,
        		{"com.faw_qm.jferp.QMCompositiveType.technicsRouteList",
                "technicsRouteList,路线,路线状态,40,false,true"}});
    }
}
