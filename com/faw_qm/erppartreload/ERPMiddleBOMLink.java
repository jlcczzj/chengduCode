/**
 * ����ERPMiddleBOMLink.java	1.0  2014/05/20
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * Ϊerp�ӿ���ʷ���ݴ������Ӹ����㲿����� ������ 20140905
 */
package com.faw_qm.erppartreload;

/**
 * ���ݻص���ERP BOM �м��ķ�װ��
 * @author houhf
 */
public class ERPMiddleBOMLink {

	//�������
	String parentNumber;
	//�Ӽ����
	String childNumber;
	//ʹ������
	int defaultUnit;
	//BOM�����
	String bomNumber;
	//�����
	String specNumber;
	//��ע
	String desc;
	//�Ӽ��Ƿ���ԭ����
	String isMater;
	
	String parentPartNumber;
	
	/**
	 * ���part�������
	 * @return parentNumber �������
	 * @author houhf
	 */
	public String getPrentPartNumber() {
		return parentNumber;
	}
	
	/**
	 * ����part�������
	 * @param parentNumber �������
	 * @author houhf
	 */
	public void setPrentPartNumber(String parentNumber) {
		this.parentNumber = parentNumber;
	}
	
	/**
	 * ��ø������
	 * @return parentNumber �������
	 * @author houhf
	 */
	public String getParentNumber() {
		return parentNumber;
	}
	
	/**
	 * ���ø������
	 * @param parentNumber �������
	 * @author houhf
	 */
	public void setParentNumber(String parentNumber) {
		this.parentNumber = parentNumber;
	}
	
	/**
	 * ����Ӽ����
	 * @return childNumber �Ӽ����
	 * @author houhf
	 */
	public String getChildNumber() {
		return childNumber;
	}
	
	/**
	 * �����Ӽ����
	 * @param childNumber �Ӽ����
	 * @author houhf
	 */
	public void setChildNumber(String childNumber) {
		this.childNumber = childNumber;
	}
	
	/**
	 * ���ʹ������
	 * @return defaultUnit ʹ������
	 * @author houhf
	 */
	public int getDefaultUnit() {
		return defaultUnit;
	}
	
	/**
	 * ����ʹ������
	 * @param defaultUnit ʹ������
	 * @author houhf
	 */
	public void setDefaultUnit(int defaultUnit) {
		this.defaultUnit = defaultUnit;
	}
	
	/**
	 * ���BOM���к�
	 * @return bomNumber BOM���к�
	 * @author houhf
	 */
	public String getBomNumber() {
		return bomNumber;
	}
	
	/**
	 * ����BOM���к�
	 * @param bomNumber
	 * @author houhf
	 */
	public void setBomNumber(String bomNumber) {
		this.bomNumber = bomNumber;
	}
	
	/**
	 * ��������
	 * @return specNumber �����
	 * @author houhf
	 */
	public String getSpecNumber() {
		return specNumber;
	}
	
	/**
	 * ���������
	 * @param specNumber �����
	 * @author houhf
	 */
	public void setSpecNumber(String specNumber) {
		this.specNumber = specNumber;
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
	 * ���� ��ע
	 * @param desc ��ע
	 * @author houhf
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * ����Ӽ��Ƿ���ԭ����
	 * @return �Ƿ���ԭ���� YΪԭ����
	 * @author houhf
	 */
	public String getIsMater() {
		return isMater;
	}
	
	/**
	 * �����Ƿ���ԭ����
	 * @param isMater �Ƿ���ԭ���� Y��ԭ����
	 * @author houhf
	 */
	public void setIsMater(String isMater) {
		this.isMater = isMater;
	}
	
}
