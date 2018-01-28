package com.faw_qm.jfpublish.receive;
// SS1 技术中心发零部件发布到中心设计视图 刘家坤 2014-7-8
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.part.util.Unit;
import com.faw_qm.pcfg.family.model.GenericPartInfo;
import com.faw_qm.pcfg.logic.LogicBase;
import com.faw_qm.util.EJBServiceHelper;

//CCBegin by liunan 2009-11-06
import java.io.IOException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import com.faw_qm.integration.util.QMProperties;
//CCEnd by liunan 2009-11-06

public class PartCreateDelegate extends AbstractStoreDelegate {

	public static final String DEFAULT_PART_LOCATION = "\\Root\\Administrator";

	private Group myParts;

	private Hashtable myPartIBAMap;
	
	//CCBegin by liunan 2009-11-06 添加对logicbase的保存
	private static String LOGICBASE_PATH;

	static {
		try {
			LOGICBASE_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.logicbase.path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static SimpleDateFormat logDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd-HH.mm");
	//CCEnd by liunan 2009-11-06

	public PartCreateDelegate(Group parts, Hashtable partIBAMap,
			PublishLoadHelper helper) {
		this.myParts = parts;
		this.myPartIBAMap = partIBAMap;
		this.myHelper = helper;
	}

	public ResultReport process() {
		userLog("##Part");
		ResultReport result = new ResultReport();
		if (myParts == null || myParts.getElementCount() == 0) {
			log("没有需要创建的零部件");
			return result;
		}
		StandardPartService ser = null;
		try {
			ser = (StandardPartService) EJBServiceHelper
					.getService("StandardPartService");
		} catch (QMException ex) {
			String msg = "ERROR:" + ex.getLocalizedMessage();
			errorLog(msg);
			errorLog(ex);
			return result;
		} catch (Exception ex) {

			String msg = "ERROR:" + ex.getLocalizedMessage();
			errorLog(msg);
			errorLog(ex);
			return result;
		}

		Enumeration enumeration = myParts.getElements();
		Element ele = null;
		String num = null;
		String name = null;
		String location = null;
		String partType = null;
		String source = null;
		String unit = null;
		String lifecycle = null;
		String creater = null;
		String modifier = null;
		String viewName = null;
		String gType = null;
		String sourceVersion = null;
		String maxCompIdUsed = null;
		//CCBegin by liunan 2008-10-28
		//添加生命周期状态的发布。
		String lifecyclestate = "";
		//CCEnd by liunan 2008-10-28
		//CCBegin by liunan 2009-03-18
		boolean flag = false;
		//CCEnd by liunan 2009-03-18
		//CCBegin by liunan 2009-12-07 添加是否有附件的标识。
		//String isHasCont = ""; 
		//CCEnd by liunan 2009-12-07
		QMTransaction transaction = null;
		QMPartIfc part = null;
		boolean isgpart = false;
		int serNum = 0;
		log("创建零部件信息：");
		System.out.println("anan 创建零部件：");
		Element node = null;
		while (enumeration.hasMoreElements()) {
			try {
				transaction = new QMTransaction();
				transaction.begin();
			} catch (QMException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			ele = (Element) enumeration.nextElement();
			num = ((String) ele.getValue("num")).trim();
			System.out.print("零件："+num);
			//CCBegin by liunan 2011-09-29 去掉名称中空格。
			//name = (String) ele.getValue("name");
			name = ((String) ele.getValue("name")).trim();
			//CCEnd by liunan 2011-09-29
			//CCBegin by liunan 2009-09-24 如果名称中含有英文“;”，则改成中文“；”
			name = name.replaceAll(";","；");
			//CCBegin by liunan 2009-09-24
			creater = (String) ele.getValue("creater");
			modifier = (String) ele.getValue("modify");
			gType = (String) ele.getValue("type");
			sourceVersion = (String) ele.getValue("sourceVersion");
			isgpart = gType.toLowerCase().trim().equals("genericpart");
			
			//CCBegin by liunan 2009-12-07 获得是否有附件的标识。
			//isHasCont = (String) ele.getValue("isHasCont");
			//CCEnd by liunan 2009-12-07

		  //CCBegin by liunan 2008-10-28
	  	//添加生命周期状态的发布。
			lifecyclestate = (String) ele.getValue("lifecyclestate");
			//CCBegin by liunan 2009-03-18
			if(lifecyclestate.equals("ADVANCEPREPARE"))
			{
				flag = true;
			}
			//CCEnd by liunan 2009-03-18
			//System.out.println("汽研零部件生命周期状态======="+lifecyclestate);
			//通过属性文件获取与之对应的解放生命周期状态。
			lifecyclestate = myHelper.mapPro.getProperty("part.lifecyclestate." + lifecyclestate);
			//System.out.println("解放零部件生命周期状态======="+lifecyclestate);
		  //CCEnd by liunan 2008-10-28

			if (name == null || name.trim().length() == 0) {
				result.failureOne();
				String msg = "at save Part (num=" + num + " name=" + name
						+ ") ERROR: 零部件名称为空";
				String s = num + "," + sourceVersion + "," + gType + ",失败";
				result.addErroMsg(s);
				errorLog(msg);
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			if (num == null || num.trim().length() == 0) {
				result.failureOne();
				String msg = "at save Part (num=" + num + " name=" + name
						+ ") ERROR: 零部件编号为空";
				String s = num + "," + name + "," + sourceVersion + "," + gType
						+ ",失败";
				result.addErroMsg(s);
				errorLog(msg);
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			myHelper.zong++;
			serNum++;
			// 判断要创建的零部件或广义部件在系统中是否已经存在
			if (isgpart) {
				try {
					part = PublishHelper.getGPartInfoByNumber(num);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (part != null) {
					try {
						transaction.rollback();
					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					log("广义部件 " + num + " 已存在");
					userLog(serNum + "," + name + "," + num + "," + "广义零部件"
							+ "," + sourceVersion + ","
							+ part.getVersionValue() + ",成功");
					this.myHelper.partsCannotStore.add(part.getPartNumber());
					//CCBegin by liunan 2011-04-22 对于已存在的，提示成功，但不往partVector中添加，防止结构多套。
					//myHelper.partVector.addElement(num);
					//CCEnd by liunan 2011-04-22
					result.successOne();
					myHelper.sucnum++;
					if (PublishHelper.VERBOSE)
						System.out.println("======>PartCreateDelegate,part: "
								+ part.getPartNumber()
								+ " exists!put into \"partsCannotStore\"!");
					continue;
				}
			} else {
				try {
					part = PublishHelper.getPartInfoByNumber(num);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (part != null) {
					try {
						transaction.rollback();
					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					log("零部件 " + num + " 已存在");
					userLog(serNum + "," + name + "," + num + "," + "零部件" + ","
							+ sourceVersion + "," + part.getVersionValue()
							+ ",成功");
					this.myHelper.partsCannotStore.add(part.getPartNumber());
					//CCBegin by liunan 2011-04-22 对于已存在的，提示成功，但不往partVector中添加，防止结构多套。
					//myHelper.partVector.addElement(num);
					//CCEnd by liunan 2011-04-22
					result.successOne();
					myHelper.sucnum++;
					if (PublishHelper.VERBOSE)
						System.out.println("======>PartCreateDelegate,part: "
								+ part.getPartNumber()
								+ " exists!put into \"partsCannotStore\"!");
					continue;
				}
			}
			partType = (String) ele.getValue("partType");
			source = (String) ele.getValue("source");
			unit = (String) ele.getValue("unit");
			location = (String) ele.getValue("path");
			if (PublishHelper.VERBOSE)
				System.out.println("=========>location ori: " + location);
			maxCompIdUsed = (String) ele.getValue("maxCompIdUsed");
			if(maxCompIdUsed == null)
			{
				maxCompIdUsed = Long.toString(1L);
			}
			if(location.equals("/Default"))
			{
				location = null;
			}
			if (location == null || location.trim().equals("")) {
				if (PublishHelper.VERBOSE)
					System.out.println("=========>location is null!");
				//location = DEFAULT_PART_LOCATION;
				location = myHelper.mapPro.getProperty("part.location.default");
				// 读取属性文件，获取文件夹对应值
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
				if (PublishHelper.VERBOSE)
					System.out.println("=========>location after process: "
							+ location);
				if (isgpart) {
					location = myHelper.mapPro.getProperty("gpart.location."
							+ location, null);
		    	//CCBegin by liunan 2008-09-03
		    	//为输出语句添加开关。
		    	if (PublishHelper.VERBOSE)
	    		//CCEnd by liunan 2008-09-03
					System.out.println("dddddddd");

					if (location == null) {
						location = myHelper.mapPro.getProperty(
								"gpart.location.default", "\\Root\\数据接收其它\\零部件");
					}
				} else {
		    	//CCBegin by liunan 2008-09-03
		    	//为输出语句添加开关。
		    	if (PublishHelper.VERBOSE)
		    	//CCEnd by liunan 2008-09-03
					System.out.println("gggggggggg");
					location = myHelper.mapPro.getProperty("part.location."
							+ location, null);
					if (location == null) {
						location = myHelper.mapPro.getProperty(
								"part.location.default", "\\Root\\数据接收其它\\零部件");
					}
				}
			}
			if (PublishHelper.VERBOSE)
				System.out.println("=========>location after map: "
						+ PublishHelper.strDecoding(location, "ISO8859-1"));
			// location = PublishHelper.strDecoding(location, "ISO8859-1");
			//System.out.println("location==============="+location);
			location = PublishHelper.normalLocation(location);
			if (PublishHelper.VERBOSE)
				System.out.println("=========>location after normal: "
						+ location);
			lifecycle = (String) ele.getValue("lifecycle");
			lifecycle = myHelper.mapPro.getProperty("jfpublish.part.lifecycle",
					null);
			if (lifecycle == null || lifecycle.trim().equals("")) {
				lifecycle = PublishLoadHelper.DEFAULT_LIFECYCLE;
			} // else {
			// lifecycle = PublishHelper.strDecoding(lifecycle, "ISO8859-1");
			// }
			viewName = (String) ele.getValue("viewName");

			// 读取属性文件，获得视图对应值
			viewName = myHelper.mapPro.getProperty("part.viewName." + viewName);
			// viewName = PublishHelper.strDecoding(viewName, "ISO8859-1");

			Properties sourceProps = new Properties();
			if (myHelper.dataSource != null) {
				sourceProps.put("dataSource", myHelper.dataSource);
			}
			if (myHelper.publishDate != null) {
				sourceProps.put("publishDate", myHelper.publishDate);
			}
			if (sourceVersion != null) {
				sourceProps.put("sourceVersion", sourceVersion);
			}
			if (myHelper.publishForm != null) {
				sourceProps.put("publishForm", myHelper.publishForm);
			}
			if (myHelper.noteNum != null && myHelper.noteNum.length() > 0) {
				sourceProps.put("noteNum", myHelper.noteNum);
			}
			if (creater != null) {
				sourceProps.put("creater", creater);
			}
			if (modifier != null) {
				sourceProps.put("modifier", modifier);
			}
			System.out.println("anantt create   零部件 "+num+" and sourceVersion=="+ (String) ele.getValue("sourceVersion")+"  and  sourceProps.get==="+sourceProps.get("sourceVersion"));//anan
			try {
				if (!isgpart) {
					part = new QMPartInfo();
				} else {
					part = new GenericPartInfo();
					((GenericPartInfo) part).setMaxCompIdUsed(Long.valueOf(
							maxCompIdUsed).longValue());
					String logicBase = (String) ele.getValue("logicBase");
					//CCBegin by liunan 2010-01-20 防止logicbase为null。
					if(logicBase==null)
					{
						logicBase = "";
					}
					//CCEnd by liunan 2010-01-20
					
					//CCBegin by liunan 2009-11-06 添加对logicbase的保存。
					try 
					{
						String path = LOGICBASE_PATH+num+"-"+logDateFormat.format(new java.util.Date())+".xml";
						FileWriter writer = new FileWriter(path);
						writer.write(logicBase);
						writer.flush();
						writer.close();
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					//CCEnd by liunan 2009-11-06
					
					LogicBase lb = LogicBaseConversion
							.xmlToLogicBase(logicBase);
					if (lb != null) {
						((GenericPartInfo) part).setLogicBase(lb);
					}
				}
				part.setPartName(name);
				part.setPartNumber(num);

		    //CCBegin by liunan 2008-10-28
	    	//设置发布过来的零部件状态，而不是仅仅的“生准”。
	    	//源码如下：
				//part.setLifeCycleState(State.toState("PREPARING"));//设置状态，需要改成解放发布的状态了。
				//CCBegin by liunan 2009-12-23 修改状态的表示方式。
				part.setLifeCycleState(LifeCycleState.toLifeCycleState(lifecyclestate));
				//CCEnd by liunan 2009-12-23
				//CCEnd by liunan 2008-10-28
				//CCBegin SS1
				viewName = "中心设计视图";
				//CCEnd SS1
				// 视图设置
				if (viewName != null && PublishHelper.isHasView(viewName)) {
					part.setViewName(viewName);

				} else {
					part.setViewName(PublishLoadHelper.DEFAULT_VIEW);
				}
				//
				// 设置默认单位(使用方式)
				if (unit == null || unit.trim().length() == 0) {
					part.setDefaultUnit(Unit.getUnitDefault());
				} else {
					// 读取属性文件，获得默认单位（使用方式）对应值

					unit = myHelper.mapPro.getProperty("part.unit." + unit,
							null);

					if (unit == null) {
						unit = myHelper.mapPro.getProperty("part.unit.default",
								null);

					}
					Unit unitT = Unit.toUnit(unit);
					if (unitT == null) {
						part.setDefaultUnit(Unit.getUnitDefault());
					} else {

						part.setDefaultUnit(unitT);
					}
				}
				// 设置来源
				if (source == null || source.trim().length() == 0) {

					// 读取属性文件，获得来源的对应值
					part.setProducedBy(ProducedBy.getProducedByDefault());
				} else {
					source = myHelper.mapPro.getProperty("part.source."
							+ source);
					ProducedBy produce = ProducedBy.toProducedBy(source);
					if (produce == null) {
						part.setProducedBy(ProducedBy.getProducedByDefault());
					} else {
						part.setProducedBy(produce);
					}
				}

				// 设置类型
				if (partType == null || partType.trim().length() == 0) {
					part.setPartType(QMPartType.getQMPartTypeDefault());
				} else {
					partType = myHelper.mapPro.getProperty("part.parttype."
							+ partType);
					// partType = PublishHelper.strDecoding(partType,
					// "ISO8859-1");
					QMPartType type = QMPartType.toQMPartType(partType);
					if (type == null) {
						part.setPartType(QMPartType.getQMPartTypeDefault());
					} else {
						part.setPartType(type);
					}
				}
				if (myHelper.user != null) {
					part.setIterationCreator(myHelper.user.getBsoID());
					part.setIterationModifier(myHelper.user.getBsoID());
					//CCBegin by liunan 2009-03-18
					//增加前准状态，不再需要标记。
					/*if(flag)
					{
						 part.setIterationNote("前准");
					}*/
					//CCEnd by liunan 2011-04-26
					//CCEnd by liunan 2009-03-18
					//CCBegin by liunan 2008-10-08
					//修改零部件发布时含有owner字段的问题。注释掉以下代码。
					//part.setOwner(myHelper.user.getBsoID());
					part.setOwner("");
					//CCEnd by liunan 2008-10-08
				}
				part = (QMPartInfo) PublishHelper.assignFolder(part, location);
				String lifeCyID = PublishHelper.getLifeCyID(lifecycle);
				part.setLifeCycleTemplate(lifeCyID);
				part = (QMPartInfo) PublishHelper.setPublishSourceInfoByIBA(
						(IBAHolderIfc) part, sourceProps);
				// 对象IBA值设置
				part = (QMPartInfo) PublishHelper.setIBAValues(
						(IBAHolderIfc) part, (Vector) myPartIBAMap.get(part
								.getPartNumber()));

				// 持久化对象
				part = (QMPartInfo) ser.savePart(part);
				transaction.commit();
				
				//CCBegin by liunan 2009-12-07 修改结果统计的内容，增加附件的信息。
				//String temp = isHasCont.trim().equals("true") ? "有附件" : "成功" ;
				String temp = "成功";
				if (isgpart) {
					log("创建广义部件 " + num + " 成功");
					userLog(serNum + "," + name + "," + num + "," + "广义零部件"
							+ "," + sourceVersion + ","
							+ part.getVersionValue() + "," + temp);
				} else {
					log("创建零部件 " + num + " 成功");
					userLog(serNum + "," + name + "," + num + "," + "零部件" + ","
							+ sourceVersion + "," + part.getVersionValue()
							+ "," + temp);
				}
				//CCEnd by liunan 2009-12-07
				myHelper.partVector.addElement(num);
				result.addSuccessMsg(num + "," + name + "," + sourceVersion
						+ "," + gType + ",成功");
				// String s = num + "," + name + "," + sourceVersion + "," +
				// gType
				// + ",成功";
				result.successOne();
				myHelper.sucnum++;
				// displayPart(part);
			} catch (QMException ex) {
				ex.printStackTrace();
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.partsCannotStore.add(num);
				this.myHelper.failedParts.add(num);
				this.myHelper.logErrorPart(num);// 将未能成功创建的零部件记录到错误日志中
				result.failureOne();
				String msg = "at save Part (num=" + num + ") ERROR:"
						+ ex.getClientMessage();
				String s = num + "," + name + "," + sourceVersion + "," + gType
						+ ",失败";
				result.addErroMsg(s);
				errorLog(msg);
				if (isgpart) {
					userLog(serNum + "," + name + "," + num + "," + "广义零部件"
							+ "," + sourceVersion + "," + " " + ",失败" + ","
							+ ex.getClientMessage());
				} else {
					userLog(serNum + "," + name + "," + num + "," + "零部件" + ","
							+ sourceVersion + "," + " " + ",失败" + ","
							+ ex.getClientMessage());
					// cache
					// partMap.put(part.getPartNumber(), part);
				}
				errorLog(ex);
				ex.printStackTrace();
			} catch (Exception ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.partsCannotStore.add(num);
				this.myHelper.failedParts.add(num);
				this.myHelper.logErrorPart(num);// 将未能成功创建的零部件记录到错误日志中
				result.failureOne();
				String msg = "at save Part (num=" + num + ") ERROR:"
						+ ex.getLocalizedMessage();
				String s = num + "," + name + "," + sourceVersion + "," + gType
						+ ",失败";
				result.addErroMsg(s);
				errorLog(msg);
				if (isgpart) {
					userLog(serNum + "," + name + "," + num + "," + "广义零部件"
							+ "," + sourceVersion + "," + " " + ",失败" + ","
							+ ex.getLocalizedMessage());
				} else {
					userLog(serNum + "," + name + "," + num + "," + "零部件" + ","
							+ sourceVersion + "," + " " + ",失败" + ","
							+ ex.getLocalizedMessage());
					// cache
					// partMap.put(part.getPartNumber(), part);
				}

				errorLog(ex);
			}
			System.out.println("  结束！");
			// debugInfo("**************************************************************************
			// end");
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
