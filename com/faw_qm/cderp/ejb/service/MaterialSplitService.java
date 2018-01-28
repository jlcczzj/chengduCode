/**
 * ���ɳ���MaterialSplitService.java	1.0              
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.ejb.service;

import java.util.Collection;
import java.util.Vector;
import java.util.HashMap;
import java.util.List;
import com.faw_qm.cderp.exception.QMXMLException;
import com.faw_qm.cderp.model.MaterialSplitIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;

/**
 * <p>Title: ���ϲ�ַ���ӿڡ�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 * SS1 ���BOM����������õ��������� ������ 2013-06-12
 * SS2 �޸ķ���ʱдXML���ݴ�������� ��� 2014-02-19
 */
public interface MaterialSplitService extends BaseService
{
	
	/**
	 * �������
	 * @param coll �㲿���ļ���
	 * @param baselineiD �㲿����ŵĻ���
	 * @param lx �������ͣ���1������bom��������2��·�߷���
	 * @param routeID ������·��
	 * @throws QMException
	 */
	 public void publishPartsToERPlc(Collection coll,String routeID)
        throws Exception;
	 /**
		 * �����㲿��
		 * @param coll �㲿���ļ���
		 * @param baselineiD �㲿����ŵĻ���
		 * @param lx �������ͣ���1������bom��������2��·�߷���
		 * @param routeID ������·��
		 * @throws QMException
		 */
		 public void publishPartsToERP(Collection coll ,String routeID,Collection vec,int i);
	 /**
     * ������ϣ�����ERP����ר��
     * @param coll �㲿���ļ���
     * @param baselineiD �㲿����ŵĻ���
     * @param isPublishRoute �Ƿ���·�߷���
     * @param routeID ������·��
     * @throws QMException
     */

	public Vector split(Collection coll, String lx, String routeID)throws QMException;

    
    /**
     * �������ϺŻ�ȡ���ϡ�
     * @param materialNumber ���Ϻš�
     * @return ���ϡ�
     * @throws QMException
     */
    public MaterialSplitIfc getMSplit(String materialNumber) throws QMException;

    /**
     * ��������Ż�ȡ�������ϡ�
     * @param partNumber ����š�
     * @return �������ϼ��ϡ�
     * @throws QMException
     */
    public List getRootMSplit(String partNumber,String partVersion) throws QMException;

    /**
     * ����ָ���ĸ����ź͸����Ϻ��������ҵ��ṹ�������з��������Ľṹ��Ϣ��
     * @param parentPartNumber �����š�
     * @param parentNumber �����Ϻš�
     * @return �ṹ��Ϣ��
     * @throws QMException
     */
    public List getMStructure(String parentPartNumber, String parentNumber)
            throws QMException;

    /**
     * �����㲿����źͲ��Ŵ��롢�Ƿ������ͷ����־��ȡ��Ӧ�����ϡ�
     * 
     * @param processRC�����չ�̵Ĳ��Ŵ���
     * @param partIfc���㲿��ֵ����
     * @param stepNumberList������ı��
     * @param stepRCList��������빤�չ�̲��Ŵ��벻ͬ�Ĳ��Ŵ���
     * @return List��ע�⣬���һ��Ԫ���Ǹ��㲿����Ӧ�������Ƿ������ͷ���ı�־������ΪBoolean
     * @throws QMException
     */

    /**
     * ���ݸ������������Գ�����,����ض�iba����ֵ��
     * return String Ϊ��iba���Ե�ֵ��
     * @throws QMXMLException 
     */
    public String getPartIBA(QMPartIfc partIfc, String ibaDisplayName)
            throws QMXMLException;
    

    //20080103 begin
    /**
     * �����ݿͻ��˴��ݵ��ԡ�;����Ϊ�ָ������㲿��BsoID�ַ�����ȡ���е��㲿�����ϡ�
     * @param partBsoIDs �㲿��BsoID�ַ�����
     * @return �㲿�����ϡ�
     * @throws QMException 
     */
    public List getAllPartByBsoID(String partBsoIDs) throws QMException;

    /**
     * ��ȡ·�����Զ��塣
     * @return ·�����Զ��������������ļ��ϡ�
     * @throws QMException 
     */
    public List getRouteDefList() throws QMException;
    //20080103 end
    
    //20080110 begin
//    /**
//     * �����㲿����źͲ��Ŵ��롢���յ������ȡ��Ӧ�����ϡ�
//     * @param partNumber���㲿�����
//     * @param stepRC������Ĳ��Ŵ���
//     * @param processType�����յ�����
//     * @return List��ע�⣬���һ��Ԫ���Ǹ��㲿����Ӧ�������Ƿ������ͷ���ı�־������ΪBoolean
//     * @throws QMException
//     */
//    public List getMaterialByStep(String partNumber, String stepRC,String processType)
//            throws QMException;
    //20080110 end
    //20080123 begin
    /**
	 * �����Ŵ���Ӻ���ת��Ϊ��ơ�
	 * @param tempRouteCodes��·�ߣ���dashDelimiter��Ϊ�ָ�����
	 */
	public String changeRoute(String routeCodes) throws QMException;

    
    /**
     * ��·���е�·�ߴ���ת��ΪList,���Ҹ��������ļ�������ȥ��·���е�������롣
     * @param routeStr
     * @return
     */
    public List getRouteCodeList(String routeStr);
    
    public Collection getPartByRouteList(TechnicsRouteListIfc list)
        throws QMException;
    
  
    
}
