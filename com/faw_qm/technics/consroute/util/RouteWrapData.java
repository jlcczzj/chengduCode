/**
 * ���ɳ���RouteWrapData.java    1.0    2011-12-31
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ������������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �����������ë��״̬ liuyang 2013-1-13
 * SS2 ������������㲿���汾 liuyang 2014-2-25
 * SS3 �������ӹ�Ӧ�̺͹�Ӧ��Bsoid liuyang 2014-8-20
 * SS4 �ɶ�������ɫ����ʶcolorFlag���ԡ� liunan 2016-10-25
 * SS5 �ɶ�����·�����⴦�����ʶ��Ϊ�ɶ����㲿�����а汾ת�� ����� 2018-01-11
 */
package com.faw_qm.technics.consroute.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;

/**
 * <p>�ͻ������ݷ�װ��</p> 
 * �����ڿͻ��������˴���·�����ݵķ�װ�࣬ ����װ��׼ʹ������Ĺ�����Ϣ�������Ϣ�Լ����·����Ϣ
 * ������Ϣ�����ɷ����޹�˾
 * @author �촺Ӣ
 * @version 1.0
 */
public class RouteWrapData implements Serializable
{
    static final long serialVersionUID = 1L;
    //��׼�������
    private String partMasterID;
    //·�߸��ı��
    private String modifyIdenty;
    //��Ҫ·�ߴ�
    private String mainStr;
    //��Ҫ·�ߴ�
    private String secondStr;
    //·������
    private String description;
    //��Ʒ����
    private int productCount;
    //��Ʒ��ʶ
    private boolean productIndenty = false;
    //�������
    String parentNum;
    //��������
    String parentName;
    //·��ID
    private String routeID;
    //��׼���������ID
    private String linkID;
    //Ҫ�洢��·����ض��󼯺�
    //20120116 xucy add  ��Ʒ��Ϣ
    private QMPartMasterIfc product;
    //������Ϣ 
    private QMPartIfc parent;
    //·����Ϣ����
    private HashMap routeMap;
    //������
    String partNum;
    //�������
    String partName;
    //����к�
    int rowNum;
    //CCBegin SS1
    private String stockID;
    //CCend SS1
    //CCBegin SS2
    private String partVersion;
    //CCend SS2
    //CCBegin SS3
    private String supplier;
    private String supplierBsoId;
    //CCEnd SS3
    //CCBegin SS4
    private String colorFlag;
    //CCend SS4
  //CCBegin SS5
    private String specialFlag;
    //CCend SS5
    //������ݿ��в����ڵ�·�ߵ�λ����
    private Object[] departmentVec;

    /**
     * RouteWrapData��Ĺ��췽��
     */
    public RouteWrapData()
    {
        super();
    }

    //CCBegin SS1
    /**
     * ���ë��״̬
     */
    public String getStockID()
    {
    	return stockID;
    }
    /**
     * ����ë��״̬
     * @param stockID
     */
    public void setStockID(String stockID)
    {
    	this.stockID=stockID;
    }
    //CCend SS1
    //CCBegin SS2

	public String getPartVersion() {
		
		return partVersion;
	}


	public void setPartVersion(String partVersion) {
		
		this.partVersion=partVersion;
	}
	//CCEnd SS2
	//CCBegin SS3
	 public String getSupplier()
	    {
	    	return supplier;
	    }

     public void setSupplier(String supplier)
	    {
	    	this.supplier=supplier;
	    }
     public String getSupplierBsoId()
	    {
	    	return supplierBsoId;
	    }

  public void setSupplierBsoId(String supplierBsoId)
	    {
	    	this.supplierBsoId=supplierBsoId;
	    }
	//CCend SS3
	
	//CCBegin SS4
	public String getColorFlag()
	{
		return colorFlag;
	}
	
	public void setColorFlag(String colorFlag)
	{	
		this.colorFlag=colorFlag;
	}
	//CCEnd SS4
	//CCBegin SS5
		public String getSpecialFlag()
		{
			return specialFlag;
		}
		
		public void setSpecialFlag(String specialFlag)
		{	
			this.specialFlag=specialFlag;
		}
		//CCEnd SS5
    /**
     * �����׼�������ID
     * @return String ��׼�������ID
     */
    public String getPartMasterID()
    {
        return partMasterID;
    }

    /**
     * ������׼�������ID
     * @param partMasterID String ��׼�������ID
     */
    public void setPartMasterID(String partMasterID)
    {
        this.partMasterID = partMasterID;
    }

    /**
     * ���·�߸��ı��
     * @return String ·�߸��ı��
     */
    public String getModifyIdenty()
    {
        return modifyIdenty;
    }

    /**
     * ����·�߸��ı��
     * @param codeContent String ·�߸��ı��
     * @see String
     */
    public void setModifyIdenty(String modifyIdenty)
    {
        this.modifyIdenty = modifyIdenty;
    }

    /**
     * �����Ҫ·�ߴ�
     * @return String ��Ҫ·�ߴ�
     */
    public String getMainStr()
    {
        return mainStr;
    }

    /**
     * ������Ҫ·�ߴ�
     * @param mainStr String ��Ҫ·�ߴ�
     */
    public void setMainStr(String mainStr)
    {
        this.mainStr = mainStr;
    }

    /**
     * ��ô�Ҫ·�ߴ�
     * @return String ��Ҫ·�ߴ�
     */
    public String getSecondStr()
    {
        return secondStr;
    }

    /**
     * ���ô�Ҫ·�ߴ�
     * @param secondStr String ��Ҫ·�ߴ�
     */
    public void setSecondStr(String secondStr)
    {
        this.secondStr = secondStr;
    }

    /**
     * ���·������
     * @return String ·������
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * ����·������
     * @param description String ·������
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * ��ò�Ʒ����
     * @return int ��Ʒ����
     */
    public int getProductCount()
    {
        return productCount;
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
     * ��ò�Ʒ��ʶ
     * @return boolean ��Ʒ��ʶ
     */
    public boolean getProductIndenty()
    {
        return productIndenty;
    }

    /**
     * ���ò�Ʒ��ʶ
     * @param productIndenty boolean ��Ʒ��ʶ
     */
    public void setProductIndenty(boolean productIndenty)
    {
        this.productIndenty = productIndenty;
    }

    /**
     * ��ø������
     * @return String �������
     */
    public String getParentNum()
    {
        return parentNum;
    }

    /**
     * ���ø������
     * @param parentNum String �������
     */
    public void setParentNum(String parentNum)
    {
        this.parentNum = parentNum;
    }

    /**
     * ��ø�������
     * @return String ��������
     */
    public String getParentName()
    {
        return parentName;
    }

    /**
     * ���ø�������
     * @param parentName String ��������
     */
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    /**
     * ���·��ID
     * @return String ·��ID
     */
    public String getRouteID()
    {
        return routeID;
    }

    /**
     * ����·��ID
     * @param description String ·��ID
     */
    public void setRouteID(String routeID)
    {
        this.routeID = routeID;
    }

    /**
     * �����׼�������ID
     * @return String ��׼�������ID
     */
    public String getLinkID()
    {
        return linkID;
    }

    /**
     * ������׼�������ID
     * @param description String ��׼�������ID
     */
    public void setLinkID(String linkID)
    {
        this.linkID = linkID;
    }

    /**
     * ���·����ض��󼯺�
     * @return HashMap ·����ض��󼯺�
     */
    public HashMap getRouteMap()
    {
        return routeMap;
    }

    /**
     * ����·����ض��󼯺�
     * @param routeMap HashMap ·����ض��󼯺�
     */
    public void setRouteMap(HashMap routeMap)
    {
        this.routeMap = routeMap;
    }

    /**
     * ��ò�Ʒ��Ϣ
     * @return QMPartMasterIfc ��Ʒ��Ϣ
     */
    public QMPartMasterIfc getProduct()
    {
        return product;
    }

    /**
     * ���ò�Ʒ
     * @param description QMPartMasterIfc ��Ʒ��Ϣ
     */
    public void setProduct(QMPartMasterIfc product)
    {
        this.product = product;
    }
    
    /**
     * ��ø�����Ϣ
     * @return QMPartMasterIfc ������Ϣ
     */
    public QMPartIfc getParent()
    {
        return parent;
    }

    /**
     * ���ø���
     * @param description QMPartIfc ������Ϣ
     */
    public void setParent(QMPartIfc parent)
    {
        this.parent = parent;
    }
    
    /**
     * ���������
     * @return String ������
     */
    public String getPartNum()
    {
        return this.partNum;
    }

    /**
     * ����������
     * @param partNum String ������
     */
    public void setPartNum(String partNum)
    {
        this.partNum = partNum;
    }

    /**
     * ����������
     * @return String �������
     */
    public String getPartName()
    {
        return this.partName;
    }

    /**
     * �����������
     * @param partName String �������
     */
    public void setPartName(String partName)
    {
        this.partName = partName;
    }

    /**
     * �������к�
     * @return int ����к�
     */
    public int getRowNum()
    {
        return this.rowNum;
    }

    /**
     * ��������к�
     * @param partName int ����к�
     */
    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }
    
    
    /**
     * ���·�ߵ�λ����
     * @return Vector ·�ߵ�λ����
     */
    public Object[] getDepartmentVec()
    {
        return this.departmentVec;
    }

    /**
     * ����·�ߵ�λ����
     * @param partName Vector ·�ߵ�λ����
     */
    public void setDepartmentVec(Object[] departmentVec)
    {
        this.departmentVec = departmentVec;
    }
    
    
    /**
     * ����ǰ��ķǾ�̬�ͷ�˲̬�ֶ�д�����
     * @param oos ObjectOutputStream ������ @
     * @throws QMException 
     */
    private void writeObject(ObjectOutputStream oos) throws QMException
    {
        try
        {
            oos.defaultWriteObject();
        }catch(Exception ex)
        {
            throw new QMException(ex);
        }
    }

    /**
     * �Ӵ�����ȡ��ǰ��ķǾ�̬�ͷ�˲̬�ֶ�
     * @param ois ObjectInputStream ����� @ @
     * @throws QMException 
     */
    private void readObject(ObjectInputStream ois) throws QMException
    {
        try
        {
            ois.defaultReadObject();
        }catch(Exception ex)
        {
            throw new QMException(ex);
        }
    }
}
