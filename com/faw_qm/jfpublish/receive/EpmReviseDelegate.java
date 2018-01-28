/**
 * SS1 不唯一、非最新小版本等错误不算发布失败，已在其它变更中发布。 liunan 2014-8-18
 */


package com.faw_qm.jfpublish.receive;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemIfc;
import com.faw_qm.content.model.URLDataInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentMasterInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.iba.definition.litedefinition.BooleanDefView;
import com.faw_qm.iba.definition.litedefinition.FloatDefView;
import com.faw_qm.iba.definition.litedefinition.IntegerDefView;
import com.faw_qm.iba.definition.litedefinition.RatioDefView;
import com.faw_qm.iba.definition.litedefinition.StringDefView;
import com.faw_qm.iba.definition.litedefinition.TimestampDefView;
import com.faw_qm.iba.definition.litedefinition.URLDefView;
import com.faw_qm.iba.definition.litedefinition.UnitDefView;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.BooleanValueDefaultView;
import com.faw_qm.iba.value.litevalue.FloatValueDefaultView;
import com.faw_qm.iba.value.litevalue.IntegerValueDefaultView;
import com.faw_qm.iba.value.litevalue.RatioValueDefaultView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.litevalue.TimestampValueDefaultView;
import com.faw_qm.iba.value.litevalue.URLValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.VersionedIfc;
//CCBegin by liunan 2008-10-23
import java.util.StringTokenizer;
import com.faw_qm.version.model.IteratedIfc;
//CCEnd by liunan 2008-10-23


public class EpmReviseDelegate extends AbstractStoreDelegate {

	private Hashtable myEpmIBA = null;

	private Group myEpmGrp = null;

	public EpmReviseDelegate(Group epmsGrp, Hashtable epmIBA,
			PublishLoadHelper helper) {
		this.myEpmGrp = epmsGrp;
		epmsGrp.toXML(new PrintWriter(System.out, true), true);
		this.myEpmIBA = epmIBA;
		this.myHelper = helper;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public ResultReport process() {
		userLog("##ReviseEPM");
		ResultReport result = new ResultReport();
		IBAValueService ibaService = null;
		try {
			ibaService = (IBAValueService) PublishHelper
					.getEJBService("IBAValueService");
		} catch (QMException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		EPMDocumentInfo epmdoc = null;
		Element epmEle = null;
		String epmNum = null;
		String epmName = null;
		String version = null;
		String modifier = null;
		Properties sourceProps = null;
		DefaultAttributeContainer attc = null;
		AbstractValueView[] valueview = null;
		AbstractValueIfc value = null;
		int sernum = 0;
		String fbsourversion = "";
		String prosourceversion = "";
		// 和修订的part的关联对应的EPM的修订或发布
		if (myEpmGrp == null || myEpmGrp.getElementCount() == 0) {
			log("没有需要修订的EPM文档！");
			// userLog("没有需要修订的EPM文档！");
			return result;
		} else {
			Enumeration eum = myEpmGrp.getElements();
			// userLog("修订EPM信息");
			QMTransaction transaction = null;
			while (eum.hasMoreElements()) {
				sernum++;

				try {
					transaction = new QMTransaction();
                                        transaction.begin();
                                //CCBegin by liunan 2008-10-22
                                //添加是修订还是升级小版本的标识，true标识修订，false表示升级小版本。
                                boolean isReviseOrUp = true;
                                //CCEnd by liunan 2008-10-22

					epmEle = (Element) eum.nextElement();
					epmNum = ((String) epmEle.getValue("num")).trim();
					epmName = ((String) epmEle.getValue("name"));
					version = (String) epmEle.getValue("sourceVersion");
					modifier = (String) epmEle.getValue("modify");

					// 修订EPM文档

					epmdoc = PublishHelper.getEPMDocInfoByNumber(epmNum);
					prosourceversion = epmdoc.getVersionValue();
					epmdoc = (EPMDocumentInfo) ibaService
							.refreshAttributeContainerWithoutConstraints(epmdoc);
					attc = (DefaultAttributeContainer) epmdoc
							.getAttributeContainer();
					valueview = attc.getAttributeValues();
					int m = valueview.length;
					for (int i = 0; i < m; i++) {
						if ((valueview[i].getDefinition().getName())
								.toLowerCase().trim().equals("sourceversion")) {
							// flag = true;
							value = IBAValueObjectsFactory.newAttributeValue(
									valueview[i], epmdoc);
							fbsourversion = ((StringValueIfc) value).getValue();
						}
					}
			  	//CCBegin by liunan 2008-10-23
          //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
          //改用产品中版本服务采用的版本比较方法。
	  			//if (!(version.compareTo(fbsourversion) > 0)) {
          if(!getPublishFlag(version,fbsourversion))
          {
          //CCEnd by liunan 2008-10-23
						try {
							transaction.rollback();
						} catch (QMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						log("epm文档 " + epmNum + "不需要修订");
						userLog(sernum + "," + epmdoc.getDocName() + ","
								+ epmNum + "," + fbsourversion + "," + version
								+ "," + prosourceversion + ","
								+ epmdoc.getVersionValue() + ",成功");
						result.successOne();
						continue;
                                              }
                                              //CCBegin by liunan 2008-10-22
                                              //走到此处，表示该零部件需要发布到解放中。然后比较两个大版本，如果一样表示零部件是升级小版本，否则是修订操作。
                                              if(version.substring(0,version.indexOf(".")).equals(fbsourversion.substring(0,fbsourversion.indexOf("."))))
                                              {
                                                      isReviseOrUp = false;
                                              }
                                              //CCEnd by liunan 2008-10-22

					//CCBegin by liunan 2008-10-23
					//走到此处，表示该零部件需要发布到解放中。然后比较两个大版本，如果一样表示零部件是升级小版本，否则是修订操作。
					if(isReviseOrUp)
                                        {
                                                epmdoc = (EPMDocumentInfo) ((VersionControlService) PublishHelper
                                                        .getEJBService("VersionControlService"))
                                                        .newVersion((VersionedIfc) epmdoc);
					}
					else
                                        {
                                                EPMDocumentInfo iterationEpmDocTemp = (EPMDocumentInfo) ((VersionControlService) PublishHelper
                                                    .getEJBService("VersionControlService"))
                                                    .newIteration((IteratedIfc) epmdoc);
                                                epmdoc = (EPMDocumentInfo) ((VersionControlService) PublishHelper
                                                    .getEJBService("VersionControlService"))
                                                    .supersede((IteratedIfc) epmdoc,(IteratedIfc) iterationEpmDocTemp);
					}
					//CCEnd by liunan 2008-10-23
					String lifecycle = myHelper.mapPro.getProperty(
							"jfpublish.epm.lifecycle", null);

					if (lifecycle == null || lifecycle.trim().equals("")) {
						lifecycle = PublishLoadHelper.DEFAULT_LIFECYCLE;
					} // else {
					// lifecycle = PublishHelper.strDecoding(lifecycle,
					// "ISO8859-1");
					// }
					String lifeCyID = PublishHelper.getLifeCyID(lifecycle);

					epmdoc.setLifeCycleTemplate(lifeCyID);

					// 保存EPM
					epmdoc
							.setAttributeContainer(new DefaultAttributeContainer());
					PersistService ps = (PersistService) PublishHelper
							.getEJBService("PersistService");
					epmdoc = (EPMDocumentInfo) ps.saveValueInfo(epmdoc);
					// 如果名称改变则重命名
					if (!epmdoc.getDocName().equals(epmName)) {
						EPMDocumentMasterInfo info = (EPMDocumentMasterInfo) epmdoc
								.getMaster();
						info.setDocName(epmName);
						ps.updateValueInfo(info);
					}

					Vector vector = new Vector();
					if (myEpmIBA.containsKey(epmNum)) {
						vector = (Vector) myEpmIBA.get(epmNum);

					}

					sourceProps = new Properties();
					if (myHelper.dataSource != null
							&& myHelper.dataSource.length() > 0) {
						sourceProps.put("dataSource", myHelper.dataSource);
					}
					if (myHelper.publishDate != null
							&& myHelper.publishDate.length() > 0) {
						sourceProps.put("publishDate", myHelper.publishDate);
					}
					if (version != null && version.length() > 0) {
						sourceProps.put("sourceVersion", version);
					}
					if (myHelper.publishForm != null
							&& myHelper.publishForm.length() > 0) {
						sourceProps.put("publishForm", myHelper.publishForm);
					}
					if (modifier != null && modifier.length() > 0) {
						sourceProps.put("modifier", modifier);
					}
					if (myHelper.noteNum != null
							&& myHelper.noteNum.length() > 0) {
						sourceProps.put("noteNum", myHelper.noteNum);
					}
					epmdoc = (EPMDocumentInfo) updateIBA((IBAHolderIfc) epmdoc,
							sourceProps, vector);
					removeContents(epmdoc);
					transaction.commit();
					//CCBegin by liunan 2008-12-11 输入信息时区分修订还是小版本升级。
					log("修订epm文档 " + epmNum + " 成功");
                                        if(isReviseOrUp)
					    userLog(sernum + "," + epmdoc.getDocName() + "," + epmNum
							+ "," + fbsourversion + "," + version + ","
							+ prosourceversion + "," + epmdoc.getVersionValue()
							+ ",成功");
                                        else
                                          userLog(sernum + "," + epmdoc.getDocName() + "," + epmNum
                                                      + "," + fbsourversion + "," + version + ","
                                                      + prosourceversion + "," + epmdoc.getVersionValue()
                                                      + ",小版本升级");
          //CCEnd by liunan 2008-12-11
					result.successOne();

				}

				catch (QMException e) {
					try {
						transaction.rollback();
					} catch (QMException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String msg = "修订EPM文档 " + epmNum + " 失败";
					errorLog(msg);
					errorLog(e.getLocalMessage());
					e.printStackTrace();
					
					
					//CCBegin SS1
					boolean suflag = false;
					//CCEnd SS1
					
					//CCBegin by liunan 2011-10-27 屏蔽“锁定对象出错：不能锁定对象”异常
					//该异常是由于同时发布的几个变更中存在相同需要发布的结构，其中一个创建好了，
					//另一个则无法修改时报出的异常，实际已经创建成功，现修改让《基础数据发布结果统计.html》
					//中不再显示此异常，而是显示成功。
					//即 出现“锁定”异常的那行记录当做成功的来处理。
					if(msg.indexOf("锁定对象出错：不能锁定对象")>0)
					{
						result.successOne();
						//CCBegin SS1
						suflag = true;
						//CCEnd SS1
					}
					//新增屏蔽信息“对象已经修订过或不是最新版本”
					if(msg.indexOf("对象已经修订过或不是最新版本")>0)
					{
						result.successOne();
						//CCBegin SS1
						suflag = true;
						//CCEnd SS1
					}
					else
					{
						result.failureOne();
					}
					//CCEnd by liunan 2011-10-27
					
					//CCBegin SS1
					if(msg.indexOf("非最新小版本的对象")!=-10)
					{
						result.successOne();
						//CCBegin SS1
						suflag = true;
						//CCEnd SS1
					}
					else
					{
						result.failureOne();
					}
					//CCEnd SS1
					
					userLog(sernum + "," + epmdoc.getDocName() + "," + epmNum
							+ "," + fbsourversion + "," + version + ","
							//CCBegin SS1
							//+ prosourceversion + "," + " " + ",失败" + ","
							+ prosourceversion + "," + " " + (suflag?",成功":",失败") + ","
							//CCEnd SS1
							+ e.getClientMessage());
				} catch (Exception e) {
					try {
						transaction.rollback();
					} catch (QMException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String msg = "修订EPM文档 " + epmNum + " 失败";
					errorLog(msg);
					errorLog(e.getLocalizedMessage());
					e.printStackTrace();
					result.failureOne();
					userLog(sernum + "," + epmdoc.getDocName() + "," + epmNum
							+ "," + fbsourversion + "," + version + ","
							+ prosourceversion + "," + " " + ",失败" + ","
							+ e.getLocalizedMessage());

				}
			}
		}
		return result;

	}

	private void removeContents(EPMDocumentInfo epmdoc) throws QMException {
		ContentService cs = (ContentService) PublishHelper
				.getEJBService("ContentService");
		Vector v = cs.getContents(epmdoc);
		ContentItemIfc item;
		for (Iterator iter = v.iterator(); iter.hasNext();) {
			item = (ContentItemIfc) iter.next();
			if (item instanceof ApplicationDataInfo) {
				cs.deleteApplicationData(epmdoc, (ApplicationDataInfo) item);
			} else {
				cs.deleteURLData(epmdoc, (URLDataInfo) item);
			}
		}
	}

	/**
	 * 更新IBA值,但不做保存
	 *
	 * @param holder
	 * @param url
	 * @param group
	 * @return
	 * @throws java.lang.Exception
	 */
	private IBAHolderIfc updateIBA(IBAHolderIfc holder, Properties props,
			Vector vector) throws QMException {
		if (holder == null) {
			return holder;
		}
		// 事物特性持有者。
		holder = ((IBAValueService) PublishHelper
				.getEJBService("IBAValueService")).refreshAttributeContainer(
				holder, null, null, null);
		// 属性容器。
		DefaultAttributeContainer container = (DefaultAttributeContainer) holder
				.getAttributeContainer();
		container.setConstraintGroups(new Vector());
		AbstractValueView aValueView[] = container.getAttributeValues();
		// 删除容器中原有的属性值。
		for (int i = 0; aValueView != null && i < aValueView.length; i++) {
			if (aValueView[i] instanceof ReferenceValueDefaultView) {
				ReferenceValueDefaultView referenceValue = (ReferenceValueDefaultView) aValueView[i];
				if (referenceValue.getLiteIBAReferenceable() != null
						&& referenceValue.getLiteIBAReferenceable()
								.getReferenceID().indexOf("Ranking") == -1) {
					container.deleteAttributeValue(aValueView[i]);
				}
			} else if (aValueView[i] instanceof IntegerValueDefaultView) {
				IntegerValueDefaultView integerValue = (IntegerValueDefaultView) aValueView[i];
				if (integerValue.getReferenceValueDefaultView() == null) {
					container.deleteAttributeValue(aValueView[i]);
				}
			} else {
				container.deleteAttributeValue(aValueView[i]);
			}
		}

		// 添加发布数据属性值。
		if (myHelper.saveSourceInfoByIBA) {
			holder = PublishHelper.setPublishSourceInfoByIBA(holder, props);
			container = (DefaultAttributeContainer) holder
					.getAttributeContainer();

		}
		// 添加原有属性值。
		if (vector != null) {
			for (int i = 0; i < vector.size(); i++) {
				if (vector.get(i) instanceof BooleanValueDefaultView) {
					BooleanValueDefaultView booleanValue = (BooleanValueDefaultView) vector
							.get(i);
					BooleanValueDefaultView booleanValue1 = new BooleanValueDefaultView(
							(BooleanDefView) booleanValue.getDefinition(),
							booleanValue.isValue());
					if (booleanValue.getReferenceValueDefaultView() != null) {
						booleanValue1.setReferenceValueDefaultView(booleanValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(booleanValue1);
				} else if (vector.get(i) instanceof FloatValueDefaultView) {
					FloatValueDefaultView floatValue = (FloatValueDefaultView) vector
							.get(i);
					FloatValueDefaultView floatValue1 = new FloatValueDefaultView(
							(FloatDefView) floatValue.getDefinition(),
							floatValue.getValue(), floatValue.getPrecision());
					if (floatValue.getReferenceValueDefaultView() != null) {
						floatValue1.setReferenceValueDefaultView(floatValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(floatValue1);
				} else if (vector.get(i) instanceof IntegerValueDefaultView) {
					IntegerValueDefaultView integerValue = (IntegerValueDefaultView) vector
							.get(i);
					IntegerValueDefaultView integerValue1 = new IntegerValueDefaultView(
							(IntegerDefView) integerValue.getDefinition(),
							integerValue.getValue());
					if (integerValue.getReferenceValueDefaultView() == null) {
						container.addAttributeValue(integerValue1);
					}
				} else if (vector.get(i) instanceof RatioValueDefaultView) {
					RatioValueDefaultView ratioValue = (RatioValueDefaultView) vector
							.get(i);
					RatioValueDefaultView ratioValue1 = new RatioValueDefaultView(
							(RatioDefView) ratioValue.getDefinition(),
							ratioValue.getValue(), ratioValue.getDenominator());
					if (ratioValue.getReferenceValueDefaultView() != null) {
						ratioValue1.setReferenceValueDefaultView(ratioValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(ratioValue1);
				} else if (vector.get(i) instanceof ReferenceValueDefaultView) {
					ReferenceValueDefaultView referenceValue1 = (ReferenceValueDefaultView) vector
							.get(i);
					if (referenceValue1.getLiteIBAReferenceable() != null
							&& referenceValue1.getLiteIBAReferenceable()
									.getReferenceID().indexOf("Ranking") == -1) {
						container.setConstraintParameter("CSM");
						container.addAttributeValue(referenceValue1);
					}
				} else if (vector.get(i) instanceof StringValueDefaultView) {
					StringValueDefaultView stringValue = (StringValueDefaultView) vector
							.get(i);
					StringValueDefaultView stringValue1 = new StringValueDefaultView(
							(StringDefView) stringValue.getDefinition(),
							stringValue.getValue());
					if (stringValue.getReferenceValueDefaultView() != null) {
						stringValue1.setReferenceValueDefaultView(stringValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(stringValue1);
				} else if (vector.get(i) instanceof TimestampValueDefaultView) {
					TimestampValueDefaultView timestampValue = (TimestampValueDefaultView) vector
							.get(i);
					TimestampValueDefaultView timestampValue1 = new TimestampValueDefaultView(
							(TimestampDefView) timestampValue.getDefinition(),
							timestampValue.getValue());
					if (timestampValue.getReferenceValueDefaultView() != null) {
						timestampValue1
								.setReferenceValueDefaultView(timestampValue
										.getReferenceValueDefaultView());
					}
					container.addAttributeValue(timestampValue1);
				} else if (vector.get(i) instanceof UnitValueDefaultView) {
					UnitValueDefaultView unitValue = (UnitValueDefaultView) vector
							.get(i);
					UnitValueDefaultView unitValue1 = new UnitValueDefaultView(
							(UnitDefView) unitValue.getDefinition(), unitValue
									.getValue(), unitValue.getPrecision());
					if (unitValue.getReferenceValueDefaultView() != null) {
						unitValue1.setReferenceValueDefaultView(unitValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(unitValue1);
				} else if (vector.get(i) instanceof URLValueDefaultView) {
					URLValueDefaultView urlValue = (URLValueDefaultView) vector
							.get(i);
					URLValueDefaultView urlValue1 = new URLValueDefaultView(
							(URLDefView) urlValue.getDefinition(), urlValue
									.getValue(), urlValue.getDescription());
					if (urlValue.getReferenceValueDefaultView() != null) {
						urlValue1.setReferenceValueDefaultView(urlValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(urlValue1);
				}
			}
		}

		holder.setAttributeContainer(container);
		container = (DefaultAttributeContainer) ((IBAValueService) PublishHelper
				.getEJBService("IBAValueService")).updateAttributeContainer(
				holder, container != null ? container.getConstraintParameter()
						: null, null, null);

		holder.setAttributeContainer(container);
		return holder;
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
