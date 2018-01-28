/** ���ɳ��� StandardPartService.java    1.0    2003/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/27 л�� ԭ��:�߼�����
 *                     ����:��д������߼���
 *                     ��ע:���v3r11-�ּ������嵥�����Ż�-л��
 * CR2 20090619 ��ǿ �޸�ԭ�򣺽��нṹ�ȽϺ󣬲�Ʒ��Ϣ���������ù淶��ʾΪ�ṹ�Ƚ�Ŀ��������ù淶����TD-2190�� 
 *                   ������������нṹ�ȽϺ󣬲�Ʒ��Ϣ��������ȷ��ʾ�����ù淶��       
 * SS1 2013-1-21 ��Ʒ��Ϣ����������������嵥�����������������erp���Ա�����         
 * SS2 ͨ��masterid��ȡ���°汾���㲿���� liunan 2013-11-25
 * SS3 ���Ӳ�Ʒ��Ϣ�������������λ����������� pante 2015-01-14
 * SS4 A004-2016-3286 ����һ�����嵥 liunan 2016-1-20
 * SS5  �ɶ�������ӻ���Ӽ� guoxiaoliang 2016-7-28
 */
package com.faw_qm.part.ejb.standardService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;

//CCBegin by liunan 2008-08-05
import com.faw_qm.version.model.MasteredIfc;
//CCBegin by liunan 2008-08-05
import com.faw_qm.part.model.PartAttrSetIfc;

//CCBegin SS2
import com.faw_qm.part.model.QMPartInfo;
//CCEnd SS2

/**
 * ��׼����񣬰����㲿���Ļ���ά�����ܣ����������¡�ɾ���������������Ϊ����
 * �㲿��������Ϣ��ѯ��ʹ�á��ο��������ȹ�����ϵ����
 * ���㲿���İ汾�������롢���������������޶����鿴�汾�Ͱ��򡢷����ȹ��ܣ���
 * �㲿����ͳ�ƻ��ܹ��ܵȷ��񷽷���
 * @author ���ȳ�
 * @version 1.0
 */
public interface StandardPartService extends BaseService
{
    /**
     * ͨ���㲿�������ֺͺ�������㲿��������Ϣ�����صļ�����ֻ��һ��QMPartMasterIfc����
     * @param partName :String �㲿�������ơ�
     * @param partNumber :String �㲿���ĺ��롣
     * @return collection :���ҵ���QMPartMasterIfc�㲿������Ϣ����ļ��ϣ�ֻ��һ��Ԫ�ء�
     * @throws QMException
     */
    public Collection findPartMaster(String partName, String partNumber)
            throws QMException;

    /**
     * ͨ��һ���㲿������Ϣ�ҵ����㲿�����а汾ֵ����ļ��ϡ�
     * @param partMasterIfc :QMPartMasterIfc����
     * @return collection :Collection ���ж�Ӧ��partMasterIfc���㲿������(QMPartIfc)�ļ��ϡ�
     * @throws QMException
     */
    public Collection findPart(QMPartMasterIfc partMasterIfc)
            throws QMException;

    /**
     * ɾ���ο��ĵ����㲿���Ĺ�����ϵ��
     * @param linkIfc PartReferenceLinkIfc
     * @throws PartException
     */
    public void deletePartReferenceLink(PartReferenceLinkIfc linkIfc)
            throws PartException;

    /**
     * ����ο��ĵ����㲿���Ĺ�����ϵ��
     * @param linkIfc PartReferenceLinkIfc
     * @throws PartException
     * @return PartReferenceLinkIfc
     */
    public PartReferenceLinkIfc savePartReferenceLink(
            PartReferenceLinkIfc linkIfc) throws PartException;

    /**
     * �����㲿�����㲿������Ϣ�Ĺ�����ϵ��
     * @param linkIfc PartUsageLinkIfc
     * @return PartUsageLinkIfc
     * @throws PartException
     */
    public PartUsageLinkIfc savePartUsageLink(PartUsageLinkIfc linkIfc)
            throws PartException;

    /**
     * ɾ���㲿�����㲿������Ϣ�Ĺ�����ϵ��
     * @param linkIfc PartUsageLinkIfc
     * @throws PartException
     */
    public void deletePartUsageLink(PartUsageLinkIfc linkIfc)
            throws PartException;

    /**
     * ���������ĵ����㲿���Ĺ�����ϵ��
     * @param linkIfc PartDescribeLinkIfc
     * @throws PartException
     * @return PartDescribeLinkIfc
     */
    public PartDescribeLinkIfc savePartDescribeLink(PartDescribeLinkIfc linkIfc)
            throws PartException;

    /**
     * ɾ�������ĵ����㲿���Ĺ�����ϵ��
     * @param linkIfc PartDescribeLinkIfc
     * @throws PartException
     */
    public void deletePartDescribeLink(PartDescribeLinkIfc linkIfc)
            throws PartException;

    /**
     * �޶��㲿������������һ���µĴ�汾��
     * @param partIfc :QMPartIfc �޶�ǰ���㲿����ֵ���󣬱���Ϊ���µĵ����汾��
     * @return partIfc:QMPartIfc �޶�����㲿����ֵ����
     * @throws QMException
     */
    public QMPartIfc revisePart(QMPartIfc partIfc) throws QMException;

    /**
     * �����㲿��ֵ�����ø��㲿�����������вο��ĵ�(DocIfc)���°汾��ֵ����ļ��ϡ�
     * �ȸ����㲿����ȡ���еĲο��ĵ�����Ϣ(DocMasterIfc)�ļ���,�ٶ�DocMasterIfc����
     * ���й��ˣ��ҳ�ÿ��DocMasterIfc����Ӧ�����°汾DocIfc����󷵻�DocIfc�ļ��ϡ�
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @return Vector �㲿���ο��ĵ�(DocIfc)ֵ����ļ��ϡ�
     * @throws QMException
     */
    public Vector getReferencesDocMasters(QMPartIfc partIfc) throws QMException;

    /**
     * ����ָ�����㲿����ֵ���󷵻ظ��㲿�������ĵ���ֵ���󼯺ϡ�
     * @param partIfc :QMPartIfc�㲿����ֵ����
     * @param flag : boolean
     * @return vector:Vector �㲿�������ĵ���ֵ���󼯺ϡ�
     * @throws QMException
     */
    public Vector getDescribedByDocs(QMPartIfc partIfc, boolean flag)
            throws QMException;

    /**
     * �������ĵ���ֵ�����ñ��ο����㲿����ֵ����ļ��ϡ�
     * @param docMasterIfc :DocMasterIfc �ο����ĵ���ֵ����
     * @return vector :Vector ���ĵ��ο����㲿����ֵ����ļ��ϡ�
     * @throws QMException
     */
    public Vector getReferencedByParts(DocMasterIfc docMasterIfc)
            throws QMException;

    /**
     * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
     * @param partIfc :QMPartIfc �㲿����ֵ����
     * @return collection:Collection ��partIfc������PartUsageLinkIfc�Ķ��󼯺ϡ�
     * @throws QMException
     */
    public Collection getUsesPartMasters(QMPartIfc partIfc) throws QMException;

    /**
     * ����ָ�����㲿�������¼��㲿�������°汾��ֵ����ļ��ϡ�
     * @param partIfc :QMPartIfc �㲿����ֵ����
     * @return collection:Collection partIfcʹ�õ��¼��Ӽ������°汾��ֵ���󼯺ϡ�
     * @throws QMException
     */
    public Collection getSubParts(QMPartIfc partIfc) throws QMException;

    /**
     * ����ָ���㲿�����������¼��㲿�������°汾��ֵ���󼯺ϡ�
     * @param partIfc :QMPartIfc �㲿����ֵ����
     * @return collection:Collection partIfcʹ�õ��¼��Ӽ������°汾��ֵ���󼯺ϡ�
     * @throws QMException
     */
    public Collection getAllSubParts(QMPartIfc partIfc) throws QMException;

    /**
     * ����ָ����QMPartMasterIfc����
     * ͨ�����������ϲ�ѯ�����PartUsageLink������QMPartMasterIfc���������°汾
     * ��QMPartIfc����ļ��ϡ�
     * @param partMasterIfc :QMPartMasterIfc����
     * @return collection ��partMasterIfc��PartUsageLink���й��������°汾QMPartIfc����ļ��ϡ�
     * @throws QMException
     */
    public Collection getUsedByParts(QMPartMasterIfc partMasterIfc)
            throws QMException;

    /**
     * ͨ��ָ����ɸѡ������������collection�е�PartUsageLinkIfc�����Ӧ��
     * ����ɸѡ������Iterated��������ɸѡ�����������ɸѡ�����򷵻ض�Ӧ��Mastered���㲿����
     * @param collection :Collection ��PartUsageLinkIfc����ļ��ϡ�
     * @param configSpecIfc :PartConfigSpecIfc �㲿����ɸѡ������
     * @return collection:Collection ����ÿ��Ԫ��Ϊһ������:
     * ����ĵ�һ��Ԫ��ΪPartUsageLinkIfc���󣬵ڶ���Ԫ��ΪQMPartIfc����
     * ���û��QMPartIfc����Ϊ������QMPartMasterIfc����
     * @throws QMException
     */
    public Collection getUsesPartsFromLinks(Collection collection,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * �����㲿����
     * @param partIfc :QMPartIfc Ҫ������㲿����ֵ����
     * @return partIfc:QMPartIfc �������㲿����ֵ����
     * @throws QMException
     */
    public QMPartIfc savePart(QMPartIfc partIfc) throws QMException;

    /**
     * ɾ��ָ�����㲿�����������������ʹ�øò�����
     * ���쳣"���㲿���Ѿ�����������ʹ�ã�����ɾ����"��
     * @param partIfc :QMPartIfc Ҫɾ�����㲿����ֵ����
     * @throws QMException
     */
    public void deletePart(QMPartIfc partIfc) throws QMException;

    /**
     * �ж��㲿��partIfc2�Ƿ����㲿��partIfc1�����Ȳ�������partIfc1����
     * @param partIfc1 :QMPartIfc ָ�����㲿����ֵ����
     * @param partIfc2 :QMPartIfc ��������㲿����ֵ����
     * @return flag:boolean:
     * flag==true:�㲿��part2���㲿��part1�����Ȳ�������part1����
     * flag==false:�㲿��part2�����㲿��part1�����Ȳ�����part1����
     * @throws QMException
     */
    public boolean isParentPart(QMPartIfc partIfc1, QMPartIfc partIfc2)
            throws QMException;

    /**
     * ���ָ���㲿�������и�����������Ϣֵ���󼯺ϡ�
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @return vector:Vector ָ���㲿�������и�����(ֱ��������)������Ϣֵ����ļ��ϡ�
     * @throws QMException
     */
    public Vector getAllParentParts(QMPartIfc partIfc) throws QMException;

    /**
     * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
     * ������������bianli()����ʵ�ֵݹ顣
     * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
     * 1��������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
     * 2������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�

     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
     * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Լ��ϣ�����Ϊ�ա�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õĶԵ�ǰ�㲿����ɸѡ������
     * @throws QMException
     * @return Vector
     */
    public Vector setBOMList(QMPartIfc partIfc, String[] attrNames,
            String[] affixAttrNames, String source, String type,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * �ּ������嵥����ʾ��
     * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
     * 0����ǰpart��BsoID��
     * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
     * 2����ǰpart�ı�ţ�
     * 3����ǰpart�����ƣ�
     * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
     * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
     *                ������������ԣ��������ж��Ƶ����Լӵ���������С�
     * �����������˵ݹ鷽����
     * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
     * PartUsageLinkIfc partLinkIfc, int parentQuantity);
     * @param partIfc :QMPartIfc ����Ĳ���ֵ����
     * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա�
     * @param affixAttrNames : String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
     * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
     * @throws QMException
     * @return Vector
     */
    public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames,
            String[] affixAttrNames, PartConfigSpecIfc configSpecIfc)
            throws QMException;

    /**
     * ����ָ���㲿����ָ����ɸѡ��������㲿���ڸ�ɸѡ�����±���Щ������ʹ�á�
     * ʹ�ý�������ڷ���ֵvector�С�
     * vector����ֵ�����ݽṹΪ��vector�е�ÿ��Ԫ�ض���Vector���͵ģ�����Ϊ���������㣬����ΪsubVector.
     * ��subVector��ÿ��Ԫ�ض���String[3]���͵ġ�
     * ���String[3]�ֱ��¼��::
     * String[0]:��κţ�
     * String[1]:�㲿�����(�㲿������)�汾(��ͼ);
     * String[2]�㲿���ڴ˽ṹ��(��������)ʹ�õ�������������ͬһ�ṹ�µļ�¼ʹ����������ͬ�ġ�

     * @param partIfc :QMPartIfc ָ�����㲿��ֵ����
     * @param configSpecIfc :PartConfigSpecIfc ָ����ɸѡ������
     * @return vector:Vector ������������ʹ�õ���Ϣ���ϡ�
     * @throws QMException
     */
    public Vector setUsageList(QMPartIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * ͨ��ָ�������ù淶�������ݿ���Ѱ��ʹ��partIfc����Ӧ��PartMasterIfc�����в���(QMPartIfc)��
     * ����ֵ����(PartUsageLinkIfc, QMPartIfc)ΪԪ�صļ��ϡ�
     * @param partIfc �㲿��ֵ����
     * @param configSpecIfc �㲿�����ù淶��
     * @return Collection
     * @throws QMException
     */
    public Collection getParentPartsByConfigSpec(QMPartIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * ����ָ�������ù淶Ϊ��ѯ�ռ���Ӳ�ѯ������
     * @param partConfigSpecIfc �㲿�����ù淶ֵ����
     * @param query QMQuery
     * @throws QMException
     * @throws QueryException
     * @return QMQuery
     */
    public QMQuery appendSearchCriteria(PartConfigSpecIfc partConfigSpecIfc,
            QMQuery query) throws QMException, QueryException;

    /**
     * �������ù淶���˳��������ù淶��collection������QMPartMasterIfc�Ĺ����QMPartIfc�����С�汾��
     * @param collection Collection
     * @param partConfigSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return Collection
     */
    public Collection filteredIterationsOf(Collection collection,
            PartConfigSpecIfc partConfigSpecIfc) throws QMException;

    /**
     * ��ѯ��ǰ�û������ù淶��
     * @throws QMException
     * @return PartConfigSpecIfc
     */
    public PartConfigSpecIfc findPartConfigSpecIfc() throws QMException;

    /**
     * ����ָ�����ù淶�����ָ��������ʹ�ýṹ��
     * ���ؼ��ϵ�ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
     * ��1��Ԫ�ؼ�¼���������¼��use��ɫ��mastered����ƥ�����ù淶��iterated����
     * ���û��ƥ����󣬼�¼mastered����
     * @param partIfc QMPartIfc
     * @param configSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return Collection
     */
    public Collection getUsesPartIfcs(QMPartIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    
    /**
     * ����ָ�����ù淶�����ָ��������ʹ�ýṹ��
     * ���ؼ���Collection��ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
     * ��1��Ԫ�ؼ�¼���������¼��use��ɫ��mastered����ƥ�����ù淶��iterated����
     * ���û��ƥ����󣬼�¼mastered����
     * @param partIfc �㲿��ֵ����
     * @return
     * @throws QMException
     */
    public Collection getUsesPartIfcs(QMPartIfc partIfc) throws QMException;
    
    /**
     * �������ù淶�а�������Ϣ���˽�����͡�
     * @param configSpecIfc PartConfigSpecIfc
     * @param collection Collection
     * @throws QMException
     * @throws QueryException
     * @return Collection
     */
    public Collection process(PartConfigSpecIfc configSpecIfc,
            Collection collection) throws QMException, QueryException;

    /**
     * 1������µ����ù淶û�г־û����棬Ϊ��ָ�������ߣ�
     * 2����ѯ���ݿ⣬��õ�ǰ�û��ľɵ����ù淶��
     * 3��������ھ����ù淶���ж��µ����ù淶�Ƿ�Ϊ�գ����ǿգ�
     *    �����ݿ���ɾ�������ù淶�����򣬽������ù淶�����ݸ�ֵ�������ù淶�У��������ݿ⡣
     * 4�����֮ǰ�����ھɵ����ù淶���������ù淶�־û����档
     * @param configSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return PartConfigSpecIfc
     */
    public PartConfigSpecIfc savePartConfigSpecIfc(
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * ͨ�����ƺͺ���������㲿��������ģ����ѯ��
     * ���nameΪnull���������ѯ�����numberΪnull�������Ʋ�ѯ��
     * ���name��numnber��Ϊnull������������㲿����ֵ����
     * @param name :String ģ����ѯ���㲿�����ơ�
     * @param number :String ģ����ѯ���㲿���ĺ��롣
     * @return collection:Collection ���ϲ�ѯ���������㲿����ֵ����ļ��ϡ�
     * @throws QMException
     */
    public Collection getAllPartMasters(String name, String number)
            throws QMException;

    /**
     * ͨ�����ƺͺ���������㲿��������ģ����ѯ�����nameΪnull���������ѯ�����number
     * Ϊnull�������Ʋ�ѯ�����name��numnber��Ϊnull������������㲿����ֵ����
     * �������nameFlagΪtrue, �����㲿�����ƺ�name����ͬ����Щ�㲿�������򣬲����㲿��
     * ���ƺ�name��ͬ���㲿�����Բ���numFlag��ͬ���Ĵ���
     * @param name ����ѯ���㲿�����ơ�
     * @param nameFlag ����������
     * @param number ����ѯ���㲿����š�
     * @param numFlag ����������
     * @return Collection ��ѯ���������㲿��(QMPartMasterIfc)�ļ��ϡ�
     * @throws QMException
     */
    public Collection getAllPartMasters(String name, boolean nameFlag,
            String number, boolean numFlag) throws QMException;

    /**
     * ����partIfcѰ�Ҷ�Ӧ��partMasterIfc,�ٲ���partUsageLink����ȡʹ���˸�partMasterIfc
     * �������㲿����
     * @param partIfc :QMPartIfc �㲿��ֵ����
     * @exception QMException �־û�������쳣��
     * @return Collection QMPartIfc�ļ��ϡ�
     */
    public Collection getPartsByUse(QMPartIfc partIfc) throws QMException;

    /**
     * ����ָ�����㲿��ֵ�����ɸѡ��������㲿���Ĳ�Ʒ�ṹ������Vector:
     * Vector�д��NormalPart�Ķ���ļ��ϣ�ÿ��NormalPart�����е����԰�����
     * ����+����+�汾+��ͼ+����+��λ��ע�⣬����ֵVector�в�������ǰ����Ĳ���partIfc��
     * @param partIfc :��ǰ�㲿��ֵ����
     * @param configSpecIfc ��ǰ�㲿�����ù淶ֵ����
     * @return Vector
     * @throws QMException
     */
    public Vector getProductStructure(QMPartIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * ����ָ�����㲿��ֵ�����ɸѡ��������㲿�����ӡ�����Vector:
     * Vector�д��NormalPart�Ķ���ļ��ϣ�ÿ��NormalPart�����е����԰�����
     * ����+����+�汾+��ͼ+����+��λ��ע�⣬����ֵVector�в�������ǰ����Ĳ���partIfc��
     * @param partIfc :��ǰ�㲿��ֵ����
     * @param configSpecIfc ��ǰ�㲿�����ù淶ֵ����
     * @return Vector
     * @throws QMException
     */
    public Vector getSubProductStructure(QMPartIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * �޸��㲿�����˷������ͻ���ֱ�ӵ��ã������ƣ���š�
     *
     * @param partMasterIfc QMPartMasterIfcֵ����
     * @param flag �Ƿ��޸��㲿���ı��,trueΪ�޸�,false���޸ġ�
     * @return partMasterIfc �޸ĺ���㲿����ֵ����
     * @throws QMException
     */
    public QMPartMasterIfc renamePartMaster(QMPartMasterIfc partMasterIfc,
            boolean flag) throws QMException;

    //CR2 Begin 20090619 zhangq  �޸�ԭ��TD-2190��
    /**
     * ������ɢ��������Ϣ����PartConfigSpecIfc����һ��ʮ����������
     * @param effectivityActive �Ƿ�����Ч�����ù淶 ��1,��0��
     * @param baselineActive �Ƿ��ǻ�׼�����ù淶 ��1,��0��
     * @param standardActive �Ƿ��Ǳ�׼���ù淶 ��1,��0��
     * @param baselineID ��׼�ߵ�BsoID��
     * @param configItemID ���ù淶��BsoID��
     * @param viewObjectID ��ͼ�����bsoID��
     * @param effectivityType ��Ч�����͡�
     * @param effectivityUnit ��Ч�Ե�λ ��Ҫ�־û���
     * @param state ״̬��
     * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true��
     * @return PartConfigSpecIfc ��װ�õ��㲿�����ù淶ֵ����
     * @throws QMException
     */
    public PartConfigSpecIfc hashtableToPartConfigSpecIfc(
            String effectivityActive, String baselineActive,
            String standardActive, String baselineID, String configItemID,
            String viewObjectID, String effectivityType,
            String effectivityUnit, String state, String workingIncluded)
            throws QMException;
    
    /**
     * ������ɢ��������Ϣ����PartConfigSpecIfc����һ��ʮ����������
     * @param isUseCache �Ƿ��û�������ù淶�ı�־ ��1,��0��
     * @param effectivityActive �Ƿ�����Ч�����ù淶 ��1,��0��
     * @param baselineActive �Ƿ��ǻ�׼�����ù淶 ��1,��0��
     * @param standardActive �Ƿ��Ǳ�׼���ù淶 ��1,��0��
     * @param baselineID ��׼�ߵ�BsoID��
     * @param configItemID ���ù淶��BsoID��
     * @param viewObjectID ��ͼ�����bsoID��
     * @param effectivityType ��Ч�����͡�
     * @param effectivityUnit ��Ч�Ե�λ ��Ҫ�־û���
     * @param state ״̬��
     * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true��
     * @return PartConfigSpecIfc ��װ�õ��㲿�����ù淶ֵ����
     * @throws QMException
     */
    public PartConfigSpecIfc hashtableToPartConfigSpecIfc(String isUseCache,
            String effectivityActive, String baselineActive,
            String standardActive, String baselineID, String configItemID,
            String viewObjectID, String effectivityType,
            String effectivityUnit, String state, String workingIncluded)
            throws QMException;
    //CR2 End 20090619 zhangq

    /**
     * �����ĵ�ֵ�����ѯ���ݿ⣬��ȡ���б����ĵ����������㲿���ļ��ϡ�
     * ���flag = true, ��ʾֻ���ع�������һ�ߵ��㲿��(QMPartIfc)�ļ��ϣ����flag = false
     * ���ع�����(PartDescribeLinkIfc)�ļ��ϡ�
     * @param docIfc DocIfc �ĵ�ֵ����
     * @param flag boolean ��������ֵ�Ľṹ��
     * @return Collection
     * @throws QMException
     */
    public Collection getDescribesQMParts(DocIfc docIfc, boolean flag)
            throws QMException;

    /**
     * ��ȡ����ֱ��ʹ�õ�ǰ������㲿����
     * �����ݵ�ǰ�Ĵ�������QMPartIfc�ҵ���Ӧ��PartMaster��
     * �ٵ���getUsedByParts(QMPartMasterIfc partMasterIfc):Collection
     * �����ҵ����е�ʹ�ø�����ļ��ϡ�
     * @param partIfc QMPartIfc
     * @return Vector
     * @throws QMException
     */
    public Vector getParentParts(QMPartIfc partIfc) throws QMException;

    /**
     * add by ������ 2004.5.24 ��ø����㲿�������Ϊ��ʷ����������A���Ϊ�õ�B��
     * B���Ϊ�õ�C�������c��bsoID����Եõ�B��A����Ϣ��
     *
     * @param partID String
     * @return java.util.Collection
     */
    public Collection findAllPreParts(String partID);

    /**
     * add by ������ 2004.6.14 ����ɸ����㲿�����������㲿�������ɸ����㲿�����Ϊ�õ����㲿����
     *
     * @param partID String
     * @return java.util.Collection
     */
    public Collection findAllSaveAsParts(String partID);

    /**
     * ����ָ���ľ����㲿����ȡ����������Ϊ��Ʒ�ĸ����㲿�������°汾��
     * @param qmPartIfc QMPartIfc �����㲿����
     * @throws QMException
     * @return Collection �����㲿�������°汾��
     */
    public Collection getParentProduct(QMPartIfc qmPartIfc) throws QMException;

    /**
     * ��ȡ���㲿���ڸ��㲿���е�ʹ��������
     * @param parentPartIfc QMPartIfc ���㲿����
     * @param childPartIfc QMPartIfc ���㲿����
     * @throws QMException
     * @return String ʹ��������
     */
    public String getPartQuantity(QMPartIfc parentPartIfc,
            QMPartIfc childPartIfc) throws QMException;

    /**
     * ��ȡ���㲿���ڸ��㲿���е�ʹ��������
     * @param parentPartIfc QMPartIfc ���㲿����
     * @param middlePartMasterIfc QMPartMasterIfc �м��㲿��������Ϣ��
     * @param childPartIfc QMPartIfc ���㲿����
     * @throws QMException
     * @return String ʹ��������
     */
    public String getPartQuantity(QMPartIfc parentPartIfc,
            QMPartMasterIfc middlePartMasterIfc, QMPartIfc childPartIfc)
            throws QMException;

    /**
     * ��ָ����Ʒ�У���ȡָ�����㲿�������и��㲿����
     * @param parentPartMasterIfc QMPartMasterIfc ��Ʒ������Ϣ��
     * @param childPartMasterIfc QMPartMasterIfc �ò�Ʒ�е����㲿��������Ϣ��
     * @throws QMException
     * @return HashMap ���㲿������,����PartNumber��ֵ��ֵ����
     */
    public HashMap getParentPartsFromProduct(
            QMPartMasterIfc parentPartMasterIfc,
            QMPartMasterIfc childPartMasterIfc) throws QMException;

    /**
     * Ϊҵ���������Ĭ���������ڡ�
     * @param basevalue BaseValueIfc
     * @throws QMException
     * @return BaseValueIfc
     */
    public BaseValueIfc setLifeCycle(BaseValueIfc basevalue) throws QMException;

    /**
     * ��ȡ�������ù淶���㲿����
     * @param partMasterIfc QMPartMasterIfc �㲿������Ϣ��
     * @param partConfigSpecIfc PartConfigSpecIfc ���ù淶��
     * @throws QMException
     * @return QMPartIfc
     */
    public QMPartIfc getPartByConfigSpec(QMPartMasterIfc partMasterIfc,
            PartConfigSpecIfc partConfigSpecIfc) throws QMException;

    /**
     * �����Ʒ�������Ӽ��ڲ�Ʒ��ʹ�õ�������
     * @param product
     * @param partConfigSpecIfc
     * @return HashMap �����Ӽ���bsoid��ֵ��һ�����飬��һλ���Ӽ���ֵ���󣬵�2λ
     ���Ӽ��ڲ�Ʒ�е�ʹ��������
     * @throws QMException
     */
    public HashMap getAllSubCounts(QMPartIfc product,
            PartConfigSpecIfc partConfigSpecIfc) throws QMException;

    /**
     * ����ָ���ľ����㲿�����ϻ�ȡ����������Ϊ��Ʒ�ĸ����㲿�������°汾��
     * @param Collection QMPartIfcs �����㲿�����ϡ�
     * @throws QMRemoteException
     * @throws QMException
     * @return Collection �����㲿�������°汾���ϡ�
     */
    public Collection getParentProduct(Collection qmPartIfcs)
            throws QMException;

    /**
     * ���������㲿�����ݿͻ��˸����㲿����11�����������㲿���Ĺ��ܣ�֧��ģ����ѯ�ͷǲ�ѯ��
     * �������ĵ������㲿�������У�Ҳ������������Ҫ���ݶ����Բ�ѯ�㲿����������
     * @param partnumber
     * @param checkboxNum
     * @param partname
     * @param checkboxName
     * @param partver
     * @param checkboxVersion
     * @param partview
     * @param checkboxView
     * @param partstate
     * @param checkboxLifeCState
     * @param parttype
     * @param checkboxPartType
     * @param partby
     * @param checkboxProducedBy
     * @param partproject
     * @param checkboxProject
     * @param partfolder
     * @param checkboxFolder
     * @param partcreator
     * @param checkboxCreator
     * @param partupdatetime
     * @param checkboxModifyTime
     * @return �㲿��ֵ���󼯺ϡ�
     * @throws QMException
     */
    public Collection findAllPartInfo(String partnumber, String checkboxNum,
            String partname, String checkboxName, String partver,
            String checkboxVersion, String partview, String checkboxView,
            String partstate, String checkboxLifeCState, String parttype,
            String checkboxPartType, String partby, String checkboxProducedBy,
            String partproject, String checkboxProject, String partfolder,
            String checkboxFolder, String partcreator, String checkboxCreator,
            String partupdatetime, String checkboxModifyTime)
            throws QMException;
    /**
     * 20070611 add whj for new request moth QMProductManager.isParentPart()
     * ���ָ���㲿�������и�����������Ϣֵ���󼯺ϡ�
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @return vector:Vector ָ���㲿�������и�����(ֱ��������)������Ϣֵ����ļ��ϡ�
     * @throws QMException
     */
    public Vector getAllParentsByPart(QMPartIfc partIfc) throws QMException;
    /**
     *  20070611 add whj for new request moth QMProductManager.isParentPart()
     * ��ȡ����ֱ��ʹ�õ�ǰ������㲿����
     * �����ݵ�ǰ�Ĵ�������QMPartIfc�ҵ���Ӧ��PartMaster��
     * �ٵ���getUsedByParts(QMPartMasterIfc partMasterIfc):Collection
     * �����ҵ����е�ʹ�ø�����ļ��ϡ�
     * @param partIfc QMPartIfc
     * @throws QMException
     * @return Vector
     */
    public Vector getParentsByPart(QMPartIfc partIfc) throws QMException;
    /**
     * 20070611 add whj for new request moth QMProductManager.isParentPart()
     * ����ָ����QMPartMasterIfc����
     * ͨ�����������ϲ�ѯ�����PartUsageLink������QMPartMasterIfc���������°汾
     * ��QMPartIfc����ļ��ϡ�
     * @param partMasterIfc :QMPartMasterIfc����
     * @return collection ��partMasterIfc��PartUsageLink���й��������°汾QMPartIfc����ļ��ϡ�
     * @throws QMException
     */
    public Collection getUsedByPParts(QMPartMasterIfc partMasterIfc) throws QMException;

    /**
     * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
     * ������������bianli()����ʵ�ֵݹ顣
     * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
     * 1��������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
     * 2������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�

     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����.
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼                                                                                     �������
     * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Լ��ϣ�����Ϊ�ա�
     * @param routeNames : String[] ���ƵĹ���·�������ϣ�����Ϊ�ա�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õĶԵ�ǰ�㲿����ɸѡ������
     * @throws QMException
     * @return Vector
     * @author liunan 2008-08-12
     */
    public Vector setBOMList(QMPartIfc partIfc, String attrNames[],
                             String affixAttrNames[], String[] routeNames,
                             String source, String type,
                             PartConfigSpecIfc configSpecIfc) throws QMException ;

    /**
     * �ּ������嵥����ʾ������IBA�͹���·�ߡ�
     * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ��Object[]��
     * 0����ǰpart��BsoID��ʾλ�ã�Ϊҳ�泬����ʹ�ã�
     * 1����ǰpart��BsoID��
     * 2����ǰpart���ڵļ���
     * 3-...�������������ԣ�
     * ���Ԫ�أ���ǰpart�ĸ�����š�
     * �����������˵ݹ鷽����fenji(String parentPartNum, QMPartIfc partIfc, int level, PartUsageLinkIfc partUsageLinkIfc);
     * ΪPart-Other-classifylisting-001.jspʹ�á�
     * @param partIfc :QMPartIfc ����Ĳ���ֵ����
     * @param attrNameArray :String[] ���Ƶ����ԣ�����Ϊ�ա�
     * @throws QMException
     * @return Vector
     */
    public Vector setMaterialList(QMPartIfc partIfc, String attrNames[]) throws//CR1����д������߼���
        QMException;

  /**
   * liuming add 20070209
   * �ּ������嵥����ʾ��
   * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
   * 0����ǰpart��BsoID��
   * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
   * 2����ǰpart�ı�ţ�
   * 3����ǰpart�����ƣ�
   * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
   * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
   *                ������������ԣ��������ж��Ƶ����Լӵ���������С�
   * �����������˵ݹ鷽����
   * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
   * PartUsageLinkIfc partLinkIfc, int parentQuantity);
   * @param partIfc :QMPartIfc ����Ĳ���ֵ����
   * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա�
   * @param affixAttrNames : String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
   * @param routeNames : String[] ���ƵĹ���·�������ϣ�����Ϊ�ա�
   * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
   * @throws QMException
   * @return Vector
   * @author liunan 2008-08-12
   */
  public Vector setMaterialList2(QMPartIfc partIfc, String attrNames[],
                                String affixAttrNames[], String[] routeNames,
                                PartConfigSpecIfc configSpecIfc) throws
      QMException;

    /**
     * �������ù淶���˳��������ù淶��collection������QMPartMasterIfc�Ĺ����QMPartIfc�����С�汾��
     * @param masterIfc MasteredIfc �������ֵ����
     * @param configSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return Collection
     * @author liunan 2008-08-01
     */
    public Collection filteredIterationsOf(MasteredIfc masterIfc,
        PartConfigSpecIfc configSpecIfc) throws QMException;

  /**
   * �������嵥����ɱ����ļ������ڷּ�����������
   * ��com.faw_qm.part.client.other.controller.MaterialController�����˴˷�����
   * @param map HashMap ����õı������Լ��ϡ�
   * @throws QMException
   * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
   * @author liunan 2008-08-01
   */
  public String getExportBOMClassfiyString(HashMap map) throws QMException;

  /**
   * �������嵥����ɱ����ļ��������޺ϼ�װ���������
   * ��com.faw_qm.part.client.other.view.LogicBomFrame�����˴˷�����
   * @param map HashMap ����õı������Լ��ϡ�
   * @throws QMException
   * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
   * @author liunan 2008-08-01
   */
  public String getExportBOMClassfiyString2(HashMap map) throws QMException;

  /**
   * �������嵥����ɱ����ļ�������ͳ�Ʊ���������
   * ��com.faw_qm.part.client.other.controller.MaterialController�����˴˷�����
   * @param map HashMap ����õı������Լ��ϡ�
   * @throws QMException
   * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
   * @author liunan 2008-08-01
   */
  public String getExportBOMStatisticsString(HashMap map) throws QMException;
  
  //CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��,����"��������"  
  /*
   *quxg ��� ,�����ĳ���ṹ�����µ������Ӳ���������,��Ϊ��part baoid,ֵΪpart����
   *��ĳ���������ظ����ֶ��ʱ,��������. �˷���������Ҫ�������Ӳ�������,����ļ�����������
   *�Ǽ���һ����part,����һ����part��Ҫ�������ṹ��չ��һ��,��������������partʱ,��չ�����
   *�˷����������ṹһ��չ��,����Ӧ��part���������浽hashmap��,�����������Ӳ�������ʱ,ֻ��
   *map��ȡ������,Ч�ʻ���ߺܶ�
   */
  public HashMap getSonPartsQuantityMap(QMPartIfc parentPartIfc) throws
      QMException;
//CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��,����"��������"  

//CCBegin by liunan 2008-10-4 �򲹶�200816
    //����(1)20080811 zhangq begin �޸�ԭ���ڲ����㲿���ı����ڲ�Ʒʱ����ʾ����ȷ����TD-1794��
    /**
     * ����ָ���㲿������Ʒ��BsoId��ָ����ɸѡ��������㲿���ڸ�ɸѡ�����±��ò�Ʒ��ʹ�õĽṹ��
     * ʹ�ý�������ڷ���ֵvector�С�
     * @param partIfc :QMPartIfc ָ�����㲿��ֵ����
     * @param productBsoId :String ʹ�ø��㲿���Ĳ�Ʒ��BsoId��
     * @param configSpecIfc :PartConfigSpecIfc ָ����ɸѡ������
     * @return vector:Vector ������������ʹ�õ���Ϣ���ϡ�
     * vector����ֵ�����ݽṹΪ��vector�е�ÿ��Ԫ�ض���Vector���͵ģ�����Ϊ���������㣬����ΪsubVector.
     * ��subVector��ÿ��Ԫ�ض���String[5]���͵ġ�
     * ���String[5]�ֱ��¼��:<br>
     * String[0]:��κţ�<br>
     * String[1]:�㲿�����(�㲿������)�汾(��ͼ);<br>
     * String[2]:�㲿���ڴ˽ṹ��(��������)ʹ�õ�������������ͬһ�ṹ�µļ�¼ʹ����������ͬ�ģ��㲿����ͬһ�Ӽ���ʹ�������ϲ���<br>
     * String[3]:�㲿����BsoId��<br>
     * String[4]:�㲿��ֵ����<br>
     * @throws QMException
     * @see QMPartInfo
     * @see PartConfigSpecInfo
     */
    public Vector getUsedProductStruct(QMPartIfc partIfc,String productBsoId, 
    		PartConfigSpecIfc configSpecIfc) throws QMException;
    //����(1)20080811 zhangq end
    //CCEnd by liunan 2008-10-4
    //add whj
    /**
     * ��ȡ��ǰ�û��ġ���ʾ���á���Ϣ
     * @return ����ʾ���á�ֵ����
     */
    public PartAttrSetIfc getCurUserInfo()
    throws QMException;
    /**
	 * ��ȡָ�������EPM�ĵ�
	 * @param baseIfc
	 * @return EPM�ĵ�ֵ���󼯺�
	 * @throws QMException
	 *@see Vector
	 */
    public Vector getEMPDocument(BaseValueIfc baseIfc)
    throws QMException;
    
	//CCBegin by leix	 2010-12-20  �����߼��ܳ��������� 
    public String getExportBOMClassfiyLogicString(HashMap map) throws QMException ;
	//CCEnd by leix	 2010-12-20  �����߼��ܳ���������
    //CCBegin SS1
    /**
     * �ּ������嵥����ʾ��
     * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
     * 0����ǰpart��BsoID��
     * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
     * 2����ǰpart�ı�ţ�
     * 3����ǰpart�����ƣ�
     * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
     * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
     *                ������������ԣ��������ж��Ƶ����Լӵ���������С�
     * �����������˵ݹ鷽����
     * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
     * PartUsageLinkIfc partLinkIfc, int parentQuantity);
     * @param partIfc :QMPartIfc ����Ĳ���ֵ����
     * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա�
     * @param affixAttrNames : String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
     * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
     * @throws QMException
     * @return Vector
     */
    public Vector setMaterialListERP(QMPartIfc partIfc, String[] attrNames,
            String[] affixAttrNames, PartConfigSpecIfc configSpecIfc)
            throws QMException;
    //CCEnd SS1
    

	//CCBegin SS2
	/**
	 * ͨ��masterid��ȡ���°汾���㲿����
	 * @param masterID masterid
	 * @return QMPartInfo ���°汾���㲿��(QMPart);
	 * @throws QMException
	 * @see QMPartInfo
	 */
	public QMPartInfo getPartByMasterID(String masterID) throws QMException;
	//CCEnd SS2  
//  CCBegin SS3   
    public boolean isSubNode(QMPartIfc part,QMPartIfc subpart);
    
    public QMPartIfc findSubPartMaster(String partNumber) throws QMException;
//    CCEnd SS3
  
  //CCBegin SS4
  public String getExportFirstLeveList(HashMap map) throws QMException ;
  //CCEnd SS4
  
  public Collection sort(Collection collection);
  
  //CCBegin SS5
  public ArrayList setBOMList(QMPartIfc partIfc, String[] attrNames,
          String[] affixAttrNames, String[] routeNames,
          String source, String type,
          PartConfigSpecIfc configSpecIfc,
          String routeDepartment) throws QMException;
  
  //CCEnd SS5
  
  
}
