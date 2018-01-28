/** ���ɳ��� EnterprisePartService.java    1.0    2003/02/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �ɶ��ṹ�������չ�� guoxiaoliang 
 */
package com.faw_qm.part.ejb.enterpriseService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import com.faw_qm.eff.model.EffConfigurationItemIfc;
import com.faw_qm.eff.model.EffManagedVersionIfc;
import com.faw_qm.eff.util.EffGroup;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.wip.model.WorkableIfc;


/**
 * ��ҵ�����񣬰�����Ʒ�ṹ�Ƚϡ���Ч�Թ���ȹ��ܵķ�����
 * @author ���ȳ�
 * @version 1.0
 */
public interface EnterprisePartService extends BaseService
{
    /**
     * ����ָ����Դ������Ŀ�겿�������Ե�ɸѡ������������������ʹ�ù�ϵ�Ĳ�ͬ��
     * �����������˵ݹ鷽����difference(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
     * QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc)��
     * ����ֵVector��Ԫ�ص����ݽṹString[]��
     * 0����ǰ���첿������ڸ������Ĳ�Σ����������Ϊ0�㣬���������Ӳ���Ϊ1�㣬�Դ����ƣ�
     * 1����ǰ���첿��������+"("+���+")" ��
     *
     * 2����ǰ���첿����Դ�����еİ汾+����ͼ�������Դ������û�иò����Ļ�����ʾ""��
     *    ���Դ�������иò��첿������û�з������ù淶�İ汾�Ļ���
     *    ��ʾ"û�з������ù淶�İ汾"��
     * 3����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Դ������û�иò����Ļ�����ʾ""
     *    ���Դ�������иò��첿������û�з������ù淶�İ汾�Ļ����ճ���ʾ����+����λ����
     *
     * 4����ǰ���첿����Ŀ�겿���еİ汾+����ͼ�������Ŀ�겿����û�иò����Ļ�����ʾ""��
     *    ���Ŀ�겿�����иò��첿������û�з������ù淶�İ汾�Ļ���
     *    ��ʾ"û�з������ù淶�İ汾"��
     * 5����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Ŀ�겿����û�иò����Ļ�����ʾ""
     *    ���Ŀ�겿�����иò��첿������û�з������ù淶�İ汾�Ļ����ճ���ʾ����+����λ����
     *
     * 6����ʾ����ɫ�ı�ʶ::������첿����Դ������Ŀ�겿���ж����ڣ����������߰汾��ͬ�Ļ�����ʾΪ��ɫ(black)��
     *    ������첿����Դ�����д��ڣ�������Ŀ�겿���в����ڵĻ�����ʾΪ��ɫ(green)��
     *    ������첿����Ŀ�겿���д��ڣ�������Դ�����в����ڵĻ�����ʾΪ��ɫ(purple)��
     *    ������첿����Դ��������Ŀ�겿���еİ汾����������ͬ�Ļ�����ʾΪ��ɫ(gray)��
     *    ��Ϣ"û�з������ù淶�İ汾"����ɫΪ��ɫ(red),���и���Ϣ���е���ɫΪ��ɫ(black)��

     * 7:�����㲿����BsoID�������Դ�㲿�����Եģ�����Դ�㲿��ʹ�õĸò����㲿����BsoID��
     *
     * 8:�����㲿����BsoID, �����Ŀ���㲿�����Եģ�����Ŀ���㲿��ʹ�õĵĲ����㲿����BsoID��

     * @param sourcePartIfc :QMPartIfcԴ������ֵ����
     * @param objectPartIfc :QMPartIfcĿ�겿����ֵ����
     * @param sourceConfigSpecIfc :PartConfigSpecIfcԴ������ɸѡ������
     * @param objectConfigSpecIfc :PartConfigSpecIfcĿ�겿����ɸѡ������
     * @return vector:Vector
     * @throws QMException
     */
    public Vector getBOMDifferences(QMPartIfc sourcePartIfc,
                                    PartConfigSpecIfc sourceConfigSpecIfc,
                                    QMPartIfc objectPartIfc,
                                    PartConfigSpecIfc objectConfigSpecIfc,HashMap map)
            throws QMException;


    /**
     * ������collection�е�Ԫ����������ΪPartUsageLinkIfc������rightBsoID(�㲿��������Ϣ��bsoID)Ϊ���ֱ����ڹ������в����ع�����
     * @param collection :CollectionPartUsageLinkIfc���󼯺ϡ�
     * @return hashtable:Hashtable������
     */
    public Hashtable consolidateUsageLink(Collection collection);


    /**
     * ���part����������PartUsageLinkIfc����ļ��ϡ�
     * @param partIfc :QMPartIfc����
     * @return collection:Colllection��part����������PartUsageLinkifc���󼯺ϡ�
     * @throws QMException
     */
    public Collection getConsolidatedBOM(QMPartIfc partIfc)
            throws QMException;


    /**
     * �½��͸�����Ч�Է�����
     * @param effConfigItemIfc EffConfigurationItemIfc ��Ч�Է�����
     * @throws QMException
     * @return EffConfigurationItemIfc ��Ч�Է�����
     */
    public EffConfigurationItemIfc saveConfigItemIfc(EffConfigurationItemIfc
            effConfigItemIfc)
            throws QMException;


    /**
     * ɾ����Ч�Է�����
     * @param configItemIfc EffConfigurationItemIfc ��Ч�Է�����
     * @throws QMException
     */
    public void deleteConfigItemIfc(EffConfigurationItemIfc configItemIfc)
            throws QMException;


    /**
     * ����һ��EffGroup����
     * @param effectivityType ��Ч�����͡�
     * @param value_range ֵ��Χ��
     * @param configItemIfc QMConfigurationItemIfc ���ù淶��
     * @param partIfc EffManagedVersionIfc �㲿����
     * @throws QMException
     * @return EffGroup
     */
    public EffGroup createEffGroup(String effectivityType, String value_range,
                                   QMConfigurationItemIfc configItemIfc,
                                   EffManagedVersionIfc partIfc)
            throws QMException;


    /**
     * ����ڵ�ǰɸѡ������partMasterIfc�Ľṹ�������Ӽ���
     * @param partMasterIfc �㲿������Ϣֵ����
     * @param configSpecIfc ���ù淶��
     * @return QMPartInfo[] �����Ӳ���ֵ����ļ��ϡ�
     * @exception QMException
     */
    public QMPartIfc[] getAllSubPartsByConfigSpec(QMPartMasterIfc partMasterIfc,
                                                  PartConfigSpecIfc
                                                  configSpecIfc)
            throws QMException;


    /**
     * ���partIfc�Ѿ����ڵ�EffGroup����ļ��ϡ�
     * @param partIfc QMPartIfc �㲿����
     * @throws QMException
     * @return Vector
     */
    public Vector outputExistingEffGroups(QMPartIfc partIfc)
            throws QMException;


    /**
     * ����ڵ�ǰɸѡ������partMasterIfc�Ľṹ�������Ӽ�����Ч�Է�Χ���������EffGroup��
     * ���ϡ��÷���������ʾ���ṹ�����Ч�Խ����
     * @param partMasterIfc :�㲿������Ϣֵ����
     * @param configSpecIfc :PartConfigSpecIfc ���ù淶ֵ����
     * @param configItemID ��Ч��������bsoID��
     * @param value_range :String ��Ч��ֵ��Χ��
     * @return Vector
     * @throws QMException
     */
    public Vector getEffGroups(QMPartMasterIfc partMasterIfc,
                               PartConfigSpecIfc configSpecIfc,
                               String configItemID, String value_range)
            throws QMException;


    /**
     * �����㲿��ֵ�����ȡ���㲿����������Ч��ֵ��
     * @param partInfo : QMPartInfo �㲿��ֵ����
     * @return Vector EffGroup�ļ��ϡ�
     * @throws QMException
     */
    public Vector getEffVector(WorkableIfc partInfo)
            throws QMException;


    /**
     * �����㲿��ֵ�����ȡ���㲿����������Ч��ֵ��
     * @param partInfo : QMPartInfo �㲿��ֵ����
     * @param isacl �Ƿ���ʿ��ơ�
     * @return Vector EffGroup�ļ��ϡ�
     * @throws QMException
     */
    public Vector getEffVector(WorkableIfc partInfo, boolean isacl)
            throws QMException;


    /**
     * ɾ��part��Ӧ����Ч��ֵ
     *
     * @param partBsoID �����bsoID��
     * @param configItemName ��Ч�Է������ơ�
     * @param effectivityType ��Ч������:"DATE","LOT_NUM","SERIAL_NUM"��
     * @throws QMException
     */
    public void deleteAllEffs(String partBsoID, String configItemName,
                              String effectivityType)
            throws QMException;


    /**
     * ����part��Ӧ����Ч��ֵ��
     *
     * @param partIfc �����
     * @param configItemName ��Ч�Է������ơ�
     * @param effectivityType ��Ч�����Ͳ���Ϊ�ձ�����:"DATE","LOT_NUM","SERIAL_NUM"֮һ��
     * @param effValue ��Ч��ֵ��
     * @throws QMException
     */
    public void createEff(QMPartIfc partIfc, String configItemName,
                          String effectivityType, String effValue)
            throws QMException;


    /**
     * ����part��Ӧ����Ч��ֵ��
     *
     * @param partIfc QMPartIfc �����
     * @param configItemName String ��Ч�Է������ơ�
     * @param oldEffectivityType String
     *   ��Ч�����Ͳ���Ϊ�ձ�����:"DATE","LOT_NUM","SERIAL_NUM"֮һ��
     * @param newEffValue String �µ���Ч��ֵ��Χ��
     * @throws QMException
     */
    public void updateEff(QMPartIfc partIfc, String configItemName,
                          String oldEffectivityType, String newEffValue)
            throws QMException;

    public Vector getEffVector(WorkableIfc partIfc,String itemName) throws QMException;
    
  //CCBegin by leixiao 2011-1-12 ԭ��:���·�������� 
    public Vector getAllSubPartsByConfigSpec(QMPartIfc partIfc,
                                                  PartConfigSpecIfc
                                                  configSpecIfc)
            throws QMException;    
  //CCBegin by leixiao 2011-1-12 ԭ��:���·�������� 
    
    //CCBegin SS1
    /**
     * ��ӡ���Ľṹ���ܣ����ؼ���û���ظ��ļ�
     * ����ڵ�ǰɸѡ������partMasterIfc�Ľṹ�������Ӽ���
     * @param partMasterIfc �㲿������Ϣֵ����
     * @param configSpecIfc ���ù淶��
     * @return Vector �����Ӳ���ֵ����ļ��ϡ�
     * @exception QMException
     */
    public Vector getAllSubPartsByConfigSpecForQQ(QMPartMasterIfc partMasterIfc,
        PartConfigSpecIfc configSpecIfc) throws QMException;
    
    //CCEnd SS1
}
