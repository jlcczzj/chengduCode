/** 生成程序时间 2009/07/17
 * 程序文件名称 Variable.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capp.util;

import java.util.HashMap;

import com.faw_qm.cappclients.beans.cappexattrpanel.GroupPanel;
import com.faw_qm.extend.util.ExtendAttGroup;

/**
 * 一个变量类，用于缓存一些相关信息
 * @author 徐德政
 * @date 2009.07.17
 *
 */
public class Variable {
	public static HashMap contents = new HashMap();
	public static String name_key = "";
	public static ExtendAttGroup group ;
	public static GroupPanel groupPanel ;
	
	public Variable() {
	}
}
