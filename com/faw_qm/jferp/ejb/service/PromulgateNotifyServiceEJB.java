/**
 * ���ɳ���PromulgateNotifyServiceEJB.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���������ɶ���������  liuzhicheng 2012-01-01
 * SS2 ���˵��ֵ������� liuzhicheng 2011-08-01 
 */
package com.faw_qm.jferp.ejb.service;

import org.apache.log4j.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import com.faw_qm.change.ejb.service.ChangeService;
import com.faw_qm.change.model.ChangeRequestIfc;
//CCBegin SS2
//import com.faw_qm.change.model.QMChangeOrderIfc;
import com.faw_qm.change.model.QMChangeNoticeIfc;
//CCEnd SS2
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.model.StreamDataInfo;
import com.faw_qm.content.util.StreamUtil;
import com.faw_qm.jferp.model.FileNameLinkInfo;
import com.faw_qm.jferp.model.PromulgateDocLinkInfo;
import com.faw_qm.jferp.model.PromulgateNotifyIfc;
import com.faw_qm.jferp.model.PromulgateNotifyInfo;
import com.faw_qm.jferp.model.PromulgatePartLinkInfo;
import com.faw_qm.jferp.model.PromulgateProductLinkInfo;
import com.faw_qm.jferp.util.Messages;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
//CCBegin SS1
import com.faw_qm.lifecycle.util.LifeCycleState;
//CCEnd SS1
import com.faw_qm.part.ejb.enterpriseService.EnterprisePartService;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.pcfg.family.model.GenericPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.jferp.model.FileNameLinkIfc;

/**
 * <p>Title: ����֪ͨ����</p>
 * <p>Description: ����֪ͨ����</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class PromulgateNotifyServiceEJB extends BaseServiceImp
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(PromulgateNotifyServiceEJB.class);

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public PromulgateNotifyServiceEJB()
    {
    }

    /**
     * getServiceName
     *
     * @return String
     */
    public String getServiceName()
    {
        return "PromulgateNotifyService";
    }

    /**
     * ��������֪ͨ
     * @param newinfo PromulgateNotifyInfo
     * @param Products ArrayList
     * @throws QMException
     * @return PromulgateNotifyIfc
     */
    public PromulgateNotifyIfc createPromulgateNotify(
            PromulgateNotifyInfo newinfo, ArrayList products, ArrayList docs,boolean flag)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("createPromulgateNotify(PromulgateNotifyInfo, ArrayList, ArrayList,boolean) - start"); //$NON-NLS-1$
        }
        SessionService sService = (SessionService) EJBServiceHelper
                .getService("SessionService");
        FolderService fService = (FolderService) EJBServiceHelper
                .getService("FolderService");
        LifeCycleService lService = (LifeCycleService) EJBServiceHelper
                .getService("LifeCycleService");
        PersistService pService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        UserIfc user = (UserIfc) sService.getCurUserInfo();
        //������0925ȥ��
        //newinfo.setDomain(DomainHelper.getDomainID(DomainHelper.SYSTEM_DOMAIN));
        newinfo.setCreator(user.getBsoID());
        String lcName = (String) RemoteProperty
                .getProperty("promulgateNotifyDefaultLC");
        LifeCycleTemplateInfo lInfo = (LifeCycleTemplateInfo) lService
                .getLifeCycleTemplate(lcName);
        if(lInfo == null)
        {
            throw new QMException("û���ҵ�����֪ͨ��Ĭ����������");
        }
        newinfo.setLifeCycleTemplate(lInfo.getBsoID());
        fService.assignFolder(newinfo, (String) RemoteProperty
                .getProperty("promulgateNotifyDefaultFolder"));
        try
        {
            newinfo = (PromulgateNotifyInfo) pService.saveValueInfo(newinfo);
            int length = products.size();
            PromulgateProductLinkInfo ProductLink;
            for (int i = 0; i < length; i++)
            {
                ProductLink = new PromulgateProductLinkInfo();
                String product = (String) ((ArrayList) products).get(i);
                ProductLink.setLeftBsoID(newinfo.getBsoID());
                ProductLink.setRightBsoID(product);
                pService.saveValueInfo(ProductLink);
            }
            length = docs.size();
            PromulgateDocLinkInfo docLink;
            for (int i = 0; i < length; i++)
            {
                docLink = new PromulgateDocLinkInfo();
                String doc = (String) ((ArrayList) docs).get(i);
                docLink.setLeftBsoID(newinfo.getBsoID());
                docLink.setRightBsoID(doc);
                pService.saveValueInfo(docLink);
            }
            //��ʼ��ȡ����
            this.obtainPartData(newinfo, products, lInfo ,flag);
        }
        catch (QMException e)
        {
            logger
                    .error(
                            "createPromulgateNotify(PromulgateNotifyInfo, ArrayList, ArrayList,boolean)", e); //$NON-NLS-1$
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("createPromulgateNotify(PromulgateNotifyInfo, ArrayList, ArrayList,boolean) - end"); //$NON-NLS-1$
        }
        return newinfo;
    }
    /**
     * ��������֪ͨ
     * @param newinfo PromulgateNotifyInfo
     * @param Products ArrayList
     * @throws QMException
     * @return PromulgateNotifyIfc
     */
    public PromulgateNotifyIfc createPromulgateNotify(
            PromulgateNotifyInfo newinfo, ArrayList products, ArrayList docs)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("createPromulgateNotify(PromulgateNotifyInfo, ArrayList, ArrayList) - start"); //$NON-NLS-1$
        }
        newinfo=(PromulgateNotifyInfo)this.createPromulgateNotify(newinfo, products, docs ,true);
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("createPromulgateNotify(PromulgateNotifyInfo, ArrayList, ArrayList) - end"); //$NON-NLS-1$
        }
        return newinfo;
    }

    /**
     * ��ȡ����
     * @param Products ArrayList
     */
    private void obtainPartData(PromulgateNotifyInfo proinfo,
            ArrayList Products, LifeCycleTemplateInfo lInfo,boolean obtionFlag) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("obtainPartData(PromulgateNotifyInfo, ArrayList, LifeCycleTemplateInfo,boolean) - start"); //$NON-NLS-1$
        }
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            //�������г��λ�õ�part
            ArrayList firstList = this.getFirstObtionParts(Products);
            //���ݲ��ñ�ʶ������ȡ
            //��ȡ״̬����
            String flag = proinfo.getPromulgateNotifyFlag();
            ///����ת��Ϊ״̬��ʾ
            //CCBegin SS1
            LifeCycleState flagstate = LifeCycleState.toLifeCycleState(flag);
            //CCEnd SS1
            if(flagstate != null)
            {
                //���ι��˵�part����
                String routeIBA = RemoteProperty.getProperty("obtionflag",
                "��");
                ArrayList obtainList=new ArrayList();
                //20071128 
                //ɢ����erp�״ε������ݣ�Ҫ�󲻸����������������״̬���ˣ���Ҫ�ǡ�������״̬�����ҲҪ���롢
                //������������ӱ��������Ƿ����״η�������ʱ������������״̬���ˡ�
                //���״�����¼����ɺ���ʵʩ��Ա���ֶ�obtionflag�ֶθ�Ϊ1��
                if(routeIBA.trim().equals("0")||!obtionFlag){
                	obtainList =firstList;
                }
                else{
                  obtainList=this.getsecondObtionParts(flagstate,firstList);
                }
                //�����㲿��������
                for (int i = 0; i < obtainList.size(); i++)
                {
                    //��������
                    PromulgatePartLinkInfo link = new PromulgatePartLinkInfo();
                    link.setLeftBsoID(proinfo.getBsoID());
                    link.setRightBsoID(((QMPartInfo) obtainList.get(i))
                            .getBsoID());
                    pService.saveValueInfo(link);
                }
            }
        }
        catch (QMException ex)
        {
            logger
                    .error(
                            "obtainPartData(PromulgateNotifyInfo, ArrayList, LifeCycleTemplateInfo,boolean)", ex); //$NON-NLS-1$
            throw ex;
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("obtainPartData(PromulgateNotifyInfo, ArrayList, LifeCycleTemplateInfo,boolean) - end"); //$NON-NLS-1$
        }
    }

    /**
     * ��ó�����ȡ�ļ���
     * @param Products ArrayList
     * @throws QMException
     * @return ArrayList
     */
    private ArrayList getFirstObtionParts(ArrayList Products)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getFirstObtionParts(ArrayList) - start"); //$NON-NLS-1$
        }
        ArrayList firstList = new ArrayList();
        try
        {
            EnterprisePartService epartService = (EnterprisePartService) EJBServiceHelper
                    .getService("EnterprisePartService");
            StandardPartService partservice = (StandardPartService) EJBServiceHelper
                    .getService("StandardPartService");
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            PartConfigSpecIfc config = partservice.findPartConfigSpecIfc();
            for (int i = 0; i < Products.size(); i++)
            {
                QMPartMasterIfc master = (QMPartMasterInfo) pService
                        .refreshInfo((String) Products.get(i));
                //����master�Լ��������ó��ι��˵�part����
                QMPartIfc[] parts = epartService.getAllSubPartsByConfigSpec(
                        master, config);
                for (int j = 0; j < parts.length; j++)
                {
                    //���˹��岿��
                    if((QMPartIfc) parts[j] instanceof GenericPartIfc)
                    {
                        continue;
                    }
                    firstList.add(parts[j]);
                }
            }
        }
        catch (QMException e)
        {
            logger.error("getFirstObtionParts(ArrayList)", e); //$NON-NLS-1$
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getFirstObtionParts(ArrayList) - end" + firstList); //$NON-NLS-1$
        }
        return firstList;
    }

    //CCBegin SS1
    /**
     * ������ȡ
     * @param states Vector
     * @param flagstate State
     * @param firstList ArrayList
     * @throws QMException
     */
    private ArrayList getsecondObtionParts(LifeCycleState flagstate, ArrayList firstList)//CCEnd SS1
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getsecondObtionParts(State, ArrayList) - start"); //$NON-NLS-1$
        }
        ArrayList secondList = new ArrayList();
        //��������ļ������õ�״̬����
        ArrayList canprostate = this.getAllPromulgateStates();
        if(!canprostate.contains(flagstate.toString()))
        {
            throw new QMException("û�з��ֿ��Է�����״̬");
        }
        //��ò��ñ�ʶ�ڼ����е�λ��
        int loca = canprostate.indexOf(flagstate.toString());
        //���λ�ȡ������״̬���Ϲ���
        //��ѭ��part����
        for (int k = 0; k < firstList.size(); k++)
        {
            QMPartInfo part = (QMPartInfo) firstList.get(k);
            //ѭ��״̬����
            for (int m = 0; m < loca; m++)
            {
                if(part.getLifeCycleState().toString().equals(
                        canprostate.get(m)))
                {
                    secondList.add(part);
                }
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getsecondObtionParts(State, ArrayList) - end"); //$NON-NLS-1$
        }
        return secondList;
    }

    /**
     * ������е����õĿɷ�����״̬����
     * @return ArrayList
     */
    private ArrayList getAllPromulgateStates()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllPromulgateStates() - start"); //$NON-NLS-1$
        }
        ArrayList list = new ArrayList();
        String s = (String) RemoteProperty.getProperty("promulgateStates");
        //���������ַ���
        if(s != null)
        {
            StringTokenizer st = new StringTokenizer(s, ";");
            while (st.hasMoreTokens())
            {
                list.add(st.nextToken());
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllPromulgateStates() - end"); //$NON-NLS-1$
        }
        return list;
    }

    /**
     * ���ݱ�Ż�ù�������
     * @param num String
     * @throws QMException
     * @return Vector
     */
    public Vector searchPartByProNumber(String num) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("searchPartByProNumber(String) - start"); //$NON-NLS-1$
        }
        Vector vecPart = new Vector();
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query = new QMQuery("QMPart", "PromulgateNotify");
            QueryCondition qc1 = new QueryCondition("promulgateNotifyNumber",
                    "=", num);
            query.addCondition(1, qc1);
            query.appendBso("PromulgatePartLinkInfo", false);
            QueryCondition qc2 = new QueryCondition("bsoID", "=", "rightBsoID");
            query.addCondition(0, 2, qc2);
            QueryCondition qc3 = new QueryCondition("bsoID", "=", "leftBsoID");
            query.addCondition(1, 2, qc3);
            //���ò���֪ͨ���ɼ�
            query.setVisiableResult(1);
            query.setChildQuery(false);
            Collection parts = pService.findValueInfo(query);
            Iterator ite = parts.iterator();
            while (ite.hasNext())
            {
                PromulgateNotifyInfo info = (PromulgateNotifyInfo) ite.next();
                vecPart.add(info);
            }
        }
        catch (Exception e)
        {
            logger.error("searchPartByProNumber(String)", e); //$NON-NLS-1$
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("searchPartByProNumber(String) - end"); //$NON-NLS-1$
        }
        return vecPart;
    }

    /**
     * ���²���֪ͨ
     *
     * @param updateBsoid String
     * @param aPartList ArrayList
     */
    public void updatePromulgateNotify(String updateBsoid,
            ArrayList productList, ArrayList docList, ArrayList partList)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("updatePromulgateNotify(String, ArrayList, ArrayList, ArrayList) - start"); //$NON-NLS-1$
        }
        PromulgateNotifyInfo curInfo = null;
        try
        {
            //��������������Ϣ�Ѿ��������
            //��ʼ�����ӵĲ�Ʒ�Լ��ĵ���Ϣ
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            curInfo = (PromulgateNotifyInfo) pService.refreshInfo(updateBsoid);
            if(curInfo.getHasPromulgate().trim().equals("1"))
            {
                //�Ѿ���������,���������
                throw new QMException(Messages.getString("Util.46"));
            }
            LifeCycleService lService = (LifeCycleService) EJBServiceHelper
                    .getService("LifeCycleService");
            LifeCycleTemplateInfo lInfo = (LifeCycleTemplateInfo) pService
                    .refreshInfo(curInfo.getLifeCycleTemplate());
            Vector states = lService.findStates(lInfo);
            //ͨ������ó���������ļ���Ϊ���еļ��ϣ������������ͻ�Խ��Խ��
            //�ĵ��Ĵ���취����ͨ��ֻҪȫ�������Ϳ��Խ��
            //��������Ʒ�Ĵ������
            //1 �¼ӵ� ����
            //2 ������ ������
            //3 ɾ���� ��ȥ�����Ժ�Ĺ�ϵ��ֻ��ɾ��productlink
            //Ҫ���ֳ��������Ҫ�������ǰ�Ĳ�Ʒ���ϣ������Ƚ�
            //���Ȼ��ԭ�еĹ�����Ʒ
            Vector vec = (Vector) this.getProductsByProID(updateBsoid);
            //�����Ʒ����
            Vector cur = new Vector();
            ArrayList obtainList = new ArrayList(1);
            for (int i = 0; i < vec.size(); i++)
            {
                cur.add(((QMPartMasterInfo) vec.get(i)).getBsoID());
            }
            //��ʼ�ж�
            for (int j = 0; j < productList.size(); j++)
            {
                //˵��ԭ�����ڣ������2
                if(cur.contains(productList.get(j)))
                {
                    //�����Ƴ�����ôʣ�µ�curʣ�µĲ�����û�иĶ��Ĳ���
                    cur.remove(productList.get(j));
                }
                //ԭ��û�еģ����¼ӵ�1
                else
                {
                
                    PromulgateProductLinkInfo ProductLink = new PromulgateProductLinkInfo();
                    String product = (String) ((ArrayList) productList).get(j);
                    ProductLink.setLeftBsoID(updateBsoid);
                    ProductLink.setRightBsoID(product);
                    pService.saveValueInfo(ProductLink);
                    ArrayList temp=new ArrayList();
                    
                    //��ȡ�µ��������
                    //20071203 zhangq add begin
//                    obtainList = this.obtionPartWhenUpdate(curInfo, product,
//                            states);
                    temp = this.obtionPartWhenUpdate(curInfo, product,partList,
                            states);
                    //20071203 zhangq add end
                    obtainList.addAll(temp);
                }
            }
            //��ʼ�ڶ���ѭ����productListû�еĶ�cur�еľ���ɾ����
            for (int k = 0; k < cur.size(); k++)
            {
                //���ڵĲ�������ǰ�ģ�����ɾ����
                if(!productList.contains(cur.get(k)))
                {
                    this.deleteProductLink(updateBsoid, (String) cur.get(k));
                }
            }
            //���Ի�õ�cur
            //��ʼ�����ĵ�
            Vector olddoc = (Vector) this.getDocLinkByProid(updateBsoid);
            for (int i = 0; i < olddoc.size(); i++)
            {
                pService.deleteValueInfo((PromulgateDocLinkInfo) olddoc.get(i));
            }
            //����
            for (int j = 0; j < docList.size(); j++)
            {
                PromulgateDocLinkInfo docLink = new PromulgateDocLinkInfo();
                String doc = (String) ((ArrayList) docList).get(j);
                docLink.setLeftBsoID(updateBsoid);
                docLink.setRightBsoID(doc);
                pService.saveValueInfo(docLink);
            }
            if(partList != null)
            {
                this.updatePartLink(partList, updateBsoid, obtainList, curInfo
                        .getPromulgateNotifyFlag());
            }
        }
        catch (QMException e)
        {
            logger
                    .error(
                            "updatePromulgateNotify(String, ArrayList, ArrayList, ArrayList)", e); //$NON-NLS-1$
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("updatePromulgateNotify(String, ArrayList, ArrayList, ArrayList) - end"); //$NON-NLS-1$
        }
        return;
    }

    /**
     * �ֶ�����partlink
     * @param parts ArrayList
     * @param proid String
     */
    private void updatePartLink(ArrayList parts, String proid,
            ArrayList obtainList, String noflag) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("updatePartLink(ArrayList, String) - start"); //$NON-NLS-1$
        }
        //�������Ĵ���������productlink
        //1 �¼ӵ� ����
        //2 ������ ������
        //3 ɾ���� ��ȥ�����Ժ�Ĺ�ϵ��ֻ��ɾ��productlink
        //Ҫ���ֳ��������Ҫ�������ǰ�Ĳ�Ʒ���ϣ������Ƚ�
        //���Ȼ��ԭ�еĹ�����Ʒ
        Vector vec = null;
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            vec = (Vector) this.getPartsByProId(proid);
            // �����Ʒ����
            Vector cur = new Vector();
            Vector obtain = new Vector();
            for (int i = 0; i < vec.size(); i++)
            {
                cur.add(((QMPartInfo) vec.get(i)).getBsoID());
            }
            for (int i = 0; i < obtainList.size(); i++)
            {
                obtain.add(((QMPartInfo) obtainList.get(i)).getBsoID());
            }
            //��ʼ�ж�
            for (int j = 0; j < parts.size(); j++)
            {
                //˵��ԭ�����ڣ������2
                if(cur.contains(parts.get(j)))
                {
                    //�����Ƴ�����ôʣ�µ�curʣ�µĲ�����û�иĶ��Ĳ���
                    cur.remove(parts.get(j));
                }
                //ԭ��û�еģ����¼ӵ�1
                else
                {
                    //20071019�޸��ֹ����������ĸ��ݲ��ñ�ʶ�Ĺ���
                    QMPartIfc curInfo = (QMPartInfo) pService
                            .refreshInfo((String) ((ArrayList) parts).get(j));
                    //CCBegin SS1
                    LifeCycleState flagstate = LifeCycleState.toLifeCycleState(noflag);
                    //CCEnd SS1
                    //20080215 begin
                    //����״̬����
                    boolean flag1 = false;
                    //���ι��˵�part����
                    String routeIBA = RemoteProperty.getProperty("obtionflag",
                    "��");
                    //ɢ����erp�״ε������ݣ�Ҫ�󲻸����������������״̬���ˣ���Ҫ�ǡ�������״̬�����ҲҪ���롢
                    //������������ӱ��������Ƿ����״η�������ʱ������������״̬���ˡ�
                    //���״�����¼����ɺ���ʵʩ��Ա���ֶ�obtionflag�ֶθ�Ϊ1��
                    if(routeIBA.trim().equals("0")){
                    	flag1 =true;
                    }
                    else{
                    	flag1 = getsecondObtionParts(flagstate, curInfo);
                    }
                    //20080215 end
                    ///////////////////////////
                    if(flag1)
                    {
                        PromulgatePartLinkInfo link = new PromulgatePartLinkInfo();
                        String product = (String) ((ArrayList) parts).get(j);
                        link.setLeftBsoID(proid);
                        link.setRightBsoID(product);
                        pService.saveValueInfo(link);
                    }
                }
            }
            //��ʼ�ڶ���ѭ����productListû�еĶ�cur�еľ���ɾ����
            for (int k = 0; k < cur.size(); k++)
            {
                ///cailina�޸Ĺ�����Ʒ�������㲿������ʱ��������20071019
                //������������ӵĹ�����Ʒ���˳����������Ҳ�������ѭ��
                //����İ취�ǻ�������²�Ʒ����ӵ��������ArrayList obtainList
                logger.debug(obtain);
                //���ڵĲ�������ǰ�ģ�����ɾ����
                if(!parts.contains(cur.get(k)) && !obtain.contains(cur.get(k)))
                {
                    this.deletePartLink(proid, (String) cur.get(k));
                }
            }
        }
        catch (QMException ex)
        {
            throw ex;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("updatePartLink(ArrayList, String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * �ֶ����¸��ݲ��ñ�ʶ�������
     *������20071019���
     * @param states Vector
     * @param flagstate State
     * @param firstList ArrayList
     * @throws QMException
     */
    //CCBegin SS1
    private boolean getsecondObtionParts(LifeCycleState flagstate, QMPartIfc part)
            throws QMException
    //CCEnd SS1
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getsecondObtionParts(State, part) - start"); //$NON-NLS-1$
        }
        boolean flag = false;
        //��������ļ������õ�״̬����
        ArrayList canprostate = this.getAllPromulgateStates();
        if(!canprostate.contains(flagstate.toString()))
        {
            throw new QMException("û�з��ֿ��Է�����״̬");
        }
        //��ò��ñ�ʶ�ڼ����е�λ��
        int loca = canprostate.indexOf(flagstate.toString());
        //���λ�ȡ������״̬���Ϲ���
        //��ѭ��part����
        //ѭ��״̬����
        for (int m = 0; m < loca; m++)
        {
            if(part.getLifeCycleState().toString().equals(canprostate.get(m)))
            {
                flag = true;
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getsecondObtionParts(State, part) - end"); //$NON-NLS-1$
        }
        return flag;
    }

    /**
     * ɾ������
     * @param proid String
     * @param partid String
     */
    private void deletePartLink(String proid, String partid) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("deletePartLink(String, String) - start"); //$NON-NLS-1$
        }
        PersistService pService = null;
        try
        {
            pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query = new QMQuery("PromulgatePartLink");
            QueryCondition qc1 = new QueryCondition("leftBsoID", "=", proid);
            query.addCondition(qc1);
            query.addAND();
            QueryCondition qc2 = new QueryCondition("rightBsoID", "=", partid);
            query.addCondition(qc2);
            Collection pros = pService.findValueInfo(query);
            Iterator ite = pros.iterator();
            while (ite.hasNext())
            {
                PromulgatePartLinkInfo adlinfo = (PromulgatePartLinkInfo) ite
                        .next();
                pService.deleteValueInfo(adlinfo);
            }
        }
        catch (QMException ex)
        {
            throw ex;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("deletePartLink(String, String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * ����ʱ���¼Ӳ�Ʒ�ĸ���
     * @param curInfo PromulgateNotifyInfo
     * @param productid String
     * @param states Vector
     * @throws QMException
     */
    private ArrayList obtionPartWhenUpdate(PromulgateNotifyInfo curInfo,
            String productid,ArrayList partList, Vector states) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("obtionPartWhenUpdate(PromulgateNotifyInfo, String, Vector) - start"); //$NON-NLS-1$
        }
        ArrayList obtainList = new ArrayList(1);
        ;
        PersistService pService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        //������ȡ
        ArrayList product = new ArrayList(1);
        product.add(productid);
        try
        {
            ArrayList firstList = this.getFirstObtionParts(product);
            String flag = curInfo.getPromulgateNotifyFlag();
            //CCBegin SS1
            LifeCycleState flagstate = LifeCycleState.toLifeCycleState(flag);
            //CCEnd SS1
            //20080215 begin
            //���ι��˵�part����
            String routeIBA = RemoteProperty.getProperty("obtionflag",
            "��");
            //ɢ����erp�״ε������ݣ�Ҫ�󲻸����������������״̬���ˣ���Ҫ�ǡ�������״̬�����ҲҪ���롢
            //������������ӱ��������Ƿ����״η�������ʱ������������״̬���ˡ�
            //���״�����¼����ɺ���ʵʩ��Ա���ֶ�obtionflag�ֶθ�Ϊ1��
            if(routeIBA.trim().equals("0")){
            	obtainList =firstList;
            }
            else{
            	obtainList = this.getsecondObtionParts(flagstate, firstList);
            }
            //20080215 end
            String partBsoId=null;
        	//20080215 begin
        	//boolean isIn=false;
        	//20080215 end
            //�����㲿��������
            for (int i = 0; i < obtainList.size(); i++)
            {
            	//20080215 begin
            	boolean isIn=false;
            	//20080215 end
                for (int j = 0; j < partList.size(); j++) {
					partBsoId = (String) partList.get(j);
					if (partBsoId != null
							&& partBsoId
									.equals(((QMPartInfo) obtainList.get(i))
											.getBsoID())) {
						isIn = true;
						break;
					}
				}
				if (!isIn) {
					// ��������
					PromulgatePartLinkInfo link = new PromulgatePartLinkInfo();
					link.setLeftBsoID(curInfo.getBsoID());
					link.setRightBsoID(((QMPartInfo) obtainList.get(i))
							.getBsoID());
					pService.saveValueInfo(link);
				}

            }
        }
        catch (QMException ex)
        {
            logger
                    .error(
                            "obtionPartWhenUpdate(PromulgateNotifyInfo, String, Vector)", ex); //$NON-NLS-1$
            throw ex;
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("obtionPartWhenUpdate(PromulgateNotifyInfo, String, Vector) - end"); //$NON-NLS-1$
        }
        return obtainList;
    }

    /**
     * ���ݲ���id��ù����ĵ�
     * @param id String
     * @throws QMException
     * @return Collection
     */
    private Collection getDocLinkByProid(String id) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getDocLinkByProid(String) - start"); //$NON-NLS-1$
        }
        Collection docs = null;
        Collection res = new Vector();
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query = new QMQuery("PromulgateDocLink");
            QueryCondition qc1 = new QueryCondition("leftBsoID", "=", id);
            query.addCondition(qc1);
            docs = pService.findValueInfo(query);
            Iterator ite = docs.iterator();
            while (ite.hasNext())
            {
                res.add(ite.next());
            }
        }
        catch (QMException ex)
        {
            logger.error("getDocLinkByProid(String)", ex); //$NON-NLS-1$
            throw ex;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getDocLinkByProid(String) - end"); //$NON-NLS-1$
        }
        return res;
    }

    /**
     * ɾ��������Ʒ
     * @param promid String
     * @param productid String
     * @throws QueryException
     * @throws ServiceLocatorException
     * @throws QMException
     */
    private void deleteProductLink(String promid, String productid)
            throws QueryException, ServiceLocatorException, QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteProductLink(String, String) - start"); //$NON-NLS-1$
        }
        PersistService pService = (PersistService) EJBServiceHelper
                .getService("PersistService");
        QMQuery query = new QMQuery("PromulgateProductLink");
        QueryCondition qc1 = new QueryCondition("leftBsoID", "=", promid);
        query.addCondition(qc1);
        query.addAND();
        QueryCondition qc2 = new QueryCondition("rightBsoID", "=", productid);
        query.addCondition(qc2);
        Collection pros = pService.findValueInfo(query);
        Iterator ite = pros.iterator();
        while (ite.hasNext())
        {
            PromulgateProductLinkInfo adlinfo = (PromulgateProductLinkInfo) ite
                    .next();
            pService.deleteValueInfo(adlinfo);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteProductLink(String, String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * ���ݲ���id��ù�����Ʒ
     * @param bsoid String
     * @return Collection
     */
    public Collection getProductsByProID(String bsoid) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getProductsByProID(String) - start"); //$NON-NLS-1$
        }
        Vector vecPart = new Vector();
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query1 = new QMQuery("QMPartMaster");
            int j = query1.appendBso("PromulgateProductLink", false);
            QueryCondition codi = new QueryCondition("leftBsoID", "=", bsoid);
            query1.addCondition(j, codi);
            QueryCondition codi1 = new QueryCondition("rightBsoID", "bsoID");
            query1.addAND();
            query1.addCondition(j, 0, codi1);
            query1.setChildQuery(false);
            Collection products = pService.findValueInfo(query1);
            Iterator ite = products.iterator();
            while (ite.hasNext())
            {
                vecPart.add(ite.next());
            }
        }
        catch (Exception e)
        {
            logger.error("getProductsByProID(String)", e); //$NON-NLS-1$
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getProductsByProID(String) - end"); //$NON-NLS-1$
        }
        return vecPart;
    }

    /**
     * ���ݲ���id��ù����ĵ�
     * @param id String
     * @return Collection
     */
    public Collection getDocsByProId(String id) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getDocsByProId(String) - start"); //$NON-NLS-1$
        }
        Vector vecDoc = new Vector();
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query1 = new QMQuery("Doc");
            int j = query1.appendBso("PromulgateDocLink", false);
            QueryCondition codi = new QueryCondition("leftBsoID", "=", id);
            query1.addCondition(j, codi);
            QueryCondition codi1 = new QueryCondition("rightBsoID", "bsoID");
            query1.addAND();
            query1.addCondition(j, 0, codi1);
            query1.setChildQuery(false);
            Collection doc = pService.findValueInfo(query1);
            Iterator ite = doc.iterator();
            while (ite.hasNext())
            {
                vecDoc.add(ite.next());
            }
        }
        catch (Exception e)
        {
            logger.error("getDocsByProId(String)", e); //$NON-NLS-1$
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getDocsByProId(String) - end"); //$NON-NLS-1$
        }
        return vecDoc;
    }

    /**
     * ��������֪ͨ
     * @param name String
     * @param checkboxnName String
     * @param num String
     * @param checkboxnNum String
     * @param Flag String
     * @param checkboxFlag String
     * @param textcreator String
     * @param checkboxcreator String
     * @throws QMException
     * @return Collection
     */
    public Collection searchPromulgateNotify(String name, String checkboxnName,
            String num, String checkboxnNum, String Flag, String checkboxFlag,
            String textcreator, String checkboxcreator) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("searchPromulgateNotify(String, String, String, String, String, String, String, String) - start"); //$NON-NLS-1$
        }
        Collection vec = new Vector();
        //  ��ó־û�����
        PersistService ps = null;
        try
        {
            ps = (PersistService) EJBServiceHelper.getService("PersistService");
            //  �����µĲ��Ҷ���query
            QMQuery query1 = null;
            query1 = new QMQuery("PromulgateNotify");
            //  �����ĵ����Ʋ�ѯ
            query1 = getPromulgateNotifyInfoByName(query1, name, checkboxnName);
            //  �����ĵ���Ų�ѯ
            query1 = getPromulgateNotifyInfoByNum(query1, num, checkboxnNum);
            //  ���ݲ��ñ�ʶ��ѯ
            query1 = getPromulgateNotifyInfoByFlag(query1, Flag, checkboxFlag);
            //  ���ݴ����߲�ѯ
            query1 = getPromulgateNotifyInfoByCreator(query1, textcreator,
                    checkboxcreator);
            Collection col = ps.findValueInfo(query1, false);
            //��õ���
            Iterator iter = col.iterator();
            while (iter.hasNext())
            {
                vec.add(iter.next());
            }
        }
        catch (QMException e)
        {
            throw e;
        }
        //�жϼ����е�����
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("searchPromulgateNotify(String, String, String, String, String, String, String, String) - end"); //$NON-NLS-1$
        }
        return vec;
    }

    /**
     * ͨ����������������֪ͨ
     *
     * @param query1 QMQuery
     * @param textcreator String
     * @param checkboxcreator String
     * @return QMQuery
     */
    private QMQuery getPromulgateNotifyInfoByCreator(QMQuery query1,
            String textcreator, String checkboxcreator) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getPromulgateNotifyInfoByCreator(QMQuery, String, String) - start"); //$NON-NLS-1$
        }
        QMQuery query = query1;
        try
        {
            //  ����ĵ���Ų�Ϊ��
            if(textcreator != null && !textcreator.trim().equals(""))
            {
                if(query.getConditionCount() > 0)
                {
                    query.addAND();
                }
                int j = query.appendBso("User", false);
                QueryCondition qc1 = new QueryCondition("creator", "bsoID");
                query.addCondition(0, j, qc1);
                query.addAND();
                //  ����û���ѯ���ĵ��в���������ĵ����
                if(checkboxcreator != null
                        && checkboxcreator.trim().equals("false"))
                {
                    query.addLeftParentheses();
                    QueryCondition cond = new QueryCondition("usersName",
                            "like", getLikeSearchString(textcreator));
                    query.addCondition(j, cond);
                    QueryCondition cond2 = new QueryCondition("usersDesc",
                            "like", getLikeSearchString(textcreator));
                    query.addOR();
                    query.addCondition(j, cond2);
                    query.addRightParentheses();
                }
                else
                {
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
        }
        catch (QMException e)
        {
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getPromulgateNotifyInfoByCreator(QMQuery, String, String) - end"); //$NON-NLS-1$
        }
        return query;
    }

    /**
     * ͨ�����ñ�ʶ��������֪ͨ
     *
     * @param query1 QMQuery
     * @param Flag String
     * @param checkboxFlag String
     * @return QMQuery
     */
    private QMQuery getPromulgateNotifyInfoByFlag(QMQuery query1, String Flag,
            String checkboxFlag) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getPromulgateNotifyInfoByFlag(QMQuery, String, String) - start"); //$NON-NLS-1$
        }
        QMQuery query = query1;
        try
        {
            if(Flag != null && !Flag.trim().equals(""))
            {
                if(query.getConditionCount() > 0)
                {
                    query.addAND();
                    //  ����û���ѯ���ĵ��в���������ĵ����
                }
                if(checkboxFlag != null && checkboxFlag.trim().equals("false"))
                {
                    QueryCondition cond = new QueryCondition(
                            "promulgateNotifyFlag", "=", Flag);
                    query.addCondition(0, cond);
                }
                //  ����û����ѯ���ĵ����
                else
                {
                    QueryCondition cond = new QueryCondition(
                            "promulgateNotifyFlag", "<>", Flag);
                    query.addCondition(0, cond);
                }
            }
        }
        catch (QMException e)
        {
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getPromulgateNotifyInfoByFlag(QMQuery, String, String) - end"); //$NON-NLS-1$
        }
        return query;
    }

    /**
     * ͨ�������������֪ͨ
     *
     * @param query1 QMQuery
     * @param num String
     * @param checkboxnNum String
     * @return QMQuery
     */
    private QMQuery getPromulgateNotifyInfoByNum(QMQuery query1, String num,
            String checkboxnNum) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getPromulgateNotifyInfoByNum(QMQuery, String, String) - start"); //$NON-NLS-1$
        }
        QMQuery query = query1;
        try
        {
            //  ����ĵ���Ų�Ϊ��
            if(num != null && !num.trim().equals(""))
            {
                if(query1.getConditionCount() >= 1)
                {
                    query1.addAND();
                    //  ����û���ѯ���ĵ��в���������ĵ����
                }
                if(checkboxnNum != null && checkboxnNum.trim().equals("false"))
                {
                    QueryCondition cond = new QueryCondition(
                            "promulgateNotifyNumber", "like",
                            getLikeSearchString(num));
                    query.addCondition(cond);
                }
                //  ����û����ѯ���ĵ����
                else
                {
                    QueryCondition cond = new QueryCondition(
                            "promulgateNotifyNumber", "not like",
                            getLikeSearchString(num));
                    query.addCondition(cond);
                }
            }
        }
        catch (QMException e)
        {
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getPromulgateNotifyInfoByNum(QMQuery, String, String) - end"); //$NON-NLS-1$
        }
        return query;
    }

    /**
     * ͨ��������������֪ͨ
     * @param query QMQuery
     * @param name String
     * @param checkboxName String
     * @throws QMException
     * @return QMQuery
     */
    private QMQuery getPromulgateNotifyInfoByName(QMQuery query, String name,
            String checkboxName) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getPromulgateNotifyInfoByName(QMQuery, String, String) - start"); //$NON-NLS-1$
        }
        QMQuery qu = query;
        try
        {
            //  ������Ʋ�Ϊ�գ����в�ѯ
            if(name != null && !name.trim().equals(""))
            {
                if(qu.getConditionCount() >= 1)
                {
                    qu.addAND();
                    //  ����û���ѯ�в������������
                }
                if(checkboxName != null && checkboxName.trim().equals("false"))
                {
                    QueryCondition cond = new QueryCondition(
                            "promulgateNotifyName", "like",
                            getLikeSearchString(name));
                    qu.addCondition(cond);
                }
                //  ����û����ѯ���ĵ�����
                else
                {
                    QueryCondition cond = new QueryCondition(
                            "promulgateNotifyName", "not like",
                            getLikeSearchString(name));
                    qu.addCondition(cond);
                }
            }
        }
        catch (QMException e)
        {
            throw e;
        } //end catch
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getPromulgateNotifyInfoByName(QMQuery, String, String) - end"); //$NON-NLS-1$
        }
        return qu;
    }

    /**
     * ƥ���ַ���ѯ�������ַ���oldStr�е�"/*"ת����"*"��"*"ת����"%"��"%"������
     * �� "shf/*pdm%cax*"  ת���� "shf*pdm%cax%"
     * @param oldStr
     * @return ת�����ƥ���ַ���ѯ��
     */
    private String getLikeSearchString(String oldStr)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getLikeSearchString(String) - start"); //$NON-NLS-1$
        }
        if(oldStr == null || oldStr.trim().equals(""))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("getLikeSearchString(String) - end"); //$NON-NLS-1$
            }
            return oldStr;
        }
        char ac[] = oldStr.toCharArray();
        int i = ac.length;
        for (int j = 0; j < i; j++)
        {
            if(ac[j] == '*')
            {
                if(j > 0 && ac[j - 1] == '/')
                {
                    for (int k = j - 1; k < i - 1; k++)
                    {
                        ac[k] = ac[k + 1];
                    }
                    i--;
                    ac[i] = ' ';
                }
                else
                {
                    ac[j] = '%';
                }
            }
            if(ac[j] == '?')
            {
                ac[j] = '_';
            }
        }
        String resultStr = (new String(ac)).trim();
        if(logger.isDebugEnabled())
        {
            logger.debug("getLikeSearchString(String) - end"); //$NON-NLS-1$
        }
        return resultStr;
    }

    /**
     * ɾ������
     * @param id String
     * @throws QMException
     */
    public void deletePromulgateNotify(String id) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("deletePromulgateNotify(String) - start"); //$NON-NLS-1$
        }
        PersistService pService = null;
        try
        {
            pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            //�жϹ�����Ʒ
            QMQuery qmq = new QMQuery("PromulgateProductLink");
            QueryCondition qc = new QueryCondition("leftBsoID", "=", id);
            qmq.addCondition(qc);
            Collection co = null;
            co = pService.findValueInfo(qmq, false);
            Iterator ite = co.iterator();
            while (ite.hasNext())
            {
                PromulgateProductLinkInfo boli = (PromulgateProductLinkInfo) ite
                        .next();
                pService.deleteValueInfo(boli);
            }
            //�жϹ�������
            QMQuery qmq1 = new QMQuery("PromulgatePartLink");
            QueryCondition qc1 = new QueryCondition("leftBsoID", "=", id);
            qmq1.addCondition(qc1);
            Collection co1 = pService.findValueInfo(qmq1, false);
            Iterator ite1 = co1.iterator();
            while (ite1.hasNext())
            {
                PromulgatePartLinkInfo boli = (PromulgatePartLinkInfo) ite1
                        .next();
                pService.deleteValueInfo(boli);
            }
            //�жϹ����ĵ�
            QMQuery qmq2 = new QMQuery("PromulgateDocLink");
            QueryCondition qc2 = new QueryCondition("leftBsoID", "=", id);
            qmq2.addCondition(qc2);
            Collection co2 = pService.findValueInfo(qmq2, false);
            Iterator ite2 = co2.iterator();
            while (ite2.hasNext())
            {
                PromulgateDocLinkInfo boli = (PromulgateDocLinkInfo) ite2
                        .next();
                pService.deleteValueInfo(boli);
            }
            //ɾ�����ļ�������
            QMQuery qmq3 = new QMQuery("FileNameLink");
            QueryCondition qc3 = new QueryCondition("notice", "=", id);
            qmq3.addCondition(qc3);
            Collection co3 = pService.findValueInfo(qmq3, false);
            Iterator ite3 = co3.iterator();
            while (ite3.hasNext())
            {
                FileNameLinkIfc boli = (FileNameLinkIfc) ite3.next();
                pService.deleteValueInfo(boli);
            }
            pService.deleteBso(id);
        }
        catch (QMException ex2)
        {
            throw ex2;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("deletePromulgateNotify(String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * ��ù������
     * @param id String
     * @throws QMException
     * @return Vector
     */
    public Vector getPartsByProId(String id) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartsByProId(String) - start"); //$NON-NLS-1$
        }
        Vector vecPart = new Vector();
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query1 = new QMQuery("QMPart");
            int j = query1.appendBso("PromulgatePartLink", false);
            QueryCondition codi = new QueryCondition("leftBsoID", "=", id);
            query1.addCondition(j, codi);
            QueryCondition codi1 = new QueryCondition("rightBsoID", "bsoID");
            query1.addAND();
            query1.addCondition(j, 0, codi1);
            query1.setChildQuery(false);
            Collection products = pService.findValueInfo(query1);
            Iterator ite = products.iterator();
            while (ite.hasNext())
            {
                vecPart.add(ite.next());
            }
        }
        catch (Exception e)
        {
            logger.error("getPartsByProId(String)", e); //$NON-NLS-1$
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartsByProId(String) - end"); //$NON-NLS-1$
        }
        return vecPart;
    }

    /**
     * ��ù������
     * @param id String
     * @throws QMException
     * @return Vector
     */
    public Collection getPartsByProId(PromulgateNotifyIfc info)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartsByProId(PromulgateNotifyIfc) - start"); //$NON-NLS-1$
        }
        Collection vecPart = new ArrayList();
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query1 = new QMQuery("QMPart");
            int j = query1.appendBso("PromulgatePartLink", false);
            QueryCondition codi = new QueryCondition("leftBsoID", "=", info
                    .getBsoID());
            query1.addCondition(j, codi);
            QueryCondition codi1 = new QueryCondition("rightBsoID", "bsoID");
            query1.addAND();
            query1.addCondition(j, 0, codi1);
            query1.setChildQuery(false);
            vecPart = pService.findValueInfo(query1);
        }
        catch (Exception e)
        {
            logger.error("getPartsByProId(PromulgateNotifyIfc)", e); //$NON-NLS-1$
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartsByProId(PromulgateNotifyIfc) - end"); //$NON-NLS-1$
        }
        return vecPart;
    }

    /**
     * ����master��ʶ������
     * @return Collection
     */
    public ArrayList getAllPartsByMaster(String num, String name)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllPartsByMaster(String, String) - start"); //$NON-NLS-1$
        }
        ArrayList list = new ArrayList();
        try
        {
            StandardPartService spService = (StandardPartService) EJBServiceHelper
                    .getService("StandardPartService");
            Collection masters = (Collection) spService.getAllPartMasters(name,
                    num);
            PartConfigSpecIfc config = spService.findPartConfigSpecIfc();
            //���id
            if(masters != null && masters.size() > 0)
            {
                Iterator iter = masters.iterator();
                while (iter.hasNext())
                {
                    QMPartMasterIfc m = (QMPartMasterIfc) iter.next();
                    //���˹��岿��
                    if(!(m instanceof GenericPartIfc))
                    {
                        QMPartIfc part = getPartByConfigSpec(m, config);
                        list.add(part);
                    }
                }
            }
        }
        catch (QMException e)
        {
            logger.error("getAllPartsByMaster(String, String)", e); //$NON-NLS-1$
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllPartsByMaster(String, String) - end"); //$NON-NLS-1$
        }
        return list;
    }

    /**
     * ��ȡ�������ù淶���㲿����
     * @param partMasterIfc QMPartMasterIfc �㲿������Ϣ��
     * @param partConfigSpecIfc PartConfigSpecIfc ���ù淶��
     * @throws QMException
     * @return QMPartIfc
     */
    private QMPartIfc getPartByConfigSpec(QMPartMasterIfc partMasterIfc,
            PartConfigSpecIfc partConfigSpecIfc) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getPartByConfigSpec(QMPartMasterIfc, PartConfigSpecIfc) - start"); //$NON-NLS-1$
        }
        StandardPartService spService = (StandardPartService) EJBServiceHelper
                .getService("StandardPartService");
        Collection collection = new ArrayList();
        collection.add(partMasterIfc);
        Collection collection2 = spService.filteredIterationsOf(collection,
                partConfigSpecIfc);
        Iterator iterator = collection2.iterator();
        Object[] obj2 = null;
        while (iterator.hasNext())
        {
            Object obj1 = iterator.next();
            if(obj1 instanceof Object[])
            {
                obj2 = (Object[]) obj1;
            }
        }
        if(obj2 == null || obj2.length == 0)
        {
            if(logger.isDebugEnabled())
            {
                logger
                        .debug("getPartByConfigSpec(QMPartMasterIfc, PartConfigSpecIfc) - end"); //$NON-NLS-1$
            }
            return null;
        }
        if(!(obj2[0] instanceof QMPartIfc))
        {
            if(logger.isDebugEnabled())
            {
                logger
                        .debug("getPartByConfigSpec(QMPartMasterIfc, PartConfigSpecIfc) - end"); //$NON-NLS-1$
            }
            return null;
        }
        QMPartIfc returnQMPartIfc = (QMPartIfc) obj2[0];
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getPartByConfigSpec(QMPartMasterIfc, PartConfigSpecIfc) - end"); //$NON-NLS-1$
        }
        return returnQMPartIfc;
    }

    /**
     * �������ȡ���������
     * @return ArrayList
     */
    public ArrayList[] obtainDataForChange(BaseValueIfc changeRequest)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("obtainDataForChange(BaseValueIfc) - start"); //$NON-NLS-1$
        }
        ArrayList afterChangelist = new ArrayList();
        ArrayList beforeChangelist = new ArrayList();
        ArrayList[] returnlist = new ArrayList[2];
        //1 ͨ����������ñ����,ͨ���������ñ���������changerecord����,�Ӷ���ñ�����part����
        /**
         * ���ݸ��ļƻ���ø�������
         * @param cr ���ļƻ�
         * @return ��������

         */
        //   public Collection getChangeOrders(ChangeRequestMMark cr) throws
        //     ChangeException, QMException {
        //    return getChangeOrders(cr, true);
        //   }
        Collection orderlist;
        Collection record;
        Collection affect;
        try
        {
            ChangeService cService = (ChangeService) EJBServiceHelper
                    .getService("ChangeService");
            orderlist = this.getOrders(changeRequest);
            if(orderlist != null && orderlist.size() != 0)
            {
                /**
                 * ���ݱ������ø��ĺ�Ŀɱ������
                 * @param co �����
                 * @return ���ĺ�Ŀɱ������
                 */
                //  public Collection getChangeablesAfter(ChangeOrderMMark co) throws
                //      ChangeException, QMException {
                //    return getChangeablesAfter(co, true);
                //  }
                Iterator iter = orderlist.iterator();
                while (iter.hasNext())
                {
                    ///����ʵ��֤��!!!
                    //��������ṩ�ķ������ǰ�������˳�����
                    //���ｫ��ʹ�!��ǰ��������ݸ��Ĺ���!!
                	//CCBegin SS2
                	QMChangeNoticeIfc order = (QMChangeNoticeIfc) iter.next();
                	//CCEnd SS2
                    record = cService.getChangeablesAfter(order);
                    if(record != null & record.size() != 0)
                    {
                        Iterator riter = record.iterator();
                        while (riter.hasNext())
                        {
                            //2 ��������,ֻ��ȡpart,ȥ���ĵ��͹��岿������������
                            BaseValueIfc b = (BaseValueIfc) riter.next();
                            if((b instanceof QMPartIfc)
                                    && !(b instanceof GenericPartIfc))
                            {
                                //3 ���ݼ��Ϸ���recoedarratlist
                                afterChangelist.add(b);
                            }
                        }
                    }
                    /**4 ��ñ��ǰ�ƻ����������affectactivedata,
                     * �������񵥻�ø���ǰ�Ŀɱ������
                     * @param co ��������
                     * @return ����ǰ�Ŀɸ��Ķ���
                     */
                    //  public Collection getChangeablesBefore(ChangeOrderMMark co) throws
                    //     ChangeException, QMException {
                    //   return getChangeablesBefore(co, true);
                    // }
                    affect = cService.getChangeablesBefore(order);
                    if(affect != null & affect.size() != 0)
                    {
                        Iterator aiter = affect.iterator();
                        while (aiter.hasNext())
                        {
                            //2 ��������,ֻ��ȡpart,ȥ���ĵ��͹��岿������������
                            BaseValueIfc b = (BaseValueIfc) aiter.next();
                            //5 ����ֻ��ȡpart,ȥ���ĵ��͹��岿������������
                            if((b instanceof QMPartIfc)
                                    && !(b instanceof GenericPartIfc))
                            {
                                beforeChangelist.add(b);
                            }
                        }
                    }
                }
            }
            returnlist[1] = afterChangelist;
            returnlist[0] = beforeChangelist;
            if(logger.isDebugEnabled())
            {
                logger.debug("[0]=" + returnlist[0]); //$NON-NLS-1$
                logger.debug("[1]=" + returnlist[1]);
            }
        }
        catch (QMException ex)
        {
            logger.error("obtainDataForChange(BaseValueIfc)", ex); //$NON-NLS-1$
            throw ex;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("obtainDataForChange(BaseValueIfc) - end"); //$NON-NLS-1$
        }
        return returnlist;
    }

    /**
     * ���ݱ�������ñ����
     * @param changeRequest BaseValueIfc
     * @return Collection
     */
    private Collection getOrders(BaseValueIfc changeRequest) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getOrders(BaseValueIfc) - start"); //$NON-NLS-1$
        }
        Collection orderlist = null;
        try
        {
            ChangeService cService = (ChangeService) EJBServiceHelper
                    .getService("ChangeService");
            ChangeRequestIfc request = (ChangeRequestIfc) changeRequest;
            //CCBegin SS2
            orderlist = cService.getChangeNotices(request);
            //CCEnd SS2
        }
        catch (QMException ex)
        {
            throw ex;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getOrders(BaseValueIfc) - end"); //$NON-NLS-1$
        }
        return orderlist;
    }

    /**
     * ���ݷ����������ļ���
     * @param base BaseValueIfc
     * @throws QMException
     * @return String
     */
    public String getFileNameByNotice(BaseValueIfc base) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getFileNameByNotice(BaseValueIfc) - start"); //$NON-NLS-1$
        }
        PersistService pService = null;
        String name = "";
        try
        {
            pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query = new QMQuery("FileNameLink");
            QueryCondition qc1 = new QueryCondition("notice", "=", base
                    .getBsoID());
            query.addCondition(qc1);
            Collection pros = pService.findValueInfo(query);
            Iterator ite = pros.iterator();
            while (ite.hasNext())
            {
                FileNameLinkInfo link = (FileNameLinkInfo) ite.next();
                name = link.getFileName();
            }
        }
        catch (QMException ex)
        {
            throw ex;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getFileNameByNotice(BaseValueIfc) - end"); //$NON-NLS-1$
        }
        return name;
    }

    /**
     * Ϊ���������Ӹ���,����������
     * @param contentHolder ContentHolderIfc
     * @param file File
     * @param streamDataInfo StreamDataInfo
     * @throws QMException
     */
    public void setContentForChangeRequest(ContentHolderIfc contentHolder,
            File file, StreamDataInfo streamDataInfo) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setContentForChangeRequest(ContentHolderIfc, String, String) - start"); //$NON-NLS-1$
        }
        ContentService service = null;
        // ��֯data
        try
        {
            //��ȡ�ļ���Ϣ
            ApplicationDataInfo applicationData = new ApplicationDataInfo();
            applicationData.setFileName(file.getName());
            applicationData.setUploadPath(file.getAbsolutePath());
            applicationData.setFileSize(streamDataInfo.getDataContent().length);
            service = (ContentService) EJBServiceHelper
                    .getService("ContentService");
            //���ϴ�
            applicationData = (ApplicationDataInfo) service.uploadContent(
                    contentHolder, applicationData);
            String streamID = applicationData.getStreamDataID();
            //д������
            StreamUtil.writeData(streamID, streamDataInfo.getDataContent());
        }
        catch (QMException ex)
        {
            logger
                    .error(
                            "setContentForChangeRequest(ContentHolderIfc, String, String)", ex); //$NON-NLS-1$
            throw ex;
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("setContentForChangeRequest(ContentHolderIfc, String, String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * ɾ������������
     * @param partid String:�㲿����BsoID
     */
    public void deletePartLink(String partid) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("deletePartLink(String) - start"); //$NON-NLS-1$
        }
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query = new QMQuery("PromulgatePartLink");
            QueryCondition qc2 = new QueryCondition("rightBsoID", "=", partid);
            query.addCondition(qc2);
            Collection pros = pService.findValueInfo(query);
            Iterator ite = pros.iterator();
            PromulgatePartLinkInfo adlinfo;
            while (ite.hasNext())
            {
                adlinfo = (PromulgatePartLinkInfo) ite.next();
                pService.deleteValueInfo(adlinfo);
            }
        }
        catch (QMException ex)
        {
            throw ex;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("deletePartLink(String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * ɾ��������Ʒ��
     * @param productid String:�㲿��Master��BsoID
     * @throws QMException
     */
    public void deleteProductLink(String productid) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteProductLink(String) - start"); //$NON-NLS-1$
        }
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query = new QMQuery("PromulgateProductLink");
            QueryCondition qc = new QueryCondition("rightBsoID", "=", productid);
            query.addCondition(qc);
            Collection pros = pService.findValueInfo(query);
            Iterator ite = pros.iterator();
            PromulgateProductLinkInfo adlinfo;
            while (ite.hasNext())
            {
                adlinfo = (PromulgateProductLinkInfo) ite.next();
                pService.deleteValueInfo(adlinfo);
            }
        }
        catch (QMException e)
        {
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteProductLink(String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * ɾ�������ĵ���
     * @param productid String:�ĵ���BsoID
     * @throws QMException
     */
    public void deleteDocLink(String docID) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteDocLink(String) - start"); //$NON-NLS-1$
        }
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMQuery query = new QMQuery("PromulgateDocLink");
            QueryCondition qc = new QueryCondition("rightBsoID", "=", docID);
            query.addCondition(qc);
            Collection pros = pService.findValueInfo(query);
            Iterator ite = pros.iterator();
            PromulgateDocLinkInfo adlinfo;
            while (ite.hasNext())
            {
                adlinfo = (PromulgateDocLinkInfo) ite.next();
                pService.deleteValueInfo(adlinfo);
            }
        }
        catch (QMException e)
        {
            throw new QMException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteDocLink(String) - end"); //$NON-NLS-1$
        }
    }
}
