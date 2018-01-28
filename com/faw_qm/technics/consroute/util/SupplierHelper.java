/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/01/03
 */
package com.faw_qm.technics.consroute.util;
 
import com.faw_qm.technics.consroute.ejb.service.SupplierService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.SupplierInfo;
import com.faw_qm.technics.consroute.model.SupplierIfc;

import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.query.DateHelper;
import com.faw_qm.util.EJBServiceHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 文档服务辅助类
 * @author 罗桥   修改时间  2003/02/21
 * @version 1.0
 */
public final class SupplierHelper
 {
	public static boolean VERBOSE = true;

	/**
	 * 根据供应商编号获得供应商值对像
	 * 
	 * @param s
	 *            供应商编号 *
	 * @throws QMException
	 * @return iteratorvector 所有活动
	 * @throws QMException
	 */
	public static Collection getSupplierInfo(String supplierCode)
			throws QMException {

		Collection coll = null;
		try {

			QMQuery qmquery = new QMQuery("Supplier");
			qmquery.addCondition(new QueryCondition("code", "=", supplierCode));
			PersistService pservice = (PersistService) EJBServiceHelper
					.getService("PersistService");
			coll = pservice.findValueInfo(qmquery);
		} catch (QMException e) {
			throw new QMException(e);
		}
		return coll;
	}

	/**
	 * 根据零部件BsoID获取该供应商的所有的基本属性集合。
	 * 
	 * @param partBsoID
	 *            零部件BsoID
	 * @return String[] 返回值是一个字符串数组:
	 * @throws QMException
	 */
	public String[] getChineseAttr(String supplierBsoID) throws QMException {
		if (VERBOSE) {
			System.out.println("SupplierHelper.getChineseAttr() begin......");
			// webaction和ejbaction之间的过渡类
		}
		PersistService pService = (PersistService) EJBServiceHelper
				.getService("PersistService");
		SupplierIfc partIfc = (SupplierIfc) pService.refreshInfo(supplierBsoID);
		String[] result = new String[17];
		result[0] = partIfc.getCode();
		result[1] = partIfc.getCodename();
		result[2] = partIfc.getJName();
		result[3] = partIfc.getPeople();
		result[4] = partIfc.getTelephone();
		result[5] = partIfc.getCreateTime().toString();
		result[6] = partIfc.getModifyTime().toString();

		if (VERBOSE) {
			System.out.println("SupplierHelper.getChineseAttr() End......");
			// webaction和ejbaction之间的过渡类
		}
		return result;
	}

	/**
	 * 通过供应商BsoID获得其值对象
	 * 
	 * @param id
	 *            供应商BsoID
	 * @return 供应商值对象
	 * @throws QMException
	 */
	public static SupplierInfo findSupplierInfoByID(String id)
			throws QMException {
		if (VERBOSE) {
			System.out
					.println("SupplierInfo findSupplierInfoByID(String id) begin... id is "
							+ id);
		} // endif
		SupplierInfo supplierInfo = null;
		try {
			// 获得持久化服务
			PersistService persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			supplierInfo = (SupplierInfo) persistService.refreshInfo(id);
		} // end try
		catch (Exception e) {
			throw new QMException(e);
		} // end catch
		if (VERBOSE) {
			System.out
					.println("SupplierInfo findSupplierInfoByID(String id) end... return is "
							+ supplierInfo);
		} // endif
		return supplierInfo;
	} // end findDocInfoByID(String)

	/**
	 * 搜索所有的零部件值对象
	 * 
	 * @return 所有的零部件值对象集
	 * @exception com.keche.exception.QMException
	 */
	public Vector findAllPartInfo(String partName,String checkboxSupplierName,
			String partNum, String checkboxSupplierNum, String projectteam,
			String checkboxprojTeam, String lifecycle,
			String checkboxlifeCState, String folder, String checkboxfolder,
			String textcreator, String checkboxcreator, String textcreateTime,
			String checkboxcreateTime) throws QMException {
		if (VERBOSE) {
			System.out.println("v findAllAdoptNoticeInfo() begin....");
		} // endif

		Collection collection = null;
		Collection result = null;
		try {
			// 获得持久化服务
			PersistService ps = (PersistService) EJBServiceHelper
					.getService("PersistService");

			// 创建新的查找对象query
			QMQuery query1 = new QMQuery("QMPart");
			// 根据零部件名称查询
			query1 = getFindPartInfoByName(query1, partName,checkboxSupplierName);
			// 根据零部件编号查询
			query1 = getFindPartInfoByNum(query1, partNum,checkboxSupplierNum);

			// 根据零部件项目组查询
			query1 = getFindPartInfoByProject(query1, projectteam,
					checkboxprojTeam);
			// 根据零部件生命周期查询
			query1 = getFindPartInfoByLifeCycle(query1, lifecycle,
					checkboxlifeCState);
			// 根据文件柜查询
			query1 = getFindPartInfoByFolder(query1, folder, checkboxfolder);

			// 根据创建者查询
			query1 = getFindPartInfoByCreator(query1, textcreator,
					checkboxcreator);
			// 根据创建time查询
			query1 = getFindPartInfoByTime(query1, textcreateTime,
					checkboxcreateTime, "createTime");
			// added by chudm for sanrq
			query1.addAND();
			QueryCondition qcdikef = new QueryCondition("iterationIfLatest",
					true);
			query1.addCondition(qcdikef);

			if (VERBOSE) {
				try {
					System.out.println(query1.getDebugSQL());
				} catch (Exception e) {
				}
			} // endif

			collection = ps.findValueInfo(query1);
			result=collection;
			// 判断集合中的内容

		} // end try
		catch (Exception e) {
			throw new QMException(e);
		} // end catch

		if (VERBOSE) {
			System.out
					.println("SupplierServiceEJB findAllSupplierInfo() end...."
							+ "return is " + result);
		} // endif
		return (Vector) result;
	} // end findAllSupplierInfo()

	public QMQuery getFindPartInfoByName(QMQuery query, String SupplierName,String checkboxSupplierName) throws QMException {
		if (VERBOSE) {
			System.out
					.println("SupplierServiceEJB.getFindPartInfoByName(QMQuery query,String SupplierName,String checkboxSupplierName) begin....");
			System.out.println("SupplierName =" + SupplierName
					);
		} // endif
		QMQuery qu = query;

		if (VERBOSE)
			System.out.println("query condition:" + qu.getConditionCount());
		try {
			// 如果供应商名称不为空，进行查询
			if (SupplierName != null && !SupplierName.trim().equals("")) {
				// 追加业务对象表
				int i = 1;
				String bsoName = null;
				try {
					bsoName = qu.getBsoNameAt(i);
				} catch (Exception ex) {
				}
				if (bsoName != null) {
					if (qu.getConditionCount() > 0)
						qu.addAND();
				} else {
					if (qu.getConditionCount() > 0)
						qu.addAND();
					i = qu.appendBso("QMPartMaster", false);
					// 关联2个表
					QueryCondition qc1 = new QueryCondition("masterBsoID",
							"bsoID");
					qu.addCondition(0, i, qc1);
					qu.addAND();
				}
				  //  如果用户查询的供应商中不想包括此供应商名称
	              if (checkboxSupplierName != null &&
	                  checkboxSupplierName.trim().equals("false")) {
	                  QueryCondition cond = new QueryCondition("partName", "like",
	                      getLikeSearchString(SupplierName));
	                  qu.addCondition(i, cond);
	              }
	              //  如果用户想查询此供应商名称
	              else {
	                  QueryCondition cond = new QueryCondition("partName",
	                      "not like", getLikeSearchString(SupplierName));
	                  qu.addCondition(i, cond);

	              }
		

				
			}
		} catch (Exception e) {
			throw new QMException(e);
		} // end catch
		if (VERBOSE) {
			System.out
					.println("v.getFindPartInfoByName(QMQuery query,String SupplierName,String checkboxSupplierName) end");

		} // endif

		return qu;

	}

	/**
	 * 根据供应商编号查询数据库
	 * 
	 * @param QMQuery
	 *            qu,String SupplierNum,String checkboxSupplierNum
	 * @return QMQuery
	 * @exception Exception
	 */
	public QMQuery getFindPartInfoByNum(QMQuery qu, String SupplierNum,String checkboxSupplierNum) throws Exception {
		if (VERBOSE) {
			System.out
					.println("getFindPartInfoByNum(QMQuery qu,String SupplierNum,String checkboxSupplierNum) begain");
			System.out.println("SupplierNum =" + SupplierNum);
		} // endif

		QMQuery query = qu;
		// 如果供应商编号不为空
		if (SupplierNum != null && !SupplierNum.trim().equals("")) {
			if (VERBOSE)
				System.out.println("query condition:"
						+ query.getConditionCount());
			// 追加业务对象表
			int i = 1;
			String bsoName = null;
			try {
				bsoName = qu.getBsoNameAt(i);
			} catch (Exception ex) {
			}

			if (bsoName != null) {
				if (query.getConditionCount() > 0)
					query.addAND();
			} else {
				if (query.getConditionCount() > 0)
					query.addAND();
				i = query.appendBso("QMPartMaster", false);
				// 关联2个表
				QueryCondition qc1 = new QueryCondition("masterBsoID", "bsoID");
				query.addCondition(0, i, qc1);
				query.addAND();
			}
				 //  如果用户查询的供应商中不想包括此供应商编号
		          if (checkboxSupplierNum != null &&
		              checkboxSupplierNum.trim().equals("false")) {
		              QueryCondition cond = new QueryCondition("partNumber", "like",
		                  getLikeSearchString(SupplierNum));
		              query.addCondition(i, cond);
		          }
		          //  如果用户想查询此供应商编号
		          else {
		              QueryCondition cond = new QueryCondition("partNumber",
		                  "not like", getLikeSearchString(SupplierNum));
		              query.addCondition(i, cond);
		          }

		}
		if (VERBOSE) {
			System.out
					.println("getFindPartInfoByNum(QMQuery qu,String SupplierNum,String checkboxSupplierNum) end");

		} // endif
		return query;
	}

	/**
	 * 根据项目组查询数据库
	 * 
	 * @param QMQuery
	 *            query1,String projectteam,String checkboxprojTeam
	 * @return QMQuery
	 * @exception Exception
	 */
	public QMQuery getFindPartInfoByProject(QMQuery query1, String projectteam,
			String checkboxprojTeam) throws Exception {
		if (VERBOSE) {
			System.out
					.println("getFindSupplierInfoByProject(QMQuery query1,String projectteam,String checkboxprojTeam) begain");
			System.out.println("projectteam =" + projectteam
					+ " checkboxprojTeam=" + checkboxprojTeam);
		} // endif
		QMQuery query = query1;

		// 如果项目编号不为空
		if (projectteam != null && !projectteam.trim().equals("")) {
			if (query.getConditionCount() > 0)
				query.addAND();

			// 如果用户查询的项目中不想包括此项目编号
			if (checkboxprojTeam != null
					&& checkboxprojTeam.trim().equals("false")) {
				QueryCondition cond = new QueryCondition("projectId", "=",
						projectteam);
				query.addCondition(0, cond);
			}
			// 如果用户想查询此项目编号
			else {
				QueryCondition cond = new QueryCondition("projectId", "<>",
						projectteam);
				query.addCondition(0, cond);
			}
		}
		if (VERBOSE) {
			System.out
					.println("getFindPartInfoByProject(QMQuery query1,String projectteam,String checkboxprojTeam) end");

		} // endif
		return query;
	}

	/**
	 * 根据供应商生命周期查询数据库
	 * 
	 * @param QMQuery
	 *            query1,String lifecycle,String checkboxlifeCState
	 * @return QMQuery
	 * @exception Exception
	 */
	public QMQuery getFindPartInfoByLifeCycle(QMQuery query1, String lifecycle,
			String checkboxlifeCState) throws Exception {
		if (VERBOSE) {
			System.out
					.println("getFindSupplierInfoByLifeCycle(QMQuery query1,String lifecycle,String checkboxlifeCState) begain");
			System.out.println("lifecycle =" + lifecycle
					+ " checkboxlifeCState=" + checkboxlifeCState);
		} // endif
		QMQuery query = query1;

		// 如果供应商编号不为空
		if (lifecycle != null && !lifecycle.trim().equals("")) {
			if (query.getConditionCount() > 0)
				query.addAND();

			// 如果用户查询的供应商中不想包括此供应商编号
			if (checkboxlifeCState != null
					&& checkboxlifeCState.trim().equals("false")) {
				QueryCondition cond = new QueryCondition("lifeCycleState", "=",
						lifecycle);
				query.addCondition(0, cond);
			}
			// 如果用户想查询此项目编号
			else {
				QueryCondition cond = new QueryCondition("lifeCycleState",
						"<>", lifecycle);
				query.addCondition(0, cond);
			}
		}
		if (VERBOSE) {
			System.out
					.println("getFindPartInfoByLifeCycle(QMQuery query1,String lifecycle,String checkboxlifeCState) end");

		} // endif
		return query;

	}

	/**
	 * 根据文件柜查询数据库
	 * 
	 * @param QMQuery
	 *            query1,String folder, String checkboxfolder
	 * @return QMQuery
	 * @exception Exception
	 */
	public QMQuery getFindPartInfoByFolder(QMQuery query1, String folder,
			String checkboxfolder) throws Exception {
		if (VERBOSE) {
			System.out
					.println("getFindSupplierInfoByFolder(QMQuery query1,String folder, String checkboxfolder) begain");
			System.out.println("folder =" + folder + " checkboxfolder="
					+ checkboxfolder);
		} // endif
		QMQuery query = query1;

		// 如果供应商编号不为空
		if (folder != null && !folder.trim().equals("")) {
			if (folder.equals("\\"))
				folder = "\\Root";
			if (folder.indexOf("\\Root") != 0) {
				// 不以Root开头
				if (folder.indexOf("\\Root") == 0)
					folder = "\\Root" + folder;
				else
					folder = "\\Root\\" + folder;
			}
			if (folder.lastIndexOf("\\") == folder.length() - 1)
				folder = folder.substring(0, folder.length() - 1);

			if (query.getConditionCount() > 0)
				query.addAND();
			// 如果用户查询的供应商中不想包括此供应商编号
			if (checkboxfolder.trim().equals("false")) {
				QueryCondition cond = new QueryCondition("location", "=",
						folder);
				query.addCondition(0, cond);
			}
			// 如果用户想查询此供应商编号
			else {
				QueryCondition cond = new QueryCondition("location", "<>",
						folder);
				query.addCondition(0, cond);
			}
		}
		if (VERBOSE) {
			System.out
					.println("getFindPartInfoByFolder(QMQuery query1,String folder, String checkboxfolder) end");

		} // endif
		return query;
	}

	/**
	 * 根据零部件创建者查询数据库
	 * 
	 * @param QMQuery
	 *            query1,String textcreator,String checkboxcreator
	 * @return QMQuery
	 * @exception Exception
	 */
	public QMQuery getFindPartInfoByCreator(QMQuery query1, String textcreator,
			String checkboxcreator) throws Exception {
		if (VERBOSE) {
			System.out
					.println("getFindSupplierInfoByCreator(QMQuery query1,String textcreator,String checkboxcreator) begain");
			System.out.println("textcreator =" + textcreator
					+ " checkboxcreator=" + checkboxcreator);
		} // endif
		QMQuery query = query1;

		// 如果供应商编号不为空
		if (textcreator != null && !textcreator.trim().equals("")) {
			if (query.getConditionCount() > 0)
				query.addAND();
			int j = query.appendBso("User", false);
			QueryCondition qc1 = new QueryCondition("iterationCreator", "bsoID");
			query.addCondition(0, j, qc1);
			query.addAND();

			// 如果用户查询的供应商中不想包括此供应商编号
			if (checkboxcreator != null
					&& checkboxcreator.trim().equals("false")) {
				// modify by ShangHaiFeng 2003.12.15
				query.addLeftParentheses();
				QueryCondition cond = new QueryCondition("usersName", "like",
						getLikeSearchString(textcreator));
				query.addCondition(j, cond);
				QueryCondition cond2 = new QueryCondition("usersDesc", "like",
						getLikeSearchString(textcreator));
				query.addOR();
				query.addCondition(j, cond2);
				query.addRightParentheses();
			} else {

				query.addLeftParentheses();
				QueryCondition cond = new QueryCondition("usersName",
						"not like", getLikeSearchString(textcreator));
				query.addCondition(j, cond);
				QueryCondition cond2 = new QueryCondition("usersDesc",
						"not like", getLikeSearchString(textcreator));
				query.addAND();
				query.addCondition(j, cond2);
				query.addRightParentheses();

			}
		}
		if (VERBOSE) {
			System.out
					.println("getFindPartInfoByCreator(QMQuery query1,String textcreator,String checkboxcreator)  end");
		} // endif
		return query;
	}

	/**
	 * 根据零部件创建时间查询数据库
	 * 
	 * @param QMQuery
	 *            query1,String textcreator,String checkboxcreator
	 * @return QMQuery
	 * @exception Exception
	 */
	public QMQuery getFindPartInfoByTime(QMQuery query1, String textcreateTime,
			String checkboxcreateTime, String timeType)
			throws QMException {
		if (VERBOSE) {
			System.out
					.println("getFindSupplierInfoByCreator(QMQuery query1,String textcreator,String checkboxcreator) begain");
			System.out.println("textCreateTime =" + textcreateTime
					+ " checkboxcreateTime=" + checkboxcreateTime);
		} // endif
		QMQuery query = query1;

		// 如果供应商编号不为空
		if (textcreateTime != null && !textcreateTime.trim().equals("")) {
			String javaFormat = "yyyy:MM:dd" + ':' + "HH:mm:ss";
			String oracleFormat = "YYYY:MM:DD:HH24:MI:SS";
			String toDateStr = "TO_DATE";
			String timeStr = textcreateTime;
			boolean betweenFlag = false;
			String beginTimeStr = "";
			String endTimeStr = "";
			String beginFormatTimeStr = null;
			String endFormatTimeStr = null;
			DateHelper dateHelperBegin = null;
			DateHelper dateHelperEnd = null;
			Timestamp beginTime = null;
			Timestamp endTime = null;
			int i = timeStr.indexOf(";");
			if (i > -1) {
				// 有明确的起止时间分隔符
				beginTimeStr = timeStr.substring(0, i);
				endTimeStr = timeStr.substring(i + 1);
				try {
					if (!beginTimeStr.trim().equals(""))
						dateHelperBegin = new DateHelper(beginTimeStr);
					if (!endTimeStr.trim().equals(""))
						dateHelperEnd = new DateHelper(endTimeStr);
				} catch (Exception ex) {
					throw new QMException(ex);
				}
				// System.out.println("beginTime ="+beginTime);
				// System.out.println("endTime ="+endTime);
			} else {
				// 无明确的起止时间分隔符
				beginTimeStr = timeStr;
				try {
					dateHelperBegin = new DateHelper(beginTimeStr);
				} catch (Exception ex) {
					throw new QMException(ex);
				}
			}
			// 如果有明确的起止时间分隔符，获取起止时间，
			// 如果没有明确的起止时间分隔符，进一步判断是否是完成时间（如2003-04-28 10:00:00.0）
			// 如不是，则算出对应起止时间。
			// 如指定搜索“2003-04-28 10”，
			// begin: 2003-04-28 10:00:00
			// end: 2003-04-28 10:59:59

			if (dateHelperBegin != null && dateHelperEnd != null) {
				betweenFlag = true;
				beginTime = new Timestamp(dateHelperBegin.getDate().getTime());
				endTime = new Timestamp(dateHelperEnd.getDate().getTime());
			} else if (dateHelperBegin != null && dateHelperEnd == null) {
				if (dateHelperBegin.fullDate()) {
					betweenFlag = false;
					beginTime = new Timestamp(dateHelperBegin.getDate()
							.getTime());
					endTime = null;
				} else {
					betweenFlag = true;
					beginTime = new Timestamp(dateHelperBegin.instantOfDate(
							DateHelper.firstInstant).getTime());
					endTime = new Timestamp(dateHelperBegin.instantOfDate(
							DateHelper.lastInstant).getTime());
				}
			} else if (dateHelperBegin == null && dateHelperEnd != null) {
				if (dateHelperEnd.fullDate()) {
					betweenFlag = false;
					beginTime = new Timestamp(dateHelperEnd.getDate().getTime());
					endTime = null;
				} else {
					betweenFlag = true;
					beginTime = new Timestamp(dateHelperEnd.instantOfDate(
							DateHelper.firstInstant).getTime());
					endTime = new Timestamp(dateHelperEnd.instantOfDate(
							DateHelper.lastInstant).getTime());
				}
			}
			// 比较两个时间大小，如果开始时间大于终止时间，时间要调换
			if (beginTime != null && endTime != null) {
				if (beginTime.compareTo(endTime) > 0) {
					Timestamp temp = beginTime;
					beginTime = endTime;
					endTime = temp;
				}
			}

			if (beginTime != null)
				beginFormatTimeStr = getDateString(beginTime, javaFormat);
			if (endTime != null)
				endFormatTimeStr = getDateString(endTime, javaFormat);

			if (VERBOSE) {
				if (betweenFlag) {
					System.out.println("beginTime =" + beginTime);
					System.out.println("endTime   =" + endTime);
					System.out.println("beginFormatTimeStr ="
							+ beginFormatTimeStr);
					System.out.println("endFormatTimeStr   ="
							+ endFormatTimeStr);
				} else {
					System.out.println("beginFormatTimeStr ="
							+ beginFormatTimeStr);
				}
			}

			if (beginFormatTimeStr == null)
				return query;
			if (query.getConditionCount() > 0)
				query.addAND();
			// 如果用户查询 +
			if (checkboxcreateTime != null
					&& checkboxcreateTime.trim().equals("false")) {
				try {
					if (betweenFlag) {
						query.addLeftParentheses();
						// QueryCondition cond = new QueryCondition(timeType,
						// ">=",toDateStr + "(" + beginFormatTimeStr + "," +
						// oracleFormat + ")");
						QueryCondition cond = new QueryCondition(timeType,
								">=", beginTime);
						query.addCondition(0, cond);
						query.addAND();
						// cond = new QueryCondition(timeType, "<=",toDateStr +
						// "(" + endFormatTimeStr + "," + oracleFormat + ")");
						cond = new QueryCondition(timeType, "<=", endTime);
						query.addCondition(0, cond);
						query.addRightParentheses();
					} else {
						// QueryCondition cond = new QueryCondition(timeType,
						// "=",toDateStr + "(" + beginFormatTimeStr + "," +
						// oracleFormat + ")");
						QueryCondition cond = new QueryCondition(timeType, "=",
								beginTime);
						query.addCondition(0, cond);
					}
				} catch (Exception ex) {
					throw new QMException(ex);
				}
			}
			// 如果用户想查询 -
			else {
				try {
					if (betweenFlag) {
						query.addLeftParentheses();
						// QueryCondition cond = new QueryCondition(timeType,
						// "<=",toDateStr + "(" + beginFormatTimeStr + "," +
						// oracleFormat + ")");
						QueryCondition cond = new QueryCondition(timeType,
								"<=", beginTime);
						query.addCondition(0, cond);
						query.addOR();
						// cond = new QueryCondition(timeType, ">=",toDateStr +
						// "(" + endFormatTimeStr + "," +oracleFormat + ")");
						cond = new QueryCondition(timeType, ">=", endTime);
						query.addCondition(0, cond);
						query.addRightParentheses();
					} else {
						// QueryCondition cond = new QueryCondition(timeType,
						// "<>",toDateStr + "(" + beginFormatTimeStr + "," +
						// oracleFormat + ")");
						QueryCondition cond = new QueryCondition(timeType,
								"<>", beginTime);
						query.addCondition(0, cond);
					}
				} catch (Exception ex) {
					throw new QMException(ex);
				}
			}

		}
		if (VERBOSE) {
			System.out
					.println("getFindPartInfoByTime(QMQuery query1,String textcreator,String checkboxcreator)  end");
		} // endif
		return query;

	}
	   /**
     * 根据时间格式字符串，转化成指定格式的时间字符串
     * @param date 要转化的时间
     * @param javaFormat 时间格式字符串
     * @return 按指定格式转化后的时间字符串
     * @throws AdoptNoticeException
     */
    private String getDateString(Date date, String javaFormat) throws
    QMException {
        if (VERBOSE)
            System.out.println("SupplierServiceEJB.getDateString(Date date, String javaFormat) begin...");
        String resultStr = null;
        SimpleDateFormat simpledateformat = new SimpleDateFormat(javaFormat);
        resultStr = simpledateformat.format(date);
        if (VERBOSE)
            System.out.println("SupplierServiceEJB.getDateString(Date date, String javaFormat) end... resultStr ="
                               + resultStr);
        return resultStr;
    }
	/**
	 * 匹配字符查询处理。将字符串oldStr中的"/*"转化成"*"，"*"转化成"%"，"%"不处理。 例 "shf/*pdm%cax*" 转化成
	 * "shf*pdm%cax%"
	 * 
	 * @param oldStr
	 * @return 转化后的匹配字符查询串
	 */
	private String getLikeSearchString(String oldStr) {
		if (oldStr == null || oldStr.trim().equals(""))
			return oldStr;
		char ac[] = oldStr.toCharArray();
		int i = ac.length;
		for (int j = 0; j < i; j++) {
			if (ac[j] == '*')
				if (j > 0 && ac[j - 1] == '/') {
					for (int k = j - 1; k < i - 1; k++)
						ac[k] = ac[k + 1];
					i--;
					ac[i] = ' ';
				} else {
					ac[j] = '%';
				}
			// modify by ShangHaiFeng 2003.12.15
			if (ac[j] == '?')
				ac[j] = '_';
		}

		String resultStr = (new String(ac)).trim();
		return resultStr;
	}
	  /**
     * 通过零部件查找供应商
     * @param part
     * @return
     */
    public Collection getSupplierByPart(String  partId,String level)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //获得路线表和零件路线的关联
        QMQuery query = new QMQuery("consListRoutePartLink");
        int k = query.appendBso("consTechnicsRouteList",false);

        QueryCondition cond3 = new QueryCondition("rightBsoID", QueryCondition.EQUAL, partId);
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, 1);
        query.addCondition(0, cond4);
        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(0, cond5);
        query.addAND();
        QueryCondition cond7 = new QueryCondition("leftBsoID", "bsoID");
        query.addCondition(0, k, cond7);
        query.addAND();
        QueryCondition cond8 = new QueryCondition("lifeCycleState",QueryCondition.EQUAL, "released");
        query.addCondition(k, cond8);
        query.addAND();
        QueryCondition cond9 = new QueryCondition("routeListLevel",QueryCondition.EQUAL, level.trim());
        query.addCondition(k, cond9);
        query.addOrderBy(k,"createTime");
        //query.setDisticnt(true);     
        //返回ListRoutePartLinkIfc
        if(VERBOSE)
        {
            System.out.println( "routeService's getPartLevelRoutes SQL = " + query.getDebugSQL());
        }
        
        String supplier="";
        String supplierBsoId="";
        Collection collection = pservice.findValueInfo(query, false);
        Iterator ites = collection.iterator();
        System.out.println("!!!!!!!!!!!!");
        Collection result=new Vector();
        if(ites.hasNext()){
           ListRoutePartLinkInfo info=(ListRoutePartLinkInfo)ites.next();
           supplier=info.getSupplierBsoId();   
           if (!supplier.equals("")) {
   			String s[] = supplier.split(";");		
   			if (s.length > 1) {
   				for(int n=0;n<s.length;n++){
   					String ss[] = s[n].split(" ");
   					if (ss.length > 1) {
   						supplierBsoId = ss[1];
   					} else {
   						supplierBsoId = ss[0];
   					}
   					SupplierIfc supIfc = (SupplierIfc) pservice.refreshInfo(supplierBsoId);
   					result.add(supIfc);
   				}
   			}else{
   				supplierBsoId=supplier;
   				SupplierIfc supIfc = (SupplierIfc) pservice.refreshInfo(supplierBsoId);
   				result.add(supIfc);

   			}
        }
      
		}
        System.out.println("###########33");
        return result;
    }
} // end class SupplierServiceHelper
