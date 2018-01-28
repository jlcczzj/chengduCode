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
 * <p>Title: �ݿͻ��в鿴����,����İ�����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: faw_qm</p>
 * @author Ѧ��
 * @version 1.0
 */


public class ViewTechnicsAttrUtil implements Serializable
{

    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**��Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.capp.util.CappLMRB";


    public ViewTechnicsAttrUtil()
    {
    }


    /**
     * �ɹ��տ�ID�õ����տ�ֵ����
     * @param id ���տ�ID
     * @return ���տ�ֵ����
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
        //  ��ó־û�����
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
     * ���û�ID�õ��û���
     * @param userID
     * @return
     * @throws QMException
     */
    public String findUserNameByID(String userID)
            throws QMException
    {
        //��ʼ
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.ViewTechnicsAttrUtil: findUserNameByID begin" +
                    " userID= " + userID);
        }
        String userName = null;
        UserInfo userInfo = null;

        //  ��ó־û�����
        PersistService persistService = (PersistService)
                                        EJBServiceHelper.getService(
                "PersistService");

        //  �����û�ID����û�������
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
     * ����ĿID�õ���Ŀ��
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
            //  ��ó־û�����
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
     * ������������ID����ȡ����������
     * @param lfcbsoID ��������ID
     * @return ��ȡ����������
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
            //  ��ó־û�����
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
     * ��ȡ���տ��Ĺ���״̬��Ϣ(������/��XX���/XX�Ĺ�������/�ڸ����ļ���)
     * @param techncisBsoID ���յ�bsoID
     * @return  ���յĹ���״̬��Ϣ
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
                technics); //�Ƿ��ǹ�������
        if (flag)
        {
            try
            {
                WorkableIfc originalcopy = wipService.originalCopyOf((
                        WorkableIfc) technics); //���ԭ������
                Object aobj1[] = new Object[1];
                if (originalcopy == null)
                {
                    aobj1[0] = "XXX";
                }
                //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
                DisplayIdentity displayidentity = IdentityFactory.
                                                  getDisplayIdentity((
                        QMTechnicsInfo) originalcopy);
                //��ö������� + ��ʶ
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
     * ��ȡ���տ��Ĺ���״̬��Ϣ(������/��XX���/XX�Ĺ�������/�ڸ����ļ���)
     * @param techncisBsoID ���յ�bsoID
     * @return  ���յĹ���״̬��Ϣ
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
                procedureInfo); //�Ƿ��ǹ�������
        if (flag)
        {
            try
            {
                WorkableIfc originalcopy = wipService.originalCopyOf((
                        WorkableIfc) procedureInfo); //���ԭ������
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
     * �ɹ��յõ���չ����
     * @param technicsID String ���յ�BsoID
     * @throws QMException
     * @return Collection ��չ����,ÿ��Ԫ�ض�������,�����е�һ��Ԫ������չ��������,
     *         �ڶ���Ԫ������չ����ֵ
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
            "��������", CappServiceHelper.getExtendNameAndValue(procedure.
            getProcessFlow())};
        returnC.add(obj1);
        Object[] obj2 = {
            "����FEMA ", CappServiceHelper.getExtendNameAndValue(procedure.
            getFema())};
        returnC.add(obj2);
        Object[] obj3 = {
            "���Ƽƻ� ", CappServiceHelper.getExtendNameAndValue(procedure.
            getProcessControl())};
        returnC.add(obj3);
      }
        Object[] obj4 = {
            "������Ϣ", CappServiceHelper.getExtendNameAndValue( (
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
     * �ɹ��տ���bsoID��ù��������������������Ϣ
     * ���а������Ե���ʾ��,����ֵ(����ֵ�е�һ��Ԫ����ͼ��·��,�ڶ������㲿����bsoID,����Ԫ����������ʾ��һһ��Ӧ)
     * @param technicsID String ���տ�bsoID
     * @throws QMException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @return Collection ��һ��Ԫ�������Ե���ʾ������,����Ԫ����ͼ��·��������ֵ����
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
            //���������
            Object[] partObjs = (Object[]) list.get(0);
            //����������
            Object[] linkObjs = (Object[]) list.get(1);
            //���������ʾ��
            Object[] labelObjs = (Object[]) list.get(2);
            int plenth = partObjs.length;
            int llenth = linkObjs.length;

            //�������Ե���ʾ��
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
            //�õ�����ֵ,����ͼ��·��
            if (partAndLinkC != null && partAndLinkC.size() != 0)
            {
                for (Iterator it = partAndLinkC.iterator(); it.hasNext(); )
                {
                    Object[] obj = (Object[]) it.next();
                    QMPartInfo partinfo = (QMPartInfo) obj[1];
                    PartUsageQMTechnicsLinkInfo linkinfo = (
                            PartUsageQMTechnicsLinkInfo) obj[0];
                    Object[] values = new Object[plenth + llenth + 2];
                    //ͼ��·��
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
            //���������
            Object[] partObjs = (Object[]) list.get(0);
            //����������
            Object[] linkObjs = (Object[]) list.get(1);
            //���������ʾ��
            Object[] labelObjs = (Object[]) list.get(2);
            int plenth = partObjs.length;
            int llenth = linkObjs.length;

            //�������Ե���ʾ��
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
            //�õ�����ֵ,����ͼ��·��
            if (partAndLinkC != null && partAndLinkC.size() != 0)
            {
                for (Iterator it = partAndLinkC.iterator(); it.hasNext(); )
                {
                    Object[] obj = (Object[]) it.next();
                    QMPartInfo partinfo = (QMPartInfo) obj[1];
                    QMProcedureQMPartLinkIfc linkinfo = (
                            QMProcedureQMPartLinkIfc) obj[0];
                    Object[] values = new Object[plenth + llenth + 2];
                    //ͼ��·��
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
     * �ɹ����ҵ�����,Ҫ�����Щ����ֵ��capp.properties�ļ��Ѿ�����
     * ����ֵ�а���������,�����bsoID,����ֵ
     * @param technicsID String ���յ�bsoID
     * @return Collection ���������,��һ��Ԫ���ǹ�����������ʾ������,���������bsoID������ֵ����
     * ��[[������,�������],[QMPart_200334,���1,����1],[QMPart_200322,���2,����2]]
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
            //����������
            Object[] maObjs = (Object[]) list.get(0);
            //����������
            Object[] linkObjs = (Object[]) list.get(1);
            //����������ʾ��
            Object[] labelObjs = (Object[]) list.get(2);
            int mlenth = maObjs.length;
            int llenth = linkObjs.length;

            //�������Ե���ʾ��
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
            //�õ�����ֵ
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
     * �õ�������,����������,����.��Щ��Ϣ�Ѿ����������ļ���,�˷����������ļ��ж�,Ȼ�����.
     * @param key �������ļ������õļ�ֵ
     * @return ArrayList ��һ��Ԫ��������������,�ڶ���Ԫ���ǹ���������,������Ԫ���ǲ������Եı���
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
            //����������
            Object[] maObjs = new Object[maAttr.countTokens()];
            int i = 0;
            while (maAttr.hasMoreTokens())
            {
                maObjs[i++] = maAttr.nextToken();
            }
            //����������
            Object[] linkObjs = new Object[linkAttr.countTokens()];
            i = 0;
            while (linkAttr.hasMoreTokens())
            {
                linkObjs[i++] = linkAttr.nextToken();
            }
            //����
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
     * �õ��㲿���빤�չ���������,����.��Щ��Ϣ�Ѿ����������ļ���,�˷����������ļ��ж�,Ȼ�����.
     * @param key �������ļ������õļ�ֵ
     * @return ArrayList ��һ��Ԫ�����������������,�ڶ���Ԫ���ǹ���������,������Ԫ����������Եı���
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
            //����������
            Object[] maObjs = new Object[maAttr.countTokens()];
            int i = 0;
            while (maAttr.hasMoreTokens())
            {
                maObjs[i++] = maAttr.nextToken();
            }
            //����������
            Object[] linkObjs = new Object[linkAttr.countTokens()];
            i = 0;
            while (linkAttr.hasMoreTokens())
            {
                linkObjs[i++] = linkAttr.nextToken();
            }
            //����
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
     * �õ��㲿���빤�����������,����.��Щ��Ϣ�Ѿ����������ļ���,�˷����������ļ��ж�,Ȼ�����.
     * @param key �������ļ������õļ�ֵ
     * @return ArrayList ��һ��Ԫ�����������������,�ڶ���Ԫ���ǹ���������,������Ԫ����������Եı���
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
            //����������
            Object[] maObjs = new Object[maAttr.countTokens()];
            int i = 0;
            while (maAttr.hasMoreTokens())
            {
                maObjs[i++] = maAttr.nextToken();
            }
            //����������
            Object[] linkObjs = new Object[linkAttr.countTokens()];
            i = 0;
            while (linkAttr.hasMoreTokens())
            {
                linkObjs[i++] = linkAttr.nextToken();
            }
            //����
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
     * �ɹ��յ�bsoId�õ������ĵ���bsoID,���,����,����,���°汾
     * @param technicsID String ���յ�bsoID
     * @throws QMException
     * @return Collection ���������,�����д���ĵ���bsoID,���,����,����,���°汾
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
     * �ɹ����bsoId�õ������ĵ���bsoID,���,����,����,���°汾
     * @param procedureID String �����bsoID
     * @throws QMException
     * @return Collection ���������,�����д���ĵ���bsoID,���,����,����,���°汾
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
     * ��DocMasterIfc�õ��ĵ�����:�ĵ���bsoID,���,����,����,���°汾
     * @param c Collection DocMasterIfc����
     * @throws QMException
     * @return ArrayList ���������,�����д���ĵ���bsoID,���,����,����,���°汾
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
                //ͨ��master�ҵ����°汾��doc
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
                    System.out.println("�ĵ�����=" + values[2]);
                }
                values[4] = infos.getVersionID();
                returnC.add(values);
            }
        }
        return returnC;

    }


    /**
     * ��jsp����
     * ͨ�������ID��ѯֵ����,����jspҳ������Ҫ�������赽ֵ������
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
            //  ��ó־û���g��
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
		//CCBegin by liuzhicheng 2010-02-23  ԭ��:������ӹ������ݣ���������ţ��ݿͻ��鿴��
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
		    	//���������ַ�
		    	s = null;
		    }
		    else
			{
				isSpechar = true;
			}
		}
		if(isSpechar)
		{
			//�������ַ���
			s = DataDisplay.translate(procedure.getContent());
		}
		//CCEnd by liuzhicheng 2010-02-23  ԭ��:������ӹ������ݣ���������ţ��ݿͻ��鿴��
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
            s = "��";
        }
        else*/
        {
            s = "��";
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
     * �ɹ���id�õ������Ĺ���ֵ����
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
     * �ɵ�ǰ����õ�������һ�����һ��ļ���
     * @param technicsBsoID ���������Ĺ��տ�bsoID
     * @param procedureID ��ǰ����bsoID
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
     * �ɵ�ǰ����õ�������һ��ļ���
     * @param technicsBsoID ���������Ĺ��տ�bsoID
     * @param procedureID ��ǰ����bsoID
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
     * �ɵ�ǰ����õ�������һ��ļ���
     * @param technicsBsoID ���������Ĺ��տ�bsoID
     * @param procedureID ��ǰ����bsoID
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
     * �ɹ����bsoID�õ���������
     * @param procedureId String �����bsoID
     * @return ArrayList ���������,�����д��:bsoID,������,��������,�汾
     */
    public ArrayList getPaceAttr(String techID, String procedureId)
            throws QMException
    {

        StandardCappService cappservice = (StandardCappService)
                                          EJBServiceHelper.getService(
                "StandardCappService");
        Collection childs = cappservice.browseProcedures(techID, procedureId);
        ArrayList list = null;
        //CCBegin by liuzhicheng 2010-02-23  ԭ��:������ӹ������ݣ���������ţ��ݿͻ��鿴��
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
        		    	//���������ַ�
        		    	content = null;
        		    }
                	else
                	{
                		content = DataDisplay.translate(child.getContent());
                	}
        		    //CCBegin by liuzhicheng 2010-02-23 ��
                }
                arrs[2] = content;
                arrs[3] = child.getVersionValue();
                list.add(arrs);
            }
        }
        return list;
   
    }


    /**
     * �ɹ���id�õ������Ĳ��϶���͹����������
     * @param id String ����bsoID
     * @throws QMException
     * @return Collection ���Ϲ��������ֵ�����������ļ���
     */
    public Collection getMaterialAndLinkBuProcedure(String id)
            throws
            QMException
    {
        return CappServiceHelper.getMaterialsByProcedureID(id, true);
    }


    /**
     * �ɹ���id�õ��������豸����͹����������
     * @param id String ����bsoID
     * @throws QMException
     * @return Collection �豸�������豸ֵ�����������ļ���
     */

    public Collection getEquipmentAndLinkBuProcedure(String id)
            throws
            QMException
    {
        return CappServiceHelper.getEquipmentsByProcedure(id, true);
    }


    /**
     * �ɹ���id�õ������Ĺ�װ����͹����������
     * @param id String ����bsoID
     * @throws QMException
     * @return Collection ��װ�����빤װֵ�����������ļ���
     */

    public Collection getToolAndLinkBuProcedure(String id)
            throws QMException
    {
        return CappServiceHelper.getToolsByProcedure(id, true);
    }


    /**
     * ��ù�������
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
     * �õ������ĸ����������
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
        //Ѧ�� �޸� ԭ����������Ĺ������½����쳣 20080317
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
        //Ѧ�� �޸Ľ���
    }
    
    
    /**
     * �õ�ָ����������������ָ����������
     * @param priInfo QMEquipmentInfo ��������
     * @return Vector ApplicationDataInfo ������
     * @throws RationException 
     * @author liunan 2009-10-12 ���ݽ��Ҫ��Ϊ�����ݿͻ���Ӹ����Ĳ鿴��
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
    
//  CCBegin by leixiao 2010-2-9 ԭ����������鿴���չ��
    public Collection getcappByPartmaster(String partID) throws
    QMException {
    	ProductManagerOfTechnicsService proService =
      (ProductManagerOfTechnicsService) EJBServiceHelper.getService(
      "ProductManagerOfTechnicsService");
    	return proService.getTechnicsBypart(partID);
}
//  CCEnd by leixiao 2010-2-9  ԭ����������鿴���չ��
    
    public static void main(String[] args)
    {

    }
}
