/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * SS1 ���·���Ƿ��Զ���ȡ����·�ߵĸ�ѡ���ʶ��Ĭ��ѡ�У����Զ��������·�ߣ�Ȼ����·�߱༭�������û������Ƿ��޸ġ� liunan 2013-4-17
 * SS2 ���б�˳���������������㲿������� liunan 2013-11-25
 * SS3 ͨ�����õ��ţ������㲿�� liuyang 2014-6-5
 * SS4 ͨ������֪ͨ���������㲿�� liuyang 2014-6-6
 * SS5 ������Ϊ·�߸�ѡ�� liuyang 2014-6-10
 * SS6 ���·�ߴ����ж��Ƿ����������·�� ����� 2014-10-15
 * SS7 ��Ӹ����� liunan 2015-6-18
 * SS8 ����㲿���Ǵ�������·�ߣ���״̬���ݶ�Ӧ���ϣ�partindex���ǡ��ޡ�����·�������״̬����Ϊ����ʱ��ȡһ������·�ߵ�״̬�������һ�¡� liunan 2015-8-5
 * SS9 �����㲿����ȡ����·�ߴ� liunan 2016-8-4
 * SS10 ��������׼����Ŀ liuyuzhu 2016-05-23
 * SS11 A004-2016-3415 ʵ��PDM������ݼ��������Բ����Զ�������EOLϵͳ liunan 2016-9-7
 * SS12 A004-2016-3433 ����·�����ѹ��ܣ����ڻ�ȡ�Ӽ���װ��·�߼��ϡ� liunan 2016-10-28
 */


package com.faw_qm.technics.route.ejb.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.faw_qm.codemanage.util.CodeManageTable;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListMasterIfc;
import java.util.ArrayList;
import com.faw_qm.cappclients.capproute.web.ReportLevelOneUtil;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;

/**
 * <p>Title: </p>
 * <p>Description: ����·�߷������</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: һ��������˾</p>
 * @author ������
 * @mender skybird
 * @version 1.0
 */

/**
 * �ο��ĵ���
 * Phos-REQ-CAPP-BR02(����·��ҵ�����)V2.0.doc
 * PHOS-REQ-CAPP-SRS-002(����ҵ��ҵ�������ݹ���������˵��-����·��) V2.0.doc
 * "Phos-CAPP-UC301--Phos-CAPP-UC322"��19��������
 */
public interface TechnicsRouteService
    extends BaseService {
//////////////////////////////////����Ϊ���Է���/////////////////////////////////////
  public Object processTest(int i) throws QMException;

//////////////////////////////////���Է�������////////////////////////////////////

  //����1.��������·�߱�
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC301
  //����2.���¹���·�߱�
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC302

  /**
   * @roseuid 402C5F01005A
   * @J2EE_METHOD  --  storeRouteList
   * �������¹���·���б��ǿռ����ֵ������������Ψһ�Լ�������ݿ������á�
   * �ο��ĵ�����������·�߱�PHOS-CAPP-UC301�����¹���·�߱�PHOS-CAPP-UC302
   *  ϵͳ����ҵ�����PHOS-CAPP-BR201���Ҫ��ǿյ������Ƿ�Ϊ��(E1)������ҵ�����PHOS-CAPP-BR202��鹤��·�߱����Ƿ�Ψһ(E2)��
   * ����
   * E1��
   * ϵͳ��ʾ��Ϣ����������+(CP00001)
   * E2��
   * ϵͳ��ʾ��Ϣ����������+(CW00001)
   * @return ����·�߱�ֵ����
   */
  public TechnicsRouteListIfc storeRouteList(TechnicsRouteListIfc
                                             //CCBegin SS7
                                             //routeListInfo) throws QMException;
                                             routeListInfo, ArrayList arrayList) throws QMException;
                                             //CCEnd SS7

  //����3.ɾ������·�߱�
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC303

  /**
   * @roseuid 40305EC40043
   * @J2EE_METHOD  --  deleteRouteList
   * ɾ������·�߱�������汾�ڵ�С�汾ȫ��ɾ����
   */
  public void deleteRouteList(TechnicsRouteListIfc routeListInfo) throws
      QMException;

  //����4.�༭�㲿����
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC304

  /**
   * @roseuid 403449360397
   * @J2EE_METHOD  --  getOptionalParts
   * @param level ·�߼�����ʾ��������һ��·�ߡ�
   * ���ݵ�λ���ƵĴ���ID��·�߼����ÿ�ѡ����㲿����
   * @return Collection �㲿��masterֵ����
   * ��Ӧ����
   * �����ǰ�༭�Ĺ���·�߱���һ������·�߱���ϵͳӦ�г���Ʒ�ṹ�е������㲿����Part:
   *  getAllSubParts(QMPartIfc).
   * �����ǰ�༭�Ĺ���·�߱��Ƕ�������·�߱���ϵͳӦ����·�߱�ĵ�λ���ԣ��г���Ʒ�ṹ������һ��·���ж�Ӧ��λ���㲿������Ϊ��ѡ�㲿����
   * ������Դ��3.4 PHOS-CAPP-BR204 �Ӳ�Ʒ�ṹ������㲿����
   * �ο��ĵ���
   * �༭�㲿����
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC304
   * ִ�����ڸ��¹���·�߱�ʱ���༭Ҫ���ƹ���·�ߵ��㲿������������㲿����ɾ���㲿��������㲿��ʱ�����Ը���·�߱��е�"���ڲ�Ʒ"���㲿�����������½ṹ���ṩ��ѡ�㲿�����û����Դ���ѡ��Ҫ��ӵ�����·�߱��е��㲿��������û����ڱ༭���Ƕ���·�߱���ѡ�㲿������Ӧ�����ڲ�Ʒ�Ľṹ����Ӧ���˲�Ʒ�����㲿���а�������λ·�ߵ�һ��·�߽��н�һ��ɸѡ���Ӷ����һ�ݱ�ѡ�㲿����
   */
  public Collection getOptionalParts(String codeID, String level,
                                     String productMasterID) throws QMException;

  //������°汾��ֵ����
  public BaseValueIfc getLatestVesion(MasterIfc masterInfo) throws QMException;

  /**
   * ������°汾ֵ����ļ���
   * @param c master���󼯺�
   * @throws QMException
   * (������)20060629 �����޸�
   */
  public Collection getLatestsVersion(Collection c) throws QMException;

 /**
  * add by guoxl on 20080307
  * �鿴һ��·�߱���ʱ������������
  *������ӷ���getLatestsVersion1
  */
   public HashMap getLatestsVersion1(Collection c) throws QMException;

  /**
   * 1.���¹���·�߱�2.�����ɾ��·�߱������Ĺ�����
   * @param saveCollection ������Ҫ��������masterID.
   * @param deleteCollection ������Ҫɾ�������masterID.
   * @param routeListID ����·�߱�ID.
   * @roseuid 40399FB60020
   * @J2EE_METHOD  --  saveListRoutePartLink
   * ���湤��·�߱�������Ĺ�����
   * �ɿͻ��˴�������༯�ϣ���ͳһ����
   * @return Collection ���е��빤��·�߱���������ֵ����
   */
  public void saveListRoutePartLink(HashMap saveCollection,
                                    HashMap deleteCollection,
                                    TechnicsRouteListIfc routeListInfo1) throws
      QMException;

  /**
   * ���¸������
   * added by skybird 2005.3.4
   * @param PartsToChange
   * @param routeListInfo1
   * @throws QMException
   */
  public void updateListRoutePartLink(Collection PartsToChange,
                                      TechnicsRouteListIfc routeListInfo1) throws
      QMException;

  //����5.����·�߱�(������)
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC305
  //����6.��������·�߱�
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC306

  /**
   * @param param ��ά���飬5��Ԫ�ء�����obj[0]=��ţ�obj[1]=true. ����˳�򣺱�š����ơ����𣨺��֣������ڲ�Ʒ���汾�š�
   * @roseuid 402CBAF700CA
   * @J2EE_METHOD  --  findRouteLists
   * ��ù���·�߱�������Χ����š����ơ��������ڲ�Ʒ���汾�š�
   * @return collection ����·�߱�ֵ�������°汾��
   * ��ʱҪ��ConfigService���й��ˡ�
   */
  public Collection findRouteLists(Object[][] param) throws QMException;

  /**
   * @roseuid 402CBAF700CA
   * @J2EE_METHOD  --  findRouteLists
   * ��ù���·�߱�������Χ����š����ơ�״̬�����𣨺��֣������ڲ�Ʒ��˵�������λ�á��������ڡ������ߡ������¡��汾�š�
   * @return collection ����·�߱�ֵ�������°汾��
   * ��ʱҪ��ConfigService���й��ˡ�
   */
  public Collection findRouteLists(TechnicsRouteListIfc routelistInfo,
                                   String productNumber, String createTime,
                                   String modifyTime) throws QMException;

  //����7.�鿴����·�߱�
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC307
  //getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)
////////////////////////////////////////////////////////////////////////////
  //����8.���ɱ���
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC308

  /**
   * @roseuid 4030537700ED
   * @J2EE_METHOD  --  exportRouteList
   * �������Ʊ��������ʽΪ�����ļ���
   * �ο��ĵ������ɱ���
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC308
   * ϵͳ�˳�����PHOS-CAPP-UI311������ҵ�����(PHOS-CAPP-BR206)��ʾһ��·�߱������(PHOS-CAPP-UI313)�����·�߱������(PHOS-CAPP-UI314)
   *  ������������
   */
  public String exportRouteList(String routeListID, String level_zh,
                                String exportFormat) throws QMException;

  /**
   * @roseuid 40305F8F0257
   * @J2EE_METHOD  --  getFirstLeveRouteListReport
   * ���ݹ���·�߱�ID���һ������·�߱������ݹ���·�߱�ID���������乤��·��ֵ����
   *
   * key:���ֵ����;value=����·�߽ڵ������󼯺ϣ�obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ����
   *
   * �ο��ĵ������ɱ���
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC308
   * ϵͳ�˳�����PHOS-CAPP-UI311������ҵ�����(PHOS-CAPP-BR206)��ʾһ��·�߱������(PHOS-CAPP-UI313)�����·�߱������(PHOS-CAPP-UI314)
   *  ������������
   */
  public CodeManageTable getFirstLeveRouteListReport(TechnicsRouteListIfc
      listInfo) throws QMException;

  /**
   * @roseuid 4031B65C0364
   * @J2EE_METHOD  --  getSecondLeveRouteListReport
   * ���ݹ���·�߱�ID��ö�������·�߱������ݹ���·�߱�ID���������乤��·��ֵ����
   * @return Map
   * key:���ֵ����;value=����·��ֵ��������
   * �����а����������ϣ�obj[0]=һ������·�߽ڵ������󼯺ϣ������������顣 obj1[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj1[1]=װ��·�߽ڵ�ֵ����
   * obj[1] = ��������·�߽ڵ������󼯺ϣ������������顣obj2[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj2[1]=װ��·�߽ڵ�ֵ���󡣡�
   *
   * �ο��ĵ������ɱ���
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC308
   * ϵͳ�˳�����PHOS-CAPP-UI311������ҵ�����(PHOS-CAPP-BR206)��ʾһ��·�߱������(PHOS-CAPP-UI313)�����·�߱������(PHOS-CAPP-UI314)
   *  ������������
   */
  public Map getSecondLeveRouteListReport(TechnicsRouteListIfc listInfo) throws
      QMException;

  public Map getFirstLeveRouteListReport1(TechnicsRouteListIfc listInfo) throws
      QMException;

////////////////////////////���ɱ��������///////////////////////////////
  //����11.��������·�߹�����
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC311
  /**
   * @roseuid 40359624031D
   * @J2EE_METHOD  --  getAllRouteLists
   * ������еĹ���·�߱�����°汾�������A,B�棬����B������С�汾��
   * �ο��ļ���ɾ������·�߱�
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC303
   * @return ����·�߱�ֵ���󼯺ϡ�
   */
  public Collection getAllRouteLists() throws QMException;

  /**
   * @roseuid 4031A5A203A3
   * @J2EE_METHOD  --  getRouteListLinkParts
   * �����·�߱���ص��㲿����
   * @return Collection ����ֵ���󼯺ϡ�
   * �ο��ĵ�����������·�߹�����
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC311
   * 1. ִ������·�߱����������(PHOS-CAPP-UI302)ѡ��һ������·�߱�ִ�б༭·�߲�����
   * 2. ϵͳ��ʾ"��ʾ�㲿����"����(PHOS-CAPP-UI325)��
   */
  public Collection getRouteListLinkParts(TechnicsRouteListIfc routeListInfo) throws
      QMException;

  //����12.����ѡ���㲿��
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC312
  //�������������������ṩ��getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)

  //����13.��������·��
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC313
  //����14.���¹���·��
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC314

  /**
   * �ɻ����ʾPHOS-CAPP-UI320���������·�߼��ڵ���Ϣ��
   * @param routelistID String
   * @param partMasterID String
   * @return Object[] obj[0]= ����·��ֵ����obj[1] = HashMap,�μ�getRouteBranchs(String routeID)
   */
  public Object[] getRouteAndBrach(String routeID) throws QMException;

  /**
   * @roseuid 40399137004C
   * @J2EE_METHOD  --  getRouteBranchs
   * ���ݹ���·��ID��ù���·�߷�֦��ض���
   * @return HashMap key=����·�߷�ֵ֦����, value=·�߽ڵ����飬obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ����
   */
  public Map getRouteBranchs(String routeID) throws QMException;

  //�鿴����·��
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC316

  /**
   * ���ݹ���·�߻����صĽڵ㼰�������
   * @param routeID String
   * @return Object[] obj[0]=·�߽ڵ�ֵ���󼯺ϣ� obj[1]=·�߽ڵ����ֵ���󼯺ϡ�
   */
  public Object[] getRouteNodeAndNodeLink(String routeID) throws QMException;

  /**
   * @roseuid 4030A78002FA
   * @J2EE_METHOD  --  saveRoute
   * ���湤��·�ߡ�
   * �ο��ĵ���
   * 1����������·��
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC313
   * ϵͳ������½��Ĺ���·�ߣ�������ˢ��Ϊ�鿴·�߽���(PHOS-CAPP-UI317)������������
   * 2�� ���¹���·��
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC314
   * �ڴ�����·�ߺ󣬵�����RoutePartLinkʱ��Ӧ��ListRoutePartLink�б�����Ӧ��·���Ƿ�ʹ��״̬���������ݵ�һ���ԡ�
   */
  public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc linkInfo,
                                    TechnicsRouteIfc routeInfo) throws
      QMException;

  //��þ���·�߰汾�Ĺ�����
  public ListRoutePartLinkIfc getListRoutePartLink(String routeListID,
      String partMasterID, String partNumber) throws QMException;

  /**
   * �ͻ����ڽ��б���ʱ��Ӧ�����ж�ListRoutePartLinkIfc��getAlterStatus()����=0��ȫ�����ó��½�״̬��=1����������
   * @roseuid 4030A8350200
   * @J2EE_METHOD  --  saveRoute
   * @param routeRelaventObejts, key = bsoName, value = ��Ӧ��ֵ���󼯺ϡ�����·�߼��ϣ�����·��ֵ����·��ID�����ID.
   * ���湤��·�߱༭ͼ��
   * @return Object[]:�����һ��Ԫ��--����·��ֵ��������ڶ���Ԫ��--HashMap �ṹͬgetRouteBranchs(String routeID)��
   * �ο��ĵ���
   * 1�� ���¹���·��
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC314
   * 2����������·��
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC313
   * 3�� PHOS-CAPP-BR211 ·�ߴ��������
   * ˵��������·�ߴ��Ĺ���Ϊ·�ߵ�λ�ڵ㣬һ����λ������һ��·�ߴ��г��ֶ�Ρ�·�ߴ��а����ӹ���λ��װ�䵥λ������·�ߴ��Ĺ��ɱ���������й���
   * 1. װ�䵥λ��һ��·�ߴ���ֻ����һ������ֻ�������һ���ڵ㣻
   * 2. һ����λ�����һ��·�ߴ��г��ֶ�Σ�������ǲ�ͬ���͵Ľڵ�(�����װ��)�������������ڵ�λ�ó��֡�
   * ��     * ���һ��·�ߴ�������˶��װ��ڵ㣬����ʾ��Ӧ����Ϣ��
   * ��     * ���װ��ڵ㲻�����ڵ㣬����ʾ��Ӧ����Ϣ��
   * ��     * ���·�ߴ��д������ڵ�ͬ���ͽڵ㣬����ʾ��Ӧ����Ϣ��
   * ע�⣺����ع���
   */
  public void saveRoute(ListRoutePartLinkIfc listLinkInfo,
                        HashMap routeRelaventObejts) throws QMException;

  
  /**
   * ͨ��·�߱��Ż��·�߱�ֵ����
   * @param routeListNum ·�߱���
   * @return ·�߱�ֵ����
   * @author ������
   */
  public TechnicsRouteListInfo findRouteListByNum(String routeListNum);
  
  //����15.ɾ������·��
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC315

  /**
   * @roseuid 4030B1C90274
   * @J2EE_METHOD  --  deleteRoute
   * @param routeListPartLinkID ������ID.
   * @param routeID ·��ID.
   * ɾ������·�ߡ�
   * �ο��ĵ���ɾ������·��
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC315
   */
  public void deleteRoute(ListRoutePartLinkIfc linkInfo) throws QMException;

  /**
   * ��ɾ����ÿ�θ���֮ǰ����ɾ����
   * @param routeID String
   * @throws QMException
   */
  public void deleteBranchRelavent(String routeID) throws QMException;

  /**
   * ��ֻ���½ڵ�λ��ʱ�����ô˷�����������·�ߵĸ��¡�
   * @param coll Collection
   * @throws QMException
   */
  public void saveOnlyNodes(Collection coll) throws QMException;

////////////·�߼���ڵ�Ĵ��������¡�ɾ��������////////////////

  //21.����Ʒ�ṹ����·�߱���
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC321
  /**
   * @param partMasterID
   * @param departmentID �û����ڵ�λ��codeID. ����һ��·�ߣ�����null��
   * @roseuid 4035D79C00B0
   * @J2EE_METHOD  --  getPartLevelRoutes
   * �������ID�͹���·�߼����ö�Ӧ�Ĺ���·�ߡ�
   * @return ���顣obj[0]= TechnicsRouteListIfc, obj[1],obj[2]��getRouteAndBrach(routeID), obj[3]=ListRoutePartLinkIfc��
   */
  public Object[] getProductStructRoutes(String partMasterID, String level_zh) throws
      QMException;

  //22. �鿴�㲿���Ĺ���·��
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC322

  /**
   *
   * @param partMasterID
   * @param departmentID �û����ڵ�λ��codeID. ����һ��·�ߣ�����null��
   * @roseuid 4035D79C00B0
   * @J2EE_METHOD  --  getPartLevelRoutes
   * �������ID�͹���·�߼����ö�Ӧ�Ĺ���·�ߡ�
   * @return Collection ���鼯�ϡ�obj[0]= TechnicsRouteListIfc, obj[1],obj[2]��getRouteAndBrach(routeID), obj[3]=linkInfo��
   * �ο��ĵ���
   * �鿴�㲿���Ĺ���·��
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC322
   * 1. ϵͳ��ʾ�鿴һ��·�߽���(PHOS-CAPP-UI332)��(S1)������������
   * 2. ϵͳ��ʾ�鿴����·�߽���(PHOS-CAPP-UI333)��(��ע1)(S1)������������
   */
  public Collection getPartLevelRoutes(String partMasterID, String level_zh) throws
      QMException;

  public Collection getPartRoutes(String partMasterID) throws QMException;

//////17.���ƹ���·��
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC317

  /**
   * �����ԡ���������·�߱��е�·�߽��и��ơ����"����"·�߱��������partMasterID���õ�·�ߡ�
   * ע�⣺�������ֻ����һ������ڲ�ͬ·�߱���·�ߵĸ��ơ��������������ĸ���ճ����
   * @param partMasterID
   * @param departmentID �û����ڵ�λ��codeID. ����һ��·�ߣ�����null��
   * @roseuid 4035D79C00B0
   * @J2EE_METHOD  --  getPartLevelRoutes
   * �������ID�͹���·�߼����ö�Ӧ�Ĺ���·�ߡ�
   * ִ���߸���һ������·�ߣ�ճ����û�й���·�ߵ��㲿���С�
   * ���ƹ���·��ʱ�����Դӵ�ǰ·�߱��е�һ���㲿���Ĺ���·�߸��ƣ�Ҳ���Դ�һ���㲿��������·�߱���ƵĹ���·�߸��ƣ�
   * ճ��ʱ������ճ������ǰѡ�е��㲿����Ҳ����ճ������·�߱���������·�ߵ��㲿��������㲿������·�ߣ�ʹ��"ճ����"����·��ʱ�����ܸ��Ƶ���Щ�㲿����
   * @return Collecion���鼯�ϡ�obj[0]= TechnicsRouteListIfc, obj[1],obj[2]��getRouteAndBrach(routeID)��
   */
  public Collection getAdoptRoutes(ListRoutePartLinkIfc linkInfo1) throws
      QMException;

  /**
   * @param routeID ����·��ID
   * @param linkInfo
   * @roseuid 4030BD76011D
   * @J2EE_METHOD  --  copyRoute
   * ��������·�߼�������ڵ㡣
   * @return ����·��ID.
   */
  public ListRoutePartLinkIfc copyRoute(String routeID,
                                        ListRoutePartLinkIfc linkInfo) throws
      QMException;

/////////////////////���ƽ�����////////////////////////////

/////////////////////����Ŀ������ʼ////////////////////////////
  /**
   * key=��λֵ����CodingIfc��CodingClassificationIfc, value=��λ��Ӧ�����master���ϡ�
   * @param routeListID String
   * @throws QMException
   * @return HashMap
   */
  public HashMap getDepartmentAndPartByList(String routeListID) throws
      QMException;

  /**
   * @roseuid 403972CA007E
   * @J2EE_METHOD  --  getParts
   * ͨ����λ����ID��ö�Ӧ��Ҫ�๤�յ������ͨ������ɻ�������Ϣ����������·�ߵ���Ϣ���˷������ɹ��ղ��ֵ��á�
   * @return ���masterInfo���ϡ�
   * ע�⣺��λ�нṹ������ʱ���ӽڵ�ҲҪ�����������ݲ�����
   */
  public Collection getParts(String departmentID) throws QMException;

  /**
   * ���ݹ���·�߻��������ĵ�λ����������ķַ���
   * @param routeListID String
   * @return Collection CodingIfc��CodingClassificationIfc
   */
  public Collection getDepartments(String routeListID) throws QMException;

/////////////////////����Ŀ��������////////////////////////////
  /**
   * ����routeMaster������еĴ�汾��Ӧ������С�汾��
   * @param listMasterInfo TechnicsRouteListMasterIfc
   * @throws QMException
   * @return Collection ���еĴ�汾��Ӧ������С�汾����config���ˡ��汾�ṩ�ķ�����allVersionsOf(MasteredIfc mastered).
   */
  public Collection getAllVersionLists(TechnicsRouteListMasterIfc
                                       listMasterInfo) throws QMException;

  /**
   * �ṩ����ıȽϡ�
   * @param iteratedVec Collection ��ͬ�汾�Ĺ���·�߱�ֵ���󼯺ϡ�
   * @param isCommonSort �Ƿ���������ıȽϷ�ʽ��
   * @throws QMException
   * @return Map key = partMasterID, value = Collection:listRoutePartLinkIfc���ϡ�����˳�򣬰汾���������С�
   */
  public Map compareIterate(Collection iteratedVec) throws QMException;

  //routeListInfoΪ�µĴ�汾����ʱҪ���ƹ�������Ҫ����initialUsedΪ�µĴ�汾��
  public TechnicsRouteListIfc newVersion(TechnicsRouteListIfc routeListInfo) throws
      QMException;

  /**
   * �������masterID��ù���·�߱�ֵ���󼯺ϡ�
   * @param partMasterID String
   * @throws QMException
   * @return Collection
   */
  public Collection getListsByPart(String partMasterID) throws QMException;

/////////////2004.4.27����ӵķ���/////////////
  /**
   * �жϸ��������masterID������·�߱����Ƿ���·�ߡ�
   * @param partMasteIDs Collection
   * @return Map, key=partMasterInfo, value = "������"���ޡ���
   */
  public Map getRouteByParts(Collection partMasteInfos, String level_zh) throws
      QMException;

  /**
   * @param �б仯�ķ�ֵ֧����alterStatus = 0;
   * @param routeMap Map
   */
  public void createRouteByBranch(ListRoutePartLinkIfc linkInfo,
                                  TechnicsRouteIfc route,
                                  Collection branchInfos) throws QMException;

  /**
   * ���·�֦��alterStatus = 1;
   * @param branchs Collection ��ֵ֧����
   */
  public void updateBranchInfos(Collection branchs) throws QMException;

  /**
   * @roseuid 4039965D0106
   * @J2EE_METHOD  --  getRouteNodes
   * ����·�����ͺͷ�֦ID��ù���·�߽ڵ㡣
   * @return Collection ����·�߽ڵ�ֵ����
   * ���ɿ����ڼ����ʼ�ڵ��Ƿ��������ӣ����У�����RouteListHandler�ĺ�����������������ʼ�ڵ㡣����ʵ�������ѡ�񡣣�
   * �ο���PHOS-CAPP-UI321
   *             �༭·��ͼ
   */
  public Collection getRouteNodes(String routeType, String routeBranchID);

  /**
   * @roseuid 4032F952020C
   * @J2EE_METHOD  --  getListPartRoute
   * �����㲿��ID�͹���·�߱�ID��ö�Ӧ�Ĺ���·�ߡ�
   * @return ����·��ֵ����
   *
   * �ο��ĵ���
   * ���� PHOS-CAPP-BR208 ��������·�߼��
   * ˵����һ���㲿����һ��·�߱���ֻ�ܴ���һ������·�߶���
   * ��     * ����㲿���Ѵ��ڹ���·�ߣ�����ʾ��Ӧ��Ϣ
   *
   * ������������·�߹�����
   * �汾 <v2.0>
   * �ĵ���ţ�PHOS-CAPP-UC311
   *
   * 4. ϵͳ�˳�����PHOS-CAPP-UI325����ִ����ѡ���·�߱��ִ����ѡ�е��㲿����ʾ��"����·�߹�����"����(PHOS-CAPP-UI316)�У�����������
         public TechnicsRouteIfc getListPartRoute(String partMasterID, String routeListID)
         {
        return null;
         }*/

  /**
   * @roseuid 40309C1300C3
   * @J2EE_METHOD  --  getRoutes
   * ���ݹ���·�߱�ID��ö�Ӧ�Ĺ���·�ߡ�
   * @return Collection ����·�߱��Ӧ�����й���·�ߣ�·�߷�֦��·�߽ڵ㡣
   public Collection getRoutes(String routeListID)
   {
           return null;
   }*/

  /**
   * @roseuid 40309EF101A5
   * @J2EE_METHOD  --  getRoute
   * ���ݲ�ƷID�����ID��ù���·��ֵ����
   * @return ����·��ֵ���󡣴˷�������ȶ��
   */
  public TechnicsRouteIfc getRoute(String productMasterID,
                                   String partMasterID);

  /**
   * @roseuid 4031A6A70254
   * @J2EE_METHOD  --  getRouteListContent
   * ���ݹ���·�߱�ID������й���·�߼���ڵ�ֵ����
   * @return ��ϣ��
   * key:bsoname;value=Collection  ������Ӧֵ���󼯺�
   */
  public java.util.HashMap getRouteListContent(String routeListID,
                                               String level_zh);

  /**
   * @roseuid 4031A9080245
   * @J2EE_METHOD  --  getListLevelRoutes
   * ���ݹ���·�߱�·�߼����ù���·�ߡ�
   * �������Ϊ�գ����ּ���
   * @return Collection ����·��ֵ����
   */
  public Collection getListLevelRoutes(String routeListID, String level_zh);

  /**
   * ����·����ض���ע�Ᵽ֤˳��
   * @param routeID String
   * @param �б仯�ķ�ֵ֧����
   * @return HashMap
   */
  public HashMap getRouteContainer(String routeID, Collection branchInfos) throws
      QMException;

  /**
   * ���ݹ���·�߻����صĽڵ�(��·�߷�֧����)���������
   * @param routeID
   * @return obj[0]=Map��key����ֵ֧����value���÷�֧�е�·�߽ڵ�ļ��ϣ� obj[1]=·�߽ڵ����ֵ���󼯺ϡ�
   * @throws QMException
   */
  public Object[] getBranchNodeAndNodeLink(String routeID) throws QMException;

  /**
   * �������ָ����Ʒ�����¹���·�߱�
   * @param productMasterID ��Ʒ
   * @param level_zh ·�߱���
   * @return ����·�߱�
   */
  public TechnicsRouteListInfo getRouteListByProduct(String productMasterID) throws
      QMException;

  public Object[] getMaterialBillRoutes(TechnicsRouteListInfo routeList,
                                        String partMasterID, String level_zh) throws
      QMException;

  /**
   * ���ָ���㲿��������Ϣ������һ���㲿��������Ϣ���ļ���
   * @param partMasterInfo ָ���㲿��������Ϣ��
   * @return ��һ���㲿��������Ϣ���ļ���
   * @throws QMException
   */
  public Collection getSubPartMasters(QMPartMasterIfc partMasterInfo) throws
      QMException;

  /**
   * �Ը������㲿�����м�飬�����Щ�㲿���Ƿ������ӵ�����·�ߵ��㲿����
   * �����ǰ�༭�Ĺ���·�߱��Ƕ�������·�߱���ϵͳӦ����·�߱�ĵ�λ���ԣ��г���Ʒ�ṹ������һ��·���ж�Ӧ��λ���㲿������Ϊ��ѡ�㲿����
   * ������Դ��3.4 PHOS-CAPP-BR204 �Ӳ�Ʒ�ṹ������㲿����
   * @param codeID ����·�߱�ĵ�λ
   * @param productMasterID ����·�߱����ڵĲ�Ʒ
   * @param subPartMasters ����м����㲿�����ϣ�Ҫ��Ԫ��Ϊ�㲿������Ϣֵ����
   * @return �����а�������Ԫ�أ���һ��Ԫ���Ƿ����������㲿������Ϣ���ϣ��ڶ���Ԫ���ǲ������������㲿������Ϣ����
   * @throws QMException
   */
  public Object[] checkSubParts(String codeID, String productMasterID,
                                Collection subPartMasters) throws QMException;

  /**
   *
   * @param depart String
   * @param partInfo QMPartInfo
   * @return Collection
   */
  public boolean getOptionPart(String depart, QMPartMasterIfc master) throws
      QMException;

  /**
   *  ͨ��part�ҳ����з������ƹ淶����part������getOptionPart�������˽����
   * @param partMaster QMPartMasterIfc
   * @param configSpecIfc PartConfigSpecIfc
   * @param depart String
   * @throws QMException
   * @return Vector
   */
  public Vector getAllSubPart(QMPartMasterIfc partMaster,
                              PartConfigSpecIfc configSpecIfc,
                              String depart) throws QMException;

  /**
   * ���ݸ��������쵥λ��ѯ��ص��㲿���͹���·��id
   * added by skybird 2005.3.9
   * @param makeDepartment ���쵥λ
   * @return Vector ���Ԫ��{partMasterID,routebsoID}
   * @throws QMException
   */
  public Vector getAllPartsRoutesM(String makeDepartment) throws QMException;

  /**
   * ���ݸ�����װ�䵥λ��ѯ��ص��㲿���͹���·��id
   * added by skybird 2005.3.9
   * @param constructDepartment װ�䵥λ
   * @return Vector ���Ԫ��{partMasterID,routebsoID}
   * @throws QMException
   */
  public Vector getAllPartsRoutesC(String constructDepartment) throws
      QMException;

  /**
   * ���ݸ��������쵥λ��װ�䵥λ��ѯ��ص��㲿���͹���·��id
   * added by skybird 2005.3.9
   * @param makeDepartment ���쵥λ
   * @param constructDepartment װ�䵥λ
   * @return Vector ���Ԫ��{partMasterID,routebsoID}
   * @throws QMException
   */
  public Vector getAllPartsRoutesA(String makeDepartment,
                                   String constructDepartment) throws
      QMException;

  public Collection getColligationRoutes(String mDepartment, String cDepartment,
                                         Vector parts, Vector products) throws
      QMException;

  /**
   * ���ݸ��������쵥λ��ѯ��ص��㲿���͹���·��id
   * added by gcy 2005.5.09
   * @param mDepartment String ���쵥λ��id
   * @param cDepartment String  װ�䵥λ��id
   * @param productID String  ��Ʒ��id
   * @param source String  �㲿������Դ
   * @param type String �㲿��������
   * @throws QMException
   * @return Collection  ������ϡ�ÿ����������һ��Vector.���η��㲿���������ڣ��㲿��id���㲿����ţ��㲿�����ƣ�
   * �汾�������ţ��㲿���ڲ�Ʒ���������ϣ�·��������·�߼��ϣ�����·�߼���ÿ����Ŀ��������װ��2��·�ߣ�
   */
  public Collection getAllPartsRoutes(String mDepartment, String cDepartment,
                                      String productID, String source,
                                      String type) throws QMException;

  /**
   * �����㲿�������Ƿ���·��
   * @param partMasterID �㲿��bsoid
   * @return
   * @throws QMException
   */
  public boolean isHasRoute(String partMasterID) throws
      QMException;

  /**
   * �����㲿�������Ƿ���·��
   * @param partMasterID �㲿��bsoid
   * @return
   * @throws QMException
   */
  public Vector[] isHasRoute(Vector vec) throws
      QMException;

  public HashMap calCountInProduct(Vector parts, QMPartIfc parent,
                               QMPartIfc product) throws QMException;

  /**
  * �����Ӽ��ڲ�Ʒ��ʹ�õ�������ֻ�����丸���ڲ�Ʒ�е�������
  * @param part QMPartIfc �Ӽ�
  * @param parent QMPartIfc ����
  * @param product QMPartIfc ��Ʒ
  * @throws QMException
  * @return int ����
  */
 public int calCountInProduct(QMPartIfc part, QMPartIfc parent,
                              QMPartIfc product) throws QMException ;


  /**
   * �õ�ǰ�û������ù淶�����㲿��
   * @param master QMPartMasterIfc
   * @throws QMException
   * @return QMPartIfc
   */
  public QMPartIfc filteredIterationsOfByDefault(QMPartMasterIfc master) throws
      QMException;

  /**
   * @roseuid 4039932300E0
   * @J2EE_METHOD  --  getBranchRouteNodes
   * ���ݹ���·�߷�֦ID��ù���·�߽ڵ㡣
   * @return Collection ����·�߽ڵ�ֵ����
   */
  public Collection getBranchRouteNodes(TechnicsRouteBranchIfc
                                        routeBranchInfo) throws
      QMException;

//��·�߽ڵ����ͷ��ࡣ--װ��·�ߡ�����·�ߡ�
  public Object[] getNodeType(Collection branchNodes) throws QMException;
  // zz��� �ж��Ƿ���һ���������Ӽ�
public Collection isParentPart (Vector maybeChild, QMPartIfc maybeParent) throws QMException;
  //zz���
  public Collection filteredIterationsOfByDefault(Collection masterCol) throws
      QMException ;
  //zz ���
  public Collection getRouteListLinkPartsforVersionCompare(TechnicsRouteListIfc routeListInfo) throws
       QMException ;
// zz 20061110 Ϊ����·������㲿����Ҫ����
   public Collection getOptionPart(String depart, Vector masters) throws
      QMException ;
  //  //zz ���
   public TechnicsRouteListMasterIfc rename(TechnicsRouteListIfc routelist)throws QMException;
     //zz ���
     public String getMaterialRoute(QMPartIfc part) throws QMException;
     public Vector setBOMList(QMPartIfc partIfc, String[] attrNames,
                                  String[] affixAttrNames, String source, String type,
                                  PartConfigSpecIfc configSpecIfc)
             throws QMException;
         public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames,
                                      String[] affixAttrNames,
                                      PartConfigSpecIfc configSpecIfc)
            throws QMException;

  public String getStatus2(String partMasterID, String level_zh,            //gg
              String depart) throws
QMException ;
  public String getStatus(String partMasterID, String level_zh) throws
  QMException ;//gg
  
//CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����"����֪ͨ�����"
  /**
   * ���ϵͳ���������ݱ�����ĵ��ż�����֪ͨ����������
   * @param noticeValue String
   * @return ArrayList
   * @throws QMException
   */
  
  //CCBegin by liunan 2012-05-22 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
  //public ArrayList getNoticeOrChangeRelatedParts(String noticeValue) throws QMException ;
  public ArrayList getNoticeOrChangeRelatedParts(String noticeValue, boolean isManufacturing) throws QMException;
  //CCEnd by liunan 2012-05-22
      

  public HashMap getCounts(String productMasterID, HashMap partMap) throws
  QMException,java.sql.SQLException;
  
  public Collection getSubPartsNoRoute(String productNumber) throws
  QMException;
  /**
   * ���ָ��������ض����ù淶������С�汾 20061209 liuming add
   * @param partMasterBsoID
   * @param configSpecIfc ���configSpecIfc=null�����õ�ǰ�û������ù淶
   * @return QMPartIfc �����ǰ�û������ù淶��û�в鿴�����Ȩ�޻�û�а汾���򷵻�null
   * @throws QMException
   */
  public QMPartIfc getLastedPartByConfig(QMPartMasterIfc master) throws
  QMException;
  public QMPartIfc getLastedPartByConfig(String partMasterBsoID,PartConfigSpecIfc configSpecIfc) throws
      QMException;
//CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·��,����׼״̬������׼
  public QMPartIfc getLastedPartofsz(QMPartMasterIfc master,PartConfigSpecIfc configSpecIfc) throws
  QMException;
//CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·��,����׼״̬������׼     
  public QMPartIfc getLastedPartByConfig(QMPartMasterIfc master,PartConfigSpecIfc configSpecIfc) throws
  QMException;
  
  public Vector getListAndBrances(QMPartIfc product, QMPartIfc part,
          String[] atts) throws QMException ;
  public Vector getListAndBrances(QMPartIfc product, QMPartIfc part,
          String[] atts, String parentFirstMakeRoute) throws QMException ;
  public Collection getRouteInfomationByPartmaster(String partMasterID) throws
  QMException ;
  /**
   * ����׼֪ͨ����׼��ϵͳ�Զ���·���а������ӹ�˾��Ƶ��㲿���Է��ӹ�˾������Ȩ��
   * ֻ���㲿����������ĵ���ͼ��������Ȩ��
   * 20071010 add by liuming  for jiefang
   * @param routeListID String
   * @throws QMException
   */
  public void setReadAclForSub(String routeListID) throws QMException;
  
  public String[] getRouteString(QMPartIfc part) throws QMException;
  
  public void saveListRoutePartLink(HashMap saveCollection,
          ArrayList updateLinksList,
          HashMap deleteCollection,
          //CCBegin SS1
          //TechnicsRouteListIfc routeListInfo1) throws QMException;
          TechnicsRouteListIfc routeListInfo1,
          //CCBegin SS5
//          boolean addRouteFlag) throws QMException;   
  //CCBegin SS7
  //boolean addRouteFlag,boolean saveAs) throws QMException; 
  boolean addRouteFlag,boolean saveAs, ArrayList arrayList, Vector vec) throws QMException; 
  //CCEnd SS7
  //CCEnd SS5
          //CCEnd SS1
  
  public String getRouteCodeDesc(String routeID) throws QMException;
//CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
//CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·�ߣ�����·��
  /**
   * �ռ��������� 20061209 liuming add
   * @param routeListID String
   * @param IsExpandByProduct �Ƿ񰴳�չ������ʱĬ����Ϊ�棩
   * @throws QMException
   */
  public ArrayList gatherExportData(String routeListID, String IsExpandByProduct)
      throws
      QMException;

//CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·�� ������·�� 
//CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·��
  public Vector findParentsAndRoutes(QMPartMasterIfc partmaster, String listID) throws
  QMException;
//CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·��
//CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·��
  public Vector getAllDeps() throws
  QMException;
//CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·��

//CCBegin by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձϷ���׼��޸�����
  public void setRouteCompletePartsState(String routeListID)throws QMException;
//CCEnd by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձϷ���׼��޸�����         

//CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
public void setRouteDisaffirmPartsState(String routeListID)throws QMException;
//CCEnd by liunan 2011-09-21
  
  public Collection getNeededCollForReport(Collection partMasterIDColl);
     public Collection getNeededCollForReport(Collection partMasterIDColl,
       TechnicsRouteListInfo routeList) ;
//CCEnd by leixiao 2008-9-11 ԭ�򣺽������
//CCBegin by leixiao 2008-10-07 ԭ�򣺽������,��׼֪ͨ������   	     
  /**
   * Ϊָ����׼�����в���״̬��������IBA����ֵ����׼֪ͨ��ţ�
   * @param routeListBsoID ��׼֪ͨ��BsoID
   * 20070115 liuming add
   */
    public void addIBAvalueForPart(String routeListBsoID) throws QMException ;
//CCEnd by leixiao 2008-10-07 ԭ�򣺽������,��׼֪ͨ��
//CCBegin by leixiao 2008-10-07 ԭ�򣺽������
/**
* ��ȡ��ǰ�û������ù淶������û����״ε�½ϵͳ������Ĭ�ϵġ�������ͼ��׼�����ù淶��
* @throws QMException ʹ��ExtendedPartServiceʱ���׳���
* @return PartConfigSpecIfc ��׼���ù淶��
*/
public  PartConfigSpecIfc getCurrentUserConfigSpec() throws
QMException;

 /**
* ������
* @param masterInfo TechnicsRouteListMasterIfc
* @return TechnicsRouteListMasterIfc
* @throws QMException
*/
public TechnicsRouteListMasterIfc renameRouteListMaster(TechnicsRouteListMasterIfc
   masterInfo)
   throws QMException;
   


public void importSaveRoute(ListRoutePartLinkIfc listLinkInfo,
                       HashMap routeRelaventObejts) throws QMException;
                       
public TechnicsRouteListInfo getRouteListbyRouteListNum(String routeListNum) throws
QMException;


public void setRouteListPartsState(String routeListID);

public QMPartIfc filteredIterationsOfByDefaultConfig(QMPartMasterIfc master) throws
QMException;

public String getRouteText(QMPartInfo part) throws QMException ;
//CCBegin by leixiao 2009-1-8 ԭ�򣺽����������·��,�汾ȡ���з����汾 
public String  getibaPartVersion(QMPartMasterIfc master) throws
QMException;
public String  getibaPartVersion(QMPartIfc part) throws
QMException;
//CCEnd by leixiao 2009-1-8 ԭ�򣺽����������·��,�汾ȡ���з����汾
//CCEnd by leixiao 2008-10-07 ԭ�򣺽������ 
public void handleversion()throws QMException;
public void handleversion1()throws QMException;


//CCBegin by liujiakun ���ն���
/**
* @param param ��ά���飬5��Ԫ�ء�����obj[0]=��ţ�obj[1]=true. ����˳�򣺱�š����ơ����𣨺��֣������ڲ�Ʒ���汾�š�
* @roseuid 402CBAF700CA
* @J2EE_METHOD  --  findRouteLists
* ��ù���·�߱�������Χ����š����ơ��������ڲ�Ʒ���汾�š�
* @return collection ����·�߱�ֵ�������°汾��
* ��ʱҪ��ConfigService���й��ˡ�
*/
public Collection findRouteListsnew(Object[][] param) throws QMException;
//CCEnd by liujiakun ���ն���    
//CCBegin by liujiakun ���ն���

//tang 20070521
public ListRoutePartLinkInfo getCurrentRouteLinkByPartID(String
   partMasterBsoID) throws QMException;

/**
* tangshutao add for qingqi 2007.09.17
* ����·�߱����㲿������ֵ���󼯺ϻ��ÿ���㲿����·�ߴ�
* @param coll Collection ·�߱����㲿������ֵ���󼯺�
* @throws QMException
* @return Vector  ������飬ÿ����������Ԫ�أ�{�㲿����ţ��㲿�����ƣ�·�ߴ�}
*/
public Vector getRouteTextByLinkCollection(Vector coll) throws
   QMException;

/**
* 2007.11.21 ���ڲ��϶���Ĺ���
* @param coll Vector
* @throws QMException
* @return Vector
*/
public Vector getRationRouteTextByLinkCol(Vector coll, String department) throws
   QMException;

/**
* tangshutao add for qingqi 2007.09.17
* ����·��ID���·�ߴ�
* @param routeid String
* @throws QMException
* @return String
*/
public String getRouteTextByRouteID(String routeid) throws
   QMException;

/**
* 2007.12.1 tangshutao ���ϸ������϶�����Ƶ��������
* @param coll Vector
* @throws QMException
* @return Vector
*/
public Vector getAssisRationRouteTextByLinkCol(Vector coll, String department) throws
   QMException;

/**
* tangshutao add for qingqi 2007.12.6
* @param info String
* @throws QMException
* @return String[] 1,·��״̬��2����·�ߡ�3����·��
*/
public String[] getRouteTextByMaster(String info) throws
   QMException;

//CCEnd by liujiakun ���ն���
/**
* 2008.01.15 ������׼��ù��������
* @param routeListInfo TechnicsRouteListIfc
* @throws QMException
* @return BaseValueIfc[]
*/

public BaseValueIfc[] getRouteListParts(TechnicsRouteListIfc routeListInfo) throws
   QMException;
//CCBegin by leixiao 2009-4-14 ԭ�򣺽������
public ArrayList getcompleteroutepart(String routeid)throws QMException;

//CCEnd by leixiao 2009-4-14 ԭ�򣺽������
//CCBegin by leixiao 2010-11-30 ԭ������"������������" 
//CCBegin by liunan 2012-05-22 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
//public ArrayList getPublishRelatedParts(String noticeValue) throws QMException;
public ArrayList getPublishRelatedParts(String noticeValue, boolean isManufacturing) throws QMException;
//CCEnd by liunan 2012-05-22
//CCEnd by leixiao 2010-11-30 ԭ������"������������" 
//CCBegin by liunan 2011-09-20 ���������㲿�������·�ߵ���׼��Ϣ��
public String getPartRoutesNew(String partMasterID) throws QMException;
//CCEnd by liunan 2011-09-20

//CCBegin SS2
public Collection findMultPartsByNumbers(Object[] param) throws QMException;
//CCEnd SS2
//CCBegin SS3
public ArrayList getAdoptNoticeRelateParts(String adoptNotice,String source,boolean flag)throws QMException;
//CCEnd SS3
//CCBegin SS4
public ArrayList getChangeRelateParts(String change,String source,boolean flag)throws QMException;
//CCEnd SS4

//CCBegin SS6
public boolean getRouteStr(TechnicsRouteListIfc routeList);
//CCEnd SS6

  //CCBegin SS8
  public String getPartRouteState(String partMasterID);
  //CCEnd SS8
  
  //CCBegin SS9
  public Vector getPartRouteStrs(String partMasterID);
  //CCEnd SS9
  
  //CCBegin SS10
  /** 
   * ��ȡ��Ŀ���ݲ�����
   * @param self
   * @param primaryBusinessObject
   * @throws QMException
   */
  public void getWFInfoAndSetProducePreparative(Object self,
			Object primaryBusinessObject) throws QMException;
  //CCEnd SS10
  //CCBegin SS11
  public void publishDataToEOL(BaseValueIfc primaryBusinessObject) throws QMException;
  //CCEnd SS11
  
	//CCBegin SS12
	public HashMap getSubPartRoute(QMPartIfc productInfo) throws QMException;
	//CCEnd SS12
}
