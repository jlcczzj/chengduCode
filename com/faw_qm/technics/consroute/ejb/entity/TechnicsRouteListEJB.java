/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/22 �촺Ӣ      ԭ��:����Ԥ������
 * CR2 ����  ��������ﲻ����������
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.util.Vector;

import javax.ejb.CreateException;

import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.enterprise.ejb.entity.RevisionControlledEJB;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.util.EJBServiceHelper;

/**
 * ����·�߷������,�ṩ·�߱�Ļ���������Ϣ
 * @author ������
 * @version 1.0
 */
public abstract class TechnicsRouteListEJB extends RevisionControlledEJB
{

    public TechnicsRouteListEJB()
    {

    }

    //   * ����·�߱�ı�ţ�Ψһ��ʶ����Ҫ���зǿռ�顣
    //   * ����·�߱��а汾����ŵ�Ψһ���ڶ�Ӧ��Master�б�֤��

    /**
     * ��ù���·�߱���
     * @return String ����·�߱���
     */
    public abstract java.lang.String getRouteListNumber();

    //  * ����·�߱�ı�ţ�Ψһ��ʶ������·�߱��а汾����ŵ�Ψһ���ڶ�Ӧ��Master�б�֤��

    /**
     * ���ù���·�߱���
     * @param number String ����·�߱���
     */
    public abstract void setRouteListNumber(String number);

    //   * ����·�߱�����ƣ���Ҫ���зǿռ�顣

    /**
     * ��ù���·�߱������
     * @return String ����·�߱������
     */
    public abstract java.lang.String getRouteListName();

    //  * ����·�߱�����ƣ���Ҫ���зǿռ��

    /**
     * ���ù���·�߱������
     * @param name String ����·�߱������
     */
    public abstract void setRouteListName(String name);

    /**
     * ���·�߱�˵��
     * @return String ·�߱�˵��
     */
    public abstract java.lang.String getRouteListDescription();

    /**
     * ����·�߱�˵��
     * @param description String ·�߱�˵��
     */

    public abstract void setRouteListDescription(String description);

    /**
     * ���·�߱���(һ��·�߻��Ƕ���·��) ֵȡ��ö�ټ�(һ��������).
     * @return String ·�߱���
     */
    public abstract java.lang.String getRouteListLevel();

    /**
     * ����·�߱���(һ��·�߻��Ƕ���·��) ֵȡ��ö�ټ�(һ��������).
     * @return String ·�߱���
     */

    public abstract void setRouteListLevel(String level);

    /**
     * ���·�߱�״̬
     * @return String ·�߱�״̬
     */
    public abstract java.lang.String getRouteListState();

    /**
     * ����·�߱�״̬
     * @return String ·�߱�״̬
     */

    public abstract void setRouteListState(String state);

    /**
     * ��ö���·�߱�ĵ�λ��ʶ
     * @return String ����·�߱�ĵ�λ��ʶ
     */
    public abstract java.lang.String getRouteListDepartment();

    /**
     * ��ö���·�߱�ĵ�λ��ʶ
     * @return String ����·�߱�ĵ�λ��ʶ
     */

    public abstract void setRouteListDepartment(String department);

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     * @return String ����·�߱��Ӧ�Ĳ�ƷID.
     */
    public abstract java.lang.String getProductMasterID();

    /**
     * ���ù���·�߱��Ӧ�Ĳ�ƷID.
     * @return String ����·�߱��Ӧ�Ĳ�ƷID.
     */

    public abstract void setProductMasterID(String productMasterID);

    /**
     * �õ��㲿��������
     * @return Vector �㲿��������
     */
    public abstract Vector getPartIndex();

    /**
     * �����㲿��������
     * @return Vector �㲿��������
     */

    public abstract void setPartIndex(Vector partIndex);

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

    /**
     * ���ҵ���������
     * @return String TechnicsRouteList
     */
    public String getBsoName()
    {
        return "consTechnicsRouteList";
    }

    /**
     * ���ֵ����
     * @throws QMException
     * @return BaseValueIfc
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        TechnicsRouteListInfo info = new TechnicsRouteListInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * ���½�ֵ����������á�
     * @param info BaseValueIfc Ҫ���õ�ֵ����
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo)info;
        //CR2 begin
//        listinfo.setRouteListName(this.getRouteListName());
//        listinfo.setRouteListNumber(this.getRouteListNumber());
        //CR2 end
        listinfo.setRouteListDescription(this.getRouteListDescription());
        listinfo.setRouteListDepartment(this.getRouteListDepartment());
        if(this.getRouteListDepartment() != null)
        {
            listinfo.setDepartmentName(getDepartmentName(this.getRouteListDepartment()));
        }else
        {
            listinfo.setDepartmentName(getFirstDepartmentID());
            //throw new TechnicsRouteException("��λID��Ӧ��Ϊ�ա�");
        }
        //����ö�����ֵ��
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("TechnicsRouteListEJB's setValueInfo RouteListLevelType.display = " + this.getRouteListLevel());
        }
        listinfo.setRouteListLevel(RouteListLevelType.toRouteListLevelType(this.getRouteListLevel()).getDisplay());
        listinfo.setRouteListState(this.getRouteListState());
        listinfo.setProductMasterID(this.getProductMasterID());
        listinfo.setPartIndex(this.getPartIndex());
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        if(this.getMasterBsoID() == null)
        {
            throw new QMException("MasterBsoID��ӦΪ�ա�");
        }
        MasterIfc masterInfo = (MasterIfc)pservice.refreshInfo(this.getMasterBsoID());
        listinfo.setMaster(masterInfo);
        //begin CR1
        listinfo.setAttribute1(this.getAttribute1());
        listinfo.setAttribute2(this.getAttribute2());
        //end CR1
    }

    private String getFirstDepartmentID()
    {
        return null;
    }

    /**
     * ��õ�λ���ơ�
     * @param departmentID String ��λID
     * @throws QMException
     * @return String ��λ����
     * @throws QMException 
     */
    private String getDepartmentName(String departmentID) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        BaseValueIfc codeInfo = pservice.refreshInfo(departmentID);
        String departmentName = null;
        if(codeInfo instanceof CodingIfc)
        {
            departmentName = ((CodingIfc)codeInfo).getCodeContent();
        }
        if(codeInfo instanceof CodingClassificationIfc)
        {
            departmentName = ((CodingClassificationIfc)codeInfo).getCodeSort();
        }
        return departmentName;
    }

    /**
     * ����ֵ������г־û���
     * @param info BaseValueIfc ֵ����
     * @throws CreateException 
     * @throws CreateException
     * @see BaseValueInfo
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter TechnicsRouteListEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo)info;
        //CR2 begin
//        this.setRouteListName(listinfo.getRouteListName());
//        this.setRouteListNumber(listinfo.getRouteListNumber());
        //CR2 end
        this.setRouteListDescription(listinfo.getRouteListDescription());
        this.setRouteListDepartment(listinfo.getRouteListDepartment());
        String value = RouteListLevelType.getValue(listinfo.getRouteListLevel());
        this.setRouteListLevel(value);
        this.setPartIndex(listinfo.getPartIndex());
        this.setRouteListState(listinfo.getRouteListState());
        this.setProductMasterID(listinfo.getProductMasterID());
        //begin CR1
        this.setAttribute1(listinfo.getAttribute1());
        this.setAttribute2(listinfo.getAttribute2());
        //end CR1
    }

    /**
     * ����ֵ������и���
     * @param info BaseValueIfc ֵ����
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter TechnicsRouteListEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo)info;
        //CR2 begin
//        this.setRouteListName(listinfo.getRouteListName());
//        this.setRouteListNumber(listinfo.getRouteListNumber());
        //CR2 end
        this.setRouteListDescription(listinfo.getRouteListDescription());
        this.setRouteListDepartment(listinfo.getRouteListDepartment());
        this.setRouteListLevel(RouteHelper.getValue(RouteListLevelType.getRouteListLevelTypeSet(), listinfo.getRouteListLevel()));
        this.setRouteListState(listinfo.getRouteListState());
        this.setPartIndex(listinfo.getPartIndex());
        this.setProductMasterID(listinfo.getProductMasterID());
        //begin CR1
        this.setAttribute1(listinfo.getAttribute1());
        this.setAttribute2(listinfo.getAttribute2());
        //end CR1
    }
}
