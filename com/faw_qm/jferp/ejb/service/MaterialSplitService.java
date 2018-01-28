/**
 * ���ɳ���MaterialSplitService.java	1.0              2007-10-7
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.ejb.service;

import java.util.Collection;
import java.util.Vector;
import java.util.HashMap;
import java.util.List;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.jferp.model.MaterialSplitIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;

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
		 public void publishPartsToERP(Collection coll ,String routeID,HashMap scbbMap,Collection vec);
	 /**
     * ������ϣ�����ERP����ר��
     * @param coll �㲿���ļ���
     * @param baselineiD �㲿����ŵĻ���
     * @param isPublishRoute �Ƿ���·�߷���
     * @param routeID ������·��
     * @throws QMException
     */

	public Vector split(Collection coll, String lx, String routeID)throws QMException;
/*
     * ������ϡ�
     * @param partBsoIDs �ԡ�;��Ϊ�ָ������㲿��BsoID���ϡ�
     * @param routes ��Ҫ��ֵ�·�ߡ�
     * @param doSplit Ϊ����������ɰ汾�Ѳ��Ϊ���ϣ����β���Ƿ����²�֡������׼����boolean������true�����²�֣�false�������²�֡�
     * @throws QMException
     */
//    public void split(String partBsoIDs, String routes, boolean doSplit)
//            throws QMException;
    //20080103 end

//    /**
//     * ��������Ż�ȡ���ϡ�Ϊ�ݿͻ���ʾʹ�á�
//     * @param partNumberList ����ż��ϡ�
//     * @return ����Map������һ�����Ϲ�����Ϣ���飻ֵ��������Ϣ���鼯�ϡ�
//     * @throws QMException
//     */
//    public HashMap getAllMaterial(List partNumberList) throws QMException;

    /**
     * ��������ż��ϵ��ַ�����ʽ������ʱ���ϼ�¼��
     * @param partNumberString ����ż��ϵ��ַ�����ʽ��p01;p02
     * @throws QMException
     */
    public void createInterimMaterial(String partNumberString)
            throws QMException;

    /**
     * ��������Ż�ȡ���ϡ�Ϊ�ݿͻ���ʾʹ�á�
     * @param partNumberList ����ż��ϡ�
     * @return ����Map������һ�����Ϲ�����Ϣ���飻ֵ��������Ϣ���鼯�ϡ�
     * @throws QMException
     */
    public HashMap getAllInterimMaterial(String partNumber) throws QMException;

    /**
     * ֻɾ�ṹ��ϵ�������±�ʶ����Ϊ��D����
     * @param parentPartNumber �����š�
     * @param parentNumber �����Ϻš�
     * @param childBsoID ��ɾ������ʱ���¼��bsoID��
     * @throws QMException 
     */
    public void delete(String parentPartNumber, String parentNumber,
            String childBsoID) throws QMException;

    /**
     * ֻ�Ľṹ��ϵ�е������Ϻš��㼶���㼶״̬�����䡣��Ҫ���滻�������ϵ���Ϣ��ӵ���ʱ���С�
     * ��Ҫ�����š������Ϻţ���ʱ��������bsoID���滻���ϵ�bsoID��
     * xiebͬ���ж����ͬ�Ӽ�ʱ��ΰ죿����������
     * @param parentPartNumber �����š�
     * @param parentNumber �����Ϻš�
     * @param childBsoID ��ʱ��������bsoID��
     * @param replaceBsoID �滻���ϵ�bsoID��
     * @throws QMException 
     */
    public void replace(String parentPartNumber, String parentNumber,
            String childBsoID, String replaceBsoID) throws QMException;

    /**
     * ��ʽִ�и��¡���ҪУ�����Ϻŵ�Ψһ�ԡ�
     * @param updateMap ������ʱ��bsoID��ֵ���޸ĵ����Ϻš�
     * @throws QMException
     */
    public void updateMaterial(HashMap updateMap) throws QMException;

    /**
     * ɾ��������ʱ���е����ݡ�
     * @throws QMException
     */
    public void deleteAllInterimData() throws QMException;

    /**
     * ��������Ż�ȡ���ϡ�
     * @param partNumber ����š� 
     * @return ���ϼ��ϡ�
     * @throws QMException
     */
    public List getAllInterimMSplit(String partNumber) throws QMException;

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
//    public List getMaterialByPro(String processRC, QMPartIfc partIfc,
//            List stepNumberList, List stepRouteCodeList) throws QMException;

//    /**
//     * ���ݹ���������������ϡ�
//     * @param partNumber���㲿�����
//     * @param processRC�����չ�̵Ĳ��Ŵ���
//     * @return List����һ��Ԫ����HashMap��key���㲿���ı�ţ�value���㲿����Ӧ������
//     *               �ڶ���Ԫ���Ǹ��㲿����Ӧ�������Ƿ������ͷ���ı�־������ΪBoolean
//     * @throws QMException
//     */
//    public List getMaterialByStep(String partNumber, String processRC)
//            throws QMException;

    /**
     * �����������ݡ�
     * @param parts
     * @throws QMException
     */
    public void publicTechnics(String parts) throws Exception;

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
	//20080123 end
	
	//20081205 zhangq begin
	/**
	 * �����㲿���ı�ź�·�ߴ�������֪ͨ�飬Ȼ�������������֪ͨ�������ϣ��������ɰ���������
	 * @param partNumber
	 * @param route
	 * @throws QMException
	 */
	//public void publishMaterial(String partNumber,String route)throws QMException;
	
    /**
     * ��ȡĬ�ϵ�·�����Զ��塣
     * @return ·�����Զ��������������ļ��ϡ�
     * @throws QMException 
     */
    public List getDefaultRouteDefList() throws QMException;
	//20081205 zhangq end
    
    /**
     * ��·���е�·�ߴ���ת��ΪList,���Ҹ��������ļ�������ȥ��·���е�������롣
     * @param routeStr
     * @return
     */
    public List getRouteCodeList(String routeStr);
    
    public Collection getPartByRouteList(TechnicsRouteListIfc list)
        throws QMException;
    
    /**
	 * ���������
	 * @param coll �㲿���ļ���
	 * @param baselineiD �㲿����ŵĻ���
	 * @param lx �������ͣ���1������bom��������2��·�߷���
	 * @param routeID ������·��
	 * @throws QMException
	 */
	 public void publishvirtualPartsToERP(Collection coll )
        throws Exception;
    
}
