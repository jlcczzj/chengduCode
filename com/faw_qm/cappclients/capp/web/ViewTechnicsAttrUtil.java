package com.faw_qm.cappclients.capp.web;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.capp.ejb.standardService.StandardCappService;
import com.faw_qm.capp.model.PartUsageQMTechnicsLinkInfo;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMProcedureQMPartLinkIfc;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsQMMaterialLinkInfo;
import com.faw_qm.capp.util.CappAssociationsUtil;
import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.doc.exception.DocException;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.extend.model.ExtendAttriedIfc;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.jview.chrset.DataDisplay;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.project.model.ProjectInfo;
import com.faw_qm.ration.exception.RationException;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.technics.route.ejb.service.ProductManagerOfTechnicsService;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;


/**
 * <p>Title: 瘦客户中查看工艺,工序的帮助类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: faw_qm</p>
 * @author 薛静
 * @version 1.0
 */


public class ViewTechnicsAttrUtil implements Serializable
{

    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.capp.util.CappLMRB";


    public ViewTechnicsAttrUtil()
    {
    }


    /**
     * 由工艺卡ID得到工艺卡值对象
     * @param id 工艺卡ID
     * @return 工艺卡值对象
     * @throws QMException
     */
    public QMTechnicsIfc findTechnicsInfoByID(String id)
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: findTechnicsInfoByID begin id=" +
                    id);
        }
        QMTechnicsIfc technics;
        //  获得持久化服务
        PersistService persistService = (PersistService)
                                        EJBServiceHelper.getService(
                "PersistService");
        technics = (QMTechnicsInfo) persistService.refreshInfo(id);

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: findTechnicsInfoByID end return is " +
                    technics);
        }
        return technics;
    }


    /**
     * 由用户ID得到用户名
     * @param userID
     * @return
     * @throws QMException
     */
    public String findUserNameByID(String userID)
            throws QMException
    {
        //开始
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: findUserNameByID begin" +
                    " userID= " + userID);
        }
        String userName = null;
        UserInfo userInfo = null;

        //  获得持久化服务
        PersistService persistService = (PersistService)
                                        EJBServiceHelper.getService(
                "PersistService");

        //  根据用户ID获得用户的名称
        userInfo = (UserInfo) persistService.refreshInfo(userID, false);
        userName = userInfo.getUsersDesc();

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: findUserNameByID end ..."
                    + " return is" + userName);
        }
        return userName;
    }


    /**
     * 由项目ID得到项目名
     * @param projectID
     * @return
     * @throws DocException
     */
    public String getProjectNameByID(String projectID)
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil:getProjectNameByID begin... projectID = " +
                    projectID);
        }
        String projectName = "";
        ProjectInfo projectInfo = null;
        try
        {
            //  获得持久化服务
            PersistService persistService = (PersistService) EJBServiceHelper.
                                            getService("PersistService");
            projectInfo = (ProjectInfo) persistService.refreshInfo(projectID, false);
            if (projectInfo != null)
            {
                projectName = projectInfo.getName();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return projectName;
        }

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil:getProjectNameByID end.. return is " +
                    projectName);
        }
        return projectName;
    }


    /**
     * 根据生命周期ID，获取生命周期名
     * @param lfcbsoID 生命周期ID
     * @return 获取生命周期名
     * @throws QMException
     */
    public String getLifeCycleNameByID(String lfcbsoID)
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: getProjectNameByID(String projectID) begin... lfcbsoID = " +
                    lfcbsoID);
        }
        String lfcName = "";
        LifeCycleTemplateInfo lfcInfo = null;
        try
        {
            //  获得持久化服务
            PersistService persistService = (PersistService) EJBServiceHelper.
                                            getService("PersistService");
            lfcInfo = (LifeCycleTemplateInfo) persistService.refreshInfo(
                    lfcbsoID, false);
            if (lfcInfo != null)
            {
                lfcName = lfcInfo.getLifeCycleName();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return lfcName;
        }

        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: getProjectNameByID end... return is  " +
                    lfcName);
        }
        return lfcName;
    }


    /**
     * 获取工艺卡的工作状态信息(己检入/由XX检出/XX的工作副本/在个人文件柜)
     * @param techncisBsoID 工艺的bsoID
     * @return  工艺的工作状态信息
     * @throws QMException
     */
    public String getTechnicsStatus(String techncisBsoID)
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: getTechnicsStatus(String techncisBsoID) begin... techncisBsoID = " +
                    techncisBsoID);
        }
        String techStatusValue = "";
        QMTechnicsIfc technics = null;
        PersistService persistService = null;
        WorkInProgressService wipService = null;

        try
        {
            if (technics == null)
            {
                persistService = (PersistService) EJBServiceHelper.getService(
                        "PersistService");
                technics = (QMTechnicsInfo) persistService.refreshInfo(
                        techncisBsoID);
            }
            wipService = (WorkInProgressService) EJBServiceHelper.getService(
                    "WorkInProgressService");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return techStatusValue;
        }
        boolean flag = WorkInProgressHelper.isWorkingCopy((WorkableIfc)
                technics); //是否是工作副本
        if (flag)
        {
            try
            {
                WorkableIfc originalcopy = wipService.originalCopyOf((
                        WorkableIfc) technics); //获得原本拷贝
                Object aobj1[] = new Object[1];
                if (originalcopy == null)
                {
                    aobj1[0] = "XXX";
                }
                //通过标识工厂获得与给定值对象对应的显示标识对象。
                DisplayIdentity displayidentity = IdentityFactory.
                                                  getDisplayIdentity((
                        QMTechnicsInfo) originalcopy);
                //获得对象类型 + 标识
                String s = displayidentity.getLocalizedMessage(null).trim();
                aobj1[0] = originalcopy.getLocation() + "\\" + s;
                techStatusValue = QMMessage.getLocalizedMessage(RESOURCE,
                        "workingCopyTitle", aobj1);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return techStatusValue;
            }
        }
        else if (WorkInProgressHelper.isCheckedOut((WorkableIfc) technics))
        {
            try
            {
                String checkOutByUser = findUserNameByID(technics.getLocker());
                if (checkOutByUser != null)
                {
                    Object aobj2[] =
                            {checkOutByUser};
                    techStatusValue = QMMessage.getLocalizedMessage(RESOURCE,
                            "techCheckedOutBy", aobj2);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return techStatusValue;
            }
        }
        else
        {
            try
            {
                FolderService folderService = (FolderService) EJBServiceHelper.
                                              getService("FolderService");
                boolean isDocPersonFolder = folderService.inPersonalFolder(
                        technics);
                if (isDocPersonFolder)
                {
                    techStatusValue = QMMessage.getLocalizedMessage(RESOURCE,
                            "inPesrsonFolder", null);
                }
                else
                {
                    techStatusValue = QMMessage.getLocalizedMessage(RESOURCE,
                            "techCheckedIn", null);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return techStatusValue;
            }
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: getTechnicsStatus(String techncisBsoID) end... return is " +
                    techStatusValue);
        }
        return techStatusValue;
    }


    /**
     * 获取工艺卡的工作状态信息(己检入/由XX检出/XX的工作副本/在个人文件柜)
     * @param techncisBsoID 工艺的bsoID
     * @return  工艺的工作状态信息
     * @throws QMException
     */
    public String getProcedureStatus(String procedureBsoID)
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: getProcedureStatus(String procedureBsoID) begin... procedureBsoID = " +
                    procedureBsoID);
        }
        String procedureStatusValue = "";
        PersistService persistService = null;
        WorkInProgressService wipService = null;
        QMProcedureInfo procedureInfo = null;
        try
        {
            persistService = (PersistService) EJBServiceHelper.getService(
                    "PersistService");
            procedureInfo = (QMProcedureInfo) persistService.refreshInfo(
                    procedureBsoID);
            wipService = (WorkInProgressService) EJBServiceHelper.getService(
                    "WorkInProgressService");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return procedureStatusValue;
        }
        boolean flag = WorkInProgressHelper.isWorkingCopy((WorkableIfc)
                procedureInfo); //是否是工作副本
        if (flag)
        {
            try
            {
                WorkableIfc originalcopy = wipService.originalCopyOf((
                        WorkableIfc) procedureInfo); //获得原本拷贝
                Object aobj1[] = new Object[1];
                if (originalcopy == null)
                {
                    aobj1[0] = "XXX";
                }
                aobj1[0] = originalcopy.getLocation() + "\\" +
                           ((QMProcedureInfo) originalcopy).
                           getTechnicsType().getCodeContent() +
                           ((QMProcedureInfo) originalcopy)
                           .getStepNumber() + "(" +
                           ((QMProcedureInfo) originalcopy).getStepName() + ")";
                procedureStatusValue = QMMessage.getLocalizedMessage(RESOURCE,
                        "workingCopyTitle", aobj1);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return procedureStatusValue;
            }
        }
        else if (WorkInProgressHelper.isCheckedOut((WorkableIfc) procedureInfo))
        {
            try
            {
                String checkOutByUser = findUserNameByID(procedureInfo.
                        getLocker());
                if (checkOutByUser != null)
                {
                    Object aobj2[] =
                            {checkOutByUser};
                    procedureStatusValue = QMMessage.getLocalizedMessage(
                            RESOURCE, "techCheckedOutBy", aobj2);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return procedureStatusValue;
            }
        }
        else
        {
            try
            {
                FolderService folderService = (FolderService) EJBServiceHelper.
                                              getService("FolderService");
                boolean isDocPersonFolder = folderService.inPersonalFolder(
                        procedureInfo);
                if (isDocPersonFolder)
                {
                    procedureStatusValue = QMMessage.getLocalizedMessage(
                            RESOURCE, "inPesrsonFolder", null);
                }
                else
                {
                    procedureStatusValue = QMMessage.getLocalizedMessage(
                            RESOURCE, "techCheckedIn", null);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return procedureStatusValue;
            }
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: getProcedureStatus(String procedureBsoID) end... return is " +
                    procedureStatusValue);
        }
        return procedureStatusValue;
    }


    /**
     * 由工艺得到扩展属性
     * @param technicsID String 工艺的BsoID
     * @throws QMException
     * @return Collection 扩展属性,每个元素都是数组,数组中第一个元素是扩展属性名称,
     *         第二个元素是扩展属性值
     */
    public Collection getDeAffixByTechnics(String technicsID)
            throws QMException
    {
        if (technicsID == null || technicsID.equals(""))
        {
            return null;
        }
        Collection returnC = null;
        try
        {
            PersistService persistService = (PersistService) EJBServiceHelper.
                                            getPersistService();
            BaseValueInfo technics = (BaseValueInfo) persistService.refreshInfo(
                    technicsID);
            if (technics == null)
            {
                return null;
            }
            if (!(technics instanceof ExtendAttriedIfc))
            {
                return null;
            }
            else
            {
                returnC = CappServiceHelper.getExtendNameAndValue((
                        ExtendAttriedIfc) technics);
            }
        }

        catch (QMException ex)
        {
            ex.printStackTrace();
            throw ex;
        }
        return returnC;
    }
    private static boolean ts16949 = (RemoteProperty.getProperty(
            "TS16949", "true")).equals("true");
    public Collection getDeAffixByProcedure(String procedureID) throws
        QMException {
      if (procedureID == null || procedureID.equals("")) {
        return null;
      }
      Collection returnC = new ArrayList();
      try {
        PersistService persistService = (PersistService) EJBServiceHelper.
            getPersistService();
        QMProcedureIfc procedure = (QMProcedureIfc) persistService.refreshInfo(
            procedureID);

        if(ts16949){
        Object[] obj1 = {
            "过程流程", CappServiceHelper.getExtendNameAndValue(procedure.
            getProcessFlow())};
        returnC.add(obj1);
        Object[] obj2 = {
            "过程FEMA ", CappServiceHelper.getExtendNameAndValue(procedure.
            getFema())};
        returnC.add(obj2);
        Object[] obj3 = {
            "控制计划 ", CappServiceHelper.getExtendNameAndValue(procedure.
            getProcessControl())};
        returnC.add(obj3);
      }
        Object[] obj4 = {
            "附加信息", CappServiceHelper.getExtendNameAndValue( (
            ExtendAttriedIfc) procedure)};
        returnC.add(obj4);

      }

      catch (QMException ex) {
        ex.printStackTrace();
        throw ex;
      }
      return returnC;
    }

    /**
     * 由工艺卡的bsoID获得工艺与零件关联的属性信息
     * 其中包括属性的显示名,属性值(属性值中第一个元素是图标路径,第二个是零部件的bsoID,其余元素与属性显示名一一对应)
     * @param technicsID String 工艺卡bsoID
     * @throws QMException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @return Collection 第一个元素是属性的显示名集合,其他元素是图标路经和属性值集合
     */
    public Collection getPartbyTechnics(String technicsID)
            throws QMException,
            ClassNotFoundException, InvocationTargetException,
            IllegalAccessException
    {
        if (technicsID == null || technicsID.equals(""))
        {
            return null;
        }
        PersistService persistService = (PersistService) EJBServiceHelper.
                                        getPersistService();
        QMTechnicsIfc technics = (QMTechnicsIfc) persistService.refreshInfo(
                technicsID);
        if (technics == null)
        {
            return null;
        }
        Collection returnC = null;
        ArrayList list = getPartAndLinkAttr("com.faw_qm.cappclients.capp.view" +
                                            technics.getTechnicsType());

        if (list != null)
        {
            returnC = new ArrayList();
            //零件属性名
            Object[] partObjs = (Object[]) list.get(0);
            //关联属性名
            Object[] linkObjs = (Object[]) list.get(1);
            //零件属性显示名
            Object[] labelObjs = (Object[]) list.get(2);
            int plenth = partObjs.length;
            int llenth = linkObjs.length;

            //所有属性的显示名
            Object[] allLabels = new Object[plenth + llenth];
            System.arraycopy(labelObjs, 0, allLabels, 0, plenth);
            String technicsType = technics.getTechnicsType().getCodeContent();
            Class classobj = Class.forName(
                    "com.faw_qm.capp.model.PartUsageQMTechnicsLinkInfo");
            for (int i = 0; i < llenth; i++)
            {
                allLabels[plenth + i] = CappAssociationsUtil.getDisplayName(
                        (String) linkObjs[i], classobj, technicsType);
                if (verbose)
                {
                    System.out.println(allLabels[plenth + i]);
                }
            }
            returnC.add(allLabels);

            Collection partAndLinkC = CappServiceHelper.getPartsAndUsageLinks(
                    technics);
            //得到属性值,包括图标路径
            if (partAndLinkC != null && partAndLinkC.size() != 0)
            {
                for (Iterator it = partAndLinkC.iterator(); it.hasNext(); )
                {
                    Object[] obj = (Object[]) it.next();
                    QMPartInfo partinfo = (QMPartInfo) obj[1];
                    PartUsageQMTechnicsLinkInfo linkinfo = (
                            PartUsageQMTechnicsLinkInfo) obj[0];
                    Object[] values = new Object[plenth + llenth + 2];
                    //图标路经
                    String imagePath = CappServiceHelper.getImageURL(partinfo);
                    imagePath = imagePath.substring(1, imagePath.length());
                    values[0] = imagePath;
                    values[1] = partinfo.getBsoID();
                    for (int i = 0; i < plenth; i++)
                    {
                        values[i +
                                2] = CappAssociationsUtil.getAttValue((Object)
                                partinfo,
                                (String) partObjs[i]);
                    }
                    for (int i = 0; i < llenth; i++)
                    {
                        values[plenth + i +
                                2] = CappAssociationsUtil.getAttValue((Object)
                                linkinfo, (String) linkObjs[i]);
                    }
                    returnC.add(values);
                }
            }
        }

        return returnC;
    }

    public Collection getPartbyProcedure(String procedureID)
            throws QMException,
            ClassNotFoundException, InvocationTargetException,
            IllegalAccessException
    {
        System.out.println(
                "getPartbyProcedure(String procedureID) end begin procedureID= " +
                procedureID);
        if (procedureID == null || procedureID.equals(""))
        {
            return null;
        }
        PersistService persistService = (PersistService) EJBServiceHelper.
                                        getPersistService();
        QMProcedureIfc procedure = (QMProcedureIfc) persistService.refreshInfo(
                procedureID);
        if (procedure == null)
        {
            return null;
        }
        Collection returnC = null;
        ArrayList list = getPartAndProcedurePartLinkAttr(
                "com.faw_qm.cappclients.capp.view" +
                procedure.getTechnicsType());
        System.out.println("list=" + list);
        if (list != null)
        {
            returnC = new ArrayList();
            //零件属性名
            Object[] partObjs = (Object[]) list.get(0);
            //关联属性名
            Object[] linkObjs = (Object[]) list.get(1);
            //零件属性显示名
            Object[] labelObjs = (Object[]) list.get(2);
            int plenth = partObjs.length;
            int llenth = linkObjs.length;

            //所有属性的显示名
            Object[] allLabels = new Object[plenth + llenth];
            System.arraycopy(labelObjs, 0, allLabels, 0, plenth);
            String technicsType = procedure.getTechnicsType().getCodeContent();
            Class classobj = Class.forName(
                    "com.faw_qm.capp.model.QMProcedureQMPartLinkInfo");
            for (int i = 0; i < llenth; i++)
            {
                allLabels[plenth + i] = CappAssociationsUtil.getDisplayName(
                        (String) linkObjs[i], classobj, technicsType);
                if (verbose)
                {
                    System.out.println(allLabels[plenth + i]);
                }
            }
            returnC.add(allLabels);

            Collection partAndLinkC = CappServiceHelper.getPartByProcedure(
                    procedureID, true);
            //得到属性值,包括图标路径
            if (partAndLinkC != null && partAndLinkC.size() != 0)
            {
                for (Iterator it = partAndLinkC.iterator(); it.hasNext(); )
                {
                    Object[] obj = (Object[]) it.next();
                    QMPartInfo partinfo = (QMPartInfo) obj[1];
                    QMProcedureQMPartLinkIfc linkinfo = (
                            QMProcedureQMPartLinkIfc) obj[0];
                    Object[] values = new Object[plenth + llenth + 2];
                    //图标路经
                    String imagePath = CappServiceHelper.getImageURL(partinfo);
                    imagePath = imagePath.substring(1, imagePath.length());
                    values[0] = imagePath;
                    values[1] = partinfo.getBsoID();
                    for (int i = 0; i < plenth; i++)
                    {
                        values[i +
                                2] = CappAssociationsUtil.getAttValue((Object)
                                partinfo,
                                (String) partObjs[i]);
                    }
                    for (int i = 0; i < llenth; i++)
                    {
                        values[plenth + i +
                                2] = CappAssociationsUtil.getAttValue((Object)
                                linkinfo, (String) linkObjs[i]);
                    }
                    System.out.println("values=" + values);
                    returnC.add(values);
                }
            }
        }
        System.out.println(
                "getPartbyProcedure(String procedureID) end return is " +
                returnC);
        return returnC;
    }


    /**
     * 由工艺找到材料,要获得哪些属性值在capp.properties文件已经配置
     * 返回值中包括属性名,零件的bsoID,属性值
     * @param technicsID String 工艺的bsoID
     * @return Collection 由数组组成,第一个元素是关联的属性显示名数组,其余是零件bsoID与属性值数组
     * 如[[零件编号,零件名称],[QMPart_200334,编号1,名称1],[QMPart_200322,编号2,名称2]]
     */
    public Collection getMaterialByTechnics(String technicsID)
            throws QMException, ClassNotFoundException,
            InvocationTargetException, IllegalAccessException
    {
        if (technicsID == null || technicsID.equals(""))
        {
            return null;
        }
        PersistService persistservice = (PersistService) EJBServiceHelper.
                                        getPersistService();
        QMTechnicsIfc technics = (QMTechnicsIfc) persistservice.refreshInfo(
                technicsID);
        if (technics == null)
        {
            return null;
        }
        Collection returnC = null;
        ArrayList list = getMaterialAndLinkAttr("QMTechnicsQMMaterialLink" +
                                                technics.getTechnicsType());

        if (list != null)
        {
            returnC = new ArrayList();
            //材料属性名
            Object[] maObjs = (Object[]) list.get(0);
            //关联属性名
            Object[] linkObjs = (Object[]) list.get(1);
            //材料属性显示名
            Object[] labelObjs = (Object[]) list.get(2);
            int mlenth = maObjs.length;
            int llenth = linkObjs.length;

            //所有属性的显示名
            Object[] allLabels = new Object[mlenth + llenth];
            System.arraycopy(labelObjs, 0, allLabels, 0, mlenth);
            String technicsType = technics.getTechnicsType().getCodeContent();
            Class classobj = Class.forName(
                    "com.faw_qm.capp.model.QMTechnicsQMMaterialLinkInfo");
            for (int i = 0; i < llenth; i++)
            {
                allLabels[mlenth + i] = CappAssociationsUtil.getDisplayName(
                        (String) linkObjs[i], classobj, technicsType);
                if (verbose)
                {
                    System.out.println(allLabels[mlenth + i]);
                }
            }
            returnC.add(allLabels);

            Collection materAndLinkC = CappServiceHelper.
                                       getMaterialsByTechnicsIfc(technicsID);
            //得到属性值
            if (materAndLinkC != null && materAndLinkC.size() != 0)
            {
                for (Iterator it = materAndLinkC.iterator(); it.hasNext(); )
                {
                    Object[] obj = (Object[]) it.next();
                    QMMaterialInfo materialinfo = (QMMaterialInfo) obj[1];
                    QMTechnicsQMMaterialLinkInfo linkinfo = (
                            QMTechnicsQMMaterialLinkInfo) obj[0];
                    Object[] values = new Object[mlenth + llenth + 1];
                    values[0] = materialinfo.getBsoID();
                    for (int i = 0; i < mlenth; i++)
                    {
                        values[i +
                                1] = CappAssociationsUtil.getAttValue((Object)
                                materialinfo, (String) maObjs[i]);
                    }
                    for (int i = 0; i < llenth; i++)
                    {
                        values[mlenth + i +
                                1] = CappAssociationsUtil.
                                     getAttValue((Object) linkinfo,
                                                 (String) linkObjs[i]);
                    }
                    returnC.add(values);
                }
            }
        }

        return returnC;
    }


    /**
     * 得到属性名,关联属性名,标题.这些信息已经配在属性文件中,此方法到属性文件中读,然后解析.
     * @param key 在属性文件中配置的键值
     * @return ArrayList 第一个元素是属性名数组,第二个元素是关联属性名,第三个元素是材料属性的标题
     */
    private ArrayList getMaterialAndLinkAttr(String key)
    {
        ArrayList list = null;
        String attr = RemoteProperty.getProperty(key);
        if (attr != null)
        {
            StringTokenizer token = new StringTokenizer(attr.trim(), ";");
            StringTokenizer maAttr = new StringTokenizer(token.nextToken(), ",");
            StringTokenizer linkAttr = new StringTokenizer(token.nextToken(),
                    ",");
            StringTokenizer labels = new StringTokenizer(token.nextToken(), ",");
            //材料属性名
            Object[] maObjs = new Object[maAttr.countTokens()];
            int i = 0;
            while (maAttr.hasMoreTokens())
            {
                maObjs[i++] = maAttr.nextToken();
            }
            //关联属性名
            Object[] linkObjs = new Object[linkAttr.countTokens()];
            i = 0;
            while (linkAttr.hasMoreTokens())
            {
                linkObjs[i++] = linkAttr.nextToken();
            }
            //标题
            Object[] labelObjs = new Object[labels.countTokens()];
            i = 0;
            while (labels.hasMoreTokens())
            {
                labelObjs[i++] = labels.nextToken();
            }

            list = new ArrayList(2);
            list.add(maObjs);
            list.add(linkObjs);
            list.add(labelObjs);
        }
        return list;
    }


    /**
     * 得到零部件与工艺关联属性名,标题.这些信息已经配在属性文件中,此方法到属性文件中读,然后解析.
     * @param key 在属性文件中配置的键值
     * @return ArrayList 第一个元素是零件属性名数组,第二个元素是关联属性名,第三个元素是零件属性的标题
     */
    private ArrayList getPartAndLinkAttr(String key)
    {
        ArrayList list = null;
        String attr = RemoteProperty.getProperty(key);
        if (attr != null)
        {
            StringTokenizer token = new StringTokenizer(attr.trim(), ";");
            StringTokenizer maAttr = new StringTokenizer(token.nextToken(), ",");
            StringTokenizer linkAttr = new StringTokenizer(token.nextToken(),
                    ",");
            token.nextToken();
            StringTokenizer labels = new StringTokenizer(token.nextToken(), ",");
            //材料属性名
            Object[] maObjs = new Object[maAttr.countTokens()];
            int i = 0;
            while (maAttr.hasMoreTokens())
            {
                maObjs[i++] = maAttr.nextToken();
            }
            //关联属性名
            Object[] linkObjs = new Object[linkAttr.countTokens()];
            i = 0;
            while (linkAttr.hasMoreTokens())
            {
                linkObjs[i++] = linkAttr.nextToken();
            }
            //标题
            Object[] labelObjs = new Object[labels.countTokens()];
            i = 0;
            while (labels.hasMoreTokens())
            {
                labelObjs[i++] = labels.nextToken();
            }

            list = new ArrayList(2);
            list.add(maObjs);
            list.add(linkObjs);
            list.add(labelObjs);
        }
        return list;
    }


    /**
     * 得到零部件与工序关联属性名,标题.这些信息已经配在属性文件中,此方法到属性文件中读,然后解析.
     * @param key 在属性文件中配置的键值
     * @return ArrayList 第一个元素是零件属性名数组,第二个元素是关联属性名,第三个元素是零件属性的标题
     */
    private ArrayList getPartAndProcedurePartLinkAttr(String key)
    {
        ArrayList list = null;
        String attr = RemoteProperty.getProperty(key);
        if (attr != null && !attr.equals("null"))
        {
            StringTokenizer token = new StringTokenizer(attr.trim(), ";");
            StringTokenizer maAttr = new StringTokenizer(token.nextToken(), ",");
            StringTokenizer linkAttr = new StringTokenizer(token.nextToken(),
                    ",");
            token.nextToken();
            StringTokenizer labels = new StringTokenizer(token.nextToken(), ",");
            //材料属性名
            Object[] maObjs = new Object[maAttr.countTokens()];
            int i = 0;
            while (maAttr.hasMoreTokens())
            {
                maObjs[i++] = maAttr.nextToken();
            }
            //关联属性名
            Object[] linkObjs = new Object[linkAttr.countTokens()];
            i = 0;
            while (linkAttr.hasMoreTokens())
            {
                linkObjs[i++] = linkAttr.nextToken();
            }
            //标题
            Object[] labelObjs = new Object[labels.countTokens()];
            i = 0;
            while (labels.hasMoreTokens())
            {
                labelObjs[i++] = labels.nextToken();
            }

            list = new ArrayList(2);
            list.add(maObjs);
            list.add(linkObjs);
            list.add(labelObjs);
        }
        return list;
    }


    /**
     * 由工艺的bsoId得到关联文档的bsoID,编号,名称,类型,最新版本
     * @param technicsID String 工艺的bsoID
     * @throws QMException
     * @return Collection 由数组组成,数组中存放文档的bsoID,编号,名称,类型,最新版本
     */
    public ArrayList getDocByTechnicsID(String technicsID)
            throws QMException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.web.ViewTechnicsAttrUtil:getDocByTechnicsID(String technicsID) begin"
                               + " technicsID=" + technicsID);
        }
        if (technicsID == null || technicsID.equals(""))
        {
            return null;
        }
        ArrayList returnC = null;
        Collection vec = CappServiceHelper.getDocMastersByTechnicsID(technicsID);

        if (vec != null && vec.size() != 0)
        {
            returnC = getDocAttrByDocMaster(vec);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.web.ViewTechnicsAttrUtil:getDocByTechnicsID(String technicsID) end"
                               + " return is " + returnC);

        }
        return returnC;

    }


    /**
     * 由工序的bsoId得到关联文档的bsoID,编号,名称,类型,最新版本
     * @param procedureID String 工序的bsoID
     * @throws QMException
     * @return Collection 由数组组成,数组中存放文档的bsoID,编号,名称,类型,最新版本
     */
    public ArrayList getDocByProcedureID(String procedureID)
            throws QMException
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.web.ViewTechnicsAttrUtil:getDocByProcedureID(String procedureID) begin"
                               + " procedureID=" + procedureID);
        }
        if (procedureID == null || procedureID.equals(""))
        {
            return null;
        }
        ArrayList returnC = null;
        Collection vec = CappServiceHelper.getDocMastersByProcedureID(
                procedureID);

        if (vec != null && vec.size() != 0)
        {
            returnC = getDocAttrByDocMaster(vec);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.web.ViewTechnicsAttrUtil:getDocByProcedureID(String procedureID) end"
                               + " return is " + returnC);

        }
        return returnC;

    }


    /**
     * 由DocMasterIfc得到文档属性:文档的bsoID,编号,名称,类型,最新版本
     * @param c Collection DocMasterIfc集合
     * @throws QMException
     * @return ArrayList 由数组组成,数组中存放文档的bsoID,编号,名称,类型,最新版本
     */
    private ArrayList getDocAttrByDocMaster(Collection c)
            throws QMException
    {
        ArrayList returnC = null;

        if (c != null && c.size() != 0)
        {
            returnC = new ArrayList();
            for (Iterator it = c.iterator(); it.hasNext(); )
            {
                String[] values = new String[5];
                //通过master找到最新版本的doc
                DocMasterInfo docMaster = (DocMasterInfo) it.next();
                if (verbose)
                {
                    System.out.println("docMaster=" + docMaster);
                }
                DocInfo infos = (DocInfo) CappServiceHelper.getLatestIteration((
                        MasteredIfc) docMaster);
                if (verbose)
                {
                    System.out.println("infos=" + infos);
                }
                values[0] = docMaster.getBsoID();
                values[1] = docMaster.getDocNum();
                values[2] = docMaster.getDocName();
                values[3] = DocServiceHelper.findDocCfLeafNodePathByID(infos.
                        getDocCfBsoID());
                if (verbose)
                {
                    System.out.println("文档类型=" + values[2]);
                }
                values[4] = infos.getVersionID();
                returnC.add(values);
            }
        }
        return returnC;

    }


    /**
     * 由jsp调用
     * 通过工序的ID查询值对象,并把jsp页面中需要的属性设到值对象中
     * @param bsoID
     * @return
     * @throws QMException
     */
    public QMProcedureInfo findProcedureInfoByID(String bsoID)
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: findProcedureInfoByID begin bsoID=" +
                    bsoID);
        }
        QMProcedureInfo procedureInfo = null;
        try
        {
            //  获得持久化服g务
            PersistService persistService = (PersistService)
                                            EJBServiceHelper.getService(
                    "PersistService");
            procedureInfo = (QMProcedureInfo) persistService.refreshInfo(bsoID);
            procedureInfo.setIterationCreator(findUserNameByID(procedureInfo.
                    getIterationCreator()));
            procedureInfo.setIterationModifier(findUserNameByID(procedureInfo.
                    getIterationModifier()));

        }
        catch (Exception e)
        {
            throw new QMException(e);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.web.ViewTechnicsAttrUtil:findProcedureInfoByID  end return is " +
                               procedureInfo);
        }
        return procedureInfo;
    }

    public String getContent(String procedureID)
    	throws QMException
	{
		if (verbose)
		{
		    System.out.println(
		            "cappclients.capp.web.ViewTechnicsAttrUtil:getContent begin.. procedureID=" +
		            procedureID);
		}
		//CCBegin by liuzhicheng 2010-02-23  原因:解放增加工艺内容（带特殊符号）瘦客户查看。
		String s = null;
		String begin = String.valueOf((char)128);
		PersistService service = (PersistService) EJBServiceHelper.
		                         getPersistService();
		
		QMProcedureInfo procedure = (QMProcedureInfo) service.refreshInfo(
		        procedureID);
		Vector vvt = procedure.getContent();
		boolean isSpechar = false;
		for(int i = 0 ; i<vvt.size() ; i++)
		{
			String spechar = vvt.elementAt(i).toString();
			int pot = spechar.indexOf(begin);
		    if(pot != -1 && (pot != spechar.length() - 1))
		    {
		    	//含有特殊字符
		    	s = null;
		    }
		    else
			{
				isSpechar = true;
			}
		}
		if(isSpechar)
		{
			//正常的字符串
			s = DataDisplay.translate(procedure.getContent());
		}
		//CCEnd by liuzhicheng 2010-02-23  原因:解放增加工艺内容（带特殊符号）瘦客户查看。
		if (verbose)
		{
		    System.out.println(
		            "cappclients.capp.web.ViewTechnicsAttrUtil:getContent end return is" +
		            s);
		}
		return s;
	}



    public String getGraphics(String procedureID)
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil:getGraphics begin procedureID=" +
                    procedureID);
        }
        String s = null;
        PersistService service = (PersistService) EJBServiceHelper.
                                 getPersistService();
        QMProcedureInfo procedure = (QMProcedureInfo) service.refreshInfo(
                procedureID);
       /* if (procedure.getTechGraphics() != null)
        {
            s = "有";
        }
        else*/
        {
            s = "无";
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil:getGraphics end return is" +
                    s);
        }
        return s;

    }


    /**
     * 由工艺id得到关联的工序值对象
     * @param bsoID String
     * @throws QMException
     * @return Collection
     */
    public Collection getProceduresByTech(String bsoID)
            throws QMException
    {
        if (bsoID == null)
        {
            return null;
        }
        StandardCappService cappservice = (StandardCappService)
                                          EJBServiceHelper.getService(
                "StandardCappService");
        return cappservice.browseProcedures(bsoID, bsoID);
    }
    /**
     * 由当前工序得到它的上一序和下一序的集合
     * @param technicsBsoID 工序所属的工艺卡bsoID
     * @param procedureID 当前工序bsoID
     * @return Collection
     * @throws QMException
     */
    public Collection getPreviousAndNextProcedure(String technicsBsoID,String procedureID)throws QMException
    {
        Vector vec=new Vector();
        Collection all=getProceduresByTech(technicsBsoID);
        Vector tempVec=new Vector(all);
        QMProcedureIfc ifc=null;
        for(int i=0;i<tempVec.size();i++)
        {
            ifc=(QMProcedureIfc)tempVec.elementAt(i);
            if(procedureID.equals(ifc.getBsoID()))
            {
                if(i>0)
                    vec.addElement(tempVec.elementAt(i-1));
                else
                    vec.addElement(null);
                if(i==tempVec.size()-1)
                    vec.addElement(null);
                else
                    vec.addElement(tempVec.elementAt(i+1));
            }
        }
        return vec;
    }
    /**
     * 由当前工序得到它的上一序的集合
     * @param technicsBsoID 工序所属的工艺卡bsoID
     * @param procedureID 当前工序bsoID
     * @return Collection
     * @throws QMException
     */
    public Collection getPreviousProcedure(String technicsBsoID,String procedureID)throws QMException
    {
    	Vector vec=new Vector();
        Collection all=getProceduresByTech(technicsBsoID);
        Vector tempVec=new Vector(all);
        QMProcedureIfc ifc=null;
        for(int i=0;i<tempVec.size();i++)
        {
            ifc=(QMProcedureIfc)tempVec.elementAt(i);
            if(procedureID.equals(ifc.getBsoID()))
            {
                if(i>0)
                    vec.addElement(tempVec.elementAt(i-1));
                else
                    vec.addElement(null);
            }
        }
        return vec;
    }
    /**
     * 由当前工序得到它的下一序的集合
     * @param technicsBsoID 工序所属的工艺卡bsoID
     * @param procedureID 当前工序bsoID
     * @return Collection
     * @throws QMException
     */
    public Collection getNextProcedure(String technicsBsoID,String procedureID)throws QMException
    {
    	Vector vec=new Vector();
        Collection all=getProceduresByTech(technicsBsoID);
        Vector tempVec=new Vector(all);
        QMProcedureIfc ifc=null;
        for(int i=0;i<tempVec.size();i++)
        {
            ifc=(QMProcedureIfc)tempVec.elementAt(i);
            if(procedureID.equals(ifc.getBsoID()))
            {
                if(i==tempVec.size()-1)
                    vec.addElement(null);
                else
                    vec.addElement(tempVec.elementAt(i+1));
            }
        }
        return vec;
    }


    /**
     * 由工序的bsoID得到工步属性
     * @param procedureId String 工序的bsoID
     * @return ArrayList 由数组组成,数组中存放:bsoID,工步号,工步内容,版本
     */
    public ArrayList getPaceAttr(String techID, String procedureId)
            throws QMException
    {

        StandardCappService cappservice = (StandardCappService)
                                          EJBServiceHelper.getService(
                "StandardCappService");
        Collection childs = cappservice.browseProcedures(techID, procedureId);
        ArrayList list = null;
        //CCBegin by liuzhicheng 2010-02-23  原因:解放增加工艺内容（带特殊符号）瘦客户查看。
        String begin = String.valueOf((char)128);
        if (childs != null && childs.size() != 0)
        {
            list = new ArrayList();
            for (Iterator it = childs.iterator(); it.hasNext(); )
            {
                QMProcedureIfc child = (QMProcedureIfc) it.next();
                String[] arrs = new String[4];
                arrs[0] = child.getBsoID();  
                arrs[1] = String.valueOf(child.getStepNumber());
                String content = "";
                if (child.getContent() != null)
                {
                	String spechar = (String) child.getContent().get(0);
        			int pot = spechar.indexOf(begin);
        		    if(pot != -1 && (pot != spechar.length() - 1))
        		    {
        		    	//含有特殊字符
        		    	content = null;
        		    }
                	else
                	{
                		content = DataDisplay.translate(child.getContent());
                	}
        		    //CCBegin by liuzhicheng 2010-02-23 。
                }
                arrs[2] = content;
                arrs[3] = child.getVersionValue();
                list.add(arrs);
            }
        }
        return list;
   
    }


    /**
     * 由工序id得到关联的材料对象和关联本身对象
     * @param id String 工序bsoID
     * @throws QMException
     * @return Collection 材料关联与材料值对象组成数组的集合
     */
    public Collection getMaterialAndLinkBuProcedure(String id)
            throws
            QMException
    {
        return CappServiceHelper.getMaterialsByProcedureID(id, true);
    }


    /**
     * 由工序id得到关联的设备对象和关联本身对象
     * @param id String 工序bsoID
     * @throws QMException
     * @return Collection 设备关联与设备值对象组成数组的集合
     */

    public Collection getEquipmentAndLinkBuProcedure(String id)
            throws
            QMException
    {
        return CappServiceHelper.getEquipmentsByProcedure(id, true);
    }


    /**
     * 由工序id得到关联的工装对象和关联本身对象
     * @param id String 工序bsoID
     * @throws QMException
     * @return Collection 工装关联与工装值对象组成数组的集合
     */

    public Collection getToolAndLinkBuProcedure(String id)
            throws QMException
    {
        return CappServiceHelper.getToolsByProcedure(id, true);
    }


    /**
     * 获得关联工艺
     * @param procedureID String
     */
    public Object[] getRelationTechByProcedure(String procedureID)
            throws QMException
    {
        PersistService service = (PersistService) EJBServiceHelper.
                                 getPersistService();
        QMProcedureInfo procedure = (QMProcedureInfo) service.refreshInfo(
                procedureID);
        String techID = procedure.getRelationCardBsoID();
        if (techID == null)
        {
            return null;
        }
        else
        {
            Object[] obj = new Object[5];
            QMTechnicsIfc technics = (QMTechnicsIfc) service.refreshInfo(techID);
            String imagePath = CappServiceHelper.getImageURL(technics);
            imagePath = imagePath.substring(1, imagePath.length());
            obj[0] = imagePath;
            obj[1] = techID;
            obj[2] = technics.getTechnicsNumber();
            obj[3] = technics.getTechnicsName();
            VersionControlService versionservice = (VersionControlService)
                    EJBServiceHelper.getService("VersionControlService");
            Collection c = versionservice.allVersionsOf(technics);
            obj[4] = ((QMTechnicsIfc) c.toArray()[0]).getVersionValue();
            return obj;
        }

    }


    /**
     * 得到工步的父工序的属性
     * @param techID String
     * @param procedureID String
     * @return Object[]
     */
    public Object[] findLastVersionFatherProcedureAtt(String techID,
            String procedureID)
            throws QMException
    {
        if (techID == null || procedureID == null)
        {
            return null;
        }
        StandardCappService cappservice = (StandardCappService)
                                          EJBServiceHelper.getService(
                "StandardCappService");
        QMProcedureIfc father = cappservice.findLastVersionFatherProcedure(
                techID, procedureID);
        //薛凯 修改 原因：浏览加锁的工步导致界面异常 20080317
        if(null==father)
        {
            Object[] obj = new Object[3];
            obj[0] = "";
            obj[1] = "";
            obj[2] = "";
            return obj;
        }
        else
        {
            Object[] obj = new Object[3];
            obj[0] = String.valueOf(father.getStepNumber());
            obj[1] = father.getStepName();
            obj[2] = father.getVersionID();
            return obj;
        }
        //薛凯 修改结束
    }
    
    
    /**
     * 得到指定工艺内容容器中指定的数据项
     * @param priInfo QMEquipmentInfo 内容容器
     * @return Vector ApplicationDataInfo 内容项
     * @throws RationException 
     * @author liunan 2009-10-12 根据解放要求，为工艺瘦客户添加附件的查看。
     */
    public Vector getContents(String id)throws QMException
    {
    	QMTechnicsIfc ifc = findTechnicsInfoByID(id);  
    	Vector c = null;
    	try
    	{
    		ContentService contentService = (ContentService)EJBServiceHelper.getService("ContentService");
    		c = (Vector)contentService.getContents((ContentHolderIfc)ifc);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
		  return c;
    }
    
//  CCBegin by leixiao 2010-2-9 原因：增加零件查看工艺规程
    public Collection getcappByPartmaster(String partID) throws
    QMException {
    	ProductManagerOfTechnicsService proService =
      (ProductManagerOfTechnicsService) EJBServiceHelper.getService(
      "ProductManagerOfTechnicsService");
    	return proService.getTechnicsBypart(partID);
}
//  CCEnd by leixiao 2010-2-9  原因：增加零件查看工艺规程
    
    public static void main(String[] args)
    {

    }
}
