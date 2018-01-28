/**
 * ���ɳ���ʱ�� 2016-5-16 �汾 1.0 ���� ��� ��Ȩ��һ��������˾���� ��������һ��������˾��˽�л�Ҫ���� δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ����� ��������Ȩ��
 * SS1 ��������¼ liuyuzhu 2017-04-27
 * SS2 �޸ĳ�ʼ������BOM ���� 2017-5-3
 * SS3 ���浥���滻���Ĺ���BOM liuyuzhu 2017-05-19
 * SS4 ���Ż�ȡ��ǰ�û����������� liunan 2017-5-24
 * SS5 ��ʼ��bom֮�󣬲����߼��ܳ��ǳɶ������ģ������ڽ�Ź���bom�Ҳ�����˳ɶ����߼��ܳɽṹ���˹�����Ҫȥ���� (����ƽ̨A004-2017-3578)2017-07-03
 * SS6 ��ѯ��������ǩ liuyuzhu 2017-07-20
 * SS7 ����BOM���� liuyuzhu 2017-07-25
 * SS8 �޸Ľṹ�ȽϷ��� liuyuzhu 2017-08-22
 * SS9 ��������ж��Ƿ�Ϊ��Ч�ṹ�ķ��� liuyuzhu 2017-09-26 
 * SS10 �滻�����������仯 liuyuzhu 2017-11-02
 * SS11 ���ж�BOM���޸Ļ�������ı�ڵ���ɫ liuyuzhu 2017-11-08
 * SS12 �����㲿�� liuyuzhu 2017-11-13
 * SS13 ����BOM�ͻ��˵��빦�� ������ 2017-12-8
 * SS14 У�鹤��bom�Ƿ�༭·�߲�����  ������ 2017-12-22
 */
package com.faw_qm.gybom.ejb.service;

import java.util.Collection;
import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.users.model.UserInfo;

/**
 * ��׼���ĵ������EJBLocal�ӿڡ�
 * 
 * @author ��� �޸�ʱ�� 2016-5-16
 */
public interface GYBomService extends BaseService
{
	/**
	 * �������BOM����
	 * 
	 * @param Collection
	 *            mtreenode Ŀ����������BOM���ڵ㼯�� ����ʱҪ��ȡ��ǰ�û����ڵ�λ��Ȼ��ֱ��ͨ�����ݿⴴ����
	 */
	public String saveBom(String mtreenode);

	/**
	 * ��ק����BOM���ϵ��ܳɼ��㲿��������BOM���ϵȲ����� ������ק������bom����ק��Ŀ��bom��֮����Ҫ��ͼ�괦��
	 * ������ק������BOM���µ��㲿�����ṹ����������BOM���£�����bom����Ҫ��ק������bom���ڵ�֮�£���ק֮����Ҫ���оֲ�ˢ�²����棩
	 * ������ק������BOM������ṹ�ĵ�����������ק��������ṹ�ĵ���������Ҫ�ı�ͼ�� ������ק��Ŀ����������ק������ק֮������ύ���档
	 * ������ק��addArr��ֵ��������ק��updateArr��ֵ���Ƴ���deleteArr��ֵ�������ƶ���addArr��updateArr��ֵ
	 * 
	 * @param String
	 *            [] addArr �����ṹ���� ����id,�Ӽ�id;�Ӽ�id1;�Ӽ�id2,����;����1;����2,��λ;��λ1;��λ2
	 * @param String
	 *            [] updateArr ���½ṹ����
	 *            linkid;����id;����,linkid1;����id1;����1,linkid2;����id2;����2
	 * @param String
	 *            [] deleteArr ɾ���ṹ���� linkid,linkid1,linkid2
	 * @param String
	 *            carModelCode ������
	 * @param String
	 *            dwbs ���� ����������json
	 */
	public String saveTreeNode(String addstr,String updatestr,String deletestr,String carModelCode,String dwbs,
		String left);

	/**
	 * ɾ������BOM����
	 * 
	 * @param Collection
	 *            mtreenode Ŀ����������BOM����������
	 *            ÿ��Ԫ����һ��6ά���飬�ֱ��Ǹ���bsoID���Ӽ�bsoID�����������ʹ��롢��λ����Ч��ʶ�� ɾ�������������ݡ�
	 */
	public void deleteGYBom(Collection mtreenode);

	/**
	 * ��������
	 * 
	 * @param Collection
	 *            ytreenode Դ�������BOM����������
	 * @param Collection
	 *            mtreenode Ŀ����������BOM����������
	 * @param Collection
	 *            links �������ϣ�ÿ��Ԫ����һ����ά���飬�ֱ��Ǹ���bsoID���Ӽ�bsoID��������
	 *            ����ʱҪ��ȡ��ǰ�û����ڵ�λ��Ȼ��ֱ��ͨ�����ݿⴴ����
	 */
	public void saveGYBom(Collection mtreenode, boolean checkexitflag, String dwbs);//SS5

	/**
	 * ��������BOM����
	 * 
	 * @param String
	 *            [] mt parentPart,childPart,quantity,carModelCode,dwbs,
	 *            effectCurrent,locker,lockDate,bz1
	 * @return String ����id
	 */
	public String saveGYBom(String[] mt);

	/**
	 * ���¹���BOM����
	 * 
	 * @param Collection
	 *            mtreenode �������ϣ�ÿ��Ԫ����һ����ά���飬�ֱ���linkid������bsoID��������
	 */
	//CCBegin SS11
//	public void updateGYBom(Collection mtreenode) throws QMException;
	public void updateGYBom(Collection mtreenode,String dwbs,String type) throws QMException;
	//CCEnd SS70

	/**
	 * ���ɳ�ʼ����BOM
	 */
	public void initGYBom(String partID,String gyID,String dwbs) throws QMException;

	/**
	 * ����ָ���㲿������һ���Ӽ��ڵ㼯�� ��������㲿�����bom��Դ��������flag��������ʱ���л�ȡ���ǻ�ȡ���½ṹ��
	 * 
	 * @param id
	 *            �㲿��bsoID
	 * @param flag
	 *            �Ƿ��ȡ��Ʊ�ǩ��
	 * @param carModelCode
	 *            ������
	 * @param dwbs
	 *            ��λ
	 * @return �㲿������һ���Ӽ������ṹ
	 */
	public String getDesignBom(String id,String flag,String carModelCode,String dwbs) throws QMException;

	/**
	 * ����ָ���㲿����һ���Ӽ��ڵ㼯�ϣ�������ʾ���BOM�㲿���б�
	 * 
	 * @param id
	 *            �㲿��bsoID
	 * @param flag
	 *            �Ƿ��ȡ��Ʊ�ǩ��
	 * @param carModelCode
	 *            ������
	 * @param dwbs
	 *            ��λ
	 * @return �㲿������һ���Ӽ������ṹ
	 */
	public String getDesignBomList(String id,String flag,String carModelCode,String dwbs) throws QMException;

	/**
	 * ����ָ���㲿������һ���Ӽ��ڵ㼯�� ������ӹ���bom��Ŀ������
	 * 
	 * @param id
	 *            �㲿��bsoID
	 * @param carModelCode
	 *            ������
	 * @param dwbs
	 *            ��λ
	 * @return �㲿������һ���Ӽ������ṹ
	 */
	public String getGYBom(String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * ����ָ���㲿����һ���Ӽ��ڵ㼯�ϣ�������ʾ����BOM�㲿���б�
	 * 
	 * @param id
	 *            �㲿��bsoID
	 * @param carModelCode
	 *            ������
	 * @param dwbs
	 *            ��λ
	 * @return �㲿������һ���Ӽ������ṹ
	 */
	public String getGYBomList(String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * �������
	 */
	public String createPart(String s) throws QMException;

	/**
	 * ���ù���bom��Ч
	 */
	public void setValidBom(String id) throws QMException;

	/**
	 * �������������bom�� ���ݱ�ź����������㲿��
	 * 
	 * @param name
	 *            �㲿������
	 * @param number1
	 *            �㲿�����
	 * @param ignoreCase
	 *            ���Դ�Сд
	 * @return �����������㲿������
	 * @exception com.faw_qm.framework.exceptions.QMException
	 */
	public String findPart(String number1,String name,String ignoreCase) throws QMException;

	/**
	 * �ṹ�Ƚ�
	 */
	public String CompartTreeResult(String desginID,String gyID,String carModelCode,String dwbs) throws QMException;

	/**
	 * ����
	 */
	public String addLock(String carModelCode,String dwbs) throws QMException;

	/**
	 * ����
	 */
	public String unLock() throws QMException;

	/**
	 * ����Ƿ���
	 */
	public String checkLock(String carModelCode,String dwbs) throws QMException;

	/**
	 * ��õ�ǰ�û�
	 * 
	 * @throws QMException
	 */
	public UserInfo getCurrentUserInfo() throws QMException;

	/**
	 * ���Ϊ����BOM�� String yid ѡ���㲿���� String mid Ŀ���㲿���� String dwbs ѡ���㲿������
	 */
	public String saveAsGYBom(String yid,String mid,String ydwbs) throws QMException;

	/**
	 * ���ñ����Ϊһ����Ч�Ĺ���BOM��¡һ��δ��ЧBOM��
	 */
	public String changeGYBom(String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * ���������滻���ͱ� id ���ͳ���bsoid,������,����,�滻����1,�滻����2,�滻����3...
	 */
	public String saveBatchUpdateCM(String id) throws QMException;

	/**
	 * ��������ȫ���滻���ͱ� id ����id
	 */
	public String saveAllBatchUpdateCM(String id) throws QMException;

	/**
	 * ��ȡ�����滻���ͱ�
	 */
	public String getBatchUpdateCM(String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * ���汾 ������bom�Ͻڵ�汾����һ���汾������ԭ���汾�ǡ�D����ѡ�񽵰汾֮���Ϊ��C���汾
	 */
	public String upperVersion(String linkid,String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * ��� �����Ҫ��ֵ��߼��ܳɣ���ͬ�����Զ�����һ���߼��ܳɺ� ��Ź���ԭ�߼��ܳɺ�+F+3λ��ˮ��
	 * 
	 * @parm String linkid ����id��linkid��
	 * @parm String carModelCode ������
	 * @parm String dwbs ����
	 */
	public String chaiFenPart(String linkid) throws QMException;

	public Vector getExportFirstLeveList(String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * ��ӱ�Ʒ ��ѡ���㲿���Ӽ���ӵ�ָ���㲿���¡� ����
	 * 
	 * @parm String parentID ָ���㲿��
	 * @parm String beipinID ѡ���㲿��
	 * @parm String carModelCode ������
	 * @parm String dwbs ����
	 */
	public String addBeiPin(String parentID,String beipinID,String carModelCode,String dwbs) throws QMException;

	/**
	 * ����bom��ʷ���ݵ��뷽�� AddUsageLink C01AM44141BF204 CQ1511065 ea 8 AddUsageLink
	 * C01AM44141BF204 Q1841240F6 ea 8 ��� ���� �Ӽ� ��λ ����
	 */
	public String uploadBomExcel(String isupdate) throws QMException;

	/**
	 * ��ȡ����BOM�б��嵥
	 */
	public Vector getBomExcel() throws QMException;

	/**
	 * �����޸Ĺ���BOM�������޸Ĺ��򼯺ϣ���Ҫ�޸ĵĳ��ͣ����е���
	 */
	public void multiChangeGYBom(QMPartIfc ypart) throws QMException;

	/**
	 * ɾ���ܳ� �Ҽ���Ӳ˵���ɾ���ܳɡ����˲˵������ǽ��ܳ�ɾ�����������Ӽ��㼶����һ���� ����
	 * 
	 * @parm String parentID ѡ���㲿���ĸ���
	 * @parm String partID ѡ���㲿��
	 * @parm String carModelCode ������
	 * @parm String dwbs ����
	 */
	public String deleteSeparable(String parentID,String partID,String carModelCode,String dwbs) throws QMException;

	/**
	 * ����BOM ָ�����͡�������BOM���ϡ�
	 */
	public Vector getExportBomList(String carModelCode) throws QMException;

	/**
	 * ��ȡ�㲿�������ڸù����ڵ����и���
	 */
	public Vector getParentFromDwbs(String id) throws QMException;

	/**
	 * ��ȡ�㲿�������ڸù����ڵ�����δ��Ч�ĸ��� ��ȡ�Ӽ�id�͵�ǰ�û���������Чbom�еĸ����� ���ؼ��� �㲿��bsoid ��� ���� ����
	 */
	public Vector getParentPart(String id) throws QMException;

	// SS2 begin 
	public void createGyBomCFHistory(String sourceID,String cfID,String parentID,String type) throws QMException;

	public Collection getGyBomCFHistoryBySourceID(String sourceID,String type) throws QMException;

	public void createGyBomReNameHistory(String sourceID,String cfID) throws QMException;

	public Collection getGyBomReNameHistoryBySourceID(String sourceID) throws QMException;
	
	public String upgradeVersion(String linkid,String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * ����������ʼ����BOM
	 */
	public void initGYZCBom(String partID,String gyID,String dwbs) throws QMException;

	/**
	 * ���ɳ��ܳ�ʼ����BOM
	 */
	public void initGYCJBom(String partID,String gyID,String dwbs) throws QMException;

	/**
	 * ���ɼ�ʻ�ҳ�ʼ����BOM
	 */
	public void initGYJSSBom(String partID,String gyID,String dwbs,String newnumber) throws QMException;
	// SS2 end

  //CCBegin SS1
  /**��������¼
	 * @param pid
	 * @param sid
	 * @return
	 */
	public String saveChangeContent(String cxh, String gc,
			String parentid, String subid,String tsubid, String quantityb, String quantitya,
			String sign) throws QMException;
	//CCEnd SS1
	
	//CCBegin SS3
	/**�滻��
	 * @param linkid linkid
	 * @param yid ԭ�ڵ�id
	 * @param xid �滻�ڵ�id
	 * @param carModelCode ����
	 * @param dwbs ����
	 * @return
	 * @throws QMException
	 */
	//CCBegin SS10
//	public String changePart(String linkid,String yid,String xid,String carModelCode,String dwbs) throws QMException;
	public String changePart(String linkid,String yid,String xid,String carModelCode,String dwbs,String parentid,String changeType) throws QMException;
	//CCEnd SS10
	//CCEnd SS3
	
	//CCBegin SS4
	/**
	 * ��ȡ��ǰ�û����ڵĹ�����
	 * @return ����
	 * @throws QMException
	 */
	public String getCurrentDWBS() throws QMException;
	//CCEnd SS4
	//CCBegin SS6
	/**��ѯ������ǩ
	 * @param carModelCode ������
	 * @return 
	 */
	public String searchLinkBook(String carModelCode) throws QMException;
	//CCEnd SS6
	//CCBegin SS7
	/**����BOM
	 * @param id
	 * @param carModelCode
	 * @param dwbs
	 * @return
	 * @throws QMException
	 */
	public Vector getExportBOMList(String id,String carModelCode,String dwbs) throws QMException;
	//CCEnd SS7
	//CCBegin SS8
	/**
	 * �ṹ�Ƚ�
	 */
	public Vector CompartTreeResult1(String desginID,String gyID,String carModelCode,String dwbs,String isRelease) throws QMException;
	//CCEnd SS8
	//CCBegin SS9
	/**
	 * ���ݳ�����͹������ж��Ƿ���Ч ����0 û��δ��Ч�ṹ û����Ч�ṹ ����1 ��δ��Ч�ṹ û����Ч�ṹ ����2 û��δ��Ч�ṹ ����Ч�ṹ
	 * ����3 ��δ��Ч�ṹ ����Ч�ṹ
	 */
	public String getEffect(String parentPart,String carModelCode,String dwbs) throws QMException;
	//CCEnd SS9
	//CCBegin SS10
	/**
     * @param linkid linkid
     * @param yid ԭ�ڵ�id
     * @param xid �滻�ڵ�id
     * @param carModelCode ����
     * @param dwbs ����
     * @param parentid ����id
     * @param changeType 
     * @return
     * @throws QMException
     */
	public String changePartOther(String linkid,String yid,String xid,String carModelCode,String dwbs,String parentid,String productid) throws QMException;
	//CCEnd SS10
	//CCBegin SS12
	/**�����㲿��
	 * @param partid ѡ�нڵ����id
	 * @param dwbs ����
	 * @return
	 * @throws QMException
	 */
	public String lockPart(String partid,String dwbs) throws QMException;
	/**�����㲿��
	 * @param partid ѡ�нڵ����id
	 * @param dwbs ����
	 * @return
	 * @throws QMException
	 */
	public String unLockPart(String partid,String dwbs) throws QMException;
	/**
	 * ��ѯ�����û��Ƿ�Ϊ��ǰ�û�
	 * 
	 * @return false û�е�ǰ�û������Ķ���,true �е�ǰ�û������Ķ���
	 * @throws QMException
	 */
	public String searchLockUser(String partid, String dwbs) throws QMException;
	//CCEnd SS12
//	CCBegin SS13
	 public String importGYBom(String csvData) throws QMException;
//	CCEnd SS13
	 /**
		 * У�鹤��bom�Ƿ�༭·�߲�����
		 */
		public Vector checkBom(String carModelCode) throws QMException;
}