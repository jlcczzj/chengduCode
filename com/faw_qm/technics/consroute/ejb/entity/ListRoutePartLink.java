/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/22 �촺Ӣ      ԭ��:����Ԥ������
 * CR2 2011/12/22 �촺Ӣ       ԭ�����Ӳ�Ʒ��Ϣ��·����Ϣ
 * CR3 2012/01/10 �촺Ӣ      ԭ������·����Чʱ���ʧЧʱ��
 * SS1 ����������ӡ��ɹ���ʶ������ Liuyang 2013-12-24 
 * SS2 ������������㲿���汾���� liuyang 2014-2-25
 * SS3 �������ӹ�Ӧ�� liuyang 2014-8-15
 * SS4 �ɶ�����·�����ϣ����ӹ���dwbs��lastEffRoute���ԡ� liunan 2016-8-11 
 * SS5 �ɶ�����·�ߣ�������ɫ����ʶcolorFlag���ԡ� liunan 2016-10-25
 * SS6 �ɶ�����·�����⴦�����ʶ��Ϊ�ɶ����㲿�����а汾ת�� ����� 2018-01-11
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import java.util.Date;

import com.faw_qm.framework.service.BinaryLink;

/**
 * ����·�߶�Ӧ�Ĳ�Ʒ�ṹ����ѡ�񡣲�Ʒ������� <p>Title: </p> <p>Description: leftID = routeListID, rightID = partMasterID.</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw_qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0 2005/07/07
 */
public interface ListRoutePartLink extends BinaryLink
{

    /**
     * ���ù���·��ID��
     * @roseuid 4039BA590094
     */
    public void setRouteID(String routeID);

    /**
     * �õ�·�ߵ�id
     * @return String
     */
    public String getRouteID();

    /**
     * �ж�����Ƿ��й���·�ߡ���Ϊ�����Ӧ�Ĺ���·��ͨ������ɾ�������Դ˴��Ĺ���·��״ ̬��Ϊ�־û����ԡ� ע�⣺��RoutePartLinkɾ��ʱӦά�������ԣ��������ݵ�һ���ԡ� ���ݵ�һ������saveRoute��RoutePostDeleteListener��ά����
     * @return boolean
     */
    public String getAdoptStatus();

    /**
     * ��������Ĺ���·���Ƿ񱻲��á�
     * @param status ���á�ȡ����
     */
    public void setAdoptStatus(String status);

    //st skybird 2005.3.1 �㲿���ĸ������
    // gcy add in 2005.4.26 for reinforce requirement  start

    /**
     * �õ������ı��
     * @return String �����ı��
     */
    public String getParentPartNum();

    /**
     * ���ø����ı��
     * @param parentPartNum String
     */
    public void setParentPartNum(String parentPartNum);

    /**
     * �õ�������
     * @return String ����������
     */
    public String getParentPartName();

    /**
     * ���ø���������
     * @param parentPartNum String
     */
    public void setParentPartName(String parentPartNum);

    /**
     * �õ�������id
     * @return String
     */
    public String getParentPartID();

    /**
     * ���ø�����id
     * @param parentPartNum String ������id
     */
    public void setParentPartID(String parentPartNum);

    /**
     * �õ���ǰ�㲿���ڸ������ڲ�Ʒ��ʹ����������һ����ֱ���ϼ���
     * @return int �㲿���ڸ�����ʹ����������һ����ֱ���ϼ���
     */
    public int getCount();

    /**
     * �����㲿���ڸ������ڲ�Ʒ��ʹ�õ���������һ����ֱ���ϼ���
     * @param count int
     */
    public void setCount(int count);

    // gcy add in 2005.4.26 for reinforce requirement  end

    /**
     * �˱�ʶ�����ж���·�߱������·���Ƿ���ʾ���������嵥�У����Ƿ�����á�ȡ����
     * @return int =0 ��ʾ�Ǵ���һ�汾�̳������ģ� int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ� int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
     */
    public int getAlterStatus();

    /**
     * �����޸�״̬
     * @param alterStatus int int =0 ��ʾ�Ǵ���һ�汾�̳������ģ� int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ� int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
     */
    public void setAlterStatus(int alterStatus);

    /**
     * �õ�·�߱��id
     * @return String ·�߱��id
     */
    public String getRouteListMasterID();

    /**
     * ����·�߱��id
     * @param listMasterID String ·�߱��id
     */
    public void setRouteListMasterID(String listMasterID);

    /**
     * �õ�������·�߱��С�汾
     * @return String
     */
    public String getRouteListIterationID();

    /**
     * ���ù�����·�߱��С�汾
     * @param listIterationID String
     */
    public void setRouteListIterationID(String listIterationID);

    /**
     * ��ʼ���ð汾��A, B.��汾�š�
     * @return String
     */
    public String getInitialUsed();

    /**
     * ���ò��ð汾��A, B.��汾��
     * @param version String
     */
    public void setInitialUsed(String version);

    //begin CR1
    /**
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1);

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1();

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2);

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2();

    //end CR1
    //begin CR2
    /**
     * ��ø��ı��
     */
    public String getModifyIdenty();

    /**
     * ���ø��ı��
     */
    public void setModifyIdenty(String identy);

    /**
     * �õ���Ʒ��id
     * @return String ��Ʒ��id
     */
    public String getProductID();

    /**
     * ���ò�Ʒ��id
     * @param parentPartNum String ��Ʒ��id
     */
    public void setProductID(String productID);

    /**
     * �����㲿���Ĳ�Ʒ��ʶ
     */
    public boolean getProductIdenty();

    /**
     * �����㲿���Ĳ�Ʒ��ʶ
     * @param productIdenty boolean �㲿���Ĳ�Ʒ��ʶ
     */
    public void setProductIdenty(boolean productIdenty);

    /**
     * �õ���Ʒ����
     * @return int ��Ʒ����
     */
    public int getProductCount();

    /**
     * ���ò�Ʒ����
     * @param productCount int ��Ʒ����
     */
    public void setProductCount(int productCount);

    /**
     * �õ�·�߷�����Ϣ
     * @return int ·�߷�����Ϣ
     */
    public int getReleaseIdenty();

    /**
     * ����·�߷�����Ϣ
     * @param releaseIdenty int ·�߷�����Ϣ
     */
    public void setReleaseIdenty(int releaseIdenty);

    /**
     * �õ���Ҫ·�ߴ���Ϣ
     * @return String ��Ҫ·�ߴ���Ϣ
     */
    public String getMainStr();

    /**
     * ������Ҫ·�ߴ���Ϣ
     * @param mainStr String ��Ҫ·�ߴ���Ϣ
     */
    public void setMainStr(String mainStr);

    /**
     * �õ���Ҫ·�ߴ���Ϣ
     * @return String ��Ҫ·�ߴ���Ϣ
     */
    public String getSecondStr();

    /**
     * ���ô�Ҫ·�ߴ���Ϣ
     * @param mainStr String ��Ҫ·�ߴ���Ϣ
     */
    public void setSecondStr(String secondStr);
    
    /**
     * ·��˵��
     */
    public java.lang.String getRouteDescription();

    /**
     * ·��˵����
     */
    public void setRouteDescription(String description);
    //end CR2
    
    //begin CR3
    /**
     * ��� ��Чʱ��
     * @return Timestamp ��Чʱ��
     */
    public Timestamp getEfficientTime();

    /**
     * ������Чʱ��
     * @param efficientData ��Чʱ��
     */
    public void setEfficientTime(Timestamp efficientTime);

    /**
     * ���ʧЧʱ��
     * @return Timestamp ʧЧʱ��
     */
    public Timestamp getDisabledDateTime();

    /**
     * ����ʧЧʱ��
     * @param disabledDateTime ʧЧʱ��
     */
    public void setDisabledDateTime(Timestamp disabledDateTime);
    //end CR3
    //CCBegin SS1
    /**
     * �õ��ɹ���ʶ
     */
    public String getStockID();
    /**
     * ���òɹ���ʶ
     * @param stockID
     */
    public void setStockID(String stockID);
  
    //CCEnd SS1
    
    //CCBegin SS2
    /**
     * �õ��㲿���汾
     */
    public String getPartVersion();
    /**
     * �����㲿���汾
     * @param partVersion
     * @return
     */
    public void setPartVersion(String partVersion);
    //CCEnd SS2
    //CCBegin SS3
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
    //CCEnd SS3
    //CCBegin SS4
    /**
     * �õ���λ��ʶ
     */
    public String getDwbs();
    /**
     * ���õ�λ��ʶ
     */
    public void setDwbs(String dwbs);
    
    /**
     * �õ�ȡ��·��
     */
    public String getLastEffRoute();
    /**
     * ����ȡ��·��
     */
    public void setLastEffRoute(String lastEffRoute);
    //CCEnd SS4
    
    
    //CCBegin SS5
    /**
     * ��ɫ��ʶ
     */
    public String getColorFlag();
    public void setColorFlag(String colorFlag);
    //CCEnd SS5
  //CCBegin SS6
    /**
     * ���⴦���ʶ
     */
    public String getSpecialFlag();
    public void setSpecialFlag(String specialFlag);
    //CCEnd SS6
}

