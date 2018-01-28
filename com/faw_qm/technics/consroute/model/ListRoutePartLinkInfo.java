/**
 * ���ɳ��� ListRoutePartLinkInfo.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/22 �촺Ӣ       ԭ������Ԥ������
 * CR2 2011/12/22 �촺Ӣ       ԭ�����Ӹ��ı�ǡ���Ʒ��Ϣ��·����Ϣ
 * CR3 2011/12/30 �촺Ӣ      ԭ��ͨ������������·��ֵ����
 * CR4 2012/01/10 �촺Ӣ      ԭ������·��������·����Чʱ���ʧЧʱ��
 * SS1 ����������ӡ��ɹ���ʶ��Liuyang 2013-12-24
 * SS2 ������������㲿���汾���� liuyang 2014-2-25
 * SS3 ���ع�Ӧ�� liuyang 2014-8-15
 * SS4 �ɶ�����·�����ϣ����ӹ���dwbs��lastEffRoute���ԡ� liunan 2016-8-11
 * SS5 �ɶ�����·�ߣ�������ɫ����ʶcolorFlag���ԡ� liunan 2016-10-25
 * SS6 �ɶ�����·�����⴦�����ʶ��Ϊ�ɶ����㲿�����а汾ת�� ����� 2018-01-11
 * SS7 �ɶ�����·��ʾ����Դ�汾 ����� 2018-01-21
 */
package com.faw_qm.technics.consroute.model;

import java.sql.Timestamp;
import java.util.Date;

import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BinaryLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.util.RouteAdoptedType;
import com.faw_qm.util.EJBServiceHelper;

/**
 * ·���㲿����ϵֵ����
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class ListRoutePartLinkInfo extends BinaryLinkInfo implements ListRoutePartLinkIfc
{

    //private static final String routeMode = RemoteProperty.getProperty("routeManagerMode", "partRelative");
    /**
     * �㲿������Ϣֵ����
     */
    private QMPartMasterIfc partMasterInfo;

    /**
     * ����ֵ����
     */
    private QMPartIfc parentPartInfo;

    /**
     * ·��id
     */
    private String routeID;

    /**
     * �޸�״̬
     */
    private int alterStatus;

    /**
     * ·��״̬
     */
    private String status;

    //st skybird 2005.3.1

    /**
     * �������
     */
    private String parentPartNum;

    /**
     * ��������
     */
    private String parentPartName;

    /**
     * ����id
     */
    private String parentPartID;

    /**
     * �㲿���ڸ����ڲ�Ʒ��ʹ�õ�����
     */
    private int count;

    /**
     * ��ʼ·�߱��汾
     */
    private String vesion;

    /**
     * ·�߱�����Ϣid
     */
    private String listMasterID;

    /**
     * ·�߱�ľ���汾
     */
    private String listIterationID;
    private static final long serialVersionUID = 1L;
    //begin CR1
    private String attribute1;
    private String attribute2;
    //end CR1

    //begin CR2
    /**
     * ���ı��
     */
    private String modifyIdenty;

    /**
     * ��Ʒid
     */
    private String productID;

    /**
     * ��Ʒ��ʶ
     */
    private boolean productIdenty;

    /**
     * ��Ʒ����
     */
    private int productCount;

    /**
     * ·���Ƿ񷢲�
     */
    private int releaseIdenty;

    /**
     * ��Ҫ·�ߴ�
     */
    private String mainStr;

    /**
     * ��Ҫ·�ߴ�
     */
    private String secondStr;
    
    
    //end CR2

    //CR3
    private TechnicsRouteIfc routeInfo;

    //begin CR4
    /**
     * ·������
     */
    private String routeDescription;
    
    /**
     * ��Чʱ��
     */
    private Timestamp efficientTime;
    
    /**
     * ʧЧʱ��
     */
    private Timestamp disabledDateTime;
    
    private String PartBranchID;
    //CCBegin SS1
    private String stockID;
    //CCEnd SS1
  //CCBegin SS2
	private String partVersion;
	//CCEnd SS2
    
    //CCBegin SS4
    private String dwbs;
    
    private String lastEffRoute;
    //CCEnd SS4
    //CCBegin SS5
    private String colorFlag;
    //CCEnd SS5
    //CCBegin SS6
    private String specialFlag;
    //CCEnd SS6
  //CCBegin SS7
    private String sourceVersion;
    //CCEnd SS7
    /**
     * ���캯��
     */
    public ListRoutePartLinkInfo()
    {}

    /**
     *�õ��µ�·�������������ֵ����
     * @return ListRoutePartLinkInfo listLinkInfo �µ�·�������������ֵ����
     */
    public static ListRoutePartLinkInfo newListRoutePartLinkInfo()
    {
        ListRoutePartLinkInfo listLinkInfo = new ListRoutePartLinkInfo();
        return listLinkInfo;
    }

    /**
     * �ͻ��˵��÷���������һ���µ�ֵ����
     * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ����
     * @param partMasterID String �������ϢID
     * @return ListRoutePartLinkInfo �µ�·�������������ֵ����
     */
    public static ListRoutePartLinkInfo newListRoutePartLinkInfo(TechnicsRouteListIfc routeListInfo, String partMasterID)
    {
        ListRoutePartLinkInfo listLinkInfo = new ListRoutePartLinkInfo();
        listLinkInfo.setPartMasterID(partMasterID);
        listLinkInfo.setRouteListID(routeListInfo.getBsoID());
   
        listLinkInfo.setInitialUsed(routeListInfo.getVersionID());
     
        //·��δ���ɣ���ʼ״̬Ϊ�̳С�
        listLinkInfo.setAlterStatus(TechnicsRouteServiceEJB.INHERIT);
        listLinkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
        listLinkInfo.setRouteListIterationID(routeListInfo.getVersionValue());
        listLinkInfo.setRouteListMasterID(routeListInfo.getMaster().getBsoID());
        return listLinkInfo;
    }
    

    /**
     * ���ҵ�����
     * @return String ListRoutePartLink
     */
    public String getBsoName()
    {
        return "consListRoutePartLink";
    }

    /**
     * �õ�·��id
     * @return String ·��id
     */
    public String getRouteID()
    {
        return this.routeID;
    }

    /**
     * ����·��id
     * @param routeID String ·��id
     */
    public void setRouteID(String routeID)
    {
        this.routeID = routeID;
    }

    //begin CR3
    /**
     * ��ù���·�߶���
     */
    public TechnicsRouteIfc getRouteInfo()
    {
        return routeInfo;
    }

    /**
     * ���ù��� ·�߶���
     * @param routeInfo ����·��
     * @return void
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo)
    {
        this.routeInfo = routeInfo;
    }

    //end CR3

    /**
     * �õ��㲿������Ϣid
     * @return String �㲿������Ϣid
     */
    public String getPartMasterID()
    {
        return this.getRightBsoID();
    }

    /**
     * �����㲿������Ϣid
     * @param partMasterID String �㲿������Ϣid
     */
    public void setPartMasterID(String partMasterID)
    {
        this.setRightBsoID(partMasterID);
    }

    /**
     * �ж�����Ƿ��й���·�ߡ���Ϊ�����Ӧ�Ĺ���·��ͨ������ɾ�������Դ˴��Ĺ���·��״ ̬��Ϊ�־û����ԡ� ע�⣺��RoutePartLinkɾ��ʱӦά�������ԣ��������ݵ�һ���ԡ� ���ݵ�һ������saveRoute��RoutePostDeleteListener��ά����
     */
    public String getAdoptStatus()
    {
        return this.status;
    }

    /**
     * ��������Ĺ���·���Ƿ񱻲��á�
     * @param status ���á�ȡ����
     */
    public void setAdoptStatus(String status)
    {
        this.status = status;
    }

    //st skybird 2005.3.1
    // gcy add in 2005.4.26 for reinforce requirement  start

    /**
     * �����㲿������㲿���ĸ������
     * @param status ���á�ȡ����
     */
    public String getParentPartNum()
    {
        return this.parentPartNum;
    }

    /**
     * �����㲿���ĸ������
     * @param parentPartNum String �㲿���ĸ������
     */
    public void setParentPartNum(String parentPartNum)
    {
        this.parentPartNum = parentPartNum;
    }

    /**
     * �õ�������
     * @return String ������
     */
    public String getParentPartName()
    {
        return this.parentPartName;
    }

    /**
     * ���ø�����
     * @param parentPartNum String ������
     */
    public void setParentPartName(String parentPartNum)
    {
        this.parentPartName = parentPartNum;
    }

    /**
     * �õ�������id
     * @return String ������id
     */
    public String getParentPartID()
    {
        return this.parentPartID;
    }

    /**
     * ���ø�����id
     * @param parentPartNum String ������id
     */
    public void setParentPartID(String parentPartNum)
    {
        this.parentPartID = parentPartNum;
    }

    /**
     * �õ�����ֵ����
     * @return String ����ֵ����
     */
    public QMPartIfc getParentPart()
    {
        return this.parentPartInfo;
    }

    /**
     * ���ø���ֵ����
     * @param part QMPartIfc ����ֵ����
     * @see QMPartInfo
     */
    public void setParentPart(QMPartIfc part)
    {
        this.parentPartInfo = part;
    }

    /**
     * �õ���ǰ�㲿���ڸ�����ʹ����������һ����ֱ���ϼ���
     * @return String ��ǰ�㲿���ڸ�����ʹ������
     */
    public int getCount()
    {
        return this.count;
    }

    /**
     * ���õ�ǰ�㲿���ڸ�����ʹ����������һ����ֱ���ϼ���
     * @param count int ��ǰ�㲿���ڸ�����ʹ������
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    // gcy add in 2005.4.26 for reinforce requirement  end

    //ed

    /**
     * ��ʼ���ð汾��A, B.��汾�š�
     * @return String A, B.��汾��
     */
    public String getInitialUsed()
    {
        return this.vesion;
    }

    /**
     * ���ó�ʼ���ð汾��A, B.��汾�š�
     * @param vesion String ��ʼ���ð汾
     */
    public void setInitialUsed(String version)
    {
        this.vesion = version;
    }

    /**
     * �˱�ʶ�����ж���·�߱������·���Ƿ���ʾ���������嵥�У����Ƿ�����á�ȡ����
     * @return int =0 ��ʾ�Ǵ���һ�汾�̳������ģ�int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ�int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
     */
    public int getAlterStatus()
    {
        return this.alterStatus;
    }

    /**
     * �����޸�״̬
     * @param alterStatus int int =0 ��ʾ�Ǵ���һ�汾�̳������ģ� int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ� int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
     */
    public void setAlterStatus(int alterStatus)
    {
        this.alterStatus = alterStatus;
    }

    /**
     * �õ�����·�߱�ID��
     * @return String ����·�߱�ID��
     */
    public String getRouteListID()
    {
        return this.getLeftBsoID();
    }

    /**
     * ���ù���·�߱�ID��
     * @param routeListID String ����·�߱�ID��
     */
    public void setRouteListID(String routeListID)
    {
        this.setLeftBsoID(routeListID);
    }

    /**
     * �õ�·�߱��id
     * @return String ·�߱��id
     */
    public String getRouteListMasterID()
    {
        return this.listMasterID;
    }

    /**
     * ����·�߱��id
     * @param listMasterID String ·�߱��id
     */
    public void setRouteListMasterID(String listMasterID)
    {
        this.listMasterID = listMasterID;
    }

    /**
     * �õ�������·�߱��С�汾
     * @return String ������·�߱��С�汾
     */
    public String getRouteListIterationID()
    {
        return this.listIterationID;
    }

    /**
     * ���ù�����·�߱��С�汾
     * @param listIterationID String ������·�߱��С�汾
     */
    public void setRouteListIterationID(String listIterationID)
    {
        this.listIterationID = listIterationID;
    }

    /**
     * ����������Ϣֵ���� �ڿͻ��˵��á�
     * @return QMPartMasterIfc �������Ϣֵ����
     */
    public QMPartMasterIfc getPartMasterInfo()
    {
        return this.partMasterInfo;
    }

    /**
     * �����������Ϣֵ���� partMasterInfo��EJB��ʼ����
     * @param partMasterInfo QMPartMasterIfc
     * @see QMPartMasterInfo
     */
    public void setPartMasterInfo(QMPartMasterIfc partMasterInfo)
    {
        this.partMasterInfo = partMasterInfo;
    }

    //begin CR1
    /**
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }

    //end CR1

    //begin CR2
    /**
     * ��ø��ı��
     * @return ���ı��
     */
    public String getModifyIdenty()
    {
        return this.modifyIdenty;
    }

    /**
     * ���ø��ı��
     * @param identy ���ı��
     */
    public void setModifyIdenty(String identy)
    {
        this.modifyIdenty = identy;
    }

    /**
     * �õ���Ʒ��id
     * @return String ��Ʒ��id
     */
    public String getProductID()
    {
        return this.productID;
    }

    /**
     * ���ò�Ʒ��id
     * @param parentPartNum String ��Ʒ��id
     */
    public void setProductID(String productID)
    {
        this.productID = productID;
    }

    /**
     * �����㲿���Ĳ�Ʒ��ʶ
     */
    public boolean getProductIdenty()
    {
        return this.productIdenty;
    }

    /**
     * �����㲿���Ĳ�Ʒ��ʶ
     * @param productIdenty boolean �㲿���Ĳ�Ʒ��ʶ
     */
    public void setProductIdenty(boolean productIdenty)
    {
        this.productIdenty = productIdenty;
    }

    /**
     * �õ���Ʒ����
     * @return int ��Ʒ����
     */
    public int getProductCount()
    {
        return this.productCount;
    }

    /**
     * ���ò�Ʒ����
     * @param productCount int ��Ʒ����
     */
    public void setProductCount(int productCount)
    {
        this.productCount = productCount;
    }

    /**
     * �õ�·�߷�����Ϣ
     * @return int ·�߷�����Ϣ
     */
    public int getReleaseIdenty()
    {
        return this.releaseIdenty;
    }

    /**
     * ����·�߷�����Ϣ
     * @param releaseIdenty int ·�߷�����Ϣ
     */
    public void setReleaseIdenty(int releaseIdenty)
    {
        this.releaseIdenty = releaseIdenty;
    }

    /**
     * �õ���Ҫ·�ߴ���Ϣ
     * @return String ��Ҫ·�ߴ���Ϣ
     */
    public String getMainStr()
    {
        return this.mainStr;
    }

    /**
     * ������Ҫ·�ߴ���Ϣ
     * @param mainStr String ��Ҫ·�ߴ���Ϣ
     */
    public void setMainStr(String mainStr)
    {
        this.mainStr = mainStr;
    }

    /**
     * �õ���Ҫ·�ߴ���Ϣ
     * @return String ��Ҫ·�ߴ���Ϣ
     */
    public String getSecondStr()
    {
        return this.secondStr;
    }

    /**
     * ���ô�Ҫ·�ߴ���Ϣ
     * @param mainStr String ��Ҫ·�ߴ���Ϣ
     */
    public void setSecondStr(String secondStr)
    {
        this.secondStr = secondStr;
    }
    
    /**
     * ���·��˵��
     * @return String ·��˵��
     */
    public String getRouteDescription()
    {
        return this.routeDescription;
    }

    /**
     * ����·��˵����
     * @param description ·��˵��
     */
    public void setRouteDescription(String description)
    {
        this.routeDescription = description;
    }
    //end CR2
    
    //begin CR4
    /**
     * ��� ��Чʱ��
     * @return Timestamp ��Чʱ��
     */
    public Timestamp getEfficientTime()
    {
        return this.efficientTime;
    }

    /**
     * ������Чʱ��
     * @param efficientData ��Чʱ��
     */
    public void setEfficientTime(Timestamp efficientTime)
    {
        this.efficientTime = efficientTime;
    }
    

    /**
     * ���ʧЧʱ��
     * @return Timestamp ʧЧʱ��
     */
    public Timestamp getDisabledDateTime()
    {
        return this.disabledDateTime;
    }

    /**
     * ����ʧЧʱ��
     * @param disabledDateTime ʧЧʱ��
     */
    public void setDisabledDateTime(Timestamp disabledDateTime)
    {
        this.disabledDateTime = disabledDateTime;
    }
    //end CR4
    
    /**
     * �õ������������С�汾
     * @return String
     */
    public String getPartBranchID() {
      return this.PartBranchID;
    }

    /**
     * ���ù����������С�汾
     * @param listIterationID String
     */
    public void setPartBranchID(String partBranchID) {
      this.PartBranchID = partBranchID;
    }

    //CCBegin SS1
    /**
     * �õ��ɹ���ʶ
     */
	public String getStockID() {
		return this.stockID;
	}

	  /**
     * ���òɹ���ʶ
     * @param stockID
     */
	public void setStockID(String stockID) {
		
		this.stockID=stockID;
	}
	  //CCEnd SS1
    //CCBegin SS2

	public String getPartVersion() {
		
		return this.partVersion;
	}


	public void setPartVersion(String partVersion) {
		
		this.partVersion=partVersion;
	}
	//CCEnd SS2

//CCBegin SS3
	private String supplier;
	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier=supplier;
		
	}
	private String supplierBsoId;
	public String getSupplierBsoId() {
		
		return this.supplierBsoId;
	}

	
	public void setSupplierBsoId(String supplierBsoId) {
		this.supplierBsoId=supplierBsoId;
	//CCEnd SS3
	}
	
	//CCBegin SS4
	public String getDwbs() {
		return this.dwbs;
	}

	public void setDwbs(String dwbs) {
		this.dwbs=dwbs;
	}
	
  public String getLastEffRoute()
  {
    return this.lastEffRoute;
  }

  public void setLastEffRoute(String lastEffRoute)
  {
    this.lastEffRoute = lastEffRoute;
  }
	//CCEnd SS4
	
	
	//CCBegin SS5
	public String getColorFlag()
	{
		return this.colorFlag;
	}
	
	public void setColorFlag(String colorFlag)
	{
		this.colorFlag=colorFlag;
	}
	//CCEnd SS5
	//CCBegin SS6
		public String getSpecialFlag()
		{
			return this.specialFlag;
		}
		
		public void setSpecialFlag(String specialFlag)
		{
			this.specialFlag=specialFlag;
		}
		//CCEnd SS6
		//CCBegin SS7
	    
	    public String getSourceVersion() {
	    	return this.sourceVersion;
	    };
	      
	    public void setSourceVersion(String sourceVersion) {
	    	this.sourceVersion = sourceVersion;
	    };
	  //CCEnd SS7
}
