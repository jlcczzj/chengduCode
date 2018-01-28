/** 
 * 生成程序 GraphRB.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.util.ListResourceBundle;

/**
 * <p> Title:路线图编辑器的专用资源 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */
public class GraphRB extends ListResourceBundle
{
    //常量定义
    public static final String ALREADY_LOCALIZE = "0";

    public static final String CAN_NOT_USE_CURRENT_MODEL = "1";

    public static final String CAN_NOT_USE_SELECTIONMODEL = "2";

    public static final String LINK_IS_NOT_EXIST = "3";

    public static final String LINK_IS_ALREADY_EXIST = "4";

    public static final String NODE_IS_NOT_EXIST = "5";

    public static final String NODE_IS_ALREADY_EXIST = "6";

    public static final String NODE_INVALIDATE = "7";

    public static final String LINK_INVALIDATE = "8";

    public static final String ASSEM_DEPARTMENT_MUST_BE_LASTED = "9";

    public static final String A_BRANCH_CAN_HAVE_ONLYONE_ASSEM = "10";

    public static final String CONFIRM_STOP_EDIT_GRAPH = "11";

    public static final String PROPERTY_VALUE_IS_EMPTY = "12";

    public static final String DEPARTMENT_IS_RECYCLE = "13";

    public static final String PROPERTY_VALUE_IS_LARGER = "14";

    public static final String NO_SELECT_DEPARTMENT = "15";

    public static final String CLOSED_LOOP = "16";

    /**
     * 构造资源类
     * @roseuid 4031A6750373
     */
    public GraphRB()
    {}

    /**
     * 获得资源内容
     * @return Object[][] 对象2维数组
     * @roseuid 4031A67503CD
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] = {
            //Titles
            {"exception", "异常信息"}, {"information", "提示"}, {"error", "错误"}, {"warning", "警告"}, {"notice", "通知"}, {"editNodeTitle", "编辑路线节点"}, {"viewNodeTitle", "查看路线节点"},
            {"editGraphTitle", "编辑路线图"},
            {"viewGraphTitle", "查看路线图"},
            //Button
            {"okJButton", "确定"}, {"cancelJButton", "取消"}, {"helpJButton", "帮助"},
            {"quitJButton", "退出"},
            //Label
            {"department", "路线单位"}, {"departmentType", "路线类别"}, {"describe", "说明"},
            {"routeGraph", "路线图"},
            //other
            {"node", "单位节点"}, {"link", "连接"}, {"editNode", "编辑节点"}, {"deleteNodeOrLink", "删除节点或连接"},

            {"0", "..已本地化.."}, {"1", "不能使用所提供的模型，正在创建一个默认模型。"}, {"2", "不能使用所提供的选择模型，正在创建一个默认选择模型。"}, {"3", "指定的连接不存在！"}, {"4", "指定的连接已存在！"}, {"5", "指定的节点不存在！"}, {"6", "指定的节点已存在！"}, {"7", "指定的节点无效！"},
            {"8", "指定的连接无效！"}, {"9", "装配单位必须是路线串中的最后一个单位！"}, {"10", "一个路线串中只能存在一个装配型单位！"}, {"11", "您确实要丢弃当前路线图中已发生的所有更改吗？"}, {"12", "必须有的属性，它的值不能设置为空。"}, {"13", "同一加工单位不能在同一位置重复出现！"},
            {"14", "属性值已经超出了它的上限。"}, {"15", "请选择有效的路线单位！"}, {"16", "生成路线串失败，因为存在闭环。"},

    };

}