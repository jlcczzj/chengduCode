/**
 * 程序ERPMiddleBOMLink.java	1.0  2014/05/20
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 为erp接口历史数据处理增加父件零部件编号 刘家坤 20140905
 */
package com.faw_qm.erppartreload;

/**
 * 数据回导中ERP BOM 中间表的封装类
 * @author houhf
 */
public class ERPMiddleBOMLink {

	//父件编号
	String parentNumber;
	//子件编号
	String childNumber;
	//使用数量
	int defaultUnit;
	//BOM行项号
	String bomNumber;
	//子组号
	String specNumber;
	//备注
	String desc;
	//子件是否是原材料
	String isMater;
	
	String parentPartNumber;
	
	/**
	 * 获得part父件编号
	 * @return parentNumber 父件编号
	 * @author houhf
	 */
	public String getPrentPartNumber() {
		return parentNumber;
	}
	
	/**
	 * 设置part父件编号
	 * @param parentNumber 父件编号
	 * @author houhf
	 */
	public void setPrentPartNumber(String parentNumber) {
		this.parentNumber = parentNumber;
	}
	
	/**
	 * 获得父件编号
	 * @return parentNumber 父件编号
	 * @author houhf
	 */
	public String getParentNumber() {
		return parentNumber;
	}
	
	/**
	 * 设置父件编号
	 * @param parentNumber 父件编号
	 * @author houhf
	 */
	public void setParentNumber(String parentNumber) {
		this.parentNumber = parentNumber;
	}
	
	/**
	 * 获得子件编号
	 * @return childNumber 子件编号
	 * @author houhf
	 */
	public String getChildNumber() {
		return childNumber;
	}
	
	/**
	 * 设置子件编号
	 * @param childNumber 子件编号
	 * @author houhf
	 */
	public void setChildNumber(String childNumber) {
		this.childNumber = childNumber;
	}
	
	/**
	 * 获得使用数量
	 * @return defaultUnit 使用数量
	 * @author houhf
	 */
	public int getDefaultUnit() {
		return defaultUnit;
	}
	
	/**
	 * 设置使用数量
	 * @param defaultUnit 使用数量
	 * @author houhf
	 */
	public void setDefaultUnit(int defaultUnit) {
		this.defaultUnit = defaultUnit;
	}
	
	/**
	 * 获得BOM项行号
	 * @return bomNumber BOM项行号
	 * @author houhf
	 */
	public String getBomNumber() {
		return bomNumber;
	}
	
	/**
	 * 设置BOM项行号
	 * @param bomNumber
	 * @author houhf
	 */
	public void setBomNumber(String bomNumber) {
		this.bomNumber = bomNumber;
	}
	
	/**
	 * 获得子组号
	 * @return specNumber 子组号
	 * @author houhf
	 */
	public String getSpecNumber() {
		return specNumber;
	}
	
	/**
	 * 设置子组号
	 * @param specNumber 子组号
	 * @author houhf
	 */
	public void setSpecNumber(String specNumber) {
		this.specNumber = specNumber;
	}
	
	/**
	 * 获得备注
	 * @return desc 备注
	 * @author houhf
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * 设置 备注
	 * @param desc 备注
	 * @author houhf
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * 获得子件是否是原材料
	 * @return 是否是原材料 Y为原材料
	 * @author houhf
	 */
	public String getIsMater() {
		return isMater;
	}
	
	/**
	 * 设置是否是原材料
	 * @param isMater 是否是原材料 Y是原材料
	 * @author houhf
	 */
	public void setIsMater(String isMater) {
		this.isMater = isMater;
	}
	
}
