package com.faw_qm.jfpublish.receive;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.affixattr.ejb.service.AffixAttrService;
import com.faw_qm.affixattr.model.AttrDefineInfo;
import com.faw_qm.affixattr.model.AttrValueInfo;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemIfc;
import com.faw_qm.content.model.URLDataInfo;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.util.DocFormData;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
//CCBegin by liunan 2008-10-23
import java.util.StringTokenizer;
//CCEnd by liunan 2008-10-23


public class DocReviseDelegate extends AbstractDocStoreDelegate {

	public DocReviseDelegate(Group docs, PublishLoadHelper helper) {
		this.myDocs = docs;
		this.myHelper = helper;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public ResultReport process() {
		userLog("##ReviseDoc");
		ResultReport result = new ResultReport();
		if (myDocs == null || myDocs.getElementCount() == 0) {
			String s = "没有要修订的文档";
			log(s);
			// userLog(s);
			return result;
		}
		Enumeration eum = myDocs.getElements();
		Element ele = null;
		String docNum = null;
		String docName = null;
		DocInfo doc = null;
		String location = null;
		String sourVersion = null;
		//CCBegin by liunan 2008-12-23
		String creater = null;
		//CCEnd by liunan 2008-12-23
		String modifier = null;
		String secret = null;
		String title = null;
		//CCBegin by liunan 2009-12-30 解决修订时文档分类未起作用的问题。
		String docType = null;
		//CCEnd by liunan 2009-12-30
		DocFormData formData = null;
		QMTransaction transaction = null;
		String prosourceversion = "";
		String fbproversion = "";
		log("修订文档信息:");
		AttrValueInfo attvalue = null;
		// 定义附加属性定义值对象
		AttrDefineInfo attDefine = null;
		AffixAttrService affService = null;
		try {
			affService = (AffixAttrService) PublishHelper
					.getEJBService("AffixAttrService");
		} catch (QMException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		PersistService pservice = null;
		try {
			pservice = (PersistService) PublishHelper
					.getEJBService("PersistService");
		} catch (QMException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int sernum = 0;
		boolean affixOk = initAffixAttrData();
		while (eum.hasMoreElements()) {
			sernum++;
			myHelper.redoczong++;
			try {
				transaction = new QMTransaction();
				transaction.begin();
				ele = (Element) eum.nextElement();
				docNum = ((String) ele.getValue("num")).trim();
				docName = ((String) ele.getValue("name"));
				sourVersion = (String) ele.getValue("sourceVersion");
				location = (String) ele.getValue("path");
				//CCBegin by liunan 2008-12-23
			  creater = (String) ele.getValue("creater");
			  //CCEnd by liunan 2008-12-23
				modifier = (String) ele.getValue("modify");
				
				//Begin by liunan 2012-03-19 修订时没有title 字段，导致后面以此为条件的判断都失效，主要是电控文档资料夹修订后都被改变了。
				title = (String) ele.getValue("title");
				//CCEnd by liunan 2012-03-19
				
				secret = (String) ele.getValue("secret");
				if (secret == null || secret.equals("")) {
					secret = "$$1";
				}
				
				//CCBegin by liunan 2009-12-30 解决修订时文档分类未起作用的问题。
				docType = (String) ele.getValue("docType");
				// 通过映射表（属性文件）取得文档类别的映射值
				docType = myHelper.mapPro.getProperty("doc.docType." + docType);
				if (docType == null) {
					docType = myHelper.mapPro.getProperty("doc.docType.default",
							null);
				}
				//CCEnd by liunan 2009-12-30
				
				if (title != null && title.indexOf("电控单元数据") != -1) {
					location = myHelper.mapPro
							.getProperty("doc.location.diankong",
									"\\Root\\电气\\36电子装置\\电控文档");
					//CCBegin by liunan 2009-05-25 为1000009开头的电控文档指定资料夹。
					if(docNum.startsWith("1000009"))
					{
						location = myHelper.mapPro.getProperty("doc.location.dkfordalianxicai",
						"\\Root\\电气\\36电子装置\\大柴锡柴公共电控文件");
					}
					//CCEnd by liunan 2009-05-25
				} else {
					if (location != null && location.length() > 0) {
						location=PublishHelper.cutPathString(location);
						if (location.startsWith("/")) {
							location = location.substring(1);
						}
						if (location.startsWith("Standard_part")) {
							location = location.substring(0, 13);
							String stand = location.substring(13);
							int l = stand.indexOf("/");
							if (l != -1 && l != 0) {
								location = location + stand.substring(0, l);
							}
						} else {
							int h = location.indexOf("/");
							int m = 0;
							if (h == -1) {
								m = location.indexOf("\\");
								if (m != -1) {
									location = location.substring(0, m);
								}
							} else {
								location = location.substring(0, h);

							}
						}

						location = myHelper.mapPro.getProperty("doc.location."
								+ location);
					}
					if (location == null) {
						location = myHelper.mapPro.getProperty(
								"doc.location.default", null);
					}
				}
				// location = PublishHelper.strDecoding(location, "ISO8859-1");
				doc = PublishHelper.getDocInfoByNumber(docNum);
				fbproversion = doc.getVersionValue();

				Vector attvalues = affService.getAffixAttr(doc);
				int i = attvalues.size();
				// boolean flag = false;
				for (int j = 0; j < i; j++) {
					attvalue = (AttrValueInfo) attvalues.elementAt(j);
					// 通过附加属性值的对象取得属性定义的ID并刷出值对象
					attDefine = (AttrDefineInfo) pservice.refreshInfo(attvalue
							.getAttrDefID(), false);
					// 如果属性定义的名称等于"version"，取出附加属性值与接收的数据比较
					if (attDefine.getAttrName().toLowerCase().trim().equals(
							"sourceversion")) {
						prosourceversion = attvalue.getValue();
					}
				}
				//CCBegin by liunan 2008-10-23
        //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
        //改用产品中版本服务采用的版本比较方法。
				//if(!(sourVersion.compareTo(prosourceversion)>0)){
        if(!getPublishFlag(sourVersion,prosourceversion))
        {
        //CCEnd by liunan 2008-10-23
					try {
						transaction.rollback();
					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					myHelper.redocnum++;
					log("文档 " + docNum + "不需要修订");
					result.successOne();
					userLog(sernum + "," + doc.getDocName() + "," + docNum + ","
							+ prosourceversion + "," + sourVersion + ","
							+ fbproversion + "," + doc.getVersionValue() + ",成功");
					continue;
				}
				formData = new DocFormData();
				
				//CCBegin by liunan 2009-05-14 解决修订时资料夹改变未起作用的问题。
				//源代码
				//formData.setDocumentAttribute("folder", location);								
				formData.setDocumentAttribute("NewLocation", location);
				//CCEnd by liunan 2009-05-14
				
				//CCBegin by liunan 2009-12-30 解决修订时文档分类未起作用的问题。
				String docCfBsoID = PublishHelper.getDocCf(docType);
				formData.setDocumentAttribute("docCfBsoID", docCfBsoID);
				//CCEnd by liunan 2009-12-30
				
				String lifecycleTemplate = myHelper.mapPro.getProperty(
						"jfpublish.doc.lifecycle", null);
				String lifecycleState = myHelper.mapPro.getProperty(
						"doc.secret." + secret, null);
				if (lifecycleTemplate == null
						|| lifecycleTemplate.trim().equals("")) {
					lifecycleTemplate = PublishLoadHelper.DEFAULT_LIFECYCLE;
				} // else {
				// lifecycleTemplate = PublishHelper.strDecoding(
				// lifecycleTemplate, "ISO8859-1");
				// }
				formData.setDocumentAttribute("lifecycleTemplate",
						lifecycleTemplate);
				// 设置生命周期状态
				formData.setDocumentAttribute("lifeCState", LifeCycleState
						.toLifeCycleState(lifecycleState));
				HashMap sourceAffixAttrMap = new HashMap();
				if (affixOk) {
					// 发布源定义存在
					AttrDefineInfo def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_dataSource);
					if (def != null) {
						sourceAffixAttrMap.put(def.getBsoID(),
								myHelper.dataSource);
					}
					// 发布源版本定义存在
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_version);
					if (def != null && sourVersion != null
							&& sourVersion.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), sourVersion);
					}
					//CCBegin by liunan 2008-12-22 修改修订后没有发布源数据创建者的问题。
					// 发布源数据的创建者
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_creater);
					if (def != null && creater != null && creater.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), creater);
					}
					//CCEnd by liunan 2008-12-22
					// 发布源数据更新者
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_modifier);
					if (def != null && modifier != null
							&& modifier.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(), modifier);
					}
					// 发布时间定义存在
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_publishDate);
					if (def != null && myHelper.publishDate != null
							&& myHelper.publishDate.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(),
								myHelper.publishDate);
					}
					// 发布方式定义存在
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_publishForm);
					if (def != null && myHelper.publishForm != null
							&& myHelper.publishForm.length() > 0) {
						sourceAffixAttrMap.put(def.getBsoID(),
								myHelper.publishForm);
					}
					// 发布令号定义存在
					def = (AttrDefineInfo) affixAttrDefMap
							.get(docAffixAttrDefName_noteNum);
					if (def != null && myHelper.noteNum != null
							&& myHelper.noteNum.length() > 0) {
						sourceAffixAttrMap
								.put(def.getBsoID(), myHelper.noteNum);
					}

				} // end 设置发布源扩展属性
				formData.setDocumentAttribute("docAttrValueList",
						sourceAffixAttrMap);
				HashMap fileDescHashMap = new HashMap();
				formData.setDocumentAttribute("fileDescriptList",
						fileDescHashMap);
				StandardDocService docService = (StandardDocService) PublishHelper
				.getEJBService("StandardDocService");
				doc = (DocInfo) docService.reviseDoc(doc, formData);
				doc = (DocInfo) checkout(doc, "检出文档");
				// formData.setDocumentAttribute("bsoID",doc.getBsoID());
				formData.setDocumentAttribute("bsoID", doc.getBsoID());
				//CCBegin by liunan 2008-12-22 修改修订后没有创建单位的问题。
				formData.setDocumentAttribute("createDept", "技术中心");
				//CCEnd by liunan 2008-12-22

        //CCBegin by liunan 2008-08-07
        //现版本doc服务中已经存在createDoc方法，但返回的是vector，中间处理过程一致，
        //此处直接调用最新版本服务，然后得到docInfo。
        //原代码如下
				//doc = (DocInfo) docService.updateDoc(formData);
				Vector docvec = (Vector) docService.updateDoc(formData);
				doc = (DocInfo)docvec.elementAt(0);
				//CCBegin by liunan 2008-08-07

				doc = (DocInfo) checkin(doc, "检入文档");
				// 如果新版本的文档名称和原来的不相同，则重命名
				if (docName != null && !docName.equals(doc.getName())) {
					formData = new DocFormData();
					formData.setDocumentAttribute("bsoID", doc.getBsoID());
					formData.setDocumentAttribute("docNum", doc.getDocNum());
					formData.setDocumentAttribute("docName", docName);
					docService.renameDoc(formData);
				}
				removeContents(doc);
				transaction.commit();
				myHelper.redocnum++;
				log("修订文档 " + docNum + " 成功");
				result.successOne();
				userLog(sernum + "," + doc.getDocName() + "," + docNum + ","
						+ prosourceversion + "," + sourVersion + ","
						+ fbproversion + "," + doc.getVersionValue() + ",成功");
			} catch (QMException e) {
				e.printStackTrace();
				errorLog("修订文档 " + docNum + " 失败");
				errorLog(e);
				try {
					transaction.rollback();
				} catch (QMException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.myHelper.logErrorDoc(docNum);// 将未能成功修订的描述文档记录到错误日志中
				this.myHelper.failedDocs.add(docNum);
				result.failureOne();
				userLog(sernum + "," + doc.getDocName() + "," + docNum + ","
						+ prosourceversion + "," + sourVersion + ","
						+ fbproversion + "," + " " + ",失败" + ","
						+ e.getClientMessage());

			}
		}
		return result;

	}

	private void removeContents(DocInfo doc) throws QMException {
		ContentService cs = (ContentService) PublishHelper
				.getEJBService("ContentService");
		Vector v = cs.getContents(doc);
		ContentItemIfc item;
		for (Iterator iter = v.iterator(); iter.hasNext();) {
			item = (ContentItemIfc) iter.next();
			if (item instanceof ApplicationDataInfo) {
				cs.deleteApplicationData(doc, (ApplicationDataInfo) item);
			} else {
				cs.deleteURLData(doc, (URLDataInfo) item);
			}
		}
	}

	private WorkableIfc checkin(WorkableIfc obj, String note)
			throws QMException {
		if (obj == null) {
			return obj;
		}
		if (WorkInProgressHelper.isCheckedOut(obj)) {
			obj = ((WorkInProgressService) PublishHelper
					.getEJBService("WorkInProgressService")).checkin(obj, note);
		}
		return obj;
	}

	private WorkableIfc checkout(WorkableIfc obj, String note)
			throws QMException {
		if (obj == null) {
			return obj;
		}
		if (WorkInProgressHelper.isCheckedOut(obj)) {
			if (!WorkInProgressHelper.isWorkingCopy(obj)) {
				obj = (WorkableIfc) PublishHelper.workingCopyOf(obj);
			}
		} else {
			// if (!helper.inPersonalFolder(obj))
			{
				PublishHelper.checkOut(obj, note);
				obj = (WorkableIfc) PublishHelper.workingCopyOf(obj);
			}
		}
		return obj;
	}


        /**
         * 比较两个版本的大小，决定是否发布此对象。
         * @param s1 String 汽研最新版本。
         * @param s2 String 解放pdm中记录的该对象对应汽研的版本。
         * @return boolean 需要发布时返回true，否则返回false。
         * @author liunan 2008-10-23
         */
        private boolean getPublishFlag(String s1, String s2)
        {
          if(s1.indexOf(".")<0)
          {
            s1 = s1 + ".1";
          }
          if (s1.equals(s2)) {
            return false;
          }

          //利用StringTokenizer分割st1
          StringTokenizer st1 = new StringTokenizer(s1, ".");
          //利用StringTokenizer分割st2
          StringTokenizer st2 = new StringTokenizer(s2, ".");
          int level1 = st1.countTokens();
          int level2 = st2.countTokens();
          //两个版本值长度大为较新的
          if (level1 < level2) {
            return false;
          }
          if (level1 > level2) {
            return true;
          }
          String[] sarray1 = new String[level1];
          String[] sarray2 = new String[level2];
          //把分割后的字串，加入字符串数组
          for (int i = 0; i < level1; i++) {
            sarray1[i] = st1.nextToken();
            sarray2[i] = st2.nextToken();
          }
          //在循环中比较数组中的字串
          for (int k = 0; k < level1; k++) {
            if (sarray1[k].length() > sarray2[k].length()) {
              return true;
            }
            if (sarray1[k].length() < sarray2[k].length()) {
              return false;
            }
            if (sarray1[k].compareTo(sarray2[k]) < 0) {
              return false;
            }
            if (sarray1[k].compareTo(sarray2[k]) > 0) {
              return true;
            }
          }
          return false;
        }

}
