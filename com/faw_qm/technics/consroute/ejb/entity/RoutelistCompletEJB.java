/**
 * ���ɳ��� RoutelistCompletEJB
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
//ע�͵���ʹ�õĵ���·��
//import java.util.Enumeration;
//CCEnd by wanghonglian 2008-07-30
import com.faw_qm.framework.service.*;
//CCBegin by wanghonglian 2008-07-30 
//ע�͵���ʹ�õĵ���·��
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
 * �ձ�֪ͨ��
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw_qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public  abstract class RoutelistCompletEJB extends ManagedEJB
{
    /**
     * ���Ψһ��BsoName
     * @return String
     */
    public String getUniqueBsoName()
    {
        return "com.faw_qm.technics.consroute.ejb.entity.RoutelistComplet";
    }
    /**
        * ���ҵ���������
        */
       public String getBsoName()
       {
           return "consRoutelistComplet";
       }

    /**
     * ��ü�������
     * @return Timestamp ��������
     */
    public abstract Timestamp getDate();

    /**
     * ���ü�������
     * @param date ��������
     */
    public abstract void setDate(Timestamp date);

    /**
     * ��ü���ע��
     * @return String ����ע��
     */
    public abstract String getNote();


    /**
     * ���ü���ע��
     * @param note ����ע��
     */
    public abstract void setNote(String note);


    /**
     * ��ü�����
     * @return String ������
     */
    public abstract String getLocker();


    /**
     * ���ü�����
     * @param locker ������
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
         * ������ݸ�ʽ����
         * @return ���ݸ�ʽ����
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
         * �������ݸ�ʽBsoID
         * @param dataFormatID ���ݸ�ʽBsoID
         */
        public abstract void setDataFormatID(String dataFormatID);

        /**
         * ������ݸ�ʽ����
         * @return ���ݸ�ʽ����
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
         * ������ݸ�ʽ����
         * @return ���ݸ�ʽֵ����
         */
        public DataFormatInfo getDataFormatInfo()
            throws QMException
        {
          if (getDataFormatID()!=null)
          {
            DataFormatInfo dataFormatInfo = null;
            //���ܸĽ�:�����ݷ���DataFormatFactory�����л�ȡ�ļ���ʽֵ������Ϣ
            String dataFormatName = DataFormatFactory.getFormatName(getDataFormatID());
            if(dataFormatName!=null)
               dataFormatInfo = DataFormatFactory.getFormatByName(dataFormatName);
            return dataFormatInfo;
          }
          return null;
        }


        /**
         * �������������
         * @return ����������
         */
        public Vector getContentVector()
            throws QMException
        {
          ContentService service = (ContentService)EJBServiceHelper.getService("ContentService");
          return service.getContents((ContentHolder)getBsoReference());
        }
        /**
           * �ж������Ƿ�������
           * @param boolean �ж������Ƿ�������
           */
          public boolean isHasContents()
              throws QMException,ServiceLocatorException
          {
            ContentService service = (ContentService)EJBServiceHelper.getService("ContentService");
            return service.isHasContent((ContentHolder)getBsoReference());
          }

          /**
           * �����Ҫ������
           * @return ��Ҫ������
           */
          public ContentItem getPrimary()
              throws QMException
          {
            ContentService service = (ContentService)EJBServiceHelper.getService("ContentService");
            return service.getPrimaryContent((FormatContentHolder)getBsoReference());
          }


    /**
     * ����ֵ����
     * @exception com.faw_qm.framework.exceptions.QMException
     * @param info ֵ����
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
     * ����ֵ����
     * @param info ֵ����
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
     * ����ֵ����
     * @param info ֵ����
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
     *  ���Ψһ��ʶ����
     */
    public IdentifyObject getIdentifyObject()
    {
        return new RoutelistCompletIdentity(this.getCompletNum());
    }
    /**
         * ��ȡֵ����
         * @return ֵ����
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
