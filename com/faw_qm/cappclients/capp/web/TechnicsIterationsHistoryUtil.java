/** ���ɳ���TechnicsIterationsHistoryUtil.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capp.web;

import java.util.Collection;
import java.util.Vector;

import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsMasterIfc;
import com.faw_qm.capp.model.QMTechnicsMasterInfo;
import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;


/**
 * <p>Title: �鿴���չ�̵İ�����ʷ</p>
 * <p>Description: �����ṩ�ķ���Ϊ�C�ͻ�����ʹ��</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class TechnicsIterationsHistoryUtil
{

    public TechnicsIterationsHistoryUtil()
    {
    }


    /**
     * ����ݿͻ���ͷ���ı�Ҫ��ʾ��Ϣ(���տ��ı�ź�����)
     * @param Bso ���տ���BsoID
     * return ���տ��ı�ź�����
     */
    public static String getVersionHead(String Bso)
    {

        String id = Bso.trim();
        String technicsNum;
        String technicsName;
        BaseValueIfc obj;
        String rStr;
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper.
                                      getService("PersistService");
            obj = pService.refreshInfo(id);
        }
        catch (Exception sle)
        {
            sle.printStackTrace();
            obj = null;
        }
        if (obj != null)
        {
            if (obj instanceof QMTechnicsIfc)
            {
                technicsNum = ((QMTechnicsIfc) obj).getTechnicsNumber();
                technicsName = ((QMTechnicsIfc) obj).getTechnicsName();
                rStr = "���չ�̱�ţ�" + technicsNum + " ---------" + " ���չ�����ƣ�" +
                       technicsName;
                return rStr;
            }
            else
            {
                technicsNum = ((QMTechnicsMasterInfo) obj).getTechnicsNumber();
                technicsName = ((QMTechnicsMasterInfo) obj).getTechnicsName();
                String[] vv = new String[2];
                rStr = "���չ�̱�ţ�" + technicsNum + " ---------" + " ���չ�����ƣ�" +
                       technicsName;
                return rStr;
            }
            //String iconUrl=pb.getIconName("StandardIcon");
            //vv.addElement(id);
        }
        else
        {
            rStr = "���չ�̱�ţ� ---------" + " ���չ�����ƣ� ";
            return rStr;
        }
    }


    /**
     * ��øö���İ�����ʷ����ļ���(���е�С�汾�ļ���)
     * @param BsoID ���տ������BsoID
     * @return Collection �汾��ʷ����ļ���
     */
    public static Collection getVersionsCollection(String BsoID)
    {
        QMTechnicsMasterIfc myMasterInfo;
        Collection myCollection = new Vector();
        try
        {
            PersistService service = (PersistService) EJBServiceHelper.
                                     getService("PersistService");
            myMasterInfo = (QMTechnicsMasterIfc) service.refreshInfo(BsoID);

            VersionControlService service1 = (VersionControlService)
                                             EJBServiceHelper.getService(
                    "VersionControlService");
            myCollection = service1.allIterationsOf((MasteredIfc) myMasterInfo);
        }
        catch (Exception sle)
        {
            sle.printStackTrace();
        }
        return myCollection;
    }


    /**
     * ��øö���İ�����ʷ����ļ��ϣ��õ�����
     * �ݿͻ�����ʾ��������ݵļ���
     * @param BsoID ���տ������BsoID
     * @return Collection String����Ķ��󼯺ϣ���������Ϊ:
     *         �汾�����BsoID,�汾,����״̬,��Ŀ����,������,����ʱ��
     *
     */
    public static Collection getMyCollection(String BsoID)
    {

        Collection myCo = getVersionsCollection(BsoID);
        Object[] aa = new Object[myCo.size()];
        myCo.toArray(aa);

        QMTechnicsIfc myInfo;
        String versionID;
        String objectState;
        String projectName;
        String objectCreator;
        String createTime;
        Collection myCollection = new Vector();
        String versionBsoID;

        for (int i = 0; i < myCo.size(); i++)
        {
            //�õ�С�汾
            myInfo = (QMTechnicsIfc) (aa[i]);
            //�õ���Ҫ��ֵ

            versionID = myInfo.getVersionValue();
            if (myInfo.getLifeCycleState() != null)
            {
                objectState = (myInfo.getLifeCycleState()).getDisplay();
            }
            else
            {
                objectState = "";
                //��Ŀ��
            }
            ProjectIfc project = null;
            try
            {
                PersistService pService = (PersistService) EJBServiceHelper.
                                          getService("PersistService");
                if (((LifeCycleManagedIfc) myInfo).getProjectId() != null)
                {
                    project = (ProjectIfc) pService.refreshInfo(((
                            LifeCycleManagedIfc) myInfo).getProjectId());

                }
                objectCreator = CappServiceHelper.getUserNameByID(myInfo.
                        getIterationCreator());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                objectCreator = "";
            }
            if (project != null)
            {
                projectName = CappServiceHelper.getIdentity(project);
            }
            else
            {
                projectName = "";
            }
            createTime = (myInfo.getModifyTime()).toString();
            versionBsoID = myInfo.getBsoID();
            //��������
            String[] myVersionArray1 =
                    {versionBsoID, versionID, objectState,
                    projectName, objectCreator, createTime};
            //�ӵ�������
            myCollection.add(myVersionArray1);
        }
        return myCollection;
    }
    //add by wangh on 20080415
    public static Collection getCurrentCollection(String BsoID)
    {

        Collection myCo = getCurrentVersionsCollection(BsoID);
        Object[] aa = new Object[myCo.size()];
        myCo.toArray(aa);

        QMTechnicsIfc myInfo;
        String versionID;
        String objectState;
        String projectName;
        String objectCreator;
        String createTime;
        String modifyTime;//liunan 2011-10-09 ����ʱ��
        Collection myCollection = new Vector();
        String versionBsoID;

        for (int i = 0; i < myCo.size(); i++)
        {
            //�õ�С�汾
            myInfo = (QMTechnicsIfc) (aa[i]);
            //�õ���Ҫ��ֵ

            versionID = myInfo.getVersionValue();
            if (myInfo.getLifeCycleState() != null)
            {
                objectState = (myInfo.getLifeCycleState()).getDisplay();
            }
            else
            {
                objectState = "";
                //��Ŀ��
            }
            ProjectIfc project = null;
            try
            {
                PersistService pService = (PersistService) EJBServiceHelper.
                                          getService("PersistService");
                if (((LifeCycleManagedIfc) myInfo).getProjectId() != null)
                {
                    project = (ProjectIfc) pService.refreshInfo(((
                            LifeCycleManagedIfc) myInfo).getProjectId());

                }
                objectCreator = CappServiceHelper.getUserNameByID(myInfo.
                        getIterationCreator());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                objectCreator = "";
            }
            if (project != null)
            {
                projectName = CappServiceHelper.getIdentity(project);
            }
            else
            {
                projectName = "";
            }
            //CCBegin by liunan 2011-10-09 ����ʱ��
            createTime = (myInfo.getCreateTime()).toString();
            modifyTime = (myInfo.getModifyTime()).toString();
            //CCEnd by liunan 2011-10-09
            versionBsoID = myInfo.getBsoID();
            //��������
            String[] myVersionArray1 =
                    {versionBsoID, versionID, objectState,
                    //projectName, objectCreator, createTime};
                    projectName, objectCreator, createTime, modifyTime};//liunan 2011-10-09 ����ʱ��
            //�ӵ�������
            myCollection.add(myVersionArray1);
        }
        return myCollection;
    }
    
    public static Collection getCurrentVersionsCollection(String BsoID) {
    	QMTechnicsIfc myInfo;
        Collection myCollection = new Vector();
        try {
            PersistService service = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            myInfo = (QMTechnicsIfc)service
                    .refreshInfo(BsoID);
            VersionControlService service1 = (VersionControlService) EJBServiceHelper
                    .getService("VersionControlService");

            myCollection = service1.getAllPredecessor((IteratedIfc)myInfo);
        } catch (Exception sle) {
            sle.printStackTrace();
        }

        return myCollection;
    }
    //add end

}