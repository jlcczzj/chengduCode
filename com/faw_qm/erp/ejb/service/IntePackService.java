/**
 * ���ɳ���IntePackService.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.ejb.service;

import java.util.Collection;
import java.util.Vector;

import com.faw_qm.erp.exception.IntePackException;
import com.faw_qm.erp.model.IntePackIfc;
import com.faw_qm.erp.model.IntePackInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;

/**
 * <p>Title: ���ɰ�����ӿڡ�</p>
 * <p>Description: ���ɰ�����ӿڡ�</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public interface IntePackService extends BaseService
{
    /**
     * �������ɰ�
     * @param intePackInfo IntePackInfo
     * @return IntePackInfo
     */
    public IntePackIfc createIntePack(IntePackIfc intePackInfo)
            throws QMException;

    /**
     * ɾ�����ɰ�
     * @param intePackIfc IntePackIfc
     * @throws QMException
     */
    public void deleteIntePack(IntePackIfc intePackIfc) throws QMException;

    /**
     * ɾ�����ɰ�
     * @param id String
     * @throws QMException
     */
    public void deleteIntePack(String id) throws QMException;

    /**
     * ���ݼ��ɰ������㲿��
     * @param bsoid String
     * @return Collection
     */
    public Collection getProductsByIntePackID(String bsoid) throws QMException;

    /**
     * �жϵ�ǰ�û��Ƿ���Ȩ�޸Ĵ��ĵ�
     * ����Ը��ĵ���QMPermission.MODIFYȨ�ޣ����� false
     * �����ǰ�û�����AdministratorȨ�ޣ����� true
     * @param intePackInfo �ĵ�ֵ����
     * @return true(��Ȩ��) or false(��Ȩ��)
     * @throws AdoptNoticeException
     */
    public boolean isIntePackPublish(IntePackInfo intePackInfo)
            throws IntePackException;
    
    /**
     * ����ERP���ݷ���
     * @param id ���ݼ��ɰ���ʶ
     * @param xmlName ����XML�ļ�������
     * @return IntePackInfo
     * @throws QMExcepiton
     */
    public IntePackInfo publishIntePack(String id ,String xmlName,Collection coll)throws QMException;
    /**
     * ����ERP���ݷ���
     * added by dikefeng 20090422��Ϊ��ʹ���ڷ�����ʱ��֪�������·������϶�����Щ����Ҫ������ѹ��˺������嵥����һ��
     * @param id ���ݼ��ɰ���ʶ
     * @param xmlName ����XML�ļ�������
     * @return IntePackInfo
     * @throws QMExcepiton
     */
    public IntePackInfo publishIntePack(String id ,String xmlName,Collection coll,Vector filterParts)throws QMException;
    /**
     * �������ɰ�����ʱûд
     * @param intePackInfo IntePackInfo
     * @throws QMException
     * @return IntePackInfo
     */
    public IntePackInfo publishIntePack(IntePackInfo intePackInfo)
            throws QMException;

    /**
     * �������ɰ�
     * @param id String
     * @throws QMException
     * @return IntePackInfo
     */
    public IntePackInfo publishIntePack(String id) throws QMException;

    /**
     * �������ɰ�
     * @param id String
     * @throws QMException
     * @return IntePackInfo
     */
    public IntePackInfo searchIntePackByID(String id) throws QMException;

    /**
     * �������ɰ�
     * @param name String:���ɰ�������
     * @param sourceType String�����ɰ�����Դ����
     * @param source String�����ɰ�����Դ
     * @param state String�����ɰ���״̬
     * @param creator String�����ɰ��Ĵ�����
     * @param createTime String�����ɰ��Ĵ���ʱ��
     * @param publisher String�����ɰ��ķ�����
     * @param publishTime String�����ɰ��ķ���ʱ��
     * @throws QMException
     * @return Collection����ѯ�������
     */
    public Collection searchIntePackByID(String name, String sourceType,
            String source, String state, String creator, String createTime,
            String publisher, String publishTime) throws QMException;

    /**
     * �������ɰ�
     * @param name String:���ɰ�������
     * @param checkboxName String�����ƵĲ�ѯ���ͣ��������߲�����
     * @param sourceType String�����ɰ�����Դ����
     * @param checkboxSourceType String����Դ���͵Ĳ�ѯ���ͣ��������߲�����
     * @param source String�����ɰ�����Դ
     * @param checkboxSource String����Դ�Ĳ�ѯ���ͣ��������߲�����
     * @param state String�����ɰ���״̬
     * @param checkboxState String��״̬�Ĳ�ѯ���ͣ��������߲�����
     * @param creator String�����ɰ��Ĵ�����
     * @param checkboxCreator String�������ߵĲ�ѯ���ͣ��������߲�����
     * @param createTime String�����ɰ��Ĵ���ʱ��
     * @param checkboxCreateTime String������ʱ��Ĳ�ѯ���ͣ��������߲�����
     * @param publisher String�����ɰ��ķ�����
     * @param checkboxPublisher String�������ߵĲ�ѯ���ͣ��������߲�����
     * @param publishTime String�����ɰ��ķ���ʱ��
     * @param checkboxPublishTime String������ʱ��Ĳ�ѯ���ͣ��������߲�����
     * @throws QMException
     * @return Collection����ѯ�������
     */
    public Collection searchIntePackByID(String name, String checkboxName,
            String sourceType, String checkboxSourceType, String source,
            String checkboxSource, String state, String checkboxState,
            String creator, String checkboxCreator, String createTime,
            String checkboxCreateTime, String publisher,
            String checkboxPublisher, String publishTime,
            String checkboxPublishTime) throws QMException;

    /**
     * �����ص����Ͻṹ��
     * @param parentNumber:�����Ϻ�
     * @param partNumber:������
     * @param partVersionID:�����汾
     * @return ��ص����Ͻṹ
     * @throws QMException
     */
    public Collection getStructureLinkMap(String parentNumber,
            String partNumber, String partVersionID) throws QMException;
    
    /**
     * �������ɰ��в��ò����Ĺ��ա�
     * @param id String
     * @throws QMException
     * @return IntePackInfo
     */
    public void publishTechByIntePack(String intePackID) throws QMException;
    
}
