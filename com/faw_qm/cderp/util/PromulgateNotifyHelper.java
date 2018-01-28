/**
 * 生成程序PromulgateNotifyHelper.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.faw_qm.capp.ejb.standardService.StandardCappService;
import com.faw_qm.capp.model.PartUsageQMTechnicsLinkIfc;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.cderp.ejb.service.MaterialSplitService;
import com.faw_qm.cderp.ejb.service.PromulgateNotifyService;
import com.faw_qm.cderp.model.PromulgateNotifyInfo;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.doc.exception.DocException;
//import com.faw_qm.jferp.ejb.service.TechnicDataService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public final class PromulgateNotifyHelper
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(PromulgateNotifyHelper.class);

    /**
     * 获得关联产品
     * @param bsoid String
     * @throws QMException
     * @return Vector
     */
    public static Vector getProductsByProID(String bsoid) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getProductsByProID(String) - start"); //$NON-NLS-1$
        }
        Vector vecPart = null;
        try
        {
            PromulgateNotifyService anService = (PromulgateNotifyService) EJBServiceHelper
                    .getService("CDPromulgateNotifyService");
            vecPart = (Vector) anService.getProductsByProID(bsoid);
        }
        catch (Exception e)
        {
            logger.error("getProductsByProID(String)", e); //$NON-NLS-1$
            e.printStackTrace();
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getProductsByProID(String) - end"); //$NON-NLS-1$
        }
        return vecPart;
    }

    /**
     * 获得关联文档
     * @param bsoid String
     * @throws QMException
     * @return Vector
     */
    public static Vector getDocsByProID(String bsoid) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getDocsByProID(String) - start"); //$NON-NLS-1$
        }
        Vector vecdoc = null;
        try
        {
            PromulgateNotifyService anService = (PromulgateNotifyService) EJBServiceHelper
                    .getService("CDPromulgateNotifyService");
            vecdoc = (Vector) anService.getDocsByProId(bsoid);
        }
        catch (Exception e)
        {
            logger.error("getDocsByProID(String)", e); //$NON-NLS-1$
            e.printStackTrace();
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getDocsByProID(String) - end"); //$NON-NLS-1$
        }
        return vecdoc;
    }

    /**
     * 获取所有生命周期模版的名称。
     * @return  Vector  所有生命周期模版的名称。
     * @exception com.faw_qm.framework.exceptions.QMException
     */
    public static Vector findAllLifeCycle() throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("findAllLifeCycle() - start"); //$NON-NLS-1$
        }
        Vector vector = null;
        try
        {
            //获取生命周期服务
            LifeCycleService lcService = (LifeCycleService) EJBServiceHelper
                    .getService("LifeCycleService");
            //获取所有生命周期模版
            vector = lcService.findCandidateTemplates("PromulgateNotify");
        } //end try
        catch (Exception e)
        {
            logger.error("findAllLifeCycle()", e); //$NON-NLS-1$
            throw new QMException(e);
        } //end catch
        Vector vector1 = new Vector();
        LifeCycleTemplateInfo lct;
        for (int i = 0; i < vector.size(); i++)
        {
            lct = (LifeCycleTemplateInfo) vector.elementAt(i);
            String name = lct.getLifeCycleName();
            vector1.addElement(name);
        } //end for i
        if(logger.isDebugEnabled())
        {
            logger.debug("findAllLifeCycle() - end"); //$NON-NLS-1$
        }
        return vector1;
    } //end findAllLifeCycle()

    /**
     * 获取所有生命周期状态
     * @return 所有生命周期状态(State集合)
     * @throws QMException
     */
    public static Vector findAllLifeCycleState() throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("findAllLifeCycleState() - start"); //$NON-NLS-1$
        }
        Vector vector = new Vector(42);
        LifeCycleState allState[] = LifeCycleState.getLifeCycleStateSet();
        for (int i = 0; i < allState.length; i++)
        {
            vector.add(allState[i]);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("findAllLifeCycleState() - end"); //$NON-NLS-1$
        }
        return vector;
    } //end

    /**
     * 根据生命周期ID，获取生命周期名
     * @param lfcbsoID 生命周期ID
     * @return 获取生命周期名
     * @throws DocException
     */
    public static String getLifeCycleNameByID(String lfcbsoID)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getLifeCycleNameByID(String) - start"); //$NON-NLS-1$
        }
        String lfcName = "";
        LifeCycleTemplateInfo lfcInfo = null;
        try
        {
            //  获得持久化服务
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            lfcInfo = (LifeCycleTemplateInfo) persistService.refreshInfo(
                    lfcbsoID, false);
            if(lfcInfo != null)
            {
                lfcName = lfcInfo.getLifeCycleName();
            }
        }
        catch (Exception ex)
        {
            logger.error("getLifeCycleNameByID(String)", ex); //$NON-NLS-1$
            return lfcName;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getLifeCycleNameByID(String) - end"); //$NON-NLS-1$
        }
        return lfcName;
    }

    /**
     * 获得枚举的采用标记
     * @throws QMException
     * @return Vector
     */
    public static PromulgateNotifyFlag[] findAllPromulgateNotifyFlag()
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("findAllPromulgateNotifyFlag() - start"); //$NON-NLS-1$
        }
        //更改成枚举文件
        PromulgateNotifyFlag[] flag = PromulgateNotifyFlag
                .getPromulgateNotifyFlagSet();
        if(logger.isDebugEnabled())
        {
            logger.debug("findAllPromulgateNotifyFlag() - end"); //$NON-NLS-1$
        }
        return flag;
    }

    /**
     * 搜索part产品
     * @param name String
     * @param number1 String
     * @throws QMException
     * @return Vector
     */
    public Vector getAllFitedPart(String name, String number1)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllFitedPart(String, String) - start"); //$NON-NLS-1$
        }
        Vector mastersvec = new Vector();
        try
        {
            StandardPartService spService = (StandardPartService) EJBServiceHelper
                    .getService("StandardPartService");
            Collection masters = (Collection) spService.getAllPartMasters(name,
                    false, number1, false);
            mastersvec.addAll(masters);
            if(logger.isDebugEnabled())
            {
                logger
                        .debug("getAllFitedPart(String, String) - come out vector number=" + masters.size()); //$NON-NLS-1$
            }
        }
        catch (QMException e)
        {
            logger.error("getAllFitedPart(String, String)", e); //$NON-NLS-1$
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllFitedPart(String, String) - end"); //$NON-NLS-1$
        }
        return mastersvec;
    }

    public ArrayList getAllPartByMaster(String name, String number)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllPartByMaster(String, String) - start"); //$NON-NLS-1$
        }
        ArrayList vec = new ArrayList();
        try
        {
            PromulgateNotifyService spService = (PromulgateNotifyService) EJBServiceHelper
                    .getService("CDPromulgateNotifyService");
            vec = (ArrayList) spService.getAllPartsByMaster(number, name);
        }
        catch (QMException e)
        {
            logger.error("getAllPartByMaster(String, String)", e); //$NON-NLS-1$
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getAllPartByMaster(String, String) - come out getAllPartByMaster"); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllPartByMaster(String, String) - end"); //$NON-NLS-1$
        }
        return vec;
    }

    /**
     *
     * @param id String
     * @throws QMException
     * @return PromulgateNotifyInfo
     */
    public static PromulgateNotifyInfo findProInfoFromId(String id)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("findProInfoFromId(String) - start"); //$NON-NLS-1$
        }
        PromulgateNotifyInfo info = null;
        try
        {
            //  获得持久化服务
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            //刷出值对象
            info = (PromulgateNotifyInfo) persistService.refreshInfo(id);
        } //end try
        catch (QMException e)
        {
            logger.error("findProInfoFromId(String)", e); //$NON-NLS-1$
            throw new QMException(e.getLocalizedMessage());
        } //end catch
        if(logger.isDebugEnabled())
        {
            logger.debug("findProInfoFromId(String) - end"); //$NON-NLS-1$
        }
        return info;
    } //

    /**
     *
     * @param userID String
     * @return String
     */
    public static String findUserNameByID(String userID) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("findUserNameByID(String) - start"); //$NON-NLS-1$
        }
        //开始
        String userName = null;
        UserInfo userInfo = null;
        //  获得持久化服务
        PersistService persistService = null;
        try
        {
            persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            userInfo = (UserInfo) persistService.refreshInfo(userID, false);
            userName = userInfo.getUsersDesc();
            if(userName == null || userName.trim().length() == 0)
            {
                userName = userInfo.getUsersName();
            }
        }
        catch (QMException ex1)
        {
            logger.error("findUserNameByID(String)", ex1); //$NON-NLS-1$
            throw new QMException(ex1.getLocalizedMessage());
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("findUserNameByID(String) - end"); //$NON-NLS-1$
        }
        return userName;
    }

    /**
     * 获取文档的工作状态信息
     * @param docBsoID 文档的bsoID
     * @return  文档的工作状态信息
     * @throws DocException
     */
    public String getProState(String bsoID) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getProState(String) - start"); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getProState(String) - getProState(String bsoID) begin... bsoID = " + bsoID); //$NON-NLS-1$
        }
        String stateValue = "";
        PersistService persistService = null;
        PromulgateNotifyInfo info = null;
        try
        {
            persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            info = (PromulgateNotifyInfo) persistService.refreshInfo(bsoID);
            stateValue = info.getLifeCycleState().getDisplay();
        }
        catch (Exception ex)
        {
            logger.error("getProState(String)", ex); //$NON-NLS-1$
            logger.error("getProState(String)", ex); //$NON-NLS-1$
            return stateValue;
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getProState(String) - getState(String bsoID) end... docStatusValue = " + stateValue); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getProState(String) - end"); //$NON-NLS-1$
        }
        return stateValue;
    }

    /**
     * 查找所有采用通知
     * @return vector
     * @exception com.faw_qm.framework.exceptions.QMException
     */
    public static Vector findAllPromulgateNotifyInfo(String name,
            String checkboxName, String num, String checkboxNum, String Flag,
            String checkboxFlag, String textcreator, String checkboxcreator)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("findAllPromulgateNotifyInfo(String, String, String, String, String, String, String, String) - start"); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("findAllPromulgateNotifyInfo(String, String, String, String, String, String, String, String) - Vector findAllPromulgateNotifyInfo() begin..."); //$NON-NLS-1$
        }
        Vector vector = null;
        try
        {
            PromulgateNotifyService proService = (PromulgateNotifyService) EJBServiceHelper
                    .getService("CDPromulgateNotifyService");
            vector = (Vector) proService.searchPromulgateNotify(name,
                    checkboxName, num, checkboxNum, Flag, checkboxFlag,
                    textcreator, checkboxcreator);
        } //end try
        catch (QMException e)
        {
            logger
                    .error(
                            "findAllPromulgateNotifyInfo(String, String, String, String, String, String, String, String)", e); //$NON-NLS-1$
            logger
                    .error(
                            "findAllPromulgateNotifyInfo(String, String, String, String, String, String, String, String)", e); //$NON-NLS-1$
            throw new QMException(e);
        } //end catch
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("findAllPromulgateNotifyInfo(String, String, String, String, String, String, String, String) - end"); //$NON-NLS-1$
        }
        return vector;
    }

    public static Vector getPartsByProId(String id) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartsByProId(String) - start"); //$NON-NLS-1$
        }
        Vector vecPart = null;
        try
        {
            PromulgateNotifyService anService = (PromulgateNotifyService) EJBServiceHelper
                    .getService("CDPromulgateNotifyService");
            vecPart = anService.getPartsByProId(id);
        }
        catch (Exception e)
        {
            logger.error("getPartsByProId(String)", e); //$NON-NLS-1$
            e.printStackTrace();
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartsByProId(String) - end"); //$NON-NLS-1$
        }
        return vecPart;
    }

    /**
     * 根据标识获得显示名
     * @param flag String
     * @return String
     */
    public static String getDisplayByFlag(String bsoid) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getDisplayByFlag(String) - start"); //$NON-NLS-1$
        }
        String dis = "";
        try
        {
            PromulgateNotifyInfo pro = findProInfoFromId(bsoid);
            dis = PromulgateNotifyFlag.toPromulgateNotifyFlag(
                    pro.getPromulgateNotifyFlag()).getDisplay();
        }
        catch (QMException e)
        {
            logger.error("getDisplayByFlag(String)", e); //$NON-NLS-1$
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getDisplayByFlag(String) - end"); //$NON-NLS-1$
        }
        return dis;
    }
    
    //20080128 begin
//	public static Vector getTechsByProID(String id) throws QMException {
//		if (logger.isDebugEnabled()) {
//			logger.debug("getTechsByProID(String) - start"); //$NON-NLS-1$
//		}
//		Vector vecTech = new Vector();
//		Vector vecPart = null;
//		QMPartIfc partIfc = null;
//		HashMap tawMap = new HashMap();
//		QMFawTechnicsIfc techIfc = null;
//		QMFawTechnicsIfc tempTechIfc = null;
//		Iterator techIter;
//		Collection techColl;
//		String logString = "";
//		try {
//			StandardCappService cappService = (StandardCappService) EJBServiceHelper
//					.getService("StandardCappService");
//			PersistService pService = (PersistService) EJBServiceHelper
//					.getService("PersistService");
//			VersionControlService versionService = (VersionControlService) EJBServiceHelper
//					.getService("VersionControlService");
//			TechnicDataService techDataService = (TechnicDataService) EJBServiceHelper
//					.getService("TechnicDataService");
//			MaterialSplitService matSplitService = (MaterialSplitService) EJBServiceHelper
//					.getService("CDMaterialSplitService");
//			/**
//			 * 逗号分隔符。用于分隔useProcessPartRouteCode。
//			 */
//			String delimiter = "，";
//			vecPart = getPartsByProId(id);
//			logger.debug("vecPart.size() is "+vecPart.size());
//			for (int i = 0; i < vecPart.size(); i++) {
//				partIfc = (QMPartIfc) vecPart.get(i);
//				Collection linkCol = cappService
//						.findPartUsageQMTechnicsLinkByPart(partIfc.getBsoID());
//				Iterator linkIter = linkCol.iterator();
//				//存放该零部件的工艺下的工序编号，键是工艺的部门代码，值是该部门下的所有工序编号的字符串
//				HashMap temp = new HashMap();
//				//存放工序编号的集合
//				//ArrayList stepDescNum = new ArrayList();
//				while (linkIter.hasNext()) {
//					PartUsageQMTechnicsLinkIfc link = (PartUsageQMTechnicsLinkIfc) linkIter
//							.next();
//					techIfc = (QMFawTechnicsIfc) pService.refreshInfo(link
//							.getRightBsoID(), false);
//					techColl = versionService.allVersionsOf(techIfc);
//					techIter = techColl.iterator();
//					while (techIter.hasNext()) {
//						tempTechIfc = (QMFawTechnicsIfc) techIter.next();
//						if (!tawMap.containsKey(tempTechIfc.getBsoID())) {
//							tawMap.put(tempTechIfc.getBsoID(), tempTechIfc
//									.getBsoID());
//							//20081211 将直接输出到屏幕修改为Log输出
//							logger.debug("tempTechIfc.getTechnicsType().getCodeContent() is "+
//									tempTechIfc.getTechnicsType().getCodeContent());
//							logger.debug("tempTechIfc.getTechnicsType().getCode() is "+
//									tempTechIfc.getTechnicsType().getCode());
//							String noPublishTechType = RemoteProperty.getProperty(
//									"noPublishTechType", "09");
//							StringTokenizer stringToken = new StringTokenizer(
//									noPublishTechType, "，");
//							String techType;
//							String tempTechtype=tempTechIfc.getTechnicsType().getCode();
//							boolean flag1=true;
//							while (stringToken.hasMoreTokens()) {
//								techType = stringToken.nextToken();
//								if (tempTechtype != null && techType != null
//										&& tempTechtype.equalsIgnoreCase(techType)) {
//									flag1 = false;
//									break;
//								}
//							}
//							//20081211 将直接输出到屏幕修改为Log输出
//							logger.debug("noPublishTechType flag1 is "+flag1);
//							if(flag1){
//								vecTech.add(tempTechIfc);
//							}
//							BaseValueInfo info = tempTechIfc.getWorkShop();
//							//工艺的部门代码
//							String technicShop = "";
//							if (info instanceof CodingInfo) {
//								technicShop = ((CodingInfo) info)
//										.getSearchWord();
//							}
//							//获得工艺使用的工序集合
//							Collection procedureCol = techDataService
//									.browseProceduresByTechnics(tempTechIfc
//											.getBsoID());
//							if (procedureCol != null && procedureCol.size() > 0) {
//								for (Iterator iterator = procedureCol
//										.iterator(); iterator.hasNext();) {
//									QMProcedureIfc procedure = (QMProcedureIfc) iterator
//											.next();
//									//				                	stepDescNum.add(procedure.getDescStepNumber());
//									if (!temp.containsKey(technicShop)) {
//										temp.put(technicShop, procedure
//												.getDescStepNumber());
//									} else {
//										String stepNumber = (String) temp
//												.get(technicShop);
//										stepNumber += "->"
//												+ procedure.getDescStepNumber();
//										temp.put(technicShop, stepNumber);
//									}
//								}
//							}
//						}
//					}
//
//				}
//				Iterator iter = temp.keySet().iterator();
//				logger.debug("temp.keySet().size() is " + temp.keySet().size());
//				while (iter.hasNext()) {
//					boolean flag1 = true;
//					String processRC = (String) iter.next();
//					if(processRC!=null){
//						
//					
//					logger.debug("processRC is " + processRC);
//					String noProcessRouteCode = RemoteProperty.getProperty(
//							"noProcessRouteCode", "CY");
//					StringTokenizer stringToken = new StringTokenizer(
//							noProcessRouteCode, delimiter);
//					String processRouteName;
//					while (stringToken.hasMoreTokens()) {
//						processRouteName = stringToken.nextToken();
//						if (processRC != null && processRouteName != null
//								&& processRC.equalsIgnoreCase(processRouteName)) {
//							flag1 = false;
//							break;
//						}
//					}
////					String hasProcessRouteCode = RemoteProperty.getProperty(
////							"hasProcessRouteCode", "CY");
////					stringToken = new StringTokenizer(
////							hasProcessRouteCode, delimiter);
////					while (stringToken.hasMoreTokens()) {
////						processRouteName = stringToken.nextToken();
////						if (processRC != null && processRouteName != null
////								&& processRC.equalsIgnoreCase(processRouteName)) {
////							flag1 = false;
////							break;
////						}
////					}
//					logger.debug("flag1 is " + flag1);
//					//如果不是特殊工艺部门代码，则进行检验，
//					//判断该部门的过程流程中的工序是否在工艺的工序中
//					if (flag1) {
//						//首先获取该零部件的过程代码。
//						RouteCodeIBAName routeCodeIBAName = RouteCodeIBAName
//								.toRouteCodeIBAName(processRC);
//						logger.debug("routeCodeIBAName is " + routeCodeIBAName);
//						if (routeCodeIBAName != null) {
//							String display = routeCodeIBAName.getDisplay();
//							String processCode = "";
//							processCode = matSplitService.getPartIBA(partIfc,
//									display);
//							logger.debug("processCode is " + processCode);
//							if(processCode!=null&&processCode.length()>0){
//								stringToken = new StringTokenizer(processCode, "->");
//								String processNum;
//								String tempProNum;
//								//boolean isNumInProRoute;
//								String tempStr = (String) temp.get(processRC);
//								logger.debug("tempStr is "+tempStr);
//								while (stringToken.hasMoreTokens()) {
//									processNum = stringToken.nextToken();
//									logger.debug("processNum is "+processNum);
//									boolean isFind=false;
//									if (tempStr != null
//											&& tempStr.trim().length() > 0) {
//										StringTokenizer tempStrToken=new StringTokenizer(tempStr, "->");
//										while (tempStrToken.hasMoreTokens()) {
//											tempProNum=tempStrToken.nextToken();
//											if(tempProNum.equals(processNum)){
//												isFind=true;
//											}
//										}
//										if(!isFind){
//											logString += "零部件"
//												+ partIfc.getPartNumber() + "的\""
//												+ display + "\"中的\'" + processNum
//												+ "\'在工艺中找不到该工序。\n";
//										}
//									}
//								}
//							}
//							else{
//								logString += "零部件"
//									+ partIfc.getPartNumber() + "没有\""
//									+ processRC + "\"车间的过程流程信息。\n";
//							}
//		
//						}
//						else{
//							logString += "零部件"
//								+ partIfc.getPartNumber() + "没有\""
//								+ processRC + "\"车间的过程流程信息。\n";
//						}
//					}
////					else {
////						continue;
////					}
//				}
//					}
//			}
//
//		} catch (Exception e) {
//			logger.error("getTechsByProID(String)", e); //$NON-NLS-1$
//			e.printStackTrace();
//			throw new QMException(e);
//		}
//		if (logger.isDebugEnabled()) {
//			logger.debug("getTechsByProID(String) - end" + vecTech.size()); //$NON-NLS-1$
//		}
//		logger.debug("vecTech.size() is "+vecTech.size());
//		logger.debug("logString is " + logString);
//		if(logString.length()>0){
//			
//		 TechnicsDataPublisher.createTechnicsLogFile("partInfo", logString);
////		 String techPath = RemoteProperty.getProperty("techPath");
////		 try
////	        {
////	            File f = new File(techPath);
////	            if(!f.exists())
////	            {
////	                f.mkdir();
////	            }
////	            Timestamp time = new Timestamp(System.currentTimeMillis());
////	            String timestr = time.toString();
////	            int r = timestr.indexOf(" ");
////	            timestr = timestr.substring(0, r);
////	            File f1 = new File(techPath + "/partInfo-" + timestr + ".log");
////	            if(!f1.exists() || !f1.isFile())
////	            {
////	                f1.createNewFile();
////	            }
////	            PrintWriter out = new PrintWriter(new FileWriter(f1, true), true);
////	            StringBuffer buffer = new StringBuffer();
////	            final SimpleDateFormat simple = new SimpleDateFormat(
////                "yyyy-MM-dd HH:mm:ss ");
////	            buffer.append(simple.format(new Date()));
////	            buffer.append(logString);
////	            out.print(buffer);
////	            out.flush();
////	            out.close();
////	        }
////	        catch (Exception e)
////	        {
////	            logger.error("create technics log file", e); //$NON-NLS-1$
////	            e.printStackTrace();
////	        }
//	        }
//		return vecTech;
//	}
//	//20080128 end
}
