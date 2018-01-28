/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/15 �촺Ӣ    ԭ��ͳһ���������ͽ���    �μ���Wipר�����˵��1_��ǿ.doc
 * CR2 2011/12/15 �촺Ӣ   ԭ���޸ļ��롢���������������޶��ķ�ʽ
 * CR3 2011/12/20 ����  ԭ��ͳһ������������ͽ���
 * CR4 2011/12/21 ����   ԭ������������ӷ���
 * CR5 2011/12/23 ����   ԭ�����Ӳ��ñ����ӷ���
 * CR6 2011/12/26 ���� ԭ�������������¹���
 * CR7 2011/12/30 �촺Ӣ  ԭ�����ӱ�����׼�����������·����Ϣ�ķ���
 * CR8 2012/01/06 �촺Ӣ  ԭ�����ӷ���·�߹���
 * CR9 2012/01/11 ���� ԭ�����Ӽ���·�߱�ʱ������Ƿ���·�ߵ��ж�
 * CR10 2012/01/11 ���� ԭ�����Ӹ����Բ�ѯ����
 * CR11 2012/01/18 xucy ԭ�����ӿ��·�߹���
 * CR12 2012/03/29 ����ԭ��μ�TD5975
 * CR13 2012/04/06 ���� ԭ��μ�TD5938
 * SS1 ����ݿͻ�����ı����� liujiakun 2013-03-01
 * SS2 �����乤��·���������ʱ��ʾ���������Ϣ pante 2013-11-02
 * SS3 �༭����·�߽����㲿��������ʾ�İ汾ֵ�޸�Ϊ����Դ�汾  ����ͮ 2013-12-16
 * SS4 ���������׼ liunan 2013-12-23
 * SS5 ���ݽ����׼��·�������㲿�� liunan 2013-12-23
 * SS6 �޸ı��������·�����������ʾ�����������⣬��Ϊconslistpartlink����rightbsoid�ﲻһ������qmpartmasterid�ˣ��µ���qmpartid�ˡ� liunan 2014-1-23
 * SS7 �������鿴����·���п������ձ�·�� pante 2014-10-09
 * SS8 ������������һ��·�� liuyang 2014-8-25
 * SS9 ���ض���·�߱��� liuyang 2014-9-3
 * SS10 �����������·���޸ĳ���׼״̬ ���� 2014-11-17
 * SS11 �����䴦�����·����ʷ���ݣ�����public������ liunan 2015-3-12
 * SS12 A005-2014-2957 ����������������㲿�����������°�·�ߣ���ȥ����·�ߣ�û�еĻ�ȡ���һ��·�ߡ� liunan 2015-7-16
 * SS13 �ɶ�����·�����ϣ�����·�߱��� liunan 2016-8-15
 * SS14 �ɶ����ͨ����׼������ guoxiaoliang 2016-07-18
 * SS15 �ɶ�������ӻ���Ӽ� guoxiaoliang 2016-7-28
 * SS16 �޸ĳɶ����������㲿��Ϊ��ȷ���� liuyuzhu 2016-11-10
 * SS17 ������ͬ����·�߱� liuyuzhu 2016-10-12
 */

package com.faw_qm.technics.consroute.ejb.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.codemanage.util.CodeManageTable;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.ModelRouteInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkingPair;

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
 * �ο��ĵ��� Phos-REQ-CAPP-BR02(����·��ҵ�����)V2.0.doc PHOS-REQ-CAPP-SRS-002(����ҵ��ҵ�������ݹ���������˵��-����·��) V2.0.doc "Phos-CAPP-UC301--Phos-CAPP-UC322"��19��������
 */
public interface TechnicsRouteService extends BaseService
{
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
     * @J2EE_METHOD -- storeRouteList �������¹���·���б��ǿռ����ֵ������������Ψһ�Լ�������ݿ������á� �ο��ĵ�����������·�߱�PHOS-CAPP-UC301�����¹���·�߱�PHOS-CAPP-UC302
     * ϵͳ����ҵ�����PHOS-CAPP-BR201���Ҫ��ǿյ������Ƿ�Ϊ��(E1)������ҵ�����PHOS-CAPP-BR202��鹤��·�߱����Ƿ�Ψһ(E2)�� ���� E1�� ϵͳ��ʾ��Ϣ����������+(CP00001) E2�� ϵͳ��ʾ��Ϣ����������+(CW00001)
     * @return ����·�߱�ֵ����
     */
    public TechnicsRouteListIfc storeRouteList(TechnicsRouteListIfc routeListInfo) throws QMException;

    //����3.ɾ������·�߱�
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC303

    /**
     * @roseuid 40305EC40043
     * @J2EE_METHOD -- deleteRouteList ɾ������·�߱�������汾�ڵ�С�汾ȫ��ɾ����
     */
    public void deleteRouteList(TechnicsRouteListIfc routeListInfo) throws QMException;

    //����4.�༭�㲿����
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC304

    /**
     * @roseuid 403449360397
     * @J2EE_METHOD -- getOptionalParts
     * @param level ·�߼�����ʾ��������һ��·�ߡ� ���ݵ�λ���ƵĴ���ID��·�߼����ÿ�ѡ����㲿����
     * @return Collection �㲿��masterֵ���� ��Ӧ���� �����ǰ�༭�Ĺ���·�߱���һ������·�߱���ϵͳӦ�г���Ʒ�ṹ�е������㲿����Part: getAllSubParts(QMPartIfc). �����ǰ�༭�Ĺ���·�߱��Ƕ�������·�߱���ϵͳӦ����·�߱�ĵ�λ���ԣ��г���Ʒ�ṹ������һ��·���ж�Ӧ��λ���㲿������Ϊ��ѡ�㲿���� ������Դ��3.4
     * PHOS-CAPP-BR204 �Ӳ�Ʒ�ṹ������㲿���� �ο��ĵ��� �༭�㲿���� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC304
     * ִ�����ڸ��¹���·�߱�ʱ���༭Ҫ���ƹ���·�ߵ��㲿������������㲿����ɾ���㲿��������㲿��ʱ�����Ը���·�߱��е�"���ڲ�Ʒ"���㲿�����������½ṹ���ṩ��ѡ�㲿�����û����Դ���ѡ��Ҫ��ӵ�����·�߱��е��㲿��������û����ڱ༭���Ƕ���·�߱���ѡ�㲿������Ӧ�����ڲ�Ʒ�Ľṹ����Ӧ���˲�Ʒ�����㲿���а�������λ·�ߵ�һ��·�߽��н�һ��ɸѡ���Ӷ����һ�ݱ�ѡ�㲿����
     */
    public Collection getOptionalParts(String codeID, String level, String productMasterID) throws QMException;

    //������°汾��ֵ����
    public BaseValueIfc getLatestVesion(MasterIfc masterInfo) throws QMException;

    /**
     * ������°汾ֵ����ļ���
     * @param c master���󼯺� @ (������)20060629 �����޸�
     */
    public Collection getLatestsVersion(Collection c) throws QMException;

    /**
     * add by guoxl on 20080307 �鿴һ��·�߱���ʱ������������ ������ӷ���getLatestsVersion1
     */
    public HashMap getLatestsVersion1(Collection c) throws QMException;


    /**
     * ���¸������ added by skybird 2005.3.4
     * @param PartsToChange
     * @param routeListInfo1 @
     */
    public void updateListRoutePartLink(Collection PartsToChange, TechnicsRouteListIfc routeListInfo1) throws QMException;

    //����5.����·�߱�(������)
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC305
    //����6.��������·�߱�
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC306

    /**
     * @param param ��ά���飬5��Ԫ�ء�����obj[0]=��ţ�obj[1]=true. ����˳�򣺱�š����ơ����𣨺��֣������ڲ�Ʒ���汾�š�
     * @roseuid 402CBAF700CA
     * @J2EE_METHOD -- findRouteLists ��ù���·�߱�������Χ����š����ơ��������ڲ�Ʒ���汾�š�
     * @return collection ����·�߱�ֵ�������°汾�� ��ʱҪ��ConfigService���й��ˡ�
     */
    public Collection findRouteLists(Object[][] param) throws QMException;

    /**
     * @roseuid 402CBAF700CA
     * @J2EE_METHOD -- findRouteLists ��ù���·�߱�������Χ����š����ơ�״̬�����𣨺��֣������ڲ�Ʒ��˵�������λ�á��������ڡ������ߡ������¡��汾�š�
     * @return collection ����·�߱�ֵ�������°汾�� ��ʱҪ��ConfigService���й��ˡ�
     */
    public Collection findRouteLists(TechnicsRouteListIfc routelistInfo, String productNumber, String createTime, String modifyTime) throws QMException;

    //����7.�鿴����·�߱�
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC307
    //getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)
    ////////////////////////////////////////////////////////////////////////////
    //����8.���ɱ���
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC308

    /**
     * ���ɱ����������Ʊ��������ʽΪ�����ļ���
     * @param routeListID String ·�߱��ID
     * @param exportFormat String �����ʽ @
     * @return String ·�߱������+���+�Ĺ���·�߱��� 20120214 xucy modify
     */
    public String exportRouteList(String routeListID, String exportFormat) throws QMException;

    /**
     * @roseuid 40305F8F0257
     * @J2EE_METHOD -- getFirstLeveRouteListReport ���ݹ���·�߱�ID���һ������·�߱������ݹ���·�߱�ID���������乤��·��ֵ���� key:���ֵ����;value=����·�߽ڵ������󼯺ϣ�obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ���� �ο��ĵ������ɱ��� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC308
     * ϵͳ�˳�����PHOS-CAPP-UI311������ҵ�����(PHOS-CAPP-BR206)��ʾһ��·�߱������(PHOS-CAPP-UI313)�����·�߱������(PHOS-CAPP-UI314) ������������
     */
    public CodeManageTable getFirstLeveRouteListReport(TechnicsRouteListIfc listInfo);

    /**
     * @roseuid 4031B65C0364
     * @J2EE_METHOD -- getSecondLeveRouteListReport ���ݹ���·�߱�ID��ö�������·�߱������ݹ���·�߱�ID���������乤��·��ֵ����
     * @return Map key:���ֵ����;value=����·��ֵ�������� �����а����������ϣ�obj[0]=һ������·�߽ڵ������󼯺ϣ������������顣 obj1[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj1[1]=װ��·�߽ڵ�ֵ���� obj[1] = ��������·�߽ڵ������󼯺ϣ������������顣obj2[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj2[1]=װ��·�߽ڵ�ֵ���󡣡�
     * �ο��ĵ������ɱ��� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC308 ϵͳ�˳�����PHOS-CAPP-UI311������ҵ�����(PHOS-CAPP-BR206)��ʾһ��·�߱������(PHOS-CAPP-UI313)�����·�߱������(PHOS-CAPP-UI314) ������������
     */
    public Map getSecondLeveRouteListReport(TechnicsRouteListIfc listInfo) throws QMException;

    public Map getFirstLeveRouteListReport1(TechnicsRouteListIfc listInfo) throws QMException;

    ////////////////////////////���ɱ��������///////////////////////////////
    //����11.��������·�߹�����
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC311
    /**
     * @roseuid 40359624031D
     * @J2EE_METHOD -- getAllRouteLists ������еĹ���·�߱�����°汾�������A,B�棬����B������С�汾�� �ο��ļ���ɾ������·�߱� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC303
     * @return ����·�߱�ֵ���󼯺ϡ�
     */
    public Collection getAllRouteLists() throws QMException;

    /**
     * @roseuid 4031A5A203A3
     * @J2EE_METHOD -- getRouteListLinkParts �����·�߱���ص��㲿����
     * @return Collection ����ֵ���󼯺ϡ� �ο��ĵ�����������·�߹����� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC311 1. ִ������·�߱����������(PHOS-CAPP-UI302)ѡ��һ������·�߱�ִ�б༭·�߲����� 2. ϵͳ��ʾ"��ʾ�㲿����"����(PHOS-CAPP-UI325)��
     */
    public Collection getRouteListLinkParts(TechnicsRouteListIfc routeListInfo) throws QMException;

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
     * @J2EE_METHOD -- getRouteBranchs ���ݹ���·��ID��ù���·�߷�֦��ض���
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
     * @J2EE_METHOD -- saveRoute ���湤��·�ߡ� �ο��ĵ��� 1����������·�� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC313 ϵͳ������½��Ĺ���·�ߣ�������ˢ��Ϊ�鿴·�߽���(PHOS-CAPP-UI317)������������ 2�� ���¹���·�� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC314
     * �ڴ�����·�ߺ󣬵�����RoutePartLinkʱ��Ӧ��ListRoutePartLink�б�����Ӧ��·���Ƿ�ʹ��״̬���������ݵ�һ���ԡ�
     */
    public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc linkInfo, TechnicsRouteIfc routeInfo) throws QMException;

    //��þ���·�߰汾�Ĺ�����
    public ListRoutePartLinkIfc getListRoutePartLink(String routeListID, String partMasterID, String partNumber) throws QMException;

    /**
     * �ͻ����ڽ��б���ʱ��Ӧ�����ж�ListRoutePartLinkIfc��getAlterStatus()����=0��ȫ�����ó��½�״̬��=1����������
     * @roseuid 4030A8350200
     * @J2EE_METHOD -- saveRoute
     * @param routeRelaventObejts, key = bsoName, value = ��Ӧ��ֵ���󼯺ϡ�����·�߼��ϣ�����·��ֵ����·��ID�����ID. ���湤��·�߱༭ͼ��
     * @return Object[]:�����һ��Ԫ��--����·��ֵ��������ڶ���Ԫ��--HashMap �ṹͬgetRouteBranchs(String routeID)�� �ο��ĵ��� 1�� ���¹���·�� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC314 2����������·�� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC313 3�� PHOS-CAPP-BR211
     * ·�ߴ�������� ˵��������·�ߴ��Ĺ���Ϊ·�ߵ�λ�ڵ㣬һ����λ������һ��·�ߴ��г��ֶ�Ρ�·�ߴ��а����ӹ���λ��װ�䵥λ������·�ߴ��Ĺ��ɱ���������й��� 1. װ�䵥λ��һ��·�ߴ���ֻ����һ������ֻ�������һ���ڵ㣻 2. һ����λ�����һ��·�ߴ��г��ֶ�Σ�������ǲ�ͬ���͵Ľڵ�(�����װ��)�������������ڵ�λ�ó��֡� �� *
     * ���һ��·�ߴ�������˶��װ��ڵ㣬����ʾ��Ӧ����Ϣ�� �� * ���װ��ڵ㲻�����ڵ㣬����ʾ��Ӧ����Ϣ�� �� * ���·�ߴ��д������ڵ�ͬ���ͽڵ㣬����ʾ��Ӧ����Ϣ�� ע�⣺����ع���
     */
    public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc listLinkInfo, HashMap routeRelaventObejts) throws QMException;

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
     * @J2EE_METHOD -- deleteRoute
     * @param routeListPartLinkID ������ID.
     * @param routeID ·��ID. ɾ������·�ߡ� �ο��ĵ���ɾ������·�� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC315
     */
    public void deleteRoute(ListRoutePartLinkIfc linkInfo) throws QMException;

    /**
     * ����ɾ�����·��
     * @param list
     * 20120113 xucy add
     */
    public void deleteRoute(ArrayList list) throws QMException;
    
    /**
     * ��ɾ����ÿ�θ���֮ǰ����ɾ����
     * @param routeID String @
     */
    public void deleteBranchRelavent(String routeID) throws QMException;

    /**
     * ��ֻ���½ڵ�λ��ʱ�����ô˷�����������·�ߵĸ��¡�
     * @param coll Collection @
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
     * @J2EE_METHOD -- getPartLevelRoutes �������ID�͹���·�߼����ö�Ӧ�Ĺ���·�ߡ�
     * @return ���顣obj[0]= TechnicsRouteListIfc, obj[1],obj[2]��getRouteAndBrach(routeID), obj[3]=ListRoutePartLinkIfc��
     */
    public Object[] getProductStructRoutes(String partMasterID, String level_zh);

    //22. �鿴�㲿���Ĺ���·��
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC322

    /**
     * @param partMasterID
     * @param departmentID �û����ڵ�λ��codeID. ����һ��·�ߣ�����null��
     * @roseuid 4035D79C00B0
     * @J2EE_METHOD -- getPartLevelRoutes �������ID�͹���·�߼����ö�Ӧ�Ĺ���·�ߡ�
     * @return Collection ���鼯�ϡ�obj[0]= TechnicsRouteListIfc, obj[1],obj[2]��getRouteAndBrach(routeID), obj[3]=linkInfo�� �ο��ĵ��� �鿴�㲿���Ĺ���·�� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC322 1.
     * ϵͳ��ʾ�鿴һ��·�߽���(PHOS-CAPP-UI332)��(S1)������������ 2. ϵͳ��ʾ�鿴����·�߽���(PHOS-CAPP-UI333)��(��ע1)(S1)������������
     */
    public Collection getPartLevelRoutes(String partMasterID, String level_zh);
    
    
    //CCBegin SS6
    public Collection getPartLevelRoutes(String partMasterID, String partID, String level_zh);
    //CCEnd SS6

    public Collection getPartRoutes(String partMasterID) throws QMException;

    //////17.���ƹ���·��
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC317

    /**
     * �����ԡ���������·�߱��е�·�߽��и��ơ����"����"·�߱��������partMasterID���õ�·�ߡ� ע�⣺�������ֻ����һ������ڲ�ͬ·�߱���·�ߵĸ��ơ��������������ĸ���ճ����
     * @param partMasterID
     * @param departmentID �û����ڵ�λ��codeID. ����һ��·�ߣ�����null��
     * @roseuid 4035D79C00B0
     * @J2EE_METHOD -- getPartLevelRoutes �������ID�͹���·�߼����ö�Ӧ�Ĺ���·�ߡ� ִ���߸���һ������·�ߣ�ճ����û�й���·�ߵ��㲿���С� ���ƹ���·��ʱ�����Դӵ�ǰ·�߱��е�һ���㲿���Ĺ���·�߸��ƣ�Ҳ���Դ�һ���㲿��������·�߱���ƵĹ���·�߸��ƣ�
     * ճ��ʱ������ճ������ǰѡ�е��㲿����Ҳ����ճ������·�߱���������·�ߵ��㲿��������㲿������·�ߣ�ʹ��"ճ����"����·��ʱ�����ܸ��Ƶ���Щ�㲿����
     * @return Collecion���鼯�ϡ�obj[0]= TechnicsRouteListIfc, obj[1],obj[2]��getRouteAndBrach(routeID)��
     */
    public Collection getAdoptRoutes(ListRoutePartLinkIfc linkInfo1) throws QMException;

    /**
     * @param routeID ����·��ID
     * @param linkInfo
     * @roseuid 4030BD76011D
     * @J2EE_METHOD -- copyRoute ��������·�߼�������ڵ㡣
     * @return ����·��ID.
     */
    public ListRoutePartLinkIfc copyRoute(String routeID, ListRoutePartLinkIfc linkInfo) throws QMException;

    /////////////////////���ƽ�����////////////////////////////

    /////////////////////����Ŀ������ʼ////////////////////////////
    /**
     * key=��λֵ����CodingIfc��CodingClassificationIfc, value=��λ��Ӧ�����master���ϡ�
     * @param routeListID String @
     * @return HashMap
     */
    public HashMap getDepartmentAndPartByList(String routeListID) throws QMException;

    /**
     * @roseuid 403972CA007E
     * @J2EE_METHOD -- getParts ͨ����λ����ID��ö�Ӧ��Ҫ�๤�յ������ͨ������ɻ�������Ϣ����������·�ߵ���Ϣ���˷������ɹ��ղ��ֵ��á�
     * @return ���masterInfo���ϡ� ע�⣺��λ�нṹ������ʱ���ӽڵ�ҲҪ�����������ݲ�����
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
     * @param listMasterInfo TechnicsRouteListMasterIfc @
     * @return Collection ���еĴ�汾��Ӧ������С�汾����config���ˡ��汾�ṩ�ķ�����allVersionsOf(MasteredIfc mastered).
     */
    public Collection getAllVersionLists(TechnicsRouteListMasterIfc listMasterInfo);

    /**
     * �ṩ����ıȽϡ�
     * @param iteratedVec Collection ��ͬ�汾�Ĺ���·�߱�ֵ���󼯺ϡ�
     * @param isCommonSort �Ƿ���������ıȽϷ�ʽ�� @
     * @return Map key = partMasterID, value = Collection:listRoutePartLinkIfc���ϡ�����˳�򣬰汾���������С�
     */
    public Map compareIterate(Collection iteratedVec)  throws QMException;

    //routeListInfoΪ�µĴ�汾����ʱҪ���ƹ�������Ҫ����initialUsedΪ�µĴ�汾��
    public TechnicsRouteListIfc newVersion(TechnicsRouteListIfc routeListInfo);

    /**
     * �������masterID��ù���·�߱�ֵ���󼯺ϡ�
     * @param partMasterID String @
     * @return Collection
     */
    public Collection getListsByPart(String partMasterID);

    /////////////2004.4.27����ӵķ���/////////////
    /**
     * �жϸ��������masterID������·�߱����Ƿ���·�ߡ�
     * @param partMasteIDs Collection
     * @return Map, key=partMasterInfo, value = "������"���ޡ���
     */
    public Map getRouteByParts(Collection partMasteInfos, String level_zh);

    /**
     * @param �б仯�ķ�ֵ֧����alterStatus = 0;
     * @param routeMap Map
     */
    public void createRouteByBranch(ListRoutePartLinkIfc linkInfo, TechnicsRouteIfc route, Collection branchInfos);

    /**
     * ���·�֦��alterStatus = 1;
     * @param branchs Collection ��ֵ֧����
     */
    public void updateBranchInfos(Collection branchs);

    /**
     * @roseuid 4039965D0106
     * @J2EE_METHOD -- getRouteNodes ����·�����ͺͷ�֦ID��ù���·�߽ڵ㡣
     * @return Collection ����·�߽ڵ�ֵ���� ���ɿ����ڼ����ʼ�ڵ��Ƿ��������ӣ����У�����RouteListHandler�ĺ�����������������ʼ�ڵ㡣����ʵ�������ѡ�񡣣� �ο���PHOS-CAPP-UI321 �༭·��ͼ
     */
    public Collection getRouteNodes(String routeType, String routeBranchID) throws QMException;

    /**
     * @roseuid 4032F952020C
     * @J2EE_METHOD -- getListPartRoute �����㲿��ID�͹���·�߱�ID��ö�Ӧ�Ĺ���·�ߡ�
     * @return ����·��ֵ���� �ο��ĵ��� ���� PHOS-CAPP-BR208 ��������·�߼�� ˵����һ���㲿����һ��·�߱���ֻ�ܴ���һ������·�߶��� �� * ����㲿���Ѵ��ڹ���·�ߣ�����ʾ��Ӧ��Ϣ ������������·�߹����� �汾 <v2.0> �ĵ���ţ�PHOS-CAPP-UC311 4.
     * ϵͳ�˳�����PHOS-CAPP-UI325����ִ����ѡ���·�߱��ִ����ѡ�е��㲿����ʾ��"����·�߹�����"����(PHOS-CAPP-UI316)�У����������� public TechnicsRouteIfc getListPartRoute(String partMasterID, String routeListID) { return null; }
     */

    /**
     * @roseuid 40309C1300C3
     * @J2EE_METHOD -- getRoutes ���ݹ���·�߱�ID��ö�Ӧ�Ĺ���·�ߡ�
     * @return Collection ����·�߱��Ӧ�����й���·�ߣ�·�߷�֦��·�߽ڵ㡣 public Collection getRoutes(String routeListID) { return null; }
     */

    /**
     * @roseuid 40309EF101A5
     * @J2EE_METHOD -- getRoute ���ݲ�ƷID�����ID��ù���·��ֵ����
     * @return ����·��ֵ���󡣴˷�������ȶ��
     */
    public TechnicsRouteIfc getRoute(String productMasterID, String partMasterID);

    /**
     * @roseuid 4031A6A70254
     * @J2EE_METHOD -- getRouteListContent ���ݹ���·�߱�ID������й���·�߼���ڵ�ֵ����
     * @return ��ϣ�� key:bsoname;value=Collection ������Ӧֵ���󼯺�
     */
    public java.util.HashMap getRouteListContent(String routeListID, String level_zh);

    /**
     * @roseuid 4031A9080245
     * @J2EE_METHOD -- getListLevelRoutes ���ݹ���·�߱�·�߼����ù���·�ߡ� �������Ϊ�գ����ּ���
     * @return Collection ����·��ֵ����
     */
    public Collection getListLevelRoutes(String routeListID, String level_zh);

    /**
     * ����·����ض���ע�Ᵽ֤˳��
     * @param routeID String
     * @param �б仯�ķ�ֵ֧����
     * @return HashMap
     */
    public HashMap getRouteContainer(String routeID, Collection branchInfos) throws QMException;

    /**
     * ���ݹ���·�߻����صĽڵ�(��·�߷�֧����)���������
     * @param routeID
     * @return obj[0]=Map��key����ֵ֧����value���÷�֧�е�·�߽ڵ�ļ��ϣ� obj[1]=·�߽ڵ����ֵ���󼯺ϡ� @
     */
    public Object[] getBranchNodeAndNodeLink(String routeID) throws QMException;

    /**
     * �������ָ����Ʒ�����¹���·�߱�
     * @param productMasterID ��Ʒ
     * @param level_zh ·�߱���
     * @return ����·�߱�
     */
    public TechnicsRouteListInfo getRouteListByProduct(String productMasterID) throws QMException;

    public Object[] getMaterialBillRoutes(TechnicsRouteListInfo routeList, String partMasterID, String level_zh) throws QMException;

    /**
     * ���ָ���㲿��������Ϣ������һ���㲿��������Ϣ���ļ���
     * @param partMasterInfo ָ���㲿��������Ϣ��
     * @return ��һ���㲿��������Ϣ���ļ��� @
     */
    public Collection getSubPartMasters(QMPartMasterIfc partMasterInfo) throws QMException;

    /**
     * �Ը������㲿�����м�飬�����Щ�㲿���Ƿ������ӵ�����·�ߵ��㲿���� �����ǰ�༭�Ĺ���·�߱��Ƕ�������·�߱���ϵͳӦ����·�߱�ĵ�λ���ԣ��г���Ʒ�ṹ������һ��·���ж�Ӧ��λ���㲿������Ϊ��ѡ�㲿���� ������Դ��3.4 PHOS-CAPP-BR204 �Ӳ�Ʒ�ṹ������㲿����
     * @param codeID ����·�߱�ĵ�λ
     * @param productMasterID ����·�߱����ڵĲ�Ʒ
     * @param subPartMasters ����м����㲿�����ϣ�Ҫ��Ԫ��Ϊ�㲿������Ϣֵ����
     * @return �����а�������Ԫ�أ���һ��Ԫ���Ƿ����������㲿������Ϣ���ϣ��ڶ���Ԫ���ǲ������������㲿������Ϣ���� @
     */
    public Object[] checkSubParts(String codeID, String productMasterID, Collection subPartMasters) throws QMException;

    /**
     * @param depart String
     * @param partInfo QMPartInfo
     * @return Collection
     */
    public boolean getOptionPart(String depart, QMPartMasterIfc master);

    /**
     * ͨ��part�ҳ����з������ƹ淶����part������getOptionPart�������˽����
     * @param partMaster QMPartMasterIfc
     * @param configSpecIfc PartConfigSpecIfc
     * @param depart String @
     * @return Vector
     */
    public Vector getAllSubPart(QMPartMasterIfc partMaster, PartConfigSpecIfc configSpecIfc, String depart);

    /**
     * ���ݸ��������쵥λ��ѯ��ص��㲿���͹���·��id added by skybird 2005.3.9
     * @param makeDepartment ���쵥λ
     * @return Vector ���Ԫ��{partMasterID,routebsoID} @
     */
    public Vector getAllPartsRoutesM(String makeDepartment);

    /**
     * ���ݸ�����װ�䵥λ��ѯ��ص��㲿���͹���·��id added by skybird 2005.3.9
     * @param constructDepartment װ�䵥λ
     * @return Vector ���Ԫ��{partMasterID,routebsoID} @
     */
    public Vector getAllPartsRoutesC(String constructDepartment);

    /**
     * ���ݸ��������쵥λ��װ�䵥λ��ѯ��ص��㲿���͹���·��id added by skybird 2005.3.9
     * @param makeDepartment ���쵥λ
     * @param constructDepartment װ�䵥λ
     * @return Vector ���Ԫ��{partMasterID,routebsoID} @
     */
    public Vector getAllPartsRoutesA(String makeDepartment, String constructDepartment);

    public Collection getColligationRoutes(String mDepartment, String cDepartment, Vector parts, Vector products);

    /**
     * ���ݸ��������쵥λ��ѯ��ص��㲿���͹���·��id added by gcy 2005.5.09
     * @param mDepartment String ���쵥λ��id
     * @param cDepartment String װ�䵥λ��id
     * @param productID String ��Ʒ��id
     * @param source String �㲿������Դ
     * @param type String �㲿�������� @
     * @return Collection ������ϡ�ÿ����������һ��Vector.���η��㲿���������ڣ��㲿��id���㲿����ţ��㲿�����ƣ� �汾�������ţ��㲿���ڲ�Ʒ���������ϣ�·��������·�߼��ϣ�����·�߼���ÿ����Ŀ��������װ��2��·�ߣ�
     */
    public Collection getAllPartsRoutes(String mDepartment, String cDepartment, String productID, String source, String type);

    /**
     * �����㲿�������Ƿ���·��
     * @param partMasterID �㲿��bsoid
     * @return @
     */
    public boolean isHasRoute(String partMasterID);

    /**
     * �����㲿�������Ƿ���·��
     * @param partMasterID �㲿��bsoid
     * @return @
     */
    public Vector[] isHasRoute(Vector vec);

  //  public HashMap calCountInProduct(Vector parts, QMPartIfc parent, QMPartIfc product);

    /**
     * �����Ӽ��ڲ�Ʒ��ʹ�õ�������ֻ�����丸���ڲ�Ʒ�е�������
     * @param part QMPartIfc �Ӽ�
     * @param parent QMPartIfc ����
     * @param product QMPartIfc ��Ʒ @
     * @return int ����
     */
    public int calCountInProduct(QMPartIfc part, QMPartIfc parent, QMPartIfc product);

    /**
     * �õ�ǰ�û������ù淶�����㲿��
     * @param master QMPartMasterIfc @
     * @return QMPartIfc
     */
    public QMPartIfc filteredIterationsOfByDefault(QMPartMasterIfc master);

    /**
     * @roseuid 4039932300E0
     * @J2EE_METHOD -- getBranchRouteNodes ���ݹ���·�߷�֦ID��ù���·�߽ڵ㡣
     * @return Collection ����·�߽ڵ�ֵ����
     */
    public Collection getBranchRouteNodes(TechnicsRouteBranchIfc routeBranchInfo) throws QMException;

    //��·�߽ڵ����ͷ��ࡣ--װ��·�ߡ�����·�ߡ�
    public Object[] getNodeType(Collection branchNodes) throws QMException;

    // zz��� �ж��Ƿ���һ���������Ӽ�
    public Collection isParentPart(Vector maybeChild, QMPartIfc maybeParent);

    //zz���
    public Collection filteredIterationsOfByDefault(Collection masterCol);

    //zz ���
    public Collection getRouteListLinkPartsforVersionCompare(TechnicsRouteListIfc routeListInfo) throws QMException;

    // zz 20061110 Ϊ����·������㲿����Ҫ����
    public Collection getOptionPart(String depart, Vector masters);

    //CR3 begin
    public TechnicsRouteListMasterIfc rename(TechnicsRouteListMasterIfc routelist);

    //CR3 end

    //zz ���
    public String getMaterialRoute(QMPartIfc part);

    public Vector setBOMList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String source, String type, PartConfigSpecIfc configSpecIfc);

    public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, PartConfigSpecIfc configSpecIfc);

    public String getStatus2(String partMasterID, String level_zh, //gg
            String depart);

    public String getStatus(String partMasterID, String level_zh);//gg

    //begin CR2
    public TechnicsRouteListIfc checkInTechRouteList(WorkableIfc workable, String location);

    /*
     * �������·�߱�
     */
    public Collection checkOutTechRouteList(TechnicsRouteListIfc[] checkoutinfo, boolean flag);

    public VersionedIfc reviseTechnicsRouteList(VersionedIfc reviseIfc, String foldLocation, String lifecycleTemName, String projectName);

    //end CR2
    //CR4 begin �������
    public Collection findMultPartsByNumbers(Object[] param);

    //CR4 end

    public boolean getAdoptStatusByPart(String partmasterID);

    //CR5 begin ���ñ��������
    public Vector findQMPartByChangeOrder(Vector changeorderinfo);

    public Collection findChangeOrders(Object[][] param);

    public Collection findAdoptOrders(Object[][] param);

    public Vector findQMPartByAdoptOrder(Vector adoptorderinfo);

    //CR5 end

    //CR6 begin
    public Vector findQMPart(Object[][] param) throws QMException;
    //CR13
    public Object[] getAllPartAndSubPartByCurConfigSpec(Vector parentPart);

    //CR6 end

    //CR7
    public Object[] createLinksAndRoutes(TechnicsRouteListIfc routeListInfo, ArrayList createRouteCol) throws QMException;
    
    /**
     * ������׼���������·����Ϣ�� �����ֱ�ΪҪɾ���������Ϣ���ϣ���׼����Ҫ������Ҫ���µĹ�����Ϣ����
     */
    public Object[] updateLinksAndRoutes(HashMap deleteCollection, TechnicsRouteListIfc routeListInfo1, ArrayList allLinkCol) throws QMException;
    
    /**
     * �õ��㲿�������и����Լ�·�����
     * @param partmaster QMPartMasterIfc��listID String
     * @throws QMException
     * @return Vector
     */
    public Vector findParentsAndRoutes(QMPartMasterIfc partmaster);
    //CR8
    //CR12 begin
    /**
     * ����·�߷���
     */
    public TechnicsRouteListIfc releaseListPartsRoute(TechnicsRouteListIfc routeList);
    //CR12 end
    /**
     * 20120109 xucy add
     * @param parts
     * @param parent
     * @param product
     * @return
     */
    public HashMap calCountInProduct(Vector parts, QMPartMasterIfc productMaseter);
    
    /**
     * ��������Ч·�� 20120112 �촺Ӣ add
     */
    public Collection findPartEffRoute(String partMasterID);
    
    /**
     * ����ָ���������Ч·�ߡ�ʧЧ·��
     * 20120110 �촺Ӣ add
     */
    public  Object[] findPartEffAndDisabledRoute(String partMasterID);
    //CR9 begin
    /**
     * �ж�Ҫ�����·�߱��е��㲿���Ƿ���·��
     * @param obj
     * @return
     */
    public boolean haveroutelist(TechnicsRouteListInfo obj);
    //CR9 end
    //CR10 begin
    /**
     * ����������������Ч·�ߵ��㲿��
     * @param param
     * @return
     */
    public  Vector copyFromByQMPart(Object[][] param);
    //CR10 end
    public HashMap saveAsRoute(Vector Vec);
    public void saveModelRoute(ModelRouteInfo modelroute);
    public ModelRouteInfo findModelRouteByPartID(String partID);
    public void delrouteobject(Vector routeVec);
    //begin CR11
     /**
     * �������·�߶���
     */
    public void saveShortCutRoute(List list, String userID);
    
    /**
     * ͨ���û������ҿ��·��
     */
    public HashMap getShortRouteMap(String userID);
    //end CR11
    
    /**
     * �����·�߱���ص��㲿����
     * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ���� @
     * @return Collection �洢ListRoutePartLinkIfc������ֵ���󼯺ϡ�
     * @see TechnicsRouteListInfo 20120129 xucy add �鿴��׼���ʱ���ô˷���
     */
    public Collection getRouteListLinkParts1(TechnicsRouteListIfc routeListInfo) throws QMException;
    public HashMap ApplyModelRoute(ArrayList list);
    /**
     * ͨ���������ϢID��ȡ�������ù淶�����
     */
    public Object filteredIterationsOf(String masterID);
    public HashMap getRouteInformation(Vector routeVec);
    public Vector ViewModelRoute(Object[] param);
    /**
     * ���ɱ����������Ʊ��������ʽΪ�����ļ���
     * @param routeListID String ·�߱��ID
     * @return String ·�߱������+���+�Ĺ���·�߱��� 20120214 xucy modify
     * �ݿͻ���
     */
    public String exportRouteList(String routeListID) throws QMException;
    
    public Map getProductRelations(String productIDs);
    
    public void setAttrForPart(BaseValueIfc primaryBusinessObject);
    
    public Collection getAllPartsRoutes_new(String mDepartment, String cDepartment, String productID, String partName , String state) throws QMException;
    
    /**
     * �������ڲ�Ʒ�е�ʹ������
     * @param partVec �������
     * @param pruduct ���ڲ�Ʒ
     * @return
     */
    public HashMap getPartsCount(Vector partVec,QMPartMasterIfc pruduct);
        //CCBegin SS1
    public QMPartIfc getLastedPartByConfig(QMPartMasterIfc master,PartConfigSpecIfc configSpecIfc) throws
        QMException;

    public ArrayList gatherExportData(String routeListID) throws QMException;
    //CCEnd SS1
    
    /**
     * ���ָ��������ض����ù淶������С�汾 20061209 liuming add
     * @param partMasterBsoID
     * @param configSpecIfc ���configSpecIfc=null�����õ�ǰ�û������ù淶
     * @return QMPartIfc �����ǰ�û������ù淶��û�в鿴�����Ȩ�޻�û�а汾���򷵻�null
     * @throws QMException
     */
    public QMPartIfc getLastedPartByConfig(String partMasterBsoID,
                                           PartConfigSpecIfc configSpecIfc) throws
        QMException;
    
    /**
     * ��׼֪ͨ�鱻��׼�󣬽�·��״̬Ϊ������׼���Ĺ������㲿��״̬����Ϊ����״̬
     * ��·��״̬Ϊ������״̬ʱ�����������㲿��״̬����Ϊ����׼��״̬
     * @author ������
     * @version 1.0
     */
    public void setRouteListPreparePartsState(BaseValueIfc primaryBusinessObject);
    
    public ArrayList getRouteCompleteLinkedPartByBsoID(String
  	      routeCompleteBsoID);
    
    /**
     * �����ձ�֪ͨ���BsoID��ȡ�����Ĺ���·��
     *@author ������
     *@version 1.0
     * @param routeCompleteBsoID �ձ�֪ͨ���BsoID
     * @return ����·�߼���
     */
    public ArrayList getCompleteLinkedListByBsoID(String
                                                  routeCompleteBsoID);
    
    
    /**
     * �����ձ�֪ͨ��������㲿������������״̬
     * ���㲿������������״̬����Ϊ����״̬
     * @author ������
     * @version 1.0
     * @param routeListCompleteID �ձ�֪ͨ���BsoID
     */
    public void setRouteListCompletePartsState(String routeListCompleteID);
    
    public void setRouteListCompletePartsState1(String routeListCompleteID);
    
    public Collection getRouteCompleteLinkedParts(String completeListID) throws
    QMException;
    
    public Collection getRouteCompleteLinkedProductsNames(String completeID) ;
    
    public Collection getNeededCollForReport(Collection partMasterIDColl);
    
    public Collection getNeededCollForReport(Collection partMasterIDColl,
            TechnicsRouteListInfo routeList);
    
    /**
     * ��ȡ�ձ�֪ͨ���·�����ƺ�˵����Ϣ
     * @author ������
     * @version 1.0
     */
    public Collection getCompleteLinkedRouteListNameAndDescr(String completeID);
    public void setRouteListPreparePartsState1(BaseValueIfc primaryBusinessObject);
    public String[] getRouteString(QMPartIfc part) throws QMException;
    public CodeManageTable getFirstLeveRouteListReportliu(TechnicsRouteListIfc
    	      listInfo) throws QMException;
    //CCBegin SS2
    public String getIBA(QMPartIfc part) throws QMException;
    //CCBegin SS2
    
    // CCBegin SS3
    /**
     * ����㲿���ķ���Դ�汾
     * @param part
     * @return
     */
    public String getSourceVersion(QMPartInfo part);
    // CCEnd SS3
    
    
    //CCBegin SS4
    public Collection getJFRouteList(Object[][] param);
    //CCEnd SS4
    
    //CCBegin SS5
    public Vector findQMPartByJFRouteList(Vector vec, String routestr);
    //CCEnd SS5
//    CCBegin SS7
    public Collection selectPartRoute (String bsoid) throws QMException;
//    CCEnd SS7
    //CCBegin SS8
    public Collection CTfindPartByRoute(Object[][] param);
    public  String[] findQMPartRouteLevelOne(String partID);
    public Vector findQMPartByCTRouteList(Vector routeListVec);
    //CCEnd SS8
    //CCBegin SS9
    public Vector CTSecondgatherExportData(String routeListID) throws QMException;
    public String getSencondRouteReportHead(String routeListID) throws QMException;
    public String getSecondPartProduct(String routeListID)throws QMException;
    //CCEnd SS9
    //CCBegin SS10
    /**
     * ���ظ���·�߷���֮�������������׼��״̬
     * @param primaryBusinessObject
     * @return
     */
    public void setCTRouteListPreparePartsState(BaseValueIfc primaryBusinessObject) ;
    //CCEnd SS10
    
    //CCBegin SS11
    public PartConfigSpecIfc getCurrentUserConfigSpec() throws QMException;
    //CCEnd SS11
    
    //CCBegin SS12
    public String[] getLaterRouteByPartID(String partID);
    //CCEnd SS12
    
    //CCBegin SS13
    public String getRouteTextByRouteID(String routeid) throws QMException;
    //CCEnd SS13
    
  //CCBegin SS14
  /**
	 * @param param
	 *            ��ά���飬5��Ԫ�ء�����obj[0]=��ţ�obj[1]=true.
	 *            true=�ǣ�false=�ǡ�����˳�򣺱�š����ơ����𣨺��֣������ڲ�Ʒ���汾�š�
	 * @roseuid 402CBAF700CA
	 * @J2EE_METHOD -- findRouteLists ��ù���·�߱�������Χ����š����ơ��������ڲ�Ʒ���汾�š�
	 * @return collection ����·�߱�ֵ�������°汾�� ��ʱҪ��ConfigService���й��ˡ�
	 */
	public Collection findRouteListsForCd(Object[][] param) throws QMException;
  //CCEnd SS14
	
	//CCBegin SS15
	/**
	 * �жϸø������·�����Ƿ�����и�·�ߵ�λ
	 * @param part QMPartIfc
	 * @param atts String[]
	 * @throws QMException
	 * @return Vector
	 */
	public boolean isIncludeDepartment(QMPartIfc part, String routeDepartment) throws QMException ;
	  
	/**
	 * רΪ���������൥����
	 * lilei add 2006-7-11
	 * @param part QMPartIfc
	 * @param atts String[]
	 * @param routes String[]
	 * @throws QMException
	 * @return Vector
	 */
	public String[] getMaterialRoute(QMPartIfc part, String[] atts, String[] routes) throws QMException;
	//CCEnd SS15
	//CCBegin SS16
	  /**�ɶ����������㲿��
	 * @param param
	 * @return
	 * @throws QMException
	 */
	public Collection cDfindMultPartsByNumbers(Object[] param)throws QMException;
	//CCEnd SS16
	//CCBegin SS17
	/**
	 * ������еĹ���·�߱�����°汾�������A,B�棬����B������С�汾�� @
	 * @return Collection ���obj[]���ϣ�<br>obj[0]������·�߱�ֵ����
	 */
	public boolean searchSameNameList(TechnicsRouteListIfc routeListInfo)throws QMException;
	//CCEnd SS17
}
