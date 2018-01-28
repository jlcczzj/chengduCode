/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 TD8290 ����BOM����������  ���� 2014-7-17
 * SS2 Bom���� xianglx 2014-9-10
 * SS3 ����BOM����������ӽ����Ʊ�����������Ӧ���ݵĲ��úͲ����ü� xianglx 2014-8-28
 * SS4 �鿴BOM�汾���߼���������û���顢����ʾ����ɹ���Ӧ֧�ְ�����/�༶���� xianglx 2014-9-28
 * SS5 �Զ�����BOM������ lishu 2017-5-12
 * SS6 ����㲿���Ƿ���ڣ������Ƿ��гɶ�·�� ����� 2017-12-30
*/

package com.faw_qm.gybomNotice.ejb.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.faw_qm.bomNotice.model.BomAdoptNoticeIfc;
import com.faw_qm.bomNotice.model.BomAdoptNoticeInfo;
import com.faw_qm.common.cipjava.booleandict;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.lock.model.LockIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;



/**
 * <p>Title: �����������ࡣ</p> <p>Description: </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ����
 * @version 1.0
 */

public interface GYBomNoticeService extends BaseService
{


    /**
     * ����������
     * @param Object[] data ���ݼ���
     * @return Object[]
     */
    public Object[] createGYBomAdoptNotice(Object[] data);

    /**
     * �����������
     * @param Object[] param
     * @throws QMException
     * @return Collection
     */
   public Collection findMultPartsByNumbers(Object[] param) throws QMException;
	 /**
    * ���õ����ü�����Ӽ�
    * @param QMPartInfo[] QMPartInfos
    * @throws QMException
    * @return Object[] 
    */
   public Object[] replaceBomAdoptNoticePart(QMPartInfo[] QMPartInfos,String selectAdoptPart,String[] usageCount) throws QMException;
	  /**
    * �����Ӳ��õ�
    * @param Object[] data ���ݼ���
    * @return Object[]
    */

	public Object[] createSubBomAdoptNotice(Object[] data) throws QMException;

    /**
     * �����Ӳ��õ�������Ӳ��õ�
     * @return Collection ���õ�����
     * @param BomAdoptNoticeInfo info ���õ�����
     * @throws QMException
     */
    public Collection getSubGYBomAdoptNotice(GYBomAdoptNoticeInfo info)throws QMException;
    /**
     * �����õ�����
     * @param list
     * @param locker
     * @throws QMException
     */
    public void unlock(LockIfc list, String locker) throws QMException;
    /**
     * �����õ�����
     * @param list
     * @param locker
     * @throws QMException
     */
    public LockIfc lock(LockIfc list, String locker) throws QMException;
	 /**
     * ɾ������BOM
     * @param BomAdoptNoticeInfo list
     */
    public void deleteGYBomAdoptNotice(GYBomAdoptNoticeInfo notice) throws QMException;
    /**
     * ���������ò��õ�����������ɾ���źż�����
     * @return Collection ���õ������������
     * @param QMPartInfo info �������
     * @throws QMException
     */
    public Collection getPartsFromBomSubAdoptNoticeLink(QMPartInfo info)throws QMException;
	
	  /**
     * �����Ӳ��õ�
     * @param Object[] data ���ݼ���
     * @param BomAdoptNoticeIfc ifc ���µĶ���
     * @return Object[]
     */

	public Object[] updateSubBomAdoptNotice(Object[] data,BomAdoptNoticeIfc ifc) throws QMException;
    /**
     * �����Ӳ��õ�����ò��õ��������
     * @return Collection ���õ������������
     * @param String bsoID ���õ�����bsoID
     * @throws QMException
     */
    public Collection getPartsFromBomSubAdoptNotice(String bsoID)throws QMException;

	 /**
     * ���ݲ��õ�����ѯʹ�������������ڲ鿴BOM��
     * @param BomAdoptNoticeIfc bomIfc
     * @return Collection
     */
 	public Collection getBomAdoptUsagePart(BomAdoptNoticeIfc bomIfc)throws QMException;
	 /**
	    * ��Ų鿴����BOM����Ų鿴����BOM��
	    * @param String bsoID
	    * @return Collection
	    */
	public Collection getReleaseBom(QMPartIfc partIfc)throws QMException ;
	   /**
	    * ���������������õ�
	    * @param condition �����������
	    * @return Vector �������
	    * @author wenliu 2014-6-12
	    */
	public Collection searchGYBomAdoptNotice(HashMap condition) throws QMException;
	   /**
	    * ���������������Ĳ��á������
	    * @param condition �����������
	    * @return Vector �������
	    * @author wenliu 2014-6-4
	    */
	public Collection searchBomAdoptChangeNotice(HashMap condition) throws QMException;
	//CCBegin SS1
	   /**
	    * ����������ϣ���ѯ������Ч·������
	    * @param QMPartInfo[] parts �������
        * @param QMPartIfc parentPart ��������
	    * @return Vector ��������Լ�·������
	    * @author wenliu 2014-6-13
	    */
	public Collection findPartAndRoute(QMPartInfo[] parts,QMPartIfc parentPart) throws QMException;
	//CCEnd SS1
	  /**
     * ��������BOM����б����
     * @param Object[] data ���ݼ���
     * @return Object[]
     */

	public Vector createGYBomZCPartLink(Vector linkVec,GYBomAdoptNoticeIfc ifc) throws QMException;
	   /**
	    * ���ݷ�����ID������BOM
	    * @param QMPartIfc part
	    * @return QMPartIfc
	    * @throws QMException 
	    */
	public Collection getBomPartFromBomAdoptNotice(GYBomAdoptNoticeIfc ifc)throws QMException;
	 /**
     * ��������BOM����б����
     * @param Object[] data ���ݼ���
     * @return Object[]
     */

	public Vector updateGYBomZCPartLink(Vector linkVec,GYBomAdoptNoticeIfc ifc) throws QMException;
	 /**
     * ��������������
     * @param Object[] data ���ݼ���
     * @param GYBomAdoptNoticeIfc ifc ����������
     * @return Object[]
     */

	public Object[] updateGYBomAdoptNotice(Object[] data,GYBomAdoptNoticeIfc ifc) throws QMException;
	  /**
     * �������ܡ���ʻ�ҷ�����
     * @param Object[] data ���ݼ���
     * @return Object[]
     */

	public Object[] createFrameAndBodyBomAdoptNotice(Object[] data) throws QMException;
	 /**
     * ���³��ܡ���ʻ�ҷ�����
     * @param Object[] data ���ݼ���
     * @param GYBomAdoptNoticeIfc ifc ����������
     * @return Object[]
     */

	public Object[] updateFrameAndBodyBomAdoptNotice(Object[] data,GYBomAdoptNoticeIfc ifc) throws QMException;
    /**
     * ����������
     * @param GYBomAdoptNoticeInfo notice
     */
    public void disuseNotice(GYBomAdoptNoticeInfo notice) throws QMException;
  /**
    * ���ݷ�����IDɢ���嵥
    * @param String bsoID
    * @return Collection
    * @throws QMException 
    */
	public  Collection getInvoiceByNotice(String bsoID)throws QMException;
	/**
     * ����ɢ���嵥
     * @param Object[] data ���ݼ���
     * @param GYBomAdoptNoticeIfc ifc ����������
     * @return Object[]
     */

	public Vector saveInvoice(Vector linkVec,GYBomAdoptNoticeIfc ifc) throws QMException;
	/**
     * ���������
     * @param String csvData CSV�ļ�����
     * @return Object[]
     */

	public Vector importVirtualPart(String csvData) throws QMException;
	
	/**
	 * �������������й���BOM
	 * @param ifc ��������������
	 * @return �����Ƿ�ɹ�
	 * @throws QMException
	 * @author houhf
	 */
	public Vector<Object[]> exportAllBOM(GYBomAdoptNoticeIfc ifc)throws QMException,IOException;
	/**
     * ����ר�ü�
     * @param String csvData CSV�ļ�����
     * @return Object[]
     */

	public Vector importSpecPart(String csvData) throws QMException;
	
//CCBegin SS2
	/**
     * ����Bom
     * @param String csvData CSV�ļ�����
     * @return Object[]
     */

	public String importBom(String csvData) throws QMException;
	public String importBom1(String csvData) throws QMException;
	public String importGYBom(String csvData) throws QMException;
//CCEnd SS2
//CCBegin SS3
	/**
	���ݱ�����Ż���õ��Ż�����е���ز����㲿��������Ϣ�ļ���
	*/
	public Vector getUseByID(BaseValueIfc ifc) throws QMException;
	/**
	���ݱ�����Ż���õ��Ż�����е���ز������㲿��������Ϣ�ļ���
	*/
	public Vector getNouseByID(BaseValueIfc ifc) throws QMException;

//CCEnd SS3
//CCBegin SS4
	public boolean isLogical(QMPartIfc ifc) throws QMException;
	public Collection getReleaseBomDJ(QMPartIfc partIfc)throws QMException ;
//CCEnd SS4
	
	//CCBegin SS5
	public Collection getFsqd(QMPartIfc partIfc) throws QMException;
	//CCEnd SS5
	
	//CCBegin SS6
	/**
	 * ���ݱ�Ż���㲿������
	 * @param partNumber
	 * @return
	 * @throws QMException
	 */
	public Collection getPartByNumber(String partNumber)throws QMException;
	/**
	 * ����ָ���㲿����ţ������л���㲿���汾
	 * @param partNumber
	 * @return
	 */
	public String getNumberOfPartFromPen(String partNumber);
	/**
	 * ����㲿���Ƿ���ڣ������Ƿ��гɶ�·��
	 * @param csvData
	 * @return
	 * @throws QMException
	 */
	public HashMap checkPartFromExcel(String csvData) throws QMException;
	//CCEnd SS6
}