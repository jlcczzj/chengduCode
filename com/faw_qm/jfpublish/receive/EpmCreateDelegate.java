package com.faw_qm.jfpublish.receive;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import com.faw_qm.epm.epmdocument.model.EPMDocumentInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentMasterIfc;
import com.faw_qm.epm.epmdocument.util.EPMApplicationType;
import com.faw_qm.epm.epmdocument.util.EPMAuthoringAppType;
import com.faw_qm.epm.epmdocument.util.EPMDocumentType;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.persist.ejb.service.PersistService;

public class EpmCreateDelegate extends AbstractStoreDelegate {

	public static final String DEFAULT_EPMDOC_LOCATION = "\\Root\\Administrator";

	private Hashtable myEpmIBA = null;

	private Group myEpmGrp = null;

	public EpmCreateDelegate(Group epmsGrp, Hashtable epmIBA,
			PublishLoadHelper helper) {
		this.myEpmGrp = epmsGrp;
		this.myEpmIBA = epmIBA;
		this.myHelper = helper;
	}

	public ResultReport process() {
		userLog("##EPM");
		ResultReport result = new ResultReport();
		if (myEpmGrp == null || myEpmGrp.getElementCount() == 0) {
			log("没有需要创建的EPM文档");
			return result;
		}
		Enumeration enumeration = myEpmGrp.getElements();
		Element ele = null;
		String doc_nr = null;
		String doc_na = null;
		String doc_type = null;
		String doc_authApp = null;
		String doc_ownerApp = null;
		String doc_desc = null;
		String location = null;
		String lifecycle = null;
		String creater = null;
		String modifier = null;
		String version = null;
		String lifestate = null;
		String sourceVersion = null;
		int sernum = 0;
		EPMDocumentInfo doc = null;
		QMTransaction transaction = null;
		log("创建EPM信息:");

		// 遍历EPM文档组
		while (enumeration.hasMoreElements()) {
			sernum++;
			try {
				transaction = new QMTransaction();
				transaction.begin();
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 读取发布的数据
			ele = (Element) enumeration.nextElement();
			doc_nr = ((String) ele.getValue("num")).trim();
			doc_na = (String) ele.getValue("name");
			version = (String) ele.getValue("sourceVersion");
			creater = (String) ele.getValue("creater");
			modifier = (String) ele.getValue("modify");
			lifestate = (String) ele.getValue("lifestate");
			if (doc_na == null || doc_na.trim().length() == 0) {
				result.failureOne();
				String msg = "at save EPM文档 (doc_nr=" + doc_nr + " doc_na="
						+ doc_na + ") ERROR: EPM文档名称为空";

				errorLog(msg);
				continue;
			}
			if (doc_nr == null || doc_nr.trim().length() == 0) {
				result.failureOne();
				String msg = "at save EPM文档 (doc_nr=" + doc_nr + " doc_na="
						+ doc_na + ") ERROR: EPM文档编号为空";

				errorLog(msg);
				continue;
			}
			// 检查要创建的EPM文档是否已经存在
			try {
				doc = PublishHelper.getEPMDocInfoByNumber(doc_nr);
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (doc != null) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log("EPM文档 " + doc_nr + "已存在");
				userLog(sernum + "," + doc_na + "," + doc_nr + ","
						+ sourceVersion + "," + doc.getVersionValue() + ",成功");
				result.successOne();
				continue;
			}
			doc_type = (String) ele.getValue("docType");
			doc_authApp = (String) ele.getValue("authApp");
			doc_ownerApp = (String) ele.getValue("ownerApp");
			doc_desc = (String) ele.getValue("desc");
			location = (String) ele.getValue("path");
//System.out.println("获取前doc_type==================="+doc_type);
			if (location == null || location.trim().equals("")) {
				location = DEFAULT_EPMDOC_LOCATION;
				// 如果指定了EPM文档或零部件的存储目录,忽略源数据目录信息
			} else {
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

//System.out.println("获取前location==================="+location);
				location = myHelper.mapPro.getProperty("epm.location."
						+ location);
//System.out.println("获取后location==================="+location);
			}
			if (location == null) {
				location = myHelper.mapPro.getProperty("epm.location.default",
						null);
			}
			//location = PublishHelper.strDecoding(location, "ISO8859-1");
			location = PublishHelper.normalLocation(location);
			// lifecycle = (String)ele.getValue("lifecycle");
			lifecycle = myHelper.mapPro.getProperty("jfpublish.epm.lifecycle",
					null);

			if (lifecycle == null || lifecycle.trim().equals("")) {
				lifecycle = PublishLoadHelper.DEFAULT_LIFECYCLE;
			} //else {
				//lifecycle = PublishHelper.strDecoding(lifecycle, "ISO8859-1");
			//}
			sourceVersion = (String) ele.getValue("sourceVersion");
			Properties sourceProps = new Properties();
			if (myHelper.dataSource != null) {
				sourceProps.put("dataSource", myHelper.dataSource);
			}
			if (myHelper.publishDate != null) {
				sourceProps.put("publishDate", myHelper.publishDate);
			}
			if (version != null) {
				sourceProps.put("sourceVersion", version);
			}
			if (creater != null) {
				sourceProps.put("creater", creater);
			}
			if (modifier != null) {
				sourceProps.put("modifier", modifier);
			}
			if (myHelper.publishForm != null) {
				sourceProps.put("publishForm", myHelper.publishForm);
			}
			if (myHelper.noteNum != null) {
				sourceProps.put("noteNum", myHelper.noteNum);

			}
			try {
				EPMAuthoringAppType authAppT = null;
				EPMDocumentType docTypeT = null;
				EPMApplicationType ownerAppT = null;
				// EPM文档作者类型
				if (doc_authApp == null || doc_authApp.trim().length() == 0) {
					authAppT = EPMAuthoringAppType.getDefaultType();
				} else {
					doc_authApp = myHelper.mapPro.getProperty(
							"epm.authoringapplation." + doc_authApp, null);
					//doc_authApp = PublishHelper.strDecoding(doc_authApp,
							//"ISO8859-1");
					authAppT = EPMAuthoringAppType
							.toAuthoringAppType(doc_authApp);
					if (authAppT == null) {
						authAppT = EPMAuthoringAppType.getDefaultType();
					}
				}
				// EPM文档所有者类型
				if (doc_ownerApp == null || doc_ownerApp.trim().length() == 0) {
					ownerAppT = EPMApplicationType.getDefaultType();
				} else {
					ownerAppT = EPMApplicationType
							.toEPMApplicationType(doc_ownerApp);
					if (ownerAppT == null) {
						ownerAppT = EPMApplicationType.getDefaultType();
					}
				}
				// EPM文档类型
				if (doc_type == null || doc_type.trim().length() == 0) {
					docTypeT = EPMDocumentType.getDefautType();
				} else {
					//CCBegin by liunan 2008-12-10 与配置文件中标识不一致。empType应改为epmType
					//源码如下：
					/*doc_type = myHelper.mapPro.getProperty("epm.empType."
							+ doc_type);*/
					doc_type = myHelper.mapPro.getProperty("epm.epmType."
							+ doc_type);
					//CCEnd by liunan 2008-12-10
					//doc_type = PublishHelper.strDecoding(doc_type, "ISO8859-1");
					docTypeT = EPMDocumentType.toEPMDocType(doc_type);
					if (docTypeT == null) {
						docTypeT = EPMDocumentType.getDefautType();
					}
				}
//System.out.println("获取后doc_type==================="+doc_type);
				// 构造EPM文档对象
				doc = EPMDocumentInfo.newEPMDocument(doc_nr, doc_na, authAppT,
						docTypeT);

				doc.setOwnerApplication(ownerAppT);
				if (lifestate == null || lifestate.equals("")) {
					lifestate = "PREPARING";
				}
				doc.setLifeCycleState(LifeCycleState.toLifeCycleState(lifestate));
			  //CCBegin by liunan 2008-09-03
		  	//为输出语句添加开关。
		  	if (PublishHelper.VERBOSE)
			  //CCEnd by liunan 2008-09-03
				System.out.println("========>State: "
						+ LifeCycleState.toLifeCycleState(lifestate));
				if (myHelper.user != null) {
					doc.setIterationCreator(myHelper.user.getBsoID());
					doc.setIterationModifier(myHelper.user.getBsoID());
					doc.setCreator(myHelper.user.getBsoID());
				}
				if (doc_desc != null) {
					doc.setDescription(doc_desc);
				}
				EPMDocumentMasterIfc epmMaster = (EPMDocumentMasterIfc) doc
						.getMaster();
				if (epmMaster != null) {
					epmMaster.setOwnerApplication(ownerAppT);
					// assign Folder
				}
				doc = (EPMDocumentInfo) PublishHelper.assignFolder(doc,
						location);
				// lifecycle TemplateID
				// modify whj
				// DomainService
				// doservice=(DomainService)getEJBService("DomainService");
				// String domainID=doservice.getDomainID("System");
				// doc.setDomain(domainID);
				String lifeCyID = PublishHelper.getLifeCyID(lifecycle);
  			//CCBegin by liunan 2008-09-03
	  		//为输出语句添加开关。
	  		if (PublishHelper.VERBOSE)
	  		//CCEnd by liunan 2008-09-03
				System.out.println("=========>Lifecycle:" + lifecycle);
				doc.setLifeCycleTemplate(lifeCyID);

				// 发布源信息IBA设置
				if (myHelper.saveSourceInfoByIBA) {
					doc = (EPMDocumentInfo) PublishHelper
							.setPublishSourceInfoByIBA((IBAHolderIfc) doc,
									sourceProps);
					// EPM对象IBA值设置
				}
				doc = (EPMDocumentInfo) PublishHelper.setIBAValues(
						(IBAHolderIfc) doc, (Vector) myEpmIBA.get(doc
								.getDocNumber()));

				PersistService ps = (PersistService) PublishHelper
						.getEJBService("PersistService");
				// 持久化EPM对象
				doc = (EPMDocumentInfo) ps.saveValueInfo(doc);
				transaction.commit();
				log("创建EPM文档 " + doc_nr + " 成功");
				userLog(sernum + "," + doc_na + "," + doc_nr + ","
						+ sourceVersion + "," + doc.getVersionValue() + ",成功");

				result.successOne();
				// displayPart(part);
			} catch (QMException ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.failureOne();
				String msg = "at save EPM文档 (doc_nr=" + doc_nr + ") ERROR:"
						+ ex.getClientMessage();

				errorLog(msg);
				userLog(sernum + "," + doc_na + "," + doc_nr + ","
						+ sourceVersion + "," + " " + ",失败" + ","
						+ ex.getClientMessage());
				// ex.printStackTrace();
				errorLog(ex);
			} catch (Exception ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.failureOne();
				String msg = "at save EPM文档 (doc_nr=" + doc_nr + ") ERROR:"
						+ ex.getLocalizedMessage();

				errorLog(msg);
				// ex.printStackTrace();
				errorLog(ex);
				userLog(sernum + "," + doc_na + "," + doc_nr + ","
						+ sourceVersion + "," + " " + ",失败" + ","
						+ ex.getLocalizedMessage());
			}
		} // end while

		return result;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
