/** ���ɳ���QMRouteFormData.java	2007/05/29
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;
 
import java.util.HashMap;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.technics.consroute.model.SupplierInfo;



/**
 * ��Ӧ�̶�Ӧ��HTMLAction�и��������Ϣ
 * @version  3.0
 * @author ������
 *
 */
public class SupplierFormData {
  protected final static String RESOURCE =
      "com.faw_qm.change.util.ChangeResource";

  // ������������
  private HashMap supplierAttributes;



  String routekind = "";

  boolean VERBOSE = true;

  public SupplierFormData() {
	  HashMap hashMap = new HashMap();
		setAttributes(hashMap);
	//	setDocInfo(null);
		
  }

  /**
	 * �����ĵ����ԡ�
	 * 
	 * @param hashtable �ĵ����Թ�ϣ��
	 */
	public void setAttributes(HashMap vec) {
		this.supplierAttributes = vec;
	}// end setDocumentAttributes(HashMap)
	
	/**
	 * ���ݼ�ֵ����Ӧ��ֵ�����ĵ����ԡ�
	 * 
	 * @param key ��ֵ
	 * @param value �ĵ�����
	 */
	public void setAttribute(String key, Object value) {
		supplierAttributes.put(key, value);
	}// end setDocumentAttributes(Stirng ,Object)

	/**
	 * ��ȡ�ĵ����ԡ�
	 * 
	 * @return �ĵ�����
	 */
	public HashMap getAttributes() {
		return supplierAttributes;
	}// end getDocumentAttributes()

	/**
	 * ���ݼ�ֵ��ȡ��Ӧ��ֵ��
	 * 
	 * @param key ��ֵ
	 * @return �ĵ�����
	 */
	public Object getAttributes(String key) {
		return supplierAttributes.get(key);
	}// end getDocumentAttributes(String)

  /**
	 * ��Hash��ת��ΪDocInfo��
	 * @return ת��֮���ֵ����
	 * @throws DocException
	 * @throws ServiceLocatorException
	 */
	public SupplierInfo hashToAimInfo() throws QMException, ServiceLocatorException {
		if (VERBOSE)
			System.out.println("����" + this.getClass().getName() + "\n"
					+ "����: public DocInfo hashToDocInfo()" + "\n"
					+ "����: ִ��hashToDocInfo��ʼ");




//			 ����DocInfo����ֵ���󣩶���
			SupplierInfo aimListInfo = new SupplierInfo();

			
			/** ����Ϊ��ֵ�ĵ�����ֵ */

				   String codename = (String)this.getAttributes("codename");
				   String code = (String)this.getAttributes("code");
				   String people = (String)this.getAttributes("people");
				   String jname = (String)this.getAttributes("jname");
				   String telephone = (String)this.getAttributes("telephone");
				

				aimListInfo.setCodename(codename.trim());
				aimListInfo.setCode(code.trim());
				aimListInfo.setPeople(people.trim());
				aimListInfo.setJName(jname.trim());
				aimListInfo.setTelephone(telephone.trim());
				
		


			
			if (VERBOSE)
				System.out.println(" docInfo DocName: ++"
						+ aimListInfo.getBsoName() + "++\n" + "DocNum"
						);
		
			

		
		if (VERBOSE)
			System.out.println("����" + this.getClass().getName() + "\n"
					+ "����: public DocInfo hashToDocInfo()" + "\n"
					+ "����ֵ: docInfo"  + "\n"
					+ "����: ִ��hashToDocInfo������");
		return aimListInfo;
	}// end hashToDocInfo()

	/**
	 * �ĵ�����
	 */

}
