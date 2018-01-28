/**
 * 生成程序PromulgateNotifyFlagRB_zh_CN.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.util;

import java.util.ListResourceBundle;

/**
 * 采用标识资源文件
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public class PromulgateNotifyFlagRB_zh_CN extends ListResourceBundle
{
    public PromulgateNotifyFlagRB_zh_CN()
    {
    }

    static final Object[][] contents = {
            {"SHIZHI", "SHIZHI,试制,试制,1,true,true"},
            {"SHENGCHANZHUNBEI", "SHENGCHANZHUNBEI,生产准备,生产准备,10,false,true"},
            {"SHENGCHAN", "SHENGCHAN,生产,生产,20,false,true"}
            };

    public Object[][] getContents()
    {
        return contents;
    }
}
