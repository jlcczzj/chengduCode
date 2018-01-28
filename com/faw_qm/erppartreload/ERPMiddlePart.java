package com.faw_qm.erppartreload;
/**
 * ����ERPMiddlePart.java	1.0  2014/05/20
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
//package com.faw_qm.erppartreload;

import java.util.Collection;
import java.util.HashMap;

/**
 * ERP���ݻص���ERP PART �м��ķ�װ��
 * @author houhf
 */
public class ERPMiddlePart {
	
	//�㲿�����
	String partNumber;
	//�㲿������
	String partType;
	//�㲿������
	String partName;
	//������λ
	String defaultUnit;
	//�㲿���汾
	String partVersion;
	//ERP���Ϻ�
	String ERPNumber;
	//����·�ߴ�
	String manuRoute;
	//װ��·�ߴ�
	String assRoute;
	//��ɫ����ʶ
	String colorFlag;
	//��Դ
	String producedBy;
	//�����
	String dummyPart;
	//��ע
	String desc;
	//��ʶ��ERPMiddlePart�Ƿ��ѯ��BOM�м��
	Boolean isFindBOM;
	//��Ÿ�ERPMiddlePart���е�һ���Ӽ���Ӧ��link
	Collection subParts;
	//��Ÿ�ERPMiddlePart���еİ��Ʒ��ԭ���Ϲ��� 	key�����Ʒ����ԭ���ϵĲ㼶��value�����Ʒ����ԭ���϶�Ӧ��link
	HashMap subLinks;
	//��ʶ����Ƿ���Ҫ�½�-new���½� update�Ǹ���
	String isNew;
	String penbs;
	
	
	/**
	 * ����㲿�����
	 * @return partNumber
	 * @author houhf
	 */
	public String getPartNumber(){
		return partNumber;
	}
	
	/**
	 * �����㲿�����
	 * @param partNumber �㲿�����
	 * @author houhf
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	/**
	 * ����㲿������
	 * @return partType
	 * @author houhf
	 */
	public String getPartType() {
		return partType;
	}
	
	/**
	 * �����㲿������
	 * @param partType �㲿������
	 * @author houhf
	 */
	public void setPartType(String partType) {
		this.partType = partType;
	}
	
	/**
	 * ����㲿������
	 * @return partName
	 * @author houhf
	 */
	public String getPartName() {
		return partName;
	}
	
	/**
	 * �����㲿������
	 * @param partName �㲿��������
	 * @author houhf
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
	/**
	 * ��ü�����λ
	 * @return defaultUnit ������λ
	 * @author houhf
	 */
	public String getDefaultUnit() {
		return defaultUnit;
	}
	
	/**
	 * ���ü�����λ
	 * @param defaultUnit ������λ
	 * @author houhf
	 */
	public void setDefaultUnit(String defaultUnit) {
		this.defaultUnit = defaultUnit;
	}
	
	/**
	 * ����㲿���汾
	 * @return partVersion
	 * @author houhf
	 */
	public String getPartVersion() {
		return partVersion;
	}
	
	/**
	 * �����㲿���汾
	 * @param partVersion �㲿���汾
	 * @author houhf
	 */
	public void setPartVersion(String partVersion) {
		this.partVersion = partVersion;
	}
	
	/**
	 * ���ERP���ϱ��
	 * @return ERPNumber ERP���ϱ��
	 * @author houhf
	 */
	public String getERPNumber() {
		return ERPNumber;
	}
	
	/**
	 * �������ϱ��
	 * @param ERPNumber ERP���ϱ��
	 * @author houhf
	 */
	public void setERPNumber(String ERPNumber) {
		this.ERPNumber = ERPNumber;
	}
	
	/**
	 * �������·��
	 * @return manuRoute ����·��
	 * @author houhf
	 */
	public String getManuRoute() {
		return manuRoute;
	}
	
	/**
	 * ��������·��
	 * @param manuRoute ����·��
	 * @author houhf
	 */
	public void setManuRoute(String manuRoute) {
		this.manuRoute = manuRoute;
	}
	
	/**
	 * ���װ��·��
	 * @return assRoute װ��·��
	 * @author houhf
	 */
	public String getAssRoute() {
		return assRoute;
	}
	
	/**
	 * ����װ��·��
	 * @param assRoute װ��·��
	 * @author houhf
	 */
	public void setAssRoute(String assRoute) {
		this.assRoute = assRoute;
	}
	
	/**
	 * �����ɫ����ʶ
	 * @return colorFlag ��ɫ����ʶ
	 */
	public String getColorFlag() {
		return colorFlag;
	}
	
	/**
	 * ������ɫ����ʶ
	 * @param colorFlag ��ɫ����ʶ
	 * @author houhf
	 */
	public void setColorFlag(String colorFlag) {
		this.colorFlag = colorFlag;
	}
	
	/**
	 * �����Դ
	 * @return producedBy ��Դ
	 * @author houhf
	 */
	public String getProducedBy() {
		return producedBy;
	}
	
	/**
	 * ������Դ
	 * @param producedBy ��Դ
	 * @author houhf
	 */
	public void setProducedBy(String producedBy) {
		this.producedBy = producedBy;
	}
	
	/**
	 * ��������
	 * @return dummyPart �����
	 * @author houhf
	 */
	public String getDummyPart() {
		return dummyPart;
	}
	
	/**
	 * ���������
	 * @param dummyPart �����
	 * @author houhf
	 */
	public void setDummyPart(String dummyPart) {
		this.dummyPart = dummyPart;
	}
	
	/**
	 * ��ñ�ע
	 * @return desc ��ע
	 * @author houhf
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * ���ñ�ע
	 * @param desc ��ע
	 * @author houhf
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * �Ƿ��ѯ��BOM�м��
	 * @return isFindBOM ��ѯ��ʶ true ��ѯ�� FALSE û��ѯ��
	 * @author houhf
	 */
	public Boolean getIsFindBOM() {
		return isFindBOM;
	}
	
	/**
	 * �����Ƿ��ѯ��ʶ
	 * @param isFindBOM
	 * @author houhf
	 */
	public void setIsFindBOM(Boolean isFindBOM) {
		this.isFindBOM = isFindBOM;
	}
	
	/**
	 * ���һ���Ӽ���Ӧ��link
	 * @return subParts һ���Ӽ���Ӧ��link
	 * @author houhf
	 */
	public Collection getSubParts() {
		return subParts;
	}
	
	/**
	 * ����һ���Ӽ���Ӧ��link
	 * @param subParts
	 * @author houhf
	 */
	public void setSubParts(Collection subParts) {
		this.subParts = subParts;
	}
	
	/**
	 * ��ð��Ʒ��ԭ���Ϲ���
	 * @return subLinks ���Ʒ��ԭ���Ϲ���
	 * @author houhf
	 */
	public HashMap getSubLinks() {
		return subLinks;
	}
	
	/**
	 * ���ð��Ʒ��ԭ���Ϲ���
	 * @param subLinks
	 * @author houhf
	 */
	public void setSubLinks(HashMap subLinks) {
		this.subLinks = subLinks;
	}

	/**
	 * ��������·�ߺϲ����򣬽����Ʒ��ԭ���ϵ�·�ߺϲ�������·���С�
	 * @return ����·��
	 * @author houhf
	 */
	private String getRouteListString()
	{
		return null;
	}
	
	/**
	 * ����½���ʶ
	 * @return isNew �½���ʶ
	 * @author houhf
	 */
	public String getIsNew() {
		return isNew;
	}
	
	/**
	 * �����½���ʶ
	 * @param isNew �½���ʶ
	 * @author houhf
	 */
	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
	
	/**
	 * ����½���ʶ
	 * @return isNew �½���ʶ
	 * @author houhf
	 */
	public String getPenbs() {
		return penbs;
	}
	
	/**
	 * �����½���ʶ
	 * @param isNew �½���ʶ
	 * @author houhf
	 */
	public void setPenbs(String penbs1) {
		this.penbs = penbs1;
	}
}
