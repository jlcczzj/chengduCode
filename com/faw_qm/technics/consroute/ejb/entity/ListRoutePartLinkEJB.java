/** ���ɳ��� ListRoutePartLinkEJB 1.0 2005.3.2
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/22 �촺Ӣ      ԭ��:����Ԥ������
 * CR2 2011/12/22 �촺Ӣ       ԭ�����Ӳ�Ʒ��Ϣ��·����Ϣ
 * CR3 2011/12/30 �촺Ӣ      ԭ��ͨ������������·��ֵ����
 * CR4 2012/01/10 �촺Ӣ      ԭ������·����Чʱ���ʧЧʱ��
 * SS1 �����乤��·���ظ����ͬһ�����ͬ�汾 pante 2013-11-16
 * SS2 ����������ӡ��ɹ���ʶ�� Liuyang 2013-12-24 
 * SS3 ������������㲿���汾���� liuyang 2014-2-25
 * SS4 ������Ҫ�������ӹ����㲿���� liunan 2014-11-4
 * SS5 �������ӹ�Ӧ�� liuyang 2014-8-15
 * SS6 �ɶ�����·�����ϣ����ӹ���dwbs��lastEffRoute���ԡ� liunan 2016-8-11
 * SS7 �ɶ�����·�ߣ�������ɫ����ʶcolorFlag���ԡ� liunan 2016-10-25
 * SS8 �ɶ�����·�����⴦�����ʶ��Ϊ�ɶ����㲿�����а汾ת�� ����� 2018-01-11
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;

import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.exception.CodeManageException;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.util.RouteAdoptedType;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * ����·�߶�Ӧ�Ĳ�Ʒ�ṹ����ѡ�񡣲�Ʒ�������
 * @author �ܴ�Ԫ
 * @version 1.0
 */
abstract public class ListRoutePartLinkEJB extends BinaryLinkEJB
{
    /**
     * ���캯��
     */
    public ListRoutePartLinkEJB()
    {

    }

    /**
     * �õ�ҵ������
     * @return String ListRoutePartLink
     */
    public String getBsoName()
    {
        return "consListRoutePartLink";
    }

    /**
     * ���ù���·��ID
     * @param routeID String ����·��ID
     */
    public abstract void setRouteID(String routeID);

    /**
     * �õ�����·��ID
     * @return String ����·��ID
     */
    public abstract String getRouteID();

    /**
     * �ж�����Ƿ��й���·�ߡ���Ϊ�����Ӧ�Ĺ���·��ͨ������ɾ�������Դ˴��Ĺ���·��״ ̬��Ϊ�־û����ԡ� ע�⣺��RoutePartLinkɾ��ʱӦά�������ԣ��������ݵ�һ���ԡ� ���ݵ�һ������saveRoute��RoutePostDeleteListener��ά����
     * @return boolean
     */
    public abstract String getAdoptStatus();

    /**
     * ��������Ĺ���·���Ƿ񱻲��á�
     * @param status ���á�ȡ����
     */
    public abstract void setAdoptStatus(String status);

    //st skybird 2005.3.1
    // gcy add in 2005.4.26 for reinforce requirement  start

    /**
     * �õ������ı��
     * @return String �����ı��
     */
    public abstract String getParentPartNum();

    /**
     * ���ø����ı��
     * @param parentPartNum String �����ı��
     */
    public abstract void setParentPartNum(String parentPartNum);

    /**
     * �õ�������
     * @return String ������
     */
    public abstract String getParentPartName();

    /**
     * ���ø���������
     * @param parentPartNum String ����������
     */
    public abstract void setParentPartName(String parentPartNum);

    /**
     * �õ�������id
     * @return String ������id
     */
    public abstract String getParentPartID();

    /**
     * ���ø�����id
     * @param parentPartNum String ������id
     */
    public abstract void setParentPartID(String parentPartNum);

    /**
     * �õ���ǰ�㲿���ڸ�����ʹ����������һ����ֱ���ϼ���
     * @return String ��ǰ�㲿���ڸ�����ʹ������
     */
    public abstract int getCount();

    /**
     * �����㲿���ڸ������ڲ�Ʒ��ʹ�õ���������һ����ֱ���ϼ���
     * @param count int �㲿���ڸ������ڲ�Ʒ��ʹ�õ�����
     */
    public abstract void setCount(int count);

    // gcy add in 2005.4.26 for reinforce requirement  end

    /**
     * ��������ж���·�߱������·���Ƿ���ʾ�ı�ʶ���������嵥�У����Ƿ�����á�ȡ����
     * @return alterStatus=0��ʾ�Ǵ���һ�汾�̳������ġ�����alterStatus=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɡ�
     */
    public abstract int getAlterStatus();

    /**
     * �����޸�״̬
     * @param alterStatus int int =0 ��ʾ�Ǵ���һ�汾�̳������ģ� int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ� int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
     */
    public abstract void setAlterStatus(int alterStatus);

    /**
     * ��ʼ���ð汾��A, B.���汾�š�
     * @return int
     */
    public abstract String getInitialUsed();

    /**
     * ���ò��ð汾��A, B.��汾��
     * @param version String ���ò��ð汾
     */
    public abstract void setInitialUsed(String version);

    /**
     * �õ�·�߱��id
     * @return String ·�߱��id
     */
    public abstract String getRouteListMasterID();

    /**
     * ����·�߱��id
     * @param listMasterID String ·�߱��id
     */
    public abstract void setRouteListMasterID(String listMasterID);

    /**
     * �õ�������·�߱��С�汾
     * @return String ·�߱��С�汾
     */
    public abstract String getRouteListIterationID();

    /**
     * ���ù�����·�߱��С�汾
     * @param listIterationID String ������·�߱��С�汾
     */
    public abstract void setRouteListIterationID(String listIterationID);

    //begin CR1
    /**
     * ����Ԥ������1
     */
    public abstract void setAttribute1(String attribute1);

    /**
     * ���Ԥ������1
     */
    public abstract String getAttribute1();

    /**
     * ����Ԥ������2
     */
    public abstract void setAttribute2(String attribute2);

    /**
     * ���Ԥ������2
     */
    public abstract String getAttribute2();

    //end CR1

    //begin CR2
    /**
     * ��ø��ı��
     */
    public abstract String getModifyIdenty();

    /**
     * ���ø��ı��
     */
    public abstract void setModifyIdenty(String identy);

    /**
     * �õ���Ʒ��id
     * @return String ��Ʒ��id
     */
    public abstract String getProductID();

    /**
     * ���ò�Ʒ��id
     * @param parentPartNum String ��Ʒ��id
     */
    public abstract void setProductID(String productID);

    /**
     * �����㲿���Ĳ�Ʒ��ʶ
     */
    public abstract boolean getProductIdenty();

    /**
     * �����㲿���Ĳ�Ʒ��ʶ
     * @param productIdenty boolean �㲿���Ĳ�Ʒ��ʶ
     */
    public abstract void setProductIdenty(boolean productIdenty);

    /**
     * �õ���Ʒ����
     * @return int ��Ʒ����
     */
    public abstract int getProductCount();

    /**
     * ���ò�Ʒ����
     * @param productCount int ��Ʒ����
     */
    public abstract void setProductCount(int productCount);

    /**
     * �õ�·�߷�����Ϣ
     * @return int ·�߷�����Ϣ
     */
    public abstract int getReleaseIdenty();

    /**
     * ����·�߷�����Ϣ
     * @param releaseIdenty int ·�߷�����Ϣ
     */
    public abstract void setReleaseIdenty(int releaseIdenty);

    /**
     * �õ���Ҫ·�ߴ���Ϣ
     * @return String ��Ҫ·�ߴ���Ϣ
     */
    public abstract String getMainStr();

    /**
     * ������Ҫ·�ߴ���Ϣ
     * @param mainStr String ��Ҫ·�ߴ���Ϣ
     */
    public abstract void setMainStr(String mainStr);

    /**
     * �õ���Ҫ·�ߴ���Ϣ
     * @return String ��Ҫ·�ߴ���Ϣ
     */
    public abstract String getSecondStr();

    /**
     * ���ô�Ҫ·�ߴ���Ϣ
     * @param mainStr String ��Ҫ·�ߴ���Ϣ
     */
    public abstract void setSecondStr(String secondStr);

    /**
     * ·��˵��
     */
    public abstract java.lang.String getRouteDescription();

    /**
     * ·��˵����
     */
    public abstract void setRouteDescription(String description);
    //end CR2

    //begin CR4
    /**
     * ��� ��Чʱ��
     * @return Timestamp ��Чʱ��
     */
    public abstract Timestamp getEfficientTime();

    /**
     * ������Чʱ��
     * @param efficientData ��Чʱ��
     */
    public abstract void setEfficientTime(Timestamp efficientTime);

    /**
     * ���ʧЧʱ��
     * @return Timestamp ʧЧʱ��
     */
    public abstract Timestamp getDisabledDateTime();

    /**
     * ����ʧЧʱ��
     * @param disabledDateTime ʧЧʱ��
     */
    public abstract void setDisabledDateTime(Timestamp disabledDateTime);
    //end CR4
    //CCBegin SS2
    /**
     * �õ��ɹ���ʶ
     */
    public abstract String getStockID();
    /**
     * ���òɹ���ʶ
     * @param stockID
     */
    public abstract void setStockID(String stockID);
    //CCEnd SS2
    //CCBegin SS3
    /**
     * ��ȡ�㲿���汾
     */
	public abstract String getPartVersion();
    /**
     * �����㲿���汾
     * @param partVersion
     */
	public abstract void setPartVersion(String partVersion);
	//CCEnd SS3
	//CCBegin SS5
	  /**
	   * �õ���Ӧ��
	   */
	  public abstract String getSupplier();
	  /**
	   * ���ù�Ӧ��
	   * @param supplier
	   * @return
	   */
	  public abstract void setSupplier(String supplier);
	  /**
	   * �õ���Ӧ��
	   */
	  public abstract String getSupplierBsoId();
	  /**
	   * ���ù�Ӧ��
	   * @param supplier
	   * @return
	   */
	  public abstract void setSupplierBsoId(String supplierBsoId);
	  //CCEnd SS5
	  
    //CCBegin SS6
    /**
     * �õ���λ��ʶ
     */
    public abstract String getDwbs();
    /**
     * ���õ�λ��ʶ
     * @param stockID
     */
    public abstract void setDwbs(String dwbs);
    
    /**
     * �õ�ȡ��·��
     */
    public abstract String getLastEffRoute();
    /**
     * ����ȡ��·��
     * @param stockID
     */
    public abstract void setLastEffRoute(String lastEffRoute);
    //CCEnd SS6
    
    //CCBegin SS7
    /**
     * ��ɫ��ʶ 
     */
    public abstract String getColorFlag();
    public abstract void setColorFlag(String colorFlag);
    //CCEnd SS7
  //CCBegin SS8
    /**
     * ���⴦���ʶ 
     */
    public abstract String getSpecialFlag();
    public abstract void setSpecialFlag(String specialFlag);
    //CCEnd SS8
    
    /**
     * ���ֵ����
     * @throws QMException
     * @return BaseValueIfc
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        ListRoutePartLinkInfo info = ListRoutePartLinkInfo.newListRoutePartLinkInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * ���½�ֵ����������á�
     * @param info BaseValueIfc ֵ����
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        ListRoutePartLinkInfo listPartInfo = (ListRoutePartLinkInfo)info;
        if(this.getAdoptStatus() != null && RouteAdoptedType.toRouteAdoptedType(this.getAdoptStatus()) != null)
        {
            listPartInfo.setAdoptStatus(RouteAdoptedType.toRouteAdoptedType(this.getAdoptStatus()).getDisplay());
        }
        listPartInfo.setInitialUsed(this.getInitialUsed());
        listPartInfo.setRouteListMasterID(this.getRouteListMasterID());
        listPartInfo.setRouteListIterationID(this.getRouteListIterationID());
        listPartInfo.setAlterStatus(this.getAlterStatus());
        //st skybird 2005.3.1
        // gcy add in 2005.4.26 for reinforce requirement  start
        listPartInfo.setParentPartNum(this.getParentPartNum());
        listPartInfo.setParentPartName(this.getParentPartName());
        listPartInfo.setParentPartID(this.getParentPartID());
        listPartInfo.setCount(this.getCount());
        if(this.getParentPartID() != null && this.getParentPartID().trim().length() != 0)
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            try
            {
                QMPartIfc partInfo = (QMPartIfc)pservice.refreshInfo(this.getParentPartID());
                listPartInfo.setParentPart(partInfo);
            }catch(Exception e)
            {
                //System.out.println("����ɾ��");
            }
        }
        // gcy add in 2005.4.26 for reinforce requirement  end
        //ed
        listPartInfo.setRouteListID(this.getLeftBsoID());
        listPartInfo.setPartMasterID(this.getRightBsoID());
        listPartInfo.setRouteID(this.getRouteID());
        if(this.getRightBsoID() != null && this.getRightBsoID().trim().length() != 0)
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            //CCBegin SS1
            QMPartIfc part = null;
            QMPartMasterIfc partMasterInfo = null;
            if(this.getRightBsoID().contains("QMPart_")){
            	part = (QMPartIfc)pservice.refreshInfo(this.getRightBsoID());
            	partMasterInfo = (QMPartMasterIfc)pservice.refreshInfo(part.getMasterBsoID());
            }
            //CCBegin SS4
            else if(this.getRightBsoID().contains("GenericPart_")){
            	part = (QMPartIfc)pservice.refreshInfo(this.getRightBsoID());
            	partMasterInfo = (QMPartMasterIfc)pservice.refreshInfo(part.getMasterBsoID());
            }
            //CCEnd SS4
            else
            	partMasterInfo = (QMPartMasterIfc)pservice.refreshInfo(this.getRightBsoID());
          //CCEnd SS1
            listPartInfo.setPartMasterInfo(partMasterInfo);
        }else
        {
            throw new QMException("partMasterID ��listRoutePart�����в�ӦΪ��");
        }
        //begin CR1
        listPartInfo.setAttribute1(this.getAttribute1());
        listPartInfo.setAttribute2(this.getAttribute2());
        //end CR1
        //begin CR2
        if(this.getModifyIdenty() != null)
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            CodingIfc codInfo = (CodingIfc)pservice.refreshInfo(this.getModifyIdenty());
            listPartInfo.setModifyIdenty(codInfo.getCodeContent());
        }
        listPartInfo.setProductID(this.getProductID());
        listPartInfo.setProductIdenty(this.getProductIdenty());
        listPartInfo.setProductCount(this.getProductCount());
        listPartInfo.setReleaseIdenty(this.getReleaseIdenty());
        listPartInfo.setMainStr(this.getMainStr());
        listPartInfo.setSecondStr(this.getSecondStr());
        listPartInfo.setRouteDescription(this.getRouteDescription());
        //end CR2
        //begin CR3
        if(this.getRouteID() != null && this.getRouteID().trim().length() != 0)
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)pservice.refreshInfo(this.getRouteID());
            listPartInfo.setRouteInfo(routeInfo);
        }else
        {
            listPartInfo.setRouteInfo(null);
        }
        //end CR3
        //begin CR4
        listPartInfo.setEfficientTime(this.getEfficientTime());
        listPartInfo.setDisabledDateTime(this.getDisabledDateTime());
        //end CR4
        //CCBegin SS2
        listPartInfo.setStockID(this.getStockID());
        //CCEnd SS2
        //CCBegin SS3
        listPartInfo.setPartVersion(this.getPartVersion());
        //CCEnd SS3
        //CCBegin SS5
        listPartInfo.setSupplier(this.getSupplier());
        listPartInfo.setSupplierBsoId(this.getSupplierBsoId());
        //CCEnd SS5
        //CCBegin SS6
        listPartInfo.setDwbs(this.getDwbs());
        listPartInfo.setLastEffRoute(this.getLastEffRoute());
        //CCEnd SS6
        //CCBegin SS7
        listPartInfo.setColorFlag(this.getColorFlag());
        //CCEnd SS7
      //CCBegin SS8
        listPartInfo.setSpecialFlag(this.getSpecialFlag());
        //CCEnd SS8
    }

    /**
     * ����ֵ������г־û���
     * @param info BaseValueIfc ֵ����
     * @see BaseValueInfo
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException 
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter ListRoutePartLinkEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        ListRoutePartLinkInfo listPartInfo = (ListRoutePartLinkInfo)info;
        this.setAdoptStatus(RouteHelper.getValue(RouteAdoptedType.getRouteAdoptedTypeSet(), listPartInfo.getAdoptStatus()));
        this.setInitialUsed(listPartInfo.getInitialUsed());
        this.setRouteListMasterID(listPartInfo.getRouteListMasterID());
        this.setRouteListIterationID(listPartInfo.getRouteListIterationID());
        this.setAlterStatus(listPartInfo.getAlterStatus());
        //st skybird 2005.3.1 �㲿���ĸ������
        // gcy add in 2005.4.26 for reinforce requirement  start
        this.setParentPartNum(listPartInfo.getParentPartNum());
        this.setParentPartName(listPartInfo.getParentPartName());
        this.setParentPartID(listPartInfo.getParentPartID());
        this.setCount(listPartInfo.getCount());
        // gcy add in 2005.4.26 for reinforce requirement  end
        //ed
        this.setRouteID(listPartInfo.getRouteID());
        //begin CR1
        this.setAttribute1(listPartInfo.getAttribute1());
        this.setAttribute2(listPartInfo.getAttribute2());
        //end CR1
        //begin CR2
        String identy = listPartInfo.getModifyIdenty();
        CodingManageService codeService;
		try {
			codeService = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
		
        Collection codes = codeService.findCoding("consTechnicsRoute", "modifyIdenty");
        if(codes != null && codes.size() > 0)
        {
            Iterator i = codes.iterator();
            String codeID = null;
            while(i.hasNext())
            {
                CodingIfc code = (CodingIfc)i.next();
                if(code.getCodeContent().equals(identy))
                    codeID = code.getBsoID();
            }
            this.setModifyIdenty(codeID);
        }
        this.setProductID(listPartInfo.getProductID());
        this.setProductIdenty(listPartInfo.getProductIdenty());
        this.setProductCount(listPartInfo.getProductCount());
        this.setReleaseIdenty(listPartInfo.getReleaseIdenty());
        this.setMainStr(listPartInfo.getMainStr());
        this.setSecondStr(listPartInfo.getSecondStr());
        this.setRouteDescription(listPartInfo.getRouteDescription());
        //end CR2
        
        //begin CR4
        this.setEfficientTime(listPartInfo.getEfficientTime());
        this.setDisabledDateTime(listPartInfo.getDisabledDateTime());
        //end CR4
        //CCBegin SS2
        this.setStockID(listPartInfo.getStockID());
        //CCEnd SS2
        //CCBegin SS3
        this.setPartVersion(listPartInfo.getPartVersion());
        //CCEnd SS3
        //CCBegin SS5
        this.setSupplier(listPartInfo.getSupplier());
        this.setSupplierBsoId(listPartInfo.getSupplierBsoId());
        //CCEnd SS5
        //CCBegin SS6
        this.setDwbs(listPartInfo.getDwbs());
        this.setLastEffRoute(listPartInfo.getLastEffRoute());
        //CCEnd SS6
        //CCBegin SS7
        this.setColorFlag(listPartInfo.getColorFlag());
        //CCEnd SS7
      //CCBegin SS8
        this.setSpecialFlag(listPartInfo.getSpecialFlag());
        //CCEnd SS8
		} catch (QMException e) {
			e.printStackTrace();
			return;
		}
    }

    /**
     * ����ֵ������и��¡�
     * @param info BaseValueIfc ֵ����
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter ListRoutePartLinkEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        ListRoutePartLinkInfo listPartInfo = (ListRoutePartLinkInfo)info;
        this.setAdoptStatus(RouteHelper.getValue(RouteAdoptedType.getRouteAdoptedTypeSet(), listPartInfo.getAdoptStatus()));
        // gcy add in 2005.4.26 for reinforce requirement  start
        this.setParentPartNum(listPartInfo.getParentPartNum());
        this.setParentPartName(listPartInfo.getParentPartName());
        this.setParentPartID(listPartInfo.getParentPartID());
        this.setCount(listPartInfo.getCount());
        // gcy add in 2005.4.26 for reinforce requirement  end
        this.setInitialUsed(listPartInfo.getInitialUsed());
        this.setRouteListMasterID(listPartInfo.getRouteListMasterID());
        this.setRouteListIterationID(listPartInfo.getRouteListIterationID());
        this.setAlterStatus(listPartInfo.getAlterStatus());
        this.setRouteID(listPartInfo.getRouteID());
        //begin CR1
        this.setAttribute1(listPartInfo.getAttribute1());
        this.setAttribute2(listPartInfo.getAttribute2());
        //end CR1
        //begin CR2
        String identy = listPartInfo.getModifyIdenty();
        CodingManageService codeService = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
        Collection codes = codeService.findCoding("consTechnicsRoute", "modifyIdenty");
        if(codes != null && codes.size() > 0)
        {
            Iterator i = codes.iterator();
            String codeID = null;
            while(i.hasNext())
            {
                CodingIfc code = (CodingIfc)i.next();
                if(code.getCodeContent().equals(identy))
                    codeID = code.getBsoID();
            }
            this.setModifyIdenty(codeID);
        }
        this.setProductID(listPartInfo.getProductID());
        this.setProductIdenty(listPartInfo.getProductIdenty());
        this.setProductCount(listPartInfo.getProductCount());
        this.setReleaseIdenty(listPartInfo.getReleaseIdenty());
        this.setMainStr(listPartInfo.getMainStr());
        this.setSecondStr(listPartInfo.getSecondStr());
        this.setRouteDescription(listPartInfo.getRouteDescription());
        //end CR2
        
        //begin CR4
        this.setEfficientTime(listPartInfo.getEfficientTime());
        this.setDisabledDateTime(listPartInfo.getDisabledDateTime());
        //end CR4
      //CCBegin SS2
        this.setStockID(listPartInfo.getStockID());
        //CCEnd SS2
        //CCBegin SS3
        this.setPartVersion(listPartInfo.getPartVersion());
        //CCEnd SS3
        //CCBegin SS5
        this.setSupplier(listPartInfo.getSupplier());
        this.setSupplierBsoId(listPartInfo.getSupplierBsoId());
        //CCEnd SS5
        //CCBegin SS6
        this.setDwbs(listPartInfo.getDwbs());
        this.setLastEffRoute(listPartInfo.getLastEffRoute());
        //CCEnd SS6
        //CCBegin SS7
        this.setColorFlag(listPartInfo.getColorFlag());
        //CCEnd SS7
      //CCBegin SS8
        this.setSpecialFlag(listPartInfo.getSpecialFlag());
        //CCEnd SS8
    }
}
