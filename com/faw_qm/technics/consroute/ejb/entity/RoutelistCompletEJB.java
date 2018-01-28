/**
 * 生成程序 RoutelistCompletEJB
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;

import javax.ejb.CreateException;

import com.faw_qm.enterprise.ejb.entity.ManagedEJB;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.technics.consroute.model.RoutelistCompletInfo;
import com.faw_qm.technics.consroute.util.*;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.codemanage.model.*;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import java.util.*;
import java.util.Vector;
//CCBegin by wanghonglian 2008-07-30 
//注释掉不使用的导入路径
//import java.util.Enumeration;
//CCEnd by wanghonglian 2008-07-30
import com.faw_qm.framework.service.*;
//CCBegin by wanghonglian 2008-07-30 
//注释掉不使用的导入路径
//import com.faw_qm.framework.exceptions.*;
//import com.faw_qm.enterprise.ejb.entity.RevisionControlledEJB;
//import com.faw_qm.doc.model.*;
//import com.faw_qm.affixattr.ejb.entity.*;
//CCEnd by wanghonglian 2008-07-30
import com.faw_qm.content.ejb.entity.*;
import com.faw_qm.content.ejb.service.*;
import com.faw_qm.content.model.DataFormatInfo;
import com.faw_qm.content.util.DataFormatFactory;
import com.faw_qm.unique.ejb.entity.*;



/**
 * 艺毕通知书
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw_qm </p>
 * @author 管春元
 * @version 1.0
 */
public  abstract class RoutelistCompletEJB extends ManagedEJB
{
    /**
     * 获得唯一性BsoName
     * @return String
     */
    public String getUniqueBsoName()
    {
        return "com.faw_qm.technics.consroute.ejb.entity.RoutelistComplet";
    }
    /**
        * 获得业务对象名。
        */
       public String getBsoName()
       {
           return "consRoutelistComplet";
       }

    /**
     * 获得加锁日期
     * @return Timestamp 加锁日期
     */
    public abstract Timestamp getDate();

    /**
     * 设置加锁日期
     * @param date 加锁日期
     */
    public abstract void setDate(Timestamp date);

    /**
     * 获得加锁注释
     * @return String 加锁注释
     */
    public abstract String getNote();


    /**
     * 设置加锁注释
     * @param note 加锁注释
     */
    public abstract void setNote(String note);


    /**
     * 获得加锁者
     * @return String 加锁者
     */
    public abstract String getLocker();


    /**
     * 设置加锁者
     * @param locker 加锁者
     */
    public abstract void setLocker(String locker);


    public abstract String getCompletNum();


     public abstract void setCompletNum(String num);


     public abstract String getCompletName();


     public abstract void setCompletName(String name);


     public abstract String getCompletType();


     public abstract void setCompletType(String type);


     public abstract String getCompletNote();


     public abstract void setCompletNote(String note);


     public abstract String getCompletState();

     public abstract void setCompletState(String state);


      public abstract String getDataFormatID();
      /**
         * 获得内容格式名称
         * @return 内容格式名称
         */
        public String getDataFormatName()
            throws QMException
        {
          DataFormatInfo dataFormat = getDataFormatInfo();
          if (dataFormat!=null)
            return dataFormat.getFormatName();
          else
            return null;
        }

        /**
         * 设置内容格式BsoID
         * @param dataFormatID 内容格式BsoID
         */
        public abstract void setDataFormatID(String dataFormatID);

        /**
         * 获得内容格式对象
         * @return 内容格式对象
         */
        public DataFormat getDataFormat()
            throws QMException
        {
          if (getDataFormatID()!=null)
          {
            PersistService service = (PersistService)EJBServiceHelper.getPersistService();
            return (DataFormat)service.refreshBso(getDataFormatID());
          }
          return null;
        }

        /**
         * 获得内容格式对象
         * @return 内容格式值对象
         */
        public DataFormatInfo getDataFormatInfo()
            throws QMException
        {
          if (getDataFormatID()!=null)
          {
            DataFormatInfo dataFormatInfo = null;
            //性能改进:从内容服务DataFormatFactory缓存中获取文件格式值对象信息
            String dataFormatName = DataFormatFactory.getFormatName(getDataFormatID());
            if(dataFormatName!=null)
               dataFormatInfo = DataFormatFactory.getFormatByName(dataFormatName);
            return dataFormatInfo;
          }
          return null;
        }


        /**
         * 获得所有内容项
         * @return 所有内容项
         */
        public Vector getContentVector()
            throws QMException
        {
          ContentService service = (ContentService)EJBServiceHelper.getService("ContentService");
          return service.getContents((ContentHolder)getBsoReference());
        }
        /**
           * 判断容器是否有内容
           * @param boolean 判断容器是否有内容
           */
          public boolean isHasContents()
              throws QMException,ServiceLocatorException
          {
            ContentService service = (ContentService)EJBServiceHelper.getService("ContentService");
            return service.isHasContent((ContentHolder)getBsoReference());
          }

          /**
           * 获得主要内容项
           * @return 主要内容项
           */
          public ContentItem getPrimary()
              throws QMException
          {
            ContentService service = (ContentService)EJBServiceHelper.getService("ContentService");
            return service.getPrimaryContent((FormatContentHolder)getBsoReference());
          }


    /**
     * 设置值对象
     * @exception com.faw_qm.framework.exceptions.QMException
     * @param info 值对象
     */
    public void setValueInfo(BaseValueIfc info)
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "enterprise Managed setValueInfo begin....enter is BaseValueIfc");
        }
        super.setValueInfo(info);
        RoutelistCompletInfo iInfo = (RoutelistCompletInfo) info;
        iInfo.setDate(getDate());
        iInfo.setLocker(getLocker());
        iInfo.setNote(getNote());
        iInfo.setCompletName(getCompletName());
        iInfo.setCompletNum(getCompletNum());
        iInfo.setCompletNote(getCompletNote());
        iInfo.setDataFormatID(getDataFormatID());
        iInfo.setDataFormatName(getDataFormatName());
        iInfo.setCompletType(RouteCompletType.toRouteCompletType(this.getCompletType()).getDisplay());
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        if(this.getCompletState() != null)
        {
           CodingIfc codInfo = (CodingIfc) pservice.refreshInfo(this.getCompletState());
           iInfo.setCompletState(codInfo.getCodeContent());
        }
        if (verbose)
        {
            System.out.println(
                    "ResourceManaged setValueInfo end....return is void");
        }
    } //end setValueInfo(BaseValueIfc)


    /**
     * 创建值对象
     * @param info 值对象
     * @exception javax.ejb.CreateException
     */
    public void createByValueInfo(BaseValueIfc info)
            throws CreateException
    {
        if (verbose)
        {
            System.out.println(
                    "ResourceManaged createByValueInfo begin....enter is BaseValueIfc");
        }
        super.createByValueInfo(info);
        RoutelistCompletInfo iInfo = (RoutelistCompletInfo) info;
        setNote(iInfo.getNote());
        setLocker(iInfo.getLocker());
        setDate(iInfo.getDate());
        this.setCompletName(iInfo.getCompletName());
        this.setCompletNote(iInfo.getCompletNote());
        this.setCompletNum(iInfo.getCompletNum());
        String value = RouteCompletType.getValue(iInfo.getCompletType());
        //System.out.println("dddddddddddddddddddddddddddddddddddd value= " + value);
        this.setCompletType(value);
        setDataFormatID(iInfo.getDataFormatID());
        String note = iInfo.getCompletState();
        try{
          CodingManageService codeService = (CodingManageService) EJBServiceHelper.
              getService("CodingManageService");
         Collection codes =  codeService.findCoding("RoutelistComplet","completState");
         Iterator i = codes.iterator();
         String codeID = null;
         while(i.hasNext())
         {
           CodingIfc code = (CodingIfc)i.next();
           if(code.getCodeContent().equals(note))
             codeID = code.getBsoID();
         }
         this.setCompletState(codeID);
        }catch(QMException ex)
        {
          ex.printStackTrace();
          throw new CreateException(ex.getClientMessage());
        }
        if (verbose)
        {
            System.out.println(
                    "ResourceManaged createByValueInfo end....return is void");
        }
    }


    /**
     * 更新值对象
     * @param info 值对象
     * @throws QMException
     */
    public void updateByValueInfo(BaseValueIfc info)
            throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "enterprise Managed updateByValueInfo begin....enter is BaseValueIfc");
        }
        super.updateByValueInfo(info);
        RoutelistCompletInfo iInfo = (RoutelistCompletInfo) info;
        setNote(iInfo.getNote());
        setLocker(iInfo.getLocker());
        setDate(iInfo.getDate());
        this.setCompletName(iInfo.getCompletName());
        this.setCompletNote(iInfo.getCompletNote());
        this.setCompletNum(iInfo.getCompletNum());
        String value = RouteCompletType.getValue(iInfo.getCompletType());
        //System.out.println("dddddddddddddddddddddddddddddddddddd value= " + value);
        this.setCompletType(value);
        setDataFormatID(iInfo.getDataFormatID());
        String note = iInfo.getCompletState();
        try{
          CodingManageService codeService = (CodingManageService) EJBServiceHelper.
              getService("CodingManageService");
         Collection codes =  codeService.findCoding("RoutelistComplet","completState");
         Iterator i = codes.iterator();
         String codeID = null;
         while(i.hasNext())
         {
            CodingIfc code = (CodingIfc)i.next();
            if(code.getCodeContent().equals(note))
               codeID = code.getBsoID();
        }
        this.setCompletState(codeID);
       }catch(QMException ex)
       {
         ex.printStackTrace();
         throw ex;
       }
        if (verbose)
        {
            System.out.println(
                    "enterprise Managed updateByValueInfo end....return is void");
        }
    } //end updateByValueInfo(BaseValueIfc)

    /**
     *  获得唯一标识对象
     */
    public IdentifyObject getIdentifyObject()
    {
        return new RoutelistCompletIdentity(this.getCompletNum());
    }
    /**
         * 获取值对象
         * @return 值对象
         */
        public BaseValueIfc getValueInfo()
                throws QMException
        {
             RoutelistCompletInfo info = new  RoutelistCompletInfo();
            setValueInfo(info);
            return info;
        }


    static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.resource.verbose", "true")).equals("true");
}
