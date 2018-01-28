/**
 * 生成程序IntePackException.java	1.0              2007-9-26
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.exception;

import com.faw_qm.framework.exceptions.QMException;

/**
 * <p>Title: 集成包的异常类。</p>
 * <p>Description: 集成包的异常类。</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class IntePackException extends QMException
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 空异常
     */
    public IntePackException()
    {
    }

    /**
     * 用字符串构造异常，可直接输出
     * @param msg 异常信息
     */
    public IntePackException(String msg)
    {
        super(msg);
    }

    /**
     * 嵌套异常信息
     * @param arg0 异常实例
     */
    public IntePackException(Exception e)
    {
        super(e);
    }

    /**
     * 嵌套异常并直接输出信息
     * @param e 异常实例
     * @param msg 异常信息
     */
    public IntePackException(Exception e, String msg)
    {
        super(e, msg);
    }

    /**
     * 参数化异常信息
     * @param rb 资源类名
     * @param key 信息的键值
     * @param obj 参数信息
     */
    public IntePackException(String rb, String key, Object[] obj)
    {
        super(rb, key, obj);
    }

    /**
     * 嵌套异常并参数化异常信息
     * @param e 异常实例
     * @param rb 资源类名
     * @param key 信息的键值
     * @param obj 参数信息
     */
    public IntePackException(Exception e, String rb, String key, Object[] obj)
    {
        super(e, rb, key, obj);
    }
}
