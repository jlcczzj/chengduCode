/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/20 �촺Ӣ       ԭ��ͳһ����
 * CR2 2011/12/22 �촺Ӣ       ԭ����׼����Ԥ������
 * CR3 ����  ��������ﲻ����������
 * SS1 ԭ�����븳ֵ���� pante 20130720
 */

package com.faw_qm.technics.consroute.model;

import java.util.Vector;

import com.faw_qm.capp.model.QMTechnicsMasterInfo;
import com.faw_qm.enterprise.model.RevisionControlledInfo;
import com.faw_qm.framework.exceptions.QMException;

//����·�߱�ı�ţ�Ψһ��ʶ��ע����sql�жԶ�Ӧ��master����Ψһ�����������ڴ�����ʱҪ��װoracle�쳣��

/**
 * ����·�߱�ֵ����
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class TechnicsRouteListInfo extends RevisionControlledInfo implements TechnicsRouteListIfc
{
    private static final long serialVersionUID = 1L;

    /**
     * ���ҵ���������
     * @return String TechnicsRouteList
     */
    public String getBsoName()
    {
        return "consTechnicsRouteList";
    }

    /**
     * ����·�߱�����ƣ���Ҫ���зǿռ��.
     */
    private String name;

    /**
     * ����·�߱�ı�ţ�Ψһ��ʶ�����зǿռ�顣
     */
    private String number;

    /**
     * ·�߱�˵��
     */
    private String description;

    /**
     * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
     */
    private String level;

    // ����·�߱��״̬
    private String state;

    /**
     * ����·�߱�ĵ�λID.
     */
    private String department;

    /**
     * ����·�߱�ĵ�λ����.
     */
    private String departmentName;

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     */
    private String productMasterID;

    private Vector partIndex;
    //begin CR2
    private String attribute1;
    private String attribute2;

    //end CR2

    /**
     * ���캯��
     */
    public TechnicsRouteListInfo()
    {
        TechnicsRouteListMasterInfo masterInfo = new TechnicsRouteListMasterInfo();
        this.setMaster(masterInfo);
    }

    /**
     * ��ù���·�߱�ı�ţ�Ψһ��ʶ��
     * @return String ����·�߱�ı��
     */
    public String getRouteListNumber()
    {
        //CR3 begin
        return ((TechnicsRouteListMasterInfo) getMaster()).getRouteListNumber();
        //CR3 end
    }

    /**
     * ���ù���·�߱�ı�ţ�Ψһ��ʶ
     * @param number -����·�߱�ı�ţ��ַ����Ȳ�����30
     * @throws QMException 
     */
    public void setRouteListNumber(String number) throws QMException
    {
        if(number == null || number.trim().length() == 0)
        {
            throw new QMException("2", null,null);
        }
        ((TechnicsRouteListMasterInfo)getMaster()).setRouteListNumber(number);
        this.number = number;
    }

    /**
     * �õ��㲿����˳��
     * @return Vector �㲿����˳��
     */
    public Vector getPartIndex()
    {
        return this.partIndex;
    }

    /**
     * �����㲿����˳��
     * @param partIndex Vector �㲿����˳��
     */
    public void setPartIndex(Vector partIndex)
    {
        this.partIndex = partIndex;
    }

    /**
     * ��ù���·�߱�����ƣ���Ҫ���зǿռ��
     * @return String ����·�߱������
     */
    public String getRouteListName()
    {
        //CR3 begin
    	//SSBegin SS1
    	//return ((TechnicsRouteListMasterInfo) getMaster()).getRouteListNumber();
        return ((TechnicsRouteListMasterInfo) getMaster()).getRouteListName();
      //SSEnd SS1
        //CR3 end
    }

    /**
     * ���ù���·�߱�����ƣ���Ҫ���зǿռ��
     * @param name String ����·�߱������
     * @throws QMException 
     * @throws TechnicsRouteException
     */
    public void setRouteListName(String name) throws QMException
    {
        if(name == null || name.trim().length() == 0)
        {
            throw new QMException("1", null,null);
        }
        ((TechnicsRouteListMasterInfo)getMaster()).setRouteListName(name);
        this.name = name;
    }

    /**
     *��� ·�߱�˵��
     * @return String ·�߱�˵��
     */
    public String getRouteListDescription()
    {
        return description;
    }

    /**
     * ����·�߱�˵��
     * @param description ·�߱�˵��
     */
    public void setRouteListDescription(String description)
    {
        this.description = description;
    }

    /**
     * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
     * @return String
     */
    public String getRouteListLevel()
    {
        return level;
    }

    /**
     * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
     * @param level ·�߼�����һ��·�߻��Ƕ���·�ߣ�
     */
    public void setRouteListLevel(String level)
    {
        this.level = level;
    }

    /**
     * ��ù���·�߱��״̬
     * @return String ����·�߱��״̬
     */
    public String getRouteListState()
    {
        return state;
    }

    /**
     * ���ù���·�߱��״̬
     * @param state ����·�߱��״̬
     */
    public void setRouteListState(String state)
    {
        this.state = state;
    }

    /**
     * ��ö���·�߱�ĵ�λID.
     * @return String ����·�߱�ĵ�λID.
     */
    public String getRouteListDepartment()
    {
        return department;
    }

    /**
     * ���ö���·�߱�ĵ�λID.
     * @param department - ����·�߱�ĵ�λID.
     */
    public void setRouteListDepartment(String department)
    {
        this.department = department;
    }

    /**
     * ��ö���·�߱�ĵ�λ����.
     * @return String ����·�߱�ĵ�λ����.
     */
    public String getDepartmentName()
    {
        return departmentName;
    }

    /**
     * ���ö���·�߱�ĵ�λ����.�˷�����EJB���á�
     * @param department - ����·�߱�ĵ�λ����.
     */
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     * @return String ����·�߱��Ӧ�Ĳ�ƷID.
     */
    public String getProductMasterID()
    {
        return productMasterID;
    }

    /**
     * ���ù���·�߱��Ӧ�Ĳ�ƷID.
     * @param productMasterID - �㲿��ID.
     */
    public void setProductMasterID(String productMasterID)
    {
        ((TechnicsRouteListMasterInfo)getMaster()).setProductMasterID(productMasterID);
        this.productMasterID = productMasterID;
    }

    //begin CR1
    /**
     * �����ʾ��ʶ
     */
    public String getIdentity()
    {
        return this.getRouteListNumber() + "(" + this.getRouteListName() + ")" + this.getVersionValue();
    }

    //end CR1

    //begin CR2
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
    //end CR2
}
