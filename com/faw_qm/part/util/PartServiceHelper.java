/**
 * ���ɳ��� PartServiceHelper.java    1.0    2003/02/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/27 л�� ԭ��:�߼�����
 *                     ����:��д����
 *                     ��ע:���v3r11-�ּ������嵥�����Ż�-л��
 * CR2 2009/06/09 ������ ԭ�򣺲鿴���岿��ʹ�ýṹͼ����ʾ����ȷ(td-2258)
 *                       ��������ӹ��岿����ͼ��
 * CR3 20090619 ��ǿ �޸�ԭ�򣺽��нṹ�ȽϺ󣬲�Ʒ��Ϣ���������ù淶��ʾΪ�ṹ�Ƚ�Ŀ��������ù淶����TD-2190�� 
 *                   ������������нṹ�ȽϺ󣬲�Ʒ��Ϣ��������ȷ��ʾ�����ù淶��
 * CR4 2009/12/25 ���� �޸�ԭ��TD2741���ڲ鿴������ʹ�ýṹʱ�����һ���䲻�������ù淶���Ӽ��ı�����ӣ�ҳ�����
 *                     ��������� ��ӷ���getPartNumberAndName���ڻ�ȡ�㲿����ź����ƣ�������֯jspҳ����Ϣ��                   
 * SS1 ��ȡ�����㲿��С�汾�Ĳο��ĵ��� liunan 2013-4-1
 * SS2 �ṹ�Ƚ��������ձ��������״̬Ϊ����������⣬�����жϡ� liunan 2013-6-6
 * SS3 2013-1-21  ��Ʒ��Ϣ����������������嵥�����������������erp���Ա����ܣ������ĸ�ʽ�ļ����ݰ������㲿��������š������������������ 
                  ÿ��������������λ����Դ������·�ߡ�װ��·�ߡ���T���ܡ��̶���ǰ�ںϼơ��ɱ���ǰ�ڣ���
                  ���к����������е�������Ҫ���㲿�������Ĺ��տ��л�ȡ
 * SS4 �������Ʒ��������㲿��ʱ���ж�Ȩ��,ֱ����� pante 2013-12-19
 * SS5 ��ȡ���ӹ�˾�����嵥�б� liuyang 2013-12-18
 * SS6 ������ɫ����ʶ liuyang 2014-6-13
 * SS7 �Ƚ��㲿�����ߵİ汾�������汾�Ƿ���ͬ����ͬ��ɫ��� xianglx 2014-10-12
 * SS8 ���㲿�����������汾�Ĳο��ĵ� gaoys 2015-4-15
 * SS9 ����windchill����10.2��ֻ�����㲿��������pdf�������ļ���ԭͼ�������㲿���¡� liunan 2015-8-21
 * SS10 A004-2015-3167 �������㲿��ͼֽ���㲿������Ҫ�ڼ�ĳ��λ��������ӣ����ӵ�ָ���㲿���鿴���档 liunan 2015-9-21
 * SS11 �ⲿϵͳͨ���㲿����źͰ汾�鿴pdmϵͳ�������ļ��� liunan 2015-9-23
 * SS12 �鿴�㲿����pdf�������ļ���Ҫͨ��epm��Ȩ�������ƣ���epm��Ȩ����������߲鿴����epm����Ȩ����������ء� liunan 2015-10-23
 * SS13 A004-2015-3229 �㲿�������ļ������� liunan 2015-12-7
 * SS14 A004-2016-3286 ����һ�����嵥 liunan 2016-1-20
 * SS15 A004-2016-3338 �ж��Ƿ������غ����߲鿴Ȩ�ޣ����ȸ��ݹ���epm�鿴Ȩ�ޣ��ж�̬Ȩ�ޣ���û�й����Ų鿴��ľ�̬Ȩ�ޡ� liunan 2016-4-1
 *      �����ļ�Ȩ��ֻ��epm��ӦȨ�ޡ� liunan 2017-3-10
 * SS16 A004-2015-3231 �޸Ļ�ȡ�����ĵ��ķ������㲿���ǹ��岿�����ó�������岿�����ù��������ҵ������ļ������Ӳ鿴�����ļ�����ѡ�� liunan 2016-11-2
 * SS17 A004-2017-3462 ��ѯһ����������ʱ��������ı䡣 liunan 2017-2-14
 * SS18 A004-2017-3479 ���Ų��ܿ�2502018K5HC2ͼֽ��û��epmȨ�ޣ����ӶԶ�̬Ȩ���ֶε��жϡ� liunan 2017-3-16
 * SS19 ���з��������Ƶ���׼�����Ḳ��ԭ�м�����û��ͼֽ����ʱ��Ҫ��ʾԭͼֽ�� liunan 2017-6-6
 * SS20 �����㲿������Ȩ�޵��жϣ����ӻ�ȡ�㲿��doc�����ķ����� liunan 2017-6-8
 */

package com.faw_qm.part.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.auth.RequestHelper;
import com.faw_qm.baseline.ejb.service.BaselineService;
import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.baseline.model.ManagedBaselineInfo;
import com.faw_qm.clients.common.CommonSearchHelper;
import com.faw_qm.clients.vc.controller.CheckInOutTaskLogic;
import com.faw_qm.codemanage.model.CodingClassificationInfo;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.content.ejb.service.ContentHolder;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.eff.ejb.service.EffService;
import com.faw_qm.eff.model.EffContextIfc;
import com.faw_qm.eff.util.EffGroup;
import com.faw_qm.effectivity.model.EffectivityManageableIfc;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.effectivity.model.QMConfigurationItemInfo;
import com.faw_qm.effectivity.util.EffectivityType;
import com.faw_qm.epm.build.model.EPMBuildHistoryIfc;
import com.faw_qm.epm.build.model.EPMBuildLinksRuleIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentInfo;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.definition.litedefinition.UnitDefView;
import com.faw_qm.iba.definition.model.AbstractAttributeDefinitionIfc;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.lifecycle.model.LifeCycleTemplateIfc;
import com.faw_qm.lifecycle.util.LifeCycleServiceHelper;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartDescribeLinkInfo;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.pcfg.family.model.GenericPartIfc;
import com.faw_qm.pcfg.family.model.GenericPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.project.model.ProjectIfc;
import com.faw_qm.ration.exception.RationException;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.units.util.QuantityOfMeasureDefaultView;
import com.faw_qm.users.model.ActorIfc;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.viewmanage.util.ViewHelper;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.model.CheckoutLinkInfo;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;

import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;

//CCBegin SS9
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemIfc;
//CCEnd SS9
//CCBegin SS10
import com.faw_qm.jfpublish.receive.PublishHelper;
//CCEnd SS10
//CCBegin SS12
import com.faw_qm.acl.ejb.entity.QMPermission;
import com.faw_qm.acl.ejb.service.AccessControlService;
import com.faw_qm.users.ejb.entity.User;
//CCEnd SS12
//CCBegin SS13
import com.faw_qm.pcfg.doc.model.*;
//CCEnd SS13
//CCBegin SS16
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.faw_qm.pcfg.family.FamilyWebHelper;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.content.util.StreamUtil;
import com.faw_qm.content.model.StreamDataInfo;
//CCEnd SS16
//CCBegin SS18
import com.faw_qm.users.ejb.service.UsersService;
import java.util.Enumeration;
import com.faw_qm.users.model.GroupInfo;
//CCEnd SS18

//CCBegin by liunan 2008-08-01
/**
 * Ϊ�ݿͻ����ṩ�ķ�����ÿ�����������÷����е�ͬ��������
 * @author ���ȳ�  �����
 * @author ������
 * @version 1.0
 * 
 */
//����(1)20080228 ��ǿ�޸� �޸�ԭ��:.�ж���Ч�Է��������ͺ�ѡ��������Ƿ�һ�£������һ���򵯳���ʾ��Ϣ��
//����(2)20080808 ��ǿ�޸� �޸�ԭ���ڲ����㲿���ı����ڲ�Ʒʱ����ʾ����ȷ����TD-1794��
//����(3)20081014 ��ǿ�޸� �޸�ԭ��:���մ����㲿��ʱ������״̬Ӧ����ʾΪ��δ���롱����TD-1868��
//����(4)20081028 ��ǿ�޸� �޸�ԭ��:�㲿���ݿͻ��鿴�������λ�ĸ���������������ʾ��λΪ���Զ�������ٶ���Ĭ�ϵ�λ����TD-1893��
//����(5)20081103 ��ǿ�޸� �޸�ԭ��:�û�û�в鿴�������ϼе��ĵ���Ȩ��ʱ���ο��ĵ���ʾΪ�գ�Ӧ����ʾ�ĵ�����Ϣ����TD-1906��
public class PartServiceHelper implements Serializable
{
  private static Object lock = new Object();
  static final long serialVersionUID = 1L;
  String resource = "com.faw_qm.part.util.PartResource";

  /**
   * ���캯����
   */
  public PartServiceHelper()
  {
  }

  /**
   * �����Ѿ��־û����㲿����ֵ�����ø��㲿��������ʹ�ù�ϵ��ֵ���󼯺ϡ�
   * @param partID :String�־û��㲿����ֵ����bsoID��
   * @return vector:Vector�����part������PartUsageLinkIfc�ļ��ϡ�
   * @throws QMException
   */
  public Vector getAllUsageLinks(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getAllUsageLinks" + "(String) begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    PartDebug.trace(this, "getAllUsageLinks" + "(String) end....return is Vector ");
    return new Vector(PartServiceRequest.getUsesPartMasters(partIfc));
  }

  /**
   * �����㲿��ֵ�����ø��㲿�����������вο��ĵ�(DocIfc)���°汾��ֵ����ļ��ϡ�
   * @param partID :Stringָ�����㲿����ֵ����bsoID��
   * @return Vector �㲿���ο��ĵ�(DocIfc)ֵ����ļ��ϣ�
   * �ȸ����㲿����ȡ���еĲο��ĵ�����Ϣ(DocMasterIfc)�ļ���,�ٶ�DocMasterIfc����
   * ���й��ˣ��ҳ�ÿ��DocMasterIfc����Ӧ�����°汾DocIfc����󷵻�DocIfc��һЩ
   * �������Եļ���:����ֵVector��ÿ��Ԫ�ض���String[]:
   * <br>String[0] : �ĵ���bsoID;
   * <br>String[1] : �ĵ��ı�ţ�
   * <br>String[2] : �ĵ������ƣ�
   * <br>String[3] : �ĵ��ķ������ͣ�
   * <br>String[4] : �ĵ��Ĵ�汾�ţ�
   * <br>String[5] : �ĵ��ļ�����״̬��
   * <br>String[6] : �ĵ������ݸ�ʽID(DataFormatID)��
   * @throws QMException
   */
  public Vector getReferencesDocIfc(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getReferences" + "DocIfc(String) begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    PartDebug.trace(this, "getReferences" + "DocIfc(String) end....return is Vector");
    Vector docVector = PartServiceRequest.getReferencesDocMasters(partIfc);
    Vector resultVector = new Vector();
    for(int i = 0; i < docVector.size(); i++)
    {
      Object obj = docVector.elementAt(i);
      if(obj instanceof DocIfc)
      {
        DocIfc docIfc = (DocIfc)obj;
        String[] temp = new String[7];
        temp[0] = docIfc.getBsoID();
        temp[1] = docIfc.getDocNum();
        temp[2] = docIfc.getDocName();
        temp[3] = docIfc.getDocCfName();
        temp[4] = docIfc.getVersionID();
        temp[5] = docIfc.getWorkableState();
        temp[6] = docIfc.getDataFormatID();
        resultVector.addElement(temp);
      }
    }//end for(int i=0; i<docVector.size(); i++)
    return resultVector;
  }
  //���!!!!

  //�޸�5 20081103 zhangq begin �޸�ԭ���û�û�в鿴�������ϼе��ĵ���Ȩ��ʱ���ο��ĵ���ʾΪ�գ�Ӧ����ʾ�ĵ�����Ϣ��
  /**
   * �����㲿��ֵ�����ø��㲿�����������вο��ĵ�(DocMasterIfc)������Ϣֵ����ļ��ϡ�
   * @param partID :Stringָ�����㲿����ֵ����bsoID��
   * @return Vector �㲿���ο��ĵ�������Ϣֵ����(DocMasterIfc)�ļ��ϣ�
   * �ȸ����㲿����ȡ���еĲο��ĵ�����Ϣ(DocMasterIfc)�ļ���,�ٷ���DocMasterIfc��һЩ
   * �������Եļ���:����ֵVector��ÿ��Ԫ�ض���String[]:
   * <br>String[0] : �ĵ�Master��bsoID;
   * <br>String[1] : �ĵ��ı�ţ�
   * <br>String[2] : �ĵ������ƣ�
   * @throws QMException
   */
  public Vector getReferencesDocMasters(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getReferences" + "DocMasters(String) begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    PartDebug.trace(this, "getReferences" + "DocMasters(String) end....return is Vector");
    Vector docVector = getDocMastersByPartIfc(partIfc);
    Vector resultVector = new Vector();
    Object obj = null;
    for(int i = 0; i < docVector.size(); i++)
    {
      obj = docVector.elementAt(i);
      if(obj instanceof DocMasterIfc){
          DocMasterIfc docMasterIfc = (DocMasterIfc)obj;
          String[] temp = new String[3];
          temp[0] = docMasterIfc.getBsoID();
          temp[1] = docMasterIfc.getDocNum();
          temp[2] = docMasterIfc.getDocName();
          resultVector.addElement(temp);
      }
    }
    return resultVector;
  }
  //�޸�5 20081103 zhangq end

  /**
   * ����ָ�����㲿����ֵ���󷵻ظ��㲿�������ĵ���ֵ���󼯺ϡ�
   * 
   * @param partID :String�㲿����ֵ����bsoID��
   * @return vector:Vector�㲿�������ĵ���ֵ���󼯺ϣ�
   * ���ص�Vector�е�ÿ��Ԫ�ض���String[]:
   * <br>String[0] : �ĵ���BsoID��
   * <br>String[1] : �ĵ��ı�ţ�
   * <br>String[2] : �ĵ������ƣ�
   * <br>String[3] : �ĵ��Ĵ�汾��
   * <br>String[4] : �ĵ�����������״̬��
   * <br>String[5] : �ĵ��ļ�����״̬
   * <br>String[6] : �ĵ������ݸ�ʽID(DataFormatID)��
   * @throws QMException
   */
  public Vector getDescribedByDocs(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getDescribedBy" + "Docs(String) begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    PartDebug.trace(this, "getDescribed" + "ByDocs(String) end....return is Vector");
    Vector docVector = PartServiceRequest.getDescribedByDocs(partIfc, true);
    Vector resultVector = new Vector();
    for(int i = 0; i < docVector.size(); i++)
    {
      Object obj = docVector.elementAt(i);
      if(obj instanceof DocIfc)
      {
        DocIfc docIfc = (DocIfc)obj;
        String[] temp = new String[7];
        temp[0] = docIfc.getBsoID();
        temp[1] = docIfc.getDocNum();
        temp[2] = docIfc.getDocName();
        temp[3] = docIfc.getVersionID();
        temp[4] = docIfc.getLifeCycleState().getDisplay();
        temp[5] = docIfc.getWorkableState();
        temp[6] = docIfc.getDataFormatID();
        resultVector.addElement(temp);
      }//end if(obj instanceof DocIfc)
    }//end for(int i=0; i<docVector.size(); i++)

    return resultVector;
  }
  /**
   * ����ָ�����㲿����ֵ���󷵻ظ��㲿��EPM�ĵ���ֵ���󼯺�,�����ǰ�汾���㲿��û��EPM�ĵ���
   * ��ȡ����һ�����ĵ����㲿��������EMP�ĵ���
   * @param partID :String�㲿����ֵ����bsoID��
   * @return vector:Vector�㲿�������ĵ���ֵ���󼯺ϣ�
   * ���ص�Vector�е�ÿ��Ԫ�ض���String[]:
   * <br>String[0] : �ĵ���BsoID��
   * <br>String[1] : �ĵ��ı�ţ�
   * <br>String[2] : �ĵ������ƣ�
   * <br>String[3] : �ĵ��Ĵ�汾��
   * <br>String[4] : �ĵ�����������״̬��
   * <br>String[5] : �ĵ��ļ�����״̬
   * <br>String[6] : �ĵ������ݸ�ʽID(DataFormatID)
   * @throws QMException
   */
  public Vector getEPMDescribedByDocs(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getEPMDescribedByDocs" + "Docs(String) begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    Vector retVal = new Vector();
    retVal = getEpmDocFromPart(partIfc);
    //CCBegin by liunan 2009-05-12
    //ȥ��û�е�ǰ�汾�Ĺ���ʱ��ȥ����ʷ�汾��part�ķ�����
    //û�й����Ͳ�����ʾ������part��
    //Դ���룺
    /*if(retVal.size() != 0)
      return retVal;
    else
    {
      VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
      Collection allPreversion = vcService.allIterationsFrom(partIfc);
      Iterator iter2 = allPreversion.iterator();
      while(iter2.hasNext())
      {
        QMPartIfc tempPart = (QMPartIfc)iter2.next();
        retVal = getEpmDocFromPart(tempPart);
        if(retVal.size() != 0)
         return retVal;
      }
    }*/
    //CCEnd by liunan 2009-05-12
    return retVal;
  }


  private Vector getEpmDocFromPart(QMPartIfc partIfc)throws QMException
  {
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    Vector retVal = new Vector();
    QMQuery query = new QMQuery("EPMBuildHistory");
    QueryCondition condition = new QueryCondition("rightBsoID","=",partIfc.getBsoID());
    query.addCondition(condition);
    Collection coll = pService.findValueInfo(query);
    Iterator iter = coll.iterator();
    while(iter.hasNext())
    {
      EPMBuildHistoryIfc link = (EPMBuildHistoryIfc)iter.next();
      EPMDocumentIfc doc = (EPMDocumentIfc)link.getBuiltBy();
      String[] temp = new String[7];
      temp[0] = doc.getBsoID();
      temp[1] = doc.getDocNumber();
      temp[2] = doc.getDocName();
      temp[3] = doc.getVersionID();
      temp[4] = doc.getLifeCycleState().getDisplay();
      temp[5] = doc.getWorkableState();
      temp[6] = doc.getDataFormatID();
      retVal.addElement(temp);
      temp = null;
    }
      return retVal;
  }
  /**
   * ��ÿ��Ա�ָ���㲿���滻�����㲿����ֵ����ļ��ϡ�
   * @param partMasterID :String:���㲿����ֵ����bsoID��
   * @return collection:Collection�������㲿���滻�����㲿����ֵ����ļ��ϡ�
   * @throws QMException
   */
  public Collection getAlternateForPartMasters(String partMasterID)
      throws QMException
  {
    PartDebug.trace(this, "getAlternateForPart" + "Masters(partMasterID) begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(partMasterID);
    PartDebug.trace(this, "getAlternateFor" + "PartMasters(partMasterID) end....return is Collection ");
    return PartServiceRequest.getAlternateForPartMasters(partMasterIfc);
  }

  /**
   * ���ָ�����㲿�����滻����ֵ����ļ��ϡ�
   * @param partMasterID :String���㲿����ֵ����bsoID��
   * @return collection:Collection�����㲿���滻����ֵ����ļ��ϡ�
   * @throws QMException
   */
  public Collection getAlternatesPartMasters(String partMasterID)
      throws QMException
  {
    PartDebug.trace(this, "getAlternatesPart" + "Masters(partMasterID) begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(partMasterID);
    PartDebug.trace(this, "getAlternates" + "PartMasters(partMasterID) end....return is Collection ");
    return PartServiceRequest.getAlternatesPartMasters(partMasterIfc);
  }

  /**
   * ����ָ���Ľṹ����滻���ļ��ϡ�
   * @param usageLinkID ָ����һ���ṹ��ֵ����bsoID��
   * @return collection�����滻�����㲿����ֵ����QMPartMasterIfc�ļ��ϡ�
   * @throws QMException
   */
  public Collection getSubstitutesPartMasters(String usageLinkID)
      throws QMException
  {
    PartDebug.trace(this, "getSubstitutes" + "PartMasters(usageLinkID) begin ....");
    if(usageLinkID == null || usageLinkID.length() == 0)
    {
      PartDebug.trace(this, "getSubstitutes" + "PartMasters(usageLinkID) end....return is null");
      return null;
    }
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc)pService.refreshInfo(usageLinkID);
    PartDebug.trace(this, "getSubstitutes" + "PartMasters(usageLinkID) end....return is Collection ");
    return PartServiceRequest.getSubstitutesPartMasters(usageLinkIfc);
  }

  /**
   * ����ָ�������㲿����ø��㲿�������滻�Ľṹ��ֵ����ļ��ϡ�
   * @param partMasterID :Stringָ�������㲿��ֵ����bsoID��
   * @return collection:Collection���㲿�������滻�Ľṹֵ����ļ��ϡ�
   * @throws QMException
   */
  public Collection getSubstituteForPartUsageLinks(String partMasterID)
      throws QMException
  {
    PartDebug.trace(this, "getSubstituteFor" + "PartUsageLinks(partMasterID) begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(partMasterID);
    PartDebug.trace(this, "getSubstitute" + "ForPartUsageLinks(partMasterID) end....return is Collection");
    return PartServiceRequest.getSubstituteForPartUsageLinks(partMasterIfc);
  }

  /**
   * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
   * 
   * @param partID :String ָ�����㲿����bsoID.
   * @param attrNames :String ����Ҫ��������Լ��ϣ����Ϊ�գ��򰴱�׼�������
   * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
   * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
   * @param effectivityActive �Ƿ�����Ч�����ù淶 ��1,��0
   * @param baselineActive �Ƿ��ǻ�׼�����ù淶 ��1,��0
   * @param standardActive �Ƿ��Ǳ�׼���ù淶 ��1,��0
   * @param baselineID ��׼�ߵ�BsoID
   * @param configItemID ���ù淶��BsoID
   * @param viewObjectID ��ͼ�����bsoID
   * @param effectivityType ��Ч������
   * @param effectivityUnit ��Ч�Ե�λ ��Ҫ�־û�
   * @param state ״̬
   * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true
   * @throws QMException
   * @return Vector �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
   *<br> 1��������������ԣ�
   * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true", "false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
   * <br>2������������ԣ�
   * BsoID�����롢���ơ��ǣ��񣩿ɷ�("true", "false")�������������������ԡ�
   */
  public Vector getBOMList(String partID, String attrNames, String source,
                           String type,
                           String effectivityActive, String baselineActive,
                           String standardActive,
                           String baselineID, String configItemID,
                           String viewObjectID, String effectivityType,
                           String effectivityUnit, String state,
                           String workingIncluded)
      throws QMException
  {
    PartDebug.trace(this, "getBOMList() begin ....");
    Vector allAttrNames = new Vector();
    String[] attrNames1 = null;
    String[] affixAttrNames = null;
    if(attrNames != null && attrNames.length() > 0)
    {
      allAttrNames = getTableHeader(attrNames);
      if(allAttrNames != null && allAttrNames.size() > 0)
      {
        attrNames1 = (String[])allAttrNames.elementAt(0);
        affixAttrNames = (String[])allAttrNames.elementAt(1);
      }
    }
    //3��6��:��Ҫ��ת��partIDΪpart.BsoID��ת��configSpecΪPartConfigSpecIfc����
    //�ٵ��÷���:StandardPartService.setBOMList(QMPartIfc partIfc, String[] attrNames, String source,
    //String type, PartConfigSpecIfc configSpecIfc)����!!!
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    String isHasConfigItem = "1";
    PartConfigSpecIfc partConfigSpecIfc = PartServiceRequest.hashtableToPartConfigSpecIfc(
        effectivityActive, baselineActive, standardActive, baselineID,
        configItemID, viewObjectID, effectivityType, effectivityUnit, state, workingIncluded);
    PartDebug.trace(this, "getBOMList() " + "end....return is Vector");
   
    return PartServiceRequest.setBOMList(partIfc, attrNames1, affixAttrNames, source, type, partConfigSpecIfc);
  }

  /**
   * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
   *
   * @param partID :String ָ�����㲿����bsoID.
   * @param attrNames :String ����Ҫ��������Լ��ϣ����Ϊ�գ��򰴱�׼�������
   * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
   * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
   * @throws QMException
   * @return Vector  �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
   * <br>1��������������ԣ�
   * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true", "false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
   *<br> 2������������ԣ�
   * BsoID�����롢���ơ��ǣ��񣩿ɷ�("true", "false")�������������������ԡ�
   */
 /* public Vector getBOMList(String partID, String attrNames, String source, String type)
      throws QMException
  {
//    Vector allAttrNames = new Vector();
    String[] attrNames1 =  attrNames.split(",");
    String[] affixAttrNames = null;
//    if(attrNames != null && attrNames.length() > 0)
//    {
//      allAttrNames = getTableHeader(attrNames);
//      if(allAttrNames != null && allAttrNames.size() > 0)
//      {
//        attrNames1 = (String[])allAttrNames.elementAt(0);
//        affixAttrNames = (String[])allAttrNames.elementAt(1);
//      }
//    }
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
   
   
    return PartServiceRequest.setBOMList(partIfc, attrNames1, affixAttrNames, source, type, partConfigSpecIfc);
  }*/

  /**
   * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿���������嵥��Ϣ��
   * 
   * @param partID String ָ���㲿����bsoID
   * @param attrNames String ���Ƶ����ԣ�����Ϊ��
   * @throws QMException
   * @return Vector ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
   *<br> 0����ǰpart��BsoID��
   *<br> 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
   *<br> 2����ǰpart�ı�ţ�
   * <br>3����ǰpart�����ƣ�
   * <br>4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
   * <br>5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
   * <br>             ��������������ԣ��������ж��Ƶ����Լӵ���������С�
   */
/*public Vector getMaterialList(String partID, String attrNames) throws//Begin CR1
  QMException {
attrNames = attrNames.trim();
if (attrNames == null || attrNames.length() == 0) {
  Vector result = new Vector();
  return result;
}
int newLength = 0;
for (int i = 0; i < attrNames.length(); i++) {
  if (attrNames.charAt(i) == ',') {
    newLength++;
  }
}
String[] attrNames1 = new String[newLength];    
try {
  for (int i = 0,j = 0, k = 0; i < attrNames.length(); i++) {
    if (attrNames.charAt(i) == ',') {
      String str = attrNames.substring(j, i);           
        attrNames1[k] = str;
        k++;          
      j = i + 1;
    }
  }
}
catch (Exception e) {
  e.printStackTrace();
}
PersistService pService = (PersistService) EJBServiceHelper.
    getPersistService();
QMPartIfc partIfc = (QMPartIfc) pService.refreshInfo(partID);    
Vector v =PartServiceRequest.setMaterialList(partIfc, attrNames1);
return v;
}//End CR1*/
  /**
   * ����ܸû�׼�߹���������㲿����ֵ����ļ��ϡ�
   * @param baselineBsoID :String��׼�ߵ�ֵ�����bsoID��
   * @return Vector ��ָ���Ļ�׼�߹���������㲿����ֵ����ļ��ϡ�
   * @throws QMException
   */
  public Vector getBaselineItems(String baselineBsoID)
      throws QMException
  {
    PartDebug.trace(this, "getBaselineItems() begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    BaselineIfc baselineIfc = (BaselineIfc)pService.refreshInfo(baselineBsoID);
    BaselineService bService = (BaselineService)EJBServiceHelper.getService("BaselineService");
    PartDebug.trace(this, "getBaselineItems() end....return is Vector");
    return(Vector)bService.getBaselineItems(baselineIfc);
  }

  /**
   * ����ָ����Դ������Ŀ�겿�������Ե�ɸѡ������������������ʹ�ù�ϵ�ṹ�Ĳ�ͬ��
   * 
   * @param sourcePartID :StringԴ������bsoID��
   * @param effectivityActive1 :String �Ƿ�����Ч�����ù淶 ��1,��0
   * @param baselineActive1 :String �Ƿ��ǻ�׼�����ù淶 ��1,��0
   * @param standardActive1 :String �Ƿ��Ǳ�׼���ù淶 ��1,��0
   * @param baselineID1 :String ��׼�ߵ�BsoID
   * @param configItemID1 :String ���ù淶��BsoID
   * @param viewObjectID1 :String ��ͼ�����bsoID
   * @param effectivityType1 :String ��Ч������
   * @param effectivityUnit1 :String ��Ч�Ե�λ ��Ҫ�־û�
   * @param state1 :String ״̬
   * @param workingIncluded1 :String �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true
   * @param objectPartID :StringĿ�겿����bsoID��
   * @param effectivityActive2 �Ƿ�����Ч�����ù淶 ��1,��0
   * @param baselineActive2 �Ƿ��ǻ�׼�����ù淶 ��1,��0
   * @param standardActive2 �Ƿ��Ǳ�׼���ù淶 ��1,��0
   * @param baselineID2 ��׼�ߵ�BsoID
   * @param configItemID2 ���ù淶��BsoID
   * @param viewObjectID2 ��ͼ�����bsoID
   * @param effectivityType2 ��Ч������
   * @param effectivityUnit2 ��Ч�Ե�λ ��Ҫ�־û�
   * @param state2 ״̬
   * @param workingIncluded2 �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true
   * @return vector Vector ����ֵVector��Ԫ�ص����ݽṹString[]��
   *<br> 0����ǰ���첿������ڸ������Ĳ�Σ����������Ϊ0�㣬���������Ӳ���Ϊ1�㣬�Դ����ƣ�
   *<br> 1����ǰ���첿��������+"("+��� + ")" ��
   *
   *<br> 2����ǰ���첿����Դ�����еİ汾+����ͼ�������Դ������û�иò����Ļ�����ʾ""��
   *    ���Դ�������иò��첿������û�з������ù淶�İ汾�Ļ���
   *    ��ʾ"û�з������ù淶�İ汾"��
   *<br> 3����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Դ������û�иò����Ļ�����ʾ""
   *    ���Դ�������иò��첿������û�з������ù淶�İ汾�Ļ����ճ���ʾ����+����λ����
   *
   *<br> 4����ǰ���첿����Ŀ�겿���еİ汾+����ͼ�������Ŀ�겿����û�иò����Ļ�����ʾ""��
   *    ���Ŀ�겿�����иò��첿������û�з������ù淶�İ汾�Ļ���
   *    ��ʾ"û�з������ù淶�İ汾"��
   *<br> 5����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Ŀ�겿����û�иò����Ļ�����ʾ""
   *    ���Ŀ�겿�����иò��첿������û�з������ù淶�İ汾�Ļ����ճ���ʾ����+����λ����
   *
   *<br> 6����ʾ����ɫ�ı�ʶ::������첿����Դ������Ŀ�겿���ж����ڣ����������߰汾��ͬ�Ļ�����ʾΪ��ɫ(black)��
   *    ������첿����Դ�����д��ڣ�������Ŀ�겿���в����ڵĻ�����ʾΪ��ɫ(green)��
   *    ������첿����Ŀ�겿���д��ڣ�������Դ�����в����ڵĻ�����ʾΪ��ɫ(purple)��
   *    ������첿����Դ��������Ŀ�겿���еİ汾����������ͬ�Ļ�����ʾΪ��ɫ(gray)��
   *    ��Ϣ"û�з������ù淶�İ汾"����ɫΪ��ɫ(red),���и���Ϣ���е���ɫΪ��ɫ(black)��
   *
   * <br>7:�����㲿����BsoID�������Դ�㲿�����Եģ�����Դ�㲿��ʹ�õĸò����㲿����BsoID��
   *
   * <br>8:�����㲿����BsoID, �����Ŀ���㲿�����Եģ�����Ŀ���㲿��ʹ�õĵĲ����㲿����BsoID��
   * ������ֻ����ҵ�汾�����ṩ��
   * @throws QMException
   */
  public Vector getBOMDifferences(String sourcePartID,
                                  String effectivityActive1,
                                  String baselineActive1,
                                  String standardActive1, String baselineID1,
                                  String configItemID1,
                                  String viewObjectID1,
                                  String effectivityType1,
                                  String effectivityUnit1, String state1,
                                  String workingIncluded1,
                                  String objectPartID,
                                  String effectivityActive2,
                                  String baselineActive2,
                                  String standardActive2, String baselineID2,
                                  String configItemID2,
                                  String viewObjectID2,
                                  String effectivityType2,
                                  String effectivityUnit2, String state2,
                                  String workingIncluded2)
      throws QMException
  {
    PartDebug.trace(this, "getBOMDifferences() begin ....");
    String isHasConfigItem = null;
    PartConfigSpecIfc sourceConfigSpecIfc = PartServiceRequest.hashtableToPartConfigSpecIfc(
        effectivityActive1, baselineActive1, standardActive1, baselineID1,
        configItemID1, viewObjectID1, effectivityType1, effectivityUnit1, state1, workingIncluded1);
    PartConfigSpecIfc objectConfigSpecIfc = PartServiceRequest.hashtableToPartConfigSpecIfc(
        effectivityActive2, baselineActive2, standardActive2, baselineID2,
        configItemID2, viewObjectID2, effectivityType2, effectivityUnit2, state2, workingIncluded2);
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    QMPartIfc sourcePartIfc = (QMPartIfc)pService.refreshInfo(sourcePartID);
    QMPartIfc objectPartIfc = (QMPartIfc)pService.refreshInfo(objectPartID);
    PartDebug.trace(this, "getBOMDifferences() end....return is Vector");
    HashMap map =new HashMap();
    map.put("dangqian",new Vector());
    map.put("mubiao",new Vector());
    return PartServiceRequest.getBOMDifferences(sourcePartIfc, sourceConfigSpecIfc, objectPartIfc, objectConfigSpecIfc,map);
  }

  /**
   * ����ָ����Դ������Ŀ�겿�������Ե�ɸѡ������������������ʹ�ù�ϵ�ṹ�Ĳ�ͬ��
   * 
   * @param sourcePartID :StringԴ������bsoID��
   * @param objectPartID :StringĿ�겿����bsoID��
   * @param effectivityActive2 �Ƿ�����Ч�����ù淶 ��1,��0
   * @param baselineActive2 �Ƿ��ǻ�׼�����ù淶 ��1,��0
   * @param standardActive2 �Ƿ��Ǳ�׼���ù淶 ��1,��0
   * @param baselineID2 ��׼�ߵ�BsoID
   * @param configItemID2 ���ù淶��BsoID
   * @param viewObjectID2 ��ͼ�����bsoID
   * @param effectivityType2 ��Ч������
   * @param effectivityUnit2 ��Ч�Ե�λ ��Ҫ�־û�
   * @param state2 ״̬
   * @param workingIncluded2 �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true
   * @return vector Vector ����ֵVector��Ԫ�ص����ݽṹString[]��
   * <br>0����ǰ���첿������ڸ������Ĳ�Σ����������Ϊ0�㣬���������Ӳ���Ϊ1�㣬�Դ����ƣ�
   * <br>1����ǰ���첿��������+"("+��� + ")" ��
   *
   * <br>2����ǰ���첿����Դ�����еİ汾+����ͼ�������Դ������û�иò����Ļ�����ʾ""��
   *    ���Դ�������иò��첿������û�з������ù淶�İ汾�Ļ���
   *    ��ʾ"û�з������ù淶�İ汾"��
   * <br>3����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Դ������û�иò����Ļ�����ʾ""
   *    ���Դ�������иò��첿������û�з������ù淶�İ汾�Ļ����ճ���ʾ����+����λ����
   *
   * <br>4����ǰ���첿����Ŀ�겿���еİ汾+����ͼ�������Ŀ�겿����û�иò����Ļ�����ʾ""��
   *    ���Ŀ�겿�����иò��첿������û�з������ù淶�İ汾�Ļ���
   *    ��ʾ"û�з������ù淶�İ汾"��
   * <br>5����ǰ���첿��������һ���㲿����ʹ�õ�����+(��λ)�����Ŀ�겿����û�иò����Ļ�����ʾ""
   *    ���Ŀ�겿�����иò��첿������û�з������ù淶�İ汾�Ļ����ճ���ʾ����+����λ����
   *
   * <br>6����ʾ����ɫ�ı�ʶ::������첿����Դ������Ŀ�겿���ж����ڣ����������߰汾��ͬ�Ļ�����ʾΪ��ɫ(black)��
   *    ������첿����Դ�����д��ڣ�������Ŀ�겿���в����ڵĻ�����ʾΪ��ɫ(green)��
   *    ������첿����Ŀ�겿���д��ڣ�������Դ�����в����ڵĻ�����ʾΪ��ɫ(purple)��
   *    ������첿����Դ��������Ŀ�겿���еİ汾����������ͬ�Ļ�����ʾΪ��ɫ(gray)��
   *    ��Ϣ"û�з������ù淶�İ汾"����ɫΪ��ɫ(red),���и���Ϣ���е���ɫΪ��ɫ(black)��
   *
   * <br>7:�����㲿����BsoID�������Դ�㲿�����Եģ�����Դ�㲿��ʹ�õĸò����㲿����BsoID��
   *
   * <br>8:�����㲿����BsoID, �����Ŀ���㲿�����Եģ�����Ŀ���㲿��ʹ�õĵĲ����㲿����BsoID��
   * ������ֻ����ҵ�汾�����ṩ��
   * @throws QMException
   */
  public Vector getBOMDifferences(String sourcePartID,
                                  String objectPartID,
                                  String effectivityActive2,
                                  String baselineActive2,
                                  String standardActive2, String baselineID2,
                                  String configItemID2,
                                  String viewObjectID2,
                                  String effectivityType2,
                                  String effectivityUnit2, String state2,
                                  String workingIncluded2,
                                  String sourcePartNames,
                                  String objectPartNames
                                  )
      throws QMException
  {
    PartDebug.trace(this, "getBOMDifferences() begin ....");
     PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    String isHasConfigItem = null;
    HashMap map = new HashMap();
    String s;
    if(sourcePartNames!=null&&sourcePartNames.trim().length()!=0)
    {
      Vector dangq=new Vector();
      StringTokenizer names=new StringTokenizer(sourcePartNames,";");
      while(names.hasMoreTokens())
      {
        String s1=null;
        String s2=null;
        String s3="";
        s=names.nextToken();
        StringTokenizer ss=new StringTokenizer(s,"*");
        if(ss.hasMoreTokens())
          s1=ss.nextToken();
          StringTokenizer a=new StringTokenizer(s1,"$");
          if(a.hasMoreTokens())
          {

            if(a.hasMoreTokens())
            {
              s3=s1;
            }
          }
          if(ss.hasMoreTokens())
            s2=ss.nextToken();
        if(s2!=null&&s3.length()>0)
        {
           QMPartIfc partifc = (QMPartIfc)pService.refreshInfo(s2);
           if(partifc!=null)
           {

             partifc.setCompare(s3);
            dangq.addElement(partifc);
           }
        }

      }

      if(objectPartNames!=null&&objectPartNames.trim().length()!=0)
    {
      Vector mub=new Vector();
      StringTokenizer namess=new StringTokenizer(objectPartNames,";");
      while(namess.hasMoreTokens())
      {
        String s1=null;
        String s2=null;
        String s3="";
        s=namess.nextToken();
        StringTokenizer ss=new StringTokenizer(s,"*");
        if(ss.hasMoreTokens())
          s1=ss.nextToken();
        StringTokenizer a=new StringTokenizer(s1,"$");
       if(a.hasMoreTokens())
       {

         if(a.hasMoreTokens())
         {
           s3=s1;
         }
       }

          if(ss.hasMoreTokens())
            s2=ss.nextToken();
        if(s2!=null)
        {
           QMPartIfc partifc = (QMPartIfc)pService.refreshInfo(s2);
           if(partifc!=null&&s3.length()>0)
           {
             partifc.setCompare(s1);
            mub.addElement(partifc);
           }
        }

      }
      map.put("dangqian",dangq);
      map.put("mubiao",mub);
    }
    else
    {
      map.put("dangqian",new Vector()) ;
      map.put("mubiao", new Vector());
    }

    }
    else
    {
      map.put("dangqian", new Vector());
      map.put("mubiao", new Vector());
    }

    PartConfigSpecIfc sourceConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
    //CR3 Begin 20090619 zhangq �޸�ԭ��TD-2190��
    PartConfigSpecIfc objectConfigSpecIfc = PartServiceRequest.hashtableToPartConfigSpecIfc(
        "0",effectivityActive2, baselineActive2, standardActive2, baselineID2,
        configItemID2, viewObjectID2, effectivityType2, effectivityUnit2, state2, workingIncluded2);
    //CR3 End 20090619 zhangq

    QMPartIfc sourcePartIfc = (QMPartIfc)pService.refreshInfo(sourcePartID);
    QMPartIfc objectPartIfc = (QMPartIfc)pService.refreshInfo(objectPartID);
    PartDebug.trace(this, "getBOMDifferences() end....return is Vector");
    return PartServiceRequest.getBOMDifferences(sourcePartIfc, sourceConfigSpecIfc, objectPartIfc, objectConfigSpecIfc,map);
  }
  /**
   * ��ȡ�ṹ�Ƚ����ս����������һ������.
 
   * @param sourcePartID :StringԴ������bsoID��
   * @param objectPartID :StringĿ�겿����bsoID��
   * @param effectivityActive2 �Ƿ�����Ч�����ù淶 ��1,��0
   * @param baselineActive2 �Ƿ��ǻ�׼�����ù淶 ��1,��0
   * @param standardActive2 �Ƿ��Ǳ�׼���ù淶 ��1,��0
   * @param baselineID2 ��׼�ߵ�BsoID
   * @param configItemID2 ���ù淶��BsoID
   * @param viewObjectID2 ��ͼ�����bsoID
   * @param effectivityType2 ��Ч������
   * @param effectivityUnit2 ��Ч�Ե�λ ��Ҫ�־û�
   * @param state2 ״̬
   * @param workingIncluded2 �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true
   * @param sourcePartNames Դ��������
   * @param objectPartNames Ŀ�������
   * @return ����ֵVector��Ԫ�ص����ݽṹString[]��
   * <br>0����ǰ���첿������ڸ������Ĳ�Σ����������Ϊ0�㣬���������Ӳ���Ϊ1�㣬�Դ����ƣ�
   * <br>1����ǰ���첿���ı�ţ�
   *
   * <br>2����ǰ���첿�������ƣ�
   * <br>3����ǰ���첿���İ汾��
   * <br>4����ǰ���첿����������
   *<br> 5����ǰ���첿���ı仯���¼ӡ��������滻�����滻��
   *<br> 6��˵��
   * @throws QMException
   */
  public Vector getUltimateReport(String sourcePartID,
                                    String objectPartID,
                                    String effectivityActive2,
                                    String baselineActive2,
                                    String standardActive2, String baselineID2,
                                    String configItemID2,
                                    String viewObjectID2,
                                    String effectivityType2,
                                    String effectivityUnit2, String state2,
                                    String workingIncluded2,
                                    String sourcePartNames,
                                    String objectPartNames
)
    throws QMException
    {
      Vector result=new Vector();
      Vector bom=getBOMDifferences(sourcePartID,objectPartID,effectivityActive2,baselineActive2,standardActive2,baselineID2,configItemID2,viewObjectID2
                                   ,effectivityType2,effectivityUnit2,state2,workingIncluded2,sourcePartNames,objectPartNames);
      Iterator iterator=bom.iterator();
      for(;iterator.hasNext();)
      {
        Object[] obj=(Object[])iterator.next();
        if (obj[6].equals("green"))
        {
          Object[] obj1 = new Object[8];
          obj1[0] = obj[0];
          obj1[1] = obj[11];
          obj1[2] = obj[12];
          obj1[3] = obj[14];
          obj1[4] = obj[13];
          obj1[5] = obj[15];
          obj1[6] = obj[16];
        //CCBegin by leixiao 2010-2-24������������״̬����ʾ
          obj1[7] = obj[17];
        //CCEnd by leixiao 2010-2-24������������״̬����ʾ
          result.add(obj1);

        }
          else
        if (obj[6].equals("purple"))
        {
          Object[] obj1 = new Object[8];
          obj1[0] = obj[0];

          obj1[1] = obj[11];
          obj1[2] = obj[12];
          obj1[3] = obj[14];
          obj1[4] = obj[13];
          obj1[5] = obj[15];
          obj1[6] = obj[16];
        //CCBegin by leixiao 2010-2-24������������״̬����ʾ
          obj1[7] = obj[17];
        //CCEnd by leixiao 2010-2-24������������״̬����ʾ
          if(!obj1[5].toString().equals("tihuan"))
          result.add(obj1);

        }
          else
        if (obj[6].equals("black"))
        {
          Object[] obj1=new Object[8];
          obj1[0]=obj[0];

          obj1[1]=obj[11];
          obj1[2]=obj[12];
          obj1[3]=obj[14];
           obj1[4]=obj[13];
           boolean flag=obj[14].equals(obj[15]);
           if(flag)
           {
             obj1[5] = "������ͬ";
               obj1[6] = "������" + obj[16];
           }
           else
           {
             obj1[5] = "�汾��ͬ";
             obj1[6] =  "�汾��" + obj[15] ;
           }
           //CCBegin by leixiao 2010-2-24������������״̬����ʾ
           obj1[7] = obj[17];
         //CCEnd by leixiao 2010-2-24������������״̬����ʾ
           result.add(obj1);
        }
          else

           if (obj[6].equals("gray")) {
             boolean oc=false;
             String sourceNum = (String) obj[16];
             String objectNum = (String) obj[13];
             Object[] obj1 = new Object[8];
             obj1[0] = obj[0];
             String num = (String) obj[11];
             String nume="";
             String num1="";
             int i = num.indexOf("*");
             if (i != -1) {
              oc=true;
               nume = num.substring(0, i);
               num1=num.substring(i+1);
               obj1[1] = nume;
             }
             else
             obj1[1] = obj[11];
           String name = (String) obj[12];
           int j= name.indexOf("*");
           if (j != -1) {

             name = name.substring(0, j);
             obj1[2] = name;
             oc=true;
           }
           else
             obj1[2] = obj[12];
             obj1[3] = obj[14];
             obj1[4] = obj[13];
             if (sourceNum.equals(objectNum)) {
               if(oc)
               {
                 obj1[5] = "�滻";
               obj1[6] = "����:"+nume+"ȡ����"+num1;
               }
               else
               {
                 obj1[5] = "";
                 obj1[6] = "";
               }
             }
             else {
               obj1[5] = "������ͬ";
               obj1[6] = "������" + obj[16];
             }
             //CCBegin by leixiao 2010-2-24������������״̬����ʾ
             //CCBegin SS2
             if(obj1.length==18)
             obj1[7] = obj[17];
             else
             obj1[7] = "";
             //CCEnd SS2
           //CCEnd by leixiao 2010-2-24������������״̬����ʾ
             result.add(obj1);

           }
      }

      return result;

    }
  /**
   * ����ָ���㲿����ָ����ɸѡ��������㲿���ڸ�ɸѡ�����±���Щ������ʹ�á�ʹ�ý��
   * �����ڷ���ֵvector�С�
   *
   * @param partID ָ���㲿����bsoID
   * @param effectivityActive �Ƿ�����Ч�����ù淶 ��1,��0
   * @param baselineActive �Ƿ��ǻ�׼�����ù淶 ��1,��0
   * @param standardActive �Ƿ��Ǳ�׼���ù淶 ��1,��0
   * @param baselineID ��׼�ߵ�BsoID
   * @param configItemID ���ù淶��BsoID
   * @param viewObjectID ��ͼ�����bsoID
   * @param effectivityType ��Ч������
   * @param effectivityUnit ��Ч�Ե�λ ��Ҫ�־û�
   * @param state ״̬
   * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true
   * @return Vector  vector����ֵ�����ݽṹΪ��vector�е�ÿ��Ԫ�ض���Vector���͵ģ�����Ϊ���������㣬����ΪsubVector.
   * ��subVector��ÿ��Ԫ�ض���String[]���͵�:
   * <br>String[0]:��κţ�
   * <br>String[1]:�㲿�����(�㲿������)�汾(��ͼ);
   * <br>String[2]�㲿���ڴ˽ṹ��(��������)ʹ�õ�������������ͬһ�ṹ�µļ�¼ʹ����������ͬ�ġ�
   * @exception QMException
   */
  public Vector getUsageList(String partID, String effectivityActive,
                             String baselineActive, String standardActive,
                             String baselineID, String configItemID,
                             String viewObjectID, String effectivityType,
                             String effectivityUnit, String state,
                             String workingIncluded)
      throws QMException
  {
    PartDebug.trace(this, "getUsageList() begin ....");
    String isHasConfigItem = null;
    PartConfigSpecIfc partConfigSpecIfc = PartServiceRequest.hashtableToPartConfigSpecIfc(
        effectivityActive, baselineActive, standardActive, baselineID,
        configItemID, viewObjectID, effectivityType, effectivityUnit, state, workingIncluded);
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    PartDebug.trace(this, "getUsageList() end....return is Vector");
    return PartServiceRequest.setUsageList(partIfc, partConfigSpecIfc);
  }

  /**
   * ����ָ���㲿����ָ����ɸѡ��������㲿���ڸ�ɸѡ�����±���Щ������ʹ�á�
   * ʹ�ý�������ڷ���ֵvector�С�
   * 
   * @param partID ָ���㲿����bsoID
   * @return Vector vector����ֵ�����ݽṹΪ��vector�е�ÿ��Ԫ�ض���Vector���͵ģ�����Ϊ���������㣬����ΪsubVector.
   * ��subVector��ÿ��Ԫ�ض���String[]���͵�:
   * <br>String[0]:��κţ�
   * <br>String[1]:�㲿�����(�㲿������)�汾(��ͼ);
   *<br> String[2]�㲿���ڴ˽ṹ��(��������)ʹ�õ�������������ͬһ�ṹ�µļ�¼ʹ����������ͬ�ġ�
   * @exception QMException
   */
  public Vector getUsageList(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getUsageList() begin ....");
    PartConfigSpecIfc partConfigSpecIfc = PartServiceRequest.findPartConfigSpecIfc();
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    PartDebug.trace(this, "getUsageList() end....return is Vector");
    //return PartServiceRequest.setUsageList(partIfc, partConfigSpecIfc);
    long t111 = System.currentTimeMillis();
    Vector vec = (Vector)PartServiceRequest.setUsageList(partIfc, partConfigSpecIfc);
    long t222 = System.currentTimeMillis();
    System.out.println("�����ڲ�Ʒ ��ʱ�� "+(t222-t111)/1000+" ��");
    return vec;
  }

  /**
   * ����ָ�����㲿����bsoID��ɸѡ��������㲿���Ĳ�Ʒ�ṹ��
   * @param partID :��ǰ�㲿����bsoID
   * @param effectivityActive �Ƿ�����Ч�����ù淶 ��1,��0
   * @param baselineActive �Ƿ��ǻ�׼�����ù淶 ��1,��0
   * @param standardActive �Ƿ��Ǳ�׼���ù淶 ��1,��0
   * @param baselineID ��׼�ߵ�BsoID
   * @param configItemID ���ù淶��BsoID
   * @param viewObjectID ��ͼ�����bsoID
   * @param effectivityType ��Ч������
   * @param effectivityUnit ��Ч�Ե�λ
   * @param state ״̬
   * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true
   * @return Vector ����Vector:
   * <br>�ؼ��֣�
   * ����+����+�汾+��ͼ+����+��λ
   *<br> ֵ��������һ���Ӽ���Ϣ�Ĺ������ؼ��ֺ�ֵ��ͬ�����ֵΪ�գ����㲿����Ϊ���νṹ
   * ��Ҷ�ڵ㡣
   * @throws QMException
   */
  //����һά������
  public Vector getProductStructure(String partID, String effectivityActive,
                                    String baselineActive,
                                    String standardActive, String baselineID,
                                    String configItemID,
                                    String viewObjectID,
                                    String effectivityType,
                                    String effectivityUnit, String state,
                                    String workingIncluded)
      throws QMException
  {
    PartDebug.trace(this, "getProductStructure() begin ....");
    //������PartConfigSpecIfc,�ٵ���StandardPartServiceEJB�е�ͬ������
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    String isHasConfigItem = "1";
    PartConfigSpecIfc partConfigSpecIfc = PartServiceRequest.hashtableToPartConfigSpecIfc(
        effectivityActive, baselineActive, standardActive, baselineID,
        configItemID, viewObjectID, effectivityType, effectivityUnit, state, workingIncluded);
    PartDebug.trace(this, "getProductStructure() end....return is Vector");
    return PartServiceRequest.getProductStructure(partIfc, partConfigSpecIfc);
  }

  //add by ������
  /**
   * ����ָ�����㲿����bsoID,����㲿���Ĳ�Ʒ�ṹ��
   * @param partID String��ǰ�㲿����bsoID
   * @return Vector һά����
   * @throws QMException
   */
  public Vector getProductStructure(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getProductStructure() begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
    PartDebug.trace(this, "getProductStructure() end....return is Vector");
    return PartServiceRequest.getSubProductStructure(partIfc, partConfigSpecIfc);
  }

  //end add
  /**
   * ������ù淶��������Ϣ��
   * 
   * @throws QMException
   * @return Object[] obj[0] �Ƿ��Ǳ�׼���ù淶 ��1
   * <br>obj[1] viewObject ��ͼ����
   * <br>obj[2] ��������״̬
   * <br>obj[3] �Ƿ��ǻ�׼�����ù淶 ��1
   * <br>obj[4] ��׼�߶���
   * <br>obj[5] �Ƿ�����Ч�����ù淶 ��1
   * <br>obj[6] ��Ч��������
   * <br>obj[7] ��Ч��ֵ
   * <br>obj[8] ��Ч������(����)
   * <br>obj[9] ��Ч������(Ӣ��)
   */
  public Object[] getConfigType()
      throws QMException
  {
    Object[] obj = new Object[11];
    PartConfigSpecInfo configInfo = (PartConfigSpecInfo)PartServiceRequest.findPartConfigSpecIfc();
    if(configInfo != null)
    {
      //���Ϊtrue��ɸѡ����Ϊ����Ч�ԡ�
      boolean flag1 = configInfo.getEffectivityActive();
      //���Ϊtrue��ɸѡ����Ϊ����׼�ߡ�
      boolean flag2 = configInfo.getBaselineActive();
      //���Ϊtrue��ɸѡ����Ϊ����׼��
      boolean flag3 = configInfo.getStandardActive();
      //����Ϊ����Ч�ԡ�ɸѡ����ʱ
      if(flag1)
      {
        obj[5] = "1";
        PartEffectivityConfigSpec effectivity = configInfo.getEffectivity();
        ViewObjectIfc viewObject = effectivity.getViewObjectIfc();
        QMConfigurationItemIfc configItem = effectivity.getEffectiveConfigItemIfc();
        String effValue = effectivity.getEffectiveUnit();
        obj[1] = viewObject;
        obj[6] = configItem;
        obj[7] = effValue;
        EffectivityType effType = effectivity.getEffectivityType();
        //ת��������
        //2003/12/13
        //String chineseEffType =effType.getDisplay();
        String chineseEffType = effType.getDisplay(RemoteProperty.getVersionLocale());
        obj[8] = chineseEffType;
        obj[9] = effType;
      }
      //����Ϊ����׼�ߡ�ɸѡ����ʱ
      if(flag2)
      {
        obj[3] = "1";
        BaselineIfc baseline = configInfo.getBaseline().getBaselineIfc();
        obj[4] = baseline;
      }
      //����Ϊ����׼��ɸѡ����ʱ
      if(flag3)
      {
        obj[0] = "1";
        String state = "";
        PartStandardConfigSpec standardConfigSpec = configInfo.getStandard();
        ViewObjectIfc viewObject = standardConfigSpec.getViewObjectIfc();
        LifeCycleState temp = standardConfigSpec.getLifeCycleState();
        obj[1] = viewObject;
        if(temp != null)
        {
          state = temp.getDisplay();
        }
        obj[2] = state;
        if(standardConfigSpec.getWorkingIncluded())
        {
        	obj[10]="yes";
        }
        else
        {
        	obj[10]="no";
        }
      }
    }
    return obj;
  }

  /**
   * ͨ�����ƺͺ���������㲿��������ģ����ѯ�����nameΪnull���������ѯ�����number
   * Ϊnull�������Ʋ�ѯ�����name��numnber��Ϊnull������������㲿����ֵ����
   * @param name :String ģ����ѯ���㲿�����ƣ�
   * @param number :Stringģ����ѯ���㲿���ĺ��룻
   * @return collection:Collection���ϲ�ѯ���������㲿����ֵ����ļ��ϡ�
   * @throws QMException
   */
  public Collection getAllPartMasters(String name, String number)
      throws QMException
  {
    PartDebug.trace(this, "getAllPartMasters() begin ....");
    PartDebug.trace(this, "getAllPartMasters()" + " end....return is Collection");
    return PartServiceRequest.getAllPartMasters(name, number);
  }

  /**
   * ͨ�����ƺͺ���������㲿��������ģ����ѯ�����nameΪnull���������ѯ�����number
   * Ϊnull�������Ʋ�ѯ�����name��numnber��Ϊnull������������㲿����ֵ����
   * �������nameFlagΪtrue, �����㲿�����ƺ�name����ͬ����Щ�㲿�������򣬲����㲿��
   * ���ƺ�name��ͬ���㲿�����Բ���numFlag��ͬ���Ĵ���
   * @param name ����ѯ���㲿������
   * @param nameFlag �����������ַ���boolean����
   * @param number ����ѯ���㲿�����
   * @param numFlag �����������ַ���boolean����
   * @return Collection ��ѯ���������㲿��(QMPartMasterIfc)�ļ���
   * @throws QMException
   */
  public Collection getAllPartMasters(String name, String nameFlag,
                                      String number,
                                      String numFlag)
      throws QMException
  {
    PartDebug.trace(this, "getAllPartMasters" + "(name, nameFlag, number, numFlag) begin ....");
    //��Ҫ���Ȱ�nameFlag, ��numFlagת��Ϊboolean���͡�
    boolean nameFlag1, numFlag1;
    if((nameFlag.trim()).equals("true"))
    {
      nameFlag1 = true;
    }
    else
    {
      nameFlag1 = false;
    }
    if((numFlag.trim()).equals("true"))
    {
      numFlag1 = true;
    }
    else
    {
      numFlag1 = false;
    }
    PartDebug.trace(this, "getAllPartMasters" + "(name, nameFlag, number, numFlag) end....return is Collection");
    return PartServiceRequest.getAllPartMasters(name, nameFlag1, number, numFlag1);
  }

  //���!!!!

  /**
   * ���ָ���㲿�������и�����������Ϣֵ���󼯺ϡ����ڲ鿴�����ڲ���WEBҳ�档����
   * partID����㲿����ֵ�������÷����е�ͬ��������
   * @param partID ��Stringָ�����㲿����bsoID��
   * @return Vectorָ���㲿�������и�������ֱ����������������Ϣֵ����ļ��ϡ�
   * @throws QMException
   */
  public Vector getAllParentParts(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getAllParentParts() begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    PartDebug.trace(this, "getAllParentParts() end....return is Vector");
    return PartServiceRequest.getAllParentParts(partIfc);
  }

  //���!!!!

  /**
   * ͨ��bsoID��ö����ֵ����
   * @param bsoID �㲿��BsoID
   * @return QMPartIfc �㲿��ֵ����
   * @see QMPartInfo
   * @throws QMException
   * 
   */
  public QMPartIfc getPartFromID(String bsoID)
      throws QMException
  {
    PartDebug.trace(this, "getPartFromID(String) begin ....");
    try
    {
      PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
      PartDebug.trace(this, "getPartFromID" + "(String) end....return is QMPartIfc");
      return(QMPartIfc)pService.refreshInfo(bsoID);
    }
    catch(QMException ex)
    {
      ex.printStackTrace();
      throw ex;
    }
  }
  //���!!

  /**
   * ͨ��bsoID��ö����ֵ����
   * @param bsoID �㲿������ϢBsoID��
   * @return QMPartMasterIfc �㲿������Ϣֵ����
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public QMPartMasterIfc getPartMasterFromID(String bsoID)
      throws QMException
  {
    PartDebug.trace(this, "getPartMasterFromID(String) begin ....");
    try
    {
      PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
      PartDebug.trace(this, "getPartMaster" + "FromID(String) end....return is QMPartMasterIfc");
      return(QMPartMasterIfc)pService.refreshInfo(bsoID);
    }
    catch(QMException ex)
    {
      ex.printStackTrace();
      throw ex;
    }
  }

  /**
   * ͨ��bsoID��ö����ֵ����
   * @param bsoID ��׼��BsoID��
   * @return ManagedBaselineIfc ��׼��ֵ����
   * @see ManagedBaselineInfo
   * @throws QMException
   */
  public ManagedBaselineIfc getBaselineFromID(String bsoID)
      throws QMException
  {
    PartDebug.trace(this, "getBaselineFromID(String) begin ....");
    try
    {
      PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
      PartDebug.trace(this, "getBaselineFrom" + "ID(String) end....return is ManagedBaselineIfc");
      return(ManagedBaselineIfc)pService.refreshInfo(bsoID);
    }
    catch(QMException ex)
    {
      ex.printStackTrace();
      throw ex;
    }
  }

  /**
   * ͨ��bsoID��ö����ֵ����
   * @param bsoID �־û������BsoID��
   * @return BaseValueIfc �־û�����ֵ����
   * @see BaseValueInfo
   * @throws QMException
   */
  public BaseValueIfc getObjectForID(String bsoID)
      throws QMException
  {
    PartDebug.trace(this, "getObjectForID(String) begin ....");
    if(bsoID == null || bsoID.length() == 0)
    {
      return null;
    }
    try
    {
      PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
      PartDebug.trace(this, "getObjectForID" + "(String) end....return is BaseValueIfc");
      return pService.refreshInfo(bsoID);
    }
    catch(QMException ex)
    {
      ex.printStackTrace();
      throw ex;
    }
  }


  /**
   * ͨ��bsoID��÷��ϵ�ǰ���ù淶���㲿��������С�汾��
   * @param bsoID �־û������BsoID��
   * @return QMPartIfc ��ǰ���ù淶�и��㲿��������С�汾��
   * @see QMPartInfo
   * @throws QMException
   */
  public QMPartIfc getPartByConfigSpec(String bsoID)
      throws QMException
  {
    PartDebug.trace(this, "getObjectForID(String) begin ....");
    if(bsoID == null || bsoID.length() == 0)
    {
      return null;
    }
    try
    {
      PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
      PartDebug.trace(this, "getObjectForID" + "(String) end....return is BaseValueIfc");
      QMPartIfc a = (QMPartIfc)pService.refreshInfo(bsoID);
      QMPartMasterIfc aa = (QMPartMasterIfc)a.getMaster();
      PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
      
      return PartServiceRequest.getPartByConfigSpec(aa , partConfigSpecIfc);
    }
    catch(QMException ex)
    {
      ex.printStackTrace();
      throw ex;
    }
  }


  /**
   *
   * @param solutionID String QMPartMasterIfc��BsoID��
   * @param addValueRange String �����ӵ���Ч��ֵ��Χ��
   * @throws QMException
   * @return Vector EffGroup����ļ��ϡ�
   */
  public Vector jj(String solutionID, String addValueRange)
      throws QMException
  {
    String configItemID = "";
    PartDebug.trace(this, "jj() begin ....");
    PartConfigSpecIfc configSpecIfc = PartServiceRequest.findPartConfigSpecIfc();
    if(configSpecIfc.getEffectivityActive() == true)
    {
      PartEffectivityConfigSpec effectivityConfigSpec = configSpecIfc.getEffectivity();
      configItemID = effectivityConfigSpec.getEffectiveConfigItemIfc().getBsoID();
    }
    Vector jl = getEffGroups(solutionID, configSpecIfc, configItemID, addValueRange);
    PartDebug.trace(this, "jj() end....return is Vector");
    return jl;
  }

  /**
   * ����ڵ�ǰɸѡ������partMasterIfc�Ľṹ�������Ӽ�����Ч�Է�Χ���������EffGroup
   * �ļ��ϡ��÷���������ʾ���ṹ�����Ч�Խ����
   * @param partMasterID :�㲿������Ϣҵ��ҵ������bsoID��
   * @param configSpecIfc :PartConfigSpecIfc ���ù淶ֵ����
   * @param configItemID ��Ч������������bsoID��
   * @param value_range ֵ��Χ��
   * @return Vector ��Ч�Է�Χ���������EffGroup�ļ���
   * @see PartConfigSpecInfo
   * @throws QMException
   */
  public Vector getEffGroups(String partMasterID, PartConfigSpecIfc configSpecIfc,
                             String configItemID, String value_range)
      throws QMException
  {
    PartDebug.trace(this, "getEffGroups() begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(partMasterID);
    PartDebug.trace(this, "getEffGroups() end....return is Vector");
    return PartServiceRequest.getEffGroups(partMasterIfc, configSpecIfc, configItemID, value_range);
  }
  //���!!!!

  /**
   * �ж�partIfcVector�е�����Ԫ��(����QMPartIfc����):�Ĳο��ĵ��Ĳ��������
   * 
   * @param partIfcVector ��ͬһ��QMPartMaster����Ĳ�ͬbranchID������С�汾���㲿��ֵ���󼯺�
   * @return Vector ����ֵVector����String[]�ļ���:
   * <br>String[0]ΪpartIfcVector��Ԫ��(QMPartIfc)�����Ĳο��ĵ�������ĵ����;
   * <br>String[1]��String[partIfcVector.size()]�ǲο��ĵ�������(partIfcVector)�еĶ���(QMPartIfc)�еĴ��������
   * ����Ϊ"true",������Ϊ"false"��
   * @throws QMException
   */
  public Vector getDeReferenceByDoc(Vector partIfcVector)
      throws QMException
  {
    PartDebug.trace(this, "getDeReferenceByDoc() begin ....");
    // ����getDocMastersByPartIfc������partIfc������partIfc���������вο��ĵ�(DocMasterIfc)�ļ���
    int n = partIfcVector.size();
    String[] tempString = new String[n + 1];
    Vector mainVector = new Vector();
    Collection tempResult = null;
    tempResult = getDocMastersByPartIfc((QMPartIfc)partIfcVector.elementAt(0));
    if(tempResult != null && tempResult.size() > 0)
    {
      Iterator iterator = tempResult.iterator();
      while(iterator.hasNext())
      {
        tempString = new String[n + 1];
        tempString[0] = ((DocMasterIfc)iterator.next()).getDocNum();
        tempString[1] = "true";
        for(int i = 2; i < tempString.length; i++)
        {
          tempString[i] = "";
        }
        mainVector.addElement(tempString);
      }//end while(iterator.hasNext())
    }//end if(tempResult != null && tempResult.size() > 0)
    //����ʵ�ֶ�partIfcVector�еĴ�1��n-1��Ԫ�صĴ���
    String tempDocMasterNum = "";
    for(int j = 1; j < n; j++)
    {
      tempResult = new Vector();
      tempResult = getDocMastersByPartIfc((QMPartIfc)partIfcVector.elementAt(j));
      if(tempResult != null && tempResult.size() > 0)
      {
        DocMasterIfc[] tempDocMasterArray = new DocMasterIfc[tempResult.size()];
        Iterator iterator1 = tempResult.iterator();
        for(int mm = 0; mm < tempResult.size(); mm++)
        {
          tempDocMasterArray[mm] = (DocMasterIfc)iterator1.next();
        }
        //DocMasterIfc[] tempDocMasterArray = (DocMasterIfc[])tempResult.toArray();
        for(int k = 0; k < tempDocMasterArray.length; k++)
        {
          tempDocMasterNum = ((DocMasterIfc)tempDocMasterArray[k]).getDocNum();
          boolean flag = false;
          for(int m = 0; m < mainVector.size(); m++)
          {
            String[] vectorString = (String[])mainVector.elementAt(m);
            //���tempDocMasterNum��vectorString[0]��ͬ�Ļ����ϲ�������mainVector�еĵ�
            //m��Ԫ�ص�ֵString[j+1]�޸ģ�String[j+1] = "true"��flag = true.�޸���Ϻ���������ѭ����
            if(tempDocMasterNum.equals(vectorString[0]))
            {
              vectorString[j + 1] = "true";
              mainVector.setElementAt(vectorString, m);
              flag = true;
              break;
            }//end if(tempDocMasterNum == vectorString[0])
            //���tempDocMasterNum��vectorString[0]����ͬ�Ļ������������ѭ��������
            //����for(int m=0; m<mainVector.size(); m++)
          }//end for(int m=0; m<mainVector.size(); m++)
          //���ѭ��for(int m=0; m<mainVector.size(); m++)��ϣ���mainVector��û���ҵ���
          //�� tempDocMasterNum ��ͬ��Ԫ�صĻ����Ͱѱ�tempDocMasterNum�ӵ�mainVector�С�
          //tempDocMasterNum , false, ..., true(��Ӧ��j��part�İ汾), false,...false.
          if(flag == false)
          {
            tempString = new String[n + 1];
            tempString[0] = tempDocMasterNum;
            for(int i1 = 1; i1 < tempString.length; i1++)
            {
              tempString[i1] = "";
            }
            tempString[j + 1] = "true";
            mainVector.addElement(tempString);
          }//end if(flag == flase)
        }//end for(int k=0; k<tempDocMasterArray.length; k++)
      }//end if(tempResult != null && tempResult.size() > 0)
    }//end for(int j=1; j<n; j++)
    //��Ϊ�鵽����DocMasterIfc�ļ��ϣ����ԣ����ж�����DocMaster�Ƿ���ͬ��ʱ��ֻ��Ҫ
    //�ж���docNumber�Ƿ���ͬ.
    //���ķ���ֵ��Vector:vector vector�е�ÿ��Ԫ��Ӧ����:
    //docMasterNumber, boolean ,boolean ,... , boolean (n��String��boolean)
    //�����Ҫ�ж�mainVector���Ƿ���������Ԫ��:String[1]...String[n]����"true"
    //��Ҫɾ��������Ԫ��!!!!
    Vector resultVector = new Vector();
    for(int i = 0; i < mainVector.size(); i++)
    {
      int account = 0;
      tempString = new String[n + 1];
      tempString = (String[])mainVector.elementAt(i);
      for(int j = 1; j < tempString.length; j++)
      {
        if(tempString[j] == "true")
        {
          account++;
        }
      }
      //����
      if(account < (tempString.length - 1))
      {
        resultVector.addElement(tempString);
      }
      //account = 0;
    }
    //end for (int i=0; i<mainVector.size(); i++)
    PartDebug.trace(this, "getDeReferenceByDoc() end....return is Vector ");
    return resultVector;
  }

  //���!!!!

  /**
   * ����partIfc�����������������DocMasterIfc�ļ��ϡ�
   * @param partIfc :QMPartIfc �㲿��ֵ����
   * @return Vector DocMasterIfc�ļ��ϡ�
   * @see QMPartInfo
   * @throws QMException
   */
  //��Ҫ��PartReferenceLink������partIfc, ��"referencedBy"
  private Vector getDocMastersByPartIfc(QMPartIfc partIfc)
      throws QMException
  {
    PartDebug.trace(this, "getDocMastersByPartIfc() begin ....");
    PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    Vector resultVector = new Vector();
    Collection collection = pservice.navigateValueInfo(partIfc, "referencedBy", "PartReferenceLink");
    if(collection == null || collection.size() == 0)
    {
      PartDebug.trace(this, "getDocMastersByPartIfc() end....return is new Vector");
      return resultVector;
    }
    else
    {
      Iterator iterator = collection.iterator();
      while(iterator.hasNext())
      {
        resultVector.addElement(iterator.next());
      }
      PartDebug.trace(this, "getDocMastersByPartIfc() end....return is Vector");
      return resultVector;
    }
  }
  //add by liyz 2008.1.23,
 /**
  * 
  * ��ȡһ�������еĶ���㲿����������EPM�ĵ����ʺ϶�汾���㲿����
  * ͨ��QMPart��EPMDocumen�Ĺ�����EPMBuildHistory����EPMDocumen�Ķ��󼯺ϡ�
  * @param partIfcVector �㲿��ֵ���󼯺�
  * @return ����һ���ִ����鼯�ϣ������Сȡ�����㲿�����󼯺ϵĴ�С+2��
  *<br> 0.EPM�ĵ�id;
  * <br>1.epm�ĵ���ţ�
  * <br>2.epm�ĵ��汾 �����������Ԫ���Ժ��Ԫ�ؼ�¼epm�ĵ��İ汾
  * @throws QMException
  */
  public Vector getDeEPMByDoc(Vector partIfcVector)
      throws QMException
  {
	PartDebug.trace(this,"getDeEPMByDoc() begin ....");
	int n=partIfcVector.size();
	String[] tempString=new String[n+2];	
	Vector mainVector = new Vector();
	Vector tempResult = new Vector();	
	//��partIfcVector������������EPMDocumentIfc���ŵ�tempResult��
	tempResult = getEpmDocByPartIfc((QMPartIfc)partIfcVector.elementAt(0));		
	if(tempResult != null && tempResult.size() > 0)		
  {
    EPMDocumentIfc[] epmIfcArray = new EPMDocumentIfc[tempResult.size()];
	Iterator iterator1 = tempResult.iterator();		
	for(int t = 0; t < tempResult.size(); t++)
	{
	   epmIfcArray[t]=(EPMDocumentIfc)iterator1.next();
	}
	//end for(int t = 0; t < tempResult.size(); t++)
	for(int i = 0; i < epmIfcArray.length; i++)
	{
		tempString = new String[n + 2];
        tempString[0] = ((EPMDocumentIfc)epmIfcArray[i]).getBsoID();
        tempString[1] = ((EPMDocumentIfc)epmIfcArray[i]).getDocNumber();
        tempString[2] = ((EPMDocumentIfc)epmIfcArray[i]).getVersionValue();        
        for(int j = 3; j < tempString.length; j++)
        {
           tempString[j]="";
        }
        mainVector.addElement(tempString);
	}
	//end for(int i = 0; i < epmIfcArray.length; i++)
  }
    //end if(tempResult != null && tempResult.size() > 0)
	
	//�����Ƕ�partIfcVector�еĴ�1��n-1��Ԫ�صĴ���
	
	String tempEPMNumber="";
	EPMDocumentIfc tempEPMIfc=null;		
	for(int j = 1; j < n; j++)
	{
	  tempResult = new Vector();
	  tempResult = getEpmDocByPartIfc((QMPartIfc)partIfcVector.elementAt(j));	  
	  if(tempResult != null && tempResult.size() > 0)
	  {	
		EPMDocumentIfc[] tempEPMArray=new EPMDocumentIfc[tempResult.size()];
		Iterator iterator2 = tempResult.iterator();		
	    for(int s = 0; s < tempResult.size(); s++)
	    {
	      tempEPMArray[s]= (EPMDocumentIfc) iterator2.next();
	    }
	    //end for(int j = 1; j < n; j++)
	    for(int k = 0; k < tempEPMArray.length; k++)
	    {
	      tempEPMIfc = (EPMDocumentIfc)tempEPMArray[k];
	      tempEPMNumber = tempEPMIfc.getDocNumber();
	      boolean flag = false;	      
	      //�Ƚ�tempEPMNumber��mainVector��Ԫ��
	      for(int m = 0; m < mainVector.size(); m++)
	      {
	    	String[] vectorString = (String[])mainVector.elementAt(m);	    	
	    	//���tempEPMNumber��vectorString[1]��ͬ���ϲ�������mainVector�е�
	    	//��m��Ԫ�ص�ֵString[j+2]�޸ģ�String[j+2] = �汾+����flag = true.�޸���Ϻ���������ѭ����
	    	if(tempEPMNumber.equals(vectorString[1]))
	    	{
	    	  vectorString[j + 2] = tempEPMIfc.getVersionValue();
	    	  mainVector.setElementAt(vectorString, m);
	    	  flag = true;
              break;
	    	}
	    	 //if(tempEPMNumber.equals(vectorString[1]))
	    	
	        //���tempEPMNumber��vectorString[1]����ͬ��
           //����for(int m=0; m < mainVector.size(); m++)
	      }
	      //end for(int m = 0; m < mainVector.size(); m++)
	      
	      //���for(int m = 0; m < mainVector.size(); m++)ѭ����������mainVector��û���ҵ���
	      //tempEPMNumber��ͬ��Ԫ�أ��Ͱѱ�tempEPMNumber�ӵ�mainVector��
	      if(flag==false)
	      {
	    	tempString = new String[n + 2];
	    	tempString[0] = tempEPMNumber;
	    	tempString[1] = tempEPMIfc.getDocNumber();
	    	//tempString[2] = tempEPMIfc.getVersionValue();
	    	for(int a = 2; a < tempString.length; a++)
	    	{
	          tempString[a]="";
	    	}
	    	tempString[j+2]=tempEPMIfc.getVersionValue();
	    	mainVector.addElement(tempString);
	      }
	      //end if(flag==false)
	    }
	    //end for(int k = 0; k < tempEPMArray.length; k++)
	  }
	  //end if(tempResult != null && tempResult.size() > 0)
	}
	//end for(int j = 1; j < n; j++)
	
	//�����mainVector�е�Ԫ�ؽ��д������tempString = (String[])mainVector.elementAt(i);
	//tempString[2]...tempString[n+1]������"false",�������洦��
	//�汾+������ͬ����Ҫɾ�������ǣ�VersionValue�����ǿգ������в�ͬ�������ʱ����Ҫ����
	Vector resultVector = new Vector();
	for(int i = 0; i < mainVector.size(); i++)
	{
	  int account =0;
	  tempString = new String[n + 2];
      String[] tempString1 = new String[n + 1];
      tempString = (String[])mainVector.elementAt(i);
      for(int j = 2; j < tempString.length; j++)
      {
    	if(tempString[j].equals(""))
    	{
    	  account++;
    	}
      }
      //end for(int j = 2; j < tempString.length; j++)
            
      if(account>0)
      {
    	for(int k = 0; k < tempString1.length; k++)
    	{
    	  tempString1[k] = tempString[k + 1];
    	}
    	resultVector.addElement(tempString1);
      }      
      else
      {
    	//������еİ汾+��������ͬ�Ļ�����ɾ����;��ͬ�Ļ���������
    	for(int k = 3; k < tempString.length; k++)
    	{
    	  if(!tempString[2].equals(tempString[k]))
    	  {
    		for(int kk = 0; kk < tempString1.length; kk++)
    		{
    			tempString1[kk] = tempString[kk+1];
    		}
    		resultVector.addElement(tempString1);
            break;
    	  }
    	  //end if(!tempString[2].equals(tempString[k]))
    	}
    	//end for(int k = 3; k < tempString.length; k++)
      }
      //end if and else
	}
	//end for(int i = 0; i < mainVector.size(); i++)
	PartDebug.trace(this, "getDeEPMByDoc() end....return is Vector");
	return resultVector;	  
  }
  //end !
  
  private Vector getEpmDocByPartIfc(QMPartIfc partIfc)
     throws QMException
  {
	PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
	Vector resultVector = new Vector();
	QMQuery query = new QMQuery("EPMBuildHistory");
    QueryCondition condition = new QueryCondition("rightBsoID","=",partIfc.getBsoID());
    query.addCondition(condition);
    Collection collection = pService.findValueInfo(query);
    Iterator iterator = collection.iterator();	    
    if(collection == null || collection.size() == 0)
    {
      return resultVector;
    }
    else
    {   
    	   Vector result =new Vector();
      while(iterator.hasNext())
      {
    	  EPMBuildHistoryIfc link = (EPMBuildHistoryIfc)iterator.next();
          EPMDocumentIfc epmDocIfc = (EPMDocumentIfc)link.getBuiltBy();          
          result.addElement(epmDocIfc);          
      }      
      return result;
    }
    //end if and else		  
  }
  //end !
  
  //ͨ��QMPart��Doc�Ĺ�����ϵ��PartDescribeLink����Doc�Ķ��󼯺ϣ�
  //���������ĵ��ǲ�ͬ�ģ�������ǵİ汾�Ͱ��򶼲���ͬ
  /**
   * �ж�partIfcVector�е�����Ԫ��QMPartIfc�������ĵ��Ĳ��������
   *
   * @param partIfcVector �㲿��ֵ���󼯺ϡ�
   * @return Vector  ����ֵVector��ÿ��Ԫ�ض���String[]:
   * <br>String[0]�������ĵ�����(DocIfc)���ĵ����(DocNum);
   * <br>String[1]��String[partIfcVector.size()]�м�¼�������ĵ���partIfcVector�еĶ���(partIfc)�еĴ��������
   * ���ڵ������¼�������ĵ��İ汾����, ������Ϊfalse,�汾�����falseΪString���͡�
   * @throws QMException
   */
  public Vector getDeDescribeByDoc(Vector partIfcVector)
      throws QMException
  {
    PartDebug.trace(this, "getDeDescribeByDoc() begin ....");
    // ����getDocsByPartIfc������partIfc������partIfc���������������ĵ�(DocIfc)�ļ���
    int n = partIfcVector.size();
    String[] tempString = new String[n + 2];
    Vector mainVector = new Vector();
    Vector tempResult = new Vector();
    //��partIfc�����еĵ�һ��Ԫ��partIfc�����������е�DocIfc���ŵ�mainVector��
    //mainVector���м��������е�ÿ��Ԫ�ض���String[]������String[0]��DocIfc��BsoID,
    //String[1]��DocIfc��docNum��String[2]��DocIfc��"�汾+����".String[3]...String[n+1]����
    //"false"!!!!
    tempResult = getDocsByPartIfc((QMPartIfc)partIfcVector.elementAt(0));
    
    if(tempResult != null && tempResult.size() > 0)
    {
      DocIfc[] docIfcArray = new DocIfc[tempResult.size()];      
      Iterator iterator1 = tempResult.iterator();
      
      for(int t = 0; t < tempResult.size(); t++)
      {
        docIfcArray[t] = (DocIfc)iterator1.next();
      }      
      //end for(int t =0; t<tempResult.size(); t++)
      for(int i = 0; i < docIfcArray.length; i++)
      {
        tempString = new String[n + 2];
        tempString[0] = ((DocIfc)docIfcArray[i]).getDocNum();
        tempString[1] = ((DocIfc)docIfcArray[i]).getDocNum();
        tempString[2] = ((DocIfc)docIfcArray[i]).getVersionValue();
        for(int j = 3; j < tempString.length; j++)
        {
          tempString[j] = "";
        }
        mainVector.addElement(tempString);
      }
      //end for(int i=0; i<docIfcArray.length; i++)
    }
    //end if(tempResult != null && tempResult.size() > 0)

    //����ʵ�ֶ�partIfcVector�еĴ�1��n-1��Ԫ��(partIfc����)�Ĵ���
    DocIfc[] tempDocArray = null;
    String tempDocNum = "";
    DocIfc tempDocIfc = null;     
    for(int j = 1; j < n; j++)
    {
      //�Ե�j��partIfc���д���:
      tempResult = new Vector();
      tempResult = getDocsByPartIfc((QMPartIfc)partIfcVector.elementAt(j));      
      if(tempResult != null && tempResult.size() > 0)
      {
        tempDocArray = new DocIfc[tempResult.size()];
        Iterator iterator = tempResult.iterator();
                
        for(int s = 0; s < tempResult.size(); s++)
        {
          tempDocArray[s] = (DocIfc)iterator.next();
        }
        //end for(int s=0; s<tempResult.size(); s++)
        for(int k = 0; k < tempDocArray.length; k++)
        {
          tempDocIfc = (DocIfc)tempDocArray[k];
          tempDocNum = tempDocIfc.getDocNum();          
          boolean flag = false;
          //��tempDocBsoID��mainVector�е�����Ԫ�ؽ��бȽ�:          
          for(int m = 0; m < mainVector.size(); m++)
          {
            String[] vectorString = (String[])mainVector.elementAt(m);            
            //���tempDocBsoID��vectorString[0]��ͬ�Ļ����ϲ�������mainVector�еĵ�
            //m��Ԫ�ص�ֵString[j+2]�޸ģ�String[j+2] = �汾+����flag = true.�޸���Ϻ���������ѭ����
            if(tempDocNum.equals(vectorString[1]))
            {
              vectorString[j + 2] = tempDocIfc.getVersionValue();
              mainVector.setElementAt(vectorString, m);
              flag = true;
              break;
            }
            //end if(tempDocBsoID.equals(vectorString[0]))

            //���tempDocBsoID��vectorString[0]����ͬ�Ļ������������ѭ��������
            //����for(int m=0; m<mainVector.size(); m++)
          }
          //end for(int m=0; m<mainVector.size(); m++)

          //���ѭ��for(int m=0; m<mainVector.size(); m++)��ϣ���mainVector��û���ҵ���
          //�� tempDocBsoID ��ͬ��Ԫ�صĻ����Ͱѱ�tempDocBsoID�ӵ�mainVector�С�
          //tempDocBsoID , false, ..., �汾+����(��Ӧ��j��part�İ汾), false,...false.
          if(flag == false)
          {
            tempString = new String[n + 2];
            tempString[0] = tempDocNum;
            tempString[1] = tempDocIfc.getDocNum();            
            for(int i1 = 2; i1 < tempString.length; i1++)
            {
              tempString[i1] = "";
            }
            //tempString[j + 2] = tempDocIfc.getVersionValue();
            mainVector.addElement(tempString);
          }
          //end if(flag == flase)
        }
        //end for(int k=0; k<tempDocArray.length; k++)
      }
      //end if(tempResult != null && tempResult.size() > 0)
    }
    //end for(int j=1; j<n; j++)

    //������Ϻ󣬽�������mainVector��,mainVector�е�Ԫ����String[]���͵�:
    //String[0]ΪDocIfc��BsoID, String[1]ΪDocIfc��DocNum��String[2]...String[n+1]��
    //false, ����"�汾+����"��

    //������Ҫ:��mainVector�е�Ԫ�ؽ��д������tempString[] = mainVector.elementAt(i)
    //tempString[2]...tempString[n+1]������"false",�������洦��
    //�汾+������ͬ����Ҫɾ�������ǣ�VersionValue�����ǿգ������в�ͬ�������ʱ��
    //������Ҫ���µ�!!
    Vector resultVector = new Vector();
    for(int i = 0; i < mainVector.size(); i++)
    {
      int account = 0;
      tempString = new String[n + 2];
      String[] tempString1 = new String[n + 1];
      tempString = (String[])mainVector.elementAt(i);
      for(int j = 2; j < tempString.length; j++)
      {
        if(tempString[j].equals(""))
        {
          account++;
        }
      }
      //end for(int j=2; j<tempString.length; j++)

      //���˵�������"false"��Ԫ��, ����Ҫ���˵�tempString[0]!!!!
      //���account>0����ʾ�˸�tempString����Ҫ���µ�::һ���в���ͬ��
      //���ڡ�
      if(account > 0)
      {
        for(int k = 0; k < tempString1.length; k++)
        {
          tempString1[k] = tempString[k + 1];
        }
        resultVector.addElement(tempString1);
      }
      //account == 0
      else
      {
        //��Ҫ��tempString[0] tempString[1],֮������е�"�汾+����"���бȽ�
        //����в���ͬ�ģ������£�������еİ汾+��������ͬ�Ļ�����ɾ������
        String s1 = tempString[2];
        for(int k = 3; k < tempString.length; k++)
        {
          if(!s1.equals(tempString[k]))
          {
            //��tempString[1][..]ת�Ƶ�tempString1[0][..]��
            for(int kk = 0; kk < tempString1.length; kk++)
            {
              tempString1[kk] = tempString[kk + 1];
            }
            resultVector.addElement(tempString1);
            break;
          }
          //end if(!s1.equals(tempString[k]))
        }
        //end for(int k=0; k<tempString.length; k++)
      }
      //end if and else(account > 0)
    }
    //end for(int i=0; i<mainVector.size(); i++)
    PartDebug.trace(this, "getDeDescribeByDoc() end....return is Vector");
    return resultVector;
  }
  //���!!!!

  /**
   * ����partIfc�����غ͸�partIfc�����������ĵ�ֵ����(DocIfc)�ļ��ϡ�
   * @param partIfc �㲿��ֵ����
   * @return Collection Collection�е�ÿ��Ԫ�ض���DocIfc���͡�
   * @see QMPartInfo
   * @throws QMException
   */
  private Vector getDocsByPartIfc(QMPartIfc partIfc)
      throws QMException
  {
    PartDebug.trace(this, "getDocsByPartIfc() begin ....");
    //��Ҫ��������ҵ�����Collection ����PartDescribeLink
    PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    Collection collection = pservice.navigateValueInfo(partIfc, "describes", "PartDescribeLink");
    Vector resultVector = new Vector();
    if(collection == null || collection.size() == 0)
    {
      PartDebug.trace(this, "getDocsByPartIfc() end....return is new Vector()");
      return resultVector;
    }
    else
    {
      Iterator iterator = collection.iterator();
      while(iterator.hasNext())
      {
        resultVector.addElement(iterator.next());
      }
      PartDebug.trace(this, "getDocsByPartIfc() end....return is Vector");
      //��Ҫ�Խ�����кϲ��������:::
      Vector result = new Vector();
      for(int i = 0; i < resultVector.size(); i++)
      {
        DocIfc docIfc = (DocIfc)resultVector.elementAt(i);
        String tempDocID = docIfc.getBsoID();
        boolean flag = false;
        for(int j = 0; j < result.size(); j++)
        {
          DocIfc docIfc1 = (DocIfc)result.elementAt(j);
          String tempDocID1 = docIfc1.getBsoID();
          if(tempDocID.equals(tempDocID1))
          {
            flag = true;
            break;
          }
        }
        //end for(int j=0; j<result.size(); j++)
        if(flag == false)
        {
          result.addElement(docIfc);
        }
      }
      //end for(int i=0; i<resultVector.size(); i++)
      return result;
    }
    //end if and else (collection == null || collection.size() == 0)
  }

  //���!!!!

  //��Ҫ���жϲ�ͬ�㲿��ʹ�õ�QMPartMasterIfc�Ĳ���ж���PartMaster��bsoID�Ƿ���ͬ
  /**
   * �ж�partIfcVector�е�����Ԫ�ص�ʹ�õ���һ���Ӳ�����ʹ�ò��������
   * ��
   * @param partIfcVector �㲿��ֵ���󼯺ϡ�
   * @return Vector ����ֵVector�е�Ԫ����String[]:
   * <br>String[0]��partIfcVector��partIfcʹ�õ���������;
   * <br>String[1]...String[partIfcVector.size()]��ÿ��partIfcʹ��ͬһ���Ӳ���������
   * ���û��ʹ��"false", ���������falseΪString����
   * @throws QMException
   */
  public Vector getDePart(Vector partIfcVector)
      throws QMException
  {
    PartDebug.trace(this, "getDePart() begin ....");
    //��Ҫһ������:����partIfc�����ظ�partIfc��ʹ�õ�partMasterIfc��partUsageLinkIfc�ļ���
    //����getDocMastersByPartIfc������partIfc������partIfc���������вο��ĵ�(DocMasterIfc)�ļ���
    int n = partIfcVector.size();
    String[] tempString = new String[n + 1];
    Vector mainVector = new Vector(10, 10);
    Vector tempResult = new Vector(10, 10);
    //��partIfcVector�еĵ�һ��Ԫ�ؽ��д����ѹ���������partMasterIfc��partNumber��
    //��ʹ�����������һ��ŵ�mainVector��
    tempResult = getPartMastersAndUsageLinks((QMPartIfc)partIfcVector.elementAt(0));
    //�ô���Ҫ��tempResult�����ж�!!!!!!::::
    if(tempResult != null && tempResult.size() > 0)
    {
      Object[] tempPartAndLinkArray = (Object[])tempResult.toArray();
      for(int i = 0; i < tempPartAndLinkArray.length; i++)
      {
        if(tempPartAndLinkArray[i] instanceof Object[])
        {
          Object[] tempObj = (Object[])tempPartAndLinkArray[i];
          tempString = new String[n + 1];
          tempString[0] = ((QMPartMasterIfc)tempObj[1]).getPartNumber();
          tempString[1] = (new Float(((PartUsageLinkIfc)tempObj[0]).getQuantity())).toString();
          if (tempString[1].endsWith(".0"))
              tempString[1] = tempString[1].substring(0, tempString[1].length() - 2);
          for(int j = 2; j < tempString.length; j++)
          {
            tempString[j] = "";
          }
          boolean flag = true;
          for(int j = 0; j < mainVector.size(); j++)
          {
            if(mainVector.elementAt(j) instanceof Object[])
            {
              String[] oldTempString = (String[])mainVector.elementAt(j);
              if(tempString[0].equals(oldTempString[0]))
              {
                float oldQuantity = (Float.valueOf(oldTempString[1])).floatValue();
                float quantity = ((PartUsageLinkIfc)tempObj[0]).getQuantity() + oldQuantity;
                String tempQuantity = String.valueOf(quantity);
                if (tempQuantity.endsWith(".0"))
                    tempQuantity = tempQuantity.substring(0,
                            tempQuantity.length() - 2);
                ((String[]) mainVector.elementAt(j))[1] =
                        tempQuantity;
                flag = false;
              }
            }
          }
          if(flag)
            mainVector.addElement(tempString);
        } //end if(tempPartAndLinkArray[i] instanceof Object[])
      } //end for (int i=0; i<tempPartAndLinkArray.length; i++)
    } //end if(tempResult != null && tempResult.size() > 0)

    //����ʵ�ֶ�partIfcVector�еĴ�1��n-1��Ԫ�صĴ���
    Vector mainVector2 = null;
    for(int j = 1; j < n; j++)
    {
      mainVector2 = new Vector();
      tempResult = getPartMastersAndUsageLinks((QMPartIfc)partIfcVector.elementAt(j));
      //�ô���Ҫ��tempResult�����ж�!!!!!!::::
      if(tempResult != null && tempResult.size() > 0)
      {
        //�ȴ���ͬһ�汾�ж����ͬ����������
        Object[] tempObj2 = null;
        for(int k = 0; k < tempResult.size(); k++)
        {
          if(tempResult.elementAt(k) instanceof Object[])
          {
            tempObj2 = (Object[])tempResult.elementAt(k);
            tempString = new String[n + 1];
            tempString[0] = ((QMPartMasterIfc)tempObj2[1]).getPartNumber();
            tempString[1] = (new Float(((PartUsageLinkIfc)tempObj2[0]).getQuantity())).toString();
            if (tempString[1].endsWith(".0"))
              tempString[1] = tempString[1].substring(0, tempString[1].length() - 2);
            for(int l = 2; l < tempString.length; l++)
            {
              tempString[l] = "";
            }
            boolean flag = true;
            for(int m = 0; m < mainVector2.size(); m++)
            {
              if(mainVector2.elementAt(m) instanceof Object[])
              {
                String[] oldTempString = (String[])mainVector2.elementAt(m);
                //���������ͬ��ϲ���ʹ������
                if(tempString[0].equals(oldTempString[0]))
                {
                  float oldQuantity = (Float.valueOf(oldTempString[1])).floatValue();
                  float quantity = ((PartUsageLinkIfc)tempObj2[0]).getQuantity() + oldQuantity;
                  String tempQuantity = String.valueOf(quantity);
                  if (tempQuantity.endsWith(".0"))
                      tempQuantity = tempQuantity.substring(0,
                            tempQuantity.length() - 2);
                  ((String[])mainVector2.elementAt(m))[1] = tempQuantity;
                  flag = false;
                }
              }
            }
            if(flag)
              mainVector2.addElement(tempString);
          }//end if(tempResult.elementAt(k) instanceof Object[])
        }//end for(int k = 0; k < tempResult.size(); k++)
        for(int k = 0; k < tempResult.size(); k++)
        {
          boolean flag1 = false;
          for(int q = k; q < mainVector2.size(); q++)
          {
            String[] vectorString2 = (String[])mainVector2.elementAt(q);
            for(int o = 0; o < mainVector.size(); o++)
            {
              String[] vectorString = (String[])mainVector.elementAt(o);
              //���tempPartMasterNum��vectorString[0]��ͬ�Ļ����ϲ�������mainVector�еĵ�
              //m��Ԫ�ص�ֵString[j+1]�޸ģ�String[j+1] = ʹ��������flag = true.�޸���Ϻ���������ѭ����
              if(vectorString2[0].equals(vectorString[0]))
              {
                vectorString[j + 1] = vectorString2[1];
                mainVector.setElementAt(vectorString, o);
                flag1 = true;
                break;
              }
              //end if(tempPartMasterNum == vectorString[0])
              //���tempPartMasterNum��vectorString[0]����ͬ�Ļ������������ѭ��������
              //����for(int m=0; m<mainVector.size(); m++)
            }
            if(flag1 == false)
            {
              tempString = new String[n + 1];
              tempString[0] = vectorString2[0];
              for(int i1 = 1; i1 < tempString.length; i1++)
              {
                tempString[i1] = "";
              }
              tempString[j + 1] = vectorString2[1];
              mainVector.addElement(tempString);
            }
          }//end for(int q = k; q < mainVector2.size(); q++)
        }//end for(int k = 0; k < tempResult.size(); k++)
      }//end if(tempResult != null && tempResult.size() > 0)
    } //end for(int j = 1; j < n; j++)
    //��Ϊ�鵽����DocMasterIfc�ļ��ϣ����ԣ����ж�����DocMaster�Ƿ���ͬ��ʱ��ֻ��Ҫ
    //�ж���docNumber�Ƿ���ͬ.
    //���ķ���ֵ��Vector:vector vector�е�ÿ��Ԫ��Ӧ����:
    //PartMasterNumber, boolean ,boolean ,... , boolean (n��String��boolean)
    //�����Ҫ��mainVector�е�Ԫ�ؽ��д���ɾ��ͬһ��BsoID���������ʹ����������ͬ�����Ԫ�ء�
    Vector resultVector = new Vector();
    boolean flag = false;
    for(int i = 0; i < mainVector.size(); i++)
    {
      flag = false;
      tempString = new String[n + 1];
      tempString = (String[])mainVector.elementAt(i);
      for(int u = 1; u < tempString.length; u++)
      {
        //�������false�ģ��ӵ����������
        if(tempString[u].equals(""))
        {
          resultVector.addElement(tempString);
          flag = true;
          break;
        }//end if (tempString[u] == "false"
      }//end for (int u=1; u<tempString.length; u++)
      //�����tempString[1]..[n]�ж���󶼲���"false"�Ļ�������Ҫ�ж�ÿ��Ԫ��
      //ת��Ϊfloat���ͺ��Ƿ�����ȵ�:����ȵĻ����ӵ���������С�
      if(flag == false)
      {
        float tempFloat = 0.0f;
        tempFloat = (new Float(tempString[1])).floatValue();
        for(int k = 2; k < tempString.length; k++)
        {
          if(tempFloat != ((new Float(tempString[k])).floatValue()))
          {
            resultVector.addElement(tempString);
            break;
          }
        }
        //end for(int k=2; k<tempString.length; k++)
      }
      //end if(flag == false)
    }
    //end for (int i=0; i<mainVector.size(); i++)
    PartDebug.trace(this, "getDePart() end....return is Vector");
    return resultVector;
  }
  //���!!!!

  /**
   * ����partIfc����ȡpartIfcʹ�õ�partMasterIfc���͹�����partUsageLinkIfc�ļ��ϡ�
   * 
   * @param partIfc �㲿��ֵ����
   * @return Vector ��������Vector ����Collection��ÿ��Ԫ���Ǹ�����Object[]:
   * ����Ӽ����еĵ�һ��Ԫ����partUsageLinkIfc����
   * �ڶ���Ԫ����partMasterIfc����
   * @see QMPartInfo
   * @throws QMException
   */
  private Vector getPartMastersAndUsageLinks(QMPartIfc partIfc)
      throws QMException
  {
    PartDebug.trace(this, "getPartMastersAndUsageLinks() begin ....");
    //û�б�Ҫ!!!
    //���Ϳ�����,�ȵ���PersistService�е�
    //expandValueInfo(BaseValueIfc info,java.lang.String roleName,java.lang.String linkBsoName,
    //    boolean otherSideOnly) throws QMException

    //�ȷ��ص���PartUsageLinkIfc�ļ��ϣ��ٸ���partUsageLinkIfc.getLeftBsoID��ȡQMPartMasterIfc��BsoID��
    //Ȼ��Ͱ�QMPartMasterIfc��BsoIDת��ΪQMPartMasterIfc

    //��󱣴����(PartUsageLinkIfc, QMPartMasterIfc)�ļ���
    Vector resultVector = new Vector();
    PersistService pService = (PersistService)EJBServiceHelper.
        getPersistService();
    Collection collection=null;
   
if(partIfc instanceof GenericPartIfc)
    {
     collection = pService.expandValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
    }else
if(partIfc instanceof QMPartIfc)
{
	collection = pService.expandValueInfo(partIfc, "usedBy", "PartUsageLink", false);
}
    if((collection == null) || (collection.size() == 0))
    {
      PartDebug.trace(this, "getPartMastersAndUsageLinks() end....return is null");
      return resultVector;
    }
    else
    {
      PartUsageLinkIfc[] links = new PartUsageLinkIfc[collection.size()];
      Iterator iterator = collection.iterator();
      for(int i = 0; i < collection.size(); i++)
      {
        links[i] = (PartUsageLinkIfc)iterator.next();
      }
      for(int i = 0; i < links.length; i++)
      {
        QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(links[i].getLeftBsoID());
        Object[] obj = {links[i], partMasterIfc};
        resultVector.addElement(obj);
      }
    }
    PartDebug.trace(this, "getPartMastersAndUsageLinks() end....return is Vector");
    return resultVector;
  }

  /**
   * ����ָ���ķָ������������ַ�����Ϊһϵ����ҵ��������ַ�����
   * 2003.08.14�����µ����󣺿��Զ��㲿���ĸ������Խ��ж��ƣ�
   * �������������Ĳ�����Ӧ�ö���ͨ���Ժ���չ���Խ������֡�
   * �����µ�����"+viewName+partName-��չ������1-��չ������2"������ͨ������ǰΪ"+"��
   * ��չ������ǰΪ"-"��
   * ������Ϊ��������ͨ���Ժ���չ���ԣ���Ҫ�޸ĸ÷����ķ���ֵ����String[]�޸�ΪVector
   * 
   * @param attrs : String
   * @return Vector Vector�к�������Ԫ�أ�ÿ��Ԫ�ض���String[]���ͣ���һ��String[]Ԫ�ر������е���ͨ��������
   * ��һ��String[]������չ��������
   */
  public Vector getTableHeader(String attrs)
  {
    PartDebug.trace(this, "getTableHeader() begin ...." + attrs);
    attrs = attrs.trim();
    if(attrs == null || attrs.length() == 0)
    {
      PartDebug.trace(this, "getTableHeader() end....return is new Vector()");
      Vector result = new Vector();
      return result;
    }
    //attrs = attrs.trim();
    if(!attrs.substring(0, 1).equals("-"))
    {
      attrs = " " + attrs;
    }
    Vector result = new Vector(); //�������Ľ����
    Vector temp1 = new Vector(); //�������е���ͨ������
    Vector temp2 = new Vector(); //�������е���չ������
    String tempString1 = ""; //��¼һ��������װ����ͨ�������ַ���
    String tempString2 = ""; //��¼һ��������װ����չ�������ַ���
    for(int i = 0; i < attrs.length(); i++)
    {
      String nowChar = attrs.substring(i, i + 1);
      //���nowChar��+,-�Ļ�����tempString���شӵ�ǰ��
      if(nowChar.equals(" ") || nowChar.equals("-"))
      {
        //Ѱ����һ��"+", "-"���ڵ�λ��
        String tempAttr = attrs.substring(i + 1, attrs.length());
        int indexadd = tempAttr.indexOf(" ");
        int indexreduce = tempAttr.indexOf("-");
        if(indexadd == -1)
        {
          if(indexreduce == -1)
          {
            if(nowChar.equals(" "))
            {
              temp1.addElement(tempAttr);
            }
            else
            {
              temp2.addElement(tempAttr);
            }
          }
          else
          {
            if(nowChar.equals(" "))
            {
              temp1.addElement(tempAttr.substring(0, indexreduce));
            }
            else
            {
              temp2.addElement(tempAttr.substring(0, indexreduce));
            }
          }
        }
        else
        {
          if(indexreduce == -1)
          {
            if(nowChar.equals(" "))
            {
              temp1.addElement(tempAttr.substring(0, indexadd));
            }
            else
            {
              temp2.addElement(tempAttr.substring(0, indexadd));
            }
          }
          else
          {
            int index = (indexadd - indexreduce) > 0 ? indexreduce : indexadd;
            if(nowChar.equals(" "))
            {
              temp1.addElement(tempAttr.substring(0, index));
            }
            else
            {
              temp2.addElement(tempAttr.substring(0, index));
            }
          }
        }
      }
    }
    String[] tempArray1 = new String[temp1.size()];
    for(int i = 0; i < temp1.size(); i++)
    {
      tempArray1[i] = (temp1.elementAt(i)).toString();
    }
    result.addElement(tempArray1);
    String[] tempArray2 = new String[temp2.size()];
    for(int i = 0; i < temp2.size(); i++)
    {
      tempArray2[i] = (temp2.elementAt(i)).toString();
    }
    result.addElement(tempArray2);
    PartDebug.trace(this, "getTableHeader() end....return is String[]");
    return result;
  }

  /**
   * ������ɢ��������Ϣ����PartConfigSpecIfc����,һ��ʮ��������,
   * �÷�������StandardPartService�е�ͬ���ķ�����
   * @param effectivityActive �Ƿ�����Ч�����ù淶 ��1,��0
   * @param baselineActive �Ƿ��ǻ�׼�����ù淶 ��1,��0
   * @param standardActive �Ƿ��Ǳ�׼���ù淶 ��1,��0
   * @param baselineID ��׼�ߵ�BsoID
   * @param configItemID ���ù淶��BsoID
   * @param viewObjectID ��ͼ�����bsoID
   * @param effectivityType ��Ч������
   * @param effectivityUnit ��Ч�Ե�λ(��Ч�Ե�ֵ)
   * @param state ��������״̬
   * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true
   * @return PartConfigSpecIfc ��װ�õ��㲿�����ù淶ֵ����
   * @see PartConfigSpecInfo
   * @throws QMException
   */
  public PartConfigSpecIfc hashtableToPartConfigSpecIfc(
      String effectivityActive, String baselineActive, String standardActive,
      String baselineID,
      String configItemID, String viewObjectID, String effectivityType,
      String effectivityUnit,
      String state, String workingIncluded)
      throws QMException
  {
    PartConfigSpecIfc configSpecIfc = PartServiceRequest.hashtableToPartConfigSpecIfc(
        effectivityActive, baselineActive, standardActive, baselineID,
        configItemID, viewObjectID, effectivityType, effectivityUnit, state, workingIncluded);
    return configSpecIfc;
  }

  /**
   * ����������㲿������ϢBsoID����ʮ�������㲿�����ù淶�Ĳ�����
   * ���ط����㲿�����ù淶�ģ��ܸ�QMPartMasterIfc����������㲿���ļ��ϡ�
   * @param partMasterID �㲿������ϢBsoID
   * @param effectivityActive �Ƿ�����Ч�����ù淶 ��1,��0
   * @param baselineActive �Ƿ��ǻ�׼�����ù淶 ��1,��0
   * @param standardActive �Ƿ��Ǳ�׼���ù淶 ��1,��0
   * @param baselineID ��׼�ߵ�BsoID
   * @param configItemID ���ù淶��BsoID
   * @param viewObjectID ��ͼ�����bsoID
   * @param effectivityType ��Ч������
   * @param effectivityUnit ��Ч�Ե�λ(��Ч�Ե�ֵ)
   * @param state ��������״̬
   * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true
   * @return Vector ���˳������㲿��ֵ����ļ��ϣ����û�кϸ���㲿������new Vector()��
   * @exception QMException
   */
  public Vector filterIterations(String partMasterID,
                                 String effectivityActive,
                                 String baselineActive, String standardActive,
                                 String baselineID,
                                 String configItemID, String viewObjectID,
                                 String effectivityType,
                                 String effectivityUnit, String state,
                                 String workingIncluded)
      throws QMException
  {
    PartDebug.trace(this, "filterIterations() begin.....");
    //��һ��:�ָ�QMPartMasterIfc
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(partMasterID);
    //�ڶ������ָ��㲿�����ù淶ֵ����
    PartConfigSpecIfc configSpecIfc = hashtableToPartConfigSpecIfc(
        effectivityActive, baselineActive, standardActive, baselineID, configItemID,
        viewObjectID, effectivityType, effectivityUnit, state, workingIncluded);
    //������������������������StandardPartService�еķ���filterdIterationsOf����
    Vector paraVector = new Vector();
    paraVector.addElement(partMasterIfc);
    Collection result = PartServiceRequest.filteredIterationsOf(paraVector, configSpecIfc);
    Vector resultVector = new Vector();
    if(result == null | result.size() == 0)
    {
      PartDebug.trace(this, "filterIterations() end....return is new Vector()");
      return resultVector;
    }
    else
    {
      Iterator iterator = result.iterator();
      while(iterator.hasNext())
      {
        resultVector.addElement(iterator.next());
      }
      PartDebug.trace(this, "filterIterations() end....return is Vector");
      return resultVector;
    }
  }

  /**
   * ����������㲿������ϢBsoID�����㲿�����ù淶,
   * ���ط����㲿�����ù淶�ģ��ܸ�QMPartMasterIfc����������㲿���ļ��ϡ�
   * @param partMasterID �㲿������ϢBsoID��
   * @return Vector ���˳������㲿��ֵ����ļ��ϣ����û�кϸ���㲿������new Vector()��
   * @exception QMException
   */
  public Vector filterIterations(String partMasterID)
      throws QMException
  {
    PartDebug.trace(this, "filterIterations() begin.....");
    //��һ��:�ָ�QMPartMasterIfc
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(partMasterID);
    //�ڶ������㲿�����ù淶ֵ����
    PartConfigSpecIfc configSpecIfc = PartServiceRequest.findPartConfigSpecIfc();
    //������������������������StandardPartService�еķ���filterdIterationsOf����
    Vector paraVector = new Vector();
    paraVector.addElement(partMasterIfc);
    Collection result = PartServiceRequest.filteredIterationsOf(paraVector, configSpecIfc);
    Vector resultVector = new Vector();
    if(result == null | result.size() == 0)
    {
      PartDebug.trace(this, "filterIterations() end....return is new Vector()");
      return resultVector;
    }
    else
    {
      Iterator iterator = result.iterator();
      while(iterator.hasNext())
      {
        resultVector.addElement(iterator.next());
      }
      PartDebug.trace(this, "filterIterations() end....return is Vector");
      return resultVector;
    }
  }

  /**
   * ����ָ�����㲿�������¼��㲿�������°汾��ֵ����ļ��ϡ�
   * @param partID : String �㲿����BsoID��
   * @return collection:Collection partIfcʹ�õ��¼��Ӽ������°汾��ֵ���󼯺ϡ�
   * @throws QMException
   */
  public Collection getSubParts(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getSubParts() begin.....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    Collection coll = PartServiceRequest.getSubParts(partIfc);
    PartDebug.trace(this, "getSubParts() end....return is Collection");
    return coll;
  }

  /**
   * ��ȡ����ֱ����ʹ�õ�ǰpartID��Ӧ���㲿�����㲿��,
   * �����ҵ����е�ʹ�ø�����ļ��ϡ�
   * @param partID :��ʹ�õ��㲿����BsoID��
   * @return Vector ����ֱ��ʹ����partID��Ӧ���㲿���ļ��ϡ�
   * @throws QMException
   */
  public Vector getParentParts(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getParentParts() begin.....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    Vector vector = PartServiceRequest.getParentParts(partIfc);
    PartDebug.trace(this, "getParentParts() end....return is Vector");
    return vector;
  }

  /**
   * �����㲿����BsoID��ȡ�㲿������Ӧ��QMPartMaster��BsoID��
   * @param partID �㲿��BsoID
   * @return String MasterBsoID
   * @throws QMException
   */
  public String getMasterIDByPartID(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getMasterIDByPartID() begin.....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    String masterID = partIfc.getMasterBsoID();
    PartDebug.trace(this, "getMasterIDByPartID() end....return is String");
    return masterID;
  }

  /**
   * �÷���������ʾ�����ṹ�����Ч�ԡ��Ľ��,��������Ĳ���masterID�������ù淶��
   * ʮ�����������ҳ���masterID�ķ������ù淶������С�汾��QMPartIfc���ٸ������
   * QMPartIfc�ҳ����и�QMPartIfcʹ�õ����㲿������Ч��ֵ��������Ч�Է������кϲ�����
   * 
   * @param masterID �㲿������ϢBsoID
   * @param effectivityActive �Ƿ�����Ч�����ù淶 ��1,��0
   * @param baselineActive �Ƿ��ǻ�׼�����ù淶 ��1,��0
   * @param standardActive �Ƿ��Ǳ�׼���ù淶 ��1,��0
   * @param baselineID ��׼�ߵ�BsoID
   * @param configItemID ���ù淶��BsoID
   * @param viewObjectID ��ͼ�����bsoID
   * @param effectivityType ��Ч������
   * @param effectivityUnit ��Ч�Ե�λ(��Ч�Ե�ֵ)
   * @param state ��������״̬
   * @param workingIncluded �Ƿ��ڸ����ļ��У�Ĭ��Ϊ0:false,1:true
   * @return Vector ����ֵVector�����ݽṹΪ:Vector�е�ÿ��Ԫ�ض���String[],String[]�е�ÿ��Ԫ��Ϊ:
   *<br> String[0]:�㲿�����룻
   *<br> String[1]:�㲿�����ƣ�
   *<br> String[2]:�㲿���汾����汾+С�汾����
   *<br> String[3]:��ͼ���ƣ�
   *<br> String[4]:��Ч�Է������ƣ�
   *<br> String[5]:��Ч�����ͣ�
   *<br> String[6]:��Ч�Է�Χֵ����������Ч��ֵ��ȡ�����족����
   *<br> String[7]:�㲿����bsoID����Ϊ�ں������г����ӣ����㲿����bsoIDҲ����������
   *<br> String[8]:�㲿����StandardIconName��
   * @throws QMException
   */
  public Vector getPopulateEffectivities(String masterID,
                                         String effectivityActive,
                                         String baselineActive,
                                         String standardActive,
                                         String baselineID,
                                         String configItemID,
                                         String viewObjectID,
                                         String effectivityType,
                                         String effectivityUnit,
                                         String state, String workingIncluded)
      throws QMException
  {
    PartDebug.trace(this, "getPopulateEffectivities() begin.....");
    EffService effService = (EffService)EJBServiceHelper.getService("EffService");

    //������һ��:masterID -> QMPartMasterIfc,
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(masterID);

    //�����ڶ���:ʮ������ -> PartConfigSpecIfc,
    PartConfigSpecIfc configSpecIfc = hashtableToPartConfigSpecIfc(
        effectivityActive, baselineActive, standardActive, baselineID, configItemID,
        viewObjectID, effectivityType, effectivityUnit, state, workingIncluded);

    //����������:����QMPartMasterIfc, PartConfigSpecIfc�ҵ���QMPartMasterIfc��
    //�������ù淶�������ӽڵ��㲿����QMPartIfc EnterprisePartService.getAllSubPartsByConfigSpec()
    QMPartIfc[] partArray = PartServiceRequest.getAllSubPartsByConfigSpec(partMasterIfc, configSpecIfc);
    //�������Ĳ�:����EnterprisePartService�еķ���
    //getEffVector(QMPartIfc)��������ȡ����QMPartIfc������EffGroup[],�Ϳ����ȶ�
    //�������㲿��QMPartIfc����ѭ������ѭ�����ٶ�EffGroup[]����ѭ������װ���Ľ����
    Vector resultVector = new Vector();
    for(int i = 0; i < partArray.length; i++)
    {
      QMPartInfo partInfo = (QMPartInfo)partArray[i];
      Vector effGroups = PartServiceRequest.getEffVector(partInfo);
      //���һ���㲿����������Ч��ֵ(���е�EffGroup����ļ���)����ѭ����
      for(int j = 0; j < effGroups.size(); j++)
      {
        EffGroup effGroup = (EffGroup)effGroups.elementAt(j);
        String configItemName = "";
        String effType = "";
        if(effGroup.getEffContext() != null)
        {
          QMConfigurationItemIfc qmConfigItemIfc = (QMConfigurationItemIfc)effGroup.getEffContext();
          configItemName = qmConfigItemIfc.getConfigItemName();
          EffectivityType type = qmConfigItemIfc.getEffectivityType();
          if(type != null)
          {
            effType = qmConfigItemIfc.getEffectivityType().getDisplay();
          }
          else
          {
            //��Ҫ����effGroup�����е�type:String����ַ�������
            //effType�ľ�������ͽ���ָ����
            //��δ�����ÿ��Բ�д����!!!!
            String typeOfEffGroup = effGroup.getType();
            if(typeOfEffGroup.equals("QMDatedEffectivity"))
            {
              effType = (EffectivityType.DATE).getDisplay();
            }
            else
            {
              if(typeOfEffGroup.equals("QMSerialNumEffectivity"))
              {
                effType = (EffectivityType.SERIAL_NUM).getDisplay();
              }
              else
              {
                if(typeOfEffGroup.equals("QMLotEffectivity"))
                {
                  effType = (EffectivityType.LOT_NUM).getDisplay();
                }
              }
            }
            //effType = "";
          }
          //effType = qmConfigItemIfc.getEffectivityType().getDisplay();
        }
        else
        {
          //��������Ч��:
          //configItemName = "";
          effType = (EffectivityType.DATE).getDisplay();
        }
        //end if and else (effGroup.getEffContext() != null)
        //��Ҫ���������͵�range���д���:����������Ч��ֵ��ȡ�����족��
        String resultRange = "";
        if(effType.equals((EffectivityType.DATE).getDisplay()))
        {
          if(effGroup.getRange() != null && effGroup.getRange().length() > 0)
          {
            resultRange = effService.getDateType(effGroup.getRange());
          }
        }
        else
        {
          resultRange = effGroup.getRange();
        }
        //end if and else (effType.equals((EffectivityType.DATE).getDisplay()))
        String[] tempString = new String[9];
        tempString[0] = partInfo.getPartNumber();
        tempString[1] = partInfo.getPartName();
        tempString[2] = partInfo.getVersionValue();
        tempString[3] = partInfo.getViewName();
        tempString[4] = configItemName;
        tempString[5] = effType;
        tempString[6] = resultRange;
        tempString[7] = partInfo.getBsoID();
        tempString[8] = partInfo.getStandardIconName();
        resultVector.addElement(tempString);
      }
      //end for(int j=0; j<effGroups.size(); j++)
    }
    //end for(int i=0; i<partArray.length; i++)
    PartDebug.trace(this, "getPopulate" + "Effectivities() end....return is String");
    return resultVector;
  }

  /**
   * �÷���������ʾ�����ṹ�����Ч�ԡ��Ľ��,��������Ĳ���masterID�������ù淶
   * ���ҳ���masterID�ķ������ù淶������С�汾��QMPartIfc���ٸ������
   * QMPartIfc�ҳ����и�QMPartIfcʹ�õ����㲿������Ч��ֵ��������Ч�Է������кϲ�����
   * 
   * @param masterID �㲿������ϢBsoID
   * @return Vector ����ֵVector�����ݽṹΪ:Vector�е�ÿ��Ԫ�ض���String[],String[]�е�ÿ��Ԫ��Ϊ:
   *<br> String[0]:�㲿�����룻
   *<br> String[1]:�㲿�����ƣ�
   *<br> String[2]:�㲿���汾����汾+С�汾����
   *<br> String[3]:��ͼ���ƣ�
   *<br> String[4]:��Ч�Է������ƣ�
   *<br> String[5]:��Ч�����ͣ�
   *<br> String[6]:��Ч�Է�Χֵ����������Ч��ֵ��ȡ�����족����
   *<br> String[7]:�㲿����bsoID����Ϊ�ں������г����ӣ����㲿����bsoIDҲ����������
   *<br> String[8]:�㲿����StandardIconName��
   * @throws QMException
   */
  public Vector getPopulateEffectivities(String masterID,String itemName)
      throws QMException
  {
    PartDebug.trace(this, "getPopulateEffectivities() begin.....");
    EffService effService = (EffService)EJBServiceHelper.getService("EffService");
    //������һ��:masterID -> QMPartMasterIfc,
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(masterID);


    //�����ڶ���:PartConfigSpecIfc,
    PartConfigSpecIfc configSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();

    //����������:����QMPartMasterIfc, PartConfigSpecIfc�ҵ���QMPartMasterIfc��
    //�������ù淶�������ӽڵ��㲿����QMPartIfc EnterprisePartService.getAllSubPartsByConfigSpec()
    QMPartIfc[] partArray = PartServiceRequest.getAllSubPartsByConfigSpec(partMasterIfc, configSpecIfc);

         QMQuery que = new QMQuery("QMConfigurationItem");
         QueryCondition cond = new QueryCondition("configItemName","=",itemName);
         que.addCondition(cond);
         Collection col = pService.findValueInfo(que);

         QMConfigurationItemIfc item = null;
         Iterator iter = col.iterator();
         while(iter.hasNext())
         {
              item = (QMConfigurationItemIfc)iter.next();
         }

    //�������Ĳ�:����EnterprisePartService�еķ���
    //getEffVector(QMPartIfc)��������ȡ����QMPartIfc������EffGroup[],�Ϳ����ȶ�
    //�������㲿��QMPartIfc����ѭ������ѭ�����ٶ�EffGroup[]����ѭ������װ���Ľ����
    Vector resultVector = new Vector();
    for(int i = 0; i < partArray.length; i++)
    {
      QMPartInfo partInfo = (QMPartInfo)partArray[i];
      Vector effGroups = PartServiceRequest.getEffVector(partInfo,item.getBsoID());
      //���һ���㲿����������Ч��ֵ(���е�EffGroup����ļ���)����ѭ����
      for(int j = 0; j < effGroups.size(); j++)
      {
        EffGroup effGroup = (EffGroup)effGroups.elementAt(j);
        String configItemName = "";
        String effType = "";
        if(effGroup.getEffContext() != null)
        {
          QMConfigurationItemIfc qmConfigItemIfc = (QMConfigurationItemIfc)effGroup.getEffContext();
          configItemName = qmConfigItemIfc.getConfigItemName();
          EffectivityType type = qmConfigItemIfc.getEffectivityType();
          if(type != null)
          {
            effType = qmConfigItemIfc.getEffectivityType().getDisplay();
          }
          else
          {
            //��Ҫ����effGroup�����е�type:String����ַ�������
            //effType�ľ�������ͽ���ָ����
            //��δ�����ÿ��Բ�д����!!!!
            String typeOfEffGroup = effGroup.getType();
            if(typeOfEffGroup.equals("QMDatedEffectivity"))
            {
              effType = (EffectivityType.DATE).getDisplay();
            }
            else
            {
              if(typeOfEffGroup.equals("QMSerialNumEffectivity"))
              {
                effType = (EffectivityType.SERIAL_NUM).getDisplay();
              }
              else
              {
                if(typeOfEffGroup.equals("QMLotEffectivity"))
                {
                  effType = (EffectivityType.LOT_NUM).getDisplay();
                }
              }
            }
            //effType = "";
          }
          //effType = qmConfigItemIfc.getEffectivityType().getDisplay();
        }
        else
        {
          //��������Ч��:
          //configItemName = "";
          effType = (EffectivityType.DATE).getDisplay();
        }
        //end if and else (effGroup.getEffContext() != null)
        //��Ҫ���������͵�range���д���:����������Ч��ֵ��ȡ�����족��
        String resultRange = "";
        if(effType.equals((EffectivityType.DATE).getDisplay()))
        {
          if(effGroup.getRange() != null && effGroup.getRange().length() > 0)
          {
            resultRange = effService.getDateType(effGroup.getRange());
          }
        }
        else
        {
          resultRange = effGroup.getRange();
        }
        //end if and else (effType.equals((EffectivityType.DATE).getDisplay()))
        String[] tempString = new String[9];
        tempString[0] = partInfo.getPartNumber();
        tempString[1] = partInfo.getPartName();
        tempString[2] = partInfo.getVersionValue();
        tempString[3] = partInfo.getViewName();
        tempString[4] = configItemName;
        tempString[5] = effType;
        tempString[6] = resultRange;
        tempString[7] = partInfo.getBsoID();
        tempString[8] = partInfo.getStandardIconName();
        resultVector.addElement(tempString);
      }
      //end for(int j=0; j<effGroups.size(); j++)
    }
    //end for(int i=0; i<partArray.length; i++)
    PartDebug.trace(this, "getPopulate" + "Effectivities() end....return is String");
    return resultVector;
  }
  /**
   * ��ȡ���ṹ�����Ч�ԵĽ������"���ṹ�����Ч�Խ��"ҳ����ʾ�á�
   * ��ǰ��ҳ����õ��Ǵ���һ��������getPopulateEffectivities������
   * �����������ֻ��ʾ��ǰ��ӵ���Ч�Է����ļ�¼��������ʾ��������ӹ�ֵ����Ч�Է����ļ�¼��
   * �ʸ�Ϊ���ô˷�����
   * @param masterID String
   * @param itemName String
   * @param type String
   * @throws QMException
   * @return Vector ����ֵVector�����ݽṹΪ:Vector�е�ÿ��Ԫ�ض���String[],String[]�е�ÿ��Ԫ��Ϊ:
   *<br> String[0]:�㲿�����룻
   *<br> String[1]:�㲿�����ƣ�
   *<br> String[2]:�㲿���汾����汾+С�汾����
   *<br> String[3]:��ͼ���ƣ�
   *<br> String[4]:��Ч�Է������ƣ�
   *<br> String[5]:��Ч�����ͣ�
   *<br> String[6]:��Ч�Է�Χֵ����������Ч��ֵ��ȡ�����족����
   *<br> String[7]:�㲿����bsoID����Ϊ�ں������г����ӣ����㲿����bsoIDҲ����������
   *<br> String[8]:�㲿����StandardIconName��
   */
  public Vector getPopulateResult(String masterID,String itemName,String type)
      throws QMException
  {
      Vector tempVector = getPopulateEffectivities(masterID,itemName);
      String temp[];
      Vector resultVector = new Vector();
      for(int i=0; i < tempVector.size(); i++)
      {
          temp = (String[])tempVector.elementAt(i);
          if(temp[4].equals(itemName) && temp[5].equals(type))
              resultVector.addElement(temp);
      }
      return resultVector;
  }

  /**
   * �����㲿��BsoID��ȡ���㲿�������еĻ������Լ��ϡ�
   * 
   * @param partBsoID �㲿��BsoID
   * @return String[] ����ֵ��һ���ַ�������:
   * <br>String[0]:�㲿����ţ�
   * <br>String[1]:�汾+����
   * <br>String[2]:�㲿�����ƣ�
   * <br>String[3]:��ͼ���ƣ�
   * <br>String[4]:�����ļ���(���λ��)��
   * <br>String[5]:��λ��
   * <br>String[6]:�����������֣�
   * <br>String[7]:��Դ��
   * <br>String[8]:��Ŀ�飻
   * <br>String[9]:���ͣ�
   * <br>String[10]:��������״̬��
   * <br>String[11]:״̬(���룬���״̬)��
   * <br>String[12]�������ߣ�
   * <br>String[13]�������ߣ�
   * <br>String[14]������ʱ�䣻
   * <br>String[15]������ʱ�䣻
   * <br>String[15]��С�汾ע�͡�
   * @throws QMException
   */
  public String[] getChineseAttr(String partBsoID)
      throws QMException
  {
    PartDebug.trace(this, "getChineseAttr(" + partBsoID + "): begin... ");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partBsoID);
        //CCBegin by liunan 2008-07-24
    //ԭ��������
    //String[] result = new String[17];
    String[] result = new String[18];
    //CCEnd by liunan 2008-07-24
    result[0] = partIfc.getPartNumber();
    result[1] = partIfc.getVersionValue();
    result[2] = partIfc.getPartName();
    //CCBegin SS10
    if(partIfc.getPartName().indexOf("����")>0)
    {
    	String partName = partIfc.getPartName();
    	String otherPartNum = partName.substring(partName.indexOf("����")+2,partName.length()-1);
    	System.out.println("otherPartNum============"+otherPartNum);
    	String otherPartID = PublishHelper.getPartBsoID(otherPartNum);
    	if(otherPartID!=null)
    	  result[2] = partName.substring(0,partName.indexOf("����"))+"<a href=\"Part-Other-PartLookOver-001.screen?bsoID="+otherPartID+"\">"+partName.substring(partName.indexOf("����"),partName.length())+"</a>";
    	System.out.println("result[2]============"+result[2]);   	
    }
    if(partIfc.getPartName().indexOf("(��")>0)
    {
    	String partName = partIfc.getPartName();
    	String otherPartNum = partName.substring(partName.indexOf("(��")+2,partName.length()-1);
    	System.out.println("otherPartNum============"+otherPartNum);
    	String otherPartID = PublishHelper.getPartBsoID(otherPartNum);
    	if(otherPartID!=null)
    	  result[2] = partName.substring(0,partName.indexOf("(��"))+"<a href=\"Part-Other-PartLookOver-001.screen?bsoID="+otherPartID+"\">"+partName.substring(partName.indexOf("(��"),partName.length())+"</a>";
    	System.out.println("result[2]============"+result[2]);   	
    }
    //CCEnd SS10
    if(partIfc.getViewName() != null && partIfc.getViewName().length() > 0)
    {
      result[3] = partIfc.getViewName();
    }
    else
    {
      result[3] = "";
    }
    result[4] = partIfc.getLocation();
    result[5] = partIfc.getDefaultUnit().getDisplay();
    //������������::����LifeCycleTemplate���BsoID����ȡ��ֵ����
    //�ٻ�ȡֵ����������������֡�
    if(partIfc.getLifeCycleTemplate() != null && partIfc.getLifeCycleTemplate().length() > 0)
    {
      LifeCycleTemplateIfc lifeCycleIfc = (LifeCycleTemplateIfc)pService.refreshInfo(partIfc.getLifeCycleTemplate());
      result[6] = lifeCycleIfc.getLifeCycleName();
    }
    else
    {
      result[6] = "";
    }
    result[7] = partIfc.getProducedBy().getDisplay();
    if(partIfc.getProjectName() != null && partIfc.getProjectName().length() > 0)
    {
      result[8] = partIfc.getProjectName();
    }
    else
    {
      result[8] = "";
    }
    result[9] = partIfc.getPartType().getDisplay();
    if(partIfc.getLifeCycleState() != null)
    {
      result[10] = partIfc.getLifeCycleState().getDisplay();
    }
    else
    {
      result[10] = "";
    }
    //��Ҫ�ȰѸ��ַ���ת����ö�����ͣ��ٻ�ȡ�����ַ���:
    String tempWorkableState = partIfc.getWorkableState();
    if(tempWorkableState != null && tempWorkableState.length() > 0)
    {
        //����(3)20081014 ��ǿ�޸� begin �޸�ԭ��:���մ����㲿��ʱ������״̬Ӧ����ʾΪ��δ���롱��
//      WorkInProgressState wState = WorkInProgressState.toWorkInProgressState(tempWorkableState);
//      result[11] = wState.getDisplay();
      WorkInProgressHelper workInProgressHelper=new WorkInProgressHelper();
      result[11] = workInProgressHelper.getStatus(partIfc);
      //����(3)20081014 ��ǿ�޸� end
    }
    else
    {
      result[11] = "";
    }

    //��ô�����
    String creater = partIfc.getIterationCreator();
    result[12] = getUserNameByID(creater);
    //��ø�����
    String modifier = partIfc.getIterationModifier();
    result[13] = getUserNameByID(modifier);
    result[14] = partIfc.getCreateTime().toString();
    result[15] = partIfc.getModifyTime().toString();
    result[16] = partIfc.getIterationNote();
        //CCBegin by liunan 2008-07-24
    if(partIfc.getColorFlag())
    {
      result[17] = "��";
    }
    else
    {
        result[17] = "";
    }
    //CCEnd by liunan 2008-07-24
    //add end
    getCurrentConfigSpec();
    PartDebug.trace(this, "getChineseAttr() end return is String[]");
    return result;
  }


//add by ������ 2004.7.11
  /**
   * ��ȡ�㲿���Ĺ���״̬��Ϣ(������/��XX���/XX�Ĺ�������/�ڸ����ļ���)��
   * @param partInfo QMPartIfc
   * @return String �㲿���Ĺ���״̬��Ϣ��
   * @see QMPartInfo
   */
  public String getPartStatus(QMPartIfc partInfo)
  {
      String partStatusValue = "";
      String resource = "com.faw_qm.part.util.PartResource";
      ResourceBundle resources = null;
      Locale locale = RemoteProperty.getVersionLocale();
      SessionService sService = null;
      WorkInProgressService wipService = null;
      try {
          wipService = (WorkInProgressService) EJBServiceHelper.getService(
              "WorkInProgressService");
      }
      catch (Exception ex) {
          ex.printStackTrace();
          return partStatusValue;
      }
      boolean flag = WorkInProgressHelper.isWorkingCopy( (WorkableIfc)
          partInfo); //�Ƿ��ǹ�������
      if (flag)
      {
          try
          {
              WorkableIfc originalcopy = wipService.originalCopyOf( (
                  WorkableIfc) partInfo); //���ԭ������
              Object aobj1[] = new Object[1];
              if (originalcopy == null) {
                  aobj1[0] = "XXX";
              }
              aobj1[0] = originalcopy.getLocation() + "\\" +
                  ( (QMPartInfo) originalcopy).getPartName();
              partStatusValue = QMMessage.getLocalizedMessage(resource,
                  "workingCopyTitle", aobj1, locale);
          }
          catch (Exception ex) {
              ex.printStackTrace();
              return partStatusValue;
          }
      }
      else if (WorkInProgressHelper.isCheckedOut( (WorkableIfc) partInfo))
      {
          try
          {
              String checkOutByUser = findUserNameByID(partInfo.getLocker());
              if (checkOutByUser != null) {
                  Object aobj2[] = {
                      checkOutByUser};
                  partStatusValue = QMMessage.getLocalizedMessage(resource,
                      "partCheckedOutBy", aobj2, locale);
              }
          }
          catch (Exception ex) {
              ex.printStackTrace();
              return partStatusValue;
          }
      }
      else
      {
          try
          {
              FolderService folderService = (FolderService) EJBServiceHelper.
                  getService("FolderService");
              boolean isPersonFolder = folderService.inPersonalFolder(
                  partInfo);
              if (isPersonFolder) {
                  partStatusValue = QMMessage.getLocalizedMessage(resource,
                      "inPesrsonFolder", null, locale);
              }
              else {
                  partStatusValue = QMMessage.getLocalizedMessage(resource,
                      "partCheckedIn", null, locale);
              }
          }
          catch (Exception ex) {
              ex.printStackTrace();
              return partStatusValue;
          }
      }

      return partStatusValue;
  }

  /**
   * ��bsoid��������ݣ�����ĵ�����
   * @param docBsoID String
   * @throws QMException
   * @return Vector ��ȡ������(HolderAndContentInfo)����
   */
  public static Vector findAllFileByID(String docBsoID)
      throws QMException
  {
    PartDebug.trace("findAllFileByID(String BsoID) begin... BsoID is"  +docBsoID);
    Vector vector = null;
    try
    {
      PersistService persistService = (PersistService)
              EJBServiceHelper.getService("PersistService");
      ContentHolder doc = (ContentHolder)persistService.refreshBso(docBsoID);
      ContentService contentService = (ContentService)
              EJBServiceHelper.getService("ContentService");
      vector = contentService.getContents(doc);
    }//end try
    catch(Exception e)
    {
        e.printStackTrace();
      throw new QMException(e);
    }//end catch
    if(vector == null)
     vector = new Vector(0);
    PartDebug.trace("findAllFileByID(String BsoID) end... return is "+vector);
    return vector;
  }
  /**
   * �����㲿��id��ȡ��ص�epm�ĵ���Ϣ��
   * @param partBsoID �㲿��id��ʶ
   * @return  String[] ����Ԫ�أ�
   *<br> 0.epm�ĵ���ţ�
   * <br>1.epm�ĵ��汾��
   * <br>2.epm�ĵ����ƣ�
   * <br>3.epm�ĵ��������ϼУ�
   *<br> 4.epm�ĵ���ʹ�õ���������ģ������
   *<br> 5.epm�ĵ�����������
   *<br> 6.epm�ĵ����͵���ʾ����
   *<br> 7.epm�ĵ�����������״̬����
   *<br> 8.epm�ĵ�����ʹ�������״̬����ʾ����
   *<br> 9.epm�ĵ�����Ӧ�ó������͵���ʾ����
   * @throws QMException
   */
  public String[] getEPMChineseAttr(String partBsoID)
      throws QMException
  {
    PartDebug.trace(this, "getChineseAttr(" + partBsoID + "): begin... ");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    EPMDocumentIfc epmDoc = (EPMDocumentIfc)pService.refreshInfo(partBsoID);
    String[] result = new String[11];
    result[0] = epmDoc.getDocNumber();
    result[1] = epmDoc.getVersionValue();
    result[2] = epmDoc.getDocName();
    result[3] = epmDoc.getLocation();
    //������������::����LifeCycleTemplate���BsoID����ȡ��ֵ����
    //�ٻ�ȡֵ����������������֡�
    if(epmDoc.getLifeCycleTemplate() != null && epmDoc.getLifeCycleTemplate().length() > 0)
    {
      LifeCycleTemplateIfc lifeCycleIfc = (LifeCycleTemplateIfc)pService.refreshInfo(epmDoc.getLifeCycleTemplate());
      result[4] = lifeCycleIfc.getLifeCycleName();
    }
    else
    {
      result[4] = "";
    }
    String projectID =epmDoc.getProjectId();
    if(projectID!=null)
    {
      ProjectIfc project = (ProjectIfc)pService.refreshInfo(projectID);
      result[5] = project.getName();
    }
    else
    {
      result[5] = "";
    }
    result[6] = epmDoc.getDocType().getDisplay();
    if(epmDoc.getLifeCycleState() != null)
    {
      result[7] = epmDoc.getLifeCycleState().getDisplay();
    }
    else
    {
      result[7] = "";
    }
    //��Ҫ�ȰѸ��ַ���ת����ö�����ͣ��ٻ�ȡ�����ַ���:
    String tempWorkableState = epmDoc.getWorkableState();
    if(tempWorkableState != null && tempWorkableState.length() > 0)
    {
        //����(3)20081014 ��ǿ�޸� begin �޸�ԭ��:���մ����㲿��ʱ������״̬Ӧ����ʾΪ��δ���롱��
//      WorkInProgressState wState = WorkInProgressState.toWorkInProgressState(tempWorkableState);
//      result[8] = wState.getDisplay();
      WorkInProgressHelper workInProgressHelper=new WorkInProgressHelper();
      result[8] = workInProgressHelper.getStatus(epmDoc);
      //����(3)20081014 ��ǿ�޸� end
    }
    else
    {
      result[8] = "";
    }
    result[9] = epmDoc.getAuthoringApplication().getDisplay();
		UserInfo user=(UserInfo)pService.refreshInfo(epmDoc.getCreator());
    result[10]=user.getUsersName();
    PartDebug.trace(this, "getChineseAttr() end return is String[]");
    return result;
  }

  /**
   * �����û�BsoID��ȡ�û���ȫ�ơ�
   * @param userID �û���BsoID
   * @return String �û���
   * @throws QMException
   */
  public static String getUserNameByID(String userID)
      throws QMException
  {
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    UserInfo userInfo = (UserInfo)pService.refreshInfo(userID);
    if(userInfo == null)
        return null;
    //��Ϊ��ʾ�û�ȫ�ƣ�liun   2005.06.14
    String userName = userInfo.getUsersDesc();
    return userName;
  }

  /**
   * �����㲿����BsoID��ȡ���㲿���ϵ����е���Ч�Է��������ƣ����ͣ���Ч��ֵ��Χ��
   * 
   * @param partBsoID �㲿����BsoID��
   * @return Vector ���ص�Vector�е�ÿ��Ԫ�ض���String[]��
   * <br>String[0]:��Ч�Է������ƣ����Ĭ��������Ч�ԣ����Բ�ָ����Ч�Է�������
   * <br>String[1]:��Ч�Է������ͣ����û����Ч�Է��������ƣ���Ĭ��Ϊ"������Ч��"��
   * <br>String[2]:��Ч��ֵ��Χ��
   * @throws QMException
   */
  public Vector getLookEff(String partBsoID)
      throws QMException
  {
    return getLookEff(partBsoID, true);
  }

  /**
   * �����㲿����BsoID��ȡ���㲿���ϵ����е���Ч�Է��������ƣ����ͣ���Ч��ֵ��Χ��
   * 
   * @param partBsoID String �㲿����BsoID��
   * @param isacl boolean
   * @throws QMException
   * @return Vector ���ص�Vector�е�ÿ��Ԫ�ض���String[]��
   * <br>String[0]:��Ч�Է������ƣ����Ĭ��������Ч�ԣ����Բ�ָ����Ч�Է�������
   * <br>String[1]:��Ч�Է������ͣ����û����Ч�Է��������ƣ���Ĭ��Ϊ"������Ч��"��
   * <br>String[2]:��Ч��ֵ��Χ��
   */
  public Vector getLookEff(String partBsoID, boolean isacl)
      throws QMException
  {
    PartDebug.trace(this, "getLookEff() begin......");
    //�ȵ���EnterprisePartService�еķ���getEffVector(QMPartInfo):Vector
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    EffService effService = (EffService)EJBServiceHelper.getService("EffService");
    EffectivityManageableIfc partInfo = (EffectivityManageableIfc)pService.refreshInfo(partBsoID);
    Vector effGroups = PartServiceRequest.getEffVector((WorkableIfc)partInfo, isacl);
    Vector resultVector = new Vector();
    //��effGroups�е����е�EffGroup�������ѭ������װ������ϣ�
    for(int i = 0; i < effGroups.size(); i++)
    {
      EffGroup effGroup = (EffGroup)effGroups.elementAt(i);
      EffContextIfc effContextIfc = effGroup.getEffContext();
      String[] tempString = new String[3];
      //���effContextIfcΪ�յĻ���һ�������ڵģ�
      if(effContextIfc == null)
      {
        tempString[0] = "";
        tempString[1] = EffectivityType.DATE.getDisplay(); //������Ч�Է���
        String dateRange = effGroup.getRange();
        tempString[2] = effService.getDateType(dateRange);
        resultVector.addElement(tempString);
      }
      else
      {
        if(effContextIfc instanceof QMConfigurationItemIfc)
        {
          QMConfigurationItemIfc configItemIfc = (QMConfigurationItemIfc)effContextIfc;
          tempString[0] = configItemIfc.getConfigItemName();
          //�����type�п���Ϊ�գ�
          EffectivityType type = configItemIfc.getEffectivityType();
          if(type != null)
          {
            tempString[1] = type.getDisplay();
          }
          else
          {
            //��Ҫ����EffGroup�б���ľ������Ч�����������������tempString[1]���и�ֵ��
            //��Ҫ����effGroup�����е�type:String����ַ�������
            //effType�ľ�������ͽ���ָ����
            //��δ�����ÿ��Բ�д����!!!!
            String typeOfEffGroup = effGroup.getType();
            if(typeOfEffGroup.equals("QMDatedEffectivity"))
            {
              tempString[1] = (EffectivityType.DATE).getDisplay();
            }
            else
            {
              if(typeOfEffGroup.equals("QMSerialNumEffectivity"))
              {
                tempString[1] = (EffectivityType.SERIAL_NUM).getDisplay();
              }
              else
              {
                if(typeOfEffGroup.equals("QMLotEffectivity"))
                {
                  tempString[1] = (EffectivityType.LOT_NUM).getDisplay();
                }
              }
            }
            //effType = "";
          }
          //tempString[1] = configItemIfc.getEffectivityType().
          //    getDisplay();
          if(tempString[1].equals(EffectivityType.DATE.getDisplay()))
          {
            String dateRange = effGroup.getRange();
            tempString[2] = effService.getDateType(dateRange);
          }
          else
          {
            tempString[2] = effGroup.getRange();
          }
          resultVector.addElement(tempString);
        }
        //end if(effContextIfc instanceof QMConfigurationItemIfc)
      }
      //end if and else (effContextIfc == null)
    }
    //end for(int i=0; i<effGroups.size(); i++)
    PartDebug.trace(this, "getLookEff() end. return is Vector");
    return resultVector;
  }


  /**********************  add 2003-05-14 (liyf) ****************************/
  /**
   * ����ָ���ṹ�пɱ������㲿���滻�����ô˷�����
   * @return vector:ÿ��Ԫ��Ϊһ����ʮ���ַ��������飬˳���¼��
   * ��ָ���ṹ�пɱ��滻���㲿������Ϣ(number  name)��
   * ������QMPart����Ϣ(number  name version view)��BsoID(masterBsoID partBsoID)
   * ��ͼ�꣨part��master��StandardIconName����
   * @param masterID String
   * @throws QMException
   */
  public Vector getSubstitutes(String masterID)
      throws QMException
  {
    try
    {
      PartDebug.trace(this, "getSubstitutes(masterID):" + masterID);
      PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
      QMPartMasterInfo master = (QMPartMasterInfo)pService.refreshInfo(masterID);
      Collection collection = pService.navigateValueInfo(master, "uses", "PartUsageLink", true);
      //���part����������С�汾��part���󼯺ϣ�����е�����С�汾�����ڼ��
      //״̬���ж��Ƿ񱻵�ǰ�û����������ǵ�ǰ�û�����ģ����ع���������
      //����������û�����ģ����ع���ԭ��
      Vector vector1 = new Vector();
      if(collection != null && collection.size() > 0)
      {
        Iterator iterator = collection.iterator();
        Vector paramVector = new Vector();
        while(iterator.hasNext())
        {
          paramVector.addElement(iterator.next());
        }
        vector1 = getVersionParts(paramVector);
      }

      PartUsageLinkInfo usageLink = new PartUsageLinkInfo();
      String usageLinkID = "";
      QMPartInfo part = new QMPartInfo();
      Collection collection1 = null;
      Vector vector = new Vector();
      for(int i = 0; i < vector1.size(); i++)
      {
        //    usageLink = (PartUsageLinkInfo) iterator.next();
        //    part = (QMPartInfo) usageLink.getUsedBy();
        //    usageLinkID = usageLink.getBsoID();
        part = (QMPartInfo)vector1.elementAt(i);
        //����part��master��ù�������󼯺�
        Collection col2 = pService.findLinkValueInfo("PartUsageLink", part, "usedBy", master);
        Iterator iterator = col2.iterator();
        //����part��masterֻ��Ψһȷ��һ��PartUsageLinkInfo����
        if(iterator.hasNext())
        {
          usageLink = (PartUsageLinkInfo)iterator.next();
          usageLinkID = usageLink.getBsoID();
        }
        //����ָ���Ľṹ����滻���ļ���

        //����ָ���Ľṹ����滻���ļ���
        //Ӧ���Ǹ���PartUsageLinkID->PartUsageLinkInfo->���ݹ�����
        //PartSubstituteLink��һ�ߵĶ���PartUsageLinkInfo������һ�ߵ�
        //QMPartMasterInfo�ļ��ϡ�
        collection1 = getSubstitutesPartMasters(usageLinkID);
        if(collection1 != null && collection.size() > 0)
        {
          Iterator iterator1 = collection1.iterator();
          QMPartMasterInfo master1 = new QMPartMasterInfo();
          //ͨ��ѭ������ָ���ṹ�пɱ��滻���㲿������Ϣ(QMPartMaster)��
          //������QMPart����Ϣ�����ַ�������a[8]��
          while(iterator1.hasNext())
          {
            master1 = (QMPartMasterInfo)iterator1.next();
            String[] a = new String[10];
            a[0] = master1.getPartNumber();
            a[1] = master1.getPartName();
            a[2] = part.getPartNumber();
            a[3] = part.getPartName();
            a[4] = part.getVersionValue();
            a[5] = part.getViewName();
            a[6] = master1.getBsoID();
            a[7] = part.getBsoID();
            a[8] = part.getStandardIconName();
            a[9] = master1.getStandardIconName();
            vector.add(a);
          }
        }
      }
      return vector;
    }
    catch(QMException e)
    {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * ����ָ���ṹ���������㲿�����滻�������ô˷�����
   * @param masterID �㲿������Ϣ��bsoID��
   * @return vector:ÿ��Ԫ��Ϊһ����ʮ���ַ��������飬˳���¼�ˡ�
   * ��ָ���ṹ�пɱ��滻���㲿������Ϣ(number  name)��
   * ������QMPart����Ϣ(number  name version view����BsoID(masterBsoID partBsoID)
   * ͼ��(part��master��standardIconName)��
   * @throws QMException
   */
  public static Vector getSubstitutedBy(String masterID)
      throws QMException
  {
    try
    {
      PartDebug.trace("getSubstituteBy() begin...");
      PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
      //����QMPartMaster��BsoID��ȡQMPartMasterInfo��
      QMPartMasterInfo valueInfo = (QMPartMasterInfo)pService.refreshInfo(masterID);
      //ͨ��master�����ҵ�PartUsageLinkInfo�Ķ��󼯺�
      Collection collection = pService.navigateValueInfo(valueInfo, "substitutes", "PartSubstituteLink", true);
      if(collection != null && collection.size() > 0)
      {
        Iterator iterator = collection.iterator();
        PartUsageLinkInfo usageLink = null;
        QMPartInfo part = null;
        QMPartMasterInfo master = null;
        Vector vector = new Vector();
        //����collection�е�����PartUsageLink��������е�QMPartInfo��
        Vector partVector = new Vector();
        //�������е�PartUsageLinkInfo:
        Vector usageLinkVector = new Vector();
        //��part��usageLink�ֱ����partVector��usageLinkVector��
        while(iterator.hasNext())
        {
          usageLink = (PartUsageLinkInfo)iterator.next();
          part = (QMPartInfo)usageLink.getUsedBy();
          partVector.add(part);
          usageLinkVector.add(usageLink);
        }
        //���part����������С�汾��part���󼯺ϣ�����е�����С�汾�����ڼ��
        //״̬���ж��Ƿ񱻵�ǰ�û����������ǵ�ǰ�û�����ģ����ع���������
        //����������û�����ģ����ع���ԭ��
        Vector partVector1 = new Vector();
        partVector1 = getVersionParts(partVector);
        Vector partIDVector = new Vector();
        for(int i = 0; i < partVector1.size(); i++)
        {
          String partID = ((QMPartInfo)partVector1.elementAt(i)).getBsoID();
          if(!partIDVector.contains(partID))
          {
            partIDVector.addElement(partID);
          }
        }
        //��ã���usageLinkVector�е����е�Ԫ�ؽ��й��ˣ������е�����ͬ��
        //QMPartMasterID����ЩPartUsageLinkInfo��ֻ����һ����
        for(int i = 0; i < usageLinkVector.size(); i++)
        {
          part = (QMPartInfo)((PartUsageLinkInfo)(usageLinkVector.elementAt(i))).getUsedBy();
          //��Ҫ�ж�part�Ƿ���partVector1�У�
          if(partIDVector.contains(part.getBsoID()))
          {
            master = (QMPartMasterInfo)((PartUsageLinkInfo)(usageLinkVector.elementAt(i))).getUses();
            String[] a = new String[10];
            a[0] = master.getPartNumber();
            a[1] = master.getPartName();
            a[2] = part.getPartNumber();
            a[3] = part.getPartName();
            a[4] = part.getVersionValue();
            a[5] = part.getViewName();
            a[6] = master.getBsoID();
            a[7] = part.getBsoID();
            a[8] = part.getStandardIconName();
            a[9] = master.getStandardIconName();
            vector.add(a);
          }
        }
        PartDebug.trace("getSubstitutedBy()end " + "return is Vector::");
        //��Ҫ��vector�е�����Ԫ�ؽ��й��ˣ�
        Vector resultVector = new Vector();
        for(int i = 0; i < vector.size(); i++)
        {
          boolean flag = false;
          String[] stringArray = (String[])vector.elementAt(i);
          for(int j = 0; j < resultVector.size(); j++)
          {
            String[] stringArray2 = (String[])resultVector.elementAt(j);
            if(stringArray[6].equals(stringArray2[6]) && stringArray[7].equals(stringArray2[7]))
            {
              flag = true;
              break;
            }
          }
          if(flag == false)
          {
            resultVector.addElement(stringArray);
          }
        }
        return resultVector;
      }
      else
      {
        PartDebug.trace("getSubstitutedBy()end " + "return is null!!!!!!!!!!!!!!!!!!!!!!!!");
        return new Vector();
      }
      //end if and else (collection != null && collection.size() > 0)
    }
    //end try
    catch(QMException e)
    {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * ���parts����������С�汾��part�����������С�汾�����ڼ��
   * ״̬���ж��Ƿ񱻵�ǰ�û����������ǵ�ǰ�û�����ģ����ع���������
   * ����������û�����ģ����ع���ԭ����
   * @param parts Vector �㲿��ֵ���󼯺�
   * @return Vector �����������Ҿ������˺���㲿��(QMPartInfo)����
   * @throws QMException
   */
  protected static Vector getVersionParts(Vector parts)
      throws QMException
  {
    PartDebug.trace("getVersionParts()----- begin");
    Vector resultVector = new Vector();
    //Vector masterVector = new Vector();
    QMPartInfo part = null;
    QMPartMasterInfo master = null;
    //   Iterator iterator=collection.iterator();
    //   while(iterator.hasNext())
    //��parts�����е�ÿ��QMPartInfo�����жϣ����ҳ���QMPartMasterInfo���ٸ���
    //��QMPartMasterInfo��ȡ���еĸ�QMPartMasterInfo�����QMPartInfo�ļ��ϣ�
    //�����ҵ���߼�����QMPartInfo�����е�����С�汾���㲿��ֵ����
    for(int i = 0; i < parts.size(); i++)
    {
      Object obj = parts.elementAt(i);
      if(obj instanceof QMPartInfo)
      {
        part = (QMPartInfo)obj;
        master = (QMPartMasterInfo)part.getMaster();
        try
        {
          //���ð汾���Ʒ���
          VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
          //���master�����д�汾�Ķ�Ӧ������С�汾���󼯺�
          //��Ҫ�Ըü����е����е��㲿��ֵ�������ѭ���жϡ�
          Collection col = vcService.allVersionsOf(master);
          Iterator iterator1 = col.iterator();
          //�����е�col�е�Ԫ�ؽ���ѭ����
          while(iterator1.hasNext())
          {
            //������°汾��part����
            part = (QMPartInfo)iterator1.next();
            //���ûỰ����
            SessionService sService = (SessionService)EJBServiceHelper.getService("SessionService");
            //��õ�ǰ�û�
            UserInfo user = (UserInfo)sService.getCurUserInfo();
            //����wip����
            WorkInProgressService wipService = (WorkInProgressService)EJBServiceHelper.getService("WorkInProgressService");
            //�ж�part�Ƿ��ڼ��״̬
            boolean flag = WorkInProgressHelper.isCheckedOut(part);
            //��������
            if(flag)
            {
              //�ж�part�Ƿ񱻵�ǰ�û����
              boolean flag1 = WorkInProgressHelper.isCheckedOut(part, user);
              //����Ǳ���ǰ�û�����������ж�part�Ƿ��ǹ�������
              //������ǣ���ù�������
              if(flag1)
              {
                boolean flag2 = WorkInProgressHelper.isWorkingCopy(part);
                if(!flag2)
                {
                  //���part����Ĺ�������
                  part = (QMPartInfo)wipService.workingCopyOf(part);
                }
              }
              //����������û��������ù���ԭ��
              else
              {
                  boolean iscopy = WorkInProgressHelper.isWorkingCopy(part);
               if(iscopy)
                part = (QMPartInfo)wipService.originalCopyOf(part);
              }
            }
            //���resultVector�в�����part����part����vector��
            //��Ҫ�ж�part�Ƿ���vector�У�
            String partName = part.getPartName();
            String partNumber = part.getPartNumber();
            String versionValue = part.getVersionValue();
            //��falgDesideΪfalse��ʱ�򣬰ѵ�ǰ��part�ӵ����Ľ��vector��
            //���򣬲���
            boolean flagDeside = false;
            for(int m = 0; m < resultVector.size(); m++)
            {
              QMPartInfo tempPartInfo = (QMPartInfo)resultVector.elementAt(m);
              //���vector�Ѿ����˸��㲿�������ӣ����򣬼ӣ�
              boolean flag01 = partName.equals(tempPartInfo.getPartName());
              boolean flag02 = partNumber.equals(tempPartInfo.getPartNumber());
              boolean flag03 = versionValue.equals(tempPartInfo.getVersionValue());
              if(flag01 && flag02 && flag03)
              {
                flagDeside = true;
                break;
              }
            }
            if(flagDeside == false)
            {
              resultVector.add(part);
            }
          }
          //end while (iterator1.hasNext())
        }
        //end try
        catch(QMException e)
        {
          String message = e.getMessage();
          e.printStackTrace();
          throw e;
        }
        //end catch(QMException e)
      }
      //end if (obj instanceof QMPartInfo)
    }
    //end for (int i = 0; i < parts.size(); i++)
    return resultVector;
  }

 
 
  //2003/11/05 webҳ��Ӧ�ø���part�ľ���״̬����ʾ��ͼ��
  /**
   * ����part��bsoID�����Ӧ״̬��ͼ�ꡣ
   * @param bsoID String
   * @throws QMException
   * @return String ͼ���·��
   */ 
  public String getPartIconByID(String bsoID)
      throws QMException
  {
    if(bsoID == null || bsoID.equals(""))
    {
      return null;
    }
    else
    {
      Object obj = getObjectForID(bsoID);
      //Begin CR2
      if(obj != null && obj instanceof GenericPartIfc)
      {
			String s = "images/genericPart.gif";
			GenericPartIfc workable = (GenericPartIfc) obj;
			String state = workable.getWorkableState();
			//ԭ��ͼ���ʽ
			if (state.equals("c/o")) {
				s = "images/genericPart.gif";				
			}
			//��׼ͼ���ʽ
			if (state.equals("c/i")) {
				s = "images/genericPart.gif";				
			}
			//����ͼ���ʽ
			if (state.equals("wrk")) {
				s = "images/genericPart_workingIcon.gif";
			}
			return s;
      }
      //End CR2
      if(obj != null && obj instanceof WorkableIfc)
      {
        String s = "images/part.gif";
        WorkableIfc workable = (WorkableIfc)obj;
        //��׼ͼ���ʽ
        String state = workable.getWorkableState();
        //ԭ��ͼ���ʽ
        if(state.equals("c/o"))
        {
          s = "images/part_originalIcon.gif";
          //��׼ͼ���ʽ
        }
        if(state.equals("c/i"))
        {
          s = "images/part.gif";
          //����ͼ���ʽ
        }
        if(state.equals("wrk"))
        {
          s = "images/part_workingIcon.gif";
        }
        return s;
      }

      //2003/12/16
      else
      {
        if(obj instanceof QMPartMasterIfc)
        {
          String s = "images/partMaster.gif";
          return s;
        }
        //Begin CR2
        if(obj instanceof GenericPartMasterIfc)
        {
        	String s = "images/genericPartMaster.gif";
	        return s;
        }
        //End CR2
        else
        {
          return null;
        }
      }
    }
  }

  //���������  2004/02/04   �Եó�



  //(add by ������)
  /**
   * ��ȡ��һ�ε����ù淶��
   * @return Object[] ���ù淶Ԫ����Ϣ:
   *<br> 0.�Ƿ��Ǳ�׼���ù淶��1����ʾ�ǣ�
   *<br> 1.�ڱ�׼���ù淶�µ���ͼ����
   *<br> 2.��׼���ù淶����������״̬
   *<br> 3.��׼���ù淶���Ƿ�����������ϼ��1����ʾ�ǣ�
   *<br> 4.�Ƿ��ǻ������ù淶��1����ʾ�ǣ�
   *<br> 5.��������
   *<br> 6.�Ƿ�����Ч�����ù淶��1����ʾ�ǣ�
   *<br> 7.��Ч�����ù淶�µ���ͼ���ƣ�
   *<br> 8.��Ч�����ù淶�µ�����������
   *<br> 9.��Ч�����ù淶�µ���Ч��������ʾ���ƣ�
   *<br> 10.��Ч�����ù淶�µ���Ч�Ե�λ
   * @throws QMException
   */
  public Object[] getOldConfigSpec()
      throws QMException
  {
    PartConfigSpecIfc config = getCurrentConfigSpec();
    return setCurrentConfigSpec(config);
  }

  /**
   * ��ȡ��ǰ�����ù淶������û����״ε�½ϵͳ������Ĭ�ϵġ���׼�����ù淶��
   * @throws QMException ʹ��ExtendedPartServiceʱ���׳���
   * @return PartConfigSpecIfc ��׼���ù淶��
   * @see PartConfigSpecInfo
   */
  public PartConfigSpecIfc getCurrentConfigSpec()
      throws QMException
  {
   

      PartConfigSpecIfc configSpec = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();

      if(configSpec == null)
      {
        configSpec = new PartConfigSpecInfo();
        configSpec.setStandardActive(true);
        configSpec.setBaselineActive(false);
        configSpec.setEffectivityActive(false);
        PartStandardConfigSpec partStandardConfigSpec = new PartStandardConfigSpec();
        ViewService vs = (ViewService)EJBServiceHelper.getService("ViewService");
        ViewObjectIfc viewObjectIfc=vs.getView("������ͼ");
        partStandardConfigSpec.setViewObjectIfc(viewObjectIfc);
        partStandardConfigSpec.setLifeCycleState(null);
        partStandardConfigSpec.setWorkingIncluded(true);
        configSpec.setStandard(partStandardConfigSpec);
        return PartServiceRequest.savePartConfigSpecIfc(configSpec);
      }
      else
        return configSpec;
   
  }

  /**
   * �����ù淶��Ԫ����Ϣ���õ���������У��Ա����ݿͻ���ʾ��
   * @param configSpec PartConfigSpecIfc ���ù淶��
   * @return Object[] ���ù淶Ԫ����Ϣ��
   *<br> 0.�Ƿ��Ǳ�׼���ù淶��1����ʾ�ǣ�
   *<br> 1.�ڱ�׼���ù淶�µ���ͼ����
   *<br> 2.��׼���ù淶����������״̬
   *<br> 3.��׼���ù淶���Ƿ�����������ϼ��1����ʾ�ǣ�
   *<br> 4.�Ƿ��ǻ������ù淶��1����ʾ�ǣ�
   *<br> 5.��������
   *<br> 6.�Ƿ�����Ч�����ù淶��1����ʾ�ǣ�
   *<br> 7.��Ч�����ù淶�µ���ͼ���ƣ�
   *<br> 8.��Ч�����ù淶�µ�����������
   *<br> 9.��Ч�����ù淶�µ���Ч��������ʾ���ƣ�
   *<br> 10.��Ч�����ù淶�µ���Ч�Ե�λ
   */
  private Object[] setCurrentConfigSpec(PartConfigSpecIfc configSpec)
  {
    Object[] obj = new Object[12];
    if(configSpec.getStandardActive() == true)
      obj[0] = "1";
    if(configSpec.getStandard() != null)
    {
      if(configSpec.getStandard().getViewObjectIfc() != null)
        obj[1] = configSpec.getStandard().getViewObjectIfc().getViewName();
      LifeCycleState state = configSpec.getStandard().getLifeCycleState();
      if(state != null)
        obj[2] = state.getDisplay();
      if(configSpec.getStandard().getWorkingIncluded() == true)
        obj[3] = "1";
    }
    if(configSpec.getBaselineActive() == true)
      obj[4] = "1";
    ManagedBaselineIfc baseline = (ManagedBaselineIfc)configSpec.getBaseline().getBaselineIfc();
    if(baseline != null)
      obj[5] = baseline.getBaselineName();
    if(configSpec.getEffectivityActive() == true)
      obj[6] = "1";
    PartEffectivityConfigSpec effectivity = configSpec.getEffectivity();
    if(effectivity != null)
    {
      if(effectivity.getViewObjectIfc() != null)
        obj[7] = effectivity.getViewObjectIfc().getViewName();
      QMConfigurationItemIfc qmConfigurationItem = effectivity.getEffectiveConfigItemIfc();
      if(qmConfigurationItem != null)
        obj[8] = qmConfigurationItem.getConfigItemName();
      if(effectivity.getEffectivityType() != null)
        obj[9] = effectivity.getEffectivityType().getDisplay(Locale.CHINA);
      //20080401 zhangq begin:��û����Ч�Է���ʱ����Ч������Ϊ�������͡�
      if(qmConfigurationItem == null) 
          obj[9] =EffectivityType.DATE.getDisplay(Locale.CHINA);
      //20080401 zhangq end
      obj[10] = effectivity.getEffectiveUnit();
    }
    obj[11] = configSpec.getBsoID();
    return obj;
  }

  /**
   * �������е���������״̬��
   * @return Vector ��������״̬(String)����
   */
  public Vector queryState()
  {
    PartDebug.trace(this, "queryState() begin********");
    Vector result = new Vector();
    HashMap hashMap = new HashMap();
    LifeCycleState[] State_type;
    State_type = LifeCycleState.getLifeCycleStateSet(Locale.CHINA);
    for(int i = 0; i < State_type.length; i++)
    {
      hashMap.put(State_type[i], State_type[i].getDisplay(RemoteProperty.getVersionLocale()));
    }
    for(int i = 0; i < hashMap.size(); i++)
    {
      result.addElement(hashMap.get(State_type[i]));
    }
    return result;
  }

  /**
   * �������е���Ч�����͡�
   * @return Collection ��Ч�����ͣ�String������
   */
  public Vector queryEffType()
  {
    Vector result = new Vector();
    EffectivityType[] effType = EffectivityType.getEffectivityTypeSet(Locale.CHINA);
    for(int i = 0; i < effType.length; i++)
    {
      String temp = effType[i].getDisplay();
      result.addElement(temp);
    }
    return result;
  }

  /**
   * �������������Ļ�׼�ߡ�
   * @param name ��׼������
   * @return Collection ���������Ļ�׼�ߣ�ManagedBaselineInfo�����ϡ�
   * @throws QMException
   **/
  public Collection queryBaseline(String name)
      throws QMException
  {
    QMQuery query = new QMQuery("ManagedBaseline");
    int length1 = name.trim().length();
    if(name != null && length1 != 0)
    {
      PartDebug.trace(this, "queryBaseline method the para is   " + name + "and  the name length is " + length1);
      String aname = replacewildcard(name);
      boolean flag1 = aname.equals(name);
       QueryCondition qc=null ;
      if(name.indexOf("%")!=-1)
     qc= new QueryCondition("baselineName",  QueryCondition.LIKE, aname);
     else
     qc= new QueryCondition("baselineName", flag1 ? QueryCondition.EQUAL : QueryCondition.LIKE, aname);
      query.addCondition(qc);
    }
    PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
    return ps.findValueInfo(query);
  }

  /**
   * ����������������Ч�������
   * @param effname ��Ч�����ơ�
   * @param efftype ��Ч�����͡�
   * @return ������������Ч��(QMConfigurationItemInfo)���ϡ�
   * @throws QMException
   */
  public Collection queryEffectivity(String effname, String efftype)
      throws QMException
  {
    PartDebug.trace(this, "queryEffectivity(String effname,String efftype) begin.............. ");
    QMQuery query = new QMQuery("QMConfigurationItem");
    boolean flag = false;
    if(effname != null && effname.trim().length() != 0)
    {
      String name = replacewildcard(effname);
      boolean flag1 =(effname.indexOf("%")==-1)&&( name.equals(effname));
      QueryCondition qc = new QueryCondition("configItemName", flag1 ? QueryCondition.EQUAL : QueryCondition.LIKE, name);
      query.addCondition(qc);
      flag = true;
    }
    if(efftype != null && efftype.trim().length() != 0)
    {
      String type = replacewildcard(efftype);
      boolean flag1 = type.equals(efftype);
      QueryCondition qc = new QueryCondition("effectivityTypeStr", flag1 ? QueryCondition.EQUAL : QueryCondition.LIKE, type);
      if(flag)
      {
        query.addAND();
      }
      query.addCondition(qc);
    }
    PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
    return ps.findValueInfo(query);
  }

  /**
   * ������Ӹ�����ַ��滻��
   * @param query String
   * @return String
   **/
  private String replacewildcard(String query)
  {
    PartDebug.trace(this, "replacewildcard(String query) of com.faw_qm.PartServiceHelper begin********");
    query = query.replace('*', '%');
    query = query.replace('\\', '%');
    query = query.replace('?', '%');
    query = query.replace('_', '%');
    return query;
  }

  /**
   * �������е���ͼ���ơ�
   * @return Collection ��ͼֵ����(ViewObjectInfo)����
   * @throws QMException
   */
  public Collection queryViewName()
      throws QMException
  {
    QMQuery query = new QMQuery("ViewObject");
    PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
    return ps.findValueInfo(query);
  }
 /**
  * �����û���id��ȡ�û�����ʾ���ơ�
  * @param userID
  * @return �û���(String)
  */
  public String findUserNameByID(String userID)
  {
    String userName = null;
    if(userID != null)
    {
      try
      {
        PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
        ActorIfc userInfo = (ActorIfc)persistService.refreshInfo(userID, false);
        userName = userInfo.getUsersDesc();
        if(userName == null || userName.trim().length() == 0)
        {
          userName = userInfo.getUsersName();
        }
      }
      catch(Exception e)
      {
          e.printStackTrace();
      } //end catch
    }
    return userName;
  }

  /**
   * �����������Գ����ߵ�id,�����iba����ֵ�ȡ�
   * @param ibaHolderBsoID String
   * @return Vector ���е�Ԫ��Ϊ��ά�ַ�������,��һά��Ÿ�iba����ֵ�����Զ��������,
   * �ڶ�ά���iba���Ե�ֵ��
   */
  public Vector getIBANameAndValue(String ibaHolderBsoID)
  {
    IBAHolderIfc ibaHolderIfc = null;
    try {
      PersistService pService = (PersistService) EJBServiceHelper.getService(
          "PersistService");
       ibaHolderIfc = (IBAHolderIfc) pService.refreshInfo(ibaHolderBsoID);
       IBAValueService ibaService = (IBAValueService)EJBServiceHelper.getService("IBAValueService");
       //CCBegin by liunan 2009-01-14 ����epm���������Ե������棬���ӹ���part�л�ȡ��
       //��������:
       /*if(ibaHolderIfc instanceof EPMDocumentIfc)
       {
           EPMDocumentIfc epm = (EPMDocumentIfc) ibaHolderIfc;
           ibaHolderIfc = getLinkedPart(epm);
           if (ibaHolderIfc==null)
           {
             return new Vector();
           }
       }*/
       //CCEnd by liunan 2009-01-14
       ibaHolderIfc = (IBAHolderIfc)ibaService.refreshAttributeContainer(ibaHolderIfc,null,null,null);
    }
    catch (ServiceLocatorException ex) {
      ex.printStackTrace();
    }
    catch (QMException ex)
    {
        ex.printStackTrace();
    }
    DefaultAttributeContainer container = (DefaultAttributeContainer)ibaHolderIfc.getAttributeContainer();
    if(container == null)
      container = new DefaultAttributeContainer();
    AbstractValueView[] values = container.getAttributeValues();
    Vector retVal = new Vector();
    for(int i = 0; i < values.length; i++)
    {
      String[] nameAndValue = new String[2];
      AbstractValueView value = values[i];
      AttributeDefDefaultView definition = value.getDefinition();
      nameAndValue[0] = definition.getDisplayName();
      //modify by liun 2005.04.21
      if(value instanceof AbstractContextualValueDefaultView)
      {
          MeasurementSystemCache.setCurrentMeasurementSystem("SI");
          String measurementSystem = MeasurementSystemCache.getCurrentMeasurementSystem();
          //�޸�4 20081028 zhangq begin �޸�ԭ���㲿���ݿͻ��鿴�������λ�ĸ���������������ʾ��λΪ���Զ�������ٶ���Ĭ�ϵ�λ
          String unitStr = "";
          if(value instanceof UnitValueDefaultView)
          {
              UnitDefView definition1 = ((UnitValueDefaultView)value).getUnitDefinition();
              QuantityOfMeasureDefaultView quantityofmeasuredefaultview = definition1.getQuantityOfMeasureDefaultView();
              if (measurementSystem != null)
              {
                  //�õ����Զ���ĵ�λ
                  unitStr = definition1.getDisplayUnitString(
                          measurementSystem);
                  //�õ�������λ�е���ʾ��λ
                  if (unitStr == null)
                  {
                      unitStr = quantityofmeasuredefaultview.
                           getDisplayUnitString(measurementSystem);
                  }
                  //�õ�������λ�е�����
                  if (unitStr == null)
                  {
                      unitStr = quantityofmeasuredefaultview.
                           getDefaultDisplayUnitString(measurementSystem);
                  }
              }

              DefaultUnitRenderer defaultunitrenderer = new
                      DefaultUnitRenderer();

              String ss = "";
              try
              {
                  ss = defaultunitrenderer.renderValue(((UnitValueDefaultView)value).toUnit(), ((UnitValueDefaultView)value).getUnitDisplayInfo(measurementSystem));
              }
              catch (UnitFormatException _ex)
              {
                  _ex.printStackTrace();
              }
              catch (IncompatibleUnitsException _ex)
              {
                  _ex.printStackTrace();
              }

              //String ddd = ((UnitValueDefaultView)value).getUnitDefinition().getDefaultDisplayUnitString(measurementSystem);

              //nameAndValue[1] = ss+ddd;
              //����ֵ�͵�λ�м��ÿո�ֿ�
              nameAndValue[1] = ss+"  "+unitStr;
          }
          //�޸�4 20081028 zhangq end
          else
          {
              nameAndValue[1] = ((AbstractContextualValueDefaultView)
                                 value).
                                getValueAsString();
              //CCBegin SS10
              if(nameAndValue[0].equals("�ĵ���Ϣ")&&nameAndValue[1]!=null&&nameAndValue[1].indexOf("��")!=-1)
              {
              	String otherPartNum = nameAndValue[1].substring(nameAndValue[1].indexOf("��")+1,nameAndValue[1].length());
              	System.out.println("otherPartNum============"+otherPartNum);
              	String otherPartID = "";
              	try
              	{
              		otherPartID = PublishHelper.getPartBsoID(otherPartNum);
              	}
              	catch(Exception e)
              	{
              		e.printStackTrace();
              	}
              	nameAndValue[1] = "<a href=\"Part-Other-PartLookOver-001.screen?bsoID="+otherPartID+"\">"+nameAndValue[1]+"</a>";
              	System.out.println("nameAndValue[1]============"+nameAndValue[1]);
              }
              if(nameAndValue[0].equals("�ĵ���Ϣ")&&nameAndValue[1]!=null&&nameAndValue[1].indexOf("�μ�")!=-1)
              {
              	String otherPartNum = nameAndValue[1].substring(nameAndValue[1].indexOf("�μ�")+2,nameAndValue[1].length());
              	System.out.println("otherPartNum============"+otherPartNum);
              	String otherPartID = "";
              	try
              	{
              		otherPartID = PublishHelper.getPartBsoID(otherPartNum);
              	}
              	catch(Exception e)
              	{
              		e.printStackTrace();
              	}
              	nameAndValue[1] = "<a href=\"Part-Other-PartLookOver-001.screen?bsoID="+otherPartID+"\">"+nameAndValue[1]+"</a>";
              	System.out.println("nameAndValue[1]============"+nameAndValue[1]);
              }
              //CCEnd SS10
          }
      }
      else
      {
          nameAndValue[1] = ((ReferenceValueDefaultView) value).
                            getLocalizedDisplayString();
      }
      retVal.add(nameAndValue);
      nameAndValue = null;
    }
    return retVal;
  }


  /**
   * ��epm�ĵ���ȡ��������㲿����
   * @param doc EPMDocumentIfc epm�ĵ���
   * @throws QMException
   * @return QMPartIfc �㲿������
   * @see QMPartInfo
   * @see EPMDocumentInfo
   */
  private static QMPartIfc getLinkedPart(EPMDocumentIfc doc) throws QMException
  {
    QMPartIfc part = null;
    QMQuery query = new QMQuery("EPMBuildLinksRule");
    QueryCondition condition = new QueryCondition("leftBsoID", "=",
                                                  doc.getBranchID());
    query.addCondition(condition);
    PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
    Vector links = (Vector)pService.findValueInfo(query);
    Vector qr = new Vector();
    for(int i=0;i<links.size();i++)
      qr.add( ( (EPMBuildLinksRuleIfc)links.elementAt(i)).getBuildTarget());
    if (qr != null && qr.size() == 1)
      part = (QMPartIfc) qr.elementAt(0);
    else
    if (qr != null && qr.size() > 1)
    {
      for(int k=0;k<qr.size();k++)
      {
        QMPartIfc temp = (QMPartIfc) qr.elementAt(k);
        if (WorkInProgressHelper.isWorkingCopy(temp))
        {
          part = temp;
          break;
        }
      }
    }
    return part;
  }


  /**
   *����id��ȡepm�ĵ�����
   * @param bsoID String
   * @throws QMException
   * @return EPMDocumentInfo epm�ĵ�����
   * @see EPMDocumentInfo
   */
  public EPMDocumentInfo findDocInfoByID(String bsoID) throws QMException
  {
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    EPMDocumentInfo info = (EPMDocumentInfo)pService.refreshInfo(bsoID);
    return info;
  }
  /**
   * 
   * ������Ϊ��ʷ������
   * @param partID String
   * @return Collection �㲿����ʷ����(QMPartInfo)����
   */
  public Collection findAllPreParts(String partID)
  {
      try
      {
          Collection col = PartServiceRequest.findAllPreParts(partID);
          return col;
      }catch(QMException e)
      {
          e.printStackTrace();
      }
      return null;
  }
  // add by ������
  /**
   *
   * ��õ�һ���������㲿����
   * @param partID String
   * @return Collection �㲿������(QMPartInfo)����
   */
  public Collection findAllSaveAsParts(String partID)
  {
      try
      {
          Collection col = PartServiceRequest.findAllSaveAsParts(partID);
          return col;
      }
      catch (QMException e)
      {
          e.printStackTrace();
      }
      return null;
  }


  /**
   * ��ȡ����������ʷ��¼��
   * @param bsoid String
   * @throws QMException
   * @return Vector ����������ʷ
   * @see LifeCycleServiceHelper#getObjectHistorys(String)
   */
  //xdj �������ڷ���Թ���������֧��
  public Vector getObjectHistorys(String bsoid) throws QMException
  {
      PersistService pService = (PersistService) EJBServiceHelper.getService(
          "PersistService");
      BaseValueIfc info = (BaseValueIfc) pService.refreshInfo(bsoid);
      if (! (info instanceof WorkableIfc))
          return new LifeCycleServiceHelper().getObjectHistorys(info.getBsoID());
      WorkableIfc workable = (WorkableIfc) info;
//      if (WorkInProgressHelper.isWorkingCopy(workable))
//      {
//          WorkInProgressService wipService = (WorkInProgressService)
//              EJBServiceHelper.getService("WorkInProgressService");
//          workable = wipService.originalCopyOf(workable);
//      }
      return new LifeCycleServiceHelper().getObjectHistorys(workable.getBsoID());
  }
  /**
   * ���ԱȽ� 2005.4.19��
   * @param ids String һ���ԡ�:���ָ��㲿��id��ϵ��ִ�
   * @throws QMException
   * @return Vector һ���ִ�����ļ��ϣ�����5������Ԫ�أ�ÿ������Ĵ�Сȡ���ڲ����а���id����������Ϊ
   * <br>title:0."����"��1.�㲿�����+����+"V"+�汾�������Ԫ��ͬ�ˣ�
   * <br>type:0."����"��1.�㲿��������ʾ���������Ԫ��ͬ�ˣ���
   * <br>source:0."��Դ"1.��Դ��ʾ���������Ԫ��ͬ�ˣ���
   * <br>state��0.����������״̬��1.״̬��ʾ���������Ԫ��ͬ�ˣ���
   * <br>content��0.iba��������1.iba�����Ӧ��ֵ
   * ����content���ܰ��������ȡ����iba����Ķ���
   */
  public Vector ibaCompare(String ids) throws QMException
  {
      if(ids == null)
          return null;
      if(ids.indexOf(":")<0)
          throw new QMException("there is no targetPart to be compared!");
      Vector result1 = new Vector();
      String bsoids[] = ids.split(":");
      int length = bsoids.length;
      String RESOURCE = "com.faw_qm.part.util.PartResource";
      PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
      IBAValueService valueService = (IBAValueService) EJBServiceHelper.getService("IBAValueService");
      QMPartIfc part = null;
      QMPartIfc[] parts = new QMPartIfc[length];
      String[] title = new String[length+1];
      String[] type = new String[length+1];
      String[] source = new String[length+1];
      String[] state = new String[length+1];
      title[0] = QMMessage.getLocalizedMessage(RESOURCE,"iba", null);
      type[0] = QMMessage.getLocalizedMessage(RESOURCE,"partType", null);
      source[0] = QMMessage.getLocalizedMessage(RESOURCE,"producedBy", null);
      state[0] = QMMessage.getLocalizedMessage(RESOURCE,"lifeCycleState", null);
      for(int i = 0; i<length; i++)
      {
          part = (QMPartIfc) pService.refreshInfo(bsoids[i]);
          parts[i] = part;
          title[i+1] = part.getPartNumber()+"("+part.getPartName()+") "+" V"+part.getVersionValue();
          type[i+1] = part.getPartType().getDisplay();
          source[i+1] = part.getProducedBy().getDisplay();
          state[i+1] = part.getLifeCycleState().getDisplay();
      }
      result1.addElement(title);
      result1.addElement(type);
      result1.addElement(source);
      result1.addElement(state);

      Hashtable table = new Hashtable();
      HashMap map = new HashMap();
      for(int i = 0; i<length; i++)
      {
          map = valueService.getIBADefinitionAndValues(parts[i]);
          table.put(title[i],map);
      }

      HashMap tempMap = new HashMap();
      Vector ibaNames = new Vector();
      for(int j = 0; j<length; j++)
      {
          tempMap = (HashMap)table.get(title[j]);
          Set deNames = tempMap.keySet();
          Iterator iter = deNames.iterator();
          while(iter.hasNext())
          {
              String ibaName = (String)iter.next();
              if(!ibaNames.contains(ibaName))
                  ibaNames.add(ibaName);
          }
      }
      for(int m = 0; m < ibaNames.size(); m++)
      {
          String definitionName = (String)ibaNames.elementAt(m);
          String content[] = new String[length+1];

          content[0] = definitionName;
          for(int k=0; k<length; k++)
          {
              tempMap = (HashMap)table.get(title[k]);
              if(tempMap.containsKey(definitionName))
              {
                  ArrayList list = (ArrayList) tempMap.get(definitionName);
                  String value = (String)list.get(0);
                  for(int p=1; p<list.size(); p++)
                  {
                      value = value + "�� "+list.get(p);
                  }
                  content[k+1] = value;
              }
              else
                  content[k+1] = "----";
          }
          result1.addElement(content);
      }
      for(int z = 4; z<result1.size(); z++)
      {
          String aa[] = new String[length+1];
          aa = (String[])result1.elementAt(z);
          String name = aa[0];
          QMQuery query = new QMQuery("AbstractAttributeDefinition");
          QueryCondition cond = new QueryCondition("name","=",name);
          query.addCondition(cond);
          Collection col = pService.findValueInfo(query);
          AbstractAttributeDefinitionIfc definition = null;
          if(col != null && col.size() !=0)
          {
              Iterator iter = col.iterator();
              definition = (AbstractAttributeDefinitionIfc)iter.next();
          }
          if(!name.equals(definition.getDisplayName()))
          {
              String cont[] = new String[length+1];
              cont[0] = definition.getDisplayName();
              for(int u = 1; u<length+1; u++)
              {
                  cont[u] = aa[u];
              }
              result1.removeElementAt(z);
              result1.insertElementAt(cont, z);
          }
      }
      return result1;
  }

  /**
   * ��ȡIBA�ȽϽ����
   * @param ids String һ����":"�ָ����㲿��id����ִ�
   * @throws QMException
   * @return Vector Ԫ��ΪString[]���飬String[]Ԫ�أ�
   * 0.iba�������ƣ�����Ԫ��Ϊ��Ӧ��ֵ
   */
  public Vector getIBAAtt(String ids) throws QMException
  {
        if(ids == null)
        {
            return null;
        }
        if(ids.indexOf(":")<0)
        {
            throw new QMException("there is no targetPart to be compared!");
        }
        Vector result = new Vector();
        String bsoids[] = ids.split(":");
        int length = bsoids.length;
        String RESOURCE = "com.faw_qm.part.util.PartResource";
        PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
        IBAValueService valueService = (IBAValueService) EJBServiceHelper.getService("IBAValueService");
        QMPartIfc part = null;
        QMPartIfc[] parts = new QMPartIfc[length];
        String[] title = new String[length+1];
        title[0] = QMMessage.getLocalizedMessage(RESOURCE,"iba", null);

        for(int i = 0; i<length; i++)
        {
            part = (QMPartIfc) pService.refreshInfo(bsoids[i]);
            title[i+1] = part.getPartNumber()+"("+part.getPartName()+") "+" V"+part.getVersionValue();
            parts[i] = part;
        }

        Hashtable table = new Hashtable();
        HashMap map = new HashMap();
        for(int i = 0; i<length; i++)
        {
            map = valueService.getIBADefAndValues(parts[i]);
            table.put(title[i],map);
        }

        HashMap tempMap = new HashMap();
        Vector ibaNames = new Vector();
        for(int j = 0; j<length; j++)
        {
            tempMap = (HashMap)table.get(title[j]);
            Set deNames = tempMap.keySet();
            Iterator iter = deNames.iterator();
            while(iter.hasNext())
            {
                String ibaName = (String)iter.next();
                if(!ibaNames.contains(ibaName))
                {
                    ibaNames.add(ibaName);
                }
            }
        }

        //��ÿ���������ƽ���ѭ����
        for(int m = 0; m < ibaNames.size(); m++)
        {
            String definitionName = (String)ibaNames.elementAt(m);
            String content[] = new String[length+1];

            //���������Ʒ���������λ�������εõ��Ƚϵĸ����汾����ֵ��
            boolean flag1 = false;
            for(int k=0; k<length; k++)
            {
                tempMap = (HashMap)table.get(title[k]);
                ArrayList list = (ArrayList) tempMap.get(definitionName);
                    if (tempMap.containsKey(definitionName))
                    {
                        //list��ʱ��������ΪdefinitionName�ĸ��汾����ֵ�ļ��ϡ�
                        String value = (String) list.get(0);
                        for (int kk = k + 1; kk < length; kk++)
                        {
                            tempMap = (HashMap) table.get(title[kk]);
                            if (tempMap.containsKey(definitionName))
                            {
                                ArrayList list2 = (ArrayList) tempMap.get(
                                        definitionName);
                                if (!list2.isEmpty())
                                {
                                    String value1 = (String) list2.get(0);
                                    if (!value.equals(value1))
                                    {
                                        flag1 = true;
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                flag1 = true;
                                break;
                            }
                        }
                    }
                    else
                    {
                        flag1 = true;
                        break;
                    }
                }
                if (flag1)
                {
                    content[0] = definitionName;
                    for (int k = 0; k < length; k++)
                    {
                        tempMap = (HashMap) table.get(title[k]);
                        if (tempMap.containsKey(definitionName))
                        {
                            ArrayList list = (ArrayList) tempMap.get(definitionName);
                            String value = (String) list.get(0);
                            for (int p = 1; p < list.size(); p++)
                            {
                                value = value + "�� " + list.get(p);
                            }
                            content[k + 1] = value;
                        }
                        else
                        {
                            content[k + 1] = "";
                        }
                    }
                    result.addElement(content);
                }
        }
        for(int z = 0; z<result.size(); z++)
        {
            String aa[] = new String[length + 1];
            aa = (String[]) result.elementAt(z);
            String name = aa[0];
            QMQuery query = new QMQuery("AbstractAttributeDefinition");
            QueryCondition cond = new QueryCondition("name", "=", name);
            query.addCondition(cond);
            Collection col = pService.findValueInfo(query);
            AbstractAttributeDefinitionIfc definition = null;
            if (col != null && col.size() != 0)
            {
                Iterator iter = col.iterator();
                definition = (AbstractAttributeDefinitionIfc) iter.next();
            }
            if (!name.equals(definition.getDisplayName()))
            {
                String cont[] = new String[length + 1];
                cont[0] = definition.getDisplayName();
                for (int u = 1; u < length + 1; u++)
                {
                    cont[u] = aa[u];
                }
                result.removeElementAt(z);
                result.insertElementAt(cont, z);
            }
        }
        return result;
    }
/**
 * ��ȡ�㲿���ı�׼��ԭ��������״̬��ͼ��·����
 * @param obj �㲿��ֵ����
 * @return String ͼ��·����
 * @throws QMException
 */
  public String getPartIconByID(BaseValueIfc obj)
      throws QMException
  {

	  //Begin CR2
	  if(obj != null && obj instanceof GenericPartIfc)
	  {
			String s = "images/genericPart.gif";
			GenericPartIfc workable = (GenericPartIfc) obj;
			String state = workable.getWorkableState();
			//ԭ��ͼ���ʽ
			if (state.equals("c/o")) {
				s = "images/genericPart.gif";				
			}
			//��׼ͼ���ʽ
			if (state.equals("c/i")) {
				s = "images/genericPart.gif";				
			}
			//����ͼ���ʽ
			if (state.equals("wrk")) {
				s = "images/genericPart_workingIcon.gif";
			}
			return s;
	  }
	  //End CR2
      if(obj != null && obj instanceof WorkableIfc)
      {
        String s = "images/part.gif";
        WorkableIfc workable = (WorkableIfc)obj;
        //��׼ͼ���ʽ
        String state = workable.getWorkableState();
        //ԭ��ͼ���ʽ
        if(state.equals("c/o"))
        {
          s = "images/part_originalIcon.gif";
          //��׼ͼ���ʽ
        }
        if(state.equals("c/i"))
        {
          s = "images/part.gif";
          //����ͼ���ʽ
        }
        if(state.equals("wrk"))
        {
          s = "images/part_workingIcon.gif";
        }
        return s;
      }

      //2003/12/16
      else
      {
        if(obj instanceof QMPartMasterIfc)
        {
          String s = "images/partMaster.gif";
          return s;
        }
        //Begin CR2
        if(obj instanceof GenericPartMasterIfc)
        {
        	String s = "images/genericPartMaster.gif";
	        return s;
        }
        //Begin CR2
        else
        {
          return null;
        }
      }

  }
  /**
   * �������ϼе�id��ȡһ����װ��Ϣ�ļ��ϡ�
   * @param folderID String ���ϼ�id
   * @return Collection �������idΪ�գ���ȡ�û��ĸ������ϼУ�����װ�������У�0.���ϼ�id��1.���ϼ����ƣ�2.��Y��������������ӵ������з��ء�
   * �������id��Ϊ�գ����ϵ�һ��Ԫ������Ϊ��0.������1."\\.."��2."Y"������Ԫ�������Ƿ�װ�����������ϼе���Ϣ��0.���ϼ�id��1.���ϼ����ƣ�2.��Y����
   */
  public static Collection selectFolderMenu(String folderID)
  {
    try
    {
      String folder1[] = new String[3];
      SessionService ss = (SessionService)EJBServiceHelper.getService("SessionService");
      UserIfc user = (UserIfc)ss.getCurUserInfo();
      FolderService fs = (FolderService)EJBServiceHelper.getService("FolderService");
      PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
      FolderIfc folder = null;
      if(folderID == null || folderID.trim().length() == 0)
      {
        List result = new ArrayList(1);
        folder = (FolderIfc)fs.getPersonalFolder((UserInfo)user);
        folder1[0] = folder.getBsoID();
        folder1[1] = folder.getName();
        folder1[2] = "Y";
        result.add(folder1);
        return result;
      }
      else
      {
        folder = (FolderIfc)ps.refreshInfo(folderID);
        Collection coll = fs.findSubFolder(folder);
        List result = new ArrayList(1 + coll.size());
        folder1[0] = "";
        folder1[1] = "\\..";
        folder1[2] = "Y";
        result.add(folder1);
        if(coll != null && coll.size() != 0)
        {
          Object[] array = coll.toArray();
          String folder2[] = new String[3];
          for(int i = 0;i < array.length;i++)
          {
            FolderIfc subfolder = (FolderIfc)array[i];
            folder2[0] = subfolder.getBsoID();
            folder2[1] = subfolder.getName();
            folder2[2] = "Y";
            result.add(folder2);
          }
        }
        return result;
      }
    }
    catch(QMException e)
    {
      e.printStackTrace();
      return null;
    }
  }
  /**
   * �Ƿ�װ��pcfg�����
   * @return String ��true������false��
   */
  public String hasPcfg()
  {
      String hasPcfg = RemoteProperty.getProperty("com.faw_qm.hasPcfg" , "true");
      return hasPcfg;
  }
  /**
   * ��ȡע��������㲿�����Ǹ����岿����bsoID��
   * @param bsoID String �㲿��bsoID
   * @return String
   */
  public String getOriginGPartID(String bsoID)
  {
          String gpID ="";

          try
          {
            PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
            QMQuery query = new QMQuery("GenericVariantLink");
            QueryCondition cond = new QueryCondition("leftBsoID", "=", bsoID);
            query.addCondition(cond);
            Collection links = (Collection) pService.findValueInfo(query);
            if(links == null||links.size()==0)
            {
                return "";
            }

            else
            {
                Iterator iter = links.iterator();
                while(iter.hasNext())
                {
                    BinaryLinkIfc link = (BinaryLinkIfc) iter.next();
                    gpID = link.getRightBsoID();
                }
            }
            return gpID;
          }
          catch(QMException e)
          {
              e.printStackTrace();
              return gpID;
          }

  }


  /**
   * ����ܻ�׼�߹���Ķ��󵽻�׼�ߣ������������Ľṹɸѡ�����ݹ�ʵ�֡�
   * @param partbso :String ����ӵ��ܻ�׼�߹���Ķ���
   * @param baselinebso :String ��׼��ֵ����
   * @return Vector QMPartIfc��bsoID�ִ����ϡ�
   * @throws QMException
   */
  public Vector[] populateBaseline(String partbso, String baselinebso)
      throws QMException
  {
      Vector[] result = new Vector[4];
      Vector exitResult= new Vector();
      Vector changeResult=new Vector();
      Vector newResult= new Vector();
      Vector allResult= new Vector();
      QMPartIfc partIfc = (QMPartIfc) getObjectForID(partbso);
      BaselineIfc baselineIfc = (BaselineIfc) getObjectForID(baselinebso);

      PartConfigSpecIfc configSpecIfc = (PartConfigSpecIfc) PartServiceRequest.
                                        findPartConfigSpecIfc();
      Vector[] vec = PartServiceRequest.populateBaseline(partIfc, baselineIfc,
              configSpecIfc);

        Vector exit=(Vector)vec[0];
        for(int j=0;j<exit.size();j++)
        {
          String str = exit.elementAt(j).toString();
          String[] attr = new String[2];
          attr[0] = str.substring(0, str.indexOf(":"));
          attr[1] = str.substring(str.indexOf(":") + 1, str.length());

          exitResult.addElement(attr);
        }
        Vector change=(Vector)vec[1];
        for(int j=0;j<change.size();j++)
        {
          String str = change.elementAt(j).toString();
          String[] attr = new String[2];
          attr[0] = str.substring(0, str.indexOf(":"));
          attr[1] = str.substring(str.indexOf(":") + 1, str.length());

          changeResult.addElement(attr);
        }

        Vector anew=(Vector)vec[2];
        for(int j=0;j<anew.size();j++)
        {
          String str = anew.elementAt(j).toString();
          String[] attr = new String[2];
          attr[0] = str.substring(0, str.indexOf(":"));
          attr[1] = str.substring(str.indexOf(":") + 1, str.length());

          newResult.addElement(attr);
        }

        Vector all=(Vector)vec[3];
        for(int j=0;j<all.size();j++)
        {
          String str = all.elementAt(j).toString();
          String[] attr = new String[2];
          attr[0] = str.substring(0, str.indexOf(":"));
          attr[1] = str.substring(str.indexOf(":") + 1, str.length());

          allResult.addElement(attr);
        }

      result[0]=exitResult;
      result[1]=changeResult;
      result[2]=newResult;
      result[3]=allResult;

      return result;
  }
  /**
   * ͨ��masterid��ȡ���°汾���㲿����
   * @param masterID masterid
   * @return QMPartInfo ���°汾���㲿��(QMPart);
   * @throws QMException
   * @see QMPartInfo
   */
  public QMPartInfo getPartByMasterID(String masterID)
    throws QMException
  {
    QMPartInfo part=null;
    Vector ve=this.filterIterations(masterID);
    Object[] obj={};
    for(Iterator ite=ve.iterator();ite.hasNext();)
    {
      obj=(Object[])ite.next();
      part=(QMPartInfo)obj[0];
    }
    return part;

  }
  /**
   * ȡ��������Ч�Է������������һ���ԡ������ָ����ִ���
   * @return ������Ч�Է������������һ���ԡ������ָ����ִ�
   * @throws QMException
   */
  public String getAllEffItemName()
  throws QMException
  {
    String names="";
    Collection effs=null;
     QMQuery query = new QMQuery("QMConfigurationItem");
     PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
    effs= ps.findValueInfo(query);
    if(effs!=null)
    {
      QMConfigurationItemInfo config=null;
      for(Iterator ite=effs.iterator();ite.hasNext();)
      {
        config=(QMConfigurationItemInfo)ite.next();
        if(names.length()==0)
        {
          names=config.getConfigItemName();
        }
        else
        {
            names=names+","+config.getConfigItemName();
        }
      }
    }
    return names;
  }
  
  //����(1)20080228 zhangq begin:�ж���Ч�Է��������ͺ�ѡ��������Ƿ�һ�£������һ���򵯳���ʾ��Ϣ��
  /**
   * ��ȡ����������Ч�Է�����Ϣ�����顣
   * @return String[]  ������Ԫ��;
   * ��һ��Ԫ����������Ч�Է�����������ɵ��ԡ������ָ����ִ���
   * �ڶ���Ԫ����������Ч�Է�����������ɵ��ԡ������ָ����ִ���
   * @throws QMException
   */
  public String[] getAllEffItemNameAndType() throws QMException
    {
        String[] nameAndType = new String[2];
        String types = "";
        String names = "";
        Collection effs = null;
        QMQuery query = new QMQuery("QMConfigurationItem");
        PersistService ps = (PersistService) EJBServiceHelper
                .getService("PersistService");
        effs = ps.findValueInfo(query);
        if(effs != null)
        {
            QMConfigurationItemInfo config = null;
            for (Iterator ite = effs.iterator(); ite.hasNext();)
            {
                config = (QMConfigurationItemInfo) ite.next();
                EffectivityType effType = config.getEffectivityType();
                if(names.length() == 0)
                {
                    names = config.getConfigItemName();
                    //types = config.getEffectivityType().getDisplay();
                    if(effType != null)
                    {
                        types = effType.getDisplay();
                    }
                    else
                    {
                        types = "";
                    }
                }
                else
                {
                    names = names + "," + config.getConfigItemName();
                    if(effType != null)
                    {
                        types = types + ","
                                + config.getEffectivityType().getDisplay();
                    }
                    else
                    {
                        types = types + ",";
                    }
                }
            }
        }
        nameAndType[0] = names;
        nameAndType[1] = types;
        return nameAndType;
    }
    //����(1)20080228 zhangq end
  
    /**
     * ����ܻ�׼�߹���Ķ��󵽻�׼�ߣ������������Ľṹɸѡ�����ݹ�ʵ�֡�
     * @param partbso :String ����ӵ��ܻ�׼�߹���Ķ���
     * @param baselinebso :String ��׼��ֵ����
     * @return Vector QMPartIfc��bsoID�ַ������ϡ�
     * @throws QMException
     */
    public Vector getPopulateBaselineResult(String partbso, String baselinebso)
            throws QMException
    {
        Vector result = new Vector();
        QMPartIfc partIfc = (QMPartIfc) getObjectForID(partbso);
        BaselineIfc baselineIfc = (BaselineIfc) getObjectForID(baselinebso);
        result = PartServiceRequest.getPopulateBaselineResult(partIfc,
                baselineIfc, PartServiceRequest.getCurrentConfigSpec());
        return result;
    }
    
    /**
     * �ڴ���/�����ĵ�ʱ�����ĵ����Ϊ�㲿���Ĳο��ĵ���
     * @param parts �㲿��BsoID���ϡ�
     * @param docMasterBsoID �ĵ�����ϢBsoID��
     * @return String ��ʾ��Ϣ��
     * @throws QMException
     */
    public String createReferenceLink(Vector parts, String docMasterBsoID)
            throws QMException
    {
        RequestHelper helper = new RequestHelper();
        QMPartIfc partIfc = null;
        String partBsoID = "";
        PartReferenceLinkIfc linkIfc = null;
        String message = "";
        for (int i = 0; i < parts.size(); i++)
        {
            partIfc = (QMPartIfc) helper.request("PersistService",
                    "refreshInfo", new Class[]{String.class},
                    new Object[]{parts.get(i)});
            partBsoID = partIfc.getBsoID();
            //�㲿���ѱ���ǰ�û�������ڸ������ϼ��С�
            if(CheckInOutTaskLogic.isCheckedOutByUser(partIfc)
                    || !CheckInOutTaskLogic.isInVault(partIfc))
            {
                linkIfc = new PartReferenceLinkInfo();
                linkIfc.setLeftBsoID(docMasterBsoID);
                linkIfc.setRightBsoID(partBsoID);
                PartServiceRequest.savePartReferenceLink(linkIfc);
                //"�ɹ����Ϊ * �Ĳο��ĵ���\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M001", new Object[] {partIfc.getIdentity()});
            }
            //�㲿���ѱ������û������
            else if(CheckInOutTaskLogic.isCheckedOutByOther(partIfc))
            {
                //"* �ѱ��û� * �����\n"
                message = message
                        + QMMessage.getLocalizedMessage(resource, "M003",
                                new Object[]{partIfc.getIdentity(),
                                        getUserNameByID(partIfc.getLocker())});
            }
            //��ǰ�û����޸��㲿����Ȩ�ޡ�����㲿�������������������㲿����
            else if(CheckInOutTaskLogic.isCheckoutAllowed(partIfc))
            {
                CheckoutLinkInfo checkoutLinkInfo = CheckInOutTaskLogic
                        .checkOutObject(partIfc, CheckInOutTaskLogic
                                .getCheckoutFolder(), "");
                //��������BsoID��
                String workableID = checkoutLinkInfo
                        .getRoleBsoID("workingCopy");
                partIfc = (QMPartIfc) helper.request("PersistService",
                        "refreshInfo", new Class[]{String.class},
                        new Object[]{workableID});
                linkIfc = new PartReferenceLinkInfo();
                linkIfc.setLeftBsoID(docMasterBsoID);
                linkIfc.setRightBsoID(partIfc.getBsoID());
                PartServiceRequest.savePartReferenceLink(linkIfc);
                WorkableIfc workableIfc = CheckInOutTaskLogic.checkInObject(
                        partIfc, "");
                //"�ɹ����Ϊ * �Ĳο��ĵ���\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M001", new Object[] {workableIfc.getIdentity()});
            }
            //��ǰ�û�û���޸��㲿����Ȩ�ޡ�
            else
            {
                //"��û���޸� * ��Ȩ�ޣ�����Ϊ���㲿����Ӳο��ĵ���\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M004", new Object[] {partIfc.getIdentity()});
            }
        }
        return message;
    }

    /**
     * �ڴ���/�����ĵ�ʱ�����ĵ����Ϊ�㲿���������ĵ���
     * @param parts �㲿��BsoID���ϡ�
     * @param docBsoID �ĵ�ֵ����BsoID��
     * @return String ��ʾ��Ϣ��
     * @throws QMException
     */
    public String createDescribeLink(Vector parts, String docBsoID)
            throws QMException
    {
        RequestHelper helper = new RequestHelper();
        QMPartIfc partIfc = null;
        DocServiceHelper dsh = null;
        String partBsoID = "";
        PartDescribeLinkIfc linkIfc = null;
        String message = "";
        for (int i = 0; i < parts.size(); i++)
        {
            partIfc = (QMPartIfc) helper.request("PersistService",
                    "refreshInfo", new Class[]{String.class},
                    new Object[]{parts.get(i)});
            partBsoID = partIfc.getBsoID();
            //�㲿���ѱ���ǰ�û�������ڸ������ϼ��С�
            if(CheckInOutTaskLogic.isCheckedOutByUser(partIfc)
                    || !CheckInOutTaskLogic.isInVault(partIfc))
            {
                linkIfc = new PartDescribeLinkInfo();
                linkIfc.setLeftBsoID(partBsoID);
                linkIfc.setRightBsoID(docBsoID);
                PartServiceRequest.savePartDescribeLink(linkIfc);
                //"�ɹ����Ϊ * �������ĵ���\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M002", new Object[] {partIfc.getIdentity()});
            }
            //�㲿���ѱ������û������
            else if(CheckInOutTaskLogic.isCheckedOutByOther(partIfc))
            {
                //"* �ѱ��û� * �����\n"
                message = message + QMMessage.getLocalizedMessage(resource, "M003",
                        new Object[]{partIfc.getIdentity(),
                        getUserNameByID(partIfc.getLocker())});
            }
            //��ǰ�û����޸��㲿����Ȩ�ޡ�����㲿�������������������㲿����
            //CCBegin SS4
            else if(dsh.isBSXGroup()){
                linkIfc = new PartDescribeLinkInfo();
                linkIfc.setLeftBsoID(partIfc.getBsoID());
                linkIfc.setRightBsoID(docBsoID);
                PartServiceRequest.savePartDescribeLink(linkIfc);
                //"�ɹ����Ϊ * �������ĵ���\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M002", new Object[] {partIfc.getIdentity()});
            }
          //CCEnd SS4
            else if(CheckInOutTaskLogic.isCheckoutAllowed(partIfc))
            {
                CheckoutLinkInfo checkoutLinkInfo = CheckInOutTaskLogic
                        .checkOutObject(partIfc, CheckInOutTaskLogic
                                .getCheckoutFolder(), "");
                //��������BsoID��
                String workableID = checkoutLinkInfo
                        .getRoleBsoID("workingCopy");
                Class[] theClass = {String.class};
                Object[] theObjs = {workableID};
                partIfc = (QMPartIfc) helper.request("PersistService",
                        "refreshInfo", theClass, theObjs);
                linkIfc = new PartDescribeLinkInfo();
                linkIfc.setLeftBsoID(partIfc.getBsoID());
                linkIfc.setRightBsoID(docBsoID);
                PartServiceRequest.savePartDescribeLink(linkIfc);
                WorkableIfc workableIfc = CheckInOutTaskLogic.checkInObject(
                        partIfc, "");
                //"�ɹ����Ϊ * �������ĵ���\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M002", new Object[] {workableIfc.getIdentity()});
            }
            //��ǰ�û�û���޸��㲿����Ȩ�ޡ�
            else
            {
                //"��û���޸� * ��Ȩ�ޣ�����Ϊ���㲿����������ĵ���\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M005", new Object[] {partIfc.getIdentity()});
            }
        }
        return message;
    }

    /**
     * �ڸ����ĵ�ʱ��ɾ���ĵ����㲿���Ĳο��ĵ���
     * @param parts �㲿��BsoID���ϡ�
     * @param docMasterBsoID �ĵ�����ϢBsoID��
     * @return String ��ʾ��Ϣ��
     */
    public String deleteReferenceLink(Vector parts, String docMasterBsoID)
    {
        RequestHelper helper = new RequestHelper();
        String message = "";
        for (int i = 0; i < parts.size(); i++)
        {
            String partBsoID = (String) parts.get(i);
            QMPartIfc partIfc = null;
            Collection coll = new ArrayList(1);
            try
            {
                partIfc = (QMPartIfc) helper.request("PersistService",
                        "refreshInfo", new Class[]{String.class},
                        new Object[]{partBsoID});
            }
            catch (QMException e)
            {
                message += e.getClientMessage();
            }
            try
            {
                QMQuery query = new QMQuery("PartReferenceLink");
                QueryCondition condition = new QueryCondition("rightBsoID",
                        QueryCondition.EQUAL, partBsoID);
                query.addCondition(condition);
                query.addAND();
                QueryCondition condition2 = new QueryCondition("leftBsoID",
                        QueryCondition.EQUAL, docMasterBsoID);
                query.addCondition(condition2);
                coll = (Collection) helper.request("PersistService",
                        "findValueInfo", new Class[]{QMQuery.class},
                        new Object[]{query});
            }
            catch (QMException e)
            {
                message += e.getClientMessage();
            }
            //ֻ����һ������
            Iterator iter = coll.iterator();
            while (iter.hasNext())
            {
                try
                {
                    helper.request("PersistService", "deleteValueInfo",
                            new Class[]{BaseValueIfc.class}, new Object[]{iter
                                    .next()});
                    message += QMMessage.getLocalizedMessage(resource, "M006",
                            new Object[]{partIfc.getIdentity()});
                }
                catch (QMException e)
                {
                    message += QMMessage.getLocalizedMessage(resource, "M008",
                            new Object[]{partIfc.getIdentity(),
                                    e.getClientMessage()});
                }
            }
        }
        return message;
    }

    /**
     * �ڸ����ĵ�ʱ��ɾ���ĵ����㲿���������ĵ���
     * @param parts �㲿��BsoID���ϡ�
     * @param docBsoID �ĵ�ֵ����BsoID��
     * @return String ��ʾ��Ϣ��
     */
    public String deleteDescribeLink(Vector parts, String docBsoID)
    {
        RequestHelper helper = new RequestHelper();
        String message = "";
        for (int i = 0; i < parts.size(); i++)
        {
            String partBsoID = (String) parts.get(i);
            QMPartIfc partIfc = null;
            Collection coll = new ArrayList(1);
            try
            {
                partIfc = (QMPartIfc) helper.request("PersistService",
                        "refreshInfo", new Class[]{String.class},
                        new Object[]{partBsoID});
            }
            catch (QMException e)
            {
                message += e.getClientMessage();
            }
            try
            {
                QMQuery query = new QMQuery("PartDescribeLink");
                QueryCondition condition = new QueryCondition("rightBsoID",
                        QueryCondition.EQUAL, docBsoID);
                query.addCondition(condition);
                query.addAND();
                QueryCondition condition2 = new QueryCondition("leftBsoID",
                        QueryCondition.EQUAL, partBsoID);
                query.addCondition(condition2);
                coll = (Collection) helper.request("PersistService",
                        "findValueInfo", new Class[]{QMQuery.class},
                        new Object[]{query});
            }
            catch (QMException e)
            {
                message += e.getClientMessage();
            }
            Iterator iter = coll.iterator();
            while (iter.hasNext())
            {
                try
                {
                    helper.request("PersistService", "deleteValueInfo",
                            new Class[]{BaseValueIfc.class}, new Object[]{iter
                                    .next()});
                    message += QMMessage.getLocalizedMessage(resource, "M007",
                            new Object[]{partIfc.getIdentity()});
                }
                catch (QMException e)
                {
                    message += QMMessage.getLocalizedMessage(resource, "M009",
                            new Object[]{partIfc.getIdentity(),
                                    e.getClientMessage()});
                }
            }
        }
        return message;
    }

    /**
     * �������ƺͱ�ţ���ȡ����汾�㲿����BsoID��
     * @param name ���ơ�
     * @param number ��š�
     * @return ArrayList ����汾�㲿����BsoID���ϡ�
     * @throws QMException
     */
    public ArrayList getAllPartByMaster(String name, String number)
            throws QMException
    {
        ArrayList partList = new ArrayList();
        Collection masters = PartServiceRequest.getAllPartMasters(name, number);
        PartConfigSpecIfc config = PartServiceRequest.findPartConfigSpecIfc();
        //���id
        if(masters != null && masters.size() > 0)
        {
            Iterator iter = masters.iterator();
            while (iter.hasNext())
            {
                QMPartMasterIfc m = (QMPartMasterIfc) iter.next();
                QMPartIfc part = PartServiceRequest.getPartByConfigSpec(m,
                        config);
                partList.add(part);
            }
        }
        return partList;
    }
    
    /**
     * ����������������״̬��
     * @return Collection ������������״̬��LifeCycleState�����ϡ�
     */
    public static Collection findAllLifeCycleState()
    {
        Collection lifeCycleStateList = new ArrayList(10);
        LifeCycleState allState[] = LifeCycleState.getLifeCycleStateSet();
        for (int i = 0; i < allState.length; i++)
            lifeCycleStateList.add(allState[i]);
        return lifeCycleStateList;
    }

    /**
     * ���������㲿�����͡�
     * @return Collection �����㲿��(QMPartType)���͵ļ��ϡ�
     */
    public static Collection findAllPartType()
    {
        Collection partTypeList = new ArrayList(10);
        QMPartType allType[] = QMPartType.getQMPartTypeSet();
        for (int i = 0; i < allType.length; i++)
            partTypeList.add(allType[i]);
        return partTypeList;
    }

    /**
     * ���������㲿����Դ��
     * @return Collection �����㲿����Դ(ProducedBy)�ļ��ϡ�
     */
    public static Collection findAllProducedBy()
    {
        Collection producedByList = new ArrayList(10);
        ProducedBy allProducedby[] = ProducedBy.getProducedBySet();
        for (int i = 0; i < allProducedby.length; i++)
            producedByList.add(allProducedby[i]);
        return producedByList;
    }
    
    /**
     * ����û�Ȩ�ޣ���ȡ���пɼ�����ͼ��
     * @return Collection ��ͼ����(String)���ϡ�
     */
    public static Collection findAllView()
    {
        return ViewHelper.getAllPartViews();
    }
    
    /**
     * ������е���Ŀ��ֻ�������õġ���ԭ��Ϊ���������õģ�
     * @return Collection ��Ŀ���ƣ�String������
     */
    public static Collection findAllProject()
            throws QMException
    {
        
        return CommonSearchHelper.getAllProjects();
    }
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
     * @return  Collection �㲿��ֵ����QMPartInfo�����ϡ�
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
        return PartServiceRequest.findAllPartInfo(partnumber, checkboxNum,
                partname, checkboxName, partver, checkboxVersion, partview,
                checkboxView, partstate, checkboxLifeCState, parttype,
                checkboxPartType, partby, checkboxProducedBy, partproject,
                checkboxProject, partfolder, checkboxFolder, partcreator,
                checkboxCreator, partupdatetime, checkboxModifyTime);
    }
    //whj add
    /**
   * �����㲿����BsoID��ȡ���㲿���ϵ����е���Ч�Է��������ƣ����ͣ���Ч��ֵ��Χ��bsoid��
   * 
   * @param partBsoID String �㲿����BsoID��
   * @param isacl boolean
   * @throws QMException
   * @return Vector ���ص�Vector�е�ÿ��Ԫ�ض���String[3]��
   * <br>String[0]:��Ч�Է������ƣ����Ĭ��������Ч�ԣ����Բ�ָ����Ч�Է�������
   * <br>String[1]:��Ч�Է������ͣ����û����Ч�Է��������ƣ���Ĭ��Ϊ"������Ч��"��
   * <br>String[2]:��Ч��ֵ��Χ��
   * <br>String[3]:��Ч��bsoid��
   */
  public Vector getLookEffectivity(String partBsoID, boolean isacl)
      throws QMException
  {
    PartDebug.trace(this, "getLookEff() begin......");
    //�ȵ���EnterprisePartService�еķ���getEffVector(QMPartInfo):Vector
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    EffService effService = (EffService)EJBServiceHelper.getService("EffService");
    EffectivityManageableIfc partInfo = (EffectivityManageableIfc)pService.refreshInfo(partBsoID);
    Vector effGroups = PartServiceRequest.getEffVector((WorkableIfc)partInfo, isacl);
    Vector resultVector = new Vector();
    //��effGroups�е����е�EffGroup�������ѭ������װ������ϣ�
    for(int i = 0; i < effGroups.size(); i++)
    {
      EffGroup effGroup = (EffGroup)effGroups.elementAt(i);
      EffContextIfc effContextIfc = effGroup.getEffContext();
      String[] tempString = new String[4];
      //���effContextIfcΪ�յĻ���һ�������ڵģ�
      if(effContextIfc == null)
      {
        tempString[0] = "";
        tempString[1] = EffectivityType.DATE.getDisplay(); //������Ч�Է���
        String dateRange = effGroup.getRange();
        tempString[2] = effService.getDateType(dateRange);
        tempString[3] = null;
        resultVector.addElement(tempString);
      }
      else
      {
        if(effContextIfc instanceof QMConfigurationItemIfc)
        {
          QMConfigurationItemIfc configItemIfc = (QMConfigurationItemIfc)effContextIfc;
          tempString[0] = configItemIfc.getConfigItemName();
          //�����type�п���Ϊ�գ�
          EffectivityType type = configItemIfc.getEffectivityType();
          if(type != null)
          {
            tempString[1] = type.getDisplay();
          }
          else
          {
            //��Ҫ����EffGroup�б���ľ������Ч�����������������tempString[1]���и�ֵ��
            //��Ҫ����effGroup�����е�type:String����ַ�������
            //effType�ľ�������ͽ���ָ����
            //��δ�����ÿ��Բ�д����!!!!
            String typeOfEffGroup = effGroup.getType();
            if(typeOfEffGroup.equals("QMDatedEffectivity"))
            {
              tempString[1] = (EffectivityType.DATE).getDisplay();
            }
            else
            {
              if(typeOfEffGroup.equals("QMSerialNumEffectivity"))
              {
                tempString[1] = (EffectivityType.SERIAL_NUM).getDisplay();
              }
              else
              {
                if(typeOfEffGroup.equals("QMLotEffectivity"))
                {
                  tempString[1] = (EffectivityType.LOT_NUM).getDisplay();
                }
              }
            }
            //effType = "";
          }
          //tempString[1] = configItemIfc.getEffectivityType().
          //    getDisplay();
          if(tempString[1].equals(EffectivityType.DATE.getDisplay()))
          {
            String dateRange = effGroup.getRange();
            tempString[2] = effService.getDateType(dateRange);
          }
          else
          {
            tempString[2] = effGroup.getRange();
          }
          tempString[3] = configItemIfc.getBsoID();
          resultVector.addElement(tempString);
        }
        //end if(effContextIfc instanceof QMConfigurationItemIfc)
      }
      //end if and else (effContextIfc == null)
    }
    //end for(int i=0; i<effGroups.size(); i++)
    PartDebug.trace(this, "getLookEff() end. return is Vector");
    return resultVector;
  }
  /**
   * ��ȡ�ݿͻ�������⡣
   * @param s ��������id
   * @return ��Ψһ�Թ����ṩ��Ψһ�Ա�ʾ(String)
   */
      public String getTitle(String s)
      {
          String s1 = "";
          try
          {
              PersistService persistservice = (PersistService)EJBServiceHelper.getService("PersistService");
              BaseValueIfc basevalueifc = persistservice.refreshInfo(s);
              s1 = IdentityFactory.getDisplayIdentity(basevalueifc).getLocalizedMessage(null);
          }
          catch(Exception exception)
          {
              exception.printStackTrace();
          }
          return s1;
      }
      
      //CCBegin by leixiao 2010-1-7 �򲹶���v4r3_p005_20100104 TD2741      
            /**
       * ��ȡ�ݿͻ����⡣
       * @param s ��������id
       * @return ֻ�����㲿�������ƺͱ�ţ�String��
       */
          public String getPartNumberAndName(String s)//CR4
          {
              String s1 = "";
              try
              {
                  PersistService persistservice = (PersistService)EJBServiceHelper.getService("PersistService");
                  BaseValueIfc basevalueifc = persistservice.refreshInfo(s);
                  if(basevalueifc instanceof QMPartInfo)
                      s1 = ((QMPartInfo)basevalueifc).getPartNumber()+" "+((QMPartInfo)basevalueifc).getPartName();
                  else if(basevalueifc instanceof QMPartMasterInfo)
                  s1 = "����Ϣ "+((QMPartMasterInfo)basevalueifc).getPartNumber()+" "+((QMPartMasterInfo)basevalueifc).getPartName();
              }
              catch(Exception exception)
              {
                  exception.printStackTrace();
              }
              return s1;
          }
          //CCEnd by leixiao 2010-1-7��v4r3_p005_20100104 
     
    //����(2)20080808 zhangq begin �޸�ԭ���ڲ����㲿���ı����ڲ�Ʒʱ����ʾ����ȷ����TD-1794��
	/**
	 * ����ָ���㲿����BsoId����㲿���ڵ�ǰ�û������ù淶�±��ò�Ʒ��ʹ�õĽṹ��
	 * ʹ�ý�������ڷ���ֵvector�С�
	 * @param partID :String ָ�����㲿��BsoId��
	 * @param productBsoId :String ʹ�ø��㲿���Ĳ�Ʒ��BsoId��
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
	public Vector getUsedProductStruct(String partID, String productBsoId)
			throws QMException {
		PartDebug.trace(this, "getUsedProductStruct() begin ....");
		PartConfigSpecIfc partConfigSpecIfc = PartServiceRequest
				.findPartConfigSpecIfc();
		PersistService pService = (PersistService) EJBServiceHelper
				.getPersistService();
		QMPartIfc partIfc = (QMPartIfc) pService.refreshInfo(partID);
		PartDebug.trace(this, "getUsedProductStruct() end....return is Vector");
		return PartServiceRequest.getUsedProductStruct(partIfc, productBsoId,
				partConfigSpecIfc);
	}
	//����(2)20080808 zhangq end
	
//	CCBegin by leix 2009-11-30
	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * ��ȡ�㲿��������ͼ��������ͼ��bomͳ�Ʊ�Ƚ�yanqi-20060915
	   * @param partID String �㲿����id
	   * @param attrNames String ��Ҫ���ص�����
	   * @param source String �㲿����Դ
	   * @param type String �㲿������
	   * @throws QMException
	   * @return Vector ���ؼ���
	   * @author liunan 2008-08-01



	   */
	  public Vector getCompareBomList(String partID, String attrNames,
	                                  String source,
	                                  String type) throws QMException {
	    if (attrNames == null || attrNames.length() == 0) {
	      Vector result = new Vector();
	      return result;
	    }
	    if (attrNames.trim().endsWith(",")) {
	      attrNames = attrNames.substring(0, attrNames.length() - 1);
	    }
	    //����Ҫ���ص��ַ����������ַ�������
	    StringTokenizer tokens = new StringTokenizer(attrNames.trim(), ",");
	    String[] attrNames1 = new String[tokens.countTokens()];
	    int i = 0;
	    while (tokens.hasMoreTokens()) {
	      attrNames1[i] = tokens.nextToken();
	      i++;
	    } //
	    String[] affixAttrNames = null;
	    String[] routeNames = null;
	    PersistService pService = (PersistService) EJBServiceHelper.
	        getPersistService();
	    QMPartIfc partIfc = (QMPartIfc) pService.refreshInfo(partID);
	    QMPartIfc enPartIfc = null;
	    QMPartIfc manuPartIfc = null;

	    Collection coll = PartServiceRequest.filteredIterationsOf( (MasteredIfc) partIfc.
	        getMaster(),getPartConfigSpecByViewName("������ͼ"));
	    if (coll != null && coll.size() != 0) {
	      Iterator it = coll.iterator();
	      Object[] obj = (Object[]) it.next();
	      enPartIfc = (QMPartInfo) obj[0];

	    }
	    coll = PartServiceRequest.filteredIterationsOf( (MasteredIfc) (partIfc.getMaster()),
	                                         getPartConfigSpecByViewName("������ͼ"));
	    if (coll != null && coll.size() != 0) {
	      Iterator it = coll.iterator();
	      Object[] obj = (Object[]) it.next();
	      manuPartIfc = (QMPartInfo) obj[0];
	    }
	    //��ȡ������ͼ�µ�bom�嵥
	    Vector en_vec = PartServiceRequest.setBOMList(enPartIfc, attrNames1,
	                                         affixAttrNames,
	                                         routeNames,
	                                         source, type,
	                                         getPartConfigSpecByViewName("������ͼ"));
	    //��ȡ������ͼ�µ�bom�嵥
	    Vector manu_vec = PartServiceRequest.setBOMList(manuPartIfc, attrNames1,
	                                           affixAttrNames,
	                                           routeNames,
	                                           source, type,
	                                           getPartConfigSpecByViewName("������ͼ"));
	    /*Iterator iter = en_vec.iterator();
	         if (iter.hasNext()) {
	      iter.next();
	         }
	         while (iter.hasNext()) {
	      Object[] enEle1111 = (Object[]) iter.next();
	      for (int ii = 0; ii < enEle1111.length; ii++) {
	        System.out.println(enEle1111[4]+","+enEle1111[ii].getClass());
	        System.out.println(enEle1111[4] + "," + enEle1111[ii]);
	      }
	         }
	         Iterator iter2 = manu_vec.iterator();
	         if (iter2.hasNext()) {
	      iter2.next();
	         }
	         while (iter2.hasNext()) {
	           Object[] manuEle1111 = (Object[]) iter2.next();
	           for (int ii = 0; ii < manuEle1111.length; ii++) {
	             System.out.println(manuEle1111[4] + "," + manuEle1111[ii]);
	           }
	         }*/
	    return compareBom(en_vec, manu_vec);
	  }

	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * @param en Vector
	   * @param manu Vector
	   * @return Vector
	   * @author liunan 2008-08-01
	   */
	  private Vector compareBom(Vector en, Vector manu) {
	    Vector result = new Vector(); //���صĽ����
	    try {
	    Vector onlyEn = new Vector(); //������ʱ���ֻ�ڹ�����ͼ�±�ʹ�õ��㲿����Ŀ����ʹ�������Ԫ��˳��Ϊ������ͼ�¶�ʹ�õĲ�������ֻ�ڹ�����ͼ��ʹ�õĲ�������ֻ��������ͼ��ʹ�õĲ���
	    HashMap numArrayMap = new HashMap(manu.size()); //��������㲿���ţ�����array��ӳ�䡣����array[0]���㲿��id,array[2]���㲿���������������е����,array[3]�����㲿���Ƿ�ɷ�,array[4]���㲿�����,array[5]���㲿������,array[6]���㲿������,array[7]���㲿���汾,array[8]���㲿����ͼ��
	    Iterator manuIter = manu.iterator();
	    //������һ��Ԫ�أ���ͷ��
	    if (manuIter.hasNext()) {
	      String[] manuEle = (String[]) manuIter.next();
	    }
	    //��������ͼ��Ԫ����ӵ�numArrayMap���Թ�����������ͼʱ��ѯ
	    while (manuIter.hasNext()) {
	      Object[] manuEle = (Object[]) manuIter.next();
	      numArrayMap.put(manuEle[4], manuEle);
	    }
	    //����������ͼ���ϵĵ�һ��Ԫ��
	    Iterator enIter = en.iterator();
	    if (enIter.hasNext()) {
	      enIter.next();
	    }
	    //����������ͼ�ļ��ϣ�
	    while (enIter.hasNext()) {
	      //���ݹ�����ͼ�������ȥnumArrayMap��ѯ�����Ƿ����ͬ�ŵ�������ͼ���㲿��������ڣ���������ͼ����ĺ�����Ԫ���빤����ͼ������кϲ��������ϲ����������ӵ�������У�Ȼ���numArrayMap��ɾ����������ͼ��ӳ��
	      Object[] compareEle = new Object[13]; //��һ��Ԫ���ǹ�����ͼ��������ͼ��ʹ������Ƿ���ͬ�ı�ʶ����ͬΪ��true������ͬΪ��false������ͬ����ʹ����ͼ�Ĳ�ͬ��ʹ�������Ĳ�ͬ��
	      Object[] enEle = (Object[]) enIter.next();
	      System.arraycopy(enEle, 0, compareEle, 1, 9);
	      if (numArrayMap.get(enEle[4]) != null) {
	        Object[] manuEle = (Object[]) numArrayMap.get(enEle[4]);
	        if (!enEle[6].toString().equals(manuEle[6].toString()) ||
	            ! ( (String) enEle[8]).equals( (String) manuEle[8])) {
	          compareEle[0] = "false";
	          //System.out.println("partNamber: " + enEle[4] + ",false:" + enEle[6] +
	          //"_" + enEle[8] + " " +
	          //manuEle[6] + "_" + manuEle[8]);
	        }
	        else {
	          compareEle[0] = "true";
	          //System.out.println("partNamber: " + enEle[4] + ",true:" + enEle[6] +
	          //"_" + enEle[8] + " " +
	          //manuEle[6] + "_" + manuEle[8]);
	        }
	        System.arraycopy(manuEle, 6, compareEle, 10, 3);
	        result.add(compareEle);
	        numArrayMap.remove(enEle[4]);
	      }
	      else {
	        compareEle[0] = "false";
	        compareEle[10] = "";
	        compareEle[11] = "";
	        compareEle[12] = "";
	        onlyEn.add(compareEle);
	      }
	    }
	    result.addAll(onlyEn);
	    //����ֻ��������ͼ�д��ڵ��㲿��
	    Iterator restIter = numArrayMap.values().iterator();
	    while (restIter.hasNext()) {
	      Object[] manuEle = (Object[]) restIter.next();
	      Object[] compareEle = new Object[13];
	      compareEle[0] = "false";
	      System.arraycopy(manuEle, 0, compareEle, 1, 6);
	      compareEle[9] = "";
	      compareEle[8] = "";
	      compareEle[7] = "";
	      System.arraycopy(manuEle, 6, compareEle, 10, 3);
	      result.add(compareEle);
	    }
	    }
	    catch (Exception e) {
	    e.printStackTrace();
	    }
	    return result;
	  }

	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * ������ͼ���Ʒ����㲿�����ù淶
	   * @param viewName String
	   * @throws QMException
	   * @return PartConfigSpecIfc
	   * @author liunan 2008-08-01
	   */
	  private PartConfigSpecIfc getPartConfigSpecByViewName(String viewName) throws
	      QMException {
	    ViewService viewService = (ViewService) EJBServiceHelper.getService(
	        "ViewService");
	    ViewObjectIfc view = viewService.getView(viewName);
	    //������ָ������ͼ����û�л�ȡ����Ӧ��ֵ�����򷵻ص�ǰ���ù淶
	    if (view == null) {
	      return (PartConfigSpecIfc) PartServiceRequest.
	          findPartConfigSpecIfc();
	    }
	    PartConfigSpecIfc partConfigSpecIfc = new PartConfigSpecInfo();
	    partConfigSpecIfc = new PartConfigSpecInfo();
	    partConfigSpecIfc.setStandardActive(true);
	    partConfigSpecIfc.setBaselineActive(false);
	    partConfigSpecIfc.setEffectivityActive(false);
	    PartStandardConfigSpec partStandardConfigSpec_en = new
	        PartStandardConfigSpec();
	    partStandardConfigSpec_en.setViewObjectIfc(view);
	    partStandardConfigSpec_en.setLifeCycleState(null);
	    partStandardConfigSpec_en.setWorkingIncluded(true);
	    partConfigSpecIfc.setStandard(partStandardConfigSpec_en);
	    return partConfigSpecIfc;
	  }


	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * yanqi-20061017
	   * �����㲿��id��ȡ���㲿����һ���Ӽ���Ϣ�б�
	   * @param partID String �㲿��id
	   * @throws QMException
	   * @return Vector ����Object�����鼯�ϣ������ÿ��Ԫ�ض����ַ�������Щ�ַ�������Ϊ�㲿���ı��,����,�汾,��ͼ,����,ʹ�÷�ʽ,����,��Դ,��������״̬
	   * @author liunan 2008-08-01
	   */
	  public Vector getFirstLevelSonPartsList(String partID) throws
	      QMException {
	    PersistService pService = (PersistService) EJBServiceHelper.
	        getPersistService();
	    QMPartIfc partIfc = (QMPartIfc) pService.refreshInfo(partID);
	    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc) PartServiceRequest.
	        findPartConfigSpecIfc();
	    return getFirstLevelSonPartsList(partIfc, partConfigSpecIfc);
	  }

	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * yanqi-20061017
	   * �����㲿��ֵ�������ù淶��ȡ���㲿����һ���Ӽ���Ϣ�б�
	   * @param part QMPartIfc �㲿��ֵ����
	   * @param config PartConfigSpecIfc ���ù淶
	   * @throws QMException
	   * @return Vector ����Object�����鼯�ϣ������ÿ��Ԫ�ض����ַ�������Щ�ַ�������Ϊ�㲿���ı��,����,�汾,��ͼ,����,ʹ�÷�ʽ,����,��Դ,��������״̬
	   * @author liunan 2008-08-01
	   */
	  private Vector getFirstLevelSonPartsList(QMPartIfc part,
	                                           PartConfigSpecIfc config) throws
	      QMException {
	    Collection coll = PartServiceRequest.getUsesPartIfcs(part, config);
	    Vector result = getPartsAttributeArrays(coll);
	    return sortStringArrays(result, 0);
	  }

	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * yanqi-20061017
	   * �������ַ�����ʾ�㲿�����Ե����鼯��
	   * @param partAndLink Collection �������鼯�ϣ�����������Ԫ�أ���һ�����㲿��ʹ�ù���ֵ���󣬵ڶ������㲿��ֵ������㲿��������Ϣֵ����
	   * @return Vector ����Object�����鼯�ϣ������ÿ��Ԫ�ض����ַ�������Щ�ַ�������Ϊ�㲿���ı��,����,�汾,��ͼ,����,ʹ�÷�ʽ,����,��Դ,��������״̬
	   * @author liunan 2008-08-01
	   */
	  private Vector getPartsAttributeArrays(Collection partAndLink) {
	    Vector result = new Vector();
	    if (partAndLink == null || partAndLink.size() == 0) {
	      return result;
	    }
	    Iterator iterator = partAndLink.iterator();
	    while (iterator.hasNext()) {
	      Object obj = iterator.next();
	      if (obj instanceof Object[]) {
	        Object[] part_link = (Object[]) obj;
	        if (part_link[1] instanceof QMPartIfc &&
	            part_link[0] instanceof PartUsageLinkIfc) {
	          result.add(getPartAttriArray( (QMPartIfc) part_link[1],
	                                       (PartUsageLinkIfc) part_link[0]));
	        }
	      }
	    }
	    return result;
	  }

	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * yanqi-20061017
	   * �������ַ�����ʾ�㲿�����Ե�����
	   * @param part QMPartIfc �㲿��ֵ����
	   * @param link PartUsageLinkIfc ʹ�ù���ֵ����
	   * @return Object[] �����ÿ��Ԫ�ض����ַ�������Щ�ַ�������Ϊ�㲿���ı��,����,�汾,��ͼ,����,ʹ�÷�ʽ,����,��Դ,��������״̬
	   * @author liunan 2008-08-01
	   */
	  private Object[] getPartAttriArray(QMPartIfc part, PartUsageLinkIfc link) {
	    Object[] result = new Object[10];
	    result[1] = part.getPartNumber();
	    result[2] = part.getPartName();
	    result[3] = part.getVersionValue();
	    result[4] = part.getViewName();
	    result[5] = link.getQMQuantity().getQuantity() + "";
	    result[6] = link.getQMQuantity().getDefaultUnit().getDisplay();
	    result[7] = part.getPartType().getDisplay();
	    result[8] = part.getProducedBy().getDisplay();
	    result[9] = part.getLifeCycleState().getDisplay();
	    result[0] = part.getBsoID();
	    return result;
	  }

	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * yanqi-20061017
	   * �Զ������������indexָ��������Ԫ�ؽ�������
	   * @param vec Vector �������鼯��
	   * @param index int ��������ĵ�index��Ԫ�ؽ�������
	   * @return Vector
	   * @author liunan 2008-08-01
	   */
	  private Vector sortStringArrays(Vector vec, int index) {
	    int size = vec.size();
	    for (int i = 0; i < size; i++) {
	      for (int j = i; j < size; j++) {
	        Object[] aa = (Object[]) vec.elementAt(i);
	        String partNumAA = (String) aa[index];
	        Object[] bb = (Object[]) vec.elementAt(j);
	        String partNumBB = (String) bb[index];
	        if (partNumAA.compareTo(partNumBB) > 0) {
	          vec.setElementAt(bb, i);
	          vec.setElementAt(aa, j);
	        }
	      }
	    }
	    return vec;
	  }

	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
	   * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
	   * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
	   * 1��������������ԣ�
	   * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true", "false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
	   * 2������������ԣ�
	   * BsoID�����롢���ơ��ǣ��񣩿ɷ�("true", "false")�������������������ԡ�
	   * @param partID :String ָ�����㲿����bsoID.
	   * @param attrNames :String ����Ҫ��������Լ��ϣ����Ϊ�գ��򰴱�׼�������
	   * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
	   * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
	   * @throws QMException
	   * @return Vector
	   * @author liunan 2008-08-01
	   */
	  public Vector getBOMList(String partID, String attrNames, String source,
	                           String type) throws QMException {
	    attrNames = attrNames.trim();
	    if (attrNames == null || attrNames.length() == 0) {
	      Vector result = new Vector();
	      return result;
	    }
	    //int tt = attrNames.indexOf("װ��·��-2,");
	    int ttt = 0;
	    for (int i = 0; i < attrNames.length(); i++) {
	      if (attrNames.charAt(i) == ',') {
	        ttt++;
	      }
	    }

	    int ff = 0, fff = 0;
	    String[] attrNames1 = null;
	    //CCBegin by liunan 2009-04-13
	   /* if (tt >= 0) {
	      attrNames1 = new String[ttt - 1];
	    }
	    else {
	      attrNames1 = new String[ttt];
	    }*/
	    //CCEnd by liunan 2009-04-13
	    attrNames1 = new String[ttt];

	    try {

	      for (int i = 0; i < attrNames.length(); i++) {
	        if (attrNames.charAt(i) == ',') {
	          String str = attrNames.substring(ff, i);
	          //CCBegin by liunan 2009-04-13
	          //if (str.equals("װ��·��-2")) {

	          //}
	          //else {
	            attrNames1[fff] = str;
	            fff++;
	          //}
	          //CCEnd by liunan 2009-04-13
	          ff = i + 1;
	        }
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();

	    }
	    String[] affixAttrNames = null;
	    String[] routeNames = null;
	    PersistService pService = (PersistService) EJBServiceHelper.
	        getPersistService();
	    QMPartIfc partIfc = (QMPartIfc) pService.refreshInfo(partID);
	    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.
	        findPartConfigSpecIfc();
	    Vector v = PartServiceRequest.setBOMList(partIfc, attrNames1, affixAttrNames, routeNames,
	                                source, type, partConfigSpecIfc);
	    return v;
	  }

		//CCBegin by leix	 2010-12-20  �����߼��ܳ���������
	  public Vector getMaterialList(String partID, String attrNames) throws
      QMException {
		  return getMaterialList(partID,attrNames,false);
	  }
	  public Vector getMaterialLogicList(String partID, String attrNames) throws
      QMException {
		  return getMaterialList(partID,attrNames,true);
	  }
	//CCEnd by leix	 2010-12-20  �����߼��ܳ���������
	 //CCBegin SS1
	  /**
	   * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿���������嵥��Ϣ��
	   * 
	   * @param partID String ָ���㲿����bsoID
	   * @param attrNames String ���Ƶ����ԣ�����Ϊ��
	   * @throws QMException
	   * @return Vector ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
	   *<br> 0����ǰpart��BsoID��
	   *<br> 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
	   *<br> 2����ǰpart�ı�ţ�
	   * <br>3����ǰpart�����ƣ�
	   * <br>4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
	   * <br>5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
	   * <br>             ��������������ԣ��������ж��Ƶ����Լӵ���������С�
	   */
	  public Vector getMaterialListERP(String partID, String attrNames)
	      throws QMException
	  {
	    PartDebug.trace(this, "getMaterialList() begin ....");

	    //Դ����
	    /*    
	    String[] attrNames1 = null;
	    String[] affixAttrNames1 = null;
	    Vector allAttrNames = new Vector();
	    if(attrNames != null && attrNames.length() > 0)
	    {
	      allAttrNames = getTableHeader(attrNames);
	      if(allAttrNames != null && allAttrNames.size() > 0)
	      {
	        attrNames1 = (String[])allAttrNames.elementAt(0);
	        affixAttrNames1 = (String[])allAttrNames.elementAt(1);
	      }
	    }*/
		
	    attrNames = attrNames.trim();
	      if (attrNames == null || attrNames.length() == 0)
	      {
	          Vector result = new Vector();
	          return result;
	      }
	      int attrSize = 0;
	      for (int i = 0; i < attrNames.length(); i++)
	      {
	          if (attrNames.charAt(i) == ',')
	          {
	              attrSize++;
	          }
	      }

	      String[] attrNames1 = null;
	      String[] affixAttrNames1 = null;
	      attrNames1 = new String[attrSize];
	      int beginSite = 0, attrTemp = 0;
	      try
	      {
	          for (int i = 0; i < attrNames.length(); i++)
	          {
	              if (attrNames.charAt(i) == ',')
	              {
	                  String str = attrNames.substring(beginSite, i);
	                  attrNames1[attrTemp] = str;
	                  attrTemp++;
	                  beginSite = i + 1;
	              }
	          }
	      }
	      catch (Exception e)
	      {
	          e.printStackTrace();

	      }
	   
	    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
	    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
	    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
	    PartDebug.trace(this, "getMaterialList() end....return is Vector");
	    return PartServiceRequest.setMaterialListERP(partIfc, attrNames1, affixAttrNames1, partConfigSpecIfc);
	  }
	  //CCEnd SS1
	
	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
	   * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿���������嵥��Ϣ��
	   * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
	   * 0����ǰpart��BsoID��
	   * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
	   * 2����ǰpart�ı�ţ�
	   * 3����ǰpart�����ƣ�
	   * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
	   * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
	   *              ��������������ԣ��������ж��Ƶ����Լӵ���������С�
	   * @param partID String ָ���㲿����bsoID
	   * @param attrNames String ���Ƶ����ԣ�����Ϊ��
	   * @throws QMException
	   * @return Vector
	   * @author liunan 2008-08-01
	   * leixiao 2010-12-21 ���Ӳ���islogic,�������� �߼��ܳ���������
	   */

	  public Vector getMaterialList(String partID, String attrNames,boolean islogic) throws
	      QMException {
	    attrNames = attrNames.trim();
	    if (attrNames == null || attrNames.length() == 0) {
	      Vector result = new Vector();
	      return result;
	    }

	    int tt = attrNames.indexOf("װ��·��-2,");
	    int t = attrNames.indexOf("����·��-2,");
	    int ttt = 0;
	    for (int i = 0; i < attrNames.length(); i++) {
	      if (attrNames.charAt(i) == ',') {
	        ttt++;
	      }
	    }
	    int ff = 0, fff = 0;
	    String[] attrNames1 = null;
	    //CCBegin by liunan 2009-04-13
	    /*if (tt >= 0 || t > 0) {
	      attrNames1 = new String[ttt + 2];
	    }
	    else {
	      attrNames1 = new String[ttt];
	    }*/
	    //CCEnd by liunan 2009-04-13
	    attrNames1 = new String[ttt];
	    try {
	      for (int i = 0; i < attrNames.length(); i++) {
	        if (attrNames.charAt(i) == ',') {
	          String str = attrNames.substring(ff, i);
	          //CCBegin by liunan 2009-04-13
	          /*if (str.equals("װ��·��-2")) {
	            attrNames1[fff] = "װ��·��-2";
	            fff++;
	            attrNames1[fff] = "װ��·�ߺϼ���-2";
	            fff++;
	            attrNames1[fff] = "װ��ϼ�����-2";
	            fff++;
	          }
	          else if (tt < 0 && str.equals("����·��-2")) {
	            attrNames1[fff] = "����·��-2";
	            fff++;
	            attrNames1[fff] = "װ��·�ߺϼ���-2";
	            fff++;
	            attrNames1[fff] = "װ��ϼ�����-2";
	            fff++;
	          }
	          else {*/
	            attrNames1[fff] = str;
	            fff++;
	          //}
	          //CCEnd by liunan 2009-04-13
	          ff = i + 1;
	        }

	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    String[] affixAttrNames1 = null;
	    String[] routeNames = null;
	    PersistService pService = (PersistService) EJBServiceHelper.
	        getPersistService();
	    QMPartIfc partIfc = (QMPartIfc) pService.refreshInfo(partID);
	    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc) PartServiceRequest.
	        findPartConfigSpecIfc();
	  //CCBegin by leix	 2010-12-20  �����߼��ܳ���������,���Ӳ���islogic
	    Vector v =PartServiceRequest.setMaterialList(partIfc, attrNames1, affixAttrNames1,
	                                     routeNames, partConfigSpecIfc,islogic);
	  //CCEnd by leix	 2010-12-20  �����߼��ܳ���������
	    return v;
	  }

	  /**
	   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
	   * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿���������嵥��Ϣ��
	   * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
	   * 0����ǰpart��BsoID��
	   * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
	   * 2����ǰpart�ı�ţ�
	   * 3����ǰpart�����ƣ�
	   * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
	   * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
	   *              ��������������ԣ��������ж��Ƶ����Լӵ���������С�
	   * @param partID String ָ���㲿����bsoID
	   * @param attrNames String ���Ƶ����ԣ�����Ϊ��
	   * @throws QMException
	   * @return Vector
	   * @author liunan 2008-08-01
	   */
	  public Vector getMaterialList2(String partID, String attrNames) throws
	      QMException {
	    attrNames = attrNames.trim();
	    if (attrNames == null || attrNames.length() == 0) {
	      Vector result = new Vector();
	      return result;
	    }

	    int tt = attrNames.indexOf("װ��·��-2,");
	    int t = attrNames.indexOf("����·��-2,");
	    int ttt = 0;
	    for (int i = 0; i < attrNames.length(); i++) {
	      if (attrNames.charAt(i) == ',') {
	        ttt++;
	      }
	    }
	    int ff = 0, fff = 0;
	    String[] attrNames1 = null;
	    if (tt >= 0 || t > 0) {
	      attrNames1 = new String[ttt + 2];
	    }
	    else {
	      attrNames1 = new String[ttt];
	    }
	    try {
	      for (int i = 0; i < attrNames.length(); i++) {
	        if (attrNames.charAt(i) == ',') {
	          String str = attrNames.substring(ff, i);
	          if (str.equals("װ��·��-2")) {
	            attrNames1[fff] = "װ��·��-2";
	            fff++;
	            attrNames1[fff] = "װ��·�ߺϼ���-2";
	            fff++;
	            attrNames1[fff] = "װ��ϼ�����-2";
	            fff++;
	          }
	          else if (tt < 0 && str.equals("����·��-2")) {
	            attrNames1[fff] = "����·��-2";
	            fff++;
	            attrNames1[fff] = "װ��·�ߺϼ���-2";
	            fff++;
	            attrNames1[fff] = "װ��ϼ�����-2";
	            fff++;
	          }
	          else {
	            attrNames1[fff] = str;
	            fff++;
	          }
	          ff = i + 1;
	        }
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }

	    String[] affixAttrNames1 = null;
	    String[] routeNames = null;

	    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
	    QMPartIfc partIfc = (QMPartIfc) pService.refreshInfo(partID);

	    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc) PartServiceRequest.
	        findPartConfigSpecIfc();
	    Vector v =PartServiceRequest.setMaterialList2(partIfc, attrNames1, affixAttrNames1,
	                                     routeNames, partConfigSpecIfc);
	    return v;
	  }
	    
//		CCEnd by leix 2009-11-30

    /**
     * �õ�ָ����������������ָ����������
     * @param priInfo QMEquipmentInfo ��������
     * @return Vector ApplicationDataInfo ������
     * @throws RationException 
     * @author liunan 2009-12-23 ���ݽ��Ҫ��Ϊ�㲿����Ӹ����Ĳ鿴��
     */
    public Vector getContents(String id)throws QMException
    {
    	PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
      QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(id);
    	Vector c = null;
    	try
    	{
    		ContentService contentService = (ContentService)EJBServiceHelper.getService("ContentService");
    		c = (Vector)contentService.getContents((ContentHolderIfc)partIfc);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
		  return c;
    }
    
    //CCBegin by liunan 2011-08-08 �����㲿��һ���Ӽ����������������ں�·�����ԡ�
   /**
	   * �����㲿��id��ȡ���㲿����һ���Ӽ���Ϣ�б�
	   * @param partID String �㲿��id
	   * @throws QMException
	   * @return Vector ����Object�����鼯�ϣ������ÿ��Ԫ�ض����ַ�������Щ�ַ�������Ϊ�㲿���ı��,����,�汾,��ͼ,����,ʹ�÷�ʽ,��������״̬,����·��,װ��·��
	   */
	  public Vector getFirstLevelStruct(String partID) throws
	      QMException {
	    PersistService pService = (PersistService) EJBServiceHelper.
	        getPersistService();
	    QMPartIfc partIfc = (QMPartIfc) pService.refreshInfo(partID);
	    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc) PartServiceRequest.
	        findPartConfigSpecIfc();
	    return getFirstLevelStruct(partIfc, partConfigSpecIfc);
	  }

	  /**
	   * �����㲿��ֵ�������ù淶��ȡ���㲿����һ���Ӽ���Ϣ�б�
	   * @param part QMPartIfc �㲿��ֵ����
	   * @param config PartConfigSpecIfc ���ù淶
	   * @throws QMException
	   * @return Vector ����Object�����鼯�ϣ������ÿ��Ԫ�ض����ַ�������Щ�ַ�������Ϊ�㲿���ı��,����,�汾,��ͼ,����,ʹ�÷�ʽ,��������״̬,����·��,װ��·��
	   */
	  private Vector getFirstLevelStruct(QMPartIfc part, PartConfigSpecIfc config) throws
	      QMException {
	    Collection coll = PartServiceRequest.getUsesPartIfcs(part, config);
	    Vector result = getPartsAttributeArrays1(part, coll);
	    return sortStringArrays(result, 0);
	  }

	  /**
	   * �������ַ�����ʾ�㲿�����Ե����鼯��
	   * @param partAndLink Collection �������鼯�ϣ�����������Ԫ�أ���һ�����㲿��ʹ�ù���ֵ���󣬵ڶ������㲿��ֵ������㲿��������Ϣֵ����
	   * @return Vector ����Object�����鼯�ϣ������ÿ��Ԫ�ض����ַ�������Щ�ַ�������Ϊ�㲿���ı��,����,�汾,��ͼ,����,ʹ�÷�ʽ,����,��Դ,��������״̬
	   */
	  private Vector getPartsAttributeArrays1(QMPartIfc part, Collection partAndLink) {
	  	
	    String makeRoute = "";
	    try
	    {
	    	TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.getService("TechnicsRouteService");
	    	Vector routeVec = trService.getListAndBrances(null, part, new String[]{"����·��",}, "");
	    	if (routeVec != null && routeVec.size() > 0)
	    	{
          HashMap map = (HashMap) routeVec.elementAt(0);
          makeRoute = (String) map.get("����·��");
        }
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    
	    Vector result = new Vector();
	    if (partAndLink == null || partAndLink.size() == 0) {
	      return result;
	    }
	    Iterator iterator = partAndLink.iterator();
	    while (iterator.hasNext()) 
	    {
	      Object obj = iterator.next();
	      if (obj instanceof Object[])
	      {
	        Object[] part_link = (Object[]) obj;
	        if (part_link[1] instanceof QMPartIfc && part_link[0] instanceof PartUsageLinkIfc) 
	        {
	          result.add(getPartAttriArray1(part, (QMPartIfc) part_link[1],
	                                       (PartUsageLinkIfc) part_link[0], makeRoute));
	        }
	      }
	    }
	    return result;
	  }

	  /**
	   * �������ַ�����ʾ�㲿�����Ե�����
	   * @param part QMPartIfc �㲿��ֵ����
	   * @param link PartUsageLinkIfc ʹ�ù���ֵ����
	   * @return Vector ����Object�����鼯�ϣ������ÿ��Ԫ�ض����ַ�������Щ�ַ�������Ϊ�㲿���ı��,����,�汾,��ͼ,����,ʹ�÷�ʽ,��������״̬,����·��,װ��·��
	   */
	  private Object[] getPartAttriArray1(QMPartIfc parentpart, QMPartIfc part, PartUsageLinkIfc link, String route) {
	    String makeRoute = "";
	    String assRoute = "";
	    //CCBegin SS6
	    String colorFlag="";
	    //CCend SS6
	    try
	    {
	    	TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.getService("TechnicsRouteService");
	    	Vector routeVec = trService.getListAndBrances(parentpart, part, new String[]{"����·��","װ��·��"}, "");
	    	if (routeVec != null && routeVec.size() > 0)
	    	{
          HashMap map = (HashMap) routeVec.elementAt(0);
          makeRoute = (String) map.get("����·��");
          assRoute = (String) map.get("װ��·��");
          //CCbegin SS6
          colorFlag=(String)map.get("��ɫ����ʶ");
          //CCend SS6
        }

        //����·���С��á�������ȡ�ø��� ����·����Ϊװ��·�ߡ�
        if(assRoute.equals("")&&makeRoute.indexOf("��")<0)
        {
        	assRoute = route;
        }
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    //CCBegin SS6
	   // Object[] result = new Object[12];
	    Object[] result = new Object[13];
	    //CCEnd SS6
	    result[1] = part.getPartNumber();
	    result[2] = part.getPartName();
	    result[3] = part.getVersionValue();
	    result[4] = part.getViewName();
	    //CCBegin SS17
	    //result[5] = link.getQMQuantity().getQuantity() + "";
	    //result[6] = link.getQMQuantity().getDefaultUnit().getDisplay();
	    result[5] = link.getQuantity() + "";
	    result[6] = link.getDefaultUnit().getDisplay();
	    //CCEnd SS17
	    result[7] = part.getPartType().getDisplay();
	    result[8] = part.getProducedBy().getDisplay();
	    result[9] = part.getLifeCycleState().getDisplay();
	    result[10] = makeRoute;
	    result[11] = assRoute;
	    result[0] = part.getBsoID();
	    //CCBegin SS6
	    result[12]=colorFlag;
	    //CCEnd SS6
	    return result;
	  }
	  //CCEnd by liunan 2011-08-08


//CCBegin SS1
  /**
   * �����㲿��ֵ�����ø��㲿�����������вο��ĵ�(DocMasterIfc)������Ϣֵ����ļ��ϡ�
   * @param partID :Stringָ�����㲿����ֵ����bsoID��
   * @return Vector �㲿���ο��ĵ�������Ϣֵ����(DocMasterIfc)�ļ��ϣ�
   * �ȸ����㲿����ȡ���еĲο��ĵ�����Ϣ(DocMasterIfc)�ļ���,�ٷ���DocMasterIfc��һЩ
   * �������Եļ���:����ֵVector��ÿ��Ԫ�ض���String[]:
   * <br>String[0] : �ĵ�Master��bsoID;
   * <br>String[1] : �ĵ��ı�ţ�
   * <br>String[2] : �ĵ������ƣ�
   * @throws QMException
   */
  public Vector getAllReferencesDocMasters(String partID)
      throws QMException
  {
    PartDebug.trace(this, "getAllReferencesDocMasters" + "DocMasters(String) begin ....");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    PartDebug.trace(this, "getAllReferencesDocMasters" + "DocMasters(String) end....return is Vector");
    Vector resultVector = new Vector();
    Collection coll = vcService.allIterationsOf(partIfc.getMaster());
		// �����ж����԰汾����,�����ҵ���һ�����������ļ���
		for (Iterator iter = coll.iterator(); iter.hasNext();) 
		{
			QMPartIfc ifc = (QMPartIfc) iter.next();
			
			Vector docVector = getDocMastersByPartIfc(ifc);
			Object obj = null;
			for(int i = 0; i < docVector.size(); i++)
			{
				obj = docVector.elementAt(i);
				if(obj instanceof DocMasterIfc)
				{
          DocMasterIfc docMasterIfc = (DocMasterIfc)obj;
          String[] temp = new String[4];
          //CCBegin SS8
          if(ifc.getIterationIfLatest())
          {
          //CCEnd SS8
        	  temp[0] = docMasterIfc.getBsoID();
              temp[1] = docMasterIfc.getDocNum();
              temp[2] = docMasterIfc.getDocName();
        	  temp[3] = ifc.getVersionValue();
        	  resultVector.addElement(temp);
          //CCBegin SS8
          }
          //CCEnd SS8
        }
      }			
		}
    return resultVector;
  }
  //CCEnd SS1	
    //CCBegin SS5
  public Vector getSubCompBom(String partID, String attrNames,String source,String type ,String makeV,String conV)throws QMException{
	  Vector vv=getBOMList(partID, attrNames,source,type);
	  Vector result=new Vector();
	  PersistService persistService = (PersistService) EJBServiceHelper
		.getService("PersistService");
	  result.add(vv.elementAt(0));
	  if(vv.size()>1){
		  for (int i = 1; i < vv.size(); i++) {
			  Object[] array = (Object[]) vv.elementAt(i);
			  String makeV1=(String)array[8];
			  String conV1=(String)array[9];
			  String codeshorten="";
			  String classsort="";
			  String codeshorten1="";
			  String classsort1="";
			  //ֻ������·�ߵ�λ
			  if(!makeV.equals("")&&conV.equals("")){
				  if(makeV.startsWith("Coding_")){
					  CodingInfo code=(CodingInfo)persistService.refreshInfo(makeV);
					  codeshorten =code.getShorten();
					  if(makeV1.indexOf(codeshorten)!=-1){
						  result.add(array);
					  }
					} else if (makeV.startsWith("CodingClassification_")) {
						CodingClassificationInfo codeClass = (CodingClassificationInfo) persistService
								.refreshInfo(makeV);
						classsort = codeClass.getClassSort();
						if (makeV1.indexOf(classsort) != -1) {
							result.add(array);
						}
					}
			  }
			  //ֻ��װ��·�ߵ�λ
			  if(!conV.equals("")&&makeV.equals("")){
				  if(conV.startsWith("Coding_")){
					  CodingInfo code=(CodingInfo)persistService.refreshInfo(conV);
					  codeshorten =code.getShorten();
					  if(conV1.indexOf(codeshorten)!=-1){
						  result.add(array);
					  }
					} else if (conV.startsWith("CodingClassification_")) {
						CodingClassificationInfo codeClass = (CodingClassificationInfo) persistService
								.refreshInfo(conV);
						classsort = codeClass.getClassSort();
						if (conV1.indexOf(classsort) != -1) {
							result.add(array);
						}

					}
			  }
			  //��������·�ߵ�λ����װ��·�ߵ�λ
			  if(!makeV.equals("")&&!conV.equals("")){
				  if(makeV.startsWith("Coding_")){
					  CodingInfo code=(CodingInfo)persistService.refreshInfo(makeV);
					  codeshorten =code.getShorten();
						if (conV.startsWith("Coding_")) {
							CodingInfo code1 = (CodingInfo) persistService
									.refreshInfo(conV);
							codeshorten1 = code1.getShorten();

							if (makeV1.indexOf(codeshorten)!=-1&&conV1.indexOf(codeshorten1) != -1) {
								result.add(array);
							}

						} else if (conV.startsWith("CodingClassification_")) {
							CodingClassificationInfo codeClass1 = (CodingClassificationInfo) persistService
									.refreshInfo(conV);
							classsort1 = codeClass1.getClassSort();
							if (conV1.indexOf(classsort1) != -1
									&& makeV1.indexOf(codeshorten) != -1) {
								result.add(array);
							}
						}
				  }else if(makeV.startsWith("CodingClassification_")){
					  CodingClassificationInfo codeClass =(CodingClassificationInfo)persistService.refreshInfo(makeV);
					  classsort =codeClass.getClassSort();
					  if (conV.startsWith("Coding_")) {
							CodingInfo code1 = (CodingInfo) persistService
									.refreshInfo(conV);
							codeshorten1 = code1.getShorten();

							if (makeV1.indexOf(classsort)!=-1&&conV1.indexOf(codeshorten1) != -1) {
								result.add(array);
							}

						} else if (conV.startsWith("CodingClassification_")) {
							CodingClassificationInfo codeClass1 = (CodingClassificationInfo) persistService
									.refreshInfo(conV);
							classsort1 = codeClass1.getClassSort();
							if (conV1.indexOf(classsort1) != -1
									&& makeV1.indexOf(classsort) != -1) {
								result.add(array);
							}
						}
				  }
			  }
			  //����Ҫ·�ߵ�λ����
			  if(makeV.equals("")&&conV.equals("")){
				  result.add(array);
			  }
		  }
	  }
	  return result;  
  }
  //CCEnd SS5
  //CCBegin SS7
/*  public static QMPartIfc getBaseLinePartByPartNum(String partNum)throws QMException{
  	QMPartIfc partIfc=null;
    PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
    QMQuery query1 = new QMQuery("QMPartMaster");
    QueryCondition condition1 = new QueryCondition("partNumber", "=", partNum);
    query1.addCondition(condition1);
    Collection partMasterIfcs=ps.findValueInfo(query1);
    Collection partIfcs=new ArrayList();
    if (partMasterIfcs!=null && partMasterIfcs.size()>0)
    {
    	QMPartMasterIfc partMasterIfc=(QMPartMasterIfc)partMasterIfcs.iterator().next();
    	QMQuery query2 = new QMQuery("QMPart");
    	QueryCondition condition2 = new QueryCondition("masterBsoID", "=", partMasterIfc.getBsoID());
    	query2.addCondition(condition2);
    	partIfcs=ps.findValueInfo(query2);
    	
		}
		Collection partBsoIDs=new ArrayList();
		for (Iterator iterator = partIfcs.iterator(); iterator.hasNext(); )
		{
			QMPartIfc partIfc2=(QMPartIfc) iterator.next();
			partBsoIDs.add(partIfc2.getBsoID());
//  System.out.println("-------"+partIfc2.getBsoID());
		}
    
  	QMQuery query = new QMQuery("ManagedBaseline");
    QueryCondition qc= new QueryCondition("baselineName",  QueryCondition.LIKE, "��������");
    query.addCondition(qc);
    Collection managedColl = ps.findValueInfo(query);
    if (managedColl!=null && managedColl.size()>0)
    {
			ManagedBaselineIfc a_BaselineIfc = (ManagedBaselineIfc)managedColl.iterator().next();
			Collection baselineables = ps.navigateValueInfo(a_BaselineIfc, "baseline","BaselineLink", true);
			for (Iterator iterator = baselineables.iterator(); iterator.hasNext(); )
			{
				QMPartIfc partIfc1=(QMPartIfc) iterator.next();
 //   System.out.println("-------++++++"+partIfc1.getBsoID());
				if (partBsoIDs.contains(partIfc1.getBsoID()))
				{
					partIfc=partIfc1;
					break;
				}
			}

  	}
//    System.out.println("-------++++++%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+partIfc);
  	return partIfc;
  }
*/
  public static HashMap getPartsByBaseLine()throws QMException{
  	HashMap map=new  HashMap();  
    PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
  	QMQuery query = new QMQuery("ManagedBaseline");
    QueryCondition qc= new QueryCondition("baselineName",  QueryCondition.LIKE, "��������");
    query.addCondition(qc);
    Collection managedColl = ps.findValueInfo(query);
    if (managedColl!=null && managedColl.size()>0)
    {
			ManagedBaselineIfc a_BaselineIfc = (ManagedBaselineIfc)managedColl.iterator().next();
			Collection baselineables = ps.navigateValueInfo(a_BaselineIfc, "baseline","BaselineLink", true);
			for (Iterator iterator = baselineables.iterator(); iterator.hasNext(); )
			{
				QMPartIfc partIfc1=(QMPartIfc) iterator.next();
				map.put(partIfc1.getPartNumber(),partIfc1.getVersionID());
 			}

  	}
  	return map;
  }

  //CCEnd SS7
  
  //CCBegin SS9
  /**
   * �õ��㲿����pdf����
   * @param id String �㲿��bsoid
   * @return Vector ApplicationDataInfo ������
   * @throws QMException 
   */
  public Vector getPartPDFContents(String id)throws QMException
  {
    Vector vec = new Vector();
    try
    {
    	Vector c = getContents(id);
    	if(c!=null)
    	{
    		ContentItemIfc item;
    		ApplicationDataInfo appDataInfo = null;
    		for (Iterator iter = c.iterator(); iter.hasNext(); )
    		{
    			item = (ContentItemIfc) iter.next();
    			if (item instanceof ApplicationDataInfo)
    			{
    				appDataInfo = (ApplicationDataInfo) item;
    				if(appDataInfo.getContentRank().equals("SECONDARY"))
    				{
    					if(appDataInfo.getFileName().toUpperCase().endsWith(".PDF"))
    					{
    						vec.add(appDataInfo);
    					}
    				}
           }
         }
    	}
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }
    System.out.println("pdf'size======="+vec.size());
    return vec;
  }
  
  /**
   * �õ��㲿����edz����
   * @param id String �㲿��bsoid
   * @return Vector ApplicationDataInfo ������
   * @throws QMException 
   */
  public Vector getPartEDZContents(String id)throws QMException
  {
    Vector vec = new Vector();
    try
    {
    	PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    	Vector c = getContents(id);
    	if(c!=null)
    	{
    		ContentItemIfc item;
    		ApplicationDataInfo appDataInfo = null;
    		for (Iterator iter = c.iterator(); iter.hasNext(); )
    		{
    			item = (ContentItemIfc) iter.next();
    			if (item instanceof ApplicationDataInfo)
    			{
    				appDataInfo = (ApplicationDataInfo) item;
    				//CCBegin SS20
    				if(!(appDataInfo.getFileName().toUpperCase().endsWith(".PDF"))&&!(appDataInfo.getFileName().toUpperCase().endsWith(".DOC")))
    				//CCEnd SS20
    				{
    					vec.add(appDataInfo);
    				}
          }
        }
    	}
    	//���㲿��û���ҵ������ļ�����ôҪ��epm���ҡ�
    	if(vec.size()==0)
    	{
    		QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(id);
    		Vector epmvec = getEpmDocByPartIfc(partIfc);
    		if(epmvec!=null&&epmvec.size()>0)
    		{
    			ContentService cs = (ContentService)EJBServiceHelper.getService("ContentService");
    			Iterator iterator2 = epmvec.iterator();
    			while(iterator2.hasNext())
    			{
    				EPMDocumentIfc epmIfc = (EPMDocumentIfc) iterator2.next();
    				System.out.println("epmIfc========="+epmIfc);
    				Vector v = (Vector)cs.getContents((ContentHolderIfc)epmIfc);
    				if(v!=null)
    				{
    					ContentItemIfc item;
    					ApplicationDataInfo appDataInfo = null;
    					for (Iterator iter = v.iterator(); iter.hasNext(); )
    					{
    						item = (ContentItemIfc) iter.next();
    						if (item instanceof ApplicationDataInfo)
    						{
    							appDataInfo = (ApplicationDataInfo) item;
    							appDataInfo.setDescription(epmIfc.getBsoID());
    							vec.add(appDataInfo);
    						}
    					}
    				}
    			}
    		}
    		//CCBegin SS19
    		else
    		{
    			System.out.println(partIfc.getIterationCreator()+"==user=="+partIfc.getIterationModifier());
    			//����������Ǽ������ģ������߲��Ǽ������ģ�Ҳ����Administrator��
    			if(partIfc.getIterationModifier().equals("User_10634")&&!partIfc.getIterationCreator().equals("User_113")&&!partIfc.getIterationCreator().equals("User_10634"))
    			{
    				//�����Դ�汾
    				String sv = PublishHelper.getIBAValue(partIfc,"sourceVersion");
    				System.out.println("�û�����Ҫ�� sourceVersion="+sv);
    				VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
    				Collection coll = vcService.allIterationsOf(partIfc.getMaster());
    				for (Iterator iter = coll.iterator(); iter.hasNext();)
    				{
    					QMPartIfc ifc = (QMPartIfc) iter.next();
    					if(ifc.getVersionValue().equals(sv))
    					{
    						epmvec = getEpmDocByPartIfc(ifc);
    						if(epmvec!=null&&epmvec.size()>0)
    						{
    							ContentService cs = (ContentService)EJBServiceHelper.getService("ContentService");
    							Iterator iterator2 = epmvec.iterator();
    							while(iterator2.hasNext())
    							{
    								EPMDocumentIfc epmIfc = (EPMDocumentIfc) iterator2.next();
    								System.out.println("epmIfc========="+epmIfc);
    								Vector v = (Vector)cs.getContents((ContentHolderIfc)epmIfc);
    								if(v!=null)
    								{
    									ContentItemIfc item;
    									ApplicationDataInfo appDataInfo = null;
    									for (Iterator iter1 = v.iterator(); iter1.hasNext(); )
    									{
    										item = (ContentItemIfc) iter1.next();
    										if (item instanceof ApplicationDataInfo)
    										{
    											appDataInfo = (ApplicationDataInfo) item;
    											appDataInfo.setDescription(epmIfc.getBsoID());
    											vec.add(appDataInfo);
    										}
    									}
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    		//CCEnd SS19
    	}
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }
    System.out.println("edz'size======="+vec.size());
    return vec;
  }
  //CCEnd SS9
  
  //CCBegin SS11
  public String[] getPartProducetView(String partnum, String partversion)
  {
  	System.out.println("getPartProducetView  partnum=="+partnum+" and partversion=="+partversion);
    String[] str = new String[3];
    try
    {
    	PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
    	VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
    	ContentService cs = (ContentService)EJBServiceHelper.getService("ContentService");
    	QMQuery query1 = new QMQuery("QMPartMaster");
    	QueryCondition condition1 = new QueryCondition("partNumber", "=", partnum);
    	query1.addCondition(condition1);
    	Collection partMasterIfcs=ps.findValueInfo(query1);
    	if (partMasterIfcs!=null && partMasterIfcs.size()>0)
    	{
    		QMPartMasterIfc partMasterIfc=(QMPartMasterIfc)partMasterIfcs.iterator().next();
    		
    		Collection coll = vcService.allIterationsOf(partMasterIfc);
    		// �����ж����԰汾����,�����ҵ���һ�����������ļ���
    		for (Iterator iter = coll.iterator(); iter.hasNext();)
    		{
    			QMPartIfc ifc = (QMPartIfc) iter.next();
    			
    			str[0] = "master";
    			str[1] = ifc.getBsoID();
    			str[2] = partMasterIfc.getBsoID();
    			if(true)
    			{
    				return str;
    			}
    			
    			if(ifc.getVersionID().equals(partversion))
    			{
    				Vector epmvec = getEpmDocByPartIfc(ifc);
    				//��epm��ȡepm�������ļ�
    				if(epmvec!=null&&epmvec.size()>0)
    				{
    					Iterator iterator2 = epmvec.iterator();
    					if(iterator2.hasNext())
    					{
    						EPMDocumentIfc epmIfc = (EPMDocumentIfc) iterator2.next();
    						Vector v = (Vector)cs.getContents((ContentHolderIfc)epmIfc);
    						if(v!=null)
    						{
    							ContentItemIfc item;
    							ApplicationDataInfo appDataInfo = null;
    							for (Iterator iter1 = v.iterator(); iter1.hasNext(); )
    							{
    								item = (ContentItemIfc) iter1.next();
    								if (item instanceof ApplicationDataInfo)
    								{
    									appDataInfo = (ApplicationDataInfo) item;;
    									str[0] = "epm";
    									str[1] = epmIfc.getBsoID();
    									str[2] = appDataInfo.getBsoID();
    									return str;
    								}
    							}
    							str[0] = "error";
    							str[1] = "�㲿��"+partnum+"��epm�ĵ�û�������ļ���";
    							str[2] = ifc.getBsoID();
    							return str;
    						}
    						else
    						{
    							str[0] = "error";
    							str[1] = "�㲿��"+partnum+"��epm�ĵ�û�������ļ���";
    							str[2] = ifc.getBsoID();
    							return str;
    						}
    					}
    				}
    				//û��epm��ȡ�㲿���������ļ���
    				else
    				{
    					Vector c = getContents(ifc.getBsoID());
    					if(c!=null)
    					{
    						ContentItemIfc item;
    						ApplicationDataInfo appDataInfo = null;
    						for (Iterator iter1 = c.iterator(); iter1.hasNext(); )
    						{
    							item = (ContentItemIfc) iter1.next();
    							if (item instanceof ApplicationDataInfo)
    							{
    								appDataInfo = (ApplicationDataInfo) item;
    								if((appDataInfo.getFileName().toUpperCase().endsWith(".EDZ")))
    								{
    									str[0] = "epm";
    									str[1] = ifc.getBsoID();
    									str[2] = appDataInfo.getBsoID();
    									return str;
    								}
    							}
    						}
    						str[0] = "error";
    						str[1] = "�㲿��"+partnum+"û�������ļ���";
    						str[2] = ifc.getBsoID();
    						return str;
    					}
    					else
    					{
    						str[0] = "error";
    						str[1] = "�㲿��"+partnum+"û�������ļ���";
    						str[2] = ifc.getBsoID();
    						return str;
    					}
    				}
    				break;
    			}
    		}
    		str[0] = "error";
    		str[1] = "ϵͳ�б��Ϊ"+partnum+"���㲿������û��"+partversion+"�汾��";
    	}
    	else
    	{
    		str[0] = "error";
    		str[1] = "ϵͳû�б��Ϊ"+partnum+"���㲿����";
    	}
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }
    System.out.println("str0======="+str[0]);
    System.out.println("str1======="+str[1]);
    System.out.println("str2======="+str[2]);
    return str;
  }
  //CCEnd SS11
  
  //CCBegin SS12
  /**
   * �жϵ�ǰ�û��Ƿ�����㲿��������������״̬����epm�в鿴Ȩ��
   * @return boolean
   */
  public boolean hasEPMReadRight(String bsoID) throws QMException
  {
  	boolean hasView = true;
  	SessionService ss = (SessionService)EJBServiceHelper.getService("SessionService");
  	AccessControlService accessControlService = (AccessControlService)EJBServiceHelper.getService("AccessControlService");
  	PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
  	
  	User user =ss.getCurUser();
  	QMPartIfc partIfc = (QMPartIfc)ps.refreshInfo(bsoID);
  	
  	//CCBegin SS15
  	//hasView = accessControlService.hasAccess(user,"EPMDocument",partIfc.getDomain(),partIfc.getLifeCycleState(),QMPermission.READ.toString());
  	Vector epmvec = getEpmDocByPartIfc(partIfc);
  	if(epmvec!=null&&epmvec.size()>0)
  	{
  		System.out.println("aaa");
  		Iterator ite = epmvec.iterator();
  		if(ite.hasNext())
  		{
  			EPMDocumentIfc epmIfc = (EPMDocumentIfc)ite.next();
  			hasView = accessControlService.hasAccess(user,epmIfc,QMPermission.READ.toString());
  		}
  		System.out.println("aaa=="+hasView);
  	}
  	else
  	{
  		System.out.println("bbb");
  		hasView = accessControlService.hasAccess(user,"EPMDocument",partIfc.getDomain(),partIfc.getLifeCycleState(),QMPermission.READ.toString());
  		System.out.println("bbb=="+hasView);
  		/*if(!hasView)
  		{
  			hasView = accessControlService.hasAccess(user,partIfc,QMPermission.READ.toString());
  		}
  		System.out.println("bbb 22=="+hasView);*/
  		//CCBegin SS18
  		if(!hasView)
  		{
  			hasView = partIfc.getAdHocAcl().getEntrySet().checkPermission(user, "read");
  			System.out.println("bbb 22=="+hasView);
  		}
  		//CCEnd SS18
  	}
  	//CCEnd SS15
  	
  	System.out.println("hasEPMReadRight  "+bsoID+"=="+hasView);
  	return hasView;
  }
  /**
   * �жϵ�ǰ�û��Ƿ�����㲿��������������״̬����epm������Ȩ��
   * @return boolean
   */
  public boolean hasEPMDownloadRight(String bsoID) throws QMException
  {
  	boolean hasView = true;
  	SessionService ss = (SessionService)EJBServiceHelper.getService("SessionService");
  	AccessControlService accessControlService = (AccessControlService)EJBServiceHelper.getService("AccessControlService");
  	PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
  	
  	User user =ss.getCurUser();
  	QMPartIfc partIfc = (QMPartIfc)ps.refreshInfo(bsoID);
  	
  	//CCBegin SS15
  	//hasView = accessControlService.hasAccess(user,"EPMDocument",partIfc.getDomain(),partIfc.getLifeCycleState(),QMPermission.DOWNLOAD.toString());
  	Vector epmvec = getEpmDocByPartIfc(partIfc);
  	if(epmvec!=null&&epmvec.size()>0)
  	{
  		Iterator ite = epmvec.iterator();
  		if(ite.hasNext())
  		{
  			EPMDocumentIfc epmIfc = (EPMDocumentIfc)ite.next();
  			hasView = accessControlService.hasAccess(user,epmIfc,QMPermission.DOWNLOAD.toString());
  		}
  	}
  	else
  	{
  		hasView = accessControlService.hasAccess(user,"EPMDocument",partIfc.getDomain(),partIfc.getLifeCycleState(),QMPermission.DOWNLOAD.toString());
  		/*if(!hasView)
  		{
  			hasView = accessControlService.hasAccess(user,partIfc,QMPermission.DOWNLOAD.toString());
  		}*/
  	}
  	//CCEnd SS15
  	
  	System.out.println("hasEPMDownloadRight  "+bsoID+"=="+hasView);
  	return hasView;
  }
  //CCEnd SS12
  
  //CCBegin SS13
	/**
	 * ���ָ���㲿���ı����ĵ�
	 * @param id String
	 * @return Collection
	 */
	public Collection getVariantSpec(String id)
	{
		Collection result = new Vector();
		try
		{
			//CCBegin SS16
			if(id.startsWith("GenericPart"))
			{
				System.out.println("gp");
				FamilyWebHelper fwh = new FamilyWebHelper();
				result = (Collection)fwh.getVariantSpec(id);
			}
			else if(id.startsWith("QMPart"))
			{
				System.out.println("part");
				QMQuery query = new QMQuery("VariantSpecVariantLink");
				QueryCondition cond = new QueryCondition("leftBsoID", "=", id);
				query.addCondition(cond);
				PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
				Collection col = ps.findValueInfo(query);
				if (col != null && col.size() != 0)
				{
					Iterator iter = col.iterator();
					while (iter.hasNext())
					{
						VariantSpecVariantLinkIfc link = (VariantSpecVariantLinkIfc)iter.next();
						VariantSpecIfc variantspec = (VariantSpecIfc)ps.refreshInfo(link.getRightBsoID());
						String string[] = new String[5];
						String iconid = DocServiceHelper.getObjectImageHtml((BaseValueInfo) variantspec);
						string[0] = iconid;
						string[1] = variantspec.getDocNum();
						string[2] = variantspec.getDocName();
						string[3] = variantspec.getVersionValue();
						string[4] = variantspec.getBsoID();
						result.add(string);
					}
				}
		  }
		  //CCEnd SS16
		}
		catch (QMException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	//CCEnd SS13
	
	//CCBegin SS14
	/**
   * ����һ�����嵥�������߼��ܳɱ������õ���������ء�   * �����ȡ����
   * 1���㼶�ɵ��������ȡװ��·��Ϊ���ܡ����㲿����������װ��ͼ���߼��ܳɣ�
   * 2��װ��ͼ����ţ���5-7Ϊ001��2��3��5�ȣ�
   * 3���߼��ܳ�����ţ���5λΪ��G��������·�߰������ܡ���
   * 4��������ŵ�5λΪ��G��������·�߰������ܡ�ʱ��ȡ��һ�㼶װ��·�߰������ܡ����Ӽ���
   * 5������·�߲������ܡ���װ��·�ߺ����ܡ�ʱ������ȡ�Ӽ�����ȡͬһ�㼶װ��·�ߺ����ܡ��ļ���
   * 6������·�߰������ܡ���װ��·��Ҳ�������ܡ�ʱ������ȡ�Ӽ�����ȡͬһ�㼶װ��·�ߺ����ܡ��ļ���
   * 7�������ȡ�߼��ܳ�ǰ4λ��
   * ��һ�ε��Բ������
   * 8�����Ӷԡ���������������1000410�������ȡ����ֱ�ӽ�����Ÿ�ֵΪ��1000����
   * 9����1000410�Ӽ��и��߼��ܳ�����·�ߺ����ܡ����Ӽ�װ��·�ߺ����ܡ����㲿��������ȡ��
   * 10������"��������"����"������ĸ",װ��·�߰���"��"���Ӽ���
   * 11�����ӵ������б�ͷ�������˳�򣬼������Ϻš���������������š�����������·�ߡ�װ��·�ߡ���������״̬����׼��š���һ��������ţ�
   * �ڶ��ε��Բ������
   * 12�������߼��ܳ�����·�ߺ����ܡ����Ӽ�װ��·�ߺ����ܡ��ļ���ȡI�������Ǹ�����ŵ�5λ�Ƿ�Ϊ��G������
   * 13�������߼��ܳ�������·�߲������ܡ�ʱ������ȡ�Ӽ���
   * 14�����Ӷԡ����������������������ƶ����߼��ܳɡ��������ȡ����ֱ�ӽ�����Ÿ�ֵΪ��2500����
   * 15�����Ӷԡ�����������������������ͷ�ܳɡ��̻Һ��ܳɡ��泵���ߵơ����Ǿ������ܳɡ����������ȡ����ֱ�ӽ�����Ÿ�ֵΪ��3900����
   * 16�����Ӷԡ����Ϻš�������5000010���������ȡ����ֱ�ӽ�����Ÿ�ֵΪ��5000����
   * �����ε��Բ������
   * 17��2400010��2500010��3722024��3902G00ֱ����ȡ
	 */
	public Vector getExportFirstLeveList(String partID, String attrNames) throws QMException
	{
      Vector vec = new Vector();
	    attrNames = attrNames.trim();
	    if (attrNames == null || attrNames.length() == 0)
	    {
	      Vector result = new Vector();
	      return result;
	    }

	    int tt = attrNames.indexOf("װ��·��-2,");
	    int t = attrNames.indexOf("����·��-2,");
	    int ttt = 0;
	    for (int i = 0; i < attrNames.length(); i++)
	    {
	      if (attrNames.charAt(i) == ',')
	      {
	        ttt++;
	      }
	    }
	    int ff = 0, fff = 0;
	    String[] attrNames1 = null;
	    attrNames1 = new String[ttt];
	    try {
	      for (int i = 0; i < attrNames.length(); i++)
	      {
	        if (attrNames.charAt(i) == ',')
	        {
	          String str = attrNames.substring(ff, i);
	          attrNames1[fff] = str;
	          fff++;
	          ff = i + 1;
	        }
	      }
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    String[] affixAttrNames1 = null;
	    String[] routeNames = null;
	    PersistService pService = (PersistService) EJBServiceHelper.getPersistService();
	    QMPartIfc partIfc = (QMPartIfc) pService.refreshInfo(partID);
	    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc) PartServiceRequest.findPartConfigSpecIfc();
	    //�����߼��ܳ������嵥�ķ�����Ȼ��Խ�����й���
	    Vector v =PartServiceRequest.setMaterialList(partIfc, attrNames1, affixAttrNames1, routeNames, partConfigSpecIfc,true);
	    
      int templevel = -1;
      String zzroute = "";
      String zproute = "";
      String num = "";
      int curlevel = 0;
      boolean is1000410 = false;
      int level1000410 = -1;
      int levesubl1000410 = -1;
      for (int ia = 0; ia < v.size(); ia++)
      {
        Object temp[] = (Object[]) v.elementAt(ia);
      	if(ia==0)
      	{
      		vec.add(addZiZuElement(temp,true));
      		continue;
      	}
      	num = temp[4]==null?"":temp[4].toString();
      	zzroute = temp[7]==null?"":temp[7].toString();
        zproute = temp[8]==null?"":temp[8].toString();
        curlevel = new Integer(temp[3].toString());
        System.out.println("num=="+num+"  and  zzroute=="+zzroute+"  and  zproute=="+zproute+"  and  curlevel=="+curlevel+"  and  templevel=="+templevel+"  and  is1000410=="+is1000410+"  and  level1000410=="+level1000410+"  and  levesubl1000410=="+levesubl1000410);
        
        //������岿�����ɵ� J001���㲿����û�б༭·������£���ȡԭ���岿����·�ߡ�
        if(isGpart(num)&&zzroute.equals("")&&zproute.equals(""))
        {
        	String gpartid = getOriginGPartID(temp[0].toString());
        	if(gpartid!=null&&!gpartid.equals(""))
        	{
        		QMPartIfc gp = (QMPartIfc)pService.refreshInfo(gpartid);
        		TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.getService("TechnicsRouteService");
        		Collection coll = trService.getRouteInfomationByPartmaster(gp.getMasterBsoID());
        		if(coll!=null&&coll.size()>0)
        		{
        			String[] str = (String[])coll.iterator().next();
        			zzroute = str[0]==null?"":str[0].toString();
        			zproute = str[1]==null?"":str[1].toString();
        			System.out.println("����󣬵õ�zzroute=="+zzroute+"  and  zproute=="+zproute);
        		}
        	}
        }
        
        if(checkSpecialPart(temp))
        {
        	vec.add(addZiZuElement(temp,false));
        	System.out.println("��������00");
        	//5000010���⴦��ֻ�����������Ӽ�
        	/*if(num.startsWith("5000010"))
        	{
        		templevel = curlevel;
        		System.out.println("templevel 00=="+templevel);
        	}*/
        	continue;
        }
        //curlevel==3  and  templevel==-1  and  is1000410==true  and  level1000410==1  and  levesubl1000410==2
        if(is1000410&&level1000410<curlevel)
        {
        	if(levesubl1000410==-1)
        	{
        		//�߼��ܳ� ����·�� ���С��ܡ���
        		if(isGpart(num)&&zzroute.indexOf("��")!=-1)
        		{
        			levesubl1000410 = curlevel;
        		}
        	}
        	else if(levesubl1000410==curlevel)
        	{
        		levesubl1000410 = -1;
        		//�߼��ܳ� ����·�� ���С��ܡ���
        		if(isGpart(num)&&zzroute.indexOf("��")!=-1)
        		{
        			levesubl1000410 = curlevel;
        		}
        	}
        	else if(levesubl1000410==(curlevel-1)&&zproute.indexOf("��")!=-1)
        	{
        		vec.add(addZiZuElement(temp,false));
        		System.out.println("��������11");
        	}
        	else if(levesubl1000410==(curlevel-1)&&isGpart(num)&&zzroute.indexOf("��")!=-1)
        	{
        		levesubl1000410 = curlevel;
        	}
        }
        else
        {
        	if(level1000410==curlevel)
        	{
        		level1000410 = -1;
        		levesubl1000410 = -1;
        		is1000410 = false;
        	}
        	
        	if(num.startsWith("1000410")||num.startsWith("5000010")||num.startsWith("280001")||num.startsWith("2400010")||num.startsWith("2500010"))
        	{
        		vec.add(addZiZuElement(temp,false));
        		System.out.println("�������� 22");
        		is1000410 = true;
        		level1000410 = curlevel;
        		continue;
        	}
        			
        	if(templevel==-1)
        	{
        		if(checkIsAddPart(temp))
        		{
        			vec.add(addZiZuElement(temp,false));
        			System.out.println("�������� 33");
        			templevel = curlevel;
        		}
        		
        		//Gpart�����첻�������ܡ�����ȡ�Ӽ���
        		if(isGpart(num)&&zzroute.indexOf("��")==-1)
        		{
        			templevel = curlevel;
        			System.out.println("templevel 11=="+templevel);
        			continue;
        		}
        	}
        	else if(templevel>=curlevel)
        	{
        		templevel = -1;
        		
        		//Gpart�����첻�������ܡ�����ȡ�Ӽ���
        		if(isGpart(num)&&zzroute.indexOf("��")==-1)
        		{
        			templevel = curlevel;
        			System.out.println("templevel 22=="+templevel);
        			if(checkIsAddPart(temp))
        			{
        				vec.add(addZiZuElement(temp,false));
        				System.out.println("�������� 33");
        			}
        			continue;
        		}
        		
        		if(checkIsAddPart(temp))
        		{
        			vec.add(addZiZuElement(temp,false));
        			System.out.println("�������� 44");
        			templevel = curlevel;
        		}
        	}
        	/*else if(templevel>curlevel)
        	{
        		templevel = -1;
        		
        		//Gpart�����첻�������ܡ�����ȡ�Ӽ���
        		if(isGpart(num)&&zzroute.indexOf("��")==-1)
        		{
        			templevel = curlevel;
        			System.out.println("templevel 22=="+templevel);
        			if(checkIsAddPart(temp))
        			{
        				vec.add(addZiZuElement(temp,false));
        				System.out.println("�������� 55");
        			}
        			continue;
        		}
        		
        		if(checkIsAddPart(temp))
        		{
        			vec.add(addZiZuElement(temp,false));
        			System.out.println("�������� 66");
        			templevel = curlevel;
        		}
        	}*/
        }
      }
	    return vec;
	}
	
	private boolean isGpart(String num) throws QMException
	{
		boolean flag = false;
		if(num.length()>=5&&num.charAt(4)=='G')
		{
			flag = true;
		}
		return flag;
	}
	
	/**
	 * �����������ݣ��жϸü��Ƿ��������������������
	 * ���ǹ��岿��������װ��ͼ��װ��·�ߺ��С��ܡ�
	 * ��4λ�ǲ㼶����5λ�Ǳ�ţ���6λ�����ƣ���10λ��װ��·��
	 */
	private boolean checkIsAddPart(Object temp[]) throws QMException
	{
		boolean flag = false;
		String zproute = temp[8]==null?"":temp[8].toString();
		//if(isGpart(temp[4].toString())&&temp[5].toString().indexOf("װ��ͼ")==-1&&zproute.indexOf("��")!=-1)
		if(zproute.indexOf("��")!=-1)
		{
			flag = true;
		}
		//û�и������򸸼��Ǳ�׼���ģ�������
		if(temp[11].equals("")||temp[11].toString().startsWith("Q")||temp[11].toString().startsWith("CQ")||temp[11].toString().startsWith("T"))
		//if(temp[11].equals("")||!java.lang.Character.isDigit(temp[11].toString().charAt(0)))
		{
			flag = false;
		}
		return flag;
	}
	
	private boolean checkSpecialPart(Object temp[]) throws QMException
	{
		boolean flag = false;
		String num = temp[4].toString();
		String name = temp[5].toString();
		//if(num.startsWith("5000010"))
		if(num.startsWith("3722024"))
		{
			flag = true;
		}
		if(name.indexOf("������ĸ")!=-1||name.indexOf("������ͷ�ܳ�")!=-1||name.indexOf("�̻Һ��ܳ�")!=-1||name.indexOf("�泵���ߵ�")!=-1||name.indexOf("���Ǿ������ܳ�")!=-1||name.indexOf("�����ƶ����߼��ܳ�")!=-1)
		{
			flag = true;
		}
		return flag;
	}
	
	/**
	 * �����߼��ܳ����������һ�����飬�ڵ�7λ�������顣
	 */
	private Object[] addZiZuElement(Object[] obj, boolean flag) throws QMException
	{
		String[] str = new String[obj.length+1];
		for(int i=0;i<str.length;i++)
		{
			if(i<6)
			{
				str[i] = obj[i].toString();
			}
			else if(i==6)
			{
				if(flag)
				{
					str[i] = "�����";
				}
				else
				{
					if(obj[5].toString().indexOf("������ͷ�ܳ�")!=-1||obj[5].toString().indexOf("�̻Һ��ܳ�")!=-1||obj[5].toString().indexOf("�泵���ߵ�")!=-1)
					{
						str[i] = "3900";
					}
					else if(obj[4].toString().startsWith("5000010"))
					{
						str[i] = "5000";
					}
					else if(obj[4].toString().startsWith("1000410"))
					{
						str[i] = "1000";
					}
					else if(obj[4].toString().startsWith("2400010"))
					{
						str[i] = "2400";
					}
					else if(obj[11].equals("")||!java.lang.Character.isDigit(obj[11].toString().charAt(0)))
					{
						str[i] = "";
					}
					else
					{
						str[i] = obj[11].toString().substring(0,4);
					}
				}
			}
			else if(i>6)
			{
				str[i] = obj[i-1].toString();
			}
		}
		if(flag)
		{
			str[4] = "���Ϻ�";
			str[5] = "��������";
		}
		return str;
	}
	//CCEnd SS14
	
	//CCBegin SS16
	/**
	 * ���������ĵ���bsoid��ȡ����������.xml���ļ���
	 * ���ļ�����ȡ�û����ɶ���ʱѡ��Ĳ�����ֻ��ȡ���ֹ�ѡ��Ĳ����������Զ������Ĳ�������ȡ��
	 */
	public Collection getVariantSpecValue(String id)
	{
		Vector result = new Vector();
		System.out.println("getVariantSpecValue="+id);
		SAXReader sax=new SAXReader();//����һ��SAXReader����  
		//File xmlFile=new File("E:\\test\\��������.xml");//����ָ����·������file����  
		Document document=null;
		try
		{
			//document = sax.read(xmlFile);//��ȡdocument����,����ĵ��޽ڵ㣬����׳�Exception��ǰ����
			Vector vec = findAllFileByID(id);
			//System.out.println(vec.size());
			if(vec!=null)
			{
				ApplicationDataInfo appInfo = (ApplicationDataInfo)vec.elementAt(0);
			  //System.out.println(appInfo);
				InputStream is = null;
				boolean fileVaultUsed = (RemoteProperty.getProperty("registryFileVaultStoreMode", "true")).equals("true");
				//System.out.println(fileVaultUsed);
				if(fileVaultUsed)
				{
					ContentClientHelper helper = new ContentClientHelper();
					is = helper.requestDownload(appInfo.getBsoID(), "true");
				}
				else
				{
					//��ȡ��ID
					String streamID = appInfo.getStreamDataID();
					//System.out.println("streamID=="+streamID);
					StreamDataInfo sd = StreamUtil.getInfoHasContent(streamID);
					//System.out.println("sd=="+sd);
					if(sd==null)
					{
						return result;
					}
					is = new ByteArrayInputStream(sd.getDataContent());
				}
				//System.out.println(is);
				if(is==null)
				{
					return result;
				}
				document = sax.read(is);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		Element root=document.getRootElement();//��ȡ���ڵ�  
		//System.out.println(root.elements().size());
		//ѭ��һ���ڵ�
		for(Iterator i = root.elementIterator(); i.hasNext();)
		{
			Element employee = (Element) i.next();
			//ѭ�������ڵ㣬�ҵ� parameter �Ľڵ�
			for(Iterator j = employee.elementIterator(); j.hasNext();)
			{
				Element node=(Element) j.next();
				//����ڵ����ֹ����룬���Ҳ������������õ��Ĳ�����������Ҫ��ʾ�ġ�
				if(node.getQualifiedName().equals("parameter")&&node.attribute("isinput").getText().equals("yes")&&node.attribute("isdriven").getText().equals("no"))
				{
					//System.out.print(node.attribute("name").getText()+":");
					String s[] = new String[2];
					//������
					s[0] = node.attribute("name").getText();
					for(Iterator k = node.elementIterator(); k.hasNext();)
					{
						Element sub=(Element) k.next();
						if(sub.getQualifiedName().equals("value"))
						{
							//System.out.println(sub.attribute("value").getText());
							//����ֵ
							s[1] = sub.attribute("value").getText();
							result.add(s);
						}
					}
				}
			} 
		}
		return result;
	}
	//CCEnd SS16
	
	
	//CCBegin SS20
  /**
   * �жϵ�ǰ�û��Ƿ�����㲿��������������״̬����part������Ȩ��
   * @return boolean
   */
  public boolean hasPartDownloadRight(String bsoID) throws QMException
  {
  	boolean hasView = true;
  	SessionService ss = (SessionService)EJBServiceHelper.getService("SessionService");
  	AccessControlService accessControlService = (AccessControlService)EJBServiceHelper.getService("AccessControlService");
  	PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
  	
  	User user =ss.getCurUser();
  	QMPartIfc partIfc = (QMPartIfc)ps.refreshInfo(bsoID);
  	
  	hasView = accessControlService.hasAccess(user,partIfc,QMPermission.DOWNLOAD.toString());
  	
  	System.out.println("hasPartDownloadRight  "+bsoID+"=="+hasView);
  	return hasView;
  }
  
  
  /**
   * �õ��㲿����doc����
   * @param id String �㲿��bsoid
   * @return Vector ApplicationDataInfo ������
   * @throws QMException 
   */
  public Vector getPartDOCContents(String id)throws QMException
  {
    Vector vec = new Vector();
    try
    {
    	PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    	Vector c = getContents(id);
    	if(c!=null)
    	{
    		ContentItemIfc item;
    		ApplicationDataInfo appDataInfo = null;
    		for (Iterator iter = c.iterator(); iter.hasNext(); )
    		{
    			item = (ContentItemIfc) iter.next();
    			if (item instanceof ApplicationDataInfo)
    			{
    				appDataInfo = (ApplicationDataInfo) item;
    				
    				if((appDataInfo.getFileName().toUpperCase().endsWith(".DOC")))
    				{
    					vec.add(appDataInfo);
    				}
          }
        }
    	}
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }
    System.out.println("doc'size======="+vec.size());
    return vec;
  }
  //CCEnd SS20
}
