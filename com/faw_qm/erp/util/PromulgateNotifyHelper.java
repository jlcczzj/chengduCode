/**
 * ���ɳ���PromulgateNotifyHelper.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.util;

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
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.doc.exception.DocException;
import com.faw_qm.erp.ejb.service.MaterialSplitService;
import com.faw_qm.erp.ejb.service.PromulgateNotifyService;
import com.faw_qm.erp.ejb.service.TechnicDataService;
import com.faw_qm.erp.model.PromulgateNotifyInfo;
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
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
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
     * ��ù�����Ʒ
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
                    .getService("PromulgateNotifyService");
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
     * ��ù����ĵ�
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
                    .getService("PromulgateNotifyService");
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
     * ��ȡ������������ģ������ơ�
     * @return  Vector  ������������ģ������ơ�
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
            //��ȡ�������ڷ���
            LifeCycleService lcService = (LifeCycleService) EJBServiceHelper
                    .getService("LifeCycleService");
            //��ȡ������������ģ��
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
     * ��ȡ������������״̬
     * @return ������������״̬(State����)
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
     * ������������ID����ȡ����������
     * @param lfcbsoID ��������ID
     * @return ��ȡ����������
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
            //  ��ó־û�����
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
     * ���ö�ٵĲ��ñ��
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
        //���ĳ�ö���ļ�
        PromulgateNotifyFlag[] flag = PromulgateNotifyFlag
                .getPromulgateNotifyFlagSet();
        if(logger.isDebugEnabled())
        {
            logger.debug("findAllPromulgateNotifyFlag() - end"); //$NON-NLS-1$
        }
        return flag;
    }

    /**
     * ����part��Ʒ
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
                    .getService("PromulgateNotifyService");
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
            //  ��ó־û�����
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            //ˢ��ֵ����
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
        //��ʼ
        String userName = null;
        UserInfo userInfo = null;
        //  ��ó־û�����
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
     * ��ȡ�ĵ��Ĺ���״̬��Ϣ
     * @param docBsoID �ĵ���bsoID
     * @return  �ĵ��Ĺ���״̬��Ϣ
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
     * �������в���֪ͨ
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
                    .getService("PromulgateNotifyService");
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
                    .getService("PromulgateNotifyService");
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
     * ���ݱ�ʶ�����ʾ��
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
    
 
}
