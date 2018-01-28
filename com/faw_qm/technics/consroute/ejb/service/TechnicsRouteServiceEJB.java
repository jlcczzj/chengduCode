/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2009/02/26  ������  ԭ���Ż��������·�߱���
 *  
 *                         ������1.������duplicate()����ȥ����ԭ����������ֱ�ӽ����������
 *                                 bsoid,createTime,modifyTime������Ϊnull��
 *                                 
 *                               2.��getRouteListLinkParts()�����ϲ���copyRouteList()�����У�
 *                                 ���ٶ�ͬһ�����ϵ�ѭ�������������־û������
 *                                 ��ѯ�ͱ��淽���滻Ϊ�����ź�.
 *                               
 *                               3.����ʾ�������·�߱�ʱ�����ҹ����߼���
 *                                 ��������з����ȥ,ֻ��ѡ��"�㲿��"TABҳʱ��ȥ�����.
 *                         
 *                         ��ע: ���ܲ�����������"�������·�߱�".  
 *                            
 *                                 
 *CR2 2009/02/18  ������   ԭ���Ż�ɾ������·�߱��ܡ�
 * 
 *                         ��������ɾ��·����Ϣʱ���ٶ��޸ĺ�Ĺ���������б���,
 *                               ɾ���������󻻳��ڳ־û������.
 *                       
 *                         ��ע: ���ܲ�����������"ɾ������·�߱�". 
 *                         
 *                         
 *CR3 2009/04/10  ������   ԭ��: �Ż���������·�߱���
 *                         
 *                         ����:1.����������ڴ������߼�.
 *                               
 *                         ��ע: ���ܲ�����������"��������·�߱�".    
 *                         
 *CR4 2009/04/22 ������    ԭ��:������·�߱�ʱ��Ų���Ψһʱ����ʾ��Ϣ����ȷ��
 *                         �������Լ������쳣��
 *                         
 *CR5 2009/06/3  ������    �μ�:������:v4r3FunctionTest;TD��2260
 *
 *
 *CR6 2009/06/16 ������    �μ���������:v4r3FunctionTest;TD��2413
 * 
 *CR7 2010/06/29 ������    �μ�: ��:product;TD��2275
 *CR8 2011/12/15 �촺Ӣ   ԭ���޸ļ��롢���������������޶��ķ�ʽ
 *CR9 2011/12/20 ����  ԭ��ͳһ����������
 *CR10 2011/12/21 �� ��  ԭ������������ӷ���
 *CR11 2011/1222  ����  ԭ��ͨ�����ID�����Ƿ������Ч��·��
 *CR12 2011/12/23 ���� ԭ������ͨ���Ӳ��ñ��������
 *CR13 2011/12/26 ���� ԭ�������������¹��ܣ�����·�ߴ����� �ṹ�����ȣ�
 *CR14 2012/01/04 �촺Ӣ   ԭ�����ӱ�����׼�����������·����Ϣ�ķ���
 *CR15 2012/01/06 �촺Ӣ   ԭ�����ӷ���·�߹���
 *CR16 2012/01/06 �촺Ӣ  ԭ�����Ӳ鿴��������
 *CR17 2012/01/11 ���� ԭ�����Ӽ���·�߱�ʱ������Ƿ���·�ߵ��ж�
 *CR18 2012/01/11 ���� ԭ�����Ӹ����Բ�ѯ����
 *CR19 2012/01/18 xucy  ԭ�����ӿ��·�߹���
 *CR20 2012/03/28 ���� ԭ��μ�TD5903
 *CR21 2012/03/28 ���� ԭ��μ�TD5940
 *CR22 2012/03/29 ���� ԭ��μ�TD5958
 *CR23 2012/03/29 ����ԭ��μ�TD5975
 *CR24 2012/03/29 ����ԭ��μ�TD5969
 *CR25 2012/04/01 ����ԭ��μ�TD6012
 *CR26 2012/04/05 ����ԭ��μ�TD6009
 *CR27 2012/04/06 ����ԭ��μ�TD5938
 *CR28 2012/04/06 ����ԭ��μ�TD5963
 *CR29 2012/04/09 ����ԭ��μ�TD6019
 *CR30 2012/04/11 ����ԭ��μ�TD6030
 *SS1 ����ݿͻ�����ı����� ������ 2013-03-01
 *SS2 ·�߰�����֪ͨ�������������޸� ������ 2013-03-01
 *SS3 �����乤��·���������ʱ��ʾ���������Ϣ pante 2013-11-02
 *SS4 �����乤��·���ظ����ͬһ�����ͬ�汾 pante 2013-11-16
 *SS5 �༭����·�߽����㲿��������ʾ�İ汾ֵ�޸�Ϊ����Դ�汾  ����ͮ 2013-12-16
 *SS6 ���������׼ liunan 2013-12-23
 *SS7 ���ݽ����׼��·�������㲿�� liunan 2013-12-23
 *SS8 �޸ı������������ʾ������������ pante 2013-12-27
 *SS9 ����������Ӳɹ���ʶ liuyang 2014-1-13
 *SS10 �����������׼����������ʼʱ�䡱�͡�����ʱ�䡱�޸�Ϊ������ʱ�䡱�͡�����ʱ�䡱 liuyang 2014-2-24
 *SS11 �����㲿���汾 liuyang 2014-2-25
 *SS12 ���������ʷ�汾��Ϊ�� liuyang 2014-2-25
 *SS13 ������Ķ���·�߱����ˮ�� Liuyang 2014-2-27
 *SS14 �޸ı��������·�����������ʾ�����������⣬��Ϊconslistpartlink����rightbsoid�ﲻһ������qmpartmasterid�ˣ��µ���qmpartid�ˡ� liunan 2014-1-23
 *SS15 �������׼����֧��ģ������ liunan 2014-2-17
 *SS16 ������Ķ���·�߱����ˮ���ڿͻ��˽��д��� Liuyang 2014-4-8
 *SS17 ����·�߰��㲿�������� Liuyang 2014-4-18
 *SS18 ��ȡ�㲿���ṹ�������㲿�����Ϻͷ����㲿�����϶�Ҫ�ĳ�QMPartIfc���������� QMPartMasterIfc �ˣ���˿ͻ�������㲿�����Ѹĳɼ�¼QMPartIfc�� liunan 2014-3-17
 *SS19 ����㲿������·������ʱ����QMPartMasterIfc��ΪQMPartIfc�� liunan 2014-5-13
 *SS20 ��������QMPartMasterIfc��ΪQMPartIfc�� liunan 2014-5-21
 *SS21 ����·�ߵ�λ��������·�ߣ�QMPartMasterIfc��ΪQMPartIfc liunan 2014-6-19
 *SS22 ������û�Ҫ��,�ձϱ���ʱ,��������������е�·����Ϣ������ pante 2014-06-26
 *SS23 �޸���ݰ�·�ߵ�λ�����޽������ pante 2014-09-04
 *SS24 �������鿴����·���п������ձ�·�� pante 2014-10-09
 *SS25 ����·�߹����㲿�����ӹ�Ӧ�� liuyang 2014-8-20
 *SS26 ������������һ��·��  liuyang 2014-8-25
 *SS27 ���ض���·�߱��� liuyang 2014-9-3
 *SS28 �����������һ��������·�����ó�����׼��״̬ ���� 2014-11-19
 *SS29 A005-2014-3019�����䣬�޸��㲿������·�߱���ʾ�ü����а汾���㲿��·�ߡ� liunan 2014-12-24
 *SS30 �������������·����׼������״̬ʱ��֧��QMPartMasterIfc��QMPartIfc��������� liunan 2015-2-6
 *SS31 �������������·����׼������״̬ʱ������ʷ��ȡbranchid����ȡ���°�����״̬��������û��branch�ļ�¼����Ϊȡrightbsoid����״̬�� liunan 2015-2-25
 *SS32 ����һ��·���޸����״̬����Ҫ�ų��������׼�����  ����  2015-3-9
 *SS33 ����SS29����ʾ�ü�����С�汾��·�ߣ�����Ӧ���Ǵ�汾������Щ��ʷ���༭·�ߵļ������ڴ�汾������С�汾�ϡ� liunan 2015-3-24
 *SS34 ��ݸ��²ɹ���ʶ����ʹ������Ҫ�ж����������ø����ԡ� liunan 2015-4-29
 *SS35 ����һ��·�߱�������������������  ����  2015-5-29
 *SS36 �㲿���б���ʾ˳������ liuzhicheng 2015-6-17 
 *SS37 A005-2014-2957 ����������������㲿�����������°�·�ߣ���ȥ����·�ߣ�û�еĻ�ȡ���һ��·�ߡ� liunan 2015-7-16
 *SS38 A005-2015-3112 �༭����·�� ���岿�� ʱ�޷��༭���޷����� liunan 2015-11-12
 *SS39 A005-2015-3110  ȥ��alterStatus���жϣ�ʵ�ʱ������������ liunan 2015-11-13
 *SS40 �ɶ�����·�����ϣ��Զ���� liunan 2016-8-3
 *SS41 �ɶ�����·�����ϣ�����·�߱��� liunan 2016-8-15
 *SS42 �ɶ����ͨ����׼������ guoxiaoliang 2016-07-18
 *SS43 �ɶ�������ӻ���Ӽ� guoxiaoliang 2016-7-28
 *SS44 ����û����ʱ������û�ʱ����Ϊû��Ȩ�ޣ��ٽ���refreshʱ����� ������ 2016-09-01
 *SS45 A032-2016-0115 �ɶ���·�ߵ�λ�����Ĳ���ʹ�� liunan 2016-9-12
 *SS46 A032-2016-0116 �ɶ������㲿������·�߲���ʹ�� liunan 2016-9-12
 *SS47 �޸ĳɶ����������㲿��Ϊ��ȷ���� liuyuzhu 2016-11-10
 *SS48 ������ͬ����·�߱� liuyuzhu 2016-10-12
 *SS49 �ɶ�����·��ͨ���㲿����Ž����жϡ� liunan 2016-10-14
 *SS50 �ɶ�������ɫ����ʶcolorFlag���ԡ� liunan 2016-10-25
 *SS51 ����·���ڻ�ȡ�����׼ʱ�򣬲��ǻ�ȡ��ű༭·�ߵİ汾����������С�汾 ������ 2016-10-28
 *SS52 �鿴·��ʱ������û��Ȩ�޵Ķ���·�߹��˵�����ʾ��Ȩ���ġ� liunan 2016-12-7
 *SS53 ����SS5����ȡ����Դ�汾����ȡ�����汾��û����Ϊ���ַ����� liunan 2017-4-11
 *SS54 �ɶ�����·�����⴦�����ʶ��Ϊ�ɶ����㲿�����а汾ת�� ����� 2018-01-11
 *SS55 �ɶ��鿴����·�߱���Ҫ��ֻ����ǰ�汾�㲿���Ķ���·�ߡ� liunan 2018-1-17
 *SS56 �ɶ�����·����ʾ����Դ�汾 ����� 2018-01-21
 */

package com.faw_qm.technics.consroute.ejb.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.bsx.util.BSXUtil;
import com.buildnum.ejb.service.numService;
import com.faw_qm.doc.util.DocLastConfigSpec;
import com.faw_qm.enterprise.model.MasterInfo;
import com.faw_qm.part.model.PartConfigSpecInfo;

import com.faw_qm.consadoptnotice.model.AdoptNoticeInfo;
import com.faw_qm.consadoptnotice.model.AdoptNoticePartLinkInfo;
import com.faw_qm.cappclients.conscapproute.graph.RouteItem;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.conscapproute.web.ReportLevelOneUtil;
import com.faw_qm.cappclients.conscapproute.web.ViewRouteListUtil;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.util.CodeManageTable;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.config.util.LatestConfigSpec;
import com.faw_qm.config.util.MultipleLatestConfigSpec;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.enterprise.ejb.service.EnterpriseService;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.jfpublish.receive.PublishHelper;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.ejb.enterpriseService.EnterprisePartService;
import com.faw_qm.part.ejb.extendedService.ExtendedPartService;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.NormalPart;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.part.util.Unit;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.capp.ejb.standardService.StandardCappService;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.consroute.model.CompletListLinkInfo;
import com.faw_qm.technics.consroute.model.CompletPartLinkInfo;
import com.faw_qm.technics.consroute.model.CompletedPartsInfo;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.consroute.model.ModelRouteInfo;
import com.faw_qm.technics.consroute.model.RouteBranchNodeLinkIfc;
import com.faw_qm.technics.consroute.model.RouteListProductLinkInfo;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.RouteNodeLinkIfc;
import com.faw_qm.technics.consroute.model.RoutelistCompletInfo;
import com.faw_qm.technics.consroute.model.ShortCutRouteInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListMasterInfo;
import com.faw_qm.technics.consroute.util.RouteAdoptedType;
import com.faw_qm.technics.consroute.util.RouteCategoryType;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.technics.consroute.util.RouteWrapData;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.util.JNDIUtil;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.version.util.VersionControlHelper;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkingPair;
import com.faw_qm.epm.build.model.EPMBuildLinksRuleInfo;
import com.faw_qm.epm.build.model.EPMBuildRuleIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;
import com.faw_qm.lifecycle.util.LifeCycleState;

// * �ο��ĵ���
// * Phos-REQ-CAPP-BR02(����·��ҵ�����)V2.0.doc
// * PHOS-REQ-CAPP-SRS-002(����ҵ��ҵ�������ݹ���������˵��-����·��) V2.0.doc
// * "Phos-CAPP-UC301--Phos-CAPP-UC322"��19��������
// *  (������) 200605 zz �����޸� �޸�ԭ��:��ѯ�㲿����������·��,Ӧ���Ǳ����õ�·��
// * (����һ)20060609 zz �����޸� �޸�ԭ��:�Ż��鿴·�ߵĹ����㲿��,�޸�Ϊ��������,�����м���������
// * (�����)20060701 zz �����޸� �޸�ԭ��:�Ż�����λ����·��,��������ù淶�Ĺ��˸�Ϊ����һ�ι���.
// * (������)20060629 zz �����޸� �޸�ԭ��:����·�����ɱ�������ٶ���,ͬʱ�޸������ɱ����jsp
// * �������ģ�2006 07 12  zz ������� �����Ż� ��ͬʱ�޸���ComcreateLinksAndRoutespareHandler
// * �������壩2006 08 03  zz ������� �����Ż� ���ڹ���·�ߴ�������һ�����Ա���·�ߴ��ַ���
// * �ݿͻ�����ʾ����·�ߴ���ʱ�����ֱ��ȡ�����ַ�����ʾ
// * ���������� 2006 08 04  zz �����޸� ������������㷨���������ظ�Ԫ��bug
// * �������ߣ� 2006 09 04 zz �����޸� �޶����°�·�߱���Ĺ����㲿����·��״̬ԭΪ���õı�Ϊȡ����
// * ��������Ӧ�ø���ǰһ�汾��״̬
// * (�����)20061110 zz �������,Ϊ����·�� "���"��ť ��Ӹ�����ѡ��λ���˹���
// * (�����)������� zz ��Ӽ�����÷��������ٿͻ��˵��ô���
// * ������ʮ�����ӹ���·�ߵ����������� ������� zz 20061214

/**
 * �ṩ�Թ���·�߱�� ���������£�ɾ�����鿴�ķ���
 * @author ������
 * @version 1.0
 */
public class TechnicsRouteServiceEJB extends BaseServiceImp
{ /////////////////�еĶ��������ܲ���Ҫdistinct.
    public final static boolean VERBOSE = Boolean.valueOf(RemoteProperty.getProperty("com.faw_qm.technics.consroute.verbose", "true")).booleanValue();

    //public static String TIME = "< " + DateFormat.getInstance().format(new java.util.Date()) + " > ";
    public static String TIME = "< " + new java.util.Date().toString() + " > ";

    //����״̬��
    public static final int INHERIT = 0;
    public static final int ROUTEALTER = 1;
    public static final int PARTDELETE = 2;

    public static final String LEFTID = "leftBsoID";
    public static final String RIGHTID = "rightBsoID";
    public static final String VERSION_SERVICE = "VersionControlService";
    public static final String CONFIG_SERVICE = "ConfigService";
    public static final String PART_SERVICE = "StandardPartService";
    //���һ��
    //public static final String FOLDER_SERVICE = "FolderService";
    public static final String CodingClassificationEJB = "CodingClassification";
    public static final String LIST_ROUTE_PART_LINKBSONAME = "consListRoutePartLink";
    public static final String PARTMASTER_BSONAME = "QMPartMaster";
    //CCBegin SS8
    public static final String PART_BSONAME = "QMPart";
    //CCEnd SS8
    public static final String ROUTELIST_BSONAME = "consTechnicsRouteList";
    public static final String ROUTELISTMASTER_BSONAME = "consTechnicsRouteListMaster";
    public static final String ROUTENODE_BSONAME = "consRouteNode";
    public static final String ROUTENODE_LINKBSONAME = "consRouteNodeLink";
    public static final String TECHNICSROUTE_BSONAME = "consTechnicsRoute";
    public static final String TECHNICSROUTEBRANCH_BSONAME = "consTechnicsRouteBranch";
    public static final String BRANCHNODE_LINKBSONAME = "consRouteBranchNodeLink";
    public static final String BRANCH_ROLENAME = "branch";
    public static final String FIRSTROUTE = "FIRSTROUTE";

    private final String comma = ",";
    private final String four_comma = comma + comma + comma + comma;
    private final String six_comma = comma + comma + comma + comma + comma + comma;
    private final String line = "--";
    private final String newline = "\n";
    public static String noRouteStr = "";
    private final static String RESOURCE = "com.faw_qm.technics.consroute.util.RouteResource";
    private String PARTRESOURCE = "com.faw_qm.part.util.PartResource";

    private static final String routeMode = RemoteProperty.getProperty("routeManagerMode", "partRelative");//xucy 1226

    public TechnicsRouteServiceEJB()
    {}

    //////////////////////////////////����Ϊ���Է���/////////////////////////////////////
    public Object processTest(int i)
    {
        Object obj = null;
        ServiceTestHandler handler = new ServiceTestHandler();
        switch(i)
        {
        case 1:
        {
            //obj = handler.findRouteLists();
            break;
        }
        default:
        {
            break;
        }
        }
        return obj;
    }

    //////////////////////////////////���Է�������////////////////////////////////////
    /**
     * getServiceName
     * @return String
     */
    /**
     * ��÷�����
     * @return TechnicsRouteService
     */
    public String getServiceName()
    {
        return "TechnicsRouteService";
    }

    /**
     * ���湤��·���б��ǿռ����ֵ������������Ψһ�Լ�������ݿ������á�
     * @param routeListInfo TechnicsRouteListIfc ָ����·�߱�ֵ���� @
     * @return TechnicsRouteListIfc ����·�߱��ֵ����
     * @see TechnicsRouteListInfo ָ����·�߱�ֵ����
     */
    public TechnicsRouteListIfc storeRouteList(TechnicsRouteListIfc routeListInfo)throws QMException
    {
        TechnicsRouteListIfc routeListInfo1 = null;
        if(PersistHelper.isPersistent(routeListInfo))
        {
            throw new QMException("routeService's storeRouteList : routeListInfo��Ӧ���־û���");
        }
        //CCBegin SS40
        String com = "";
        //CCEnd SS40
        try
        {
            FolderService fservice = (FolderService)EJBServiceHelper.getService("FolderService");
            fservice.assignFolder(routeListInfo, routeListInfo.getLocation());
            //CCBegin SS16
//           //CCBegin SS13
//            String comp="";
//            try {
//    			 comp=RouteClientUtil.getUserFromCompany();
//    		} catch (QMException e) {
//    			e.printStackTrace();
//    		}
//    		if(comp.equals("zczx")){
//                numService sservice = (numService)EJBServiceHelper.getService("numService");
//                String num="";
//                String routeListNumber =routeListInfo.getRouteListNumber()+"-";
//                if(routeListInfo.getRouteListState().equals("��׼")){
//                   num=sservice.buildSerialNum(routeListNumber, 3);
//                }
//                if(routeListInfo.getRouteListState().equals("����׼")){
//                    num=sservice.buildSerialNum(routeListNumber, 3);
//                   }
//                if(routeListInfo.getRouteListState().equals("ǰ׼")){
//                    num=sservice.buildSerialNum(routeListNumber, 3);
//                   }
//                if(routeListInfo.getRouteListState().equals("��׼")){
//                    num=sservice.buildSerialNum(routeListNumber, 3);
//                   }
//                if(routeListInfo.getRouteListState().equals("�ձ�")){
//                    num=sservice.buildSerialNum(routeListNumber, 3);
//                   }
//                if(routeListInfo.getRouteListState().equals("�շ�")){
//                    num=sservice.buildSerialNum(routeListNumber, 3);
//                   }
//                routeListInfo.setRouteListNumber(num);   
//
//    		} 
//            //CCEnd SS13
            //CCEnd SS16
            
            //CCBegin SS40
            DocServiceHelper dsh = new DocServiceHelper();
            com = dsh.getUserFromCompany();
            if(com.equals("cd"))
            {
            	String num = getAutoRouteListNumber(routeListInfo);
              if (num.getBytes().length > 200)
              {
            	  throw new QMException("�Զ������ȡ�����ڲ�Ʒ��Ϣ��װ�ɵı�ų��ȴ���200");
              }
              routeListInfo.setRouteListNumber(num);
            }
            //CCEnd SS40
			
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            routeListInfo1 = (TechnicsRouteListIfc)pservice.saveValueInfo(routeListInfo);
  
        }catch(Exception e)
        {
            e.printStackTrace();
            if(VERBOSE)
            {
                System.out.println(TIME + "!!!!!e.getMessage = " + e.getMessage() + "\n @@@@@" + e.toString());
            }
            if(e instanceof Exception)
            {
            	e.printStackTrace();
                //�ж�Ψһ�ԡ�
                //xucy
                //                String obj = routeListInfo.getRouteListName()+"_"+routeListInfo.getRouteListNumber();
                //                throw new QMException("3", obj);
                Object[] obj = {routeListInfo.getRouteListName(), routeListInfo.getRouteListNumber()};
                throw new QMException("com.faw_qm.technics.consroute.util.RouteResource", "3", obj);
            }else
            {
                this.setRollbackOnly();
                throw new QMException(e);
            }
            //throw new TechnicsRouteException(e);
        }
        try
        {
            if(VERBOSE)
            {
                System.out.println(TIME + "��ʼ����·�߱�Ͳ�Ʒ�Ĺ�����");
                //��ʱrouteListInfo�Ѿ������档����·�߱�Ͳ�Ʒ�Ĺ������˹���ֻ�ܴ�����ɾ����ɾ���ɳ־û�ά����
            }
            RouteListProductLinkInfo listProductInfo = new RouteListProductLinkInfo();
            //if(routeListInfo1.getMaster() == null || routeListInfo1.getMaster().getBsoID() == null)
            //throw new TechnicsRouteException("routeService's storeRouteList ·�߱��masterû�б�������");
            listProductInfo.setRouteListMasterID(routeListInfo1.getMasterBsoID());
            if(routeListInfo1.getProductMasterID() == null)
            {
                throw new QMException("routeService's storeRouteList ��Ʒ��masterIDû�б����á�");
            }
            listProductInfo.setProductMasterID(routeListInfo1.getProductMasterID());
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            pservice.saveValueInfo(listProductInfo);
        }catch(Exception e)
        {
            this.setRollbackOnly();
            throw new QMException(e);
        }

        return routeListInfo1;
    }

    /**
     * ���¹���·�߱�ֻ���м򵥴洢��
     * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ���� @
     * @return TechnicsRouteListIfc ����·�߱�ֵ����
     * @throws QMException 
     */
    private TechnicsRouteListIfc updateRouteList(TechnicsRouteListIfc routeListInfo) throws QMException
    {
        TechnicsRouteListIfc routeListInfo1 = null;
        if(!PersistHelper.isPersistent(routeListInfo))
        {
            throw new QMException("routeService's storeRouteList : routeListInfoû�б��־û���");
        }
        //����ʱ���ܸ���Ψһ���ԣ�����Ҫ��SQLException��װ��
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        routeListInfo1 = (TechnicsRouteListIfc)pservice.updateValueInfo(routeListInfo);
        return routeListInfo1;
    }

    /**
     * ɾ������·�߱�������汾�ڵ�С�汾ȫ��ɾ����
     * @param routeListInfo TechnicsRouteListIfc ����·�߱�ֵ���� ����ָ����ֵ����ɾ��·�߱� @
     * @throws QMException 
     * @see TechnicsRouteListInfo ����·�߱�ֵ����
     */
    public void deleteRouteList(TechnicsRouteListIfc routeListInfo) throws QMException
    {
        ///////////////�а汾ɾ��������֧  2004.9.10 ������
        ///////////////���Ե��ò����źŵ�ɾ��,�Լ��������ɾ��. Ч�ʸ���.
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        deleteRouteListListener(routeListInfo);
        pservice.deleteValueInfo(routeListInfo);
        /*
         * //�ҵ�routeListID��Ӧ������С�汾�� VersionControlService vservice = (VersionControlService)EJBServiceHelper.getService(VERSION_SERVICE); Collection vec1 =
         * vservice.iteratiosaveRoutensOf(routeListInfo); if(VERBOSE) System.out.println(TIME+"deleteRouteListener ���е�С�汾��"+vec1); //ɾ���ڴ˰汾·�߱��н�����·�ߡ� //���ü���ɾ��·�ߡ���Ч�ʵͣ��˴��ݲ�����
         * /////////////������������ʱ����һ���ô������Իָ���ɾ��ǰ��״̬������״̬�Ƿ�ָ��� //����ɾ���� PersistService pservice = (PersistService)EJBServiceHelper.getPersistService(); Iterator iter1 = vec1.iterator();
         * if(iter1.hasNext()) pservice.deleteValueInfo((BaseValueIfc)iter1.next());
         */
    }

    /**
     * �༭�㲿����,ִ�����ڸ��¹���·�߱�ʱ���༭Ҫ���ƹ���·�ߵ��㲿������������㲿����ɾ���㲿���� ����㲿��ʱ�����Ը���·�߱��е�"���ڲ�Ʒ"���㲿�����������½ṹ���ṩ��ѡ�㲿�����û����Դ��� ѡ��Ҫ��ӵ�����·�߱��е��㲿��������û����ڱ༭���Ƕ���·�߱���ѡ�㲿������Ӧ�����ڲ�Ʒ
     * �Ľṹ����Ӧ���˲�Ʒ�����㲿���а�������λ·�ߵ�һ��·�߽��н�һ��ɸѡ���Ӷ����һ�ݱ�ѡ�㲿����
     * @param codeID String ·�߱�λ�����ݵ�λ���ƵĴ���ID��·�߼����ÿ�ѡ����㲿����
     * @param level String ·�߼�����ʾ��������һ��·�߻����·�� �����ǰ�༭�Ĺ���·�߱���һ������·�߱���ϵͳӦ�г���Ʒ�ṹ�е������㲿���� �����ǰ�༭�Ĺ���·�߱��Ƕ�������·�߱���ϵͳӦ����·�߱�ĵ�λ���ԣ��г���Ʒ�ṹ������һ��·���ж�Ӧ��λ���㲿������Ϊ��ѡ�㲿����
     * @param productMasterID String ���ڲ�ƷMasterID @
     * @return Collection ��ŵ���vector:<br> vector�а���ˢ�º��partMasterID <br> partMasterID�ǹ��˺��Ʒ�Ӽ���masterֵ����
     */
    public Collection getOptionalParts(String codeID, String level, String productMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getOptionalParts : level = " + level);
        }
        //�����һ��·�ߡ�
        Collection result = new Vector();
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMPartIfc productInfo = (QMPartIfc)getLatestVesion(productMasterID);
        if(level.equalsIgnoreCase(RouteListLevelType.FIRSTROUTE.getDisplay()))
        {
            //����Ӽ���
            Collection coll = getAllSubParts(productInfo);
            for(Iterator iter = coll.iterator();iter.hasNext();)
            {
                String partMasterID = ((QMPartIfc)iter.next()).getMaster().getBsoID();
                result.add(pservice.refreshInfo(partMasterID));
            }
        }else if(level.equalsIgnoreCase(RouteListLevelType.SENCONDROUTE.getDisplay()))
        {
            QMQuery query = new QMQuery(ROUTENODE_BSONAME, LIST_ROUTE_PART_LINKBSONAME);
            QueryCondition cond1 = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, codeID);
            query.addCondition(0, cond1);
            query.addAND();
            QueryCondition cond2 = new QueryCondition("routeID", "routeID");
            query.addCondition(0, 1, cond2);
            query.addAND();
            QueryCondition cond3 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
            query.addCondition(1, cond3);
            query.addAND();
            QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
            query.addCondition(1, cond4);
            query.setDisticnt(true);
            query.setVisiableResult(0);
            if(VERBOSE)
            {
                System.out.println(TIME + " RouteService getOptionalParts's SQL = " + query.getDebugSQL());
            }
            Collection coll = pservice.findValueInfo(query);
            //���й��ˣ���ø�����Ʒ���Ӽ���
            for(Iterator iter = coll.iterator();iter.hasNext();)
            {
                ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
                String partMasterID = listLinkInfo.getPartMasterID();
                //�����Ӽ����ˡ�
                boolean flag = isChildPart(productInfo, partMasterID);
                if(flag)
                {
                    result.add(pservice.refreshInfo(partMasterID));
                }
            }
        }else
        {
            throw new QMException("����·�ߵȼ�����ȷ");
        }
        if(VERBOSE)
        {
            System.out.println(TIME + " RouteService getOptionalParts's result = " + result);
        }
        return result;
    }

    /**
     * ͨ��·�߱��Ż��·�߱�ֵ����
     * @param routeListNum ·�߱���
     * @return ·�߱�ֵ����
     * @author ������
     */
    public TechnicsRouteListInfo findRouteListByNum(String routeListNum)
    {
        TechnicsRouteListInfo routeListInfo = null;
        Collection col = null;
        if(routeListNum == null || routeListNum.trim().equals(""))
        {
            return null;
        }
        try
        {
            QMQuery query = new QMQuery("consTechnicsRouteList");
            QueryCondition cond = new QueryCondition("routeListNumber", "=", routeListNum);
            query.addCondition(cond);
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

            col = pservice.findValueInfo(query);

            for(Iterator iter = col.iterator();iter.hasNext();)
            {
                routeListInfo = (TechnicsRouteListInfo)iter.next();
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return routeListInfo;

    }

    /**
     * �Ը������㲿�����м�飬�����Щ�㲿���Ƿ������ӵ�����·�ߵ��㲿���� �����ǰ�༭�Ĺ���·�߱��Ƕ�������·�߱���ϵͳӦ����·�߱�ĵ�λ���ԣ� �г���Ʒ�ṹ������һ��·���ж�Ӧ��λ���㲿������Ϊ��ѡ�㲿����
     * @param codeID String ����·�߱�ĵ�λ
     * @param productMasterID String ����·�߱����ڵĲ�Ʒ
     * @param subPartMasters Collection ����м����㲿�����ϣ�Ҫ��Ԫ��Ϊ�㲿������Ϣֵ���� @
     * @return Object[] �����а�������Ԫ��:<br>Object[0]:��һ��Ԫ���Ƿ����������㲿������Ϣ����;<br> Object[1]:�ڶ���Ԫ���ǲ������������㲿������Ϣ����
     */
    public Object[] checkSubParts(String codeID, String productMasterID, Collection subPartMasters)throws QMException
    {
        if(subPartMasters == null || subPartMasters.size() == 0)
        {
            return null;
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMPartIfc productInfo = (QMPartIfc)getLatestVesion(productMasterID);
        QMQuery query = new QMQuery(ROUTENODE_BSONAME, LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond1 = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, codeID);
        query.addCondition(0, cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("routeID", "routeID");
        query.addCondition(0, 1, cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(1, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(1, cond4);
        query.setDisticnt(true);
        query.setVisiableResult(0);
        if(VERBOSE)
        {
            System.out.println(TIME + " RouteService getOptionalParts's SQL = " + query.getDebugSQL());
        }
        Collection coll = pservice.findValueInfo(query);
        Vector masteridVector = new Vector();
        //���й��ˣ���ø�����Ʒ���Ӽ���
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            String partMasterID = listLinkInfo.getPartMasterID();
            //�����Ӽ����ˡ�
            boolean flag = isChildPart(productInfo, partMasterID);
            if(flag && !masteridVector.contains(partMasterID))
            {
                masteridVector.add(partMasterID);
            }
        } //���ˣ������ݿ����ҵ������з����������㲿��

        Vector v1 = new Vector(); //����װ�ط����������㲿��
        Vector v2 = new Vector(); //����װ�ز������������㲿��
        //������жԱȣ�
        if(masteridVector.size() > 0)
        {
            for(Iterator iter = subPartMasters.iterator();iter.hasNext();)
            {
                QMPartMasterIfc tempMaster = (QMPartMasterIfc)iter.next();
                if(masteridVector.contains(tempMaster.getBsoID()))
                {
                    v1.add(tempMaster);
                }else
                {
                    v2.add(tempMaster);
                }
            }
        }

        Object[] array = new Object[2];
        array[0] = v1;
        array[1] = v2;

        return array;

    }

    /**
     * ���ָ���㲿��������Ϣ������һ���㲿��������Ϣ���ļ���
     * @param partMasterInfo QMPartMasterIfc ָ���㲿��������Ϣ��ֵ���� @
     * @return Collection ��ŵ���HashMap:<br> key:partMasterBsoID <br>value:Master ��һ���㲿��������Ϣ��partInfo.getMaster()���ļ���
     * @see QMPartMasterInfo
     */
    public Collection getSubPartMasters(QMPartMasterIfc partMasterInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("����TechnicsRouteService: getSubPartMasters ������" + partMasterInfo.getBsoID());
        }
        HashMap map = new HashMap();
        QMPartIfc productInfo = (QMPartIfc)getLatestVesion(partMasterInfo);
        if(VERBOSE)
        {
            System.out.println("��Ʒ���°汾��" + productInfo.getBsoID());
        }
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        Collection c = partService.getSubParts(productInfo);
        if(VERBOSE)
        {
            System.out.println("Part�����������");
        }
        for(Iterator ite = c.iterator();ite.hasNext();)
        {
            QMPartIfc partInfo = (QMPartIfc)ite.next();
            String tempMasterID = partInfo.getMasterBsoID();
            if(!map.containsKey(tempMasterID))
            {
                QMPartMasterIfc tempMasterInfo = (QMPartMasterIfc)partInfo.getMaster();
                map.put(tempMasterID, tempMasterInfo);
            }
        }
        Vector reVector = new Vector();
        if(map.size() > 0)
        {
            reVector.addAll(map.values());
        }
        return reVector;
    }
  //CR27 begin
    //��ø�����Ʒ�������Ӽ�������������
    private Vector getAllSubParts(QMPartIfc productInfo)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        //EnterprisePartService enterprisePartService = (EnterprisePartService)EJBServiceHelper.getService("EnterprisePartService");
        Vector subs = new Vector();
        PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
        subs= partService.getProductStructure(productInfo,configSpecIfc);
        return subs;
    }
  //CR27 end
    //�жϸ���partMasterID�Ƿ��ǲ�Ʒ���Ӽ���
    private boolean isChildPart(QMPartIfc productInfo, String partMasterID)throws QMException
    {
        boolean flag = false;
        Collection result = getAllSubParts(productInfo);
        //�������Ƚϡ�
        for(Iterator iter = result.iterator();iter.hasNext();)
        {
            QMPartIfc partInfo = (QMPartIfc)iter.next();
            if(partInfo.getMaster().getBsoID().equals(partMasterID))
            {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * ������°汾��ֵ����
     * @param masterInfo MasterIfc Masterֵ���� @
     * @return BaseValueIfc
     * @see MasterInfo
     */
    public BaseValueIfc getLatestVesion(MasterIfc masterInfo)throws QMException
    {
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService(CONFIG_SERVICE);
        Collection coll1 = cservice.filteredIterationsOf(masterInfo, new LatestConfigSpec());
        Iterator iter1 = coll1.iterator();
        BaseValueIfc info = null;
        if(iter1.hasNext())
        {
            Object[] obj = (Object[])iter1.next();
            info = (BaseValueIfc)obj[0];
        }
        return info;
    }

    private BaseValueIfc getLatestVesion(String masterID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        MasterIfc masterInfo = (MasterIfc)pservice.refreshInfo(masterID);
        return getLatestVesion(masterInfo);
    }

    /**
     * ������°汾ֵ����ļ���
     * @param c Collection master���󼯺� @
     * @return Collection ���obj[]���飺<br>obj[0]:���°汾ֵ����
     */
    //(������)20060629 �����޸�
    public Collection getLatestsVersion(Collection c)throws QMException
    {
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService(CONFIG_SERVICE);
        Collection coll1 = cservice.filteredIterationsOf(c, new LatestConfigSpec());
        Iterator iter1 = coll1.iterator();
        BaseValueIfc info = null;
        Vector v = new Vector();
        while(iter1.hasNext())
        {
            Object[] obj = (Object[])iter1.next();
            info = (BaseValueIfc)obj[0];
            v.add(info);
        }
        return v;
    }

    /**
     * ������°汾ֵ����ļ���
     * @param c Collection master���󼯺� @
     * @return HashMap key:������ value�����°汾ֵ����
     */
    //   *add by guoxl on 20080307(�鿴һ��·�߱���ʱ������������)
    //   *������ӷ���getLatestsVersion1
    public HashMap getLatestsVersion1(Collection c)throws QMException
    {
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService(CONFIG_SERVICE);
        Collection coll1 = cservice.filteredIterationsOf(c, new LatestConfigSpec());
        Iterator iter1 = coll1.iterator();
        BaseValueIfc info = null;

        HashMap hash = new HashMap();

        while(iter1.hasNext())
        {
            Object[] obj = (Object[])iter1.next();
            info = (BaseValueIfc)obj[0];
            if(info instanceof QMPartIfc)
                hash.put(((QMPartIfc)info).getPartNumber(), info);
        }
        return hash;

    }

    //add end

    //begin CR14
    /**
     *������׼���������·����Ϣ�������ֱ�ΪҪ�������׼����������ϣ���׼����Ҫ�����·����Ϣ
     */
    public Object[] createLinksAndRoutes(TechnicsRouteListIfc routeListInfo, ArrayList createRouteCol)throws QMException
    {
        if(routeListInfo == null)
        {
            throw new QMException("TechnicsRouteList is NULL");
        }

        Object[] obj = new Object[2];
        TechnicsRouteListIfc routeList = this.storeRouteList(routeListInfo);
        ArrayList linklists = saveLinksAndRoutes(routeList, createRouteCol);
        obj[0] = routeList;
        obj[1] = linklists;
        return obj;

    }

    /**
     * ������׼���������·����Ϣ�� �����ֱ�ΪҪɾ���������Ϣ���ϣ���׼����Ҫ������Ҫ���µĹ�����Ϣ����
     */
    public Object[] updateLinksAndRoutes(HashMap deleteCollection, TechnicsRouteListIfc routeListInfo1, ArrayList allLinkCol)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's saveListRoutePartLink......................... saveCollection = " + ", deleteCollection = " + deleteCollection);
        }
        //����·�߱�
        TechnicsRouteListIfc routeListInfo = updateRouteList(routeListInfo1);
        if(VERBOSE)
        {
            if(routeListInfo1.getBsoID() == routeListInfo.getBsoID())
            {
                System.out.println("���º�����õĶ������ǰ�Ķ����bsoidһ��");
            }else
            {
                System.out.println("���º�����õĶ������ǰ�Ķ����bsoid��һ��");
            }
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        try
        {
            Object[] obj = new Object[2];
            //20111230 �촺Ӣ  add  ���¹�������·����Ϣ
            ArrayList list = this.updataAllLinkAndRoute(routeListInfo, allLinkCol);
            obj[0] = routeListInfo;
            obj[1] = list;
            //����Ҫɾ����������ϣ���ɾ��������ǡ�
            if(deleteCollection != null && deleteCollection.size() > 0)
            {
            for(Iterator iter = deleteCollection.keySet().iterator();iter.hasNext();)
            {
                Object[] tmp = (Object[])deleteCollection.get(iter.next());
               // System.out.println(tmp.length);
                String partMasterID = (String)tmp[0];
                QMPartMasterIfc productPart = null;
                String productID = null;
                QMPartIfc parentPart = null;
                String parentPartID = null;
                if(routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
                {
                    productPart = (QMPartMasterIfc)tmp[1];
                    if(productPart != null)
                    {
                        productID = productPart.getBsoID();
                    }

                        parentPart = (QMPartIfc)tmp[2];
                        if(parentPart != null)
                        {
                            //20120405
                            parentPartID = parentPart.getBsoID();
                            System.out.println("parentPartID===" + parentPartID);
                    }
                }
                //��ø�����Ϣ
                else if(routeMode.equals("parentRelative"))
                {
                    parentPart = (QMPartIfc)tmp[1];
                    if(parentPart != null)
                    {
                        parentPartID = parentPart.getBsoID();
                        System.out.println("parentPartID===" + parentPartID);
                    }
                }

                QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
                query.addCondition(cond);
                query.addAND();
                QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
                query.addCondition(cond1);

                query.addAND();
                QueryCondition condP;
                if(productID != null && !productID.trim().equals(""))
                {
                    condP = new QueryCondition("productID", QueryCondition.EQUAL, productID);
                }else
                {
                    condP = new QueryCondition("productID", QueryCondition.IS_NULL);
                }
                query.addCondition(condP);
                //20120108 xucy  add
                query.addAND();
                QueryCondition condParent;
                if(parentPartID != null && !parentPartID.trim().equals(""))
                {
                    condParent = new QueryCondition("parentPartID", QueryCondition.EQUAL, parentPartID);
                }else
                {
                    condParent = new QueryCondition("parentPartID", QueryCondition.IS_NULL);
                }
                query.addCondition(condParent);

                //����
                query.addOrderBy("createTime", false);
                if(VERBOSE)
                {
                    System.out.println(TIME + "....saveListRoutePartLink routeListInfo.bsoID = " + routeListInfo.getBsoID());
                    System.out.println(TIME + "saveListRoutePartLink partMasterID = " + partMasterID);
                    System.out.println(TIME + "saveListRoutePartLink productID = " + productID);
                    System.out.println(TIME + "routeService's saveListRoutePartLink SQL = " + query.getDebugSQL());
                }
                //
                Collection coll = pservice.findValueInfo(query);
                //if(VERBOSE)
                {
                    System.out.println(TIME + "routeSevice's saveListRoutePartLink ԭ�й������ϣ� coll = " + coll);
                    //��Ϊ�п�������������������2
                }
                if(coll.size() > 2)
                {
                    if(VERBOSE)
                    {
                        System.out.println(TIME + coll);
                    }
                    throw new QMException("saveListRoutePartLink's deleteCollection�����ܶ�������������������ѡ�������");
                }
                //��ɾ��������ǻ�ɾ��������
                Iterator iter1 = coll.iterator();
                if(iter1.hasNext())
                {
                    ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter1.next();
                    if(listLinkInfo.getAlterStatus() == INHERIT)
                    {
                        //skybird��δ�����ȫ������
                        //������ǰ�İ汾Ϊȡ��״̬��
                        QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                        QueryCondition cond2 = new QueryCondition("routeListMasterID", QueryCondition.EQUAL, routeListInfo.getMaster().getBsoID());
                        query1.addCondition(cond2);
                        query1.addAND();
                        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
                        query1.addCondition(cond3);
                        query1.addAND();
                        QueryCondition cond4 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
                        query1.addCondition(cond4);
                        //////////////////�ų��Լ�, �����п������ʱ������� 2004.9.11 ������
                        query1.addAND();
                        QueryCondition cond5 = new QueryCondition(LEFTID, QueryCondition.NOT_EQUAL, routeListInfo.getBsoID());
                        query1.addCondition(cond5);
                        query1.addAND();
                        ////////////////////////    Ӧ��֤��ͬһ����֧��        2004.9.11 ������  versionID=A ����A.1 A.2
                        QueryCondition cond6 = new QueryCondition("initialUsed", QueryCondition.EQUAL, routeListInfo.getVersionID());
                        query1.addCondition(cond6);
                        //bengin,mended by skybird,2005,1,19,
                        query1.addAND();
                        QueryCondition cond7 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
                        query1.addCondition(cond7);
                        //end
                        //gcy add  2005/05/19
                        //20120108 xucy  add
                        query1.addAND();
                        QueryCondition condPn;
                        if(productID != null && !productID.trim().equals(""))
                        {
                            condPn = new QueryCondition("productID", QueryCondition.EQUAL, productID);
                        }else
                        {
                            condPn = new QueryCondition("productID", QueryCondition.IS_NULL);
                        }
                        query1.addCondition(condPn);
                        //gcy add  2005/05/19 end
                        query1.addAND();
                        QueryCondition condParent1;
                        if(parentPartID != null && !parentPartID.trim().equals(""))
                        {
                            condParent1 = new QueryCondition("parentPartID", QueryCondition.EQUAL, parentPartID);
                        }else
                        {
                            condParent1 = new QueryCondition("parentPartID", QueryCondition.IS_NULL);
                        }
                        query1.addCondition(condParent1);

                        if(VERBOSE)
                        {
                            System.out.println(TIME + "routeSevice's saveListRoutePartLink INHERIT SQL = " + query1.getDebugSQL());
                        }
                        Collection coll1 = pservice.findValueInfo(query1);
                        if(VERBOSE)
                        {
                            System.out.println(TIME + "routeSevice's saveListRoutePartLink INHERIT coll = " + coll1);
                        }
                        if(coll1.size() > 1)
                        {
                            throw new QMException("saveListRoutePartLink����������������״̬");
                        }
                        Iterator iter2 = coll1.iterator();
                        if(iter2.hasNext())
                        {
                            ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkIfc)iter2.next();
                            if(VERBOSE)
                            {
                                System.out.println(TIME + "saveListRoutePartLink deleteCollection INHERIT: " + coll + " , linkInfo.BsoID = " + listLinkInfo1.getBsoID());
                            }
                            listLinkInfo1.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
                            pservice.saveValueInfo(listLinkInfo1);
                        }
                    }else if(listLinkInfo.getAlterStatus() == ROUTEALTER)
                    {
                        if(VERBOSE)
                        {
                            System.out.println(TIME + "saveListRoutePartLink deleteCollection ROUTEALTER: linkBsoID = " + listLinkInfo.getBsoID());
                        }
                        if(listLinkInfo.getRouteID() != null)
                        {
                            //skybird�о����˳�򲻶�
                            deleteRoute(listLinkInfo);
                            listLinkInfo.setRouteID(null);
                        }
                    }else
                    {
                        throw new QMException("saveListRoutePartLink������ɾ��״̬�������Ӧ���֡�");
                    }
                    //û���½�����������ɾ����ǡ�
                    if(coll.size() == 1)
                    {
                        if(VERBOSE)
                        {
                            System.out.println(TIME + "if(coll.size() == 1) û���½�����������ɾ����ǡ�");
                        }
                        Collection preLinks = this.searchPreLink(routeListInfo, partMasterID, productID, parentPartID);
                        if(preLinks != null && preLinks.size() > 0)
                        {
                            if(VERBOSE)
                            {
                                System.out.println(TIME + "��ǰ�汾���д˹�������������Ϊ�Ѿ�ɾ��״̬");
                            }
                            listLinkInfo.setAlterStatus(PARTDELETE);
                            //��ɾ�����Ƿ����״̬����Ϊȡ����
                            listLinkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
                            pservice.saveValueInfo(listLinkInfo);
                        }else
                        {
                            if(VERBOSE)
                            {
                                System.out.println(TIME + "��ǰ�汾��û�д˹���������ɾ���ι���");
                            }
                            pservice.deleteValueInfo(listLinkInfo);
                        }
                    }
                    //�����½�������//ɾ���˹�����
                    else if(coll.size() == 2)
                    {
                        if(VERBOSE)
                        {
                            System.out.println(TIME + "else if(coll.size() == 2) �����½�������//ɾ���˹�����");
                        }
                        pservice.deleteValueInfo(listLinkInfo);
                    }
                }
            }
            }
            return obj;
        }catch(Exception e)
        {
        	 e.printStackTrace();
            this.setRollbackOnly();
            throw new QMException(e);
        }
        //Collection result = getRouteListLinkParts(routeListInfo.getBsoID());
        //return result;

    }

    /**
     * ���洴������׼���������·����Ϣ���������͸�����׼ʱ����
     * @param saveCollection
     * @param routeListInfo
     * @param createRouteCol
     */
    private ArrayList saveLinksAndRoutes(TechnicsRouteListIfc routeListInfo, ArrayList createRouteCol)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        try
        {
            //Set  set =  saveCollection.keySet();
            //���Ҹ���׼�����й���
            QMQuery myQuery = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
            QueryCondition linkCond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
            myQuery.addCondition(linkCond);
            Collection linkColl = pservice.findValueInfo(myQuery);
            ArrayList list = new ArrayList();
            //��ʼ���������·��
            for(int i = 0, j = createRouteCol.size();i < j;i++)
            {
                //�����������ID����Ʒ��Ϣ�򸸼���Ϣ
                RouteWrapData routeData = (RouteWrapData)createRouteCol.get(i);
                ListRoutePartLinkIfc listLinkInfo = createLinkAndRoute(routeListInfo, linkColl, routeData);
                list.add(listLinkInfo);
            }
            return list;
        }catch(Exception e)
        {
            e.printStackTrace();
            this.setRollbackOnly();
            throw new QMException(e);
        }
    }

    /**
     * ����������·�߶���
     */
    private ListRoutePartLinkIfc createLinkAndRoute(TechnicsRouteListIfc routeListInfo, Collection linkColl, RouteWrapData routeData)throws QMException
    {
        if(routeData == null)
        {
            throw new QMException("RouteWrapData is null");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        String partMasterID = routeData.getPartMasterID();
        String productID = null;
        //20120108 xucy add
        String parentPartID = null;
        //�Ͳ�Ʒ�й�ʱ��ò�Ʒ��Ϣ
        //                if(routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
        //                {
        QMPartMasterIfc productMaster = routeData.getProduct();
        if(productMaster != null)
        {
            productID = productMaster.getBsoID();
        }
        //                }
        //                //��ø�����Ϣ
        //                else if(routeMode.equals("parentRelative"))
        //                {
        QMPartIfc parentPart = (QMPartIfc)routeData.getParent();
        if(parentPart != null)
        {
            //20120405
            parentPartID = parentPart.getBsoID();
        }
        //}
        //Begin CR3
        if(linkColl != null && linkColl.size() != 0)
        {
            //End CR3
            //�����Ƿ���ɾ��״̬�Ĺ�����ɾ����
            QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
            QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
            query.addCondition(cond);
            query.addAND();
            QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
            query.addCondition(cond1);
            query.addAND();
            QueryCondition cond2 = new QueryCondition("alterStatus", QueryCondition.EQUAL, PARTDELETE);
            query.addCondition(cond2);
            if(routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
            {
                query.addAND();
                QueryCondition condP2 = new QueryCondition("productID", QueryCondition.NOT_NULL);
                query.addCondition(condP2);
                query.addAND();
                QueryCondition condP1 = new QueryCondition("productID", QueryCondition.EQUAL, productID);
                query.addCondition(condP1);
            }else
            {
                query.addAND();
                QueryCondition condP;
                if(productID != null && !productID.trim().equals(""))
                {
                    condP = new QueryCondition("productID", QueryCondition.EQUAL, productID);
                }else
                {
                    condP = new QueryCondition("productID", QueryCondition.IS_NULL);
                }
                query.addCondition(condP);
            }
            //20120108 xucy  add
            query.addAND();
            QueryCondition condParent;
            if(parentPartID != null && !parentPartID.trim().equals(""))
            {
                condParent = new QueryCondition("parentPartID", QueryCondition.EQUAL, parentPartID);
            }else
            {
                condParent = new QueryCondition("parentPartID", QueryCondition.IS_NULL);
            }
            query.addCondition(condParent);

            if(VERBOSE)
            {
                System.out.println(TIME + "saveListRoutePartLink'saveCollection routeListInfo.bsoID = " + routeListInfo.getBsoID());
                System.out.println(TIME + "saveListRoutePartLink'saveCollection partMasterID = " + partMasterID);
                System.out.println(TIME + "routeService's saveListRoutePartLink'saveCollection SQL = " + query.getDebugSQL());
            }
            Collection coll = pservice.findValueInfo(query);
            if(coll.size() > 1)
            {
                if(VERBOSE)
                {
                    System.out.println(TIME + coll);
                }
                throw new QMException("saveListRoutePartLink'saveCollection��ͬһ�������һ��·�߱�汾�в������������������������ù�����");
            }
            Iterator iter1 = coll.iterator();
            if(iter1.hasNext())
            {
                ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter1.next();
                //ɾ��������
                pservice.deleteValueInfo(listLinkInfo);
            }
        }
        ListRoutePartLinkInfo listLinkInfo = ListRoutePartLinkInfo.newListRoutePartLinkInfo(routeListInfo, partMasterID);

        //st skybird 2005.3.4 ���游����
        //  QMPartIfc partifc = (QMPartIfc) tmp[1];
        //�Ͳ�Ʒ�й�ʱ����������Ϣ
        if(routeMode.equals("partRelative")||routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
        {
            listLinkInfo.setProductCount(routeData.getProductCount());
        }
        //xucy 20111229 add  ������Ӽ���·��
        //boolean productIdenty = false;

        //ed
        //20120108 xucy add
        //pservice.storeValueInfo(listLinkInfo);
        //20111230xucy add
        //��ʼ����·�ߺ�·�߷�֧��Ϣ
        //                if(createRouteCol != null && createRouteCol.size() > 0)
        //                {
        //                    //���һ�������·����Ϣ
        //                    RouteWrapData routeWrap = (RouteWrapData)createRouteCol.get(partMasterID);

        //����Ǵ������������ʼ�����������·����Ϣ
      //CCBegin SS22
//        listLinkInfo.setModifyIdenty(routeData.getModifyIdenty());
//        listLinkInfo.setMainStr(RouteHelper.getRouteBranchStr(routeData.getMainStr()));
//        listLinkInfo.setSecondStr(RouteHelper.getRouteBranchStr(routeData.getSecondStr()));
//        listLinkInfo.setRouteDescription(routeData.getDescription());
//        //CCBegin SS9
//        listLinkInfo.setStockID(routeData.getStockID());
//        //CCEnd SS9
      
        String comp=RouteClientUtil.getUserFromCompany();
        if(routeListInfo.getRouteListState().equals("�ձ�") && comp.equals("zczx")){
        	Collection coll = selectPartRoute(listLinkInfo.getRightBsoID());
        	Iterator iter1 = coll.iterator();
        	if(iter1.hasNext())
        	{
        		ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkIfc)iter1.next();
        		listLinkInfo.setModifyIdenty(listLinkInfo1.getModifyIdenty());
        		listLinkInfo.setMainStr(listLinkInfo1.getMainStr());
        		listLinkInfo.setSecondStr(listLinkInfo1.getSecondStr());
        		//CCBegin SS9
                listLinkInfo.setStockID(listLinkInfo1.getStockID());
                //CCEnd SS9
        	}
        }
        else{
        	listLinkInfo.setModifyIdenty(routeData.getModifyIdenty());
        	listLinkInfo.setMainStr(RouteHelper.getRouteBranchStr(routeData.getMainStr()));
        	listLinkInfo.setSecondStr(RouteHelper.getRouteBranchStr(routeData.getSecondStr()));
        	//CCBegin SS9
            listLinkInfo.setStockID(routeData.getStockID());
            //CCEnd SS9
        }
        listLinkInfo.setRouteDescription(routeData.getDescription());
      //CCEnd SS22
        
        //CCBegin SS11
        listLinkInfo.setPartVersion(routeData.getPartVersion());
        //CCEnd SS11
        //CCBegin SS12
        //CCBegin SS25
        listLinkInfo.setSupplier(routeData.getSupplier());
        listLinkInfo.setSupplierBsoId(routeData.getSupplierBsoId());
        //CCEnd SS25
        //CCBegin SS50
        listLinkInfo.setColorFlag(routeData.getColorFlag());
        //CCEnd SS50
        //CCBegin SS54
        listLinkInfo.setSpecialFlag(routeData.getSpecialFlag());
        //CCEnd SS54
      //CCBegin SS22
//        String comp="";
      //CCEnd SS22
        try {
			 comp=RouteClientUtil.getUserFromCompany();
		} catch (QMException e) {
			e.printStackTrace();
		}
        if(comp.equals("zczx")){
        	listLinkInfo.setInitialUsed("");
        }
        //CCEnd SS12
        //CCBegin SS40
        if(comp.equals("cd"))
        {
        	listLinkInfo.setDwbs("W34");
        }
        //CCEnd SS40
        listLinkInfo.setReleaseIdenty(-1);
        //                if(productMaster != null && (routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative")))
        //                {
        listLinkInfo.setProductIdenty(routeData.getProductIndenty());
        if(routeData.getProductIndenty())
        {
            listLinkInfo.setProductID(productID);
        }
        //}
        //�Ͳ�Ʒ�й�ʱ���ò�Ʒ��Ϣ
        if(parentPart != null && ((routeMode.equals("parentRelative"))||(routeMode.equals("productAndparentRelative"))))
        {
            listLinkInfo.setParentPartID(parentPartID);
            listLinkInfo.setParentPartNum(routeData.getParentNum());
            listLinkInfo.setParentPartName(routeData.getParentName());
        }
        //20120108 xucy add �������
        pservice.storeValueInfo(listLinkInfo);
        //��Ҫ·����Ϣ�����򱣴�·����ض���
        if(routeData.getMainStr() != null && !routeData.getMainStr().equals(""))
        {
            this.saveRoute(listLinkInfo, routeData.getRouteMap());
        }
        return listLinkInfo;
    }
    //CR28 begin
    /**
     * ������µ���׼���������·����Ϣ xucy 20111230 add
     */
    private ArrayList updataAllLinkAndRoute(TechnicsRouteListIfc routeListInfo, ArrayList updateLinkCol)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //Object[] obj = new Object[2];
        ArrayList list = new ArrayList();
        for(int i = 0, j = updateLinkCol.size();i < j;i++)
        {
            //���Ҫ���µ������·����Ϣ
            RouteWrapData wrapData = (RouteWrapData)updateLinkCol.get(i);
            if(wrapData == null)
            {
                throw new QMException("RouteWrapData is null");
            }
            String linkID = wrapData.getLinkID();
            //���ڹ�������Ҫ���µĶ���
            if(linkID != null && !linkID.equals(""))
            {
                ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)pservice.refreshInfo(linkID);
                //������ԭ����·�ߣ��������������·����Ϣ
                if(linkInfo.getRouteID() != null && !linkInfo.getRouteID().trim().equals(""))
                {
                    //20120120 xucy add
                    boolean isChangeMain = RouteHelper.compareRouteStr(wrapData.getMainStr(), linkInfo.getMainStr());
                    boolean isChangeSecond = RouteHelper.compareRouteStr(wrapData.getSecondStr(), linkInfo.getSecondStr());
                    if(routeListInfo.getOwner() != null && routeListInfo.getWorkableState().equals("wrk"))
                    {
                        TechnicsRouteListIfc original = (TechnicsRouteListIfc)pservice.refreshInfo(routeListInfo.getPredecessorID());
                        //���ݹ����е������ԭ��·�߱����ԭ��·�߱������޹��������ID�������ͬ˵�����õ�ԭ��·�ߣ����ý�����Ϣ�½�·�ߣ�Ȼ�����赽�����С�
                        ListRoutePartLinkIfc origLink = findOriginalListPartLink(original, linkInfo.getPartMasterID());
                        if(origLink.getRouteID() != null && linkInfo.getRouteID().equals(origLink.getRouteID()))
                        {
                            if(!isChangeMain || !isChangeSecond)
                            {
                                if(wrapData.getRouteMap() != null && wrapData.getRouteMap().size() > 0)
                                {
                                    TechnicsRouteIfc routeinfo = this.saveRoute(null, wrapData.getRouteMap());
                                    System.out.println("qqq"+routeinfo.getBsoID());
                                    linkInfo.setRouteID(routeinfo.getBsoID());
                                }
                            }
                        }
                        else
                        {
                            //�����Ҫ·�ߴ����б��ޣ���ɾ��·�������Ϣ�����������·����
                            if(wrapData.getMainStr() == null || wrapData.getMainStr().equals(""))
                            {
                                //ɾ���ڵ�ͷ�֧
                                deleteContainedObjects(linkInfo.getRouteID());
                                //pservice.deleteValueInfo(linkInfo.getRouteID());
                                //20120113 xucy ɾ��·��
                                deleteRoute(linkInfo);
                                linkInfo.setRouteID("");
                            }
                            //�����Ҫ·�ߴ����ڲ��ҷ����仯�˻��Ҫ·�߷����˱仯����ɾ��ԭ·����Ϣ��������·����Ϣ
                            else if(!isChangeMain || !isChangeSecond)
                            {
                                deleteContainedObjects(linkInfo.getRouteID());
                                if(wrapData.getRouteMap() != null && wrapData.getRouteMap().size() > 0)
                                {
                                    this.saveRoute(linkInfo, wrapData.getRouteMap());
                                }
                            }
                        }
                    }
                    else
                    {
                    //�����Ҫ·�ߴ����б��ޣ���ɾ��·�������Ϣ�����������·����
                    if(wrapData.getMainStr() == null || wrapData.getMainStr().equals(""))
                    {
                        //ɾ���ڵ�ͷ�֧
                        deleteContainedObjects(linkInfo.getRouteID());
                        //pservice.deleteValueInfo(linkInfo.getRouteID());
                        //20120113 xucy ɾ��·��
                        deleteRoute(linkInfo);
                        linkInfo.setRouteID("");
                    }
                    //�����Ҫ·�ߴ����ڲ��ҷ����仯�˻��Ҫ·�߷����˱仯����ɾ��ԭ·����Ϣ��������·����Ϣ
                    else if(!isChangeMain || !isChangeSecond)
                    {
                        deleteContainedObjects(linkInfo.getRouteID());
                        if(wrapData.getRouteMap() != null && wrapData.getRouteMap().size() > 0)
                        {
                            this.saveRoute(linkInfo, wrapData.getRouteMap());
                        }
                    }
                    }
                }
                //���ԭ��û��·�ߣ��򴴽�·����Ϣ
                else
                {
                    if(wrapData.getRouteMap() != null && wrapData.getRouteMap().size() > 0)
                    {
                        //                    linkInfo.setMainStr((String)obj[2]);
                        //                    linkInfo.setSecondStr((String)obj[3]);
                        if(wrapData.getMainStr() != null && !wrapData.getMainStr().equals(""))
                        {
                            this.saveRoute(linkInfo, wrapData.getRouteMap());
                        }
                    }
                }
                //20120113 xucy modify
                linkInfo.setModifyIdenty(wrapData.getModifyIdenty());
                linkInfo.setRouteDescription(wrapData.getDescription());
                linkInfo.setMainStr(RouteHelper.getRouteBranchStr(wrapData.getMainStr()));
                linkInfo.setSecondStr(RouteHelper.getRouteBranchStr(wrapData.getSecondStr()));
                linkInfo.setProductIdenty(wrapData.getProductIndenty());
                linkInfo.setParentPartNum(wrapData.getParentNum());
                linkInfo.setParentPartName(wrapData.getParentName());
                linkInfo.setProductCount(wrapData.getProductCount());
//              CCBegin SS22
//                //CCBegin SS9
                //CCBegin SS34
                linkInfo.setStockID(wrapData.getStockID());
//                //CCEnd SS9
              //String comp=RouteClientUtil.getUserFromCompany();
              //if(!routeListInfo.getRouteListState().equals("�ձ�") && !comp.equals("zczx")){
              	//CCBegin SS9
              	//linkInfo.setStockID(wrapData.getStockID());
              	//CCEnd SS9
              	
              //}
              	//CCEnd SS34
//            CCEnd SS22
                //CCBegin SS25
                linkInfo.setSupplier(wrapData.getSupplier());
                linkInfo.setSupplierBsoId(wrapData.getSupplierBsoId());
                //CCEnd SS25
                //CCBegin SS50
                linkInfo.setColorFlag(wrapData.getColorFlag());
                //CCEnd SS50
                //CCBegin SS54
                linkInfo.setSpecialFlag(wrapData.getSpecialFlag());
                //CCEnd SS54
                if(wrapData.getParent()!=null)
                {
                linkInfo.setParentPartID(wrapData.getParent().getBsoID());
                }
                if(wrapData.getProduct()!=null)
                {
                linkInfo.setProductID(wrapData.getProduct().getBsoID());
                }
                //CR29 begin
                else
                {
                    linkInfo.setProductID(null);
                }
                //CR29 end
                pservice.updateValueInfo(linkInfo);
                list.add(linkInfo);
            }
            //�޹�������Ҫ�����Ķ���
            else
            {
                //���Ҹ���׼�����й���
                QMQuery myQuery = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                QueryCondition linkCond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
                myQuery.addCondition(linkCond);
                Collection linkColl = pservice.findValueInfo(myQuery);
                ListRoutePartLinkIfc listLinkInfo = createLinkAndRoute(routeListInfo, linkColl, wrapData);
                list.add(listLinkInfo);
            }

        }
        return list;
    }
  //CR28 end
    //end CR14

    //xucy 20111227
    private Collection searchPreLink(TechnicsRouteListIfc routeListInfo, String partMasterID, String productID, String parentID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond2 = new QueryCondition("routeListMasterID", QueryCondition.EQUAL, routeListInfo.getMaster().getBsoID());
        query1.addCondition(cond2);
        query1.addAND();
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query1.addCondition(cond3);
        //////////////////�ų��Լ�
        query1.addAND();
        QueryCondition cond5 = new QueryCondition(LEFTID, QueryCondition.NOT_EQUAL, routeListInfo.getBsoID());
        query1.addCondition(cond5);
        query1.addAND();
        ////////////////////////    Ӧ��֤��ͬһ����֧��    versionID=A ����A.1 A.2
        QueryCondition cond6 = new QueryCondition("initialUsed", QueryCondition.EQUAL, routeListInfo.getVersionID());
        query1.addCondition(cond6);
        query1.addAND();
        QueryCondition condPn;
        if(parentID != null && !parentID.trim().equals(""))
        {
            condPn = new QueryCondition("productID", QueryCondition.EQUAL, productID);
        }else
        {
            condPn = new QueryCondition("productID", QueryCondition.IS_NULL);
        }
        query1.addCondition(condPn);
        query1.addAND();
        QueryCondition condPn1;
        if(parentID != null && !parentID.trim().equals(""))
        {
            condPn1 = new QueryCondition("parentPartID", QueryCondition.EQUAL, parentID);
        }else
        {
            condPn1 = new QueryCondition("parentPartID", QueryCondition.IS_NULL);
        }
        query1.addCondition(condPn1);
        if(VERBOSE)
        {
            System.out.println(TIME + "routeSevice's saveListRoutePartLink INHERIT SQL = " + query1.getDebugSQL());
        }
        Collection coll1 = pservice.findValueInfo(query1);
        return coll1;

    }

    /**
     * ���¸��������
     * @param PartsToChange Collection Ҫ���µĸ�������
     * @param routeListInfo1 TechnicsRouteListIfc ·�߱�ֵ���� @
     * @see TechnicsRouteListInfo
     */
    // * ������gcy 05.04.29��
    // * added by skybird 2005.3.4
    public void updateListRoutePartLink(Collection PartsToChange, TechnicsRouteListIfc routeListInfo1)throws QMException
    {
        TechnicsRouteListIfc routeListInfo = updateRouteList(routeListInfo1);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        try
        {
            for(Iterator iter = PartsToChange.iterator();iter.hasNext();)
            {
                Object[] tmp = (Object[])(iter.next());
                String partMasterID = (String)tmp[0];
                QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
                query.addCondition(cond);
                query.addAND();
                QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
                query.addCondition(cond1);
                Collection coll = pservice.findValueInfo(query);
                Iterator iter1 = coll.iterator();
                if(iter1.hasNext())
                {
                    ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter1.next();
                    listLinkInfo.setParentPartNum((String)tmp[1]);
                    pservice.updateValueInfo(listLinkInfo);
                }
            }
        }catch(Exception e)
        {
            this.setRollbackOnly();
            throw new QMException(e);
        }

    }

    /**
     * ����·�߱�(������)
     * @param param Object[][] ��ά���飬5��Ԫ�ء�����obj[0]=��ţ�obj[1]=true. true=�ǣ� false=�ǡ�����˳�򣺱�š����ơ����𣨺��֣������ڲ�Ʒ���汾�š� @
     * @return Collection ���obj[]:����·�߱���������routelistֵ����<br> ��ʱҪ��ConfigService���й��ˡ�
     */

    public Collection findRouteLists(Object[][] param)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTELIST_BSONAME);
        int i= query.appendBso(ROUTELISTMASTER_BSONAME, false);
        query.addCondition(0, i,  new QueryCondition("masterBsoID", "bsoID"));
        query.addAND();
        //zz ���ò�ѯʱ�Ƿ���Դ�Сд
        boolean ignore = ((Boolean)param[5][0]).booleanValue();
        query.setIgnoreCase(ignore);
        int n = query.appendBso(PARTMASTER_BSONAME, false);
        query.setChildQuery(false);
        String number = (String)param[0][0];
        boolean numberFlag = ((Boolean)param[0][1]).booleanValue();
        if(number != null && number.trim().length() != 0)
        {
            QueryCondition cond = RouteHelper.handleWildcard("routeListNumber", number, numberFlag);
            query.addCondition(i, cond);
            query.addAND();
        }
        String name = (String)param[1][0];
        boolean nameFlag = ((Boolean)param[1][1]).booleanValue();
        if(name != null && name.trim().length() != 0)
        {
            QueryCondition cond1 = RouteHelper.handleWildcard("routeListName", name, nameFlag);
            query.addCondition(i, cond1);
            query.addAND();
        }
        String level_zh = (String)param[2][0];
        if(level_zh != null && level_zh.trim().length() != 0)
        {
            String level = RouteHelper.getValue(RouteListLevelType.getRouteListLevelTypeSet(), level_zh);
            QueryCondition cond4 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, level);
            query.addCondition(cond4);
            query.addAND();
        }
        String productNumber = (String)param[3][0];
        boolean productNumberFlag = ((Boolean)param[3][1]).booleanValue();
        if(productNumber != null && productNumber.trim().length() != 0)
        {
            //�����Ʒ���������󣬽�����ʾ���Ƿ���Ҫ��
            hasPartNumber(productNumber);
            QueryCondition cond5 = RouteHelper.handleWildcard("partNumber", productNumber, productNumberFlag);
            query.addCondition(n, cond5);
            query.addAND();
            QueryCondition cond100 = new QueryCondition("productMasterID", "bsoID");
            query.addCondition(0, n, cond100);
            query.addAND();
        }
        String version = (String)param[4][0];
        boolean versionFlag = ((Boolean)param[4][1]).booleanValue();
        //����汾ǡ��Ϊ���°棬�����ѳ��������ϼеĶ�����
        if(version != null && version.trim().length() != 0)
        {
            QueryCondition cond6 = RouteHelper.handleWildcard("versionID", version, versionFlag);
            query.addCondition(0, cond6);
            query.addAND();
        }
        QueryCondition cond12 = new QueryCondition("iterationIfLatest", Boolean.TRUE);
        query.addCondition(cond12);
//        if(VERBOSE)
//        {
//            System.out.println(TIME + "routeService's findRouteLists else... clause, SQL = " + query.getDebugSQL());
//        }
        query.setDisticnt(true);
//        query.setIsSeries(true);  guo delete
        //addListOrderBy(query);
        //routelistֵ���󼯺ϡ�
        System.out.println("sql���====="+query.getDebugSQL());
        Collection result = pservice.findValueInfo(query);
        //���ݹ���״̬���й��ˡ�ԭ����������ͬʱ���ڡ���ù���·�߱�ֵ���󼯺ϡ�
        filterByWorkState(result);
        //����·�߱����������С�
        Collection sortedVec = RouteHelper.sortedInfos(result, "getRouteListNumber", false);
        return sortedVec;
    }

    //�����Ʒ���������󣬽�����ʾ���Ƿ���Ҫ��
    private void hasPartNumber(String productNumber)throws QMException
    {
        Collection productMasterInfo = getProductMasterID(productNumber);
        if(productMasterInfo.size() == 0)
        {
            Object[] obj = new Object[]{productNumber};
            throw new QMException("com.faw_qm.technics.consroute.util.RouteResource", "5", obj);
        }
        if(VERBOSE)
        {
            System.out.println(TIME + "findRouteLists's productNumber = " + productNumber + ", productMasterInfo = " + productMasterInfo);
        }
    }

    //��������Ż�����masterID.
    private Collection getProductMasterID(String productNumber)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(PARTMASTER_BSONAME);
        //GenericPartMaster��������
        query.setChildQuery(false);
        QueryCondition cond = RouteHelper.handleWildcard("partNumber", productNumber);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    /**
     * ���ݹ���״̬���й��ˡ�ԭ����������ͬʱ���ڡ���ù���·�߱�ֵ���󼯺ϡ�
     * @param total Collection
     */
    private void filterByWorkState(Collection total)
    {
        Object[] obj = total.toArray();
        for(int i = 0;i < obj.length;i++)
        {
            TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)obj[i];
            if(listInfo.getWorkableState().trim().equals(CompareHandler.WRK))
            {
                String decessorID = listInfo.getPredecessorID();
                //ȥ��ԭ��
                for(Iterator iter = total.iterator();iter.hasNext();)
                {
                    TechnicsRouteListIfc originalInfo = (TechnicsRouteListIfc)iter.next();
                    if(decessorID.equals(originalInfo.getBsoID()))
                    {
                        total.remove(originalInfo);
                        break;
                    }
                }
            }
        }
    }

    private void addListOrderBy(QMQuery query)throws QMException
    {
        query.addOrderBy(0, "routeListNumber", false);
    }

    /**
     * ��������·�߱�������Χ����š����ơ�״̬�����𣨺��֣������ڲ�Ʒ��˵�������λ�á� �������ڡ������ߡ������¡��汾�š�
     * @param routelistInfo TechnicsRouteListIfc ·�߱�ֵ����
     * @param productNumber String ���ڲ�Ʒ���
     * @param createTime String ����ʱ��
     * @param modifyTime String �޸�ʱ�� @
     * @return Collection ���obj[]:����·�߱��������Ĺ���·�߱�ֵ�������������°汾��<br>
     * @see TechnicsRouteListInfo
     */
    public Collection findRouteLists(TechnicsRouteListIfc routelistInfo, String productNumber, String createTime, String modifyTime)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's findRouteLists createTime = " + createTime + ", modifyTime = " + modifyTime);
        }
        if(routelistInfo == null)
        {
            throw new QMException("routelistInfo����Ϊ�ա�");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTELIST_BSONAME);
        int m = query.appendBso(new UserInfo().getBsoName(), false);
        int n = query.appendBso(PARTMASTER_BSONAME, false);
        query.setChildQuery(false);
        if(routelistInfo.getRouteListNumber() != null && routelistInfo.getRouteListNumber().trim().length() != 0)
        {
            QueryCondition cond1 = RouteHelper.handleWildcard("routeListNumber", routelistInfo.getRouteListNumber());
            query.addCondition(0, cond1);
            query.addAND();
        }
        if(routelistInfo.getRouteListName() != null && routelistInfo.getRouteListName().trim().length() != 0)
        {
            QueryCondition cond2 = RouteHelper.handleWildcard("routeListName", routelistInfo.getRouteListName());
            query.addCondition(0, cond2);
            query.addAND();
        }
        if(routelistInfo.getLifeCycleState() != null && routelistInfo.getLifeCycleState().toString() != null && routelistInfo.getLifeCycleState().toString().trim().length() != 0)
        {
            QueryCondition cond3 = new QueryCondition("lifeCycleState", QueryCondition.EQUAL, routelistInfo.getLifeCycleState().toString());
            query.addCondition(0, cond3);
            query.addAND();
        }
        String level_zh = routelistInfo.getRouteListLevel();
        if(level_zh != null && level_zh.trim().length() != 0)
        {
            String level = RouteHelper.getValue(RouteListLevelType.getRouteListLevelTypeSet(), level_zh);
            QueryCondition cond4 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, level);
            query.addCondition(0, cond4);
            query.addAND();
        }
        if(routelistInfo.getRouteListDescription() != null && routelistInfo.getRouteListDescription().trim().length() != 0)
        {
            QueryCondition cond5 = RouteHelper.handleWildcard("routeListDescription", routelistInfo.getRouteListDescription());
            query.addCondition(0, cond5);
            query.addAND();
        }
        if(routelistInfo.getLocation() != null && routelistInfo.getLocation().trim().length() != 0)
        {
            QueryCondition cond6 = new QueryCondition("location", QueryCondition.EQUAL, routelistInfo.getLocation());
            query.addCondition(0, cond6);
            query.addAND();
        }
        if(createTime != null && createTime.trim().length() != 0)
        {
            RouteHelper.handleTimeQuery(query, createTime, "createTime");
        }
        if(routelistInfo.getIterationCreator() != null && routelistInfo.getIterationCreator().trim().length() != 0)
        {
            //////����routelistInfo.getIterationCreator()�����û�������ȫ�ơ�
            query.addLeftParentheses();
            QueryCondition cond7 = RouteHelper.handleWildcard("usersDesc", routelistInfo.getIterationCreator());
            query.addCondition(m, cond7);
            query.addOR();
            QueryCondition cond8 = RouteHelper.handleWildcard("usersName", routelistInfo.getIterationCreator());
            query.addCondition(m, cond8);
            query.addRightParentheses();
            query.addAND();
            QueryCondition cond80 = new QueryCondition("iterationCreator", "bsoID");
            query.addCondition(0, m, cond80);
            query.addAND();
        }
        if(modifyTime != null && modifyTime.trim().length() != 0)
        {
            RouteHelper.handleTimeQuery(query, modifyTime, "modifyTime");
        }
        if(productNumber != null && productNumber.trim().length() != 0)
        {
            //�����Ʒ���������󣬽�����ʾ���Ƿ���Ҫ��
            //hasPartNumber(productNumber);
            QueryCondition cond10 = RouteHelper.handleWildcard("partNumber", productNumber);
            query.addCondition(n, cond10);
            query.addAND();
            QueryCondition cond100 = new QueryCondition("productMasterID", "bsoID");
            query.addCondition(0, n, cond100);
            query.addAND();
            //if(VERBOSE)
            //System.out.println(TIME + "findRouteLists's for clause end......... SQL = " + query.getDebugSQL());
        }
        //����汾ǡ��Ϊ���°棬����Ȩ�ޣ������ѳ��������ϼеĶ����������������Ȼҵ���������
        if(routelistInfo.getVersionID() != null && routelistInfo.getVersionID().trim().length() != 0)
        {
            //////////////////getVersionValue�ĳ�getVersionID 2004.9.11 ������
            QueryCondition cond11 = RouteHelper.handleWildcard("versionID", routelistInfo.getVersionID());
            query.addCondition(0, cond11);
            query.addAND();
            //routelistֵ���󼯺ϡ�
            if(VERBOSE)
            {
                System.out.println(TIME + "routeService's findRouteLists if... clause, SQL = " + query.getDebugSQL());
                //���������������й��ˣ��ݲ����С�
            }
        }
        QueryCondition cond12 = new QueryCondition("iterationIfLatest", Boolean.TRUE);
        query.addCondition(cond12);
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's findRouteLists else... clause, SQL = " + query.getDebugSQL());
            //routelistֵ���󼯺ϡ�
        }
        query.setDisticnt(true);
        //addListOrderBy(query);
        Collection result = pservice.findValueInfo(query);
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's findRouteLists result = " + result);
            //���ݹ���״̬���й��ˡ�ԭ����������ͬʱ���ڡ���ù���·�߱�ֵ���󼯺ϡ�
        }
        filterByWorkState(result);
        //����·�߱����������С�
        Collection sortedVec = RouteHelper.sortedInfos(result, "getRouteListNumber", false);
        if(VERBOSE)
        {
            System.out.println(TIME + "exit routeService's findRouteLists sortedVec = " + sortedVec);
        }
        return sortedVec;
    }

    /**
     * @roseuid 402CBAF700CA
     * @J2EE_METHOD -- findRouteLists ��ù���·�߱�������Χ����š����ơ�״̬���������ڲ�Ʒ��˵�������λ�á��������ڡ������ߡ������¡��汾�š�
     * @return collection ����·�߱�ֵ�������°汾�� ��ʱҪ��ConfigService���й��ˡ� public Collection findRouteLists(TechnicsRouteListIfc routelistInfo, String productNumber) { Collection result = new Vector();
     * PersistService pservice = (PersistService)EJBServiceHelper.getPersistService(); QMQuery query = new QMQuery(ROUTELIST_BSONAME, ROUTELISTMASTER_BSONAME);
     * if(routelistInfo.getRouteListNumber()!=null&&routelistInfo.getRouteListNumber().trim().length()!=0) { QueryCondition cond1 = new QueryCondition("routeListNumber", QueryCondition.EQUAL,
     * routelistInfo.getRouteListNumber()); query.addCondition(0, cond1); query.addAND(); } if(routelistInfo.getRouteListName()!=null&&routelistInfo.getRouteListName().trim().length()!=0) {
     * QueryCondition cond2 = new QueryCondition("routeListName", QueryCondition.EQUAL, routelistInfo.getRouteListName()); query.addCondition(0, cond2); query.addAND(); }
     * if(routelistInfo.getLifeCycleState().toString()!=null&& routelistInfo.getLifeCycleState().toString().trim().length()!=0) { QueryCondition cond3 = new QueryCondition("lifeCycleState",
     * QueryCondition.EQUAL, routelistInfo.getLifeCycleState().toString()); query.addCondition(0, cond3); query.addAND(); }
     * if(routelistInfo.getRouteListLevel()!=null&&routelistInfo.getRouteListLevel().trim().length()!=0) { QueryCondition cond4 = new QueryCondition("routeListLevel", QueryCondition.EQUAL,
     * routelistInfo.getRouteListLevel()); query.addCondition(0, cond4); query.addAND(); } if(routelistInfo.getRouteListDescription()!=null&&routelistInfo.getRouteListDescription().trim().length()!=0)
     * { QueryCondition cond5 = new QueryCondition("routeListDescription", QueryCondition.EQUAL, routelistInfo.getRouteListDescription()); query.addCondition(0, cond5); query.addAND(); }
     * if(routelistInfo.getLocation()!=null&&routelistInfo.getLocation().trim().length()!=0) { QueryCondition cond6 = new QueryCondition("location", QueryCondition.EQUAL, routelistInfo.getLocation());
     * query.addCondition(0, cond6); query.addAND(); } if(routelistInfo.getCreateTime()!=null) { //QueryCondition cond7 = new QueryCondition("createTime", QueryCondition.EQUAL,
     * routelistInfo.getCreateTime()); //query.addCondition(0, cond7); //query.addAND(); } if(routelistInfo.getCreator()!=null&&routelistInfo.getCreator().trim().length()!=0) { QueryCondition cond8 =
     * new QueryCondition("creator", QueryCondition.EQUAL, routelistInfo.getCreator()); query.addCondition(0, cond8); query.addAND(); } if(routelistInfo.getModifyTime()!=null) { //QueryCondition cond9
     * = new QueryCondition("modifyTime", QueryCondition.EQUAL, routelistInfo.getModifyTime()); //query.addCondition(0, cond9); //query.addAND(); }
     * if(productNumber!=null&&productNumber.trim().length()!=0) { Collection productMasterInfo = getProductMasterID(productNumber); for(Iterator iter = productMasterInfo.iterator(); iter.hasNext(); )
     * {///////////////////////////////���������������������������������� QueryCondition cond10 = new QueryCondition("productMasterID", QueryCondition.EQUAL, ((QMPartMasterIfc)iter.next()).getBsoID());
     * query.addCondition(0, cond10); query.addOR(); } } //����汾ǡ��Ϊ���°棬�����ѳ��������ϼеĶ����� if(routelistInfo.getVersionValue()!=null&&routelistInfo.getVersionValue().trim().length()!=0) { QueryCondition
     * cond11 = new QueryCondition("versionValue", QueryCondition.EQUAL, routelistInfo.getVersionValue()); query.addCondition(0, cond11); query.setDisticnt(true); query.setVisiableResult(1);
     * //routelistֵ���󼯺ϡ� if(VERBOSE) System.out.println(TIME + "routeService's findRouteLists if... clause, SQL = " + query.getDebugSQL()); result = pservice.findValueInfo(query); //���������������й��ˣ��ݲ����С� }
     * else { QueryCondition cond11 = new QueryCondition("iterationIfLatest", Boolean.TRUE); query.addCondition(0, cond11); query.addAND(); QueryCondition cond12 = new QueryCondition("masterBsoID",
     * "bsoID"); query.addCondition(0, 1, cond12); query.setDisticnt(true); query.setVisiableResult(0); if(VERBOSE) System.out.println(TIME + "routeService's findRouteLists else... clause, SQL = " +
     * query.getDebugSQL()); //routelistMasterֵ���󼯺ϡ� Collection coll = pservice.findValueInfo(query); for(Iterator iter = coll.iterator(); iter.hasNext(); ) { //���˺�Ľ���� TechnicsRouteListIfc
     * routeListInfo = (TechnicsRouteListIfc)getLatestVesion((TechnicsRouteListMasterIfc) iter.next()); result.add(routeListInfo); } } return result; }
     */

    //����7.�鿴����·�߱�
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC307
    //getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)
    ////////////////////////////////////////////////////////////////////////////

    /**
     * ���ɱ����������Ʊ��������ʽΪ�����ļ���
     * @param routeListID String ·�߱��ID
     * @param exportFormat String �����ʽ @
     * @return String ·�߱������+���+�Ĺ���·�߱��� 20120214 xucy modify
     */
    public String exportRouteList(String routeListID, String exportFormat)throws QMException
    {
        String result = "";
        if(exportFormat.trim().equals(".csv"))
        {
            result = exportRouteList(routeListID);
        }else
        {
            throw new QMException("��ʱֻ֧��.csv��ʽ��");
        }
        return result.toString();
    }

    //��ӱ���ͷ��
    private void appendReportHead(TechnicsRouteListIfc listInfo, StringBuffer result, String level_zh)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter appendReportHead : listInfo.bsoid = " + listInfo.getBsoID());
            //���·�߱���Ϣ��
        }
        String listNumberVal = listInfo.getRouteListNumber();
        String listNameVal = listInfo.getRouteListName();
        Object[] listObj = null;
        String key = null;
        //����·�ߵı�ͷ��ͬ��
        if(level_zh.trim().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
        {
            String departmentName = listInfo.getDepartmentName();
            listObj = new Object[]{departmentName, listNumberVal, listNameVal};
            key = "9";
        }else
        {
            listObj = new Object[]{listNumberVal, listNameVal};
            key = "6";
        }
        String list_total = QMMessage.getLocalizedMessage(RESOURCE, key, listObj);
        result.append(list_total);
        result.append(newline);
        //��Ӳ�Ʒ��Ϣ��
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        Object[] productObj = new Object[2];
        if(listInfo.getProductMasterID() == null || listInfo.getProductMasterID().trim().equals(""))
        {
            productObj = new Object[]{"", ""};
        }else
        {
            QMPartMasterIfc productMasterInfo = (QMPartMasterIfc)pservice.refreshInfo(listInfo.getProductMasterID());
            String partNumberVal = productMasterInfo.getPartNumber();
            String partNamerVal = productMasterInfo.getPartName();
            productObj = new Object[]{partNumberVal, partNamerVal};
        }
        key = "7";
        String product_total = QMMessage.getLocalizedMessage(RESOURCE, key, productObj);
        result.append(product_total + four_comma);
        //��ӱ���������ڡ�
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
        Object[] dayObj = new Object[]{year, month, day};
        key = "8";
        String date_total = QMMessage.getLocalizedMessage(RESOURCE, key, dayObj);
        result.append(date_total);
        result.append(newline);
        if(VERBOSE)
        {
            System.out.println("exit appendReportHead : result = " + result);
        }
    }

    /**
     * ���ɱ����������Ʊ��������ʽΪ�����ļ���
     * @param routeListID String ·�߱��ID
     * @return String ·�߱������+���+�Ĺ���·�߱��� 20120214 xucy modify �ݿͻ���
     */
    public String exportRouteList(String routeListID)throws QMException
    {
        StringBuffer result = new StringBuffer();
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
        appendReportHead(listInfo, result);
        Map reportMap = null;
        String partName = JNDIUtil.getFeatureValue(new QMPartMasterInfo().getBsoName(), "DisplayName");
        String listName = JNDIUtil.getFeatureValue(new TechnicsRouteListMasterInfo().getBsoName(), "DisplayName");

        result.append(partName + four_comma);
        result.append(listName);
        result.append(newline);

        String order = "���";
        String number = JNDIUtil.getPropertyDescript(new QMPartMasterInfo().getBsoName(), "partNumber").getDisplayName();
        String name = JNDIUtil.getPropertyDescript(new QMPartMasterInfo().getBsoName(), "partName").getDisplayName();
        String version = "�汾";
        result.append(order + comma + number + comma + name + comma + version + comma);
        //String manuRoute = RouteCategoryType.MANUFACTUREROUTE.getDisplay();
        //String assemRoute = RouteCategoryType.ASSEMBLYROUTE.getDisplay();
        result.append("��Ҫ·��" + comma + "��Ҫ·��");
        //CR25 begin
        if(routeMode.equals("productRelative"))
        {
            result.append(comma+"��Ʒ���"+comma+"��Ʒ����");
        }else if(routeMode.equals("parentRelative"))
        {
            result.append(comma+"�������"+comma+"��������");
        }else if(routeMode.equals("productAndparentRelative"))
        {
            result.append(comma+"��Ʒ���"+comma+"��Ʒ����"+comma+"�������"+comma+"��������");
        }
        result.append(newline);
        //reportMap = getFirstLeveRouteListReport1(listInfo);
        int i = 0;
        //Collection sortedVec = RouteHelper.sortedInfos(reportMap.keySet());
        Collection sortedVec = this.getRouteListLinkParts1(listInfo);
        for(Iterator iter = sortedVec.iterator();iter.hasNext();)
        {
            i++;
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            result.append(i + comma);
            appendPartMsg(linkInfo, result);
            //Collection nodes = (Collection)reportMap.get(partMasterInfo);

            //appendNodesMsg(nodes, result, four_comma);
            appendRouteStrMsg(linkInfo, result);
            appendRouteModeInfo(linkInfo,result);
            //CR25 end
            result.append(newline);
        }

        return result.toString();
    }

    //��firstNodes��secondNodes�ϳ�һ���¼��ϡ�������д��result�С�
    private void appendNodesMsg_Second(Collection firstNodes, Collection secondNodes, StringBuffer result)throws QMException
    {
        Object[] firstObj = firstNodes.toArray();
        Object[] secondObj = secondNodes.toArray();
        int i = firstObj.length;
        int j = secondObj.length;
        int x = 0;
        if(j > i)
        {
            x = j;
        }else
        {
            x = i;
        }
        Object[] inteObj = new Object[x];
        for(int k = 0;k < x;k++)
        {
            Object[] bigObj = new Object[2];
            Object obj1 = null;
            Object obj2 = null;
            if(k < i)
            {
                obj1 = firstObj[k];
            }
            if(k < j)
            {
                obj2 = secondObj[k];
            }
            bigObj[0] = obj1;
            bigObj[1] = obj2;
            inteObj[k] = bigObj;
        }
        PrintWriter out = null;
        try
        {
            out = new PrintWriter(new FileWriter("c:\111.txt", true));
        }catch(IOException ex)
        {
            ex.printStackTrace();
        }
        if(x == 0)
        {
            result.append(comma);
            result.append(comma);
            result.append(comma);
            result.append(comma);
            result.append(newline);
            return;
        }
        for(int v = 0;v < inteObj.length;v++)
        {
            out.println("enter inteObj ." + (v + 1) + "��");
            //result = new StringBuffer();

            if(v > 0)
            {
                result.append(four_comma);
            }
            Object[] bigObj = (Object[])inteObj[v];
            //add by guoxl on 2008-07-04(���ɶ���·�߱��ر�����������ʹ���)
            if(bigObj[0] instanceof String)
            {
                Object[] first1 = new Object[1];
                first1[0] = bigObj[0].toString();
                appendOnlyOneBranch(first1, result);

            }else
            {
                // add by guoxl end (���ɶ���·�߱��ر�����������ʹ���)
                Object[] first = (Object[])bigObj[0];
                appendOnlyOneBranch(first, result);
            }
            //add by guoxl on 2008-07-04(���ɶ���·�߱��ر�����������ʹ���)
            if(bigObj[1] instanceof String)
            {
                Object[] second1 = new Object[1];
                second1[0] = bigObj[1].toString();
                appendOnlyOneBranch(second1, result);
            }else
            {
                // add by guoxl end (���ɶ���·�߱��ر�����������ʹ���)  
                Object[] second = (Object[])bigObj[1];
                appendOnlyOneBranch(second, result);
            }
            result.append(newline);
            out.println(result);
            out.println("exit inteObj ." + (v + 1) + "��");
        }
    }

    //��ӱ���ͷ��
    private void appendReportHead(TechnicsRouteListIfc listInfo, StringBuffer result)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter appendReportHead : listInfo.bsoid = " + listInfo.getBsoID());
            //���·�߱���Ϣ��
        }
        String listNumberVal = listInfo.getRouteListNumber();
        String listNameVal = listInfo.getRouteListName();
        Object[] listObj = null;
        String key = null;
        //����·�ߵı�ͷ��ͬ��
        //        if(level_zh.trim().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
        //        {
        //            String departmentName = listInfo.getDepartmentName();
        //            listObj = new Object[]{departmentName, listNumberVal, listNameVal};
        //            key = "9";
        //        }else
        {
            listObj = new Object[]{listNumberVal, listNameVal};
            key = "6";
        }
        String list_total = QMMessage.getLocalizedMessage(RESOURCE, key, listObj);
        result.append(list_total);
        result.append(newline);
        //��Ӳ�Ʒ��Ϣ��
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        Object[] productObj = new Object[2];
        if(listInfo.getProductMasterID() == null || listInfo.getProductMasterID().trim().equals(""))
        {
            productObj = new Object[]{"", ""};
        }else
        {
            QMPartMasterIfc productMasterInfo = (QMPartMasterIfc)pservice.refreshInfo(listInfo.getProductMasterID());
            String partNumberVal = productMasterInfo.getPartNumber();
            String partNamerVal = productMasterInfo.getPartName();
            productObj = new Object[]{partNumberVal, partNamerVal};
        }
        key = "7";
        String product_total = QMMessage.getLocalizedMessage(RESOURCE, key, productObj);
        result.append(product_total + four_comma);

        //��ӱ���������ڡ�
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1);
        String day = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
        Object[] dayObj = new Object[]{year, month, day};
        key = "8";
        String date_total = QMMessage.getLocalizedMessage(RESOURCE, key, dayObj);
        result.append(date_total);
        result.append(newline);
        if(VERBOSE)
        {
            System.out.println("exit appendReportHead : result = " + result);
        }
    }

    //��������Ϣ��
    private void appendPartMsg(ListRoutePartLinkIfc link, StringBuffer result)throws QMException
    {
        result.append(link.getPartMasterInfo().getPartNumber() + comma);
        result.append(link.getPartMasterInfo().getPartName() + comma);
        result.append(((QMPartIfc)getLatestVesion(link.getPartMasterInfo())).getVersionValue() + comma);
    }

    //��ӽڵ���Ϣ��
    private void appendNodesMsg(Collection nodes, StringBuffer result, String comma_num)throws QMException
    {
        int i = 0;
        for(Iterator iter1 = nodes.iterator();iter1.hasNext();)
        {
            i++;
            if(i > 1)
            {
                result.append(newline);
                result.append(comma_num);
            }
            Object[] obj = (Object[])iter1.next();
            appendOnlyOneBranch(obj, result);
        }
    }

    /**
     * ���·����Ϣ��20120214 xucy add
     */
    private void appendRouteStrMsg(ListRoutePartLinkIfc link, StringBuffer result)
    {
        if(link.getMainStr() == null)
        {
            result.append("" + comma);
        }else
        {
            result.append(link.getMainStr() + comma);
        }
        if(link.getSecondStr() == null)
        {
            result.append("" + comma);
        }else
        {
            result.append(link.getSecondStr() + comma);
        }
    }
    //CR25 begin
    /**
     * ���ݲ�ͬģʽ������ɱ������Ϣ����
     */
    private void appendRouteModeInfo(ListRoutePartLinkIfc link, StringBuffer result)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMPartMasterInfo master;
        if(routeMode.equals("productRelative"))
        {
           if(link.getProductID()==null)
           {
               result.append("" + comma+""+comma);
           }else
           {
               master=(QMPartMasterInfo)pservice.refreshInfo(link.getProductID());
               result.append(master.getPartNumber() + comma+master.getPartName()+comma);
           }
        }else if(routeMode.equals("parentRelative"))
        {
           if(link.getParentPartNum()==null)
           {
               result.append(""+comma+""+comma);
           }else
           {
               result.append(link.getParentPartNum()+comma+link.getParentPartName()+comma);
           }
        }else if(routeMode.equals("productAndparentRelative"))
        {
            if(link.getProductID()==null)
            {
                result.append("" + comma+""+comma);
            }else
            {
                master=(QMPartMasterInfo)pservice.refreshInfo(link.getProductID());
                result.append(master.getPartNumber() + comma+master.getPartName()+comma);
            }
            if(link.getParentPartNum()==null)
            {
                result.append(""+comma+""+comma);
            }else
            {
                result.append(link.getParentPartNum()+comma+link.getParentPartName()+comma);
            }
        }
    }
    //CR25 end
    private void appendOnlyOneBranch(Object[] obj, StringBuffer result)
    {

        if(obj == null)
        {
            result.append(comma + comma);
            return;
        }
        //add by guoxl on 2008-07-04(���ɶ���·�߱��ر�����������ʹ���)
        if(obj[0] instanceof String)
        {
            String branch = obj[0].toString();
            int index = branch.indexOf("@");
            if(index != -1)
            {
                String sub1 = branch.substring(0, index);
                String sub2 = branch.substring(index + 1);
                result.append(sub1 + comma + sub2);

            }else
                result.append(obj[0] + comma);

        }else
        {
            //add by guoxl end (���ɶ���·�߱��ر�����������ʹ���)
            Collection manuColl = (Collection)obj[0];
            int k = 0;
            for(Iterator iter2 = manuColl.iterator();iter2.hasNext();)
            {
                k++;
                RouteNodeIfc manuNode = (RouteNodeIfc)iter2.next();
                if(manuColl.size() == k)
                {
                    result.append(manuNode.getNodeDepartmentName() + comma);
                }else
                {
                    result.append(manuNode.getNodeDepartmentName() + line);
                }
            }
            if(manuColl.size() == 0)
            {
                result.append(comma);
            }
            RouteNodeIfc assemNode = (RouteNodeIfc)obj[1];
            if(assemNode != null)
            {
                result.append(assemNode.getNodeDepartmentName());
            }
        }
        result.append(comma);
    }

    /**
     * ���ݹ���·�߱�ID���һ������·�߱���
     * @param listInfo TechnicsRouteListIfc ·�߱�ֵ���󣬸��ݹ���·�߱�ID���������乤��·��ֵ���� @
     * @return CodeManageTable key:partMasterInfo���ֵ����;<br> value����ŵ���Map:key:routeBranchInfo ·�߷�ֵ֧����;<br> value: routeStr ·�ߴ�
     * @see TechnicsRouteListInfo
     */
    //obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ����
    public CodeManageTable getFirstLeveRouteListReport(TechnicsRouteListIfc listInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
        if(!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.FIRSTROUTE.getDisplay()))
        {
            throw new QMException("·�߱�Ӧ����һ��·�ߡ�");
        }
        //CCBegin SS35
        String comp=RouteClientUtil.getUserFromCompany();
        //CCEnd SS35
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME, PARTMASTER_BSONAME);
        QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL, listInfo.getBsoID());
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond2);
        QueryCondition cond3 = new QueryCondition(RIGHTID, "bsoID");
        query.addAND();
        query.addCondition(0, 1, cond3);
        query.setVisiableResult(1);
        //CCBegin SS35  SS36
        System.out.println("comp_____________3333333333333333333333333333333333__________"+comp);
        UserIfc user = (UserIfc)pservice.refreshInfo(listInfo.getIterationCreator());
        System.out.println("user.getUsersName()getFirstLeveRouteListReport__________________________________________________________"+user.getUsersName());
        if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))
        {
//        if(comp.equals("ct")){
        	query.addOrderBy("modifyTime");       
        }else{
        	query.addOrderBy(1, "partNumber");
        }
        //CCEnd SS35  SS36
        
        Collection coll = pservice.findValueInfo(query);
        //CCBegin SS8
     
        QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME, PART_BSONAME);
        QueryCondition cond11 = new QueryCondition(LEFTID, QueryCondition.EQUAL, listInfo.getBsoID());
        query1.addCondition(cond11);
        query1.addAND();
        QueryCondition cond21 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query1.addCondition(cond21);
        QueryCondition cond31 = new QueryCondition(RIGHTID, "bsoID");
        query1.addAND();
        query1.addCondition(0, 1, cond31);
        //CCBegin SS17
        int k=query1.appendBso(PARTMASTER_BSONAME, false);
    	QueryCondition cond41 = new QueryCondition("masterBsoID", "bsoID");
        query1.addAND();
		query1.addCondition(1, k, cond41);
        
      //CCBegin SS35
        System.out.println("user.getUsersName();____________________________"+user.getUsersName());
        if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))
        {
//        if(comp.equals("ct")){  
        	query1.addOrderBy(0,"modifyTime"); 
        }else{
        	query1.addOrderBy(k, "partNumber");
        }
        //CCEnd SS35
        //CCEnd SS17
        query1.setVisiableResult(1);

//        query1.addOrderBy(1, "partNumber");
        Collection coll1 = pservice.findValueInfo(query1);
      //CCEnd SS8
        CodeManageTable firstMap = new CodeManageTable();
        
        //CCBeign SS36
        if (coll != null && coll.size() > 0) 
        {
        	Vector vecListRoutePartLinkBefore = new Vector();
        	Vector vecListRoutePartLinkAfter = new Vector();  
        	for(Iterator iter = coll.iterator();iter.hasNext();)
            {
				ListRoutePartLinkIfc linkInfoOld = (ListRoutePartLinkIfc) iter.next();
                vecListRoutePartLinkBefore.add(linkInfoOld);
            }
            if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))
            {
            	vecListRoutePartLinkAfter = sortLinks(listInfo , vecListRoutePartLinkBefore);
            }else
            {
            	vecListRoutePartLinkAfter = vecListRoutePartLinkBefore;
            }
        
            for(int ii = 0; ii < vecListRoutePartLinkAfter.size(); ii++)  
            {
                ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)vecListRoutePartLinkAfter.elementAt(ii);
                QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();
                // ���ݹ���·��ID��ù���·�߷�֦��ض���
                //�������������ɱ��������Ż�
                //   Map routeMap = getRouteBranchs(linkInfo.getRouteID()); ԭ����
                Map routeMap = this.getDirectRouteBranchStrs(linkInfo.getRouteID());
                Object[] obj = new Object[2];
                obj[0] = routeMap.values().toString();
                obj[1] = linkInfo;
                firstMap.put(partMasterInfo,obj );
            }
        }

        if (coll1 != null && coll1.size() > 0) 
        {
        	Vector vecListRoutePartLinkBefore = new Vector();
        	Vector vecListRoutePartLinkAfter = new Vector();  
            for(Iterator iter = coll1.iterator();iter.hasNext();)
            {
                ListRoutePartLinkIfc linkInfoOld = (ListRoutePartLinkIfc)iter.next();
                vecListRoutePartLinkBefore.add(linkInfoOld);
            }
            if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))
            {
            	vecListRoutePartLinkAfter = sortLinks(listInfo , vecListRoutePartLinkBefore);
            }else
            {
            	vecListRoutePartLinkAfter = vecListRoutePartLinkBefore;
            }
        
            for(int ii = 0; ii < vecListRoutePartLinkAfter.size(); ii++)  
            {
            	//CCBegin SS8
	            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)vecListRoutePartLinkAfter.elementAt(ii);
	            QMPartIfc partInfo = (QMPartIfc) pservice.refreshInfo(linkInfo.getRightBsoID());
	            // ���ݹ���·��ID��ù���·�߷�֦��ض���
	            //�������������ɱ��������Ż�
	            //   Map routeMap = getRouteBranchs(linkInfo.getRouteID()); ԭ����
	            Map routeMap = this.getDirectRouteBranchStrs(linkInfo.getRouteID());
	            Object[] obj = new Object[2];
	            obj[0] = routeMap.values().toString();
	            obj[1] = linkInfo;
	            firstMap.put(partInfo,obj );
            }
        }
        //CCEnd SS8
        //CCEnd SS36
        if(VERBOSE)
        {
            System.out.println("exit getFirstLeveRouteListReport , firstMap.values = " + firstMap.values().toString());
        }
        return firstMap;
    }
    
    
    //CCBegin SS36
	private Vector sortLinks(TechnicsRouteListIfc  routeListInfo , Vector v) 
	{

		Vector indexs = routeListInfo.getPartIndex();
		if (VERBOSE) {
			System.out.println("����ǰ v= " + v + " ����indexs = " + indexs);
		}
		if (indexs == null || indexs.size() == 0) {
			 System.out.println("���򼯺�Ϊ��");
			return v;
		}
		Vector result = new Vector();
		String partid;
		String partNum;
		String[] index;

		for (int i = 0; i < indexs.size(); i++) {
			index = (String[]) indexs.elementAt(i);
			partid = index[0];
			System.out.println("˳�����" + partid);
			partNum = index[1];
			ListRoutePartLinkIfc link;
			for (int j = 0; j < v.size(); j++) {
				link = (ListRoutePartLinkIfc) v.elementAt(j);
				System.out.println("�������" + link.getRightBsoID());
				if (link.getRightBsoID().equals(partid)) {
					result.add(link);
					v.remove(link);
					break;
				}
			}
		}
		if (VERBOSE) {
			System.out.println("����� result= " + result);
		}
		return result;
	}
    //CCEnd SS36
    

    /**
     * ���ݹ���·�߱�ID���һ������·�߱���
     * @param listInfo TechnicsRouteListIfc ·��ֵ���󣬸��ݹ���·�߱�ID���������乤��·��ֵ���� @
     * @return Map key:QMPartMasterIfc���(����Ϣ)ֵ����;value�����һ��map: key��TechnicsRouteBranchIfc ����·�߷�ֵ֦����,<br> value��·�߽ڵ����飬obj[0]=����·�߽ڵ�ֵ���󼯺ϣ� obj[1]=װ��·�߽ڵ�ֵ����
     * @see TechnicsRouteListInfo
     */
    public Map getFirstLeveRouteListReport1(TechnicsRouteListIfc listInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
        if(!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.FIRSTROUTE.getDisplay()))
        {
            throw new QMException("·�߱�Ӧ����һ��·�ߡ�");
        }
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);  
        QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL, listInfo.getBsoID());
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond2);
        
        //CCBegin SS35
        String comp=RouteClientUtil.getUserFromCompany();        
        UserIfc user = (UserIfc)pservice.refreshInfo(listInfo.getIterationCreator());
        System.out.println("user.getUsersName()getFirstLeveRouteListReport1__________________________________________________________"+user.getUsersName());
        if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))
        {
//        if(comp.equals("ct")){
        	query.addOrderBy(0,"modifyTime"); 
        }    
        //CCEnd SS35
        
        Collection coll = pservice.findValueInfo(query);
        Map firstMap = new HashMap();
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();
            Map routeMap = getRouteBranchs(linkInfo.getRouteID());
            firstMap.put(partMasterInfo, routeMap.values());
        }
        if(VERBOSE)
        {
            System.out.println("exit getFirstLeveRouteListReport , firstMap.values = " + firstMap.values());
        }
        return firstMap;
    }

    /**
     * ���ݹ���·�߱�ID��ö�������·�߱���
     * @param listInfo TechnicsRouteListIfc ·�߱�ֵ���� @
     * @return Map key:QMPartMasterIfc���(����Ϣ)ֵ����;<br> value�������һ��obj[] :<bt>obj[0]:һ������·�߽ڵ������󼯺�,obj[1]:��������·�߽ڵ������󼯺�<br> obj2[0]�� ����·�߽ڵ�ֵ���󼯺ϣ�obj2[1]��װ��·�߽ڵ�ֵ����
     * @see TechnicsRouteListInfo
     */
    // �����а����������ϣ�obj[0]=һ������·�߽ڵ������󼯺ϣ������������顣 obj1[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj1[1]=װ��·�߽ڵ�ֵ����
    // * obj[1] = ��������·�߽ڵ������󼯺ϣ������������顣obj2[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj2[1]=װ��·�߽ڵ�ֵ���󡣡�
    public Map getSecondLeveRouteListReport(TechnicsRouteListIfc listInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
        if(!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
        {
            throw new QMException("·�߱�Ӧ���Ƕ���·�ߡ�");
        }
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL, listInfo.getBsoID());
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond2);
        Collection coll = pservice.findValueInfo(query);
        String departmentID = listInfo.getRouteListDepartment();
        if(departmentID == null || departmentID.trim().length() == 0)
        {
            throw new QMException("����·�߱����е�λ��");
        }
        Map secondMap = new HashMap();
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();

            //���partMasterInfo�Ķ���·�߶�Ӧ��һ��·�ߴ���
            //Collection nodes = getFirstNodesBySecondRoute(departmentID, listInfo.getProductMasterID());
            Collection nodes = getFirstNodesBySecondRoute(departmentID, linkInfo.getPartMasterID());
            if(nodes != null)
            { //lm add 20040526
                Object obj[] = new Object[2];
                obj[0] = nodes;
                //��ö���·�߰���·�ߴ���
                //Map routeMap = getRouteBranchs(linkInfo.getRouteID());//ԭ����
                //�������壩2006 08 03  zz ������� �����Ż�
                Map routeMap = getDirectRouteBranchStrs(linkInfo.getRouteID());//end
                obj[1] = routeMap.values();
                secondMap.put(partMasterInfo, obj);
            }
            ///////////////////// lm add 20040826
            else
            { //û��һ��·��ʱ��ֻ��ʾ����·�߽ڵ�
                Object obj2[] = new Object[2];
                obj2[0] = new Vector();
                //��ö���·�߰���·�ߴ���
                //  Map routeMap = getRouteBranchs(linkInfo.getRouteID());ԭ����
                //�������壩2006 08 03  zz ������� �����Ż�
                Map routeMap = getDirectRouteBranchStrs(linkInfo.getRouteID());//end
                obj2[1] = routeMap.values();
                secondMap.put(partMasterInfo, obj2);
            }
        }
        if(VERBOSE)
        {
            Collection values = secondMap.values();
            for(Iterator iter = values.iterator();iter.hasNext();)
            {
                Object[] obj1 = (Object[])iter.next();
                Collection firstColl = (Collection)obj1[0];
                for(Iterator iter1 = firstColl.iterator();iter1.hasNext();)
                {
                    Object[] obj2 = (Object[])iter1.next();
                    if(VERBOSE)
                    {
                        System.out.println("getFirstLeveRouteListReport , firstColl -- manuBranch=" + (Collection)obj2[0] + ", assemblyBranch " + (RouteNodeIfc)obj2[1]);
                    }
                }
                Collection secondColl = (Collection)obj1[1];
                for(Iterator iter2 = secondColl.iterator();iter2.hasNext();)
                {
                    Object[] obj3 = (Object[])iter2.next();
                    if(VERBOSE)
                    {
                        System.out.println("getFirstLeveRouteListReport , firstColl -- manuBranch=" + (Collection)obj3[0] + ", assemblyBranch " + (RouteNodeIfc)obj3[1]);
                    }
                }
            }
        }
        if(VERBOSE)
        {
            System.out.println("exit getSecondLeveRouteListReport...");
        }
        return secondMap;
    }

    //���partMasterInfo�Ķ���·�߶�Ӧ��һ��·�ߴ����˹�������б仯��
    private Collection getFirstNodesBySecondRoute(String departmentID, String productMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter getFirstNodesBySecondRoute, departmentID = " + departmentID + ", productMasterID = " + productMasterID);
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int i = query.appendBso(TECHNICSROUTE_BSONAME, false);
        int j = query.appendBso(ROUTENODE_BSONAME, false);
        int k = query.appendBso(ROUTELIST_BSONAME, false);
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, productMasterID);
        query.addCondition(0, cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition(LEFTID, "bsoID");
        query.addCondition(0, k, cond4);
        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, RouteListLevelType.FIRSTROUTE.toString());
        query.addCondition(k, cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("routeID", "routeID");
        query.addCondition(0, j, cond6);
        query.addAND();
        QueryCondition cond7 = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, departmentID);
        query.addCondition(j, cond7);
        query.addAND();
        QueryCondition cond8 = new QueryCondition("routeID", "bsoID");
        query.addCondition(0, i, cond8);
        //·���޸�ʱ�併�����С�
        //query.addOrderBy(i, "modifyTime", true);
        query.setDisticnt(true);
        if(VERBOSE)
        {
            System.out.println("getFirstNodesBySecondRoute's SQL = " + query.getDebugSQL());
        }
        Collection coll = pservice.findValueInfo(query);
        //for(Iterator iter = coll.iterator(); iter.hasNext();)
        //���쳣�Ƿ�Ӧ���׳���
        if(coll.size() == 0)
        {
            if(VERBOSE)
            {
                System.out.println(productMasterID + "û�ж�Ӧ��һ��·�ߡ�");
            }
            return null; //lm modify 20040526
        }
        //·���޸�ʱ�併�����С�
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
        Collection result = null;
        Iterator iter = sortedVec.iterator();
        //ȡ����޸ĵ�·�߶�Ӧ�Ĺ�����
        if(iter.hasNext())
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            if(VERBOSE)
            {
                System.out.println("routeService's getFirstNodesBySecondRoute linkInfo = " + linkInfo);
                //���һ��·�߰���·�ߴ���
            }
            // Map routeMap = getRouteBranchs(linkInfo.getRouteID());
            //  �������壩2006 08 03  zz ������� �����Ż� ���ɱ���  ֱ�Ӵ�branch��ȡ·�ߴ��ַ���,���ûָ�node����
            Map routeMap = this.getDirectRouteBranchStrs(linkInfo.getRouteID());
            result = routeMap.values();
        }
        if(VERBOSE)
        {
            System.out.println("exit getFirstNodesBySecondRoute  result = " + result);
        }
        return result;
    }

    ////////////////////////////���ɱ��������///////////////////////////////

    /**
     * ������еĹ���·�߱�����°汾�������A,B�棬����B������С�汾�� @
     * @return Collection ���obj[]���ϣ�<br>obj[0]������·�߱�ֵ����
     */
    public Collection getAllRouteLists()throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService(CONFIG_SERVICE);
        QMQuery query = new QMQuery(ROUTELISTMASTER_BSONAME);
        Collection coll = pservice.findValueInfo(query);
        Vector listVec = new Vector();
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            TechnicsRouteListMasterIfc listMasterInfo = (TechnicsRouteListMasterIfc)iter.next();
            //������еĹ���·�߱�����°汾�������A,B�棬����B������С�汾��
            Collection coll1 = cservice.filteredIterationsOf(listMasterInfo, new LatestConfigSpec());
            Iterator iter1 = coll1.iterator();
            if(iter1.hasNext())
            {
                Object[] obj = (Object[])iter1.next();
                //if(!(obj[0] instanceof MasteredIfc))
                listVec.addElement(obj[0]);
            }
        }
        return listVec;
    }

    /**
     * �����·�߱���ص��㲿����
     * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ���� @
     * @return Collection �洢ListRoutePartLinkIfc������ֵ���󼯺ϡ�
     * @see TechnicsRouteListInfo
     */
    public Collection getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter the class:1731�У�����:getRouteListLinkParts()");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
        query.addCondition(cond);
        query.addAND();
        //�п������δʹ��·�ߡ�
        QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond1);
        //CR30 begin
//        if(routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
//        {
//            query.addAND();
//            QueryCondition cond2 = new QueryCondition("productID", QueryCondition.NOT_NULL);
//            query.addCondition(cond2);
//        }
        //CR30 end
        Collection coll = pservice.findValueInfo(query);
        ///////2004.4.28�޸�//////////
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            if(linkInfo.getRouteID() == null)
            {
                /**
                 * ԭ�д��� String status = getStatus(linkInfo.getPartMasterID(), routeListInfo.getRouteListLevel()); linkInfo.setAdoptStatus(status);
                 */
                /*
                 * �����޸� �޸��� skybird 2005.2.24
                 */
                //begin
                String level = routeListInfo.getRouteListLevel();
                String level2 = new String("����·��");
                String status = null;
                if(level.equals(level2))
                {
                    String departmentBsoid = routeListInfo.getRouteListDepartment();
                    status = getStatus2(linkInfo.getPartMasterID(), level, departmentBsoid);
                }else
                {
                    status = getStatus(linkInfo.getPartMasterID(), routeListInfo.getRouteListLevel());
                }
                linkInfo.setAdoptStatus(status);
                //end
            }
        }
        return coll;
    }

    /**
     * �����·�߱���ص��㲿����
     * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ���� @
     * @return Collection �洢ListRoutePartLinkIfc������ֵ���󼯺ϡ�
     * @see TechnicsRouteListInfo 20120129 xucy add �鿴��׼���ʱ���ô˷���
     */
    public Collection getRouteListLinkParts1(TechnicsRouteListIfc routeListInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter the class:1731�У�����:getRouteListLinkParts()");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
        query.addCondition(cond);
        query.addAND();
        //�п������δʹ��·�ߡ�
        QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond1);
        //CR26 begin
//        if(routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
//        {
//            query.addAND();
//            QueryCondition cond2 = new QueryCondition("productID", QueryCondition.NOT_NULL);
//            query.addCondition(cond2);
//        }
        //CR26 end
        //CCBegin SS36
        UserIfc user = (UserIfc)pservice.refreshInfo(routeListInfo.getIterationCreator());
        String comp=RouteClientUtil.getUserFromCompany();  
//        System.out.println("user.getUsersName()getFirstLeveRouteListReport1__________________________________________________________"+user.getUsersName());
        if(user.getUsersName().startsWith("ct")&&comp.equals("ct"))
        {
//        if(comp.equals("ct")){
        	query.addOrderBy("modifyTime");  
        }
        //CCEnd SS36
//        System.out.println(query.getDebugSQL());
        Collection coll = pservice.findValueInfo(query);
        ///////2004.4.28�޸�//////////
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
//          System.out.println("ddddddd" + linkInfo.getParentPart());
            if(linkInfo.getRouteID() == null)
            {
                //20120113 xucy modify ����������Ч·��
                Collection col = findPartEffRoute(linkInfo.getRightBsoID());
                Iterator ii = col.iterator();
                ListRoutePartLinkInfo link = null;
                if(col != null && col.size() > 0)
                {
                    //����·����Զֻ��һ��
                    if(ii.hasNext())
                    {
                        Object[] objss = (Object[])ii.next();
                        link = (ListRoutePartLinkInfo)objss[0];
                    }
                    if(link != null && link.getBsoID().trim().length() > 0)
                    {
                        System.out.println();
                        linkInfo.setAdoptStatus("�Ѵ���");
                    }else
                    {
                        linkInfo.setAdoptStatus("δ����");
                    }
                }else
                {
                    linkInfo.setAdoptStatus("δ����");
                }
            }else
            {
                linkInfo.setAdoptStatus("�ѱ���");
            }
        }

        Vector indexs = routeListInfo.getPartIndex();
        if(indexs == null || indexs.size() == 0)
        {
            // System.out.println("���򼯺�Ϊ��");
            return coll;
        }
        Vector vec = new Vector(coll);
        Vector result = new Vector();
        String partid;
        String partNum;
        String[] index;
        for(int i = 0;i < indexs.size();i++)
        {
            index = (String[])indexs.elementAt(i);
            partid = index[0];
            partNum = index[1];
            ListRoutePartLinkIfc link;
            for(int j = 0;j < vec.size();j++)
            {
                link = (ListRoutePartLinkIfc)vec.elementAt(j);
                if(link.getRightBsoID().equals(partid))
                {
//                  CCBegin SS56
                    QMPartInfo partInfo = (QMPartInfo)pservice.refreshInfo(partid);
//                    CCEnd SS56
//                  CCBegin SS56
                    if(comp.equals("cd"))
                    {
                    	System.out.println("partInfo==="+partInfo.getBsoID()+"===source=="+getSourceVersion(partInfo));
                    	link.setSourceVersion(getSourceVersion(partInfo));
                    }
//                    CCEnd SS56
                    result.add(link);
                    vec.remove(link);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * �����·�߱���ص��㲿���� �������ǲ鿴·�߱�汾��ʷ�ǵ��ã����Բ��ò鿴����·�߱��Ƿ�Ϊ�������㲿������·�ߣ����ع���ֵ����Ϳ���
     * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ���� @
     * @return Collection ���ListRoutePartLinkIfc������ֵ���󼯺ϡ�
     * @see TechnicsRouteListInfo
     */
    //�������ģ�zz ������� �����Ż� 2006 07 12
    public Collection getRouteListLinkPartsforVersionCompare(TechnicsRouteListIfc routeListInfo)throws QMException
    {

        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
        query.addCondition(cond);
        query.addAND();
        //�п������δʹ��·�ߡ�
        QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond1);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    /**
     * �������·�߼��ڵ���Ϣ��
     * @param routeID String ·��ID @
     * @return Object[]<br> obj[0]��TechnicsRouteIfc ����·��ֵ����obj[1] �� HashMap,�μ�getRouteBranchs(String routeID)
     */
    public Object[] getRouteAndBrach(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        Object[] objs = new Object[2];
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)pservice.refreshInfo(routeID);
        objs[0] = routeInfo;
        Map map = getRouteBranchs(routeID);
        objs[1] = map;
        return objs;
    }

    /**
     * ���ݹ���·��ID��ù���·�߷�֦��ض���
     * @param routeID String ·��ID @
     * @return Map <br>key��TechnicsRouteBranchIfc ����·�߷�ֵ֦����, value��·�߽ڵ����飬obj[0]=����·�߽ڵ�ֵ���󼯺ϣ� obj[1]=װ��·�߽ڵ�ֵ����
     */
    public Map getRouteBranchs(String routeID)throws QMException
    {
        //  System.out.println(" ����getRouteBranchs���� �������� routeID " + routeID);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);//��TechnicsRouteBranch
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        // System.out.println(" ����getRouteBranchs���� �������� coll " + coll.size());
        Map map = new HashMap();
        //HashMap map = new HashMap();
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            TechnicsRouteBranchIfc routeBranchInfo = (TechnicsRouteBranchIfc)iter.next();
            //���֦���������٣�δ�����������鷽ʽ��
            Collection branchNodes = getBranchRouteNodes(routeBranchInfo);
            //obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ����
            Object[] obj = getNodeType(branchNodes);
            map.put(routeBranchInfo, obj);
        }
        return map;
    }

    /**
     * �ݿͻ�����ʾ·�ߴ�
     * @param routeID String ·��ID @
     * @return Map key:TechnicsRouteBranchIfc ��ֵ֧����; value:·�ߴ�,routeBranchInfo.getRouteStr();
     */
    //�������壩zz �������
    public Map getDirectRouteBranchStrs(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);//��TechnicsRouteBranch
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        Map map = new HashMap();
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            TechnicsRouteBranchIfc routeBranchInfo = (TechnicsRouteBranchIfc)iter.next();
            String routeStr = routeBranchInfo.getRouteStr();

            map.put(routeBranchInfo, routeStr);
        }
        return map;

    }

    /**
     * ���·�߷�֧
     * @param routeID String ·��ID @
     * @return Collection ���pservice.findValueInfo(query)�� �÷�֧�е�·�߽ڵ�ļ���
     */
    public Collection getgetRouteBranchs2(String routeID)throws QMException
    {

        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTENODE_BSONAME);
        query.appendBso(TECHNICSROUTEBRANCH_BSONAME, false);
        query.appendBso(BRANCHNODE_LINKBSONAME, false);
        QueryCondition cond1 = new QueryCondition(LEFTID, "bsoID");
        QueryCondition cond2 = new QueryCondition(RIGHTID, "bsoID");
        QueryCondition cond3 = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(1, cond3);
        query.addAND();
        query.addCondition(0, 1, cond1);
        query.addAND();
        query.addCondition(0, 2, cond2);
        Collection result = pservice.findValueInfo(query);
        return result;
    }

    /**
     * ����·��ID��ø�·���а���֧���з����·�߽ڵ�
     * @param routeID String ·��ID
     * @return Map <br>key��TechnicsRouteBranchIfc��ֵ֧����value��getBranchRouteNodes(routeBranchInfo); �÷�֧�е�·�߽ڵ�ļ��� @
     */
    private Map getBranchNodes(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        Map map = new HashMap();
        //HashMap map = new HashMap();
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            TechnicsRouteBranchIfc routeBranchInfo = (TechnicsRouteBranchIfc)iter.next();
            //���֦���������٣�δ�����������鷽ʽ��
            Collection branchNodes = getBranchRouteNodes(routeBranchInfo);
            map.put(routeBranchInfo, branchNodes);
        }
        return map;
    }

    /**
     * �ݹ���·�߷�֦ID��ù���·�߽ڵ㡣
     * @param routeBranchInfo TechnicsRouteBranchIfc ·��ֵ���� @
     * @return Collection ���pservice.findValueInfo(query)������·�߽ڵ�ֵ����
     * @see TechnicsRouteBranchInfo
     */
    public Collection getBranchRouteNodes(TechnicsRouteBranchIfc routeBranchInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //Collection coll = pservice.navigateValueInfo(routeBranchInfo, BRANCH_ROLENAME, BRANCHNODE_LINKBSONAME, true);
        //QMQuery query = new QMQuery(BRANCHNODE_LINKBSONAME);
        //    QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL,
        //                                             routeBranchInfo.getBsoID());
        //    query.addCondition(cond);
        //    //���������С�
        //    query.addOrderBy("bsoID", false);
        //    Collection coll = pservice.findValueInfo(query);
        //    Collection result = new Vector();
        //    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
        //      RouteBranchNodeLinkIfc linkInfo = (RouteBranchNodeLinkIfc) iter.
        //          next();
        //      result.add(linkInfo.getRouteNodeInfo());
        //    }
        //(����һ)20060609 �����޸� zzstart
        QMQuery query = new QMQuery(ROUTENODE_BSONAME);
        query.appendBso(BRANCHNODE_LINKBSONAME, false);
        QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeBranchInfo.getBsoID());
        query.addCondition(1, cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition(RIGHTID, "bsoID");
        query.addCondition(1, 0, cond2);
        query.addOrderBy(1, "bsoID", false);
        Collection result = pservice.findValueInfo(query);
        //Collection result = pservice.findValueInfo(query);
        //(����һ)20060609 �����޸� end
        return result;
    }

    /**
     * ��·�߽ڵ����ͷ��ࡣװ��·�ߡ�����·�ߡ�
     * @param branchNodes Collection ·�߽ڵ㼯�� @
     * @return Object[] obj[0]�������һ��vector:��vector�����RouteNodeIfc·�߽ڵ�ֵ���� <br> obj[1]��RouteNodeIfcװ��ڵ�ֵ����
     */
    public Object[] getNodeType(Collection branchNodes)throws QMException
    {
        Object[] obj = new Object[2];
        Vector vec = new Vector();
        RouteNodeIfc assemNodeInfo = null;
        Vector test = null;
        int i = 0;
        if(VERBOSE)
        {
            i = 0;
            test = new Vector();
        }
        for(Iterator iter = branchNodes.iterator();iter.hasNext();)
        {
            RouteNodeIfc nodeInfo = (RouteNodeIfc)iter.next();
            if(VERBOSE)
            {
                System.out.println(TIME + "routeService getNodeType : type = " + nodeInfo.getRouteType());
            }
            if(nodeInfo.getRouteType().equals(RouteCategoryType.MANUFACTUREROUTE.getDisplay()))
            {
                vec.add(nodeInfo);
            }else if(nodeInfo.getRouteType().equals(RouteCategoryType.ASSEMBLYROUTE.getDisplay()))
            {
                if(VERBOSE)
                {
                    i++;
                    test.add(nodeInfo);
                }
                assemNodeInfo = nodeInfo;
            }else
            {
                throw new QMException("�ڵ����Ͳ���ȷ" + nodeInfo.getRouteType());
            }
        }
        if(VERBOSE)
        {
            if(i > 1)
            {
                System.out.println(TIME + "һ����֧�г��ֶ��װ��·�ߡ� " + test);
            }
        }
        obj[0] = vec;
        obj[1] = assemNodeInfo;
        return obj;
    }

    //�鿴����·��
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC316

    /**
     * ���ݹ���·�߻����صĽڵ㼰�������
     * @param routeID String ·��ID @
     * @return Object[] <br> obj[0]:RouteNodeInfo·�߽ڵ�ֵ���󼯺ϣ� obj[1]:RouteNodeLinkInfo·�߽ڵ����ֵ���󼯺ϡ�
     */
    public Object[] getRouteNodeAndNodeLink(String routeID)throws QMException
    {
        Object[] obj = new Object[2];
        obj[0] = getRouteNodes(routeID);
        obj[1] = getNodelink(routeID);
        return obj;
    }

    /**
     * ���ݹ���·�߻����صĽڵ�(��·�߷�֧����)���������
     * @param routeID String
     * @return Object[] <br> obj[0]:�����һ��Map��key:TechnicsRouteBranchIfc��ֵ֧���� value:�μ�getBranchRouteNodes(routeBranchInfo);�÷�֧�е�·�߽ڵ�ļ��ϣ���<br> obj[1]��RouteNodeLinkInfo·�߽ڵ����ֵ���󼯺ϡ� @
     */
    public Object[] getBranchNodeAndNodeLink(String routeID)throws QMException
    {
        Object[] obj = new Object[2];
        obj[0] = getBranchNodes(routeID);
        obj[1] = getNodelink(routeID);
        return obj;
    }

    /**
     * @roseuid 40309C6D03A8
     * @J2EE_METHOD -- getRouteNodes ���ݹ���·�߻�ö�Ӧ�Ľڵ㡣 �Ȼ�ù���·�߶�Ӧ��������ʼ�ڵ㡣
     * @return Collection ���·�߽ڵ�����ӵ�ֵ���󼯺ϣ��μ�pservice.findValueInfo(query);��
     */
    private Collection getRouteNodes(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTENODE_BSONAME);
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    /**
     * @roseuid 40309CCD01E3
     * @J2EE_METHOD -- getNodelink ���ݹ���·�߻�ö�Ӧ�Ľڵ������
     */
    private Collection getNodelink(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTENODE_LINKBSONAME);
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    /**
     * ���棬���¹���·�ߡ� ϵͳ������½��Ĺ���·�ߣ�������ˢ��Ϊ�鿴·�߽��� �ڴ�����·�ߺ󣬵�����RoutePartLinkʱ��Ӧ��ListRoutePartLink�б�����Ӧ��·�� �Ƿ�ʹ��״̬���������ݵ�һ���ԡ�
     * @param linkInfo ListRoutePartLinkIfc ·�߱����������ֵ����
     * @param routeInfo TechnicsRouteIfc ·��ֵ���� @
     * @return TechnicsRouteIfc ����·��ֵ����
     * @see ListRoutePartLinkInfo
     * @see TechnicsRouteInso
     */
    public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc linkInfo, TechnicsRouteIfc routeInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        TechnicsRouteIfc routeInfoSaved = null;
        //�ж�·���Ǹ��»����½���
        if(PersistHelper.isPersistent(routeInfo))
        {
            if(VERBOSE)
            {
                System.out.println(TIME + "enter routeService's saveRoute if cause ,��ʼ���¹���·�ߡ�");
                //���¹���·�ߡ�
            }
            routeInfoSaved = (TechnicsRouteIfc)pservice.saveValueInfo(routeInfo);
        }else
        {
            if(VERBOSE)
            {
                System.out.println(TIME + "enter routeService's saveRoute else cause ,��ʼ��������·�ߡ�");
                //���湤��·�ߡ�
            }
            //��ѡ��ĸ���·�߶������һ�ݣ�bsoID��ͬ
            System.out.println("���ı��ID====" + routeInfo.getModifyIdenty());
            routeInfoSaved = (TechnicsRouteIfc)pservice.saveValueInfo(routeInfo);
            if(linkInfo != null)
            {
                String routeListID = linkInfo.getRouteListID();
                String partMasterID = linkInfo.getPartMasterID();
                //�޸� skybird 2005.1.29
                //begin
                String INITIALUSED = linkInfo.getInitialUsed();
                //end
                //����ListRoutePartLink��skybird
                if(linkInfo.getAlterStatus() == INHERIT)
                {
                    //��������,����ԭ����·��״̬Ϊȡ����
                    //  listPostProcess(linkInfo.getRouteListMasterID(), routeListID,
                    //                  partMasterID);
                    //begin �����listPostProcess()���������˸�����
                    listPostProcess(linkInfo.getRouteListMasterID(), routeListID, partMasterID, INITIALUSED);
                    //end
                }
                TechnicsRouteListIfc routeListInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
                linkInfo.setRouteID(routeInfoSaved.getBsoID());
                //���ʱ�ĸ��Ʋ�������
                //linkInfo.setInitialUsed(routeListInfo.getVersionID());
                linkInfo.setRouteListIterationID(routeListInfo.getVersionValue());
                linkInfo.setAlterStatus(ROUTEALTER);
                linkInfo.setAdoptStatus(RouteAdoptedType.ADOPT.getDisplay());
                pservice.saveValueInfo(linkInfo);
            }
        }
        if(VERBOSE)
        {
            System.out.println("exit TechnicsRouteServiecEJB:saveRoute() 2031");
        }
        return routeInfoSaved;
    }

    /**
     * ��þ���·�߰汾�Ĺ�����
     * @param routeListID String ·�߱�ID
     * @param partMasterID String �㲿��ס��ϢID
     * @param partNumber String ������ @
     * @return ListRoutePartLinkIfc ·�߱����������ֵ����
     */
    public ListRoutePartLinkIfc getListRoutePartLink(String routeListID, String partMasterID, String partNumber)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListID);
        query.addCondition(cond);
        query.addAND();
        //�п������δʹ��·�ߡ�
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(cond1);
        query.addAND();
        //������ɾ��״̬�Ĺ�����
        QueryCondition cond2 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond2);
        query.addAND();
        QueryCondition cond3;
        if(partNumber != null)
        {
            cond3 = new QueryCondition("parentPartNum", QueryCondition.EQUAL, partNumber);
        }else
        {
            cond3 = new QueryCondition("parentPartNum", QueryCondition.IS_NULL);
        }
        query.addCondition(cond3);
        Collection coll = pservice.findValueInfo(query);
        if(coll.size() > 1)
        {
            throw new QMException("partMasterID��listID��õĹ�����Ӧ�ó���һ����");
        }
        Iterator iter = coll.iterator();
        ListRoutePartLinkIfc linkInfo = null;
        if(iter.hasNext())
        {
            linkInfo = (ListRoutePartLinkIfc)iter.next();
        }
        return linkInfo;
    }

    /*
     * ��������,����ԭ����·��״̬Ϊȡ���� ���޸� �޸��ߣ�skybird 2005.1.29 �޸�˵�������������ϵͳִ�й����л����쳣�� ��˼Ӹ�����arg4��������޸ĵ����˳����߼��ĸı䣬 ��֪���Ƿ�Ϻ�����Ĺ涨��
     */
    private void listPostProcess(String routeListMasterID, String routeListID, String partMasterID, String arg4)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's listProcess " + routeListMasterID + " " + routeListID + " " + partMasterID);
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);//ListRoutePartLink
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.NOT_EQUAL, routeListID);
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("routeListMasterID", QueryCondition.EQUAL, routeListMasterID);
        query.addCondition(cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(cond3);
        /**
         * �����޸ģ�skybird ʱ�䣺2005.1.24
         */
        //begin
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(cond4);
        //end
        /*
         * �����޸ģ�skybird 2005.1.29
         */
        //begin
        query.addAND();
        QueryCondition cond5 = new QueryCondition("initialUsed", QueryCondition.EQUAL, arg4);
        query.addCondition(cond5);
        //end
        Collection coll = pservice.findValueInfo(query);
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's listProcess coll = " + coll);
            //һ���������·�ߣ���ʹԭ���������û����·�ߣ�ҲҪ������Ϊȡ��������getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)Ҫ�����⡣
        }
        if(coll.size() > 1)
        {
            throw new QMException("routeService's listPostProcess : ��õĹ�����Ӧ���ж���� " + coll.size());
        }
        Iterator iter = coll.iterator();
        if(iter.hasNext())
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            if(VERBOSE)
            {
                System.out.println("���ù���ǰ " + linkInfo.getBsoID() + "��״̬Ϊ==" + linkInfo.getAdoptStatus());
            }
            linkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
            pservice.saveValueInfo(linkInfo);
            if(VERBOSE)
            {
                System.out.println("���ù�����2222222222222 " + linkInfo.getBsoID() + "��״̬Ϊ==" + linkInfo.getAdoptStatus());

            }
        }
    }

    /**
     * ���湤��·�߱༭ͼ�� ·�ߴ�������� ����·�ߴ��Ĺ���Ϊ·�ߵ�λ�ڵ㣬һ����λ������һ��·�ߴ��г��ֶ�Ρ�·�ߴ��а����ӹ���λ��װ�䵥λ������·�ߴ��Ĺ��ɱ���������й��� 1. װ�䵥λ��һ��·�ߴ���ֻ����һ������ֻ�������һ���ڵ㣻 2. һ����λ�����һ��·�ߴ��г��ֶ�Σ�������ǲ�ͬ���͵Ľڵ�(�����װ��)�������������ڵ�λ�ó��֡� �� *
     * ���һ��·�ߴ�������˶��װ��ڵ㣬����ʾ��Ӧ����Ϣ�� �� * ���װ��ڵ㲻�����ڵ㣬����ʾ��Ӧ����Ϣ�� �� * ���·�ߴ��д������ڵ�ͬ���ͽڵ㣬����ʾ��Ӧ����Ϣ�� ע�⣺����ع���
     * @param listLinkInfo ListRoutePartLinkIfc ·�����������ֵ����
     * @param routeRelaventObejts HashMap key: TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME<br> value:RouteNodeInfo·�߽ڵ�ֵ�����RouteNodeLinkInfo·�߽ڵ����ֵ���� @
     * @see ListRoutePartLinkInfo
     */
    // �ͻ����ڽ��б���ʱ��Ӧ�����ж�ListRoutePartLinkIfc��getAlterStatus()����=0��ȫ�����ó��½�״̬��=1����������
    public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc listLinkInfo, HashMap routeRelaventObejts)throws QMException
    {
        TechnicsRouteIfc routeinfo = null;
        try
        {
            if(VERBOSE)
            {
                System.out.println("���¹���·�ߣ�enter the TechnicsRouteServiceEJB:saveRoute():2135");
            }
            routeinfo = SaveRouteHandler.doSave(listLinkInfo, routeRelaventObejts);
            if(VERBOSE)
            {
                System.out.println("exit the TechnicsRouteServiceEJB:saveRoute():2138");
            }
        }catch(QMException exception)
        {
            this.setRollbackOnly();
            throw new QMException(exception);
        }
        return routeinfo;
    }

    /**
     *�����ʷ·�߱������·��ID return true:ɾ����ʷ�汾������·��;false:��ɾ�� CR7
     */
    private boolean isDeleteHistoryRoute(ListRoutePartLinkIfc linkInfo)throws QMException
    {

        boolean flag = false;

        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, linkInfo.getRouteID());
        query.addCondition(cond);

        Collection coll = pservice.findValueInfo(query);
        if(coll != null && coll.size() > 1)
        {

            flag = false;
        }else
        {

            flag = true;

        }
        return flag;

    }

    /**
     * ɾ������·�ߡ�
     * @param linkInfo ListRoutePartLinkIfc ·�����������ֵ���� @
     * @see ListRoutePartLinkInfo
     */
    //  ���õ��˷����ĵط���
    //   * 1.ɾ��������������˴˷���
    //   * 2.�༭·�����ɾ��һ���㲿����·��Ҳ�����˴˷�����

    public void deleteRoute(ListRoutePartLinkIfc linkInfo)throws QMException
    {
        //        if(linkInfo.getRouteID() == null || linkInfo.getRouteID().trim().length() == 0)
        //        {
        //            throw new QMException("�����û��·�ߣ�����ɾ��");
        //        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        boolean flag = isDeleteHistoryRoute(linkInfo);//CR7

        /*
         * ������ж����ã�������ʲô�û�û����ã�-----2005.1.31-skybird ���뵽ɾ��һ��·�ߵ�ʱ��ֻ�ܶԴ�汾������С�汾����ɾ�������� ���������в�ͬ��״̬�������ã����Ի��д��жϡ�
         */
        /*
         * int =0 ��ʾ�Ǵ���һ�汾�̳������ģ�int=1�� �ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ�int=2�� �˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
         */

        if(linkInfo.getAlterStatus() == ROUTEALTER)
        {//1
            if(flag)
            {//Begin CR7
                //ɾ��·�߰�������
                deleteContainedObjects(linkInfo.getRouteID());
                //ɾ��·�߱���
                pservice.deleteBso(linkInfo.getRouteID());
            }//End CR7
        }
        //    else if (linkInfo.getAlterStatus() == INHERIT) {//0              //Begin CR6
        //      //������һ������·��Ϊȡ��״̬��
        //      QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        //      QueryCondition cond = new QueryCondition("alterStatus",
        //                                               QueryCondition.EQUAL, ROUTEALTER);
        //      query.addCondition(cond);
        //      query.addAND();
        //      QueryCondition cond1 = new QueryCondition(RIGHTID,
        //                                                QueryCondition.EQUAL,
        //                                                linkInfo.getPartMasterID());
        //      query.addCondition(cond1);
        //      query.addAND();
        //      QueryCondition cond2 = new QueryCondition("routeListMasterID",
        //                                                QueryCondition.EQUAL,
        //                                                linkInfo.getRouteListMasterID());
        //      query.addCondition(cond2);
        //      query.addAND();
        //      QueryCondition cond3 = new QueryCondition("adoptStatus",
        //                                                QueryCondition.EQUAL,
        //                                                RouteAdoptedType.ADOPT.toString());
        //      query.addCondition(cond3);
        //      /*
        //        �����޸�--skybird  2005.1.31
        //        �޸ķ�ʽ�����
        //        �޸�Ŀ�ģ��˲�ѯ���������������߼���ɾ��·�ߵĲ���Ӧ��ֻ�Ա�
        //        �汾�Ĺ���״̬����Ӱ�죬���ԼӸ���ѯ������
        //       */
        //      //begin
        //      query.addAND();
        //      QueryCondition cond4 = new QueryCondition("initialUsed",
        //                                                QueryCondition.EQUAL,
        //                                                linkInfo.getInitialUsed());
        //      query.addCondition(cond4);
        //      //end
        //      Collection coll = pservice.findValueInfo(query);
        //      //һ���������·�ߣ���ʹԭ���������û����·�ߣ�ҲҪ������Ϊȡ����
        //      if (coll.size() > 1) {
        //        throw new TechnicsRouteException(
        //            "routeService's listPostProcess : ��õĹ�����Ӧ���ж���� " +
        //            coll.size());
        //      }
        //      Iterator iter = coll.iterator();
        //      
        //         if (iter.hasNext()) {
        //             ListRoutePartLinkIfc linkInfo1 = (ListRoutePartLinkIfc) iter.
        //            next();
        //          linkInfo1.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
        //          pservice.saveValueInfo(linkInfo1);
        //      }
        //     }                                                                      //End CR6

        //���ù����ࡣ
        linkInfo.setRouteID(null);
        linkInfo.setAlterStatus(ROUTEALTER);
        //skybird,2005.1.21,�޸�
        //ԭ�еĴ��룺linkInfo.setAdoptStatus(RouteAdoptedType.ADOPT.getDisplay());
        //added begin
        linkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
        //added end
        pservice.saveValueInfo(linkInfo);
    }

    /**
     * ����ɾ�����·��
     * @param list 20120113 xucy add
     */
    public void deleteRoute(ArrayList list)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        for(int i = 0, j = list.size();i < j;i++)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)pservice.refreshInfo((String)list.get(i));
            this.deleteRoute(linkInfo);
        }
    }

    /**
     * ��ɾ��·�߱�ʱɾ��·����Ϣ. �˷�����ɾ��·����Ϣ���ٱ����޸ĺ�Ĺ�������.
     * @param linkInfo �������� @ CR2
     */

    private void deleteRouteForDeleteListen(ListRoutePartLinkIfc linkInfo)throws QMException
    {
        if(linkInfo.getRouteID() == null || linkInfo.getRouteID().trim().length() == 0)
        {
            throw new QMException("�����û��·�ߣ�����ɾ��");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        if(linkInfo.getAlterStatus() == ROUTEALTER)
        {
            // ɾ��·�߰�������
            deleteContainedObjects(linkInfo.getRouteID());
            // ɾ��·�߱���
            pservice.deleteBso(linkInfo.getRouteID());
        }

    }

    /**
     * @roseuid 4030B10E02D9
     * @J2EE_METHOD -- deleteNode ɾ���ڵ�ID.��������ɳ־û�ɾ��������ԣ���
     */
    private void deleteNode(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //�ɳ־û�ɾ��node_node_link./////////////����ԡ�
        //�Լ�ɾ��
        QMQuery query2 = new QMQuery(ROUTENODE_LINKBSONAME);
        QueryCondition cond2 = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query2.addCondition(cond2);
        Collection coll2 = pservice.findValueInfo(query2);
        //ɾ�����нڵ������
        for(Iterator iter2 = coll2.iterator();iter2.hasNext();)
        {
            pservice.deleteValueInfo((BaseValueIfc)iter2.next());
        }

        QMQuery query = new QMQuery(ROUTENODE_BSONAME);
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        //ɾ�����нڵ㡣
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            pservice.deleteValueInfo((BaseValueIfc)iter.next());
        }

    }

    /**
     * ��ɾ����ÿ�θ���֮ǰ����ɾ���� (�˷���ѭ������sql��ѯ��Ч���д�����)
     * @param routeID String ·��ID @
     */
    public void deleteBranchRelavent(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        //ɾ��BranchNodeLink��Branch
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)iter.next();
            QMQuery branchQuery = new QMQuery(BRANCHNODE_LINKBSONAME);
            QueryCondition cond2 = new QueryCondition(LEFTID, QueryCondition.EQUAL, branch.getBsoID());
            branchQuery.addCondition(cond2);
            Collection coll2 = pservice.findValueInfo(branchQuery); //�ҵ����й���
            for(Iterator iter2 = coll2.iterator();iter2.hasNext();)
            {
                pservice.deleteValueInfo((BaseValueIfc)iter2.next());
            }
            pservice.deleteValueInfo(branch);
        }

    }

    /**
     * ɾ��·��ʱ���������⣬�־û���ɾ����Ӧ�Ĺ���������ʱ����ɾ��������
     * @roseuid 4030B1D703B4
     * @J2EE_METHOD -- deleteRouteListener ɾ������·�߽ڵ㼰�������
     */
    public void deleteContainedObjects(String routeID)throws QMException
    {
        //ɾ��·�߷�֦��
        deleteBranchRelavent(routeID);
        //ɾ��·�߽ڵ㡣
        deleteNode(routeID);

    }

    /**
     * ��ֻ���½ڵ�λ��ʱ�����ô˷�����������·�ߵĸ��¡�
     * @param coll Collection RouteNodeIfc·�߽ڵ�ֵ���󼯺� @
     */
    public void saveOnlyNodes(Collection coll)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's saveOnlyNodes " + coll);
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        if(coll.size() > 0 && !(coll.iterator().next() instanceof RouteNodeIfc))
        {
            throw new QMException("�������ͱ���ΪRouteNodeIfc��");
        }
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            BaseValueIfc nodeInfo = (BaseValueIfc)iter.next();
            //ֻ���Ǹ���·�߽ڵ㡣
            pservice.updateValueInfo(nodeInfo);
        }
    }

    ////////////·�߼���ڵ�Ĵ��������¡�ɾ��������////////////////

    /**
     * �������ָ����Ʒ�����¹���·�߱�
     * @param productMasterID String ���ڲ�Ʒ @
     * @return TechnicsRouteListInfo ����·�߱�
     */
    public TechnicsRouteListInfo getRouteListByProduct(String productMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getRouteListByProduct productMasterID = " + productMasterID);
        }
        if(productMasterID == null || productMasterID.trim().length() == 0)
        {
            throw new QMException("�����������");
        }
        Object[] obj = new Object[3];
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query1 = new QMQuery("consRouteListProductLink");
        QueryCondition condition1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, productMasterID);
        query1.addCondition(condition1);
        //����RouteListProductLink
        Collection coll = pservice.findValueInfo(query1);
        if(coll != null)
        {
            //�������С�
            Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
            if(VERBOSE)
            {
                System.out.println(TIME + "��ѯ��Ʒ�ṹ��Ӧ�Ĺ����� " + coll);
            }
            Iterator iter = sortedVec.iterator();
            //ֻȡ���´�����·�߱��Ӧ�Ĺ�����
            if(iter.hasNext())
            {
                RouteListProductLinkInfo linkInfo = (RouteListProductLinkInfo)iter.next();
                String routeListMasterID = linkInfo.getRouteListMasterID();
                TechnicsRouteListInfo routeList = (TechnicsRouteListInfo)getLatestVesion(routeListMasterID);
                return routeList;
            }else
            {
                return null;
            }
        }else
        {
            return null;
        }

    }

    /**
     * ���������嵥���㲿��·�� ����û�ѡ���������·�ߣ�ϵͳӦ����û���ǰҪ���������嵥���㲿��������ж�Ӧ�� ����·�߱�(����·�߱��еġ����ڲ�Ʒ�����Թ��������㲿��)����ʹ�ô��㲿�������¹� ��·�߱�Ϊ����·�ߵ�����Դ������û���乤��·�߱����г����㲿����ȡ����ʱ������� ��·�߱��е�·��Ϊ����Դ�������ǰҪ���������嵥���㲿��û�ж�Ӧ�Ĺ���·�߱���
     * ���㲿��ȫ��ȡ����ʱ������µ�·�߱��е�·��Ϊ����Դ�������Ʒ�ṹ�е�ĳЩ�㲿�� û�б��ƹ�����·�ߣ����й�·�ߵ����������ֵ��
     * @param routeList TechnicsRouteListInfo ·�߱�ֵ����
     * @param partMasterID String ���MasterID
     * @param level_zh String ·�߼��� @
     * @return Object[] obj[0]:TechnicsRouteListIfc, <br>obj[1],obj[2] ��getRouteAndBrach(routeID), <br>obj[3]:ListRoutePartLinkIfc��
     * @see TechnicsRouteListInfo
     */
    public Object[] getMaterialBillRoutes(TechnicsRouteListInfo routeList, String partMasterID, String level_zh)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getMaterialBillRoutes routeList = " + routeList + "; partMasterID = " + partMasterID + "; level_zh = " + level_zh);
        }
        Object[] obj = new Object[4];
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //���ҵ�ǰ·�߱���partMasterID�Ĺ���
        QMQuery query2 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition condition2 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query2.addCondition(condition2);
        query2.addAND();
        QueryCondition condition3 = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeList.getBsoID());
        query2.addCondition(condition3);
        query2.addAND();
        int i = query2.appendBso(ROUTELIST_BSONAME, false);
        QueryCondition cond1 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, level_zh);
        query2.addCondition(i, cond1);
        query2.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query2.addCondition(0, cond4);
        query2.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query2.addCondition(0, cond5);
        query2.addAND();
        QueryCondition cond6 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query2.addCondition(0, cond6);
        if(VERBOSE)
        {
            System.out.println(query2.getDebugSQL());
        }
        Collection col = pservice.findValueInfo(query2);
        if(col != null && col.size() > 0)
        {
            if(col.size() > 1)
            {
                throw new QMException("��Ӧ�ò�������¼��");
            }
            ListRoutePartLinkIfc listPartLinkInfo = (ListRoutePartLinkIfc)col.toArray()[0];
            String routeID = listPartLinkInfo.getRouteID();
            obj[0] = routeList;
            Object[] route = getRouteAndBrach(routeID);
            obj[1] = route[0];
            obj[2] = route[1];
            obj[3] = listPartLinkInfo;
        }else
        { //����û���乤��·�߱����г����㲿����ȡ����ʱ������µ�·�߱��е�·��Ϊ����Դ
            obj = getProductStructRoutes(partMasterID, level_zh);
        }
        if(VERBOSE)
        {
            System.out.println(TIME + "exit... routeService's getMaterialBillRoutes " + obj);
        }
        return obj;
    }

    /**
     * ����Ʒ�ṹ����·�߱���
     * @param partMasterID String
     * @param level_zh String ·�߼��� @
     * @return Object[] obj[0]: TechnicsRouteListIfc,<br> obj[1], obj[2]��getRouteAndBrach(routeID), <br>obj[3]:ListRoutePartLinkIfc��
     */
    public Object[] getProductStructRoutes(String partMasterID, String level_zh)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getProductStructRoutes partMasterID = " + partMasterID + ", level_zh = " + level_zh);
        }
        if(partMasterID == null || partMasterID.trim().length() == 0 || level_zh == null || level_zh.trim().length() == 0)
        {
            throw new QMException("�����������");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = getLinkQuery(partMasterID, level_zh);
        //����ListRoutePartLinkIfc
        Collection coll = pservice.findValueInfo(query);
        //�������С�
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
        if(VERBOSE)
        {
            System.out.println(TIME + "��ѯ��Ʒ�ṹ��Ӧ�Ĺ����� " + coll);
        }
        Object[] obj = new Object[4];
        Iterator iter = sortedVec.iterator();
        //ֻȡ���´�����·�߱��Ӧ�Ĺ�����
        if(iter.hasNext())
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            String routeListID = linkInfo.getRouteListID();
            String routeID = linkInfo.getRouteID();
            TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
            obj[0] = listInfo;
            Object[] route = getRouteAndBrach(routeID);
            obj[1] = route[0];
            obj[2] = route[1];
            obj[3] = linkInfo;
        }
        if(VERBOSE)
        {
            System.out.println(TIME + "exit... routeService's getProductStructRoutes " + obj);
        }
        return obj;
    }

    /**
     * �������masterID��·�߼����������ǲ���״̬�Ĺ�����Ĳ�ѯ������
     * @param partMasterID String
     * @param level_zh String ·�߼��� @
     * @return QMQuery
     */
    private QMQuery getLinkQuery(String partMasterID, String level_zh)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("skybird-------TRS:QMQuery():������level_zh" + level_zh);
        }
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int i = query.appendBso(ROUTELIST_BSONAME, false);
        /*
         * if(departmentID != null && departmentID.trim().length() !=0) { level = RouteListLevelType.SENCONDROUTE.toString(); QueryCondition cond1 = new QueryCondition("routeListDepartment",
         * QueryCondition.EQUAL, departmentID); query.addCondition(i, cond1); query.addAND(); } else level = RouteListLevelType.FIRSTROUTE.toString();
         */
        String level = RouteHelper.getValue(RouteListLevelType.getRouteListLevelTypeSet(), level_zh);
        QueryCondition cond1 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, level);
        query.addCondition(i, cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition(LEFTID, "bsoID");
        query.addCondition(0, i, cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond4);
        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(0, cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(0, cond6);
        //�������С�
        //query.addOrderBy(i, "createTime", true);
        ///////////////////////////
        query.setDisticnt(true);
        return query;
    }

    /**
     * �����ӵķ���-------skybird 2005.1.24 �������������һ����������󲿷��ظ�����
     * @param partMasterID �㲿������ID
     * @param level_zh ·�߱�ļ���
     * @param depart ��������·������Ӧ��·�߼���
     */
    private QMQuery getLinkQuery2(String partMasterID, String level_zh, String depart)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("skybird-------TRS:QMQuery2():������level_zh" + level_zh);
            System.out.println("skybird-------TRS:QMQuery2():������depart" + depart);
        }
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int i = query.appendBso(ROUTELIST_BSONAME, false);
        String level = RouteHelper.getValue(RouteListLevelType.getRouteListLevelTypeSet(), level_zh);
        QueryCondition cond1 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, level);
        query.addCondition(i, cond1);
        query.addAND();
        QueryCondition cond7 = new QueryCondition("routeListDepartment", QueryCondition.EQUAL, depart);
        query.addCondition(i, cond7);
        query.addAND();
        QueryCondition cond2 = new QueryCondition(LEFTID, "bsoID");
        query.addCondition(0, i, cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond4);
        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(0, cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(0, cond6);
        //�������С�
        //query.addOrderBy(i, "createTime", true);
        ///////////////////////////
        query.setDisticnt(true);
        return query;
    }

    //22. �鿴�㲿���Ĺ���·��
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC322

    /**
     * �������ID�͹���·�߼����ö�Ӧ�Ĺ���·�ߡ�
     * @param partMasterID String
     * @param level_zh String ·�߼��� @
     * @return Collection ���鼯�ϡ�obj[0]: TechnicsRouteListIfc, <br> obj[1],obj[2]��getRouteAndBrach(routeID),<br> obj[3]:linkInfo��
     */
    public Collection getPartLevelRoutes(String partMasterID, String level_zh)throws QMException
    {
        if(partMasterID == null || partMasterID.trim().length() == 0 || level_zh == null || level_zh.trim().length() == 0)
        {
            throw new QMException("�����������");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int i = query.appendBso(ROUTELIST_BSONAME, false);
        int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
        /*
         * String level = null; if(departmentID != null && departmentID.trim().length() != 0) { level = RouteListLevelType.SENCONDROUTE.toString(); QueryCondition cond = new
         * QueryCondition("routeListDepartment", QueryCondition.EQUAL, departmentID); query.addCondition(i, cond); query.addAND(); } else { level = RouteListLevelType.FIRSTROUTE.toString(); }
         */
        String level = RouteHelper.getValue(RouteListLevelType.getRouteListLevelTypeSet(), level_zh);
        QueryCondition cond1 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, level);
        query.addCondition(i, cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition(LEFTID, "bsoID");
        query.addCondition(0, i, cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond4);

        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(0, cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
        query.addCondition(0, j, cond6);
        query.addAND();
        //zz added start (������)
        QueryCondition condx = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(0, condx);
        //zz added end (������)

        //·���޸�ʱ�併�����С�
        //query.addOrderBy(j, "modifyTime", true);
        //SQL������������
        query.setDisticnt(true);
        //����ListRoutePartLinkIfc
        Collection coll = pservice.findValueInfo(query);
        //·���޸�ʱ�併�����С�
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
        Collection result = new Vector();
        for(Iterator iter = sortedVec.iterator();iter.hasNext();)
        {
            Object[] obj = new Object[4];
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            String routeListID = linkInfo.getRouteListID();
            String routeID = linkInfo.getRouteID();
            TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
            obj[0] = listInfo;
            Object[] route = getRouteAndBrach(routeID);
            obj[1] = route[0];
            obj[2] = route[1];
            obj[3] = linkInfo;
            result.add(obj);
        }
        return result;
    }

    /**
     * �����㲿������·��
     * @param partMasterID String �㲿��bsoid @
     * @return Collection ���鼯�ϡ�String[] {partID, routeID, routeState, linkID, state, parentPartNum});
     */
    public Collection getPartRoutes(String partMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getPartLevelRoutes, partMasterID = " + partMasterID);
        }
        if(partMasterID == null || partMasterID.trim().length() == 0)
        {
            throw new QMException("�����������");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond4);
        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(0, cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
        query.addCondition(0, j, cond6);
        //SQL������������
        query.setDisticnt(true);
        //����ListRoutePartLinkIfc
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's getPartLevelRoutes SQL = " + query.getDebugSQL());
        }
        Collection coll = pservice.findValueInfo(query);
        //·���޸�ʱ�併�����С�
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
        if(VERBOSE)
        {
            System.out.println(TIME + "��ѯ���Ϊ�� coll = " + sortedVec.size());
        }
        Collection result = new Vector();
        String partID = "";
        String routeID = "";
        String routeState = "";
        String linkID = "";
        String routelistID = "";
        String state = "";
        String parentPartNum = "";
        String parentPartName = "";
        for(Iterator iter = sortedVec.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            QMPartMasterIfc part = (QMPartMasterIfc)pservice.refreshInfo(partMasterID);
            partID = part.getBsoID();
            routelistID = linkInfo.getLeftBsoID();
            TechnicsRouteListIfc techinicsRoute = (TechnicsRouteListIfc)pservice.refreshInfo(routelistID);
            state = techinicsRoute.getRouteListState();
            routeID = linkInfo.getRouteID();
            routeState = linkInfo.getAdoptStatus();
            linkID = linkInfo.getBsoID();
            parentPartNum = linkInfo.getParentPartNum();
            result.add(new String[]{partID, routeID, routeState, linkID, state, parentPartNum});
        }
        if(VERBOSE)
        {
            System.out.println(TIME + "exit... routeService's getPartLevelRoutes " + result);
        }
        return result;
    }

    //////17.���ƹ���·��
    //�汾 <v2.0>
    //�ĵ���ţ�PHOS-CAPP-UC317

    /**
     * ���ƹ���·�� �����ԡ���������·�߱��е�·�߽��и��ơ����"����"·�߱��������partMasterID���õ�·�ߡ� ע�⣺�������ֻ����һ������ڲ�ͬ·�߱���·�ߵĸ��ơ��������������ĸ���ճ���� �������ID�͹���·�߼����ö�Ӧ�Ĺ���·�ߡ� ִ���߸���һ������·�ߣ�ճ����û�й���·�ߵ��㲿���С�
     * ���ƹ���·��ʱ�����Դӵ�ǰ·�߱��е�һ���㲿���Ĺ���·�߸��ƣ�Ҳ���Դ�һ���㲿��������·�߱���ƵĹ���·�߸��ƣ� ճ��ʱ������ճ������ǰѡ�е��㲿����Ҳ����ճ������·�߱���������·�ߵ��㲿��������㲿������·�ߣ�ʹ��"ճ����"����·��ʱ�����ܸ��Ƶ���Щ�㲿����
     * @param linkInfo1 ListRoutePartLinkIfc @
     * @return Collection ���鼯�ϡ�obj[0]: TechnicsRouteListIfc, <br> obj[1],obj[2]��getRouteAndBrach(routeID)��
     * @see ListRoutePartLinkInfo
     */
    public Collection getAdoptRoutes(ListRoutePartLinkIfc linkInfo1)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getAdoptRoutes partMasterID = " + linkInfo1.getPartMasterID());
            /*
             * if(linkInfo1.getRouteID() != null && linkInfo1.getRouteID().trim().length() !=0) throw new TechnicsRouteException("4", null);
             */
        }
        String depart = "";
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        TechnicsRouteListIfc listInfo1 = (TechnicsRouteListIfc)pservice.refreshInfo(linkInfo1.getRouteListID());
        String level = RouteHelper.getValue(RouteListLevelType.getRouteListLevelTypeSet(), listInfo1.getRouteListLevel());
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        /////////����·�߼�����й���///////
        int i = query.appendBso(ROUTELIST_BSONAME, false);
        QueryCondition cond0 = new QueryCondition(LEFTID, "bsoID");
        query.addCondition(0, i, cond0);
        query.addAND();
        QueryCondition cond1 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, level);
        query.addCondition(i, cond1);
        query.addAND();
        if(listInfo1.getRouteListLevel().equals("����·��"))
        {
            depart = listInfo1.getRouteListDepartment();
            QueryCondition cond7 = new QueryCondition("routeListDepartment", QueryCondition.EQUAL, depart);
            query.addCondition(i, cond7);
            query.addAND();
        }
        QueryCondition cond2 = new QueryCondition("routeListMasterID", QueryCondition.NOT_EQUAL, linkInfo1.getRouteListMasterID());
        query.addCondition(cond2);
        query.addAND();
        if(VERBOSE)
        {
            System.out.println("linkInfo1.routeListMasterID()" + linkInfo1.getRouteListMasterID());
        }
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, linkInfo1.getPartMasterID());
        query.addCondition(cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(cond4);
        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(cond6);
        if(VERBOSE)
        {
            System.out.println("RouteAdoptedType.ADOPT.toString()" + RouteAdoptedType.ADOPT.toString());
        }
        //�������С�
        //query.addOrderBy("createTime", true);
        //////////////////
        query.setDisticnt(true);
        //����ListRoutePartLinkIfc
        Collection coll = pservice.findValueInfo(query);
        if(VERBOSE)
        {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&" + query.getDebugSQL());
        }
        //�������С�
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
        Collection result = new Vector();
        for(Iterator iter = sortedVec.iterator();iter.hasNext();)
        {
            Object[] obj = new Object[3];
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            String routeListID = linkInfo.getRouteListID();
            String routeID = linkInfo.getRouteID();
            TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
            obj[0] = listInfo;
            Object[] route = getRouteAndBrach(routeID);
            obj[1] = route[0];
            obj[2] = route[1];
            result.add(obj);
        }

        if(VERBOSE)
        {
            System.out.println(TIME + "exit... routeService's getAdoptRoutes " + result);
        }
        return result;
    }

    /**
     * ���ƹ���·��
     * @param routeID String ·��ID: ���ø���routeID,�����Ӧ��·����Ϣ��
     * @param linkInfo ListRoutePartLinkIfc @
     * @return ListRoutePartLinkIfc ·�����������
     * @see ListRoutePartLinkInfo
     */
    public ListRoutePartLinkIfc copyRoute(String routeID, ListRoutePartLinkIfc linkInfo)throws QMException
    {
        if(linkInfo.getRouteID() != null && linkInfo.getRouteID().trim().length() != 0)
        {
            throw new QMException("com.faw_qm.technics.consroute.util.RouteResource", "4", null);
        }
        //���ø���routeID,�����Ӧ��·����Ϣ��
        HashMap routeRelaventObejts = getRouteContainer(routeID, null);
        //����·��
        saveRoute(linkInfo, routeRelaventObejts);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        ListRoutePartLinkIfc linkInfo1 = (ListRoutePartLinkIfc)pservice.refreshInfo(linkInfo.getBsoID());
        return linkInfo1;
        //��һ�ַ�ʽ��������saveRoute���Լ�����
    }

    /**
     * ����·����ض���ע�Ᵽ֤˳��
     * @param routeID String
     * @param branchInfos Collection �б仯�ķ�ֵ֧���� @
     * @return HashMap <br> key:TECHNICSROUTE_BSONAME��ROUTENODE_LINKBSONAME��BRANCHNODE_LINKBSONAME �� TECHNICSROUTEBRANCH_BSONAME��ROUTENODE_BSONAME <br>
     * value:TechnicsRouteIfc·��ֵ����RouteNodeLinkIfc�ڵ������RouteBranchNodeLinkIfc��֧��ڵ������ branchItemVec��֧���ϣ�nodeItemVec�ڵ㼯��
     */
    public HashMap getRouteContainer(String routeID, Collection branchInfos)throws QMException
    {
        HashMap relaventObejts = new HashMap();
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //1.���·�ߵ�HashMap��
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)pservice.refreshInfo(routeID);
        setupNewBaseValueIfc(routeInfo);
        RouteItem item = createRouteItem(routeInfo);
        relaventObejts.put(TECHNICSROUTE_BSONAME, item);
        //��ýڵ㼰�ڵ������
        Object[] obj1 = getRouteNodeAndNodeLink(routeID);
        //�ڵ㼯�ϡ�
        Collection nodes = (Collection)obj1[0];
        //�ڵ�������ϡ�
        Collection nodeLinks = (Collection)obj1[1];
        //2.��ӽڵ�������ϵ�HashMap
        //�����еĶ���ΪRouteItem.
        Collection nodeLinkItemVec = new Vector();
        for(Iterator iter1 = nodeLinks.iterator();iter1.hasNext();)
        {
            RouteNodeLinkIfc nodeLinkInfo = (RouteNodeLinkIfc)iter1.next();
            //���ù���Դ�ڵ㡣
            RouteNodeIfc sourceNode = nodeLinkInfo.getSourceNode();
            RouteNodeIfc node1 = (RouteNodeIfc)getTheSameInfo(sourceNode, nodes);
            nodeLinkInfo.setSourceNode(node1);
            //���ù���Ŀ�Ľڵ㡣
            RouteNodeIfc destNode = nodeLinkInfo.getDestinationNode();
            RouteNodeIfc node2 = (RouteNodeIfc)getTheSameInfo(destNode, nodes);
            nodeLinkInfo.setDestinationNode(node2);
            RouteItem nodeLinkItem = getNodeLinkItem(nodeLinkInfo, routeInfo);
            nodeLinkItemVec.add(nodeLinkItem);
        }
        relaventObejts.put(ROUTENODE_LINKBSONAME, nodeLinkItemVec);
        //3.��ӷ�֦��ڵ�������ϵ�HashMap
        //������й���·�߷�֧�ͽڵ������ֵ����branchNodeLinks��˳��
        Collection branchNodeLinks = getBranchRouteLinks(routeID);
        Collection branches = getOnlyRouteBranch(routeID);
        Collection branchNodeLinkItemVec = new Vector();
        for(Iterator iter = branchNodeLinks.iterator();iter.hasNext();)
        {
            RouteBranchNodeLinkIfc branchNodeLinkInfo = (RouteBranchNodeLinkIfc)iter.next();
            //��÷�֦item��
            TechnicsRouteBranchIfc branchInfo = branchNodeLinkInfo.getRouteBranchInfo();
            //����ڷ�֦��ڵ�����еķ�֧��branches�Ķ�Ӧֵ��
            TechnicsRouteBranchIfc branch1 = (TechnicsRouteBranchIfc)getTheSameInfo(branchInfo, branches);
            branchNodeLinkInfo.setRouteBranchInfo(branch1);
            RouteNodeIfc branchNode = branchNodeLinkInfo.getRouteNodeInfo();
            //����ڷ�֦��ڵ�����еĽڵ���nodes�Ķ�Ӧֵ��
            RouteNodeIfc node1 = (RouteNodeIfc)getTheSameInfo(branchNode, nodes);
            branchNodeLinkInfo.setRouteNodeInfo(node1);
            RouteItem branchNodeLinkItem = getBranchNodeLinkItem(branchNodeLinkInfo);
            branchNodeLinkItemVec.add(branchNodeLinkItem);
        }
        relaventObejts.put(BRANCHNODE_LINKBSONAME, branchNodeLinkItemVec);
        //4.��ӷ�֧���ϵ�HashMap
        Collection branchItemVec = new Vector();
        for(Iterator iter11 = branches.iterator();iter11.hasNext();)
        {
            TechnicsRouteBranchIfc branchInfo = (TechnicsRouteBranchIfc)iter11.next();
            if(branchInfos != null)
            {
                if(branchInfo.getBsoID() == null)
                {
                    throw new QMException("��ֵ֧��������Ϊ�յ�ʱ�����ԡ�");
                }
                //���ݴ����ֵ֧���󼯺������Ƿ���Ҫ·����Ϣ��
                for(Iterator branchIter = branchInfos.iterator();branchIter.hasNext();)
                {
                    TechnicsRouteBranchIfc paraBranch = (TechnicsRouteBranchIfc)branchIter.next();
                    if(branchInfo.getBsoID().trim().equals(paraBranch.getBsoID().trim()))
                    {
                        branchInfo.setMainRoute(paraBranch.getMainRoute());
                    }
                }
            }
            RouteItem branchItem = getBranchItem(branchInfo, routeInfo);
            branchItemVec.add(branchItem);
        }
        relaventObejts.put(TECHNICSROUTEBRANCH_BSONAME, branchItemVec);
        //5.��ӽڵ㼯�ϵ�HashMap.
        Collection nodeItemVec = new Vector();
        for(Iterator iter2 = nodes.iterator();iter2.hasNext();)
        {
            RouteNodeIfc nodeInfo = (RouteNodeIfc)iter2.next();
            RouteItem nodeItem = getNodeItem(nodeInfo, routeInfo);
            nodeItemVec.add(nodeItem);
        }
        relaventObejts.put(ROUTENODE_BSONAME, nodeItemVec);
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's    VERBOSE begin.....................");
            //TECHNICSROUTE_BSONAME��ROUTENODE_LINKBSONAME��TECHNICSROUTEBRANCH_BSONAME
            //ROUTENODE_BSONAME��BRANCHNODE_LINKBSONAME
            //1.·�ߡ�
            System.out.println("1.route.........................................");
            RouteItem routeItem1 = (RouteItem)relaventObejts.get(TECHNICSROUTE_BSONAME);
            TechnicsRouteIfc route = (TechnicsRouteIfc)routeItem1.getObject();
            System.out.println(TIME + "route.... routeID = " + route.getBsoID());
            System.out.println(TIME + "route.... routehashCode = " + route.hashCode());
            //2.�ڵ㡣
            System.out.println("2.node.........................................");
            Collection nodes1 = (Collection)relaventObejts.get(ROUTENODE_BSONAME);
            for(Iterator iterator = nodes1.iterator();iterator.hasNext();)
            {
                RouteItem item1 = (RouteItem)iterator.next();
                RouteNodeIfc node = (RouteNodeIfc)item1.getObject();
                System.out.println(TIME + "node..... nodeID = " + node.getBsoID() + ", routeID = " + node.getRouteInfo().getBsoID());
                System.out.println(TIME + "node..... hashCode = " + node.getBsoID() + ", routehashCode = " + node.getRouteInfo().hashCode());
            }
            System.out.println("3.nodeLink.........................................");
            //3.�ڵ������
            Collection nodelinks1 = (Collection)relaventObejts.get(ROUTENODE_LINKBSONAME);
            for(Iterator iterator = nodelinks1.iterator();iterator.hasNext();)
            {
                RouteItem item1 = (RouteItem)iterator.next();
                RouteNodeLinkIfc nodeLink = (RouteNodeLinkIfc)item1.getObject();
                System.out.println(TIME + "nodeLink..... nodeLinkID = " + nodeLink.getBsoID() + ", routeID = " + nodeLink.getRouteInfo().getBsoID() + ", sourceNodeID = "
                        + nodeLink.getSourceNode().getBsoID() + nodeLink.getDestinationNode().getBsoID());
                System.out.println(TIME + "nodeLink..... nodeLinkHashCode = " + nodeLink.hashCode() + ", routeHashCode = " + nodeLink.getRouteInfo().hashCode() + ", sourceNodeHashCode = "
                        + nodeLink.getSourceNode().hashCode() + nodeLink.getDestinationNode().hashCode());
            }
            //4.��֧
            System.out.println("4.branch.........................................");
            Collection branchs1 = (Collection)relaventObejts.get(TECHNICSROUTEBRANCH_BSONAME);
            for(Iterator iterator = branchs1.iterator();iterator.hasNext();)
            {
                RouteItem item1 = (RouteItem)iterator.next();
                TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)item1.getObject();
                System.out.println(TIME + "branch..... brachID = " + branch.getBsoID() + ", routeID = " + branch.getRouteInfo().getBsoID());
                System.out.println(TIME + "branch..... brachHashCode = " + branch.hashCode() + ", routeHashCode = " + branch.getRouteInfo().hashCode());
            }
            //5.��֦������
            System.out.println("5.branchNodeLink.........................................");
            Collection brancheNodes1 = (Collection)relaventObejts.get(BRANCHNODE_LINKBSONAME);
            for(Iterator iterator = brancheNodes1.iterator();iterator.hasNext();)
            {
                RouteItem item1 = (RouteItem)iterator.next();
                RouteBranchNodeLinkIfc branchNode = (RouteBranchNodeLinkIfc)item1.getObject();
                System.out.println(TIME + "branchNode..... branchNodeID = " + branchNode.getBsoID() + ", branchID = " + branchNode.getRouteBranchInfo().getBsoID() + ", nodeID = "
                        + branchNode.getRouteNodeInfo().getBsoID());
                System.out.println(TIME + "branchNode..... branchNodeHashCode = " + branchNode.hashCode() + ", branchHashCode = " + branchNode.getRouteBranchInfo().hashCode() + ", nodeHashCode = "
                        + branchNode.getRouteNodeInfo().hashCode());
            }
            System.out.println(TIME + "routeService's getRouteContainer VERBOSE end.....................");
        }
        return relaventObejts;
    }

    private Collection getOnlyRouteBranch(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    //����ڷ�֦��ڵ�����еĽڵ���nodes�Ķ�Ӧֵ��
    private BaseValueIfc getTheSameInfo(BaseValueIfc node, Collection nodes)throws QMException
    {
        for(Iterator iter = nodes.iterator();iter.hasNext();)
        {
            BaseValueIfc nodeInfo = (BaseValueIfc)iter.next();
            if(node.getBsoID().equals(nodeInfo.getBsoID()))
            {
                return nodeInfo;
            }
        }
        throw new QMException("�ڵ㷶Χ����");
        //   return null;
    }

    /**
     * @roseuid 4039932300E0
     * @J2EE_METHOD -- getBranchRouteNodes ���ݹ���·�߷�֦ID��ù���·�߽ڵ㡣
     * @return Collection ����·�߷�֧�ͽڵ������ֵ����
     */
    private Collection getBranchRouteLinks(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(BRANCHNODE_LINKBSONAME);
        int i = query.appendBso(TECHNICSROUTEBRANCH_BSONAME, false);
        QueryCondition cond = new QueryCondition(LEFTID, "bsoID");
        query.addCondition(0, i, cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(i, cond1);
        //���������С�
        //query.addOrderBy(0, "bsoID", false);
        query.setDisticnt(true);
        Collection coll = pservice.findValueInfo(query);
        //���������С�
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getBsoID", false);
        return sortedVec;
    }

    private RouteItem getNodeItem(RouteNodeIfc nodeInfo, TechnicsRouteIfc routeInfo)
    {
        setupNewBaseValueIfc(nodeInfo);
        nodeInfo.setRouteInfo(routeInfo);
        RouteItem item = createRouteItem(nodeInfo);
        return item;
    }

    private RouteItem getNodeLinkItem(RouteNodeLinkIfc nodeLinkInfo, TechnicsRouteIfc routeInfo)
    {
        setupNewBaseValueIfc(nodeLinkInfo);
        nodeLinkInfo.setRouteInfo(routeInfo);
        RouteItem item = createRouteItem(nodeLinkInfo);
        return item;
    }

    private RouteItem getBranchItem(TechnicsRouteBranchIfc branchInfo, TechnicsRouteIfc routeInfo)
    {
        setupNewBaseValueIfc(branchInfo);
        branchInfo.setRouteInfo(routeInfo);
        RouteItem item = createRouteItem(branchInfo);
        return item;
    }

    private RouteItem getBranchNodeLinkItem(RouteBranchNodeLinkIfc branchNodeInfo)
    {
        setupNewBaseValueIfc(branchNodeInfo);
        RouteItem item = createRouteItem(branchNodeInfo);
        return item;
    }

    private void setupNewBaseValueIfc(BaseValueIfc info)
    {
        info.setBsoID(null);
        info.setCreateTime(null);
        info.setModifyTime(null);
    }

    private RouteItem createRouteItem(BaseValueIfc info)
    {
        RouteItem item = new RouteItem();
        item.setObject(info);
        item.setState(RouteItem.CREATE);
        return item;
    }

    /////////////////////���ƽ�����////////////////////////////

    /////////////////////����Ŀ������ʼ////////////////////////////

    /**
     * ����·�߻�õ�λ�����
     * @param routeListID String ·�߱�ID ����·�߱�ID��õ�λ @
     * @return HashMap key:��λֵ���� ; value:���masterInfo����
     */
    public HashMap getDepartmentAndPartByList(String routeListID)throws QMException
    {
        HashMap departmentAndPart = new HashMap();
        Collection departments = getDepartments(routeListID);
        for(Iterator iter = departments.iterator();iter.hasNext();)
        {
            BaseValueIfc code = (BaseValueIfc)iter.next();
            Collection parts = getParts(code.getBsoID());
            departmentAndPart.put(code, parts);
        }
        return departmentAndPart;
    }

    /**
     * ͨ����λ����ID��ö�Ӧ��Ҫ�๤�յ������ͨ������ɻ�������Ϣ�� ��������·�ߵ���Ϣ���˷������ɹ��ղ��ֵ��á�
     * @param departmentID String ��λID @
     * @return Collection ���masterInfo����,�μ�linkInfo.getPartMasterID();��
     */
    // ע�⣺��λ�нṹ������ʱ���ӽڵ�ҲҪ�����������ݲ�����
    public Collection getParts(String departmentID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int i = query.appendBso(ROUTENODE_BSONAME, false);
        QueryCondition cond2 = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, departmentID);
        query.addCondition(i, cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond5 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(0, cond5);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("routeID", "routeID");
        query.addCondition(0, i, cond4);
        Collection linkVec = pservice.findValueInfo(query);
        Collection partMasterIDVec = new Vector();
        for(Iterator iter = linkVec.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            String partMasterID = linkInfo.getPartMasterID();
            if(!partMasterIDVec.contains(partMasterID))
            {
                partMasterIDVec.add(partMasterID);
            }
        }
        Collection partMasterInfoVec = new Vector();
        for(Iterator iter1 = partMasterIDVec.iterator();iter1.hasNext();)
        {
            String bsoID = (String)iter1.next();
            BaseValueIfc info = pservice.refreshInfo(bsoID);
            partMasterInfoVec.add(info);
        }
        return partMasterInfoVec;
    }

    /**
     * ���ݹ���·�߻��������ĵ�λ����������ķַ���
     * @param routeListID String @
     * @return Collection CodingIfc��CodingClassificationIfc
     */
    public Collection getDepartments(String routeListID)throws QMException
    {
        Collection codeVec = getDepartmentIDByList(routeListID);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        Collection codeInfoVec = new Vector();
        for(Iterator iter1 = codeVec.iterator();iter1.hasNext();)
        {
            String bsoID = (String)iter1.next();
            BaseValueIfc info = pservice.refreshInfo(bsoID);
            codeInfoVec.add(info);
        }
        return codeInfoVec;
    }

    /**
     * ���ݹ���·�߻��������ĵ�λID��
     * @param routeListID String
     * @return Collection bsoID.
     */
    private Collection getDepartmentIDByList(String routeListID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTENODE_BSONAME);
        int i = query.appendBso(LIST_ROUTE_PART_LINKBSONAME, false);
        //ROUTENODE_BSONAME, TECHNICSROUTE_BSONAME,LIST_ROUTE_PART_LINKBSONAME
        QueryCondition cond2 = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListID);
        query.addCondition(i, cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(i, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("routeID", "routeID");
        query.addCondition(0, i, cond4);
        Collection nodes = pservice.findValueInfo(query);
        Collection codeVec = new Vector();
        for(Iterator iter = nodes.iterator();iter.hasNext();)
        {
            RouteNodeIfc nodeInfo = (RouteNodeIfc)iter.next();
            String departmentID = nodeInfo.getNodeDepartment();
            if(!codeVec.contains(departmentID))
            {
                codeVec.add(departmentID);
            }
        }
        return codeVec;
    }

    /////////////////////����Ŀ��������////////////////////////////

    /**
     * ����routeMaster������еĴ�汾��Ӧ������С�汾��
     * @param listMasterInfo TechnicsRouteListMasterIfc @
     * @return Collection ��ŵ���Object[]��<br> obj[0]:���еĴ�汾��Ӧ������С�汾��<br> ��config���ˡ�
     * @see TechnicsRouteListMasterInfo
     */
    //�汾�ṩ�ķ�����allVersionsOf(MasteredIfc mastered).
    public Collection getAllVersionLists(TechnicsRouteListMasterIfc listMasterInfo)throws QMException
    {
        //PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        ConfigService cservice = (ConfigService)EJBServiceHelper.getService(CONFIG_SERVICE);
        Vector listVec = new Vector();
        Collection coll1 = cservice.filteredIterationsOf(listMasterInfo, new MultipleLatestConfigSpec());
        for(Iterator iter1 = coll1.iterator();iter1.hasNext();)
        {
            Object[] obj = (Object[])iter1.next();
            listVec.addElement(obj[0]);
        }
        return listVec;
    }

    /**
     * �ṩ����ıȽϡ�
     * @param iteratedVec Collection ��ͬ�汾�Ĺ���·�߱�ֵ���󼯺ϡ� @
     * @return Map key �� partMasterID, value �� Collection:listRoutePartLinkIfc���ϡ�����˳�򣬰汾���������С�
     */
    public Map compareIterate(Collection iteratedVec)throws QMException
    {
        boolean isCommonSort = Boolean.valueOf(RemoteProperty.getProperty("com.faw_qm.technics.consroute.isCommonCompare", "true")).booleanValue();
        if(VERBOSE)
        {
            System.out.println("enter the class:TechnicsRouteServiceEJB,method:compareIterate()" + isCommonSort);
            System.out.println("�����Ĳ�����iterateVec" + iteratedVec);
        }
        if(isCommonSort)
        {
            return CompareHandler.commonCompareIterate(iteratedVec);
        }else
        {
            return CompareHandler.compareIterate(iteratedVec);
        }
    }

    ///////////////���ܷ�����
    /**
     * �ͻ��˽����жϣ��Ƿ��Ǵ���·�ߡ���true,��ʹ�Ǹ��£�ҲҪ��״̬����Ϊ������
     * @param RouteListID String
     * @param partMasterID String @
     * @return boolean public boolean isCreateRoute(String routeListID, String partMasterID) { boolean flag = true; PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
     * QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME); QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListID); query.addCondition(cond); query.addAND();
     * //�п������δʹ��·�ߡ� QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID); query.addCondition(cond1); query.addAND(); QueryCondition cond2 = new
     * QueryCondition("rouetID", QueryCondition.NOT_NULL); query.addCondition(cond2); Collection coll = pservice.findValueInfo(query); if(coll.size()==1) flag = false; return flag; }
     */
    /**
     * �Ƿ���·�ߡ�
     * @param partMasterID String
     * @param routeListID String
     * @return boolean public boolean isHasRoute(String partMasterID, String routeListID) { getListRoutePartLink(partMasterID, routeListID); if(routeInfo==null) return false; else return true; }
     */

    //routeListInfoΪ�µĴ�汾����ʱҪ���ƹ�������Ҫ����initialUsedΪ�µĴ�汾��
    /**
     * ����°汾
     * @param routeListInfo TechnicsRouteListIfc Ϊ�µĴ�汾����ʱҪ���ƹ����� ��Ҫ����initialUsedΪ�µĴ�汾�� @
     * @return TechnicsRouteListIfc ·�߱�ֵ����
     * @see TechnicsRouteListInfo
     */
    public TechnicsRouteListIfc newVersion(TechnicsRouteListIfc routeListInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        TechnicsRouteListIfc updateRouteListInfo = (TechnicsRouteListIfc)pservice.saveValueInfo(routeListInfo);
        newVersionListener(updateRouteListInfo);
        return updateRouteListInfo;
    }

    protected void newVersionListener(TechnicsRouteListIfc routeListInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter routeService's newVersionListener : routeListInfo.bsoID = " + routeListInfo.getBsoID());
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        String decessorID = routeListInfo.getPredecessorID();
        if(decessorID == null || decessorID.trim().length() == 0)
        {
            return;
        }
        if(VERBOSE)
        {
            System.out.println("decessorID = " + decessorID);
            //�����һ��С�汾��
        }
        TechnicsRouteListIfc originalList = (TechnicsRouteListIfc)pservice.refreshInfo(decessorID);
        //�����µĹ�����
        Collection coll = getRouteListLinkParts(originalList);
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            if(VERBOSE)
            {
                System.out.println("ԭ������listLinkInfo.bsoID = " + listLinkInfo.getBsoID());
            }
            ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkInfo)((ListRoutePartLinkInfo)listLinkInfo).duplicate();
            if(VERBOSE)
            {
                System.out.println("�¹�����listLinkInfo1.bsoID = " + listLinkInfo1.getBsoID());
            }
            listLinkInfo1.setRouteListID(routeListInfo.getBsoID());
            //��alterStatus���ó�INHERIT״̬��
            listLinkInfo1.setAlterStatus(INHERIT);//0
            //��adoptStatus���ó�CANCEL״̬��
            // �������ߣ� 2006 09 04 zz �����޸� ��������޶����°�·�߱���Ĺ����㲿����·��״̬ԭΪ���õı�Ϊȡ���ˣ�
            // ��������Ӧ�ø���ǰһ�汾��״̬ ���Բ���״̬��Ϊȡ��
            // listLinkInfo1.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
            //����initialUsedΪ�µĴ�汾��
            listLinkInfo1.setInitialUsed(routeListInfo.getVersionID());
            pservice.saveValueInfo(listLinkInfo1);
        }
        if(VERBOSE)
        {
            System.out.println("exit routeService's newVersionListener");
        }
    }

    //////////////��������ʼ//////////////////////////////////////
    /**
     * ������ɾ���������ݣ���Ҫ��֤���ݻָ���δɾ��ʱ��״̬����ʱ���ûع����־û������źţ��Ѿ����ûع���
     * @roseuid 403085BA0008
     * @J2EE_METHOD -- deleteRouteListListener, ʵ���ڳ������ʱ�á� ��Ϊ����·��û�а汾���˷�������ɾ���͸���������ҵ����󡣰���������·�ߡ�·�߽ڵ㡣Ҫ����master���ƺ͹���ԭ���Ƿ�һ�¡�
     */
    protected void deleteRouteListListener(TechnicsRouteListIfc routeListInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //ɾ����routeListInfo�汾�д����Ĺ���·�ߡ�
        QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        //        QueryCondition cond3 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        //        query1.addCondition(cond3);
        //        query1.addAND();
        //����½��Ĺ���·�ߡ�
        QueryCondition cond4 = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
        query1.addCondition(cond4);
        Collection coll1 = pservice.findValueInfo(query1);
        for(Iterator iter5 = coll1.iterator();iter5.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkIfc)iter5.next();
            String routeID = null;
            if(listLinkInfo1.getAlterStatus() == ROUTEALTER)
            {
                routeID = listLinkInfo1.getRouteID();
            }
            //��ɾ����׼���������ɾ��·��
            pservice.deleteValueInfo(listLinkInfo1);
            if(routeID != null && routeID.trim().length() != 0)
            {
                deleteRouteForDeleteListen(listLinkInfo1);
            }
        }
        //Begin CR2
        //�˹����־û���ɾ����                                                 
        //            QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);               
        //            QueryCondition cond2 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
        //                                                      routeListInfo.getBsoID());
        //            query.addCondition(cond2);
        //            Collection coll = pservice.findValueInfo(query);
        //            //ɾ��ListRoutePartLink���еĶ�Ӧ�Ĺ�����
        //            for (Iterator iter3 = coll.iterator(); iter3.hasNext(); ) {
        //              pservice.deleteValueInfo( (BaseValueIfc) iter3.next());
        //            }
        //End CR2
    }

    //�ָ����ݡ�
    private void comebackHandle(ListRoutePartLinkIfc listLinkInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's comebackHandle " + listLinkInfo.getBsoID());
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition("routeListMasterID", QueryCondition.EQUAL, listLinkInfo.getRouteListMasterID());
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, listLinkInfo.getPartMasterID());
        query.addCondition(cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition(LEFTID, QueryCondition.NOT_EQUAL, listLinkInfo.getRouteListID());
        query.addCondition(cond3);
        //���������С�
        query.addOrderBy("routeListIterationID", true);
        Collection coll = pservice.findValueInfo(query);
        Iterator iter = coll.iterator();
        //ֻ�����һ����
        if(iter.hasNext())
        {
            ListRoutePartLinkIfc old_listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            if(VERBOSE)
            {
                System.out.println(TIME + "routeService's comebackHandle " + old_listLinkInfo.getBsoID());
            }
            old_listLinkInfo.setAdoptStatus(RouteAdoptedType.ADOPT.getDisplay());
            pservice.saveValueInfo(old_listLinkInfo);
        }
    }

    //////////////////////ɾ�����������ʼ///////////////////////////
    /**
     * �������ɾ��ʱ��ɾ�����еĺ�routeList�Ĺ�������ɾ����Ӧ��·��ID.
     * @param partMasterInfo QMPartMasterIfc
     */
    protected void deletePartMasterListener(QMPartMasterIfc partMasterInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter the TechnicsRouteServiceEJB:deletePartmasterListener():ɾ���������");
        }
        try
        {
            String partMasterID = partMasterInfo.getBsoID();
            //��������ɾ�����ҵ����н���·�ߵ�listRoutePartlink�����࣬ɾ��·�ߡ�
            deletePartRoutes(partMasterID);
            //ɾ������ListRoutePartLink������
            deletePartLinks(partMasterID);
            //�����ɾ������ǲ�Ʒ��ɾ����ص�·�߱�ȡ�
            deleteProductRelaventObject(partMasterID);
            //�־û�ɾ����Ʒ��·�߱�Ĺ�����
        }catch(Exception e)
        {
            this.setRollbackOnly();
            throw new QMException(e);
        }
    }

    //ɾ��������·�ߡ�
    private void deletePartRoutes(String partMasterID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(cond1);
        Collection coll = pservice.findValueInfo(query);
        //ɾ��·�ߡ�
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            if(listLinkInfo.getRouteID() != null)
            {
                deleteRoute(listLinkInfo);
            }
        }
    }

    //ɾ������������йصĹ�����
    private void deletePartLinks(String partMasterID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(cond1);
        Collection coll = pservice.findValueInfo(query);
        //ɾ��������
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            //��ʹû��Ȩ�ޣ�·�߱����Ҳ�ᱻɾ����
            pservice.removeValueInfo(listLinkInfo);
        }
    }

    //ɾ�����Ʒ��ص�·�߱��·�ߵȡ�//����Ҫ�ָ�״̬����Ϊ���Ч�ʡ��ֶ�ɾ��·�߼�������
    private void deleteProductRelaventObject(String productMasterID)throws QMException
    {
        //ɾ�����Ʒ��ص�·�ߡ�
        deleteProductRoutes(productMasterID);
        //ɾ�����Ʒ��ص�listRoutePartLink��
        deleteProductLinks(productMasterID);
        //ɾ�����Ʒ��ص�·�߱�
        deleteProdcutRouteLists(productMasterID);
    }

    //ɾ�����Ʒ��ص�·�ߡ�
    private void deleteProductRoutes(String productMasterID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int i = query.appendBso(ROUTELISTMASTER_BSONAME, false);
        QueryCondition cond1 = new QueryCondition("productMasterID", QueryCondition.EQUAL, productMasterID);
        query.addCondition(i, cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("routeListMasterID", "bsoID");
        query.addCondition(0, i, cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond3);

        Collection coll = pservice.findValueInfo(query);
        //ɾ��·�ߡ�
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            if(listLinkInfo.getRouteID() != null)
            {
                deleteRoute(listLinkInfo);
            }
        }
    }

    /**
     * ɾ�����Ʒ��ص�listRoutePartLink�� ����˴���ɾ����������·�߱�ɾ�����������������ݴ����˷�Ч�ʡ�
     * @param productMasterID String
     */
    private void deleteProductLinks(String productMasterID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int i = query.appendBso(ROUTELISTMASTER_BSONAME, false);
        QueryCondition cond1 = new QueryCondition("productMasterID", QueryCondition.EQUAL, productMasterID);
        query.addCondition(i, cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("routeListMasterID", "bsoID");
        query.addCondition(0, i, cond2);
        Collection coll = pservice.findValueInfo(query);
        //ɾ�����й�����
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            //��ʹû��Ȩ�ޣ�·�߱����Ҳ�ᱻɾ����
            pservice.removeValueInfo(listLinkInfo);
        }
    }

    //ɾ�����Ʒ��ص�·�߱�
    private void deleteProdcutRouteLists(String productMasterID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTELIST_BSONAME);
        int i = query.appendBso(ROUTELISTMASTER_BSONAME, false);
        QueryCondition cond1 = new QueryCondition("productMasterID", QueryCondition.EQUAL, productMasterID);
        query.addCondition(i, cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("masterBsoID", "bsoID");
        query.addCondition(0, i, cond2);
        Collection coll = pservice.findValueInfo(query);
        //ɾ�����й�����
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)iter.next();
            //��ʹû��Ȩ�ޣ�·�߱�Ҳ�ᱻɾ����
            pservice.removeValueInfo(listInfo);
        }
    }

    //////////////////////ɾ���������������///////////////////////////
    /**
     * @param original ·�߱�ԭ��
     * @param work ·�߱���
     * @roseuid 4031A3570092
     * @J2EE_METHOD -- copyRouteList
     * @return ���ƺ�Ĺ���·�߸����� ���𿽱���������·�߱��ڲ�Ʒ����ʱ�����õ�����ʱ��ע�⣺ԭ�����Ӽ����ܲ������ڲ�Ʒ���Ӽ��� ���ڿͻ��˽��м�飬������ʾ��ʶ�������û����ġ� ��������·�߱����ʱ·�߱�����·�߼���ڵ�ĸ��ơ�
     */
    //Begin CR1
    /*
     * ԭ����: protected void copyRouteList(TechnicsRouteListIfc original, TechnicsRouteListIfc work) { if (VERBOSE) { System.out.println(TIME + "enter routeService's copyRouteList,ԭ�� " + original + "����"
     * + work); } PersistService pservice = (PersistService) EJBServiceHelper. getPersistService(); Collection coll = getRouteListLinkParts(original); if (VERBOSE) { System.out.println(TIME +
     * "copyRouteList... ԭ�������Ź���coll = " + coll); } for (Iterator iter = coll.iterator(); iter.hasNext(); ) { //ԭ�����㲿������ ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter. next();
     * //��ù����Ŀ��� ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkInfo) ( ( ListRoutePartLinkInfo) listLinkInfo).duplicate(); listLinkInfo1.setRouteListID(work.getBsoID());
     * //��alterStatus���ó�INHERIT״̬�� listLinkInfo1.setAlterStatus(INHERIT); //��adoptStatus���ó�CANCEL״̬��//���ģ�2004.5.12��adoptStatus���ó�ADOPT״̬�� //////////////////�����������������״̬,���д˸���. 2004.9.11 ������
     * //listLinkInfo1.setAdoptStatus(RouteAdoptedType.ADOPT.getDisplay()); if (!listLinkInfo1.getAdoptStatus().equals(RouteAdoptedType.ADOPT. getDisplay())) {
     * listLinkInfo1.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay()); } if (VERBOSE) { System.out.println(TIME + "����ǰ�� listLinkInfo.bsoID = " + listLinkInfo.getBsoID() +
     * ", listLinkInfo1.bsoID = " + listLinkInfo1.getBsoID()); } pservice.saveValueInfo(listLinkInfo1); if (VERBOSE) { System.out.println(TIME + "����� listLinkInfo1.bsoID = " +
     * listLinkInfo1.getBsoID()); } } if (VERBOSE) { System.out.println(TIME + "exit routeService's copyRouteList... "); } }
     */
    protected void copyRouteList(TechnicsRouteListIfc original, TechnicsRouteListIfc work)throws QMException
    {

        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, original.getBsoID());
        query.addCondition(cond);
        query.addAND();
        // �п������δʹ��·�ߡ�
        QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond1);

        Collection coll = pservice.findValueInfo(query, false);

        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            if(linkInfo.getRouteID() == null)
            {

                String level = original.getRouteListLevel();
                String level2 = new String("����·��");
                String status = null;
                if(level.equals(level2))
                {
                    String departmentBsoid = original.getRouteListDepartment();
                    status = getStatus2(linkInfo.getPartMasterID(), level, departmentBsoid);
                }else
                {
                    status = getStatus(linkInfo.getPartMasterID(), original.getRouteListLevel());
                }
                linkInfo.setAdoptStatus(status);

            }
            // �������ù������������
            linkInfo.setBsoID(null);
            linkInfo.setCreateTime(null);
            linkInfo.setModifyTime(null);

            linkInfo.setRouteListID(work.getBsoID());

            // ��alterStatus���ó�INHERIT״̬��
            linkInfo.setAlterStatus(INHERIT);

            // ����·��״̬
            if(!linkInfo.getAdoptStatus().equals(RouteAdoptedType.ADOPT.getDisplay()))
            {
                linkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
            }

            pservice.saveValueInfo(linkInfo, false);

        }

    }

    //End CR1
    //����ListRoutePartLink��setRouteListIterationID
    protected void checkinListener(TechnicsRouteListIfc listInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's checkinListener bsoid = " + listInfo.getBsoID());
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL, listInfo.getBsoID());
        query.addCondition(cond1);
        Collection coll = pservice.findValueInfo(query);
        String routeListIterationID = listInfo.getVersionValue();
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            listLinkInfo.setRouteListIterationID(routeListIterationID);
            if(VERBOSE)
            {
                System.out.println(TIME + "routeService's checkinListener adoptStatus = " + listLinkInfo.getAdoptStatus());
            }
            pservice.saveValueInfo(listLinkInfo);
        }
    }

    //////////////�������������//////////////////////////////////////

    /**
     * �������masterID��ù���·�߱�ֵ���󼯺ϡ�
     * @param partMasterID String @
     * @return Collection TechnicsRouteListInfo����·�߱�ֵ���󼯺�
     */
    public Collection getListsByPart(String partMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getListsByPart, partMasterID = " + partMasterID);
        }
        if(partMasterID == null || partMasterID.trim().length() == 0)
        {
            throw new QMException("partMasterID����Ϊ�ա�");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTELIST_BSONAME);
        int i = query.appendBso(LIST_ROUTE_PART_LINKBSONAME, false);
        //int j = query.appendBso(ROUTELIST_BSONAME, false);
        QueryCondition cond2 = new QueryCondition("bsoID", LEFTID);
        query.addCondition(0, i, cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(i, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("iterationIfLatest", Boolean.TRUE);
        query.addCondition(0, cond4);
        //·�߱�Ĵ���ʱ�併�����С�
        query.addOrderBy(0, "createTime", true);
        //SQL������������
        //query.setDisticnt(true);
        //����ListRoutePartLinkIfc
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's getPartLevelRoutes SQL = " + query.getDebugSQL());
        }
        Collection coll = pservice.findValueInfo(query);
        //·�߱�Ĵ���ʱ�併�����С�
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
        if(VERBOSE)
        {
            System.out.println(TIME + "��ѯ���Ϊ�� sortedVec = " + sortedVec);
            System.out.println(TIME + "exit... routeService's getListsByPart ");
        }
        return sortedVec;
    }

    /////////////2004.4.27����ӵķ���/////////////

    /**
     * �жϸ��������masterID������·�߱����Ƿ���·�ߡ�
     * @param partMasteInfos Collection QMPartMasterIfc����
     * @param level_zh String ·�߼��� @
     * @return Map key��partMasterInfo, value ��ȷ��һ���㲿���� ��״ֵ̬,�μ�getStatus(partMasterInfo.getBsoID(), level_zh)<br> ���"������"���ޡ���
     */
    public Map getRouteByParts(Collection partMasteInfos, String level_zh)throws QMException
    {
        Map partMap = new HashMap();
        for(Iterator iter = partMasteInfos.iterator();iter.hasNext();)
        {
            QMPartMasterIfc partMasterInfo = (QMPartMasterIfc)iter.next();
            partMap.put(partMasterInfo, getStatus(partMasterInfo.getBsoID(), level_zh));
        }
        return partMap;
    }

    /**
     * ���ݸ�����partMasterID,level_zh��·�߱��𣩣�����̬��ȷ��һ���㲿���� ��״ֵ̬��
     * @param partMasterID
     * @param level_zh ·�߱���
     */
    public String getStatus(String partMasterID, String level_zh)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = getLinkQuery(partMasterID, level_zh);
        Collection coll = pservice.findValueInfo(query);
        String status = null;
        if(coll.size() > 0)
        {
            status = RouteAdoptedType.EXIST.getDisplay();
            if(VERBOSE)
            {
                System.out.println("TRS:��̬��ȡһ���㲿����·��״̬" + status);
            }
        }else
        {
            status = RouteAdoptedType.NOTHING.getDisplay();
            if(VERBOSE)
            {
                System.out.println("TRS:��̬��ȡһ���㲿����·��״̬" + status);
            }
        }
        return status;
    }

    /**
     * �÷����ڹ���·�߱�ִ�и��²�����ʱ�������õķ����� ����Ե��Ƕ�������·�߱�����Ρ��÷�����һ���㲿 ����·���ǿյ�ʱ����ã�����̬�������㲿����·�� ״̬��
     * @param partmasterID ·�߱���㲿��
     * @param level_zh ·�߱�ļ���
     * @param depart ����·�߱�ĵ�λ����ֵ
     */
    // * �����ӵ�һ������--------- skybird 2005.2.24
    public String getStatus2(String partMasterID, String level_zh, String depart)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = getLinkQuery2(partMasterID, level_zh, depart);
        Collection coll = pservice.findValueInfo(query);
        String status = null;
        if(coll.size() > 0)
        {
            status = RouteAdoptedType.EXIST.getDisplay();
            if(VERBOSE)
            {
                System.out.println("TRS:��̬��ȡһ���㲿����·��״̬" + status);
            }
        }else
        {
            status = RouteAdoptedType.NOTHING.getDisplay();
            if(VERBOSE)
            {
                System.out.println("TRS:��̬��ȡһ���㲿����·��״̬" + status);
            }
        }
        return status;
    }

    /**
     * @param �б仯�ķ�ֵ֧����alterStatus = 0;
     * @param routeMap Map
     */
    /**
     * ���ݷ�֧����·��
     * @param linkInfo ListRoutePartLinkIfc ·�����������ֵ����
     * @param route TechnicsRouteIfc ·��ֵ����
     * @param branchInfos Collection ��ֵ֧���� ���ݷ�֧����·�� @
     * @see ListRoutePartLinkInfo
     * @see TechnicsRouteInfo
     */
    public void createRouteByBranch(ListRoutePartLinkIfc linkInfo, TechnicsRouteIfc route, Collection branchInfos)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("TechnicsRouteServiceEJB,�ɸ���·�ߵ��ã�û�������·�ߣ�alterstatus 0��");
        }
        //���ø���routeID,�����Ӧ��·����Ϣ��
        HashMap routeRelaventObejts = getRouteContainer2(linkInfo.getRouteID(), route, branchInfos);
        //����·�ߡ�
        saveRoute(linkInfo, routeRelaventObejts);
    }

    /**
     * ���·�֦��
     * @param branchs Collection RouteBranchNodeLinkIfcҪ���·�ֵ֦���� @
     */
    public void updateBranchInfos(Collection branchs)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        for(Iterator iter = branchs.iterator();iter.hasNext();)
        {
            //��ʱ�ķ�ֵ֧�����Ѿ��־û���
            pservice.updateValueInfo((BaseValueIfc)iter.next());
        }
    }

    /**
     * ����·�����ͺͷ�֦ID��ù���·�߽ڵ㡣
     * @param routeType String ·������
     * @param routeBranchID String ·�߷�֧ID
     * @return Collection null
     */
    //���ɿ����ڼ����ʼ�ڵ��Ƿ��������ӣ����У�����RouteListHandler�ĺ�����������������ʼ�ڵ㡣����ʵ�������ѡ�񡣣�
    public Collection getRouteNodes(String routeType, String routeBranchID)
    {
        return null;
    }

    //
    //   * �����㲿��ID�͹���·�߱�ID��ö�Ӧ�Ĺ���·�ߡ�
    //   * @return ����·��ֵ����
    //   *
    //   * �ο��ĵ���
    //   * ���� PHOS-CAPP-BR208 ��������·�߼��
    //   * ˵����һ���㲿����һ��·�߱���ֻ�ܴ���һ������·�߶���
    //   * ��     * ����㲿���Ѵ��ڹ���·�ߣ�����ʾ��Ӧ��Ϣ
    //   *
    //   * ������������·�߹�����
    //   * �汾 <v2.0>
    //   * �ĵ���ţ�PHOS-CAPP-UC311
    //   *
    //   * 4. ϵͳ�˳�����PHOS-CAPP-UI325����ִ����ѡ���·�߱��ִ����ѡ�е��㲿����ʾ��"����·�߹�����"����(PHOS-CAPP-UI316)�У�����������
    //         public TechnicsRouteIfc getListPartRoute(String partMasterID, String routeListID)
    //         {
    //        return null;
    //         }*/

    /**
     * @roseuid 40309C1300C3
     * @J2EE_METHOD -- getRoutes ���ݹ���·�߱�ID��ö�Ӧ�Ĺ���·�ߡ�
     * @return Collection ����·�߱��Ӧ�����й���·�ߣ�·�߷�֦��·�߽ڵ㡣 public Collection getRoutes(String routeListID) { return null; }
     */

    /**
     * ���ݲ�ƷID�����ID��ù���·��ֵ����
     * @param productMasterID String ��ƷID
     * @param partMasterID String���ID
     * @return TechnicsRouteIfc null
     */
    public TechnicsRouteIfc getRoute(String productMasterID, String partMasterID)
    {
        return null;
    }

    /**
     * ���ݹ���·�߱�ID������й���·�߼���ڵ�ֵ����
     * @param routeListID String ·�߱�ID
     * @param level_zh String ·�߱���
     * @return HashMap null
     */
    public java.util.HashMap getRouteListContent(String routeListID, String level_zh)
    {
        return null;
    }

    /**
     * ���ݹ���·�߱�·�߼����ù���·�ߡ� �������Ϊ�գ����ּ���
     * @param routeListID String ·�߱�ID
     * @param level_zh String ·�߼���
     * @return Collection null
     */
    public Collection getListLevelRoutes(String routeListID, String level_zh)
    {
        return null;
    }

    /**
     * ���ѡ������
     * @param depart String ��λ
     * @param master QMPartMasterIfc @
     * @return boolean ���������true,���򷵻�false
     * @see QMPartMasterInfo
     */

    public boolean getOptionPart(String depart, QMPartMasterIfc master)throws QMException
    {
        Collection coll = null;

        try
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            CodingManageService cservice = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
            CodingClassificationIfc coding = (CodingClassificationIfc)pservice.refreshInfo(depart);
            //Collection col = cservice.getOnlyCoding(coding);
            CodingClassificationIfc code;
            QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
            int n = query.appendBso(ROUTELIST_BSONAME, false);
            int m = query.appendBso(ROUTENODE_BSONAME, false);
            QueryCondition cond = new QueryCondition(RIGHTID, QueryCondition.EQUAL, master.getBsoID());
            query.addCondition(cond);
            query.addAND();
            QueryCondition condition1 = new QueryCondition(LEFTID, "bsoID");
            query.addCondition(0, n, condition1);
            query.addAND();
            QueryCondition condition2 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, FIRSTROUTE);
            query.addCondition(n, condition2);
            query.addAND();
            QueryCondition condition3 = new QueryCondition("routeID", "routeID");
            query.addCondition(0, m, condition3);
            /**
             * if (col != null && col.size() > 0) { Object[] codings = col.toArray(); if (codings.length == 1) { query.addAND(); code = (CodingIfc) codings[0]; QueryCondition condition4 = new
             * QueryCondition("nodeDepartment", QueryCondition.EQUAL, code.getBsoID()); query.addCondition(m, condition4); } else { query.addAND(); query.addLeftParentheses(); code = (CodingIfc)
             * codings[0]; QueryCondition condition4 = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, code.getBsoID()); query.addCondition(m, condition4); for (int i = 1; i <
             * codings.length; i++) { query.addOR(); code = (CodingIfc) codings[i]; QueryCondition condition5 = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, code.getBsoID());
             * query.addCondition(m, condition5); } query.addRightParentheses(); } }
             **/
            //add begin
            query.addAND();
            code = (CodingClassificationIfc)coding;
            QueryCondition condition4 = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, code.getBsoID());
            query.addCondition(m, condition4);
            //i added
            coll = pservice.findValueInfo(query);
            if(VERBOSE)
            {
                System.out.println("%%%%%%%%%%%%%%%%%%%%" + query.getDebugSQL());
            }
            if(coll != null && coll.size() > 0)
            {
                return true;
            }else
            {
                return false;
            }
        }catch(QMException ex)
        {
            ex.printStackTrace();
            throw ex;
        }
    }

    // zz (�����)20061110 �������,Ϊ����·�� "���"��ť ��Ӹ�����ѡ��λ���˹���

    /**
     * Ϊ����·�� "���"��ť ��Ӹ�����ѡ��λ���˹���
     * @param depart String ��λ
     * @param masters Vector @
     * @return Collection ListRoutePartLinkInfo��RightBsoID <br> ���Ͼ�·�ߵ�λ���˺���������
     */
    public Collection getOptionPart(String depart, Vector masters)throws QMException
    {
        if(masters != null && masters.size() >= 1)
        {
            String[] array = new String[masters.size()];
            Vector viaDepart = new Vector();
            for(int i = 0;i < masters.size();i++)
            {

                QMPartMasterIfc parti = (QMPartMasterIfc)masters.elementAt(i);
                viaDepart.add(parti.getBsoID());

            }

            Object[] obj = (Object[])viaDepart.toArray();
            for(int i = 0;i < obj.length;i++)
            {
                array[i] = (String)obj[i];
            }
            Collection coll = null;
            try
            {
                PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
                CodingManageService cservice = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
                CodingClassificationIfc coding = (CodingClassificationIfc)pservice.refreshInfo(depart);
                CodingClassificationIfc code;
                QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                int n = query.appendBso(ROUTELIST_BSONAME, false);
                int m = query.appendBso(ROUTENODE_BSONAME, false);

                QueryCondition cond = new QueryCondition(RIGHTID, QueryCondition.IN, array);

                query.addCondition(cond);
                query.addAND();
                QueryCondition condition1 = new QueryCondition(LEFTID, "bsoID");
                query.addCondition(0, n, condition1);
                query.addAND();
                QueryCondition condition2 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, FIRSTROUTE);
                query.addCondition(n, condition2);
                query.addAND();
                QueryCondition condition3 = new QueryCondition("routeID", "routeID");
                query.addCondition(0, m, condition3);

                query.addAND();
                code = (CodingClassificationIfc)coding;
                QueryCondition condition4 = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, code.getBsoID());
                query.addCondition(m, condition4);

                coll = pservice.findValueInfo(query);

                if(coll != null && coll.size() > 0)
                {
                    ;
                    Vector vector = new Vector();
                    for(Iterator iter = coll.iterator();iter.hasNext();)
                    {
                        ListRoutePartLinkInfo behindPart = (ListRoutePartLinkInfo)iter.next();
                        //��·�ߵ�λ���˺�����
                        String filterPart = (String)behindPart.getRightBsoID();

                        vector.add(pservice.refreshInfo(filterPart));

                    }

                    return vector;
                }else
                {
                    return null;
                }
            }catch(QMException ex)
            {
                ex.printStackTrace();
                throw ex;
            }

        }else
            return null;

    }

    private Collection filteredIterationsOf(Collection collection, PartConfigSpecIfc configSpecIfc)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        return partService.filteredIterationsOf(collection, configSpecIfc);
    }

    /**
     * �õ�ǰ�û������ù淶�����㲿��
     * @param master QMPartMasterIfc @
     * @return QMPartIfc ���˺������ֵ����
     * @see QMPartMasterInfo
     */

    public QMPartIfc filteredIterationsOfByDefault(QMPartMasterIfc master)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
        Vector vec = new Vector();
        vec.add(master);
        Collection col;
        col = (Collection)partService.filteredIterationsOf(vec, configSpecIfc);
        if(col != null && col.size() > 0)
        {
            Object[] parts = (Object[])col.toArray()[0];
            return (QMPartIfc)parts[0];
        }

        return null;
    }

    /**
     * ͨ��part�ҳ����з������ƹ淶����part������getOptionPart�������˽����
     * @param partMaster QMPartMasterIfc
     * @param configSpecIfc PartConfigSpecIfc
     * @param depart String ��λ @
     * @return Vector QMPartIfc[] ���鼯��:<br> partInfos[i]�����˺���������
     * @see QMPartMasterInfo
     */
    public Vector getAllSubPart(QMPartMasterIfc partMaster, PartConfigSpecIfc configSpecIfc, String depart)throws QMException
    {
        Vector vec = new Vector();
        try
        {
            EnterprisePartService enterprisePartService = (EnterprisePartService)EJBServiceHelper.getService("EnterprisePartService");
            // Object obj = enterprisePartService.
            //  getAllSubPartsByConfigSpec(partMaster,
            //                     configSpecIfc);

            Object Object = enterprisePartService.getAllSubPartsByConfigSpec(partMaster, configSpecIfc);
            if(VERBOSE)
            {
                System.out.println("=====getAllSubPart" + Object);
            }
            if(VERBOSE)
            {
                System.out.println("1111111=====getAllSubPart" + Object.getClass());
            }
            QMPartIfc[] partInfos = (QMPartIfc[])Object;

            if(getOptionPart(depart, partMaster))
            {
                QMPartIfc part = filteredIterationsOfByDefault(partMaster);
                if(part != null)
                {
                    vec.add(part);
                }
            }
            if(partInfos != null)
            {
                for(int i = 0;i < partInfos.length;i++)
                {
                    if(getOptionPart(depart, (QMPartMasterIfc)partInfos[i].getMaster()))
                    {
                        vec.add(partInfos[i]);
                    }
                }
            }
        }

        catch(QMException ex)
        {
            throw ex;
        }
        return vec;
    }

    /**
     * ����·����ض���ע�Ᵽ֤˳�� //��getRouteContainer���� lm add 20040827
     * @param routeID String
     * @param �б仯�ķ�ֵ֧����
     * @return HashMap
     */
    private HashMap getRouteContainer2(String routeID, TechnicsRouteIfc oldRoute, Collection branchInfos)throws QMException
    {
        HashMap relaventObejts = new HashMap();
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //1.���·�ߵ�HashMap��
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)pservice.refreshInfo(routeID);
        setupNewBaseValueIfc(routeInfo);
        routeInfo.setRouteDescription(oldRoute.getRouteDescription());
        RouteItem item = createRouteItem(routeInfo);
        relaventObejts.put(TECHNICSROUTE_BSONAME, item);
        //��ýڵ㼰�ڵ������
        Object[] obj1 = getRouteNodeAndNodeLink(routeID);
        //�ڵ㼯�ϡ�
        Collection nodes = (Collection)obj1[0];
        //�ڵ�������ϡ�
        Collection nodeLinks = (Collection)obj1[1];
        //2.��ӽڵ�������ϵ�HashMap
        //�����еĶ���ΪRouteItem.
        Collection nodeLinkItemVec = new Vector();
        for(Iterator iter1 = nodeLinks.iterator();iter1.hasNext();)
        {
            RouteNodeLinkIfc nodeLinkInfo = (RouteNodeLinkIfc)iter1.next();
            //���ù���Դ�ڵ㡣
            RouteNodeIfc sourceNode = nodeLinkInfo.getSourceNode();
            RouteNodeIfc node1 = (RouteNodeIfc)getTheSameInfo(sourceNode, nodes);
            nodeLinkInfo.setSourceNode(node1);
            //���ù���Ŀ�Ľڵ㡣
            RouteNodeIfc destNode = nodeLinkInfo.getDestinationNode();
            RouteNodeIfc node2 = (RouteNodeIfc)getTheSameInfo(destNode, nodes);
            nodeLinkInfo.setDestinationNode(node2);
            RouteItem nodeLinkItem = getNodeLinkItem(nodeLinkInfo, routeInfo);
            nodeLinkItemVec.add(nodeLinkItem);
        }
        relaventObejts.put(ROUTENODE_LINKBSONAME, nodeLinkItemVec);
        //3.��ӷ�֦��ڵ�������ϵ�HashMap
        //������й���·�߷�֧�ͽڵ������ֵ����branchNodeLinks��˳��
        Collection branchNodeLinks = getBranchRouteLinks(routeID);
        Collection branches = getOnlyRouteBranch(routeID);
        Collection branchNodeLinkItemVec = new Vector();
        for(Iterator iter = branchNodeLinks.iterator();iter.hasNext();)
        {
            RouteBranchNodeLinkIfc branchNodeLinkInfo = (RouteBranchNodeLinkIfc)iter.next();
            //��÷�֦item��
            TechnicsRouteBranchIfc branchInfo = branchNodeLinkInfo.getRouteBranchInfo();
            //����ڷ�֦��ڵ�����еķ�֧��branches�Ķ�Ӧֵ��
            TechnicsRouteBranchIfc branch1 = (TechnicsRouteBranchIfc)getTheSameInfo(branchInfo, branches);
            branchNodeLinkInfo.setRouteBranchInfo(branch1);
            RouteNodeIfc branchNode = branchNodeLinkInfo.getRouteNodeInfo();
            //����ڷ�֦��ڵ�����еĽڵ���nodes�Ķ�Ӧֵ��
            RouteNodeIfc node1 = (RouteNodeIfc)getTheSameInfo(branchNode, nodes);
            branchNodeLinkInfo.setRouteNodeInfo(node1);
            RouteItem branchNodeLinkItem = getBranchNodeLinkItem(branchNodeLinkInfo);
            branchNodeLinkItemVec.add(branchNodeLinkItem);
        }
        relaventObejts.put(BRANCHNODE_LINKBSONAME, branchNodeLinkItemVec);
        //4.��ӷ�֧���ϵ�HashMap
        Collection branchItemVec = new Vector();
        for(Iterator iter11 = branches.iterator();iter11.hasNext();)
        {
            TechnicsRouteBranchIfc branchInfo = (TechnicsRouteBranchIfc)iter11.next();
            if(branchInfos != null)
            {
                if(branchInfo.getBsoID() == null)
                {
                    throw new QMException("��ֵ֧��������Ϊ�յ�ʱ�����ԡ�");
                }
                //���ݴ����ֵ֧���󼯺������Ƿ���Ҫ·����Ϣ��
                for(Iterator branchIter = branchInfos.iterator();branchIter.hasNext();)
                {
                    TechnicsRouteBranchIfc paraBranch = (TechnicsRouteBranchIfc)branchIter.next();
                    if(branchInfo.getBsoID().trim().equals(paraBranch.getBsoID().trim()))
                    {
                        branchInfo.setMainRoute(paraBranch.getMainRoute());
                    }
                }
            }
            RouteItem branchItem = getBranchItem(branchInfo, routeInfo);
            branchItemVec.add(branchItem);
        }
        relaventObejts.put(TECHNICSROUTEBRANCH_BSONAME, branchItemVec);
        //5.��ӽڵ㼯�ϵ�HashMap.
        Collection nodeItemVec = new Vector();
        for(Iterator iter2 = nodes.iterator();iter2.hasNext();)
        {
            RouteNodeIfc nodeInfo = (RouteNodeIfc)iter2.next();
            RouteItem nodeItem = getNodeItem(nodeInfo, routeInfo);
            nodeItemVec.add(nodeItem);
        }
        relaventObejts.put(ROUTENODE_BSONAME, nodeItemVec);
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's    VERBOSE begin.....................");
            //TECHNICSROUTE_BSONAME��ROUTENODE_LINKBSONAME��TECHNICSROUTEBRANCH_BSONAME
            //ROUTENODE_BSONAME��BRANCHNODE_LINKBSONAME
            //1.·�ߡ�
            System.out.println("1.route.........................................");
            RouteItem routeItem1 = (RouteItem)relaventObejts.get(TECHNICSROUTE_BSONAME);
            TechnicsRouteIfc route = (TechnicsRouteIfc)routeItem1.getObject();
            System.out.println(TIME + "route.... routeID = " + route.getBsoID());
            System.out.println(TIME + "route.... routehashCode = " + route.hashCode());
            //2.�ڵ㡣
            //      System.out.println(
            //          "2.node.........................................");
            Collection nodes1 = (Collection)relaventObejts.get(ROUTENODE_BSONAME);
            for(Iterator iterator = nodes1.iterator();iterator.hasNext();)
            {
                RouteItem item1 = (RouteItem)iterator.next();
                RouteNodeIfc node = (RouteNodeIfc)item1.getObject();
                System.out.println(TIME + "node..... nodeID = " + node.getBsoID() + ", routeID = " + node.getRouteInfo().getBsoID());
                System.out.println(TIME + "node..... hashCode = " + node.getBsoID() + ", routehashCode = " + node.getRouteInfo().hashCode());
            }
            System.out.println("3.nodeLink.........................................");
            //3.�ڵ������
            Collection nodelinks1 = (Collection)relaventObejts.get(ROUTENODE_LINKBSONAME);
            for(Iterator iterator = nodelinks1.iterator();iterator.hasNext();)
            {
                RouteItem item1 = (RouteItem)iterator.next();
                RouteNodeLinkIfc nodeLink = (RouteNodeLinkIfc)item1.getObject();
                System.out.println(TIME + "nodeLink..... nodeLinkID = " + nodeLink.getBsoID() + ", routeID = " + nodeLink.getRouteInfo().getBsoID() + ", sourceNodeID = "
                        + nodeLink.getSourceNode().getBsoID() + nodeLink.getDestinationNode().getBsoID());
                System.out.println(TIME + "nodeLink..... nodeLinkHashCode = " + nodeLink.hashCode() + ", routeHashCode = " + nodeLink.getRouteInfo().hashCode() + ", sourceNodeHashCode = "
                        + nodeLink.getSourceNode().hashCode() + nodeLink.getDestinationNode().hashCode());
            }
            //4.��֧
            System.out.println("4.branch.........................................");
            Collection branchs1 = (Collection)relaventObejts.get(TECHNICSROUTEBRANCH_BSONAME);
            for(Iterator iterator = branchs1.iterator();iterator.hasNext();)
            {
                RouteItem item1 = (RouteItem)iterator.next();
                TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)item1.getObject();
                System.out.println(TIME + "branch..... brachID = " + branch.getBsoID() + ", routeID = " + branch.getRouteInfo().getBsoID());
                System.out.println(TIME + "branch..... brachHashCode = " + branch.hashCode() + ", routeHashCode = " + branch.getRouteInfo().hashCode());
            }
            //5.��֦������
            System.out.println("5.branchNodeLink.........................................");
            Collection brancheNodes1 = (Collection)relaventObejts.get(BRANCHNODE_LINKBSONAME);
            for(Iterator iterator = brancheNodes1.iterator();iterator.hasNext();)
            {
                RouteItem item1 = (RouteItem)iterator.next();
                RouteBranchNodeLinkIfc branchNode = (RouteBranchNodeLinkIfc)item1.getObject();
                System.out.println(TIME + "branchNode..... branchNodeID = " + branchNode.getBsoID() + ", branchID = " + branchNode.getRouteBranchInfo().getBsoID() + ", nodeID = "
                        + branchNode.getRouteNodeInfo().getBsoID());
                System.out.println(TIME + "branchNode..... branchNodeHashCode = " + branchNode.hashCode() + ", branchHashCode = " + branchNode.getRouteBranchInfo().hashCode() + ", nodeHashCode = "
                        + branchNode.getRouteNodeInfo().hashCode());
            }
            System.out.println(TIME + "routeService's getRouteContainer VERBOSE end.....................");
        }
        return relaventObejts;
    }

    /**
     * ���ݸ��������쵥λ��ѯ��ص��㲿���͹���·��id
     * @param makeDepartment String ���쵥λ @
     * @return Vector ��ŵ��� <br> String[] {listRoutePartLink.getPartMasterID(), routeid, listRoutePartLink.getAdoptStatus(), listRoutePartLink.getBsoID()}
     */
    // * added by skybird 2005.3.9
    public Vector getAllPartsRoutesM(String makeDepartment)throws QMException
    {
        Iterator iterate = null;
        //�洢���صĽ��
        Vector result = new Vector();
        //�洢��ʱ��ѯ���
        Collection coll = null;
        //�洢��ʱ��·��id
        Vector routeidVec = new Vector();
        //��ʱ·��id
        String routeid = new String();
        //��ʱ����·��
        ListRoutePartLinkIfc listRoutePartLink = null;
        TechnicsRouteIfc technicsRoute = null;
        //�ҳ����к��д�·�ߵ�λ��·�߽ڵ�
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTENODE_BSONAME);
        QueryCondition cond = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, makeDepartment);
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition("routeType", QueryCondition.EQUAL, "MANUFACTUREROUTE");
        query.addCondition(cond1);
        coll = pservice.findValueInfo(query);
        if(coll != null && coll.size() != 0)
        {
            iterate = coll.iterator();
            while(iterate.hasNext())
            {
                RouteNodeIfc tmp = (RouteNodeIfc)iterate.next();
                //�����������ظ���·��
                if(routeidVec.contains(tmp.getRouteInfo()))
                {
                    continue;
                }
                routeidVec.addElement(tmp.getRouteInfo());
                if(VERBOSE)
                {
                    System.out.print("" + tmp.getRouteInfo().getBsoID() + ",");
                    System.out.println("4450");
                }
            }
            if(routeidVec.size() != 0)
            {
                System.out.println("the num of route" + routeidVec.size());
            }
            //�����ǲ�Ѱ�㲿��·�ߺ�·�߱������������
            for(int i = 0;i < routeidVec.size();i++)
            {
                technicsRoute = (TechnicsRouteIfc)routeidVec.elementAt(i);
                routeid = technicsRoute.getBsoID();
                if(VERBOSE)
                {
                    System.out.println("4461row routeid" + routeid);
                }
                //���µĲ�ѯ���ܲ����ظ�
                query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeid);
                query.addCondition(cond);
                coll = pservice.findValueInfo(query);
                if(VERBOSE)
                {
                    System.out.println("�����õ�expected num is 1:" + coll.size());
                }
                if(coll != null && coll.size() != 0)
                {
                    iterate = coll.iterator();
                    while(iterate.hasNext())
                    {
                        listRoutePartLink = (ListRoutePartLinkIfc)iterate.next();

                        result.addElement(new String[]{listRoutePartLink.getPartMasterID(), routeid, listRoutePartLink.getAdoptStatus(), listRoutePartLink.getBsoID()});
                    }
                }
            }
            return result;
        }
        return result;
    }

    /**
     * ���ݸ�����װ�䵥λ��ѯ��ص��㲿���͹���·��id
     * @param constructDepartment String װ�䵥λ @
     * @return Vector ��ŵ��� String[] {listRoutePartLink.getPartMasterID(), routeid, listRoutePartLink.getAdoptStatus(), listRoutePartLink.getBsoID()}
     */
    // * added by skybird 2005.3.9
    public Vector getAllPartsRoutesC(String constructDepartment)throws QMException
    {
        Iterator iterate = null;
        //�洢���صĽ��
        Vector result = new Vector();
        //�洢��ʱ��ѯ���
        Collection coll = null;
        //�洢��ʱ��·��id
        Vector routeidVec = new Vector();
        //��ʱ·��id
        String routeid = new String();
        //��ʱ����·��
        ListRoutePartLinkIfc listRoutePartLink = null;
        TechnicsRouteIfc technicsRoute = null;
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTENODE_BSONAME);
        QueryCondition cond = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, constructDepartment);
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition("routeType", QueryCondition.EQUAL, "ASSEMBLYROUTE");
        query.addCondition(cond1);
        coll = pservice.findValueInfo(query);
        if(coll != null && coll.size() != 0)
        {
            iterate = coll.iterator();
            while(iterate.hasNext())
            {
                RouteNodeIfc tmp = (RouteNodeIfc)iterate.next();
                if(routeidVec.contains(tmp.getRouteInfo()))
                {
                    continue;
                }
                routeidVec.addElement(tmp.getRouteInfo());
            }
            //�����ǲ�Ѱ�㲿��·�ߺ�·�߱������������
            for(int i = 0;i < routeidVec.size();i++)
            {
                technicsRoute = (TechnicsRouteIfc)routeidVec.elementAt(i);
                routeid = technicsRoute.getBsoID();
                //���µĲ�ѯ���ܲ����ظ�
                query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeid);
                query.addCondition(cond);
                coll = pservice.findValueInfo(query);
                if(coll != null && coll.size() != 0)
                {
                    iterate = coll.iterator();
                    while(iterate.hasNext())
                    {
                        listRoutePartLink = (ListRoutePartLinkIfc)iterate.next();

                        result.addElement(new String[]{listRoutePartLink.getPartMasterID(), routeid, listRoutePartLink.getAdoptStatus(), listRoutePartLink.getBsoID()});
                    }
                }
            }
            return result;
        }
        return result;
    }

    /**
     * ���ݸ��������쵥λ��װ�䵥λ��ѯ��ص��㲿���͹���·��id
     * @param makeDepartment String ���쵥λ
     * @param constructDepartment String װ�䵥λ @
     * @return Vector ���������vector:<br> 1.mResult Vector:�������쵥λ��ѯ���صĽ����<br> ��ŵ���String[] {listRoutePartLink.getPartMasterID(), routeid, listRoutePartLink.getAdoptStatus(), listRoutePartLink.getBsoID()}
     * 2.cResult Vector������װ�䵥λ��ѯ���صĽ��;<br> ��ŵ���String[] {listRoutePartLink.getPartMasterID(), routeid, listRoutePartLink.getAdoptStatus(), listRoutePartLink.getBsoID()}
     */
    //* added by skybird 2005.3.9
    public Vector getAllPartsRoutesA(String makeDepartment, String constructDepartment)throws QMException
    {
        //�洢���صĽ��
        Vector result = new Vector();
        //�������쵥λ��ѯ���صĽ��
        Vector mResult = new Vector();
        //����װ�䵥λ��ѯ���صĽ��
        Vector cResult = new Vector();
        mResult = getAllPartsRoutesM(makeDepartment);
        cResult = getAllPartsRoutesC(makeDepartment);
        if((mResult == null || mResult.size() == 0) || (cResult == null || cResult.size() == 0))
        {
            return result;
        }else
        {

            for(int i = 0;i < cResult.size();i++)
            {
                String[] tmp = (String[])cResult.elementAt(i);
                for(int j = 0;j < mResult.size();j++)
                {
                    String[] tmp1 = (String[])mResult.elementAt(j);
                    if(tmp1[1].equals(tmp[1]))
                    {
                        if(tmp1[0].equals(tmp[0]))
                        {
                            result.addElement(mResult.elementAt(j));
                        }else
                        {
                            result.addElement(mResult.elementAt(j));
                            result.addElement(cResult.elementAt(i));
                        }
                    }
                }
            } //end for
            if(VERBOSE)
            {
                System.out.println("result" + result.size());
            }
            return result;
        }
    }

    //end of this method
    /**
     * ��·�ߺ����ӷ���
     * @param routeAndLinks Collection ·�ߺ����Ӽ���
     * @return Collection
     */
    //zz �����޸ģ��������� 2006 08 04
    //������������⣬�����Ԫ�ػ������ظ�Ԫ��
    private Collection sortRouteAndLinks(Collection routeAndLinks)
    {
        //CCBegin SS21
        //QMPartMasterIfc partmaster;
        QMPartIfc partmaster;
        //CCEnd SS21
        BaseValueIfc[] bsos;
        Vector result = new Vector();
        Iterator routeIterator1 = routeAndLinks.iterator();
        while(routeIterator1.hasNext())
        {

            bsos = (BaseValueIfc[])routeIterator1.next();
            //CCBegin SS21
            //partmaster = (QMPartMasterIfc)bsos[2];
            //QMPartMasterIfc temp;
            partmaster = (QMPartIfc)bsos[2];
            QMPartIfc temp;
            //CCEnd SS21
            if(result.size() == 0)
            {

                result.add(bsos);
            }else
            {

                boolean flag = true;
                for(int i = 0;i < result.size();i++)
                {
                    //CCBegin SS21
                    //temp = (QMPartMasterIfc)(((BaseValueIfc[])result.elementAt(i))[2]);
                    temp = (QMPartIfc)(((BaseValueIfc[])result.elementAt(i))[2]);
                    //CCEnd SS21
                    if(partmaster.getPartNumber().compareTo(temp.getPartNumber()) < 0)
                    {
                        result.add(i, bsos);
                        flag = false;
                        break;
                    }
                }
                if(flag)
                    result.add(result.size(), bsos);
            }
        }
        return result;
    }

    /**
     * ���ݸ��������쵥λ��ѯ��ص��㲿���͹���·��id
     * @param mDepartment String ���쵥λ��id
     * @param cDepartment String װ�䵥λ��id
     * @param productID String ��Ʒ��id
     * @param source String �㲿������Դ
     * @param type String �㲿�������� @
     * @return Collection vector����:<br> part.getLifeCycleState().getDisplay()<br> part.getBsoID()<br> partmaster.getPartNumber()<br> partmaster.getPartName()<br> part.getVersionValue()<br>
     * lrpLink.getParentPartNum()<br> Integer(this.calCountInProduct(part, lrpLink.getParentPart(), product, boms, midleBoms))<br> route.getRouteDescription()<br>
     * ������ϡ�ÿ����������һ��Vector.���η��㲿���������ڣ��㲿��id���㲿����ţ��㲿�����ƣ� �汾�������ţ��㲿���ڲ�Ʒ���������ϣ�·��������·�߼��ϣ�����·�߼���ÿ����Ŀ��������װ��2��·�ߣ�
     */
    /*
     * delete by guoxl on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����) public Collection getAllPartsRoutes(String mDepartment, String cDepartment, String productID, String source, String type) { if (VERBOSE) {
     * System.out.println("����·�߷���getAllPartsRoutes���� :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + " productID =" + productID); } try { PersistService pservice =
     * (PersistService) EJBServiceHelper. getPersistService(); StandardPartService partService = (StandardPartService) EJBServiceHelper. getService(PART_SERVICE); VersionControlService vcservice =
     * (VersionControlService) EJBServiceHelper.getService ("VersionControlService"); if (productID != null && !productID.trim().equals("")) { QMPartIfc product = (QMPartIfc)
     * pservice.refreshInfo(productID); //�˷�����part���ṩ Vector subs = (Vector) partService.getAllSubParts(product); Vector products = new Vector(); products.add(product); return
     * getColligationRoutesDetial(mDepartment, cDepartment, subs, products, false); } else { PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc) partService.findPartConfigSpecIfc(); HashMap
     * bomMap = new HashMap(); Vector mdepartIDs = new Vector(); Vector cdepartIDs = new Vector(); if (mDepartment != null && !mDepartment.trim().equals("")) { BaseValueIfc base = (BaseValueIfc)
     * pservice.refreshInfo(mDepartment); mdepartIDs = this.getAllSubDepartMentID(mdepartIDs, base); } if (cDepartment != null && !cDepartment.trim().equals("")) { BaseValueIfc base = (BaseValueIfc)
     * pservice.refreshInfo(cDepartment); cdepartIDs = this.getAllSubDepartMentID(cdepartIDs, base); } Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs, cdepartIDs, null); //
     * System.out.println("5383�� getRouteByPartAndDep �õ��� routeAndLinks ���ϵĳ���" + routeAndLinks.size()); if (routeAndLinks != null && routeAndLinks.size() > 0) { routeAndLinks =
     * sortRouteAndLinks(routeAndLinks); // System.out.println(" sortRouteAndLinks ֮��� routeAndLinks ���ϵĳ��� " +routeAndLinks.size() ); } Iterator routeIterator = routeAndLinks.iterator();
     * BaseValueIfc[] bsos; TechnicsRouteIfc route; HashMap parts = new HashMap(); ArrayList partMasters = new ArrayList(); ListRoutePartLinkIfc lrpLink; QMPartMasterIfc partmaster; QMPartIfc part;
     * //(�����)20060701 �����޸� begin while (routeIterator.hasNext()) { bsos = (BaseValueIfc[]) routeIterator.next(); partmaster = (QMPartMasterIfc) bsos[2]; // �õ�ǰ�û������ù淶���� if
     * (!parts.containsKey(partmaster.getBsoID())) { //parts.put(partmaster.getBsoID(), // this.filteredIterationsOfByDefault(partmaster)); parts.put(partmaster.getBsoID(),null);
     * partMasters.add(partmaster); } } Collection filteredPartmasters = this.filteredIterationsOfByDefault(partMasters); for(Iterator f = filteredPartmasters.iterator();f.hasNext();) { Object[] obj =
     * (Object[])f.next(); parts.put(((QMPartIfc)obj[0]).getMasterBsoID(),((BaseValueIfc)obj[0])); } //(�����)20060701 �����޸� end //(�����)20060725 �����޸� end Vector products =
     * this.getAllParentProductOnce(new Vector(parts.values())); //(�����)20060725 �����޸� end Iterator routeIterator1 = routeAndLinks.iterator(); Vector content = new Vector(); Vector result = new
     * Vector(); Vector productNums = new Vector(); for (int j = 0; j < products.size(); j++) { QMPartIfc tempP = (QMPartIfc) products.elementAt(j); productNums.add(tempP.getPartNumber() + "(" +
     * tempP.getVersionValue() + ")"); } result.add(productNums); while (routeIterator1.hasNext()) { Vector temp = new Vector(); HashMap map; bsos = (BaseValueIfc[]) routeIterator1.next();
     * 
     * lrpLink = (ListRoutePartLinkIfc) bsos[0]; route = (TechnicsRouteIfc) bsos[1]; // System.out.println("��bsos��ȡ������route " +route.getBsoID() ); partmaster = (QMPartMasterIfc) bsos[2]; //ȡ���°汾���е�����
     * part = (QMPartIfc) parts.get(partmaster.getBsoID()); temp.add(part.getLifeCycleState().getDisplay()); temp.add(part.getBsoID()); temp.add(partmaster.getPartNumber());
     * temp.add(partmaster.getPartName()); temp.add(part.getVersionValue()); temp.add(lrpLink.getParentPartNum()); QMPartIfc product; Vector counts = new Vector(); for (int j = 0; j < products.size();
     * j++) { product = (QMPartIfc) products.elementAt(j); List boms = null; List midleBoms = null; if(lrpLink.getParentPartID()==null) counts.add(new Integer(0)); else{
     * 
     * if (bomMap.containsKey(product.getBsoID())) boms = (List) bomMap.get(product.getBsoID()); else { String[] attrNames = {"quantity"}; boms = partService.setBOMList(product, attrNames, null, null,
     * null, partConfigSpecIfc); bomMap.put(product.getBsoID(), boms); } if (bomMap.containsKey(lrpLink.getParentPart().getBsoID())) midleBoms = (List) bomMap.get(lrpLink.getParentPart().getBsoID());
     * else { String[] attrNames = {"quantity"}; midleBoms = partService.setBOMList(lrpLink.getParentPart(), attrNames, null, null, null, partConfigSpecIfc);
     * bomMap.put(lrpLink.getParentPart().getBsoID(), midleBoms); } //���⣬�������Ϊ0û����.���⣬�����ж��Ƿ��Ʒ����·�߱��Ӧ�ģ�����ǣ�����ֱ���� counts.add(new Integer(this.calCountInProduct(part, lrpLink.getParentPart(), product,
     * boms, midleBoms))); } } temp.add(counts); // �������壩�ݿͻ��鿴ʱ����ֱ�Ӵ�TechnicsRouteBranchIfc�������������·�ߴ��ַ�����ȡ zz start // map = (HashMap) getRouteBranchs(route.getBsoID()); //ԭ���� //
     * assembleBranchStr(temp, map);//ԭ���� map = (HashMap) getDirectRouteBranchStrs(route.getBsoID()); assembleBranchStrForThin(temp, map); // �������壩�ݿͻ��鿴ʱ����ֱ�Ӵ�TechnicsRouteBranchIfc�������������·�ߴ��ַ�����ȡ zz
     * end //add by guoxl 2008.11.17(���·�߱��ţ���ӵ�������) String routeListNum=lrpLink.getLeftBsoID(); TechnicsRouteListIfc routeListIfc=(TechnicsRouteListIfc) pservice.refreshInfo(routeListNum);
     * temp.add(routeListIfc.getRouteListNumber()); //add end by guoxl temp.add(route.getRouteDescription()); content.add(temp); } result.add(content); return result; } } catch (Exception e) {
     * e.printStackTrace(); } return null; }delete end by guoxl end on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)
     */
    //add by guoxl on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)
    public Collection getAllPartsRoutes(String mDepartment, String cDepartment, String productID, String source, String type)throws QMException
    {

        if(VERBOSE)
        {
            System.out.println("���뷽��==getAllPartsRoutes==" + "mDepartment===" + mDepartment + "/n" + "cDepartment=======" + cDepartment + "productID=====" + productID + "/n" + "source=====" + source
                    + "/n" + "type=========" + type);
        }
        try
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
            VersionControlService vcservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
            if(productID != null && !productID.trim().equals(""))
            {

                QMPartIfc product = (QMPartIfc)pservice.refreshInfo(productID);

                Vector subs = new Vector();
                subs.add(product);
                Vector products = new Vector();
                products.add(product);

                return getColligationRoutesDetial(mDepartment, cDepartment, subs, products, false);
            }//ֻ�е�λ
            else
            {
                //�����������ù��

                PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)partService.findPartConfigSpecIfc();

                HashMap bomMap = new HashMap();
                Vector mdepartIDs = new Vector();
                Vector cdepartIDs = new Vector();
                if(mDepartment != null && !mDepartment.trim().equals(""))
                {
                    BaseValueIfc base = (BaseValueIfc)pservice.refreshInfo(mDepartment);

                    mdepartIDs.add(base.getBsoID());

                }
                if(cDepartment != null && !cDepartment.trim().equals(""))
                {
                    BaseValueIfc base = (BaseValueIfc)pservice.refreshInfo(cDepartment);

                    cdepartIDs.add(base.getBsoID());

                }

                //ͨ���������ãӣѣ̲���
                Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs, cdepartIDs, null);

                if(routeAndLinks != null && routeAndLinks.size() > 0)
                {

                    routeAndLinks = sortRouteAndLinks(routeAndLinks);

                    //  System.out.println(" sortRouteAndLinks ֮��� routeAndLinks ���ϵĳ��� " +routeAndLinks.size() );
                }

                Iterator routeIterator = routeAndLinks.iterator();
                BaseValueIfc[] bsos;
                TechnicsRouteIfc route;
                HashMap parts = new HashMap();
                ArrayList partMasters = new ArrayList();
                ListRoutePartLinkIfc lrpLink;
                QMPartMasterIfc partmaster;
                QMPartIfc part;

                HashMap partParentsMap = new HashMap();

                //(�����)20060701 �����޸� begin
                while(routeIterator.hasNext())
                {
                    bsos = (BaseValueIfc[])routeIterator.next();
                    //add by guoxl on 2008-12-19(���ʵʩ����)
                    lrpLink = (ListRoutePartLinkIfc)bsos[0];
                    QMPartIfc parentsPartIfc = (QMPartIfc)lrpLink.getParentPart();

                    if(parentsPartIfc != null)
                    {

                        if(partParentsMap.containsKey(parentsPartIfc.getBsoID()))
                        {

                            continue;
                        }else
                        {
                            partParentsMap.put(parentsPartIfc.getBsoID(), parentsPartIfc);

                        }

                    }
                    //add by guoxl on 2008-12-19(���ʵʩ����)
                    partmaster = (QMPartMasterIfc)bsos[2];
                    // �õ�ǰ�û������ù淶����
                    if(!parts.containsKey(partmaster.getBsoID()))
                    {

                        parts.put(partmaster.getBsoID(), null);
                        partMasters.add(partmaster);
                    }
                }
                //ͨ��partMasters�������ù淶����,Ȼ�󷵻�QMpartIfc����
                Collection filteredPartmasters = this.filteredIterationsOfByDefault(partMasters);

                for(Iterator f = filteredPartmasters.iterator();f.hasNext();)
                {
                    Object[] obj = (Object[])f.next();
                    parts.put(((QMPartIfc)obj[0]).getMasterBsoID(), ((BaseValueIfc)obj[0]));

                }
                //(�����)20060701 �����޸� end
                //(�����)20060725 �����޸� end

                //add by guoxl on 2008-12-19(���ʵʩ����)
                Vector products = new Vector(partParentsMap.values());
                //add by guoxl end on 2008-12-19(���ʵʩ����)

                //(�����)20060725 �����޸� end
                Iterator routeIterator1 = routeAndLinks.iterator();
                Vector content = new Vector();
                Vector result = new Vector();
                Vector productNums = new Vector();

                for(int j = 0;j < products.size();j++)
                {
                    QMPartIfc tempP = (QMPartIfc)products.elementAt(j);

                    productNums.add(tempP.getPartNumber() + "(" + tempP.getVersionValue() + ")");

                }
                result.add(productNums);

                while(routeIterator1.hasNext())
                {
                    Vector temp = new Vector();
                    HashMap map;
                    bsos = (BaseValueIfc[])routeIterator1.next();

                    lrpLink = (ListRoutePartLinkIfc)bsos[0];

                    route = (TechnicsRouteIfc)bsos[1];
                    //  System.out.println("��bsos��ȡ������route " +route.getBsoID() );
                    partmaster = (QMPartMasterIfc)bsos[2];
                    //ȡ���°汾���е�����
                    part = (QMPartIfc)parts.get(partmaster.getBsoID());
                    if(null == part)//add by guoxl for TD3779
                        continue;
                    temp.add(part.getLifeCycleState().getDisplay());
                    temp.add(part.getBsoID());
                    temp.add(partmaster.getPartNumber());
                    temp.add(partmaster.getPartName());
                    temp.add(part.getVersionValue());
                    temp.add(lrpLink.getParentPartNum());
                    QMPartIfc product;
                    Vector counts = new Vector();

                    int count = lrpLink.getCount();
                    String parentsBso = lrpLink.getParentPartID();

                    for(int j = 0;j < products.size();j++)
                    {
                        product = (QMPartIfc)products.elementAt(j);

                        if(lrpLink.getParentPartID() == null || !parentsBso.equals(product.getBsoID()))
                        {

                            counts.add(new Integer(0));

                        }else
                        {

                            counts.add(new Integer(count));
                        }
                    }
                    temp.add(counts);

                    // �������壩�ݿͻ��鿴ʱ����ֱ�Ӵ�TechnicsRouteBranchIfc�������������·�ߴ��ַ�����ȡ zz start
                    //         map = (HashMap) getRouteBranchs(route.getBsoID()); //ԭ����
                    //          assembleBranchStr(temp, map);//ԭ����
                    //                    map = (HashMap)getDirectRouteBranchStrs(route.getBsoID());
                    //                    assembleBranchStrForThin(temp, map);
                    temp.add(lrpLink.getMainStr());
                    temp.add(lrpLink.getSecondStr());
                    // �������壩�ݿͻ��鿴ʱ����ֱ�Ӵ�TechnicsRouteBranchIfc�������������·�ߴ��ַ�����ȡ zz end
                    //add by guoxl 2008.12.9(���·�߱��ţ���ӵ�������)
                    String routeListNum = lrpLink.getLeftBsoID();
                    TechnicsRouteListIfc routeListIfc = (TechnicsRouteListIfc)pservice.refreshInfo(routeListNum);

                    TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo)routeListIfc.getMaster();
                    //������е�����С�汾
                    routeListIfc = (TechnicsRouteListIfc)getLatestVesion(masterinfo);
                    temp.add(routeListIfc.getRouteListNumber() + "(" + routeListIfc.getVersionValue() + ")");
                    //add end by guoxl
                    temp.add(lrpLink.getRouteDescription());
                    content.add(temp);
                }
                result.add(content);
                return result;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * ͨ���㲿�������ֺͺ�������㲿��������Ϣ,���صļ�����ֻ��һ��QMPartMasterIfc����
     * @param partName :String �㲿�������ơ�
     * @param partNumber :String �㲿���ĺ��롣
     * @return collection:���ҵ���QMPartMasterIfc�㲿������Ϣ����ļ��ϣ�ֻ��һ��Ԫ�ء�
     * @throws QMException 
     * @
     * @see QMPartMasterInfo
     */
    private Collection findPartMaster(String partName, String partNumber) throws QMException
        
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("QMPartMaster");
        if (partName != null && !partName.equals("")) {
			QueryCondition condition1 = new QueryCondition("partName", "=",
					partName);
			query.addCondition(condition1);
			
		}
        if((partName != null && !partName.equals(""))&&
        		(partNumber != null && !partNumber.equals(""))){
        	
        	query.addAND();
        }
        if (partNumber != null && !partNumber.equals("")) {
			QueryCondition condition2 = new QueryCondition("partNumber", "=",
					partNumber);
			query.addCondition(condition2);
		}
        return pservice.findValueInfo(query);
    }
    //add end by guoxl 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)
    public Collection getAllPartsRoutes_new(String mDepartment, String cDepartment, String partNum, String partName , String state)throws QMException
    {

        if(VERBOSE)
        {
            System.out.println("���뷽��==getAllPartsRoutes==" + "mDepartment===" + mDepartment + "\n" + "cDepartment=======" + cDepartment + "partNum=====" + partNum + "\n" + "partName=====" + partName
                    + "\n" + "type=========" + state);
        }
        try
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
            VersionControlService vcservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
            if((partNum != null && !partNum.trim().equals(""))||
            		(partName != null && !partName.trim().equals("")))
            {

//                QMPartIfc product = (QMPartIfc)pservice.refreshInfo(productID);
                Vector mV=(Vector)findPartMaster(partName,partNum);
                QMPartMasterIfc partM=(QMPartMasterIfc)mV.get(0);
                
                Vector partV=(Vector)partService.findPart(partM);
                QMPartIfc product = (QMPartIfc)partV.get(0);
                
                Vector subs = new Vector();
                subs.add(product);
                Vector products = new Vector();
                products.add(product);

                return getColligationRoutesDetial(mDepartment, cDepartment, subs, products, false,state);
            }//ֻ�е�λ
            else
            {
                //�����������ù��

                PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)partService.findPartConfigSpecIfc();

                HashMap bomMap = new HashMap();
                Vector mdepartIDs = new Vector();
                Vector cdepartIDs = new Vector();
                if(mDepartment != null && !mDepartment.trim().equals(""))
                {
                    BaseValueIfc base = (BaseValueIfc)pservice.refreshInfo(mDepartment);

                    mdepartIDs.add(base.getBsoID());

                }
                if(cDepartment != null && !cDepartment.trim().equals(""))
                {
                    BaseValueIfc base = (BaseValueIfc)pservice.refreshInfo(cDepartment);

                    cdepartIDs.add(base.getBsoID());

                }

                //ͨ���������ãӣѣ̲���
                Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs, cdepartIDs, null);

                if(routeAndLinks != null && routeAndLinks.size() > 0)
                {

                    routeAndLinks = sortRouteAndLinks(routeAndLinks);

                    //  System.out.println(" sortRouteAndLinks ֮��� routeAndLinks ���ϵĳ��� " +routeAndLinks.size() );
                }

                Iterator routeIterator = routeAndLinks.iterator();
                BaseValueIfc[] bsos;
                TechnicsRouteIfc route;
                HashMap parts = new HashMap();
                ArrayList partMasters = new ArrayList();
                ListRoutePartLinkIfc lrpLink;
                //CCBegin SS21
                //QMPartMasterIfc partmaster;
                QMPartIfc partmaster;
                //CCEnd SS21
                QMPartIfc part;

                HashMap partParentsMap = new HashMap();

                //(�����)20060701 �����޸� begin
                while(routeIterator.hasNext())
                {
                    bsos = (BaseValueIfc[])routeIterator.next();
                    //add by guoxl on 2008-12-19(���ʵʩ����)
                    lrpLink = (ListRoutePartLinkIfc)bsos[0];
                    QMPartIfc parentsPartIfc = (QMPartIfc)lrpLink.getParentPart();

                    if(parentsPartIfc != null)
                    {

                        if(partParentsMap.containsKey(parentsPartIfc.getBsoID()))
                        {

                            continue;
                        }else
                        {
                            partParentsMap.put(parentsPartIfc.getBsoID(), parentsPartIfc);

                        }

                    }
                    //add by guoxl on 2008-12-19(���ʵʩ����)
                    //CCBegin SS21
                    //partmaster = (QMPartMasterIfc)bsos[2];
                    partmaster = (QMPartIfc)bsos[2];
                    //CCEnd SS21
                    // �õ�ǰ�û������ù淶����
                    if(!parts.containsKey(partmaster.getBsoID()))
                    {

                        //CCBegin SS21
                        //parts.put(partmaster.getBsoID(), null);
                        //partMasters.add(partmaster);
                        parts.put(partmaster.getBsoID(), partmaster);
                        //CCEnd SS21
                    }
                }
                //ͨ��partMasters�������ù淶����,Ȼ�󷵻�QMpartIfc����
                //CCBegin SS21
                /*Collection filteredPartmasters = this.filteredIterationsOfByDefault(partMasters);

                for(Iterator f = filteredPartmasters.iterator();f.hasNext();)
                {
                    Object[] obj = (Object[])f.next();
                    parts.put(((QMPartIfc)obj[0]).getMasterBsoID(), ((BaseValueIfc)obj[0]));

                }*/
                //CCEnd SS21
                //(�����)20060701 �����޸� end
                //(�����)20060725 �����޸� end

                //add by guoxl on 2008-12-19(���ʵʩ����)
                Vector products = new Vector(partParentsMap.values());
                //add by guoxl end on 2008-12-19(���ʵʩ����)

                //(�����)20060725 �����޸� end
                Iterator routeIterator1 = routeAndLinks.iterator();
                Vector content = new Vector();
                Vector result = new Vector();
                Vector productNums = new Vector();

                for(int j = 0;j < products.size();j++)
                {
                    QMPartIfc tempP = (QMPartIfc)products.elementAt(j);

                    productNums.add(tempP.getPartNumber() + "(" + tempP.getVersionValue() + ")");

                }
                result.add(productNums);

                while(routeIterator1.hasNext())
                {
					Vector temp = new Vector();
					HashMap map;
					bsos = (BaseValueIfc[]) routeIterator1.next();

					lrpLink = (ListRoutePartLinkIfc) bsos[0];

					String routeListNum = lrpLink.getLeftBsoID();
					TechnicsRouteListIfc routeListIfc = (TechnicsRouteListIfc) pservice
							.refreshInfo(routeListNum);

					if (state != null && !state.equals("")) {
						if (routeListIfc.getRouteListState().equals(state)) {
							TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo) routeListIfc
									.getMaster();
							// ������е�����С�汾
							routeListIfc = (TechnicsRouteListIfc) getLatestVesion(masterinfo);
							
							route = (TechnicsRouteIfc) bsos[1];
							// System.out.println("��bsos��ȡ������route "
							// +route.getBsoID() );
							//CCBegin SS21
							//partmaster = (QMPartMasterIfc) bsos[2];
							partmaster = (QMPartIfc) bsos[2];
							//CCEnd SS21
							// ȡ���°汾���е�����
							part = (QMPartIfc) parts.get(partmaster.getBsoID());
							if (null == part)// add by guoxl for TD3779
								continue;

							temp.add(partmaster.getPartNumber());
							temp.add(partmaster.getPartName());
							temp.add(routeListIfc.getRouteListState());
							
							QMPartIfc product;
							Vector counts = new Vector();

							int count = lrpLink.getProductCount();
							String parentsBso = lrpLink.getParentPartID();

							for (int j = 0; j < products.size(); j++) {
								product = (QMPartIfc) products.elementAt(j);

								if (lrpLink.getParentPartID() == null
										|| !parentsBso.equals(product
												.getBsoID())) {

									counts.add(new Integer(0));

								} else {

									counts.add(new Integer(count));
								}
							}
							temp.add(new Integer(count));
							temp.add(routeListIfc.getVersionValue());

							
							String mainRoute = lrpLink.getMainStr();
							String sRoute = lrpLink.getSecondStr();
							String make = "";
							String zhuang = "";
							String sMake = "";
							String sZhuang = "";
							
							if (sRoute != null && !sRoute.equals("")) {
								int index = mainRoute.indexOf("=");
								int index2 = sRoute.indexOf("=");
								if (index > -1) {
									make = mainRoute.substring(0, index);
									zhuang = mainRoute.substring(index + 1,
											mainRoute.length());

								} else {
									make = mainRoute;

								}

								if (index2 > -1) {

									sMake = sRoute.substring(0, index2);
									sZhuang = sRoute.substring(index2 + 1,
											sRoute.length());
									make = "��:" + make + ";" + "��:" + sMake;
									zhuang = "��:" + zhuang + "��:" + sZhuang;
								} else {

									make = "��:" + make + ";" + "��:"+ sRoute;
									if(!zhuang.equals(""))
									   zhuang = "��:" + zhuang;
								}

							} else {

								if (mainRoute != null && !mainRoute.equals("")) {
									int index = mainRoute.indexOf("=");
									if (index > -1) {
										make = mainRoute.substring(0, index);
										zhuang = mainRoute.substring(index + 1,
												mainRoute.length());
									} else {
										make = mainRoute;
									}
								}
							}
							temp.add(make);
							temp.add(zhuang);
							
							temp.add(routeListIfc.getRouteListNumber());
							temp.add(routeListIfc.getCreateTime().toString());
							content.add(temp);
						}
					}else{
						
						
						TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo) routeListIfc
						.getMaster();
				// ������е�����С�汾
				routeListIfc = (TechnicsRouteListIfc) getLatestVesion(masterinfo);
				
				route = (TechnicsRouteIfc) bsos[1];
				// System.out.println("��bsos��ȡ������route "
				// +route.getBsoID() );
				//CCBegin SS21
				//partmaster = (QMPartMasterIfc) bsos[2];
				partmaster = (QMPartIfc) bsos[2];
				//CCEnd SS21
				// ȡ���°汾���е�����
				part = (QMPartIfc) parts.get(partmaster.getBsoID());
				if (null == part)// add by guoxl for TD3779
					continue;

				temp.add(partmaster.getPartNumber());
				temp.add(partmaster.getPartName());
				temp.add(routeListIfc.getRouteListState());
				
				QMPartIfc product;
				Vector counts = new Vector();

				int count = lrpLink.getProductCount();
				String parentsBso = lrpLink.getParentPartID();

				for (int j = 0; j < products.size(); j++) {
					product = (QMPartIfc) products.elementAt(j);

					if (lrpLink.getParentPartID() == null
							|| !parentsBso.equals(product
									.getBsoID())) {

						counts.add(new Integer(0));

					} else {

						counts.add(new Integer(count));
					}
				}
				temp.add(new Integer(count));
				temp.add(routeListIfc.getVersionValue());

				
				String mainRoute = lrpLink.getMainStr();
				String sRoute = lrpLink.getSecondStr();
				String make = "";
				String zhuang = "";
				String sMake = "";
				String sZhuang = "";
				
				if (sRoute != null && !sRoute.equals("")) {
					int index = mainRoute.indexOf("=");
					int index2 = sRoute.indexOf("=");
					if (index > -1) {
						make = mainRoute.substring(0, index);
						zhuang = mainRoute.substring(index + 1,
								mainRoute.length());

					} else {
						make = mainRoute;

					}

					if (index2 > -1) {

						sMake = sRoute.substring(0, index2);
						sZhuang = sRoute.substring(index2 + 1,
								sRoute.length());
						make = "��:" + make + ";" + "��:" + sMake;
						zhuang = "��:" + zhuang + "��:" + sZhuang;
					} else {

						make = "��:" + make + ";" + "��:"+ sRoute;
						if(!zhuang.equals(""))
						   zhuang = "��:" + zhuang;
					}

				} else {

					if (mainRoute != null && !mainRoute.equals("")) {
						int index = mainRoute.indexOf("=");
						if (index > -1) {
							make = mainRoute.substring(0, index);
							zhuang = mainRoute.substring(index + 1,
									mainRoute.length());
						} else {
							make = mainRoute;
						}
					}
				}
				temp.add(make);
				temp.add(zhuang);
				
				temp.add(routeListIfc.getRouteListNumber());
				temp.add(routeListIfc.getCreateTime().toString());
				content.add(temp);
						
					
					}
				}
//                result.add(content);	 
                
                return content;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
	 * ͳ���ۺ�·�߷ֲ�
	 * 
	 * @param mDepartment
	 *            String ���쵥λid
	 * @param cDepartment
	 *            String װ�䵥λid
	 * @param parts
	 *            Vector Ҫͳ�Ƶ��Ӽ�
	 * @param products
	 * Vector ��Ʒ�ļ��� @
	 * @return Collection vector���ϣ�
	 *         ������ϡ�ÿ����������һ��Vector.���η��㲿���������ڣ��㲿��id���㲿����ţ��㲿�����ƣ�
	 *         �汾�������ţ��㲿���ڲ�Ʒ���������ϣ�·��������·�߼��ϣ�����·�߼���ÿ����Ŀ��������װ��2��·�ߣ�
	 */
    public Collection getColligationRoutes(String mDepartment, String cDepartment, Vector parts, Vector products)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("����·�߷���getColligationRoutes���� :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + "  parts=" + parts + "  products  =" + products);
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        boolean showAll = false;
        Vector partInfos = new Vector();
        Vector productInfos = new Vector();
        if(parts == null || parts.size() == 0)
        {
            for(int i = 0;i < products.size();i++)
            {
                productInfos.add(pservice.refreshInfo((String)products.elementAt(i)));
            }
            partInfos = getAllSubParts(productInfos);
            showAll = true;
        }
        if(products == null || products.size() == 0)
        {
            for(int i = 0;i < parts.size();i++)
            {
                this.addToVector(partInfos, (QMPartIfc)pservice.refreshInfo((String)parts.elementAt(i)));
                // partInfos.add(pservice.refreshInfo( (String) parts.elementAt(i)));
            }
            productInfos = this.getAllParentProduct(partInfos);
        }
        if(parts != null && parts.size() != 0 && products != null && products.size() != 0)
        {
            for(int i = 0;i < parts.size();i++)
            {
                this.addToVector(partInfos, (QMPartIfc)pservice.refreshInfo((String)parts.elementAt(i)));
            }
            for(int i = 0;i < products.size();i++)
            {
                productInfos.add(pservice.refreshInfo((String)products.elementAt(i)));
            }
        }
        try
        {
            //5.25����Ϊû���㲿��Ҳ��ʾ
            return getColligationRoutesDetial(mDepartment, cDepartment, partInfos, productInfos, true);
        }catch(Exception e)
        {
            e.printStackTrace();
            throw new QMException(e);
        }
    }

    /**
     * �õ���ǰ���ŵ������Ӳ���
     * @param results Vector �������
     * @param depart BaseValueIfc ��ǰ���� @
     * @return Vector �������
     */
    private Vector getAllSubDepartMentID(Vector results, BaseValueIfc depart)throws QMException
    {
        CodingManageService cmService = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
        results.add(depart.getBsoID());
        if(depart instanceof CodingClassificationIfc)
        {
            Collection col = cmService.findDirectCodingClassification((CodingClassificationIfc)depart, true);

            if(col != null && col.size() > 0)
            {
                Iterator i = col.iterator();
                while(i.hasNext())
                {

                    //��õ�ǰ���ŵ������Ӳ���
                    getAllSubDepartMentID(results, (BaseValueIfc)i.next());

                }
            }
        }
        return results;
    }

    /**
     * �����ۺ�·��
     * @param mDepartment String ���쵥λ��id
     * @param cDepartment String װ�䵥λ��id
     * @param parts Vector �Ӽ�����
     * @param products Vector �������
     * @param showAll boolean �Ƿ���ʾ���в�Ʒ�ṹ��ֻ�е������ۺ�·�߿ͻ���û�����ұߣ��첿������ʱΪtrue @
     * @return Collection ������ϡ�ÿ����������һ��Vector.���η��㲿���������ڣ��㲿��id���㲿����ţ��㲿�����ƣ� �汾�������ţ��㲿���ڲ�Ʒ���������ϣ�·��������·�߼��ϣ�����·�߼���ÿ����Ŀ��������װ��2��·�ߣ�
     */
    private Collection getColligationRoutesDetial(String mDepartment, String cDepartment, Vector parts, Vector products, boolean showAll)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("����·�߷���getColligationRoutesDetial���� :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + "  parts=" + parts + "  products  =" + products);
        }

        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)partService.findPartConfigSpecIfc();
        HashMap bomMap = new HashMap();
        Iterator i = parts.iterator();
        Vector result = new Vector();

        Vector content = new Vector();

        QMPartIfc part;
        Vector mdepartIDs = new Vector();
        Vector cdepartIDs = new Vector();
        if(mDepartment != null && !mDepartment.trim().equals(""))
        {
            BaseValueIfc base = (BaseValueIfc)pservice.refreshInfo(mDepartment);
            mdepartIDs = this.getAllSubDepartMentID(mdepartIDs, base);
        }
        if(cDepartment != null && !cDepartment.trim().equals(""))
        {
            BaseValueIfc base = (BaseValueIfc)pservice.refreshInfo(cDepartment);
            cdepartIDs = this.getAllSubDepartMentID(cdepartIDs, base);
        }
        while(i.hasNext())
        {
            part = (QMPartIfc)i.next();
            Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs, cdepartIDs, part.getMasterBsoID());
            if(VERBOSE)
            {
                System.out.println("����·�߷���getRouteByPartAndDep���� :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + " partMasterID=" + part.getMasterBsoID() + "�õ������"
                        + routeAndLinks);

                //�����Ҫ��ʾ������Ʒ�ṹ����ǰ�첿����û��·�ߣ���ֻ��ʾ��Ʒ�ṹ��Ϣ
            }
            if(showAll && (routeAndLinks == null || routeAndLinks.size() == 0))
            {
                Vector temp = new Vector();
                temp.add(part.getLifeCycleState().getDisplay());
                temp.add(part.getBsoID());
                temp.add(part.getPartNumber());
                temp.add(part.getPartName());
                temp.add(part.getVersionValue());
                temp.add("");
                QMPartIfc product;
                Vector counts = new Vector();

                temp.add(counts);
                temp.add(new Integer(1));
                Vector makeBranchs = new Vector();
                Vector tempRoute = new Vector();
                tempRoute.add("");
                tempRoute.add("");
                makeBranchs.add(tempRoute);
                temp.add(makeBranchs);
                temp.add("");
                content.add(temp);
            }else
            {

                BaseValueIfc[] bsos;
                TechnicsRouteIfc route;
                ListRoutePartLinkIfc lrpLink;

                HashMap parentsMap = new HashMap();
                Vector productNums = new Vector();

                //add by guoxl
                //��ò��ظ��ĸ�������
                Iterator routeIterator1 = routeAndLinks.iterator();
                while(routeIterator1.hasNext())
                {
                    bsos = (BaseValueIfc[])routeIterator1.next();
                    lrpLink = (ListRoutePartLinkIfc)bsos[0];

                    if(lrpLink.getParentPartID() != null && !parentsMap.containsKey(lrpLink.getParentPartID()))
                    {

                        parentsMap.put(lrpLink.getParentPartID(), lrpLink.getParentPart());
                    }

                }
                products.clear();
                products = new Vector(parentsMap.values());

                for(int j = 0;j < products.size();j++)
                {
                    QMPartIfc tempP = (QMPartIfc)products.elementAt(j);

                    productNums.add(tempP.getPartNumber() + "(" + tempP.getVersionValue() + ")");

                }
                result.add(productNums);
                //add end by guoxl
                Iterator routeIterator = routeAndLinks.iterator();
                while(routeIterator.hasNext())
                {
                    Vector temp = new Vector();
                    HashMap map;
                    bsos = (BaseValueIfc[])routeIterator.next();
                    lrpLink = (ListRoutePartLinkIfc)bsos[0];
                    route = (TechnicsRouteIfc)bsos[1];

                    temp.add(part.getLifeCycleState().getDisplay());
                    temp.add(part.getBsoID());
                    temp.add(part.getPartNumber());
                    temp.add(part.getPartName());
                    temp.add(part.getVersionValue());
                    temp.add(lrpLink.getParentPartNum());
                    QMPartIfc product;
                    Vector counts = new Vector();

                    String parentBsoid = lrpLink.getParentPartID();
                    int count = lrpLink.getCount();
                    for(int j = 0;j < products.size();j++)
                    {
                        product = (QMPartIfc)products.elementAt(j);
                        if(lrpLink.getParentPartID() == null || !parentBsoid.equals(product.getBsoID()))
                        {
                            counts.add(new Integer(0));
                        }else
                        {
                            counts.add(new Integer(count));
                        }

                    }
                    //add by guoxl end
                    temp.add(counts);
                    //                    map = (HashMap)getRouteBranchs(route.getBsoID());
                    //                    assembleBranchStr(temp, map);
                    temp.add(lrpLink.getMainStr());
                    temp.add(lrpLink.getSecondStr());
                    //add by guoxl 2008.12.9(���·�߱��ţ���ӵ�������)
                    String routeListNum = lrpLink.getLeftBsoID();
                    TechnicsRouteListIfc routeListIfc = (TechnicsRouteListIfc)pservice.refreshInfo(routeListNum);

                    TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo)routeListIfc.getMaster();
                    //������е�����С�汾
                    routeListIfc = (TechnicsRouteListIfc)getLatestVesion(masterinfo);
                    temp.add(routeListIfc.getRouteListNumber() + "(" + routeListIfc.getVersionValue() + ")");
                    //add end by guoxl--
                    temp.add(lrpLink.getRouteDescription());
                    content.add(temp);
                }
            }
        }
        result.add(content);
        return result;
    }
    /**
     * �����ۺ�·��
     * @param mDepartment String ���쵥λ��id
     * @param cDepartment String װ�䵥λ��id
     * @param parts Vector �Ӽ�����
     * @param products Vector �������
     * @param showAll boolean �Ƿ���ʾ���в�Ʒ�ṹ��ֻ�е������ۺ�·�߿ͻ���û�����ұߣ��첿������ʱΪtrue @
     * @return Collection ������ϡ�ÿ����������һ��Vector.���η��㲿���������ڣ��㲿��id���㲿����ţ��㲿�����ƣ� �汾�������ţ��㲿���ڲ�Ʒ���������ϣ�·��������·�߼��ϣ�����·�߼���ÿ����Ŀ��������װ��2��·�ߣ�
     */
    private Collection getColligationRoutesDetial(String mDepartment, String cDepartment, Vector parts, Vector products, boolean showAll,String state)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("����·�߷���getColligationRoutesDetial���� :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + "  parts=" + parts + "  products  =" + products);
        }

        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)partService.findPartConfigSpecIfc();
        HashMap bomMap = new HashMap();
        Iterator i = parts.iterator();
        Vector result = new Vector();

        Vector content = new Vector();

        QMPartIfc part;
        Vector mdepartIDs = new Vector();
        Vector cdepartIDs = new Vector();
        if(mDepartment != null && !mDepartment.trim().equals(""))
        {
            BaseValueIfc base = (BaseValueIfc)pservice.refreshInfo(mDepartment);
            mdepartIDs = this.getAllSubDepartMentID(mdepartIDs, base);
        }
        if(cDepartment != null && !cDepartment.trim().equals(""))
        {
            BaseValueIfc base = (BaseValueIfc)pservice.refreshInfo(cDepartment);
            cdepartIDs = this.getAllSubDepartMentID(cdepartIDs, base);
        }
        while(i.hasNext())
        {
            part = (QMPartIfc)i.next();
//          CCBegin SS23
          String comp=RouteClientUtil.getUserFromCompany();
          Collection routeAndLinks = null;
          //CCBegin SS45
          //if(comp.equals("zczx"))
          if(comp.equals("zczx")||comp.equals("cd"))
        	//CCEnd SS45
        	  routeAndLinks = this.getRouteByPartAndDepForZC(mdepartIDs, cdepartIDs, part.getBsoID());
          else
        	  //          	CCEnd SS23
        	  routeAndLinks = this.getRouteByPartAndDep(mdepartIDs, cdepartIDs, part.getMasterBsoID());
            if(VERBOSE)
            {
                System.out.println("����·�߷���getRouteByPartAndDep���� :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + " partMasterID=" + part.getMasterBsoID() + "�õ������"
                        + routeAndLinks);

                //�����Ҫ��ʾ������Ʒ�ṹ����ǰ�첿����û��·�ߣ���ֻ��ʾ��Ʒ�ṹ��Ϣ
            }
            if(showAll && (routeAndLinks == null || routeAndLinks.size() == 0))
            {
                Vector temp = new Vector();
                temp.add(part.getLifeCycleState().getDisplay());
                temp.add(part.getBsoID());
                temp.add(part.getPartNumber());
                temp.add(part.getPartName());
                temp.add(part.getVersionValue());
                temp.add("");
                QMPartIfc product;
                Vector counts = new Vector();

                temp.add(counts);
                temp.add(new Integer(1));
                Vector makeBranchs = new Vector();
                Vector tempRoute = new Vector();
                tempRoute.add("");
                tempRoute.add("");
                makeBranchs.add(tempRoute);
                temp.add(makeBranchs);
                temp.add("");
                content.add(temp);
            }else
            {

                BaseValueIfc[] bsos;
                TechnicsRouteIfc route;
                ListRoutePartLinkIfc lrpLink;

                HashMap parentsMap = new HashMap();
                Vector productNums = new Vector();

                //add by guoxl
                //��ò��ظ��ĸ�������
                Iterator routeIterator1 = routeAndLinks.iterator();
                while(routeIterator1.hasNext())
                {
                    bsos = (BaseValueIfc[])routeIterator1.next();
                    lrpLink = (ListRoutePartLinkIfc)bsos[0];

                    if(lrpLink.getParentPartID() != null && !parentsMap.containsKey(lrpLink.getParentPartID()))
                    {

                        parentsMap.put(lrpLink.getParentPartID(), lrpLink.getParentPart());
                    }

                }
                products.clear();
                products = new Vector(parentsMap.values());

                for(int j = 0;j < products.size();j++)
                {
                    QMPartIfc tempP = (QMPartIfc)products.elementAt(j);

                    productNums.add(tempP.getPartNumber() + "(" + tempP.getVersionValue() + ")");

                }
                result.add(productNums);
                //add end by guoxl
                Iterator routeIterator = routeAndLinks.iterator();
                while(routeIterator.hasNext())
                {
					Vector temp = new Vector();
					HashMap map;
					bsos = (BaseValueIfc[]) routeIterator.next();
					lrpLink = (ListRoutePartLinkIfc) bsos[0];

					String routeListNum = lrpLink.getLeftBsoID();
					TechnicsRouteListIfc routeListIfc = (TechnicsRouteListIfc) pservice
							.refreshInfo(routeListNum);

					if (state != null && !state.equals("")) {
						if (routeListIfc.getRouteListState().equals(state)) {
							TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo) routeListIfc
									.getMaster();
							// ������е�����С�汾
							routeListIfc = (TechnicsRouteListIfc) getLatestVesion(masterinfo);


							route = (TechnicsRouteIfc) bsos[1];

							temp.add(part.getPartNumber());
							temp.add(part.getPartName());

							QMPartIfc product;
							Vector counts = new Vector();

							String parentBsoid = lrpLink.getParentPartID();
							int count = lrpLink.getProductCount();
							for (int j = 0; j < products.size(); j++) {
								product = (QMPartIfc) products.elementAt(j);
								if (lrpLink.getParentPartID() == null
										|| !parentBsoid.equals(product
												.getBsoID())) {
									counts.add(new Integer(0));
								} else {
									counts.add(new Integer(count));
								}

							}
							// add by guoxl end
							temp.add(routeListIfc.getRouteListState());
							temp.add(new Integer(count));
							
							temp.add(routeListIfc.getVersionValue());
							// map = (HashMap)getRouteBranchs(route.getBsoID());
							// assembleBranchStr(temp, map);
							String mainRoute = lrpLink.getMainStr();
							String sRoute = lrpLink.getSecondStr();
							String make = "";
							String zhuang = "";
							String sMake = "";
							String sZhuang = "";
							
							
							if (sRoute != null && !sRoute.equals("")) {
								int index = mainRoute.indexOf("=");
								int index2 = sRoute.indexOf("=");
								if (index > -1) {
									make = mainRoute.substring(0, index);
									zhuang = mainRoute.substring(index + 1,
											mainRoute.length());

								} else {
									make = mainRoute;

								}

								if (index2 > -1) {

									sMake = sRoute.substring(0, index2);
									sZhuang = sRoute.substring(index2 + 1,
											sRoute.length());
									make = "��:" + make + ";" + "��:" + sMake;
									zhuang = "��:" + zhuang + "��:" + sZhuang;
								} else {

									make = "��:" + make + ";" + "��:"+ sRoute;
									if(!zhuang.equals(""))
									   zhuang = "��:" + zhuang;
								}

							} else {

								if (mainRoute != null && !mainRoute.equals("")) {
									int index = mainRoute.indexOf("=");
									if (index > -1) {
										make = mainRoute.substring(0, index);
										zhuang = mainRoute.substring(index + 1,
												mainRoute.length());
									} else {
										make = mainRoute;
									}
								}
							}
							temp.add(make);
							temp.add(zhuang);
							temp.add(masterinfo.getRouteListNumber());
							temp.add(routeListIfc.getCreateTime().toString());
							content.add(temp);
						}
					} else {

						TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo) routeListIfc
								.getMaster();
						// ������е�����С�汾
						routeListIfc = (TechnicsRouteListIfc) getLatestVesion(masterinfo);


						route = (TechnicsRouteIfc) bsos[1];

						temp.add(part.getPartNumber());
						temp.add(part.getPartName());

						QMPartIfc product;
						Vector counts = new Vector();

						String parentBsoid = lrpLink.getParentPartID();
						int count = lrpLink.getProductCount();
						for (int j = 0; j < products.size(); j++) {
							product = (QMPartIfc) products.elementAt(j);
							if (lrpLink.getParentPartID() == null
									|| !parentBsoid.equals(product.getBsoID())) {
								counts.add(new Integer(0));
							} else {
								counts.add(new Integer(count));
							}

						}
						// add by guoxl end
						temp.add(routeListIfc.getRouteListState());
						temp.add(new Integer(count));
						temp.add(routeListIfc.getVersionValue());
						// map = (HashMap)getRouteBranchs(route.getBsoID());
						// assembleBranchStr(temp, map);
						String mainRoute = lrpLink.getMainStr();
						String sRoute = lrpLink.getSecondStr();
						String make = "";
						String zhuang = "";
						String sMake = "";
						String sZhuang = "";
						
						if (sRoute != null && !sRoute.equals("")) {
							int index2 = sRoute.indexOf("=");
							int index = mainRoute.indexOf("=");
							if (index > -1) {
								make = mainRoute.substring(0, index);
								zhuang = mainRoute.substring(index + 1,
										mainRoute.length());
							} else {
								make = mainRoute;
							}

							if (index2 > -1) {

								sMake = sRoute.substring(0, index2);
								sZhuang = sRoute.substring(index2 + 1, sRoute
										.length());
								make = "��:" + make + ";" + "��:" + sMake;
								zhuang = "��:" + zhuang + "��:" + sZhuang;
							} else {

								make = "��:" + mainRoute + ";" + "��:" + sRoute;
								zhuang = "��:" + zhuang;
							}

						}
						
						if (mainRoute != null && !mainRoute.equals("")) {
							int index = mainRoute.indexOf("=");
							if (index > -1) {
								make = mainRoute.substring(0, index);
								zhuang = mainRoute.substring(index + 1,
										mainRoute.length());
							} else {
								make = mainRoute;
							}
						}
						temp.add(make);
						temp.add(zhuang);
						temp.add(masterinfo.getRouteListNumber());
						temp.add(routeListIfc.getCreateTime().toString());
						content.add(temp);
						 System.out.println("aaaaaaaaa============="+temp);
					}
				}
            }
        }
//        result.add(content);
       
        return content;
    }
    /**
	 * ��·�ߴ���ӽ������
	 * 
	 * @param result
	 *            Vector �����
	 * @param map
	 *            HashMap ·�ߴ�����
	 * @return Vector
	 */
    private Vector assembleBranchStr(Vector result, HashMap map)
    {
        if(VERBOSE)
        {
            System.out.println("����·�߷���assembleBranchStr���� :" + "map = " + map);

        }
        Vector makeBranchs = new Vector();
        if(map == null || map.size() == 0)
        {
            Vector temp = new Vector();
            temp.add("");
            temp.add("");
            makeBranchs.add(temp);
            result.add(new Integer(1));
            result.add(makeBranchs);
            return result;
        }
        Iterator values = map.values().iterator();
        while(values.hasNext())
        {
            Vector temp = new Vector();
            String makeStr = "";
            String assemStr = "";
            Object[] objs = (Object[])values.next();
            Vector makeNodes = (Vector)objs[0]; //����ڵ�
            RouteNodeIfc asseNode = (RouteNodeIfc)objs[1]; //װ��ڵ�
            if(makeNodes != null && makeNodes.size() > 0)
            {
                for(int m = 0;m < makeNodes.size();m++)
                {
                    RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
                    if(makeStr == "")
                    {
                        makeStr = makeStr + node.getNodeDepartmentName();
                    }else
                    {
                        makeStr = makeStr + "��" + node.getNodeDepartmentName();
                    }
                }
            }
            if(asseNode != null)
            {
                assemStr = asseNode.getNodeDepartmentName();
            }
            if(makeStr == null || makeStr.equals(""))
            {
                makeStr = "";
            }
            if(assemStr == null || assemStr.equals(""))
            {
                assemStr = "";
            }
            temp.add(makeStr);
            temp.add(assemStr);
            makeBranchs.add(temp);
            //      System.out.println("makeStr makeStr " + makeStr);
            //       System.out.println("assemStr assemStr " + assemStr);
        }
        result.add(new Integer(makeBranchs.size()));
        result.add(makeBranchs);
        if(VERBOSE)
        {
            System.out.println("����·�߷���assembleBranchStr���� :" + "makeBranchs = " + makeBranchs);
        }
        return result;
    }

    /**
     * �������壩zz ������ӣ������ݿͻ���ʾ·�ߴ� ��·�ߴ���ӽ������,�ݿͻ����ã�·�ߴ�������branchNodeֱ�Ӵ�branch������ȡ���ַ���
     * @param result Vector �����
     * @param map HashMap ·�ߴ�����
     * @return Vector
     */
    private Vector assembleBranchStrForThin(Vector result, HashMap map)
    {
        if(VERBOSE)
        {
            System.out.println("����·�߷���assembleBranchStr���� :" + "map = " + map);

        }
        Vector makeBranchs = new Vector();
        if(map == null || map.size() == 0)
        {
            Vector temp = new Vector();
            temp.add("");
            temp.add("");
            makeBranchs.add(temp);
            result.add(new Integer(1));
            result.add(makeBranchs);
            return result;
        }
        Iterator values = map.values().iterator();
        while(values.hasNext())
        {
            Vector temp = new Vector();
            String makeStr = "";
            String assemStr = "";
            String unionStr = (String)values.next();
            //  System.out.println( "unionStr unionStr unionStr " + unionStr);
            if(unionStr != null)
            {
                StringTokenizer hh = new StringTokenizer(unionStr, "=");
                if(hh.hasMoreTokens())
                {
                    makeStr = hh.nextToken();
                    assemStr = hh.nextToken();
                }
            }
            //        System.out.println("���� ���� ���� " +makeStr );
            //            System.out.println("װ�� װ�� װ�� " +assemStr );

            //      Object[] objs = (Object[]) values.next();
            //      Vector makeNodes = (Vector) objs[0]; //����ڵ�
            //      RouteNodeIfc asseNode = (RouteNodeIfc) objs[1]; //װ��ڵ�
            //      if (makeNodes != null && makeNodes.size() > 0) {
            //        for (int m = 0; m < makeNodes.size(); m++) {
            //          RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
            //          if (makeStr == "") {
            //            makeStr = makeStr + node.getNodeDepartmentName();
            //          }
            //          else {
            //            makeStr = makeStr + "��" + node.getNodeDepartmentName();
            //          }
            //        }
            //      }
            //      if (asseNode != null) {
            //        assemStr = asseNode.getNodeDepartmentName();
            //      }
            //      if (makeStr == null || makeStr.equals("")) {
            //        makeStr = "";
            //      }
            //      if (assemStr == null || assemStr.equals("")) {
            //        assemStr = "";
            //      }
            temp.add(makeStr);
            temp.add(assemStr);
            makeBranchs.add(temp);
        }
        result.add(new Integer(makeBranchs.size()));
        result.add(makeBranchs);
        if(VERBOSE)
        {
            System.out.println("����·�߷���assembleBranchStr���� :" + "makeBranchs = " + makeBranchs);
        }
        return result;
    }

    private boolean isParentPart(QMPartIfc sub, QMPartIfc parent)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        return partService.isParentPart(sub, parent);
    }

    /**
     * �����Ӽ��ڲ�Ʒ��ʹ�õ�������ֻ�����丸���ڲ�Ʒ�е�������
     * @param parts Vector �Ӽ�
     * @param parent QMPartIfc ����
     * @param product QMPartIfc ��Ʒ @
     * @return int �ڲ�Ʒ��ʹ�õ�����
     * @see QMPartInfo
     */
    /**
     * delete by guoxl for ʵʩ���� 2010.10.29 public HashMap calCountInProduct(Vector parts, QMPartIfc parent, QMPartIfc product) { if(VERBOSE)
     * System.out.println("���� calCountInProduct   parts="+parts+" parent="+parent+" product="+product); StandardPartService partService = (StandardPartService) EJBServiceHelper.
     * getService(PART_SERVICE); PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc) partService.findPartConfigSpecIfc(); HashMap map = new HashMap(); List boms = null; List midleBoms = null;
     * try { //long before4 = System.currentTimeMillis(); String[] attrNames = {"quantity"}; boms = partService.setBOMList(product, attrNames, null, null, null, partConfigSpecIfc); // long after4 =
     * System.currentTimeMillis(); // System.out.println("��ʱcalCountInProduct====="+(after4 - before4)); } catch (Exception e) { throw new TechnicsRouteException(e); } try { //long before5 =
     * System.currentTimeMillis(); String[] attrNames = {"quantity"}; midleBoms = partService.setBOMList(parent, attrNames, null, null, null, partConfigSpecIfc); // long after5 =
     * System.currentTimeMillis(); // System.out.println("��ʱcalCountInProduct====="+(after5 - before5)); } catch (Exception ee) { throw new TechnicsRouteException(ee); } for (int i = 0; i <
     * parts.size(); i++) { QMPartIfc part = (QMPartIfc) parts.elementAt(i); map.put(part.getBsoID(), new Integer(this.calCountInProduct(part, parent, product, boms, midleBoms))); } return map; }
     */
    public HashMap calCountInProduct(Vector parts, QMPartIfc product)throws QMException
    {

        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)partService.findPartConfigSpecIfc();
        HashMap map = new HashMap();

        for(int i = 0;i < parts.size();i++)
        {

            QMPartIfc part = (QMPartIfc)parts.elementAt(i);

            String count = partService.getPartQuantity(product, part);

            if(count != null)
            {
                map.put(part.getBsoID(), new Integer(count));
            }else
            {
                map.put(part.getBsoID(), 0);
            }

        }
        return map;

    }

    /**
     * 20120109 xucy add
     * @param parts
     * @param parent
     * @param product
     * @return
     */
    public HashMap calCountInProduct(Vector parts, QMPartMasterIfc productMaseter)throws QMException
    {
        // QMPartIfc parent = filterPart(parentMaster.getBsoID());
        QMPartIfc product = filterPart(productMaseter.getBsoID());
        HashMap map = this.calCountInProduct(parts, product);
        return map;

    }

    /**
     * �㲿��������
     * @param master
     * @return 20120109 xucy add
     */
    private QMPartIfc filterPart(String master)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        QMPartIfc part = null;
        try
        {
            Class[] cla = {String.class};
            Object[] obj = {master};
            // return useServiceMethod("PersistService", "refreshInfo", cla,
            // obj);
//      delete guoxl QMPartMasterInfo partmaster = (QMPartMasterInfo)RequestHelper.request("PersistService", "refreshInfo", cla, obj);
            
            PersistService ps=(PersistService) EJBServiceHelper
			.getService("PersistService");
            QMPartMasterInfo partmaster = (QMPartMasterInfo)ps.refreshInfo(master);
            
            part = filteredIterationsOfByDefault(partmaster);
        }catch(Exception e)
        {
            e.printStackTrace();
            String message = e.getMessage();
            DialogFactory.showWarningDialog(null, message);
            return null;
        }
        return part;
    }

    /**
     * �����Ӽ��ڲ�Ʒ��ʹ�õ�������ֻ�����丸���ڲ�Ʒ�е�������
     * @param part QMPartIfc �Ӽ�
     * @param parent QMPartIfc ����
     * @param product QMPartIfc ��Ʒ @
     * @return int �ڲ�Ʒ��ʹ������
     * @see QMPartInfo
     */
    public int calCountInProduct(QMPartIfc part, QMPartIfc parent, QMPartIfc product)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        if(parent == null)
            return 0;
        String count = (String)partService.getPartQuantity(product, (QMPartMasterIfc)parent.getMaster(), part);
        if(VERBOSE)
            System.out.println(part.getBsoID() + part.getPartName() + "��" + parent.getBsoID() + parent.getPartName() + product.getBsoID() + product.getPartName() + "�е�����==" + count);
        if(count != null && !count.trim().equals(""))
            return new Integer(count).intValue();
        else
            return 0;
    }

    /**
     * �����Ӽ��ڲ�Ʒ��ʹ�õ�������ֻ�����丸���ڲ�Ʒ�е�������
     * @param part QMPartIfc �Ӽ�
     * @param parent QMPartIfc ����
     * @param product QMPartIfc ��Ʒ @
     * @return int �����Ӽ��ڲ�Ʒ��ʹ�õ���������
     */
    private int calCountInProduct(QMPartIfc part, QMPartIfc parent, QMPartIfc product, List list, List midleList)throws QMException
    {
        if(parent == null)
        {
            return 0;
        }
        String count = (String)getPartQuantity(product, (QMPartMasterIfc)parent.getMaster(), part, list, midleList);
        if(VERBOSE)
        {
            System.out.println(part.getBsoID() + part.getPartName() + "��" + parent.getBsoID() + parent.getPartName() + product.getBsoID() + product.getPartName() + "�е�����==" + count);
        }
        if(count != null && !count.trim().equals(""))
        {
            return new Integer(count).intValue();
        }else
        {
            return 0;
        }
    }

    /**
     * �����Ӽ��ڲ�Ʒ��ʹ�õ�����
     * @param part QMPartIfc �Ӽ�
     * @param parent QMPartIfc ���� @
     * @return int ����
     */
    private int calCountInProduct(QMPartIfc part, QMPartIfc parent, List list)
    {
        if(part.getPartNumber().equals(parent.getPartNumber()))
        {
            return 1;
        }
        String count = (String)getPartQuantity(parent, part, list);
        if(VERBOSE)
        {
            System.out.println(part.getBsoID() + part.getPartName() + "��" + parent.getBsoID() + parent.getPartName() + "�е�����==" + count);
        }
        if(count != null && !count.trim().equals(""))
        {
            return new Integer(count).intValue();
        }else
        {
            return 0;
        }
    }

    /**
     * ͨ�������װ�䵥λ�����쵥λ���·�ߣ�����������ļ���
     * @param mDepartment String ���쵥λ��id
     * @param cDepartment String װ�䵥λ��id
     * @param partMasterID String ��Ʒid @
     * @return Collection ������Ϊvector����ʽΪ��·�����㲿��������·�߰����㲿��
     */
    /*
     * delete by guoxl end on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����) private Collection getRouteByPartAndDep(Vector mDepartments, Vector cDepartments, String partMasterID) { if (VERBOSE) {
     * System.out.println("����·�߷���getRouteByPartAndDep���� :" + "mDepartment = " + mDepartments + "  cDepartment=" + cDepartments + " partMasterID=" + partMasterID);
     * 
     * } PersistService pservice = (PersistService) EJBServiceHelper. getPersistService();
     * 
     * QMQuery query = new QMQuery(this.LIST_ROUTE_PART_LINKBSONAME); int routeCount = query.appendBso(this.TECHNICSROUTE_BSONAME, true); int partCount = query.appendBso("QMPartMaster", true);
     * 
     * QueryCondition routeToLink = new QueryCondition("routeID", "bsoID"); query.addCondition(0, routeCount, routeToLink); query.addAND(); QueryCondition partToLink = new QueryCondition("rightBsoID",
     * "bsoID"); query.addCondition(0, partCount, partToLink); query.addAND(); QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, this.ROUTEALTER); query.addCondition(0,
     * cond4); query.addAND(); QueryCondition cond3 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString()); query.addCondition(0, cond3); if (partMasterID !=
     * null) { query.addAND(); QueryCondition partCond = new QueryCondition("bsoID", QueryCondition.EQUAL, partMasterID); query.addCondition(partCount, partCond);
     * 
     * } int nodeCount = query.appendBso(this.ROUTENODE_BSONAME, false); if (cDepartments.size() != 0 || mDepartments.size() != 0) { if (mDepartments.size() != 0 && cDepartments.size() == 0) {
     * query.addAND();
     * 
     * query.addLeftParentheses(); QueryCondition condm = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String) mDepartments. elementAt(0)); query.addCondition(nodeCount, condm); for
     * (int i = 1; i < mDepartments.size(); i++) { query.addOR(); QueryCondition condmt = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String) mDepartments.elementAt(i));
     * query.addCondition(nodeCount, condmt); } query.addRightParentheses();
     * 
     * query.addAND(); QueryCondition condm1 = new QueryCondition("routeType", QueryCondition.EQUAL, "MANUFACTUREROUTE"); query.addCondition(nodeCount, condm1);
     * 
     * } if (cDepartments.size() != 0 && mDepartments.size() == 0) { query.addAND();
     * 
     * query.addLeftParentheses(); QueryCondition condc = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String) cDepartments. elementAt(0)); query.addCondition(nodeCount, condc); for
     * (int i = 1; i < cDepartments.size(); i++) { query.addOR(); QueryCondition condct = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String) cDepartments.elementAt(i));
     * query.addCondition(nodeCount, condct);
     * 
     * } query.addRightParentheses();
     * 
     * query.addAND(); QueryCondition condc1 = new QueryCondition("routeType", QueryCondition.EQUAL, "ASSEMBLYROUTE"); query.addCondition(nodeCount, condc1); } if (mDepartments.size() != 0 &&
     * cDepartments.size() != 0) { query.addAND();
     * 
     * query.addLeftParentheses(); QueryCondition condc = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String) cDepartments. elementAt(0)); query.addCondition(nodeCount, condc); for
     * (int i = 1; i < cDepartments.size(); i++) { query.addOR(); QueryCondition condct = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String) cDepartments.elementAt(i));
     * query.addCondition(nodeCount, condct);
     * 
     * } query.addRightParentheses();
     * 
     * query.addAND(); QueryCondition condc1 = new QueryCondition("routeType", QueryCondition.EQUAL, "ASSEMBLYROUTE"); query.addCondition(nodeCount, condc1); int mNodeCount =
     * query.appendBso(this.ROUTENODE_BSONAME, false); query.addAND();
     * 
     * query.addLeftParentheses(); QueryCondition condm = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String) mDepartments. elementAt(0)); query.addCondition(nodeCount, condm); for
     * (int i = 1; i < mDepartments.size(); i++) { query.addOR(); QueryCondition condmt = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String) mDepartments.elementAt(i));
     * query.addCondition(mNodeCount, condmt); } query.addRightParentheses();
     * 
     * query.addAND(); QueryCondition condm1 = new QueryCondition("routeType", QueryCondition.EQUAL, "MANUFACTUREROUTE"); query.addCondition(mNodeCount, condm1);
     * 
     * } } query.addAND(); QueryCondition nodetoRoute = new QueryCondition("routeID", "bsoID"); query.addCondition(nodeCount, routeCount, nodetoRoute); // query.addOrderBy(partCount, "partNumber");
     * query.setDisticnt(true); if (VERBOSE) { System.out.println("getRouteByPartAndDep ��ѯ����Ϊ�� " + query.getDebugSQL()); } return pservice.findValueInfo(query);
     * 
     * }delete end by guoxl end on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)
     */
    //add by guoxl on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)
    private Collection getRouteByPartAndDep(Vector mDepartments, Vector cDepartments, String partMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("����·�߷���getRouteByPartAndDep���� :" + "mDepartment = " + mDepartments + "  cDepartment=" + cDepartments + " partMasterID=" + partMasterID);

        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //���������в������ݣ�����·���������������·�߱������������Ϣ
        QMQuery query = new QMQuery(this.LIST_ROUTE_PART_LINKBSONAME);
        int routeCount = query.appendBso(this.TECHNICSROUTE_BSONAME, true);
        //CCBegin SS21
        //int partCount = query.appendBso("QMPartMaster", true);
        int partCount = query.appendBso("QMPart", true);
        //CCEnd SS21

        QueryCondition routeToLink = new QueryCondition("routeID", "bsoID");
        query.addCondition(0, routeCount, routeToLink);//���һ����͵ڶ�������ӹ�������routeID=bsoID
        query.addAND();
        QueryCondition partToLink = new QueryCondition("rightBsoID", "bsoID");
        query.addCondition(0, partCount, partToLink);//���һ����͵���������ӹ�������rightBsoID=bsoID
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, this.ROUTEALTER);
        query.addCondition(0, cond4);
        query.addAND();
        QueryCondition cond3 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(0, cond3);
        if(partMasterID != null)
        {
            query.addAND();
            QueryCondition partCond = new QueryCondition("bsoID", QueryCondition.EQUAL, partMasterID);
            query.addCondition(partCount, partCond);

        }
        int nodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
        //add by guoxl on 2008-12-19(���ʵʩ����)
        int mNodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
        //add by guoxl end on 2008-12-19(���ʵʩ����)
        if(cDepartments.size() != 0 || mDepartments.size() != 0)
        {
            if(mDepartments.size() != 0 && cDepartments.size() == 0)
            {
                query.addAND();

                query.addLeftParentheses();
                QueryCondition condm = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String)mDepartments.elementAt(0));
                query.addCondition(nodeCount, condm);

                query.addRightParentheses();

                query.addAND();
                QueryCondition condm1 = new QueryCondition("routeType", QueryCondition.EQUAL, "MANUFACTUREROUTE");

                query.addCondition(nodeCount, condm1);

            }
            if(cDepartments.size() != 0 && mDepartments.size() == 0)
            {
                query.addAND();

                query.addLeftParentheses();
                QueryCondition condc = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String)cDepartments.elementAt(0));
                query.addCondition(nodeCount, condc);

                query.addRightParentheses();

                query.addAND();
                QueryCondition condc1 = new QueryCondition("routeType", QueryCondition.EQUAL, "ASSEMBLYROUTE");
                query.addCondition(nodeCount, condc1);
            }
            if(mDepartments.size() != 0 && cDepartments.size() != 0)
            {
                query.addAND();

                query.addLeftParentheses();
                QueryCondition condc = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String)cDepartments.elementAt(0));

                //�ڱ�routeNode���������Ϊָ��װ�䵥λ�ļ���
                query.addCondition(nodeCount, condc);

                query.addRightParentheses();

                query.addAND();
                QueryCondition condc1 = new QueryCondition("routeType", QueryCondition.EQUAL, "ASSEMBLYROUTE");
                query.addCondition(nodeCount, condc1);

                query.addAND();

                query.addLeftParentheses();
                QueryCondition condm = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String)mDepartments.elementAt(0));

                query.addCondition(mNodeCount, condm);

                query.addRightParentheses();

                query.addAND();
                QueryCondition condm1 = new QueryCondition("routeType", QueryCondition.EQUAL, "MANUFACTUREROUTE");
                query.addCondition(mNodeCount, condm1);

            }
        }
        query.addAND();
        QueryCondition nodetoRoute = new QueryCondition("routeID", "bsoID");
        query.addCondition(nodeCount, routeCount, nodetoRoute);
        //add by guoxl  on 2008-12-19(���ʵʩ����)
        query.addAND();
        query.addCondition(mNodeCount, routeCount, nodetoRoute);
        //add by guoxl end on 2008-12-19(���ʵʩ����)
        // query.addOrderBy(partCount, "partNumber");
        query.setDisticnt(true);
        if(VERBOSE)
        {
            System.out.println("getRouteByPartAndDep ��ѯ����Ϊ�� " + query.getDebugSQL());
        }

        return pservice.findValueInfo(query);

    }//add end by guoxl on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)

//  CCBegin SS23
  private Collection getRouteByPartAndDepForZC(Vector mDepartments, Vector cDepartments, String partMasterID)throws QMException
  {
      if(VERBOSE)
      {
          System.out.println("����·�߷���getRouteByPartAndDep���� :" + "mDepartment = " + mDepartments + "  cDepartment=" + cDepartments + " partMasterID=" + partMasterID);

      }
      PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
      //���������в������ݣ�����·���������������·�߱������������Ϣ
      QMQuery query = new QMQuery(this.LIST_ROUTE_PART_LINKBSONAME);
      int routeCount = query.appendBso(this.TECHNICSROUTE_BSONAME, true);
      int partCount = query.appendBso("QMPart", true);

      QueryCondition routeToLink = new QueryCondition("routeID", "bsoID");
      query.addCondition(0, routeCount, routeToLink);//���һ����͵ڶ�������ӹ�������routeID=bsoID
      query.addAND();
      QueryCondition partToLink = new QueryCondition("rightBsoID", "bsoID");
      query.addCondition(0, partCount, partToLink);//���һ����͵���������ӹ�������rightBsoID=bsoID
      query.addAND();
      QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, this.ROUTEALTER);
      query.addCondition(0, cond4);
      query.addAND();
      QueryCondition cond3 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
      query.addCondition(0, cond3);
      if(partMasterID != null)
      {
          query.addAND();
          QueryCondition partCond = new QueryCondition("bsoID", QueryCondition.EQUAL, partMasterID);
          query.addCondition(partCount, partCond);
      }
      int nodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
      //add by guoxl on 2008-12-19(���ʵʩ����)
      int mNodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
      //add by guoxl end on 2008-12-19(���ʵʩ����)
      if(cDepartments.size() != 0 || mDepartments.size() != 0)
      {
          if(mDepartments.size() != 0 && cDepartments.size() == 0)
          {
              query.addAND();

              query.addLeftParentheses();
              QueryCondition condm = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String)mDepartments.elementAt(0));
              query.addCondition(nodeCount, condm);

              query.addRightParentheses();

              query.addAND();
              QueryCondition condm1 = new QueryCondition("routeType", QueryCondition.EQUAL, "MANUFACTUREROUTE");

              query.addCondition(nodeCount, condm1);
          }
          if(cDepartments.size() != 0 && mDepartments.size() == 0)
          {
              query.addAND();

              query.addLeftParentheses();
              QueryCondition condc = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String)cDepartments.elementAt(0));
              query.addCondition(nodeCount, condc);

              query.addRightParentheses();

              query.addAND();
              QueryCondition condc1 = new QueryCondition("routeType", QueryCondition.EQUAL, "ASSEMBLYROUTE");
              query.addCondition(nodeCount, condc1);
          }
          if(mDepartments.size() != 0 && cDepartments.size() != 0)
          {
              query.addAND();

              query.addLeftParentheses();
              QueryCondition condc = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String)cDepartments.elementAt(0));

              //�ڱ�routeNode���������Ϊָ��װ�䵥λ�ļ���
              query.addCondition(nodeCount, condc);

              query.addRightParentheses();

              query.addAND();
              QueryCondition condc1 = new QueryCondition("routeType", QueryCondition.EQUAL, "ASSEMBLYROUTE");
              query.addCondition(nodeCount, condc1);

              query.addAND();

              query.addLeftParentheses();
              QueryCondition condm = new QueryCondition("nodeDepartment", QueryCondition.EQUAL, (String)mDepartments.elementAt(0));

              query.addCondition(mNodeCount, condm);

              query.addRightParentheses();

              query.addAND();
              QueryCondition condm1 = new QueryCondition("routeType", QueryCondition.EQUAL, "MANUFACTUREROUTE");
              query.addCondition(mNodeCount, condm1);
          }
      }
      query.addAND();
      QueryCondition nodetoRoute = new QueryCondition("routeID", "bsoID");
      query.addCondition(nodeCount, routeCount, nodetoRoute);
      //add by guoxl  on 2008-12-19(���ʵʩ����)
      query.addAND();
      query.addCondition(mNodeCount, routeCount, nodetoRoute);
      //add by guoxl end on 2008-12-19(���ʵʩ����)
      // query.addOrderBy(partCount, "partNumber");
      query.setDisticnt(true);
      if(VERBOSE)
      {
          System.out.println("getRouteByPartAndDep ��ѯ����Ϊ�� " + query.getDebugSQL());
      }
      return pservice.findValueInfo(query);

  }//add end by guoxl on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)
//  CCEnd SS23
    
    /**
     * �õ������㲿���ĸ������ϲ���ͬ��
     * @param subs Vector �Ӽ����� @
     * @return Vector HashMap���ϣ�key:partBsoID,value:QMPartIfc �ϲ���ĸ�������
     */
    private Vector getAllParentProduct(Vector subs)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        HashMap subMap = new HashMap();
        QMPartIfc part;
        for(int i = 0;i < subs.size();i++)
        {
            part = (QMPartIfc)subs.elementAt(i);
            Collection temparts = (Collection)partService.getParentProduct(part);
            QMPartIfc temppart;
            if(temparts == null || temparts.size() == 0)
            {
                if(VERBOSE)
                {
                    System.out.println("����Ϊ0");
                }
                continue;
            }
            Iterator ite1 = temparts.iterator();
            while(ite1.hasNext())
            {
                temppart = (QMPartIfc)ite1.next();
                if(!subMap.containsKey(temppart.getBsoID()))
                {
                    subMap.put(temppart.getBsoID(), temppart);
                }
            }

        }
        return new Vector(subMap.values());
    }

    private Vector getAllParentProductOnce(Vector subParts)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        Collection partColl = (Collection)partService.getParentProduct(subParts);
        HashMap subMap = new HashMap();
        Iterator ite1 = partColl.iterator();
        while(ite1.hasNext())
        {
            QMPartIfc temppart = (QMPartIfc)ite1.next();
            if(!subMap.containsKey(temppart.getBsoID()))
            {
                subMap.put(temppart.getBsoID(), temppart);
            }
        }

        return new Vector(subMap.values());
    }

    /**
     * �õ������Ӽ����ϲ���ͬ��
     * @param products Vector ��Ʒ���� @
     * @return Vector �ϲ�����Ӽ�����
     */
    private Vector getAllSubParts(Vector products)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        EnterprisePartService enterprisePartService = (EnterprisePartService)EJBServiceHelper.getService("EnterprisePartService");
        PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
        // HashMap subMap = new HashMap();
        Vector result = new Vector();
        QMPartIfc part;
        for(int i = 0;i < products.size();i++)
        {
            part = (QMPartIfc)products.elementAt(i);
            QMPartIfc[] temparts = (QMPartIfc[])enterprisePartService.getAllSubPartsByConfigSpec((QMPartMasterIfc)part.getMaster(), configSpecIfc);
            QMPartIfc temppart;
            if(temparts == null || temparts.length == 0)
            {
                continue;
            }
            for(int j = 0;j < temparts.length;j++)
            {
                temppart = (QMPartIfc)temparts[j];
                this.addToVector(result, temppart);
            }
        }
        return result;
    }

    /**
     * ��������㲿���������
     * @param vec Vector
     * @param part QMPartIfc
     */
    private void addToVector(Vector vec, QMPartIfc part)throws QMException
    {
        if(vec.size() == 0)
        {
            vec.add(part);
            return;
        }else
        {
            for(int i = 0;i < vec.size();i++)
            {
                QMPartIfc exitPart = (QMPartIfc)vec.elementAt(i);
                if(part.getBsoID().equals(exitPart.getBsoID()))
                {
                    return;
                }else if(part.getPartNumber().compareTo(exitPart.getPartNumber()) < 0)
                {
                    vec.add(i, part);
                    return;
                }
            }
        }
        vec.add(vec.size(), part);

    }

    /**
     * �����㲿�������Ƿ���·��
     * @param vec Vector ���ֵ���󼯺� @
     * @return Vector[] ���������vector:<br> 1.successVec vector:��ŵ���QMPartIfc ���ֵ���� <br> 2.failVec vector::��ŵ���QMPartIfc ���ֵ���� <br> ��·�߻���·������ļ���
     */
    public Vector[] isHasRoute(Vector vec)throws QMException
    {
        Vector successVec = new Vector();
        Vector failVec = new Vector();
        QMPartIfc part;
        for(int j = 0;j < vec.size();j++)
        {
            part = (QMPartIfc)vec.elementAt(j);
            if(!isHasRoute(part.getMasterBsoID()))
            {
                failVec.add(part);
            }else
            {
                successVec.add(part);
            }
        }
        return new Vector[]{successVec, failVec};
    }

    /**
     * �����㲿�������Ƿ���·��
     * @param partMasterID String ���ID @
     * @return boolean ��·�߷���true ���򷵻�false
     */
    public boolean isHasRoute(String partMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getPartLevelRoutes, partMasterID = " + partMasterID);
        }
        if(partMasterID == null || partMasterID.trim().length() == 0)
        {
            throw new QMException("�����������");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond4);
        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(0, cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
        query.addCondition(0, j, cond6);
        //SQL������������
        query.setDisticnt(true);
        //����ListRoutePartLinkIfc
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's getPartLevelRoutes SQL = " + query.getDebugSQL());
        }
        Collection coll = pservice.findValueInfo(query);
        if(coll != null && coll.size() > 0)
        {
            return true;
        }else
        {
            return false;
        }

    }

    /**
     * ��ȡ���㲿���ڸ��㲿���е�ʹ��������
     * @param parentPartIfc QMPartIfc ���㲿����
     * @param childPartIfc QMPartIfc ���㲿���� @
     * @return String ʹ��������
     * @see QMPartInfo
     */
    private String getPartQuantity(QMPartIfc parentPartIfc, QMPartIfc childPartIfc, List childParts)
    {
        if(parentPartIfc.getPartNumber().equals(childPartIfc.getPartNumber()))
            return "1";
        //��ȡ���㲿��ͳ�Ʊ�
        Object[] childPartsArray = childParts.toArray();
        //��ȡָ�����㲿����ʹ��������
        for(int i = 0;i < childPartsArray.length;i++)
        {
            if(childPartsArray[i] instanceof Object[])
            {
                Object[] childPart = (Object[])childPartsArray[i];
                if(childPart[0].equals(childPartIfc.getBsoID()))
                    return childPart[3].toString();
            }
        }
        return null;
    }

    /**
     * ��ȡ���㲿���ڸ��㲿���е�ʹ��������
     * @param parentPartIfc QMPartIfc ���㲿����
     * @param middlePartMasterIfc QMPartMasterIfc �м��㲿��������Ϣ��
     * @param childPartIfc QMPartIfc ���㲿���� @
     * @return String ʹ��������
     */
    private String getPartQuantity(QMPartIfc parentPartIfc, QMPartMasterIfc middlePartMasterIfc, QMPartIfc childPartIfc, List childParts, List midleList)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        //��ȡ��ǰ�û������ù淶��
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)partService.findPartConfigSpecIfc();
        String middleQuantity = null;
        QMPartIfc middlePartIfc = null;
        if(parentPartIfc.getPartNumber().equals(middlePartMasterIfc.getPartNumber()))
        {
            middleQuantity = "1";
            middlePartIfc = parentPartIfc;
        }else
        {
            Object[] childPartsArray = childParts.toArray();

            //��ȡ�м��㲿����ʹ��������
            for(int i = 0;i < childPartsArray.length;i++)
            {
                if(childPartsArray[i] instanceof Object[])
                {
                    Object[] childPart = (Object[])childPartsArray[i];
                    if(childPart[1].equals(middlePartMasterIfc.getPartNumber()))
                        middleQuantity = childPart[3].toString();
                }
            }
            if(middleQuantity == null || middleQuantity.equals(""))
                return null;
            middlePartIfc = getPartByConfigSpec(middlePartMasterIfc, partConfigSpecIfc);
        }
        String quantity = getPartQuantity(middlePartIfc, childPartIfc, midleList);
        if(quantity == null || quantity.equals(""))
            return null;
        float middleQuantity2 = Float.valueOf(middleQuantity).floatValue();
        float quantity2 = Float.valueOf(quantity).floatValue();
        String tempQuantity = String.valueOf(middleQuantity2 * quantity2);
        if(tempQuantity.endsWith(".0"))
            tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
        return tempQuantity;
    }

    /**
     * ��ȡ�������ù淶���㲿����
     * @param partMasterIfc QMPartMasterIfc �㲿������Ϣ��
     * @param partConfigSpecIfc PartConfigSpecIfc ���ù淶�� @
     * @return QMPartIfc
     */
    private QMPartIfc getPartByConfigSpec(QMPartMasterIfc partMasterIfc, PartConfigSpecIfc partConfigSpecIfc)throws QMException
    {
        Collection collection = new ArrayList();
        collection.add(partMasterIfc);
        Collection collection2 = filteredIterationsOf(collection, partConfigSpecIfc);
        Iterator iterator = collection2.iterator();
        Object[] obj2 = null;
        while(iterator.hasNext())
        {
            Object obj1 = iterator.next();
            if(obj1 instanceof Object[])
            {
                obj2 = (Object[])obj1;
            }
        }
        if(obj2 == null || obj2.length == 0)
            return null;
        if(!(obj2[0] instanceof QMPartIfc))
            return null;
        return (QMPartIfc)obj2[0];
    }

    /**
     * �õ�ǰ�û������ù淶�����㲿��
     * @param masterCol Collection @
     * @return Collection �����е����Ϳɲμ� ConfigService ������<br> filteredIterationsOf(Collection collection, ConfigSpec configSpec) ����.<br> ���˺�����ļ���
     */
    public Collection filteredIterationsOfByDefault(Collection masterCol)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
        Collection col;

        col = (Collection)partService.filteredIterationsOf(masterCol, configSpecIfc);

        return col;
    }

    /**
     * �ж��Ƿ��Ǹ���
     * @param maybeChild Vector ���ֵ���󼯺�
     * @param maybeParent QMPartIfc @
     * @return Collection vector���ϣ�<br> QMPartIfc�Ǹ������������
     * @see QMPartInfo
     */
    //(�����)������� zz ��Ӽ�����÷��������ٿͻ��˵��ô����������
    public Collection isParentPart(Vector maybeChild, QMPartIfc maybeParent)throws QMException
    {
        if(maybeChild != null)
        {
            for(int i = 0;i < maybeChild.size();i++)
            {
                QMPartIfc onePart = (QMPartIfc)maybeChild.elementAt(i);
                boolean be = isParentPartnotinSubTable(onePart, maybeParent);
                maybeChild.remove(i);
                maybeChild.add(i, new Boolean(be));

            }
            return maybeChild;
        }else
            return null;
    }

    private boolean isParentPartnotinSubTable(QMPartIfc partIfc1, QMPartIfc partIfc2)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc)partIfc1.getMaster();
        QMPartMasterIfc partMasterIfc2 = (QMPartMasterIfc)partIfc2.getMaster();
        if(partMasterIfc1.getBsoID().equals(partMasterIfc2.getBsoID()))
        {
            System.out.println("partMasterIfc1  partMasterIfc2 xiangdeng");
            return true;
        }
        Vector temp = getAllParentParts(partIfc1);
        //���partIfc1û�и��׽ڵ㣬˵��partIfc2��Զ��������partIfc1�ĸ��׽ڵ㣬���Է���
        //��Զ����false
        if(temp == null || temp.size() == 0)
        {

            return false;
        }
        for(int i = 0;i < temp.size();i++)
        {
            String bsoID1 = partMasterIfc2.getBsoID();
            String bsoID2 = ((QMPartMasterIfc)temp.elementAt(i)).getBsoID();
            //���partMasterIfc2��BsoID��partIfc1��ĳ�����׽ڵ��BsoID��ȣ�����true;
            if(bsoID1.equals(bsoID2))
            {

                return true;
            }
        }

        return false;

    }

    private Vector getAllParentParts(QMPartIfc partIfc)throws QMException
    {
        //  System.out.println("getAllParentParts========"+partIfc.getMasterBsoID());
        Vector tempresult = getParentParts(partIfc);
        Vector result = new Vector();
        if(tempresult != null)
        {
            for(int i = 0;i < tempresult.size();i++)
            {
                QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc)((QMPartIfc)tempresult.elementAt(i)).getMaster();
                if(partMasterIfc1 != null)
                    result.addElement(partMasterIfc1);
                Vector temp = getAllParentParts((QMPartIfc)tempresult.elementAt(i));
                for(int j = 0;j < temp.size();j++)
                {
                    result.addElement(temp.elementAt(j));
                }
            }
        }

        return result;
    }

    private Vector getParentParts(QMPartIfc partIfc)throws QMException
    {

        QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)partIfc.getMaster();
        Collection collection = getUsedByParts(partMasterIfc);
        Vector result = new Vector();
        Vector vector = new Vector();
        //collection��Ӧ����QMPartIfc�ļ���
        if(collection != null && collection.size() > 0)
        {
            Iterator iterator = collection.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if(obj instanceof QMPartIfc)
                {
                    //�ڲ鿴�����ڽ��棬���һ������ʹ��һ���Ӽ���Σ���ֻ���г�һ����¼������ÿ�ζ��г�
                    QMPartIfc partIfc2 = (QMPartIfc)obj;
                    String string = partIfc2.getPartNumber() + partIfc2.getVersionValue();
                    if(!vector.contains(string))
                    {
                        vector.addElement(string);
                        result.addElement(partIfc2);
                    }
                }
            }

            return result;
        }else
        {

            return null;
        }
    }

    /**
     * ����ָ����QMPartMasterIfc���� ͨ�����������ϲ�ѯ�����PartUsageLink������QMPartMasterIfc���������°汾 ��QMPartIfc����ļ��ϡ�
     * @param partMasterIfc :QMPartMasterIfc����
     * @return collection ��partMasterIfc��PartUsageLink���й��������°汾QMPartIfc����ļ��ϡ� @
     */
    private Collection getUsedByParts(QMPartMasterIfc partMasterIfc)throws QMException
    {

        QMQuery query = new QMQuery("QMPart", "PartUsageLink");
        //�����°汾��ѯ����������cond
        QueryCondition condition1 = VersionControlHelper.getCondForLatest(true);
        //���ݴ������ֵȷ�����λ�ã���������Ӳ�ѯ����,����0��ʾ�ǵ�һ����:�ǶԵ�һ������Ӳ�ѯ����
        query.addCondition(0, condition1);
        query.setChildQuery(false);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        //����һ��ֵ����partMasterIfc�ͱ�ֵ�����ڹ������еĹ�����ɫ��"uses", �����������һ�ߵ�bso����
        //query - ��ѯ�Ĺ�������
        Collection outcoll = (Collection)pservice.navigateValueInfo(partMasterIfc, "uses", query);
        // System.out.println("getUsedByParts dejieguo outcoll " + outcoll.size()+"partMasterIfc=="+partMasterIfc.getBsoID());
        return outcoll;
    }

    /**
     * ����·�ߵ������� ·�߱�ֻTechnicsRouteListMaster�����ƺͱ��,TechnicsRouteListҲ�����ƺͱ��
     * @param routelist TechnicsRouteListIfc ·�߱�ֵ���� @
     * @return TechnicsRouteListMasterIfc ·�߱�ֵ����
     * @see TechnicsRouteListInfo
     */
    //    ������ʮ�����ӹ���·�ߵ����������� ������� zz 20061214
    //     flag �Ƿ��޸�·�߱���
    //CR9 begin
    public TechnicsRouteListMasterIfc rename(TechnicsRouteListMasterIfc routelist)throws QMException
    {
        if(routelist == null)
        {
            return null; //�����������Ϊ�շ��ؿ�
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
        routelist = (TechnicsRouteListMasterIfc)pservice.saveValueInfo(routelist, false);

        QMQuery query = new QMQuery(ROUTELIST_BSONAME);
        QueryCondition condition = new QueryCondition("masterBsoID", QueryCondition.EQUAL, routelist.getBsoID());
        query.addCondition(condition);
        Collection coll = pservice.findValueInfo(query);
        if(coll != null && coll.size() > 0)
        {
            try
            {
                for(Iterator iter = coll.iterator();iter.hasNext();)
                {
                    TechnicsRouteListIfc routelisti = (TechnicsRouteListIfc)iter.next();

                    routelisti.setRouteListNumber(routelist.getRouteListNumber());
                    routelisti.setRouteListName(routelist.getRouteListName());

                    routelisti = (TechnicsRouteListIfc)pservice.updateValueInfo(routelisti);
                }

            }catch(Exception ex)
            {
                if(ex instanceof SQLException)
                {
                    //�ж�Ψһ�ԡ�
                    Object[] obj = {routelist.getRouteListName(), routelist.getRouteListNumber()};
                    throw new QMException("com.faw_qm.technics.consroute.util.RouteResource", "3", obj);
                }else
                {
                    this.setRollbackOnly();
                    throw new QMException(ex);
                }

            }

        }

        return routelist;
    }

    //CR9 end

    /**
     * רΪ���������൥����
     * @param part QMPartIfc ���ֵ���� @
     * @return String
     * @see QMPartInfo
     */
    //  * lilei add 2006-7-11
    public String getMaterialRoute(QMPartIfc part)throws QMException
    {
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        ListRoutePartLinkInfo info = null;
        //  TechnicsRouteListInfo routelist = null;
        //  TechnicsRouteIfc routeIfc = null;//zz
        String routeString = "";//zz
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition qc = new QueryCondition("rightBsoID", "=", part.getMasterBsoID());
        query.addCondition(qc);
        //QueryCondition qc1=new QueryCondition("adoptStatus","=","adopt");
        QueryCondition qc1 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());

        query.addAND();
        query.addCondition(qc1);
        Collection cols = null;

        try
        {
            cols = pService.findValueInfo(query, false);
            if(cols != null)
            {
                // System.out.println("��ѯ���Ĺ������� " + cols.size());
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        Iterator ite = cols.iterator();
        if(ite.hasNext())
        {
            info = (ListRoutePartLinkInfo)ite.next();

        }
        if(info != null)
        {
            //      routelist = (TechnicsRouteListInfo) pService.refreshInfo(info.
            //          getLeftBsoID());
            //      routeIfc = (TechnicsRouteIfc) pService.refreshInfo(info.getRouteID());
            if(info.getRouteID() != null)
            {
                Map map = getDirectRouteBranchStrs(info.getRouteID());
                if(!map.isEmpty())
                {
                    Iterator values = map.values().iterator();
                    routeString = (String)values.next();
                    while(values.hasNext())
                    {

                        String routeStr = (String)values.next();
                        routeString = routeString + ";" + routeStr;
                    }

                }

            }

        }

        return routeString;
    }

    //////////////////////////////////////////////////////
    /**
     * �ּ������嵥����ʾ��
     * @param partIfc :QMPartIfc ����Ĳ���ֵ����
     * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա�
     * @param affixAttrNames : String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
     * @param configSpecIfc :PartConfigSpecIfc ���ù淶�� @
     * @return Vector ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ� 0����ǰpart��BsoID�� 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ� 2-...���ɱ�ģ����û�ж������ԣ�2����ǰpart�ı�ţ�3����ǰpart������ 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ� 5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
     * ������������ԣ��������ж��Ƶ����Լӵ���������С� �����������˵ݹ鷽���� fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc, PartUsageLinkIfc partLinkIfc, int parentQuantity);
     * @see QMPartInfo
     * @see PartConfigSpecInfo
     */

    public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, PartConfigSpecIfc configSpecIfc)throws QMException
    {
        System.out.println("������� setMaterialList");
        Vector vector = null;
        try
        {
            //            PartDebug.trace(this, PartDebug.PART_SERVICE,
            //                            "setMaterialList begin ....");
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            int level = 0;
            PartUsageLinkIfc partLinkIfc = new PartUsageLinkInfo();
            float parentQuantity = 1.0f;

            //��¼�����ͱ�������������ڵ�λ�á�
            int quantitySite = 0;
            int numberSite = 0;
            for(int i = 0;i < attrNames.length;i++)
            {
                String attr = attrNames[i];
                attr = attr.trim();
                if(attr != null && attr.length() > 0)
                {
                    if(attr.equals("quantity"))
                    {
                        quantitySite = 3 + i;
                    }
                    if(attr.equals("partNumber"))
                    {
                        numberSite = 3 + i;
                    }
                }
            }

            vector = fenji(partIfc, attrNames, affixAttrNames, configSpecIfc, level, partLinkIfc, parentQuantity);
            //            PartDebug.trace(this, PartDebug.PART_SERVICE,
            //                            "setMaterialList end....return is Vector");
            //�ѽ�������еĵ�һ��Ԫ�ص�ʹ�õ��������""
            if(vector != null && vector.size() > 0)
            {
                Object[] first = (Object[])vector.elementAt(0);

                //����������������������������Ϊ�ա�
                if(quantitySite > 0)
                {
                    first[quantitySite] = "";
                }
                vector.setElementAt(first, 0);
            }
            //����Ҫ���vector�����һ��Ԫ�أ�
            Vector firstElement = new Vector();
            firstElement.addElement("");
            firstElement.addElement("");
            String ssss = QMMessage.getLocalizedMessage(PARTRESOURCE, "level", null);
            firstElement.addElement(ssss);
            //        ssss = QMMessage.getLocalizedMessage(RESOURCE, "partNumber", null);
            //        firstElement.addElement(ssss);
            //        ssss = QMMessage.getLocalizedMessage(RESOURCE, "partName", null);
            //        firstElement.addElement(ssss);
            //        ssss = QMMessage.getLocalizedMessage(RESOURCE, "quantity", null);
            //        firstElement.addElement(ssss);
            //������Ҫͨ���ж���ȷ��firstElement��ֵ:
            boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
            boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
            if(attrNullTrueFlag)
            {
                if(affixAttrNullTrueFlag)
                {
                    //                ssss = QMMessage.getLocalizedMessage(RESOURCE, "versionValue", null);
                    //                firstElement.addElement(ssss);
                    //                ssss = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);
                    //                firstElement.addElement(ssss);
                }
            }else
            {
                for(int i = 0;i < attrNames.length;i++)
                {
                    String attr = attrNames[i];
                    ssss = QMMessage.getLocalizedMessage(PARTRESOURCE, attr, null);
                    firstElement.addElement(ssss);
                }
            }
            //�����firstElement�е����е�Ԫ����װ��ϣ�������Ҫ��firstElement ->Object[]
            //����ӵ�vector�еĵ�һ��λ�ã�
            Object[] tempArray = new Object[firstElement.size()];
            for(int i = 0;i < firstElement.size();i++)
            {
                tempArray[i] = firstElement.elementAt(i);
            }
            vector.insertElementAt(tempArray, 0);
            //2003.09.12Ϊ�˷�ֹ"null"���뵽����ֵ�У����Զ�vector�е�ÿ��Ԫ���ж�
            //���Ƿ�Ϊnull, �����null����ת��Ϊ""
            for(int i = 0;i < vector.size();i++)
            {
                Object[] temp = (Object[])vector.elementAt(i);
                for(int j = 0;j < temp.length;j++)
                {
                    if(temp[j] == null)
                    {
                        temp[j] = "";
                    }
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return vector;
    }

    //add by guoxl on 2008.03.21(part�������嵥���ܵ��ô˷��������޸Ĵ˴�)
    /**
     * ��ȡpart���滻���ı�ţ����ƣ����ִ���ʽ����
     * @param part �㲿����
     * @return �����ı�ţ����ƣ�
     */
    private String getAlternates(QMPartIfc part)throws QMException
    {
        String alternates = "";

        ExtendedPartService pservice = (ExtendedPartService)EJBServiceHelper.getService("ExtendedPartService");
        Collection altes = pservice.getAlternatesPartMasters((QMPartMasterIfc)part.getMaster());
        Iterator ite = altes.iterator();
        for(;ite.hasNext();)
        {
            QMPartMasterIfc master = (QMPartMasterIfc)ite.next();
            if(alternates.length() == 0)
            {
                alternates = master.getPartNumber() + "(" + master.getPartName() + ")";
            }else
                alternates = alternates + ";" + master.getPartNumber() + "(" + master.getPartName() + ")";
        }
        return alternates;
    }

    /**
     * ��ȡpart�Ľṹ�滻���ı�ţ����ƣ����ִ���ʽ����
     * @param part �㲿����
     * @return �ṹ�����ı�ţ����ƣ�
     */
    private String getSubstitutes(PartUsageLinkIfc usageLinkIfc)throws QMException
    {
        String substitutes = "";
        if(usageLinkIfc == null)
            return substitutes;
        if(!PersistHelper.isPersistent(usageLinkIfc))
            return substitutes;
        System.out.println("aaaaaaaaaaa the usagelink is " + usageLinkIfc.getBsoID());
        ExtendedPartService pservice = (ExtendedPartService)EJBServiceHelper.getService("ExtendedPartService");
        Collection subst = pservice.getSubstitutesPartMasters(usageLinkIfc);
        Iterator ite = subst.iterator();
        for(;ite.hasNext();)
        {
            QMPartMasterIfc master = (QMPartMasterIfc)ite.next();
            if(substitutes.length() == 0)
            {
                substitutes = master.getPartNumber() + "(" + master.getPartName() + ")";
            }else
                substitutes = substitutes + ";" + master.getPartNumber() + "(" + master.getPartName() + ")";
        }
        return substitutes;
    }

    // add by guoxl end

    /**
     * ˽�з�������setMaterialList()�������ã�ʵ�ֶ��Ʒּ������嵥�Ĺ��ܡ� ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ� 0����ǰpart��BsoID�� 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ� 2-...���ɱ�ģ����û�ж������ԣ�2����ǰpart�ı�ţ�3����ǰpart������ 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
     * 5����ǰpart�İ汾�ţ�6����ǰpart����ͼ�� ������������ԣ��������ж��Ƶ����Լӵ���������С�
     * @param partIfc :QMPartIfc ��ǰ�Ĳ�����
     * @param attrNames :String[] ���Ƶ����Լ��ϣ�����Ϊ�ա�
     * @param affixAttrNames :String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
     * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
     * @param level :int ��ǰpart���ڵļ���
     * @param partLinkIfc :PartUsageLinkIfc ��¼�˵�ǰpart�����׽ڵ��ʹ�ù�ϵ��ֵ����
     * @param parentQuantity :int ��ǰpart�ĸ��׽ڵ㱻�������ʹ�õ������� @
     * @return Vector
     */

    private Vector fenji(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, PartConfigSpecIfc configSpecIfc, int level, PartUsageLinkIfc partLinkIfc, float parentQuantity)throws QMException

    {
        //   PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji begin ....");
        //���������ֵ��
        Vector resultVector = new Vector();
        Object[] tempArray = null;
        //��ʶ���Ƶ���ͨ�����Ƿ�Ϊ�գ����Ϊ�գ��ñ�ʶΪ�棬����Ϊ�٣�
        boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
        //��ʶ���Ƶ���չ�����Ƿ�Ϊ�գ����Ϊ�գ��ñ�ʶΪ�棬����Ϊ�٣�
        boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
        //����ַ��������б������"ͷ��Ϣ":
        if(attrNullTrueFlag)
        {
            if(affixAttrNullTrueFlag)
            {
                //��������Ķ��Ƶ����Լ��϶�Ϊ�յĻ���
                tempArray = new Object[3];
                //                tempArray = new Object[7];
            }else
            {
                //���ֻ�ж��Ƶ���չ���Բ�Ϊ�յ�ʱ��
                tempArray = new Object[3 + affixAttrNames.length];
            }
        }else
        {
            if(affixAttrNullTrueFlag)
            {
                //���ֻ�ж��Ƶ���ͨ���Բ�Ϊ�յ�ʱ��
                tempArray = new Object[3 + attrNames.length];
            }else
            {
                //����������Ƶ����Լ��϶���Ϊ�յ�ʱ��
                tempArray = new Object[3 + affixAttrNames.length + attrNames.length];
            }
        }
        //end if and else (attrNames == null || attrNames.length == 0)
        tempArray[0] = partIfc.getBsoID();
        int numberSite = 0;
        for(int i = 0;i < attrNames.length;i++)
        {
            String attr = attrNames[i];
            attr = attr.trim();
            if(attr != null && attr.length() > 0)
            {
                if(attr.equals("partNumber"))
                {
                    numberSite = 3 + i;
                }
            }
        }
        tempArray[1] = new Integer(numberSite);//����������ڵ�λ��
        tempArray[2] = new Integer(level);//level�ĳ�ʼֵΪ0��
        //        tempArray[2] = partIfc.getPartNumber();
        //        tempArray[3] = partIfc.getPartName();
        //���level = 0 ˵��������Ĳ�����
        /**
         * if (level == 0) { parentQuantity = 1f; String quan = "1"; tempArray[4] = new String(quan); } else { //�ɲ��������������ٱ���parentBsoID,���Ǳ���PartUsageLinkIfc����ѭ������������ //��������ʡ���ٲ��ҵĹ��̡�QMPartUsageLinkIfc
         * partLinkIfc parentQuantity = partLinkIfc.getQuantity();//parentQuantity*partLinkIfc.getQuantity(); String tempQuantity = String.valueOf(parentQuantity); if (tempQuantity.endsWith(".0"))
         * tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2); tempArray[4] = tempQuantity; }
         */
        //�ж��Ƿ���Ҫ�������Խ��������
        if(attrNullTrueFlag)
        {
            //����������Ƶ����Լ��϶�Ϊ�յĻ���
            if(affixAttrNullTrueFlag)
            {
                //                tempArray[5] = partIfc.getVersionValue();
                //                if (partIfc.getViewName() == null ||
                //                    partIfc.getViewName().length() == 0)
                //                {
                //                    tempArray[6] = "";
                //                }
                //                else
                //                {
                //                    tempArray[6] = partIfc.getViewName();
                //                }
            }
        }
        //������������Ƶ���ͨ����Ϊ�յ�ʱ��
        //���棺������Ƶ���ͨ���Բ�Ϊ�յ�ʱ��
        else
        {
            //�Զ��Ƶ���ͨ���Խ���ѭ����
            for(int j = 0;j < attrNames.length;j++)
            {
                String attr = attrNames[j];
                attr = attr.trim();
                if(attr != null && attr.length() > 0)
                {
                    //modify by liun 2005.3.25 ��Ϊ�ӹ����еõ���λ
                    String temp = tempArray[1].toString();

                    //add by guoxl(part�������嵥�����ô˴������޸�)
                    //����������滻��
                    if(attr.equals("alternates"))
                    {
                        tempArray[3 + j] = getAlternates(partIfc);
                    }
                    //��������ǽṹ�滻��
                    else if(attr.equals("substitutes"))
                    {
                        tempArray[3 + j] = getSubstitutes(partLinkIfc);
                    }
                    //add by guoxl end
                    else if(attr.equals("defaultUnit") && !temp.equals("0"))
                    {
                        Unit unit = partLinkIfc.getDefaultUnit();
                        if(unit != null)
                        {
                            tempArray[3 + j] = unit.getDisplay();
                        }else
                        {
                            tempArray[3 + j] = "";
                        }
                    }else if(attr.equals("quantity"))
                    {
                        //���level = 0 ˵��������Ĳ�����
                        if(level == 0)
                        {
                            parentQuantity = 1f;
                            String quan = "1";
                            tempArray[3 + j] = new String(quan);
                        }else
                        {
                            //�ɲ��������������ٱ���parentBsoID,���Ǳ���PartUsageLinkIfc����ѭ������������
                            //��������ʡ���ٲ��ҵĹ��̡�QMPartUsageLinkIfc partLinkIfc
                            parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();
                            String tempQuantity = String.valueOf(parentQuantity);
                            if(tempQuantity.endsWith(".0"))
                                tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                            tempArray[3 + j] = tempQuantity;
                        }
                    }
                    //zz start
                    else if(attr.equals("routeList"))
                    {
                        System.out.println("  attr equales routelist ");
                        // tempArray[3+ attrNames.length-1] = "�صص���";
                        TechnicsRouteService technicsrouteservice = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
                        String routeString = technicsrouteservice.getMaterialRoute(partIfc);
                        tempArray[3 + j] = routeString;
                    }
                    //zz end

                    else
                    {
                        attr = (attr.substring(0, 1)).toUpperCase() + attr.substring(1, attr.length());
                        attr = "get" + attr;
                        //���ڵ�attr����"getProducedBy"�ȹ̶����ַ����ˣ�
                        try
                        {
                            Class partClass = Class.forName("com.faw_qm.part.model.QMPartInfo");
                            Method method1 = partClass.getMethod(attr, null);
                            Object obj = method1.invoke(partIfc, null);
                            //������Ҫ�ж�obj�Ƿ�Ϊnull, ���Ϊnull, attrNames[i] = "";
                            //���obj��Ϊnull, ������String, attrNames[i] = (String)obj;
                            //���obj��ö�����ͣ�attrNames[i] = (EnumerationType)obj.getDisplay();
                            if(obj == null)
                            {
                                tempArray[3 + j] = "";
                            }else
                            {
                                if(obj instanceof String)
                                {
                                    String tempString = (String)obj;
                                    if(tempString != null && tempString.length() > 0)
                                    {
                                        tempArray[3 + j] = tempString;
                                    }else
                                    {
                                        tempArray[3 + j] = "";
                                    }
                                }else
                                {
                                    if(obj instanceof EnumeratedType)
                                    {
                                        EnumeratedType tempType = (EnumeratedType)obj;
                                        if(tempType != null)
                                        {
                                            tempArray[3 + j] = tempType.getDisplay();
                                        }else
                                        {
                                            tempArray[3 + j] = "";
                                        }
                                    }
                                }
                            }
                            //end if(obj == null)
                        }catch(Exception ex)
                        {
                            ex.printStackTrace();
                            throw new QMException(ex);
                        }
                    }
                }
            }
            //end for (int j=0; j<attrNames.length; j++)

        }
        //end else (attrNames == null)
        resultVector.addElement(tempArray);
        Collection collection = PartServiceRequest.getUsesPartIfcs(partIfc, configSpecIfc);
        if((collection == null) || (collection.size() == 0))
        {
            //PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji end....return is Vector");
            return resultVector;
        }else
        {
            Object[] temp = (Object[])collection.toArray();
            level++;
            for(int k = 0;k < temp.length;k++)
            {
                if(temp[k] instanceof Object[])
                {
                    Object[] obj = (Object[])temp[k];
                    //ȡtemp�е�Ԫ�ؽ���ѭ����temp[k][0]��PartUsageLinkIfc,
                    //temp[k][1]��QMPartIfc
                    Vector tempResult = new Vector();
                    if(obj[1] instanceof QMPartIfc && obj[0] instanceof PartUsageLinkIfc)
                    {
                        tempResult = fenji((QMPartIfc)obj[1], attrNames, affixAttrNames, configSpecIfc, level, (PartUsageLinkIfc)obj[0], parentQuantity);
                    }
                    for(int m = 0;m < tempResult.size();m++)
                    {
                        resultVector.addElement(tempResult.elementAt(m));
                    }
                }
                //end if(temp[k] instanceof Object[])
            }
            //end for (int k=0; k<temp.length; k++)
            level--;
            //   PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji end....return Vector ");
            return resultVector;
        }
    }

    /**
     * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ�� ������������bianli()����ʵ�ֵݹ顣 �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ�� 1��������������ԣ� BsoID���ǣ��񣩿ɷ֣�"true","false"������š����ơ�������ת��Ϊ�ַ��ͣ����汾����ͼ�� 2������������ԣ�
     * BsoID���ǣ��񣩿ɷ֡������������ԡ�
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
     * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Լ��ϣ�����Ϊ�ա�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õĶԵ�ǰ�㲿����ɸѡ������ @
     * @return Vector ���Object[] :tempArray[i] �㲿����ͳ�Ʊ���Ϣ��
     * @see QMPartInfo
     * @see PartConfigSpecInfo
     */
    public Vector setBOMList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String source, String type, PartConfigSpecIfc configSpecIfc)throws QMException
    {
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        Vector vector = new Vector();
        float parentQuantity = 1.0f;

        //��¼�����ͱ�������������ڵ�λ�á�
        int quantitySite = 0;
        int numberSite = 0;
        for(int i = 0;i < attrNames.length;i++)
        {
            String attr = attrNames[i];
            attr = attr.trim();
            if(attr != null && attr.length() > 0)
            {
                if(attr.equals("quantity"))
                {
                    quantitySite = 3 + i;
                }
                if(attr.equals("partNumber"))
                {
                    numberSite = 3 + i;
                }

            }
        }
        PartUsageLinkIfc partLinkIfc = new PartUsageLinkInfo();
        vector = bianli(partIfc, attrNames, affixAttrNames, source, type, configSpecIfc, parentQuantity, partLinkIfc);
        //�����vector�е�Ԫ�ؽ��кϲ������Ĵ���...........
        Vector resultVector = new Vector();
        for(int i = 0;i < vector.size();i++)
        {
            Object[] temp1 = (Object[])vector.elementAt(i);
            //2003.09.12Ϊ�˷�ֹ"null"���뵽����ֵ�У����Զ�temp1�е�ÿ��Ԫ���ж�
            //���Ƿ�Ϊnull, �����null����ת��Ϊ""
            for(int j = 0;j < temp1.length;j++)
            {
                if(temp1[j] == null)
                {
                    temp1[j] = "";
                }
            }
            //�����ǰ���partNumber���кϲ��ģ�����
            String partNumber1 = (String)temp1[numberSite];
            boolean flag = false;
            for(int j = 0;j < resultVector.size();j++)
            {
                Object[] temp2 = (Object[])resultVector.elementAt(j);
                String partNumber2 = (String)temp2[numberSite];
                if(partNumber1.equals(partNumber2))
                {
                    flag = true;

                    //���������λ�ô���0��˵���������������������Ȼ����ͬ�㲿��
                    //�������ϲ���
                    if(quantitySite > 0)
                    {
                        //��temp2��temp1�е�Ԫ�ؽ��кϲ����ŵ�resultVector��ȥ��:::
                        float float1 = (new Float(temp1[quantitySite].toString())).floatValue();
                        float float2 = (new Float(temp2[quantitySite].toString())).floatValue();
                        String tempQuantity = String.valueOf(float1 + float2);
                        if(tempQuantity.endsWith(".0"))
                            tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                        temp1[quantitySite] = tempQuantity;
                    }
                    resultVector.setElementAt(temp1, j);
                    break;
                }
                //end if (partNumber1.equals(partNumber2))
            }
            //end for (int j=0; j<resultVector.size(); j++)
            if(flag == false)
            {
                resultVector.addElement(temp1);
            }
            //end if(flag == false)
        }
        //end for (int i=0; i<vector.size(); i++)

        //��Ҫ�Ե�һ��Ԫ�ؽ����жϣ�����䣬source��type���������source, type��ͬ��
        //�ͱ���������ɾ������
        //��ʵ����partIfc������:::
        boolean flag1 = false;
        boolean flag2 = false;
        String source1 = (partIfc.getProducedBy()).toString();
        String type1 = (partIfc.getPartType()).toString();
        if(source != null && source.length() > 0)
        {
            if(source.equals(source1))
            {
                flag1 = true;
            }
        }else
        {
            flag1 = true;
        }
        if(type != null && type.length() > 0)
        {
            if(type.equals(type1))
            {
                flag2 = true;
            }
        }else
        {
            flag2 = true;
        }
        if(!flag1 || !flag2)
        {
            resultVector.removeElementAt(0);
        }else
        {
            //�ѵ�һ��Ԫ�ص������ĳ�""
            Object[] firstObj = (Object[])resultVector.elementAt(0);

            //����������������������������Ϊ�ա�
            if(quantitySite > 0)
            {
                firstObj[quantitySite] = "";
            }
            resultVector.setElementAt(firstObj, 0);
        }
        //����ű���������Ľ����
        Vector result = new Vector();
        //Ȼ�����ﻹ��Ҫ�����ķ���ֵ���ϰ��յ�ǰ��source��type���й��ˣ�
        for(int i = 0;i < resultVector.size();i++)
        {
            Object[] element = (Object[])resultVector.elementAt(i);
            QMPartIfc onePart = (QMPartIfc)pService.refreshInfo((String)element[0]);
            boolean flag11 = false;
            boolean flag22 = false;
            if(source != null && source.length() > 0)
            {
                if(onePart.getProducedBy().toString().equals(source))
                {
                    flag11 = true;
                }
            }else
            {
                flag11 = true;
            }
            if(type != null && type.length() > 0)
            {
                if(onePart.getPartType().toString().equals(type))
                {
                    flag22 = true;
                }
            }else
            {
                flag22 = true;
            }
            if(flag11 && flag22)
            {
                result.addElement(element);
            }
        }
        //����Ҫ���vector�����һ��Ԫ�أ�
        Vector firstElement = new Vector();
        firstElement.addElement("");
        firstElement.addElement("");
        String ssss = "";
        //        String ssss = QMMessage.getLocalizedMessage(RESOURCE, "partNumber", null);
        //        firstElement.addElement(ssss);
        //        ssss = QMMessage.getLocalizedMessage(RESOURCE, "partName", null);
        //        firstElement.addElement(ssss);
        ssss = QMMessage.getLocalizedMessage(PARTRESOURCE, "isHasSubParts", null);
        firstElement.addElement(ssss);
        //        ssss = QMMessage.getLocalizedMessage(RESOURCE, "quantity", null);
        //        firstElement.addElement(ssss);
        //������Ҫͨ���ж���ȷ��firstElement��ֵ:
        boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
        boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
        //������Ƶ���ͨ����Ϊ�գ�
        if(attrNullTrueFlag)
        {
            //������Ƶ���չ����ҲΪ�գ�
            if(affixAttrNullTrueFlag)
            {
                //                ssss = QMMessage.getLocalizedMessage(RESOURCE, "versionValue", null);
                //                firstElement.addElement(ssss);
                //                ssss = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);
                //                firstElement.addElement(ssss);
            }
        }
        //������Ƶ���ͨ���Բ�Ϊ�յĻ���
        else
        {
            for(int i = 0;i < attrNames.length;i++)
            {
                String attr = attrNames[i];
                ssss = QMMessage.getLocalizedMessage(PARTRESOURCE, attr, null);
                firstElement.addElement(ssss);
            }
        }
        //�����firstElement�е����е�Ԫ����װ��ϣ�������Ҫ��firstElement ->Object[]
        //����ӵ�vector�еĵ�һ��λ�ã�
        Object[] tempArray = new Object[firstElement.size()];
        for(int i = 0;i < firstElement.size();i++)
        {
            tempArray[i] = firstElement.elementAt(i);
        }
        result.insertElementAt(tempArray, 0);
        return result;
    }

    /**
     * ��������setBOMList�����ã�ʵ�ֵݹ���õĹ��ܡ� �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ�� 1��������������ԣ� BsoID���ǣ��񣩿ɷ֣�"true","false"������š����ơ�������ת��Ϊ�ַ��ͣ����汾����ͼ�� 2������������ԣ� BsoID���ǣ��񣩿ɷ֡������������ԡ�
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
     * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Եļ��ϣ�����Ϊ�ա�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õ�ɸѡ������
     * @param parentQuantity :float ʹ���˵�ǰ�����Ĳ��������������ʹ�õ�������
     * @param partLinkIfc :PartUsageLinkIfc ���ӵ�ǰ�������丸�����Ĺ�����ϵֵ���� @
     * @return Vector
     */
    private Vector bianli(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String source, String type, PartConfigSpecIfc configSpecIfc, float parentQuantity,
            PartUsageLinkIfc partLinkIfc)throws QMException
    {
        //����������Ҫʵ�ֹ���Ϊ:::
        //1���жϵ�ǰ���㲿���Ƿ��ǿɷֵ��㲿�����Է����ڰѸ��㲿���ŵ�����������е�ʱ�򣬿���ȷ��
        //���㲿���Ŀɷֱ�־
        //  PartDebug.trace(this, PartDebug.PART_SERVICE, "bianli begin ....");
        Vector resultVector = new Vector();
        //�������浱ǰ���㲿�������кϸ�����㲿���ļ��ϣ�
        Vector hegeVector = new Vector();
        Collection collection = PartServiceRequest.getUsesPartIfcs(partIfc, configSpecIfc);
        //���ʱ���Ӧ�����ж�collection�Ƿ���"null"
        if(collection != null && collection.size() > 0)
        {
            //��Ҫ��collection�е�����Ԫ�ؽ���ѭ���������������Ԫ��
            //��QMPartIfc��������Դ�����ͺ�����Ĳ�����һ�µģ�
            //������������㲿���ǿɷֵ�.���Ǹ���source, type�����ӽڵ���й���:::
            Object[] resultArray = new Object[collection.size()];
            collection.toArray(resultArray);
            for(int i = 0;i < resultArray.length;i++)
            {
                boolean isHasSubParts = true; //false
                Object obj = resultArray[i];
                if(obj instanceof Object[])
                {
                    Object[] obj1 = (Object[])obj;
                    if(obj1[1] instanceof QMPartIfc)
                    {
                        //��һ���൱��������һ���Ե�ǰ�㲿�������ж����㲿���Ĺ�������.
                        if(isHasSubParts == true)
                        {
                            hegeVector.addElement(obj);
                        }
                        //end if(isHasSubParts == true)
                    }
                    //end if (obj1[1] instanceof QMPartIfc)
                }
                //end if(obj instanceof Object[])
            }
            //end for (int i=0; i<resultArray.length; i++)
        }
        //end if(collection != null && collection.size() > 0)

        //�ѱ�part->resultVector��;
        Object[] tempArray = null;
        boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
        boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
        if(attrNullTrueFlag)
        {
            //����������Ƶ����Լ��϶�Ϊ�յ�ʱ��
            if(affixAttrNullTrueFlag)
            {
                tempArray = new Object[3];
                //               tempArray = new Object[7];
            }
            //������Ƶ���ͨ����Ϊ�գ������Ƶ���չ���Բ�Ϊ�յ�ʱ��
            else
            {
                tempArray = new Object[3 + affixAttrNames.length];
            }
        }else
        {
            //������Ƶ���ͨ���Լ��ϲ�Ϊ�գ����Ƶ���չ���Լ���Ϊ�յ�ʱ��
            if(affixAttrNullTrueFlag)
            {
                tempArray = new Object[3 + attrNames.length];
            }
            //����������Ƶ����Լ��϶���Ϊ�յ�ʱ��
            else
            {
                tempArray = new Object[3 + affixAttrNames.length + attrNames.length];
            }
        }
        tempArray[0] = partIfc.getBsoID();
        int numberSite = 0;
        for(int i = 0;i < attrNames.length;i++)
        {
            String attr = attrNames[i];
            attr = attr.trim();
            if(attr != null && attr.length() > 0)
            {
                if(attr.equals("partNumber"))
                {
                    numberSite = 3 + i;
                }
            }
        }
        tempArray[1] = new Integer(numberSite);//����������ڵ�λ��
        //       tempArray[1] = partIfc.getPartNumber();
        //       tempArray[2] = partIfc.getPartName();
        String isHasSubParts1 = QMMessage.getLocalizedMessage(PARTRESOURCE, "false", null);
        if(hegeVector != null && hegeVector.size() > 0)
        {
            isHasSubParts1 = QMMessage.getLocalizedMessage(PARTRESOURCE, "true", null);
        }
        tempArray[2] = isHasSubParts1;
        //��Ҫ���ж�partLinkIfc�Ƿ��ǳ־û��ģ�������ǣ�parentQuantity = 1.0
        //�����:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
        //       if (partLinkIfc == null || !PersistHelper.isPersistent(partLinkIfc))
        //       {
        //           parentQuantity = 1.0f;
        //       }
        //       else
        //       {
        //           parentQuantity = parentQuantity * partLinkIfc.getQuantity();
        //       }
        //       String tempQuantity = String.valueOf(parentQuantity);
        //       if (tempQuantity.endsWith(".0"))
        //           tempQuantity = tempQuantity.substring(0,
        //                                                 tempQuantity.length() - 2);
        //       tempArray[4] = tempQuantity;
        //������Ҫ�����������Ƶ����Լ����������Ľ�����Ͻ�����֯��
        if(attrNullTrueFlag)
        {
            //���������Ƶ����Լ��϶�Ϊ�յ�ʱ��
            if(affixAttrNullTrueFlag)
            {
                //               tempArray[5] = partIfc.getVersionValue();
                //               if (partIfc.getViewName() == null ||
                //                   partIfc.getViewName().length() == 0)
                //               {
                //                   tempArray[6] = "";
                //               }
                //               else
                //               {
                //                   tempArray[6] = partIfc.getViewName();
                //               }
            }
            //�����������Ƶ���ͨ����Ϊ�գ������Ƶ���չ���Լ��ϲ�Ϊ�յ�ʱ��
        }
        //��������������Ƶ���ͨ���Լ���Ϊ�յ�ʱ��
        //���濪ʼ�������Ƶ���ͨ���Լ��ϲ�Ϊ�յ�ʱ��
        else
        {
            //�Ȱ����е���ͨ���Ե�ֵ�ŵ�tempArray�У�
            for(int i = 0;i < attrNames.length;i++)
            {
                String attr = attrNames[i];
                attr = attr.trim();
                if(attr != null && attr.length() > 0)
                {
                    //modify by liun 2005.3.25 ��Ϊ�ӹ����еõ���λ
                    //modify by guoxl on 2008.03.21(part�������嵥�����õ��˴������޸�)
                    if(attr.equals("alternates"))
                    {
                        tempArray[3 + i] = getAlternates(partIfc);
                    }
                    //��������ǽṹ�滻��
                    else if(attr.equals("substitutes"))
                    {
                        tempArray[3 + i] = getSubstitutes(partLinkIfc);
                    }else
                    //add by guoxl end
                    if(attr.equals("defaultUnit"))
                    {
                        Unit unit = partLinkIfc.getDefaultUnit();
                        if(unit != null)
                        {
                            tempArray[3 + i] = unit.getDisplay();
                        }else
                        {
                            tempArray[3 + i] = "";
                        }
                    }else if(attr.equals("quantity"))
                    {
                        //��Ҫ���ж�partLinkIfc�Ƿ��ǳ־û��ģ�������ǣ�parentQuantity = 1.0
                        //�����:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
                        if(partLinkIfc == null || !PersistHelper.isPersistent(partLinkIfc))
                        {
                            parentQuantity = 1.0f;
                        }else
                        {
                            parentQuantity = parentQuantity * partLinkIfc.getQuantity();
                        }
                        String tempQuantity = String.valueOf(parentQuantity);
                        if(tempQuantity.endsWith(".0"))
                            tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                        tempArray[3 + i] = tempQuantity;
                    }
                    //zz start  fff
                    else if(attr.equals("routeList"))
                    {

                        TechnicsRouteService technicsrouteservice = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
                        String routeString = technicsrouteservice.getMaterialRoute(partIfc);
                        tempArray[3 + i] = routeString;

                    }

                    //zz end
                    else
                    {
                        attr = (attr.substring(0, 1)).toUpperCase() + attr.substring(1, attr.length());
                        attr = "get" + attr;
                        //���ڵ�attr����"getProducedBy"�ȹ̶����ַ����ˣ�
                        try
                        {
                            Class partClass = Class.forName("com.faw_qm.part.model.QMPartInfo");
                            Method method1 = partClass.getMethod(attr, null);
                            Object obj = method1.invoke(partIfc, null);
                            //������Ҫ�ж�obj�Ƿ�Ϊnull, ���Ϊnull, attrNames[i] = "";
                            //���obj��Ϊnull, ������String, tempArray[i + 5] = (String)obj;
                            //���obj��ö�����ͣ�tempArray[i + 5] = (EnumerationType)obj.getDisplay();
                            if(obj == null)
                            {
                                tempArray[i + 3] = "";
                            }else
                            {
                                if(obj instanceof String)
                                {
                                    String tempString = (String)obj;
                                    if(tempString != null && tempString.length() > 0)
                                    {
                                        tempArray[i + 3] = tempString;
                                    }else
                                    {
                                        tempArray[i + 3] = "";
                                    }
                                }else
                                {
                                    if(obj instanceof EnumeratedType)
                                    {
                                        EnumeratedType tempType = (EnumeratedType)obj;
                                        if(tempType != null)
                                        {
                                            tempArray[i + 3] = tempType.getDisplay();
                                        }else
                                        {
                                            tempArray[i + 3] = "";
                                        }
                                    }
                                }
                            }
                        }catch(Exception ex)
                        {
                            ex.printStackTrace();
                            throw new QMException(ex);
                        }
                    }
                }
            }
            //end for (int i=0; i<attrNames.length; i++)
        }
        //end if and else if (attrNames == null || attrNames.length == 0)
        resultVector.addElement(tempArray);
        //���Ѿ����˴���ĵ�ǰ������㲿�����������㲿�����еݹ鴦��::::
        if(hegeVector != null && hegeVector.size() > 0)
        {
            for(int j = 0;j < hegeVector.size();j++)
            {
                Object obj = hegeVector.elementAt(j);
                if(obj instanceof Object[])
                {
                    Object[] obj2 = (Object[])obj;
                    if((obj2[0] != null) && (obj2[1] != null))
                    {
                        Vector tempVector = bianli((QMPartIfc)obj2[1], attrNames, affixAttrNames, source, type, configSpecIfc, parentQuantity, (PartUsageLinkIfc)obj2[0]);
                        for(int k = 0;k < tempVector.size();k++)
                            resultVector.addElement(tempVector.elementAt(k));
                    }
                }
            }
        }

        return resultVector;
    }

    //begin CR8
    /*
     * ���빤��·�߱�
     */
//    public TechnicsRouteListIfc checkInTechRouteList(WorkableIfc workable, String location)throws QMException
//    {
//        WorkingPair workPair = null;
//        try
//        {
//            WorkInProgressService workInService = (WorkInProgressService)EJBServiceHelper.getService("WorkInProgressService");
//            WorkableIfc copyInfo=workInService.checkin(workable, location);
//         
//            copyInfo= workInService.workingCopyOf(workable);
//            checkinListener((TechnicsRouteListIfc)copyInfo);
//            
//        }catch(Exception ex)
//        {
//            setRollbackOnly();
//            throw new QMException(ex);
//        }
//        return (TechnicsRouteListIfc)workPair.getWorkingCopy();
//    }
    
    public TechnicsRouteListIfc checkInTechRouteList(WorkableIfc workable, String location) throws QMException
    {
        WorkingPair workPair = null;
        try
        {
            WorkInProgressService workInService = (WorkInProgressService)EJBServiceHelper.getService("WorkInProgressService");
            workPair = (WorkingPair)workInService.checkIn(workable, location);
            //������벻�ɹ��׳��쳣
            if(!workPair.isOperateSuccess())
            {
                throw workPair.getException();
            }
            checkinListener((TechnicsRouteListIfc)workPair.getWorkingCopy());
        }catch(Exception ex)
        {
            setRollbackOnly();
            throw new QMException(ex);
        }
        return (TechnicsRouteListIfc)workPair.getWorkingCopy();
    }

    /*
     * �������·�߱�
     */
    public Collection checkOutTechRouteList(TechnicsRouteListIfc[] checkoutinfo, boolean flag)throws QMException
    {
        //  PersistService service = (PersistService)EJBServiceHelper.getPersistService();
        // ���workable����ʵ��

        WorkInProgressService workservice = (WorkInProgressService)EJBServiceHelper.getService("WorkInProgressService");
        //begin CR31
        // ��ü���ļ���
        //FolderIfc folderIfc = workservice.getCheckoutFolder();
        //add by guo
//        Collection techRouteVec = (Collection)workservice.checkOut(checkoutinfo, flag);
        Collection techRouteVec=new Vector();
       
        for(int i=0;i<checkoutinfo.length;i++){
        	
        	TechnicsRouteListIfc trl=checkoutinfo[i];
            workservice.checkout(trl);//���
            WorkableIfc work=workservice.workingCopyOf(trl);//��ø���
            copyRouteList(trl,(TechnicsRouteListIfc)work);
            WorkingPair wp=new WorkingPair(trl,work);
            if(work!=null)
               wp.setOperateSuccess(true);
            else
               wp.setOperateSuccess(false);
            techRouteVec.add(wp);
        }
        //add end by guo
//        Iterator ite = techRouteVec.iterator();
//        while(ite.hasNext())
//        {
//            WorkingPair techRouteIfc = (WorkingPair)ite.next();
//            for(int i = 0;i < checkoutinfo.length;i++)
//            {
//                TechnicsRouteListIfc info = checkoutinfo[i];
//                if(techRouteIfc.getWorkingCopy() != null)
//                {
//                    if(info.getBsoID().equals(((TechnicsRouteListIfc)techRouteIfc.getWorkingCopy()).getPredecessorID()))
//                    {
//                        copyRouteList(info, (TechnicsRouteListIfc)techRouteIfc.getWorkingCopy());
//                    }
//                }
//            }
//        }
        return techRouteVec;
    }

    /*
     * �޶�����·�߱�
     */
    public VersionedIfc reviseTechnicsRouteList(VersionedIfc reviseIfc, String foldLocation, String lifecycleTemName, String projectName)throws QMException
    {
        // ��ø������������ļ��м���������
        // ����ʹ����Դ

        PersistService persistservice = (PersistService)EJBServiceHelper.getPersistService();
        EnterpriseService enService = (EnterpriseService)EJBServiceHelper.getService("EnterpriseService");
        VersionedIfc reviseroute = (VersionedIfc)enService.revise(reviseIfc, foldLocation, lifecycleTemName, projectName, true);
        //�����µĹ�����
        Collection coll = getRouteListLinkParts((TechnicsRouteListIfc)reviseIfc);
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            if(VERBOSE)
            {
                System.out.println("ԭ������listLinkInfo.bsoID = " + listLinkInfo.getBsoID());
            }
            ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkInfo)((ListRoutePartLinkInfo)listLinkInfo).duplicate();
            if(VERBOSE)
            {
                System.out.println("�¹�����listLinkInfo1.bsoID = " + listLinkInfo1.getBsoID());
            }
            listLinkInfo1.setRouteListID(reviseroute.getBsoID());
            //��alterStatus���ó�INHERIT״̬��
            listLinkInfo1.setAlterStatus(INHERIT);//0
            //��adoptStatus���ó�CANCEL״̬��
            // �������ߣ� 2006 09 04 zz �����޸� ��������޶����°�·�߱���Ĺ����㲿����·��״̬ԭΪ���õı�Ϊȡ���ˣ�
            // ��������Ӧ�ø���ǰһ�汾��״̬ ���Բ���״̬��Ϊȡ��
            // listLinkInfo1.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
            //����initialUsedΪ�µĴ�汾��
            listLinkInfo1.setInitialUsed(reviseroute.getVersionID());
            persistservice.saveValueInfo(listLinkInfo1);
        }
        return reviseroute;
    }

    //end CR8

    //CR10 begin
    public Collection findMultPartsByNumbers(Object[] param)throws QMException
    {
        Collection result = null;
        try
        {
            if(param != null && param.length > 0)
            {
                PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
                QMQuery query = new QMQuery("QMPartMaster");
                query.setChildQuery(false);
                for(int i = 0;i < param.length;i++)
                {
                    if(param[i] != null && param[i].toString().trim().length() > 0)
                    {
                        QueryCondition cond = new QueryCondition("partNumber", QueryCondition.LIKE, "%" + param[i].toString() + "%");
                        if(query.getConditionCount() > 0)
                        {
                            query.addOR();
                        }
                        query.addCondition(cond);
                    }
                }
                if(query.getConditionCount() > 0)
                {
                    query.addOrderBy("partNumber", false);
                    result = pservice.findValueInfo(query, false);
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            throw new QMException(e);
        }

        return result;
    }

    //CR10 end

    //begin CR11
    public boolean getAdoptStatusByPart(String partmasterID)throws QMException
    {
        boolean flag = false;
        try
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
            QueryCondition cond = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partmasterID);
            query.addCondition(cond);
            query.addAND();
            //�п������δʹ��·�ߡ�
            QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
            query.addCondition(cond1);
            query.addAND();
            QueryCondition cond2 = new QueryCondition("releaseIdenty", QueryCondition.EQUAL, 1);
            query.addCondition(cond2);
            Collection coll = pservice.findValueInfo(query);
            if(coll.size() > 0)
            {
                flag = true;
            }
            System.out.println("sql==" + query.getDebugSQL());
        }catch(Exception e)
        {
            e.printStackTrace();
            throw new QMException(e);
        }
        return flag;
    }

    //end CR11

    // CR12 begin
    /**
     * ���ݱ��֪ͨ���BsoID�������������㲿������
     * @param bsoid String
     * @return Vector
     */
    public Vector findQMPartByChangeOrder(Vector changeorderinfo)throws QMException
    {
    
        Vector ver = new Vector();
    	/*  delete by guoxl
        try
        {
            PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
            VersionControlService versionservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
            Iterator iter = changeorderinfo.iterator();
            while(iter.hasNext())
            {
                ModifyInfo info = (ModifyInfo)iter.next();
                String bsoid = info.getBsoID();
                QMQuery query = new QMQuery("ModifyObjectLink");
                query.addLeftParentheses();
                QueryCondition cond = new QueryCondition("leftBsoID", "=", bsoid);
                query.addCondition(cond);
                query.addRightParentheses();
                // add end by tangshutao

                Collection col = ps.findValueInfo(query);
                Iterator ite = col.iterator();
                while(ite.hasNext())
                {
                    ModifyObjectLinkIfc link = (ModifyObjectLinkIfc)ite.next();
                    String partbranchid = link.getRightBsoID();
                    Class[] paraClass = {String.class};
                    String[] obj = {partbranchid};
                    QMPartIfc part = (QMPartIfc)versionservice.getLatestIteration(partbranchid);
                    ver.add(part);
                }
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
        */
        return ver;
        
        
    }
    
    

    /**
     * @param param ��ά���飬5��Ԫ�ء�
     * @J2EE_METHOD -- findChangeOrders ��ñ��֪ͨ�顣������Χ����š����ơ������ߡ�����ʱ�䡣
     * @return collection ���֪ͨ��ֵ���󼯺�
     */

    public Collection findChangeOrders(Object[][] param)
    {
        Collection result = null;
        try
        {
            //CR22 begin
            UsersService userservice = (UsersService)EJBServiceHelper.getService("UsersService");
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            QMQuery query = new QMQuery("Modify");
            query.setChildQuery(false);
            String number = (String)param[0][0];
            boolean numberFlag = ((Boolean)param[0][1]).booleanValue();
            if(number != null && number.trim().length() != 0)
            {
                QueryCondition cond = RouteHelper.handleWildcard("modifyNumber", number, numberFlag);
                query.addCondition(cond);
            }

            String name = (String)param[1][0];
            boolean nameFlag = ((Boolean)param[1][1]).booleanValue();
            if(name != null && name.trim().length() != 0)
            {
                QueryCondition cond1 = RouteHelper.handleWildcard("modifyName", name, nameFlag);
                query.addAND();
                query.addCondition(cond1);

            }

            String creator = (String)param[2][0];
            boolean creatorFlag = ((Boolean)param[2][1]).booleanValue();
            if(creator != null && creator.trim().length() != 0)
            {
                String creatorID = userservice.getUser(creator).getBsoID();
                QueryCondition cond2 = RouteHelper.handleWildcard("creator", creatorID, creatorFlag);
                query.addAND();
                query.addCondition(cond2);

            }
            //CR22 end
            String beginTime = (String)param[3][0];
            String endTime = (String)param[4][0];
            Timestamp beginTimestamp = null;
            Timestamp endTimestamp = null;
            if(beginTime != null && beginTime.trim().length() > 0)
            {
                beginTime = beginTime + " 00:00:00";
                beginTimestamp = new Timestamp(new Date(beginTime).getTime());
                String beginTimestamp1 = beginTimestamp.toString();

                // �޸��ַ��������������ݿ��е�ʱ���ַ������бȽ�
                beginTimestamp1 = beginTimestamp1.replace('-', '/');
                // tang 20070504
                QueryCondition beginTimecondition = new QueryCondition("createTime", ">=", beginTimestamp);
                query.addAND();
                query.addCondition(beginTimecondition);
            }

            if(endTime != null && endTime.trim().length() > 0)
            {
                endTime = endTime + " 23:59:59";
                endTimestamp = new Timestamp(new Date(endTime).getTime());
                String endTimestamp1 = endTimestamp.toString();
                endTimestamp1 = endTimestamp1.replace('-', '/');

                // tang 20070504
                QueryCondition endTimecondition = new QueryCondition("createTime", "<=", endTimestamp);
                query.addAND();
                query.addCondition(endTimecondition);
            }
            //System.out.println("������SQL��䣺" + query.getDebugSQL());

            result = pservice.findValueInfo(query, false);

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param param ��ά���飬5��Ԫ�ء�
     * @J2EE_METHOD -- findChangeOrders ��ñ��֪ͨ�顣������Χ����š����ơ������ߡ�����ʱ�䡣
     * @return collection ���֪ͨ��ֵ���󼯺�
     */

    public Collection findAdoptOrders(Object[][] param)
    {
        Collection result = null;
        try
        {
            //CR22 begin
            UsersService userservice = (UsersService)EJBServiceHelper.getService("UsersService");
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
          //CCBegin SS2
            QMQuery query = new QMQuery("consAdoptNotice");
            query.setChildQuery(false);
            String number = (String)param[0][0];
            boolean numberFlag = ((Boolean)param[0][1]).booleanValue();
            if(number != null && number.trim().length() != 0)
            {
                QueryCondition cond = RouteHelper.handleWildcard("adoptNumber", number, numberFlag);
                query.addCondition(cond);
            }

            String name = (String)param[1][0];
            boolean nameFlag = ((Boolean)param[1][1]).booleanValue();
            if(name != null && name.trim().length() != 0)
            {
                QueryCondition cond1 = RouteHelper.handleWildcard("adoptName", name, nameFlag);
                query.addAND();
                query.addCondition(cond1);

            }
//            String creator = (String)param[2][0];
//            boolean creatorFlag = ((Boolean)param[2][1]).booleanValue();
//            if(creator != null && creator.trim().length() != 0)
//            {
//                String creatorID = userservice.getUser(creator).getBsoID();
//                QueryCondition cond2 = RouteHelper.handleWildcard("creator", creatorID, creatorFlag);
//                query.addAND();
//                query.addCondition(cond2);
//
//            }
            //CCEnd SS2
            //CR22 end
            String beginTime = (String)param[3][0];
            String endTime = (String)param[4][0];
            Timestamp beginTimestamp = null;
            Timestamp endTimestamp = null;

            if(beginTime != null && beginTime.trim().length() > 0)
            {
                beginTime = beginTime + " 00:00:00";
                beginTimestamp = new Timestamp(new Date(beginTime).getTime());
                String beginTimestamp1 = beginTimestamp.toString();
                // tang 20070504
                QueryCondition beginTimecondition = new QueryCondition("createTime", ">=", beginTimestamp);
                query.addAND();
                query.addCondition(beginTimecondition);
            }
            if(endTime != null && endTime.trim().length() > 0)
            {
                endTime = endTime + " 23:59:59";
                endTimestamp = new Timestamp(new Date(endTime).getTime());
                String endTimestamp1 = endTimestamp.toString();
                // tang 20070504
                QueryCondition endTimecondition = new QueryCondition("createTime", "<=", endTimestamp);
                query.addAND();
                query.addCondition(endTimecondition);
            }
            if(VERBOSE)
            {
                System.out.println("query :" + query.getDebugSQL());
            }
          //CCBegin SS2
            //���ѯ�����������°汾����SQL����
            DocLastConfigSpec config = new DocLastConfigSpec();
            config.appendSearchCriteria(query);
          //CCEnd SS2
            result = pservice.findValueInfo(query);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }
   

    /**
     * ���ݲ���֪ͨ���BsoID�������������㲿������
     * @param bsoid String
     * @return Vector
     */
    public Vector findQMPartByAdoptOrder(Vector adoptorderinfo)
    {
        Vector ver = new Vector();

        try
        {
            PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
            VersionControlService versionservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
            Iterator iter = adoptorderinfo.iterator();
            while(iter.hasNext())
            {//CCBegin SS2
            	AdoptNoticeInfo info = (AdoptNoticeInfo)iter.next();
                String bsoid = info.getBsoID();
                QMQuery query = new QMQuery("consAdoptNoticePartLink");
              //CCEnd SS2
                query.addLeftParentheses();
                QueryCondition cond = new QueryCondition("leftBsoID", "=", bsoid);
                query.addCondition(cond);
                query.addRightParentheses();
                // add end by tangshutao
                Collection col = ps.findValueInfo(query);
                Iterator ite = col.iterator();
                while(ite.hasNext())
                {
                	//CCBegin SS2
                	AdoptNoticePartLinkInfo link = (AdoptNoticePartLinkInfo)ite.next();
                    String partid = link.getRightBsoID();
                    QMPartIfc part = (QMPartIfc)ps.refreshInfo(partid);
                    ver.add(part);
                } //CCEnd SS2
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return ver;
    }

    // CR12 end
    //CR13 begin
    public Vector findQMPart(Object[][] param)	throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        Vector result1 = new Vector();
        Collection part1 = null;
        Collection part2 = null;
        // ���
        String number = (String)param[0][0];
        boolean numberFlag = ((Boolean)param[0][1]).booleanValue();
        // ����
        String name = (String)param[1][0];
        boolean nameFlag = ((Boolean)param[1][1]).booleanValue();
        // ����·��
        String makeroute = (String)param[2][0];
        boolean makerouteFlag = ((Boolean)param[2][1]).booleanValue();
        // ·�ߵ��ж�����
        int ifindex = (Integer)param[3][0];
        // װ��·��
        String assembleroute = (String)param[4][0];
        boolean assemblerouteFlag = ((Boolean)param[4][1]).booleanValue();
        // ����·�ߺ�װ��·�߶�Ϊ�գ�ֻ���㲿��
        if(makeroute.equals("") && assembleroute.equals(""))
        {
            result1 = findQMPartByPart(number, name, numberFlag, nameFlag);
        }
        // ������ƶ�Ϊ�գ�ֻ����·�����㲿��
        else if(number.equals("") && name.equals(""))
        {
            result1 = findQMPartByRoute(makeroute, assembleroute, ifindex, makerouteFlag, assemblerouteFlag);
        }else
        {
            part1 = findQMPartByPart(number, name, numberFlag, nameFlag);
            part2 = findQMPartByRoute(makeroute, assembleroute, ifindex, makerouteFlag, assemblerouteFlag);
            Iterator iterator = part1.iterator();
            while(iterator.hasNext())
            {
                //CCBegin SS19
                //QMPartMasterInfo partmaster1 = (QMPartMasterInfo)iterator.next();
                QMPartInfo partmaster1 = (QMPartInfo)iterator.next();
                //CCEnd SS19
                Iterator iterator1 = part2.iterator();
                while(iterator1.hasNext())
                {
                    //CCBegin SS19
                    //QMPartMasterInfo partmaster2 = (QMPartMasterInfo)iterator1.next();
                    QMPartInfo partmaster2 = (QMPartInfo)iterator1.next();
                    //CCEnd SS19
                    if(partmaster1.getBsoID().equals(partmaster2.getBsoID()))
                    {
                        result1.add(partmaster1);
                    }
                }
            }
        }
        return result1;
    }

    /**
     * ����·��ID����㲿��
     * @param routeIDVec ·�߼���
     * @return
     */
    private Vector getPartByRouteID(Vector routeIDVec)throws QMException
    {
        Collection result = null;
        Vector partVec = new Vector();
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        try
        {
            for(int i = 0, j = routeIDVec.size();i < j;i++)
            {

                TechnicsRouteInfo routeinfo = (TechnicsRouteInfo)routeIDVec.elementAt(i);
                String routeID = routeinfo.getBsoID();
                QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME, PARTMASTER_BSONAME);
                query.setChildQuery(false);
                query.addCondition(0, new QueryCondition("routeID", QueryCondition.EQUAL, routeID));
                query.addAND();
                query.addCondition(0, 1, new QueryCondition(RIGHTID, "bsoID"));
                query.setVisiableResult(0);
                //System.out.println("������SQL��䣺" + query.getDebugSQL());
                result = (Collection)pservice.findValueInfo(query, false);
                Iterator iterator = result.iterator();
                while(iterator.hasNext())
                {
                    QMPartMasterInfo partmaster = (QMPartMasterInfo)iterator.next();
                    //CCBegin SS19
                    //partVec.add(partmaster);
                    QMPartIfc part = this.filteredIterationsOfByDefault(partmaster);
                    if(part!=null)
                    partVec.add(part);
                    //CCEnd SS19
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return partVec;
    }
//CR24 begin
    private Vector findQMPartByRoute(String makeroute, String assembleroute, int ifindex, boolean makerouteFlag, boolean assemblerouteFlag)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        Vector routeIDVec = new Vector();
        Collection result = null;
        Vector result1 = null;
        QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
        if(!makeroute.equals(""))
        {
            if(!makerouteFlag)
            {
                query.addCondition(new QueryCondition("routeStr", QueryCondition.NOT_LIKE, "%" + makeroute + "%"));
            }else
            {
                query.addCondition(new QueryCondition("routeStr", QueryCondition.LIKE, "%" + makeroute + "%"));
            }
            if(!assembleroute.equals(""))
            {
                if(ifindex == 0)
                {
                    query.addAND();
                }else
                {
                    query.addOR();
                }
            }
        }
        if(!assembleroute.equals(""))
        {

            if(!assemblerouteFlag)
            {
                query.addCondition(new QueryCondition("routeStr", QueryCondition.NOT_LIKE, "%" + assembleroute + "%"));
            }else
            {
                query.addCondition(new QueryCondition("routeStr", QueryCondition.LIKE, "%" + assembleroute + "%"));
            }
        }
        // ������ظ�
        query.setDisticnt(true);
        //System.out.println("������SQL��䣺" + query.getDebugSQL());
        result = (Collection)pservice.findValueInfo(query, false);
        Iterator iterator = result.iterator();
        while(iterator.hasNext())
        {
            TechnicsRouteBranchInfo techroutebranch = (TechnicsRouteBranchInfo)iterator.next();
            // ���·�ߴ�
            String routestr = techroutebranch.getRouteStr();
            int index = routestr.indexOf("=");
            //CCBegin SS19
            //String make = routestr.substring(0, index);
            //String assemble = routestr.substring(index + 1, routestr.length());
            String make = "";
            String assemble = "";
            if(index==-1)
            {
            // �������·��
            make = routestr;
          }
          else
          {
            // �������·��
            make = routestr.substring(0, index);
            // ���װ��·��
            assemble = routestr.substring(index + 1, routestr.length());
          }
          //CCEnd SS19
            if(assembleroute.equals(""))
            {
                if(makerouteFlag)
                {
                    if(make.indexOf(makeroute) != -1)
                    {
                        routeIDVec.add(techroutebranch.getRouteInfo());
                    }
                }else
                {
                    if(make.indexOf(makeroute) == -1)
                    {
                        routeIDVec.add(techroutebranch.getRouteInfo());
                    }
                }
            }else if(makeroute.equals(""))
            {
                if(assemblerouteFlag)
                {
                    if(assemble.indexOf(assembleroute) != -1)
                    {
                        routeIDVec.add(techroutebranch.getRouteInfo());
                    }
                }else
                {
                    if(assemble.indexOf(assembleroute) == -1)
                    {
                        routeIDVec.add(techroutebranch.getRouteInfo());
                    }
                }
            }else if(!makeroute.equals("") && !assembleroute.equals(""))
            {
                if(ifindex == 0)
                { // ����Ϊ���롱��ʱ�� ��Ҫͬʱ����
                    if(!makerouteFlag && !assemblerouteFlag)
                    {
                        if(make.indexOf(makeroute) == -1 && assemble.indexOf(assembleroute) == -1)
                        {
                            routeIDVec.add(techroutebranch.getRouteInfo());
                        }
                    }else if(makerouteFlag && assemblerouteFlag)
                    {
                        if(make.indexOf(makeroute) != -1 && assemble.indexOf(assembleroute) != -1)
                        {
                            routeIDVec.add(techroutebranch.getRouteInfo());
                        }
                    }else if(!makerouteFlag && assemblerouteFlag)
                    {
                        if(make.indexOf(makeroute) == -1 && assemble.indexOf(assembleroute) != -1)
                        {
                            routeIDVec.add(techroutebranch.getRouteInfo());
                        }
                    }else if(makerouteFlag && !assemblerouteFlag)
                    {
                        if(make.indexOf(makeroute) != -1 && assemble.indexOf(assembleroute) == -1)
                        {
                            routeIDVec.add(techroutebranch.getRouteInfo());
                        }
                    }
                }else
                {
                    if(!makerouteFlag && !assemblerouteFlag)
                    {
                        if(make.indexOf(makeroute) == -1 || assemble.indexOf(assembleroute) == -1)
                        {
                            routeIDVec.add(techroutebranch.getRouteInfo());
                        }
                    }else if(makerouteFlag && assemblerouteFlag)
                    {
                        if(make.indexOf(makeroute) != -1 || assemble.indexOf(assembleroute) != -1)
                        {
                            routeIDVec.add(techroutebranch.getRouteInfo());
                        }
                    }else if(!makerouteFlag && assemblerouteFlag)
                    {
                        if(make.indexOf(makeroute) == -1 || assemble.indexOf(assembleroute) != -1)
                        {
                            routeIDVec.add(techroutebranch.getRouteInfo());
                        }
                    }else if(makerouteFlag && !assemblerouteFlag)
                    {
                        if(make.indexOf(makeroute) != -1 || assemble.indexOf(assembleroute) == -1)
                        {
                            routeIDVec.add(techroutebranch.getRouteInfo());
                        }
                    }
                }

                //                    //��ѡ�С��ǡ�
                //                if(!makerouteFlag && !assemblerouteFlag)
                //                {
                //                    //����װ��·��Ϊ�ղ���ѡ�С��ǡ�
                //                    if(!makeroute.equals("") && assembleroute.equals(""))
                //                    {
                //                        if(make.indexOf(makeroute) == -1)
                //                        {
                //                            routeIDVec.add(techroutebranch.getRouteInfo());
                //                        }
                //                    } //��������·��Ϊ�ղ���ѡ�С��ǡ�
                //                    else if(makeroute.equals("") && !assembleroute.equals(""))
                //                    {
                //                        if(assemble.indexOf(assembleroute) == -1)
                //                        {
                //                            routeIDVec.add(techroutebranch.getRouteInfo());
                //                        }
                //                    }else
                //                    {
                //                        if(make.indexOf(makeroute) == -1 && assemble.indexOf(assembleroute) == -1)
                //                        {
                //                            routeIDVec.add(techroutebranch.getRouteInfo());
                //                        }
                //                    }
                //                    
                //                }
                //               //��ûѡ�С��ǡ�
                //                else if(makerouteFlag && assemblerouteFlag)
                //                {
                //                    if(make.indexOf(makeroute) != -1 && assemble.indexOf(assembleroute) != -1)
                //                    {
                //                        routeIDVec.add(techroutebranch.getRouteInfo());
                //                    }
                //                }
                //                //��һ��ѡ�С��ǡ�
                //                else 
                //                {
                //                    //ѡ��װ��·�ߵġ��ǡ�
                //                    if(makerouteFlag && !assemblerouteFlag)
                //                    {
                //                        if(assembleroute.equals(""))
                //                        {
                //                            if(make.indexOf(makeroute) != -1)
                //                            {
                //                                routeIDVec.add(techroutebranch.getRouteInfo());
                //                            }
                //                        }else if(!assembleroute.equals(""))
                //                        {
                //                            if(assemble.indexOf(assembleroute) == -1)
                //                            {
                //                                routeIDVec.add(techroutebranch.getRouteInfo());
                //                            }
                //                        }
                //                        if(make.indexOf(makeroute) != -1 && assemble.indexOf(assembleroute) == -1)
                //                        {
                //                            routeIDVec.add(techroutebranch.getRouteInfo());
                //                        }
                //                    }
                //                    else if(!makerouteFlag && assemblerouteFlag)
                //                    {
                //                        
                //                    }
                //                }

            }

        }
        if(!routeIDVec.isEmpty())
        {
            result1 = getPartByRouteID(routeIDVec);
        }
        return result1;
    }
//CR24 end
    private Vector findQMPartByPart(String number, String name, boolean numberFlag, boolean nameFlag)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        Vector result1 = null;
        try
        {
            QMQuery query = new QMQuery(PARTMASTER_BSONAME);
            query.setChildQuery(false);
            if(number != null && number.trim().length() != 0)
            {
                QueryCondition cond = RouteHelper.handleWildcard("partNumber", number, numberFlag);
                query.addCondition(cond);
            }
            if(name != null && name.trim().length() != 0)
            {
                QueryCondition cond1 = RouteHelper.handleWildcard("partName", name, nameFlag);
                query.addAND();
                query.addCondition(cond1);
            }
            //System.out.println("������SQL��䣺" + query.getDebugSQL());
            result1 = (Vector)pservice.findValueInfo(query, false);
            //CCBegin SS4
            result1 = findQMPart(result1);
          //CCEnd SS4
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return result1;
    }
//CR27 begin
    /**
     * �����㲿���������Ӽ�,���ṹ���ʱʹ��
     */
    public Object[] getAllPartAndSubPartByCurConfigSpec(Vector parentPart)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        VersionControlService verservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
        QMPartMasterIfc[] aa = null;
        Object[] obj= new Object[2];
        try
        {
            for(int i = 0;i < parentPart.size();i++)
            {
                //CCBegin SS18
                /*QMPartMasterIfc master = (QMPartMasterIfc)parentPart.get(i);
              Collection verCol= verservice.allVersionsOf(master);
              QMPartIfc part=null;
              Vector masterPartVec=new Vector();
              Vector partVec=new Vector();
              Iterator it=verCol.iterator();
              if(it.hasNext())
              {
                 part=(QMPartIfc)it.next();
              }*/
              Vector masterPartVec=new Vector();
              Vector partVec=new Vector();
              QMPartIfc part = (QMPartIfc)parentPart.get(i);
              //CCEnd SS18
                Vector col = getAllSubParts(part);
                for(int j=0;j<col.size();j++)
                {
                   if(((NormalPart)col.get(j)).getBsoID().indexOf("QMPartMaster")!=-1)
                   {
                       QMPartMasterIfc masterpartinfo=(QMPartMasterIfc)pservice.refreshInfo(((NormalPart)col.get(j)).getBsoID());
                       masterPartVec.addElement(masterpartinfo);
                   }else
                   {
                       QMPartIfc partinfo=(QMPartIfc)pservice.refreshInfo(((NormalPart)col.get(j)).getBsoID());
                       partVec.addElement(partinfo);
                   }
                }
                 obj[0]=masterPartVec;
                 obj[1]=partVec;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            throw new QMException(e);
        }
        return obj;
    }
  //CR27 end
    //CR13 end

    //begin CR16
    /**
     * �õ��㲿�������и����Լ�·�����
     * @param partmaster QMPartMasterIfc��listID String
     * @throws QMException
     * @return Vector
     */
    public Vector findParentsAndRoutes(QMPartMasterIfc partmaster)throws QMException
    {
        Vector result = new Vector();
        StandardPartService standardPartService = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
        QMPartIfc part = this.filteredIterationsOfByDefault(partmaster);
        PartConfigSpecIfc configSpecIfc = standardPartService.findPartConfigSpecIfc();
        Collection col = standardPartService.getParentPartsByConfigSpec(part, configSpecIfc);
        if(col == null || col.size() == 0)
        {
            return result;
        }
        Iterator i1 = col.iterator();
        while(i1.hasNext())
        {
            Object[] temp = new Object[4];
            Object[] objs = (Object[])i1.next();
            PartUsageLinkIfc linkinfo = (PartUsageLinkIfc)objs[0];
            QMPartInfo parentinfo = (QMPartInfo)objs[1];
            temp[0] = parentinfo;
            temp[1] = linkinfo;
            //��ȡ��Ч·��  ֻ��һ��
            Collection cols = this.findPartEffRoute(parentinfo.getMasterBsoID());
            if(cols == null || cols.size() == 0)
            {
                temp[2] = null;
                temp[3] = null;
            }
            Iterator i = cols.iterator();
            ListRoutePartLinkInfo info = null;
            TechnicsRouteListInfo routelist = null;

            //�������ˣ������ǰ·�߱�����·�ߣ���ȡ��ǰ·�߱��еģ����������µġ����⣬��Ҫ��׼��
            if(i.hasNext())
            {
                Object[] objss = (Object[])i.next();
                info = (ListRoutePartLinkInfo)objss[0];
                routelist = (TechnicsRouteListInfo)objss[1];
                //if(routelist1.getRouteListState().equals("��׼"))
                //   continue;
                //                if(routelist1.getBsoID().equals(listID))
                //                {
                //                    info = info1;
                //                    routelist = routelist1;
                //                    break;
                //                }else
                //                {
                //                    if(info == null)
                //                    {
                //                        info = info1;
                //                        routelist = routelist1;
                //                    }else if(routelist.getModifyTime().before(routelist.getModifyTime()))
                //                    {
                //                        info = info1;
                //                        routelist = routelist1;
                //                    }
                //                }
            }
            if(info != null)
            {
                // Object[] objss = (Object[]) i.next();
                //ListRoutePartLinkInfo info = (ListRoutePartLinkInfo) objss[0];
                //  TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) objss[1];
                temp[2] = routelist;
                HashMap map = (HashMap)getRouteBranchs(info.getRouteID());

                if(map == null || map.size() == 0)
                {
                    continue;
                }
                String routeText = "";
                Iterator values = map.values().iterator();
                while(values.hasNext())
                {
                    boolean isTemp = false;
                    String makeStr = "";
                    Object[] objs1 = (Object[])values.next();
                    Vector makeNodes = (Vector)objs1[0]; //����ڵ�
                    RouteNodeIfc asseNode = (RouteNodeIfc)objs1[1]; //װ��ڵ�
                    if(asseNode != null && asseNode.getIsTempRoute())
                    {
                        isTemp = true;
                    }
                    if(makeNodes != null && makeNodes.size() > 0)
                    {
                        for(int m = 0;m < makeNodes.size();m++)
                        {
                            RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
                            if(node.getIsTempRoute())
                            {
                                isTemp = true;
                            }
                            if(makeStr == "")
                            {
                                makeStr = makeStr + node.getNodeDepartmentName();
                            }else
                            {
                                makeStr = makeStr + "-" + node.getNodeDepartmentName();
                            }
                        }
                    }
                    if(asseNode != null)
                    {
                        makeStr = makeStr + "-" + asseNode.getNodeDepartmentName();
                    }
                    if(isTemp)
                    {
                        makeStr = makeStr + "(��ʱ)";
                    }
                    if(makeStr == null || makeStr.equals(""))
                    {
                        makeStr = "";
                    }
                    if("".equals(routeText))
                    {
                        routeText = makeStr;
                    }else
                    {
                        routeText = routeText + ";" + makeStr;
                    }
                }
                temp[3] = routeText;
            }
            result.add(temp);
        }
        return result;
    }

    /**
     * ����·�߱�͹�������
     * @param part
     * @return
     */
    private Collection getRoutesAndLinks(QMPartIfc part)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //���·�߱�����·�ߵĹ���
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
        int k = query.appendBso(this.ROUTELIST_BSONAME, true);
        //part��master��id
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, part.getMasterBsoID());
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond4);
        query.addAND();
        //·�߲�Ϊ��
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(0, cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
        query.addCondition(0, j, cond6);
        query.addAND();
        QueryCondition cond7 = new QueryCondition("leftBsoID", "bsoID");
        query.addCondition(0, k, cond7);
        //SQL������������
        query.setDisticnt(true);
        //����ListRoutePartLinkIfc
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's getPartLevelRoutes SQL = " + query.getDebugSQL());
        }
        return pservice.findValueInfo(query, false);

    }

    //end CR16

    //begin CR15
    //CR23 begin
    /**
     * ����·�߷���
     */
    public TechnicsRouteListIfc releaseListPartsRoute(TechnicsRouteListIfc routeList)throws QMException
    {
        //CR21 begin
        String lifeCycleState = RemoteProperty.getProperty("lifeCycleState");
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        Vector linkCol = (Vector)getRouteListLinkParts(routeList);
        routeList = (TechnicsRouteListIfc)pservice.refreshInfo(routeList);
        if(routeList.getLifeCycleState().toString().equals(lifeCycleState))
        {
            throw new QMException("����׼�Ѿ�������·�ߣ��������·���·�ߣ�");
        }
        //CR21 end
        if(linkCol != null)
        {
            LifeCycleTemplateInfo template = (LifeCycleTemplateInfo)pservice.refreshInfo(routeList.getLifeCycleTemplate());
            LifeCycleService lifeService = (LifeCycleService)EJBServiceHelper.getService("LifeCycleService");
            Vector vec = (Vector)lifeService.findStates(template);
            Iterator ite = vec.iterator();
            Vector stateName = new Vector();
            while(ite.hasNext())
            {
                LifeCycleState state = (LifeCycleState)ite.next();
                stateName.add(state.toString());
            }
            if(stateName.contains(lifeCycleState))
            {
                routeList.setLifeCycleState(LifeCycleState.toLifeCycleState(lifeCycleState));
            }else
            {
            
                throw new QMException("����׼�е���������ģ����û���ѷ���״̬�����ܷ���·�ߣ�");
            }
            for(int i = 0, j = linkCol.size();i < j;i++)
            {
                ListRoutePartLinkIfc linkIfc = (ListRoutePartLinkIfc)linkCol.elementAt(i);
                if(linkIfc.getReleaseIdenty() == -1)
                {
                    //�������׼�����û�з�����·�ߣ������÷�����ʶΪ1������������Чʱ��
                    linkIfc.setReleaseIdenty(1);
                    linkIfc.setEfficientTime(new Timestamp(System.currentTimeMillis()));
                    pservice.updateValueInfo(linkIfc);

                    //���������������׼����û����Ч·�ߣ���������ΪʧЧ����������ʧЧ����
                    QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                    QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, linkIfc.getRightBsoID());
                    query1.addCondition(cond3);
                    query1.addAND();
                    QueryCondition cond5 = new QueryCondition(LEFTID, QueryCondition.NOT_EQUAL, routeList.getBsoID());
                    query1.addCondition(cond5);
                    query1.addAND();
                    QueryCondition cond7 = new QueryCondition("releaseIdenty", QueryCondition.EQUAL, 1);
                    query1.addCondition(cond7);
                    if(routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
                    {
                        //��������Ͳ�Ʒ��أ���ȡ���˼��ڴ˲�Ʒ�е�·��
                        if(linkIfc.getProductIdenty() == true)
                        {
                            //��Ӳ�Ʒ��Ϣ
                            query1.addAND();
                            String productID = linkIfc.getProductID();
                            QueryCondition condPn = new QueryCondition("productID", QueryCondition.EQUAL, productID);
                            query1.addCondition(condPn);
                        }
                    }
                    if(routeMode.equals("parentRelative") || routeMode.equals("productAndparentRelative"))
                    {
                        //��Ӹ�����Ϣ
                        query1.addAND();
                        QueryCondition condP;
                        String parentID = linkIfc.getParentPartID();
                        if(parentID != null && !parentID.trim().equals(""))
                        {
                            condP = new QueryCondition("parentPartID", QueryCondition.EQUAL, parentID);
                        }else
                        {
                            condP = new QueryCondition("parentPartID", QueryCondition.IS_NULL);
                        }
                        query1.addCondition(condP);
                    }
                    if(VERBOSE)
                    {
                        System.out.println(TIME + "routeSevice's saveListRoutePartLink INHERIT SQL = " + query1.getDebugSQL());
                    }
                    Collection coll1 = pservice.findValueInfo(query1);
                    if(coll1 != null && coll1.size() > 0)
                    {
                        Iterator it = coll1.iterator();
                        if(it.hasNext())
                        {
                            ListRoutePartLinkIfc releaselinkIfc = (ListRoutePartLinkIfc)it.next();
                            releaselinkIfc.setReleaseIdenty(0);
                            releaselinkIfc.setDisabledDateTime(new Timestamp(System.currentTimeMillis()));
                            pservice.updateValueInfo(releaselinkIfc);
                        }
                    }
                }
            }
            routeList = (TechnicsRouteListIfc)pservice.updateValueInfo(routeList);
        }
        return routeList;
    }

    //CR23 end
    //end CR15

    /**
     * ����ָ���������Ч·�ߡ�ʧЧ·�� 20120110 �촺Ӣ add
     */
    public Object[] findPartEffAndDisabledRoute(String partMasterID)throws QMException
    {
        Object[] obj = new Object[2];
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query1.addCondition(cond1);
        query1.addAND();
        QueryCondition cond2 = new QueryCondition("releaseIdenty", QueryCondition.EQUAL, 1);
        query1.addCondition(cond2);
        Collection coll1 = pservice.findValueInfo(query1);
        if(coll1 != null && coll1.size() > 0)
        {
            Iterator it = coll1.iterator();
            if(it.hasNext())
            {
                obj[0] = (ListRoutePartLinkIfc)it.next();
            }
        }
        QMQuery query2 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query2.addCondition(cond3);
        query2.addAND();
        QueryCondition cond4 = new QueryCondition("releaseIdenty", QueryCondition.EQUAL, 0);
        query2.addCondition(cond4);
        query2.addAND();
        QueryCondition cond5 = new QueryCondition("disabledDateTime", QueryCondition.NOT_NULL);
        query2.addCondition(cond5);
        query2.addOrderBy("disabledDateTime", true);
        Collection coll2 = pservice.findValueInfo(query2);
        if(coll2 != null && coll2.size() > 0)
        {
            Iterator it = coll2.iterator();
            if(it.hasNext())
            {
                obj[1] = (ListRoutePartLinkIfc)it.next();
            }
        }
        return obj;
    }

    //CR17 begin
    /**
     * �ж�Ҫ�����·�߱��е��㲿���Ƿ���·��
     * @param obj
     * @return
     */
    public boolean haveroutelist(TechnicsRouteListInfo obj)throws QMException
    {
        boolean flag = false;
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        TechnicsRouteListInfo listinfo = null;
        String listname = "";
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, obj.getBsoID());
        query.addCondition(cond);
        Collection col = (Collection)pservice.findValueInfo(query);
        Iterator ite = col.iterator();
        while(ite.hasNext())
        {
            ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)ite.next();
            if(link.getRouteID() == null)
            {
                listinfo = (TechnicsRouteListInfo)pservice.refreshInfo(obj.getBsoID());
                listname = listinfo.getRouteListName() + " ";
                break;
            }
        }
        if(!listname.equals(""))
        {
            // DialogFactory.showInformDialog(null, "·�߱�"+listname+"����û��·�ߵ��㲿�������ܼ��룡");
            throw new QMException("·�߱�" + listname + "����û��·�ߵ��㲿�������ܼ��룡");
        }else
        {
            flag = true;
        }

        return flag;
    }

    //CR17 end
    //CR18 begin
    /**
     * ����������������Ч·�ߵ��㲿��
     * @param param
     * @return
     */
    public Vector copyFromByQMPart(Object[][] param)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        Collection result = null;
        Collection result1 = null;
        Vector linkVec = new Vector();
        // ���
        String number = (String)param[0][0];
        boolean numberFlag = ((Boolean)param[0][1]).booleanValue();
        // ����
        String name = (String)param[1][0];
        boolean nameFlag = ((Boolean)param[1][1]).booleanValue();

        QMQuery query = new QMQuery(PARTMASTER_BSONAME);
        query.setChildQuery(false);

        if(number != null && number.trim().length() != 0)
        {
            QueryCondition cond = RouteHelper.handleWildcard("partNumber", number, numberFlag);
            query.addCondition(cond);
        }
        if(name != null && name.trim().length() != 0)
        {
            QueryCondition cond1 = RouteHelper.handleWildcard("partName", name, nameFlag);
            query.addAND();
            query.addCondition(cond1);
        }
        result = (Collection)pservice.findValueInfo(query, false);
        Iterator ite = result.iterator();
        while(ite.hasNext())
        {
            QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
            query1.setChildQuery(false);
            QMPartMasterInfo part = (QMPartMasterInfo)ite.next();
            QueryCondition cond = new QueryCondition(RIGHTID, QueryCondition.EQUAL, part.getBsoID());
            query1.addCondition(cond);
            query1.addAND();
            QueryCondition cond1 = new QueryCondition("releaseIdenty", QueryCondition.EQUAL, 1);
            query1.addCondition(cond1);
            result1 = (Collection)pservice.findValueInfo(query1, false);

            Iterator ite1 = result1.iterator();
            while(ite1.hasNext())
            {
                ListRoutePartLinkInfo link = (ListRoutePartLinkInfo)ite1.next();
                linkVec.add(link);
            }

        }
        return linkVec;
    }

    //CR18 end

    /**
     * ��������Ч·�� 20120112 �촺Ӣ add
     */
    public Collection findPartEffRoute(String partMasterID)throws QMException
    {
        //        ListRoutePartLinkInfo link = null;
        //        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //        QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        //        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        //        query1.addCondition(cond1);
        //        query1.addAND();
        //        QueryCondition cond2 = new QueryCondition("releaseIdenty", QueryCondition.EQUAL, 1);
        //        query1.addCondition(cond2);
        //        query1.addAND();
        //        QueryCondition cond3 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        //        query1.addCondition(cond3);
        //        Collection coll1 = pservice.findValueInfo(query1);
        //        if(coll1 != null && coll1.size() > 0)
        //        {
        //            Iterator it = coll1.iterator();
        //            if(it.hasNext())
        //            {
        //                link = (ListRoutePartLinkInfo)it.next();
        //            }
        //        }
        //        return link;
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //���·�߱�����·�ߵĹ���
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
        int k = query.appendBso(this.ROUTELIST_BSONAME, true);
        //part��master��id
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(0, cond1);
        //        query.addAND();
        //        QueryCondition cond2 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        //        query.addCondition(0, cond2);
        query.addAND();
        //·�߲�Ϊ��
        QueryCondition cond3 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("releaseIdenty", QueryCondition.EQUAL, 1);
        query.addCondition(cond4);
        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", "bsoID");
        query.addCondition(0, j, cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("leftBsoID", "bsoID");
        query.addCondition(0, k, cond6);
        //SQL������������
        query.setDisticnt(true);
        //����ListRoutePartLinkIfc
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's getPartLevelRoutes SQL = " + query.getDebugSQL());
        }
        return pservice.findValueInfo(query, false);

    }

    /**
     * �����µ�·�ߣ�������͹���ʹ�ã�
     */
    public HashMap saveAsRoute(Vector Vec)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        HashMap modelroutemap = new HashMap();
        for(int i = 0, j = Vec.size();i < j;i++)
        {
            RouteWrapData wrapdata = (RouteWrapData)Vec.get(i);
            TechnicsRouteIfc routeinfo = this.saveRoute(null, wrapdata.getRouteMap());
            modelroutemap.put(wrapdata.getPartMasterID(), routeinfo.getBsoID());
        }
        return modelroutemap;
    }

    /**
     * ������͹���
     * @param modelroute
     */
    public void saveModelRoute(ModelRouteInfo modelroute)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        pservice.saveValueInfo(modelroute);
    }

    /**
     * �����Ƿ���ڵ��͹���
     * @param partID masterBsoID
     * @return
     */
    public ModelRouteInfo findModelRouteByPartID(String partID)throws QMException
    {
        Collection result = null;
        ModelRouteInfo modelroute = null;
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("consModelRoute");
        //CCBegin SS49
        DocServiceHelper dsh = new DocServiceHelper();
        String comp = dsh.getUserFromCompany();
        if(comp.equals("cd"))
        {
        	QMPartMasterIfc part = (QMPartMasterIfc)pservice.refreshInfo(partID);
        	String partnumber = part.getPartNumber().trim();
        	if (partnumber.length() >= 7)
        	{
        		partID = partnumber.substring(0, 7);
        	}
        	else
        	{
        		partID = partnumber;
        	}
        }
        //CCEnd SS49
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, partID);
        query.addCondition(cond);
        result = (Collection)pservice.findValueInfo(query);
        Iterator ite1 = result.iterator();
        while(ite1.hasNext())
        {
            modelroute = (ModelRouteInfo)ite1.next();
        }
        return modelroute;
    }

    /**
     * ɾ��·�ߣ�·�߽ڵ����Ϣ
     * @param routeVec
     */
    public void delrouteobject(Vector routeVec)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        for(int i = 0;i < routeVec.size();i++)
        {
            String routeID = (String)routeVec.get(i);
            this.deleteContainedObjects(routeID);
            TechnicsRouteInfo routeinfo = (TechnicsRouteInfo)pservice.refreshInfo(routeID);
            pservice.deleteValueInfo(routeinfo);
        }
    }

    /**
     * ͨ��·��ID�����Ҫ·�ߴ�Ҫ·�ߵ�
     * @param routeVec
     * @param partVec
     * @return
     */
    public HashMap getRouteInformation(Vector routeVec)throws QMException
    {
        HashMap map = new HashMap();
        for(int i = 0;i < routeVec.size();i++)
        {
            ModelRouteInfo modelRoute = (ModelRouteInfo)routeVec.get(i);
            //String partid = (String)partVec.get(i);
            RouteWrapData wrapdata = new RouteWrapData();
            String mainstr = "";
            String secondstr = "";
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            //���·�߱�����·�ߵĹ���
            QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
            QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, modelRoute.getRightBsoID());
            query.addCondition(cond);
            //����
            query.addOrderBy("bsoID", false);
            Collection col = pservice.findValueInfo(query);
            Iterator ite = col.iterator();
            if(ite.hasNext())
            {
                TechnicsRouteBranchInfo routebranchinfo = (TechnicsRouteBranchInfo)ite.next();
                if(routebranchinfo.getRouteStr().endsWith("="))
                {
                    mainstr = routebranchinfo.getRouteStr().substring(0, routebranchinfo.getRouteStr().length() - 1);
                }else
                {
                    mainstr = routebranchinfo.getRouteStr();
                }
                wrapdata.setMainStr(mainstr);
            }
            if(ite.hasNext())
            {
                TechnicsRouteBranchInfo routebranchinfo = (TechnicsRouteBranchInfo)ite.next();
                if(routebranchinfo.getRouteStr().endsWith("="))
                {
                    secondstr = routebranchinfo.getRouteStr().substring(0, routebranchinfo.getRouteStr().length() - 1);
                }else
                {
                    secondstr = routebranchinfo.getRouteStr();
                }
                wrapdata.setSecondStr(secondstr);
            }
            while(ite.hasNext())
            {
                TechnicsRouteBranchInfo routebranchinfo = (TechnicsRouteBranchInfo)ite.next();
                if(routebranchinfo.getRouteStr().endsWith("="))
                {
                    secondstr = secondstr + ";" + routebranchinfo.getRouteStr().substring(0, routebranchinfo.getRouteStr().length() - 1);
                }else
                {
                    secondstr = secondstr + ";" + routebranchinfo.getRouteStr();
                }
                wrapdata.setSecondStr(secondstr);
            }
            TechnicsRouteInfo routeinfo = (TechnicsRouteInfo)pservice.refreshInfo(modelRoute.getRightBsoID());
            wrapdata.setDescription(routeinfo.getRouteDescription());
            wrapdata.setPartMasterID(modelRoute.getLeftBsoID());
            //wrapdataVec.add(wrapdata);
            map.put(modelRoute.getLeftBsoID(), wrapdata);
        }
        return map;
    }

    /**
     * ��ѯ����·����Ҫ
     * @param routeVec
     * @param partVec
     * @return
     */
    private Vector getRouteInformation1(Vector routeVec)throws QMException
    {
        HashMap map = new HashMap();
        Vector vec = new Vector();
        for(int i = 0;i < routeVec.size();i++)
        {
            ModelRouteInfo modelRoute = (ModelRouteInfo)routeVec.get(i);
            //String partid = (String)partVec.get(i);
            RouteWrapData wrapdata = new RouteWrapData();
            String mainstr = "";
            String secondstr = "";
            PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
            //���·�߱�����·�ߵĹ���
            QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
            QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, modelRoute.getRightBsoID());
            query.addCondition(cond);
            //����
            query.addOrderBy("bsoID", false);
            Collection col = pservice.findValueInfo(query);
            Iterator ite = col.iterator();
            if(ite.hasNext())
            {
                TechnicsRouteBranchInfo routebranchinfo = (TechnicsRouteBranchInfo)ite.next();
                if(routebranchinfo.getRouteStr().endsWith("="))
                {
                    mainstr = routebranchinfo.getRouteStr().substring(0, routebranchinfo.getRouteStr().length() - 1);
                }else
                {
                    mainstr = routebranchinfo.getRouteStr();
                }
                wrapdata.setMainStr(mainstr);
            }
            if(ite.hasNext())
            {
                TechnicsRouteBranchInfo routebranchinfo = (TechnicsRouteBranchInfo)ite.next();
                if(routebranchinfo.getRouteStr().endsWith("="))
                {
                    secondstr = routebranchinfo.getRouteStr().substring(0, routebranchinfo.getRouteStr().length() - 1);
                }else
                {
                    secondstr = routebranchinfo.getRouteStr();
                }
                wrapdata.setSecondStr(secondstr);
            }
            while(ite.hasNext())
            {
                TechnicsRouteBranchInfo routebranchinfo = (TechnicsRouteBranchInfo)ite.next();
                if(routebranchinfo.getRouteStr().endsWith("="))
                {
                    secondstr = secondstr + ";" + routebranchinfo.getRouteStr().substring(0, routebranchinfo.getRouteStr().length() - 1);
                }else
                {
                    secondstr = secondstr + ";" + routebranchinfo.getRouteStr();
                }
                wrapdata.setSecondStr(secondstr);
            }
            TechnicsRouteInfo routeinfo = (TechnicsRouteInfo)pservice.refreshInfo(modelRoute.getRightBsoID());
            wrapdata.setDescription(routeinfo.getRouteDescription());
            wrapdata.setPartMasterID(modelRoute.getLeftBsoID());
            vec.add(wrapdata);
            //CCBegin SS46
            //QMPartMasterInfo part = (QMPartMasterInfo)pservice.refreshInfo(modelRoute.getLeftBsoID());
            //CCEnd SS46
            //  map.put(part.getPartNumber(), wrapdata);
        }
        return vec;
    }

    /**
     * �鿴���͹���
     */
    public Vector ViewModelRoute(Object[] param)throws QMException
    {
        Vector routeVec = new Vector();
        Collection result = this.findMultPartsByNumbers(param);
        //CR20 begin
        if(result != null)
        {
            Iterator ite1 = result.iterator();
            while(ite1.hasNext())
            {
                QMPartMasterInfo partinfo = (QMPartMasterInfo)ite1.next();
                ModelRouteInfo modelrouteinfo = this.findModelRouteByPartID(partinfo.getBsoID());
                if(modelrouteinfo != null)
                {
                    routeVec.add(modelrouteinfo);
                }
                //CCBegin SS46
                /*else
                {
                	//����bsoid��ȡ
                	VersionControlService verservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
                	//Collection verCol= verservice.allIterationsOf(partinfo);
                	Collection verCol= verservice.allVersionsOf(partinfo);
                	Iterator it=verCol.iterator();
                	while(it.hasNext())
                	{
                		QMPartIfc part=(QMPartIfc)it.next();
                		ModelRouteInfo modelrouteinfo1 = this.findModelRouteByPartID(part.getBsoID());
                		if(modelrouteinfo1 != null)
                		{
                			routeVec.add(modelrouteinfo1);
                		}
                	}
                }*/
                //CCEnd SS46
            }
        }
        //CR20 end
        Vector vec = this.getRouteInformation1(routeVec);

        return vec;
    }

    /**
     * Ӧ�õ��͹���
     * @param list
     * @return
     */
    public HashMap ApplyModelRoute(ArrayList list)throws QMException
    {
        Vector routeVec = new Vector();
        for(int i = 0;i < list.size();i++)
        {
            String partID = (String)list.get(i);
            //CCBegin SS49
            if(partID.startsWith("QMPart_"))
            {
            	PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
            	QMPartIfc master = (QMPartIfc) pService.refreshInfo(partID);
            	partID = master.getMasterBsoID();
            }
            //CCEnd SS49
            ModelRouteInfo modelrouteinfo = this.findModelRouteByPartID(partID);
            if(modelrouteinfo != null)
            {
                routeVec.add(modelrouteinfo);
            }
        }
        HashMap wrapdataMap = this.getRouteInformation(routeVec);
        return wrapdataMap;
    }

    //begin CR19
    /**
     * �������·�߶���
     */
    public void saveShortCutRoute(List list, String userID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //ɾ���ɵĿ�ݼ�ֵ
        deleteShortCutRoute(userID);
        //�����µĿ�ݼ�ֵ
        for(int i = 0, j = list.size();i < j;i++)
        {
            ShortCutRouteInfo shortCutRoute = (ShortCutRouteInfo)list.get(i);
            pservice.saveValueInfo(shortCutRoute);
        }
    }

    /**
     * ͨ���û������ҿ��·��
     */
    public HashMap getShortRouteMap(String userID)throws QMException
    {
        HashMap map = new HashMap();
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("consShortCutRoute");
        System.out.println("userID===" + userID);
        QueryCondition cond = new QueryCondition("userID", QueryCondition.EQUAL, userID);
        query.addCondition(cond);
        System.out.println(query.getDebugSQL());
        Collection col = pservice.findValueInfo(query);

        System.out.println("col===" + col.size());
        if(col != null && col.size() > 0)
        {
            Iterator it = col.iterator();
            while(it.hasNext())
            {
                ShortCutRouteInfo shortRoute = (ShortCutRouteInfo)it.next();
                map.put(shortRoute.getShortKey(), shortRoute.getRouteStr());
            }
        }
        return map;
    }

    /**
     * ɾ���ɵĿ�ݼ�ֵ
     */
    private void deleteShortCutRoute(String userID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("consShortCutRoute");
        System.out.println("userID===" + userID);
        QueryCondition cond = new QueryCondition("userID", QueryCondition.EQUAL, userID);
        query.addCondition(cond);
        Collection col = pservice.findValueInfo(query);
        System.out.println("col===" + col.size());
        if(col != null && col.size() > 0)
        {
            Iterator it = col.iterator();
            while(it.hasNext())
            {
                ShortCutRouteInfo shortRoute = (ShortCutRouteInfo)it.next();
                pservice.deleteValueInfo(shortRoute);
            }
        }
    }

    //end CR19

    /**
     * ͨ���������ϢID��ȡ�������ù淶�����
     */
    public Object filteredIterationsOf(String masterID)throws QMException
    {
    	//delete by guoxl
		// PersistService pservice =
		// (PersistService)EJBServiceHelper.getPersistService();
		// MasteredIfc master = (MasteredIfc)pservice.refreshInfo(masterID);
		// ConfigService cService =
		// (ConfigService)EJBServiceHelper.getService("ConfigService");
		// return cService.filteredIterationsOf(master);

		StandardPartService partService = (StandardPartService) EJBServiceHelper
				.getService(PART_SERVICE);
		PersistService pService = (PersistService) EJBServiceHelper
				.getService("PersistService");
		QMPartMasterIfc master = (QMPartMasterIfc) pService
				.refreshInfo(masterID);
		PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
		Vector vec = new Vector();
		vec.add(master);
		Collection col;
		col = (Collection) partService.filteredIterationsOf(vec, configSpecIfc);
		if (col != null && col.size() > 0) {
			Object[] parts = (Object[]) col.toArray()[0];
			return (QMPartIfc) parts[0];
		}

		return null;

	}

    /**
	 * ��ò�Ʒ��ʹ���������Ϣ��������š����ơ�·�ߡ���Ʒ��ʹ��������Ϣ��
	 * 
	 * @param productIDs
	 *            ��Ʒid��
	 * @return
	 */
    public Map getProductRelations(String productIDs)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        StringTokenizer token = new StringTokenizer(productIDs, ";");
        //key:����� value:Map( key:��Ʒid value:String[]{ �������·�ߣ�����} )
        Map mapPart = new HashMap();
        while(token.hasMoreTokens())
        {
            String productID = token.nextToken();
            QMPartMasterIfc product = (QMPartMasterIfc)pservice.refreshInfo(productID);
            //}

            //        for(int i = 0; i < list.size(); i++)
            //        {
            //            //��Ʒ
            //            QMPartMasterIfc master = (QMPartMasterIfc)list.get(i);

            QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
            QueryCondition cond = new QueryCondition("productID", QueryCondition.EQUAL, product.getBsoID());
            query.addCondition(cond);
            query.addAND();
            //�п������δʹ��·�ߡ�
            QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
            query.addCondition(cond1);
            query.addOrderBy("rightBsoID", false);
            Collection coll = pservice.findValueInfo(query);
            Iterator it = coll.iterator();
            while(it.hasNext())
            {
                ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)it.next();
                String partNum = link.getPartMasterInfo().getPartNumber();
                //ÿ�������Ӧ��Map
                Map mapSub = (Map)mapPart.get(partNum);
                if(mapSub == null)
                {
                    mapSub = new HashMap();
                    mapPart.put(partNum, mapSub);
                }
                mapSub.put(product.getPartNumber(), new String[]{link.getPartMasterInfo().getPartName(), link.getMainStr(), String.valueOf(link.getProductCount())});
            }

        }
        return mapPart;
    }
  //CR28 begin
    /**
     * ͨ��ԭ��·�ߺ͸��������е�������ԭ��·���������Ĺ���
     * @param original
     * @param partMasterID
     * @return
     * td5963 
     */
    private ListRoutePartLinkIfc findOriginalListPartLink(TechnicsRouteListIfc original, String partMasterID)throws QMException
    {
        ListRoutePartLinkIfc link = null;
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition("leftBsoID", QueryCondition.EQUAL, original.getBsoID());
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition("rightBsoID", QueryCondition.EQUAL, partMasterID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        Iterator it = coll.iterator();
        if(it.hasNext())
        {
            link = (ListRoutePartLinkIfc)it.next();
        }
        return link;
    }
  //CR28 end
    
    public void setAttrForPart(BaseValueIfc primaryBusinessObject) {
        SessionService sessions = null;
        BSXUtil bsxutil = new BSXUtil();
        TechnicsRouteListIfc myRouteList = (TechnicsRouteListIfc)
            primaryBusinessObject;
        try {
          //PartConfigSpecIfc cpnfigspec = this.getCurrentUserConfigSpec();
          PersistService ps = (PersistService) EJBServiceHelper.getService(
              "PersistService");

          TechnicsRouteListIfc routelist = (TechnicsRouteListIfc) ps.refreshInfo(
              myRouteList.getBsoID(), false);
          HashMap pmap = new HashMap();
          Collection coll = this.getRouteListLinkParts(routelist);
          for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
            //�㲿������
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.next();
            String partMasterID = listLinkInfo.getRightBsoID();
            //BaseValueIfc partInfo = (BaseValueIfc)getLastedPartByConfig(partMasterID,cpnfigspec);
            //if(partInfo instanceof QMPartIfc)
            //{
            String status = listLinkInfo.getAdoptStatus();
            if (status.equals(RouteAdoptedType.ADOPT.getDisplay())) {
              if (pmap.containsKey(partMasterID)) {
                continue;
              }
              else {
                pmap.put(partMasterID, partMasterID);
              }
            }
            //}

          }

          sessions = (SessionService) EJBServiceHelper.getService("SessionService");

         
          CodeManageTable map = this.getFirstLeveRouteListReportliu(
              myRouteList);
          Enumeration enum1 = map.keys();
          while (enum1.hasMoreElements()) {
            QMPartMasterIfc partmaster = (QMPartMasterIfc)enum1.nextElement();
            if (!pmap.containsKey(partmaster.getBsoID())) {
              continue;
            }

            Collection branches = (Collection) map.get(partmaster);

            String makeStr = "";
            String assemStr = "";
            if (branches != null && branches.size() > 0) {
              Iterator ite = branches.iterator();
              while (ite.hasNext()) {
                String unionStr = (String) ite.next();
                if (unionStr != null) {
                  StringTokenizer hh = new StringTokenizer(unionStr, "@");
                  if (hh.hasMoreTokens()) {
                    makeStr += hh.nextToken() + "/";
//                                   System.out.println("makeStr)))))))))))"+makeStr);
                    assemStr += hh.nextToken() + "/";
//                                   System.out.println("assemStr)))))))))))"+assemStr);
                  }
                }
              }
            }
            //0212
            String makeStr1 = makeStr.substring(0, makeStr.length() - 1);
//                    System.out.println("makeStr1makeStr1______"+makeStr1);
            String assemStr1 = assemStr.substring(0, assemStr.length() - 1);
//                    System.out.println("assemStr1assemStr1_____________"+assemStr1);
            sessions.setAdministrator();
            bsxutil.savePartAttr(partmaster, makeStr1, assemStr1,
                                 myRouteList.getRouteListNumber());
          }
          //savePartAttr(resultsMap);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        finally {
          try {
            sessions.freeAdministrator();
          }
          catch (QMException e1) {
            e1.printStackTrace();
          }
        }
      }
    
    public CodeManageTable getFirstLeveRouteListReportliu(TechnicsRouteListIfc
    	      listInfo) throws QMException {
    	    PersistService pservice = (PersistService) EJBServiceHelper.
    	        getPersistService();
    	    //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
    	    if (!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.
    	        FIRSTROUTE.getDisplay())) {
    	      throw new QMException("·�߱�Ӧ����һ��·�ߡ�");
    	    }
    	    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME,
    	                                PARTMASTER_BSONAME);
    	    QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
    	                                              listInfo.getBsoID());
    	    query.addCondition(cond1);
    	    query.addAND();
    	    QueryCondition cond2 = new QueryCondition("alterStatus",
    	                                              QueryCondition.NOT_EQUAL,
    	                                              PARTDELETE);
    	    query.addCondition(cond2);
    	    QueryCondition cond3 = new QueryCondition(RIGHTID, "bsoID");
    	    query.addAND();
    	    query.addCondition(0, 1, cond3);
    	    query.setVisiableResult(1);
    	    query.addOrderBy(1, "partNumber");
    	    Collection coll = pservice.findValueInfo(query);
    	    CodeManageTable firstMap = new CodeManageTable();
    	    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
    	      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
    	      QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();
    	      // ���ݹ���·��ID��ù���·�߷�֦��ض���
    	      //�������������ɱ��������Ż�
    	      //Map routeMap = getRouteBranchs(linkInfo.getRouteID()); //ԭ����
    	      Map routeMap = this.getDirectRouteBranchStrs(linkInfo.getRouteID());
    	      firstMap.put(partMasterInfo, routeMap.values());
    	    }
    	    if (VERBOSE) {
    	      System.out.println(
    	          "exit getFirstLeveRouteListReport , firstMap.values = " +
    	          firstMap.values());
    	    }
    	    return firstMap;
    	  }
    
    //CCBegin SS1
    /**
     * ���ָ��������ض����ù淶������С�汾 20061209 liuming add
     * @param master QMPartMasterIfc
     * @param configSpecIfc ���configSpecIfc=null�����õ�ǰ�û������ù淶
     * @return QMPartIfc �����ǰ�û������ù淶��û�в鿴�����Ȩ�޻�û�а汾���򷵻�null
     * @throws QMException
     */
    public QMPartIfc getLastedPartByConfig(QMPartMasterIfc master,
                                           PartConfigSpecIfc configSpecIfc) throws
        QMException {
      if (master == null) {
        throw new QMException("Master Is Null");
      }
      if (configSpecIfc == null || configSpecIfc.getStandard() == null ||
          configSpecIfc.getStandard().getViewObjectIfc() == null) {
        //��õ�ǰ�û������ù淶
        configSpecIfc = getCurrentUserConfigSpec();
      }
      ConfigService service = (ConfigService) EJBServiceHelper.getService(
          "ConfigService");

      //{{{{{{�����������°汾
      Collection collection = service.filteredIterationsOf(master,
          new PartConfigSpecAssistant(configSpecIfc));
      if ( (collection == null) || (collection.size() == 0)) {
        return null;
      }
      Iterator iterator = collection.iterator();
      Object obj = iterator.next();
      QMPartIfc partIfc = null;
      if (obj instanceof Object[]) {
        Object[] obj1 = (Object[]) obj;
        partIfc = (QMPartIfc) obj1[0];
      }
      else {
        partIfc = (QMPartIfc) obj;
      }
      return partIfc;
    }
    /**
     * 20070116 liuming add
     * ��ȡ��ǰ�û������ù淶������û����״ε�½ϵͳ������Ĭ�ϵġ�������ͼ��׼�����ù淶��
     * @throws QMException ʹ��ExtendedPartServiceʱ���׳���
     * @return PartConfigSpecIfc ��׼���ù淶��
     */
    public PartConfigSpecIfc getCurrentUserConfigSpec() throws
        QMException {
      StandardPartService spService = (StandardPartService) EJBServiceHelper.
          getService("StandardPartService");
      PartConfigSpecIfc configSpec = (PartConfigSpecIfc) spService.
          findPartConfigSpecIfc();
      if (configSpec == null) {
        configSpec = new PartConfigSpecInfo();
        configSpec.setStandardActive(true);
        configSpec.setBaselineActive(false);
        configSpec.setEffectivityActive(false);
        PartStandardConfigSpec partStandardConfigSpec = new
            PartStandardConfigSpec();
        ViewService viewService = (ViewService) EJBServiceHelper.getService(
            "ViewService");
        ViewObjectIfc view = viewService.getView("������ͼ");
        partStandardConfigSpec.setViewObjectIfc(view);
        partStandardConfigSpec.setLifeCycleState(null);
        partStandardConfigSpec.setWorkingIncluded(true);
        configSpec.setStandard(partStandardConfigSpec);
        ExtendedPartService extendedPartService = (ExtendedPartService)
            EJBServiceHelper.getService("ExtendedPartService");
        return extendedPartService.savePartConfigSpec(configSpec);
      }
      else if (configSpec.getStandard() == null ||
               configSpec.getStandard().getViewObjectIfc() == null) {
        configSpec.setStandardActive(true);
        configSpec.setBaselineActive(false);
        configSpec.setEffectivityActive(false);
        PartStandardConfigSpec partStandardConfigSpec = new
            PartStandardConfigSpec();
        ViewService viewService = (ViewService) EJBServiceHelper.getService(
            "ViewService");
        ViewObjectIfc view = viewService.getView("������ͼ");
        partStandardConfigSpec.setViewObjectIfc(view);
        partStandardConfigSpec.setLifeCycleState(null);
        partStandardConfigSpec.setWorkingIncluded(true);
        configSpec.setStandard(partStandardConfigSpec);
        ExtendedPartService extendedPartService = (ExtendedPartService)
            EJBServiceHelper.getService("ExtendedPartService");
        return extendedPartService.savePartConfigSpec(configSpec);
      }
      else {
        return configSpec;
      }
    }
    /**
     * �ռ��������� 20061209 liuming add
     * @param routeListID String
     * @throws QMException
     */
    public ArrayList gatherExportData(String routeListID) throws
        QMException {
      return ReportLevelOneUtil.getAllInformation2(routeListID);
    }
    //CCEnd SS1
    
     /**
     * �������ڲ�Ʒ�е�ʹ������
     * @param partVec �������
     * @param pruduct ���ڲ�Ʒ
     * @return
     */
    public HashMap getPartsCount(Vector partVec,QMPartMasterIfc pruduct){
    	
    	HashMap partCountMap=null;
    	
    	HashMap reCountMap=new HashMap();
    	 try {
			PersistService pservice = (PersistService) EJBServiceHelper.
			    getPersistService();
			Vector masterInfoVec=new Vector();
			Vector partInfoVec=new Vector();
			for(int i=0;i<partVec.size();i++){
				
				String partMID=(String)partVec.get(i);
				//CCBegin SS20
				//QMPartMasterIfc partM=(QMPartMasterIfc)pservice.refreshInfo(partMID);
				//masterInfoVec.add(partM);
				if(partMID.indexOf("QMPartMaster")!=-1)
				{
					QMPartMasterIfc partM=(QMPartMasterIfc)pservice.refreshInfo(partMID);
					masterInfoVec.add(partM);
				}
				else
				{
					QMPartIfc partM=(QMPartIfc)pservice.refreshInfo(partMID);
					masterInfoVec.add(partM.getMaster());
				}
				//CCEnd SS20
			}
			HashMap partMap=getLatestsVersion1(masterInfoVec);
			
			for(Iterator it=partMap.values().iterator();it.hasNext();){
				
				QMPartIfc part=(QMPartIfc)it.next();
				partInfoVec.add(part);
			}
			
			 partCountMap=this.calCountInProduct(partInfoVec,pruduct);
			 
			 for(Iterator keyIt=partCountMap.keySet().iterator();keyIt.hasNext();){
				 
				 String key=(String)keyIt.next();
				 
				 String value=((Integer)partCountMap.get(key)).toString();
				 
				 QMPartIfc  partInfo=(QMPartIfc)pservice.refreshInfo(key);
				 //CCBegin SS20
				 //String partMasterID=partInfo.getMasterBsoID();
				 //reCountMap.put(partMasterID, value);
				 reCountMap.put(partInfo.getBsoID(), value);
				 //CCEnd SS20
			 }
			
			
		} catch (QMException e) {
			e.printStackTrace();
			return null;
		}
    	
    	 
    	
    	return reCountMap;
    	
    }
    
    
    //��׼֪ͨ������׼�������㲿����״̬
    /**
     * ��׼֪ͨ�鱻��׼�󣬽�·��״̬Ϊ������׼���Ĺ������㲿��״̬����Ϊ����״̬
     * ��·��״̬Ϊ������״̬ʱ�����������㲿��״̬����Ϊ����׼��״̬
     * @author ������
     * @version 1.0
     */
    public void setRouteListPreparePartsState(BaseValueIfc primaryBusinessObject) {
      //CCBegin by wanghonglian 2008-08-12
      SessionService sService = null;
      try {
      	
        VersionControlService vcservice = (VersionControlService)
             EJBServiceHelper.getService
             ("VersionControlService");
             
        sService = (SessionService) EJBServiceHelper.getService(
            "SessionService");
        LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.
            getService("LifeCycleService");
        TechnicsRouteListIfc myRouteList = (TechnicsRouteListIfc)
            primaryBusinessObject;
        //CCBegin SS31
        PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
        //CCEnd SS31
        Collection partsColl = (Collection) getRouteListLinkParts(myRouteList);
        if (partsColl != null) {
          if (myRouteList.getRouteListState() != null &&
              myRouteList.getRouteListState().equals("����׼")) {
            for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
              ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
                  iter.next();
                          
              //CCBegin by liunan 2011-12-15 �������״̬������branchid����㲿����״̬��
              /*String partMasterID = routePartLink.getRightBsoID();
              QMPartInfo partInfo = (QMPartInfo) getLastedPartByConfig(
                  partMasterID, getCurrentUserConfigSpec());*/ 
              QMPartInfo partInfo = null;
              //CCBegin SS30
              String rightid = routePartLink.getRightBsoID();
              if(rightid.indexOf("QMPartMaster_")!=-1)
              //CCEnd SS30
//              if(routePartLink.getPartBranchID()==null)
              {
              	String partMasterID = routePartLink.getRightBsoID();
              	partInfo = (QMPartInfo) getLastedPartByConfig(partMasterID, getCurrentUserConfigSpec());
              }
              //CCBegin SS30
              else if(rightid.indexOf("QMPart_")!=-1)
              {
              	//CCBegin SS31
              	if(routePartLink.getPartBranchID()==null)
              	{
              		partInfo = (QMPartInfo) pservice.refreshInfo(rightid);
              	}
              	else
              	//CCEnd SS31
              	partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
              }
              //CCEnd SS30
//              else
//              {
//              	partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
//              }
              //CCEnd by liunan 2011-12-15
                                      
              sService.setAdministrator();
              lservice.setLifeCycleState(partInfo,
            		  LifeCycleState.toLifeCycleState("BSXTrialProduce"));
              //CCBegin by wanghonglian 2008-08-21
              EPMBuildRuleIfc buildrule = getBuildRule(partInfo);
              if (buildrule != null) {
                EPMDocumentIfc document = (EPMDocumentIfc) buildrule.
                    getBuildSource();
                lservice.setLifeCycleState(document,
                		LifeCycleState.toLifeCycleState("BSXTrialProduce"));
              }
              //CCEnd by wanghonglian 2008-08-21
            }
          }
        //Begin by chudaming 2009-6-25
          else if(myRouteList.getRouteListState() != null &&
                  myRouteList.getRouteListState().equals("����")){
          	for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
                  ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
                      iter.next();
                  
                  //CCBegin by liunan 2011-12-15 �������״̬������branchid����㲿����״̬��
                  /*String partMasterID = routePartLink.getRightBsoID();
                  QMPartInfo partInfo = (QMPartInfo) getLastedPartByConfig(
                      partMasterID, getCurrentUserConfigSpec());*/ 
                  QMPartInfo partInfo = null;
//              if(routePartLink.getPartBranchID()==null)
              //CCBegin SS30
              String rightid = routePartLink.getRightBsoID();
              if(rightid.indexOf("QMPartMaster_")!=-1)
              //CCEnd SS30
              
              {
              	String partMasterID = routePartLink.getRightBsoID();
              	partInfo = (QMPartInfo) getLastedPartByConfig(partMasterID, getCurrentUserConfigSpec());
              }
              //CCBegin SS30
              else if(rightid.indexOf("QMPart_")!=-1)
              {
              	//CCBegin SS31
              	if(routePartLink.getPartBranchID()==null)
              	{
              		partInfo = (QMPartInfo) pservice.refreshInfo(rightid);
              	}
              	else
              	//CCEnd SS31
              	partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
              }
              //CCEnd SS30    
//              else
//              {
//              	partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
//              }
                  //CCEnd by liunan 2011-12-15
                  
                  sService.setAdministrator();
                  lservice.setLifeCycleState(partInfo,
                		  LifeCycleState.toLifeCycleState("BSXDisused"));
                  //CCBegin by wanghonglian 2008-08-21
                  EPMBuildRuleIfc buildrule = getBuildRule(partInfo);
                  if (buildrule != null) {
                    EPMDocumentIfc document = (EPMDocumentIfc) buildrule.
                        getBuildSource();
                    lservice.setLifeCycleState(document,
                    		LifeCycleState.toLifeCycleState("BSXDisused"));
                  }
                  //CCEnd by wanghonglian 2008-08-21
                }
          }
        //End by chudaming 2009-6-25
          else {
            for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
              ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
                  iter.next();
              //CCBegin by liunan 2011-12-15 �������״̬������branchid����㲿����״̬��
              /*String partMasterID = routePartLink.getRightBsoID();
              QMPartInfo partInfo = (QMPartInfo) getLastedPartByConfig(
                  partMasterID, getCurrentUserConfigSpec());*/ 
              QMPartInfo partInfo = null;
//              if(routePartLink.getPartBranchID()==null)
              //CCBegin SS30
              String rightid = routePartLink.getRightBsoID();
              if(rightid.indexOf("QMPartMaster_")!=-1)
              //CCEnd SS30
              
              {
              	String partMasterID = routePartLink.getRightBsoID();
              	partInfo = (QMPartInfo) getLastedPartByConfig(partMasterID, getCurrentUserConfigSpec());
              }
              //CCBegin SS30
              else if(rightid.indexOf("QMPart_")!=-1)
              {
              	//CCBegin SS31
              	if(routePartLink.getPartBranchID()==null)
              	{
              		partInfo = (QMPartInfo) pservice.refreshInfo(rightid);
              	}
              	else
              	//CCEnd SS31
              	partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
              }
              //CCEnd SS30    
//              else
//              {
//              	partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
//              }
              //CCEnd by liunan 2011-12-15
              
              sService.setAdministrator();
              lservice.setLifeCycleState(partInfo, LifeCycleState.toLifeCycleState("BSXTrialYield"));
              //CCBegin by wanghonglian 2008-08-21
              EPMBuildRuleIfc buildrule = getBuildRule(partInfo);
              if (buildrule != null) {
                EPMDocumentIfc document = (EPMDocumentIfc) buildrule.
                    getBuildSource();
                lservice.setLifeCycleState(document,
                		LifeCycleState.toLifeCycleState("BSXTrialYield"));
              }
              //CCEnd by wanghonglian 2008-08-21
            }

          }
        }

      }
      catch (QMException ex) {
        ex.printStackTrace();
      }
      finally {
        try {
          sService.freeAdministrator();
        }
        catch (QMException e) {
          e.printStackTrace();
        }
      }
      //CCEnd by wanghonglian 2008-08-12
    }
    
    /**
     * ���ָ��������ض����ù淶������С�汾 20061209 liuming add
     * @param partMasterBsoID
     * @param configSpecIfc ���configSpecIfc=null�����õ�ǰ�û������ù淶
     * @return QMPartIfc �����ǰ�û������ù淶��û�в鿴�����Ȩ�޻�û�а汾���򷵻�null
     * @throws QMException
     */
    public QMPartIfc getLastedPartByConfig(String partMasterBsoID,
                                           PartConfigSpecIfc configSpecIfc) throws
        QMException {
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      QMPartMasterIfc masterInfo = (QMPartMasterIfc) pservice.refreshInfo(
          partMasterBsoID);
      return getLastedPartByConfig(masterInfo, configSpecIfc);
    }
    
    /**
     * ���ڸ������㲿������ȡ���Ĺ�����򣬸ù��������EPM�ĵ������ǹ�������.
     * @param part QMPartIfc
     * @throws QMException
     * @return EPMBuildRuleIfc
     */
    private EPMBuildRuleIfc getBuildRule(QMPartIfc part) throws QMException {
      PersistService pService = (PersistService) EJBServiceHelper
          .getService("PersistService");
      // Collection collection =
      // pService.navigateValueInfo(part,"buildTarget",new
      // EPMBuildLinksRuleInfo().getBsoName(),false);
      QMQuery query = new QMQuery(new EPMBuildLinksRuleInfo().getBsoName());
      QueryCondition condition = new QueryCondition("rightBsoID", "=", part
                                                    .getBranchID());
      query.addCondition(condition);
      Collection collection = pService.findValueInfo(query);
      Iterator iter = collection.iterator();
      while (iter.hasNext()) {
        EPMBuildRuleIfc rule = (EPMBuildRuleIfc) iter.next();
        if (!WorkInProgressHelper.isWorkingCopy( (WorkableIfc) rule.
                                                getBuildSource())) {
          return rule;
        }
      }
      return null;
    }
    
    public ArrayList getRouteCompleteLinkedPartByBsoID(String
    	      routeCompleteBsoID) {
    	    try {
    	      ArrayList partColl = new ArrayList();
    	      PersistService service = (PersistService) EJBServiceHelper.
    	          getPersistService();
    	      QMQuery query = new QMQuery("CompletPartLink");
    	      query.addCondition(new QueryCondition("leftBsoID", "=",
    	                                            routeCompleteBsoID));
    	      Collection coll = service.findValueInfo(query);
    	      for (Iterator iterator = coll.iterator(); iterator.hasNext(); ) {
    	        CompletPartLinkInfo completePartInfo = (CompletPartLinkInfo)
    	            iterator.next();
    	        String partID = completePartInfo.getRightBsoID();
    	        QMPartInfo partInfo = (QMPartInfo) service.
    	            refreshInfo(partID);
    	        partColl.add(partInfo);
    	      }
    	      return partColl;

    	    }
    	    catch (QMException ex) {
    	      ex.printStackTrace();
    	      return null;
    	    }

    	  }
    /**
     * �����ձ�֪ͨ���BsoID��ȡ�����Ĺ���·��
     *@author ������
     *@version 1.0
     * @param routeCompleteBsoID �ձ�֪ͨ���BsoID
     * @return ����·�߼���
     */
    public ArrayList getCompleteLinkedListByBsoID(String
                                                  routeCompleteBsoID) {
      try {
        ArrayList routeColl = new ArrayList();
        PersistService service = (PersistService) EJBServiceHelper.
            getPersistService();
        QMQuery query = new QMQuery("CompletListLink");
        query.addCondition(new QueryCondition("leftBsoID", "=",
                                              routeCompleteBsoID));
        Collection coll = service.findValueInfo(query);
        for (Iterator iterator = coll.iterator(); iterator.hasNext(); ) {
          CompletListLinkInfo completeListLinkInfo = (
              CompletListLinkInfo)
              iterator.next();
          String routeID = completeListLinkInfo.getRightBsoID();
          TechnicsRouteListInfo routeInfo = (TechnicsRouteListInfo)
              service.refreshInfo(routeID);
          routeColl.add(routeInfo);
        }
        return routeColl;

      }
      catch (QMException ex) {
        ex.printStackTrace();
        return new ArrayList();
      }

    }
    
    /**
     * �����ձ�֪ͨ��������㲿������������״̬
     * ���㲿������������״̬����Ϊ����״̬
     * @author ������
     * @version 1.0
     * @param routeListCompleteID �ձ�֪ͨ���BsoID
     */
    public void setRouteListCompletePartsState(String routeListCompleteID) {
      //CCBegin by wanghonglian 2008-08-12
      SessionService sService = null;
      try {
        sService = (SessionService) EJBServiceHelper.getService(
            "SessionService");
        PersistService perService = (PersistService) EJBServiceHelper.
            getPersistService();
        LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.
            getService("LifeCycleService");
        RoutelistCompletInfo routeListComplete = (RoutelistCompletInfo)
            perService.refreshInfo(
                routeListCompleteID);
        if (routeListComplete.getCompletType() != null &&
            routeListComplete.getCompletType().equals("��׼")) {
          QMQuery query11 = new QMQuery("CompletListLink");
          query11.addCondition(new QueryCondition("leftBsoID", "=",
                                                  routeListCompleteID));
          Collection collf = perService.findValueInfo(query11);
          for (Iterator iterator = collf.iterator(); iterator.hasNext(); ) {
            CompletListLinkInfo comListLink = (CompletListLinkInfo)
                iterator.next();
            TechnicsRouteListInfo routeList = (TechnicsRouteListInfo)
                perService.refreshInfo(comListLink.getRightBsoID());
            Collection partsColl = (Collection) getRouteListLinkParts(routeList);
            for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
              ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
                  iter.next();
              String partMasterID = routePartLink.getRightBsoID();
              QMPartInfo partInfo = (QMPartInfo) getLastedPartByConfig(
                  partMasterID, getCurrentUserConfigSpec());
              sService.setAdministrator();
              lservice.setLifeCycleState(partInfo, LifeCycleState.toLifeCycleState("BSXYield"));
              //CCBegin by wanghonglian 2008-08-21
              //�����㲿��������CAD�ĵ�����������״̬
              EPMBuildRuleIfc buildrule = getBuildRule(partInfo);
              if (buildrule != null) {
                EPMDocumentIfc document = (EPMDocumentIfc) buildrule.
                    getBuildSource();
                lservice.setLifeCycleState(document, LifeCycleState.toLifeCycleState("BSXYield"));
              }
              //CCEnd by wanghonglian 2008-08-21
            }
          }

        }
        if (routeListComplete.getCompletType() != null &&
            routeListComplete.getCompletType().equals("���")) {
          QMQuery query11 = new QMQuery("CompletPartLink");
          query11.addCondition(new QueryCondition("leftBsoID", "=",
                                                  routeListCompleteID));
          Collection collf = perService.findValueInfo(query11);
          for (Iterator iterator = collf.iterator(); iterator.hasNext(); ) {
            CompletPartLinkInfo comPartLink = (CompletPartLinkInfo)
                iterator.next();
            String partID = comPartLink.getRightBsoID();
            QMPartInfo partInfo = (QMPartInfo) perService.refreshInfo(partID);
            sService.setAdministrator();
            lservice.setLifeCycleState(partInfo, LifeCycleState.toLifeCycleState("BSXYield"));
            //CCBegin by wanghonglian 2008-08-21
            //�����㲿��������CAD�ĵ�����������״̬
            EPMBuildRuleIfc buildrule = getBuildRule(partInfo);
            if (buildrule != null) {
              EPMDocumentIfc document = (EPMDocumentIfc) buildrule.getBuildSource();
              lservice.setLifeCycleState(document, LifeCycleState.toLifeCycleState("BSXYield"));
            }
            //CCEnd by wanghonglian 2008-08-21
          }

          QMQuery query1 = new QMQuery("CompletedParts");
          query1.addCondition(new QueryCondition("leftBsoID", "=",
                                                 routeListCompleteID));
          Collection coll = perService.findValueInfo(query1);
          for (Iterator iterator = coll.iterator(); iterator.hasNext(); ) {
            CompletedPartsInfo comPartsLink = (CompletedPartsInfo)
                iterator.next();
            String partID = comPartsLink.getRightBsoID();
            QMPartInfo partInfo = (QMPartInfo) perService.refreshInfo(partID);
            sService.setAdministrator();
            lservice.setLifeCycleState(partInfo, LifeCycleState.toLifeCycleState("BSXYield"));
            //CCBegin by wanghonglian 2008-08-21
            //�����㲿��������CAD�ĵ�����������״̬
            EPMBuildRuleIfc buildrule = getBuildRule(partInfo);
            if (buildrule != null) {
              EPMDocumentIfc document = (EPMDocumentIfc) buildrule.getBuildSource();
              lservice.setLifeCycleState(document, LifeCycleState.toLifeCycleState("BSXYield"));
            }
            //CCEnd by wanghonglian 2008-08-21
          }

        }

      }
      catch (QMException ex) {
        ex.printStackTrace();
      }
      finally {
        try {
          sService.freeAdministrator();
        }
        catch (QMException e) {
          e.printStackTrace();
        }
      }
      //CCEnd by wanghonglian 2008-08-12
    }
    
    public void setRouteListCompletePartsState1(String routeListCompleteID) {
        //CCBegin by wanghonglian 2008-08-12
        SessionService sService = null;
        BSXUtil aa = new BSXUtil();
        try {
          sService = (SessionService) EJBServiceHelper.getService(
              "SessionService");
          PersistService perService = (PersistService) EJBServiceHelper.
              getPersistService();
          LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.
              getService("LifeCycleService");
          RoutelistCompletInfo routeListComplete = (RoutelistCompletInfo)
              perService.refreshInfo(
                  routeListCompleteID);
          if (routeListComplete.getCompletType() != null &&
              routeListComplete.getCompletType().equals("��׼")) {
            QMQuery query11 = new QMQuery("CompletListLink");
            query11.addCondition(new QueryCondition("leftBsoID", "=",
                                                    routeListCompleteID));
            Collection collf = perService.findValueInfo(query11);
            for (Iterator iterator = collf.iterator(); iterator.hasNext(); ) {
              CompletListLinkInfo comListLink = (CompletListLinkInfo)
                  iterator.next();
              TechnicsRouteListInfo routeList = (TechnicsRouteListInfo)
                  perService.refreshInfo(comListLink.getRightBsoID());
              Collection partsColl = (Collection) getRouteListLinkParts(routeList);
              for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
                ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
                    iter.next();
                String partMasterID = routePartLink.getRightBsoID();
                QMPartInfo partInfo = (QMPartInfo) getLastedPartByConfig(
                    partMasterID, getCurrentUserConfigSpec());
                sService.setAdministrator();
                LifeCycleState partstate = partInfo.getLifeCycleState();
//              System.out.println("partstate.toString()==================" +
//                                 partstate.toString());
             String partstatez = "";
             if (partstate.toString().equals("RELEASED")) {
               partstatez = "�ѷ���";
             }
             if (partstate.toString().equals("BSXTrialProduce")) {
               partstatez = "����";
             }
             if (partstate.toString().equals("BSXTrialYield")) {
               partstatez = "����׼��";
             }
             if (partstate.toString().equals("BSXYield")) {
               partstatez = "����";
             }
             
           System.out.println(
                 "partstate.toString()000000000000000=33333333333333344444=================" +
                 partstatez);
             aa.saveAttrForCadFromPart(partInfo, "lifeCycleState", partstatez);

                //CCEnd by wanghonglian 2008-08-21
              }
            }

          }
          if (routeListComplete.getCompletType() != null &&
              routeListComplete.getCompletType().equals("���")) {
            QMQuery query11 = new QMQuery("CompletPartLink");
            query11.addCondition(new QueryCondition("leftBsoID", "=",
                                                    routeListCompleteID));
            Collection collf = perService.findValueInfo(query11);
            for (Iterator iterator = collf.iterator(); iterator.hasNext(); ) {
              CompletPartLinkInfo comPartLink = (CompletPartLinkInfo)
                  iterator.next();
              String partID = comPartLink.getRightBsoID();
              QMPartInfo partInfo = (QMPartInfo) perService.refreshInfo(partID);
              sService.setAdministrator();
              LifeCycleState partstate = partInfo.getLifeCycleState();
//              System.out.println("partstate.toString()==================" +
//                                 partstate.toString());
            String partstatez = "";
            if (partstate.toString().equals("RELEASED")) {
              partstatez = "�ѷ���";
            }
            if (partstate.toString().equals("BSXTrialProduce")) {
              partstatez = "����";
            }
            if (partstate.toString().equals("BSXTrialYield")) {
              partstatez = "����׼��";
            }
            if (partstate.toString().equals("BSXYield")) {
              partstatez = "����";
            }
           
          System.out.println(
                "partstate.toString()000000000000000=33333333333333344444=================" +
                partstatez);
            aa.saveAttrForCadFromPart(partInfo, "lifeCycleState", partstatez);

              //CCEnd by wanghonglian 2008-08-21
            }

            QMQuery query1 = new QMQuery("CompletedParts");
            query1.addCondition(new QueryCondition("leftBsoID", "=",
                                                   routeListCompleteID));
            Collection coll = perService.findValueInfo(query1);
            for (Iterator iterator = coll.iterator(); iterator.hasNext(); ) {
              CompletedPartsInfo comPartsLink = (CompletedPartsInfo)
                  iterator.next();
              String partID = comPartsLink.getRightBsoID();
              QMPartInfo partInfo = (QMPartInfo) perService.refreshInfo(partID);
              sService.setAdministrator();
              LifeCycleState partstate = partInfo.getLifeCycleState();
//              System.out.println("partstate.toString()==================" +
//                                 partstate.toString());
             String partstatez = "";
             if (partstate.toString().equals("RELEASED")) {
               partstatez = "�ѷ���";
             }
             if (partstate.toString().equals("BSXTrialProduce")) {
               partstatez = "����";
             }
             if (partstate.toString().equals("BSXTrialYield")) {
               partstatez = "����׼��";
             }
             if (partstate.toString().equals("BSXYield")) {
               partstatez = "����";
             }
           System.out.println(
                 "partstate.toString()000000000000000=33333333333333344444=================" +
                 partstatez);
             aa.saveAttrForCadFromPart(partInfo, "lifeCycleState", partstatez);

              //CCEnd by wanghonglian 2008-08-21
            }

          }

        }
        catch (QMException ex) {
          ex.printStackTrace();
        }
        finally {
          try {
            sService.freeAdministrator();
          }
          catch (QMException e) {
            e.printStackTrace();
          }
        }
        //CCEnd by wanghonglian 2008-08-12
    }
    
    public Collection getRouteCompleteLinkedParts(String completeListID) throws
    QMException {

		Collection partsColl = new ArrayList();
		PersistService service = (PersistService) EJBServiceHelper
				.getPersistService();
		QMQuery query = new QMQuery("CompletedParts");
		query
				.addCondition(new QueryCondition("leftBsoID", "=",
						completeListID));
		Collection coll = service.findValueInfo(query);
		for (Iterator iterator = coll.iterator(); iterator.hasNext();) {
			CompletedPartsInfo comPartsLinkinfo = (CompletedPartsInfo) iterator
					.next();
			QMPartInfo partInfo = (QMPartInfo) service
					.refreshInfo(comPartsLinkinfo.getRightBsoID());

			partsColl.add(partInfo);
		}
		return partsColl;
}
    
    public Collection getRouteCompleteLinkedProductsNames(String completeID) {
        Collection backColl = new ArrayList();
        try {
          PersistService pservice = (PersistService) EJBServiceHelper.
              getPersistService();
          RoutelistCompletInfo completeInfo = (RoutelistCompletInfo) pservice.
              refreshInfo(completeID);
          if (completeInfo.getCompletType().equals("��׼")) {
            ArrayList routeListInfos = getCompleteLinkedListByBsoID(completeID);
            for (int i = 0; i < routeListInfos.size(); i++) {
              TechnicsRouteListInfo routeListInfo = (TechnicsRouteListInfo)
                  routeListInfos.get(i);
              QMPartMasterInfo partMasterInfo = (QMPartMasterInfo) pservice.
                  refreshInfo(
                      routeListInfo.getProductMasterID());
              String num_name = "��׼֪ͨ�� " + routeListInfo.getRouteListName() +
                  "���ڲ�Ʒ " + partMasterInfo.getPartNumber() + "_" +
                  partMasterInfo.getPartName();
              backColl.add(num_name);
            }
          }
          return backColl;
        }
        catch (QMException ex) {
          ex.printStackTrace();
          return backColl;
        }

      }
    
    public Collection getNeededCollForReport(Collection partMasterIDColl) {
        return getNeededCollForReport(partMasterIDColl, null);
      }
    
    public Collection getNeededCollForReport(Collection partMasterIDColl,
            TechnicsRouteListInfo routeList) {
		try {
			int i = 1;
			PersistService pservice = (PersistService) EJBServiceHelper
					.getPersistService();
			Collection back = new ArrayList();
			String useforMainProduct = "";
			if (routeList != null) {
				// CCBegin by wanghonglian 2008-05-08
				// ������ �ձ�֪ͨ�� �����Ƿ�Ϊ�յ��ж�
				String masterid = routeList.getProductMasterID();
				QMPartMasterIfc mainProductInfo = null;
				if (masterid != null) {
					mainProductInfo = (QMPartMasterIfc) pservice
							.refreshInfo(masterid);
					useforMainProduct = mainProductInfo.getPartNumber();
				}
				// ԭ�������£�
				// QMPartMasterIfc mainProductInfo = (QMPartMasterIfc) pservice.
				// refreshInfo(
				// routeList.getProductMasterID());

				// CCEnd by wanghonglian 2008-05-08
			}
			for (Iterator iter = partMasterIDColl.iterator(); iter.hasNext();) {
				ArrayList beyond = new ArrayList();
				String sqlNum = "";
				String partNum = "";
				String partName = "";
				String count = "";
				String produceRoute = "";
				String setupRoute = "";
				String temRoute = "";
				String modifySign = "";
				String desc = "";
				String youXiaoQi = "";
				String[] produceRoute1 = new String[1];
				String[] setupRoute1 = new String[1];
				String[] temRoute1 = new String[1];
				sqlNum = Integer.toString(i);
				String partmasterID = (String) iter.next();
				QMPartMasterIfc partMasterInfo = (QMPartMasterIfc) pservice
						.refreshInfo(partmasterID);
				partNum = partMasterInfo.getPartNumber();
				partName = partMasterInfo.getPartName();

				QMQuery query = new QMQuery("ListRoutePartLink");
				QueryCondition cond1 = new QueryCondition("rightBsoID",
						QueryCondition.EQUAL, partMasterInfo.getBsoID());
				query.addCondition(cond1);
				if (routeList != null) {
					QueryCondition cond2 = new QueryCondition("leftBsoID",
							QueryCondition.EQUAL, routeList.getBsoID());
					query.addAND();
					query.addCondition(cond2);
				}

				Collection coll = pservice.findValueInfo(query);
				for (Iterator iter1 = coll.iterator(); iter1.hasNext();) {
					ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter1
							.next();
					if (linkInfo.getRouteID() == null) {
						continue;
					}
					TechnicsRouteListInfo routeList1 = (TechnicsRouteListInfo) pservice
							.refreshInfo(linkInfo.getRouteListID());
					// CCBegin by wanghonglian 2008-05-08
					// �����乫˾ �ձ�֪ͨ�� �����Ƿ�Ϊ�յ��ж�
					QMPartMasterIfc mainProductInfo = null;
					if (routeList1 != null) {
						String master = routeList1.getProductMasterID();
						if (master != null) {
							mainProductInfo = (QMPartMasterIfc) pservice
									.refreshInfo(master);
						} else {
							continue;
						}
					}
					// ԭ�������£�
					// QMPartMasterIfc mainProductInfo = (QMPartMasterIfc)
					// pservice.
					// refreshInfo(routeList1.getProductMasterID());
					// CCEnd by wanghonglian 2008-05-08
					useforMainProduct = mainProductInfo.getPartNumber();
					Object[] objs = getRouteAndBrach(linkInfo.getRouteID());
					TechnicsRouteIfc route = (TechnicsRouteIfc) objs[0];
					HashMap map = (HashMap) objs[1];
					if (map.size() > 0) {
						desc = route.getRouteDescription();
						modifySign = route.getModifyIdenty();
						// if (route.getDefaultDescreption() != null) {
						// youXiaoQi = route.getDefaultDescreption();
						// }
						if (route.getRouteDescription() != null) {
							youXiaoQi = route.getRouteDescription();
						}
						count = Integer.toString(linkInfo.getCount());
						String[] hehe = getRouteLineText(map);
						temRoute = hehe[0];
						produceRoute = hehe[1];
						setupRoute = hehe[2];
						produceRoute1 = getBrokenString(produceRoute);
						setupRoute1 = getBrokenString(setupRoute);
						temRoute1 = getBrokenString(temRoute);
						break;
					}
				}
				if (desc == null) {
					desc = "";
				}
				ArrayList allRoute = getRouteInformation(produceRoute1,
						setupRoute1, temRoute1);
				beyond.add(sqlNum);
				beyond.add(partNum);
				beyond.add(partName);
				beyond.add(count);
				beyond.add(allRoute);
				beyond.add(modifySign);
				beyond.add(desc);
				beyond.add(youXiaoQi);
				beyond.add(useforMainProduct);
				back.add(beyond);
				i++;
			}

			return back;
		} catch (QMException ex) {
			ex.printStackTrace();
			return null;
		}

	}
    
    private String[] getRouteLineText(HashMap map) {
        String tempRoute = "";
        String produceRoute = "";
        String setupRoute = "";
        Collection coll = map.keySet();
        int i = 0;
        for (Iterator iter1 = coll.iterator(); iter1.hasNext(); ) {
          Object[] branchs = (Object[]) map.get(iter1.next());
          if (branchs != null && branchs.length > 0) {
            String tempRoute1 = "��";
            Vector makeNodes = (Vector) branchs[0]; //����ڵ�
            RouteNodeIfc asseNode = (RouteNodeIfc) branchs[1];
            if (i > 0) {
              produceRoute = produceRoute + "$$$";
              setupRoute = setupRoute + "$$$";
              tempRoute += "$$$";
            }
            if (makeNodes != null && makeNodes.size() > 0) {
              for (int m = 0; m < makeNodes.size(); m++) {
                RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
                if (node == null) {
                  continue;
                }
                if (node.getIsTempRoute()) {
                  tempRoute1 = "��";
                }
                if (node.getIsTempRoute()) {
                  if (produceRoute.length() > 0 && !produceRoute.endsWith("$$$")) {
                    produceRoute += "-" + node.getNodeDepartmentName() + "*";
                  }
                  else {
                    produceRoute += node.getNodeDepartmentName() + "*";
                  }
                }
                else {
                  if (produceRoute.length() > 0 && !produceRoute.endsWith("$$$")) {
                    produceRoute += "-" + node.getNodeDepartmentName();
                  }
                  else {
                    produceRoute += node.getNodeDepartmentName();
                  }

                }
              }
            }
            if (asseNode != null) {
              setupRoute += asseNode.getNodeDepartmentName();
            }
            tempRoute += tempRoute1;
          }
          i++;
        }
//        System.out.println("tempRoute " + tempRoute);
//        System.out.println("produceRoute " + produceRoute);
        //   System.out.println("setupRoute " + setupRoute);
        String[] hehe = {
            tempRoute, produceRoute, setupRoute};
        return hehe;
      }

      private String[] getBrokenString(String hehe) {
        StringTokenizer a = new StringTokenizer(hehe, "$$$");
        String[] fff = new String[a.countTokens()];
        int i = 0;
        while (a.hasMoreTokens()) {
          String aa = a.nextToken();
          fff[i] = aa;
          i++;
        }
        return fff;
      }
      
      private ArrayList getRouteInformation(String[] produceRoute1,
              String[] setupRoute1,
              String[] temRoute1) {
		ArrayList hehe = new ArrayList();
		for (int i = 0; i < temRoute1.length; i++) {
			String temp = temRoute1[i];
			String produceRoute = "";
			String setupRoute = "";
			if (i < produceRoute1.length) {
				produceRoute = produceRoute1[i];
			}
			if (i < setupRoute1.length) {
				setupRoute = setupRoute1[i];
			}
			String[] aa = { produceRoute, setupRoute, temp };
			hehe.add(aa);
		}
		return hehe;
  }
      
      //�����乫˾ �ձ�֪ͨ�����ӷ���
      /**
       * ��ȡ�ձ�֪ͨ���·�����ƺ�˵����Ϣ
       * @author ������
       * @version 1.0
       */
      public Collection getCompleteLinkedRouteListNameAndDescr(String completeID) {
        Collection hehe = new ArrayList();
        Collection coll = getCompleteLinkedListByBsoID(completeID);
        String nameAndDecs = "";
        for (Iterator iter1 = coll.iterator(); iter1.hasNext(); ) {
          TechnicsRouteListInfo routeList = (TechnicsRouteListInfo) iter1.next();
          String name = routeList.getRouteListName();
          String desc = routeList.getRouteListDescription();
          if (desc == null || desc.length() < 1) {
            desc = "";
          }
          nameAndDecs = "��׼֪ͨ�� " + name + "˵����" + desc;
          hehe.add(nameAndDecs);
        }
        return hehe;
      }
      /** 20070108 liuming add
       * ���ָ�����������·�ߺ�װ��·��
       * ע�⣺��Ϊ�߼��ܳ�Ҳ�п��ܱ���·�ߣ����Բ��ٿ����߼��ܳɵ����⣬������ͨ���
       * @param part QMPartIfc
       * @return String[] ��һ��Ԫ��Ϊ����·��,�ڶ���Ԫ��Ϊװ��·��
       * @throws QMException
       */
      public String[] getRouteString(QMPartIfc part) throws QMException {
        PersistService pservice = (PersistService) EJBServiceHelper.
            getPersistService();
        String[] result = new String[2];
        result[0] = "";
        result[1] = "";

        //��ȡ���·�߱����·�߱�Ĺ���
        Collection cols = getRoutesAndLinks(part);

        Iterator i = cols.iterator();
        ListRoutePartLinkInfo info = null;
        TechnicsRouteListInfo routelist = null;

        //��ȡ���µ�·�߱���Ҫ��׼�ĺ�״̬Ϊȡ����
        while (i.hasNext()) {
          Object[] objss = (Object[]) i.next();
          ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo) objss[0];
          TechnicsRouteListInfo routelist1 = (TechnicsRouteListInfo) objss[1];

          if (routelist1.getRouteListState().equals("��׼")) {
            continue;
          }
          if (info1.getRouteID() == null ||
              ( (com.faw_qm.technics.route.model.TechnicsRouteInfo) pservice.
               refreshInfo(info1.getRouteID())).getRouteDescription().substring(3,
              4).equals("Q")) { //���·�߱��Ϊȡ������Ҫ
            continue;
          }

          if (info == null) {
            info = info1;
            routelist = routelist1;
          }
          else if (routelist.getModifyTime().before(routelist1.getModifyTime())) {
            info = info1;
            routelist = routelist1;
          }
        } //

        //����ҵ������µ�·�߱�
        if (info != null) {
          String routeText = ""; //����·�ߣ���ͬ��·�ߴ�֮���á�/���ָ���ͬһ·�ߴ�������ڵ�֮���á�--���ָ�
          ArrayList routeList = new ArrayList();
          String assRouteText = ""; //װ��·��
          HashMap map = (HashMap) getRouteBranchs(info.getRouteID());
          //һ���㲿�������ж��·�ߴ���һ��·�ߴ����ֿ����ж������ڵ��һ��װ��ڵ㡣������Ƕ�·�ߴ����еı���
          Iterator values = map.values().iterator();
          while (values.hasNext()) {
            boolean isTemp = false;
            String makeStr = "";
            Object[] objs1 = (Object[]) values.next();
            Vector makeNodes = (Vector) objs1[0]; //����ڵ�
            RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //װ��ڵ�
            if (asseNode != null && asseNode.getIsTempRoute()) {
              isTemp = true;
            }
            if (makeNodes != null && makeNodes.size() > 0) {
              for (int m = 0; m < makeNodes.size(); m++) {
                RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
                if (node.getIsTempRoute()) {
                  isTemp = true;
                }
                if (makeStr == "") {
                  makeStr = makeStr + node.getNodeDepartmentName();
                }
                else {
                  makeStr = makeStr + "--" + node.getNodeDepartmentName();
                }
              }
            }

            if (isTemp) {
              makeStr = makeStr + "(��ʱ)";
            }
            if (makeStr == null || makeStr.equals("")) {
              makeStr = "";
            }
            if (!routeList.contains(makeStr)) {
              routeList.add(makeStr);
              if (!routeText.trim().equals("")) {
                if (routeText.endsWith("/")) {
                  routeText = routeText + makeStr;
                }
                else {
                  routeText = routeText + "/" + makeStr;
                }
              }
              else {
                routeText = routeText + makeStr;
              }
            }
            //yanqi-20061027
            if (asseNode != null) {
              if (!assRouteText.trim().equals("")) {
                assRouteText = assRouteText + "/" +
                    asseNode.getNodeDepartmentName();
              }
              else {
                assRouteText = assRouteText +
                    asseNode.getNodeDepartmentName();
              }
            } //
          }

          routeText = routeText.endsWith("/") ?
              routeText.substring(0, routeText.length() - 1) :
              routeText;
          assRouteText = assRouteText.endsWith("/") ?
              assRouteText.substring(0, assRouteText.length() - 1) :
              assRouteText;

          result[0] = routeText;
          result[1] = assRouteText;
        }

        return result;
      }
      public void setRouteListPreparePartsState1(BaseValueIfc primaryBusinessObject) {
    	    //CCBegin by wanghonglian 2008-08-12
    	    SessionService sService = null;
    	    BSXUtil aa = new BSXUtil();
    	    try {
    	    	sService = (SessionService) EJBServiceHelper.getService(
    	          "SessionService");
    	      //CCBegin by liunan 2011-12-15 �������״̬������branchid����㲿����״̬��
    	      VersionControlService vcservice = (VersionControlService)
    	           EJBServiceHelper.getService
    	           ("VersionControlService");
    	      //CCEnd by liunan 2011-12-15
    	      LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.
    	          getService("LifeCycleService");
    	      TechnicsRouteListIfc myRouteList = (TechnicsRouteListIfc)
    	          primaryBusinessObject;
    	      
    	      //CCBegin by liunan 2011-12-15
    	      PersistService pService = (PersistService) EJBServiceHelper
    	        .getService("PersistService");
    	      //CCEnd by liunan 2011-12-15
    	      
    	      Collection partsColl = (Collection) getRouteListLinkParts(myRouteList);
    	      if (partsColl != null) {
    	        if (myRouteList.getRouteListState() != null &&
    	            myRouteList.getRouteListState().equals("����׼")) {
    	          for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
    	            ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
    	                iter.next();
    	            //CCBegin by liunan 2011-12-15 �������״̬������branchid����㲿����״̬��
    	            /*String partMasterID = routePartLink.getRightBsoID();
    	            QMPartInfo partInfo = (QMPartInfo) getLastedPartByConfig(
    	                partMasterID, getCurrentUserConfigSpec());*/
    	            QMPartInfo partInfo = null;
    	            
                  //CCBegin SS30
    	            /*if(routePartLink.getPartBranchID()==null)
    	            {
    	            	String partMasterID = routePartLink.getRightBsoID();
    	            	partInfo = (QMPartInfo) getLastedPartByConfig(partMasterID, getCurrentUserConfigSpec());
    	            }
    	            else
    	            {
    	            	partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
    	            }*/
                  String rightid = routePartLink.getRightBsoID();
                  if(rightid.indexOf("QMPartMaster_")!=-1)
    	            {
    	            	String partMasterID = routePartLink.getRightBsoID();
    	            	partInfo = (QMPartInfo) getLastedPartByConfig(partMasterID, getCurrentUserConfigSpec());
    	            }
                  else if(rightid.indexOf("QMPart_")!=-1)
                  {
                  	//CCBegin SS31
                  	if(routePartLink.getPartBranchID()==null)
                  	{
                  		partInfo = (QMPartInfo) pService.refreshInfo(rightid);
                  	}
                  	else
                  	//CCEnd SS31
              	    partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
                  }
                  //CCEnd SS30
    	            //CCEnd by liunan 2011-12-15
    	            sService.setAdministrator();
    	            LifeCycleState partstate = partInfo.getLifeCycleState();
//    	            System.out.println("partstate.toString()==================" +
//    	                               partstate.toString());
    	            String partstatez = "";
    	            if (partstate.toString().equals("RELEASED")) {
    	              partstatez = "�ѷ���";
    	            }
    	            if (partstate.toString().equals("BSXTrialProduce")) {
    	              partstatez = "����";
    	            }
    	            if (partstate.toString().equals("BSXTrialYield")) {
    	              partstatez = "����׼��";
    	            }
    	            if (partstate.toString().equals("BSXYield")) {
    	              partstatez = "����";
    	            }
    	            if (partstate.toString().equals("BSXDisused")) {
    	                partstatez = "����";
    	              }
    	          System.out.println(
    	                "partstate.toString()000000000000000=33333333333333344444=================" +
    	                partstatez);
    	            aa.saveAttrForCadFromPart(partInfo, "lifeCycleState", partstatez);

    	            //CCEnd by wanghonglian 2008-08-21
    	          }
    	        }
    	      //Begin by chudaming 2009-6-25
    	        else if(myRouteList.getRouteListState() != null &&
    	                myRouteList.getRouteListState().equals("����")){                	
    	        	for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
    	                ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
    	                    iter.next();
    	                
    	                //CCBegin by liunan 2011-12-15 �������״̬������branchid����㲿����״̬��
    	                /*String partMasterID = routePartLink.getRightBsoID();
    	                QMPartInfo partInfo = (QMPartInfo) getLastedPartByConfig(
    	                    partMasterID, getCurrentUserConfigSpec());*/
    	                QMPartInfo partInfo = null;
                  //CCBegin SS30
    	            /*if(routePartLink.getPartBranchID()==null)
    	            {
    	            	String partMasterID = routePartLink.getRightBsoID();
    	            	partInfo = (QMPartInfo) getLastedPartByConfig(partMasterID, getCurrentUserConfigSpec());
    	            }
    	            else
    	            {
    	            	partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
    	            }*/
                  String rightid = routePartLink.getRightBsoID();
                  if(rightid.indexOf("QMPartMaster_")!=-1)
    	            {
    	            	String partMasterID = routePartLink.getRightBsoID();
    	            	partInfo = (QMPartInfo) getLastedPartByConfig(partMasterID, getCurrentUserConfigSpec());
    	            }
                  else if(rightid.indexOf("QMPart_")!=-1)
                  {
                  	//CCBegin SS31
                  	if(routePartLink.getPartBranchID()==null)
                  	{
                  		partInfo = (QMPartInfo) pService.refreshInfo(rightid);
                  	}
                  	else
                  	//CCEnd SS31
              	    partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
                  }
                  //CCEnd SS30
    	                //CCEnd by liunan 2011-12-15
    	                
    	                sService.setAdministrator();
    	                LifeCycleState partstate = partInfo.getLifeCycleState();
    	                            String partstatez = "";
    	                            if (partstate.toString().equals("RELEASED")) {
    	                              partstatez = "�ѷ���";
    	                            }
    	                            if (partstate.toString().equals("BSXTrialProduce")) {
    	                              partstatez = "����";
    	                            }
    	                            if (partstate.toString().equals("BSXTrialYield")) {
    	                              partstatez = "����׼��";
    	                            }
    	                            if (partstate.toString().equals("BSXYield")) {
    	                              partstatez = "����";
    	                            }
    	                            if (partstate.toString().equals("BSXDisused")) {
    	                                partstatez = "����";
    	                              }
    	               System.out.println(
    	                    "partstate.toString()000000000000000==================" +
    	                   partstatez);
    	                            aa.saveAttrForCadFromPart(partInfo, "lifeCycleState", partstatez);
    	              }
    	        }
    	      //End by chudaming 2009-6-25
    	        else {        	
    	          for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
    	            ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
    	                iter.next();
    	            
    	            //CCBegin by liunan 2011-12-15 �������״̬������branchid����㲿����״̬��
    	            /*String partMasterID = routePartLink.getRightBsoID();
    	            QMPartInfo partInfo = (QMPartInfo) getLastedPartByConfig(
    	                partMasterID, getCurrentUserConfigSpec());*/
    	            QMPartInfo partInfo = null;
                  //CCBegin SS30
    	            /*if(routePartLink.getPartBranchID()==null)
    	            {
    	            	String partMasterID = routePartLink.getRightBsoID();
    	            	partInfo = (QMPartInfo) getLastedPartByConfig(partMasterID, getCurrentUserConfigSpec());
    	            }
    	            else
    	            {
    	            	partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
    	            }*/
                  String rightid = routePartLink.getRightBsoID();
                  if(rightid.indexOf("QMPartMaster_")!=-1)
    	            {
    	            	String partMasterID = routePartLink.getRightBsoID();
    	            	partInfo = (QMPartInfo) getLastedPartByConfig(partMasterID, getCurrentUserConfigSpec());
    	            }
                  else if(rightid.indexOf("QMPart_")!=-1)
                  {
                  	//CCBegin SS31
                  	if(routePartLink.getPartBranchID()==null)
                  	{
                  		partInfo = (QMPartInfo) pService.refreshInfo(rightid);
                  	}
                  	else
                  	//CCEnd SS31
              	    partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
                  }
                  //CCEnd SS30
    	            //CCEnd by liunan 2011-12-15
    	            
    	            sService.setAdministrator();
    	            LifeCycleState partstate = partInfo.getLifeCycleState();
    	                        String partstatez = "";
    	                        if (partstate.toString().equals("RELEASED")) {
    	                          partstatez = "�ѷ���";
    	                        }
    	                        if (partstate.toString().equals("BSXTrialProduce")) {
    	                        	
    	                          partstatez = "����";
    	                        }
    	                        if (partstate.toString().equals("BSXTrialYield")) {
    	                          partstatez = "����׼��";
    	                        }
    	                        if (partstate.toString().equals("BSXYield")) {
    	                          partstatez = "����";
    	                        }
    	                        if (partstate.toString().equals("BSXDisused")) {
    	                            partstatez = "����";
    	                          }
    	           System.out.println(
    	                "partstate.toString()000000000000000==================" +
    	               partstatez);
    	                        aa.saveAttrForCadFromPart(partInfo, "lifeCycleState", partstatez);

    	            //CCEnd by wanghonglian 2008-08-21
    	          }

    	        }
    	      }

    	    }
    	    catch (QMException ex) {
    	      ex.printStackTrace();
    	    }
    	    catch (Exception ex1) {
    	      ex1.printStackTrace();
    	    }
    	    finally {
    	      try {
    	        sService.freeAdministrator();
    	      }
    	      catch (QMException e) {
    	        e.printStackTrace();
    	      }
    	    }
    	    //CCEnd by wanghonglian 2008-08-12
    	  }
      
      //CCBegin SS3
      public String getIBA(QMPartIfc part) throws QMException {
    	  IBAValueService ibaService = null;
    	  ibaService = (IBAValueService) PublishHelper
    			  .getEJBService("IBAValueService");
    	  part = (QMPartInfo) ibaService
    			  .refreshAttributeContainerWithoutConstraints(part);
    	  DefaultAttributeContainer attc = (DefaultAttributeContainer) part
    			  .getAttributeContainer();
    	  AbstractValueView[] valueview = attc.getAttributeValues();
    	  int m = valueview.length;
    	  String version = "";
    	  for (int ii = 0; ii < m; ii++) {
    		  if ((valueview[ii].getDefinition().getName())
    				  .toLowerCase().trim().equals("sourceversion")) {
    			  AbstractValueIfc value = IBAValueObjectsFactory.newAttributeValue(
    					  valueview[ii], part);
    			  version = ((StringValueIfc) value).getValue().toString();
    		  }
    		  else if((valueview[ii].getDefinition().getName())
    				  .toLowerCase().trim().equals("partversion")) {
    			  AbstractValueIfc value = IBAValueObjectsFactory.newAttributeValue(
    					  valueview[ii], part);
    			  version = ((StringValueIfc) value).getValue().toString();
    		  }
    	  }
    	  return version;
      }
      //CCEnd SS3
      
    //CCBegin SS4
      public Vector findQMPart(Vector vec) throws QMException {
    	  PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    	  String [] s = new String[vec.size()];
    	  for (int i = 0; i < vec.size(); i++) {
    		  QMPartMasterInfo orderlist = null;
    		  //System.out.println("class========"+vec.elementAt(i).getClass());
    		  //System.out.println("vec========"+vec.elementAt(i));
    		  orderlist = (QMPartMasterInfo) vec.elementAt(i);
    		  //System.out.println("bsoid========"+orderlist.getBsoID());
    		  s[i] = orderlist.getBsoID();
    	  }
    	  QMQuery query = new QMQuery("QMPart");
    	  query.setChildQuery(false);
    	  QueryCondition cond = new QueryCondition("masterBsoID", QueryCondition.IN,s);
    	  query.addCondition(cond);
//    	  query.addAND();
//    	  QueryCondition cond1 = new QueryCondition("iterationIfLatest", QueryCondition.EQUAL, 1);
//          query.addCondition(cond1);
          //System.out.println("������SQL��䣺" + query.getDebugSQL());
          Vector result1 = (Vector)pservice.findValueInfo(query, false);
    	  return result1;
      }
      //CCEnd SS4
      
      // CCBegin SS5
      /**
       * ����㲿���ķ���Դ�汾
       * @param part
       * @return
       */
      public String getSourceVersion(QMPartInfo part){
    	  String version = "";
    	  IBAValueService ibaService = null;
    	  try {
    	  	//CCBegin SS53
    	  	String version1 = "";
    	  	String version2 = "";
    	  	//CCEnd SS53
    		  ibaService = (IBAValueService) PublishHelper
    		  .getEJBService("IBAValueService");

    		  part = (QMPartInfo) ibaService
    		  .refreshAttributeContainerWithoutConstraints(part);
    		  DefaultAttributeContainer attc = (DefaultAttributeContainer) part
    		  .getAttributeContainer();
    		  AbstractValueView[] valueview = attc.getAttributeValues();
    		  int m = valueview.length;
    		  for (int ii = 0; ii < m; ii++) {
    			  if ((valueview[ii].getDefinition().getName())
    					  .toLowerCase().trim().equals("sourceversion")) {
    				  AbstractValueIfc value = IBAValueObjectsFactory.newAttributeValue(
    						  valueview[ii], part);
    				  //CCBegin SS53
    				  //version = ((StringValueIfc) value).getValue().toString();
    				  version1 = ((StringValueIfc) value).getValue().toString();
    				  //CCEnd SS53
    			  }
    			  //CCBegin SS53
    			  //else if((valueview[ii].getDefinition().getName())
    			  if((valueview[ii].getDefinition().getName())
    			  //CCEnd SS53
    					  .toLowerCase().trim().equals("partversion")) {
    				  AbstractValueIfc value = IBAValueObjectsFactory.newAttributeValue(
    						  valueview[ii], part);
    				  
    				  //CCBegin SS53
    				  //version = ((StringValueIfc) value).getValue().toString();
    				  version2 = ((StringValueIfc) value).getValue().toString();
    				  //CCEnd SS53
    			  }
    		  }
    		  //CCBegin SS53
    		  if(!version1.equals(""))
    		  {
    		  	version = version1;
    		  }
    		  else if(!version2.equals(""))
    		  {
    		  	version = version2;
    		  }
    		  else
    		  {
    		  	version = "";
    		  }
    		  //CCEnd SS53
    	  }
			catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  return version;
      }
    //CCEnd SS5
      
    //CCBegin SS6
      
      /**
       * @param param ��ά���飬5��Ԫ�ء�
       * @J2EE_METHOD -- findChangeOrders ��ñ��֪ͨ�顣������Χ����š����ơ������ߡ�����ʱ�䡣
       * @return collection ���֪ͨ��ֵ���󼯺�
       */
      public Collection getJFRouteList(Object[][] param)
      {
          Collection result = null;
          try
          {
              PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
              QMQuery query = new QMQuery("TechnicsRouteList");
              query.setChildQuery(false);
              String number = (String)param[0][0];
              boolean numberFlag = ((Boolean)param[0][1]).booleanValue();
              if(number != null && number.trim().length() != 0)
              {
                  QueryCondition cond = RouteHelper.handleWildcard("routeListNumber", number, numberFlag);
                  query.addCondition(cond);
              }

              String name = (String)param[1][0];
              boolean nameFlag = ((Boolean)param[1][1]).booleanValue();
              if(name != null && name.trim().length() != 0)
              {
                  QueryCondition cond1 = RouteHelper.handleWildcard("routeListName", name, nameFlag);
                  query.addAND();
                  query.addCondition(cond1);

              }
              String beginTime = (String)param[3][0];
              String endTime = (String)param[4][0];
              Timestamp beginTimestamp = null;
              Timestamp endTimestamp = null;

              if(beginTime != null && beginTime.trim().length() > 0)
              {
                  beginTime = beginTime + " 00:00:00";
                  beginTimestamp = new Timestamp(new Date(beginTime).getTime());
                  String beginTimestamp1 = beginTimestamp.toString();
                  // tang 20070504
                  QueryCondition beginTimecondition = new QueryCondition("createTime", ">=", beginTimestamp);
                  query.addAND();
                  query.addCondition(beginTimecondition);
              }
              if(endTime != null && endTime.trim().length() > 0)
              {
                  endTime = endTime + " 23:59:59";
                  endTimestamp = new Timestamp(new Date(endTime).getTime());
                  String endTimestamp1 = endTimestamp.toString();
                  // tang 20070504
                  QueryCondition endTimecondition = new QueryCondition("createTime", "<=", endTimestamp);
                  query.addAND();
                  query.addCondition(endTimecondition);
              }
              //CCBegin SS10
              String updatbeginTime = (String)param[5][0];
              String updateendTime = (String)param[6][0];
              Timestamp updatebeginTimestamp = null;
              Timestamp updateendTimestamp = null;

              if(updatbeginTime != null && updatbeginTime.trim().length() > 0)
              {
            	  updatbeginTime = updatbeginTime + " 00:00:00";
            	  updatebeginTimestamp = new Timestamp(new Date(updatbeginTime).getTime());
                  String updatebeginTimestamp1 = updatebeginTimestamp.toString();
                  QueryCondition beginTimecondition = new QueryCondition("modifyTime", ">=", updatebeginTimestamp);
                  query.addAND();
                  query.addCondition(beginTimecondition);
              }
              if(updateendTime != null && updateendTime.trim().length() > 0)
              {
            	  updateendTime = updateendTime + " 23:59:59";
                  updateendTimestamp = new Timestamp(new Date(updateendTime).getTime());
                  String updateendTimestamp1 = updateendTimestamp.toString();
                  QueryCondition endTimecondition = new QueryCondition("modifyTime", "<=", updateendTimestamp);
                  query.addAND();
                  query.addCondition(endTimecondition);
              }
              //CCEnd SS10
              if(VERBOSE)
              {
                  System.out.println("query :" + query.getDebugSQL());
              }
              
              //CCBegin SS15
              boolean ignore = ((Boolean)param[2][0]).booleanValue();
              query.setIgnoreCase(ignore);
              //CCEnd SS15
              //���ѯ�����������°汾����SQL����
              DocLastConfigSpec config = new DocLastConfigSpec();
              config.appendSearchCriteria(query);
              result = pservice.findValueInfo(query, false);
          }catch(Exception e)
          {
              e.printStackTrace();
          }
          return result;
      }
     //CCEnd SS6
     
     //CCBegin SS7
      /**
       * ������׼֪ͨ���BsoID�������������㲿������
       * @param bsoid String
       * @return Vector
       */
      public Vector findQMPartByJFRouteList(Vector routeListVec, String routestr)
      {
          Vector vec = new Vector();
          Vector masterInfoVec=new Vector();
          try
          {
              PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
              VersionControlService versionservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
              Iterator iter = routeListVec.iterator();
              while(iter.hasNext())
              {
              	com.faw_qm.technics.route.model.TechnicsRouteListInfo info = (com.faw_qm.technics.route.model.TechnicsRouteListInfo)iter.next();
                  String bsoid = info.getBsoID();                
                  
                  QMQuery query = new QMQuery("ListRoutePartLink", "TechnicsRouteBranch");
                  QueryCondition cond1 = new QueryCondition("leftBsoID", "=", bsoid);
                  query.addCondition(0, cond1);
                  query.addAND();
                  QueryCondition cond2 = new QueryCondition("routeID", "routeID");
                  query.addCondition(0, 1, cond2);
                  query.addAND();
                  QueryCondition cond3 = new QueryCondition("routeStr",QueryCondition.LIKE,"%"+routestr+"%");
                  query.addCondition(1, cond3);
                  query.setDisticnt(true);
                  query.setVisiableResult(1);
                  
                  //System.out.println("������SQL��䣺" + query.getDebugSQL());
                  
                  Collection col = ps.findValueInfo(query, false);
                  Iterator ite = col.iterator();
                  while(ite.hasNext())
                  {
                  	  com.faw_qm.technics.route.model.ListRoutePartLinkInfo link = (com.faw_qm.technics.route.model.ListRoutePartLinkInfo)ite.next();
                      //CCBegin SS51
              //    	String partmasterid = link.getRightBsoID();
//                      QMPartMasterIfc partM=(QMPartMasterIfc)ps.refreshInfo(partmasterid);
//                      masterInfoVec.add(partM);
	                  	QMPartIfc part=link.getPartBranchInfo();
	                  	vec.add(part);
                  }
              }
              
//              HashMap partMap=getLatestsVersion1(masterInfoVec);
//              for(Iterator it=partMap.values().iterator();it.hasNext();)
//              {
//              	QMPartIfc part=(QMPartIfc)it.next();
//              	vec.add(part);
//              }
             // CCEnd SS51
              
          }catch(Exception e)
          {
              e.printStackTrace();
          }
          return vec;
      }
      //CCEnd SS7
      
      //CCBegin SS14    
    /**
     * �������ID�͹���·�߼����ö�Ӧ�Ĺ���·�ߡ�
     * @param partMasterID String
     * @param level_zh String ·�߼��� @
     * @return Collection ���鼯�ϡ�obj[0]: TechnicsRouteListIfc, <br> obj[1],obj[2]��getRouteAndBrach(routeID),<br> obj[3]:linkInfo��
     */
    public Collection getPartLevelRoutes(String partMasterID, String partID, String level_zh)throws QMException
    {
        if(partMasterID == null || partMasterID.trim().length() == 0 || level_zh == null || level_zh.trim().length() == 0)
        {
            throw new QMException("�����������");
        }
        System.out.println("level_zh==="+level_zh);
        //CCBegin SS55
        String comp=RouteClientUtil.getUserFromCompany();
        //CCEnd SS55
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //CCBegin SS29
        VersionControlService verservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
        QMPartMasterIfc master = (QMPartMasterIfc)pservice.refreshInfo(partMasterID);
//        CCBegin SS56
        QMPartInfo partInfo = (QMPartInfo)pservice.refreshInfo(partID);
//        CCEnd SS56
        //CCBegin SS33
        //Collection verCol= verservice.allVersionsOf(master);
        Collection verCol= verservice.allIterationsOf(master);
        //CCEnd SS33
        //CCEnd SS29
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int i = query.appendBso(ROUTELIST_BSONAME, false);
        int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
        /*
         * String level = null; if(departmentID != null && departmentID.trim().length() != 0) { level = RouteListLevelType.SENCONDROUTE.toString(); QueryCondition cond = new
         * QueryCondition("routeListDepartment", QueryCondition.EQUAL, departmentID); query.addCondition(i, cond); query.addAND(); } else { level = RouteListLevelType.FIRSTROUTE.toString(); }
         */
        String level = RouteHelper.getValue(RouteListLevelType.getRouteListLevelTypeSet(), level_zh);
        QueryCondition cond1 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, level);
        query.addCondition(i, cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition(LEFTID, "bsoID");
        query.addCondition(0, i, cond2);
        query.addAND();
        
        //CCBegin SS14
        //CCBegin SS55
        if(comp.equals("cd"))
        {
        	 query.addCondition(0, new QueryCondition(RIGHTID, QueryCondition.EQUAL, partID));
        }
        else
        {
        //CCEnd SS55
        query.addLeftParentheses();
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(0, cond3);
        query.addOR();
        QueryCondition cond33 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partID);
        query.addCondition(0, cond33);
        //CCBegin SS29
        Iterator it=verCol.iterator();
        while(it.hasNext())
        {
        	QMPartIfc part=(QMPartIfc)it.next();
        	query.addOR();
        	QueryCondition cond333 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, part.getBsoID());
        	query.addCondition(0, cond333);
        }
        //CCEnd SS29
        query.addRightParentheses();
        //CCBegin 
        }
        //CCEnd SS55
        //CCEnd SS14
        
        //CCBegin SS39
        //query.addAND();
        //QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        //query.addCondition(0, cond4);
        //CCEnd SS39

        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(0, cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
        query.addCondition(0, j, cond6);
        query.addAND();
        //zz added start (������)
        QueryCondition condx = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(0, condx);
        //zz added end (������)

        //·���޸�ʱ�併�����С�
        //query.addOrderBy(j, "modifyTime", true);
        //SQL������������
        query.setDisticnt(true);
        //����ListRoutePartLinkIfc
        Collection coll = pservice.findValueInfo(query);
        System.out.println("anan coll.size="+coll.size());
        //·���޸�ʱ�併�����С�
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
        Collection result = new Vector();
        for(Iterator iter = sortedVec.iterator();iter.hasNext();)
        {
            Object[] obj = new Object[4];
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            String routeListID = linkInfo.getRouteListID();
            String routeID = linkInfo.getRouteID();
           // System.out.println("anan routeListID="+routeListID());
            //CCBegin SS52
            TechnicsRouteListIfc listInfo = null;
            try
            {
            	listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
            }
            catch(QMException e)
            {
            	e.printStackTrace();
            }
            if(listInfo==null)
            {
            	continue;
            }
            //CCEnd SS52
            //CCBegin SS44
            //CCBegin SS55
            //String comp=RouteClientUtil.getUserFromCompany();
            //CCEnd SS55
            String lifeCycleTemplate = listInfo.getLifeCycleTemplate();
            if(lifeCycleTemplate.contains("BSXUP") && comp.equals("zczx")){
            	continue;
            }
           //CCEnd SS44
            
            obj[0] = listInfo;
            Object[] route = getRouteAndBrach(routeID);
            obj[1] = route[0];
            obj[2] = route[1];
            obj[3] = linkInfo;
            result.add(obj);
        }
        System.out.println("anan result.size="+result.size());
        return result;
    }
    //CCEnd SS14
//    CCBegin SS24
    public Collection selectPartRoute (String bsoid) throws QMException{
    	PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    	QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    	QueryCondition cond = new QueryCondition(RIGHTID, QueryCondition.EQUAL, bsoid);
    	query.addCondition(cond);
    	query.addAND();
    	QueryCondition cond1 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
    	query.addCondition(cond1);
    	Collection coll = pservice.findValueInfo(query);
    	return coll;
    }
//  CCEnd SS24
  //CCBegin SS26
    public Collection CTfindPartByRoute(Object[][] param) throws QMException {
		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();
		QMQuery query = new QMQuery(ROUTELIST_BSONAME);
		int i = query.appendBso(ROUTELISTMASTER_BSONAME, false);
		query.addCondition(0, i, new QueryCondition("masterBsoID", "bsoID"));
		query.addAND();
		boolean ignore = ((Boolean) param[5][0]).booleanValue();
		query.setIgnoreCase(ignore);
		int n = query.appendBso(PARTMASTER_BSONAME, false);
		query.setChildQuery(false);
		String number = (String) param[0][0];
		boolean numberFlag = ((Boolean) param[0][1]).booleanValue();
		if (number != null && number.trim().length() != 0) {
			QueryCondition cond = RouteHelper.handleWildcard("routeListNumber",
					number, numberFlag);
			query.addCondition(i, cond);
			query.addAND();
		}
		String name = (String) param[1][0];
		boolean nameFlag = ((Boolean) param[1][1]).booleanValue();
		if (name != null && name.trim().length() != 0) {
			QueryCondition cond1 = RouteHelper.handleWildcard("routeListName",
					name, nameFlag);
			query.addCondition(i, cond1);
			query.addAND();
		}
		String level_zh = (String) param[2][0];
		if (level_zh != null && level_zh.trim().length() != 0) {
			String level = RouteHelper.getValue(RouteListLevelType
					.getRouteListLevelTypeSet(), level_zh);
			QueryCondition cond4 = new QueryCondition("routeListLevel",
					QueryCondition.EQUAL, level);
			query.addCondition(cond4);
			query.addAND();
		}
		String productNumber = (String) param[3][0];
		boolean productNumberFlag = ((Boolean) param[3][1]).booleanValue();
		if (productNumber != null && productNumber.trim().length() != 0) {
			//�����Ʒ���������󣬽�����ʾ���Ƿ���Ҫ��
			hasPartNumber(productNumber);
			QueryCondition cond5 = RouteHelper.handleWildcard("partNumber",
					productNumber, productNumberFlag);
			query.addCondition(n, cond5);
			query.addAND();
			QueryCondition cond100 = new QueryCondition("productMasterID",
					"bsoID");
			query.addCondition(0, n, cond100);
			query.addAND();
		}
		String version = (String) param[4][0];
		boolean versionFlag = ((Boolean) param[4][1]).booleanValue();
		//����汾ǡ��Ϊ���°棬�����ѳ��������ϼеĶ�����
		if (version != null && version.trim().length() != 0) {
			QueryCondition cond6 = RouteHelper.handleWildcard("versionID",
					version, versionFlag);
			query.addCondition(0, cond6);
			query.addAND();
		}
		QueryCondition cond12 = new QueryCondition("iterationIfLatest",
				Boolean.TRUE);
		query.addCondition(cond12);
	    query.addAND();
	    QueryCondition cond13 = new QueryCondition("location",QueryCondition.EQUAL,"\\Root\\��ų��طֹ�˾\\һ��·��");
	    query.addCondition(cond13);
	    query.setDisticnt(true);
		Collection result = pservice.findValueInfo(query);
		//���ݹ���״̬���й��ˡ�ԭ����������ͬʱ���ڡ���ù���·�߱�ֵ���󼯺ϡ�
		filterByWorkState(result);
		//����·�߱����������С�
		Collection sortedVec = RouteHelper.sortedInfos(result,
				"getRouteListNumber", false);
		return sortedVec;
	}
    /**
     * ������׼֪ͨ���BsoID�������������㲿������
     * @param bsoid String
     * @return Vector
     */
    public Vector findQMPartByCTRouteList(Vector routeListVec)
    {
        Vector vec = new Vector();
        try
        {
            PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
            VersionControlService versionservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
            Iterator iter = routeListVec.iterator();
            while(iter.hasNext())
            {
            	com.faw_qm.technics.consroute.model.TechnicsRouteListInfo info = (com.faw_qm.technics.consroute.model.TechnicsRouteListInfo)iter.next();
                String bsoid = info.getBsoID();                
               // QMQuery query = new QMQuery("consListRoutePartLink", "consTechnicsRouteBranch");
                QMQuery query = new QMQuery("consListRoutePartLink");
                QueryCondition cond1 = new QueryCondition("leftBsoID", "=", bsoid);
                query.addCondition(0, cond1);
//                query.addAND();
//                QueryCondition cond2 = new QueryCondition("routeID", "routeID");
//                query.addCondition(0, 1, cond2);
//                query.setVisiableResult(1);    
                Collection col = ps.findValueInfo(query, false);
                Iterator ite = col.iterator();
                while(ite.hasNext())
                {
                	com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo link = (com.faw_qm.technics.consroute.model.ListRoutePartLinkInfo)ite.next();
                    String partid = link.getRightBsoID();
                    QMPartIfc partM=(QMPartIfc)ps.refreshInfo(partid);
                    vec.add(partM);
                }
            }       
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return vec;
    }
    /**
     * ͨ���㲿��ID����һ��·��
     * @param partID
     * @return
     * @throws QMException
     */
    public String[] findQMPartRouteLevelOne(String partID)throws QMException{
    	 String[] route =new String[2];
    	 PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
         //���·�߱�����·�ߵĹ���
         QMQuery query = new QMQuery("consListRoutePartLink");
         int k = query.appendBso("consTechnicsRouteList",false);
         //part��master��id
         QueryCondition cond3 = new QueryCondition("rightBsoID", QueryCondition.EQUAL, partID);
         query.addCondition(0, cond3);
         query.addAND();
         QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, 1);
         query.addCondition(0, cond4);
         query.addAND();
         //·�߲�Ϊ��
         QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
         query.addCondition(0, cond5);
         query.addAND();
         QueryCondition cond7 = new QueryCondition("leftBsoID", "bsoID");
         query.addCondition(0, k, cond7);
         query.addAND();
         QueryCondition cond8 = new QueryCondition("lifeCycleState",QueryCondition.EQUAL, "released");
         query.addCondition(k, cond8);
         query.addAND();
         QueryCondition cond9 = new QueryCondition("routeListLevel",QueryCondition.EQUAL, RouteListLevelType.FIRSTROUTE.getDisplay());
         query.addCondition(k, cond9);
         query.addOrderBy(k,"createTime");

         if(VERBOSE)
         {
             System.out.println( "routeService's getPartLevelRoutes SQL = " + query.getDebugSQL());
         }
         
         Collection collection = pservice.findValueInfo(query, false);
         Iterator ites = collection.iterator();
         Collection result=new Vector();
         String makeStr="";
         String assemStr="";
         if(ites.hasNext()){
            ListRoutePartLinkInfo info=(ListRoutePartLinkInfo)ites.next();
            String routeID = info.getRouteID();
            if(info.getMainStr()!=null){
            	String[] s = info.getMainStr().split("=");
            	if(s.length>1){
            		makeStr = s[0];
            		assemStr = s[1];
            	}
            	else{
            		makeStr = s[0];
            		assemStr = "";
            	}
            route[0]=makeStr;
            route[1]=assemStr;
         }
       
     }
         return route;
    }
   //CCEnd SS26
   //CCBegin SS27
    public Vector CTSecondgatherExportData(String routeListID) throws QMException{
    	   Vector vec=new Vector();
    	   vec= ViewRouteListUtil.getSencondRouteReport(routeListID);
    	   return vec;
    }
    public String getSencondRouteReportHead(String routeListID) throws QMException{
    	 return ViewRouteListUtil.getSencondRouteReportHead(routeListID);
    }
    public String getSecondPartProduct(String routeListID)throws QMException{
    	 return ViewRouteListUtil.getSecondPartProduct(routeListID);
    }
    //CCEnd SS27
    //CCBegin SS28
      /**
       * ���ظ���·�߷���֮�������������׼��״̬
       * @param primaryBusinessObject
       * @return
       */
      public void setCTRouteListPreparePartsState(BaseValueIfc primaryBusinessObject) {
  	    SessionService sService = null;
  	    BSXUtil aa = new BSXUtil();
  	    try {
  	    	sService = (SessionService) EJBServiceHelper.getService(
  	          "SessionService");
  	      VersionControlService vcservice = (VersionControlService)
  	           EJBServiceHelper.getService
  	           ("VersionControlService");
  	      LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.
  	          getService("LifeCycleService");
  	      TechnicsRouteListIfc myRouteList = (TechnicsRouteListIfc)
  	          primaryBusinessObject;
  	      
  	      PersistService pService = (PersistService) EJBServiceHelper
  	        .getService("PersistService");
          sService.setAdministrator();  
  	      Collection partsColl = (Collection) getRouteListLinkParts(myRouteList);
  	      if (partsColl != null) {
  	        if (myRouteList.getRouteListState() != null ) {
  	          for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
  	            ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
  	                iter.next();
  	            QMPartInfo partInfo = null;
  	            if(routePartLink.getPartBranchID()==null)
  	            {
  	            	String partID = routePartLink.getRightBsoID();
  	            	partInfo = (QMPartInfo) pService.refreshInfo(partID);
  	            }
  	            else
  	            {
  	            	partInfo = (QMPartInfo) vcservice.getLatestIteration(routePartLink.getPartBranchID());
  	            }
  	               //CCBegin SS32
  	          LifeCycleState partstate = partInfo.getLifeCycleState();
              
              if (!partstate.toString().equals("PREPARING")) {
   	             //·�߷���֮�������������׼��״̬
    	            //ֻ�й���ԭ�������޸�����׼��״̬
    	            if(partInfo.getWorkableState().equals("c/i")){
    	            	aa.setLifeCycleState(partInfo,"PREPARING");
    	            }  
              }
  	            //CCEnd SS32
  	          }
  	        }

  	      }

  	    }
  	    catch (QMException ex) {
  	      ex.printStackTrace();
  	    }
  	    catch (Exception ex1) {
  	      ex1.printStackTrace();
  	    }
  	    finally {
  	      try {
  	        sService.freeAdministrator();
  	      }
  	      catch (QMException e) {
  	        e.printStackTrace();
  	      }
  	    }
  	  }
      //CCEnd SS28


    //CCBegin SS37
    public String[] getLaterRouteByPartID(String id)throws QMException
    {
    	String[] route =new String[]{"",""};
    	PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    	VersionControlService verservice = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
    	if(id == null || id.trim().length() == 0)
    	{
    		throw new QMException("�㲿��idΪ�գ�");
    	}
    	QMPartMasterIfc master = null;
    	QMPartIfc part = null;
    	//CCBegin SS38
    	if(id.startsWith("QMPartMaster_")||id.startsWith("GenericPartMaster_"))
    	//CCEnd SS38
    	{
    		master = (QMPartMasterIfc)pservice.refreshInfo(id);
    	}
    	//CCBegin SS38
    	else if(id.startsWith("QMPart_")||id.startsWith("GenericPart_"))
    	//CCEnd SS38
    	{
    		part = (QMPartIfc)pservice.refreshInfo(id);
    		master = (QMPartMasterIfc)pservice.refreshInfo(part.getMasterBsoID());
      }
      String partID = "";
      String partMasterID = master.getBsoID();
      if(part!=null)
      {
      	partID = part.getBsoID();
      }
      Collection verCol= verservice.allIterationsOf(master);
      QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
      int i = query.appendBso(ROUTELIST_BSONAME, false);
      int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
        QueryCondition cond2 = new QueryCondition(LEFTID, "bsoID");
        query.addCondition(0, i, cond2);
        query.addAND();
        query.addLeftParentheses();
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(0, cond3);
        query.addOR();
        QueryCondition cond33 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partID);
        query.addCondition(0, cond33);
        Iterator it=verCol.iterator();
        while(it.hasNext())
        {
        	QMPartIfc part1=(QMPartIfc)it.next();
        	query.addOR();
        	QueryCondition cond333 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, part1.getBsoID());
        	query.addCondition(0, cond333);
        }
        query.addRightParentheses();
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond4);

        query.addAND();
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
        query.addCondition(0, cond5);
        query.addAND();
        QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
        query.addCondition(0, j, cond6);
        query.addAND();
        QueryCondition condx = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(0, condx);

        //·���޸�ʱ�併�����С�
        query.setDisticnt(true);
        Collection coll = pservice.findValueInfo(query);
        //·���޸�ʱ�併�����С�
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
        Collection result = new Vector();
        Iterator iter = sortedVec.iterator();
        //�ȴӶ���·����ȡ
        while(iter.hasNext())
        {
        	System.out.println("ȡ�Զ���·�ߣ�");
        	ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
        	System.out.println("modifyidentity==="+linkInfo.getModifyIdenty());
        	if(linkInfo.getModifyIdenty()!=null&&linkInfo.getModifyIdenty().equals("ȡ��"))
        	{
        		continue;
        	}
        	if(linkInfo.getMainStr()!=null)
        	{
        		route[0] = linkInfo.getMainStr();
        	}
        	else
        	{
        		route[0] = "";
        	}
        	if(linkInfo.getSecondStr()!=null)
        	{
        		route[1] = linkInfo.getSecondStr();
        	}
        	else
        	{
        		route[1] = "";
        	}
        	break;
        }
        //û�ж���·�ߣ��ʹӽ��һ��·����ȡ��
        if(sortedVec==null||sortedVec.size()==0)
        {
        	System.out.println("ȡ�Խ��һ��·�ߣ�");
        	com.faw_qm.technics.route.ejb.service.TechnicsRouteService trs = (com.faw_qm.technics.route.ejb.service.TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
        	Collection coll1 = trs.getRouteInfomationByPartmaster(partMasterID);
        	if(coll1!=null)
        	{
        		Iterator ite = coll1.iterator();
        		if(ite.hasNext())
        		{
        			String[] str = (String[])ite.next();
        			route[0] = str[0]+"="+str[1];
        			route[1] = "";
        		}
        	}
        }
        return route;
    }
    //CCEnd SS37    

  //CCBegin SS40
	/**
	 * �Զ�����·�߱���
	 * @param routelist
	 * @return ·�߱���
	 * @throws QMException
	 * @author ������ for �Զ����
	 * @throws QMException
	 */
	private String getAutoRouteListNumber(TechnicsRouteListIfc routelist)throws QMException
	{
		String result = "";
		try
		{
			StandardCappService scs = (StandardCappService) EJBServiceHelper.getService("StandardCappService");
			PersistService ps = (PersistService) EJBServiceHelper.getPersistService();
			String className = "cdRouteList";
			String baseKey = "";
			if (routelist.getRouteListState().equalsIgnoreCase("��׼"))
			{
				baseKey = "C׼";
			}
			else if (routelist.getRouteListState().equalsIgnoreCase("��׼"))
			{
				baseKey = "C��";
			}
			else if (routelist.getRouteListState().equalsIgnoreCase("����"))
			{
				baseKey = "C��";
			}
			else if (routelist.getRouteListState().equalsIgnoreCase("�շ�"))
			{
				baseKey = "C��";
			}
			else if (routelist.getRouteListState().equalsIgnoreCase("ǰ׼"))
			{
				baseKey = "Cǰ";
			}
			String product = routelist.getProductMasterID();
			System.out.println("baseKey=="+baseKey);
			QMPartMasterIfc master = (QMPartMasterIfc) ps.refreshInfo(product);
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int month = Calendar.getInstance().get(Calendar.MONTH);

			String yearString = (new Integer(year)).toString();
			String monthString = "";
			if (month < 9) {
				monthString = "0" + (new Integer(month + 1)).toString();
			} else {
				monthString = (new Integer(month + 1)).toString();

			}
			baseKey = baseKey + "-" + master.getPartNumber() + "-" + yearString
					+ "-" + monthString;
			int sortNumber = scs.getNextSortNumber(className, baseKey, false);
			result = baseKey + "-" + (new Integer(sortNumber)).toString();
			System.out.println("result=="+result);
		} catch (QMException e) {
			e.printStackTrace();
			throw new QMException(e);
		}
		return result.toUpperCase();
	}
  //CCEnd SS40
  
  
  //CCBegin SS41
  /**
   * tangshutao add for qingqi 2007.09.17
   * ����·��ID���·�ߴ�
   * @param routeid String
   * @throws QMException
   * @return String
   */
  public String getRouteTextByRouteID(String routeid) throws
    QMException {
  String routeText = "";
  HashMap map = (HashMap) getRouteBranchs(routeid);
  if (map == null || map.size() == 0) {
    return "";
  }
  Iterator values = map.values().iterator();
  while (values.hasNext()) {
    boolean isTemp = false;
    String makeStr = "";
    Object[] objs1 = (Object[]) values.next();
    Vector makeNodes = (Vector) objs1[0]; //����ڵ�
    RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //װ��ڵ�
    if (asseNode != null && asseNode.getIsTempRoute()) {
      isTemp = true;
    }
    if (makeNodes != null && makeNodes.size() > 0) {
      for (int m = 0; m < makeNodes.size(); m++) {
        RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
        if (node.getIsTempRoute()) {
          isTemp = true;
        }
        if (makeStr == "") {
          makeStr = makeStr + node.getNodeDepartmentName();
        }
        else {
          makeStr = makeStr + "--" + node.getNodeDepartmentName();
        }
      }
    }
    if (asseNode != null) {
      makeStr = makeStr + "=" + asseNode.getNodeDepartmentName();
    }
    if (isTemp) {
      makeStr = makeStr + "(��ʱ)";
    }
    if (makeStr == null || makeStr.equals("")) {
      makeStr = "";
    }
    if (routeText.equals("")) {
      routeText = makeStr;
    }
    else {
      routeText = routeText + "," + makeStr;
    }
  }

  if (routeText.trim().equals("")) {
    return "";
  }
  //System.out.println("·�ߴ�routeText: " + routeText);
  return routeText;
  }
  //CCEnd SS41
  
  //CCBegin SS42
  /**
	 * @param param
	 *            ��ά���飬5��Ԫ�ء�����obj[0]=��ţ�obj[1]=true.
	 *            true=�ǣ�false=�ǡ�����˳�򣺱�š����ơ����𣨺��֣������ڲ�Ʒ���汾�š�
	 * @roseuid 402CBAF700CA
	 * @J2EE_METHOD -- findRouteLists ��ù���·�߱�������Χ����š����ơ��������ڲ�Ʒ���汾�š�
	 * @return collection ����·�߱�ֵ�������°汾�� ��ʱҪ��ConfigService���й��ˡ�
	 */
	public Collection findRouteListsForCd(Object[][] param) throws QMException {
		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();
		QMQuery query = new QMQuery(ROUTELIST_BSONAME);
		
		if(param.length>4)
		{
		 boolean ignore =   ( (Boolean) param[5][0]).booleanValue();
		 query.setIgnoreCase(ignore);
		}
		 // int n = query.appendBso(PARTMASTER_BSONAME, false);
		query.setChildQuery(false);
		String number = (String) param[0][0];
		boolean numberFlag = ((Boolean) param[0][1]).booleanValue();
		if (number != null && number.trim().length() != 0) {
			QueryCondition cond = RouteHelper.handleWildcard("routeListNumber",
					number, numberFlag);
			query.addCondition(0, cond);
			query.addAND();
		}
		String name = (String) param[1][0];
		boolean nameFlag = ((Boolean) param[1][1]).booleanValue();
		if (name != null && name.trim().length() != 0) {
			QueryCondition cond1 = RouteHelper.handleWildcard("routeListName",
					name, nameFlag);
			query.addCondition(0, cond1);
			query.addAND();
		}
	
		QueryCondition cond12 = new QueryCondition("iterationIfLatest",
				Boolean.TRUE);
		query.addCondition(cond12);
		if (VERBOSE) {
			System.out.println(TIME
					+ "routeService's findRouteLists else... clause, SQL = "
					+ query.getDebugSQL());
		}
		// added by dikef for search by create time
		String beginTime = (String) param[2][0];
		// System.out.println("beginTime="+beginTime);
		String endTime = (String) param[3][0];
		// System.out.println("endTime="+endTime);
		Timestamp beginTimestamp = null;
		Timestamp endTimestamp = null;
		/*
		 * String data = "2005/11/17 15:32:05"; Timestamp ts = new Timestamp(new
		 * Date(data).getTime()); QueryCondition qc = new
		 * QueryCondition("createTime", "<=", ts);
		 */
	
		if (beginTime != null && beginTime.trim().length() > 0) {
			// modified by dikef
			// beginTime = beginTime + " 00:00:00";
			StringTokenizer beginST = new StringTokenizer(beginTime, "/");
			int k = beginST.countTokens();
			if (k == 4) {
				int i = beginTime.lastIndexOf("/");
	
				String hour = beginTime.substring(i + 1);
				beginTime = beginTime.substring(0, i) + " 00:00:00";
				// modified by dikef end
				beginTimestamp = new Timestamp(new Date(beginTime).getTime());
				beginTimestamp.setHours((new Integer(hour)).intValue());
				QueryCondition beginTimecondition = new QueryCondition(
						"createTime", ">=", beginTimestamp);
				query.addAND();
				query.addCondition(beginTimecondition);
			} else {
				beginTime = beginTime + " 00:00:00";
				beginTimestamp = new Timestamp(new Date(beginTime).getTime());
				QueryCondition beginTimecondition = new QueryCondition(
						"createTime", ">=", beginTimestamp);
				query.addAND();
				query.addCondition(beginTimecondition);
	
			}
		}
		if (endTime != null && endTime.trim().length() > 0) {
			StringTokenizer endST = new StringTokenizer(endTime, "/");
			int k = endST.countTokens();
			if (k == 4) {
	
				int j = endTime.lastIndexOf("/");
				String hour = endTime.substring(j + 1);
				endTime = endTime.substring(0, j) + " 24:00:00";
				// endTime = endTime + " 24:00:00";
				endTimestamp = new Timestamp(new Date(endTime).getTime());
				endTimestamp.setHours((new Integer(hour)).intValue());
				QueryCondition endTimecondition = new QueryCondition(
						"createTime", "<=", endTimestamp);
				query.addAND();
				query.addCondition(endTimecondition);
			} else {
				endTime = endTime + " 22:00:00";
				endTimestamp = new Timestamp(new Date(endTime).getTime());
				QueryCondition endTimecondition = new QueryCondition(
						"createTime", "<=", endTimestamp);
				query.addAND();
				query.addCondition(endTimecondition);
	
			}
		}
	
		// added by dikef for search by create time
		query.setDisticnt(true);
		// addListOrderBy(query);
		// routelistֵ���󼯺ϡ�
		Collection result = pservice.findValueInfo(query);
		// ���ݹ���״̬���й��ˡ�ԭ����������ͬʱ���ڡ���ù���·�߱�ֵ���󼯺ϡ�
		// filterByWorkState(result);
		// ����·�߱����������С�
		// modified by dikef 20060824
		// Collection sortedVec = RouteHelper.sortedInfos(result,
		// "getRouteListNumber", false);
		return result;
	}
  //CCEnd SS42
  
  
  //CCBegin SS43
  /**
   * �жϸø������·�����Ƿ�����и�·�ߵ�λ
   * @param part QMPartIfc
   * @param atts String[]
   * @throws QMException
   * @return Vector
   */
  public boolean isIncludeDepartment(QMPartIfc part, String routeDepartment) throws
      QMException {
    boolean falg = false;
    PersistService pService = (PersistService) EJBServiceHelper.getService(
        "PersistService");
    ListRoutePartLinkInfo info = null;
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition qc = new QueryCondition("rightBsoID", "=",
                                           part.getMasterBsoID());
    query.addCondition(qc);
    QueryCondition qc1 = new QueryCondition("currentEffctive", true);
    query.addAND();
    query.addCondition(qc1);
    Collection cols = pService.findValueInfo(query, false);
    Iterator ite = cols.iterator();
    if (ite.hasNext()) {
      info = (ListRoutePartLinkInfo) ite.next();
    }
    if (info != null) {

      HashMap map = (HashMap) getRouteBranchs(info.getRouteID());
      //·�ߴ�
      Iterator values = map.values().iterator();
      //���·�߷�֧�ķ���,����ֵ��hashmap,ֵ��һ������,����ĵ�һ��Ԫ����vector
      while (values.hasNext()) {

        Object[] objs1 = (Object[]) values.next();
        Vector makeNodes = (Vector) objs1[0]; //����ڵ�
        RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //װ��ڵ�
        if (makeNodes != null && makeNodes.size() > 0) {
          for (int m = 0; m < makeNodes.size(); m++) {
            RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
            if (node.getNodeDepartmentName().equalsIgnoreCase(routeDepartment)) {
              falg = true;
              break;
            }
          }
        }
        if (asseNode != null) {
          if (asseNode.getNodeDepartmentName().equalsIgnoreCase(routeDepartment)) {
            falg = true;
            break;
          }
        }
      }
    }
    return falg;

  }
  /**
   * רΪ���������൥����
   * lilei add 2006-7-11
   * @param part QMPartIfc
   * @param atts String[]
   * @param routes String[]
   * @throws QMException
   * @return Vector
   */
  public String[] getMaterialRoute(QMPartIfc part, String[] atts,
                                   String[] routes) throws QMException {
    PersistService pService = (PersistService) EJBServiceHelper.getService(
        "PersistService");
    ListRoutePartLinkInfo info = null;
    TechnicsRouteListInfo routelist = null;
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition qc = new QueryCondition("rightBsoID", "=",
                                           part.getMasterBsoID());
    query.addCondition(qc);
    QueryCondition qc1 = new QueryCondition("currentEffctive", true);
    query.addAND();
    query.addCondition(qc1);
    Collection cols = null;
    try {
      cols = pService.findValueInfo(query, false);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    Iterator ite = cols.iterator();
    if (ite.hasNext()) {
      info = (ListRoutePartLinkInfo) ite.next();
      routelist = (TechnicsRouteListInfo) pService.refreshInfo(info.
          getLeftBsoID());
    }
    if (info != null) {
      try {
        HashMap map = (HashMap) getRouteBranchs(info.getRouteID());
        Iterator values = map.values().iterator();
        String routeText = "";
        String assRouteText = "";
        String temproutetext = "";
        String tempassroutetext = "";
        while (values.hasNext()) {
          boolean isTemp = false;
          String makeStr = "";
          Object[] objs1 = (Object[]) values.next();
          Vector makeNodes = (Vector) objs1[0]; //����ڵ�
          RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //װ��ڵ�
          //�����ж�װ�����ǲ���
          if (asseNode != null && asseNode.getIsTempRoute()) {
            isTemp = true;
          }
          if (makeNodes != null && makeNodes.size() > 0) {
            //��ÿһ������ڵ���
            for (int m = 0; m < makeNodes.size(); m++) {
              RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
              if (node.getIsTempRoute()) {
                isTemp = true;
              }
              if (makeStr == "") {
                makeStr = makeStr + node.getNodeDepartmentName();
              }
              else {
                makeStr = makeStr + "-" + node.getNodeDepartmentName();
              }
            }
          }
          //�������ʱ·��,��������·���Ա߼�����ʱ����
          if (isTemp) {
            //makeStr = makeStr + "(��ʱ)";
            if (makeStr == null || makeStr.equals("")) {
              makeStr = "";
            }
            if (!temproutetext.trim().equals("")) {
              temproutetext = temproutetext + "/" + makeStr;
            }
            else {
              temproutetext = temproutetext + makeStr;
            }

            if (asseNode != null) {
              if (!tempassroutetext.trim().equals("")) {
                tempassroutetext = tempassroutetext + "/" +
                    asseNode.getNodeDepartmentName();
              }
              else {
                tempassroutetext = tempassroutetext +
                    asseNode.getNodeDepartmentName();
              }
            }
            continue;
          }
          //�������·��Ϊ��
          if (makeStr == null || makeStr.equals("")) {
            makeStr = "";
          }
          //
          if (!routeText.trim().equals("")) {
            routeText = routeText + "/" + makeStr;
          }
          else {
            routeText = routeText + makeStr;
          }
          if (asseNode != null) {
            if (!assRouteText.trim().equals("")) {
              assRouteText = assRouteText + "/" +
                  asseNode.getNodeDepartmentName();
            }
            else {
              assRouteText = assRouteText +
                  asseNode.getNodeDepartmentName();
            }
          }
        }
        for (int j = 0; j < atts.length; j++) {
          //��ø���������
          String att = atts[j];
          //����ֵ
          String value = "";
          //�������׼���
          if (att.equals("��׼���")) {
            value = routelist.getRouteListNumber();
          } //����Ǳ�ע
          else if (att.equals("��ע")) {
            if (info.getRouteID() != null && info.getRouteID().length() > 0) {
              TechnicsRouteInfo route = (TechnicsRouteInfo) pService.
                  refreshInfo(info.getRouteID(), false);
              value = route.getRouteDescription();
            }
          }
          else if (att.equals("����·��")) {
            value = routeText;
          }
          else if (att.equals("װ��·��")) {
            value = assRouteText;
          }
          else if (att.equals("��ʱ����·��")) {
            value = temproutetext;
          }
          else if (att.equals("��ʱװ��·��")) {
            value = tempassroutetext;
          }
          routes[j] = value;
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    return routes;
  }
  //CCEnd SS43
  //CCBegin SS47
  /**�ɶ����������㲿��
 * @param param
 * @return
 * @throws QMException
 */
public Collection cDfindMultPartsByNumbers(Object[] param)throws QMException
  {
      Collection result = null;
      try
      {
          if(param != null && param.length > 0)
          {
              PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
              QMQuery query = new QMQuery("QMPartMaster");
              query.setChildQuery(false);
              System.out.println("��������======"+param.length);
              for(int i = 0;i < param.length;i++)
              {
                  if(param[i] != null && param[i].toString().trim().length() > 0)
                  {
                      QueryCondition cond = new QueryCondition("partNumber", QueryCondition.EQUAL,param[i].toString());
                      if(query.getConditionCount() > 0)
                      {
                          query.addOR();
                      }
                      query.addCondition(cond);
                  }
              }
              if(query.getConditionCount() > 0)
              {
                  query.addOrderBy("partNumber", false);
                  result = pservice.findValueInfo(query, false);
              }
          }
      }catch(Exception e)
      {
          e.printStackTrace();
          throw new QMException(e);
      }

      return result;
  }
  //CCEnd SS47
//CCBegin SS48
/**
 * ������еĹ���·�߱�����°汾�������A,B�棬����B������С�汾�� @
 * @return Collection ���obj[]���ϣ�<br>obj[0]������·�߱�ֵ����
 */
public boolean searchSameNameList(TechnicsRouteListIfc routeListInfo)throws QMException
{
	boolean flag = false;
    PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    QMQuery query = new QMQuery(ROUTELISTMASTER_BSONAME);
    Collection coll = pservice.findValueInfo(query);
    //Ҫ�����·�߱������
    String yname = routeListInfo.getRouteListName();
    for(Iterator iter = coll.iterator();iter.hasNext();)
    {
        TechnicsRouteListMasterIfc listMasterInfo = (TechnicsRouteListMasterIfc)iter.next();
        String name = listMasterInfo.getRouteListName();
        if(name.equals(yname)){
        	flag = true;
        }
    }
    return flag;
}
//CCEnd SS48
}
