/** ���ɳ��� PartServiceRequest.java    1.0    2004/11/02
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/27 л�� ԭ��:�߼�����
 *                     ����:��д����
 *                     ��ע:���v3r11-�ּ������嵥�����Ż�-л��
 * CR2 20090619 ��ǿ �޸�ԭ�򣺽��нṹ�ȽϺ󣬲�Ʒ��Ϣ���������ù淶��ʾΪ�ṹ�Ƚ�Ŀ��������ù淶����TD-2190�� 
 *                   ������������нṹ�ȽϺ󣬲�Ʒ��Ϣ��������ȷ��ʾ�����ù淶��
 * SS1 2013-1-21  ��Ʒ��Ϣ����������������嵥�����������������erp���Ա����ܣ������ĸ�ʽ�ļ����ݰ������㲿��������š������������������ 
                  ÿ��������������λ����Դ������·�ߡ�װ��·�ߡ���T���ܡ��̶���ǰ�ںϼơ��ɱ���ǰ�ڣ���
                  ���к����������е�������Ҫ���㲿�������Ĺ��տ��л�ȡ
 */
package com.faw_qm.part.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.auth.RequestHelper;
import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.baseline.model.BaselineableIfc;
import com.faw_qm.change.model.ChangeableIfc;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.eff.model.EffConfigurationItemIfc;
import com.faw_qm.eff.model.EffManagedVersionIfc;
import com.faw_qm.eff.util.EffGroup;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartAlternateLinkIfc;
import com.faw_qm.part.model.PartAttrSetInfo;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartDescribeLinkInfo;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkInfo;
import com.faw_qm.part.model.PartSubstituteLinkIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.visitor.Walker;
import com.faw_qm.wip.model.WorkableIfc;


//CCBegin by liunan 2008-08-01
import com.faw_qm.version.model.MasteredIfc;
//CCBegin by liunan 2008-08-01

/**
 *
 *  Title: �ṩ��������PartService�ķ����Ľӿڣ����������ǿͻ��˵��û��Ƿ���˵��á� 
 *  Description: ���ķ�����ǩ��������PartService��һ�µģ�ֻ��������������ʵ����Щ������ҵ���߼������ǰѷ������Ͳ��������Լ��������ݸ�helper��request�������ɸ÷���ȥ�����ĵ��÷��� 
 *  Copyright: Copyright (c) 2004 
 *  Company: һ������ 
 * @author л��
 * @version 1.0
 */
//����(1)20080808 ��ǿ�޸� �޸�ԭ���ڲ����㲿���ı����ڲ�Ʒʱ����ʾ����ȷ����TD-1794��
public class PartServiceRequest implements Serializable
{
  private static RequestHelper helper = new RequestHelper();
  private static String STANDARD = "StandardPartService";
  private static String EXTENDED = "ExtendedPartService";
  private static String ENTERPRISE = "EnterprisePartService";
  static final long serialVersionUID = 1L;

  /**
   * ���캯����
   */
  PartServiceRequest()
  {
  }

//�����Ǳ�׼�湦�ܡ�

  /**
   * ͨ���㲿�������ֺͺ�������㲿��������Ϣ�����صļ�����ֻ��һ��QMPartMasterIfc����
   * @param partName :String �㲿�������ơ�
   * @param partNumber :String �㲿���ĺ��롣
   * @return collection :���ҵ���QMPartMasterIfc�㲿������Ϣ����ļ��ϣ�ֻ��һ��Ԫ�ء�
   * @throws QMException
   */
  public static Collection findPartMaster(String partName, String partNumber)
      throws QMException
  {
    Class[] paraClass = {String.class, String.class};
    Object[] para = {partName, partNumber};
    Collection collection = (Collection)helper.request(STANDARD, "findPartMaster", paraClass, para);
    return collection;
  }

  /**
   * ͨ��һ���㲿������Ϣ�ҵ����㲿�����а汾ֵ����ļ��ϡ�
   * @param partMasterIfc :QMPartMasterIfc����
   * @return collection :Collection ���ж�Ӧ��partMasterIfc���㲿������(QMPartIfc)�ļ��ϡ�
   * @throws QMException
   */
  public static Collection findPart(QMPartMasterIfc partMasterIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class};
    Object[] para = {partMasterIfc};
    Collection collection = (Collection)helper.request(STANDARD,"findPart", paraClass, para);
    return collection;
  }

  /**
   * ɾ���ο��ĵ����㲿���Ĺ�����ϵ��
   * @param linkIfc PartReferenceLinkIfc
   * @throws PartException
   * @throws QMException
   * @see PartReferenceLinkInfo
   */
  public static void deletePartReferenceLink(PartReferenceLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartReferenceLinkIfc.class};
    Object[] para = {linkIfc};
    helper.request(STANDARD,"deletePartReferenceLink", paraClass, para);
  }

  /**
   * ����ο��ĵ����㲿���Ĺ�����ϵ��
   * @param linkIfc PartReferenceLinkIfc 
   * @throws PartException
   * @return PartReferenceLinkIfc �ο��ĵ����㲿���Ĺ�����ϵ�Ķ���
   * @throws QMException
   * @see PartReferenceLinkInfo
   */
  public static PartReferenceLinkIfc savePartReferenceLink(PartReferenceLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartReferenceLinkIfc.class};
    Object[] para = {linkIfc};
    PartReferenceLinkIfc partReferenceLinkIfc = (PartReferenceLinkIfc)helper.request(STANDARD,"savePartReferenceLink", paraClass, para);
    return partReferenceLinkIfc;
  }

  /**
   * �����㲿�����㲿������Ϣ�Ĺ�����ϵ��
   * @param linkIfc PartUsageLinkIfc
   * @return PartUsageLinkIfc �㲿�����㲿������Ϣ�Ĺ�����ϵ�Ķ���
   * @throws QMException
   * @see PartUsageLinkInfo
   */
  public static PartUsageLinkIfc savePartUsageLink(PartUsageLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartUsageLinkIfc.class};
    Object[] para = {linkIfc};
    PartUsageLinkIfc partUsageLinkIfc = (PartUsageLinkIfc)helper.request(STANDARD,"savePartUsageLink", paraClass, para);
    return partUsageLinkIfc;
  }

  /**
   * ɾ���㲿�����㲿������Ϣ�Ĺ�����ϵ��
   * @param linkIfc PartUsageLinkIfc
   * @throws QMException
   *  @see PartUsageLinkInfo
   */
  public static void deletePartUsageLink(PartUsageLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartUsageLinkIfc.class};
    Object[] para = {linkIfc};
    helper.request(STANDARD,"deletePartUsageLink", paraClass, para);
  }

  /**
   * ���������ĵ����㲿���Ĺ�����ϵ��
   * @param linkIfc PartDescribeLinkIfc
   * @throws PartException
   * @return PartDescribeLinkIfc �����ĵ����㲿���Ĺ�����ϵ�Ķ���
   * @throws QMException
   *  @see PartDescribeLinkInfo
   */
  public static PartDescribeLinkIfc savePartDescribeLink(PartDescribeLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartDescribeLinkIfc.class};
    Object[] para = {linkIfc};
    PartDescribeLinkIfc partDescribeLinkIfc = (PartDescribeLinkIfc)helper.request(STANDARD,"savePartDescribeLink", paraClass, para);
    return partDescribeLinkIfc;
  }

  /**
   * ɾ�������ĵ����㲿���Ĺ�����ϵ��
   * @param linkIfc PartDescribeLinkIfc
   * @throws QMException
   */
  public static void deletePartDescribeLink(PartDescribeLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartDescribeLinkIfc.class};
    Object[] para = {linkIfc};
    helper.request(STANDARD,"deletePartDescribeLink", paraClass, para);
  }

  /**
   *  �����㲿��ֵ�����ø��㲿�����������вο��ĵ�(DocIfc)���°汾��ֵ����ļ��ϡ� 
   *  �ȸ����㲿����ȡ���еĲο��ĵ�����Ϣ(DocMasterIfc)�ļ���,�ٶ�DocMasterIfc����
   * ���й��ˣ��ҳ�ÿ��DocMasterIfc����Ӧ�����°汾DocIfc����󷵻�DocIfc�ļ��ϡ� 
   * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
   * @return Vector �㲿���ο��ĵ�(DocIfc)ֵ����ļ��ϡ�
   * @see QMPartInfo
   * @throws QMException
   * 
   */
  public static Vector getReferencesDocMasters(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Vector vector = (Vector)helper.request(STANDARD,"getReferencesDocMasters", paraClass, para);
    return vector;
  }

  /**
   *  ����ָ�����㲿����ֵ���󷵻ظ��㲿�������ĵ���ֵ���󼯺ϡ� 
   *  �����־flagΪtrue,���صĽ��Vector����DocIfc�ļ��ϣ� 
   *  ���flagΪfalse�����ص�Vector��PartDescribeLinkIfc�ļ��ϡ� 
   * @param partIfc :QMPartIfc�㲿����ֵ����
   * @param flag : boolean
   * @return vector �����־flagΪtrue,���صĽ������DocIfc�ļ��ϣ� 
   *  ���flagΪfalse�����ص���PartDescribeLinkIfc�ļ���
   * @throws QMException
   */
  public static Vector getDescribedByDocs(QMPartIfc partIfc, boolean flag)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, boolean.class};
    Object[] para = {partIfc, new Boolean(flag)};
    Vector Vector = (Vector)helper.request(STANDARD,"getDescribedByDocs", paraClass, para);
    return Vector;
  }

  /**
   * �������ĵ���ֵ�����ñ��ο����㲿����ֵ����ļ��ϡ�
   * @param docMasterIfc :DocMasterIfc �ο����ĵ���ֵ����
   * @return vector :Vector ���ĵ��ο����㲿����ֵ����ļ��ϡ�
   * @see DocMasterInfo
   * @throws QMException
   * 
   */
  public static Vector getReferencedByParts(DocMasterIfc docMasterIfc)
      throws QMException
  {
    Class[] paraClass = {DocMasterIfc.class};
    Object[] para = {docMasterIfc};
    Vector Vector = (Vector)helper.request(STANDARD,"getReferencedByParts", paraClass, para);
    return Vector;
  }

  /**
   * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
   * @param partIfc :QMPartIfc �㲿����ֵ����
   * @return collection ��partIfc������PartUsageLinkIfc�Ķ��󼯺ϡ�
   * @see QMPartInfo
   * @throws QMException
   * 
   */
  public static Collection getUsesPartMasters(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getUsesPartMasters", paraClass, para);
    return collection;
  }

  /**
   * ����ָ�����㲿�������¼��㲿�������°汾��ֵ����ļ��ϡ�
   * @param partIfc :QMPartIfc �㲿����ֵ����
   * @return collection  partIfcʹ�õ��¼��Ӽ������°汾��ֵ����(	QMPartInfo)���ϡ�
   * @see QMPartInfo
   * @throws QMException
   */
  public static Collection getSubParts(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getSubParts", paraClass, para);
    return collection;
  }

  /**
   * ����ָ���㲿�����������¼��㲿�������°汾��ֵ���󼯺ϡ�
   * @param partIfc :QMPartIfc �㲿����ֵ����
   * @return collection  partIfcʹ�õ��¼��Ӽ������°汾��ֵ����(QMPartInfo)���ϡ�
   *  @see QMPartInfo
   * @throws QMException
   */
  public static Collection getAllSubParts(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getAllSubParts", paraClass, para);
    return collection;
  }

  /**
   * ����ָ����QMPartMasterIfc����ͨ�����������ϲ�ѯ�����PartUsageLink������QMPartMasterIfc���������°汾
   * ��QMPartIfc����ļ��ϡ�
   * @param partMasterIfc :QMPartMasterIfc����
   * @return collection ��partMasterIfc��PartUsageLink���й��������°汾QMPartIfc����ļ��ϡ�
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public static Collection getUsedByParts(QMPartMasterIfc partMasterIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class};
    Object[] para = {partMasterIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getUsedByParts", paraClass, para);
    return collection;
  }

  /**
   * ͨ��ָ����ɸѡ������������collection�е�PartUsageLinkIfc�����Ӧ��
   * ����ɸѡ������Iterated��������ɸѡ�����������ɸѡ�����򷵻ض�Ӧ��Mastered���㲿����
   * @param collection :Collection ��PartUsageLinkIfc����ļ��ϡ�
   * @param configSpecIfc :PartConfigSpecIfc �㲿����ɸѡ������
   * @return collection ÿ��Ԫ��Ϊһ������:����ĵ�һ��Ԫ��ΪPartUsageLinkIfc���󣬵ڶ���Ԫ��ΪQMPartIfc����
   * ���û��QMPartIfc����Ϊ������QMPartMasterIfc����
   * @see PartConfigSpecInfo
   * @throws QMException
   */
  public static Collection getUsesPartsFromLinks(Collection collection, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {Collection.class, PartConfigSpecIfc.class};
    Object[] para = {collection, configSpecIfc};
    Collection collection2 = (Collection)helper.request(STANDARD,"getUsesPartsFromLinks", paraClass, para);
    return collection2;
  }

  /**
   * �����㲿����
   * @param partIfc :QMPartIfc Ҫ������㲿����ֵ����
   * @return partIfc:QMPartIfc �������㲿����ֵ����
   * @see QMPartInfo
   * @throws QMException
   */
  public static QMPartIfc savePart(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    QMPartIfc qmPartIfc = (QMPartIfc)helper.request(STANDARD,"savePart", paraClass, para);
    return qmPartIfc;
  }

  /**
   * ɾ��ָ�����㲿�����������������ʹ�øò��������쳣"���㲿���Ѿ�����������ʹ�ã�����ɾ����"��
   * @param partIfc :QMPartIfc Ҫɾ�����㲿����ֵ����
   * @see QMPartInfo
   * @throws QMException
   */
  public static void deletePart(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    helper.request(STANDARD,"deletePart", paraClass, para);
  }

  /**
   * �ж��㲿��partIfc2�Ƿ����㲿��partIfc1�����Ȳ�������partIfc1����
   * @param partIfc1 :QMPartIfc ָ�����㲿����ֵ����
   * @param partIfc2 :QMPartIfc ��������㲿����ֵ����
   * @return flag:boolean:
   * <br>flag==true:�㲿��part2���㲿��part1�����Ȳ�������part1����
   * <br>flag==false:�㲿��part2�����㲿��part1�����Ȳ�����part1����
   * @see QMPartInfo
   * @throws QMException
   */
  public static boolean isParentPart(QMPartIfc partIfc1, QMPartIfc partIfc2)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, QMPartIfc.class};
    Object[] para = {partIfc1, partIfc2};
    boolean flag = ((Boolean)helper.request(STANDARD,"isParentPart", paraClass, para)).booleanValue();
    return flag;
  }

  /**
   * ���ָ���㲿�������и�����������Ϣֵ���󼯺ϡ�
   * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
   * @return vector:Vector ָ���㲿�������и�����(ֱ��������)������Ϣֵ����(QMPartMasterInfo)�ļ��ϡ�
   * @see QMPartInfo
   * @throws QMException
   */
  public static Vector getAllParentParts(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Vector vector = (Vector)helper.request(STANDARD,"getAllParentsByPart", paraClass, para);
    return vector;
  }

  /**
   *  ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ�� 
   *  ������������bianli()����ʵ�ֵݹ顣 
   * 
   * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
   * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
   * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Լ��ϣ�����Ϊ�ա�
   * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
   * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
   * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õĶԵ�ǰ�㲿����ɸѡ������
   * @throws QMException
   * @return Vector �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
   *<br> 1��������������ԣ�BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
   *<br>2������������ԣ�BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�
   *@see QMPartInfo
   *@see PartConfigSpecInfo
   */
  public static Vector setBOMList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
                           String source, String type, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, String[].class, String[].class, String.class, String.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, attrNames, affixAttrNames, source, type, configSpecIfc};
    Vector vector = (Vector)helper.request(STANDARD,"setBOMList", paraClass, para);
    return vector;
  }
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
  public static Vector setMaterialList(QMPartIfc partIfc, String[] attrNames)//Begin CR1
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, String[].class};
    Object[] para = {partIfc, attrNames};
    Vector vector = (Vector)helper.request(STANDARD,"setMaterialList", paraClass, para);
    return vector;
  }//End CR1
    //CCBegin SS1
  /**
   * <p>�ּ������嵥����ʾ��</p>
   * <p>���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�</p>
   * <p>0����ǰpart��BsoID��</p>
   * <p>1����ǰpart���ڵļ���ת��Ϊ�ַ��͡�</p>
   * <p>2����ǰpart�ı�š�</p>
   * <p>3����ǰpart�����ơ�</p>
   * <p>4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��͡�</p>
   * <p>5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��</p>
   * <p>              ������������ԣ��������ж��Ƶ����Լӵ���������С�</p>
   * <p>�����������˵ݹ鷽����</p>
   * <p>fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc, PartUsageLinkIfc partLinkIfc, int parentQuantity)��</p>
   * @param partIfc :QMPartIfc ����Ĳ���ֵ����
   * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա�
   * @param affixAttrNames : String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
   * @param configSpecIfc :PartConfigSpecIfc �ṹɸѡ������
   * @throws QMException
   * @return Vector
   */
  public static Vector setMaterialListERP(QMPartIfc partIfc, String[] attrNames,
                                       String[] affixAttrNames,
                                       PartConfigSpecIfc configSpecIfc) throws
      QMException {
    Class[] paraClass = {
        QMPartIfc.class, String[].class, String[].class, PartConfigSpecIfc.class};
    Object[] para = {
        partIfc, attrNames, affixAttrNames, configSpecIfc};
    Vector vector = (Vector) helper.request(STANDARD, "setMaterialListERP",
                                            paraClass, para);
    return vector;
  }
  //CCEnd SS1
  /**
   *  ����ָ���㲿����ָ����ɸѡ��������㲿���ڸ�ɸѡ�����±���Щ������ʹ�á� 
   *  ʹ�ý�������ڷ���ֵvector�С�
   *
   * @param partIfc :QMPartIfc ָ�����㲿��ֵ����
   * @param configSpecIfc :PartConfigSpecIfc ָ����ɸѡ������
   * @return vector:Vector ������������ʹ�õ���Ϣ���ϡ�
   *  vector����ֵ�����ݽṹΪ��vector�е�ÿ��Ԫ�ض���Vector���͵ģ�����Ϊ���������㣬����ΪsubVector��
   * ��subVector��ÿ��Ԫ�ض���String[]���͵�:
   * <br> String[0]:��κš� 
   * <br> String[1]:�㲿�����(�㲿������)�汾(��ͼ)�� 
   * <br> String[2]�㲿���ڴ˽ṹ��(��������)ʹ�õ�������������ͬһ�ṹ�µļ�¼ʹ����������ͬ�ģ��㲿����ͬһ�Ӽ���ʹ�������ϲ���
   * @see QMPartInfo
   * @see PartConfigSpecInfo 
   * @throws QMException
   */
  public static Vector setUsageList(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, configSpecIfc};
    Vector vector = (Vector)helper.request(STANDARD,"setUsageList", paraClass, para);
    return vector;
  }

  /**
   * ͨ��ָ���Ľṹɸѡ�����������ݿ���Ѱ��ʹ��partIfc����Ӧ��PartMasterIfc�����в���(QMPartIfc)��
   * @param partIfc �㲿��ֵ����
   * @param configSpecIfc �㲿���ṹɸѡ������
   * @return Collection ����ֵ����(PartUsageLinkIfc, QMPartIfc)ΪԪ�صļ���
   * @see QMPartInfo
   * @see PartConfigSpecInfo
   * @throws QMException
   */
  public static Collection getParentPartsByConfigSpec(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, configSpecIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getParentPartsByConfigSpec", paraClass, para);
    return collection;
  }

  /**
   * ����ָ���Ľṹɸѡ����Ϊ��ѯ�ռ���Ӳ�ѯ������
   * @param partConfigSpecIfc �㲿���ṹɸѡ����ֵ����
   * @param query QMQuery
   * @throws QMException
   * @throws QueryException
   * @return QMQuery ���ز�ѯ���ʽ
   * @see PartConfigSpecInfo
   * @see QMQuery
   */
  public static QMQuery appendSearchCriteria(PartConfigSpecIfc partConfigSpecIfc, QMQuery query)
      throws QMException, QueryException
  {
    Class[] paraClass = {PartConfigSpecIfc.class, QMQuery.class};
    Object[] para = {partConfigSpecIfc, query};
    QMQuery qmQuery = (QMQuery)helper.request(STANDARD,"appendSearchCriteria", paraClass, para);
    return qmQuery;
  }

  /**
   * ���ݽṹɸѡ�������˳����Ͻṹɸѡ������collection������QMPartMasterIfc�Ĺ����QMPartIfc�����С�汾��
   * @param collection Collection
   * @param partConfigSpecIfc PartConfigSpecIfc
   * @throws QMException
   * @return Collection ����QMPartMasterIfc�Ĺ����QMPartIfc�����С�汾
   * @see PartConfigSpecInfo
   */
  public static Collection filteredIterationsOf(Collection collection, PartConfigSpecIfc partConfigSpecIfc)
      throws QMException
  {
    Class[] paraClass = {Collection.class, PartConfigSpecIfc.class};
    Object[] para = {collection, partConfigSpecIfc};
    Collection collection2 = (Collection)helper.request(STANDARD,"filteredIterationsOf", paraClass, para);
    return collection2;
  }

  /**
   * ��ѯ��ǰ�û��Ľṹɸѡ������
   * @throws QMException
   * @return PartConfigSpecIfc ���ù淶
   *  @see PartConfigSpecInfo
   */
  public static PartConfigSpecIfc findPartConfigSpecIfc()
      throws QMException
  {
    Class[] paraClass = {};
    Object[] para = {};
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)helper.request(STANDARD,"findPartConfigSpecIfc", paraClass, para);
    return partConfigSpecIfc;
  }

  /**
   *  ����ָ���ṹɸѡ���������ָ��������ʹ�ýṹ�� 
   *   
   * @param partIfc QMPartIfc
   * @param configSpecIfc PartConfigSpecIfc
   * @throws QMException
   * @return Collection ���ؼ��ϵ�ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
   * ��1��Ԫ�ؼ�¼���������¼��use��ɫ��mastered����ƥ��ṹɸѡ������iterated����
   * ���û��ƥ����󣬼�¼mastered����
   *  @see PartConfigSpecInfo
   *  @see QMPartInfo
   */
  public static Collection getUsesPartIfcs(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, configSpecIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getUsesPartIfcs", paraClass, para);
    return collection;
  }

  /**
   * ���ݽṹɸѡ�����а�������Ϣ���˽�����ϡ�
   * 
   * @param configSpecIfc PartConfigSpecIfc
   * @param collection Collection ��Ҫ���˵Ķ��󼯺�
   * @throws QMException
   * @throws QueryException
   * @return Collection ���˺�Ľ������
   *<br> 1�������ǰ��Ч��������Ч��������Ч�Թ��˽�������ӷ�����
   * <br>2�������ǰ����������Ч�����û��߹��˽�������ӷ�����
   * <br>3�������ǰ��׼������Ч�����ñ�׼���˽�������ӷ�����
   * @see PartConfigSpecInfo
   */
  public static Collection process(PartConfigSpecIfc configSpecIfc, Collection collection)
      throws QMException, QueryException
  {
    Class[] paraClass = {PartConfigSpecIfc.class, Collection.class};
    Object[] para = {configSpecIfc, collection};
    Collection collection2 = (Collection)helper.request(STANDARD,"process", paraClass, para);
    return collection2;
  }

  /**
   * �������ù淶��
   *  <br>1������µĽṹɸѡ����û�г־û����棬Ϊ��ָ�������ߣ� 
   *   <br>2����ѯ���ݿ⣬��õ�ǰ�û��ľɵĽṹɸѡ������
   *   <br>3��������ھɽṹɸѡ�������ж��µĽṹɸѡ�����Ƿ�Ϊ�գ����ǿգ� 
   *     �����ݿ���ɾ���ɽṹɸѡ���������򣬽��½ṹɸѡ���������ݸ�ֵ���ɽṹɸѡ�����У��������ݿ⣻ 
   *   <br>4�����֮ǰ�����ھɵĽṹɸѡ���������½ṹɸѡ�����־û����档 
   * @param configSpecIfc PartConfigSpecIfc
   * @throws QMException
   * @return PartConfigSpecIfc ���������ù淶
   * @see PartConfigSpecInfo
   */
  public static PartConfigSpecIfc savePartConfigSpecIfc(PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {PartConfigSpecIfc.class};
    Object[] para = {configSpecIfc};
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)helper.request(STANDARD,"savePartConfigSpecIfc", paraClass, para);
    return partConfigSpecIfc;
  }

  /**
   *  ͨ�����ƺͺ���������㲿��������ģ����ѯ��
   *  ���nameΪnull���������ѯ�����numberΪnull�������Ʋ�ѯ��
   *  ���name��numnber��Ϊnull������������㲿����ֵ���� 
   * @param name :String ģ����ѯ���㲿�����ơ�
   * @param number :String ģ����ѯ���㲿���ĺ��롣
   * @return collection:Collection ���ϲ�ѯ���������㲿����ֵ����QMPartMasterInfo���ļ��ϡ�
   * @throws QMException
   */
  public static Collection getAllPartMasters(String name, String number)
      throws QMException
  {
    Class[] paraClass = {String.class, String.class};
    Object[] para = {name, number};
    Collection collection = (Collection)helper.request(STANDARD,"getAllPartMasters", paraClass, para);
    return collection;
  }

  /**
   *  ͨ�����ƺͺ���������㲿��������ģ����ѯ��
   *  ���nameΪnull���������ѯ�� 
   *  ���numberΪnull�������Ʋ�ѯ�� 
   *  ���name��numnber��Ϊnull������������㲿����ֵ����
   *  �������nameFlagΪtrue, �����㲿�����ƺ�name����ͬ����Щ�㲿���� 
   *  ���򣬲����㲿�����ƺ�name��ͬ���㲿�����Բ���numFlag��ͬ���Ĵ��� 
   * @param name ����ѯ���㲿�����ơ�
   * @param nameFlag ����������
   * @param number ����ѯ���㲿����š�
   * @param numFlag ����������
   * @return Collection ��ѯ���������㲿��(QMPartMasterIfc)�ļ��ϡ�
   * @throws QMException
   */
  public static Collection getAllPartMasters(String name, boolean nameFlag, String number, boolean numFlag)
      throws QMException
  {
    Class[] paraClass = {String.class, boolean.class, String.class, boolean.class};
    Object[] para = {name, new Boolean(nameFlag), number, new Boolean(numFlag)};
    Collection collection = (Collection)helper.request(STANDARD,"getAllPartMasters", paraClass, para);
    return collection;
  }

  /**
   * ����partIfcѰ�Ҷ�Ӧ��partMasterIfc,�ٲ���partUsageLink����ȡʹ���˸�partMasterIfc�������㲿����
   * @param partIfc :QMPartIfc �㲿��ֵ����
   * @exception QMException �־û�������쳣��
   * @return Collection QMPartIfc�ļ��ϡ�
   * @see QMPartInfo
   */
  public static Collection getPartsByUse(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getPartsByUse", paraClass, para);
    return collection;
  }

  /**
   *  ����ָ�����㲿��ֵ�����ɸѡ��������㲿���Ĳ�Ʒ�ṹ������Vector�� 
   *   
   * @param partIfc :��ǰ�㲿��ֵ����
   * @param configSpecIfc ��ǰ�㲿���ṹɸѡ����ֵ����
   * @return Vector Vector�д��NormalPart�Ķ���ļ��ϣ�ÿ��NormalPart�����е����԰�����
   * ����+����+�汾+��ͼ+����+��λ��ע�⣬����ֵVector�в�������ǰ����Ĳ���partIfc��
   * @see QMPartInfo
   * @see PartConfigSpecInfo
   * @throws QMException
   * 
   */
  public static Vector getProductStructure(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, configSpecIfc};
    Vector vector = (Vector)helper.request(STANDARD,"getProductStructure", paraClass, para);
    return vector;
  }

  /**
   *  ����ָ�����㲿��ֵ�����ɸѡ��������㲿�����ӡ�����Vector�� 
   *  
   * @param partIfc :��ǰ�㲿��ֵ����
   * @param configSpecIfc ��ǰ�㲿���ṹɸѡ����ֵ����
   * @return Vector  Vector�д��NormalPart�Ķ���ļ��ϣ�ÿ��NormalPart�����е����԰�����
   * ����+����+�汾+��ͼ+����+��λ��ע�⣬����ֵVector�в�������ǰ����Ĳ���partIfc��
   * @see QMPartInfo
   * @see PartConfigSpecInfo
   * @throws QMException
   */
  public static Vector getSubProductStructure(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, configSpecIfc};
    Vector vector = (Vector)helper.request(STANDARD,"getSubProductStructure", paraClass, para);
    return vector;
  }

  /**
   * �޸��㲿�����˷������ͻ���ֱ�ӵ��ã������ƣ���š�
   * @param partMasterIfc QMPartMasterIfcֵ����
   * @param flag �Ƿ��޸��㲿���ı��,trueΪ�޸�,false���޸ġ�
   * @return partMasterIfc �޸ĺ���㲿����ֵ����
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public static QMPartMasterIfc renamePartMaster(QMPartMasterIfc partMasterIfc, boolean flag)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class, boolean.class};
    Object[] para = {partMasterIfc, new Boolean(flag)};
    QMPartMasterIfc qmPartMasterIfc = (QMPartMasterIfc)helper.request(STANDARD,"renamePartMaster", paraClass, para);
    return qmPartMasterIfc;
  }

  //CR2 Begin 20090619 zhangq �޸�ԭ��TD-2190��
  /**
   * ������ɢ��������Ϣ����PartConfigSpecIfc����һ��ʮ����������
   * @param effectivityActive �Ƿ�����Ч�Խṹɸѡ���� ��1,��0��
   * @param baselineActive �Ƿ��ǻ��߽ṹɸѡ���� ��1,��0��
   * @param standardActive �Ƿ��Ǳ�׼�ṹɸѡ���� ��1,��0��
   * @param baselineID ���ߵ�BsoID��
   * @param configItemID �ṹɸѡ������BsoID��
   * @param viewObjectID ��ͼ�����bsoID��
   * @param effectivityType ��Ч�����͡�
   * @param effectivityUnit ��Ч�Ե�λ ��Ҫ�־û���
   * @param state ״̬��
   * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true��
   * @return PartConfigSpecIfc ��װ�õ��㲿���ṹɸѡ����ֵ����
   * @throws QMException
   */
  public static PartConfigSpecIfc hashtableToPartConfigSpecIfc(
      String effectivityActive, String baselineActive, String standardActive, String baselineID,
      String configItemID, String viewObjectID, String effectivityType, String effectivityUnit,
      String state, String workingIncluded)
      throws QMException
  {
//    Class[] paraClass = {String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
//    Object[] para = {effectivityActive, baselineActive, standardActive, baselineID, configItemID, viewObjectID, effectivityType, effectivityUnit, state, workingIncluded};
//    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)helper.request(STANDARD,"hashtableToPartConfigSpecIfc", paraClass, para);
    return hashtableToPartConfigSpecIfc( "1",
    	       effectivityActive,  baselineActive,  standardActive,  baselineID,
    	       configItemID,  viewObjectID,  effectivityType,  effectivityUnit,
    	       state,  workingIncluded);
  }

  /**
   * ������ɢ��������Ϣ����PartConfigSpecIfc����һ��ʮ����������
   * @param isUseCache �Ƿ��û�������ù淶�ı�־ ��1,��0��
   * @param effectivityActive �Ƿ�����Ч�Խṹɸѡ���� ��1,��0��
   * @param baselineActive �Ƿ��ǻ��߽ṹɸѡ���� ��1,��0��
   * @param standardActive �Ƿ��Ǳ�׼�ṹɸѡ���� ��1,��0��
   * @param baselineID ���ߵ�BsoID��
   * @param configItemID �ṹɸѡ������BsoID��
   * @param viewObjectID ��ͼ�����bsoID��
   * @param effectivityType ��Ч�����͡�
   * @param effectivityUnit ��Ч�Ե�λ ��Ҫ�־û���
   * @param state ״̬��
   * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true��
   * @return PartConfigSpecIfc ��װ�õ��㲿���ṹɸѡ����ֵ����
   * @throws QMException
   */
  public static PartConfigSpecIfc hashtableToPartConfigSpecIfc(String isUseCache,
      String effectivityActive, String baselineActive, String standardActive, String baselineID,
      String configItemID, String viewObjectID, String effectivityType, String effectivityUnit,
      String state, String workingIncluded)
      throws QMException
  {
    Class[] paraClass = {String.class,String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
    Object[] para = {isUseCache,effectivityActive, baselineActive, standardActive, baselineID, configItemID, viewObjectID, effectivityType, effectivityUnit, state, workingIncluded};
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)helper.request(STANDARD,"hashtableToPartConfigSpecIfc", paraClass, para);
    return partConfigSpecIfc;
  }
  //CR2 End 20090619 zhangq

  /**
   *  �����ĵ�ֵ�����ѯ���ݿ⣬��ȡ���б����ĵ����������㲿���ļ��ϡ�
   *  
   * @param docIfc DocIfc �ĵ�ֵ����
   * @param flag boolean ��������ֵ�Ľṹ��
   * @return Collection ���flag = true����ʾֻ���ع�������һ�ߵ��㲿��(QMPartIfc)�ļ��ϣ� 
   *  ���flag = false�����ع�����(PartDescribeLinkIfc)�ļ��ϡ� 
   *  @see DocInfo
   * @throws QMException
   */
  public static Collection getDescribesQMParts(DocIfc docIfc, boolean flag)
      throws QMException
  {
    Class[] paraClass = {DocIfc.class, boolean.class};
    Object[] para = {docIfc, new Boolean(flag)};
    Collection collection = (Collection)helper.request(STANDARD,"getDescribesQMParts", paraClass, para);
    return collection;
  }

  /**
   *  ��ȡ����ʹ�õ�ǰ������㲿���� 
   *  �����ݵ�ǰ�Ĵ�������QMPartIfc�ҵ���Ӧ��PartMaster��
   * �ٵ���getUsedByParts(QMPartMasterIfc partMasterIfc):Collection�����ҵ����е�ʹ�ø�����ļ��ϡ� 
   * @param partIfc QMPartIfc
   * @return Vector ���е�ʹ�ø������(QMPartInfo)����
   * @see QMPartInfo
   * @throws QMException
   */
  public static Vector getParentParts(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Vector vector = (Vector)helper.request(STANDARD,"getParentParts", paraClass, para);
    return vector;
  }

  /**
   *  �����ݿͻ���ʾ���Ϊ��ʷ��
   *  ѭ���صõ�ָ���㲿�������Ϊ��ʷ���������磺�㲿��a���㲿��b���Ϊ��������b��c���Ϊ������
   * ��ô����a��bsoID����Ի��b��c����Ӧ��Ϣ����ֵ��Vector������ÿ��Ԫ����String���͵����飬�Դ����ʷ��������Ϣ�� 
   * @param partID String
   * @throws QMException
   * @return Collection ����ÿ��Ԫ����String���͵����飬�Դ����ʷ��������Ϣ:
   *<br> 0.��ţ�
   *<br> 1.�������û�����
   *<br> 2.����ʱ�䣻
   *<br> 3.�㲿��id��
   *<br> 4.�㲿���汾��
   *<br> 5.�㲿����ͼ���ơ�
   */
  public static Collection findAllPreParts(String partID)
      throws QMException
  {
    Class[] paraClass = {String.class};
    Object[] para = {partID};
    Collection collection2 = (Collection)helper.request(STANDARD,"findAllPreParts", paraClass, para);
    return collection2;
  }

  /**
   *  ����ɸ����㲿�����������㲿���� 
   * @param partID String
   * @throws QMException
   * @return Collection �ɸ����㲿�����Ϊ�õ����㲿��(QMPartInfo)��
   */
  public static Collection findAllSaveAsParts(String partID)
      throws QMException
  {
    Class[] paraClass = {String.class};
    Object[] para = {partID};
    Collection collection = (Collection)helper.request(STANDARD,"findAllSaveAsParts", paraClass, para);
    return collection;
  }

  /**
   * ����ָ���ľ����㲿����ȡ����������Ϊ��Ʒ�ĸ����㲿�������°汾��
   * @param qmPartIfc QMPartIfc �����㲿����
   * @throws QMRemoteException
   * @throws QMException
   * @return Collection �����㲿�������°汾(QMPartInfo)��
   * @see QMPartInfo
   */
  public static Collection getParentProduct(QMPartIfc qmPartIfc)
          throws QMException
  {
      Class[] paraClass = {QMPartIfc.class};
      Object[] para = {qmPartIfc};
      Collection collection = (Collection)helper.request(STANDARD,"getParentProduct", paraClass, para);
      return collection;
  }


  /**
     * ��ȡ���㲿���ڸ��㲿���е�ʹ��������
     * @param parentPartIfc QMPartIfc ���㲿����
     * @param childPartIfc QMPartIfc ���㲿����
     * @throws QMException
     * @return String ʹ��������
     * @see QMPartInfo
     */
    public static String getPartQuantity(QMPartIfc parentPartIfc,
                                  QMPartIfc childPartIfc)
            throws QMException
    {
        Class[] paraClass = {QMPartIfc.class, QMPartIfc.class};
        Object[] para = {parentPartIfc, childPartIfc};
        String quantity = (String)helper.request(STANDARD,
                "getPartQuantity", paraClass, para);
        return quantity;
    }


    /**
     * ��ȡ���㲿���ڸ��㲿���е�ʹ��������
     * @param parentPartIfc QMPartIfc ���㲿����
     * @param middlePartMasterIfc QMPartMasterIfc �м��㲿������Ϣ��
     * @param childPartIfc QMPartIfc ���㲿����
     * @throws QMException
     * @return String ʹ��������
     * @see QMPartInfo
     * @see QMPartMasterInfo
     */
    public static String getPartQuantity(QMPartIfc parentPartIfc, QMPartMasterIfc middlePartMasterIfc,
                                  QMPartIfc childPartIfc)
            throws QMException
    {
        Class[] paraClass = {QMPartIfc.class, QMPartMasterIfc.class, QMPartIfc.class};
        Object[] para = {parentPartIfc, middlePartMasterIfc, childPartIfc};
        String quantity = (String)helper.request(STANDARD,
                "getPartQuantity", paraClass, para);
        return quantity;
    }


    /**
     * ��ָ����Ʒ�У���ȡָ�����㲿�������и��㲿����
     * @param parentPartMasterIfc QMPartMasterIfc ��Ʒ������Ϣ��
     * @param childPartMasterIfc QMPartMasterIfc �ò�Ʒ�е����㲿��������Ϣ��
     * @throws QMException
     * @return HashMap ���㲿������,����PartNumber��ֵ��ֵ����(QMPartInfo)��
     * @see QMPartMasterInfo
     */
    public static HashMap getParentPartsFromProduct(QMPartMasterIfc parentPartMasterIfc,
                        QMPartMasterIfc childPartMasterIfc)
            throws QMException
    {
        Class[] paraClass = {QMPartMasterIfc.class, QMPartMasterIfc.class};
        Object[] para = {parentPartMasterIfc, childPartMasterIfc};
        HashMap hashMap = (HashMap)helper.request(STANDARD, "getParentPartsFromProduct", paraClass, para);
        return hashMap;
    }

    /**
     * �����������ڡ�
     * @param basevalue ҵ�����
     * @return BaseValueIfc ҵ�����
     * @see BaseValueInfo
     * @throws QMException
     */
    public BaseValueIfc setLifeCycle(BaseValueIfc basevalue) throws QMException
    {
        Class[] paraClass = {BaseValueIfc.class};
        Object[] para = {basevalue};
        BaseValueIfc baseValueIfc = (BaseValueIfc)helper.request(STANDARD, "setLifeCycle", paraClass, para);
        return baseValueIfc;
    }

    /**
     * ��ȡ�������ù淶���㲿����
     * @param partIfc �㲿����
     * @param configSpecIfc ���ù淶��
     * @throws QMException
     * @return QMPartIfc ͨ�����ù淶���˵��㲿������
     * @see QMPartMasterInfo
     * @see PartConfigSpecInfo
     */
    public static QMPartIfc getPartByConfigSpec(QMPartMasterIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException
    {
        Class[] paraClass = {QMPartMasterIfc.class, PartConfigSpecIfc.class};
        Object[] para = {partIfc, configSpecIfc};
        QMPartIfc QMPartIfc = (QMPartIfc) helper.request(STANDARD,
                "getPartByConfigSpec", paraClass, para);
        return QMPartIfc;
    }
    
    /**
     * ���������㲿�����ݿͻ��˸����㲿����11�����������㲿���Ĺ��ܣ�֧��ģ����ѯ�ͷǲ�ѯ��
     * �������ĵ������㲿�������У�Ҳ������������Ҫ���ݶ����Բ�ѯ�㲿����������
     * @param partnumber �㲿����ţ�
     * @param checkboxNum �Ƿ�����㲿����š��ǡ���
     * @param partname�㲿�����ƣ�
     * @param checkboxName �Ƿ�����㲿�����ơ��ǡ���
     * @param partver �㲿���汾��
     * @param checkboxVersion �Ƿ�����㲿���汾���ǡ���
     * @param partview �㲿����ͼ��
     * @param checkboxView �Ƿ�����㲿����ͼ���ǡ���
     * @param partstate �㲿��״̬��
     * @param checkboxLifeCState �Ƿ�����㲿��״̬���ǡ���
     * @param parttype �㲿�����ͣ�
     * @param checkboxPartType �Ƿ�����㲿�����͡��ǡ���
     * @param partby �㲿����Դ��
     * @param checkboxProducedBy �Ƿ�����㲿����Դ���ǡ���
     * @param partproject �㲿�������飻
     * @param checkboxProject �Ƿ���������顰�ǡ���
     * @param partfolder �㲿���������ϼУ�
     * @param checkboxFolder �Ƿ�������ϼС��ǡ���
     * @param partcreator �㲿�������ߣ�
     * @param checkboxCreator �Ƿ���������ߡ��ǡ���
     * @param partupdatetime ����ʱ�䣻
     * @param checkboxModifyTime �Ƿ��������ʱ�䡰�ǡ���
     * @return �㲿��ֵ����(QMPartInfo)���ϡ�
     * @throws QMException
     */
    public static Collection findAllPartInfo(String partnumber,
            String checkboxNum, String partname, String checkboxName,
            String partver, String checkboxVersion, String partview,
            String checkboxView, String partstate, String checkboxLifeCState,
            String parttype, String checkboxPartType, String partby,
            String checkboxProducedBy, String partproject,
            String checkboxProject, String partfolder, String checkboxFolder,
            String partcreator, String checkboxCreator, String partupdatetime,
            String checkboxModifyTime) throws QMException
    {
        Class[] paraClass = {String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class};
        Object[] para = {partnumber, checkboxNum, partname, checkboxName,
                partver, checkboxVersion, partview, checkboxView, partstate,
                checkboxLifeCState, parttype, checkboxPartType, partby,
                checkboxProducedBy, partproject, checkboxProject, partfolder,
                checkboxFolder, partcreator, checkboxCreator, partupdatetime,
                checkboxModifyTime};
        Collection collection = (Collection) helper.request(STANDARD,
                "findAllPartInfo", paraClass, para);
        return collection;
    }


//��������չ�湦�ܡ�

  /**
   * �����ݿ���ɾ��һ�����ߡ�
   * @param baselineIfc :BaselineIfc ���ݿ����Ѿ����ڵĻ��ߡ�
   * @see BaselineIfc
   * @throws QMException
   */
  public static void deleteBaseline(BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {BaselineIfc.class};
    Object[] para = {baselineIfc};
    helper.request(EXTENDED,"deleteBaseline", paraClass, para);
  }

  /**
   * ������ݿ��е����л��ߡ�
   * @return  collection  ���ݿ������л��ߵ�ֵ����(ManagedBaselineInfo)�ļ��ϡ�
   * @throws QMException
   */
  public static Collection getAllBaselines()
      throws QMException
  {
    Class[] paraClass = {};
    Object[] para = {};
    Collection collection = (Collection)helper.request(EXTENDED,"getAllBaselines", paraClass, para);
    return collection;
  }

  /**
   *  ���㲿����ӵ�ָ���Ļ����У� 
   *  ��������в����ڸ��㲿�����κΰ汾��������뵽�����У� 
   *  ����������Ѿ����ڸ��㲿���������������أ� 
   *  ����������Ѿ����ڸ��㲿����������ͬ�汾���ô���Ĳ��������滻ԭ�������еĶ���
   * @param partIfc :QMPartIfc Ҫ��ӵ������е��㲿����ֵ����
   * @param baselineIfc :BaselineIfc Ҫ���part�Ļ��ߡ�
   * @return baselineIfc :BaselineIfc ������㲿���Ļ��ߵ�ֵ����
   * @see QMPartInfo
   * @see BaselineInfo
   * @throws QMException
   */
  public static BaselineIfc addToBaseline(QMPartIfc partIfc, BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, BaselineIfc.class};
    Object[] para = {partIfc, baselineIfc};
    BaselineIfc baselineIfc2 = (BaselineIfc)helper.request(EXTENDED,"addToBaseline", paraClass, para);
    return baselineIfc2;
  }

  /**
   *  ������ܻ��߹�����㲿��ͬʱ��ӵ�ָ���Ļ����С��Լ�����ÿ���������������в����� 
   *  ��������в����ڸ��㲿�����κΰ汾��������뵽�����У� 
   *  ����������Ѿ����ڸ��㲿���������������أ� 
   * ����������Ѿ����ڸ��㲿����������ͬ�汾���ô���Ĳ��������滻ԭ�������еĶ��� 
   * @param collection :Collection Ҫ��ӵ������е��㲿����ֵ����ļ��ϡ�
   * @param baselineIfc :BaselineIfc Ҫ���part�Ļ��ߡ�
   * @return baselineIfc :BaselineIfc ������㲿���Ļ��ߵ�ֵ����
   * @see BaselineInfo
   * @throws QMException
   */
  public static BaselineIfc addToBaseline(Collection collection, BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {Collection.class, BaselineIfc.class};
    Object[] para = {collection, baselineIfc};
    BaselineIfc baselineIfc2 = (BaselineIfc)helper.request(EXTENDED,"addToBaseline", paraClass, para);
    return baselineIfc2;
  }

  /**
   * �ӻ������Ƴ��㲿�������ػ��ߡ�
   * @param partIfc :QMPartIfc �����ڻ�����Ҫ���Ƴ����㲿����ֵ����
   * @param baselineIfc :BaselineIfc ����part�Ļ��ߵ�ֵ����
   * @return BaselineIfc ����ֵ����
   * @see BaselineInfo
   * @see QMPartInfo
   * @throws QMException
   */
  public static BaselineIfc removePartFromBaseline(QMPartIfc partIfc, BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, BaselineIfc.class};
    Object[] para = {partIfc, baselineIfc};
    BaselineIfc baselineIfc2 = (BaselineIfc)helper.request(EXTENDED,"removePartFromBaseline", paraClass, para);
    return baselineIfc2;
  }

  /**
   *  �����removePartFromBaseline��Ϊ��removePartsFromBaseline���͸�ȷ�С� 
   *  �ӻ�����ͬʱ�Ƴ�����㲿���� 
   * @param collection :Collection �����ڻ��߽����Ƴ����㲿����ֵ����ļ��ϡ�
   * @param baselineIfc :BaselineIfc ��Ҫ�Ƴ��㲿���Ļ��ߵ�ֵ����
   * @return baselineIfc :BaselineIfc ���Ƴ�����Ļ��ߵ�ֵ����
   * @see BaselineInfo
   * @throws QMException
   */
  public static BaselineIfc removePartFromBaseline(Collection collection, BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {Collection.class, BaselineIfc.class};
    Object[] para = {collection, baselineIfc};
    BaselineIfc baselineIfc2 = (BaselineIfc)helper.request(EXTENDED,"removePartFromBaseline", paraClass, para);
    return baselineIfc2;
  }

  /**
   * ���һ���㲿������Щ���߹���Ļ���ֵ����ļ��ϡ�
   * @param partIfc :QMPartIfc �ܻ��߹�����㲿����
   * @return collection :Collection ����partMasterIfc�����л��ߵ�ֵ����(BaselineInfo)�ļ��ϡ�
   * @see QMPartInfo
   * @throws QMException
   */
  public static Collection getBaselines(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getBaselines", paraClass, para);
    return collection;
  }

  /**
   * ����ܸû��߹�����㲿����ֵ����ļ��ϡ�
   * @param baselineIfc :BaselineIfc ���ߵ�ֵ����
   * @return collection:Collection ��ָ���Ļ��߹���������㲿����ֵ����(QMPartInfo)�ļ��ϡ�
   * @see BaselineIfc
   * @throws QMException
   */
  public static Collection getBaselineItems(BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {BaselineIfc.class};
    Object[] para = {baselineIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getBaselineItems", paraClass, para);
    return collection;
  }

  /**
   *  �ж��ܻ��߹�����㲿�����κε����汾�Ƿ�Ϊ������ߵ�һ���֡�
   *  ��part��ӦMaster���κε���(Iterated)��baselineһ���֣�����true�����򷵻�false�� 
   * @param partIfc :QMPartIfc �㲿����ֵ����
   * @param baselineIfc :BaselineIfc ���ߵ�ֵ����
   * @return boolean ��baselineIfc����partIfc���κε����汾������true�����򷵻�false��
   * @see QMPartInfo
   * @see BaselineIfc
   * @throws QMException
   */
  public static boolean isAnyIterationInBaseline(QMPartIfc partIfc, BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, BaselineIfc.class};
    Object[] para = {partIfc, baselineIfc};
    boolean flag = ((Boolean)helper.request(EXTENDED,"isAnyIterationInBaseline", paraClass, para)).booleanValue();
    return flag;
  }

  /**
   *  �жϾ�����ܻ��߹���Ķ����Ƿ�Ϊ������ߵ�һ���֣�
   *  ��������a_BaselineableIfc��Ӧ��ʵ��EJB��a_BaselineIfc��Ӧ��Entity EJB��һ���ַ���true�����򷵻�false�� 
   * @param a_BaselineableIfc �����BaselineableIfcʵ��
   * @param a_BaselineIfc �����BaselineIfcʵ��
   * @return boolean ��a_BaselineIfc��Ӧ��ʵ��EJB������a_BaselineableIfc��Ӧ��ʵ��EJB������true�����򷵻�false��
   * @see BaselineIfc
   * @exception QMException
   */
  public static boolean isInBaseline(BaselineableIfc a_BaselineableIfc, BaselineIfc a_BaselineIfc)
      throws QMException
  {
    Class[] paraClass = {BaselineableIfc.class, BaselineIfc.class};
    Object[] para = {a_BaselineableIfc, a_BaselineIfc};
    boolean flag = ((Boolean)helper.request(EXTENDED,"isInBaseline", paraClass, para)).booleanValue();
    return flag;
  }

  /**
   *  �����ݿ��б�����ߣ� 
   *  �����жϸû����ǲ����Ѿ��־û��ģ�������ǣ��ͱ��棻����ǣ��͸��¡� 
   * @param baselineIfc :BaselineIfc δ�־û��Ļ��ߡ�
   * @return baselineIfc:BaselineIfc �־û��Ļ��ߡ�
   * @see BaselineIfc
   * @throws QMException
   */
  public static BaselineIfc saveBaseline(BaselineIfc baselineIfc)
      throws QMException
  {

    Class[] paraClass = {BaselineIfc.class};
    Object[] para = {baselineIfc};
    BaselineIfc baselineIfc2 = (BaselineIfc)helper.request(EXTENDED,"saveBaseline", paraClass, para);
    return baselineIfc2;
  }

  /**
   * ����ܻ��߹���Ķ��󵽻��ߣ������������Ľṹɸѡ�����ݹ�ʵ�֡�
   * @param partIfc :QMPartIfc ����ӵ��ܻ��߹���Ķ���
   * @param baselineIfc :BaselineIfc ����ֵ����
   * @param configSpecIfc :ConfigSpec partMasterIfc �Ľṹɸѡ������
   * @return Vector QMPartIfc��:���(����)��ͼ(�汾�����)��
   *  @see BaselineIfc
   *  @see QMPartInfo
   *  @see PartConfigSpecInfo
   * @throws QMException
   */
  public static Vector[] populateBaseline(QMPartIfc partIfc, BaselineIfc baselineIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, BaselineIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, baselineIfc, configSpecIfc};
    Vector[] vector = (Vector[])helper.request(EXTENDED,"populateBaseline", paraClass, para);
    return vector;
  }

  /**
   * ��ñ�ָ�����㲿�����滻�����㲿����ֵ����ļ��ϡ�
   * @param partMasterIfc :QMPartMasterIfc ���㲿����ֵ����
   * @return Collection �������㲿���滻�����㲿����ֵ����(QMPartMasterInfo)�ļ��ϡ�
   * @throws QMException
   * @see QMPartMasterInfo
   */
  public static Collection getAlternateForPartMasters(QMPartMasterIfc partMasterIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class};
    Object[] para = {partMasterIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getAlternateForPartMasters", paraClass, para);
    return collection;
  }

  /**
   * ���ָ�����㲿�����滻����ֵ����ļ��ϡ�
   * @param partMasterIfc :QMPartMasterIfc ���㲿����ֵ����
   * @return collection:Collection �����㲿���滻����ֵ����(QMPartMasterInfo)�ļ��ϡ�
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public static Collection getAlternatesPartMasters(QMPartMasterIfc partMasterIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class};
    Object[] para = {partMasterIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getAlternatesPartMasters", paraClass, para);
    return collection;
  }

  /**
   * ����ָ���Ľṹ����滻���ļ��ϡ�
   * @param usageLinkIfc :PartUsageLinkIfc ָ����һ���ṹ��ֵ����
   * @return collection:Collection �����滻�����㲿����ֵ����QMPartMasterIfc�ļ��ϡ�
   * @see PartUsageLinkInfo
   * @throws QMException
   */
  public static Collection getSubstitutesPartMasters(PartUsageLinkIfc usageLinkIfc)
      throws QMException
  {
    Class[] paraClass = {PartUsageLinkIfc.class};
    Object[] para = {usageLinkIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getSubstitutesPartMasters", paraClass, para);
    return collection;
  }

  /**
   * ����ָ�������㲿����ø��㲿�������滻�Ľṹ��ֵ����ļ��ϡ�
   * @param partMasterIfc : QMPartMasterIfc ָ�������㲿��ֵ����
   * @return collection:Collection ���㲿�������滻�Ľṹֵ����(PartSubstituteLinkInfo)�ļ��ϡ�
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public static Collection getSubstituteForPartUsageLinks(QMPartMasterIfc partMasterIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class};
    Object[] para = {partMasterIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getSubstituteForPartUsageLinks", paraClass, para);
    return collection;
  }

  /**
   * �������㲿�����滻��ϵ��
   * @param link PartAlternateLinkIfc ����ǰ���滻��ϵ
   * @return PartAlternamteLinkIfc  �������滻��ϵ
   * @see PartAlternateLinkInfo
   * @throws QMException
   */
  public static PartAlternateLinkIfc savePartAlternateLink(PartAlternateLinkIfc link)
      throws QMException
  {
    Class[] paraClass = {PartAlternateLinkIfc.class};
    Object[] para = {link};
    PartAlternateLinkIfc partAlternateLinkIfc = (PartAlternateLinkIfc)helper.request(EXTENDED,"savePartAlternateLink", paraClass, para);
    return partAlternateLinkIfc;
  }

  /**
   * ����ṹ�滻��ϵ��
   * @param link PartSubstituteLinkIfc ����ǰ�Ľṹ�滻��ϵ
   * @return PartSubstituteLinkIfc �����Ľṹ�滻��ϵ
   * @see PartSubstituteLinkInfo
   * @throws QMException
   */
  public static PartSubstituteLinkIfc savePartSubstituteLink(PartSubstituteLinkIfc link)
      throws QMException
  {
    Class[] paraClass = {PartSubstituteLinkIfc.class};
    Object[] para = {link};
    PartSubstituteLinkIfc partSubstituteLinkIfc = (PartSubstituteLinkIfc)helper.request(EXTENDED,"savePartSubstituteLink", paraClass, para);
    return partSubstituteLinkIfc;
  }

  /**
   * ɾ�����㲿�����滻��ϵ��
   * @param link PartAlternateLinkIfc �滻��ϵ�Ķ���
   * @see PartAlternateLinkInfo
   * @throws QMException
   */
  public static void deletePartAlternateLink(PartAlternateLinkIfc link)
      throws QMException
  {
    Class[] paraClass = {PartAlternateLinkIfc.class};
    Object[] para = {link};
    helper.request(EXTENDED,"deletePartAlternateLink", paraClass, para);
  }

  /**
   * ɾ���ṹ�滻��ϵ��
   * @param link PartSubstituteLinkIfc �ṹ�滻��ϵ�Ķ���
   *  @see PartSubstituteLinkInfo
   * @throws QMException
   */
  public static void deletePartSubstituteLink(PartSubstituteLinkIfc link)
      throws QMException
  {
    Class[] paraClass = {PartSubstituteLinkIfc.class};
    Object[] para = {link};
    helper.request(EXTENDED,"deletePartSubstituteLink", paraClass, para);
  }

  /**
   *  ��ָ�����㲿����������һ����ͼ�в������·������㲿������Ϣ��ֵ���� 
   *  ��������Ϣ��Ϊ�˼��������Ժ������ڷ�������չ�� 
   * @param partIfc :QMPartIfc Ҫ�������㲿����ֵ����
   * @return masterIfc QMPartMasterIfc ���������㲿��������Ϣ��ֵ����
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public static QMPartMasterIfc publish(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    QMPartMasterIfc qmPartMasterIfc = (QMPartMasterIfc)helper.request(EXTENDED,"publish", paraClass, para);
    return qmPartMasterIfc;
  }

  /**
   * ���ṹ��ӻ��ߡ�
   * @param a_BaseValueIfc ��Ҫ��ӵ������е��κ�Entity EJB��Ӧ��ֵ����
   * @param a_BaselineIfc ����a_BaseValueIfc�Ļ��ߵ�ֵ����
   * @param a_Walker Walker��һ��ʵ����
   * @return Vector baselineableIfc��:���(����)��ͼ(�汾�����)��
   * @see BaselineIfc
   * @see Walker
   * @exception QMException
   */
  public static Vector populateBaseline(BaseValueIfc a_BaseValueIfc, BaselineIfc a_BaselineIfc, Walker a_Walker)
      throws QMException
  {
    Class[] paraClass = {BaseValueIfc.class, BaselineIfc.class, Walker.class};
    Object[] para = {a_BaseValueIfc, a_BaselineIfc, a_Walker};
    Vector vector = (Vector)helper.request(EXTENDED,"populateBaseline", paraClass, para);
    return vector;
  }

  /**
   * �����û�Ȩ�ޣ�������пɼ�����ͼ����
   * @return Vector ������Ȩ�޵���ͼ����(ViewObjectInfo)�ļ��ϡ�
   * @throws QMException
   */
  public static Vector getAllViewInfos()
      throws QMException
  {
    Class[] paraClass = {};
    Object[] para = {};
    Vector vector = (Vector)helper.request(EXTENDED,"getAllViewInfos", paraClass, para);
    return vector;
  }

  /**
   *  ͨ����չ���ԵĲ�ѯ������÷���������Affixed���󼯺ϡ� 
   *  
   * @param vector Vector ����Ĳ���vector:�ͻ��˵���չ���ԵĲ�ѯ������ÿ��Ԫ��Ϊ�ַ���������String[]:
   * <br> 1�����Զ����bsoID��
   *  <br>2����ѯ��������ֻ�С�=����!=�������������
   * <br> 3���ͻ��������Ҫ��ѯ������ֵ�� 
   * @return Collection ����������"�ĵ������㲿��"(ͳһ��ʾΪAffixIfcֵ����)�ļ��ϡ�
   * @throws QMException
   */
  public static Collection getValueInfoForExtendAttr(Vector vector)
      throws QMException
  {
    Class[] paraClass = {Vector.class};
    Object[] para = {vector};
    Collection collection = (Collection)helper.request(EXTENDED,"getValueInfoForExtendAttr", paraClass, para);
    return collection;
  }

  /**
   * ��ȡ��ǰ�Ľṹɸѡ����������û����״ε�½ϵͳ������Ĭ�ϵġ���׼���ṹɸѡ������
   * @throws QMException
   * @return PartConfigSpecIfc ��׼�ṹɸѡ������
   * @see PartConfigSpecInfo
   */
  public static PartConfigSpecIfc getCurrentConfigSpec()
      throws QMException
  {
    Class[] paraClass = {};
    Object[] para = {};
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)helper.request(EXTENDED,"getCurrentConfigSpec", paraClass, para);
    return partConfigSpecIfc;
  }

  /**
   * ���ݸ������㲿���ж����Ƿ��������㲿�����滻����ṹ�滻����
   * @param part QMPartIfc �������㲿��
   * @throws QMException
   * @return boolean true��ʾ���㲿����һ���滻����ṹ�滻�������򶼲��ǡ�
   * @see QMPartInfo
   */
  public static boolean isAlterOrSub(QMPartIfc part)
          throws QMException
  {
      Class[] paraClass = {QMPartIfc.class};
      Object[] para = {part};
      Boolean flag  =  (Boolean)helper.request(EXTENDED, "isAlterOrSub", paraClass, para);
      return flag.booleanValue();
  }

    /**
     * ��ȡ��ͬһ�����е��㲿���Ľṹ�е������Ӽ���
     * @param partIfc �㲿����
     * @param baselineIfc ���ߡ�
     * @param configSpec ���ù淶��
     * @return Vector �Ӽ�(QMPartInfo)���ϡ�
     * @see QMPartInfo
     * @see BaselineIfc
     * @see PartConfigSpecInfo
     * @throws QMException
     */
    public static Vector getPopulateBaselineResult(QMPartIfc partIfc,
            BaselineIfc baselineIfc, PartConfigSpecIfc configSpec) throws QMException
    {
        Class[] paraClass = {QMPartIfc.class, BaselineIfc.class, PartConfigSpecIfc.class};
        Object[] para = {partIfc, baselineIfc, configSpec};
        Vector result = (Vector) helper.request(EXTENDED,
                "getPopulateBaselineResult", paraClass, para);
        return result;
    }

//��������ҵ�湦�ܡ�

  /**
   *  ����ָ����Դ������Ŀ�겿�������Ե�ɸѡ������������������ʹ�ù�ϵ�Ĳ�ͬ�� 
   *  �����������˵ݹ鷽����difference(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
   * QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc)�� 
   *  
   * @param sourcePartIfc :QMPartIfcԴ������ֵ����
   * @param objectPartIfc :QMPartIfcĿ�겿����ֵ����
   * @param sourceConfigSpecIfc :PartConfigSpecIfcԴ������ɸѡ������
   * @param objectConfigSpecIfc :PartConfigSpecIfcĿ�겿����ɸѡ������
   * @return vector  ����ֵVector��Ԫ�ص����ݽṹString[]�� 
   *  <br>0����ǰ���첿������ڸ������Ĳ�Σ����������Ϊ0�㣬���������Ӳ���Ϊ1�㣬�Դ����ƣ�
   *   <br>1����ǰ���첿��������+"("+���+")" �� 
   *   <br>2����ǰ���첿����Դ�����еİ汾+����ͼ�������Դ������û�иò����Ļ�����ʾ""��
   *     ���Դ�������иò��첿������û�з��Ͻṹɸѡ�����İ汾�Ļ�����ʾ"û�з��Ͻṹɸѡ�����İ汾"��
   *   <br>3����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Դ������û�иò����Ļ�����ʾ""�� 
   *     ���Դ�������иò��첿������û�з��Ͻṹɸѡ�����İ汾�Ļ����ճ���ʾ����+����λ���� 
   *   <br>4����ǰ���첿����Ŀ�겿���еİ汾+����ͼ�������Ŀ�겿����û�иò����Ļ�����ʾ""�� 
   *     ���Ŀ�겿�����иò��첿������û�з��Ͻṹɸѡ�����İ汾�Ļ�����ʾ"û�з��Ͻṹɸѡ�����İ汾"��
   *   <br>5����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Ŀ�겿����û�иò����Ļ�����ʾ""��
   *     ���Ŀ�겿�����иò��첿������û�з��Ͻṹɸѡ�����İ汾�Ļ����ճ���ʾ����+����λ����
   *   <br>6����ʾ����ɫ�ı�ʶ::������첿����Դ������Ŀ�겿���ж����ڣ����������߰汾��ͬ�Ļ�����ʾΪ��ɫ(black)��
   *     ������첿����Դ�����д��ڣ�������Ŀ�겿���в����ڵĻ�����ʾΪ��ɫ(green)��
   *     ������첿����Ŀ�겿���д��ڣ�������Դ�����в����ڵĻ�����ʾΪ��ɫ(purple)�� 
   *     ������첿����Դ��������Ŀ�겿���еİ汾����������ͬ�Ļ�����ʾΪ��ɫ(gray)��
   *     ��Ϣ"û�з��Ͻṹɸѡ�����İ汾"����ɫΪ��ɫ(red),���и���Ϣ���е���ɫΪ��ɫ(black)��
   *   <br>7:�����㲿����BsoID�������Դ�㲿�����Եģ�����Դ�㲿��ʹ�õĸò����㲿����BsoID��
   *   <br>8:�����㲿����BsoID, �����Ŀ���㲿�����Եģ�����Ŀ���㲿��ʹ�õĵĲ����㲿����BsoID�� 
   * @throws QMException
   * @see QMPartInfo
   * @see PartConfigSpecInfo
   */
  public static Vector getBOMDifferences(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
                                  QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc,HashMap map)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class, QMPartIfc.class, PartConfigSpecIfc.class,HashMap.class};
    Object[] para = {sourcePartIfc, sourceConfigSpecIfc, objectPartIfc, objectConfigSpecIfc,map};
    Vector vector = (Vector)helper.request(ENTERPRISE,"getBOMDifferences", paraClass, para);
    return vector;
  }

  /**
   * ������collection�е�Ԫ����������ΪPartUsageLinkIfc������rightBsoID(�㲿��������Ϣ��bsoID)Ϊ���ֱ����ڹ������в����ع�����
   * @param collection :CollectionPartUsageLinkIfc���󼯺ϡ�
   * @throws QMException
   * @return HashMap �����㲿��������Ϣ��bsoID��ֵ��PartUsageLinkIfc����
   */
  public static HashMap consolidateUsageLink(Collection collection)
      throws QMException
  {
    Class[] paraClass = {Collection.class};
    Object[] para = {collection};
    HashMap hashMap = (HashMap)helper.request(ENTERPRISE,"consolidateUsageLink", paraClass, para);
    return hashMap;
  }

  /**
   * ���part����������PartUsageLinkIfc����ļ��ϡ�
   * @param partIfc :QMPartIfc����
   * @return collection:Colllection��part����������PartUsageLinkifc���󼯺ϡ�
   * @throws QMException
   * @see QMPartInfo
   */
  public static Collection getConsolidatedBOM(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(ENTERPRISE,"getConsolidatedBOM", paraClass, para);
    return collection;
  }

  /**
   * �����½��͸��µ���Ч�Է���.
   * @param effConfigItemIfc :EffConfigurationItemIfc ��Ч��������ֵ����
   * @throws QMException
   * @return EffConfigurationItemIfc ��������Ч��������ֵ����
   * @see EffConfigurationItemInfo
   */
  public static EffConfigurationItemIfc saveConfigItemIfc(EffConfigurationItemIfc effConfigItemIfc)
      throws QMException
  {
    Class[] paraClass = {EffConfigurationItemIfc.class};
    Object[] para = {effConfigItemIfc};
    EffConfigurationItemIfc effConfigurationItemIfc = (EffConfigurationItemIfc)helper.request(ENTERPRISE,"saveConfigItemIfc", paraClass, para);
    return effConfigurationItemIfc;
  }

  /**
   * ɾ����Ч�Է�����
   * @param configItemIfc EffConfigurationItemIfc
   * @throws QMException
   * @see EffConfigurationItemInfo
   */
  public static void deleteConfigItemIfc(EffConfigurationItemIfc configItemIfc)
      throws QMException
  {
    Class[] paraClass = {EffConfigurationItemIfc.class};
    Object[] para = {configItemIfc};
    helper.request(ENTERPRISE,"deleteConfigItemIfc", paraClass, para);
  }

  /**
   * ����һ��EffGroup����
   * @param effectivityType ��Ч�����͡�
   * @param value_range ֵ��Χ��
   * @param configItemIfc QMConfigurationItemIfc
   * @param partIfc EffManagedVersionIfc
   * @throws QMException
   * @return EffGroup �������EffGroup����
   * @see QMConfigurationItemInfo
   * @see EffGroup
   */
  public static EffGroup createEffGroup(String effectivityType, String value_range, QMConfigurationItemIfc configItemIfc, EffManagedVersionIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {String.class, String.class, QMConfigurationItemIfc.class, EffManagedVersionIfc.class};
    Object[] para = {effectivityType, value_range, configItemIfc};
    EffGroup effGroup = (EffGroup)helper.request(ENTERPRISE,"createEffGroup", paraClass, para);
    return effGroup;
  }

  /**
   * ����ڵ�ǰɸѡ������partMasterIfc�Ľṹ�������Ӽ���
   * @param partMasterIfc �㲿������Ϣֵ����
   * @param configSpecIfc �ṹɸѡ������
   * @return QMPartIfc[] �����Ӳ���ֵ����(QMPartInfo)�ļ��ϡ�
   * @throws QMException
   * @see QMPartMasterInfo
   * @see QMPartInfo
   * @see PartConfigSpecInfo
   */
  public static QMPartIfc[] getAllSubPartsByConfigSpec(QMPartMasterIfc partMasterIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partMasterIfc, configSpecIfc};
    QMPartIfc[] qmPartIfc = (QMPartIfc[])helper.request(ENTERPRISE,"getAllSubPartsByConfigSpec", paraClass, para);
    return qmPartIfc;
  }

  /**
   * ���partIfc�Ѿ����ڵ�EffGroup����ļ��ϡ�
   * @param partIfc QMPartIfc
   * @throws QMException
   * @return Vector EffGroup����ļ���
   * @see QMPartInfo
   */
  public static Vector outputExistingEffGroups(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Vector vector = (Vector)helper.request(ENTERPRISE,"outputExistingEffGroups", paraClass, para);
    return vector;
  }

  /**
   * ����ڵ�ǰɸѡ������partMasterIfc�Ľṹ�������Ӽ�����Ч�Է�Χ���������EffGroup�ļ��ϡ��÷���������ʾ���ṹ�����Ч�Խ����
   * @param partMasterIfc :�㲿������Ϣֵ����
   * @param configSpecIfc :PartConfigSpecIfc �ṹɸѡ����ֵ����
   * @param configItemID ��Ч��������bsoID��
   * @param value_range :String ��Ч��ֵ��Χ��
   * @return Vector EffGroup�ļ���
   * @throws QMException
   *  @see QMPartMasterInfo
   * @see PartConfigSpecInfo
   */
  public static Vector getEffGroups(QMPartMasterIfc partMasterIfc, PartConfigSpecIfc configSpecIfc, String configItemID, String value_range)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class, PartConfigSpecIfc.class, String.class, String.class};
    Object[] para = {partMasterIfc, configSpecIfc, configItemID, value_range};
    Vector vector = (Vector)helper.request(ENTERPRISE,"getEffGroups", paraClass, para);
    return vector;
  }

  /**
   * �����㲿��ֵ�����ȡ���㲿����������Ч��ֵ��
   * @param partIfc : QMPartIfc �㲿��ֵ����
   * @return Vector EffGroup�ļ��ϡ�
   * @throws QMException
   * @see WorkableIfc
   */
  public static Vector getEffVector(WorkableIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {WorkableIfc.class};
    Object[] para = {partIfc};
    Vector vector = (Vector)helper.request(ENTERPRISE,"getEffVector", paraClass, para);
    return vector;
  }
  /**
   * �����㲿��ֵ�������Ч��������id��ȡ���㲿����������Ч��ֵ��
   * @param partIfc : WorkableIfc �㲿��ֵ����
   * @param itemID  ��Ч��������id
   * @return Vector EffGroup�ļ��ϡ�
   * @throws QMException
   * @see WorkableIfc
   */
  public static Vector getEffVector(WorkableIfc partIfc,String itemName)
      throws QMException
  {
    Class[] paraClass = {WorkableIfc.class,String.class};
    Object[] para = {partIfc,itemName};
    Vector vector = (Vector)helper.request(ENTERPRISE,"getEffVector", paraClass, para);
    return vector;
  }

  /**
   * �����㲿��ֵ�����ȡ���㲿����������Ч��ֵ��
   * @param partIfc : QMPartIfc �㲿��ֵ����
   * @param isacl �Ƿ���ʿ��ơ�
   * @return Vector EffGroup�ļ��ϡ�
   * @throws QMException
   * @see WorkableIfc
   */
  public static Vector getEffVector(WorkableIfc partIfc, boolean isacl)
      throws QMException
  {
    Class[] paraClass = {WorkableIfc.class, boolean.class};
    Object[] para = {partIfc, new Boolean(isacl)};
    Vector vector = (Vector)helper.request(ENTERPRISE,"getEffVector", paraClass, para);
    return vector;
  }

  /**
   * ɾ��part��Ӧ����Ч��ֵ��
   * @param partBsoID �����bsoID��
   * @param configItemName ��Ч�Է������ơ�
   * @param effectivityType ��Ч������:"DATE","LOT_NUM","SERIAL_NUM"��
   * @throws QMException
   */
  public static void deleteAllEffs(String partBsoID, String configItemName, String effectivityType)
      throws QMException
  {
    Class[] paraClass = {String.class, String.class, String.class};
    Object[] para = {partBsoID, configItemName, effectivityType};
    helper.request(ENTERPRISE,"deleteAllEffs", paraClass, para);
  }

  /**
   * ����part��Ӧ����Ч��ֵ��
   * @param partIfc �����
   * @param configItemName ��Ч�Է������ơ�
   * @param effectivityType ��Ч�����Ͳ���Ϊ�ձ�����:"DATE","LOT_NUM","SERIAL_NUM"֮һ��
   * @param effValue ��Ч��ֵ��
   * @throws QMException
   * @see QMPartInfo
   */
  public static void createEff(QMPartIfc partIfc, String configItemName, String effectivityType, String effValue)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, String.class, String.class, String.class};
    Object[] para = {partIfc, configItemName, effectivityType, effValue};
    helper.request(ENTERPRISE,"createEff", paraClass, para);
  }

  /**
   * ����part��Ӧ����Ч��ֵ��
   * @param partIfc QMPartIfc �����
   * @param configItemName String ��Ч�Է������ơ�
   * @param oldEffectivityType String ��Ч�����Ͳ���Ϊ�ձ�����:"DATE","LOT_NUM","SERIAL_NUM"֮һ��
   * @param newEffValue String �µ���Ч��ֵ��Χ��
   * @throws QMException
   * @see QMPartInfo
   */
  public static void updateEff(QMPartIfc partIfc, String configItemName,
            String oldEffectivityType, String newEffValue) throws QMException
    {
        Class[] paraClass = {QMPartIfc.class, String.class, String.class,
                String.class};
        Object[] para = {partIfc, configItemName, oldEffectivityType,
                newEffValue};
        helper.request(ENTERPRISE, "updateEff", paraClass, para);
    }
  
  /**
   * �����������Գ����ߵĶ���,�����iba����ֵ�ĸ�����
   * @param ibaHolderIfc �������Գ�����ֵ����
   * @return int �㲿��ֵ���������iba����ֵ�ĸ���
   * @see QMPartInfo
   */
  public static int getIBACount(QMPartIfc ibaHolderIfc)
  throws QMException
  {
	 
	  Class[] paraClass = {IBAHolderIfc.class};
      Object[] para = {ibaHolderIfc};
      ibaHolderIfc=(QMPartIfc) helper.request(EXTENDED, "getIBAContainer", paraClass, para);
    
    DefaultAttributeContainer container = (DefaultAttributeContainer)ibaHolderIfc.getAttributeContainer();
   
    if(container == null)
      container = new DefaultAttributeContainer();
    
    AbstractValueView[] defs=container.getAttributeValues();
 
    return defs.length;
  }
  
  /**
   * �㲿���Ƿ���Է����������ԡ�
   * @param partIfc QMPartIfc �����㲿����
   * @throws QMException
   * @return boolean �����жϽ����
   * @see QMPartInfo
   */
  public static boolean  publishIBA(QMPartIfc partIfc)
      throws QMException
  {
	  try
	  {
	    Class[] paraClass = {QMPartIfc.class};
        Object[] para = {partIfc};
       
       boolean flage=((Boolean)helper.request(EXTENDED, "publishIBA", paraClass, para)).booleanValue();
       return flage;
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
		  throw new QMException(e);
	  }
	  
  }
  
    //����(1)20080808 zhangq begin �޸�ԭ���ڲ����㲿���ı����ڲ�Ʒʱ����ʾ����ȷ����TD-1794��
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
	public static Vector getUsedProductStruct(QMPartIfc partIfc,
			String productBsoId, PartConfigSpecIfc configSpecIfc)
			throws QMException {
		Class[] paraClass = { QMPartIfc.class, String.class,
				PartConfigSpecIfc.class };
		Object[] para = { partIfc, productBsoId, configSpecIfc };
		Vector vector = (Vector) helper.request(STANDARD,
				"getUsedProductStruct", paraClass, para);
		return vector;
	}
	//����(2)20080808 zhangq end
	
	//add whj 2008/09/03
	 /**
	   * ��ȡ�б�����ִ�
	   * @param s ���ݲ���s�жϻ�ȡֵ�����ĸ�����s:"usage","description","reference","route","summary","regulation"
	   * @return
	   * @throws QMException
	   *@see String
	   */
	  public static String[] getListHead(String s)
	  throws QMException
	  {
		  String[] heads=null;
		  String head="";
		  //����session�����ȡ��ǰ�û���ID
		  //String userid=getCurUserID();
		  //���ݵ�ǰ�û���ID��ѯ��PartAttrSet��Ӧ���ֶ�owner��ȡPartAttrSetInfo����
		  PartAttrSetInfo info=getPartAttrSetInfo();
		  //���ݲ���s�жϻ�ȡֵ�����ĸ�����s:"usage","description","reference","route","summary","regulation"
		
		  if(s.equals("usage"))
		  {
			  head=info.getUsageAttr();			
			  StringTokenizer st=new StringTokenizer(head,",");
			  int j=st.countTokens();			  
			  heads=new String[j];
			  for(int i=0;st.hasMoreTokens();i++)
			  {				  
				  String en=st.nextToken();				  
				 heads[i]=PartUsageList.toPartUsageList(en).getDisplay();				 
			  }			 
		  }
		 
		  if(s.equals("description"))
		  {			 
			  head=info.getDescriptionAttr();
			  StringTokenizer st=new StringTokenizer(head,",");
			  int j=st.countTokens();
			  heads=new String[j];
			  for(int i=0;st.hasMoreElements();i++)
			  {				
				  String en=st.nextToken();
				 heads[i]=PartDescriptionList.toPartDescriptionList(en).getDisplay();
			  }
			  
		  }
		
			  if(s.equals("reference"))
			  {				 
				  head=info.getReferenceAttr();
				  StringTokenizer st=new StringTokenizer(head,",");
				  int j=st.countTokens();
				  heads=new String[j];
				  for(int i=0;st.hasMoreElements();i++)
				  {					 
					  String en=st.nextToken();
					 heads[i]=PartReferenceList.toPartReferenceList(en).getDisplay();
				  }
			  }
		
				  if(s.equals("route"))
				  {					 
					  head=info.getCappRoute();
					  StringTokenizer st=new StringTokenizer(head,",");
					  int j=st.countTokens();			  
					  heads=new String[j];
					  for(int i=0;st.hasMoreTokens();i++)
					  {				  
						  String en=st.nextToken();				  
						 heads[i]=TechnicsRouteList.toTechnicsRouteList(en).getDisplay();
						 
					  }
				  }
					  if(s.equals("summary"))
					  {
						  head=info.getCappSummary();
						  StringTokenizer st=new StringTokenizer(head,",");
						  int j=st.countTokens();			  
						  heads=new String[j];
						  for(int i=0;st.hasMoreTokens();i++)
						  {				  
							  String en=st.nextToken();				  
							 heads[i]=TechnicsSummaryList.toTechnicsSummaryList(en).getDisplay();
							 
						  }
					  }
						  if(s.equals("regulation"))
						  {
							  head=info.getCappRegulation();
							  StringTokenizer st=new StringTokenizer(head,",");
							  int j=st.countTokens();			  
							  heads=new String[j];
							  for(int i=0;st.hasMoreTokens();i++)
							  {				  
								  String en=st.nextToken();				  
								 heads[i]=TechnicsRegulateionList.toTechnicsRegulateionList(en).getDisplay();
								 
							  }
						  }							  
		 
		  return heads;
	  }
	  
	  
	  /**
	   * ��ȡ�б�����ִ�(Ӣ��)
	   * @param s ���ݲ���s�жϻ�ȡֵ�����ĸ�����s:"usage","description","reference","route","summary","regulation"
	   * @return
	   * @throws QMException
	   *@see String
	   */
	  public static String[] getListHeadEn(String s)
	  throws QMException
	  {
		  String[] heads=null;
		  String head="";
		  //����session�����ȡ��ǰ�û���ID
		  //String userid=getCurUserID();
		  //���ݵ�ǰ�û���ID��ѯ��PartAttrSet��Ӧ���ֶ�owner��ȡPartAttrSetInfo����
		  PartAttrSetInfo info=getPartAttrSetInfo();
		  //���ݲ���s�жϻ�ȡֵ�����ĸ�����s:"usage","description","reference","route","summary","regulation"
		
		  if(s.equals("usage"))
		  {
			  head=info.getUsageAttr();			
			  StringTokenizer st=new StringTokenizer(head,",");
			  int j=st.countTokens();			  
			  heads=new String[j];
			  for(int i=0;st.hasMoreTokens();i++)
			  {				  
				  String en=st.nextToken();				  
				 heads[i]=en;				 
			  }			 
		  }
		 
		  if(s.equals("description"))
		  {			 
			  head=info.getDescriptionAttr();
			  StringTokenizer st=new StringTokenizer(head,",");
			  int j=st.countTokens();
			  heads=new String[j];
			  for(int i=0;st.hasMoreElements();i++)
			  {				  
				  String en=st.nextToken();
				 heads[i]=en;
			  }			  
		  }
		
			  if(s.equals("reference"))
			  {				 
				  head=info.getReferenceAttr();
				  StringTokenizer st=new StringTokenizer(head,",");
				  int j=st.countTokens();
				  heads=new String[j];
				  for(int i=0;st.hasMoreElements();i++)
				  {					  
					 String en=st.nextToken();
					 heads[i]=en;
				  }
			  }
		
				  if(s.equals("route"))
				  {					 
					  head=info.getCappRoute();
					  StringTokenizer st=new StringTokenizer(head,",");
					  int j=st.countTokens();
					  heads=new String[j];
					  for(int i=0;st.hasMoreElements();i++)
					  {					  
						 String en=st.nextToken();
						 heads[i]=en;
					  }
				  }
					  if(s.equals("summary"))
					  {
						  head=info.getCappSummary();
						  StringTokenizer st=new StringTokenizer(head,",");
						  int j=st.countTokens();			  
						  heads=new String[j];
						  for(int i=0;st.hasMoreTokens();i++)
						  {				  
							  String en=st.nextToken();				  
							 heads[i]=en;				 
						  }
					  }
						  if(s.equals("regulation"))
						  {
							  head=info.getCappRegulation();
							  StringTokenizer st=new StringTokenizer(head,",");
							  int j=st.countTokens();			  
							  heads=new String[j];
							  for(int i=0;st.hasMoreTokens();i++)
							  {				  
								  String en=st.nextToken();				  
								 heads[i]=en;				 
							  }	
						  }
							  
		  return heads;
	  }
	  
	  /**
	   * ��ȡ�б����ķ���
	   * @param s
	   * @return
	   * @throws QMException
	   */
	  public static String[] getListMethod(String s)
	  throws QMException
	  {
		  String[] headingMethod=null;
		  PartAttrSetInfo info=getPartAttrSetInfo();
		  //���ݲ���s�жϻ�ȡֵ�����ĸ�����s:"usage","description","reference","route","summary","regulation"
		  String head="";
		  if(s.equals("usage"))
		  {
			  head=info.getUsageAttr();	
		  }
		  else 
			  if(s.equals("description"))
			  {
				  head=info.getDescriptionAttr();
			  }
			  else 
				  if(s.equals("reference"))
				  {
					  head=info.getReferenceAttr();
				  }
				  else if(s.equals("route"))
				  {
					  head = info.getCappRoute();
				  }
				  else if(s.equals("summary"))
				  {
					  head = info.getCappSummary();
				  }
				  else if(s.equals("regulation"))
				  {
					  head = info.getCappRegulation();
				  }
			  StringTokenizer st=new StringTokenizer(head,",");
			  int j=st.countTokens();			  
			  headingMethod=new String[j];
			  for(int i=0;st.hasMoreTokens();i++)
			  {				  
				  String en=st.nextToken();				  
				  headingMethod[i]="get"+en.substring(0,1).toUpperCase()+en.substring(1);				 
			  }		 
		  
		  return headingMethod;
	  }
	  /**
	   * ��ȡ��ǰ�û���ID
	   * @return
	   * @throws QMException
	   *@see String
	   */
	  public static String getCurUserID()
	  throws QMException
	  {
		  Class[] paraClass = {};
		    Object[] para = {};
		  BaseValueIfc base=(BaseValueIfc)  helper.request("SessionService","getCurUserInfo", paraClass, para);
		  return base.getBsoID();
	  }
	  /**
	   * �õ���ǰ�û��ġ��������á�����
	   * @param s
	   * @return
	   *@see PartAttrSetInfo
	   */
	  public static PartAttrSetInfo getPartAttrSetInfo()
	  throws QMException
	  {
		  Class[] paraClass = {};
		    Object[] para = {};
		    PartAttrSetInfo info=(PartAttrSetInfo)  helper.request(STANDARD,"getCurUserInfo", paraClass, para);
		   
		    if(info==null)
		    {
		    	String cappRegulation=RemoteProperty.getProperty("part.manager.cappRegulation",
		    			"technicsNumber,technicsName,technicsType,productState,versionValue");
		    	String cappRoute=RemoteProperty.getProperty("part.manager.cappRoute",
		    			"routeListNumber,routeListName,routeListLevel,routeListState,versionID");
		    	String cappSummary=RemoteProperty.getProperty("part.manager.cappSummary",
		    			"tecTotalNumber,tecTotalName,totalType,lifeCycleState");
		    	String description=RemoteProperty.getProperty("part.manager.description","docNum,docName,versionID");
		    	String reference=RemoteProperty.getProperty("part.manager.reference","number,name");
		    	String usage=RemoteProperty.getProperty("part.manager.usage","number,name,viewName,state,type,producedBy,iterationID,unitName,quantityString");
		    	
		    	String owner=getCurUserID();
		    	info=new PartAttrSetInfo();
		    	info.setCappRegulation(cappRegulation);
		    	info.setCappRoute(cappRoute);
		    	info.setCappSummary(cappSummary);
		    	info.setDescriptionAttr(description);
		    	info.setOwner(owner);
		    	info.setReferenceAttr(reference);
		    	info.setUsageAttr(usage);
		    	 Class[] paraClass1 = {BaseValueIfc.class};
		 	    Object[] para1 = {info};
		 	 info=(PartAttrSetInfo)  helper.request("PersistService","saveValueInfo", paraClass1, para1);
		 	
		    }
		   
		  return info;
	  }
	
	public static Vector getEPMDocument(BaseValueIfc baseIfc)
	throws QMException
	{
		 Class[] paraClass = {BaseValueIfc.class};
		    Object[] para = {baseIfc};
		  Vector epmdocs=(Vector)  helper.request(STANDARD,"getEMPDocument", paraClass, para);
		  return epmdocs;
	}
	
	 /**
     * ����ҵ�����ID��Ϣ������������ص����и������󣨼ƻ���
     * ��Ҫ��ҵ��������ʵ��ChangeableIfc�ӿڣ���
     * @param bsoID ҵ�����ID��Ϣ,Ҫ��ҵ��������ʵ��ChangeableIfc�ӿڡ�
     * @return ������ص����и������󣨼ƻ�����
     * @throws QMException
     */
    public static Collection getRelevantChangeRequests(String bsoID)
            throws
            QMException
    {
        Collection coll = null;
        ChangeableIfc chageable = null;
        BaseValueInfo qmobj = null;
        if (bsoID == null || bsoID.trim().length() == 0)
        {
            return null;
        }        
        Class[] paraClass = {String.class};
	    Object[] para = {bsoID};
	    qmobj=(BaseValueInfo)  helper.request("PersistService","refreshInfo", paraClass, para);       
        if (!(qmobj instanceof ChangeableIfc))
        {
            throw new QMException("ҵ��������ʵ��ChangeableIfc�ӿ�!");
        }
        else
        {
            chageable = (ChangeableIfc) qmobj;
        }
        
        Class[] paraClass1 = {ChangeableIfc.class};
	    Object[] para1 = {chageable};
	    coll=(Vector)  helper.request( "ChangeService","getRelevantChangeRequests", paraClass1, para1);      
        if (coll == null)
        {
            coll = new Vector(0);
        }
        return coll;
    }
    
    /**
     * ����ҵ�����ID��Ϣ������������ص��������ⱨ��
     * ��Ҫ��ҵ��������ʵ��ChangeableIfc�ӿڣ���
     * @param bsoID ҵ�����ID��Ϣ,Ҫ��ҵ��������ʵ��ChangeableIfc�ӿڡ�
     * @return ������ص����вο��������
     * @throws QMException
     */
    public static Collection getChangeIssues(String bsoID)
            throws
            QMException
    {
    	Collection coll = null;
        ChangeableIfc chageable = null;
        BaseValueInfo qmobj = null;
        if (bsoID == null || bsoID.trim().length() == 0)
        {
            return null;
        }        
        Class[] paraClass = {String.class};
	    Object[] para = {bsoID};
	    qmobj=(BaseValueInfo)  helper.request("PersistService","refreshInfo", paraClass, para);       
        if (!(qmobj instanceof ChangeableIfc))
        {
            throw new QMException("ҵ��������ʵ��ChangeableIfc�ӿ�!");
        }
        else
        {
            chageable = (ChangeableIfc) qmobj;
        }
        
        Class[] paraClass1 = {ChangeableIfc.class};
	    Object[] para1 = {chageable};
	    coll=(Vector)  helper.request( "ChangeService","getChangeIssues", paraClass1, para1);      
        if (coll == null)
        {
            coll = new Vector(0);
        }
        return coll;
    }
    
    /**
     * ����ҵ�����ID��Ϣ������������ص���δ��ɵ����и��Ļ
     * ��Ҫ��ҵ��������ʵ��ChangeableInf�ӿڣ���
     * @param bsoID ҵ�����ID��Ϣ,Ҫ��ҵ��������ʵ��ChangeableInf�ӿڡ�
     * @return ������ص���δ��ɵ����и��Ļ��
     * @throws QMException
     */
    public static Collection getAffectingChangeActivities(String bsoID)
            throws
            QMException
    {
    	Collection coll = null;
        ChangeableIfc chageable = null;
        BaseValueInfo qmobj = null;
        if (bsoID == null || bsoID.trim().length() == 0)
        {
            return null;
        }        
        Class[] paraClass = {String.class};
	    Object[] para = {bsoID};
	    qmobj=(BaseValueInfo)  helper.request("PersistService","refreshInfo", paraClass, para);       
        if (!(qmobj instanceof ChangeableIfc))
        {
            throw new QMException("ҵ��������ʵ��ChangeableIfc�ӿ�!");
        }
        else
        {
            chageable = (ChangeableIfc) qmobj;
        }
        
        Class[] paraClass1 = {ChangeableIfc.class};
	    Object[] para1 = {chageable};
	    coll=(Vector)  helper.request( "ChangeService","getAffectingChangeActivities", paraClass1, para1);      
        if (coll == null)
        {
            coll = new Vector(0);
        }
        return coll;
    }
    
    /**
     * ����ҵ�����ID��Ϣ������������ص�����ɵ����и��Ļ
     * ��Ҫ��ҵ��������ʵ��ChangeableIfc�ӿڣ���
     * @param bsoID ҵ�����ID��Ϣ,Ҫ��ҵ��������ʵ��ChangeableIfc�ӿڡ�
     * @return ������ص�����ɵ����и��Ļ��
     * @throws QMException
     */
    public static Collection getImplementedChangeActivities(String bsoID)
            throws
            QMException
    {
    	Collection coll = null;
        ChangeableIfc chageable = null;
        BaseValueInfo qmobj = null;
        if (bsoID == null || bsoID.trim().length() == 0)
        {
            return null;
        }        
        Class[] paraClass = {String.class};
	    Object[] para = {bsoID};
	    qmobj=(BaseValueInfo)  helper.request("PersistService","refreshInfo", paraClass, para);       
        if (!(qmobj instanceof ChangeableIfc))
        {
            throw new QMException("ҵ��������ʵ��ChangeableIfc�ӿ�!");
        }
        else
        {
            chageable = (ChangeableIfc) qmobj;
        }
        
        Class[] paraClass1 = {ChangeableIfc.class};
	    Object[] para1 = {chageable};
	    coll=(Vector)  helper.request( "ChangeService","getImplementedChangeActivities", paraClass1, para1);      
        if (coll == null)
        {
            coll = new Vector(0);
        }
        return coll;
    }

    /**
     * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
     * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
     * ���ݽṹɸѡ�������˳����Ͻṹɸѡ������masterIfc������QMPartMasterIfc�Ĺ����QMPartIfc�����С�汾��
     * @param masterIfc MasteredIfc
     * @param partConfigSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return Collection
     * @author liunan 2008-08-01
     */
    public static Collection filteredIterationsOf(MasteredIfc masterIfc, PartConfigSpecIfc partConfigSpecIfc)
        throws QMException
    {
      Class[] paraClass = {MasteredIfc.class, PartConfigSpecIfc.class};
      Object[] para = {masterIfc, partConfigSpecIfc};
      Collection collection2 = (Collection)helper.request(STANDARD,"filteredIterationsOf", paraClass, para);
      return collection2;
    }

    /**
     * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
     * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
     * <p>����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��</p>
     * <p>������������bianli()����ʵ�ֵݹ顣</p>
     * <p>�ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��</p>
     * <p>1��������������ԣ�BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��</p>
     * <p>2������������ԣ�BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�</p>
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
     * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Լ��ϣ�����Ϊ�ա�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õĶԵ�ǰ�㲿����ɸѡ������
     * @throws QMException
     * @return Vector
     * @author liunan 2008-08-01
     */
    public static Vector setBOMList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,String[] routeNames,
                             String source, String type, PartConfigSpecIfc configSpecIfc)
        throws QMException
    {
      Class[] paraClass = {QMPartIfc.class, String[].class, String[].class, String[].class, String.class, String.class, PartConfigSpecIfc.class};
      Object[] para = {partIfc, attrNames, affixAttrNames, routeNames, source, type, configSpecIfc};
      //CCBegin by liunan 2009-02-11
      //Vector vector = (Vector)helper.request(STANDARD,"setBOMList", paraClass, para);
      Vector vector = (Vector)helper.request("JFService","setBOMList", paraClass, para);
      //CCEnd by liunan 2009-02-11
      return vector;
    }

  /**
   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
   * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
   * <p>�ּ������嵥����ʾ��</p>
   * <p>���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�</p>
   * <p>0����ǰpart��BsoID��</p>
   * <p>1����ǰpart���ڵļ���ת��Ϊ�ַ��͡�</p>
   * <p>2����ǰpart�ı�š�</p>
   * <p>3����ǰpart�����ơ�</p>
   * <p>4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��͡�</p>
   * <p>5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��</p>
   * <p>              ������������ԣ��������ж��Ƶ����Լӵ���������С�</p>
   * <p>�����������˵ݹ鷽����</p>
   * <p>fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc, PartUsageLinkIfc partLinkIfc, int parentQuantity)��</p>
   * @param partIfc :QMPartIfc ����Ĳ���ֵ����
   * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա�
   * @param affixAttrNames : String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
   * @param routeNames : String[] ���ƵĹ���·�������ϣ�����Ϊ�ա�
   * @param configSpecIfc :PartConfigSpecIfc �ṹɸѡ������
   * @throws QMException
   * @return Vector
   */
  public static Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String[] routeNames, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, String[].class, String[].class, String[].class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, attrNames, affixAttrNames, routeNames, configSpecIfc};
    //CCBegin by liunan 2009-02-11
    //Vector vector = (Vector)helper.request(STANDARD,"setMaterialList", paraClass, para);
    Vector vector = (Vector)helper.request("JFService","setMaterialList", paraClass, para);
    //CCEnd by liunan 2009-02-11
    return vector;
  }
  
//CCBegin by leixiao	 2010-12-20  �����߼��ܳ���������,���Ӳ���islogic
  public static Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String[] routeNames, PartConfigSpecIfc configSpecIfc,boolean islogic)
  throws QMException
{
Class[] paraClass = {QMPartIfc.class, String[].class, String[].class, String[].class, PartConfigSpecIfc.class,boolean.class};
Object[] para = {partIfc, attrNames, affixAttrNames, routeNames, configSpecIfc,islogic};
Vector vector = (Vector)helper.request("JFService","setMaterialList", paraClass, para);
return vector;
}
//CCEnd by leixiao	 2010-12-20  �����߼��ܳ���������,���Ӳ���islogic
  
  /**
   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
   * <p>�ּ������嵥����ʾ��</p>
   * <p>���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�</p>
   * <p>0����ǰpart��BsoID��</p>
   * <p>1����ǰpart���ڵļ���ת��Ϊ�ַ��͡�</p>
   * <p>2����ǰpart�ı�š�</p>
   * <p>3����ǰpart�����ơ�</p>
   * <p>4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��͡�</p>
   * <p>5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��</p>
   * <p>              ������������ԣ��������ж��Ƶ����Լӵ���������С�</p>
   * <p>�����������˵ݹ鷽����</p>
   * <p>fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc, PartUsageLinkIfc partLinkIfc, int parentQuantity)��</p>
   * @param partIfc :QMPartIfc ����Ĳ���ֵ����
   * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա�
   * @param affixAttrNames : String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
   * @param routeNames : String[] ���ƵĹ���·�������ϣ�����Ϊ�ա�
   * @param configSpecIfc :PartConfigSpecIfc �ṹɸѡ������
   * @throws QMException
   * @return Vector
   * @author liunan 2008-08-05
   */
  public static Vector setMaterialList2(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String[] routeNames, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, String[].class, String[].class, String[].class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, attrNames, affixAttrNames, routeNames, configSpecIfc};
    //CCBegin by liunan 2009-02-11
    //Vector vector = (Vector)helper.request(STANDARD,"setMaterialList2", paraClass, para);
    Vector vector = (Vector)helper.request("JFService","setMaterialList2", paraClass, para);
    //CCEnd by liunan 2009-02-11    
    return vector;
  }
}
