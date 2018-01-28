/**
 * 生成程序PromulgateNotifyServiceEJB.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 青气升级成都兼容问题  liuzhicheng 2012-01-01
 * SS2 变更说明值对象更改 liuzhicheng 2011-08-01 
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
 * <p>Title: 采用通知服务</p>
 * <p>Description: 采用通知服务</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
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
     * 创建采用通知
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
        //蔡丽娜0925去掉
        //newinfo.setDomain(DomainHelper.getDomainID(DomainHelper.SYSTEM_DOMAIN));
        newinfo.setCreator(user.getBsoID());
        String lcName = (String) RemoteProperty
                .getProperty("promulgateNotifyDefaultLC");
        LifeCycleTemplateInfo lInfo = (LifeCycleTemplateInfo) lService
                .getLifeCycleTemplate(lcName);
        if(lInfo == null)
        {
            throw new QMException("没有找到采用通知书默认生命周期");
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
            //开始获取数据
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
     * 创建采用通知
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
     * 提取数据
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
            //缓存所有初次获得的part
            ArrayList firstList = this.getFirstObtionParts(Products);
            //根据采用标识二次提取
            //获取状态集合
            String flag = proinfo.getPromulgateNotifyFlag();
            ///忘记转化为状态表示
            //CCBegin SS1
            LifeCycleState flagstate = LifeCycleState.toLifeCycleState(flag);
            //CCEnd SS1
            if(flagstate != null)
            {
                //二次过滤的part集合
                String routeIBA = RemoteProperty.getProperty("obtionflag",
                "１");
                ArrayList obtainList=new ArrayList();
                //20071128 
                //散热器erp首次导入数据，要求不根据零件的生命周期状态过滤，主要是“生产”状态的零件也要导入、
                //所以在这里添加变量控制是否是首次发布数据时根据生命周期状态过滤。
                //当首次数据录入完成后，由实施人员发字段obtionflag字段改为1；
                if(routeIBA.trim().equals("0")||!obtionFlag){
                	obtainList =firstList;
                }
                else{
                  obtainList=this.getsecondObtionParts(flagstate,firstList);
                }
                //建立零部件关联联
                for (int i = 0; i < obtainList.size(); i++)
                {
                    //建立关联
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
     * 获得初次提取的集合
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
                //根据master以及配置项获得初次过滤的part集合
                QMPartIfc[] parts = epartService.getAllSubPartsByConfigSpec(
                        master, config);
                for (int j = 0; j < parts.length; j++)
                {
                    //过滤广义部件
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
     * 二次提取
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
        //获得属性文件中配置的状态集合
        ArrayList canprostate = this.getAllPromulgateStates();
        if(!canprostate.contains(flagstate.toString()))
        {
            throw new QMException("没有发现可以发布的状态");
        }
        //获得采用标识在集合中的位置
        int loca = canprostate.indexOf(flagstate.toString());
        //二次获取，根据状态集合过滤
        //现循环part集合
        for (int k = 0; k < firstList.size(); k++)
        {
            QMPartInfo part = (QMPartInfo) firstList.get(k);
            //循环状态集合
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
     * 获得所有的配置的可发布的状态集合
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
        //操作解析字符串
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
     * 根据标号获得关联部件
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
            //设置采用通知不可见
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
     * 更新采用通知
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
            //在这里对象本身的信息已经处理完毕
            //开始处理附加的产品以及文档信息
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            curInfo = (PromulgateNotifyInfo) pService.refreshInfo(updateBsoid);
            if(curInfo.getHasPromulgate().trim().equals("1"))
            {
                //已经发布过的,不允许更新
                throw new QMException(Messages.getString("Util.46"));
            }
            LifeCycleService lService = (LifeCycleService) EJBServiceHelper
                    .getService("LifeCycleService");
            LifeCycleTemplateInfo lInfo = (LifeCycleTemplateInfo) pService
                    .refreshInfo(curInfo.getLifeCycleTemplate());
            Vector states = lService.findStates(lInfo);
            //通过输出得出传输过来的集合为所有的集合，如果不做处理就会越来越多
            //文档的处理办法很普通，只要全部操作就可以解决
            //而关联产品的处理包括
            //1 新加的 创建
            //2 不动的 不处理
            //3 删除的 不去处理以后的关系，只是删除productlink
            //要区分出这三类就要缓存更新前的产品集合，留作比较
            //首先获得原有的关联产品
            Vector vec = (Vector) this.getProductsByProID(updateBsoid);
            //缓存产品集合
            Vector cur = new Vector();
            ArrayList obtainList = new ArrayList(1);
            for (int i = 0; i < vec.size(); i++)
            {
                cur.add(((QMPartMasterInfo) vec.get(i)).getBsoID());
            }
            //开始判断
            for (int j = 0; j < productList.size(); j++)
            {
                //说明原来存在，是情况2
                if(cur.contains(productList.get(j)))
                {
                    //把它移出，那么剩下的cur剩下的不包括没有改动的部分
                    cur.remove(productList.get(j));
                }
                //原来没有的，是新加的1
                else
                {
                
                    PromulgateProductLinkInfo ProductLink = new PromulgateProductLinkInfo();
                    String product = (String) ((ArrayList) productList).get(j);
                    ProductLink.setLeftBsoID(updateBsoid);
                    ProductLink.setRightBsoID(product);
                    pService.saveValueInfo(ProductLink);
                    ArrayList temp=new ArrayList();
                    
                    //提取新的零件数据
                    //20071203 zhangq add begin
//                    obtainList = this.obtionPartWhenUpdate(curInfo, product,
//                            states);
                    temp = this.obtionPartWhenUpdate(curInfo, product,partList,
                            states);
                    //20071203 zhangq add end
                    obtainList.addAll(temp);
                }
            }
            //开始第二次循环，productList没有的而cur有的就是删除的
            for (int k = 0; k < cur.size(); k++)
            {
                //现在的不包含以前的，则是删除的
                if(!productList.contains(cur.get(k)))
                {
                    this.deleteProductLink(updateBsoid, (String) cur.get(k));
                }
            }
            //测试获得的cur
            //开始处理文档
            Vector olddoc = (Vector) this.getDocLinkByProid(updateBsoid);
            for (int i = 0; i < olddoc.size(); i++)
            {
                pService.deleteValueInfo((PromulgateDocLinkInfo) olddoc.get(i));
            }
            //创建
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
     * 手动更新partlink
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
        //这个问题的处理方法类似productlink
        //1 新加的 创建
        //2 不动的 不处理
        //3 删除的 不去处理以后的关系，只是删除productlink
        //要区分出这三类就要缓存更新前的产品集合，留作比较
        //首先获得原有的关联产品
        Vector vec = null;
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            vec = (Vector) this.getPartsByProId(proid);
            // 缓存产品集合
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
            //开始判断
            for (int j = 0; j < parts.size(); j++)
            {
                //说明原来存在，是情况2
                if(cur.contains(parts.get(j)))
                {
                    //把它移出，那么剩下的cur剩下的不包括没有改动的部分
                    cur.remove(parts.get(j));
                }
                //原来没有的，是新加的1
                else
                {
                    //20071019修改手工关联部件的根据采用标识的过滤
                    QMPartIfc curInfo = (QMPartInfo) pService
                            .refreshInfo((String) ((ArrayList) parts).get(j));
                    //CCBegin SS1
                    LifeCycleState flagstate = LifeCycleState.toLifeCycleState(noflag);
                    //CCEnd SS1
                    //20080215 begin
                    //根据状态过滤
                    boolean flag1 = false;
                    //二次过滤的part集合
                    String routeIBA = RemoteProperty.getProperty("obtionflag",
                    "１");
                    //散热器erp首次导入数据，要求不根据零件的生命周期状态过滤，主要是“生产”状态的零件也要导入、
                    //所以在这里添加变量控制是否是首次发布数据时根据生命周期状态过滤。
                    //当首次数据录入完成后，由实施人员发字段obtionflag字段改为1；
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
            //开始第二次循环，productList没有的而cur有的就是删除的
            for (int k = 0; k < cur.size(); k++)
            {
                ///cailina修改关联产品，关联零部件不随时更新问题20071019
                //问题在于新添加的关联产品过滤出的零件集合也参与这次循环
                //解决的办法是缓存根据新产品新添加的零件集合ArrayList obtainList
                logger.debug(obtain);
                //现在的不包含以前的，则是删除的
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
     * 手动更新根据采用标识过滤零件
     *蔡丽娜20071019添加
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
        //获得属性文件中配置的状态集合
        ArrayList canprostate = this.getAllPromulgateStates();
        if(!canprostate.contains(flagstate.toString()))
        {
            throw new QMException("没有发现可以发布的状态");
        }
        //获得采用标识在集合中的位置
        int loca = canprostate.indexOf(flagstate.toString());
        //二次获取，根据状态集合过滤
        //现循环part集合
        //循环状态集合
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
     * 删除链接
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
     * 更新时对新加产品的更新
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
        //初次提取
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
            //二次过滤的part集合
            String routeIBA = RemoteProperty.getProperty("obtionflag",
            "１");
            //散热器erp首次导入数据，要求不根据零件的生命周期状态过滤，主要是“生产”状态的零件也要导入、
            //所以在这里添加变量控制是否是首次发布数据时根据生命周期状态过滤。
            //当首次数据录入完成后，由实施人员发字段obtionflag字段改为1；
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
            //建立零部件关联联
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
					// 建立关联
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
     * 根据采用id获得关联文档
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
     * 删除关联产品
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
     * 根据采用id获得关联产品
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
     * 根据采用id获得关联文档
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
     * 搜索采用通知
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
        //  获得持久化服务
        PersistService ps = null;
        try
        {
            ps = (PersistService) EJBServiceHelper.getService("PersistService");
            //  创建新的查找对象query
            QMQuery query1 = null;
            query1 = new QMQuery("PromulgateNotify");
            //  根据文档名称查询
            query1 = getPromulgateNotifyInfoByName(query1, name, checkboxnName);
            //  根据文档编号查询
            query1 = getPromulgateNotifyInfoByNum(query1, num, checkboxnNum);
            //  根据采用标识查询
            query1 = getPromulgateNotifyInfoByFlag(query1, Flag, checkboxFlag);
            //  根据创建者查询
            query1 = getPromulgateNotifyInfoByCreator(query1, textcreator,
                    checkboxcreator);
            Collection col = ps.findValueInfo(query1, false);
            //获得迭代
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
        //判断集合中的内容
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("searchPromulgateNotify(String, String, String, String, String, String, String, String) - end"); //$NON-NLS-1$
        }
        return vec;
    }

    /**
     * 通过创建者搜索采用通知
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
            //  如果文档编号不为空
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
                //  如果用户查询的文档中不想包括此文档编号
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
     * 通过采用标识搜索采用通知
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
                    //  如果用户查询的文档中不想包括此文档编号
                }
                if(checkboxFlag != null && checkboxFlag.trim().equals("false"))
                {
                    QueryCondition cond = new QueryCondition(
                            "promulgateNotifyFlag", "=", Flag);
                    query.addCondition(0, cond);
                }
                //  如果用户想查询此文档编号
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
     * 通过编号搜索采用通知
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
            //  如果文档编号不为空
            if(num != null && !num.trim().equals(""))
            {
                if(query1.getConditionCount() >= 1)
                {
                    query1.addAND();
                    //  如果用户查询的文档中不想包括此文档编号
                }
                if(checkboxnNum != null && checkboxnNum.trim().equals("false"))
                {
                    QueryCondition cond = new QueryCondition(
                            "promulgateNotifyNumber", "like",
                            getLikeSearchString(num));
                    query.addCondition(cond);
                }
                //  如果用户想查询此文档编号
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
     * 通过名称搜索采用通知
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
            //  如果名称不为空，进行查询
            if(name != null && !name.trim().equals(""))
            {
                if(qu.getConditionCount() >= 1)
                {
                    qu.addAND();
                    //  如果用户查询中不想包括此名称
                }
                if(checkboxName != null && checkboxName.trim().equals("false"))
                {
                    QueryCondition cond = new QueryCondition(
                            "promulgateNotifyName", "like",
                            getLikeSearchString(name));
                    qu.addCondition(cond);
                }
                //  如果用户想查询此文档名称
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
     * 匹配字符查询处理。将字符串oldStr中的"/*"转化成"*"，"*"转化成"%"，"%"不处理。
     * 例 "shf/*pdm%cax*"  转化成 "shf*pdm%cax%"
     * @param oldStr
     * @return 转化后的匹配字符查询串
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
     * 删除采用
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
            //判断关联产品
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
            //判断关联部件
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
            //判断关联文档
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
            //删除与文件的链接
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
     * 获得关联零件
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
     * 获得关联零件
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
     * 根据master标识获得零件
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
            //获得id
            if(masters != null && masters.size() > 0)
            {
                Iterator iter = masters.iterator();
                while (iter.hasNext())
                {
                    QMPartMasterIfc m = (QMPartMasterIfc) iter.next();
                    //过滤广义部件
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
     * 获取符合配置规范的零部件。
     * @param partMasterIfc QMPartMasterIfc 零部件主信息。
     * @param partConfigSpecIfc PartConfigSpecIfc 配置规范。
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
     * 变更单提取变更后数据
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
        //1 通过变更请求获得变更单,通过变更单获得变更活动关联的changerecord关联,从而获得变更后的part数据
        /**
         * 根据更改计划获得更改任务单
         * @param cr 更改计划
         * @return 更改任务单

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
                 * 根据变更单获得更改后的可变更对象
                 * @param co 变更单
                 * @return 更改后的可变更对象
                 */
                //  public Collection getChangeablesAfter(ChangeOrderMMark co) throws
                //      ChangeException, QMException {
                //    return getChangeablesAfter(co, true);
                //  }
                Iterator iter = orderlist.iterator();
                while (iter.hasNext())
                {
                    ///根据实践证明!!!
                    //变更管理提供的方法变更前后的数据顺序错了
                    //这里将错就错!将前后零件数据更改过来!!
                	//CCBegin SS2
                	QMChangeNoticeIfc order = (QMChangeNoticeIfc) iter.next();
                	//CCEnd SS2
                    record = cService.getChangeablesAfter(order);
                    if(record != null & record.size() != 0)
                    {
                        Iterator riter = record.iterator();
                        while (riter.hasNext())
                        {
                            //2 过滤数据,只提取part,去掉文档和广义部件等其他数据
                            BaseValueIfc b = (BaseValueIfc) riter.next();
                            if((b instanceof QMPartIfc)
                                    && !(b instanceof GenericPartIfc))
                            {
                                //3 数据集合放在recoedarratlist
                                afterChangelist.add(b);
                            }
                        }
                    }
                    /**4 获得变更前计划的零件数据affectactivedata,
                     * 根据任务单获得更改前的可变更对象
                     * @param co 更改任务单
                     * @return 更改前的可更改对象
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
                            //2 过滤数据,只提取part,去掉文档和广义部件等其他数据
                            BaseValueIfc b = (BaseValueIfc) aiter.next();
                            //5 过滤只提取part,去掉文档和广义部件等其他数据
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
     * 根据变更请求获得变更单
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
     * 根据发布对象获得文件名
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
     * 为变更请求添加附件,供发布调用
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
        // 组织data
        try
        {
            //获取文件信息
            ApplicationDataInfo applicationData = new ApplicationDataInfo();
            applicationData.setFileName(file.getName());
            applicationData.setUploadPath(file.getAbsolutePath());
            applicationData.setFileSize(streamDataInfo.getDataContent().length);
            service = (ContentService) EJBServiceHelper
                    .getService("ContentService");
            //先上传
            applicationData = (ApplicationDataInfo) service.uploadContent(
                    contentHolder, applicationData);
            String streamID = applicationData.getStreamDataID();
            //写入数据
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
     * 删除关联部件。
     * @param partid String:零部件的BsoID
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
     * 删除关联产品。
     * @param productid String:零部件Master的BsoID
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
     * 删除关联文档。
     * @param productid String:文档的BsoID
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
