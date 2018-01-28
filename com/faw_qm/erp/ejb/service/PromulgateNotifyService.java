/**
 * ���ɳ���PromulgateNotifyService.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.ejb.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.model.StreamDataInfo;
import com.faw_qm.erp.model.PromulgateNotifyIfc;
import com.faw_qm.erp.model.PromulgateNotifyInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: ����֪ͨ�����ӿ�</p>
 * <p>Description: ����֪ͨ�����ӿ�</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public interface PromulgateNotifyService extends BaseService
{
    /**
     * ���ݲ���������Ʒ
     * @param bsoid String
     * @return Collection
     */
    public Collection getProductsByProID(String bsoid) throws QMException;

    /**
     * ��������֪ͨ
     *
     * @param aInfo PromulgateNotifyInfo
     * @param aPartList ArrayList
     * @return PromulgateNotifyInfo
     */
    public PromulgateNotifyIfc createPromulgateNotify(
            PromulgateNotifyInfo newinfo, ArrayList aPartList, ArrayList doclist)
            throws QMException;
    
    /**
     * ��������֪ͨ
     * @param newinfo PromulgateNotifyInfo
     * @param Products ArrayList
     * @throws QMException
     * @return PromulgateNotifyIfc
     */
    public PromulgateNotifyIfc createPromulgateNotify(
            PromulgateNotifyInfo newinfo, ArrayList aPartList, ArrayList docs,boolean flag)
            throws QMException;

    /**
     * ͨ������������
     * @param num String
     * @throws QMException
     * @return Vector
     */
    public Vector searchPartByProNumber(String num) throws QMException;

    /**
     * ���²���֪ͨ
     *
     * @param updateBsoid String
     * @param aPartList ArrayList
     */
    public void updatePromulgateNotify(String updateBsoid,
            ArrayList productList, ArrayList docList, ArrayList partList)
            throws QMException;

    public Collection searchPromulgateNotify(String name, String checkboxnName,
            String num, String checkboxnNum, String Flag, String checkboxFlag,
            String textcreator, String checkboxcreator) throws QMException;

    /**
     * ͨ������id���������ĵ�
     * @param id String
     * @return Collection
     */
    public Collection getDocsByProId(String id) throws QMException;

    /**
     * ɾ������֪ͨ
     * @param id String
     * @throws QMException
     */
    public void deletePromulgateNotify(String id) throws QMException;

    /**
     * ��ù������
     * @param id String
     * @throws QMException
     * @return Vector
     */
    public Vector getPartsByProId(String id) throws QMException;

    /**
     * ����master��ʶ������
     * @return Collection
     */
    public ArrayList getAllPartsByMaster(String num, String name)
            throws QMException;

    /**
     * ��ù������
     * @param id String
     * @throws QMException
     * @return Vector
     */
    public Collection getPartsByProId(PromulgateNotifyIfc info)
            throws QMException;

    /**
     * �������ȡ���������
     * @return ArrayList
     */
    public ArrayList[] obtainDataForChange(BaseValueIfc changeRequest)
            throws QMException;

    /**
     * ���ݷ����������ļ���
     * @param base BaseValueIfc
     * @throws QMException
     * @return String
     */
    public String getFileNameByNotice(BaseValueIfc base) throws QMException;

    /**
     * Ϊ���������Ӹ���,����������
     * @param contentHolder ContentHolderIfc
     * @param file File
     * @throws QMException
     */
    public void setContentForChangeRequest(ContentHolderIfc contentHolder,
            File file, StreamDataInfo streamDataInfo) throws QMException;

    /**
     * ɾ������������
     * @param partid String:�㲿����BsoID
     */
    public void deletePartLink(String partid) throws QMException;

    /**
     * ɾ��������Ʒ��
     * @param productid String:�㲿��Master��BsoID
     * @throws QMException
     */
    public void deleteProductLink(String productid) throws QMException;

    /**
     * ɾ�������ĵ���
     * @param productid String:�ĵ���BsoID
     * @throws QMException
     */
    public void deleteDocLink(String docID) throws QMException;
}
