/**
 * ���ɳ���TechnicDataService.java	1.0              2007-10-31
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.erp.ejb.service;

import com.faw_qm.framework.service.BaseService;
import com.faw_qm.capp.model.QMTechnicsIfc;
import java.util.Collection;
import com.faw_qm.framework.exceptions.QMException;
import java.util.ArrayList;


/**
 * <p>Title: �������ݷ���ӿڡ�</p>
 * <p>Description: �������ݷ���ӿڡ�</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author �촺Ӣ
 * @version 1.0
 */
public interface TechnicDataService extends BaseService
{
    /**
     * ͨ������ֵ�����øù���ʹ�õ��㲿������
     * @param technic ����ֵ����
     * @throws QMException
     * @return Collection
     */
    public Collection getPartsByTechnics(QMTechnicsIfc technic)
            throws QMException;


    /**
     * ���ݹ���ID�����ʹ�õĹ��򼯺�
     * @param bsoID String
     * @throws QMException
     * @return Collection
     */
    public Collection browseProceduresByTechnics(String bsoID)
            throws
            QMException;


    /**
     * �ɹ��տ�id�ҵ�����ʹ�ò��ϵĹ���
     * @param tech techBsoID ����id
     * @throws CappException
     * @return Collection ����ʹ�ò��ϵĹ����ļ���
     */
    public Collection findTechnicsMaterialsLinkByTech(String techBsoID)
            throws
            QMException;


    /**
     * �������µĹ�����Ϣд�뵽QMXMLProcess��
     * @param bsoID String
     * @throws QMException
     * @return ArrayList
     */
    public ArrayList getTechnicsData(String technicbsoID)
            throws
            QMException;


}
