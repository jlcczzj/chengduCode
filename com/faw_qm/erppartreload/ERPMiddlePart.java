package com.faw_qm.erppartreload;
/**
 * 程序ERPMiddlePart.java	1.0  2014/05/20
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
//package com.faw_qm.erppartreload;

import java.util.Collection;
import java.util.HashMap;

/**
 * ERP数据回导中ERP PART 中间表的封装类
 * @author houhf
 */
public class ERPMiddlePart {
	
	//零部件编号
	String partNumber;
	//零部件类型
	String partType;
	//零部件名称
	String partName;
	//计量单位
	String defaultUnit;
	//零部件版本
	String partVersion;
	//ERP物料号
	String ERPNumber;
	//制造路线串
	String manuRoute;
	//装配路线串
	String assRoute;
	//颜色件标识
	String colorFlag;
	//来源
	String producedBy;
	//虚拟件
	String dummyPart;
	//备注
	String desc;
	//标识该ERPMiddlePart是否查询过BOM中间表
	Boolean isFindBOM;
	//存放该ERPMiddlePart所有的一级子件对应的link
	Collection subParts;
	//存放该ERPMiddlePart所有的半成品和原材料关联 	key：半成品或者原材料的层级；value：半成品或者原材料对应的link
	HashMap subLinks;
	//标识零件是否需要新建-new是新建 update是更新
	String isNew;
	String penbs;
	
	
	/**
	 * 获得零部件编号
	 * @return partNumber
	 * @author houhf
	 */
	public String getPartNumber(){
		return partNumber;
	}
	
	/**
	 * 设置零部件编号
	 * @param partNumber 零部件编号
	 * @author houhf
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	/**
	 * 获得零部件类型
	 * @return partType
	 * @author houhf
	 */
	public String getPartType() {
		return partType;
	}
	
	/**
	 * 设置零部件类型
	 * @param partType 零部件类型
	 * @author houhf
	 */
	public void setPartType(String partType) {
		this.partType = partType;
	}
	
	/**
	 * 获得零部件名称
	 * @return partName
	 * @author houhf
	 */
	public String getPartName() {
		return partName;
	}
	
	/**
	 * 设置零部件名称
	 * @param partName 零部件件名称
	 * @author houhf
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
	/**
	 * 获得计量单位
	 * @return defaultUnit 计量单位
	 * @author houhf
	 */
	public String getDefaultUnit() {
		return defaultUnit;
	}
	
	/**
	 * 设置计量单位
	 * @param defaultUnit 计量单位
	 * @author houhf
	 */
	public void setDefaultUnit(String defaultUnit) {
		this.defaultUnit = defaultUnit;
	}
	
	/**
	 * 获得零部件版本
	 * @return partVersion
	 * @author houhf
	 */
	public String getPartVersion() {
		return partVersion;
	}
	
	/**
	 * 设置零部件版本
	 * @param partVersion 零部件版本
	 * @author houhf
	 */
	public void setPartVersion(String partVersion) {
		this.partVersion = partVersion;
	}
	
	/**
	 * 获得ERP物料编号
	 * @return ERPNumber ERP物料编号
	 * @author houhf
	 */
	public String getERPNumber() {
		return ERPNumber;
	}
	
	/**
	 * 设置物料编号
	 * @param ERPNumber ERP物料编号
	 * @author houhf
	 */
	public void setERPNumber(String ERPNumber) {
		this.ERPNumber = ERPNumber;
	}
	
	/**
	 * 获得制造路线
	 * @return manuRoute 制造路线
	 * @author houhf
	 */
	public String getManuRoute() {
		return manuRoute;
	}
	
	/**
	 * 设置制造路线
	 * @param manuRoute 制造路线
	 * @author houhf
	 */
	public void setManuRoute(String manuRoute) {
		this.manuRoute = manuRoute;
	}
	
	/**
	 * 获得装配路线
	 * @return assRoute 装配路线
	 * @author houhf
	 */
	public String getAssRoute() {
		return assRoute;
	}
	
	/**
	 * 设置装配路线
	 * @param assRoute 装配路线
	 * @author houhf
	 */
	public void setAssRoute(String assRoute) {
		this.assRoute = assRoute;
	}
	
	/**
	 * 获得颜色件标识
	 * @return colorFlag 颜色件标识
	 */
	public String getColorFlag() {
		return colorFlag;
	}
	
	/**
	 * 设置颜色件标识
	 * @param colorFlag 颜色件标识
	 * @author houhf
	 */
	public void setColorFlag(String colorFlag) {
		this.colorFlag = colorFlag;
	}
	
	/**
	 * 获得来源
	 * @return producedBy 来源
	 * @author houhf
	 */
	public String getProducedBy() {
		return producedBy;
	}
	
	/**
	 * 设置来源
	 * @param producedBy 来源
	 * @author houhf
	 */
	public void setProducedBy(String producedBy) {
		this.producedBy = producedBy;
	}
	
	/**
	 * 获得虚拟件
	 * @return dummyPart 虚拟件
	 * @author houhf
	 */
	public String getDummyPart() {
		return dummyPart;
	}
	
	/**
	 * 设置虚拟件
	 * @param dummyPart 虚拟件
	 * @author houhf
	 */
	public void setDummyPart(String dummyPart) {
		this.dummyPart = dummyPart;
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
	 * 设置备注
	 * @param desc 备注
	 * @author houhf
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * 是否查询过BOM中间表
	 * @return isFindBOM 查询标识 true 查询过 FALSE 没查询过
	 * @author houhf
	 */
	public Boolean getIsFindBOM() {
		return isFindBOM;
	}
	
	/**
	 * 设置是否查询标识
	 * @param isFindBOM
	 * @author houhf
	 */
	public void setIsFindBOM(Boolean isFindBOM) {
		this.isFindBOM = isFindBOM;
	}
	
	/**
	 * 获得一级子件对应的link
	 * @return subParts 一级子件对应的link
	 * @author houhf
	 */
	public Collection getSubParts() {
		return subParts;
	}
	
	/**
	 * 设置一级子件对应的link
	 * @param subParts
	 * @author houhf
	 */
	public void setSubParts(Collection subParts) {
		this.subParts = subParts;
	}
	
	/**
	 * 获得半成品和原材料关联
	 * @return subLinks 半成品和原材料关联
	 * @author houhf
	 */
	public HashMap getSubLinks() {
		return subLinks;
	}
	
	/**
	 * 设置半成品和原材料关联
	 * @param subLinks
	 * @author houhf
	 */
	public void setSubLinks(HashMap subLinks) {
		this.subLinks = subLinks;
	}

	/**
	 * 根据制造路线合并规则，将半成品和原材料的路线合并到制造路线中。
	 * @return 制造路线
	 * @author houhf
	 */
	private String getRouteListString()
	{
		return null;
	}
	
	/**
	 * 获得新建标识
	 * @return isNew 新建标识
	 * @author houhf
	 */
	public String getIsNew() {
		return isNew;
	}
	
	/**
	 * 设置新建标识
	 * @param isNew 新建标识
	 * @author houhf
	 */
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	
	/**
	 * 获得新建标识
	 * @return isNew 新建标识
	 * @author houhf
	 */
	public String getPenbs() {
		return penbs;
	}
	
	/**
	 * 设置新建标识
	 * @param isNew 新建标识
	 * @author houhf
	 */
	public void setPenbs(String penbs1) {
		this.penbs = penbs1;
	}
}
