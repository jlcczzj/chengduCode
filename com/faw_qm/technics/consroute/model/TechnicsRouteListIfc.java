/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/22 �촺Ӣ       ԭ����׼����Ԥ������
 */

package com.faw_qm.technics.consroute.model;

import java.util.Vector;

import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.framework.exceptions.QMException;

/**
 * ����Ϊ����ʹ�����Ҳ���������ʹ�ã�Ϊ����Map. ������֮ͬ��
 * �������Լ�ejb.
 */

/**
 * ����·�߱�ֵ����ӿ� <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface TechnicsRouteListIfc extends RevisionControlledIfc
{
    /**
     * ����·�߱�ı�ţ�Ψһ��ʶ��
     * @return java.lang.String
     */
    public String getRouteListNumber();

    /**
     * ����·�߱�ı�ţ�Ψһ��ʶ
     * @param number - �ַ����Ȳ�����30
     * @throws QMException 
     */
    public void setRouteListNumber(String number) throws QMException;

    /**
     * �õ��첿����˳��
     */
    public Vector getPartIndex();

    /**
     * �����㲿������ѫ
     */
    public void setPartIndex(Vector partIndex);

    /**
     * ����·�߱�����ƣ���Ҫ���зǿռ��
     * @return java.lang.String
     */
    public String getRouteListName();

    /**
     * ����·�߱�����ƣ���Ҫ���зǿռ��
     * @param name
     * @throws QMException 
     */
    public void setRouteListName(String name) throws QMException;

    /**
     * ·�߱�˵��
     * @return java.lang.String
     */
    public String getRouteListDescription();

    /**
     * ·�߱�˵��
     * @param description
     */
    public void setRouteListDescription(String description);

    /**
     * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
     * @return java.lang.String
     */
    public String getRouteListLevel();

    /**
     * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
     * @param level
     */
    public void setRouteListLevel(String level);

    /**
     * ����·�߱��״̬ ǰ׼����׼����׼����׼�ȣ��ڴ��������ά��
     * @return java.lang.String
     */
    public String getRouteListState();

    /**
     * ���ù���·�߱��״̬
     * @param state
     */
    public void setRouteListState(String state);

    /**
     * ����·�߱�ĵ�λ����ID
     * @return java.lang.String
     */
    public String getRouteListDepartment();

    /**
     * ����·�߱�ĵ�λ����ID
     * @param department - ����·�߱�ĵ�λ����ID
     */
    public void setRouteListDepartment(String department);

    /**
     * ����·�߱�ĵ�λ����.
     * @return java.lang.String
     */
    public String getDepartmentName();

    /**
     * ����·�߱�ĵ�λ����.�˷�����EJB���á�
     * @param department - ����·�߱�ĵ�λ����.
     */
    public void setDepartmentName(String departmentName);

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     * @return java.lang.String
     */
    public String getProductMasterID();

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     * @param productMasterID - �㲿��ID.
     */
    public void setProductMasterID(String productMasterID);

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
}
