package com.faw_qm.jfpublish.receive;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentMasterIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.exception.LifeCycleException;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.model.LifeCycleTemplateMasterIfc;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;

public class LifeCycleHelper {

	 private static String logPath =
	 "/opt/pdmv4r3/domains/changeLifeCycle.log";

	 private static String errorLogPath =
	 "/opt/pdmv4r3/domains/changeLifeCycleError.log";

	 private static String recoveryLog =
	 "/opt/pdmv4r3/domains/changeLifeCycleRecover.log";
	 
	 private static String logFilePath =
	 "/opt/pdmv4r3/domains/ChangeLifeCycle";

	//private static String logPath = "E://changeLifeCycle.log";

	//private static String errorLogPath = "E://changeLifeCycleError.log";

	//private static String recoveryLog = "E://changeLifeCycleRecover.log";
	 
	//private static String logFilePath ="E://ChangeLifeCycle";

	private static PersistService ser;
	private static LifeCycleService lfs;
	private static HashMap lifeTemplateMap=new HashMap();

	// private static PrintWriter writer;

	// private static PrintWriter errorWriter;
	static {
		try {
			ser = (PersistService) EJBServiceHelper
					.getService("PersistService");
			lfs = (LifeCycleService) EJBServiceHelper
			.getService("LifeCycleService");
		} catch (ServiceLocatorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*
		 * FileOutputStream out = null; FileOutputStream errorOut = null; try {
		 * out = new FileOutputStream(logPath, true); errorOut = new
		 * FileOutputStream(errorLogPath, true); writer = new PrintWriter(out,
		 * true); errorWriter = new PrintWriter(errorOut, true); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	public LifeCycleHelper() {
		super(); // TODO Auto-generated constructor stub
	}

	public static void resetLifeCycleAndState(Collection infos) {
		try {
			if (infos != null && infos.size() != 0) {
				int total = infos.size();
				int successful = 0;
				int fail = 0;
		  	//CCBegin by liunan 2008-09-03
		  	//为输出语句添加开关。
	  		if (PublishHelper.VERBOSE)
		  	//CCEnd by liunan 2008-09-03
				System.out.print("=====>total amount: " + total);
				Iterator iter = infos.iterator();
				while (iter.hasNext()) {
					boolean flag = resetLifeCycleAndState((LifeCycleManagedIfc) iter
							.next());
					if (flag) {
						successful++;
					} else {
						fail++;
					}
				}
				String message = "======>成功数： " + successful + ",失败数: " + fail;
		  	//CCBegin by liunan 2008-09-03
		  	//为输出语句添加开关。
		  	if (PublishHelper.VERBOSE)
	  		//CCEnd by liunan 2008-09-03
				System.out.println(message);
				printMessage(message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void resetRrrorInfos() {
		resetLifeCycleAndState(getInfosFromErrorLog(LifeCycleHelper.recoveryLog));
	}
	
	public static void resetInfoByLog(int logIndex) {
		resetLifeCycleAndState(getInfosFromErrorLog(LifeCycleHelper.logFilePath+logIndex+".log"));
	}

	/**
	 * 从错误日志中取出错误对象
	 * 
	 * @param logFileName
	 *            日志文件名
	 * @return
	 */
	public static Collection getInfosFromErrorLog(String logFileName) {
		Collection result = new ArrayList();
		File file = new File(logFileName); // 文件对象
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // 获取文件输入流
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		LifeCycleManagedIfc info = null; // 文档对象
		String line = "";
		// 不断从临时文件中读出字符串

		while (true) {
			try {
				if ((line = br.readLine()) == null) {
					break;

				} else {
					info = (LifeCycleManagedIfc) ser.refreshInfo(line);
					if (info != null) {
						result.add(info);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	private static LifeCycleTemplateInfo getLatestLifeCycleTemplateByName(String lifeCycleName) throws LifeCycleException, QMException
	{
		//System.out.println("=====>"+lifeCycleName);
		if(lifeTemplateMap.get(lifeCycleName)!=null)
		{
			return (LifeCycleTemplateInfo) lifeTemplateMap.get(lifeCycleName);
		}else
		{
			LifeCycleTemplateInfo lifeCycle = lfs.getLifeCycleTemplate(lifeCycleName);
			lifeTemplateMap.put(lifeCycleName,lifeCycle);
			return lifeCycle;
		}
	}
	public static void resetDocLifeCycleByLifeCycle(String oriLifeCycle,
			String newLifeCycle) {
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>enter LifeCycleHelper.resetDocLifeCycleByLifeCycle.");
		resetLifeCycleAndState(LifeCycleHelper
				.getDocsByLifeCycleTemplate(oriLifeCycle), newLifeCycle);
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>leave LifeCycleHelper.resetDocLifeCycleByLifeCycle.");
	}

	public static void resetLifeCycleAndState(Collection infos,
			String newLifeCycle) {
		try {
			if (infos != null && infos.size() != 0) {
				int total = infos.size();
				int successful = 0;
				int fail = 0;
	  		//CCBegin by liunan 2008-09-03
	  		//为输出语句添加开关。
	  		if (PublishHelper.VERBOSE)
	  		//CCEnd by liunan 2008-09-03
				System.out.print("=====>total amount: " + total);
				Iterator iter = infos.iterator();
				while (iter.hasNext()) {
					boolean flag = resetLifeCycleAndState(
							(LifeCycleManagedIfc) iter.next(), newLifeCycle);
					if (flag) {
						successful++;
					} else {
						fail++;
					}
				}
				String message = "======>成功数： " + successful + ",失败数: " + fail;
			  //CCBegin by liunan 2008-09-03
		  	//为输出语句添加开关。
		  	if (PublishHelper.VERBOSE)
		  	//CCEnd by liunan 2008-09-03
				System.out.println(message);
				printMessage(message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void resetLifeCycleAndState(Collection infos,
			String newLifeCycle, String newState) {
		try {
			if (infos != null && infos.size() != 0) {
				int total = infos.size();
				int successful = 0;
				int fail = 0;
	  		//CCBegin by liunan 2008-09-03
	  		//为输出语句添加开关。
		  	if (PublishHelper.VERBOSE)
	  		//CCEnd by liunan 2008-09-03
				System.out.print("=====>total amount: " + total);
				Iterator iter = infos.iterator();
				while (iter.hasNext()) {
					boolean flag = resetLifeCycleAndState(
							(LifeCycleManagedIfc) iter.next(), newLifeCycle,
							newState);
					if (flag) {
						successful++;
					} else {
						fail++;
					}
				}
				String message = "======>成功数： " + successful + ",失败数: " + fail;
	  		//CCBegin by liunan 2008-09-03
	  		//为输出语句添加开关。
	  		if (PublishHelper.VERBOSE)
	  		//CCEnd by liunan 2008-09-03 
				System.out.println(message);
				printMessage(message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Collection getAllParts() {
		try {
			QMQuery query = new QMQuery("QMPartMaster");
			Collection coll = (Collection) ser.findValueInfo(query, false);
			Collection result = new ArrayList();
			if (coll != null || coll.size() > 0) {
				Iterator iter = coll.iterator();
				while (iter.hasNext()) {
					result.addAll(PublishHelper
							.getAllIterationsOf((MasteredIfc) iter.next()));
				}
			}
			//CCBegin by liunan 2008-09-03
			//为输出语句添加开关。
			if (PublishHelper.VERBOSE)
			//CCEnd by liunan 2008-09-03
			System.out.println("LifeCycleHelper.getAllParts(),part amouts: "
					+ result.size());
			result=filterIteration(result);
			//CCBegin by liunan 2008-09-03
			//为输出语句添加开关。
			if (PublishHelper.VERBOSE)
			//CCEnd by liunan 2008-09-03
			System.out.println("LifeCycleHelper.getAllParts(),part amouts: "
					+ result.size());
			printMessage("LifeCycleHelper.getAllParts(),part amouts: "
					+ result.size());
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private static Collection filterIteration(Collection iterations) {
		Iterator iter=iterations.iterator();
		Collection result=new ArrayList();
		VersionControlService ser = null;
		try {
			ser = (VersionControlService) EJBServiceHelper
			.getService("VersionControlService");
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(iter.hasNext())
		{
			IteratedIfc info=(IteratedIfc) iter.next();
			if(ser.isLatestIteration(info))
			{
				result.add(info);
			}else
			{
	  		//CCBegin by liunan 2008-09-03
	  		//为输出语句添加开关。
	  		if (PublishHelper.VERBOSE)
	  		//CCEnd by liunan 2008-09-03
				System.out.println(info.getBsoID());
			}
		}
		return result;
	}

	public static Collection getAllEpms() {
		try {
			QMQuery query = new QMQuery("EPMDocumentMaster");
			Collection coll = (Collection) ser.findValueInfo(query, false);
			Collection result = new ArrayList();
			if (coll != null || coll.size() > 0) {
				Iterator iter = coll.iterator();
				while (iter.hasNext()) {
					result.addAll(PublishHelper
							.getAllIterationsOf((MasteredIfc) iter.next()));
				}
			}
			//CCBegin by liunan 2008-09-03
			//为输出语句添加开关。
			if (PublishHelper.VERBOSE)
			//CCEnd by liunan 2008-09-03
			System.out.println("LifeCycleHelper.getAllEpms(),Epm amouts: "
					+ result.size());
			result=filterIteration(result);
			//CCBegin by liunan 2008-09-03
			//为输出语句添加开关。
			if (PublishHelper.VERBOSE)
			//CCEnd by liunan 2008-09-03
			System.out.println("LifeCycleHelper.getAllEpms(),Epm amouts: "
					+ result.size());
			printMessage("LifeCycleHelper.getAllEpms(),Epm amouts: "
					+ result.size());
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static Collection getAllGenericParts() {
		try {
			QMQuery query = new QMQuery("GenericPartMaster");
			Collection coll = (Collection) ser.findValueInfo(query, false);
			Collection result = new ArrayList();
			if (coll != null || coll.size() > 0) {
				Iterator iter = coll.iterator();
				while (iter.hasNext()) {
					result.addAll(PublishHelper
							.getAllIterationsOf((MasteredIfc) iter.next()));
				}
			}
			result=filterIteration(result);
			printMessage("LifeCycleHelper.getAllGenericParts(),GP amouts: "
					+ result.size());
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * public static boolean resetLifeCycleAndState(LifeCycleManagedIfc info) {
	 * try { printInfo(info, null, null); LifeCycleService lfs =
	 * (LifeCycleService) EJBServiceHelper .getService("LifeCycleService");
	 * State stateOri = info.getLifeCycleState(); LifeCycleTemplateInfo lfTmpIfc =
	 * lfs.getLifeCycleTemplate(info); LifeCycleTemplateInfo lfTmpIfcLatest =
	 * lfs .getLifeCycleTemplate(lfTmpIfc.getLifeCycleName());
	 * lfs.reassign(info, lfTmpIfcLatest); lfs.setLifeCycleState(info,
	 * stateOri); return true; } catch (Exception e) { if (e instanceof
	 * QMException) { printError(info, ((QMException) e).getClientMessage()); }
	 * else { printError(info, e.getMessage()); } // TODO Auto-generated catch
	 * block e.printStackTrace(); return false; } }
	 */

	public static boolean resetLifeCycleAndState(LifeCycleManagedIfc info) {
		try {
			//printInfo(info, null, null);
			LifeCycleTemplateInfo lfTmpIfc = lfs.getLifeCycleTemplate(info);
			return resetLifeCycleAndState(info, lfTmpIfc.getLifeCycleName());
		} catch (Exception e) {
			if (e instanceof QMException) {
				printError(info, ((QMException) e).getClientMessage());
			} else {
				printError(info, e.getMessage());
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static boolean resetLifeCycleAndState(LifeCycleManagedIfc info,
			String newLifeCycle) {
		try {
			//printInfo(info, newLifeCycle, null);
			LifeCycleState stateOri = info.getLifeCycleState();
			LifeCycleTemplateInfo lfTmpIfcLatest = 
			getLatestLifeCycleTemplateByName(newLifeCycle);
			lfs.reassign(info, lfTmpIfcLatest);
			lfs.setLifeCycleState(info, stateOri);
			return true;
		} catch (Exception e) {
			if (e instanceof QMException) {
				printError(info, ((QMException) e).getClientMessage());
			} else {
				printError(info, e.getMessage());
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static void resetDocLifeCycleAndState(String docNumber,
			String newLifeCycle, String newState) throws QMException {
		DocMasterIfc docmaster = PublishHelper.getDocMasterByNumber(docNumber);
		Collection docs = PublishHelper.getAllIterationsOf(docmaster);
		if (docs != null && docs.size() > 0) {
			Iterator iter = docs.iterator();
			while (iter.hasNext()) {
				resetLifeCycleAndState((LifeCycleManagedIfc) iter.next(),
						newLifeCycle, newState);
			}
		}
	}

	public static void resetPartLifeCycleAndState(String partNumber,
			String newLifeCycle, String newState) throws QMException {
		QMPartMasterIfc partmaster = PublishHelper
				.getPartMasterByNumber(partNumber);
		Collection parts = PublishHelper.getAllIterationsOf(partmaster);
		if (parts != null && parts.size() > 0) {
			Iterator iter = parts.iterator();
			while (iter.hasNext()) {
				resetLifeCycleAndState((LifeCycleManagedIfc) iter.next(),
						newLifeCycle, newState);
			}
		}
	}

	public static void resetEpmLifeCycleAndState(String epmNumber,
			String newLifeCycle, String newState) throws QMException {
		EPMDocumentMasterIfc epmmaster = PublishHelper
				.getEPMDocMasterByNumber(epmNumber);
		Collection epms = PublishHelper.getAllIterationsOf(epmmaster);
		if (epms != null && epms.size() > 0) {
			Iterator iter = epms.iterator();
			while (iter.hasNext()) {
				resetLifeCycleAndState((LifeCycleManagedIfc) iter.next(),
						newLifeCycle, newState);
			}
		}
	}

	public static void resetState(LifeCycleManagedIfc info, String state) {
		try {
			LifeCycleState newState = LifeCycleState.toLifeCycleState(state);
			if (newState == null) {
		  	//CCBegin by liunan 2008-09-03
		  	//为输出语句添加开关。
		  	if (PublishHelper.VERBOSE)
		  	//CCEnd by liunan 2008-09-03
				System.out.println("LifeCycleHelper.resetState,info is: "
						+ info.getBsoID() + ",state is: " + state
						+ ",can't get that state!");
			} else {
				lfs.setLifeCycleState(info, newState, false);
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
	}

	public static void setPartState(String partNumber, String state) {
		try {
		  //CCBegin by liunan 2008-09-03
		  //为输出语句添加开关。
		  if (PublishHelper.VERBOSE)
		  //CCEnd by liunan 2008-09-03
			System.out.println("===" + state);
			QMPartIfc part = PublishHelper.getPartInfoByNumber(partNumber);
		  //CCBegin by liunan 2008-09-03
		  //为输出语句添加开关。
		  if (PublishHelper.VERBOSE)
		  //CCEnd by liunan 2008-09-03
			System.out.println("=====>part oristate: "
					+ part.getLifeCycleState().getDisplay());
		  LifeCycleState newState = LifeCycleState.toLifeCycleState(state);
			if (newState != null) {
		  	//CCBegin by liunan 2008-09-03
		  	//为输出语句添加开关。
		  	if (PublishHelper.VERBOSE)
		  	//CCEnd by liunan 2008-09-03
				System.out.println("ss: " + newState.getDisplay());
			} else {
		  	//CCBegin by liunan 2008-09-03
		  	//为输出语句添加开关。
		  	if (PublishHelper.VERBOSE)
		  	//CCEnd by liunan 2008-09-03
				System.out.println("啦啦啦");
			}

			part = (QMPartIfc) lfs.setLifeCycleState(part, newState, false);
			if (part == null) {
		  	//CCBegin by liunan 2008-09-03
		  	//为输出语句添加开关。
		  	if (PublishHelper.VERBOSE)
		  	//CCEnd by liunan 2008-09-03
				System.out.println(" sffsd ");
			}
		  //CCBegin by liunan 2008-09-03
		  //为输出语句添加开关。
		  if (PublishHelper.VERBOSE)
		  //CCEnd by liunan 2008-09-03
			System.out.println("=====>part state: "
					+ part.getLifeCycleState().getDisplay());
			// part.setLifeCycleState(newState);
			// ser.saveValueInfo(part);
			// ser.refreshInfo(part);
		} catch (QMException e) {
			e.printStackTrace();
		}
	}

	public static boolean resetLifeCycleAndState(LifeCycleManagedIfc info,
			String newLifeCycle, String newState) {
		try {
			printInfo(info, newLifeCycle, newState);
			LifeCycleState state = LifeCycleState.toLifeCycleState(newState);
			LifeCycleTemplateInfo lfTmpIfcLatest =getLatestLifeCycleTemplateByName(newLifeCycle);
		  //CCBegin by liunan 2008-09-03
		  //为输出语句添加开关。
		  if (PublishHelper.VERBOSE)
		  //CCEnd by liunan 2008-09-03
			System.out.println("=====>resetLifeCycleAndState,newLifeCycle: "
					+ newLifeCycle + ",newState: " + newState + ",templateID: "
					+ lfTmpIfcLatest.getBsoID());
			info = lfs.reassign(info, lfTmpIfcLatest);
			lfs.setLifeCycleState(info, state, true);
			return true;
		} catch (Exception e) {
			if (e instanceof QMException) {
				printError(info, ((QMException) e).getClientMessage());
			} else {
				printError(info, e.getMessage());
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static void printInfo(LifeCycleManagedIfc info, String newLifeCycle,
			String newState) {
		PrintWriter writer = null;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(logPath);
			writer = new PrintWriter(out, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printInfoMessage(info, writer);
		printLifeCycleMessage(newLifeCycle, newState, writer);
		writer.close();

	}

	private static void printLifeCycleMessage(String newLifeCycle,
			String newState, PrintWriter writer) {
		if (newLifeCycle != null && newLifeCycle.length() > 0) {
			writer.println("新的生命周期模板为:" + newLifeCycle + ",");
		}
		if (newState != null && newState.length() > 0) {
			writer.println("新的生命周期状态为:" + newState);
		}
	}

	private static void printInfoMessage(LifeCycleManagedIfc info,
			PrintWriter mywriter) {

		if (info instanceof DocIfc) {
			printDocInfoMessage((DocIfc) info, mywriter);
		} else if (info instanceof EPMDocumentIfc) {
			printEPMInfoMessage((EPMDocumentIfc) info, mywriter);
		} else if (info instanceof QMPartIfc) {
			printPartInfoMessage((QMPartIfc) info, mywriter);
		}
	}

	public static void printDocInfoMessage(DocIfc info, PrintWriter mywriter) {
		mywriter.print("更新文档" + info.getDocNum() + "的生命周期模板和生命周期状态，");
		mywriter.flush();
	}

	public static void printEPMInfoMessage(EPMDocumentIfc info,
			PrintWriter mywriter) {
		mywriter.print("更新EPM文档" + info.getDocNumber() + "的生命周期模板和生命周期状态，");
		mywriter.flush();
	}

	public static void printPartInfoMessage(QMPartIfc info, PrintWriter mywriter) {
		mywriter.print("更新零部件" + info.getPartNumber() + "的生命周期模板和生命周期状态，");
		mywriter.flush();
	}

	public static void resetAllPartsLifeCycle() {
  	//CCBegin by liunan 2008-09-03
	  //为输出语句添加开关。
  	if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>enter LifeCycleHelper.resetAllPartsLifeCycle.");

		resetLifeCycleAndState(LifeCycleHelper.getAllParts());
		resetLifeCycleAndState(LifeCycleHelper.getAllGenericParts());
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>leave LifeCycleHelper.resetAllPartsLifeCycle.");
	}

	public static void resetAllEpmsLifeCycle() {
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>enter LifeCycleHelper.resetAllEpmsLifeCycle.");
		resetLifeCycleAndState(LifeCycleHelper.getAllEpms());
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>leave LifeCycleHelper.resetAllEpmsLifeCycle.");
	}

	public static void resetEpmsByLifeCycle(String oriLifeCycle) {
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>enter LifeCycleHelper.resetEpmsByLifeCycle.");
		resetLifeCycleAndState(LifeCycleHelper
				.getEpmsByLifeCycleTemplate(oriLifeCycle));
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>leave LifeCycleHelper.resetEpmsByLifeCycle.");
	}

	public static void resetPartsByLifeCycle(String oriLifeCycle) {
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>enter LifeCycleHelper.resetPartsByLifeCycle.");
		resetLifeCycleAndState(LifeCycleHelper
				.getPartsByLifeCycleTemplate(oriLifeCycle));
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>leave LifeCycleHelper.resetPartsByLifeCycle.");
	}

	public static void resetEpmsNotLifeCycle(String oriLifeCycle) {
		resetLifeCycleAndState(LifeCycleHelper
				.getEpmsNotLifeCycleTemplate(oriLifeCycle));
	}

	public static void resetPartsNotLifeCycle(String oriLifeCycle) {
		resetLifeCycleAndState(LifeCycleHelper
				.getPartsNotLifeCycleTemplate(oriLifeCycle));
	}

	public static void resetEpmsByLifeCycle(String oriLifeCycle,
			String newLifeCycle) {
		resetLifeCycleAndState(LifeCycleHelper
				.getEpmsByLifeCycleTemplate(oriLifeCycle), newLifeCycle);
	}

	public static void resetPartsByLifeCycle(String oriLifeCycle,
			String newLifeCycle) {
		resetLifeCycleAndState(LifeCycleHelper
				.getPartsByLifeCycleTemplate(oriLifeCycle), newLifeCycle);
	}

	public static void resetPartsByLifeCycle(String oriLifeCycle,
			String newLifeCycle, String newLifeCycleState) {
		Collection parts = LifeCycleHelper
				.getPartsByLifeCycleTemplate(oriLifeCycle);
		resetLifeCycleAndState(parts, newLifeCycle, newLifeCycleState);
	}

	public static void resetEpmsNotLifeCycle(String oriLifeCycle,
			String newLifeCycle) {
		resetLifeCycleAndState(LifeCycleHelper
				.getEpmsNotLifeCycleTemplate(oriLifeCycle), newLifeCycle);
	}

	public static void resetPartsNotLifeCycle(String oriLifeCycle,
			String newLifeCycle) {
		resetLifeCycleAndState(LifeCycleHelper
				.getPartsNotLifeCycleTemplate(oriLifeCycle), newLifeCycle);
	}

	// DocServiceHelper.reassignLifyCycleTemplate(String bsoID,String
	// lfName)
	// DocServiceHelper.setLifeCycleState(String bsoID,String stateStr,
	// boolean flag)

	public static Collection getDocsByLifeCycleTemplate(
			String lifeCycleTemplateName) {
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("lifeCycle is: " + lifeCycleTemplateName);
		try {
	
			LifeCycleTemplateMasterIfc master = (LifeCycleTemplateMasterIfc) lfs
					.getLifeCycleTemplate(lifeCycleTemplateName).getMaster();
			Collection iterations = PublishHelper.getAllIterationsOf(master);
			Collection result = new ArrayList();
			if (iterations != null) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句添加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out.println("iteration size: " + iterations.size());
				Iterator iter123 = iterations.iterator();
				while (iter123.hasNext()) {
					LifeCycleTemplateInfo info = (LifeCycleTemplateInfo) iter123
							.next();
		      //CCBegin by liunan 2008-09-03
	      	//为输出语句添加开关。
		      if (PublishHelper.VERBOSE)
	      	//CCEnd by liunan 2008-09-03
					System.out.println("lala: " + info.getBsoID());
					QMQuery query = new QMQuery("Doc");
					QueryCondition condition = new QueryCondition(
							"lifeCycleTemplate", QueryCondition.EQUAL, info
									.getBsoID());
					query.addCondition(condition);
					Collection co = (Collection) ser
							.findValueInfo(query, false);
	      	//CCBegin by liunan 2008-09-03
	      	//为输出语句添加开关。
      		if (PublishHelper.VERBOSE)
	      	//CCEnd by liunan 2008-09-03
					System.out.println("co size: " + co.size());
					result.addAll(co);
				}
			}
	  	//CCBegin by liunan 2008-09-03
	  	//为输出语句添加开关。
	  	if (PublishHelper.VERBOSE)
	  	//CCEnd by liunan 2008-09-03
			System.out.println("coll size: " + result.size());
			LifeCycleHelper.printDocs(result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static Collection getEpmsByLifeCycleTemplate(
			String lifeCycleTemplateName) {
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("lifeCycle is: " + lifeCycleTemplateName);
		try {
			LifeCycleTemplateMasterIfc master = (LifeCycleTemplateMasterIfc) lfs
					.getLifeCycleTemplate(lifeCycleTemplateName).getMaster();
			Collection iterations = PublishHelper.getAllIterationsOf(master);
			Collection result = new ArrayList();
			if (iterations != null) {
	    	//CCBegin by liunan 2008-09-03
    		//为输出语句添加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out.println("iteration size: " + iterations.size());
				Iterator iter123 = iterations.iterator();
				while (iter123.hasNext()) {
					LifeCycleTemplateInfo info = (LifeCycleTemplateInfo) iter123
							.next();
	      	//CCBegin by liunan 2008-09-03
	      	//为输出语句添加开关。
	      	if (PublishHelper.VERBOSE)
      		//CCEnd by liunan 2008-09-03
					System.out.println("lala: " + info.getBsoID());
					QMQuery query = new QMQuery("EPMDocument");
					QueryCondition condition = new QueryCondition(
							"lifeCycleTemplate", QueryCondition.EQUAL, info
									.getBsoID());
					query.addCondition(condition);
					Collection co = (Collection) ser
							.findValueInfo(query, false);
	      	//CCBegin by liunan 2008-09-03
	      	//为输出语句添加开关。
	      	if (PublishHelper.VERBOSE)
	      	//CCEnd by liunan 2008-09-03 
					System.out.println("co size: " + co.size());
					result.addAll(co);
				}
			}
  		//CCBegin by liunan 2008-09-03
	  	//为输出语句添加开关。
	  	if (PublishHelper.VERBOSE)
	  	//CCEnd by liunan 2008-09-03
			System.out.println("coll size: " + result.size());
			LifeCycleHelper.printEpms(result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static Collection getPartsByLifeCycleTemplate(
			String lifeCycleTemplateName) {
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("lifeCycle is: " + lifeCycleTemplateName);
		try {
			LifeCycleTemplateMasterIfc master = (LifeCycleTemplateMasterIfc) lfs
					.getLifeCycleTemplate(lifeCycleTemplateName).getMaster();
			Collection iterations = PublishHelper.getAllIterationsOf(master);
			Collection result = new ArrayList();
			if (iterations != null) {
	    	//CCBegin by liunan 2008-09-03
    		//为输出语句添加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out.println("iteration size: " + iterations.size());
				Iterator iter123 = iterations.iterator();
				while (iter123.hasNext()) {
					LifeCycleTemplateInfo info = (LifeCycleTemplateInfo) iter123
							.next();
		      //CCBegin by liunan 2008-09-03
	      	//为输出语句添加开关。
      		if (PublishHelper.VERBOSE)
	      	//CCEnd by liunan 2008-09-03
					System.out.println("lala: " + info.getBsoID());
					QMQuery query1 = new QMQuery("QMPart");
					QMQuery query2 = new QMQuery("GenericPart");
					QueryCondition condition = new QueryCondition(
							"lifeCycleTemplate", QueryCondition.EQUAL, info
									.getBsoID());
					query1.addCondition(condition);
					query2.addCondition(condition);
					Collection coP = (Collection) ser.findValueInfo(query1,
							false);
					Collection coG = (Collection) ser.findValueInfo(query2,
							false);
	      	//CCBegin by liunan 2008-09-03
	      	//为输出语句添加开关。
	      	if (PublishHelper.VERBOSE)
	      	//CCEnd by liunan 2008-09-03
	      	{
				    	System.out.println("coP size: " + coP.size());
				     	System.out.println("coG size: " + coG.size());
			  	}
	      	//CCEnd by liunan 2008-09-03
					result.addAll(coP);
					result.addAll(coG);
				}
			}
	  	//CCBegin by liunan 2008-09-03
	  	//为输出语句添加开关。
  		if (PublishHelper.VERBOSE)
	  	//CCEnd by liunan 2008-09-03
			System.out.println("coll size: " + result.size());
			LifeCycleHelper.printParts(result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static Collection getPartsNotLifeCycleTemplate(
			String lifeCycleTemplateName) {
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("lifeCycle is: " + lifeCycleTemplateName);
		LifeCycleTemplateMasterIfc master = null;
		try {
			master = (LifeCycleTemplateMasterIfc) lfs.getLifeCycleTemplate(
					lifeCycleTemplateName).getMaster();
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collection iterations = null;
		try {
			iterations = PublishHelper.getAllIterationsOf(master);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QMQuery query1 = null;
		try {
			query1 = new QMQuery("QMPart");
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QMQuery query2 = null;
		try {
			query2 = new QMQuery("GenericPart");
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (iterations != null) {
  		//CCBegin by liunan 2008-09-03
	   	//为输出语句添加开关。
  		if (PublishHelper.VERBOSE)
  		//CCEnd by liunan 2008-09-03
			System.out.println("iteration size: " + iterations.size());
			Iterator iter123 = iterations.iterator();
			while (iter123.hasNext()) {
				LifeCycleTemplateInfo info = (LifeCycleTemplateInfo) iter123
						.next();
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句添加开关。
    		if (PublishHelper.VERBOSE)
    		//CCEnd by liunan 2008-09-03
				System.out.println("lala: " + info.getBsoID());
				QueryCondition condition = new QueryCondition(
						"lifeCycleTemplate", QueryCondition.NOT_EQUAL, info
								.getBsoID());
				try {
					query1.addCondition(condition);
				} catch (QueryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					query2.addCondition(condition);
				} catch (QueryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (iter123.hasNext()) {
					query1.addAND();
					query2.addAND();
				}
			}
			try {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句添加开关。
	    	if (PublishHelper.VERBOSE)
	    	{
			    	System.out.println("query1: " + query1.getSQLSelf());
			     	System.out.println("query2: " + query2.getSQLSelf());
			  }
	    	//CCEnd by liunan 2008-09-03
			} catch (QueryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Collection templates = new ArrayList();
		Collection coll = null;
		try {
			coll = (Collection) ser.findValueInfo(query1, false);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			coll.addAll((Collection) ser.findValueInfo(query2, false));
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("coll size: " + coll.size());
		printParts(coll);
		Iterator iter = coll.iterator();
		while (iter.hasNext()) {
			QMPartIfc partInfo = (QMPartIfc) iter.next();
			if (!templates.contains(partInfo.getLifeCycleTemplate())) {
				templates.add(partInfo.getLifeCycleTemplate());
			}
			try {
		    //CCBegin by liunan 2008-09-03
	    	//为输出语句添加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("LifeCycleHelper.getPartsNotLifeCycleTemplate,part number: "
								+ partInfo.getPartNumber()
								+ ",lifecycle: "
								+ ((LifeCycleTemplateInfo) ser
										.refreshInfo(partInfo
												.getLifeCycleTemplate()))
										.getLifeCycleName()
								+ partInfo.getLifeCycleTemplate());
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Iterator i = templates.iterator();
		while (i.hasNext()) {
			try {
	     	//CCBegin by liunan 2008-09-03
    		//为输出语句添加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out.println("=====>template: "
						+ ((LifeCycleTemplateInfo) ser.refreshInfo((String) i
								.next())).getLifeCycleName());
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return coll;
	}

	private static void printDocs(Collection docs) {
		PrintWriter writer = null;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(logPath, true);
			writer = new PrintWriter(out, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (docs != null && docs.size() > 0) {
			Iterator iter = docs.iterator();
			while (iter.hasNext()) {
				DocIfc doc = (DocIfc) iter.next();
				try {
					writer.println("doc number: "
							+ doc.getDocNum()
							+ ",lifeCycle: "
							+ ((LifeCycleTemplateInfo) ser.refreshInfo(doc
									.getLifeCycleTemplate()))
									.getLifeCycleName());
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		writer.close();
	}

	private static void printParts(Collection parts) {
		PrintWriter writer = null;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(logPath, true);
			writer = new PrintWriter(out, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (parts != null && parts.size() > 0) {
			Iterator iter = parts.iterator();
			while (iter.hasNext()) {
	    	//CCBegin by liunan 2008-09-03
    		//为输出语句添加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out.println("=====>print parts!");
				QMPartIfc part = (QMPartIfc) iter.next();
				try {
					writer.println("part number: "
							+ part.getPartNumber()
							+ ",lifeCycle: "
							+ ((LifeCycleTemplateInfo) ser.refreshInfo(part
									.getLifeCycleTemplate()))
									.getLifeCycleName());
					// writer.flush();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		writer.close();
	}

	private static void printEpms(Collection epms) {
		PrintWriter writer = null;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(logPath, true);
			writer = new PrintWriter(out, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (epms != null && epms.size() > 0) {
			Iterator iter = epms.iterator();
			while (iter.hasNext()) {
				EPMDocumentIfc epm = (EPMDocumentIfc) iter.next();
				try {
					writer.println("part number: "
							+ epm.getDocNumber()
							+ ",lifeCycle: "
							+ ((LifeCycleTemplateInfo) ser.refreshInfo(epm
									.getLifeCycleTemplateIfc()))
									.getLifeCycleName());
					// writer.flush();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		writer.close();
	}

	public static void printMessage(String message) {
		PrintWriter writer = null;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(logPath, true);
			writer = new PrintWriter(out, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.println(message);
		// writer.flush();
		writer.close();
		// System.out.println("printMessage out!");
	}

	public static void printError(LifeCycleManagedIfc info, String message) {
		PrintWriter errorWriter = null;
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(errorLogPath, true);
			errorWriter = new PrintWriter(out, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printInfoMessage(info, errorWriter);
		errorWriter.print("错误信息:");
		errorWriter.flush();
		errorWriter.println(message);
		errorWriter.close();
		try {
			out = new FileOutputStream(recoveryLog, true);
			errorWriter = new PrintWriter(out, true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		errorWriter.println(info.getBsoID());
		errorWriter.close();
		// System.out.println("printMessage out!");
	}

	public static Collection getEpmsNotLifeCycleTemplate(
			String lifeCycleTemplateName) {
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("lifeCycle is: " + lifeCycleTemplateName);
		try {
			LifeCycleTemplateMasterIfc master = (LifeCycleTemplateMasterIfc) lfs
					.getLifeCycleTemplate(lifeCycleTemplateName).getMaster();
			Collection iterations = PublishHelper.getAllIterationsOf(master);
			QMQuery query = new QMQuery("EPMDocument");
			if (iterations != null) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句添加开关。
	    	if (PublishHelper.VERBOSE)
    		//CCEnd by liunan 2008-09-03
				System.out.println("iteration size: " + iterations.size());
				Iterator iter123 = iterations.iterator();
				while (iter123.hasNext()) {
					LifeCycleTemplateInfo info = (LifeCycleTemplateInfo) iter123
							.next();
		      //CCBegin by liunan 2008-09-03
	      	//为输出语句添加开关。
	      	if (PublishHelper.VERBOSE)
	      	//CCEnd by liunan 2008-09-03
					System.out.println("lala: " + info.getBsoID());
					QueryCondition condition = new QueryCondition(
							"lifeCycleTemplate", QueryCondition.NOT_EQUAL, info
									.getBsoID());
					query.addCondition(condition);
					if (iter123.hasNext()) {
						query.addAND();
					}
				}
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句添加开关。
    		if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out.println(query.getSQLSelf());
			}
			Collection templates = new ArrayList();
			Collection coll = (Collection) ser.findValueInfo(query, false);
			printEpms(coll);
  		//CCBegin by liunan 2008-09-03
  		//为输出语句添加开关。
  		if (PublishHelper.VERBOSE)
  		//CCEnd by liunan 2008-09-03
			System.out.println("coll size: " + coll.size());
			Iterator iter = coll.iterator();
			while (iter.hasNext()) {
				EPMDocumentIfc epm = (EPMDocumentIfc) iter.next();
				if (!templates.contains(epm.getLifeCycleTemplate())) {
					templates.add(epm.getLifeCycleTemplate());
				}
     		//CCBegin by liunan 2008-09-03
      	//为输出语句添加开关。
	     	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("LifeCycleHelper.getEpmsNotLifeCycleTemplate,epm number: "
								+ epm.getDocNumber()
								+ ",lifecycle: "
								+ ((LifeCycleTemplateInfo) ser.refreshInfo(epm
										.getLifeCycleTemplate()))
										.getLifeCycleName()
								+ epm.getLifeCycleTemplate());
				/*
				 * printMessage("LifeCycleHelper.getEpmsNotLifeCycleTemplate,epm
				 * number: " + epm.getDocNumber() + ",lifecycle: " +
				 * ((LifeCycleTemplateInfo) ser.refreshInfo(epm
				 * .getLifeCycleTemplate())).getLifeCycleName() +
				 * epm.getLifeCycleTemplate());
				 */
			}
			Iterator i = templates.iterator();
			while (i.hasNext()) {
				printMessage("=====>template: "
						+ ((LifeCycleTemplateInfo) ser.refreshInfo((String) i
								.next())).getLifeCycleName());
			}
			return coll;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void logAllParts(int amount)
	{
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>enter logAllParts.");
		Collection parts=getAllParts();
		parts.addAll(getAllGenericParts());
		logLifeCycleManagedInfos((ArrayList) parts,amount);
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>leave logAllParts.");
	}
	public static void logAllEpms(int amount)
	{
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>enter logAllEpms.");
		logLifeCycleManagedInfos((ArrayList) getAllEpms(),amount);
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>leave logAllEpms.");
	}
	public static void logLifeCycleDocs(int amount,String LifeCycle)
	{
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>enter logDocs.");
		logLifeCycleManagedInfos((ArrayList)LifeCycleHelper.getDocsByLifeCycleTemplate(LifeCycle),amount);
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>leave logDocs.");
	}
	public static void logLifeCycleManagedInfos(ArrayList infos, int amount) {
		if (infos == null || infos.size() == 0 || amount == 0) {
			return;
		}
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>infos amount: "+infos.size());
		//LifeCycleManagedIfc
		int infosAmount = infos.size();
		int n = infosAmount % amount;// 余数
		int m = infosAmount / amount;
		for (int i = 0; i < m; i++) {
			File logFile = new File(logFilePath  + i
					+ ".log");
			PrintWriter writer = null;
			try {
				writer = new PrintWriter(new FileWriter(logFile, true), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int j = 0; j < amount; j++) {
				LifeCycleManagedIfc info = (LifeCycleManagedIfc) infos.get(i * amount + j);
			    writer.println(info.getBsoID());
			}
			writer.close();
		}
		File logFile = new File(logFilePath + m
				+ ".log");
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(logFile, true), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int j = 1; j < n + 1; j++) {
			LifeCycleManagedIfc info = (LifeCycleManagedIfc) infos.get(infosAmount - j);
		    writer.println(info.getBsoID());
		}
		if (writer != null) {
			writer.close();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
