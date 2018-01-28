/** 
 * 生成程序 RouteItem.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.graph;

import com.faw_qm.framework.service.*;
import java.io.*;

/**
 * <p> Title:为服务端完成持久化工作提供的业务对象信息封装类 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class RouteItem implements Serializable
{
    /** 业务对象(路线节点或连接值对象) */
    private BaseValueIfc object;

    /** 标记业务对象的状态 */
    private String state;

    /** 业务对象的状态:新建 */
    public static final String CREATE = "create";

    /** 业务对象的状态:更新 */
    public static final String UPDATE = "update";

    /** 业务对象的状态:删除 */
    public static final String DELETE = "delete";

    private static final String versionID = "$Header:RouteItem.java 4 $";

    /**
     * 构造函数
     */
    public RouteItem()
    {}

    /**
     * 构造一个包含指定业务对象的RouteItem
     * @param obj 指定业务对象(路线节点或连接值对象)
     */
    public RouteItem(BaseValueIfc obj)
    {
        setObject(obj);
    }

    /**
     * 设置业务对象
     * @param obj 指定业务对象(路线节点或连接值对象)
     */
    public void setObject(BaseValueIfc obj)
    {
        object = obj;
    }

    /**
     * 获得业务对象
     * @return 路线节点或连接值对象
     */
    public BaseValueIfc getObject()
    {
        return object;
    }

    /**
     * 获得节点或连接的状态
     * @return 新建、更新或删除
     */
    public String getState()
    {
        return state;
    }

    /**
     * 设置 节点或连接 的 状态
     * @param state 新建、更新或删除
     */
    public void setState(String state)
    {
        this.state = state;
    }

    /**
     * 写对象
     * @param oos java.io.ObjectOutputStream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream oos) throws IOException
    {
        oos.defaultWriteObject();
    }

    /**
     * 读对象
     * @param ois java.io.ObjectInputStream
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
    {
        ois.defaultReadObject();
    }

}