/** 生成程序QMRouteFormData.java	2007/05/29
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.util;
 
import java.util.HashMap;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.technics.consroute.model.SupplierInfo;



/**
 * 供应商对应的HTMLAction中各种相关信息
 * @version  3.0
 * @author 唐树涛
 *
 */
public class SupplierFormData {
  protected final static String RESOURCE =
      "com.faw_qm.change.util.ChangeResource";

  // 包含更改属性
  private HashMap supplierAttributes;



  String routekind = "";

  boolean VERBOSE = true;

  public SupplierFormData() {
	  HashMap hashMap = new HashMap();
		setAttributes(hashMap);
	//	setDocInfo(null);
		
  }

  /**
	 * 设置文档属性。
	 * 
	 * @param hashtable 文档属性哈希表
	 */
	public void setAttributes(HashMap vec) {
		this.supplierAttributes = vec;
	}// end setDocumentAttributes(HashMap)
	
	/**
	 * 根据键值和相应的值设置文档属性。
	 * 
	 * @param key 键值
	 * @param value 文档属性
	 */
	public void setAttribute(String key, Object value) {
		supplierAttributes.put(key, value);
	}// end setDocumentAttributes(Stirng ,Object)

	/**
	 * 获取文档属性。
	 * 
	 * @return 文档属性
	 */
	public HashMap getAttributes() {
		return supplierAttributes;
	}// end getDocumentAttributes()

	/**
	 * 根据键值获取相应的值。
	 * 
	 * @param key 键值
	 * @return 文档属性
	 */
	public Object getAttributes(String key) {
		return supplierAttributes.get(key);
	}// end getDocumentAttributes(String)

  /**
	 * 将Hash表转化为DocInfo。
	 * @return 转化之后的值对象
	 * @throws DocException
	 * @throws ServiceLocatorException
	 */
	public SupplierInfo hashToAimInfo() throws QMException, ServiceLocatorException {
		if (VERBOSE)
			System.out.println("类名" + this.getClass().getName() + "\n"
					+ "方法: public DocInfo hashToDocInfo()" + "\n"
					+ "描述: 执行hashToDocInfo开始");




//			 创建DocInfo（即值对象）对象
			SupplierInfo aimListInfo = new SupplierInfo();

			
			/** 以下为设值文档属性值 */

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
			System.out.println("类名" + this.getClass().getName() + "\n"
					+ "方法: public DocInfo hashToDocInfo()" + "\n"
					+ "返回值: docInfo"  + "\n"
					+ "描述: 执行hashToDocInfo结束。");
		return aimListInfo;
	}// end hashToDocInfo()

	/**
	 * 文档属性
	 */

}
