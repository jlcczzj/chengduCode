/**
 * 生成程序Messages.java	1.0              2006-11-16
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.util.ResourceBundle;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;

/**
 * <p>Title: 获取资源信息封装类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public final class Messages
{
    public static final String BUNDLE_NAME = "com.faw_qm.jferp.util.ERPResource"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME, RemoteProperty.getVersionLocale());

    /**
     * 缺省构造函数。
     */
    private Messages()
    {
        super();
    }

    /**
     * 获取资源值。
     * @param key 健。
     * @return 资源值。
     */
    public final static String getString(String key)
    {
        return RESOURCE_BUNDLE.getString(key);
    }

    /**
     * 获取带有变量的资源值。
     * @param key 键。
     * @param aobj 变量集合。
     * @return 资源值。
     */
    public final static String getString(String key, Object aobj[])
    {
        return QMMessage.getLocalizedMessage(BUNDLE_NAME, key, aobj);
    }
}
